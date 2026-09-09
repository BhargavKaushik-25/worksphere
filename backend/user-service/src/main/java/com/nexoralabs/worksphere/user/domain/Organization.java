package com.nexoralabs.worksphere.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "organization")
public class Organization extends BaseEntity {
    @Column(name = "organization_name", nullable = false, length = 150) private String name;
    @Column(name = "organization_code", nullable = false, length = 50) private String code;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}
