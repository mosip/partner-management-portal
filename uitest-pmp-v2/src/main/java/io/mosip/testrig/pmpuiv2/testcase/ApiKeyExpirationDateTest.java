package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.ApiKeyPage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.OidcClientPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "ApiKeyAuthPartnerTest" }, groups = { "ApiKeyExpirationDateTest" })
public class ApiKeyExpirationDateTest extends BaseClass {

	private static final boolean ADMIN_VIEW = true;
	private static final boolean PARTNER_VIEW = false;

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private ApiKeyPage apiKeyPage;
	private OidcClientPage oidcClientPage;

	@Test(priority = 1, description = "Verify API Key expiration date in tabular and individual view as Authentication Partner")
	public void apiKeyExpirationDateAsAuthPartner() {

		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);

		loginAsAuthPartner();

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		assertTrue(oidcClientPage.isApiKeyTabDisplayed(), GlobalConstants.isApiKeyTabDisplayed);
		oidcClientPage.clickOnApiKeyTab();
		assertTrue(apiKeyPage.isSubTitleOfTabularViewDisplayed(), GlobalConstants.isSubTitleOfTabularViewDisplayed);

		assertTrue(apiKeyPage.isCreationDateHeaderDisplayed(), GlobalConstants.isCreationDateHeaderDisplayed);
		assertTrue(apiKeyPage.isExpirationDateHeaderDisplayed(), GlobalConstants.isApiKeyExpirationDateHeaderDisplayed);
		assertTrue(apiKeyPage.isExpirationDateHeaderAfterCreationDate(),
				GlobalConstants.isExpirationDateHeaderAfterCreationDate);
		assertTrue(apiKeyPage.isExpirationDateSameAsBrowserDateFormat(PARTNER_VIEW),
				GlobalConstants.isExpirationDateSameAsBrowserDateFormat);

		assertTrue(apiKeyPage.isApiListItem1Displayed(), GlobalConstants.isApiListDisplayed);
		apiKeyPage.clickOnApiListItem1();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);

		assertTrue(apiKeyPage.isApiKeyDetailsExpirationDateLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsExpirationDateLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsExpirationDateContextDisplayed(),
				GlobalConstants.isApiKeyDetailsExpirationDateContextDisplayed);
		assertTrue(apiKeyPage.isIndividualViewFieldOrderCorrect(), GlobalConstants.isIndividualViewFieldOrderCorrect);
		assertTrue(apiKeyPage.isExpirationDateStyledLikeOtherFields(),
				GlobalConstants.isExpirationDateStyledLikeOtherFields);

		apiKeyPage.clickOnViewApiKeyBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);
	}

	@Test(priority = 2, description = "Verify redirect to the API Key tabular view after submitting API Key details", dependsOnMethods = "apiKeyExpirationDateAsAuthPartner")
	public void redirectToTabularViewAfterSubmit() {

		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);

		loginAsAuthPartner();
		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnApiKeyTab();

		apiKeyPage.clickOnCreateApiKey();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		apiKeyPage.enterNameOfApiKeyTextBox(GlobalConstants.EXPIRY_APIKEY + BaseClass.data);
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();

		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);
		assertTrue(apiKeyPage.isApiListItem1Displayed(), GlobalConstants.isApiListDisplayed);
		assertTrue(apiKeyPage.isExpirationDateHeaderDisplayed(), GlobalConstants.isApiKeyExpirationDateHeaderDisplayed);
		assertTrue(apiKeyPage.isExpirationDateNotBeforeCreationDate(PARTNER_VIEW),
				GlobalConstants.isExpirationDateNotBeforeCreationDate);

		String expirationDateInList = apiKeyPage.getExpirationDateFromList(PARTNER_VIEW);
		apiKeyPage.clickOnApiListItem1();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);
		assertEquals(apiKeyPage.getExpirationDateFromIndividualView(), expirationDateInList,
				GlobalConstants.isExpirationDateConsistentAcrossViews);
		apiKeyPage.clickOnViewApiKeyBackButton();
	}

	@Test(priority = 3, description = "Verify API Key expiration date, sorting and row clickability as Partner Admin", dependsOnMethods = "redirectToTabularViewAfterSubmit")
	public void apiKeyExpirationDateAsPartnerAdmin() {

		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);

		assertTrue(dashboardPage.isAuthenticationServicesDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		oidcClientPage = dashboardPage.clickOnAuthenticationServices();
		assertTrue(oidcClientPage.isApiKeyTabDisplayed(), GlobalConstants.isApiKeyTabDisplayed);
		oidcClientPage.clickOnApiKeyTab();

		assertTrue(apiKeyPage.isCreationDateHeaderDisplayed(), GlobalConstants.isCreationDateHeaderDisplayed);
		assertTrue(apiKeyPage.isExpirationDateHeaderDisplayed(), GlobalConstants.isApiKeyExpirationDateHeaderDisplayed);
		assertTrue(apiKeyPage.isExpirationDateHeaderAfterCreationDate(),
				GlobalConstants.isExpirationDateHeaderAfterCreationDate);
		assertTrue(apiKeyPage.isExpirationDateSameAsBrowserDateFormat(ADMIN_VIEW),
				GlobalConstants.isExpirationDateSameAsBrowserDateFormat);

		assertTrue(apiKeyPage.isExpiryDateAscIconDisplayed(), GlobalConstants.isExpiryDateAscIconDisplayed);
		assertTrue(apiKeyPage.isExpiryDateDescIconDisplayed(), GlobalConstants.isExpiryDateDescIconDisplayed);

		apiKeyPage.clickOnExpiryDateAscIcon();
		assertTrue(apiKeyPage.isExpiryDateColumnSorted(true, ADMIN_VIEW), GlobalConstants.isExpiryDateSortedAscending);

		apiKeyPage.clickOnExpiryDateDescIcon();
		assertTrue(apiKeyPage.isExpiryDateColumnSorted(false, ADMIN_VIEW), GlobalConstants.isExpiryDateSortedDescending);

		filterApiKeysByName(GlobalConstants.ACTIVATE_ADMINAPIKEY);
		apiKeyPage.clickOnActivatedAdminApiKey();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsExpirationDateLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsExpirationDateLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsExpirationDateContextDisplayed(),
				GlobalConstants.isApiKeyDetailsExpirationDateContextDisplayed);
		apiKeyPage.clickOnViewApiKeyBackButton();

		filterApiKeysByName(GlobalConstants.DEACTIVATE_APIKEY);
		assertTrue(apiKeyPage.isDeactivatedRowNotClickable(ADMIN_VIEW),
				GlobalConstants.isDeactivatedApiKeyRowNotClickable);
	}

	private void filterApiKeysByName(String apiKeyName) {
		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		apiKeyPage.enterValidApiKeyNameInAdminFilter(apiKeyName);
		apiKeyPage.clickOnApplyFilterButton();
	}

	private void loginAsAuthPartner() {
		dashboardPage = new DashboardPage(driver);
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.AUTH_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();
	}
}
