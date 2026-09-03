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
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test(dependsOnGroups = { "MispPolicyTest" }, groups = { "MispServicesTest" })
public class MispServicesTest extends BaseClass {
    private DashboardPage dashboardPage;
    private MispPartnerPage mispPartnerPage;
    private PartnerCertificatePage partnerCertificatePage;
    private MispServicesPage mispServicesPage;

    @Test(priority = 01, description = "Create MISP license key")
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

        assertTrue(mispServicesPage.isMispLicenseListSubTitleDisplayed(), GlobalConstants.isMispLicenseListSubTitleDisplayed);
        String mispListSubTitleText = mispServicesPage.getMispLicenseListSubTitleText();
        String expectedSubTitlePrefix = getExpectedMispListHeaderText(GlobalConstants.LIST_OF_MISP_LICENSE_KEYS_SUBTITLE_TEXT,
                GlobalConstants.LIST_OF_MISP_LICENSE_KEYS_SUBTITLE_TEXT_FRA, GlobalConstants.LIST_OF_MISP_LICENSE_KEYS_SUBTITLE_TEXT_ARA);
        assertTrue(mispListSubTitleText.matches("^" + java.util.regex.Pattern.quote(expectedSubTitlePrefix) + " \\(\\d+\\)$"),
                GlobalConstants.isMispLicenseListSubTitleCountFormatCorrect);
        int countInSubTitle = Integer.parseInt(mispListSubTitleText.replaceAll("[^0-9]", ""));
        int recordsPerPage = Integer.parseInt(mispServicesPage.getSelectedRecordsPerPageText());
        int expectedRowCountOnPage = Math.min(countInSubTitle, recordsPerPage);
        assertEquals(mispServicesPage.getMispLicenseListRowCount(), expectedRowCountOnPage,
                GlobalConstants.isMispLicenseListSubTitleCountMatchesRowCount);

        assertTrue(mispServicesPage.isGenerateMispLicenceKeyButtonPositionedTopRight(),
                GlobalConstants.isGenerateMispLicenceKeyButtonPositionedTopRight);
        assertTrue(mispServicesPage.isFilterButtonDisplayed(), GlobalConstants.isFilterButtonDisplayed);
        assertTrue(mispServicesPage.isFilterButtonPositionedTopRight(),
                GlobalConstants.isFilterButtonPositionedTopRight);

        int unfilteredRowCountBaseline = mispServicesPage.getMispLicenseListRowCount();
        assertTrue(unfilteredRowCountBaseline > 0, GlobalConstants.isMispLicenseListTablePopulatedWithRows);

        assertTrue(mispServicesPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonAccessible);
        mispServicesPage.clickOnFilterButton();
        assertTrue(mispServicesPage.isMispLicenseFilterPanelDisplayed(), GlobalConstants.isFilterButtonAccessible);
        assertTrue(!mispServicesPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonDisabledOnceExpanded);
        assertTrue(mispServicesPage.isMispLicenseFilterResetButtonDisplayed(),
                GlobalConstants.isMispLicenseFilterResetLinkDisplayedWhenExpanded);

        assertTrue(mispServicesPage.areMispFilterTextFieldsGenuineInputs(), GlobalConstants.areMispFilterTextFieldsGenuineInputs);
        assertTrue(mispServicesPage.isMispFilterStatusFieldADropdown(), GlobalConstants.isMispFilterStatusFieldADropdown);
        assertEquals(mispServicesPage.getMispFilterLicenseKeyNameSearchPlaceholder(), getExpectedMispListHeaderText(
                GlobalConstants.MISP_LICENSE_KEY_NAME_SEARCH_PLACEHOLDER, GlobalConstants.MISP_LICENSE_KEY_NAME_SEARCH_PLACEHOLDER_FRA,
                GlobalConstants.MISP_LICENSE_KEY_NAME_SEARCH_PLACEHOLDER_ARA), GlobalConstants.isMispFilterLicenseKeyNameSearchBarVisible);

