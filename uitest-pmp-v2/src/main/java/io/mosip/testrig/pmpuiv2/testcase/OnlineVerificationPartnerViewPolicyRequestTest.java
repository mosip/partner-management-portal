package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.MapBiometricExtractorPage;
import io.mosip.testrig.pmpuiv2.pages.MapCredentialTypePage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.ViewPolicyRequestPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

/**
 * Online Verification Partner: Partner Admin views a submitted policy request
 * (View Partner-Policy Linking, opened from Dashboard > Partner Policy Linking).
 *
 * Builds on the request {@link OnlineVerificationPartnerMapCredentialTypeTest}
 * took through all three steps (DATAPOLICY_PARTLINK), and raises a second one
 * (AUTHPOLICY_PARTLINK) to view the partially mapped states. The first is
 * approved and the second rejected along the way, so every lifecycle status the
 * suite can reach is viewed, and their colour coding compared.
 *
 * The status colours are collected across scenarios - TestNG runs every method
 * of a class on one instance, and the dependsOnMethods chain fixes the order.
 *
 * Covered here: TC 01, 02, 03, 04, 05, 06, 07, 08, 10, 11, 12, 16, 17, 21, 22,
 * 23 and 29, plus Map Credential Type TC 06 (non-admin) and TC 07 (mapping
 * closed once final).
 * The page as built differs from the sheet in places, and the assertions follow
 * the page: the title is "View Partner-Policy Linking", Section 1 has no
 * Partner ID Alias or policy descriptions, and Comments is a fixed pair of
 * cards (admin decision above the partner's request comment) rather than a
 * history list.
 * Not automated: TC 09 (Deactivated - nothing in the suite deactivates a
 * request), 13 (language - already covered by running the suite per login
 * language with locale-driven assertions), 14 (cross-browser / responsive),
 * 15 (audit log), 18 (per-permission admin), 19 (backend failure), 20 (session
 * timeout), 24 (no comments - the partner comment is mandatory, so the card is
 * never empty), 25/27 (maximum-length text), 26 (Face + Iris + Finger mapped
 * together - depends on the extractor configurations the environment has),
 * 28 (10+ comments - the page renders two cards), 30 (UTC Created On - the
 * sheet text was cut off).
 */
@Test(dependsOnGroups = {
		"OnlineVerificationPartnerMapCredentialTypeTest" }, groups = { "OnlineVerificationPartnerViewPolicyRequestTest" })
public class OnlineVerificationPartnerViewPolicyRequestTest extends BaseClass {

	private BasePage basePage;
	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private PartnerPolicyMappingPage partnerPolicyMappingPage;
	private MapBiometricExtractorPage mapBiometricExtractorPage;
	private MapCredentialTypePage mapCredentialTypePage;
	private ViewPolicyRequestPage viewPolicyRequestPage;

	private String pendingStatusColour;
	private String approvedStatusColour;

