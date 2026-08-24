package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.mosip.testrig.pmpuiv2.utility.LogUtil;

public class PoliciesPage extends BasePage {

	private static final Duration REQUEST_POLICY_OUTCOME_TIMEOUT = Duration.ofSeconds(5);

	@FindBy(id = "title_back_icon")
	private WebElement policiesTitle;

	@FindBy(xpath = "//div[@class='flex flex-col items-center']")
	private WebElement policiesEmptyTable;

	@FindBy(id = "show_request_policy")
	private WebElement requestPolicyButton;

	@FindBy(id = "policies_request_btn")
	private WebElement policies_request_btn;

	@FindBy(id = "request_policy_partner_id_dropdown_btn")
	private WebElement partnerIdDropdown;

	@FindBy(id = "request_policies_policy_name_dropdown_btn")
	private WebElement policyNameDropdown;

	@FindBy(id = "request_policies_policy_name_search_input")
	private WebElement searchBoxForPolicyName;

	@FindBy(id = "request_policy_comment_box")
	private WebElement commentsTextBox;

	@FindBy(id = "request_policies_form_submit_btn")
	private WebElement submitButton;

	@FindBy(id = "request_policy_confirmation_header")
	private WebElement policySubmittedSuccessfully;

	@FindBy(xpath = "//p[text()='No Data Available.']")
	private WebElement noDataAvailableText;

	@FindBy(id = "request_policies_policy_name_option1")
	private WebElement requestPolicyNameOption;

	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;

	@FindBy(id = "sub_title_btn")
	private WebElement policyButton;

	@FindBy(id = "list_of_policies")
	private WebElement listOfPolicyRequested;

	@FindBy(xpath = "//div[text()='Pending For Approval']")
	private WebElement pendingForApproval;

	@FindBy(id = "policy_list_view1")
	private WebElement elipcisButton;

	@FindBy(id = "policy_list_view_card")
	private WebElement cardViewButton;

	@FindBy(id = "view_policy_back_btn")
	private WebElement backButton;

	@FindBy(id = "policy_partner_id_filter_dropdown_btn")
	private WebElement policyPartnerIdFilter;

	@FindBy(id = "policy_partner_type_filter_dropdown_btn")
	private WebElement policyPartnerTypeFilter;

	@FindBy(id = "policy_partner_type_filter_option1")
	private WebElement policyPartnerTypeFilterOption1;

	@FindBy(id = "policy_status_filter_option1")
	private WebElement policy_status_filter_option1;

	@FindBy(id = "policy_group_filter_dropdown_btn")
	private WebElement policyGroupFilter;

	@FindBy(id = "policy_group_filter_option1")
	private WebElement policyGroupFilterOption1;

	@FindBy(id = "policy_status_filter_dropdown_btn")
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
	private WebElement partnerIdText;

	@FindBy(id = "policy_list_item1")
	private WebElement policyListItem1;

	@FindBy(id = "sub_title_home_btn")
	private WebElement subTitleHomeButton;

	@FindBy(id = "sub_title_btn")
	private WebElement subTitleButton;

	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;

	@FindBy(xpath = "//h1[text()='View Policy Details']")
	private WebElement viewPolicyDetailsText;

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

	@FindBy(id = "policy_name_filter_dropdown_btn")
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

	@FindBy(xpath = "//span[text()='Select policy name']")
	private WebElement policyNamePlaceholder;

	@FindBy(xpath = "//textarea[@placeholder='Mention the purpose of requesting the policy']")
	private WebElement policyCommentBoxPlaceholder;

	@FindBy(id = "show_request_policy")
	private WebElement middleRequestPolicyButton;

	@FindBy(id = "policies_request_btn")
	private WebElement tabularPoliciesRequestButton;
	
	@FindBy(id = "policies_misp_policy_tab")
	private WebElement mispPolicyTab;

	@FindBy(xpath = "//*[@id='page_title' or @id='list_of_policies' or @id='show_request_policy'"
			+ " or @id='policies_request_btn' or @id='polices_list_error_msg'"
			+ " or @id='sub_title_home_btn' or @id='loading_text']")
	private List<WebElement> policiesListPageMarkers;

	public PoliciesPage(WebDriver driver) {
		super(driver);
	}

	public boolean isPoliciesPageDisplayed() {
		return isElementDisplayed(policiesTitle);
	}

