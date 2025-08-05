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

public class PartnerPolicyLinkingTest extends BaseClass {

	private BasePage basePage;
	private DashboardPage dashboardPage;
	private PolicyGroupPage policygroupPage;
	private PoliciesPage policiesPage;
	private AuthPolicyPage authPolicyPage;
	private DatasharePolicyPage datasharePolicyPage;
	private LoginPage loginPage;
	private PartnerPolicyMappingPage partnerPolicyMappingPage;

	@Test(priority = 01, description = "Create Auth DataShare Policy")
	public void creatAuthPolicyDataSharePolicy() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY_PARTLINK);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY_PARTLINK);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		authPolicyPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY_PARTLINK2);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY_PARTLINK2);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		authPolicyPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.DATAPOLICY_PARTLINK);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.DATAPOLICY_PARTLINK);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		datasharePolicyPage.clickOnGoBackButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnPublishButton();
		datasharePolicyPage.clickOnPublishPolicyButton();
		datasharePolicyPage.clickOnPublishPolicyCloseButton();
	}

	@Test(priority = 02, description = "Request Auth DataShare Policy")
	public void RequestAuthDataSharePolicy() {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		loginPage = new LoginPage(driver);

		logoutFromPartner(dashboardPage);

		loginPage.enterUserName(GlobalConstants.AUTHPARTNER);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		dashboardPage.clickOnPoliciesTitle();

		requestPolicy(policiesPage, GlobalConstants.AUTHPOLICY_PARTLINK);

		requestPolicy(policiesPage, GlobalConstants.AUTHPOLICY_PARTLINK2);

		requestPolicy(policiesPage, GlobalConstants.DATAPOLICY_PARTLINK);

	}

	@Test(priority = 02, description = "Tabular View Of Partner Policy")
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

		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.Random_DATA);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		assertTrue(partnerPolicyMappingPage.isNoResultsFoundMessageDisplayed(),
				GlobalConstants.isNoResultsFoundMessageDisplayed);
		assertTrue(partnerPolicyMappingPage.isCancelButtonOfTextBoxDisplayed(),
				GlobalConstants.isCancelButtonOfTextBoxDisplayed);
		partnerPolicyMappingPage.clickOnCancelButtonOfTextBox();
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter("authPollink");
		partnerPolicyMappingPage.clickOnFilterResetButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		assertTrue(partnerPolicyMappingPage.isPartnerIdDescIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(partnerPolicyMappingPage.isPartnerIdAscIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeDescIconDisplayed(),
				GlobalConstants.isPartnerTypeDescIconDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeAscIconDisplayed(),
				GlobalConstants.isPartnerTypeAscIconDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupNameDescIconDisplayed(),
				GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupNameAscIconDisplayed(),
				GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(partnerPolicyMappingPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(partnerPolicyMappingPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(partnerPolicyMappingPage.isCreatedDateTimeDescISconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(partnerPolicyMappingPage.isCreatedDateTimeAscIconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescAscIcon);

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

	@Test(priority = 03, description = "Approve Reject Requested Policies")
	public void approveRejectRequestedPolicies() {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
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
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY_PARTLINK);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		assertTrue(partnerPolicyMappingPage.isStatusApprovedDisplayed(), GlobalConstants.isStatusApprovedDisplayed);
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertFalse(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);

		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
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

		loginAsAuthPartner(dashboardPage);

		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnFilterButton();
		policiesPage.selectActivatedStatusInFilter();
		assertTrue(policiesPage.isStatusApprovedDisplayed(), GlobalConstants.isStatusApprovedDisplayed);
		policiesPage.selectRejectedStatusInFilter();
		assertTrue(policiesPage.isStatusRejectedDisplayed(), GlobalConstants.isStatusRejectedDisplayed);

	}

	@Test(priority = 04, description = "View Requested Policie Details")
	public void viewRequestedPolicieDetails() {

		dashboardPage = new DashboardPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY_PARTLINK);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		assertTrue(partnerPolicyMappingPage.isViewButtoEnabled(), GlobalConstants.isViewButtoEnabled);
		partnerPolicyMappingPage.clickOnviewButton();

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

	private void logoutFromPartner(DashboardPage dashboardPage) {
		dashboardPage.clickOnProfileDropdown();
		dashboardPage.clickOnLogoutButton();

	}

	private void loginAsAuthPartner(DashboardPage dashboardPage) {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.AUTHPARTNER);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

	}

	private void requestPolicy(PoliciesPage policiesPage, String authPolicyName) {
		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterAuthPolicyNameDropdown(authPolicyName);
		policiesPage.enterComments(GlobalConstants.REQUEST);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

	}
}