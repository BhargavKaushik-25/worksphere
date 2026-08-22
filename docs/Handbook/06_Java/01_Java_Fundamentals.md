# Java Fundamentals

## 1. Introduction to Java Fundamentals

Java fundamentals are the basic language concepts required to write, understand, debug, and maintain Java programs.

Before learning object-oriented programming, frameworks such as Spring Boot, database access, web development, or enterprise application development, a developer must be comfortable with the core Java language.

This chapter focuses only on the fundamental language concepts that form the foundation for later Java topics.

The chapter covers:

* Java program structure
* Variables
* Primitive data types
* Reference types
* Constants
* Type conversion and casting
* Operators
* Expressions
* Statements and blocks
* Conditional statements
* Loops
* `break` and `continue`
* Methods
* Parameters and return values
* Method overloading
* Arrays
* Strings
* Basic input and output
* Scope and lifetime
* Fundamental use of `static`
* Common beginner mistakes
* Java best practices
* The transition from fundamentals to OOP

The objective is not merely to memorize Java syntax. The objective is to understand what the code means, how the language behaves, and why a particular construct should be used.

---

## 2. Java Program Structure

A Java program is composed of language constructs such as classes, methods, variables, statements, and expressions.

A very small Java program can look like this:

```java
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, Java");
    }
}
```

### 2.1 Class declaration

```java
public class Main {
}
```

The program defines a class named `Main`.

For a public top-level class, the source file name normally matches the class name:

```text
Main.java
```

The class provides a structural container for Java code.

Detailed object-oriented concepts such as designing classes and creating objects are covered separately. At the fundamentals level, the important point is that Java source code is organized into classes.

### 2.2 The `main()` method

```java
public static void main(String[] args)
```

The `main()` method is the conventional entry point for a standalone Java application.

Its components are:

* `public` — allows the runtime to access the method.
* `static` — allows the method to be invoked without first creating an object.
* `void` — the method does not return a value.
* `main` — the method name recognized as the application entry point.
* `String[] args` — an array containing command-line arguments.

### 2.3 Statements

A statement represents an executable instruction.

Example:

```java
int age = 25;
System.out.println(age);
```

Most Java statements end with a semicolon:

```java
;
```

### 2.4 Blocks

A block is a group of statements enclosed in braces:

```java
{
    int x = 10;
    int y = 20;
    System.out.println(x + y);
}
```

Blocks are used extensively with methods, conditions, loops, and other language constructs.

### 2.5 Comments

Single-line comment:

```java
// This is a comment
```

Multi-line comment:

```java
/*
 * This is a
 * multi-line comment.
 */
```

Comments explain code to humans and are ignored by the compiler.

---

## 3. Variables

A variable is a named storage location used to hold a value during program execution.

Example:

```java
int age = 25;
```

Here:

* `int` is the data type.
* `age` is the variable name.
* `25` is the initial value.
* `=` is the assignment operator.

### 3.1 Declaration

A variable can be declared without immediately assigning a value:

```java
int age;
```

### 3.2 Initialization

A variable can then be initialized:

```java
age = 25;
```

Declaration and initialization can also be performed together:

```java
int age = 25;
```

### 3.3 Reassignment

A variable can generally be assigned a new value:

```java
int age = 25;
age = 26;
```

The variable now contains `26`.

### 3.4 Variable naming rules

Java identifiers can contain:

* Letters
* Digits
* `_`
* `$`

However, an identifier cannot begin with a digit.

Valid:

```java
age
employeeName
employee1
_total
```

Invalid:

```java
1employee
employee-name
```

Java keywords cannot be used as identifiers:

```java
int class;   // Invalid
```

### 3.5 Naming conventions

Java convention generally uses `camelCase` for variables and methods:

```java
employeeName
employeeId
calculateSalary()
```

Meaningful names are preferred over vague names:

```java
int employeeAge;
```

is clearer than:

```java
int x;
```

when the value represents an employee's age.

---

## 4. Primitive Data Types

Java has eight primitive data types.

