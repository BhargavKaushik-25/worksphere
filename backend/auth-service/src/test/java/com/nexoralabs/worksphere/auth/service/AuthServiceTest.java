package com.nexoralabs.worksphere.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.nexoralabs.worksphere.auth.api.AuthDtos;
import com.nexoralabs.worksphere.auth.config.AuthProperties;
import com.nexoralabs.worksphere.auth.domain.AccountStatus;
import com.nexoralabs.worksphere.auth.domain.AuthUser;
import com.nexoralabs.worksphere.auth.repository.AuthUserRepository;
import com.nexoralabs.worksphere.auth.repository.AuthUserRoleRepository;
import com.nexoralabs.worksphere.auth.repository.LoginAttemptRepository;
import com.nexoralabs.worksphere.auth.repository.RefreshTokenRepository;
import com.nexoralabs.worksphere.auth.security.JwtService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock AuthUserRepository users;
    @Mock AuthUserRoleRepository userRoles;
    @Mock RefreshTokenRepository refreshTokens;
    @Mock LoginAttemptRepository attempts;
    @Mock PasswordEncoder passwordEncoder;
    @Mock JwtService jwtService;
    private AuthService service;

    @BeforeEach void setUp() { service = new AuthService(users, userRoles, refreshTokens, attempts, passwordEncoder, jwtService, properties()); }

    @Test void validLoginReturnsTokensWithoutPasswordHash() {
        AuthUser user = user(AccountStatus.ACTIVE, true); when(users.findByEmailIgnoreCase("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("secret", "bcrypt-hash")).thenReturn(true); when(userRoles.findRoleNames(user.getId())).thenReturn(List.of("USER"));
        when(jwtService.generateAccessToken(any(), anyString(), any())).thenReturn("access");
        AuthDtos.TokenResponse response = service.login(new AuthDtos.LoginRequest("user@example.com", "secret"));
        assertEquals("access", response.accessToken()); assertEquals(900, response.expiresIn());
    }

    @Test void invalidCredentialsAreRejected() {
        AuthUser user = user(AccountStatus.ACTIVE, true); when(users.findByEmailIgnoreCase("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("bad", "bcrypt-hash")).thenReturn(false);
        assertEquals("INVALID_CREDENTIALS", assertThrows(AuthException.class,
                () -> service.login(new AuthDtos.LoginRequest("user@example.com", "bad"))).getCode());
    }

    @Test void inactiveAndLockedAccountsAreRejected() {
        AuthUser inactive = user(AccountStatus.INACTIVE, true); when(users.findByEmailIgnoreCase("user@example.com")).thenReturn(Optional.of(inactive));
        assertEquals("ACCOUNT_INACTIVE", assertThrows(AuthException.class,
                () -> service.login(new AuthDtos.LoginRequest("user@example.com", "secret"))).getCode());
        AuthUser locked = user(AccountStatus.LOCKED, true); locked.setLockedUntil(java.time.Instant.now().plusSeconds(60));
        when(users.findByEmailIgnoreCase("user@example.com")).thenReturn(Optional.of(locked));
        assertEquals("ACCOUNT_LOCKED", assertThrows(AuthException.class,
                () -> service.login(new AuthDtos.LoginRequest("user@example.com", "secret"))).getCode());
    }

    @Test void meResponseDoesNotExposePasswordHash() {
        AuthUser user = user(AccountStatus.ACTIVE, true); when(users.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRoles.findRoleNames(user.getId())).thenReturn(List.of("USER"));
        AuthDtos.UserResponse response = service.me(user.getId());
        assertEquals(user.getEmail(), response.email());
        org.junit.jupiter.api.Assertions.assertFalse(java.util.Arrays.stream(response.getClass().getRecordComponents()).anyMatch(c -> c.getName().toLowerCase().contains("password")));
    }

    private AuthUser user(AccountStatus status, boolean enabled) {
        AuthUser user = new AuthUser(); user.setId(UUID.randomUUID()); user.setEmail("user@example.com"); user.setUsername("user");
        user.setPasswordHash("bcrypt-hash"); user.setAccountStatus(status); user.setEnabled(enabled); return user;
    }
    private AuthProperties properties() {
        AuthProperties properties = new AuthProperties(); properties.getJwt().setSecret("unit-test-secret-that-is-at-least-32-characters-long");
        return properties;
    }
}