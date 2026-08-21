package io.mosip.testrig.pmpuiv2.testcase;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.MispServicesPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test(dependsOnGroups = { "MispPolicyTest" }, groups = { "MispServicesTest" })
public class MispServicesTest extends BaseClass {
    private DashboardPage dashboardPage;
    private MispPartnerPage mispPartnerPage;
    private PartnerCertificatePage partnerCertificatePage;
    private MispServicesPage mispServicesPage;

    @Test(priority = 01, description = "This is a test case create misp licence key")
    public void createMispLicenceKey() {
        mispPartnerPage = new MispPartnerPage(driver);
        dashboardPage = new DashboardPage(driver);
        partnerCertificatePage = new PartnerCertificatePage(driver);
        mispServicesPage = new MispServicesPage(driver);

        dashboardPage.clickOnMispServices();
        assertTrue(mispServicesPage.isGenerateMispLicenceKeyButtonDisplayed(),
                GlobalConstants.isGenerateMispLicenceKeyButtonDisplayed);
        mispServicesPage.clickOnGenerateMispLicenceKeyButton();
        assertTrue(mispServicesPage.isGenerateMispLicenceKeyPageDisplayed(),
                GlobalConstants.isGenerateMispLicenceKeyPageDisplayed);
        assertTrue(mispServicesPage.isAllFieldsAreMandatorySubtitleDisplayed(),
                GlobalConstants.isAllFieldsAreMandatorySubtitleDisplayed);
        assertTrue(mispServicesPage.isMispServicesBreadcombDisplayed(),
                GlobalConstants.isMispServicesBreadcombDisplayed);
        assertTrue(mispServicesPage.isGenerateMispLicenceKeyHomeButtonDisplayed(),
                GlobalConstants.isGenerateMispLicenceKeyHomeButtonDisplayed);

        mispServicesPage.clickOnMispServicesBreadcomb();
        assertTrue(mispServicesPage.isGenerateMispLicenceKeyButtonDisplayed(),
                GlobalConstants.isGenerateMispLicenceKeyButtonDisplayed);
        mispServicesPage.clickOnGenerateMispLicenceKeyButton();
        assertTrue(mispServicesPage.isPartnerIdLabelDisplayed(), GlobalConstants.isPartnerIdLabelDisplayed);
        assertTrue(mispServicesPage.isPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLabelDisplayed);
        assertTrue(mispServicesPage.isPolicyGroupLabelDisplayed(), GlobalConstants.isPolicyGroupLabelDisplayed);
        assertTrue(mispServicesPage.isPolicyNameLabelDisplayed(), GlobalConstants.isPolicyNameLabelDisplayed);
        assertTrue(mispServicesPage.isMispLicenceKeyLabelDisplayed(), GlobalConstants.isMispLicenceKeyLabelDisplayed);
        assertTrue(mispServicesPage.isCalenderLabelDisplayed(), GlobalConstants.isCalenderLabelDisplayed);
        assertTrue(mispServicesPage.isClearFormButtonDisplayed(), GlobalConstants.isClearFormButtonDisplayed);
        assertTrue(mispServicesPage.isCancelButtonDisplayed(), GlobalConstants.isCancelButtonDisplayed);
        assertTrue(mispServicesPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);

        mispServicesPage.clickOnPartnerIdInfoButton();
        assertTrue(mispServicesPage.isPartnerIdInfoDescriptionDisplayed(),
                GlobalConstants.isPartnerIdInfoDescriptionDisplayed);
        assertTrue(mispServicesPage.isPartnerIdInfoDescriptionNotEditable(),
                GlobalConstants.isPartnerIdInfoDescriptionNotEditable);
        mispServicesPage.clickOnPartnerIdInfoButton();

        mispServicesPage.clickOnPolicyGroupInfoButton();
        assertTrue(mispServicesPage.isPolicyGroupInfoDescriptionDisplayed(),
                GlobalConstants.isPolicyGroupInfoDescriptionDisplayed);
        mispServicesPage.clickOnPolicyGroupInfoButton();

        mispServicesPage.clickOnPolicyNameInfoButton();
        assertTrue(mispServicesPage.isPolicyNameInfoDescriptionDisplayed(),
                GlobalConstants.isPolicyNameInfoDescriptionDisplayed);
        mispServicesPage.clickOnPolicyNameInfoButton();

        assertTrue(mispServicesPage.isPartnerIdPlaceholderDisplayed(),
                GlobalConstants.isPartnerIdPlaceHolderDisplayed);
        assertTrue(mispServicesPage.isPartnerTypePlaceholderDisplayed(),
                GlobalConstants.isPartnerTypePlaceHolderDisplayed);
        assertTrue(mispServicesPage.isPolicyNamePlaceholderDisplayed(),
                GlobalConstants.isPolicyNamePlaceHolderDisplayed);
        assertTrue(mispServicesPage.isPolicyGroupPlaceholderDisplayed(),
                GlobalConstants.isPolicyGroupPlaceHolderDisplayed);
        assertTrue(mispServicesPage.isMispLicenseKeyNamePlaceholderDisplayed(),
                GlobalConstants.isMispLicenseKeyNamePlaceholderDisplayed);

        assertTrue(mispServicesPage.isMispLicenseKeyGuidenceNoteDisplayed(),
                GlobalConstants.isMispLicenseKeyGuidenceNoteDisplayed);
        assertTrue(mispServicesPage.isMispLicenseKeyGuidenceNoteNotEditable(),
                GlobalConstants.isMispLicenseKeyGuidenceNoteNotEditable);

        assertTrue(mispServicesPage.isMispLicenseKeyImportantNoteDisplayed(),
                GlobalConstants.isMispLicenseKeyImportantNoteDisplayed);
        assertTrue(mispServicesPage.isMispLicenseKeyImportantNoteNotEditable(),
                GlobalConstants.isMispLicenseKeyImportantNoteNotEditable);

        mispServicesPage.clickOnPartnerIdDropdownButton();
        assertTrue(mispServicesPage.isPartnerIdDisplayedInDropdown(), GlobalConstants.isPartnerIdDisplayedInDropdown);
        mispServicesPage.clickOnPartnerIdOption1();
        assertEquals(mispServicesPage.getPartnerType(), GlobalConstants.MISP_PARTNER);

        mispServicesPage.selectPartnerId(GlobalConstants.MISP_PARTNER_USER);
        assertEquals(mispServicesPage.getPartnerType(), GlobalConstants.MISP_PARTNER);
        assertEquals(mispServicesPage.getPolicyGroup(), GlobalConstants.DEFAULT_POLICYGROUP);

        assertTrue(mispServicesPage.isPolicyNameHelpTextDisplayed(), "Verify Policy Name help text is displayed");
        mispServicesPage.clickOnPolicyNameDropdown();
        assertTrue(mispServicesPage.isMispPolicyNameDisplayed(), GlobalConstants.isMispPolicyNameDisplayed);
        assertTrue(mispServicesPage.isMispPolicyNameDescriptionDisplayed(),
                GlobalConstants.isMispPolicyNameDescriptionDisplayed);

        mispServicesPage.clickOnExpiryDateCalenderInfoIcon();
        assertTrue(mispServicesPage.isExpiryDateCalenderInfoDescriptionDisplayed(),
                GlobalConstants.isExpiryDateCalenderInfoDescriptionDisplayed);
        assertEquals(mispServicesPage.getExpiryDateCalenderInfoDescriptionText(),
                getExpectedExpiryDateInfoText());
        assertTrue(mispServicesPage.areInfoIconsColorAndFontSizeConsistent(),
                GlobalConstants.areInfoIconsColorAndFontSizeConsistent);
        mispServicesPage.clickOnExpiryDate();
        assertTrue(mispServicesPage.isCalendarDisplayed(), GlobalConstants.isCalendarDisplayed);
        assertTrue(
                mispServicesPage.getCalendarCurrentMonthText().matches(
                        "^(January|February|March|April|May|June|July|August|September|October|November|December) \\d{4}$"),
                GlobalConstants.isCalendarDisplayedInEnglish);

        createMispLicenseKeyWithPastExpiryDate(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_POLICY_01,
                GlobalConstants.MISP_LICENSEKEY_01);
        assertTrue(mispServicesPage.isGenerateLicenseKeyErrorMessageDisplayed(),
                GlobalConstants.isGenerateLicenseKeyErrorMessageDisplayed);
        mispServicesPage.clickOnClearFormButton();

        createMispLicenseKeyWithTodayExpiryDate(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_POLICY_01,
                GlobalConstants.MISP_LICENSEKEY_01);
        assertTrue(mispServicesPage.isGenerateLicenseKeyErrorMessageDisplayed(),
                GlobalConstants.isGenerateLicenseKeyErrorMessageDisplayed);
        mispServicesPage.clickOnClearFormButton();

        String expiryDateValue = mispServicesPage.selectFutureDateAndGetValue();
        assertTrue(expiryDateValue.matches("\\d{2}/\\d{2}/\\d{4}"), GlobalConstants.isExpiryDateFormatValid);

        String updatedExpiryDateValue = mispServicesPage.reopenCalendarAndSelectAlternateDate();
        assertTrue(!updatedExpiryDateValue.equals(expiryDateValue), GlobalConstants.isExpiryDateSelectionChangeable);
        mispServicesPage.clickOnClearFormButton();

        createMispLicenseKey(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_POLICY_01,
                GlobalConstants.MISP_LICENSEKEY_01);
        assertTrue(mispServicesPage.isMispLicenseKeyPopupDisplayed(), GlobalConstants.isMispLicenseKeyPopupDisplayed);
        String fullMispLicenseKeyValue = mispServicesPage.getMispLicenseKeyIdText();
        assertTrue(mispServicesPage.getCopyIdPopupTitleText().contains(GlobalConstants.MISP_LICENSEKEY_01),
                GlobalConstants.isCopyIdPopupTitleShowsLicenseKeyName);
        assertTrue(mispServicesPage.getCopyIdPopupSubtitleText().contains(GlobalConstants.MISP_PARTNER_USER),
                GlobalConstants.isCopyIdPopupSubtitleShowsPartnerId);
        assertEquals(mispServicesPage.getCopyIdPopupAlertMessageText(), GlobalConstants.COPY_ID_POPUP_ALERT_MSG);
        mispServicesPage.clickOnCopyIdButton();
        assertTrue(mispServicesPage.isCopiedTextDisplayed(), GlobalConstants.isCopiedTextDisplayedAfterCopyClick);
        assertTrue(mispServicesPage.isCopyButtonRevertedWithinFewSeconds(),
                GlobalConstants.isCopyButtonRevertedAfterFewSeconds);

        mispServicesPage.clickOnCopyIdButton();
        assertTrue(mispServicesPage.isCopiedTextDisplayed(), GlobalConstants.isCopiedTextDisplayedAfterCopyClick);

        mispServicesPage.clickOnPopupCloseButton();
        assertTrue(mispServicesPage.isLicenseKeyConfirmationHeaderDisplayed(),
                GlobalConstants.isLicenseKeyConfirmationHeaderDisplayed);
        assertEquals(mispServicesPage.getLicenseKeyConfirmationHeaderText(),
                GlobalConstants.LICENSE_KEY_CONFIRMATION_HEADER_TEXT);
        assertTrue(mispServicesPage.isLicenseKeyConfirmationHeaderNotEditable(),
                GlobalConstants.isLicenseKeyConfirmationHeaderNotEditable);
        assertTrue(mispServicesPage.isConfirmationSuccessIconDisplayed(),
                GlobalConstants.isMispConfirmationSuccessIconDisplayed);
        mispServicesPage.clickOnConfirmationGoBackButton();
        assertTrue(mispServicesPage.isPartnerIdHeaderDisplayed(), GlobalConstants.isPartnerIdHeaderDisplayed);
        assertTrue(mispServicesPage.isOrgNameHeaderDisplayed(), GlobalConstants.isOrganisationHeaderDisplayed);
        assertTrue(mispServicesPage.isPolicyGroupHeaderDisplayed(), GlobalConstants.isPolicyGroupHeaderDisplayed);
        assertTrue(mispServicesPage.isPolicyNameHeaderDisplayed(), GlobalConstants.isPolicyNameHeaderDisplayed);
        assertTrue(mispServicesPage.isMispLicenseKeyNameHeaderDisplayed(),
                GlobalConstants.isMispLicenseKeyNameHeaderDisplayed);
        assertTrue(mispServicesPage.isCreationDateHeaderDisplayed(), GlobalConstants.isCreationDateHeaderDisplayed);
        assertTrue(mispServicesPage.isExpirationDateHeaderDisplayed(),
                GlobalConstants.isExpirationDateHeaderDisplayed);
        assertTrue(mispServicesPage.isStatusHeaderDisplayed(), GlobalConstants.isStatusHeaderDisplayed);
        assertTrue(mispServicesPage.isMispLicenseKeyHeaderDisplayed(), GlobalConstants.isMispLicenseKeyHeaderDisplayed);
        assertTrue(mispServicesPage.isActionHeaderDisplayed(), GlobalConstants.isActionHeaderDisplayed);
        assertEquals(mispServicesPage.getLatestLicenseRowPartnerId(), GlobalConstants.MISP_PARTNER_USER);
        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.ACTIVE_STATUS_LABEL);

