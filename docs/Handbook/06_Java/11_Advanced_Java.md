# 11_Advanced_Java

## Chapter Information

**Chapter Number:** 11

**Chapter Name:** Advanced Java

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

### Next Chapter

- Spring Framework

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Understand Reflection in Java.
- Understand Annotations and Metadata.
- Explain Serialization and Deserialization.
- Work with Java I/O.
- Understand NIO (New I/O).
- Understand Class Loaders at an advanced level.
- Understand Dynamic Proxies.
- Use Reflection responsibly.
- Understand enterprise use cases of advanced Java features.
- Prepare for framework-level development.
- Answer advanced Java interview questions.

---

# Introduction to Advanced Java

## What Is Advanced Java?

Advanced Java refers to language features and APIs that go beyond everyday programming and are commonly used by:

- Framework Developers
- Library Developers
- Enterprise Applications
- Application Servers
- Middleware Platforms

Examples include:

- Reflection
- Annotations
- Serialization
- Java I/O
- NIO
- Dynamic Proxies
- Class Loading

---

# Why Advanced Java Matters

Many enterprise frameworks depend heavily on these features.

Examples:

```text
Spring Framework
Hibernate
Spring Boot
JUnit
Mockito
Jakarta EE
```

To understand how modern frameworks work internally, you must understand Advanced Java concepts.

---

# Reflection

## What Is Reflection?

Reflection allows a program to inspect and manipulate classes, methods, fields, and constructors at runtime.

Normally Java code is known at compile time.

Reflection enables runtime inspection.

---

# Why Reflection Exists

Suppose a framework needs to:

```text
Discover Classes
Inspect Annotations
Create Objects
Invoke Methods
```

without knowing those classes beforehand.

Reflection makes this possible.

---

# Real-World Analogy

Imagine entering a building.

Without Reflection:

```text
You already know every room.
```

With Reflection:

```text
You enter and inspect the building dynamically.
```

You discover:

- Rooms
- Doors
- Resources
- Structure

at runtime.

---

# Obtaining Class Objects

Example:

```java
Class<User> clazz =
        User.class;
```

Another approach:

```java
Class<?> clazz =
        Class.forName(
            "com.example.User"
        );
```

---

# Reading Class Information

Example:

```java
Class<User> clazz =
        User.class;

System.out.println(
        clazz.getName()
);
```

Output:

```text
com.example.User
```

---

# Reading Fields

Example:

```java
Field[] fields =
        clazz.getDeclaredFields();
```

Reflection can inspect:

```text
Private Fields
Protected Fields
Public Fields
```

---

# Reading Methods

Example:

```java
Method[] methods =
        clazz.getDeclaredMethods();
```

Useful for frameworks.

---

# Creating Objects Dynamically

Example:

```java
User user =
        User.class
            .getDeclaredConstructor()
            .newInstance();
```

Object created without:

```java
new User()
```

---

# Invoking Methods Dynamically

Example:

```java
Method method =
        clazz.getMethod("getName");

String result =
        (String) method.invoke(user);
```

Method executed dynamically.

---

# Reflection Advantages

- Dynamic behavior
- Framework development
- Runtime inspection
- Flexible architecture

---

# Reflection Limitations

- Slower than normal execution
- Complex code
- Reduced type safety
- Potential security concerns

---

# Reflection in Enterprise Applications

Used extensively by:

```text
Spring Framework
Hibernate
JUnit
Jackson
Mockito
```

Most modern Java frameworks rely heavily on Reflection.

---

# Annotations

## What Is an Annotation?

Annotations provide metadata about code.

Metadata means:

```text
Information about information
```

Annotations do not normally change logic directly.

Instead they describe behavior.

---

# Example

```java
@Override
public String toString() {
    return "User";
}
```

Here:

```java
@Override
```

is an annotation.

---

# Common Java Annotations

## @Override

Indicates method overriding.

---

## @Deprecated

Marks old code.

Example:

```java
@Deprecated
public void oldMethod() {

}
```

---

## @SuppressWarnings

Suppresses compiler warnings.

Example:

```java
@SuppressWarnings("unchecked")
```

---

# Custom Annotations

Developers can create their own annotations.

Example:

```java
public @interface Audit {

}
```

Usage:

```java
@Audit
public void saveUser() {

}
```

---

# Why Annotations Matter

Frameworks use annotations heavily.

Examples:

```java
@RestController

@Service

@Repository

@Entity

@Component

@Autowired
```

Understanding annotations is essential before learning Spring.

---

# Serialization

## What Is Serialization?

Serialization converts an object into a format suitable for:

- File Storage
- Network Transmission
- Caching
- Messaging

---

# Real-World Analogy

Imagine shipping a product.

Before shipping:

```text
Product
```

must become:

```text
Packaged Box
```

Serialization packages objects for movement or storage.

---

# Example

```java
User user =
        new User();
```

becomes:

```text
Byte Stream
```

for storage or transmission.

---

# Serializable Interface

Example:

```java
public class User
        implements Serializable {

}
```

The marker interface enables serialization.

---

# Deserialization

The reverse operation.

```text
Byte Stream
     ↓
Java Object
```

---

# serialVersionUID

Serialization uses:

```java
private static final long
serialVersionUID = 1L;
```

to verify compatibility between versions.

---

# Serialization Use Cases

- Session Storage
- Distributed Systems
- Messaging Systems
- Caching
- Persistence

---

# Java Input and Output (I/O)

## What Is I/O?

I/O stands for:

```text
Input / Output
```

Input:

```text
Reading Data
```

Output:

```text
Writing Data
```

---

# Examples of I/O

Reading:

- Files
- Databases
- Network Connections

