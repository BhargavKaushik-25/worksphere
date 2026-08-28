# 18_Microservices_Architecture

## Chapter Information

**Chapter Number:** 18

**Chapter Name:** Microservices Architecture

### Prerequisites

- Java Fundamentals
- Object-Oriented Programming (OOP)
- Exception Handling
- Collections
- Generics
- Modern Java
- Streams
- Optional
- Concurrency
- JVM Fundamentals
- Advanced Java
- Spring Framework
- Spring Boot
- REST API Development
- Spring Data JPA & Hibernate
- Spring Security
- Backend Testing

### Next Chapter

- Distributed Communication and Service Integration

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Explain Microservices Architecture.
- Differentiate Monoliths and Microservices.
- Understand Domain-Driven Design (DDD) fundamentals.
- Identify service boundaries.
- Understand Database per Service.
- Understand API Gateway architecture.
- Understand Service Discovery.
- Understand distributed system challenges.
- Explain CAP Theorem.
- Understand resilience patterns.
- Understand observability requirements.
- Understand Microservices security considerations.
- Relate these concepts to WorkSphere architecture.
- Answer enterprise-level microservices interview questions confidently.

---

# Introduction to Microservices Architecture

## What Is Software Architecture?

Software Architecture defines:

```text
What components exist?
What responsibilities do they have?
How do they communicate?
How does the system scale?
How does the system evolve?
```

Architecture is about making structural decisions that influence the entire system.

---

# What Are Microservices?

Microservices Architecture is an architectural style where an application is composed of multiple small, independently deployable services.

Each service:

- Has a clearly defined responsibility.
- Owns its own business logic.
- Can be deployed independently.
- Can scale independently.
- Can evolve independently.

---

# Why Were Microservices Introduced?

As software systems grow, monolithic applications often become difficult to maintain.

Common challenges include:

- Large codebases.
- Long deployment cycles.
- Team coordination issues.
- Scaling inefficiencies.
- Tight coupling between modules.

Microservices attempt to solve these issues through decomposition and separation of concerns.

---

# Real-World Analogy

Consider an airport.

Instead of one giant department handling everything:

```text
Security
Immigration
Boarding
Baggage
Ground Operations
```

specialized departments focus on specific responsibilities.

The airport still functions as one system, but internally responsibilities are separated.

Microservices follow the same idea.

---

# Evolution of Software Architectures

Most enterprise systems evolve through architectural stages.

```text
Monolith
    ↓
Modular Monolith
    ↓
Microservices
```

Microservices are not the starting point of every application.

They are often an evolutionary destination.

---

# Monolithic Architecture

## What Is a Monolith?

A Monolithic Application is a single deployable application containing all business functionality.

Example:

```text
Authentication
Users
Projects
Tasks
Reports
Notifications
```

all deployed together.

---

# Monolithic Architecture Diagram

```text
                Frontend
                    │
                    ▼
        ┌───────────────────────┐
        │   Monolithic App      │
        │                       │
        │ Users                 │
        │ Projects              │
        │ Tasks                 │
        │ Notifications         │
        │ Reports               │
        └───────────────────────┘
                    │
                    ▼
              Database
```

---

# Advantages of Monoliths

## Simpler Development

Everything exists within one codebase.

---

## Easier Local Execution

One application.

One deployment.

---

## Easier Debugging

Method calls occur inside the same process.

---

## Lower Operational Complexity

Smaller infrastructure footprint.

---

# Limitations of Monoliths

As systems grow:

- Codebases become larger.
- Deployments become slower.
- Scaling becomes inefficient.
- Team ownership becomes difficult.
- Technology flexibility decreases.

---

# What Is a Microservice?

A Microservice is a small and focused software service responsible for a specific business capability.

A microservice should answer:

```text
What business responsibility do I own?
```

rather than:

```text
What technical functionality do I provide?
```

---

# Microservices Architecture

```text
                   Frontend
                       │
                       ▼

                 API Gateway

        ┌────────┬────────┬────────┐

        ▼        ▼        ▼        ▼

 Authentication  User    Project   Task
    Service     Service  Service  Service

        ▼        ▼        ▼        ▼

      Auth DB  User DB Project DB Task DB
```

Each service owns its own domain.

---

