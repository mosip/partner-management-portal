package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "ManualAdjudicationPartnerTest" }, groups = { "ManualAdjudicationPolicyTest" })
public class ManualAdjudicationPolicyTest extends BaseClass {

	private DashboardPage dashboardPage;
	private PoliciesPage policiesPage;
	private AuthPolicyPage authPolicyPage;
	private PartnerPolicyMappingPage partnerPolicyMappingPage;
	private BasePage basePage;

	@Test(priority = 1, description = "Create and publish auth policies for MA partner policy linking")
	public void createAuthPoliciesForManualAdjudication() {
		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		basePage = new BasePage(driver);

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();

		createAndPublishAuthPolicy(GlobalConstants.MA_POLICY_01, GlobalConstants.MA_POLICY_01_DESCRIPTION);
		createAndPublishAuthPolicy(GlobalConstants.MA_POLICY_03, GlobalConstants.MA_POLICY_03_DESCRIPTION);
	}

	@Test(priority = 2, description = "Request / approve / reject / view policy linking for Manual Adjudication Partner",
			dependsOnMethods = "createAuthPoliciesForManualAdjudication")
	public void partnerPolicyLinkingForManualAdjudication() {
		dashboardPage = new DashboardPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		basePage = new BasePage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnRequestPolicyButton();

		assertTrue(partnerPolicyMappingPage.isRequestPolicyMandatoryMsgDisplayed(),
				GlobalConstants.isRequestPolicyMandatoryMsgDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyPartnerTypeDropdownDisplayed(),
				GlobalConstants.isRequestPolicyPartnerTypeDropdownDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyPartnerIdDropdownDisplayed(),
				GlobalConstants.isRequestPolicyPartnerIdDropdownDisplayed);

		partnerPolicyMappingPage.selectPartnerType(GlobalConstants.MANUAL_ADJUDICATION_PARTNER);
		partnerPolicyMappingPage.selectPartnerIdDropdown(GlobalConstants.MA_PARTNER_USER);
		partnerPolicyMappingPage.selectPolicyName(GlobalConstants.MA_POLICY_01);
		partnerPolicyMappingPage.enterRequestPolicyComment(GlobalConstants.MA_POLICY_01_DESCRIPTION);
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesFormSubmitButtonEnabled(),
				GlobalConstants.isRequestPoliciesFormSubmitButtonEnabled);
		partnerPolicyMappingPage.clickOnRequestPoliciesFormSubmitButton();

		assertTrue(partnerPolicyMappingPage.isConfirmationSuccessIconDisplayed(),
				GlobalConstants.isConfirmationSuccessIconDisplayed);
		partnerPolicyMappingPage.clickOnConfirmationCustomButton();
		assertTrue(partnerPolicyMappingPage.isApprovePopupTitleDisplayed(),
				GlobalConstants.isApprovePopupTitleDisplayed);
		partnerPolicyMappingPage.clickOnApprovePopupSubmitButton();
		verifyStatus(GlobalConstants.MA_POLICY_01, GlobalConstants.APPROVED);

		requestPolicy(GlobalConstants.MA_POLICY_01, GlobalConstants.MA_POLICY_01_DESCRIPTION);
		basePage.scrollToStartPage();
		assertTrue(partnerPolicyMappingPage.isRequestPolicyErrorMsgDisplayed(),
				GlobalConstants.isRequestPolicyErrorMsgDisplayed);
		partnerPolicyMappingPage.clickOnRequestPoliciesFormClearButton();
		partnerPolicyMappingPage.clickOnRequestPoliciesFormCancelButton();

		requestPolicy(GlobalConstants.MA_POLICY_03, GlobalConstants.MA_POLICY_03_DESCRIPTION);
		partnerPolicyMappingPage.clickOnConfirmationGoBackButton();
		verifyStatus(GlobalConstants.MA_POLICY_03, GlobalConstants.PENDING_FOR_APPROVAL);

		clickOnActionButton(GlobalConstants.MA_POLICY_03);
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertTrue(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		partnerPolicyMappingPage.clickOnRejectButton();
		partnerPolicyMappingPage.clickOnFilterResetButton();
		verifyStatus(GlobalConstants.MA_POLICY_03, GlobalConstants.REJECTED);

		requestPolicy(GlobalConstants.MA_POLICY_03, GlobalConstants.MA_POLICY_03_DESCRIPTION);
		partnerPolicyMappingPage.clickOnConfirmationCustomButton();
		partnerPolicyMappingPage.clickOnApprovePopupSubmitButton();

		clickOnActionButton(GlobalConstants.MA_POLICY_03);
		partnerPolicyMappingPage.clickOnViewButton();
		assertEquals(partnerPolicyMappingPage.getPageTitle(), GlobalConstants.VIEW_PARTNER_POLICY_PAGE,
				GlobalConstants.isViewPartnerPolicyLinkingPageTitleDisplayed);
		partnerPolicyMappingPage.clickOnTitleBackIcon();
	}

	private void createAndPublishAuthPolicy(String policyName, String description) {
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(policyName);
		authPolicyPage.enterpolicyDescription(description);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		assertTrue(authPolicyPage.isPolicySavedAsDraftMessageDisplayed(),
				GlobalConstants.isPolicySavedAsDraftMessageDisplayed);
		authPolicyPage.clickOnGoBackButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(policyName);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
	}

	private void requestPolicy(String policyName, String comment) {
		partnerPolicyMappingPage.clickOnRequestPolicyButton();
		partnerPolicyMappingPage.selectPartnerType(GlobalConstants.MANUAL_ADJUDICATION_PARTNER);
		partnerPolicyMappingPage.selectPartnerIdDropdown(GlobalConstants.MA_PARTNER_USER);
		partnerPolicyMappingPage.selectPolicyName(policyName);
		partnerPolicyMappingPage.enterRequestPolicyComment(comment);
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesFormSubmitButtonEnabled(),
				GlobalConstants.isRequestPoliciesFormSubmitButtonEnabled);
		partnerPolicyMappingPage.clickOnRequestPoliciesFormSubmitButton();
	}

	private void verifyStatus(String policyName, String status) {
		assertTrue(partnerPolicyMappingPage.isStatusDisplayedForPolicyName(policyName, status),
				GlobalConstants.isStatusDisplayedForPolicyName);
	}

	private void clickOnActionButton(String policyName) {
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(policyName);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
	}
}