package io.mosip.testrig.pmpuiv2.testcase;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "OnlineVerificationPartnerTest" })
public class OnlineVerificationPartnerTest extends BaseClass {

    @Test(priority = 1, description = "Check the Title, breadcrumb, and subtitle of the Create Partner screen")
    public void createPartnerScreenDetails() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        Assert.assertTrue(mispPartnerPage.isCreatePrtnerPageTitleDisplayed(), GlobalConstants.isCreatePartnerPageTitleCorrect);
        Assert.assertEquals(mispPartnerPage.getCreatePartnerPageTitleText(), GlobalConstants.CREATE_PARTNER_PAGE_TITLE,
                GlobalConstants.isCreatePartnerPageTitleCorrect);
        Assert.assertEquals(mispPartnerPage.getBreadcrumbTextOfCreatePartnerPage(),
                GlobalConstants.BREADCUMB_TEXT_OF_CREATE_PARTNER, GlobalConstants.isBreadcrumbClickable);
        Assert.assertTrue(mispPartnerPage.isCreatePartnerMandatoryFieldInfoDisplayed(),
                GlobalConstants.isMandatoryFieldInfoTextCorrect);
        Assert.assertEquals(mispPartnerPage.getCreatePartnerMandatoryFieldInfoText(), GlobalConstants.MANDATORY_FIELD_INFO_TEXT,
                GlobalConstants.isMandatoryFieldInfoTextCorrect);
    }

    @Test(priority = 2, description = "Verify the partner type dropdown and select Online Verification Partner")
    public void partnerTypeDropdown() {
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
                GlobalConstants.ONLINE_VERIFICATION_PARTNER_OPTION_DISPLAYED);
        mispPartnerPage.clickOnPartnerTypeOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
        Assert.assertEquals(mispPartnerPage.getSelectedPartnerTypeText(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
                GlobalConstants.ONLINE_VERIFICATION_PARTNER_SELECTED_SUCCESSFULLY);
    }

    @Test(priority = 3, description = "Verify hamburger, Home, and List of Partners navigation")
    public void createPartnerNavigation() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
        dashboardPage.clickOnHamburgerOpen();
        dashboardPage.clickOnPartnerOfHamburger();
        mispPartnerPage.clickOnCreatePartnerButton();
        selectOnlineVerificationPartner(mispPartnerPage);
        Assert.assertTrue(mispPartnerPage.isCreatePrtnerPageTitleDisplayed(),
                GlobalConstants.CREATE_PARTNER_NAVIGABLE_FROM_HAMBURGER);
        Assert.assertEquals(mispPartnerPage.getCreatePartnerPageTitleText(), GlobalConstants.CREATE_PARTNER_PAGE_TITLE,
                GlobalConstants.CREATE_PARTNER_NAVIGABLE_FROM_HAMBURGER);
        Assert.assertEquals(mispPartnerPage.getSelectedPartnerTypeText(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
                GlobalConstants.ONLINE_VERIFICATION_PARTNER_SELECTED_SUCCESSFULLY);

        mispPartnerPage.clickOnListOfPartnerButton();
        Assert.assertTrue(mispPartnerPage.isListOfPartnersDisplayed(),
                GlobalConstants.LIST_OF_PARTNERS_BUTTON_RETURNS_TO_PARTNER_LIST);

        mispPartnerPage = navigateToCreatePartnerPage();
        mispPartnerPage.clickOnCreatePartnerHomeButton();
        Assert.assertTrue(dashboardPage.isPartnersDisplayed(),
                GlobalConstants.CREATE_PARTNER_HOME_RETURNS_TO_DASHBOARD);
    }

    @Test(priority = 4, description = "Verify Submit stays disabled when a mandatory field is empty")
    public void mandatoryFieldsKeepSubmitDisabled() {
        assertSubmitDisabledWhenSkipped("policyGroup", GlobalConstants.POLICY_GROUP_MANDATORY_FOR_ONLINE_VERIFICATION_PARTNER);
        assertSubmitDisabledWhenSkipped("address", GlobalConstants.ADDRESS_MANDATORY_FOR_ONLINE_VERIFICATION_PARTNER);
        assertSubmitDisabledWhenSkipped("organisation", GlobalConstants.ORGANISATION_NAME_MANDATORY_FOR_ONLINE_VERIFICATION_PARTNER);
        assertSubmitDisabledWhenSkipped("phone", GlobalConstants.PHONE_NUMBER_MANDATORY_FOR_ONLINE_VERIFICATION_PARTNER);
        assertSubmitDisabledWhenSkipped("email", GlobalConstants.EMAIL_MANDATORY_FOR_ONLINE_VERIFICATION_PARTNER);
        assertSubmitDisabledWhenSkipped("username", GlobalConstants.USERNAME_MANDATORY_FOR_ONLINE_VERIFICATION_PARTNER);
        assertSubmitDisabledWhenSkipped("notificationLanguage", GlobalConstants.NOTIFICATION_LANGUAGE_MANDATORY_FOR_ONLINE_VERIFICATION_PARTNER);
    }

    @Test(priority = 5, description = "Verify username characters and maximum length")
    public void usernameValidation() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterUserName("###");
        Assert.assertTrue(mispPartnerPage.isUsernameMustStartWithLetterErrorDisplayed(),
                GlobalConstants.USERNAME_MUST_START_WITH_LETTER_FOR_ONLINE_VERIFICATION_PARTNER);
        Assert.assertEquals(mispPartnerPage.getUserNameValidationErrorText(), GlobalConstants.USERNAME_MUST_START_WITH_LETTER,
                GlobalConstants.USERNAME_MUST_START_WITH_LETTER_FOR_ONLINE_VERIFICATION_PARTNER);

        mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterUserName("swe#$$$%%^^^");
        Assert.assertEquals(mispPartnerPage.getUserNameValidationErrorText(), GlobalConstants.USERNAME_ALLOWED_CHARACTERS_ERROR,
                GlobalConstants.USERNAME_INVALID_CHARACTER_FOR_ONLINE_VERIFICATION_PARTNER);

        mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterUserName("12344swe");
        Assert.assertTrue(mispPartnerPage.isUsernameMustStartWithLetterErrorDisplayed(),
                GlobalConstants.USERNAME_MUST_START_WITH_LETTER_FOR_ONLINE_VERIFICATION_PARTNER);
        Assert.assertEquals(mispPartnerPage.getUserNameValidationErrorText(), GlobalConstants.USERNAME_MUST_START_WITH_LETTER,
                GlobalConstants.USERNAME_MUST_START_WITH_LETTER_FOR_ONLINE_VERIFICATION_PARTNER);

        mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterUserName("a".repeat(37));
        Assert.assertTrue(mispPartnerPage.getUserNameFieldValue().length() <= GlobalConstants.USERNAME_MAX_LENGTH,
                GlobalConstants.USERNAME_MAX_LENGTH_ENFORCED_FOR_ONLINE_VERIFICATION_PARTNER);
    }

    @Test(priority = 6, description = "Verify organisation info, maximum length, and special characters")
    public void organisationValidation() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        mispPartnerPage.clickOnPartnerOragnizationInfoButton();
        Assert.assertTrue(mispPartnerPage.isOrganizationNameInfoDisplayed(), GlobalConstants.ORGANIZATION_INFO_MESSAGE_CORRECT);
        Assert.assertEquals(mispPartnerPage.getOrganizationNameInfoText(), GlobalConstants.ORG_NAME_INFO_TEXT,
                GlobalConstants.ORGANIZATION_INFO_MESSAGE_CORRECT);

        mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterPartnerOrganisation("A".repeat(130));
        Assert.assertTrue(mispPartnerPage.getPartnerOrganisationFieldValue().length() <= GlobalConstants.ORG_NAME_MAX_LENGTH,
                GlobalConstants.ORG_NAME_MAX_LENGTH_ENFORCED_FOR_ONLINE_VERIFICATION_PARTNER);

        mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterPartnerOrganisation(GlobalConstants.DISALLOWED_SPECIAL_CHARS_ORG);
        Assert.assertTrue(mispPartnerPage.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.ORG_INVALID_CHARACTERS_REJECTED_FOR_ONLINE_VERIFICATION_PARTNER);
        Assert.assertEquals(mispPartnerPage.getOrganizationNameValidationErrorText(), GlobalConstants.ORG_INVALID_CHARACTER_ERROR,
                GlobalConstants.ORG_INVALID_CHARACTERS_REJECTED_FOR_ONLINE_VERIFICATION_PARTNER);
    }

    @Test(priority = 7, description = "Verify address and email maximum length")
    public void addressAndEmailMaxLength() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterPartnerAddress("A".repeat(2001));
        Assert.assertTrue(mispPartnerPage.getPartnerAddressFieldValue().length() <= GlobalConstants.ADDRESS_MAX_LENGTH,
                GlobalConstants.ADDRESS_MAX_LENGTH_ENFORCED_FOR_ONLINE_VERIFICATION_PARTNER);

        mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterEmailId("a".repeat(255));
        Assert.assertTrue(mispPartnerPage.getEmailFieldValue().length() <= GlobalConstants.EMAIL_MAX_LENGTH,
                GlobalConstants.EMAIL_MAX_LENGTH_ENFORCED_FOR_ONLINE_VERIFICATION_PARTNER);
    }

    @Test(priority = 8, description = "Verify Clear Form and Cancel")
    public void clearFormAndCancel() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        Assert.assertTrue(mispPartnerPage.isCreatePartnerClearButtonEnabled(), GlobalConstants.CLEAR_FORM_BUTTON_CLICKABLE);
        mispPartnerPage.clickOnCreatePartnerClearButton();

        mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        mispPartnerPage.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        mispPartnerPage.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        mispPartnerPage.clickOnCreatePartnerClearButton();
        Assert.assertEquals(mispPartnerPage.getPartnerOrganisationFieldValue(), "", GlobalConstants.isClearFormClearsAllFields);
        Assert.assertEquals(mispPartnerPage.getPartnerAddressFieldValue(), "", GlobalConstants.isClearFormClearsAllFields);
        Assert.assertEquals(mispPartnerPage.getUserNameFieldValue(), "", GlobalConstants.isClearFormClearsAllFields);

        mispPartnerPage.clickOnCreatePartnerCancelButton();
        Assert.assertTrue(mispPartnerPage.isListOfPartnersDisplayed(),
                GlobalConstants.CANCEL_WITHOUT_DATA_RETURNS_TO_PARTNER_LIST);

        mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        mispPartnerPage.clickOnCreatePartnerCancelButton();
        Assert.assertTrue(mispPartnerPage.isCancelConfirmationPopupDisplayed(),
                GlobalConstants.CANCEL_AFTER_DATA_SHOWS_CONFIRMATION);
        Assert.assertEquals(mispPartnerPage.getCancelConfirmationPopupText(), GlobalConstants.CANCEL_CONFIRMATION_POPUP_TEXT,
                GlobalConstants.isCancelPopupTextCorrect);
        mispPartnerPage.clickOnCancelConfirmationPopupCancelButton();
        Assert.assertTrue(mispPartnerPage.isCreatePrtnerPageTitleDisplayed(),
                GlobalConstants.CANCEL_POPUP_CANCEL_KEEPS_CREATE_PARTNER_PAGE);
        Assert.assertEquals(mispPartnerPage.getPartnerOrganisationFieldValue(), GlobalConstants.ORGANISATION_NAME,
                GlobalConstants.CANCEL_POPUP_CANCEL_KEEPS_CREATE_PARTNER_PAGE);

        mispPartnerPage.clickOnCreatePartnerCancelButton();
        mispPartnerPage.clickOnCancelConfirmationPopupProceedButton();
        Assert.assertTrue(mispPartnerPage.isListOfPartnersDisplayed(),
                GlobalConstants.CANCEL_POPUP_PROCEED_RETURNS_TO_PARTNER_LIST);
    }

    @Test(priority = 9, description = "Verify adding existing email ID")
    public void existingEmailIsRejected() {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        String sharedEmail = GlobalConstants.OVP_PARTNER_EMAIL;
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.selectFirstActivePolicyGroup();
        mispPartnerPage.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
        mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        mispPartnerPage.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        mispPartnerPage.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
        mispPartnerPage.enterEmailId(sharedEmail);
        mispPartnerPage.enterUserName(GlobalConstants.OVP_PARTNER_USER);
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
        mispPartnerPage.enterUserName(GlobalConstants.OVP_PARTNER_USER_2);
        mispPartnerPage.clickOnCreatePartnerSubmitButton();
        Assert.assertTrue(mispPartnerPage.isEmailAddressIsAlreadyRegisteredErrorDisplayed(),
                GlobalConstants.isEmailAlreadyRegisteredErrorDisplayed);
        Assert.assertEquals(mispPartnerPage.getEmailAlreadyRegisteredErrorText(),
                GlobalConstants.EMAIL_ALREADY_REGISTERED_ERROR_MSG,
                GlobalConstants.isEmailAlreadyRegisteredErrorTextCorrect);
    }

    private MispPartnerPage navigateToCreatePartnerPage() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
        if (!driver.findElements(By.id("create_partner_btn")).isEmpty()) {
            mispPartnerPage.clickOnCreatePartnerButton();
            return mispPartnerPage;
        }
        if (!driver.findElements(By.id("sub_title_btn")).isEmpty()) {
            if (mispPartnerPage.isCreatePartnerClearButtonEnabled()) {
                mispPartnerPage.clickOnCreatePartnerClearButton();
            }
            mispPartnerPage.clickOnListOfPartnerButton();
            mispPartnerPage.clickOnCreatePartnerButton();
            return mispPartnerPage;
        }
        dashboardPage.clickOnPartners();
        mispPartnerPage.clickOnCreatePartnerButton();
        return mispPartnerPage;
    }

    private void selectOnlineVerificationPartner(MispPartnerPage mispPartnerPage) {
        mispPartnerPage.clickOnPartnerTypeDropdown();
        mispPartnerPage.clickOnPartnerTypeOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
    }

    private void assertSubmitDisabledWhenSkipped(String skippedField, String message) {
        MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, skippedField);
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(), message);
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
}
