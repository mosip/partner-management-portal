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

/**
 * Credential Partner – Upload / Re-Upload Partner Certificate.
 */
@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "CredentialPartnerCertificateTest" })
public class CredentialPartnerCertificateTest extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private PartnerCertificatePage partnerCertificatePage;
	private RegisterPage registerPage;

	@Test(priority = 1, description = "Prerequisite: Register Credential Partner for certificate upload/re-upload scenarios")
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
		partnerCertificatePage.clickOnGoBackButton();

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

		LogUtil.step("Logout from Credential Partner to prepare for certificate card verification");
		logoutFromPartner();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
	}

	@Test(priority = 2, description = "Verify Partner Certificate card is visible for Credential Partner on Dashboard", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyPartnerCertificateCardVisibleForCredentialPartner() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);

		LogUtil.step("Step 1: Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Step 2: Navigate to the dashboard");
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

		LogUtil.step("Step 3: Observe available certificate cards");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateCardVisibleForCredentialPartner);
	}

	@Test(priority = 3, description = "Verify Upload button is displayed for first-time partner certificate", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyUploadButtonDisplayedForFirstTimeCertificate() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Navigate to Partner Certificate page from dashboard");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		LogUtil.step("Verify Upload button is displayed for first-time certificate upload");
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
	}

	@Test(priority = 4, description = "Verify Upload button is clickable and opens the certificate upload popup", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyUploadButtonClickableAndOpensCertificateUploadPopup() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Navigate to Partner Certificate page from dashboard");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		LogUtil.step("Verify Upload button is clickable");
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		assertTrue(partnerCertificatePage.isUploadButtonEnabled(),
				GlobalConstants.isUploadButtonClickableForFirstTimeCertificate);

		LogUtil.step("Click Upload button and verify certificate upload popup is displayed");
		partnerCertificatePage.clickOnUploadButton();
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(),
				GlobalConstants.isPleaseTabToSelectTextDisplayed);
		assertTrue(partnerCertificatePage.isPartnercertFormatesTextDisplayed(),
				GlobalConstants.isCertFormatesTextDisplayed);
	}

	@Test(priority = 5, description = "Verify popup title on Upload action", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyUploadPartnerCertificatePopupTitle() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Navigate to Partner Certificate card and click Upload");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();

		LogUtil.step("Verify popup title is clearly shown as Upload Partner Certificate");
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		assertEquals(partnerCertificatePage.getUploadCertificatePopupTitle(),
				GlobalConstants.UPLOAD_PARTNER_CERTIFICATE_POPUP_TITLE,
				GlobalConstants.isUploadPartnerCertificatePopupTitleClearlyShown);
	}

	@Test(priority = 6, description = "Verify popup subtitle on Upload action", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyUploadPartnerCertificatePopupSubtitle() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Navigate to Partner Certificate card and click Upload");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();

		LogUtil.step("Verify popup title is displayed");
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		assertEquals(partnerCertificatePage.getUploadCertificatePopupTitle(),
				GlobalConstants.UPLOAD_PARTNER_CERTIFICATE_POPUP_TITLE,
				GlobalConstants.isUploadPartnerCertificatePopupTitleClearlyShown);

		LogUtil.step("Verify subtitle reads Please select all fields and upload the certificate clearly below the title");
		assertTrue(partnerCertificatePage.isUploadCertificatePopupSubtitleDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupSubtitleDisplayed);
		assertEquals(partnerCertificatePage.getUploadCertificatePopupSubtitle(),
				GlobalConstants.UPLOAD_PARTNER_CERTIFICATE_POPUP_SUBTITLE,
				GlobalConstants.isUploadPartnerCertificatePopupSubtitleClearlyShown);
		assertTrue(partnerCertificatePage.isUploadPopupSubtitleDisplayedBelowTitle(),
				GlobalConstants.isUploadPartnerCertificatePopupSubtitleBelowTitle);
	}

	@Test(priority = 7, description = "Verify Partner Type Name is displayed as Credential Partner", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyPartnerTypeNameDisplayedAsCredentialPartner() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Navigate to Partner Certificate page and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();

		LogUtil.step("Verify Partner Type Name is displayed as Credential Partner");
		assertTrue(partnerCertificatePage.isPartnerTypeContextDisplayed(),
				GlobalConstants.isCredentialPartnerTypeNameDisplayed);
		assertEquals(partnerCertificatePage.getPartnerType(), GlobalConstants.CREDENTIAL_PARTNER_TYPE_NAME,
				GlobalConstants.isCredentialPartnerTypeNameDisplayed);
	}

	@Test(priority = 8, description = "Verify Partner Type Name field is non-editable", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyPartnerTypeNameFieldIsNonEditable() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Navigate to Partner Certificate page and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();

		LogUtil.step("Verify Partner Type Name field is non-editable and value cannot be changed");
		assertTrue(partnerCertificatePage.isPartnerTypeContextDisplayed(),
				GlobalConstants.isPartnerTypeNameFieldNonEditable);
		assertTrue(partnerCertificatePage.isPartnerTypeFieldNonEditable(),
				GlobalConstants.isPartnerTypeNameFieldNonEditable);
	}

	@Test(priority = 15, description = "Verify Partner Type Name matches list view", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyPartnerTypeNameMatchesListView() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Navigate to Partner Certificate list view and capture Partner Type Name");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isPartnerTypeFromListViewDisplayed(),
				GlobalConstants.isPartnerTypeNameMatchesListView);
		String partnerTypeFromListView = partnerCertificatePage.getPartnerTypeFromListView();

		LogUtil.step("Open Upload popup and verify Partner Type Name matches list view");
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

	@Test(priority = 16, description = "Verify placeholder text for Partner Domain Type field", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyPartnerDomainTypePlaceholderText() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

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

		LogUtil.step("Verify Partner Domain Type field displays AUTH");
		assertTrue(partnerCertificatePage.isPartnerDomainTypeContextDisplayed(),
				GlobalConstants.isPartnerDomainTypePlaceholderTextAuth);
		assertEquals(partnerCertificatePage.getPartnerDomainType(), GlobalConstants.PARTNER_DOMAIN_TYPE_AUTH,
				GlobalConstants.isPartnerDomainTypePlaceholderTextAuth);
	}

	@Test(priority = 17, description = "Verify Partner Domain Type is a mandatory field", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyPartnerDomainTypeIsMandatory() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

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

		LogUtil.step("Verify Partner Domain Type is mandatory: label and value are present and cannot be cleared");
		assertTrue(partnerCertificatePage.isUploadPopupPartnerDomainTypeLabelDisplayed(),
				GlobalConstants.isPartnerDomainTypeMandatory);
		assertTrue(partnerCertificatePage.isPartnerDomainTypeContextDisplayed(),
				GlobalConstants.isPartnerDomainTypeMandatory);
		assertFalse(partnerCertificatePage.getPartnerDomainType().isEmpty(),
				GlobalConstants.isPartnerDomainTypeMandatory);
		assertTrue(partnerCertificatePage.isPartnerDomainTypeContextDisabled(),
				GlobalConstants.isPartnerDomainTypeMandatory);
	}

	@Test(priority = 18, description = "Verify values are populated as per configured domain types", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyPartnerDomainTypePopulatedAsPerConfiguredDomainTypes() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

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

		LogUtil.step("Verify Partner Domain Type is auto-populated with the configured domain type for Credential Partner");
		assertTrue(partnerCertificatePage.isPartnerDomainTypeContextDisplayed(),
				GlobalConstants.isPartnerDomainTypePopulatedAsPerConfiguredDomainTypes);
		assertTrue(partnerCertificatePage.isPartnerDomainTypeContextDisabled(),
				GlobalConstants.isPartnerDomainTypeAutoPopulated);
		String autoPopulatedDomainType = partnerCertificatePage.getPartnerDomainType();
		assertFalse(autoPopulatedDomainType.isEmpty(), GlobalConstants.isPartnerDomainTypeAutoPopulated);
		assertEquals(autoPopulatedDomainType, GlobalConstants.PARTNER_DOMAIN_TYPE_AUTH,
				GlobalConstants.isPartnerDomainTypePopulatedAsPerConfiguredDomainTypes);
	}

	@Test(priority = 19, description = "Verify display text in certificate upload section", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyDisplayTextInCertificateUploadSection() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

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

		LogUtil.step("Verify certificate upload section display text");
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

	@Test(priority = 9, description = "Verify all fields and UI components in Upload Partner Certificate popup", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyUploadPartnerCertificatePopupLayoutAndFields() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Navigate to Partner Certificate page and open Upload popup");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		partnerCertificatePage.clickOnUploadButton();

		LogUtil.step("Verify Title");
		assertEquals(partnerCertificatePage.getUploadCertificatePopupTitle(),
				GlobalConstants.UPLOAD_PARTNER_CERTIFICATE_POPUP_TITLE,
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);

		LogUtil.step("Verify Subtitle");
		assertEquals(partnerCertificatePage.getUploadCertificatePopupSubtitle(),
				GlobalConstants.UPLOAD_PARTNER_CERTIFICATE_POPUP_SUBTITLE,
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);

		LogUtil.step("Verify Partner Type Name");
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

		LogUtil.step("Verify Partner Domain Type");
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

		LogUtil.step("Verify Certificate Upload section");
		assertTrue(partnerCertificatePage.isUploadCertificateCardDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);
		assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);
		assertTrue(partnerCertificatePage.isPartnercertFormatesTextDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);

		LogUtil.step("Verify Cancel button");
		assertTrue(partnerCertificatePage.isCertificateUploadCancelButtonDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);

		LogUtil.step("Verify Submit button");
		assertTrue(partnerCertificatePage.isUploadPopupSubmitButtonDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopupLayoutDisplayed);
	}

	@Test(priority = 10, description = "Verify only Upload button is available when no partner certificate exists", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyOnlyUploadButtonAvailableWhenNoCertificateExists() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Navigate to Partner Certificate page from dashboard");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		LogUtil.step("Verify only Upload button is available when no certificate exists");
		assertTrue(partnerCertificatePage.isUploadButtonDisplayed(),
				GlobalConstants.isUploadButtonDisplayedForFirstTimeCertificate);
		assertFalse(partnerCertificatePage.isPartnerCertificateReuploadButtonPresent(),
				GlobalConstants.isOnlyUploadButtonAvailableWhenNoCertificateExists);
		assertFalse(partnerCertificatePage.isDownloadButtonPresent(),
				GlobalConstants.isOnlyUploadButtonAvailableWhenNoCertificateExists);
	}

	@Test(priority = 11, description = "Verify uploading a valid partner certificate is successful", dependsOnMethods = "registerCredentialPartnerForCertificateFlow")
	public void verifyValidPartnerCertificateUploadIsSuccessful() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Navigate to Partner Certificate page and open Upload popup");
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

		LogUtil.step("Select a valid certificate and submit upload");
		partnerCertificatePage.uploadCertificate();
		assertTrue(partnerCertificatePage.isUploadedCertificateNameDisplayed(),
				GlobalConstants.isUploadedCertificateNameDisplayed);
		partnerCertificatePage.clickOnSubmitButton();

		LogUtil.step("Verify certificate upload is successful");
		assertTrue(partnerCertificatePage.isSuccessMessageDisplayed(),
				GlobalConstants.isCredentialPartnerCertificateUploadedSuccessfully);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
	}

	@Test(priority = 12, description = "Verify Re-Upload with invalid certificate format shows the correct error message", dependsOnMethods = "verifyValidPartnerCertificateUploadIsSuccessful")
	public void verifyReUploadInvalidCertificateShowsFormatError() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login with valid Credential Partner credentials");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Navigate to Partner Certificate page and verify Re-Upload button label");
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

		LogUtil.step("Verify upload fails with invalid certificate format error message");
		assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(),
				GlobalConstants.isInvalidCertFormatePopupDisplayed);
		assertEquals(partnerCertificatePage.getInvalidFormatErrorMessage(),
				GlobalConstants.INVALID_CERTIFICATE_FORMAT_ERROR_MESSAGE,
				GlobalConstants.isInvalidCertificateFormatErrorMessageDisplayed);
	}

	/**
	 * MOSIP-44516: Verify Re-Upload with same certificate file.
	 * Expected: Allow replacement and refresh certificate details.
	 */
	@Test(priority = 14, description = "Verify Re-Upload with same certificate file", dependsOnMethods = "verifyValidPartnerCertificateUploadIsSuccessful")
	public void verifyReUploadWithSameCertificateFile() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Step 1: Login to the PMS Portal as Credential Partner");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

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
		assertTrue(partnerCertificatePage.isUploadedCertificateNameDisplayed(),
				GlobalConstants.isUploadedCertificateNameDisplayed);
		assertEquals(partnerCertificatePage.getUploadedCertificateFileName(),
				GlobalConstants.SAME_CERTIFICATE_FILE_NAME,
				GlobalConstants.isSameCertificateFileSelectedForReUpload);
		partnerCertificatePage.clickOnSubmitButton();

		LogUtil.step("Verify replacement is allowed and certificate details are refreshed");
		assertTrue(partnerCertificatePage.isSuccessMessageDisplayed(),
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
