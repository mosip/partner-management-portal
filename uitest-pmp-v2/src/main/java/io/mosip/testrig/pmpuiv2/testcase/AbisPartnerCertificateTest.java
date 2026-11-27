package io.mosip.testrig.pmpuiv2.testcase;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

/**
 * Automates MOSIP-43609 - Upload / Re-Upload ABIS Partner Certificate.
 *
 * ABIS partners use Partner Domain = AUTH. The certificate trust chain
 * (abisRootCA / abisIntermediateCA) is uploaded under the AUTH domain once at
 * the start so that the valid ABIS client certificates (signed by that chain)
 * are accepted on upload.
 */
@Test(dependsOnGroups = { "AbisPartnerTest" }, groups = { "AbisPartnerCertificateTest" })
public class AbisPartnerCertificateTest extends BaseClass {

	private DashboardPage dashboardPage;
	private MispPartnerPage mispPartnerPage;
	private PartnerCertificatePage partnerCertificatePage;
	private BasePage basePage;

	private void initPages() {
		dashboardPage = new DashboardPage(driver);
		mispPartnerPage = new MispPartnerPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		basePage = new BasePage(driver);
	}

	/** Uploads the ABIS Root CA and Intermediate CA under the AUTH partner domain. */
	private void uploadAbisTrustChainUnderAuthDomain() {
		dashboardPage.clickOnCertificateTrustStore();
		Assert.assertTrue(partnerCertificatePage.isUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadAbisRootCaCertificate();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		partnerCertificatePage.clickOnIntermediateCACertTab();
		Assert.assertTrue(partnerCertificatePage.isIntermediateUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadAbisIntermediateCaCertificate();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnHomeButton();
	}

	/** Creates an ABIS partner with the organization name that matches the test certificates. */
	private void createAbisPartner(String userName, String emailId) {
		dashboardPage.clickOnPartners();
		mispPartnerPage.clickOnCreatePartnerButton();
		mispPartnerPage.clickOnPartnerTypeDropdown();
		mispPartnerPage.clickOnAbisPartnerOption();
		mispPartnerPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		mispPartnerPage.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
		mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
		mispPartnerPage.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
		mispPartnerPage.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
		mispPartnerPage.enterEmailId(emailId);
		mispPartnerPage.enterUserName(userName);
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
		Assert.assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isAbisPartnerCreatedSuccessfully);
	}

	/** Opens the certificate upload / re-upload popup for a partner via the action menu. */
	private void openUploadPopupFromActionMenu(String partnerId) {
		mispPartnerPage.clickActionButtonByPartnerId(partnerId);
		mispPartnerPage.clickOnUploadOrReuploadCertificateButton();
	}

	@Test(priority = 1,
	      description = "Upload a valid ABIS partner certificate (.cer from success screen, .pem from action menu) and verify the partner becomes Active / Uploaded. (TC 01,02,03,12,35)")
	public void uploadCertificateHappyPath() {
		initPages();
		uploadAbisTrustChainUnderAuthDomain();

		// Path A - upload valid .cer from the create-partner success screen
		String userCer = "abiscrt1" + BaseClass.data;
		createAbisPartner(userCer, "abiscrt1" + BaseClass.data + "@test.com");
		Assert.assertTrue(mispPartnerPage.isUploadPartnerCertificateButtonDisplayed(),
				GlobalConstants.isUploadCertButtonOnSuccessScreenDisplayed);
		mispPartnerPage.clickOnUploadPartnerCertificateButton();
		Assert.assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadAbisValidClientCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertTrue(partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed(),
				GlobalConstants.isAbisCertUploadedFromSuccessScreen);
		partnerCertificatePage.clickOnCloseButton();
		basePage.scrollToStartPage();
		Assert.assertTrue(
				mispPartnerPage.isMispPartnerRowStatusDisplayed(userCer, GlobalConstants.UPLOADED_STATUS,
						GlobalConstants.ACTIVE_STATUS),
				GlobalConstants.isStatusDisplayed);

		// Path B - upload valid .pem from the partners list action menu (Not Uploaded record)
		String userPem = "abiscrt2" + BaseClass.data;
		createAbisPartner(userPem, "abiscrt2" + BaseClass.data + "@test.com");
		mispPartnerPage.clickOnSuccessMsgHomeButton();
		dashboardPage.clickOnPartners();
		basePage.scrollToStartPage();
		openUploadPopupFromActionMenu(userPem);
		Assert.assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadAbisValidClientPemCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertTrue(partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed(),
				GlobalConstants.isAbisCertUploadedViaActionMenu);
		partnerCertificatePage.clickOnCloseButton();
		basePage.scrollToStartPage();
		Assert.assertTrue(
				mispPartnerPage.isMispPartnerRowStatusDisplayed(userPem, GlobalConstants.UPLOADED_STATUS,
						GlobalConstants.ACTIVE_STATUS),
				GlobalConstants.isStatusDisplayed);
	}

	@Test(priority = 2,
	      description = "Verify ABIS partner certificate upload validation: wrong format, different org, expired, validity < 1 year, non-Version-3, CA/SubCA not uploaded, and self-signed are each rejected. (TC 04,05,06,07,31,32,34,36,37)")
	public void uploadCertificateNegativeValidation() {
		initPages();

		String user = "abisneg" + BaseClass.data;
		createAbisPartner(user, "abisneg" + BaseClass.data + "@test.com");
		mispPartnerPage.clickOnSuccessMsgHomeButton();
		dashboardPage.clickOnPartners();
		basePage.scrollToStartPage();

		// Wrong format (not .cer / .pem) - rejected on selection
		openUploadPopupFromActionMenu(user);
		partnerCertificatePage.uploadCertificateInvalidCert();
		Assert.assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(),
				GlobalConstants.isInvalidCertFormatePopupDisplayed);
		partnerCertificatePage.clickOnCertificateUploadCancelButton();

		// Different organization than the partner
		openUploadPopupFromActionMenu(user);
		partnerCertificatePage.uploadAbisDifferentOrgCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(),
				GlobalConstants.isAbisDifferentOrgCertRejected);
		partnerCertificatePage.clickOnCertificateUploadCancelButton();

