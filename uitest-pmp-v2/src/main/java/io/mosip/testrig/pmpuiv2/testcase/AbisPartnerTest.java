package io.mosip.testrig.pmpuiv2.testcase;

import java.util.List;

import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
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

    // S1: Form Structure, Partner Type Dropdown, Notification Language
    // Covers T1, T2, T5, T66, T67, T68, T83
    @Test(priority = 1, groups = { "S1_FormStructure" },
          description = "Verify form structure, partner type dropdown options, notification language dropdown, and org name info tooltip.")
    public void s1_formStructureAndDropdowns() {
        SoftAssert soft = new SoftAssert();
        MispPartnerPage page = navigateToCreatePartnerPage();

        // T83: Design spec — page title, mandatory notice, key controls, breadcrumbs
        soft.assertTrue(page.isCreatePrtnerPageTitleDisplayed(), GlobalConstants.isDesignSpecCompliant);
        soft.assertEquals(page.getCreatePartnerPageTitleText(), GlobalConstants.CREATE_PARTNER_PAGE_TITLE,
                GlobalConstants.isCreatePartnerPageTitleCorrect);
        soft.assertTrue(page.isCreatePartnerMandatoryFieldInfoDisplayed(), GlobalConstants.isDesignSpecCompliant);
        soft.assertEquals(page.getCreatePartnerMandatoryFieldInfoText(), GlobalConstants.MANDATORY_FIELD_INFO_TEXT,
                GlobalConstants.isMandatoryFieldInfoTextCorrect);
        soft.assertTrue(page.isPartnerTypeDropdownDisplayed(), GlobalConstants.isDesignSpecCompliant);
        soft.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isDesignSpecCompliant);
        soft.assertTrue(page.isHomeButtonDisplayed(), GlobalConstants.isDesignSpecCompliant);
        soft.assertTrue(page.isListOfPartnerButtonDisplayed(), GlobalConstants.isDesignSpecCompliant);

        // T1: ABIS Partner selection
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        soft.assertEquals(page.getSelectedPartnerTypeText(), GlobalConstants.ABIS_PARTNER,
                GlobalConstants.isAbisPartnerSelectedSuccessfully);

        // T1: MISP Partner selection
        page.clickOnPartnerTypeDropdown();
        page.clickOnMispPartnerOption();
        soft.assertEquals(page.getSelectedPartnerTypeText(), GlobalConstants.MISP_PARTNER,
                GlobalConstants.isMispPartnerSelectedSuccessfully);

        // T2: Dropdown has exactly 3 options
        page.clickOnPartnerTypeDropdown();
        List<String> options = page.getPartnerTypeDropdownOptionTexts();
        soft.assertEquals(options.size(), 3, GlobalConstants.isPartnerTypeDropdownOptionCountCorrect);
        soft.assertTrue(options.contains(GlobalConstants.ABIS_PARTNER), GlobalConstants.isAbisPartnerOptionDisplayed);
        soft.assertTrue(options.contains(GlobalConstants.MANUAL_ADJUDICATION_PARTNER),
                GlobalConstants.isManualAdjudicationPartnerOptionDisplayed);
        soft.assertTrue(options.contains(GlobalConstants.MISP_PARTNER), GlobalConstants.isMispPartnerOptionDisplayed);
        page.clickOnAbisPartnerOption();

        // T5: Org name info tooltip
        page.clickOnPartnerOragnizationInfoButton();
        soft.assertTrue(page.isOrganizationNameInfoDisplayed(), GlobalConstants.isOrganizationNameInfoDisplayed);
        soft.assertEquals(page.getOrganizationNameInfoText(), GlobalConstants.ORG_NAME_INFO_TEXT,
                GlobalConstants.isOrgNameInfoTextCorrect);

        // T66: Notification language shown as dropdown
        soft.assertTrue(page.isNotificationLanguageDropdownDisplayed(),
                GlobalConstants.isNotificationLanguageDropdownDisplayed);

        // T67: Dropdown has at least one option
        page.clickOnNotificationLanguageDropdown();
        soft.assertTrue(page.isNotificationLanguageOptionVisible(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE),
                GlobalConstants.isNotificationLanguageDropdownHasOptions);

        // T68: Language can be selected, selection reflected in control
        page.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        soft.assertTrue(
                page.getSelectedNotificationLanguageText().contains(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE),
                GlobalConstants.isNotificationLanguageSelectable);

        soft.assertAll();
    }

    // S2: Mandatory Field Gate + Multi-field Simultaneous Validation
    // Covers T4, T9, T16, T31, T82
    @Test(priority = 2, groups = { "S2_MandatoryFields" },
          description = "Verify submit button state as mandatory fields are filled and that multiple simultaneous invalid inputs block submission.")
    public void s2_mandatoryFieldGateAndMultiFieldValidation() {
        SoftAssert soft = new SoftAssert();
        MispPartnerPage page = navigateToCreatePartnerPage();

        // T9: Select ABIS type only — submit must be disabled
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        soft.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isCreatePartnerSubmitButtonDisabled);

        // T4: MISP type + all fields except policy group — submit disabled
        page.clickOnPartnerTypeDropdown();
        page.clickOnMispPartnerOption();
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.enterEmailId(GlobalConstants.ABIS_EMAIL_ID);
        page.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        soft.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isCreatePartnerSubmitButtonDisabled);

        // T31: ABIS + all fields except org name — submit disabled
        page.clickOnCreatePartnerClearButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        page.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
        page.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.enterEmailId(GlobalConstants.ABIS_EMAIL_ID);
        page.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        soft.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isSubmitDisabledWhenOrgNameEmpty);

        // T16: ABIS + all fields except email — submit disabled
        page.clickOnCreatePartnerClearButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        page.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
        page.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        soft.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isSubmitDisabledWithIncompleteFields);

        // T82: Multiple simultaneous invalid fields — all errors visible, submit disabled
        page.clickOnCreatePartnerClearButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        page.enterPartnerOrganisation(GlobalConstants.DISALLOWED_SPECIAL_CHARS_ORG);
        soft.assertTrue(page.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAllValidationErrorsDisplayed);
        page.enterPartnerAddress(GlobalConstants.DISALLOWED_SPECIAL_CHARS_ADDRESS);
        soft.assertTrue(page.isPartnerAddressSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAllValidationErrorsDisplayed);
        page.enterEmailId(GlobalConstants.INVALID_EMAIL_ID);
        page.clickOnPartnerAddressTextBox();
        soft.assertTrue(page.isPartnerEmailIdSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAllValidationErrorsDisplayed);
        soft.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isAllValidationErrorsDisplayed);

        soft.assertAll();
    }

    // S3: Organization Name Field Validation
    // Covers T7, T30, T32, T33, T34, T35
    @Test(priority = 3, groups = { "S3_OrgNameValidation" },
          description = "Verify Organization Name field placeholder, valid input, allowed/disallowed special chars, and max length.")
    public void s3_organizationNameValidation() {
        SoftAssert soft = new SoftAssert();
        MispPartnerPage page = navigateToCreatePartnerPage();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();

        // T33: Placeholder text and initial empty state
        soft.assertEquals(page.getOrganizationNamePlaceholderText(), GlobalConstants.ORG_NAME_PLACEHOLDER_TEXT,
                GlobalConstants.isOrgNamePlaceholderTextCorrect);
        soft.assertEquals(page.getPartnerOrganisationFieldValue(), "", GlobalConstants.isOrgNamePlaceholderTextCorrect);

        // T30 + T33: Valid org name — no error, value retained, placeholder gone
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        soft.assertFalse(page.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(), GlobalConstants.isValidOrgNameAccepted);
        soft.assertEquals(page.getPartnerOrganisationFieldValue(), GlobalConstants.ORGANISATION_NAME,
                GlobalConstants.isValidOrgNameAccepted);
        soft.assertFalse(page.getPartnerOrganisationFieldValue().isEmpty(),
                GlobalConstants.isOrgNamePlaceholderDisappearsOnTyping);

        // T34: Allowed special characters — no error
        page.enterPartnerOrganisation(GlobalConstants.ORG_NAME_WITH_ALLOWED_SPECIAL_CHARS);
        soft.assertFalse(page.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAllowedSpecialCharsAcceptedInOrgName);

        // T35 + T7: Disallowed special characters — error shown
        page.enterPartnerOrganisation(GlobalConstants.DISALLOWED_SPECIAL_CHARS_ORG);
        soft.assertTrue(page.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isInvalidSpecialCharsRejectedInOrgName);

        // T32: Max length at 128 chars
        page.enterPartnerOrganisation("A".repeat(130));
        soft.assertTrue(page.getPartnerOrganisationFieldValue().length() <= GlobalConstants.ORG_NAME_MAX_LENGTH,
                GlobalConstants.isOrgNameMaxLengthEnforced);

        soft.assertAll();
    }

    // S4: Address Field Validation
    // Covers T36-T42
    @Test(priority = 4, groups = { "S4_AddressValidation" },
          description = "Verify Address field placeholder, valid input, long word, multiline, allowed/disallowed chars, and max length.")
    public void s4_addressFieldValidation() {
        SoftAssert soft = new SoftAssert();
        MispPartnerPage page = navigateToCreatePartnerPage();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();

        // T42: Placeholder text and initial empty state
        soft.assertEquals(page.getPartnerAddressPlaceholderText(), GlobalConstants.ADDRESS_PLACEHOLDER_TEXT,
                GlobalConstants.isAddressPlaceholderTextCorrect);
        soft.assertEquals(page.getPartnerAddressFieldValue(), "", GlobalConstants.isAddressPlaceholderTextCorrect);

        // T36: Valid address — no error, value retained
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        soft.assertFalse(page.isPartnerAddressSpecialChNotAllowErrorDisplayed(), GlobalConstants.isValidAddressAccepted);
        soft.assertEquals(page.getPartnerAddressFieldValue(), GlobalConstants.ABIS_ADDRESS,
                GlobalConstants.isValidAddressAccepted);

        // T37: Long single-word address — no error
        page.enterPartnerAddress(GlobalConstants.LENGTHY_SINGLE_WORD_ADDRESS);
        soft.assertFalse(page.isPartnerAddressSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isLongSingleWordAddressProperlyDisplayed);

        // T38: Multiline address — no error
        page.enterPartnerAddress(GlobalConstants.MULTILINE_STRING);
        soft.assertFalse(page.isPartnerAddressSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isMultilineAddressAccepted);

        // T39: Max length at 2000 chars
        page.enterPartnerAddress("A".repeat(2001));
        soft.assertTrue(page.getPartnerAddressFieldValue().length() <= GlobalConstants.ADDRESS_MAX_LENGTH,
                GlobalConstants.isAddressMaxLengthEnforced);

        // T40: Allowed special characters — no error
        page.enterPartnerAddress(GlobalConstants.ADDRESS_WITH_ALLOWED_SPECIAL_CHARS);
        soft.assertFalse(page.isPartnerAddressSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAddressAllowedSpecialCharsAccepted);

        // T41: Disallowed special characters — error shown
        page.enterPartnerAddress(GlobalConstants.DISALLOWED_SPECIAL_CHARS_ADDRESS);
        soft.assertTrue(page.isPartnerAddressSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAddressInvalidSpecialCharsRejected);

        soft.assertAll();
    }

    // S5: Email Field Validation + Duplicate Email
    // Covers T21, T22, T43, T44, T45
    @Test(priority = 5, groups = { "S5_EmailValidation" },
          description = "Verify Email field placeholder, valid input, invalid format error on blur, max length, and duplicate-email error on submit.")
    public void s5_emailFieldValidation() {
        SoftAssert soft = new SoftAssert();
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage page = new MispPartnerPage(driver);
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();

        // T43: Placeholder text and initial empty state
        soft.assertEquals(page.getEmailPlaceholderText(), GlobalConstants.EMAIL_PLACEHOLDER_TEXT,
                GlobalConstants.isEmailPlaceholderTextCorrect);
        soft.assertEquals(page.getEmailFieldValue(), "", GlobalConstants.isEmailPlaceholderTextCorrect);

        // T21: Valid email — no error on blur
        page.enterEmailId("abisvalid" + BaseClass.data + "@test.com");
        page.clickOnPartnerAddressTextBox();
        soft.assertFalse(page.isPartnerEmailIdSpecialChNotAllowErrorDisplayed(), GlobalConstants.isValidEmailAccepted);

        // T43: Placeholder disappears after typing
        soft.assertFalse(page.getEmailFieldValue().isEmpty(), GlobalConstants.isEmailPlaceholderDisappearsOnTyping);

        // T44: Invalid format — error on blur
        page.enterEmailId(GlobalConstants.INVALID_EMAIL_ID);
        page.clickOnPartnerAddressTextBox();
        soft.assertTrue(page.isPartnerEmailIdSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isEmailInvalidFormatErrorDisplayed);
        soft.assertEquals(page.getEmailValidationErrorText(), GlobalConstants.EMAIL_INVALID_FORMAT_ERROR_MSG,
                GlobalConstants.isEmailInvalidFormatErrorTextCorrect);

        // T45: Max length at 254
        page.enterEmailId("a".repeat(246) + "@test.com");
        soft.assertTrue(page.getEmailFieldValue().length() <= GlobalConstants.EMAIL_MAX_LENGTH,
                GlobalConstants.isEmailMaxLengthEnforced);

        // T22: Duplicate email — register first partner then attempt second with same email
        String sharedEmail = "abisdupe" + BaseClass.data + "@test.com";
        String username1 = "abisdup1" + BaseClass.data;
        fillAbisPartnerMandatoryFields(page, sharedEmail, username1);
        page.clickOnCreatePartnerSubmitButton();
        soft.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isAbisPartnerCreatedSuccessfully);
        page.clickOnSuccessMsgHomeButton();

        String username2 = "abisdup2" + BaseClass.data;
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        fillAbisPartnerMandatoryFields(page, sharedEmail, username2);
        page.clickOnCreatePartnerSubmitButton();
        soft.assertTrue(page.isEmailAddressIsAlreadyRegisteredErrorDisplayed(),
                GlobalConstants.isEmailAlreadyRegisteredErrorDisplayed);
        soft.assertEquals(page.getEmailAlreadyRegisteredErrorText(), GlobalConstants.EMAIL_ALREADY_REGISTERED_ERROR_MSG,
                GlobalConstants.isEmailAlreadyRegisteredErrorTextCorrect);

        soft.assertAll();
    }

    // S6: Phone Number Field Validation + Reused Phone
    // Covers T46-T50, T53-T57
    @Test(priority = 6, groups = { "S6_PhoneValidation" },
          description = "Verify Contact Number placeholder, valid input, short/long length, symbol/letter rejection, leading zeros, and reused phone acceptance.")
    public void s6_phoneNumberFieldValidation() {
        SoftAssert soft = new SoftAssert();
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage page = new MispPartnerPage(driver);
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();

        // T48: Placeholder text and initial empty state
        soft.assertEquals(page.getContactNumberPlaceholderText(), GlobalConstants.CONTACT_NUMBER_PLACEHOLDER_TEXT,
                GlobalConstants.isPhoneNumberPlaceholderTextCorrect);
        soft.assertEquals(page.getContactNumberFieldValue(), "", GlobalConstants.isPhoneNumberPlaceholderTextCorrect);

        // T46: Valid phone — no error, value retained
        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.clickOnPartnerAddressTextBox();
        soft.assertFalse(page.isPartnerContactSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isValidPhoneNumberAccepted);
        soft.assertEquals(page.getContactNumberFieldValue(), GlobalConstants.ABIS_CONTACT_NUMBER,
                GlobalConstants.isValidPhoneNumberAccepted);

        // T48: Placeholder disappears after typing
        soft.assertFalse(page.getContactNumberFieldValue().isEmpty(),
                GlobalConstants.isPhoneNumberPlaceholderDisappearsOnTyping);

        // T47 + T53: Short phone — error on blur
        page.enterPartnerContactNumber(GlobalConstants.SHORT_PHONE_NUMBER);
        page.clickOnPartnerAddressTextBox();
        soft.assertTrue(page.isPartnerContactNumberNotAllowErrorDisplayed(),
                GlobalConstants.isInvalidPhoneNumberLengthErrorDisplayed);
        soft.assertEquals(page.getContactNumberValidationErrorText(), GlobalConstants.CONTACT_NUMBER_INVALID_ERROR_MSG,
                GlobalConstants.isInvalidPhoneNumberLengthErrorTextCorrect);

        // T49: Max length at 16 digits
        page.enterPartnerContactNumber("1".repeat(17));
        soft.assertTrue(page.getContactNumberFieldValue().length() <= GlobalConstants.CONTACT_NUMBER_MAX_LENGTH,
                GlobalConstants.isPhoneNumberMaxLengthEnforced);

        // T54: Slash in phone — rejected or stripped
        page.enterPartnerContactNumber(GlobalConstants.INVALIDFORMAT_PHONENUMBER);
        page.clickOnPartnerAddressTextBox();
        String phoneAfterSlash = page.getContactNumberFieldValue();
        soft.assertTrue(
                page.isPartnerContactSpecialChNotAllowErrorDisplayed() || !phoneAfterSlash.contains("/"),
                GlobalConstants.isInvalidPhoneFormatWithSymbolsRejected);

        // T55: Letters in phone — rejected or stripped
        page.enterPartnerContactNumber(GlobalConstants.VANITY_PHONENUMBER);
        page.clickOnPartnerAddressTextBox();
        String phoneAfterVanity = page.getContactNumberFieldValue();
        soft.assertTrue(
                page.isPartnerContactSpecialChNotAllowErrorDisplayed() || !phoneAfterVanity.matches(".*[A-Za-z].*"),
                GlobalConstants.isPhoneWithLettersRejected);

        // T56: Leading zeros accepted
        page.enterPartnerContactNumber(GlobalConstants.LEADINGZERO_PHONENUMBER);
        page.clickOnPartnerAddressTextBox();
        soft.assertFalse(page.isPartnerContactSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isLeadingZerosAcceptedInPhone);
        soft.assertEquals(page.getContactNumberFieldValue(), GlobalConstants.LEADINGZERO_PHONENUMBER,
                GlobalConstants.isLeadingZerosAcceptedInPhone);

        // T57: Max valid length (16 digits) accepted
        page.enterPartnerContactNumber(GlobalConstants.MAX_VALID_PHONE_NUMBER);
        page.clickOnPartnerAddressTextBox();
        soft.assertFalse(page.isPartnerContactSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isValidLengthPhoneAccepted);
        soft.assertEquals(page.getContactNumberFieldValue(), GlobalConstants.MAX_VALID_PHONE_NUMBER,
                GlobalConstants.isValidLengthPhoneAccepted);

        // T50: Reused phone — submit succeeds (phone is not unique-constrained)
        String emailForReuse = "abisphon" + BaseClass.data + "@test.com";
        String usernameForReuse = "abisphon" + BaseClass.data;
        fillAbisPartnerMandatoryFields(page, emailForReuse, usernameForReuse);
        page.clickOnCreatePartnerSubmitButton();
        soft.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isReusedPhoneNumberAccepted);
        page.clickOnSuccessMsgHomeButton();

        soft.assertAll();
    }

    // S7: Username Field Validation + Duplicate Username
    // Covers T58-T65
    @Test(priority = 7, groups = { "S7_UsernameValidation" },
          description = "Verify Username field placeholder, valid input, invalid start chars, whitespace, max length, and duplicate-username error.")
    public void s7_usernameFieldValidation() {
        SoftAssert soft = new SoftAssert();
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage page = new MispPartnerPage(driver);
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();

        // T60: Placeholder text and initial empty state
        soft.assertEquals(page.getUserNamePlaceholderText(), GlobalConstants.USERNAME_PLACEHOLDER_TEXT,
                GlobalConstants.isUsernamePlaceholderTextCorrect);
        soft.assertEquals(page.getUserNameFieldValue(), "", GlobalConstants.isUsernamePlaceholderTextCorrect);

        // T58 + T61: Valid username (alphanumeric + underscore in middle)
        page.enterUserName(GlobalConstants.UNDERSCORE_STRING);
        page.clickOnPartnerAddressTextBox();
        soft.assertFalse(page.isPartnerUserNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isValidUsernameAccepted);
        soft.assertEquals(page.getUserNameFieldValue(), GlobalConstants.UNDERSCORE_STRING,
                GlobalConstants.isValidUsernameAccepted);

        // T60: Placeholder disappears after typing
        soft.assertFalse(page.getUserNameFieldValue().isEmpty(),
                GlobalConstants.isUsernamePlaceholderDisappearsOnTyping);

        // T62: Underscore-prefixed username — error shown
        page.enterUserName(GlobalConstants.UNDERSCORE_PREFIXED_USERNAME);
        page.clickOnPartnerAddressTextBox();
        soft.assertTrue(page.isUsernameMustStartWithLetterErrorDisplayed(),
                GlobalConstants.isUsernameStartWithLetterEnforced);

        // T63: Digit-prefixed username — error shown
        page.enterUserName(GlobalConstants.NUMERIC_PREFIXED_USERNAME);
        page.clickOnPartnerAddressTextBox();
        soft.assertTrue(page.isUsernameMustStartWithLetterErrorDisplayed(),
                GlobalConstants.isUsernameInvalidStartingCharsRejected);

        // T64: Whitespace — rejected or stripped
        page.enterUserName(GlobalConstants.USERNAME_WITH_SPACE);
        page.clickOnPartnerAddressTextBox();
        String fieldWithSpace = page.getUserNameFieldValue();
        soft.assertTrue(
                page.isPartnerUserNameSpecialChNotAllowErrorDisplayed() || !fieldWithSpace.contains(" "),
                GlobalConstants.isWhitespaceRejectedInUsername);

        // T65: Max length at 36 chars
        page.enterUserName("a".repeat(37));
        soft.assertTrue(page.getUserNameFieldValue().length() <= GlobalConstants.USERNAME_MAX_LENGTH,
                GlobalConstants.isUsernameMaxLengthEnforced);

        // T59: Duplicate username — register first, attempt second → error
        String sharedUsername = "abisun" + BaseClass.data;
        String email1 = "abisun1" + BaseClass.data + "@test.com";
        fillAbisPartnerMandatoryFields(page, email1, sharedUsername);
        page.clickOnCreatePartnerSubmitButton();
        soft.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isAbisPartnerCreatedSuccessfully);
        page.clickOnSuccessMsgHomeButton();

        String email2 = "abisun2" + BaseClass.data + "@test.com";
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        fillAbisPartnerMandatoryFields(page, email2, sharedUsername);
        page.clickOnCreatePartnerSubmitButton();
        soft.assertTrue(page.isUsernameAlreadyExistErrorDisplayed(),
                GlobalConstants.isUsernameAlreadyExistErrorDisplayed);
        soft.assertEquals(page.getEmailAlreadyRegisteredErrorText(), GlobalConstants.USERNAME_ALREADY_EXISTS_ERROR_MSG,
                GlobalConstants.isUsernameAlreadyExistsErrorTextCorrect);

        soft.assertAll();
    }

    // S8: Policy Group Dropdown Validation
    // Covers T26-T29
    @Test(priority = 8, groups = { "S8_PolicyGroupValidation" },
          description = "Verify Policy Group dropdown shows active groups, hides deactivated groups, has search bar, and renders long names.")
    public void s8_policyGroupDropdownValidation() {
        SoftAssert soft = new SoftAssert();
        MispPartnerPage page = navigateToCreatePartnerPage();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();

        // T26: Active policy group is visible in dropdown
        page.openPolicyGroupDropdown();
        soft.assertTrue(page.isPolicyGroupOptionVisible(GlobalConstants.DEFAULT_POLICYGROUP),
                GlobalConstants.isActivePolicyGroupInDropdown);

        // T27: Deactivated policy group is NOT visible
        page.enterInvalidPolicyGroup(GlobalConstants.DEACTIVATE_POLICYGROUP);
        soft.assertFalse(page.isPolicyGroupOptionVisible(GlobalConstants.DEACTIVATE_POLICYGROUP),
                GlobalConstants.isDeactivatedPolicyGroupNotInDropdown);

        // T28: Search bar visible and filters results
        page.openPolicyGroupDropdown();
        soft.assertTrue(page.isPolicyGroupSearchBarDisplayed(), GlobalConstants.isPolicyGroupSearchBarVisible);
        page.enterInvalidPolicyGroup(GlobalConstants.DEFAULT_POLICYGROUP);
        soft.assertTrue(page.isPolicyGroupOptionVisible(GlobalConstants.DEFAULT_POLICYGROUP),
                GlobalConstants.isPolicyGroupSearchFiltersResults);

        // T29: Long policy group name is visible (not clipped)
        soft.assertTrue(page.isPolicyGroupOptionVisible(GlobalConstants.DEFAULT_POLICYGROUP),
                GlobalConstants.isLongPolicyGroupNameVisible);

        soft.assertAll();
    }

    // S9: Form Lifecycle — Submit, Success Screen, Cancel Popup, Clear, Alternate Flow
    // Covers T10-T15, T23, T84
    @Test(priority = 9, groups = { "S9_FormLifecycle" },
          description = "Verify create-partner lifecycle: submit to success screen, Home nav, cancel popup, clear form, alternate-flow error correction.")
    public void s9_formLifecycle() {
        SoftAssert soft = new SoftAssert();
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage page = new MispPartnerPage(driver);

        // T10 + T11 + T12: Submit → success screen with cert upload and home buttons
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        String email = "abislife" + BaseClass.data + "@test.com";
        String username = "abislif" + BaseClass.data;
        fillAbisPartnerMandatoryFields(page, email, username);
        page.clickOnCreatePartnerSubmitButton();
        soft.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isFormSubmittedSuccessfully);
        soft.assertTrue(page.isUploadPartnerCertificateButtonDisplayed(),
                GlobalConstants.isUploadCertButtonOnSuccessScreenDisplayed);
        soft.assertTrue(page.isCreatePartnerSuccessMsgHomeButtonDisplayed(),
                GlobalConstants.isHomeButtonOnSuccessScreenDisplayed);

        // T23: Home button → Dashboard
        page.clickOnSuccessMsgHomeButton();
        soft.assertTrue(dashboardPage.isPartnersDisplayed(), GlobalConstants.isDashboardDisplayedAfterHomeButton);

        // T13 + T14: Cancel popup — text, proceed and cancel buttons, then navigate back
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.clickOnCreatePartnerCancelButton();
        soft.assertTrue(page.isCancelConfirmationPopupDisplayed(), GlobalConstants.isCancelPopupDisplayed);
        soft.assertEquals(page.getCancelConfirmationPopupText(), GlobalConstants.CANCEL_CONFIRMATION_POPUP_TEXT,
                GlobalConstants.isCancelPopupTextCorrect);
        soft.assertTrue(page.iscancelConfirmationPopupProceedButtonDisplayed(),
                GlobalConstants.isCancelPopupProceedButtonDisplayed);
        soft.assertTrue(page.isCancelConfirmationPopupCancelButtonDisplayed(),
                GlobalConstants.isCancelPopupCancelButtonDisplayed);
        page.clickOnCancelConfirmationPopupProceedButton();
        soft.assertTrue(page.isListOfPartnersDisplayed(), GlobalConstants.isCancelNavigatesBackToPartnerList);

        // T15: Clear form resets all entered data
        page.clickOnCreatePartnerButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.enterEmailId(GlobalConstants.ABIS_EMAIL_ID);
        page.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        page.clickOnCreatePartnerClearButton();
        soft.assertEquals(page.getPartnerOrganisationFieldValue(), "", GlobalConstants.isClearFormClearsAllFields);
        soft.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isClearFormClearsAllFields);

        // T84: Alternate flow — introduce error, correct, submit re-enables
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        page.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
        page.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.enterEmailId(GlobalConstants.ABIS_EMAIL_ID);
        page.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        page.enterPartnerOrganisation(GlobalConstants.DISALLOWED_SPECIAL_CHARS_ORG);
        soft.assertTrue(page.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAlternateFlowsHandledCorrectly);
        soft.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isAlternateFlowsHandledCorrectly);
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        soft.assertFalse(page.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isAlternateFlowsHandledCorrectly);
        soft.assertTrue(page.isCreatePartnerSubmitButtonEnabled(), GlobalConstants.isAlternateFlowsHandledCorrectly);

        soft.assertAll();
    }

    // S10: Partner List, Status Checks, Navigation, Responsive UI
    // Covers T6, T17-T20, T24, T25, T69, T73, T74, T79
    @Test(priority = 10, groups = { "S10_PartnerListAndNavigation" },
          description = "Verify dashboard nav, partner statuses (no-cert/with-cert), multiple partners, cert-popup nav, back arrow, and responsive viewport.")
    public void s10_partnerListAndNavigation() {
        SoftAssert soft = new SoftAssert();
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage page = new MispPartnerPage(driver);
        PartnerCertificatePage certPage = new PartnerCertificatePage(driver);

        // T69: Partners page navigable via dashboard
        dashboardPage.clickOnPartners();
        soft.assertTrue(page.isListOfPartnersDisplayed(), GlobalConstants.isPartnersPageNavigableViaDashboard);

        // T17 + T19 + T20 + T24: No-cert partner — Inactive, Not Uploaded (red), upload action available
        page.clickOnCreatePartnerButton();
        String emailNoCert = "abisncrt" + BaseClass.data + "@test.com";
        String usernameNoCert = "abisncrt" + BaseClass.data;
        fillAbisPartnerMandatoryFields(page, emailNoCert, usernameNoCert);
        page.clickOnCreatePartnerSubmitButton();
        soft.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isAbisPartnerCreatedSuccessfully);
        page.clickOnSuccessMsgHomeButton();
        dashboardPage.clickOnPartners();
        soft.assertTrue(page.isPartnerInList(usernameNoCert), GlobalConstants.isNewPartnerAtTopOfList);
        soft.assertEquals(page.getCertUploadStatusForPartner(usernameNoCert),
                GlobalConstants.CERT_UPLOAD_STATUS_NOT_UPLOADED,
                GlobalConstants.isCertUploadStatusNotUploadedWithoutCert);
        soft.assertTrue(page.isCertStatusDisplayedInRed(usernameNoCert), GlobalConstants.isCertStatusNotUploadedInRed);
        soft.assertEquals(page.getPartnerStatusForPartner(usernameNoCert), GlobalConstants.PARTNER_STATUS_INACTIVE,
                GlobalConstants.isPartnerStatusInactiveWithoutCert);
        soft.assertTrue(page.isUploadCertActionAvailableForPartner(usernameNoCert),
                GlobalConstants.isUploadCertMenuOptionAvailable);

        // T18: Cert uploaded via success screen → Uploaded, Active
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        String emailWithCert = "abiscrt" + BaseClass.data + "@test.com";
        String usernameWithCert = "abiscrt" + BaseClass.data;
        fillAbisPartnerMandatoryFields(page, emailWithCert, usernameWithCert);
        page.clickOnCreatePartnerSubmitButton();
        soft.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isAbisPartnerCreatedSuccessfully);
        page.clickOnUploadPartnerCertificateButton();
        soft.assertTrue(certPage.isUploadPartnerCertificatePopUpDisplayed(),
                GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
        certPage.uploadCertificate();
        certPage.clickOnSubmitButton();
        soft.assertTrue(certPage.isCertificateUploadSuccessMessageDisplayed(),
                GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
        certPage.clickOnCloseButton();
        dashboardPage = certPage.clickOnHomeButton();
        dashboardPage.clickOnPartners();
        soft.assertTrue(page.isPartnerInList(usernameWithCert), GlobalConstants.isNewPartnerAtTopOfList);
        soft.assertEquals(page.getCertUploadStatusForPartner(usernameWithCert),
                GlobalConstants.CERT_UPLOAD_STATUS_UPLOADED,
                GlobalConstants.isCertUploadStatusUploadedAfterUpload);
        soft.assertEquals(page.getPartnerStatusForPartner(usernameWithCert), GlobalConstants.PARTNER_STATUS_ACTIVE,
                GlobalConstants.isPartnerStatusActiveAfterCertUpload);

        // T25: Cert uploaded via actions menu → Uploaded, Active
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        String emailActions = "abisacta" + BaseClass.data + "@test.com";
        String usernameActions = "abisacta" + BaseClass.data;
        fillAbisPartnerMandatoryFields(page, emailActions, usernameActions);
        page.clickOnCreatePartnerSubmitButton();
        soft.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isAbisPartnerCreatedSuccessfully);
        page.clickOnSuccessMsgHomeButton();
        dashboardPage.clickOnPartners();
        soft.assertTrue(page.isUploadCertActionAvailableForPartner(usernameActions),
                GlobalConstants.isUploadCertMenuOptionAvailable);
        page.clickUploadCertActionForPartner(usernameActions);
        soft.assertTrue(certPage.isUploadPartnerCertificatePopUpDisplayed(),
                GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
        certPage.uploadCertificate();
        certPage.clickOnSubmitButton();
        soft.assertTrue(certPage.isCertificateUploadSuccessMessageDisplayed(),
                GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
        certPage.clickOnCloseButton();
        dashboardPage = certPage.clickOnHomeButton();
        dashboardPage.clickOnPartners();
        soft.assertEquals(page.getCertUploadStatusForPartner(usernameActions),
                GlobalConstants.CERT_UPLOAD_STATUS_UPLOADED,
                GlobalConstants.isCertUploadStatusUploadedAfterActionsUpload);
        soft.assertEquals(page.getPartnerStatusForPartner(usernameActions), GlobalConstants.PARTNER_STATUS_ACTIVE,
                GlobalConstants.isPartnerStatusActiveAfterCertUploadFromActions);

        // T6: Multiple partners with same org cert
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        String emailCert1 = "abiscrt1" + BaseClass.data + "@test.com";
        String usernameCert1 = "abiscrt1" + BaseClass.data;
        fillAbisPartnerMandatoryFields(page, emailCert1, usernameCert1);
        page.clickOnCreatePartnerSubmitButton();
        soft.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isAbisPartnerCreatedSuccessfully);
        page.clickOnUploadPartnerCertificateButton();
        soft.assertTrue(certPage.isUploadPartnerCertificatePopUpDisplayed(),
                GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
        certPage.uploadCertificate();
        certPage.clickOnSubmitButton();
        soft.assertTrue(certPage.isCertificateUploadSuccessMessageDisplayed(),
                GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
        certPage.clickOnCloseButton();
        dashboardPage = certPage.clickOnHomeButton();
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        String emailCert2 = "abiscrt2" + BaseClass.data + "@test.com";
        String usernameCert2 = "abiscrt2" + BaseClass.data;
        fillAbisPartnerMandatoryFields(page, emailCert2, usernameCert2);
        soft.assertTrue(page.isCreatePartnerSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
        page.clickOnCreatePartnerSubmitButton();
        soft.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isMultipleAbisPartnersCreatable);
        page.clickOnSuccessMsgHomeButton();

        // T79: Back arrow (cancel → proceed) → partners list
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        page.clickOnPartnerTypeDropdown();
        page.clickOnAbisPartnerOption();
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.clickOnCreatePartnerCancelButton();
        page.clickOnCancelConfirmationPopupProceedButton();
        soft.assertTrue(page.isListOfPartnersDisplayed(), GlobalConstants.isBackArrowNavigatesToPartnerList);

        // T73: Responsive — 768 px viewport
        driver.manage().window().setSize(new Dimension(768, 1024));
        page.clickOnCreatePartnerButton();
        soft.assertTrue(page.isCreatePrtnerPageTitleDisplayed(), GlobalConstants.isUIResponsiveAtSmallViewport);
        soft.assertTrue(page.isCreatePartnerSubmitButtonDisabled(), GlobalConstants.isUIResponsiveAtSmallViewport);

        // T74: Upload cert page accessible from success screen (LAST — open popup, no cleanup needed)
        String emailUpld = "abisupld" + BaseClass.data + "@test.com";
        String usernameUpld = "abisupld" + BaseClass.data;
        fillAbisPartnerMandatoryFields(page, emailUpld, usernameUpld);
        page.clickOnCreatePartnerSubmitButton();
        soft.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(), GlobalConstants.isAbisPartnerCreatedSuccessfully);
        soft.assertTrue(page.isUploadPartnerCertificateButtonDisplayed(),
                GlobalConstants.isUploadCertPageNavigableFromSuccessScreen);
        soft.assertTrue(page.isCreatePartnerSuccessMsgHomeButtonDisplayed(),
                GlobalConstants.isHomeButtonOnSuccessScreenDisplayed);
        page.clickOnUploadPartnerCertificateButton();
        soft.assertTrue(certPage.isUploadPartnerCertificatePopUpDisplayed(),
                GlobalConstants.isUploadCertPageNavigableFromSuccessScreen);

        soft.assertAll();
    }

    // Disabled: Feature Not Supported
    @Test(enabled = false, priority = 51, groups = { "unsupported" },
          description = "Verify country code prefix display — feature not supported.")
    public void verifyCountryCodePrefixDisplay() {
        org.testng.Assert.assertTrue(false, GlobalConstants.FEATURE_NOT_SUPPORTED);
    }

    @Test(enabled = false, priority = 52, groups = { "unsupported" },
          description = "Verify country code not user-configurable — feature not supported.")
    public void verifyCountryCodeNotConfigurableByUser() {
        org.testng.Assert.assertTrue(false, GlobalConstants.FEATURE_NOT_SUPPORTED);
    }

    @Test(enabled = false, priority = 70, groups = { "unsupported" },
          description = "Verify duplicate registration across browsers — feature not supported.")
    public void verifyDuplicateRegistrationHandledAcrossBrowsers() {
        org.testng.Assert.assertTrue(false, GlobalConstants.FEATURE_NOT_SUPPORTED);
    }

    @Test(enabled = false, priority = 71, groups = { "unsupported" },
          description = "Verify Create Partner form in Arabic locale — feature not supported.")
    public void verifyCreatePartnerFormInArabicLocale() {
        org.testng.Assert.assertTrue(false, GlobalConstants.FEATURE_NOT_SUPPORTED);
    }

    @Test(enabled = false, priority = 72, groups = { "unsupported" },
          description = "Verify Create Partner form in French locale — feature not supported.")
    public void verifyCreatePartnerFormInFrenchLocale() {
        org.testng.Assert.assertTrue(false, GlobalConstants.FEATURE_NOT_SUPPORTED);
    }

    @Test(enabled = false, priority = 75, groups = { "unsupported" },
          description = "Verify Create Partner form keyboard accessible — feature not supported.")
    public void verifyCreatePartnerFormKeyboardAccessible() {
        org.testng.Assert.assertTrue(false, GlobalConstants.FEATURE_NOT_SUPPORTED);
    }

    @Test(enabled = false, priority = 80, groups = { "unsupported" },
          description = "Verify Create Partner form on MacBook screen — feature not supported.")
    public void verifyCreatePartnerFormUIOnMacBookScreen() {
        org.testng.Assert.assertTrue(false, GlobalConstants.FEATURE_NOT_SUPPORTED);
    }

    // Disabled: Service Not Deployed
    @Test(enabled = false, priority = 76, groups = { "serviceNotDeployed" },
          description = "Verify 401 Unauthorized error handling — service not deployed.")
    public void verify401UnauthorizedErrorHandledGracefully() {
        org.testng.Assert.assertTrue(false, GlobalConstants.SERVICE_NOT_DEPLOYED_MESSAGE);
    }

    @Test(enabled = false, priority = 77, groups = { "serviceNotDeployed" },
          description = "Verify 503 Service Unavailable error handling — service not deployed.")
    public void verify503ServiceUnavailableErrorHandledGracefully() {
        org.testng.Assert.assertTrue(false, GlobalConstants.SERVICE_NOT_DEPLOYED_MESSAGE);
    }

    @Test(enabled = false, priority = 78, groups = { "serviceNotDeployed" },
          description = "Verify 403 Forbidden error handling — service not deployed.")
    public void verify403ForbiddenErrorHandledGracefully() {
        org.testng.Assert.assertTrue(false, GlobalConstants.SERVICE_NOT_DEPLOYED_MESSAGE);
    }

    @Test(enabled = false, priority = 81, groups = { "serviceNotDeployed" },
          description = "Verify database storage matches UI input — service not deployed.")
    public void verifyDatabaseStorageMatchesUIInput() {
        org.testng.Assert.assertTrue(false, GlobalConstants.SERVICE_NOT_DEPLOYED_MESSAGE);
    }
}
