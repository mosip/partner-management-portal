package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.kernel.util.KeycloakUserManager;
import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.MapBiometricExtractorProviderPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "OnlineVerificationPartnerMapBiometricExtractorTest" })
public class OnlineVerificationPartnerMapBiometricExtractorTest extends BaseClass {

	private static String partnerId;
	private static String policyName;

	@Test(priority = 1, description = "Open Map Biometric Extractor Provider for an Online Verification Partner policy request")
	public void openBiometricExtractorMappingForOnlineVerificationPartner() {
		String suffix = BaseClass.data;
		partnerId = "ovpmap" + suffix;

		uploadAuthTrustCertificates();
		publishAuthPolicy(suffix);
		MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
		createPartner(mispPartnerPage, partnerId, "ovpmap" + suffix + "@test.com");
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
		uploadPartnerCertificate(mispPartnerPage);

		DashboardPage dashboardPage = new PartnerCertificatePage(driver).clickOnHomeButton();
		dashboardPage.clickOnPartnerPolicyMappingTab();
		PartnerPolicyMappingPage policyMappingPage = new PartnerPolicyMappingPage(driver);
		policyMappingPage.clickOnRequestPolicyButton();
		policyMappingPage.selectPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		policyMappingPage.selectPartnerIdDropdown(partnerId);
		policyMappingPage.clickOnRequestPoliciesPolicyNameDropdown();
		policyMappingPage.clickOnRequestPoliciesPolicyNameOption1();
		policyMappingPage.enterRequestPolicyComment(GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_REQUEST_COMMENT);
		policyMappingPage.clickOnRequestPoliciesFormSubmitButton();

		MapBiometricExtractorProviderPage mapPage = new MapBiometricExtractorProviderPage(driver);
		assertEquals(mapPage.waitUntilPageTitle(GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_TITLE),
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_TITLE,
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_PAGE_OPENED);
		assertEquals(mapPage.getMandatoryMappingBanner(), GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_BANNER,
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_BANNER_DISPLAYED);
		assertEquals(mapPage.getPartnerId(), partnerId,
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_PARTNER_ID_DISPLAYED);
		assertEquals(mapPage.getPartnerType(), GlobalConstants.ONLINE_VERIFICATION_PARTNER_TYPE_CODE,
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_PARTNER_TYPE_DISPLAYED);
		policyName = mapPage.getPolicyName();
		assertTrue(mapPage.isSaveAndProceedDisabled(),
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_SAVE_DISABLED_WITHOUT_PROVIDER);
	}

	@Test(priority = 2, description = "Biometric modality lists Face, Iris, and Finger", dependsOnMethods = "openBiometricExtractorMappingForOnlineVerificationPartner")
	public void biometricModalityListsFaceIrisAndFinger() {
		MapBiometricExtractorProviderPage mapPage = openSavedPolicyRequestMapPage();
		mapPage.openFirstModalityDropdown();
		assertTrue(mapPage.isModalityOptionDisplayed(1, GlobalConstants.BIOMETRIC_MODALITY_FACE)
				&& mapPage.isModalityOptionDisplayed(1, GlobalConstants.BIOMETRIC_MODALITY_IRIS)
				&& mapPage.isModalityOptionDisplayed(1, GlobalConstants.BIOMETRIC_MODALITY_FINGER),
				GlobalConstants.BIOMETRIC_MODALITY_OPTIONS_DISPLAYED);
	}

	@Test(priority = 3, description = "Face lists an active biometric extractor provider configuration", dependsOnMethods = "biometricModalityListsFaceIrisAndFinger")
	public void faceModalityListsActiveProviderConfiguration() {
		MapBiometricExtractorProviderPage mapPage = openSavedPolicyRequestMapPage();
		mapPage.selectModality(1, GlobalConstants.BIOMETRIC_MODALITY_FACE);
		mapPage.openFirstProviderDropdown();
		assertTrue(mapPage.isFirstProviderOptionDisplayed(),
				GlobalConstants.FACE_PROVIDER_CONFIGURATION_DISPLAYED);
		mapPage.selectFirstProvider();
		assertEquals(mapPage.getProviderName(), GlobalConstants.MOSIP_BIO_EXTRACTOR_PROVIDER_NAME,
				GlobalConstants.FACE_PROVIDER_NAME_POPULATED);
		assertEquals(mapPage.getProviderVersion(), GlobalConstants.MOSIP_BIO_EXTRACTOR_PROVIDER_VERSION,
				GlobalConstants.FACE_PROVIDER_VERSION_POPULATED);
	}

