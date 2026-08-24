# Java Generics

## Chapter Information

**Chapter Number:** 05  
**Chapter Name:** Java Generics  
**Prerequisites:**

- Java Fundamentals
- Object-Oriented Programming (OOP)
- Exception Handling
- Collections Framework

**Next Chapter:**

- Modern Java and Lambda Expressions

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Explain what Generics are.
- Explain why Generics were introduced.
- Understand type safety in Java.
- Create generic classes.
- Create generic methods.
- Create generic interfaces.
- Use type parameters correctly.
- Understand bounded type parameters.
- Understand wildcard usage.
- Understand type erasure.
- Use Generics effectively with Collections.
- Apply Generics in enterprise applications.
- Explain Generics confidently during interviews.

---

# Introduction to Generics

## What Are Generics?

Generics are a Java language feature that allows classes, interfaces, and methods to operate on different data types while maintaining type safety.

In simple terms:

> Generics allow us to write reusable code that works with multiple data types without sacrificing compile-time safety.

A generic component works with placeholders that are replaced with actual types when the code is used.

Example:

```java
List<String> names = new ArrayList<>();
```

Here:

```java
String
```

is supplied as the type argument.

The List knows it should only contain String objects.

---

# Why Were Generics Introduced?

## The Problem Before Generics

Before Java 5, collections stored objects without type information.

Example:

```java
List users = new ArrayList();

users.add("Bhargav");
users.add(100);

String name = (String) users.get(0);
```

Problems:

- No compile-time type checking.
- Frequent type casting.
- Runtime errors.
- Difficult maintenance.
- Less readable code.

A mistake could remain hidden until execution.

Example:

```java
String name = (String) users.get(1);
```

Result:

```text
ClassCastException
```

at runtime.

---

# Real-World Analogy

Imagine a warehouse.

Without labels:

```text
Box
Box
Box
Box
```

Nobody knows what is inside.

With labels:

```text
Books
Electronics
Documents
Clothes
```

The contents become predictable.

Generics act like labels for data containers.

They tell Java exactly what kind of objects should be stored.

---

# Benefits of Generics

Generics provide:

## Type Safety

Errors are caught during compilation.

Example:

```java
List<String> names = new ArrayList<>();

names.add("Bhargav");
names.add(100);
```

Compiler Error:

```text
Integer cannot be converted to String
```

---

## Elimination of Casting

Without Generics:

```java
String name = (String) list.get(0);
```

With Generics:

```java
String name = list.get(0);
```

No manual casting required.

---

## Better Readability

Example:

```java
List<Employee> employees
```

Immediately communicates:

```text
This collection contains Employee objects.
```

---

## Better Maintainability

Large enterprise systems become easier to maintain because data types are explicitly defined.

---

# Generic Type Parameters

Generic placeholders are represented using letters.

Common conventions:

| Symbol | Meaning |
|----------|----------|
| T | Type |
| E | Element |
| K | Key |
| V | Value |
| N | Number |
| R | Result |

Example:

```java
public class Box<T> {

}
```

Here:

```java
T
```

can represent any type.

---

# Creating a Generic Class

## Without Generics

```java
public class Box {

    private Object value;

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
```

Usage:

```java
Box box = new Box();

box.setValue("Java");

String value = (String) box.getValue();
```

Casting is required.

---

## With Generics

```java
public class Box<T> {

    private T value;

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
```

Usage:

```java
Box<String> box = new Box<>();

box.setValue("Java");

String value = box.getValue();
```

No casting needed.

Type-safe.

Cleaner.

Safer.

---

# Multiple Type Parameters

A class may use more than one type parameter.

Example:

```java
public class Pair<K, V> {

    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
```

Usage:

```java
Pair<Integer, String> user =
        new Pair<>(1, "Bhargav");
```

Common enterprise usage:

```java
ID + DTO
Key + Value
Request + Response
```

---

# Generic Methods

A method can be generic even if the class itself is not.

Example:

```java
public class Utility {

    public static <T> void print(T value) {
        System.out.println(value);
    }
}
```

Usage:

```java
Utility.print("Java");
Utility.print(100);
Utility.print(true);
```

Output:

```text
Java
100
true
```

---

# Understanding Generic Method Syntax

Example:

```java
public static <T> T getValue(T value)
```

Breakdown:

```java
<T>
```

declares a type parameter.

```java
T
```

before the method name indicates the return type.

```java
(T value)
```

is the parameter type.

---

# Generic Interfaces

Interfaces can also use Generics.

Example:

```java
public interface Repository<T> {

    void save(T entity);

    T findById(Long id);
}
```

Implementation:

```java
public class UserRepository
        implements Repository<User> {

}
```

---

# Generics and Collections

Collections are where Generics are used most frequently.

Example:

```java
List<String> names
```

means:

```text
Only String objects can be added.
```

---

## Generic List

```java
List<String> names =
        new ArrayList<>();

names.add("Java");
names.add("Spring");
```

Valid.

---

```java
names.add(100);
```

Invalid.

