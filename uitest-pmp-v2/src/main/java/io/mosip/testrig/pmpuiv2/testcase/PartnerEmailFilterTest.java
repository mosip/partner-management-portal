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

	@Test(priority = 1, description = "Verify Email Address column is visible in List of Partners")
	public void emailAddressColumnHeaderIsVisible() {
		navigateToPartnerListPage();

		assertTrue(partnerAdminPage.isEmailAddressHeaderTagDisplayed(),
				GlobalConstants.isEmailAddressColumnHeaderVisible);
	}

	@Test(priority = 2, description = "Verify the Email Address column is not sortable")
	public void emailAddressColumnIsNotSortable() {
		navigateToPartnerListPage();

		assertTrue(partnerAdminPage.getSortIconCountForColumn(GlobalConstants.PARTNER_ID_COLUMN) > 0,
				GlobalConstants.isSortIconLocatorValidForSortableColumn);

		assertEquals(partnerAdminPage.getSortIconCountForColumn(GlobalConstants.EMAIL_ADDRESS_COLUMN), 0,
				GlobalConstants.isEmailAddressColumnNotSortable);
	}

	@Test(priority = 3, description = "Verify the placeholder text for the email textbox")
	public void emailAddressFilterPlaceholderIsCorrect() {
		navigateToPartnerListPage();

		partnerAdminPage.clickOnFilterButton();
		assertEquals(partnerAdminPage.getEmailAddressFilterPlaceholder(),
				GlobalConstants.EMAIL_ADDRESS_FILTER_PLACEHOLDER,
				GlobalConstants.isEmailAddressFilterPlaceholderCorrect);

		partnerAdminPage.clickOnFilterResetButton();
	}

	@Test(priority = 4, description = "Verify the info icon beside email address text")
	public void emailAddressFilterInfoIconIsDisplayed() {
		navigateToPartnerListPage();

		partnerAdminPage.clickOnFilterButton();
		assertTrue(partnerAdminPage.isEmailAddressFilterInfoIconDisplayed(),
				GlobalConstants.isEmailAddressFilterInfoIconDisplayed);

		partnerAdminPage.clickOnFilterResetButton();
	}

	@Test(priority = 5, description = "Verify info icon tooltip text")
	public void emailAddressFilterInfoTooltipTextIsCorrect() {
		navigateToPartnerListPage();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.clickOnEmailAddressFilterInfoIcon();

		assertEquals(partnerAdminPage.getEmailAddressFilterInfoTooltipText(),
				GlobalConstants.EMAIL_ADDRESS_FILTER_INFO_TOOLTIP,
				GlobalConstants.isEmailAddressFilterInfoTooltipCorrect);

		partnerAdminPage.clickOnFilterResetButton();
	}

	@Test(priority = 6, description = "Verify exact match filtering using full email")
	public void exactEmailMatchReturnsOnlyThatRecord() {
		navigateToPartnerListPage();

		String existingEmail = partnerAdminPage.getFirstRowEmailAddress();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterEmailAddressInFilter(existingEmail);
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isPartnerListLoadedByEmail(existingEmail), GlobalConstants.isPartnerListLoaded);

		List<String> emailsAfterFilter = partnerAdminPage.getEmailAddressColumnValues();
		assertEquals(emailsAfterFilter, List.of(existingEmail),
				GlobalConstants.isExactEmailMatchReturningOnlyThatRecord);

		partnerAdminPage.clickOnFilterResetButton();
	}

	@Test(priority = 7, description = "Verify that searching with a partial email address shows no results")
	public void partialEmailReturnsNoResults() {
		navigateToPartnerListPage();

		String partialEmail = partnerAdminPage.getFirstRowEmailAddress().split("@")[0] + "@";

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterEmailAddressInFilter(partialEmail);
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isNoResultsFoundsDisplayed(), GlobalConstants.isPartialEmailReturningNoResults);

		partnerAdminPage.clickOnFilterResetButton();
	}

	@Test(priority = 8, description = "Verify email filter with non-existing full email")
	public void nonExistingEmailReturnsNoResults() {
		navigateToPartnerListPage();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterEmailAddressInFilter(GlobalConstants.NON_EXISTING_EMAIL);
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isNoResultsFoundsDisplayed(), GlobalConstants.isNonExistingEmailReturningNoResults);

		partnerAdminPage.clickOnFilterResetButton();
	}

	@Test(priority = 9, description = "Verify the combined filtering with Email Address and other filters like Partner Type")
	public void combinedEmailAndMismatchedPartnerTypeReturnsNoResults() {
		navigateToPartnerListPage();

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

	@Test(priority = 10, description = "Verify case sensitivity of email filter")
	public void emailFilterIsNotCaseSensitive() {
		navigateToPartnerListPage();

		String existingEmail = partnerAdminPage.getFirstRowEmailAddress();

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterEmailAddressInFilter(existingEmail.toUpperCase());
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isPartnerListLoadedByEmail(existingEmail),
				GlobalConstants.isEmailFilterCaseInsensitive);

		partnerAdminPage.clickOnFilterResetButton();
	}

	@Test(priority = 11, description = "Verify the email inputs with unsupported domains or special characters")
	public void emailWithDisallowedSpecialCharactersShowsValidationError() {
		navigateToPartnerListPage();

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
