package io.mosip.testrig.pmpuiv2.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpuiv2.utility.BaseClass;

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
	private WebElement policyGroupNameTextLabel;
	
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
	
	@FindBy(xpath = "//p[text()='Missing Input Parameter']")
	private WebElement missingParameterErrorMessage;
	
	@FindBy(xpath = "//h1[text()='Policy Group created successfully!']")
	private WebElement titleOfSuccessMessage;
	
	@FindBy(id = "confirmation_go_back_btn")
	private WebElement successGoBackButton;
	
	@FindBy(id = "confirmation_home_btn")
	private WebElement successHomeButton;
	
	@FindBy(xpath = "//p[text()='Policy group already exists with this name']")
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
	private WebElement datasharePolicyTab;
	
	@FindBy(xpath = "//h1[text()='Policies']")
	private WebElement titleOfPage;
	
	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;
	
	@FindBy(xpath = "//p[contains(text(), 'List of Policy Groups')]")
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
	
	@FindBy(id = "policy_group_details_view_btn")
	private WebElement policyGroupViewButton;
	
	@FindBy(id = "policy_group_deactivate_btn")
	private WebElement deactivateButton;
	
	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilterButton;
	
	@FindBy(xpath = "//p[text()='No Results Found']")
	private WebElement noResultsFoundMessage;
	
	@FindBy(xpath = "//p[text()='Policy Group ID']")
	private WebElement policyGroupIdLabel;
	
	@FindBy(xpath = "//div[text()='Activated']")
	private WebElement statusOfPolicyGroup;
	
	@FindBy(xpath = "//p[text()='Policy Group Name']")
	private WebElement policyGroupNameLabel;
	
	@FindBy(xpath = "//p[text()='Automation123']")
	private WebElement policyGroupNameValue;
	
	@FindBy(xpath = "//p[text()='Policy Group Description']")
	private WebElement policyGroupDescriptionabel;
	
	@FindBy(xpath = "//p[text()='Automation123']")
	private WebElement policyGroupDescriptionValue;
	
	@FindBy(id = "header_hamburger_open_sidenav")
	private WebElement hamburgerOption;
	
	@FindBy(xpath = "//p[normalize-space()='2024 © MOSIP - All rights reserved.']")
	private WebElement mosipRightsText;
	
	@FindBy(id = "footer_documentation_link")
	private WebElement footerDocumentationLink;
	
	@FindBy(id = "footer_contact_us_link")
	private WebElement footerContactUsLink;
	
	@FindBy(id = "deactivate_policy_group__confirm_btn")
	private WebElement deactivateConfirmBtn;
	
	@FindBy(id = "id_desc_icon")
	private WebElement partnerIdDescIcon;
	
	@FindBy(id = "id_asc_icon")
	private WebElement partnerIdAscIcon;
	
	@FindBy(id = "name_asc_icon")
	private WebElement policyGroupNameAscIcon;
	
	@FindBy(id = "name_desc_icon")
	private WebElement policyGroupNameDescIcon;
	
	@FindBy(id = "desc_asc_icon")
	private WebElement policyGroupDescriptionAscIcon;
	
	@FindBy(id = "desc_desc_icon")
	private WebElement policyGroupDescriptionDescIcon;
	
	@FindBy(id = "crDtimes_asc_icon")
	private WebElement creationDateAscIcon;
	
	@FindBy(id = "crDtimes_desc_icon")
	private WebElement creationDateDescIcon;
	
	@FindBy(id = "status_asc_icon")
	private WebElement statusAscIcon;
	
	@FindBy(id = "status_desc_icon")
	private WebElement statusDescIcon;
	
	@FindBy(xpath = "//p[contains(text(), 'Do you want to deactivate the Policy Group')]")
	private WebElement deactivatePolicyGroupPopup;
	
	@FindBy(xpath = "//p[text()='Upon clicking ‘Confirm’, the selected policy group will be deactivated.']")
	private WebElement deactivatePopupSubtitle;
	
	@FindBy(id = "deactivate_policy_group_cancel_btn")
	private WebElement deactivateCancelBtn;
	
	@FindBy(xpath = "//p[text()='Error: Policies in Activated and Draft Status Detected!']")
	private WebElement deactivateErrorPopup;
	
	@FindBy(xpath = "//p[contains(text(), 'To proceed with deactivating the policy group, please ensure the following:')]")
	private WebElement errorPopupDescription;
	
	@FindBy(id = "alert_error_popup_okay_btn")
	private WebElement errorPopupOkayBtn;
	
	@FindBy(id = "something_went_wrong_home_btn")
	private WebElement somethingWentWrongHomeBtn;
	
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
	
	public void enterPolicyGroupName(String policyGroupNameValue) {
		enter(policyGroupNameTextbox,policyGroupNameValue);
	}
	
	public void enterPolicyGroupNameDescription(String policyGroupDescValue) {
		enter(policyGroupNameDescriptionTextbox,policyGroupDescValue);
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
		return isElementEnabled(submitButton);
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
	
	public boolean isDatasharePolicyTabDisplayed() {
		return isElementDisplayed(datasharePolicyTab);
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
		enter(policyGroupNameFilter, value);
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
	
	public void clickOnApplyFilterButton() {
		clickOnElement(applyFilterButton);
	}
	
	public void clickOnPolicyGroupActionButton() {
		clickOnElement(policyGroupActionButton);
	}
	
	public boolean isPolicyGroupViewButtonDisplayed() {
		return isElementDisplayed(policyGroupViewButton);
	}
	
	public boolean isDeactivateButtonDisplayed() {
		return isElementDisplayed(deactivateButton);
	}
	
	public boolean isNoResultsFoundMessageDisplayed() {
		return isElementDisplayed(noResultsFoundMessage);
	}
	
	public void clickOnDeactivateButton() {
		clickOnElement(deactivateButton);
	}
	
	public boolean isStatusOfPolicyGroupDisplayed() {
		return isElementDisplayed(statusOfPolicyGroup);
	}
	
	public boolean isPolicyGroupNameLabelDisplayed() {
		return isElementDisplayed(policyGroupNameLabel);
	}
	
	public boolean isPolicyGroupNameContextDisplayed() {
		return isElementDisplayed(policyGroupNameValue);
	}
	
	public boolean isPolicyGroupDescriptionLabelDisplayed() {
		return isElementDisplayed(policyGroupDescriptionabel);
	}
	
	public boolean isPolicyGroupDescriptionContextDisplayed() {
		return isElementDisplayed(policyGroupDescriptionValue);
	}
	
	public void clickOnTitleBackIconButton() {
		clickOnElement(titleBackIcon);
	}
	
	public boolean isHamburgerOptionDisplayed() {
		return isElementDisplayed(hamburgerOption);
	}
	
	public boolean isMosipRightsTextDisplayed() {
		return isElementDisplayed(mosipRightsText);
	}
	
	public boolean isFooterDocumentationLinkDisplayed() {
		return isElementDisplayed(footerDocumentationLink);
	}
	
	public boolean isFooterContactUsLinkDisplayed() {
		return isElementDisplayed(footerContactUsLink);
	}
	
	public void clickOnDeactivateConfirmButton() {
		clickOnElement(deactivateConfirmBtn);
	}
	
	public boolean isPolicyGroupIdDescIconDisplayed() {
		return isElementDisplayed(partnerIdDescIcon);
	}
	
	public boolean isPolicyGroupIdAscIconDisplayed() {
		return isElementDisplayed(partnerIdAscIcon);
	}
	
	public boolean isPolicyGroupNameDescIconDisplayed() {
		return isElementDisplayed(policyGroupNameDescIcon);
	}
	
	public boolean isPolicyGroupNameAscIconDisplayed() {
		return isElementDisplayed(policyGroupNameAscIcon);
	}
	
	public boolean isPolicyGroupDescriptionDescIconDisplayed() {
		return isElementDisplayed(policyGroupDescriptionDescIcon);
	}
	
	public boolean isPolicyGroupDescriptionAscIconDisplayed() {
		return isElementDisplayed(policyGroupDescriptionAscIcon);
	}
	
	public boolean isCreatedDateTimeDescISconDisplayed() {
		return isElementDisplayed(creationDateDescIcon);
	}
	
	public boolean isCreatedDateTimeAscIconDisplayed() {
		return isElementDisplayed(creationDateAscIcon);
	}
	
	public boolean isStatusDescISconDisplayed() {
		return isElementDisplayed(statusDescIcon);
	}
	
	public boolean isStatusAscIconDisplayed() {
		return isElementDisplayed(statusAscIcon);
	}
	
	public void clickOnDatasharePolicyTab() {
		clickOnElement(datasharePolicyTab);
	}
	
	public boolean isMissingParameterErrorMessageDisplayed() {
		return isElementDisplayed(missingParameterErrorMessage);
	}
	
	public boolean isDeactivatePolicyGroupPopupDisplayed() {
		return isElementDisplayed(deactivatePolicyGroupPopup);
	}
	
	public boolean isDeactivatePopupTitleDisplayed() {
		return isElementDisplayed(deactivatePolicyGroupPopup);
	}
	
	public boolean isDeactivatePopupSubtitleDisplayed() {
		return isElementDisplayed(deactivatePopupSubtitle);
	}
	
	public boolean isDeactivateConfirmButtonAvailable() {
		return isElementDisplayed(deactivateConfirmBtn);
	}
	
	public boolean isDeactivateCancelButtonAvailable() {
		return isElementDisplayed(deactivateCancelBtn);
	}
	
	public void clickOnDeactivateCancelBtn() {
		clickOnElement(deactivateCancelBtn);
	}
	
	public boolean isDeactivateButtonEnabled() {
		return isElementEnabled(deactivateButton);
	}
	
	public boolean isDeactivateErrorPopupDisplayed() {
		return isElementDisplayed(deactivateErrorPopup);
	}
	
	public boolean isDeactivateErrorPopupTitleDisplayed() {
		return isElementDisplayed(deactivateErrorPopup);
	}
	
	public boolean isErrorPopupDescriptionDisplayed() {
		return isElementDisplayed(errorPopupDescription);
	}
	
	public boolean isErrorPopupOkayBtnDisplayed() {
		return isElementDisplayed(errorPopupOkayBtn);
	}
	
	public void clickOnErrorPopupOkayBtn() {
		clickOnElement(errorPopupOkayBtn);
	}
	
	public void clickOnSomethingWentWrongHomeBtn() {
		clickOnElement(somethingWentWrongHomeBtn);
	}

}
