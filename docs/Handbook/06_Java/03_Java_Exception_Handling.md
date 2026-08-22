# Java Exception Handling

## 1. Introduction to Exception Handling

Exception handling is the mechanism Java provides for detecting, communicating, and handling abnormal conditions that occur during program execution.

A program can encounter situations such as:

* Invalid input
* Missing files
* Database connectivity problems
* Invalid numeric conversion
* Accessing an invalid array index
* Attempting to use a `null` reference
* A requested resource being unavailable

Without appropriate handling, such conditions can terminate the current operation or application unexpectedly.

Java provides structured mechanisms for dealing with such situations, including:

* `try`
* `catch`
* `finally`
* `throw`
* `throws`
* Custom exceptions
* `try-with-resources`

The purpose of exception handling is not to hide errors. It is to make program behavior predictable when exceptional conditions occur.

A useful conceptual flow is:

```text
Normal execution
       ↓
Exceptional condition occurs
       ↓
Exception object is created/thrown
       ↓
Matching handler is searched
       ↓
Exception handled or propagated
       ↓
Program continues or operation fails appropriately
```

Exception handling should be designed as part of application behavior rather than added only after failures occur.

---

## 2. Errors vs Exceptions

Java distinguishes between `Error` and `Exception`.

Both are part of the broader `Throwable` hierarchy, but they represent different categories of problems.

### 2.1 Exceptions

Exceptions generally represent conditions that application code may be able to handle.

Examples include:

```java
IOException
SQLException
IllegalArgumentException
NullPointerException
NumberFormatException
```

For example:

```java
int number = Integer.parseInt("abc");
```

This can result in a `NumberFormatException`.

An application may validate the input or handle the exception appropriately.

### 2.2 Errors

`Error` generally represents serious conditions associated with the runtime environment or JVM.

Examples include:

```java
OutOfMemoryError
StackOverflowError
NoClassDefFoundError
```

Application code generally should not attempt to recover from every `Error`.

### 2.3 Important distinction

A simplified conceptual distinction is:

```text
Throwable
├── Error
└── Exception
```

Exceptions are normally the primary focus of application-level exception handling.

---

## 3. Exception Hierarchy

Java's exception hierarchy begins with `Throwable`.

A simplified structure is:

```text
Throwable
├── Error
└── Exception
    ├── RuntimeException
    └── Other checked exceptions
```

Examples:

```text
Exception
├── IOException
├── SQLException
├── RuntimeException
│   ├── NullPointerException
│   ├── IllegalArgumentException
│   ├── NumberFormatException
│   └── IndexOutOfBoundsException
└── ...
```

This hierarchy is important because Java determines exception handling behavior partly through inheritance relationships.

For example:

```java
catch (Exception e) {
}
```

can catch many exception types because they inherit from `Exception`.

However, catching a broad parent type can reduce the specificity of error handling.

---

## 4. Checked Exceptions

Checked exceptions are exceptions that the compiler requires the program to account for.

They are generally exceptions other than `RuntimeException` and its subclasses, and they must be either:

* Caught using `try-catch`, or
* Declared using `throws`

For example, file operations can involve `IOException`.

```java
import java.io.IOException;

public class FileReaderExample {

    public static void readFile() throws IOException {
        // File operation
    }
}
```

The method declares that it may result in an `IOException`.

### 4.1 Why checked exceptions exist

Checked exceptions communicate that a caller should consider a condition that may reasonably occur during the operation.

Examples include:

* File access failure
* Network-related I/O failure
* Certain database-related failures

### 4.2 Catching a checked exception

```java
try {
    readFile();
} catch (IOException e) {
    System.out.println("Unable to read file");
}
```

The compiler requires appropriate handling when the called method declares a checked exception.

---

## 5. Unchecked Exceptions

Unchecked exceptions are exceptions derived from `RuntimeException`.

The compiler does not require them to be explicitly caught or declared.

Common examples include:

* `NullPointerException`
* `IllegalArgumentException`
* `ArithmeticException`
* `NumberFormatException`
* `IndexOutOfBoundsException`
* `ClassCastException`

