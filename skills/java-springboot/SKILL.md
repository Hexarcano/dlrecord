---
name: java-springboot
description: Java coding standards, Spring Boot best practices, Lombok usage, and Testing guidelines.
metadata:
  author: hexarcano
  scope: [backend, coding]
  auto_invoke: "Writing Java code, creating classes, writing tests"
allowed-tools: Read, Edit, Write, Grep
---

# Java & Spring Boot Coding Standards

## 1. Lombok Usage

Use Lombok to reduce boilerplate, but follow these rules:
*   ✅ **@Data**: Use for DTOs and mutable domain objects (if carefully designed).
*   ✅ **@Value**: Use for immutable Value Objects (Domain).
*   ✅ **@Builder**: Recommended for complex object creation.
*   ✅ **@RequiredArgsConstructor**: MANDATORY for Dependency Injection (Controllers, Services, Adapters).
*   ✅ **@Slf4j**: For logging.
*   ⚠️ **@ToString** / **@EqualsAndHashCode**: Be careful with circular dependencies (exclude relationships).
*   ❌ **@AllArgsConstructor**: Forbidden on Spring Components (use RequiredArgs); Discouraged on JPA Entities.

## 2. Java Modern Practices (Java 25)

*   **Var**: Use `var` for local variables where type is obvious.
*   **Records**: Use `record` for simple data carriers (DTOs, Value Objects) if mutability is not required.
*   **Streams**: Use Streams API for collection processing. Avoid complex nested `for` loops.
*   **Switch**: Use enhanced `switch` expressions.

## 3. Spring Boot Patterns

*   **Dependency Injection**: Constructor Injection always (`@RequiredArgsConstructor`).
*   **Validation**: Use `jakarta.validation` (`@NotNull`, `@Email`) on DTOs.
*   **Exception Handling**:
    *   Throw specific runtime exceptions (e.g., `UserNotFoundException`).
    *   Let `@ControllerAdvice` handle the HTTP response mapping.

## 4. Import Management

### Prohibit Fully Qualified Names (FQN)

**Rule:** Do NOT use fully qualified names for classes inside methods, parameters, or fields if they can be imported.
**Exception:** Only use FQN if there is a class name collision (e.g. `java.util.Date` vs `java.sql.Date`).

**Bad Example:**

```java
public void configure(org.springframework.security.config.annotation.web.builders.HttpSecurity http) {
    http.sessionManagement(s -> s.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS));
}
```

**Good Example:**

```java
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

public void configure(HttpSecurity http) {
    http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
}
```

## 5. Naming Conventions

*   **Classes**: PascalCase (`UserService`).
*   **Methods**: camelCase (`saveUser`).
*   **Constants**: UPPER_SNAKE_CASE (`MAX_RETRY_COUNT`).
*   **Generics**: Single uppercase letter (`T`, `R`) or descriptive (`TUser`).

## 6. Testing

> **IMPORTANT**: For detailed Testing patterns (Mocks, Verifications, Optimizations), refer to the specialized **`java-unit-testing-springboot`** skill.

*   **Framework**: JUnit 5 + Mockito.
*   **Unit Tests**: Use `@ExtendWith(MockitoExtension.class)`.
*   **Integration Tests**: Use `@SpringBootTest(webEnvironment = RANDOM_PORT)`.

## 7. Code Style (Spotless/Checkstyle)

*   **Indentation**: 4 spaces (or match project .editorconfig).
*   **Braces**: K&R style (same line).
*   **Imports**: No wildcards (`import java.util.*` is forbidden).
