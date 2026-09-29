package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.RegisterPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

/**
 * Registers the Credential Partner used by the Map Credential Type and policy
 * approval suites, and uploads its partner certificate so the partner is
 * Active and able to raise policy requests.
 *
 * Credential Partners resolve to the AUTH partner domain, so the AUTH trust
 * chain uploaded by {@code PolicyAdminAndPartnerCreation} already covers the
 * client certificate used here.
 */
@Test(dependsOnGroups = { "PolicyAdminAndPartnerCreation" }, groups = { "CredentialPartnerCreation" })
public class CredentialPartnerCreation extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private PartnerCertificatePage partnerCertificatePage;
	private RegisterPage registerPage;

	@Test(priority = 1, description = "Register a Credential Partner and upload its partner certificate")
	public void registerCredentialPartnerUser() {
		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		loginPage = new LoginPage(driver);

		logoutFromPartner();
		registerPage = loginPage.clickRegisterButton();

		registerPage.enterFirstName(GlobalConstants.CREDENTIAL_PARTNER_USER);
		registerPage.enterLastName(GlobalConstants.CREDENTIAL_PARTNER_USER);
		registerPage.enterOrganizationName(GlobalConstants.ORGANISATION_NAME);
		registerPage.selectPartnerTypeByValue(GlobalConstants.CREDENTIAL_PARTNER_TYPE_VALUE);
		registerPage.enterAddress(GlobalConstants.CREDENTIAL_PARTNER_ADDRESS + data);
		registerPage.enterEmail("0" + data + "credential" + "@gmail.com");
		registerPage.enterPhone(GlobalConstants.CREDENTIAL_PARTNER_CONTACT_NUMBER);
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername(GlobalConstants.CREDENTIAL_PARTNER_USER);
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
		partnerCertificatePage.uploadPolicyUserClientCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
		partnerCertificatePage.clickOnHomeButton();
	}

	private void logoutFromPartner() {
		dashboardPage.clickOnProfileDropdown();
		dashboardPage.clickOnLogoutButton();
	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayed()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}
}
