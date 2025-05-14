package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.OldPmpPage;
import io.mosip.testrig.pmprevampui.pages.PoliciesPage;
import io.mosip.testrig.pmprevampui.pages.RegisterPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class NewUserPolicy extends BaseClass {
		
	@Test
	public void RequestNewPolicyWithouUploadingCertificates() {

		DashboardPage dashboardpage = new DashboardPage(driver);

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		assertTrue(loginpage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);

		RegisterPage registerPage = loginpage.clickRegisterButton();
		assertTrue(registerPage.isRegisterPageTitleDisplayed(), GlobalConstants.isRegisterPageTitleDisplayed);
		assertTrue(registerPage.isFirstNameTextBoxDisplayed(), GlobalConstants.isFirstNameTextBoxDisplayed);
		// old pmp
		registerPage.openNewTab();
		OldPmpPage oldPmpPage = new OldPmpPage(driver);
		
		oldPmpPage.refreshPage();
		oldPmpPage.EnterUserName(userid);
		oldPmpPage.EntePasswordTextBox(password);
		oldPmpPage.clickOnLoginButton();
		
		oldPmpPage.clickOnPolicyMenuGroup();
		oldPmpPage.clickOnPolicyMenuGroupTab();
		
		oldPmpPage.clickOnCreatePolicyGroup();
		
		oldPmpPage.EnterNameTextBox("0"+data+"n");
		oldPmpPage.EnterDescriptionTextBox("0"+data+"n");

		oldPmpPage.clickOnCreateButton();
		oldPmpPage.clickOnConfirmMessagePopup();
		
		oldPmpPage.clickOnAuthPolicyTab();
		oldPmpPage.clickOnCreatePolicyTab();
		oldPmpPage.EnterNameTextBox("0"+data+"n");
 		oldPmpPage.EnterDescriptionTextBox("0"+data+"n");
		oldPmpPage.policyGroupNameDropDown("0"+data+"n");
		oldPmpPage.EnterPoliciesTextBox(ConfigManager.getpolicyData());
		oldPmpPage.clickOnCreateButton();
		oldPmpPage.clickOnConfirmMessagePopup();

		oldPmpPage.clickOnfilterButton();
		oldPmpPage.EnterNameTextBox("0"+data+"n");
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
		
		registerPage.enterFirstName("0"+data+"n");

		assertTrue(registerPage.isLastNameTextBoxDisplayed(), GlobalConstants.isLastNameTextBoxDisplayed);
		registerPage.enterLastName("0"+ data+"n");

		assertTrue(registerPage.isOrganizationNameDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
		registerPage.enterOrganizationName("xyz");

		assertTrue(registerPage.isPartnerTypeDropdownDisplayed(), GlobalConstants.isPartnerTypeDropdownDisplayed);
		registerPage.selectPartnerTypeDropdown(2);

		assertTrue(registerPage.isAddressTextBoxDisplayed(), GlobalConstants.isAddressTextBoxDisplayed);
		registerPage.enterAddress("0"+ data+"n");

		assertTrue(registerPage.isEmailTextBoxDisplayed(), GlobalConstants.isEmailTextBoxDisplayed);
		registerPage.enterEmail("0"+data+"n"+"@gmail.com");

		assertTrue(registerPage.isPhoneNumberTextboxDisplayed(), GlobalConstants.isPhoneNumberTextboxDisplayed);
		registerPage.enterPhone("9876543211");

		assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(), GlobalConstants.isNotificationLanguageDropdownDisplayed);
		registerPage.selectNotificationLanguageDropdown();

		assertTrue(registerPage.isUsernameTextBoxDisplayed(), GlobalConstants.isUsernameTextBoxDisplayed);
		registerPage.enterUsername("0"+ data+"n");

		assertTrue(registerPage.isPasswordTextBoxDisplayed(), GlobalConstants.isPasswordTextBoxDisplayed);
		registerPage.enterPassword("mosip123");

		assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(), GlobalConstants.isPasswordConfirmTextBoxDisplayed);
		registerPage.enterPasswordConfirm("mosip123");

		assertTrue(registerPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage = registerPage.clickSubmitButton();

		assertTrue(dashboardpage.isSelectPolicyGroupPopUpDisplayed(), GlobalConstants.isSelectPolicyGroupPopUpDisplayed);
		dashboardpage.selectPolicyGroupDropdown("0"+ data+"n");

		assertTrue(dashboardpage.isSubmitButtonSelectPolicyGroupPopUpDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage.clickOnSubmitButton();

		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(), GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardpage.clickOnCheckbox();

		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
		dashboardpage.clickOnProceedButton();

		assertTrue(dashboardpage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		PoliciesPage policiesPage=dashboardpage.clickOnPoliciesTitle();

		assertTrue(policiesPage.isPoliciesPageDisplayed(), GlobalConstants.isPoliciesPageDisplayed);
		policiesPage.clickOnRequestPolicyButton();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.clickOnPartnerIdDropdown();
		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataTextDisplaed);
	}
}
