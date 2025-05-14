package io.mosip.testrig.pmprevampui.pages;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.apache.log4j.Logger;

import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
import io.mosip.testrig.pmprevampui.utility.JsonUtil;
import io.mosip.testrig.pmprevampui.utility.LogUtil;
import io.mosip.testrig.pmprevampui.utility.Screenshot;
import io.mosip.testrig.pmprevampui.utility.WaitUtil;

public class BasePage {

	protected WebDriver driver;
	public static String appendDate = getPreAppend() + getDateTime();
	private static final Logger logger = Logger.getLogger(BasePage.class);

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public static String getDateTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mmHHddMMyyyy");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public void waitForElementVisible(WebElement element) {
		WaitUtil.waitForVisibility(driver, element);
	}

	protected void waitForElementClickable(WebElement element) {
		WaitUtil.waitForClickability(driver, element);
	}

	public void clickOnElement(WebElement element) {
		LogUtil.action("Clicking on element: ", element);
		try {
			waitForElementClickable(element);
			element.click();
		} catch (Exception e) {
			LogUtil.step("Standard click failed, trying JS click: " + element.toString());
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			} catch (Exception jsEx) {
				LogUtil.error("JS click failed on element: " + element.toString());
				LogUtil.error("Exception: " + jsEx.getMessage());
				takeScreenshot();
				throw jsEx;
			}
		}
	}

	public void enter(WebElement element, String value) {
		LogUtil.action("Entering value '" + value + "' into element: ", element);
		try {
			waitForElementVisible(element);
			element.clear();
			element.sendKeys(value);
		} catch (Exception e) {
			LogUtil.step("Standard entry failed, trying JS click before typing: " + element.toString());
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			} catch (Exception jsEx) {
				LogUtil.error("JS click failed before typing: " + element.toString());
				LogUtil.error("Exception: " + jsEx.getMessage());
				takeScreenshot();
				throw jsEx;
			}
		}
	}

	public void enter(WebElement element, CharSequence[] value) {
		LogUtil.action("Entering value '" + value + "' into element: ", element);
		try {
			waitForElementVisible(element);
			element.clear();
			element.sendKeys(value);
		} catch (Exception e) {
			LogUtil.step("Standard entry failed, trying JS click before typing: " + element.toString());
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			} catch (Exception jsEx) {
				LogUtil.error("JS click failed before typing: " + element.toString());
				takeScreenshot();
				throw jsEx;
			}
		}
	}

	public void uploadImage(WebElement element, String path) {
		LogUtil.action("Uploading image: " + path + " to element: ", element);
		try {
			if (element.isDisplayed()) {
				waitForElementVisible(element);
			}

			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				element.sendKeys(path);
			} catch (ElementNotInteractableException e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", element);
				element.sendKeys(path);
			}

		} catch (Exception e) {
			LogUtil.step("Image upload failed for element: " + element.toString());
			takeScreenshot();
			throw new RuntimeException("Failed to upload file. Ensure <input type='file'> is used.", e);
		}
	}

	public void dropdownByIndex(WebElement element, int index) {
		LogUtil.action("Selecting dropdown index " + index + " for element: ", element);
		try {
			waitForElementVisible(element);
			clickOnElement(element);
			Select dropdown = new Select(element);
			dropdown.selectByIndex(index);

		} catch (Exception e) {
			LogUtil.step("Dropdown index select failed, trying JS click: " + element.toString());
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			} catch (Exception jsEx) {
				LogUtil.error("Dropdown index select failed: " + element.toString());
				takeScreenshot();
				throw jsEx;
			}
		}
	}

	public void dropdown(WebElement element, String value) throws IOException {
		LogUtil.action("Selecting dropdown value '" + value + "' for element: ", element);
		try {
			waitForElementVisible(element);
			clickOnElement(element);
			click(By.xpath("//*[text()='" + value + "']"));
		} catch (Exception e) {
			LogUtil.step("Dropdown text select failed, trying JS click: " + element.toString());
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			} catch (Exception jsEx) {
				LogUtil.error("Dropdown text select failed: " + element.toString());
				takeScreenshot();
				throw jsEx;
			}
		}
	}

	public void dropdownWithPosition(WebElement element, String value, int position) throws IOException {
		LogUtil.action("Selecting dropdown position " + position + " for element: ", element);
		try {
			waitForElementVisible(element);
			clickOnElement(element);
			click(By.xpath("(//*[contains(text(),'" + value + "')])[" + position + "]"));
		} catch (Exception e) {
			LogUtil.step("Dropdown position select failed, trying JS click: " + element.toString());
			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				WebElement option = driver
						.findElement(By.xpath("(//*[contains(text(),'" + value + "')])[" + position + "]"));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
			} catch (Exception jsEx) {
				LogUtil.error("Dropdown text select failed: " + element.toString());
				takeScreenshot();
				throw new RuntimeException("Failed to select dropdown value '" + value + "' at position " + position,
						jsEx);
			}
		}
	}

	public void selectByValueInDropdown(WebElement element, String value) {
		LogUtil.action("Selecting dropdown value '" + value + "' for element: ", element);
		Select select = new Select(element);
		select.selectByValue(value);
	}

	protected void click(By by) {
		try {
			WebElement element = driver.findElement(by);
			LogUtil.action("Clicking on element: ", element);
			waitForElementClickable(element);
			element.click();
		} catch (Exception e) {
			throw new RuntimeException("Failed to click on element: " + by, e);
		}
	}

	public String generateRandomAlphabetString() {
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
		LogUtil.verify("Checking is element is displayed: ", element);
		try {
			waitForElementToBeVisible(element);
			return true;
		} catch (Exception e) {
			takeScreenshot();
			return false;
		}
	}


	protected boolean isElementDisabled(WebElement element) {
		LogUtil.verify("Checking is element is disabled: ", element);
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			return wait.until(driver -> !element.isEnabled());
		} catch (Exception e) {
			takeScreenshot();
			return false;
		}
	}

	protected boolean isElementEnabled(WebElement element) {
		LogUtil.verify("Checking is element is enabled: ", element);
		try {
			waitForElementClickable(element);
			return element.isEnabled();
		} catch (Exception e) {
			takeScreenshot();
			return false;
		}
	}

	protected void waitForElementToBeVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	private void waitForElementToBeDisabled(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOfAllElements(element));
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
		LogUtil.action("Getting text from element: ", element);
		waitForElementVisible(element);
		return element.getText();
	}

	protected String getTextFromAttribute(WebElement element, String atrr) {
		LogUtil.action("Getting text from element for the " + atrr + " attribute: ", element);
		waitForElementVisible(element);
		return element.getAttribute(atrr);
	}

	public static String getTestData() {
		return JsonUtil.readJsonFileText("TestData.json");
	}

	public void refreshThePage() {
		LogUtil.action("Refreshing the page");
		driver.navigate().refresh();
	}

	public void navigateBack() {
		LogUtil.action("Navigating to the back page");
		driver.navigate().back();
	}

	public void navigateForword() {
		LogUtil.action("Navigating to the forward page");
		driver.navigate().forward();
	}

	public void reload() {
		LogUtil.action("Reloading the page");
		driver.navigate().refresh();
	}

	public void back() {
		LogUtil.action("Navigating to the back page");
		driver.navigate().back();
	}

	public void acceptAlert() {
		LogUtil.action("Accepting the alert");
		driver.switchTo().alert().accept();
	}

	public void cancelAlert() {
		LogUtil.action("Cancelling the alert");
		driver.switchTo().alert().dismiss();
	}

	public String getAlertText() {
		LogUtil.action("Getting the alert text");
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		return alertText;
	}

	protected void clearTextBox(WebElement element) {
		this.waitForElementToBeVisible(element);
		element.clear();
	}

	public void scrollToEndPage() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

	}

	public void scrollToStartPage() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0);");

	}

	private void takeScreenshot() {
		try {
			String base64Image = Screenshot.ClickScreenshot(driver);
			String html = "<p><img src='data:image/png;base64," + base64Image + "' width='900' height='450'/></p>";
			Reporter.log(html, true);
			logger.info("Screenshot captured.");
		} catch (IOException e) {
			logger.error("Failed to take screenshot", e);
		}
	}
}
