package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;

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
		submitCredentials(userName, password);
		if (isCredentialErrorDisplayed()) {
			submitCredentials(userName, password);
		}
	}

	private void submitCredentials(String userName, String password) {
		enterUserName(userName);
		enterPassword(password);
		clickOnLoginButton();
		try {
			new WebDriverWait(driver, Duration.ofSeconds(20)).until(d -> {
				String url = d.getCurrentUrl() == null ? "" : d.getCurrentUrl();
				return !url.contains("openid-connect") || !d.findElements(By.cssSelector("#input-error, .alert-error")).isEmpty();
			});
		} catch (TimeoutException ignored) {
		}
	}

	private boolean isCredentialErrorDisplayed() {
		return isElementDisplayedQuick(By.cssSelector("#input-error, .alert-error"), Duration.ofSeconds(2));
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
