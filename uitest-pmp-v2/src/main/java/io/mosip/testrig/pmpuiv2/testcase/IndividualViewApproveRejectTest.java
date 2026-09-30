package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.FtmPage;
import io.mosip.testrig.pmpuiv2.pages.IndividualViewPage;
import io.mosip.testrig.pmpuiv2.pages.ListOfSbiPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

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

	@Test(priority = 1, description = "Verify Approve/Reject button visibility by record status in the individual view")
	public void approveRejectVisibilityByRecordStatus() {

		openPartnerPolicyLinking();

		openPolicyRequestWithStatus(STATUS_PENDING);
		assertTrue(individualViewPage.isApproveRejectButtonDisplayed(),
				GlobalConstants.isIndividualViewApproveRejectButtonDisplayed);
		assertTrue(individualViewPage.isPartnerStatusLabelDisplayed(), GlobalConstants.isPartnerStatusFieldDisplayed);
		partnerPolicyMappingPage.clickOnViewBackButton();

		openPolicyRequestWithStatus(STATUS_APPROVED);
		assertTrue(individualViewPage.isApproveRejectButtonAbsent(),
				GlobalConstants.isApproveRejectButtonAbsentForNonPendingRecord);
		assertTrue(individualViewPage.isPartnerStatusLabelDisplayed(), GlobalConstants.isPartnerStatusFieldDisplayed);
		partnerPolicyMappingPage.clickOnViewBackButton();

		openPolicyRequestWithStatus(STATUS_REJECTED);
		assertTrue(individualViewPage.isApproveRejectButtonAbsent(),
				GlobalConstants.isApproveRejectButtonAbsentForNonPendingRecord);
		assertTrue(individualViewPage.isPartnerStatusLabelDisplayed(), GlobalConstants.isPartnerStatusFieldDisplayed);
		partnerPolicyMappingPage.clickOnViewBackButton();
	}

	@Test(priority = 2, description = "Verify Partner Status Activated colour coding and Approve/Reject availability", dependsOnMethods = "approveRejectVisibilityByRecordStatus")
	public void partnerStatusActivatedColourCoding() {

		openPartnerPolicyLinking();
		openPendingPolicyRequestForPartner(GlobalConstants.AUTH_PARTNER_ID);

		assertEquals(readPartnerStatus(), GlobalConstants.PARTNER_STATUS_ACTIVE,
				GlobalConstants.isPartnerStatusActiveBeforeDeactivation);

		assertTrue(individualViewPage.isPartnerStatusActivatedColourCoded(),
				GlobalConstants.isPartnerStatusActivatedGreen);
		assertTrue(individualViewPage.isApproveRejectButtonEnabled(),
				GlobalConstants.isIndividualViewApproveRejectButtonEnabled);
	}

	@Test(priority = 4, description = "Verify Approve/Reject in the individual view opens the confirmation popup", dependsOnMethods = "approveRejectVisibilityByRecordStatus")
	public void approveRejectPopupFromIndividualView() {

		openPartnerPolicyLinking();
		openPolicyRequestWithStatus(STATUS_PENDING);

		assertTrue(individualViewPage.isApproveRejectButtonDisplayed(),
				GlobalConstants.isIndividualViewApproveRejectButtonDisplayed);
		individualViewPage.clickOnApproveRejectButton();

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

	@Test(priority = 5, description = "Verify keyboard access to the Approve/Reject buttons and confirmation popup", dependsOnMethods = "approveRejectPopupFromIndividualView")
	public void approveRejectKeyboardAccessibility() {

		openPartnerPolicyLinking();
		openPolicyRequestWithStatus(STATUS_PENDING);

		assertTrue(individualViewPage.openApproveRejectPopupUsingKeyboard(),
				GlobalConstants.isApproveRejectReachableByKeyboard);
		assertTrue(individualViewPage.areApproveRejectPopupButtonsKeyboardReachable(),
				GlobalConstants.isApproveRejectPopupButtonsKeyboardReachable);
		assertTrue(individualViewPage.closeApproveRejectPopupUsingEscape(),
				GlobalConstants.isApproveRejectPopupClosedByEscape);
	}

	@Test(priority = 6, description = "Verify Approve/Reject in the FTM Chip individual view", dependsOnMethods = "approveRejectPopupFromIndividualView")
	public void approveRejectInFtmChipIndividualView() {

		dashboardPage = new DashboardPage(driver);
		individualViewPage = new IndividualViewPage(driver);
		ftmPage = new FtmPage(driver);

		dashboardPage.clickOnFTMChipTab();
		ftmPage.clickOnFilterButton();
		ftmPage.clickOnStatusFilter();
		ftmPage.clickOnFtmStatusFilterPendingForApproval();
		ftmPage.clickOnApplyFilterButton();
		ftmPage.clickOnFtmListItem1();

		verifyApproveRejectPopupOnCurrentIndividualView();
	}

	@Test(priority = 7, description = "Verify Approve/Reject in the SBI-Device individual view", dependsOnMethods = "approveRejectPopupFromIndividualView")
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

	private void openPartnerPolicyLinking() {
		dashboardPage = new DashboardPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);
		individualViewPage = new IndividualViewPage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingTitleDisplayed(),
				GlobalConstants.isViewPartnerPolicyLinkingPageTitleDisplayed);
	}

	private String readPartnerStatus() {
		assertTrue(individualViewPage.isPartnerStatusLabelDisplayed(), GlobalConstants.isPartnerStatusFieldDisplayed);
		return individualViewPage.getPartnerStatus();
	}

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

	private void openPendingPolicyRequestForPartner(String partnerId) {
		if (partnerPolicyMappingPage.isFilterResetButtonEnabled()) {
			partnerPolicyMappingPage.clickOnFilterResetButton();
		}

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterPartnerIdInFilter(partnerId);
		partnerPolicyMappingPage.clickOnStatusFilterDropdown();
		partnerPolicyMappingPage.clickOnPendingForApprovalStatus();
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPendingForApprovalPolicy();

		assertTrue(partnerPolicyMappingPage.isPartnerPolicyDetailsPageDisplayed(),
				GlobalConstants.isViewPartnerPolicyLinkingPageTitleDisplayed);
	}

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
