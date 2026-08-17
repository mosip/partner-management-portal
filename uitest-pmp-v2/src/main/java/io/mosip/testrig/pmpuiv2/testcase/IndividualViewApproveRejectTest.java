package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.SkipException;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.FtmPage;
import io.mosip.testrig.pmpuiv2.pages.IndividualViewPage;
import io.mosip.testrig.pmpuiv2.pages.ListOfSbiPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

/**
 * Approve/Reject offered on the Partner Admin individual view pages wherever
 * admin approval or rejection applies.
 *
 * Covers TC_38408_01 to TC_38408_06, TC_38408_10, TC_38408_13 and TC_38408_14.
 *
 * Not covered here:
 *
 * TC_38408_07 asks for Chrome, Firefox, Edge and Safari. BaseClass.setUp()
 * builds a ChromeDriver unconditionally, so the suite has no other browser to
 * run against.
 *
 * TC_38408_08 and TC_38408_09 need two authenticated sessions alive at once to
 * produce an approve/reject collision. DriverManager hands out one WebDriver
 * per test method, so that state cannot be reached.
 *
 * TC_38408_11 (Arabic) and TC_38408_12 (French) need a language switch, which
 * the fixed English login and the text-based shared locators do not support.
 *
 * Every method starts from the dashboard of a freshly signed-in Partner Admin
 * session, because BaseClass.setUp() builds a new driver and logs in for each
 * one. The dashboard cards used to reach each section only exist on the
 * dashboard, so no method navigates from one section straight into another.
 */
@Test(dependsOnGroups = { "PartnerPolicyMappingTest" }, groups = { "IndividualViewApproveRejectTest" })
public class IndividualViewApproveRejectTest extends BaseClass {

	private static final String STATUS_PENDING = "pending";
	private static final String STATUS_APPROVED = "approved";
	private static final String STATUS_REJECTED = "rejected";

	private DashboardPage dashboardPage;
	private PartnerPolicyMappingPage partnerPolicyMappingPage;
	private IndividualViewPage individualViewPage;
	private FtmPage ftmPage;
	private ListOfSbiPage listOfSbiPage;

	/**
	 * TC_38408_01 Approve/Reject visible only for Pending for Approval records.
	 * TC_38408_02 the buttons are absent for Approved and Rejected records.
	 * TC_38408_04 'Partner Status' is present whatever the record status.
	 */
	@Test(priority = 1, description = "Approve/Reject button visibility by record status in the individual view")
	public void approveRejectVisibilityByRecordStatus() {

		openPartnerPolicyLinking();

		// TC_38408_01: pending record shows Approve/Reject
		openPolicyRequestWithStatus(STATUS_PENDING);
		assertTrue(individualViewPage.isApproveRejectButtonDisplayed(),
				GlobalConstants.isIndividualViewApproveRejectButtonDisplayed);
		// TC_38408_04: Partner Status present on a pending record
		assertTrue(individualViewPage.isPartnerStatusLabelDisplayed(), GlobalConstants.isPartnerStatusFieldDisplayed);
		partnerPolicyMappingPage.clickOnViewBackButton();

		// TC_38408_02: approved record hides Approve/Reject
		openPolicyRequestWithStatus(STATUS_APPROVED);
		assertTrue(individualViewPage.isApproveRejectButtonAbsent(),
				GlobalConstants.isApproveRejectButtonAbsentForNonPendingRecord);
		// TC_38408_04: Partner Status present on an approved record
		assertTrue(individualViewPage.isPartnerStatusLabelDisplayed(), GlobalConstants.isPartnerStatusFieldDisplayed);
		partnerPolicyMappingPage.clickOnViewBackButton();

		// TC_38408_02: rejected record hides Approve/Reject
		openPolicyRequestWithStatus(STATUS_REJECTED);
		assertTrue(individualViewPage.isApproveRejectButtonAbsent(),
				GlobalConstants.isApproveRejectButtonAbsentForNonPendingRecord);
		// TC_38408_04: Partner Status present on a rejected record
		assertTrue(individualViewPage.isPartnerStatusLabelDisplayed(), GlobalConstants.isPartnerStatusFieldDisplayed);
		partnerPolicyMappingPage.clickOnViewBackButton();
	}

