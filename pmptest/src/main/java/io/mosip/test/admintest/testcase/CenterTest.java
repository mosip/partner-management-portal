package io.mosip.test.admintest.testcase;
import static org.testng.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.mosip.test.pmptest.utility.BaseClass;
import io.mosip.test.pmptest.utility.Commons;
import io.mosip.test.pmptest.utility.JsonUtil;
public class CenterTest extends BaseClass{

	@Test(groups = "CTR")
	
  public void centerCRUD() throws Exception {
	  
   Reporter.log("centerCRUD",true);
   String holidayDate=JsonUtil.JsonObjParsing(Commons.getTestData(),"holidayDateCenter");
    Commons.click(driver,By.id("admin/resources"));

    Commons.click(driver,By.id("/admin/resources/centers"));
    
    Commons.click(driver, By.id("Create Center"));
    /*
     * Select Registration Center Type
     */
     
    /**
     * centerTypeCode dropdown
     */
   Commons.enter(driver, By.id("name"), data);
   
     Commons.dropdown(driver,By.id("centerTypeCode"));  
     Commons.enter(driver, By.id("contactPerson"),data);
     Commons.enter(driver,By.id("contactPhone"),data);
    
     Commons.enter(driver,By.id("longitude"),"1.1234");
     Commons.enter(driver,By.id("latitude"),"2.2345");
     Commons.enter(driver,By.id("addressLine1"),data);
     Commons.enter(driver,By.id("addressLine2"),data);
     Commons.enter(driver,By.id("addressLine3"),data);
    
    Commons.dropdown(driver, By.xpath("(//*[@id='fieldName'])[1]"));
    Commons.dropdown(driver, By.xpath("(//*[@id='fieldName'])[2]"));
    Commons.dropdown(driver, By.xpath("(//*[@id='fieldName'])[3]"));
    Commons.dropdown(driver, By.xpath("(//*[@id='fieldName'])[4]"));
    Commons.dropdown(driver, By.xpath("(//*[@id='fieldName'])[5]"));
   
  
    Commons.dropdown(driver, By.id("zone"));
    Commons.dropdown(driver, By.id("holidayZone"));


    
    
    Commons.enter(driver,By.id("noKiosk"),"10");
    
    Commons.dropdown(driver,By.id("processingTime"),"45");
    Commons.dropdown(driver,By.id("startTime"),"9:00 AM");
    Commons.dropdown(driver,By.id("endTime"),"5:00 PM");
    Commons.dropdown(driver,By.id("lunchStartTime"),"1:00 PM");
    Commons.dropdown(driver,By.id("lunchEndTime"),"2:00 PM");
    
     Commons.click(driver,By.cssSelector(".mat-list-item:nth-child(1) .mat-pseudo-checkbox"));
    Commons.click(driver,By.cssSelector(".mat-list-item:nth-child(2) .mat-pseudo-checkbox"));
    Commons.click(driver,By.cssSelector(".mat-list-item:nth-child(3) > .mat-list-item-content"));
    Commons.click(driver,By.cssSelector(".mat-list-item:nth-child(4) > .mat-list-item-content"));
    Commons.click(driver,By.cssSelector(".mat-list-item:nth-child(5) > .mat-list-item-content"));
    
    Commons.enter(driver,By.id("holidayDate"),holidayDate);
    Commons.click(driver, By.id("createExceptionalHoliday"));
    
    Commons.createRes(driver);
   	Commons.filterCenter(driver, By.id("name"), data);
   	

   	Commons.editCenter(driver,data+1,By.id("name"));
   	
   	Commons.filterCenter(driver, By.id("name"), data+1);
   	
   	Commons.activate(driver);
   	Commons.editCenter(driver,data+2,By.id("name"));
   	Commons.filterCenter(driver, By.id("name"), data+2);
   	Commons.deactivate(driver);
   	Commons.decommission(driver);
  }
}
