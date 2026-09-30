package io.mosip.testrig.pmpuiv2.testcase;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "OnlineVerificationPartnerTest" })
public class OnlineVerificationPartnerTest extends BaseClass {

    private MispPartnerPage navigateToCreatePartnerPage() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
        dashboardPage.clickOnPartners();
        mispPartnerPage.clickOnCreatePartnerButton();
        return mispPartnerPage;
    }

    private void selectOnlineVerificationPartner(MispPartnerPage page) {
        page.clickOnPartnerTypeDropdown();
        page.clickOnPartnerTypeOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
    }

    private void fillMandatoryFieldsExcept(MispPartnerPage page, String skippedField) {
        selectOnlineVerificationPartner(page);
        if (!"policyGroup".equals(skippedField)) {
            page.selectFirstActivePolicyGroup();
        }
        if (!"notificationLanguage".equals(skippedField)) {
            page.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        }
        if (!"organisation".equals(skippedField)) {
            page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        }
        if (!"address".equals(skippedField)) {
            page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        }
        if (!"phone".equals(skippedField)) {
            page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        }
        if (!"email".equals(skippedField)) {
            page.enterEmailId(GlobalConstants.ABIS_EMAIL_ID);
        }
        if (!"username".equals(skippedField)) {
            page.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        }
    }

    @Test(priority = 6, description = "Verify User can create a partner without policy group")
    public void policyGroupIsMandatory() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(page, "policyGroup");
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isPolicyGroupMandatoryForOnlineVerificationPartner);
    }

    @Test(priority = 7, description = "Check the Title of the screen")
    public void createPartnerPageTitle() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        Assert.assertTrue(page.isCreatePrtnerPageTitleDisplayed(), GlobalConstants.isCreatePartnerPageTitleCorrect);
        Assert.assertEquals(page.getCreatePartnerPageTitleText(), GlobalConstants.CREATE_PARTNER_PAGE_TITLE,
                GlobalConstants.isCreatePartnerPageTitleCorrect);
    }

    @Test(priority = 8, description = "Check the breadcrumb of the Create Partner screen")
    public void createPartnerBreadcrumb() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        Assert.assertEquals(page.getBreadcrumbTextOfCreatePartnerPage(),
                GlobalConstants.BREADCUMB_TEXT_OF_CREATE_PARTNER, GlobalConstants.isBreadcrumbClickable);
    }

    @Test(priority = 9, description = "Check the subtitle of the Create Partner screen")
    public void createPartnerSubtitle() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        Assert.assertTrue(page.isCreatePartnerMandatoryFieldInfoDisplayed(),
                GlobalConstants.isMandatoryFieldInfoTextCorrect);
        Assert.assertEquals(page.getCreatePartnerMandatoryFieldInfoText(), GlobalConstants.MANDATORY_FIELD_INFO_TEXT,
                GlobalConstants.isMandatoryFieldInfoTextCorrect);
    }

    @Test(priority = 10, description = "Verify if user can navigate to create partner screen from the Hamburger menu")
    public void navigateFromHamburgerMenu() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage page = new MispPartnerPage(driver);
        dashboardPage.clickOnHamburgerOpen();
        dashboardPage.clickOnPartnerOfHamburger();
        page.clickOnCreatePartnerButton();
        selectOnlineVerificationPartner(page);
        Assert.assertTrue(page.isCreatePrtnerPageTitleDisplayed(),
                GlobalConstants.isCreatePartnerNavigableFromHamburger);
        Assert.assertEquals(page.getCreatePartnerPageTitleText(), GlobalConstants.CREATE_PARTNER_PAGE_TITLE,
                GlobalConstants.isCreatePartnerNavigableFromHamburger);
        Assert.assertEquals(page.getSelectedPartnerTypeText(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
                GlobalConstants.isOnlineVerificationPartnerSelectedSuccessfully);
    }

    @Test(priority = 11, description = "Verify the partner type dropdown list")
    public void partnerTypeDropdownList() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        page.clickOnPartnerTypeDropdown();
        List<String> options = page.getPartnerTypeDropdownOptionTexts();
        Assert.assertTrue(options.contains(GlobalConstants.ABIS_PARTNER),
                GlobalConstants.isAbisPartnerOptionDisplayed);
        Assert.assertTrue(options.contains(GlobalConstants.MISP_PARTNER),
                GlobalConstants.isMispPartnerOptionDisplayed);
        Assert.assertTrue(options.contains(GlobalConstants.MANUAL_ADJUDICATION_PARTNER),
                GlobalConstants.isManualAdjudicationPartnerOptionDisplayed);
        Assert.assertTrue(options.contains(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
                GlobalConstants.isOnlineVerificationPartnerOptionDisplayed);
    }

    @Test(priority = 12, description = "Verify user is able to select the Online Verification Partner from the dropdown")
    public void selectOnlineVerificationPartnerFromDropdown() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        Assert.assertEquals(page.getSelectedPartnerTypeText(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
                GlobalConstants.isOnlineVerificationPartnerSelectedSuccessfully);
    }

    @Test(priority = 15, description = "Verify User is able to create a partner without adding address")
    public void addressIsMandatory() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(page, "address");
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isAddressMandatoryForOnlineVerificationPartner);
    }

    @Test(priority = 16, description = "Verify User is able to create a partner without selecting Organisation Name")
    public void organisationNameIsMandatory() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(page, "organisation");
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isOrganisationNameMandatoryForOnlineVerificationPartner);
    }

    @Test(priority = 17, description = "Verify User is able to create a partner without adding phone number")
    public void phoneNumberIsMandatory() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(page, "phone");
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isPhoneNumberMandatoryForOnlineVerificationPartner);
    }

    @Test(priority = 18, description = "Verify User is able to create a partner without adding email ID")
    public void emailIsMandatory() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(page, "email");
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isEmailMandatoryForOnlineVerificationPartner);
    }

    @Test(priority = 19, description = "Verify User is able to create a partner without adding username")
    public void usernameIsMandatory() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(page, "username");
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isUsernameMandatoryForOnlineVerificationPartner);
    }

    @Test(priority = 20, description = "Verify adding username with only special characters")
    public void usernameSpecialCharactersOnly() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        page.enterUserName("###");
        Assert.assertTrue(page.isUsernameMustStartWithLetterErrorDisplayed(),
                GlobalConstants.isUsernameMustStartWithLetterForOnlineVerificationPartner);
        Assert.assertEquals(page.getUserNameValidationErrorText(), GlobalConstants.USERNAME_MUST_START_WITH_LETTER,
                GlobalConstants.isUsernameMustStartWithLetterForOnlineVerificationPartner);
    }

    @Test(priority = 21, description = "Verify adding username combination of letters and special characters")
    public void usernameLettersAndSpecialCharacters() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        page.enterUserName("swe#$$$%%^^^");
        Assert.assertEquals(page.getUserNameValidationErrorText(), GlobalConstants.USERNAME_ALLOWED_CHARACTERS_ERROR,
                GlobalConstants.isUsernameInvalidCharacterForOnlineVerificationPartner);
    }

    @Test(priority = 22, description = "Verify adding username combination of letters and digits")
    public void usernameStartingWithDigits() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        page.enterUserName("12344swe");
        Assert.assertTrue(page.isUsernameMustStartWithLetterErrorDisplayed(),
                GlobalConstants.isUsernameMustStartWithLetterForOnlineVerificationPartner);
        Assert.assertEquals(page.getUserNameValidationErrorText(), GlobalConstants.USERNAME_MUST_START_WITH_LETTER,
                GlobalConstants.isUsernameMustStartWithLetterForOnlineVerificationPartner);
    }

    @Test(priority = 23, description = "Verify creating a partner without selecting notification type")
    public void notificationLanguageIsMandatory() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(page, "notificationLanguage");
        Assert.assertTrue(page.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isNotificationLanguageMandatoryForOnlineVerificationPartner);
    }

    @Test(priority = 25, description = "Verify clear form button is clickable")
    public void clearFormButtonIsClickable() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        Assert.assertTrue(page.isCreatePartnerClearButtonEnabled(), GlobalConstants.isClearFormButtonClickable);
        page.clickOnCreatePartnerClearButton();
    }

    @Test(priority = 26, description = "Verify clicking on clear form button")
    public void clearFormClearsEnteredDetails() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        page.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        page.clickOnCreatePartnerClearButton();
        Assert.assertEquals(page.getPartnerOrganisationFieldValue(), "", GlobalConstants.isClearFormClearsAllFields);
        Assert.assertEquals(page.getPartnerAddressFieldValue(), "", GlobalConstants.isClearFormClearsAllFields);
        Assert.assertEquals(page.getUserNameFieldValue(), "", GlobalConstants.isClearFormClearsAllFields);
    }

    @Test(priority = 27, description = "Verify clicking on cancel button before adding any field")
    public void cancelWithoutDetailsReturnsToPartnerList() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        page.clickOnCreatePartnerCancelButton();
        Assert.assertTrue(page.isListOfPartnersDisplayed(),
                GlobalConstants.isCancelWithoutDataReturnsToPartnerList);
    }

    @Test(priority = 32, description = "Verify on clicking list of partners button")
    public void listOfPartnersButtonReturnsToPartnerList() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        page.clickOnListOfPartnerButton();
        Assert.assertTrue(page.isListOfPartnersDisplayed(),
                GlobalConstants.isListOfPartnersButtonReturnsToPartnerList);
    }

    @Test(priority = 28, description = "Verify clicking on cancel button after adding any field")
    public void cancelAfterDetailsShowsConfirmation() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.clickOnCreatePartnerCancelButton();
        Assert.assertTrue(page.isCancelConfirmationPopupDisplayed(),
                GlobalConstants.isCancelAfterDataShowsConfirmation);
        Assert.assertEquals(page.getCancelConfirmationPopupText(), GlobalConstants.CANCEL_CONFIRMATION_POPUP_TEXT,
                GlobalConstants.isCancelPopupTextCorrect);
    }

    @Test(priority = 29, description = "Verify on clicking proceed button in the popup")
    public void cancelPopupProceedReturnsToPartnerList() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.clickOnCreatePartnerCancelButton();
        page.clickOnCancelConfirmationPopupProceedButton();
        Assert.assertTrue(page.isListOfPartnersDisplayed(),
                GlobalConstants.isCancelPopupProceedReturnsToPartnerList);
    }

    @Test(priority = 30, description = "Verify on clicking cancel button in the popup")
    public void cancelPopupCancelKeepsCreatePartnerPage() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.clickOnCreatePartnerCancelButton();
        page.clickOnCancelConfirmationPopupCancelButton();
        Assert.assertTrue(page.isCreatePrtnerPageTitleDisplayed(),
                GlobalConstants.isCancelPopupCancelKeepsCreatePartnerPage);
        Assert.assertEquals(page.getPartnerOrganisationFieldValue(), GlobalConstants.ORGANISATION_NAME,
                GlobalConstants.isCancelPopupCancelKeepsCreatePartnerPage);
    }

    @Test(priority = 31, description = "Verify on clicking home button in the screen")
    public void homeButtonReturnsToDashboard() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage page = navigateToCreatePartnerPage();
        page.clickOnCreatePartnerHomeButton();
        Assert.assertTrue(dashboardPage.isPartnersDisplayed(),
                GlobalConstants.isCreatePartnerHomeReturnsToDashboard);
    }

    @Test(priority = 36, description = "Verify the organisation info field")
    public void organizationNameInfoMessage() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        page.clickOnPartnerOragnizationInfoButton();
        Assert.assertTrue(page.isOrganizationNameInfoDisplayed(), GlobalConstants.isOrganizationInfoMessageCorrect);
        Assert.assertEquals(page.getOrganizationNameInfoText(), GlobalConstants.ORG_NAME_INFO_TEXT,
                GlobalConstants.isOrganizationInfoMessageCorrect);
    }

    @Test(priority = 40, description = "Verify adding organisation name exceeding 128 characters")
    public void organizationNameMaxLength() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        page.enterPartnerOrganisation("A".repeat(130));
        Assert.assertTrue(page.getPartnerOrganisationFieldValue().length() <= GlobalConstants.ORG_NAME_MAX_LENGTH,
                GlobalConstants.isOrgNameMaxLengthEnforcedForOnlineVerificationPartner);
    }

    @Test(priority = 41, description = "Verify adding organisation name with special characters only")
    public void organizationNameDisallowedCharacters() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        page.enterPartnerOrganisation(GlobalConstants.DISALLOWED_SPECIAL_CHARS_ORG);
        Assert.assertTrue(page.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isOrgInvalidCharactersRejectedForOnlineVerificationPartner);
        Assert.assertEquals(page.getOrganizationNameValidationErrorText(), GlobalConstants.ORG_INVALID_CHARACTER_ERROR,
                GlobalConstants.isOrgInvalidCharactersRejectedForOnlineVerificationPartner);
    }

    @Test(priority = 42, description = "Verify adding address details with maximum characters")
    public void addressMaxLength() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        page.enterPartnerAddress("A".repeat(2001));
        Assert.assertTrue(page.getPartnerAddressFieldValue().length() <= GlobalConstants.ADDRESS_MAX_LENGTH,
                GlobalConstants.isAddressMaxLengthEnforcedForOnlineVerificationPartner);
    }

    @Test(priority = 43, description = "Verify adding email address with maximum characters")
    public void emailMaxLength() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        page.enterEmailId("a".repeat(255));
        Assert.assertTrue(page.getEmailFieldValue().length() <= GlobalConstants.EMAIL_MAX_LENGTH,
                GlobalConstants.isEmailMaxLengthEnforcedForOnlineVerificationPartner);
    }

    @Test(priority = 46, description = "Verify adding username with maximum characters")
    public void usernameMaxLength() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(page);
        page.enterUserName("a".repeat(37));
        Assert.assertTrue(page.getUserNameFieldValue().length() <= GlobalConstants.USERNAME_MAX_LENGTH,
                GlobalConstants.isUsernameMaxLengthEnforcedForOnlineVerificationPartner);
    }

    @Test(priority = 39, description = "Verify adding existing email ID")
    public void existingEmailIsRejected() {
        MispPartnerPage page = navigateToCreatePartnerPage();
        String sharedEmail = "ovpmail" + BaseClass.data + "@test.com";
        selectOnlineVerificationPartner(page);
        page.selectFirstActivePolicyGroup();
        page.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.enterEmailId(sharedEmail);
        page.enterUserName("ovpuser1" + BaseClass.data);
        page.clickOnCreatePartnerSubmitButton();
        Assert.assertTrue(page.isCreatePartnerSuccessMsgDisplayed(),
                GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
        page.clickOnSuccessMsgHomeButton();

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickOnPartners();
        page.clickOnCreatePartnerButton();
        selectOnlineVerificationPartner(page);
        page.selectFirstActivePolicyGroup();
        page.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        page.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        page.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        page.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        page.enterEmailId(sharedEmail);
        page.enterUserName("ovpuser2" + BaseClass.data);
        page.clickOnCreatePartnerSubmitButton();
        Assert.assertTrue(page.isEmailAddressIsAlreadyRegisteredErrorDisplayed(),
                GlobalConstants.isEmailAlreadyRegisteredErrorDisplayed);
        Assert.assertEquals(page.getEmailAlreadyRegisteredErrorText(),
                GlobalConstants.EMAIL_ALREADY_REGISTERED_ERROR_MSG,
                GlobalConstants.isEmailAlreadyRegisteredErrorTextCorrect);
    }
}
