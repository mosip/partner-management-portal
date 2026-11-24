package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.util.List;

import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;

public class PartnerPolicyMappingPage extends BasePage {

	@FindBy(id = "page_title")
	private WebElement partnerPolicyLinkingTitle;

	@FindBy(xpath = "//p[contains(text(),'List of Partner-Policy Linkages')]")
	private WebElement partnerPolicyLinkingSubTitle;

	@FindBy(xpath = "//div[text()='Partner ID']")
	private WebElement partnerIdHeader;

	@FindBy(xpath = "//div[text()='Partner Type']")
	private WebElement partnerTypeHeader;

	@FindBy(xpath = "//div[text()='Organisation Name']")
	private WebElement organisationNameHeader;

	@FindBy(xpath = "//div[text()='Policy ID']")
	private WebElement policyIdHeader;

	@FindBy(xpath = "//div[text()='Policy Group']")
	private WebElement policyGroupHeader;

	@FindBy(xpath = "//div[text()='Policy Name']")
	private WebElement policyNameHeader;

	@FindBy(xpath = "//div[text()='Creation Date']")
	private WebElement creationDateHeader;

	@FindBy(xpath = "//div[text()='Status']")
	private WebElement statusHeader;

	@FindBy(xpath = "//div[text()='Action']")
	private WebElement actionHeader;

	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;

	@FindBy(id = "filter_btn")
	private WebElement filterButton;

	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;

	@FindBy(id = "policy_group_filter")
	private WebElement policyGroupFilter;

	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilterButton;

	@FindBy(id = "partner_list_view1")
	private WebElement partnerListViewElipsisButton;

	@FindBy(css = "[id^='policy_requests_ovp_map_biometric_extractor_']")
	private WebElement mapBiometricExtractorAction;

	@FindBy(xpath = "//p[contains(text(), 'Do you want to Approve/Reject the Policy')]")
	private WebElement confirmationPopup;

	@FindBy(id = "approve-reject_popup_description")
	private WebElement confirmationPopupDetailedMessage;

	@FindBy(id = "approve_popup_description")
	private WebElement confirmPopupDetailedMessage;

	@FindBy(id = "approve_btn")
	private WebElement approveButton;

	@FindBy(id = "reject_btn")
	private WebElement rejectButton;

	@FindBy(id = "confirmation_custom_btn")
	private WebElement confirmationCustomButton;

	@FindBy(id = "policy_name_filter")
	private WebElement policyNameFilter;

	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;

	@FindBy(id = "partner_id_filter")
	private WebElement partnerIdFilter;

	@FindBy(id = "partner_type_filter_dropdown_btn")
	private WebElement partnerTypeFilter;

	@FindBy(id = "partner_organisation_filter")
	private WebElement organisationFilter;

	@FindBy(id = "policy_id_filter")
	private WebElement policyIdFilter;

	@FindBy(id = "status_filter_dropdown_btn")
	private WebElement statusDropdown;

	@FindBy(id = "status_filter_option1")
	private WebElement approvedStatus;

	@FindBy(id = "status_filter_option2")
	private WebElement pendingForApprovalStatus;

	@FindBy(id = "status_filter_option3")
	private WebElement rejectedStatus;

	@FindBy(xpath = "//p[text()='Policy ID']")
	private WebElement policyIdLabel;

	@FindBy(xpath = "//p[text()='Policy Name']")
	private WebElement policyNameLabel;

	@FindBy(xpath = "//p[text()='Policy Group']")
	private WebElement policyGroupLabel;

	@FindBy(xpath = "//p[text()='Status']")
	private WebElement statusLabel;

	@FindBy(xpath = "//p[text()='Organisation Name']")
	private WebElement organisationLabel;

	@FindBy(xpath = "//p[text()='Partner ID']")
	private WebElement partnerIdLabel;

	@FindBy(xpath = "//p[text()='Partner Type']")
	private WebElement partnerTypeLabel;

	@FindBy(xpath = "//input[@placeholder='Search Policy ID']")
	private WebElement searchPolicyId;

	@FindBy(xpath = "//input[@placeholder='Search Policy Name']")
	private WebElement searchPolicyName;

	@FindBy(xpath = "//input[@placeholder='Search Policy Group']")
	private WebElement searchPolicyGroup;

	@FindBy(xpath = "//input[@placeholder='Search Partner ID']")
	private WebElement searchPartnerId;

	@FindBy(xpath = "//span[text()='Select Partner Type']")
	private WebElement searchPartnerType;

	@FindBy(xpath = "//input[@placeholder='Search Organisation']")
	private WebElement searchOrganisation;

	@FindBy(xpath = "//p[text()='No Results Found']")
	private WebElement noResultsFound;

	@FindBy(xpath = "//button[text()='x']")
	private WebElement cancelButtonOfTextBox;

	@FindBy(id = "partner_details_approve_or_reject_btn")
	private WebElement approveRejectButton;

	@FindBy(id = "partner_details_view_btn")
	private WebElement viewButton;

	@FindBy(id = "partner_list_item1")
	private WebElement approvedPolicy;

	@FindBy(id = "partner_list_item1")
	private WebElement pendingForApprovalPolicy;

	@FindBy(id = "partner_list_item1")
	private WebElement rejectedPolicy;

	@FindBy(xpath = "//h1[text()='View Partner-Policy Linking']")
	private WebElement partnerPolicyDetailsPage;

	@FindBy(id = "view_partner_policy_request_back_btn")
	private WebElement viewBackButton;

	@FindBy(id = "partnerId_desc_icon")
	private WebElement partnerId_desc_icon;

	@FindBy(id = "partnerId_asc_icon")
	private WebElement partnerId_asc_icon;

	@FindBy(id = "partnerType_desc_icon")
	private WebElement partnerType_desc_icon;

	@FindBy(id = "partnerType_asc_icon")
	private WebElement partnerType_asc_icon;

