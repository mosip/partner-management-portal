package io.mosip.testrig.pmpui.testcase;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpui.utility.BaseClass;
import io.mosip.testrig.pmpui.utility.Commons;
import io.mosip.testrig.pmpui.utility.RealTimeReport;

@Listeners(RealTimeReport.class)
public class AdminFtmDetailsTest extends BaseClass {
	private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(AdminFtmDetailsTest.class);
	
	@Test(groups = {"FD"},dataProvider = "data-provider-FTM",dependsOnGroups = "RFTM")
	public void adminFtmDetailsTest(String cer) throws InterruptedException, IOException {
		
		test=extent.createTest("AdminFtmDetailsTest", "verify Login");
		Commons.click(test,driver, By.xpath("//a[@href='#/pmp/resources/ftmdetails/view']"));

	
		String dropdwnVal=cer.substring(0, cer.indexOf("_", 0));
		String orgName=cer.substring(0, cer.length()-4);
		
	Thread.sleep(3000);
		Commons.filter(test,driver, By.id("make"),By.id("partnerOrganizationName"), data,orgName);
		Thread.sleep(3000);
	//	test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Edit0"));
		Commons.enter(test,driver, By.id("model"), data + 1);
		Commons.click(test,driver, By.xpath("//button[@id='createButton']"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));

		Thread.sleep(3000);
		Commons.filter(test,driver, By.id("make"),By.id("partnerOrganizationName"), data,orgName);
		Thread.sleep(3000);
	//	test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		
		Commons.click(test,driver, By.id("Upload Certificate0"));
	//	test.log(Status.INFO, "Upload certificate");
		
		Commons.uploadPartnerCert(driver,By.id("partnerDomain"),dropdwnVal,"//ftm_cert//",cer);

		Thread.sleep(3000);
		Commons.filter(test,driver, By.id("make"),By.id("partnerOrganizationName"), data,orgName);
		Thread.sleep(3000);
		Commons.click(test,driver, By.id("ellipsis-button0"));

		Commons.click(test,driver, By.id("View Certificate0"));
	//	test.log(Status.INFO, "view certificate");
		String cert=Commons.getText(driver,By.xpath("//p"));
		logger.info(cert);
		Commons.click(test,driver, By.id("confirmmessagepopup"));		
	
		Thread.sleep(3000);
		Commons.filter(test,driver, By.id("make"),By.id("partnerOrganizationName"), data,orgName);
		Thread.sleep(3000);
	//	test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Activate0"));
	//	test.log(Status.INFO, "Click on Activate");
		Commons.click(test,driver, By.xpath("//button[@id='confirmpopup']"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));
		
		Thread.sleep(3000);
		Commons.filter(test,driver, By.id("make"),By.id("partnerOrganizationName"), data,orgName);
		Thread.sleep(3000);
	//	test.log(Status.INFO, "Click on filter");
		Commons.click(test,driver, By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Deactivate0"));
	//	test.log(Status.INFO, "Click on deactivate");
		Commons.click(test,driver, By.xpath("//button[@id='confirmpopup']"));
		Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));
		

	}
}
