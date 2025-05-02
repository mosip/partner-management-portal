package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PartnerPolicyMappingPage extends BasePage {

	
	@FindBy(xpath = "//h1[text()='Partner - Policy Linking']")
	private WebElement partnerPolicyLinkingTitle;
	
	@FindBy(xpath = "//p[text()='List of Partner-Policy Linkages']")
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
	
	@FindBy(xpath = "//p[contains(text(), 'Approve / Reject')]")
	private WebElement approveOrRejectButton;
	
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
	
	@FindBy(xpath = "//input[@placeholder='Select Partner Type']")
	private WebElement searchPartnerType;
	
	@FindBy(xpath = "//input[@placeholder='Search Organisation']")
	private WebElement searchOrganisation;
	
	@FindBy(xpath = "//p[text()='No Results Found']")
	private WebElement noResultsFound;
	
	@FindBy(xpath = "//button[text()='x']")
	private WebElement cancelButtonOfTextBox;
	
	@FindBy(id = "partner_details_view_btn")
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
		clickOnElement(approveOrRejectButton);
	}
	
	public boolean isConfirmationPopupDisplayed() {
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
	
	public void clickOnApproveRejectButton() {
		clickOnElement(rejectButton);
	}
	
	public void clickOnApproveSubmitButton() {
		clickOnElement(approveButton);
	}
	
	public void enterPendingPolicyNameInFilter(String value) {
		enter(policyNameFilter,value);
	}
	
	public void clickOnRejectButton() {
		clickOnElement(rejectButton);
	}
	
	public void clickOnHomeButton() {
		clickOnElement(homeButton);
	}
	
	public void clickOntitleBackIcon() {
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
}
