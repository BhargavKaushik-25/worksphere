package com.nexoralabs.worksphere.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nexoralabs.worksphere.project.api.ProjectDtos;
import com.nexoralabs.worksphere.project.domain.Project;
import com.nexoralabs.worksphere.project.domain.ProjectMember;
import com.nexoralabs.worksphere.project.repository.ProjectMemberRepository;
import com.nexoralabs.worksphere.project.repository.ProjectRepository;
import com.nexoralabs.worksphere.shared.exception.ApiException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock ProjectRepository projects;
    @Mock ProjectMemberRepository members;
    private ProjectService service;
    private UUID organizationId;
    private UUID managerId;

    @BeforeEach
    void setUp() {
        service = new ProjectService(projects, members);
        organizationId = UUID.randomUUID();
        managerId = UUID.randomUUID();
    }

    @Test
    void managerCanCreateProjectWithinOrganization() {
        ProjectDtos.CreateProjectRequest request = new ProjectDtos.CreateProjectRequest(
                organizationId, null, "Roadmap", "ROAD", "MVP roadmap", managerId,
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 2, 1));
        when(projects.existsByOrganizationIdAndKeyIgnoreCaseAndDeletedFalse(organizationId, "ROAD")).thenReturn(false);
        when(projects.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProjectDtos.ProjectResponse response = service.create(request, auth(managerId, organizationId, "ROLE_MANAGER"));

        assertEquals("ROAD", response.key());
        assertEquals(Project.Status.PLANNED, response.status());
    }

    @Test
    void invalidDatesAreRejected() {
        ProjectDtos.CreateProjectRequest request = new ProjectDtos.CreateProjectRequest(
                organizationId, null, "Roadmap", "ROAD", null, managerId,
                LocalDate.of(2026, 2, 1), LocalDate.of(2026, 1, 1));

        ApiException exception = assertThrows(ApiException.class, () -> service.create(request, auth(managerId, organizationId, "ROLE_MANAGER")));
        assertEquals("INVALID_DATES", exception.getErrorCode());
    }

    @Test
    void managerCannotCreateProjectForAnotherOrganization() {
        ProjectDtos.CreateProjectRequest request = new ProjectDtos.CreateProjectRequest(
                organizationId, null, "Roadmap", "ROAD", null, managerId,
                LocalDate.of(2026, 1, 1), null);

        ApiException exception = assertThrows(ApiException.class, () -> service.create(request, auth(managerId, UUID.randomUUID(), "ROLE_MANAGER")));
        assertEquals("FORBIDDEN", exception.getErrorCode());
    }

    @Test
    void memberCanReadOnlyProjectTheyBelongTo() {
        UUID employeeId = UUID.randomUUID();
        Project project = project(Project.Status.ACTIVE);
        ProjectMember membership = new ProjectMember();
        membership.setProject(project);
        membership.setMemberId(employeeId);
        when(projects.findByIdAndDeletedFalse(project.getId())).thenReturn(Optional.of(project));
        when(members.existsByProjectIdAndMemberIdAndDeletedFalse(project.getId(), employeeId)).thenReturn(true);

        ProjectDtos.ProjectResponse response = service.get(project.getId(), auth(employeeId, null, "ROLE_EMPLOYEE"));

        assertEquals(project.getId(), response.id());
    }

    @Test
    void archiveCannotBeReopened() {
        Project project = project(Project.Status.ARCHIVED);
        when(projects.findByIdAndDeletedFalse(project.getId())).thenReturn(Optional.of(project));
        ProjectDtos.UpdateStatusRequest request = new ProjectDtos.UpdateStatusRequest(Project.Status.ACTIVE);

        ApiException exception = assertThrows(ApiException.class,
                () -> service.updateStatus(project.getId(), request, auth(managerId, organizationId, "ROLE_MANAGER")));
        assertEquals("CONFLICT", exception.getErrorCode());
    }

    private Authentication auth(UUID principal, UUID organization, String role) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                principal, null, List.of(new SimpleGrantedAuthority(role)));
        authentication.setDetails(organization);
        return authentication;
    }

    private Project project(Project.Status status) {
        Project project = new Project();
        setId(project, UUID.randomUUID());
        project.setOrganizationId(organizationId);
        project.setName("Roadmap"); project.setKey("ROAD"); project.setProjectManagerId(managerId);
        project.setStartDate(LocalDate.of(2026, 1, 1)); project.setStatus(status);
        return project;
    }

    private void setId(Object entity, UUID id) {
        try {
            var field = entity.getClass().getSuperclass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, id);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError(exception);
        }
    }
}