# Core Principles of Microservices

## Single Responsibility

Each service should have one primary business purpose.

---

## Independent Deployment

Services can be released separately.

---

## Loose Coupling

Services should minimize dependencies.

---

## High Cohesion

Related functionality should remain together.

---

## Autonomous Ownership

Each service owns its:

- Code
- Logic
- Data
- Lifecycle

---

# Domain-Driven Design (DDD)

## What Is Domain-Driven Design?

Domain-Driven Design (DDD) is an approach for designing software around business domains.

Rather than focusing on technology first:

```text
Business Problem
      ↓
Business Domain
      ↓
Bounded Contexts
      ↓
Services
```

DDD helps identify proper service boundaries.

---

# What Is a Domain?

A Domain represents the business area a system addresses.

Examples:

```text
Banking
Healthcare
Insurance
Project Management
E-Commerce
```

WorkSphere belongs primarily to:

```text
Project Management
Collaboration
Workflow Management
```

---

# Subdomains

A domain can be divided into smaller sections.

Example:

```text
Project Management Domain

    ↓

Users
Projects
Tasks
Teams
Notifications
Reporting
```

These become candidates for service boundaries.

---

# Bounded Context

## What Is a Bounded Context?

A Bounded Context defines a logical boundary where a model has a specific meaning.

Example:

```text
User Context

Project Context

Task Context
```

Inside each context, terminology and business rules remain consistent.

---

# Why Bounded Contexts Matter

Microservices should usually align with bounded contexts.

Bad:

```text
Random Technical Split
```

Good:

```text
Business-Oriented Split
```

---

# Service Boundary Identification

One of the most important architectural decisions is:

```text
Where should a service begin?
Where should a service end?
```

---

# Poor Boundary Example

```text
CreateTaskService

UpdateTaskService

DeleteTaskService
```

Too fragmented.

---

# Better Boundary Example

```text
Task Service
```

that owns:

- Task Creation
- Task Updates
- Task Deletion
- Task Queries

---

# Business Capability Decomposition

One common decomposition technique is:

```text
Business Capability Mapping
```

Example:

```text
User Management

Project Management

Task Management

Notifications

Reporting
```

Each capability may become a service.

---

# Service Ownership Principle

A service should own:

```text
Business Logic
Data
APIs
Rules
Validation
```

Ownership should be clear.

---

# Service Communication Principle

Services communicate through contracts, not through internal implementation details.

Common approaches:

```text
REST APIs

Messaging

Events
```

---

# Database Per Service

## What Is Database Per Service?

Each service owns its own database.

Example:

```text
User Service
      ↓
User Database

Project Service
      ↓
Project Database

Task Service
      ↓
Task Database
```

---

# Why Database Per Service Exists

Benefits:

- Loose coupling
- Independent deployments
- Data ownership
- Better autonomy

---

# Shared Database Anti-Pattern

Bad:

```text
All Services
      ↓
One Database
```

Consequences:

- Tight coupling
- Deployment challenges
- Shared ownership confusion

---

# Data Ownership Principle

Only the owning service should modify its data directly.

Other services must communicate through APIs or events.

---

# API Gateway

## What Is an API Gateway?

An API Gateway acts as a centralized entry point for clients.

Instead of clients calling every service directly:

```text
Client
   ↓
Gateway
   ↓
Services
```

the Gateway handles routing and cross-cutting concerns.

---

# Responsibilities of API Gateway

Typical responsibilities include:

- Request routing
- Authentication
- Authorization
- SSL termination
- Rate limiting
- Logging
- Request aggregation

---

# Benefits of API Gateway

## Simplified Clients

Clients interact with one endpoint.

---

## Centralized Security

Authentication handled consistently.

---

## Better Observability

Request tracking becomes easier.

---

# End of Part 1

# Service Discovery

## Why Service Discovery Exists

In a microservices environment, service instances are dynamic.

Examples:

```text
Service Scales Up
New Instances Created

Service Restarts
New Address Assigned

Container Recreated
IP Changes
```

Hardcoded service addresses become difficult to manage.

---

# What Is Service Discovery?

Service Discovery is a mechanism that allows services to locate other services dynamically.

Instead of storing static addresses:

```text
User Service
     ↓
Service Registry
     ↓
Project Service
```

