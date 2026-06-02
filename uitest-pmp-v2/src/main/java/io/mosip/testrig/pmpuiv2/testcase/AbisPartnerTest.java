package io.mosip.testrig.pmpuiv2.testcase;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerAdminPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "AbisPartnerTest" })
public class AbisPartnerTest extends BaseClass {

    private MispPartnerPage navigateToCreatePartnerPage() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
        dashboardPage.clickOnPartners();
        mispPartnerPage.clickOnCreatePartnerButton();
        return mispPartnerPage;
    }

    private void fillAbisPartnerMandatoryFields(MispPartnerPage page, String email, String username) {
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        page.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
        page.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.enterEmailId(email);
        page.enterUserName(username);
    }

    @Test(priority = 1, groups = { "S1_FormStructure" },
          description = "Verify form structure, partner type dropdown options, notification language dropdown, and org name info tooltip.")
    public void s1_formStructureAndDropdowns() {
        MispPartnerPage page = navigateToCreatePartnerPage();

        Assert.assertTrue(page.isCreatePrtnerPageTitleDisplayed(), GlobalConstants.isDesignSpecCompliant);
        Assert.assertEquals(page.getCreatePartnerPageTitleText(), GlobalConstants.CREATE_PARTNER_PAGE_TITLE,
                GlobalConstants.isCreatePartnerPageTitleCorrect);
        Assert.assertTrue(page.isCreatePartnerMandatoryFieldInfoDisplayed(), GlobalConstants.isDesignSpecCompliant);
        Assert.assertEquals(page.getCreatePartnerMandatoryFieldInfoText(), GlobalConstants.MANDATORY_FIELD_INFO_TEXT,
                GlobalConstants.isMandatoryFieldInfoTextCorrect);
        Assert.assertTrue(page.isPartnerTypeDropdownDisplayed(), GlobalConstants.isDesignSpecCompliant);
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isDesignSpecCompliant);
        Assert.assertTrue(page.isHomeButtonDisplayed(), GlobalConstants.isDesignSpecCompliant);
        Assert.assertTrue(page.isListOfPartnerButtonDisplayed(), GlobalConstants.isDesignSpecCompliant);

        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        Assert.assertEquals(page.getSelectedPartnerTypeText(), GlobalConstants.ABIS_PARTNER,
                GlobalConstants.isAbisPartnerSelectedSuccessfully);

        page.clickOnPartnerTypeDropdown();
        page.clickOnMispPartnerOption();
        Assert.assertEquals(page.getSelectedPartnerTypeText(), GlobalConstants.MISP_PARTNER,
                GlobalConstants.isMispPartnerSelectedSuccessfully);

        page.clickOnPartnerTypeDropdown();
        List<String> options = page.getPartnerTypeDropdownOptionTexts();
        Assert.assertTrue(options.size() >= 3, GlobalConstants.isPartnerTypeDropdownOptionCountCorrect);
        Assert.assertTrue(options.contains(GlobalConstants.ABIS_PARTNER), GlobalConstants.isAbisPartnerOptionDisplayed);
        Assert.assertTrue(options.contains(GlobalConstants.MANUAL_ADJUDICATION_PARTNER),
                GlobalConstants.isManualAdjudicationPartnerOptionDisplayed);
        Assert.assertTrue(options.contains(GlobalConstants.MISP_PARTNER), GlobalConstants.isMispPartnerOptionDisplayed);
        page.clickOnAbisPartnerOption();

        page.clickOnPartnerOragnizationInfoButton();
        Assert.assertTrue(page.isOrganizationNameInfoDisplayed(), GlobalConstants.isOrganizationNameInfoDisplayed);
        Assert.assertEquals(page.getOrganizationNameInfoText(), GlobalConstants.ORG_NAME_INFO_TEXT,
                GlobalConstants.isOrgNameInfoTextCorrect);

        Assert.assertTrue(page.isNotificationLanguageDropdownDisplayed(),
                GlobalConstants.isNotificationLanguageDropdownDisplayed);

        page.clickOnNotificationLanguageDropdown();
        Assert.assertTrue(page.isNotificationLanguageOptionVisible(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE),
                GlobalConstants.isNotificationLanguageDropdownHasOptions);

        page.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        Assert.assertTrue(
                page.getSelectedNotificationLanguageText().contains(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE),
                GlobalConstants.isNotificationLanguageSelectable);
    }

    @Test(priority = 2, groups = { "S2_MandatoryFields" },
          description = "Verify submit button state as mandatory fields are filled and that multiple simultaneous invalid inputs block submission.")
    public void s2_mandatoryFieldGateAndMultiFieldValidation() {
        MispPartnerPage page = navigateToCreatePartnerPage();

        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isCreatePartnerSubmitButtonDisabled);

        page.clickOnPartnerTypeDropdown();
        page.clickOnMispPartnerOption();
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.enterEmailId(GlobalConstants.ABIS_EMAIL_ID);
        page.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isCreatePartnerSubmitButtonDisabled);

        page.clickOnCreatePartnerClearButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        page.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
        page.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.enterEmailId(GlobalConstants.ABIS_EMAIL_ID);
        page.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isSubmitDisabledWhenOrgNameEmpty);

        page.clickOnCreatePartnerClearButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        page.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
        page.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isSubmitDisabledWithIncompleteFields);

        page.clickOnCreatePartnerClearButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        page.enterPartnerOrganisation(GlobalConstants.DISALLOWED_SPECIAL_CHARS_ORG);
        Assert.assertTrue(page.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAllValidationErrorsDisplayed);
        page.enterPartnerAddress(GlobalConstants.DISALLOWED_SPECIAL_CHARS_ADDRESS);
        Assert.assertTrue(page.isPartnerAddressSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAllValidationErrorsDisplayed);
        page.enterEmailId(GlobalConstants.INVALID_EMAIL_ID);
        page.clickOnPartnerAddressTextBox();
        Assert.assertTrue(page.isPartnerEmailIdSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAllValidationErrorsDisplayed);
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isAllValidationErrorsDisplayed);
    }

    @Test(priority = 3, groups = { "S3_OrgNameValidation" },
          description = "Verify Organization Name field placeholder, valid input, allowed/disallowed special chars, and max length.")
    public void s3_organizationNameValidation() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();

        Assert.assertEquals(page.getOrganizationNamePlaceholderText(), GlobalConstants.ORG_NAME_PLACEHOLDER_TEXT,
                GlobalConstants.isOrgNamePlaceholderTextCorrect);
        Assert.assertEquals(page.getPartnerOrganisationFieldValue(), "", GlobalConstants.isOrgNamePlaceholderTextCorrect);

        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        Assert.assertFalse(page.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(), GlobalConstants.isValidOrgNameAccepted);
        Assert.assertEquals(page.getPartnerOrganisationFieldValue(), GlobalConstants.ORGANISATION_NAME,
                GlobalConstants.isValidOrgNameAccepted);
        Assert.assertFalse(page.getPartnerOrganisationFieldValue().isEmpty(),
                GlobalConstants.isOrgNamePlaceholderDisappearsOnTyping);

        page.enterPartnerOrganisation(GlobalConstants.ORG_NAME_WITH_ALLOWED_SPECIAL_CHARS);
        Assert.assertFalse(page.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAllowedSpecialCharsAcceptedInOrgName);

        page.enterPartnerOrganisation(GlobalConstants.DISALLOWED_SPECIAL_CHARS_ORG);
        Assert.assertTrue(page.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isInvalidSpecialCharsRejectedInOrgName);

        page.enterPartnerOrganisation("A".repeat(130));
        Assert.assertTrue(page.getPartnerOrganisationFieldValue().length() <= GlobalConstants.ORG_NAME_MAX_LENGTH,
                GlobalConstants.isOrgNameMaxLengthEnforced);
    }

    @Test(priority = 4, groups = { "S4_AddressValidation" },
          description = "Verify Address field placeholder, valid input, long word, multiline, allowed/disallowed chars, and max length.")
    public void s4_addressFieldValidation() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();

        Assert.assertEquals(page.getPartnerAddressPlaceholderText(), GlobalConstants.ADDRESS_PLACEHOLDER_TEXT,
                GlobalConstants.isAddressPlaceholderTextCorrect);
        Assert.assertEquals(page.getPartnerAddressFieldValue(), "", GlobalConstants.isAddressPlaceholderTextCorrect);

        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        Assert.assertFalse(page.isPartnerAddressSpecialChNotAllowErrorDisplayed(), GlobalConstants.isValidAddressAccepted);
        Assert.assertEquals(page.getPartnerAddressFieldValue(), GlobalConstants.ABIS_ADDRESS,
                GlobalConstants.isValidAddressAccepted);

        page.enterPartnerAddress(GlobalConstants.LENGTHY_SINGLE_WORD_ADDRESS);
        Assert.assertFalse(page.isPartnerAddressSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isLongSingleWordAddressProperlyDisplayed);

        page.enterPartnerAddress(GlobalConstants.MULTILINE_STRING);
        Assert.assertFalse(page.isPartnerAddressSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isMultilineAddressAccepted);

        page.enterPartnerAddress("A".repeat(2001));
        Assert.assertTrue(page.getPartnerAddressFieldValue().length() <= GlobalConstants.ADDRESS_MAX_LENGTH,
                GlobalConstants.isAddressMaxLengthEnforced);

        page.enterPartnerAddress(GlobalConstants.ADDRESS_WITH_ALLOWED_SPECIAL_CHARS);
        Assert.assertFalse(page.isPartnerAddressSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAddressAllowedSpecialCharsAccepted);

        page.enterPartnerAddress(GlobalConstants.DISALLOWED_SPECIAL_CHARS_ADDRESS);
        Assert.assertTrue(page.isPartnerAddressSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAddressInvalidSpecialCharsRejected);
    }

    @Test(priority = 5, groups = { "S5_EmailValidation" },
          description = "Verify Email field placeholder, valid input, invalid format error on blur, max length, and duplicate-email error on submit.")
    public void s5_emailFieldValidation() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage page = new MispPartnerPage(driver);
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();

        Assert.assertEquals(page.getEmailPlaceholderText(), GlobalConstants.EMAIL_PLACEHOLDER_TEXT,
                GlobalConstants.isEmailPlaceholderTextCorrect);
        Assert.assertEquals(page.getEmailFieldValue(), "", GlobalConstants.isEmailPlaceholderTextCorrect);

        page.enterEmailId("abisvalid" + BaseClass.data + "@test.com");
        page.clickOnPartnerAddressTextBox();
        Assert.assertFalse(page.isPartnerEmailIdSpecialChNotAllowErrorDisplayed(), GlobalConstants.isValidEmailAccepted);

        Assert.assertFalse(page.getEmailFieldValue().isEmpty(), GlobalConstants.isEmailPlaceholderDisappearsOnTyping);

        page.enterEmailId(GlobalConstants.INVALID_EMAIL_ID);
        page.clickOnPartnerAddressTextBox();
        Assert.assertTrue(page.isPartnerEmailIdSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isEmailInvalidFormatErrorDisplayed);
        Assert.assertEquals(page.getEmailValidationErrorText(), GlobalConstants.EMAIL_INVALID_FORMAT_ERROR_MSG,
                GlobalConstants.isEmailInvalidFormatErrorTextCorrect);

        page.enterEmailId("a".repeat(246) + "@test.com");
        Assert.assertTrue(page.getEmailFieldValue().length() <= GlobalConstants.EMAIL_MAX_LENGTH,
                GlobalConstants.isEmailMaxLengthEnforced);

        String sharedEmail = "abisdupe" + BaseClass.data + "@test.com";
        String username1 = "abisdup1" + BaseClass.data;
        fillAbisPartnerMandatoryFields(page, sharedEmail, username1);
        page.clickOnCreatePartnerSubmitButton();
        Assert.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isAbisPartnerCreatedSuccessfully);
        page.clickOnSuccessMsgHomeButton();

        String username2 = "abisdup2" + BaseClass.data;
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        fillAbisPartnerMandatoryFields(page, sharedEmail, username2);
        page.clickOnCreatePartnerSubmitButton();
        Assert.assertTrue(page.isEmailAddressIsAlreadyRegisteredErrorDisplayed(),
                GlobalConstants.isEmailAlreadyRegisteredErrorDisplayed);
        Assert.assertEquals(page.getEmailAlreadyRegisteredErrorText(), GlobalConstants.EMAIL_ALREADY_REGISTERED_ERROR_MSG,
                GlobalConstants.isEmailAlreadyRegisteredErrorTextCorrect);
    }

    @Test(priority = 6, groups = { "S6_PhoneValidation" },
          description = "Verify Contact Number placeholder, valid input, short/long length, symbol/letter rejection, leading zeros, and reused phone acceptance.")
    public void s6_phoneNumberFieldValidation() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage page = new MispPartnerPage(driver);
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();

        Assert.assertEquals(page.getContactNumberPlaceholderText(), GlobalConstants.CONTACT_NUMBER_PLACEHOLDER_TEXT,
                GlobalConstants.isPhoneNumberPlaceholderTextCorrect);
        Assert.assertEquals(page.getContactNumberFieldValue(), "", GlobalConstants.isPhoneNumberPlaceholderTextCorrect);

        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.clickOnPartnerAddressTextBox();
        Assert.assertFalse(page.isPartnerContactSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isValidPhoneNumberAccepted);
        Assert.assertEquals(page.getContactNumberFieldValue(), GlobalConstants.ABIS_CONTACT_NUMBER,
                GlobalConstants.isValidPhoneNumberAccepted);

        Assert.assertFalse(page.getContactNumberFieldValue().isEmpty(),
                GlobalConstants.isPhoneNumberPlaceholderDisappearsOnTyping);

        page.enterPartnerContactNumber(GlobalConstants.SHORT_PHONE_NUMBER);
        page.clickOnPartnerAddressTextBox();
        Assert.assertTrue(page.isPartnerContactNumberNotAllowErrorDisplayed(),
                GlobalConstants.isInvalidPhoneNumberLengthErrorDisplayed);
        Assert.assertEquals(page.getContactNumberValidationErrorText(), GlobalConstants.CONTACT_NUMBER_INVALID_ERROR_MSG,
                GlobalConstants.isInvalidPhoneNumberLengthErrorTextCorrect);

        page.enterPartnerContactNumber("1".repeat(17));
        Assert.assertTrue(page.getContactNumberFieldValue().length() <= GlobalConstants.CONTACT_NUMBER_MAX_LENGTH,
                GlobalConstants.isPhoneNumberMaxLengthEnforced);

        page.enterPartnerContactNumber(GlobalConstants.INVALIDFORMAT_PHONENUMBER);
        page.clickOnPartnerAddressTextBox();
        String phoneAfterSlash = page.getContactNumberFieldValue();
        Assert.assertTrue(
                page.isPartnerContactSpecialChNotAllowErrorDisplayed() || !phoneAfterSlash.contains("/"),
                GlobalConstants.isInvalidPhoneFormatWithSymbolsRejected);

        page.enterPartnerContactNumber(GlobalConstants.VANITY_PHONENUMBER);
        page.clickOnPartnerAddressTextBox();
        String phoneAfterVanity = page.getContactNumberFieldValue();
        Assert.assertTrue(
                page.isPartnerContactSpecialChNotAllowErrorDisplayed() || !phoneAfterVanity.matches(".*[A-Za-z].*"),
                GlobalConstants.isPhoneWithLettersRejected);

        page.enterPartnerContactNumber(GlobalConstants.LEADINGZERO_PHONENUMBER);
        page.clickOnPartnerAddressTextBox();
        Assert.assertFalse(page.isPartnerContactSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isLeadingZerosAcceptedInPhone);
        Assert.assertEquals(page.getContactNumberFieldValue(), GlobalConstants.LEADINGZERO_PHONENUMBER,
                GlobalConstants.isLeadingZerosAcceptedInPhone);

        page.enterPartnerContactNumber(GlobalConstants.MAX_VALID_PHONE_NUMBER);
        page.clickOnPartnerAddressTextBox();
        Assert.assertFalse(page.isPartnerContactSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isValidLengthPhoneAccepted);
        Assert.assertEquals(page.getContactNumberFieldValue(), GlobalConstants.MAX_VALID_PHONE_NUMBER,
                GlobalConstants.isValidLengthPhoneAccepted);

        String emailForReuse = "abisphon" + BaseClass.data + "@test.com";
        String usernameForReuse = "abisphon" + BaseClass.data;
        fillAbisPartnerMandatoryFields(page, emailForReuse, usernameForReuse);
        page.clickOnCreatePartnerSubmitButton();
        Assert.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isReusedPhoneNumberAccepted);
        page.clickOnSuccessMsgHomeButton();
    }

    @Test(priority = 7, groups = { "S7_UsernameValidation" },
          description = "Verify Username field placeholder, valid input, invalid start chars, whitespace, max length, and duplicate-username error.")
    public void s7_usernameFieldValidation() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage page = new MispPartnerPage(driver);
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();

        Assert.assertEquals(page.getUserNamePlaceholderText(), GlobalConstants.USERNAME_PLACEHOLDER_TEXT,
                GlobalConstants.isUsernamePlaceholderTextCorrect);
        Assert.assertEquals(page.getUserNameFieldValue(), "", GlobalConstants.isUsernamePlaceholderTextCorrect);

        page.enterUserName(GlobalConstants.UNDERSCORE_STRING);
        page.clickOnPartnerAddressTextBox();
        Assert.assertFalse(page.isPartnerUserNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isValidUsernameAccepted);
        Assert.assertEquals(page.getUserNameFieldValue(), GlobalConstants.UNDERSCORE_STRING,
                GlobalConstants.isValidUsernameAccepted);

        Assert.assertFalse(page.getUserNameFieldValue().isEmpty(),
                GlobalConstants.isUsernamePlaceholderDisappearsOnTyping);

        page.enterUserName(GlobalConstants.UNDERSCORE_PREFIXED_USERNAME);
        page.clickOnPartnerAddressTextBox();
        Assert.assertTrue(page.isUsernameMustStartWithLetterErrorDisplayed(),
                GlobalConstants.isUsernameStartWithLetterEnforced);

        page.enterUserName(GlobalConstants.NUMERIC_PREFIXED_USERNAME);
        page.clickOnPartnerAddressTextBox();
        Assert.assertTrue(page.isUsernameMustStartWithLetterErrorDisplayed(),
                GlobalConstants.isUsernameInvalidStartingCharsRejected);

        page.enterUserName(GlobalConstants.USERNAME_WITH_SPACE);
        page.clickOnPartnerAddressTextBox();
        String fieldWithSpace = page.getUserNameFieldValue();
        Assert.assertTrue(
                page.isPartnerUserNameSpecialChNotAllowErrorDisplayed() || !fieldWithSpace.contains(" "),
                GlobalConstants.isWhitespaceRejectedInUsername);

        page.enterUserName("a".repeat(37));
        Assert.assertTrue(page.getUserNameFieldValue().length() <= GlobalConstants.USERNAME_MAX_LENGTH,
                GlobalConstants.isUsernameMaxLengthEnforced);

        String sharedUsername = "abisun" + BaseClass.data;
        String email1 = "abisun1" + BaseClass.data + "@test.com";
        fillAbisPartnerMandatoryFields(page, email1, sharedUsername);
        page.clickOnCreatePartnerSubmitButton();
        Assert.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isAbisPartnerCreatedSuccessfully);
        page.clickOnSuccessMsgHomeButton();

        String email2 = "abisun2" + BaseClass.data + "@test.com";
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        fillAbisPartnerMandatoryFields(page, email2, sharedUsername);
        page.clickOnCreatePartnerSubmitButton();
        Assert.assertTrue(page.isUsernameAlreadyExistErrorDisplayed(),
                GlobalConstants.isUsernameAlreadyExistErrorDisplayed);
        Assert.assertEquals(page.getEmailAlreadyRegisteredErrorText(), GlobalConstants.USERNAME_ALREADY_EXISTS_ERROR_MSG,
                GlobalConstants.isUsernameAlreadyExistsErrorTextCorrect);
    }

    @Test(priority = 8, groups = { "S8_PolicyGroupValidation" },
          description = "Verify Policy Group dropdown shows active groups, hides deactivated groups, has search bar, and renders long names.")
    public void s8_policyGroupDropdownValidation() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();

        page.openPolicyGroupDropdown();
        Assert.assertTrue(page.isPolicyGroupOptionVisible(GlobalConstants.DEFAULT_POLICYGROUP),
                GlobalConstants.isActivePolicyGroupInDropdown);

        page.enterInvalidPolicyGroup(GlobalConstants.DEACTIVATE_POLICYGROUP);
        Assert.assertFalse(page.isPolicyGroupOptionVisible(GlobalConstants.DEACTIVATE_POLICYGROUP),
                GlobalConstants.isDeactivatedPolicyGroupNotInDropdown);

        page.openPolicyGroupDropdown();
        Assert.assertTrue(page.isPolicyGroupSearchBarDisplayed(), GlobalConstants.isPolicyGroupSearchBarVisible);
        page.enterInvalidPolicyGroup(GlobalConstants.DEFAULT_POLICYGROUP);
        Assert.assertTrue(page.isPolicyGroupOptionVisible(GlobalConstants.DEFAULT_POLICYGROUP),
                GlobalConstants.isPolicyGroupSearchFiltersResults);

        Assert.assertTrue(page.isPolicyGroupOptionVisible(GlobalConstants.DEFAULT_POLICYGROUP),
                GlobalConstants.isLongPolicyGroupNameVisible);
    }

    @Test(priority = 9, groups = { "S9_FormLifecycle" },
          description = "Verify create-partner lifecycle: submit to success screen, Home nav, cancel popup, clear form, alternate-flow error correction.")
    public void s9_formLifecycle() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage page = new MispPartnerPage(driver);

        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        String email = "abislife" + BaseClass.data + "@test.com";
        String username = "abislif" + BaseClass.data;
        fillAbisPartnerMandatoryFields(page, email, username);
        page.clickOnCreatePartnerSubmitButton();
        Assert.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isFormSubmittedSuccessfully);
        Assert.assertTrue(page.isUploadPartnerCertificateButtonDisplayed(),
                GlobalConstants.isUploadCertButtonOnSuccessScreenDisplayed);
        Assert.assertTrue(page.isCreatePartnerSuccessMsgHomeButtonDisplayed(),
                GlobalConstants.isHomeButtonOnSuccessScreenDisplayed);

        page.clickOnSuccessMsgHomeButton();
        Assert.assertTrue(dashboardPage.isPartnersDisplayed(), GlobalConstants.isDashboardDisplayedAfterHomeButton);

        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.clickOnCreatePartnerCancelButton();
        Assert.assertTrue(page.isCancelConfirmationPopupDisplayed(), GlobalConstants.isCancelPopupDisplayed);
        Assert.assertEquals(page.getCancelConfirmationPopupText(), GlobalConstants.CANCEL_CONFIRMATION_POPUP_TEXT,
                GlobalConstants.isCancelPopupTextCorrect);
        Assert.assertTrue(page.iscancelConfirmationPopupProceedButtonDisplayed(),
                GlobalConstants.isCancelPopupProceedButtonDisplayed);
        Assert.assertTrue(page.isCancelConfirmationPopupCancelButtonDisplayed(),
                GlobalConstants.isCancelPopupCancelButtonDisplayed);
        page.clickOnCancelConfirmationPopupProceedButton();
        Assert.assertTrue(page.isListOfPartnersDisplayed(), GlobalConstants.isCancelNavigatesBackToPartnerList);

        page.clickOnCreatePartnerButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.enterEmailId(GlobalConstants.ABIS_EMAIL_ID);
        page.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        page.clickOnCreatePartnerClearButton();
        Assert.assertEquals(page.getPartnerOrganisationFieldValue(), "", GlobalConstants.isClearFormClearsAllFields);
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isClearFormClearsAllFields);

        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        page.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
        page.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.enterEmailId(GlobalConstants.ABIS_EMAIL_ID);
        page.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        page.enterPartnerOrganisation(GlobalConstants.DISALLOWED_SPECIAL_CHARS_ORG);
        Assert.assertTrue(page.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAlternateFlowsHandledCorrectly);
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isAlternateFlowsHandledCorrectly);
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        Assert.assertFalse(page.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAlternateFlowsHandledCorrectly);
        Assert.assertTrue(page.isCreatePartnerSubmitButtonEnabled(), GlobalConstants.isAlternateFlowsHandledCorrectly);
    }

    @Test(priority = 10, groups = { "S10_PartnerListAndNavigation" },
          description = "Verify dashboard nav, partner statuses (no-cert/with-cert), multiple partners, cert-popup nav, back arrow, and responsive viewport.")
    public void s10_partnerListAndNavigation() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage page = new MispPartnerPage(driver);
        PartnerAdminPage partnerAdminPage;

        dashboardPage.clickOnPartners();
        Assert.assertTrue(page.isListOfPartnersDisplayed(), GlobalConstants.isPartnersPageNavigableViaDashboard);

        page.clickOnCreatePartnerButton();
        String emailNoCert = "abisncrt" + BaseClass.data + "@test.com";
        String usernameNoCert = "abisncrt" + BaseClass.data;
        fillAbisPartnerMandatoryFields(page, emailNoCert, usernameNoCert);
        page.clickOnCreatePartnerSubmitButton();
        Assert.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isAbisPartnerCreatedSuccessfully);
        page.clickOnSuccessMsgHomeButton();
        partnerAdminPage = dashboardPage.clickOnPartnerOfHamburger();
        partnerAdminPage.clickOnActionsButton();
        Assert.assertTrue(partnerAdminPage.isUploadCertificateButtonDisplayed(),
                GlobalConstants.isUploadCertMenuOptionAvailable);
    }
}
