package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
import io.mosip.testrig.pmprevampui.kernel.util.KeycloakUserManager;
import io.mosip.testrig.pmprevampui.pages.ApiKeyPage;
import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.OidcClientPage;
import io.mosip.testrig.pmprevampui.pages.OldPmpPage;
import io.mosip.testrig.pmprevampui.pages.PartnerCertificatePage;
import io.mosip.testrig.pmprevampui.pages.PoliciesPage;
import io.mosip.testrig.pmprevampui.pages.RegisterPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;
import io.mosip.testrig.pmprevampui.utility.TestRunner;

public class CreateOidecClient extends BaseClass {
	
	  @Test public void CreateOidecClient() {
	  
	  DashboardPage dashboardpage = new DashboardPage(driver); RegisterPage
	  registerPage = new RegisterPage(driver);
	  dashboardpage.clickOnProfileDropdown();
	  assertTrue(dashboardpage.isLogoutButtonDisplayed(),
	  GlobalConstants.isLogoutButtonDisplayed);
	  
	  LoginPage loginpage = dashboardpage.clickOnLogoutButton();
	  assertTrue(loginpage.isLoginPageDisplayed(),
	  GlobalConstants.isLoginPageDisplayed);
	  
	  registerPage.openNewTab(); OldPmpPage oldPmpPage = new OldPmpPage(driver);
	  
	  oldPmpPage.refreshPage(); oldPmpPage.EnterUserName(userid);
	  oldPmpPage.EntePasswordTextBox(password); oldPmpPage.clickOnLoginButton();
	  
	  oldPmpPage.clickOnPartnerPolicyMappingTab();
	  oldPmpPage.clickOnfilterButton(); oldPmpPage.EnterPartnerNameTextBox("xyz");
	  oldPmpPage.EnterRequestDetail("0"+data); oldPmpPage.clickOnApplyTextButton();
	  oldPmpPage.clickOnEllipsisButton(); oldPmpPage.clickOnManagePolicy();
	  
	  oldPmpPage.clickOnConfirmpopup(); oldPmpPage.clickOnConfirmMessagePopup();
	  
	  oldPmpPage.clickOnUserProfile(); oldPmpPage.clickOnLogOut();
	  
	  registerPage.CloseTheTab(); registerPage.openPreviousTab();
	  registerPage.refreshThePage(); registerPage.openRevampInNewTab();
	  
	  LoginPage loginPage = new LoginPage(driver);
	  loginPage.enterUserName("0"+data); loginPage.enterPassword(password);
	  loginPage.ClickOnLoginButton();
	  
	  assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(),
	  GlobalConstants.isAuthenticationServicesDisplayed); OidcClientPage
	  oidcClientPage=dashboardpage.clickOnAuthenticationServicesTitle();
	  
	  assertTrue(oidcClientPage.isCreateOidcClientDisplayed(),
	  GlobalConstants.isCreateOIDCClientDisplayed);
	  assertTrue(oidcClientPage.isPolicyGroupHeaderTextDisplayed(),
	  GlobalConstants.isPolicyGroupHeaderTextDisplayed);
	  assertTrue(oidcClientPage.isPolicyNameHeaderTextDisplayed(),
	  GlobalConstants.isPolicyNameHeaderTextDisplayed);
	  assertTrue(oidcClientPage.isOIDCClientNameHeaderTextDisplayed(),
	  GlobalConstants.isOIDCClientNameHeaderTextDisplayed);
	  assertTrue(oidcClientPage.isCreatedDateHeaderTextDisplayed(),
	  GlobalConstants.isCreatedDateHeaderTextDisplayed);
	  assertTrue(oidcClientPage.isStatusHeaderTextDisplayed(),
	  GlobalConstants.isStatusHeaderTextDisplayed);
	  assertTrue(oidcClientPage.isOIDCClientIDHeaderTextDisplayed(),
	  GlobalConstants.isOIDCClientIDHeaderTextDisplayed);
	  assertTrue(oidcClientPage.isActionHeaderTextDisplayed(),
	  GlobalConstants.isActionHeaderTextDisplayed);
	  oidcClientPage.clickOnCreateOidcClientButton();
	  
	  assertTrue(oidcClientPage.isPartnerIdDropdownDisplayed(),
	  GlobalConstants.isPartnerIdDropdownDisplayed);
	  oidcClientPage.selectPartnerIdDropdown("0"+data);
	  
	  assertTrue(oidcClientPage.isPolicyNameDropdownDisplayed(),
	  GlobalConstants.isPolicyNameDropdownDisplayed);
	  oidcClientPage.selectPolicyNameDropdown("0"+data);
	  