services ask the registry where another service is running.

---

# Service Registry

A Service Registry maintains information about running service instances.

Typical information:

```text
Service Name

Instance Address

Port

Health Status
```

---

# Service Discovery Workflow

```text
Project Service Starts
         ↓
Registers With Registry
         ↓
Registry Stores Location
         ↓
User Service Requests Location
         ↓
Registry Returns Location
         ↓
Communication Begins
```

---

# Client-Side Discovery

In Client-Side Discovery:

```text
Service
    ↓
Registry
    ↓
Target Service
```

The calling service queries the registry directly.

Benefits:

- Simple architecture
- Direct routing

Limitations:

- Discovery logic inside clients

---

# Server-Side Discovery

In Server-Side Discovery:

```text
Client
   ↓
Load Balancer
   ↓
Registry
   ↓
Target Service
```

Infrastructure handles discovery.

Benefits:

- Simpler clients
- Centralized routing

---

# Benefits of Service Discovery

## Dynamic Scaling

New service instances become discoverable automatically.

---

## Improved Resilience

Failed instances can be removed.

---

## Reduced Configuration

No need to manually update service addresses.

---

# Centralized Configuration

## Why Configuration Becomes Difficult

As microservices grow:

```text
Database Settings

Message Broker Settings

Secrets

API URLs

Environment Variables
```

must be managed consistently.

---

# Traditional Problem

Without centralized configuration:

```text
Service A Configuration

Service B Configuration

Service C Configuration

Service D Configuration
```

creating duplication and maintenance overhead.

---

# Centralized Configuration Concept

```text
Configuration Server
          ↓
All Services
```

Services retrieve configuration from a centralized location.

---

# Benefits

## Consistency

All services use the same configuration standards.

---

## Manageability

Changes occur in one location.

---

## Environment Separation

Support for:

```text
Development

Testing

Production
```

---

# Microservice Communication

Services rarely operate in isolation.

Communication is unavoidable.

Common communication styles:

```text
Synchronous

Asynchronous
```

---

# Synchronous Communication

## What Is Synchronous Communication?

A service sends a request and waits for a response.

Example:

```text
User Service
       ↓
Project Service
       ↓
Response
```

The caller is blocked until a response arrives.

---

# REST-Based Communication

The most common synchronous approach.

Example:

```http
GET /projects/1
```

Microservices often communicate using REST APIs.

---

# Advantages

- Simple
- Familiar
- Easy debugging
- Immediate response

---

# Limitations

- Increased dependency
- Higher latency
- Potential cascading failures

---

# Cascading Failure Example

```text
User Service
      ↓
Project Service
      ↓
Task Service
      ↓
Notification Service
```

Failure at one level may impact multiple services.

---

# Asynchronous Communication

## What Is Asynchronous Communication?

The sender does not wait for an immediate response.

Instead:

```text
Service
   ↓
Event
   ↓
Message Broker
   ↓
Consumer Service
```

Communication becomes decoupled.

---

# Event-Driven Architecture

Many microservice systems use:

```text
Event-Driven Architecture
```

where events describe things that have happened.

Examples:

```text
UserCreated

ProjectCreated

TaskAssigned

TaskCompleted
```

---

# Event Flow Example

```text
Task Service
      ↓
TaskCompleted Event
      ↓
Message Broker
      ↓
Notification Service
      ↓
Send Notification
```

The services remain loosely coupled.

---

# Advantages

## Better Scalability

Consumers process events independently.

---

## Better Resilience

Systems tolerate temporary failures more effectively.

---

## Loose Coupling

Services do not need deep awareness of each other.

---

# Message Brokers

A Message Broker acts as an intermediary.

Examples include:

```text
Apache Kafka

RabbitMQ

ActiveMQ
```

---

# Synchronous vs Asynchronous

| Synchronous | Asynchronous |
|------------|-------------|
| Request/Response | Event Driven |
| Caller Waits | Caller Continues |
| Easier Debugging | Better Scalability |
| Stronger Coupling | Looser Coupling |
| Immediate Result | Delayed Processing |

---

# Distributed System Challenges

Microservices create distributed systems.

Distributed systems introduce challenges not present in monoliths.

---

# Challenge 1: Network Failure

