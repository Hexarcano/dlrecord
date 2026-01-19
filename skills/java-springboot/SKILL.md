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
*   ✅ **@RequiredArgsConstructor**: Use for dependency injection (final fields) instead of `@Autowired` on fields.
*   ✅ **@Slf4j**: For logging.
*   ⚠️ **@ToString** / **@EqualsAndHashCode**: Be careful with circular dependencies (exclude relationships).
*   ❌ **@AllArgsConstructor**: Avoid on JPA Entities (breaks lazy loading proxies sometimes).

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

## 4. Naming Conventions
*   **Classes**: PascalCase (`UserService`).
*   **Methods**: camelCase (`saveUser`).
*   **Constants**: UPPER_SNAKE_CASE (`MAX_RETRY_COUNT`).
*   **Generics**: Single uppercase letter (`T`, `R`) or descriptive (`TUser`).

## 5. Testing
*   **Framework**: JUnit 5 + Mockito.
*   **Unit Tests**:
    *   Classes ending in `Test`.
    *   Use `@ExtendWith(MockitoExtension.class)`.
    *   Mock dependencies using `@Mock` and inject with `@InjectMocks`.
*   **Integration Tests**:
    *   Classes ending in `IT`.
    *   Use `@SpringBootTest(webEnvironment = RANDOM_PORT)`.

## 6. Code Style (Spotless/Checkstyle)
*   **Indentation**: 4 spaces (or match project .editorconfig).
*   **Braces**: K&R style (same line).
*   **Imports**: No wildcards (`import java.util.*` is forbidden).
