package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.BiometricConfigurationPage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.DatasharePolicyPage;
import io.mosip.testrig.pmpuiv2.pages.ManualAdjudicationPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.MapBiometricExtractorProviderPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.PolicyGroupPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(alwaysRun = true, groups = { "MapBiometricExtractorProviderTest" })
public class MapBiometricExtractorProviderTest extends BaseClass {

	private DashboardPage dashboardPage;
	private BiometricConfigurationPage biometricConfigurationPage;
	private ManualAdjudicationPartnerPage partnerPage;
	private PartnerCertificatePage partnerCertificatePage;
	private DatasharePolicyPage datasharePolicyPage;
	private PolicyGroupPage policygroupPage;
	private AuthPolicyPage authPolicyPage;
	private PartnerPolicyMappingPage partnerPolicyMappingPage;
	private MapBiometricExtractorProviderPage mapBioPage;
	private BasePage basePage;

	private static String partnerId;
	private static String policyName;
	private static String bioConfigName;

	public MapBiometricExtractorProviderTest() {
		this.userid = GlobalConstants.QAJAVA21_ADMIN_USER;
		this.password = GlobalConstants.QAJAVA21_ADMIN_PASSWORD;
	}

	@Test(priority = 1, description = "Creating biometric config, OVP partner and datashare policy for mapping")
	public void CreatingPrerequisitesForMapBiometricExtractor() throws InterruptedException {
		partnerId = "ovp" + data;
		policyName = "ovppolicy" + data;
		bioConfigName = "ovpbio" + data;

		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);
		partnerPage = new ManualAdjudicationPartnerPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		basePage = new BasePage(driver);

		handleTermsAndCondition();

		assertTrue(biometricConfigurationPage.isBiometricConfigurationDashboardCardDisplayed(),
				GlobalConstants.isBiometricConfigurationDashboardCardDisplayed);
		biometricConfigurationPage.clickOnBiometricConfigurationDashboardCard();
		biometricConfigurationPage.clickOnCreateBiometricConfigurationButton();
		biometricConfigurationPage.enterConfigurationName(bioConfigName);
		biometricConfigurationPage.enterProviderName(GlobalConstants.BIOMETRIC_CONFIGURATION_PROVIDER_NAME);
		biometricConfigurationPage.enterProviderVersion(GlobalConstants.BIOMETRIC_CONFIGURATION_PROVIDER_VERSION);
		biometricConfigurationPage.selectBiometricModality(GlobalConstants.BIOMETRIC_CONFIGURATION_MODALITY_FACE);
		biometricConfigurationPage.selectCredentialDataFormatRawData();
		biometricConfigurationPage.selectFirstAttributeNameIfNeeded();
		biometricConfigurationPage.clickOnSubmitButton();
		assertTrue(biometricConfigurationPage.isBiometricConfigurationCreatedSuccessfully(),
				GlobalConstants.isBiometricConfigurationCreatedSuccessfully);
		biometricConfigurationPage.clickOnSuccessHomeButton();

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

