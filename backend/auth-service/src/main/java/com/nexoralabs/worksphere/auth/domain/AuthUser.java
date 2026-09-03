package com.nexoralabs.worksphere.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "auth_user")
@SQLRestriction("is_deleted = false")
public class AuthUser {
    @Id @GeneratedValue private UUID id;
    @Column(nullable = false, length = 100) private String username;
    @Column(nullable = false, length = 255) private String email;
    @Column(name = "password_hash", nullable = false) private String passwordHash;
    @Enumerated(EnumType.STRING) @Column(name = "account_status", nullable = false, length = 20)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;
    @Column(nullable = false) private boolean enabled = true;
    @Column(name = "locked_until") private Instant lockedUntil;
    @Column(name = "last_login_at") private Instant lastLoginAt;
    @Column(name = "created_at", nullable = false) private Instant createdAt = Instant.now();
    @Column(name = "created_by") private UUID createdBy;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt = Instant.now();
    @Column(name = "updated_by") private UUID updatedBy;
    @Column(name = "deleted_at") private Instant deletedAt;
    @Column(name = "deleted_by") private UUID deletedBy;
    @Column(name = "is_deleted", nullable = false) private boolean deleted;
    @Version private long version;

    public UUID getId() { return id; } public void setId(UUID value) { id = value; }
    public String getUsername() { return username; } public void setUsername(String value) { username = value; }
    public String getEmail() { return email; } public void setEmail(String value) { email = value; }
    public String getPasswordHash() { return passwordHash; } public void setPasswordHash(String value) { passwordHash = value; }
    public AccountStatus getAccountStatus() { return accountStatus; } public void setAccountStatus(AccountStatus value) { accountStatus = value; }
    public boolean isEnabled() { return enabled; } public void setEnabled(boolean value) { enabled = value; }
    public Instant getLockedUntil() { return lockedUntil; } public void setLockedUntil(Instant value) { lockedUntil = value; }
    public Instant getLastLoginAt() { return lastLoginAt; } public void setLastLoginAt(Instant value) { lastLoginAt = value; }
}