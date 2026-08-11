# AGENTS.md — `helm/`

> Helm chart for deploying the `pmp-ui-v2` UI to Kubernetes.
> Parent guide: [repo root `AGENTS.md`](../AGENTS.md).
> Related: [`deploy/AGENTS.md`](../deploy/AGENTS.md) (install scripts that use this chart), [`pmp-ui-v2/AGENTS.md`](../pmp-ui-v2/AGENTS.md).

---

## 1. Purpose

Single Helm chart (`pmp-ui-v2`) packaging the deployment, service, gateway/virtualservice (Istio), configmap, and service monitor for the `pmp-ui-v2` React app. There is no chart for `uitest-pmp-v2` in this repo — that automation rig is installed from the shared `mosip/uitestrig` chart (see `deploy/uitest-pmp-v2/install.sh`).

---

## 2. Layout

```text
helm/pmp-ui-v2/
├── Chart.yaml              # name: pmp-ui-v2, depends on bitnami "common" chart
├── values.yaml              # default chart values
├── templates/
│   ├── deployment.yaml
│   ├── service.yaml
│   ├── configmap.yaml       # env-config.js values injected into the container
│   ├── gateway.yaml
│   ├── virtualservice.yaml  # Istio routing
│   ├── service-account.yaml
│   ├── servicemonitor.yaml
│   ├── extra-list.yaml
│   ├── _helpers.tpl
│   └── NOTES.txt
├── README.md
└── .gitignore
```

CI lints and publishes this chart via `.github/workflows/chart-lint-publish.yml`.

---

## 3. How to run

Lint locally before pushing chart changes:

```bash
cd helm
helm lint pmp-ui-v2
```

Install/upgrade (normally invoked through `deploy/pmp-ui-v2/install.sh`, not run bare):

```bash
helm -n pms install pmp-ui-v2 mosip/pmp-ui-v2 \
  --set pmp_uiv2.react_app_partner_manager_api_base_url="https://<api-internal-host>/v1/partnermanager" \
  --set pmp_uiv2.react_app_policy_manager_api_base_url="https://<api-internal-host>/v1/policymanager" \
  --version <chart-version>
```

---

## 4. Agent rules

### Do

1. Bump `version` in `Chart.yaml` for any chart change that should ship as a new release.
2. Add new runtime env values through `templates/configmap.yaml` + `values.yaml`, matching how `pmp-ui-v2/public/env-config.js` expects them (see [`pmp-ui-v2/AGENTS.md`](../pmp-ui-v2/AGENTS.md)).
3. Keep Istio resources (`gateway.yaml`, `virtualservice.yaml`) consistent with the namespace/host conventions used in `deploy/pmp-ui-v2/install.sh`.
4. Run `helm lint` before submitting chart changes — CI will fail `chart-lint-publish.yml` otherwise.

### Do not

1. Do not rename the chart or its release name (`pmp-ui-v2`) without updating `deploy/pmp-ui-v2/install.sh` and any downstream references.
2. Do not hardcode environment-specific hostnames in `values.yaml` defaults — they should be passed via `--set` at install time, as the deploy scripts do.
