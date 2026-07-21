# Database Design

## Document Information

+----------------------+--------------------------------------------------+
| Attribute            | Value                                            |
+----------------------+--------------------------------------------------+
| Document Name        | Database Design                                  |
| Project              | WorkSphere                                       |
| Version              | 1.0                                              |
| Status               | Approved                                         |
| Owner                | Bhargav Kaushik                                  |
| Prepared By          | Bhargav Kaushik                                  |
| Last Updated         | July 2026                                        |
+----------------------+--------------------------------------------------+

---

# Table of Contents

1. Purpose
2. Scope
3. Database Design Principles
4. Database Architecture
5. Database per Service Strategy
6. Database Technology Stack
7. Database Standards
8. References
9. Version History

---

# 1. Purpose

This document defines the complete database architecture and design standards
for the WorkSphere platform.

It establishes the principles, conventions, storage strategy, and service
ownership model that shall be followed throughout the lifecycle of the
application.

The objective is to ensure consistency, scalability, maintainability,
performance, and data integrity across all database implementations.

This document serves as the primary reference for:

- Database Architects
- Backend Developers
- DevOps Engineers
- Solution Architects
- QA Engineers

---

# 2. Scope

This document covers:

- Database Architecture
- Database Ownership
- Database Standards
- Database Naming Conventions
- Schema Organization
- Service Databases
- Relationships
- Constraints
- Indexing
- Transactions
- Audit Strategy
- Backup Strategy
- Migration Strategy

The detailed table structures and Entity Relationship Diagrams (ERDs) are
documented separately within subsequent sections and related documents.

---

# 3. Database Design Principles

The WorkSphere platform follows modern cloud-native database design
principles to ensure scalability, loose coupling, and long-term
maintainability.

---

## DBP-001 Service Ownership

Each microservice shall exclusively own its database.

No other service shall directly read from or write to another service's
database.

Inter-service communication must occur through REST APIs or asynchronous
events.

---

## DBP-002 Loose Coupling

Database schemas shall remain independent.

Business services communicate through well-defined interfaces rather than
shared database structures.

---

## DBP-003 High Cohesion

Each database shall contain only data relevant to the business capability
owned by its corresponding microservice.

Example:

- Authentication Service stores authentication data.
- Project Service stores project information.
- Task Service stores task-related information.

---

## DBP-004 Normalization

Relational data shall be normalized to at least Third Normal Form (3NF)
unless denormalization is justified for performance reasons.

---

## DBP-005 Data Integrity

Data integrity shall be enforced through:

- Primary Keys
- Foreign Keys (within the same service database)
- Unique Constraints
- Check Constraints
- NOT NULL Constraints

---

## DBP-006 Security

Sensitive information shall never be stored in plain text.

Examples include:

- Passwords
- API Secrets
- Refresh Tokens
- Security Credentials

Such data shall always be encrypted or securely hashed using approved
cryptographic algorithms.

---

## DBP-007 Scalability

The database architecture shall support:

- Horizontal scaling at the service level
- Independent database optimization
- Independent backups
- Independent maintenance windows

---

## DBP-008 Performance

Database design shall prioritize:

- Efficient indexing
- Optimized queries
- Minimal redundancy
- Connection pooling
- Appropriate caching

---

## DBP-009 Availability

Database systems shall support:

- Automated backups
- Point-in-Time Recovery (PITR)
- Disaster Recovery
- High Availability deployment

---

## DBP-010 Auditability

Critical business operations shall be traceable.

Audit information shall support:

- User accountability
- Compliance requirements
- Security investigations
- Operational monitoring

---

# 4. Database Architecture

WorkSphere adopts a **Database per Service** architecture aligned with the
overall microservices architecture.

Each service owns its schema, data model, and persistence layer.

Services communicate through APIs and events rather than direct database
access.

This approach provides:

- Service Independence
- Independent Deployment
- Independent Scaling
- Fault Isolation
- Better Security
- Technology Flexibility

---

## 4.1 High-Level Database Architecture

```text
                    +----------------------+
                    |     API Gateway      |
                    +----------+-----------+
                               |
         ---------------------------------------------------------
         |        |        |        |        |        |          |
         ▼        ▼        ▼        ▼        ▼        ▼          ▼

+-------------+ +-------------+ +-------------+ +-------------+
| Auth DB     | | User DB     | | Org DB      | | Workspace DB|
+-------------+ +-------------+ +-------------+ +-------------+

+-------------+ +-------------+ +-------------+ +-------------+
| Project DB  | | Task DB     | | Document DB | | Notify DB   |
+-------------+ +-------------+ +-------------+ +-------------+

+-------------+ +-------------+
| Analytics DB| | Audit DB    |
+-------------+ +-------------+
```

---

## 4.2 Database Characteristics

The WorkSphere database architecture provides:

- Independent ownership
- Independent schema evolution
- Independent scaling
- Independent deployment
- Fault isolation
- Simplified maintenance
- Technology flexibility
- Improved security

---

# 5. Database per Service Strategy

The platform follows the Database per Service pattern.

Each microservice maintains complete ownership of its persistent data.

---

## Strategy Objectives

The strategy is designed to achieve:

- Strong service boundaries
- Loose coupling
- Independent deployments
- Independent scaling
- Better maintainability
- Simplified ownership

---

## Service Database Mapping

| Microservice | Database |
|--------------|----------|
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

## Database Ownership Rules

The following rules shall always apply:

1. Every database belongs to exactly one microservice.

2. Direct database access across services is prohibited.

3. Data sharing shall occur only through:
   - REST APIs
   - Event-driven messaging
   - Published business events

4. Cross-service joins shall never be performed.

5. Distributed transactions shall be avoided wherever possible.

6. Eventual consistency shall be preferred for inter-service workflows.

---

## Benefits

The Database per Service strategy provides:

- Better scalability
- Independent schema evolution
- Independent releases
- Reduced coupling
- Improved fault isolation
- Easier testing
- Better maintainability

