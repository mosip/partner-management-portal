package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmprevampui.utility.TestRunner;

public class AuthPolicyPage extends BasePage {
	
	
	@FindBy(id = "create_auth_policy_btn")
	private WebElement createAuthPolicyButton;
	
	@FindBy(id = "policy_group_dropdown")
	private WebElement policyGroupDropdown;
	
	@FindBy(id = "policy_group_dropdown_search_input")
	private WebElement policyGroupDropdownSearchInput;
	
	@FindBy(id = "policy_group_dropdown_option1")
	private WebElement policyGroupDropdownOption1;
	
	@FindBy(id = "policy_name_box")
	private WebElement policyNameBox;
	
	@FindBy(id = "policy_description_box")
	private WebElement policyDescriptionBox;
	
	@FindBy(id = "fileInput")
	private WebElement uploadFile;
	
	@FindBy(id = "create_policy_form_submit_btn")
	private WebElement createPolicyFormSubmitButton;

	@FindBy(id = "confirmation_go_back_btn")
	private WebElement goBackButton;
	
	@FindBy(id = "filter_btn")
	private WebElement filterButton;
	
	@FindBy(id = "policy_name_filter")
	private WebElement policyNameFilter;
	
	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilterButton;
	
	@FindBy(id = "policy_name_filter_option1")
	private WebElement policyNameFilterOption1;
	
	@FindBy(id = "policies_list_view1")
	private WebElement policiesListViewElipsisButton;
	
	@FindBy(id = "policy_publish_btn")
	private WebElement policyPublishButton;
	
	@FindBy(id = "publish_policy_button")
	private WebElement publishPolicyButton;
	
	@FindBy(id = "success_msg_close_icon")
	private WebElement successMsgCloseButton;
	
	@FindBy(id = "publish_policy_close_button")
	private WebElement publishPolicyCloseButton;
	
	@FindBy(id = "policy_group_filter")
	private WebElement policyGroupFilterBox;
	
	@FindBy(id = "policy_deactivate_btn")
	private WebElement policyDeactivateButton;
	
	@FindBy(id = "deactivate_policy_group__confirm_btn")
	private WebElement deactivateConfirmButton;

	public AuthPolicyPage(WebDriver driver) {
		super(driver);
		
	}
	
	public void clickOnCreateAuthPolicyButton() {
		clickOnElement(createAuthPolicyButton);
	}
	
	public void selectpolicyGroupDropdown(String value) {
		clickOnElement(policyGroupDropdown);
		enter(policyGroupDropdownSearchInput,value);
		clickOnElement(policyGroupDropdownOption1);
	}
	
	public void enterPolicyName(String val) {
		enter(policyNameBox,val);
	}
	
	public void enterpolicyDescription(String val) {
		enter(policyDescriptionBox,val);
	}
	
	public void uploadPolicyData() {
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_revamp_cert\\policyData.json");
	}
	
	public void clickOnCreatePolicyFormSubmitButton() {
		clickOnElement(createPolicyFormSubmitButton);
	}
	
	public void clickOnGoBackButton() {
		clickOnElement(goBackButton);
	}
	
	public void clickOnFilterButton() {
		clickOnElement(filterButton);
	}
	
	public boolean isFilterButtonButtonEnabled() {
		return isElementEnabled(filterButton);
	}
	
	public void enterPendingPolicyNameInFilter(String value) {
		enter(policyNameFilter,value);
	}
	
	public void clickOnPolicyNameFilter() {
		clickOnElement(policyNameFilter);
		clickOnElement(policyNameFilterOption1);
	}
	
	public void clickOnApplyFilterButton() {
		clickOnElement(applyFilterButton);
	}
	
	public void clickOnPoliciesListViewElipsisButton() {
		clickOnElement(policiesListViewElipsisButton);
	}
	
	public void clickOnPolicyPublishButton() {
		clickOnElement(policyPublishButton);
	}
	
	public void clickOnPublishPolicyButton() {
		clickOnElement(publishPolicyButton);
	}
	
	public void clickOnSuccessMsgCloseButton() {
		clickOnElement(successMsgCloseButton);
	}
	
	public void clickOnPublishPolicyCloseButton() {
		clickOnElement(publishPolicyCloseButton);
	}
	
	public void enterpolicyGroupFilterBox(String val) {
		enter(policyGroupFilterBox, val);
	}
	
	public void clickOnDeactivateButton() {
		clickOnElement(policyDeactivateButton);
	}
	
	public void clickOnDeactivateConfirmButton() {
		clickOnElement(deactivateConfirmButton);
	}

}
