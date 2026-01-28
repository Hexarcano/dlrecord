---
name: mockito-2.25.0
description: >
    Standards for mocking dependencies using Mockito 2.25.0+.
    Trigger: When you need to SIMULATE the behavior of components to support a Unit Test.
metadata:
  author: hexarcano
  version: "1.0"
  scope: [backend, testing]
  auto_invoke:
    - "Simulating component behavior"
    - "Isolating logic from dependencies"
    - "Verifying interactions between components"
    - "Refactoring existing tests"
allowed-tools: Read, Edit, Write, Glob, Grep, Bash, WebFetch, WebSearch, Task
---

# Mockito Testing Standards

> 💡 **Usage**: Apply this skill when writing Unit Tests where the SUT (System Under Test) depends on other components that need to be simulated.

## 1. Proficiency Requirements

- **Core Stacks**: `org.mockito.Mockito`, `org.mockito.ArgumentCaptor`, `org.mockito.junit.jupiter.MockitoExtension`
- **Key Concepts**: Stubbing (`when`), Verification (`verify`), Strictness (UnnecessaryStubbingException).

## 2. Rules & Standards

| Category | Rule | Why? |
|----------|------|------|
| **Extension** | `Test class must use @ExtendWith(MockitoExtension.class)` | Initializes mocks and manages lifecycle automatically |
| **Strictness** | **NEVER** use `lenient()` without a documented reason | Mockito 2+ fails fast if you mock something that isn't called. This keeps tests clean. |
| **Verification** | Verify **behavior**, not implementation details. | Check calls that *should* happen, don't verify strict order unless necessary (`InOrder`). |
| **Stubs** | `when(mock.method()).thenReturn(val)` | Standard syntax. Avoid `doReturn` unless mocking void/spy methods. |
| **Field Injection** | Use `@InjectMocks` for the implementation, `@Mock` for interfaces | Automates constructor injection for the test subject. |

## 3. Implementation Patterns

### Pattern A: Standard Service Test with Mocks

> **When to use**: Testing a service that calls a repository.

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository; // Dependency

    @InjectMocks
    private UserService userService; // SUT (System Under Test)

    @Test
    void shouldFindUserById() {
        // Arrange
        User expectedUser = new User("123", "John");
        when(userRepository.findById("123")).thenReturn(Optional.of(expectedUser));

        // Act
        User actual = userService.findById("123");

        // Assert
        assertEquals("John", actual.getName());
        verify(userRepository).findById("123"); // Optional redundancy if return value is asserted
    }
}
```

### Pattern B: Argument Captor (Validation of Input)

> **When to use**: To check WHAT was passed to a dependency (e.g. saving an entity).

```java
@Captor
ArgumentCaptor<User> userCaptor;

@Test
void shouldSaveUserWithCorrectData() {
    // Act
    userService.register("Jane", "jane@test.com");

    // Assert (Verify the dependency was called with the right data)
    verify(userRepository).save(userCaptor.capture());
    User savedUser = userCaptor.getValue();
    
    assertEquals("Jane", savedUser.getName());
    assertEquals("jane@test.com", savedUser.getEmail());
}
```

### Pattern C: Void Methods / Exceptions

> **When to use**: When the dependency returns void or you want to force an error.

```java
@Test
void shouldHandleRepositoryError() {
    // Arrange
    doThrow(new DatabaseException()).when(userRepository).deleteById(anyString());

    // Act & Assert
    assertThrows(ServiceException.class, () -> userService.deleteUser("123"));
}
```

## 4. Verification Checklist

- [ ] **One Concept**: Test validates ONE specific behavior, not a sequence of events.
- [ ] **Clean Setup**: Mocks are setup in `Arrange` phase, not mixed with assertions.
- [ ] **No Logic in Test**: Test contains linear calls, no `if`, `for` loops or complex calculations.
- [ ] **Strict Mocks**: No unnecessary stubs (Mockito throws exception if a stub is unused).