---

# 6. Database Technology Stack

| Category | Technology |
|----------|------------|
| Database Engine | PostgreSQL 17 |
| ORM Framework | Spring Data JPA |
| Migration Tool | Flyway |
| Connection Pool | HikariCP |
| SQL Dialect | PostgreSQL |
| Object Storage | MinIO |
| Cache | Redis |

---

# 7. Database Standards

The following standards apply to every database used within WorkSphere.

| Standard ID | Description |
|--------------|-------------|
| DBS-001 | Every table shall have a Primary Key. |
| DBS-002 | UUID shall be preferred for entity identifiers. |
| DBS-003 | Every table shall include audit columns. |
| DBS-004 | Foreign Keys are allowed only within the same service database. |
| DBS-005 | Every frequently searched column shall be indexed. |
| DBS-006 | Sensitive data shall be encrypted or hashed. |
| DBS-007 | Schema changes shall be managed exclusively through Flyway migrations. |
| DBS-008 | Soft Delete shall be preferred over physical deletion where appropriate. |
| DBS-009 | All timestamps shall be stored in UTC. |
| DBS-010 | Naming conventions shall remain consistent across all databases. |

---

# 8. References

This document should be read together with:

- System Architecture
- Architecture Decision Records
- Technology Stack & Standards Guide
- Security Architecture
- ER Diagram
- API Design

---

# Version History

+---------+------------+----------------------------------------------------------+-------------------+
| Version | Date       | Description                                              | Author            |
+---------+------------+----------------------------------------------------------+-------------------+
| 1.0     | July 2026  | Initial release of Database Design document              | Bhargav Kaushik   |
+---------+------------+----------------------------------------------------------+-------------------+

---

# End of Part 1

---

# 9. Database Naming Conventions

To ensure consistency, maintainability, and readability across all
microservices, WorkSphere follows standardized database naming conventions.

These conventions apply to every database, schema, table, column,
constraint, index, sequence, and database object.

---

## 9.1 Database Naming Standards

+--------------------------+---------------------------------------------+
| Rule                     | Standard                                    |
+--------------------------+---------------------------------------------+
| Character Case           | lowercase                                   |
| Word Separator           | underscore (_)                              |
| Naming Style             | snake_case                                  |
| Database Suffix          | _db                                         |
+--------------------------+---------------------------------------------+

### Examples

+--------------------------+-----------------------------+
| Microservice             | Database                    |
+--------------------------+-----------------------------+
| Authentication Service   | auth_db                     |
| User Service             | user_db                     |
| Organization Service     | organization_db             |
| Workspace Service        | workspace_db                |
| Project Service          | project_db                  |
| Task Service             | task_db                     |
| Document Service         | document_db                 |
| Notification Service     | notification_db             |
| Analytics Service        | analytics_db                |
| Audit Service            | audit_db                    |
+--------------------------+-----------------------------+

---

## 9.2 Table Naming Standards

All database tables shall use singular nouns.

+--------------------------+-----------------------------+
| Rule                     | Standard                    |
+--------------------------+-----------------------------+
| Character Case           | lowercase                   |
| Naming Style             | snake_case                  |
| Table Names              | Singular                    |
+--------------------------+-----------------------------+

### Examples

+--------------------------+-----------------------------+
| Business Entity          | Table Name                  |
+--------------------------+-----------------------------+
| User                     | user                        |
| Role                     | role                        |
| Permission               | permission                  |
| Workspace                | workspace                   |
| Project                  | project                     |
| Task                     | task                        |
| Notification             | notification                |
| Audit Log                | audit_log                   |
+--------------------------+-----------------------------+

---

## 9.3 Column Naming Standards

+--------------------------+-----------------------------+
| Rule                     | Standard                    |
+--------------------------+-----------------------------+
| Character Case           | lowercase                   |
| Naming Style             | snake_case                  |
| Business Meaning         | Descriptive                 |
+--------------------------+-----------------------------+

### Examples

+--------------------------+-----------------------------+
| Business Field           | Database Column             |
+--------------------------+-----------------------------+
| User ID                  | user_id                     |
| Organization ID          | organization_id             |
| First Name               | first_name                  |
| Last Name                | last_name                   |
| Created At               | created_at                  |
| Updated At               | updated_at                  |
| Deleted At               | deleted_at                  |
| Is Deleted               | is_deleted                  |
+--------------------------+-----------------------------+

---

# 10. Common Entity Standard

All business entities within WorkSphere shall inherit a common logical
Base Entity model.

This ensures consistency across every microservice.

---

## Standard Base Entity

+--------------------------+-----------------------------+
| Column                   | Description                 |
+--------------------------+-----------------------------+
| id                       | UUID Primary Key            |
| created_at               | Record Creation Time        |
| created_by               | Created By User             |
| updated_at               | Last Modification Time      |
| updated_by               | Last Modified By            |
| deleted_at               | Soft Delete Timestamp       |
| deleted_by               | Deleted By User             |
| is_deleted               | Soft Delete Flag            |
| version                  | Optimistic Lock Version     |
+--------------------------+-----------------------------+

---

## Design Decision

During implementation this model will become a reusable Spring Boot
`@MappedSuperclass` inherited by all JPA entities.

Benefits include:

- Consistent auditing
- Reduced code duplication
- Simplified maintenance
- Standard lifecycle management

---

# 11. UUID Strategy

WorkSphere adopts UUIDs as the primary identifier for every business
entity.

---

## UUID Standard

+--------------------------+-----------------------------+
| Attribute                | Value                       |
+--------------------------+-----------------------------+
| Identifier Type          | UUID                        |
| Preferred Version        | UUID Version 4              |
| Generated By             | Application                 |
+--------------------------+-----------------------------+

---

## Rationale

UUIDs provide:

- Global uniqueness
- Distributed identifier generation
- Better support for microservices
- Easier data synchronization
- No dependency on database-generated IDs

