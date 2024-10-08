package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PoliciesPage extends BasePage{	
	
	@FindBy(id = "title_back_icon")
	private WebElement policiesTitle;
	
	@FindBy(xpath = "//div[@class='flex flex-col items-center']")
	private WebElement policiesEmptyTable;
	
	@FindBy(id = "policies_request_policy_btn")
	private WebElement requestPolicyButton;
	
	@FindBy(id = "request_policy_partner_id")
	private WebElement partnerIdDropdown;
	
	@FindBy(id = "request_policies_policy_name")
	private WebElement policyNameDropdown;
	
	@FindBy(id = "request_policies_policy_name_search_input")
	private WebElement searchBoxForPolicyName;
	
	@FindBy(id = "request_policy_comment_box")
	private WebElement commentsTextBox;
	
	@FindBy(id = "request_policies_form_submit_btn")
	private WebElement submitButton;
	
	@FindBy(xpath = "//*[text()='Policy Submitted Successfully!']")
	private WebElement policySubmittedSuccessfully;
	
	@FindBy(xpath = "//*[text()='No Data Available.']")
	private WebElement noDataAvailableText;
	
	public PoliciesPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPoliciesPageDisplayed() {
		return isElementDisplayed(policiesTitle);
	}
	
	public  void clickOnRequestPolicyButton() {
		clickOnElement(requestPolicyButton);
	}
	
	public  void selectPartnerIdDropdown(String value) {
		clickOnElement(partnerIdDropdown);
		clickOnElement(driver.findElement(By.xpath("(//*[contains(text(),'"+value+"')])[2]")));
	}
	
	public  void selectPolicyNameDropdown(String value) {
		clickOnElement(policyNameDropdown);
	}
	
	public void enterComments(String comments) {
		enter(commentsTextBox, comments);
	}
	
	public boolean isPartnerIdDropdownDisplayed() {
		return isElementDisplayed(partnerIdDropdown);
	}
	
	public boolean isPolicyNameDropdownDisplayed() {
		return isElementDisplayed(policyNameDropdown);
	}
	
	public boolean isSubmitButtonDisplayed() {
		return isElementDisplayed(submitButton);
	}
	
	public  void clickSubmitButton() {
		clickOnElement(submitButton);
	}
	
	public boolean isPolicySubmittedSuccessfullyDisplayed() {
		return isElementDisplayed(policySubmittedSuccessfully);
	}
	
	public boolean isNoDataAvailableTextDisplayed() {
		return isElementDisplayed(noDataAvailableText);
	}
	
	
	public  void searchInPolicyName(String value) {
		enter(searchBoxForPolicyName,value);
	}
	
	public boolean isPoliciesEmptyTableDisplayed() {
		return isElementDisplayed(policiesEmptyTable);
	}
	
	public boolean isPoliciesEmptyTableEnabled() {
		return isElementEnabled(requestPolicyButton);
	}
	
}