Method calls inside a monolith:

```java
service.execute()
```

usually succeed if the application is healthy.

Microservice calls:

```http
HTTP Requests
```

are vulnerable to:

```text
Network Failure
Timeouts
DNS Issues
Packet Loss
```

---

# Challenge 2: Latency

Communication across networks takes time.

Example:

```text
User Service
   ↓ 75ms
Project Service
   ↓ 120ms
Task Service
```

Latency accumulates.

---

# Challenge 3: Partial Failure

One service can fail while others remain healthy.

Example:

```text
User Service        Healthy

Project Service     Healthy

Task Service        Failed
```

The system becomes partially available.

---

# Challenge 4: Data Consistency

Multiple services often own separate databases.

Maintaining consistency becomes difficult.

---

# CAP Theorem

## What Is CAP Theorem?

CAP Theorem is one of the most important distributed systems concepts.

It states a distributed system cannot fully guarantee all three simultaneously:

```text
Consistency

Availability

Partition Tolerance
```

during a network partition.

---

# Consistency

All nodes see the same data at the same time.

---

# Availability

Every request receives a response.

---

# Partition Tolerance

The system continues functioning despite network partitions.

---

# CAP Trade-Off

When network failures occur:

```text
Choose Consistency

OR

Choose Availability
```

while maintaining partition tolerance.

---

# Why CAP Matters

Microservices are distributed systems.

Architectural decisions often involve CAP trade-offs.

---

# Consistency Models

## Strong Consistency

All users immediately see the latest data.

---

## Eventual Consistency

Data becomes consistent over time.

Common in:

```text
Microservices

Messaging Systems

Distributed Databases
```

---

# Eventual Consistency Example

```text
Project Created
       ↓
Project Service Updated
       ↓
Notification Service Updated Later
```

Temporary differences may exist.

Eventually the systems become consistent.

---

# End of Part 2

# Distributed Transactions

## Why Distributed Transactions Exist

In a monolithic application:

```text
Application
     ↓
Single Database
```

A single database transaction is usually sufficient.

Example:

```text
Create User
Create Profile
Create Settings
```

Everything succeeds or everything rolls back.

---

# The Microservices Problem

Consider:

```text
User Service
      ↓
User Database

Notification Service
      ↓
Notification Database
```

A single database transaction cannot span both services easily.

---

# Example Scenario

```text
Create User
      ↓
Store User
      ↓
Create Welcome Notification
```

What happens if:

```text
User Created Successfully

Notification Creation Fails
```

Now the system is partially updated.

---

# What Is a Distributed Transaction?

A Distributed Transaction is a transaction that spans multiple services or databases.

The goal is:

```text
Maintain Data Consistency
Across Multiple Services
```

---

# Traditional Solution: Two-Phase Commit (2PC)

## What Is Two-Phase Commit?

A coordination protocol where a central coordinator controls transaction execution.

Phases:

```text
Prepare Phase
      ↓
Commit Phase
```

---

# Problems with 2PC

Although theoretically consistent:

```text
Slow

Complex

Not Highly Scalable

Coordinator Dependency
```

Most modern microservice systems avoid 2PC.

---

# Saga Pattern

## What Is Saga Pattern?

Saga Pattern is the preferred approach for distributed transactions in microservices.

Instead of:

```text
One Large Transaction
```

we use:

```text
Multiple Local Transactions
```

coordinated through events or commands.

---

# Saga Workflow Example

```text
Create Project
       ↓
Project Service Success
       ↓
Create Default Tasks
       ↓
Task Service Success
       ↓
Send Notification
       ↓
Notification Service Success
```

Each service commits its own transaction.

---

# Compensating Transactions

If failure occurs:

```text
Project Created
Task Creation Failed
```

a compensating action may run:

```text
Delete Project
```

to restore consistency.

---

# Benefits of Saga Pattern

## Better Scalability

No global transaction lock.

---

## Better Availability

Services remain autonomous.

---

## Better Microservice Compatibility

Aligns with Database Per Service principles.

---

# Saga Coordination Models

Two common approaches exist:

```text
Choreography

Orchestration
```

---

# Choreography

## What Is Choreography?

Services coordinate through events.

Example:

