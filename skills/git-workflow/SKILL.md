---
name: git-workflow
description: >
  Standard Git workflow for the project.
  Trigger: When the user asks to save changes, commit code, or manage branches.
metadata:
  author: hexarcano
  version: "1.0"
  scope: [git, workflow]
  auto_invoke:
    - "Creating a commit"
    - "Checking file status"
    - "Pushing changes"
allowed-tools: Read, Edit, Write, Glob, Grep, Bash, WebFetch, WebSearch, Task
---

# Git Workflow & Commit Standards

## 1. The Smart Commit Process (Mandatory)

Before generating any commit message, you **MUST** follow these steps to ensure accuracy:

### Step 1: Check Status

Run `git status` to see which files are modified.
*   **Goal**: Identify if we are committing just one logical change or mixing multiple things.

### Step 2: Analyze Changes

Run `git diff` (for unstaged) or `git diff --staged` (for staged).
*   **Goal**: Understand exactly *what* changed logically.
*   *Question*: Did you rename a variable? Add a feature? Fix a bug?

### Step 3: Formulate Message

Based on the diff, construct the message using **Conventional Commits**.

## 2. Conventional Commits Specification

Follow the [Conventional Commits](https://www.conventionalcommits.org/) specification.

**Format**: `<type>(<scope>): <description>`

| Type | Meaning | Example |
| :--- | :--- | :--- |
| `feat` | New feature | `feat(auth): add login endpoint` |
| `fix` | Bug fix | `fix(user): prevent NPE in service` |
| `refactor` | Code change that isn't a fix or feature | `refactor(core): simplify date parsing` |
| `test` | Adding/missing tests | `test(auth): add unit tests for login` |
| `docs` | Documentation only | `docs(readme): update install guide` |
| `style` | Formatting, missing semi-colons, etc. | `style(lint): fix indentation` |
| `chore` | Build tasks, package manager configs | `chore(deps): upgrade dependencies` |

**Rules**:
*   **Scope**: The module or component affected (e.g., `api`, `ui`, `database`, `auth`).
*   **Description**: Imperative mood ("add" not "added"), lowercase, no period at end.

## 3. Branching Strategy

*   `main` / `master`:  Production-ready code.
*   `feature/<name>`: For new features (e.g., `feature/user-profile`).
*   `fix/<name>`: For bug fixes (e.g., `fix/login-error`).

## 4. Standard Workflow

1.  **Pull latest**: `git pull origin main`
2.  **Create Branch**: `git checkout -b feature/my-feature`
3.  **Work**: Edit files + Run Project Guidelines (Linting/Formatting).
4.  **Verify**: Run Tests/Build.
5.  **Commit**: (See Step 1-3 above).
6.  **Push**: `git push origin feature/my-feature`
