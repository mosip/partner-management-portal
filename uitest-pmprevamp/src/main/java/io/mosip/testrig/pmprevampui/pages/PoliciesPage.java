package io.mosip.testrig.pmprevampui.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmprevampui.utility.TestRunner;

public class PoliciesPage extends BasePage {

	@FindBy(id = "title_back_icon")
	private WebElement policiesTitle;

	@FindBy(xpath = "//div[@class='flex flex-col items-center']")
	private WebElement policiesEmptyTable;

	@FindBy(id = "show_request_policy")
	private WebElement requestPolicyButton;

	@FindBy(id = "policies_request_btn")
	private WebElement policies_request_btn;

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

	@FindBy(id = "request_policies_policy_name_option1")
	private WebElement requestPolicyNameOption;

	@FindBy(id = "sub_title_home_btn")
	private WebElement HomeButton;

	@FindBy(id = "sub_title_btn")
	private WebElement PolicyButton;

	@FindBy(id = "list_of_policies")
	private WebElement ListOfPolicyRequested;

	@FindBy(xpath = "//*[text()='Pending For Approval']")
	private WebElement PendingForApproval;

	@FindBy(id = "policy_list_view1")
	private WebElement ElipcisButton;

	@FindBy(id = "policy_list_view_card")
	private WebElement CardViewButton;

	@FindBy(id = "view_policy_back_btn")
	private WebElement BackButton;

	@FindBy(id = "policy_partner_id_filter")
	private WebElement policyPartnerIdFilter;

	@FindBy(id = "policy_partner_type_filter")
	private WebElement policyPartnerTypeFilter;

	@FindBy(id = "policy_partner_type_filter_option1")
	private WebElement policyPartnerTypeFilterOption1;

	@FindBy(id = "policy_status_filter_option1")
	private WebElement policy_status_filter_option1;

	@FindBy(id = "policy_group_filter")
	private WebElement policyGroupFilter;
	
	@FindBy(id = "policy_group_filter_option1")
	private WebElement policyGroupFilterOption1;
	
	@FindBy(id = "policy_name_filter")
	private WebElement policyNameFilter;
	
	@FindBy(id = "policy_name_filter_option1")
	private WebElement policyNameFilterOption1;
	
	@FindBy(id = "policy_status_filter")
	private WebElement policyStatusFilter;

	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;

	@FindBy(id = "policy_partner_id_filter_option1")
	private WebElement policyPartnerIdFilterOption1;

	@FindBy(id = "filter_btn")
	private WebElement filterButton;

	@FindBy(xpath = "//*[@aria-label='Next page']")
	private WebElement nextPage;

	@FindBy(xpath = "//*[@aria-label='Previous page']")
	private WebElement previousPage;

	@FindBy(xpath = "//*[text()='Partner ID']")
	private WebElement PartnerIdText;

	@FindBy(id = "policy_list_item1")
	private WebElement policyListItem1;

	@FindBy(id = "sub_title_home_btn")
	private WebElement subTitleHomeButton;

	@FindBy(id = "sub_title_btn")
	private WebElement subTitleButton;

	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;

	@FindBy(xpath = "//*[text()='View Policy Details']")
	private WebElement ViewPolicyDetailsText;

	@FindBy(id = "partnerId_desc_icon")
	private WebElement partnerId_desc_icon;

	@FindBy(id = "partnerId_asc_icon")
	private WebElement partnerId_asc_icon;

	@FindBy(id = "partnerType_desc_icon")
	private WebElement partnerType_desc_icon;

	@FindBy(id = "partnerType_asc_icon")
	private WebElement partnerType_asc_icon;

	@FindBy(id = "policyGroupName_desc_icon")
	private WebElement policyGroupName_desc_icon;

	@FindBy(id = "policyGroupName_asc_icon")
	private WebElement policyGroupName_asc_icon;

	@FindBy(id = "policyName_desc_icon")
	private WebElement policyName_desc_icon;

	@FindBy(id = "policyName_asc_icon")
	private WebElement policyName_asc_icon;

	@FindBy(id = "createdDateTime_desc_icon")
	private WebElement createdDateTime_desc_icon;

	@FindBy(id = "createdDateTime_asc_icon")
	private WebElement createdDateTime_asc_icon;

	@FindBy(id = "status_desc_icon")
	private WebElement status_desc_icon;

