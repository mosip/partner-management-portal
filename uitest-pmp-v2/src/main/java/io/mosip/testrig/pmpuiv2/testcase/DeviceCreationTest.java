package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
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
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "SbiCreationTest" }, groups = { "DeviceCreationTest" })
public class DeviceCreationTest extends BaseClass {
	private DeviceProviderPage deviceProviderPage;
	private DashboardPage dashboardpage;
	private ListOfSbiPage listOfSbiPage;
	private AddDevicePage addDevicePage;
	private ListOfDevicesPage listOfDevicesPage;
	private ViewDeviceDetailsPage viewDeviceDetailsPage;
	private BasePage basePage;
	private AuthPolicyPage authPolicyPage;

	@Test(priority = 1, description = "Add and verify device for SBI's")
	public void addAndVerifyDeviceInSbi() throws Exception {
		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);
		addDevicePage = new AddDevicePage(driver);
		basePage = new BasePage(driver);

		loginAsDeviceProvider();

		deviceProviderPage = dashboardpage.clickOnDeviceProviderServicesTitle();

		listOfSbiPage.clickOnAddDeviceButtonForSbi(GlobalConstants.ALPHANUMERIC);

		Assert.assertTrue(addDevicePage.isAddDeviceTitleDisplayed(), "Add Device page did not load");
		assertTrue(addDevicePage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertEquals(addDevicePage.getSubTitle(), GlobalConstants.LIST_OF_SBI);

		fillDeviceDetails(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION_TEMP,
				GlobalConstants.AUTOMATION_TEMP);
		addDevicePage.clickOnSubmit();
		assertTrue(addDevicePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		addDevicePage.closeSuccessMessage();
		assertTrue(addDevicePage.isDeviceTypeDisabled(), GlobalConstants.isDeviceTypeDisabled);
		addDevicePage.clickOnBackToListOfSbiButton();

		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.ALPHANUMERIC);
		assertTrue(listOfDevicesPage.isPartnerIdInFirstColumnInPartnerDisplayed(),
				GlobalConstants.isPartnerIdInFirstColoumnDisplayed);
		listOfDevicesPage.clickOnAddDeviceButtonFromDeviceList();
		addMultipleDevices(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION_DEACTIVATING,
				GlobalConstants.AUTOMATION_DEACTIVATING, 5);
		addDevicePage.clickOnBackToDevices();
		listOfDevicesPage.clickOnListOfSbiButton();

		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.AUTOMATION);
		assertEquals(listOfSbiPage.getListOfSbiMessage(), GlobalConstants.LIST_OF_SBI_PAGE_MESSAGE);
		verifyListOfDevicesHeadersBeforeAddingDevices();
		assertTrue(listOfDevicesPage.isAddDeviceButtonEnabled(), GlobalConstants.isAddDeviceButtonEnabled);
		listOfDevicesPage.clickOnAddDeviceButton();
		verifyAddDeviceLabelsAndPageDetails();
		assertTrue(addDevicePage.isMandatoryMessageDisplayed(), GlobalConstants.isMandatoryMessageDisplayed);

		verifyInitialDevicePage();

		addDevicePage.clickOnClear();
		assertEquals(addDevicePage.getDeviceTypeValue(), GlobalConstants.INITIAL_DEVICE_TYPE);

		addDevicePage.clickOnDeviceType();
		assertTrue(addDevicePage.isDeviceTypeOptionDisplayed(), GlobalConstants.isDeviceTypeOptionsDisplayed);
		addDevicePage.clickOnDeviceType();
		assertEquals(addDevicePage.isDeviceTypeOptionDisplayed(), false);

		addDevicePage.selectAddDeviceType(GlobalConstants.FACE);
		assertEquals(addDevicePage.getDeviceTypeValue(), GlobalConstants.FACE);
		assertTrue(addDevicePage.isDeviceSubTypeEnabled(), GlobalConstants.isDeviceSubTypeEnabled);
		addDevicePage.clickOnDeviceSubType();
		assertTrue(addDevicePage.isDeviceSubTypeOptionDisplayed(), GlobalConstants.isDeviceSubTypeOptionsDisplayed);
		addDevicePage.clickOnDeviceSubType();
		assertEquals(addDevicePage.isDeviceSubTypeOptionDisplayed(), false);
		addDevicePage.selectDeviceSubType(GlobalConstants.FULL_FACE);
		assertTrue(addDevicePage.isSubmitDisabled(), GlobalConstants.isSubmitButtonDisabled);

