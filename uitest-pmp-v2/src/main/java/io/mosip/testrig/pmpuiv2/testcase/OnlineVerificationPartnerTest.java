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

    private void selectOnlineVerificationPartner(MispPartnerPage mispPartnerPage) {
        mispPartnerPage.clickOnPartnerTypeDropdown();
        mispPartnerPage.clickOnPartnerTypeOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
    }

    private void fillMandatoryFieldsExcept(MispPartnerPage mispPartnerPage, String skippedField) {
        selectOnlineVerificationPartner(mispPartnerPage);
        if (!"policyGroup".equals(skippedField)) {
            mispPartnerPage.selectFirstActivePolicyGroup();
        }
        if (!"notificationLanguage".equals(skippedField)) {
            mispPartnerPage.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        }
        if (!"organisation".equals(skippedField)) {
            mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        }
        if (!"address".equals(skippedField)) {
            mispPartnerPage.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        }
        if (!"phone".equals(skippedField)) {
            mispPartnerPage.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        }
        if (!"email".equals(skippedField)) {
            mispPartnerPage.enterEmailId(GlobalConstants.ABIS_EMAIL_ID);
        }
        if (!"username".equals(skippedField)) {
            mispPartnerPage.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        }
    }

    @Test(priority = 6, description = "Verify User can create a partner without policy group")
    public void policyGroupIsMandatory() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, "policyGroup");
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isPolicyGroupMandatoryForOnlineVerificationPartner);
    }

    @Test(priority = 7, description = "Check the Title of the screen")
    public void createPartnerPageTitle() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        Assert.assertTrue(mispPartnerPage.isCreatePrtnerPageTitleDisplayed(), GlobalConstants.isCreatePartnerPageTitleCorrect);
        Assert.assertEquals(mispPartnerPage.getCreatePartnerPageTitleText(), GlobalConstants.CREATE_PARTNER_PAGE_TITLE,
                GlobalConstants.isCreatePartnerPageTitleCorrect);
    }

    @Test(priority = 8, description = "Check the breadcrumb of the Create Partner screen")
    public void createPartnerBreadcrumb() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        Assert.assertEquals(mispPartnerPage.getBreadcrumbTextOfCreatePartnerPage(),
                GlobalConstants.BREADCUMB_TEXT_OF_CREATE_PARTNER, GlobalConstants.isBreadcrumbClickable);
    }

    @Test(priority = 9, description = "Check the subtitle of the Create Partner screen")
    public void createPartnerSubtitle() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        Assert.assertTrue(mispPartnerPage.isCreatePartnerMandatoryFieldInfoDisplayed(),
                GlobalConstants.isMandatoryFieldInfoTextCorrect);
        Assert.assertEquals(mispPartnerPage.getCreatePartnerMandatoryFieldInfoText(), GlobalConstants.MANDATORY_FIELD_INFO_TEXT,
                GlobalConstants.isMandatoryFieldInfoTextCorrect);
    }

    @Test(priority = 10, description = "Verify if user can navigate to create partner screen from the Hamburger menu")
    public void navigateFromHamburgerMenu() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
        dashboardPage.clickOnHamburgerOpen();
        dashboardPage.clickOnPartnerOfHamburger();
        mispPartnerPage.clickOnCreatePartnerButton();
        selectOnlineVerificationPartner(mispPartnerPage);
        Assert.assertTrue(mispPartnerPage.isCreatePrtnerPageTitleDisplayed(),
                GlobalConstants.isCreatePartnerNavigableFromHamburger);
        Assert.assertEquals(mispPartnerPage.getCreatePartnerPageTitleText(), GlobalConstants.CREATE_PARTNER_PAGE_TITLE,
                GlobalConstants.isCreatePartnerNavigableFromHamburger);
        Assert.assertEquals(mispPartnerPage.getSelectedPartnerTypeText(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
                GlobalConstants.isOnlineVerificationPartnerSelectedSuccessfully);
    }

    @Test(priority = 11, description = "Verify the partner type dropdown list")
    public void partnerTypeDropdownList() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        mispPartnerPage.clickOnPartnerTypeDropdown();
        List<String> options = mispPartnerPage.getPartnerTypeDropdownOptionTexts();
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
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        Assert.assertEquals(mispPartnerPage.getSelectedPartnerTypeText(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
                GlobalConstants.isOnlineVerificationPartnerSelectedSuccessfully);
    }

    @Test(priority = 15, description = "Verify User is able to create a partner without adding address")
    public void addressIsMandatory() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, "address");
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isAddressMandatoryForOnlineVerificationPartner);
    }

    @Test(priority = 16, description = "Verify User is able to create a partner without selecting Organisation Name")
    public void organisationNameIsMandatory() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, "organisation");
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isOrganisationNameMandatoryForOnlineVerificationPartner);
    }

    @Test(priority = 17, description = "Verify User is able to create a partner without adding phone number")
    public void phoneNumberIsMandatory() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, "phone");
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isPhoneNumberMandatoryForOnlineVerificationPartner);
    }

    @Test(priority = 18, description = "Verify User is able to create a partner without adding email ID")
    public void emailIsMandatory() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, "email");
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isEmailMandatoryForOnlineVerificationPartner);
    }

    @Test(priority = 19, description = "Verify User is able to create a partner without adding username")
    public void usernameIsMandatory() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, "username");
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isUsernameMandatoryForOnlineVerificationPartner);
    }

    @Test(priority = 20, description = "Verify adding username with only special characters")
    public void usernameSpecialCharactersOnly() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterUserName("###");
        Assert.assertTrue(mispPartnerPage.isUsernameMustStartWithLetterErrorDisplayed(),
                GlobalConstants.isUsernameMustStartWithLetterForOnlineVerificationPartner);
        Assert.assertEquals(mispPartnerPage.getUserNameValidationErrorText(), GlobalConstants.USERNAME_MUST_START_WITH_LETTER,
                GlobalConstants.isUsernameMustStartWithLetterForOnlineVerificationPartner);
    }

    @Test(priority = 21, description = "Verify adding username combination of letters and special characters")
    public void usernameLettersAndSpecialCharacters() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterUserName("swe#$$$%%^^^");
        Assert.assertEquals(mispPartnerPage.getUserNameValidationErrorText(), GlobalConstants.USERNAME_ALLOWED_CHARACTERS_ERROR,
                GlobalConstants.isUsernameInvalidCharacterForOnlineVerificationPartner);
    }

    @Test(priority = 22, description = "Verify adding username combination of letters and digits")
    public void usernameStartingWithDigits() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterUserName("12344swe");
        Assert.assertTrue(mispPartnerPage.isUsernameMustStartWithLetterErrorDisplayed(),
                GlobalConstants.isUsernameMustStartWithLetterForOnlineVerificationPartner);
        Assert.assertEquals(mispPartnerPage.getUserNameValidationErrorText(), GlobalConstants.USERNAME_MUST_START_WITH_LETTER,
                GlobalConstants.isUsernameMustStartWithLetterForOnlineVerificationPartner);
    }

    @Test(priority = 23, description = "Verify creating a partner without selecting notification type")
    public void notificationLanguageIsMandatory() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, "notificationLanguage");
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isNotificationLanguageMandatoryForOnlineVerificationPartner);
    }

    @Test(priority = 25, description = "Verify clear form button is clickable")
    public void clearFormButtonIsClickable() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        Assert.assertTrue(mispPartnerPage.isCreatePartnerClearButtonEnabled(), GlobalConstants.isClearFormButtonClickable);
        mispPartnerPage.clickOnCreatePartnerClearButton();
    }

    @Test(priority = 26, description = "Verify clicking on clear form button")
    public void clearFormClearsEnteredDetails() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        mispPartnerPage.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        mispPartnerPage.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        mispPartnerPage.clickOnCreatePartnerClearButton();
        Assert.assertEquals(mispPartnerPage.getPartnerOrganisationFieldValue(), "", GlobalConstants.isClearFormClearsAllFields);
        Assert.assertEquals(mispPartnerPage.getPartnerAddressFieldValue(), "", GlobalConstants.isClearFormClearsAllFields);
        Assert.assertEquals(mispPartnerPage.getUserNameFieldValue(), "", GlobalConstants.isClearFormClearsAllFields);
    }

    @Test(priority = 27, description = "Verify clicking on cancel button before adding any field")
    public void cancelWithoutDetailsReturnsToPartnerList() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.clickOnCreatePartnerCancelButton();
        Assert.assertTrue(mispPartnerPage.isListOfPartnersDisplayed(),
                GlobalConstants.isCancelWithoutDataReturnsToPartnerList);
    }

    @Test(priority = 32, description = "Verify on clicking list of partners button")
    public void listOfPartnersButtonReturnsToPartnerList() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        mispPartnerPage.clickOnListOfPartnerButton();
        Assert.assertTrue(mispPartnerPage.isListOfPartnersDisplayed(),
                GlobalConstants.isListOfPartnersButtonReturnsToPartnerList);
    }

    @Test(priority = 28, description = "Verify clicking on cancel button after adding any field")
    public void cancelAfterDetailsShowsConfirmation() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        mispPartnerPage.clickOnCreatePartnerCancelButton();
        Assert.assertTrue(mispPartnerPage.isCancelConfirmationPopupDisplayed(),
                GlobalConstants.isCancelAfterDataShowsConfirmation);
        Assert.assertEquals(mispPartnerPage.getCancelConfirmationPopupText(), GlobalConstants.CANCEL_CONFIRMATION_POPUP_TEXT,
                GlobalConstants.isCancelPopupTextCorrect);
    }

    @Test(priority = 29, description = "Verify on clicking proceed button in the popup")
    public void cancelPopupProceedReturnsToPartnerList() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        mispPartnerPage.clickOnCreatePartnerCancelButton();
        mispPartnerPage.clickOnCancelConfirmationPopupProceedButton();
        Assert.assertTrue(mispPartnerPage.isListOfPartnersDisplayed(),
                GlobalConstants.isCancelPopupProceedReturnsToPartnerList);
    }

    @Test(priority = 30, description = "Verify on clicking cancel button in the popup")
    public void cancelPopupCancelKeepsCreatePartnerPage() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        mispPartnerPage.clickOnCreatePartnerCancelButton();
        mispPartnerPage.clickOnCancelConfirmationPopupCancelButton();
        Assert.assertTrue(mispPartnerPage.isCreatePrtnerPageTitleDisplayed(),
                GlobalConstants.isCancelPopupCancelKeepsCreatePartnerPage);
        Assert.assertEquals(mispPartnerPage.getPartnerOrganisationFieldValue(), GlobalConstants.ORGANISATION_NAME,
                GlobalConstants.isCancelPopupCancelKeepsCreatePartnerPage);
    }

    @Test(priority = 31, description = "Verify on clicking home button in the screen")
    public void homeButtonReturnsToDashboard() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        mispPartnerPage.clickOnCreatePartnerHomeButton();
        Assert.assertTrue(dashboardPage.isPartnersDisplayed(),
                GlobalConstants.isCreatePartnerHomeReturnsToDashboard);
    }

    @Test(priority = 36, description = "Verify the organisation info field")
    public void organizationNameInfoMessage() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        mispPartnerPage.clickOnPartnerOragnizationInfoButton();
        Assert.assertTrue(mispPartnerPage.isOrganizationNameInfoDisplayed(), GlobalConstants.isOrganizationInfoMessageCorrect);
        Assert.assertEquals(mispPartnerPage.getOrganizationNameInfoText(), GlobalConstants.ORG_NAME_INFO_TEXT,
                GlobalConstants.isOrganizationInfoMessageCorrect);
    }

    @Test(priority = 40, description = "Verify adding organisation name exceeding 128 characters")
    public void organizationNameMaxLength() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterPartnerOrganisation("A".repeat(130));
        Assert.assertTrue(mispPartnerPage.getPartnerOrganisationFieldValue().length() <= GlobalConstants.ORG_NAME_MAX_LENGTH,
                GlobalConstants.isOrgNameMaxLengthEnforcedForOnlineVerificationPartner);
    }

    @Test(priority = 41, description = "Verify adding organisation name with special characters only")
    public void organizationNameDisallowedCharacters() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterPartnerOrganisation(GlobalConstants.DISALLOWED_SPECIAL_CHARS_ORG);
        Assert.assertTrue(mispPartnerPage.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isOrgInvalidCharactersRejectedForOnlineVerificationPartner);
        Assert.assertEquals(mispPartnerPage.getOrganizationNameValidationErrorText(), GlobalConstants.ORG_INVALID_CHARACTER_ERROR,
                GlobalConstants.isOrgInvalidCharactersRejectedForOnlineVerificationPartner);
    }

    @Test(priority = 42, description = "Verify adding address details with maximum characters")
    public void addressMaxLength() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterPartnerAddress("A".repeat(2001));
        Assert.assertTrue(mispPartnerPage.getPartnerAddressFieldValue().length() <= GlobalConstants.ADDRESS_MAX_LENGTH,
                GlobalConstants.isAddressMaxLengthEnforcedForOnlineVerificationPartner);
    }

    @Test(priority = 43, description = "Verify adding email address with maximum characters")
    public void emailMaxLength() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterEmailId("a".repeat(255));
        Assert.assertTrue(mispPartnerPage.getEmailFieldValue().length() <= GlobalConstants.EMAIL_MAX_LENGTH,
                GlobalConstants.isEmailMaxLengthEnforcedForOnlineVerificationPartner);
    }

    @Test(priority = 46, description = "Verify adding username with maximum characters")
    public void usernameMaxLength() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterUserName("a".repeat(37));
        Assert.assertTrue(mispPartnerPage.getUserNameFieldValue().length() <= GlobalConstants.USERNAME_MAX_LENGTH,
                GlobalConstants.isUsernameMaxLengthEnforcedForOnlineVerificationPartner);
    }

    @Test(priority = 39, description = "Verify adding existing email ID")
    public void existingEmailIsRejected() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        String sharedEmail = "ovpmail" + BaseClass.data + "@test.com";
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.selectFirstActivePolicyGroup();
        mispPartnerPage.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        mispPartnerPage.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        mispPartnerPage.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        mispPartnerPage.enterEmailId(sharedEmail);
        mispPartnerPage.enterUserName("ovpuser1" + BaseClass.data);
        mispPartnerPage.clickOnCreatePartnerSubmitButton();
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
                GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
        mispPartnerPage.clickOnSuccessMsgHomeButton();

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickOnPartners();
        mispPartnerPage.clickOnCreatePartnerButton();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.selectFirstActivePolicyGroup();
        mispPartnerPage.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        mispPartnerPage.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        mispPartnerPage.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        mispPartnerPage.enterEmailId(sharedEmail);
        mispPartnerPage.enterUserName("ovpuser2" + BaseClass.data);
        mispPartnerPage.clickOnCreatePartnerSubmitButton();
        Assert.assertTrue(mispPartnerPage.isEmailAddressIsAlreadyRegisteredErrorDisplayed(),
                GlobalConstants.isEmailAlreadyRegisteredErrorDisplayed);
        Assert.assertEquals(mispPartnerPage.getEmailAlreadyRegisteredErrorText(),
                GlobalConstants.EMAIL_ALREADY_REGISTERED_ERROR_MSG,
                GlobalConstants.isEmailAlreadyRegisteredErrorTextCorrect);
    }
}
