# Java Object-Oriented Programming

## 1. Introduction to Object-Oriented Programming

Object-Oriented Programming (OOP) is a programming approach in which software is organized around objects that represent data and behavior.

Java is fundamentally an object-oriented programming language, and most enterprise Java applications are designed using OOP principles.

The Java fundamentals chapter established the language foundation:

* Variables
* Data types
* Operators
* Expressions
* Conditions
* Loops
* Methods
* Arrays
* Strings
* Scope
* Basic `static` usage

OOP builds upon those fundamentals by providing mechanisms for organizing related data and behavior into meaningful structures.

The major OOP concepts are:

* Classes
* Objects
* Encapsulation
* Inheritance
* Polymorphism
* Abstraction

Additional concepts such as constructors, interfaces, composition, method overriding, and access modifiers support these core principles.

A simplified learning progression is:

```text
Java Fundamentals
        ↓
Classes and Objects
        ↓
Encapsulation
        ↓
Inheritance
        ↓
Polymorphism
        ↓
Abstraction
        ↓
Interfaces and Composition
        ↓
Enterprise Java Design
```

The goal of this chapter is to understand these concepts conceptually and practically, not merely memorize their definitions.

---

## 2. Classes

A class is a blueprint or definition that describes the data and behavior associated with a particular type of object.

A simple class can be written as:

```java
public class Employee {
    String name;
    int employeeId;
}
```

Here:

* `Employee` is the class name.
* `name` is a field.
* `employeeId` is another field.

A class can contain:

* Fields
* Methods
* Constructors
* Nested types
* Initializers
* Other members

A class defines what objects of that type can contain and do.

### 2.1 Class declaration

A typical class declaration is:

```java
public class Employee {
}
```

The `public` modifier controls accessibility.

The class name follows Java naming conventions and normally uses PascalCase:

```text
Employee
EmployeeAccount
UserProfile
```

### 2.2 Fields

Fields represent data associated with an object.

```java
public class Employee {
    String name;
    int employeeId;
}
```

Each object created from this class can have its own values for these fields.

### 2.3 Methods

Methods represent behavior.

```java
public class Employee {

    String name;

    void displayName() {
        System.out.println(name);
    }
}
```

The class now contains both:

* Data: `name`
* Behavior: `displayName()`

This combination of data and behavior is fundamental to object-oriented design.

---

## 3. Objects

An object is an instance of a class.

If a class is the definition, an object is an actual instance created from that definition.

Example:

```java
Employee employee = new Employee();
```

Here:

* `Employee` is the type.
* `employee` is the reference variable.
* `new Employee()` creates an object.

### 3.1 Accessing fields

```java
Employee employee = new Employee();

employee.name = "Alex";
employee.employeeId = 101;
```

The object now contains its own state.

### 3.2 Calling methods

```java
employee.displayName();
```

The object can invoke the behavior defined by its class.

### 3.3 Multiple objects

A single class can be used to create multiple objects:

```java
Employee employee1 = new Employee();
Employee employee2 = new Employee();

employee1.name = "Alex";
employee2.name = "Priya";
```

Each object represents a separate instance.

The class defines the structure, while each object maintains its own instance state.

---

## 4. Fields and Instance Variables

A field declared inside a class but outside a method is a member variable.

When the field belongs to each object individually, it is commonly called an instance variable.

Example:

```java
public class Employee {

    String name;
    int employeeId;
}
```

Each object has its own instance fields:

```java
Employee employee1 = new Employee();
Employee employee2 = new Employee();

employee1.name = "Alex";
employee2.name = "Priya";
```

Changing `employee1.name` does not automatically change `employee2.name`.

### 4.1 Instance state

The values stored in instance fields represent an object's state.

For example:

```java
public class Employee {

    String name;
    boolean active;
}
```

An employee object may have:

```text
name   = "Alex"
active = true
```

Another object may have:

```text
name   = "Priya"
active = false
```

The class is the same, but the object state differs.

---

## 5. Instance Methods

An instance method is a method that operates on an object.

Example:

```java
public class Employee {

    String name;

    void introduce() {
        System.out.println("Employee: " + name);
    }
}
```

