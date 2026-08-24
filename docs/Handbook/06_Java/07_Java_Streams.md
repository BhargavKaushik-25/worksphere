# Java Streams

## Chapter Information

**Chapter Number:** 07

**Chapter Name:** Java Streams

### Prerequisites

- Java Fundamentals
- OOP
- Exception Handling
- Collections
- Generics
- Modern Java and Lambda Expressions

### Next Chapter

- Java Optional

---

# Learning Objectives

By the end of this chapter, you should be able to:

- Explain what Streams are.
- Explain why Streams were introduced.
- Understand Stream pipelines.
- Differentiate between collections and streams.
- Use intermediate operations.
- Use terminal operations.
- Filter data efficiently.
- Transform data using map().
- Sort data using Stream operations.
- Work with Collectors.
- Understand Parallel Streams.
- Apply Streams in enterprise applications.
- Explain Streams confidently during interviews.

---

# Introduction to Streams

## What Is a Stream?

A Stream is a sequence of elements that supports processing operations.

It allows operations such as:

- Filtering
- Mapping
- Sorting
- Grouping
- Aggregating

to be performed in a clean and readable way.

Streams do not store data.

They process data from a source.

---

# Why Were Streams Introduced?

Before Java 8, operations on collections required verbose code.

Example:

```java
List<String> names = Arrays.asList(
        "Bhargav",
        "Java",
        "Spring"
);

for(String name : names){
    if(name.startsWith("J")){
        System.out.println(name);
    }
}
```

This works but becomes difficult when processing large datasets.

Streams provide a more expressive approach.

Example:

```java
names.stream()
     .filter(name -> name.startsWith("J"))
     .forEach(System.out::println);
```

Cleaner.

More readable.

Easier to maintain.

---

# Real-World Analogy

Imagine a water treatment plant.

Water enters.

It passes through multiple filters.

The final purified water exits.

```text
Source
   ↓
Filter
   ↓
Transform
   ↓
Sort
   ↓
Result
```

A Stream pipeline behaves similarly.

Data flows through operations and produces a result.

---

# Streams vs Collections

## Collections

Collections store data.

Example:

```java
List<String> names =
        new ArrayList<>();
```

Responsibility:

```text
Store Data
```

---

## Streams

Streams process data.

Example:

```java
names.stream()
```

Responsibility:

```text
Process Data
```

---

# Important Characteristics of Streams

## Streams Do Not Store Data

They work on data from sources.

---

## Streams Do Not Modify Original Data

Example:

```java
List<String> names =
        List.of("java", "spring");
```

Transforming:

```java
names.stream()
     .map(String::toUpperCase)
```

does not change the original list.

---

## Streams Support Functional Programming

Streams integrate naturally with:

- Lambda Expressions
- Method References
- Functional Interfaces

---

# Creating Streams

## From Collections

```java
List<String> names =
        List.of("Java", "Spring");

Stream<String> stream =
        names.stream();
```

---

## From Arrays

```java
String[] names =
        {"Java", "Spring"};

Stream<String> stream =
        Arrays.stream(names);
```

---

## Using Stream.of()

```java
Stream<String> stream =
        Stream.of("Java", "Spring");
```

---

# Understanding Stream Pipeline

A Stream pipeline consists of:

```text
Source
    ↓
Intermediate Operations
    ↓
Terminal Operation
```

Example:

```java
names.stream()
     .filter(name -> name.startsWith("J"))
     .map(String::toUpperCase)
     .forEach(System.out::println);
```

Pipeline:

```text
Source
↓
filter()
↓
map()
↓
forEach()
```

---

# Intermediate Operations

Intermediate operations produce another Stream.

They are lazy.

They execute only when a terminal operation is called.

---

## filter()

Used to select matching elements.

Example:

```java
List<Integer> numbers =
        List.of(1,2,3,4,5);

numbers.stream()
       .filter(n -> n % 2 == 0)
       .forEach(System.out::println);
```

Output:

```text
2
4
```

---

## map()

Transforms data.

Example:

```java
names.stream()
     .map(String::toUpperCase)
     .forEach(System.out::println);
```

Output:

```text
JAVA
SPRING
```

---

## sorted()

Sorts elements.

Example:

```java
numbers.stream()
       .sorted()
       .forEach(System.out::println);
```

---

## distinct()

Removes duplicate values.

Example:

```java
Stream.of(1,1,2,2,3)
      .distinct()
      .forEach(System.out::println);
```

Output:

```text
1
2
3
```

---

## limit()

Returns the first n elements.

Example:

```java
Stream.of(1,2,3,4,5)
      .limit(3)
      .forEach(System.out::println);
```

Output:

```text
1
2
3
```

---

## skip()

Skips elements.

Example:

```java
Stream.of(1,2,3,4,5)
      .skip(2)
      .forEach(System.out::println);
```

Output:

```text
3
4
5
```

---

# Terminal Operations

Terminal operations produce a final result.

After a terminal operation, the Stream is closed.

---

## forEach()

Used for processing each element.

```java
names.stream()
     .forEach(System.out::println);
```

---

## collect()

Converts a stream result into a collection.

Example:

```java
List<String> result =
        names.stream()
             .map(String::toUpperCase)
             .collect(Collectors.toList());
```

---

## count()

Returns element count.

```java
long count =
        names.stream()
             .count();
```

---

## findFirst()

Returns first element.

```java
Optional<String> result =
        names.stream()
             .findFirst();
```

---

## anyMatch()

Returns true if any element matches.

```java
boolean found =
        names.stream()
             .anyMatch(
                name -> name.startsWith("J")
             );
```

---

## allMatch()

Returns true if all elements match.

