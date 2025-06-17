package io.mosip.testrig.pmpuiv2.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpuiv2.utility.TestRunner;

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

	@FindBy(xpath = "//h1[text()='Policy Submitted Successfully!']")
	private WebElement policySubmittedSuccessfully;

	@FindBy(xpath = "//p[text()='No Data Available.']")
	private WebElement noDataAvailableText;
	
	@FindBy(id = "request_policies_policy_name_option1")
	private WebElement requestPolicyNameOption;

	@FindBy(id = "sub_title_home_btn")
	private WebElement HomeButton;

	@FindBy(id = "sub_title_btn")
	private WebElement PolicyButton;

	@FindBy(id = "list_of_policies")
	private WebElement ListOfPolicyRequested;

	@FindBy(xpath = "//div[text()='Pending For Approval']")
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
	
	@FindBy(id = "policy_status_filter")
	private WebElement policyStatusFilter;

	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;

	@FindBy(id = "policy_partner_id_filter_option1")
	private WebElement policyPartnerIdFilterOption1;

	@FindBy(xpath = "//a[@aria-label='Next page']")
	private WebElement nextPage;

	@FindBy(xpath = "//a[@aria-label='Previous page']")
	private WebElement previousPage;

	@FindBy(xpath = "//div[text()='Partner ID']")
	private WebElement PartnerIdText;

	@FindBy(id = "policy_list_item1")
	private WebElement policyListItem1;

	@FindBy(id = "sub_title_home_btn")
	private WebElement subTitleHomeButton;

	@FindBy(id = "sub_title_btn")
	private WebElement subTitleButton;

	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;

	@FindBy(xpath = "//h1[text()='View Policy Details']")
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
	
	@FindBy(id = "policies_data_share_policy_tab")
	private WebElement policiesDataPolicyTab;
	
	@FindBy(id = "policy_data_box")
	private WebElement policyDataBox;
	
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
	
	@FindBy(id = "policy_deactivate_btn")
	private WebElement policyDeactivateButton;
	
	@FindBy(id = "filter_btn")
	private WebElement filterButton;
	
	@FindBy(id = "policy_name_filter_option1")
	private WebElement policyNameFilterOption1;
	
	@FindBy(id = "policy_name_filter")
	private WebElement policyNameFilter;

	@FindBy(id = "deactivate_policy_group__confirm_btn")
	private WebElement deactivateConfirmButton;
	
	@FindBy(xpath = "//h1[text()='Policies']")
	private WebElement titleOfPolicyPage;
	
	@FindBy(xpath = "//span[text()='authpolicy01']")
	private WebElement policyName;
	
	@FindBy(xpath = "//p[text()='authpolicy 01']")
	private WebElement policyDescription;
	
	@FindBy(xpath = "//p[text()='This policy is already pending for approval against your partner ID.']")
	private WebElement policyPendingForApproval;
	
	@FindBy(xpath = "//p[text()='This policy has already been approved against your partner ID.']")
	private WebElement policyAlreadyApproved;
	
	@FindBy(id = "error_close_btn")
	private WebElement errorCloseButton;
	
	@FindBy(xpath = "//p[text()='Home']")
	private WebElement sideNavigationHomeIcon;
	
	@FindBy(id = "policies_policy_group_tab")
	private WebElement policiesPolicyGroupTab;
	
	@FindBy(id = "confirmation_go_back_btn")
	private WebElement goBackButton;
	
	@FindBy(id = "create_auth_policy_btn")
	private WebElement createAuthPolicyButton;
	
	@FindBy(id = "policy_status_filter_option3")
	private WebElement rejectedStatus;
	
	@FindBy(xpath = "//div[text()='Approved']")
	private WebElement statusApproved;
	
	@FindBy(xpath = "//div[text()='Rejected']")
	private WebElement statusRejected;
	
	@FindBy(id = "block_messsage_proceed")
	private WebElement dataLostProcceedButton;
	
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

	public void clickOnRequestPolicyButtonOfTabularPage() {
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

	public void clickOnPolicyStatusFilter() {
		clickOnElement(policyStatusFilter);
		clickOnElement(policy_status_filter_option1);
	}

	public void clickOnFilterResetButton() {
		clickOnElement(filterResetButton);
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

	public void clickOnAuthPolicyTab() {
		clickOnElement(policiesAuthPolicyTab);
	}

	public boolean isFilterButtonButtonEnabled() {
		return isElementEnabled(filterButton);
    }

	public void clickOnCreateAuthPolicyButton() {
		clickOnElement(createAuthPolicyButton);
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
	
	public void enterPendingPolicyNameInFilter(String value) {
		enter(policyNameFilter,value);
	}
	
	public void enterInvalidPolicyNameDropdown(String value) {
		clickOnElement(policyNameDropdown);
		enter(searchBoxForPolicyName,value);
	}
	
	public void enterValidPolicyNameDropdown(String value) {
		clickOnElement(policyNameDropdown);
		enter(searchBoxForPolicyName,value);
		clickOnElement(requestPolicyNameOption);
	}
	
	public void clickOnFilterButton() {
		clickOnElement(filterButton);
	}
	
	public void clickOnPolicyNameFilter() {
		clickOnElement(policyNameFilter);
		clickOnElement(policyNameFilterOption1);
	}

	public void clickOnDeactivateButton() {
		clickOnElement(policyDeactivateButton);
	}
	
	public void clickOnDeactivateConfirmButton() {
		clickOnElement(deactivateConfirmButton);
	}
	
	public boolean isNoDataAvailableDisplayed() {
		return isElementDisplayed(noDataAvailableText);
	}
	
	public boolean isPolicyViewPageBackButtonEnabled() {
		return isElementDisplayed(BackButton);
	}
	
	public boolean isRequestPolicyButtonDisplayed() {
		return isElementDisplayed(policies_request_btn);
	}
	
	public boolean isTitleOfPolicyPageDisplayed() {
		return isElementDisplayed(titleOfPolicyPage);
	}
	
	public void clickOnPolicyNameDescendingBtn() {
		clickOnElement(policyName_desc_icon);
	}
	
	public void clickOnPolicyNameAscendingBtn() {
		clickOnElement(policyName_asc_icon);
	}
	
	public boolean isPolicyNameDisplayed() {
		return isElementDisplayed(policyName);
	}
	
	public boolean isPolicyDescriptionDisplayed() {
		return isElementDisplayed(policyDescription);
	}
	
	public void enterAuthPolicyNameDropdown(String authPolicyName) {
		clickOnElement(policyNameDropdown);
		enter(searchBoxForPolicyName,authPolicyName);
		clickOnElement(requestPolicyNameOption);
	}
	
	public boolean isSubmitButtonEnabled() {
		return isElementDisplayed(submitButton);
	}
	
	public boolean isPolicyAlreadyApprovedMessageDisplayed() {
		return isElementDisplayed(policyAlreadyApproved);
	}
	
	public boolean isPolicyPendingForApprovalMessageDisplayed() {
		return isElementDisplayed(policyPendingForApproval);
	}
	
	public void clickOnErrorCloseButton() {
		clickOnElement(errorCloseButton);
	}
	
	public void clickOnTitleBackIcon() {
		clickOnElement(titleBackIcon);
	}

	public boolean isPoliciesPolicyGroupTabDisplayed() {
		return isElementDisplayed(policiesPolicyGroupTab);
	}
	
	public void clickOnGoBackButton() {
		clickOnElement(goBackButton);
	}
	
	public boolean isPoliciesAuthPolicyTabDisplayed() {
		return isElementDisplayed(policiesAuthPolicyTab);
	}
	
	public void clickOnPoliciesPolicyGroupTab() {
		clickOnElement(policiesPolicyGroupTab);
	}
	
	public void clickOnDataSharePolicyTab() {
		clickOnElement(policiesDataPolicyTab);
	}
	
	public void selectActivatedStatusInFilter() {
		clickOnElement(policyStatusFilter);
		clickOnElement(policy_status_filter_option1);
	}
	
	public void selectRejectedStatusInFilter() {
		clickOnElement(policyStatusFilter);
		clickOnElement(rejectedStatus);
	}
	
	public boolean isStatusApprovedDisplayed() {
		return isElementDisplayed(statusApproved);
	}
	
	public boolean isStatusRejectedDisplayed() {
		return isElementDisplayed(statusRejected);
	}
	
	public void clickOnDataLostProcceedButton() {
		clickOnElement(dataLostProcceedButton);
	}
	
	public void clickOnPolicyNameDropdown() {
		clickOnElement(policyNameDropdown);
	}
}
