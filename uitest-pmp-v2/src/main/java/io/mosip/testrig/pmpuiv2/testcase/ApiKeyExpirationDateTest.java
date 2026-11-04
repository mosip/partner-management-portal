package io.mosip.testrig.pmpuiv2.testcase;

import java.text.Normalizer;
import java.time.LocalDate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;
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
	private OidcClientPage oidcClientPage;
	private ApiKeyPage apiKeyPage;
	private LoginPage loginPage;

	@Test(priority = 1, description = "Verify login and navigation to the API Key list")
	public void verifyLoginAndNavigateToApiKeyList() {
		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);

		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
		switchLoginTo(GlobalConstants.AUTH_PARTNER_ID, GlobalConstants.PARTNER_PASSWORD);

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		assertTrue(oidcClientPage.isOidcClientTabDisplayed(), GlobalConstants.isOidcClientTabDisplayed);
		assertTrue(oidcClientPage.isApiKeyTabDisplayed(), GlobalConstants.isApiKeyTabDisplayed);
		oidcClientPage.clickOnApiKeyTab();
		assertTrue(apiKeyPage.isSubTitleOfTabularViewDisplayed(), GlobalConstants.isSubTitleOfTabularViewDisplayed);
		assertTrue(apiKeyPage.isApiListItem1Displayed(), GlobalConstants.isApiListDisplayed);
	}

	@Test(priority = 2, description = "Verify API Key expiration date as Auth Partner", dependsOnMethods = "verifyLoginAndNavigateToApiKeyList")
	public void verifyApiKeyExpirationDateAsAuthPartner() {
		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);

		switchLoginTo(GlobalConstants.AUTH_PARTNER_ID, GlobalConstants.PARTNER_PASSWORD);

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

	@Test(priority = 3, description = "Verify redirect to API Key list after creating a key", dependsOnMethods = "verifyApiKeyExpirationDateAsAuthPartner")
	public void verifyRedirectToTabularViewAfterSubmit() {
		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);

		switchLoginTo(GlobalConstants.AUTH_PARTNER_ID, GlobalConstants.PARTNER_PASSWORD);
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

	@Test(priority = 4, description = "Verify API Key expiration date and sorting as Partner Admin", dependsOnMethods = "verifyRedirectToTabularViewAfterSubmit")
	public void verifyApiKeyExpirationDateAsPartnerAdmin() {
		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);

		switchLoginTo(userid, password);

		assertTrue(dashboardPage.isAdminAuthenticationServicesDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		oidcClientPage = dashboardPage.clickOnAdminAuthenticationServices();
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

		apiKeyPage.filterApiKeysByPartnerIdAndName(GlobalConstants.AUTH_PARTNER_ID, GlobalConstants.ACTIVATE_ADMINAPIKEY);
		apiKeyPage.clickOnActivatedAdminApiKey();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsExpirationDateLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsExpirationDateLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsExpirationDateContextDisplayed(),
				GlobalConstants.isApiKeyDetailsExpirationDateContextDisplayed);
		apiKeyPage.clickOnViewApiKeyBackButton();

		apiKeyPage.filterApiKeysByPartnerIdAndName(GlobalConstants.AUTH_PARTNER_ID, GlobalConstants.DEACTIVATE_APIKEY);
		assertTrue(apiKeyPage.isDeactivatedRowNotClickable(ADMIN_VIEW),
				GlobalConstants.isDeactivatedApiKeyRowNotClickable);
	}

	@Test(priority = 5, description = "Verify Action Menu for an expired API Key", dependsOnMethods = "verifyApiKeyExpirationDateAsPartnerAdmin")
	public void verifyExpiredApiKeyActionMenu() {
		navigateToDeactivatedApiKeyRow();

		assertTrue(apiKeyPage.isApiKeyItem1Displayed(), GlobalConstants.isApiKeyItem1Displayed);
		assertTrue(apiKeyPage.isApiKeyStatusDeactivatedDisplayed(),
				GlobalConstants.isApiKeyStatusDeactivatedDisplayed);
		assertTrue(apiKeyPage.isActionButtonDisplayed(), GlobalConstants.isActionButtonDisplayed);
		assertTrue(apiKeyPage.isActionButtonEnabled(), GlobalConstants.isActionButtonEnabled);

		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		assertTrue(apiKeyPage.isDeactivateButtonDisplayed(), GlobalConstants.isDeactivateButtonDisplayed);
		assertTrue(apiKeyPage.isEditExpiryDateButtonDisplayed(), GlobalConstants.isEditExpiryDateButtonDisplayed);

		apiKeyPage.clickOnViewButton();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);

		apiKeyPage.clickOnViewApiKeyBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);
	}

	@Test(priority = 6, description = "Verify Edit API Key page fields and read-only state", dependsOnMethods = "verifyExpiredApiKeyActionMenu")
	public void verifyEditExpiryDatePageDisplaysCorrectly() {
		navigateToActiveApiKeyRow();
		assertTrue(apiKeyPage.isApiKeyItem1Displayed(), GlobalConstants.isApiKeyItem1Displayed);

		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isEditExpiryDateButtonDisplayed(), GlobalConstants.isEditExpiryDateButtonDisplayed);
		assertTrue(apiKeyPage.isEditExpiryDateButtonEnabled(), GlobalConstants.isEditExpiryDateButtonEnabled);

		apiKeyPage.clickOnEditExpiryDateButton();
		assertTrue(apiKeyPage.isEditApiKeyExpiryPageDisplayed(), GlobalConstants.isEditApiKeyExpiryPageDisplayed);
		assertEquals(apiKeyPage.getEditApiKeyExpiryPageTitle(), "Edit API key expiry",
				GlobalConstants.isEditApiKeyExpiryPageDisplayed);

		assertTrue(apiKeyPage.isEditApiKeyPartnerIdLabelDisplayed(), GlobalConstants.isEditApiKeyPartnerIdLabelDisplayed);
		assertTrue(apiKeyPage.isEditApiKeyPartnerTypeLabelDisplayed(), GlobalConstants.isEditApiKeyPartnerTypeLabelDisplayed);
		assertTrue(apiKeyPage.isEditApiKeyPolicyGroupLabelDisplayed(), GlobalConstants.isEditApiKeyPolicyGroupLabelDisplayed);
		assertTrue(apiKeyPage.isEditApiKeyPolicyNameLabelDisplayed(), GlobalConstants.isEditApiKeyPolicyNameLabelDisplayed);
		assertTrue(apiKeyPage.isEditApiKeyPolicyGroupDescriptionLabelDisplayed(),
				GlobalConstants.isEditApiKeyPolicyGroupDescriptionLabelDisplayed);
		assertTrue(apiKeyPage.isEditApiKeyPolicyNameDescriptionLabelDisplayed(),
				GlobalConstants.isEditApiKeyPolicyNameDescriptionLabelDisplayed);
		assertTrue(apiKeyPage.isEditApiKeyExpiryDateLabelDisplayed(), GlobalConstants.isEditApiKeyExpiryDateLabelDisplayed);

		assertTrue(apiKeyPage.isEditApiKeyPartnerIdFieldReadOnly(), GlobalConstants.isEditApiKeyPartnerIdFieldReadOnly);
		assertTrue(apiKeyPage.isEditApiKeyPartnerTypeFieldReadOnly(), GlobalConstants.isEditApiKeyPartnerTypeFieldReadOnly);
		assertTrue(apiKeyPage.isEditApiKeyPolicyGroupFieldReadOnly(), GlobalConstants.isEditApiKeyPolicyGroupFieldReadOnly);
		assertTrue(apiKeyPage.isEditApiKeyPolicyNameFieldReadOnly(), GlobalConstants.isEditApiKeyPolicyNameFieldReadOnly);
		assertTrue(apiKeyPage.isEditApiKeyPolicyGroupDescriptionFieldReadOnly(),
				GlobalConstants.isEditApiKeyPolicyGroupDescriptionFieldReadOnly);
		assertTrue(apiKeyPage.isEditApiKeyPolicyNameDescriptionFieldReadOnly(),
				GlobalConstants.isEditApiKeyPolicyNameDescriptionFieldReadOnly);
	}

	@Test(priority = 7, description = "Verify Expiry Date calendar restricts to future dates", dependsOnMethods = "verifyEditExpiryDatePageDisplaysCorrectly")
	public void verifyExpiryDateCalendarBehavior() {
		navigateToEditExpiryPageForActiveApiKey();

		assertTrue(apiKeyPage.isEditApiKeyExpiryDateFieldEnabled(), GlobalConstants.isEditApiKeyExpiryDateFieldEnabled);

		String originalExpiryDate = apiKeyPage.getEditApiKeyExpiryDateValue();
		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		assertTrue(apiKeyPage.isEditApiKeyExpiryDateCalendarDisplayed(), GlobalConstants.isEditApiKeyExpiryDateCalendarDisplayed);

		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		assertNotEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), originalExpiryDate,
				GlobalConstants.isEditApiKeyExpiryDateSelectable);

		// Today is before minDate and gets rejected, so enter tomorrow to bring the current month into view
		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.enterEditApiKeyExpiryDate(PmpTestUtil.tomorrowDate);
		assertTrue(apiKeyPage.isTodayDisabledInEditApiKeyExpiryDateCalendar(),
				GlobalConstants.isTodayDisabledInEditApiKeyExpiryDateCalendar);
		assertTrue(apiKeyPage.getDisabledDayCountInEditApiKeyExpiryDateCalendar() > 1,
				GlobalConstants.isPastDatesDisabledInEditApiKeyExpiryDateCalendar);

		String beforeClick = apiKeyPage.getEditApiKeyExpiryDateValue();
		apiKeyPage.clickOnDay1InEditApiKeyExpiryDateCalendar();
		assertEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), beforeClick,
				GlobalConstants.isPastDatesDisabledInEditApiKeyExpiryDateCalendar);

		// Clearing the field makes the picker fall back to today, so check the past date was rejected
		// rather than expecting the previous value back
		String pastDate = LocalDate.now().minusDays(3).format(PmpTestUtil.dateFormatter);
		apiKeyPage.enterEditApiKeyExpiryDate(pastDate);
		apiKeyPage.blurEditApiKeyExpiryDateField();
		assertTrue(!apiKeyPage.isEditApiKeySubmitButtonEnabled(),
				GlobalConstants.isPastDateRejectedOnEditApiKeyExpiryDate);
		assertNotEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), pastDate,
				GlobalConstants.isPastDateRejectedOnEditApiKeyExpiryDate);

		// The calendar is now on the current month, where day 15 may already be past, so use a date two months out
		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.enterEditApiKeyExpiryDate(PmpTestUtil.nextMonth24thDate);
		String displayedValue = apiKeyPage.getEditApiKeyExpiryDateValue();
		assertEquals(displayedValue, PmpTestUtil.nextMonth24thDate,
				GlobalConstants.isFutureDateSelectableInEditApiKeyExpiryDateCalendar);
		assertTrue(displayedValue.matches("(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01])/\\d{4}"),
				GlobalConstants.isEditApiKeyExpiryDateDisplayedInCorrectFormat);
	}

	@Test(priority = 8, description = "Verify Cancel and Submit button behavior on Edit API Key page", dependsOnMethods = "verifyExpiryDateCalendarBehavior")
	public void verifyCancelAndSubmitButtonBehavior() {
		navigateToEditExpiryPageForActiveApiKey();

		assertTrue(apiKeyPage.isEditApiKeyCancelButtonDisplayed(), GlobalConstants.isEditApiKeyCancelButtonDisplayed);
		assertTrue(apiKeyPage.isEditApiKeySubmitButtonDisplayed(), GlobalConstants.isEditApiKeySubmitButtonDisplayed);
		assertTrue(apiKeyPage.isEditApiKeyCancelButtonEnabled(), GlobalConstants.isEditApiKeyCancelButtonEnabled);

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

		apiKeyPage.clickOnActionButton();
		apiKeyPage.clickOnEditExpiryDateButton();
		assertTrue(apiKeyPage.isEditApiKeyExpiryPageDisplayed(), GlobalConstants.isEditApiKeyExpiryPageDisplayed);
		assertEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), originalExpiryDate,
				GlobalConstants.isEditApiKeyCancelDiscardsChanges);

		apiKeyPage.clearEditApiKeyExpiryDateField();
		assertTrue(!apiKeyPage.isEditApiKeySubmitButtonEnabled(), GlobalConstants.isEditApiKeySubmitButtonDisabledWhenEmpty);

		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.clickOnDay1InEditApiKeyExpiryDateCalendar();
		String newExpiryDate = apiKeyPage.getEditApiKeyExpiryDateValue();
		assertNotEquals(newExpiryDate, originalExpiryDate, GlobalConstants.isEditApiKeyExpiryDateSelectable);
		assertTrue(apiKeyPage.isEditApiKeySubmitButtonEnabled(), GlobalConstants.isEditApiKeySubmitButtonEnabled);

		apiKeyPage.clickOnEditApiKeySubmitButton();
		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);
		apiKeyPage.clickOnEditApiKeyConfirmationGoBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);

		apiKeyPage.clickOnActionButton();
		apiKeyPage.clickOnEditExpiryDateButton();
		assertTrue(apiKeyPage.isEditApiKeyExpiryPageDisplayed(), GlobalConstants.isEditApiKeyExpiryPageDisplayed);
		assertEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), newExpiryDate,
				GlobalConstants.isEditApiKeyExpiryDateUpdatedSuccessfully);
	}

	@Test(priority = 9, description = "Verify confirmation popup, Undo Changes, and post-submit navigation", dependsOnMethods = "verifyCancelAndSubmitButtonBehavior")
	public void verifyExpiryDateConfirmationAndUndoBehavior() {
		navigateToEditExpiryPageForActiveApiKey();

		String apiKeyName = apiKeyPage.getEditApiKeyName();
		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		String newExpiryDateRaw = apiKeyPage.getEditApiKeyExpiryDateValue();
		assertTrue(apiKeyPage.isEditApiKeySubmitButtonEnabled(), GlobalConstants.isEditApiKeySubmitButtonEnabled);

		apiKeyPage.clickOnEditApiKeySubmitButton();
		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);
		assertEquals(apiKeyPage.getEditApiKeyConfirmationHeaderText(),
				"Expiry date for API key " + apiKeyName + " has been updated successfully.",
				GlobalConstants.isEditApiKeyConfirmationMessageTextCorrect);

		apiKeyPage.clickOnEditApiKeyConfirmationGoBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);

		// Go Back reloads the list unfiltered and newest first, so filter to the edited key again
		apiKeyPage.filterApiKeysByPartnerIdAndName(GlobalConstants.AUTH_PARTNER_ID, GlobalConstants.ACTIVATE_ADMINAPIKEY);

		LocalDate newExpiryDate = LocalDate.parse(newExpiryDateRaw, PmpTestUtil.dateFormatter);
		String expectedListFormat = newExpiryDate.format(PmpTestUtil.nonZeroPadderDateFormatter);
		assertEquals(apiKeyPage.getExpirationDateFromListWhenUpdated(ADMIN_VIEW, expectedListFormat), expectedListFormat,
				GlobalConstants.isUpdatedExpiryDateReflectedInList);

		apiKeyPage.clickOnActionButton();
		apiKeyPage.clickOnEditExpiryDateButton();
		assertTrue(apiKeyPage.isEditApiKeyExpiryPageDisplayed(), GlobalConstants.isEditApiKeyExpiryPageDisplayed);

		String originalExpiryDate = apiKeyPage.getEditApiKeyExpiryDateValue();
		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.clickOnDay1InEditApiKeyExpiryDateCalendar();
		assertNotEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), originalExpiryDate,
				GlobalConstants.isEditApiKeyExpiryDateSelectable);
		assertTrue(apiKeyPage.isEditApiKeyUndoChangesButtonEnabled(), GlobalConstants.isEditApiKeyUndoChangesButtonEnabled);

		apiKeyPage.clickOnEditApiKeyUndoChangesButton();
		assertEquals(apiKeyPage.getEditApiKeyExpiryDateValue(), originalExpiryDate,
				GlobalConstants.isEditApiKeyUndoChangesResetsExpiryDate);
		assertTrue(!apiKeyPage.isEditApiKeySubmitButtonEnabled(), GlobalConstants.isEditApiKeySubmitButtonDisabledAfterUndo);

		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		apiKeyPage.clickOnEditApiKeySubmitButton();
		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);

		apiKeyPage.clickOnEditApiKeyConfirmationHomeButton();
		dashboardPage = new DashboardPage(driver);
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
	}

	@Test(priority = 10, description = "Verify Action Menu and View option for an Active API Key", dependsOnMethods = "verifyExpiryDateConfirmationAndUndoBehavior")
	public void verifyActionMenuAndViewOptionForActiveApiKey() {
		navigateToActiveApiKeyRow();

		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		assertTrue(apiKeyPage.isDeactivateButtonDisplayed(), GlobalConstants.isDeactivateButtonDisplayed);
		assertTrue(apiKeyPage.isEditExpiryDateButtonDisplayed(), GlobalConstants.isEditExpiryDateButtonDisplayed);
		assertTrue(apiKeyPage.isEditExpiryDateButtonEnabled(), GlobalConstants.isEditExpiryDateButtonEnabled);

		apiKeyPage.clickOnViewButton();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);

		apiKeyPage.clickOnViewApiKeyBackButton();
		assertTrue(apiKeyPage.isApiKeyListViewDisplayed(), GlobalConstants.isApiKeyListViewDisplayed);
	}

	@Test(priority = 11, description = "Verify Deactivate option triggers the deactivation flow", dependsOnMethods = "verifyActionMenuAndViewOptionForActiveApiKey")
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

	@Test(priority = 12, description = "Verify Deactivate option completes deactivation", dependsOnMethods = "verifyDeactivateOptionTriggersDeactivationFlow")
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
		oidcClientPage = dashboardPage.clickOnAdminAuthenticationServices();
		oidcClientPage.clickOnApiKeyTab();

		apiKeyPage.filterApiKeysByPartnerIdAndName(GlobalConstants.AUTH_PARTNER_ID, throwawayKeyName);
		assertTrue(apiKeyPage.isApiKeyItem1Displayed(), GlobalConstants.isApiKeyItem1Displayed);

		apiKeyPage.clickOnActionButton();
		apiKeyPage.clickOnApiKeyDeactivateButton();
		assertTrue(apiKeyPage.isApiKeyInAdminDeactivatePopupDisplayed(), GlobalConstants.isApiKeyDeactivatePopupDisplayed);

		apiKeyPage.clickOnDeactivateSubmitButton();
		assertTrue(apiKeyPage.isApiKeyStatusDeactivatedDisplayed(), GlobalConstants.isApiKeyStatusDeactivatedDisplayed);
	}

	@Test(priority = 13, description = "Verify new API Key gets a default expiry date", dependsOnMethods = "verifyDeactivateOptionCompletesDeactivationForThrowawayKey")
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

	@Test(priority = 14, description = "Verify Edit API Key flow in the configured login language", dependsOnMethods = "verifyNewApiKeyReceivesDefaultExpiryDate")
	public void verifyEditApiKeyFlowDisplayedInLoginLanguage() {
		String loginLanguage = ConfigManager.getloginlang();
		boolean isRtlLanguage = "ara".equalsIgnoreCase(loginLanguage);
		ApiKeyPage.init(loginLanguage);

		dashboardPage = new DashboardPage(driver);
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.selectLanguage(loginLanguage);
		loginPage.login(userid, password);

		dashboardPage = new DashboardPage(driver);
		// Wait for the dashboard first, so the RTL check reads the portal and not the Keycloak page being left
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
		if (isRtlLanguage) {
			assertTrue(dashboardPage.isPageDirectionRtl(), GlobalConstants.isRtlLayoutAppliedForArabic);
		}

		oidcClientPage = dashboardPage.clickOnAdminAuthenticationServices();
		oidcClientPage.clickOnApiKeyTab();
		apiKeyPage = new ApiKeyPage(driver);

		apiKeyPage.filterApiKeysByPartnerIdAndName(GlobalConstants.AUTH_PARTNER_ID, GlobalConstants.ACTIVATE_ADMINAPIKEY);
		assertTrue(apiKeyPage.isApiKeyItem1Displayed(), GlobalConstants.isApiKeyItem1Displayed);

		apiKeyPage.clickOnActionButton();
		assertLocalizedTextEquals(apiKeyPage.getViewButtonText(), ApiKeyPage.API_KEY_ACTION_VIEW_OPTION_TEXT,
				GlobalConstants.isApiKeyActionMenuTextDisplayedInLoginLanguage);
		assertLocalizedTextEquals(apiKeyPage.getEditExpiryDateButtonText(), ApiKeyPage.API_KEY_ACTION_EDIT_EXPIRY_OPTION_TEXT,
				GlobalConstants.isApiKeyActionMenuTextDisplayedInLoginLanguage);
		assertLocalizedTextEquals(apiKeyPage.getDeactivateButtonText(), ApiKeyPage.API_KEY_ACTION_DEACTIVATE_OPTION_TEXT,
				GlobalConstants.isApiKeyActionMenuTextDisplayedInLoginLanguage);

		apiKeyPage.clickOnEditExpiryDateButton();
		assertTrue(apiKeyPage.isEditApiKeyExpiryPageDisplayed(), GlobalConstants.isEditApiKeyExpiryPageDisplayed);
		assertLocalizedTextEquals(apiKeyPage.getEditApiKeyExpiryPageTitle(), ApiKeyPage.API_KEY_EDIT_EXPIRY_PAGE_TITLE,
				GlobalConstants.isEditApiKeyPageTextDisplayedInLoginLanguage);
		assertLocalizedTextEquals(apiKeyPage.getEditApiKeyPartnerIdLabelText(), ApiKeyPage.API_KEY_PARTNER_ID_LABEL,
				GlobalConstants.isEditApiKeyPageTextDisplayedInLoginLanguage);
		assertLocalizedTextEquals(apiKeyPage.getEditApiKeyPartnerTypeLabelText(), ApiKeyPage.API_KEY_PARTNER_TYPE_LABEL,
				GlobalConstants.isEditApiKeyPageTextDisplayedInLoginLanguage);
		assertLocalizedTextEquals(apiKeyPage.getEditApiKeyPolicyGroupLabelText(), ApiKeyPage.API_KEY_POLICY_GROUP_LABEL,
				GlobalConstants.isEditApiKeyPageTextDisplayedInLoginLanguage);
		assertLocalizedTextEquals(apiKeyPage.getEditApiKeyPolicyNameLabelText(), ApiKeyPage.API_KEY_POLICY_NAME_LABEL,
				GlobalConstants.isEditApiKeyPageTextDisplayedInLoginLanguage);
		assertLocalizedTextEquals(apiKeyPage.getEditApiKeyPolicyGroupDescriptionLabelText(),
				ApiKeyPage.API_KEY_POLICY_GROUP_DESCRIPTION_LABEL, GlobalConstants.isEditApiKeyPageTextDisplayedInLoginLanguage);
		assertLocalizedTextEquals(apiKeyPage.getEditApiKeyPolicyNameDescriptionLabelText(),
				ApiKeyPage.API_KEY_POLICY_NAME_DESCRIPTION_LABEL, GlobalConstants.isEditApiKeyPageTextDisplayedInLoginLanguage);
		assertLocalizedTextEquals(apiKeyPage.getEditApiKeyExpiryDateLabelText(), ApiKeyPage.API_KEY_EXPIRY_DATE_LABEL,
				GlobalConstants.isEditApiKeyPageTextDisplayedInLoginLanguage);
		assertLocalizedTextEquals(apiKeyPage.getEditApiKeyUndoChangesButtonText(), ApiKeyPage.API_KEY_UNDO_CHANGES_BTN_TEXT,
				GlobalConstants.isEditApiKeyPageTextDisplayedInLoginLanguage);
		assertLocalizedTextEquals(apiKeyPage.getEditApiKeyCancelButtonText(), ApiKeyPage.API_KEY_CANCEL_BTN_TEXT,
				GlobalConstants.isEditApiKeyPageTextDisplayedInLoginLanguage);
		assertLocalizedTextEquals(apiKeyPage.getEditApiKeySubmitButtonText(), ApiKeyPage.API_KEY_SUBMIT_BTN_TEXT,
				GlobalConstants.isEditApiKeyPageTextDisplayedInLoginLanguage);
		if (isRtlLanguage) {
			assertTrue(apiKeyPage.isPageDirectionRtl(), GlobalConstants.isRtlLayoutAppliedForArabic);
		}

		String apiKeyName = apiKeyPage.getEditApiKeyName();
		apiKeyPage.clickOnEditApiKeyExpiryDateField();
		apiKeyPage.selectDay15InEditApiKeyExpiryDateCalendar();
		apiKeyPage.clickOnEditApiKeySubmitButton();

		assertTrue(apiKeyPage.isEditApiKeyConfirmationHeaderDisplayed(), GlobalConstants.isEditApiKeyConfirmationHeaderDisplayed);
		String expectedMessage = ApiKeyPage.API_KEY_CONFIRMATION_MESSAGE_TEMPLATE.replace("{0}", apiKeyName);
		assertLocalizedTextEquals(apiKeyPage.getEditApiKeyConfirmationHeaderText(), expectedMessage,
				GlobalConstants.isEditApiKeyPageTextDisplayedInLoginLanguage);
		assertLocalizedTextEquals(apiKeyPage.getEditApiKeyConfirmationGoBackButtonText(), ApiKeyPage.API_KEY_CONFIRMATION_GO_BACK_BTN_TEXT,
				GlobalConstants.isEditApiKeyPageTextDisplayedInLoginLanguage);
		assertLocalizedTextEquals(apiKeyPage.getEditApiKeyConfirmationHomeButtonText(), ApiKeyPage.API_KEY_CONFIRMATION_HOME_BTN_TEXT,
				GlobalConstants.isEditApiKeyPageTextDisplayedInLoginLanguage);

	}

	private void navigateToApiKeyTab() {
		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);
		oidcClientPage = dashboardPage.clickOnAdminAuthenticationServices();
		oidcClientPage.clickOnApiKeyTab();
	}

	private void navigateToDeactivatedApiKeyRow() {
		navigateToApiKeyTab();
		apiKeyPage.filterApiKeysByPartnerIdAndName(GlobalConstants.AUTH_PARTNER_ID, GlobalConstants.DEACTIVATE_APIKEY);
	}

	private void navigateToActiveApiKeyRow() {
		navigateToApiKeyTab();
		apiKeyPage.filterApiKeysByPartnerIdAndName(GlobalConstants.AUTH_PARTNER_ID, GlobalConstants.ACTIVATE_ADMINAPIKEY);
	}

	private void navigateToEditExpiryPageForActiveApiKey() {
		navigateToActiveApiKeyRow();
		apiKeyPage.clickOnActionButton();
		apiKeyPage.clickOnEditExpiryDateButton();
	}

	private void switchLoginTo(String username, String pwd) {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(username);
		loginPage.enterPassword(pwd);
		loginPage.clickOnLoginButton();
	}

	private static void assertLocalizedTextEquals(String actual, String expected, String message) {
		assertEquals(actual == null ? null : Normalizer.normalize(actual, Normalizer.Form.NFC),
				expected == null ? null : Normalizer.normalize(expected, Normalizer.Form.NFC), message);
	}
}
