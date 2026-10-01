package io.mosip.testrig.pmpuiv2.testcase;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerAdminPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;

@Test(dependsOnGroups = { "OnlineVerificationPartnerTest" }, groups = { "OnlineVerificationPartnerViewTest" })
public class OnlineVerificationPartnerViewTest extends BaseClass {

    @Test(priority = 1, description = "TC_1853_03 to TC_1853_07 Filter Online Verification Partners")
    public void filterOnlineVerificationPartners() {
        PartnerAdminPage partnerAdminPage = openPartnerList();

        LogUtil.step("TC_1853_04 TC_1853_05 Select Online Verification Partner with no manual search");
        partnerAdminPage.clickOnFilterButton();
        partnerAdminPage.clickOnPartnerTypeDropdown();
        Assert.assertTrue(partnerAdminPage.isPartnerTypeFilterOptionDisplayed(),
                GlobalConstants.isPartnerTypeFilterOptionsOpen);
        Assert.assertFalse(partnerAdminPage.isPartnerTypeDropdownSearchInputDisplayed(),
                GlobalConstants.isPartnerTypeFilterSearchAbsent);
        partnerAdminPage.selectPartnerTypeFilterOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
        Assert.assertEquals(partnerAdminPage.getPartnerTypeFilterButtonText(),
                GlobalConstants.ONLINE_VERIFICATION_PARTNER,
                GlobalConstants.isOnlineVerificationPartnerSelectableInFilter);

        LogUtil.step("TC_1853_03 TC_1853_06 Apply the filter and list only that partner type");
        partnerAdminPage.clickOnApplyFiltersBtn();
        Assert.assertTrue(
                partnerAdminPage.isPartnerListLoadedByPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
                GlobalConstants.isOnlineVerificationPartnerFilterApplied);
        Assert.assertEquals(partnerAdminPage.getFirstRowPartnerType(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
                GlobalConstants.isOnlineVerificationPartnerFilterApplied);
        Assert.assertTrue(partnerAdminPage.areAllVisiblePartnerTypes(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
                GlobalConstants.isOnlyOnlineVerificationPartnersListed);

        LogUtil.step("TC_1853_07 Apply partner type and Inactive status together");
        partnerAdminPage.clickOnFilterResetButton();
        Assert.assertTrue(partnerAdminPage.isPartnerListLoaded(), GlobalConstants.isPartnerListLoaded);
        applyOnlineVerificationPartnerAndStatusFilter(partnerAdminPage, GlobalConstants.PARTNER_STATUS_INACTIVE);
        Assert.assertTrue(
                partnerAdminPage.isPartnerListLoadedByPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
                GlobalConstants.isMultipleFiltersShowOnlineVerificationPartner);
        Assert.assertTrue(partnerAdminPage.isFirstRowStatus(GlobalConstants.PARTNER_STATUS_INACTIVE),
                GlobalConstants.isMultipleFiltersShowOnlineVerificationPartner);
        Assert.assertTrue(partnerAdminPage.areAllVisiblePartnerTypes(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
                GlobalConstants.isMultipleFiltersShowOnlineVerificationPartner);
        Assert.assertTrue(partnerAdminPage.areAllVisiblePartnerStatuses(GlobalConstants.PARTNER_STATUS_INACTIVE),
                GlobalConstants.isMultipleFiltersShowOnlineVerificationPartner);
    }

    @Test(priority = 2, description = "TC_1853_01 TC_1853_02 TC_1853_08 TC_1853_12 TC_1853_16 to TC_1853_19 TC_1853_21 TC_1853_22 View partner details")
    public void viewOnlineVerificationPartnerDetails() {
        PartnerAdminPage partnerAdminPage = openFilteredOnlineVerificationPartnerList();

        LogUtil.step("TC_1853_01 Open the details from the partner row");
        partnerAdminPage.clickOnActivatedPartner();
        Assert.assertTrue(partnerAdminPage.isViewPartnersDetailsPageDisplayed(),
                GlobalConstants.isViewPartnerDetailsOpened);
        Assert.assertEquals(partnerAdminPage.getPageTitleText(), GlobalConstants.VIEW_PARTNER_DETAILS_TITLE,
                GlobalConstants.isViewPartnerDetailsOpened);
        Assert.assertEquals(partnerAdminPage.getViewPartnerDetailsPartnerType(),
                GlobalConstants.ONLINE_VERIFICATION_PARTNER, GlobalConstants.isViewPartnerDetailsOpened);
        partnerAdminPage.clickOnViewPartnerBackButton();
        Assert.assertTrue(partnerAdminPage.isPartnerListLoaded(), GlobalConstants.isBackButtonReturnsToPartnerList);

        LogUtil.step("TC_1853_08 Open the same details from the action menu");
        applyOnlineVerificationPartnerFilter(partnerAdminPage);
        Assert.assertTrue(
                partnerAdminPage.isPartnerListLoadedByPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
                GlobalConstants.isOnlineVerificationPartnerFilterApplied);
        String partnerId = partnerAdminPage.getFirstRowPartnerId();
        String status = partnerAdminPage.getFirstRowPartnerStatus();
        String partnerType = partnerAdminPage.getFirstRowPartnerType();
        String organisation = partnerAdminPage.getFirstRowOrganisationName();
        String policyGroup = partnerAdminPage.getFirstRowPolicyGroup();
        String email = partnerAdminPage.getFirstRowEmailAddress();
        openViewOfCurrentFirstRow(partnerAdminPage);

        LogUtil.step("TC_1853_12 TC_1853_02 TC_1853_16 TC_1853_17 Title and read only details");
        Assert.assertEquals(partnerAdminPage.getPageTitleText(), GlobalConstants.VIEW_PARTNER_DETAILS_TITLE,
                GlobalConstants.isViewPartnerDetailsOpened);
        Assert.assertTrue(partnerAdminPage.areViewPartnerValueFieldsReadOnly(),
                GlobalConstants.isViewPartnerDetailsReadOnly);
        Assert.assertFalse(partnerAdminPage.isViewPartnerEditControlDisplayed(),
                GlobalConstants.isViewPartnerDetailsReadOnly);

        LogUtil.step("TC_1853_18 TC_1853_19 TC_1853_21 TC_1853_22 Partner values match the list");
        Assert.assertEquals(partnerAdminPage.getViewPartnerIdText(), GlobalConstants.PARTNER_ID + ": " + partnerId,
                GlobalConstants.isViewPartnerIdMatchesList);
        Assert.assertEquals(partnerAdminPage.getPartnerStatusInViewPartnerDetails(), status,
                GlobalConstants.isViewPartnerStatusMatchesList);
        Assert.assertEquals(partnerAdminPage.getViewPartnerDetailsPartnerType(), partnerType,
                GlobalConstants.isViewPartnerFieldsMatchList);
        Assert.assertEquals(partnerAdminPage.getViewOrganisationName(), organisation,
                GlobalConstants.isViewPartnerFieldsMatchList);
        Assert.assertEquals(partnerAdminPage.getViewPolicyGroup(), policyGroup,
                GlobalConstants.isViewPartnerFieldsMatchList);
        Assert.assertEquals(partnerAdminPage.getViewEmail(), email, GlobalConstants.isViewPartnerFieldsMatchList);
        Assert.assertEquals(partnerAdminPage.getViewFirstName(), GlobalConstants.BLANK_FIELD_VALUE,
                GlobalConstants.isBlankNameShownAsDash);
        Assert.assertEquals(partnerAdminPage.getViewLastName(), GlobalConstants.BLANK_FIELD_VALUE,
                GlobalConstants.isBlankNameShownAsDash);
    }

    @Test(priority = 3, description = "TC_1853_13 TC_1853_14 TC_1853_15 TC_1853_33 View partner navigation")
    public void viewPartnerDetailsNavigation() {
        PartnerAdminPage partnerAdminPage = openFilteredOnlineVerificationPartnerList();
        openViewOfCurrentFirstRow(partnerAdminPage);

        LogUtil.step("TC_1853_13 Back beside the title returns to the partner list");
        partnerAdminPage.clickOnViewPartnerBackButton();
        Assert.assertTrue(partnerAdminPage.isPartnerListLoaded(), GlobalConstants.isBackButtonReturnsToPartnerList);

        LogUtil.step("TC_1853_15 List of Partners beside Home returns to the partner list");
        applyOnlineVerificationPartnerFilter(partnerAdminPage);
        openViewOfCurrentFirstRow(partnerAdminPage);
        partnerAdminPage.clickOnlistOfPartners();
        Assert.assertTrue(partnerAdminPage.isPartnerListLoaded(),
                GlobalConstants.isListOfPartnersButtonReturnsToPartnerList);

        LogUtil.step("TC_1853_33 Go Back returns to the partner list");
        applyOnlineVerificationPartnerFilter(partnerAdminPage);
        openViewOfCurrentFirstRow(partnerAdminPage);
        Assert.assertTrue(partnerAdminPage.isGobackButtonInViewPatnerPageDisplayed(),
                GlobalConstants.isBackButtonReturnsToPartnerList);
        partnerAdminPage.clickOngobackButtonInPartnerDetailsPage();
        Assert.assertTrue(partnerAdminPage.isPartnerListLoaded(), GlobalConstants.isBackButtonReturnsToPartnerList);

        LogUtil.step("TC_1853_14 Home returns to the dashboard");
        applyOnlineVerificationPartnerFilter(partnerAdminPage);
        openViewOfCurrentFirstRow(partnerAdminPage);
        partnerAdminPage.clickOnBreadcrumb();
        Assert.assertTrue(new DashboardPage(driver).isPartnersDisplayed(),
                GlobalConstants.isCreatePartnerHomeReturnsToDashboard);
    }

    @Test(priority = 4, description = "TC_1853_09 TC_1853_10 View option for Active and Inactive partners")
    public void viewOptionForPartnerStatus() {
        PartnerAdminPage partnerAdminPage = openPartnerList();

        LogUtil.step("TC_1853_09 View is available for an Active partner");
        applyOnlineVerificationPartnerAndStatusFilter(partnerAdminPage, GlobalConstants.PARTNER_STATUS_ACTIVE);
        Assert.assertTrue(
                partnerAdminPage.isPartnerListLoadedByPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
                GlobalConstants.isOnlineVerificationPartnerFilterApplied);
        Assert.assertTrue(partnerAdminPage.isFirstRowStatus(GlobalConstants.PARTNER_STATUS_ACTIVE),
                GlobalConstants.isViewOptionAvailable);
        partnerAdminPage.clickOnActionsButton();
        Assert.assertTrue(partnerAdminPage.isViewButtonsDisplayed(), GlobalConstants.isViewOptionAvailable);
        Assert.assertTrue(partnerAdminPage.isViewOptionEnabled(), GlobalConstants.isViewOptionAvailable);

        LogUtil.step("TC_1853_10 View is available for an Inactive partner");
        partnerAdminPage.clickOnFilterResetButton();
        Assert.assertTrue(partnerAdminPage.isPartnerListLoaded(), GlobalConstants.isPartnerListLoaded);
        applyOnlineVerificationPartnerAndStatusFilter(partnerAdminPage, GlobalConstants.PARTNER_STATUS_INACTIVE);
        Assert.assertTrue(
                partnerAdminPage.isPartnerListLoadedByPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
                GlobalConstants.isOnlineVerificationPartnerFilterApplied);
        Assert.assertTrue(partnerAdminPage.isFirstRowStatus(GlobalConstants.PARTNER_STATUS_INACTIVE),
                GlobalConstants.isViewOptionAvailable);
        partnerAdminPage.clickOnActionsButton();
        Assert.assertTrue(partnerAdminPage.isViewButtonsDisplayed(), GlobalConstants.isViewOptionAvailable);
        Assert.assertTrue(partnerAdminPage.isViewOptionEnabled(), GlobalConstants.isViewOptionAvailable);
    }

    @Test(priority = 5, description = "TC_1853_24 to TC_1853_26 and TC_1853_28 to TC_1853_32 Partner certificate on the view page")
    public void viewPartnerCertificate() {
        PartnerAdminPage partnerAdminPage = openPartnerList();

        LogUtil.step("TC_1853_24 TC_1853_28 Certificate is greyed out and download is disabled before upload");
        applyOnlineVerificationPartnerAndStatusFilter(partnerAdminPage, GlobalConstants.PARTNER_STATUS_INACTIVE);
        Assert.assertTrue(partnerAdminPage.isFirstRowStatus(GlobalConstants.PARTNER_STATUS_INACTIVE),
                GlobalConstants.isCertificateDownloadDisabled);
        openViewOfCurrentFirstRow(partnerAdminPage);
        Assert.assertTrue(partnerAdminPage.isPartnerCertificateSectionGreyedOut(),
                GlobalConstants.isCertificateSectionGreyed);
        Assert.assertTrue(partnerAdminPage.isDownloadCertificateButtonDisplayed(),
                GlobalConstants.isCertificateDownloadDisabled);
        Assert.assertFalse(partnerAdminPage.isDownloadCertificateButtonEnabledInViewPartnerPage(),
                GlobalConstants.isCertificateDownloadDisabled);

        LogUtil.step("TC_1853_25 TC_1853_26 Active partner shows certificate dates and an enabled download");
        partnerAdminPage.clickOnViewPartnerBackButton();
        Assert.assertTrue(partnerAdminPage.isPartnerListLoaded(), GlobalConstants.isBackButtonReturnsToPartnerList);
        applyOnlineVerificationPartnerAndStatusFilter(partnerAdminPage, GlobalConstants.PARTNER_STATUS_ACTIVE);
        Assert.assertTrue(partnerAdminPage.isFirstRowStatus(GlobalConstants.PARTNER_STATUS_ACTIVE),
                GlobalConstants.isCertificateDownloadEnabled);
        openViewOfCurrentFirstRow(partnerAdminPage);
        Assert.assertFalse(GlobalConstants.BLANK_FIELD_VALUE.equals(partnerAdminPage.getViewCertificateExpiry()),
                GlobalConstants.isCertificateDatesDisplayed);
        Assert.assertFalse(GlobalConstants.BLANK_FIELD_VALUE.equals(partnerAdminPage.getViewCertificateUploadTime()),
                GlobalConstants.isCertificateDatesDisplayed);
        Assert.assertTrue(partnerAdminPage.isDownloadCertificateButtonEnabledInViewPartnerPage(),
                GlobalConstants.isCertificateDownloadEnabled);

        LogUtil.step("TC_1853_29 TC_1853_30 TC_1853_31 TC_1853_32 Download original, MOSIP signed, then original again");
        partnerAdminPage.clickOnDownloadCertificateButtonInViewPartnerPage();
        Assert.assertTrue(partnerAdminPage.isOriginalCertificateDropdownDisplayed(),
                GlobalConstants.isCertificateDownloadOptionsDisplayed);
        Assert.assertTrue(partnerAdminPage.isMosipSignedCertificateDropdownDisplayed(),
                GlobalConstants.isCertificateDownloadOptionsDisplayed);
        partnerAdminPage.clickOnOriginnalCertificateInViewPartnerPage();
        Assert.assertEquals(partnerAdminPage.getViewPartnerSuccessMessage(),
                GlobalConstants.ORIGINAL_CERTIFICATE_DOWNLOAD_SUCCESS, GlobalConstants.isCertificateDownloaded);
        partnerAdminPage.clickOnMosipSignedCertificateInViewPartnerPage();
        Assert.assertEquals(partnerAdminPage.getViewPartnerSuccessMessage(),
                GlobalConstants.MOSIP_SIGNED_CERTIFICATE_DOWNLOAD_SUCCESS, GlobalConstants.isCertificateDownloaded);
        partnerAdminPage.clickOnOriginnalCertificateInViewPartnerPage();
        Assert.assertEquals(partnerAdminPage.getViewPartnerSuccessMessage(),
                GlobalConstants.ORIGINAL_CERTIFICATE_DOWNLOAD_SUCCESS, GlobalConstants.isCertificateDownloaded);
    }

    private PartnerAdminPage openPartnerList() {
        PartnerAdminPage partnerAdminPage = new PartnerAdminPage(driver);
        if (driver.findElements(By.id("filter_btn")).isEmpty()) {
            new DashboardPage(driver).clickOnPartners();
        }
        Assert.assertTrue(partnerAdminPage.isPartnerListLoaded(), GlobalConstants.isPartnerListLoaded);
        return partnerAdminPage;
    }

    private PartnerAdminPage openFilteredOnlineVerificationPartnerList() {
        PartnerAdminPage partnerAdminPage = openPartnerList();
        applyOnlineVerificationPartnerFilter(partnerAdminPage);
        Assert.assertTrue(
                partnerAdminPage.isPartnerListLoadedByPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
                GlobalConstants.isOnlineVerificationPartnerFilterApplied);
        return partnerAdminPage;
    }

    private void openViewOfCurrentFirstRow(PartnerAdminPage partnerAdminPage) {
        partnerAdminPage.clickOnActionsButton();
        partnerAdminPage.clickOnViewButtonInListOfPartnerDetailsScreen();
        Assert.assertTrue(partnerAdminPage.isViewPartnersDetailsPageDisplayed(),
                GlobalConstants.isViewPartnerDetailsOpened);
    }

    private void applyOnlineVerificationPartnerFilter(PartnerAdminPage partnerAdminPage) {
        partnerAdminPage.clickOnFilterButton();
        partnerAdminPage.clickOnPartnerTypeDropdown();
        partnerAdminPage.selectPartnerTypeFilterOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
        partnerAdminPage.clickOnApplyFiltersBtn();
        Assert.assertTrue(
                partnerAdminPage.isPartnerListLoadedByPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
                GlobalConstants.isOnlineVerificationPartnerFilterApplied);
    }

    private void applyOnlineVerificationPartnerAndStatusFilter(PartnerAdminPage partnerAdminPage, String status) {
        partnerAdminPage.clickOnFilterButton();
        partnerAdminPage.clickOnPartnerTypeDropdown();
        partnerAdminPage.selectPartnerTypeFilterOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
        partnerAdminPage.clickOnStatusFilter();
        partnerAdminPage.selectStatusFilterOption(status);
        partnerAdminPage.clickOnApplyFiltersBtn();
    }
}