		// Expired certificate
		openUploadPopupFromActionMenu(user);
		partnerCertificatePage.uploadAbisExpiredCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertTrue(partnerCertificatePage.isCertificateExpiredErrorDisplayed(),
				GlobalConstants.isCertificateExpiredErrorDisplayed);
		partnerCertificatePage.clickOnCertificateUploadCancelButton();

		// Validity less than one year
		openUploadPopupFromActionMenu(user);
		partnerCertificatePage.uploadAbisLessThanOneYearCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(),
				GlobalConstants.isAbisLessThanOneYearCertRejected);
		partnerCertificatePage.clickOnCertificateUploadCancelButton();

		// Non X.509 Version 3 certificate
		openUploadPopupFromActionMenu(user);
		partnerCertificatePage.uploadAbisNonVersion3Certificate();
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(),
				GlobalConstants.isAbisNonVersion3CertRejected);
		partnerCertificatePage.clickOnCertificateUploadCancelButton();

		// CA / SubCA not present in the trust store
		openUploadPopupFromActionMenu(user);
		partnerCertificatePage.uploadAbisCaNotUploadedCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertTrue(partnerCertificatePage.isNoRootCertDisplayed(),
				GlobalConstants.isAbisCaNotUploadedCertRejected);
		partnerCertificatePage.clickOnCertificateUploadCancelButton();

		// Self-signed certificate
		openUploadPopupFromActionMenu(user);
		partnerCertificatePage.uploadAbisSelfSignedCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertTrue(partnerCertificatePage.isErrorCodeForSelfSignedCerDisplayed(),
				GlobalConstants.isAbisSelfSignedCertRejected);
		partnerCertificatePage.clickOnCertificateUploadCancelButton();
	}

	@Test(priority = 3,
	      description = "Verify ABIS partner certificate status in the partners tabular view: Not Uploaded => Inactive (in red), Uploaded => Active. (TC 08,09,10)")
	public void certificateStatusInTabularView() {
		initPages();

		String user = "abissts" + BaseClass.data;
		createAbisPartner(user, "abissts" + BaseClass.data + "@test.com");
		mispPartnerPage.clickOnSuccessMsgHomeButton();
		dashboardPage.clickOnPartners();
		basePage.scrollToStartPage();

		// Without a certificate the partner is Inactive / Not Uploaded (in red)
		Assert.assertTrue(
				mispPartnerPage.isMispPartnerRowStatusDisplayed(user, GlobalConstants.NOTUPLOADED_STATUS,
						GlobalConstants.INACTIVE_STATUS),
				GlobalConstants.isStatusDisplayed);
		Assert.assertTrue(mispPartnerPage.isCertStatusDisplayedInRed(user),
				GlobalConstants.isCertStatusNotUploadedInRed);

		// After uploading a valid certificate the partner becomes Active / Uploaded
		openUploadPopupFromActionMenu(user);
		partnerCertificatePage.uploadAbisValidClientCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertTrue(partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed(),
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		basePage.scrollToStartPage();
		Assert.assertTrue(
				mispPartnerPage.isMispPartnerRowStatusDisplayed(user, GlobalConstants.UPLOADED_STATUS,
						GlobalConstants.ACTIVE_STATUS),
				GlobalConstants.isStatusDisplayed);
	}

	@Test(priority = 4,
	      description = "Verify the Re-Upload Partner Certificate popup on an Active ABIS partner: title, subtitle (Partner ID), Partner Type = ABIS Partner, Domain = AUTH (disabled), upload icon and format hint, last-uploaded-on date present and non-editable, and Cancel returns to the partners list. (TC 13-23)")
	public void reUploadPopupUiVerification() {
		initPages();

		String user = "abisrup" + BaseClass.data;
		createAbisPartner(user, "abisrup" + BaseClass.data + "@test.com");
		mispPartnerPage.clickOnUploadPartnerCertificateButton();
		Assert.assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadAbisValidClientCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertTrue(partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed(),
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		basePage.scrollToStartPage();

		// Re-open via the action menu - now it is the Re-Upload popup
		openUploadPopupFromActionMenu(user);
		Assert.assertTrue(partnerCertificatePage.isMispPartnerCertificatePopupDisplayed(),
				GlobalConstants.isMispPartnerCertificatePopupDisplayed);
		Assert.assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(),
				GlobalConstants.isReUploadPartnerCertificateDisplayed);
		Assert.assertTrue(partnerCertificatePage.isCorrespondingPartnerIdDisplayed(),
				GlobalConstants.isCorrespondingPartnerIdDisplayed);
		Assert.assertEquals(partnerCertificatePage.getPartnerType(), GlobalConstants.ABIS_PARTNER,
				GlobalConstants.isAbisPartnerTypeInPopupCorrect);
		Assert.assertEquals(partnerCertificatePage.getPartnerDomainType(), GlobalConstants.ABIS_DOMAINTYPE,
				GlobalConstants.isAbisPartnerDomainAuthInPopup);
		Assert.assertTrue(partnerCertificatePage.isPartnerDomainTypeValueDisabled(),
				GlobalConstants.isPartnerDomainTypeValueDisabled);
		Assert.assertTrue(partnerCertificatePage.isUploadCertificateIconDisplayed(),
				GlobalConstants.isUploadCertificateIconDisplayed);
		Assert.assertTrue(partnerCertificatePage.isCertFormatesTextDisplayed(),
				GlobalConstants.isCertFormatesTextDisplayed);
		Assert.assertTrue(partnerCertificatePage.isLastUploadTimeAndDateTextDisplayed(),
				GlobalConstants.isLastUploadTimeAndDateTextDisplayed);
		Assert.assertTrue(partnerCertificatePage.isLastCertificateUploadDateDisplayed(),
				GlobalConstants.isLastUploadTimeAndDateTextDisplayed);
		Assert.assertTrue(partnerCertificatePage.isLastUploadDateNotEditable(),
				GlobalConstants.isLastUploadDateNotEditable);

		// Cancel returns to the partners list
		partnerCertificatePage.clickOnCertificateUploadCancelButton();
		Assert.assertTrue(mispPartnerPage.isListOfPartnersDisplayed(), GlobalConstants.isListOfPartnersDisplayed);
	}

	@Test(priority = 5,
	      description = "Re-upload happy path: replace an already-uploaded ABIS certificate with another valid certificate and confirm the certificate details remain non-editable after upload. (TC 11,38)")
	public void reUploadHappyPath() {
		initPages();

		String user = "abisrhp" + BaseClass.data;
		createAbisPartner(user, "abisrhp" + BaseClass.data + "@test.com");
		mispPartnerPage.clickOnUploadPartnerCertificateButton();
		Assert.assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadAbisValidClientCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertTrue(partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed(),
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		basePage.scrollToStartPage();

		// Re-upload with a second valid certificate
		openUploadPopupFromActionMenu(user);
		Assert.assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(),
				GlobalConstants.isReUploadPartnerCertificateDisplayed);
		Assert.assertTrue(partnerCertificatePage.isCertificateFormatTextNotEditable(),
				GlobalConstants.isCertificateFormatTextNotEditable);
		partnerCertificatePage.uploadAbisReuploadClientCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertTrue(partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed(),
				GlobalConstants.isAbisReUploadSuccess);
		partnerCertificatePage.clickOnCloseButton();
		basePage.scrollToStartPage();
		Assert.assertTrue(
				mispPartnerPage.isMispPartnerRowStatusDisplayed(user, GlobalConstants.UPLOADED_STATUS,
						GlobalConstants.ACTIVE_STATUS),
				GlobalConstants.isStatusDisplayed);
	}
}
