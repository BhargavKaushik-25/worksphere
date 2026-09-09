package com.nexoralabs.worksphere.project.api;

import com.nexoralabs.worksphere.project.domain.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

public final class ProjectDtos {
    private ProjectDtos() { }
    public record CreateProjectRequest(@NotNull UUID organizationId, UUID workspaceId,
            @NotBlank @Size(max = 150) String name, @NotBlank @Size(max = 30) String key,
            String description, @NotNull UUID projectManagerId, @NotNull LocalDate startDate, LocalDate endDate) { }
    public record UpdateStatusRequest(@NotNull Project.Status status) { }
    public record MemberRequest(@NotNull UUID memberId, ProjectMemberRole projectRole) { }
    public enum ProjectMemberRole { MANAGER, MEMBER }
    public record ProjectResponse(UUID id, UUID organizationId, UUID workspaceId, String name, String key,
            String description, Project.Status status, UUID projectManagerId, LocalDate startDate, LocalDate endDate) { }
    public record ProjectMemberResponse(UUID id, UUID projectId, UUID memberId, ProjectMemberRole projectRole) { }
}
