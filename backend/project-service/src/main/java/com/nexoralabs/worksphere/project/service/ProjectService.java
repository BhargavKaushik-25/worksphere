package com.nexoralabs.worksphere.project.service;

import com.nexoralabs.worksphere.project.api.ProjectDtos;
import com.nexoralabs.worksphere.project.domain.Project;
import com.nexoralabs.worksphere.project.domain.ProjectMember;
import com.nexoralabs.worksphere.project.repository.ProjectMemberRepository;
import com.nexoralabs.worksphere.project.repository.ProjectRepository;
import com.nexoralabs.worksphere.shared.exception.ApiException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {
    private final ProjectRepository projects;
    private final ProjectMemberRepository members;

    public ProjectService(ProjectRepository projects, ProjectMemberRepository members) {
        this.projects = projects; this.members = members;
    }

    @Transactional
    public ProjectDtos.ProjectResponse create(ProjectDtos.CreateProjectRequest request, Authentication caller) {
        requireOrganizationManager(caller, request.organizationId());
        validateDates(request.startDate(), request.endDate());
        if (projects.existsByOrganizationIdAndKeyIgnoreCaseAndDeletedFalse(request.organizationId(), request.key())) throw conflict("Project key already exists");
        Project project = new Project(); project.setOrganizationId(request.organizationId()); project.setWorkspaceId(request.workspaceId()); project.setName(request.name());
        project.setKey(request.key()); project.setDescription(request.description()); project.setProjectManagerId(request.projectManagerId()); project.setStartDate(request.startDate()); project.setEndDate(request.endDate());
        project.setCreatedBy(callerId(caller));
        return response(projects.save(project));
    }

    @Transactional(readOnly = true)
    public ProjectDtos.ProjectResponse get(UUID projectId, Authentication caller) {
        Project project = project(projectId); requireVisible(project, caller); return response(project);
    }

    @Transactional(readOnly = true)
    public List<ProjectDtos.ProjectResponse> filter(UUID organizationId, Project.Status status, String search, Authentication caller) {
        requireOrganizationManagerOrMember(caller, organizationId);
        return projects.filter(organizationId, status, search == null ? "" : search).stream().filter(project -> canSee(project, caller)).map(this::response).toList();
    }

    @Transactional
    public ProjectDtos.ProjectResponse updateStatus(UUID projectId, ProjectDtos.UpdateStatusRequest request, Authentication caller) {
        Project project = project(projectId); requireManager(project, caller); validateTransition(project.getStatus(), request.status());
        project.setStatus(request.status()); project.setUpdatedBy(callerId(caller));
        if (request.status() == Project.Status.ARCHIVED) { project.setDeletedAt(java.time.Instant.now()); project.setDeleted(true); project.setDeletedBy(callerId(caller)); }
        return response(projects.save(project));
    }

    @Transactional
    public ProjectDtos.ProjectMemberResponse addMember(UUID projectId, ProjectDtos.MemberRequest request, Authentication caller) {
        Project project = project(projectId); requireManager(project, caller);
        if (members.existsByProjectIdAndMemberIdAndDeletedFalse(projectId, request.memberId())) throw conflict("Project member already exists");
        ProjectMember member = new ProjectMember(); member.setProject(project); member.setMemberId(request.memberId());
        member.setRole(request.projectRole() == null ? ProjectMember.Role.MEMBER : ProjectMember.Role.valueOf(request.projectRole().name()));
        return member(members.save(member));
    }

    @Transactional
    public ProjectDtos.ProjectResponse assignManager(UUID projectId, UUID managerId, Authentication caller) {
        Project project = project(projectId); requireManager(project, caller); project.setProjectManagerId(managerId); project.setUpdatedBy(callerId(caller)); return response(projects.save(project));
    }

    private void requireVisible(Project project, Authentication caller) { if (!canSee(project, caller)) throw forbidden(); }
    private boolean canSee(Project project, Authentication caller) {
        UUID callerId = callerId(caller);
        return hasRole(caller, "ADMIN") && organizationMatches(caller, project.getOrganizationId())
            || hasRole(caller, "ORGANIZATION_ADMIN") && organizationMatches(caller, project.getOrganizationId())
                || hasRole(caller, "MANAGER") && organizationMatches(caller, project.getOrganizationId())
                || project.getProjectManagerId().equals(callerId)
                || members.existsByProjectIdAndMemberIdAndDeletedFalse(project.getId(), callerId);
    }
    private void requireOrganizationManager(Authentication caller, UUID organizationId) { if (!(hasRole(caller, "ADMIN") || hasRole(caller, "ORGANIZATION_ADMIN") || hasRole(caller, "MANAGER")) || !organizationMatches(caller, organizationId)) throw forbidden(); }
    private void requireOrganizationManagerOrMember(Authentication caller, UUID organizationId) {
        if (!organizationMatches(caller, organizationId) && !hasMemberInOrganization(caller, organizationId)) throw forbidden();
    }
    private void requireManager(Project project, Authentication caller) { if (!canManage(project, caller)) throw forbidden(); }
    private boolean canManage(Project project, Authentication caller) { return hasRole(caller, "ADMIN") && organizationMatches(caller, project.getOrganizationId()) || hasRole(caller, "ORGANIZATION_ADMIN") && organizationMatches(caller, project.getOrganizationId()) || hasRole(caller, "MANAGER") && organizationMatches(caller, project.getOrganizationId()) || project.getProjectManagerId().equals(callerId(caller)); }
    private boolean hasMemberInOrganization(Authentication caller, UUID organizationId) {
        UUID callerId = callerId(caller);
        return members.findByMemberIdAndDeletedFalse(callerId).stream()
                .anyMatch(member -> member.getProject().getOrganizationId().equals(organizationId));
    }
    private boolean organizationMatches(Authentication caller, UUID organizationId) {
        Object details = caller == null ? null : caller.getDetails();
        UUID context = details instanceof UUID value ? value : details instanceof String value ? parseUuid(value) : null;
        return context != null && context.equals(organizationId);
    }
    private UUID parseUuid(String value) { try { return UUID.fromString(value); } catch (IllegalArgumentException ignored) { return null; } }
    private UUID callerId(Authentication caller) { if (caller == null || !(caller.getPrincipal() instanceof UUID id)) throw unauthorized(); return id; }
    private boolean hasRole(Authentication caller, String role) { return caller != null && caller.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(value -> value.equals(role) || value.equals("ROLE_" + role)); }
    private Project project(UUID id) { return projects.findByIdAndDeletedFalse(id).orElseThrow(() -> notFound("Project not found")); }
    private void validateDates(LocalDate start, LocalDate end) { if (end != null && end.isBefore(start)) throw new ApiException("INVALID_DATES", "Project end date must not precede start date", HttpStatus.BAD_REQUEST); }
    private void validateTransition(Project.Status current, Project.Status next) { if (current == Project.Status.ARCHIVED && next != Project.Status.ARCHIVED) throw conflict("Archived projects cannot be reopened"); }
    private ProjectDtos.ProjectResponse response(Project p) { return new ProjectDtos.ProjectResponse(p.getId(), p.getOrganizationId(), p.getWorkspaceId(), p.getName(), p.getKey(), p.getDescription(), p.getStatus(), p.getProjectManagerId(), p.getStartDate(), p.getEndDate()); }
    private ProjectDtos.ProjectMemberResponse member(ProjectMember m) { return new ProjectDtos.ProjectMemberResponse(m.getId(), m.getProject().getId(), m.getMemberId(), ProjectDtos.ProjectMemberRole.valueOf(m.getRole().name())); }
    private ApiException forbidden() { return new ApiException("FORBIDDEN", "Access denied", HttpStatus.FORBIDDEN); }
    private ApiException unauthorized() { return new ApiException("UNAUTHORIZED", "Authentication is required", HttpStatus.UNAUTHORIZED); }
    private ApiException notFound(String message) { return new ApiException("NOT_FOUND", message, HttpStatus.NOT_FOUND); }
    private ApiException conflict(String message) { return new ApiException("CONFLICT", message, HttpStatus.CONFLICT); }
}
