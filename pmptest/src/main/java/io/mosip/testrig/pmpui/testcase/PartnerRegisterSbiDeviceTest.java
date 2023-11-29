package io.mosip.testrig.pmpui.testcase;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.mosip.testrig.pmpui.utility.Commons;
import io.mosip.testrig.pmpui.utility.JsonUtil;
import io.mosip.testrig.pmpui.utility.RealTimeReport;
import io.mosip.testrig.pmpui.utility.RegisterBaseClass;

@Listeners(RealTimeReport.class)
public class PartnerRegisterSbiDeviceTest extends RegisterBaseClass {
	private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(PartnerRegisterSbiDeviceTest.class);
	@Test(groups = "RSD",dataProvider = "data-provider-DEVICE-SBI" , dependsOnGroups = {"UFCC","DSP"})
	public void partnerRegisterSbiDeviceTest(String cer) throws InterruptedException, AWTException{
		String datetime=Commons.getDateTime();
		String dropdwnVal=cer.substring(0, cer.indexOf("_", 0));
		String orgName=cer.substring(0, cer.length()-4);
		test=extent.createTest("PartnerRegisterSbiDeviceTest", "verify Login");
		Commons.click(test,driver, By.xpath("//a[contains(text(),'Register')]"));
		Commons.enter(test,driver, By.id("firstName"), datetime);
		Commons.enter(test,driver, By.id("lastName"), datetime);
		try {
			Commons.enter(test,driver, By.id("organizationName"), orgName);
			Select select = new Select(driver.findElement(By.id("user.attributes.partnerType")));
		
		if(dropdwnVal.contains("DEVICE") ||  dropdwnVal.contains("FTM") )
		{	select.selectByValue(dropdwnVal+"_PROVIDER");
		}
		else {
			select.selectByValue(dropdwnVal+"_PARTNER");	
		}
		
		
		Commons.enter(test,driver, By.id("address"), data);
		Commons.enter(test,driver, By.id("email"), datetime+"@automationlabs.com");
		Commons.enter(test,driver, By.id("phoneNumber"), "9178338765");
		Commons.selOption(test,driver, By.id("user.attributes.langCode"), "English");
		
		
		Commons.enter(test,driver, By.id("username"),orgName+data);
		Commons.enter(test,driver, By.id("password"), orgName+data);
		Commons.enter(test,driver, By.id("password-confirm"),orgName+data);
		
		Commons.click(test,driver, By.xpath("//input[@type='submit']"));
		
		if(!(dropdwnVal.contains("DEVICE") ||  dropdwnVal.contains("FTM") ))
			{Commons.dropdown(test,driver, By.id("mat-select-0"),data);
		Commons.click(test,driver, By.id("applyTxt"));
		Commons.click(test,driver, By.id("/pmp/resources/policymapping/view"));	
		Commons.click(test,driver, By.id("/pmp/home"));
		}
		
		Commons.click(test,driver, By.id("uploadCertificate"));
		test.log(Status.INFO, "Click on uploadCertificate");
		if(dropdwnVal.contentEquals("CREDENTIAL")) Commons.uploadPartnerCert(driver,By.id("partnerDomain"),"AUTH","\\auth_cert\\",cer);
		else Commons.uploadPartnerCert(driver,By.id("partnerDomain"),dropdwnVal,"\\device_sbi_cert\\",cer);

			
		Commons.click(test,driver, By.id("viewCertificate"));
		test.log(Status.INFO, "Click on viewCertificate");
		
		String certificate=Commons.getText(driver,By.xpath("//p"));
		logger.info(certificate);
		Commons.click(test,driver, By.id("confirmmessagepopup"));	
		
		
		switch(dropdwnVal) {
		case "DEVICE":
					Commons.click(test,driver, By.id("/pmp/resources/devicedetails/view"));
					

					Commons.click(test,driver, By.id("Create Device"));
					Commons.dropdown(test,driver, By.id("deviceProviderId"),orgName);
					Commons.dropdown(test,driver, By.id("deviceTypeCode"),By.id("Face"));
					
					Commons.dropdown(test,driver, By.id("deviceSubTypeCode"),By.id("Full face"));
					Commons.enter(test,driver, By.xpath("//input[@id='make']"), data);
					Commons.enter(test,driver, By.xpath("//input[@id='model']"), data);
					Commons.click(test,driver, By.xpath("//button[@id='createButton']"));

					Commons.click(test,driver, By.id("confirmmessagepopup"));
					Thread.sleep(3000);
					Commons.filter(test,driver, By.id("make"),By.id("partnerOrganizationName"), data,orgName);
					Thread.sleep(3000);
					test.log(Status.INFO, "Click on filter");
					Commons.click(test,driver, By.id("ellipsis-button0"));
					Commons.click(test,driver, By.id("Edit0"));
					test.log(Status.INFO, "Click on edit");
					Commons.enter(test,driver, By.xpath("//input[@id='model']"), data + 1);
					Commons.click(test,driver, By.xpath("//button[@id='createButton']"));

					Commons.click(test,driver, By.id("confirmmessagepopup"));
				
// SBI View
					
					Commons.click(test,driver, By.id("/pmp/resources/sbidetails/view"));
					Commons.click(test,driver, By.xpath("//button[@id='Create SBI']"));
					
					Commons.dropdown(test,driver, By.id("providerId"),orgName);
					
					Commons.enter(test,driver, By.xpath("//input[@id='swVersion']"), data);
					Commons.enter(test,driver, By.xpath("//input[@id='swBinaryHash']"), data);
					Commons.enter(test,driver, By.xpath("//input[@id='swCreateDateTime']"), JsonUtil.JsonObjParsing(Commons.getTestData(),"sbivalidDate"));
					Commons.enter(test,driver, By.xpath("//input[@id='swExpiryDateTime']"), JsonUtil.JsonObjParsing(Commons.getTestData(),"sbiexpiryDate"));
					Commons.click(test,driver, By.xpath("//button[@id='createButton']"));
					Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));
					Thread.sleep(3000);
					Commons.filter(test,driver, By.id("swVersion"),data);
					Thread.sleep(3000);
					Commons.click(test,driver, By.id("ellipsis-button0"));
					Commons.click(test,driver, By.id("Edit0"));
					Commons.enter(test,driver, By.xpath("//input[@id='swBinaryHash']"), data+1);

					Commons.click(test,driver, By.xpath("//button[@id='createButton']"));
					Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));
				
		break;	
		case "FTM":
			Commons.click(test,driver, By.id("/pmp/resources/ftmdetails/view"));
			

			Commons.click(test,driver, By.id("Create Device"));
			Commons.dropdown(test,driver, By.id("ftpProviderId"),orgName);
			Commons.enter(test,driver, By.xpath("//input[@id='make']"), data);
			Commons.enter(test,driver, By.xpath("//input[@id='model']"), data);
			Commons.click(test,driver, By.xpath("//button[@id='createButton']"));

			Commons.click(test,driver, By.id("confirmmessagepopup"));

			Thread.sleep(3000);
			Commons.filter(test,driver, By.id("make"),By.id("partnerOrganizationName"), data,orgName);
			Thread.sleep(3000);
			Commons.click(test,driver, By.id("ellipsis-button0"));
			Commons.click(test,driver, By.id("Edit0"));
			Commons.enter(test,driver, By.xpath("//input[@id='model']"), data + 1);
			Commons.click(test,driver, By.xpath("//button[@id='createButton']"));
			Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));