Writing:

- Files
- Logs
- Reports

---

# File Reading Example

```java
FileReader reader =
        new FileReader(
            "data.txt"
        );
```

---

# File Writing Example

```java
FileWriter writer =
        new FileWriter(
            "output.txt"
        );
```

---

# Streams in I/O

Java I/O uses streams.

Examples:

```java
InputStream

OutputStream

Reader

Writer
```

---

# Byte Streams

Used for binary data.

Examples:

```java
FileInputStream

FileOutputStream
```

Suitable for:

- Images
- Videos
- Files

---

# Character Streams

Used for text.

Examples:

```java
FileReader

FileWriter
```

Suitable for:

- Text Files
- Logs
- Reports

---

# Buffered Streams

Buffered streams improve performance.

Example:

```java
BufferedReader
```

instead of:

```java
FileReader
```

directly.

Benefits:

- Fewer disk operations
- Faster access

---

# Java NIO

## What Is NIO?

NIO stands for:

```text
New Input Output
```

Introduced to improve scalability and performance.

---

# Why NIO Exists

Traditional I/O:

```text
Blocking Operations
```

can limit scalability.

NIO offers:

```text
Non-Blocking Operations
```

and improved efficiency.

---

# Important NIO Components

## Path

Represents a file location.

Example:

```java
Path path =
        Paths.get(
            "data.txt"
        );
```

---

## Files

Provides utility methods.

Example:

```java
Files.readString(path);
```

---

## Channels

Provide faster data transfer.

Example:

```java
FileChannel
```

---

## Buffers

Temporary memory containers.

Example:

```java
ByteBuffer
```

---

# Traditional I/O vs NIO

| Traditional I/O | NIO |
|---------------|------|
| Stream Based | Buffer Based |
| Blocking | Supports Non-Blocking |
| Older API | Modern API |
| Simpler | More Powerful |

---

# Dynamic Proxies

## What Is a Dynamic Proxy?

Dynamic proxies allow behavior to be generated at runtime.

Used heavily in enterprise frameworks.

Example:

```text
Transaction Handling
Logging
Security
Monitoring
```

without modifying actual business logic.

---

# Why Dynamic Proxies Matter

Spring AOP heavily relies on proxy-based architectures.

Understanding proxies helps explain:

```java
@Transactional

@Cacheable

@Async
```

behavior later.

---

# Advanced Class Loading Concepts

The JVM uses multiple class loaders.

Examples:

```text
Bootstrap ClassLoader
Platform ClassLoader
Application ClassLoader
```

---

# Why Class Loaders Matter

Class loaders provide:

- Isolation
- Security
- Dependency Loading
- Plugin Systems

---

# Enterprise Usage

Advanced Java concepts appear in:

## Spring Framework

Uses:

- Reflection
- Annotations
- Proxies

---

## Hibernate

Uses:

- Reflection
- Annotations
- Dynamic Proxy Mechanisms

---

## Microservices

Use:

- Serialization
- Deserialization
- Networking APIs

---

## Application Servers

Use:

- Class Loaders
- Reflection
- Runtime Discovery

---

# WorkSphere Relevance

These concepts will become important when WorkSphere reaches:

- Spring Framework
- Spring Boot
- JPA/Hibernate
- REST APIs
- Microservices
- Event-Driven Systems

Many Spring features internally rely on:

```text
Reflection
Annotations
Proxies
Serialization
```

Understanding them now makes future learning much easier.

---

# Common Mistakes

## Overusing Reflection

Reflection is powerful but should be used carefully.

---

## Ignoring Serialization Compatibility

Changing serialized classes can break systems.

---

## Poor Resource Management

Always close I/O resources.

---

## Using Old I/O Everywhere

Modern applications often prefer NIO APIs.

---

# Interview Questions

## Beginner

### What is Reflection?

### What are Annotations?

### What is Serialization?

### What is Java I/O?

---

## Intermediate

### Reflection vs Normal Method Calls?

### What is serialVersionUID?

### Byte Stream vs Character Stream?

### Why was NIO introduced?

---

## Advanced

### How does Reflection work?

### What are Dynamic Proxies?

### Explain Class Loaders.

### Blocking vs Non-Blocking I/O?

---

## Enterprise

### How does Spring use Reflection?

### Why are annotations important?

### How does Hibernate use proxies?

### Why is serialization important in distributed systems?

---

# Summary

In this chapter you learned:

- Reflection
- Annotations
- Serialization
- Deserialization
- Java I/O
- NIO
- Dynamic Proxies
- Class Loading Concepts
- Enterprise Applications

These concepts form the bridge between core Java and enterprise frameworks.

---

# Revision Checklist

Before moving ahead, ensure you can:

- Explain Reflection.
- Create and use annotations.
- Explain Serialization.
- Explain Deserialization.
- Use I/O APIs.
- Explain NIO.
- Explain Dynamic Proxies.
- Explain Class Loaders.
- Discuss enterprise use cases.

---

# Definition of Done

You have completed this chapter when you can:

✓ Explain Reflection confidently.

✓ Explain Annotations confidently.

✓ Explain Serialization.

✓ Use Java I/O and NIO basics.

✓ Understand Dynamic Proxies.

✓ Explain enterprise framework internals.

✓ Prepare for Spring Framework concepts.

✓ Answer Advanced Java interview questions.

---

# What's Next?

12_Spring_Framework.md

In the next chapter you will learn:

- Spring Framework Fundamentals
- Dependency Injection (DI)
- Inversion of Control (IoC)
- Spring Container
- Beans
- Configuration
- Spring Architecture

This chapter marks the transition from Java language mastery into enterprise Java backend development.