package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerAdminPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "DeactivatePartnerCreation",
		"PartnerDetailsTest" }, groups = { "PartnerDeactivateOptionTest" })
public class PartnerDeactivateOptionTest extends BaseClass {

	private DashboardPage dashboardPage;
	private PartnerAdminPage partnerAdminPage;

	@Test(priority = 1, description = "Verify the Deactivate option in Action menu for Active Partners in List of Partners")
	public void deactivateOptionAvailableForActivatedPartner() {
		openActionMenuForPartner(true);

		assertTrue(partnerAdminPage.isViewButtonsDisplayed(), GlobalConstants.isViewButtonsDisplayed);
		assertTrue(partnerAdminPage.isViewOptionEnabled(), GlobalConstants.isViewButtonsEnabled);

		assertTrue(partnerAdminPage.isDeactivateButtonsDisplayed(), GlobalConstants.isDeactivateButtonsDisplayed);
		assertTrue(partnerAdminPage.isDeactivateOptionEnabled(), GlobalConstants.isDeactivateOptionEnabled);
	}

	@Test(priority = 2, description = "Verify the Deactivate option in Action menu for Active Partners is accessible")
	public void deactivateOptionAccessibleForActivatedPartner() {
		openActionMenuForPartner(true);

		assertTrue(partnerAdminPage.isViewButtonsDisplayed(), GlobalConstants.isViewButtonsDisplayed);
		assertTrue(partnerAdminPage.isDeactivateButtonsDisplayed(), GlobalConstants.isDeactivateButtonsDisplayed);

		// Accessible means operable, not merely visible: reachable by keyboard, not mouse only
		assertTrue(partnerAdminPage.isDeactivateOptionKeyboardOperable(),
				GlobalConstants.isDeactivateOptionKeyboardOperable);
		assertTrue(partnerAdminPage.isDeactivateOptionCursorPointer(),
				GlobalConstants.isDeactivateOptionCursorPointer);

		partnerAdminPage.pressEnterOnDeactivateOption();
		assertTrue(partnerAdminPage.isDeactivatePopupHeaderDisplayed(),
				GlobalConstants.isDeactivatePopupDisplayedOnEnterKey);
		partnerAdminPage.clickOnDeactivatePopupCancelButton();
	}

	@Test(priority = 3, description = "Verify popup is displayed upon clicking Deactivate option")
	public void deactivatePopupDisplayedOnClickingDeactivateOption() {
		openActionMenuForPartner(true);

		assertTrue(partnerAdminPage.isDeactivateButtonsDisplayed(), GlobalConstants.isDeactivateButtonsDisplayed);
		assertTrue(partnerAdminPage.isDeactivateOptionEnabled(), GlobalConstants.isDeactivateOptionEnabled);

		partnerAdminPage.clickOnDeactivateOptionInActionMenu();
		assertTrue(partnerAdminPage.isDeactivatePopupHeaderDisplayed(),
				GlobalConstants.isDeactivatePopupHeaderDisplayed);
		assertTrue(partnerAdminPage.isDeactivatePopupDescriptionDisplayed(),
				GlobalConstants.isDeactivatePopupDescriptionDisplayed);
		assertTrue(partnerAdminPage.isDeactivatePopupCancelButtonDisplayed(),
				GlobalConstants.isDeactivatePopupCancelButtonDisplayed);
		assertTrue(partnerAdminPage.isDeactivatePopupConfirmButtonDisplayed(),
				GlobalConstants.isDeactivatePopupConfirmButtonDisplayed);

		// Cancel keeps the partner Active for the final method to deactivate itself
		partnerAdminPage.clickOnDeactivatePopupCancelButton();
	}

	@Test(priority = 4, description = "Verify Deactivate Partner popup title")
	public void deactivatePopupTitleIsCorrect() {
		openDeactivatePopupForActivePartner();

		String expectedTitle = String.format(GlobalConstants.DEACTIVATE_PARTNER_POPUP_TITLE,
				GlobalConstants.PARTNERDETAILS_USER_ID, GlobalConstants.ORGANISATION_NAME);
		assertEquals(partnerAdminPage.getDeactivatePopupTitle(), expectedTitle,
				GlobalConstants.isDeactivatePopupTitleCorrect);

		partnerAdminPage.clickOnDeactivatePopupCancelButton();
	}