```text
Project Created Event
        ↓
Task Service Reacts
        ↓
Task Created Event
        ↓
Notification Service Reacts
```

No central controller exists.

---

# Advantages

- Loosely coupled
- Highly scalable
- Event-driven

---

# Limitations

- Harder debugging
- Complex event flows

---

# Orchestration

## What Is Orchestration?

A central orchestrator controls workflow execution.

Example:

```text
Orchestrator
      ↓
Project Service
      ↓
Task Service
      ↓
Notification Service
```

---

# Advantages

- Easier monitoring
- Easier debugging
- Central control

---

# Limitations

- Additional component
- More coupling

---

# Fault Tolerance

## What Is Fault Tolerance?

Fault tolerance allows systems to continue operating despite failures.

Microservices assume:

```text
Failures Are Normal
```

not exceptional.

---

# Building Resilient Systems

Systems should anticipate:

```text
Network Failures

Service Failures

Timeouts

Database Failures
```

---

# Retry Pattern

## What Is Retry?

Temporary failures may succeed later.

Example:

```text
Timeout
    ↓
Retry
    ↓
Success
```

---

# Retry Considerations

Retries should be:

```text
Limited

Controlled

Backed Off
```

to avoid overwhelming systems.

---

# Exponential Backoff

Instead of:

```text
Retry Immediately
Retry Immediately
Retry Immediately
```

use:

```text
1 Second
2 Seconds
4 Seconds
8 Seconds
```

between retries.

---

# Timeout Pattern

## Why Timeouts Matter

A service should not wait forever.

Bad:

```text
Infinite Waiting
```

Good:

```text
Request Timeout
After Defined Duration
```

---

# Benefits

- Better responsiveness
- Faster recovery
- Improved stability

---

# Circuit Breaker Pattern

## What Is a Circuit Breaker?

A Circuit Breaker prevents repeated calls to a failing service.

---

# Real-World Analogy

Think of an electrical circuit breaker.

When overload occurs:

```text
Circuit Opens
```

preventing further damage.

---

# States of a Circuit Breaker

```text
Closed
  ↓
Open
  ↓
Half Open
```

---

# Closed State

Requests flow normally.

---

# Open State

Requests fail immediately.

The failing service is temporarily avoided.

---

# Half-Open State

Small number of requests are allowed.

If successful:

```text
Circuit Closes
```

Otherwise:

```text
Circuit Opens Again
```

---

# Benefits

- Prevents cascading failures.
- Allows recovery.
- Improves stability.

---

# Bulkhead Pattern

## What Is Bulkhead Pattern?

Bulkheads isolate failures.

Inspired by ship compartments.

Example:

```text
Service A Resources

Service B Resources

Service C Resources
```

Failure in one area should not consume all resources.

---

# Benefits

- Better isolation
- Improved reliability
- Reduced blast radius

---

# Fallback Pattern

## What Is Fallback?

When a service fails:

```text
Return Alternative Response
```

Examples:

```text
Cached Result

Default Value

Graceful Error Message
```

---

# Observability

## What Is Observability?

Observability is the ability to understand what is happening inside a system.

---

# Why Observability Matters

Microservices increase complexity.

Instead of:

```text
One Application
```

you may have:

```text
10 Services

20 Services

50 Services
```

Troubleshooting becomes harder.

---

# Three Pillars of Observability

```text
Logs

Metrics

Tracing
```

---

# Logging

Logs capture events.

Example:

```text
User Created

Task Updated

Notification Sent
```

---

# Centralized Logging

Instead of:

```text
Separate Service Logs
```

logs are aggregated centrally.

Benefits:

```text
Unified Search

Better Troubleshooting

Operational Visibility
```

---

# Metrics

Metrics provide numerical measurements.

Examples:

```text
Request Count

Error Rate

Response Time

CPU Usage

Memory Usage
```

---

# Distributed Tracing

## What Is Distributed Tracing?

Tracing follows requests across services.

Example:

```text
User Request
      ↓
Gateway
      ↓
User Service
      ↓
Project Service
      ↓
Task Service
```

Tracing reveals the complete path.

---

# Correlation ID

A Correlation ID uniquely identifies a request.

Example:

```text
REQUEST-12345
```

This ID appears across all service logs.

---

# Benefits

