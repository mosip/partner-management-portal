package io.mosip.testrig.pmpui.testcase;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.mosip.testrig.pmpui.utility.BaseClass;
import io.mosip.testrig.pmpui.utility.Commons;
import io.mosip.testrig.pmpui.utility.RealTimeReport;

@Listeners(RealTimeReport.class)
public class AdminSbiDetailsTest extends BaseClass {
	private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(AdminSbiDetailsTest.class);
	@Test(groups = "SD",dataProvider = "data-provider-DEVICE-SBI",dependsOnGroups = "RSD")
	public void adminSbiDetailsTest(String cer) throws Exception {

		String dropdwnVal=cer.substring(0, cer.indexOf("_", 0));
		String orgName=cer.substring(0, cer.length()-4);
		
		test=extent.createTest("AdminSbiDetailsTest", "verify Login");
		Commons.click(test,driver, By.xpath("//a[@href='#/pmp/resources/sbidetails/view']"));
	
		//Commons.filter(test,driver, By.id("swVersion"),By.id("providerId"), data,orgName);
		Thread.sleep(3000);
		Commons.filter(test,driver, By.id("swVersion"),data);
		Thread.sleep(3000);
		test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Reject0"));
		test.log(Status.INFO, "Click on reject");

		Commons.click(test,driver, By.xpath("//button[@id='confirmpopup']"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));
		
		Thread.sleep(3000);
		Commons.filter(test,driver, By.id("swVersion"),data);
		Thread.sleep(3000);
		test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Edit0"));
		test.log(Status.INFO, "Click on edit");
		Commons.enter(test,driver, By.xpath("//input[@id='swBinaryHash']"), data+1);

		Commons.click(test,driver, By.xpath("//button[@id='createButton']"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));
		
		Thread.sleep(3000);
		Commons.filter(test,driver, By.id("swVersion"),data);
		Thread.sleep(3000);
		test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Approve0"));
		test.log(Status.INFO, "Click on approve");
		Commons.click(test,driver, By.xpath("//button[@id='confirmpopup']"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));
		
		
	
		
	}
}
