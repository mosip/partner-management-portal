package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

	@FindBy(id = "kc-page-title")
	private WebElement loginPageTitle;
	
	@FindBy(xpath = "//*[contains(text(), 'Register')]")
	private WebElement registerButton;
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public  RegisterPage clickRegisterButton() {
		clickOnElement(registerButton);
		return new RegisterPage(driver);
	}
	
	public boolean isLoginPageDisplayed() {
		return isElementDisplayed(loginPageTitle);
	}
	
}
