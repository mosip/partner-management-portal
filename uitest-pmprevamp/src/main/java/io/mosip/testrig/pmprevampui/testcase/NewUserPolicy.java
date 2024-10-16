package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertFalse;
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

public class NewUserPolicy extends BaseClass {
	
	
	@Test
	public void VerifyingPolicyCreationAndFilter() {

		DashboardPage dashboardpage = new DashboardPage(driver);

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		assertTrue(loginpage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		
		LoginPage loginPage =  new LoginPage(driver);
		loginPage.enterUserName("0"+data);
		loginPage.enterPassword(password);
		loginPage.ClickOnLoginButton();
		
		assertTrue(dashboardpage.isPartnerCertificateTitleDisplayed(), GlobalConstants.isPartnerCertificateTitleDisplayed);
		PartnerCertificatePage partnerCertificatePage=dashboardpage.clickOnPartnerCertificateTitle();

 		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(), GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();

		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();

		assertTrue(partnerCertificatePage.isSucessMessageDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		dashboardpage = partnerCertificatePage.clickOnHomeButton();

		assertTrue(dashboardpage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		PoliciesPage policiesPage=dashboardpage.clickOnPoliciesTitle();
		policiesPage.clickOnRequestPolicyButton();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.selectPartnerIdDropdown("0"+ data);

		assertTrue(policiesPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		policiesPage.selectPolicyNameDropdown("0"+ data);
		policiesPage.enterComments("0"+ data);

		assertTrue(policiesPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		policiesPage.clickSubmitButton();

		assertTrue(policiesPage.isPolicySubmittedSuccessfullyDisplayed(), GlobalConstants.isSubmitButtonDisplayed);

		policiesPage.clickOnHomeButton();
		dashboardpage.clickOnPoliciesTitle();
		
		assertTrue(policiesPage.isListOfPolicyRequestedDisplayed(), GlobalConstants.isListOfPolicyRequestedTextDisplayed);
		assertTrue(policiesPage.isNextPageDisplayed(), GlobalConstants.isNextPageDisplayed);
		assertTrue(policiesPage.isPreviousPageDisplayed(), GlobalConstants.isPreviousPageDisplayed);
		assertTrue(policiesPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalDisplayed);
		
		policiesPage.clickOnElipcisButton();
		policiesPage.clickOnCardViewButton();
		assertTrue(policiesPage.isPartnerIdTextDisplayed(), GlobalConstants.isPartnerIdTextDisplayed);
		policiesPage.clickOnBackButton();
		assertTrue(policiesPage.isPartnerIdDescIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(policiesPage.isPartnerIdAscIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(policiesPage.isPartnerTypeDescIconDisplayed(), GlobalConstants.isPartnerTypeDescAscIcon);
		assertTrue(policiesPage.isPartnerTypeAscIconDisplayed(), GlobalConstants.isPartnerTypeDescAscIcon);
		assertTrue(policiesPage.isPolicyGroupNameDescIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(policiesPage.isPolicyGroupNameAscIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(policiesPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(policiesPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(policiesPage.isCreatedDateTimeDescISconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(policiesPage.isCreatedDateTimeAscIconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
		policiesPage.clickOnFilterButton();
		
		policiesPage.clickOnPolicyPartnerIdFilter("0"+ data);
		policiesPage.clickOnPolicyPartnerTypeFilter();
		policiesPage.clickOnPolicyGroupFilter("0"+ data);
		policiesPage.clickOnPolicyNameFilter("0"+ data);
		policiesPage.clickOnPolicyStatusFilter();
		policiesPage.clickOnFilterResetButton();
		policiesPage.isFilterButtonButtonEnabled();
		
		policiesPage.clickOnPolicyListItem1();
		assertTrue(policiesPage.isSubTitleHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(policiesPage.isSubTitleButtonDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(policiesPage.isTitleBackIconDisplayed(), GlobalConstants.isBackArrow);
		assertTrue(policiesPage.isViewPolicyDetailsTextDisplayed(), GlobalConstants.isViewPolicyTitle);

	}
	
	
	@Test
	public void VerifyingSelectingInvalidPolicyGroup() {

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
		
		oldPmpPage.EnterNameTextBox("0"+data+"0");
		oldPmpPage.EnterDescriptionTextBox("0"+data+"0");

		oldPmpPage.clickOnCreateButton();
		oldPmpPage.clickOnConfirmMessagePopup();
		
		oldPmpPage.clickOnAuthPolicyTab();
		oldPmpPage.clickOnCreatePolicyTab();
		oldPmpPage.EnterNameTextBox("0"+data+"0");
 		oldPmpPage.EnterDescriptionTextBox("0"+data+"0");
		oldPmpPage.policyGroupNameDropDown("0"+data+"0");
		oldPmpPage.EnterPoliciesTextBox(ConfigManager.getpolicyData());
		oldPmpPage.clickOnCreateButton();
		oldPmpPage.clickOnConfirmMessagePopup();

		oldPmpPage.clickOnfilterButton();
		oldPmpPage.EnterNameTextBox("0"+data+"0");
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
		
		registerPage.enterFirstName("0"+data+"0");
		assertTrue(registerPage.isLastNameTextBoxDisplayed(), GlobalConstants.isLastNameTextBoxDisplayed);
		registerPage.enterLastName("0"+ data+"0");

		assertTrue(registerPage.isOrganizationNameDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
		registerPage.enterOrganizationName("xyz");

		assertTrue(registerPage.isPartnerTypeDropdownDisplayed(), GlobalConstants.isPartnerTypeDropdownDisplayed);
		registerPage.selectPartnerTypeDropdown();

		assertTrue(registerPage.isAddressTextBoxDisplayed(), GlobalConstants.isAddressTextBoxDisplayed);
		registerPage.enterAddress("0"+ data+"0");

		assertTrue(registerPage.isEmailTextBoxDisplayed(), GlobalConstants.isEmailTextBoxDisplayed);
		registerPage.enterEmail("0"+ data+"0"+ "@gmail.com");

		assertTrue(registerPage.isPhoneNumberTextboxDisplayed(), GlobalConstants.isPhoneNumberTextboxDisplayed);
		registerPage.enterPhone("9876543212");

		assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(), GlobalConstants.isNotificationLanguageDropdownDisplayed);
		registerPage.selectNotificationLanguageDropdown();

		assertTrue(registerPage.isUsernameTextBoxDisplayed(), GlobalConstants.isUsernameTextBoxDisplayed);
		registerPage.enterUsername("0"+ data+"0");

		assertTrue(registerPage.isPasswordTextBoxDisplayed(), GlobalConstants.isPasswordTextBoxDisplayed);
		registerPage.enterPassword("mosip123");

		assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(), GlobalConstants.isPasswordConfirmTextBoxDisplayed);
		registerPage.enterPasswordConfirm("mosip123");

		assertTrue(registerPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage = registerPage.clickSubmitButton();
		
		assertTrue(dashboardpage.isSelectPolicyGroupPopUpDisplayed(), GlobalConstants.isSelectPolicyGroupPopUpDisplayed);
		assertTrue(dashboardpage.isSelectPolicyGroupViewMoreAndLess(), GlobalConstants.isSelectPolicyGroupViewMoreAndLess);
		assertTrue(dashboardpage.isSelectPolicyGroupSubmitEnabled(), GlobalConstants.isSelectPolicyGroupSubmitDisabled);
		dashboardpage.clickOnSelectPolicyGroupSubmit();
		dashboardpage.selectSelectPolicyGroupDropdownForInvalid("abc"+ data);
		assertTrue(dashboardpage.isNoDataAvailableTextDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		
		dashboardpage.clickOnSelectPolicyGroupLogout();
		
	}
	
	

	@Test
	public void searchWithInvalidPolicyName() {

		DashboardPage dashboardpage = new DashboardPage(driver);

		assertTrue(dashboardpage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		PoliciesPage policiesPage=dashboardpage.clickOnPoliciesTitle();

		assertTrue(policiesPage.isPoliciesPageDisplayed(), GlobalConstants.isPoliciesPageDisplayed);
		assertTrue(policiesPage.isPoliciesEmptyTableDisplayed(), GlobalConstants.isPolicyEmptyTableIsDisplayed);
		assertTrue(policiesPage.isPoliciesEmptyTableEnabled(), GlobalConstants.isRequestPolicyEnabled);
		policiesPage.clickOnRequestPolicyButton();
		
		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.selectPartnerIdDropdown("automationuiiii");

		assertTrue(policiesPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		policiesPage.selectInvalidPolicyNameDropdown(data+"123");
		policiesPage.searchInPolicyName(data +"123");
		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);

	}
	
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
		registerPage.selectPartnerTypeDropdown();

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
		dashboardpage.selectSelectPolicyGroupDropdown("0"+ data+"n");

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
