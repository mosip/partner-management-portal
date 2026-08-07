# AGENTS.md

## Repository Overview

This repository contains the reference front-end UI for MOSIP's **Partner Management (PMS)** module — the portal partners, policy managers, and admins use to onboard partners, manage OIDC clients, and create/approve/publish policies. It is the UI counterpart to the [partner-management-services](https://github.com/mosip/partner-management-services) backend, which exposes the REST APIs this portal consumes.

**On the `develop` branch, `pmp-ui-v2` is the active, tracked UI application.** There is no other UI module tracked in git on `develop`; if a `pmp-revamp-ui` or `uitest-pmp` directory is present in a local working copy, treat it as leftover/untracked local state (e.g. from another branch) and not part of the current codebase — verify with `git ls-tree develop --name-only` before relying on it.

Top-level layout (as tracked on `develop`):

- `pmp-ui-v2` — the React front-end application
- `uitest-pmp-v2` — Selenium + TestNG UI automation test suite for the portal
- `deploy` — install/delete/restart shell scripts for Kubernetes deployment
- `helm` — Helm chart (`helm/pmp-ui-v2`) for installing the UI module
- `.github/workflows` — CI: `push-trigger.yml`, `chart-lint-publish.yml`, `codeql.yml` (CodeQL security analysis on push/PR to `develop`), `tag.yml`

## Technology Stack

**`pmp-ui-v2`** (`package.json` name: `partner-management-ui`):
- React 18 (bootstrapped with Create React App, `react-scripts` 5.x)
- Redux Toolkit + React Redux for state management
- React Router DOM 6 for routing
- Tailwind CSS for styling
- i18next / react-i18next for internationalization
- axios for HTTP calls
- jose / pkijs / asn1js for client-side crypto/JWT handling
- Testing: `@testing-library/react`, `@testing-library/jest-dom`, `@testing-library/user-event` (Jest via `react-scripts test`)

**`uitest-pmp-v2`**:
- Java 21+, Maven
- Selenium WebDriver + TestNG for browser automation
- ExtentReports for visual/HTML test reporting, Log4j for logging
- Main class: `io.mosip.testrig.pmpuiv2.utility.TestRunner`

## Build & Test Commands

Build and run the UI locally:

```bash
cd pmp-ui-v2
npm install
npm start          # dev server, http://localhost:3000
npm run build       # production build -> pmp-ui-v2/build
```

Run UI unit tests:

```bash
cd pmp-ui-v2
npm run test:ci
```

Build a Docker image for the UI:

```bash
cd pmp-ui-v2
docker build -f Dockerfile .
```

Build and run the Selenium/TestNG UI automation suite (see `uitest-pmp-v2/README.md` for full detail, including config properties and IDE setup):

```bash
cd uitest-pmp-v2
mvn clean install -Dgpg.skip=true -Dmaven.gitcommitid.skip=true
java -Dpath.config=src/main/resources -Denv.user=<username> -jar target/pmp-automation.jar
```

## Configuration

- `pmp-ui-v2/public/env-config.js` and `.env` / `.env.development` hold runtime/environment configuration for the UI (API base URLs, feature flags, etc.). Do not hardcode environment-specific values in source files — read them from this config layer.
- `uitest-pmp-v2` test configuration lives in `src/main/resources/config.properties` (scenario selection, headless mode, endpoints) and a separate `kernel-properties` file for secrets — **never commit real credentials in `kernel-properties`**.

## Project Structure Notes (`pmp-ui-v2/src`)

- `AppRoutes.js` — top-level route definitions
- `auth/` — authentication/session handling
- `nav/` — navigation/side-menu components
- `pages/` — feature pages, organized by domain: `admin/`, `common/`, `dashboard/`, `partner/`
- `services/` — API client modules (axios-based calls to the PMS backend)
- `store.js`, `notificationsSlice.js` — Redux store and slices
- `i18n.js` — internationalization setup
- `utils/`, `svg/` — shared helpers and static assets

## Development Workflow

- Default integration branch is `develop`. Branch from `develop` for new work.
- CI (GitHub Actions) runs a Maven/npm package build on push (`push-trigger.yml`), lints/publishes Helm charts (`chart-lint-publish.yml`), and runs CodeQL analysis on every push/PR to `develop` plus a weekly scheduled scan (`codeql.yml`) — keep new dependencies and code CodeQL-clean.
- Frontend and UI-test changes are independent build units (`pmp-ui-v2` via npm, `uitest-pmp-v2` via Maven); there is no shared reactor between them.

## Pull Request Guidelines

- Keep UI changes and UI-automation-test changes in separate, scoped commits/PRs where practical.
- Do not commit `node_modules/`, `build/`, Maven `target/`, generated `_rendered/`, Helm `Chart.lock`, or any file containing real credentials (`kernel-properties`, environment-specific config with secrets).
- License: this project is licensed under MPL 2.0 (`LICENSE`); do not introduce dependencies incompatible with that license without flagging it.

## Repository-Specific Considerations

- `uitest-pmp-v2/README.md` already documents the automation framework in depth (config properties, TestNG suite structure, troubleshooting) — prefer that file over re-deriving test-rig conventions here.
- The UI automation suite drives a real browser against a deployed PMP + PMS + Keycloak environment; it is not a component/unit test suite and requires environment credentials to run.
- This portal has no backend logic of its own — business rules, validation, and persistence live in `partner-management-services`. When behavior looks wrong, check whether the fix belongs in that repository instead.