	@Test(priority = 5, description = "Verify Deactivate Partner popup sub title")
	public void deactivatePopupSubTitleIsCorrect() {
		openDeactivatePopupForActivePartner();

		assertEquals(partnerAdminPage.getDeactivatePopupSubTitle(),
				GlobalConstants.DEACTIVATE_PARTNER_POPUP_DESCRIPTION, GlobalConstants.isDeactivatePopupSubTitleCorrect);

		partnerAdminPage.clickOnDeactivatePopupCancelButton();
	}

	@Test(priority = 6, description = "Verify in Deactivate Partner popup Partner ID and Org name is aligned properly")
	public void deactivatePopupPartnerIdAndOrganisationRenderCorrectly() {
		openDeactivatePopupForActivePartner();

		assertTrue(partnerAdminPage.isDeactivatePopupTitleFullyInterpolated(),
				GlobalConstants.isDeactivatePopupTitleFullyInterpolated);

		String title = partnerAdminPage.getDeactivatePopupTitle();
		assertTrue(title.contains(GlobalConstants.PARTNERDETAILS_USER_ID),
				GlobalConstants.isPartnerIdPresentInDeactivatePopupTitle);
		assertTrue(title.contains(GlobalConstants.ORGANISATION_NAME),
				GlobalConstants.isOrganisationPresentInDeactivatePopupTitle);
		assertTrue(
				partnerAdminPage.isPartnerIdOrderedBeforeOrganisation(GlobalConstants.PARTNERDETAILS_USER_ID,
						GlobalConstants.ORGANISATION_NAME),
				GlobalConstants.isPartnerIdOrderedBeforeOrganisation);

		partnerAdminPage.clickOnDeactivatePopupCancelButton();
	}

	@Test(priority = 7, description = "Verify Confirm and Cancel buttons are available in popup")
	public void deactivatePopupHasConfirmAndCancelButtons() {
		openDeactivatePopupForActivePartner();

		assertTrue(partnerAdminPage.isDeactivatePopupCancelButtonDisplayed(),
				GlobalConstants.isDeactivatePopupCancelButtonDisplayed);
		assertTrue(partnerAdminPage.isDeactivatePopupConfirmButtonDisplayed(),
				GlobalConstants.isDeactivatePopupConfirmButtonDisplayed);

		partnerAdminPage.clickOnDeactivatePopupCancelButton();
	}

	@Test(priority = 8, description = "Verify Confirm button in popup is accessible")
	public void deactivatePopupConfirmButtonIsAccessible() {
		openDeactivatePopupForActivePartner();

		assertTrue(partnerAdminPage.isDeactivatePopupConfirmButtonDisplayed(),
				GlobalConstants.isDeactivatePopupConfirmButtonDisplayed);
		// Confirm is a real <button>, so unlike the Action menu option it is natively
		// keyboard operable and isEnabled() actually means something.
		assertTrue(partnerAdminPage.isDeactivatePopupConfirmButtonNativeButton(),
				GlobalConstants.isDeactivatePopupConfirmButtonNativeButton);
		assertTrue(partnerAdminPage.isDeactivatePopupConfirmButtonEnabled(),
				GlobalConstants.isDeactivatePopupConfirmButtonEnabled);
		assertTrue(partnerAdminPage.isDeactivatePopupConfirmButtonFocusable(),
				GlobalConstants.isDeactivatePopupConfirmButtonFocusable);

		partnerAdminPage.clickOnDeactivatePopupCancelButton();
	}

	@Test(priority = 9, description = "Verify upon clicking on confirm button the partner is deactivated and the row is greyed out", dependsOnMethods = {
			"deactivateOptionAvailableForActivatedPartner", "deactivateOptionAccessibleForActivatedPartner",
			"deactivatePopupDisplayedOnClickingDeactivateOption", "deactivatePopupTitleIsCorrect",
			"deactivatePopupSubTitleIsCorrect", "deactivatePopupPartnerIdAndOrganisationRenderCorrectly",
			"deactivatePopupHasConfirmAndCancelButtons", "deactivatePopupConfirmButtonIsAccessible" })
	public void partnerIsDeactivatedAndGreyedOutOnConfirm() {
		openDeactivatePopupForActivePartner();

		// The irreversible step, which is why every other scenario cancels
		partnerAdminPage.clickOnDeactivatePopupConfirmButton();

		assertTrue(partnerAdminPage.isSubTitleListDisplayed(), GlobalConstants.isSubTitleListDisplayed);
		assertTrue(partnerAdminPage.isTitlePartnerDisplayed(), GlobalConstants.isTitlePartnerDisplayed);

		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.DEACTIVATED,
				GlobalConstants.isPartnerStatusDeactivatedAfterConfirm);