Example:

```java
int value = Integer.parseInt("abc");
```

This can produce:

```text
NumberFormatException
```

### 5.1 Why unchecked exceptions exist

Unchecked exceptions commonly indicate programming errors, invalid assumptions, or invalid state.

For example:

```java
String name = null;
name.length();
```

The resulting `NullPointerException` often indicates that the program failed to ensure the reference was valid.

### 5.2 Do not catch everything

Unchecked exceptions should not automatically be caught everywhere.

Instead, developers should first consider why the exception can occur and whether the condition should be prevented or handled at an appropriate boundary.

---

## 6. The `try` Block

A `try` block contains code that may produce an exception.

Example:

```java
try {
    int result = 10 / 0;
}
```

A `try` block normally works together with at least one `catch` or `finally`.

A complete example is:

```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero");
}
```

The potentially failing operation is placed inside the `try` block.

### 6.1 Keep `try` blocks focused

Avoid putting large amounts of unrelated code inside a single `try` block.

Prefer:

```java
try {
    readFile();
} catch (IOException e) {
    handleFileError(e);
}
```

over putting the entire application operation inside one enormous `try` block.

A focused `try` block makes it clearer which operation can fail.

---

## 7. The `catch` Block

A `catch` block handles a matching exception.

Example:

```java
try {
    int value = Integer.parseInt("abc");
} catch (NumberFormatException e) {
    System.out.println("Invalid number");
}
```

The variable:

```java
e
```

contains the exception object.

### 7.1 Exception information

The exception object can provide useful information:

```java
e.getMessage();
```

and:

```java
e.printStackTrace();
```

`getMessage()` can provide a descriptive message.

`printStackTrace()` prints diagnostic information including the call stack.

In production systems, exception details should generally be recorded through the application's logging mechanism rather than relying on direct console output.

### 7.2 Catching a parent exception

This is possible:

```java
try {
    performOperation();
} catch (Exception e) {
    handleException(e);
}
```

However, broad exception handling should be used carefully.

---

## 8. Multiple `catch` Blocks

A `try` block can have multiple `catch` blocks.

Example:

```java
try {
    int value = Integer.parseInt(input);
    int result = 100 / value;
} catch (NumberFormatException e) {
    System.out.println("Input is not a valid number");
} catch (ArithmeticException e) {
    System.out.println("Division by zero is not allowed");
}
```

### 8.1 Order matters

More specific exceptions must be caught before broader exceptions.

Correct:

```java
try {
    performOperation();
} catch (NumberFormatException e) {
    handleNumberError(e);
} catch (RuntimeException e) {
    handleRuntimeError(e);
}
```

Incorrect:

```java
try {
    performOperation();
} catch (RuntimeException e) {
    handleRuntimeError(e);
} catch (NumberFormatException e) {
    handleNumberError(e);
}
```

The second `catch` becomes unreachable because `NumberFormatException` is already covered by `RuntimeException`.

### 8.2 Multi-catch

Java also supports handling multiple unrelated exception types in one `catch` block:

```java
try {
    performOperation();
} catch (IOException | SQLException e) {
    handleExternalOperationError(e);
}
```

This is useful when the handling logic is genuinely the same.

---

## 9. The `finally` Block

The `finally` block contains code intended to execute after the `try`/`catch` processing.

Example:

```java
try {
    performOperation();
} catch (Exception e) {
    handleException(e);
} finally {
    cleanup();
}
```

`finally` is traditionally used for cleanup operations.

### 9.1 Important behavior

A `finally` block generally executes whether the operation succeeds or an exception occurs.

However, there are extreme circumstances in which it may not execute, such as abrupt JVM termination.

Therefore, `finally` should not be treated as an absolute guarantee under every possible runtime condition.

### 9.2 Avoid returning from `finally`

Avoid code such as:

```java
try {
    return calculate();
} finally {
    return anotherValue();
}
```

Returning from `finally` can override the original return value or exception and make program behavior difficult to understand.

