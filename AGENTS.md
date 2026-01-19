# Repository Guidelines

## Project Overview

| Component | Location | Tech Stack |
|-----------|----------|------------|
| Backend | backend-springboot | Java 25, Gradle, Spring Boot 4.0.1, Lombok, Spring Security, PostgreSQL |

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
| `java-springboot` | Java best practices, Lombok, Testing | [SKILL.md](skills/java-springboot/SKILL.md) |

### Frontend Skills (Vue.js)

| Skill | Description | URL |
|-------|-------------|-----|
| `vue-frontend` | Vue 3 composition API, component structure, and styling | [SKILL.md](skills/vue-frontend/SKILL.md) |

## Auto-invoke Skills

When performing these actions, **ALWAYS** invoke the corresponding skill **FIRST**:

| Action | Skill |
|--------|-------|
| Creating or modifying Java/Spring Boot code | `java-springboot` |
| Creating new modules or refactoring | `project-architecture` |
| Creating or modifying Vue.js components | `vue-frontend` |
| Creating a commit or pull request | `git-workflow` |
| Writing Unit Tests (Java) | `java-springboot` |