| Type      |                 Typical Size | Purpose                         |
| --------- | ---------------------------: | ------------------------------- |
| `byte`    |                       8 bits | Small integer values            |
| `short`   |                      16 bits | Small-to-medium integer values  |
| `int`     |                      32 bits | General integer values          |
| `long`    |                      64 bits | Large integer values            |
| `float`   |                      32 bits | Single-precision decimal values |
| `double`  |                      64 bits | Double-precision decimal values |
| `char`    |                      16 bits | A single UTF-16 code unit       |
| `boolean` | JVM-dependent representation | Logical `true`/`false`          |

### 4.1 Integer types

```java
byte smallNumber = 100;
short quantity = 1000;
int age = 25;
long population = 1_400_000_000L;
```

`int` is commonly used when a general-purpose integer is required.

For a `long` literal, an `L` suffix can be used:

```java
long value = 10000000000L;
```

### 4.2 Floating-point types

```java
float temperature = 36.5F;
double price = 999.99;
```

A decimal literal is treated as a `double` by default.

Therefore:

```java
float value = 10.5F;
```

uses the `F` suffix.

For financial calculations requiring exact decimal behavior, primitive floating-point types should not automatically be assumed to be appropriate. Later Java APIs such as `BigDecimal` may be more suitable.

### 4.3 Character type

```java
char grade = 'A';
```

A `char` uses single quotes:

```java
'A'
```

A `String` uses double quotes:

```java
"A"
```

These are different types.

### 4.4 Boolean type

```java
boolean active = true;
boolean completed = false;
```

A `boolean` represents a logical condition.

Java does not treat `0` and `1` as substitutes for `false` and `true`.

---

## 5. Reference Types

Reference types are different from primitive types.

Examples include:

```java
String name = "Bhargav";
int[] numbers = {10, 20, 30};
```

The variable associated with a reference type holds a reference to an object or array rather than directly representing a primitive value in the same way a primitive variable does.

Common reference types include:

* Classes
* Arrays
* Interfaces
* Enums
* `String`
* Other object types

### 5.1 `null`

A reference variable can contain `null`:

```java
String name = null;
```

`null` means that the reference does not currently refer to an object.

Calling an instance method through a `null` reference can result in a `NullPointerException`.

Example:

```java
String name = null;
System.out.println(name.length());
```

This is invalid at runtime because `name` does not refer to a `String` object.

### 5.2 Primitive versus reference values

Primitive:

```java
int age = 25;
```

Reference:

```java
String name = "Bhargav";
```

Understanding this distinction is important before learning objects, method calls, collections, and memory behavior in greater depth.

---

## 6. Constants and `final`

The `final` keyword can prevent a variable from being assigned a new value after initialization.

Example:

```java
final int MAX_RETRIES = 3;
```

The following is not allowed:

```java
MAX_RETRIES = 5;
```

### 6.1 Naming constants

Constants are commonly written using uppercase letters with underscores:

```java
final int MAX_USERS = 100;
final double TAX_RATE = 0.18;
```

### 6.2 `final` reference variables

A `final` reference cannot be reassigned:

```java
final String name = "Java";
```

The reference cannot later point to another `String`.

The broader distinction between final references and mutable objects belongs to object-oriented programming and should be studied separately.

---

## 7. Type Conversion and Casting

Java often needs to convert one numeric type into another.

There are two important categories:

* Widening conversion
* Narrowing conversion

### 7.1 Widening conversion

A value can generally be converted from a smaller compatible numeric type to a larger one without explicit casting.

```java
int number = 100;
long value = number;
```

Another example:

```java
int number = 10;
double value = number;
```

The conversion is performed automatically.

### 7.2 Narrowing conversion

Converting a larger numeric type to a smaller one usually requires an explicit cast.

```java
double price = 99.99;
int value = (int) price;
```

The fractional part is discarded, so `value` becomes `99`.

Casting does not mean rounding.

### 7.3 Data loss

Narrowing conversions can lose information:

```java
long value = 100000L;
int number = (int) value;
```

A developer should ensure that the source value is within the valid range of the target type.

### 7.4 Character and numeric conversion

A `char` can participate in numeric conversions:

```java
char letter = 'A';
int value = letter;
```

The numeric value corresponds to the character's Unicode representation.

### 7.5 String conversion

Converting between strings and primitive values normally requires parsing methods:

```java
int age = Integer.parseInt("25");
double salary = Double.parseDouble("45000.50");
```

The reverse direction can use:

```java
String text = String.valueOf(25);
```

