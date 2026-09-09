package com.nexoralabs.worksphere.project.api;

import com.nexoralabs.worksphere.project.domain.Project;
import com.nexoralabs.worksphere.project.service.ProjectService;
import com.nexoralabs.worksphere.shared.api.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService service;
    public ProjectController(ProjectService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProjectDtos.ProjectResponse> create(@Valid @RequestBody ProjectDtos.CreateProjectRequest request, Authentication caller) {
        return ApiResponse.success("Project created", service.create(request, caller));
    }

    @GetMapping("/{projectId}")
    public ApiResponse<ProjectDtos.ProjectResponse> get(@PathVariable UUID projectId, Authentication caller) {
        return ApiResponse.success("Project retrieved", service.get(projectId, caller));
    }

    @GetMapping
    public ApiResponse<List<ProjectDtos.ProjectResponse>> filter(@RequestParam UUID organizationId,
            @RequestParam(required = false) Project.Status status, @RequestParam(defaultValue = "") String search,
            Authentication caller) {
        return ApiResponse.success("Projects retrieved", service.filter(organizationId, status, search, caller));
    }

    @PatchMapping("/{projectId}/status")
    public ApiResponse<ProjectDtos.ProjectResponse> updateStatus(@PathVariable UUID projectId,
            @Valid @RequestBody ProjectDtos.UpdateStatusRequest request, Authentication caller) {
        return ApiResponse.success("Project status updated", service.updateStatus(projectId, request, caller));
    }

    @PatchMapping("/{projectId}/manager/{managerId}")
    public ApiResponse<ProjectDtos.ProjectResponse> assignManager(@PathVariable UUID projectId,
            @PathVariable UUID managerId, Authentication caller) {
        return ApiResponse.success("Project manager assigned", service.assignManager(projectId, managerId, caller));
    }

    @PostMapping("/{projectId}/members")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProjectDtos.ProjectMemberResponse> addMember(@PathVariable UUID projectId,
            @Valid @RequestBody ProjectDtos.MemberRequest request, Authentication caller) {
        return ApiResponse.success("Project member assigned", service.addMember(projectId, request, caller));
    }
}
