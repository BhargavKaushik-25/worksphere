# ADR-008: PostgreSQL as the Primary Database

## Status

Accepted

---

## Date

July 2026

---

## Context

The WorkSphere platform requires a reliable, scalable, and
enterprise-grade relational database to support multiple business
domains such as authentication, user management, workspaces, projects,
tasks, documents, notifications, analytics, and auditing.

The selected database must provide strong ACID compliance, high
performance, extensibility, and broad ecosystem support while
integrating well with the Spring Boot technology stack.

---

## Decision

The platform shall use PostgreSQL as the primary relational database for
all core microservices.

Each microservice shall maintain its own dedicated PostgreSQL database,
following the Database per Service architectural pattern.

PostgreSQL shall provide:

- ACID-compliant transactions
- Referential integrity
- Advanced indexing
- JSON/JSONB support
- Full-text search capabilities
- High availability support
- Backup and recovery mechanisms
- Mature tooling and ecosystem

Database schema changes shall be managed through version-controlled
migration scripts.

---

## Consequences

### Positive

- Proven enterprise reliability
- Strong SQL standards compliance
- Excellent performance
- Rich indexing capabilities
- Native JSON support
- Broad community and commercial support
- Excellent compatibility with Spring Boot and Hibernate

### Negative

- Operational overhead compared to embedded databases
- Database tuning required for optimal performance
- Backup and replication planning required
- Infrastructure resources increase with multiple service databases

---

## Alternatives Considered

### MySQL

Rejected because PostgreSQL offers stronger advanced SQL features,
better JSON capabilities, and greater extensibility for the project's
long-term requirements.

### MongoDB

Rejected because the platform's core business domains benefit from a
relational data model with strong transactional consistency.

### H2 Database

Rejected for production because it is intended primarily for
development, testing, and lightweight use cases.

---

## References

- Database Design
- System Architecture
- Deployment Architecture
- ADR-001: Adopt Microservices Architecture
- ADR-002: Database per Service

---

## Decision Owner

Software Architecture Team

---

# End of ADR-008