	@FindBy(id = "status_asc_icon")
	private WebElement status_asc_icon;
	
	@FindBy(id = "request_policy_partner_id_option1")
	private WebElement requestPolicyPartnerIdOption1;
	
	@FindBy(id = "policies_auth_policy_tab")
	private WebElement policiesAuthPolicyTab;
	
	@FindBy(id = "create_auth_policy_btn")
	private WebElement createAuthPolicyButton;
	
	@FindBy(id = "policy_name_box")
	private WebElement policyNameBox;
	
	@FindBy(id = "policy_description_box")
	private WebElement policyDescriptionBox;
	
	@FindBy(id = "policy_data_box")
	private WebElement policyDataBox;
	
	@FindBy(id = "fileInput")
	private WebElement uploadFile;
	
	@FindBy(id = "create_policy_form_submit_btn")
	private WebElement createPolicyFormSubmitButton;
	
	@FindBy(id = "policy_group_dropdown")
	private WebElement policyGroupDropdown;

	@FindBy(id = "policy_group_dropdown_search_input")
	private WebElement policyGroupDropdownSearchInput;
	
	@FindBy(id = "policy_group_dropdown_option1")
	private WebElement policyGroupDropdownOption1;
	
	@FindBy(id = "confirmation_go_back_btn")
	private WebElement goBackButton;
	
	@FindBy(id = "policy_group_filter")
	private WebElement policyGroupFilterBox;
	
	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilterButton;
	
	@FindBy(id = "policies_list_view1")
	private WebElement policiesListViewElipsisButton;
	
	@FindBy(id = "policy_publish_btn")
	private WebElement policyPublishButton;
	
	@FindBy(id = "publish_policy_button")
	private WebElement publishPolicyButton;
	
	@FindBy(id = "success_msg_close")
	private WebElement successMsgCloseButton;
	
	@FindBy(id = "publish_policy_close_button")
	private WebElement publishPolicyCloseButton;
	
	@FindBy(id = "policy_details_partner_id_label")
	private WebElement policyDetailsPartnerIdLabel;
	
	@FindBy(id = "policy_details_policy_group_name_context")
	private WebElement policyDetailsPolicyGroupNameContext;
	
	@FindBy(id = "policy_details_partner_type_label")
	private WebElement policyDetailsPartnerTypeLabel;
	
	@FindBy(id = "policy_details_partner_type_context")
	private WebElement policyDetailsPartnerTypeContext;
	
	@FindBy(id = "policy_details_policy_group_name_label")
	private WebElement policyDetailsPolicyGroupNameLabel;
	
	@FindBy(id = "policy_details_policy_name_label")
	private WebElement policyDetailsPolicyNameLabel;
	
	@FindBy(id = "policy_details_policy_name_context")
	private WebElement policyDetailsPolicyNameContext;
	
	@FindBy(id = "policy_details_policy_group_description_label")
	private WebElement policyDetailsPolicyGroupDescriptionLabel;
	
	@FindBy(id = "policy_details_policy_group_description_context")
	private WebElement policyDetailsPolicyGroupDescriptionContext;
	
	@FindBy(id = "policy_details_policy_name_description_label")
	private WebElement policyDetailsPolicyNameDescriptionLabel;
	
	@FindBy(id = "policy_details_policy_name_description_context")
	private WebElement policyDetailsPolicyNameDescriptionContext;
	
	@FindBy(id = "policy_details_comments")
	private WebElement policyDetailsComments;
	
	@FindBy(id = "request_policies_form_clear_btn")
	private WebElement requestPoliciesFormClearButton;
	
	@FindBy(id = "request_policies_form_cancel_btn")
	private WebElement requestPoliciesFormCancelButton;
	
	public PoliciesPage(WebDriver driver) {
		super(driver);
	}

	public boolean isPoliciesPageDisplayed() {
		return isElementDisplayed(policiesTitle);
	}

