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

    @Test(priority = 1, description = "Create Online Verification Partner")
    public void createOnlineVerificationPartner() {
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
        Assert.assertEquals(mispPartnerPage.getSelectedPartnerTypeText(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
                GlobalConstants.isOnlineVerificationPartnerSelectedSuccessfully);

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

        mispPartnerPage.clickOnPartnerOragnizationInfoButton();
        Assert.assertTrue(mispPartnerPage.isOrganizationNameInfoDisplayed(), GlobalConstants.isOrganizationInfoMessageCorrect);
        Assert.assertEquals(mispPartnerPage.getOrganizationNameInfoText(), GlobalConstants.ORG_NAME_INFO_TEXT,
                GlobalConstants.isOrganizationInfoMessageCorrect);

        mispPartnerPage.enterUserName("###");
        Assert.assertTrue(mispPartnerPage.isUsernameMustStartWithLetterErrorDisplayed(),
                GlobalConstants.isUsernameMustStartWithLetterForOnlineVerificationPartner);
        Assert.assertEquals(mispPartnerPage.getUserNameValidationErrorText(), GlobalConstants.USERNAME_MUST_START_WITH_LETTER,
                GlobalConstants.isUsernameMustStartWithLetterForOnlineVerificationPartner);

        mispPartnerPage.enterUserName("swe#$$$%%^^^");
        Assert.assertEquals(mispPartnerPage.getUserNameValidationErrorText(), GlobalConstants.USERNAME_ALLOWED_CHARACTERS_ERROR,
                GlobalConstants.isUsernameInvalidCharacterForOnlineVerificationPartner);

        mispPartnerPage.enterUserName("12344swe");
        Assert.assertTrue(mispPartnerPage.isUsernameMustStartWithLetterErrorDisplayed(),
                GlobalConstants.isUsernameMustStartWithLetterForOnlineVerificationPartner);
        Assert.assertEquals(mispPartnerPage.getUserNameValidationErrorText(), GlobalConstants.USERNAME_MUST_START_WITH_LETTER,
                GlobalConstants.isUsernameMustStartWithLetterForOnlineVerificationPartner);

        mispPartnerPage.enterUserName("a".repeat(37));
        Assert.assertTrue(mispPartnerPage.getUserNameFieldValue().length() <= GlobalConstants.USERNAME_MAX_LENGTH,
                GlobalConstants.isUsernameMaxLengthEnforcedForOnlineVerificationPartner);

        mispPartnerPage.enterPartnerOrganisation("A".repeat(130));
        Assert.assertTrue(mispPartnerPage.getPartnerOrganisationFieldValue().length() <= GlobalConstants.ORG_NAME_MAX_LENGTH,
                GlobalConstants.isOrgNameMaxLengthEnforcedForOnlineVerificationPartner);

        mispPartnerPage.enterPartnerOrganisation(GlobalConstants.DISALLOWED_SPECIAL_CHARS_ORG);
        Assert.assertTrue(mispPartnerPage.isPartnerOrgNameSpecialChNotAllowErrorDisplayed(),
                GlobalConstants.isOrgInvalidCharactersRejectedForOnlineVerificationPartner);
        Assert.assertEquals(mispPartnerPage.getOrganizationNameValidationErrorText(), GlobalConstants.ORG_INVALID_CHARACTER_ERROR,
                GlobalConstants.isOrgInvalidCharactersRejectedForOnlineVerificationPartner);

        mispPartnerPage.enterPartnerAddress("A".repeat(2001));
        Assert.assertTrue(mispPartnerPage.getPartnerAddressFieldValue().length() <= GlobalConstants.ADDRESS_MAX_LENGTH,
                GlobalConstants.isAddressMaxLengthEnforcedForOnlineVerificationPartner);

        mispPartnerPage.enterEmailId("a".repeat(255));
        Assert.assertTrue(mispPartnerPage.getEmailFieldValue().length() <= GlobalConstants.EMAIL_MAX_LENGTH,
                GlobalConstants.isEmailMaxLengthEnforcedForOnlineVerificationPartner);

        Assert.assertTrue(mispPartnerPage.isCreatePartnerClearButtonEnabled(), GlobalConstants.isClearFormButtonClickable);
        mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        mispPartnerPage.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
        mispPartnerPage.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
        mispPartnerPage.clickOnCreatePartnerClearButton();
        Assert.assertEquals(mispPartnerPage.getPartnerOrganisationFieldValue(), "", GlobalConstants.isClearFormClearsAllFields);
        Assert.assertEquals(mispPartnerPage.getPartnerAddressFieldValue(), "", GlobalConstants.isClearFormClearsAllFields);
        Assert.assertEquals(mispPartnerPage.getUserNameFieldValue(), "", GlobalConstants.isClearFormClearsAllFields);

        mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, "policyGroup");
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isPolicyGroupMandatoryForOnlineVerificationPartner);

        mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, "address");
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isAddressMandatoryForOnlineVerificationPartner);

        mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, "organisation");
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isOrganisationNameMandatoryForOnlineVerificationPartner);

        mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, "phone");
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isPhoneNumberMandatoryForOnlineVerificationPartner);

        mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, "email");
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isEmailMandatoryForOnlineVerificationPartner);

        mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, "username");
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isUsernameMandatoryForOnlineVerificationPartner);

        mispPartnerPage = navigateToCreatePartnerPage();
        fillMandatoryFieldsExcept(mispPartnerPage, "notificationLanguage");
        Assert.assertTrue(mispPartnerPage.isCreatePartnerSubmitButtonDisabled(),
                GlobalConstants.isNotificationLanguageMandatoryForOnlineVerificationPartner);

        mispPartnerPage.clickOnCreatePartnerClearButton();
        mispPartnerPage.clickOnCreatePartnerCancelButton();
        Assert.assertTrue(mispPartnerPage.isListOfPartnersDisplayed(),
                GlobalConstants.isCancelWithoutDataReturnsToPartnerList);

        mispPartnerPage = navigateToCreatePartnerPage();
        mispPartnerPage.clickOnListOfPartnerButton();
        Assert.assertTrue(mispPartnerPage.isListOfPartnersDisplayed(),
                GlobalConstants.isListOfPartnersButtonReturnsToPartnerList);

        mispPartnerPage = navigateToCreatePartnerPage();
        selectOnlineVerificationPartner(mispPartnerPage);
        mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
        mispPartnerPage.clickOnCreatePartnerCancelButton();
        Assert.assertTrue(mispPartnerPage.isCancelConfirmationPopupDisplayed(),
                GlobalConstants.isCancelAfterDataShowsConfirmation);
        Assert.assertEquals(mispPartnerPage.getCancelConfirmationPopupText(), GlobalConstants.CANCEL_CONFIRMATION_POPUP_TEXT,
                GlobalConstants.isCancelPopupTextCorrect);
        mispPartnerPage.clickOnCancelConfirmationPopupCancelButton();
        Assert.assertTrue(mispPartnerPage.isCreatePrtnerPageTitleDisplayed(),
                GlobalConstants.isCancelPopupCancelKeepsCreatePartnerPage);
        Assert.assertEquals(mispPartnerPage.getPartnerOrganisationFieldValue(), GlobalConstants.ORGANISATION_NAME,
                GlobalConstants.isCancelPopupCancelKeepsCreatePartnerPage);

        mispPartnerPage.clickOnCreatePartnerCancelButton();
        mispPartnerPage.clickOnCancelConfirmationPopupProceedButton();
        Assert.assertTrue(mispPartnerPage.isListOfPartnersDisplayed(),
                GlobalConstants.isCancelPopupProceedReturnsToPartnerList);

        DashboardPage dashboardPage = new DashboardPage(driver);
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

        mispPartnerPage.clickOnCreatePartnerHomeButton();
        Assert.assertTrue(dashboardPage.isPartnersDisplayed(),
                GlobalConstants.isCreatePartnerHomeReturnsToDashboard);

        mispPartnerPage = navigateToCreatePartnerPage();
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
