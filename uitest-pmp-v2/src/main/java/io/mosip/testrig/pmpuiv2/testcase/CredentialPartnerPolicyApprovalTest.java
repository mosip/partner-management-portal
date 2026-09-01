package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.MapBiometricExtractorPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

/**
 * MOSIP-44662 - Approve / Reject a Credential Partner policy request as
 * Partner Admin.
 *
 * The suite logs in as the default Partner Admin user, so the approval
 * scenarios need no user switch; only the "incomplete mapping" scenario hops
 * over to the Credential Partner to raise a bare Step 1 request first.
 *
 * Covers TC_44662_01 to _05 and _07 to _10.
 */
@Test(dependsOnGroups = {
		"CredentialPartnerMapCredentialTypeTest" }, groups = { "CredentialPartnerPolicyApprovalTest" })
public class CredentialPartnerPolicyApprovalTest extends BaseClass {

	private BasePage basePage;
	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private PoliciesPage policiesPage;
	private PartnerPolicyMappingPage partnerPolicyMappingPage;
	private MapBiometricExtractorPage mapBiometricExtractorPage;

	private void initPages() {
		basePage = new BasePage(driver);
		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		policiesPage = new PoliciesPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);
		mapBiometricExtractorPage = new MapBiometricExtractorPage(driver);
	}

	/** Filters the policy linkage list down to a single policy request row. */
	private void openPolicyRequestByPolicyName(String policyName) {
		dashboardPage.clickOnPartnerPolicyMappingTab();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingTitleDisplayed);
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(policyName);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		basePage.scrollToStartPage();
	}

	@Test(priority = 1, description = "Verify a Pending For Approval Credential Partner policy request is visible to the "
			+ "Partner Admin with the Approve / Reject action, that the popup carries the biometric and credential type "
			+ "mapping sections, and that a fully mapped request is approved and moves to Approved. (TC 01,02,03,04,05)")
	public void pendingRequestVisibilityAndApproval() {
		initPages();

		openPolicyRequestByPolicyName(GlobalConstants.DATAPOLICY_PARTLINK);

		// The pending request is listed (TC_01)
		assertTrue(
				partnerPolicyMappingPage.isPolicyRowStatusDisplayed(GlobalConstants.DATAPOLICY_PARTLINK,
						GlobalConstants.PENDING_FOR_APPROVAL),
				GlobalConstants.isPendingPolicyRequestVisibleToAdmin);

		// The action menu offers Approve / Reject (TC_02)
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		assertTrue(partnerPolicyMappingPage.isApproveRejectButtonDisplayed(),
				GlobalConstants.isApproveRejectOptionDisplayed);
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();

		// The popup shows the policy details plus both mapping sections (TC_03)
		assertTrue(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		assertTrue(basePage.isTextPresentOnPage(GlobalConstants.BIO_EXTRACTOR_PROVIDER_MAPPING_SECTION),
				GlobalConstants.isBiometricMappingSectionDisplayedInPopup);
		assertTrue(basePage.isTextPresentOnPage(GlobalConstants.CREDENTIAL_TYPE_SECTION),
				GlobalConstants.isCredentialTypeSectionDisplayedInPopup);

		// A fully mapped request approves cleanly (TC_04)
		partnerPolicyMappingPage.clickOnApproveSubmitButton();
		assertTrue(partnerPolicyMappingPage.isConfirmationCustomButtonDisplayed(),
				GlobalConstants.isPolicyApprovedSuccessfully);
		partnerPolicyMappingPage.clickOnConfirmationCustomButton();

		// and the listing reflects the new status (TC_05)
		openPolicyRequestByPolicyName(GlobalConstants.DATAPOLICY_PARTLINK);
		assertTrue(
				partnerPolicyMappingPage.isPolicyRowStatusDisplayed(GlobalConstants.DATAPOLICY_PARTLINK,
						GlobalConstants.APPROVED),
				GlobalConstants.isPolicyStatusApprovedInList);
	}

	@Test(priority = 2, dependsOnMethods = "pendingRequestVisibilityAndApproval",
			description = "Verify approval is blocked with an error when the biometric extractor and credential type "
					+ "mappings are incomplete, the empty mapping sections are shown, and the request stays Pending For "
					+ "Approval. (TC 07,08,09,10)")
	public void approvalBlockedForIncompleteMapping() {
		initPages();

		// Raise a bare Step 1 request as the Credential Partner - no bio, no credential type
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_USER);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnRequestPolicyButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterAuthPolicyNameDropdown(GlobalConstants.AUTHPOLICY_PARTLINK2);
		policiesPage.enterComments(GlobalConstants.AUTHPOLICY_PARTLINK2);
		policiesPage.clickSubmitButton();
		// Credential Partners land on Step 2 instead of a confirmation screen; cancel straight out
		// so the request stays bare (no bio extractor, no credential type) for the admin check.
		assertTrue(mapBiometricExtractorPage.isMapBiometricExtractorPageDisplayed(),
				GlobalConstants.isMapBiometricExtractorPageDisplayedAfterRequest);
		mapBiometricExtractorPage.clickOnCancelButton();

		// Back to the Partner Admin
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(userid);
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		openPolicyRequestByPolicyName(GlobalConstants.AUTHPOLICY_PARTLINK2);
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertTrue(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);

		// The empty mapping section is rendered for the unmapped request (TC_10)
		assertTrue(basePage.isTextPresentOnPage(GlobalConstants.NO_BIO_EXTRACTORS_MAPPED),
				GlobalConstants.isEmptyMappingSectionDisplayed);

		// Approving is refused and the reason is shown (TC_07, TC_08, TC_09)
		partnerPolicyMappingPage.clickOnApproveSubmitButton();
		assertTrue(basePage.isTextPresentOnPage(GlobalConstants.APPROVE_BLOCKED_BOTH_MAPPINGS_MISSING),
				GlobalConstants.isApprovalBlockedErrorDisplayed);

		// and the request is left exactly as it was (TC_08, TC_09)
		openPolicyRequestByPolicyName(GlobalConstants.AUTHPOLICY_PARTLINK2);
		assertTrue(
				partnerPolicyMappingPage.isPolicyRowStatusDisplayed(GlobalConstants.AUTHPOLICY_PARTLINK2,
						GlobalConstants.PENDING_FOR_APPROVAL),
				GlobalConstants.isPolicyStatusStillPendingAfterBlockedApproval);
	}
}
