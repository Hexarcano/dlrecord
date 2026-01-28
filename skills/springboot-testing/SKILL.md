---
name: java-unit-testing-springboot
description: >
  Best practices for testing Spring Boot applications: Unit Tests (JUnit 5 + Mockito) and Slice Testing.
  Trigger: Creating tests for Services, Controllers, or Integration scenarios.
metadata:
  author: hexarcano
  scope: [backend, testing]
  auto_invoke:
    - "Writing Unit Tests for Services"
    - "Testing Controllers (REST API)"
    - "Debugging test failures"
allowed-tools: Read, Edit, Write, Glob, Grep
---

# Spring Boot Testing Strategy

## 1. Testing Pyramid (Strict Application)

| Component | Annotation | Scope | Goal |
| :--- | :--- | :--- | :--- |
| **Service / Use Case** | `@ExtendWith(MockitoExtension.class)` | **Unit** (Pure Java) | Verify logic (ifs, loops), Mock dependencies. Fast. |
| **Controller** | `@WebMvcTest(MyController.class)` | **Slice** (Web Layer) | Verify JSON serialization, HTTP Status, Exception Mapping. Mocks Service. |
| **Integration (Database)** | `@DataJpaTest` | **Slice** (JPA Layer) | Verify Queries, Constraints, Relationships. |
| **Full App E2E** | `@SpringBootTest` | **Integration** (Full Context) | **See `springboot-e2e-testing` skill.** |

## 2. Rules by Layer

### A. Service Layer (Unit Tests)

*   **Context**: **NO Spring Context**.
*   **Tools**: JUnit 5 + Mockito.
*   **Rule**: Use `verify()` to assert side effects (`save`, `delete`).

### B. Controller Layer (`@WebMvcTest`)

*   **Context**: Only loads Web bean + Security Config.
*   **Rule**: Mock the Service (`@MockitoBean` or `@MockBean`).
*   **Assertions**: prefer `jsonPath("$.field")` and `status().isOk()`.

> **NOTE**: For **Real Database** or **Full Context** tests, refer to `skills/springboot-e2e-testing/SKILL.md`.

## 3. Critical Patterns (The "Golden Rules")

1.  **Strict Mocking**: If a Mock isn't called, the test should fail (`strictness = Strictness.STRICT_STUBS`).
2.  **Naming Convention**: `should[ExpectedBehavior]_When[Condition]`.
    *   Example: `shouldReturn200_WhenBrandExists`
3.  **Verification**:
    *   `verify(repo).save(any())` -> Mandatory for Write operations.
    *   `verify(repo, never()).save(any())` -> Mandatory for Read-only/Error paths.
4.  **Test Exceptions**: Use `assertThrows()` for "Sad Paths".

## 4. Common Pitfalls

*   ❌ **Using `@SpringBootTest` for Controller Unit Tests**: It's slow and conceptually wrong if you mock the service anyway. Use `@WebMvcTest`.
*   ❌ **Testing Frameworks**: Don't test that Spring works (e.g. `@NotNull`). Test **YOUR** logic.
*   ❌ **Leaking Context**: `@MockBean` resets the Spring Context. Use imports carefully to avoid reloading context per test class.
