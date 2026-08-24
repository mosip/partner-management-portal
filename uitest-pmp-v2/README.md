# 🧩 PMP Automation

## 📘 Overview
**PMP Automation** is a **Selenium WebDriver + TestNG** based automation framework developed for the **Partner Management Portal (PMP)**.

It automates key business workflows such as:
- Partner creation and modification
- Policy creation and approval
- Publish and deactivation of partners

This framework reduces manual effort, increases consistency, and accelerates regression cycles.

---

## 🚀 Tech Stack

| Component | Description |
|------------|-------------|
| **Java 21+** | Core programming language |
| **Selenium WebDriver** | Browser automation |
| **TestNG** | Test execution and reporting |
| **Maven** | Build and dependency management |
| **Log4j** | Logging framework |
| **TestNG Reports** | HTML-based test reports |
| **ExtentReports** | Detailed execution and visual reporting |
| **ChromeDriver** | Local browser driver for Chrome |

---

## ⚙️ Project Structure

```
pmp-automation/
├── src/
│   ├── main/
│   │   ├── java/              # Core framework and test logic
│   │   └── resources/         # Config files and test data
│   └── test/
│       └── java/              # Test classes and runners
├── logs/                      # Automation log files
├── pmptest/Reports/           # ExtentReports output
├── test-output/               # TestNG report output
├── pom.xml                    # Maven project file
└── README.md                  # Project documentation
```

---

## 🧱 Build & Run Instructions

### 🔗 Access Test Automation Code

#### Option 1: From Browser
1. Clone or download the repository as a ZIP file from GitHub.
2. Unzip the contents to your local machine.
3. Open a terminal (Linux) or command prompt (Windows) and continue with the steps below.

#### Option 2: From Git Bash
```bash
git clone https://github.com/mosip/partner-management-portal
```

---

### 🏗️ Build Test Automation Code

```bash
cd uitest-pmp-v2
mvn clean install -Dgpg.skip=true -Dmaven.gitcommitid.skip=true
```

This command downloads dependencies, compiles the code, and packages the test suite for execution.

---

### ▶️ Execute Test Automation Suite

#### Option 1: Using JAR

1. Navigate to the target directory:  
   ```bash
   cd target/
   ```
2. Prepare the folder structure:
- Place the generated JAR (pmp-automation.jar) and src/main/resources folder in the same directory.
- Ensure the chromedriver binary is placed inside a folder named chromedriver at the same level as the JAR.

3. Run the automation suite JAR:  
   ```bash
   java -jar pmp-automation.jar -Dpath.config=src/main/resources -Denv.user=<username>

   ```

---

#### Option 2: Using Eclipse IDE

