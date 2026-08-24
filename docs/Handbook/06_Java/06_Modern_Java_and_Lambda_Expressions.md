# Modern Java and Lambda Expressions

## Chapter Information

**Chapter Number:** 06

**Prerequisites**

- Java Fundamentals
- OOP
- Exception Handling
- Collections
- Generics

**Next Chapter**

- Java Streams

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Explain what Modern Java is.
- Understand why Java evolved after Java 8.
- Explain Functional Programming concepts.
- Understand Functional Interfaces.
- Use Lambda Expressions confidently.
- Use Method References.
- Understand built-in Functional Interfaces.
- Understand variable capture rules.
- Apply Lambdas in enterprise applications.
- Understand how Lambdas support Streams.
- Answer Lambda-related interview questions.

---

# Introduction to Modern Java

## What Is Modern Java?

Modern Java refers to the programming style and features introduced from Java 8 onward that transformed Java into a more expressive, concise, and functional programming language.

Important additions include:

- Lambda Expressions
- Functional Interfaces
- Method References
- Streams API
- Optional
- New Date and Time API

---

# Why Did Java Need Modern Features?

Before Java 8:

- Verbose code
- Repetitive anonymous classes
- Difficult collection processing
- More boilerplate code

Example:

```java
Collections.sort(users, new Comparator<User>() {
    @Override
    public int compare(User u1, User u2) {
        return u1.getName().compareTo(u2.getName());
    }
});
```

Although correct, this code is lengthy.

Modern Java introduced a cleaner approach.

---

# Real World Analogy

Imagine giving instructions to a delivery service.

Traditional Java:

```text
Write an entire document describing every step.
```

Modern Java:

```text
Provide only the required action.
```

Lambda Expressions focus on the behavior rather than the implementation details.

---

# Evolution of Java

## Java Before Version 8

Mainly Object-Oriented.

Everything required classes and objects.

Behavior was difficult to pass around.

---

## Java 8 Revolution

Introduced:

- Functional Programming support
- Lambda Expressions
- Streams API
- Method References

Java became more expressive and powerful.

---

# Functional Programming Basics

## What Is Functional Programming?

Functional Programming is a programming paradigm where behavior can be treated as data.

This means functions can be:

- Passed as parameters
- Returned from methods
- Stored for later execution

---

## OOP vs Functional Programming

### Object-Oriented Programming

Focus:

```text
Objects and State
```

Example:

```java
User user = new User();
```

---

### Functional Programming

Focus:

```text
Behavior and Operations
```

Example:

```java
x -> x * 2
```

---

## Benefits of Functional Programming

- Cleaner code
- Less boilerplate
- Easier collection processing
- Better readability
- Easier parallel processing

---

# Functional Interfaces

## What Is a Functional Interface?

A Functional Interface contains exactly one abstract method.

Example:

```java
@FunctionalInterface
public interface Greeting {

    void sayHello();
}
```

---

## Why Functional Interfaces Exist

Lambda Expressions require a target method.

The Functional Interface provides that target.

---

# Rules of Functional Interfaces

Must contain:

```text
Exactly one abstract method
```

May contain:

- Default methods
- Static methods

Example:

```java
@FunctionalInterface
public interface Calculator {

    int calculate(int a, int b);

    default void print() {
        System.out.println("Calculator");
    }
}
```

---

# Lambda Expressions

## What Is a Lambda Expression?

A Lambda Expression is an anonymous function.

It provides implementation without creating a separate class.

---

# Lambda Syntax

General syntax:

```java
(parameters) -> expression
```

or

```java
(parameters) -> {
    statements
}
```

---

# First Lambda Example

Traditional:

```java
Greeting greeting = new Greeting() {

    @Override
    public void sayHello() {
        System.out.println("Hello");
    }
};
```

Lambda Version:

```java
Greeting greeting =
        () -> System.out.println("Hello");
```

Much shorter and cleaner.

---

# Understanding Lambda Components

Example:

```java
(name) -> System.out.println(name);
```

Components:

Parameter:

```java
(name)
```

Lambda operator:

```java
->
```

Body:

```java
System.out.println(name)
```

---

# Lambda Expression Variations

## No Parameters

```java
() -> System.out.println("Hello");
```

---

## One Parameter

```java
name -> System.out.println(name);
```

---

## Multiple Parameters

```java
(a, b) -> a + b
```

---

## Multiple Statements

```java
(a, b) -> {
    int result = a + b;
    return result;
}
```

---

# Method References

## What Are Method References?

Method References provide an even shorter syntax for Lambdas.

Example:

Lambda:

```java
name -> System.out.println(name)
```

Method Reference:

```java
System.out::println
```

---

# Types of Method References

## Static Method Reference

```java
ClassName::staticMethod
```

Example:

```java
Integer::parseInt
```

---

## Instance Method Reference