	@FindBy(id = "orgName_asc_icon")
	private WebElement orgName_asc_icon;

	@FindBy(id = "orgName_desc_icon")
	private WebElement orgName_desc_icon;

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

	@FindBy(id = "approve-reject_popup_title")
	private WebElement policyNameInPopup;

	@FindBy(xpath = "//div[text()='Approved']")
	private WebElement approved;

	@FindBy(xpath = "//div[text()='Rejected']")
	private WebElement rejected;

	@FindBy(xpath = "//div[text()='Pending For Approval']")
	private WebElement pendingForApproval;

	@FindBy(id = "sub_title_btn")
	private WebElement listOfPartnerPolicyLinkages;

	@FindBy(xpath = "//span[text()='pmpui-auth']")
	private WebElement partnerIdContext;

	@FindBy(xpath = "//div[contains(text(), 'Created On')]")
	private WebElement createdOnLabel;

	@FindBy(xpath = "//p[text()='Policy ID']")
	private WebElement policyId;

	@FindBy(xpath = "//span[text()='26621']")
	private WebElement policyIdContext;

	@FindBy(xpath = "//p[text()='Policy Name']")
	private WebElement policyName;

	@FindBy(xpath = "//p[text()='authpolpartlink']")
	private WebElement policyNameContext;

	@FindBy(xpath = "//p[text()='Policy Group']")
	private WebElement policyGroup;

	@FindBy(xpath = "//p[text()='automationui policy group']")
	private WebElement policyGroupContext;

	@FindBy(id = "view_partner_policy_request_partner_type_context")
	private WebElement partnerTypeContext;

	@FindBy(xpath = "//p[text()='AABBCC']")
	private WebElement organisationNameContext;

	@FindBy(xpath = "//p[text()='Partner Status']")
	private WebElement partnerStatusLabel;

	@FindBy(xpath = "//span[text()='Active']")
	private WebElement partnerStatusActivated;

	@FindBy(xpath = "//p[text()='Comments']")
	private WebElement commentsLabel;

	@FindBy(xpath = "//h4[text()='Admin Comments']")
	private WebElement adminCommentsLabel;

	@FindBy(xpath = "//h4[text()=\"Partner's Comments\"]")
	private WebElement partnerCommentsLabel;

	@FindBy(id = "partner_policy_request_partner_comment")
	private WebElement partnerCommentsContext;

	@FindBy(id = "view_partner_policy_request_status")
	private WebElement partnerPolicyRequestStatus;

	@FindBy(id = "request_policy_btn")
	private WebElement requestPolicyButton;

	@FindBy(id = "page_title")
	private WebElement pageTitle;

	@FindBy(id = "sub_title_two_btn")
	private WebElement requestPolicyBreadcrumb;

	@FindBy(id = "sub_title_home_btn")
	private WebElement requestPolicyHomeButton;

	@FindBy(id = "request_policy_mandantory_msg")
	private WebElement requestPolicyMandatoryMsg;

	@FindBy(id = "request_policy_partner_type_dropdown_btn")
	private WebElement requestPolicyPartnerTypeDropdown;

	@FindBy(id = "request_policy_partner_id_dropdown_btn")
	private WebElement requestPolicyPartnerIdDropdown;

	@FindBy(id = "request_policy_partner_id_search_input")
	private WebElement requestPolicyPartnerIdSearchInput;

	@FindBy(id = "request_policy_partner_id_no_data_available")
	private WebElement requestPolicyPartnerIdNoDataAvailable;

	@FindBy(id = "request_policy_policy_group")
	private WebElement requestPolicyPolicyGroup;

	@FindBy(id = "request_policies_policy_name_dropdown_btn")
	private WebElement requestPoliciesPolicyNameDropdown;

	@FindBy(id = "request_policies_policy_name_search_input")
	private WebElement requestPoliciesPolicyNameSearchInput;

	@FindBy(id = "request_policies_policy_name_option1")
	private WebElement requestPoliciesPolicyNameOption1;

	@FindBy(id = "request_policies_policy_name_description_1")
	private WebElement requestPoliciesPolicyNameDescription1;

	@FindBy(id = "request_policy_comment_box")
	private WebElement requestPolicyCommentBox;

	@FindBy(id = "request_policy_partner_id_info")
	private WebElement requestPolicyPartnerIdInfo;

	@FindBy(id = "request_policy_policy_group")
	private WebElement requestPolicyGroup;

	@FindBy(id = "request_policies_form_clear_btn")
	private WebElement requestPoliciesFormClearButton;

	@FindBy(id = "request_policies_form_cancel_btn")
	private WebElement requestPoliciesFormCancelButton;

	@FindBy(id = "request_policies_form_submit_btn")
	private WebElement requestPoliciesFormSubmitButton;

	@FindBy(id = "blocker_prompt_description")
	private WebElement blockerPromptDescription;

	@FindBy(id = "block_messsage_proceed")
	private WebElement blockMessageProceedButton;

	@FindBy(id = "block_message_cancel")
	private WebElement blockMessageCancelButton;

	@FindBy(id = "confirmation_success_icon")
	private WebElement confirmationSuccessIcon;

	@FindBy(id = "request_policy_confirmation_header")
	private WebElement requestPolicyConfirmationHeader;

	@FindBy(id = "request_policy_confirmation_description")
	private WebElement requestPolicyConfirmationDescription;

	@FindBy(id = "confirmation_go_back_btn")
	private WebElement confirmationGoBackButton;

	@FindBy(id = "approve_popup_title")
	private WebElement approvePopupTitle;

	@FindBy(id = "approve_popup_cancel_btn")
	private WebElement approvePopupCancelButton;

	@FindBy(id = "approve_popup_submit_btn")
	private WebElement approvePopupSubmitButton;

	@FindBy(id = "request_policy_error_msg")
	private WebElement requestPolicyErrorMsg;

	@FindBy(id = "approve-reject_popup_title")
	private WebElement approveRejectPopupTitle;

