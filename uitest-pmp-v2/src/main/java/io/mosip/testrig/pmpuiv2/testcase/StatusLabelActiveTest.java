package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerAdminPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;

@Test(dependsOnGroups = { "PartnerDetailsTest", "AuthPartnerCreation" }, groups = { "StatusLabelActiveTest" })
public class StatusLabelActiveTest extends BaseClass {

	private DashboardPage dashboardPage;
	private PartnerAdminPage partnerAdminPage;
	private PoliciesPage policiesPage;
	private LoginPage loginPage;

	@Test(priority = 1, description = "Verify Status updated from Activated to Active for Partner Admins")
	public void partnerAdminStatusLabelReadsActive() {
		navigateToPartnerListPage();

		List<String> statuses = partnerAdminPage.getStatusColumnValues();
		LogUtil.step("Partner list statuses: " + statuses);
		assertTrue(statuses.contains(GlobalConstants.PARTNER_STATUS_ACTIVE),
				GlobalConstants.isActiveStatusLabelDisplayed);
		assertNoActivatedLabel();

		// The status also appears on the details screen, so it is checked there too.
		partnerAdminPage.clickOnActivatedPartner();
		assertTrue(partnerAdminPage.isViewPartnersDetailsPageDisplayed(),
				GlobalConstants.isViewPartnersDetailsPageDisplayed);
		assertNoActivatedLabel();
	}

	@Test(priority = 2, description = "Verify Status updated from Activated to Active for Auth Partners")
	public void authPartnerStatusLabelReadsActive() {
		dashboardPage = new DashboardPage(driver);
		partnerAdminPage = new PartnerAdminPage(driver);

		loginAs(GlobalConstants.AUTH_PARTNER_ID);

		policiesPage = dashboardPage.clickOnPoliciesTitle();
		assertNoActivatedLabel();

		returnToDashboard();
		dashboardPage.clickOnAuthenticationServicesTitle();
		assertNoActivatedLabel();
	}

	@Test(priority = 3, description = "Verify Status updated from Activated to Active for MISP Partners")
	public void mispPartnerStatusLabelReadsActive() {
		navigateToPartnerListPage();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.clickOnPartnerTypeDropdown();
		partnerAdminPage.selectPartnerTypeFilterOption(GlobalConstants.MISP_PARTNER);
		partnerAdminPage.clickOnApplyFiltersBtn();

		List<String> statuses = partnerAdminPage.getStatusColumnValues();
		LogUtil.step("MISP partner statuses: " + statuses);
		assertTrue(statuses.contains(GlobalConstants.PARTNER_STATUS_ACTIVE),
				GlobalConstants.isActiveStatusLabelDisplayed);
		assertNoActivatedLabel();
	}

	@Test(priority = 4, description = "Verify Status updated from Activated to Active for Policy Managers")
	public void policyManagerStatusLabelReadsActive() {
		dashboardPage = new DashboardPage(driver);
		partnerAdminPage = new PartnerAdminPage(driver);

		policiesPage = dashboardPage.clickOnPoliciesTitle();
		assertNoActivatedLabel();

		returnToDashboard();
		dashboardPage.clickOnPartnerPolicyMappingTab();
		assertNoActivatedLabel();
	}

	@Test(priority = 5, description = "Verify Status updated from activated to active in all search dropdown")
	public void statusSearchDropdownOffersActive() {
		navigateToPartnerListPage();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.clickOnStatusFilter();

		assertEquals(partnerAdminPage.getStatusFilterOptionText(1), GlobalConstants.PARTNER_STATUS_ACTIVE,
				GlobalConstants.isActiveOptionInStatusDropdown);
		assertNoActivatedLabel();
	}

	// Exact-text matching, so a legitimate 'Deactivated' badge is never counted here.
	private void assertNoActivatedLabel() {
		assertEquals(partnerAdminPage.countElementsWithExactText(GlobalConstants.ACTIVATED_STATUS_LABEL), 0,
				GlobalConstants.isActivatedStatusLabelAbsent);
	}

	// The dashboard cards are only reachable from the dashboard itself, so each card
	// visit starts from a fresh load rather than from whatever page the last one opened.
	private void returnToDashboard() {
		driver.get(envPathPmpUiv2);
	}

	private void loginAs(String userName) {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.login(userName, GlobalConstants.PARTNER_PASSWORD);
	}

	private void navigateToPartnerListPage() {
		dashboardPage = new DashboardPage(driver);
		partnerAdminPage = new PartnerAdminPage(driver);

		assertTrue(dashboardPage.isPartnersDisplayed(), GlobalConstants.isPartnersButtonDisplayed);
		dashboardPage.clickOnPartners();
		assertTrue(partnerAdminPage.isSubTitleListDisplayed(), GlobalConstants.isSubTitleListDisplayed);
		assertTrue(partnerAdminPage.isPartnerListLoaded(), GlobalConstants.isPartnerListLoaded);
	}

}
