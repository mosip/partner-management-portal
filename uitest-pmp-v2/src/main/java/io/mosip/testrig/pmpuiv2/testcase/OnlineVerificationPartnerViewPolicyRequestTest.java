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
import io.mosip.testrig.pmpuiv2.pages.MapCredentialTypePage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "OnlineVerificationPartnerViewPolicyRequestTest" })
public class OnlineVerificationPartnerViewPolicyRequestTest extends BaseClass {

	@Test(priority = 1, description = "Create Online Verification Partner policy requests used by the view details cases")
	public void createOnlineVerificationPartnerPolicyRequestsForView() {
		createFaceConfiguration(GlobalConstants.VIEW_POLICY_REQUEST_CONFIG_NAME,
				GlobalConstants.VIEW_POLICY_REQUEST_PROVIDER_NAME);
		new PartnerCertificatePage(driver).uploadAuthTrustCertificates();
		publishAuthPolicy(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_STEP1);
		publishAuthPolicy(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_STEP2);
		publishAuthPolicy(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_FULL);
		publishAuthPolicy(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_REJECT);

		MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
		createPartner(mispPartnerPage, GlobalConstants.VIEW_POLICY_REQUEST_PARTNER_ID,
				GlobalConstants.VIEW_POLICY_REQUEST_PARTNER_EMAIL);
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
		new PartnerCertificatePage(driver).uploadPartnerCertificate(mispPartnerPage);
		new PartnerCertificatePage(driver).clickOnHomeButton();

		requestPolicyAndLeaveAfterStep1(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_STEP1);
		requestPolicyAndSaveBiometricMapping(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_STEP2);
		requestPolicyMapBiometricAndSubmitCredential(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_FULL,
				GlobalConstants.ALLOWED_CREDENTIAL_TYPE_AUTH);
		requestPolicyMapBiometricAndSubmitCredential(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_REJECT,
				GlobalConstants.ALLOWED_CREDENTIAL_TYPE_QRCODE);
	}

	@Test(priority = 2, description = "Open View Partner-Policy Linking by clicking the policy request row", dependsOnMethods = "createOnlineVerificationPartnerPolicyRequestsForView")
	public void openViewPolicyRequestByRowClick() {
		PartnerPolicyMappingPage listPage = openPolicyRequestList();
		filterByPartnerAndPolicy(listPage, GlobalConstants.VIEW_POLICY_REQUEST_POLICY_FULL);
		listPage.clickOnFirstPolicyRequestRow();
		assertEquals(listPage.waitUntilPageTitle(GlobalConstants.VIEW_PARTNER_POLICY_PAGE),
				GlobalConstants.VIEW_PARTNER_POLICY_PAGE, GlobalConstants.VIEW_POLICY_REQUEST_OPENED_BY_ROW_CLICK);
		assertTrue(listPage.isPartnerPolicyDetailsPageDisplayed(),
				GlobalConstants.VIEW_POLICY_REQUEST_TITLE_DISPLAYED);
		assertTrue(listPage.isBiometricExtractorProviderMappingDisplayed()
				&& listPage.isCredentialTypeSectionDisplayed() && listPage.isViewCommentsSectionDisplayed(),
				GlobalConstants.VIEW_POLICY_REQUEST_SECTIONS_DISPLAYED);
	}

	@Test(priority = 3, description = "Open View Partner-Policy Linking using the View action", dependsOnMethods = "openViewPolicyRequestByRowClick")
	public void openViewPolicyRequestByViewAction() {
		PartnerPolicyMappingPage listPage = openPolicyRequestList();
		filterByPartnerAndPolicy(listPage, GlobalConstants.VIEW_POLICY_REQUEST_POLICY_FULL);
		listPage.openFirstPolicyRequestAction();
		assertTrue(listPage.isViewButtoEnabled(), GlobalConstants.VIEW_POLICY_REQUEST_OPENED_BY_VIEW_ACTION);
		listPage.clickOnViewButton();
		assertEquals(listPage.waitUntilPageTitle(GlobalConstants.VIEW_PARTNER_POLICY_PAGE),
				GlobalConstants.VIEW_PARTNER_POLICY_PAGE, GlobalConstants.VIEW_POLICY_REQUEST_OPENED_BY_VIEW_ACTION);
		assertTrue(listPage.areViewPolicyRequestFieldsReadOnly(), GlobalConstants.VIEW_POLICY_REQUEST_FIELDS_READ_ONLY);
		assertFalse(listPage.isViewPolicyRequestEditControlDisplayed(),
				GlobalConstants.VIEW_POLICY_REQUEST_HAS_NO_EDIT_ACTIONS);
	}

