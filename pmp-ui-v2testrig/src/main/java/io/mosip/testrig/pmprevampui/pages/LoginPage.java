package io.mosip.testrig.pmpuiv2.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	@FindBy(id = "kc-page-title")
	private WebElement loginPageTitle;

	@FindBy(xpath = "//*[contains(text(), 'Register')]")
	private WebElement registerButton;

	@FindBy(id = "username")
	private WebElement usernameTextBox;

	@FindBy(id = "password")
	private WebElement passwordTextBox;

	@FindBy(xpath = "//input[@name=\'login\']")
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
		clickOnElement(LoginButton);
	}

}