---

## 10. `throw`

The `throw` statement explicitly throws an exception.

Example:

```java
if (age < 0) {
    throw new IllegalArgumentException("Age cannot be negative");
}
```

The program creates an exception object and throws it.

### 10.1 Why explicitly throw exceptions?

A method may detect invalid input or an invalid state and communicate the problem to its caller.

Example:

```java
public void setAge(int age) {
    if (age < 0) {
        throw new IllegalArgumentException("Age cannot be negative");
    }

    this.age = age;
}
```

This prevents an invalid state from being accepted.

### 10.2 Throwing custom exceptions

A custom exception can also be thrown:

```java
throw new EmployeeNotFoundException("Employee not found");
```

Custom exceptions are discussed later in this chapter.

---

## 11. `throws`

The `throws` keyword declares exceptions that a method may propagate to its caller.

Example:

```java
public void readFile() throws IOException {
    // File operation
}
```

The method is not necessarily handling the exception itself.

It is communicating:

```text
This method may result in IOException.
```

The caller can then decide how to handle it.

### 11.1 `throw` versus `throws`

`throw`:

```java
throw new IllegalArgumentException();
```

actually throws an exception.

`throws`:

```java
void read() throws IOException
```

declares that a method may propagate an exception.

A simple comparison:

| Keyword  | Purpose                                    |
| -------- | ------------------------------------------ |
| `throw`  | Explicitly throws an exception             |
| `throws` | Declares exceptions a method may propagate |

---

## 12. Exception Propagation

If a method does not handle an exception, the exception can propagate to its caller.

Consider:

```java
public void methodA() {
    methodB();
}
```

Then:

```java
public void methodB() {
    methodC();
}
```

And:

```java
public void methodC() {
    throw new RuntimeException("Failure");
}
```

The exception can propagate:

```text
methodC()
   ↓
methodB()
   ↓
methodA()
   ↓
caller
```

The runtime searches the call stack for an appropriate handler.

### 12.1 Handling at an appropriate boundary

A useful design principle is to handle an exception where the application has enough context to make a meaningful decision.

For example:

* A low-level component may report a database failure.
* A service layer may translate the failure into a meaningful application-level exception.
* An API boundary may convert that exception into an appropriate response.

The exact architecture depends on the application.

---

## 13. Creating Custom Exceptions

Custom exceptions allow an application to represent domain-specific exceptional conditions.

Example:

```java
public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
```

Usage:

```java
throw new EmployeeNotFoundException("Employee not found");
```

### 13.1 Why custom exceptions?

A custom exception can communicate more clearly what went wrong.

Instead of:

```java
throw new RuntimeException("Something went wrong");
```

a domain-specific exception communicates intent:

```java
throw new EmployeeNotFoundException("Employee not found");
```

### 13.2 Checked custom exception

A custom exception can extend `Exception`:

```java
public class ExternalServiceException extends Exception {

    public ExternalServiceException(String message) {
        super(message);
    }
}
```

This creates a checked exception.

### 13.3 Unchecked custom exception

A custom exception can extend `RuntimeException`:

```java
public class InvalidEmployeeException extends RuntimeException {

    public InvalidEmployeeException(String message) {
        super(message);
    }
}
```

This creates an unchecked exception.

### 13.4 Choosing between checked and unchecked

The choice should be based on the application's API and error-handling model.

Do not create checked exceptions merely because they seem more formal.

Likewise, do not make every exception unchecked without considering whether callers should be explicitly required to account for the condition.

---

## 14. `try-with-resources`

`try-with-resources` provides a structured way to work with resources that implement `AutoCloseable`.

Examples of resources include:

* Files
* Streams
* Database resources
* Other closeable resources

A basic example:

```java
try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
    String line = reader.readLine();
}
```

The resource is automatically closed when the `try` block finishes.

### 14.1 Why it is preferred

Before `try-with-resources`, developers often used `finally` for manual cleanup:

```java
Resource resource = null;

try {
    resource = openResource();
} finally {
    if (resource != null) {
        resource.close();
    }
}
```

`try-with-resources` is usually clearer and safer:

```java
try (Resource resource = openResource()) {
    useResource(resource);
}
```

### 14.2 Multiple resources

Multiple resources can be declared:

```java
try (
    ResourceOne first = openFirst();
    ResourceTwo second = openSecond()
) {
    useResources(first, second);
}
```

They are closed automatically in reverse order of creation.

### 14.3 Resource cleanup

`try-with-resources` is especially important for enterprise applications because resources such as files, database connections, and streams should not remain open unnecessarily.

---

## 15. Common Exception Types

### 15.1 `NullPointerException`

Occurs when code attempts an operation on a `null` reference that requires an actual object.

Example:

```java
String name = null;
name.length();
```

### 15.2 `IllegalArgumentException`

Indicates that a method received an inappropriate argument.

Example:

```java
if (age < 0) {
    throw new IllegalArgumentException("Age cannot be negative");
}
```

### 15.3 `ArithmeticException`

Can occur during invalid arithmetic operations.

Example:

```java
int result = 10 / 0;
```

### 15.4 `NumberFormatException`

Occurs when text cannot be converted to the expected numeric representation.

```java
int age = Integer.parseInt("abc");
```

### 15.5 `ArrayIndexOutOfBoundsException`

Occurs when an array is accessed using an invalid index.

```java
int[] numbers = {1, 2, 3};

System.out.println(numbers[3]);
```

### 15.6 `IndexOutOfBoundsException`

A broader exception associated with invalid indexes in various indexed structures.

### 15.7 `ClassCastException`

Occurs when an object is incorrectly cast to an incompatible type.

```java
Object value = "Java";
Integer number = (Integer) value;
```

### 15.8 `IOException`

A checked exception associated with many input/output operations.

It can represent problems such as:

* File access failures
* Stream failures
* Other I/O conditions

### 15.9 `SQLException`

A checked exception associated with many JDBC database operations.

It can communicate problems related to database interaction.

---

## 16. Exception Handling Strategy

Good exception handling involves more than adding `try-catch` everywhere.

A practical strategy is:

```text
1. Prevent invalid conditions when possible
        ↓
2. Detect exceptional conditions
        ↓
3. Throw meaningful exceptions
        ↓
4. Propagate when the current layer cannot handle them
        ↓
5. Handle at an appropriate boundary
        ↓
6. Log useful diagnostic information
        ↓
7. Communicate an appropriate result to the caller
```

### 16.1 Prevent predictable problems

If invalid input can be validated before an operation, validation may be preferable to relying entirely on exceptions.

### 16.2 Use exceptions for exceptional conditions

Do not use exceptions as normal control-flow mechanisms when ordinary conditional logic expresses the situation more clearly.

### 16.3 Preserve useful context

When an exception is translated into another exception, preserve the original cause when appropriate.

Example:

```java
try {
    performDatabaseOperation();
} catch (SQLException e) {
    throw new EmployeeRepositoryException(
        "Unable to load employee",
        e
    );
}
```

The original exception is retained as the cause.

---

## 17. Exception Chaining

Exception chaining means preserving an original exception as the cause of another exception.

Example:

```java
try {
    performOperation();
} catch (IOException e) {
    throw new ServiceException("Service operation failed", e);
}
```

The second argument:

```java
e
```

is the original cause.

### 17.1 Why chaining matters

Without chaining:

```java
throw new ServiceException("Operation failed");
```

the original diagnostic information can be lost.

With chaining:

```java
throw new ServiceException("Operation failed", e);
```

the original cause remains available.

This is especially useful when moving an error across architectural layers.

---

## 18. Handling Multiple Exceptions

Different failures may require different responses.

Example:

```java
try {
    int value = Integer.parseInt(input);
    process(value);
} catch (NumberFormatException e) {
    handleInvalidInput(e);
} catch (IllegalArgumentException e) {
    handleInvalidArgument(e);
}
```

However, avoid creating separate handlers merely because different exception classes exist.

