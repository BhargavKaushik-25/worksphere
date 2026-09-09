package com.nexoralabs.worksphere.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "project_member")
public class ProjectMember extends BaseEntity {
    public enum Role { MANAGER, MEMBER }
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @Column(name = "member_id", nullable = false) private UUID memberId;
    @Enumerated(EnumType.STRING) @Column(name = "project_role", nullable = false, length = 30) private Role role = Role.MEMBER;
    @Column(name = "joined_at", nullable = false) private Instant joinedAt = Instant.now();
    public Project getProject() { return project; }
    public void setProject(Project value) { project = value; }
    public UUID getMemberId() { return memberId; }
    public void setMemberId(UUID value) { memberId = value; }
    public Role getRole() { return role; }
    public void setRole(Role value) { role = value; }
    public Instant getJoinedAt() { return joinedAt; }
}
