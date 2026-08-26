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
	}

	@Test(priority = 5, description = "Verify Submit button stays disabled until every mandatory field is filled", dependsOnMethods = "additionalInfoChecks")
	public void submitValidationChecks() {
		OidcClientPage oidcClientPage = openCreateOidcClientForm();

		assertFalse(oidcClientPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitDisabledWhenPartnerIdEmpty);

		oidcClientPage.selectPartnerIdDropdown();
		assertFalse(oidcClientPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitDisabledWhenPolicyNameEmpty);

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
	}

	@Test(priority = 6, description = "Verify partner can create an OIDC Client with required and optional details", dependsOnMethods = "submitValidationChecks")
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
		oidcClientPage.clickConfirmationGoBackButton();

		assertTrue(oidcClientPage.isOidcClientTabDisplayed(), GlobalConstants.isRedirectedToOidcClientListAfterSubmit);
		assertTrue(oidcClientPage.getFirstOidcClientRowName().contains(oidcClientName),
				GlobalConstants.isNewOidcClientNameAtTopOfList);
		assertTrue(oidcClientPage.getFirstOidcClientRowStatus().equalsIgnoreCase(GlobalConstants.PARTNER_STATUS_ACTIVE),
				GlobalConstants.isNewOidcClientStatusActive);
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