---

# 12. Multi-Tenancy Strategy

## Selected Approach

Shared Database with Shared Schema.

Each organization is logically isolated using an `organization_id`
column.

---

### Illustration

```text
Organization
     │
     ├───────────────┐
     │               │
     ▼               ▼

User            Workspace

     │               │

     ▼               ▼

Project -------> Task
```

Every business entity belongs to one organization.

---

## Organization Ownership

+--------------------------+-----------------------------+
| Entity                   | Organization Aware          |
+--------------------------+-----------------------------+
| User                     | Yes                         |
| Workspace                | Yes                         |
| Project                  | Yes                         |
| Task                     | Yes                         |
| Document                 | Yes                         |
| Notification             | Yes                         |
+--------------------------+-----------------------------+

---

## Benefits

- Lower operational cost
- Simpler deployment
- Better scalability
- Easier maintenance
- Enterprise-proven architecture

---

# 13. Cross-Service Data Ownership

Every microservice owns its own data.

Direct database access across services is strictly prohibited.

---

## Communication Rules

+--------------------------+-----------------------------+
| Rule                     | Status                      |
+--------------------------+-----------------------------+
| REST API                 | ✔ Allowed                   |
| Event Messaging          | ✔ Allowed                   |
| Shared Database Access   | ✘ Not Allowed               |
| Cross-Service JOIN       | ✘ Not Allowed               |
| Cross-Service FK         | ✘ Not Allowed               |
+--------------------------+-----------------------------+

---

## Reference Strategy

Services shall reference external entities using UUIDs only.

Example:

```text
Task Service

task
--------
id
project_id
assignee_id
reporter_id
```

User information shall always be retrieved from the User Service.

---

# 14. Data Type Standards

+--------------------------+-----------------------------+
| Business Data            | PostgreSQL Type             |
+--------------------------+-----------------------------+
| Identifier               | UUID                        |
| Name                     | VARCHAR(100)                |
| Email                    | VARCHAR(255)                |
| Phone                    | VARCHAR(20)                 |
| Description              | TEXT                        |
| Status                   | VARCHAR(30)                 |
| Boolean                  | BOOLEAN                     |
| Integer                  | INTEGER                     |
| Decimal                  | NUMERIC                     |
| Date                     | DATE                        |
| Timestamp                | TIMESTAMP WITH TIME ZONE    |
| JSON                     | JSONB                       |
+--------------------------+-----------------------------+

---

# 15. Soft Delete Strategy

WorkSphere adopts Soft Delete for all business entities.

Records are marked as deleted instead of being physically removed.

---

## Required Columns

+--------------------------+-----------------------------+
| Column                   | Purpose                     |
+--------------------------+-----------------------------+
| is_deleted               | Logical Delete Flag         |
| deleted_at               | Deletion Timestamp          |
| deleted_by               | User Performing Delete      |
+--------------------------+-----------------------------+

---

## Benefits

- Data recovery
- Audit compliance
- Historical reporting
- Business traceability
- Safer operations

---

# 16. Design Standards

+-----------+--------------------------------------------------------------+
| ID        | Standard                                                     |
+-----------+--------------------------------------------------------------+
| DBS-011   | Every entity shall inherit the Base Entity structure.         |
| DBS-012   | UUID shall be used as the primary identifier.                |
| DBS-013   | Every business entity shall contain organization_id.         |
| DBS-014   | Soft Delete shall be used instead of physical deletion.      |
| DBS-015   | Cross-service foreign keys are prohibited.                   |
| DBS-016   | Cross-service communication shall occur via APIs or events.  |
| DBS-017   | All timestamps shall be stored in UTC.                       |
| DBS-018   | All audit columns are mandatory.                             |
+-----------+--------------------------------------------------------------+

---

# End of Part 2

---

# 17. Business Domain Database Design

## Overview

The WorkSphere platform follows a Domain-Driven Design (DDD) approach,
where each microservice owns a dedicated database aligned with a specific
business capability.

Each domain is independently deployable, independently scalable, and
independently maintainable.

The following sections describe the database design for each domain.

---

# 17.1 Authentication Domain

## Purpose

The Authentication Domain is responsible for user authentication,
authorization, identity verification, session management, and security.

It acts as the first security boundary for every request entering the
WorkSphere platform.

---

## Database Information

+--------------------------+--------------------------------------+
| Attribute                | Value                                |
+--------------------------+--------------------------------------+
| Database Name            | auth_db                              |
| Owning Service           | Authentication Service               |
| Primary Responsibility   | Authentication & Authorization       |
| Database Engine          | PostgreSQL                           |
| Cache                    | Redis                                |
+--------------------------+--------------------------------------+

---

## Business Responsibilities

The Authentication Domain is responsible for:

- User Login
- User Logout
- Password Management
- Refresh Token Management
- Role Assignment
- Permission Management
- Authentication Audit
- Session Validation
- Password History
- Account Locking

---

## Core Tables

+--------------------------+--------------------------------------+
| Table                    | Purpose                              |
+--------------------------+--------------------------------------+
| user                      | User credentials                    |
| role                      | System roles                        |
| permission                | Available permissions               |
| role_permission           | Role-permission mapping             |
| user_role                 | User-role mapping                   |
| refresh_token             | Active refresh tokens               |
| password_history          | Password history                    |
| login_attempt             | Failed login attempts               |
+--------------------------+--------------------------------------+

---

## Table Relationships

```text
user
 │
 ├────────────── user_role ────────────── role
 │                                           │
 │                                           │
 │                               role_permission
 │                                           │
 │                                           │
 └──────────────────────────────────── permission


user
 │
 ├──────── refresh_token

user
 │
 ├──────── password_history

user
 │
 └──────── login_attempt
```

---

## Authentication Business Rules