	/**
	 * TC_38408_05 an activated partner's status carries the green colour coding.
	 * TC_38408_10 Approve/Reject stays enabled for that record.
	 */
	@Test(priority = 2, description = "Partner Status Activated colour coding and Approve/Reject availability", dependsOnMethods = "approveRejectVisibilityByRecordStatus")
	public void partnerStatusActivatedColourCoding() {

		openPartnerPolicyLinking();
		openPolicyRequestWithStatus(STATUS_PENDING);

		String partnerStatus = readPartnerStatus();
		if (!GlobalConstants.PARTNER_STATUS_ACTIVE.equalsIgnoreCase(partnerStatus)) {
			throw new SkipException("The first Pending for Approval request belongs to a partner in '" + partnerStatus
					+ "' status, so the Activated colour coding of TC_38408_05 cannot be checked on it.");
		}

		// TC_38408_05: an activated partner renders green
		assertTrue(individualViewPage.isPartnerStatusActivatedColourCoded(),
				GlobalConstants.isPartnerStatusActivatedGreen);
		// TC_38408_10: the buttons remain enabled, not greyed out
		assertTrue(individualViewPage.isApproveRejectButtonEnabled(),
				GlobalConstants.isIndividualViewApproveRejectButtonEnabled);
	}

	/**
	 * TC_38408_06 Partner Status 'Deactivated' carries the grey colour coding.
	 * TC_38408_10 Approve/Reject is still offered for a deactivated partner.
	 *
	 * Needs a pending policy request raised by a deactivated partner. The test
	 * skips when the environment holds none, rather than reporting a pass it did
	 * not earn.
	 */
	@Test(priority = 3, description = "Partner Status Deactivated colour coding and Approve/Reject availability", dependsOnMethods = "approveRejectVisibilityByRecordStatus")
	public void partnerStatusDeactivatedColourCoding() {

		openPartnerPolicyLinking();
		openPolicyRequestWithStatus(STATUS_PENDING);

		String partnerStatus = readPartnerStatus();
		if (!GlobalConstants.DEACTIVATED.equalsIgnoreCase(partnerStatus)) {
			throw new SkipException("The first Pending for Approval request belongs to a partner in '" + partnerStatus
					+ "' status, so the Deactivated colour coding of TC_38408_06 cannot be exercised. Deactivate a "
					+ "partner holding a pending request to enable this check.");
		}

		// TC_38408_06: Deactivated renders grey
		assertTrue(individualViewPage.isPartnerStatusDeactivatedColourCoded(),
				GlobalConstants.isPartnerStatusDeactivatedGrey);
		// TC_38408_10: still offered even though the partner is deactivated
		assertTrue(individualViewPage.isApproveRejectButtonEnabled(),
				GlobalConstants.isIndividualViewApproveRejectButtonEnabled);
	}

	/**
	 * TC_38408_03 the individual view Approve/Reject opens the same confirmation
	 * popup as the tabular view, carrying both the Approve and the Reject buttons.
	 */
	@Test(priority = 4, description = "Approve/Reject in the individual view opens the tabular-view confirmation popup", dependsOnMethods = "approveRejectVisibilityByRecordStatus")
	public void approveRejectPopupFromIndividualView() {

		openPartnerPolicyLinking();
		openPolicyRequestWithStatus(STATUS_PENDING);

		assertTrue(individualViewPage.isApproveRejectButtonDisplayed(),
				GlobalConstants.isIndividualViewApproveRejectButtonDisplayed);
		individualViewPage.clickOnApproveRejectButton();

		// TC_38408_03: the popup matches the tabular view - title, copy, both actions
		assertTrue(individualViewPage.isApproveRejectPopupDisplayed(),
				GlobalConstants.isIndividualViewApproveRejectPopupDisplayed);
		assertTrue(individualViewPage.isApproveRejectPopupHeaderDisplayed(),
				GlobalConstants.isApproveRejectPopupTitleDisplayed);
		assertTrue(individualViewPage.isApproveRejectPopupDescriptionDisplayed(),
				GlobalConstants.isConfirmationPopupDetailedMessageDisplayed);
		assertTrue(individualViewPage.isApproveButtonDisplayed(), GlobalConstants.isApproveButtonDisplayed);
		assertTrue(individualViewPage.isRejectButtonDisplayed(), GlobalConstants.isRejectButtonDisplayed);

		individualViewPage.clickOnPopupCloseIcon();
	}

