package io.mosip.testrig.pmpuiv2.testcase;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.MispServicesPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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
        mispServicesPage.clickOnExpiryDate();
        assertTrue(mispServicesPage.isCalendarDisplayed(), GlobalConstants.isCalendarDisplayed);

        createMispLicenseKey(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_POLICY_01,
                GlobalConstants.MISP_LICENSEKEY_01);
        assertTrue(mispServicesPage.isMispLicenseKeyPopupDisplayed(), GlobalConstants.isMispLicenseKeyPopupDisplayed);
        mispServicesPage.closeCopyIdPopup();

        mispServicesPage.clickOnGenerateMispLicenceKeyButton();
        createMispLicenseKey(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_POLICY_01,
                GlobalConstants.MISP_LICENSEKEY_01);
        assertTrue(mispServicesPage.isGenerateLicenseKeyErrorMessageDisplayed(),
                GlobalConstants.isGenerateLicenseKeyErrorMessageDisplayed);
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
        assertTrue(mispServicesPage.isGenerateLicenseKeyErrorMessageDisplayed(),
                GlobalConstants.isGenerateLicenseKeyErrorMessageDisplayed);
        mispServicesPage.clickOnClearFormButton();

    }

    @Test(priority = 2, description = "Verify Note Display on License Key Generation Screen")
    public void importantNoteDisplayedWithExpectedTextOnGenerateScreen() {
        openGenerateMispLicenseKeyScreen();

        assertTrue(mispServicesPage.isMispLicenseKeyImportantNoteDisplayed(),
                GlobalConstants.isMispLicenseKeyImportantNoteDisplayed);
        assertEquals(mispServicesPage.getMispLicenseKeyImportantNoteText(),
                GlobalConstants.MISP_LICENSE_KEY_IMPORTANT_NOTE, GlobalConstants.isImportantNoteTextCorrect);
    }

    @Test(priority = 3, description = "Verify Important Note is Read-Only")
    public void importantNoteIsReadOnlyOnGenerateScreen() {
        openGenerateMispLicenseKeyScreen();

        assertTrue(mispServicesPage.isMispLicenseKeyImportantNoteNotEditable(),
                GlobalConstants.isMispLicenseKeyImportantNoteNotEditable);
        assertFalse(mispServicesPage.isMispLicenseKeyImportantNoteFocusable(),
                GlobalConstants.isImportantNoteNotFocusable);
        assertTrue(
                mispServicesPage
                        .isMispLicenseKeyImportantNoteUnchangedAfterTyping(GlobalConstants.IMPORTANT_NOTE_EDIT_ATTEMPT),
                GlobalConstants.isImportantNoteReadOnly);
    }

    @Test(priority = 4, description = "Verify Note Does Not Interfere with Form Fields or Buttons")
    public void importantNoteDoesNotInterfereWithFormFieldsOnGenerateScreen() {
        openGenerateMispLicenseKeyScreen();

        assertTrue(mispServicesPage.isMispLicenseKeyImportantNoteWithinViewport(),
                GlobalConstants.isImportantNoteFullyVisible);
        assertFalse(mispServicesPage.isMispLicenseKeyImportantNoteCovered(), GlobalConstants.isImportantNoteNotCovered);

        assertTrue(mispServicesPage.isLicenseKeyNameFieldDisplayed(),
                GlobalConstants.isMispLicenseKeyNamePlaceholderDisplayed);
        assertTrue(mispServicesPage.isMispLicenseKeyGuidenceNoteDisplayed(),
                GlobalConstants.isMispLicenseKeyGuidenceNoteDisplayed);

        assertFalse(mispServicesPage.isImportantNoteOverlappingSubmitButton(),
                GlobalConstants.isImportantNoteNotOverlappingFormFields);
        assertFalse(mispServicesPage.isImportantNoteOverlappingLicenseKeyNameField(),
                GlobalConstants.isImportantNoteNotOverlappingFormFields);
        assertFalse(mispServicesPage.isImportantNoteOverlappingGuidenceNote(),
                GlobalConstants.isImportantNoteNotOverlappingFormFields);

        // The form must still work with the note on screen.
        assertTrue(mispServicesPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
        assertTrue(mispServicesPage.isCancelButtonDisplayed(), GlobalConstants.isCancelButtonDisplayed);
        assertTrue(mispServicesPage.isClearFormButtonDisplayed(), GlobalConstants.isClearFormButtonDisplayed);
    }

    private void openGenerateMispLicenseKeyScreen() {
        dashboardPage = new DashboardPage(driver);
        mispServicesPage = new MispServicesPage(driver);

        dashboardPage.clickOnMispServices();
        assertTrue(mispServicesPage.isGenerateMispLicenceKeyButtonDisplayed(),
                GlobalConstants.isGenerateMispLicenceKeyButtonDisplayed);
        mispServicesPage.clickOnGenerateMispLicenceKeyButton();
        assertTrue(mispServicesPage.isGenerateMispLicenceKeyPageDisplayed(),
                GlobalConstants.isGenerateMispLicenceKeyPageDisplayed);
    }

    private void createMispLicenseKey(String partnerIdValue, String policyName, String licenseKeyName) {
        mispServicesPage.selectPartnerId(partnerIdValue);
        mispServicesPage.selectPolicyName(policyName);
        mispServicesPage.enterLicenseKeyName(licenseKeyName);
        mispServicesPage.enterExpiryDate();
        assertTrue(mispServicesPage.isCreateLicenseKeySubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
        mispServicesPage.clickOnSubmitButton();
    }

}
