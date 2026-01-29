---
name: springboot
description: >
    Java coding standards, Spring Boot best practices, Lombok usage, and Testing guidelines.
    Trigger: When implementing backend logic (Services, Controllers, APIs) or modifying Spring Components.
metadata:
  author: hexarcano
  scope: [backend, coding]
  auto_invoke:
    - "Creating Services or Repositories"
    - "Implementing DTOs or Models"
    - "Designing REST Controllers"
    - "Applying Lombok annotations
    - "Refactoring existing code"
allowed-tools: Read, Edit, Write, Glob, Grep, Bash, WebFetch, WebSearch, Task
---

# Java & Spring Boot Coding Standards

## 1. Lombok Usage

Use Lombok to reduce boilerplate, but follow these rules:
*   ✅ **@Data**: Use for DTOs and mutable domain objects. **FORBIDDEN** on JPA Entities (breaks hashcode/equals/lazy loading).
*   ✅ **@Getter / @Setter**: Use this combination for JPA Entities.
*   ✅ **@Value**: Use for immutable Value Objects (Domain).
*   ✅ **@Builder**: Recommended for complex object creation.
*   ✅ **@RequiredArgsConstructor**: MANDATORY for Dependency Injection (Controllers, Services, Adapters).
*   ✅ **@Slf4j**: For logging.
*   ⚠️ **@ToString** / **@EqualsAndHashCode**: Be careful with circular dependencies (exclude relationships).
*   ❌ **@AllArgsConstructor**: Forbidden on Spring Components (use RequiredArgs); Discouraged on JPA Entities.

## 2. Architecture & Packaging (Hexagonal Quick-Ref)

*   **Structure**: Feature-based packaging (`com.hexarcano.dlrecord.<feature>`).
    *   `domain`: Pure Java logic (Models, Exceptions). NO Spring dependencies.
    *   `application`: Use Cases (`Service`), Input/Output Ports.
    *   `infrastructure`: Adapters (Controllers, JPA Entities).
*   **Mapping**: Manual mapping methods preferred inside DTOs/Entities (`toDomainModel()`, `fromDomainModel()`).

> **NOTE:** For deep architectural rules (Layers, Dependencies), refer to `skills/project-architecture/SKILL.md`.

## 3. Java Modern Practices

*   **Records**: Use `record` for simple data carriers (DTOs, Value Objects) if mutability is not required.
*   **Streams**: Use Streams API for collection processing. Avoid complex nested `for` loops.
*   **Switch**: Use enhanced `switch` expressions.

## 4. Spring Boot & JPA Patterns

*   **Auto-Configuration & Dependency Injection**:
    *   **No Manual Config**: Rely on Spring Boot's auto-configuration and component scanning. Avoid manual `@Bean` definitions or `new` keyword instantiation unless strictly necessary (e.g., external libraries).
    *   **IoC Container**: Let Spring manage beans using annotations (`@Service`, `@Component`).
    *   **Injection Style**: Constructor Injection (`@RequiredArgsConstructor`) is **MANDATORY**. Field injection (`@Autowired`) is **FORBIDDEN**.
*   **IDs**: Use **UUID** (String) for primary keys (`@GeneratedValue(strategy = GenerationType.UUID)`).
*   **Controller**: Use `@RequestMapping("/api/v1/<resource>")`. Return `ResponseEntity`. Prefer fluent API (e.g., `ResponseEntity.notFound().build()`) over `new ResponseEntity<>(HttpStatus...)`.
*   **Validation**: Use `jakarta.validation` (`@NotNull`, `@Email`) on DTOs.
*   **Exception Handling**:
    *   **Custom Exceptions**: Create specific RuntimeExceptions for Use Case/Domain validations (e.g., `InvalidBrandNameException`).
    *   **Global Handling**: Register ALL custom exceptions in the `GlobalExceptionHandler` (`@RestControllerAdvice`) to map them to appropriate HTTP statuses.
    *   **No Logic in Controller**: Do NOT use try-catch blocks in Controllers; let the Global Handler utilize the exception mapping.
*   **Auditing**: Use `@EntityListeners(AuditingEntityListener.class)` on Entities with `createdAt`/`updatedAt`.
*   **Pagination**: **MANDATORY** for list endpoints (`findAll`). Use `Pageable` as Controller argument and return `Page<T>` (not `List<T>`).
*   **Entities**: MUST annotate with `@Entity` and `@Table(name = "snake_case_name")`. Do not rely on default naming.

## 5. Import Management

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

## 6. Naming Conventions

*   **Classes**: PascalCase (`UserService`).
*   **Methods**: camelCase (`saveUser`).
*   **Constants**: UPPER_SNAKE_CASE (`MAX_RETRY_COUNT`).
*   **Generics**: Single uppercase letter (`T`, `R`) or descriptive (`TUser`).

## 7. Testing

> **IMPORTANT**: For detailed Testing patterns (Mocks, Verifications, Optimizations), refer to the specialized **`java-unit-testing-springboot`** skill.

*   **Framework**: JUnit 5 + Mockito.
*   **Unit Tests**: Use `@ExtendWith(MockitoExtension.class)`.
*   **Integration Tests**: Use `@SpringBootTest(webEnvironment = RANDOM_PORT)`.

## 8. Code Style (Spotless/Checkstyle)

*   **Indentation**: 4 spaces (or match project .editorconfig).
*   **Braces**: K&R style (same line).
*   **Imports**: No wildcards (`import java.util.*` is forbidden).