break;	
		

		case "AUTH":
			
						
			Commons.click(test,driver, By.id("/pmp/resources/policymapping/view"));	
			Commons.click(test,driver, By.xpath("//button[@id='Map Policy']"));
			Commons.dropdown(test,driver, By.id("partnerId"),orgName);
			Commons.dropdown(test,driver, By.id("policyId"),"AUTH"+data);
			Commons.enter(test,driver, By.id("requestDetail"), data);
			Commons.click(test,driver, By.xpath("//button[@id='createButton']"));

			Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));

			
			Thread.sleep(3000);
				Commons.filter(test,driver, By.id("requestDetail"),By.id("partnerName"), data,orgName);

break;	

		case "CREDENTIAL":
			
			
			Commons.click(test,driver, By.id("/pmp/resources/policymapping/view"));	
			Commons.click(test,driver, By.xpath("//button[@id='Map Policy']"));
			Commons.dropdown(test,driver, By.id("partnerId"),orgName);
			Commons.dropdown(test,driver, By.id("policyId"),"DS"+data);
			Commons.enter(test,driver, By.id("requestDetail"), data);
			Commons.click(test,driver, By.xpath("//button[@id='createButton']"));

			Commons.click(test,driver, By.xpath("//button[@id='confirmmessagepopup']"));

			Thread.sleep(3000);
			Commons.filter(test,driver, By.id("requestDetail"),By.id("partnerName"), data,orgName);
			Thread.sleep(3000);


break;	

		}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