		fillDeviceDetails(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION);
		addDevicePage.clickOnClear();
		assertEquals(addDevicePage.getDeviceTypeValue(), GlobalConstants.INITIAL_DEVICE_TYPE);
		addDevicePage.enterMakeName(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(deviceProviderPage.isSpecialCharacterErrorMessageDisplayed(),
				GlobalConstants.isSpecialCharacterErrorMessageDisplayed);
		addDevicePage.clickOnClear();
		addDevicePage.enterModelName(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(deviceProviderPage.isSpecialCharacterErrorMessageDisplayed(),
				GlobalConstants.isSpecialCharacterErrorMessageDisplayed);
		addDevicePage.clickOnClear();

		fillDeviceDetails(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION);
		addDevicePage.reload();
//		assertEquals(deviceProviderPage.getAlertText(), GlobalConstants.RELOAD_MESSAGE);
//		addDevicePage.cancelAlert();
		assertEquals(addDevicePage.getDeviceTypeValue(), GlobalConstants.INITIAL_DEVICE_TYPE);

		addMultipleDevices(GlobalConstants.IRIS, GlobalConstants.SINGLE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION, 1);
		verifyAddDeviceLabelsAndPageDetails();
		assertTrue(addDevicePage.isMandatoryMessageDisplayed(), GlobalConstants.isMandatoryMessageDisplayed);
		assertTrue(addDevicePage.isDeleteButtonEnabled(), GlobalConstants.isDeleteButtonEnabled);

		fillDeviceDetailsWithPosition(GlobalConstants.IRIS, GlobalConstants.SINGLE, GlobalConstants.AUTOMATION_2,
				GlobalConstants.AUTOMATION_2, 2);
		addDevicePage.clickOnDeleteButton();
		addDevicePage.clickOnBackToDevices();
		assertEquals(listOfDevicesPage.isDeviceDisplayed(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_2, GlobalConstants.AUTOMATION_2), false);

		listOfDevicesPage.clickOnAddDeviceButtonFromDeviceList();
		addMultipleDevices(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION, 25);
		assertEquals(addDevicePage.getMaximumDeviceAlert(), GlobalConstants.MAXIMUM_DEVICE_ALERT_MESSAGE);
		addDevicePage.clickOnCancel();
		verifyAddDeviceLabelsAndPageDetails();
		assertTrue(addDevicePage.isTwentyFifthDeviceIsDisplayed(), GlobalConstants.isCreatedTwentyFifthDeviceDisplayed);
		addDevicePage.clickOnAddDevice();
		addDevicePage.clickOnConfirm();
		verifyAddDeviceLabelsAndPageDetails();
		assertTrue(addDevicePage.isMandatoryMessageDisplayed(), GlobalConstants.isMandatoryMessageDisplayed);
		verifyInitialDevicePage();
		addDevicePage.clickOnBackToDevices();
		assertTrue(
				listOfDevicesPage.isDeviceDisplayed(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
						GlobalConstants.AUTOMATION_25, GlobalConstants.AUTOMATION_25),
				GlobalConstants.isCreatedTwentyFifthDeviceDisplayed);

		listOfDevicesPage.clickOnAddDeviceButtonFromDeviceList();
		addDevices(GlobalConstants.FINGER, GlobalConstants.SINGLE, GlobalConstants.CHARACTERS_36,
				GlobalConstants.CHARACTERS_36);
		addDevices(GlobalConstants.FINGER, GlobalConstants.SLAP, GlobalConstants.CHARACTERS_1,
				GlobalConstants.CHARACTERS_1);
		addDevices(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS, GlobalConstants.ALPHANUMERIC,
				GlobalConstants.ALPHANUMERIC);
		addDevices(GlobalConstants.IRIS, GlobalConstants.SINGLE, GlobalConstants.ALPHANUMERIC_AND_SYMBOLS,
				GlobalConstants.ALPHANUMERIC_AND_SYMBOLS);
		addDevices(GlobalConstants.IRIS, GlobalConstants.DOUBLE, GlobalConstants.SINGLE_NUMERIC,
				GlobalConstants.SINGLE_NUMERIC);
		addDevices(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION_UPPERCASE,
				GlobalConstants.AUTOMATION_UPPERCASE);
		addDevices(GlobalConstants.IRIS, GlobalConstants.SINGLE, GlobalConstants.AUTOMATION_LOWERCASE,
				GlobalConstants.AUTOMATION_LOWERCASE);
		addDevices(GlobalConstants.FINGER, GlobalConstants.SLAP, GlobalConstants.AUTOMATION_REJECTING,
				GlobalConstants.AUTOMATION_REJECTING);
		addDevices(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS, GlobalConstants.ALPHANUMERIC,
				GlobalConstants.AUTOMATION);
		addDevices(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS, GlobalConstants.AUTOMATION,
				GlobalConstants.ALPHANUMERIC);
		addDevices(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.CHARACTER_WITH_SPACE,
				GlobalConstants.CHARACTER_WITH_SPACE);

		fillDeviceDetails(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.CHARACTER_WITH_SPACE,
				GlobalConstants.CHARACTER_WITH_SPACE);
		addDevicePage.clickOnSubmit();
		assertEquals(addDevicePage.getDuplicateDeviceErrorMessage(), GlobalConstants.DUPLICATE_DEVICE_ERROR_MESSAGE);
		addDevicePage.clickOnDeleteButton();
		fillDeviceDetails(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION);
		addDevicePage.clickOnSubmit();
		assertTrue(addDevicePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		addDevicePage.closeSuccessMessage();

		addDevicePage.clickOnBackToDevices();
		listOfDevicesPage.clickOnAddDeviceButtonFromDeviceList();
		fillDeviceDetails(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.CHARACTER_WITH_SPACE,
				GlobalConstants.CHARACTER_WITH_SPACE);
		addDevicePage.clickOnBackToDevices();
		addDevicePage.clickOnProceed();

		listOfDevicesPage.clickOnHome();
		assertTrue(dashboardpage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

	}

	@Test(priority = 2, description = "Approve and reject the devices as admin", dependsOnMethods = "addAndVerifyDeviceInSbi")
	public void approveAndRejectDevices() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);

		dashboardpage.clickOnSbiDevices();
		listOfSbiPage.clickOnDeviceTab();

		assertTrue(listOfDevicesPage.isPartnerIdInFirstColumnDisplayed(),
				GlobalConstants.isPartnerIdInFirstColoumnDisplayed);
		listOfDevicesPage.clickOnDeviceThreeDotsAsAdmin(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION, GlobalConstants.AUTOMATION);
		verifyOptionsInActionMenuAsAdmin();
		listOfDevicesPage.clickOnApproveOrReject();
		listOfDevicesPage.clickOnApprove();

		listOfDevicesPage.clickOnDeviceThreeDotsAsAdmin(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE);
		listOfDevicesPage.clickOnApproveOrReject();
		listOfDevicesPage.clickOnApprove();

		listOfDevicesPage.clickOnDeviceThreeDotsAsAdmin(GlobalConstants.FINGER, GlobalConstants.SLAP,
				GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.AUTOMATION_REJECTING);
		listOfDevicesPage.clickOnApproveOrReject();
		listOfDevicesPage.clickOnReject();

		listOfDevicesPage.clickOnDeviceThreeDotsAsAdmin(GlobalConstants.FINGER, GlobalConstants.SLAP,
				GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.AUTOMATION_REJECTING);
		listOfDevicesPage.isApproceRejectDeviceDisabled();

		listOfDevicesPage.selectMaxRecordsPerPage();
		listOfDevicesPage.clickOnFilterButton();
		listOfDevicesPage.applyMakeFilter(GlobalConstants.AUTOMATION_DEACTIVATING);

		listOfDevicesPage.clickOnFilteredDeviceEllipsisButtonAsAdmin(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_1, GlobalConstants.AUTOMATION_DEACTIVATING_1);
		listOfDevicesPage.clickOnApproveOrReject();
		listOfDevicesPage.isApproceRejectpopupDisplayed();
		listOfDevicesPage.isApprovePopupTitleDisplayed();
		listOfDevicesPage.isApprovePopupSubTitleDisplayed();
		listOfDevicesPage.isApprovePopupDescriptionDisplayed();
		listOfDevicesPage.isApprovePopupCloseIconDisplayed();
		listOfDevicesPage.clickOnApprovePopupCloseIcon();

		listOfDevicesPage.clickOnFilteredDeviceEllipsisButtonAsAdmin(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_1, GlobalConstants.AUTOMATION_DEACTIVATING_1);
		listOfDevicesPage.clickOnApproveOrReject();
		listOfDevicesPage.clickOnApprove();
		assertTrue(listOfDevicesPage.isDeviceStatusApprovedDisplayed(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_1, GlobalConstants.AUTOMATION_DEACTIVATING_1,
				GlobalConstants.APPROVED), GlobalConstants.isStatusDisplayed);

		listOfDevicesPage.clickOnFilteredDeviceEllipsisButtonAsAdmin(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_2, GlobalConstants.AUTOMATION_DEACTIVATING_2);
		listOfDevicesPage.clickOnApproveOrReject();
		listOfDevicesPage.clickOnApprove();

		listOfDevicesPage.clickOnFilteredDeviceEllipsisButtonAsAdmin(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_3, GlobalConstants.AUTOMATION_DEACTIVATING_3);
		listOfDevicesPage.clickOnApproveOrReject();
		listOfDevicesPage.clickOnReject();

		assertTrue(listOfDevicesPage.isDeviceStatusRejectedDisplayed(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_3, GlobalConstants.AUTOMATION_DEACTIVATING_3,
				GlobalConstants.REJECTED), GlobalConstants.isStatusDisplayed);

		listOfDevicesPage.clickOnFilteredDeviceEllipsisButtonAsAdmin(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_4, GlobalConstants.AUTOMATION_DEACTIVATING_4);
		listOfDevicesPage.clickOnApproveOrReject();
		listOfDevicesPage.clickOnApprove();

		listOfDevicesPage.clickOnFilteredDeviceEllipsisButtonAsAdmin(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_4, GlobalConstants.AUTOMATION_DEACTIVATING_4);
		listOfDevicesPage.clickOnDeactivateButton();
		listOfDevicesPage.clickOnDeactivateSubmit();

		listOfDevicesPage.clickOnFilteredDeviceEllipsisButtonAsAdmin(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_4, GlobalConstants.AUTOMATION_DEACTIVATING_4);
		listOfDevicesPage.isApproceRejectDeviceDisabled();
	}

	@Test(priority = 3, description = "Verify and Deactivate the Device from List Of Devices Page", dependsOnMethods = "approveAndRejectDevices")
	public void verifyAndDeactivateDeviceFromListOfDevicesPage() throws InterruptedException {

		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);

		loginAsDeviceProvider();

		deviceProviderPage = dashboardpage.clickOnDeviceProviderServicesTitle();
		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.AUTOMATION);

