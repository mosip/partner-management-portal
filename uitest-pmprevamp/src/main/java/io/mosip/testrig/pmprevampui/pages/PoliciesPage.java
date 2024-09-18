package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PoliciesPage extends BasePage{	
	
	@FindBy(xpath = "//*[text()='Policies']")
	private WebElement policiesTitle;
	
	@FindBy(xpath = "//*[text()='Request Policy']")
	private WebElement requestPolicyButton;
	
	@FindBy(xpath = "(//div[@class='relative w-full'])[1]")
	private WebElement partnerIdDropdown;
	
	@FindBy(xpath = "(//div[@class='relative w-full'])[2]")
	private WebElement policyNameDropdown;
	
	@FindBy(xpath = "//div[@class='flex flex-col w-full']/textarea")
	private WebElement commentsTextBox;
	
	@FindBy(xpath = "//*[text()='Submit']")
	private WebElement submitButton;
	
	@FindBy(xpath = "//*[text()='Policy Submitted Successfully!']")
	private WebElement policySubmittedSuccessfully;
	
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
		dropdownByIndex(partnerIdDropdown,0);
		clickOnElement(driver.findElement(By.xpath("(//*[contains(text(),'"+value+"')])[2]")));
	}
	
	public  void selectPolicyNameDropdown(String value) {
		dropdownByIndex(policyNameDropdown,0);
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

}