Invalid numeric text can result in a runtime parsing exception.

---

## 8. Operators

Operators perform operations on values.

Major categories include:

* Arithmetic operators
* Assignment operators
* Relational operators
* Logical operators
* Unary operators
* Conditional operator
* Bitwise operators

### 8.1 Arithmetic operators

```java
+   -   *   /   %
```

Example:

```java
int a = 10;
int b = 3;

int sum = a + b;
int difference = a - b;
int product = a * b;
int quotient = a / b;
int remainder = a % b;
```

When both operands are integers, integer division is performed:

```java
int result = 10 / 3;
```

The result is `3`, not `3.333...`.

### 8.2 Assignment operators

```java
=
+=
-=
*=
/=
%=
```

Example:

```java
int count = 10;
count += 5;
```

This is equivalent in effect to:

```java
count = count + 5;
```

### 8.3 Relational operators

```java
==
!=
>
<
>=
<=
```

They are commonly used to compare values.

Example:

```java
int age = 25;

boolean eligible = age >= 18;
```

### 8.4 Logical operators

```java
&&
||
!
```

Example:

```java
boolean employee = true;
boolean active = true;

if (employee && active) {
    System.out.println("Active employee");
}
```

`&&` means logical AND.

`||` means logical OR.

`!` means logical NOT.

### 8.5 Increment and decrement

```java
++
--
```

Example:

```java
int count = 0;
count++;
```

The value becomes `1`.

### 8.6 Conditional operator

The ternary operator has the form:

```java
condition ? valueIfTrue : valueIfFalse
```

Example:

```java
int age = 20;
String status = age >= 18 ? "Adult" : "Minor";
```

It is useful for short conditional expressions but should not replace readable multi-step logic.

---

## 9. Expressions

An expression is a combination of values, variables, operators, method calls, or other constructs that produces a value.

Examples:

```java
a + b
age >= 18
price * quantity
name.length()
```

For example:

```java
int total = price * quantity;
```

The expression:

```java
price * quantity
```

produces a value that is assigned to `total`.

Expressions are fundamental because conditions, assignments, method arguments, and calculations are frequently built from them.

### 9.1 Operator precedence

Java follows defined precedence rules.

For example:

```java
int result = 2 + 3 * 4;
```

Multiplication is evaluated before addition, resulting in `14`.

Parentheses can make intent explicit:

```java
int result = (2 + 3) * 4;
```

This produces `20`.

Use parentheses when they improve clarity rather than relying unnecessarily on the reader to remember precedence rules.

---

## 10. Statements and Blocks

Statements are executable instructions.

Examples:

```java
int age = 25;
age++;
System.out.println(age);
```

A block groups statements:

```java
if (age >= 18) {
    System.out.println("Adult");
    System.out.println("Eligible for the condition");
}
```

Blocks establish scope.

A variable declared inside a block generally cannot be accessed outside that block:

```java
if (true) {
    int value = 10;
}

// value is not accessible here
```

This relationship between blocks and variable visibility becomes especially important when working with loops and conditional logic.

---

## 11. Conditional Statements

Conditional statements allow a program to make decisions.

### 11.1 `if`

```java
if (age >= 18) {
    System.out.println("Adult");
}
```

The block executes when the condition is `true`.

### 11.2 `if-else`

```java
if (age >= 18) {
    System.out.println("Adult");
} else {
    System.out.println("Minor");
}
```

### 11.3 `if-else if-else`

```java
if (marks >= 90) {
    System.out.println("A");
} else if (marks >= 75) {
    System.out.println("B");
} else if (marks >= 60) {
    System.out.println("C");
} else {
    System.out.println("Needs improvement");
}
```

Conditions are evaluated from top to bottom.

### 11.4 Nested conditions

An `if` can appear inside another `if`:

```java
if (employee) {
    if (active) {
        System.out.println("Active employee");
    }
}
```

Nested logic should be kept understandable. Excessive nesting often makes code harder to read.

### 11.5 `switch`

`switch` can be useful when one expression is compared against multiple possible values.

```java
int day = 2;

switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
        System.out.println("Tuesday");
        break;
    default:
        System.out.println("Other day");
}
```

Modern Java also supports enhanced `switch` syntax, but the fundamental concept remains the same: select behavior based on a value.

