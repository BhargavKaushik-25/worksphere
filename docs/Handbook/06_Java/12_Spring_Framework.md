# 12_Spring_Framework

## Chapter Information

**Chapter Number:** 12

**Chapter Name:** Spring Framework

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

### Next Chapter

- Spring Boot

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Explain what Spring Framework is.
- Understand why Spring was created.
- Explain Inversion of Control (IoC).
- Explain Dependency Injection (DI).
- Understand Spring Beans.
- Understand the Spring Container.
- Configure Spring applications.
- Explain Bean Lifecycle.
- Understand Spring Modules.
- Understand enterprise usage of Spring.
- Explain Spring concepts confidently in interviews.

---

# Introduction to Spring Framework

## What Is Spring Framework?

Spring Framework is the most widely used enterprise Java framework for building scalable, maintainable, and testable applications.

Spring simplifies enterprise application development by providing:

- Dependency Injection
- Inversion of Control
- Transaction Management
- Security Integration
- Database Integration
- Testing Support
- Web Development Support

---

# Full Form

Spring itself is not an acronym.

It is a framework designed to simplify Java enterprise development.

---

# Why Spring Exists

Before Spring, Java enterprise development commonly used:

```text
Enterprise JavaBeans (EJB)
```

Developers faced:

- Complex configuration
- Heavy infrastructure code
- Difficult testing
- Tight coupling
- High learning curve

Spring solved these problems by promoting simplicity and loose coupling.

---

# Problems Spring Solves

Without Spring:

```java
public class UserService {

    private UserRepository repository =
            new UserRepository();

}
```

Problems:

- Tight coupling
- Difficult testing
- Difficult maintenance
- Hard dependency management

Spring manages dependencies automatically.

---

# Real-World Analogy

Imagine building a house.

Without Spring:

```text
You build every component yourself.
```

With Spring:

```text
A professional contractor manages
the wiring,
plumbing,
and infrastructure.
```

You focus on business requirements.

Spring handles infrastructure.

---

# What Is a Framework?

A framework provides a predefined structure for building applications.

Instead of creating everything from scratch:

```text
Framework
     ↓
Provides Foundation
     ↓
Developer adds business logic
```

Spring is a framework.

Your WorkSphere code becomes the application built on top of it.

---

# Spring Philosophy

Spring promotes:

## Loose Coupling

Components should depend on abstractions rather than concrete implementations.

---

## Testability

Applications should be easy to test.

---

## Maintainability

Code should be easier to modify.

---

## Reusability

Components should be reusable.

---

## Modularity

Applications should be divided into focused components.

---

# Understanding Coupling

## What Is Tight Coupling?

Example:

```java
public class UserService {

    private UserRepository repository =
            new UserRepository();

}
```

UserService directly creates UserRepository.

Both classes become tightly connected.

---

# Problems with Tight Coupling

- Difficult testing
- Difficult upgrades
- Difficult replacements
- Reduced flexibility

---

# What Is Loose Coupling?

Dependencies are provided externally.

Example:

```java
public class UserService {

    private UserRepository repository;

}
```

Dependency is supplied separately.

This approach is called Dependency Injection.

---

# Inversion of Control (IoC)

## What Is IoC?

IoC stands for:

```text
Inversion of Control
```

---

# Traditional Control

Application code creates and manages dependencies.

Example:

```java
UserRepository repository =
        new UserRepository();
```

Application controls everything.

---

# Inverted Control

Spring controls object creation.

Spring provides required objects to your application.

Example:

```text
Spring Creates Object
        ↓
Spring Manages Object
        ↓
Spring Provides Object
```

This is Inversion of Control.

---

# Why IoC Exists

Benefits:

- Reduced coupling
- Easier testing
- Better maintainability
- Better scalability

---

# Dependency Injection (DI)

## What Is Dependency Injection?

Dependency Injection is a technique used to implement Inversion of Control.

Dependencies are provided from outside rather than being created internally.

---

# Simple Example

Without DI:

```java
UserService

creates

UserRepository
```

With DI:

```text
Spring
   ↓
creates UserRepository
   ↓
injects into UserService
```

---

# Benefits of Dependency Injection

- Loose coupling
- Easier testing
- Better maintainability
- Cleaner architecture

---

# Types of Dependency Injection

## Constructor Injection

Recommended approach.

Example:

```java
public class UserService {

    private final UserRepository repository;

    public UserService(
            UserRepository repository
    ) {
        this.repository = repository;
    }

}
```

---

## Setter Injection

Example:

```java
public void setRepository(
        UserRepository repository
) {
    this.repository = repository;
}
```

---

## Field Injection

Example:

```java
@Autowired
private UserRepository repository;
```

Common but generally less preferred than constructor injection.

---

# Spring Container

## What Is the Spring Container?

The Spring Container is the core component of Spring Framework.

Responsibilities:

- Creates objects
- Manages objects
- Injects dependencies
- Controls lifecycle

---

# Spring Container Responsibilities

```text
Create Beans
↓
Manage Beans
↓
Inject Dependencies
↓
Destroy Beans
```

---

# What Is a Bean?

A Bean is an object managed by Spring.

Example:

```java
@Service
public class UserService {

}
```

UserService becomes a Spring Bean.

---

# Bean Lifecycle

A bean passes through several stages.

```text
Instantiation
      ↓
Dependency Injection
      ↓
Initialization
      ↓
Usage
      ↓
Destruction
```

---

# Bean Scopes

Spring supports multiple bean scopes.

---

## Singleton

Default scope.

```text
One Instance
Per Spring Container
```

---

## Prototype

```text
New Object
Every Request
```

---

## Request Scope

