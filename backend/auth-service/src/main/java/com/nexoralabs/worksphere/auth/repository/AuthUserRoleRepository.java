package com.nexoralabs.worksphere.auth.repository;

import com.nexoralabs.worksphere.auth.domain.AuthUserRole;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthUserRoleRepository extends JpaRepository<AuthUserRole, UUID> {
    @Query("select ur.role.roleName from AuthUserRole ur where ur.user.id = :userId and ur.role.deleted = false")
    List<String> findRoleNames(UUID userId);
}