---

## 12. Loops

Loops repeatedly execute code while a condition or iteration rule allows it.

Common loops include:

* `for`
* `while`
* `do-while`
* Enhanced `for`

### 12.1 `for` loop

```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
```

The common structure is:

```java
for (initialization; condition; update) {
    // body
}
```

### 12.2 `while` loop

```java
int count = 0;

while (count < 5) {
    System.out.println(count);
    count++;
}
```

The condition is checked before each iteration.

### 12.3 `do-while` loop

```java
int count = 0;

do {
    System.out.println(count);
    count++;
} while (count < 5);
```

The body executes at least once because the condition is evaluated after the body.

### 12.4 Enhanced `for` loop

For arrays:

```java
int[] numbers = {10, 20, 30};

for (int number : numbers) {
    System.out.println(number);
}
```

This is useful when the program needs each element rather than an explicit index.

### 12.5 Choosing a loop

Use a `for` loop when the iteration structure is naturally expressed with initialization, condition, and update.

Use `while` when repetition depends primarily on a condition.

Use `do-while` when the body must execute at least once.

Use enhanced `for` when iterating through array elements without needing direct index management.

---

## 13. `break` and `continue`

### 13.1 `break`

`break` immediately terminates the nearest enclosing loop or applicable `switch`.

Example:

```java
for (int i = 0; i < 10; i++) {
    if (i == 5) {
        break;
    }

    System.out.println(i);
}
```

The loop stops when `i` becomes `5`.

### 13.2 `continue`

`continue` skips the remaining statements of the current loop iteration and proceeds to the next iteration.

```java
for (int i = 0; i < 5; i++) {
    if (i == 2) {
        continue;
    }

    System.out.println(i);
}
```

The value `2` is skipped.

### 13.3 Best practice

`break` and `continue` can simplify certain loops, but excessive use can make control flow difficult to understand.

Prefer clear loop conditions when they express the intended logic naturally.

---

## 14. Methods

A method is a named block of code that performs a specific operation.

Example:

```java
static void greet() {
    System.out.println("Hello");
}
```

A method can be invoked from appropriate code:

```java
greet();
```

### 14.1 Why methods are important

Methods help developers:

* Reuse logic
* Reduce duplication
* Organize code
* Improve readability
* Test individual operations
* Give meaningful names to operations

Instead of repeatedly writing the same calculation, place it inside a method.

### 14.2 Method structure

Example:

```java
static int add(int a, int b) {
    return a + b;
}
```

Important parts include:

* `static` — method-level modifier used here so it can be called without an object.
* `int` — return type.
* `add` — method name.
* `int a, int b` — parameters.
* `return a + b;` — returned result.

The object-oriented implications of instance methods are covered separately.

---

## 15. Method Parameters and Return Values

Parameters allow data to be passed into methods.

```java
static void printName(String name) {
    System.out.println(name);
}
```

Invocation:

```java
printName("Bhargav");
```

### 15.1 Multiple parameters

```java
static int add(int a, int b) {
    return a + b;
}
```

Invocation:

```java
int result = add(10, 20);
```

### 15.2 Return values

A method can return a value:

```java
static int square(int number) {
    return number * number;
}
```

The caller can use the result:

```java
int result = square(5);
```

### 15.3 `void`

A method that does not return a value uses `void`:

```java
static void printMessage() {
    System.out.println("Hello");
}
```

### 15.4 Java is pass-by-value

Java passes arguments by value.

For primitive values, the method receives a copy of the primitive value.

For reference variables, the value being passed is the reference value itself. This means a method can use the received reference to modify the referenced object's state, but assigning a different reference to the parameter does not replace the caller's reference.

This distinction becomes important when working with arrays and objects.

---

## 16. Method Overloading

Method overloading allows multiple methods in the same class to have the same name while having different parameter lists.

Example:

```java
static int add(int a, int b) {
    return a + b;
}

static int add(int a, int b, int c) {
    return a + b + c;
}
```

Both methods are named `add`, but their parameter lists differ.

Another example:

```java
static double calculate(double a, double b) {
    return a + b;
}

static int calculate(int a, int b) {
    return a + b;
}
```

### 16.1 What distinguishes overloaded methods?

The parameter list can differ by:

* Number of parameters
* Parameter types
* Parameter order when the types differ

