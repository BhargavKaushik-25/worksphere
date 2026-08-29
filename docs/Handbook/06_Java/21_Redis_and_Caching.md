# 21_Redis_and_Caching

## Chapter Information

**Chapter Number:** 21

**Chapter Name:** Redis and Caching

### Prerequisites

- Java Fundamentals
- OOP
- Exception Handling
- Collections
- Generics
- Modern Java
- Streams
- Optional
- Concurrency
- Spring Framework
- Spring Boot
- REST API Development
- Spring Data JPA & Hibernate
- Spring Security
- Backend Testing
- Microservices Architecture
- Distributed Communication and Service Integration
- Apache Kafka

### Next Chapter

- Docker and Containerization

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Explain caching fundamentals.
- Explain why caching exists.
- Understand Redis architecture.
- Configure Redis conceptually.
- Understand cache patterns.
- Understand cache consistency challenges.
- Understand distributed caching.
- Understand session storage.
- Understand rate limiting.
- Apply caching strategies in enterprise applications.
- Explain caching concepts during interviews.

---

# Introduction to Caching

## What Is Caching?

Caching is the practice of storing frequently used data in a fast-access storage layer to reduce retrieval time.

Instead of repeatedly retrieving data from a slower source:

```text
Database
API
Disk
Network Service
```

data is temporarily stored in a cache.

---

# Why Caching Exists

Consider the following request flow:

```text
Client
  ↓
API
  ↓
Database
```

Every request reaches the database.

Problems:

```text
Higher Latency
Database Load
Reduced Scalability
```

Caching helps reduce these problems.

---

# Real World Analogy

Imagine a library.

Without caching:

```text
Request Book
      ↓
Go To Storage Room
      ↓
Find Book
```

With caching:

```text
Popular Books
      ↓
Placed On Front Desk
      ↓
Instant Access
```

Redis often acts as the front desk for frequently used information.

---

# Benefits of Caching

## Faster Responses

Data can be served quickly.

---

## Reduced Database Load

Fewer database queries.

---

## Improved Scalability

Applications handle more users.

---

## Cost Efficiency

Reduced infrastructure pressure.

---

# What Is Redis?

Redis stands for:

```text
Remote Dictionary Server
```

Redis is an in-memory data structure store commonly used for:

- Caching
- Session Storage
- Rate Limiting
- Distributed Locks
- Messaging
- Counters

---

# Why Redis Is Popular

Redis stores data primarily in memory.

Memory access is significantly faster than database access.

Benefits:

```text
High Performance

Low Latency

Simple Data Structures

Wide Adoption
```

---

# Redis Architecture

Basic Architecture:

```text
Application
      ↓
Redis
      ↓
Database
```
