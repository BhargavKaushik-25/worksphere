# Unit Testing

## Document Information

| Field | Value |
|---|---|
| Document | Unit Testing |
| Project | WorkSphere |
| Section | Testing and Quality |
| Version | 1.0 |
| Status | Approved |
| Owner | WorkSphere Engineering Team |
| Last Updated | August 2026 |

---

## 1. Purpose

This document defines the unit testing standards and practices for the WorkSphere project.

Unit testing focuses on verifying small, isolated units of application logic independently from larger system components.

The objective is to detect defects early, provide fast developer feedback, support safe refactoring, and establish confidence in individual units of behavior.

This document follows the overall principles defined in:

`00_Testing_and_Quality_Overview.md`

and

`01_Testing_Strategy.md`

---

## 2. What Is Unit Testing?

A unit test verifies the behavior of a small, independently testable portion of application logic.

A unit may include:

- A method
- A class
- A function
- A validator
- A business rule
- A transformation
- A calculation
- A utility

The unit should normally be tested independently from external infrastructure.

Typical external dependencies include:

- Databases
- External APIs
- Message brokers
- File systems
- Network services
- Authentication providers

These dependencies may be replaced with appropriate test doubles when isolation is required.

---

## 3. Objectives

Unit testing in WorkSphere should achieve the following objectives:

1. Verify business logic.
2. Detect defects early.
3. Support safe refactoring.
4. Provide fast feedback.
5. Validate edge cases.
6. Validate error conditions.
7. Improve code design and testability.
8. Reduce regression risk.
9. Document expected behavior through executable tests.
10. Support continuous integration.

---

## 4. Unit Testing Principles

WorkSphere unit tests should follow these principles:

1. Tests should be isolated.
2. Tests should be deterministic.
3. Tests should be fast.
4. Tests should test behavior.
5. Tests should have meaningful assertions.
6. Tests should be easy to understand.
7. Tests should be maintainable.
8. Tests should avoid unnecessary dependencies.
9. Tests should not depend on execution order.
10. Tests should fail for meaningful reasons.
11. Tests should cover important edge cases.
12. Tests should be maintained alongside production code.

---

## 5. Unit Test Structure

A unit test should generally follow the:

