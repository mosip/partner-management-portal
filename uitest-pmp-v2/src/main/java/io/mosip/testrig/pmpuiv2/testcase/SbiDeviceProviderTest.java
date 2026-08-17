package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.SkipException;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.AddDevicePage;
import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.DeviceProviderPage;
import io.mosip.testrig.pmpuiv2.pages.ListOfDevicesPage;
import io.mosip.testrig.pmpuiv2.pages.ListOfSbiPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.ViewDeviceDetailsPage;
import io.mosip.testrig.pmpuiv2.pages.ViewSbiDetailsPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "DeviceCreationTest" }, groups = { "SbiDeviceProviderTest" })
public class SbiDeviceProviderTest extends BaseClass {
	private DeviceProviderPage deviceProviderPage;
	private DashboardPage dashboardpage;
	private ListOfSbiPage listOfSbiPage;
	private AddDevicePage addDevicePage;
	private ListOfDevicesPage listOfDevicesPage;
	private ViewDeviceDetailsPage viewDeviceDetailsPage;
	private ViewSbiDetailsPage viewSbiDetailsPage;
	private BasePage basePage;
	private AuthPolicyPage authPolicyPage;

	@Test(priority = 1, description = "Verify List Of SBI Page and Deactivate Sbi")
	public void verifySbiPage() {

		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);

		loginAsDeviceProvider();

		deviceProviderPage = dashboardpage.clickOnDeviceProviderServicesTitle();

		assertEquals(listOfSbiPage.getListOfSbiMessage(), GlobalConstants.LIST_OF_SBI_PAGE_MESSAGE);
		assertTrue(listOfSbiPage.isDeviceApprovedCountDisplayed(), GlobalConstants.isDeviceApprovedCountDisplayed);
		assertTrue(listOfSbiPage.isDevicePendingForApprovalCountDisplayed(),
				GlobalConstants.isDevicePendingForApprovalCountDisplayed);

		assertEquals(listOfSbiPage.isAddDeviceButtonEnabled(GlobalConstants.AUTOMATION_REJECTING), false);
		assertEquals(listOfSbiPage.isAddDeviceButtonEnabled(GlobalConstants.AUTOMATION), true);
		assertEquals(listOfSbiPage.getSbiListArrowDirection(), GlobalConstants.SBI_DETAIL_ARROW_VALUE);

		listOfSbiPage.clickOnThreeDotsOfSbiList(GlobalConstants.ALPHANUMERIC);

		assertTrue(listOfSbiPage.isDeactivateOptionEnabled(), GlobalConstants.isDeactivateSbiEnabled);
		listOfSbiPage.clickOnDeactivateSbi();
		assertTrue(listOfSbiPage.isDeactivateSbiPopupTitleDisplayed(), GlobalConstants.isDeactivateSbiPopupDisplayed);
		assertEquals(listOfSbiPage.getDeactivateSbiPopupMessage(), GlobalConstants.DEACTIVATE_SBI_POPUP_MESSAGE);
		assertEquals(listOfSbiPage.getDeactivateSbiPopupTitle(), GlobalConstants.DEACTIVATE_SBI_POPUP_TITLE);
		assertEquals(listOfSbiPage.getDeactivateSbiPopupDeviceDetails(),
				GlobalConstants.DEACTIVATE_SBI_POPUP_DEVICE_DETAILS);
		assertTrue(listOfSbiPage.isHighlightedConfirmDeactivateSbiDisplayed(),
				GlobalConstants.isHighlightedConfirmDeactivateSbiDisplayed);
		listOfSbiPage.clickOnDeactivateSbiCancel();

		listOfSbiPage.clickOnThreeDotsOfSbiList(GlobalConstants.ALPHANUMERIC);
		listOfSbiPage.clickOnDeactivateSbi();
		listOfSbiPage.clickOnDeactivateSubmit();
		assertTrue(listOfSbiPage.isDeactivatedStatusDisplayed(GlobalConstants.ALPHANUMERIC),
				GlobalConstants.isStatusDisplayed);
		listOfSbiPage.reload();
		assertTrue(listOfSbiPage.isDeactivatedStatusDisplayed(GlobalConstants.ALPHANUMERIC),
				GlobalConstants.isStatusDisplayed);
		assertTrue(listOfSbiPage.isDeactivatedSbiGreyedOut(), GlobalConstants.isDeactivatedSbiGreyedOut);
		assertEquals(listOfSbiPage.isAddDeviceButtonEnabled(GlobalConstants.ALPHANUMERIC), false);

		verifySbiDetails(GlobalConstants.ALPHANUMERIC);

