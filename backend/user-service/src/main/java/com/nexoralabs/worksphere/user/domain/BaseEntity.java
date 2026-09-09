package com.nexoralabs.worksphere.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {
    @Id @GeneratedValue protected UUID id;
    @Column(name = "created_at", nullable = false) protected Instant createdAt = Instant.now();
    @Column(name = "created_by") protected UUID createdBy;
    @Column(name = "updated_at", nullable = false) protected Instant updatedAt = Instant.now();
    @Column(name = "updated_by") protected UUID updatedBy;
    @Column(name = "deleted_at") protected Instant deletedAt;
    @Column(name = "deleted_by") protected UUID deletedBy;
    @Column(name = "is_deleted", nullable = false) protected boolean deleted;
    @Version protected long version;
    public UUID getId() { return id; }
    public boolean isDeleted() { return deleted; }
}
