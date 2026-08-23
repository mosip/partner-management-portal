package io.mosip.testrig.pmpuiv2.testcase;

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
 * End-to-end Credential Partner onboarding flow: trust certificate setup,
 * registration, and partner certificate upload.
 * MOSIP-44515 TC_44515_03: registration without partner type defaults to Device Partner.
 */
@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "CredentialPartnerCreation" })
public class CredentialPartnerCreation extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private PartnerCertificatePage partnerCertificatePage;
	private RegisterPage registerPage;

	@Test(priority = 1, description = "Prerequisite: Upload Root CA and SubCA trust certificates for Auth domain")
	public void uploadCredentialRootCaSubCaTrustCertificates() {

		LogUtil.step("Open Certificate Trust Store and upload Root CA for Auth domain");
		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(partnerCertificatePage.isUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		LogUtil.step("Upload SubCA (Intermediate CA) trust certificate for Auth domain");
		partnerCertificatePage.clickOnIntermediateCACertTab();
		assertTrue(partnerCertificatePage.isIntermediateUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();
	}

	@Test(priority = 2, description = "MOSIP-44515 TC_44515_01 - Register Credential Partner (steps 1-4)", dependsOnMethods = "uploadCredentialRootCaSubCaTrustCertificates")
	public void registerCredentialPartnerUser() {

		LogUtil.step("Step 1: Navigate to New Registration page");
		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);

		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		registerPage = loginPage.clickRegisterButton();

		LogUtil.step("Step 2: Enter valid registration details for Credential Partner");
		registerPage.enterFirstName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		registerPage.enterLastName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		registerPage.enterOrganizationName(GlobalConstants.ORGANISATION_NAME);
		registerPage.selectCredentialPartnerInPartnerTypeDropdown();
		registerPage.enterAddress("0" + data);
		registerPage.enterEmail("0" + data + "credential" + "@gmail.com");
		registerPage.enterPhone("9876543211");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername(GlobalConstants.CREDENTIAL_PARTNER_ID);
		registerPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		registerPage.enterPasswordConfirm(GlobalConstants.PARTNER_PASSWORD);

		LogUtil.step("Step 3: Submit the registration form");
		dashboardPage = registerPage.clickSubmitButton();

		LogUtil.step("Select policy group and accept terms and conditions");
		dashboardPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		dashboardPage.clickOnSubmitButton();

		handleTermsAndCondition();

		LogUtil.step("Step 4: Verify user account is successfully created and dashboard is displayed");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);

		LogUtil.step("Logout from Credential Partner to prepare for TC_44515_01 login steps");
		logoutFromPartner();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
	}

	@Test(priority = 3, description = "Login as Credential Partner, open Partner Certificate card, and upload partner certificate", dependsOnMethods = "registerCredentialPartnerUser")
	public void uploadPartnerCertificateAfterLogin() {
		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Login as registered Credential Partner");
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Open Partner Certificate card and click Upload Partner Certificate");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();

		assertTrue(partnerCertificatePage.isPartnerCertificateUploadPageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.clickOnUploadButton();

		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(),
				GlobalConstants.isPleaseTabToSelectTextDisplayed);
		assertTrue(partnerCertificatePage.isPartnercertFormatesTextDisplayed(),
				GlobalConstants.isCertFormatesTextDisplayed);

		LogUtil.step("Upload partner certificate file and submit");
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isCredentialPartnerSuccessMessageDisplayed(),
				GlobalConstants.isCredentialPartnerSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
	}

	@Test(priority = 4, description = "MOSIP-44515 TC_44515_03 - Verify default Device Partner when partner type is not selected during registration", dependsOnMethods = "uploadCredentialRootCaSubCaTrustCertificates")
	public void registerWithoutPartnerTypeDefaultsToDevicePartner() {

		LogUtil.step("Step 1: Navigate to New Registration page");
		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		registerPage = new RegisterPage(driver);

		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		registerPage = loginPage.clickRegisterButton();
		assertTrue(registerPage.isRegisterPageTitleDisplayed(), GlobalConstants.isRegisterPageTitleDisplayed);

		LogUtil.step("Step 2: Enter valid registration data");
		registerPage.enterFirstName(GlobalConstants.CREDENTIAL_PARTNER_NO_TYPE_USER_ID);
		registerPage.enterLastName(GlobalConstants.CREDENTIAL_PARTNER_NO_TYPE_USER_ID);
		registerPage.enterOrganizationName(GlobalConstants.ORGANISATION_NAME);

		LogUtil.step("Step 3: Fill all other mandatory fields");
		registerPage.enterAddress("0" + data + "crednotype");
		registerPage.enterEmail("0" + data + "crednotype" + "@gmail.com");
		registerPage.enterPhone("9876543212");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername(GlobalConstants.CREDENTIAL_PARTNER_NO_TYPE_USER_ID);
		registerPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		registerPage.enterPasswordConfirm(GlobalConstants.PARTNER_PASSWORD);

		LogUtil.step("Step 4: Leave Partner Type field unselected");
		// Partner type dropdown is intentionally not selected; system assigns default Device Partner on submit.

		LogUtil.step("Step 5: Click on Register/Submit button");
		dashboardPage = registerPage.clickSubmitButton();
		handleTermsAndCondition();

		LogUtil.step("Verify registration succeeds with default Device Partner dashboard");
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
		assertTrue(dashboardPage.isDeviceProviderServicesTitleDisplayed(),
				GlobalConstants.isDeviceProviderServicesTitleDisplayed);
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isDevicePartnerRegistrationSuccessfulWithoutPartnerType);

		LogUtil.step("Logout from Device Partner created via default partner type assignment");
		logoutFromPartner();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayed()) {
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
