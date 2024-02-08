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
public class AdminPolicyGroupTest extends BaseClass {

	@Test(groups = "PG",dependsOnGroups = "UFCC")
	public void adminPolicyGroupTest() throws InterruptedException, IOException {
		 

		test=extent.createTest("AdminPolicyGroupTest", "verify Login");
		Commons.click(test,driver, By.id("policymenugroup"));

		Commons.click(test,driver, By.xpath("//a[@href='#/pmp/resources/policygroup/view']"));
		Commons.click(test,driver, By.xpath("//button[@id='Create Policy Group']"));
		
		Commons.enter(test,driver, By.id("name"), data);
		Commons.enter(test,driver, By.id("desc"), data);
		Commons.click(test,driver, By.xpath("//button[@id='createButton']"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));
		
		
		
		
		Commons.filter(test,driver, By.id("name"), data);
		test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Edit0"));
		test.log(Status.INFO, "Click on edit");
		Commons.enter(test,driver, By.id("desc"), data + 1);
		Commons.click(test,driver, By.xpath("//button[@id='createButton']"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));

		
		

		Commons.filter(test,driver, By.id("name"), data);
		test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Deactivate0"));
		test.log(Status.INFO, "Click on deactive");
		Commons.click(test,driver, By.id("confirmpopup"));
		Commons.click(test,driver, By.id("confirmmessagepopup"));
		
	
		Commons.filter(test,driver, By.id("name"), data);
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Activate0"));
		test.log(Status.INFO, "Click on activate");
		Commons.click(test,driver, By.id("confirmpopup"));
		Commons.click(test,driver, By.id("confirmmessagepopup"));
		
		
		
	}
}