	  oidcClientPage.enterNameOidcTextBox("0"+data);
	  
	  String publicKey = ConfigManager.getrandomPublicKey() + "\"" +
	  generateRandomString(4) + "\"}";
	  
	  oidcClientPage.enterPublicKeyTextBox(publicKey);
	  oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
	  oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
	  oidcClientPage.clickOnSubmitButton();
	  assertTrue(oidcClientPage.isDetailsSubmittedSuccessFullyDisplayed(),
	  GlobalConstants.isAutherisationCodeTextDisplayed);
	  oidcClientPage.clickConfirmationGoBackButton();
	  
	  oidcClientPage.clickOidcShowCopyPopupButton();
	  oidcClientPage.clickCopyIdButton();
	  
	  oidcClientPage.clickCopyIdCloseButton();
	  assertTrue(oidcClientPage.isOidcDetailsElipsisButtonDisplayed(),
	  GlobalConstants.isAutherisationCodeTextDisplayed);
	  oidcClientPage.clickOidcDetailsElipsisButton();
	  assertTrue(oidcClientPage.isOidcDetailsViewButtonDisplayed(),
	  GlobalConstants.isAutherisationCodeTextDisplayed);
	  oidcClientPage.clickOidcEditButton();
	  oidcClientPage.clickoidcEditAddNewRedirectUrl();
	  
	  oidcClientPage.EnterPublickeySecondTextBox(publicKey);
	  oidcClientPage.clickOnSubmitButton();
	  assertTrue(oidcClientPage.isModifiedSuccessfullTextMessageDisplayed(),
	  GlobalConstants.isAutherisationCodeTextDisplayed);
	  oidcClientPage.clickConfirmationGoBackButton();
	  
