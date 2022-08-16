package io.mosip.test.pmptest.testcase;

import java.awt.AWTException;
import org.openqa.selenium.By;

import org.testng.annotations.Test;



import io.mosip.test.pmptest.utility.BaseClass;
import io.mosip.test.pmptest.utility.Commons;
import io.mosip.test.pmptest.utility.ExtentReportUtil;
import io.mosip.test.pmptest.utility.Reporter;
import io.mosip.test.pmptest.utility.RealTimeReport;
import org.testng.annotations.Listeners;

@Listeners(value=Reporter.class)
public class AdminUploadCaCertTest extends BaseClass {
	
	@Test(groups = "UFCC",dataProvider = "data-provider-ca")
	public void adminUploadCaCertTest(String cer) throws InterruptedException, AWTException {

		

		Commons.click(driver, By.xpath("//a[@href='#/pmp/resources/uploadcacert/upload']"));
		
		String dropdwnVal=cer.substring(0, cer.indexOf("_", 0));
		String orgName=cer.substring(0, cer.length()-4);
		

		
		  ExtentReportUtil.test1 = ExtentReportUtil.reports
                  .createTest("UploadFtmCaCertTest : " + dropdwnVal + " Orgname : " + orgName);
		
		Commons.uploadPartnerCert(driver,By.id("partnerDomain"),dropdwnVal,"\\ca_cert\\",cer);
		 ExtentReportUtil.reports.flush();

	}
}