```java
boolean valid =
        numbers.stream()
               .allMatch(
                   n -> n > 0
               );
```

---

## noneMatch()

Returns true when no elements match.

```java
boolean result =
        names.stream()
             .noneMatch(
                 String::isEmpty
             );
```

---

# Stream Mapping

One of the most common enterprise operations.

Example:

```java
List<UserDTO> dtos =
        users.stream()
             .map(user ->
                  new UserDTO(
                        user.getId(),
                        user.getName()
                  )
              )
             .collect(Collectors.toList());
```

Used extensively in Spring Boot applications.

---

# Stream Filtering

Common example:

```java
tasks.stream()
     .filter(Task::isCompleted)
     .forEach(System.out::println);
```

Useful for business rules and validations.

---

# Stream Sorting

## Natural Sorting

```java
numbers.stream()
       .sorted()
       .forEach(System.out::println);
```

---

## Custom Sorting

```java
users.stream()
     .sorted(
         Comparator.comparing(
             User::getName
         )
     )
     .forEach(System.out::println);
```

---

# Collectors

Collectors allow aggregation of Stream results.

---

## toList()

```java
.collect(Collectors.toList())
```

---

## toSet()

```java
.collect(Collectors.toSet())
```

---

## joining()

```java
.collect(Collectors.joining(","))
```

Output:

```text
Java,Spring,Docker
```

---

## groupingBy()

Example:

```java
Map<String,
    List<Employee>> employees
=
employeeList.stream()
            .collect(
                Collectors.groupingBy(
                    Employee::getDepartment
                )
            );
```

Very important enterprise concept.

---

## counting()

```java
Collectors.counting()
```

Useful for reporting.

---

# Stream Reduction

Reduction combines elements into a single result.

Example:

```java
int total =
        numbers.stream()
               .reduce(
                    0,
                    Integer::sum
               );
```

Output:

```text
15
```

---

# Parallel Streams

## What Are Parallel Streams?

Parallel Streams divide processing across multiple threads.

Example:

```java
numbers.parallelStream()
       .forEach(System.out::println);
```

---

# Advantages

Potential benefits:

- Faster processing
- Better CPU utilization
- Large dataset processing

---

# Limitations

Not always faster.

Potential issues:

- Thread management overhead
- Less predictable execution
- Harder debugging

Use only after measuring performance.

---

# Stream Best Practices

## Keep Pipelines Readable

Good:

```java
users.stream()
     .filter(User::isActive)
     .map(User::getName)
```

Avoid extremely long pipelines.

---

## Prefer Method References

Good:

```java
User::getName
```

instead of:

```java
user -> user.getName()
```

when readability improves.

---

## Avoid Side Effects

Streams should focus on transformations.

Avoid modifying external state.

---

# Common Mistakes

## Reusing Streams

Invalid:

```java
Stream<String> stream =
        names.stream();

stream.count();
stream.forEach(System.out::println);
```

Stream already consumed.

---

## Overusing Parallel Streams

Not every problem benefits.

---

## Complex Lambda Expressions

Keep pipelines simple.

---

## Ignoring Null Values

Streams cannot automatically handle null references safely.

---

# Enterprise Usage

Streams are heavily used for:

- DTO mapping
- Data transformation
- Filtering business data
- Reporting
- Aggregation
- API response construction
- Validation logic

Modern Spring Boot applications use Streams extensively.

---

# WorkSphere Relevance

Possible examples:

## User Processing

```java
users.stream()
```

---

## Project Filtering

```java
projects.stream()
```

---

## Task Completion Reports

```java
tasks.stream()
```

---

## Dashboard Statistics

```java
tasks.stream()
     .count()
```

---

## DTO Mapping

```java
entities.stream()
        .map(...)
```

Streams will likely become one of the most frequently used Java features throughout WorkSphere development.

---

# Interview Questions

## Beginner

- What is a Stream?
- Why were Streams introduced?
- Stream vs Collection?

---

## Intermediate

- What is a Stream pipeline?
- Explain filter().
- Explain map().
- Explain collect().
- Explain reduce().

---

## Advanced

- Lazy Evaluation?
- Intermediate vs Terminal Operations?
- Parallel Streams?
- Reduce vs Collect?
- Stream Reuse?

---

## Enterprise

- How are Streams used in Spring Boot?
- Why are Streams useful for DTO mapping?
- When should Parallel Streams be avoided?
- How can Streams improve readability?

---

# Summary

In this chapter you learned:

- Stream Fundamentals
- Stream Pipelines
- Intermediate Operations
- Terminal Operations
- Collectors
- Reduction
- Parallel Streams
- Best Practices
- Enterprise Usage

Streams are one of the most important Modern Java features and are heavily used in enterprise backend applications.

---

# Revision Checklist

Before moving ahead, ensure you can:

- Create Streams.
- Explain Stream Pipeline.
- Use filter().
- Use map().
- Use sorted().
- Use collect().
- Use reduce().
- Use Collectors.
- Explain Parallel Streams.
- Explain enterprise usage.
- Answer interview questions.

---

# Definition of Done

You have completed this chapter when you can:

✓ Create Stream pipelines confidently.

✓ Distinguish intermediate and terminal operations.

✓ Use filtering and mapping effectively.

✓ Transform collections into business results.

✓ Use Collectors appropriately.

✓ Explain Parallel Streams.

✓ Understand enterprise usage.

✓ Answer Stream-related interview questions.

---

# What's Next?

08_Java_Optional.md

You will learn:

- Null Problems in Java
- Optional Class
- Optional Creation
- Optional Operations
- Functional Processing
- Best Practices
- Enterprise Usage

Optional complements Streams and helps write safer, cleaner, and more expressive Java code.