		assertTrue(partnerAdminPage.isFirstRowStatusBadgeDeactivated(),
				GlobalConstants.isPartnerStatusBadgeGreyAfterConfirm);
		assertTrue(partnerAdminPage.isFirstPartnerRowGreyedOut(), GlobalConstants.isPartnerRowGreyedOutAfterConfirm);
	}

	@Test(priority = 10, description = "Verify the Deactivate option is neither available nor accessible once the partner is deactivated", dependsOnMethods = "partnerIsDeactivatedAndGreyedOutOnConfirm")
	public void deactivateOptionUnavailableForDeactivatedPartner() {
		dashboardPage = new DashboardPage(driver);
		partnerAdminPage = new PartnerAdminPage(driver);

		dashboardPage.clickOnPartners();
		filterForPartner(false);
		assertTrue(partnerAdminPage.isFirstPartnerRowDisplayed(), GlobalConstants.isDeactivatedPartnerRowDisplayed);
		partnerAdminPage.clickOnActionsButton();

		assertTrue(partnerAdminPage.isDeactivateOptionDisabled(),
				GlobalConstants.isDeactivateOptionDisabledForDeactivatedPartner);
		assertFalse(partnerAdminPage.isDeactivateOptionEnabled(), GlobalConstants.isDeactivateOptionEnabled);
		assertTrue(partnerAdminPage.isDeactivateOptionCursorDefault(),
				GlobalConstants.isDeactivateOptionCursorDefault);

		// Absence assertions use the short-window check: the full-timeout variant costs ~45s each
		partnerAdminPage.clickOnDeactivateOptionInActionMenu();
		assertFalse(partnerAdminPage.isDeactivatePopupHeaderDisplayedQuick(),
				GlobalConstants.isDeactivatePopupHeaderDisplayed);
		partnerAdminPage.pressEnterOnDeactivateOption();
		assertFalse(partnerAdminPage.isDeactivatePopupHeaderDisplayedQuick(),
				GlobalConstants.isDeactivatePopupNotDisplayedOnEnterKey);

		partnerAdminPage.clickOnFilterResetButton();
	}

	private void openActionMenuForPartner(boolean activated) {
		dashboardPage = new DashboardPage(driver);
		partnerAdminPage = new PartnerAdminPage(driver);

		assertTrue(dashboardPage.isPartnersDisplayed(), GlobalConstants.isPartnersButtonDisplayed);
		dashboardPage.clickOnPartners();
		assertTrue(partnerAdminPage.isSubTitleListDisplayed(), GlobalConstants.isSubTitleListDisplayed);
		assertTrue(partnerAdminPage.isTitlePartnerDisplayed(), GlobalConstants.isTitlePartnerDisplayed);

		filterForPartner(activated);
		if (activated) {
			assertTrue(partnerAdminPage.isActivatedPartnersDisplayed(), GlobalConstants.isActivatedPartnersDisplayed);
		}

		partnerAdminPage.clickOnActionsButton();
	}

	private void openDeactivatePopupForActivePartner() {
		openActionMenuForPartner(true);
		assertTrue(partnerAdminPage.isDeactivateOptionEnabled(), GlobalConstants.isDeactivateOptionEnabled);
		partnerAdminPage.clickOnDeactivateOptionInActionMenu();
		assertTrue(partnerAdminPage.isDeactivatePopupHeaderDisplayed(),
				GlobalConstants.isDeactivatePopupHeaderDisplayed);
	}

	private void filterForPartner(boolean activated) {
		assertTrue(partnerAdminPage.isPartnerListLoaded(), GlobalConstants.isPartnerListLoaded);
		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(GlobalConstants.PARTNERDETAILS_USER_ID);
		partnerAdminPage.clickOnStatusFilter();
		if (activated) {
			partnerAdminPage.clickActivatedButton();
		} else {
			partnerAdminPage.clickOnDeActivatedStatusInFilters();
		}
		partnerAdminPage.clickOnApplyFiltersBtn();
	}

}
