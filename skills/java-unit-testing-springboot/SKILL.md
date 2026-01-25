---
name: java-unit-testing-springboot
description: >
  Best practices for writing Unit Tests in Spring Boot using JUnit 5 and Mockito.
  Trigger: When the user asks to create unit tests, debug failing tests, or optimize test coverage for Java/Spring Boot.
metadata:
  author: hexarcano
  scope: [backend, coding]
  auto_invoke: "Writing Java code, creating classes, writing tests"
---

## When to Use

- Creating Unit Tests for **Application Layer** (Use Cases, Services).
- Mocking external dependencies (Repositories, Ports).
- Verifying business logic, validations, and conditional execution paths.
- **NOT** for Integration Tests (Controller/WebMvc) involving Spring Context or Security (unless specifically requested).

## Critical Patterns (The "Golden Rules")

1.  **Strict Mocking**: Use `@ExtendWith(MockitoExtension.class)`. Do not load the Spring Context (`@SpringBootTest`) for unit tests.
2.  **Naming Convention**: Use `should[ExpectedBehavior]_When[Condition]`.
    *   Example: `shouldUpdateBrand_WhenNameIsDifferent`
    *   Example: `shouldThrowException_WhenNameIsInvalid`
3.  **Variable Consistency**: Extract IDs to variables (`String uuid = "uuid-123"`) and use the **same variable** for both Mock setup (`when`) and Method execution.
4.  **Verification is Mandatory**:
    *   Use `verify(repo, times(1)).save(...)` to confirm side-effects.
    *   Use `verify(repo, never()).save(...)` to confirm optimizations (e.g., no-change updates).
5.  **Test Exceptions**: Always test the "Sad Path". Use `assertThrows(Exception.class, () -> call())` and verify that **no persistence occurred** (`never()`).

## Code Examples

### 1. The "Happy Path" (Update with Changes)

```java
@Test
void shouldUpdateEntity_WhenChangesDetected() {
    // Arrange
    String uuid = "uuid-123";
    MyEntity existing = new MyEntity(uuid, "OldValue");
    UpdateCommand command = new UpdateCommand("NewValue");

    when(repository.findById(uuid)).thenReturn(Optional.of(existing));
    // Mock the return of save to avoid NullPointerException if return value is used
    when(repository.save(any(MyEntity.class))).thenAnswer(i -> i.getArgument(0));

    // Act
    Optional<MyEntity> result = useCase.update(uuid, command);

    // Assert
    assertTrue(result.isPresent());
    assertEquals("NewValue", result.get().getValue());
    verify(repository, times(1)).save(any(MyEntity.class)); // CRITICAL
}
```

### 2. The "Optimization Path" (No Changes)

```java
@Test
void shouldNotCallSave_WhenNoChanges() {
    // Arrange
    String uuid = "uuid-123";
    MyEntity existing = new MyEntity(uuid, "SameValue");
    UpdateCommand command = new UpdateCommand("SameValue");

    when(repository.findById(uuid)).thenReturn(Optional.of(existing));

    // Act
    useCase.update(uuid, command);

    // Assert
    // Verify that the database was NOT touched
    verify(repository, never()).save(any(MyEntity.class));
}
```

### 3. The "Sad Path" (Validation Exception)

```java
@Test
void shouldThrowException_WhenDataIsInvalid() {
    // Arrange
    String uuid = "uuid-123";
    UpdateCommand command = new UpdateCommand(""); // Invalid data

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> {
        useCase.update(uuid, command);
    });

    // Verification: Ensure transaction safely aborted before persistence
    verify(repository, never()).save(any(MyEntity.class));
}
```

## Troubleshooting

| Issue | Cause | Fix |
|-------|-------|-----|
| `NullPointerException` in Test | Mock returning null implicitly | Use `when(...).thenReturn(...)` or `thenAnswer(...)`. |
| `Wanted but not invoked: save` | Logic didn't reach save | Check `if` conditions or ensure inputs trigger the change. |
| `Argument(s) are different!` | `equals()` not implemented | Use Lombok `@Data` or `@EqualsAndHashCode` on Entities/VOs. |
| `UnnecessaryStubbingException` | Strict Stubbing active | Remove `when` calls for scenarios that don't occur in that specific test. |
