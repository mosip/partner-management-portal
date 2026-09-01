package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.MapBiometricExtractorPage;
import io.mosip.testrig.pmpuiv2.pages.MapCredentialTypePage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

/**
 * MOSIP-44660 - Credential Partner: Map Credential Type (Step 3 of the policy
 * request flow).
 *
 * The scenarios are chained on purpose: one policy request is carried through
 * the whole flow so that a single Step 1 + Step 2 setup serves the layout,
 * dropdown, clear/cancel and submit checks. A second policy request is used at
 * the end for the duplicate credential type rule.
 *
 * Covers TC_44660_01 to _13 and _16 to _30.
 */
@Test(dependsOnGroups = { "CredentialPartnerCreation",
		"PartnerPolicyMappingTest" }, groups = { "CredentialPartnerMapCredentialTypeTest" })
public class CredentialPartnerMapCredentialTypeTest extends BaseClass {

	private BasePage basePage;
	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private PoliciesPage policiesPage;
	private MapBiometricExtractorPage mapBiometricExtractorPage;
	private MapCredentialTypePage mapCredentialTypePage;

	private void initPages() {
		basePage = new BasePage(driver);
		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		policiesPage = new PoliciesPage(driver);
		mapBiometricExtractorPage = new MapBiometricExtractorPage(driver);
		mapCredentialTypePage = new MapCredentialTypePage(driver);
	}

