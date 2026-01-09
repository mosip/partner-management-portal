package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerAdminPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(groups = { "PartnerDetailsTest" })
public class PartnerDetailsTest extends BaseClass {

	private DashboardPage dashboardPage;
	private PartnerAdminPage partnerAdminPage;

	@Test(priority = 1, description = "verifying the partner details in tabular form")
	public void partnerDetailsVerification() {
		dashboardPage = new DashboardPage(driver);
		partnerAdminPage = new PartnerAdminPage(driver);

		assertTrue(dashboardPage.isPartnersDisplayed(), GlobalConstants.isPartnersButtonDisplayed);
		dashboardPage.clickOnPartners();
		assertTrue(partnerAdminPage.isSubTitleListDisplayed(), GlobalConstants.isSubTitleListDisplayed);
		assertTrue(partnerAdminPage.isTitlePartnerDisplayed(), GlobalConstants.isTitlePartnerDisplayed);

		assertTrue(partnerAdminPage.isPartnerIdHeaderTagDisplayed(), GlobalConstants.isPartnerIdHeaderTagDisplayed);
		assertTrue(partnerAdminPage.isPartnerTypeHeaderTagDisplayed(), GlobalConstants.isPartnerTypeHeaderTagDisplayed);
		assertTrue(partnerAdminPage.isOrganisationNameHeaderTagDisplayed(),
				GlobalConstants.isOrganisationNameHeaderTagDisplayed);
		assertTrue(partnerAdminPage.isPolicyGroupHeaderTagDisplayed(), GlobalConstants.isPolicyGroupHeaderTagDisplayed);
		assertTrue(partnerAdminPage.isEmailAddressHeaderTagDisplayed(),
				GlobalConstants.isEmailAddressHeaderTagDisplayed);
		assertTrue(partnerAdminPage.isStatusHeaderTagDisplayed(), GlobalConstants.isStatusHeaderTagDisplayed);
		assertTrue(partnerAdminPage.isActionHeaderTagDisplayed(), GlobalConstants.isActionHeaderTagDisplayed);
		assertTrue(partnerAdminPage.isPartnersIdDescIconDisplayed(), GlobalConstants.isPartnersIdDescIconDisplayed);
		assertTrue(partnerAdminPage.isPartnersIdAscIconDisplayed(), GlobalConstants.isPartnersIdAscIconDisplayed);
		assertTrue(partnerAdminPage.isPolicyGroupNamesDescIconDisplayed(),
				GlobalConstants.isPolicyGroupNamesDescIconDisplayed);
		assertTrue(partnerAdminPage.isPolicyGroupNamesAscIconDisplayed(),
				GlobalConstants.isPolicyGroupNamesAscIconDisplayed);
		assertTrue(partnerAdminPage.isOrganizationAscIconDisplayed(), GlobalConstants.isOrganizationAscIconDisplayed);
		assertTrue(partnerAdminPage.isOrganizationDescIconDisplayed(), GlobalConstants.isOrganizationDescIconDisplayed);
		assertTrue(partnerAdminPage.isCertificatesUploadStatusDescIconDisplayed(),
				GlobalConstants.isCertificatesUploadStatusDescIconDisplayed);
		assertTrue(partnerAdminPage.isCertificateUploadsStatusAscIconDisplayed(),
				GlobalConstants.isCertificateUploadsStatusAscIconDisplayed);
		assertTrue(partnerAdminPage.isFilterButtonsDisplayed(), GlobalConstants.isFilterButtonsDisplayed);

		partnerAdminPage.clickOnFilterButton();
		assertTrue(partnerAdminPage.isPartnersIdFilterDisplayed(), GlobalConstants.isPartnersIdFilterDisplayed);
		assertTrue(partnerAdminPage.isPartnersTypeFilterDisplayed(), GlobalConstants.isPartnerTypeFilterDisplayed);
		assertTrue(partnerAdminPage.isOrganisationFilterDisplayed(), GlobalConstants.isOrganisationFilterDisplayed);
		assertTrue(partnerAdminPage.isEmailsAddressFilterDisplayed(), GlobalConstants.isEmailsAddressFilterDisplayed);
		assertTrue(partnerAdminPage.isCertUploadsStatusFilterDisplayed(),
				GlobalConstants.isCertUploadsStatusFilterDisplayed);
		assertTrue(partnerAdminPage.isStatusFiltersDisplayed(), GlobalConstants.isStatusFiltersDisplayed);
		assertTrue(partnerAdminPage.isPolicyGroupsFilterDisplayed(), GlobalConstants.isPolicyGroupsFilterDisplayed);
		partnerAdminPage.clickOnFilterResetButton();
		assertTrue(partnerAdminPage.isTabularFieldDisplayed(), GlobalConstants.isTabularFieldDisplayed);

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.clickOnPartnerTypeDropdown();
		partnerAdminPage.clickOnAuthenticationPartner();
		partnerAdminPage.clickOnApplyFiltersBtn();
		assertTrue(partnerAdminPage.isAuthenticationPartnerCellDisplayed(),
				GlobalConstants.isauthenticationPartnerCellDisplayed);
		assertTrue(partnerAdminPage.isPartnerIdSearchBarDisplayed(), GlobalConstants.isPartnerIdSearchBarDisplayed);
		assertTrue(partnerAdminPage.isOrganisationSearchBarDisplayed(),
				GlobalConstants.isOrganisationSearchBarDisplayed);
		assertTrue(partnerAdminPage.isPolicyGroupSearchBarDisplayed(), GlobalConstants.isPolicyGroupSearchBarDisplayed);
		assertTrue(partnerAdminPage.isEmailAddressSearchBarDisplayed(),
				GlobalConstants.isEmailAddressSearchBarDisplayed);
		partnerAdminPage.clickOnFilterResetButton();

		partnerAdminPage.clickOnFilterButton();
		assertTrue(partnerAdminPage.isStatusFiltersDisplayed(), GlobalConstants.isDropDownBoxDisplayed);
		partnerAdminPage.clickOnStatusFilter();
		partnerAdminPage.clickOnDeActivatedStatusInFilters();
		partnerAdminPage.clickOnApplyFiltersBtn();
		assertTrue(partnerAdminPage.isFiltersButtonDisabled(), GlobalConstants.isFiltersButtonDisabled);
		partnerAdminPage.clickOnFilterResetButton();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(GlobalConstants.PARTNERDETAILS_USER_ID);
		partnerAdminPage.clickOnStatusFilter();
		partnerAdminPage.clickActivatedButton();
		partnerAdminPage.clickOnApplyFiltersBtn();
		assertTrue(partnerAdminPage.isActivatedPartnersDisplayed(), GlobalConstants.isActivatedPartnersDisplayed);
		partnerAdminPage.clickOnActivatedPartner();
		assertTrue(partnerAdminPage.isViewPartnersDetailsPageDisplayed(),
				GlobalConstants.isViewPartnersDetailsPageDisplayed);
		partnerAdminPage.clickOnlistOfPartners();
		assertTrue(partnerAdminPage.isSubTitleListDisplayed(), GlobalConstants.isSubTitleListDisplayed);
		partnerAdminPage.clickOnActionsButton();
		assertTrue(partnerAdminPage.isViewButtonsDisplayed(), GlobalConstants.isViewButtonsDisplayed);
		assertTrue(partnerAdminPage.isDeactivateButtonsDisplayed(), GlobalConstants.isDeactivateButtonsDisplayed);

		assertTrue(partnerAdminPage.isPartnersIdDescIconDisplayed(), GlobalConstants.isPartnersIdDescIconDisplayed);
		assertTrue(partnerAdminPage.isPartnersIdAscIconDisplayed(), GlobalConstants.isPartnersIdAscIconDisplayed);
		assertTrue(partnerAdminPage.isPolicyGroupNamesDescIconDisplayed(),
				GlobalConstants.isPolicyGroupNamesDescIconDisplayed);
		assertTrue(partnerAdminPage.isPolicyGroupNamesAscIconDisplayed(),
				GlobalConstants.isPolicyGroupNamesAscIconDisplayed);
		assertTrue(partnerAdminPage.isOrganizationAscIconDisplayed(), GlobalConstants.isOrganizationAscIconDisplayed);
		assertTrue(partnerAdminPage.isOrganizationDescIconDisplayed(), GlobalConstants.isOrganizationDescIconDisplayed);
		assertTrue(partnerAdminPage.isCertificatesUploadStatusDescIconDisplayed(),
				GlobalConstants.isCertificatesUploadStatusDescIconDisplayed);
		assertTrue(partnerAdminPage.isCertificateUploadsStatusAscIconDisplayed(),
				GlobalConstants.isCertificateUploadsStatusAscIconDisplayed);

		// Re-enable deactivated partner verification once stability issues are resolved
//		partnerAdminPage.clickOnFilterButton();
//		partnerAdminPage.clickOnStatusFilter();
//		partnerAdminPage.clickOnDeActivatedStatusInFilters();
//		partnerAdminPage.clickOnApplyFiltersBtn();
//		assertTrue(partnerAdminPage.isDeactivatedPartnerRowDisplayed(), GlobalConstants.isDeactivatedPartnerRowDisplayed);
//		partnerAdminPage.clickOnActionsButton();
//		assertTrue(partnerAdminPage.isViewButtonsDisplayed(), GlobalConstants.isViewButtonsDisplayed);

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(GlobalConstants.PARTNERDETAILS_USER_ID);
		partnerAdminPage.enterInvalidOrganisationNameFilter(GlobalConstants.INVALID_DATA);
		partnerAdminPage.enterInvalidPolicyGroupFilter(GlobalConstants.INVALID_DATA);
		partnerAdminPage.enterInvalidEmailFilter(GlobalConstants.INVALID_DATA);
		partnerAdminPage.clickOnApplyFiltersBtn();
		assertTrue(partnerAdminPage.isNoResultsFoundsDisplayed(), GlobalConstants.isNoResultsFoundsDisplayed);
		partnerAdminPage.clickOnFilterResetButton();
		partnerAdminPage.clickOnFilterButton();
		assertTrue(partnerAdminPage.isFiltersButtonDisabled(), GlobalConstants.isFiltersButtonDisabled);
		partnerAdminPage.clickOnFilterResetButton();
		assertTrue(partnerAdminPage.isSubTitleOfTabularViewsDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewsDisplayed);
		assertTrue(partnerAdminPage.isPrefixOfPagesDisplayed(), GlobalConstants.isPrefixOfPagesDisplayed);
		partnerAdminPage.clickOnBreadcrumb();
		assertTrue(partnerAdminPage.isPartnersButtonDisplayed(), GlobalConstants.isPartnersButtonDisplayed);

	}

}