	@FindBy(id = "approve-reject_popup_sub_title")
	private WebElement approveRejectPopupSubTitle;

	@FindBy(id = "view_partner_policy_sub_title_id")
	private WebElement viewPartnerIdSubtitle;

	@FindBy(id = "view_partner_policy_request_created_on")
	private WebElement viewCreatedOnDate;

	@FindBy(id = "view_partner_policy_request_created_date_time")
	private WebElement viewCreatedOnTime;

	@FindBy(id = "view_partner_policy_request_organisation_context")
	private WebElement viewOrganisationContext;

	@FindBy(id = "view_partner_policy_request_policy_id_context")
	private WebElement viewPolicyIdContext;

	@FindBy(id = "view_partner_policy_request_policy_name_context")
	private WebElement viewPolicyNameContext;

	@FindBy(id = "view_partner_policy_request_policy_group_context")
	private WebElement viewPolicyGroupContext;

	@FindBy(id = "view_partner_policy_request_partner_status_context")
	private WebElement viewPartnerStatusContext;

	@FindBy(id = "view_partner_policy_request_comments_label")
	private WebElement viewCommentsLabel;

	@FindBy(id = "view_partner_policy_request_admin_comments_label")
	private WebElement viewAdminCommentsLabel;

	@FindBy(id = "partner_policy_request_partner_comment_label")
	private WebElement viewPartnerCommentsLabel;

	@FindBy(id = "partner_policy_request_status")
	private WebElement viewAdminCommentStatus;

	@FindBy(id = "view_approve_reject_btn")
	private WebElement viewApproveRejectButton;

	@FindBy(xpath = "//p[text()='Biometric Extractor Provider Mapping']")
	private WebElement biometricExtractorProviderMappingLabel;

	@FindBy(xpath = "//td[normalize-space()='No Biometric Extractor Provider Configuration has been mapped to this policy.']")
	private WebElement noBiometricExtractorMappedMessage;

	@FindBy(xpath = "//table//td[normalize-space()='face']")
	private WebElement faceModalityMappedCell;

	@FindBy(xpath = "//p[text()='Credential Type']")
	private WebElement credentialTypeLabel;

	@FindBy(xpath = "//p[text()='Credential Type']/following-sibling::p[1]")
	private WebElement credentialTypeValue;

	@FindBy(css = "input, textarea, select")
	private List<WebElement> editableInputs;

	@FindBy(xpath = "//button[normalize-space()='Save' or normalize-space()='Edit' or normalize-space()='Update' or normalize-space()='Delete']")
	private List<WebElement> editActionButtons;

	public PartnerPolicyMappingPage(WebDriver driver) {
		super(driver);
	}

	public boolean isPartnerPolicyLinkingTitleDisplayed() {
		return isElementDisplayed(partnerPolicyLinkingTitle);
	}

