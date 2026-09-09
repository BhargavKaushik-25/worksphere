package com.nexoralabs.worksphere.user.repository;

import com.nexoralabs.worksphere.user.domain.EmployeeDepartment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDepartmentRepository extends JpaRepository<EmployeeDepartment, UUID> {
    boolean existsByEmployeeIdAndDepartmentIdAndDeletedFalse(UUID employeeId, UUID departmentId);
}