        mispServicesPage.enterMispFilterPolicyGroup(GlobalConstants.DEFAULT_POLICYGROUP);
        mispServicesPage.selectMispFilterStatusActive();
        mispServicesPage.clickOnApplyFilterButton();

        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count > 0);
        int filteredRowCount = mispServicesPage.getMispLicenseListRowCount();
        assertTrue(filteredRowCount > 0, GlobalConstants.isMultiFilterResultsUpdatedDynamically);
        for (int row = 1; row <= filteredRowCount; row++) {
            assertEquals(mispServicesPage.getLicenseRowStatus(row), GlobalConstants.ACTIVE_STATUS_LABEL,
                    GlobalConstants.isMultiFilterResultsUpdatedDynamically);
            assertTrue(mispServicesPage.getLicenseRowPolicyGroup(row).contains(GlobalConstants.DEFAULT_POLICYGROUP),
                    GlobalConstants.isMultiFilterResultsUpdatedDynamically);
        }
        int filteredSubTitleCount = Integer
                .parseInt(mispServicesPage.getMispLicenseListSubTitleText().replaceAll("[^0-9]", ""));
        assertEquals(filteredSubTitleCount, filteredRowCount, GlobalConstants.isMultiFilterSubTitleCountMatchesFilteredRowCount);

        mispServicesPage.clickOnMispLicenseFilterResetButton();

        assertTrue(!mispServicesPage.isMispLicenseFilterResetButtonDisplayed(),
                GlobalConstants.isMispLicenseFilterResetLinkHiddenWhenCollapsed);
        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count == unfilteredRowCountBaseline);
        assertEquals(mispServicesPage.getMispLicenseListRowCount(), unfilteredRowCountBaseline,
                GlobalConstants.isMispLicenseFilterResetClearsAllFiltersAndShowsFullList);

        mispServicesPage.clickOnFilterButton();
        mispServicesPage.enterMispFilterPartnerId(GlobalConstants.ALPHANUMERIC);
        mispServicesPage.clickOnApplyFilterButton();
        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count == 0);
        assertEquals(mispServicesPage.getMispLicenseListRowCount(), 0,
                GlobalConstants.isMispLicenseListEmptyForNoMatchingFilter);
        assertTrue(mispServicesPage.isNoResultsFoundMessageDisplayed(),
                GlobalConstants.isMispLicenseListEmptyForNoMatchingFilter);
        assertEquals(mispServicesPage.getNoResultsFoundMessageText(), getExpectedMispListHeaderText(
                GlobalConstants.NO_RESULTS_FOUND_TEXT, GlobalConstants.NO_RESULTS_FOUND_TEXT_FRA,
                GlobalConstants.NO_RESULTS_FOUND_TEXT_ARA), GlobalConstants.isMispLicenseListEmptyForNoMatchingFilter);
        mispServicesPage.clickOnMispLicenseFilterResetButton();

        mispServicesPage.clickOnFilterButton();
        mispServicesPage.enterMispFilterPartnerId(GlobalConstants.SPECIAL_CHARACTERS);
        assertTrue(mispServicesPage.isMispFilterPartnerIdInputErrorDisplayed(),
                GlobalConstants.isInvalidFilterInputBlocksSubmission);
        assertTrue(!mispServicesPage.isApplyFilterButtonEnabled(), GlobalConstants.isInvalidFilterInputBlocksSubmission);
        mispServicesPage.clickOnMispLicenseFilterResetButton();

        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count == unfilteredRowCountBaseline);
        assertEquals(mispServicesPage.getMispLicenseListRowCount(), unfilteredRowCountBaseline,
                GlobalConstants.isMispLicenseFilterResetClearsAllFiltersAndShowsFullList);
        assertEquals(mispServicesPage.getMispLicenseListRowCount(), unfilteredRowCountBaseline,
                GlobalConstants.isMispLicenseFilterResetClearsAllFiltersAndShowsFullList);

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

        assertEquals(mispServicesPage.getPartnerIdHeaderText(), getExpectedMispListHeaderText(
                GlobalConstants.MISP_LIST_HEADER_PARTNER_ID, GlobalConstants.MISP_LIST_HEADER_PARTNER_ID_FRA,
                GlobalConstants.MISP_LIST_HEADER_PARTNER_ID_ARA), GlobalConstants.isMispLicenseListTableHeaderTextCorrect);
        assertEquals(mispServicesPage.getOrgNameHeaderText(), getExpectedMispListHeaderText(
                GlobalConstants.MISP_LIST_HEADER_ORG_NAME, GlobalConstants.MISP_LIST_HEADER_ORG_NAME_FRA,
                GlobalConstants.MISP_LIST_HEADER_ORG_NAME_ARA), GlobalConstants.isMispLicenseListTableHeaderTextCorrect);
        assertEquals(mispServicesPage.getPolicyGroupHeaderText(), getExpectedMispListHeaderText(
                GlobalConstants.MISP_LIST_HEADER_POLICY_GROUP, GlobalConstants.MISP_LIST_HEADER_POLICY_GROUP_FRA,
                GlobalConstants.MISP_LIST_HEADER_POLICY_GROUP_ARA), GlobalConstants.isMispLicenseListTableHeaderTextCorrect);
        assertEquals(mispServicesPage.getPolicyNameHeaderText(), getExpectedMispListHeaderText(
                GlobalConstants.MISP_LIST_HEADER_POLICY_NAME, GlobalConstants.MISP_LIST_HEADER_POLICY_NAME_FRA,
                GlobalConstants.MISP_LIST_HEADER_POLICY_NAME_ARA), GlobalConstants.isMispLicenseListTableHeaderTextCorrect);
        assertEquals(mispServicesPage.getMispLicenseKeyNameHeaderText(), getExpectedMispListHeaderText(
                GlobalConstants.MISP_LIST_HEADER_LICENSE_KEY_NAME, GlobalConstants.MISP_LIST_HEADER_LICENSE_KEY_NAME_FRA,
                GlobalConstants.MISP_LIST_HEADER_LICENSE_KEY_NAME_ARA), GlobalConstants.isMispLicenseListTableHeaderTextCorrect);
        assertEquals(mispServicesPage.getCreationDateHeaderText(), getExpectedMispListHeaderText(
                GlobalConstants.MISP_LIST_HEADER_CREATION_DATE, GlobalConstants.MISP_LIST_HEADER_CREATION_DATE_FRA,
                GlobalConstants.MISP_LIST_HEADER_CREATION_DATE_ARA), GlobalConstants.isMispLicenseListTableHeaderTextCorrect);
        assertEquals(mispServicesPage.getExpirationDateHeaderText(), getExpectedMispListHeaderText(
                GlobalConstants.MISP_LIST_HEADER_EXPIRATION_DATE, GlobalConstants.MISP_LIST_HEADER_EXPIRATION_DATE_FRA,
                GlobalConstants.MISP_LIST_HEADER_EXPIRATION_DATE_ARA), GlobalConstants.isMispLicenseListTableHeaderTextCorrect);
        assertEquals(mispServicesPage.getStatusHeaderText(), getExpectedMispListHeaderText(
                GlobalConstants.MISP_LIST_HEADER_STATUS, GlobalConstants.MISP_LIST_HEADER_STATUS_FRA,
                GlobalConstants.MISP_LIST_HEADER_STATUS_ARA), GlobalConstants.isMispLicenseListTableHeaderTextCorrect);
        assertEquals(mispServicesPage.getMispLicenseKeyHeaderText(), getExpectedMispListHeaderText(
                GlobalConstants.MISP_LIST_HEADER_LICENSE_KEY, GlobalConstants.MISP_LIST_HEADER_LICENSE_KEY_FRA,
                GlobalConstants.MISP_LIST_HEADER_LICENSE_KEY_ARA), GlobalConstants.isMispLicenseListTableHeaderTextCorrect);
        assertEquals(mispServicesPage.getActionHeaderText(), getExpectedMispListHeaderText(
                GlobalConstants.MISP_LIST_HEADER_ACTION, GlobalConstants.MISP_LIST_HEADER_ACTION_FRA,
                GlobalConstants.MISP_LIST_HEADER_ACTION_ARA), GlobalConstants.isMispLicenseListTableHeaderTextCorrect);

        assertEquals(mispServicesPage.getLatestLicenseRowPartnerId(), GlobalConstants.MISP_PARTNER_USER);
        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.ACTIVE_STATUS_LABEL);

        String browserTodayLocaleDateString = mispServicesPage.getBrowserTodayLocaleDateString();
        assertEquals(mispServicesPage.getLicenseRowCreationDate(1), browserTodayLocaleDateString,
                GlobalConstants.isCreatedDateDisplayedInBrowserSettingTime);
        String latestExpirationDateCellText = mispServicesPage.getLicenseRowExpirationDate(1);
        assertTrue(!latestExpirationDateCellText.isEmpty() && !latestExpirationDateCellText.equals("-"),
                GlobalConstants.isExpirationDateDisplayedInBrowserSettingTime);
        assertTrue(mispServicesPage.isDateCellFormatConsistentWithBrowserLocale(latestExpirationDateCellText,
                browserTodayLocaleDateString), GlobalConstants.isExpirationDateDisplayedInBrowserSettingTime);

        mispServicesPage.clickOnViewLicenseKeyButton(1);
        assertTrue(mispServicesPage.isMispLicenseKeyPopupDisplayed(), GlobalConstants.isMispLicenseKeyPopupDisplayed);
        assertTrue(mispServicesPage.getCopyIdPopupTitleText().contains(GlobalConstants.MISP_LICENSEKEY_01),
                GlobalConstants.isEyeIconPopupTitleShowsLicenseKeyName);
        assertTrue(mispServicesPage.getCopyIdPopupSubtitleText().contains(GlobalConstants.MISP_PARTNER_USER),
                GlobalConstants.isEyeIconPopupSubtitleShowsPartnerId);
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

    @Test(priority = 02, description = "Deactivate option and popup behavior", dependsOnMethods = "createMispLicenceKey")
    public void deactivateOptionAndConfirmationPopupBehavior() {
        mispPartnerPage = new MispPartnerPage(driver);
        dashboardPage = new DashboardPage(driver);
        partnerCertificatePage = new PartnerCertificatePage(driver);
        mispServicesPage = new MispServicesPage(driver);

        // Each @Test method starts on a fresh browser session (BaseClass re-logs in per method),
        // so the MISP license list has to be navigated to and its async data load waited for explicitly.
        dashboardPage.clickOnMispServices();
        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count > 0);

        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.ACTIVE_STATUS_LABEL);

        int rowCountBeforeCancel = mispServicesPage.getMispLicenseListRowCount();

        mispServicesPage.clickOnMispLicenseListActionButton();
        assertTrue(mispServicesPage.isMispLicenseListActionMenuDisplayed(),
                GlobalConstants.isMispLicenseListActionMenuDisplayed);
        assertTrue(mispServicesPage.isMispLicenseListDeactivateButtonDisplayed(),
                GlobalConstants.isMispLicenseListDeactivateButtonDisplayed);
        assertTrue(mispServicesPage.isMispLicenseListDeactivateButtonEnabled(),
                GlobalConstants.isMispLicenseListDeactivateButtonEnabled);

        mispServicesPage.clickOnMispLicenseListDeactivateButton();
        assertTrue(mispServicesPage.isDeactivatePopupHeaderDisplayed(),
                GlobalConstants.isDeactivatePopupHeaderDisplayedForMispLicense);
        assertTrue(mispServicesPage.isDeactivatePopupDescriptionDisplayed(),
                GlobalConstants.isDeactivatePopupDescriptionDisplayedForMispLicense);
        assertTrue(mispServicesPage.isDeactivateSubmitButtonDisplayed(),
                GlobalConstants.isDeactivateSubmitButtonDisplayedForMispLicense);
        assertTrue(mispServicesPage.isDeactivateSubmitButtonEnabled(),
                GlobalConstants.isDeactivateSubmitButtonEnabledForMispLicense);
        assertTrue(mispServicesPage.isDeactivateCancelButtonDisplayed(),
                GlobalConstants.isDeactivateCancelButtonAvailableForMispLicense);
        assertTrue(mispServicesPage.isDeactivateCancelButtonEnabled(),
                GlobalConstants.isDeactivateCancelButtonEnabledForMispLicense);

        // The deactivate confirmation popup always shows '-' in place of the license key name, regardless of which row triggered it.
        String expectedTitle = String.format(GlobalConstants.DEACTIVATE_MISP_LICENSE_POPUP_TITLE, "-");
        assertEquals(mispServicesPage.getDeactivatePopupTitleText(), expectedTitle,
                GlobalConstants.isDeactivatePopupTitleCorrectForMispLicense);
        assertEquals(mispServicesPage.getDeactivatePopupDescriptionText(),
                GlobalConstants.DEACTIVATE_MISP_LICENSE_POPUP_DESCRIPTION,
                GlobalConstants.isDeactivatePopupSubtitleCorrectForMispLicense);

        mispServicesPage.clickOnDeactivateCancelButton();

        assertTrue(!mispServicesPage.isDeactivatePopupHeaderDisplayedQuick(),
                GlobalConstants.isDeactivatePopupClosedAfterCancelForMispLicense);
        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.ACTIVE_STATUS_LABEL,
                GlobalConstants.isMispLicenseKeyRemainsActiveAfterCancel);
        assertEquals(mispServicesPage.getMispLicenseListRowCount(), rowCountBeforeCancel,
                GlobalConstants.isMispServicesTabularViewUnchangedAfterCancel);
    }

    @Test(priority = 03, description = "Regenerate screen navigation", dependsOnMethods = "deactivateOptionAndConfirmationPopupBehavior")
    public void regenerateMispLicenseKeyNavigation() {
        mispPartnerPage = new MispPartnerPage(driver);
        dashboardPage = new DashboardPage(driver);
        partnerCertificatePage = new PartnerCertificatePage(driver);
        mispServicesPage = new MispServicesPage(driver);

        // Each @Test method starts on a fresh browser session (BaseClass re-logs in per method),
        // so the MISP license list has to be navigated to and its async data load waited for explicitly.
        dashboardPage.clickOnMispServices();
        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count > 0);

        String partnerIdBeforeRegenerate = mispServicesPage.getLatestLicenseRowPartnerId();
        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.ACTIVE_STATUS_LABEL);

        mispServicesPage.clickOnMispLicenseListActionButton();
        assertTrue(mispServicesPage.isMispLicenseListActionMenuDisplayed(),
                GlobalConstants.isMispLicenseListActionMenuDisplayed);
        assertTrue(mispServicesPage.isMispLicenseListRegenerateButtonDisplayed(),
                GlobalConstants.isMispLicenseListRegenerateButtonDisplayed);
        assertTrue(mispServicesPage.isMispLicenseListRegenerateButtonEnabled(),
                GlobalConstants.isMispLicenseListRegenerateButtonEnabled);

        mispServicesPage.clickOnMispLicenseListRegenerateButton();

        assertEquals(mispServicesPage.getPageTitleText(), getExpectedRegenerateMispLicenseKeyPageTitle());
        assertTrue(mispServicesPage.isMispServicesBreadcombDisplayed(),
                GlobalConstants.isMispServicesBreadcombDisplayed);
        assertTrue(mispServicesPage.isGenerateMispLicenceKeyHomeButtonDisplayed(),
                GlobalConstants.isGenerateMispLicenceKeyHomeButtonDisplayed);
        assertEquals(mispServicesPage.getBreadcrumbText(), getExpectedRegenerateBreadcrumbText(),
                GlobalConstants.isRegenerateBreadcrumbTextCorrect);

        assertTrue(mispServicesPage.isRegenerateMandatoryFieldsSubtitleDisplayed(),
                GlobalConstants.isAllFieldsAreMandatorySubtitleDisplayed);
        assertEquals(mispServicesPage.getRegenerateMandatoryFieldsSubtitleText(), getExpectedRegenerateMandatoryFieldsSubtitleText(),
                GlobalConstants.isRegenerateMandatoryFieldsSubtitleCorrect);

        assertTrue(mispServicesPage.isRegeneratePartnerIdLabelDisplayed(),
                GlobalConstants.isRegeneratePartnerIdLabelDisplayed);
        assertTrue(mispServicesPage.isRegeneratePartnerTypeLabelDisplayed(),
                GlobalConstants.isRegeneratePartnerTypeLabelDisplayed);
        assertTrue(mispServicesPage.isRegeneratePolicyGroupLabelDisplayed(),
                GlobalConstants.isRegeneratePolicyGroupLabelDisplayed);
        assertTrue(mispServicesPage.isRegeneratePolicyNameLabelDisplayed(),
                GlobalConstants.isRegeneratePolicyNameLabelDisplayed);
        assertTrue(mispServicesPage.isRegenerateLicenseKeyNameLabelDisplayed(),
                GlobalConstants.isRegenerateLicenseKeyNameLabelDisplayed);

        assertEquals(mispServicesPage.getRegeneratePartnerId(), partnerIdBeforeRegenerate);
        assertEquals(mispServicesPage.getRegeneratePartnerType(), GlobalConstants.MISP_PARTNER);
        assertEquals(mispServicesPage.getRegeneratePolicyGroup(), GlobalConstants.NO_POLICY_GROUP_SELECTED);
        assertTrue(mispServicesPage.isRegeneratePartnerIdFieldDisabled(),
                GlobalConstants.isRegeneratePartnerIdCarriedOver);
        assertTrue(mispServicesPage.isRegeneratePartnerTypeFieldDisabled(),
                GlobalConstants.isRegeneratePartnerTypeCarriedOver);
        assertTrue(mispServicesPage.isRegeneratePolicyGroupFieldDisabled(),
                GlobalConstants.isRegeneratePolicyGroupCarriedOver);
        assertTrue(mispServicesPage.isRegeneratePolicyGroupPlaceholderWithinViewport(),
                GlobalConstants.isRegeneratePolicyGroupPlaceholderAligned);
        assertEquals(mispServicesPage.getRegeneratePolicyName(), getExpectedNoPolicyNameSelectedText());
        assertTrue(mispServicesPage.isRegeneratePolicyNameFieldDisabled(),
                GlobalConstants.isRegeneratePolicyNameCarriedOver);
        assertTrue(mispServicesPage.isRegeneratePolicyNamePlaceholderWithinViewport(),
                GlobalConstants.isRegeneratePolicyNamePlaceholderAligned);

        assertTrue(mispServicesPage.isRegenerateLicenseKeyNameFieldDisplayed(),
                GlobalConstants.isRegenerateLicenseKeyNameFieldEditable);
        assertEquals(mispServicesPage.getRegenerateLicenseKeyNameFieldValue(), "");
        assertTrue(mispServicesPage.isRegenerateLicenseKeyNameFieldEnabled(),
                GlobalConstants.isRegenerateLicenseKeyNameFieldIsTextbox);

        assertEquals(mispServicesPage.getRegenerateLicenseKeyNameHelpText(), getExpectedRegenerateLicenseKeyNameHelpText(),
                GlobalConstants.isRegenerateLicenseKeyNameHelpTextCorrect);
        assertTrue(mispServicesPage.isRegenerateLicenseKeyNameHelpTextDisabledForEdit(),
                GlobalConstants.isRegenerateLicenseKeyNameHelpTextDisabledForEdit);

        mispServicesPage.enterRegenerateLicenseKeyName(GlobalConstants.MISP_LICENSEKEY_REGENERATE_TEMP);
        assertEquals(mispServicesPage.getRegenerateLicenseKeyNameFieldValue(), GlobalConstants.MISP_LICENSEKEY_REGENERATE_TEMP,
                GlobalConstants.isRegenerateLicenseKeyNameFieldIsTextbox);
        mispServicesPage.clickOnRegenerateClearFormButton();
        assertEquals(mispServicesPage.getRegenerateLicenseKeyNameFieldValue(), "",
                GlobalConstants.isRegenerateClearFormButtonClearsLicenseKeyNameField);
        assertTrue(mispServicesPage.isRegenerateExpiryDateFieldDisplayed(),
                GlobalConstants.isRegenerateExpiryDateFieldDisplayed);

        mispServicesPage.clickOnRegenerateExpiryDateField();
        assertTrue(mispServicesPage.isCalendarDisplayed(), GlobalConstants.isRegenerateCalendarDisplayed);
        assertTrue(mispServicesPage.getCalendarMonthHeaderText().matches(
                        "(January|February|March|April|May|June|July|August|September|October|November|December) \\d{4}"),
                GlobalConstants.isRegenerateCalendarDisplayedInEnglishOnly);
        String regenerateExpiryDateValue = mispServicesPage.selectFutureDateInOpenCalendarAndGetRegenerateValue();
        assertTrue(regenerateExpiryDateValue.matches("\\d{2}/\\d{2}/\\d{4}"), GlobalConstants.isRegenerateExpiryDateFormatValid);

        String updatedRegenerateExpiryDateValue = mispServicesPage.reopenRegenerateCalendarAndSelectAlternateDate();
        assertTrue(!updatedRegenerateExpiryDateValue.equals(regenerateExpiryDateValue),
                GlobalConstants.isRegenerateExpiryDateSelectionChangeable);

        mispServicesPage.enterFreeTextIntoRegenerateExpiryDateField(GlobalConstants.ALPHANUMERIC);
        assertEquals(mispServicesPage.getRegenerateExpiryDateFieldValue(), GlobalConstants.ALPHANUMERIC,
                GlobalConstants.isRegenerateExpiryDateFieldFreelyEditable);

        mispServicesPage.clickOnRegenerateExpiryDateCalenderInfoIcon();
        assertTrue(mispServicesPage.isRegenerateExpiryDateCalenderInfoDescriptionDisplayed(),
                GlobalConstants.isRegenerateExpiryDateCalenderInfoDescriptionDisplayed);
        assertEquals(mispServicesPage.getRegenerateExpiryDateCalenderInfoDescriptionText(), getExpectedExpiryDateInfoText(),
                GlobalConstants.isRegenerateExpiryDateCalenderInfoTextCorrect);
        assertTrue(mispServicesPage.isRegenerateExpiryDateCalenderInfoDescriptionNotEditable(),
                GlobalConstants.isRegenerateExpiryDateCalenderInfoDescriptionNotEditable);
        assertEquals(mispServicesPage.getRegenerateExpiryDateCalenderInfoIconCursor(), "pointer",
                GlobalConstants.isRegenerateInfoIconHoverCursorPointer);
        mispServicesPage.clickOnRegenerateClearFormButton();

        assertTrue(mispServicesPage.isRegenerateClearFormButtonDisplayed(),
                GlobalConstants.isRegenerateClearFormButtonDisplayed);
        assertTrue(mispServicesPage.isRegenerateCancelButtonDisplayed(),
                GlobalConstants.isRegenerateCancelButtonDisplayed);
        assertTrue(mispServicesPage.isRegenerateSubmitButtonDisplayed(),
                GlobalConstants.isRegenerateSubmitButtonDisplayed);
        assertTrue(!mispServicesPage.isRegenerateSubmitButtonEnabled(),
                GlobalConstants.isRegenerateSubmitButtonDisabledByDefault);

        mispServicesPage.enterRegenerateLicenseKeyName(GlobalConstants.MISP_LICENSEKEY_REGENERATE_TEMP);
        mispServicesPage.clickOnRegenerateCancelButton();
        assertTrue(mispServicesPage.isCancelConfirmationPopupDisplayed(), GlobalConstants.isCancelConfirmationPopupDisplayed);
        mispServicesPage.clickOnCancelConfirmationPopupProceedButton();
        assertEquals(mispServicesPage.getPageTitleText(), getExpectedMispServicesPageTitle(),
                GlobalConstants.isRegenerateCancelReturnsToMispServicesList);

        mispServicesPage.clickOnMispLicenseListActionButton();
        mispServicesPage.clickOnMispLicenseListRegenerateButton();
        assertEquals(mispServicesPage.getRegenerateLicenseKeyNameFieldValue(), "",
                GlobalConstants.isRegenerateCancelDoesNotPersistUnsavedData);

        mispServicesPage.clickOnRegenerateCancelButton();
        assertEquals(mispServicesPage.getPageTitleText(), getExpectedMispServicesPageTitle(),
                GlobalConstants.isRegenerateCancelReturnsToMispServicesList);
    }

    @Test(priority = 04, description = "Regenerate validations and submission", dependsOnMethods = "regenerateMispLicenseKeyNavigation")
    public void regenerateMispLicenseKeyValidationsAndSubmission() {
        mispPartnerPage = new MispPartnerPage(driver);
        dashboardPage = new DashboardPage(driver);
        partnerCertificatePage = new PartnerCertificatePage(driver);
        mispServicesPage = new MispServicesPage(driver);

        // Each @Test method starts on a fresh browser session (BaseClass re-logs in per method),
        // so the MISP license list has to be navigated to and its async data load waited for explicitly.
        dashboardPage.clickOnMispServices();
        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count > 0);

        String partnerIdBeforeRegenerate = mispServicesPage.getLatestLicenseRowPartnerId();

        mispServicesPage.clickOnMispLicenseListActionButton();
        mispServicesPage.clickOnMispLicenseListRegenerateButton();
        assertEquals(mispServicesPage.getPageTitleText(), getExpectedRegenerateMispLicenseKeyPageTitle());

        assertTrue(mispServicesPage.isRegenerateImportantNoteDisplayed(),
                GlobalConstants.isRegenerateImportantNoteDisplayed);

        mispServicesPage.enterRegenerateLicenseKeyName(GlobalConstants.SPECIAL_CHARACTERS);
        assertTrue(mispServicesPage.isRegenerateInvalidCharacterErrorMessageDisplayed(),
                GlobalConstants.isRegenerateInvalidCharacterErrorMessageDisplayed);
        mispServicesPage.clickOnRegenerateClearFormButton();

        mispServicesPage.enterRegenerateLicenseKeyName("A".repeat(130));
        assertTrue(mispServicesPage.getRegenerateLicenseKeyNameFieldValue()
                        .length() <= GlobalConstants.MISP_LICENSE_KEY_NAME_MAX_LENGTH,
                GlobalConstants.isMispLicenseKeyNameMaxLengthEnforced);
        mispServicesPage.clickOnRegenerateClearFormButton();

        mispServicesPage.enterRegenerateLicenseKeyName(GlobalConstants.MISP_LICENSEKEY_REGENERATE_TEMP);
        assertTrue(mispServicesPage.isRegenerateSubmitButtonEnabled(),
                GlobalConstants.isRegenerateSubmitButtonEnabledWithOnlyLicenseKeyName);
        mispServicesPage.clickOnRegenerateClearFormButton();

        regenerateMispLicenseKeyWithPastExpiryDate(GlobalConstants.MISP_LICENSEKEY_REGENERATE_TEMP);
        assertTrue(mispServicesPage.isRegenerateErrorMessageDisplayed(),
                GlobalConstants.isRegenerateErrorMessageDisplayedForPastExpiryDate);
        mispServicesPage.clickOnRegenerateClearFormButton();

        regenerateMispLicenseKeyWithTodayExpiryDate(GlobalConstants.MISP_LICENSEKEY_REGENERATE_TEMP);
        assertTrue(mispServicesPage.isRegenerateErrorMessageDisplayed(),
                GlobalConstants.isRegenerateErrorMessageDisplayedForTodayExpiryDate);
        mispServicesPage.clickOnRegenerateClearFormButton();

        regenerateMispLicenseKey(GlobalConstants.MISP_LICENSEKEY_01);
        assertEquals(mispServicesPage.getRegenerateErrorMessageText(),
                GlobalConstants.DUPLICATE_LICENSE_KEY_NAME_ERROR_MSG,
                GlobalConstants.isRegenerateDuplicateLicenseKeyNameErrorDisplayed);
        mispServicesPage.clickOnRegenerateClearFormButton();

        regenerateMispLicenseKey(GlobalConstants.MISP_LICENSEKEY_01_REGENERATED);
        assertTrue(mispServicesPage.isMispLicenseKeyPopupDisplayed(),
                GlobalConstants.isRegenerateMispLicenseKeyPopupDisplayed);
        assertTrue(mispServicesPage.getCopyIdPopupTitleText().contains(GlobalConstants.MISP_LICENSEKEY_01_REGENERATED),
                GlobalConstants.isRegenerateCopyIdPopupTitleShowsNewLicenseKeyName);
        assertTrue(mispServicesPage.getCopyIdPopupSubtitleText().contains(partnerIdBeforeRegenerate),
                GlobalConstants.isRegenerateCopyIdPopupSubtitleShowsSamePartnerId);
        assertTrue(mispServicesPage.isMispLicenseKeyPopupHeaderDisplayed(),
                GlobalConstants.isRegenerateCopyIdPopupHeaderDisplayed);
        assertEquals(mispServicesPage.getMispLicenseKeyPopupHeaderText(), getExpectedMispLicenseKeyPopupHeaderText(),
                GlobalConstants.isRegenerateCopyIdPopupHeaderTextCorrect);
        String regeneratedLicenseKeyIdText = mispServicesPage.getMispLicenseKeyIdText();
        assertTrue(regeneratedLicenseKeyIdText != null && !regeneratedLicenseKeyIdText.isEmpty(),
                GlobalConstants.isRegenerateCopyIdPopupLicenseKeyValueDisplayed);
        assertTrue(mispServicesPage.isMispLicenseKeyIdBold(), GlobalConstants.isRegenerateCopyIdPopupLicenseKeyValueBold);
        assertEquals(mispServicesPage.getCopyIdPopupAlertMessageText(), GlobalConstants.COPY_ID_POPUP_ALERT_MSG,
                GlobalConstants.isRegenerateCopyIdPopupNoteDisplayed);
        mispServicesPage.clickOnCopyIdButton();
        assertTrue(mispServicesPage.isCopiedTextDisplayed(), GlobalConstants.isCopiedTextDisplayedAfterCopyClick);
        assertTrue(mispServicesPage.isCopyButtonRevertedWithinFewSeconds(),
                GlobalConstants.isCopyButtonRevertedAfterFewSeconds);

        mispServicesPage.clickOnCopyIdButton();
        assertTrue(mispServicesPage.isCopiedTextDisplayed(), GlobalConstants.isRegenerateCopyOperationRepeatable);

        mispServicesPage.clickOnPopupCloseButton();

        assertTrue(mispServicesPage.isRegenerateConfirmationHeaderDisplayed(),
                GlobalConstants.isRegenerateConfirmationHeaderDisplayed);
        assertEquals(mispServicesPage.getRegenerateConfirmationHeaderText(), getExpectedRegenerateConfirmationHeaderText());
        assertTrue(mispServicesPage.isRegenerateConfirmationHeaderNotEditable(),
                GlobalConstants.isRegenerateConfirmationHeaderNotEditable);
        assertTrue(mispServicesPage.isConfirmationSuccessIconDisplayed(),
                GlobalConstants.isMispConfirmationSuccessIconDisplayed);

        mispServicesPage.clickOnConfirmationGoBackButton();
        assertEquals(mispServicesPage.getPageTitleText(), getExpectedMispServicesPageTitle());
        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count > 0);
        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.ACTIVE_STATUS_LABEL);

        assertEquals(mispServicesPage.getLicenseRowLicenseKeyName(2), GlobalConstants.MISP_LICENSEKEY_01,
                "Verify the row below the newly regenerated license is the original license record it was regenerated from");
        assertEquals(mispServicesPage.getLicenseRowStatus(2), GlobalConstants.MISP_LICENSE_INACTIVE_STATUS_LABEL,
                GlobalConstants.isPreviousLicenseAutoDeactivatedOnRegenerate);

        mispServicesPage.clickOnViewLicenseKeyButton(1);
        String maskedRegeneratedLicenseKeyIdText = mispServicesPage.getMispLicenseKeyIdText();
        assertTrue(!maskedRegeneratedLicenseKeyIdText.equals(regeneratedLicenseKeyIdText)
                        && maskedRegeneratedLicenseKeyIdText.endsWith(
                        regeneratedLicenseKeyIdText.substring(regeneratedLicenseKeyIdText.length() - 4)),
                GlobalConstants.isMispLicenseKeyMaskedOnView);
        mispServicesPage.clickOnPopupCloseButton();

        mispServicesPage.clickOnMispLicenseListActionButton();
        mispServicesPage.clickOnMispLicenseListViewButton();
        assertTrue(mispServicesPage.isViewMispLicenseKeyExpiryDateNotEditable(),
                GlobalConstants.isRegenerateExpiryDateNotEditableAfterSubmission);
        mispServicesPage.clickOnViewMispLicenseKeyBackButton();
    }

    @Test(priority = 05, description = "Regenerate name and navigation checks", dependsOnMethods = "regenerateMispLicenseKeyValidationsAndSubmission")
    public void regenerateNameCaseSensitivityNumericHomeAndFutureExpiryStatus() {
        mispPartnerPage = new MispPartnerPage(driver);
        dashboardPage = new DashboardPage(driver);
        partnerCertificatePage = new PartnerCertificatePage(driver);
        mispServicesPage = new MispServicesPage(driver);

        // Each @Test method starts on a fresh browser session (BaseClass re-logs in per method),
        // so the MISP license list has to be navigated to and its async data load waited for explicitly.
        dashboardPage.clickOnMispServices();
        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count > 0);

        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.ACTIVE_STATUS_LABEL);

        mispServicesPage.clickOnMispLicenseListActionButton();
        mispServicesPage.clickOnMispLicenseListRegenerateButton();

        regenerateMispLicenseKey(GlobalConstants.MISP_LICENSEKEY_CASE_SENSITIVITY_BASE);
        assertTrue(mispServicesPage.isMispLicenseKeyPopupDisplayed(), GlobalConstants.isRegenerateMispLicenseKeyPopupDisplayed);
        mispServicesPage.clickOnPopupCloseButton();
        mispServicesPage.clickOnConfirmationGoBackButton();
        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count > 0);
        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.ACTIVE_STATUS_LABEL);

        mispServicesPage.clickOnMispLicenseListActionButton();
        mispServicesPage.clickOnMispLicenseListRegenerateButton();

        regenerateMispLicenseKey(GlobalConstants.MISP_LICENSEKEY_CASE_SENSITIVITY_BASE);
        assertEquals(mispServicesPage.getRegenerateErrorMessageText(), GlobalConstants.DUPLICATE_LICENSE_KEY_NAME_ERROR_MSG,
                GlobalConstants.isRegenerateDuplicateLicenseKeyNameErrorDisplayedForExactCaseMatch);
        mispServicesPage.clickOnRegenerateClearFormButton();

        regenerateMispLicenseKey(GlobalConstants.MISP_LICENSEKEY_CASE_SENSITIVITY_VARIANT);
        assertTrue(mispServicesPage.isMispLicenseKeyPopupDisplayed(), GlobalConstants.isRegenerateLicenseKeyNameUniqueAcrossCaseVariant);
        mispServicesPage.clickOnPopupCloseButton();
        mispServicesPage.clickOnConfirmationGoBackButton();
        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count > 0);
        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.ACTIVE_STATUS_LABEL);

        mispServicesPage.clickOnMispLicenseListActionButton();
        mispServicesPage.clickOnMispLicenseListRegenerateButton();

        regenerateMispLicenseKey(GlobalConstants.MISP_LICENSEKEY_NUMERIC);
        assertTrue(mispServicesPage.isMispLicenseKeyPopupDisplayed(), GlobalConstants.isNumericLicenseKeyNameAccepted);
        mispServicesPage.clickOnPopupCloseButton();
        mispServicesPage.clickOnConfirmationGoBackButton();
        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count > 0);
        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.ACTIVE_STATUS_LABEL);

        mispServicesPage.clickOnMispLicenseListActionButton();
        mispServicesPage.clickOnMispLicenseListRegenerateButton();

        regenerateMispLicenseKey(GlobalConstants.MISP_LICENSEKEY_HOME_BUTTON_TEST);
        assertTrue(mispServicesPage.isMispLicenseKeyPopupDisplayed(), GlobalConstants.isRegenerateMispLicenseKeyPopupDisplayed);
        mispServicesPage.clickOnPopupCloseButton();

        assertTrue(mispServicesPage.isRegenerateConfirmationHeaderDisplayed(),
                GlobalConstants.isRegenerateConfirmationHeaderDisplayed);
        mispServicesPage.clickOnConfirmationHomeButton();
        assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isRegenerateHomeButtonNavigatesToDashboard);

        dashboardPage.clickOnMispServices();
        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count > 0);
        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.ACTIVE_STATUS_LABEL);

        mispServicesPage.clickOnMispLicenseListActionButton();
        mispServicesPage.clickOnMispLicenseListViewButton();

        assertEquals(mispServicesPage.getViewMispLicenseKeyDetailsStatusText(), GlobalConstants.ACTIVE_STATUS_LABEL,
                GlobalConstants.isMispLicenseKeyStatusActiveInViewDetailsForFutureExpiry);

        mispServicesPage.clickOnViewMispLicenseKeyBackButton();
    }

    @Test(priority = 6, description = "Regenerate disabled for inactive license", dependsOnMethods = "regenerateNameCaseSensitivityNumericHomeAndFutureExpiryStatus")
    public void regenerateOptionDisabledForInactiveLicense() {
        mispPartnerPage = new MispPartnerPage(driver);
        dashboardPage = new DashboardPage(driver);
        partnerCertificatePage = new PartnerCertificatePage(driver);
        mispServicesPage = new MispServicesPage(driver);

        // Each @Test method starts on a fresh browser session (BaseClass re-logs in per method),
        // so the MISP license list has to be navigated to and its async data load waited for explicitly.
        dashboardPage.clickOnMispServices();
        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count > 0);

        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.ACTIVE_STATUS_LABEL);
        assertTrue(mispServicesPage.isLicenseRowNormalColor(1), GlobalConstants.isDeactivatedLicenseRowGreyedOut);
        assertTrue(mispServicesPage.isLicenseRowStatusPillGreen(1), GlobalConstants.isActiveLicenseStatusGreen);

        assertTrue(mispServicesPage.areAllSortIconsPresentExceptActionAndLicenseKey(),
                GlobalConstants.isMispLicenseListSortIconsPresentExceptActionAndLicenseKey);

        mispServicesPage.clickOnSortAscIcon("partnerId");
        assertTrue(mispServicesPage.waitUntilTableSortedAscendingByColumn(1),
                GlobalConstants.isMispLicenseListColumnSortingFunctional);
        assertTrue(mispServicesPage.isSortAscIconActive("partnerId"), GlobalConstants.isMispLicenseListColumnSortingFunctional);

        mispServicesPage.clickOnSortDescIcon("partnerId");
        assertTrue(mispServicesPage.waitUntilTableSortedDescendingByColumn(1),
                GlobalConstants.isMispLicenseListColumnSortingFunctional);
        assertTrue(mispServicesPage.isSortDescIconActive("partnerId"), GlobalConstants.isMispLicenseListColumnSortingFunctional);

        mispServicesPage.clickOnSortDescIcon("createdDateTime");
        assertTrue(mispServicesPage.waitUntilTableSortedDescendingByColumn(6),
                GlobalConstants.isMispLicenseListColumnSortingFunctional);

        mispServicesPage.clickOnLicenseRowPartnerIdCell(1);
        assertTrue(mispServicesPage.isViewMispLicenseKeyDetailsPageDisplayed(),
                GlobalConstants.isMispLicenseListActiveRowClickNavigatesToDetails);
        mispServicesPage.clickOnViewMispLicenseKeyBackButton();

        mispServicesPage.clickOnMispLicenseListActionButton();
        mispServicesPage.clickOnMispLicenseListDeactivateButton();
        assertTrue(mispServicesPage.isDeactivatePopupHeaderDisplayed(),
                GlobalConstants.isDeactivatePopupHeaderDisplayedForMispLicense);
        assertTrue(mispServicesPage.isDeactivateCancelButtonDisplayed(),
                GlobalConstants.isDeactivateCancelButtonAvailableForMispLicense);
        mispServicesPage.clickOnDeactivateCancelButton();
        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.ACTIVE_STATUS_LABEL);

        mispServicesPage.clickOnMispLicenseListActionButton();
        mispServicesPage.clickOnMispLicenseListDeactivateButton();
        mispServicesPage.clickOnDeactivateSubmitButton();
        mispServicesPage.waitUntilLatestLicenseRowStatusEquals(GlobalConstants.MISP_LICENSE_INACTIVE_STATUS_LABEL);
        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.MISP_LICENSE_INACTIVE_STATUS_LABEL,
                GlobalConstants.isMispLicenseStatusInactiveAfterDeactivation);

        assertTrue(!mispServicesPage.isMispLicenseListEyeIconPresentForRow(1),
                GlobalConstants.isMispLicenseListEyeIconPresentOnlyForActive);
        assertTrue(mispServicesPage.isLicenseRowGreyedOut(1), GlobalConstants.isDeactivatedLicenseRowGreyedOut);

        mispServicesPage.clickOnLicenseRowPartnerIdCell(1);
        assertTrue(!mispServicesPage.isViewMispLicenseKeyDetailsPageDisplayedQuick(),
                GlobalConstants.isMispLicenseListDeactivatedRowClickDoesNotNavigate);
        assertEquals(mispServicesPage.getPageTitleText(), getExpectedMispServicesPageTitle(),
                GlobalConstants.isMispLicenseListDeactivatedRowClickDoesNotNavigate);

        mispServicesPage.clickOnMispLicenseListActionButton();
        assertTrue(!mispServicesPage.isMispLicenseListRegenerateButtonEnabled(),
                GlobalConstants.isMispLicenseListRegenerateButtonDisabledForInactiveLicense);
        assertTrue(mispServicesPage.isMispLicenseListDeactivateButtonDisplayed(),
                GlobalConstants.isMispLicenseListActionMenuAlwaysShowsAllThreeItems);
        assertTrue(!mispServicesPage.isMispLicenseListDeactivateButtonEnabled(),
                GlobalConstants.isMispLicenseListDeactivateButtonEnabledOnlyForActive);
        assertTrue(mispServicesPage.isMispLicenseListActionMenuDisplayed(),
                GlobalConstants.isMispLicenseListActionMenuAlwaysShowsAllThreeItems);

        mispServicesPage.clickOnMispLicenseListRegenerateButton();
        assertEquals(mispServicesPage.getPageTitleText(), getExpectedMispServicesPageTitle(),
                GlobalConstants.isRegenerateNavigationBlockedForInactiveLicense);

        assertTrue(mispServicesPage.isTableSortedDescendingByCreationDate(),
                GlobalConstants.isMispLicenseListSortedDescendingByCreatedDate);

        String defaultPageSizeText = mispServicesPage.getSelectedRecordsPerPageText();
        assertEquals(defaultPageSizeText, "8", GlobalConstants.isMispLicenseListDefaultPageSizeEight);
        assertTrue(mispServicesPage.getMispLicenseListRowCount() <= Integer.parseInt(defaultPageSizeText),
                GlobalConstants.isMispLicenseListDefaultPageSizeEight);

        mispServicesPage.clickOnPaginationRecordsPerPageDropdown();
        mispServicesPage.selectPaginationRecordsPerPageOption(2);
        assertEquals(mispServicesPage.getSelectedRecordsPerPageText(), "16", GlobalConstants.isMispLicenseListPageSizeConfigurable);
        assertTrue(mispServicesPage.getMispLicenseListRowCount() <= 16, GlobalConstants.isMispLicenseListPageSizeConfigurable);

        mispServicesPage.clickOnMispServicesTitleBackIcon();
        assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isMispServicesBackButtonNavigatesToHome);
    }

    @Test(priority = 7, description = "Deactivated license UI behavior", dependsOnMethods = "regenerateOptionDisabledForInactiveLicense")
    public void deactivationTabularViewRowStyleActionMenuAndIndividualViewStatus() {
        mispPartnerPage = new MispPartnerPage(driver);
        dashboardPage = new DashboardPage(driver);
        partnerCertificatePage = new PartnerCertificatePage(driver);
        mispServicesPage = new MispServicesPage(driver);
        dashboardPage.clickOnMispServices();

        mispServicesPage.clickOnGenerateMispLicenceKeyButton();
        createMispLicenseKey(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_POLICY_01,
                GlobalConstants.MISP_LICENSEKEY_DEACTIVATE_CONFIRM_TEST);
        assertTrue(mispServicesPage.isMispLicenseKeyPopupDisplayed(), GlobalConstants.isMispLicenseKeyPopupDisplayed);
        mispServicesPage.closeCopyIdPopup();

        // The list still needs to refresh to reflect the license just created above - reading the
        // count immediately can catch it before that refresh, giving a "before" baseline of 0.
        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count > 0);
        int rowCountBeforeDeactivation = mispServicesPage.getMispLicenseListRowCount();

        mispServicesPage.clickOnMispLicenseListActionButton();
        mispServicesPage.clickOnMispLicenseListDeactivateButton();
        mispServicesPage.clickOnDeactivateSubmitButton();

        assertEquals(mispServicesPage.getPageTitleText(), getExpectedMispServicesPageTitle(),
                GlobalConstants.isMispServicesTabularViewDisplayedAfterDeactivation);
        assertEquals(mispServicesPage.getMispLicenseListRowCount(), rowCountBeforeDeactivation,
                GlobalConstants.isMispServicesTabularViewDisplayedAfterDeactivation);

        mispServicesPage.clickOnGenerateMispLicenceKeyButton();
        createMispLicenseKey(GlobalConstants.MISP_PARTNER_USER, GlobalConstants.MISP_POLICY_01,
                GlobalConstants.MISP_LICENSEKEY_ROW_GREYED_OUT_TEST);
        assertTrue(mispServicesPage.isMispLicenseKeyPopupDisplayed(), GlobalConstants.isMispLicenseKeyPopupDisplayed);
        mispServicesPage.closeCopyIdPopup();

        mispServicesPage.clickOnMispLicenseListActionButton();
        mispServicesPage.clickOnMispLicenseListDeactivateButton();
        mispServicesPage.clickOnDeactivateSubmitButton();
        mispServicesPage.waitUntilLatestLicenseRowStatusEquals(GlobalConstants.MISP_LICENSE_INACTIVE_STATUS_LABEL);

        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.MISP_LICENSE_INACTIVE_STATUS_LABEL,
                GlobalConstants.isMispLicenseStatusInactiveAfterDeactivation);
        assertTrue(mispServicesPage.isLicenseRowGreyedOut(1), GlobalConstants.isDeactivatedLicenseRowGreyedOut);
        assertTrue(mispServicesPage.isMispLicenseListActionButtonNormalColor(),
                GlobalConstants.isMispLicenseListActionMenuIconNotGreyedOutForDeactivatedRow);

        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.MISP_LICENSE_INACTIVE_STATUS_LABEL);

        mispServicesPage.clickOnMispLicenseListActionButton();
        assertTrue(mispServicesPage.isMispLicenseListActionMenuDisplayed(),
                GlobalConstants.isMispLicenseListActionMenuAlwaysShowsAllThreeItems);
        assertTrue(mispServicesPage.isMispLicenseListRegenerateButtonDisplayed(),
                GlobalConstants.isMispLicenseListActionMenuAlwaysShowsAllThreeItems);
        assertTrue(!mispServicesPage.isMispLicenseListRegenerateButtonEnabled(),
                GlobalConstants.isMispLicenseListRegenerateButtonDisabledForInactiveLicense);
        assertTrue(mispServicesPage.isMispLicenseListDeactivateButtonDisplayed(),
                GlobalConstants.isMispLicenseListActionMenuAlwaysShowsAllThreeItems);
        assertTrue(!mispServicesPage.isMispLicenseListDeactivateButtonEnabled(),
                GlobalConstants.isMispLicenseListDeactivateButtonEnabledOnlyForActive);

        // dashboardPage.clickOnMispServices() clicks the dashboard's own MISP Services card, so a
        // fresh round-trip through the dashboard is needed first - we're still on the list page here.
        mispServicesPage.clickOnMispServicesTitleBackIcon();
        dashboardPage.clickOnMispServices();
        mispServicesPage.waitUntilMispLicenseListRowCountSatisfies(count -> count > 0);
        assertEquals(mispServicesPage.getLatestLicenseRowStatus(), GlobalConstants.MISP_LICENSE_INACTIVE_STATUS_LABEL);

        mispServicesPage.clickOnMispLicenseListActionButton();
        mispServicesPage.clickOnMispLicenseListViewButton();

        assertEquals(mispServicesPage.getViewMispLicenseKeyDetailsStatusText(),
                GlobalConstants.MISP_LICENSE_INACTIVE_STATUS_LABEL,
                GlobalConstants.isMispLicenseKeyStatusDeactivatedInViewDetails);

        mispServicesPage.clickOnViewMispLicenseKeyBackButton();
    }

    @Test(priority = 8, description = "Important Note behavior", dependsOnMethods = "deactivationTabularViewRowStyleActionMenuAndIndividualViewStatus")
    public void importantNoteTextReadOnlyAndFormFieldInterference() {
        mispPartnerPage = new MispPartnerPage(driver);
        dashboardPage = new DashboardPage(driver);
        partnerCertificatePage = new PartnerCertificatePage(driver);
        mispServicesPage = new MispServicesPage(driver);

        openGenerateMispLicenseKeyScreen();

        assertTrue(mispServicesPage.isMispLicenseKeyImportantNoteDisplayed(),
                GlobalConstants.isMispLicenseKeyImportantNoteDisplayed);
        assertEquals(mispServicesPage.getMispLicenseKeyImportantNoteText(),
                GlobalConstants.MISP_LICENSE_KEY_IMPORTANT_NOTE, GlobalConstants.isImportantNoteTextCorrect);

        assertTrue(mispServicesPage.isMispLicenseKeyImportantNoteNotEditable(),
                GlobalConstants.isMispLicenseKeyImportantNoteNotEditable);
        assertFalse(mispServicesPage.isMispLicenseKeyImportantNoteFocusable(),
                GlobalConstants.isImportantNoteNotFocusable);
        assertTrue(
                mispServicesPage
                        .isMispLicenseKeyImportantNoteUnchangedAfterTyping(GlobalConstants.IMPORTANT_NOTE_EDIT_ATTEMPT),
                GlobalConstants.isImportantNoteReadOnly);

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

    private void regenerateMispLicenseKey(String licenseKeyName) {
        mispServicesPage.enterRegenerateLicenseKeyName(licenseKeyName);
        mispServicesPage.enterRegenerateExpiryDate();
        assertTrue(mispServicesPage.isRegenerateSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
        mispServicesPage.clickOnRegenerateSubmitButton();
    }

    private void regenerateMispLicenseKeyWithPastExpiryDate(String licenseKeyName) {
        mispServicesPage.enterRegenerateLicenseKeyName(licenseKeyName);
        mispServicesPage.enterRegeneratePastExpiryDate();
        mispServicesPage.clickOnRegenerateSubmitButton();
    }

    private void regenerateMispLicenseKeyWithTodayExpiryDate(String licenseKeyName) {
        mispServicesPage.enterRegenerateLicenseKeyName(licenseKeyName);
        mispServicesPage.enterRegenerateTodayExpiryDate();
        mispServicesPage.clickOnRegenerateSubmitButton();
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

    private String getExpectedRegenerateMispLicenseKeyPageTitle() {
        String lang = ConfigManager.getloginlang();
        if ("ara".equalsIgnoreCase(lang)) {
            return GlobalConstants.REGENERATE_MISP_LICENSE_KEY_PAGE_TITLE_ARA;
        } else if ("fra".equalsIgnoreCase(lang)) {
            return GlobalConstants.REGENERATE_MISP_LICENSE_KEY_PAGE_TITLE_FRA;
        }
        return GlobalConstants.REGENERATE_MISP_LICENSE_KEY_PAGE_TITLE;
    }

    private String getExpectedRegenerateBreadcrumbText() {
        String lang = ConfigManager.getloginlang();
        if ("ara".equalsIgnoreCase(lang)) {
            return GlobalConstants.REGENERATE_BREADCRUMB_TEXT_ARA;
        } else if ("fra".equalsIgnoreCase(lang)) {
            return GlobalConstants.REGENERATE_BREADCRUMB_TEXT_FRA;
        }
        return GlobalConstants.REGENERATE_BREADCRUMB_TEXT;
    }

    private String getExpectedRegenerateMandatoryFieldsSubtitleText() {
        String lang = ConfigManager.getloginlang();
        if ("ara".equalsIgnoreCase(lang)) {
            return GlobalConstants.REGENERATE_MANDATORY_FIELDS_SUBTITLE_TEXT_ARA;
        } else if ("fra".equalsIgnoreCase(lang)) {
            return GlobalConstants.REGENERATE_MANDATORY_FIELDS_SUBTITLE_TEXT_FRA;
        }
        return GlobalConstants.REGENERATE_MANDATORY_FIELDS_SUBTITLE_TEXT;
    }

    private String getExpectedMispListHeaderText(String eng, String fra, String ara) {
        String lang = ConfigManager.getloginlang();
        if ("ara".equalsIgnoreCase(lang)) {
            return ara;
        } else if ("fra".equalsIgnoreCase(lang)) {
            return fra;
        }
        return eng;
    }

    private String getExpectedMispLicenseKeyPopupHeaderText() {
        String lang = ConfigManager.getloginlang();
        if ("ara".equalsIgnoreCase(lang)) {
            return GlobalConstants.MISP_LICENSE_KEY_POPUP_HEADER_TEXT_ARA;
        } else if ("fra".equalsIgnoreCase(lang)) {
            return GlobalConstants.MISP_LICENSE_KEY_POPUP_HEADER_TEXT_FRA;
        }
        return GlobalConstants.MISP_LICENSE_KEY_POPUP_HEADER_TEXT;
    }

    private String getExpectedRegenerateLicenseKeyNameHelpText() {
        String lang = ConfigManager.getloginlang();
        if ("ara".equalsIgnoreCase(lang)) {
            return GlobalConstants.REGENERATE_LICENSE_KEY_NAME_HELP_TEXT_ARA;
        } else if ("fra".equalsIgnoreCase(lang)) {
            return GlobalConstants.REGENERATE_LICENSE_KEY_NAME_HELP_TEXT_FRA;
        }
        return GlobalConstants.REGENERATE_LICENSE_KEY_NAME_HELP_TEXT;
    }

    private String getExpectedNoPolicyNameSelectedText() {
        String lang = ConfigManager.getloginlang();
        if ("ara".equalsIgnoreCase(lang)) {
            return GlobalConstants.NO_POLICY_NAME_SELECTED_ARA;
        } else if ("fra".equalsIgnoreCase(lang)) {
            return GlobalConstants.NO_POLICY_NAME_SELECTED_FRA;
        }
        return GlobalConstants.NO_POLICY_NAME_SELECTED;
    }

    private String getExpectedRegenerateConfirmationHeaderText() {
        String lang = ConfigManager.getloginlang();
        if ("ara".equalsIgnoreCase(lang)) {
            return GlobalConstants.REGENERATE_LICENSE_KEY_CONFIRMATION_HEADER_TEXT_ARA;
        } else if ("fra".equalsIgnoreCase(lang)) {
            return GlobalConstants.REGENERATE_LICENSE_KEY_CONFIRMATION_HEADER_TEXT_FRA;
        }
        return GlobalConstants.REGENERATE_LICENSE_KEY_CONFIRMATION_HEADER_TEXT;
    }

    private String getExpectedMispServicesPageTitle() {
        String lang = ConfigManager.getloginlang();
        if ("ara".equalsIgnoreCase(lang)) {
            return GlobalConstants.MISP_SERVICES_PAGE_TITLE_ARA;
        } else if ("fra".equalsIgnoreCase(lang)) {
            return GlobalConstants.MISP_SERVICES_PAGE_TITLE_FRA;
        }
        return GlobalConstants.MISP_SERVICES_PAGE_TITLE;
    }

}
