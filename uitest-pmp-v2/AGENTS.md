# AGENTS.md — `uitest-pmp-v2/`

> Selenium WebDriver + TestNG UI automation suite for the Partner Management Portal.
> Parent guide: [repo root `AGENTS.md`](../AGENTS.md).
> Related: [`pmp-ui-v2/AGENTS.md`](../pmp-ui-v2/AGENTS.md) (the app this suite drives), [`deploy/AGENTS.md`](../deploy/AGENTS.md).
> Deep framework/config reference: [`README.md`](README.md) in this folder — prefer it over re-deriving test-rig conventions here.

---

## 1. Purpose

End-to-end browser tests that exercise the deployed `pmp-ui-v2` portal (partner creation/modification, policy creation/approval/publish, OIDC client, device/SBI/FTM management, MISP flows) against a real PMP + `partner-management-services` + Keycloak environment. It is not a unit/component test suite and requires environment credentials to run.

---

## 2. Layout

```text
uitest-pmp-v2/
├── src/main/java/io/mosip/testrig/pmpuiv2/
│   ├── authentication/fw/util/  # RestClient
│   ├── dbaccess/                 # DBManager (Postgres access for verification)
│   ├── driver/                   # DriverManager (WebDriver/ChromeDriver setup)
│   ├── fw/util/                  # AdminTestUtil, PmpTestUtil
│   ├── kernel/                   # ApplicationLibrary, CommonLibrary, ConfigManager,
│   │                              # KernelAuthentication, KeycloakUserManager, S3Adapter
│   ├── pages/                    # Page objects, e.g. LoginPage, PartnerAdminPage,
│   │                              # OidcClientPage, PoliciesPage, DeviceProviderPage
│   ├── testcase/                  # TestNG test classes, e.g. AuthPartnerCreation,
│   │                              # DatasharePolicyTest, FtmPartnerCreation
│   └── utility/                   # TestRunner (main class), BaseClass, LogUtil, Screenshot
├── src/main/resources/
│   ├── config/                    # config.properties, kernel-properties
│   ├── testngFile/                # testng.xml suite definitions
│   ├── auth_cert/, ca_cert/, pmp_uiv2_cert/  # certs used by test flows
│   ├── log4j.properties, logback.xml
│   └── Screenshots/                # failure screenshots written at runtime
├── Dockerfile
├── entrypoint.sh                   # container entrypoint: runs the shaded jar
└── pom.xml
```

Main class: `io.mosip.testrig.pmpuiv2.utility.TestRunner`.

---

## 3. How to run

Build (produces a shaded jar via `maven-shade-plugin`, name pattern `pmpuiv2-<version>-jar-with-dependencies.jar`, see `pom.xml` `<fileName>`):

```bash
cd uitest-pmp-v2
mvn clean install -Dgpg.skip=true -Dmaven.gitcommitid.skip=true
```

Run the built jar — **`-D` system properties must precede `-jar`**, otherwise `TestRunner` will not see them:

```bash
cd uitest-pmp-v2/target
java -Dpath.config=src/main/resources -Denv.user=<username> -jar pmpuiv2-*-jar-with-dependencies.jar
```

Place the jar and `src/main/resources` folder in the same directory, and a `chromedriver` binary in a `chromedriver/` folder alongside them before running.

Container entrypoint (`entrypoint.sh`) runs the same jar without `-D` overrides (config is baked into the image):

```bash
java -jar pmpuiv2-*-jar-with-dependencies.jar
```

Run/debug from Eclipse: import as an existing Maven project, set main class `io.mosip.testrig.pmpuiv2.utility.TestRunner`, VM arguments `-Dpath.config=src/main/resources -Denv.user=<username>`.

---

## 4. Configuration

- `src/main/resources/config/config.properties` — scenario selection (`pmpscenariosToExecute`), `headless` mode, `langcode`, portal/API URLs (`pmpUiv2Url`, `apiInternalEndPoint`, `apiEnvUser`).
- `src/main/resources/config/kernel-properties` (and similarly named files) — client secrets, DB passwords, partner credentials. **Never commit real credentials here.**
- `src/main/resources/testngFile/` — TestNG suite XML files controlling which test classes run.

---

## 5. Agent rules

### Do

1. Keep the JVM `-D` property flags (`-Dpath.config`, `-Denv.user`, etc.) **before** `-jar` in every run command — reversing the order silently drops them and `TestRunner` falls back to defaults.
2. Add new page interactions to a `pages/*Page.java` object rather than inlining Selenium locators in a test class.
3. When `pmp-ui-v2` changes a page's DOM/flow, update the matching `pages/` class and any `testcase/` that exercises it.
4. Keep `pmpuiv2-*-jar-with-dependencies.jar` naming (`pom.xml` `<fileName>` property) in sync with `entrypoint.sh` and `Dockerfile` if the artifact name ever changes.
5. Use `README.md` in this folder for framework/config detail beyond this file (config properties, TestNG structure, troubleshooting).

### Do not

1. Do not commit real values in `kernel-properties` or any credentials file.
2. Do not treat this suite as a substitute for `pmp-ui-v2` unit tests — it requires a live deployed environment.
3. Do not put `-D` system properties after `-jar` in run commands, Dockerfiles, or CI scripts.
