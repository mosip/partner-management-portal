package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.DeviceProviderPage;
import io.mosip.testrig.pmpuiv2.pages.ListOfSbiPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "DevicePartnerCreation" }, groups = { "SbiCreationTest" })
public class SbiCreationTest extends BaseClass {

	private DeviceProviderPage deviceProviderPage;
	private DashboardPage dashboardpage;
	private ListOfSbiPage listOfSbiPage;

	@Test(priority = 1, description = "Creating SBI Device")
	public void createSbiDevice() {

		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);

		loginAsDeviceProvider();

		dashboardpage.clickOnHamburgerOpen();
		assertEquals(dashboardpage.getSideNavDeviceProviderTitle(), GlobalConstants.DEVICE_PROVIDER_TITLE);
		deviceProviderPage = dashboardpage.clickOnSideNavDeviceProvider();

		dashboardpage.clickOnHamburgerClose();
		deviceProviderPage.clickOnHome();

		dashboardpage.clickOnSideNavDeviceProvider();
		deviceProviderPage.clickOnHome();

		deviceProviderPage = dashboardpage.clickOnDeviceProviderServicesTitle();
		deviceProviderPage.clickOnAddSbiButton();

		assertTrue(deviceProviderPage.isAddSbiDetailsTitleDisplayed(), GlobalConstants.isAddSbiDetailsTitleDisplayed);
		assertTrue(deviceProviderPage.isAddSbiDetailsSubTitleDisplayed(),
				GlobalConstants.isAddSbiDetailsSubTitleDisplayed);
		assertTrue(deviceProviderPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertTrue(deviceProviderPage.isListOfSbiTitleButtonDisplayed(), GlobalConstants.isListOfSbiButtonDisplayed);
		assertTrue(deviceProviderPage.isAddSbiDetailsInfoMessageDisplayed(),
				GlobalConstants.isAddSbiDetailsInfoMessageDisplayed);
		assertTrue(deviceProviderPage.isPartnerIdLabelDisplayed(), GlobalConstants.isLabelsDisplayed);
		assertTrue(deviceProviderPage.isPartnerTypeLabelDisplayed(), GlobalConstants.isLabelsDisplayed);
		assertTrue(deviceProviderPage.isSbiVersionLabelDisplayed(), GlobalConstants.isLabelsDisplayed);
		assertTrue(deviceProviderPage.isSbiBinaryHashLabelDisplayed(), GlobalConstants.isLabelsDisplayed);
		assertTrue(deviceProviderPage.isCreatedDateLabelDisplayed(), GlobalConstants.isLabelsDisplayed);
		assertTrue(deviceProviderPage.isExpiryDateLabelDisplayed(), GlobalConstants.isLabelsDisplayed);
		assertEquals(deviceProviderPage.getSbiVersion(), GlobalConstants.INITIAL_SBI_VERSION);
		assertEquals(deviceProviderPage.getSbiBinaryHash(), GlobalConstants.INITIAL_SBI_BINARY_HASH);

		deviceProviderPage.clickOnPartnerIdInfo();
		assertEquals(deviceProviderPage.getPartnerIdInfoMessage(), GlobalConstants.ADD_SBI_PARTNER_ID_INFO_MESSAGE);
		deviceProviderPage.clickOnExpiryInfo();
		assertEquals(deviceProviderPage.getExpiryDateInfoMessage(), GlobalConstants.ADD_SBI_EXPIRY_DATE_INFO_MESSAGE);
		assertTrue(deviceProviderPage.isPartnerTypeDisabled(), GlobalConstants.isPartnerTypeDisabled);

		deviceProviderPage.clickOnAddSbiPartnerIdBox();
		deviceProviderPage.clickOnPartnerOption();
		assertEquals(deviceProviderPage.getPartnerType(), GlobalConstants.DEVICE_PROVIDER);

		deviceProviderPage.enterSbiVersion(GlobalConstants.SPACE);
		deviceProviderPage.enterSbiBinaryHash(GlobalConstants.SPACE);
		assertTrue(deviceProviderPage.isSubmitButtonDisabled(), GlobalConstants.isSubmitButtonDisabled);

		deviceProviderPage.enterSbiVersion(GlobalConstants.AUTOMATION);
		deviceProviderPage.enterSbiBinaryHash(GlobalConstants.AUTOMATION);

		assertEquals(deviceProviderPage.getCreatedDateValue(), PmpTestUtil.todayDate);
		assertEquals(deviceProviderPage.getExpiredDateValue(), PmpTestUtil.todayDate);

		deviceProviderPage.clickOnSubmit();
		assertTrue(deviceProviderPage.isSameDateErrorMessageDisplayed(),
				GlobalConstants.isSameDateErrorMessageDisplayed);

		deviceProviderPage.enterFutureDateOnCreatedDate();
		deviceProviderPage.clickOnSubmit();
		assertTrue(deviceProviderPage.isFutureDateErrorMessageDisplayed(),
				GlobalConstants.isFutureDateErrorMessageDisplayed);
		deviceProviderPage.clickOnClearForm();

		fillSbiDetailsOnly(GlobalConstants.AUTOMATION, GlobalConstants.AUTOMATION);
		deviceProviderPage.enterCurrentDateOnCreatedDate();
		deviceProviderPage.enterPastDateOnExpiryDate();
		deviceProviderPage.clickOnSubmit();
		assertTrue(deviceProviderPage.isSameDateErrorMessageDisplayed(),
				GlobalConstants.isSameDateErrorMessageDisplayed);
		deviceProviderPage.clickOnClearForm();

		fillSbiDetailsOnly(GlobalConstants.AUTOMATION, GlobalConstants.AUTOMATION);
		deviceProviderPage.enterPastDateOnCreatedDate();
		deviceProviderPage.enterPastDateOnExpiryDate();
		deviceProviderPage.clickOnSubmit();
		assertTrue(deviceProviderPage.isSameDateErrorMessageDisplayed(),
				GlobalConstants.isPastDateErrorMessageDisplayed);
		deviceProviderPage.clickOnClearForm();

		fillSbiDetailsOnly(GlobalConstants.AUTOMATION, GlobalConstants.AUTOMATION);
		deviceProviderPage.enterFutureDateOnCreatedDate();
		deviceProviderPage.enterFutureDateOnExpiryDate();
		deviceProviderPage.clickOnSubmit();
		assertTrue(deviceProviderPage.isFutureDateErrorMessageDisplayed(),
				GlobalConstants.isFutureDateErrorMessageDisplayed);
		deviceProviderPage.clickOnClearForm();

		assertEquals(deviceProviderPage.getPartnerId(), GlobalConstants.INITIAL_PARTNER_ID);
		assertEquals(deviceProviderPage.getPartnerType(), GlobalConstants.INITIAL_PARTNER_TYPE);

		deviceProviderPage.clickOnCancel();

		deviceProviderPage.clickOnAddSbiButton();
		deviceProviderPage.clickOnAddSbiPartnerIdBox();
		deviceProviderPage.clickOnPartnerOption();
		assertEquals(deviceProviderPage.getPartnerType(), GlobalConstants.DEVICE_PROVIDER);

		deviceProviderPage.enterSbiVersion(GlobalConstants.AUTOMATION);
		deviceProviderPage.enterSbiBinaryHash(GlobalConstants.AUTOMATION);
		assertTrue(deviceProviderPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
		deviceProviderPage.enterExpiryDate();
		deviceProviderPage.clickOnSubmit();
		verifyCreatedSbi(GlobalConstants.AUTOMATION);
		assertTrue(listOfSbiPage.isPendingForApprovalDisplayed(GlobalConstants.AUTOMATION),
				GlobalConstants.isStatusDisplayed);

		addSbi(GlobalConstants.AUTOMATION, GlobalConstants.AUTOMATION);
		assertTrue(deviceProviderPage.isSbiExistsErrorMessageDisplayed(),
				GlobalConstants.isSbiExistsErrorMessageDisplayed);

		deviceProviderPage.enterSbiBinaryHash(GlobalConstants.ALPHANUMERIC);
		deviceProviderPage.clickOnSubmit();
		assertTrue(deviceProviderPage.isSbiExistsErrorMessageDisplayed(),
				GlobalConstants.isSbiExistsErrorMessageDisplayed);

		deviceProviderPage.reload();

		deviceProviderPage.enterSbiVersion(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(deviceProviderPage.isSpecialCharacterErrorMessageDisplayed(),
				GlobalConstants.isSpecialCharacterErrorMessageDisplayed);
		deviceProviderPage.clickOnClearForm();
		deviceProviderPage.enterSbiBinaryHash(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(deviceProviderPage.isSpecialCharacterErrorMessageDisplayed(),
				GlobalConstants.isSpecialCharacterErrorMessageDisplayed);
		deviceProviderPage.back();
		assertTrue(deviceProviderPage.isNavigationAlertMessageDisplayed(),
				GlobalConstants.isNavigationAlertMessageDisplayed);
		deviceProviderPage.clickOnNavigationAlertCancel();
		deviceProviderPage.clickOnClearForm();
		deviceProviderPage.back();

		fillSbiDetails(GlobalConstants.ALPHANUMERIC, GlobalConstants.ALPHANUMERIC);
		deviceProviderPage.enterPastDateOnCreatedDate();
		deviceProviderPage.enterExpiryDate();
		deviceProviderPage.clickOnSubmit();
		verifyCreatedSbi(GlobalConstants.ALPHANUMERIC);

		addSbi(GlobalConstants.AUTOMATION_REJECTING, GlobalConstants.AUTOMATION_REJECTING);
		verifyCreatedSbi(GlobalConstants.AUTOMATION_REJECTING);

		addSbi(GlobalConstants.NUMERIC, GlobalConstants.NUMERIC);
		verifyCreatedSbi(GlobalConstants.NUMERIC);

		addSbi(GlobalConstants.AUTOMATION_DEACTIVATING, GlobalConstants.AUTOMATION_DEACTIVATING);
		verifyCreatedSbi(GlobalConstants.AUTOMATION_DEACTIVATING);

		addSbi(GlobalConstants.DEACTIVATE_SBI_ASADMIN, GlobalConstants.DEACTIVATE_SBI_ASADMIN);
		verifyCreatedSbi(GlobalConstants.DEACTIVATE_SBI_ASADMIN);

		// Enter manually & Verify date format in created date
		deviceProviderPage.clickOnAddSbiFromSbiListButton();
		fillSbiDetailsDateManually(GlobalConstants.AUTOMATION_TEMP, GlobalConstants.AUTOMATION_TEMP);
		deviceProviderPage.enterDateManuallyInCreatedDate(GlobalConstants.YYYY_MM_DD_FORMATTER);
		assertTrue(deviceProviderPage.isEnteredDateInYearDateWithSlashDisplayed(),
				GlobalConstants.isEnteredDateInYearDateWithSlashDisplayed);
		deviceProviderPage.clickOnExpiryDate();
		assertTrue(deviceProviderPage.isEnteredDateChangedToDateYearFormat(),
				GlobalConstants.isEnteredDateChangedToDateYearFormat);

		fillSbiDetailsDateManually(GlobalConstants.AUTOMATION_TEMP, GlobalConstants.AUTOMATION_TEMP);
		deviceProviderPage.enterDateManuallyInCreatedDate(GlobalConstants.YYYY_MMM_DD_FORMATTER);
		assertTrue(deviceProviderPage.isEnteredDateInYearDateWithHyphenDisplayed(),
				GlobalConstants.isEnteredDateInYearDateWithHyphenDisplayed);
		deviceProviderPage.clickOnExpiryDate();
		assertTrue(deviceProviderPage.isEnteredDateChangedToDateYearFormat(),
				GlobalConstants.isEnteredDateChangedToDateYearFormat);

		fillSbiDetailsDateManually(GlobalConstants.AUTOMATION_TEMP, GlobalConstants.AUTOMATION_TEMP);
		deviceProviderPage.enterDateManuallyInCreatedDate(GlobalConstants.YYYY_MM_DD_FORMATTER2);
		assertTrue(deviceProviderPage.isEnteredDateInYearDateWithDotDisplayed(),
				GlobalConstants.isEnteredDateInYearDateWithDotDisplayed);
		deviceProviderPage.clickOnExpiryDate();
		assertTrue(deviceProviderPage.isEnteredDateChangedToDateYearFormat(),
				GlobalConstants.isEnteredDateChangedToDateYearFormat);

		// Enter manually & Verify date format in expired date
		fillSbiDetailsOnly(GlobalConstants.AUTOMATION_TEMP, GlobalConstants.AUTOMATION_TEMP);
		deviceProviderPage.enterDateManuallyInExpiryDate(GlobalConstants.YYYY_MM_DD_FORMATTER);
		assertTrue(deviceProviderPage.isExpireDateInYearDateWithSlashDisplayed(),
				GlobalConstants.isExpireDateInYearDateWithSlashDisplayed);
		deviceProviderPage.clickOnCreatedDate();
		assertTrue(deviceProviderPage.isExpireDateChangedToDateYearFormat(),
				GlobalConstants.isExpireDateChangedToDateYearFormat);

		fillSbiDetailsOnly(GlobalConstants.AUTOMATION_TEMP, GlobalConstants.AUTOMATION_TEMP);
		deviceProviderPage.enterDateManuallyInExpiryDate(GlobalConstants.YYYY_MMM_DD_FORMATTER);
		assertTrue(deviceProviderPage.isExpireDateInYearDateWithHyphenDisplayed(),
				GlobalConstants.isExpireDateInYearDateWithHyphenDisplayed);
		deviceProviderPage.clickOnCreatedDate();
		assertTrue(deviceProviderPage.isExpireDateChangedToDateYearFormat(),
				GlobalConstants.isExpireDateChangedToDateYearFormat);

		fillSbiDetailsOnly(GlobalConstants.AUTOMATION_TEMP, GlobalConstants.AUTOMATION_TEMP);
		deviceProviderPage.enterDateManuallyInExpiryDate(GlobalConstants.YYYY_MM_DD_FORMATTER2);
		assertTrue(deviceProviderPage.isExpireDateInYearDateWithDotDisplayed(),
				GlobalConstants.isExpireDateInYearDateWithDotDisplayed);
		deviceProviderPage.clickOnCreatedDate();
		assertTrue(deviceProviderPage.isExpireDateChangedToDateYearFormat(),
				GlobalConstants.isExpireDateChangedToDateYearFormat);

	}

	@Test(priority = 2, description = "Approving and Rejecting the SBI's", dependsOnMethods = "createSbiDevice")
	public void ApproveAndRejectSbi() {

		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);

		dashboardpage.clickOnSbiDevices();

		assertTrue(listOfSbiPage.isSbiAdminListPageTitleDisplayed(), GlobalConstants.isSbiAdminListPageTitleDisplayed);
		assertTrue(listOfSbiPage.isSbiAdminListPageBreadcumbDisplayed(),
				GlobalConstants.isSbiAdminListPageBreadcumbDisplayed);
		verifyListOfSbiHeaders();
		verifySortingOfSbiList();

		assertTrue(listOfSbiPage.isPartnerIdInFirstColumnDisplayed(),
				GlobalConstants.isPartnerIdInFirstColoumnDisplayed);
		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(GlobalConstants.AUTOMATION);
		assertTrue(listOfSbiPage.isApproveRejectButtonEnabled(), GlobalConstants.isApproveRejectButtonEnabled);
		listOfSbiPage.clickOnApproveOrReject();
		assertTrue(listOfSbiPage.isApproveOrRejectSbiPopupDisplayed(),
				GlobalConstants.isApproveOrRejectSbiPopupDisplayed);
		assertTrue(listOfSbiPage.isApproveOrRejectSbiPopupTitleDisplayed(),
				GlobalConstants.isApproveOrRejectSbiPopupTitleDisplayed);
		assertTrue(listOfSbiPage.isApproveOrRejectSbiPopupSubtitleDisplayed(),
				GlobalConstants.isApproveOrRejectSbiPopupSubtitleDisplayed);
		assertTrue(listOfSbiPage.isApproveOrRejectSbiPopupDescrDisplayed(),
				GlobalConstants.isApproveOrRejectSbiPopupDescDisplayed);
		listOfSbiPage.clickOnApprove();

		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(GlobalConstants.ALPHANUMERIC);
		listOfSbiPage.clickOnApproveOrReject();
		listOfSbiPage.clickOnApprove();

		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(GlobalConstants.AUTOMATION_DEACTIVATING);
		listOfSbiPage.clickOnApproveOrReject();
		listOfSbiPage.clickOnApprove();

		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(GlobalConstants.DEACTIVATE_SBI_ASADMIN);
		listOfSbiPage.clickOnApproveOrReject();
		listOfSbiPage.clickOnApprove();

		listOfSbiPage.clickOnFilterButton();
		assertTrue(listOfSbiPage.isFilterButtonDisabled(), GlobalConstants.isFilterButtonDisabled);
		assertTrue(listOfSbiPage.isApplyFilterButtonDisabled(), GlobalConstants.isApplyFilterButtonDisabled);

		assertTrue(listOfSbiPage.isPartnerIdFilterDisplayed(), GlobalConstants.isPartnerIdFilterDisplayed);
		assertTrue(listOfSbiPage.isOrganisationFilterDisplayed(), GlobalConstants.isOrganisationFilterDisplayed);
		assertTrue(listOfSbiPage.isSbiIdFilterDisplayed(), GlobalConstants.isSbiIdFilterDisplayed);
		assertTrue(listOfSbiPage.isSbiVersionFilterDisplayed(), GlobalConstants.isSbiVersionFilterDisplayed);
		assertTrue(listOfSbiPage.isSbiExpiryStatusFilterDisplayed(), GlobalConstants.isSbiExpiryStatusFilterDisplayed);
		assertTrue(listOfSbiPage.isStatusFilterDisplayed(), GlobalConstants.isStatusFilterDisplayed);

		listOfSbiPage.enterPartnerIdInFilter(GlobalConstants.DEVICE_PARTNER_ID);
		listOfSbiPage.enterSbiVersionInFilter(GlobalConstants.INVALID_DATA);
		listOfSbiPage.clickOnApplyFilterButton();
		assertTrue(listOfSbiPage.isNoResultsFoundDisplayed(), GlobalConstants.isNoResultsFoundDisplayed);
		listOfSbiPage.clickOnFilterResetButton();

		listOfSbiPage.reload();
		assertTrue(listOfSbiPage.isSbiAdminListPageTitleDisplayed(), GlobalConstants.isSbiAdminListPageTitleDisplayed);

		listOfSbiPage.clickOnFilterButton();
		listOfSbiPage.enterPartnerIdInFilter(GlobalConstants.DEVICE_PARTNER_ID);
		listOfSbiPage.enterSbiVersionInFilter(GlobalConstants.AUTOMATION_REJECTING);
		listOfSbiPage.selectPendingForApprovalStatusInFilter();
		listOfSbiPage.clickOnApplyFilterButton();

		assertTrue(listOfSbiPage.isPendingForApprovalDisplayedInAdminPage(GlobalConstants.AUTOMATION_REJECTING));
		listOfSbiPage.clickOnPendingForApprovalSbiItem();
		assertTrue(listOfSbiPage.isSbiDetailsPageDisplayed(), GlobalConstants.isSbiDetailsPageDisplayed);
		listOfSbiPage.clickOnSbiDetailsBackButton();

		verifyViewOptionIsEnabled(GlobalConstants.AUTOMATION_REJECTING, true);
		verifyApproveOrRejectSbiIsEnabled(GlobalConstants.AUTOMATION_REJECTING, true);

		listOfSbiPage.clickOnFilterButton();
		listOfSbiPage.enterPartnerIdInFilter(GlobalConstants.DEVICE_PARTNER_ID);
		listOfSbiPage.enterSbiVersionInFilter(GlobalConstants.AUTOMATION_REJECTING);
		listOfSbiPage.clickOnApplyFilterButton();

		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(GlobalConstants.AUTOMATION_REJECTING);
		listOfSbiPage.clickOnApproveOrReject();
		listOfSbiPage.clickOnReject();
		assertTrue(listOfSbiPage.isRejectedStatusDisplayedInAdminPage(GlobalConstants.AUTOMATION_REJECTING));
		listOfSbiPage.clickOnRejectedSbiItem();
		assertTrue(listOfSbiPage.isSbiDetailsPageDisplayed(), GlobalConstants.isSbiDetailsPageDisplayed);
		listOfSbiPage.clickOnSbiDetailsBackButton();

		verifyViewOptionIsEnabled(GlobalConstants.AUTOMATION_REJECTING, true);
		verifyApproveOrRejectSbiIsEnabled(GlobalConstants.AUTOMATION_REJECTING, false);

		listOfSbiPage.clickOnFilterButton();
		listOfSbiPage.enterPartnerIdInFilter(GlobalConstants.DEVICE_PARTNER_ID);
		listOfSbiPage.enterSbiVersionInFilter(GlobalConstants.AUTOMATION_REJECTING);
		listOfSbiPage.clickOnApplyFilterButton();
		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(GlobalConstants.AUTOMATION_REJECTING);
		listOfSbiPage.clickOnApproveOrReject();
		assertFalse(listOfSbiPage.isApproveOrRejectSbiPopupDisplayed(),
				GlobalConstants.isApproveOrRejectSbiPopupDisplayed);
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

	private void fillSbiDetailsOnly(String sbiVersion, String sbiBinaryHash) {
		deviceProviderPage.clickOnAddSbiPartnerIdBox();
		deviceProviderPage.clickOnPartnerOption();
		deviceProviderPage.enterSbiVersion(sbiVersion);
		deviceProviderPage.enterSbiBinaryHash(sbiBinaryHash);
	}

	private void fillSbiDetailsDateManually(String sbiVersion, String sbiBinaryHash) {
		deviceProviderPage.clickOnClearForm();
		deviceProviderPage.clickOnAddSbiPartnerIdBox();
		deviceProviderPage.clickOnPartnerOption();
		deviceProviderPage.enterSbiVersion(sbiVersion);
		deviceProviderPage.enterSbiBinaryHash(sbiBinaryHash);
	}

	private void verifyCreatedSbi(String sbiVersion) {
		assertTrue(deviceProviderPage.isListOfSbiTitleDisplayed(), GlobalConstants.isListOfSbiTitleDisplayed);
		assertTrue(listOfSbiPage.isCreatedSbiDisplayed(sbiVersion), GlobalConstants.isCreatedSbiDisplayed);
	}

	private void addSbi(String sbiVersion, String sbiBinaryHash) {
		fillSbiDetails(sbiVersion, sbiBinaryHash);
		deviceProviderPage.enterExpiryDate();
		deviceProviderPage.clickOnSubmit();
	}

	private void verifyListOfSbiHeaders() {
		assertTrue(listOfSbiPage.isPartnerIdHeaderDisplayed(), GlobalConstants.isPartnerIdHeaderDisplayed);
		assertTrue(listOfSbiPage.isOrganisationHeaderDisplayed(), GlobalConstants.isOrganisationHeaderDisplayed);
		assertTrue(listOfSbiPage.isSbiIdHeaderDisplayed(), GlobalConstants.isSbiIdHeaderDisplayed);
		assertTrue(listOfSbiPage.isSbiVersionHeaderDisplayed(), GlobalConstants.isSbiVersionHeaderDisplayed);
		assertTrue(listOfSbiPage.isSbiCreationDateHeaderDisplayed(), GlobalConstants.isSbiCreationDateHeaderDisplayed);
		assertTrue(listOfSbiPage.isSbiExpirationDateHeaderDisplayed(),
				GlobalConstants.isSbiExpirationDateHeaderDisplayed);
		assertTrue(listOfSbiPage.isSbiExpiryStatusHeaderDisplayed(), GlobalConstants.isSbiExpiryStatusHeaderDisplayed);
		assertTrue(listOfSbiPage.isCreationDateHeaderDisplayed(), GlobalConstants.isCreationDateHeaderDisplayed);
		assertTrue(listOfSbiPage.isStatusHeaderDisplayed(), GlobalConstants.isStatusHeaderDisplayed);
		assertTrue(listOfSbiPage.isLinkedDevicesHeaderDisplayed(), GlobalConstants.isLinkedDevicesHeaderDisplayed);
		assertTrue(listOfSbiPage.isActionHeaderDisplayed(), GlobalConstants.isActionHeaderDisplayed);
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

	private void verifySortingOfSbiList() {
		assertTrue(listOfSbiPage.isPartnerIdAscIconDisplayed(), GlobalConstants.isPartnerIdAscIconDisplayed);
		assertTrue(listOfSbiPage.isPartnerIdDescIconDisplayed(), GlobalConstants.isPartnerIdDescIconDisplayed);
		assertTrue(listOfSbiPage.isOrgNameAscIconDisplayed(), GlobalConstants.isOrgNameAscIconDisplayed);
		assertTrue(listOfSbiPage.isOrgNameDescIconDisplayed(), GlobalConstants.isOrgNameDescIconDisplayed);
		assertTrue(listOfSbiPage.isSbiIdAscIconDisplayed(), GlobalConstants.isSbiIdAscIconDisplayed);
		assertTrue(listOfSbiPage.isSbiIdDescIconDisplayed(), GlobalConstants.isSbiIdDescIconDisplayed);
		assertTrue(listOfSbiPage.isSbiCreatedDateTimeAscIconDisplayed(),
				GlobalConstants.isSbiCreatedDateTimeAscIconDisplayed);
		assertTrue(listOfSbiPage.isSbiCreatedDateTimeDescIconDisplayed(),
				GlobalConstants.isSbiCreatedDateTimeDescIconDisplayed);
		assertTrue(listOfSbiPage.isSbiExpiryDateTimeAscIconDisplayed(),
				GlobalConstants.isSbiExpiryDateTimeAscIconDisplayed);
		assertTrue(listOfSbiPage.isSbiExpiryDateTimeDescIconDisplayed(),
				GlobalConstants.isSbiExpiryDateTimeDescIconDisplayed);
		assertTrue(listOfSbiPage.isSbiExpiryStatusAscIconDisplayed(),
				GlobalConstants.isSbiExpiryStatusAscIconDisplayed);
		assertTrue(listOfSbiPage.isSbiExpiryStatusDescIconDisplayed(),
				GlobalConstants.isSbiExpiryStatusDescIconDisplayed);
		assertTrue(listOfSbiPage.isCreatedDateTimeAscIconDisplayed(),
				GlobalConstants.isCreatedDateTimeAscIconDisplayed);
		assertTrue(listOfSbiPage.isCreatedDateTimeDescIconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescIconDisplayed);
		assertTrue(listOfSbiPage.isStatusAscIconDisplayed(), GlobalConstants.isStatusAscIconDisplayed);
		assertTrue(listOfSbiPage.isStatusDescIconDisplayed(), GlobalConstants.isStatusDescIconDisplayed);
		assertTrue(listOfSbiPage.isCountOfAssociatedDevicesAscIconDisplayed(),
				GlobalConstants.isCountOfAssociatedDevicesAscIconDisplayed);
		assertTrue(listOfSbiPage.isCountOfAssociatedDevicesDescIconDisplayed(),
				GlobalConstants.isCountOfAssociatedDevicesDescIconDisplayed);
	}

}