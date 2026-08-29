# 22_Docker_and_Containerization

## Chapter Information

**Chapter Number:** 22

**Chapter Name:** Docker and Containerization

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

### Next Chapter

- Kubernetes and Container Orchestration

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Explain containerization.
- Understand why Docker exists.
- Explain Docker architecture.
- Create Docker images.
- Create Docker containers.
- Write Dockerfiles.
- Use Docker Compose.
- Understand networking and volumes.
- Containerize Spring Boot applications.
- Explain Docker usage in Microservices.
- Explain Docker concepts in interviews.

---

# Introduction to Containerization

## What Is Containerization?

Containerization is a method of packaging an application together with all of its dependencies into a single deployable unit called a container.

The container includes:

- Application Code
- Runtime
- Libraries
- Dependencies
- Configuration

---

# Why Containerization Exists

Before containers, applications often suffered from:

```text
Works On My Machine
But Not On Server
```

Problems included:

- Different Operating Systems
- Different Java Versions
- Missing Libraries
- Environment Differences

Containers solve these issues by packaging everything together.

---

# Real-World Analogy

Imagine shipping a product.

Without containers:

```text
Ship Product
Ship Tools
Ship Instructions
Hope It Works
```

With containers:

```text
Everything Packaged
Ready To Use
```

A container behaves like a fully packaged application environment.

---

# Traditional Deployment Challenges

Example:

```text
Developer Machine
       ↓
QA Server
       ↓
Production Server
```

Each environment may differ.

Applications behave inconsistently.

---

# Container-Based Deployment

```text
Build Container Once
          ↓
Run Anywhere
```

Benefits:

- Predictable behavior
- Consistent environments
- Easier deployments

---

# What Is Docker?

Docker is the most widely used containerization platform.

Docker allows developers to:

- Build Images
- Create Containers
- Run Applications
- Package Dependencies
- Distribute Software

---

# Why Docker Became Popular

Docker simplifies:

```text
Packaging

Deployment

Scaling

Environment Consistency
```

and has become a standard industry tool.

---

# Docker Architecture

Docker consists of:

```text
Docker Client

Docker Engine

Docker Images

Docker Containers

Docker Registry
```

---

# High-Level Architecture

```text
Developer
      ↓
Docker Client
      ↓
Docker Engine
      ↓
Containers
```

---

# Docker Client

The Docker Client is the interface used to interact with Docker.

Examples:

```bash
docker build

docker run

docker ps
```

---

# Docker Engine

Docker Engine executes Docker operations.

Responsibilities:

- Build Images
- Create Containers
- Manage Containers
- Manage Networking

---

# Docker Image

## What Is an Image?

A Docker Image is a blueprint for creating containers.

An image contains:

```text
Application

Dependencies

Runtime

Configuration
```

---

# Image Analogy

Think of an image as:

```text
Class
```

in Java.

---

# Docker Container

A container is a running instance of an image.

Analogy:

```text
Image = Class

Container = Object
```

Multiple containers can be created from the same image.

---

# Example

```text
Spring Boot Image
        ↓
Container 1

Container 2

Container 3
```

---

# Docker Registry

A registry stores Docker images.

Popular registry:

```text
Docker Hub
```

---

# Why Registries Exist

Registries allow images to be:

```text
Stored

Shared

Downloaded

Versioned
```

---

# Docker Image Lifecycle

```text
Dockerfile
      ↓
Build Image
      ↓
Push Registry
      ↓
Pull Image
      ↓
Run Container
```

---

# What Is a Dockerfile?

A Dockerfile is a text file containing instructions for building an image.

Docker reads the instructions and builds the image automatically.

---

# Example Dockerfile

```dockerfile
FROM openjdk:21

COPY target/app.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
```

---

# Common Dockerfile Instructions

## FROM

Defines base image.

Example:

```dockerfile
FROM openjdk:21
```

---

## COPY

Copies files into image.

Example:

```dockerfile
COPY target/app.jar app.jar
```

---

## WORKDIR

Defines working directory.

Example:

```dockerfile
WORKDIR /app
```

---

## RUN

Executes commands during image creation.

Example:

```dockerfile
RUN mkdir logs
```

---

## EXPOSE

Documents container port.

Example:

```dockerfile
EXPOSE 8080
```

---

## ENTRYPOINT

Defines startup command.

Example:

```dockerfile
ENTRYPOINT ["java","-jar","app.jar"]
```

---

# Building Images

Example:

```bash
docker build -t worksphere-user-service .
```

Result:

```text
Docker Image Created
```

---

# Running Containers

Example:

```bash
docker run worksphere-user-service
```

