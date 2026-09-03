package com.nexoralabs.worksphere.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity @Table(name = "auth_user_role")
public class AuthUserRole {
    @Id @GeneratedValue private UUID id;
    @ManyToOne(optional = false) @JoinColumn(name = "user_id") private AuthUser user;
    @ManyToOne(optional = false) @JoinColumn(name = "role_id") private Role role;
    public UUID getId() { return id; } public void setId(UUID value) { id = value; }
    public AuthUser getUser() { return user; } public void setUser(AuthUser value) { user = value; }
    public Role getRole() { return role; } public void setRole(Role value) { role = value; }
}