Usage:

```java
Employee employee = new Employee();
employee.name = "Alex";

employee.introduce();
```

The method operates using the object's state.

### 5.1 Instance method versus static method

Instance method:

```java
employee.introduce();
```

It is invoked through an object.

Static method:

```java
Math.max(10, 20);
```

It belongs to the class rather than to a particular object.

The distinction becomes important when deciding whether behavior requires object-specific state.

---

## 6. Constructors

A constructor is a special member used when an object is created.

Example:

```java
public class Employee {

    String name;
    int employeeId;

    Employee(String name, int employeeId) {
        this.name = name;
        this.employeeId = employeeId;
    }
}
```

Object creation:

```java
Employee employee = new Employee("Alex", 101);
```

The constructor initializes the object's state.

### 6.1 Constructor characteristics

A constructor:

* Has the same name as the class.
* Does not have a return type.
* Runs when an object is created using `new`.
* Can accept parameters.
* Can be overloaded.

### 6.2 No-argument constructor

A constructor without parameters is a no-argument constructor.

```java
public class Employee {

    Employee() {
        System.out.println("Employee created");
    }
}
```

### 6.3 Default constructor

If a class declares no constructor, Java can provide a default no-argument constructor.

However, once a class explicitly declares a constructor, Java does not automatically provide another no-argument constructor.

For example:

```java
public class Employee {

    Employee(String name) {
    }
}
```

This does not automatically provide:

```java
new Employee();
```

unless a no-argument constructor is explicitly defined.

### 6.4 Constructor overloading

A class can have multiple constructors:

```java
public class Employee {

    Employee() {
    }

    Employee(String name) {
    }

    Employee(String name, int employeeId) {
    }
}
```

This provides different ways of constructing the object.

---

## 7. `this` Keyword

The `this` keyword refers to the current object.

It is commonly used when a parameter and field have the same name.

```java
public class Employee {

    private String name;

    Employee(String name) {
        this.name = name;
    }
}
```

Here:

```text
this.name
```

refers to the instance field.

While:

```text
name
```

refers to the constructor parameter.

### 7.1 Calling another constructor

`this()` can invoke another constructor in the same class.

```java
public class Employee {

    private String name;
    private int employeeId;

    Employee() {
        this("Unknown", 0);
    }

    Employee(String name, int employeeId) {
        this.name = name;
        this.employeeId = employeeId;
    }
}
```

A constructor call using `this()` must appear as the first statement in the constructor.

### 7.2 Calling the current object's method

`this` can also be used explicitly:

```java
this.displayName();
```

Although the explicit use is not always necessary, understanding what `this` represents is important.

---

## 8. Encapsulation

Encapsulation means controlling access to an object's internal state and exposing appropriate operations for interacting with that state.

A common implementation uses:

* `private` fields
* Public or appropriately restricted methods

Example:

```java
public class Employee {

    private String name;
    private int employeeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

The fields are not directly accessible from unrelated classes.

Instead, access is controlled through methods.

### 8.1 Why encapsulation matters

Encapsulation can:

* Protect object state
* Control how data is changed
* Enforce validation
* Reduce unintended dependencies
* Make implementation details easier to change

For example:

```java
public void setEmployeeId(int employeeId) {
    if (employeeId > 0) {
        this.employeeId = employeeId;
    }
}
```

The class can enforce a basic rule before changing its state.

### 8.2 Encapsulation is more than getters and setters

Simply generating getters and setters for every field does not automatically produce good encapsulation.

Good encapsulation means exposing behavior appropriate to the object's responsibility while protecting unnecessary implementation details.

---

## 9. Access Modifiers

Java provides access modifiers to control visibility.

The primary access levels are:

* `public`
* `protected`
* package-private
* `private`

### 9.1 `public`

A public member is accessible from appropriate code across packages.

```java
public class Employee {
    public void display() {
    }
}
```

### 9.2 `private`

A private member is accessible only within its declaring class.

```java
public class Employee {

