# ADR-002: Database per Service

## Status

Accepted

---

## Date

July 2026

---

## Context

The WorkSphere platform follows a microservices architecture where each
service represents a distinct business domain.

Sharing a single database across multiple services would introduce
tight coupling, reduce service autonomy, complicate deployments, and
make schema evolution difficult.

To maintain independent ownership and scalability, database boundaries
must align with service boundaries.

---

## Decision

Each microservice shall own its own database.

No service shall directly access another service's database.

All cross-service interactions shall occur through published APIs or
approved asynchronous messaging mechanisms.

Each service shall independently manage:

- Database schema
- Migrations
- Indexes
- Backups
- Performance optimization
- Data lifecycle

Example database ownership:

| Service | Database |
|---------|----------|
| Authentication Service | auth_db |
| User Service | user_db |
| Organization Service | organization_db |
| Workspace Service | workspace_db |
| Project Service | project_db |
| Task Service | task_db |
| Document Service | document_db |
| Notification Service | notification_db |
| Analytics Service | analytics_db |
| Audit Service | audit_db |

---

## Consequences

### Positive

- Independent schema evolution
- Strong service ownership
- Independent deployments
- Better fault isolation
- Improved scalability
- Reduced coupling
- Easier maintenance

### Negative

- Data duplication between services
- Distributed transactions become more complex
- Additional API communication
- Eventual consistency where applicable

---

## Alternatives Considered

### Shared Database

Rejected due to increased coupling, reduced autonomy, and deployment
constraints.

### Shared Schema with Separate Tables

Rejected because schema ownership would remain distributed and difficult
to govern consistently.

---

## References

- Database Design
- System Architecture
- API Design
- ADR-001: Adopt Microservices Architecture

---

## Decision Owner

Software Architecture Team

---

# End of ADR-002