+-----------+--------------------------------------------------------------+
| Rule ID   | Rule                                                         |
+-----------+--------------------------------------------------------------+
| AUTH-DB-001 | One user may have multiple roles.                          |
| AUTH-DB-002 | One role may contain multiple permissions.                 |
| AUTH-DB-003 | Passwords shall never be stored in plain text.             |
| AUTH-DB-004 | Refresh tokens shall have expiration timestamps.           |
| AUTH-DB-005 | Password reuse shall be prevented.                         |
| AUTH-DB-006 | Failed login attempts shall be recorded.                   |
| AUTH-DB-007 | Account lock policy shall be configurable.                 |
+-----------+--------------------------------------------------------------+

---

## Index Recommendations

+--------------------------+--------------------------------------+
| Table                    | Recommended Index                    |
+--------------------------+--------------------------------------+
| user                     | email                                |
| refresh_token            | token                                |
| login_attempt            | user_id                              |
| role                     | role_name                            |
| permission               | permission_name                      |
+--------------------------+--------------------------------------+

---

## Security Considerations

- Passwords shall be hashed using BCrypt.
- JWT Refresh Tokens shall be securely stored.
- Password history shall prevent reuse.
- Authentication data shall never be exposed through public APIs.
- Sensitive columns shall be encrypted where applicable.

---

# 17.2 User Domain

## Purpose

The User Domain manages employee profile information and user preferences.

Unlike the Authentication Domain, this database contains business profile
information rather than authentication credentials.

---

## Database Information

+--------------------------+--------------------------------------+
| Attribute                | Value                                |
+--------------------------+--------------------------------------+
| Database Name            | user_db                              |
| Owning Service           | User Service                         |
| Primary Responsibility   | Employee Profiles                    |
+--------------------------+--------------------------------------+

---

## Business Responsibilities

- Employee Information
- Contact Information
- Profile Picture
- Preferences
- Time Zone
- Language
- Notification Preferences

---

## Core Tables

+--------------------------+--------------------------------------+
| Table                    | Purpose                              |
+--------------------------+--------------------------------------+
| employee                 | Employee profile                     |
| address                  | Employee addresses                   |
| emergency_contact        | Emergency contacts                   |
| user_preference          | User preferences                     |
| profile_picture          | Profile image metadata               |
+--------------------------+--------------------------------------+

---

## Table Relationships

```text
employee
 │
 ├──────── address

employee
 │
 ├──────── emergency_contact

employee
 │
 ├──────── profile_picture

employee
 │
 └──────── user_preference
```

---

## Business Rules

+-----------+--------------------------------------------------------------+
| Rule ID   | Rule                                                         |
+-----------+--------------------------------------------------------------+
| USER-DB-001 | Every employee belongs to one organization.                |
| USER-DB-002 | Profile pictures are stored in MinIO.                      |
| USER-DB-003 | Preferences are configurable per employee.                 |
| USER-DB-004 | Personal information shall follow privacy policies.        |
| USER-DB-005 | Email addresses must be unique within an organization.     |
+-----------+--------------------------------------------------------------+

---

## Index Recommendations

+--------------------------+--------------------------------------+
| Table                    | Recommended Index                    |
+--------------------------+--------------------------------------+
| employee                 | organization_id                      |
| employee                 | email                                |
| employee                 | employee_code                        |
| profile_picture          | employee_id                          |
+--------------------------+--------------------------------------+

---

# 17.3 Organization Domain

## Purpose

The Organization Domain represents the tenant structure of WorkSphere.

It defines organizations, departments, teams, designations, and reporting
structures.

---

## Database Information

+--------------------------+--------------------------------------+
| Attribute                | Value                                |
+--------------------------+--------------------------------------+
| Database Name            | organization_db                      |
| Owning Service           | Organization Service                 |
| Primary Responsibility   | Organization Structure               |
+--------------------------+--------------------------------------+

---

## Business Responsibilities

- Organization Management
- Department Management
- Team Management
- Designation Management
- Reporting Hierarchy
- Organization Settings

---

## Core Tables

+--------------------------+--------------------------------------+
| Table                    | Purpose                              |
+--------------------------+--------------------------------------+
| organization             | Organization master                  |
| department               | Departments                          |
| team                     | Teams                                |
| designation              | Job designations                     |
| reporting_structure      | Employee reporting hierarchy         |
| organization_setting     | Organization configuration           |
+--------------------------+--------------------------------------+

---

## Table Relationships

```text
organization
 │
 ├──────── department
 │            │
 │            └──────── team
 │
 ├──────── designation
 │
 └──────── organization_setting
```

---

## Business Rules

+-----------+--------------------------------------------------------------+
| Rule ID   | Rule                                                         |
+-----------+--------------------------------------------------------------+
| ORG-DB-001 | Every department belongs to one organization.               |
| ORG-DB-002 | Teams belong to departments.                                |
| ORG-DB-003 | Designations are organization-specific.                     |
| ORG-DB-004 | Organization settings are configurable.                     |
| ORG-DB-005 | Reporting hierarchy shall not contain circular references.  |
+-----------+--------------------------------------------------------------+

---

## Index Recommendations

+--------------------------+--------------------------------------+
| Table                    | Recommended Index                    |
+--------------------------+--------------------------------------+
| organization             | organization_code                    |
| department               | organization_id                      |
| team                     | department_id                        |
| designation              | organization_id                      |
+--------------------------+--------------------------------------+

---

## Design Notes

1. Every database follows the common Base Entity standard.
2. Every business entity contains `organization_id`.
3. UUIDs are used as primary keys.
4. Audit columns are mandatory.
5. Soft delete is enabled for all business entities.
6. Cross-service foreign keys are prohibited.

---

# End of Part 3

---

# 17.4 Workspace Domain

## Purpose

The Workspace Domain provides logical collaboration spaces where employees
can organize projects, documents, discussions, and team activities.

A workspace acts as the primary collaboration boundary within an
organization.

---

## Database Information

