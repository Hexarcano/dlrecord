---
name: project-architecture
description: >
    Strict guidelines for Hexagonal Architecture (Ports & Adapters) and Domain-Driven Design (DDD) structure.
    Trigger: When creating new modules, refactoring package structure, or designing system components.
metadata:
  author: hexarcano
  version: "1.0"
  scope: [backend, architecture]
  auto_invoke:
    - "Creating new feature modules (e.g. auth, payment)"
    - "Checking naming convention"
    - "Refactoring package structure"
    - "Designing Domain Models or Use Cases"
allowed-tools: Read, Write, Glob, Grep, Task
---

# Project Architecture Rules (Hexagonal & DDD)

## Core Principles

1.  **Dependency Rule**: Dependencies point **INWARDS**.
    *   `Domain` depends on NOTHING.
    *   `Application` depends on `Domain`.
    *   `Infrastructure` depends on `Application` and `Domain`.
2.  **Separation of Concerns**: Isolate business logic (`Domain`) from technical details (`Infrastructure`).


## Module Structure (Hexagonal Feature-Based)

Structure by Feature (`com.hexarcano.dlrecord.<feature>`):

```text
<feature>/
├── domain/                     # 1. ENTERPRISE LOGIC (Pure Java)
│   ├── model/                  # POJOs (Entities, Aggregates)
│   └── exception/              # Domain-specific exceptions
├── application/                # 2. APPLICATION LOGIC
│   ├── port/
│   │   ├── in/                 # Interfaces (Use Cases)
│   │   └── out/                # Interfaces (Repo/External Ports)
│   ├── service/                # Implementation of Input Ports
│   └── implementation/         # Specific logic for use cases if needed
└── infrastructure/             # 3. ADAPTERS (Frameworks)
    ├── controller/             # REST Adapters (Web)
    ├── entity/                 # JPA Entities (Persistence)
    ├── repository/             # Spring Data Interfaces
    ├── adapter/                # Implementation of Output Ports
    └── config/                 # Spring Configurations
```

## Naming Strategy (Strict)

| Component | Pattern | Example |
| :--- | :--- | :--- |
| **Use Case (Interface)** | `<Verb><Resource>UseCase` | `CreateBrandUseCase` |
| **Use Case (Impl)** | `<Verb><Resource>` | `CreateBrand` |
| **Command (Input)** | `<Verb><Resource>Command` | `CreateBrandCommand` |
| **Service (Facade)** | `<Resource>Service` | `BrandService` |
| **Repository Port** | `<Resource>RepositoryPort` | `BrandRepositoryPort` |
| **Repository Adapter** | `Jpa<Resource>RepositoryAdapter` | `JpaBrandRepositoryAdapter` |
| **JPA Entity** | `<Resource>Entity` | `BrandEntity` |
| **Controller** | `<Resource>Controller` | `BrandController` |
| **DTO (Response/Request)** | `<Action><Resource><Request/Response>` | `CreateBrandRequest` |

## Layer Rules & Mappings

### 1. Domain Layer (`domain`)

*   **Rules**: Zero Framework Dependencies (No Spring, No JPA).
*   **Content**: Business value objects composed of primitive types.

### 2. Application Layer (`application`)

*   **Rules**: Orchestrates the flow. Depends strictly on `domain` and `port`.
*   **Ports**: Interfaces defining WHAT to do, not HOW.

### 3. Infrastructure Layer (`infrastructure`)

*   **Rules**: Implements the technical details. Depends on `application` and `domain`.
*   **Mapping Strategy (CRITICAL)**:
    *   **Manual Mapping**: Do NOT use AutoMapper/MapStruct unless authorized.
    *   **Entities**: Must have `toDomainModel()` and `fromDomainModel()` methods.
    *   **Controller**: Maps DTOs -> Commands -> Domain Models.

## Data Flow

`Request` -> `Controller` -> `Service (Port In)` -> `Domain Logic` -> `Port Out` -> `Adapter` -> `DB/External`
