# PMP Automation

## Overview
**PMP Automation** is a Selenium WebDriver + TestNG based automation framework for the **Partner Management Portal (PMP)**.  
It automates flows such as partner/policy creation, edit, approve, publish, and deactivate — reducing manual effort and ensuring consistency.

---

## Tech Stack
- Java 21+
- Selenium WebDriver
- TestNG
- Maven (build management)
- Log4j (logging)
- TestNg Reports (HTML reports)
- ChromeDriver

---

## Build & Run
1. Build jar:  
   ```bash
   mvn clean install
   ```
2. Place the generated **jar** with `src/main/resources` in one folder
3. Run jar with VM args:  
   ```bash
   java -jar pmp-automation.jar -Dpath.config=\src\main\resources -Denv.user=<username>
   ```
4. Ensure `chromedriver` is available inside a folder named **chromedriver**

---

## Configurations

### Config.properties
- `langcode:eng` → Admin login page language (ENG/HIN/FRA etc.)

### kernel-properties
- Update client secrets and DB passwords
- Add required partner credentials and roles
- Scenario execution:
  - `pmpscenariosToExecute:""` → run all scenarios
  - `pmpscenariosToExecute:"BL,CT"` → exclude BL and CT tagged test cases
- Run mode:
  - `"headless":"yes"` → run in headless mode
  - `"headless":"no"` → run with browser UI

### TestNG
- Run specific or all scenarios by updating `testng.xml`

---

## Execution Results & Logs
- Logs: `\logs\AutomationLogs.log`
- TestNG report: `\test-output\emailable-report.html`
- Extent Report: `\pmptest\Reports`

---

## License
This project is licensed under the terms of [Mozilla Public License 2.0](../LICENSE).