	@Test(priority = 4, description = "View policy request information for a fully completed Online Verification Partner request", dependsOnMethods = "openViewPolicyRequestByViewAction")
	public void viewFullyCompletedPolicyRequestInformation() {
		PartnerPolicyMappingPage viewPage = openView(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_FULL);
		assertEquals(viewPage.getViewPolicyRequestStatusText(), GlobalConstants.PENDING_FOR_APPROVAL,
				GlobalConstants.VIEW_POLICY_REQUEST_STATUS_DISPLAYED);
		assertTrue(viewPage.getViewCreatedOnDateText().startsWith(GlobalConstants.CREATED_ON_LABEL + " "),
				GlobalConstants.VIEW_POLICY_REQUEST_CREATED_ON_DISPLAYED);
		assertTrue(viewPage.getViewCreatedOnTimeText().matches(".*\\d{1,2}:\\d{2}.*"),
				GlobalConstants.VIEW_POLICY_REQUEST_CREATED_ON_DISPLAYED);
		assertTrue(viewPage.getViewPartnerIdSubtitleText().contains(GlobalConstants.VIEW_POLICY_REQUEST_PARTNER_ID),
				GlobalConstants.VIEW_POLICY_REQUEST_FIELDS_MATCH_CREATION);
		assertEquals(viewPage.getViewPartnerTypeText(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
				GlobalConstants.VIEW_POLICY_REQUEST_PARTNER_TYPE_DISPLAYED);
		assertEquals(viewPage.getViewOrganisationText(), GlobalConstants.ORGANISATION_NAME,
				GlobalConstants.VIEW_POLICY_REQUEST_FIELDS_MATCH_CREATION);
		assertEquals(viewPage.getViewPolicyNameText(), GlobalConstants.VIEW_POLICY_REQUEST_POLICY_FULL,
				GlobalConstants.VIEW_POLICY_REQUEST_FIELDS_MATCH_CREATION);
		assertEquals(viewPage.getViewPolicyGroupText(), GlobalConstants.DEFAULT_POLICYGROUP,
				GlobalConstants.VIEW_POLICY_REQUEST_FIELDS_MATCH_CREATION);
		assertEquals(viewPage.getViewPartnerStatusText(), GlobalConstants.PARTNER_STATUS_ACTIVE,
				GlobalConstants.VIEW_POLICY_REQUEST_FIELDS_MATCH_CREATION);
		assertFalse(viewPage.getViewPolicyIdText().isEmpty(), GlobalConstants.VIEW_POLICY_REQUEST_FIELDS_MATCH_CREATION);
	}

	@Test(priority = 5, description = "View biometric extractor mapping for a completed Online Verification Partner request", dependsOnMethods = "viewFullyCompletedPolicyRequestInformation")
	public void viewBiometricExtractorMappingForCompletedRequest() {
		PartnerPolicyMappingPage viewPage = openView(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_FULL);
		assertEquals(viewPage.waitUntilBiometricMappingState(), "mapped",
				GlobalConstants.VIEW_POLICY_REQUEST_BIOMETRIC_MAPPED);
		assertTrue(viewPage.isFaceBiometricModalityMapped(), GlobalConstants.VIEW_POLICY_REQUEST_BIOMETRIC_MAPPED);
		assertTrue(viewPage.isProviderConfigurationMapped(GlobalConstants.VIEW_POLICY_REQUEST_CONFIG_NAME),
				GlobalConstants.VIEW_POLICY_REQUEST_BIOMETRIC_MAPPED);
	}

	@Test(priority = 6, description = "View credential type mapping for a completed Online Verification Partner request", dependsOnMethods = "viewBiometricExtractorMappingForCompletedRequest")
	public void viewCredentialTypeMappingForCompletedRequest() {
		PartnerPolicyMappingPage viewPage = openView(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_FULL);
		assertEquals(viewPage.waitUntilCredentialTypeValue(), GlobalConstants.ALLOWED_CREDENTIAL_TYPE_AUTH,
				GlobalConstants.VIEW_POLICY_REQUEST_CREDENTIAL_MAPPED);
	}

	@Test(priority = 7, description = "View comments for an Online Verification Partner policy request", dependsOnMethods = "viewCredentialTypeMappingForCompletedRequest")
	public void viewCommentsForPolicyRequest() {
		PartnerPolicyMappingPage viewPage = openView(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_FULL);
		assertTrue(viewPage.isViewCommentsSectionDisplayed(), GlobalConstants.VIEW_POLICY_REQUEST_COMMENTS_DISPLAYED);
		assertEquals(viewPage.getViewPartnerCommentText(), GlobalConstants.VIEW_POLICY_REQUEST_COMMENT,
				GlobalConstants.VIEW_POLICY_REQUEST_COMMENTS_DISPLAYED);
		assertEquals(viewPage.getViewAdminCommentStatusText(), GlobalConstants.PENDING_FOR_APPROVAL,
				GlobalConstants.VIEW_POLICY_REQUEST_COMMENTS_DISPLAYED);
	}

	@Test(priority = 8, description = "View an approved Online Verification Partner policy request", dependsOnMethods = "viewCommentsForPolicyRequest")
	public void viewApprovedPolicyRequest() {
		PartnerPolicyMappingPage listPage = openPolicyRequestList();
		filterByPartnerAndPolicy(listPage, GlobalConstants.VIEW_POLICY_REQUEST_POLICY_FULL);
		listPage.openFirstPolicyRequestAction();
		listPage.clickOnApproveOrRejectButton();
		assertEquals(listPage.waitUntilApproveButtonEnabled(), "enabled",
				GlobalConstants.VIEW_POLICY_REQUEST_APPROVED_STATUS);
		listPage.clickOnApproveSubmitButton();
		listPage.waitUntilFirstPolicyRequestRowContains(GlobalConstants.VIEW_POLICY_REQUEST_PARTNER_ID,
				GlobalConstants.APPROVED);

		PartnerPolicyMappingPage viewPage = openView(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_FULL);
		assertEquals(viewPage.getViewPolicyRequestStatusText(), GlobalConstants.APPROVED,
				GlobalConstants.VIEW_POLICY_REQUEST_APPROVED_STATUS);
		assertEquals(viewPage.waitUntilBiometricMappingState(), "mapped",
				GlobalConstants.VIEW_POLICY_REQUEST_BIOMETRIC_MAPPED);
		assertEquals(viewPage.waitUntilCredentialTypeValue(), GlobalConstants.ALLOWED_CREDENTIAL_TYPE_AUTH,
				GlobalConstants.VIEW_POLICY_REQUEST_CREDENTIAL_MAPPED);
		assertTrue(viewPage.areViewPolicyRequestFieldsReadOnly(), GlobalConstants.VIEW_POLICY_REQUEST_FIELDS_READ_ONLY);
	}

	@Test(priority = 9, description = "View a rejected Online Verification Partner policy request", dependsOnMethods = "viewApprovedPolicyRequest")
	public void viewRejectedPolicyRequest() {
		PartnerPolicyMappingPage listPage = openPolicyRequestList();
		filterByPartnerAndPolicy(listPage, GlobalConstants.VIEW_POLICY_REQUEST_POLICY_REJECT);
		listPage.openFirstPolicyRequestAction();
		listPage.clickOnApproveOrRejectButton();
		assertEquals(listPage.waitUntilApproveButtonEnabled(), "enabled",
				GlobalConstants.VIEW_POLICY_REQUEST_REJECTED_STATUS);
		listPage.clickOnRejectButton();
		listPage.waitUntilFirstPolicyRequestRowContains(GlobalConstants.VIEW_POLICY_REQUEST_PARTNER_ID,
				GlobalConstants.REJECTED);

		PartnerPolicyMappingPage viewPage = openView(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_REJECT);
		assertEquals(viewPage.getViewPolicyRequestStatusText(), GlobalConstants.REJECTED,
				GlobalConstants.VIEW_POLICY_REQUEST_REJECTED_STATUS);
		assertEquals(viewPage.getViewAdminCommentStatusText(), GlobalConstants.REJECTED,
				GlobalConstants.VIEW_POLICY_REQUEST_COMMENTS_DISPLAYED);
		assertTrue(viewPage.areViewPolicyRequestFieldsReadOnly(), GlobalConstants.VIEW_POLICY_REQUEST_FIELDS_READ_ONLY);
	}

	@Test(priority = 10, description = "Back returns to Partner - Policy Linking with the partner filter intact", dependsOnMethods = "viewRejectedPolicyRequest")
	public void backReturnsToPolicyRequestListWithFilter() {
		PartnerPolicyMappingPage listPage = openPolicyRequestList();
		filterByPartnerAndPolicy(listPage, GlobalConstants.VIEW_POLICY_REQUEST_POLICY_STEP1);
		listPage.openFirstPolicyRequestAction();
		listPage.clickOnViewButton();
		assertEquals(listPage.waitUntilPageTitle(GlobalConstants.VIEW_PARTNER_POLICY_PAGE),
				GlobalConstants.VIEW_PARTNER_POLICY_PAGE, GlobalConstants.VIEW_POLICY_REQUEST_TITLE_DISPLAYED);
		listPage.clickOnViewBackButton();
		assertEquals(listPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE),
				GlobalConstants.PARTNER_POLICY_LINKING_TITLE, GlobalConstants.VIEW_POLICY_REQUEST_BACK_PRESERVES_FILTER);
		assertEquals(listPage.getPartnerIdFilterValue(), GlobalConstants.VIEW_POLICY_REQUEST_PARTNER_ID,
				GlobalConstants.VIEW_POLICY_REQUEST_BACK_PRESERVES_FILTER);
	}

