package com.nexoralabs.worksphere.user.service;

import com.nexoralabs.worksphere.shared.exception.ApiException;
import com.nexoralabs.worksphere.user.api.UserDtos;
import com.nexoralabs.worksphere.user.domain.Department;
import com.nexoralabs.worksphere.user.domain.Employee;
import com.nexoralabs.worksphere.user.domain.EmployeeDepartment;
import com.nexoralabs.worksphere.user.domain.EmployeeTeam;
import com.nexoralabs.worksphere.user.domain.Organization;
import com.nexoralabs.worksphere.user.domain.Team;
import com.nexoralabs.worksphere.user.repository.DepartmentRepository;
import com.nexoralabs.worksphere.user.repository.EmployeeDepartmentRepository;
import com.nexoralabs.worksphere.user.repository.EmployeeRepository;
import com.nexoralabs.worksphere.user.repository.EmployeeTeamRepository;
import com.nexoralabs.worksphere.user.repository.OrganizationRepository;
import com.nexoralabs.worksphere.user.repository.TeamRepository;
import java.util.UUID;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserOrganizationService {
    private final OrganizationRepository organizations;
    private final EmployeeRepository employees;
    private final DepartmentRepository departments;
    private final TeamRepository teams;
    private final EmployeeDepartmentRepository employeeDepartments;
    private final EmployeeTeamRepository employeeTeams;

    public UserOrganizationService(OrganizationRepository organizations, EmployeeRepository employees,
            DepartmentRepository departments, TeamRepository teams, EmployeeDepartmentRepository employeeDepartments,
            EmployeeTeamRepository employeeTeams) {
        this.organizations = organizations; this.employees = employees; this.departments = departments;
        this.teams = teams; this.employeeDepartments = employeeDepartments; this.employeeTeams = employeeTeams;
    }

    @Transactional
    public UserDtos.OrganizationResponse createOrganization(UserDtos.OrganizationRequest request, Authentication caller) {
        requireRole(caller, "ADMIN");
        if (organizations.findByCodeIgnoreCaseAndDeletedFalse(request.code()).isPresent())
            throw conflict("Organization code already exists");
        Organization organization = new Organization(); organization.setName(request.name()); organization.setCode(request.code());
        return organization(organizations.save(organization));
    }

    @Transactional
    public UserDtos.EmployeeResponse createEmployee(UUID organizationId, UserDtos.EmployeeRequest request, Authentication caller) {
        requireOrganizationAdmin(caller, organizationId);
        Organization organization = organization(organizationId);
        if (employees.findByAuthUserIdAndDeletedFalse(request.authUserId()).isPresent()
                || employees.existsByOrganizationIdAndEmployeeCodeIgnoreCaseAndDeletedFalse(organizationId, request.employeeCode())
                || employees.existsByOrganizationIdAndEmailIgnoreCaseAndDeletedFalse(organizationId, request.email()))
            throw conflict("Employee identity or profile already exists");
        Employee employee = new Employee(); employee.setOrganization(organization); employee.setAuthUserId(request.authUserId());
        employee.setEmployeeCode(request.employeeCode()); employee.setFirstName(request.firstName()); employee.setLastName(request.lastName());
        employee.setEmail(request.email()); employee.setPhone(request.phone());
        return employee(employees.save(employee));
    }

    @Transactional(readOnly = true)
    public UserDtos.EmployeeResponse getEmployee(UUID employeeId, Authentication caller) {
        Employee employee = employee(employeeId);
        requireVisible(caller, employee);
        return employee(employee);
    }

    @Transactional(readOnly = true)
    public UserDtos.EmployeeResponse getMyProfile(Authentication caller) {
        Employee employee = employees.findByAuthUserIdAndDeletedFalse(callerId(caller))
                .orElseThrow(() -> notFound("Employee profile not found"));
        return employee(employee);
    }

    @Transactional
    public UserDtos.EmployeeResponse updateMyProfile(UserDtos.ProfileUpdateRequest request, Authentication caller) {
        Employee employee = employees.findByAuthUserIdAndDeletedFalse(callerId(caller))
                .orElseThrow(() -> notFound("Employee profile not found"));
        employee.setFirstName(request.firstName()); employee.setLastName(request.lastName()); employee.setPhone(request.phone());
        return employee(employees.save(employee));
    }

    @Transactional(readOnly = true)
    public List<UserDtos.EmployeeResponse> searchEmployees(UUID organizationId, String search, Authentication caller) {
        if (hasRole(caller, "ADMIN")) {
            return employees.searchByOrganization(organizationId, search == null ? "" : search).stream().map(this::employee).toList();
        }
        if (!hasRole(caller, "MANAGER") && !hasRole(caller, "ORGANIZATION_ADMIN")) throw forbidden();
        Employee callerEmployee = employees.findByAuthUserIdAndDeletedFalse(callerId(caller)).orElseThrow(() -> forbidden());
        if (!callerEmployee.getOrganization().getId().equals(organizationId)) throw forbidden();
        return employees.searchByOrganization(organizationId, search == null ? "" : search).stream().map(this::employee).toList();
    }

    @Transactional
    public UserDtos.DepartmentResponse createDepartment(UUID organizationId, UserDtos.DepartmentRequest request, Authentication caller) {
        requireOrganizationAdmin(caller, organizationId); Organization organization = organization(organizationId);
        if (departments.existsByOrganizationIdAndNameIgnoreCaseAndDeletedFalse(organizationId, request.name()))
            throw conflict("Department already exists");
        Department department = new Department(); department.setOrganization(organization); department.setName(request.name()); department.setDescription(request.description());
        return department(departments.save(department));
    }

    @Transactional
    public UserDtos.TeamResponse createTeam(UUID departmentId, UserDtos.TeamRequest request, Authentication caller) {
        Department department = departments.findById(departmentId).orElseThrow(() -> notFound("Department not found"));
        requireOrganizationAdmin(caller, department.getOrganization().getId());
        if (teams.existsByDepartmentIdAndNameIgnoreCaseAndDeletedFalse(departmentId, request.name())) throw conflict("Team already exists");
        Team team = new Team(); team.setOrganization(department.getOrganization()); team.setDepartment(department); team.setName(request.name()); team.setDescription(request.description());
        return team(teams.save(team));
    }

    @Transactional
    public void assignDepartment(UUID employeeId, UUID departmentId, Authentication caller) {
        Employee employee = employee(employeeId); requireOrganizationAdmin(caller, employee.getOrganization().getId());
        Department department = departments.findById(departmentId).orElseThrow(() -> notFound("Department not found"));
        sameOrganization(employee.getOrganization(), department.getOrganization());
        if (employeeDepartments.existsByEmployeeIdAndDepartmentIdAndDeletedFalse(employeeId, departmentId)) throw conflict("Employee is already assigned");
        EmployeeDepartment assignment = new EmployeeDepartment(); assignment.setEmployee(employee); assignment.setDepartment(department); employeeDepartments.save(assignment);
    }

    @Transactional
    public void assignTeam(UUID employeeId, UUID teamId, Authentication caller) {
        Employee employee = employee(employeeId); requireOrganizationAdmin(caller, employee.getOrganization().getId());
        Team team = teams.findById(teamId).orElseThrow(() -> notFound("Team not found"));
        sameOrganization(employee.getOrganization(), team.getOrganization());
        if (employeeTeams.existsByEmployeeIdAndTeamIdAndDeletedFalse(employeeId, teamId)) throw conflict("Employee is already assigned");
        EmployeeTeam assignment = new EmployeeTeam(); assignment.setEmployee(employee); assignment.setTeam(team); employeeTeams.save(assignment);
    }

    private void requireVisible(Authentication caller, Employee employee) {
        UUID callerId = callerId(caller);
        if (callerId.equals(employee.getAuthUserId())) return;
        if (hasRole(caller, "ADMIN") || hasRole(caller, "MANAGER")) {
            Employee callerEmployee = employees.findByAuthUserIdAndDeletedFalse(callerId).orElseThrow(() -> forbidden());
            sameOrganization(callerEmployee.getOrganization(), employee.getOrganization()); return;
        }
        throw forbidden();
    }
    private void requireOrganizationAdmin(Authentication caller, UUID organizationId) {
        if (hasRole(caller, "ADMIN")) return;
        if (!hasRole(caller, "ORGANIZATION_ADMIN")) throw forbidden();
        Employee callerEmployee = employees.findByAuthUserIdAndDeletedFalse(callerId(caller)).orElseThrow(() -> forbidden());
        if (!callerEmployee.getOrganization().getId().equals(organizationId)) throw forbidden();
    }
    private void requireRole(Authentication caller, String role) { if (!hasRole(caller, role)) throw forbidden(); }
    private boolean hasRole(Authentication caller, String role) {
        return caller != null && caller.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .anyMatch(value -> value.equals("ROLE_" + role) || value.equals(role));
    }
    private UUID callerId(Authentication caller) { if (caller == null || !(caller.getPrincipal() instanceof UUID id)) throw unauthorized(); return id; }
    private Organization organization(UUID id) { return organizations.findById(id).orElseThrow(() -> notFound("Organization not found")); }
    private Employee employee(UUID id) { return employees.findById(id).orElseThrow(() -> notFound("Employee not found")); }
    private void sameOrganization(Organization first, Organization second) { if (!first.getId().equals(second.getId())) throw forbidden(); }
    private UserDtos.OrganizationResponse organization(Organization value) { return new UserDtos.OrganizationResponse(value.getId(), value.getName(), value.getCode()); }
    private UserDtos.EmployeeResponse employee(Employee value) { return new UserDtos.EmployeeResponse(value.getId(), value.getOrganization().getId(), value.getAuthUserId(), value.getEmployeeCode(), value.getFirstName(), value.getLastName(), value.getEmail(), value.getPhone(), value.getStatus().name()); }
    private UserDtos.DepartmentResponse department(Department value) { return new UserDtos.DepartmentResponse(value.getId(), value.getOrganization().getId(), value.getName(), value.getDescription()); }
    private UserDtos.TeamResponse team(Team value) { return new UserDtos.TeamResponse(value.getId(), value.getOrganization().getId(), value.getDepartment().getId(), value.getName(), value.getDescription()); }
    private ApiException forbidden() { return new ApiException("FORBIDDEN", "Access denied", HttpStatus.FORBIDDEN); }
    private ApiException unauthorized() { return new ApiException("UNAUTHORIZED", "Authentication is required", HttpStatus.UNAUTHORIZED); }
    private ApiException notFound(String message) { return new ApiException("NOT_FOUND", message, HttpStatus.NOT_FOUND); }
    private ApiException conflict(String message) { return new ApiException("CONFLICT", message, HttpStatus.CONFLICT); }
}
