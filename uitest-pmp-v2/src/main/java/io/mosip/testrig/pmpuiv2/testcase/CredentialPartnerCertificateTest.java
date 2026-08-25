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

		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);

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
