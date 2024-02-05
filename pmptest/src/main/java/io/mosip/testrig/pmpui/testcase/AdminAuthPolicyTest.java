package io.mosip.testrig.pmpui.testcase;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.mosip.testrig.pmpui.kernel.util.ConfigManager;
import io.mosip.testrig.pmpui.utility.BaseClass;
import io.mosip.testrig.pmpui.utility.Commons;
import io.mosip.testrig.pmpui.utility.JsonUtil;
import io.mosip.testrig.pmpui.utility.RealTimeReport;

@Listeners(RealTimeReport.class)
public class AdminAuthPolicyTest extends BaseClass {

	@Test(groups = {"AP"},dependsOnGroups = "PG")
	public void adminAuthPolicyTest() throws InterruptedException, IOException{
	
		test=extent.createTest("AdminAuthPolicyTest", "verify Login");
		Commons.click(test,driver, By.id("policymenugroup"));
		test.log(Status.INFO, "clicked on policymenugroup");
		Commons.click(test,driver, By.xpath("//a[@href='#/pmp/resources/authpolicy/view']"));
		
        Commons.click(test,driver, By.xpath("//button[@id='Create Policy']"));
		Thread.sleep(3000);
		Commons.enter(test,driver, By.id("name"), "AUTH"+data);
		Commons.enter(test,driver, By.id("desc"), data);
		Thread.sleep(3000);
		Commons.dropdownbyid(test,driver, By.id("policyGroupName"),data);
		test.log(Status.INFO, "dropdown selected");
		
		
		String policyData;
		try {
			policyData = ConfigManager.getpolicyData();
			Commons.enter(test,driver, By.id("policies"), policyData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Commons.click(test,driver, By.xpath("//button[@id='createButton']"));
		Thread.sleep(3000);
	
		Commons.click(test,driver, By.id("confirmmessagepopup"));
		
		Thread.sleep(3000);
		Commons.filter(test,driver, By.id("name"), "AUTH"+data);
		Thread.sleep(3000);
		test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Edit0"));
		Commons.enter(test,driver, By.id("desc"), data+1);
		
		
		Commons.click(test,driver, By.xpath("//button[@id='createButton']"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));

		Thread.sleep(3000);
		Commons.filter(test,driver, By.id("name"), "AUTH"+data);
		Thread.sleep(3000);
		test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Activate0"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmpopup']"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));
		
		
		
		
		
	}
}
