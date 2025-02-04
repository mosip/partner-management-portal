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
public class AdminPartnerPolicyMappingTest extends BaseClass {

	@Test(groups = "PPM",dependsOnGroups = "RAC",dataProvider = "data-provider-AUTH" )
	public void adminPartnerPolicyMappingTest(String cer) throws InterruptedException, IOException {

		String dropdwnVal=cer.substring(0, cer.indexOf("_", 0));
		String orgName=cer.substring(0, cer.length()-4);
		
		test=extent.createTest("AdminPartnerPolicyMappingTest", "verify Login");
		Commons.click(test,driver, By.xpath("//a[@href='#/pmp/resources/policymapping/view']"));


		Thread.sleep(50000);
		Commons.filter(test,driver, By.id("requestDetail"),By.id("partnerName"), data,orgName);
		Thread.sleep(1000);
		test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Manage Policy0"));
		
		Commons.click(test,driver, By.id("confirmpopup"));
		
		Commons.click(test,driver, By.id("confirmmessagepopup"));
	}
}
