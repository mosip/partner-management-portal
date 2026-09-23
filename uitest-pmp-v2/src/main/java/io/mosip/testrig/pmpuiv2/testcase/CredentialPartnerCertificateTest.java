package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.RegisterPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "CredentialPartnerCertificateTest" })
public class CredentialPartnerCertificateTest extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private PartnerCertificatePage partnerCertificatePage;
	private RegisterPage registerPage;

	@Test(priority = 1, description = "Registers the Credential Partner for certificate upload and re-upload scenarios.")
	public void registerCredentialPartnerForCertificateFlow() {

		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		loginPage = new LoginPage(driver);

		LogUtil.step("Upload Root CA and SubCA trust certificates for Auth domain");
		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(partnerCertificatePage.isUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.waitForAdminTrustCertificateReadyToSubmit();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackAfterAdminTrustCertificateSubmit();

		partnerCertificatePage.clickOnIntermediateCACertTab();
		assertTrue(partnerCertificatePage.isIntermediateUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.waitForAdminTrustCertificateReadyToSubmit();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackAfterAdminTrustCertificateSubmit();

		LogUtil.step("Register Credential Partner user");
		logoutFromPartner();
		registerPage = loginPage.clickRegisterButton();

		registerPage.enterFirstName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		registerPage.enterLastName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		registerPage.enterOrganizationName(GlobalConstants.ORGANISATION_NAME);
		registerPage.selectCredentialPartnerInPartnerTypeDropdown();
		registerPage.enterAddress("0" + data);
		registerPage.enterEmail("0" + data + "credential@gmail.com");
		registerPage.enterPhone(GlobalConstants.CREDENTIAL_PARTNER_PHONE);
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername(GlobalConstants.CREDENTIAL_PARTNER_ID);
		registerPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		registerPage.enterPasswordConfirm(GlobalConstants.PARTNER_PASSWORD);
		dashboardPage = registerPage.clickSubmitButton();

		dashboardPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		dashboardPage.clickOnSubmitButton();
		handleTermsAndCondition();

		// Wait for dashboard after consent before asserting cards (avoids post-proceed race).
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);

		LogUtil.step("Logout from Credential Partner before checking the certificate card");
		logoutFromPartner();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
	}

	@Test(priority = 2, description = "The Partner Certificate card is visible for a Credential Partner on the dashboard.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void partnerCertificateCardVisibleForCredentialPartner() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);

		LogUtil.step("Step 1: Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Step 2: Navigate to the dashboard");
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

		LogUtil.step("Step 3: Observe available certificate cards");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateCardVisibleForCredentialPartner);
	}

	@Test(priority = 3, description = "The Upload button is displayed for a first-time partner certificate.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void uploadButtonDisplayedForFirstTimeCertificate() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate page from dashboard");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		LogUtil.step("The Upload button is displayed for a first-time certificate upload");
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
	}

	@Test(priority = 4, description = "The Upload button is clickable and opens the certificate upload popup.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void uploadButtonClickableAndOpensCertificateUploadPopup() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate page from dashboard");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		LogUtil.step("The Upload button is clickable");
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		assertTrue(partnerCertificatePage.isUploadButtonEnabled(),
				GlobalConstants.isUploadButtonClickableForFirstTimeCertificate);

		LogUtil.step("Click the Upload button and confirm the certificate upload popup is displayed");
		partnerCertificatePage.clickOnUploadButton();
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(),
				GlobalConstants.isPleaseTabToSelectTextDisplayed);
		assertTrue(partnerCertificatePage.isPartnercertFormatesTextDisplayed(),
				GlobalConstants.isCertFormatesTextDisplayed);
	}

	@Test(priority = 5, description = "The popup title on the Upload action is Upload Partner Certificate.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void uploadPartnerCertificatePopupTitle() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate card and click Upload");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();

		LogUtil.step("The popup title is clearly shown as Upload Partner Certificate");
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		assertEquals(partnerCertificatePage.getUploadCertificatePopupTitle(),
				GlobalConstants.UPLOAD_PARTNER_CERTIFICATE_POPUP_TITLE,
				GlobalConstants.isUploadPartnerCertificatePopupTitleClearlyShown);
	}

	@Test(priority = 6, description = "The popup subtitle is shown below the Upload Partner Certificate title.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void uploadPartnerCertificatePopupSubtitle() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate card and click Upload");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();

		LogUtil.step("The popup title is displayed");
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		assertEquals(partnerCertificatePage.getUploadCertificatePopupTitle(),
				GlobalConstants.UPLOAD_PARTNER_CERTIFICATE_POPUP_TITLE,
				GlobalConstants.isUploadPartnerCertificatePopupTitleClearlyShown);

		LogUtil.step("The subtitle reads Please select all fields and upload the certificate and is shown below the title");
		assertTrue(partnerCertificatePage.isUploadCertificatePopupSubtitleDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupSubtitleDisplayed);
		assertEquals(partnerCertificatePage.getUploadCertificatePopupSubtitle(),
				GlobalConstants.UPLOAD_PARTNER_CERTIFICATE_POPUP_SUBTITLE,
				GlobalConstants.isUploadPartnerCertificatePopupSubtitleClearlyShown);
		assertTrue(partnerCertificatePage.isUploadPopupSubtitleDisplayedBelowTitle(),
				GlobalConstants.isUploadPartnerCertificatePopupSubtitleBelowTitle);
	}

	@Test(priority = 7, description = "The Partner Type Name is displayed as Credential Partner.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void partnerTypeNameDisplayedAsCredentialPartner() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate page and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();

		LogUtil.step("The Partner Type Name is displayed as Credential Partner");
		assertTrue(partnerCertificatePage.isPartnerTypeContextDisplayed(),
				GlobalConstants.isCredentialPartnerTypeNameDisplayed);
		assertEquals(partnerCertificatePage.getPartnerType(), GlobalConstants.CREDENTIAL_PARTNER_TYPE_NAME,
				GlobalConstants.isCredentialPartnerTypeNameDisplayed);
	}

	@Test(priority = 8, description = "The Partner Type Name field is non-editable.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void partnerTypeNameFieldIsNonEditable() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate page and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();

		LogUtil.step("The Partner Type Name field is non-editable and the value cannot be changed");
		assertTrue(partnerCertificatePage.isPartnerTypeContextDisplayed(),
				GlobalConstants.isPartnerTypeNameFieldNonEditable);
		assertTrue(partnerCertificatePage.isPartnerTypeFieldNonEditable(),
				GlobalConstants.isPartnerTypeNameFieldNonEditable);
	}

	@Test(priority = 9, description = "All fields and UI components are displayed in the Upload Partner Certificate popup.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void uploadPartnerCertificatePopupLayoutAndFields() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate page and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();

		LogUtil.step("The popup title is present");
		assertEquals(partnerCertificatePage.getUploadCertificatePopupTitle(),
				GlobalConstants.UPLOAD_PARTNER_CERTIFICATE_POPUP_TITLE,
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);

		LogUtil.step("The popup subtitle is present");
		assertEquals(partnerCertificatePage.getUploadCertificatePopupSubtitle(),
				GlobalConstants.UPLOAD_PARTNER_CERTIFICATE_POPUP_SUBTITLE,
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);

		LogUtil.step("The Partner Type Name field is present");
		assertTrue(partnerCertificatePage.isUploadPopupPartnerTypeLabelDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);
		assertEquals(partnerCertificatePage.getUploadPopupPartnerTypeLabelText(),
				GlobalConstants.UPLOAD_POPUP_PARTNER_TYPE_LABEL,
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);
		assertTrue(partnerCertificatePage.isPartnerTypeContextDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);
		assertTrue(partnerCertificatePage.isPartnerTypeContextDisabled(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);
		assertEquals(partnerCertificatePage.getPartnerType(), GlobalConstants.CREDENTIAL_PARTNER_TYPE_NAME,
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);

		LogUtil.step("The Partner Domain Type field is present");
		assertTrue(partnerCertificatePage.isUploadPopupPartnerDomainTypeLabelDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);
		assertEquals(partnerCertificatePage.getUploadPopupPartnerDomainTypeLabelText(),
				GlobalConstants.UPLOAD_POPUP_PARTNER_DOMAIN_TYPE_LABEL,
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainTypeContextDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainTypeContextDisabled(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);
		assertEquals(partnerCertificatePage.getPartnerDomainType(), GlobalConstants.PARTNER_DOMAIN_TYPE_AUTH,
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);

		LogUtil.step("The certificate upload section is present");
		assertTrue(partnerCertificatePage.isUploadCertificateCardDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);
		assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);
		assertTrue(partnerCertificatePage.isPartnercertFormatesTextDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);

		LogUtil.step("The Cancel button is present");
		assertTrue(partnerCertificatePage.isCertificateUploadCancelButtonDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);

		LogUtil.step("The Submit button is present");
		assertTrue(partnerCertificatePage.isCertificateUploadSubmitButtonDisabled(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);
	}

	@Test(priority = 10, description = "Only the Upload button is available when no partner certificate exists.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void onlyUploadButtonAvailableWhenNoCertificateExists() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate page from dashboard");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		LogUtil.step("Only the Upload button is available when no certificate exists");
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		assertFalse(partnerCertificatePage.isPartnerCertificateReuploadButtonPresent(),
				GlobalConstants.isOnlyUploadButtonAvailableWhenNoCertificateExists);
		assertFalse(partnerCertificatePage.isDownloadButtonPresent(),
				GlobalConstants.isOnlyUploadButtonAvailableWhenNoCertificateExists);
	}

	@Test(priority = 28, description = "Uploading a valid partner certificate is successful.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void validPartnerCertificateUploadIsSuccessful() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate page and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		openUploadOrReUploadPopup();
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);

		LogUtil.step("Select a valid certificate and submit upload");
		partnerCertificatePage.uploadCertificate();
		assertTrue(partnerCertificatePage.isUploadedCertificateFileNameLabelDisplayed(),
				GlobalConstants.isUploadedCertificateNameDisplayed);
		partnerCertificatePage.waitForPartnerCertificateReadyToSubmit();
		partnerCertificatePage.clickOnPartnerCertificateUploadSubmitButton();

		LogUtil.step("The certificate upload is successful");
		assertTrue(partnerCertificatePage.isPartnerCertificateUploadSuccessful(),
				GlobalConstants.isCredentialPartnerCertificateUploadedSuccessfully);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
	}

	@Test(priority = 29, description = "Re-upload with an invalid certificate format shows the correct error message.", dependsOnMethods = "validPartnerCertificateUploadIsSuccessful")
	public void reUploadInvalidCertificateShowsFormatError() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to the Partner Certificate page and confirm the Re-Upload button label");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isPartnerCertificateReuploadButtonDisplayed(),
				GlobalConstants.isReUploadButtonLabelDisplayedAfterCertificateExists);
		assertTrue(partnerCertificatePage.getPartnerCertificateReuploadButtonText()
				.contains(GlobalConstants.RE_UPLOAD_BUTTON_LABEL),
				GlobalConstants.isReUploadButtonLabelDisplayedAfterCertificateExists);

		LogUtil.step("Click Re-Upload and select an invalid certificate");
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();
		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		partnerCertificatePage.uploadCertificateInvalidCert();

		LogUtil.step("The upload fails with the invalid certificate format error message");
		assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(),
				GlobalConstants.isInvalidCertFormatePopupDisplayed);
		assertEquals(partnerCertificatePage.getInvalidFormatErrorMessage(),
				GlobalConstants.INVALID_CERTIFICATE_FORMAT_ERROR_MESSAGE,
				GlobalConstants.isInvalidCertificateFormatErrorMessageDisplayed);
	}

	@Test(priority = 30, description = "Re-upload with the same certificate file is successful.", dependsOnMethods = "validPartnerCertificateUploadIsSuccessful")
	public void reUploadWithSameCertificateFile() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Step 1: Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Step 2: Navigate to the Partner Certificate card");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isPartnerCertificateReuploadButtonDisplayed(),
				GlobalConstants.isReUploadButtonLabelDisplayedAfterCertificateExists);
		assertTrue(partnerCertificatePage.isDownloadButtonDisplayed(),
				GlobalConstants.isReUploadWithSameCertificateFileSuccessful);

		String uploadedDateBeforeReUpload = partnerCertificatePage.getCertificateUploadedDateInPartnerPortal();
		assertFalse(uploadedDateBeforeReUpload.isEmpty(),
				GlobalConstants.isCertificateDetailsDisplayedBeforeReUpload);

		LogUtil.step("Step 3: Click Re-Upload");
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();
		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		assertTrue(partnerCertificatePage.isReUploadCertificateWarningMessageDisplayed(),
				GlobalConstants.isReUploadCertificateWarningMessageDisplayed);
		assertEquals(partnerCertificatePage.getReUploadCertificateWarningMessage(),
				GlobalConstants.REUPLOAD_CERTIFICATE_WARNING_MESSAGE,
				GlobalConstants.isReUploadCertificateWarningMessageDisplayed);

		LogUtil.step("Step 4: Upload the same certificate file and submit");
		partnerCertificatePage.uploadCertificate();
		assertTrue(partnerCertificatePage.isUploadedCertificateFileNameLabelDisplayed(),
				GlobalConstants.isUploadedCertificateNameDisplayed);
		assertEquals(partnerCertificatePage.getUploadedCertificateFileName(),
				GlobalConstants.SAME_CERTIFICATE_FILE_NAME,
				GlobalConstants.isSameCertificateFileSelectedForReUpload);
		partnerCertificatePage.waitForPartnerCertificateReadyToSubmit();
		partnerCertificatePage.clickOnPartnerCertificateUploadSubmitButton();

		LogUtil.step("Replacement is allowed and the certificate details are refreshed");
		assertTrue(partnerCertificatePage.isPartnerCertificateUploadSuccessful(),
				GlobalConstants.isReUploadWithSameCertificateFileSuccessful);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
		assertTrue(partnerCertificatePage.isPartnerCertificateReuploadButtonDisplayed(),
				GlobalConstants.isReUploadWithSameCertificateFileSuccessful);
		assertTrue(partnerCertificatePage.isDownloadButtonDisplayed(),
				GlobalConstants.isCertificateDetailsRefreshedAfterSameFileReUpload);
		String uploadedDateAfterReUpload = partnerCertificatePage.getCertificateUploadedDateInPartnerPortal();
		assertFalse(uploadedDateAfterReUpload.isEmpty(),
				GlobalConstants.isCertificateDetailsRefreshedAfterSameFileReUpload);
		assertFalse(partnerCertificatePage.getCertificateExpiryDateInPartnerPortal().isEmpty(),
				GlobalConstants.isCertificateDetailsRefreshedAfterSameFileReUpload);
	}

	@Test(priority = 14, description = "The Partner Type Name in the popup matches the partner type shown in the list view.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void partnerTypeNameMatchesListView() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate list view and capture Partner Type Name");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isPartnerTypeFromListViewDisplayed(),
				GlobalConstants.isPartnerTypeNameMatchesListView);
		String partnerTypeFromListView = partnerCertificatePage.getPartnerTypeFromListView();

		LogUtil.step("Open the Upload popup and confirm the Partner Type Name matches the list view");
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		assertTrue(partnerCertificatePage.isPartnerTypeContextDisplayed(),
				GlobalConstants.isPartnerTypeNameMatchesListView);
		assertEquals(partnerCertificatePage.getPartnerType(), partnerTypeFromListView,
				GlobalConstants.isPartnerTypeNameMatchesListView);
	}

	@Test(priority = 15, description = "The Partner Domain Type field displays AUTH.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void partnerDomainTypePlaceholderText() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate card and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);

		LogUtil.step("The Partner Domain Type field displays AUTH");
		assertTrue(partnerCertificatePage.isPartnerDomainTypeContextDisplayed(),
				GlobalConstants.isPartnerDomainTypePlaceholderTextAuth);
		assertEquals(partnerCertificatePage.getPartnerDomainType(), GlobalConstants.PARTNER_DOMAIN_TYPE_AUTH,
				GlobalConstants.isPartnerDomainTypePlaceholderTextAuth);
	}

	@Test(priority = 16, description = "Partner Domain Type is a mandatory field.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void partnerDomainTypeIsMandatory() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate card and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);

		LogUtil.step("Partner Domain Type is mandatory: the label and value are present and cannot be cleared");
		assertTrue(partnerCertificatePage.isUploadPopupPartnerDomainTypeLabelDisplayed(),
				GlobalConstants.isPartnerDomainTypeMandatory);
		assertTrue(partnerCertificatePage.isPartnerDomainTypeContextDisplayed(),
				GlobalConstants.isPartnerDomainTypeMandatory);
		assertFalse(partnerCertificatePage.getPartnerDomainType().isEmpty(),
				GlobalConstants.isPartnerDomainTypeMandatory);
		assertTrue(partnerCertificatePage.isPartnerDomainTypeContextDisabled(),
				GlobalConstants.isPartnerDomainTypeMandatory);
	}

	@Test(priority = 17, description = "Partner Domain Type is populated from the configured domain types.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void partnerDomainTypePopulatedAsPerConfiguredDomainTypes() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate card and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);

		LogUtil.step("Partner Domain Type is auto-populated with the configured domain type for the Credential Partner");
		assertTrue(partnerCertificatePage.isPartnerDomainTypeContextDisplayed(),
				GlobalConstants.isPartnerDomainTypePopulatedAsPerConfiguredDomainTypes);
		assertTrue(partnerCertificatePage.isPartnerDomainTypeContextDisabled(),
				GlobalConstants.isPartnerDomainTypeAutoPopulated);
		String autoPopulatedDomainType = partnerCertificatePage.getPartnerDomainType();
		assertFalse(autoPopulatedDomainType.isEmpty(), GlobalConstants.isPartnerDomainTypeAutoPopulated);
		assertEquals(autoPopulatedDomainType, GlobalConstants.PARTNER_DOMAIN_TYPE_AUTH,
				GlobalConstants.isPartnerDomainTypePopulatedAsPerConfiguredDomainTypes);
	}

	@Test(priority = 18, description = "The certificate upload section shows the expected display text.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void displayTextInCertificateUploadSection() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate card and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);

		LogUtil.step("The certificate upload section shows the expected display text");
		assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(),
				GlobalConstants.isCertificateUploadSectionDisplayTextShown);
		assertTrue(partnerCertificatePage.isPartnercertFormatesTextDisplayed(),
				GlobalConstants.isCertificateUploadSectionDisplayTextShown);
		assertEquals(partnerCertificatePage.getUploadPopupSelectCertificateText(),
				GlobalConstants.CERTIFICATE_UPLOAD_SELECT_TEXT,
				GlobalConstants.isCertificateUploadSectionDisplayTextShown);
		assertEquals(partnerCertificatePage.getUploadPopupCertificateFormatText(),
				GlobalConstants.CERTIFICATE_UPLOAD_FORMAT_TEXT,
				GlobalConstants.isCertificateUploadSectionDisplayTextShown);
		assertEquals(partnerCertificatePage.getCertificateUploadSectionDisplayText(),
				GlobalConstants.CERTIFICATE_UPLOAD_SECTION_DISPLAY_TEXT,
				GlobalConstants.isCertificateUploadSectionDisplayTextShown);
	}

	@Test(priority = 19, description = "The upload icon is visible and enabled.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void uploadIconIsVisibleAndEnabled() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate card and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);

		LogUtil.step("The upload icon is visible");
		assertTrue(partnerCertificatePage.isUploadPopupCertificateIconDisplayed(),
				GlobalConstants.isUploadIconVisible);

		LogUtil.step("The upload icon is enabled and clickable");
		assertTrue(partnerCertificatePage.isUploadPopupCertificateIconEnabledAndClickable(),
				GlobalConstants.isUploadIconEnabledAndClickable);
	}

	@Test(priority = 20, description = "Clicking upload opens the local file browser and allows a certificate file to be selected.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void clickingUploadOpensFileBrowserAndAllowsFileSelection() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();

		LogUtil.step("Navigate to Partner Certificate card and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);

		LogUtil.step("The upload control is configured to open the local file browser");
		assertTrue(partnerCertificatePage.isUploadCertificateCardDisplayed(),
				GlobalConstants.isCertificateFileInputConfiguredForLocalFileBrowser);
		assertTrue(partnerCertificatePage.isCertificateFileInputConfiguredForLocalFileBrowser(),
				GlobalConstants.isCertificateFileInputConfiguredForLocalFileBrowser);
		partnerCertificatePage.clickOnUploadCertificateCard();

		LogUtil.step("A certificate file can be selected from the local file browser");
		partnerCertificatePage.uploadCertificate();
		assertTrue(partnerCertificatePage.isUploadedCertificateFileNameLabelDisplayed(),
				GlobalConstants.isUserAbleToSelectCertificateFile);
		assertEquals(partnerCertificatePage.getUploadedCertificateFileName(),
				GlobalConstants.SAME_CERTIFICATE_FILE_NAME,
				GlobalConstants.isUserAbleToSelectCertificateFile);
	}

	@Test(priority = 21, description = "The Cancel button closes the popup and returns to the certificate list.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void cancelButtonClosesPopupAndReturnsToCertificateListView() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

		LogUtil.step("Navigate to Partner Certificate list view and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isCertificateListViewDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		openUploadOrReUploadPopup();
		assertTrue(partnerCertificatePage.isCertificateUploadCancelButtonDisplayed(),
				GlobalConstants.isCancelButtonClosesUploadPopup);

		LogUtil.step("Click Cancel and confirm the popup is closed");
		partnerCertificatePage.clickOnCertificateUploadCancelButton();
		assertFalse(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayedQuick(),
				GlobalConstants.isCancelButtonClosesUploadPopup);

		LogUtil.step("The user is returned to the certificate list view");
		assertTrue(partnerCertificatePage.isCertificateListViewDisplayed(),
				GlobalConstants.isRedirectedToCertificateListViewAfterCancel);
		assertTrue(partnerCertificatePage.isUploadButtonPresent()
				|| partnerCertificatePage.isPartnerCertificateReuploadButtonPresent(),
				GlobalConstants.isRedirectedToCertificateListViewAfterCancel);
	}

	@Test(priority = 22, description = "No certificate changes are saved when Cancel is clicked.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void noChangesAreSavedOnCancel() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

		LogUtil.step("Navigate to Partner Certificate list view and capture state before Cancel");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isCertificateListViewDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonPresent(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		assertFalse(partnerCertificatePage.isPartnerCertificateReuploadButtonPresent(),
				GlobalConstants.isNoCertificateChangesPersistedOnCancel);
		assertFalse(partnerCertificatePage.isDownloadButtonPresent(),
				GlobalConstants.isNoCertificateChangesPersistedOnCancel);

		LogUtil.step("Select a certificate file in Upload popup, then click Cancel");
		partnerCertificatePage.clickOnUploadButton();
		assertTrue(partnerCertificatePage.isCertificateUploadCancelButtonDisplayed(),
				GlobalConstants.isCancelButtonClosesUploadPopup);
		partnerCertificatePage.uploadCertificate();
		assertTrue(partnerCertificatePage.isUploadedCertificateFileNameLabelDisplayed(),
				GlobalConstants.isUserAbleToSelectCertificateFile);
		assertEquals(partnerCertificatePage.getUploadedCertificateFileName(),
				GlobalConstants.SAME_CERTIFICATE_FILE_NAME,
				GlobalConstants.isUserAbleToSelectCertificateFile);
		partnerCertificatePage.clickOnCertificateUploadCancelButton();
		assertFalse(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayedQuick(),
				GlobalConstants.isCancelButtonClosesUploadPopup);

		LogUtil.step("No certificate changes are saved in the system");
		assertTrue(partnerCertificatePage.isCertificateListViewDisplayed(),
				GlobalConstants.isRedirectedToCertificateListViewAfterCancel);
		assertTrue(partnerCertificatePage.isUploadButtonPresent(),
				GlobalConstants.isNoCertificateChangesPersistedOnCancel);
		assertFalse(partnerCertificatePage.isPartnerCertificateReuploadButtonPresent(),
				GlobalConstants.isNoCertificateChangesPersistedOnCancel);
		assertFalse(partnerCertificatePage.isDownloadButtonPresent(),
				GlobalConstants.isNoCertificateChangesPersistedOnCancel);

		LogUtil.step("Re-open the Upload popup and confirm the selected file was not retained");
		partnerCertificatePage.clickOnUploadButton();
		assertTrue(partnerCertificatePage.isUploadCertificateCardDisplayed(),
				GlobalConstants.isNoCertificateChangesPersistedOnCancel);
		assertFalse(partnerCertificatePage.isUploadedCertificateFileNameLabelDisplayedQuick(),
				GlobalConstants.isNoCertificateChangesPersistedOnCancel);
	}

	@Test(priority = 23, description = "The Cancel button is clickable and responsive.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void cancelButtonIsClickableAndResponsive() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

		LogUtil.step("Navigate to Partner Certificate list view and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isCertificateListViewDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		openUploadOrReUploadPopup();

		LogUtil.step("The Cancel button is displayed, enabled, and clickable");
		assertTrue(partnerCertificatePage.isCertificateUploadCancelButtonDisplayed(),
				GlobalConstants.isCertificateUploadCancelButtonClickable);
		assertTrue(partnerCertificatePage.isCertificateUploadCancelButtonEnabled(),
				GlobalConstants.isCertificateUploadCancelButtonClickable);
		assertTrue(partnerCertificatePage.isCertificateUploadCancelButtonFocusable(),
				GlobalConstants.isCertificateUploadCancelButtonClickable);

		LogUtil.step("Click Cancel and confirm the button is responsive");
		partnerCertificatePage.clickOnCertificateUploadCancelButton();
		assertFalse(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayedQuick(),
				GlobalConstants.isCertificateUploadCancelButtonResponsive);
		assertTrue(partnerCertificatePage.isCertificateListViewDisplayed(),
				GlobalConstants.isCertificateUploadCancelButtonResponsive);
	}

	@Test(priority = 24, description = "The Submit button stays disabled while the certificate file is being fetched.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void submitButtonRemainsDisabledDuringFetchingCertificateFile() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

		LogUtil.step("Navigate to Partner Certificate list view and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isCertificateListViewDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		openUploadOrReUploadPopup();

		LogUtil.step("Select a certificate and confirm Submit stays disabled while the file is fetched");
		partnerCertificatePage.uploadCertificate();
		assertTrue(partnerCertificatePage.isCertificateUploadSubmitButtonDisabled(),
				GlobalConstants.isSubmitDisabledWhileFetchingCertificate);
		assertTrue(partnerCertificatePage.isCertificateUploadFetchingMsgDisplayed(),
				GlobalConstants.isCertificateUploadFetchingMsgDisplayed);
	}

	@Test(priority = 25, description = "The certificate name is displayed after a successful fetch.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void certificateNameDisplayedAfterSuccessfulFetch() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

		LogUtil.step("Navigate to Partner Certificate list view and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isCertificateListViewDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		openUploadOrReUploadPopup();

		LogUtil.step("Select certificate and wait for successful fetch");
		partnerCertificatePage.uploadCertificate();
		assertTrue(partnerCertificatePage.isFetchCertificateSuccessMessageDisplayed(),
				GlobalConstants.isCertificateNameDisplayedAfterSuccessfulFetch);
		assertEquals(partnerCertificatePage.getFetchCertificateSuccessMessage(),
				GlobalConstants.FETCH_CERTIFICATE_SUCCESS_MESSAGE,
				GlobalConstants.isCertificateNameDisplayedAfterSuccessfulFetch);

		LogUtil.step("The certificate name is displayed after a successful fetch");
		assertTrue(partnerCertificatePage.isRemoveCertificateCardDisplayed(),
				GlobalConstants.isCertificateNameDisplayedAfterSuccessfulFetch);
		assertTrue(partnerCertificatePage.isUploadedCertificateFileNameLabelDisplayed(),
				GlobalConstants.isCertificateNameDisplayedAfterSuccessfulFetch);
		assertEquals(partnerCertificatePage.getUploadedCertificateFileName(),
				GlobalConstants.SAME_CERTIFICATE_FILE_NAME,
				GlobalConstants.isCertificateNameDisplayedAfterSuccessfulFetch);
	}

	@Test(priority = 26, description = "A success message is displayed after the certificate is fetched.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void successMessageAfterCertificateFetch() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

		LogUtil.step("Navigate to Partner Certificate list view and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isCertificateListViewDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		openUploadOrReUploadPopup();

		LogUtil.step("Select a certificate and confirm the success message after fetch");
		partnerCertificatePage.uploadCertificate();
		assertTrue(partnerCertificatePage.isFetchCertificateSuccessMessageDisplayed(),
				GlobalConstants.isSuccessMessageDisplayedAfterCertificateFetch);
		assertEquals(partnerCertificatePage.getFetchCertificateSuccessMessage(),
				GlobalConstants.FETCH_CERTIFICATE_SUCCESS_MESSAGE,
				GlobalConstants.isSuccessMessageDisplayedAfterCertificateFetch);
	}

	@Test(priority = 27, description = "The Submit button is enabled after a successful fetch.", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void submitButtonIsEnabledAfterSuccessfulFetch() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButtonRetryingRejection();
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

		LogUtil.step("Navigate to Partner Certificate list view and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isCertificateListViewDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		openUploadOrReUploadPopup();

		LogUtil.step("Select certificate and wait for successful fetch");
		partnerCertificatePage.uploadCertificate();
		assertTrue(partnerCertificatePage.isFetchCertificateSuccessMessageDisplayed(),
				GlobalConstants.isSuccessMessageDisplayedAfterCertificateFetch);
		assertTrue(partnerCertificatePage.isUploadedCertificateFileNameLabelDisplayed(),
				GlobalConstants.isCertificateNameDisplayedAfterSuccessfulFetch);

		LogUtil.step("The Submit button is enabled after a successful fetch");
		assertTrue(partnerCertificatePage.isCertificateUploadSubmitButtonEnabled(),
				GlobalConstants.isSubmitEnabledAfterSuccessfulCertificateFetch);
	}

	private void openUploadOrReUploadPopup() {
		if (partnerCertificatePage.isUploadButtonPresent()) {
			partnerCertificatePage.clickOnUploadButton();
		} else {
			partnerCertificatePage.clickOnPartnerCertificateReuploadButton();
		}
	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayedQuick()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}

	private void logoutFromPartner() {
		dashboardPage.clickOnProfileDropdown();
		assertTrue(dashboardPage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);
		loginPage = dashboardPage.clickOnLogoutButton();
	}

}