Ask:

* Does the application need different behavior?
* Does the caller need different information?
* Can the exceptions be handled together?
* Is the exception better propagated?

If the handling behavior is identical, multi-catch can be appropriate:

```java
catch (IOException | SQLException e) {
    logFailure(e);
}
```

---

## 19. Logging Exceptions

In enterprise applications, exceptions often need to be recorded for diagnosis.

A logging system should generally capture information such as:

* Exception type
* Message
* Relevant context
* Stack trace
* Operation being performed
* Correlation or request information where available

Avoid logging sensitive information.

For example, passwords, authentication secrets, tokens, and other confidential values should not be included simply because an exception occurred.

### 19.1 Do not silently swallow exceptions

Avoid:

```java
try {
    performOperation();
} catch (Exception e) {
}
```

This hides the failure and makes diagnosis extremely difficult.

If an exception is intentionally handled without being rethrown, the handling should still be meaningful.

---

## 20. Common Beginner Mistakes

### 20.1 Catching `Exception` everywhere

This:

```java
catch (Exception e) {
    System.out.println("Error");
}
```

may hide the actual problem.

Prefer handling the most appropriate exception when the application genuinely needs different behavior.

### 20.2 Empty catch blocks

Avoid:

```java
catch (Exception e) {
}
```

It silently discards the failure.

### 20.3 Using exceptions for normal control flow

Do not deliberately cause exceptions to implement ordinary branching logic.

Use conditions when the situation is expected and ordinary.

### 20.4 Losing the original cause

Avoid:

```java
catch (SQLException e) {
    throw new ServiceException("Database failure");
}
```

when the original cause is important.

Prefer:

```java
catch (SQLException e) {
    throw new ServiceException("Database failure", e);
}
```

### 20.5 Catching too early

A lower-level component may not have enough context to decide how the exception should be handled.

Sometimes propagation is more appropriate.

### 20.6 Catching too late

Allowing every exception to reach the highest possible level can result in poor error messages and inadequate recovery.

Handle failures at a meaningful boundary.

### 20.7 Overusing `finally`

`finally` is useful for cleanup, but `try-with-resources` is generally preferable for `AutoCloseable` resources.

### 20.8 Returning from `finally`

Avoid returning from a `finally` block because it can hide or override the original result or exception.

### 20.9 Logging and rethrowing without purpose

This pattern can create duplicate logs:

```text
Layer A logs
    ↓
Layer B logs
    ↓
Layer C logs
```

A better design determines which layer has responsibility for logging and handling the failure.

### 20.10 Exposing sensitive exception details

Do not return internal stack traces, database details, credentials, or other sensitive information directly to external users.

---

## 21. Exception Handling Best Practices

### 21.1 Catch specific exceptions when possible

Prefer:

```java
catch (NumberFormatException e) {
}
```

over:

```java
catch (Exception e) {
}
```

when only a specific failure is expected.

### 21.2 Preserve the cause

Use exception chaining when translating exceptions across layers.

### 21.3 Provide meaningful messages

Prefer:

```java
throw new IllegalArgumentException("Employee ID must be positive");
```

over:

```java
throw new IllegalArgumentException("Invalid");
```

The message should explain the relevant condition without exposing sensitive information.

### 21.4 Use custom exceptions for meaningful domain failures

When a failure has domain-specific meaning, a custom exception can make the API clearer.

### 21.5 Use `try-with-resources`

Prefer automatic resource management for `AutoCloseable` resources.

### 21.6 Do not swallow exceptions

Every caught exception should have a meaningful reason for being caught.

### 21.7 Keep handlers focused

A handler should perform the necessary response without introducing unrelated business logic.

### 21.8 Avoid over-catching

Catching an exception only to immediately rethrow the same exception without adding value is usually unnecessary.

### 21.9 Validate input

Many invalid conditions can be detected before an operation begins.

### 21.10 Keep external error messages safe

Internal technical details should not automatically be exposed to end users or external API consumers.

---

