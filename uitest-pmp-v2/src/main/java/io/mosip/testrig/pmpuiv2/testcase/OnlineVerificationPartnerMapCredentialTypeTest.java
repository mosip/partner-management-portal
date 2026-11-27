package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BioExtractorConfigurationPage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.MapBiometricExtractorProviderPage;
import io.mosip.testrig.pmpuiv2.pages.MapCredentialTypePage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "OnlineVerificationPartnerMapCredentialTypeTest" })
public class OnlineVerificationPartnerMapCredentialTypeTest extends BaseClass {

	private static String dataSuffix;
	private static String partnerId;
	private static String policyName;
	private static String secondPolicyName;
	private static String configurationName;

	@Test(priority = 1, description = "Open Map Credential Type for an Online Verification Partner policy request")
	public void openMapCredentialTypeForOnlineVerificationPartner() {
		dataSuffix = BaseClass.data;
		partnerId = "ovpcred" + dataSuffix;
		configurationName = "facecfg" + dataSuffix;

		createFaceConfiguration(configurationName, "faceprov" + dataSuffix);
		OnlineVerificationPartnerCertificateSteps.uploadAuthTrustCertificates(driver);
		publishAuthPolicy("ovpcredpol" + dataSuffix);
		MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
		createPartner(mispPartnerPage, partnerId, "ovpcred" + dataSuffix + "@test.com");
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
		OnlineVerificationPartnerCertificateSteps.uploadPartnerCertificate(driver, mispPartnerPage);
		new PartnerCertificatePage(driver).clickOnHomeButton();

		requestPolicy(partnerId, "ovpcredpol" + dataSuffix);
		saveFaceMapping();
		MapCredentialTypePage mapPage = new MapCredentialTypePage(driver);
		assertEquals(mapPage.waitUntilPageTitle(GlobalConstants.MAP_CREDENTIAL_TYPE_TITLE),
				GlobalConstants.MAP_CREDENTIAL_TYPE_TITLE, GlobalConstants.MAP_CREDENTIAL_TYPE_PAGE_OPENED);
		assertEquals(mapPage.getMandatoryMappingBanner(), GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_BANNER,
				GlobalConstants.MAP_CREDENTIAL_TYPE_BANNER_DISPLAYED);
		assertEquals(mapPage.getPartnerId(), partnerId, GlobalConstants.MAP_CREDENTIAL_TYPE_PARTNER_ID_DISPLAYED);
		assertEquals(mapPage.getPartnerType(), GlobalConstants.ONLINE_VERIFICATION_PARTNER_TYPE_CODE,
				GlobalConstants.MAP_CREDENTIAL_TYPE_PARTNER_TYPE_DISPLAYED);
		assertEquals(mapPage.getPolicyGroup(), GlobalConstants.DEFAULT_POLICYGROUP,
				GlobalConstants.MAP_CREDENTIAL_TYPE_POLICY_GROUP_DISPLAYED);
		policyName = mapPage.getPolicyName();
		assertEquals(policyName, "ovpcredpol" + dataSuffix, GlobalConstants.MAP_CREDENTIAL_TYPE_POLICY_NAME_DISPLAYED);
		assertEquals(mapPage.waitUntilBiometricModalityPopulated(), GlobalConstants.BIOMETRIC_MODALITY_FACE,
				GlobalConstants.MAP_CREDENTIAL_TYPE_MODALITY_DISPLAYED);
		assertEquals(mapPage.waitUntilProviderConfigurationPopulated(), configurationName,
				GlobalConstants.MAP_CREDENTIAL_TYPE_PROVIDER_CONFIGURATION_DISPLAYED);
		assertTrue(mapPage.areAutoPopulatedFieldsReadOnly(), GlobalConstants.MAP_CREDENTIAL_TYPE_FIELDS_READ_ONLY);
		assertEquals(mapPage.getCredentialTypeText(), GlobalConstants.SELECT_CREDENTIAL_TYPE,
				GlobalConstants.SELECT_CREDENTIAL_TYPE_DISPLAYED);
		assertTrue(mapPage.isSubmitDisabled(), GlobalConstants.MAP_CREDENTIAL_TYPE_SUBMIT_DISABLED_WITHOUT_SELECTION);
	}

	@Test(priority = 2, description = "Credential Type lists the configured credential types", dependsOnMethods = "openMapCredentialTypeForOnlineVerificationPartner")
	public void credentialTypeDropdownListsConfiguredTypes() {
		List<String> options = openSavedCredentialTypePage().getCredentialTypeOptions();
		assertTrue(!options.isEmpty() && options.contains(GlobalConstants.ALLOWED_CREDENTIAL_TYPE_AUTH)
				&& options.contains(GlobalConstants.ALLOWED_CREDENTIAL_TYPE_QRCODE),
				GlobalConstants.CREDENTIAL_TYPE_DROPDOWN_LISTS_CONFIGURED_TYPES);
	}

