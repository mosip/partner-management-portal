package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmprevampui.utility.BaseClass;

public class PolicyGroupPage extends BasePage{
	
	@FindBy(id = "create_policy_group_btn")
	private WebElement createPolicyGroupButton;
	
	@FindBy(xpath = "//h1[text()='Create Policy Group']")
	private WebElement createPolicyGroupTitle;
	
	@FindBy(id = "sub_title_home_btn")
	private WebElement subTitleHomeButton;
	
	@FindBy(id = "sub_title_btn")
	private WebElement subTitleButton;
	
	@FindBy(xpath = "//p[text()='All fields marked with * are mandatory.']")
	private WebElement policyGroupCreationFormSubTitle;
	
	@FindBy(xpath = "//label[text()='Policy Group Name']")
	private WebElement policyGroupNameLabel;
	
	@FindBy(id = "policy_group_name")
	private WebElement policyGroupNameTextbox;
	
	@FindBy(xpath = "//label[text()='Policy Group Description']")
	private WebElement policyGroupDescriptionLabel;
	
	@FindBy(id = "policy_group_description")
	private WebElement policyGroupNameDescriptionTextbox;
	
	@FindBy(id = "createPolicy_clear_form")
	private WebElement clearFormButton;
	
	@FindBy(id = "createPolicy_cancel_btn")
	private WebElement cancelButton;
	
	@FindBy(id = "createPolicy_submit_btn")
	private WebElement submitButton;
	
	@FindBy(xpath = "//h1[text()='Policy Group created successfully!']")
	private WebElement titleOfSuccessMessage;
	
	@FindBy(id = "confirmation_go_back_btn")
	private WebElement successGoBackButton;
	
	@FindBy(id = "confirmation_home_btn")
	private WebElement successHomeButton;
	
	@FindBy(xpath = "//h1[text()='Policy group already exists with this name']")
	private WebElement sameNamePolicyGroupAlreadyExistErrorMessage;
	
	@FindBy(id = "error_close_btn")
	private WebElement errorCloseButton;
	
	@FindBy(xpath = "//p[text()='Your changes will be lost, are you sure you want to proceed?']")
	private WebElement browserBackConfirmationMessage;
	
	@FindBy(id = "block_message_cancel")
	private WebElement browserBackCancelButton;
	
	@FindBy(id = "block_messsage_proceed")
	private WebElement browserBackProceedButton;
	
	public PolicyGroupPage(WebDriver driver) {
		super(driver);
	}
	
	public void clickOnCreatePolicyGroupButton() {
		clickOnElement(createPolicyGroupButton);
	}

	public boolean isSubTitleHomeDisplayed() {
		return isElementDisplayed(subTitleHomeButton);
	}
	
	public boolean isSubTitleDisplayed() {
		return isElementDisplayed(subTitleButton);
	}
	
	public boolean isCreatePolicyGroupTitleDisplayed() {
		return isElementDisplayed(createPolicyGroupTitle);
	}
	
	public boolean isPolicyGroupCreationFormSubTitleDisplayed() {
		return isElementDisplayed(policyGroupCreationFormSubTitle);
	}
	
	public boolean isPolicyGroupNameTextboxDisplayed() {
		return isElementDisplayed(policyGroupNameTextbox);
	}
	
	public boolean isPolicyGroupNameDescriptionTextboxDisplayed() {
		return isElementDisplayed(policyGroupNameDescriptionTextbox);
	}
	
	public boolean isClearFormButtonAvailable() {
		return isElementDisplayed(clearFormButton);
	}
	
	public boolean isCancelButtonAvailable() {
		return isElementDisplayed(cancelButton);
	}
	
	public boolean isSubmitButtonAvailable() {
		return isElementDisplayed(submitButton);
	}
	
	public void clickOnSubmitButton() {
		clickOnElement(submitButton);
	}
	
	public void clickOnCancelButton() {
		clickOnElement(cancelButton);
	}
	
	public void clickOnClearFormButton() {
		clickOnElement(clearFormButton);
	}
	
	public void enterPolicyGroupName(String value) {
		clickOnElement(policyGroupNameTextbox);
	}
	
	public void enterPolicyGroupNameDescription(String value) {
		clickOnElement(policyGroupNameDescriptionTextbox);
	}
	
	public boolean isPolicyGroupSuccessMessageDisplayed() {
		return isElementDisplayed(titleOfSuccessMessage);
	}
	
	public boolean isTitleOfSuccessMessageDisplayed() {
		return isElementDisplayed(titleOfSuccessMessage);
	}
	
	public boolean isSuccessGoBackButtonAvailable() {
		return isElementDisplayed(successGoBackButton);
	}
	
	public boolean isSuccessHomeButtonAvailable() {
		return isElementDisplayed(successHomeButton);
	}
	
	public void clickOnSuccessGoBackButton() {
		clickOnElement(successGoBackButton);
	}
	
	public void clickOnSuccessHomeButton() {
		clickOnElement(successHomeButton);
	}
	
	public boolean isSubmitButtonEnabled() {
		return isElementDisplayed(submitButton);
	}
	
	public boolean isSameNamePolicyGroupAlreadyExistMessageDisplayed() {
		return isElementDisplayed(sameNamePolicyGroupAlreadyExistErrorMessage);
	}
	
	public void clickOnErrorCloseButton() {
		clickOnElement(errorCloseButton);
	}
	
	public boolean isBrowserBackConfirmationPopupDisplayed() {
		return isElementDisplayed(browserBackConfirmationMessage);
	}
	
	public boolean isBrowserBackProceedButtonAvailable() {
		return isElementDisplayed(browserBackProceedButton);
	}
	
	public boolean isBrowserBackCancelButtonAvailable() {
		return isElementDisplayed(browserBackCancelButton);
	}
	
	public void navigateBackDefaultButton() {
		driver.navigate().back();
	}
	
	public void clickOnBrowserBackCancelButton() {
		clickOnElement(browserBackCancelButton);
	}
	
	public void clickOnBrowserBackProceedButton() {
		clickOnElement(browserBackProceedButton);
	}

}
