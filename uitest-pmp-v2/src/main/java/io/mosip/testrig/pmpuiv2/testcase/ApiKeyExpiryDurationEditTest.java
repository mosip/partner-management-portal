package io.mosip.testrig.pmpuiv2.testcase;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.pages.ApiKeyPage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.OidcClientPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

// Every @Test method here is self-contained: BaseClass.setUp() is @BeforeMethod, so TestNG gives each
// method its own fresh ChromeDriver + fresh default-admin login (and @AfterMethod quits that driver
// afterwards) - nothing about the browser or page navigation survives between methods, only real
// backend state (e.g. an expiry date actually submitted) does. dependsOnMethods is used purely for
// execution order and skip-on-failure cascading, matching the pattern already established in
// MispServicesTest - never for carrying DOM/browser state forward.
@Test(dependsOnGroups = { "ApiKeyAuthPartnerTest" }, groups = { "ApiKeyExpiryDurationEditTest" })
public class ApiKeyExpiryDurationEditTest extends BaseClass {

	private static final boolean ADMIN_VIEW = true;

	private DashboardPage dashboardPage;
	private OidcClientPage oidcClientPage;
	private ApiKeyPage apiKeyPage;
	private LoginPage loginPage;

	private void navigateToApiKeyTab() {
		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);
		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnApiKeyTab();
	}

	// DEACTIVATE_APIKEY (manually deactivated via the admin action menu) is reused as the stand-in
	// for an "expired" key across this ticket's TCs - the app has no UI path today to create an
	// already time-expired key, since API Key expiry isn't user-set at creation (unlike MISP License
	// Keys, which expose a date picker).
	private void navigateToDeactivatedApiKeyRow() {
		navigateToApiKeyTab();
		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		apiKeyPage.enterValidApiKeyNameInAdminFilter(GlobalConstants.DEACTIVATE_APIKEY);
		apiKeyPage.clickOnApplyFilterButton();
	}

	// Edit Expiry Date only functions on an Active key in this build (verified against the live QA
	// environment - on a Deactivated row it renders greyed out and is a no-op when clicked).
	// ACTIVATE_ADMINAPIKEY is the existing seeded Active key fixture created by ApiKeyAuthPartnerTest,
	// reused here the same way ApiKeyExpirationDateTest's admin-view test does.
	private void navigateToActiveApiKeyRow() {
		navigateToApiKeyTab();
		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		apiKeyPage.enterValidApiKeyNameInAdminFilter(GlobalConstants.ACTIVATE_ADMINAPIKEY);
		apiKeyPage.clickOnApplyFilterButton();
	}

	private void navigateToEditExpiryPageForActiveApiKey() {
		navigateToActiveApiKeyRow();
		apiKeyPage.clickOnActionButton();
		apiKeyPage.clickOnEditExpiryDateButton();
	}

	// Auth Partner creates their own API keys via the "Generate API Key" form (id generate_api_key,
	// only reachable in the partner's own view) - the admin view used throughout this class has no
	// create action. Switches the current session's login rather than relying on @BeforeMethod, so a
	// single test can create a key as the partner, then switch back to admin to deactivate it via the
	// same admin action menu every other test in this class uses.
	private void switchLoginTo(String username, String pwd) {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(username);
		loginPage.enterPassword(pwd);
		loginPage.clickOnLoginButton();
	}

	@Test(priority = 1, description = "Verify that User can successfully log in to the PMS portal")
	public void verifyLoginToPmsPortal() {
		dashboardPage = new DashboardPage(driver);

		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
	}

	@Test(priority = 2, description = "Verify that the Authentication Services card is displayed on the PMS home screen", dependsOnMethods = "verifyLoginToPmsPortal")
	public void verifyAuthenticationServicesCardDisplayed() {
		dashboardPage = new DashboardPage(driver);

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
	}

	@Test(priority = 3, description = "Verify that user can click on the Authentication Services card", dependsOnMethods = "verifyAuthenticationServicesCardDisplayed")
	public void verifyClickOnAuthenticationServicesCard() {
		dashboardPage = new DashboardPage(driver);

		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		assertTrue(oidcClientPage.isApiKeyTabDisplayed(), GlobalConstants.isApiKeyTabDisplayed);
	}

	@Test(priority = 4, description = "Verify that Authentication Services page displays two tabs: OIDC Client and API Key", dependsOnMethods = "verifyClickOnAuthenticationServicesCard")
	public void verifyAuthenticationServicesTabsDisplayed() {
		dashboardPage = new DashboardPage(driver);

		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		assertTrue(oidcClientPage.isOidcClientTabDisplayed(), GlobalConstants.isOidcClientTabDisplayed);
		assertTrue(oidcClientPage.isApiKeyTabDisplayed(), GlobalConstants.isApiKeyTabDisplayed);
	}

	@Test(priority = 5, description = "Verify that user can view the list of all API Keys under the API Key tab", dependsOnMethods = "verifyAuthenticationServicesTabsDisplayed")
	public void verifyApiKeyListDisplayedUnderApiKeyTab() {
		navigateToApiKeyTab();

		assertTrue(apiKeyPage.isSubTitleOfTabularViewDisplayed(), GlobalConstants.isSubTitleOfTabularViewDisplayed);
		assertTrue(apiKeyPage.isApiKeyItem1Displayed(), GlobalConstants.isApiKeyItem1Displayed);
	}

	@Test(priority = 6, description = "Verify that expired API Keys are also displayed in the API Key list, with status shown as Deactivated", dependsOnMethods = "verifyApiKeyListDisplayedUnderApiKeyTab")
	public void verifyExpiredApiKeyListedAsDeactivated() {
		navigateToDeactivatedApiKeyRow();

		assertTrue(apiKeyPage.isApiKeyItem1Displayed(), GlobalConstants.isApiKeyItem1Displayed);
		assertTrue(apiKeyPage.isApiKeyStatusDeactivatedDisplayed(),
				GlobalConstants.isApiKeyStatusDeactivatedDisplayed);
	}

	@Test(priority = 7, description = "Verify that the three-dot Action Menu is visible for an expired API Key", dependsOnMethods = "verifyExpiredApiKeyListedAsDeactivated")
	public void verifyActionMenuVisibleForExpiredApiKey() {
		navigateToDeactivatedApiKeyRow();

		assertTrue(apiKeyPage.isActionButtonDisplayed(), GlobalConstants.isActionButtonDisplayed);
		assertTrue(apiKeyPage.isActionButtonEnabled(), GlobalConstants.isActionButtonEnabled);

		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
	}

	@Test(priority = 8, description = "Verify that the Action Menu displays View, Edit Expiry Date and Deactivate options for an expired API Key", dependsOnMethods = "verifyActionMenuVisibleForExpiredApiKey")
	public void verifyActionMenuOptionsForExpiredApiKey() {
		navigateToDeactivatedApiKeyRow();
		apiKeyPage.clickOnActionButton();

		assertTrue(apiKeyPage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		assertTrue(apiKeyPage.isDeactivateButtonDisplayed(), GlobalConstants.isDeactivateButtonDisplayed);
		assertTrue(apiKeyPage.isEditExpiryDateButtonDisplayed(), GlobalConstants.isEditExpiryDateButtonDisplayed);
	}

	@Test(priority = 9, description = "Verify that the Action Menu of expired API Key shows the View option", dependsOnMethods = "verifyActionMenuOptionsForExpiredApiKey")
	public void verifyViewOptionInActionMenuForExpiredApiKey() {
		navigateToDeactivatedApiKeyRow();
		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);

		apiKeyPage.clickOnViewButton();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);

		apiKeyPage.clickOnViewApiKeyBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);
	}

	@Test(priority = 10, description = "Verify that the Action Menu of expired API Key shows the Edit Expiry Date option", dependsOnMethods = "verifyViewOptionInActionMenuForExpiredApiKey")
	public void verifyEditExpiryDateOptionInActionMenuForExpiredApiKey() {
		navigateToDeactivatedApiKeyRow();
		apiKeyPage.clickOnActionButton();

		assertTrue(apiKeyPage.isEditExpiryDateButtonDisplayed(), GlobalConstants.isEditExpiryDateButtonDisplayed);
	}

	@Test(priority = 11, description = "Verify that clicking Edit Expiry Date for an Active API Key redirects to the Edit API Key page", dependsOnMethods = "verifyEditExpiryDateOptionInActionMenuForExpiredApiKey")
	public void verifyEditExpiryDateRedirectsToEditApiKeyPage() {
		navigateToActiveApiKeyRow();
		assertTrue(apiKeyPage.isApiKeyItem1Displayed(), GlobalConstants.isApiKeyItem1Displayed);

		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isEditExpiryDateButtonDisplayed(), GlobalConstants.isEditExpiryDateButtonDisplayed);
		assertTrue(apiKeyPage.isEditExpiryDateButtonEnabled(), GlobalConstants.isEditExpiryDateButtonEnabled);

		apiKeyPage.clickOnEditExpiryDateButton();
		assertTrue(apiKeyPage.isEditApiKeyExpiryPageDisplayed(), GlobalConstants.isEditApiKeyExpiryPageDisplayed);
		assertEquals(apiKeyPage.getEditApiKeyExpiryPageTitle(), "Edit API key expiry",
				GlobalConstants.isEditApiKeyExpiryPageDisplayed);
	}

	@Test(priority = 12, description = "Verify that all fields are displayed on the Edit API Key page", dependsOnMethods = "verifyEditExpiryDateRedirectsToEditApiKeyPage")
	public void verifyAllFieldsDisplayedOnEditApiKeyPage() {
		navigateToEditExpiryPageForActiveApiKey();

		assertTrue(apiKeyPage.isEditApiKeyPartnerIdLabelDisplayed(), GlobalConstants.isEditApiKeyPartnerIdLabelDisplayed);
		assertTrue(apiKeyPage.isEditApiKeyPartnerTypeLabelDisplayed(), GlobalConstants.isEditApiKeyPartnerTypeLabelDisplayed);
		assertTrue(apiKeyPage.isEditApiKeyPolicyGroupLabelDisplayed(), GlobalConstants.isEditApiKeyPolicyGroupLabelDisplayed);
		assertTrue(apiKeyPage.isEditApiKeyPolicyNameLabelDisplayed(), GlobalConstants.isEditApiKeyPolicyNameLabelDisplayed);
		assertTrue(apiKeyPage.isEditApiKeyPolicyGroupDescriptionLabelDisplayed(),
				GlobalConstants.isEditApiKeyPolicyGroupDescriptionLabelDisplayed);
		assertTrue(apiKeyPage.isEditApiKeyPolicyNameDescriptionLabelDisplayed(),
				GlobalConstants.isEditApiKeyPolicyNameDescriptionLabelDisplayed);
		assertTrue(apiKeyPage.isEditApiKeyExpiryDateLabelDisplayed(), GlobalConstants.isEditApiKeyExpiryDateLabelDisplayed);
	}

	@Test(priority = 14, description = "Verify that the Partner ID field is displayed as read-only", dependsOnMethods = "verifyAllFieldsDisplayedOnEditApiKeyPage")
	public void verifyPartnerIdFieldReadOnlyOnEditApiKeyPage() {
		navigateToEditExpiryPageForActiveApiKey();

		assertTrue(apiKeyPage.isEditApiKeyPartnerIdFieldReadOnly(), GlobalConstants.isEditApiKeyPartnerIdFieldReadOnly);
	}

	@Test(priority = 15, description = "Verify that the Partner Type field is displayed as read-only", dependsOnMethods = "verifyPartnerIdFieldReadOnlyOnEditApiKeyPage")
	public void verifyPartnerTypeFieldReadOnlyOnEditApiKeyPage() {
		navigateToEditExpiryPageForActiveApiKey();

		assertTrue(apiKeyPage.isEditApiKeyPartnerTypeFieldReadOnly(), GlobalConstants.isEditApiKeyPartnerTypeFieldReadOnly);
	}

	@Test(priority = 16, description = "Verify that the Policy Group field is displayed as read-only", dependsOnMethods = "verifyPartnerTypeFieldReadOnlyOnEditApiKeyPage")
	public void verifyPolicyGroupFieldReadOnlyOnEditApiKeyPage() {
		navigateToEditExpiryPageForActiveApiKey();

		assertTrue(apiKeyPage.isEditApiKeyPolicyGroupFieldReadOnly(), GlobalConstants.isEditApiKeyPolicyGroupFieldReadOnly);
	}

	@Test(priority = 17, description = "Verify that the Policy Name field is displayed as read-only", dependsOnMethods = "verifyPolicyGroupFieldReadOnlyOnEditApiKeyPage")
	public void verifyPolicyNameFieldReadOnlyOnEditApiKeyPage() {
		navigateToEditExpiryPageForActiveApiKey();

		assertTrue(apiKeyPage.isEditApiKeyPolicyNameFieldReadOnly(), GlobalConstants.isEditApiKeyPolicyNameFieldReadOnly);
	}

	@Test(priority = 18, description = "Verify that the Policy Group Description field is displayed as read-only", dependsOnMethods = "verifyPolicyNameFieldReadOnlyOnEditApiKeyPage")
	public void verifyPolicyGroupDescriptionFieldReadOnlyOnEditApiKeyPage() {
		navigateToEditExpiryPageForActiveApiKey();

		assertTrue(apiKeyPage.isEditApiKeyPolicyGroupDescriptionFieldReadOnly(),
				GlobalConstants.isEditApiKeyPolicyGroupDescriptionFieldReadOnly);
	}

	@Test(priority = 19, description = "Verify that the Policy Name Description field is displayed as read-only", dependsOnMethods = "verifyPolicyGroupDescriptionFieldReadOnlyOnEditApiKeyPage")
	public void verifyPolicyNameDescriptionFieldReadOnlyOnEditApiKeyPage() {
		navigateToEditExpiryPageForActiveApiKey();

		assertTrue(apiKeyPage.isEditApiKeyPolicyNameDescriptionFieldReadOnly(),
				GlobalConstants.isEditApiKeyPolicyNameDescriptionFieldReadOnly);
	}

	@Test(priority = 20, description = "Verify that Expiry Date field is editable", dependsOnMethods = "verifyPolicyNameDescriptionFieldReadOnlyOnEditApiKeyPage")
	public void verifyExpiryDateFieldEditableOnEditApiKeyPage() {
		navigateToEditExpiryPageForActiveApiKey();

		assertTrue(apiKeyPage.isEditApiKeyExpiryDateFieldEnabled(), GlobalConstants.isEditApiKeyExpiryDateFieldEnabled);

		String originalExpiryDate = apiKeyPage.getEditApiKeyExpiryDateValue();
		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		assertTrue(apiKeyPage.isEditApiKeyExpiryDateCalendarDisplayed(), GlobalConstants.isEditApiKeyExpiryDateCalendarDisplayed);

		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		assertNotEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), originalExpiryDate,
				GlobalConstants.isEditApiKeyExpiryDateSelectable);
	}

	@Test(priority = 21, description = "Verify that the Expiry Date field allows selecting a new date using the date picker", dependsOnMethods = "verifyExpiryDateFieldEditableOnEditApiKeyPage")
	public void verifyExpiryDateCalendarPickerAllowsSelectingNewDate() {
		navigateToEditExpiryPageForActiveApiKey();

		String beforeSelection = apiKeyPage.getEditApiKeyExpiryDateValue();
		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		assertTrue(apiKeyPage.isEditApiKeyExpiryDateCalendarDisplayed(), GlobalConstants.isEditApiKeyExpiryDateCalendarDisplayed);

		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		assertNotEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), beforeSelection,
				GlobalConstants.isEditApiKeyExpiryDateSelectable);
	}

	// Checked the live QA build's react-datepicker instance directly: its maxDate prop is undefined,
	// and the default expiry on an existing key is already an arbitrary far-future placeholder
	// (07/31/4764) with no upper bound enforced anywhere in the calendar or elsewhere in this form.
	// There is no UI-observable "max expiry is configurable" surface to assert against today, so this
	// is left as a stub - fill in once a config field or an enforced ceiling actually exists to check.
	@Test(priority = 22, description = "Verify Max Expiry Date for API Key is configurable", dependsOnMethods = "verifyExpiryDateCalendarPickerAllowsSelectingNewDate")
	public void verifyMaxExpiryDateForApiKeyIsConfigurable() {
	}

	@Test(priority = 23, description = "Verify that only future dates are selectable in the Expiry Date picker", dependsOnMethods = "verifyMaxExpiryDateForApiKeyIsConfigurable")
	public void verifyOnlyFutureDatesSelectableInExpiryDateCalendar() {
		navigateToEditExpiryPageForActiveApiKey();

		// Typing today's date drives the calendar to open on the current month (see
		// enterEditApiKeyExpiryDate's comment in ApiKeyPage) so today's disabled state is actually
		// visible to check, instead of whatever far-future month the stored expiry opens on.
		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.enterEditApiKeyExpiryDate(PmpTestUtil.todayDate);
		assertTrue(apiKeyPage.isTodayDisabledInEditApiKeyExpiryDateCalendar(),
				GlobalConstants.isTodayDisabledInEditApiKeyExpiryDateCalendar);

		String beforeSelection = apiKeyPage.getEditApiKeyExpiryDateValue();
		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		assertNotEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), beforeSelection,
				GlobalConstants.isFutureDateSelectableInEditApiKeyExpiryDateCalendar);
	}

	// Checked the live QA build: typing a past date shows no error text anywhere on the page (before
	// or after blur) - the field silently reverts to its original value and Submit stays disabled.
	// There is no "Expiry date must be a future date" message to assert against today, so this checks
	// the real observed behavior (silent rejection) instead of the message text from the TC spec.
	@Test(priority = 24, description = "Verify API Key Expiry Date does not allow past dates", dependsOnMethods = "verifyOnlyFutureDatesSelectableInExpiryDateCalendar")
	public void verifyPastDateRejectedOnEditApiKeyExpiryDate() {
		navigateToEditExpiryPageForActiveApiKey();

		String originalExpiryDate = apiKeyPage.getEditApiKeyExpiryDateValue();
		String pastDate = LocalDate.now().minusDays(3).format(PmpTestUtil.dateFormatter);
		apiKeyPage.enterEditApiKeyExpiryDate(pastDate);
		apiKeyPage.blurEditApiKeyExpiryDateField();
		assertTrue(!apiKeyPage.isEditApiKeySubmitButtonEnabled(),
				GlobalConstants.isPastDateRejectedOnEditApiKeyExpiryDate);
		assertEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), originalExpiryDate,
				GlobalConstants.isPastDateRejectedOnEditApiKeyExpiryDate);
	}

	@Test(priority = 25, description = "Verify that past dates are disabled in the API Key Expiry Date calendar", dependsOnMethods = "verifyPastDateRejectedOnEditApiKeyExpiryDate")
	public void verifyPastDatesDisabledInEditApiKeyExpiryDateCalendar() {
		navigateToEditExpiryPageForActiveApiKey();

		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.enterEditApiKeyExpiryDate(PmpTestUtil.todayDate);
		assertTrue(apiKeyPage.getDisabledDayCountInEditApiKeyExpiryDateCalendar() > 1,
				GlobalConstants.isPastDatesDisabledInEditApiKeyExpiryDateCalendar);

		String beforeClick = apiKeyPage.getEditApiKeyExpiryDateValue();
		apiKeyPage.clickOnDay1InEditApiKeyExpiryDateCalendar();
		assertEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), beforeClick,
				GlobalConstants.isPastDatesDisabledInEditApiKeyExpiryDateCalendar);
	}

	@Test(priority = 26, description = "Verify that the newly selected future date is displayed in the Expiry Date field", dependsOnMethods = "verifyPastDatesDisabledInEditApiKeyExpiryDateCalendar")
	public void verifyNewlySelectedFutureDateDisplayedInExpiryDateField() {
		navigateToEditExpiryPageForActiveApiKey();

		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();

		String displayedValue = apiKeyPage.getEditApiKeyExpiryDateValue();
		assertTrue(displayedValue.matches("(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01])/\\d{4}"),
				GlobalConstants.isEditApiKeyExpiryDateDisplayedInCorrectFormat);
	}

	// Past dates cannot be selected via the calendar at all - disabled cells are a genuine no-op on
	// click (TC_38157_24) - and typing one directly reverts silently before Submit is even enabled
	// (TC_38157_23), both verified against the live QA build. So there is no reachable path to
	// "select a past date via the picker, then click Submit" in the current build; left as a stub
	// until either restriction changes.
	@Test(priority = 27, description = "Verify that system shows an error message when a past date is selected and submitted", dependsOnMethods = "verifyNewlySelectedFutureDateDisplayedInExpiryDateField")
	public void verifyErrorMessageOnSubmittingPastDate() {
	}

	@Test(priority = 28, description = "Verify Edit API key screen displays Cancel and Submit buttons at the bottom", dependsOnMethods = "verifyErrorMessageOnSubmittingPastDate")
	public void verifyCancelAndSubmitButtonsDisplayedOnEditApiKeyPage() {
		navigateToEditExpiryPageForActiveApiKey();

		assertTrue(apiKeyPage.isEditApiKeyCancelButtonDisplayed(), GlobalConstants.isEditApiKeyCancelButtonDisplayed);
		assertTrue(apiKeyPage.isEditApiKeySubmitButtonDisplayed(), GlobalConstants.isEditApiKeySubmitButtonDisplayed);
	}

	@Test(priority = 29, description = "Verify that the Cancel button is clickable", dependsOnMethods = "verifyCancelAndSubmitButtonsDisplayedOnEditApiKeyPage")
	public void verifyCancelButtonClickableOnEditApiKeyPage() {
		navigateToEditExpiryPageForActiveApiKey();

		assertTrue(apiKeyPage.isEditApiKeyCancelButtonEnabled(), GlobalConstants.isEditApiKeyCancelButtonEnabled);

		apiKeyPage.clickOnEditApiKeyCancelButton();
		assertTrue(apiKeyPage.isEditApiKeyCancelConfirmationPopupDisplayed(),
				GlobalConstants.isEditApiKeyCancelConfirmationPopupDisplayed);
		apiKeyPage.clickOnEditApiKeyCancelConfirmationProceedButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);
	}

	@Test(priority = 30, description = "Verify that clicking Cancel returns to the API Key list without saving any changes", dependsOnMethods = "verifyCancelButtonClickableOnEditApiKeyPage")
	public void verifyCancelDiscardsUnsavedExpiryDateChange() {
		navigateToEditExpiryPageForActiveApiKey();

		String originalExpiryDate = apiKeyPage.getEditApiKeyExpiryDateValue();
		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		assertNotEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), originalExpiryDate,
				GlobalConstants.isEditApiKeyExpiryDateSelectable);

		apiKeyPage.clickOnEditApiKeyCancelButton();
		assertTrue(apiKeyPage.isEditApiKeyCancelConfirmationPopupDisplayed(),
				GlobalConstants.isEditApiKeyCancelConfirmationPopupDisplayed);
		apiKeyPage.clickOnEditApiKeyCancelConfirmationProceedButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);

		// Reopen and confirm the change was genuinely discarded, not silently persisted
		apiKeyPage.clickOnActionButton();
		apiKeyPage.clickOnEditExpiryDateButton();
		assertTrue(apiKeyPage.isEditApiKeyExpiryPageDisplayed(), GlobalConstants.isEditApiKeyExpiryPageDisplayed);
		assertEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), originalExpiryDate,
				GlobalConstants.isEditApiKeyCancelDiscardsChanges);
	}

	@Test(priority = 31, description = "Verify that the Submit button is clickable", dependsOnMethods = "verifyCancelDiscardsUnsavedExpiryDateChange")
	public void verifySubmitButtonClickableOnEditApiKeyPage() {
		navigateToEditExpiryPageForActiveApiKey();

		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		assertTrue(apiKeyPage.isEditApiKeySubmitButtonEnabled(), GlobalConstants.isEditApiKeySubmitButtonEnabled);

		apiKeyPage.clickOnEditApiKeySubmitButton();
		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);

		apiKeyPage.clickOnEditApiKeyConfirmationGoBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);
	}

	@Test(priority = 32, description = "Verify that clicking Submit updates the Expiry Date successfully", dependsOnMethods = "verifySubmitButtonClickableOnEditApiKeyPage")
	public void verifySubmitUpdatesExpiryDateSuccessfully() {
		navigateToEditExpiryPageForActiveApiKey();

		String beforeChange = apiKeyPage.getEditApiKeyExpiryDateValue();
		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.clickOnDay1InEditApiKeyExpiryDateCalendar();
		String newExpiryDate = apiKeyPage.getEditApiKeyExpiryDateValue();
		assertNotEquals(newExpiryDate, beforeChange, GlobalConstants.isEditApiKeyExpiryDateSelectable);

		apiKeyPage.clickOnEditApiKeySubmitButton();
		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);
		apiKeyPage.clickOnEditApiKeyConfirmationGoBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);

		// Reopen and confirm the new date genuinely persisted, not just a one-off success message
		apiKeyPage.clickOnActionButton();
		apiKeyPage.clickOnEditExpiryDateButton();
		assertTrue(apiKeyPage.isEditApiKeyExpiryPageDisplayed(), GlobalConstants.isEditApiKeyExpiryPageDisplayed);
		assertEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), newExpiryDate,
				GlobalConstants.isEditApiKeyExpiryDateUpdatedSuccessfully);
	}

	@Test(priority = 33, description = "Verify that the Submit button remains disabled when no date is selected", dependsOnMethods = "verifySubmitUpdatesExpiryDateSuccessfully")
	public void verifySubmitDisabledWhenExpiryDateCleared() {
		navigateToEditExpiryPageForActiveApiKey();

		apiKeyPage.clearEditApiKeyExpiryDateField();
		assertTrue(!apiKeyPage.isEditApiKeySubmitButtonEnabled(), GlobalConstants.isEditApiKeySubmitButtonDisabledWhenEmpty);
	}

	@Test(priority = 34, description = "Verify that a confirmation popup appears after clicking Submit", dependsOnMethods = "verifySubmitDisabledWhenExpiryDateCleared")
	public void verifyConfirmationPopupAppearsAfterSubmit() {
		navigateToEditExpiryPageForActiveApiKey();

		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		assertTrue(apiKeyPage.isEditApiKeySubmitButtonEnabled(), GlobalConstants.isEditApiKeySubmitButtonEnabled);

		apiKeyPage.clickOnEditApiKeySubmitButton();
		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);

		apiKeyPage.clickOnEditApiKeyConfirmationGoBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);
	}

	// The API Key Name subtitle only renders before submission - the confirmation screen that follows
	// drops it - so it has to be captured up-front and compared against the message text afterwards.
	@Test(priority = 35, description = "Verify that the popup message displays correct confirmation text", dependsOnMethods = "verifyConfirmationPopupAppearsAfterSubmit")
	public void verifyConfirmationPopupMessageText() {
		navigateToEditExpiryPageForActiveApiKey();

		String apiKeyName = apiKeyPage.getEditApiKeyName();
		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.clickOnDay1InEditApiKeyExpiryDateCalendar();
		apiKeyPage.clickOnEditApiKeySubmitButton();

		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);
		assertEquals(apiKeyPage.getEditApiKeyConfirmationHeaderText(),
				"Expiry date for API key " + apiKeyName + " has been updated successfully.",
				GlobalConstants.isEditApiKeyConfirmationMessageTextCorrect);

		apiKeyPage.clickOnEditApiKeyConfirmationGoBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);
	}

	@Test(priority = 36, description = "Verify Go back button on confirmation screen navigates to the previous page", dependsOnMethods = "verifyConfirmationPopupMessageText")
	public void verifyGoBackButtonOnConfirmationScreen() {
		navigateToEditExpiryPageForActiveApiKey();

		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		apiKeyPage.clickOnEditApiKeySubmitButton();
		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);

		apiKeyPage.clickOnEditApiKeyConfirmationGoBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);
	}

	@Test(priority = 37, description = "Verify Home button on confirmation screen redirects to the Home Dashboard", dependsOnMethods = "verifyGoBackButtonOnConfirmationScreen")
	public void verifyHomeButtonOnConfirmationScreen() {
		navigateToEditExpiryPageForActiveApiKey();

		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.clickOnDay1InEditApiKeyExpiryDateCalendar();
		apiKeyPage.clickOnEditApiKeySubmitButton();
		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);

		apiKeyPage.clickOnEditApiKeyConfirmationHomeButton();
		dashboardPage = new DashboardPage(driver);
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
	}

	@Test(priority = 38, description = "Verify that the Undo changes button is clickable", dependsOnMethods = "verifyHomeButtonOnConfirmationScreen")
	public void verifyUndoChangesButtonClickableOnEditApiKeyPage() {
		navigateToEditExpiryPageForActiveApiKey();

		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		assertTrue(apiKeyPage.isEditApiKeyUndoChangesButtonEnabled(), GlobalConstants.isEditApiKeyUndoChangesButtonEnabled);

		apiKeyPage.clickOnEditApiKeyUndoChangesButton();
	}

	@Test(priority = 39, description = "Verify that the Undo Changes option resets the expiry date to the original date", dependsOnMethods = "verifyUndoChangesButtonClickableOnEditApiKeyPage")
	public void verifyUndoChangesResetsExpiryDateToOriginal() {
		navigateToEditExpiryPageForActiveApiKey();

		String originalExpiryDate = apiKeyPage.getEditApiKeyExpiryDateValue();

		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.clickOnDay1InEditApiKeyExpiryDateCalendar();
		assertNotEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), originalExpiryDate,
				GlobalConstants.isEditApiKeyExpiryDateSelectable);

		apiKeyPage.clickOnEditApiKeyUndoChangesButton();
		assertEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), originalExpiryDate,
				GlobalConstants.isEditApiKeyUndoChangesResetsExpiryDate);
		assertTrue(!apiKeyPage.isEditApiKeySubmitButtonEnabled(), GlobalConstants.isEditApiKeySubmitButtonDisabledAfterUndo);
	}

	// There is no configuration UI anywhere in this app for the confirmation message text (verified
	// throughout this class - it's a fixed string driven only by the API key name). "Not configurable"
	// is confirmed by that absence: the message stays the exact hardcoded format every time it's
	// checked, with no settings screen or field that could alter it.
	@Test(priority = 40, description = "Verify the popup confirmation text is not configurable", dependsOnMethods = "verifyUndoChangesResetsExpiryDateToOriginal")
	public void verifyPopupConfirmationTextIsNotConfigurable() {
		navigateToEditExpiryPageForActiveApiKey();

		String apiKeyName = apiKeyPage.getEditApiKeyName();
		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		apiKeyPage.clickOnEditApiKeySubmitButton();

		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);
		assertEquals(apiKeyPage.getEditApiKeyConfirmationHeaderText(),
				"Expiry date for API key " + apiKeyName + " has been updated successfully.",
				GlobalConstants.isEditApiKeyConfirmationMessageTextCorrect);
	}

	@Test(priority = 41, description = "Verify Go back button functionality on confirmation screen navigates to the previous page", dependsOnMethods = "verifyPopupConfirmationTextIsNotConfigurable")
	public void verifyGoBackButtonFunctionalityOnConfirmationScreen() {
		navigateToEditExpiryPageForActiveApiKey();

		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.clickOnDay1InEditApiKeyExpiryDateCalendar();
		apiKeyPage.clickOnEditApiKeySubmitButton();
		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);

		apiKeyPage.clickOnEditApiKeyConfirmationGoBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);
	}

	@Test(priority = 42, description = "Verify Home button functionality on confirmation screen redirects to the Home Dashboard", dependsOnMethods = "verifyGoBackButtonFunctionalityOnConfirmationScreen")
	public void verifyHomeButtonFunctionalityOnConfirmationScreen() {
		navigateToEditExpiryPageForActiveApiKey();

		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		apiKeyPage.clickOnEditApiKeySubmitButton();
		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);

		apiKeyPage.clickOnEditApiKeyConfirmationHomeButton();
		dashboardPage = new DashboardPage(driver);
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
	}

	// Enumerated every visible button on the live confirmation screen: only "Go Back" and "Home"
	// exist (confirmation_go_back_btn / confirmation_home_btn) - there is no Close button or icon
	// anywhere on it. Left as a stub rather than an assertion that would always fail against the
	// current build; revisit if a Close control gets added.
	@Test(priority = 43, description = "Verify that the popup displays a Close button", dependsOnMethods = "verifyHomeButtonFunctionalityOnConfirmationScreen")
	public void verifyCloseButtonDisplayedOnConfirmationPopup() {
	}

	// This TC's title says "Go Back" but its steps say "click Close" - there is no Close button
	// (confirmed in verifyCloseButtonDisplayedOnConfirmationPopup), so this exercises the real Go
	// Back control per the title, and additionally confirms the list reflects the new date (not just
	// that navigation happened) by comparing against the row's Expiration Date cell.
	@Test(priority = 44, description = "Verify that clicking Go Back dismisses the popup and the list reflects the updated expiry date", dependsOnMethods = "verifyCloseButtonDisplayedOnConfirmationPopup")
	public void verifyGoBackDismissesPopupAndShowsUpdatedExpiryInList() {
		navigateToEditExpiryPageForActiveApiKey();

		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		String newExpiryDateRaw = apiKeyPage.getEditApiKeyExpiryDateValue();
		apiKeyPage.clickOnEditApiKeySubmitButton();
		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);

		apiKeyPage.clickOnEditApiKeyConfirmationGoBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);

		LocalDate newExpiryDate = LocalDate.parse(newExpiryDateRaw, PmpTestUtil.dateFormatter);
		String expectedListFormat = newExpiryDate.format(PmpTestUtil.nonZeroPadderDateFormatter);
		assertEquals(apiKeyPage.getExpirationDateFromList(ADMIN_VIEW), expectedListFormat,
				GlobalConstants.isUpdatedExpiryDateReflectedInList);
	}

	// Verified live: editing a key that was NOT at the top of the unfiltered list (row 3) and
	// submitting a new expiry does not move it - the list keeps its original order afterwards,
	// unrelated to which row was last modified. The "sorted by last updated" premise doesn't hold
	// in this build, so left as a stub rather than an assertion that would always fail.
	@Test(priority = 45, description = "Verify that the updated API Key appears at the top of the list after redirection", dependsOnMethods = "verifyGoBackDismissesPopupAndShowsUpdatedExpiryInList")
	public void verifyUpdatedApiKeyAppearsAtTopOfList() {
	}

	@Test(priority = 46, description = "Verify that only View, Deactivate and Edit Expiry Date options are visible in the Action Menu after updating the expiry date", dependsOnMethods = "verifyUpdatedApiKeyAppearsAtTopOfList")
	public void verifyActionMenuOptionsAfterExpiryUpdate() {
		navigateToActiveApiKeyRow();

		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		assertTrue(apiKeyPage.isDeactivateButtonDisplayed(), GlobalConstants.isDeactivateButtonDisplayed);
		assertTrue(apiKeyPage.isEditExpiryDateButtonDisplayed(), GlobalConstants.isEditExpiryDateButtonDisplayed);
	}

	// Contrasts with verifyActionMenuOptionsForExpiredApiKey (priority 8), where Edit Expiry Date is
	// displayed but greyed out/non-functional for a Deactivated key. Here the key is still Active
	// (its own status was never toggled), so the same option should be genuinely enabled, not just
	// present in the DOM.
	@Test(priority = 47, description = "Verify that the Edit Expiry Date option is visible once the API Key is Active", dependsOnMethods = "verifyActionMenuOptionsAfterExpiryUpdate")
	public void verifyEditExpiryDateOptionVisibleForActiveApiKey() {
		navigateToActiveApiKeyRow();
		apiKeyPage.clickOnActionButton();

		assertTrue(apiKeyPage.isEditExpiryDateButtonDisplayed(), GlobalConstants.isEditExpiryDateButtonDisplayed);
		assertTrue(apiKeyPage.isEditExpiryDateButtonEnabled(), GlobalConstants.isEditExpiryDateButtonEnabled);
	}

	@Test(priority = 48, description = "Verify that the View option opens the API Key details page", dependsOnMethods = "verifyEditExpiryDateOptionVisibleForActiveApiKey")
	public void verifyViewOptionOpensApiKeyDetailsPage() {
		navigateToActiveApiKeyRow();
		apiKeyPage.clickOnActionButton();

		apiKeyPage.clickOnViewButton();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);

		apiKeyPage.clickOnViewApiKeyBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);
	}

	// Opens the real deactivation confirmation popup (proving Deactivate is clickable and triggers
	// the flow) but cancels out rather than completing it - this key is the shared Active fixture
	// this whole test class has been reusing; actually deactivating it would break any later TC that
	// still expects it Active.
	@Test(priority = 49, description = "Verify that the Deactivate option is clickable for the Active API Key and triggers the deactivation flow", dependsOnMethods = "verifyViewOptionOpensApiKeyDetailsPage")
	public void verifyDeactivateOptionTriggersDeactivationFlow() {
		navigateToActiveApiKeyRow();

		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isDeactivateButtonDisplayed(), GlobalConstants.isDeactivateButtonDisplayed);
		assertTrue(apiKeyPage.isDeactivateButtonEnabled(), GlobalConstants.isDeactivateButtonEnabled);

		apiKeyPage.clickOnApiKeyDeactivateButton();
		assertTrue(apiKeyPage.isApiKeyInAdminDeactivatePopupDisplayed(), GlobalConstants.isApiKeyDeactivatePopupDisplayed);
		assertTrue(apiKeyPage.isApiKeyInAdminDeactivateTitleDisplayed(), GlobalConstants.isApiKeyDeactivateTitleDisplayed);
		assertTrue(apiKeyPage.isApiKeyInAdminDeactivateInfoTextDisplayed(), GlobalConstants.isApiKeyDeactivationInfoTextDisplayed);

		apiKeyPage.clickOnDeactivateCancelButton();
	}

	// Creates its own disposable key (as Auth Partner, the only role that can create one), switches
	// back to Partner Admin, then completes the deactivation for real - deliberately not reusing the
	// shared ACTIVATE_ADMINAPIKEY fixture other TCs in this class depend on staying Active.
	@Test(priority = 50, description = "Verify that the Deactivate option completes the deactivation flow", dependsOnMethods = "verifyDeactivateOptionTriggersDeactivationFlow")
	public void verifyDeactivateOptionCompletesDeactivationForThrowawayKey() {
		dashboardPage = new DashboardPage(driver);
		switchLoginTo(GlobalConstants.AUTH_PARTNER_ID, GlobalConstants.PARTNER_PASSWORD);

		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnApiKeyTab();
		apiKeyPage = new ApiKeyPage(driver);

		String throwawayKeyName = GlobalConstants.DEACTIVATE_THROWAWAY_APIKEY + BaseClass.data;
		apiKeyPage.clickOnCreateApiKey();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		apiKeyPage.enterNameOfApiKeyTextBox(throwawayKeyName);
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();

		switchLoginTo(userid, password);
		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnApiKeyTab();

		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		apiKeyPage.enterValidApiKeyNameInAdminFilter(throwawayKeyName);
		apiKeyPage.clickOnApplyFilterButton();
		assertTrue(apiKeyPage.isApiKeyItem1Displayed(), GlobalConstants.isApiKeyItem1Displayed);

		apiKeyPage.clickOnActionButton();
		apiKeyPage.clickOnApiKeyDeactivateButton();
		assertTrue(apiKeyPage.isApiKeyInAdminDeactivatePopupDisplayed(), GlobalConstants.isApiKeyDeactivatePopupDisplayed);

		apiKeyPage.clickOnDeactivateSubmitButton();
		assertTrue(apiKeyPage.isApiKeyStatusDeactivatedDisplayed(), GlobalConstants.isApiKeyStatusDeactivatedDisplayed);
	}

	// "System configuration" has no UI screen anywhere in this app - PMS exposes no settings page for
	// API key default validity - so this validates only what's actually UI-observable: a newly created
	// key gets an automatic, non-blank expiry consistently later than its creation date. Whether that
	// duration matches one specific configured value, and whether changing that config live-updates
	// future keys, are both outside what Selenium can check without direct backend/config access.
	@Test(priority = 51, description = "Verify that a newly created API Key receives an automatically computed default expiry date", dependsOnMethods = "verifyDeactivateOptionCompletesDeactivationForThrowawayKey")
	public void verifyNewApiKeyReceivesDefaultExpiryDate() {
		dashboardPage = new DashboardPage(driver);
		switchLoginTo(GlobalConstants.AUTH_PARTNER_ID, GlobalConstants.PARTNER_PASSWORD);

		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnApiKeyTab();
		apiKeyPage = new ApiKeyPage(driver);

		String newKeyName = GlobalConstants.EXPIRY_APIKEY + BaseClass.data;
		apiKeyPage.clickOnCreateApiKey();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		apiKeyPage.enterNameOfApiKeyTextBox(newKeyName);
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();

		assertTrue(apiKeyPage.isApiListItem1Displayed(), GlobalConstants.isApiListDisplayed);
		assertTrue(apiKeyPage.isExpirationDateNotBeforeCreationDate(false),
				GlobalConstants.isExpirationDateNotBeforeCreationDate);
	}

	// Verified live against the QA environment: switching kc_locale to "ara" on the Keycloak login
	// page (via LoginPage.selectLanguage, the same mechanism BaseClass.setUp() uses for a full
	// language-pass suite run) renders the whole PMS app in Arabic with body dir="rtl". This is the
	// only TC in the class needing a non-English login, so it switches language and calls
	// ApiKeyPage.init("ara") directly rather than requiring a separate full suite run per language.
	@Test(priority = 52, description = "Verify that the Edit API Key page and expiry update flow are displayed in Arabic when logged in with Arabic language", dependsOnMethods = "verifyNewApiKeyReceivesDefaultExpiryDate")
	public void verifyEditApiKeyFlowDisplayedInArabic() {
		ApiKeyPage.init("ara");

		dashboardPage = new DashboardPage(driver);
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.selectLanguage("ara");
		loginPage.login(userid, password);

		dashboardPage = new DashboardPage(driver);
		assertTrue(dashboardPage.isPageDirectionRtl(), GlobalConstants.isRtlLayoutAppliedForArabic);
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnApiKeyTab();
		apiKeyPage = new ApiKeyPage(driver);

		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		apiKeyPage.enterValidApiKeyNameInAdminFilter(GlobalConstants.ACTIVATE_ADMINAPIKEY);
		apiKeyPage.clickOnApplyFilterButton();
		assertTrue(apiKeyPage.isApiKeyItem1Displayed(), GlobalConstants.isApiKeyItem1Displayed);

		apiKeyPage.clickOnActionButton();
		assertEquals(apiKeyPage.getViewButtonText(), ApiKeyPage.API_KEY_ACTION_VIEW_OPTION_TEXT,
				GlobalConstants.isApiKeyActionMenuTextDisplayedInArabic);
		assertEquals(apiKeyPage.getEditExpiryDateButtonText(), ApiKeyPage.API_KEY_ACTION_EDIT_EXPIRY_OPTION_TEXT,
				GlobalConstants.isApiKeyActionMenuTextDisplayedInArabic);
		assertEquals(apiKeyPage.getDeactivateButtonText(), ApiKeyPage.API_KEY_ACTION_DEACTIVATE_OPTION_TEXT,
				GlobalConstants.isApiKeyActionMenuTextDisplayedInArabic);

		apiKeyPage.clickOnEditExpiryDateButton();
		assertTrue(apiKeyPage.isEditApiKeyExpiryPageDisplayed(), GlobalConstants.isEditApiKeyExpiryPageDisplayed);
		assertEquals(apiKeyPage.getEditApiKeyExpiryPageTitle(), ApiKeyPage.API_KEY_EDIT_EXPIRY_PAGE_TITLE,
				GlobalConstants.isEditApiKeyPageTextDisplayedInArabic);
		assertEquals(apiKeyPage.getEditApiKeyPartnerIdLabelText(), ApiKeyPage.API_KEY_PARTNER_ID_LABEL,
				GlobalConstants.isEditApiKeyPageTextDisplayedInArabic);
		assertEquals(apiKeyPage.getEditApiKeyPartnerTypeLabelText(), ApiKeyPage.API_KEY_PARTNER_TYPE_LABEL,
				GlobalConstants.isEditApiKeyPageTextDisplayedInArabic);
		assertEquals(apiKeyPage.getEditApiKeyPolicyGroupLabelText(), ApiKeyPage.API_KEY_POLICY_GROUP_LABEL,
				GlobalConstants.isEditApiKeyPageTextDisplayedInArabic);
		assertEquals(apiKeyPage.getEditApiKeyPolicyNameLabelText(), ApiKeyPage.API_KEY_POLICY_NAME_LABEL,
				GlobalConstants.isEditApiKeyPageTextDisplayedInArabic);
		assertEquals(apiKeyPage.getEditApiKeyPolicyGroupDescriptionLabelText(),
				ApiKeyPage.API_KEY_POLICY_GROUP_DESCRIPTION_LABEL, GlobalConstants.isEditApiKeyPageTextDisplayedInArabic);
		assertEquals(apiKeyPage.getEditApiKeyPolicyNameDescriptionLabelText(),
				ApiKeyPage.API_KEY_POLICY_NAME_DESCRIPTION_LABEL, GlobalConstants.isEditApiKeyPageTextDisplayedInArabic);
		assertEquals(apiKeyPage.getEditApiKeyExpiryDateLabelText(), ApiKeyPage.API_KEY_EXPIRY_DATE_LABEL,
				GlobalConstants.isEditApiKeyPageTextDisplayedInArabic);
		assertEquals(apiKeyPage.getEditApiKeyUndoChangesButtonText(), ApiKeyPage.API_KEY_UNDO_CHANGES_BTN_TEXT,
				GlobalConstants.isEditApiKeyPageTextDisplayedInArabic);
		assertEquals(apiKeyPage.getEditApiKeyCancelButtonText(), ApiKeyPage.API_KEY_CANCEL_BTN_TEXT,
				GlobalConstants.isEditApiKeyPageTextDisplayedInArabic);
		assertEquals(apiKeyPage.getEditApiKeySubmitButtonText(), ApiKeyPage.API_KEY_SUBMIT_BTN_TEXT,
				GlobalConstants.isEditApiKeyPageTextDisplayedInArabic);
		assertTrue(apiKeyPage.isPageDirectionRtl(), GlobalConstants.isRtlLayoutAppliedForArabic);

		String apiKeyName = apiKeyPage.getEditApiKeyName();
		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		apiKeyPage.clickOnEditApiKeySubmitButton();

		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);
		String expectedMessage = ApiKeyPage.API_KEY_CONFIRMATION_MESSAGE_TEMPLATE.replace("{0}", apiKeyName);
		assertEquals(apiKeyPage.getEditApiKeyConfirmationHeaderText(), expectedMessage,
				GlobalConstants.isEditApiKeyPageTextDisplayedInArabic);
		assertEquals(apiKeyPage.getEditApiKeyConfirmationGoBackButtonText(), ApiKeyPage.API_KEY_CONFIRMATION_GO_BACK_BTN_TEXT,
				GlobalConstants.isEditApiKeyPageTextDisplayedInArabic);
		assertEquals(apiKeyPage.getEditApiKeyConfirmationHomeButtonText(), ApiKeyPage.API_KEY_CONFIRMATION_HOME_BTN_TEXT,
				GlobalConstants.isEditApiKeyPageTextDisplayedInArabic);
	}
}