		assertEquals(listOfSbiPage.isViewDeviceButtonEnabled(GlobalConstants.ALPHANUMERIC), true);
		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.ALPHANUMERIC);
		assertTrue(listOfDevicesPage.isDeviceListAddDeviceButtonDisabled(),
				GlobalConstants.isDeviceListAddDeviceButtonDisabled);
		listOfDevicesPage.clickOnListOfSbiButton();

		listOfSbiPage.clickOnThreeDotsOfSbiList(GlobalConstants.AUTOMATION_DEACTIVATING);
		listOfSbiPage.clickOnDeactivateSbi();
		listOfSbiPage.clickOnDeactivateSubmit();
		assertTrue(listOfSbiPage.isDeactivatedStatusDisplayed(GlobalConstants.AUTOMATION_DEACTIVATING),
				GlobalConstants.isStatusDisplayed);

		verifyDeactivateSbiIsEnabled(GlobalConstants.ALPHANUMERIC, false);
		verifyDeactivateSbiIsEnabled(GlobalConstants.AUTOMATION_REJECTING, false);
		verifyDeactivateSbiIsEnabled(GlobalConstants.AUTOMATION, true);

		assertTrue(listOfSbiPage.isRejectedStatusDisplayed(GlobalConstants.AUTOMATION_REJECTING),
				GlobalConstants.isStatusDisplayed);
		assertTrue(listOfSbiPage.isViewDeviceButtonEnabled(GlobalConstants.AUTOMATION_REJECTING),
				GlobalConstants.isViewDevicesEnabled);
		verifySbiDetails(GlobalConstants.AUTOMATION_REJECTING);
		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.AUTOMATION_REJECTING);
		assertTrue(listOfDevicesPage.isRejectedStatusDisplayed(), GlobalConstants.isStatusDisplayed);
		assertEquals(listOfSbiPage.getListOfSbiMessage(), GlobalConstants.LIST_OF_SBI_PAGE_MESSAGE);
		assertTrue(listOfDevicesPage.isAddDeviceButtonDisabled(), GlobalConstants.isAddDeviceButtonDisabled);
		listOfDevicesPage.clickOnListOfSbiButton();

	}

	@Test(priority = 2, description = "Verify SBI Devices with Pending for approval status", dependsOnMethods = "verifySbiPage")
	public void verifySbiWithPendingForApprovalStatus() {
		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);
		addDevicePage = new AddDevicePage(driver);

		loginAsDeviceProvider();

		deviceProviderPage = dashboardpage.clickOnDeviceProviderServicesTitle();

		assertEquals(listOfSbiPage.isAddDeviceButtonEnabled(GlobalConstants.NUMERIC), false);
		verifyDeactivateSbiIsEnabled(GlobalConstants.NUMERIC, false);
		verifySbiDetails(GlobalConstants.NUMERIC);
		assertTrue(listOfSbiPage.isViewDeviceButtonEnabled(GlobalConstants.NUMERIC),
				GlobalConstants.isViewDevicesEnabled);
		assertEquals(listOfSbiPage.isAddDeviceButtonEnabled(GlobalConstants.NUMERIC), false);

		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.NUMERIC);
		assertEquals(listOfSbiPage.getListOfSbiMessage(), GlobalConstants.LIST_OF_SBI_PAGE_MESSAGE);
		assertTrue(addDevicePage.isPendingForApprovalStatusDisplayed(), GlobalConstants.isStatusDisplayed);
		assertTrue(listOfDevicesPage.isAddDeviceButtonDisabled(), GlobalConstants.isAddDeviceButtonDisabled);
		addDevicePage.clickOnListOfSbiButton();

	}

	@Test(priority = 3, description = "Creating SBI Devices which are already exists", dependsOnMethods = "verifySbiWithPendingForApprovalStatus")
	public void createSbiDeviceWhichExist() {

		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		loginAsDeviceProvider();

		deviceProviderPage = dashboardpage.clickOnDeviceProviderServicesTitle();

		addSbi(GlobalConstants.AUTOMATION, GlobalConstants.AUTOMATION);
		assertTrue(deviceProviderPage.isSbiExistsErrorMessageDisplayed(),
				GlobalConstants.isSbiExistsErrorMessageDisplayed);

		deviceProviderPage.clickOnListOfSbiTitleButton();
		deviceProviderPage.clickOnAlertProceed();
		addSbi(GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.AUTOMATION_REJECTING);
		assertTrue(deviceProviderPage.isSbiExistsErrorMessageDisplayed(),
				GlobalConstants.isSbiExistsErrorMessageDisplayed);
	}

	@Test(priority = 4, description = "Verifying the SBI and Devices after deactivating", dependsOnMethods = "createSbiDeviceWhichExist")
	public void verifySbiAndDeviceAfterDeactivate() {

		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);
		loginAsDeviceProvider();

		deviceProviderPage = dashboardpage.clickOnDeviceProviderServicesTitle();

		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.ALPHANUMERIC);

		listOfDevicesPage.isDeviceStatusDisplayed(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_5, GlobalConstants.AUTOMATION_DEACTIVATING_5,
				GlobalConstants.REJECTED);
		listOfDevicesPage.isDeviceStatusDisplayed(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_4, GlobalConstants.AUTOMATION_DEACTIVATING_4,
				GlobalConstants.DEACTIVATED);
		listOfDevicesPage.isDeviceStatusDisplayed(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_3, GlobalConstants.AUTOMATION_DEACTIVATING_3,
				GlobalConstants.REJECTED);
		listOfDevicesPage.clickOnDeviceThreeDots(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_3, GlobalConstants.AUTOMATION_DEACTIVATING_3);
		assertTrue(listOfDevicesPage.isDeactivateDeviceDisabled(), GlobalConstants.isDeactivateDeviceDisabled);
		assertTrue(listOfDevicesPage.isViewDeviceEnabled(), GlobalConstants.isViewDevicesEnabled);

		assertTrue(listOfDevicesPage.getDeviceStatusClassValue(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_2, GlobalConstants.AUTOMATION_DEACTIVATING_2,
				GlobalConstants.DEACTIVATED).contains(GlobalConstants.DEACTIVATED_BACKGROUND));

		listOfDevicesPage.clickOnDeviceThreeDots(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_2, GlobalConstants.AUTOMATION_DEACTIVATING_2);
		assertTrue(listOfDevicesPage.isDeactivateDeviceDisabled(), GlobalConstants.isDeactivateDeviceDisabled);
		assertTrue(listOfDevicesPage.isViewDeviceEnabled(), GlobalConstants.isViewDevicesEnabled);

		listOfDevicesPage.clickOnListOfSbiButton();
		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.AUTOMATION_DEACTIVATING);
		assertEquals(listOfSbiPage.getListOfSbiMessage(), GlobalConstants.LIST_OF_SBI_PAGE_MESSAGE);
		assertTrue(listOfDevicesPage.isAddDeviceButtonDisabled(), GlobalConstants.isAddDeviceButtonDisabled);

		// Adding same rejected device again, these code required
