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
public class AdminDataSharePolicyTest extends BaseClass {

	@Test(groups = "DSP",dependsOnGroups = "AP")
	public void adminDataSharePolicyTest() throws InterruptedException, IOException {
		
		test=extent.createTest("AdminDataSharePolicyTest", "verify Login");
		Commons.click(test,driver, By.id("policymenugroup"));

		Commons.click(test,driver, By.xpath("//a[@href='#/pmp/resources/datasharepolicy/view']"));
		
       Commons.click(test,driver, By.xpath("//button[@id='Create Policy']"));
       
		
		Commons.enter(test,driver, By.id("name"), "DS"+data);
		Commons.enter(test,driver, By.id("desc"), data);
		Thread.sleep(3000);
		Commons.dropdown(test,driver, By.xpath("//mat-select[@id='policyGroupName']"),data);
		Thread.sleep(3000);
		test.log(Status.INFO, "Dropdoen selected");
		String dataSharepolicyData;
		try {
			dataSharepolicyData =ConfigManager.getdataSharepolicyData();
			Commons.enter(test,driver, By.id("policies"), dataSharepolicyData);
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread.sleep(3000);
		Commons.click(test,driver, By.xpath("//button[@id='createButton']"));
	
		Commons.click(test,driver, By.id("confirmmessagepopup"));
		
		Thread.sleep(3000);
		Commons.filter(test,driver, By.id("desc"), data);
		Thread.sleep(3000);
		test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Edit0"));
		Commons.enter(test,driver, By.id("desc"), data+1);
		
		
		Commons.click(test,driver, By.xpath("//button[@id='createButton']"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));
		Thread.sleep(3000);
		Commons.filter(test,driver, By.id("desc"), data+1);
		test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Activate0"));
       Thread.sleep(3000);
		Commons.click(test,driver, By.xpath("//button[@id='confirmpopup']"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));
		
		
		


		
	}
}
