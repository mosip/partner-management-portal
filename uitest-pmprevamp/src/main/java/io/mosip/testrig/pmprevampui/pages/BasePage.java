package io.mosip.testrig.pmprevampui.pages;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
import io.mosip.testrig.pmprevampui.utility.JsonUtil;
import io.mosip.testrig.pmprevampui.utility.Screenshot;

public class BasePage {

	protected static WebDriver driver;
	public static String appendDate = getPreAppend() + getDateTime();

	public BasePage(WebDriver driver) {
		BasePage.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public static String getDateTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mmHHddMMyyyy");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public static void clickOnElement(WebElement element) {
		wait(1000);
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
		} catch (Exception e) {
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			} catch (Exception exception) {
				try {
					Reporter.log("<p><img src='data:image/png;base64," + Screenshot.ClickScreenshot(driver)
							+ "' width='900' height='450'/></p>");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				throw exception;
			}
		}
	}

	public static void enter(WebElement element, String value) {

		try {
			Thread.sleep(3000);
			element.clear();
			element.sendKeys(value);
		} catch (Exception e) {
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			} catch (Exception exception) {
				try {
					Reporter.log("<p><img src='data:image/png;base64," + Screenshot.ClickScreenshot(driver)
							+ "' width='900' height='450'/></p>");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				throw exception;
			}
		}
	}

	public static void enter(WebElement element, CharSequence[] value) {

		try {
			Thread.sleep(3000);
			element.clear();
			element.sendKeys(value);
		} catch (Exception e) {
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			} catch (Exception exception) {
				try {
					Reporter.log("<p><img src='data:image/png;base64," + Screenshot.ClickScreenshot(driver)
							+ "' width='900' height='450'/></p>");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				throw exception;
			}
		}
	}

	public static void uploadImage(WebElement element, String path) {
		try {
			wait(1000);
			element.sendKeys(path);
		} catch (Exception e) {
			try {
				wait(1000);
				element.sendKeys(path);
			} catch (Exception exception) {
				try {
					Reporter.log("<p><img src='data:image/png;base64," + Screenshot.ClickScreenshot(driver)
							+ "' width='900' height='450'/></p>");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				throw exception;
			}
		}
	}

	public static void dropdownByIndex(WebElement element, int index) {

		try {
			Thread.sleep(50);
			clickOnElement(element);
			Select dropdown = new Select(element);
			dropdown.selectByIndex(index);

		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
		}
	}

	public static void dropdown(WebElement element, String value) throws IOException {

		try {
			Thread.sleep(50);
			clickOnElement(element);
			Thread.sleep(50);
			String val = "'" + value + "'";
			click(By.xpath("//*[contains(text()," + val + ")]"));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);

		}
	}
	
	public static void dropdownWithPosition(WebElement element, String value, int position) throws IOException {

		try {
			Thread.sleep(50);
			clickOnElement(element);
			Thread.sleep(50);
			click(By.xpath("(//*[contains(text(),'" + value + "')])[" + position + "]"));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);

		}
	}
	
	public static void selectByValueInDropdown(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
	}

	protected static void click(By by) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(by).click();
	}

	public static String generateRandomAlphabetString() {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder(15);

		for (int i = 0; i < 15; i++) {
			int index = random.nextInt(alphabet.length());
			stringBuilder.append(alphabet.charAt(index));
		}

		return stringBuilder.toString();
	}

	protected boolean isElementDisplayed(WebElement element) {
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			Thread.sleep(2000);
			waitForElementToBeVisible(element);
			return true;
		} catch (Exception e) {
			try {
				Reporter.log("<p><img src='data:image/png;base64," + Screenshot.ClickScreenshot(driver)
						+ "' width='900' height='450'/></p>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}

	protected boolean isElementEnabled(WebElement element) {
		try {
			waitForElementToBeVisible(element);
			return element.isEnabled();
		} catch (Exception e) {
			try {
				Reporter.log("<p><img src='data:image/png;base64," + Screenshot.ClickScreenshot(driver)
						+ "' width='900' height='450'/></p>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}
	
	private void waitForElementToBeVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void wait(int wait) {
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getPreAppend() {
		String preappend = null;
		try {

			preappend = ConfigManager.getpreappend();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return preappend;
	}

	public static int getSplitdigit() {
		String splitdigit = null;
		try {

			splitdigit = ConfigManager.getsplitdigit();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.parseInt(splitdigit);
	}

	protected String getTextFromLocator(WebElement element) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		this.waitForElementToBeVisible(element);
		return element.getText();
	}

	protected String getTextFromAttribute(WebElement element, String atrr) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		this.waitForElementToBeVisible(element);
		return element.getAttribute(atrr);
	}

	public static String getTestData() {
		return JsonUtil.readJsonFileText("TestData.json");
	}
	
	public void reload() {
		driver.navigate().refresh();
	}
	
	public void back() {
		driver.navigate().back();
	}
	
	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}
	
	public void cancelAlert() {
		driver.switchTo().alert().dismiss();
	}
	
	public String getAlertText() {
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		return alertText;
	}

}