	public void clickOnRequestPolicyButton() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clickOnElement(requestPolicyButton);
	}

	public void clickOnRequestPolicyButtonWithFilter() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clickOnElement(policies_request_btn);
	}

	public void selectPartnerIdDropdown() {
		clickOnElement(partnerIdDropdown);
		clickOnElement(requestPolicyPartnerIdOption1);
	}

	public void selectPolicyNameDropdown(String value) {
		clickOnElement(policyNameDropdown);
		enter(searchBoxForPolicyName,value);
		clickOnElement(requestPolicyNameOption);
	}

	public void selectInvalidPolicyNameDropdown(String value) {
		clickOnElement(policyNameDropdown);
		enter(searchBoxForPolicyName, value);
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

	public void clickSubmitButton() {
		clickOnElement(submitButton);
	}

	public boolean isPolicySubmittedSuccessfullyDisplayed() {
		return isElementDisplayed(policySubmittedSuccessfully);
	}

	public boolean isNoDataAvailableTextDisplayed() {
		return isElementDisplayed(noDataAvailableText);
	}

	public void searchInPolicyName(String value) {
		enter(searchBoxForPolicyName, value);
	}

	public boolean isPoliciesEmptyTableDisplayed() {
		return isElementDisplayed(policiesEmptyTable);
	}

	public boolean isPoliciesEmptyTableEnabled() {
		return isElementEnabled(requestPolicyButton);
	}

	public void clickOnHomeButton() {
		clickOnElement(HomeButton);
	}

	public void clickOnPartnerIdDropdown() {
		clickOnElement(partnerIdDropdown);
	}

	public boolean isListOfPolicyRequestedDisplayed() {
		return isElementDisplayed(ListOfPolicyRequested);
	}

	public boolean isPendingForApprovalTextDisplayed() {
		return isElementDisplayed(PendingForApproval);
	}

	public void clickOnElipcisButton() {
		clickOnElement(ElipcisButton);
	}

	public boolean isCardViewButtonDisplayed() {
		return isElementDisplayed(CardViewButton);
	}

	public void clickOnCardViewButton() {
		clickOnElement(CardViewButton);
	}

	public void clickOnBackButton() {
		clickOnElement(BackButton);
	}

	public void clickOnPolicyPartnerIdFilter() {
		clickOnElement(policyPartnerIdFilter);
		clickOnElement(policyPartnerIdFilterOption1);
	}

	public void clickOnPolicyPartnerTypeFilter() {
		clickOnElement(policyPartnerTypeFilter);
		clickOnElement(policyPartnerTypeFilterOption1);
	}

	public void clickOnPolicyGroupFilter() {
		clickOnElement(policyGroupFilter);
		clickOnElement(policyGroupFilterOption1);
	}

	public void clickOnPolicyNameFilter() {
		clickOnElement(policyNameFilter);
		clickOnElement(policyNameFilterOption1);
	}

	public void clickOnPolicyStatusFilter() {
		clickOnElement(policyStatusFilter);
		clickOnElement(policy_status_filter_option1);
	}

	public void clickOnFilterResetButton() {
		clickOnElement(filterResetButton);
	}

	public void clickOnFilterButton() {
		clickOnElement(filterButton);
	}

	public boolean isNextPageDisplayed() {
		return isElementDisplayed(nextPage);
	}

	public boolean isPreviousPageDisplayed() {
		return isElementDisplayed(previousPage);
	}

	public boolean isPartnerIdTextDisplayed() {
		return isElementDisplayed(PartnerIdText);
	}

	public boolean isPartnerIdDescIconDisplayed() {
		return isElementDisplayed(partnerId_desc_icon);
	}

	public boolean isPartnerIdAscIconDisplayed() {
		return isElementDisplayed(partnerId_asc_icon);
	}

	public boolean isPartnerTypeDescIconDisplayed() {
		return isElementDisplayed(partnerType_desc_icon);
	}

	public boolean isPartnerTypeAscIconDisplayed() {
		return isElementDisplayed(partnerType_asc_icon);
	}

	public boolean isPolicyGroupNameDescIconDisplayed() {
		return isElementDisplayed(policyGroupName_desc_icon);
	}

	public boolean isPolicyGroupNameAscIconDisplayed() {
		return isElementDisplayed(policyGroupName_asc_icon);
	}

	public boolean isPolicyNameDescIconDisplayed() {
		return isElementDisplayed(policyName_desc_icon);
	}

	public boolean isPolicyNameAscIconDisplayed() {
		return isElementDisplayed(policyName_asc_icon);
	}

	public boolean isCreatedDateTimeDescISconDisplayed() {
		return isElementDisplayed(createdDateTime_desc_icon);
	}

	public boolean isCreatedDateTimeAscIconDisplayed() {
		return isElementDisplayed(createdDateTime_asc_icon);
	}

	public boolean isStatusDescIconDisplayed() {
		return isElementDisplayed(status_desc_icon);
	}

	public boolean isStatusAscIconDisplayed() {
		return isElementDisplayed(status_asc_icon);
	}

	public boolean isFilterButtonButtonEnabled() {
		return isElementEnabled(filterButton);
	}

	public boolean isSubTitleHomeButtonDisplayed() {
		return isElementDisplayed(subTitleHomeButton);
	}

	public boolean isSubTitleButtonDisplayed() {
		return isElementDisplayed(subTitleButton);
	}

	public boolean isTitleBackIconDisplayed() {
		return isElementDisplayed(titleBackIcon);
	}

	public boolean isViewPolicyDetailsTextDisplayed() {
		return isElementDisplayed(ViewPolicyDetailsText);
	}

	public void clickOnPolicyListItem1() {
		clickOnElement(policyListItem1);
	}

	public void clickOnpoliciesAuthPolicyTab() {
		clickOnElement(policiesAuthPolicyTab);
	}
	
	public void clickOnCreateAuthPolicyButton() {
		clickOnElement(createAuthPolicyButton);
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
	
	public void selectpolicyGroupDropdown(String value) {
		clickOnElement(policyGroupDropdown);
		enter(policyGroupDropdownSearchInput,value);
		clickOnElement(policyGroupDropdownOption1);
	}

	public void clickOngoBackButton() {
		clickOnElement(goBackButton);
	}
	
	public void enterpolicyGroupFilterBox(String val) {
		enter(policyGroupFilterBox, val);
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
	
	public boolean isPolicyDetailsPartnerIdLabelDisplayed() {
		return isElementDisplayed(policyDetailsPartnerIdLabel);
	}
	
	public boolean isPolicyDetailsPolicyGroupNameLabelDisplayed() {
		return isElementDisplayed(policyDetailsPolicyGroupNameLabel);
	}
	
	public boolean isPolicyDetailsPolicyGroupNameContextDisplayed() {
		return isElementDisplayed(policyDetailsPolicyGroupNameContext);
	}
	
	public boolean isPolicyDetailsPartnerTypeLabelDisplayed() {
		return isElementDisplayed(policyDetailsPartnerTypeLabel);
	}
	
	public boolean isPolicyDetailsPartnerTypeContextDisplayed() {
		return isElementDisplayed(policyDetailsPartnerTypeContext);
	}
	
	public boolean isPolicyDetailsPolicyNameLabelDisplayed() {
		return isElementDisplayed(policyDetailsPolicyNameLabel);
	}
	
	public boolean isPolicyDetailsPolicyNameContextDisplayed() {
		return isElementDisplayed(policyDetailsPolicyNameContext);
	}
	
	public boolean isPolicyDetailsPolicyGroupDescriptionLabelDisplayed() {
		return isElementDisplayed(policyDetailsPolicyGroupDescriptionLabel);
	}
	
	public boolean isPolicyDetailsPolicyGroupDescriptionContextDisplayed() {
		return isElementDisplayed(policyDetailsPolicyGroupDescriptionContext);
	}
	
	public boolean isPolicyDetailsPolicyNameDescriptionLabelDisplayed() {
		return isElementDisplayed(policyDetailsPolicyNameDescriptionLabel);
	}
	
	public boolean isPolicyDetailsPolicyNameDescriptionContextDisplayed() {
		return isElementDisplayed(policyDetailsPolicyNameDescriptionContext);
	}
	
	public boolean isPolicyDetailsCommentsDisplayed() {
		return isElementDisplayed(policyDetailsComments);
	}
	
	public void clickOnRequestPoliciesFormClearButton() {
		clickOnElement(requestPoliciesFormClearButton);
	}
	
	public String getThePolicyCommentBoxText() {
		return getTextFromAttribute(commentsTextBox,"placeholder");
	}
	
	public String getThepolicyNameDropdownBoxText() {
		return getTextFromLocator(policyNameDropdown);
	}
	
	public void clickOnRequestPoliciesFormCancelButton() {
		clickOnElement(requestPoliciesFormCancelButton);
	}
	
	public void clickOnSubTitleHomeButton() {
		clickOnElement(subTitleHomeButton);
	}
	
}
