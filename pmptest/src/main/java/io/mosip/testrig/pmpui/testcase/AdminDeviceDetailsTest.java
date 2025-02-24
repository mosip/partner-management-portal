package io.mosip.testrig.pmpui.testcase;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.mosip.testrig.pmpui.utility.BaseClass;
import io.mosip.testrig.pmpui.utility.Commons;
import io.mosip.testrig.pmpui.utility.RealTimeReport;

@Listeners(RealTimeReport.class)
public class AdminDeviceDetailsTest extends BaseClass {

	private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(AdminDeviceDetailsTest.class);
	@Test(groups = "DD",dataProvider = "data-provider-DEVICE-SBI",dependsOnGroups = {"SD","AP"})
	
	public void adminDeviceDetailsTest(String cer) throws InterruptedException, IOException {

		String dropdwnVal=cer.substring(0, cer.indexOf("_", 0));
		String orgName=cer.substring(0, cer.length()-4);
		
		test=extent.createTest("AdminDeviceDetailsTest", "verify Login");
		Commons.click(test,driver, By.xpath("//a[@href='#/pmp/resources/devicedetails/view']"));
		Thread.sleep(3000);
		Commons.filter(test,driver, By.id("make"),By.id("partnerOrganizationName"), data,orgName);
		Thread.sleep(3000);
		test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Approve0"));
		//Commons.click(test,driver, By.id("Activate0"));
		
		Commons.click(test,driver, By.xpath("//button[@id='confirmpopup']"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));
		Thread.sleep(3000);
		Commons.filter(test,driver, By.id("make"),By.id("partnerOrganizationName"), data,orgName);
		Thread.sleep(3000);
		test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Edit0"));
		//Commons.enter(test,driver, By.xpath("//input[@id='model']"), data + 1);
		

		Commons.dropdown(test,driver, By.id("SBIVersion")); 
		
		Commons.click(test,driver, By.xpath("//button[@id='mapSBIVersion']"));
		Commons.click(test,driver, By.id("confirmmessagepopup"));
		
		/*
		Commons.filter(test,driver, By.id("make"), data);
		
		Commons.click(test,driver, By.id("ellipsis-button0"));
		
		Commons.click(test,driver, By.id("Upload Certificate0"));
	
		
		Commons.uploadPartnerCert(driver,By.id("partnerDomain"),dropdwnVal,"\\partner_cert\\",cer);

		Commons.filter(test,driver, By.id("make"), data);

		Commons.click(test,driver, By.id("ellipsis-button0"));

		Commons.click(test,driver, By.id("View Certificate0"));

		String cert=Commons.getText(driver,By.xpath("//p"));
		logger.info(cert);
		Commons.click(test,driver, By.id("confirmmessagepopup"));		
	*/
		Commons.click(test,driver, By.xpath("//a[@href='#/pmp/resources/devicedetails/view']"));
		Commons.filter(test,driver, By.id("make"),By.id("partnerOrganizationName"), data,orgName);
		test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Reject0"));
		test.log(Status.INFO, "Click on reject");
		Commons.click(test,driver, By.xpath("//button[@id='confirmpopup']"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));
		

	}
}
