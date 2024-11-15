package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.OidcClientPage;
import io.mosip.testrig.pmprevampui.pages.OldPmpPage;
import io.mosip.testrig.pmprevampui.pages.PartnerCertificatePage;
import io.mosip.testrig.pmprevampui.pages.PoliciesPage;
import io.mosip.testrig.pmprevampui.pages.RegisterPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class RegisterNewUser extends BaseClass {

	@Test(priority = 1,description = "This is a test case register new user")
	public void registerNewUser() {
		DashboardPage dashboardpage = new DashboardPage(driver);

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		assertTrue(loginpage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);

		RegisterPage registerPage = loginpage.clickRegisterButton();
		assertTrue(registerPage.isRegisterPageTitleDisplayed(), GlobalConstants.isRegisterPageTitleDisplayed);
		assertTrue(registerPage.isFirstNameTextBoxDisplayed(), GlobalConstants.isFirstNameTextBoxDisplayed);
		
		registerPage.openNewTab();
		OldPmpPage oldPmpPage = new OldPmpPage(driver);
		
		oldPmpPage.refreshPage();
		oldPmpPage.EnterUserName(userid);
		oldPmpPage.EntePasswordTextBox(password);
		oldPmpPage.clickOnLoginButton();
		
		oldPmpPage.clickOnPolicyMenuGroup();
		oldPmpPage.clickOnPolicyMenuGroupTab();
		
		oldPmpPage.clickOnCreatePolicyGroup();
		
		
		oldPmpPage.EnterNameTextBox("0"+data);
		oldPmpPage.EnterDescriptionTextBox("0"+data);

		oldPmpPage.clickOnCreateButton();
		oldPmpPage.clickOnConfirmMessagePopup();
		
//		oldPmpPage.clickOnPolicyTab();
		oldPmpPage.clickOnAuthPolicyTab();
		oldPmpPage.clickOnCreatePolicyTab();
		oldPmpPage.EnterNameTextBox("0"+data);
 		oldPmpPage.EnterDescriptionTextBox("0"+data);
		oldPmpPage.policyGroupNameDropDown("0"+data);
		oldPmpPage.EnterPoliciesTextBox(ConfigManager.getpolicyData());
		oldPmpPage.clickOnCreateButton();
		oldPmpPage.clickOnConfirmMessagePopup();

		oldPmpPage.clickOnfilterButton();
		oldPmpPage.EnterNameTextBox("0"+data);
		oldPmpPage.clickOnApplyTextButton();

		oldPmpPage.clickOnPolicyGroupElipsis();
		oldPmpPage.clickOnActivateButton();
		oldPmpPage.clickOnYesButton();
		oldPmpPage.clickOnConfirmMessagePopup();

		oldPmpPage.clickOnUserProfile();
		oldPmpPage.clickOnLogOut();
		
		registerPage.CloseTheTab();
		registerPage.openPreviousTab();
		registerPage.refreshThePage();
		registerPage.openRevampInNewTab();
		loginpage.clickRegisterButton();
		
		registerPage.enterFirstName("0"+data);

		assertTrue(registerPage.isLastNameTextBoxDisplayed(), GlobalConstants.isLastNameTextBoxDisplayed);
		registerPage.enterLastName("0"+ data);

		assertTrue(registerPage.isOrganizationNameDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
		registerPage.enterOrganizationName("xyz");

		assertTrue(registerPage.isPartnerTypeDropdownDisplayed(), GlobalConstants.isPartnerTypeDropdownDisplayed);
		registerPage.selectPartnerTypeDropdown();

		assertTrue(registerPage.isAddressTextBoxDisplayed(), GlobalConstants.isAddressTextBoxDisplayed);
		registerPage.enterAddress("0"+ data);

		assertTrue(registerPage.isEmailTextBoxDisplayed(), GlobalConstants.isEmailTextBoxDisplayed);
		registerPage.enterEmail("0"+ data + "@gmail.com");

		assertTrue(registerPage.isPhoneNumberTextboxDisplayed(), GlobalConstants.isPhoneNumberTextboxDisplayed);
		registerPage.enterPhone("9876543210");

		assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(), GlobalConstants.isNotificationLanguageDropdownDisplayed);
		registerPage.selectNotificationLanguageDropdown();

		assertTrue(registerPage.isUsernameTextBoxDisplayed(), GlobalConstants.isUsernameTextBoxDisplayed);
		registerPage.enterUsername("0"+ data);

		assertTrue(registerPage.isPasswordTextBoxDisplayed(), GlobalConstants.isPasswordTextBoxDisplayed);
		registerPage.enterPassword("mosip123");

		assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(), GlobalConstants.isPasswordConfirmTextBoxDisplayed);
		registerPage.enterPasswordConfirm("mosip123");

		assertTrue(registerPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage = registerPage.clickSubmitButton();

		assertTrue(dashboardpage.isSelectPolicyGroupPopUpDisplayed(), GlobalConstants.isSelectPolicyGroupPopUpDisplayed);
		dashboardpage.selectSelectPolicyGroupDropdown("0"+ data);

		assertTrue(dashboardpage.isSubmitButtonSelectPolicyGroupPopUpDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage.clickOnSubmitButton();

		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(), GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardpage.clickOnCheckbox();

		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
		dashboardpage.clickOnProceedButton();

		assertTrue(dashboardpage.isPartnerCertificateTitleDisplayed(), GlobalConstants.isPartnerCertificateTitleDisplayed);
	}
	
	
	@Test(priority = 2, dependsOnMethods = {"registerNewUser"},description = "This is a test case reupload the cert and download again")
	public void ReuploadTheCertificatesAndVerifyDownloadAgain() {

		DashboardPage dashboardpage = new DashboardPage(driver);

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		assertTrue(loginpage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		
		RegisterPage registerPage =  new RegisterPage(driver);
		LoginPage loginPage =  new LoginPage(driver);
		loginPage.enterUserName("0"+data);
		loginPage.enterPassword(password);
		loginPage.ClickOnLoginButton();

		assertTrue(dashboardpage.isPartnerCertificateTitleDisplayed(), GlobalConstants.isPartnerCertificateTitleDisplayed);
		PartnerCertificatePage partnerCertificatePage=dashboardpage.clickOnPartnerCertificateTitle();

 		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(), GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.clickOnUploadButton();

		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(), GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();

		assertTrue(partnerCertificatePage.isSucessMessageDisplayed(), GlobalConstants.isSucessMessageDisplayed);
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
		assertTrue(partnerCertificatePage.isSucessMessageDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.clickOnRemoveCertificateButton();
		
		partnerCertificatePage.uploadCertificateInvalidCert();
		assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(), GlobalConstants.isInvalidCertFormatePopupDisplayed);
		
		partnerCertificatePage.clickOnCertificateUploadCancelButton();
		
		partnerCertificatePage.clickOnDownloadButton();
		partnerCertificatePage.clickOnOriginalCertificateDownloadButton();
		assertTrue(partnerCertificatePage.isOriginalSignedCertDownloadedPopupDisplayed(), GlobalConstants.isOriginalCertificateDownloadPopupDisplayed);
		
		partnerCertificatePage.clickOnMosipSignedCertificateDownloadButton();
		assertTrue(partnerCertificatePage.isMosipSignedCertPopupDisplayed(), GlobalConstants.isMosipCertificateDownloadPopupDisplayed);
	}	
}