	public String waitUntilPageTitle(String expectedTitle) {
		return new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			String title = getTextFromLocator(partnerPolicyLinkingTitle);
			return expectedTitle.equals(title == null ? "" : title.trim()) ? title.trim() : null;
		});
	}

	public boolean isPartnerPolicyLinkingSubTitleDisplayed() {
		return isElementDisplayed(partnerPolicyLinkingSubTitle);
	}

	public boolean isPartnerIdHeaderDisplayed() {
		return isElementDisplayed(partnerIdHeader);
	}

	public boolean isPartnerTypeHeaderDisplayed() {
		return isElementDisplayed(partnerTypeHeader);
	}

	public boolean isOrganisationNameHeaderDisplayed() {
		return isElementDisplayed(organisationNameHeader);
	}

	public boolean isPolicyIdHeaderDisplayed() {
		return isElementDisplayed(policyIdHeader);
	}

	public boolean isPolicyGroupHeaderDisplayed() {
		return isElementDisplayed(policyGroupHeader);
	}

	public boolean isPolicyNameHeaderDisplayed() {
		return isElementDisplayed(policyNameHeader);
	}

	public boolean isCreationDateHeaderDisplayed() {
		return isElementDisplayed(creationDateHeader);
	}

	public boolean isStatusHeaderDisplayed() {
		return isElementDisplayed(statusHeader);
	}

	public boolean isActionHeaderDisplayed() {
		return isElementDisplayed(actionHeader);
	}

	public boolean isHomeButtonDisplayed() {
		return isElementDisplayed(homeButton);
	}

	public boolean isFilterButtonDisplayed() {
		return isElementDisplayed(filterButton);
	}

	public boolean isFilterButtonEnabled() {
		return isElementEnabled(filterButton);
	}

	public boolean isFilterButtonEnabledQuick() {
		return isElementDisplayedQuick(By.id("filter_btn"), ABSENCE_CHECK_TIMEOUT) && filterButton.isEnabled();
	}

	public boolean isFilterButtonDisabled() {
		return isElementDisabled(filterButton);
	}

	public void clickOnFilterButton() {
		clickOnElement(filterButton);
	}

	public void clickOnFilterResetButton() {
		clickOnElement(filterResetButton);
	}

	public void enterpolicyGroupFilter(String val) {
		enter(policyGroupFilter, val);
	}

	public void clickOnApplyFilterButton() {
		clickOnElement(applyFilterButton);
	}

	public void clickOnPartnerListViewElipsisButton() {
		clickOnElement(partnerListViewElipsisButton);
	}

	public void filterPolicyRequestsByPartnerId(String partnerId) {
		clickOnFilterButton();
		enterPartnerIdInFilter(partnerId);
		clickOnApplyFilterButton();
	}

	public String waitUntilFirstPolicyRequestRowContains(String partnerId) {
		return new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			String row = getTextFromLocator(approvedPolicy);
			if (row == null || !row.contains(partnerId)) {
				return null;
			}
			return row.trim();
		});
	}

	public void openFirstPolicyRequestAction() {
		clickOnElement(partnerListViewElipsisButton);
	}

	public boolean isMapBiometricExtractorActionDisplayed() {
		return isElementDisplayedQuick(mapBiometricExtractorAction, ABSENCE_CHECK_TIMEOUT);
	}

	public boolean isMapBiometricExtractorActionDisabled() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
				if (!isElementDisplayedQuick(mapBiometricExtractorAction, ABSENCE_CHECK_TIMEOUT)) {
					return null;
				}
				String value = mapBiometricExtractorAction.getAttribute("class");
				return value != null && value.contains("pointer-events-none") ? value : null;
			});
			return true;
		} catch (TimeoutException timeout) {
			return false;
		}
	}

	public void openMapBiometricExtractorAction() {
		clickOnElement(mapBiometricExtractorAction);
	}

	public void clickOnApproveOrRejectButton() {
		clickOnElement(approveRejectButton);
	}

	public boolean isApproveOrRejectConfirmationPopupDisplayed() {
		return isElementEnabled(confirmationPopup);
	}

	public boolean isApproveOrRejectConfirmationPopupDisplayedQuick() {
		return isElementDisplayedQuick(By.xpath("//p[contains(text(), 'Do you want to Approve/Reject the Policy')]"),
				ABSENCE_CHECK_TIMEOUT) && confirmationPopup.isEnabled();
	}

	public boolean isPolicyPopupSubtitleDisplayed() {
		return isElementEnabled(confirmationPopup);
	}

	public boolean isConfirmationPopupDetailedMessageDisplayed() {
		return isElementEnabled(confirmationPopupDetailedMessage);
	}

	public boolean isConfirmPopupDetailedMessageDisplayed() {
		return isElementEnabled(confirmPopupDetailedMessage);
	}

	public boolean isApproveRejectButtonDisplayed() {
		return isElementEnabled(rejectButton);
	}

	public boolean isApproveSubmitButtonDisplayed() {
		return isElementEnabled(approveButton);
	}

	public void clickOnRejectButton() {
		clickOnElement(rejectButton);
	}

	public void clickOnApproveSubmitButton() {
		clickOnElement(approveButton);
	}

	public boolean isConfirmationCustomButtonDisplayed() {
		return isElementDisplayed(confirmationCustomButton);
	}

	public void clickOnConfirmationCustomButton() {
		clickOnElement(confirmationCustomButton);
	}

	public void enterPartnerIdInFilter(String value) {
		enter(partnerIdFilter, value);
	}

	public void enterPendingPolicyNameInFilter(String value) {
		enter(policyNameFilter, value);
	}

	public void clickOnHomeButton() {
		clickOnElement(homeButton);
	}

	public void clickOnTitleBackIcon() {
		clickOnElement(titleBackIcon);
	}

	public boolean isPartnerIdFilterDisplayed() {
		return isElementDisplayed(partnerIdFilter);
	}

	public boolean isPartnerTypeFilterDisplayed() {
		return isElementDisplayed(partnerTypeFilter);
	}

	public boolean isOrganisationFilterDisplayed() {
		return isElementDisplayed(organisationFilter);
	}

	public boolean isPolicyIdFilterDisplayed() {
		return isElementDisplayed(policyIdFilter);
	}

	public boolean isPolicyNameFilterDisplayed() {
		return isElementDisplayed(policyNameFilter);
	}

	public boolean isPolicyGroupFilterDisplayed() {
		return isElementDisplayed(policyGroupFilter);
	}

	public boolean isStatusDropdownDisplayed() {
		return isElementDisplayed(statusDropdown);
	}

	public void clickOnStatusFilterDropdown() {
		clickOnElement(statusDropdown);
	}

	public boolean isApprovedStatusDisplayed() {
		return isElementDisplayed(approvedStatus);
	}

	public boolean isPendingForApprovalStatusDisplayed() {
		return isElementDisplayed(pendingForApprovalStatus);
	}

	public boolean isRejectedStatusDisplayed() {
		return isElementDisplayed(rejectedStatus);
	}

	public boolean isPolicyIdFilterLabelDisplayed() {
		return isElementDisplayed(policyIdLabel);
	}

	public boolean isPolicyNameFilterLabelDisplayed() {
		return isElementDisplayed(policyNameLabel);
	}

	public boolean isPolicyGroupFilterLabelDisplayed() {
		return isElementDisplayed(policyGroupLabel);
	}

	public boolean isStatusFilterLabelDisplayed() {
		return isElementDisplayed(statusLabel);
	}

	public boolean isOrganisationLabelDisplayed() {
		return isElementDisplayed(organisationLabel);
	}

	public boolean isPartnerIdLabelDisplayed() {
		return isElementDisplayed(partnerIdLabel);
	}

	public boolean isPartnerTypeLabelDisplayed() {
		return isElementDisplayed(partnerTypeLabel);
	}

	public boolean isPolicyIdFilterPlaceHolderDisplayed() {
		return isElementDisplayed(searchPolicyId);
	}

	public boolean isPolicyNameFilterPlaceHolderDisplayed() {
		return isElementDisplayed(searchPolicyName);
	}

	public boolean isPolicyGroupFilterPlaceHolderDisplayed() {
		return isElementDisplayed(searchPolicyGroup);
	}

	public boolean isStatusFilterPlaceHolderDisplayed() {
		return isElementDisplayed(statusDropdown);
	}

	public boolean isPartnerIdFilterPlaceHolderDisplayed() {
		return isElementDisplayed(searchPartnerId);
	}

	public boolean isPartnerTypeFilterPlaceHolderDisplayed() {
		return isElementDisplayed(searchPartnerType);
	}

	public boolean isOrganisationFilterPlaceHolderDisplayed() {
		return isElementDisplayed(searchOrganisation);
	}

	public boolean isNoResultsFoundMessageDisplayed() {
		return isElementDisplayed(noResultsFound);
	}

	public boolean isCancelButtonOfTextBoxDisplayed() {
		return isElementDisplayed(cancelButtonOfTextBox);
	}

	public void clickOnCancelButtonOfTextBox() {
		clickOnElement(cancelButtonOfTextBox);
	}

	public boolean isApplyFilterButtonDisabled() {
		return isElementDisabled(applyFilterButton);
	}

	public boolean isFilterResetButtonDisplayed() {
		return isElementDisplayed(filterResetButton);
	}

	public boolean isFilterResetButtonDisplayedQuick() {
		return isElementDisplayedQuick(By.id("filter_reset_btn"), ABSENCE_CHECK_TIMEOUT);
	}

	public boolean isFilterResetButtonEnabled() {
		return isElementEnabled(filterResetButton);
	}

	public boolean isFilterResetButtonEnabledQuick() {
		return isElementDisplayedQuick(By.id("filter_reset_btn"),
				ABSENCE_CHECK_TIMEOUT) && filterResetButton.isEnabled();
	}

	public boolean isViewButtoEnabled() {
		return isElementEnabled(viewButton);
	}

	public boolean isApproveRejectButtonEnabled() {
		return isElementEnabled(approveRejectButton);
	}

	public void clickOnPendingForApprovalStatus() {
		clickOnElement(pendingForApprovalStatus);
	}

	public void clickOnApprovedStatus() {
		clickOnElement(approvedStatus);
	}

	public void clickOnRejectedStatus() {
		clickOnElement(rejectedStatus);
	}

	public void clickOnApprovedPolicy() {
		clickOnElement(approvedPolicy);
	}

	public void clickOnPendingForApprovalPolicy() {
		clickOnElement(pendingForApprovalPolicy);
	}

	public void clickOnRejectedPolicy() {
		clickOnElement(rejectedPolicy);
	}

	public boolean isPartnerPolicyDetailsPageDisplayed() {
		return isElementDisplayed(partnerPolicyDetailsPage);
	}

	public void clickOnViewBackButton() {
		clickOnElement(viewBackButton);
	}

	public boolean isPartnerIdDescIconDisplayed() {
		return isElementDisplayed(partnerId_desc_icon);
	}

	public boolean isPartnerIdAscIconDisplayed() {
		return isElementDisplayed(partnerId_asc_icon);
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

	public boolean isOrgNameDescIconDisplayed() {
		return isElementDisplayed(orgName_desc_icon);
	}

	public boolean isOrgNameAscIconDisplayed() {
		return isElementDisplayed(orgName_asc_icon);
	}

	public boolean isPartnerTypeDescIconDisplayed() {
		return isElementDisplayed(partnerType_desc_icon);
	}

	public boolean isPartnerTypeAscIconDisplayed() {
		return isElementDisplayed(partnerType_asc_icon);
	}

	public void clickOnPartnerIdDescIcon() {
		clickOnElement(partnerId_desc_icon);
	}

	public void clickOnPartnerIdAscIcon() {
		clickOnElement(partnerId_asc_icon);
	}

	public void clickOnPolicyGroupNameDescIcon() {
		clickOnElement(policyGroupName_desc_icon);
	}

	public void clickOnPolicyGroupNameAscIcon() {
		clickOnElement(policyGroupName_asc_icon);
	}

	public void clickOnPolicyNameDescIcon() {
		clickOnElement(policyName_desc_icon);
	}

	public void clickOnPolicyNameAscIcon() {
		clickOnElement(policyName_asc_icon);
	}

	public void clickOnCreatedDateTimeDescIcon() {
		clickOnElement(createdDateTime_desc_icon);
	}

	public void clickOnCreatedDateTimeAscIcon() {
		clickOnElement(createdDateTime_asc_icon);
	}

	public void clickOnStatusDescIcon() {
		clickOnElement(status_desc_icon);
	}

	public void clickOnStatusAscIcon() {
		clickOnElement(status_asc_icon);
	}

	public void clickOnOrgNameDescIcon() {
		clickOnElement(orgName_desc_icon);
	}

	public void clickOnOrgNameAscIcon() {
		clickOnElement(orgName_asc_icon);
	}

	public boolean isPolicyNameInPopupDisplayed() {
		return isElementDisplayed(policyNameInPopup);
	}

	public boolean isStatusApprovedDisplayed() {
		return isElementDisplayed(approved);
	}

	public boolean isStatusRejectedDisplayed() {
		return isElementDisplayed(rejected);
	}

	public boolean isStatusPendingForApprovalDisplayed() {
		return isElementDisplayed(pendingForApproval);
	}

	public void clickOnViewButton() {
		clickOnElement(viewButton);
	}

	public boolean isListOfPartnerPolicyLinkagesDisplayed() {
		return isElementDisplayed(listOfPartnerPolicyLinkages);
	}

	public boolean isPartnerIdContextDisplayed() {
		return isElementDisplayed(partnerIdContext);
	}

	public boolean isCreatedOnLabelDisplayed() {
		return isElementDisplayed(createdOnLabel);
	}

	public boolean isCreatedDateDisplayed() {
		WebElement createdDate = driver
				.findElement(By.xpath("//div[text()='Created On " + PmpTestUtil.todayDateWithoutZeroPadder + "']"));
		return isElementDisplayed(createdDate);
	}

	public boolean isPolicyIdLabelDisplayed() {
		return isElementDisplayed(policyId);
	}

	public boolean isPolicyIdContextDisplayed() {
		return isElementDisplayed(policyIdContext);
	}

	public boolean isPolicyNameLabelDisplayed() {
		return isElementDisplayed(policyName);
	}

	public boolean isPolicyNameContextDisplayed() {
		return isElementDisplayed(policyNameContext);
	}

	public boolean isPolicyGroupLabelDisplayed() {
		return isElementDisplayed(policyGroup);
	}

	public boolean isPolicyGroupContextDisplayed() {
		return isElementDisplayed(policyGroupContext);
	}

	public boolean isPartnerTypeContextDisplayed() {
		return isElementDisplayed(partnerTypeContext);
	}

	public boolean isOrgNameLabelDisplayed() {
		return isElementDisplayed(organisationLabel);
	}

	public boolean isOrgNameContextDisplayed() {
		return isElementDisplayed(organisationNameContext);
	}

	public boolean isPartnerStatusLabelDisplayed() {
		return isElementDisplayed(partnerStatusLabel);
	}

	public boolean isPartnerStatusActivatedDisplayed() {
		return isElementDisplayed(partnerStatusActivated);
	}

	public boolean isCommentsLabelDisplayed() {
		return isElementDisplayed(commentsLabel);
	}

	public boolean isAdminCommentsLabelDisplayed() {
		return isElementDisplayed(adminCommentsLabel);
	}

	public boolean isPartnerCommentsLabelDisplayed() {
		return isElementDisplayed(partnerCommentsLabel);
	}

	public boolean isPartnerCommentsContextDisplayed() {
		return isElementDisplayed(partnerCommentsContext);
	}

	public boolean isCommentsCreatedDateDisplayed() {
		WebElement createdDate = driver
				.findElement(By.xpath("//div[text()='Created On " + PmpTestUtil.todayDateWithoutZeroPadder + "']"));
		return isElementDisplayed(createdDate);
	}

	public boolean isPartnerIdDisplayedInFirstColumn() {
		By partnerIdColumnHeader = By.id("partnerPolicyMappingRequestList.partnerId_header");
		return isDisplayed(partnerIdColumnHeader);
	}

	public void clickOnRequestPolicyButton() {
		clickOnElement(requestPolicyButton);
	}

	public void clickOnPartnerPolicyLinkingActionButton(String policyName) {
		By actionButton = By.xpath(
				"//td[normalize-space()='" + policyName + "']/parent::tr//button[contains(@id,'partner_list_view')]");
		click(actionButton);
	}

	public String getPageTitle() {
		return getTextFromLocator(pageTitle);
	}

	public boolean isRequestPolicyBreadcrumbDisplayed() {
		return isElementDisplayed(requestPolicyBreadcrumb);
	}

	public boolean isRequestPolicyHomeButtonDisplayed() {
		return isElementDisplayed(requestPolicyHomeButton);
	}

	public boolean isRequestPolicyMandatoryMsgDisplayed() {
		return isElementDisplayed(requestPolicyMandatoryMsg);
	}

	public boolean isRequestPolicyPartnerTypeDropdownDisplayed() {
		return isElementDisplayed(requestPolicyPartnerTypeDropdown);
	}

	public void clickOnRequestPolicyPartnerTypeDropdown() {
		clickOnElement(requestPolicyPartnerTypeDropdown);
	}

	public boolean isRequestPolicyPartnerIdDropdownDisplayed() {
		return isElementDisplayed(requestPolicyPartnerIdDropdown);
	}

	public void clickOnRequestPolicyPartnerIdDropdown() {
		clickOnElement(requestPolicyPartnerIdDropdown);
	}

	public boolean isRequestPolicyPartnerIdNoDataAvailableDisplayed() {
		return isElementDisplayed(requestPolicyPartnerIdNoDataAvailable);
	}

	public boolean isRequestPolicyPolicyGroupDisplayed() {
		return isElementDisplayed(requestPolicyPolicyGroup);
	}

	public String getRequestPolicyPolicyGroupText() {
		return getTextFromLocator(requestPolicyPolicyGroup);
	}

	public boolean isRequestPoliciesPolicyNameDropdownDisplayed() {
		return isElementDisplayed(requestPoliciesPolicyNameDropdown);
	}

	public void clickOnRequestPoliciesPolicyNameDropdown() {
		clickOnElement(requestPoliciesPolicyNameDropdown);
	}

	public boolean isRequestPoliciesPolicyNameOptionDisplayed() {
		return isElementDisplayed(requestPoliciesPolicyNameOption1);
	}

	public String getRequestPoliciesPolicyNameOption1Text() {
		return getTextFromLocator(requestPoliciesPolicyNameOption1);
	}

	public void clickOnRequestPoliciesPolicyNameOption1() {
		clickOnElement(requestPoliciesPolicyNameOption1);
	}

	public boolean isRequestPoliciesPolicyNameDescriptionDisplayed() {
		return isElementDisplayed(requestPoliciesPolicyNameDescription1);
	}

	public String getRequestPoliciesPolicyNameDescriptionText() {
		return getTextFromLocator(requestPoliciesPolicyNameDescription1);
	}

	public boolean isRequestPolicyCommentBoxDisplayed() {
		return isElementDisplayed(requestPolicyCommentBox);
	}

	public void enterRequestPolicyComment(String comment) {
		enter(requestPolicyCommentBox, comment);
	}

	public String getRequestPolicyCommentBoxPlaceholderText() {
		return getTextFromAttribute(requestPolicyCommentBox, GlobalConstants.PLACEHOLDER);
	}

	public boolean isRequestPolicyPartnerIdInfoDisplayed() {
		return isElementDisplayed(requestPolicyPartnerIdInfo);
	}

	public void clickOnRequestPolicyPartnerIdInfo() {
		clickOnElement(requestPolicyPartnerIdInfo);
	}

	public void selectPartnerType(String partnerType) {
		clickOnElement(requestPolicyPartnerTypeDropdown);
		By partnerTypeOption = By
				.xpath("//button[contains(@id,'request_policy_partner_type_option') and text()='" + partnerType + "']");
		click(partnerTypeOption);
	}

	public String getPartnerType() {
		return getTextFromLocator(requestPolicyPartnerTypeDropdown);
	}

	public void selectPartnerIdDropdown(String value) {
		clickOnElement(requestPolicyPartnerIdDropdown);
		clearTextBox(requestPolicyPartnerIdSearchInput);
		enter(requestPolicyPartnerIdSearchInput, value);

		By partnerIdOption = By.xpath("//span[normalize-space()='" + value + "']");

		try {
			waitScrollAndClick(partnerIdOption);
		} catch (TimeoutException | NoSuchElementException e) {
			logger.warn("Partner ID not found: " + value);
			throw new NoSuchElementException("Failed to select partner ID: " + value, e);
		}
	}

	public void searchPartnerIdInDropdown(String value) {
		clickOnElement(requestPolicyPartnerIdDropdown);
		clearTextBox(requestPolicyPartnerIdSearchInput);
		enter(requestPolicyPartnerIdSearchInput, value);
	}

	public String getRequestPolicyPartnerIdDropdown() {
		return getTextFromAttribute(requestPolicyPartnerIdDropdown, GlobalConstants.PLACEHOLDER);
	}

	public boolean isRequestPolicyGroupDisabled() {
		return isElementDisabled(requestPolicyGroup);
	}

	public String getRequestPoliciesPolicyNameDropdown() {
		return getTextFromAttribute(requestPoliciesPolicyNameDropdown, GlobalConstants.PLACEHOLDER);
	}

	public boolean isRequestPoliciesPolicyNameSearchInputDisplayed() {
		return isElementDisplayed(requestPoliciesPolicyNameSearchInput);
	}

	public void searchPolicyNameInDropdown(String value) {
		clickOnElement(requestPoliciesPolicyNameDropdown);
		clearTextBox(requestPoliciesPolicyNameSearchInput);
		enter(requestPoliciesPolicyNameSearchInput, value);
	}

	public void selectRequestPolicyByName(String policyName) {
		searchPolicyNameInDropdown(policyName);
		new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			if (!isElementDisplayedQuick(requestPoliciesPolicyNameOption1, ABSENCE_CHECK_TIMEOUT)) {
				return null;
			}
			String option = getTextFromLocator(requestPoliciesPolicyNameOption1);
			if (option == null) {
				return null;
			}
			String policyLabel = option.split("\\R", 2)[0].trim();
			String normalized = option.replaceAll("\\s+", " ").trim();
			return policyName.equals(policyLabel) || normalized.equals(policyName)
					|| normalized.startsWith(policyName + " ") ? policyName : null;
		});
		clickOnElement(requestPoliciesPolicyNameOption1);
	}

	public boolean isRequestPoliciesFormClearButtonDisplayed() {
		return isElementDisplayed(requestPoliciesFormClearButton);
	}

	public void clickOnRequestPoliciesFormClearButton() {
		clickOnElement(requestPoliciesFormClearButton);
	}

	public boolean isRequestPoliciesFormCancelButtonDisplayed() {
		return isElementDisplayed(requestPoliciesFormCancelButton);
	}

	public void clickOnRequestPoliciesFormCancelButton() {
		clickOnElement(requestPoliciesFormCancelButton);
	}

	public boolean isRequestPoliciesFormSubmitButtonDisplayed() {
		return isElementDisplayed(requestPoliciesFormSubmitButton);
	}

	public boolean isRequestPoliciesFormSubmitButtonEnabled() {
		return isElementEnabled(requestPoliciesFormSubmitButton);
	}

	public boolean isRequestPoliciesFormSubmitButtonDisabled() {
		return isElementDisabled(requestPoliciesFormSubmitButton);
	}

	public void clickOnRequestPoliciesFormSubmitButton() {
		clickOnElement(requestPoliciesFormSubmitButton);
	}

	public boolean isBlockerPromptDescriptionDisplayed() {
		return isElementDisplayed(blockerPromptDescription);
	}

	public String getBlockerPromptDescriptionText() {
		return getTextFromLocator(blockerPromptDescription);
	}

	public void clickOnBlockMessageProceedButton() {
		clickOnElement(blockMessageProceedButton);
	}

	public void clickOnBlockMessageCancelButton() {
		clickOnElement(blockMessageCancelButton);
	}

	public void selectPolicyName(String value) {
		searchPolicyNameInDropdown(value);
		By policyNameOption = By.xpath("//span[normalize-space()='" + value + "']");
		try {
			waitScrollAndClick(policyNameOption);
		} catch (TimeoutException | NoSuchElementException e) {
			logger.warn("Policy name not found: " + value);
			throw new NoSuchElementException("Failed to select policy name: " + value, e);
		}
	}

	public boolean isConfirmationSuccessIconDisplayed() {
		return isElementDisplayed(confirmationSuccessIcon);
	}

	public boolean isRequestPolicyConfirmationHeaderDisplayed() {
		return isElementDisplayed(requestPolicyConfirmationHeader);
	}

	public boolean isRequestPolicyConfirmationDescriptionDisplayed() {
		return isElementDisplayed(requestPolicyConfirmationDescription);
	}

	public boolean isConfirmationGoBackButtonDisplayed() {
		return isElementDisplayed(confirmationGoBackButton);
	}

	public void clickOnConfirmationGoBackButton() {
		clickOnElement(confirmationGoBackButton);
	}

	public boolean isApprovePopupTitleDisplayed() {
		return isElementDisplayed(approvePopupTitle);
	}

	public boolean isApprovePopupCancelButtonDisplayed() {
		return isElementDisplayed(approvePopupCancelButton);
	}

	public void clickOnApprovePopupCancelButton() {
		clickOnElement(approvePopupCancelButton);
	}

	public boolean isApprovePopupSubmitButtonDisplayed() {
		return isElementDisplayed(approvePopupSubmitButton);
	}

	public void clickOnApprovePopupSubmitButton() {
		clickOnElement(approvePopupSubmitButton);
	}

	public boolean isRequestPolicyErrorMsgDisplayed() {
		return isElementDisplayed(requestPolicyErrorMsg);
	}

	public String getRequestPolicyErrorMsgText() {
		return getTextFromLocator(requestPolicyErrorMsg);
	}

	public boolean isStatusDisplayedForPolicyName(String policyName, String statusText) {
		By statusElement = By.xpath(
				"//tr[.//td[normalize-space()='" + policyName + "']]//div[normalize-space()='" + statusText + "']");
		return isDisplayed(statusElement);
	}

	public boolean isApproveRejectPopupTitleDisplayed() {
		return isElementDisplayed(approveRejectPopupTitle);
	}

	public boolean isApproveRejectPopupSubTitleDisplayed() {
		return isElementDisplayed(approveRejectPopupSubTitle);
	}

	public void filterPolicyRequestsByPartnerIdAndPolicyName(String partnerId, String policyName) {
		clickOnFilterButton();
		enterPartnerIdInFilter(partnerId);
		enterPendingPolicyNameInFilter(policyName);
		clickOnApplyFilterButton();
	}

	public String getPartnerIdFilterValue() {
		return partnerIdFilter.getAttribute("value") == null ? "" : partnerIdFilter.getAttribute("value").trim();
	}

	public String waitUntilFirstPolicyRequestRowContains(String partnerId, String expectedText) {
		return new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			String row = getTextFromLocator(approvedPolicy);
			if (row == null || !row.contains(partnerId)) {
				return null;
			}
			row = row.trim();
			return expectedText == null || row.contains(expectedText) ? row : null;
		});
	}

	public void clickOnFirstPolicyRequestRow() {
		clickOnElement(approvedPolicy);
	}

	public String getViewPartnerIdSubtitleText() {
		return getTextFromLocator(viewPartnerIdSubtitle).trim();
	}

	public String getViewPolicyRequestStatusText() {
		return getTextFromLocator(partnerPolicyRequestStatus).trim();
	}

	public String getViewCreatedOnDateText() {
		return getTextFromLocator(viewCreatedOnDate).trim();
	}

	public String getViewCreatedOnTimeText() {
		return getTextFromLocator(viewCreatedOnTime).trim();
	}

	public String getViewPartnerTypeText() {
		return getTextFromLocator(partnerTypeContext).trim();
	}

	public String getViewOrganisationText() {
		return getTextFromLocator(viewOrganisationContext).trim();
	}

	public String getViewPolicyIdText() {
		return getTextFromLocator(viewPolicyIdContext).trim();
	}

	public String getViewPolicyNameText() {
		return getTextFromLocator(viewPolicyNameContext).trim();
	}

	public String getViewPolicyGroupText() {
		return getTextFromLocator(viewPolicyGroupContext).trim();
	}

	public String getViewPartnerStatusText() {
		return getTextFromLocator(viewPartnerStatusContext).trim();
	}

	public boolean isViewCommentsSectionDisplayed() {
		return isElementDisplayed(viewCommentsLabel) && isElementDisplayed(viewAdminCommentsLabel)
				&& isElementDisplayed(viewPartnerCommentsLabel);
	}

	public String getViewPartnerCommentText() {
		return getTextFromLocator(partnerCommentsContext).trim();
	}

	public String getViewAdminCommentStatusText() {
		return getTextFromLocator(viewAdminCommentStatus).trim();
	}

	public boolean isBiometricExtractorProviderMappingDisplayed() {
		return isElementDisplayed(biometricExtractorProviderMappingLabel);
	}

	public boolean isCredentialTypeSectionDisplayed() {
		return isElementDisplayed(credentialTypeLabel);
	}

	public String waitUntilBiometricMappingState() {
		return new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			if (isElementDisplayedQuick(noBiometricExtractorMappedMessage, ABSENCE_CHECK_TIMEOUT)) {
				return "unmapped";
			}
			if (isElementDisplayedQuick(faceModalityMappedCell, ABSENCE_CHECK_TIMEOUT)) {
				return "mapped";
			}
			return null;
		});
	}

	public boolean isFaceBiometricModalityMapped() {
		return isElementDisplayedQuick(faceModalityMappedCell, ABSENCE_CHECK_TIMEOUT);
	}

	public boolean isProviderConfigurationMapped(String configurationName) {
		By configurationCell = By.xpath("//table//td[normalize-space()='" + configurationName + "']");
		return isElementDisplayedQuick(configurationCell, ABSENCE_CHECK_TIMEOUT);
	}

	public String waitUntilCredentialTypeValue() {
		return new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			if (!isElementDisplayedQuick(credentialTypeValue, ABSENCE_CHECK_TIMEOUT)) {
				return null;
			}
			String value = getTextFromLocator(credentialTypeValue);
			if (value == null) {
				return null;
			}
			value = value.trim();
			if (value.isEmpty() || value.toLowerCase().startsWith("loading")) {
				return null;
			}
			return value;
		});
	}

	public boolean areViewPolicyRequestFieldsReadOnly() {
		WebElement[] valueFields = { partnerTypeContext, viewOrganisationContext, viewPolicyIdContext,
				viewPolicyNameContext, viewPolicyGroupContext };
		for (WebElement valueField : valueFields) {
			String tag = valueField.getTagName();
			if ("input".equalsIgnoreCase(tag) || "textarea".equalsIgnoreCase(tag) || "select".equalsIgnoreCase(tag)) {
				return false;
			}
		}
		return true;
	}

	public boolean isViewPolicyRequestEditControlDisplayed() {
		try {
			return new WebDriverWait(driver, ABSENCE_CHECK_TIMEOUT).ignoring(StaleElementReferenceException.class)
					.until(webDriver -> {
						for (WebElement element : editableInputs) {
							if (element.isDisplayed()) {
								return true;
							}
						}
						for (WebElement element : editActionButtons) {
							if (element.isDisplayed()) {
								return true;
							}
						}
						return null;
					});
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String waitUntilApproveButtonEnabled() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(webDriver -> {
			if (!isElementDisplayedQuick(approveButton, ABSENCE_CHECK_TIMEOUT)) {
				return null;
			}
			return approveButton.isEnabled() ? "enabled" : null;
		});
	}

	public boolean isViewApproveRejectButtonDisplayed() {
		return isElementDisplayedQuick(viewApproveRejectButton, ABSENCE_CHECK_TIMEOUT);
	}

}
