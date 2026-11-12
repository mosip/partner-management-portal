package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {

	private static final Logger logger = Logger.getLogger(LoginPage.class);

	@FindBy(id = "kc-page-title")
	private WebElement loginPageTitle;

	@FindBy(css = "#kc-registration a")
	private WebElement registerButton;

	@FindBy(id = "username")
	private WebElement usernameTextBox;

	@FindBy(id = "password")
	private WebElement passwordTextBox;

	@FindBy(xpath = "//input[@name='login']")
	private WebElement LoginButton;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public RegisterPage clickRegisterButton() {
		clickOnElement(registerButton);
		return new RegisterPage(driver);
	}

	public boolean isLoginPageDisplayed() {
		return isElementDisplayed(loginPageTitle);
	}

	public void enterUserName(String value) {
		enter(usernameTextBox, value);
	}

	public void enterPassword(String value) {
		enter(passwordTextBox, value);
	}

	public void clickOnLoginButton() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", LoginButton);
		clickOnElement(LoginButton);
	}

	public void login(String userName, String password) {
		enterUserName(userName);
		enterPassword(password);
		clickOnLoginButton();
		assertLoginSucceeded(userName);
	}

	public void assertLoginSucceeded(String userName) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(20)).until(d -> credentialErrorShown(d) || loginPageLeft(d));
		} catch (TimeoutException e) {
			throw new IllegalStateException("Login did not finish for user " + userName, e);
		}
		if (credentialErrorShown(driver)) {
			String message = driver.findElement(By.cssSelector("#input-error, .alert-error")).getText();
			throw new IllegalStateException("Login failed for user " + userName
					+ (message == null || message.isBlank() ? "" : ": " + message.trim()));
		}
	}

	private boolean credentialErrorShown(WebDriver currentDriver) {
		List<WebElement> errors = currentDriver.findElements(By.cssSelector("#input-error, .alert-error"));
		return !errors.isEmpty() && errors.get(0).isDisplayed();
	}

	private boolean loginPageLeft(WebDriver currentDriver) {
		String url = currentDriver.getCurrentUrl();
		return url != null && !url.contains("openid-connect");
	}

	public void selectLanguage(String kcLocale) {
		if (kcLocale == null || kcLocale.isBlank() || kcLocale.equalsIgnoreCase("eng")) {
			return;
		}
		isLoginPageDisplayed();
		try {
			WebElement languageOption = driver
					.findElement(By.cssSelector("a[href*='kc_locale=" + kcLocale + "']"));
			String targetUrl = languageOption.getAttribute("href");
			driver.get(targetUrl);
			isLoginPageDisplayed();
		} catch (NoSuchElementException e) {
			logger.error("Language selector for '" + kcLocale + "' not found on login page.", e);
			takeScreenshot();
			throw new IllegalStateException("Configured login language is unavailable: " + kcLocale, e);
		}
	}

}