	@Test(priority = 3, description = "A credential type selection replaces the previous selection", dependsOnMethods = "credentialTypeDropdownListsConfiguredTypes")
	public void selectedCredentialTypeReplacesThePrevious() {
		MapCredentialTypePage mapPage = openSavedCredentialTypePage();
		mapPage.selectCredentialType(GlobalConstants.ALLOWED_CREDENTIAL_TYPE_AUTH);
		assertEquals(mapPage.getCredentialTypeText(), GlobalConstants.ALLOWED_CREDENTIAL_TYPE_AUTH,
				GlobalConstants.CREDENTIAL_TYPE_SELECTION_REPLACES_PREVIOUS);
		mapPage.selectCredentialType(GlobalConstants.ALLOWED_CREDENTIAL_TYPE_QRCODE);
		assertEquals(mapPage.getCredentialTypeText(), GlobalConstants.ALLOWED_CREDENTIAL_TYPE_QRCODE,
				GlobalConstants.CREDENTIAL_TYPE_SELECTION_REPLACES_PREVIOUS);
	}

	@Test(priority = 4, description = "Clear Form resets the credential type and keeps the biometric mapping", dependsOnMethods = "selectedCredentialTypeReplacesThePrevious")
	public void clearFormResetsCredentialType() {
		MapCredentialTypePage mapPage = openSavedCredentialTypePage();
		String modality = mapPage.waitUntilBiometricModalityPopulated();
		String configuration = mapPage.waitUntilProviderConfigurationPopulated();
		mapPage.selectCredentialType(GlobalConstants.ALLOWED_CREDENTIAL_TYPE_AUTH);
		mapPage.clickClearForm();
		assertEquals(mapPage.getCredentialTypeText(), GlobalConstants.SELECT_CREDENTIAL_TYPE,
				GlobalConstants.CLEAR_FORM_RESETS_CREDENTIAL_TYPE);
		assertEquals(mapPage.waitUntilBiometricModalityPopulated(), modality,
				GlobalConstants.CLEAR_FORM_KEEPS_BIOMETRIC_MAPPING);
		assertEquals(mapPage.waitUntilProviderConfigurationPopulated(), configuration,
				GlobalConstants.CLEAR_FORM_KEEPS_BIOMETRIC_MAPPING);
	}

