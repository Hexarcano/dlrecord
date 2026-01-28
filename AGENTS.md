# Repository Guidelines

> **Single Source of Truth** - This file is the master for all AI assistants.

> Each component has an AGENTS.md file with specific guidelines (e.g., backend-springboot/AGENTS.md, fontend-vue/AGENTS.md).

# Quick Start

When working on this project, Gemini automatically loads relevant skills based on context.

## Available Skills

Use these skills for detailed patterns on-demand:

### Generic Skills

| Skill | Description | URL |
|-------|-------------|-----|
| `git-workflow` | Git commit conventions and branching strategy | [SKILL.md](skills/git-workflow/SKILL.md) |

### Backend Skills (Spring Boot)

| Skill | Description | URL |
|-------|-------------|-----|
| `project-architecture` | Hexagonal Architecture and DDD rules | [SKILL.md](skills/project-architecture/SKILL.md) |
| `springboot` | Java, Lombok, Springboot guideliness | [SKILL.md](skills/springboot/SKILL.md) |
| `springboot-testing` | Testing architecture | [SKILL.md](skills/springboot-testing/SKILL.md) |
| `junit-6` | JUnit 6 testing framework | [SKILL.md](skills/junit-6/SKILL.md) |
| `mockito-2.25.0` | Mockito mocking framework | [SKILL.md](skills/mockito-2.25.0/SKILL.md) |
| `springboot-e2e-testing` | E2E Testing with TestContainers | [SKILL.md](skills/springboot-e2e-testing/SKILL.md) |

### Frontend Skills (Vue.js)

| Skill | Description | URL |
|-------|-------------|-----|
| `vue-frontend` | Vue 3 composition API, component structure, and styling | [SKILL.md](skills/vue-frontend/SKILL.md) |

## Auto-invoke Skills

When performing these actions, **ALWAYS** invoke the corresponding skill **FIRST**:

| Action | Skill |
|--------|-------|
| Creating or modifying Java/Spring Boot code | `springboot` |
| Writing Unit Tests (Service/UseCase) or Slice Tests (Controller) | `springboot-testing` |
| Writing E2E/Integration Tests (Docker/TestContainers) | `springboot-e2e-testing` |
| Writing strict JUnit 6 tests (Assertions, Lifecycle) | `junit-6` |
| Mocking dependencies (Mockito strictness) | `mockito-2.25.0` |
| Creating new modules or refactoring architecture | `project-architecture` |
| Creating or modifying Vue.js components | `vue-frontend` |
| Creating a commit or pull request | `git-workflow` |

## Project Overview

| Component | Location | Tech Stack |
|-----------|----------|------------|
| Backend | `backend-springboot/` | Java 25, Gradle (Kotlin), Spring Boot 4.0.1, Lombok, Spring Security, JUnit 6, Mockito 2.25.0, PostgreSQL |
| Frontend | `frontend-vue/` | Typescript, Bun, Vue 3.5.26, Vite, Vue Router, Pinia, Vitest, ESLint, Prettier |