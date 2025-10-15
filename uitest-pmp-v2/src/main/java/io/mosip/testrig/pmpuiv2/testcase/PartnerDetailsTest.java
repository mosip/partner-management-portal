package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;

import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;

import io.mosip.testrig.pmpuiv2.pages.partnersAdmin;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class PartnerDetailsTest extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private BasePage basePage;
	private partnersAdmin partnerAdmin;

//	@Test(priority = 1, description = "verifying the partner details in tabular form")
//	public void partnerDetailsVerification() {
//		dashboardPage = new DashboardPage(driver);
//		basePage = new BasePage(driver);
//		loginPage = new LoginPage(driver);
//		partnerAdmin = new partnersAdmin(driver);
//
//		assertTrue(dashboardPage.isPartnersDisplayed(), GlobalConstants.isPartnersButtonDisplayed);
//		dashboardPage.clickOnPartners();
//		assertTrue(partnerAdmin.isSubTitleListDisplayed(), GlobalConstants.isSubTitleListDisplayed);
//		assertTrue(partnerAdmin.isTitlePartnerDisplayed(), GlobalConstants.isTitlePartnerDisplayed);
//
//		assertTrue(partnerAdmin.isPartnerIdHeaderTagDisplayed(), GlobalConstants.isPartnerIdHeaderTagDisplayed);
//		assertTrue(partnerAdmin.isPartnerTypeHeaderTagDisplayed(), GlobalConstants.isPartnerTypeHeaderTagDisplayed);
//		assertTrue(partnerAdmin.isOrganisationNameHeaderTagDisplayed(),
//				GlobalConstants.isOrganisationNameHeaderTagDisplayed);
//		assertTrue(partnerAdmin.isPolicyGroupHeaderTagDisplayed(), GlobalConstants.isPolicyGroupHeaderTagDisplayed);
//		assertTrue(partnerAdmin.isEmailAddressHeaderTagDisplayed(), GlobalConstants.isEmailAddressHeaderTagDisplayed);
//		assertTrue(partnerAdmin.isStatusHeaderTagDisplayed(), GlobalConstants.isStatusHeaderTagDisplayed);
//		assertTrue(partnerAdmin.isActionHeaderTagDisplayed(), GlobalConstants.isActionHeaderTagDisplayed);
//		assertTrue(partnerAdmin.isPartnersIdDescIconDisplayed(), GlobalConstants.isPartnersIdDescIconDisplayed);
//		assertTrue(partnerAdmin.isPartnersIdAscIconDisplayed(), GlobalConstants.isPartnersIdAscIconDisplayed);
//		assertTrue(partnerAdmin.isPolicyGroupNamesDescIconDisplayed(),
//				GlobalConstants.isPolicyGroupNamesDescIconDisplayed);
//		assertTrue(partnerAdmin.isPolicyGroupNamesAscIconDisplayed(),
//				GlobalConstants.isPolicyGroupNamesAscIconDisplayed);
//		assertTrue(partnerAdmin.isOrganizationAscIconDisplayed(), GlobalConstants.isOrganizationAscIconDisplayed);
//		assertTrue(partnerAdmin.isOrganizationDescIconDisplayed(), GlobalConstants.isOrganizationDescIconDisplayed);
//		assertTrue(partnerAdmin.isCertificatesUploadStatusDescIconDisplayed(),
//				GlobalConstants.isCertificatesUploadStatusDescIconDisplayed);
//		assertTrue(partnerAdmin.isCertificateUploadsStatusAscIconDisplayed(),
//				GlobalConstants.isCertificateUploadsStatusAscIconDisplayed);
//		assertTrue(partnerAdmin.isFilterButtonsDisplayed(), GlobalConstants.isFilterButtonsDisplayed);
//
//		partnerAdmin.clickOnrowInPartnerDetailsScreen();
//		assertTrue(partnerAdmin.isUserNavigatedToPartnerDetailsPage(),
//				GlobalConstants.isUserNavigatedToPartnerDetailsPage);
//		partnerAdmin.clickOngobackButtonInPartnerDetailsPage();
//
//		partnerAdmin.clickOnFilterButton();
//		assertTrue(partnerAdmin.isPartnersIdFilterDisplayed(), GlobalConstants.isPartnersIdFilterDisplayed);
//		assertTrue(partnerAdmin.isPartnersTypeFilterDisplayed(), GlobalConstants.isPartnerTypeFilterDisplayed);
//		assertTrue(partnerAdmin.isOrganisationFilterDisplayed(), GlobalConstants.isOrganisationFilterDisplayed);
//		assertTrue(partnerAdmin.isEmailsAddressFilterDisplayed(), GlobalConstants.isEmailsAddressFilterDisplayed);
//		assertTrue(partnerAdmin.isCertUploadsStatusFilterDisplayed(),
//				GlobalConstants.isCertUploadsStatusFilterDisplayed);
//		assertTrue(partnerAdmin.isStatusFiltersDisplayed(), GlobalConstants.isStatusFiltersDisplayed);
//		assertTrue(partnerAdmin.isPolicyGroupsFilterDisplayed(), GlobalConstants.isPolicyGroupsFilterDisplayed);
//		partnerAdmin.clickOnFilterResetButton();
//		assertTrue(partnerAdmin.isTabularFieldDisplayed(), GlobalConstants.isTabularFieldDisplayed);
//
//		partnerAdmin.clickOnFilterButton();
//		partnerAdmin.clickOnPartnerTypeDropdown();
//		partnerAdmin.clickOnAuthenticationPartner();
//		partnerAdmin.clickOnApplyFiltersBtn();
//		assertTrue(partnerAdmin.isAuthenticationPartnerCellDisplayed(),
//				GlobalConstants.isauthenticationPartnerCellDisplayed);
//		assertTrue(partnerAdmin.isPartnerIdSearchBarDisplayed(), GlobalConstants.isPartnerIdSearchBarDisplayed);
//		assertTrue(partnerAdmin.isOrganisationSearchBarDisplayed(), GlobalConstants.isOrganisationSearchBarDisplayed);
//		assertTrue(partnerAdmin.isPolicyGroupSearchBarDisplayed(), GlobalConstants.isPolicyGroupSearchBarDisplayed);
//		assertTrue(partnerAdmin.isEmailAddressSearchBarDisplayed(), GlobalConstants.isEmailAddressSearchBarDisplayed);
//		partnerAdmin.clickOnFilterResetButton();
//
//		partnerAdmin.clickOnFilterButton();
//		assertTrue(partnerAdmin.isStatusFiltersDisplayed(), GlobalConstants.isDropDownBoxDisplayed);
//		partnerAdmin.clickOnStatusFilter();
//		partnerAdmin.clickOnDeActivatedStatusInFilters();
//		partnerAdmin.clickOnApplyFiltersBtn();
//		assertTrue(partnerAdmin.isFiltersButtonDisabled(), GlobalConstants.isFiltersButtonDisabled);
//		partnerAdmin.enterPartnerIdsFilter(GlobalConstants.AUTH_PARTNER_ID);
//		partnerAdmin.clickOnStatusFilter();
//		partnerAdmin.clickActivatedButton();
//		partnerAdmin.clickOnApplyFiltersBtn();
//		assertTrue(partnerAdmin.isActivatedPartnersDisplayed(), GlobalConstants.isActivatedPartnersDisplayed);
//		partnerAdmin.clickOnActivatedPartner();
//		assertTrue(partnerAdmin.isViewPartnersDetailsPageDisplayed(),
//				GlobalConstants.isViewPartnersDetailsPageDisplayed);
//		partnerAdmin.clickOnlistOfPartners();
//		assertTrue(partnerAdmin.isSubTitleListDisplayed(), GlobalConstants.isSubTitleListDisplayed);
//		partnerAdmin.clickOnActionsButton();
//		assertTrue(partnerAdmin.isViewButtonsDisplayed(), GlobalConstants.isViewButtonsDisplayed);
//		assertTrue(partnerAdmin.isDeactivateButtonsDisplayed(), GlobalConstants.isDeactivateButtonsDisplayed);
//
//		assertTrue(partnerAdmin.isPartnersIdDescIconDisplayed(), GlobalConstants.isPartnersIdDescIconDisplayed);
//		assertTrue(partnerAdmin.isPartnersIdAscIconDisplayed(), GlobalConstants.isPartnersIdAscIconDisplayed);
//		assertTrue(partnerAdmin.isPolicyGroupNamesDescIconDisplayed(),
//				GlobalConstants.isPolicyGroupNamesDescIconDisplayed);
//		assertTrue(partnerAdmin.isPolicyGroupNamesAscIconDisplayed(),
//				GlobalConstants.isPolicyGroupNamesAscIconDisplayed);
//		assertTrue(partnerAdmin.isOrganizationAscIconDisplayed(), GlobalConstants.isOrganizationAscIconDisplayed);
//		assertTrue(partnerAdmin.isOrganizationDescIconDisplayed(), GlobalConstants.isOrganizationDescIconDisplayed);
//		assertTrue(partnerAdmin.isCertificatesUploadStatusDescIconDisplayed(),
//				GlobalConstants.isCertificatesUploadStatusDescIconDisplayed);
//		assertTrue(partnerAdmin.isCertificateUploadsStatusAscIconDisplayed(),
//				GlobalConstants.isCertificateUploadsStatusAscIconDisplayed);
//
//		partnerAdmin.clickOnFilterButton();
//		partnerAdmin.clickOnStatusFilter();
//		partnerAdmin.clickOnDeActivatedStatusInFilters();
//		partnerAdmin.clickOnApplyFiltersBtn();
//		assertTrue(partnerAdmin.isDeactivatedPartnerRowDisplayed(), GlobalConstants.isDeactivatedPartnerRowDisplayed);
//		partnerAdmin.clickOnActionsButton();
//		assertTrue(partnerAdmin.isViewButtonsDisplayed(), GlobalConstants.isViewButtonsDisplayed);
//
//		partnerAdmin.clickOnFilterResetButton();
//		partnerAdmin.clickOnFilterButton();
//		partnerAdmin.clickOnPartnerTypeDropdown();
//		partnerAdmin.clickOnAuthenticationPartner();
//		partnerAdmin.clickOnApplyFiltersBtn();
//
//		partnerAdmin.enterInvalidPartnerIdFilter(GlobalConstants.INVALID_DATA);
//		partnerAdmin.enterInvalidOrganisationNameFilter(GlobalConstants.INVALID_DATA);
//		partnerAdmin.enterInvalidPolicyGroupFilter(GlobalConstants.INVALID_DATA);
//		partnerAdmin.enterInvalidEmailFilter(GlobalConstants.INVALID_DATA);
//		partnerAdmin.clickOnApplyFiltersBtn();
//		assertTrue(partnerAdmin.isNoResultsFoundsDisplayed(), GlobalConstants.isNoResultsFoundsDisplayed);
//		partnerAdmin.clickOnFilterResetButton();
//		partnerAdmin.clickOnFilterButton();
//		assertTrue(partnerAdmin.isFiltersButtonDisabled(), GlobalConstants.isFiltersButtonDisabled);
//		partnerAdmin.clickOnFilterResetButton();
//		assertTrue(partnerAdmin.isSubTitleOfTabularViewsDisplayed(), GlobalConstants.isSubTitleOfTabularViewsDisplayed);
//		assertTrue(partnerAdmin.isPrefixOfPagesDisplayed(), GlobalConstants.isPrefixOfPagesDisplayed);
//		partnerAdmin.clickOnBreadcrumb();
//		assertTrue(partnerAdmin.isPartnersButtonDisplayed(), GlobalConstants.isPartnersButtonDisplayed);
//
//	}
//
//	@Test(priority = 2, description = "view individual partner details in tabular form")
//	public void partnerIndividualDetailsVerification() {
//		dashboardPage = new DashboardPage(driver);
//		basePage = new BasePage(driver);
//		loginPage = new LoginPage(driver);
//		partnerAdmin = new partnersAdmin(driver);
//
//		assertTrue(partnerAdmin.isPartnersButtonDisplayed(), GlobalConstants.isPartnersButtonDisplayed);
//		partnerAdmin.clickOnPartnersTab();
//		assertTrue(partnerAdmin.isSubTitleOfTabularViewsDisplayed(), GlobalConstants.isSubTitleOfTabularViewsDisplayed);
//		assertTrue(partnerAdmin.isBreadcrumbsDisplayed(), GlobalConstants.isBreadcrumbsDisplayed);
//		assertTrue(partnerAdmin.isTitlePartnerDisplayed(), GlobalConstants.isTitlePartnerDisplayed);
//		assertTrue(partnerAdmin.isOrganisationNameHeaderTagDisplayed(),
//				GlobalConstants.isOrganisationNameHeaderTagDisplayed);
//		assertTrue(partnerAdmin.isPolicyGroupHeaderTagDisplayed(), GlobalConstants.isPolicyGroupHeaderTagDisplayed);
//		assertTrue(partnerAdmin.isEmailAddressHeaderTagDisplayed(), GlobalConstants.isEmailAddressHeaderTagDisplayed);
//		assertTrue(partnerAdmin.isBackButtonAccessible(), GlobalConstants.isBackButtonAccessible);
//		assertTrue(partnerAdmin.isMosipIconsDisplayed(), GlobalConstants.isMosipIconsDisplayed);
//		assertTrue(partnerAdmin.isHamburgersIconDisplayed(), GlobalConstants.isHamburgersIconDisplayed);
//		assertTrue(partnerAdmin.isFooterMosipTextsDisplayed(), GlobalConstants.isFooterMosipTextsDisplayed);
//		assertTrue(partnerAdmin.isFooterDocumentationsDisplayed(), GlobalConstants.isFooterDocumentationsDisplayed);
//		assertTrue(partnerAdmin.isFootersContactUsDisplayed(), GlobalConstants.isFootersContactUsDisplayed);
//		partnerAdmin.clickOnActionsButton();
//		assertTrue(partnerAdmin.isViewButtonsDisplayed(), GlobalConstants.isViewButtonsDisplayed);
//		partnerAdmin.clickOnViewPartnerDetailsScreen();
//		assertTrue(partnerAdmin.isViewPartnersDetailsPageDisplayed(),
//				GlobalConstants.isViewPartnersDetailsPageDisplayed);
//		assertTrue(partnerAdmin.isBreadcrumbsDisplayed(), GlobalConstants.isBreadcrumbsDisplayed);
//		assertTrue(partnerAdmin.isPartnerIdDisplayed(), GlobalConstants.isPartnerIdDisplayed);
//		assertTrue(partnerAdmin.isPartnerStatusInViewPartnerPageDisplayed(),
//				GlobalConstants.isPartnerStatusInViewPartnerPageDisplayed);
//		assertTrue(partnerAdmin.isPartnerCreatedDateInViewPartnerPageDisplayed(),
//				GlobalConstants.isPartnerCreatedDateInViewPartnerPageDisplayed);
//		assertTrue(partnerAdmin.isPartnerCertificateDisplayed(), GlobalConstants.isPartnerCertificateDisplayed);
//		assertTrue(partnerAdmin.isPartnerTypeInViewPartnerPageDisplayed(),
//				GlobalConstants.isPartnerTypeInViewPartnerPageDisplayed);
//		assertTrue(partnerAdmin.isDeviceProviderInViewPartnerPageDisplayed(),
//				GlobalConstants.isDeviceProviderInViewPartnerPageDisplayed);
//		assertTrue(partnerAdmin.isExpiryDateTimeDisplayed(), GlobalConstants.isExpiryDateTimeDisplayed);
//		assertTrue(partnerAdmin.isTimeOfUploadDisplayed(), GlobalConstants.isTimeOfUploadDisplayed);
//		assertTrue(partnerAdmin.isDownloadCertificateButtonDisplayed(),
//				GlobalConstants.isDownloadCertificateButtonDisplayed);
//		partnerAdmin.clickOnDownloadCertificateButtonInViewPartnerPage();
//		assertTrue(partnerAdmin.isOriginalCertificateDropdownDisplayed(),
//				GlobalConstants.isOriginalCertificateDropdownDisplayed);
//		assertTrue(partnerAdmin.isMosipSignedCertificateDropdownDisplayed(),
//				GlobalConstants.isMosipSignedCertificateDropdownDisplayed);
//		partnerAdmin.clickOnOriginnalCertificateInViewPartnerPage();
//		assertTrue(partnerAdmin.isSuccessMassageInOriginalCertificateDisplayed(),
//				GlobalConstants.isSuccessMassageInOriginalCertificateDisplayed);
//		partnerAdmin.clickOnMosipSignedCertificateInViewPartnerPage();
//		assertTrue(partnerAdmin.isSuccessMassageInMosipSignedCertificateDisplayed(),
//				GlobalConstants.isSuccessMassageInMosipSignedCertificateDisplayed);
//		assertTrue(partnerAdmin.isGobackButtonInViewPatnerPageDisplayed(),
//				GlobalConstants.isGobackButtonInViewPatnerPageDisplayed);
//		partnerAdmin.clickOngobackButtonInPartnerDetailsPage();
//		assertTrue(partnerAdmin.isSubTitleListDisplayed(), GlobalConstants.isSubTitleListDisplayed);
//		partnerAdmin.clickOnAuthId();
//		partnerAdmin.clickOnViewPartnerBackButton();
//		assertTrue(partnerAdmin.isNoneditableInListOfPartnerDisplayed(),
//				GlobalConstants.isNoneditableInListOfPartnerDisplayed);
//		partnerAdmin.clickOnActionsButton();
//		partnerAdmin.clickOnDeactivateButton();
//		assertTrue(partnerAdmin.isDeactivatePartnerHeaderDisplayed(),
//				GlobalConstants.isDeactivatePartnerHeaderDisplayed);
//		assertTrue(partnerAdmin.isDeactivatePartnerDescriptionDisplayed(),
//				GlobalConstants.isDeactivatePartnerDescriptionDisplayed);
//		assertTrue(partnerAdmin.isDeactivateCancelButtonDisplayed(), GlobalConstants.isDeactivateCancelButtonDisplayed);
//		assertTrue(partnerAdmin.isDeactivateConfirmButtonDisplayed(),
//				GlobalConstants.isDeactivateConfirmButtonDisplayed);
//		partnerAdmin.clickOnConfirmButton();
//		assertTrue(partnerAdmin.isDeactivateColorCodeButtonDisplayed(),
//				GlobalConstants.isDeactivateColorCodeButtonDisplayed);
//		assertTrue(partnerAdmin.isCertificateUploadStatusHeaderTagDisplayed(),
//				GlobalConstants.isCertificateUploadStatusHeaderTagDisplayed);
//
//		partnerAdmin.clickOnActionsButton();
//		partnerAdmin.clickOnViewButtonInListOfPartnerDetailsScreen();
//		assertTrue(partnerAdmin.isViewPartnersDetailsPageDisplayed(),
//				GlobalConstants.isViewPartnersDetailsPageDisplayed);
//
//		assertTrue(partnerAdmin.isDisabledDownloadCertificateButtonDisplayed(),
//				GlobalConstants.isDisabledDownloadCertificateButtonDisplayed);
//
//	}

	@Test(priority = 3, description = "tabular view of policy request")
	public void deactivatePartnerDetails() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		loginPage = new LoginPage(driver);
		partnerAdmin = new partnersAdmin(driver);

		assertTrue(partnerAdmin.isPolicyButtonsDisplayed(), GlobalConstants.isPolicyButtonsDisplayed);
		partnerAdmin.clickOnPolicyButton();
		assertTrue(partnerAdmin.isrequestPolicyBtnDisplayed(), GlobalConstants.isrequestPolicyBtnDisplayed);
		partnerAdmin.clickOnRequestPolicyBtn();
		assertTrue(partnerAdmin.isPartnerIdDropdownInRequestPolicyPageDisplayed(),
				GlobalConstants.isPartnerIdDropdownInRequestPolicyPageDisplayed);
		partnerAdmin.clickOnPartnerIdDropdownInRequestPolicyPage();
		assertTrue(partnerAdmin.isNoDataAvailabelsDisplayed(), GlobalConstants.isNoDataAvailabeslDisplayed);
		partnerAdmin.clickOnPolicesButtonInRequestPolicy();
		partnerAdmin.enterPartnerIdInRequestPolicy(GlobalConstants.Policy_ID);
		partnerAdmin.enterTestPolicyInRequestPolicyScreen(GlobalConstants.Policy_Name);
		partnerAdmin.enterCommentBoxInRequestPolicy(GlobalConstants.Comments);
		assertTrue(partnerAdmin.isCancelButtonInRequestPolicyScreenDisplayed(),
				GlobalConstants.isCancelButtonInRequestPolicyScreenDisplayed);
		assertTrue(partnerAdmin.isSubmitButtonoInRequestPolicyScreenDisplayed(),
				GlobalConstants.isSubmitButtonoInRequestPolicyScreenDisplayed);
		assertTrue(partnerAdmin.isTtlepartnerInRequestPolicyDisplayed(),
				GlobalConstants.isTtlepartnerInRequestPolicyDisplayed);
		assertTrue(partnerAdmin.isPolicyButtonInRequestPolicyScreenDisplayed(),
				GlobalConstants.isPolicyButtonInRequestPolicyScreenDisplayed);
		partnerAdmin.clickOnPolicesButtonInRequestPolicy();
		assertTrue(partnerAdmin.isListOfPolicyRequestTiteDisplayed(),
				GlobalConstants.isListOfPolicyRequestTiteDisplayed);
		assertTrue(partnerAdmin.isFilterButtonInListOfPolicyRequestScreenDisplayed(),
				GlobalConstants.isFilterButtonInListOfPolicyRequestScreenDisplayed);
		partnerAdmin.clickOnFilterButtonInListOfPolicyRequestScreen();
		assertTrue(partnerAdmin.isPartnerIdInRequestPartnerScreenDisplayed(),
				GlobalConstants.isPartnerIdInRequestPartnerScreenDisplayed);
		assertTrue(partnerAdmin.isPartnerTypeInListOfPartnerScreenDisplayed(),
				GlobalConstants.isFilterButtonInListOfPolicyRequestScreenDisplayed);
		assertTrue(partnerAdmin.isPolicyNameInListOfPolicyScrreenDisplayed(),
				GlobalConstants.isPolicyNameInListOfPolicyScrreenDisplayed);
		assertTrue(partnerAdmin.isStatusInListOfPartnerScreenDisplayed(),
				GlobalConstants.isStatusInListOfPartnerScreenDisplayed);

	}

}
