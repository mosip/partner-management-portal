package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerAdminPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;

@Test(dependsOnGroups = { "PartnerDetailsTest" }, groups = { "PartnerEmailFilterTest" })
public class PartnerEmailFilterTest extends BaseClass {

	private DashboardPage dashboardPage;
	private PartnerAdminPage partnerAdminPage;

	// The presentation checks all read the same screen, so they share one session rather than
	// re-launching the browser and logging in again for each individual assertion.
	@Test(priority = 1, description = "Verify the Email Address column and filter presentation: column visibility, "
			+ "absence of sorting, filter placeholder, info icon and its tooltip")
	public void emailAddressColumnAndFilterPresentation() {
		navigateToPartnerListPage();

		LogUtil.step("Scenario: Email Address column is visible in List of Partners");
		assertTrue(partnerAdminPage.isEmailAddressHeaderTagDisplayed(),
				GlobalConstants.isEmailAddressColumnHeaderVisible);

		LogUtil.step("Scenario: Email Address column is not sortable");
		// Confirms the lookup finds icons where they do exist, so an empty email result is meaningful.
		assertTrue(partnerAdminPage.getSortIconCountForColumn(GlobalConstants.PARTNER_ID_COLUMN) > 0,
				GlobalConstants.isSortIconLocatorValidForSortableColumn);
		assertEquals(partnerAdminPage.getSortIconCountForColumn(GlobalConstants.EMAIL_ADDRESS_COLUMN), 0,
				GlobalConstants.isEmailAddressColumnNotSortable);

		partnerAdminPage.clickOnFilterButton();

		LogUtil.step("Scenario: the email textbox placeholder text");
		assertEquals(partnerAdminPage.getEmailAddressFilterPlaceholder(),
				GlobalConstants.EMAIL_ADDRESS_FILTER_PLACEHOLDER,
				GlobalConstants.isEmailAddressFilterPlaceholderCorrect);

		LogUtil.step("Scenario: the info icon beside the email address field");
		assertTrue(partnerAdminPage.isEmailAddressFilterInfoIconDisplayed(),
				GlobalConstants.isEmailAddressFilterInfoIconDisplayed);

		LogUtil.step("Scenario: the info icon tooltip text");
		// The icon carries role="button"/tabindex="0", so the tooltip is toggled by activation, not hover.
		partnerAdminPage.clickOnEmailAddressFilterInfoIcon();
		assertEquals(partnerAdminPage.getEmailAddressFilterInfoTooltipText(),
				GlobalConstants.EMAIL_ADDRESS_FILTER_INFO_TOOLTIP,
				GlobalConstants.isEmailAddressFilterInfoTooltipCorrect);

		partnerAdminPage.clickOnFilterResetButton();
	}

	@Test(priority = 2, description = "Verify the Email Address filter behaviour: exact match, partial and non-existing "
			+ "addresses, a conflicting Partner Type, casing and disallowed special characters")
	public void emailAddressFilterBehaviour() {
		navigateToPartnerListPage();

		verifyExactEmailMatchReturnsOnlyThatRecord();
		verifyPartialEmailReturnsNoResults();
		verifyNonExistingEmailReturnsNoResults();
		verifyCombinedEmailAndMismatchedPartnerTypeReturnsNoResults();
		verifyEmailFilterIsNotCaseSensitive();
		verifyDisallowedSpecialCharactersShowValidationError();
	}

	private void verifyExactEmailMatchReturnsOnlyThatRecord() {
		LogUtil.step("Scenario: exact match filtering using a full email address");
		String existingEmail = partnerAdminPage.getFirstRowEmailAddress();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterEmailAddressInFilter(existingEmail);
		partnerAdminPage.clickOnApplyFiltersBtn();

		// Reading the column straight after Apply races the re-render and goes stale.
		assertTrue(partnerAdminPage.isPartnerListLoadedByEmail(existingEmail), GlobalConstants.isPartnerListLoaded);

		List<String> emailsAfterFilter = partnerAdminPage.getEmailAddressColumnValues();
		assertEquals(emailsAfterFilter, List.of(existingEmail),
				GlobalConstants.isExactEmailMatchReturningOnlyThatRecord);

		partnerAdminPage.clickOnFilterResetButton();
	}

	private void verifyPartialEmailReturnsNoResults() {
		LogUtil.step("Scenario: a partial email address shows no results");
		String partialEmail = partnerAdminPage.getFirstRowEmailAddress().split("@")[0] + "@";

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterEmailAddressInFilter(partialEmail);
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isNoResultsFoundsDisplayed(), GlobalConstants.isPartialEmailReturningNoResults);

		partnerAdminPage.clickOnFilterResetButton();
	}

	private void verifyNonExistingEmailReturnsNoResults() {
		LogUtil.step("Scenario: a non-existing full email address shows no results");
		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterEmailAddressInFilter(GlobalConstants.NON_EXISTING_EMAIL);
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isNoResultsFoundsDisplayed(), GlobalConstants.isNonExistingEmailReturningNoResults);

		partnerAdminPage.clickOnFilterResetButton();
	}

	private void verifyCombinedEmailAndMismatchedPartnerTypeReturnsNoResults() {
		LogUtil.step("Scenario: an email combined with a conflicting Partner Type shows no results");
		// The email must belong to a non-Authentication partner, otherwise the two
		// filters agree and the record legitimately comes back.
		String conflictingEmail = partnerAdminPage
				.getEmailOfFirstRowWithPartnerTypeOtherThan(GlobalConstants.AUTHENTICATION_PARTNER);

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterEmailAddressInFilter(conflictingEmail);
		partnerAdminPage.clickOnPartnerTypeDropdown();
		partnerAdminPage.clickOnAuthenticationPartner();
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isNoResultsFoundsDisplayed(),
				GlobalConstants.isCombinedEmailAndPartnerTypeMismatchReturningNoResults);

		partnerAdminPage.clickOnFilterResetButton();
	}

	private void verifyEmailFilterIsNotCaseSensitive() {
		LogUtil.step("Scenario: the email filter is not case sensitive");
		String existingEmail = partnerAdminPage.getFirstRowEmailAddress();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterEmailAddressInFilter(existingEmail.toUpperCase());
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isPartnerListLoadedByEmail(existingEmail),
				GlobalConstants.isEmailFilterCaseInsensitive);

		partnerAdminPage.clickOnFilterResetButton();
	}

	private void verifyDisallowedSpecialCharactersShowValidationError() {
		LogUtil.step("Scenario: an email with disallowed special characters is rejected");
		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterEmailAddressInFilter(GlobalConstants.EMAIL_WITH_DISALLOWED_SPECIAL_CHARACTERS);

		assertTrue(partnerAdminPage.isInvalidCharacterErrorDisplayed(),
				GlobalConstants.isInvalidCharacterErrorDisplayed);
		LogUtil.step("Invalid character message: " + partnerAdminPage.getInvalidCharacterErrorText());

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
