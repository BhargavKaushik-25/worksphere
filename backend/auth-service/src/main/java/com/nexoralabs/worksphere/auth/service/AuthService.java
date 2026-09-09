package com.nexoralabs.worksphere.auth.service;

import com.nexoralabs.worksphere.auth.api.AuthDtos;
import com.nexoralabs.worksphere.auth.config.AuthProperties;
import com.nexoralabs.worksphere.auth.domain.AccountStatus;
import com.nexoralabs.worksphere.auth.domain.AuthUser;
import com.nexoralabs.worksphere.auth.domain.LoginAttempt;
import com.nexoralabs.worksphere.auth.domain.RefreshToken;
import com.nexoralabs.worksphere.auth.repository.AuthUserRepository;
import com.nexoralabs.worksphere.auth.repository.AuthUserRoleRepository;
import com.nexoralabs.worksphere.auth.repository.LoginAttemptRepository;
import com.nexoralabs.worksphere.auth.repository.RefreshTokenRepository;
import com.nexoralabs.worksphere.auth.security.JwtService;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final AuthUserRepository users;
    private final AuthUserRoleRepository userRoles;
    private final RefreshTokenRepository refreshTokens;
    private final LoginAttemptRepository attempts;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthProperties properties;
    private final SecureRandom secureRandom = new SecureRandom();

    public AuthService(AuthUserRepository users, AuthUserRoleRepository userRoles, RefreshTokenRepository refreshTokens,
            LoginAttemptRepository attempts, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthProperties properties) {
        this.users = users; this.userRoles = userRoles; this.refreshTokens = refreshTokens; this.attempts = attempts;
        this.passwordEncoder = passwordEncoder; this.jwtService = jwtService; this.properties = properties;
    }

    @Transactional
    public AuthDtos.TokenResponse login(AuthDtos.LoginRequest request) {
        AuthUser user = users.findByEmailIgnoreCase(request.email()).orElse(null);
        if (user == null) { audit(request.email(), null, false, "INVALID_CREDENTIALS"); throw invalid(); }
        Instant now = Instant.now();
        if (user.getAccountStatus() == AccountStatus.LOCKED && user.getLockedUntil() != null
                && user.getLockedUntil().isAfter(now)) {
            audit(request.email(), user, false, "ACCOUNT_LOCKED");
            throw new AuthException("ACCOUNT_LOCKED", "Account is locked", 423);
        }
        if (user.getAccountStatus() == AccountStatus.LOCKED) { user.setAccountStatus(AccountStatus.ACTIVE); user.setLockedUntil(null); }
        if (!user.isEnabled() || user.getAccountStatus() == AccountStatus.INACTIVE) {
            audit(request.email(), user, false, "ACCOUNT_INACTIVE");
            throw new AuthException("ACCOUNT_INACTIVE", "Account is inactive", 403);
        }
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            recordFailure(user, request.email()); throw invalid();
        }
        audit(request.email(), user, true, null);
        user.setLastLoginAt(now);
        List<String> roles = userRoles.findRoleNames(user.getId());
        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail(), roles);
        String rawRefresh = randomToken();
        RefreshToken refresh = new RefreshToken(); refresh.setUser(user); refresh.setTokenHash(hash(rawRefresh));
        refresh.setExpiresAt(now.plus(properties.getJwt().getRefreshTokenExpiration())); refreshTokens.save(refresh);
        return new AuthDtos.TokenResponse(accessToken, rawRefresh, properties.getJwt().getAccessTokenExpiration().toSeconds());
    }

    @Transactional
    public AuthDtos.TokenResponse refresh(AuthDtos.RefreshRequest request) {
        RefreshToken existing = refreshTokens.findByTokenHash(hash(request.refreshToken()))
                .orElseThrow(() -> new AuthException("INVALID_REFRESH_TOKEN", "Invalid refresh token", 401));
        Instant now = Instant.now();
        if (existing.getRevokedAt() != null || !existing.getExpiresAt().isAfter(now)) {
            throw new AuthException("INVALID_REFRESH_TOKEN", "Invalid refresh token", 401);
        }
        AuthUser user = existing.getUser();
        if (!user.isEnabled() || user.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new AuthException("ACCOUNT_UNAVAILABLE", "Account is not available", 403);
        }
        existing.setRevokedAt(now);
        refreshTokens.save(existing);
        List<String> roles = userRoles.findRoleNames(user.getId());
        return issueTokens(user, roles, now);
    }

    @Transactional
    public void logout(String rawToken) {
        refreshTokens.findByTokenHash(hash(rawToken)).ifPresent(token -> { token.setRevokedAt(Instant.now()); refreshTokens.save(token); });
    }

    @Transactional(readOnly = true)
    public AuthDtos.UserResponse me(UUID userId) {
        AuthUser user = users.findById(userId).orElseThrow(() -> new AuthException("UNAUTHORIZED", "User not found", 401));
        return new AuthDtos.UserResponse(user.getId(), user.getEmail(), user.getUsername(), userRoles.findRoleNames(userId));
    }

    private void recordFailure(AuthUser user, String email) {
        audit(email, user, false, "INVALID_CREDENTIALS");
        long failures = attempts.countByUserIdAndSuccessfulFalseAndAttemptedAtAfter(user.getId(),
                Instant.now().minus(properties.getLockout().getDuration()));
        if (failures >= properties.getLockout().getMaxFailedAttempts()) {
            user.setAccountStatus(AccountStatus.LOCKED); user.setLockedUntil(Instant.now().plus(properties.getLockout().getDuration()));
        }
    }

    private void audit(String email, AuthUser user, boolean successful, String reason) {
        LoginAttempt attempt = new LoginAttempt(); attempt.setEmail(email); attempt.setUser(user);
        attempt.setSuccessful(successful); attempt.setFailureReason(reason); attempts.save(attempt);
    }
    private AuthDtos.TokenResponse issueTokens(AuthUser user, List<String> roles, Instant now) {
        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail(), roles);
        String rawRefresh = randomToken();
        RefreshToken refresh = new RefreshToken();
        refresh.setUser(user);
        refresh.setTokenHash(hash(rawRefresh));
        refresh.setExpiresAt(now.plus(properties.getJwt().getRefreshTokenExpiration()));
        refreshTokens.save(refresh);
        return new AuthDtos.TokenResponse(accessToken, rawRefresh,
                properties.getJwt().getAccessTokenExpiration().toSeconds());
    }
    private AuthException invalid() { return new AuthException("INVALID_CREDENTIALS", "Invalid email or password", 401); }
    private String randomToken() { byte[] bytes = new byte[48]; secureRandom.nextBytes(bytes); return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes); }
    private String hash(String value) { try { return Base64.getUrlEncoder().withoutPadding().encodeToString(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8))); } catch (Exception e) { throw new IllegalStateException(e); } }
}