		listOfDevicesPage.clickOnDeviceThreeDots(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE);
		listOfDevicesPage.clickOnDeactivateDevice();
		listOfDevicesPage.clickOnDeactivateCancel();

		listOfDevicesPage.clickOnDeviceThreeDots(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE);
		listOfDevicesPage.clickOnDeactivateDevice();
		listOfDevicesPage.clickOnDeactivateSubmit();
		assertTrue(listOfDevicesPage.isDeviceStatusDeactivatedDisplayed(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE,
				GlobalConstants.DEACTIVATED), GlobalConstants.isDeviceStatusDisplayed);

		listOfDevicesPage.clickOnDeviceThreeDots(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE);
		assertTrue(listOfDevicesPage.isDeactivateDeviceDisabled(), GlobalConstants.isDeactivateDeviceDisabled);
		assertTrue(listOfDevicesPage.isViewDeviceEnabled(), GlobalConstants.isViewDevicesEnabled);

	}

	@Test(priority = 4, description = "Verify List Of Devices and View Device Details Page", dependsOnMethods = "verifyAndDeactivateDeviceFromListOfDevicesPage")
	public void verifyListOfDevicesPage() throws InterruptedException {

		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);
		viewDeviceDetailsPage = new ViewDeviceDetailsPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		basePage = new BasePage(driver);

		loginAsDeviceProvider();

		deviceProviderPage = dashboardpage.clickOnDeviceProviderServicesTitle();
		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.AUTOMATION);

		assertTrue(listOfDevicesPage.isListOfDevicesTitleInPartnerDisplayed(),
				GlobalConstants.isListOfDevicesTitleDisplayed);
		assertTrue(listOfDevicesPage.isSubTitleDisplayed(GlobalConstants.AUTOMATION),
				GlobalConstants.isSbiVersionDisplayed);
		assertTrue(listOfDevicesPage.isApprovedStatusDisplayed(), GlobalConstants.isStatusDisplayed);
		assertEquals(listOfDevicesPage.getBreadcrumbText(), GlobalConstants.LIST_OF_SBI_PAGE_BREADCUMB2);
		assertTrue(listOfDevicesPage.isDeviceCreationDateSameAsBrowserDateFormat(),
				GlobalConstants.isDeviceCreationDateSameAsBrowserDateFormat);

		verifyListOfDevicesHeaders();

		verifySortingOfListOfDevices();

		listOfDevicesPage.clickOnListOfSbiButton();
		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.AUTOMATION);
		listOfDevicesPage.clickOnDevice(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION, GlobalConstants.APPROVED);
		assertTrue(viewDeviceDetailsPage.isDeviceDetailsPageTitleDisplayed(),
				GlobalConstants.isDeviceDetailsTitleDisplayed);
		assertEquals(viewDeviceDetailsPage.getBreadcrumbText(), GlobalConstants.DEVICE_DETAIL_PAGE_BREADCUMB);
		assertTrue(viewDeviceDetailsPage.isApprovedStatusDisplayed(), GlobalConstants.isStatusDisplayed);
		verifyDeviceDetails(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION);

		assertEquals(viewDeviceDetailsPage.getPartnerIdLabel(), GlobalConstants.PARTNER_ID);
		assertEquals(viewDeviceDetailsPage.getPartnerTypeLabel(), GlobalConstants.PARTNER_TYPE);
		assertEquals(viewDeviceDetailsPage.getDeviceTypeLabel(), GlobalConstants.DEVICE_TYPE);
		assertEquals(viewDeviceDetailsPage.getDeviceSubTypeLabel(), GlobalConstants.DEVICE_SUB_TYPE);
		assertEquals(viewDeviceDetailsPage.getMakeLabel(), GlobalConstants.MAKE);
		assertEquals(viewDeviceDetailsPage.getModelLabel(), GlobalConstants.MODEL);
		assertEquals(viewDeviceDetailsPage.getSbiVersionLabel(), GlobalConstants.SBI_VERSION);
		assertEquals(viewDeviceDetailsPage.getSbiVersionContext(), GlobalConstants.AUTOMATION);

		viewDeviceDetailsPage.clickOnBack();
		assertTrue(listOfDevicesPage.isListOfDevicesHeadingDisplayed(),
				GlobalConstants.isListOfDevicesHeadingDisplayed);

		listOfDevicesPage.clickOnDevice(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION, GlobalConstants.APPROVED);
		viewDeviceDetailsPage.clickOnListOfDevices();
		assertTrue(listOfDevicesPage.isListOfDevicesHeadingDisplayed(),
				GlobalConstants.isListOfDevicesHeadingDisplayed);

		listOfDevicesPage.clickOnDeviceThreeDots(GlobalConstants.FINGER, GlobalConstants.SLAP,
				GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.AUTOMATION_REJECTING);
		assertTrue(listOfDevicesPage.isDeactivateDeviceDisabled(), GlobalConstants.isDeactivateDeviceDisabled);
		assertTrue(listOfDevicesPage.isViewDeviceEnabled(), GlobalConstants.isViewDevicesEnabled);
		listOfDevicesPage.clickOnViewDevice();
		viewDeviceDetailsPage.clickOnListOfSbi();
		assertEquals(listOfSbiPage.getListOfSbiMessage(), GlobalConstants.LIST_OF_SBI_PAGE_MESSAGE);

		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.AUTOMATION);

		verifyActionMenuOfDevices(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION, GlobalConstants.APPROVED, true, true);
		verifyActionMenuOfDevices(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS, GlobalConstants.AUTOMATION,
				GlobalConstants.ALPHANUMERIC, GlobalConstants.PENDING_FOR_APPROVAL, true, false);
		verifyActionMenuOfDevices(GlobalConstants.IRIS, GlobalConstants.SINGLE, GlobalConstants.AUTOMATION_LOWERCASE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.DEACTIVATED, true, false);
		verifyActionMenuOfDevices(GlobalConstants.FINGER, GlobalConstants.SLAP, GlobalConstants.AUTOMATION_REJECTING,
				GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.REJECTED, true, false);

		verifyDeviceDetailsWithViewDeviceFromMenuDots(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION, GlobalConstants.AUTOMATION, GlobalConstants.APPROVED);
		verifyDeviceDetailsWithViewDeviceFromMenuDots(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS,
				GlobalConstants.AUTOMATION, GlobalConstants.ALPHANUMERIC, GlobalConstants.PENDING_FOR_APPROVAL);
		verifyDeviceDetailsWithViewDeviceFromMenuDots(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE,
				GlobalConstants.DEACTIVATED);
		verifyDeviceDetailsWithViewDeviceFromMenuDots(GlobalConstants.FINGER, GlobalConstants.SLAP,
				GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.REJECTED);

		listOfDevicesPage.clickOnDevice(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS, GlobalConstants.AUTOMATION,
				GlobalConstants.ALPHANUMERIC, GlobalConstants.PENDING_FOR_APPROVAL);
		assertTrue(viewDeviceDetailsPage.isDeviceDetailsPageTitleDisplayed(),
				GlobalConstants.isDeviceDetailsTitleDisplayed);
		basePage.navigateBack();
		assertTrue(listOfDevicesPage.isListOfDevicesHeadingDisplayed(),
				GlobalConstants.isListOfDevicesHeadingDisplayed);
		basePage.refreshThePage();

		listOfDevicesPage.clickOnDevice(GlobalConstants.FINGER, GlobalConstants.SLAP,
				GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.REJECTED);
		assertTrue(viewDeviceDetailsPage.isDeviceDetailsPageTitleDisplayed(),
				GlobalConstants.isDeviceDetailsTitleDisplayed);
		basePage.navigateBack();
		assertTrue(listOfDevicesPage.isListOfDevicesHeadingDisplayed(),
				GlobalConstants.isListOfDevicesHeadingDisplayed);

		listOfDevicesPage.clickOnDevice(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE,
				GlobalConstants.DEACTIVATED);
