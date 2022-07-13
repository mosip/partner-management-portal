package io.mosip.test.pmptest.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Commons {
	private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(Commons.class);

	public static String appendDate="Z"+getDateTime();
	
	public static String getDateTime()
	  {
		
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
	   LocalDateTime now = LocalDateTime.now();
	   return dtf.format(now);
	  }
	
	public  static void filter(WebDriver driver, By by,String data) {
		logger.info("Inside Filter " + by + data);
		Commons.click(driver, By.id("Filter")); 
	
		Commons.enter(driver, by, data); 
		Commons.click(driver, By.id("applyTxt")); 
	}
	
	public  static void filterCenter(WebDriver driver, By by,String data) {
		logger.info("Inside filterCenter " + by + data);
		Commons.click(driver, By.id("Filter")); 
	
		Commons.dropdowncenter(driver, by, data); 
		
		Commons.click(driver, By.id("applyTxt")); 
	}
	public  static void click(WebDriver driver, By by) {
		logger.info("Clicking " + by );
		try {
			(new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(by));
			Thread.sleep(500);
			driver.findElement(by).click();
			Thread.sleep(500);
		}catch (StaleElementReferenceException sere) {
			// simply retry finding the element in the refreshed DOM
			driver.findElement(by).click();
		}
		catch (TimeoutException toe) {
			driver.findElement(by).click();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println( "Element identified by " + by.toString() + " was not clickable after 20 seconds");
		} catch (Exception e) {
		
		JavascriptExecutor executor = (JavascriptExecutor) driver;
	     executor.executeScript("arguments[0].click();", driver.findElement(by));
	     
	  }}
  
	public static void enter(WebDriver driver, By by,String value) {
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
				// simply retry finding the element in the refreshed DOM
				driver.findElement(by).sendKeys(value);
			}
			catch (TimeoutException toe) {
				driver.findElement(by).sendKeys(value);
				System.out.println( "Element identified by " + by.toString() + " was not clickable after 20 seconds");
			} }
	
	public static void dropdown(WebDriver driver, By by)
	  {
		logger.info("Selecting DropDown Index Zero Value " + by );
		  
		 try {
			 //Thread.sleep(500);
			 click(driver,by);//REGION
				//Thread.sleep(500);
			
		   String att= driver.findElement(by).getAttribute("aria-owns");
		   String[] list=att.split(" ");
		    click( driver,By.id(list[0]));
		    try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }catch(Exception e)
		 
		 {
			 e.getMessage();
		 }
	  }
	
	public static void dropdown(WebDriver driver, By by,String value)
	  {
		logger.info("Selecting DropDown By Value " + by +value );
		  
		 try {
			 Thread.sleep(50);
			 click(driver,by);
				Thread.sleep(50);
			   String val="'"+value +"'";
		   
		    click( driver,By.xpath("//span[contains(text(),"+val+")]"));
		    try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }catch(Exception e)
		 
		 {
			 e.getMessage();
		 }
	  }
	
	public static void dropdowncenter(WebDriver driver, By by,String value)
	  {
		logger.info("Selecting DropDown By Value " + by +value );
		  
		 try {
			 Thread.sleep(500);
			 click(driver,by);
				Thread.sleep(500);
			   String val="'"+value +"'";
		   
		    click( driver,By.id(value));
		    try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }catch(Exception e)
		 
		 {
			 e.getMessage();
		 }
	  }
	
	public static void dropdown(WebDriver driver, By by,By value)
	  {
		logger.info("Selecting DropDown By Value " + by +value );
		 try {  
			 Thread.sleep(500);
			 click(driver,by);
			 Thread.sleep(500);
		    click( driver,value);
		  
				Thread.sleep(500);
			
		 }catch(Exception e)
		 
		 {
			 e.getMessage();
		 }
	  }
	public static String getTestData()
	{
		return JsonUtil.readJsonFileText("TestData.json");
	}
	
	
	
	public static String getJsonData()
	{
		return JsonUtil.readJsonFileText("PolicyData.json");
	}
	
	
	
	public static String getFieldData(String idfield) throws Exception
	{
		return	JsonUtil.JsonObjSimpleParsing(getTestData(), idfield);
	
	}

	public static void clickSpan(WebDriver driver,String key) throws Exception {
		
		String val=Commons.getFieldData(key);
		String var="//span[contains(text(),'"+ val+ "')]";
		  Commons.click(driver,By.xpath(var)); 
		  logger.info("clickSpan" + var );
	}

	public static void deactivate(WebDriver driver) {
		Commons.click(driver,By.id("ellipsis-button0"));
		Commons.click(driver, By.id("Deactivate0")); 

	    Commons.click(driver,By.id("confirmpopup")); 
		Commons.click(driver, By.id("confirmmessagepopup")); 
		 logger.info("Click deactivate and Confirm");
	}

	public static void activate(WebDriver driver) {
		Commons.click(driver,By.id("ellipsis-button0"));
		Commons.click(driver, By.id("Activate0")); 

	    Commons.click(driver,By.id("confirmpopup")); 
		Commons.click(driver, By.id("confirmmessagepopup")); 
		 logger.info("Click activate and Confirm");
	}

	public static void edit(WebDriver driver,String data,By by) {
		Commons.click(driver,By.id("ellipsis-button0"));
		Commons.click(driver, By.id("Edit0")); 
		
		Assert.assertNotEquals(data,
				driver.findElement(by).getText());
		driver.findElement(by).clear();

		Commons.enter(driver, by, data);

		Commons.click(driver, By.id("createButton"));
		Commons.click(driver, By.id("confirmmessagepopup")); 

		 logger.info("Click Edit and Confirm" + by + data);
	}

	public static void editRes(WebDriver driver,String data,By by) {
		Commons.click(driver,By.id("ellipsis-button0"));
		Commons.click(driver, By.id("Edit0")); 
		
		Assert.assertNotEquals(data,
				driver.findElement(by).getText());
		driver.findElement(by).clear();

		Commons.enter(driver, by, data);

		Commons.click(driver, By.id("createButton"));

	    Commons.click(driver,By.id("confirmpopup")); 
			Commons.click(driver, By.id("confirmmessagepopup")); 

			 logger.info("Click Edit and Confirm" + by + data);
	}
	public static void editCenter(WebDriver driver,String data,By by) {
		Commons.click(driver,By.id("ellipsis-button0"));
		Commons.click(driver, By.id("Edit0")); 
		
		Assert.assertNotEquals(data,
				driver.findElement(by).getText());
		driver.findElement(by).clear();

		Commons.enter(driver, by, data);

		Commons.click(driver, By.xpath("(//*[@id='createButton'])[1]"));

	    Commons.click(driver,By.id("confirmpopup")); 
			Commons.click(driver, By.id("confirmmessagepopup")); 

			Commons.click(driver,  By.xpath("(//*[@id='cancel'])[1]"));
			Commons.click(driver,  By.xpath("(//*[@id='cancel'])[1]"));
			 logger.info("Click editCenter and Confirm" + by + data);
	}
	
	public static void create(WebDriver driver) {
		Commons.click(driver, By.xpath("//button[@id='createButton']")); 
		Commons.click(driver, By.id("confirmmessagepopup")); 
		
		logger.info("Click create");
	}
	public static void createRes(WebDriver driver) {
		 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Commons.click(driver, By.xpath("//button[@id='createButton']")); 
		 Commons.click(driver,By.id("confirmpopup")); 
		Commons.click(driver, By.id("confirmmessagepopup")); 
		logger.info("Click and confirm");
	}

	public static void decommission(WebDriver driver) {
		 Commons.click(driver,By.id("ellipsis-button0"));
		    Commons.click(driver,By.id("Decommission0"));

		    Commons.click(driver,By.id("confirmpopup")); 
			Commons.click(driver, By.id("confirmmessagepopup")); 
			logger.info("Click decommission and confirm");
	}
	
	
	
}
