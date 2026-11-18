package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BioExtractorConfigurationPage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.MapBiometricExtractorProviderPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.pages.RegisterPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "OnlineVerificationPartnerMapBiometricExtractorTest" })
public class OnlineVerificationPartnerMapBiometricExtractorTest extends BaseClass {

	private static String partnerId;
	private static String policyName;
	private static String configurationName;
	private static String providerName;

	@Test(priority = 1, description = "Open Map Biometric Extractor Provider for an Online Verification Partner policy request")
	public void openBiometricExtractorMappingForOnlineVerificationPartner() {
		String suffix = BaseClass.data;
		partnerId = "ovpmap" + suffix;
		policyName = "ovppolicy" + suffix;
		configurationName = "facecfg" + suffix;
		providerName = "faceprov" + suffix;

		createFaceConfiguration(configurationName, providerName);
		OnlineVerificationPartnerCertificateSteps.uploadAuthTrustCertificates(driver);
		publishAuthPolicy(suffix);
		MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
		createPartner(mispPartnerPage, GlobalConstants.ONLINE_VERIFICATION_PARTNER, partnerId,
				"ovpmap" + suffix + "@test.com", GlobalConstants.ONLINE_VERIFICATION_PARTNER_ADDRESS,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_CONTACT_NUMBER,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_NOTIFICATION_LANGUAGE);
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
		OnlineVerificationPartnerCertificateSteps.uploadPartnerCertificate(driver, mispPartnerPage);

		DashboardPage dashboardPage = new PartnerCertificatePage(driver).clickOnHomeButton();
		dashboardPage.clickOnPartnerPolicyMappingTab();
		PartnerPolicyMappingPage policyMappingPage = new PartnerPolicyMappingPage(driver);
		policyMappingPage.clickOnRequestPolicyButton();
		policyMappingPage.selectPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		policyMappingPage.selectPartnerIdDropdown(partnerId);
		policyMappingPage.selectRequestPolicyByName(policyName);
		policyMappingPage.enterRequestPolicyComment(GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_REQUEST_COMMENT);
		policyMappingPage.clickOnRequestPoliciesFormSubmitButton();

		MapBiometricExtractorProviderPage mapPage = new MapBiometricExtractorProviderPage(driver);
		assertEquals(mapPage.waitUntilPageTitle(GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_TITLE),
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_TITLE,
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_PAGE_OPENED);
		assertTrue(mapPage.isMandatoryMappingBannerDisplayed(),
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_BANNER_DISPLAYED);
		assertEquals(mapPage.getPartnerId(), partnerId,
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_PARTNER_ID_DISPLAYED);
		assertEquals(mapPage.getPolicyName(), policyName,
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_POLICY_NAME_DISPLAYED);
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

	@Test(priority = 3, description = "Face lists an active biometric extractor provider configuration", dependsOnMethods = "openBiometricExtractorMappingForOnlineVerificationPartner")
	public void faceModalityListsActiveProviderConfiguration() {
		MapBiometricExtractorProviderPage mapPage = openSavedPolicyRequestMapPage();
		mapPage.selectModality(1, GlobalConstants.BIOMETRIC_MODALITY_FACE);
		mapPage.selectProviderConfiguration(configurationName);
		assertEquals(mapPage.waitUntilFirstProviderConfigurationSelected(), configurationName,
				GlobalConstants.FACE_PROVIDER_CONFIGURATION_DISPLAYED);
		assertEquals(mapPage.getProviderName(), providerName, GlobalConstants.FACE_PROVIDER_NAME_POPULATED);
		assertEquals(mapPage.getProviderVersion(), GlobalConstants.MOSIP_BIO_EXTRACTOR_PROVIDER_VERSION,
				GlobalConstants.FACE_PROVIDER_VERSION_POPULATED);
	}

	@Test(priority = 4, description = "Clear Form resets an unsaved modality", dependsOnMethods = "openBiometricExtractorMappingForOnlineVerificationPartner")
	public void clearFormResetsUnsavedModality() {
		MapBiometricExtractorProviderPage mapPage = openSavedPolicyRequestMapPage();
		mapPage.selectModality(1, GlobalConstants.BIOMETRIC_MODALITY_FACE);
		mapPage.clickClearForm();
		assertEquals(mapPage.getFirstModalityText(), GlobalConstants.SELECT_BIOMETRIC_MODALITY,
				GlobalConstants.CLEAR_FORM_RESETS_MODALITY);
	}

	@Test(priority = 5, description = "A selected modality is not offered on the next row", dependsOnMethods = "openBiometricExtractorMappingForOnlineVerificationPartner")
	public void selectedModalityIsNotOfferedAgain() {
		MapBiometricExtractorProviderPage mapPage = openSavedPolicyRequestMapPage();
		mapPage.clickClearForm();
		mapPage.selectModality(1, GlobalConstants.BIOMETRIC_MODALITY_FACE);
		mapPage.clickAddMore();
		mapPage.openModalityDropdown(2);
		assertTrue(!mapPage.isModalityOptionDisplayed(2, GlobalConstants.BIOMETRIC_MODALITY_FACE)
				&& mapPage.isModalityOptionDisplayed(2, GlobalConstants.BIOMETRIC_MODALITY_IRIS)
				&& mapPage.isModalityOptionDisplayed(2, GlobalConstants.BIOMETRIC_MODALITY_FINGER),
				GlobalConstants.SELECTED_MODALITY_IS_NOT_REPEATED);
	}

	@Test(priority = 6, description = "Cancel returns to the policy request list", dependsOnMethods = "openBiometricExtractorMappingForOnlineVerificationPartner")
	public void cancelReturnsToPolicyRequestList() {
		MapBiometricExtractorProviderPage mapPage = openSavedPolicyRequestMapPage();
		mapPage.clickClearForm();
		mapPage.clickCancel();
		assertEquals(mapPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE),
				GlobalConstants.PARTNER_POLICY_LINKING_TITLE,
				GlobalConstants.CANCEL_RETURNS_TO_POLICY_REQUEST_LIST);
		PartnerPolicyMappingPage listPage = new PartnerPolicyMappingPage(driver);
		String policyRequestRow = filteredPolicyRequestRow(listPage, partnerId);
		assertTrue(policyRequestRow.contains(policyName)
				&& policyRequestRow.contains(GlobalConstants.PENDING_FOR_APPROVAL),
				GlobalConstants.POLICY_REQUEST_REMAINS_PENDING_FOR_APPROVAL);
		listPage.openFirstPolicyRequestAction();
		assertTrue(listPage.isMapBiometricExtractorActionDisplayed(),
				GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_ACTION_AVAILABLE);
	}

