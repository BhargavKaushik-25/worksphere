# 16_Spring_Security

## Chapter Information

**Chapter Number:** 16

**Chapter Name:** Spring Security

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
- Spring Data JPA & Hibernate

### Next Chapter

- Backend Testing

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Explain Authentication and Authorization.
- Understand Spring Security architecture.
- Implement secure authentication mechanisms.
- Understand JWT authentication.
- Implement Role-Based Access Control (RBAC).
- Configure password encoding.
- Secure REST APIs.
- Understand security filters.
- Apply secure development practices.
- Explain Spring Security concepts during interviews.

---

# Introduction to Security

## Why Security Matters

Modern applications store sensitive data:

- User Information
- Passwords
- Financial Data
- Business Information
- Project Data
- Internal Resources

Without proper security:

```text
Unauthorized Access
Data Theft
Privilege Escalation
Data Modification
System Compromise
```

can occur.

---

# Real-World Analogy

Imagine an office building.

Before entering:

```text
Identity Verification
```

must occur.

After entering:

```text
Access Restrictions
```

determine which rooms can be accessed.

Applications work similarly.

```text
Authentication
    ↓
Authorization
```

---

# What Is Authentication?

Authentication answers:

```text
Who Are You?
```

The system verifies identity.

Example:

```text
Username
Password
```

or

```text
JWT Token
```

or

```text
OAuth Login
```

---

# What Is Authorization?

Authorization answers:

```text
What Are You Allowed To Do?
```

Examples:

```text
Admin
Manager
Employee
User
```

Each role receives different permissions.

---

# Authentication vs Authorization

| Authentication | Authorization |
|---------------|---------------|
| Verifies Identity | Verifies Permissions |
| Who are you? | What can you do? |
| Happens First | Happens After Authentication |
| Login Process | Permission Check |

---

# What Is Spring Security?

Spring Security is the official Spring framework for securing applications.

It provides:

- Authentication
- Authorization
- Password Security
- Session Management
- JWT Support
- OAuth Integration
- Security Filters
- Protection Against Common Attacks

---

# Why Spring Security Exists

Without Spring Security, developers must manually implement:

```text
Login Logic
Session Handling
Access Control
Security Policies
Password Protection
```

Spring Security provides a standardized solution.

---

# Spring Security Architecture

High-Level Flow:

```text
Request
    ↓
Security Filter Chain
    ↓
Authentication
    ↓
Authorization
    ↓
Controller
```

Every request passes through security verification.

---

# Security Filter Chain

## What Is a Filter?

A filter intercepts requests before they reach application logic.

Example:

```text
Request
    ↓
Security Check
    ↓
Controller
```

---

# Why Filters Exist

Filters handle:

- Authentication
- Authorization
- Security Validation
- Token Verification
- Request Inspection

before business logic executes.

---

# Default Spring Security Behavior

When Spring Security is added:

```xml
spring-boot-starter-security
```

Spring automatically protects all endpoints.

Default behavior:

```text
All Endpoints Secured
Generated Password
Default Login Page
```

---

# UserDetails

## What Is UserDetails?

Represents an authenticated user.

Contains information such as:

```text
Username
Password
Authorities
Roles
Account Status
```

---

# UserDetailsService

Responsible for loading user information.

Example:

```java
public interface UserDetailsService {

    UserDetails loadUserByUsername(
            String username
    );

}
```

Spring Security uses it during authentication.

---

# Password Security

## Never Store Plain Text Passwords

Bad:

```text
password123
```

stored directly in database.

If the database is compromised:

```text
All Passwords Exposed
```

---

# Password Encoding

Passwords should be stored as hashes.

Example:

```text
password123
```

becomes:

```text
$2a$10$...
```

---

# BCrypt

Spring Security commonly uses:

```text
BCrypt
```

for password hashing.

---

# BCrypt Example

```java
PasswordEncoder encoder =
        new BCryptPasswordEncoder();

String encoded =
        encoder.encode("password123");
```

---

# Why BCrypt Is Popular

Benefits:

- Secure
- Salted Hashes
- Industry Standard
- Resistant to Rainbow Table Attacks

---

# Authentication Flow

Typical flow:

```text
User Login Request
        ↓
User Details Loaded
        ↓
Password Validation
        ↓
Authentication Success
        ↓
Access Granted
```

---

# Session-Based Authentication

Traditional web applications use sessions.

Flow:

```text
Login
   ↓
Session Created
   ↓
Session ID Stored
   ↓
Subsequent Requests Reuse Session
```

---

# Advantages

- Simpler implementation
- Common for server-rendered applications

---

# Limitations

- Less scalable
- Server stores session state

Modern APIs often use JWT instead.

---

# JWT Authentication

## Full Form

JWT stands for:

```text
JSON Web Token
```

---

# What Is JWT?

JWT is a compact token used for stateless authentication.

Example:

```text
eyJhbGciOi...
```

---

# Why JWT Exists

Instead of storing sessions:

```text
Server Stores Nothing
```

Authentication information resides inside the token.

---

# Benefits of JWT

- Stateless
- Scalable
- Suitable for APIs
- Suitable for Microservices

---

# JWT Structure

A JWT consists of:

```text
Header
Payload
Signature
```

---

# Header

Contains algorithm information.

Example:

```json
{
  "alg": "HS256"
}
```

---

# Payload

Contains claims.

Example:

```json
{
  "username":"bhargav",
  "role":"ADMIN"
}
```

---

# Signature

Protects integrity.

Used to verify token authenticity.

---

# JWT Authentication Flow

```text
User Login
     ↓
Credentials Validated
     ↓
JWT Generated
     ↓
Client Stores Token
     ↓
Token Sent With Requests
     ↓
API Validates Token
```

