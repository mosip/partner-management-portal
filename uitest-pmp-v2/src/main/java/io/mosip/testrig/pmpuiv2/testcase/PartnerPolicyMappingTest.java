package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.DatasharePolicyPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.pages.PolicyGroupPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PolicyCreationForAuthPartner" }, groups = { "PartnerPolicyMappingTest" })
public class PartnerPolicyMappingTest extends BaseClass {
	private BasePage basePage;
	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private PoliciesPage policiesPage;
	private AuthPolicyPage authPolicyPage;
	private PartnerPolicyMappingPage partnerPolicyMappingPage;
	private PolicyGroupPage policygroupPage;
	private DatasharePolicyPage datasharePolicyPage;

	@Test(priority = 1, description = "Create Auth DataShare Policy")
	public void createAuthPolicyDataSharePolicy() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY_PARTLINK);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY_PARTLINK);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY_PARTLINK);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		authPolicyPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY_PARTLINK2);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY_PARTLINK2);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY_PARTLINK2);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		authPolicyPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.DATAPOLICY_PARTLINK);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.DATAPOLICY_PARTLINK);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		datasharePolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY_PARTLINK);
		authPolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnPublishButton();
		datasharePolicyPage.clickOnPublishPolicyButton();
		datasharePolicyPage.clickOnPublishPolicyCloseButton();
	}

	@Test(priority = 2, description = "Request Auth DataShare Policy", dependsOnMethods = "createAuthPolicyDataSharePolicy")
	public void requestAuthDataSharePolicy() {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		loginPage = new LoginPage(driver);

		loginAsAuthPartner();

		dashboardPage.clickOnPoliciesTitle();

		requestPolicy(GlobalConstants.AUTHPOLICY_PARTLINK);

		requestPolicy(GlobalConstants.AUTHPOLICY_PARTLINK2);

		requestPolicy(GlobalConstants.DATAPOLICY_PARTLINK);

	}

	@Test(priority = 3, description = "Tabular View Of Partner Policy", dependsOnMethods = "requestAuthDataSharePolicy")
	public void tabularViewOfPartnerPolicy() {

		dashboardPage = new DashboardPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingTitleDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingSubTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingSubTitleDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerIdHeaderDisplayed(), GlobalConstants.isPartnerIdHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerIdDisplayedInFirstColumn(), GlobalConstants.isPartnerIdDisplayedInFirstColumn);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeHeaderDisplayed(),
				GlobalConstants.isPartnerTypeHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isOrganisationNameHeaderDisplayed(),
				GlobalConstants.isOrganisationNameHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyIdHeaderDisplayed(), GlobalConstants.isPolicyIdHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupHeaderDisplayed(),
				GlobalConstants.isPolicyGroupHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameHeaderDisplayed(), GlobalConstants.isPolicyNameHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isCreationDateHeaderDisplayed(),
				GlobalConstants.isCreationDateHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusHeaderDisplayed(), GlobalConstants.isStatusHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isActionHeaderDisplayed(), GlobalConstants.isActionHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);

		assertFalse(partnerPolicyMappingPage.isFilterResetButtonDisplayed(),
				GlobalConstants.isFilterResetButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isFilterButtonDisplayed(), GlobalConstants.isFilterButtonDisplayed);
		partnerPolicyMappingPage.clickOnFilterButton();
		assertTrue(partnerPolicyMappingPage.isFilterButtonDisabled(), GlobalConstants.isFilterButtonDisabled);
		assertTrue(partnerPolicyMappingPage.isFilterResetButtonEnabled(), GlobalConstants.isFilterResetButtonEnabled);
		assertTrue(partnerPolicyMappingPage.isApplyFilterButtonDisabled(), GlobalConstants.isApplyFilterButtonDisabled);

		partnerPolicyMappingPage.clickOnFilterResetButton();
		partnerPolicyMappingPage.clickOnFilterButton();
		assertFalse(partnerPolicyMappingPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);

		assertTrue(partnerPolicyMappingPage.isPartnerIdFilterDisplayed(), GlobalConstants.isPartnerIdFilterDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeFilterDisplayed(),
				GlobalConstants.isPartnerTypeFilterDisplayed);
		assertTrue(partnerPolicyMappingPage.isOrganisationFilterDisplayed(),
				GlobalConstants.isOrganisationFilterDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyIdFilterDisplayed(), GlobalConstants.isPolicyIdFilterDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameFilterDisplayed(), GlobalConstants.isPolicyNameFilterDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupFilterDisplayed(),
				GlobalConstants.isPolicyGroupFilterDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusDropdownDisplayed(), GlobalConstants.isStatusDropdownDisplayed);

		assertTrue(partnerPolicyMappingPage.isPolicyIdFilterLabelDisplayed(),
				GlobalConstants.isPolicyIdFilterLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameFilterLabelDisplayed(),
				GlobalConstants.isPolicyNameFilterLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupFilterLabelDisplayed(),
				GlobalConstants.isPolicyGroupFilterLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusFilterLabelDisplayed(),
				GlobalConstants.isStatusFilterLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isOrganisationLabelDisplayed(),
				GlobalConstants.isOrganisationLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerIdLabelDisplayed(), GlobalConstants.isPartnerIdLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLabelDisplayed);

		assertTrue(partnerPolicyMappingPage.isPolicyIdFilterPlaceHolderDisplayed(),
				GlobalConstants.isPolicyIdFilterPlaceHolderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameFilterPlaceHolderDisplayed(),
				GlobalConstants.isPolicyNameFilterPlaceHolderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupFilterPlaceHolderDisplayed(),
				GlobalConstants.isPolicyGroupFilterPlaceHolderDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusFilterPlaceHolderDisplayed(),
				GlobalConstants.isStatusFilterPlaceHolderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerIdFilterPlaceHolderDisplayed(),
				GlobalConstants.isPartnerIdFilterPlaceHolderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeFilterPlaceHolderDisplayed(),
				GlobalConstants.isPartnerTypeFilterPlaceHolderDisplayed);
		assertTrue(partnerPolicyMappingPage.isOrganisationFilterPlaceHolderDisplayed(),
				GlobalConstants.isOrganisationFilterPlaceHolderDisplayed);

		partnerPolicyMappingPage.clickOnStatusFilterDropdown();
		assertTrue(partnerPolicyMappingPage.isApprovedStatusDisplayed(), GlobalConstants.isApprovedStatusDisplayed);
		assertTrue(partnerPolicyMappingPage.isPendingForApprovalStatusDisplayed(),
				GlobalConstants.isPendingForApprovalStatusDisplayed);
		assertTrue(partnerPolicyMappingPage.isRejectedStatusDisplayed(), GlobalConstants.isRejectedStatusDisplayed);

		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.RANDOM_DATA);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		assertTrue(partnerPolicyMappingPage.isNoResultsFoundMessageDisplayed(),
				GlobalConstants.isNoResultsFoundMessageDisplayed);
		assertTrue(partnerPolicyMappingPage.isCancelButtonOfTextBoxDisplayed(),
				GlobalConstants.isCancelButtonOfTextBoxDisplayed);
		partnerPolicyMappingPage.clickOnCancelButtonOfTextBox();
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter("authPollink");

		partnerPolicyMappingPage.clickOnFilterResetButton();
		partnerPolicyMappingPage.clickOnFilterButton();
		assertTrue(partnerPolicyMappingPage.isPartnerIdDescIconDisplayed(),
				GlobalConstants.isPartnerIdDescIconDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerIdAscIconDisplayed(), GlobalConstants.isPartnerIdAscIconDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeDescIconDisplayed(),
				GlobalConstants.isPartnerTypeDescIconDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeAscIconDisplayed(),
				GlobalConstants.isPartnerTypeAscIconDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupNameDescIconDisplayed(),
				GlobalConstants.isPolicyGroupNameDescIconDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupNameAscIconDisplayed(),
				GlobalConstants.isPolicyGroupNameAscIconDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameDescIconDisplayed(),
				GlobalConstants.isPolicyNameDescIconDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameAscIconDisplayed(),
				GlobalConstants.isPolicyNameAscIconDisplayed);
		assertTrue(partnerPolicyMappingPage.isCreatedDateTimeDescIconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescIconDisplayed);
		assertTrue(partnerPolicyMappingPage.isCreatedDateTimeAscIconDisplayed(),
				GlobalConstants.isCreatedDateTimeAscIconDisplayed);

		partnerPolicyMappingPage.clickOnPartnerIdDescIcon();
		partnerPolicyMappingPage.clickOnPartnerIdAscIcon();
		partnerPolicyMappingPage.clickOnOrgNameDescIcon();
		partnerPolicyMappingPage.clickOnOrgNameAscIcon();
		partnerPolicyMappingPage.clickOnPolicyGroupNameDescIcon();
		partnerPolicyMappingPage.clickOnPolicyGroupNameAscIcon();
		partnerPolicyMappingPage.clickOnPolicyNameDescIcon();
		partnerPolicyMappingPage.clickOnPolicyNameAscIcon();
		partnerPolicyMappingPage.clickOnCreatedDateTimeDescIcon();
		partnerPolicyMappingPage.clickOnCreatedDateTimeAscIcon();
		partnerPolicyMappingPage.clickOnStatusDescIcon();
		partnerPolicyMappingPage.clickOnStatusAscIcon();

		partnerPolicyMappingPage.clickOnStatusFilterDropdown();
		partnerPolicyMappingPage.clickOnPendingForApprovalStatus();
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		assertTrue(partnerPolicyMappingPage.isApproveRejectButtonEnabled(),
				GlobalConstants.isApproveRejectButtonEnabled);
		assertTrue(partnerPolicyMappingPage.isViewButtoEnabled(), GlobalConstants.isViewButtoEnabled);
		partnerPolicyMappingPage.clickOnPendingForApprovalPolicy();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyDetailsPageDisplayed(),
				GlobalConstants.isPartnerPolicyDetailsPageDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusPendingForApprovalDisplayed(),
				GlobalConstants.isStatusPendingForApprovalDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerStatusActivatedDisplayed(),
				GlobalConstants.isPartnerStatusActivatedDisplayed);
		partnerPolicyMappingPage.clickOnViewBackButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.clickOnStatusFilterDropdown();
		partnerPolicyMappingPage.clickOnApprovedStatus();
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertFalse(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		assertTrue(partnerPolicyMappingPage.isViewButtoEnabled(), GlobalConstants.isViewButtoEnabled);
		partnerPolicyMappingPage.clickOnApprovedPolicy();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyDetailsPageDisplayed(),
				GlobalConstants.isPartnerPolicyDetailsPageDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusApprovedDisplayed(), GlobalConstants.isStatusApprovedDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerStatusActivatedDisplayed(),
				GlobalConstants.isPartnerStatusActivatedDisplayed);
		partnerPolicyMappingPage.clickOnViewBackButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.clickOnStatusFilterDropdown();
		partnerPolicyMappingPage.clickOnRejectedStatus();
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertFalse(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		assertTrue(partnerPolicyMappingPage.isViewButtoEnabled(), GlobalConstants.isViewButtoEnabled);
		partnerPolicyMappingPage.clickOnRejectedPolicy();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyDetailsPageDisplayed(),
				GlobalConstants.isPartnerPolicyDetailsPageDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusRejectedDisplayed(), GlobalConstants.isStatusRejectedDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerStatusActivatedDisplayed(),
				GlobalConstants.isPartnerStatusActivatedDisplayed);
		partnerPolicyMappingPage.clickOnViewBackButton();

		assertTrue(authPolicyPage.isPaginationDisplayed(), GlobalConstants.isPaginationDisplayed);
		assertTrue(authPolicyPage.isPreviusPageButtonDisplayed(), GlobalConstants.isPreviusPageButtonDisplayed);
		assertTrue(authPolicyPage.isNextPageButtonDisplayed(), GlobalConstants.isNextPageButtonDisplayed);
		assertTrue(authPolicyPage.isPage1Displayed(), GlobalConstants.isPage1Displayed);
		authPolicyPage.clickOnNextPageButton();
		assertTrue(authPolicyPage.isPage2Displayed(), GlobalConstants.isPage2Displayed);
		authPolicyPage.clickOnPreviusPageButton();
		assertTrue(authPolicyPage.isPage1Displayed(), GlobalConstants.isPage1Displayed);

		assertTrue(authPolicyPage.isPrefixOfPageDisplayed(), GlobalConstants.isPrefixOfPageDisplayed);
		assertTrue(authPolicyPage.isRecordPerPageDisplayed(), GlobalConstants.isRecordPerPageDisplayed);
		assertTrue(authPolicyPage.isItemPerPage8Displayed(), GlobalConstants.isItemPerPage8Displayed);
		assertTrue(authPolicyPage.isexpandIconDisplayed(), GlobalConstants.isexpandIconDisplayed);
		authPolicyPage.selectItemPerPageNumber();
		assertTrue(authPolicyPage.isItemPerPage16Displayed(), GlobalConstants.isItemPerPage16Displayed);
		partnerPolicyMappingPage.clickOnTitleBackIcon();

	}

	@Test(priority = 4, description = "Approve Reject Requested Policies", dependsOnMethods = "tabularViewOfPartnerPolicy")
	public void approveRejectRequestedPolicies() {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY_PARTLINK);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		assertTrue(partnerPolicyMappingPage.isApproveRejectButtonEnabled(),
				GlobalConstants.isApproveRejectButtonEnabled);
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();

		assertTrue(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameInPopupDisplayed(),
				GlobalConstants.isPolicyNameInPopupDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyPopupSubtitleDisplayed(),
				GlobalConstants.isPolicyPopupSubtitleDisplayed);
		assertTrue(partnerPolicyMappingPage.isConfirmationPopupDetailedMessageDisplayed(),
				GlobalConstants.isConfirmationPopupDetailedMessageDisplayed);
		assertTrue(partnerPolicyMappingPage.isApproveRejectButtonDisplayed(),
				GlobalConstants.isApproveRejectButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isApproveSubmitButtonDisplayed(),
				GlobalConstants.isApproveSubmitButtonDisplayed);
		partnerPolicyMappingPage.clickOnApproveSubmitButton();

		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingTitleDisplayed);
		partnerPolicyMappingPage.clickOnFilterResetButton();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY_PARTLINK);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		assertTrue(partnerPolicyMappingPage.isStatusApprovedDisplayed(), GlobalConstants.isStatusApprovedDisplayed);
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertFalse(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);

		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY_PARTLINK2);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		partnerPolicyMappingPage.clickOnRejectButton();
		assertTrue(partnerPolicyMappingPage.isStatusRejectedDisplayed(), GlobalConstants.isStatusRejectedDisplayed);
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertFalse(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);

		loginAsAuthPartner();

		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnFilterButton();
		policiesPage.selectActivatedStatusInFilter();
		assertTrue(policiesPage.isStatusApprovedDisplayed(), GlobalConstants.isStatusApprovedDisplayed);
		policiesPage.selectRejectedStatusInFilter();
		assertTrue(policiesPage.isStatusRejectedDisplayed(), GlobalConstants.isStatusRejectedDisplayed);

	}

	@Test(priority = 5, description = "View Requested Policy Details", dependsOnMethods = "approveRejectRequestedPolicies")
	public void viewRequestedPolicyDetails() {

		dashboardPage = new DashboardPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY_PARTLINK);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		assertTrue(partnerPolicyMappingPage.isViewButtoEnabled(), GlobalConstants.isViewButtoEnabled);
		partnerPolicyMappingPage.clickOnViewButton();

		assertTrue(partnerPolicyMappingPage.isPartnerPolicyDetailsPageDisplayed(),
				GlobalConstants.isPartnerPolicyDetailsPageDisplayed);
		assertTrue(partnerPolicyMappingPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isListOfPartnerPolicyLinkagesDisplayed(),
				GlobalConstants.isListOfPartnerPolicyLinkagesDisplayed);

		assertTrue(partnerPolicyMappingPage.isPartnerIdLabelDisplayed(), GlobalConstants.isPartnerIdLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerIdContextDisplayed(), GlobalConstants.isPartnerIdContextDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusApprovedDisplayed(), GlobalConstants.isStatusApprovedDisplayed);
		assertTrue(partnerPolicyMappingPage.isCreatedOnLabelDisplayed(), GlobalConstants.isCreatedOnLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isCreatedDateDisplayed(), GlobalConstants.isCreatedDateDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyIdLabelDisplayed(), GlobalConstants.isPolicyIdLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameLabelDisplayed(), GlobalConstants.isPolicyNameLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameContextDisplayed(),
				GlobalConstants.isPolicyNameContextDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupLabelDisplayed(), GlobalConstants.isPolicyGroupLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupContextDisplayed(),
				GlobalConstants.isPolicyGroupContextDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeContextDisplayed(),
				GlobalConstants.isPartnerTypeContextDisplayed);
		assertTrue(partnerPolicyMappingPage.isOrgNameLabelDisplayed(), GlobalConstants.isOrgNameLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isOrgNameContextDisplayed(), GlobalConstants.isOrgNameContextDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerStatusLabelDisplayed(),
				GlobalConstants.isPartnerStatusLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerStatusActivatedDisplayed(),
				GlobalConstants.isPartnerStatusActivatedDisplayed);

		assertTrue(partnerPolicyMappingPage.isCommentsLabelDisplayed(), GlobalConstants.isCommentsLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isAdminCommentsLabelDisplayed(),
				GlobalConstants.isAdminCommentsLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerCommentsLabelDisplayed(),
				GlobalConstants.isPartnerCommentsLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusApprovedDisplayed(), GlobalConstants.isStatusApprovedDisplayed);
		assertTrue(partnerPolicyMappingPage.isCommentsCreatedDateDisplayed(), GlobalConstants.isCreatedDateDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerCommentsContextDisplayed(),
				GlobalConstants.isPartnerCommentsContextDisplayed);
		partnerPolicyMappingPage.clickOnTitleBackIcon();

	}

	@Test(priority = 6, description = "Search with invalid policy name", dependsOnMethods = "viewRequestedPolicyDetails")
	public void searchWithInvalidPolicyName() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		PoliciesPage policiesPage = dashboardPage.clickOnPoliciesTitle();

		policiesPage.clickOnRequestPolicyButton();

		policiesPage.selectPartnerIdDropdown();

		assertTrue(policiesPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		policiesPage.selectInvalidPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY + "123");
		policiesPage.searchInPolicyName(GlobalConstants.DEFAULT_POLICY + "123");

		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		policiesPage.clickOnRequestPoliciesFormClearButton();

		assertTrue(policiesPage.isPolicyNamePlaceHolderDisplayed(), GlobalConstants.isPolicyNamePlaceHolderDisplayed);
		assertTrue(policiesPage.isPolicyCommentBoxPlaceholderDisplayed(),
				GlobalConstants.isPolicyCommentBoxPlaceholderDisplayed);

		policiesPage.clickOnRequestPoliciesFormCancelButton();
		assertTrue(policiesPage.isListOfPolicyRequestedDisplayed(),
				GlobalConstants.isListOfPolicyRequestedTextDisplayed);
	}

	@Test(priority = 7, description = "Resubmit already submitted request policy", dependsOnMethods = "searchWithInvalidPolicyName")
	public void reSubmitAlreadySubmittedRequestPolicy() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		policiesPage = dashboardPage.clickOnPoliciesTitle();

		policiesPage.clickOnRequestPolicyButton();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		policiesPage.enterComments(GlobalConstants.DEFAULT_POLICY);
		policiesPage.clickSubmitButton();
		assertTrue(policiesPage.isPolicyAlreadyApprovedMessageDisplayed(),
				GlobalConstants.isPolicyAlreadyApprovedMessageDisplayed);
		policiesPage.clickOnErrorCloseButton();
		policiesPage.enterAuthPolicyNameDropdown(GlobalConstants.PENDING_POLICY);
		policiesPage.clickSubmitButton();
		assertTrue(policiesPage.isPolicyPendingForApprovalMessageDisplayed(),
				GlobalConstants.isPolicyPendingForApprovalMessageDisplayed);
		policiesPage.clickOnErrorCloseButton();

		policiesPage.clickOnRequestPoliciesFormClearButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterAuthPolicyNameDropdown(GlobalConstants.AUTHPOLICY02);
		policiesPage.enterComments(GlobalConstants.DEFAULT_POLICY);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

	}

	private void loginAsAuthPartner() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.AUTH_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();
	}

	private void requestPolicy(String authPolicyName) {
		policiesPage.clickOnRequestPolicyButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterAuthPolicyNameDropdown(authPolicyName);
		policiesPage.enterComments(GlobalConstants.DEFAULT_POLICY);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

	}

}
