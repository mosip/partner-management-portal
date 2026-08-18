package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerAdminPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerDetailsTest" }, groups = { "PartnerFilterTest" })
public class PartnerFilterTest extends BaseClass {

	private static final int PARTIAL_SEARCH_LENGTH = 5;

	private DashboardPage dashboardPage;
	private PartnerAdminPage partnerAdminPage;

	@Test(priority = 1, description = "Verify the Results for the dropdown search without clicking on apply now")
	public void dropdownSearchResultsNotDisplayedWithoutApplyNow() {
		navigateToPartnerListPage();

		int baselineCount = partnerAdminPage.getPartnerRowCount();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.clickOnStatusFilter();
		partnerAdminPage.clickOnDeActivatedStatusInFilters();

		assertEquals(partnerAdminPage.getPartnerRowCount(), baselineCount,
				GlobalConstants.isPartnerListUnchangedBeforeApplyingDropdownFilter);

		partnerAdminPage.clickOnFilterResetButton();
	}

	@Test(priority = 2, description = "Verify the results entering set of letters for any of the text field")
	public void textFieldPartialSearchReturnsRelatedResults() {
		navigateToPartnerListPage();

		String partialPartnerId = partnerAdminPage.getFirstRowPartnerId().substring(0, PARTIAL_SEARCH_LENGTH);

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(partialPartnerId);
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isPartnerListLoaded(partialPartnerId),
				GlobalConstants.isPartialPartnerIdSearchReturnsRelatedResults);

		partnerAdminPage.clickOnFilterResetButton();
	}

	@Test(priority = 3, description = "Verfiy the results entering the values in lowercase if any of the fields have uppercase included in it")
	public void textFieldSearchIsCaseInsensitive() {
		navigateToPartnerListPage();

		String existingPartnerId = partnerAdminPage.getFirstRowPartnerId();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(existingPartnerId.toUpperCase());
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isPartnerListLoaded(existingPartnerId),
				GlobalConstants.isPartnerIdSearchCaseInsensitive);

		partnerAdminPage.clickOnFilterResetButton();
	}

	@Test(priority = 4, description = "Verfiy the results when entered invalid values")
	public void invalidValuesShowErrorOnlyAfterApplyNow() {
		navigateToPartnerListPage();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterInvalidPartnerIdFilter(GlobalConstants.INVALID_DATA);
		assertFalse(partnerAdminPage.isNoResultsFoundQuick(),
				GlobalConstants.isNoResultsMessageAbsentBeforeApplyingInvalidFilter);

		partnerAdminPage.clickOnApplyFiltersBtn();
		assertTrue(partnerAdminPage.isNoResultsFoundsDisplayed(), GlobalConstants.isNoResultsFoundsDisplayed);

		partnerAdminPage.clickOnFilterResetButton();
	}

	@Test(priority = 5, description = "Verfiy the Results on clicking Apply now after selected Dropdown/textfield search")
	public void applyNowDisplaysFilteredResultsInTabularView() {
		navigateToPartnerListPage();

		String existingPartnerId = partnerAdminPage.getFirstRowPartnerId();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(existingPartnerId);
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isPartnerListLoaded(existingPartnerId),
				GlobalConstants.isFilteredResultsDisplayedInTabularViewAfterApply);

		partnerAdminPage.clickOnFilterResetButton();
	}

	@Test(priority = 6, description = "Verify the total number of filtered results is displayed properly")
	public void filteredResultsCountDisplayedProperly() {
		navigateToPartnerListPage();

		String existingPartnerId = partnerAdminPage.getFirstRowPartnerId();
		int unfilteredCount = partnerAdminPage.getFilteredPartnersCountFromSubtitle();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(existingPartnerId);
		partnerAdminPage.clickOnApplyFiltersBtn();
		assertTrue(partnerAdminPage.waitForFilteredCountToChangeFrom(unfilteredCount),
				GlobalConstants.isPartnerListLoaded);

		assertEquals(partnerAdminPage.getFilteredPartnersCountFromSubtitle(),
				partnerAdminPage.getPartnerIdColumnValues().size(),
				GlobalConstants.isFilteredPartnersCountMatchesDisplayedRows);

		partnerAdminPage.clickOnFilterResetButton();
	}

	@Test(priority = 7, description = "Verfiy the sort button functionality")
	public void sortButtonFunctionalityForPartnerIdColumn() {
		navigateToPartnerListPage();

		String firstRowBeforeSorting = partnerAdminPage.getFirstRowPartnerId();

		partnerAdminPage.clickOnPartnerIdAscendingIcon();
		assertTrue(partnerAdminPage.waitForFirstRowPartnerIdToChangeFrom(firstRowBeforeSorting),
				GlobalConstants.isPartnerListLoaded);

		List<String> ascendingOrder = partnerAdminPage.getPartnerIdColumnValues();
		List<String> expectedAscendingOrder = new ArrayList<>(ascendingOrder);
		expectedAscendingOrder.sort(String.CASE_INSENSITIVE_ORDER);
		assertEquals(ascendingOrder, expectedAscendingOrder, GlobalConstants.isPartnerIdColumnSortedAscending);

		partnerAdminPage.clickOnPartnerIdDescendingIcon();
		assertTrue(partnerAdminPage.waitForFirstRowPartnerIdToChangeFrom(ascendingOrder.get(0)),
				GlobalConstants.isPartnerListLoaded);

		List<String> descendingOrder = partnerAdminPage.getPartnerIdColumnValues();
		List<String> expectedDescendingOrder = new ArrayList<>(descendingOrder);
		expectedDescendingOrder.sort(String.CASE_INSENSITIVE_ORDER.reversed());
		assertEquals(descendingOrder, expectedDescendingOrder, GlobalConstants.isPartnerIdColumnSortedDescending);
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
