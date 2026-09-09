package com.nexoralabs.worksphere.user.repository;

import com.nexoralabs.worksphere.user.domain.Team;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    boolean existsByDepartmentIdAndNameIgnoreCaseAndDeletedFalse(UUID departmentId, String name);
}
