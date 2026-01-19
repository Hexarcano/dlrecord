---
name: vue-frontend
description: Best practices for Vue 3 development, including Composition API and component structure.
---

# Vue Frontend Skills

## Component Style
- **Composition API**: ALWAYS use `<script setup lang="ts">` (or `lang="js"` if not using TypeScript).
- **Single File Components**: Keep Template, Script, and Style in the same `.vue` file.

## Script Setup Structure
Order the code inside `<script setup>` as follows:
1.  Imports (Vue, components, utilities).
2.  Props & Emits definitions (`defineProps`, `defineEmits`).
3.  Reactive state (`ref`, `reactive`).
4.  Computed properties (`computed`).
5.  Life cycle hooks (`onMounted`, etc.).
6.  Functions / Event Handlers.

## Naming
- **Components**: PascalCase (e.g., `UserProfile.vue`).
- **Files**: PascalCase matching component name.
- **Props**: camelCase in definition, kebab-case in templates (e.g., `user-id="123"`).

## State Management
- Use **Pinia** for global state management (avoid Vuex in new projects).
- Keep component-local state in `ref` or `reactive`.

## CSS / Styling
- Use **Scoped CSS**: `<style scoped>`.
- Avoid global styles unless for resets or usage in `App.vue`.