	/**
	 * TC_38408_13 the Approve/Reject action is completable without a mouse: Tab to
	 * focus, Enter to open the popup, Tab to reach the popup buttons, Escape to
	 * dismiss.
	 */
	@Test(priority = 5, description = "Keyboard access to the Approve/Reject buttons and confirmation popup", dependsOnMethods = "approveRejectPopupFromIndividualView")
	public void approveRejectKeyboardAccessibility() {

		openPartnerPolicyLinking();
		openPolicyRequestWithStatus(STATUS_PENDING);

		// TC_38408_13: Tab reaches the button and Enter opens the popup
		assertTrue(individualViewPage.openApproveRejectPopupUsingKeyboard(),
				GlobalConstants.isApproveRejectReachableByKeyboard);
		// TC_38408_13: the popup's own buttons are reachable by Tab
		assertTrue(individualViewPage.areApproveRejectPopupButtonsKeyboardReachable(),
				GlobalConstants.isApproveRejectPopupButtonsKeyboardReachable);
		// TC_38408_13: Escape dismisses the popup
		assertTrue(individualViewPage.closeApproveRejectPopupUsingEscape(),
				GlobalConstants.isApproveRejectPopupClosedByEscape);
	}

	/** TC_38408_01 and TC_38408_03 on the FTM Chip individual view. */
	@Test(priority = 6, description = "Approve/Reject in the FTM Chip individual view", dependsOnMethods = "approveRejectPopupFromIndividualView")
	public void approveRejectInFtmChipIndividualView() {

		dashboardPage = new DashboardPage(driver);
		individualViewPage = new IndividualViewPage(driver);
		ftmPage = new FtmPage(driver);

		dashboardPage.clickOnFTMChipTab();
		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterPendingForApproval();
		ftmPage.clickOnApplyFilterButton();
		ftmPage.clickOnFtmListItem1();

		verifyApproveRejectPopupOnCurrentIndividualView();
	}

	/** TC_38408_01 and TC_38408_03 on the SBI-Device individual view. */
	@Test(priority = 7, description = "Approve/Reject in the SBI-Device individual view", dependsOnMethods = "approveRejectPopupFromIndividualView")
	public void approveRejectInSbiIndividualView() {

		dashboardPage = new DashboardPage(driver);
		individualViewPage = new IndividualViewPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);

		dashboardPage.clickOnSbiDevices();
		listOfSbiPage.clickOnFilterButton();
		listOfSbiPage.enterPartnerIdInFilter(GlobalConstants.DEVICE_PARTNER_ID);
		listOfSbiPage.selectPendingForApprovalStatusInFilter();
		listOfSbiPage.clickOnApplyFilterButton();
		listOfSbiPage.clickOnPendingForApprovalSbiItem();

