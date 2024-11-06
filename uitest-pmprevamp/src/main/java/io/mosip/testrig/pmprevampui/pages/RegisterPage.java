package io.mosip.testrig.pmprevampui.pages;

import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePage {

	@FindBy(id = "kc-page-title")
	private WebElement registerPageTitle;

	@FindBy(id = "firstName")
	private WebElement firstNameTextbox;

	@FindBy(id = "lastName")
	private WebElement lastNameTextbox;

	@FindBy(id = "organizationName")
	private WebElement organizationNameTextbox;

	@FindBy(id = "user.attributes.partnerType")
	private WebElement partnerTypeDropdown;

	@FindBy(id = "address")
	private WebElement addressTextbox;

	@FindBy(id = "email")
	private WebElement emailTextbox;

	@FindBy(id = "phoneNumber")
	private WebElement phoneNumberTextbox;

	@FindBy(id = "user.attributes.langCode")
	private WebElement notificationLanguageDropdown;

	@FindBy(id = "username")
	private WebElement usernameTextbox;

	@FindBy(id = "password")
	private WebElement passwordTextbox;

	@FindBy(id = "password-confirm")
	private WebElement passwordConfirmTextbox;

	@FindBy(xpath = "//input[@type='submit']")
	private WebElement submitButton;

	public RegisterPage(WebDriver driver) {
		super(driver);
	}

	public boolean isRegisterPageTitleDisplayed() {
		return isElementDisplayed(registerPageTitle);
	}

	public boolean isFirstNameTextBoxDisplayed() {
		return isElementDisplayed(firstNameTextbox);
	}

	public boolean isLastNameTextBoxDisplayed() {
		return isElementDisplayed(lastNameTextbox);
	}

	public boolean isOrganizationNameDisplayed() {
		return isElementDisplayed(organizationNameTextbox);
	}

	public boolean isPartnerTypeDropdownDisplayed() {
		return isElementDisplayed(partnerTypeDropdown);
	}

	public void selectPartnerTypeDropdown() {
		dropdownByIndex(partnerTypeDropdown, 2);
	}

	public boolean isAddressTextBoxDisplayed() {
		return isElementDisplayed(addressTextbox);
	}

	public boolean isEmailTextBoxDisplayed() {
		return isElementDisplayed(emailTextbox);
	}

	public boolean isPhoneNumberTextboxDisplayed() {
		return isElementDisplayed(phoneNumberTextbox);
	}

	public boolean isNotificationLanguageDropdownDisplayed() {
		return isElementDisplayed(notificationLanguageDropdown);
	}

	public void selectNotificationLanguageDropdown() {
		dropdownByIndex(notificationLanguageDropdown, 0);
	}

	public boolean isUsernameTextBoxDisplayed() {
		return isElementDisplayed(usernameTextbox);
	}

	public boolean isPasswordTextBoxDisplayed() {
		return isElementDisplayed(passwordTextbox);
	}

	public boolean isPasswordConfirmTextBoxDisplayed() {
		return isElementDisplayed(passwordConfirmTextbox);
	}

	public boolean isSubmitButtonDisplayed() {
		return isElementDisplayed(submitButton);
	}

	public void enterFirstName(String firstName) {
		enter(firstNameTextbox, firstName);
	}

	public void enterLastName(String lastName) {
		enter(lastNameTextbox, lastName);
	}

	public void enterOrganizationName(String organizationName) {
		enter(organizationNameTextbox, organizationName);
	}

	public void enterAddress(String address) {
		enter(addressTextbox, address);
	}

	public void enterEmail(String email) {
		enter(emailTextbox, email);
	}

	public void enterPhone(String phone) {
		enter(phoneNumberTextbox, phone);
	}

	public void enterUsername(String username) {
		enter(usernameTextbox, username);
	}

	public void enterPassword(String password) {
		enter(passwordTextbox, password);
	}

	public void enterPasswordConfirm(String passwordConfirm) {
		enter(passwordConfirmTextbox, passwordConfirm);
	}

	public DashboardPage clickSubmitButton() {
		clickOnElement(submitButton);
		return new DashboardPage(driver);
	}

	public void openNewTab() {
		((JavascriptExecutor) driver).executeScript("window.open('https://pmp.dev1.mosip.net/ ')");
		Set<String> allWindowHandles = driver.getWindowHandles();
		System.out.println(allWindowHandles);
		if (allWindowHandles.size() >= 2) {
			String secondWindowHandle = allWindowHandles.toArray(new String[0])[1];
			String firstWindowHandle = allWindowHandles.toArray(new String[0])[0];
			// Switch to the second window
			driver.switchTo().window(secondWindowHandle);
		}
	}

	public void openPreviousTab() {
		Set<String> allWindowHandles = driver.getWindowHandles();
		System.out.println(allWindowHandles);

		String firstWindowHandle = allWindowHandles.toArray(new String[0])[0];
		// Switch to the second window
		driver.switchTo().window(firstWindowHandle);
	}

	public void openRevampInNewTab() {
		((JavascriptExecutor) driver).executeScript("window.open('https://pmp-new.dev1.mosip.net/ ')");
		Set<String> allWindowHandles = driver.getWindowHandles();
		System.out.println(allWindowHandles);
		if (allWindowHandles.size() >= 2) {
			String secondWindowHandle = allWindowHandles.toArray(new String[0])[1];
			String firstWindowHandle = allWindowHandles.toArray(new String[0])[0];
			// Switch to the second window
			driver.switchTo().window(secondWindowHandle);
		}
	}

	public void refreshThePage() {
		driver.navigate().refresh();
	}

	public void CloseTheTab() {
		driver.close();
	}
}
