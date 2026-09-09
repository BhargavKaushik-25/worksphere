package com.nexoralabs.worksphere.project.repository;

import com.nexoralabs.worksphere.project.domain.Project;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Optional<Project> findByIdAndDeletedFalse(UUID id);
    boolean existsByOrganizationIdAndKeyIgnoreCaseAndDeletedFalse(UUID organizationId, String key);
    @Query("select p from Project p where p.organizationId = :organizationId and p.deleted = false "
            + "and (:status is null or p.status = :status) and (:search = '' or lower(p.name) like lower(concat('%', :search, '%')) "
            + "or lower(p.key) like lower(concat('%', :search, '%')))")
    List<Project> filter(@Param("organizationId") UUID organizationId, @Param("status") Project.Status status, @Param("search") String search);
}