		verifyApproveRejectPopupOnCurrentIndividualView();
	}

	/**
	 * TC_38408_14 Approve/Reject must not be offered once an SBI has expired.
	 *
	 * The source test sheet records this scenario as Failing, so a failure here is
	 * the open product defect rather than a broken test. Register the method name
	 * in the known-issues list to have the suite skip it while the defect is open.
	 */
	@Test(priority = 8, description = "Approve/Reject is not offered for an expired SBI", dependsOnMethods = "approveRejectInSbiIndividualView")
	public void approveRejectNotOfferedForExpiredSbi() {

		dashboardPage = new DashboardPage(driver);
		individualViewPage = new IndividualViewPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);

		dashboardPage.clickOnSbiDevices();
		listOfSbiPage.clickOnFilterButton();
		listOfSbiPage.enterPartnerIdInFilter(GlobalConstants.DEVICE_PARTNER_ID);
		listOfSbiPage.selectExpiredSbiExpiryStatusInFilter();
		listOfSbiPage.clickOnApplyFilterButton();

		if (!listOfSbiPage.isAnySbiListed()) {
			throw new SkipException("No expired SBI exists for partner " + GlobalConstants.DEVICE_PARTNER_ID
					+ ", so the expired-SBI rule of TC_38408_14 cannot be exercised. Seed an SBI with a past "
					+ "expiry date to enable this check.");
		}

		listOfSbiPage.clickOnFirstSbiItem();

		// TC_38408_14: expired SBI must not expose Approve/Reject
		assertTrue(individualViewPage.isApproveRejectButtonAbsent(),
				GlobalConstants.isApproveRejectButtonAbsentForExpiredSbi);
	}

	/** Lands on the Partner - Policy Linking list from the dashboard. */
	private void openPartnerPolicyLinking() {
		dashboardPage = new DashboardPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);
		individualViewPage = new IndividualViewPage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingTitleDisplayed(),
				GlobalConstants.isViewPartnerPolicyLinkingPageTitleDisplayed);
	}

	/** TC_38408_04: the Partner Status value shown on the individual view. */
	private String readPartnerStatus() {
		assertTrue(individualViewPage.isPartnerStatusLabelDisplayed(), GlobalConstants.isPartnerStatusFieldDisplayed);
		return individualViewPage.getPartnerStatus();
	}

	/** TC_38408_03: the confirmation popup carries both actions and then closes. */
	private void verifyApproveRejectPopupOnCurrentIndividualView() {
		assertTrue(individualViewPage.isApproveRejectButtonDisplayed(),
				GlobalConstants.isIndividualViewApproveRejectButtonDisplayed);
		individualViewPage.clickOnApproveRejectButton();

		assertTrue(individualViewPage.isApproveRejectPopupDisplayed(),
				GlobalConstants.isIndividualViewApproveRejectPopupDisplayed);
		assertTrue(individualViewPage.isApproveButtonDisplayed(), GlobalConstants.isApproveButtonDisplayed);
		assertTrue(individualViewPage.isRejectButtonDisplayed(), GlobalConstants.isRejectButtonDisplayed);

		individualViewPage.clickOnPopupCloseIcon();
	}

	/**
	 * Opens the first partner-policy request matching the requested status.
	 *
	 * Clears any filter still applied from an earlier call first, so a stale
	 * selection cannot return a record in the wrong status.
	 */
	private void openPolicyRequestWithStatus(String status) {
		if (partnerPolicyMappingPage.isFilterResetButtonEnabled()) {
			partnerPolicyMappingPage.clickOnFilterResetButton();
		}

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.clickOnStatusFilterDropdown();

		switch (status) {
		case STATUS_PENDING:
			partnerPolicyMappingPage.clickOnPendingForApprovalStatus();
			partnerPolicyMappingPage.clickOnApplyFilterButton();
			partnerPolicyMappingPage.clickOnPendingForApprovalPolicy();
			break;
		case STATUS_APPROVED:
			partnerPolicyMappingPage.clickOnApprovedStatus();
			partnerPolicyMappingPage.clickOnApplyFilterButton();
			partnerPolicyMappingPage.clickOnApprovedPolicy();
			break;
		case STATUS_REJECTED:
			partnerPolicyMappingPage.clickOnRejectedStatus();
			partnerPolicyMappingPage.clickOnApplyFilterButton();
			partnerPolicyMappingPage.clickOnRejectedPolicy();
			break;
		default:
			throw new IllegalArgumentException("Unsupported policy request status: " + status);
		}

		assertTrue(partnerPolicyMappingPage.isPartnerPolicyDetailsPageDisplayed(),
				GlobalConstants.isViewPartnerPolicyLinkingPageTitleDisplayed);
	}
}
