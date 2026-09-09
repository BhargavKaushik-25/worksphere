package com.nexoralabs.worksphere.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "project")
public class Project extends BaseEntity {
    public enum Status { PLANNED, ACTIVE, COMPLETED, ARCHIVED }
    @Column(name = "organization_id", nullable = false) private UUID organizationId;
    @Column(name = "workspace_id") private UUID workspaceId;
    @Column(name = "project_name", nullable = false, length = 150) private String name;
    @Column(name = "project_key", nullable = false, length = 30) private String key;
    private String description;
    @Enumerated(EnumType.STRING) @Column(name = "project_status", nullable = false, length = 20) private Status status = Status.PLANNED;
    @Column(name = "project_manager_id", nullable = false) private UUID projectManagerId;
    @Column(name = "start_date", nullable = false) private LocalDate startDate;
    @Column(name = "end_date") private LocalDate endDate;
    public UUID getOrganizationId() { return organizationId; }
    public void setOrganizationId(UUID value) { organizationId = value; }
    public UUID getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(UUID value) { workspaceId = value; }
    public String getName() { return name; }
    public void setName(String value) { name = value; }
    public String getKey() { return key; }
    public void setKey(String value) { key = value; }
    public String getDescription() { return description; }
    public void setDescription(String value) { description = value; }
    public Status getStatus() { return status; }
    public void setStatus(Status value) { status = value; }
    public UUID getProjectManagerId() { return projectManagerId; }
    public void setProjectManagerId(UUID value) { projectManagerId = value; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate value) { startDate = value; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate value) { endDate = value; }
}
