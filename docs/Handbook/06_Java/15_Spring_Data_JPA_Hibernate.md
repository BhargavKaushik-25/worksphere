# 15_Spring_Data_JPA_Hibernate

## Chapter Information

**Chapter Number:** 15

**Chapter Name:** Spring Data JPA & Hibernate

### Prerequisites

- Java Fundamentals
- OOP
- Exception Handling
- Collections
- Generics
- Lambda Expressions
- Streams
- Optional
- Concurrency
- JVM Fundamentals
- Advanced Java
- Spring Framework
- Spring Boot
- REST API Development

### Next Chapter

- Spring Security

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Explain persistence in enterprise applications.
- Understand Object Relational Mapping (ORM).
- Explain JPA.
- Explain Hibernate.
- Create Entities.
- Configure entity relationships.
- Work with repositories.
- Understand CRUD operations.
- Use Query Methods.
- Understand JPQL.
- Understand Pagination and Sorting.
- Apply JPA best practices.
- Explain persistence concepts during interviews.

---

# Introduction to Persistence

## What Is Persistence?

Persistence is the process of storing application data permanently.

Without persistence:

```text
Application Stops
        ↓
Data Lost
```

With persistence:

```text
Application Stops
        ↓
Data Remains
```

Examples:

- Users
- Projects
- Tasks
- Teams
- Reports

---

# Why Persistence Exists

Enterprise applications must store information beyond application execution.

Example:

```text
User Creates Project
```

That project must still exist tomorrow.

Persistence solves this problem.

---

# Real-World Analogy

Think of a notebook.

Without persistence:

```text
Write On Whiteboard
Power Off
Everything Gone
```

With persistence:

```text
Write In Notebook
Information Saved
```

Databases act like the notebook of enterprise applications.

---

# What Is ORM?

ORM stands for:

```text
Object Relational Mapping
```

---

# Why ORM Exists

Java works with:

```java
Objects
```

Databases work with:

```text
Tables
Rows
Columns
```

ORM bridges this gap.

---

# ORM Mapping Example

Java Object:

```java
User
```

Database Table:

```text
users
```

ORM automatically translates between them.

---

# Benefits of ORM

- Less SQL boilerplate
- Faster development
- Better maintainability
- Object-oriented programming model

---

# What Is JPA?

JPA stands for:

```text
Java Persistence API
```

JPA is a specification.

It defines rules for persistence in Java applications.

---

# Important Clarification

JPA is NOT an implementation.

JPA defines:

```text
What must be done
```

Implementations decide:

```text
How it is done
```

---

# Popular JPA Implementations

Examples:

```text
Hibernate
EclipseLink
OpenJPA
```

Most Spring Boot projects use:

```text
Hibernate
```

---

# What Is Hibernate?

Hibernate is the most widely used JPA implementation.

It provides:

- ORM
- Query Support
- Caching
- Lazy Loading
- Transaction Integration

Hibernate executes the actual persistence operations.

---

# Relationship Between Spring Data JPA and Hibernate

```text
Application
      ↓
Spring Data JPA
      ↓
JPA Specification
      ↓
Hibernate
      ↓
Database
```

Spring Data JPA simplifies data access.

Hibernate performs the underlying ORM work.

---

# Entity

## What Is an Entity?

An Entity represents a database table.

Example:

```java
@Entity
public class User {

}
```

---

# Entity Mapping Example

```java
@Entity
@Table(name = "users")
public class User {

}
```

Mapping:

```text
User Class
      ↓
users Table
```

---

# Primary Key

Every entity requires a unique identifier.

Example:

```java
@Id
private Long id;
```

---

# Auto Generated IDs

Example:

```java
@Id
@GeneratedValue
private Long id;
```

Database automatically generates IDs.

---

# Column Mapping

Example:

```java
@Column(name = "email")
private String email;
```

Maps Java field to database column.

---

# Common Entity Annotations

## @Entity

Marks a class as an entity.

---

## @Table

Maps entity to a table.

---

## @Id

Defines primary key.

---

## @GeneratedValue

Generates IDs automatically.

---

## @Column

Maps columns.

---

## @Transient

Excludes field from database persistence.

---

# Entity Lifecycle

```text
Transient
    ↓
Persistent
    ↓
Detached
    ↓
Removed
```

Understanding lifecycle helps explain Hibernate behavior.

---

# Relationships

Enterprise applications rarely have isolated tables.

Relationships are essential.

---

# One-to-One Relationship

Example:

```text
User
    ↔
UserProfile
```

Annotation:

```java
@OneToOne
```

---

# One-to-Many Relationship

Example:

```text
Project
      ↓
Many Tasks
```

Annotation:

```java
@OneToMany
```

---

# Many-to-One Relationship

Example:

```text
Many Tasks
      ↓
One Project
```

Annotation:

```java
@ManyToOne
```

---

# Many-to-Many Relationship

Example:

```text
Users
 ↔
 Roles
```

Annotation:

```java
@ManyToMany
```

---

# Fetch Types

JPA loads related data in different ways.

---

## Eager Loading

Loads related data immediately.

```java
FetchType.EAGER
```

---

## Lazy Loading

Loads related data only when needed.

```java
FetchType.LAZY
```

Generally preferred for performance.

---

# Repository Layer

## What Is a Repository?

Repository provides database operations.

Spring Data JPA generates much of the implementation automatically.

---

# JpaRepository

Example:

```java
public interface UserRepository
        extends JpaRepository<
                User,
                Long
        > {

}
```

---

# Built-In CRUD Operations

Available automatically:

```java
save()

findById()

findAll()

delete()

deleteById()

existsById()
```

No implementation required.

---

# Save Operation

Example:

```java
repository.save(user);
```

