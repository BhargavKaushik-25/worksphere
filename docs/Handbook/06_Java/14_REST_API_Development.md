# 14_REST_API_Development

## Chapter Information

**Chapter Number:** 14

**Chapter Name:** REST API Development

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

### Next Chapter

- Spring Data JPA & Hibernate

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Explain REST.
- Understand REST architectural principles.
- Understand HTTP fundamentals.
- Design RESTful APIs.
- Work with HTTP methods.
- Use request and response bodies.
- Use HTTP status codes correctly.
- Build REST APIs using Spring Boot.
- Understand API documentation.
- Apply REST principles in enterprise systems.
- Explain REST concepts during interviews.

---

# Introduction to REST

## Full Form

REST stands for:

```text
Representational State Transfer
```

REST is an architectural style for designing distributed systems and web services.

---

# What Is REST?

REST is a set of architectural principles that allow applications to communicate using HTTP.

Example:

```text
Frontend
    ↓
REST API
    ↓
Backend
    ↓
Database
```

Most modern enterprise applications rely on REST APIs for communication.

---

# Why REST Exists

Applications often need communication between:

```text
Frontend ↔ Backend

Mobile App ↔ Backend

Microservice ↔ Microservice

Third Party System ↔ Application
```

REST provides a standardized way for systems to exchange information.

---

# Real World Analogy

Think of a restaurant.

Customer:

```text
Frontend
```

Waiter:

```text
REST API
```

Kitchen:

```text
Backend Logic
```

The customer never directly enters the kitchen.

The waiter acts as the communication layer.

REST APIs perform a similar role.

---

# What Is an API?

API stands for:

```text
Application Programming Interface
```

An API provides a contract for communication between systems.

Example:

```text
Create User
Get User
Update User
Delete User
```

Applications interact through APIs rather than directly accessing internal systems.

---

# REST Architecture

REST follows several architectural constraints.

These constraints make systems:

- Scalable
- Maintainable
- Reliable
- Interoperable

---

# REST Constraints

## Client-Server Architecture

The client and server remain independent.

Client:

```text
Frontend
Mobile App
External Consumer
```

Server:

```text
Business Logic
Database Access
Security
```

Benefits:

- Separation of concerns
- Independent development

---

## Statelessness

Every request must contain all required information.

Bad:

```text
Server remembers previous request state.
```

Good:

```text
Each request is self-contained.
```

Example:

```http
Authorization: Bearer token
```

Each request includes authentication information.

---

## Cacheability

Responses may be cached to improve performance.

Benefits:

- Faster responses
- Reduced server load

---

## Uniform Interface

REST APIs should follow consistent conventions.

Example:

```text
/users
/projects
/tasks
```

instead of:

```text
/getUsers
/fetchTasks
/loadProjects
```

---

# HTTP Fundamentals

REST APIs typically use:

```text
HTTP
```

HTTP stands for:

```text
HyperText Transfer Protocol
```

---

# HTTP Request Structure

```http
GET /users/1

Headers

Body
```

---

# HTTP Response Structure

```http
Status Code

Headers

Response Body
```

---

# HTTP Methods

REST uses HTTP methods to represent operations.

---

## GET

Purpose:

```text
Retrieve Data
```

Example:

```http
GET /users
```

Meaning:

```text
Get all users
```

---

## POST

Purpose:

```text
Create Data
```

Example:

```http
POST /users
```

Meaning:

```text
Create user
```

---

## PUT

Purpose:

```text
Replace existing resource
```

Example:

```http
PUT /users/1
```

Meaning:

```text
Update entire user
```

---

## PATCH

Purpose:

```text
Partial update
```

Example:

```http
PATCH /users/1
```

Meaning:

```text
Update only specific fields
```

---

## DELETE

Purpose:

```text
Delete resource
```

Example:

```http
DELETE /users/1
```

Meaning:

```text
Delete user
```

---

# REST Resource Design

Resources should use nouns.

Good:

```text
/users
/projects
/tasks
/teams
```

Bad:

```text
/getUsers
/createProject
/updateTask
```

REST focuses on resources rather than actions.

---

# URI Design Principles

## Collection Resource

```http
GET /users
```

Get all users.

---

## Single Resource

```http
GET /users/1
```

Get a specific user.

---

## Nested Resource

```http
GET /projects/1/tasks
```

Get tasks belonging to a project.

---

# Request Body

The request body contains client data.

Example:

```json
{
  "name": "Bhargav",
  "email": "bhargav@example.com"
}
```

Commonly used with:

```http
POST
PUT
PATCH
```

---

# Response Body

The server returns data to the client.

Example:

```json
{
  "id": 1,
  "name": "Bhargav",
  "email": "bhargav@example.com"
}
```

---

# JSON

JSON stands for:

```text
JavaScript Object Notation
```

Most REST APIs exchange data using JSON.

Benefits:

- Lightweight
- Human readable
- Language independent

---

# HTTP Status Codes

Status codes indicate request results.

---

## 200 OK

Request successful.

Example:

```http
GET /users/1
```

---

## 201 Created

Resource successfully created.

Example:

```http
POST /users
```

---

## 204 No Content

Request successful, no response body.

Example:

```http
DELETE /users/1
```

---

## 400 Bad Request

Client sent invalid data.

---

## 401 Unauthorized

Authentication required.

---

## 403 Forbidden

Authenticated but not allowed.

---

## 404 Not Found

Resource does not exist.

---

## 500 Internal Server Error

