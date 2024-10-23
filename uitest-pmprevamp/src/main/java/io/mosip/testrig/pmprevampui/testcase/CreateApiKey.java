package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
import io.mosip.testrig.pmprevampui.pages.ApiKeyPage;
import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.OidcClientPage;
import io.mosip.testrig.pmprevampui.pages.PartnerCertificatePage;
import io.mosip.testrig.pmprevampui.pages.PoliciesPage;
import io.mosip.testrig.pmprevampui.pages.RegisterPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class CreateApiKey extends BaseClass {

	@Test
	public void CreateApiKey() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		ApiKeyPage apiKeyPage = new ApiKeyPage(driver);

		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(), GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage=dashboardpage.clickOnAuthenticationServicesTitle();

		assertTrue(oidcClientPage.isCreateOidcClientDisplayed(), GlobalConstants.isCreateOIDCClientDisplayed);		
		assertTrue(apiKeyPage.isPolicyGroupHeaderTextDisplayed(), GlobalConstants.isPolicyGroupHeaderTextDisplayed);
		assertTrue(apiKeyPage.isPolicyNameHeaderTextDisplayed(), GlobalConstants.isPolicyNameHeaderTextDisplayed);
		assertTrue(apiKeyPage.isCreatedDateHeaderTextDisplayed(), GlobalConstants.isCreatedDateHeaderTextDisplayed);
		assertTrue(apiKeyPage.isStatusHeaderTextDisplayed(), GlobalConstants.isStatusHeaderTextDisplayed);
		assertTrue(apiKeyPage.isActionHeaderTextDisplayed(), GlobalConstants.isActionHeaderTextDisplayed);
		oidcClientPage.clickOnApiKeyTab();
		
		assertTrue(apiKeyPage.isGenerateAPIKeyDisplayed(), GlobalConstants.isGenerateAPIKeyDisplayed);
		apiKeyPage.ClickOnAPIKeyDisplayed();
		assertTrue(apiKeyPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		apiKeyPage.selectPartnerIdDropdown("automationuiiii");
		
		assertTrue(apiKeyPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		apiKeyPage.enterNameOfApiKeyTextBox(data+"ad");
		
		apiKeyPage.ClickOnSubmitButton();
		assertTrue(oidcClientPage.isAuthorizationCodeTextDisplayed(), GlobalConstants.isAutherisationCodeTextDisplayed);
			
	}
}