	private void initPages() {
		basePage = new BasePage(driver);
		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);
		mapBiometricExtractorPage = new MapBiometricExtractorPage(driver);
		mapCredentialTypePage = new MapCredentialTypePage(driver);
		viewPolicyRequestPage = new ViewPolicyRequestPage(driver);
	}

	private void openPolicyLinkingFilteredBy(String policyName) {
		dashboardPage.clickOnPartnerPolicyMappingTab();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingTitleDisplayed);
		filterPolicyLinkingBy(policyName);
	}

	private void filterPolicyLinkingBy(String policyName) {
		partnerPolicyMappingPage.clickOnFilterResetButton();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(policyName);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		basePage.scrollToStartPage();
	}

	/** Opens the view page for the OVP request against the given policy through the row's View option. */
	private void openViewPolicyRequest(String policyName) {
		partnerPolicyMappingPage.clickOnActionButtonByPartnerAndPolicy(GlobalConstants.OVP_PARTNER_USER, policyName);
		partnerPolicyMappingPage.clickOnViewOptionByPartnerAndPolicy(GlobalConstants.OVP_PARTNER_USER, policyName);
		assertTrue(viewPolicyRequestPage.isViewPolicyRequestPageDisplayed(),
				GlobalConstants.isViewPolicyRequestPageDisplayed);
	}

	private void assertRequestInfo(String policyName) {
		assertTrue(viewPolicyRequestPage.getPartnerIdHeaderText().contains(GlobalConstants.OVP_PARTNER_USER),
				GlobalConstants.isPolicyRequestInfoCorrect);
		assertTrue(viewPolicyRequestPage.isCreatedOnDisplayed(), GlobalConstants.isCreatedOnShownOnViewPage);
		assertEquals(viewPolicyRequestPage.getPartnerType(), ViewPolicyRequestPage.ONLINE_VERIFICATION_PARTNER_TYPE,
				GlobalConstants.isPolicyRequestInfoCorrect);
		assertEquals(viewPolicyRequestPage.getOrganisation(), GlobalConstants.ORGANISATION_NAME,
				GlobalConstants.isPolicyRequestInfoCorrect);
		assertFalse(viewPolicyRequestPage.getPolicyId().isEmpty(), GlobalConstants.isPolicyRequestInfoCorrect);
		assertEquals(viewPolicyRequestPage.getPolicyName(), policyName, GlobalConstants.isPolicyRequestInfoCorrect);
		assertEquals(viewPolicyRequestPage.getPolicyGroup(), GlobalConstants.DEFAULT_POLICYGROUP,
				GlobalConstants.isPolicyRequestInfoCorrect);
		assertFalse(viewPolicyRequestPage.getPartnerStatus().isEmpty(), GlobalConstants.isPolicyRequestInfoCorrect);
	}

	/** Both mapping sections stay on screen whatever the request's mapping state. */
	private void assertMappingSectionsDisplayed() {
		assertTrue(viewPolicyRequestPage.isBiometricMappingSectionDisplayed(),
				GlobalConstants.isBiometricMappingSectionDisplayedOnView);
		assertTrue(viewPolicyRequestPage.isCredentialTypeSectionDisplayed(),
				GlobalConstants.isCredentialTypeSectionDisplayedOnView);
	}

	private void assertBiometricMapped() {
		List<List<String>> rows = viewPolicyRequestPage.getBiometricMappingRows();
		assertFalse(rows.isEmpty(), GlobalConstants.isBiometricMappingShownOnView);
		for (List<String> row : rows) {
			assertFalse(row.get(0).isEmpty() || GlobalConstants.NOT_MAPPED_VALUE.equals(row.get(0)),
					GlobalConstants.isBiometricMappingShownOnView);
		}
	}

	private void assertReadOnly() {
		assertEquals(viewPolicyRequestPage.getEditableFieldCount(), 0, GlobalConstants.isViewPageReadOnly);
		assertTrue(viewPolicyRequestPage.isBackButtonDisplayed(), GlobalConstants.isViewPageReadOnly);
	}

	/** Approve / Reject from the listing; the popup closes back onto the listing. */
	private void decideRequest(String policyName, boolean approve) {
		partnerPolicyMappingPage.clickOnActionButtonByPartnerAndPolicy(GlobalConstants.OVP_PARTNER_USER, policyName);
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertTrue(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		if (approve) {
			partnerPolicyMappingPage.clickOnApproveSubmitButton();
		} else {
			partnerPolicyMappingPage.clickOnRejectButton();
		}
		filterPolicyLinkingBy(policyName);
	}

	private void loginAs(String userName, String userPassword) {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.login(userName, userPassword);
	}

	@Test(priority = 1, description = "Verify the View page opens both by clicking the policy request row and through the "
			+ "View option of the action menu, and that Back returns to the Partner Policy Linking listing. (TC 01,02,10)")
	public void openViewPageFromListing() {
		initPages();
		openPolicyLinkingFilteredBy(GlobalConstants.DATAPOLICY_PARTLINK);

		// Clicking the row itself (TC_01)
		partnerPolicyMappingPage.clickOnPolicyRequestRow(GlobalConstants.OVP_PARTNER_USER,
				GlobalConstants.DATAPOLICY_PARTLINK);
		assertTrue(viewPolicyRequestPage.isViewPolicyRequestPageDisplayed(),
				GlobalConstants.isViewPolicyRequestOpenedByRowClick);
		assertEquals(viewPolicyRequestPage.getPolicyName(), GlobalConstants.DATAPOLICY_PARTLINK,
				GlobalConstants.isViewPolicyRequestOpenedByRowClick);
		assertMappingSectionsDisplayed();
		assertTrue(viewPolicyRequestPage.isCommentsSectionDisplayed(), GlobalConstants.isCommentsSectionDisplayedOnView);

		// Back lands on the listing (TC_10). Whether the applied filter survives is not
		// asserted - the listing keeps its filters in component state, which is rebuilt on return.
		viewPolicyRequestPage.clickOnBackButton();
		assertTrue(partnerPolicyMappingPage.isPolicyLinkingListDisplayed(),
				GlobalConstants.isBackNavigatesToPolicyLinkingList);

		// Through the View option of the action menu (TC_02)
		filterPolicyLinkingBy(GlobalConstants.DATAPOLICY_PARTLINK);
		openViewPolicyRequest(GlobalConstants.DATAPOLICY_PARTLINK);
		assertEquals(viewPolicyRequestPage.getPolicyName(), GlobalConstants.DATAPOLICY_PARTLINK,
				GlobalConstants.isViewPolicyRequestOpenedByViewOption);
		assertReadOnly();
	}

	@Test(priority = 2, dependsOnMethods = "openViewPageFromListing",
			description = "Verify a fully mapped Pending For Approval request shows its request information, biometric "
					+ "extractor mapping, credential type and both comment cards, read only. (TC 03,04,05,06,22)")
	public void pendingRequestDetails() {
		initPages();
		openPolicyLinkingFilteredBy(GlobalConstants.DATAPOLICY_PARTLINK);
		openViewPolicyRequest(GlobalConstants.DATAPOLICY_PARTLINK);

		// Section 1 - policy request information (TC_03)
		assertEquals(viewPolicyRequestPage.getStatusText(), GlobalConstants.PENDING_FOR_APPROVAL,
				GlobalConstants.isStatusShownOnViewPage);
		pendingStatusColour = viewPolicyRequestPage.getStatusColourClass();
		assertRequestInfo(GlobalConstants.DATAPOLICY_PARTLINK);

		// Section 2 - biometric extractor mapping (TC_04)
		assertMappingSectionsDisplayed();
		assertBiometricMapped();

		// Section 3 - credential type (TC_05)
		String credentialType = viewPolicyRequestPage.getCredentialType();
		assertFalse(credentialType.isEmpty(), GlobalConstants.isCredentialTypeShownOnView);
		assertNotEquals(credentialType, GlobalConstants.NOT_MAPPED_VALUE, GlobalConstants.isCredentialTypeShownOnView);

		// Comments - admin decision card above the partner's request comment (TC_06)
		assertTrue(viewPolicyRequestPage.isCommentsSectionDisplayed(), GlobalConstants.isCommentsSectionDisplayedOnView);
		assertTrue(viewPolicyRequestPage.isAdminCommentCardDisplayed(), GlobalConstants.isCommentsSectionDisplayedOnView);
		assertTrue(viewPolicyRequestPage.isPartnerCommentCardDisplayed(),
				GlobalConstants.isCommentsSectionDisplayedOnView);
		assertEquals(viewPolicyRequestPage.getAdminCommentStatus(), GlobalConstants.PENDING_FOR_APPROVAL,
				GlobalConstants.isAdminCommentStatusCorrect);
		assertEquals(viewPolicyRequestPage.getPartnerComment(), GlobalConstants.OVP_POLICY_REQUEST_COMMENT,
				GlobalConstants.isPartnerCommentShownOnView);
		assertTrue(viewPolicyRequestPage.isAdminCommentAbovePartnerComment(), GlobalConstants.isLatestCommentFirst);

		// Nothing on the page is editable; the only action is the pending-only Approve / Reject (TC_22)
		assertReadOnly();
		assertTrue(viewPolicyRequestPage.isApproveRejectButtonPresent(),
				GlobalConstants.isApproveRejectOfferedWhilePending);
	}

	@Test(priority = 3, dependsOnMethods = "pendingRequestDetails",
			description = "Verify a request with only Step 1 done shows both mappings as not mapped, and one with Steps 1 "
					+ "and 2 done shows the biometric mapping with the credential type still not mapped - with every "
					+ "section kept on screen. (TC 11,12)")
	public void partialWorkflowViews() {
		initPages();

		// Step 1 only - raised by the admin, Step 2 cancelled
		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnRequestPolicyButton();
		partnerPolicyMappingPage.selectPartnerType(MapCredentialTypePage.ONLINE_VERIFICATION_PARTNER_TYPE);
		partnerPolicyMappingPage.selectPartnerIdDropdown(GlobalConstants.OVP_PARTNER_USER);
		partnerPolicyMappingPage.selectPolicyName(GlobalConstants.AUTHPOLICY_PARTLINK);
		partnerPolicyMappingPage.enterRequestPolicyComment(GlobalConstants.OVP_POLICY_REQUEST_COMMENT);
		partnerPolicyMappingPage.clickOnRequestPoliciesFormSubmitButton();
		assertTrue(mapBiometricExtractorPage.isMapBiometricExtractorPageDisplayed(),
				GlobalConstants.isOvpRequestRoutedToBiometricMapping);
		mapBiometricExtractorPage.clickOnCancelButton();

		filterPolicyLinkingBy(GlobalConstants.AUTHPOLICY_PARTLINK);
		openViewPolicyRequest(GlobalConstants.AUTHPOLICY_PARTLINK);
		assertRequestInfo(GlobalConstants.AUTHPOLICY_PARTLINK);
		assertMappingSectionsDisplayed();
		assertTrue(viewPolicyRequestPage.isNoBiometricMappingMessageDisplayed(),
				GlobalConstants.isBiometricNotMappedShownOnView);
		assertEquals(viewPolicyRequestPage.getCredentialType(), GlobalConstants.NOT_MAPPED_VALUE,
				GlobalConstants.isCredentialTypeNotMappedShownOnView);

		// Steps 1 and 2 - map the extractor, then leave Step 3 undone
		viewPolicyRequestPage.clickOnBackButton();
		filterPolicyLinkingBy(GlobalConstants.AUTHPOLICY_PARTLINK);
		partnerPolicyMappingPage.clickOnActionButtonByPartnerAndPolicy(GlobalConstants.OVP_PARTNER_USER,
				GlobalConstants.AUTHPOLICY_PARTLINK);
		partnerPolicyMappingPage.clickOnOvpMapBiometricExtractorOption(GlobalConstants.OVP_PARTNER_USER,
				GlobalConstants.AUTHPOLICY_PARTLINK);
		mapBiometricExtractorPage.mapFirstAvailableExtractorAndSave();
		assertTrue(
				mapCredentialTypePage
						.isMapCredentialTypePageTitleDisplayed(MapCredentialTypePage.MAP_CREDENTIAL_TYPE_TITLE),
				GlobalConstants.isMapCredentialTypePageTitleDisplayed);
		mapCredentialTypePage.clickOnCancelButton();

		filterPolicyLinkingBy(GlobalConstants.AUTHPOLICY_PARTLINK);
		openViewPolicyRequest(GlobalConstants.AUTHPOLICY_PARTLINK);
		assertMappingSectionsDisplayed();
		assertBiometricMapped();
		assertEquals(viewPolicyRequestPage.getCredentialType(), GlobalConstants.NOT_MAPPED_VALUE,
				GlobalConstants.isCredentialTypeNotMappedShownOnView);
	}

	@Test(priority = 4, dependsOnMethods = "partialWorkflowViews",
			description = "Approve the fully mapped request, then verify its view shows Approved with every section "
					+ "populated, read only with no Approve / Reject, and that both mappings are closed on the listing. "
					+ "(TC 07,22 and Map Credential Type TC 07)")
	public void approvedRequestDetails() {
		initPages();
		openPolicyLinkingFilteredBy(GlobalConstants.DATAPOLICY_PARTLINK);
		decideRequest(GlobalConstants.DATAPOLICY_PARTLINK, true);
		assertTrue(
				partnerPolicyMappingPage.isPolicyRowStatusDisplayed(GlobalConstants.OVP_PARTNER_USER,
						GlobalConstants.DATAPOLICY_PARTLINK, GlobalConstants.APPROVED),
				GlobalConstants.isPolicyStatusApprovedInList);

		// Mapping cannot be edited once the request is final (Map Credential Type TC_07)
		partnerPolicyMappingPage.clickOnActionButtonByPartnerAndPolicy(GlobalConstants.OVP_PARTNER_USER,
				GlobalConstants.DATAPOLICY_PARTLINK);
		assertTrue(
				partnerPolicyMappingPage.isOvpMapBiometricExtractorOptionDisabled(GlobalConstants.OVP_PARTNER_USER,
						GlobalConstants.DATAPOLICY_PARTLINK),
				GlobalConstants.isMappingBlockedForFinalStatus);
		assertTrue(
				partnerPolicyMappingPage.isOvpMapCredentialTypeOptionDisabled(GlobalConstants.OVP_PARTNER_USER,
						GlobalConstants.DATAPOLICY_PARTLINK),
				GlobalConstants.isMappingBlockedForFinalStatus);
		partnerPolicyMappingPage.clickOnActionButtonByPartnerAndPolicy(GlobalConstants.OVP_PARTNER_USER,
				GlobalConstants.DATAPOLICY_PARTLINK);

		// The approved view (TC_07) - approved requests read the partner's live mappings
		openViewPolicyRequest(GlobalConstants.DATAPOLICY_PARTLINK);
		assertEquals(viewPolicyRequestPage.getStatusText(), GlobalConstants.APPROVED,
				GlobalConstants.isStatusShownOnViewPage);
		approvedStatusColour = viewPolicyRequestPage.getStatusColourClass();
		assertRequestInfo(GlobalConstants.DATAPOLICY_PARTLINK);
		assertMappingSectionsDisplayed();
		assertBiometricMapped();
		assertNotEquals(viewPolicyRequestPage.getCredentialType(), GlobalConstants.NOT_MAPPED_VALUE,
				GlobalConstants.isCredentialTypeShownOnView);
		assertEquals(viewPolicyRequestPage.getAdminCommentStatus(), GlobalConstants.APPROVED,
				GlobalConstants.isAdminCommentStatusCorrect);

		// Read only, and the decision can no longer be taken from here (TC_22)
		assertReadOnly();
		assertFalse(viewPolicyRequestPage.isApproveRejectButtonPresent(),
				GlobalConstants.isApproveRejectHiddenOnceFinal);
	}

	@Test(priority = 5, dependsOnMethods = "approvedRequestDetails",
			description = "Reject the partially mapped request, then verify its view shows Rejected with the decision in "
					+ "Comments and the saved configuration preserved, and that Pending For Approval, Approved and "
					+ "Rejected each carry a distinct status colour. (TC 08,29)")
	public void rejectedRequestDetailsAndStatusColours() {
		initPages();
		openPolicyLinkingFilteredBy(GlobalConstants.AUTHPOLICY_PARTLINK);
		decideRequest(GlobalConstants.AUTHPOLICY_PARTLINK, false);
		assertTrue(
				partnerPolicyMappingPage.isPolicyRowStatusDisplayed(GlobalConstants.OVP_PARTNER_USER,
						GlobalConstants.AUTHPOLICY_PARTLINK, GlobalConstants.REJECTED),
				GlobalConstants.isPolicyRejectedSuccessfully);

		// The rejected view (TC_08)
		openViewPolicyRequest(GlobalConstants.AUTHPOLICY_PARTLINK);
		assertEquals(viewPolicyRequestPage.getStatusText(), GlobalConstants.REJECTED,
				GlobalConstants.isStatusShownOnViewPage);
		assertEquals(viewPolicyRequestPage.getAdminCommentStatus(), GlobalConstants.REJECTED,
				GlobalConstants.isAdminCommentStatusCorrect);
		assertEquals(viewPolicyRequestPage.getPartnerComment(), GlobalConstants.OVP_POLICY_REQUEST_COMMENT,
				GlobalConstants.isPartnerCommentShownOnView);
		assertRequestInfo(GlobalConstants.AUTHPOLICY_PARTLINK);
		assertMappingSectionsDisplayed();
		assertBiometricMapped();
		assertReadOnly();
		assertFalse(viewPolicyRequestPage.isApproveRejectButtonPresent(),
				GlobalConstants.isApproveRejectHiddenOnceFinal);

		// Each lifecycle status has its own colour (TC_29)
		String rejectedStatusColour = viewPolicyRequestPage.getStatusColourClass();
		Set<String> colours = new HashSet<>(List.of(pendingStatusColour, approvedStatusColour, rejectedStatusColour));
		assertEquals(colours.size(), 3, GlobalConstants.isStatusColourDistinct);
	}

	@Test(priority = 6, description = "Verify opening the View page directly with no selected request, or with a malformed "
			+ "request identifier, shows no request data and returns to the Partner Policy Linking listing. (TC 16,23)")
	public void directNavigationWithoutValidRequest() {
		initPages();
		// Land on the portal first so the listing has data to render on the redirect
		dashboardPage.clickOnPartnerPolicyMappingTab();
		assertTrue(partnerPolicyMappingPage.isPolicyLinkingListDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingTitleDisplayed);

		// No request selected - the page has nothing to show (TC_16)
		viewPolicyRequestPage.openWithSelectedRequest(null);
		assertTrue(partnerPolicyMappingPage.isPolicyLinkingListDisplayed(),
				GlobalConstants.isRedirectedToListingForMissingRequest);
		assertTrue(viewPolicyRequestPage.isViewPolicyRequestPageAbsent(), GlobalConstants.isViewPageNotRendered);

		// A malformed identifier (TC_23)
		viewPolicyRequestPage.openWithSelectedRequest("{invalid-policy-request");
		assertTrue(partnerPolicyMappingPage.isPolicyLinkingListDisplayed(),
				GlobalConstants.isRedirectedToListingForInvalidRequest);
		assertTrue(viewPolicyRequestPage.isViewPolicyRequestPageAbsent(), GlobalConstants.isViewPageNotRendered);
	}

	@Test(priority = 7, description = "Verify a user without PARTNER_ADMIN is refused the Partner Policy Linking listing "
			+ "and the View page with the no-access error. (TC 17 and Map Credential Type TC 06)")
	public void nonAdminAccessDenied() {
		initPages();
		String listUrl = viewPolicyRequestPage.getPolicyRequestsListUrl();
		String viewUrl = viewPolicyRequestPage.getViewPolicyRequestUrl();

		loginAs(GlobalConstants.AUTH_PARTNER_ID, GlobalConstants.PARTNER_PASSWORD);
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isRedirectedToDashboard);

		// The listing - the only way into Step 2 / Step 3 for an OVP request (Map Credential Type TC_06)
		viewPolicyRequestPage.openUrl(listUrl);
		assertTrue(viewPolicyRequestPage.isNoAccessErrorDisplayed(), GlobalConstants.isNoAccessShownForNonAdmin);

		// The view page itself (TC_17)
		viewPolicyRequestPage.openUrl(viewUrl);
		assertTrue(viewPolicyRequestPage.isNoAccessErrorDisplayed(), GlobalConstants.isNoAccessShownForNonAdmin);
		assertTrue(viewPolicyRequestPage.isViewPolicyRequestPageAbsent(), GlobalConstants.isViewPageNotRendered);
	}

	@Test(priority = 8, description = "Verify an unauthenticated user opening the View page by URL is sent to the login "
			+ "page and shown no policy data. (TC 21)")
	public void unauthenticatedAccessRedirectsToLogin() {
		initPages();
		String viewUrl = viewPolicyRequestPage.getViewPolicyRequestUrl();

		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();

		viewPolicyRequestPage.openUrl(viewUrl);
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isRedirectedToLoginWhenUnauthenticated);
		assertTrue(viewPolicyRequestPage.isViewPolicyRequestPageAbsent(), GlobalConstants.isViewPageNotRendered);
	}
}
