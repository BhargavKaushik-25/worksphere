# 09_Java_Concurrency

## Chapter Information

**Chapter Number:** 09

**Chapter Name:** Java Concurrency

### Prerequisites

- Java Fundamentals
- OOP
- Exception Handling
- Collections
- Generics
- Modern Java and Lambda Expressions
- Streams
- Optional

### Next Chapter

- JVM Fundamentals

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Explain Concurrency and Multithreading.
- Distinguish Processes and Threads.
- Understand Thread Lifecycle.
- Create Threads in Java.
- Understand Race Conditions.
- Use Synchronization correctly.
- Understand Deadlocks.
- Use the Executor Framework.
- Work with Future and CompletableFuture.
- Use Concurrent Collections.
- Apply Concurrency concepts in enterprise applications.
- Answer Java Concurrency interview questions confidently.

---

# Introduction to Concurrency

## What Is Concurrency?

Concurrency is the ability of a system to execute multiple tasks during overlapping periods of time.

It helps applications:

- Improve performance
- Improve responsiveness
- Better utilize CPU resources
- Handle multiple users simultaneously

---

# Why Concurrency Exists

Imagine a web application handling:

- User Login
- Dashboard Requests
- Notifications
- File Uploads
- Email Sending

If everything happened sequentially:

```text
Request 1
      ↓
Request 2
      ↓
Request 3
      ↓
Request 4
```

the application would become slow.

Concurrency allows multiple operations to progress simultaneously.

---

# Real-World Analogy

Imagine a restaurant.

Single Cook:

```text
Order 1
↓
Order 2
↓
Order 3
```

Slow.

Multiple Cooks:

```text
Order 1
Order 2
Order 3
```

processed in parallel.

Much faster.

Threads behave similarly.

---

# Process vs Thread

## What Is a Process?

A Process is an independent running program.

Examples:

- Chrome Browser
- IntelliJ IDEA
- VS Code
- Spotify

Each process has its own memory space.

---

## What Is a Thread?

A Thread is a lightweight unit of execution within a process.

One process can contain multiple threads.

Example:

```text
Chrome Process
    ├─ Tab Thread
    ├─ Network Thread
    ├─ Rendering Thread
    └─ Background Thread
```

---

# Process vs Thread Comparison

| Process | Thread |
|----------|----------|
| Independent Execution | Part of Process |
| Separate Memory | Shared Memory |
| Higher Resource Usage | Lower Resource Usage |
| Slower Creation | Faster Creation |
| More Isolation | More Communication |

---

# What Is Multithreading?

Multithreading is the ability to execute multiple threads within the same process.

Example:

```text
Application
  ↓
Thread 1
Thread 2
Thread 3
Thread 4
```

All can work simultaneously.

---

# Benefits of Multithreading

## Improved Performance

Tasks execute concurrently.

---

## Better CPU Utilization

Modern CPUs contain multiple cores.

Multiple threads can use them efficiently.

---

## Improved Responsiveness

Applications remain responsive during long-running operations.

---

## Better User Experience

Background work does not block user interactions.

---

# Creating Threads in Java

## Method 1: Extending Thread Class

Example:

```java
public class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println(
                "Thread Running"
        );
    }
}
```

Usage:

```java
MyThread thread =
        new MyThread();

thread.start();
```

---

# Why start() Instead of run()?

Incorrect:

```java
thread.run();
```

Executes like a normal method.

Correct:

```java
thread.start();
```

Creates a new thread.

---

# Method 2: Implementing Runnable

Preferred approach.

Example:

```java
public class Task
        implements Runnable {

    @Override
    public void run() {
        System.out.println(
                "Task Running"
        );
    }
}
```

Usage:

```java
Thread thread =
        new Thread(
                new Task()
        );

thread.start();
```

---

# Why Runnable Is Preferred

Java supports only single inheritance.

Extending Thread blocks inheritance.

Implementing Runnable is more flexible.

---

# Lambda-Based Runnable

Modern Java allows:

```java
Thread thread =
        new Thread(
            () -> System.out.println(
                  "Running"
            )
        );

thread.start();
```

Cleaner and shorter.

---

# Thread Lifecycle

A thread moves through several states.