+--------------------------+--------------------------------------+
| Attribute                | Value                                |
+--------------------------+--------------------------------------+
| Database Name            | workspace_db                         |
| Owning Service           | Workspace Service                    |
| Primary Responsibility   | Workspace Management                 |
| Database Engine          | PostgreSQL                           |
+--------------------------+--------------------------------------+

---

## Business Responsibilities

- Workspace Management
- Workspace Membership
- Workspace Roles
- Workspace Settings
- Workspace Invitations
- Workspace Activity Tracking

---

## Core Tables

+--------------------------+--------------------------------------+
| Table                    | Purpose                              |
+--------------------------+--------------------------------------+
| workspace                | Workspace master                     |
| workspace_member         | Workspace membership                 |
| workspace_role           | Workspace roles                      |
| workspace_invitation     | Pending invitations                  |
| workspace_setting        | Workspace configuration              |
| workspace_activity       | Workspace activity history           |
+--------------------------+--------------------------------------+

---

## Table Relationships

```text
workspace
    │
    ├──────── workspace_member
    │
    ├──────── workspace_role
    │
    ├──────── workspace_setting
    │
    ├──────── workspace_invitation
    │
    └──────── workspace_activity
```

---

## Business Rules

+------------------+-------------------------------------------------------+
| Rule ID          | Rule                                                  |
+------------------+-------------------------------------------------------+
| WS-DB-001        | Every workspace belongs to one organization.          |
| WS-DB-002        | Workspace names must be unique within an organization.|
| WS-DB-003        | Users may belong to multiple workspaces.              |
| WS-DB-004        | Every workspace must have at least one administrator. |
| WS-DB-005        | Invitations shall expire after a configurable period. |
+------------------+-------------------------------------------------------+

---

## Recommended Indexes

+--------------------------+--------------------------------------+
| Table                    | Index                               |
+--------------------------+--------------------------------------+
| workspace                | organization_id                     |
| workspace                | workspace_name                      |
| workspace_member         | workspace_id                        |
| workspace_member         | member_id                           |
| workspace_invitation     | invitation_token                    |
+--------------------------+--------------------------------------+

---

## Future Enhancements

- Workspace Templates
- Archived Workspaces
- Workspace Categories
- Workspace Branding
- Guest Access

---

# 17.5 Project Domain

## Purpose

The Project Domain manages projects executed within WorkSphere.

Projects provide planning, scheduling, progress tracking, milestones,
resource allocation, and reporting.

---

## Database Information

+--------------------------+--------------------------------------+
| Attribute                | Value                                |
+--------------------------+--------------------------------------+
| Database Name            | project_db                           |
| Owning Service           | Project Service                      |
| Primary Responsibility   | Project Management                   |
+--------------------------+--------------------------------------+

---

## Business Responsibilities

- Project Lifecycle
- Milestone Management
- Sprint Planning
- Project Membership
- Project Templates
- Project Activity

---

## Core Tables

+--------------------------+--------------------------------------+
| Table                    | Purpose                              |
+--------------------------+--------------------------------------+
| project                  | Project master                       |
| project_member           | Team members                         |
| milestone                | Project milestones                   |
| sprint                   | Sprint planning                      |
| project_template         | Project templates                    |
| project_activity         | Activity timeline                    |
+--------------------------+--------------------------------------+

---

## Table Relationships

```text
project
    │
    ├──────── project_member
    │
    ├──────── milestone
    │
    ├──────── sprint
    │
    ├──────── project_template
    │
    └──────── project_activity
```

---

## Business Rules

+------------------+-------------------------------------------------------+
| Rule ID          | Rule                                                  |
+------------------+-------------------------------------------------------+
| PROJ-DB-001      | Every project belongs to one workspace.               |
| PROJ-DB-002      | Projects have one owner.                              |
| PROJ-DB-003      | Milestones belong to one project.                     |
| PROJ-DB-004      | Closed projects are read-only.                        |
| PROJ-DB-005      | Project names must be unique within a workspace.      |
+------------------+-------------------------------------------------------+

---

## Recommended Indexes

+--------------------------+--------------------------------------+
| Table                    | Index                               |
+--------------------------+--------------------------------------+
| project                  | workspace_id                        |
| project                  | project_status                      |
| milestone                | project_id                          |
| sprint                   | project_id                          |
| project_member           | member_id                           |
+--------------------------+--------------------------------------+

---

## Future Enhancements

- Portfolio Management
- Budget Tracking
- Risk Register
- Dependency Mapping
- Resource Planning

---

# 17.6 Task Domain

## Purpose

The Task Domain manages all work items within projects, including task
assignment, prioritization, comments, attachments, history, and workflow.

This is expected to become one of the largest databases in WorkSphere.

---

## Database Information

+--------------------------+--------------------------------------+
| Attribute                | Value                                |
+--------------------------+--------------------------------------+
| Database Name            | task_db                              |
| Owning Service           | Task Service                         |
| Primary Responsibility   | Task Management                      |
+--------------------------+--------------------------------------+

---

## Business Responsibilities

- Task Management
- Task Assignment
- Comments
- Attachments
- Labels
- Task History
- Workflow Tracking

---

## Core Tables

+--------------------------+--------------------------------------+
| Table                    | Purpose                              |
+--------------------------+--------------------------------------+
| task                     | Task master                          |
| task_comment             | User comments                        |
| task_attachment          | File attachments                     |
| task_label               | Labels                               |
| task_history             | Change history                       |
| task_checklist           | Checklist items                      |
| task_dependency          | Task dependencies                    |
+--------------------------+--------------------------------------+

---

## Table Relationships

```text
task
   │
   ├──────── task_comment
   │
   ├──────── task_attachment
   │
   ├──────── task_label
   │
   ├──────── task_history
   │
   ├──────── task_checklist
   │
   └──────── task_dependency
```

---

## Business Rules

