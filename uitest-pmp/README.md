# PMP Automation

This folder contains the code for automated UI testing of the Partner Management Portal. 

Since release 1.2.2.0, this is now deprecated. Instead refer to 

> **Latest** PMS Revamp Portal built in React JS is avaialble in the folder [pmp-revamp-ui](https://github.com/mosip/partner-management-portal/tree/release-1.2.2.x/pmp-revamp-ui)

## Overview
Selenium webdriver based Admin Portal Automation covers CRUD(create, read, update and delete) operation performed via UI with Chrome driver

## Build
1. Build jar `mvn clean install`
2. Place jar in one folder along with src/main/resources files and folder
3. Run jar with vm args- ``` java  -Dpath=https://env.mosip.net/ -Duserid=user -Dpassword=pwd -jar nameofAdminJar.jar```

## Configurations
1. Update below keys from `Config.properties`
* langcode:eng -- Admin login page language selection description placed in `TestData.json`


2. Update below keys from `TestData.json`
* setExcludedGroups:"" -- To run all the scenario mentioned below
* setExcludedGroups:"BL,CT" -- To exclude testcases execution based on below tags
* To run in hardless mode "hedless":"yes"
* To run it in regular "hedless": "no"
3. Chrome driver place under working directory inside folder name chromedriver

## Below tags with scenarios
*
"1AdminAuthPolicyTest.class": "groups = AP, dependsOnGroups = PG , 
"2AdminDataSharePolicyTest.class":"groups = DSP, dependsOnGroups = AP, 
"3AdminDeviceDetailsTest.class":"groups = DD, dependsOnGroups = SD,AP 
"4AdminFtmDetailsTest.class":"groups = FD, dependsOnGroups = RFTM ",
"5AdminPartnerPolicyMappingTest.class":"groups = PPM, dependsOnGroups = RAC ",
"6AdminPolicyGroupTest.class":"groups = PG, dependsOnGroups = UFCC ",
"7AdminSbiDetailsTest.class":"groups = SD, dependsOnGroups = RSD ",
"8AdminUploadCaCertTest.class":"groups = UFCC, dependsOnGroups =  ",
"9PartnerLoginAuthCredTest.class":"groups = PLAC, dependsOnGroups = PPM ",
"10PartnerRegisterAuthCredTest.class":"groups = RAC, dependsOnGroups = UFCC,DSP ",
"11PartnerRegisterFTMTest.class":"groups = RFTM, dependsOnGroups = UFCC ",
"12PartnerRegisterSbiDeviceTest.class":"groups = RSD, dependsOnGroups = UFCC,DSP ",
## Execution result and logs
1. Verify the failure in the logs file `\logs\AutomationLogs.log`
2. Execution results present under test-output folder file `emailable-report.html`
3. for extent reports find it in `partner-management-portal\pmptest\Reports`
## License
This project is licensed under the terms of [Mozilla Public License 2.0](../LICENSE).

