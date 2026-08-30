# 27_System_Design_Fundamentals

## Chapter Information

**Chapter Number:** 27

**Chapter Name:** System Design Fundamentals

### Prerequisites

- Java Fundamentals
- Spring Framework
- Spring Boot
- REST API Development
- Spring Data JPA & Hibernate
- Spring Security
- Backend Testing
- Microservices Architecture
- Distributed Communication
- Apache Kafka
- Redis and Caching
- Docker and Containerization
- Kubernetes and Container Orchestration
- CI/CD and DevOps Automation
- AWS Cloud Fundamentals
- Monitoring, Logging and Observability

### Next Chapter

- Scalability and Performance Engineering

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Explain System Design.
- Analyze Functional Requirements.
- Analyze Non-Functional Requirements.
- Understand Scalability.
- Understand Availability.
- Understand Reliability.
- Understand Load Balancing.
- Understand Database Design Trade-offs.
- Design basic distributed systems.
- Discuss system design during interviews.
- Apply design thinking to WorkSphere.

---

# Introduction

## What Is System Design?

System Design is the process of defining the architecture, components, interfaces, and dataflow of a software system.

It answers questions such as:

```text
How will the system work?

How will it scale?

How will it remain available?

How will it perform under load?
```

---

# Why System Design Matters

Good code alone is not enough.

Applications must also be:

```text
Scalable

Reliable

Maintainable

Secure

Observable
```

System design helps achieve those goals.

---

# Real-World Analogy

Imagine constructing a city.

Before building:

```text
Roads

Water Supply

Electricity

Transportation
```

must be planned.

System design performs similar planning for software systems.

---

# System Design Process

Typical flow:

```text
Requirements
      ↓
Architecture
      ↓
Components
      ↓
Data Flow
      ↓
Scalability
      ↓
Operations
```

---

# Requirements Analysis

System design starts with requirements.

Two categories are important:

```text
Functional Requirements

Non-Functional Requirements
```

---

# Functional Requirements

## What Are Functional Requirements?

Functional requirements describe:

```text
What The System Must Do
```

---

# Example

WorkSphere may support:

```text
User Registration

Authentication

Project Creation

Task Management

Notifications
```

These are functional requirements.

---

# Non-Functional Requirements

## What Are Non-Functional Requirements?

Non-functional requirements describe:

```text
How Well The System Must Perform
```

---

# Examples

```text
Scalability

Security

Availability

Latency

Reliability
```

---

# Example Questions

```text
How Many Users?

How Fast Must Responses Be?

How Much Downtime Is Allowed?
```

---

# Capacity Planning

Before designing systems, estimate usage.

Examples:

```text
Users

Requests

Storage

Traffic
```

---

# Why Capacity Planning Matters

Design choices depend on scale.

A system serving:

```text
100 Users
```

is very different from one serving:

```text
10 Million Users
```

---

# System Design Building Blocks

Core components commonly include:

```text
Clients

APIs

Services

Databases

Caches

Message Brokers
```

---

# Clients

Clients consume services.

Examples:

```text
Web Applications

Mobile Applications

External APIs
```

---

# APIs

APIs act as communication interfaces.

Example:

```text
REST APIs

GraphQL APIs
```

---

# Services

Business logic typically resides here.

Examples:

```text
Authentication Service

User Service

Project Service
```

---

# Databases

Store information permanently.

Examples:

```text
PostgreSQL

MySQL

MongoDB
```

---

# Caches

Improve performance.

Examples:

```text
Redis

In-Memory Caches
```

---

# Message Brokers

Support asynchronous communication.

Examples:

```text
Kafka

RabbitMQ
```

---

# Scalability

## What Is Scalability?

Scalability is the ability of a system to handle increasing load.

---

# Vertical Scaling

Increase resources.

Example:

```text
More CPU

More Memory
```

for the same server.

---

# Advantages

Simple to implement.

---

# Limitations

Physical limits eventually exist.

---

# Horizontal Scaling

Add additional servers.

Example:

```text
Server 1

Server 2

Server 3
```

---

# Advantages

Supports larger growth.

---

# Common Modern Approach

Most cloud-native systems prefer:

```text
Horizontal Scaling
```

---

# Availability

## What Is Availability?

Availability measures whether a system is accessible and operational.

---

# Example

```text
99.9% Uptime
```

indicates high availability.

---

# Improving Availability

Strategies include:

```text
Redundancy

Failover

Multiple Instances

Load Balancing
```

---

# Reliability

## What Is Reliability?

Reliability measures whether a system performs correctly over time.

---

# Reliable Systems

Reliable systems:

```text
Produce Correct Results

Recover From Failures

Remain Stable
```

---

# Fault Tolerance

## What Is Fault Tolerance?

The ability to continue operating despite failures.

Example:

```text
Server Failure
      ↓
Application Still Functions
```

---

# Load Balancing

## What Is Load Balancing?

Load balancing distributes traffic across multiple servers.

---

# Example

Without Load Balancing:

```text
Users
 ↓
Single Server
```

---

# With Load Balancing

```text
Users
 ↓
Load Balancer
 ↓
Server A

Server B

Server C
```

---

# Benefits

```text
Higher Availability

Better Performance

Fault Tolerance
```

---

# Stateless Design

## What Is Statelessness?

Each request contains