---

# Authorization Header

Common JWT format:

```http
Authorization:
Bearer eyJhbGciOi...
```

---

# What Is RBAC?

RBAC stands for:

```text
Role-Based Access Control
```

---

# Why RBAC Exists

Different users need different permissions.

Example:

```text
Admin
Manager
Member
Viewer
```

Each role has different access levels.

---

# Example Permissions

Admin:

```text
Create User
Delete User
Manage System
```

User:

```text
Create Task
View Task
Update Task
```

---

# Spring Security Roles

Example:

```java
ROLE_ADMIN

ROLE_USER
```

---

# Securing Endpoints

Example:

```java
@PreAuthorize(
    "hasRole('ADMIN')"
)
```

Only administrators can access.

---

# Common Authorization Techniques

## Endpoint Level

Protect URLs.

---

## Method Level

Protect service methods.

Example:

```java
@PreAuthorize
```

---

## Role Level

Restrict functionality based on roles.

---

# CSRF Protection

## What Is CSRF?

CSRF stands for:

```text
Cross-Site Request Forgery
```

An attacker tricks a user into performing unwanted actions.

---

# Why CSRF Exists

The browser automatically sends credentials.

Attackers exploit this behavior.

---

# Spring Security Protection

Spring Security includes built-in CSRF protection.

For stateless JWT APIs, it is often disabled intentionally.

---

# CORS

## What Is CORS?

CORS stands for:

```text
Cross-Origin Resource Sharing
```

---

# Why CORS Exists

Browser security prevents unrestricted cross-origin requests.

Example:

```text
Frontend:
localhost:4200

Backend:
localhost:8080
```

Different origins require CORS configuration.

---

# Security Configuration

Modern Spring Security uses:

```java
SecurityFilterChain
```

instead of older configuration approaches.

---

# Example Security Flow

```text
Request
    ↓
JWT Validation
    ↓
Authentication
    ↓
Authorization
    ↓
Controller
```

---

# Exception Handling

Security exceptions commonly include:

```text
401 Unauthorized

403 Forbidden
```

---

# 401 Unauthorized

User identity not verified.

---

# 403 Forbidden

User authenticated but lacks required permission.

---

# Security Best Practices

## Use Strong Passwords

Avoid weak credentials.

---

## Always Hash Passwords

Use BCrypt.

---

## Principle of Least Privilege

Users should receive minimum required permissions.

---

## Validate JWT Tokens

Never trust incoming tokens automatically.

---

## Secure Sensitive Endpoints

Protect administrative APIs.

---

## Rotate Secrets

Regularly update keys and secrets.

---

## Use HTTPS

Encrypt communication.

---

# Common Security Vulnerabilities

Developers should understand:

```text
SQL Injection

Cross-Site Scripting (XSS)

Cross-Site Request Forgery (CSRF)

Broken Authentication

Privilege Escalation
```

Security is a continuous process.

---

# Enterprise Usage

Spring Security is widely used in:

- Banking Applications
- Insurance Systems
- Government Platforms
- Healthcare Systems
- SaaS Products
- Enterprise APIs

Most modern Spring Boot systems use Spring Security.

---

# WorkSphere Relevance

WorkSphere will require:

```text
Authentication
Authorization
Role Management
JWT Security
Protected APIs
```

Potential roles:

```text
SUPER_ADMIN

ADMIN

PROJECT_MANAGER

TEAM_MEMBER
```

Spring Security will become one of the most critical backend components of WorkSphere.

---

# Common Mistakes

## Storing Plain Passwords

Never do this.

---

## Excessive Permissions

Violates least-privilege principles.

---

## Trusting JWT Without Validation

Always validate signatures and expiration.

---

## Exposing Sensitive Endpoints

Administrative APIs should be protected.

---

# Interview Questions

## Beginner

### What is Authentication?

### What is Authorization?

### Authentication vs Authorization?

### What is Spring Security?

---

## Intermediate

### What is BCrypt?

### What is JWT?

### Session vs JWT?

### What is UserDetailsService?

---

## Advanced

### Explain Spring Security Architecture.

### What is Security Filter Chain?

### Explain RBAC.

### What is CSRF?

### What is CORS?

---

## Enterprise

### Why is JWT suitable for microservices?

### How would security be implemented in WorkSphere?

### Why should passwords be hashed?

### What security challenges exist in enterprise systems?

---

# Summary

In this chapter you learned:

- Authentication
- Authorization
- Spring Security
- UserDetails
- Password Encoding
- BCrypt
- JWT
- RBAC
- Security Filters
- CORS
- CSRF
- Security Best Practices

Spring Security forms the foundation of secure enterprise Spring Boot applications.

---

# Revision Checklist

Before moving ahead, ensure you can:

- Explain Authentication.
- Explain Authorization.
- Explain Spring Security.
- Explain BCrypt.
- Explain JWT.
- Explain RBAC.
- Explain Security Filters.
- Explain CORS.
- Explain CSRF.
- Apply security best practices.

---

# Definition of Done

You have completed this chapter when you can:

✓ Explain Spring Security architecture.

✓ Implement secure authentication.

✓ Implement JWT-based APIs.

✓ Configure authorization rules.

✓ Secure endpoints.

✓ Protect passwords correctly.

✓ Explain security best practices.

✓ Discuss WorkSphere security architecture.

✓ Answer Spring Security interview questions.

---

# What's Next?

17_Backend_Testing.md

In the next chapter you will learn:

- Unit Testing
- Integration Testing
- JUnit 5
- Mockito
- TestContainers
- API Testing
- Security Testing
- Test Strategies

This chapter completes the core enterprise backend development journey by teaching how to verify and maintain application quality.