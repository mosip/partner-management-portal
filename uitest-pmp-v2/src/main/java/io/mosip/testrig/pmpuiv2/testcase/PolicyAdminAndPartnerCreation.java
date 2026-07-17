package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.kernel.util.KeycloakUserManager;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.RegisterPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "PolicyAdminAndPartnerCreation" })
public class PolicyAdminAndPartnerCreation extends BaseClass {
	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private PartnerCertificatePage partnerCertificatePage;
	private RegisterPage registerPage;

	@Test(priority = 1, description = "Creating Partner Admin")
	public void policiesAdminCreation() {
		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);

		logoutFromPartner();
		registerPage = loginPage.clickRegisterButton();

		registerPage.enterFirstName(GlobalConstants.POLICIES_ADMIN);
		registerPage.enterLastName(GlobalConstants.POLICIES_ADMIN);
		registerPage.enterOrganizationName(GlobalConstants.ORGANISATION_NAME);
		registerPage.selectPartnerTypeDropdown(1);
		registerPage.enterAddress("0" + data);
		registerPage.enterEmail("0" + data + "policyadmin" + "@gmail.com");
		registerPage.enterPhone("9876897289");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername(GlobalConstants.POLICIES_ADMIN);
		registerPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		registerPage.enterPasswordConfirm(GlobalConstants.PARTNER_PASSWORD);
		dashboardPage = registerPage.clickSubmitButton();

		KeycloakUserManager.assignRole(GlobalConstants.POLICIES_ADMIN, "PARTNER_ADMIN");

		handleTermsAndCondition();

	}

	@Test(priority = 2, description = "Uploading Trust Certificate", dependsOnMethods = "policiesAdminCreation")
	public void uploadTrustCertificate() {

		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		loginPage = new LoginPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(partnerCertificatePage.isUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionFtm();

		partnerCertificatePage.uploadPolicyAdminUserRootCaCert();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		assertTrue(partnerCertificatePage.isUploadedSuccessfullyMessageDisplayed(),
				GlobalConstants.isUploadedSuccessfullyMessageDisplayed);
		assertTrue(partnerCertificatePage.isSuccessIconDisplayed(), GlobalConstants.isSuccessIconDisplayed);
		partnerCertificatePage.clickOnGoBackButton();

		partnerCertificatePage.clickOnIntermediateCACertTab();
		assertTrue(partnerCertificatePage.isIntermediateUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionFtm();
		partnerCertificatePage.uploadPolicyAdminUserRootSubCaCert();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();
		dashboardPage.clickOnProfileDropdown();
		assertTrue(dashboardPage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);
		dashboardPage.clickOnLogoutButton();
	}

	@Test(priority = 3, description = "This is a test case register new user", dependsOnMethods = "uploadTrustCertificate")
	public void registerNewUser() {
		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		loginPage = new LoginPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(partnerCertificatePage.isUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadPolicyUserRootCaCert();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		partnerCertificatePage.clickOnIntermediateCACertTab();
		assertTrue(partnerCertificatePage.isIntermediateUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadPolicyUserIntermediateCaCert();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		logoutFromPartner();

		registerPage = loginPage.clickRegisterButton();

		registerPage.enterFirstName(GlobalConstants.POLICIES_USER_ID);
		registerPage.enterLastName(GlobalConstants.POLICIES_USER_ID);
		registerPage.enterOrganizationName(GlobalConstants.ORGANISATION_NAME);
		registerPage.selectPartnerTypeDropdown(2);
		registerPage.enterAddress("0" + data + "policies");
		registerPage.enterEmail("0" + data + "policies" + "@gmail.com");
		registerPage.enterPhone("8947349533");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername(GlobalConstants.POLICIES_USER_ID);
		registerPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		registerPage.enterPasswordConfirm(GlobalConstants.PARTNER_PASSWORD);
		dashboardPage = registerPage.clickSubmitButton();

		dashboardPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		dashboardPage.clickOnSubmitButton();

		handleTermsAndCondition();

		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();

		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.clickOnUploadButton();

		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(),
				GlobalConstants.isPleaseTabToSelectTextDisplayed);
		assertTrue(partnerCertificatePage.isPartnercertFormatesTextDisplayed(), GlobalConstants.isCertFormatesTextDisplayed);
		partnerCertificatePage.uploadPolicyUserClientCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
		dashboardPage = partnerCertificatePage.clickOnHomeButton();

	}

	private void logoutFromPartner() {
		dashboardPage.clickOnProfileDropdown();
		dashboardPage.clickOnLogoutButton();
	}

	private void loginAsPartnerAdmin() {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.POLICIES_ADMIN);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayed()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}

}
