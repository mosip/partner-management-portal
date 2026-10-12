package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerAdminPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "DeactivatePartner2Creation" }, groups = { "PartnerDeactivateNavigationTest" })
public class PartnerDeactivateNavigationTest extends BaseClass {

	private DashboardPage dashboardPage;
	private PartnerAdminPage partnerAdminPage;

	@Test(priority = 1, description = "Verify by clicking confirm button in popup and then click browser back and then click forward browser button")
	public void deactivatedPartnerStaysGreyedAfterBrowserBackAndForward() {
		dashboardPage = new DashboardPage(driver);
		partnerAdminPage = new PartnerAdminPage(driver);

		dashboardPage.clickOnPartners();
		filterForSecondDeactivatePartner(true);

		partnerAdminPage.clickOnActionsButton();
		partnerAdminPage.clickOnDeactivateOptionInActionMenu();
		assertTrue(partnerAdminPage.isDeactivatePopupHeaderDisplayed(),
				GlobalConstants.isDeactivatePopupHeaderDisplayed);
		partnerAdminPage.clickOnDeactivatePopupConfirmButton();

		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.DEACTIVATED,
				GlobalConstants.isPartnerStatusDeactivatedAfterConfirm);

		partnerAdminPage.navigateBack();
		assertTrue(dashboardPage.isPartnersDisplayed(), GlobalConstants.isPartnersButtonDisplayed);

		partnerAdminPage.navigateForward();
		assertTrue(partnerAdminPage.isSubTitleListDisplayed(), GlobalConstants.isSubTitleListDisplayed);
		assertTrue(partnerAdminPage.isTitlePartnerDisplayed(), GlobalConstants.isTitlePartnerDisplayed);

		filterForSecondDeactivatePartner(false);

		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.DEACTIVATED,
				GlobalConstants.isPartnerStillDeactivatedAfterBrowserForward);
		assertTrue(partnerAdminPage.isFirstPartnerRowGreyedOut(),
				GlobalConstants.isPartnerStillDeactivatedAfterBrowserForward);
	}

	private void filterForSecondDeactivatePartner(boolean activated) {
		assertTrue(partnerAdminPage.isPartnerListLoaded(), GlobalConstants.isPartnerListLoaded);
		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(GlobalConstants.PARTNER_DEACTIVATE2_USER_ID);
		partnerAdminPage.clickOnStatusFilter();
		if (activated) {
			partnerAdminPage.clickActivatedButton();
		} else {
			partnerAdminPage.clickOnDeActivatedStatusInFilters();
		}
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isPartnerListLoaded(GlobalConstants.PARTNER_DEACTIVATE2_USER_ID),
				GlobalConstants.isFilteredPartnerListLoaded);
	}

}