	@Test(priority = 11, description = "Partial workflow with only Request Policy completed shows unmapped sections", dependsOnMethods = "backReturnsToPolicyRequestListWithFilter")
	public void viewPartialWorkflowWithOnlyRequestPolicyCompleted() {
		PartnerPolicyMappingPage viewPage = openView(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_STEP1);
		assertEquals(viewPage.getViewPolicyNameText(), GlobalConstants.VIEW_POLICY_REQUEST_POLICY_STEP1,
				GlobalConstants.VIEW_POLICY_REQUEST_FIELDS_MATCH_CREATION);
		assertTrue(viewPage.isBiometricExtractorProviderMappingDisplayed()
				&& viewPage.isCredentialTypeSectionDisplayed(), GlobalConstants.VIEW_POLICY_REQUEST_SECTIONS_DISPLAYED);
		assertEquals(viewPage.waitUntilBiometricMappingState(), "unmapped",
				GlobalConstants.VIEW_POLICY_REQUEST_BIOMETRIC_UNMAPPED);
		assertEquals(viewPage.waitUntilCredentialTypeValue(), GlobalConstants.BLANK_FIELD_PLACEHOLDER,
				GlobalConstants.VIEW_POLICY_REQUEST_CREDENTIAL_UNMAPPED);
	}

