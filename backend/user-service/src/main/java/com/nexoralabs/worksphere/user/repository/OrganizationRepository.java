package com.nexoralabs.worksphere.user.repository;

import com.nexoralabs.worksphere.user.domain.Organization;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    Optional<Organization> findByCodeIgnoreCaseAndDeletedFalse(String code);
}
