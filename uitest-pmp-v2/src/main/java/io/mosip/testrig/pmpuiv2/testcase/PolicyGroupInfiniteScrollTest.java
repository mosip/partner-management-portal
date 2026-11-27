package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.pages.PolicyGroupPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "PolicyGroupInfiniteScrollTest" })
public class PolicyGroupInfiniteScrollTest extends BaseClass {

	private static final int FIRST_PAGE_SIZE = 5;
	private static final int POLICY_GROUP_COUNT = 6;

	private DashboardPage dashboardPage;
	private PoliciesPage policiesPage;
	private PolicyGroupPage policyGroupPage;
	private AuthPolicyPage authPolicyPage;

	@Test(priority = 1, description = "MOSIP-41028: Create enough policy groups for the dropdown to page")
	public void createPolicyGroupsForInfiniteScroll() {
		dashboardPage = new DashboardPage(driver);
		policyGroupPage = new PolicyGroupPage(driver);

		dashboardPage.clickOnPolicyButton();
		for (int index = 1; index <= POLICY_GROUP_COUNT; index++) {
			String policyGroupName = policyGroupName(index);
			policyGroupPage.clickOnCreatePolicyGroupButton();
			policyGroupPage.enterPolicyGroupName(policyGroupName);
			policyGroupPage.enterPolicyGroupNameDescription(policyGroupName);
			policyGroupPage.clickOnSubmitButton();
			assertTrue(policyGroupPage.isPolicyGroupSuccessMessageDisplayed(),
					GlobalConstants.isPolicyGroupSuccessMessageDisplayed);
			policyGroupPage.clickOnSuccessGoBackButton();
		}
	}

	@Test(priority = 2, description = "MOSIP-41028: Policy group dropdown fetches the next page when scrolled",
			dependsOnMethods = "createPolicyGroupsForInfiniteScroll")
	public void policyGroupDropdownLoadsNextPageOnScroll() {
		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);

		openPolicyGroupDropdown();
		assertTrue(authPolicyPage.isPolicyGroupDropdownSearchInputDisplayed(),
				GlobalConstants.isPolicyGroupDropdownSearchInputDisplayed);

		int firstPageCount = authPolicyPage.waitForPolicyGroupOptionCount(FIRST_PAGE_SIZE);
		assertEquals(firstPageCount, FIRST_PAGE_SIZE, GlobalConstants.isPolicyGroupDropdownFirstPageLoaded);

		int nextPageCount = authPolicyPage.scrollPolicyGroupList(firstPageCount);
		assertTrue(nextPageCount > FIRST_PAGE_SIZE, GlobalConstants.isPolicyGroupDropdownNextPageLoaded);
	}

	@Test(priority = 3, description = "MOSIP-41028: Policy group search and selection still work",
			dependsOnMethods = "policyGroupDropdownLoadsNextPageOnScroll")
	public void policyGroupDropdownSearchAndSelectStillWorks() {
		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);

		String policyGroupName = policyGroupName(1);
		openPolicyGroupDropdown();
		int firstPageCount = authPolicyPage.waitForPolicyGroupOptionCount(FIRST_PAGE_SIZE);
		int nextPageCount = authPolicyPage.scrollPolicyGroupList(firstPageCount);
		assertTrue(nextPageCount > FIRST_PAGE_SIZE, GlobalConstants.isPolicyGroupDropdownNextPageLoaded);
		int matches = authPolicyPage.searchOpenPolicyGroupDropdown(policyGroupName);
		assertEquals(matches, 1, GlobalConstants.isPolicyGroupSelectedFromSearch);
		authPolicyPage.clickPolicyGroupOption(policyGroupName);
		assertEquals(authPolicyPage.getSelectedPolicyGroupText(), policyGroupName,
				GlobalConstants.isPolicyGroupSelectedFromSearch);
	}

	private void openCreateAuthPolicy() {
		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();
		assertTrue(authPolicyPage.isPolicyGroupDropdownDisplayed(), GlobalConstants.isPolicyGroupDropdownDisplayed);
	}

	private void openPolicyGroupDropdown() {
		openCreateAuthPolicy();
		authPolicyPage.openPolicyGroupDropdown();
	}

	private static String policyGroupName(int index) {
		return String.format("scrollpg%02d", index);
	}
}
