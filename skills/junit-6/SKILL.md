---
name: junit-6
description: >
    JUnit 6 (Jupiter) Testing Framework patterns.
    Trigger: When writing UNIT or INTEGRATION tests to verify business logic or component behavior.
metadata:
  author: hexarcano
  version: "1.0"
  scope: [backend, testing]
  auto_invoke:
    - "Verifying business logic in Use Cases or Models"
    - "Testing web layer responses (Controllers)"
    - "Ensuring component correctness with assertions"
    - "Refactoring existing tests"
allowed-tools: Read, Edit, Write, Glob, Grep, Bash, WebFetch, WebSearch, Task
---

# JUnit 6 Testing Standards

> 💡 **Usage**: Apply this skill when writing **Unit** or **Integration** tests in Java.

## 1. Proficiency Requirements

- **Core Stacks**: JUnit Jupiter API (`org.junit.jupiter.api`)
- **Key Concepts**: Lifecycle (`@BeforeAll`, `@BeforeEach`), Assertions, Parameterized Tests.

## 2. Rules & Standards

| Category | Rule | Why? |
|----------|------|------|
| **Visibility** | Test classes and methods should be `package-private` (no `public`) | Less boilerplate, JUnit Jupiter supports it natively |
| **Lifecycle** | Use `@BeforeEach` for setup, avoid `@BeforeAll` unless expensive | better isolation |
| **Assertions** | Use `Assertions.assert*` or AssertJ `assertThat` | Standard, readable error messages |
| **Display** | Use `@DisplayName("should expected behavior when state")` | Readable reports |
| **Null Safety** | Use JSpecify (`@Nullable`, `@NonNull`) | Catch NPEs at compile time (JUnit 6 standard) |
| **Data-Driven** | Use `@ParameterizedTest` instead of duplicating methods | Reduces code duplication and covers edge cases |

## 3. Implementation Patterns

### Pattern A: Standard Test Class

> **When to use**: Creating a new Unit Test class.

```java
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Test
    @DisplayName("should throw exception when user not found")
    void shouldThrowExceptionWhenUserNotFound() {
        // ...
    }
}
```

### Pattern B: Parameterized Test

> **When to use**: Testing multiple data inputs for the same logic.

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@ParameterizedTest
@CsvSource({
    "test@example.com, true",
    "invalid-email, false"
})
void shouldValidateEmail(String email, boolean expected) {
    assertEquals(expected, validator.isValid(email));
}
```

## 4. Verification Checklist

- [ ] **AAA Pattern**: Test follows Arrange (Setup) -> Act (Method Call) -> Assert (Validation) structure.
- [ ] **Scenarios**: Both Happy Path (Success) and Sad Path (Exceptions/Edge cases) are covered.
- [ ] **Cleanliness**: No `System.out.println` used; logic validates ONLY the specific unit/component.
