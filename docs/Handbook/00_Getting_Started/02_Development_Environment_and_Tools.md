# WorkSphere Development Environment and Tools

This document provides the reference list of tools required to develop, build, test, containerize, run, and deploy the WorkSphere application.

The purpose of this document is to maintain a single reference point for the development environment and the role of each tool.

---

## 1. Development Tools

| Tool                   | Purpose                                                                                                                                  |
| ---------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| **Java JDK 17**        | Provides the Java Development Kit required to develop, compile, and run the WorkSphere backend applications.                             |
| **Maven**              | Manages Java project dependencies, builds, packaging, testing, and application lifecycle tasks.                                          |
| **Git**                | Provides distributed version control for tracking WorkSphere source code and documentation changes.                                      |
| **GitHub**             | Hosts the WorkSphere Git repository and provides remote source-code collaboration and version history.                                   |
| **Visual Studio Code** | Provides the primary lightweight development environment for editing source code, configuration files, documentation, and project files. |
| **Node.js**            | Provides the JavaScript runtime required for frontend development and Angular tooling.                                                   |
| **npm**                | Manages JavaScript/TypeScript packages and dependencies used by the frontend application.                                                |
| **npx**                | Executes locally installed Node.js command-line tools without requiring separate global installations.                                   |
| **Angular CLI**        | Provides command-line tools for creating, developing, building, testing, and maintaining the WorkSphere Angular frontend.                |

---

## 2. Backend and Application Development

| Tool                | Purpose                                                                                                                  |
| ------------------- | ------------------------------------------------------------------------------------------------------------------------ |
| **Spring Boot**     | Provides the application framework used to build WorkSphere backend services and REST APIs.                              |
| **Spring Data JPA** | Simplifies persistence and database access for Java applications using JPA.                                              |
| **Spring Security** | Provides authentication and authorization capabilities for securing backend applications and APIs.                       |
| **Hibernate**       | Provides the JPA implementation used for object-relational mapping between Java entities and relational database tables. |

---

## 3. Database Tools

| Tool           | Purpose                                                                                                                       |
| -------------- | ----------------------------------------------------------------------------------------------------------------------------- |
| **PostgreSQL** | Provides the relational database used for persistent WorkSphere application data.                                             |
| **pgAdmin**    | Provides a graphical interface for managing PostgreSQL databases, users, schemas, queries, and database administration tasks. |

---

## 4. Containerization and Local Infrastructure

| Tool               | Purpose                                                                                                    |
| ------------------ | ---------------------------------------------------------------------------------------------------------- |
| **Docker Desktop** | Provides the local Docker environment for building and running WorkSphere containers.                      |
| **Docker Engine**  | Executes WorkSphere containers and provides the underlying container runtime used by Docker Desktop.       |
| **Docker Compose** | Defines and runs multiple WorkSphere containers together as a local multi-service application environment. |
| **WSL 2**          | Provides the Linux-based environment and virtualization layer used by Docker Desktop on Windows.           |

---

## 5. Kubernetes and Container Orchestration

| Tool           | Purpose                                                                                                                    |
| -------------- | -------------------------------------------------------------------------------------------------------------------------- |
| **kubectl**    | Provides the command-line interface for communicating with and managing Kubernetes clusters.                               |
| **Minikube**   | Provides a local Kubernetes cluster for developing, testing, and validating WorkSphere deployments on a developer machine. |
| **Kubernetes** | Provides container orchestration for deploying, scaling, networking, and managing WorkSphere services.                     |

---

## 6. WorkSphere Development Stack

The tools above support the following development flow:

```text
Developer
   │
   ├── Visual Studio Code
   │
   ├── Git
   │     └── GitHub
   │
   ├── Backend
   │     ├── Java JDK 17
   │     ├── Maven
   │     ├── Spring Boot
   │     ├── Spring Data JPA
   │     ├── Hibernate
   │     └── Spring Security
   │
   ├── Frontend
   │     ├── Node.js
   │     ├── npm / npx
   │     └── Angular CLI
   │
   ├── Database
   │     └── PostgreSQL
   │           └── pgAdmin
   │
   ├── Containers
   │     ├── Docker Desktop
   │     ├── Docker Engine
   │     └── Docker Compose
   │
   └── Kubernetes
         ├── WSL 2
         ├── Minikube
         └── kubectl
```

---

## 7. Verified Local Environment

The following tools have been installed and verified in the current WorkSphere development environment:

| Tool               | Verified Version |
| ------------------ | ---------------- |
| Java JDK           | 17.0.6           |
| Maven              | 3.9.11           |
| Git                | 2.46.0.windows.1 |
| Node.js            | 24.20.0          |
| npm                | 11.19.0          |
| npx                | 11.19.0          |
| Angular CLI        | 22.1.6           |
| Docker Engine      | 29.7.2           |
| Docker Compose     | 5.4.0            |
| WSL 2              | 2.5.10.0         |
| kubectl            | 1.32.2           |
| Minikube           | 1.38.1           |
| Kubernetes         | 1.35.1           |
| PostgreSQL         | 17.11            |
| Visual Studio Code | 1.131.0          |

---

## 8. Environment Relationship

The local WorkSphere infrastructure follows this general relationship:

```text
Windows
   │
   └── WSL 2
         │
         └── Docker Desktop
               │
               └── Docker Engine
                     │
                     └── Minikube
                           │
                           └── Kubernetes
                                 │
                                 └── kubectl
```

The application development tools operate alongside this infrastructure:

```text
Java 17 + Maven
        │
        └── WorkSphere Backend

Node.js + npm + Angular CLI
        │
        └── WorkSphere Frontend

PostgreSQL
        │
        └── WorkSphere Persistent Data

Docker + Docker Compose
        │
        └── Local Containerized Environment

Minikube + Kubernetes + kubectl
        │
        └── Local Kubernetes Environment
```

---

## 9. Purpose of This Document

This document should be used as the primary quick reference when:

* Setting up WorkSphere on a new development machine.
* Verifying required development tools.
* Troubleshooting missing command-line tools.
* Checking the expected development environment.
* Understanding how the WorkSphere development tools fit together.
* Preparing the environment before starting application development.

Tool versions may change over time. When a tool is upgraded, this document should be updated to reflect the actual verified environment rather than assuming that a newer version is installed.

---

## 10. Important Rule

The presence of a tool in this document means that the tool has a defined role in the WorkSphere development environment.

It does **not** mean that every tool must be running simultaneously.

For example:

* Java and Maven are required for backend development and builds.
* Node.js, npm, and Angular CLI are required for frontend development.
* PostgreSQL is required when database functionality is being developed or tested.
* Docker is required when containerized services are being developed or executed.
* Minikube, Kubernetes, and kubectl are required when Kubernetes-based deployment and orchestration are being developed or tested.

This document should remain focused on the tools required to support the WorkSphere development lifecycle and should not become a general-purpose list of unrelated developer software.
