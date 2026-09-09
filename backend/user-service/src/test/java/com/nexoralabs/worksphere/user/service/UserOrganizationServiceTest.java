package com.nexoralabs.worksphere.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nexoralabs.worksphere.shared.exception.ApiException;
import com.nexoralabs.worksphere.user.api.UserDtos;
import com.nexoralabs.worksphere.user.domain.Employee;
import com.nexoralabs.worksphere.user.domain.Organization;
import com.nexoralabs.worksphere.user.repository.DepartmentRepository;
import com.nexoralabs.worksphere.user.repository.EmployeeDepartmentRepository;
import com.nexoralabs.worksphere.user.repository.EmployeeRepository;
import com.nexoralabs.worksphere.user.repository.EmployeeTeamRepository;
import com.nexoralabs.worksphere.user.repository.OrganizationRepository;
import com.nexoralabs.worksphere.user.repository.TeamRepository;
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
class UserOrganizationServiceTest {
    @Mock OrganizationRepository organizations;
    @Mock EmployeeRepository employees;
    @Mock DepartmentRepository departments;
    @Mock TeamRepository teams;
    @Mock EmployeeDepartmentRepository employeeDepartments;
    @Mock EmployeeTeamRepository employeeTeams;
    private UserOrganizationService service;

    @BeforeEach
    void setUp() {
        service = new UserOrganizationService(organizations, employees, departments, teams, employeeDepartments, employeeTeams);
    }

    @Test
    void managerCannotReadEmployeeFromAnotherOrganization() {
        UUID managerId = UUID.randomUUID();
        Organization first = organization(UUID.randomUUID());
        Organization second = organization(UUID.randomUUID());
        Employee manager = employee(managerId, first);
        Employee target = employee(UUID.randomUUID(), second);
        when(employees.findById(target.getId())).thenReturn(Optional.of(target));
        when(employees.findByAuthUserIdAndDeletedFalse(managerId)).thenReturn(Optional.of(manager));

        ApiException exception = assertThrows(ApiException.class, () -> service.getEmployee(target.getId(), auth(managerId, "ROLE_MANAGER")));
        assertEquals("FORBIDDEN", exception.getErrorCode());
    }

    @Test
    void employeeCanReadOwnProfileWithoutAdminRole() {
        UUID callerId = UUID.randomUUID();
        Employee employee = employee(UUID.randomUUID(), organization(UUID.randomUUID()));
        employee.setAuthUserId(callerId);
        when(employees.findById(employee.getId())).thenReturn(Optional.of(employee));

        UserDtos.EmployeeResponse response = service.getEmployee(employee.getId(), auth(callerId));

        assertEquals(employee.getId(), response.id());
    }

    private Authentication auth(UUID id, String... roles) {
        return new UsernamePasswordAuthenticationToken(id, null, List.of(roles).stream().map(SimpleGrantedAuthority::new).toList());
    }
    private Organization organization(UUID id) { Organization value = new Organization(); setId(value, id); return value; }
    private Employee employee(UUID authId, Organization organization) { Employee value = new Employee(); setId(value, UUID.randomUUID()); value.setAuthUserId(authId); value.setOrganization(organization); value.setEmployeeCode("E1"); value.setFirstName("First"); value.setLastName("Last"); value.setEmail("user@example.com"); return value; }
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
