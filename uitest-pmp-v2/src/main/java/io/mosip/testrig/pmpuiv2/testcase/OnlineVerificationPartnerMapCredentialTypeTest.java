package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.MapBiometricExtractorPage;
import io.mosip.testrig.pmpuiv2.pages.MapCredentialTypePage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

/**
 * Online Verification Partner: Partner Admin maps a credential type (Step 3)
 * from Dashboard > Partner Policy Linking.
 *
 * Unlike a Credential Partner, an OVP partner never drives the mapping itself:
 * the Partner Admin raises the request on the partner's behalf and both
 * mapping steps are offered from the admin row action menu. The suite logs in
 * as the default Partner Admin, so no user switch is needed.
 *
 * One policy request is carried through the whole flow, so a single Step 1 +
 * Step 2 setup serves the list, validation, refresh and submit checks. The
 * View Policy Details suite picks the same request up afterwards.
 *
 * Covered here: TC 01 (map successfully), 02 (only configured types listed),
 * 04 (existing mapping cannot be updated), 05 (cannot proceed without a
 * selection) and 14 (refresh before save).
 * TC 06 (non-admin) and TC 07 (final status) are asserted in
 * {@link OnlineVerificationPartnerViewPolicyRequestTest}, where the non-admin
 * login and the approved request already exist.
 * Not automated - they need environment configuration or fault injection the
 * rig cannot drive: TC 03 (single configured type), 08 (session timeout),
 * 09 (backend failure), 10 (no types configured), 11 (100+ types),
 * 12 (special characters in a type), 13 (two admins at once), 15 (audit log).
 */
@Test(dependsOnGroups = { "OnlineVerificationPartnerCreation",
		"PartnerPolicyMappingTest" }, groups = { "OnlineVerificationPartnerMapCredentialTypeTest" })
public class OnlineVerificationPartnerMapCredentialTypeTest extends BaseClass {

	private BasePage basePage;
	private DashboardPage dashboardPage;
	private PartnerPolicyMappingPage partnerPolicyMappingPage;
	private MapBiometricExtractorPage mapBiometricExtractorPage;
	private MapCredentialTypePage mapCredentialTypePage;

