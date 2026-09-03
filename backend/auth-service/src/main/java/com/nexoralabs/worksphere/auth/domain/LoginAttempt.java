package com.nexoralabs.worksphere.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.net.InetAddress;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "login_attempt")
public class LoginAttempt {
    @Id @GeneratedValue private UUID id;
    @ManyToOne @JoinColumn(name = "user_id") private AuthUser user;
    @Column(nullable = false) private String email;
    @Column(nullable = false) private boolean successful;
    @Column(name = "attempted_at", nullable = false) private Instant attemptedAt = Instant.now();
    @Column(name = "failure_reason") private String failureReason;
    @Column(name = "ip_address") private InetAddress ipAddress;
    public void setUser(AuthUser value) { user = value; } public void setEmail(String value) { email = value; }
    public void setSuccessful(boolean value) { successful = value; } public void setFailureReason(String value) { failureReason = value; }
    public void setIpAddress(InetAddress value) { ipAddress = value; }
}