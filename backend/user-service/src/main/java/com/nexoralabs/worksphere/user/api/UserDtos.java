package com.nexoralabs.worksphere.user.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public final class UserDtos {
    private UserDtos() { }
    public record OrganizationRequest(@NotBlank @Size(max = 150) String name,
                                      @NotBlank @Size(max = 50) String code) { }
    public record EmployeeRequest(@NotNull UUID authUserId, @NotBlank @Size(max = 50) String employeeCode,
                                  @NotBlank @Size(max = 100) String firstName, @NotBlank @Size(max = 100) String lastName,
                                  @NotBlank @Email @Size(max = 255) String email, @Size(max = 20) String phone) { }
    public record DepartmentRequest(@NotBlank @Size(max = 100) String name, String description) { }
    public record TeamRequest(@NotBlank @Size(max = 100) String name, String description) { }
    public record ProfileUpdateRequest(@NotBlank @Size(max = 100) String firstName,
                                       @NotBlank @Size(max = 100) String lastName, @Size(max = 20) String phone) { }
    public record OrganizationResponse(UUID id, String name, String code) { }
    public record EmployeeResponse(UUID id, UUID organizationId, UUID authUserId, String employeeCode,
                                   String firstName, String lastName, String email, String phone, String status) { }
    public record DepartmentResponse(UUID id, UUID organizationId, String name, String description) { }
    public record TeamResponse(UUID id, UUID organizationId, UUID departmentId, String name, String description) { }
}