	private void initPages() {
		basePage = new BasePage(driver);
		dashboardPage = new DashboardPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);
		mapBiometricExtractorPage = new MapBiometricExtractorPage(driver);
		mapCredentialTypePage = new MapCredentialTypePage(driver);
	}

	private void openPolicyLinkingFilteredBy(String policyName) {
		dashboardPage.clickOnPartnerPolicyMappingTab();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingTitleDisplayed);
		filterPolicyLinkingBy(policyName);
	}

	/** Acting on a request leaves the admin on the listing, so the filter is reset and re-applied in place. */
	private void filterPolicyLinkingBy(String policyName) {
		partnerPolicyMappingPage.clickOnFilterResetButton();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(policyName);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		basePage.scrollToStartPage();
	}

	/** Opens Step 3 for the OVP request against the given policy. */
	private void openMapCredentialType(String policyName) {
		openPolicyLinkingFilteredBy(policyName);
		partnerPolicyMappingPage.clickOnActionButtonByPartnerAndPolicy(GlobalConstants.OVP_PARTNER_USER, policyName);
		partnerPolicyMappingPage.clickOnOvpMapCredentialTypeOption(GlobalConstants.OVP_PARTNER_USER, policyName);
		assertTrue(
				mapCredentialTypePage
						.isMapCredentialTypePageTitleDisplayed(MapCredentialTypePage.MAP_CREDENTIAL_TYPE_TITLE),
				GlobalConstants.isMapCredentialTypePageTitleDisplayed);
	}

	@Test(priority = 1, description = "Raise an Online Verification Partner policy request as Partner Admin, verify Map "
			+ "Credential Type stays gated until the biometric extractor is mapped, then map it and land on the Step 3 "
			+ "form with the OVP banner and the request's details auto-populated. (Setup for TC 01)")
	public void raiseRequestAndReachMapCredentialType() {
		initPages();

		// Step 1 - raised by the admin on the partner's behalf; OVP requests go straight on to Step 2
		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnRequestPolicyButton();
		partnerPolicyMappingPage.selectPartnerType(MapCredentialTypePage.ONLINE_VERIFICATION_PARTNER_TYPE);
		partnerPolicyMappingPage.selectPartnerIdDropdown(GlobalConstants.OVP_PARTNER_USER);
		partnerPolicyMappingPage.selectPolicyName(GlobalConstants.DATAPOLICY_PARTLINK);
		partnerPolicyMappingPage.enterRequestPolicyComment(GlobalConstants.OVP_POLICY_REQUEST_COMMENT);
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesFormSubmitButtonEnabled(),
				GlobalConstants.isRequestPoliciesFormSubmitButtonEnabled);
		partnerPolicyMappingPage.clickOnRequestPoliciesFormSubmitButton();
		assertTrue(mapBiometricExtractorPage.isMapBiometricExtractorPageDisplayed(),
				GlobalConstants.isOvpRequestRoutedToBiometricMapping);

		// Leave Step 2 undone so the Step 3 gating is assertable from the listing
		mapBiometricExtractorPage.clickOnCancelButton();
		filterPolicyLinkingBy(GlobalConstants.DATAPOLICY_PARTLINK);
		partnerPolicyMappingPage.clickOnActionButtonByPartnerAndPolicy(GlobalConstants.OVP_PARTNER_USER,
				GlobalConstants.DATAPOLICY_PARTLINK);
		assertTrue(
				partnerPolicyMappingPage.isOvpMapBiometricExtractorOptionDisplayed(GlobalConstants.OVP_PARTNER_USER,
						GlobalConstants.DATAPOLICY_PARTLINK),
				GlobalConstants.isOvpMappingOptionsOfferedToAdmin);
		assertTrue(
				partnerPolicyMappingPage.isOvpMapCredentialTypeOptionDisplayed(GlobalConstants.OVP_PARTNER_USER,
						GlobalConstants.DATAPOLICY_PARTLINK),
				GlobalConstants.isOvpMappingOptionsOfferedToAdmin);
		assertTrue(
				partnerPolicyMappingPage.isOvpMapCredentialTypeOptionDisabled(GlobalConstants.OVP_PARTNER_USER,
						GlobalConstants.DATAPOLICY_PARTLINK),
				GlobalConstants.isMapCredentialTypeOptionDisabledBeforePrerequisites);

		// Step 2 - saving it drops the admin straight onto Step 3
		partnerPolicyMappingPage.clickOnOvpMapBiometricExtractorOption(GlobalConstants.OVP_PARTNER_USER,
				GlobalConstants.DATAPOLICY_PARTLINK);
		assertTrue(mapBiometricExtractorPage.isMapBiometricExtractorPageDisplayed(),
				GlobalConstants.isOvpBiometricMappingEnabledAfterRequest);
		mapBiometricExtractorPage.mapFirstAvailableExtractorAndSave();

		assertTrue(
				mapCredentialTypePage
						.isMapCredentialTypePageTitleDisplayed(MapCredentialTypePage.MAP_CREDENTIAL_TYPE_TITLE),
				GlobalConstants.isMapCredentialTypePageTitleDisplayed);
		assertTrue(mapCredentialTypePage.isMandatoryMappingBannerDisplayed(),
				GlobalConstants.isOvpMandatoryBannerDisplayed);
		assertTrue(
				mapCredentialTypePage.getMandatoryMappingBannerText()
						.contains(MapCredentialTypePage.OVP_MANDATORY_MAPPING_BANNER),
				GlobalConstants.isOvpMandatoryBannerDisplayed);
		assertEquals(mapCredentialTypePage.getReadOnlyFieldValue(MapCredentialTypePage.PARTNER_ID_FIELD),
				GlobalConstants.OVP_PARTNER_USER, GlobalConstants.isPartnerIdAutoPopulated);
		assertEquals(mapCredentialTypePage.getReadOnlyFieldValue(MapCredentialTypePage.PARTNER_TYPE_FIELD),
				MapCredentialTypePage.ONLINE_VERIFICATION_PARTNER_TYPE, GlobalConstants.isPartnerTypeAutoPopulatedAsOvp);
		assertEquals(mapCredentialTypePage.getReadOnlyFieldValue(MapCredentialTypePage.POLICY_GROUP_FIELD),
				GlobalConstants.DEFAULT_POLICYGROUP, GlobalConstants.isPolicyGroupAutoPopulated);
		assertEquals(mapCredentialTypePage.getReadOnlyFieldValue(MapCredentialTypePage.POLICY_NAME_FIELD),
				GlobalConstants.DATAPOLICY_PARTLINK, GlobalConstants.isPolicyNameAutoPopulated);
		assertTrue(mapCredentialTypePage.isCredentialTypeDropdownDisplayed(),
				GlobalConstants.isCredentialTypeDropdownDisplayed);
	}

	@Test(priority = 2, dependsOnMethods = "raiseRequestAndReachMapCredentialType",
			description = "Verify the Credential Type dropdown lists only distinct configured values, and that Submit is "
					+ "held back until a type is selected - including after Clear Form. (TC 02,05)")
	public void credentialTypeListAndMandatorySelection() {
		initPages();
		openMapCredentialType(GlobalConstants.DATAPOLICY_PARTLINK);

		// Only configured values are listed - no blanks, no duplicates (TC_02)
		List<String> options = mapCredentialTypePage.getCredentialTypeOptions();
		assertFalse(options.isEmpty(), GlobalConstants.isCredentialTypeOptionsDisplayed);
		assertFalse(options.stream().anyMatch(String::isEmpty), GlobalConstants.isCredentialTypeListWellFormed);
		assertEquals(new HashSet<>(options).size(), options.size(), GlobalConstants.isCredentialTypeListWellFormed);

		// No selection, no submit (TC_05)
		String placeholder = mapCredentialTypePage.getSelectedCredentialType();
		assertTrue(mapCredentialTypePage.isSubmitButtonDisabled(),
				GlobalConstants.isSubmitDisabledWithoutCredentialType);

		mapCredentialTypePage.selectCredentialType(options.get(0));
		assertTrue(mapCredentialTypePage.isSubmitButtonEnabled(), GlobalConstants.isSubmitEnabledWithCredentialType);

		// Clearing the selection puts the block back (TC_05)
		mapCredentialTypePage.clickOnClearFormButton();
		assertEquals(mapCredentialTypePage.getSelectedCredentialType(), placeholder,
				GlobalConstants.isCredentialTypeSelectionClearedAfterClearForm);
		assertTrue(mapCredentialTypePage.isSubmitButtonDisabled(),
				GlobalConstants.isCredentialTypeSelectionClearedAfterClearForm);
	}

	@Test(priority = 3, dependsOnMethods = "credentialTypeListAndMandatorySelection",
			description = "Verify a Credential Type selected but not saved is discarded by a browser refresh and is not "
					+ "persisted against the request. (TC 14)")
	public void unsavedSelectionDiscardedOnRefresh() {
		initPages();
		openMapCredentialType(GlobalConstants.DATAPOLICY_PARTLINK);

		String placeholder = mapCredentialTypePage.getSelectedCredentialType();
		List<String> options = mapCredentialTypePage.getCredentialTypeOptions();
		mapCredentialTypePage.selectCredentialType(options.get(0));

		// The route state survives the reload, so the form comes back - just without the selection
		basePage.refreshThePage();
		assertTrue(
				mapCredentialTypePage
						.isMapCredentialTypePageTitleDisplayed(MapCredentialTypePage.MAP_CREDENTIAL_TYPE_TITLE),
				GlobalConstants.isMapCredentialTypePageTitleDisplayed);
		assertEquals(mapCredentialTypePage.getSelectedCredentialType(), placeholder,
				GlobalConstants.isUnsavedCredentialTypeDiscardedOnRefresh);

		// Nothing reached the backend - Step 3 is still open for this request
		mapCredentialTypePage.clickOnCancelButton();
		filterPolicyLinkingBy(GlobalConstants.DATAPOLICY_PARTLINK);
		partnerPolicyMappingPage.clickOnActionButtonByPartnerAndPolicy(GlobalConstants.OVP_PARTNER_USER,
				GlobalConstants.DATAPOLICY_PARTLINK);
		assertFalse(
				partnerPolicyMappingPage.isOvpMapCredentialTypeOptionDisabled(GlobalConstants.OVP_PARTNER_USER,
						GlobalConstants.DATAPOLICY_PARTLINK),
				GlobalConstants.isUnsavedCredentialTypeNotPersisted);
	}

	@Test(priority = 4, dependsOnMethods = "unsavedSelectionDiscardedOnRefresh",
			description = "Verify a valid Credential Type is mapped successfully with an acknowledgement, the request stays "
					+ "Pending For Approval, and neither mapping can be re-opened to update it. (TC 01,04)")
	public void mapCredentialTypeAndBlockRemap() {
		initPages();
		openMapCredentialType(GlobalConstants.DATAPOLICY_PARTLINK);

		// Map successfully (TC_01)
		List<String> options = mapCredentialTypePage.getCredentialTypeOptions();
		mapCredentialTypePage.selectCredentialType(options.get(0));
		mapCredentialTypePage.clickOnSubmitButton();
		assertTrue(mapCredentialTypePage.isAcknowledgementScreenDisplayed(),
				GlobalConstants.isMapCredentialTypeAcknowledgementDisplayed);
		assertEquals(mapCredentialTypePage.getAcknowledgementHeader(),
				MapCredentialTypePage.MAP_CREDENTIAL_TYPE_SUCCESS_HEADER,
				GlobalConstants.isMapCredentialTypeAcknowledgementHeaderCorrect);

		// Go Back on the admin path returns to Partner Policy Linking
		mapCredentialTypePage.clickOnGoBackButton();
		assertTrue(partnerPolicyMappingPage.isPolicyLinkingListDisplayed(),
				GlobalConstants.isBackNavigatesToPolicyLinkingList);

		filterPolicyLinkingBy(GlobalConstants.DATAPOLICY_PARTLINK);
		assertTrue(
				partnerPolicyMappingPage.isPolicyRowStatusDisplayed(GlobalConstants.OVP_PARTNER_USER,
						GlobalConstants.DATAPOLICY_PARTLINK, GlobalConstants.PENDING_FOR_APPROVAL),
				GlobalConstants.isPendingPolicyRequestVisibleToAdmin);

		// The saved mapping cannot be re-opened to change it (TC_04)
		partnerPolicyMappingPage.clickOnActionButtonByPartnerAndPolicy(GlobalConstants.OVP_PARTNER_USER,
				GlobalConstants.DATAPOLICY_PARTLINK);
		assertTrue(
				partnerPolicyMappingPage.isOvpMapCredentialTypeOptionDisabled(GlobalConstants.OVP_PARTNER_USER,
						GlobalConstants.DATAPOLICY_PARTLINK),
				GlobalConstants.isCredentialTypeRemapBlocked);
		assertTrue(
				partnerPolicyMappingPage.isOvpMapBiometricExtractorOptionDisabled(GlobalConstants.OVP_PARTNER_USER,
						GlobalConstants.DATAPOLICY_PARTLINK),
				GlobalConstants.isBiometricRemapBlocked);
	}
}
