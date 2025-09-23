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

	@Test(priority = 1, description = "verifying the partner details in tabular form")
	public void partnerDetailsVerification() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		loginPage = new LoginPage(driver);
		partnerAdmin = new partnersAdmin(driver);

		assertTrue(dashboardPage.isPartnersDisplayed(), GlobalConstants.isPartnersButtonDisplayed);
		dashboardPage.clickOnPartners();
		assertTrue(partnerAdmin.isSubTitleListDisplayed(), GlobalConstants.isSubTitleListDisplayed);
		assertTrue(partnerAdmin.isTitlePartnerDisplayed(), GlobalConstants.isTitlePartnerDisplayed);

		assertTrue(partnerAdmin.isPartnerIdHeaderTagDisplayed(), GlobalConstants.isPartnerIdHeaderTagDisplayed);
		assertTrue(partnerAdmin.isPartnerTypeHeaderTagDisplayed(), GlobalConstants.isPartnerTypeHeaderTagDisplayed);
		assertTrue(partnerAdmin.isOrganisationNameHeaderTagDisplayed(),
				GlobalConstants.isOrganisationNameHeaderTagDisplayed);
		assertTrue(partnerAdmin.isPolicyGroupHeaderTagDisplayed(), GlobalConstants.isPolicyGroupHeaderTagDisplayed);
		assertTrue(partnerAdmin.isEmailAddressHeaderTagDisplayed(), GlobalConstants.isEmailAddressHeaderTagDisplayed);
		assertTrue(partnerAdmin.isStatusHeaderTagDisplayed(), GlobalConstants.isStatusHeaderTagDisplayed);
		assertTrue(partnerAdmin.isActionHeaderTagDisplayed(), GlobalConstants.isActionHeaderTagDisplayed);
		assertTrue(partnerAdmin.isPartnersIdDescIconDisplayed(), GlobalConstants.isPartnersIdDescIconDisplayed);
		assertTrue(partnerAdmin.isPartnersIdAscIconDisplayed(), GlobalConstants.isPartnersIdAscIconDisplayed);
		assertTrue(partnerAdmin.isPolicyGroupNamesDescIconDisplayed(),
				GlobalConstants.isPolicyGroupNamesDescIconDisplayed);
		assertTrue(partnerAdmin.isPolicyGroupNamesAscIconDisplayed(),
				GlobalConstants.isPolicyGroupNamesAscIconDisplayed);
		assertTrue(partnerAdmin.isOrganizationAscIconDisplayed(), GlobalConstants.isOrganizationAscIconDisplayed);
		assertTrue(partnerAdmin.isOrganizationDescIconDisplayed(), GlobalConstants.isOrganizationDescIconDisplayed);
		assertTrue(partnerAdmin.isCertificatesUploadStatusDescIconDisplayed(),
				GlobalConstants.isCertificatesUploadStatusDescIconDisplayed);
		assertTrue(partnerAdmin.isCertificateUploadsStatusAscIconDisplayed(),
				GlobalConstants.isCertificateUploadsStatusAscIconDisplayed);
		assertTrue(partnerAdmin.isFilterButtonsDisplayed(), GlobalConstants.isFilterButtonsDisplayed);

		partnerAdmin.clickOnrowInPartnerDetailsScreen();
		assertTrue(partnerAdmin.isUserNavigatedToPartnerDetailsPage(),
				GlobalConstants.isUserNavigatedToPartnerDetailsPage);
		partnerAdmin.clickOngobackButtonInPartnerDetailsPage();

		partnerAdmin.clickOnFilterButton();
		assertTrue(partnerAdmin.isPartnersIdFilterDisplayed(), GlobalConstants.isPartnersIdFilterDisplayed);
		assertTrue(partnerAdmin.isPartnersTypeFilterDisplayed(), GlobalConstants.isPartnerTypeFilterDisplayed);
		assertTrue(partnerAdmin.isOrganisationFilterDisplayed(), GlobalConstants.isOrganisationFilterDisplayed);
		assertTrue(partnerAdmin.isEmailsAddressFilterDisplayed(), GlobalConstants.isEmailsAddressFilterDisplayed);
		assertTrue(partnerAdmin.isCertUploadsStatusFilterDisplayed(),
				GlobalConstants.isCertUploadsStatusFilterDisplayed);
		assertTrue(partnerAdmin.isStatusFiltersDisplayed(), GlobalConstants.isStatusFiltersDisplayed);
		assertTrue(partnerAdmin.isPolicyGroupsFilterDisplayed(), GlobalConstants.isPolicyGroupsFilterDisplayed);
		partnerAdmin.clickOnFilterResetButton();
		assertTrue(partnerAdmin.isTabularFieldDisplayed(), GlobalConstants.isTabularFieldDisplayed);

		partnerAdmin.clickOnFilterButton();
		partnerAdmin.clickOnPartnerTypeDropdown();
		partnerAdmin.clickOnAuthenticationPartner();
		partnerAdmin.clickOnApplyFiltersBtn();
		assertTrue(partnerAdmin.isAuthenticationPartnerCellDisplayed(),
				GlobalConstants.isauthenticationPartnerCellDisplayed);
		assertTrue(partnerAdmin.isPartnerIdSearchBar(), GlobalConstants.isPartnerIdSearchBar);
		assertTrue(partnerAdmin.isOrganisationSearchBar(), GlobalConstants.isOrganisationSearchBar);
		assertTrue(partnerAdmin.isPolicyGroupsSearchBar(), GlobalConstants.isPolicyGroupsSearchBar);
		assertTrue(partnerAdmin.isEmailAddressSearchBar(), GlobalConstants.isEmailAddressSearchBar);
		partnerAdmin.clickOnFilterResetButton();

		partnerAdmin.clickOnFilterButton();
		assertTrue(partnerAdmin.isStatusFiltersDisplayed(), GlobalConstants.isDropDownBoxDisplayed);
		partnerAdmin.clickOnStatusFilter();
		partnerAdmin.clickOnDeActivatedStatusInFilters();
		partnerAdmin.clickOnApplyFiltersBtn();
		assertTrue(partnerAdmin.isFiltersButtonDisabled(), GlobalConstants.isFiltersButtonDisabled);
		partnerAdmin.enterPartnerIds("pmpui-auth");
		partnerAdmin.clickOnStatusFilter();
		partnerAdmin.clickActivatedButton();
		partnerAdmin.clickOnApplyFiltersBtn();
		assertTrue(partnerAdmin.isActivatedPartnersDisplayed(), GlobalConstants.isActivatedPartnersDisplayed);
		partnerAdmin.clickOnActivatedPartner();
		assertTrue(partnerAdmin.isViewPartnersDetailsPageDisplayed(),
				GlobalConstants.isViewPartnersDetailsPageDisplayed);
		partnerAdmin.clickOnlistOfPartners();
		assertTrue(partnerAdmin.isSubTitleListDisplayed(), GlobalConstants.isSubTitleListDisplayed);
		partnerAdmin.clickOnActionsButton();
		assertTrue(partnerAdmin.isViewButtonsDisplayed(), GlobalConstants.isViewButtonsDisplayed);
		assertTrue(partnerAdmin.isDeactivateButtonsDisplayed(), GlobalConstants.isDeactivateButtonsDisplayed);

		assertTrue(partnerAdmin.isPartnersIdDescIconDisplayed(), GlobalConstants.isPartnersIdDescIconDisplayed);
		assertTrue(partnerAdmin.isPartnersIdAscIconDisplayed(), GlobalConstants.isPartnersIdAscIconDisplayed);
		assertTrue(partnerAdmin.isPolicyGroupNamesDescIconDisplayed(),
				GlobalConstants.isPolicyGroupNamesDescIconDisplayed);
		assertTrue(partnerAdmin.isPolicyGroupNamesAscIconDisplayed(),
				GlobalConstants.isPolicyGroupNamesAscIconDisplayed);
		assertTrue(partnerAdmin.isOrganizationAscIconDisplayed(), GlobalConstants.isOrganizationAscIconDisplayed);
		assertTrue(partnerAdmin.isOrganizationDescIconDisplayed(), GlobalConstants.isOrganizationDescIconDisplayed);
		assertTrue(partnerAdmin.isCertificatesUploadStatusDescIconDisplayed(),
				GlobalConstants.isCertificatesUploadStatusDescIconDisplayed);
		assertTrue(partnerAdmin.isCertificateUploadsStatusAscIconDisplayed(),
				GlobalConstants.isCertificateUploadsStatusAscIconDisplayed);

		partnerAdmin.clickOnFilterButton();
		partnerAdmin.clickOnStatusFilter();
		partnerAdmin.clickOnDeActivatedStatusInFilters();
		partnerAdmin.clickOnApplyFiltersBtn();
		assertTrue(partnerAdmin.isDeactivatedPartnerRowDisplayed(), GlobalConstants.isDeactivatedPartnerRowDisplayed);
		partnerAdmin.clickOnActionsButton();
		assertTrue(partnerAdmin.isViewButtonsDisplayed(), GlobalConstants.isViewButtonsDisplayed);

		partnerAdmin.clickOnFilterResetButton();
		partnerAdmin.clickOnFilterButton();
		partnerAdmin.clickOnPartnerTypeDropdown();
		partnerAdmin.clickOnAuthenticationPartner();
		partnerAdmin.clickOnApplyFiltersBtn();

		partnerAdmin.enterInvalidPartnerId("123e");
		partnerAdmin.enterInvalidOrganisationName("asd");
		partnerAdmin.enterInvalidPolicyGroup("101e");
		partnerAdmin.enterInvalidEmail("asz@11");
		partnerAdmin.clickOnApplyFiltersBtn();
		assertTrue(partnerAdmin.isNoResultsFoundsDisplayed(), GlobalConstants.isNoResultsFoundsDisplayed);
		partnerAdmin.clickOnFilterResetButton();
		partnerAdmin.clickOnFilterButton();
		assertTrue(partnerAdmin.isFiltersButtonDisabled(), GlobalConstants.isFiltersButtonDisabled);
		partnerAdmin.clickOnFilterResetButton();
		assertTrue(partnerAdmin.isSubTitleOfTabularViewsDisplayed(), GlobalConstants.isSubTitleOfTabularViewsDisplayed);
		assertTrue(partnerAdmin.isPrefixOfPagesDisplayed(), GlobalConstants.isPrefixOfPagesDisplayed);
		partnerAdmin.clickOnBreadcrumb();
		assertTrue(partnerAdmin.isPartnersButtonDisplayed(), GlobalConstants.isPartnersButtonDisplayed);

	}

	
}