One object per HTTP request.

---

## Session Scope

One object per user session.

---

# Spring Configuration

Spring needs configuration information.

Historically:

```xml
applicationContext.xml
```

was commonly used.

Modern Spring prefers:

```java
@Configuration
```

based configuration.

---

# Important Spring Annotations

## @Component

General-purpose Spring Bean.

Example:

```java
@Component
public class EmailService {

}
```

---

## @Service

Business logic layer.

Example:

```java
@Service
public class UserService {

}
```

---

## @Repository

Database access layer.

Example:

```java
@Repository
public class UserRepository {

}
```

---

## @Controller

Web layer.

Example:

```java
@Controller
public class UserController {

}
```

---

## @Configuration

Defines configuration classes.

---

## @Bean

Registers objects manually.

Example:

```java
@Bean
public EmailService emailService() {
    return new EmailService();
}
```

---

# Spring Modules

Spring consists of multiple modules.

---

## Spring Core

Provides:

- IoC
- DI
- Bean Management

---

## Spring Context

Provides application context features.

---

## Spring AOP

AOP stands for:

```text
Aspect-Oriented Programming
```

Used for:

- Logging
- Security
- Monitoring

---

## Spring JDBC

Database connectivity support.

---

## Spring ORM

ORM stands for:

```text
Object Relational Mapping
```

Supports:

- Hibernate
- JPA

---

## Spring Web

Used for web applications and REST APIs.

---

## Spring Test

Provides testing support.

---

# Spring Architecture Overview

```text
Application Code
        ↓
Spring Framework
        ↓
Database
Web Layer
Security
Transactions
Logging
```

Spring connects all application components together.

---

# Aspect-Oriented Programming (AOP)

## What Is AOP?

AOP separates cross-cutting concerns from business logic.

Examples:

- Logging
- Security
- Audit Tracking
- Monitoring

---

# Without AOP

Repeated code appears everywhere.

---

# With AOP

Common functionality is centralized.

Benefits:

- Cleaner code
- Better maintainability

---

# Transaction Management

## What Is a Transaction?

A transaction is a unit of work.

Example:

```text
Withdraw Money
Deposit Money
```

Both must succeed together.

---

# Spring Transaction Support

Spring simplifies transactions using:

```java
@Transactional
```

This becomes important later with Spring Boot and JPA.

---

# Enterprise Benefits of Spring

## Loose Coupling

Applications become easier to maintain.

---

## Testability

Dependencies can be mocked easily.

---

## Productivity

Developers focus on business logic.

---

## Scalability

Supports enterprise-grade systems.

---

## Ecosystem

Large ecosystem and community support.

---

# Spring in Enterprise Applications

Used by:

- Banking Systems
- Insurance Platforms
- E-Commerce Systems
- Microservices
- Cloud Applications
- SaaS Products

Spring remains the dominant enterprise Java ecosystem.

---

# WorkSphere Relevance

WorkSphere backend development will heavily rely on Spring.

Potential examples:

```text
User Service
Project Service
Task Service
Notification Service
Authentication Service
```

These services will become Spring-managed components.

Future concepts such as:

```text
Spring Boot
Spring Data JPA
Spring Security
Microservices
```

all build upon Spring Framework fundamentals.

---

# Common Mistakes

## Confusing IoC and DI

IoC is the principle.

DI is the implementation technique.

---

## Using Field Injection Everywhere

Constructor injection is generally preferred.

---

## Creating Objects Manually

Avoid:

```java
new UserRepository()
```

inside Spring-managed services.

---

## Ignoring Bean Scopes

Can cause memory and lifecycle issues.

---

# Interview Questions

## Beginner

### What is Spring Framework?

### Why was Spring created?

### What is a Framework?

### What is a Bean?

---

## Intermediate

### What is IoC?

### What is DI?

### Constructor Injection vs Setter Injection?

### What is Spring Container?

---

## Advanced

### Explain Bean Lifecycle.

### Explain Bean Scopes.

### What is AOP?

### How does Spring manage dependencies?

---

## Enterprise

### Why is Spring popular?

### How does Spring improve maintainability?

### How does Spring improve testing?

### Why is Dependency Injection important in large applications?

---

# Summary

In this chapter you learned:

- Spring Framework Fundamentals
- IoC
- Dependency Injection
- Spring Container
- Beans
- Bean Lifecycle
- Bean Scopes
- Spring Annotations
- AOP
- Transaction Management
- Enterprise Usage

These concepts form the foundation for all modern Spring-based applications.

---

# Revision Checklist

Before moving ahead, ensure you can:

- Explain Spring Framework.
- Explain IoC.
- Explain Dependency Injection.
- Explain Spring Container.
- Explain Beans.
- Explain Bean Lifecycle.
- Explain Bean Scopes.
- Use core Spring annotations.
- Explain AOP.
- Explain enterprise benefits.

---

# Definition of Done

You have completed this chapter when you can:

✓ Explain Spring Framework confidently.

✓ Explain IoC and DI clearly.

✓ Understand Spring Beans.

✓ Understand Bean Lifecycle.

✓ Understand Bean Scopes.

✓ Use Spring annotations.

✓ Explain AOP fundamentals.

✓ Understand enterprise applications of Spring.

✓ Answer Spring Framework interview questions.

---

# What's Next?

13_Spring_Boot.md

In the next chapter you will learn:

- What Spring Boot is
- Why Spring Boot exists
- Auto Configuration
- Starter Dependencies
- Spring Boot Architecture
- Building REST APIs
- Configuration Management
- Application Properties
- Production Readiness

This chapter begins actual enterprise application development similar to WorkSphere's backend architecture.