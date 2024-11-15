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
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		
		loginpage.enterUserName("0"+data);
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();

		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(), GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage=dashboardpage.clickOnAuthenticationServicesTitle();

//		assertTrue(oidcClientPage.isCreateOidcClientDisplayed(), GlobalConstants.isCreateOIDCClientDisplayed);		
//		assertTrue(apiKeyPage.isPolicyGroupHeaderTextDisplayed(), GlobalConstants.isPolicyGroupHeaderTextDisplayed);
//		assertTrue(apiKeyPage.isPolicyNameHeaderTextDisplayed(), GlobalConstants.isPolicyNameHeaderTextDisplayed);
//		assertTrue(apiKeyPage.isCreatedDateHeaderTextDisplayed(), GlobalConstants.isCreatedDateHeaderTextDisplayed);
//		assertTrue(apiKeyPage.isStatusHeaderTextDisplayed(), GlobalConstants.isStatusHeaderTextDisplayed);
//		assertTrue(apiKeyPage.isActionHeaderTextDisplayed(), GlobalConstants.isActionHeaderTextDisplayed);
		oidcClientPage.clickOnApiKeyTab();
		
		assertTrue(apiKeyPage.isGenerateAPIKeyDisplayed(), GlobalConstants.isGenerateAPIKeyDisplayed);
		apiKeyPage.ClickOnAPIKeyDisplayed();
		assertTrue(apiKeyPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		apiKeyPage.selectPartnerIdDropdown("0"+data);
		
		assertTrue(apiKeyPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		apiKeyPage.enterNameOfApiKeyTextBox("0"+data);
		
		apiKeyPage.ClickOnSubmitButton();
//		assertTrue(oidcClientPage.isAuthorizationCodeTextDisplayed(), GlobalConstants.isAutherisationCodeTextDisplayed);
		
		apiKeyPage.clickOnCopyIdButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		assertTrue(apiKeyPage.isConfirmationGoBackButtonDisplayed(), GlobalConstants.isGoBackButtonDisplayed);
		apiKeyPage.clickOnConfirmationGoBackButton();
		assertTrue(apiKeyPage.isApiListItem1Displayed(), GlobalConstants.isApiListDisplayed);
		
		
		assertTrue(apiKeyPage.isPartnerIdDescIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(apiKeyPage.isPartnerIdAscIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(apiKeyPage.isApiKeyLabelDescIconDisplayed(), GlobalConstants.isPartnerTypeDescAscIcon);
		assertTrue(apiKeyPage.isApiKeyLabelAscIconDisplayed(), GlobalConstants.isPartnerTypeDescAscIcon);
		assertTrue(apiKeyPage.isPolicyGroupNameDescIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(apiKeyPage.isPolicyGroupNameAscIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(apiKeyPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(apiKeyPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(apiKeyPage.isCreatedDateTimeDescISconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(apiKeyPage.isCreatedDateTimeAscIconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(apiKeyPage.isFilterButtonButtonEnabled(), GlobalConstants.isFiletrButtonDisplayedOrEnabled);
		
		apiKeyPage.clickOnFilterButton();
		apiKeyPage.clickOnApiKeyPartnerIdFilter();
		apiKeyPage.clickOnApiKeySelectPolicyGroupFilter();
		apiKeyPage.clickOnApiKeySelectPolicyNameFilter();
		apiKeyPage.clickOnApiKeySelectClientNameFilter();
		apiKeyPage.clickOnApiKeySelectStatusFilter();
		
		apiKeyPage.clickOnFilterResetButton();
		assertTrue(apiKeyPage.isFilterButtonButtonEnabled(), GlobalConstants.isFiletrButtonDisplayedOrEnabled);
	}
}
