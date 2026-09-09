package com.nexoralabs.worksphere.user.repository;

import com.nexoralabs.worksphere.user.domain.EmployeeTeam;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeTeamRepository extends JpaRepository<EmployeeTeam, UUID> {
    boolean existsByEmployeeIdAndTeamIdAndDeletedFalse(UUID employeeId, UUID teamId);
}
