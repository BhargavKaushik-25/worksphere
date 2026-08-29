# 23_Kubernetes_and_Container_Orchestration

## Chapter Information

**Chapter Number:** 23

**Chapter Name:** Kubernetes and Container Orchestration

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

### Next Chapter

- CI/CD and DevOps Automation

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Explain Kubernetes.
- Explain Container Orchestration.
- Understand Kubernetes Architecture.
- Understand Pods.
- Understand Deployments.
- Understand Services.
- Understand ConfigMaps and Secrets.
- Understand Scaling concepts.
- Understand Self-Healing.
- Understand Kubernetes networking.
- Deploy Spring Boot applications conceptually.
- Explain Kubernetes in enterprise environments.
- Relate Kubernetes concepts to WorkSphere architecture.

---

# Introduction to Container Orchestration

## What Is Container Orchestration?

Container Orchestration is the process of managing, deploying, scaling, networking, and monitoring containers automatically.

Without orchestration:

```text
Create Container
Start Container
Monitor Container
Scale Container
Replace Failed Container
```

all happen manually.

For enterprise environments this becomes impossible at scale.

---

# Why Container Orchestration Exists

Imagine an application containing:

```text
User Service

Project Service

Task Service

Notification Service

Redis

Kafka

PostgreSQL
```

Running and managing dozens or hundreds of containers manually is not practical.

Container orchestration solves this problem.

---

# Real-World Analogy

Imagine an airport.

Without orchestration:

```text
Every Pilot
Finds Gates
Schedules Flights
Manages Refueling
```

Chaos occurs.

With orchestration:

```text
Central Control System
Coordinates Everything
```

Kubernetes acts as the control system for containers.

---

# What Is Kubernetes?

Kubernetes (K8s) is an open-source container orchestration platform.

Originally developed by Google and now maintained by the Cloud Native Computing Foundation (CNCF).

---

# Why Kubernetes Exists

Docker solved:

```text
How To Package Applications
```

Kubernetes solves:

```text
How To Run Applications At Scale
```

---

# Problems Kubernetes Solves

```text
Container Scheduling

Scaling

Self-Healing

Load Balancing

Rolling Updates

Service Discovery

Configuration Management
```

---

# Kubernetes Architecture

Kubernetes consists of:

```text
Control Plane

Worker Nodes
```

---

# High-Level Architecture

```text
               Control Plane

                    │

        ┌───────────┼───────────┐

        ▼           ▼           ▼

      Node 1      Node 2      Node 3

        │           │           │

      Pods        Pods        Pods
```

---

# What Is a Cluster?

A Cluster is a group of machines managed by Kubernetes.

Example:

```text
Machine A
Machine B
Machine C
```

working together.

---

# Control Plane

The Control Plane manages the cluster.

Responsibilities include:

```text
Scheduling

Monitoring

Scaling

Decision Making
```

---

# Worker Nodes

Worker Nodes run actual application workloads.

Example:

```text
User Service

Project Service

Task Service
```

containers execute here.

---

# Major Kubernetes Components

```text
API Server

Scheduler

Controller Manager

etcd

Kubelet

Kube Proxy
```

---

# API Server

Acts as the central entry point.

All communication goes through it.

Responsibilities:

```text
Receive Requests

Validate Requests

Update Cluster State
```

---

# Scheduler

Determines where Pods should run.

Example:

```text
Node A
Has Resources
```

Scheduler may place workloads there.

---

# Controller Manager

Ensures desired state matches actual state.

Example:

```text
Desired:
3 Pods

Actual:
2 Pods
```

Controller creates an additional Pod.

---

# etcd

## What Is etcd?

etcd is Kubernetes' distributed key-value store.

Stores:

```text
Cluster Configuration

Cluster State

Metadata
```

It is the source of truth for Kubernetes.

---

# Kubelet

Runs on every node.

Responsibilities:

```text
Pod Monitoring

Container Lifecycle Management
```

---

# Kube Proxy

