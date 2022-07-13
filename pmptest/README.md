# Admin Automation

## Overview
Selenium webdriver based Admin Portal Automation covers CRUD(create, read, update and delete) operation performed via UI with Chrome driver

## Build
1. Build jar `mvn clean install`
2. Place jar in one folder along with src/main/resources files and folder
3. Run jar with vm args- ``` java  -Dpath=https://env.mosip.net/ -Duserid=user -Dpassword=pwd -jar nameofAdminJar.jar```

## Configurations
1. Update below keys from `Config.properties`
* langcode:eng -- Admin login page language selection description placed in `TestData.json`
* bulkwait:10000 -- Bulk upload wait

2. Update below keys from `TestData.json`
* setExcludedGroups:"" -- To run all the scenario mentioned below
* setExcludedGroups:"BL,CT" -- To exclude testcases execution based on below tags

3. Chrome driver place under working directory inside folder name chromedriver

## Below tags with scenarios
* blocklistedwordsCRUD: BL
* bulkUploadCRUD: BU
* centerCRUD: CTR
* centerTypeCRUD: CT
* deviceSpecCRUD: DS
* deviceCRUD: D
* deviceTypesCRUD: DT
* documentCategoriesCRUD: DOC
* documentTypesCRUD: DOCT
* dynamicFieldCRUD: DF
* holidaysCRUD: H
* machineSpecCRUD: MS
* machineCRUD: M
* machineTypesCRUD: MT
* templateCRUD: T

## Execution result and logs
1. Verify the failure in the logs file `\logs\AutomationLogs.log`
1. Execution results present under test-output folder file `emailable-report.html`

## License
This project is licensed under the terms of [Mozilla Public License 2.0](../LICENSE).

