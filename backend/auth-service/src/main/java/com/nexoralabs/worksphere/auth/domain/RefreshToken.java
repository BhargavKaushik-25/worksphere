package com.nexoralabs.worksphere.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "refresh_token")
public class RefreshToken {
    @Id @GeneratedValue private UUID id;
    @ManyToOne(optional = false) @JoinColumn(name = "user_id") private AuthUser user;
    @Column(name = "token_hash", nullable = false, unique = true) private String tokenHash;
    @Column(name = "expires_at", nullable = false) private Instant expiresAt;
    @Column(name = "revoked_at") private Instant revokedAt;
    public UUID getId() { return id; } public void setId(UUID value) { id = value; }
    public AuthUser getUser() { return user; } public void setUser(AuthUser value) { user = value; }
    public String getTokenHash() { return tokenHash; } public void setTokenHash(String value) { tokenHash = value; }
    public Instant getExpiresAt() { return expiresAt; } public void setExpiresAt(Instant value) { expiresAt = value; }
    public Instant getRevokedAt() { return revokedAt; } public void setRevokedAt(Instant value) { revokedAt = value; }
}