```text
NEW
 ↓
RUNNABLE
 ↓
RUNNING
 ↓
WAITING / BLOCKED
 ↓
TERMINATED
```

---

# NEW State

Thread object created.

Example:

```java
Thread thread =
        new Thread();
```

Not started yet.

---

# RUNNABLE State

Thread prepared for execution.

Example:

```java
thread.start();
```

---

# RUNNING State

Thread actively executes.

CPU assigns execution time.

---

# BLOCKED State

Waiting for access to resources.

Common examples:

- Lock waiting
- Resource contention

---

# WAITING State

Thread pauses until another thread signals it.

---

# TERMINATED State

Execution completed.

Thread cannot restart.

---

# Understanding Race Conditions

## What Is a Race Condition?

Occurs when multiple threads modify shared data simultaneously.

Example:

```java
balance = balance + 100;
```

Two threads execute together.

Unexpected results may occur.

---

# Example

```java
counter++;
```

appears simple.

Internally:

```text
Read
Modify
Write
```

Multiple threads can interfere.

Result becomes unpredictable.

---

# Synchronization

## What Is Synchronization?

Synchronization ensures only one thread accesses critical code at a time.

Example:

```java
public synchronized void increment() {

    counter++;

}
```

---

# Why Synchronization Exists

Without synchronization:

```text
Data Corruption
Inconsistent Results
Race Conditions
```

may occur.

---

# Synchronized Method Example

```java
public synchronized void deposit(
        double amount
) {
    balance += amount;
}
```

Only one thread may execute at a time.

---

# Synchronized Block

Sometimes only part of a method needs protection.

Example:

```java
public void increment() {

    synchronized(this) {
        counter++;
    }
}
```

More efficient.

---

# Thread Safety

## What Is Thread Safety?

A class is thread-safe when multiple threads can use it safely without producing incorrect results.

Examples:

```java
StringBuffer
ConcurrentHashMap
AtomicInteger
```

---

# Deadlock

## What Is Deadlock?

Deadlock occurs when two or more threads wait forever for each other.

Example:

```text
Thread A
waiting for Lock B

Thread B
waiting for Lock A
```

Neither proceeds.

Application freezes.

---

# Deadlock Example

```java
Thread A
  Lock X
  Waiting For Y

Thread B
  Lock Y
  Waiting For X
```

Result:

```text
Deadlock
```

---

# How To Avoid Deadlocks

- Consistent lock order
- Short lock duration
- Avoid nested locks
- Use modern concurrency tools

---

# Executor Framework

## Why Executor Framework Exists

Creating threads manually becomes difficult.

Example:

```java
new Thread(...)
new Thread(...)
new Thread(...)
```

Problems:

- Resource waste
- Poor scalability
- Hard maintenance

---

# Thread Pool Concept

Instead of creating new threads repeatedly:

```text
Pool of Reusable Threads
```

handles tasks.

---

# Creating ExecutorService

Example:

```java
ExecutorService service =
        Executors.newFixedThreadPool(5);
```

Creates:

```text
5 Worker Threads
```

---

# Submitting Tasks

Example:

```java
service.submit(
        () -> System.out.println(
              "Task Running"
        )
);
```

---

# Shutting Down ExecutorService

Always close resources.

```java
service.shutdown();
```

Best practice.

---

# Future

## What Is Future?

Represents a result that will be available later.

Example:

```java
Future<Integer> future =
        service.submit(
            () -> 100
        );
```

---

# Retrieving Future Result

```java
Integer value =
        future.get();
```

Result:

```text
100
```

---

# Limitation of Future

Problems:

- Blocking operations
- Limited composition
- Less flexible

Java introduced CompletableFuture.

---

# CompletableFuture

## What Is CompletableFuture?

A modern asynchronous programming API.

Provides:

- Non-blocking execution
- Chaining
- Combination of tasks
- Better readability

---

# Creating CompletableFuture

Example:

```java
CompletableFuture<String> future =
        CompletableFuture
            .supplyAsync(
                () -> "Java"
            );
```

---

# Processing Results

```java
future.thenApply(
        String::toUpperCase
);
```

Output:

```text
JAVA
```

---

# Chaining Operations

Example:

```java
CompletableFuture
    .supplyAsync(() -> "Java")
    .thenApply(String::toUpperCase)
    .thenApply(String::length);
```