//		listOfDevicesPage.clickOnListOfSbiButton();
//		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.AUTOMATION);
//		listOfDevicesPage.clickOnAddDeviceButtonFromDeviceList();
//		fillDeviceDetailsForRejectVerification(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION_DEACTIVATING_3,
//				GlobalConstants.AUTOMATION_DEACTIVATING_3);
//		addDevicePage.clickOnSubmit();
//		assertTrue(addDevicePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
//		addDevicePage.closeSuccessMessage();

	}

	@Test(priority = 5, description = "Verify linked devices of SBI list", dependsOnMethods = "verifySbiAndDeviceAfterDeactivate")
	public void verifyLinkedDevicesOfSbiList() {

		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);

		dashboardpage.clickOnSbiDevices();

		assertTrue(listOfSbiPage.isSbiCreationDateSameAsBrowserDateFormat(),
				GlobalConstants.isSbiCreationDateSameAsBrowserDateFormat);
		assertTrue(listOfSbiPage.isSbiExpirationDateSameAsBrowserDateFormat(),
				GlobalConstants.isSbiExpirationDateSameAsBrowserDateFormat);

		verifyLinkedDevicesOfApprovededSbiInList(GlobalConstants.AUTOMATION, GlobalConstants.APPROVED);

		verifyLinkedDevicesOfPendingForApprovalSbiInList(GlobalConstants.NUMERIC, GlobalConstants.PENDING_FOR_APPROVAL);

		verifyLinkedDevicesOfRejectedSbiInList(GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.REJECTED);

		verifyLinkedDevicesOfDeactivatedSbiInList(GlobalConstants.ALPHANUMERIC, GlobalConstants.APPROVED);

		assertTrue(authPolicyPage.isPaginationDisplayed(), GlobalConstants.isPaginationDisplayed);
		assertTrue(authPolicyPage.isPreviusPageButtonDisplayed(), GlobalConstants.isPreviusPageButtonDisplayed);
		assertTrue(authPolicyPage.isNextPageButtonDisplayed(), GlobalConstants.isNextPageButtonDisplayed);
		assertTrue(authPolicyPage.isPrefixOfPageDisplayed(), GlobalConstants.isPrefixOfPageDisplayed);
		assertTrue(authPolicyPage.isRecordPerPageDisplayed(), GlobalConstants.isRecordPerPageDisplayed);

	}

	@Test(priority = 6, description = "Verify SBI details page as admin", dependsOnMethods = "verifyLinkedDevicesOfSbiList")
	public void verifySbiDetailsPageAsAdmin() {
		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		viewSbiDetailsPage = new ViewSbiDetailsPage(driver);

		dashboardpage.clickOnSbiDevices();
		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(GlobalConstants.AUTOMATION);

		listOfSbiPage.clickOnSbiViewButton();

		assertTrue(viewSbiDetailsPage.isViewSbiDetailsTitleDisplayed(), GlobalConstants.isViewSbiDetailsTitleDisplayed);
		assertEquals(viewSbiDetailsPage.getBreadcrumbText(), GlobalConstants.LIST_OF_SBI_PAGE_BREADCUMB);
		assertTrue(viewSbiDetailsPage.isViewSbiDetailsSubTitleDisplayed(),
				GlobalConstants.isViewSbiDetailsSubTitleDisplayed);

		verifySbiDetailsAsAdmin(GlobalConstants.AUTOMATION);
		verifyViewOptionIsEnabled(GlobalConstants.ALPHANUMERIC, true);
		verifyViewOptionIsEnabled(GlobalConstants.AUTOMATION, true);
		verifyApproveOrRejectSbiIsEnabled(GlobalConstants.ALPHANUMERIC, false);
		verifySbiStatusInDetailsPage(GlobalConstants.AUTOMATION, GlobalConstants.APPROVED);
		verifySbiStatusInDetailsPage(GlobalConstants.ALPHANUMERIC, GlobalConstants.DEACTIVATED);
		verifySbiStatusInDetailsPage(GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.REJECTED);
		verifySbiStatusInDetailsPage(GlobalConstants.NUMERIC, GlobalConstants.PENDING_FOR_APPROVAL);
		verifyLinkedDeviceLinkInSbiDetailsPage(GlobalConstants.AUTOMATION);

	}

	@Test(priority = 7, description = "Verify Tabular View Of Devices page as admin", dependsOnMethods = "verifySbiDetailsPageAsAdmin")
	public void verifyTabularViewOfDevicesPageAsAdmin() {
		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		viewDeviceDetailsPage = new ViewDeviceDetailsPage(driver);
		basePage = new BasePage(driver);

		dashboardpage.clickOnSbiDevices();
		listOfSbiPage.clickOnDeviceTab();
		verifyListOfDevicesHeaders();

		assertTrue(listOfDevicesPage.isListOfDevicesTitleDisplayed(), GlobalConstants.isListOfDevicesTitleDisplayed);
		assertTrue(listOfDevicesPage.isHomeBreadcumbDisplayed(), GlobalConstants.isHomeBreadcumbDisplayed);

		assertTrue(listOfDevicesPage.isDeviceIdDisplayedInFifthColumnOnSbiDevicePage(),
				GlobalConstants.isDeviceIdDisplayedInFifthColumnOnSbiDevicePage);
		assertTrue(listOfDevicesPage.isCreationDateSameAsBrowserDateFormat(),
				GlobalConstants.isCreationDateSameAsBrowserDateFormat);

		verifyActionMenuOfDevicesInAdmin(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION, GlobalConstants.APPROVED, true, true, true);

		verifyActionMenuOfDevicesInAdmin(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS, GlobalConstants.AUTOMATION,
				GlobalConstants.ALPHANUMERIC, GlobalConstants.PENDING_FOR_APPROVAL, true, false, false);

		verifyActionMenuOfDevicesInAdmin(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.DEACTIVATED,
				true, false, true);

		verifyActionMenuOfDevicesInAdmin(GlobalConstants.FINGER, GlobalConstants.SLAP,
				GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.REJECTED,
				true, false, true);

		listOfDevicesPage.clickOnDevice(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION, GlobalConstants.APPROVED);

		assertTrue(viewDeviceDetailsPage.isDeviceDetailsPageTitleDisplayed(),
				GlobalConstants.isDeviceDetailsTitleDisplayed);
		viewDeviceDetailsPage.clickOnListOfDevicesBreadCumbInAdmin();
		assertTrue(listOfDevicesPage.isListOfDeviceInAdminDisplayed(), GlobalConstants.isListOfDevicesHeadingDisplayed);
		listOfDevicesPage.clickOnDevice(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS, GlobalConstants.AUTOMATION,
				GlobalConstants.ALPHANUMERIC, GlobalConstants.PENDING_FOR_APPROVAL);
		assertTrue(viewDeviceDetailsPage.isDeviceDetailsPageTitleDisplayed(),
				GlobalConstants.isDeviceDetailsTitleDisplayed);

		basePage.navigateBack();
		listOfDevicesPage.clickOnDevice(GlobalConstants.FINGER, GlobalConstants.SLAP,
				GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.REJECTED);
		assertTrue(viewDeviceDetailsPage.isDeviceDetailsPageTitleDisplayed(),
				GlobalConstants.isDeviceDetailsTitleDisplayed);

		basePage.back();
		assertTrue(listOfDevicesPage.isListOfDeviceInAdminDisplayed(), GlobalConstants.isListOfDevicesHeadingDisplayed);
		listOfDevicesPage.clickOnDevice(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE,
				GlobalConstants.DEACTIVATED);

		listOfDevicesPage.clickOnFilterButton();
		assertTrue(listOfDevicesPage.isFilterButtonDisabled(), GlobalConstants.isFilterButtonDisabled);
		assertTrue(listOfDevicesPage.isResetFilterDisplayed(), GlobalConstants.isResetFilterDisplayed);

		verifyDeviceFilterHeadersInAdmin();

		verifyDeviceFilterPlaceHodersInAdmin();

		verifyMakeModelFilterInAdmin();

		listOfDevicesPage.clickOnResetFilter();
		listOfDevicesPage.clickOnFilterButton();
		listOfDevicesPage.selectDeviceStatusFilterInAdmin(GlobalConstants.APPROVED);
		verifySortingOfListOfDevicesInAdmin();

		listOfDevicesPage.clickOnResetFilter();
		listOfDevicesPage.clickOnFilterButton();
		listOfDevicesPage.enterPartnerIdInFilterInAdmin(GlobalConstants.DEVICE_PARTNER_ID);
		listOfDevicesPage.selectDeviceTypeFilterInAdmin(GlobalConstants.FACE);
		listOfDevicesPage.clickOnApplyFilterButton();

		// required for later
//		assertEquals(listOfDevicesPage.getListOfDevicesTitle(), GlobalConstants.LIST_OF_DEVICES_TITLE_COUNT_28);
		assertTrue(authPolicyPage.isPaginationDisplayed(), GlobalConstants.isPaginationDisplayed);
		assertTrue(authPolicyPage.isPreviusPageButtonDisplayed(), GlobalConstants.isPreviusPageButtonDisplayed);
		assertTrue(authPolicyPage.isNextPageButtonDisplayed(), GlobalConstants.isNextPageButtonDisplayed);
		assertTrue(authPolicyPage.isPage1Displayed(), GlobalConstants.isPage1Displayed);
		authPolicyPage.clickOnNextPageButton();
		assertTrue(authPolicyPage.isPage2Displayed(), GlobalConstants.isPage2Displayed);
		authPolicyPage.clickOnPreviusPageButton();
		assertTrue(authPolicyPage.isPage1Displayed(), GlobalConstants.isPage1Displayed);
		assertTrue(authPolicyPage.isPrefixOfPageDisplayed(), GlobalConstants.isPrefixOfPageDisplayed);
		assertTrue(authPolicyPage.isRecordPerPageDisplayed(), GlobalConstants.isRecordPerPageDisplayed);
		assertTrue(authPolicyPage.isItemPerPage8Displayed(), GlobalConstants.isItemPerPage8Displayed);
		assertTrue(authPolicyPage.isexpandIconDisplayed(), GlobalConstants.isexpandIconDisplayed);
		authPolicyPage.selectItemPerPageNumber();
		assertTrue(authPolicyPage.isItemPerPage16Displayed(), GlobalConstants.isItemPerPage16Displayed);

	}

	@Test(priority = 8, description = "Verify View Of Devices Page As Admin", dependsOnMethods = "verifyTabularViewOfDevicesPageAsAdmin")
	public void verifyViewOfDevicesPageAsAdmin() {
		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);
		viewDeviceDetailsPage = new ViewDeviceDetailsPage(driver);

		dashboardpage.clickOnSbiDevices();
		listOfSbiPage.clickOnDeviceTab();
		listOfDevicesPage.clickOnDevice(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION, GlobalConstants.APPROVED);
		assertTrue(viewDeviceDetailsPage.isDeviceDetailsPageTitleDisplayed(),
				GlobalConstants.isDeviceDetailsTitleDisplayed);
		assertEquals(viewDeviceDetailsPage.getBreadcrumbTextOfDeviceDetailsInAdmin(),
				GlobalConstants.DEVICE_DETAIL_PAGE_BREADCUMB2);
		assertTrue(viewDeviceDetailsPage.isApprovedStatusDisplayed(), GlobalConstants.isStatusDisplayed);
		verifyDeviceDetailsAsAdmin(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION, GlobalConstants.ORGANISATION_NAME);

		viewDeviceDetailsPage.clickOnListOfDevicesBreadCumbInAdmin();
		verifyDeviceStatusInDeviceDetailsAsAdmin(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS,
				GlobalConstants.AUTOMATION, GlobalConstants.ALPHANUMERIC, GlobalConstants.PENDING_FOR_APPROVAL);
		verifyDeviceStatusInDeviceDetailsAsAdmin(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE,
				GlobalConstants.DEACTIVATED);
		verifyDeviceStatusInDeviceDetailsAsAdmin(GlobalConstants.FINGER, GlobalConstants.SLAP,
				GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.REJECTED);

	}

	/**
	 * TC_38189_23: a device that is Pending for Approval but linked to no SBI must
	 * offer Reject only, so the admin cannot approve an orphaned device.
	 *
	 * Devices created through the portal are always linked to an SBI, so this needs
	 * an orphaned device already present in the environment. When the list holds
	 * none the test skips rather than reporting a pass it did not earn.
	 */
	@Test(priority = 9, description = "Only Reject is offered for a device that is not linked to an SBI", dependsOnMethods = "verifyViewOfDevicesPageAsAdmin")
	public void verifyRejectOnlyOptionForOrphanDevice() {
		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);

		dashboardpage.clickOnSbiDevices();
		listOfSbiPage.clickOnDeviceTab();
		assertTrue(listOfDevicesPage.isListOfDevicesTitleDisplayed(), GlobalConstants.isListOfDevicesTitleDisplayed);

		int orphanRow = listOfDevicesPage.findOrphanPendingDeviceRow();
		if (orphanRow < 0) {
			throw new SkipException("No Pending for Approval device without a linked SBI exists in this "
					+ "environment, so the reject-only popup of TC_38189_23 cannot be exercised. Seed an "
					+ "orphaned device to enable this check.");
		}

		// Precondition 2: the linked SBI column carries no value for this device
		assertTrue(listOfDevicesPage.isLinkedSbiColumnEmpty(orphanRow), GlobalConstants.isLinkedSbiColumnEmpty);

		listOfDevicesPage.clickOnDeviceListActionMenu(orphanRow);
		listOfDevicesPage.clickOnApproveOrReject();

		// The reject-only popup replaces the usual approve/reject popup
		assertTrue(listOfDevicesPage.isRejectOnlyPopupDisplayed(), GlobalConstants.isRejectOnlyPopupDisplayed);
		assertTrue(listOfDevicesPage.isRejectOnlyPopupHeaderDisplayed(), GlobalConstants.isRejectOnlyPopupDisplayed);
		assertTrue(listOfDevicesPage.isRejectOnlyPopupDescriptionDisplayed(),
				GlobalConstants.isRejectOnlyPopupDescriptionDisplayed);
		assertTrue(listOfDevicesPage.isRejectOnlyPopupRejectButtonDisplayed(),
				GlobalConstants.isRejectOnlyPopupRejectButtonDisplayed);

		// The point of the popup: no Approve is on offer
		assertTrue(listOfDevicesPage.isApproveButtonAbsentInRejectOnlyPopup(),
				GlobalConstants.isApproveButtonNotDisplayedForOrphanDevice);

		listOfDevicesPage.clickOnRejectOnlyPopupCloseIcon();
	}

	private void loginAsDeviceProvider() {
		dashboardpage.clickOnProfileDropdown();
		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		loginpage.enterUserName(GlobalConstants.DEVICE_PARTNER_ID);
		loginpage.enterPassword(password);
		loginpage.clickOnLoginButton();
	}

	private void fillSbiDetails(String sbiVersion, String sbiBinaryHash) {
		deviceProviderPage.clickOnAddSbiFromSbiListButton();
		deviceProviderPage.clickOnAddSbiPartnerIdBox();
		deviceProviderPage.clickOnPartnerOption();
		assertEquals(deviceProviderPage.getPartnerType(), GlobalConstants.DEVICE_PROVIDER);
		deviceProviderPage.enterSbiVersion(sbiVersion);
		deviceProviderPage.enterSbiBinaryHash(sbiBinaryHash);
		assertTrue(deviceProviderPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
	}

	private void verifySbiDetails(String sbiVersion) {
		listOfSbiPage.clickOnSbiListArrow(sbiVersion);
		assertTrue(listOfSbiPage.isPartnerIdTextDisplayed(), GlobalConstants.isSbiDetailsAreDisplayed);
		assertTrue(listOfSbiPage.isPartnerIdValueDisplayed(), GlobalConstants.isSbiDetailsAreDisplayed);
		assertTrue(listOfSbiPage.isPartnerTypeTextDisplayed(), GlobalConstants.isSbiDetailsAreDisplayed);
		assertTrue(listOfSbiPage.isPartnerTypeValueDisplayed(), GlobalConstants.isSbiDetailsAreDisplayed);
		assertTrue(listOfSbiPage.isSubmittedOnTextDisplayed(), GlobalConstants.isSbiDetailsAreDisplayed);
		assertTrue(listOfSbiPage.isSbiCreationDateTextDisplayed(), GlobalConstants.isSbiDetailsAreDisplayed);
		assertTrue(listOfSbiPage.isSbiExpirationDateTextDisplayed(), GlobalConstants.isSbiDetailsAreDisplayed);
		assertTrue(listOfSbiPage.isSubmittedOnDateDisplayed(), GlobalConstants.isSbiDetailsAreDisplayed);
		if (GlobalConstants.ALPHANUMERIC.equals(sbiVersion)) {
			assertTrue(listOfSbiPage.isPreviousMonthSbiCreationDateDisplayed(),
					GlobalConstants.isSbiDetailsAreDisplayed);
		} else {
			assertTrue(listOfSbiPage.isSbiCreationDateDisplayed(), GlobalConstants.isSbiDetailsAreDisplayed);
		}
		assertTrue(listOfSbiPage.isSbiExpirationDateDisplayed(), GlobalConstants.isSbiDetailsAreDisplayed);
		listOfSbiPage.clickOnSbiListArrow(sbiVersion);
	}

	private void verifyListOfDevicesHeaders() {
		assertTrue(listOfDevicesPage.isDeviceIdHeaderDisplayed(), GlobalConstants.isListOfDevicesHeadersDisplayed);
		assertTrue(listOfDevicesPage.isDeviceTypeHeaderDisplayed(), GlobalConstants.isListOfDevicesHeadersDisplayed);
		assertTrue(listOfDevicesPage.isDeviceSubTypeHeaderDisplayed(), GlobalConstants.isListOfDevicesHeadersDisplayed);
		assertTrue(listOfDevicesPage.isMakeHeaderDisplayed(), GlobalConstants.isListOfDevicesHeadersDisplayed);
		assertTrue(listOfDevicesPage.isModelHeaderDisplayed(), GlobalConstants.isListOfDevicesHeadersDisplayed);
		assertTrue(listOfDevicesPage.isCreationDateHeaderDisplayed(), GlobalConstants.isListOfDevicesHeadersDisplayed);
		assertTrue(listOfDevicesPage.isStatusHeaderDisplayed(), GlobalConstants.isListOfDevicesHeadersDisplayed);
		assertTrue(listOfDevicesPage.isActionHeaderDisplayed(), GlobalConstants.isListOfDevicesHeadersDisplayed);
	}

	private void verifyDeactivateSbiIsEnabled(String sbiVersion, boolean status) {
		listOfSbiPage.clickOnThreeDotsOfSbiList(sbiVersion);
		assertEquals(listOfSbiPage.isDeactivateOptionEnabled(), status);
	}

	private void addSbi(String sbiVersion, String sbiBinaryHash) {
		fillSbiDetails(sbiVersion, sbiBinaryHash);
		deviceProviderPage.enterExpiryDate();
		deviceProviderPage.clickOnSubmit();
	}

	private void verifySortingOfListOfDevicesInAdmin() {
		basePage = new BasePage(driver);

		assertTrue(listOfDevicesPage.isPartnerIdAscIconInAdminDisplayed(), GlobalConstants.isPartnerIdAscIconDisplayed);
		assertTrue(listOfDevicesPage.isPartnerIdDescIconInAdminDisplayed(),
				GlobalConstants.isPartnerIdDescIconDisplayed);
		assertTrue(listOfDevicesPage.isOrgNameAscIconInAdminDisplayed(), GlobalConstants.isOrgNameAscIconDisplayed);
		assertTrue(listOfDevicesPage.isOrgNameDescIconInAdminDisplayed(), GlobalConstants.isOrgNameDescIconDisplayed);
		assertTrue(listOfDevicesPage.isSbiIdAscIconInAdminDisplayed(), GlobalConstants.isSbiIdAscIconDisplayed);
		assertTrue(listOfDevicesPage.isSbiIdDescIconInAdminDisplayed(), GlobalConstants.isSbiIdDescIconDisplayed);
		assertTrue(listOfDevicesPage.isSbiVersionAscIconInAdminDisplayed(),
				GlobalConstants.isSbiVersionAscIconDisplayed);
		assertTrue(listOfDevicesPage.isSbiVersionDescIconInAdminDisplayed(),
				GlobalConstants.isSbiVersionDescIconDisplayed);
		assertTrue(listOfDevicesPage.isDeviceIdAscIconInAdminDisplayed(), GlobalConstants.isDeviceIdAscIconDisplayed);
		assertTrue(listOfDevicesPage.isDeviceIdDescIconInAdminDisplayed(), GlobalConstants.isDeviceIdDescIconDisplayed);
		assertTrue(listOfDevicesPage.isDeviceTypeAscIconInAdminDisplayed(),
				GlobalConstants.isDeviceTypeCodeAscIconDisplayed);
		assertTrue(listOfDevicesPage.isDeviceTypeDescIconInAdminDisplayed(),
				GlobalConstants.isDeviceTypeCodeDescIconDisplayed);
		assertTrue(listOfDevicesPage.isDeviceSubTypeAscIconInAdminDisplayed(),
				GlobalConstants.isDeviceSubTypeCodeAscIconDisplayed);
		assertTrue(listOfDevicesPage.isDeviceSubTypeDescIconInAdminDisplayed(),
				GlobalConstants.isDeviceSubTypeCodeDescIconDisplayed);
		assertTrue(listOfDevicesPage.isMakeAscIconInAdminDisplayed(), GlobalConstants.isMakeAscIconDisplayed);
		assertTrue(listOfDevicesPage.isMakeDescIconInAdminDisplayed(), GlobalConstants.isMakeDescIconDisplayed);
		assertTrue(listOfDevicesPage.isModelAscIconInAdminDisplayed(), GlobalConstants.isModelAscIconDisplayed);
		assertTrue(listOfDevicesPage.isModelDescIconInAdminDisplayed(), GlobalConstants.isModelDescIconDisplayed);
		assertTrue(listOfDevicesPage.isCreatedDateTimeAscIconInAdminDisplayed(),
				GlobalConstants.isCreatedDateTimeAscIconDisplayed);
		assertTrue(listOfDevicesPage.isCreatedDateTimeDescIconInAdminDisplayed(),
				GlobalConstants.isCreatedDateTimeDescIconDisplayed);
		assertTrue(listOfDevicesPage.isStatusAscIconInAdminDisplayed(), GlobalConstants.isStatusAscIconDisplayed);
		assertTrue(listOfDevicesPage.isStatusDescIconInAdminDisplayed(), GlobalConstants.isStatusDescIconDisplayed);

		basePage.scrollToEndPage();
		basePage.scrollToStartPage();
		listOfDevicesPage.clickOnPartnerIdAscIconInAdmin();
		listOfDevicesPage.clickOnPartnerIdDescIconInAdmin();
		listOfDevicesPage.clickOnOrgNameAscIconInAdmin();
		listOfDevicesPage.clickOnOrgNameDescIconInAdmin();
		listOfDevicesPage.clickOnDeviceIdAscIconInAdmin();
		listOfDevicesPage.clickOnDeviceIdDescIconInAdmin();
		listOfDevicesPage.clickOnDeviceSubTypeAscIconInAdmin();
		listOfDevicesPage.clickOnDeviceSubTypeDescIconInAdmin();
		listOfDevicesPage.clickOnMakeAscIconInAdmin();
		listOfDevicesPage.clickOnMakeDescIconInAdmin();
		listOfDevicesPage.clickOnModelAscIconInAdmin();
		listOfDevicesPage.clickOnModelDescIconInAdmin();
		listOfDevicesPage.clickOnCreatedDateTimeAscIconInAdmin();
		listOfDevicesPage.clickOnCreatedDateTimeDescIconInAdmin();
		listOfDevicesPage.clickOnStatusAscIconInAdmin();
		listOfDevicesPage.clickOnStatusDescIconInAdmin();

	}

	private void verifyViewOptionIsEnabled(String sbiVersion, boolean status) {
		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(sbiVersion);
		assertEquals(listOfSbiPage.isViewOptionEnabled(), status);
		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(sbiVersion);
	}

	private void verifyApproveOrRejectSbiIsEnabled(String sbiVersion, boolean status) {
		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(sbiVersion);
		assertEquals(listOfSbiPage.isApproveRejectOptionEnabled(), status);
		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(sbiVersion);
	}

	private void verifySbiDetailsAsAdmin(String sbiVersion) {
		assertTrue(viewSbiDetailsPage.isCreatedDateDisplayed(), GlobalConstants.isCreatedDateTextDisplayed);
		assertEquals(viewSbiDetailsPage.getPartnerIdText(), GlobalConstants.DEVICE_PARTNER_ID);
		assertEquals(viewSbiDetailsPage.getPartnerTypeText(), GlobalConstants.DEVICE_PROVIDER);
		assertEquals(viewSbiDetailsPage.getOrganisationText(), GlobalConstants.ORGANISATION_NAME);
		assertEquals(viewSbiDetailsPage.getLinkedDeviceText(), GlobalConstants.Linked_Device38);
		assertEquals(viewSbiDetailsPage.getSbiVersionText(), sbiVersion);
		assertTrue(viewSbiDetailsPage.isSbiCreationDateSameAsBrowserDateFormat(),
				GlobalConstants.isSbiCreationDateSameAsBrowserDateFormat);
		assertTrue(viewSbiDetailsPage.isSbiExpirationDateSameAsBrowserDateFormat(),
				GlobalConstants.isSbiExpirationDateSameAsBrowserDateFormat);
		viewSbiDetailsPage.clickOnTitleBackIcon();
	}

	private void verifySbiStatusInDetailsPage(String sbiVersion, String status) {
		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(sbiVersion);
		listOfSbiPage.clickOnSbiViewButton();

		switch (status) {
		case "Approved":
			assertTrue(viewSbiDetailsPage.isApprovedStatusDisplayed(), GlobalConstants.isStatusDisplayed);
			break;
		case "Pending For Approval":
			assertTrue(viewSbiDetailsPage.isPendingForApprovalStatusDisplayed(), GlobalConstants.isStatusDisplayed);
			break;
		case "Deactivated":
			assertTrue(viewSbiDetailsPage.isDeactivatedStatusDisplayed(), GlobalConstants.isStatusDisplayed);
			break;
		case "Rejected":
			assertTrue(viewSbiDetailsPage.isRejectedStatusDisplayed(), GlobalConstants.isStatusDisplayed);
			break;
		default:
			logger.info("Status is not matching, please check the status");
		}
		viewSbiDetailsPage.clickOnSbiDetailsBackButton();
	}

	private void verifyLinkedDeviceLinkInSbiDetailsPage(String sbiVersion) {
		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(sbiVersion);
		listOfSbiPage.clickOnSbiViewButton();
		viewSbiDetailsPage.clickOnLinkedDevices();
		assertTrue(listOfSbiPage.isLinkedDevicesListDisplayed(), GlobalConstants.isLinkedDevicesListDisplayed);
		listOfSbiPage.clickOnBackIconOfLinkedDevices();
	}

	private void verifyLinkedDevicesOfApprovededSbiInList(String sbiVersion, String status) {
		assertTrue(listOfSbiPage.isLinkedDevicePresentForStatus("Approved", "38"), GlobalConstants.Linked_Device38);
		listOfSbiPage.clickOnLinkedDevicesInSbiList("Approved", "38");
		assertTrue(listOfSbiPage.isLinkedDevicesListDisplayed(), GlobalConstants.isLinkedDevicesListDisplayed);
		listOfSbiPage.clickOnBackIconOfLinkedDevices();
	}

	private void verifyLinkedDevicesOfDeactivatedSbiInList(String sbiVersion, String status) {
		assertTrue(listOfSbiPage.isLinkedDevicePresentForStatus("Deactivated", "6"), GlobalConstants.Linked_Device6);
		listOfSbiPage.clickOnLinkedDevicesInSbiList("Deactivated", "6");
		assertTrue(listOfSbiPage.isLinkedDevicesListDisplayed(), GlobalConstants.isLinkedDevicesListDisplayed);
		listOfSbiPage.clickOnBackIconOfLinkedDevices();
	}

	private void verifyLinkedDevicesOfPendingForApprovalSbiInList(String sbiVersion, String status) {
		assertTrue(listOfSbiPage.isLinkedDevicePresentForStatus("Pending For Approval", "0"),
				GlobalConstants.Linked_Device0);
		listOfSbiPage.clickOnLinkedDevicesInSbiList("Pending For Approval", "0");
		assertFalse(listOfSbiPage.isLinkedDevicesListDisplayed(), GlobalConstants.isLinkedDevicesListDisplayed);
	}

	private void verifyLinkedDevicesOfRejectedSbiInList(String sbiVersion, String status) {
		assertTrue(listOfSbiPage.isLinkedDevicePresentForStatus("Rejected", "0"), GlobalConstants.Linked_Device0);
		listOfSbiPage.clickOnLinkedDevicesInSbiList("Rejected", "0");
		assertFalse(listOfSbiPage.isLinkedDevicesListDisplayed(), GlobalConstants.isLinkedDevicesListDisplayed);
	}

	private void verifyDeviceDetailsAsAdmin(String deviceType, String deviceSubType, String make, String model,
			String orgName) {
		assertTrue(viewDeviceDetailsPage.isCreatedDateDisplayed(), GlobalConstants.isCreatedDateTextDisplayed);
		assertTrue(viewDeviceDetailsPage.isCreationDateInAdminSameAsBrowserDateFormat(),
				GlobalConstants.isDeviceCreationDateSameAsBrowserDateFormat);
		assertEquals(viewDeviceDetailsPage.getMakeContextInAdmin(), make);
		assertEquals(viewDeviceDetailsPage.getModelContextInAdmin(), model);
		assertEquals(viewDeviceDetailsPage.getPartnerIdContextInAdmin(), GlobalConstants.DEVICE_PARTNER_ID);
		assertEquals(viewDeviceDetailsPage.getPartnerTypeContextInAdmin(), GlobalConstants.DEVICE_PROVIDER);
		assertEquals(viewDeviceDetailsPage.getDeviceTypeContextInAdmin(), deviceType);
		assertEquals(viewDeviceDetailsPage.getDeviceSubTypeContextInAdmin(), deviceSubType);
		assertEquals(viewDeviceDetailsPage.getOrgNameContextInAdmin(), orgName);
		assertTrue(viewDeviceDetailsPage.isSbiIdContextInAdminDisplayed(), GlobalConstants.isSbiIdContextDisplayed);
		assertTrue(viewDeviceDetailsPage.isDeviceIdContextInAdminDisplayed(),
				GlobalConstants.isDeviceIdContextDisplayed);
		assertTrue(viewDeviceDetailsPage.isBackButtonInAdminDeviceDetailsDisplayed(), GlobalConstants.isBackButton);
	}

	private void verifyDeviceStatusInDeviceDetailsAsAdmin(String deviceType, String deviceSubType, String make,
			String model, String status) {
		listOfDevicesPage.clickOnDeviceThreeDotsInAdmin(deviceType, deviceSubType, make, model);
		listOfDevicesPage.clickOnViewDeviceOfTabularInAdmin();
		switch (status) {
		case "Approved":
			assertTrue(viewDeviceDetailsPage.isApprovedStatusDisplayed(), GlobalConstants.isStatusDisplayed);
			break;
		case "Pending For Approval":
			assertTrue(viewDeviceDetailsPage.isPendingForApprovalStatusDisplayed(), GlobalConstants.isStatusDisplayed);
			break;
		case "Deactivated":
			assertTrue(viewDeviceDetailsPage.isDeactivatedStatusDisplayed(), GlobalConstants.isStatusDisplayed);
			break;
		case "Rejected":
			assertTrue(viewDeviceDetailsPage.isRejectedStatusDisplayed(), GlobalConstants.isStatusDisplayed);
			break;
		default:
			logger.info("Status is not matching, please check the status");
		}
		viewDeviceDetailsPage.clickOnBackButtonInAdminDeviceDetails();
	}

	private void verifyActionMenuOfDevicesInAdmin(String deviceType, String deviceSubType, String make, String model,
			String status, boolean status1, boolean status2, boolean status3) {
		listOfDevicesPage.clickOnDeviceThreeDots(deviceType, deviceSubType, make, model);
		assertEquals(listOfDevicesPage.isViewOptionDisplayed(), status1);
		assertEquals(listOfDevicesPage.isDeactivateDeviceEnabledInAdmin(), status2);
		assertEquals(listOfDevicesPage.isApproceRejectDeviceDisabled(), status3);
		listOfDevicesPage.clickOnDeviceThreeDots(deviceType, deviceSubType, make, model);
	}

	private void verifyDeviceFilterHeadersInAdmin() {
		assertTrue(listOfDevicesPage.isFilterHeaderDisplayed(GlobalConstants.PARTNER_ID),
				GlobalConstants.isFilterDevicesHeaderDisplayed);
		assertTrue(listOfDevicesPage.isFilterHeaderDisplayed(GlobalConstants.ORGANISATION),
				GlobalConstants.isFilterDevicesHeaderDisplayed);
		assertTrue(listOfDevicesPage.isFilterHeaderDisplayed(GlobalConstants.SBI_ID),
				GlobalConstants.isFilterDevicesHeaderDisplayed);
		assertTrue(listOfDevicesPage.isFilterHeaderDisplayed(GlobalConstants.SBI_VERSION),
				GlobalConstants.isFilterDevicesHeaderDisplayed);
		assertTrue(listOfDevicesPage.isFilterHeaderDisplayed(GlobalConstants.DEVICE_ID),
				GlobalConstants.isFilterDevicesHeaderDisplayed);
		assertTrue(listOfDevicesPage.isFilterHeaderDisplayed(GlobalConstants.DEVICE_TYPE),
				GlobalConstants.isFilterDevicesHeaderDisplayed);
		assertTrue(listOfDevicesPage.isFilterHeaderDisplayed(GlobalConstants.DEVICE_SUB_TYPE),
				GlobalConstants.isFilterDevicesHeaderDisplayed);
		assertTrue(listOfDevicesPage.isFilterHeaderDisplayed(GlobalConstants.MAKE),
				GlobalConstants.isFilterDevicesHeaderDisplayed);
		assertTrue(listOfDevicesPage.isFilterHeaderDisplayed(GlobalConstants.MODEL),
				GlobalConstants.isFilterDevicesHeaderDisplayed);
		assertTrue(listOfDevicesPage.isFilterHeaderDisplayed(GlobalConstants.STATUS),
				GlobalConstants.isFilterDevicesHeaderDisplayed);
	}

	private void verifyDeviceFilterPlaceHodersInAdmin() {
		assertTrue(listOfDevicesPage.isDeviceIdPlaceHolderInAdminDisplayed(),
				GlobalConstants.isDeviceIdPlaceHolderDisplayed);
		assertTrue(listOfDevicesPage.isDeviceTypePlaceHolderInAdminDisplayed(),
				GlobalConstants.isDeviceTypePlaceHolderDisplayed);
		assertTrue(listOfDevicesPage.isDeviceSubTypePlaceHolderInAdminDisplayed(),
				GlobalConstants.isDeviceSubTypePlaceHolderDisplayed);
		assertTrue(listOfDevicesPage.isMakePlaceHolderInAdminDisplayed(), GlobalConstants.isMakePlaceHolderDisplayed);
		assertTrue(listOfDevicesPage.isModelPlaceHolderInAdminDisplayed(), GlobalConstants.isModelPlaceHolderDisplayed);
		assertTrue(listOfDevicesPage.isStatusPlaceHolderInAdminDisplayed(),
				GlobalConstants.isStatusPlaceHolderDisplayed);
		assertTrue(listOfDevicesPage.isPartneIdPlaceHolderInAdminDisplayed(),
				GlobalConstants.isPartnerIdPlaceHolderDisplayed);
		assertTrue(listOfDevicesPage.isOrganisationPlaceHolderInAdminDisplayed(),
				GlobalConstants.isOrganisationPlaceHolderDisplayed);
		assertTrue(listOfDevicesPage.isSbiIdPlaceHolderInAdminDisplayed(), GlobalConstants.isSbiIdPlaceHolderDisplayed);
		assertTrue(listOfDevicesPage.isSbiVersionPlaceHolderInAdminDisplayed(),
				GlobalConstants.isSbiVersionPlaceHolderDisplayed);
	}

	private void verifyMakeModelFilterInAdmin() {
		listOfDevicesPage.enterInvalidValueInDeviceMakeFilterInAdmin(GlobalConstants.INVALID_DATA);
		listOfDevicesPage.enterInvalidValueInDeviceModelFilterInAdmin(GlobalConstants.INVALID_DATA);
		listOfDevicesPage.clickOnApplyFilterButton();
		assertTrue(listOfDevicesPage.isNoResultsFoundInAdminDisplayed(), GlobalConstants.isNoResultsFoundDisplayed);
	}

}
