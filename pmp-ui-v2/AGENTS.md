# AGENTS.md — `pmp-ui-v2/`

> React front-end for MOSIP's Partner Management Portal (PMS UI).
> Parent guide: [repo root `AGENTS.md`](../AGENTS.md).
> Related: [`uitest-pmp-v2/AGENTS.md`](../uitest-pmp-v2/AGENTS.md) (automation that drives this app), [`deploy/AGENTS.md`](../deploy/AGENTS.md), [`helm/AGENTS.md`](../helm/AGENTS.md).

---

## 1. Purpose

Single-page app (Create React App, `package.json` name `partner-management-ui`) that partners, policy managers, and admins use to onboard partners, manage OIDC clients, and create/approve/publish policies. It talks to the `partner-management-services` backend over REST (no server-side logic lives here).

---

## 2. Layout

```text
pmp-ui-v2/
├── public/            # static assets, env-config.js (runtime config), i18n/
├── src/
│   ├── AppRoutes.js    # top-level route definitions
│   ├── auth/           # authentication/session handling
│   ├── nav/            # navigation/side-menu components
│   ├── pages/           # feature pages: admin/, common/, dashboard/, partner/
│   ├── services/        # axios API clients calling the PMS backend
│   ├── store.js, notificationsSlice.js  # Redux store and slices
│   ├── i18n.js          # internationalization setup
│   ├── utils/, svg/     # shared helpers and static assets
├── nginx/               # nginx config used by the production Docker image
├── Dockerfile
├── configure_start.sh   # writes runtime env into env-config.js at container start
├── package.json
└── tailwind.config.js
```

---

## 3. How to run

```bash
cd pmp-ui-v2
npm install
npm start            # dev server, http://localhost:3000
npm run build         # production build -> pmp-ui-v2/build
npm run test:ci       # Jest unit tests (react-scripts test), non-interactive
```

Build a Docker image:

```bash
cd pmp-ui-v2
docker build -f Dockerfile .
```

CI (`.github/workflows/push-trigger.yml`) builds and publishes the `pmp-ui-v2` Docker image via `mosip/kattu/.github/workflows/docker-build.yml` on push/PR to `develop`, `release*`, `master`, and versioned branches.

---

## 4. Configuration

- `public/env-config.js` — runtime configuration (API base URLs, feature flags) read by the app at startup. `configure_start.sh` regenerates this file from environment variables when the Docker container starts — do not hardcode environment-specific values into source files.
- `.env` / `.env.development` — build-time CRA environment variables.
- Deployed via the `pmp-ui-v2` Helm chart ([`helm/AGENTS.md`](../helm/AGENTS.md)); values such as API base URLs are injected at install time (see `deploy/pmp-ui-v2/install.sh`).

---

## 5. Agent rules

### Do

1. Put new environment-specific values in `public/env-config.js` / `configure_start.sh`, never hardcoded in `src/`.
2. Keep API calls inside `src/services/` — components should not call `axios` directly.
3. Group new feature pages under the existing `src/pages/<domain>/` convention (`admin/`, `common/`, `dashboard/`, `partner/`).
4. Run `npm run test:ci` before submitting UI changes.
5. When changing a page/flow that `uitest-pmp-v2` automates (partner creation, policy publish, OIDC client, device/SBI/FTM pages), check whether the corresponding `Page` object in [`uitest-pmp-v2/AGENTS.md`](../uitest-pmp-v2/AGENTS.md) needs updating too — element locators break silently otherwise.

### Do not

1. Do not commit `node_modules/`, `build/`, or `package-lock.json` changes that aren't required by the change (large diff noise).
2. Do not embed backend business logic here — validation and persistence belong in `partner-management-services`.
3. Do not bypass `src/services/` to call the backend directly from a page component.
