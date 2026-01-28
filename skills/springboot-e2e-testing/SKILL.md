---
name: springboot-e2e-testing
description: >
  Standards for End-to-End (E2E) and Integration Testing using TestContainers (Docker).
  Trigger: When creating full integration tests that require a real database or running E2E scenarios.
metadata:
  author: hexarcano
  scope: [backend, testing, e2e]
  auto_invoke:
    - "Creating E2E tests"
    - "Configuring TestContainers"
    - "Testing with real database (PostgreSQL)"
allowed-tools: Read, Edit, Write, Glob, Grep
---

# E2E & Integration Testing Standards (TestContainers)

## 1. Core Principles

E2E tests verify the **entire application stack** working together.

*   ✅ **Real Database**: Uses **TestContainers** (Docker) to run a real PostgreSQL instance. Never use H2 for E2E.
*   ✅ **Real Context**: Uses `@SpringBootTest(webEnvironment = RANDOM_PORT)` to start the server.
*   ✅ **No Mocks**: Do **NOT** use `@MockitoBean`. Test the real flow. (Exceptions: 3rd party APIs like Stripe/SendGrid).

## 2. Configuration Pattern (Singleton Container)

To avoid restarting Docker for every test class (which is slow), use the **AbstractBaseIntegrationTest** pattern.

### Base Class Structure

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class AbstractIntegrationTest {

    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
```

## 3. Implementation Rules

### Testing HTTP Endpoints

Use `TestRestTemplate` or `WebTestClient` to make real HTTP calls to your running server.

```java
class BrandE2ETest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateAndPersistBrand() {
        // 1. Send Request
        CreateBrandRequest request = new CreateBrandRequest("Tesla");
        ResponseEntity<BrandResponse> response = restTemplate.postForEntity("/api/v1/brands", request, BrandResponse.class);

        // 2. Verify Response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().uuid()).isNotNull();

        // 3. Verify Database (Optional but recommended)
        // You can inject the repository here to check if it's in the DB
    }
}
```

## 4. Dependencies

Ensure `build.gradle` includes:
```groovy
testImplementation "org.springframework.boot:spring-boot-testcontainers"
testImplementation "org.testcontainers:postgresql"
testImplementation "org.testcontainers:junit-jupiter"
```
