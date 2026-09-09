package com.nexoralabs.worksphere.user.api;

import com.nexoralabs.worksphere.shared.api.ApiResponse;
import com.nexoralabs.worksphere.user.service.UserOrganizationService;
import jakarta.validation.Valid;
import java.util.UUID;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserOrganizationController {
    private final UserOrganizationService service;
    public UserOrganizationController(UserOrganizationService service) { this.service = service; }

    @PostMapping("/organizations")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserDtos.OrganizationResponse> createOrganization(@Valid @RequestBody UserDtos.OrganizationRequest request, Authentication caller) {
        return ApiResponse.success("Organization created", service.createOrganization(request, caller));
    }
    @PostMapping("/organizations/{organizationId}/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserDtos.EmployeeResponse> createEmployee(@PathVariable UUID organizationId, @Valid @RequestBody UserDtos.EmployeeRequest request, Authentication caller) {
        return ApiResponse.success("Employee created", service.createEmployee(organizationId, request, caller));
    }
    @GetMapping("/employees/{employeeId}")
    public ApiResponse<UserDtos.EmployeeResponse> getEmployee(@PathVariable UUID employeeId, Authentication caller) {
        return ApiResponse.success("Employee retrieved", service.getEmployee(employeeId, caller));
    }
    @GetMapping("/employees/me")
    public ApiResponse<UserDtos.EmployeeResponse> getMyProfile(Authentication caller) { return ApiResponse.success("Profile retrieved", service.getMyProfile(caller)); }
    @PatchMapping("/employees/me")
    public ApiResponse<UserDtos.EmployeeResponse> updateMyProfile(@Valid @RequestBody UserDtos.ProfileUpdateRequest request, Authentication caller) { return ApiResponse.success("Profile updated", service.updateMyProfile(request, caller)); }
    @GetMapping("/organizations/{organizationId}/employees")
    public ApiResponse<List<UserDtos.EmployeeResponse>> searchEmployees(@PathVariable UUID organizationId, @RequestParam(defaultValue = "") String search, Authentication caller) {
        return ApiResponse.success("Employees retrieved", service.searchEmployees(organizationId, search, caller));
    }
    @PostMapping("/organizations/{organizationId}/departments")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserDtos.DepartmentResponse> createDepartment(@PathVariable UUID organizationId, @Valid @RequestBody UserDtos.DepartmentRequest request, Authentication caller) { return ApiResponse.success("Department created", service.createDepartment(organizationId, request, caller)); }
    @PostMapping("/departments/{departmentId}/teams")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserDtos.TeamResponse> createTeam(@PathVariable UUID departmentId, @Valid @RequestBody UserDtos.TeamRequest request, Authentication caller) { return ApiResponse.success("Team created", service.createTeam(departmentId, request, caller)); }
    @PostMapping("/employees/{employeeId}/departments/{departmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void assignDepartment(@PathVariable UUID employeeId, @PathVariable UUID departmentId, Authentication caller) { service.assignDepartment(employeeId, departmentId, caller); }
    @PostMapping("/employees/{employeeId}/teams/{teamId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void assignTeam(@PathVariable UUID employeeId, @PathVariable UUID teamId, Authentication caller) { service.assignTeam(employeeId, teamId, caller); }
}
