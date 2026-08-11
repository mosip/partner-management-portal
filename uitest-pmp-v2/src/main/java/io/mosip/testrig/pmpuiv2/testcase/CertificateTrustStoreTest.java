package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "AuthPartnerCreation" }, groups = { "CertificateTrustStoreTest" })
public class CertificateTrustStoreTest extends BaseClass {

	private DashboardPage dashboardPage;
	private PartnerCertificatePage partnerCertificatePage;

	@Test(priority = 1, description = "Upload Invalid Certificates")
	public void uploadInvlidCertificates() {

		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		assertTrue(dashboardPage.isCertificateTrustStoreDisplayed(), GlobalConstants.isCertificateTrustStoreDisplayed);
		dashboardPage.clickOnCertificateTrustStore();
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();

		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isSubtitleHomeButtonDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isSubtitleButtonButtonDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		assertTrue(partnerCertificatePage.isAdminCertUploadCancelButtonDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		assertTrue(partnerCertificatePage.isUploadTrustCertificateTextDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isPartnerPageSubTitleTextDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isCertFormatesTextDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadBoxHeaderTextDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isSubmitButtonForAdminDisplayed(), GlobalConstants.isSubmitButtonEnabled);

		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isSubtitleHomeButtonDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isSubtitleButtonButtonDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		assertTrue(partnerCertificatePage.isAdminCertUploadCancelButtonDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		assertTrue(partnerCertificatePage.isUploadTrustCertificateTextDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isPartnerPageSubTitleTextDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isCertFormatesTextDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isUploadBoxHeaderTextDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		assertTrue(partnerCertificatePage.isSubmitButtonForAdminDisplayed(), GlobalConstants.isSubmitButtonEnabled);

		assertTrue(partnerCertificatePage.isPartnerAdminCertUploadTitleDisplayed(),
				GlobalConstants.isPartnerAdminCertUploadTitleDisplayed);
		assertTrue(partnerCertificatePage.isUploadInstructionMessageDisplayed(),
				GlobalConstants.isUploadInstructionMessageDisplayed);

		assertTrue(partnerCertificatePage.isSelectPartnerDomainPlaceHolderDisplayed(),
				GlobalConstants.isSelectPartnerDomainPlaceHolderDisplayed);
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		assertTrue(partnerCertificatePage.isPartnerDomainDropdownAuthDisplayed(),
				GlobalConstants.isPartnerDomainDropdownAuthDisplayed);
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();

		assertTrue(partnerCertificatePage.isUploadCertInstructionTextDisplayed(),
				GlobalConstants.isUploadCertInstructionTextDisplayed);
		assertTrue(partnerCertificatePage.isCertFormatesTextDisplayed(), GlobalConstants.isCertFormatesTextDisplayed);
		assertTrue(partnerCertificatePage.isSubmitButtonForAdminDisabled(),
				GlobalConstants.isSubmitButtonForAdminDisabled);

		partnerCertificatePage.uploadExpiredCertificateForRootCa();
		assertTrue(partnerCertificatePage.isUploadedRootCACertificateNameDisplayed(),
				GlobalConstants.isUploadedRootCACertificateNameDisplayed);
		assertTrue(partnerCertificatePage.isCertificateRemoveButtonDisplayed(),
				GlobalConstants.isCertificateRemoveButtonDisplayed);
		assertTrue(partnerCertificatePage.isSubmitButtonForAdminDisplayed(),
				GlobalConstants.isSubmitButtonForAdminDisplayed);
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		assertTrue(partnerCertificatePage.isCertificateDatesNotValidMessageDisplayed(),
				GlobalConstants.isCertificateDatesNotValidMessageDisplayed);
		partnerCertificatePage.clickOnErrorCloseButton();
		partnerCertificatePage.clickOnCertificateClearButton();
		assertTrue(partnerCertificatePage.isSelectPartnerDomainPlaceHolderDisplayed(),
				GlobalConstants.isSelectPartnerDomainPlaceHolderDisplayed);
		assertTrue(partnerCertificatePage.isUploadCertInstructionTextDisplayed(),
				GlobalConstants.isUploadCertInstructionTextDisplayed);

	}

	@Test(priority = 2, description = "Tabular View Of Uploaded Root CA Certificates", dependsOnMethods = "uploadInvlidCertificates")
	public void tabularViewOfUploadedRootCACertificates() {

		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(partnerCertificatePage.isCertificateTrustStoreTitleDisplayed(),
				GlobalConstants.isCertificateTrustStoreTitleDisplayed);
		assertTrue(partnerCertificatePage.isRootCACertTabDisplayed(), GlobalConstants.isRootCACertTabDisplayed);
		assertTrue(partnerCertificatePage.isSubtitleOfRootCADisplayed(), GlobalConstants.isSubtitleOfRootCADisplayed);
		assertTrue(partnerCertificatePage.isBreadcumbHomeDisplayed(), GlobalConstants.isBreadcumbHomeDisplayed);

		assertTrue(partnerCertificatePage.isCertificateIdHeaderDisplayed(),
				GlobalConstants.isCertificateIdHeaderDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainHeaderDisplayed(),
				GlobalConstants.isPartnerDomainHeaderDisplayed);
		assertTrue(partnerCertificatePage.isIssuedToHeaderDisplayed(), GlobalConstants.isIssuedToHeaderDisplayed);
		assertTrue(partnerCertificatePage.isIssuedByHeaderDisplayed(), GlobalConstants.isIssuedByHeaderDisplayed);
		assertTrue(partnerCertificatePage.isValidFromHeaderDisplayed(), GlobalConstants.isValidFromHeaderDisplayed);
		assertTrue(partnerCertificatePage.isValidToHeaderDisplayed(), GlobalConstants.isValidToHeaderDisplayed);
		assertTrue(partnerCertificatePage.isUploadedOnHeaderDisplayed(), GlobalConstants.isUploadedOnHeaderDisplayed);
		assertTrue(partnerCertificatePage.isValidityStatusHeaderDisplayed(),
				GlobalConstants.isValidityStatusHeaderDisplayed);
		assertTrue(partnerCertificatePage.isActionHeaderDisplayed(), GlobalConstants.isActionHeaderDisplayed);

		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		assertTrue(partnerCertificatePage.isCertificateUploadPopupDisplayed(),
				GlobalConstants.isCertificateUploadPopupDisplayed);
		partnerCertificatePage.clickOnTitleBackButton();

		assertTrue(partnerCertificatePage.isCertificateIdAscIconDisplayed(),
				GlobalConstants.isCertificateIdAscIconDisplayed);
		assertTrue(partnerCertificatePage.isCertificateIdDescIconDisplayed(),
				GlobalConstants.isCertificateIdDescIconDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainAscIconDisplayed(),
				GlobalConstants.isPartnerDomainAscIconDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainDescIconDisplayed(),
				GlobalConstants.isPartnerDomainDescIconDisplayed);
		assertTrue(partnerCertificatePage.isIssuedToAscIconDisplayed(), GlobalConstants.isIssuedToAscIconDisplayed);
		assertTrue(partnerCertificatePage.isIssuedToDescIconDisplayed(), GlobalConstants.isIssuedToDescIconDisplayed);
		assertTrue(partnerCertificatePage.isIssuedByAscIconDisplayed(), GlobalConstants.isIssuedByAscIconDisplayed);
		assertTrue(partnerCertificatePage.isIssuedByDescIconDisplayed(), GlobalConstants.isIssuedByDescIconDisplayed);
		assertTrue(partnerCertificatePage.isValidFromAscIconDisplayed(), GlobalConstants.isValidFromAscIconDisplayed);
		assertTrue(partnerCertificatePage.isValidFromDescIconDisplayed(), GlobalConstants.isValidFromDescIconDisplayed);
		assertTrue(partnerCertificatePage.isValidToAscIconDisplayed(), GlobalConstants.isValidToAscIconDisplayed);
		assertTrue(partnerCertificatePage.isValidToDescIconDisplayed(), GlobalConstants.isValidToDescIconDisplayed);
		assertTrue(partnerCertificatePage.isUploadedTimeAscIconDisplayed(),
				GlobalConstants.isUploadedTimeAscIconDisplayed);
		assertTrue(partnerCertificatePage.isUploadedTimeDescIconDisplayed(),
				GlobalConstants.isUploadedTimeDescIconDisplayed);

		partnerCertificatePage.clickOnCertificateIdAscIcon();
		partnerCertificatePage.clickOnCertificateIdDescIcon();
		partnerCertificatePage.clickOnPartnerDomainAscIcon();
		partnerCertificatePage.clickOnPartnerDomainDescIcon();
		partnerCertificatePage.clickOnIssuedToAscIcon();
		partnerCertificatePage.clickOnIssuedToDescIcon();
		partnerCertificatePage.clickOnIssuedByAscIcon();
		partnerCertificatePage.clickOnIssuedByDescIcon();
		partnerCertificatePage.clickOnValidFromAscIcon();
		partnerCertificatePage.clickOnValidFromDescIcon();
		partnerCertificatePage.clickOnValidToAscIcon();
		partnerCertificatePage.clickOnValidToDescIcon();
		partnerCertificatePage.clickOnUploadedTimeAscIcon();
		partnerCertificatePage.clickOnUploadedTimeDescIcon();

		assertTrue(partnerCertificatePage.isFilterButtonDisplayed(), GlobalConstants.isFilterButtonDisplayed);
		partnerCertificatePage.clickOnFilterButton();
		assertTrue(partnerCertificatePage.isFilterButtonDisabled(), GlobalConstants.isFilterButtonDisabled);
		assertTrue(partnerCertificatePage.isFilterResetButtonEnabled(), GlobalConstants.isFilterResetButtonEnabled);
		assertTrue(partnerCertificatePage.isApplyFilterButtonDisabled(), GlobalConstants.isApplyFilterButtonDisabled);

		assertTrue(partnerCertificatePage.isCertIssuedByFilterDisplayed(),
				GlobalConstants.isCertIssuedByFilterDisplayed);
		assertTrue(partnerCertificatePage.isCertIssuedToFilterDisplayed(),
				GlobalConstants.isCertIssuedToFilterDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainFilterDisplayed(),
				GlobalConstants.isPartnerDomainFilterDisplayed);
		assertTrue(partnerCertificatePage.isCertIdFilterDisplayed(), GlobalConstants.isCertIdFilterDisplayed);

		partnerCertificatePage.selectPartnerDomainAuthInFilter();
		partnerCertificatePage.enterIssuedToInFilter(GlobalConstants.ROOTCAISSUEDTO);
		partnerCertificatePage.enterIssuedByInFilter(GlobalConstants.ROOTCAISSUEDBY);
		partnerCertificatePage.clickOnApplyFilterButton();
		partnerCertificatePage.clickOnCertificateList1();
		assertTrue(partnerCertificatePage.isRootCACertificateDetailsPageDisplayed(),
				GlobalConstants.isRootCACertificateDetailsPageDisplayed);
		partnerCertificatePage.clickOnViewTrustCertificateBackButton();

		partnerCertificatePage.clickOnFilterButton();
		partnerCertificatePage.selectPartnerDomainAuthInFilter();
		partnerCertificatePage.enterIssuedToInFilter("yfgtfgeyfght");
		partnerCertificatePage.clickOnApplyFilterButton();
		assertTrue(partnerCertificatePage.isNoResultsFoundDisplayed(), GlobalConstants.isNoResultsFoundDisplayed);
		partnerCertificatePage.clickOnTitleBackButton();

	}

	@Test(priority = 3, description = "Download Uploaded Root CA Certificates", dependsOnMethods = "tabularViewOfUploadedRootCACertificates")
	public void downloadUploadedRootCACertificates() {

		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		partnerCertificatePage.clickOnFilterButton();
		partnerCertificatePage.selectPartnerDomainAuthInFilter();
		partnerCertificatePage.enterIssuedToInFilter(GlobalConstants.ROOTCAISSUEDTO);
		partnerCertificatePage.enterIssuedByInFilter(GlobalConstants.ROOTCAISSUEDBY);
		partnerCertificatePage.clickOnApplyFilterButton();

		partnerCertificatePage.clickOncertificatelistview1();
		partnerCertificatePage.clickOnRootCADownloadButton();
		assertTrue(partnerCertificatePage.isRootCACertificateDownloadedDisplayed(),
				GlobalConstants.isRootCACertificateDownloadedDisplayed);
		partnerCertificatePage.clickOnSuccessMsgCloseButton();

		partnerCertificatePage.clickOnCertificateList1();
		assertTrue(partnerCertificatePage.isRootCACertificateDetailsPageDisplayed(),
				GlobalConstants.isRootCACertificateDetailsPageDisplayed);
		assertTrue(partnerCertificatePage.isCertificateDownloadButtonDisplayed(),
				GlobalConstants.isCertificateDownloadButtonDisplayed);
		partnerCertificatePage.clickOnCertificateDownloadButton();
		assertTrue(partnerCertificatePage.isRootCACertificateDownloadedDisplayed(),
				GlobalConstants.isRootCACertificateDownloadedDisplayed);
		partnerCertificatePage.clickOnSuccessMsgCloseButton();

	}

	@Test(priority = 4, description = "View Root CA Certificate Details", dependsOnMethods = "downloadUploadedRootCACertificates")
	public void viewRootCACertificateDetails() {

		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		partnerCertificatePage.clickOnFilterButton();
		partnerCertificatePage.selectPartnerDomainAuthInFilter();
		partnerCertificatePage.enterIssuedToInFilter(GlobalConstants.ROOTCAISSUEDTO);
		partnerCertificatePage.enterIssuedByInFilter(GlobalConstants.ROOTCAISSUEDBY);
		partnerCertificatePage.clickOnApplyFilterButton();
		partnerCertificatePage.clickOnCertificateList1();

		assertTrue(partnerCertificatePage.isRootCACertificateDetailsPageDisplayed(),
				GlobalConstants.isRootCACertificateDetailsPageDisplayed);
		assertTrue(partnerCertificatePage.isBreadcumbOfRootCADisplayed(), GlobalConstants.isBreadcumbOfRootCADisplayed);

		assertTrue(partnerCertificatePage.isCertificateIdLabelDisplayed(),
				GlobalConstants.isCertificateIdLabelDisplayed);
		assertTrue(partnerCertificatePage.isUploadedOnDateDisplayed(), GlobalConstants.isUploadedOnDateDisplayed);
		assertTrue(partnerCertificatePage.isIssuedToLabelDisplayed(), GlobalConstants.isIssuedToLabelDisplayed);
		assertTrue(partnerCertificatePage.isRootCAIssuedToContextDisplayed(),
				GlobalConstants.isRootCAIssuedToContextDisplayed);
		assertTrue(partnerCertificatePage.isIssuedByLabelDisplayed(), GlobalConstants.isIssuedByLabelDisplayed);
		assertTrue(partnerCertificatePage.isIssuedByContextDisplayed(), GlobalConstants.isIssuedByContextDisplayed);
		assertTrue(partnerCertificatePage.isCertificateThumbprintLabelDisplayed(),
				GlobalConstants.isCertificateThumbprintLabelDisplayed);
		assertTrue(partnerCertificatePage.isCertificateThumbprintValueDisplayed(),
				GlobalConstants.isCertificateThumbprintValueDisplayed);

		assertTrue(partnerCertificatePage.isRootCertificateTitleDisplayed(),
				GlobalConstants.isRootCertificateTitleDisplayed);

		assertTrue(partnerCertificatePage.isTrustCertificatePartnerTypeLabelDisplayed(),
				GlobalConstants.isTrustCertificatePartnerTypeLabelDisplayed);
		assertTrue(partnerCertificatePage.isTrustCertificatePartnerTypeContextDisplayed(),
				GlobalConstants.isTrustCertificatePartnerTypeContextDisplayed);

		assertTrue(partnerCertificatePage.isTrustCertificateLabelUploadDateTimeDisplayed(),
				GlobalConstants.isTrustCertificateLabelUploadDateTimeDisplayed);
		assertTrue(partnerCertificatePage.isTrustCertificateContextUploadDateTimeDisplayed(),
				GlobalConstants.isTrustCertificateContextUploadDateTimeDisplayed);
//		assertTrue(partnerCertificatePage.isValidFromDateTimeFormatValid(),
//				GlobalConstants.isValidFromDateTimeFormatValid);

		assertTrue(partnerCertificatePage.isTrustCertificateLabelExpiryDateTimeDisplayed(),
				GlobalConstants.isTrustCertificateLabelExpiryDateTimeDisplayed);
		assertTrue(partnerCertificatePage.isTrustCertificateContextExpiryDateTimeDisplayed(),
				GlobalConstants.isTrustCertificateContextExpiryDateTimeDisplayed);
//		assertTrue(partnerCertificatePage.isValidToDateTimeFormatValid(), GlobalConstants.isValidToDateTimeFormatValid);

		assertTrue(partnerCertificatePage.isCertificateDownloadButtonDisplayed(),
				GlobalConstants.isCertificateDownloadButtonDisplayed);
		assertTrue(partnerCertificatePage.isViewTrustCertificateBackButtonDisplayed(),
				GlobalConstants.isViewTrustCertificateBackButtonDisplayed);
		partnerCertificatePage.clickOnBreadcumbOfRootCA();
		assertTrue(partnerCertificatePage.isCertificateTrustStoreTitleDisplayed(),
				GlobalConstants.isCertificateTrustStoreTitleDisplayed);

	}

	@Test(priority = 5, description = "Tabular View Of Uploaded Intermediate CA Certificates", dependsOnMethods = "viewRootCACertificateDetails")
	public void tabularViewOfUploadedIntermediateCACertificates() {

		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(partnerCertificatePage.isCertificateTrustStoreTitleDisplayed(),
				GlobalConstants.isCertificateTrustStoreTitleDisplayed);
		assertTrue(partnerCertificatePage.isRootCACertTabDisplayed(), GlobalConstants.isRootCACertTabDisplayed);
		assertTrue(partnerCertificatePage.isIntermediateCACertTabDisplayed(),
				GlobalConstants.isIntermediateCACertTabDisplayed);
		partnerCertificatePage.clickOnIntermediateCACertTab();
		assertTrue(partnerCertificatePage.isSubtitleOfIntermediateCADisplayed(),
				GlobalConstants.isSubtitleOfIntermediateCADisplayed);
		assertTrue(partnerCertificatePage.isBreadcumbHomeDisplayed(), GlobalConstants.isBreadcumbHomeDisplayed);

		assertTrue(partnerCertificatePage.isCertificateIdHeaderDisplayed(),
				GlobalConstants.isCertificateIdHeaderDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainHeaderDisplayed(),
				GlobalConstants.isPartnerDomainHeaderDisplayed);
		assertTrue(partnerCertificatePage.isIssuedToHeaderDisplayed(), GlobalConstants.isIssuedToHeaderDisplayed);
		assertTrue(partnerCertificatePage.isIssuedByHeaderDisplayed(), GlobalConstants.isIssuedByHeaderDisplayed);
		assertTrue(partnerCertificatePage.isValidFromHeaderDisplayed(), GlobalConstants.isValidFromHeaderDisplayed);
		assertTrue(partnerCertificatePage.isValidToHeaderDisplayed(), GlobalConstants.isValidToHeaderDisplayed);
		assertTrue(partnerCertificatePage.isUploadedOnHeaderDisplayed(), GlobalConstants.isUploadedOnHeaderDisplayed);
		assertTrue(partnerCertificatePage.isValidityStatusHeaderDisplayed(),
				GlobalConstants.isValidityStatusHeaderDisplayed);
		assertTrue(partnerCertificatePage.isActionHeaderDisplayed(), GlobalConstants.isActionHeaderDisplayed);

		assertTrue(partnerCertificatePage.isCertificateIdAscIconDisplayed(),
				GlobalConstants.isCertificateIdAscIconDisplayed);
		assertTrue(partnerCertificatePage.isCertificateIdDescIconDisplayed(),
				GlobalConstants.isCertificateIdDescIconDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainAscIconDisplayed(),
				GlobalConstants.isPartnerDomainAscIconDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainDescIconDisplayed(),
				GlobalConstants.isPartnerDomainDescIconDisplayed);
		assertTrue(partnerCertificatePage.isIssuedToAscIconDisplayed(), GlobalConstants.isIssuedToAscIconDisplayed);
		assertTrue(partnerCertificatePage.isIssuedToDescIconDisplayed(), GlobalConstants.isIssuedToDescIconDisplayed);
		assertTrue(partnerCertificatePage.isIssuedByAscIconDisplayed(), GlobalConstants.isIssuedByAscIconDisplayed);
		assertTrue(partnerCertificatePage.isIssuedByDescIconDisplayed(), GlobalConstants.isIssuedByDescIconDisplayed);
		assertTrue(partnerCertificatePage.isValidFromAscIconDisplayed(), GlobalConstants.isValidFromAscIconDisplayed);
		assertTrue(partnerCertificatePage.isValidFromDescIconDisplayed(), GlobalConstants.isValidFromDescIconDisplayed);
		assertTrue(partnerCertificatePage.isValidToAscIconDisplayed(), GlobalConstants.isValidToAscIconDisplayed);
		assertTrue(partnerCertificatePage.isValidToDescIconDisplayed(), GlobalConstants.isValidToDescIconDisplayed);
		assertTrue(partnerCertificatePage.isUploadedTimeAscIconDisplayed(),
				GlobalConstants.isUploadedTimeAscIconDisplayed);
		assertTrue(partnerCertificatePage.isUploadedTimeDescIconDisplayed(),
				GlobalConstants.isUploadedTimeDescIconDisplayed);

		partnerCertificatePage.clickOnCertificateIdAscIcon();
		partnerCertificatePage.clickOnCertificateIdDescIcon();
		partnerCertificatePage.clickOnPartnerDomainAscIcon();
		partnerCertificatePage.clickOnPartnerDomainDescIcon();
		partnerCertificatePage.clickOnIssuedToAscIcon();
		partnerCertificatePage.clickOnIssuedToDescIcon();
		partnerCertificatePage.clickOnIssuedByAscIcon();
		partnerCertificatePage.clickOnIssuedByDescIcon();
		partnerCertificatePage.clickOnValidFromAscIcon();
		partnerCertificatePage.clickOnValidFromDescIcon();
		partnerCertificatePage.clickOnValidToAscIcon();
		partnerCertificatePage.clickOnValidToDescIcon();
		partnerCertificatePage.clickOnUploadedTimeAscIcon();
		partnerCertificatePage.clickOnUploadedTimeDescIcon();

		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		assertTrue(partnerCertificatePage.isCertificateUploadPopupDisplayed(),
				GlobalConstants.isCertificateUploadPopupDisplayed);
		partnerCertificatePage.clickOnTitleBackButton();

		assertTrue(partnerCertificatePage.isFilterButtonDisplayed(), GlobalConstants.isFilterButtonDisplayed);
		partnerCertificatePage.clickOnFilterButton();
		assertTrue(partnerCertificatePage.isFilterButtonDisabled(), GlobalConstants.isFilterButtonDisabled);
		assertTrue(partnerCertificatePage.isFilterResetButtonEnabled(), GlobalConstants.isFilterResetButtonEnabled);
		assertTrue(partnerCertificatePage.isApplyFilterButtonDisabled(), GlobalConstants.isApplyFilterButtonDisabled);

		assertTrue(partnerCertificatePage.isCertIssuedByFilterDisplayed(),
				GlobalConstants.isCertIssuedByFilterDisplayed);
		assertTrue(partnerCertificatePage.isCertIssuedToFilterDisplayed(),
				GlobalConstants.isCertIssuedToFilterDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainFilterDisplayed(),
				GlobalConstants.isPartnerDomainFilterDisplayed);
		assertTrue(partnerCertificatePage.isCertIdFilterDisplayed(), GlobalConstants.isCertIdFilterDisplayed);

		partnerCertificatePage.selectPartnerDomainAuthInFilter();
		partnerCertificatePage.enterIssuedToInFilter(GlobalConstants.INTERCAISSUEDTO);
		partnerCertificatePage.enterIssuedByInFilter(GlobalConstants.INTERCAISSUEDBY);
		partnerCertificatePage.clickOnApplyFilterButton();
		assertTrue(partnerCertificatePage.isStatusValidDisplayed(), GlobalConstants.isStatusValidDisplayed);
		assertTrue(partnerCertificatePage.isIssuedToDetailsDisplayed(), GlobalConstants.isIssuedToDetailsDisplayed);
		assertTrue(partnerCertificatePage.isIssuedByDetailsDisplayed(), GlobalConstants.isIssuedByDetailsDisplayed);

		partnerCertificatePage.clickOnCertificateList1();
		assertTrue(partnerCertificatePage.isViewIntermediateCADetailsPageDisplayed(),
				GlobalConstants.isViewIntermediateCADetailsPageDisplayed);
		partnerCertificatePage.clickOnViewTrustCertificateBackButton();

		partnerCertificatePage.clickOnFilterButton();
		partnerCertificatePage.selectPartnerDomainAuthInFilter();
		partnerCertificatePage.enterIssuedToInFilter("yfgtfgeyfght");
		partnerCertificatePage.clickOnApplyFilterButton();
		assertTrue(partnerCertificatePage.isNoResultsFoundDisplayed(), GlobalConstants.isNoResultsFoundDisplayed);
		partnerCertificatePage.clickOnTitleBackButton();
	}

	@Test(priority = 6, description = "Download Uploaded Int CA Certificates", dependsOnMethods = "tabularViewOfUploadedIntermediateCACertificates")
	public void downloadUploadedIntCACertificates() {

		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		partnerCertificatePage.clickOnIntermediateCACertTab();
		partnerCertificatePage.clickOnFilterButton();
		partnerCertificatePage.selectPartnerDomainAuthInFilter();
		partnerCertificatePage.enterIssuedToInFilter(GlobalConstants.INTERCAISSUEDTO);
		partnerCertificatePage.enterIssuedByInFilter(GlobalConstants.INTERCAISSUEDBY);
		partnerCertificatePage.clickOnApplyFilterButton();

		partnerCertificatePage.clickOncertificatelistview1();
		assertTrue(partnerCertificatePage.isDownloadCertificateChainButtonDisplayed(),
				GlobalConstants.isDownloadCertificateChainButtonDisplayed);
		partnerCertificatePage.clickOnDownloadCertificateChainButton();
		assertTrue(partnerCertificatePage.isIntCACertDownloadedSuccessMsgDisplayed(),
				GlobalConstants.isIntCACertDownloadedSuccessMsgDisplayed);
		partnerCertificatePage.clickOnSuccessMsgCloseButton();

		partnerCertificatePage.clickOnCertificateList1();
		assertTrue(partnerCertificatePage.isViewIntermediateCADetailsPageDisplayed(),
				GlobalConstants.isViewIntermediateCADetailsPageDisplayed);
		assertTrue(partnerCertificatePage.isCertificateDownloadButtonDisplayed(),
				GlobalConstants.isCertificateDownloadButtonDisplayed);
		partnerCertificatePage.clickOnCertificateDownloadButton();
		assertTrue(partnerCertificatePage.isIntCACertDownloadedSuccessMsgDisplayed(),
				GlobalConstants.isIntCACertDownloadedSuccessMsgDisplayed);
		partnerCertificatePage.clickOnSuccessMsgCloseButton();

	}

	@Test(priority = 7, description = "View Intermediate CA Certificate Details", dependsOnMethods = "downloadUploadedIntCACertificates")
	public void viewIntCACertificateDetails() {

		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		partnerCertificatePage.clickOnIntermediateCACertTab();
		partnerCertificatePage.clickOnFilterButton();
		partnerCertificatePage.selectPartnerDomainAuthInFilter();
		partnerCertificatePage.enterIssuedToInFilter(GlobalConstants.INTERCAISSUEDTO);
		partnerCertificatePage.enterIssuedByInFilter(GlobalConstants.INTERCAISSUEDBY);
		partnerCertificatePage.clickOnApplyFilterButton();

		partnerCertificatePage.clickOnCertificateList1();
		assertTrue(partnerCertificatePage.isViewIntermediateCADetailsPageDisplayed(),
				GlobalConstants.isViewIntermediateCADetailsPageDisplayed);
		assertTrue(partnerCertificatePage.isIntCACertBreadcumbDisplayed(),
				GlobalConstants.isIntCACertBreadcumbDisplayed);

		assertTrue(partnerCertificatePage.isCertificateIdLabelDisplayed(),
				GlobalConstants.isCertificateIdLabelDisplayed);
		assertTrue(partnerCertificatePage.isUploadedOnDateDisplayed(), GlobalConstants.isUploadedOnDateDisplayed);
		assertTrue(partnerCertificatePage.isIssuedToLabelDisplayed(), GlobalConstants.isIssuedToLabelDisplayed);
		assertTrue(partnerCertificatePage.isIssuedByLabelDisplayed(), GlobalConstants.isIssuedByLabelDisplayed);

		assertTrue(partnerCertificatePage.isIntCAIssuedToContextDisplayed(),
				GlobalConstants.isIntCAIssuedToContextDisplayed);
		assertTrue(partnerCertificatePage.isIntCAIssuedByContextDisplayed(),
				GlobalConstants.isIntCAIssuedByContextDisplayed);
		assertTrue(partnerCertificatePage.isCertificateThumbprintLabelDisplayed(),
				GlobalConstants.isCertificateThumbprintLabelDisplayed);
		assertTrue(partnerCertificatePage.isCertificateThumbprintValueDisplayed(),
				GlobalConstants.isCertificateThumbprintValueDisplayed);

		assertTrue(partnerCertificatePage.isIntCACertificateTitleDisplayed(),
				GlobalConstants.isIntCACertificateTitleDisplayed);

		assertTrue(partnerCertificatePage.isTrustCertificatePartnerTypeLabelDisplayed(),
				GlobalConstants.isTrustCertificatePartnerTypeLabelDisplayed);
		assertTrue(partnerCertificatePage.isTrustCertificatePartnerTypeContextDisplayed(),
				GlobalConstants.isTrustCertificatePartnerTypeContextDisplayed);

		assertTrue(partnerCertificatePage.isTrustCertificateLabelUploadDateTimeDisplayed(),
				GlobalConstants.isTrustCertificateLabelUploadDateTimeDisplayed);
		assertTrue(partnerCertificatePage.isTrustCertificateContextUploadDateTimeDisplayed(),
				GlobalConstants.isTrustCertificateContextUploadDateTimeDisplayed);
		assertTrue(partnerCertificatePage.isValidFromDateTimeFormatValid(),
				GlobalConstants.isValidFromDateTimeFormatValid);

		assertTrue(partnerCertificatePage.isTrustCertificateLabelExpiryDateTimeDisplayed(),
				GlobalConstants.isTrustCertificateLabelExpiryDateTimeDisplayed);
		assertTrue(partnerCertificatePage.isTrustCertificateContextExpiryDateTimeDisplayed(),
				GlobalConstants.isTrustCertificateContextExpiryDateTimeDisplayed);
		assertTrue(partnerCertificatePage.isValidToDateTimeFormatValid(), GlobalConstants.isValidToDateTimeFormatValid);

		assertTrue(partnerCertificatePage.isCertificateDownloadButtonDisplayed(),
				GlobalConstants.isCertificateDownloadButtonDisplayed);
		assertTrue(partnerCertificatePage.isViewTrustCertificateBackButtonDisplayed(),
				GlobalConstants.isViewTrustCertificateBackButtonDisplayed);
		partnerCertificatePage.clickOnIntCACertBreadcumb();
		assertTrue(partnerCertificatePage.isCertificateTrustStoreTitleDisplayed(),
				GlobalConstants.isCertificateTrustStoreTitleDisplayed);

	}
}