```java
object::method
```

Example:

```java
printer::print
```

---

## Constructor Reference

```java
ClassName::new
```

Example:

```java
User::new
```

---

# Built-In Functional Interfaces

Java provides commonly used Functional Interfaces.

---

## Predicate

Represents:

```text
Input → Boolean
```

Example:

```java
Predicate<Integer> isEven =
        number -> number % 2 == 0;
```

---

## Function

Represents:

```text
Input → Output
```

Example:

```java
Function<String, Integer> length =
        text -> text.length();
```

---

## Consumer

Represents:

```text
Input → No Return Value
```

Example:

```java
Consumer<String> printer =
        System.out::println;
```

---

## Supplier

Represents:

```text
No Input → Output
```

Example:

```java
Supplier<String> supplier =
        () -> "Java";
```

---

## BiFunction

Two inputs and one result.

```java
BiFunction<Integer,Integer,Integer>
```

---

## BiConsumer

Two inputs and no result.

---

## UnaryOperator

Input and output are same type.

---

## BinaryOperator

Two same-type inputs produce same-type output.

---

# Variable Capture

## What Is Variable Capture?

Lambdas can access variables outside their body.

Example:

```java
String message = "Hello";

Runnable r =
        () -> System.out.println(message);
```

---

# Effectively Final Variables

This is valid:

```java
String message = "Hello";
```

This is invalid:

```java
message = "Updated";
```

after lambda creation.

Variables used by Lambdas must be effectively final.

---

# Why This Rule Exists

It prevents unexpected behavior and improves thread safety.

---

# Benefits of Lambda Expressions

## Reduced Boilerplate

Less code.

---

## Better Readability

Intent becomes clearer.

---

## Better Collection Processing

Works naturally with Streams.

---

## Functional Programming Support

Allows behavior composition.

---

# Limitations of Lambda Expressions

## Overuse Can Reduce Readability

Complex Lambdas become difficult to understand.

---

## Debugging May Be Harder

Stack traces can become less obvious.

---

# Enterprise Usage

Lambda Expressions are heavily used in:

- Spring Boot
- Stream Processing
- Event Handling
- Data Transformation
- Microservices
- Validation Logic

---

# WorkSphere Relevance

Potential WorkSphere uses include:

- Task filtering
- User filtering
- Sorting projects
- Stream processing
- Validation rules
- Data transformation

Example:

```java
tasks.stream()
     .filter(Task::isCompleted);
```

---

# Common Mistakes

## Writing Complex Lambdas

Avoid large blocks of logic.

---

## Ignoring Readability

Shorter code is not always better.

---

## Misusing Functional Interfaces

Choose the correct interface.

---

## Not Understanding Variable Scope

Learn effectively final rules properly.

---

# Best Practices

- Keep Lambdas small.
- Prefer Method References when readable.
- Use built-in Functional Interfaces.
- Focus on clarity.
- Write expressive code.

---

# Interview Questions

## Beginner

- What is a Lambda Expression?
- Why was it introduced?
- What is a Functional Interface?

---

## Intermediate

- Explain Predicate.
- Explain Function.
- Explain Consumer.
- Explain Supplier.

---

## Advanced

- Explain variable capture.
- What does effectively final mean?
- Lambda vs Anonymous Class?
- Method Reference vs Lambda?

---

## Enterprise

- How does Spring Boot use Lambdas?
- Why are Lambdas important for Streams?
- How do Lambdas improve maintainability?

---

# Summary

In this chapter you learned:

- Modern Java evolution
- Functional Programming basics
- Functional Interfaces
- Lambda Expressions
- Method References
- Built-in Functional Interfaces
- Variable Capture
- Enterprise usage
- Best practices

These concepts form the foundation for the Streams API.

---

# Revision Checklist

Before moving ahead, ensure you can:

- Explain Modern Java.
- Explain Functional Programming.
- Create Functional Interfaces.
- Write Lambda Expressions.
- Use Method References.
- Use Predicate.
- Use Function.
- Use Consumer.
- Use Supplier.
- Explain variable capture.
- Explain effectively final variables.
- Discuss enterprise usage.

---

# Definition of Done

You have completed this chapter when you can:

✓ Write Lambda Expressions confidently.

✓ Create Functional Interfaces.

✓ Use Method References.

✓ Use built-in Functional Interfaces.

✓ Explain variable capture.

✓ Explain Modern Java concepts.

✓ Understand enterprise applications of Lambdas.

✓ Answer Lambda-related interview questions.

---

# What's Next?

07_Java_Streams.md

You will learn:

- Streams API
- Stream Pipelines
- Intermediate Operations
- Terminal Operations
- Filtering
- Mapping
- Sorting
- Collectors
- Parallel Streams
- Stream Best Practices

Streams are one of the most widely used Modern Java features in enterprise backend development.