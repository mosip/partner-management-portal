package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;
import io.mosip.testrig.pmpuiv2.pages.ApiKeyPage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.OidcClientPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

/**
 * Authentication Services: API Key expiration date in the individual (detailed)
 * view and the tabular view, for both the Authentication Partner and the
 * Partner Admin.
 *
 * Covers TC_40334_01 to TC_40334_10, TC_40334_13 and TC_40334_14.
 *
 * TC_40334_11 (Arabic) and TC_40334_12 (French) are not covered here. The suite
 * logs in through one fixed English session and the shared page objects locate
 * several elements by their English text, so a language switch would fail on
 * the locators rather than on the feature under test.
 */
@Test(dependsOnGroups = { "ApiKeyAuthPartnerTest" }, groups = { "ApiKeyExpirationDateTest" })
public class ApiKeyExpirationDateTest extends BaseClass {

	/** The Partner Admin list, as opposed to the Authentication Partner list. */
	private static final boolean ADMIN_VIEW = true;
	private static final boolean PARTNER_VIEW = false;

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private ApiKeyPage apiKeyPage;
	private OidcClientPage oidcClientPage;

	/**
	 * TC_40334_01 navigate to the API Keys tab and see the tabular list.
	 * TC_40334_02 'Expiration Date' column present, beside 'Creation Date'.
	 * TC_40334_04 an active API Key row opens the individual view.
	 * TC_40334_05 the date renders in the browser locale format.
	 * TC_40334_06 the individual view field sequence ends with Expiration Date.
	 * TC_40334_07 the column is visible to the Authentication Partner.
	 * TC_40334_10 the field carries the same typography as its neighbours.
	 */
	@Test(priority = 1, description = "API Key expiration date in tabular and individual view as Authentication Partner")
	public void apiKeyExpirationDateAsAuthPartner() {

		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);

		loginAsAuthPartner();

		// TC_40334_01: dashboard -> Authentication Services -> API Keys
		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		assertTrue(oidcClientPage.isApiKeyTabDisplayed(), GlobalConstants.isApiKeyTabDisplayed);
		oidcClientPage.clickOnApiKeyTab();
		assertTrue(apiKeyPage.isSubTitleOfTabularViewDisplayed(), GlobalConstants.isSubTitleOfTabularViewDisplayed);

		// TC_40334_02 and TC_40334_07: column present and positioned after Creation Date
		assertTrue(apiKeyPage.isCreationDateHeaderDisplayed(), GlobalConstants.isCreationDateHeaderDisplayed);
		assertTrue(apiKeyPage.isExpirationDateHeaderDisplayed(), GlobalConstants.isExpirationDateHeaderDisplayed);
		assertTrue(apiKeyPage.isExpirationDateHeaderAfterCreationDate(),
				GlobalConstants.isExpirationDateHeaderAfterCreationDate);

		// TC_40334_05: browser locale date format
		assertTrue(apiKeyPage.isExpirationDateSameAsBrowserDateFormat(PARTNER_VIEW),
				GlobalConstants.isExpirationDateSameAsBrowserDateFormat);