        mispServicesPage.clickOnViewLicenseKeyButton(1);
        String maskedMispLicenseKeyValue = mispServicesPage.getMispLicenseKeyIdText();
        assertTrue(fullMispLicenseKeyValue != null && fullMispLicenseKeyValue.length() > 4,
                "Verify the generated MISP license key was captured before masking is checked");
        assertTrue(!maskedMispLicenseKeyValue.equals(fullMispLicenseKeyValue)
                && maskedMispLicenseKeyValue.endsWith(fullMispLicenseKeyValue.substring(fullMispLicenseKeyValue.length() - 4)),
                GlobalConstants.isMispLicenseKeyMaskedOnView);
        mispServicesPage.clickOnPopupCloseButton();

        mispServicesPage.clickOnGenerateMispLicenceKeyButton();
        createMispLicenseKeyWhileOffline(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_POLICY_01,
                GlobalConstants.MISP_LICENSEKEY_01);

        createMispLicenseKey(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_POLICY_01,
                GlobalConstants.MISP_LICENSEKEY_01);
        assertEquals(mispServicesPage.getGenerateLicenseKeyErrorText(),
                GlobalConstants.DUPLICATE_LICENSE_KEY_NAME_ERROR_MSG);
        mispServicesPage.clickOnClearFormButton();