Manages networking between Pods and Services.

---

# What Is a Pod?

A Pod is the smallest deployable unit in Kubernetes.

Pods contain:

```text
One Or More Containers
```

---

# Why Pods Exist

Kubernetes manages Pods, not individual containers.

Example:

```text
User Service Container
```

running inside:

```text
User Service Pod
```

---

# Pod Lifecycle

```text
Pending

Running

Succeeded

Failed
```

---

# Pod Example

```text
User Service Pod

Container:
Spring Boot Application
```

---

# Why Pods Are Ephemeral

Pods may be:

```text
Created

Destroyed

Recreated
```

at any time.

Applications must not rely on Pod permanence.

---

# Deployments

## What Is a Deployment?

A Deployment defines how Pods should run.

Example:

```text
Desired Pods = 3
```

Kubernetes maintains that state.

---

# Responsibilities

Deployments manage:

```text
Scaling

Updates

Replacements

Availability
```

---

# Example

```text
User Service

Replicas: 3
```

Kubernetes ensures three instances remain available.

---

# ReplicaSet

A ReplicaSet maintains the desired number of Pod replicas.

Example:

```text
Desired = 3

Running = 2
```

Kubernetes creates another Pod.

---

# Self-Healing

## What Is Self-Healing?

Kubernetes automatically recovers from failures.

Example:

```text
Pod Crashes
      ↓
Kubernetes Detects Failure
      ↓
New Pod Created
```

---

# Why Self-Healing Matters

Benefits:

```text
Higher Availability

Reduced Downtime

Improved Reliability
```

---

# Scaling

## Horizontal Scaling

Add more Pods.

Example:

```text
Pod 1
Pod 2
Pod 3
Pod 4
```

---

# Vertical Scaling

Increase resources:

```text
CPU

Memory
```

for a Pod.

---

# Autoscaling

Kubernetes can scale automatically.

Example:

```text
High CPU Load
      ↓
More Pods Created
```

---

# Services

## What Is a Service?

Pods are temporary.

Their IP addresses frequently change.

A Service provides a stable endpoint.

---

# Service Example

```text
User Service
      ↓
Pod A

Pod B

Pod C
```

Clients communicate through the Service.

---

# Types of Services

```text
ClusterIP

NodePort

LoadBalancer
```

---

# ClusterIP

Internal communication only.

Used between services.

---

# NodePort

Exposes applications through node ports.

Useful for development and learning.

---

# LoadBalancer

Used in cloud environments.

Provides external access.

---

# Service Discovery

Kubernetes provides built-in service discovery.

Example:

```text
user-service

project-service

task-service
```

Services can discover each other automatically.

---

# ConfigMaps

## What Is a ConfigMap?

Stores non-sensitive configuration.

Examples:

```text
Application Name

Database URL

Feature Flags
```

---

# Why ConfigMaps Exist

Avoid hardcoding configuration inside containers.

---

# Secrets

## What Are Secrets?

Kubernetes Secrets store sensitive information.

Examples:

```text
Passwords

API Keys

JWT Secrets

Certificates
```

---

# Why Secrets Exist

Sensitive information should never be hardcoded.

---

# Volumes

Pods are temporary.

Persistent storage requires volumes.

Example:

```text
Database Data

Uploaded Files

Application Assets
```

---

# Persistent Volumes

Persistent Volumes allow data to survive Pod recreation.

---

# Networking in Kubernetes

Kubernetes networking enables:

```text
Pod Communication

Service Communication

External Access
```

---

# Kubernetes Networking Model

Every Pod receives:

```text
IP Address
```

Pods can communicate with each other directly.

---

# Rolling Updates

## What Are Rolling Updates?

Rolling Updates deploy new versions gradually.

Example:

```text
Version 1
    ↓
Replace One Pod
    ↓
Replace Next Pod
```

No complete downtime.

---

# Benefits

```text
Reduced Risk

Minimal Downtime
```

---

# Rollback

If deployment fails:

```text
Rollback To Previous Version
```

Kubernetes supports rollout reversal.

---

# Resource Management

Kubernetes supports:

```text
CPU Limits

Memory Limits

Resource Requests
```

to prevent resource abuse.

---

# Monitoring

Production environments require monitoring.

Typical areas:

```text
CPU

Memory

Network

Pod Health

Application Metrics
```

---

# Health Checks

Kubernetes supports:

```text
Liveness Probe

Readiness Probe
```

---

# Liveness Probe

Determines:

```text
Is Application Alive?
```

---

# Readiness Probe

Determines:

```text
Can Application Receive Traffic?
```

---

# Kubernetes and Microservices

Kubernetes is an excellent platform for:

```text
Microservices

Cloud-Native Applications

Distributed Systems
```

because it provides automation and scalability.

---

# Enterprise Usage

Kubernetes is widely used by:

- Google
- Netflix
- Spotify
- Uber
- Airbnb
- Financial Institutions
- SaaS Organizations

It has become the industry standard for container orchestration.

---

# WorkSphere Relevance

Potential WorkSphere deployment:

```text
Authentication Service

User Service

Project Service

Task Service

Notification Service

Redis

Kafka

PostgreSQL
```

all managed by Kubernetes.

Benefits:

```text
Scalability

High Availability

Self-Healing

Cloud Readiness
```

---

# Common Mistakes

## Treating Pods As Permanent

Pods can disappear at any time.

---

## Hardcoding Configuration

Use ConfigMaps and Secrets.

---

## Ignoring Resource Limits

Can destabilize clusters.

---

## Not Using Health Checks

Reduces reliability.

---

## Storing Sensitive Data Incorrectly

Use Secrets.

---

# Interview Questions

## Beginner

### What is Kubernetes?

### Why was Kubernetes created?

### What is a Cluster?

### What is a Pod?

---

## Intermediate

### What is a Deployment?

### What is a Service?

### What is a ConfigMap?

### What is a Secret?

---

## Advanced

### Explain Kubernetes Architecture.

### What is etcd?

### What is Self-Healing?

### Explain Rolling Updates.

### Explain Autoscaling.

---

## Enterprise

### Why is Kubernetes important for Microservices?

### How would WorkSphere use Kubernetes?

### What are Liveness and Readiness Probes?

### Why are Deployments used instead of Pods directly?

---

# Summary

In this chapter you learned:

- Container Orchestration
- Kubernetes Fundamentals
- Cluster Architecture
- Pods
- Deployments
- ReplicaSets
- Services
- ConfigMaps
- Secrets
- Scaling
- Self-Healing
- Persistent Storage
- Rolling Updates
- Monitoring

Kubernetes is the industry-standard platform for running containerized applications at scale and is a critical technology for modern enterprise backend engineering.

---

# Revision Checklist

Before moving ahead, ensure you can:

- Explain Kubernetes.
- Explain Pods.
- Explain Deployments.
- Explain ReplicaSets.
- Explain Services.
- Explain ConfigMaps.
- Explain Secrets.
- Explain Scaling.
- Explain Self-Healing.
- Explain WorkSphere deployment architecture.

---

# Definition of Done

You have completed this chapter when you can:

✓ Explain Kubernetes architecture.

✓ Explain Pods and Deployments.

✓ Explain Services and Networking.

✓ Explain ConfigMaps and Secrets.

✓ Explain Scaling.

✓ Explain Self-Healing.

✓ Explain Rolling Updates.

✓ Discuss Kubernetes in WorkSphere.

✓ Answer Kubernetes interview questions confidently.

---

# What's Next?

24_CI_CD_and_DevOps_Automation.md

In the next chapter you will learn:

- DevOps Fundamentals
- CI/CD Pipelines
- GitHub Actions
- Build Automation
- Deployment Automation
- Infrastructure as Code
- Release Management
- DevOps Best Practices

This chapter moves WorkSphere into modern software delivery and production engineering.