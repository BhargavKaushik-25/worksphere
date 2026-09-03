package com.nexoralabs.worksphere.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity @Table(name = "role")
public class Role {
    @Id @GeneratedValue private UUID id;
    @Column(name = "role_name", nullable = false, length = 100) private String roleName;
    private String description;
    @Column(name = "is_deleted", nullable = false) private boolean deleted;
    public UUID getId() { return id; } public void setId(UUID value) { id = value; }
    public String getRoleName() { return roleName; } public void setRoleName(String value) { roleName = value; }
    public boolean isDeleted() { return deleted; } public void setDeleted(boolean value) { deleted = value; }
}