+------------------+-------------------------------------------------------+
| Rule ID          | Rule                                                  |
+------------------+-------------------------------------------------------+
| TASK-DB-001      | Every task belongs to one project.                    |
| TASK-DB-002      | Tasks may have one assignee.                          |
| TASK-DB-003      | Task history is immutable.                            |
| TASK-DB-004      | Attachments are stored in MinIO.                      |
| TASK-DB-005      | Completed tasks cannot be edited without reopening.   |
| TASK-DB-006      | Circular task dependencies are prohibited.            |
+------------------+-------------------------------------------------------+

---

## Recommended Indexes

+--------------------------+--------------------------------------+
| Table                    | Index                               |
+--------------------------+--------------------------------------+
| task                     | project_id                          |
| task                     | assignee_id                         |
| task                     | due_date                            |
| task                     | priority                            |
| task_comment             | task_id                             |
| task_history             | task_id                             |
+--------------------------+--------------------------------------+

---

## Future Enhancements

- Kanban Boards
- Gantt Charts
- AI Task Suggestions
- Time Tracking
- Workload Forecasting
- Burndown Analytics

---

# Architecture Notes

+-----------+------------------------------------------------------------+
| Note ID   | Description                                                |
+-----------+------------------------------------------------------------+
| ARCH-001  | Every database follows the Base Entity standard.           |
| ARCH-002  | UUID is the primary key for all entities.                  |
| ARCH-003  | organization_id is mandatory for business entities.        |
| ARCH-004  | Soft delete is enabled for all business tables.            |
| ARCH-005  | Cross-service foreign keys are prohibited.                 |
| ARCH-006  | Business communication occurs through APIs or events.      |
+-----------+------------------------------------------------------------+

---

# End of Part 4

---

# 17.7 Document Domain

## Purpose

The Document Domain manages all digital assets uploaded to the WorkSphere
platform.

This includes documents, images, spreadsheets, presentations, PDFs,
attachments, folders, and version history.

Actual file binaries are stored in MinIO, while this database stores only
metadata and references.

---

## Database Information

+--------------------------+--------------------------------------+
| Attribute                | Value                                |
+--------------------------+--------------------------------------+
| Database Name            | document_db                          |
| Owning Service           | Document Service                     |
| Primary Responsibility   | Document Management                  |
| Storage Engine           | PostgreSQL                           |
| Object Storage           | MinIO                               |
+--------------------------+--------------------------------------+

---

## Business Responsibilities

- Document Storage
- Folder Management
- Version Control
- Metadata Management
- File Sharing
- File Permissions
- Attachment Management

---

## Core Tables

+--------------------------+--------------------------------------+
| Table                    | Purpose                              |
+--------------------------+--------------------------------------+
| document                 | Document metadata                    |
| folder                   | Folder hierarchy                     |
| document_version         | Version history                      |
| document_permission      | Sharing permissions                  |
| document_tag             | Tags & categorization                |
| document_activity        | Audit trail                          |
+--------------------------+--------------------------------------+

---

## Table Relationships

```text
folder
   │
   └──────── document
                │
                ├──────── document_version
                ├──────── document_permission
                ├──────── document_tag
                └──────── document_activity
```

---

## Business Rules

+------------------+-------------------------------------------------------+
| Rule ID          | Rule                                                  |
+------------------+-------------------------------------------------------+
| DOC-DB-001       | Every document belongs to one workspace.              |
| DOC-DB-002       | Files are stored only in MinIO.                       |
| DOC-DB-003       | Database stores metadata only.                        |
| DOC-DB-004       | Every modification creates a new document version.    |
| DOC-DB-005       | Deleted documents follow the Soft Delete policy.      |
+------------------+-------------------------------------------------------+

---

## Recommended Indexes

+--------------------------+--------------------------------------+
| Table                    | Index                               |
+--------------------------+--------------------------------------+
| document                 | workspace_id                        |
| document                 | folder_id                           |
| document                 | owner_id                            |
| document_version         | document_id                         |
| folder                   | parent_folder_id                    |
+--------------------------+--------------------------------------+

---

## Future Enhancements

- OCR Metadata
- AI Document Search
- Smart Tagging
- Document Approval Workflow
- External Sharing
- Digital Signatures

---

# 17.8 Notification Domain

## Purpose

The Notification Domain manages all user notifications generated by the
WorkSphere platform.

Notifications may be delivered through multiple communication channels.

---

## Database Information

+--------------------------+--------------------------------------+
| Attribute                | Value                                |
+--------------------------+--------------------------------------+
| Database Name            | notification_db                      |
| Owning Service           | Notification Service                 |
| Primary Responsibility   | Notification Management              |
+--------------------------+--------------------------------------+

---

## Business Responsibilities

- In-App Notifications
- Email Notifications
- Push Notifications
- Notification Templates
- Delivery Tracking
- User Notification Preferences

---

## Core Tables

+--------------------------+--------------------------------------+
| Table                    | Purpose                              |
+--------------------------+--------------------------------------+
| notification             | Notification master                  |
| notification_template    | Message templates                    |
| notification_delivery    | Delivery status                      |
| notification_preference  | User preferences                     |
| notification_queue       | Pending notifications                |
+--------------------------+--------------------------------------+

---

## Table Relationships

```text
notification
     │
     ├──────── notification_delivery
     ├──────── notification_template
     ├──────── notification_queue
     └──────── notification_preference
```

---

## Business Rules

+------------------+-------------------------------------------------------+
| Rule ID          | Rule                                                  |
+------------------+-------------------------------------------------------+
| NOTI-DB-001      | Notifications belong to one recipient.                |
| NOTI-DB-002      | Delivery status must be tracked.                      |
| NOTI-DB-003      | Templates shall support placeholders.                 |
| NOTI-DB-004      | Failed deliveries shall be retryable.                 |
| NOTI-DB-005      | User preferences override default channels.           |
+------------------+-------------------------------------------------------+

---

## Recommended Indexes