Clean asynchronous pipeline.

---

# Concurrent Collections

Regular collections are not thread-safe.

Example:

```java
HashMap
ArrayList
HashSet
```

Problems may occur under concurrency.

---

# ConcurrentHashMap

Thread-safe alternative to HashMap.

Example:

```java
Map<Long, User> users =
        new ConcurrentHashMap<>();
```

---

# CopyOnWriteArrayList

Thread-safe List implementation.

Example:

```java
List<String> names =
        new CopyOnWriteArrayList<>();
```

---

# Atomic Classes

Java provides atomic operations.

Example:

```java
AtomicInteger counter =
        new AtomicInteger();
```

Increment safely:

```java
counter.incrementAndGet();
```

Avoids manual synchronization.

---

# Concurrency Best Practices

## Prefer ExecutorService

Instead of manually creating threads.

---

## Keep Shared State Minimal

Less shared data means fewer concurrency issues.

---

## Use Immutable Objects

Immutable objects are naturally thread-safe.

---

## Avoid Excessive Synchronization

Can reduce performance.

---

## Prefer Concurrent Collections

Safer and more scalable.

---

# Common Mistakes

## Creating Too Many Threads

Can hurt performance.

---

## Ignoring Thread Safety

Produces unpredictable bugs.

---

## Forgetting Executor Shutdown

Causes resource leaks.

---

## Overusing Synchronization

Reduces scalability.

---

## Blocking CompletableFuture

Defeats asynchronous benefits.

---

# Enterprise Usage

Concurrency is used in:

- Web Servers
- Microservices
- Notification Systems
- Background Jobs
- Batch Processing
- Distributed Systems
- API Processing

Almost every enterprise backend application uses concurrency.

---

# WorkSphere Relevance

Potential future usage in WorkSphere:

- Email notifications
- Report generation
- Background task processing
- Dashboard statistics
- Scheduled jobs
- Async integrations
- Audit processing

Concurrency knowledge will become increasingly important as WorkSphere evolves into a multi-service platform.

---

# Interview Questions

## Beginner

### What is a Thread?

### Process vs Thread?

### What is Multithreading?

### What is Synchronization?

---

## Intermediate

### Runnable vs Thread?

### What is a Race Condition?

### What is Thread Safety?

### What is ExecutorService?

---

## Advanced

### Explain Deadlock.

### What is CompletableFuture?

### ConcurrentHashMap vs HashMap?

### AtomicInteger vs Synchronization?

---

## Enterprise

### Why use Thread Pools?

### When should CompletableFuture be used?

### How does Spring Boot use asynchronous processing?

### How can concurrency improve scalability?

---

# Summary

In this chapter you learned:

- Processes and Threads
- Multithreading
- Thread Lifecycle
- Synchronization
- Race Conditions
- Deadlocks
- Executor Framework
- Future
- CompletableFuture
- Concurrent Collections
- Thread Safety

Concurrency is one of the most important topics for enterprise Java backend development and forms the foundation of scalable systems.

---

# Revision Checklist

Before moving ahead, ensure you can:

- Explain Thread concepts.
- Create Threads.
- Use Runnable.
- Explain Thread Lifecycle.
- Explain Synchronization.
- Identify Race Conditions.
- Explain Deadlocks.
- Use ExecutorService.
- Use Future.
- Use CompletableFuture.
- Use Concurrent Collections.
- Explain enterprise usage.

---

# Definition of Done

You have completed this chapter when you can:

✓ Explain concurrency fundamentals.

✓ Create and manage threads.

✓ Understand thread safety.

✓ Explain synchronization.

✓ Explain deadlocks.

✓ Use ExecutorService confidently.

✓ Use CompletableFuture confidently.

✓ Use concurrent collections.

✓ Discuss enterprise concurrency scenarios.

✓ Answer concurrency interview questions confidently.

---

# What's Next?

10_JVM_Fundamentals.md

In the next chapter you will learn:

- What the JVM is
- JVM Architecture
- Class Loading
- Memory Areas
- Heap and Stack
- Garbage Collection
- JIT Compilation
- JVM Performance Basics

This chapter will help you understand what happens behind the scenes when Java applications run.