Unexpected server error.

---

# REST API Example

## Create User

Request:

```http
POST /users
```

Body:

```json
{
  "name": "Bhargav"
}
```

Response:

```http
201 Created
```

---

## Get User

Request:

```http
GET /users/1
```

Response:

```json
{
  "id": 1,
  "name": "Bhargav"
}
```

---

# REST APIs in Spring Boot

Spring Boot makes API development straightforward.

---

# @RestController

Example:

```java
@RestController
public class UserController {

}
```

This class handles REST requests.

---

# @RequestMapping

Example:

```java
@RequestMapping("/users")
```

Defines base URL.

---

# GET Endpoint

Example:

```java
@GetMapping("/{id}")
public User getUser(
        @PathVariable Long id
) {
    return service.findById(id);
}
```

---

# POST Endpoint

Example:

```java
@PostMapping
public User createUser(
        @RequestBody User user
) {
    return service.save(user);
}
```

---

# PUT Endpoint

Example:

```java
@PutMapping("/{id}")
public User updateUser(
        @PathVariable Long id,
        @RequestBody User user
) {
    return service.update(id,user);
}
```

---

# DELETE Endpoint

Example:

```java
@DeleteMapping("/{id}")
public void deleteUser(
        @PathVariable Long id
) {
    service.delete(id);
}
```

---

# Request Parameters

Example:

```http
GET /users?page=1
```

Spring Boot:

```java
@RequestParam Integer page
```

---

# Path Variables

Example:

```http
GET /users/10
```

Spring Boot:

```java
@PathVariable Long id
```

---

# Request Headers

Example:

```http
Authorization: Bearer token
```

Spring Boot:

```java
@RequestHeader String authorization
```

---

# Validation

REST APIs must validate input.

Example:

```java
@NotBlank
private String name;
```

Prevents invalid requests.

---

# Exception Handling

Centralized exception handling improves API consistency.

Example:

```java
@RestControllerAdvice
```

Combined with:

```java
@ExceptionHandler
```

---

# API Documentation

Good APIs must be documented.

Popular tools:

```text
Swagger
OpenAPI
SpringDoc
```

Benefits:

- Easier integration
- Better maintainability
- Faster onboarding

---

# REST API Best Practices

## Use Nouns

Good:

```text
/users
```

Bad:

```text
/getUsers
```

---

## Use Correct Status Codes

Do not always return:

```http
200
```

Use appropriate responses.

---

## Version APIs

Example:

```text
/api/v1/users
```

---

## Validate Inputs

Never trust client data.

---

## Return Consistent Responses

Use standard structures.

---

# Security Considerations

REST APIs should consider:

- Authentication
- Authorization
- Input Validation
- Rate Limiting
- Secure Headers
- Logging

Most enterprise APIs use token-based security.

---

# Enterprise Usage

REST APIs are used in:

- Banking Systems
- E-Commerce Platforms
- Mobile Applications
- SaaS Products
- Microservices
- Cloud Applications

REST remains the dominant communication style in modern backend systems.

---

# WorkSphere Relevance

WorkSphere backend will expose resources such as:

```text
/users

/projects

/tasks

/teams

/notifications
```

These APIs will be consumed by:

```text
Angular Frontend

Future Mobile Applications

Microservices

External Integrations
```

REST APIs will serve as the primary communication layer between WorkSphere components.

---

# Common Mistakes

## Using Verbs in URLs

Bad:

```text
/createUser
```

---

## Ignoring Status Codes

Always use meaningful responses.

---

## Returning Internal Exceptions

Do not expose sensitive information.

---

## Poor API Naming

Consistency is important.

---

# Interview Questions

## Beginner

### What is REST?

### What is an API?

### What is HTTP?

### What is JSON?

---

## Intermediate

### Explain REST Constraints.

### GET vs POST?

### PUT vs PATCH?

### Path Variable vs Request Parameter?

---

## Advanced

### Why is REST stateless?

### API Versioning Strategies?

### REST vs SOAP?

### How do you secure REST APIs?

---

## Enterprise

### Why is REST popular in microservices?

### How should APIs be documented?

### How should APIs be versioned?

### How would REST fit into WorkSphere?

---

# Summary

In this chapter you learned:

- REST Fundamentals
- HTTP Protocol
- REST Constraints
- Request and Response Handling
- JSON
- Status Codes
- API Design
- Spring Boot REST Controllers
- Validation
- Exception Handling
- API Documentation

REST APIs form the communication backbone of modern backend applications.

---

# Revision Checklist

Before moving ahead, ensure you can:

- Explain REST.
- Explain HTTP methods.
- Use status codes correctly.
- Design REST resources.
- Build REST controllers.
- Handle requests and responses.
- Apply validation.
- Explain API documentation.
- Answer REST interview questions.

---

# Definition of Done

You have completed this chapter when you can:

✓ Explain REST architecture.

✓ Build REST APIs using Spring Boot.

✓ Use HTTP methods correctly.

✓ Return proper status codes.

✓ Validate requests.

✓ Design clean API endpoints.

✓ Document APIs properly.

✓ Explain REST concepts confidently.

---

# What's Next?

15_Spring_Data_JPA_Hibernate.md

In the next chapter you will learn:

- Persistence Layer Design
- JPA Fundamentals
- Hibernate
- Entity Mapping
- Relationships
- Repositories
- Query Methods
- JPQL
- Performance Considerations

This chapter will enable WorkSphere to persist and retrieve data from databases professionally.