	@Test(priority = 5, description = "Cancel returns to the policy request list", dependsOnMethods = "clearFormResetsCredentialType")
	public void cancelReturnsToPolicyRequestList() {
		MapCredentialTypePage mapPage = openSavedCredentialTypePage();
		mapPage.selectCredentialType(GlobalConstants.ALLOWED_CREDENTIAL_TYPE_AUTH);
		mapPage.clickCancel();
		assertEquals(mapPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE),
				GlobalConstants.PARTNER_POLICY_LINKING_TITLE,
				GlobalConstants.CANCEL_FROM_CREDENTIAL_TYPE_RETURNS_TO_POLICY_LIST);
		mapPage.filterByPartnerId(partnerId);
		String policyRequestRow = mapPage.waitUntilFirstPolicyRequestRowContains(partnerId);
		assertTrue(policyRequestRow.contains(policyName) && policyRequestRow.contains(GlobalConstants.PENDING_FOR_APPROVAL),
				GlobalConstants.POLICY_REQUEST_REMAINS_PENDING_FOR_APPROVAL);
		mapPage.openFirstPolicyRequestAction();
		assertEquals(mapPage.waitUntilMapCredentialTypeActionEnabled(), "enabled",
				GlobalConstants.MAP_CREDENTIAL_TYPE_ACTION_AVAILABLE);
	}

	@Test(priority = 6, description = "Refresh before submit does not save the credential type", dependsOnMethods = "cancelReturnsToPolicyRequestList")
	public void refreshBeforeSubmitDropsTheSelection() {
		MapCredentialTypePage mapPage = openSavedCredentialTypePage();
		mapPage.selectCredentialType(GlobalConstants.ALLOWED_CREDENTIAL_TYPE_AUTH);
		mapPage.refreshThePage();
		assertEquals(mapPage.waitUntilPageTitle(GlobalConstants.MAP_CREDENTIAL_TYPE_TITLE),
				GlobalConstants.MAP_CREDENTIAL_TYPE_TITLE, GlobalConstants.MAP_CREDENTIAL_TYPE_PAGE_OPENED);
		assertEquals(mapPage.getCredentialTypeText(), GlobalConstants.SELECT_CREDENTIAL_TYPE,
				GlobalConstants.REFRESH_BEFORE_SUBMIT_DOES_NOT_SAVE_CREDENTIAL_TYPE);
	}

	@Test(priority = 7, description = "Submit shows the policy request acknowledgement", dependsOnMethods = "refreshBeforeSubmitDropsTheSelection")
	public void submitShowsPolicyRequestAcknowledgement() {
		MapCredentialTypePage mapPage = openSavedCredentialTypePage();
		mapPage.selectCredentialType(GlobalConstants.ALLOWED_CREDENTIAL_TYPE_AUTH);
		mapPage.clickSubmit();
		assertEquals(mapPage.waitUntilPageTitle(GlobalConstants.REQUEST_POLICY_TITLE),
				GlobalConstants.REQUEST_POLICY_TITLE, GlobalConstants.SUBMIT_SHOWS_POLICY_REQUEST_ACKNOWLEDGEMENT);
		assertEquals(mapPage.getAcknowledgementHeader(), GlobalConstants.POLICY_REQUESTED_SUCCESSFULLY,
				GlobalConstants.SUBMIT_SHOWS_POLICY_REQUEST_ACKNOWLEDGEMENT);
		assertEquals(mapPage.getAcknowledgementDescription(), GlobalConstants.POLICY_REQUEST_SUBMITTED_DESCRIPTION,
				GlobalConstants.ACKNOWLEDGEMENT_DESCRIPTION_DISPLAYED);
		mapPage.clickGoBack();
		assertEquals(mapPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE),
				GlobalConstants.PARTNER_POLICY_LINKING_TITLE, GlobalConstants.GO_BACK_RETURNS_TO_POLICY_REQUEST_LIST);
		mapPage.filterByPartnerId(partnerId);
		String policyRequestRow = mapPage.waitUntilFirstPolicyRequestRowContains(partnerId);
		assertTrue(policyRequestRow.contains(GlobalConstants.PENDING_FOR_APPROVAL),
				GlobalConstants.POLICY_REQUEST_REMAINS_PENDING_FOR_APPROVAL);
		mapPage.openFirstPolicyRequestAction();
		assertEquals(mapPage.waitUntilMapCredentialTypeActionDisabled(), "disabled",
				GlobalConstants.SUBMITTED_REQUEST_DISABLES_MAP_CREDENTIAL_TYPE);
	}

	@Test(priority = 8, description = "Approving a submitted policy request shows Approved", dependsOnMethods = "submitShowsPolicyRequestAcknowledgement")
	public void approveSubmittedPolicyRequestShowsApprovedStatus() {
		openPolicyRequestRow(policyName);
		new PartnerPolicyMappingPage(driver).clickOnApproveOrRejectButton();
		new PartnerPolicyMappingPage(driver).clickOnApproveSubmitButton();
		assertTrue(new MapCredentialTypePage(driver)
				.waitUntilFirstPolicyRequestRowContains(partnerId, GlobalConstants.APPROVED)
				.contains(GlobalConstants.APPROVED),
				GlobalConstants.APPROVED_POLICY_REQUEST_SHOWS_APPROVED_STATUS);
	}

	@Test(priority = 9, description = "Home on the policy request acknowledgement opens the dashboard", dependsOnMethods = "approveSubmittedPolicyRequestShowsApprovedStatus")
	public void homeFromAcknowledgementOpensTheDashboard() {
		secondPolicyName = "ovpcredpolb" + dataSuffix;
		publishAuthPolicy(secondPolicyName);
		requestPolicy(partnerId, secondPolicyName);
		saveFaceMapping();
		MapCredentialTypePage mapPage = new MapCredentialTypePage(driver);
		mapPage.waitUntilPageTitle(GlobalConstants.MAP_CREDENTIAL_TYPE_TITLE);
		mapPage.selectCredentialType(GlobalConstants.ALLOWED_CREDENTIAL_TYPE_QRCODE);
		mapPage.clickSubmit();
		mapPage.waitUntilPageTitle(GlobalConstants.REQUEST_POLICY_TITLE);
		mapPage.clickHome();
		assertTrue(new DashboardPage(driver).isWelcomeMessageDisplayed(),
				GlobalConstants.HOME_FROM_ACKNOWLEDGEMENT_OPENS_DASHBOARD);
	}

	@Test(priority = 10, description = "Rejecting a submitted policy request shows Rejected", dependsOnMethods = "homeFromAcknowledgementOpensTheDashboard")
	public void rejectSubmittedPolicyRequestShowsRejectedStatus() {
		openPolicyRequestRow(secondPolicyName);
		new PartnerPolicyMappingPage(driver).clickOnApproveOrRejectButton();
		new PartnerPolicyMappingPage(driver).clickOnRejectButton();
		assertTrue(new MapCredentialTypePage(driver)
				.waitUntilFirstPolicyRequestRowContains(partnerId, GlobalConstants.REJECTED)
				.contains(GlobalConstants.REJECTED),
				GlobalConstants.REJECTED_POLICY_REQUEST_SHOWS_REJECTED_STATUS);
	}

	private void openPolicyRequestRow(String selectedPolicyName) {
		new DashboardPage(driver).clickOnPartnerPolicyMappingTab();
		MapCredentialTypePage mapPage = new MapCredentialTypePage(driver);
		mapPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE);
		mapPage.filterByPartnerIdAndPolicyName(partnerId, selectedPolicyName);
		mapPage.waitUntilFirstPolicyRequestRowContains(partnerId, selectedPolicyName);
		mapPage.openFirstPolicyRequestAction();
	}

	private MapCredentialTypePage openSavedCredentialTypePage() {
		new DashboardPage(driver).clickOnPartnerPolicyMappingTab();
		return openCredentialTypeFromList();
	}

	private MapCredentialTypePage openCredentialTypeFromList() {
		MapCredentialTypePage mapPage = new MapCredentialTypePage(driver);
		mapPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE);
		mapPage.filterByPartnerId(partnerId);
		mapPage.waitUntilFirstPolicyRequestRowContains(partnerId);
		mapPage.openFirstPolicyRequestAction();
		mapPage.waitUntilMapCredentialTypeActionEnabled();
		mapPage.openMapCredentialTypeAction();
		mapPage.waitUntilPageTitle(GlobalConstants.MAP_CREDENTIAL_TYPE_TITLE);
		return mapPage;
	}

	private void requestPolicy(String selectedPartnerId, String selectedPolicyName) {
		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickOnPartnerPolicyMappingTab();
		PartnerPolicyMappingPage policyMappingPage = new PartnerPolicyMappingPage(driver);
		policyMappingPage.clickOnRequestPolicyButton();
		policyMappingPage.selectPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		policyMappingPage.selectPartnerIdDropdown(selectedPartnerId);
		policyMappingPage.selectRequestPolicyByName(selectedPolicyName);
		policyMappingPage.enterRequestPolicyComment(GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_REQUEST_COMMENT);
		policyMappingPage.clickOnRequestPoliciesFormSubmitButton();
	}

	private void saveFaceMapping() {
		MapBiometricExtractorProviderPage mapPage = new MapBiometricExtractorProviderPage(driver);
		mapPage.waitUntilPageTitle(GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_TITLE);
		mapPage.selectModality(1, GlobalConstants.BIOMETRIC_MODALITY_FACE);
		mapPage.selectProviderConfiguration(configurationName);
		assertEquals(mapPage.waitUntilFirstProviderConfigurationSelected(), configurationName,
				GlobalConstants.FACE_PROVIDER_CONFIGURATION_DISPLAYED);
		mapPage.clickSaveAndProceed();
		assertTrue(mapPage.isSaveConfirmationDisplayed(), GlobalConstants.SAVE_CONFIRMATION_DISPLAYED);
		mapPage.confirmSave();
	}

	private void createFaceConfiguration(String selectedConfigurationName, String providerName) {
		new DashboardPage(driver).clickOnBiometricProviderConfiguration();
		BioExtractorConfigurationPage configurationPage = new BioExtractorConfigurationPage(driver);
		configurationPage.clickCreate();
		configurationPage.enterConfigurationName(selectedConfigurationName);
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

	private void createPartner(MispPartnerPage mispPartnerPage, String selectedPartnerId, String email) {
		mispPartnerPage.clickOnPartnerTypeDropdown();
		mispPartnerPage.clickOnPartnerTypeOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		mispPartnerPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		mispPartnerPage.selectNotificationLanguage(GlobalConstants.ONLINE_VERIFICATION_PARTNER_NOTIFICATION_LANGUAGE);
		mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
		mispPartnerPage.enterPartnerAddress(GlobalConstants.ONLINE_VERIFICATION_PARTNER_ADDRESS);
		mispPartnerPage.enterPartnerContactNumber(GlobalConstants.ONLINE_VERIFICATION_PARTNER_CONTACT_NUMBER);
		mispPartnerPage.enterEmailId(email);
		mispPartnerPage.enterUserName(selectedPartnerId);
	}

	private void publishAuthPolicy(String policy) {
		DashboardPage dashboardPage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		AuthPolicyPage authPolicyPage = new AuthPolicyPage(driver);
		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(policy);
		authPolicyPage.enterpolicyDescription(policy);
		authPolicyPage.uploadPolicyData();
		authPolicyPage.clickOnSaveAsDraftButton();
		assertTrue(authPolicyPage.isPolicySavedAsDraftMessageDisplayed(),
				GlobalConstants.isPolicySavedAsDraftMessageDisplayed);
		authPolicyPage.clickOnGoBackButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(policy);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		authPolicyPage.clickOnHomeButton();
	}

}