Creates or updates data.

---

# Find By ID

Example:

```java
Optional<User> user =
        repository.findById(1L);
```

Returns Optional.

---

# Find All

Example:

```java
List<User> users =
        repository.findAll();
```

---

# Delete

Example:

```java
repository.deleteById(1L);
```

---

# Query Methods

Spring Data JPA can generate queries automatically.

---

# Example

```java
findByEmail(String email)
```

Spring generates the SQL automatically.

---

# More Examples

```java
findByName()

findByStatus()

findByProjectId()

findByCreatedDate()
```

Useful for common business requirements.

---

# Derived Queries

Method names become queries.

Example:

```java
findByEmailAndStatus()
```

Generates appropriate SQL automatically.

---

# JPQL

## What Is JPQL?

JPQL stands for:

```text
Java Persistence Query Language
```

JPQL works with:

```text
Entities
```

instead of tables.

---

# Example

JPQL:

```java
SELECT u
FROM User u
```

SQL:

```sql
SELECT *
FROM users
```

JPQL operates on objects.

---

# Custom Queries

Example:

```java
@Query(
    "SELECT u FROM User u
     WHERE u.status='ACTIVE'"
)
```

Useful for complex requirements.

---

# Pagination

## Why Pagination Exists

Large datasets are inefficient.

Bad:

```text
Load 1 Million Rows
```

Good:

```text
Load 20 Rows
```

at a time.

---

# Pageable

Example:

```java
Pageable pageable =
        PageRequest.of(
                0,
                20
        );
```

---

# Page

Example:

```java
Page<User> users =
        repository.findAll(pageable);
```

Provides:

- Content
- Total Pages
- Total Elements

---

# Sorting

Example:

```java
Sort.by("name")
```

or

```java
Sort.by(
    Sort.Direction.DESC,
    "createdDate"
)
```

---

# Transactions

Database operations should be consistent.

---

# What Is a Transaction?

A transaction is a unit of work.

Example:

```text
Create Project
Create Tasks
Send Notification
```

Everything succeeds together.

Or everything fails together.

---

# @Transactional

Spring manages transactions using:

```java
@Transactional
```

Example:

```java
@Transactional
public void createProject() {

}
```

---

# Hibernate Features

## Dirty Checking

Hibernate automatically detects changes.

Example:

```java
user.setName("Bhargav");
```

Changes can be persisted automatically.

---

## First-Level Cache

Hibernate stores frequently accessed entities in memory during a session.

Improves performance.

---

## SQL Generation

Hibernate converts:

```java
repository.save(user)
```

into SQL automatically.

---

# Performance Considerations

## Avoid N+1 Queries

Common JPA performance issue.

---

## Prefer Lazy Loading

Unless eager loading is required.

---

## Use Pagination

For large datasets.

---

## Fetch Only Required Data

Avoid loading unnecessary objects.

---

# Enterprise Usage

Spring Data JPA and Hibernate are used in:

- Banking Systems
- ERP Systems
- E-Commerce Platforms
- Insurance Systems
- SaaS Applications
- Enterprise Microservices

They remain the most common persistence technologies in the Spring ecosystem.

---

# WorkSphere Relevance

Potential WorkSphere Entities:

```text
User

Project

Task

Team

Role

Notification
```

Repositories:

```text
UserRepository

ProjectRepository

TaskRepository
```

Spring Data JPA will become the primary persistence mechanism in WorkSphere backend development.

---

# Common Mistakes

## Using Eager Loading Everywhere

Can cause performance issues.

---

## Ignoring Transactions

Can create inconsistent data.

---

## Returning Entities Directly

Prefer DTOs in APIs.

---

## Loading Entire Tables

Use pagination.

---

# Interview Questions

## Beginner

### What is JPA?

### What is Hibernate?

### What is ORM?

### What is an Entity?

---

## Intermediate

### JPA vs Hibernate?

### What is JpaRepository?

### One-to-Many vs Many-to-One?

### What is Lazy Loading?

---

## Advanced

### Explain Entity Lifecycle.

### Explain JPQL.

### What is Dirty Checking?

### Explain N+1 Query Problem.

---

## Enterprise

### Why is Spring Data JPA popular?

### How should large datasets be handled?

### Why is pagination important?

### What persistence challenges may exist in WorkSphere?

---

# Summary

In this chapter you learned:

- Persistence Fundamentals
- ORM
- JPA
- Hibernate
- Entities
- Relationships
- Repositories
- Query Methods
- JPQL
- Pagination
- Transactions
- Performance Considerations

These concepts form the foundation of data persistence in modern Spring Boot applications.

---

# Revision Checklist

Before moving ahead, ensure you can:

- Explain ORM.
- Explain JPA.
- Explain Hibernate.
- Create Entities.
- Create Repositories.
- Use CRUD operations.
- Explain Relationships.
- Explain Lazy Loading.
- Explain JPQL.
- Explain Transactions.
- Explain Pagination.

---

# Definition of Done

You have completed this chapter when you can:

✓ Explain JPA and Hibernate.

✓ Create entities and repositories.

✓ Use CRUD operations confidently.

✓ Understand relationships.

✓ Use Pagination and Sorting.

✓ Explain transactions.

✓ Explain JPQL.

✓ Understand enterprise persistence patterns.

✓ Answer JPA and Hibernate interview questions.

---

# What's Next?

16_Spring_Security.md

In the next chapter you will learn:

- Authentication
- Authorization
- Spring Security
- JWT (JSON Web Token)
- Role-Based Access Control (RBAC)
- Password Encoding
- Security Filters
- API Protection

This chapter introduces one of the most important enterprise backend topics and is directly relevant to WorkSphere authentication and authorization architecture.