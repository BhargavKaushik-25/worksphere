package com.nexoralabs.worksphere.auth.security;

import com.nexoralabs.worksphere.auth.config.AuthProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final AuthProperties properties;
    private SecretKey signingKey;

    public JwtService(AuthProperties properties) { this.properties = properties; }

    @PostConstruct
    void validateSecret() {
        String secret = properties.getJwt().getSecret();
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException("AUTH_JWT_SECRET must be configured with at least 32 characters");
        }
        signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UUID userId, String email, List<String> roles) {
        Instant now = Instant.now();
        return Jwts.builder().subject(userId.toString()).claim("email", email).claim("roles", roles)
                .id(UUID.randomUUID().toString()).issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(properties.getJwt().getAccessTokenExpiration())))
                .signWith(signingKey).compact();
    }

    public Claims parse(String token) {
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
    }
}