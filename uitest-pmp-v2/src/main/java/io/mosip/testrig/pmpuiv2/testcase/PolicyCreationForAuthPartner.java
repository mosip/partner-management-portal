package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class PolicyCreationForAuthPartner extends BaseClass {
	private BasePage basePage;
	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private PoliciesPage policiesPage;
	private AuthPolicyPage authPolicyPage;
	private PartnerPolicyMappingPage partnerPolicyMappingPage;

	@Test(priority = 1, description = "Policy creation and filter")
	public void verifyingPolicyCreationAndFilter() {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		basePage = new BasePage(driver);
		loginPage = new LoginPage(driver);

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();

		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.DEFAULT_POLICY);
		authPolicyPage.enterpolicyDescription(GlobalConstants.DEFAULT_POLICY);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();
		basePage.scrollToStartPage();

		createAuthPolicy(GlobalConstants.AUTHPOLICY01, GlobalConstants.AUTHPOLICY01_DESCRIPTION);

		basePage.scrollToStartPage();

		createAuthPolicy(GlobalConstants.AUTHPOLICY02, GlobalConstants.AUTHPOLICY02_DESCRIPTION);

		basePage.scrollToStartPage();

		createAuthPolicy(GlobalConstants.PENDING_POLICY, GlobalConstants.PENDING_POLICY_DESCRIPTION);

		basePage.scrollToStartPage();

		createAuthPolicy(GlobalConstants.DEACTIVATE_AUTHPOLICY, GlobalConstants.DEACTIVATE_POLICY_DESCRIPTION);

		basePage.scrollToStartPage();
		authPolicyPage.clickOnFilterButton();

		filterAndPublishAuthPolicy(GlobalConstants.DEFAULT_POLICY);

		filterAndPublishAuthPolicy(GlobalConstants.AUTHPOLICY01);

		filterAndPublishAuthPolicy(GlobalConstants.AUTHPOLICY02);

		filterAndPublishAuthPolicy(GlobalConstants.PENDING_POLICY);

		filterAndDeactivateAuthPolicy(GlobalConstants.DEACTIVATE_AUTHPOLICY);

		loginAsAuthPartner();

		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		dashboardPage.clickOnPoliciesTitle();

		assertTrue(policiesPage.isPoliciesEmptyTableDisplayed(), GlobalConstants.isPoliciesEmptyTableDisplayed);
		assertTrue(policiesPage.isPoliciesEmptyTableEnabled(), GlobalConstants.isRequestPolicyEnabled);
		policiesPage.clickOnRequestPolicyButton();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.selectPartnerIdDropdown();
		assertTrue(policiesPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		policiesPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		policiesPage.enterComments(GlobalConstants.DEFAULT_POLICY);
		assertTrue(policiesPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		policiesPage.clickSubmitButton();
		assertTrue(policiesPage.isPolicySubmittedSuccessfullyDisplayed(), GlobalConstants.isPolicySubmittedSuccessfullyDisplayed);
		
		policiesPage.clickOnHomeButton();
		dashboardPage.clickOnPoliciesTitle();
		assertTrue(policiesPage.isListOfPolicyRequestedDisplayed(),
				GlobalConstants.isListOfPolicyRequestedTextDisplayed);
		assertTrue(policiesPage.isNextPageDisplayed(), GlobalConstants.isNextPageDisplayed);
		assertTrue(policiesPage.isPreviousPageDisplayed(), GlobalConstants.isPreviousPageDisplayed);
		assertTrue(policiesPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalDisplayed);

		policiesPage.clickOnElipcisButton();
		policiesPage.clickOnCardViewButton();
		assertTrue(policiesPage.isPolicyDetailsPartnerIdLabelDisplayed(),
				GlobalConstants.isPolicyDetailsPartnerIdLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyGroupNameLabelDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyGroupNameLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyGroupNameContextDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyGroupNameContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPartnerTypeLabelDisplayed(),
				GlobalConstants.isPolicyDetailsPartnerTypeLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPartnerTypeContextDisplayed(),
				GlobalConstants.isPolicyDetailsPartnerTypeContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyNameLabelDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyNameLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyNameContextDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyNameContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyGroupDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyGroupDescriptionLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyGroupDescriptionContextDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyGroupDescriptionContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyNameDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyNameDescriptionLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyNameDescriptionContextDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyNameDescriptionContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsCommentsDisplayed(), GlobalConstants.isPolicyDetailsCommentsDisplayed);
		assertTrue(policiesPage.isPolicyViewPageBackButtonEnabled(), GlobalConstants.isPolicyViewPageBackButtonEnabled);
		policiesPage.clickOnBackButton();

		assertTrue(policiesPage.isPartnerIdDescIconDisplayed(), GlobalConstants.isPartnerIdDescIconDisplayed);
		assertTrue(policiesPage.isPartnerIdAscIconDisplayed(), GlobalConstants.isPartnerIdAscIconDisplayed);
		assertTrue(policiesPage.isPartnerTypeDescIconDisplayed(), GlobalConstants.isPartnerTypeDescIcon);
		assertTrue(policiesPage.isPartnerTypeAscIconDisplayed(), GlobalConstants.isPartnerTypeAscIcon);
		assertTrue(policiesPage.isPolicyGroupNameDescIconDisplayed(), GlobalConstants.isPolicyGroupNameDescIconDisplayed);
		assertTrue(policiesPage.isPolicyGroupNameAscIconDisplayed(), GlobalConstants.isPolicyGroupNameAscIconDisplayed);
		assertTrue(policiesPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescIconDisplayed);
		assertTrue(policiesPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameAscIconDisplayed);
		assertTrue(policiesPage.isCreatedDateTimeDescIconDisplayed(), GlobalConstants.isCreatedDateTimeDescIconDisplayed);
		assertTrue(policiesPage.isCreatedDateTimeAscIconDisplayed(), GlobalConstants.isCreatedDateTimeAscIconDisplayed);

		policiesPage.clickOnFilterButton();
		policiesPage.clickOnPolicyPartnerIdFilter();
		policiesPage.clickOnPolicyPartnerTypeFilter();
		policiesPage.clickOnPolicyGroupFilter();
		policiesPage.clickOnPolicyNameFilter();
		policiesPage.clickOnPolicyStatusFilter();
		policiesPage.clickOnPolicyNameDescendingBtn();
		policiesPage.clickOnPolicyNameAscendingBtn();
		policiesPage.clickOnFilterResetButton();
		policiesPage.isFilterButtonButtonEnabled();

		policiesPage.clickOnPolicyListItem1();
		assertTrue(policiesPage.isSubTitleHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(policiesPage.isTitleBackIconDisplayed(), GlobalConstants.isBackArrow);
		policiesPage.clickOnTitleBackIcon();
		assertTrue(policiesPage.isTitleOfPolicyPageDisplayed(), GlobalConstants.isTitleOfPolicyPageDisplayed);
		assertTrue(policiesPage.isRequestPolicyButtonDisplayed(), GlobalConstants.isRequestPolicyButtonDisplayed);

		requestPolicy(GlobalConstants.PENDING_POLICY);

		requestPolicy(GlobalConstants.AUTHPOLICY02);

		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterInvalidPolicyNameDropdown(GlobalConstants.DEACTIVATE_AUTHPOLICY);
		assertTrue(policiesPage.isNoDataAvailableDisplayed(), GlobalConstants.isNoDataAvailableDisplayed);
		policiesPage.clickOnRequestPoliciesFormClearButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterInvalidPolicyNameDropdown(GlobalConstants.AUTHPOLICY01);
		assertTrue(policiesPage.isPolicyNameDisplayed(), GlobalConstants.isPolicyNameDisplayed);
		assertTrue(policiesPage.isPolicyDescriptionDisplayed(), GlobalConstants.isPolicyDescriptionDisplayed);
		policiesPage.enterComments(GlobalConstants.DEFAULT_POLICY);
		policiesPage.enterComments(GlobalConstants.SPACE);
		policiesPage.enterComments(GlobalConstants.AUTHPOLICY01_DESCRIPTION);
		policiesPage.clickOnRequestPoliciesFormClearButton();

	}

	@Test(priority = 2, description = "Partner-Policy mapping")
	public void partnerPolicyMapping() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnFilterButton();

		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.DEFAULT_POLICY);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertTrue(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		assertTrue(partnerPolicyMappingPage.isConfirmationPopupDetailedMessageDisplayed(),
				GlobalConstants.isConfirmationPopupDetailedMessageDisplayed);
		assertTrue(partnerPolicyMappingPage.isApproveRejectButtonDisplayed(),
				GlobalConstants.isApproveRejectButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isApproveSubmitButtonDisplayed(),
				GlobalConstants.isApproveSubmitButtonDisplayed);
		partnerPolicyMappingPage.clickOnApproveSubmitButton();

		partnerPolicyMappingPage.clickOnFilterResetButton();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY02);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertTrue(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		partnerPolicyMappingPage.clickOnRejectButton();
		partnerPolicyMappingPage.clickOnFilterResetButton();
	}

	private void createAuthPolicy(String policyNameValue, String policyDescValue) {
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(policyNameValue);
		authPolicyPage.enterpolicyDescription(policyDescValue);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();
	}

	private void filterAndPublishAuthPolicy(String policyNameFilterValue) {
		authPolicyPage.enterPolicyNameInFilter(policyNameFilterValue);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();

	}

	private void filterAndDeactivateAuthPolicy(String policyNameFilterValue) {
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(policyNameFilterValue);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnDeactivateButton();
		authPolicyPage.clickOnDeactivateConfirmButton();

	}

	private void requestPolicy(String authPolicyName) {
		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterAuthPolicyNameDropdown(authPolicyName);
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
}