Changing only the return type is not sufficient:

```java
int test(int value) { ... }
double test(int value) { ... } // Invalid overload
```

Method overloading is an important foundation for understanding compile-time polymorphism later in Java.

---

## 17. Arrays

An array stores multiple values of the same type in a fixed-size structure.

### 17.1 Declaration

```java
int[] numbers;
```

### 17.2 Creation

```java
numbers = new int[5];
```

### 17.3 Initialization

```java
int[] numbers = {10, 20, 30, 40, 50};
```

### 17.4 Indexing

Array indexes start at `0`.

For:

```java
int[] numbers = {10, 20, 30};
```

the indexes are:

```text
0 -> 10
1 -> 20
2 -> 30
```

Access:

```java
System.out.println(numbers[0]);
```

### 17.5 Updating an element

```java
numbers[1] = 25;
```

### 17.6 Array length

```java
System.out.println(numbers.length);
```

`length` is a property of the array.

### 17.7 Iterating over an array

Using an index:

```java
for (int i = 0; i < numbers.length; i++) {
    System.out.println(numbers[i]);
}
```

Using enhanced `for`:

```java
for (int number : numbers) {
    System.out.println(number);
}
```

### 17.8 Array limitations

An array has a fixed length after creation.

If a program requires a dynamically growing collection, Java's collection framework provides more suitable structures. Collections are intentionally outside the scope of this fundamentals chapter.

---

## 18. Strings

`String` is a commonly used reference type representing text.

Example:

```java
String name = "Java";
```

### 18.1 String literals

```java
String language = "Java";
```

### 18.2 Common methods

```java
String text = "Hello Java";

int length = text.length();
String upper = text.toUpperCase();
String lower = text.toLowerCase();
boolean contains = text.contains("Java");
```

### 18.3 String concatenation

```java
String firstName = "Bhargav";
String message = "Hello " + firstName;
```

### 18.4 Comparing strings

Do not generally use `==` to compare string contents.

Prefer:

```java
String a = "Java";
String b = "Java";

if (a.equals(b)) {
    System.out.println("Equal");
}
```

`==` compares references for reference types, whereas `equals()` is used to compare content when the class provides appropriate equality behavior.

### 18.5 Strings are immutable

A `String` object cannot be changed after it is created.

For example:

```java
String text = "Hello";
text = text + " Java";
```

The original `String` is not modified in place; the expression produces another string value and the variable is reassigned.

Detailed string handling, mutable string alternatives, and performance considerations can be studied later.

---

## 19. Basic Input and Output

Java programs commonly use standard output for displaying information.

### 19.1 Output

```java
System.out.println("Hello");
```

`println()` prints the value followed by a line break.

```java
System.out.print("Hello");
```

`print()` does not automatically add a line break.

### 19.2 Formatted output

```java
System.out.printf("Age: %d%n", age);
```

Formatted output can be useful when multiple values must be displayed in a structured format.

### 19.3 Input with `Scanner`

Basic console input can be performed using `Scanner`:

```java
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.println("Hello " + name);

        scanner.close();
    }
}
```

Common methods include:

```java
nextInt()
nextLong()
nextDouble()
next()
nextLine()
```

### 19.4 Input pitfalls

A common beginner issue occurs when mixing methods such as:

```java
nextInt()
```

and:

```java
nextLine()
```

`nextInt()` reads the integer but may leave the line separator in the input stream. A subsequent `nextLine()` can therefore consume the remaining line instead of waiting for new input.

Understanding how the input API consumes data prevents many beginner errors.

---

## 20. Scope and Lifetime

Scope determines where a variable can be accessed.

Lifetime describes how long the variable exists as part of program execution.

### 20.1 Local variable

A variable declared inside a method or block is local to that scope.

```java
static void calculate() {
    int result = 100;
    System.out.println(result);
}
```

`result` cannot be directly accessed outside its scope.

### 20.2 Block scope

```java
if (true) {
    int value = 10;
    System.out.println(value);
}

// value is not accessible here
```

### 20.3 Loop scope

```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}

// i is not accessible here
```

### 20.4 Parameter scope

Method parameters are available inside the method body:

```java
static int square(int number) {
    return number * number;
}
```

`number` belongs to the method's parameter scope.

