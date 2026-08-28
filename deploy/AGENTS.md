# AGENTS.md — `deploy/`

> Shell installers that wrap the Helm charts for cluster-side deployment.
> Parent guide: [repo root `AGENTS.md`](../AGENTS.md).
> Related: [`helm/AGENTS.md`](../helm/AGENTS.md) (charts these scripts install), [`pmp-ui-v2/AGENTS.md`](../pmp-ui-v2/AGENTS.md), [`uitest-pmp-v2/AGENTS.md`](../uitest-pmp-v2/AGENTS.md).

---

## 1. Purpose

Cluster install/delete/restart scripts for the two deployable units in this repo: the `pmp-ui-v2` portal and the `uitest-pmp-v2` automation rig (as a scheduled cronjob via the `uitestrig` chart). Both scripts assume `helm repo` already includes the `mosip` chart repo and `kubectl` is pointed at the target cluster (or a `kubeconfig` path is passed as `$1`).

---

## 2. Layout

```text
deploy/
├── copy_cm_func.sh          # shared helper: copies a configmap/secret between namespaces
├── pmp-ui-v2/
│   ├── install.sh            # installs the pmp-ui-v2 Helm release into the `pms` namespace
│   ├── delete.sh
│   ├── restart.sh
│   └── README.md
└── uitest-pmp-v2/
    ├── install.sh             # installs the uitestrig Helm release (cronjob) into `pms`
    ├── delete.sh
    ├── values.yaml
    └── README.md
```

---

## 3. How to run

Install the UI:

```bash
cd deploy/pmp-ui-v2
./install.sh [kubeconfig]
```

`install.sh` creates namespace `pms`, labels it for Istio injection, copies `global` and `config-server-share` configmaps into it, then runs `helm -n pms install pmp-ui-v2 mosip/pmp-ui-v2` with API base URLs derived from the cluster's `global` configmap.

Install the automation rig (prompts for a daily cron hour and whether the target has a public domain + valid TLS):

```bash
cd deploy/uitest-pmp-v2
./install.sh [kubeconfig]
```

`install.sh` creates/labels namespace `pms`, copies required configmaps (`global`, `keycloak-host`, `artifactory-share`, `config-server-share`) and secrets (`keycloak-client-secrets`, `s3`, `postgres-postgresql`), then runs `helm -n pms install uitest-pmp-v2 mosip/uitestrig -f values.yaml` with a generated cron schedule.

---

## 4. Agent rules

### Do

1. Pass a `kubeconfig` path as the first argument if not targeting the current kube-context default.
2. Keep `CHART_VERSION` in each `install.sh` in sync with the chart version actually published for that release line.
3. Use `copy_cm_func.sh` for any new configmap/secret propagation instead of duplicating `kubectl` copy logic.
4. Update `deploy/uitest-pmp-v2/values.yaml` (not the install script) for uitestrig chart value overrides that should persist across runs.

### Do not

1. Do not hardcode cluster-specific hostnames — derive them from the `global` configmap as the existing scripts do.
2. Do not install `uitest-pmp-v2` (a scheduled test rig) against a namespace/cluster that lacks the `pms-partner`/`pms-policy` services it exercises.
3. Do not commit real Keycloak/S3/Postgres secret values anywhere under this folder.
