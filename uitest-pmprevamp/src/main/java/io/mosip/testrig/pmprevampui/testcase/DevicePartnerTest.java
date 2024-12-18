
package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.PartnerCertificatePage;
import io.mosip.testrig.pmprevampui.pages.RegisterPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class DevicePartnerTest extends BaseClass {

	@Test(priority = 1,description = "This is a test case register new device user")
	public void registerNewUser() throws InterruptedException {
		DashboardPage dashboardpage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		RegisterPage registerPage = new RegisterPage(driver);
		
		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(), GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardpage.clickOnCheckbox();

		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
		dashboardpage.clickOnProceedButton();
		
		dashboardpage.clickOnRootOFTrustCertText();
		dashboardpage.clickOnRootCertificateUploadButton();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnpartnerpartnerDomainSelectorDropdownOptionDevice();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.ClickonSubmitButtonForAdmin();
		partnerCertificatePage.ClickOnGoBackButton();
		dashboardpage.clickOnRootCertificateUploadButton();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnpartnerpartnerDomainSelectorDropdownOptionDevice();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.ClickonSubmitButtonForAdmin();
		partnerCertificatePage.ClickOnGoBackButton();
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		assertTrue(loginpage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		
		loginpage.clickRegisterButton();
		
		registerPage.enterFirstName("pmpui-device");
		assertTrue(registerPage.isLastNameTextBoxDisplayed(), GlobalConstants.isLastNameTextBoxDisplayed);
		registerPage.enterLastName("pmpui-device");

		assertTrue(registerPage.isOrganizationNameDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
		registerPage.enterOrganizationName("AABBCC");

		assertTrue(registerPage.isPartnerTypeDropdownDisplayed(), GlobalConstants.isPartnerTypeDropdownDisplayed);
		registerPage.selectDeviceProviderInPartnerTypeDropdown();

		assertTrue(registerPage.isAddressTextBoxDisplayed(), GlobalConstants.isAddressTextBoxDisplayed);
		registerPage.enterAddress("0"+ data + "device");

		assertTrue(registerPage.isEmailTextBoxDisplayed(), GlobalConstants.isEmailTextBoxDisplayed);
		registerPage.enterEmail("0"+ data + "device" + "@gmail.com");

		assertTrue(registerPage.isPhoneNumberTextboxDisplayed(), GlobalConstants.isPhoneNumberTextboxDisplayed);
		registerPage.enterPhone("9876543010");

		assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(), GlobalConstants.isNotificationLanguageDropdownDisplayed);
		registerPage.selectNotificationLanguageDropdown();

		assertTrue(registerPage.isUsernameTextBoxDisplayed(), GlobalConstants.isUsernameTextBoxDisplayed);
		registerPage.enterUsername("pmpui-device");

		assertTrue(registerPage.isPasswordTextBoxDisplayed(), GlobalConstants.isPasswordTextBoxDisplayed);
		registerPage.enterPassword("mosip123");

		assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(), GlobalConstants.isPasswordConfirmTextBoxDisplayed);
		registerPage.enterPasswordConfirm("mosip123");

		assertTrue(registerPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage = registerPage.clickSubmitButton();

		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(), GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardpage.clickOnCheckbox();

		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
		dashboardpage.clickOnProceedButton();
		
		Thread.sleep(3000);
		assertTrue(dashboardpage.isPartnerCertificateTitleDisplayed(), GlobalConstants.isPartnerCertificateTitleDisplayed);
		
		dashboardpage.clickOnPartnerCertificateTitle();

 		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(), GlobalConstants.isPartnerCertificatePageDisplayed);
 		partnerCertificatePage.clickOnUploadButton();

		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(), GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();

		assertTrue(partnerCertificatePage.isDeviceProviderSuccessMessageDisplayed(), GlobalConstants.isDeviceProviderSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		dashboardpage = partnerCertificatePage.clickOnHomeButton();

		dashboardpage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();
		
		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(), GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
//		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateSubTextDisplayed(), GlobalConstants.isReUploadPartnerCertificateSubTextDisplayed);
		
		assertTrue(partnerCertificatePage.isPartnerDomainTypeDisplayed(), GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		assertTrue(partnerCertificatePage.isPartnerCertOvelayDisplayed(), GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isDeviceProviderSuccessMessageDisplayed(), GlobalConstants.isDeviceProviderSuccessMessageDisplayed);
		partnerCertificatePage.clickOnRemoveCertificateButton();
		
		partnerCertificatePage.uploadCertificateInvalidCert();
		assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(), GlobalConstants.isInvalidCertFormatePopupDisplayed);
		
		partnerCertificatePage.clickOnCertificateUploadCancelButton();
		
		partnerCertificatePage.clickOnDownloadButton();
		partnerCertificatePage.clickOnOriginalCertificateDownloadButton();
		assertTrue(partnerCertificatePage.isOriginalSignedCertDownloadedPopupDisplayed(), GlobalConstants.isOriginalCertificateDownloadPopupDisplayed);
		
		partnerCertificatePage.clickOnMosipSignedCertificateDownloadButton();
		assertTrue(partnerCertificatePage.isMosipSignedCertPopupDisplayed(), GlobalConstants.isMosipCertificateDownloadPopupDisplayed);

//		assertTrue(partnerCertificatePage.isSucessMessageDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.ClickOnsuccessMsgCloseButton();
	}
	
}