Docker creates a running container.

---

# Container Lifecycle

```text
Created
    ↓
Running
    ↓
Stopped
    ↓
Removed
```

---

# Listing Containers

Example:

```bash
docker ps
```

Displays running containers.

---

# Stopping Containers

Example:

```bash
docker stop container-id
```

---

# Removing Containers

Example:

```bash
docker rm container-id
```

---

# Container Networking

Containers often communicate with each other.

Example:

```text
User Service Container
         ↓
Project Service Container
         ↓
Redis Container
```

---

# Docker Networks

Docker provides networking support.

Benefits:

```text
Service Communication

Isolation

Name Resolution
```

---

# Port Mapping

Applications inside containers frequently expose ports.

Example:

```bash
docker run -p 8080:8080 app
```

Meaning:

```text
Host Port 8080
      ↓
Container Port 8080
```

---

# Volumes

## What Are Volumes?

Containers are temporary.

Data stored inside containers may be lost.

Volumes provide persistent storage.

---

# Why Volumes Exist

Without volumes:

```text
Container Deleted
      ↓
Data Lost
```

With volumes:

```text
Container Deleted
      ↓
Data Remains
```

---

# Volume Example

```bash
docker volume create postgres-data
```

---

# Docker Compose

## What Is Docker Compose?

Docker Compose manages multi-container applications.

Instead of starting containers manually:

```text
Application

Database

Redis

Kafka
```

Compose manages everything together.

---

# Why Docker Compose Exists

Microservices require multiple components.

Managing them individually becomes difficult.

---

# Example WorkSphere Components

```text
User Service

Project Service

Task Service

PostgreSQL

Redis

Kafka
```

Docker Compose can start all services together.

---

# docker-compose.yml

Example:

```yaml
version: "3.9"

services:

  app:
    image: worksphere-app

  postgres:
    image: postgres

  redis:
    image: redis
```

---

# Benefits of Docker Compose

- Simple local development
- Consistent environments
- Easier testing
- Faster onboarding

---

# Multi-Container Architecture

Example:

```text
Spring Boot
      ↓
PostgreSQL

Redis

Kafka
```

All services run independently but communicate together.

---

# Spring Boot and Docker

Spring Boot applications are commonly containerized.

Typical workflow:

```text
Build JAR
      ↓
Create Docker Image
      ↓
Run Container
```

---

# Microservices and Docker

Microservices benefit greatly from Docker.

Reasons:

```text
Independent Deployment

Environment Consistency

Scalability

Simplified Operations
```

---

# Image Versioning

Never rely on:

```text
latest
```

Prefer versions.

Example:

```text
v1.0

v1.1

v2.0
```

---

# Docker Best Practices

## Use Small Images

Smaller images:

- Build Faster
- Deploy Faster
- Use Less Storage

---

## Avoid Hardcoded Configuration

Use:

```text
Environment Variables
```

---

## Minimize Layers

Simplifies image maintenance.

---

## Keep Images Immutable

Containers should not be modified at runtime.

---

## Version Images

Always use image tags.

---

# Security Considerations

Important areas:

```text
Image Scanning

Secrets Management

Least Privilege

Dependency Updates
```

---

# Common Mistakes

## Storing Data Inside Containers

Data may be lost.

Use volumes.

---

## Using Latest Everywhere

Causes unpredictable deployments.

---

## Huge Images

Increase deployment time.

---

## Hardcoding Secrets

Never place passwords inside images.

---

## Running Everything In One Container

Containers should have a focused responsibility.

---

# Enterprise Usage

Docker is widely used by:

- Netflix
- Amazon
- Google
- Uber
- Spotify
- Enterprise SaaS Companies

It is the foundation of modern cloud-native development.

---

# WorkSphere Relevance

Potential WorkSphere containers:

```text
Authentication Service

User Service

Project Service

Task Service

Notification Service

PostgreSQL

Redis

Kafka
```

Docker enables:

```text
Local Development

Testing

Deployment

Environment Consistency
```

across all WorkSphere services.

---

# Interview Questions

## Beginner

### What is Docker?

### What is Containerization?

### Why is Docker used?

### Difference Between Image and Container?

---

## Intermediate

### What is a Dockerfile?

### What is Docker Compose?

### What are Volumes?

### What are Docker Networks?

---

## Advanced

### Explain Docker Architecture.

### How does Docker improve Microservices?

### What are Docker best practices?

### How should containers handle configuration?

---

## Enterprise

### Why do organizations use Docker?

### How would Docker help WorkSphere?

### What challenges does Docker solve?

### Why are containers important for Kubernetes?

---

# Summary

In this chapter you learned:

- 