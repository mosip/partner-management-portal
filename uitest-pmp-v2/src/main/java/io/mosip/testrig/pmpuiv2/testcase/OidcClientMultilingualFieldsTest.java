package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.OidcClientPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PolicyCreationForAuthPartner" }, groups = { "OidcClientMultilingualFieldsTest" })
public class OidcClientMultilingualFieldsTest extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;

	@Test(priority = 1, description = "Verify login and Authentication Services card on the PMS home screen")
	public void loginAndDashboardChecks() {
		loginAsAuthPartner();

		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
	}

	@Test(priority = 2, description = "Verify Create OIDC Client form layout - sections, field labels, and Grant Type", dependsOnMethods = "loginAndDashboardChecks")
	public void createFormLayoutChecks() {
		OidcClientPage oidcClientPage = openCreateOidcClientForm();

		assertTrue(oidcClientPage.isPrimaryInformationSectionDisplayed(),
				GlobalConstants.isPrimaryInformationSectionDisplayed);
		assertTrue(oidcClientPage.isAdditionalInformationSectionDisplayed(),
				GlobalConstants.isAdditionalInformationSectionDisplayed);

		oidcClientPage.clickOnPrimaryInformationSectionHeader();
		assertFalse(oidcClientPage.isPartnerIdDropdownDisplayed(),
				GlobalConstants.isPrimaryInformationSectionCollapsible);
		oidcClientPage.clickOnPrimaryInformationSectionHeader();
		assertTrue(oidcClientPage.isPartnerIdDropdownDisplayed(),
				GlobalConstants.isPrimaryInformationSectionCollapsible);

		oidcClientPage.clickOnAdditionalInformationSectionHeader();
		assertTrue(oidcClientPage.isUserInfoResponseTypeDropdownDisplayed(),
				GlobalConstants.isAdditionalInformationSectionCollapsible);
		oidcClientPage.clickOnAdditionalInformationSectionHeader();
		assertFalse(oidcClientPage.isUserInfoResponseTypeDropdownDisplayed(),
				GlobalConstants.isAdditionalInformationSectionCollapsible);

		assertTrue(oidcClientPage.isPartnerIdFieldLabelDisplayed(), GlobalConstants.isPartnerIdFieldLabelDisplayed);
		assertTrue(oidcClientPage.isPartnerTypeFieldDisplayed(), GlobalConstants.isPartnerTypeFieldDisplayed);
		assertTrue(oidcClientPage.isPolicyGroupFieldDisplayed(), GlobalConstants.isPolicyGroupFieldDisplayed);
		assertTrue(oidcClientPage.isPolicyNameFieldLabelDisplayed(), GlobalConstants.isPolicyNameFieldLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientNameFieldLabelDisplayed(),
				GlobalConstants.isOidcClientNameFieldLabelDisplayed);
		assertTrue(oidcClientPage.isPublicKeyFieldLabelDisplayed(), GlobalConstants.isPublicKeyFieldLabelDisplayed);
		assertTrue(oidcClientPage.isLogoUriFieldLabelDisplayed(), GlobalConstants.isLogoUriFieldLabelDisplayed);
		assertTrue(oidcClientPage.isRedirectUriFieldLabelDisplayed(), GlobalConstants.isRedirectUriFieldLabelDisplayed);
		assertTrue(oidcClientPage.isGrantTypeFieldLabelDisplayed(), GlobalConstants.isGrantTypeFieldLabelDisplayed);

		assertTrue(oidcClientPage.isAuthorizationCodeTextDisplayed(),
				GlobalConstants.isGrantTypePreSelectedAsAuthorizationCode);
		oidcClientPage.clickOnGrantTypeDropdown();
		assertTrue(oidcClientPage.isAuthorizationCodeTextDisplayed(), GlobalConstants.isGrantTypeNonEditable);
	}

	@Test(priority = 3, description = "Verify OIDC Client Name multilingual section - Add New, language options, defaults", dependsOnMethods = "createFormLayoutChecks")
	public void multilingualSectionChecks() {
		OidcClientPage oidcClientPage = openCreateOidcClientForm();

		assertTrue(oidcClientPage.isAddClientNameLanguageButtonDisplayed(),
				GlobalConstants.isAddNewLanguageButtonVisibleAndClickable);
		oidcClientPage.clickOnAddClientNameLanguageButton();

		assertTrue(oidcClientPage.getClientNameLanguageRow1SelectedText().equalsIgnoreCase("English"),
				GlobalConstants.isLanguageDropdownDefaultsToEnglish);
		assertTrue(
				oidcClientPage.getClientNameLanguageRow1Placeholder()
						.equals("Please enter the OIDC client name in English"),
				GlobalConstants.isClientNameLanguageRow1PlaceholderCorrect);

		oidcClientPage.clickOnClientNameLanguageDropdownRow1();
		assertTrue(oidcClientPage.isEnglishLanguageOptionDisplayed(), GlobalConstants.isConfiguredLanguageCodesInDropdown);
		assertTrue(oidcClientPage.isFrenchLanguageOptionDisplayed(), GlobalConstants.isConfiguredLanguageCodesInDropdown);
		assertTrue(oidcClientPage.isArabicLanguageOptionDisplayed(), GlobalConstants.isConfiguredLanguageCodesInDropdown);
		oidcClientPage.clickOnClientNameLanguageDropdownRow1();

		oidcClientPage.clickOnAddClientNameLanguageButton();
		oidcClientPage.clickOnClientNameLanguageDropdownRow2();
		assertFalse(oidcClientPage.isEnglishLanguageOptionRow2Displayed(),
				GlobalConstants.isRow2ExcludesAlreadySelectedLanguage);
		assertTrue(oidcClientPage.isFrenchLanguageOptionRow2Displayed(),
				GlobalConstants.isRow2ExcludesAlreadySelectedLanguage);
	}

	@Test(priority = 4, description = "Verify Forgot Password / SignUp Banner toggle defaults and info tooltip text", dependsOnMethods = "multilingualSectionChecks")
	public void additionalInfoChecks() {
		OidcClientPage oidcClientPage = openCreateOidcClientForm();

		oidcClientPage.clickOnAdditionalInformationSectionHeader();

		assertTrue(oidcClientPage.isForgotPasswordBannerToggleOn(), GlobalConstants.isForgotPasswordBannerToggleOnByDefault);
		assertTrue(oidcClientPage.isSignUpBannerToggleOn(), GlobalConstants.isSignUpBannerToggleOnByDefault);

		oidcClientPage.clickOnForgotPasswordBannerInfoIcon();
		assertTrue(
				oidcClientPage.getForgotPasswordBannerInfoTooltipText().equals(
						"Enable this option to display 'Forgot Password' link on the eSignet authentication screen."),
				GlobalConstants.isForgotPasswordBannerInfoTooltipCorrect);

		assertTrue(oidcClientPage.getConsentExpiryValue().equals("10"), GlobalConstants.isConsentExpiryDefaultValueTen);

		oidcClientPage.enterConsentExpiryDuration("15");
		assertTrue(oidcClientPage.getConsentExpiryValue().equals("15"), GlobalConstants.isConsentExpiryAcceptsPositiveInteger);

		oidcClientPage.clickOnConsentExpiryInfoIcon();
		assertTrue(
				oidcClientPage.getConsentExpiryInfoTooltipText()
						.equals("Duration (in minutes) for which user consent remains valid before re-prompt."),
				GlobalConstants.isConsentExpiryInfoTooltipCorrect);

		assertTrue(oidcClientPage.isUserInfoResponseTypeDropdownDisplayed(),
				GlobalConstants.isUserInfoResponseTypeDropdownDisplayed);
		assertTrue(oidcClientPage.getUserInfoResponseTypeDropdownPlaceholder().equals("Select user info response type"),
				GlobalConstants.isUserInfoResponseTypePlaceholderDisplayed);

		oidcClientPage.clickOnUserInfoResponseTypeDropdown();
		assertTrue(oidcClientPage.isJweOptionDisplayed(), GlobalConstants.isUserInfoResponseTypeOptionsCorrect);
		assertTrue(oidcClientPage.isJwsOptionDisplayed(), GlobalConstants.isUserInfoResponseTypeOptionsCorrect);
		oidcClientPage.clickOnUserInfoResponseTypeDropdown();

		oidcClientPage.clickOnUserInfoResponseTypeInfoIcon();
		assertTrue(
				oidcClientPage.getUserInfoResponseTypeInfoTooltipText()
						.equals("Defines the format in which user info will be returned."),
				GlobalConstants.isUserInfoResponseTypeInfoTooltipCorrect);

		assertTrue(oidcClientPage.isPurposeTypeDropdownDisplayed(), GlobalConstants.isPurposeTypeDropdownDisplayed);
		assertTrue(oidcClientPage.getPurposeTypeDropdownPlaceholder().equals("Select Purpose Type"),
				GlobalConstants.isPurposeTypePlaceholderDisplayed);

		oidcClientPage.clickOnPurposeTypeDropdown();
		assertTrue(oidcClientPage.isLoginPurposeOptionDisplayed(), GlobalConstants.isPurposeTypeOptionsCorrect);
		assertTrue(oidcClientPage.isLinkPurposeOptionDisplayed(), GlobalConstants.isPurposeTypeOptionsCorrect);
		assertTrue(oidcClientPage.isVerifyPurposeOptionDisplayed(), GlobalConstants.isPurposeTypeOptionsCorrect);
		oidcClientPage.clickOnPurposeTypeDropdown();
	}

	@Test(priority = 5, description = "Verify Purpose Title/Subtitle fields only appear after selecting Purpose Type, and their multilingual row/language dropdown behavior", dependsOnMethods = "additionalInfoChecks")
	public void purposeTitleSubtitleChecks() {
		OidcClientPage oidcClientPage = openCreateOidcClientForm();
		oidcClientPage.clickOnAdditionalInformationSectionHeader();

		assertFalse(oidcClientPage.isPurposeTitleLabelDisplayed(),
				GlobalConstants.isPurposeTitleSubtitleHiddenWhenNoPurposeTypeSelected);
		assertFalse(oidcClientPage.isPurposeSubtitleLabelDisplayed(),
				GlobalConstants.isPurposeTitleSubtitleHiddenWhenNoPurposeTypeSelected);

		oidcClientPage.clickOnPurposeTypeDropdown();
		oidcClientPage.clickOnLoginPurposeOption();

		assertTrue(oidcClientPage.isPurposeTitleLabelDisplayed(),
				GlobalConstants.isPurposeTitleSubtitleDisplayedWhenPurposeTypeSelected);
		assertTrue(oidcClientPage.isPurposeSubtitleLabelDisplayed(),
				GlobalConstants.isPurposeTitleSubtitleDisplayedWhenPurposeTypeSelected);

		assertFalse(oidcClientPage.isPurposeTitleLanguageDropdownDisplayed(),
				GlobalConstants.isNoPurposeTitleSubtitleRowsVisibleByDefault);
		assertFalse(oidcClientPage.isPurposeSubtitleLanguageDropdownDisplayed(),
				GlobalConstants.isNoPurposeTitleSubtitleRowsVisibleByDefault);

		oidcClientPage.clickOnAddPurposeTitleLanguageButton();
		assertTrue(oidcClientPage.isPurposeTitleLanguageDropdownDisplayed(),
				GlobalConstants.isNoPurposeTitleSubtitleRowsVisibleByDefault);

		oidcClientPage.clickOnAddPurposeSubtitleLanguageButton();
		assertTrue(oidcClientPage.isPurposeSubtitleLanguageDropdownDisplayed(),
				GlobalConstants.isNoPurposeTitleSubtitleRowsVisibleByDefault);

		oidcClientPage.clickOnPurposeTitleLanguageDropdown();
		assertTrue(oidcClientPage.isPurposeTitleLanguageDefaultOptionDisplayed(),
				GlobalConstants.isPurposeTitleLanguageDropdownContainsDefaultAndAllLanguages);
		assertTrue(oidcClientPage.isPurposeTitleLanguageEnglishOptionDisplayed(),
				GlobalConstants.isPurposeTitleLanguageDropdownContainsDefaultAndAllLanguages);
		assertTrue(oidcClientPage.isPurposeTitleLanguageFrenchOptionDisplayed(),
				GlobalConstants.isPurposeTitleLanguageDropdownContainsDefaultAndAllLanguages);
		assertTrue(oidcClientPage.isPurposeTitleLanguageArabicOptionDisplayed(),
				GlobalConstants.isPurposeTitleLanguageDropdownContainsDefaultAndAllLanguages);
		oidcClientPage.clickOnPurposeTitleLanguageDropdown();

		assertTrue(oidcClientPage.isPurposeTitleTextBoxDisplayed(), GlobalConstants.isPurposeTitleSubtitleTextBoxDisplayed);
		assertTrue(
				oidcClientPage.getPurposeTitleTextBoxPlaceholder()
						.equals("Enter the Purpose Title in your primary language."),
				GlobalConstants.isPurposeTitlePlaceholderCorrect);

		assertTrue(oidcClientPage.isPurposeSubtitleTextBoxDisplayed(), GlobalConstants.isPurposeTitleSubtitleTextBoxDisplayed);
		assertTrue(
				oidcClientPage.getPurposeSubtitleTextBoxPlaceholder()
						.equals("Enter the Purpose Subtitle in your primary language."),
				GlobalConstants.isPurposeSubtitlePlaceholderCorrect);

		oidcClientPage.clickOnAddPurposeTitleLanguageLink();
		oidcClientPage.clickOnPurposeTitleRow2LanguageDropdown();
		assertFalse(oidcClientPage.isPurposeTitleLanguageDefaultOptionDisplayed(),
				GlobalConstants.isAdditionalRowsExcludeAlreadyUsedLanguages);
		assertTrue(oidcClientPage.isPurposeTitleLanguageEnglishOptionDisplayed(),
				GlobalConstants.isAdditionalRowsExcludeAlreadyUsedLanguages);
		assertTrue(oidcClientPage.isPurposeTitleLanguageFrenchOptionDisplayed(),
				GlobalConstants.isAdditionalRowsExcludeAlreadyUsedLanguages);
		assertTrue(oidcClientPage.isPurposeTitleLanguageArabicOptionDisplayed(),
				GlobalConstants.isAdditionalRowsExcludeAlreadyUsedLanguages);
		oidcClientPage.clickOnPurposeTitleRow2LanguageDropdown();
	}

	@Test(priority = 6, description = "Verify Purpose Title and Purpose Subtitle are independently optional, and a blank multilingual row text blocks submit", dependsOnMethods = "purposeTitleSubtitleChecks")
	public void purposeTitleSubtitleOptionalityChecks() {
		OidcClientPage titleOnlyForm = openCreateOidcClientForm();
		fillMandatoryFieldsAndSelectLoginPurposeType(titleOnlyForm);
		titleOnlyForm.clickOnAddPurposeTitleLanguageButton();
		titleOnlyForm.enterPurposeTitleText(GlobalConstants.DEFAULT_POLICY + BaseClass.data);
		assertTrue(titleOnlyForm.isSubmitButtonEnabled(), GlobalConstants.isSubmitAllowedWithOnlyPurposeTitleFilled);

		OidcClientPage subtitleOnlyForm = openCreateOidcClientForm();
		fillMandatoryFieldsAndSelectLoginPurposeType(subtitleOnlyForm);
		subtitleOnlyForm.clickOnAddPurposeSubtitleLanguageButton();
		subtitleOnlyForm.enterPurposeSubtitleText(GlobalConstants.DEFAULT_POLICY + BaseClass.data);
		assertTrue(subtitleOnlyForm.isSubmitButtonEnabled(),
				GlobalConstants.isSubmitAllowedWithOnlyPurposeSubtitleFilled);

		OidcClientPage blankTextForm = openCreateOidcClientForm();
		fillMandatoryFieldsAndSelectLoginPurposeType(blankTextForm);
		blankTextForm.clickOnAddPurposeTitleLanguageButton();
		assertFalse(blankTextForm.isSubmitButtonEnabled(), GlobalConstants.isSubmitBlockedWhenLanguageRowTextEmpty);
	}

	private void fillMandatoryFieldsAndSelectLoginPurposeType(OidcClientPage oidcClientPage) {
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		oidcClientPage.enterNameOidcTextBox(GlobalConstants.DEFAULT_POLICY + BaseClass.data);
		oidcClientPage.enterPublicKeyTextBox(PmpTestUtil.generateJWKPublicKey());
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnAdditionalInformationSectionHeader();
		oidcClientPage.clickOnPurposeTypeDropdown();
		oidcClientPage.clickOnLoginPurposeOption();
	}

	@Test(priority = 7, description = "Verify Submit button stays disabled until every mandatory field is filled", dependsOnMethods = "purposeTitleSubtitleOptionalityChecks")
	public void submitValidationChecks() {
		OidcClientPage oidcClientPage = openCreateOidcClientForm();

		assertFalse(oidcClientPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitDisabledWhenPartnerIdEmpty);

		oidcClientPage.selectPartnerIdDropdown();
		assertFalse(oidcClientPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitDisabledWhenPolicyNameEmpty);

		assertFalse(oidcClientPage.getPartnerTypeContextText().isEmpty(),
				GlobalConstants.isPartnerTypeAutoPopulatedAndReadOnly);
		assertTrue(oidcClientPage.isPartnerTypeContextDisabled(), GlobalConstants.isPartnerTypeAutoPopulatedAndReadOnly);

		assertFalse(oidcClientPage.getPolicyGroupContextText().isEmpty(),
				GlobalConstants.isPolicyGroupAutoPopulatedAndReadOnly);
		assertTrue(oidcClientPage.isPolicyGroupContextDisabled(), GlobalConstants.isPolicyGroupAutoPopulatedAndReadOnly);

		oidcClientPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		oidcClientPage.enterNameOidcTextBox(GlobalConstants.DEFAULT_POLICY + BaseClass.data);
		assertFalse(oidcClientPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitDisabledWhenPublicKeyEmpty);

		oidcClientPage.enterPublicKeyTextBox(PmpTestUtil.generatePemPublicKey());
		assertTrue(oidcClientPage.isPublicKeyFormatErrorDisplayed(), GlobalConstants.isPublicKeyRejectedForNonJwkFormat);

		oidcClientPage.enterPublicKeyTextBox(PmpTestUtil.generateJWKPublicKey());
		assertFalse(oidcClientPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitDisabledWhenLogoUriEmpty);

		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		assertFalse(oidcClientPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitDisabledWhenRedirectUriEmpty);

		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		assertTrue(oidcClientPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabledAfterFillingForm);

		oidcClientPage.clickOnAdditionalInformationSectionHeader();
		assertTrue(oidcClientPage.getPurposeTypeDropdownPlaceholder().equals("Select Purpose Type"),
				GlobalConstants.isSubmitAllowedWithoutPurposeTypeSelected);
		assertTrue(oidcClientPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitAllowedWithoutPurposeTypeSelected);
	}

	@Test(priority = 8, description = "Verify partner can create an OIDC Client with required and optional details", dependsOnMethods = "submitValidationChecks")
	public void fillAndSubmitFlow() {
		OidcClientPage oidcClientPage = openCreateOidcClientForm();

		String oidcClientName = GlobalConstants.DEFAULT_POLICY + BaseClass.data;
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		oidcClientPage.enterNameOidcTextBox(oidcClientName);
		oidcClientPage.enterPublicKeyTextBox(PmpTestUtil.generateJWKPublicKey());
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());

		oidcClientPage.clickOnAddClientNameLanguageButton();
		oidcClientPage.enterClientNameForLanguageRow1(oidcClientName);

		oidcClientPage.clickOnAddClientNameLanguageButton();
		oidcClientPage.clickOnClientNameLanguageDropdownRow2();
		oidcClientPage.selectFrenchForClientNameLanguageRow2();
		assertFalse(oidcClientPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitDisabledWhenLanguageRowTextBlank);

		oidcClientPage.enterClientNameForLanguageRow2(oidcClientName);
		assertTrue(oidcClientPage.isClientNameLanguageRow2Displayed(), GlobalConstants.isMultipleLanguageRowsSupported);

		oidcClientPage.clickOnAdditionalInformationSectionHeader();
		oidcClientPage.clickOnForgotPasswordBannerToggle();
		oidcClientPage.clickOnSignUpBannerToggle();
		oidcClientPage.enterConsentExpiryDuration("15");

		assertTrue(oidcClientPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabledAfterFillingForm);
		oidcClientPage.clickOnSubmitButton();

		assertTrue(oidcClientPage.isOidcSubmittedSuccessfullyDisplayed(),
				GlobalConstants.isOidcSubmittedSuccessfullyDisplayed);
		assertTrue(
				oidcClientPage.getConfirmationDescriptionText()
						.equals("OIDC Client details has been successfully submitted."),
				GlobalConstants.isConfirmationDescriptionMessageCorrect);
		oidcClientPage.clickConfirmationGoBackButton();

		assertTrue(oidcClientPage.isOidcClientTabDisplayed(), GlobalConstants.isRedirectedToOidcClientListAfterSubmit);
		assertTrue(oidcClientPage.getFirstOidcClientRowName().contains(oidcClientName),
				GlobalConstants.isNewOidcClientNameAtTopOfList);
		assertTrue(oidcClientPage.getFirstOidcClientRowStatus().equalsIgnoreCase(GlobalConstants.PARTNER_STATUS_ACTIVE),
				GlobalConstants.isNewOidcClientStatusActive);
	}

	@Test(priority = 9, description = "Verify Partner creates an OIDC Client with multilingual Client Name, Purpose Title, and Purpose Subtitle", dependsOnMethods = "fillAndSubmitFlow")
	public void multilingualWithPurposeFieldsSubmitFlow() {
		OidcClientPage oidcClientPage = openCreateOidcClientForm();

		String oidcClientName = GlobalConstants.DEFAULT_POLICY + BaseClass.data;
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		oidcClientPage.enterNameOidcTextBox(oidcClientName);
		oidcClientPage.enterPublicKeyTextBox(PmpTestUtil.generateJWKPublicKey());
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());

		oidcClientPage.clickOnAddClientNameLanguageButton();
		oidcClientPage.enterClientNameForLanguageRow1(oidcClientName);
		oidcClientPage.clickOnAddClientNameLanguageButton();
		oidcClientPage.clickOnClientNameLanguageDropdownRow2();
		oidcClientPage.selectFrenchForClientNameLanguageRow2();
		oidcClientPage.enterClientNameForLanguageRow2(oidcClientName);

		oidcClientPage.clickOnAdditionalInformationSectionHeader();
		oidcClientPage.clickOnPurposeTypeDropdown();
		oidcClientPage.clickOnLoginPurposeOption();

		oidcClientPage.clickOnAddPurposeTitleLanguageButton();
		oidcClientPage.enterPurposeTitleText("Purpose Title " + BaseClass.data);

		oidcClientPage.clickOnAddPurposeSubtitleLanguageButton();
		oidcClientPage.enterPurposeSubtitleText("Purpose Subtitle " + BaseClass.data);

		assertTrue(oidcClientPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabledAfterFillingForm);
		oidcClientPage.clickOnSubmitButton();

		assertTrue(oidcClientPage.isOidcSubmittedSuccessfullyDisplayed(),
				GlobalConstants.isMultilingualWithPurposeFieldsSubmitSuccessful);
		oidcClientPage.clickConfirmationGoBackButton();

		assertTrue(oidcClientPage.getFirstOidcClientRowName().contains(oidcClientName),
				GlobalConstants.isMultilingualWithPurposeFieldsSubmitSuccessful);
	}

	@Test(priority = 10, description = "Verify existing backend integrations and validations remain unchanged when only standard mandatory fields are used", dependsOnMethods = "multilingualWithPurposeFieldsSubmitFlow")
	public void legacyOnlySubmitFlow() {
		OidcClientPage oidcClientPage = openCreateOidcClientForm();

		String oidcClientName = GlobalConstants.DEFAULT_POLICY + BaseClass.data;
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		oidcClientPage.enterNameOidcTextBox(oidcClientName);
		oidcClientPage.enterPublicKeyTextBox(PmpTestUtil.generateJWKPublicKey());
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());

		assertTrue(oidcClientPage.isSubmitButtonEnabled(), GlobalConstants.isLegacyOnlySubmitSuccessfulWithoutRegression);
		oidcClientPage.clickOnSubmitButton();

		assertTrue(oidcClientPage.isOidcSubmittedSuccessfullyDisplayed(),
				GlobalConstants.isLegacyOnlySubmitSuccessfulWithoutRegression);
		oidcClientPage.clickConfirmationGoBackButton();

		assertTrue(oidcClientPage.getFirstOidcClientRowName().contains(oidcClientName),
				GlobalConstants.isLegacyOnlySubmitSuccessfulWithoutRegression);
	}

	@Test(priority = 11, description = "Verify no language validation for multilingual text fields - non-matching language text is accepted", dependsOnMethods = "legacyOnlySubmitFlow")
	public void noLanguageValidationChecks() {
		OidcClientPage oidcClientPage = openCreateOidcClientForm();

		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		oidcClientPage.enterNameOidcTextBox(GlobalConstants.DEFAULT_POLICY + BaseClass.data);
		oidcClientPage.enterPublicKeyTextBox(PmpTestUtil.generateJWKPublicKey());
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());

		oidcClientPage.clickOnAddClientNameLanguageButton();
		assertTrue(oidcClientPage.getClientNameLanguageRow1SelectedText().equalsIgnoreCase("English"),
				GlobalConstants.isNoLanguageValidationForMultilingualText);
		oidcClientPage.enterClientNameForLanguageRow1("Hola Mundo " + BaseClass.data);

		assertTrue(oidcClientPage.isSubmitButtonEnabled(), GlobalConstants.isNoLanguageValidationForMultilingualText);
	}

	private OidcClientPage openCreateOidcClientForm() {
		loginAsAuthPartner();

		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		assertTrue(oidcClientPage.isOidcClientTabDisplayed(), GlobalConstants.isOidcClientTabDisplayed);
		oidcClientPage.clickOnCreateOidcClientButton();
		return oidcClientPage;
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