        createMispLicenseKey(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_POLICY_01,
                GlobalConstants.MISP_LICENSEKEY_DEACTIVATE);
        assertTrue(mispServicesPage.isMispLicenseKeyPopupDisplayed(), GlobalConstants.isMispLicenseKeyPopupDisplayed);
        mispServicesPage.closeCopyIdPopup();

        mispServicesPage.clickOnGenerateMispLicenceKeyButton();
        createMispLicenseKey(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_POLICY_01,
                GlobalConstants.MISP_LICENSEKEY_DEACTIVATE_CASE_INSENSITIVE);
        assertTrue(mispServicesPage.isMispLicenseKeyPopupDisplayed(), GlobalConstants.isMispLicenseKeyPopupDisplayed);
        mispServicesPage.closeCopyIdPopup();

        mispServicesPage.clickOnGenerateMispLicenceKeyButton();
        createMispLicenseKey(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_POLICY_01,
                GlobalConstants.MISP_LICENSEKEY_DEACTIVATE);
        assertEquals(mispServicesPage.getGenerateLicenseKeyErrorText(),
                GlobalConstants.DUPLICATE_LICENSE_KEY_NAME_ERROR_MSG);
        mispServicesPage.clickOnClearFormButton();

        createMispLicenseKey(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_POLICY_01,
                GlobalConstants.MISP_LICENSEKEY_01_CASE_INSENSITIVE);
        mispServicesPage.closeCopyIdPopup();