		dashboardPage.clickOnPartners();
		partnerPage.clickOnCreatePartnerButton();
		partnerPage.clickOnPartnerTypeDropdown();
		partnerPage.clickOnOnlineVerificationPartnerOption();
		partnerPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPage.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
		partnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
		partnerPage.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
		partnerPage.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
		partnerPage.enterEmailId(partnerId + "@gmail.com");
		partnerPage.enterUserName(partnerId);
		assertTrue(partnerPage.isCreatePartnerSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
		partnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(partnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
		partnerPage.clickOnUploadPartnerCertificateButton();
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		partnerCertificatePage.clickOnCloseButton();
		basePage.scrollToStartPage();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyName(policyName);
		datasharePolicyPage.enterpolicyDescription(policyName);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		datasharePolicyPage.clickOnGoBackButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(policyName);
		authPolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnPublishButton();
		datasharePolicyPage.clickOnPublishPolicyButton();
		datasharePolicyPage.clickOnPublishPolicyCloseButton();
	}

	@Test(priority = 2, description = "Requesting policy opens Map Biometric Extractor Provider page",
			dependsOnMethods = "CreatingPrerequisitesForMapBiometricExtractor")
	public void NavigatingToMapBiometricExtractorProviderPage() {
		dashboardPage = new DashboardPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);
		mapBioPage = new MapBiometricExtractorProviderPage(driver);

		handleTermsAndCondition();
		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnRequestPolicyButton();
		partnerPolicyMappingPage.selectPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		partnerPolicyMappingPage.selectPartnerIdDropdown(partnerId);
		partnerPolicyMappingPage.selectPolicyName(policyName);
		partnerPolicyMappingPage.enterRequestPolicyComment(policyName);
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesFormSubmitButtonEnabled(),
				GlobalConstants.isRequestPoliciesFormSubmitButtonEnabled);
		partnerPolicyMappingPage.clickOnRequestPoliciesFormSubmitButton();

