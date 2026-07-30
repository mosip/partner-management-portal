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

	@Test(priority = 2, description = "Register Credential Partner, select policy group, accept terms, verify dashboard, and logout", dependsOnMethods = "uploadCredentialRootCaSubCaTrustCertificates")
	public void registerCredentialPartnerUser() {

		LogUtil.step("Logout admin and open registration page for Credential Partner");
		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);

		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		registerPage = loginPage.clickRegisterButton();

		LogUtil.step("Fill registration form and submit as Credential Partner");
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
		dashboardPage = registerPage.clickSubmitButton();

		LogUtil.step("Select policy group and accept terms and conditions");
		dashboardPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		dashboardPage.clickOnSubmitButton();

		handleTermsAndCondition();

		LogUtil.step("Verify dashboard service cards are displayed after registration");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);

		LogUtil.step("Logout from Credential Partner profile menu");
		logoutFromPartner();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
	}

	@Test(priority = 3, description = "Login as Credential Partner, open Partner Certificate card, and upload partner certificate", dependsOnMethods = "registerCredentialPartnerUser")
	public void uploadPartnerCertificateAfterLogin() {
		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Logout admin session and login as registered Credential Partner");
		logoutFromPartner();

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
		assertTrue(partnerCertificatePage.isCertFormatesTextDisplayed(), GlobalConstants.isCertFormatesTextDisplayed);

		LogUtil.step("Upload partner certificate file and submit");
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isCredentialPartnerSuccessMessageDisplayed(),
				GlobalConstants.isCredentialPartnerSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
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