Compiler error.

---

## Generic Set

```java
Set<Integer> ids =
        new HashSet<>();
```

Only Integer values are allowed.

---

## Generic Map

```java
Map<Long, User> users =
        new HashMap<>();
```

Meaning:

```text
Key → Long
Value → User
```

Very common in enterprise systems.

---

# Raw Types

A raw type ignores Generics.

Example:

```java
List list = new ArrayList();
```

This compiles but is discouraged.

Problems:

- Loses type safety.
- Generates warnings.
- Increases runtime risks.

Preferred:

```java
List<String> list =
        new ArrayList<>();
```

Always prefer parameterized types.

---

# Bounded Type Parameters

Sometimes we want to restrict allowed types.

Example:

```java
public class Calculator<T extends Number> {

}
```

Now only subclasses of Number are allowed.

Valid:

```java
Calculator<Integer>
Calculator<Double>
Calculator<Long>
```

Invalid:

```java
Calculator<String>
```

Compiler Error.

---

# Why Bounded Types Exist

Suppose we want mathematical operations.

Without restriction:

```java
T
```

might become:

```java
String
Employee
Project
```

which makes no sense for calculations.

Bounded types guarantee suitable data types.

---

# Example of Bounded Generic Class

```java
public class NumberBox<T extends Number> {

    private T number;

    public NumberBox(T number) {
        this.number = number;
    }

    public double getDoubleValue() {
        return number.doubleValue();
    }
}
```

# Multiple Bounds

Java allows multiple bounds for generic type parameters.

Example:

```java
public class DataProcessor
        <T extends Number & Comparable<T>> {

}
```

Meaning:

```text
T must:
1. Be a Number
2. Implement Comparable
```

Valid:

```java
Integer
Long
Double
```

These classes satisfy both requirements.

---

# Wildcards

Wildcards increase the flexibility of Generic code.

The wildcard symbol is:

```java
?
```

Meaning:

```text
Unknown Type
```

---

# Unbounded Wildcards

Example:

```java
public void print(List<?> list)
```

This method can accept:

```java
List<String>
List<Integer>
List<User>
List<Project>
```

Usage:

```java
public static void print(List<?> list) {

    for (Object item : list) {
        System.out.println(item);
    }
}
```

Purpose:

```text
Read data when type is not important.
```

---

# Upper Bounded Wildcards

Syntax:

```java
? extends Type
```

Example:

```java
public void process(List<? extends Number> numbers)
```

Accepted:

```java
List<Integer>
List<Double>
List<Long>
```

Rejected:

```java
List<String>
```

---

## Real Example

```java
public static double sum(
        List<? extends Number> numbers) {

    double total = 0;

    for (Number number : numbers) {
        total += number.doubleValue();
    }

    return total;
}
```

Usage:

```java
List<Integer> numbers =
        Arrays.asList(1, 2, 3);

double result = sum(numbers);
```

---

# Lower Bounded Wildcards

Syntax:

```java
? super Type
```

Example:

```java
List<? super Integer>
```

Meaning:

```text
Integer
Number
Object
```

are all valid.

---

## Example

```java
public static void addNumbers(
        List<? super Integer> list) {

    list.add(100);
}
```

Purpose:

```text
Useful when adding values.
```

---

# Understanding PECS

One of the most popular interview topics.

PECS means:

```text
Producer Extends
Consumer Super
```

Rule:

If data is being read:

```java
? extends T
```

If data is being inserted:

```java
? super T
```

---

# Wildcard Comparison

## Unbounded

```java
List<?>
```

Use when:

```text
Type does not matter.
```

---

## Upper Bounded

```java
List<? extends Number>
```

Use when:

```text
Reading values from related subclasses.
```

---

## Lower Bounded

```java
List<? super Integer>
```

Use when:

```text
Adding values safely.
```

---

# Type Erasure

## What Is Type Erasure?

Type Erasure is the mechanism Java uses to implement Generics.

Generics exist only during compilation.

After compilation:

```java
List<String>
```

becomes:

```java
List
```

at runtime.

---

# Why Type Erasure Exists

When Generics were introduced in Java 5:

Java had to remain compatible with older code.

Type Erasure allowed:

```text
Old code and new code
to work together.
```

---

# Example

Source Code:

```java
List<String> names =
        new ArrayList<>();
```

After Compilation:

```java
List names =
        new ArrayList();
```

The compiler inserts the necessary type checks.

---

# Limitations Caused by Type Erasure

## Cannot Create Generic Arrays

Invalid:

```java
T[] values = new T[10];
```

---

## Cannot Instantiate Type Parameter

Invalid:

```java
T object = new T();
```

---

## Cannot Use Primitive Types

Invalid:

```java
List<int>
```

Correct:

```java
List<Integer>
```

Autoboxing handles conversion.

---

# Generics Best Practices

## Program to Interfaces

Preferred:

```java
List<User> users =
        new ArrayList<>();
```

Not:

```java
ArrayList<User> users =
        new ArrayList<>();
```

---