	/** Every test method starts on a fresh browser, so re-enter as the credential partner. */
	private void loginAsCredentialPartner() {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_USER);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();
	}

	/** Step 1 - raise a policy request for the given policy. */
	private void raisePolicyRequest(String policyName) {
		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnRequestPolicyButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterAuthPolicyNameDropdown(policyName);
		policiesPage.enterComments(policyName);
		policiesPage.clickSubmitButton();
		// Credential Partners skip the confirmation screen: RequestPolicy.js routes straight to
		// Step 2, so landing on Map Biometric Extractor Provider is the success signal here.
		assertTrue(mapBiometricExtractorPage.isMapBiometricExtractorPageDisplayed(),
				GlobalConstants.isMapBiometricExtractorPageDisplayedAfterRequest);
		// Cancel back to the listing so Step 2 stays incomplete and the Step 3 gating is assertable.
		mapBiometricExtractorPage.clickOnCancelButton();
	}

	/** Opens the newest policy request row action menu on the policies list. */
	private void openActionMenuOfLatestRequest() {
		basePage.scrollToStartPage();
		policiesPage.clickOnActionMenuByRow(1);
	}

	/** Step 2 - map the first extractor config; caller must already have the action menu open. */
	private void completeBiometricExtractorStep() {
		policiesPage.clickOnMapBiometricExtractorOption();
		assertTrue(mapBiometricExtractorPage.isMapBiometricExtractorPageDisplayed(),
				GlobalConstants.isMapCredentialTypeOptionEnabledAfterPrerequisites);
		mapBiometricExtractorPage.mapFirstAvailableExtractorAndSave();
	}

	private void openMapCredentialType() {
		dashboardPage.clickOnPoliciesTitle();
		openActionMenuOfLatestRequest();
		policiesPage.clickOnMapCredentialTypeOption();
	}

	@Test(priority = 1, description = "Verify Step 3 is gated behind Step 1 and Step 2, and that the Map Credential Type "
			+ "form shows the mandatory banner, page title, every field and action button, with the auto populated "
			+ "fields read only. (TC 01,02,03,04,05,06,07,30)")
	public void prerequisiteGatingAndFormLayout() {
		initPages();
		loginAsCredentialPartner();

		// Step 1 only - Step 3 must still be closed off (TC_01)
		raisePolicyRequest(GlobalConstants.DATAPOLICY_PARTLINK);
		openActionMenuOfLatestRequest();
		assertTrue(policiesPage.isMapCredentialTypeOptionDisplayed(),
				GlobalConstants.isMapCredentialTypeOptionAvailableAfterCancel);
		assertTrue(policiesPage.isMapCredentialTypeOptionDisabled(),
				GlobalConstants.isMapCredentialTypeOptionDisabledBeforePrerequisites);

		// Step 2 completes and drops the user straight onto Step 3 (TC_01)
		completeBiometricExtractorStep();

		// Page title and mandatory banner (TC_03, TC_30)
		assertTrue(mapCredentialTypePage.isMapCredentialTypePageTitleDisplayed(GlobalConstants.MAP_CREDENTIAL_TYPE_TITLE),
				GlobalConstants.isMapCredentialTypePageTitleDisplayed);
		assertTrue(mapCredentialTypePage.isMandatoryMappingBannerDisplayed(),
				GlobalConstants.isMapCredentialTypeMandatoryBannerDisplayed);
		assertTrue(
				mapCredentialTypePage.getMandatoryMappingBannerText()
						.contains(GlobalConstants.MAP_CREDENTIAL_TYPE_MANDATORY_BANNER),
				GlobalConstants.isMapCredentialTypeMandatoryBannerDisplayed);

		// All fields and action buttons are present (TC_02, TC_07)
		assertTrue(mapCredentialTypePage.isReadOnlyFieldDisplayed(MapCredentialTypePage.PARTNER_ID_FIELD),
				GlobalConstants.isPartnerIdFieldDisplayedInMapCredentialType);
		assertTrue(mapCredentialTypePage.isReadOnlyFieldDisplayed(MapCredentialTypePage.PARTNER_TYPE_FIELD),
				GlobalConstants.isPartnerTypeFieldDisplayedInMapCredentialType);
		assertTrue(mapCredentialTypePage.isReadOnlyFieldDisplayed(MapCredentialTypePage.POLICY_GROUP_FIELD),
				GlobalConstants.isPolicyGroupFieldDisplayedInMapCredentialType);
		assertTrue(mapCredentialTypePage.isReadOnlyFieldDisplayed(MapCredentialTypePage.POLICY_NAME_FIELD),
				GlobalConstants.isPolicyNameFieldDisplayedInMapCredentialType);
		assertTrue(mapCredentialTypePage.isReadOnlyFieldDisplayed(MapCredentialTypePage.BIOMETRIC_MODALITY_FIELD),
				GlobalConstants.isBiometricModalityFieldDisplayedInMapCredentialType);
		assertTrue(
				mapCredentialTypePage.isReadOnlyFieldDisplayed(MapCredentialTypePage.BIOMETRIC_PROVIDER_CONFIG_FIELD),
				GlobalConstants.isBiometricProviderConfigFieldDisplayedInMapCredentialType);
		assertTrue(mapCredentialTypePage.isCredentialTypeDropdownDisplayed(),
				GlobalConstants.isCredentialTypeDropdownDisplayed);
		assertTrue(mapCredentialTypePage.isClearFormButtonDisplayed(),
				GlobalConstants.isClearFormButtonDisplayedInMapCredentialType);
		assertTrue(mapCredentialTypePage.isCancelButtonDisplayed(),
				GlobalConstants.isCancelButtonDisplayedInMapCredentialType);
		assertTrue(mapCredentialTypePage.isSubmitButtonDisplayed(),
				GlobalConstants.isSubmitButtonDisplayedInMapCredentialType);

		// Auto populated from the selected policy request (TC_04)
		assertEquals(mapCredentialTypePage.getReadOnlyFieldValue(MapCredentialTypePage.PARTNER_TYPE_FIELD),
				GlobalConstants.CREDENTIAL_PARTNER, GlobalConstants.isPartnerTypeAutoPopulated);
		assertNotEquals(mapCredentialTypePage.getReadOnlyFieldValue(MapCredentialTypePage.PARTNER_ID_FIELD), "-",
				GlobalConstants.isPartnerIdAutoPopulated);
		assertEquals(mapCredentialTypePage.getReadOnlyFieldValue(MapCredentialTypePage.POLICY_GROUP_FIELD),
				GlobalConstants.DEFAULT_POLICYGROUP, GlobalConstants.isPolicyGroupAutoPopulated);
		assertEquals(mapCredentialTypePage.getReadOnlyFieldValue(MapCredentialTypePage.POLICY_NAME_FIELD),
				GlobalConstants.DATAPOLICY_PARTLINK, GlobalConstants.isPolicyNameAutoPopulated);

		// Read only and non editable (TC_05, TC_06)
		assertTrue(mapCredentialTypePage.isReadOnlyFieldNotEditable(MapCredentialTypePage.PARTNER_ID_FIELD),
				GlobalConstants.isPartnerIdNotEditable);
		assertTrue(mapCredentialTypePage.isReadOnlyFieldNotEditable(MapCredentialTypePage.PARTNER_TYPE_FIELD),
				GlobalConstants.isPartnerTypeNotEditable);
		assertTrue(mapCredentialTypePage.isReadOnlyFieldNotEditable(MapCredentialTypePage.POLICY_GROUP_FIELD),
				GlobalConstants.isPolicyGroupNotEditable);
		assertTrue(mapCredentialTypePage.isReadOnlyFieldNotEditable(MapCredentialTypePage.POLICY_NAME_FIELD),
				GlobalConstants.isPolicyNameNotEditable);
	}

	@Test(priority = 2, dependsOnMethods = "prerequisiteGatingAndFormLayout",
			description = "Verify the Credential Type dropdown lists the configured values, allows a selection, and holds "
					+ "only one credential type per policy request. (TC 08,09,10)")
	public void credentialTypeDropdownBehaviour() {
		initPages();
		loginAsCredentialPartner();
		openMapCredentialType();

		// Configured credential types are listed (TC_08)
		List<String> options = mapCredentialTypePage.getCredentialTypeOptions();
		assertFalse(options.isEmpty(), GlobalConstants.isCredentialTypeOptionsDisplayed);

		// A value can be selected (TC_09)
		String firstOption = options.get(0);
		mapCredentialTypePage.selectCredentialType(firstOption);
		assertEquals(mapCredentialTypePage.getSelectedCredentialType(), firstOption,
				GlobalConstants.isCredentialTypeSelectable);

		// Selecting another replaces the first, so only one is ever held (TC_10)
		if (options.size() > 1) {
			String secondOption = options.get(1);
			mapCredentialTypePage.selectCredentialType(secondOption);
			assertEquals(mapCredentialTypePage.getSelectedCredentialType(), secondOption,
					GlobalConstants.isOnlyOneCredentialTypeSelected);
		}
	}

	@Test(priority = 3, dependsOnMethods = "credentialTypeDropdownBehaviour",
			description = "Verify Clear Form resets the Credential Type, and Cancel returns to the policy list leaving the "
					+ "request Pending For Approval with the Map Credential Type action still offered. "
					+ "(TC 12,13,16,17,18,19)")
	public void clearFormAndCancelBehaviour() {
		initPages();
		loginAsCredentialPartner();
		openMapCredentialType();

		String placeholder = mapCredentialTypePage.getSelectedCredentialType();
		List<String> options = mapCredentialTypePage.getCredentialTypeOptions();
		mapCredentialTypePage.selectCredentialType(options.get(0));

		// Clear Form is clickable and resets the selection (TC_12, TC_13)
		assertTrue(mapCredentialTypePage.isClearFormButtonEnabled(),
				GlobalConstants.isClearFormButtonDisplayedInMapCredentialType);
		mapCredentialTypePage.clickOnClearFormButton();
		assertEquals(mapCredentialTypePage.getSelectedCredentialType(), placeholder,
				GlobalConstants.isCredentialTypeClearedAfterClearForm);

		// Cancel is clickable and returns to the policy list (TC_16, TC_17)
		assertTrue(mapCredentialTypePage.isCancelButtonEnabled(),
				GlobalConstants.isCancelButtonDisplayedInMapCredentialType);
		mapCredentialTypePage.clickOnCancelButton();
		assertTrue(policiesPage.isListOfPolicyRequestedDisplayed(),
				GlobalConstants.isListOfPolicyRequestedTextDisplayed);

		// The request is untouched - still pending, still offering the action (TC_18, TC_19)
		basePage.scrollToStartPage();
		assertTrue(
				policiesPage.isPolicyRowStatusDisplayed(GlobalConstants.DATAPOLICY_PARTLINK,
						GlobalConstants.PENDING_FOR_APPROVAL),
				GlobalConstants.isPolicyStatusPendingAfterCancel);
		openActionMenuOfLatestRequest();
		assertTrue(policiesPage.isMapCredentialTypeOptionDisplayed(),
				GlobalConstants.isMapCredentialTypeOptionAvailableAfterCancel);
		assertFalse(policiesPage.isMapCredentialTypeOptionDisabled(),
				GlobalConstants.isMapCredentialTypeOptionEnabledAfterPrerequisites);
	}

	@Test(priority = 4, dependsOnMethods = "clearFormAndCancelBehaviour",
			description = "Verify Submit stays disabled until a Credential Type is chosen, then submits the policy request "
					+ "and shows the acknowledgement screen with the correct header, description and navigation buttons. "
					+ "(TC 20,21,22,23,24,25,26,28,29)")
	public void submitValidationAndAcknowledgement() {
		initPages();
		loginAsCredentialPartner();
		openMapCredentialType();

		// Submit is held disabled until a credential type is picked (TC_21)
		assertTrue(mapCredentialTypePage.isSubmitButtonDisabled(),
				GlobalConstants.isSubmitDisabledWithoutCredentialType);

		List<String> options = mapCredentialTypePage.getCredentialTypeOptions();
		mapCredentialTypePage.selectCredentialType(options.get(0));

		// Submit becomes clickable (TC_20)
		assertTrue(mapCredentialTypePage.isSubmitButtonEnabled(), GlobalConstants.isSubmitEnabledWithCredentialType);
		mapCredentialTypePage.clickOnSubmitButton();

		// Acknowledgement screen (TC_22, TC_23, TC_24, TC_25)
		assertTrue(mapCredentialTypePage.isAcknowledgementScreenDisplayed(),
				GlobalConstants.isMapCredentialTypeAcknowledgementDisplayed);
		assertEquals(mapCredentialTypePage.getAcknowledgementHeader(),
				GlobalConstants.MAP_CREDENTIAL_TYPE_SUCCESS_HEADER,
				GlobalConstants.isMapCredentialTypeAcknowledgementHeaderCorrect);
		assertTrue(
				mapCredentialTypePage.getAcknowledgementDescription()
						.contains(GlobalConstants.MAP_CREDENTIAL_TYPE_SUCCESS_DESCRIPTION),
				GlobalConstants.isMapCredentialTypeAcknowledgementDescriptionCorrect);

		// Both navigation buttons are offered (TC_26, TC_28)
		assertTrue(mapCredentialTypePage.isGoBackButtonDisplayed(),
				GlobalConstants.isAcknowledgementGoBackButtonDisplayed);
		assertTrue(mapCredentialTypePage.isGoBackButtonEnabled(),
				GlobalConstants.isAcknowledgementGoBackButtonDisplayed);
		assertTrue(mapCredentialTypePage.isHomeButtonDisplayed(), GlobalConstants.isAcknowledgementHomeButtonDisplayed);
		assertTrue(mapCredentialTypePage.isHomeButtonEnabled(), GlobalConstants.isAcknowledgementHomeButtonDisplayed);

		// Home returns to the dashboard (TC_29)
		mapCredentialTypePage.clickOnHomeButton();
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isRedirectedToDashboard);
	}

	@Test(priority = 5, dependsOnMethods = "submitValidationAndAcknowledgement",
			description = "Verify the same credential type cannot be mapped again for the same partner on another policy, "
					+ "and that Go Back on the acknowledgement screen returns to the policy listing. (TC 11,27)")
	public void duplicateCredentialTypeAndGoBackNavigation() {
		initPages();
		loginAsCredentialPartner();

		// A second policy request, taken through Step 2 onto Step 3
		raisePolicyRequest(GlobalConstants.DATAPOLICY_PARTLINK2);
		openActionMenuOfLatestRequest();
		completeBiometricExtractorStep();

		List<String> options = mapCredentialTypePage.getCredentialTypeOptions();
		String alreadyMapped = options.get(0);

		// Re-using the credential type mapped in the previous scenario is rejected (TC_11)
		mapCredentialTypePage.selectCredentialType(alreadyMapped);
		mapCredentialTypePage.clickOnSubmitButton();
		assertTrue(mapCredentialTypePage.isErrorMessageDisplayed(), GlobalConstants.isDuplicateCredentialTypeRejected);
		assertTrue(mapCredentialTypePage.getErrorMessage().contains(GlobalConstants.MAP_CREDENTIAL_TYPE_DUPLICATE_MSG),
				GlobalConstants.isDuplicateCredentialTypeRejected);

		// A different credential type goes through, then Go Back lands on the listing (TC_27)
		if (options.size() > 1) {
			mapCredentialTypePage.selectCredentialType(options.get(1));
			mapCredentialTypePage.clickOnSubmitButton();
			assertTrue(mapCredentialTypePage.isAcknowledgementScreenDisplayed(),
					GlobalConstants.isMapCredentialTypeAcknowledgementDisplayed);
			mapCredentialTypePage.clickOnGoBackButton();
			assertTrue(policiesPage.isListOfPolicyRequestedDisplayed(), GlobalConstants.isRedirectedToPolicyListing);
		}
	}
}