    private String name;
}
```

This is commonly used for encapsulating internal state.

### 9.3 `protected`

A protected member is accessible within the same package and also through inheritance relationships in other packages, subject to Java's access rules.

```java
protected String department;
```

### 9.4 Package-private

If no access modifier is specified, the member has package-private access.

```java
class Employee {
}
```

A package-private member is accessible within the same package.

### 9.5 Visibility summary

| Modifier        | Same Class | Same Package | Subclass in Other Package    | Other Packages |
| --------------- | ---------- | ------------ | ---------------------------- | -------------- |
| `private`       | Yes        | No           | No                           | No             |
| package-private | Yes        | Yes          | No                           | No             |
| `protected`     | Yes        | Yes          | Yes, subject to access rules | No             |
| `public`        | Yes        | Yes          | Yes                          | Yes            |

Access control is one of the foundations of encapsulation.

---

## 10. Getters and Setters

A getter retrieves a value.

A setter changes a value.

Example:

```java
public class Employee {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

Usage:

```java
Employee employee = new Employee();

employee.setName("Alex");

System.out.println(employee.getName());
```

### 10.1 Validation in setters

A setter can enforce rules:

```java
public void setName(String name) {
    if (name != null && !name.isBlank()) {
        this.name = name;
    }
}
```

This is more useful than exposing the field directly.

### 10.2 When not to create setters

Not every field needs to be freely changeable.

For example, an identifier might be assigned once and then remain unchanged.

In such cases, the class can avoid providing a setter or use another controlled operation.

---

## 11. Inheritance

Inheritance allows one class to derive from another class.

The child class inherits accessible members from the parent class.

Example:

```java
public class Employee {

    protected String name;

    public void work() {
        System.out.println("Employee is working");
    }
}
```

A subclass:

```java
public class Manager extends Employee {

    public void manage() {
        System.out.println("Manager is managing");
    }
}
```

Usage:

```java
Manager manager = new Manager();

manager.name = "Alex";
manager.work();
manager.manage();
```

`Manager` is a specialized form of `Employee`.

### 11.1 `extends`

Java uses `extends` for class inheritance:

```java
public class Manager extends Employee {
}
```

### 11.2 Is-a relationship

Inheritance should generally represent a meaningful "is-a" relationship.

For example:

```text
Manager is an Employee
```

can be conceptually reasonable.

However:

```text
Employee is a Database
```

would not normally represent an appropriate inheritance relationship.

### 11.3 Java class inheritance

A Java class can directly extend one class.

Java does not support multiple inheritance of classes.

Multiple behavior contracts can instead be achieved using interfaces.

---

## 12. `super` Keyword

The `super` keyword refers to the parent-class portion of an object.

### 12.1 Calling a parent constructor

```java
public class Employee {

    protected String name;

    Employee(String name) {
        this.name = name;
    }
}
```

Subclass:

```java
public class Manager extends Employee {

    Manager(String name) {
        super(name);
    }
}
```

### 12.2 Calling a parent method

```java
public class Employee {

    void work() {
        System.out.println("Employee working");
    }
}
```

Subclass:

```java
public class Manager extends Employee {

    @Override
    void work() {
        super.work();
        System.out.println("Manager managing");
    }
}
```

### 12.3 Accessing a parent field

`super` can also refer to an inherited field when appropriate:

```java
super.name
```

The use of `super` makes the parent-class behavior explicit.

---

## 13. Method Overriding

Method overriding occurs when a subclass provides its own implementation of an inherited method.

Parent:

```java
public class Employee {

    void work() {
        System.out.println("Employee working");
    }
}
```

Child:

```java
public class Manager extends Employee {

    @Override
    void work() {
        System.out.println("Manager managing");
    }
}
```

The `@Override` annotation tells the compiler that the method is intended to override an inherited method.

### 13.1 Why `@Override` is recommended

It helps the compiler detect mistakes.

For example, a typo in the method name could otherwise accidentally create a new method rather than override the parent method.

### 13.2 Important overriding rules

The overriding method must have a compatible signature and return type according to Java's overriding rules.

A method cannot be overridden if it is not inheritable in the required way.

For example, `private` methods are not overridden by subclasses.

A `final` method cannot be overridden.

---

## 14. Polymorphism

Polymorphism means that one common type can represent objects of different concrete types, allowing the appropriate behavior to be selected at runtime.

Example:

```java
Employee employee = new Manager();
employee.work();
```

Although the reference type is `Employee`, the actual object is a `Manager`.

If `Manager` overrides `work()`, the manager's implementation is selected at runtime.

### 14.1 Upcasting

Assigning a subclass object to a superclass reference is called upcasting.

```java
Manager manager = new Manager();
Employee employee = manager;
```

This is generally safe because a `Manager` is an `Employee`.

### 14.2 Runtime method dispatch

Consider:

```java
Employee employee = new Manager();
employee.work();
```

The method implementation associated with the actual object is selected.

This is a key feature of runtime polymorphism.

### 14.3 Why polymorphism matters

Polymorphism allows code to depend on a general type rather than tightly coupling it to a specific implementation.

This becomes especially important in enterprise application design, APIs, services, and testing.

---

## 15. Upcasting and Downcasting

### 15.1 Upcasting

Upcasting is converting a subclass reference to a superclass reference:

```java
Manager manager = new Manager();
Employee employee = manager;
```

It is usually implicit.

### 15.2 Downcasting

Downcasting attempts to treat a superclass reference as a specific subclass type:

```java
Employee employee = new Manager();

Manager manager = (Manager) employee;
```

This is allowed when the actual object is compatible with the target type.

### 15.3 Unsafe downcasting

This can fail:

```java
Employee employee = new Employee();

Manager manager = (Manager) employee;
```

The runtime will throw a `ClassCastException` because the object is not actually a `Manager`.

### 15.4 `instanceof`

Before downcasting, code can check the object's type:

```java
if (employee instanceof Manager) {
    Manager manager = (Manager) employee;
}
```

Modern Java also supports pattern matching for `instanceof`, but the fundamental concept is the same.

Downcasting should be used carefully. Excessive downcasting often indicates that a design could use a better abstraction.

---

## 16. Abstraction

Abstraction means exposing essential behavior while hiding unnecessary implementation details.

For example, a user of a payment service may need to know:

```text
processPayment()
```

without needing to know every internal step used to process the payment.

In Java, abstraction is commonly implemented using:

* Abstract classes
* Interfaces

Abstraction helps application code focus on what something does rather than every detail of how it does it.

---

## 17. Abstract Classes

An abstract class is a class declared with the `abstract` keyword.

Example:

```java
public abstract class Employee {

    public abstract void work();

    public void display() {
        System.out.println("Employee");
    }
}
```

The class contains:

* An abstract method
* A concrete method

### 17.1 Abstract methods

An abstract method declares behavior without providing an implementation:

```java
public abstract void work();
```

A concrete subclass must provide an implementation unless it is also abstract.

```java
public class Manager extends Employee {

    @Override
    public void work() {
        System.out.println("Manager working");
    }
}
```

### 17.2 Cannot instantiate an abstract class

This is invalid:

```java
Employee employee = new Employee();
```

An abstract class cannot be directly instantiated.

### 17.3 When abstract classes are useful

Abstract classes are useful when related classes share:

* Common state
* Common implementation
* A common conceptual base
* Some behavior that must be implemented differently by subclasses

---

## 18. Interfaces

An interface defines a contract that implementing classes agree to follow.

Example:

```java
public interface Notifiable {

    void sendNotification();
}
```

A class can implement it:

```java
public class EmailNotification implements Notifiable {

    @Override
    public void sendNotification() {
        System.out.println("Sending email");
    }
}
```

### 18.1 `implements`

Java uses `implements` for implementing interfaces:

```java
public class EmailNotification implements Notifiable {
}
```

### 18.2 Multiple interfaces

A class can implement multiple interfaces:

```java
public class EmployeeService implements Auditable, Loggable {
}
```

This provides a way to combine multiple behavioral contracts without multiple class inheritance.

### 18.3 Interface reference

An interface can be used as a reference type:

```java
Notifiable notification = new EmailNotification();
notification.sendNotification();
```

This is another important application of polymorphism.

### 18.4 Default and static methods

Modern Java interfaces can contain more than abstract method declarations.

They can also contain:

* Default methods
* Static methods
* Private helper methods

These capabilities should be understood after the basic interface contract model is clear.

---

## 19. Abstract Class vs Interface

Both abstract classes and interfaces support abstraction, but they serve different design purposes.

| Aspect            | Abstract Class            | Interface                                            |
| ----------------- | ------------------------- | ---------------------------------------------------- |
| Declared using    | `abstract class`          | `interface`                                          |
| Class inheritance | A class extends one class | A class can implement multiple interfaces            |
| Instance state    | Can have instance fields  | Does not provide ordinary per-object instance fields |
| Constructors      | Can have constructors     | Cannot be instantiated as objects                    |
| Concrete methods  | Supported                 | Supported through interface method features          |
| Primary role      | Shared base and behavior  | Contract/capability                                  |

A simplified rule is:

* Use an abstract class when closely related classes share meaningful state or implementation.
* Use an interface when you want to define a contract or capability that different classes can implement.

The exact choice should depend on the design rather than a rigid rule.

---

## 20. Composition vs Inheritance

Composition means building a class by containing or using objects of other classes rather than inheriting from them.

Example:

```java
public class Engine {
    public void start() {
        System.out.println("Engine started");
    }
}
```

Another class can use it:

```java
public class Car {

    private Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void start() {
        engine.start();
    }
}
```

Here:

```text
Car has an Engine
```

This is a "has-a" relationship.

Inheritance represents:

```text
Manager is an Employee
```

Composition represents:

```text
Car has an Engine
```

### 20.1 Why composition is often preferred

Composition can provide:

* Lower coupling
* Greater flexibility
* Easier replacement of dependencies
* Better separation of responsibilities
* More reusable components

Inheritance should therefore not be used simply because code reuse is possible.

---

## 21. Association, Aggregation, and Composition

These terms describe relationships between objects.

### 21.1 Association

Association represents a general relationship between objects.

For example:

```text
Employee works with Project
```

The objects are related, but neither relationship necessarily defines the lifecycle of the other.

### 21.2 Aggregation

Aggregation represents a whole-part relationship where the contained object can exist independently.

Conceptually:

```text
Department has Employees
```

An employee may continue to exist independently of a particular department object.

### 21.3 Composition

Composition represents a stronger ownership relationship.

Conceptually:

```text
House has Rooms
```

The lifecycle relationship is stronger because the parts are conceptually owned by the whole.

These concepts are useful for modeling relationships, but the exact lifecycle behavior depends on the application design.

---

## 22. The `Object` Class

In Java, classes ultimately derive from `Object`, directly or indirectly, except for the special handling of primitive types.

The `Object` class provides fundamental methods that are inherited by ordinary classes.

Important methods include:

* `toString()`
* `equals(Object obj)`
* `hashCode()`
* `getClass()`

Understanding these methods is important for working effectively with Java objects.

---

## 23. `equals()` and `hashCode()`

### 23.1 `equals()`

The `equals()` method is used to determine logical equality between objects.

For a custom class, developers may override it when two separate objects should be considered equal based on their relevant data.

Example concept:

```java
Employee employee1 = ...;
Employee employee2 = ...;
```

Two employee objects might be logically equal if they represent the same employee identifier, depending on the application's equality definition.

### 23.2 `==` versus `equals()`

For object references:

```java
employee1 == employee2
```

checks whether the references refer to the same object.

By contrast:

```java
employee1.equals(employee2)
```

can check logical equality according to the class's implementation.

### 23.3 `hashCode()`

`hashCode()` returns an integer hash value associated with an object.

When `equals()` is overridden, `hashCode()` generally must also be overridden consistently.

The essential contract is:

> If two objects are equal according to `equals()`, they must return the same `hashCode()`.

The reverse is not required: two unequal objects can have the same hash code.

This contract becomes particularly important when objects are used with hash-based collections.

---

## 24. `toString()`

`toString()` provides a textual representation of an object.

The default implementation may not provide useful business information.

A class can override it:

```java
@Override
public String toString() {
    return "Employee{name='" + name + "', employeeId=" + employeeId + "}";
}
```

Then:

```java
System.out.println(employee);
```

can produce a useful representation.

A good `toString()` implementation is valuable for:

* Debugging
* Logging
* Development
* Understanding object state

Sensitive information should not be exposed through `toString()`.

---

## 25. `static` in the Context of OOP

The fundamentals chapter introduced `static` at a basic level.

In OOP, the distinction becomes clearer.

An instance field belongs to an object:

```java
public class Employee {

    private String name;
}
```

A static field belongs to the class:

```java
public class Employee {

    private static int employeeCount;
}
```

### 25.1 Instance member

Each object has its own instance field:

```java
Employee employee1 = new Employee();
Employee employee2 = new Employee();
```

The objects can have different `name` values.

### 25.2 Static member

A static field is shared at the class level:

```java
Employee.employeeCount
```

There is one class-level variable associated with the class rather than one independent copy for every object.

### 25.3 Static methods

Static methods can be called through the class:

```java
Math.max(10, 20);
```

A static method does not have an object-specific `this` reference.

Therefore, it cannot directly access instance members without an object reference.

---

## 26. `final` in the Context of OOP

The fundamentals chapter introduced `final` for variables.

In OOP, `final` can also apply to classes and methods.

### 26.1 Final variable

A final variable cannot be reassigned after initialization.

```java
final int id = 101;
```

### 26.2 Final method

A final method cannot be overridden by subclasses:

```java
public final void calculate() {
}
```

### 26.3 Final class

A final class cannot be extended:

```java
public final class Utility {
}
```

This can be useful when inheritance should not be permitted for a particular class.

---

## 27. Common OOP Mistakes

### 27.1 Creating classes without clear responsibilities

A class should have a meaningful responsibility.

A class containing unrelated business logic, database operations, input handling, formatting, and application startup logic can become difficult to maintain.

### 27.2 Making every field public

This exposes internal state and weakens encapsulation.

Prefer appropriate access control.

### 27.3 Adding getters and setters mechanically

Not every field needs unrestricted read and write access.

Expose only what the design requires.

### 27.4 Using inheritance only for code reuse

Inheritance should represent a meaningful relationship.

If the relationship is not conceptually "is-a", composition may be more appropriate.

### 27.5 Excessive inheritance

Deep inheritance hierarchies can make behavior difficult to understand.

Prefer simple and purposeful hierarchies.

### 27.6 Excessive downcasting

Frequent downcasting can indicate that the abstraction is not designed correctly.

### 27.7 Forgetting `@Override`

Use `@Override` when overriding methods.

This improves compiler checking and readability.

### 27.8 Incorrect `equals()` implementation

Equality should reflect the logical identity of the class.

Avoid casually comparing unrelated fields.

### 27.9 Overriding `equals()` without `hashCode()`

This violates the equality/hash-code contract and can cause unexpected behavior with hash-based data structures.

### 27.10 Putting too much logic into constructors

Constructors should primarily establish a valid initial state.

Large constructors containing extensive business operations can make objects difficult to create and test.

### 27.11 Using `static` to avoid learning OOP

Turning everything into static methods does not create a good object-oriented design.

Use instance behavior when behavior belongs to object state.

---

## 28. OOP Best Practices

### 28.1 Keep classes cohesive

A class should have a clear and understandable responsibility.

### 28.2 Encapsulate state

Keep internal state private when direct external modification is not required.

### 28.3 Favor composition when appropriate

Do not automatically choose inheritance when a has-a relationship better represents the design.

### 28.4 Program to abstractions

When appropriate, depend on interfaces or suitable abstract types rather than unnecessary concrete implementations.

### 28.5 Prefer small, focused methods

Methods should have clear responsibilities.

### 28.6 Keep inheritance hierarchies shallow

Deep inheritance often increases coupling and makes behavior harder to trace.

### 28.7 Use `@Override`

Whenever overriding an inherited method, use the annotation.

### 28.8 Maintain equality contracts

When overriding `equals()`, implement `hashCode()` consistently.

### 28.9 Avoid exposing mutable internal state unnecessarily

Do not expose internal collections, arrays, or mutable objects without considering whether callers can unintentionally modify internal state.

### 28.10 Use meaningful abstractions

An interface or abstract class should represent a meaningful concept rather than exist solely to increase the number of types.

---

## 29. How OOP Applies to WorkSphere

WorkSphere is an enterprise software project, so OOP provides the foundation for organizing application behavior into maintainable components.

The exact classes, interfaces, relationships, and implementations should follow the approved WorkSphere requirements and architecture rather than being invented inside this learning chapter.

At a conceptual level, WorkSphere may contain domain concepts that can eventually be represented through Java types.

For example, a conceptual domain object could have:

```java
public class Employee {

    private String name;
    private int employeeId;

    public Employee(String name, int employeeId) {
        this.name = name;
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public int getEmployeeId() {
        return employeeId;
    }
}
```

This example demonstrates:

* A class
* An object-oriented representation
* Private state
* A constructor
* Encapsulation
* Methods for controlled access

### 29.1 Interfaces and application design

Enterprise applications frequently use interfaces to define contracts.

Conceptually:

```java
public interface EmployeeService {
    void processEmployee();
}
```

An implementation can then provide the behavior:

```java
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public void processEmployee() {
        // implementation
    }
}
```

This illustrates abstraction and polymorphism without claiming that this exact structure is the approved WorkSphere implementation.

### 29.2 Why OOP matters for WorkSphere

Understanding OOP prepares the developer for later concepts such as:

* Domain modeling
* Service classes
* Repository abstractions
* Dependency injection
* API models
* Security components
* Testing
* Maintainable enterprise architecture

The exact implementation should be determined by WorkSphere's dedicated architecture and design documentation.

---

## 30. Interview Questions

### Basic questions

1. What is object-oriented programming?
2. What is a class?
3. What is an object?
4. What is the difference between a class and an object?
5. What is an instance variable?
6. What is an instance method?
7. What is a constructor?
8. What is the difference between a constructor and a method?
9. What is the `this` keyword?
10. What is encapsulation?
11. Why are fields commonly declared `private`?
12. What are Java's access modifiers?
13. What is inheritance?
14. What is the purpose of `extends`?
15. What is the `super` keyword?
16. What is method overriding?
17. What is method overloading?
18. What is polymorphism?
19. What is upcasting?
20. What is downcasting?
21. What is abstraction?
22. What is an abstract class?
23. What is an interface?
24. What is the difference between an abstract class and an interface?
25. What is composition?
26. What is the difference between inheritance and composition?
27. What is association?
28. What is aggregation?
29. What is composition in object relationships?
30. What is the `Object` class?
31. What is `equals()`?
32. What is `hashCode()`?
33. Why should `hashCode()` generally be overridden when `equals()` is overridden?
34. What is `toString()`?
35. What does `static` mean in OOP?
36. What does `final` mean when applied to a method?
37. What does `final` mean when applied to a class?

### Practical questions

38. Create an `Employee` class with private fields.
39. Add a constructor to initialize an object.
40. Create getters and setters.
41. Add validation to a setter.
42. Create a parent class and subclass.
43. Override a parent method.
44. Demonstrate runtime polymorphism.
45. Demonstrate upcasting.
46. Explain when downcasting is unsafe.
47. Create an abstract class with an abstract method.
48. Create an interface and implement it.
49. Demonstrate composition using two classes.
50. Override `toString()`.
51. Explain how `equals()` and `hashCode()` should work together.

### Design-oriented questions

52. When should inheritance be used?
53. When should composition be preferred?
54. Why should fields generally not be public?
55. Why can excessive inheritance be problematic?
56. Why is programming to an interface useful?
57. Why should a class have a clear responsibility?
58. Why is polymorphism useful in enterprise applications?
59. Why should constructors generally avoid complex business operations?
60. Why is encapsulation important for maintainable software?

---

## 31. Summary

Object-Oriented Programming provides a structured way to organize Java applications around objects and their responsibilities.

The essential concepts covered in this chapter are:

* Classes
* Objects
* Fields
* Instance methods
* Constructors
* `this`
* Encapsulation
* Access modifiers
* Getters and setters
* Inheritance
* `super`
* Method overriding
* Polymorphism
* Upcasting
* Downcasting
* Abstraction
* Abstract classes
* Interfaces
* Composition
* Association
* Aggregation
* The `Object` class
* `equals()`
* `hashCode()`
* `toString()`
* `static` in OOP
* `final` in OOP
* Common OOP mistakes
* OOP best practices

The most important conceptual relationships are:

```text
Class
  ↓
Object
  ↓
State + Behavior
  ↓
Encapsulation
  ↓
Abstraction
  ↓
Polymorphism
  ↓
Maintainable Object-Oriented Design
```

Inheritance is an important OOP mechanism, but it should not be treated as the only way to design relationships between classes.

Good Java design uses the appropriate combination of encapsulation, abstraction, composition, interfaces, inheritance, and polymorphism.

---

## 32. Revision Checklist

* [ ] I understand what object-oriented programming means.
* [ ] I understand the difference between a class and an object.
* [ ] I can create a class.
* [ ] I can create objects using `new`.
* [ ] I understand instance variables.
* [ ] I understand instance methods.
* [ ] I understand constructors.
* [ ] I can create a parameterized constructor.
* [ ] I understand constructor overloading.
* [ ] I understand the `this` keyword.
* [ ] I understand encapsulation.
* [ ] I understand why fields are commonly private.
* [ ] I understand `public`, `protected`, package-private, and `private`.
* [ ] I can create getters and setters when appropriate.
* [ ] I understand inheritance.
* [ ] I understand `extends`.
* [ ] I understand the `super` keyword.
* [ ] I understand method overriding.
* [ ] I use `@Override` when appropriate.
* [ ] I understand polymorphism.
* [ ] I understand upcasting.
* [ ] I understand downcasting and its risks.
* [ ] I understand `instanceof`.
* [ ] I understand abstraction.
* [ ] I understand abstract classes.
* [ ] I understand abstract methods.
* [ ] I understand interfaces.
* [ ] I understand `implements`.
* [ ] I understand the difference between abstract classes and interfaces.
* [ ] I understand composition.
* [ ] I understand association.
* [ ] I understand aggregation.
* [ ] I understand the difference between inheritance and composition.
* [ ] I understand the role of the `Object` class.
* [ ] I understand `equals()`.
* [ ] I understand `hashCode()`.
* [ ] I understand why `equals()` and `hashCode()` must be consistent.
* [ ] I understand `toString()`.
* [ ] I understand `static` in an object-oriented context.
* [ ] I understand `final` classes and methods.
* [ ] I can identify common OOP design mistakes.
* [ ] I can explain how OOP concepts can support WorkSphere's enterprise application design.

---

## 33. Definition of Done

This chapter is considered complete when the learner can independently:

1. Explain the purpose of object-oriented programming.
2. Distinguish clearly between classes and objects.
3. Create classes with meaningful state and behavior.
4. Create objects from classes.
5. Use instance fields and instance methods correctly.
6. Create constructors and initialize object state.
7. Use `this` correctly.
8. Explain and implement encapsulation.
9. Select appropriate access modifiers.
10. Implement controlled access through methods where appropriate.
11. Explain inheritance and use `extends`.
12. Use `super` appropriately.
13. Override methods correctly.
14. Use `@Override`.
15. Explain runtime polymorphism.
16. Perform and explain upcasting.
17. Understand the risks of downcasting.
18. Explain abstraction.
19. Create and use abstract classes.
20. Create and implement interfaces.
21. Explain the difference between interfaces and abstract classes.
22. Distinguish inheritance from composition.
23. Understand association, aggregation, and composition.
24. Explain the basic role of the `Object` class.
25. Understand `equals()`, `hashCode()`, and `toString()`.
26. Explain the OOP meaning of `static`.
27. Explain `final` for variables, methods, and classes.
28. Identify common object-oriented design mistakes.
29. Apply basic OOP best practices.
30. Explain how these concepts provide a foundation for enterprise Java development and the later WorkSphere implementation.

Completion of this chapter does **not** imply mastery of exception handling, collections, generics, lambdas, streams, concurrency, JVM internals, garbage collection, or framework-specific development. Those topics belong to their respective chapters and should be studied separately.