- Easier troubleshooting
- Better visibility
- Faster root-cause analysis

---

# Security in Microservices

Microservice security is more complex than monolithic security.

---

# Authentication

Typically handled through:

```text
JWT

OAuth 2.0

OpenID Connect
```

---

# Authorization

Determines:

```text
Which User

Can Access

Which Resource
```

---

# Secure Communication

Services should communicate securely.

Common approaches:

```text
HTTPS

TLS

Token Validation
```

---

# Secrets Management

Sensitive information should not be stored directly in source code.

Examples:

```text
Passwords

API Keys

Certificates

Database Credentials
```

---

# Deployment Considerations

Microservices are independently deployable.

Deployment strategies include:

```text
Rolling Deployment

Blue-Green Deployment

Canary Deployment
```

---

# Rolling Deployment

Instances are updated gradually.

Benefits:

```text
Reduced Downtime
```

---

# Blue-Green Deployment

Two environments exist.

```text
Blue = Current

Green = New
```

Traffic switches after validation.

---

# Canary Deployment

New version receives limited traffic first.

Benefits:

```text
Reduced Risk
```

---

# Microservices and Kubernetes

Microservices are commonly deployed using Kubernetes.

Kubernetes provides:

```text
Container Orchestration

Scaling

Self-Healing

Service Discovery

Load Balancing
```

---

# Enterprise Usage

Microservices are widely used by:

- Netflix
- Amazon
- Uber
- Spotify
- Banking Platforms
- SaaS Products

because they support:

```text
Scalability

Resilience

Independent Releases
```

---

# WorkSphere Service Landscape

Potential WorkSphere microservices may include:

```text
Authentication Service

User Service

Project Service

Task Service

Notification Service

Reporting Service
```

Supporting infrastructure may include:

```text
API Gateway

Service Registry

Configuration Server

Logging Platform

Monitoring Platform
```

---

# WorkSphere Communication Example

```text
User Creates Project
        ↓
Project Service
        ↓
Project Created Event
        ↓
Task Service
        ↓
Create Default Tasks
        ↓
Notification Service
        ↓
Notify Users
```

This demonstrates a typical event-driven workflow.

---

# Summary

In this chapter you learned:

- Monolithic Architecture
- Microservices Architecture
- Domain-Driven Design
- Bounded Contexts
- Service Boundaries
- Database Per Service
- API Gateway
- Service Discovery
- Centralized Configuration
- Distributed Communication
- CAP Theorem
- Eventual Consistency
- Saga Pattern
- Choreography vs Orchestration
- Retry Pattern
- Circuit Breaker Pattern
- Bulkhead Pattern
- Observability
- Distributed Tracing
- Security Considerations
- Deployment Strategies

Microservices enable scalable, maintainable, and independently deployable enterprise systems, but they introduce distributed-system challenges that require architectural discipline and operational maturity.

---

# Revision Checklist

Before moving ahead, ensure you can:

- Explain Microservices Architecture.
- Compare Monoliths and Microservices.
- Explain Bounded Contexts.
- Identify service boundaries.
- Explain Database Per Service.
- Explain API Gateway.
- Explain Service Discovery.
- Explain CAP Theorem.
- Explain Eventual Consistency.
- Explain Saga Pattern.
- Explain Circuit Breaker Pattern.
- Explain Distributed Tracing.
- Explain Observability.
- Explain WorkSphere service boundaries.

---

# Definition of Done

You have completed this chapter when you can:

✓ Explain Microservices confidently.

✓ Design service boundaries.

✓ Explain API Gateway and Service Discovery.

✓ Explain CAP Theorem.

✓ Explain Saga Pattern.

✓ Explain resilience patterns.

✓ Explain observability requirements.

✓ Discuss Microservices security.

✓ Relate Microservices concepts to WorkSphere.

✓ Answer enterprise-level Microservices interview questions.

---

# What's Next?

19_Distributed_Communication_and_Service_Integration.md

In the next chapter you will learn:

- OpenFeign
- Service-to-Service Communication
- Event-Driven Architecture
- Apache Kafka
- Messaging Patterns
- Event Streaming
- Integration Patterns
- Distributed Communication in WorkSphere

This chapter moves from Microservices Architecture into actual communication mechanisms between distributed services.