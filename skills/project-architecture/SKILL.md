---
name: project-architecture
description: Strict guidelines for Hexagonal Architecture (Ports & Adapters) and Domain-Driven Design (DDD) structure.
metadata:
  author: hexarcano
  scope: [backend, architecture]
  auto_invoke: "Creating new modules, refactoring package structure, designing system components"
allowed-tools: Read, Write, Glob, Grep
---

# Project Architecture Rules (Hexagonal & DDD)

## Core Principles

1.  **Dependency Rule**: Dependencies point **INWARDS**.
    *   `Domain` depends on NOTHING.
    *   `Application` depends on `Domain`.
    *   `Infrastructure` depends on `Application` and `Domain`.
2.  **Separation of Concerns**: Isolate business logic (`Domain`) from technical details (`Infrastructure`).

## Module Structure (Bounding Context)

Each logical module (e.g., `auth`, `devicemodel`) must follow this directory layout:

```text
<module_name>/
├── application/                # ORCHESTRATION LAYER
│   ├── implementation          # Implementation of 'port.in' (Use Cases)
│   ├── port/
│   │   ├── in/                 # Use Case Interfaces (Inputs)
│   │   │   └── command/        # Input Commands (Records/POJOs) for Use Cases
│   │   └── out/                # Repository/External System Interfaces (Outputs)
│   └── service/                # Facade joining and implementing all Input Ports, delegating to implementation classes.
├── domain/                     # BUSINESS RULES LAYER
│   ├── model/                  # Entities, Aggregates, Value Objects (POJOs)
│   └── exception/              # Domain-specific exceptions
└── infrastructure/             # ADAPTERS LAYER
    ├── adapter/                # Implementations of 'port.out'
    ├── config/                 # Spring @Configuration beans
    ├── controller/             # REST Controllers
    ├── entity/                 # JPA/Database Entities
    └── repository/             # Spring Data Repositories
```

## Layer Definitions & Rules

### 1. Domain Layer (`domain`)

*   **Content**: Pure Java objects reflecting business reality.
*   **Rules**:
    *   ✅ **POJOs Only**: No Spring framework annotations (`@Service`, `@Autowired`).
    *   ❌ **No Persistence**: Never use JPA annotations (`@Entity`, `@Table`) here.
    *   Logic: Rich behavioral models.

### 2. Application Layer (`application`)

*   **Content**: Application logic, flow control.
*   **Rules**:
    *   ✅ **Implementation**: Actual implement `port.in` of use cases.
    *   ✅ **Service**: Facade that centralizes and call `port.in` interfaces for controller calls.
    *   ✅ **Ports**: Interfaces only. `port.in` defines what the app *can do*. `port.out` defines what the app *needs*.
    *   ✅ **Commands**: Use specific Command objects (Java Records) in `port.in.command` for input data (e.g. `UpdateDeviceCommand`), decoupling Controller from Domain.
    *   ❌ **Anotations**: Never use JPA / framework annotations.

### 3. Infrastructure Layer (`infrastructure`)

*   **Content**: Framework specific implementations (Web, Database, Messaging).
*   **Rules**:
    *   ✅ **Controllers**: Convert HTTP DTOs to Commands (App Layer). Call `application.service`.
    *   ✅ **Persistence**: Implement `port.out`. Map `domain.model` to `infrastructure.entity` before saving.
    *   ✅ **Access**: Never access `infrastructure` classes from `domain` or `application`.
    *   ✅ **Configuration**: All module configuration using spring `@Configuration` annotation in <\<module_name>ModuleConfig>.
    *   ❌ **Anotations**: Never use Spring configuration annotation outside <\<module_name>ModuleConfig>.


## Data Conversion Strategy

*   **Request** (`Web DTO`) → **Controller** → `Command` → `Domain` (Service)
*   **Service** → **Repository Port**
*   **Repository Adapter** → `Domain` → `JPA Entity` (save)
*   **Database** → `JPA Entity` → `Domain` (load)
