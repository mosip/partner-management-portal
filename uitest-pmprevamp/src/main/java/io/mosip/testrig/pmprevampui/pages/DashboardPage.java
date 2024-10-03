package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage{

	@FindBy(xpath = "//div[@class='flex items-center ml-1']/button")
	private WebElement profileDropdown;
	
	@FindBy(xpath = "//button[contains(text(), 'Logout')]")
	private WebElement logoutButton;
	
	@FindBy(xpath = "//*[text()='Select Policy Group']")
	private WebElement selectPolicyGroupPopUp;
	
	@FindBy(xpath = "//div[@class='relative w-full']/button")
	private WebElement selectPolicyGroupDropdown;
	
	@FindBy(xpath = "//*[text()='Submit']")
	private WebElement submitButton;
	
	@FindBy(xpath = "//*[@class='min-h-2']")
	private WebElement value;
	
	@FindBy(xpath = "//*[text()='Terms and Conditions']")
	private WebElement termsAndConditionsPopUp;
	
	@FindBy(id = "default-checkbox")
	private WebElement checkbox;
	
	@FindBy(xpath = "//*[text()='Proceed']")
	private WebElement proceedButton;
	
	@FindBy(xpath = "//*[text()='Partner Certificate']")
	private WebElement partnerCertificateTitle;
	
	@FindBy(xpath = "//*[text()='Policies']")
	private WebElement policiesTitle;
	
	public DashboardPage(WebDriver driver) {
		super(driver);
	}
	
	public  void clickOnProfileDropdown() {
		clickOnElement(profileDropdown);
	}
	
	public  LoginPage clickOnLogoutButton() {
		clickOnElement(logoutButton);
		return new LoginPage(driver);
	}
	
	public boolean isLogoutButtonDisplayed() {
		return isElementDisplayed(logoutButton);
	}
	
	public boolean isSelectPolicyGroupPopUpDisplayed() {
		return isElementDisplayed(selectPolicyGroupPopUp);
	}
	
	public boolean isSubmitButtonSelectPolicyGroupPopUpDisplayed() {
		return isElementDisplayed(submitButton);
	}
	
	public void selectSelectPolicyGroupDropdown() {
		clickOnElement(selectPolicyGroupDropdown);
		clickOnElement(value);
	}
	
	public  void clickOnSubmitButton() {
		clickOnElement(submitButton);
	}
	
	public boolean isTermsAndConditionsPopUppDisplayed() {
		return isElementDisplayed(termsAndConditionsPopUp);
	}
	
	public  void clickOnCheckbox() {
		clickOnElement(checkbox);
	}
	
	public boolean isProceedButtonDisplayed() {
		return isElementDisplayed(proceedButton);
	}
	
	public  void clickOnProceedButton() {
		clickOnElement(proceedButton);
	}
	
	public boolean isPartnerCertificateTitleDisplayed() {
		return isElementDisplayed(partnerCertificateTitle);
	}
	
	public boolean isPoliciesTitleDisplayed() {
		return isElementDisplayed(policiesTitle);
	}
	
	public  PoliciesPage clickOnPoliciesTitle() {
		clickOnElement(policiesTitle);
		return new PoliciesPage(driver);
	}
	
	public  PartnerCertificatePage clickOnPartnerCertificateTitle() {
		clickOnElement(partnerCertificateTitle);
		return new PartnerCertificatePage(driver);
	}
	
}