//		assertFalse(viewDeviceDetailsPage.isDeviceDetailsPageTitleDisplayed(),
//				GlobalConstants.isDeviceDetailsTitleDisplayed);

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

		viewDeviceDetailsPage.clickOnHome();
		assertTrue(dashboardpage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

	}

	@Test(priority = 5, description = "Verify Device filtering in list of devices page", dependsOnMethods = "verifyListOfDevicesPage")
	public void verifyDeviceFilteringInListOfDevicesPage() {
		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);

		loginAsDeviceProvider();

		deviceProviderPage = dashboardpage.clickOnDeviceProviderServicesTitle();
		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.AUTOMATION);
		listOfDevicesPage.clickOnFilterButton();
		assertTrue(listOfDevicesPage.isFilterButtonDisabled(), GlobalConstants.isFilterButtonDisabled);
		assertTrue(listOfDevicesPage.isResetFilterDisplayed(), GlobalConstants.isResetFilterDisplayed);

		assertTrue(listOfDevicesPage.isFilterHeaderDisplayed(GlobalConstants.DEVICE_ID),
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

		verifyPlaceHodersOfDeviceFilter();

		verifySearchBarInMakeModelFilter();

		listOfDevicesPage.clickOnResetFilter();

		listOfDevicesPage.clickOnFilterButton();
		listOfDevicesPage.selectDeviceTypeFilter(GlobalConstants.FACE);
		assertEquals(listOfDevicesPage.getListOfDevicesTitle(), GlobalConstants.LIST_OF_DEVICES_TITLE_COUNT_28);
		listOfDevicesPage.selectStatusFilter(GlobalConstants.APPROVED);
		assertEquals(listOfDevicesPage.getListOfDevicesTitle(), GlobalConstants.LIST_OF_DEVICES_TITLE_COUNT_1);
		listOfDevicesPage.clickOnResetFilter();

		listOfDevicesPage.clickOnFilterButton();
		deviceFilterWithStatus(GlobalConstants.APPROVED, true, false, false, false);
		assertEquals(listOfDevicesPage.getListOfDevicesTitle(), GlobalConstants.LIST_OF_DEVICES_TITLE_COUNT_1);
		deviceFilterWithStatus(GlobalConstants.PENDING_FOR_APPROVAL, false, true, false, false);
		assertEquals(listOfDevicesPage.getListOfDevicesTitle(),
				GlobalConstants.LIST_OF_DEVICES_TITLE_PENDING_FOR_APPROVAL);
		deviceFilterWithStatus(GlobalConstants.DEACTIVATED, false, false, true, false);
		assertEquals(listOfDevicesPage.getListOfDevicesTitle(), GlobalConstants.LIST_OF_DEVICES_TITLE_COUNT_1);
		deviceFilterWithStatus(GlobalConstants.REJECTED, false, false, false, true);
		assertEquals(listOfDevicesPage.getListOfDevicesTitle(), GlobalConstants.LIST_OF_DEVICES_TITLE_COUNT_1);
		listOfDevicesPage.clickOnResetFilter();

	}

	private void loginAsDeviceProvider() {
		dashboardpage.clickOnProfileDropdown();
		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		loginpage.enterUserName(GlobalConstants.DEVICE_PARTNER_ID);
		loginpage.enterPassword(password);
		loginpage.clickOnLoginButton();
	}

	private void verifyAddDeviceLabelsAndPageDetails() {
		assertTrue(addDevicePage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertEquals(addDevicePage.getSubTitle(), GlobalConstants.LIST_OF_DEVICES);
		assertTrue(addDevicePage.isSbiVersionDisplayed(GlobalConstants.AUTOMATION),
				GlobalConstants.isSbiVersionDisplayed);
		assertTrue(addDevicePage.isApprovedStatusDisplayed(), GlobalConstants.isStatusDisplayed);
		assertEquals(addDevicePage.getAddDeviceMessage(), GlobalConstants.ADD_DEVICE_MESSAGE);
		assertTrue(addDevicePage.isDeviceTypeDisplayed(), GlobalConstants.isLabelsDisplayed);
		assertTrue(addDevicePage.isDeviceSubTypeDisplayed(), GlobalConstants.isLabelsDisplayed);
		assertTrue(addDevicePage.isMakeLabelDisplayed(), GlobalConstants.isLabelsDisplayed);
		assertTrue(addDevicePage.isModelLabelDisplayed(), GlobalConstants.isLabelsDisplayed);
		assertTrue(addDevicePage.isCopyrightsMessageDisplayed(), GlobalConstants.isCopyrightsMessageDisplayed);
		assertTrue(addDevicePage.isFooterDocumentLinkDisplayed(), GlobalConstants.isDocumentationLinkDisplayed);
		assertTrue(addDevicePage.isFooterContactLinkDisplayed(), GlobalConstants.isContactLinkDisplayed);
		assertTrue(addDevicePage.isOrganizationNameDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
		assertTrue(addDevicePage.isHeaderUserProfileDisplayed(), GlobalConstants.isUserProfileIconDisplayed);
	}

	private void fillDeviceDetails(String deviceType, String deviceSubType, String make, String model) throws Exception {
		addDevicePage.selectAddDeviceType(deviceType);
		addDevicePage.selectDeviceSubType(deviceSubType);
		addDevicePage.enterMakeName(make);
		addDevicePage.enterModelName(model);
		assertTrue(addDevicePage.isSubmitEnabled(), GlobalConstants.isSubmitButtonEnabled);
	}

	private void addDevices(String deviceType, String deviceSubType, String make, String model) throws Exception {
		fillDeviceDetails(deviceType, deviceSubType, make, model);
		addDevicePage.clickOnSubmit();
		assertTrue(addDevicePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		addDevicePage.closeSuccessMessage();
		addDevicePage.clickOnBackToDevices();
		listOfDevicesPage.clickOnAddDeviceButtonFromDeviceList();
	}

	private void fillDeviceDetailsWithPosition(String deviceType, String deviceSubType, String make, String model,
			int position) throws InterruptedException {
		addDevicePage.selectAddDeviceTypeWithPosition(deviceType, position);
		addDevicePage.selectDeviceSubTypeWithPosition(deviceSubType, position);
		addDevicePage.enterMakeNameWithPosition(make, position);
		addDevicePage.enterModelNameWithPosition(model, position);
		assertTrue(addDevicePage.isSubmitEnabled(), GlobalConstants.isSubmitButtonEnabled);
	}

	private void addMultipleDevices(String deviceType, String deviceSubType, String make, String model, int deviceCount)
			throws InterruptedException {
		for (int position = 1; position <= deviceCount; position++) {
			fillDeviceDetailsWithPosition(deviceType, deviceSubType, make + position, model + position, position);
			addDevicePage.clickOnSubmit();
			assertTrue(addDevicePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
			addDevicePage.closeSuccessMessage();
			assertTrue(addDevicePage.isAddDeviceEnabled(), GlobalConstants.isAddDeviceButtonEnabled);
			addDevicePage.clickOnAddDevice();
		}
	}

	private void verifyInitialDevicePage() {
		assertEquals(addDevicePage.isDeviceSubTypeDisabled(), true);
		assertEquals(addDevicePage.isSubmitDisabled(), true);
		assertEquals(addDevicePage.isAddDeviceDisabled(), true);
		assertEquals(addDevicePage.isDeleteButtonDisabled(), true);
		assertEquals(addDevicePage.getDeviceTypeValue(), GlobalConstants.INITIAL_DEVICE_TYPE);
		assertEquals(addDevicePage.getDeviceSubTypeValue(), GlobalConstants.INITIAL_DEVICE_SUB_TYPE);
		assertEquals(addDevicePage.getMakePlaceholder(), GlobalConstants.INITIAL_MAKE);
		assertEquals(addDevicePage.getModelPlaceholder(), GlobalConstants.INITIAL_MODEL);
	}

	private void verifyDeviceDetails(String deviceType, String deviceSubType, String make, String model) {
		assertTrue(viewDeviceDetailsPage.isCreatedDateDisplayed(), GlobalConstants.isCreatedDateTextDisplayed);
		assertEquals(viewDeviceDetailsPage.getMakeContext(), make);
		assertEquals(viewDeviceDetailsPage.getModelContext(), model);
		assertEquals(viewDeviceDetailsPage.getPartnerIdContext(), GlobalConstants.DEVICE_PARTNER_ID);
		assertEquals(viewDeviceDetailsPage.getPartnerTypeContext(), GlobalConstants.DEVICE_PROVIDER);
		assertEquals(viewDeviceDetailsPage.getDeviceTypeContext(), deviceType);
		assertEquals(viewDeviceDetailsPage.getDeviceSubTypeContext(), deviceSubType);
		assertTrue(viewDeviceDetailsPage.isBackButtonDisplayed(), GlobalConstants.isBackButton);
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

	private void verifyListOfDevicesHeadersBeforeAddingDevices() {
		assertTrue(listOfDevicesPage.isDeviceIdHeaderBeforeAddingDevicesDisplayed(),
				GlobalConstants.isListOfDevicesHeadersDisplayed);
		assertTrue(listOfDevicesPage.isDeviceTypeHeaderBeforeAddingDevicesDisplayed(),
				GlobalConstants.isListOfDevicesHeadersDisplayed);
		assertTrue(listOfDevicesPage.isDeviceSubTypeHeaderBeforeAddingDevicesDisplayed(),
				GlobalConstants.isListOfDevicesHeadersDisplayed);
		assertTrue(listOfDevicesPage.isMakeHeaderBeforeAddingDevicesDisplayed(),
				GlobalConstants.isListOfDevicesHeadersDisplayed);
		assertTrue(listOfDevicesPage.isModelHeaderBeforeAddingDevicesDisplayed(),
				GlobalConstants.isListOfDevicesHeadersDisplayed);
		assertTrue(listOfDevicesPage.isCreationDateHeaderBeforeAddingDevicesDisplayed(),
				GlobalConstants.isListOfDevicesHeadersDisplayed);
		assertTrue(listOfDevicesPage.isStatusHeaderBeforeAddingDevicesDisplayed(),
				GlobalConstants.isListOfDevicesHeadersDisplayed);
		assertTrue(listOfDevicesPage.isActionHeaderBeforeAddingDevicesDisplayed(),
				GlobalConstants.isListOfDevicesHeadersDisplayed);
	}

	private void deviceFilterWithStatus(String status, boolean approved, boolean pendingForApproval,
			boolean deactiavted, boolean rejected) {
		listOfDevicesPage.selectStatusFilter(status);
		assertEquals(listOfDevicesPage.isDeviceDisplayed(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION, GlobalConstants.AUTOMATION), approved);
		assertEquals(listOfDevicesPage.isDeviceDisplayed(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS,
				GlobalConstants.ALPHANUMERIC, GlobalConstants.AUTOMATION), pendingForApproval);
		assertEquals(listOfDevicesPage.isDeviceDisplayed(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE), deactiavted);
		assertEquals(listOfDevicesPage.isDeviceDisplayed(GlobalConstants.FINGER, GlobalConstants.SLAP,
				GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.AUTOMATION_REJECTING), rejected);
	}

	private void verifyDeviceDetailsWithViewDeviceFromMenuDots(String deviceType, String deviceSubType, String make,
			String model, String status) throws InterruptedException {
		listOfDevicesPage.clickOnDeviceThreeDots(deviceType, deviceSubType, make, model);
		listOfDevicesPage.clickOnViewDevice();

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

		verifyDeviceDetails(deviceType, deviceSubType, make, model);
		viewDeviceDetailsPage.clickOnBack();
	}

	private void verifySortingOfListOfDevices() {
		BasePage basePage = new BasePage(driver);

		assertTrue(listOfDevicesPage.isDeviceIdAscIconDisplayed(), GlobalConstants.isDeviceIdAscIconDisplayed);
		assertTrue(listOfDevicesPage.isDeviceIdDescIconDisplayed(), GlobalConstants.isDeviceIdDescIconDisplayed);
		assertTrue(listOfDevicesPage.isDeviceTypeCodeAscIconDisplayed(),
				GlobalConstants.isDeviceTypeCodeAscIconDisplayed);
		assertTrue(listOfDevicesPage.isDeviceTypeCodeDescIconDisplayed(),
				GlobalConstants.isDeviceTypeCodeDescIconDisplayed);
		assertTrue(listOfDevicesPage.isDeviceSubTypeCodeAscIconDisplayed(),
				GlobalConstants.isDeviceSubTypeCodeAscIconDisplayed);
		assertTrue(listOfDevicesPage.isDeviceSubTypeCodeDescIconDisplayed(),
				GlobalConstants.isDeviceSubTypeCodeDescIconDisplayed);
		assertTrue(listOfDevicesPage.isMakeAscIconDisplayed(), GlobalConstants.isMakeAscIconDisplayed);
		assertTrue(listOfDevicesPage.isMakeDescIconDisplayed(), GlobalConstants.isMakeDescIconDisplayed);
		assertTrue(listOfDevicesPage.isModelAscIconDisplayed(), GlobalConstants.isModelAscIconDisplayed);
		assertTrue(listOfDevicesPage.isModelDescIconDisplayed(), GlobalConstants.isModelDescIconDisplayed);
		assertTrue(listOfDevicesPage.isCreatedDateTimeAscIconDisplayed(),
				GlobalConstants.isCreatedDateTimeAscIconDisplayed);
		assertTrue(listOfDevicesPage.isCreatedDateTimeDescIconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescIconDisplayed);
		assertTrue(listOfDevicesPage.isStatusAscIconDisplayed(), GlobalConstants.isStatusAscIconDisplayed);
		assertTrue(listOfDevicesPage.isStatusDescIconDisplayed(), GlobalConstants.isStatusDescIconDisplayed);

		basePage.scrollToEndPage();
		basePage.scrollToStartPage();
		listOfDevicesPage.clickOnDeviceIdAscIcon();
		listOfDevicesPage.clickOnDeviceIdDescIcon();
		listOfDevicesPage.clickOnDeviceTypeCodeAscIcon();
		listOfDevicesPage.clickOnDeviceTypeCodeDescIcon();
		listOfDevicesPage.clickOnDeviceSubTypeCodeAscIcon();
		listOfDevicesPage.clickOnDeviceSubTypeCodeDescIcon();
		listOfDevicesPage.clickOnMakeAscIcon();
		listOfDevicesPage.clickOnMakeDescIcon();
		listOfDevicesPage.clickOnModelAscIcon();
		listOfDevicesPage.clickOnModelDescIcon();
		listOfDevicesPage.clickOnCreatedDateTimeAscIcon();
		listOfDevicesPage.clickOnCreatedDateTimeDescIcon();
		listOfDevicesPage.clickOnStatusAscIcon();
		listOfDevicesPage.clickOnStatusDescIcon();
	}

	private void verifyOptionsInActionMenuAsAdmin() {
		assertTrue(listOfDevicesPage.isApproveRejectOptionDisplayed(), GlobalConstants.isApproveRejectOptionDisplayed);
		assertTrue(listOfDevicesPage.isViewOptionDisplayed(), GlobalConstants.isViewOptionDisplayed);
		assertTrue(listOfDevicesPage.isDeactivateOptionDisplayed(), GlobalConstants.isDeactivateOptionDisplayed);
	}

	private void verifySearchBarInMakeModelFilter() {

		listOfDevicesPage.clickOnDeviceMakeFilter();
		assertTrue(listOfDevicesPage.isDeviceMakeFilterSearchBarDisplayed(),
				GlobalConstants.isDeviceMakeFilterSearchBarDisplayed);
		listOfDevicesPage.enterInvalidValueInDeviceMakeFilter(GlobalConstants.INVALID_DATA);
		assertTrue(listOfDevicesPage.isMakeDropdownNoDataAvailableDisplayed(),
				GlobalConstants.isNoDataAvailableDisplayed);

		listOfDevicesPage.clickOnDeviceModelFilter();
		assertTrue(listOfDevicesPage.isDeviceModelFilterSearchBarDisplayed(),
				GlobalConstants.isDeviceModelFilterSearchBarDisplayed);
		listOfDevicesPage.enterInvalidValueInDeviceModelFilter(GlobalConstants.INVALID_DATA);
		assertTrue(listOfDevicesPage.isModelDropdownNoDataAvailableDisplayed(),
				GlobalConstants.isNoDataAvailableDisplayed);

	}

	private void verifyPlaceHodersOfDeviceFilter() {
		assertTrue(listOfDevicesPage.isDeviceIdPlaceHolderDisplayed(), GlobalConstants.isDeviceIdPlaceHolderDisplayed);
		assertTrue(listOfDevicesPage.isDeviceTypePlaceHolderDisplayed(),
				GlobalConstants.isDeviceTypePlaceHolderDisplayed);
		assertTrue(listOfDevicesPage.isDeviceSubTypePlaceHolderDisplayed(),
				GlobalConstants.isDeviceSubTypePlaceHolderDisplayed);
		assertTrue(listOfDevicesPage.isMakePlaceHolderDisplayed(), GlobalConstants.isMakePlaceHolderDisplayed);
		assertTrue(listOfDevicesPage.isModelPlaceHolderDisplayed(), GlobalConstants.isModelPlaceHolderDisplayed);
		assertTrue(listOfDevicesPage.isStatusPlaceHolderDisplayed(), GlobalConstants.isStatusPlaceHolderDisplayed);

	}

	private void verifyActionMenuOfDevices(String deviceType, String deviceSubType, String make, String model,
			String status, boolean status1, boolean status2) throws InterruptedException {
		listOfDevicesPage.clickOnDeviceThreeDots(deviceType, deviceSubType, make, model);
		assertEquals(listOfDevicesPage.isViewDeviceEnabled(), status1);
		assertEquals(listOfDevicesPage.isDeactivateDeviceEnabled(), status2);
		listOfDevicesPage.clickOnDeviceThreeDots(deviceType, deviceSubType, make, model);
	}

}
