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
	
	@FindBy(id = "policies_auth_policy_tab")
	private WebElement authPolicyTab;
	
	@FindBy(id = "policies_data_share_policy_tab")
	private WebElement dataSharePolicyTab;
	
	@FindBy(xpath = "//h1[text()='Policies']")
	private WebElement titleOfPage;
	
	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;
	
	@FindBy(xpath = "//p[text()='List of Policy Groups']")
	private WebElement subtitleOfPage;
	
	@FindBy(xpath = "//div[text()='Policy Group ID']")
	private WebElement policyGroupIdHeader;
	
	@FindBy(xpath = "//div[text()='Policy Group Name']")
	private WebElement policyGroupNameHeader;
	
	@FindBy(xpath = "//div[text()='Policy Group Description']")
	private WebElement policyGroupDescriptionHeader;
	
	@FindBy(xpath = "//div[text()='Creation Date']")
	private WebElement creationDateHeader;
	
	@FindBy(xpath = "//div[text()='Status']")
	private WebElement statusHeader;
	
	@FindBy(xpath = "//div[text()='Action']")
	private WebElement actionHeader;
	
	@FindBy(id = "filter_btn")
	private WebElement filterButton;
	
	@FindBy(id = "pagination_card")
	private WebElement pagination;
	
	@FindBy(id = "policy_group_id_filter")
	private WebElement policyGroupIdFilter;
	
	@FindBy(id = "policy_group_name_filter")
	private WebElement policyGroupNameFilter;
	
	@FindBy(id = "policy_group_description_filter")
	private WebElement policyGroupDescriptionFilter;
	
	@FindBy(id = "status_filter")
	private WebElement statusFilter;
	
	@FindBy(id = "policy_group_list_item1")
	private WebElement policyGroupList1;
	
	@FindBy(id = "policy_group_view_back_btn")
	private WebElement policyGroupViewBackButton;
	
	@FindBy(xpath = "//h1[text()='View Policy Group']")
	private WebElement viewPolicyGroupPageTitle;
	
	@FindBy(id = "status_filter_option1")
	private WebElement activatedOption;
	
	@FindBy(id = "status_filter_option2")
	private WebElement deActivatedOption;
	
	@FindBy(id = "policy_group_list_view1")
	private WebElement policyGroupActionButton;
	
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
	
	public boolean isAuthPolicyTabDisplayed() {
		return isElementDisplayed(authPolicyTab);
	}
	
	public boolean isDataSharePolicyTabDisplayed() {
		return isElementDisplayed(dataSharePolicyTab);
	}
	
	public boolean isTitleOfPageDisplayed() {
		return isElementDisplayed(titleOfPage);
	}
	
	public boolean isBackiconDisplayed() {
		return isElementDisplayed(titleBackIcon);
	}
	
	public boolean isSubTitleOfPageDisplayed() {
		return isElementDisplayed(subtitleOfPage);
	}
	
	public boolean isPolicyGroupHeaderTextDisplayed() {
		return isElementDisplayed(policyGroupIdHeader);
	}
	
	public boolean isPolicyGroupNameHeaderDisplayed() {
		return isElementDisplayed(policyGroupNameHeader);
	}
	
	public boolean isPolicyGroupDescriptionHeaderDisplayed() {
		return isElementDisplayed(policyGroupDescriptionHeader);
	}
	
	public boolean isCreatedDateHeaderTextDisplayed() {
		return isElementDisplayed(creationDateHeader);
	}
	
	public boolean isStatusHeaderTextDisplayed() {
		return isElementDisplayed(statusHeader);
	}
	
	public boolean isActionHeaderTextDisplayed() {
		return isElementDisplayed(actionHeader);
	}
	
	public boolean isFiletrButtonDisplayedOrEnabled() {
		return isElementDisplayed(filterButton);
	}
	
	public boolean isPaginationDisplayed() {
		return isElementDisplayed(pagination);
	}
	
	public void clickOnFilterButton() {
		clickOnElement(filterButton);
		
	}
	
	public void clickOnPolicyGroupIdFilter(String value) {
		clickOnElement(policyGroupIdFilter);
	}
	
	public void clickOnPolicyGroupNameFilter(String value) {
		clickOnElement(policyGroupNameFilter);
	}
	
	public void clickOnPolicyGroupDescriptionFilter(String value) {
		clickOnElement(policyGroupDescriptionFilter);
	}
	
	public void clickOnStatusFilter() {
		clickOnElement(statusFilter);
		clickOnElement(activatedOption);
	}
	
	public void clickOnPolicyGroupList1() {
		clickOnElement(policyGroupList1);
	}
	
	public void clickOnPolicyGroupViewBackButton() {
		clickOnElement(policyGroupViewBackButton);
	}
	
	public boolean isViewPolicyGroupPageTitleDisplayed() {
		return isElementDisplayed(viewPolicyGroupPageTitle);
	}
	
	
}
