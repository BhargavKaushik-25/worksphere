# WorkSphere MVP Scope

## Document Information

| Field | Details |
|---|---|
| Document Name | WorkSphere MVP Scope |
| Project | WorkSphere |
| Version | 1.0 |
| Status | Proposed |
| Owner | Nexora Labs |
| Last Updated | 2026-09-03 |

## 1. Purpose

This document defines the Minimum Viable Product (MVP) scope for WorkSphere.
MVP means Minimum Viable Product, not Minimum Viable Price. It is the smallest
usable release that delivers meaningful business value and supports a complete
workplace-management workflow.

This scope is a focused subset of the broader Version 1 business requirements.

## 2. MVP Objective

The MVP shall enable an organization to authenticate users, manage its basic
structure, create projects, assign work, and track task progress through a web
application.

## 3. Supported Users

| Role | MVP Responsibilities |
|---|---|
| Organization Admin | Manage the organization, users, departments, teams, and roles |
| Manager | Create projects, assign work, and monitor progress |
| Employee | View assigned work and update task progress |

## 4. Functional Scope

### 4.1 Authentication and Access

- Login and logout
- Secure password hashing
- JWT-based authentication
- Protected API endpoints
- Active and inactive account status
- Basic password validation
- Basic authentication-event logging
- Role-based access control for protected operations

### 4.2 Organization and User Management

- Create an organization
- Create and manage user and employee profiles
- Assign Organization Admin, Manager, and Employee roles
- Create departments and teams
- Assign employees to departments and teams
- View and update permitted profile information
- Search users and employees
- Enforce basic organization and role boundaries

### 4.3 Project Management

- Create projects
- Assign a project manager
- Assign employees to projects
- Define project start and end dates
- View and filter projects
- Archive projects

Supported project statuses:

- Planned
- Active
- Completed
- Archived

### 4.4 Task Management

- Create tasks within projects
- Assign tasks to active employees
- Set task priority and dates
- Update task status and progress
- View task details and task history
- Filter tasks by project, assignee, priority, and status
- Preserve task ownership and prevent unauthorized access

Supported task statuses:

- To Do
- In Progress
- Blocked
- Completed

Supported priorities:

- Low
- Medium
- High
- Critical

### 4.5 Frontend Application

- Login page
- Authenticated application layout
- Dashboard
- Profile page
- Organization, department, and team views
- Project list and details views
- Task list and details views
- Authentication guards
- Role-aware navigation
- Form validation
- Loading, empty, and error states
- Responsive layout

## 5. Technical Scope

### 5.1 Backend

The initial implementation shall use Java 21, Spring Boot 3, Maven, Spring
Security, Spring Data JPA, Spring Validation, and REST APIs.

The initial backend structure is:

```text
backend/
├── api-gateway/
├── auth-service/
├── user-service/
├── project-service/
├── task-service/
└── shared/
```

The shared module shall contain only genuinely common contracts and utilities,
such as API error models, validation types, and event contracts.

### 5.2 Frontend

The frontend shall use Angular 20, TypeScript, SCSS, Angular Material, and
Angular Router. NgRx remains optional until application state complexity
justifies it.

### 5.3 Database

- PostgreSQL
- One database owned by each service
- Flyway-managed migrations
- UUID primary keys
- Appropriate unique, foreign-key, check, and not-null constraints
- Audit columns on business entities
- Soft deletion where appropriate
- No direct cross-service database access

### 5.4 API

APIs shall follow `/api/v1/{resource}` and use REST, JSON, standard HTTP status
codes, input validation, consistent error responses, pagination for collection
endpoints, OpenAPI documentation, and JWT authorization.

### 5.5 Local Operations

The MVP shall run locally with Docker Compose and provide PostgreSQL, the
application services, the API Gateway, environment configuration, health
checks, and structured application logging.

## 6. End-to-End MVP Workflow

```text
Organization Admin logs in
    -> creates organization structure
    -> creates employees and assigns roles
    -> creates departments and teams
    -> Manager creates a project
    -> Manager assigns employees to the project
    -> Manager creates tasks
    -> Employees update task progress
    -> Manager views project and task status
```

The workflow must operate from the frontend through the APIs to the database.

## 7. Explicitly Deferred

The following are part of the broader product vision but are not required for
MVP completion:

- Leave management
- Attendance management
- Document storage and sharing
- Advanced notifications
- Reporting and analytics
- External integrations
- AI assistant
- Real-time chat
- Mobile applications
- Payroll, recruitment, and performance management
- Workflow automation
- Multi-tenant SaaS behavior
- Full audit and compliance microservice
- Kubernetes production deployment

Basic security and authentication auditing remains in scope.

## 8. Service and Folder Impact

| Folder | MVP Work |
|---|---|
| `backend/auth-service` | Authentication, JWT, password security, and security tests |
| `backend/user-service` | Organizations, users, employees, departments, teams, and roles |
| `backend/project-service` | Projects, ownership, dates, members, and lifecycle |
| `backend/task-service` | Tasks, assignments, priorities, statuses, progress, and history |
| `backend/shared` | Limited shared API, validation, security, and event contracts |
| `backend/api-gateway` | Routing, authentication forwarding, CORS, and request boundaries |
| `frontend` | Angular application, screens, guards, forms, and API integration |
| `database` | Service migrations, constraints, indexes, and seed data |
| `deployment` | Docker Compose and local service configuration |
| `testing` | Cross-service integration, end-to-end, and contract tests |
| `.github` | Build, test, and security-check workflows |
| `docs` | Keep requirements, API, database, and UI decisions synchronized |

`notification-service` is deferred unless a core workflow requires it.

## 9. Acceptance Criteria

The MVP is complete when:

- Users can log in and log out securely.
- Unauthenticated users cannot access protected resources.
- An administrator can create users, roles, departments, and teams.
- A manager can create a project and assign employees.
- A manager can create and assign tasks.
- Employees can view and update their assigned tasks.
- Managers can view project and task status.
- Invalid input produces clear validation errors.
- Core business rules have automated tests.
- Database changes use Flyway migrations.
- The application runs locally using Docker Compose.
- API documentation is available through OpenAPI or Swagger.
- Frontend loading, empty, validation, and error states are handled.

## 10. Recommended Implementation Order

1. Confirm and baseline this MVP scope.
2. Create the Java/Maven service foundations.
3. Set up PostgreSQL, Flyway, and local configuration.
4. Implement authentication and protected endpoints.
5. Implement users, organizations, departments, teams, and roles.
6. Create the Angular application foundation and authentication flow.
7. Implement project management.
8. Implement task management.
9. Add integration and end-to-end tests.
10. Add Docker Compose and CI build/test checks.
11. Review implementation against this document and update related design docs.

## 11. Relationship to Version 1

The MVP is the first usable delivery within the broader WorkSphere Version 1
vision. Deferred capabilities remain roadmap items and shall not be added to
the MVP unless the scope is formally reviewed and this document is updated.