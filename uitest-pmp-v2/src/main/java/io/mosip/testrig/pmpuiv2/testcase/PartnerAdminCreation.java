package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.kernel.util.KeycloakUserManager;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.RegisterPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class PartnerAdminCreation extends BaseClass{
	
	private BasePage basePage;
	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private PartnerCertificatePage partnerCertificatePage;

	
	@Test(priority = 1, description = "Creating Partner Admin")
	public void partnerAdminCreation() {
    dashboardPage = new DashboardPage(driver);
	loginPage = new LoginPage(driver);
	
	RegisterPage registerPage = loginPage.clickRegisterButton();
	assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
	loginPage.clickRegisterButton();

	registerPage.enterFirstName("pmpui-v2");
	assertTrue(registerPage.isLastNameTextBoxDisplayed(), GlobalConstants.isLastNameTextBoxDisplayed);
	registerPage.enterLastName("pmpui-v2");

	assertTrue(registerPage.isOrganizationNameDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
	registerPage.enterOrganizationName("AABBCC");

	assertTrue(registerPage.isPartnerTypeDropdownDisplayed(), GlobalConstants.isPartnerTypeDropdownDisplayed);
	registerPage.selectPartnerTypeDropdown(2);

	assertTrue(registerPage.isAddressTextBoxDisplayed(), GlobalConstants.isAddressTextBoxDisplayed);
	registerPage.enterAddress("0" + data);

	assertTrue(registerPage.isEmailTextBoxDisplayed(), GlobalConstants.isEmailTextBoxDisplayed);
	registerPage.enterEmail("0" + data +"admin"+ "@gmail.com");

	assertTrue(registerPage.isPhoneNumberTextboxDisplayed(), GlobalConstants.isPhoneNumberTextboxDisplayed);
	registerPage.enterPhone("9876556789");

	assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(),
			GlobalConstants.isNotificationLanguageDropdownDisplayed);
	registerPage.selectNotificationLanguageDropdown();

	assertTrue(registerPage.isUsernameTextBoxDisplayed(), GlobalConstants.isUsernameTextBoxDisplayed);
	registerPage.enterUsername("pmpui-v2");

	assertTrue(registerPage.isPasswordTextBoxDisplayed(), GlobalConstants.isPasswordTextBoxDisplayed);
	registerPage.enterPassword("mosip123");

	assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(), GlobalConstants.isPasswordConfirmTextBoxDisplayed);
	registerPage.enterPasswordConfirm("mosip123");

	assertTrue(registerPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
	dashboardPage = registerPage.clickSubmitButton();
	
	KeycloakUserManager.assignRole("pmpui-v2", "PARTNER_ADMIN");
	
	dashboardPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
	assertTrue(dashboardPage.isSubmitButtonSelectPolicyGroupPopUpDisplayed(),
			GlobalConstants.isSubmitButtonDisplayed);
	dashboardPage.clickOnSubmitButton();

	assertTrue(dashboardPage.isTermsAndConditionsPopUppDisplayed(),
			GlobalConstants.isTermsAndConditionsPopUppDisplayed);
	dashboardPage.clickOnCheckbox();
	assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
	dashboardPage.clickOnProceedButton();

}
	
	@Test(priority = 2, description = "Uploading Trust Certificate")
	public void uploadTrustCertificate() {

		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		
		dashboardPage.clickOnCertificateTrustStore();
		dashboardPage.clickOnRootCertificateUploadButton();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnpartnerpartnerDomainSelectorDropdownOptionAuth();

		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.ClickonSubmitButtonForAdmin();
		assertTrue(partnerCertificatePage.isUploadRootCertificateFirstErrorMessageDisplayed(),
				GlobalConstants.isUploadRootCertificateFirstErrorMessageDisplayed);
		partnerCertificatePage.clickOnErrorCloseButton();
		partnerCertificatePage.clickOnRemoveCertificateButton();

		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.ClickonSubmitButtonForAdmin();
		assertTrue(partnerCertificatePage.isUploadedSuccessfullyMessageDisplayed(),
				GlobalConstants.isUploadedSuccessfullyMessageDisplayed);
		assertTrue(partnerCertificatePage.isSuccessIconDisplayed(), GlobalConstants.isSuccessIconDisplayed);
		partnerCertificatePage.ClickOnGoBackButton();

		dashboardPage.clickOnRootCertificateUploadButton();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnpartnerpartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.ClickonSubmitButtonForAdmin();
		partnerCertificatePage.ClickOnGoBackButton();
		dashboardPage.clickOnProfileDropdown();
		assertTrue(dashboardPage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);
		dashboardPage.clickOnLogoutButton();
	}
	
	
}