##### 1️⃣ Install Eclipse
Download the latest Eclipse IDE from [Eclipse Downloads](https://www.eclipse.org/downloads/).

##### 2️⃣ Import Maven Project
- Open Eclipse → **File > Import**  
- Select **Maven > Existing Maven Projects** → **Next**  
- Browse to your `uitest-pmp-v2` directory → **Finish**

##### 3️⃣ Build the Project
- Right-click project → **Maven > Update Project**  
- Wait for dependencies to download.

##### 4️⃣ Configure Run Parameters
- Go to **Run > Run Configurations...**  
- Create a new **Java Application** configuration  
- Set:
  - **Main class:** `io.mosip.testrig.pmpuiv2.utility.TestRunner`  
  - **VM arguments:**  
    ```bash
    Dpath.config=src/main/resources -Denv.user=admin
    ```

##### 5️⃣ Run or Debug
Click **Run** to execute, or **Debug** to run in debug mode with breakpoints.

---

## 🔧 Configuration Details

### 🗂️ Kernel.properties

All properties below are read from `src/main/resources/config/Kernel.properties` (not a separate `config.properties` file).

| Property | Description |
|-----------|-------------|
| `loginlang=ara` | Language the suite runs in — see "🌐 Multi-language Support" below |
| `headless=yes/no` | Run in headless or normal browser mode |
| `pmpscenariosToExecute` | Comma-separated list of scenarios to skip; empty to run all |
| `mosip_idrepo_client_secret`, `mosip_testrig_client_secret`, `mosip_admin_client_secret`, etc. | Client secrets required for API authentication |
| `keycloak-external-url` | External Keycloak URL |
| `pmpUiv2Url` | PMP portal URL |
| `apiEnvUser` | Environment user (e.g., api-internal.qa) |
| `apiInternalEndPoint` | API base endpoint (e.g., https://api-internal.qa.mosip.net) |
| `Postgres passwords` | Database connection passwords |

**Examples:**
```properties
# Run all scenarios
pmpscenariosToExecute=""

# Run specific scenarios
pmpscenariosToExecute="BL,CT"
```

---

### 🔐 kernel-properties

Contains environment-specific and sensitive data like:
- Client secrets  
- Database passwords  
- Partner credentials  
- Role configurations  

⚠️ **Important:** Never commit this file with real credentials to version control.

---

## 🌐 Multi-language Support

The suite can run against the login page in a specific language, or run the entire suite once per language in sequence.

### How it works

`loginlang` in `kernel-properties` (or the equivalent OS environment variable, which takes priority over the file) controls this:

```properties
# Single language — the whole run uses this language
loginlang=ara

# Multiple languages — the whole suite runs once per language, in order
loginlang=eng,ara,fra
```

At the start of each test, `BaseClass` reads the current language and — if it isn't English — selects the matching option (`ara`/`fra`) on the Keycloak login page before signing in. English is the default and requires no selection.

When `loginlang` holds a comma-separated list, `TestRunner` loops the full suite once per language:
- **Sequential, not parallel.** Each language's run completes fully before the next one starts.
- **Clean state between runs.** The test database is reset after each language's run so the next language doesn't inherit data from the previous one.
- **Separate reports.** Each run's TestNG report file name includes the language it ran in, e.g. `PMPUI-...-ara-report.html`, so results from different languages don't overwrite each other.

A single value behaves exactly as before — no behavior change for existing single-language runs.

---

## 🧪 Test Execution

### 🧩 Using TestNG

You can run:
- All tests  
- Specific modules  
- Grouped tests (via `@Groups` or scenario tags)

Example `testng.xml`:  
```xml
<suite name="PMP Automation Suite">
    <test name="Partner Management Tests">
        <classes>
            <class name="tests.partner.CreatePartnerTest"/>
            <class name="tests.policy.PublishPolicyTest"/>
        </classes>
    </test>
</suite>
```

---

## 📊 Execution Results & Logs

| Output | Location | Description |
|--------|-----------|-------------|
| **Logs** | `\logs\AutomationLogs.log` | Step-by-step execution logs |
| **TestNG Report** | `\test-output\emailable-report.html` | Summary HTML report |
| **Extent Report** | `\pmptest\Reports\` | Visual detailed report with screenshots |

---

## 🧰 Troubleshooting

| Issue | Possible Fix |
|-------|---------------|
| Browser not launching | Ensure ChromeDriver path is correct and compatible with Chrome version |
| Tests not picking config | Verify `-Dpath.config` or `-Dconfig.path` is correctly set |
| Login failure | Check `loginlang` and credentials in `kernel-properties` |
| No report generated | Ensure `testng.xml` and ExtentReport listener are properly configured |

---

## 🧑‍💻 Contribution Guide

1. Fork the repository  
2. Create a feature branch  
```bash
git checkout -b feature/<your-feature>
```  
3. Commit changes  
```bash
git commit -m "Added <your-feature>"
```  
4. Push and raise a PR

---

## 🪪 License
This project is licensed under the terms of the **Mozilla Public License 2.0**.  
Refer to the `LICENSE` file for full details.

---

## 🏁 Future Enhancements
- ✅ Multi-browser execution (Edge, Firefox)  
- ✅ Dockerized test execution  
- ✅ CI/CD integration (GitHub Actions / Jenkins)  
- ✅ Parallel execution support