Understanding scope helps prevent accidental variable misuse and naming conflicts.

---

## 21. `static` at the Fundamental Level

At the fundamentals level, `static` means that a member belongs to the class rather than to an individual object instance.

A simple example:

```java
static int count = 0;
```

A static method can be called without creating an object:

```java
static int add(int a, int b) {
    return a + b;
}
```

The `main()` method is static:

```java
public static void main(String[] args)
```

This allows the Java runtime to invoke it as the application entry point without first creating an instance of the class.

### 21.1 Static variable

A static variable is associated with the class:

```java
public class Counter {
    static int count = 0;
}
```

### 21.2 Static method

```java
public class Calculator {

    static int add(int a, int b) {
        return a + b;
    }
}
```

It can be invoked as:

```java
int result = Calculator.add(10, 20);
```

### 21.3 Fundamental limitation

A static method cannot directly access an instance member because an instance member belongs to a particular object.

Detailed object lifecycle, classes, objects, instance members, and object-oriented design are intentionally outside this chapter.

---

## 22. Common Beginner Mistakes

### 22.1 Using `=` instead of `==`

Assignment:

```java
age = 25;
```

Comparison:

```java
age == 25;
```

These have different meanings.

### 22.2 Comparing strings with `==`

Incorrect for content comparison:

```java
if (name == "Java") {
}
```

Prefer:

```java
if ("Java".equals(name)) {
}
```

The latter form also avoids a null-reference problem when `name` is `null`.

### 22.3 Integer division surprise

```java
int result = 5 / 2;
```

The result is:

```text
2
```

not `2.5`.

Use an appropriate floating-point operand when a fractional result is required:

```java
double result = 5.0 / 2;
```

### 22.4 Array index errors

For:

```java
int[] numbers = new int[5];
```

valid indexes are:

```text
0 through 4
```

Using index `5` causes an `ArrayIndexOutOfBoundsException`.

### 22.5 Forgetting to update a loop variable

Example:

```java
int count = 0;

while (count < 5) {
    System.out.println(count);
}
```

The condition never becomes false, producing an unintended infinite loop.

### 22.6 Missing braces

Although Java can allow a single statement without braces, beginners should generally use braces:

```java
if (condition) {
    doSomething();
}
```

This reduces accidental control-flow errors when code is modified later.

### 22.7 Ignoring type ranges

A primitive type has a defined range.

Choosing a type that cannot represent the required value can cause overflow or data loss.

### 22.8 Confusing `length` and `length()`

For arrays:

```java
numbers.length
```

For strings:

```java
text.length()
```

An array has a `length` field, while `String` provides a `length()` method.

### 22.9 Overusing `static`

Using `static` everywhere is not a substitute for understanding object-oriented design.

At this stage, use it where the language structure requires or clearly benefits from it, such as simple utility-style methods and the application entry point.

### 22.10 Writing excessively large methods

A method that performs many unrelated tasks becomes difficult to understand and test.

Break meaningful responsibilities into smaller methods.

---

## 23. Best Practices

### 23.1 Use meaningful names

Prefer:

```java
int employeeAge;
```

over:

```java
int x;
```

when the purpose is known.

### 23.2 Keep methods focused

A method should ideally perform one clear logical operation.

### 23.3 Avoid unnecessary duplication

If the same logic appears repeatedly, consider extracting it into a method.

### 23.4 Use constants for fixed values

Prefer:

```java
final int MAX_RETRIES = 3;
```

over repeatedly writing unexplained numeric literals.

### 23.5 Use braces consistently

Braces improve readability and reduce accidental control-flow changes.

### 23.6 Prefer clarity over cleverness

Code should be understandable to another developer.

Avoid unnecessarily compact expressions when a clearer form communicates the intent better.

### 23.7 Validate assumptions

Do not assume that input is always valid.

For example, numeric parsing should account for the possibility of invalid input.

### 23.8 Choose appropriate data types

Use a type that correctly represents the required value and behavior.

### 23.9 Avoid unnecessary casts

Casting should have a clear reason.

Do not use casts simply to silence compiler errors without understanding the conversion.

### 23.10 Keep responsibilities separated

Even at the fundamentals level, organize calculations, input handling, and output in a reasonably structured way.

This prepares the codebase for later object-oriented design.

