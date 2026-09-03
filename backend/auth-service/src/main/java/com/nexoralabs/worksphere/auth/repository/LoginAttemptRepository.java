package com.nexoralabs.worksphere.auth.repository;

import com.nexoralabs.worksphere.auth.domain.LoginAttempt;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, UUID> {
    long countByUserIdAndSuccessfulFalseAndAttemptedAtAfter(UUID userId, Instant after);
}