		assertTrue(mapBioPage.isMapBiometricExtractorProviderPageDisplayed(),
				GlobalConstants.isMapBiometricExtractorProviderPageDisplayed);
		assertTrue(mapBioPage.getPageTitleText().toLowerCase().contains("biometric"),
				GlobalConstants.isMapBiometricExtractorProviderPageTitleDisplayed);
		assertTrue(mapBioPage.isMandatoryMappingBannerDisplayed(),
				GlobalConstants.isMapBiometricMandatoryBannerDisplayed);
		assertTrue(mapBioPage.isReadOnlyPartnerDetailsDisplayed(partnerId),
				GlobalConstants.isMapBiometricReadOnlyPartnerDetailsDisplayed);
		assertTrue(mapBioPage.isModalityDropdownDisplayed(),
				GlobalConstants.isMapBiometricModalityDropdownDisplayed);
		assertTrue(mapBioPage.isConfigDropdownDisplayed(),
				GlobalConstants.isMapBiometricConfigDropdownDisplayed);
		assertTrue(mapBioPage.isClearFormButtonDisplayed(), GlobalConstants.isMapBiometricClearFormDisplayed);
		assertTrue(mapBioPage.isCancelButtonDisplayed(), GlobalConstants.isMapBiometricCancelDisplayed);
		assertTrue(mapBioPage.isSaveAndProceedButtonDisplayed(),
				GlobalConstants.isMapBiometricSaveAndProceedDisplayed);
		assertFalse(mapBioPage.isSaveAndProceedButtonEnabled(),
				"Save & Proceed should stay disabled until mapping row is complete");
	}

	@Test(priority = 3, description = "Mapping modality and verifying Add More excludes used modality",
			dependsOnMethods = "NavigatingToMapBiometricExtractorProviderPage")
	public void MappingBiometricModalityAndAddMore() throws InterruptedException {
		mapBioPage = new MapBiometricExtractorProviderPage(driver);

		if (!mapBioPage.isMapBiometricExtractorProviderPageDisplayed()) {
			openMapPageViaPolicyRequestAction();
		}

		mapBioPage.selectModalityForRow(1, GlobalConstants.BIOMETRIC_CONFIGURATION_MODALITY_FACE);
		Thread.sleep(1500);
		mapBioPage.selectFirstConfigForRow(1);
		Thread.sleep(1000);

		assertTrue(mapBioPage.isSaveAndProceedButtonEnabled(),
				"Save & Proceed should enable after modality and configuration are selected");
		assertTrue(mapBioPage.isAddMoreButtonDisplayed(), GlobalConstants.isMapBiometricAddMoreDisplayed);
		assertTrue(mapBioPage.isAddMoreButtonEnabled(),
				"Add More should be enabled after first modality mapping is filled");

		mapBioPage.clickOnAddMoreButton();
		Thread.sleep(1000);
		List<String> secondRowModalities = mapBioPage.getModalityOptionsForRow(2);
		boolean faceStillAvailable = secondRowModalities.stream()
				.anyMatch(text -> text.toLowerCase().contains("face"));
		assertFalse(faceStillAvailable, GlobalConstants.isMapBiometricUsedModalityExcludedFromDropdown);
	}

	@Test(priority = 4, description = "Cancel returns to policy request list with Map Biometric action",
			dependsOnMethods = "MappingBiometricModalityAndAddMore")
	public void CancellingMapBiometricReturnsToPolicyRequestList() throws InterruptedException {
		dashboardPage = new DashboardPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);
		mapBioPage = new MapBiometricExtractorProviderPage(driver);

		if (!mapBioPage.isMapBiometricExtractorProviderPageDisplayed()) {
			openMapPageViaPolicyRequestAction();
			mapBioPage.selectModalityForRow(1, GlobalConstants.BIOMETRIC_CONFIGURATION_MODALITY_FACE);
			Thread.sleep(1500);
			mapBioPage.selectFirstConfigForRow(1);
		}

		mapBioPage.clickOnCancelButton();
		Thread.sleep(1500);
		if (mapBioPage.isBlockerPromptDisplayed()) {
			mapBioPage.clickOnBlockerProceedButton();
			Thread.sleep(1500);
		}

		assertTrue(partnerPolicyMappingPage.isStatusDisplayedForPolicyName(policyName,
				GlobalConstants.PENDING_FOR_APPROVAL) || isPolicyRequestListVisible(),
				GlobalConstants.isMapBiometricCancelReturnsToList);

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(policyName);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerPolicyLinkingActionButton(policyName);
		assertTrue(mapBioPage.isMapBiometricActionDisplayedInPolicyRequestsList(),
				GlobalConstants.isMapBiometricActionDisplayedAfterCancel);
	}

	@Test(priority = 5, description = "Save and Proceed navigates to Map Credential Type step",
			dependsOnMethods = "CancellingMapBiometricReturnsToPolicyRequestList")
	public void SavingMapBiometricAndProceedingToCredentialType() throws InterruptedException {
		mapBioPage = new MapBiometricExtractorProviderPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		if (!mapBioPage.isMapBiometricExtractorProviderPageDisplayed()) {
			openMapPageViaPolicyRequestAction();
		}

		mapBioPage.selectModalityForRow(1, GlobalConstants.BIOMETRIC_CONFIGURATION_MODALITY_FACE);
		Thread.sleep(1500);
		mapBioPage.selectFirstConfigForRow(1);
		assertTrue(mapBioPage.isSaveAndProceedButtonEnabled(),
				"Save & Proceed should be enabled before confirm");
		mapBioPage.clickOnSaveAndProceedButton();
		assertTrue(mapBioPage.isSaveConfirmPopupDisplayed(),
				GlobalConstants.isMapBiometricSaveConfirmPopupDisplayed);
		mapBioPage.clickOnSaveConfirmSubmitButton();
		Thread.sleep(2000);
		assertTrue(mapBioPage.isMapCredentialTypePageDisplayed(),
				GlobalConstants.isMapCredentialTypePageDisplayedAfterSave);
	}

	private void openMapPageViaPolicyRequestAction() throws InterruptedException {
		dashboardPage = new DashboardPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);
		mapBioPage = new MapBiometricExtractorProviderPage(driver);

		handleTermsAndCondition();
		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(policyName);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerPolicyLinkingActionButton(policyName);
		mapBioPage.clickOnMapBiometricActionFromPolicyRequestsList();
		Thread.sleep(1500);
		assertTrue(mapBioPage.isMapBiometricExtractorProviderPageDisplayed(),
				GlobalConstants.isMapBiometricExtractorProviderPageDisplayed);
	}

	private boolean isPolicyRequestListVisible() {
		return driver.getCurrentUrl() != null && (driver.getCurrentUrl().contains("policy-requests-list")
				|| driver.getCurrentUrl().contains("partner-policy")
				|| driver.getCurrentUrl().contains("policies-list"));
	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayed()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}
}