---

## 24. How These Fundamentals Lead Into OOP

Java fundamentals provide the language-level foundation for object-oriented programming.

For example:

```java
int employeeId = 101;
String employeeName = "Alex";
double salary = 50000.0;
```

These variables represent individual pieces of information.

As applications become larger, related data and behavior need better organization.

That leads to concepts such as:

* Classes
* Objects
* Fields
* Constructors
* Instance methods
* Encapsulation
* Inheritance
* Polymorphism
* Abstraction
* Interfaces

These concepts should not be treated as replacements for fundamentals. They build on them.

A developer who understands variables, types, expressions, conditions, loops, arrays, strings, and methods will have a much stronger foundation for understanding OOP.

The learning progression is therefore:

```text
Java Syntax
    ↓
Variables and Data Types
    ↓
Expressions and Operators
    ↓
Control Flow
    ↓
Methods
    ↓
Arrays and Strings
    ↓
Scope and Basic static Usage
    ↓
Classes and Objects
    ↓
OOP Principles
```

---

## 25. WorkSphere Relevance

The WorkSphere system is an enterprise software project, so Java fundamentals form the base for understanding and implementing its application logic.

At the fundamentals level, these concepts can be related to WorkSphere without introducing WorkSphere-specific implementation details that have not yet been defined.

### Variables and data types

Application logic needs variables to represent values such as identifiers, names, statuses, counts, and other domain data.

Example:

```java
int employeeId = 101;
String employeeName = "Alex";
boolean active = true;
```

### Conditions

Business rules commonly require decisions:

```java
if (active) {
    System.out.println("Employee is active");
}
```

The actual WorkSphere business rules should come from the approved requirements rather than being invented inside the Java learning material.

### Loops

Repeated processing can require loops:

```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
```

### Methods

Application behavior should be organized into meaningful operations:

```java
static boolean isValidEmployeeId(int employeeId) {
    return employeeId > 0;
}
```

### Arrays

Arrays provide a basic mechanism for handling fixed-size groups of values.

More advanced application data handling may later use Java Collections, which are outside the scope of this chapter.

### Strings

Enterprise applications frequently process text:

```java
String employeeName = "Alex";
```

String operations therefore form an important basic skill.

### Why this matters

The purpose of learning these fundamentals in the WorkSphere documentation is to establish the Java foundation required before progressing into:

```text
Java Fundamentals
        ↓
Object-Oriented Programming
        ↓
Advanced Java
        ↓
Spring / Spring Boot
        ↓
Persistence and APIs
        ↓
Security
        ↓
Testing
        ↓
Deployment and Enterprise Architecture
```

The exact WorkSphere implementation should be defined by the project's approved architecture, requirements, API, database, security, and other relevant documentation.

---

## 26. Interview Questions

### Basic questions

1. What is a variable in Java?
2. What are the eight primitive data types?
3. What is the difference between primitive and reference types?
4. What is the purpose of `final`?
5. What is type casting?
6. What is widening conversion?
7. What is narrowing conversion?
8. What is the difference between `==` and `equals()`?
9. What is an expression?
10. What is a statement?
11. What is a block?
12. What is the purpose of an `if` statement?
13. What is the difference between `while` and `do-while`?
14. What is the difference between `break` and `continue`?
15. What is a method?
16. What is a method parameter?
17. What is a return value?
18. What is method overloading?
19. What is an array?
20. Why do array indexes start at `0`?
21. What is the difference between `array.length` and `String.length()`?
22. Why is `String` considered a reference type?
23. What does `null` mean for a reference variable?
24. What is variable scope?
25. What is the purpose of `static`?
26. Why is the `main()` method static?
27. What is integer division?
28. Why can narrowing conversion result in data loss?
29. What happens when an invalid array index is accessed?
30. What does Java's pass-by-value behavior mean?

### Practical questions

31. Write a method to determine whether a number is even.
32. Write a method to find the largest of two numbers.
33. Write a loop that prints numbers from 1 to 10.
34. Write a program that calculates the sum of an integer array.
35. Write a method that returns the square of a number.
36. Write an overloaded method named `add`.
37. Write a program that counts the number of elements in an array.
38. Write a program that checks whether a string is empty.
39. Write a program that prints only even numbers from an array.
40. Explain what happens when an integer is divided by another integer.