	@Test(priority = 4, description = "Clear Form resets an unsaved modality", dependsOnMethods = "faceModalityListsActiveProviderConfiguration")
	public void clearFormResetsUnsavedModality() {
		MapBiometricExtractorProviderPage mapPage = openSavedPolicyRequestMapPage();
		mapPage.selectModality(1, GlobalConstants.BIOMETRIC_MODALITY_FACE);
		mapPage.clickClearForm();
		assertEquals(mapPage.getFirstModalityText(), GlobalConstants.SELECT_BIOMETRIC_MODALITY,
				GlobalConstants.CLEAR_FORM_RESETS_MODALITY);
	}

	@Test(priority = 5, description = "Iris shows no active biometric extractor provider configuration", dependsOnMethods = "clearFormResetsUnsavedModality")
	public void irisModalityHasNoProviderConfiguration() {
		MapBiometricExtractorProviderPage mapPage = openSavedPolicyRequestMapPage();
		mapPage.selectModality(1, GlobalConstants.BIOMETRIC_MODALITY_IRIS);
		mapPage.openFirstProviderDropdown();
		assertTrue(mapPage.isFirstProviderListEmpty(),
				GlobalConstants.IRIS_MODALITY_HAS_NO_PROVIDER_CONFIGURATION);
	}

	@Test(priority = 6, description = "A selected modality is not offered on the next row", dependsOnMethods = "irisModalityHasNoProviderConfiguration")
	public void selectedModalityIsNotOfferedAgain() {
		MapBiometricExtractorProviderPage mapPage = openSavedPolicyRequestMapPage();
		mapPage.clickClearForm();
		mapPage.selectModality(1, GlobalConstants.BIOMETRIC_MODALITY_FACE);
		mapPage.clickAddMore();
		mapPage.openModalityDropdown(2);
		assertFalse(mapPage.isModalityOptionDisplayed(2, GlobalConstants.BIOMETRIC_MODALITY_FACE),
				GlobalConstants.SELECTED_MODALITY_IS_NOT_REPEATED);
	}

