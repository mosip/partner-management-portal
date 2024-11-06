package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ApiKeyPage extends BasePage{	
	
	@FindBy(xpath = "Enter a name for API key")
	private WebElement enterNameOfApiKeyTextBox;
	
	@FindBy(xpath = "generate_submit_btn")
	private WebElement submitButton;
	
	@FindBy(id = "generate_partner_id")
	private WebElement partnerIdDropdown;

	@FindBy(id = "generate_policy_name")
	private WebElement policyNameDropdown;
	
	@FindBy(id = "generate_api_key_btn")
	private WebElement generateAPIKey;
	
	@FindBy(xpath = "//*[contains(text(), 'Partner ID')]")
	private WebElement partnerIDHeaderText;
	
	@FindBy(xpath = "//*[contains(text(), 'Policy Group')]")
	private WebElement PolicyGroupHeaderText;
	
	@FindBy(xpath = "//*[contains(text(), 'Policy Name')]")
	private WebElement PolicyNameHeaderText;
	
	@FindBy(xpath = "//*[contains(text(), 'API Key Name')]")
	private WebElement ApiKeyHeaderText;
	
	@FindBy(xpath = "//*[contains(text(), 'Created Date')]")
	private WebElement CreatedDateHeaderText;
	
	@FindBy(xpath = "//*[contains(text(), 'Status')]")
	private WebElement StatusHeaderText;
	
	@FindBy(xpath = "//*[contains(text(), 'Action')]")
	private WebElement ActionHeaderText;
	
	@FindBy(id = "generate_partner_id_option1")
	private WebElement generatePartnerIdOption1;
	
	@FindBy(id = "copy_id_btn")
	private WebElement copyIdButton;
	
	
	
	
	public ApiKeyPage(WebDriver driver) {
		super(driver);
	}
	
	public  void enterNameOfApiKeyTextBox(String value) {
		enter(enterNameOfApiKeyTextBox,value);
	}
	
	public  void selectPartnerIdDropdown(String value) {
		 clickOnElement(partnerIdDropdown);
		 clickOnElement(generatePartnerIdOption1);
	}
	
	public boolean isPartnerIdDropdownDisplayed() {
		return isElementDisplayed(partnerIdDropdown);
	}
	
	public boolean isPolicyNameDropdownDisplayed() {
		return isElementDisplayed(policyNameDropdown);
	}
	
	public  void selectPolicyNameDropdown(String value) {
		dropdownByIndex(policyNameDropdown,0);
	}
	
	public boolean isGenerateAPIKeyDisplayed() {
		return isElementDisplayed(generateAPIKey);
	}
	
	public void ClickOnAPIKeyDisplayed() {
		 clickOnElement(generateAPIKey);
	}
	
	public void ClickOnSubmitButton() {
		 clickOnElement(submitButton);
	}
	
	public boolean isPartnerIDHeaderTextDisplayed() {
		return isElementDisplayed(partnerIDHeaderText);
	}
	
	public boolean isPolicyGroupHeaderTextDisplayed() {
		return isElementDisplayed(PolicyGroupHeaderText);
	}
	
	public boolean isPolicyNameHeaderTextDisplayed() {
		return isElementDisplayed(PolicyNameHeaderText);
	}
	
	public boolean isApiKeyHeaderTextDisplayed() {
		return isElementDisplayed(ApiKeyHeaderText);
	}
	
	public boolean isCreatedDateHeaderTextDisplayed() {
		return isElementDisplayed(CreatedDateHeaderText);
	}
	
	public boolean isStatusHeaderTextDisplayed() {
		return isElementDisplayed(StatusHeaderText);
	}
	
	public boolean isActionHeaderTextDisplayed() {
		return isElementDisplayed(ActionHeaderText);
	}
		
}
