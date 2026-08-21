package io.mosip.testrig.pmpuiv2.pages;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.apache.log4j.Logger;

import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;
import io.mosip.testrig.pmpuiv2.utility.JsonUtil;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;
import io.mosip.testrig.pmpuiv2.utility.Screenshot;
import io.mosip.testrig.pmpuiv2.utility.WaitUtil;

public class BasePage {

	protected WebDriver driver;
	protected static final int STALE_RETRY = 2;
	private static final String REDACTED_VALUE = "***";
	protected static final Logger logger = Logger.getLogger(BasePage.class);

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
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

	protected void waitForElementClickable(By locator) {
		WaitUtil.waitForClickable(driver, locator);
	}

	public void clickOnElement(WebElement element) {
		LogUtil.action("Clicking on element", element);
		int attempts = 0;
		while (attempts < 2) {
			try {
				WaitUtil.waitForVisibility(driver, element);
				WaitUtil.waitForClickability(driver, element);
				element.click();
				return;

			} catch (StaleElementReferenceException stale) {
				attempts++;
				LogUtil.step("Page URL: " + driver.getCurrentUrl());
				LogUtil.step("Page Title: " + driver.getTitle());
				LogUtil.step("Stale element detected. Retry " + attempts + "/" + STALE_RETRY);
			} catch (ElementClickInterceptedException intercepted) {
				LogUtil.step("Click intercepted. Falling back to JS click.");
				jsClick(element);
				return;
			} catch (ElementNotInteractableException ei) {
				LogUtil.step("Click intercepted. Falling back to JS click.");
				jsClick(element);
				return;
			} catch (TimeoutException timeout) {
				LogUtil.error("Element not clickable within timeout");
				takeScreenshot();
				throw timeout;
			} catch (Exception e) {
				LogUtil.error("Unexpected click failure: " + e.getClass().getSimpleName());
				takeScreenshot();
				throw new RuntimeException("Click failed for element: " + element, e);
			}
		}

		takeScreenshot();
		throw new RuntimeException("Failed to click element after " + STALE_RETRY + " stale retries");
	}

	public void click(By locator) throws ElementClickInterceptedException {
		LogUtil.action("Clicking on element :" + locator);

		int attempts = 0;
		while (attempts < STALE_RETRY) {
			try {
				WebElement element = WaitUtil.waitForClickable(driver, locator);
				element.click();
				return;

			} catch (StaleElementReferenceException e) {
				attempts++;
				LogUtil.step("Stale element. Retrying " + attempts + "/" + STALE_RETRY);

			} catch (ElementClickInterceptedException intercepted) {
				LogUtil.step("Click intercepted. Falling back to JS click.");
				jsClick(locator);
				return;
			} catch (ElementNotInteractableException e) {
				LogUtil.step("Click intercepted. Falling back to JS click.");
				jsClick(locator);
				return;
			} catch (TimeoutException e) {
				takeScreenshot();
				throw e;
			} catch (WebDriverException e) {
				if (e.getMessage() != null && e.getMessage().contains("does not belong to the document")) {
					attempts++;
					LogUtil.step("DOM refreshed. Retrying " + attempts + "/" + STALE_RETRY);
				} else {
					throw e;
				}
			}

		}

		takeScreenshot();
		throw new RuntimeException("Failed to click element after retries: " + locator);
	}

	private void jsClick(By locator) {
		WebElement element = driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public void enter(WebElement element, String value) {
		enterValue(element, value, value);
	}

	// Types the value but keeps it out of the logs and reports - for personal data such as
	// the partner email addresses the filter tests read back from the live list.
	public void enterRedacted(WebElement element, String value) {
		enterValue(element, value, REDACTED_VALUE);
	}

	private void enterValue(WebElement element, String value, String loggedValue) {
		LogUtil.action("Entering value '" + loggedValue + "' into element: ", element);
		int attempts = 0;

		while (attempts < STALE_RETRY) {
			try {
				waitForElementVisible(element);
				element.clear();
				element.sendKeys(value);
				return;
			} catch (StaleElementReferenceException e) {
				attempts++;
				LogUtil.step("Page URL: " + driver.getCurrentUrl());
				LogUtil.step("Page Title: " + driver.getTitle());
				LogUtil.step("Retrying enter due to stale element");
			}
		}
		takeScreenshot();
		throw new RuntimeException("Element still stale after retry while entering text");
	}

	public void enter(WebElement element, CharSequence[] value) {
		LogUtil.action("Entering value '" + value + "' into element: ", element);
		int attempts = 0;

		while (attempts < STALE_RETRY) {
			try {
				waitForElementVisible(element);
				element.clear();
				element.sendKeys(value);
				return;
			} catch (StaleElementReferenceException e) {
				attempts++;
				LogUtil.step("Page URL: " + driver.getCurrentUrl());
				LogUtil.step("Page Title: " + driver.getTitle());
				LogUtil.step("Retrying enter due to stale element");
			}
		}
		takeScreenshot();
		throw new RuntimeException("Element still stale after retry while entering text");
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

		} catch (StaleElementReferenceException e) {
			LogUtil.step("Retrying image upload due to stale element");
			LogUtil.step("Page URL: " + driver.getCurrentUrl());
			LogUtil.step("Page Title: " + driver.getTitle());
			WaitUtil.waitForVisibility(driver, element);
			element.sendKeys(path);
		} catch (Exception e) {
			LogUtil.step("Image upload failed for element: " + element.toString());
			takeScreenshot();
			throw new RuntimeException("Failed to upload file. Ensure <input type='file'> is used.", e);
		}
	}