		// TC_40334_04: an active row opens the individual view
		assertTrue(apiKeyPage.isApiListItem1Displayed(), GlobalConstants.isApiListDisplayed);
		apiKeyPage.clickOnApiListItem1();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);

		// TC_40334_06: Expiration Date present and last in the field sequence
		assertTrue(apiKeyPage.isApiKeyDetailsExpirationDateLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsExpirationDateLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsExpirationDateContextDisplayed(),
				GlobalConstants.isApiKeyDetailsExpirationDateContextDisplayed);
		assertTrue(apiKeyPage.isIndividualViewFieldOrderCorrect(), GlobalConstants.isIndividualViewFieldOrderCorrect);

		// TC_40334_10: the field is styled like every other field on the page
		assertTrue(apiKeyPage.isExpirationDateStyledLikeOtherFields(),
				GlobalConstants.isExpirationDateStyledLikeOtherFields);

		apiKeyPage.clickOnViewApiKeyBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);
	}

	/**
	 * TC_40334_08 the expiry equals the creation date plus the configured duration.
	 * TC_40334_09 after submitting, the user lands back on the tabular view with
	 * the new key listed.
	 */
	@Test(priority = 2, description = "Redirect to the API Key tabular view after submitting API Key details", dependsOnMethods = "apiKeyExpirationDateAsAuthPartner")
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

		// TC_40334_09: back on the list, with the submitted key visible
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);
		assertTrue(apiKeyPage.isApiListItem1Displayed(), GlobalConstants.isApiListDisplayed);
		assertTrue(apiKeyPage.isExpirationDateHeaderDisplayed(), GlobalConstants.isExpirationDateHeaderDisplayed);

		// TC_40334_08: expiry equals creation date plus the configured duration
		assertTrue(
				apiKeyPage.isExpirationDateOffsetFromCreationDate(ConfigManager.getApiKeyExpiryPeriodInDays(),
						PARTNER_VIEW),
				GlobalConstants.isExpirationDateOffsetFromCreationDate);
	}

	/**
	 * TC_40334_02 'Expiration Date' column in the Partner Admin tabular view.
	 * TC_40334_03 the column sorts ascending and descending.
	 * TC_40334_05 the date renders in the browser locale format.
	 * TC_40334_13 the individual view shows Expiration Date for Partner Admin.
	 * TC_40334_14 rows open the individual view except those in Deactivated status.
	 */
	@Test(priority = 3, description = "API Key expiration date, sorting and row clickability as Partner Admin", dependsOnMethods = "redirectToTabularViewAfterSubmit")
	public void apiKeyExpirationDateAsPartnerAdmin() {

		// BaseClass.setUp() already signs this session in as the Partner Admin.
		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);

		assertTrue(dashboardPage.isAuthenticationServicesDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		oidcClientPage = dashboardPage.clickOnAuthenticationServices();
		assertTrue(oidcClientPage.isApiKeyTabDisplayed(), GlobalConstants.isApiKeyTabDisplayed);
		oidcClientPage.clickOnApiKeyTab();

		// TC_40334_02: column present and beside Creation Date
		assertTrue(apiKeyPage.isCreationDateHeaderDisplayed(), GlobalConstants.isCreationDateHeaderDisplayed);
		assertTrue(apiKeyPage.isExpirationDateHeaderDisplayed(), GlobalConstants.isExpirationDateHeaderDisplayed);
		assertTrue(apiKeyPage.isExpirationDateHeaderAfterCreationDate(),
				GlobalConstants.isExpirationDateHeaderAfterCreationDate);

		// TC_40334_05: browser locale date format in the admin list
		assertTrue(apiKeyPage.isExpirationDateSameAsBrowserDateFormat(ADMIN_VIEW),
				GlobalConstants.isExpirationDateSameAsBrowserDateFormat);

		// TC_40334_03: sort ascending then descending on Expiration Date
		assertTrue(apiKeyPage.isExpiryDateAscIconDisplayed(), GlobalConstants.isExpiryDateAscIconDisplayed);
		assertTrue(apiKeyPage.isExpiryDateDescIconDisplayed(), GlobalConstants.isExpiryDateDescIconDisplayed);

		apiKeyPage.clickOnExpiryDateAscIcon();
		assertTrue(apiKeyPage.isExpiryDateColumnSorted(true, ADMIN_VIEW), GlobalConstants.isExpiryDateSortedAscending);

		apiKeyPage.clickOnExpiryDateDescIcon();
		assertTrue(apiKeyPage.isExpiryDateColumnSorted(false, ADMIN_VIEW), GlobalConstants.isExpiryDateSortedDescending);

		// TC_40334_13 and TC_40334_14: an active row opens the individual view
		filterApiKeysByName(GlobalConstants.ACTIVATE_ADMINAPIKEY);
		apiKeyPage.clickOnActivatedAdminApiKey();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsExpirationDateLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsExpirationDateLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsExpirationDateContextDisplayed(),
				GlobalConstants.isApiKeyDetailsExpirationDateContextDisplayed);
		apiKeyPage.clickOnViewApiKeyBackButton();

		// TC_40334_14: a Deactivated row must not open the individual view
		filterApiKeysByName(GlobalConstants.DEACTIVATE_APIKEY);
		assertTrue(apiKeyPage.isDeactivatedRowNotClickable(ADMIN_VIEW),
				GlobalConstants.isDeactivatedApiKeyRowNotClickable);
	}

	/** Narrows the admin list to the API Key carrying the given name. */
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
