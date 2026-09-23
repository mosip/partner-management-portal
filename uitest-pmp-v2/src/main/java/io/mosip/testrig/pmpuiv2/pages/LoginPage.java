package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.mosip.testrig.pmpuiv2.utility.LogUtil;

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

	private static final By LOGIN_REJECTED = By
			.xpath("//*[contains(text(),'Invalid username or password')]");

	private String lastUsername;
	private String lastPassword;

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
		lastUsername = value;
		enter(usernameTextBox, value);
	}

	public void enterPassword(String value) {
		lastPassword = value;
		enter(passwordTextBox, value);
	}

	public void clickOnLoginButton() {
		clickOnElement(LoginButton);
	}

	public void clickOnLoginButtonRetryingRejection() {
		submitLogin();
		retryLoginIfRejected();
	}

	public void retryLoginIfRejected() {
		for (int attempt = 0; attempt < 2 && lastUsername != null && lastPassword != null; attempt++) {
			if (!isLoginRejected()) {
				return;
			}
			LogUtil.step("Sign in was rejected. Retrying login.");
			pauseBeforeLoginRetry();
			enter(usernameTextBox, lastUsername);
			enter(passwordTextBox, lastPassword);
			submitLogin();
		}
	}

	private void submitLogin() {
		clickOnElement(LoginButton);
	}

	private boolean isLoginRejected() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(8)).until(d -> leftLoginPage(d)
					|| d.findElements(LOGIN_REJECTED).stream().anyMatch(this::isInvalidCredentialMessage));
		} catch (Exception ignored) {
			return false;
		}
		if (leftLoginPage(driver)) {
			return false;
		}
		return driver.findElements(LOGIN_REJECTED).stream().anyMatch(this::isInvalidCredentialMessage);
	}

	private boolean leftLoginPage(WebDriver currentDriver) {
		String url = currentDriver.getCurrentUrl();
		if (url == null) {
			return false;
		}
		return !url.contains("/auth/") && !url.contains("openid-connect") && !url.contains("login-actions");
	}

	private boolean isInvalidCredentialMessage(WebElement element) {
		try {
			String text = element.getText();
			return element.isDisplayed() && text != null && text.toLowerCase().contains("invalid");
		} catch (Exception e) {
			return false;
		}
	}

	private void pauseBeforeLoginRetry() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void login(String userName, String password) {
		enterUserName(userName);
		enterPassword(password);
		clickOnLoginButton();
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