## 22. Exception Handling in WorkSphere

WorkSphere is an enterprise software project, so predictable handling of failures is important for maintainability and reliability.

The exact WorkSphere exception hierarchy and implementation should be defined by the project's approved architecture and implementation decisions. This chapter therefore establishes the Java foundation without inventing project-specific exception classes or behavior.

At a conceptual level, different application layers may encounter different failures.

For example:

```text
Client / API
      ↓
Application / Service Layer
      ↓
Persistence / Repository Layer
      ↓
Database or External System
```

A lower layer may encounter a technical failure such as an I/O or database exception.

A higher layer may need to translate that failure into a meaningful application-level result.

### 22.1 Example conceptual translation

A repository operation might encounter a database exception:

```java
try {
    loadEmployee();
} catch (SQLException e) {
    throw new EmployeeDataAccessException(
        "Unable to access employee data",
        e
    );
}
```

The service layer can then decide how the failure should affect the business operation.

The example illustrates the principle of exception translation and chaining. It does not establish that these exact classes must exist in WorkSphere.

### 22.2 API boundaries

If an exception reaches an API boundary, the application should generally avoid exposing raw internal stack traces.

Instead, an appropriate external error representation can be produced according to the project's API and error-response design.

### 22.3 Logging

WorkSphere's eventual implementation should establish consistent logging behavior so that failures can be diagnosed without exposing sensitive information.

### 22.4 Resource management

Enterprise applications may interact with resources such as:

* Database connections
* Files
* Streams
* Network resources

Proper resource management is therefore important.

Where applicable, Java's `try-with-resources` mechanism should be considered.

### 22.5 Why exception handling matters

Effective exception handling helps WorkSphere:

* Fail predictably
* Preserve useful diagnostic information
* Avoid resource leaks
* Prevent silent failures
* Separate technical failures from application behavior
* Provide appropriate error responses
* Improve maintainability

The implementation should follow WorkSphere's approved architecture rather than introducing arbitrary exception-handling patterns.

---

## 23. Interview Questions

### Basic questions

1. What is exception handling?
2. Why is exception handling required?
3. What is the difference between an error and an exception?
4. What is the `Throwable` class?
5. What is the difference between checked and unchecked exceptions?
6. Give examples of checked exceptions.
7. Give examples of unchecked exceptions.
8. What is `RuntimeException`?
9. What is the purpose of `try`?
10. What is the purpose of `catch`?
11. What is the purpose of `finally`?
12. What is the purpose of `throw`?
13. What is the purpose of `throws`?
14. What is the difference between `throw` and `throws`?
15. What is exception propagation?
16. What is a custom exception?
17. What is `try-with-resources`?
18. What is exception chaining?
19. Why should the original exception cause be preserved?
20. Why should exceptions not be silently swallowed?

### Practical questions

21. Write code that handles a `NumberFormatException`.
22. Write code that handles division by zero.
23. Write a method that throws an `IllegalArgumentException`.
24. Write a method that declares a checked exception using `throws`.
25. Demonstrate multiple `catch` blocks.
26. Demonstrate multi-catch.
27. Demonstrate a `finally` block.
28. Create a custom unchecked exception.
29. Create a custom checked exception.
30. Demonstrate exception propagation.
31. Demonstrate exception chaining.
32. Demonstrate `try-with-resources`.

### Design questions

33. When should an exception be caught?
34. When should an exception be propagated?
35. Why should broad exception handling be avoided?
36. When should a custom exception be created?
37. Why is `try-with-resources` preferred for resource cleanup?
38. Why should `equals()`-style business conditions not be replaced with exceptions?
39. Why should exception messages be meaningful?
40. Why should sensitive information not be included in exception messages?
41. Where should application-level exception handling occur?
42. How can exception handling support enterprise application reliability?

---

## 24. Summary

Exception handling provides Java applications with a structured way to deal with abnormal conditions.

The major concepts covered in this chapter are:

* Errors versus exceptions
* The `Throwable` hierarchy
* Checked exceptions
* Unchecked exceptions
* `try`
* `catch`
* Multiple `catch` blocks
* Multi-catch
* `finally`
* `throw`
* `throws`
* Exception propagation
* Custom exceptions
* `try-with-resources`
* Common exception types
* Exception chaining
* Logging considerations
* Common mistakes
* Exception-handling best practices
* Enterprise application considerations

The key principle is:

```text
Do not hide failures.
Understand them.
Handle them where appropriate.
Propagate them when necessary.
Preserve useful context.
```

Good exception handling improves the reliability and maintainability of Java applications.

It should complement good design rather than compensate for poor validation or unclear application structure.

---

## 25. Revision Checklist

* [ ] I understand what exception handling means.
* [ ] I understand the difference between `Error` and `Exception`.
* [ ] I understand the basic `Throwable` hierarchy.
* [ ] I understand checked exceptions.
* [ ] I understand unchecked exceptions.
* [ ] I know common checked exception examples.
* [ ] I know common unchecked exception examples.
* [ ] I understand the purpose of `try`.
* [ ] I understand the purpose of `catch`.
* [ ] I understand the purpose of `finally`.
* [ ] I understand the `throw` statement.
* [ ] I understand the `throws` declaration.
* [ ] I can explain `throw` versus `throws`.
* [ ] I understand exception propagation.
* [ ] I understand multiple `catch` blocks.
* [ ] I understand why catch-block order matters.
* [ ] I understand multi-catch.
* [ ] I can create a custom exception.
* [ ] I understand checked custom exceptions.
* [ ] I understand unchecked custom exceptions.
* [ ] I understand `try-with-resources`.
* [ ] I understand why resources should be closed properly.
* [ ] I understand exception chaining.
* [ ] I know how to preserve an original exception cause.
* [ ] I understand common Java exception types.
* [ ] I understand why empty catch blocks are dangerous.
* [ ] I understand why broad exception handling should be avoided.
* [ ] I understand why exceptions should not normally be used for ordinary control flow.
* [ ] I understand why exceptions should not be silently swallowed.
* [ ] I understand basic exception logging considerations.
* [ ] I understand why sensitive information should not be exposed through errors.
* [ ] I understand how exception handling can fit into enterprise application layers.
* [ ] I can explain how exception handling could support WorkSphere without inventing project-specific behavior.

---

## 26. Definition of Done

This chapter is considered complete when the learner can independently:

1. Explain why Java provides exception handling.
2. Distinguish errors from application-level exceptions.
3. Explain the basic `Throwable` hierarchy.
4. Distinguish checked and unchecked exceptions.
5. Identify common Java exception types.
6. Use `try` and `catch` correctly.
7. Use multiple `catch` blocks correctly.
8. Explain why catch-block ordering matters.
9. Use multi-catch where appropriate.
10. Use `finally` appropriately.
11. Explain the purpose of `throw`.
12. Explain the purpose of `throws`.
13. Clearly distinguish `throw` from `throws`.
14. Explain exception propagation.
15. Decide when an exception should be handled locally and when it should be propagated.
16. Create meaningful custom exceptions.
17. Understand the difference between checked and unchecked custom exceptions.
18. Use `try-with-resources` for appropriate resources.
19. Explain exception chaining.
20. Preserve the original cause when translating exceptions.
21. Avoid empty catch blocks.
22. Avoid unnecessary broad exception handling.
23. Avoid using exceptions as ordinary control flow.
24. Write meaningful and safe exception messages.
25. Understand basic exception logging practices.
26. Avoid exposing sensitive implementation details through errors.
27. Explain how exception handling can support a layered enterprise application.
28. Apply these concepts to WorkSphere without inventing requirements or architecture.
29. Explain how proper exception handling improves reliability and maintainability.

Completion of this chapter does **not** imply mastery of Java Collections, Generics, Lambda expressions, Streams, Optional, Concurrency, JVM internals, Garbage Collection, Spring-specific exception handling, or other advanced Java/framework topics. Those topics belong to their respective chapters and should be studied separately.
