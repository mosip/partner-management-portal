package io.mosip.testrig.pmprevampui.testcase;

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
	
	@Test
	public void CreateOidecClientcWithSpaceValu() {

		DashboardPage dashboardpage = new DashboardPage(driver);

		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(), GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage=dashboardpage.clickOnAuthenticationServicesTitle();

		assertTrue(oidcClientPage.isCreateOidcClientDisplayed(), GlobalConstants.isCreateOIDCClientDisplayed);
		oidcClientPage.clickOnCreateOidcClientButton();
	
		assertTrue(oidcClientPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		oidcClientPage.selectPartnerIdDropdown();
		
		assertTrue(oidcClientPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		oidcClientPage.selectPolicyNameDropdown(data+"ad");
		
		oidcClientPage.enterNameOidcTextBox(" ");
		
		oidcClientPage.enterPublicKeyTextBox(" ");
		oidcClientPage.enterLogoUrTextBox(" ");
		oidcClientPage.enterRedirectUriTextBox(" ");
		
		assertTrue(oidcClientPage.isEnterValidUriForLogoUriTextDisplayed(), GlobalConstants.isEnterValidLogoUriTextDisplayed);
		assertTrue(oidcClientPage.isEnterValidUriForRedirectUriTextDisplayed(), GlobalConstants.isEnterRedirectUriTextDisplayed);
		
	}
	
	@Test
	public void CreateOidecClientWithouUploadingCertificates() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		ApiKeyPage apiKeyPage = new ApiKeyPage(driver);
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		
		loginpage.enterUserName("0"+ data+"n");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();

		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(), GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage=dashboardpage.clickOnAuthenticationServicesTitle();
		
		assertTrue(oidcClientPage.isCreateOidcClientDisplayed(), GlobalConstants.isCreateOIDCClientDisplayed);		
		assertTrue(oidcClientPage.isPolicyGroupHeaderTextDisplayed(), GlobalConstants.isPolicyGroupHeaderTextDisplayed);
		assertTrue(oidcClientPage.isPolicyNameHeaderTextDisplayed(), GlobalConstants.isPolicyNameHeaderTextDisplayed);
		assertTrue(oidcClientPage.isOIDCClientNameHeaderTextDisplayed(), GlobalConstants.isOIDCClientNameHeaderTextDisplayed);
		assertTrue(oidcClientPage.isCreatedDateHeaderTextDisplayed(), GlobalConstants.isCreatedDateHeaderTextDisplayed);
		assertTrue(oidcClientPage.isStatusHeaderTextDisplayed(), GlobalConstants.isStatusHeaderTextDisplayed);
		assertTrue(oidcClientPage.isOIDCClientIDHeaderTextDisplayed(), GlobalConstants.isOIDCClientIDHeaderTextDisplayed);
		assertTrue(oidcClientPage.isActionHeaderTextDisplayed(), GlobalConstants.isActionHeaderTextDisplayed);
		oidcClientPage.clickOnCreateOidcClientButton();
	
		assertTrue(oidcClientPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
//		assertTrue(oidcClientPage.isUserIdDoesNotExistsPopupDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		oidcClientPage.clickOnPartnerIdDropdown();
		
		assertTrue(oidcClientPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		
		String publicKey = KeycloakUserManager.readJsonData(TestRunner.getResourcePath() + "/" + "config/"+"/publicKey.json").toString();
		oidcClientPage.enterPublicKeyTextBox(publicKey);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnAddNewRedirectUrlButton();
		oidcClientPage.EntercreateOidcRedirectUrl2(ConfigManager.getRedirectUri()+"a");
		oidcClientPage.clickOnAddNewRedirectUrlButton();
		oidcClientPage.EntercreateOidcRedirectUrl3(ConfigManager.getRedirectUri()+"b");
		oidcClientPage.clickOnAddNewRedirectUrlButton();
		oidcClientPage.EntercreateOidcRedirectUrl4(ConfigManager.getRedirectUri()+"c");
		oidcClientPage.clickOnAddNewRedirectUrlButton();
		oidcClientPage.EntercreateOidcRedirectUrl5(ConfigManager.getRedirectUri()+"d");
		
		oidcClientPage.clickOnCreateOidcClearForm();
		
		assertFalse(oidcClientPage.isCreateOidcRedirectUrl5Displayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
	}
}