+--------------------------+--------------------------------------+
| Table                    | Index                               |
+--------------------------+--------------------------------------+
| notification             | recipient_id                        |
| notification             | status                              |
| notification_delivery    | notification_id                     |
| notification_queue       | scheduled_time                      |
+--------------------------+--------------------------------------+

---

## Future Enhancements

- SMS Integration
- Microsoft Teams Integration
- Slack Integration
- WhatsApp Integration
- Notification Analytics

---

# 17.9 Analytics Domain

## Purpose

The Analytics Domain stores reporting data, dashboards, KPIs, and
aggregated business metrics.

This database is optimized for analytical queries rather than transactional
operations.

---

## Database Information

+--------------------------+--------------------------------------+
| Attribute                | Value                                |
+--------------------------+--------------------------------------+
| Database Name            | analytics_db                         |
| Owning Service           | Analytics Service                    |
| Primary Responsibility   | Reporting & Dashboards               |
+--------------------------+--------------------------------------+

---

## Business Responsibilities

- Dashboards
- KPI Management
- Reports
- Usage Statistics
- Business Metrics
- Trend Analysis

---

## Core Tables

+--------------------------+--------------------------------------+
| Table                    | Purpose                              |
+--------------------------+--------------------------------------+
| dashboard                | Dashboard definitions                |
| report                   | Generated reports                    |
| metric                   | Business metrics                     |
| dashboard_widget         | Dashboard widgets                    |
| analytics_snapshot       | Historical snapshots                 |
+--------------------------+--------------------------------------+

---

## Business Rules

+------------------+-------------------------------------------------------+
| Rule ID          | Rule                                                  |
+------------------+-------------------------------------------------------+
| ANA-DB-001       | Analytics data is read optimized.                     |
| ANA-DB-002       | Reports may be generated asynchronously.              |
| ANA-DB-003       | Historical snapshots are immutable.                   |
| ANA-DB-004       | KPIs are organization specific.                       |
+------------------+-------------------------------------------------------+

---

## Future Enhancements

- AI Forecasting
- Predictive Analytics
- Executive Scorecards
- Data Warehouse Integration

---

# 17.10 Audit Domain

## Purpose

The Audit Domain provides a centralized audit trail for security,
compliance, operational monitoring, and forensic investigations.

Audit information shall be immutable.

---

## Database Information

+--------------------------+--------------------------------------+
| Attribute                | Value                                |
+--------------------------+--------------------------------------+
| Database Name            | audit_db                             |
| Owning Service           | Audit Service                        |
| Primary Responsibility   | Audit Logging                        |
+--------------------------+--------------------------------------+

---

## Business Responsibilities

- Audit Logs
- User Activities
- Security Events
- Login History
- System Events
- Compliance Records

---

## Core Tables

+--------------------------+--------------------------------------+
| Table                    | Purpose                              |
+--------------------------+--------------------------------------+
| audit_log                | System audit records                 |
| activity_log             | User activities                      |
| login_history            | Authentication history               |
| security_event           | Security incidents                   |
| system_event             | Platform events                      |
+--------------------------+--------------------------------------+

---

## Business Rules

+------------------+-------------------------------------------------------+
| Rule ID          | Rule                                                  |
+------------------+-------------------------------------------------------+
| AUD-DB-001       | Audit records are immutable.                          |
| AUD-DB-002       | Audit records cannot be updated.                      |
| AUD-DB-003       | Every security event is logged.                       |
| AUD-DB-004       | Login history is retained per policy.                 |
| AUD-DB-005       | Audit data is never physically deleted.               |
+------------------+-------------------------------------------------------+

---

## Recommended Indexes

+--------------------------+--------------------------------------+
| Table                    | Index                               |
+--------------------------+--------------------------------------+
| audit_log                | created_at                          |
| activity_log             | user_id                             |
| login_history            | user_id                             |
| security_event           | severity                            |
+--------------------------+--------------------------------------+

---

## Future Enhancements

- Compliance Reports
- SIEM Integration
- Threat Intelligence
- Risk Scoring
- Security Dashboard

---

# 18. Database Domain Summary

+--------------------------+--------------------------+-------------------------------+
| Domain                   | Database                 | Primary Responsibility        |
+--------------------------+--------------------------+-------------------------------+
| Authentication           | auth_db                  | Identity & Access Management  |
| User                     | user_db                  | Employee Profiles             |
| Organization             | organization_db          | Organization Structure        |
| Workspace                | workspace_db             | Collaboration Spaces          |
| Project                  | project_db               | Project Management            |
| Task                     | task_db                  | Task Management               |
| Document                 | document_db              | File Management               |
| Notification             | notification_db          | User Notifications            |
| Analytics                | analytics_db             | Reporting & Insights          |
| Audit                    | audit_db                 | Compliance & Audit            |
+--------------------------+--------------------------+-------------------------------+

---

# End of Part 5

---

# 19. Relationship Strategy

## Overview

Relationships within WorkSphere are designed to support a distributed
microservices architecture while maintaining strong data consistency within
each service boundary.

The following principles apply to all databases.

---

## Relationship Principles

+----------------------+-----------------------------------------------------------+
| Principle ID         | Description                                               |
+----------------------+-----------------------------------------------------------+
| REL-001              | Relationships are allowed only within the same database.  |
| REL-002              | Cross-service foreign keys are prohibited.                |
| REL-003              | Cross-service references use UUID values only.            |
| REL-004              | Business validation occurs at the service layer.          |
| REL-005              | Circular dependencies shall be avoided.                  |
+----------------------+-----------------------------------------------------------+

---

## Relationship Types

+----------------------+------------------------------------------+
| Relationship          | Example                                  |
+----------------------+------------------------------------------+
| One-to-One           | Employee → Profile Picture               |
| One-to-Many          | Project → Task                           |
| Many-to-Many         | User ↔ Role                              |
| Self-Reference       | Employee → Manager                       |
+----------------------+------------------------------------------+

