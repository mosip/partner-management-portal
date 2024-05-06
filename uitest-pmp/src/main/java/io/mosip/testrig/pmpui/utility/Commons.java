package io.mosip.testrig.pmpui.utility;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import io.mosip.testrig.pmpui.kernel.util.ConfigManager;

public class Commons extends BaseClass {
	private static final Logger logger = Logger.getLogger(Commons.class);
	public static String appendDate=getPreAppend()+getDateTime();
	public static String getDateTime()
	{


		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public static String getUnique()
	{


		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddHHmm");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public  static void filter(ExtentTest test,WebDriver driver, By by,String data) {
		logger.info("Inside Filter " + by + data);
		try
		{
			Commons.click( test,driver, By.id("Filter")); 

			Commons.enter(test,driver, by, data); 
			Commons.click(test,driver, By.id("applyTxt")); 
		}
		catch (Exception e) {
			try {
				test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.ClickScreenshot(driver)).build());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(by));

		}

	}


	public  static void filter(ExtentTest test,WebDriver driver, By by1,By by2,String data1,String data2) throws InterruptedException, IOException {
		logger.info("Inside Filter " + by1 + data1);
		logger.info("Inside Filter " + by2 + data2);

		Commons.click(test,driver, By.id("Filter")); 

		Commons.enter( test,driver, by1, data1);


		Commons.click(test,driver, By.id("Filter")); 
		Thread.sleep(1000);
		Commons.enter( test,driver, by1, data1);
		Thread.sleep(1000);
		Commons.enter( test,driver, by2, data2);
		Commons.click( test,driver, By.id("applyTxt")); 


	}

	public  static void filterCenter(ExtentTest test,WebDriver driver, By by,String data) {
		logger.info("Inside filterCenter " + by + data);
		try
		{Commons.click(test,driver, By.id("Filter")); 

		Commons.dropdowncenter(test,driver, by, data); 

		Commons.click(test,driver, By.id("applyTxt")); 
		}
		catch (Exception e) {
			try {
				test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.ClickScreenshot(driver)).build());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(by));

		}
	}
	public  static void click(ExtentTest test,WebDriver driver, By by) throws IOException {
		logger.info("Clicking " + by );
		try {
			(new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(by));
			Thread.sleep(2000);
			driver.findElement(by).click();
			Thread.sleep(2000);
		}catch (StaleElementReferenceException sere) {
			Reporter.log("<p><img src='data:image/png;base64," + Screenshot.ClickScreenshot(driver) + "' width='900' height='450'/></p>");

			// simply retry finding the element in the refreshed DOM
			driver.findElement(by).click();
		}
		catch (TimeoutException toe) {
			Reporter.log("<p><img src='data:image/png;base64," + Screenshot.ClickScreenshot(driver) + "' width='900' height='450'/></p>");
			driver.findElement(by).click();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println( "Element identified by " + by.toString() + " was not clickable after 20 seconds");
		} catch (Exception e) {
			try {
				test.fail(e.getMessage());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(by));

		}}
	public  static void clickWebelement(ExtentTest test,WebDriver driver, By by) throws IOException, InterruptedException {
		logger.info("Clicking " + by );

		try {
			(new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(by));
			Thread.sleep(500);
			WebElement checkbox= driver.findElement(by);
			js.executeScript("arguments[0].click();", checkbox);
			Thread.sleep(500);
		}catch (StaleElementReferenceException sere) {
			Reporter.log("<p><img src='data:image/png;base64," + Screenshot.ClickScreenshot(driver) + "' width='900' height='450'/></p>");

			// simply retry finding the element in the refreshed DOM
			driver.findElement(by).click();
		}
	}



	public static void enter(ExtentTest test,WebDriver driver, By by,String value) throws IOException {

		logger.info("Entering " + by +value);
		//value="10";
		try {
			(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOfElementLocated(by));
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(value);
			try {
				Thread.sleep(8);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch (StaleElementReferenceException sere) {

			Reporter.log("<p><img src='data:image/png;base64," + Screenshot.ClickScreenshot(driver) + "' width='900' height='450'/></p>");


			// simply retry finding the element in the refreshed DOM
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(value);
		}
		catch (TimeoutException toe) {

			Reporter.log("<p><img src='data:image/png;base64," + Screenshot.ClickScreenshot(driver) + "' width='900' height='450'/></p>");


			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(value);
			System.out.println( "Element identified by " + by.toString() + " was not clickable after 20 seconds");
		} }

	public static void dropdown(ExtentTest test,WebDriver driver, By by)
	{
		logger.info("Selecting DropDown Index Zero Value " + by );

		try {
			Thread.sleep(500);
			click(test,driver,by);//REGION
			Thread.sleep(500);

			String att= driver.findElement(by).getAttribute("aria-owns");
			String[] list=att.split(" ");
			click(test, driver,By.id(list[0]));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch (Exception e) {
			try {
				test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.ClickScreenshot(driver)).build());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(by));

		}
	}

	public static void dropdown(ExtentTest test,WebDriver driver, By by,String value)
	{
		logger.info("Selecting DropDown By Value " + by +value );

		try {
			Thread.sleep(50);
			click(test,driver,by);
			Thread.sleep(50);
			String val="'"+value +"'";

			click(test,driver,By.xpath("//span[contains(text(),"+val+")]"));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch (Exception e) {
			try {
				test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.ClickScreenshot(driver)).build());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(by));

		}
	}
	//option[@value,'FTM_PROVIDER']

	public static void dropdownbyid(ExtentTest test,WebDriver driver, By by,String value)
	{
		logger.info("Selecting DropDown By Value " + by +value );

		try {
			Thread.sleep(500);
			click(test,driver,by);
			Thread.sleep(500);
			String val="'"+value +"'";

			click(test,driver,By.id(value));
			try {
				Thread.sleep(500);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch (Exception e) {
			try {
				test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.ClickScreenshot(driver)).build());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(by));

		}
	}
	public static void selOption(ExtentTest test,WebDriver driver, By by,String value)
	{
		logger.info("Selecting DropDown By Value " + by +value );

		try {
			Thread.sleep(50);
			click(test,driver,by);
			Thread.sleep(50);
			String val="'"+value +"'";
			//select/option[contains(text(),'FTM Provider')]
			// String xpath="//select/option[contains(text(),"+val+"]";
			//clickAction(test,driver,By.xpath(xpath));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch (Exception e) {
			try {
				test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.ClickScreenshot(driver)).build());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(by));

		}
	}


	public static void dropdowncenter(ExtentTest test,WebDriver driver, By by,String value)
	{
		logger.info("Selecting DropDown By Value " + by +value );

		try {
			Thread.sleep(50);
			click(test,driver,by);
			Thread.sleep(50);
			String val="'"+value +"'";

			click(test,driver,By.id(value));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch (Exception e) {
			try {
				test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.ClickScreenshot(driver)).build());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(by));

		}
	}

	public static void dropdown(ExtentTest test,WebDriver driver, By by,By value)
	{
		logger.info("Selecting DropDown By Value " + by +value );
		try {  
			Thread.sleep(50);
			click(test,driver,by);
			Thread.sleep(50);
			click(test,driver,value);

			Thread.sleep(500);

		}catch (Exception e) {
			try {
				test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.ClickScreenshot(driver)).build());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(by));

		}
	}
	public static String getTestData()
	{
		return JsonUtil.readJsonFileText("TestData.json");
	}




	public static void deactivate(ExtentTest test,WebDriver driver) throws InterruptedException, IOException {


		Commons.click(test,driver,By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Deactivate0")); 

		Commons.click(test,driver,By.id("confirmpopup")); 
		Commons.click(test,driver, By.id("confirmmessagepopup")); 
		logger.info("Click deactivate and Confirm");


	}

	public static void activate(ExtentTest test,WebDriver driver) throws InterruptedException, IOException {

		Commons.click(test,driver,By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Activate0")); 

		Commons.click(test,driver,By.id("confirmpopup")); 
		Commons.click(test,driver, By.id("confirmmessagepopup")); 
		logger.info("Click activate and Confirm");
	}

	public static void edit(ExtentTest test,WebDriver driver,String data,By by) {
		try
		{Commons.click(test,driver,By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Edit0")); 

		Assert.assertNotEquals(data,
				driver.findElement(by).getText());
		driver.findElement(by).clear();

		Commons.enter(test,driver, by, data);

		Commons.click(test,driver, By.id("createButton"));
		Commons.click(test,driver, By.id("confirmmessagepopup")); 

		logger.info("Click Edit and Confirm" + by + data);}
		catch (Exception e) {
			try {
				test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.ClickScreenshot(driver)).build());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(by));

		}
	}

	public static void editRes(ExtentTest test,WebDriver driver,String data,By by) {
		try{Commons.click(test,driver,By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Edit0")); 

		Assert.assertNotEquals(data,
				driver.findElement(by).getText());
		driver.findElement(by).clear();

		Commons.enter(test,driver, by, data);

		Commons.click(test,driver, By.id("createButton"));

		Commons.click(test,driver,By.id("confirmpopup")); 
		Commons.click(test,driver, By.id("confirmmessagepopup")); 

		logger.info("Click Edit and Confirm" + by + data);
		}catch (Exception e) {
			try {
				test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.ClickScreenshot(driver)).build());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(by));

		}
	}
	public static void editCenter(ExtentTest test,WebDriver driver,String data,By by) {
		try{Commons.click(test,driver,By.id("ellipsis-button0"));
		Commons.click(test,driver, By.id("Edit0")); 

		Assert.assertNotEquals(data,
				driver.findElement(by).getText());
		driver.findElement(by).clear();

		Commons.enter(test,driver, by, data);

		Commons.click(test,driver, By.xpath("(//*[@id='createButton'])[1]"));

		Commons.click(test,driver,By.id("confirmpopup")); 
		Commons.click(test,driver, By.id("confirmmessagepopup")); 

		Commons.click(test,driver,  By.xpath("(//*[@id='cancel'])[1]"));
		Commons.click(test,driver,  By.xpath("(//*[@id='cancel'])[1]"));
		logger.info("Click editCenter and Confirm" + by + data);}
		catch (Exception e) {
			try {
				test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.ClickScreenshot(driver)).build());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(by));

		}
	}


	public static void create(ExtentTest test,WebDriver driver) throws InterruptedException, IOException {


		Commons.click(test,driver, By.xpath("//button[@id='createButton']")); 
		Commons.click(test,driver, By.id("confirmmessagepopup")); 

		logger.info("Click create");

	}

	public static void createRes(ExtentTest test,WebDriver driver) throws InterruptedException, IOException {

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Commons.click(test,driver, By.xpath("//button[@id='createButton']")); 
		Commons.click(test,driver,By.id("confirmpopup")); 
		Commons.click(test,driver, By.id("confirmmessagepopup")); 
		logger.info("Click and confirm");



	}


	public static void decommission(ExtentTest test,WebDriver driver) throws InterruptedException, IOException {

		Commons.click(test,driver,By.id("ellipsis-button0"));
		Commons.click(test,driver,By.id("Decommission0"));

		Commons.click(test,driver,By.id("confirmpopup")); 
		Commons.click(test,driver, By.id("confirmmessagepopup")); 
		logger.info("Click decommission and confirm");
	}

	public static void clickAction(ExtentTest test,WebDriver driver, By by) {

		try
		{Actions action = new Actions(driver);
		WebElement we=driver.findElement(by);
		action.moveToElement(we).click().perform();
		}
		catch (Exception e) {
			try {
				test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.ClickScreenshot(driver)).build());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(by));

		}
	}




	public static String getText(WebDriver driver, By by) {
		String str=driver.findElement(by).getText();
		return str;
	}

	public static void uploadPartnerCertold(WebDriver driver, By by,String orgName,String folder,String str) throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		Commons.dropdown(test,driver, by, By.id(orgName)
				);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Commons.clickAction(test,driver, By.xpath("//*[@type='button']"));

		String filePath=null;;
		try {
			filePath = TestRunner.getResourcePath() + "/" + folder+str ;
			logger.info("uploadPartnerCertold"+filePath);
		} catch (Exception e) {

			// TODO Auto-generated catch block;
			e.printStackTrace();
		}
		StringSelection ss = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		Robot robot;
		try {
			robot = new Robot();
			robot.delay(250);
			robot.keyPress(KeyEvent.VK_ENTER); robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_CONTROL); robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V); robot.delay(250);
			robot.keyRelease(KeyEvent.VK_CONTROL); robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(250); robot.keyRelease(KeyEvent.VK_ENTER); robot.delay(250);
			Commons.click(test,driver, By.id("createButton"));
			Commons.click(test,driver, By.id("confirmmessagepopup"));
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void uploadPartnerCert(WebDriver driver, By by,String orgName,String folder,String str) throws InterruptedException, IOException {


		// TODO Auto-generated method stub

		Commons.dropdown(test,driver, by, By.id(orgName)
				);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		String filePath=null;;
		try {
			filePath = TestRunner.getResourcePath() + "/" + folder+str ;
			logger.info("uploadPartnerCertold"+filePath);
			Commons.enter(test, driver, By.id("fileInput"), filePath);
		} catch (Exception e) {

			// TODO Auto-generated catch block;
			e.printStackTrace();
		}

		Commons.click(test,driver, By.id("createButton"));
		Commons.click(test,driver, By.id("confirmmessagepopup"));


	}
	public static String getPreAppend() 
	{
		String preappend = null;
		try {

			preappend = ConfigManager.getpreappend();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return preappend;
	}
	public static int getSplitdigit() 
	{
		String splitdigit = null;
		try {

			splitdigit = ConfigManager.getsplitdigit();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.parseInt(splitdigit);
	}
}