	/**
	 * True when Policies list page has loaded after side-nav/dashboard navigation.
	 * Requires policies URL and a content marker (avoids matching title_back_icon on other pages,
	 * and waits through the policies-list loading state).
	 */
	public boolean isPoliciesListPageDisplayed() {
		try {
			return new WebDriverWait(driver, Duration.ofSeconds(45)).until(d -> {
				String url = d.getCurrentUrl();
				if (!(url.contains("policies-list") || url.contains("/policies/"))) {
					return false;
				}
				for (WebElement marker : policiesListPageMarkers) {
					try {
						if (marker.isDisplayed()) {
							return true;
						}
					} catch (Exception ignored) {
						// Stale or not yet present marker — keep waiting.
					}
				}
				return false;
			});
		} catch (TimeoutException e) {
			LogUtil.step("Policies list page not displayed. URL: " + driver.getCurrentUrl());
			return false;
		}
	}

	public void clickOnRequestPolicyButton() {
		if (isTabularRequestPolicyButtonDisplayed()) {
			clickOnElement(tabularPoliciesRequestButton);

		} else if (isMiddleRequestPolicyButtonDisplayed()) {
			clickOnElement(middleRequestPolicyButton);

		} else {
			throw new RuntimeException("Request Policy button not found in middle or tabular view");
		}
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
		enter(searchBoxForPolicyName, value);
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

	// Short window: used to assert the request did NOT succeed.
	public boolean isPolicySubmittedSuccessfullyDisplayedQuick() {
		return isElementDisplayedQuick(By.id("request_policy_confirmation_header"), REQUEST_POLICY_OUTCOME_TIMEOUT);
	}

	public boolean isRequestPolicyErrorMessageDisplayed() {
		return isElementDisplayedQuick(By.id("request_policy_error_msg"), REQUEST_POLICY_OUTCOME_TIMEOUT);
	}

	public String getRequestPolicyErrorMessage() {
		return getTextFromLocator(By.id("request_policy_error_msg")).trim();
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
		if (isMiddleRequestPolicyButtonDisplayed()) {
			return isElementEnabled(middleRequestPolicyButton);

		} else if (isTabularRequestPolicyButtonDisplayed()) {
			return isElementEnabled(tabularPoliciesRequestButton);

		} else {
			return false;
		}
	}

	public void clickOnHomeButton() {
		clickOnElement(homeButton);
	}

	public void clickOnPartnerIdDropdown() {
		clickOnElement(partnerIdDropdown);
	}

	public boolean isListOfPolicyRequestedDisplayed() {
		return isElementDisplayed(listOfPolicyRequested);
	}

	public boolean isPendingForApprovalTextDisplayed() {
		return isElementDisplayed(pendingForApproval);
	}

	public void clickOnElipcisButton() {
		clickOnElement(elipcisButton);
	}

	public boolean isCardViewButtonDisplayed() {
		return isElementDisplayed(cardViewButton);
	}

	public void clickOnCardViewButton() {
		clickOnElement(cardViewButton);
	}

	public void clickOnBackButton() {
		clickOnElement(backButton);
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
		return isElementDisplayed(partnerIdText);
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

	public boolean isCreatedDateTimeDescIconDisplayed() {
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
		return isElementDisplayed(viewPolicyDetailsText);
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

	public boolean isPolicyNamePlaceHolderDisplayed() {
		return isElementDisplayed(policyNamePlaceholder);
	}

	public boolean isPolicyCommentBoxPlaceholderDisplayed() {
		return isElementDisplayed(policyCommentBoxPlaceholder);
	}

	public void clickOnRequestPoliciesFormCancelButton() {
		clickOnElement(requestPoliciesFormCancelButton);
	}

	public void clickOnSubTitleHomeButton() {
		clickOnElement(subTitleHomeButton);
	}

	public void enterPendingPolicyNameInFilter(String value) {
		enter(policyNameFilter, value);
	}

	public void enterInvalidPolicyNameDropdown(String value) {
		clickOnElement(policyNameDropdown);
		enter(searchBoxForPolicyName, value);
	}

	public void enterValidPolicyNameDropdown(String value) {
		clickOnElement(policyNameDropdown);
		enter(searchBoxForPolicyName, value);
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
		return isElementDisplayed(backButton);
	}

	public boolean isMiddleRequestPolicyButtonDisplayed() {
		return isElementDisplayed(middleRequestPolicyButton);
	}

	public boolean isTabularRequestPolicyButtonDisplayed() {
		return isElementDisplayed(tabularPoliciesRequestButton);
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
		enter(searchBoxForPolicyName, authPolicyName);
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

	public void clickOnPolicyButton() {
		clickOnElement(policyButton);
	}

	public boolean isPolicyIdDisplayedInFourthColumnOnPoliciesPage() {
		By policyIdColumnHeader = By.id("policies.policyId_header");
		return isDisplayed(policyIdColumnHeader);
	}
	
	public void clickOnMispPolicyTab() {
		clickOnElement(mispPolicyTab);
	}

}