---

# 20. Referential Integrity

Referential integrity shall be enforced using foreign key constraints
only within the owning service database.

Example:

```text
Project Database

project
    │
    └──────── milestone
```

The following is prohibited:

```text
Task Database

task.user_id
        │
        ▼
User Database

employee
```

Instead, the Task Service shall retrieve employee information through the
User Service API.

---

# 21. Transaction Management

## Overview

Transactions shall remain local to a single microservice whenever possible.

Distributed transactions across services shall be avoided.

---

## Transaction Standards

+----------------------+-----------------------------------------------------------+
| Standard ID          | Description                                               |
+----------------------+-----------------------------------------------------------+
| TX-001               | ACID transactions within a service.                       |
| TX-002               | Distributed transactions are discouraged.                 |
| TX-003               | Eventual consistency is preferred.                        |
| TX-004               | Business events synchronize data.                         |
| TX-005               | Retry mechanisms handle transient failures.               |
+----------------------+-----------------------------------------------------------+

---

# 22. Indexing Strategy

Indexes shall be created only where they provide measurable value.

Over-indexing should be avoided because it impacts write performance.

---

## Recommended Indexed Columns

+----------------------+-------------------------------------------+
| Category             | Typical Columns                           |
+----------------------+-------------------------------------------+
| Primary Lookup       | id                                        |
| Authentication       | email, username                           |
| Organization         | organization_id                           |
| Workspace            | workspace_id                              |
| Project              | project_id                                |
| Task                 | assignee_id, due_date, status             |
| Audit                | created_at                                |
+----------------------+-------------------------------------------+

---

# 23. Performance Guidelines

The database layer shall follow these performance recommendations.

+----------------------+-----------------------------------------------------------+
| Guideline ID         | Recommendation                                            |
+----------------------+-----------------------------------------------------------+
| PERF-001             | Use pagination for large datasets.                        |
| PERF-002             | Avoid SELECT *.                                           |
| PERF-003             | Use lazy loading where appropriate.                       |
| PERF-004             | Cache frequently accessed data using Redis.               |
| PERF-005             | Monitor slow queries regularly.                           |
| PERF-006             | Create indexes based on usage patterns.                   |
+----------------------+-----------------------------------------------------------+

---

# 24. Backup and Recovery Strategy

The platform shall support automated backup and disaster recovery.

---

## Backup Standards

+----------------------+-------------------------------------------+
| Category             | Strategy                                 |
+----------------------+-------------------------------------------+
| Full Backup          | Daily                                    |
| Incremental Backup   | Hourly                                   |
| Point-in-Time        | Enabled                                  |
| Backup Verification  | Scheduled                                |
| Disaster Recovery    | Supported                                |
+----------------------+-------------------------------------------+

---

# 25. Data Retention Policy

Each business domain shall define its own retention policy.

+----------------------+-------------------------------------------+
| Data Type            | Retention Strategy                       |
+----------------------+-------------------------------------------+
| Audit Logs           | Long-term retention                      |
| Login History        | As per security policy                   |
| Notifications        | Configurable                             |
| Analytics            | Historical archive                       |
| Soft Deleted Data    | Configurable purge policy                |
+----------------------+-------------------------------------------+

---

# 26. Database Security Guidelines

Database security is a core architectural requirement.

---

## Security Standards

+----------------------+-----------------------------------------------------------+
| Standard ID          | Description                                               |
+----------------------+-----------------------------------------------------------+
| SEC-DB-001           | Passwords shall be hashed using BCrypt.                   |
| SEC-DB-002           | Secrets shall never be stored in plain text.              |
| SEC-DB-003           | Database access follows least privilege.                  |
| SEC-DB-004           | TLS shall be used for database connections.               |
| SEC-DB-005           | Sensitive data shall be encrypted where required.         |
| SEC-DB-006           | Audit logging shall be enabled.                           |
+----------------------+-----------------------------------------------------------+

---

# 27. Database Migration Strategy

WorkSphere uses Flyway for version-controlled database migrations.

---

## Migration Standards

+----------------------+-----------------------------------------------------------+
| Standard ID          | Description                                               |
+----------------------+-----------------------------------------------------------+
| MIG-001              | All schema changes use Flyway.                            |
| MIG-002              | Manual production schema changes are prohibited.          |
| MIG-003              | Every migration is versioned.                             |
| MIG-004              | Rollback procedures shall be documented.                  |
| MIG-005              | Migrations are executed during deployment.                |
+----------------------+-----------------------------------------------------------+

---

# 28. Database Design Best Practices

The following practices apply throughout the WorkSphere platform.

- Prefer UUIDs over auto-increment identifiers.
- Normalize data to Third Normal Form unless justified.
- Keep services loosely coupled.
- Use soft delete for business entities.
- Avoid unnecessary joins.
- Design indexes based on actual query patterns.
- Keep business logic outside the database.
- Store timestamps in UTC.
- Version-control every schema change.
- Follow the Base Entity standard consistently.

---

# 29. References

This document should be read together with the following documents:

+----------------------+-------------------------------------------+
| Document             | Purpose                                  |
+----------------------+-------------------------------------------+
| 05_System_Architecture.md      | Overall system architecture     |
| 06_Architecture_Decision_Record.md | Architectural decisions     |
| 06A_Technology_Stack.md        | Technology standards            |
| 07A_ER_Diagram.md              | Entity relationships            |
| 08_API_Design.md               | API specifications              |
| Authentication.md              | Security architecture           |
+----------------------+-------------------------------------------+

---

# Version History

+-----------+--------------+----------------------------------------------+-------------------+
| Version   | Date         | Description                                  | Author            |
+-----------+--------------+----------------------------------------------+-------------------+
| 1.0       | July 2026    | Initial release of Database Design document  | Bhargav Kaushik   |
+-----------+--------------+----------------------------------------------+-------------------+

---

# End of Document