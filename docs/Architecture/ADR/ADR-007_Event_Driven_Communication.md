# ADR-007: Event-Driven Communication

## Status

Accepted

---

## Date

July 2026

---

## Context

While REST APIs are appropriate for synchronous request-response
communication, certain business processes in the WorkSphere platform do
not require an immediate response.

Examples include sending notifications, generating audit logs,
processing analytics events, and other background activities.

Implementing these workflows synchronously would increase service
coupling, response times, and the risk of cascading failures.

---

## Decision

The platform shall adopt event-driven communication for asynchronous
business workflows.

Services shall publish domain events when significant business actions
occur, and interested services shall subscribe to the events they
require.

Examples include:

- User Registered
- Workspace Created
- Project Created
- Task Assigned
- Task Completed
- Document Uploaded
- Notification Requested
- Audit Event Generated

Apache ActiveMQ shall be used as the initial message broker for
asynchronous communication.

Event messages shall:

- Be immutable after publication
- Include a unique event identifier
- Contain timestamps
- Follow standardized event schemas
- Support retry mechanisms where appropriate
- Be processed independently by consumers

REST APIs shall remain the default mechanism for synchronous
communication.

---

## Consequences

### Positive

- Loose coupling between services
- Improved scalability
- Faster API response times
- Better fault isolation
- Improved system resilience
- Independent background processing
- Easier future system expansion

### Negative

- Increased architectural complexity
- Eventual consistency
- More difficult debugging
- Message ordering considerations
- Broker infrastructure maintenance

---

## Alternatives Considered

### REST APIs Only

Rejected because synchronous communication is inefficient for background
processing and increases service dependencies.

### Shared Database Triggers

Rejected because they violate service ownership and create tight
coupling between services.

---

## References

- System Architecture
- API Design
- Deployment Architecture
- ADR-001: Adopt Microservices Architecture
- ADR-003: REST API Communication Between Services
- ADR-006: Service Discovery

---

## Decision Owner

Software Architecture Team

---

# End of ADR-007