package com.nexoralabs.worksphere.project.repository;

import com.nexoralabs.worksphere.project.domain.ProjectMember;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, UUID> {
    boolean existsByProjectIdAndMemberIdAndDeletedFalse(UUID projectId, UUID memberId);
    boolean existsByProjectIdAndMemberIdAndRoleAndDeletedFalse(UUID projectId, UUID memberId, ProjectMember.Role role);
    List<ProjectMember> findByProjectIdAndDeletedFalse(UUID projectId);
    List<ProjectMember> findByMemberIdAndDeletedFalse(UUID memberId);
}
