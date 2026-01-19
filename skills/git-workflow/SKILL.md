---
name: git-workflow
description: Standard Git workflow for the project, including commit messages and branching.
---

# Git Workflow

## Commit Messages
Follow the [Conventional Commits](https://www.conventionalcommits.org/) specification.

**Format**: `<type>(<scope>): <description>`

**Types**:
- `feat`: A new feature
- `fix`: A bug fix
- `docs`: Documentation only changes
- `style`: Changes that do not affect the meaning of the code (white-space, formatting, etc)
- `refactor`: A code change that neither fixes a bug nor adds a feature
- `perf`: A code change that improves performance
- `test`: Adding missing tests or correcting existing tests
- `chore`: Changes to the build process or auxiliary tools and libraries

**Examples**:
- `feat(auth): add login endpoint`
- `fix(user): handle null pointer in user service`
- `style(ui): fix button alignment`

## Branching Strategy
- `main` / `master`:  Production-ready code.
- `develop`: Integration branch (optional, depending on flow).
- `feature/<name>`: For new features.
- `bugfix/<name>`: For bug fixes.
- `hotfix/<name>`: For urgent production fixes.

## Working on a Task
1.  **Pull latest changes**: `git pull origin main`
2.  **Create a branch**: `git checkout -b feature/my-new-feature`
3.  **Make changes**: Edit files.
4.  **Verify**: Run tests.
5.  **Commit**: `git commit -m "feat(scope): descriptions"`
6.  **Push**: `git push origin feature/my-new-feature`
