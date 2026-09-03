package com.nexoralabs.worksphere.auth.repository;

import com.nexoralabs.worksphere.auth.domain.Role;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, UUID> { }