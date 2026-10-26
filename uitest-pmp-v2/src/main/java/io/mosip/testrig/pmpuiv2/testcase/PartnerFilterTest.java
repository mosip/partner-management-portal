package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerAdminPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;

@Test(dependsOnGroups = { "PartnerDetailsTest" }, groups = { "PartnerFilterTest" })
public class PartnerFilterTest extends BaseClass {

	private static final int PARTIAL_SEARCH_LENGTH = 5;

	private DashboardPage dashboardPage;
	private PartnerAdminPage partnerAdminPage;

	// Each test method spawns its own browser and login, so the filter scenarios share a
	// single session and reset the panel between them rather than repeating that setup six times.
	@Test(priority = 1, description = "Verify the filter section in List of Partners: dropdown search without Apply Now, "
			+ "partial and case-insensitive text search, invalid values, applied results and the filtered count")
	public void partnerListFilterBehaviour() {
		navigateToPartnerListPage();

		verifyDropdownSelectionHasNoEffectBeforeApplyNow();
		verifyPartialTextSearchReturnsRelatedResults();
		verifyTextSearchIsCaseInsensitive();
		verifyInvalidValuesShowErrorOnlyAfterApplyNow();
		verifyApplyNowDisplaysFilteredResultsInTabularView();
		verifyFilteredResultsCountDisplayedProperly();
	}

	@Test(priority = 2, description = "Verfiy the sort button functionality")
	public void sortButtonFunctionalityForPartnerIdColumn() {
		navigateToPartnerListPage();

		String firstRowBeforeSorting = partnerAdminPage.getFirstRowPartnerId();

		// The list is paginated, so descending page 1 holds different records than ascending
		// page 1 - each page is checked for being sorted in its own right, not against the other.
		partnerAdminPage.clickOnPartnerIdAscendingIcon();
		assertTrue(partnerAdminPage.waitForFirstRowPartnerIdToChangeFrom(firstRowBeforeSorting),
				GlobalConstants.isPartnerListLoaded);

		List<String> ascendingOrder = partnerAdminPage.getPartnerIdColumnValues();
		assertEquals(ascendingOrder, expectedSortOrder(ascendingOrder, false),
				GlobalConstants.isPartnerIdColumnSortedAscending);

		partnerAdminPage.clickOnPartnerIdDescendingIcon();
		assertTrue(partnerAdminPage.waitForFirstRowPartnerIdToChangeFrom(ascendingOrder.get(0)),
				GlobalConstants.isPartnerListLoaded);

		List<String> descendingOrder = partnerAdminPage.getPartnerIdColumnValues();
		assertEquals(descendingOrder, expectedSortOrder(descendingOrder, true),
				GlobalConstants.isPartnerIdColumnSortedDescending);
	}

	// The sort happens in the database, whose collation decides whether case matters: a byte-order
	// collation puts "Automation123" before "abis...", a linguistic one does not. Either is a valid sort,
	// so return the ordering the actual values already follow, falling back to case-insensitive.
	private List<String> expectedSortOrder(List<String> actual, boolean descending) {
		Comparator<String> caseSensitive = descending ? Comparator.<String>reverseOrder() : Comparator.<String>naturalOrder();
		List<String> caseSensitiveOrder = new ArrayList<>(actual);
		caseSensitiveOrder.sort(caseSensitive);
		if (caseSensitiveOrder.equals(actual)) {
			return caseSensitiveOrder;
		}
		List<String> caseInsensitiveOrder = new ArrayList<>(actual);
		caseInsensitiveOrder.sort(descending ? String.CASE_INSENSITIVE_ORDER.reversed() : String.CASE_INSENSITIVE_ORDER);
		return caseInsensitiveOrder;
	}

	private void verifyDropdownSelectionHasNoEffectBeforeApplyNow() {
		LogUtil.step("Scenario: dropdown search results are not displayed without clicking Apply Now");
		int baselineCount = partnerAdminPage.getPartnerRowCount();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.clickOnStatusFilter();
		partnerAdminPage.clickOnDeActivatedStatusInFilters();

		assertEquals(partnerAdminPage.getPartnerRowCount(), baselineCount,
				GlobalConstants.isPartnerListUnchangedBeforeApplyingDropdownFilter);

		partnerAdminPage.clickOnFilterResetButton();
	}

	private void verifyPartialTextSearchReturnsRelatedResults() {
		LogUtil.step("Scenario: entering a set of letters in a text field returns related results");
		// Derived from the live list - partner IDs differ per environment, so nothing is hardcoded.
		String partialPartnerId = partnerAdminPage.getFirstRowPartnerId().substring(0, PARTIAL_SEARCH_LENGTH);

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(partialPartnerId);
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isPartnerListLoaded(partialPartnerId),
				GlobalConstants.isPartialPartnerIdSearchReturnsRelatedResults);

		partnerAdminPage.clickOnFilterResetButton();
	}

	private void verifyTextSearchIsCaseInsensitive() {
		LogUtil.step("Scenario: text search results are not case sensitive");
		String existingPartnerId = partnerAdminPage.getFirstRowPartnerId();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(existingPartnerId.toUpperCase());
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isPartnerListLoaded(existingPartnerId),
				GlobalConstants.isPartnerIdSearchCaseInsensitive);

		partnerAdminPage.clickOnFilterResetButton();
	}

	private void verifyInvalidValuesShowErrorOnlyAfterApplyNow() {
		LogUtil.step("Scenario: invalid values report no results only after Apply Now is clicked");
		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterInvalidPartnerIdFilter(GlobalConstants.INVALID_DATA);
		assertFalse(partnerAdminPage.isNoResultsFoundQuick(),
				GlobalConstants.isNoResultsMessageAbsentBeforeApplyingInvalidFilter);

		partnerAdminPage.clickOnApplyFiltersBtn();
		assertTrue(partnerAdminPage.isNoResultsFoundsDisplayed(), GlobalConstants.isNoResultsFoundsDisplayed);

		partnerAdminPage.clickOnFilterResetButton();
	}

	private void verifyApplyNowDisplaysFilteredResultsInTabularView() {
		LogUtil.step("Scenario: Apply Now displays the filtered results in the tabular view");
		String existingPartnerId = partnerAdminPage.getFirstRowPartnerId();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(existingPartnerId);
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isPartnerListLoaded(existingPartnerId),
				GlobalConstants.isFilteredResultsDisplayedInTabularViewAfterApply);

		partnerAdminPage.clickOnFilterResetButton();
	}

	private void verifyFilteredResultsCountDisplayedProperly() {
		LogUtil.step("Scenario: the total number of filtered results is displayed properly");
		// Filtering to a single partner keeps the total on one page, so the subtitle
		// count and the rendered row count are directly comparable despite pagination.
		String existingPartnerId = partnerAdminPage.getFirstRowPartnerId();
		int unfilteredCount = partnerAdminPage.getFilteredPartnersCountFromSubtitle();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(existingPartnerId);
		partnerAdminPage.clickOnApplyFiltersBtn();
		assertTrue(partnerAdminPage.waitForFilteredCountToChangeFrom(unfilteredCount),
				GlobalConstants.isPartnerListLoaded);

		// Counting via the column reader, which waits for the rows to actually render.
		assertEquals(partnerAdminPage.getFilteredPartnersCountFromSubtitle(),
				partnerAdminPage.getPartnerIdColumnValues().size(),
				GlobalConstants.isFilteredPartnersCountMatchesDisplayedRows);

		partnerAdminPage.clickOnFilterResetButton();
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