	  assertTrue(oidcClientPage.isPartnerIdDescIconDisplayed(),
	  GlobalConstants.isPartnerIdDescAscIcon);
	  assertTrue(oidcClientPage.isPartnerIdAscIconDisplayed(),
	  GlobalConstants.isPartnerIdDescAscIcon);
	  assertTrue(oidcClientPage.isOidcClientNameDescIconDisplayed(),
	  GlobalConstants.isPartnerTypeDescAscIcon);
	  assertTrue(oidcClientPage.isOidcClientNameAscIconDisplayed(),
	  GlobalConstants.isPartnerTypeDescAscIcon);
	  assertTrue(oidcClientPage.isPolicyGroupNameDescIconDisplayed(),
	  GlobalConstants.isPolicyGroupNameDescAscIcon);
	  assertTrue(oidcClientPage.isPolicyGroupNameAscIconDisplayed(),
	  GlobalConstants.isPolicyGroupNameDescAscIcon);
	  assertTrue(oidcClientPage.isPolicyNameDescIconDisplayed(),
	  GlobalConstants.isPolicyNameDescAscIcon);
	  assertTrue(oidcClientPage.isPolicyNameAscIconDisplayed(),
	  GlobalConstants.isPolicyNameDescAscIcon);
	  assertTrue(oidcClientPage.isCreatedDateTimeDescISconDisplayed(),
	  GlobalConstants.isCreatedDateTimeDescAscIcon);
	  assertTrue(oidcClientPage.isCreatedDateTimeAscIconDisplayed(),
	  GlobalConstants.isCreatedDateTimeDescAscIcon);
	  oidcClientPage.isFilterButtonButtonEnabled(); }
	  
	  @Test public void CreateOidecClientcWithSpaceValu() {
	  
	  DashboardPage dashboardpage = new DashboardPage(driver);
	  
	  assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(),
	  GlobalConstants.isAuthenticationServicesDisplayed); OidcClientPage
	  oidcClientPage=dashboardpage.clickOnAuthenticationServicesTitle();
	  
	  assertTrue(oidcClientPage.isCreateOidcClientDisplayed(),
	  GlobalConstants.isCreateOIDCClientDisplayed);
	  oidcClientPage.clickOnCreateOidcClientButton();
	  
	  assertTrue(oidcClientPage.isPartnerIdDropdownDisplayed(),
	  GlobalConstants.isPartnerIdDropdownDisplayed);
	  oidcClientPage.selectPartnerIdDropdown("automationuiiii");
	  
	  assertTrue(oidcClientPage.isPolicyNameDropdownDisplayed(),
	  GlobalConstants.isPolicyNameDropdownDisplayed);
	  oidcClientPage.selectPolicyNameDropdown(data+"ad");
	  
	  oidcClientPage.enterNameOidcTextBox(" ");
	  
	  String publicKey = ConfigManager.getrandomPublicKey() + "\"" +
	  generateRandomString(4) + "\"}";
	  
	  oidcClientPage.enterPublicKeyTextBox(publicKey);
	  oidcClientPage.enterLogoUrTextBox(" ");
	  oidcClientPage.enterRedirectUriTextBox(" ");
	  
	  assertTrue(oidcClientPage.isEnterValidUriForLogoUriTextDisplayed(),
	  GlobalConstants.isEnterValidLogoUriTextDisplayed);
	  assertTrue(oidcClientPage.isEnterValidUriForRedirectUriTextDisplayed(),
	  GlobalConstants.isEnterRedirectUriTextDisplayed);
	  
	  }
	
	  @Test public void ClearFormOidecClient() {
		  DashboardPage dashboardpage = new DashboardPage(driver);
	  RegisterPage registerPage = new
	  RegisterPage(driver);
	  assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(), GlobalConstants.isAuthenticationServicesDisplayed); 
	  OidcClientPage oidcClientPage =dashboardpage.clickOnAuthenticationServicesTitle(); 
	  assertTrue(oidcClientPage.isCreateOidcClientDisplayed(),
	  GlobalConstants.isCreateOIDCClientDisplayed); 
	  oidcClientPage.clickOnCreateOidcClientButton(); 
	  oidcClientPage.enterNameOidcTextBox("0"+data); String publicKey =
	  ConfigManager.getrandomPublicKey() + "\"" + generateRandomString(4) + "\"}";
	  oidcClientPage.enterPublicKeyTextBox(publicKey); 
	  oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri()); 
	  oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri()); 
	  oidcClientPage.clickOnClearFormButton(); 
	  assertTrue(oidcClientPage.isLogoUriempty(), GlobalConstants.isLogoUriempty);
	  }
      
      @Test public void addingSecondRedirectUri() { 
	  DashboardPage dashboardpage = new DashboardPage(driver); 
	  RegisterPage registerPage = new RegisterPage(driver); 
	  assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(),
	  GlobalConstants.isAuthenticationServicesDisplayed); 
	  OidcClientPage oidcClientPage =dashboardpage.clickOnAuthenticationServicesTitle(); 
	  assertTrue(oidcClientPage.isCreateOidcClientDisplayed(),GlobalConstants.
	  isCreateOIDCClientDisplayed); 
	  oidcClientPage.clickOnCreateOidcClientButton();
	  oidcClientPage.clickOnRedirectUriAddNew(); //
	  assertTrue(oidcClientPage.isRedirectUri2TextBoxDisplayed(),GlobalConstants.
	  isRedirectUri2TextBoxDisplayed); 
	  }
      
      
      @Test public void deletingSecondRedirectUri() 
      { 
      DashboardPage dashboardpage = new DashboardPage(driver); 
      RegisterPage registerPage = new RegisterPage(driver); 
	  assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(),GlobalConstants.isAuthenticationServicesDisplayed); 
	  OidcClientPage oidcClientPage =dashboardpage.clickOnAuthenticationServicesTitle(); 
	  assertTrue(oidcClientPage.isCreateOidcClientDisplayed(), GlobalConstants.isCreateOIDCClientDisplayed); 
	  oidcClientPage.clickOnCreateOidcClientButton(); 
	  oidcClientPage.clickOnRedirectUriAddNew(); 
	  assertTrue(oidcClientPage.isRedirectUri2TextBoxDisplayed(),GlobalConstants.isRedirectUri2TextBoxDisplayed); 
	  oidcClientPage.clickOnRedirectUri2Delete();
	  assertFalse(oidcClientPage.isRedirectUri2TextBoxDisplayed(),GlobalConstants.isRedirectUri2TextBoxDisplayed); 
	  } 
	  
	 	
		@Test public void invalidLogoUriVerification() {
		DashboardPage dashboardpage = new DashboardPage(driver);
		RegisterPage registerPage = new RegisterPage(driver); 
		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(), GlobalConstants.isAuthenticationServicesDisplayed); 
		OidcClientPage oidcClientPage =dashboardpage.clickOnAuthenticationServicesTitle(); 
		assertTrue(oidcClientPage.isCreateOidcClientDisplayed(), GlobalConstants.isCreateOIDCClientDisplayed); 
		oidcClientPage.clickOnCreateOidcClientButton(); 
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getrandomPublicKey()); 
		assertTrue(oidcClientPage.isInvalidLogoUriErrorVissible(), GlobalConstants.isInvalidLogoUriErrorVissible); 
		
	 }
}