	public void dropdownByIndex(WebElement element, int index) {
		LogUtil.action("Selecting dropdown index " + index + " for element: ", element);
		int attempts = 0;
		while (attempts < STALE_RETRY) {
			try {
				waitForElementVisible(element);
				new Select(element).selectByIndex(index);
				return;
			} catch (StaleElementReferenceException e) {
				attempts++;
				LogUtil.step("Page URL: " + driver.getCurrentUrl());
				LogUtil.step("Page Title: " + driver.getTitle());
				LogUtil.step("Retrying dropdown selection due to stale element");
			}
		}
		takeScreenshot();
		throw new RuntimeException("Dropdown still stale after retry");
	}

	public void dropdown(WebElement element, String value) throws IOException {
		LogUtil.action("Selecting dropdown value '" + value + "' for element: ", element);
		try {
			waitForElementVisible(element);
			clickOnElement(element);
			click(By.xpath("//*[text()='" + value + "']"));
			return;
		} catch (StaleElementReferenceException e) {
			LogUtil.step("Retrying dropdown selection due to stale element");
			LogUtil.step("Page URL: " + driver.getCurrentUrl());
			LogUtil.step("Page Title: " + driver.getTitle());
			click(By.xpath("//*[text()='" + value + "']"));
		}
		takeScreenshot();
		throw new RuntimeException("Dropdown still stale after retry");
	}

	public void dropdownWithPosition(WebElement element, String value, int position) throws IOException {
		LogUtil.action("Selecting dropdown position " + position + " for element: ", element);

		try {
			waitForElementVisible(element);
			clickOnElement(element);

			click(By.xpath("(//*[normalize-space(text())='" + value + "' and not(contains(@class,'hidden'))])["
					+ position + "]"));
			return;

		} catch (StaleElementReferenceException e) {
			LogUtil.step("Retrying dropdownWithPosition due to stale element");
			LogUtil.step("Page URL: " + driver.getCurrentUrl());
			LogUtil.step("Page Title: " + driver.getTitle());

			click(By.xpath("(//*[normalize-space(text())='" + value + "' and not(contains(@class,'hidden'))])["
					+ position + "]"));
			return;
		}
	}