        mispServicesPage.clickOnGenerateMispLicenceKeyButton();
        mispServicesPage.enterInvalidPartnerId(GlobalConstants.ALPHANUMERIC);
        assertTrue(mispServicesPage.isPartnerIdNoDataAvailableDisplayed(),
                GlobalConstants.isPartnerIdNoDataAvailableDisplayed);
        mispServicesPage.clickOnClearFormButton();

        mispServicesPage.selectPartnerId(GlobalConstants.MISP_PARTNER_USER);
        mispServicesPage.selectPolicyName(GlobalConstants.MISP_POLICY_01);
        mispServicesPage.enterLicenseKeyName(GlobalConstants.SPECIAL_CHARACTERS);
        assertTrue(mispServicesPage.isInvalidCharacterErrorMessageDisplayed(),
                GlobalConstants.isInvalidCharacterErrorMessageDisplayed);
        mispServicesPage.clickOnClearFormButton();

        mispServicesPage.enterLicenseKeyName("A".repeat(130));
        assertTrue(mispServicesPage.getLicenseKeyNameFieldValue().length() <= GlobalConstants.MISP_LICENSE_KEY_NAME_MAX_LENGTH,
                GlobalConstants.isMispLicenseKeyNameMaxLengthEnforced);
        mispServicesPage.clickOnClearFormButton();