### Conceptual interview expectation

A strong candidate should not only provide definitions.

The candidate should be able to:

* Explain the difference between related concepts.
* Write small working examples.
* Predict program output.
* Identify common runtime problems.
* Explain why a particular construct is appropriate.
* Modify a simple program without breaking its control flow.
* Recognize when a problem requires a later Java concept rather than forcing everything into fundamentals.

---

## 27. Summary

Java fundamentals provide the foundation for all subsequent Java development.

The essential concepts covered in this chapter are:

* Java program structure
* Variables
* Primitive data types
* Reference types
* Constants and `final`
* Type conversion and casting
* Operators
* Expressions
* Statements and blocks
* Conditional statements
* Loops
* `break` and `continue`
* Methods
* Method parameters and return values
* Method overloading
* Arrays
* Strings
* Basic input and output
* Scope and lifetime
* Fundamental `static` usage
* Common beginner mistakes
* Best practices
* The transition toward OOP

A developer should be able to read and write basic Java programs before moving into more advanced language and framework concepts.

The next stages of Java learning can build upon this foundation rather than repeating it.

---

## 28. Revision Checklist

Use this checklist to verify understanding of the chapter.

* [ ] I understand the basic structure of a Java program.
* [ ] I understand the purpose of the `main()` method.
* [ ] I can declare and initialize variables.
* [ ] I know the eight primitive data types.
* [ ] I understand the difference between primitive and reference types.
* [ ] I understand what `null` represents.
* [ ] I know how `final` can be used for constants.
* [ ] I understand widening and narrowing conversions.
* [ ] I understand explicit casting.
* [ ] I know the major Java operator categories.
* [ ] I understand expressions and operator precedence.
* [ ] I understand statements and blocks.
* [ ] I can use `if`, `else if`, and `else`.
* [ ] I understand the basic use of `switch`.
* [ ] I can write `for`, `while`, and `do-while` loops.
* [ ] I understand the enhanced `for` loop.
* [ ] I understand `break`.
* [ ] I understand `continue`.
* [ ] I can define and call a method.
* [ ] I understand method parameters.
* [ ] I understand method return values.
* [ ] I understand that Java uses pass-by-value.
* [ ] I understand method overloading.
* [ ] I can create and access an array.
* [ ] I understand zero-based array indexing.
* [ ] I can iterate through an array.
* [ ] I understand basic `String` operations.
* [ ] I understand why `String` content should generally be compared using `equals()`.
* [ ] I can perform basic console input and output.
* [ ] I understand variable scope.
* [ ] I understand the fundamental purpose of `static`.
* [ ] I can identify common beginner mistakes.
* [ ] I can write simple, readable Java code.
* [ ] I understand how these fundamentals prepare me for OOP.

---

## 29. Definition of Done

This chapter is considered complete when the learner can independently:

1. Create a basic Java source file.
2. Explain the structure of a simple Java program.
3. Declare, initialize, and update variables.
4. Select appropriate primitive data types for common values.
5. Explain primitive versus reference types at a fundamental level.
6. Use `final` for values that should not be reassigned.
7. Perform basic type conversions and understand casting.
8. Use arithmetic, comparison, logical, and assignment operators.
9. Understand and construct basic expressions.
10. Use statements and blocks correctly.
11. Implement conditional logic using `if`, `else`, and `switch`.
12. Implement repetition using `for`, `while`, and `do-while`.
13. Use `break` and `continue` appropriately.
14. Create methods with and without return values.
15. Pass arguments to methods.
16. Explain Java's pass-by-value behavior at a fundamental level.
17. Implement basic method overloading.
18. Create, access, update, and iterate over arrays.
19. Perform common string operations.
20. Read basic console input and produce console output.
21. Understand variable scope and basic lifetime concepts.
22. Explain the fundamental purpose of `static`.
23. Identify and correct common beginner-level Java errors.
24. Write readable and maintainable beginner-level Java code.
25. Explain how Java fundamentals provide the foundation for object-oriented programming.

Completion of this chapter does **not** imply mastery of OOP, exception handling, collections, generics, lambdas, streams, concurrency, JVM internals, garbage collection, or other advanced Java topics. Those concepts belong to their respective learning chapters and should be studied separately.