## Avoid Raw Types

Avoid:

```java
List list =
        new ArrayList();
```

Use:

```java
List<String> list =
        new ArrayList<>();
```

---

## Use Meaningful Type Parameters

Standard names:

```java
T
E
K
V
R
```

make code easier to understand.

---

## Prefer Compile-Time Safety

Allow the compiler to catch mistakes.

Compiler errors are much cheaper than production bugs.

---

# Generics in Enterprise Applications

Generics are used extensively in enterprise development.

Examples include:

## Repository Layer

```java
Repository<User>
```

---

## API Responses

```java
ResponseEntity<User>
```

---

## Collections

```java
List<Project>
Set<Role>
Map<Long, User>
```

---

## DTO Handling

```java
Page<TaskDTO>
```

---

## Service Layer Utilities

```java
GenericValidator<T>
```

---

## Framework Development

Spring Framework itself uses Generics heavily.

Examples:

```java
JpaRepository<User, Long>

ResponseEntity<String>

Optional<Employee>
```

Understanding Generics is essential for understanding modern Spring Boot code.

---

# WorkSphere Relevance

Although implementation may not yet exist, Generics are expected to be useful throughout WorkSphere.

Possible examples include:

```java
List<User>
List<Project>
List<Task>
```

Repository operations:

```java
Repository<Project>
Repository<Task>
```

API responses:

```java
ResponseEntity<TaskDTO>
```

Pagination:

```java
Page<ProjectDTO>
```

Understanding Generics will make future Spring Boot and microservices development significantly easier.

---

# Common Mistakes

## Using Raw Types

Bad:

```java
List list =
        new ArrayList();
```

---

## Ignoring Compiler Warnings

Compiler warnings often identify type-safety issues.

---

## Overusing Wildcards

Wildcards should improve readability.

They should not make code harder to understand.

---

## Confusing extends and super

Remember:

```text
Producer → Extends
Consumer → Super
```

---

## Forgetting Type Erasure

Generics provide compile-time safety.

They do not preserve type information at runtime.

---

# Advantages of Generics

## Type Safety

Compile-time checking.

---

## Reusability

One implementation works with many data types.

---

## Reduced Casting

Cleaner code.

---

## Better Maintainability

Large systems become easier to manage.

---

## Better Readability

Data intent becomes obvious.

---

# Limitations of Generics

## Type Erasure

Runtime information is removed.

---

## No Primitive Types

Must use wrapper classes.

---

## Additional Learning Curve

Wildcards and bounds can be difficult initially.

---

# Interview Questions

## Beginner Level

### What are Generics?

### Why were Generics introduced?

### What problem do they solve?

### What is type safety?

### What are raw types?

---

## Intermediate Level

### What is a generic class?

### What is a generic method?

### What is a generic interface?

### What are bounded type parameters?

### What are wildcards?

---

## Advanced Level

### Explain Type Erasure.

### What is PECS?

### Difference between:

```java
List<T>
List<?>
List<? extends T>
List<? super T>
```

### Why can't primitive types be used with Generics?

### What limitations does Type Erasure create?

---

## Enterprise Level

### How does Spring Boot use Generics?

### How are Generics used in repositories?

### How are Generics used in API responses?

### How do Generics improve maintainability in large systems?

### How might Generics be used in WorkSphere?

---

# Summary

Generics were introduced to provide reusable and type-safe programming.

Key concepts learned:

- Generic Classes
- Generic Methods
- Generic Interfaces
- Type Parameters
- Bounded Types
- Wildcards
- Type Erasure
- PECS Principle
- Enterprise Usage
- Best Practices

Generics are one of the most heavily used features in modern Java development and form an essential foundation for Spring, Spring Boot, and enterprise backend engineering.

---

# Revision Checklist

Before moving to the next chapter, verify that you can:

- Explain why Generics exist.
- Explain type safety.
- Create a generic class.
- Create a generic method.
- Create a generic interface.
- Use multiple type parameters.
- Explain bounded types.
- Use wildcard syntax correctly.
- Explain PECS.
- Explain Type Erasure.
- Use Generics with Collections.
- Explain enterprise use cases.
- Answer common interview questions.

---

# Definition of Done

You have successfully completed this chapter when you can:

✓ Explain Generics clearly.

✓ Use Generic classes professionally.

✓ Use Generic methods confidently.

✓ Understand wildcard behavior.

✓ Apply PECS correctly.

✓ Explain Type Erasure.

✓ Work comfortably with Generic Collections.

✓ Understand how Spring Boot uses Generics.

✓ Recognize enterprise use cases.

✓ Answer beginner, intermediate, and advanced interview questions related to Generics.

---

# What's Next?

Next Chapter:

06_Modern_Java_and_Lambda_Expressions.md

In the next chapter you will learn:

- Functional Programming Concepts
- Functional Interfaces
- Lambda Expressions
- Method References
- Built-in Functional Interfaces
- Modern Java Development Practices

These concepts build directly on top of Collections and Generics and prepare you for the Streams API.