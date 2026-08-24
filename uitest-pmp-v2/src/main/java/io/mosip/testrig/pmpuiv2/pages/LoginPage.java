package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.mosip.testrig.pmpuiv2.utility.LogUtil;

public class LoginPage extends BasePage {

	@FindBy(id = "kc-page-title")
	private WebElement loginPageTitle;

	@FindBy(xpath = "//*[contains(text(), 'Register') or contains(text(), \"S'enregistrer\") or contains(text(), 'Inscription') or contains(text(), 'تسجيل')]")
	private WebElement registerButton;

	@FindBy(id = "username")
	private WebElement usernameTextBox;

	@FindBy(id = "password")
	private WebElement passwordTextBox;

	@FindBy(xpath = "//input[@name='login']")
	private WebElement LoginButton;

	@FindBy(id = "kc-current-locale-link")
	private WebElement currentLocaleLink;

	@FindBy(id = "kc-locale-dropdown")
	private WebElement localeDropdown;

	@FindBy(xpath = "//*[@id='kc-locale']//*[contains(normalize-space(.),'Français') or contains(normalize-space(.),'Francais') or normalize-space(.)='French' or contains(@href,'kc_locale=fr')]")
	private WebElement frenchLanguageOption;

	@FindBy(xpath = "//*[@id='kc-locale']//*[contains(normalize-space(.),'العربية') or contains(normalize-space(.),'Arabic') or contains(normalize-space(.),'عربي') or contains(@href,'kc_locale=ar')]")
	private WebElement arabicLanguageOption;

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
		clickOnElement(LoginButton);
	}

	public void login(String userName, String password) {
		enterUserName(userName);
		enterPassword(password);
		clickOnLoginButton();
	}

	public boolean isLanguageDropdownDisplayed() {
		return isElementDisplayed(currentLocaleLink) || isElementDisplayed(localeDropdown);
	}

	public String getCurrentLocaleText() {
		return getTextFromLocator(currentLocaleLink).trim();
	}

	public void selectFrenchLanguage() {
		selectLanguage("French", frenchLanguageOption, this::isFrenchLocaleText);
	}

	public void selectArabicLanguage() {
		selectLanguage("Arabic", arabicLanguageOption, this::isArabicLocaleText);
	}

	public boolean isFrenchLanguageSelected() {
		return isLocaleSelected(this::isFrenchLocaleText);
	}

	public boolean isArabicLanguageSelected() {
		return isLocaleSelected(this::isArabicLocaleText);
	}

	private void selectLanguage(String languageName, WebElement languageOption, Predicate<String> localeMatcher) {
		LogUtil.step("Select " + languageName + " language from sign-in page language dropdown");
		if (!isLanguageDropdownDisplayed()) {
			throw new RuntimeException("Language dropdown is not displayed on the sign-in page");
		}

		String currentLocale = getCurrentLocaleText();
		if (localeMatcher.test(currentLocale)) {
			LogUtil.step(languageName + " language is already selected on sign-in page: " + currentLocale);
			return;
		}

		clickOnElement(currentLocaleLink);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement languageLink = wait.until(ExpectedConditions.elementToBeClickable(languageOption));
		String href = languageLink.getAttribute("href");
		if (href != null && !href.isBlank() && !href.equals("#")) {
			driver.navigate().to(href);
		} else {
			languageLink.click();
		}

		wait.until(d -> {
			List<WebElement> localeLinks = d.findElements(By.id("kc-current-locale-link"));
			if (localeLinks.isEmpty()) {
				return false;
			}
			return localeMatcher.test(localeLinks.get(0).getText());
		});
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("kc-page-title")));
		LogUtil.step(languageName + " language selected. Current locale: " + getCurrentLocaleText());
	}

	private boolean isLocaleSelected(Predicate<String> localeMatcher) {
		try {
			return new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> localeMatcher.test(getCurrentLocaleText()));
		} catch (TimeoutException e) {
			return false;
		}
	}

	private boolean isFrenchLocaleText(String text) {
		if (text == null) {
			return false;
		}
		String normalized = text.trim().toLowerCase(Locale.ROOT);
		return normalized.contains("français") || normalized.contains("francais") || normalized.equals("french")
				|| normalized.startsWith("fr");
	}

	private boolean isArabicLocaleText(String text) {
		if (text == null) {
			return false;
		}
		String normalized = text.trim().toLowerCase(Locale.ROOT);
		return text.contains("العربية") || text.contains("عربي") || normalized.contains("arabic")
				|| normalized.startsWith("ar");
	}

}
