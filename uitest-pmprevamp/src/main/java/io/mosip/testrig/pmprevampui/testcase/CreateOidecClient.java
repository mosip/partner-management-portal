package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.OidcClientPage;
import io.mosip.testrig.pmprevampui.pages.PartnerCertificatePage;
import io.mosip.testrig.pmprevampui.pages.PoliciesPage;
import io.mosip.testrig.pmprevampui.pages.RegisterPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class CreateOidecClient extends BaseClass {

	@Test
	public void CreateOidecClient() {

		DashboardPage dashboardpage = new DashboardPage(driver);

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
		oidcClientPage.selectPartnerIdDropdown("automationuiiii");
		
		assertTrue(oidcClientPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		oidcClientPage.selectPolicyNameDropdown(data+"ad");
		
		oidcClientPage.enterNameOidcTextBox(data+"ad");
		
		String publicKey = ConfigManager.getrandomPublicKey() + "\"" + generateRandomString(4) + "\"}";
		
		oidcClientPage.enterPublicKeyTextBox(publicKey);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnSubmitButton();
		
		assertTrue(oidcClientPage.isAuthorizationCodeTextDisplayed(), GlobalConstants.isAutherisationCodeTextDisplayed);
			
	}
	
	@Test
	public void CreateOidecClientcWithSpaceValu() {

		DashboardPage dashboardpage = new DashboardPage(driver);

		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(), GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage=dashboardpage.clickOnAuthenticationServicesTitle();

		assertTrue(oidcClientPage.isCreateOidcClientDisplayed(), GlobalConstants.isCreateOIDCClientDisplayed);
		oidcClientPage.clickOnCreateOidcClientButton();
	
		assertTrue(oidcClientPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		oidcClientPage.selectPartnerIdDropdown("automationuiiii");
		
		assertTrue(oidcClientPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		oidcClientPage.selectPolicyNameDropdown(data+"ad");
		
		oidcClientPage.enterNameOidcTextBox(" ");
		
		String publicKey = ConfigManager.getrandomPublicKey() + "\"" + generateRandomString(4) + "\"}";
		
		oidcClientPage.enterPublicKeyTextBox(publicKey);
		oidcClientPage.enterLogoUrTextBox(" ");
		oidcClientPage.enterRedirectUriTextBox(" ");
		
		assertTrue(oidcClientPage.isEnterValidUriForLogoUriTextDisplayed(), GlobalConstants.isEnterValidLogoUriTextDisplayed);
		assertTrue(oidcClientPage.isEnterValidUriForRedirectUriTextDisplayed(), GlobalConstants.isEnterRedirectUriTextDisplayed);
		
	}
}
