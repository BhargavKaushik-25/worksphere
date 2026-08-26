# 13_Spring_Boot

## Chapter Information

**Chapter Number:** 13

**Chapter Name:** Spring Boot

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

### Next Chapter

- REST API Development

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Explain what Spring Boot is.
- Understand why Spring Boot was created.
- Explain Spring Boot architecture.
- Understand Auto Configuration.
- Use Starter Dependencies.
- Create Spring Boot applications.
- Understand Embedded Servers.
- Manage configurations.
- Create enterprise applications using Spring Boot.
- Explain Spring Boot concepts confidently during interviews.

---

# Introduction to Spring Boot

## What Is Spring Boot?

Spring Boot is an opinionated framework built on top of the Spring Framework.

It simplifies application development by reducing configuration, setup complexity, and boilerplate code.

Spring Boot allows developers to build production-ready applications quickly.

---

# Why Spring Boot Exists

Before Spring Boot, developers needed to configure:

- XML files
- Application Contexts
- Server Deployments
- Dependency Management
- Bean Definitions

This setup process was often time-consuming.

Spring Boot simplifies these tasks through convention and automation.

---

# Problems Spring Boot Solves

Traditional Spring applications often required:

```text
Complex Setup
XML Configuration
Manual Dependency Management
External Server Deployment
```

Spring Boot solves these through:

```text
Auto Configuration
Starter Dependencies
Embedded Servers
Opinionated Defaults
```

---

# Real-World Analogy

Imagine buying a car.

Without Spring Boot:

```text
Buy Engine
Buy Tires
Buy Electronics
Assemble Yourself
```

With Spring Boot:

```text
Car Comes Pre-Assembled
Ready To Drive
```

Spring Boot provides sensible defaults so developers can focus on business logic.

---

# Spring vs Spring Boot

## Spring Framework

Provides:

- IoC Container
- Dependency Injection
- Bean Management
- AOP

Requires additional setup.

---

## Spring Boot

Provides:

- Spring Framework Features
- Auto Configuration
- Embedded Servers
- Production Tools
- Starter Dependencies

Reduces configuration overhead.

---

# Spring Boot Architecture

## High-Level Architecture

```text
Application
     ↓
Spring Boot
     ↓
Spring Framework
     ↓
JVM
```

Spring Boot builds on top of Spring Framework.

---

# Core Components

Spring Boot consists of:

```text
Starter Dependencies
Auto Configuration
Spring Boot CLI
Actuator
Embedded Server
```

---

# Starter Dependencies

## What Are Starter Dependencies?

Starter dependencies are preconfigured dependency packages.

Instead of manually adding many related libraries:

```text
Add One Starter
Get Everything Needed
```

---

# Example

Instead of manually managing:

```text
Spring Core
Spring Web
Jackson
Validation APIs
Logging
```

You use:

```xml
spring-boot-starter-web
```

---

# Common Starters

## Spring Web

```xml
spring-boot-starter-web
```

Provides:

- REST APIs
- Embedded Server
- Spring MVC

---

## Spring Data JPA

```xml
spring-boot-starter-data-jpa
```

Provides database integration.

---

## Spring Security

```xml
spring-boot-starter-security
```

Provides authentication and authorization.

---

## Testing

```xml
spring-boot-starter-test
```

Provides testing libraries.

---

# Auto Configuration

## What Is Auto Configuration?

Spring Boot automatically configures components based on available dependencies.

Example:

If:

```xml
spring-boot-starter-web
```

is present,

Spring Boot automatically configures:

- Dispatcher Servlet
- JSON Processing
- Web Components

without manual configuration.

---

# Why Auto Configuration Exists

Developers should not repeatedly configure common components.

Spring Boot provides sensible defaults.

---

# Benefits of Auto Configuration

- Faster development
- Less boilerplate code
- Easier maintenance
- Consistent configuration

---

# Embedded Servers

## What Are Embedded Servers?

Spring Boot applications contain their own web server.

Common servers include:

- Tomcat
- Jetty
- Undertow

---

# Traditional Deployment

```text
Build WAR
Deploy To Server
Configure Server
```

---

# Spring Boot Deployment

```text
Build JAR
Run JAR
Application Starts
```

Much simpler.

---

# Embedded Tomcat

Default embedded server:

```text
Apache Tomcat
```

Example:

```bash
java -jar application.jar
```

Application starts with Tomcat automatically.

---

# Spring Boot Application Structure

Typical application structure:

```text
Controller
     ↓
Service
     ↓
Repository
     ↓
Database
```

This aligns with enterprise architecture principles.

---

# Spring Boot Main Class

Example:

```java
@SpringBootApplication
public class WorkSphereApplication {

    public static void main(
            String[] args
    ) {

        SpringApplication.run(
                WorkSphereApplication.class,
                args
        );

    }

}
```

---

# @SpringBootApplication

## What Is @SpringBootApplication?

This annotation combines:

```java
@Configuration

@EnableAutoConfiguration

@ComponentScan
```

into one annotation.

---

# Component Scanning

Spring Boot automatically discovers components.

Examples:

```java
@Controller

@Service

@Repository

@Component
```

These become Spring-managed Beans.

---

# Application Configuration