	public void dropdownWithPosition(By dropdownLocator, String value, int position) {

		LogUtil.action("Selecting dropdown position " + position + " with locator " + dropdownLocator);

		int attempts = 0;

		while (attempts < 2) {
			try {
				WebElement dropdown = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getTimeout()))
						.until(ExpectedConditions.elementToBeClickable(dropdownLocator));

				dropdown.click();

				By optionLocator = By.xpath("//div[contains(@class,'menu')]//*[contains(text(),'" + value + "')]");

				WebElement option = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getTimeout()))
						.until(ExpectedConditions.elementToBeClickable(optionLocator));

				option.click();
				return;

			} catch (StaleElementReferenceException e) {
				attempts++;
				LogUtil.step("Retrying dropdownWithPosition due to stale element");
			}
		}

		takeScreenshot();
		throw new RuntimeException("Failed to select dropdown value: " + value);
	}

	public void selectByValueInDropdown(WebElement element, String value) {
		LogUtil.action("Selecting dropdown value '" + value + "' for element: ", element);
		int attempts = 0;
		while (attempts < STALE_RETRY) {
			try {
				WaitUtil.waitForVisibility(driver, element);
				new Select(element).selectByValue(value);
				return;
			} catch (StaleElementReferenceException e) {
				attempts++;
				LogUtil.step("Page URL: " + driver.getCurrentUrl());
				LogUtil.step("Page Title: " + driver.getTitle());
				LogUtil.step("Retrying selectByValue due to stale element");
			}
		}
		takeScreenshot();
		throw new RuntimeException("Dropdown still stale after " + STALE_RETRY + " retries: value=" + value);
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
			WaitUtil.waitForVisibility(driver, element);
			return true;
		} catch (Exception e) {
			LogUtil.error("isElementDisplayed failed: " + e.getClass().getSimpleName());
			takeScreenshot();
			return false;
		}
	}

	protected boolean isElementDisplayedQuick(By locator, Duration timeout) {
		LogUtil.verify("Checking is element is displayed: ", locator);
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
			return false;
		}
	}

	protected boolean isElementDisabled(WebElement element) {
		LogUtil.verify("Checking if element is disabled: ", element);
		try {
			WaitUtil.waitForVisibility(driver, element);
			return !element.isEnabled();
		} catch (StaleElementReferenceException e) {
			LogUtil.step("Element became stale while checking disabled state");
			LogUtil.step("Page URL: " + driver.getCurrentUrl());
			LogUtil.step("Page Title: " + driver.getTitle());
			return true; // treat stale as disabled
		} catch (Exception e) {
			LogUtil.error("isElementDisabled failed: " + e.getClass().getSimpleName());
			takeScreenshot();
			return false;
		}
	}

	protected boolean isElementEnabled(WebElement element) {
		LogUtil.verify("Checking if element is enabled: ", element);
		try {
			WaitUtil.waitForVisibility(driver, element);
			return element.isEnabled();
		} catch (StaleElementReferenceException e) {
			LogUtil.step("Element became stale while checking enabled state");
			LogUtil.step("Page URL: " + driver.getCurrentUrl());
			LogUtil.step("Page Title: " + driver.getTitle());
			return false;
		} catch (Exception e) {
			LogUtil.error("isElementEnabled failed: " + e.getClass().getSimpleName());
			takeScreenshot();
			return false;
		}
	}

	protected boolean isElementEnabled(By locator) {
		LogUtil.verify("Checking if element is enabled: ", locator);

		try {
			WebElement element = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getTimeout()))
					.until(ExpectedConditions.visibilityOfElementLocated(locator));

			return element.isEnabled();

		} catch (TimeoutException e) {
			LogUtil.step("Element not present within timeout: " + locator);
			takeScreenshot();
			return false;

		} catch (StaleElementReferenceException e) {
			LogUtil.step("Element became stale while checking enabled state: " + locator);
			LogUtil.step("Page URL: " + driver.getCurrentUrl());
			LogUtil.step("Page Title: " + driver.getTitle());
			takeScreenshot();
			return false;

		} catch (Exception e) {
			LogUtil.error("isElementEnabled failed for locator: " + locator);
			LogUtil.error("Exception: " + e.getClass().getSimpleName());
			takeScreenshot();
			return false;
		}
	}

	protected boolean isElementNotEditable(WebElement element) {
		LogUtil.verify("Checking element is not editable: ", element);
		try {
			WaitUtil.waitForVisibility(driver, element);
			String editableAttr = element.getAttribute("contenteditable");
			if ("true".equalsIgnoreCase(editableAttr)) {
				return false;
			}
			try {
				WebElement parent = element.findElement(By.xpath("./ancestor::button"));
				if (parent.getAttribute("disabled") != null) {
					return true;
				}
			} catch (Exception ignored) {
			}
			String tag = element.getTagName();
			return !tag.matches("input|textarea");

		} catch (Exception e) {
			LogUtil.error("isElementNotEditable failed: " + e.getClass().getSimpleName());
			takeScreenshot();
			return true;
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

	public void navigateForward() {
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
		WaitUtil.waitForVisibility(driver, element);
		element.click();
		element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		element.sendKeys(Keys.DELETE);
	}

	private void focus(WebElement element) {
		WaitUtil.waitForVisibility(driver, element);
		((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);
	}

	// sendKeys(Keys.ENTER) is rejected on non-input elements like a div/p menu option.
	public void focusAndPressEnter(WebElement element) {
		LogUtil.action("Focusing element and pressing Enter: ", element);
		focus(element);
		new Actions(driver).sendKeys(Keys.ENTER).perform();
	}

	public void hoverOverElement(WebElement element) {
		LogUtil.action("Hovering over element: ", element);
		WaitUtil.waitForVisibility(driver, element);
		new Actions(driver).moveToElement(element).perform();
	}

	// Focuses, then reports whether focus landed - a tabindex-less element leaves activeElement on body.
	public boolean isElementFocusable(WebElement element) {
		LogUtil.verify("Checking if element can take keyboard focus: ", element);
		focus(element);
		return element.equals(driver.switchTo().activeElement());
	}

	protected String getComputedStyle(WebElement element, String property) {
		LogUtil.verify("Reading computed style '" + property + "' from element: ", element);
		return (String) ((JavascriptExecutor) driver)
				.executeScript("return getComputedStyle(arguments[0])[arguments[1]];", element, property);
	}

	protected String getBodyComputedStyle(String property) {
		return getComputedStyle(driver.findElement(By.tagName("body")), property);
	}

	protected boolean isElementWithinViewport(WebElement element) {
		LogUtil.verify("Checking if element is fully inside the viewport: ", element);
		WaitUtil.waitForVisibility(driver, element);
		return Boolean.TRUE.equals(((JavascriptExecutor) driver).executeScript(
				"var b=arguments[0].getBoundingClientRect();"
						+ "return b.top>=0 && b.left>=0 && b.bottom<=window.innerHeight && b.right<=window.innerWidth;",
				element));
	}

	// Tolerance covers the sub-pixel offset flex centring can leave on fractional viewport widths.
	protected boolean isElementHorizontallyCentred(WebElement element, int tolerancePx) {
		LogUtil.verify("Checking if element is horizontally centred: ", element);
		WaitUtil.waitForVisibility(driver, element);
		return Boolean.TRUE.equals(((JavascriptExecutor) driver).executeScript(
				"var b=arguments[0].getBoundingClientRect();"
						+ "return Math.abs((b.left+b.right)/2 - window.innerWidth/2) <= arguments[1];",
				element, tolerancePx));
	}

	// True when something else sits on top of the element's centre point, e.g. an overlay.
	protected boolean isElementCoveredAtCentre(WebElement element) {
		LogUtil.verify("Checking if element is covered by an overlay: ", element);
		return Boolean.TRUE.equals(((JavascriptExecutor) driver).executeScript(
				"var e=arguments[0];" + "var b=e.getBoundingClientRect();"
						+ "var t=document.elementFromPoint(b.left+b.width/2,b.top+b.height/2);"
						+ "return t!==null && t!==e && !e.contains(t);",
				element));
	}

	protected int getElementCount(By locator) {
		LogUtil.verify("Counting elements for: ", locator);
		return driver.findElements(locator).size();
	}

	public void scrollToEndPage() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	public void scrollToStartPage() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0);");
	}

	protected void takeScreenshot() {
		try {
			String base64Image = Screenshot.ClickScreenshot(driver);
			String html = "<p><img src='data:image/png;base64," + base64Image + "' width='900' height='450'/></p>";
			Reporter.log(html, true);
			logger.info("Screenshot captured.");
		} catch (IOException e) {
			logger.error("Failed to take screenshot", e);
		}
	}

	public void scrollIntoView(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (StaleElementReferenceException e) {
			LogUtil.step("Element stale while scrolling into view");
		}
	}

	public boolean isDisplayed(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getTimeout()));

			return wait.until(driver -> {
				try {
					WebElement el = driver.findElement(locator);
					return el.isDisplayed() && el.getSize().getHeight() > 0;
				} catch (StaleElementReferenceException | NoSuchElementException e) {
					return false;
				}
			});

		} catch (TimeoutException e) {
			LogUtil.step("Page URL: " + driver.getCurrentUrl());
			LogUtil.step("Page Title: " + driver.getTitle());
			return false;
		}
	}

	public boolean isTextPresent(By locator, String expectedText) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getTimeout()));

			return wait.until(ExpectedConditions
					.refreshed(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText)));
		} catch (TimeoutException e) {
			return false;
		} catch (StaleElementReferenceException e) {
			LogUtil.step("Text check failed due to stale element: " + locator);
			LogUtil.step("Page URL: " + driver.getCurrentUrl());
			LogUtil.step("Page Title: " + driver.getTitle());
			return false;
		}
	}

	private void jsClick(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			LogUtil.step("JS click executed successfully");
		} catch (Exception e) {
			LogUtil.error("JS click failed");
			takeScreenshot();
			throw e;
		}
	}

	public void waitScrollAndClick(By option) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(option));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
	}

	protected String getTextFromLocator(By locator) {
		WebElement element = driver.findElement(locator);
		waitForElementVisible(element);
		return element.getText();
	}

	protected String getTextFromAttribute(By locator, String attr) {
		WebElement element = driver.findElement(locator);
		LogUtil.action("Getting text from element for the " + attr + " attribute: ", element);
		waitForElementVisible(element);
		return element.getAttribute(attr);
	}

	protected WebElement waitAndFindElement(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getTimeout()));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

}