	@Test(priority = 7, description = "Cancel returns to the policy request list", dependsOnMethods = "selectedModalityIsNotOfferedAgain")
	public void cancelReturnsToPolicyRequestList() {
		MapBiometricExtractorProviderPage mapPage = openSavedPolicyRequestMapPage();
		mapPage.clickClearForm();
		mapPage.clickCancel();
		assertEquals(mapPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE),
				GlobalConstants.PARTNER_POLICY_LINKING_TITLE,
				GlobalConstants.CANCEL_RETURNS_TO_POLICY_REQUEST_LIST);
		assertTrue(mapPage.getPolicyRequestRowText(policyName).contains(GlobalConstants.PENDING_FOR_APPROVAL),
				GlobalConstants.POLICY_REQUEST_REMAINS_PENDING_FOR_APPROVAL);
		mapPage.openPolicyRequestAction(policyName);
		assertTrue(mapPage.isMapBiometricExtractorActionDisplayed(),
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_ACTION_AVAILABLE);
	}

	@Test(priority = 8, description = "Save and Proceed opens credential type mapping", dependsOnMethods = "cancelReturnsToPolicyRequestList")
	public void saveBiometricExtractorMappingOpensCredentialType() {
		MapBiometricExtractorProviderPage mapPage = openPolicyRequestList();
		mapPage.openPolicyRequestAction(policyName);
		mapPage.openMapBiometricExtractorAction();
		assertEquals(mapPage.waitUntilPageTitle(GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_TITLE),
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_TITLE,
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_PAGE_OPENED);
		mapPage.selectModality(1, GlobalConstants.BIOMETRIC_MODALITY_FACE);
		mapPage.openFirstProviderDropdown();
		mapPage.selectFirstProvider();
		mapPage.clickSaveAndProceed();
		assertTrue(mapPage.isSaveConfirmationDisplayed(), GlobalConstants.SAVE_OPENS_MAP_CREDENTIAL_TYPE);
		mapPage.confirmSave();
		assertEquals(mapPage.waitUntilPageTitle(GlobalConstants.MAP_CREDENTIAL_TYPE_TITLE),
				GlobalConstants.MAP_CREDENTIAL_TYPE_TITLE, GlobalConstants.SAVE_OPENS_MAP_CREDENTIAL_TYPE);
		mapPage.clickTitleBack();
		assertEquals(mapPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE),
				GlobalConstants.PARTNER_POLICY_LINKING_TITLE, GlobalConstants.SAVED_MAPPING_DISABLES_MAP_ACTION);
		mapPage.openPolicyRequestAction(policyName);
		assertTrue(mapPage.waitForMapBiometricExtractorActionClass().contains("pointer-events-none"),
				GlobalConstants.SAVED_MAPPING_DISABLES_MAP_ACTION);
	}

	@Test(priority = 9, description = "An Authentication Partner policy request has no biometric extractor action", dependsOnMethods = "saveBiometricExtractorMappingOpensCredentialType")
	public void authenticationPartnerHasNoBiometricExtractorAction() {
		MapBiometricExtractorProviderPage mapPage = openPolicyRequestList();
		assertFalse(mapPage.isMapBiometricExtractorActionDisplayedForPartnerType(GlobalConstants.AUTHENTICATION_PARTNER),
				GlobalConstants.AUTHENTICATION_PARTNER_HAS_NO_MAP_BIOMETRIC_ACTION);
	}

	@Test(priority = 10, description = "A user without PARTNER_ADMIN cannot open policy requests", dependsOnMethods = "authenticationPartnerHasNoBiometricExtractorAction")
	public void userWithoutAdminRoleCannotOpenPolicyRequests() {
		KeycloakUserManager.createUsers(GlobalConstants.NON_ADMIN_USER_ID, GlobalConstants.PARTNER_PASSWORD,
				"roles.auth", null);
		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickOnProfileDropdown();
		LoginPage loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.login(GlobalConstants.NON_ADMIN_USER_ID, GlobalConstants.PARTNER_PASSWORD);
		if (dashboardPage.isTermsAndConditionsPopupDisplayedQuick()) {
			dashboardPage.clickOnCheckbox();
			dashboardPage.clickOnProceedButton();
		}
		new MapBiometricExtractorProviderPage(driver).openAdminPolicyRequestList();
		assertEquals(dashboardPage.getRuntimeErrorTitle(), GlobalConstants.NO_ACCESS_PAGE_TITLE,
				GlobalConstants.USER_WITHOUT_ADMIN_ROLE_CANNOT_OPEN_POLICY_REQUESTS);
	}

	private MapBiometricExtractorProviderPage openPolicyRequestList() {
		new DashboardPage(driver).clickOnPartnerPolicyMappingTab();
		return new MapBiometricExtractorProviderPage(driver);
	}

	private MapBiometricExtractorProviderPage openSavedPolicyRequestMapPage() {
		MapBiometricExtractorProviderPage mapPage = openPolicyRequestList();
		mapPage.openPolicyRequestAction(policyName);
		mapPage.openMapBiometricExtractorAction();
		mapPage.waitUntilPageTitle(GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_TITLE);
		return mapPage;
	}

	private MispPartnerPage navigateToCreatePartnerPage() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
		if (!mispPartnerPage.isCreatePartnerButtonPresent()) {
			dashboardPage.clickOnPartners();
		}
		mispPartnerPage.clickOnCreatePartnerButton();
		return mispPartnerPage;
	}

	private void createPartner(MispPartnerPage mispPartnerPage, String partnerId, String email) {
		mispPartnerPage.clickOnPartnerTypeDropdown();
		mispPartnerPage.clickOnPartnerTypeOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		mispPartnerPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		mispPartnerPage.selectNotificationLanguage(GlobalConstants.ONLINE_VERIFICATION_PARTNER_NOTIFICATION_LANGUAGE);
		mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
		mispPartnerPage.enterPartnerAddress(GlobalConstants.ONLINE_VERIFICATION_PARTNER_ADDRESS);
		mispPartnerPage.enterPartnerContactNumber(GlobalConstants.ONLINE_VERIFICATION_PARTNER_CONTACT_NUMBER);
		mispPartnerPage.enterEmailId(email);
		mispPartnerPage.enterUserName(partnerId);
	}

	private void publishAuthPolicy(String suffix) {
		DashboardPage dashboardPage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		AuthPolicyPage authPolicyPage = new AuthPolicyPage(driver);
		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName("ovppolicy" + suffix);
		authPolicyPage.enterpolicyDescription("ovppolicy" + suffix);
		authPolicyPage.uploadPolicyData();
		authPolicyPage.clickOnSaveAsDraftButton();
		assertTrue(authPolicyPage.isPolicySavedAsDraftMessageDisplayed(),
				GlobalConstants.isPolicySavedAsDraftMessageDisplayed);
		authPolicyPage.clickOnGoBackButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter("ovppolicy" + suffix);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		authPolicyPage.clickOnHomeButton();
	}

	private void uploadAuthTrustCertificates() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		dashboardPage.clickOnCertificateTrustStore();
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();
		partnerCertificatePage.clickOnIntermediateCACertTab();
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnHomeButton();
	}

	private void uploadPartnerCertificate(MispPartnerPage mispPartnerPage) {
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		mispPartnerPage.clickOnUploadPartnerCertificateButton();
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed(),
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
	}
}