        createMispLicenseKey(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_POLICY_01,
                GlobalConstants.MISP_LICENSEKEY_NUMERIC);
        assertTrue(mispServicesPage.isMispLicenseKeyPopupDisplayed(),
                GlobalConstants.isNumericLicenseKeyNameAccepted);
        mispServicesPage.closeCopyIdPopup();

        mispServicesPage.clickOnGenerateMispLicenceKeyButton();
        mispServicesPage.selectPartnerId(GlobalConstants.MISP_PARTNER_WITHOUT_POLICYGROUP);
        assertEquals(mispServicesPage.getPolicyGroup(), GlobalConstants.NO_POLICY_GROUP_SELECTED);
        mispServicesPage.enterLicenseKeyName(GlobalConstants.MISP_LICENSEKEY_01);
        mispServicesPage.enterExpiryDate();
        assertTrue(mispServicesPage.isCreateLicenseKeySubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
        mispServicesPage.clickOnSubmitButton();
        assertTrue(mispServicesPage.isMispLicenseKeyPopupDisplayed(), GlobalConstants.isMispLicenseKeyPopupDisplayed);
        mispServicesPage.closeCopyIdPopup();

    }

    private void createMispLicenseKey(String partnerIdValue, String policyName, String licenseKeyName) {
        mispServicesPage.selectPartnerId(partnerIdValue);
        mispServicesPage.selectPolicyName(policyName);
        mispServicesPage.enterLicenseKeyName(licenseKeyName);
        mispServicesPage.enterExpiryDate();
        assertTrue(mispServicesPage.isCreateLicenseKeySubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
        mispServicesPage.clickOnSubmitButton();
    }

    private String getExpectedExpiryDateInfoText() {
        String lang = ConfigManager.getloginlang();
        if ("ara".equalsIgnoreCase(lang)) {
            return GlobalConstants.EXPIRY_DATE_CALENDER_INFO_TEXT_ARA;
        } else if ("fra".equalsIgnoreCase(lang)) {
            return GlobalConstants.EXPIRY_DATE_CALENDER_INFO_TEXT_FRA;
        }
        return GlobalConstants.EXPIRY_DATE_CALENDER_INFO_TEXT;
    }

    private void createMispLicenseKeyWithPastExpiryDate(String partnerIdValue, String policyName,
            String licenseKeyName) {
        mispServicesPage.selectPartnerId(partnerIdValue);
        mispServicesPage.selectPolicyName(policyName);
        mispServicesPage.enterLicenseKeyName(licenseKeyName);
        mispServicesPage.enterPastExpiryDate();
        assertTrue(mispServicesPage.isCreateLicenseKeySubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
        mispServicesPage.clickOnSubmitButton();
    }

    private void createMispLicenseKeyWithTodayExpiryDate(String partnerIdValue, String policyName,
            String licenseKeyName) {
        mispServicesPage.selectPartnerId(partnerIdValue);
        mispServicesPage.selectPolicyName(policyName);
        mispServicesPage.enterLicenseKeyName(licenseKeyName);
        mispServicesPage.enterTodayExpiryDate();
        assertTrue(mispServicesPage.isCreateLicenseKeySubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
        mispServicesPage.clickOnSubmitButton();
    }

    private void createMispLicenseKeyWhileOffline(String partnerIdValue, String policyName, String licenseKeyName) {
        mispServicesPage.selectPartnerId(partnerIdValue);
        mispServicesPage.selectPolicyName(policyName);
        mispServicesPage.enterLicenseKeyName(licenseKeyName);
        mispServicesPage.enterExpiryDate();
        assertTrue(mispServicesPage.isCreateLicenseKeySubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
        mispServicesPage.setNetworkOffline(true);
        try {
            mispServicesPage.clickOnSubmitButton();
            assertTrue(mispServicesPage.isNetworkErrorPageDisplayed(), GlobalConstants.isNetworkErrorPageDisplayed);
        } finally {
            mispServicesPage.setNetworkOffline(false);
        }
        mispServicesPage.clickOnNetworkErrorRetryButton();
        dashboardPage.clickOnMispServices();
        mispServicesPage.clickOnGenerateMispLicenceKeyButton();
    }

}
