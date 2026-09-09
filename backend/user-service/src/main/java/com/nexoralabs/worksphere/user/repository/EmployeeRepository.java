package com.nexoralabs.worksphere.user.repository;

import com.nexoralabs.worksphere.user.domain.Employee;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByAuthUserIdAndDeletedFalse(UUID authUserId);
    boolean existsByOrganizationIdAndEmployeeCodeIgnoreCaseAndDeletedFalse(UUID organizationId, String employeeCode);
    boolean existsByOrganizationIdAndEmailIgnoreCaseAndDeletedFalse(UUID organizationId, String email);
    @Query("select e from Employee e where e.organization.id = :organizationId and e.deleted = false "
            + "and (:search = '' or lower(e.firstName) like lower(concat('%', :search, '%')) "
            + "or lower(e.lastName) like lower(concat('%', :search, '%')) "
            + "or lower(e.email) like lower(concat('%', :search, '%')))")
    List<Employee> searchByOrganization(@Param("organizationId") UUID organizationId, @Param("search") String search);
}
