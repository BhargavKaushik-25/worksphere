package com.nexoralabs.worksphere.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {
    public enum Status { ACTIVE, INACTIVE }
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
    @Column(name = "auth_user_id", nullable = false) private java.util.UUID authUserId;
    @Column(name = "employee_code", nullable = false, length = 50) private String employeeCode;
    @Column(name = "first_name", nullable = false, length = 100) private String firstName;
    @Column(name = "last_name", nullable = false, length = 100) private String lastName;
    @Column(nullable = false, length = 255) private String email;
    @Column(length = 20) private String phone;
    @Enumerated(EnumType.STRING) @Column(name = "account_status", nullable = false, length = 20) private Status status = Status.ACTIVE;
    public Organization getOrganization() { return organization; }
    public void setOrganization(Organization organization) { this.organization = organization; }
    public java.util.UUID getAuthUserId() { return authUserId; }
    public void setAuthUserId(java.util.UUID authUserId) { this.authUserId = authUserId; }
    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