	@Test(priority = 12, description = "Partial workflow with biometric mapping completed keeps credential type unmapped", dependsOnMethods = "viewPartialWorkflowWithOnlyRequestPolicyCompleted")
	public void viewPartialWorkflowWithBiometricMappingCompleted() {
		PartnerPolicyMappingPage viewPage = openView(GlobalConstants.VIEW_POLICY_REQUEST_POLICY_STEP2);
		assertEquals(viewPage.waitUntilBiometricMappingState(), "mapped",
				GlobalConstants.VIEW_POLICY_REQUEST_BIOMETRIC_MAPPED);
		assertTrue(viewPage.isFaceBiometricModalityMapped(), GlobalConstants.VIEW_POLICY_REQUEST_BIOMETRIC_MAPPED);
		assertEquals(viewPage.waitUntilCredentialTypeValue(), GlobalConstants.BLANK_FIELD_PLACEHOLDER,
				GlobalConstants.VIEW_POLICY_REQUEST_CREDENTIAL_UNMAPPED);
	}

	@Test(priority = 13, description = "A user without PARTNER_ADMIN stays on the main page", dependsOnMethods = "viewPartialWorkflowWithBiometricMappingCompleted")
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
				&& !dashboardPage.isPartnerAdminSideNavDisplayed()
				&& !dashboardPage.isPartnerPolicyMappingTabDisplayed(),
				GlobalConstants.VIEW_POLICY_REQUEST_NON_ADMIN_STAYS_ON_MAIN_PAGE);
	}

	private PartnerPolicyMappingPage openPolicyRequestList() {
		new DashboardPage(driver).clickOnPartnerPolicyMappingTab();
		PartnerPolicyMappingPage listPage = new PartnerPolicyMappingPage(driver);
		listPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE);
		return listPage;
	}

	private void filterByPartnerAndPolicy(PartnerPolicyMappingPage listPage, String policyName) {
		if (listPage.isFilterResetButtonEnabledQuick()) {
			listPage.clickOnFilterResetButton();
		}
		listPage.filterPolicyRequestsByPartnerIdAndPolicyName(GlobalConstants.VIEW_POLICY_REQUEST_PARTNER_ID,
				policyName);
		listPage.waitUntilFirstPolicyRequestRowContains(GlobalConstants.VIEW_POLICY_REQUEST_PARTNER_ID, policyName);
	}

	private PartnerPolicyMappingPage openView(String policyName) {
		PartnerPolicyMappingPage listPage = openPolicyRequestList();
		filterByPartnerAndPolicy(listPage, policyName);
		listPage.openFirstPolicyRequestAction();
		listPage.clickOnViewButton();
		assertEquals(listPage.waitUntilPageTitle(GlobalConstants.VIEW_PARTNER_POLICY_PAGE),
				GlobalConstants.VIEW_PARTNER_POLICY_PAGE, GlobalConstants.VIEW_POLICY_REQUEST_TITLE_DISPLAYED);
		return listPage;
	}

	private void requestPolicyAndLeaveAfterStep1(String policyName) {
		requestPolicy(policyName);
		MapBiometricExtractorProviderPage mapPage = new MapBiometricExtractorProviderPage(driver);
		mapPage.waitUntilPageTitle(GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_TITLE);
		mapPage.clickCancel();
		mapPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE);
	}

	private void requestPolicyAndSaveBiometricMapping(String policyName) {
		requestPolicy(policyName);
		saveFaceMappingAndReturnToList();
	}

	private void requestPolicyMapBiometricAndSubmitCredential(String policyName, String credentialType) {
		requestPolicy(policyName);
		MapBiometricExtractorProviderPage mapPage = new MapBiometricExtractorProviderPage(driver);
		mapPage.waitUntilPageTitle(GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_TITLE);
		mapPage.selectModality(1, GlobalConstants.BIOMETRIC_MODALITY_FACE);
		mapPage.selectProviderConfiguration(GlobalConstants.VIEW_POLICY_REQUEST_CONFIG_NAME);
		mapPage.clickSaveAndProceed();
		assertTrue(mapPage.isSaveConfirmationDisplayed(), GlobalConstants.SAVE_CONFIRMATION_DISPLAYED);
		mapPage.confirmSave();
		MapCredentialTypePage credentialPage = new MapCredentialTypePage(driver);
		credentialPage.waitUntilPageTitle(GlobalConstants.MAP_CREDENTIAL_TYPE_TITLE);
		credentialPage.selectCredentialType(credentialType);
		credentialPage.clickSubmit();
		credentialPage.waitUntilPageTitle(GlobalConstants.REQUEST_POLICY_TITLE);
		credentialPage.clickGoBack();
		credentialPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE);
	}

	private void saveFaceMappingAndReturnToList() {
		MapBiometricExtractorProviderPage mapPage = new MapBiometricExtractorProviderPage(driver);
		mapPage.waitUntilPageTitle(GlobalConstants.MAP_BIOMETRIC_EXTRACTOR_PROVIDER_TITLE);
		mapPage.selectModality(1, GlobalConstants.BIOMETRIC_MODALITY_FACE);
		mapPage.selectProviderConfiguration(GlobalConstants.VIEW_POLICY_REQUEST_CONFIG_NAME);
		mapPage.clickSaveAndProceed();
		assertTrue(mapPage.isSaveConfirmationDisplayed(), GlobalConstants.SAVE_CONFIRMATION_DISPLAYED);
		mapPage.confirmSave();
		MapCredentialTypePage credentialPage = new MapCredentialTypePage(driver);
		credentialPage.waitUntilPageTitle(GlobalConstants.MAP_CREDENTIAL_TYPE_TITLE);
		credentialPage.clickTitleBack();
		credentialPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE);
	}

	private void requestPolicy(String policyName) {
		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickOnPartnerPolicyMappingTab();
		PartnerPolicyMappingPage policyMappingPage = new PartnerPolicyMappingPage(driver);
		policyMappingPage.waitUntilPageTitle(GlobalConstants.PARTNER_POLICY_LINKING_TITLE);
		policyMappingPage.clickOnRequestPolicyButton();
		policyMappingPage.selectPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		policyMappingPage.selectPartnerIdDropdown(GlobalConstants.VIEW_POLICY_REQUEST_PARTNER_ID);
		policyMappingPage.selectRequestPolicyByName(policyName);
		policyMappingPage.enterRequestPolicyComment(GlobalConstants.VIEW_POLICY_REQUEST_COMMENT);
		policyMappingPage.clickOnRequestPoliciesFormSubmitButton();
	}

	private void createFaceConfiguration(String configurationName, String providerName) {
		new DashboardPage(driver).clickOnBiometricProviderConfiguration();
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
