package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.ManualAdjudicationPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerAdminPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "ManualAdjudicationPartnerTest" })
public class ManualAdjudicationPartnerTest extends BaseClass {

	private DashboardPage dashboardPage;
	private ManualAdjudicationPartnerPage maPartnerPage;
	private PartnerCertificatePage partnerCertificatePage;
	private BasePage basePage;

	@Test(priority = 1, description = "Create Manual Adjudication Partner with AUTH trust and certificate upload")
	public void createManualAdjudicationPartner() throws InterruptedException {
		maPartnerPage = new ManualAdjudicationPartnerPage(driver);
		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		basePage = new BasePage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(partnerCertificatePage.isUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		partnerCertificatePage.clickOnIntermediateCACertTab();
		assertTrue(partnerCertificatePage.isIntermediateUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnHomeButton();

		dashboardPage.clickOnPartners();
		maPartnerPage.clickOnCreatePartnerButton();
		assertTrue(maPartnerPage.isCreatePrtnerPageTitleDisplayed(),
				GlobalConstants.isCreatePrtnerPageTitleDisplayed);
		assertEquals(maPartnerPage.getCreatePartnerPageTitleText(), GlobalConstants.CREATE_PARTNER_PAGE_TITLE,
				GlobalConstants.isCreatePartnerPageTitleCorrect);
		assertTrue(maPartnerPage.isCreatePartnerMandatoryFieldInfoDisplayed(),
				GlobalConstants.isCreatePartnerMandatoryFieldInfoDisplayed);
		assertTrue(maPartnerPage.isDefaultManualAdjudicationPartnerDisplayed(),
				GlobalConstants.isDefaultManualAdjudicationPartnerDisplayed);

		maPartnerPage.clickOnPartnerOragnizationInfoButton();
		assertTrue(maPartnerPage.isOrganizationNameInfoDisplayed(), GlobalConstants.isOrganizationNameInfoDisplayed);

		createMaPartner(GlobalConstants.MA_PARTNER_USER, GlobalConstants.MANUAL_ADJUDICATION_PARTNER,
				GlobalConstants.DEFAULT_POLICYGROUP, GlobalConstants.MA_NOTIFICATION_LANGUAGE,
				GlobalConstants.MA_ADDRESS, GlobalConstants.ORGANISATION_NAME, GlobalConstants.MA_CONTACT_NUMBER,
				GlobalConstants.MA_EMAIL_ID);

		assertTrue(maPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isManualAdjudicationPartnerCreatedSuccessfully);
		assertTrue(maPartnerPage.isUploadPartnerCertificateButtonDisplayed(),
				GlobalConstants.isUploadPartnerCertificateButtonDisplayed);
		assertTrue(maPartnerPage.isCreatePartnerSuccessMsgHomeButtonDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgHomeButtonDisplayed);

		maPartnerPage.clickOnUploadPartnerCertificateButton();
		assertTrue(partnerCertificatePage.isMispPartnerCertificatePopupDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed(),
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		basePage.scrollToStartPage();

		assertTrue(maPartnerPage.isManualAdjudicationPartnerRowStatusDisplayed(GlobalConstants.MA_PARTNER_USER,
				GlobalConstants.UPLOADED_STATUS, GlobalConstants.ACTIVE_STATUS), GlobalConstants.isStatusDisplayed);

		maPartnerPage.clickActionButtonByPartnerId(GlobalConstants.MA_PARTNER_USER);
		maPartnerPage.clickOnUploadOrReuploadCertificateButton();
		assertTrue(partnerCertificatePage.isMispPartnerCertificatePopupDisplayed(),
				GlobalConstants.isMispPartnerCertificatePopupDisplayed);
		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateDisplayed(),
				GlobalConstants.isReUploadPartnerCertificateDisplayed);
		assertTrue(partnerCertificatePage.isCorrespondingPartnerIdDisplayed(),
				GlobalConstants.isCorrespondingPartnerIdDisplayed);
		assertEquals(partnerCertificatePage.getPartnerType(), GlobalConstants.MANUAL_ADJUDICATION_PARTNER,
				"Verify partner type is Manual Adjudication Partner");
		assertEquals(partnerCertificatePage.getPartnerDomainType(), GlobalConstants.AUTH_DOMAIN_TYPE,
				"Verify partner domain is AUTH");
		assertTrue(partnerCertificatePage.isPartnercertFormatesTextDisplayed(),
				GlobalConstants.isCertFormatesTextDisplayed);
		assertTrue(partnerCertificatePage.isLastUploadTimeAndDateTextDisplayed(),
				GlobalConstants.isLastUploadTimeAndDateTextDisplayed);
		assertTrue(partnerCertificatePage.isCertificateFormatTextNotEditable(),
				GlobalConstants.isCertificateFormatTextNotEditable);
		partnerCertificatePage.clickOnCertificateUploadCancelButton();
	}

	@Test(priority = 2, description = "Negative validations, cancel, duplicate email/username for MA partner",
			dependsOnMethods = "createManualAdjudicationPartner")
	public void createMultipleManualAdjudicationPartner() throws InterruptedException {
		maPartnerPage = new ManualAdjudicationPartnerPage(driver);
		dashboardPage = new DashboardPage(driver);

		dashboardPage.clickOnPartners();
		maPartnerPage.clickOnCreatePartnerButton();
		verifyEnteringSpecialCharacterInAllMandatoryField();
		maPartnerPage.clickOnCreatePartnerClearButton();
		assertTrue(maPartnerPage.isCreatePartnerSubmitButtonDisabled(),
				GlobalConstants.isCreatePartnerSubmitButtonDisabled);

		maPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
		maPartnerPage.clickOnCreatePartnerCancelButton();
		assertTrue(maPartnerPage.isCancelConfirmationPopupDisplayed(),
				GlobalConstants.isCancelConfirmationPopupDisplayed);
		maPartnerPage.clickOnCancelConfirmationPopupProceedButton();
		assertTrue(maPartnerPage.isListOfPartnersDisplayed(), GlobalConstants.isListOfPartnersDisplayed);

		maPartnerPage.clickOnCreatePartnerButton();
		createMaPartner(GlobalConstants.MA_PARTNER_USER, GlobalConstants.MANUAL_ADJUDICATION_PARTNER,
				GlobalConstants.DEFAULT_POLICYGROUP, GlobalConstants.MA_NOTIFICATION_LANGUAGE,
				GlobalConstants.MA_ADDRESS, GlobalConstants.ORGANISATION_NAME, GlobalConstants.MA_CONTACT_NUMBER,
				GlobalConstants.MA_EMAIL_ID);
		assertTrue(maPartnerPage.isEmailAddressIsAlreadyRegisteredErrorDisplayed()
				|| maPartnerPage.isUsernameAlreadyExistErrorDisplayed(),
				GlobalConstants.isEmailAddressIsAlreadyRegisteredErrorDisplayed);

		maPartnerPage.clickOnCreatePartnerClearButton();
		createMaPartner(GlobalConstants.MA_PARTNER_USER_2, GlobalConstants.MANUAL_ADJUDICATION_PARTNER,
				GlobalConstants.DEFAULT_POLICYGROUP, GlobalConstants.MA_NOTIFICATION_LANGUAGE,
				GlobalConstants.MA_ADDRESS, GlobalConstants.ORGANISATION_NAME, GlobalConstants.MA_CONTACT_NUMBER,
				GlobalConstants.MA_EMAIL_ID_2);
		assertTrue(maPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isMultipleManualAdjudicationPartnersCreatable);
		maPartnerPage.clickOnSuccessMsgHomeButton();
	}

	@Test(priority = 3, description = "Verify Create Partner page defaults to Manual Adjudication Partner",
			dependsOnMethods = "createManualAdjudicationPartner")
	public void verifyDefaultManualAdjudicationPartnerType() {
		maPartnerPage = new ManualAdjudicationPartnerPage(driver);
		dashboardPage = new DashboardPage(driver);

		dashboardPage.clickOnPartners();
		maPartnerPage.clickOnCreatePartnerButton();
		assertTrue(maPartnerPage.isCreatePrtnerPageTitleDisplayed(),
				GlobalConstants.isCreatePartnerPageNavigationDisplayed);
		assertTrue(maPartnerPage.isDefaultManualAdjudicationPartnerDisplayed(),
				GlobalConstants.isDefaultManualAdjudicationPartnerDisplayed);
		assertTrue(maPartnerPage.isCreatePartnerSubmitButtonDisabled(),
				GlobalConstants.isCreatePartnerSubmitButtonDisabled);
		maPartnerPage.clickOnPartnerTypeDropdown();
		assertTrue(maPartnerPage.isManualAdjudicationPartnerOptionDisplayed(),
				GlobalConstants.isManualAdjudicationPartnerOptionDisplayed);
	}

	@Test(priority = 4, description = "MOSIP-44410 View Manual Adjudication Partner details (read-only)",
			dependsOnMethods = "createManualAdjudicationPartner")
	public void viewManualAdjudicationPartnerDetails() {
		maPartnerPage = new ManualAdjudicationPartnerPage(driver);
		dashboardPage = new DashboardPage(driver);
		PartnerAdminPage partnerAdminPage = new PartnerAdminPage(driver);

		dashboardPage.clickOnPartners();
		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(GlobalConstants.MA_PARTNER_USER);
		partnerAdminPage.clickOnPartnerTypeDropdown();
		partnerAdminPage.selectPartnerTypeFilterOption(GlobalConstants.MANUAL_ADJUDICATION_PARTNER);
		partnerAdminPage.clickOnApplyFiltersBtn();

		maPartnerPage.clickActionButtonByPartnerId(GlobalConstants.MA_PARTNER_USER);
		assertTrue(partnerAdminPage.isViewButtonsDisplayed(), GlobalConstants.isViewButtonsDisplayed);
		maPartnerPage.clickOnViewButton();

		assertTrue(maPartnerPage.isViewPartnerDetailsPageDisplayed(),
				GlobalConstants.isViewPartnerDetailsPageDisplayed);
		assertTrue(maPartnerPage.isViewPartnerIdDisplayed(), GlobalConstants.isPartnerIdDisplayed);
		assertEquals(maPartnerPage.getViewPartnerIdText().trim(), GlobalConstants.MA_PARTNER_USER,
				GlobalConstants.isPartnerIdDisplayed);
		assertTrue(maPartnerPage.isViewPartnerStatusDisplayed(),
				GlobalConstants.isPartnerStatusInViewPartnerPageDisplayed);
		assertTrue(maPartnerPage.isViewPartnerCreatedOnDisplayed(),
				GlobalConstants.isPartnerCreatedDateInViewPartnerPageDisplayed);
		assertTrue(maPartnerPage.isViewPartnerTypeDisplayed(),
				GlobalConstants.isPartnerTypeInViewPartnerPageDisplayed);
		String viewPartnerType = maPartnerPage.getViewPartnerTypeText();
		assertTrue(viewPartnerType.contains(GlobalConstants.MANUAL_ADJUDICATION_PARTNER)
				|| viewPartnerType.toLowerCase().contains("manual adjudication")
				|| viewPartnerType.toLowerCase().contains("manual_adjudication")
				|| viewPartnerType.toUpperCase().contains("MANUAL_ADJUDICATION"),
				GlobalConstants.isManualAdjudicationPartnerTypeInViewDisplayed);
		assertTrue(maPartnerPage.isViewOrganisationNameDisplayed(),
				GlobalConstants.isOrganisationNameInViewPartnerPageDisplayed);
		assertTrue(maPartnerPage.isViewPartnerCertificateSectionDisplayed(),
				GlobalConstants.isPartnerCertificateInViewPartnerDetailsPageDisplayed);
		assertTrue(maPartnerPage.isViewPageReadOnly(), GlobalConstants.isViewPartnerDetailsReadOnly);
		assertTrue(maPartnerPage.isViewTitleBackIconDisplayed() || maPartnerPage.isViewGoBackButtonDisplayed(),
				GlobalConstants.isViewPartnerBackNavigationDisplayed);

		maPartnerPage.clickOnViewGoBackButton();
		assertTrue(maPartnerPage.isListOfPartnersDisplayed(), GlobalConstants.isListOfPartnersDisplayed);
	}

	@Test(priority = 5, description = "MOSIP-44349 Later upload cert from list + invalid/expired negatives",
			dependsOnMethods = "createMultipleManualAdjudicationPartner")
	public void uploadCertificateFromPartnersListForMaPartner() throws InterruptedException {
		maPartnerPage = new ManualAdjudicationPartnerPage(driver);
		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		PartnerAdminPage partnerAdminPage = new PartnerAdminPage(driver);
		basePage = new BasePage(driver);

		dashboardPage.clickOnPartners();
		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(GlobalConstants.MA_PARTNER_USER_2);
		partnerAdminPage.clickOnPartnerTypeDropdown();
		partnerAdminPage.selectPartnerTypeFilterOption(GlobalConstants.MANUAL_ADJUDICATION_PARTNER);
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(maPartnerPage.isManualAdjudicationPartnerRowStatusDisplayed(GlobalConstants.MA_PARTNER_USER_2,
				GlobalConstants.NOTUPLOADED_STATUS, GlobalConstants.INACTIVE_STATUS), GlobalConstants.isStatusDisplayed);

		maPartnerPage.clickActionButtonByPartnerId(GlobalConstants.MA_PARTNER_USER_2);
		maPartnerPage.clickOnUploadOrReuploadCertificateButton();
		assertTrue(partnerCertificatePage.isMispPartnerCertificatePopupDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertEquals(partnerCertificatePage.getPartnerType(), GlobalConstants.MANUAL_ADJUDICATION_PARTNER,
				"Verify partner type is Manual Adjudication Partner");
		assertEquals(partnerCertificatePage.getPartnerDomainType(), GlobalConstants.AUTH_DOMAIN_TYPE,
				"Verify partner domain is AUTH");

		partnerCertificatePage.uploadCertificateInvalidCert();
		assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(),
				GlobalConstants.isInvalidCertFormatePopupDisplayed);

		partnerCertificatePage.uploadExpiredCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isCertificateExpiredErrorDisplayed(),
				GlobalConstants.isCertificateExpiredErrorDisplayed);
		partnerCertificatePage.clickOnCertificateUploadCancelButton();

		maPartnerPage.clickActionButtonByPartnerId(GlobalConstants.MA_PARTNER_USER_2);
		maPartnerPage.clickOnUploadOrReuploadCertificateButton();
		assertTrue(partnerCertificatePage.isMispPartnerCertificatePopupDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed(),
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		basePage.scrollToStartPage();

		assertTrue(maPartnerPage.isManualAdjudicationPartnerRowStatusDisplayed(GlobalConstants.MA_PARTNER_USER_2,
				GlobalConstants.UPLOADED_STATUS, GlobalConstants.ACTIVE_STATUS), GlobalConstants.isStatusDisplayed);
	}

	@Test(priority = 6, description = "MOSIP-44411 Deactivate Manual Adjudication Partner (cancel + confirm)",
			dependsOnMethods = "uploadCertificateFromPartnersListForMaPartner")
	public void deactivateManualAdjudicationPartner() {
		maPartnerPage = new ManualAdjudicationPartnerPage(driver);
		dashboardPage = new DashboardPage(driver);
		PartnerAdminPage partnerAdminPage = new PartnerAdminPage(driver);

		dashboardPage.clickOnPartners();
		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(GlobalConstants.MA_PARTNER_USER_2);
		partnerAdminPage.clickOnPartnerTypeDropdown();
		partnerAdminPage.selectPartnerTypeFilterOption(GlobalConstants.MANUAL_ADJUDICATION_PARTNER);
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(maPartnerPage.isManualAdjudicationPartnerRowStatusDisplayed(GlobalConstants.MA_PARTNER_USER_2,
				GlobalConstants.UPLOADED_STATUS, GlobalConstants.ACTIVE_STATUS), GlobalConstants.isStatusDisplayed);

		maPartnerPage.clickActionButtonByPartnerId(GlobalConstants.MA_PARTNER_USER_2);
		assertTrue(partnerAdminPage.isDeactivateButtonsDisplayed(), GlobalConstants.isDeactivateButtonsDisplayed);
		assertTrue(partnerAdminPage.isDeactivateOptionEnabled(), GlobalConstants.isDeactivateOptionEnabled);

		partnerAdminPage.clickOnDeactivateOptionInActionMenu();
		assertTrue(partnerAdminPage.isDeactivatePopupHeaderDisplayed(),
				GlobalConstants.isDeactivatePopupHeaderDisplayed);
		assertTrue(partnerAdminPage.isDeactivatePopupDescriptionDisplayed(),
				GlobalConstants.isDeactivatePopupDescriptionDisplayed);
		assertTrue(partnerAdminPage.isDeactivatePopupCancelButtonDisplayed(),
				GlobalConstants.isDeactivatePopupCancelButtonDisplayed);
		assertTrue(partnerAdminPage.isDeactivatePopupConfirmButtonDisplayed(),
				GlobalConstants.isDeactivatePopupConfirmButtonDisplayed);

		String expectedTitle = String.format(GlobalConstants.DEACTIVATE_PARTNER_POPUP_TITLE,
				GlobalConstants.MA_PARTNER_USER_2, GlobalConstants.ORGANISATION_NAME);
		assertEquals(partnerAdminPage.getDeactivatePopupTitle(), expectedTitle,
				GlobalConstants.isDeactivatePopupTitleCorrect);

		partnerAdminPage.clickOnDeactivatePopupCancelButton();
		assertTrue(maPartnerPage.isManualAdjudicationPartnerRowStatusDisplayed(GlobalConstants.MA_PARTNER_USER_2,
				GlobalConstants.UPLOADED_STATUS, GlobalConstants.ACTIVE_STATUS),
				GlobalConstants.isPartnerStatusUnchangedAfterCancel);

		maPartnerPage.clickActionButtonByPartnerId(GlobalConstants.MA_PARTNER_USER_2);
		partnerAdminPage.clickOnDeactivateOptionInActionMenu();
		partnerAdminPage.clickOnDeactivatePopupConfirmButton();

		assertTrue(maPartnerPage.isManualAdjudicationPartnerRowStatusDisplayed(GlobalConstants.MA_PARTNER_USER_2,
				GlobalConstants.UPLOADED_STATUS, GlobalConstants.DEACTIVATED),
				GlobalConstants.isPartnerStatusDeactivatedAfterConfirm);
		assertTrue(partnerAdminPage.isFirstPartnerRowGreyedOut(),
				GlobalConstants.isPartnerRowGreyedOutAfterConfirm);

		maPartnerPage.clickActionButtonByPartnerId(GlobalConstants.MA_PARTNER_USER_2);
		assertTrue(partnerAdminPage.isDeactivateOptionDisabled(),
				GlobalConstants.isDeactivateOptionDisabledForDeactivatedPartner);
		assertFalse(partnerAdminPage.isDeactivateOptionEnabled(), GlobalConstants.isDeactivateOptionEnabled);

		assertTrue(partnerAdminPage.isViewButtonsDisplayed(), GlobalConstants.isViewButtonsDisplayed);
		maPartnerPage.clickOnViewButton();
		assertEquals(partnerAdminPage.getPartnerStatusInViewPartnerDetails(), GlobalConstants.DEACTIVATED,
				GlobalConstants.isPartnerStatusDeactivatedInViewDetails);
		assertTrue(partnerAdminPage.isPartnerCertificateSectionGreyedOut(),
				GlobalConstants.isCertificateSectionGreyedOutInViewDetails);
		maPartnerPage.clickOnViewGoBackButton();
		assertTrue(maPartnerPage.isListOfPartnersDisplayed(), GlobalConstants.isListOfPartnersDisplayed);
	}

	private void createMaPartner(String userName, String partnerType, String policyGroup, String notification,
			String address, String organisation, String contactNumber, String emailId) {
		if (!maPartnerPage.isDefaultManualAdjudicationPartnerDisplayed()) {
			maPartnerPage.clickOnPartnerTypeDropdown();
			maPartnerPage.clickOnManualAdjudicationPartnerOption();
		}
		maPartnerPage.selectPolicyGroupDropdown(policyGroup);
		maPartnerPage.selectNotificationLanguage(notification);
		maPartnerPage.enterPartnerAddress(address);
		maPartnerPage.enterPartnerOrganisation(organisation);
		maPartnerPage.enterPartnerContactNumber(contactNumber);
		maPartnerPage.enterEmailId(emailId);
		maPartnerPage.enterUserName(userName);
		assertTrue(maPartnerPage.isCreatePartnerSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
		maPartnerPage.clickOnCreatePartnerSubmitButton();
	}

	private void verifyEnteringSpecialCharacterInAllMandatoryField() {
		maPartnerPage.enterPartnerAddress(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(maPartnerPage.isPartnerAddressSpecialChNotAllowErrorDisplayed(),
				GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
		maPartnerPage.enterPartnerOrganisation(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(maPartnerPage.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
				GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
		maPartnerPage.enterPartnerContactNumber(GlobalConstants.SPECIAL_CHARACTERS);
		maPartnerPage.enterEmailId(GlobalConstants.SPECIAL_CHARACTERS);
		maPartnerPage.enterUserName(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(maPartnerPage.isPartnerUserNameSpecialChNotAllowErrorDisplayed(),
				GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
		assertTrue(maPartnerPage.isCreatePartnerSubmitButtonDisabled(),
				GlobalConstants.isCreatePartnerSubmitButtonDisabled);
	}
}