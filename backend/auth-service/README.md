# WorkSphere Auth Service

Standalone Spring Boot foundation for the WorkSphere authentication service.

## Stack

- Java 21
- Spring Boot 3.5.x
- Maven
- PostgreSQL with Spring Data JPA
- Flyway
- Spring Web, Validation, and Actuator

## Run

From this directory:

```text
mvn spring-boot:run
```

The default port is `8081`. Runtime settings are externalized through environment variables; see `src/main/resources/application.yml` for the available placeholders.

This scaffold intentionally contains no authentication flows, JWT or password handling, entities, repositories, migrations, or business logic.