```text
Arrange
   ↓
Act
   ↓
Assert

pattern.

Arrange

Prepare the required input and test state.

Act

Execute the unit being tested.

Assert

Verify the expected result.

Example:

// Arrange
Project project = createValidProject();

// Act
Project result = projectService.create(project);

// Assert
assertEquals(project.getName(), result.getName());

The exact implementation depends on the selected testing framework.

6. Given-When-Then Style

Tests may also use the:

Given
When
Then

structure.

Example:

Given a valid authenticated user
When the user creates a project
Then the project should be created successfully

This style can improve readability for business-oriented behavior.

The selected style should remain consistent within the relevant codebase.

7. Unit Testing Scope

Unit tests should primarily target logic that can be validated independently.

Typical targets include:

Business services
Validators
Utility classes
Mappers
Calculators
Transformation logic
Business rules
Permission checks
Data-processing logic
Error-handling logic
8. What Should Not Be a Unit Test?

The following should generally not be tested as pure unit tests:

Actual database integration
Actual network communication
Actual external API calls
Complete browser workflows
Full deployment environments
Real message broker communication

Those concerns should be validated through appropriate integration, system, or end-to-end tests.

9. Backend Unit Testing

Backend unit tests should cover important business and application logic.

Potential targets include:

Controller
   ↓
Service
   ↓
Repository

For pure unit testing, the service can be tested independently from the repository.

For example:

ProjectService
    ↓
Mock ProjectRepository

This allows business behavior to be tested without requiring a real database.

10. Service Layer Testing

The service layer usually contains important business logic.

Unit tests should verify:

Valid operations
Invalid operations
Business rules
Duplicate detection
Permission decisions
Missing data
Exception behavior
State transitions

Example scenarios:

createProject_withValidData_shouldCreateProject

createProject_withDuplicateName_shouldRejectRequest

getProject_withUnknownId_shouldThrowException

deleteProject_withoutPermission_shouldRejectRequest
11. Controller Layer Testing

Controllers should primarily be validated through appropriate web-layer or integration tests.

However, controller-specific logic that exists independently may also be unit tested.

The goal should be to avoid duplicating the same behavior across large numbers of test layers.

Controller tests should focus on:

Request mapping
Validation
Response behavior
HTTP status
Error handling

where appropriate.

12. Repository Layer Testing

Repository behavior involving actual persistence should generally be tested using integration tests.

Pure unit tests may still verify repository-related helper logic if such logic exists.

Do not mock the repository and claim that the actual database interaction has been tested.

Unit testing and integration testing have different purposes.

13. Frontend Unit Testing

Frontend unit tests should validate isolated frontend logic.

Potential targets include:

Utility functions
Validators
State transformations
Selectors
Data formatting
Business rules
Pure functions

Example:

formatUserName()
calculateTaskProgress()
validateEmail()
filterProjects()
calculatePagination()
14. Component Testing vs Unit Testing

Frontend components may contain significant behavior.

Where a component requires rendering and user interaction, component testing may be more appropriate than pure unit testing.

A simplified distinction is:

Pure Function
    ↓
Unit Test

Rendered Component
    ↓
Component Test

Complete User Workflow
    ↓
End-to-End Test

The testing layer should match the behavior being validated.

15. Test Isolation

Unit tests should isolate the unit under test from unnecessary external dependencies.

For example:

ProjectService
     |
     +---- ProjectRepository
     |
     +---- NotificationService
     |
     +---- UserService

A unit test may replace these dependencies with test doubles:

ProjectService
     |
     +---- Mock Repository
     |
     +---- Mock NotificationService
     |
     +---- Mock UserService

This allows the test to focus on ProjectService behavior.

16. Test Doubles

Test doubles may include:

Mocks
Stubs
Fakes
Spies

They should be used intentionally.

The goal is isolation, not excessive mocking.

17. Mock Usage

Mocks should be used when interaction with a dependency is important to the behavior being tested.

For example:

Service
   ↓
Repository.save()

A test may verify that the repository is called with the correct information.

However, tests should avoid verifying every internal implementation detail.

18. Stub Usage

Stubs can provide predetermined responses.

Example:

UserRepository.findById()
        ↓
Returns Test User

The service can then be tested against that controlled response.

19. Fake Usage

A fake provides a lightweight working implementation.

For example, an in-memory repository may be used when appropriate.

Fakes can be useful when a realistic implementation is easier to work with than a large collection of mocks.

20. Spy Usage

Spies can observe interactions with an object.

They may be used when the test needs to verify:

A method was called
A method was called a specific number of times
Specific arguments were provided

Spies should not be used merely to inspect irrelevant implementation details.

21. Deterministic Tests

Unit tests should produce the same result when executed repeatedly under the same conditions.

Tests should avoid uncontrolled dependencies on:

Current time
Random values
Network availability
Local machine state
File system state
Environment-specific configuration

When time or randomness is important, controlled test values should be introduced.

22. Test Independence

Each unit test should be independently executable.

Tests should not depend on:

Another test
Test execution order
Shared mutable state
Previous test results

Each test should establish its own required state.

23. Test Data

Unit test data should be:

Simple
Relevant
Predictable
Easy to understand
Easy to maintain

Avoid creating unnecessarily large objects when only a few fields are relevant to the behavior being tested.

24. Test Fixtures

Reusable test fixtures may be used when multiple tests require the same baseline data.

Example:

createValidUser()
createValidProject()
createValidTask()

Fixtures should remain simple and should not hide important test conditions.

A test reader should be able to understand the meaningful state of the test.

25. Positive Unit Tests

Positive tests should verify expected successful behavior.

Examples:

shouldCreateProjectWithValidData

shouldCalculateCorrectTaskProgress

shouldValidateCorrectEmail

shouldReturnUserForValidId

Positive tests establish that normal behavior works correctly.

26. Negative Unit Tests

Negative tests should verify that invalid conditions are handled correctly.

Examples:

shouldRejectNullProject

shouldRejectInvalidEmail

shouldThrowExceptionForUnknownUser

shouldRejectUnauthorizedOperation

Negative tests are essential for robust business logic.

27. Boundary Testing

Unit tests should verify important boundaries.

For a maximum length of 100 characters:

99 characters → Valid
100 characters → Valid
101 characters → Invalid

For numeric values:

Minimum - 1 → Invalid
Minimum → Valid
Typical Value → Valid
Maximum → Valid
Maximum + 1 → Invalid
28. Null and Empty Values

Where applicable, unit tests should verify:

Null values
Empty strings
Blank strings
Empty collections
Missing optional values

The expected behavior should come from the relevant requirement or business rule.

29. Exception Testing

Tests should verify expected exceptions.

Example:

assertThrows(
    ProjectNotFoundException.class,
    () -> projectService.getProject(unknownId)
);

Exception tests should verify the correct exception type and, where meaningful, relevant error information.

30. Error Message Testing

Error messages should be tested when they form part of observable behavior.

However, tests should avoid unnecessary coupling to implementation-specific wording.

Prefer validating meaningful error codes or stable messages where the system defines them as part of its contract.

31. Parameterized Testing

Parameterized tests may be used when the same behavior must be verified against multiple inputs.

Example scenarios:

Valid email
Invalid email
Empty email
Uppercase email
Email with whitespace

Parameterized tests can reduce duplication while maintaining readable coverage.

32. Testing Business Rules

Business rules should receive strong unit test coverage.

Example:

Rule:
Only project managers can delete a project.

Tests should include:

Manager → Allowed

Developer → Rejected

Viewer → Rejected

Unknown Role → Rejected

Business rules should be tested independently of infrastructure whenever practical.

33. Testing State Transitions

Where entities or workflows have defined states, transitions should be tested.

Example:

TODO
 ↓
IN_PROGRESS
 ↓
COMPLETED

Tests should verify both valid and invalid transitions.

For example:

TODO → IN_PROGRESS     Valid

IN_PROGRESS → COMPLETED Valid

COMPLETED → TODO        Invalid
34. Testing Collections

Collection-processing logic should be tested for:

Empty collection
One element
Multiple elements
Duplicate elements
Null elements where allowed
Large collections where relevant

Tests should verify both result correctness and ordering where ordering is part of the contract.

35. Testing Mapping Logic

Mapping between entities and DTOs should be tested where mapping behavior contains meaningful logic.

Examples:

Entity → DTO

DTO → Entity

Entity → Response Model

Tests should verify:

Correct field mapping
Missing values
Optional fields
Nested values
Transformation rules
36. Testing Validation Logic

Validation logic should receive unit-level coverage.

Examples include:

Required fields
String length
Email format
Numeric limits
Allowed values
Cross-field rules

Both valid and invalid inputs should be tested.

37. Testing Utility Functions

Utility functions should generally have focused unit tests.

Examples:

Date formatting
String formatting
Pagination calculations
Sorting
Filtering
Conversion
Validation

Because utilities may be reused widely, defects can have broad impact.

38. Testing Security-Related Logic

Security-sensitive business logic should receive strong unit-level coverage.

Examples include:

Permission checks
Role evaluation
Access decisions
Token-related logic
Security validation

Unit tests do not replace dedicated security testing.

They provide an additional layer of protection.

39. Testing Logging Behavior

Logging generally should not be tested at the level of every individual log statement.

Testing may be appropriate when:

Logging is part of an important audit requirement.
Security events must be recorded.
Specific error information must be generated.

Avoid brittle tests that fail merely because a non-essential log message changes.

40. Testing Time

Time-dependent logic should use controllable time sources where possible.

Instead of directly relying on:

LocalDateTime.now()

application design may use an injectable or controllable clock where appropriate.

This allows tests to simulate:

Current time
Future time
Expired time
Boundary times
41. Testing Randomness

Random behavior should be controllable in tests.

Tests should not depend on unpredictable random output.

Where practical, inject a deterministic source of randomness or provide controlled test values.

42. Testing IDs

Tests should avoid unnecessary assumptions about generated identifiers.

Instead of asserting:

ID must equal 1

prefer validating:

ID exists
ID is valid
ID is unique

unless a specific ID value is part of the behavior being tested.

43. Test Naming

Test names should describe behavior.

Recommended pattern:

method_condition_expectedResult

Example:

createProject_validData_returnsProject

createProject_duplicateName_throwsException

getProject_unknownId_returnsNotFound

The exact naming convention may vary, but consistency is required.

44. Test Readability

Tests should be easier to understand than the implementation they validate.

Avoid:

Excessive abstraction
Deep helper chains
Unclear variables
Large setup blocks
Hidden assertions
Irrelevant configuration

A developer should be able to quickly understand why a test exists.

45. One Behavior Per Test

A test should preferably validate one meaningful behavior.

This does not mean a test must contain only one assertion.

Multiple assertions are acceptable when they collectively validate the same behavior.

Avoid tests that verify unrelated functionality simultaneously.

46. Meaningful Assertions

Every unit test should contain meaningful verification.

Avoid tests that merely execute code without checking the result.

Weak:

service.createProject(project);

Better:

Project result = service.createProject(project);

assertEquals(project.getName(), result.getName());
47. Avoiding Over-Testing

Not every line requires a separate test.

Testing effort should focus on:

Business behavior
Important conditions
Error handling
Risk
Publicly observable behavior

Tests should not be created simply to satisfy a numerical coverage target.

48. Avoiding Under-Testing

Important business logic should not remain untested.

Under-testing is particularly risky for:

Authentication
Authorization
Data validation
Business rules
Data transformations
Critical calculations
State transitions
49. Test Duplication

Duplicate tests should be avoided.

If multiple tests verify exactly the same behavior without providing additional value, the suite should be simplified.

However, similar-looking tests may remain justified when they validate different business conditions.

50. Refactoring and Unit Tests

Unit tests should provide confidence during refactoring.

If internal implementation changes while external behavior remains the same, well-designed behavioral tests should continue to pass.

Tests that depend heavily on internal implementation details may require unnecessary changes.

51. Unit Tests and Code Design

Difficult-to-test code may indicate design problems.

Potential warning signs include:

Large classes
Excessive dependencies
Hidden global state
Static dependencies
Complex constructors
Large methods
Tight coupling

Testability should therefore be considered during design and implementation.

52. Unit Test Execution

Developers should run relevant unit tests:

During development
Before committing
During pull requests
In CI pipelines
Before releases

The exact execution scope may vary according to the change.

53. Local Development Workflow

A recommended workflow is:

Modify Code
    ↓
Write / Update Unit Test
    ↓
Run Relevant Test
    ↓
Fix Failure
    ↓
Run Full Relevant Unit Suite
    ↓
Commit

This provides fast feedback during development.

54. CI Integration

Unit tests should be executed automatically in CI.

A typical sequence is:

Pull Request
     ↓
Build
     ↓
Unit Tests
     ↓
Quality Checks
     ↓
Integration Tests

A critical unit test failure should normally block progression through the pipeline.

55. Unit Test Failures

When a unit test fails, determine whether the cause is:

Production Defect
Test Defect
Test Data Problem
Configuration Problem
Environment Problem

The failure should not simply be ignored.

56. Flaky Unit Tests

Unit tests should normally be highly deterministic.

If a unit test fails intermittently, investigate:

Shared state
Time dependency
Randomness
Concurrency
Improper cleanup
Test-order dependency

Flaky unit tests should be corrected promptly.

57. Unit Test Performance

Unit tests should execute quickly.

Slow unit tests reduce developer feedback speed and discourage frequent execution.

Potential causes of slow unit tests include:

Unnecessary I/O
Real network calls
Real database calls
Excessive setup
Large datasets
Complex initialization

Such tests may belong at a higher testing level.

58. Unit Test Coverage

Coverage should be evaluated together with risk.

High-value coverage areas include:

Business rules
Error handling
Security-sensitive logic
Data validation
State transitions
Critical calculations

Coverage should not become a goal independent of meaningful behavior.

59. Mutation Testing

Mutation testing may be considered for critical business logic.

Mutation testing intentionally introduces small changes to production logic and checks whether tests detect them.

If tests continue to pass after a meaningful mutation, test strength may be insufficient.

Mutation testing should be introduced only when its maintenance cost is justified.

60. Test Maintenance

Unit tests should be maintained alongside production code.

Maintenance includes:

Updating tests after legitimate behavior changes
Removing obsolete tests
Fixing flaky tests
Improving unclear tests
Refactoring duplicated setup
Updating test dependencies

Outdated tests can become a form of technical debt.

61. Regression Tests

When a defect is discovered, a unit test should be added when the defect can be meaningfully reproduced at the unit level.

The desired workflow is:

Defect
  ↓
Root Cause
  ↓
Unit Test
  ↓
Fix
  ↓
Test Passes
  ↓
Regression Protection
62. Unit Tests for Defect Prevention

Unit tests should not only verify existing behavior.

They should also protect against known historical failures.

A previously discovered defect should influence future test coverage.

63. Test Data Privacy

Unit tests must not contain real production secrets or sensitive production data.

Avoid storing:

Real passwords
Production tokens
Private keys
Real customer information
Production database credentials

Use synthetic test data.

64. Test Configuration

Test configuration should remain separate from production configuration.

Examples include:

application-test.yml
test.properties
test environment variables

The exact mechanism depends on the technology stack.

Production credentials must never be required to execute unit tests.

65. Dependency Isolation

External dependencies should be isolated when testing unit behavior.

Examples:

Database       → Mock / Fake

External API   → Stub / Mock

Message Broker → Mock / Stub

Clock          → Controlled Clock

Random Source  → Deterministic Source

Integration testing should validate the actual integration separately.

66. Unit Testing and Integration Testing

Unit and integration tests serve different purposes.

Aspect	Unit Test	Integration Test
Scope	Small unit	Multiple components
Database	Usually mocked/isolated	Often real/test database
Network	Usually avoided	May be included
Speed	Very fast	Slower
Isolation	High	Lower
Main Goal	Logic correctness	Integration correctness

Both levels are required for comprehensive testing.

67. Unit Testing and End-to-End Testing

Unit tests validate isolated logic.

End-to-end tests validate complete workflows.

Example:

Unit Test
ProjectService.createProject()

        ↓

Integration Test
ProjectService + Database

        ↓

End-to-End Test
User creates project through UI

These layers should complement one another.

68. Unit Testing and Security

Unit tests should validate security-related decisions where practical.

Examples:

Admin → Allowed

Manager → Allowed

Employee → Restricted

Unauthenticated → Rejected

Dedicated security testing must still validate broader security behavior.

69. Unit Testing and Performance

Unit tests should be fast and should not be used as a substitute for performance testing.

Performance characteristics requiring realistic infrastructure should be tested at higher levels.

Unit tests may still validate computational complexity or performance-sensitive algorithms when appropriate.

70. Unit Testing and Accessibility

Pure backend unit tests generally do not validate accessibility.

Frontend unit or component tests may validate some accessibility-related behavior.

Dedicated accessibility testing remains necessary for broader user experience validation.

71. Pull Request Expectations

A pull request containing meaningful logic changes should normally include appropriate unit test changes.

Reviewers should consider:

Was new behavior tested?
Are important negative cases covered?
Are existing tests still valid?
Are tests readable?
Are assertions meaningful?
Is unnecessary mocking present?
72. Definition of Done for Unit Testing

Unit testing for a feature should generally be considered complete when:

Important business logic has appropriate unit coverage.
Positive scenarios are tested.
Negative scenarios are tested.
Important boundaries are tested.
Tests are deterministic.
Tests are readable.
Tests pass locally.
Tests pass in CI.
Relevant regression tests exist.
73. Recommended Unit Testing Checklist

Before completing a change, verify:

[ ] Important logic identified
[ ] Positive scenarios tested
[ ] Negative scenarios tested
[ ] Boundary cases considered
[ ] Null / empty cases considered
[ ] Exceptions tested
[ ] Tests are isolated
[ ] Tests are deterministic
[ ] Assertions are meaningful
[ ] Test names are clear
[ ] No unnecessary mocking
[ ] Relevant regression test added
[ ] Local tests pass
[ ] CI tests pass
74. Common Unit Testing Anti-Patterns
74.1 Testing Implementation Details

Tests should focus on behavior rather than private implementation structure.

74.2 Excessive Mocking

Too many mocks can make tests fragile and unrealistic.

74.3 No Assertions

Executing code without verifying results provides little value.

74.4 Giant Tests

Large tests covering unrelated behavior are difficult to maintain.

74.5 Shared Mutable State

Shared state creates test-order dependencies.

74.6 Random Test Data

Uncontrolled random data can produce non-reproducible failures.

74.7 Real External Dependencies

Real databases and APIs make unit tests slower and less isolated.

74.8 Coverage Obsession

High coverage does not automatically mean high-quality tests.

74.9 Ignoring Test Failures

A failing test should be investigated.

74.10 Ignoring Flaky Tests

Flaky tests reduce confidence in the entire test suite.

75. Recommended Test Structure

A typical WorkSphere unit test organization may follow the application structure.

Example:

backend/
└── src/
    ├── main/
    │   └── java/
    │       └── ...
    │
    └── test/
        └── java/
            └── ...

The exact directory structure should follow the selected backend framework and project conventions.

76. Frontend Unit Test Organization

Frontend tests should remain close to the functionality they validate where the frontend framework conventions support this approach.

Example:

src/
├── components/
│   ├── ProjectCard/
│   │   ├── ProjectCard.*
│   │   └── ProjectCard.test.*
│
├── services/
│   ├── project.service.*
│   └── project.service.test.*
│
└── utils/
    ├── validation.*
    └── validation.test.*

The exact structure should follow the WorkSphere frontend architecture.

77. Naming Test Files

Test files should have predictable names.

Examples:

ProjectServiceTest.java
ProjectControllerTest.java
ProjectValidatorTest.java
project.service.test.ts
validation.test.ts

The exact naming convention should remain consistent with the chosen framework.

78. Test Review Checklist

Reviewers should consider:

[ ] Does the test validate meaningful behavior?
[ ] Is the expected result clear?
[ ] Are assertions meaningful?
[ ] Are important edge cases covered?
[ ] Is the test isolated?
[ ] Is unnecessary mocking avoided?
[ ] Is the test deterministic?
[ ] Is the test maintainable?
[ ] Does the test avoid implementation coupling?
79. Unit Testing Metrics

Useful metrics may include:

Unit test pass rate
Unit test execution time
Unit test failure rate
Coverage of critical logic
Flaky test count
Test maintenance effort

Metrics should be used to improve engineering practices rather than create artificial targets.

80. Continuous Improvement

Unit testing practices should evolve as WorkSphere evolves.

Improvements may include:

Better test isolation
Faster test execution
Improved fixtures
Better coverage of critical logic
Reduced duplication
Improved test naming
Better CI integration
Better failure diagnostics

The unit test suite should remain a long-term engineering asset.

81. Final Principles

The WorkSphere unit testing strategy can be summarized as:

Test small units of behavior.
Keep unit tests fast.
Keep unit tests deterministic.
Isolate external dependencies.
Test both valid and invalid behavior.
Test important boundaries.
Use meaningful assertions.
Avoid unnecessary implementation coupling.
Avoid excessive mocking.
Maintain tests with production code.
Add regression tests for important defects.
Use coverage as a supporting metric.
Treat test failures seriously.
Fix flaky tests.
Run unit tests continuously through CI.
Prioritize critical business logic.
Keep tests readable and maintainable.
Use unit tests as an engineering feedback mechanism.

82. Conclusion

Unit testing provides the fastest and most focused layer of automated validation within the WorkSphere testing strategy.

Well-designed unit tests help developers:

Detect defects early.
Refactor safely.
Understand expected behavior.
Validate business rules.
Protect against regressions.
Receive fast feedback.
Improve code quality.

Unit testing does not replace integration, system, security, performance, accessibility, or end-to-end testing.

Instead, it forms the foundation upon which the broader WorkSphere testing strategy is built.

The objective is not to create the largest possible test suite.

The objective is to create a fast, reliable, meaningful, maintainable, and risk-focused unit test suite that gives the WorkSphere engineering team confidence to evolve the system safely.