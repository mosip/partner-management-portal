package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "MispPartnerTest" })
public class MispPartnerTest extends BaseClass {
	private DashboardPage dashboardPage;
	private MispPartnerPage mispPartnerPage;
	private PartnerCertificatePage partnerCertificatePage;

	@Test(priority = 01, description = "This is a test case register new misp user")
	public void createMispPartner() throws InterruptedException {
		mispPartnerPage = new MispPartnerPage(driver);
		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(partnerCertificatePage.isUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionMisp();
		partnerCertificatePage.uploadCertificateMispRootCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		partnerCertificatePage.clickOnIntermediateCACertTab();
		assertTrue(partnerCertificatePage.isIntermediateUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionMisp();
		partnerCertificatePage.uploadCertificateMispSubCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnHomeButton();

		dashboardPage.clickOnPartners();
		mispPartnerPage.clickOnCreatePartnerButton();
		assertTrue(mispPartnerPage.isCreatePrtnerPageTitleDisplayed(),
				GlobalConstants.isCreatePrtnerPageTitleDisplayed);
		assertEquals(mispPartnerPage.getBreadcrumbTextOfCreatePartnerPage(),
				GlobalConstants.BREADCUMB_TEXT_OF_CREATE_PARTNER);
		assertTrue(mispPartnerPage.isCreatePartnerMandatoryFieldInfoDisplayed(),
				GlobalConstants.isCreatePartnerMandatoryFieldInfoDisplayed);
		assertTrue(mispPartnerPage.isDefaultMispPartnerDisplayed(), GlobalConstants.isDefaultMispPartnerDisplayed);
		mispPartnerPage.clickOnPartnerTypeDropdown();

		mispPartnerPage.clickOnPartnerOragnizationInfoButton();
		assertTrue(mispPartnerPage.isOrganizationNameInfoDisplayed(), GlobalConstants.isOrganizationNameInfoDisplayed);

		createMispPartner(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_PARTNER,
				GlobalConstants.DEFAULT_POLICYGROUP, GlobalConstants.MISP_NOTIFICATION_LANGUAGE,
				GlobalConstants.MISP_ADDRESS, GlobalConstants.ORGANISATION_NAME, GlobalConstants.MISP_CONTACT_NUMBER,
				GlobalConstants.MISP_EMAIL_ID);

		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
		assertTrue(mispPartnerPage.isUploadPartnerCertificateButtonDisplayed(),
				GlobalConstants.isUploadPartnerCertificateButtonDisplayed);
		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgHomeButtonDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgHomeButtonDisplayed);

		mispPartnerPage.clickOnUploadPartnerCertificateButton();
		assertTrue(partnerCertificatePage.isMispPartnerCertificatePopupDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.uploadCertificateMispClient();
		partnerCertificatePage.clickOnSubmitButton();

		assertTrue(partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed(),
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
	}

	@Test(priority = 02, description = "Create multiple misp partner with negative scenarios", dependsOnMethods = "createMispPartner")
	public void createMultipleMispPartner() throws InterruptedException {
		mispPartnerPage = new MispPartnerPage(driver);
		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		dashboardPage.clickOnPartners();
		mispPartnerPage.clickOnCreatePartnerButton();
		verifyEnteringSpecialCharacterInAllMandatoryField();
		mispPartnerPage.clickOnCreatePartnerClearButton();
		assertTrue(mispPartnerPage.isPartnerOrganizationPlaceholderDisplayed(),
				GlobalConstants.isPartnerOrganizationPlaceholderDisplayed);
		mispPartnerPage.enterInvalidPolicyGroup(GlobalConstants.RANDOM_DATA);
		assertTrue(mispPartnerPage.isNoPolicyGroupDisplayed(), GlobalConstants.isNoPolicyGroupDisplayed);

		mispPartnerPage.enterPartnerAddress(GlobalConstants.MISP_ADDRESS);
		mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ALPHANUMERIC);
		mispPartnerPage.enterPartnerContactNumber(GlobalConstants.INVALIDFORMAT_PHONENUMBER);
		mispPartnerPage.clickOnPartnerEmailIdTextBox();
		assertTrue(mispPartnerPage.isPartnerContactNumberNotAllowErrorDisplayed(),
				GlobalConstants.isPartnerContactNumberNotAllowErrorDisplayed);

		mispPartnerPage.enterPartnerContactNumber(GlobalConstants.VANITY_PHONENUMBER);
		mispPartnerPage.clickOnPartnerEmailIdTextBox();
		assertTrue(mispPartnerPage.isPartnerContactNumberNotAllowErrorDisplayed(),
				GlobalConstants.isPartnerContactNumberNotAllowErrorDisplayed);

		mispPartnerPage.enterUserName(GlobalConstants.UNDERSCORE_PREFIXED_USERNAME);
		mispPartnerPage.clickOnPartnerEmailIdTextBox();
		assertTrue(mispPartnerPage.isUsernameMustStartWithLetterErrorDisplayed(),
				GlobalConstants.isUsernameMustStartWithLetterErrorDisplayed);

		mispPartnerPage.clickOnCreatePartnerCancelButton();
		assertTrue(mispPartnerPage.isCancelConfirmationPopupDisplayed(),
				GlobalConstants.isCancelConfirmationPopupDisplayed);

		mispPartnerPage.clickOnCancelConfirmationPopupProceedButton();
		assertTrue(mispPartnerPage.isListOfPartnersDisplayed(), GlobalConstants.isListOfPartnersDisplayed);

		mispPartnerPage.clickOnCreatePartnerButton();
		createMispPartner(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_PARTNER,
				GlobalConstants.DEFAULT_POLICYGROUP, GlobalConstants.MISP_NOTIFICATION_LANGUAGE,
				GlobalConstants.LENGTHY_STRING, GlobalConstants.ALPHANUMERIC, GlobalConstants.LEADINGZERO_PHONENUMBER,
				GlobalConstants.MISP_EMAIL_ID);
		assertTrue(mispPartnerPage.isEmailAddressIsAlreadyRegisteredErrorDisplayed(),
				GlobalConstants.isEmailAddressIsAlreadyRegisteredErrorDisplayed);
		mispPartnerPage.clickOnErrorMessageCloseButton();
		mispPartnerPage.clickOnPartnerEmailIdCancelBtn();
		mispPartnerPage.enterEmailId(GlobalConstants.MISP_EMAIL_ID2);
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(mispPartnerPage.isUsernameAlreadyExistErrorDisplayed(),
				GlobalConstants.isUsernameAlreadyExistErrorDisplayed);
		mispPartnerPage.enterUserName(GlobalConstants.LENGTHY_STRING);
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
		mispPartnerPage.clickOnSuccessMsgHomeButton();

		dashboardPage.clickOnPartners();
		mispPartnerPage.clickOnCreatePartnerButton();
		createMispPartner(GlobalConstants.UNDERSCORE_STRING, GlobalConstants.MISP_PARTNER,
				GlobalConstants.DEFAULT_POLICYGROUP, GlobalConstants.MISP_NOTIFICATION_LANGUAGE,
				GlobalConstants.MULTILINE_STRING, GlobalConstants.ORGANISATION_NAME,
				GlobalConstants.MISP_CONTACT_NUMBER, GlobalConstants.MISP_EMAIL_ID3);
		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
		mispPartnerPage.clickOnSuccessMsgHomeButton();

		dashboardPage.clickOnPartners();
		mispPartnerPage.clickOnCreatePartnerButton();
		createMispPartner(GlobalConstants.ALPHANUMERIC, GlobalConstants.MISP_PARTNER,
				GlobalConstants.DEFAULT_POLICYGROUP, GlobalConstants.MISP_NOTIFICATION_LANGUAGE,
				GlobalConstants.ALPHANUMERIC, GlobalConstants.ORGANISATION_NAME, GlobalConstants.MISP_CONTACT_NUMBER,
				GlobalConstants.MISP_EMAIL_ID4);
		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
		mispPartnerPage.clickOnSuccessMsgHomeButton();
	}

	private void createMispPartner(String userName, String partnerType, String policyGroup, String notificaction,
			String address, String organisation, String contactNumber, String emailId) {
		mispPartnerPage.selectPartnerType(partnerType);
		mispPartnerPage.selectPolicyGroupDropdown(policyGroup);
		mispPartnerPage.selectNotificationLanguage(notificaction);
		mispPartnerPage.enterPartnerAddress(address);
		mispPartnerPage.enterPartnerOrganisation(organisation);
		mispPartnerPage.enterPartnerContactNumber(contactNumber);
		mispPartnerPage.enterEmailId(emailId);
		mispPartnerPage.enterUserName(userName);
		assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
	}

	private void verifyEnteringSpecialCharacterInAllMandatoryField() {

		mispPartnerPage.enterPartnerAddress(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(mispPartnerPage.isPartnerAddressSpecialChNotAllowErrorDisplayed(),
				GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
		mispPartnerPage.enterPartnerOrganisation(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(mispPartnerPage.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
				GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
		mispPartnerPage.enterPartnerContactNumber(GlobalConstants.SPECIAL_CHARACTERS);
		mispPartnerPage.enterEmailId(GlobalConstants.SPECIAL_CHARACTERS);
		mispPartnerPage.enterUserName(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(mispPartnerPage.isPartnerUserNameSpecialChNotAllowErrorDisplayed(),
				GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
		assertTrue(mispPartnerPage.isPartnerContactSpecialChNotAllowErrorDisplayed(),
				GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
		assertTrue(mispPartnerPage.isPartnerEmailIdSpecialChNotAllowErrorDisplayed(),
				GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
		assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
				GlobalConstants.isCreatePartnerSubmitButtonDisabled);

	}
}