Spring Boot supports configuration using:

```text
application.properties
```

or

```text
application.yml
```

---

# Example Configuration

```properties
server.port=8080

spring.application.name=worksphere
```

---

# YAML Configuration

Example:

```yaml
server:
  port: 8080

spring:
  application:
    name: worksphere
```

---

# Profiles

## What Are Profiles?

Profiles allow different configurations for different environments.

Example:

```text
Development
Testing
Production
```

---

# Example

```properties
spring.profiles.active=dev
```

---

# Logging Support

Spring Boot provides built-in logging support.

Common logging framework:

```text
Logback
```

Example:

```java
logger.info("User Created");
```

---

# Exception Handling

Spring Boot provides tools for centralized exception handling.

Example:

```java
@ControllerAdvice
```

and

```java
@ExceptionHandler
```

---

# Configuration Properties

Used for strongly typed configuration.

Example:

```java
@ConfigurationProperties
```

Allows mapping configuration values into Java objects.

---

# Spring Boot Actuator

## What Is Actuator?

Spring Boot Actuator provides:

- Health Checks
- Metrics
- Monitoring
- Environment Information

---

# Why Actuator Exists

Production systems require visibility into application behavior.

Actuator helps operators monitor applications.

---

# Example Endpoints

```text
/actuator/health

/actuator/info

/actuator/metrics
```

---

# Spring Boot Development Workflow

Typical workflow:

```text
Create Project
      ↓
Add Dependencies
      ↓
Create Entities
      ↓
Create Repositories
      ↓
Create Services
      ↓
Create Controllers
      ↓
Test APIs
      ↓
Deploy
```

---

# Advantages of Spring Boot

## Rapid Development

Less configuration.

---

## Productivity

Focus on business logic.

---

## Embedded Server

Simpler deployment.

---

## Large Ecosystem

Seamless integration with Spring projects.

---

## Production Readiness

Actuator and monitoring support included.

---

# Limitations of Spring Boot

## Hidden Configuration

Auto configuration can initially appear magical.

---

## Learning Curve

Many concepts exist beneath the abstraction.

---

## Memory Usage

Typically higher than lightweight frameworks.

---

# Enterprise Usage

Spring Boot is widely used for:

- REST APIs
- Microservices
- Enterprise Applications
- Cloud Systems
- SaaS Platforms
- Backend Services

It is currently one of the most popular Java backend technologies.

---

# WorkSphere Relevance

Spring Boot will form the foundation of WorkSphere's backend services.

Potential future services include:

```text
Authentication Service

User Service

Project Service

Task Service

Notification Service

Reporting Service
```

Spring Boot will provide:

- REST APIs
- Dependency Injection
- Database Access
- Security Integration
- Testing Support

for all WorkSphere backend modules.

---

# Common Mistakes

## Fighting Auto Configuration

Many beginners manually configure components unnecessarily.

---

## Ignoring Starter Dependencies

Starter dependencies simplify maintenance.

---

## Poor Package Structure

Can break component scanning.

---

## Hardcoding Configuration

Use configuration files instead.

---

# Interview Questions

## Beginner

### What is Spring Boot?

### Why was Spring Boot created?

### Difference between Spring and Spring Boot?

### What is a Starter Dependency?

---

## Intermediate

### What is Auto Configuration?

### What is Embedded Tomcat?

### What is @SpringBootApplication?

### How does Component Scanning work?

---

## Advanced

### Explain Spring Boot startup process.

### Explain Profiles.

### Explain Spring Boot Actuator.

### How does Auto Configuration work internally?

---

## Enterprise

### Why is Spring Boot popular?

### How does Spring Boot help microservices?

### How is Spring Boot deployed?

### How does Spring Boot improve developer productivity?

---

# Summary

In this chapter you learned:

- Spring Boot Fundamentals
- Starter Dependencies
- Auto Configuration
- Embedded Servers
- Configuration Management
- Profiles
- Logging
- Exception Handling
- Actuator
- Enterprise Usage

Spring Boot transforms Spring Framework into a rapid application development platform and serves as the foundation of modern Java backend engineering.

---

# Revision Checklist

Before moving ahead, ensure you can:

- Explain Spring Boot.
- Explain why Spring Boot exists.
- Explain Starter Dependencies.
- Explain Auto Configuration.
- Explain Embedded Servers.
- Use application properties.
- Use Profiles.
- Explain Actuator.
- Discuss enterprise benefits.

---

# Definition of Done

You have completed this chapter when you can:

✓ Explain Spring Boot confidently.

✓ Build a basic Spring Boot application.

✓ Understand Auto Configuration.

✓ Use Starter Dependencies.

✓ Configure applications properly.

✓ Explain Embedded Servers.

✓ Explain Actuator.

✓ Discuss enterprise use cases.

✓ Answer Spring Boot interview questions.

---

# What's Next?

14_REST_API_Development.md

In the next chapter you will learn:

- REST Fundamentals
- HTTP Protocol
- REST Architecture
- REST Constraints
- API Design Principles
- Request and Response Handling
- Status Codes
- Swagger/OpenAPI
- REST APIs with Spring Boot

This is where WorkSphere begins exposing real backend functionality through APIs.