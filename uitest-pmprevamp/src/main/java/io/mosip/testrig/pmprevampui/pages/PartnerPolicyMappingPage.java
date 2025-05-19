package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmprevampui.fw.util.PmpTestUtil;

public class PartnerPolicyMappingPage extends BasePage {

	
	@FindBy(xpath = "//h1[text()='Partner - Policy Linking']")
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
	
	@FindBy(xpath = "//p[contains(text(), 'Do you want to Approve/Reject the Policy')]")
	private WebElement confirmationPopup;
	
	@FindBy(xpath = "//p[contains(text(), 'Please review the policy details carefully before taking appropriate action.')]")
	private WebElement confirmationPopupDetailedMessage;
	
	@FindBy(id = "approve_btn")
	private WebElement approveButton;
	
	@FindBy(id = "reject_btn")
	private WebElement rejectButton;
	
	@FindBy(id = "policy_name_filter")
	private WebElement policyNameFilter;
	
	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;
	
	@FindBy(id = "partner_id_filter")
	private WebElement partnerIdFilter;
	
	@FindBy(id = "partner_type_filter")
	private WebElement partnerTypeFilter;
	
	@FindBy(id = "partner_organisation_filter")
	private WebElement organisationFilter;
	
	@FindBy(id = "policy_id_filter")
	private WebElement policyIdFilter;
	
	@FindBy(id = "status_filter")
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
	
	@FindBy(id = "view_api_key_back_btn")
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
	
	@FindBy(xpath = "//p[text()='authpolpartlink']")
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
	
	@FindBy(xpath = "//p[text()='Auth_Partner']")
	private WebElement partnerTypeContext;
	
	@FindBy(xpath = "//p[text()='AABBCC']")
	private WebElement organisationNameContext;
	
	@FindBy(xpath = "//p[text()='Partner Status']")
	private WebElement partnerStatusLabel;
	
	@FindBy(xpath = "//span[text()='Activated']")
	private WebElement partnerStatusActivated;
	
	@FindBy(xpath = "//p[text()='Comments']")
	private WebElement commentsLabel;
	
	@FindBy(xpath = "//h4[text()='Admin Comments']")
	private WebElement adminCommentsLabel;
	
	@FindBy(xpath = "//h4[text()=\"Partner's Comments\"]")
	private WebElement partnerCommentsLabel;
	
	@FindBy(xpath = "//span[text()='request']")
	private WebElement partnerCommentsContext;
	
	public PartnerPolicyMappingPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPartnerPolicyLinkingTitleDisplayed() {
	    return isElementDisplayed(partnerPolicyLinkingTitle);
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
		enter(policyGroupFilter,val);
	}

	public void clickOnApplyFilterButton() {
		clickOnElement(applyFilterButton);
	}
	
	public void clickOnPartnerListViewElipsisButton() {
		clickOnElement(partnerListViewElipsisButton);
	}
	
	public void clickOnApproveOrRejectButton() {
		clickOnElement(approveRejectButton);
	}
	
	public boolean isApproveOrRejectConfirmationPopupDisplayed() {
		return isElementEnabled(confirmationPopup);
	}
	
	public boolean isPolicyPopupSubtitleDisplayed() {
		return isElementEnabled(confirmationPopup);
	}
	
	public boolean isConfirmationPopupDetailedMessageDisplayed() {
		return isElementEnabled(confirmationPopupDetailedMessage);
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
	
	public void enterPendingPolicyNameInFilter(String value) {
		enter(policyNameFilter,value);
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
	
	public boolean isFilterResetButtonEnabled() {
		return isElementEnabled(filterResetButton);
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
	
	public void clickOnviewButton() {
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
		return isElementDisplayed(policyGroupContext);
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

}