	@Test(priority = 7, description = "Save and Proceed opens credential type mapping", dependsOnMethods = "openBiometricExtractorMappingForOnlineVerificationPartner")
	public void saveBiometricExtractorMappingOpensCredentialType() {
		MapBiometricExtractorProviderPage mapPage = openSavedPolicyRequestMapPage();
		mapPage.selectModality(1, GlobalConstants.BIOMETRIC_MODALITY_FACE);
		mapPage.selectProviderConfiguration(configurationName);
		mapPage.clickSaveAndProceed();
		assertTrue(mapPage.isSaveConfirmationDisplayed(), GlobalConstants.SAVE_CONFIRMATION_DISPLAYED);
		mapPage.confirmSave();
		assertEquals(mapPage.waitUntilPageTitle(GlobalConstants.MAP_CREDENTIAL_TYPE_TITLE),
				GlobalConstants.MAP_CREDENTIAL_TYPE_TITLE, GlobalConstants.SAVE_OPENS_MAP_CREDENTIAL_TYPE);
		mapPage.clickTitleBack();
		assertEquals(mapPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE),
				GlobalConstants.PARTNER_POLICY_LINKING_TITLE, GlobalConstants.TITLE_BACK_RETURNS_TO_POLICY_REQUEST_LIST);
		PartnerPolicyMappingPage listPage = new PartnerPolicyMappingPage(driver);
		filteredPolicyRequestRow(listPage, partnerId);
		listPage.openFirstPolicyRequestAction();
		assertTrue(listPage.isMapBiometricExtractorActionDisabled(),
				GlobalConstants.SAVED_MAPPING_DISABLES_MAP_ACTION);
	}

	@Test(priority = 8, description = "An Authentication Partner policy request has no biometric extractor action", dependsOnMethods = "openBiometricExtractorMappingForOnlineVerificationPartner")
	public void authenticationPartnerHasNoBiometricExtractorAction() {
		String authPartnerId = "authmap" + BaseClass.data;
		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickOnProfileDropdown();
		LoginPage loginPage = dashboardPage.clickOnLogoutButton();
		RegisterPage registerPage = loginPage.clickRegisterButton();
		registerPage.enterFirstName(authPartnerId);
		registerPage.enterLastName(authPartnerId);
		registerPage.enterOrganizationName(GlobalConstants.ORGANISATION_NAME);
		registerPage.selectAuthenticationPartner();
		registerPage.enterAddress(GlobalConstants.AUTHENTICATION_PARTNER_ADDRESS);
		registerPage.enterEmail(authPartnerId + "@test.com");
		registerPage.enterPhone(GlobalConstants.AUTHENTICATION_PARTNER_CONTACT_NUMBER);
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername(authPartnerId);
		registerPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		registerPage.enterPasswordConfirm(GlobalConstants.PARTNER_PASSWORD);
		dashboardPage = registerPage.clickSubmitButton();
		dashboardPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		dashboardPage.clickOnSubmitButton();
		if (dashboardPage.isTermsAndConditionsPopupDisplayed()) {
			dashboardPage.clickOnCheckbox();
			dashboardPage.clickOnProceedButton();
		}

		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		dashboardPage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnUploadButton();
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
		dashboardPage = partnerCertificatePage.clickOnHomeButton();

		PoliciesPage policiesPage = dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnRequestPolicyButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterAuthPolicyNameDropdown(policyName);
		policiesPage.enterComments(GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_REQUEST_COMMENT);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.login(GlobalConstants.PARTNER_ADMIN, GlobalConstants.PARTNER_PASSWORD);
		if (dashboardPage.isTermsAndConditionsPopupDisplayedQuick()) {
			dashboardPage.clickOnCheckbox();
			dashboardPage.clickOnProceedButton();
		}

		PartnerPolicyMappingPage listPage = openPolicyRequestList();
		listPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE);
		filteredPolicyRequestRow(listPage, authPartnerId);
		listPage.openFirstPolicyRequestAction();
		assertFalse(listPage.isMapBiometricExtractorActionDisplayed(),
				GlobalConstants.AUTHENTICATION_PARTNER_HAS_NO_MAP_BIOMETRIC_ACTION);
	}

	@Test(priority = 9, description = "A user without PARTNER_ADMIN stays on the main page")
	public void userWithoutAdminRoleStaysOnMainPage() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickOnProfileDropdown();
		LoginPage loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.login(GlobalConstants.NON_ADMIN_USER_ID, GlobalConstants.PARTNER_PASSWORD);
		if (dashboardPage.isTermsAndConditionsPopupDisplayedQuick()) {
			dashboardPage.clickOnCheckbox();
			dashboardPage.clickOnProceedButton();
		}
		assertTrue(dashboardPage.isWelcomeMessageDisplayed() && !dashboardPage.isPartnerAdminCardDisplayed()
				&& !dashboardPage.isPartnerAdminSideNavDisplayed(),
				GlobalConstants.USER_WITHOUT_ADMIN_ROLE_STAYS_ON_MAIN_PAGE);
	}

	private PartnerPolicyMappingPage openPolicyRequestList() {
		new DashboardPage(driver).clickOnPartnerPolicyMappingTab();
		return new PartnerPolicyMappingPage(driver);
	}

	private String filteredPolicyRequestRow(PartnerPolicyMappingPage listPage, String selectedPartnerId) {
		listPage.filterPolicyRequestsByPartnerId(selectedPartnerId);
		String policyRequestRow = listPage.waitUntilFirstPolicyRequestRowContains(selectedPartnerId);
		assertTrue(policyRequestRow.contains(selectedPartnerId),
				GlobalConstants.POLICY_REQUEST_BELONGS_TO_CREATED_PARTNER);
		return policyRequestRow;
	}

	private MapBiometricExtractorProviderPage openSavedPolicyRequestMapPage() {
		PartnerPolicyMappingPage listPage = openPolicyRequestList();
		filteredPolicyRequestRow(listPage, partnerId);
		listPage.openFirstPolicyRequestAction();
		listPage.openMapBiometricExtractorAction();
		MapBiometricExtractorProviderPage mapPage = new MapBiometricExtractorProviderPage(driver);
		mapPage.waitUntilPageTitle(GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_TITLE);
		return mapPage;
	}

	private void createFaceConfiguration(String configurationName, String providerName) {
		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickOnBiometricProviderConfiguration();
		BioExtractorConfigurationPage configurationPage = new BioExtractorConfigurationPage(driver);
		configurationPage.clickCreate();
		configurationPage.enterConfigurationName(configurationName);
		configurationPage.enterProviderName(providerName);
		configurationPage.enterProviderVersion(GlobalConstants.MOSIP_BIO_EXTRACTOR_PROVIDER_VERSION);
		configurationPage.selectFaceModality();
		configurationPage.selectRawDataFormat();
		configurationPage.selectAttributeName();
		configurationPage.clickSubmit();
		assertTrue(configurationPage.isConfirmationDisplayed(),
				GlobalConstants.BIOMETRIC_EXTRACTOR_CONFIGURATION_CREATED);
		configurationPage.clickConfirmationHome();
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

	private void createPartner(MispPartnerPage mispPartnerPage, String partnerType, String partnerId, String email,
			String address, String phone, String language) {
		mispPartnerPage.clickOnPartnerTypeDropdown();
		mispPartnerPage.clickOnPartnerTypeOption(partnerType);
		mispPartnerPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		mispPartnerPage.selectNotificationLanguage(language);
		mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
		mispPartnerPage.enterPartnerAddress(address);
		mispPartnerPage.enterPartnerContactNumber(phone);
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
}
