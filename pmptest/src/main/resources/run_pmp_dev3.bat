chdir /d D:\Pmp_UI_Test_Rig



java -jar pmptest-1.2.1-SNAPSHOT-jar-with-dependencies.jar



xcopy D:\Pmp_UI_Test_Rig\test-output\emailable-report.html D:\Pmp_UI_Test_Rig\test-output\Reports\PmpUI-qa121-Report-%date:~-4,4%%date:~-7,2%%date:~-10,2%%time%.html*

