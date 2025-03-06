package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.fw.util.PmpTestUtil;
import io.mosip.testrig.pmprevampui.pages.AddDevicePage;
import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.DeviceProviderPage;
import io.mosip.testrig.pmprevampui.pages.ListOfDevicesPage;
import io.mosip.testrig.pmprevampui.pages.ListOfSbiPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.PartnerCertificatePage;
import io.mosip.testrig.pmprevampui.pages.RegisterPage;
import io.mosip.testrig.pmprevampui.pages.ViewDeviceDetailsPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class DeviceProviderTest extends BaseClass {

	private DeviceProviderPage deviceProviderPage;
	private DashboardPage dashboardpage;
	private ListOfSbiPage listOfSbiPage;
	private AddDevicePage addDevicePage;
	private ListOfDevicesPage listOfDevicesPage;
	private ViewDeviceDetailsPage viewDeviceDetailsPage;

	@Test(priority = 25, description = "This is a test case register new device user")
	public void registerNewUser() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		RegisterPage registerPage = new RegisterPage(driver);

//		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(),
//				GlobalConstants.isTermsAndConditionsPopUppDisplayed);
//		dashboardpage.clickOnCheckbox();
//
//		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
//		dashboardpage.clickOnProceedButton();

		dashboardpage.clickOnRootOFTrustCertText();
		dashboardpage.clickOnRootCertificateUploadButton();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.ClickOnDeviceInPartnerDomainSelectorDropdown();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.ClickonSubmitButtonForAdmin();
		partnerCertificatePage.ClickOnGoBackButton();
		dashboardpage.clickOnRootCertificateUploadButton();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.ClickOnDeviceInPartnerDomainSelectorDropdown();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.ClickonSubmitButtonForAdmin();
		partnerCertificatePage.ClickOnGoBackButton();
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		assertTrue(loginpage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);

		loginpage.clickRegisterButton();

		registerPage.enterFirstName("pmpui-device");
		assertTrue(registerPage.isLastNameTextBoxDisplayed(), GlobalConstants.isLastNameTextBoxDisplayed);
		registerPage.enterLastName("pmpui-device");

		assertTrue(registerPage.isOrganizationNameDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
		registerPage.enterOrganizationName("AABBCC");

		assertTrue(registerPage.isPartnerTypeDropdownDisplayed(), GlobalConstants.isPartnerTypeDropdownDisplayed);
		registerPage.selectDeviceProviderInPartnerTypeDropdown();

		assertTrue(registerPage.isAddressTextBoxDisplayed(), GlobalConstants.isAddressTextBoxDisplayed);
		registerPage.enterAddress("0" + data + "device");

		assertTrue(registerPage.isEmailTextBoxDisplayed(), GlobalConstants.isEmailTextBoxDisplayed);
		registerPage.enterEmail("0" + data + "device" + "@gmail.com");

		assertTrue(registerPage.isPhoneNumberTextboxDisplayed(), GlobalConstants.isPhoneNumberTextboxDisplayed);
		registerPage.enterPhone("9876543010");

		assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(),
				GlobalConstants.isNotificationLanguageDropdownDisplayed);
		registerPage.selectNotificationLanguageDropdown();

		assertTrue(registerPage.isUsernameTextBoxDisplayed(), GlobalConstants.isUsernameTextBoxDisplayed);
		registerPage.enterUsername("pmpui-device");

		assertTrue(registerPage.isPasswordTextBoxDisplayed(), GlobalConstants.isPasswordTextBoxDisplayed);
		registerPage.enterPassword("mosip123");

		assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(), GlobalConstants.isPasswordConfirmTextBoxDisplayed);
		registerPage.enterPasswordConfirm("mosip123");

		assertTrue(registerPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage = registerPage.clickSubmitButton();

		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(),
				GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardpage.clickOnCheckbox();

		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
		dashboardpage.clickOnProceedButton();

		Thread.sleep(3000);
		assertTrue(dashboardpage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);

		dashboardpage.clickOnPartnerCertificateTitle();

		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.clickOnUploadButton();

		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();

		assertTrue(partnerCertificatePage.isDeviceProviderSuccessMessage(),
				GlobalConstants.isDeviceProviderSucessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		dashboardpage = partnerCertificatePage.clickOnHomeButton();

		dashboardpage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();

		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
//		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateSubTextDisplayed(), GlobalConstants.isReUploadPartnerCertificateSubTextDisplayed);

		assertTrue(partnerCertificatePage.isPartnerDomainTypeDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		assertTrue(partnerCertificatePage.isPartnerCertOvelayDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);

		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isDeviceProviderSuccessMessage(),
				GlobalConstants.isDeviceProviderSucessMessageDisplayed);
		partnerCertificatePage.clickOnRemoveCertificateButton();

		partnerCertificatePage.uploadCertificateInvalidCert();
		assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(),
				GlobalConstants.isInvalidCertFormatePopupDisplayed);

		partnerCertificatePage.clickOnCertificateUploadCancelButton();

		partnerCertificatePage.clickOnDownloadButton();
		partnerCertificatePage.clickOnOriginalCertificateDownloadButton();
		assertTrue(partnerCertificatePage.isOriginalSignedCertDownloadedPopupDisplayed(),
				GlobalConstants.isOriginalCertificateDownloadPopupDisplayed);

		partnerCertificatePage.clickOnMosipSignedCertificateDownloadButton();
		assertTrue(partnerCertificatePage.isMosipSignedCertPopupDisplayed(),
				GlobalConstants.isMosipCertificateDownloadPopupDisplayed);

//		assertTrue(partnerCertificatePage.isSucessMessageDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.ClickOnsuccessMsgCloseButton();
	}

	@Test(priority = 26, description = "Creating SBI Device")
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
		assertEquals(deviceProviderPage.isPartnerTypeEnabled(), false);

		deviceProviderPage.clickOnAddSbiPartnerIdBox();
		deviceProviderPage.clickOnPartnerOption();
		assertEquals(deviceProviderPage.getPartnerType(), GlobalConstants.DEVICE_PROVIDER);

		deviceProviderPage.enterSbiVersion(GlobalConstants.SPACE);
		deviceProviderPage.enterSbiBinaryHash(GlobalConstants.SPACE);
		assertEquals(deviceProviderPage.isSubmitButtonEnabled(), false);

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
				GlobalConstants.isPastDateErrorMessageDisplayed);
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
//		assertEquals(deviceProviderPage.getAlertText(), GlobalConstants.RELOAD_MESSAGE);
//		addDevicePage.cancelAlert();

		deviceProviderPage.back();
		// assertTrue(deviceProviderPage.isNavigationAlertMessageDisplayed(),
		// GlobalConstants.isNavigationAlertMessageDisplayed);
		// deviceProviderPage.clickOnNavigationAlertCancel();

		fillSbiDetails(GlobalConstants.ALPHANUMERIC, GlobalConstants.ALPHANUMERIC);
		deviceProviderPage.enterPastDateOnCreatedDate();
		deviceProviderPage.enterExpiryDate();
		deviceProviderPage.clickOnSubmit();
		verifyCreatedSbi(GlobalConstants.ALPHANUMERIC);

		addSbi(GlobalConstants.SPECIAL_CHARACTERS, GlobalConstants.SPECIAL_CHARACTERS);
		verifyCreatedSbi(GlobalConstants.SPECIAL_CHARACTERS);

		addSbi(GlobalConstants.NUMERIC, GlobalConstants.NUMERIC);
		verifyCreatedSbi(GlobalConstants.NUMERIC);

		addSbi(GlobalConstants.AUTOMATION_DEACTIVATING, GlobalConstants.AUTOMATION_DEACTIVATING);
		verifyCreatedSbi(GlobalConstants.AUTOMATION_DEACTIVATING);
	}

	@Test(priority = 27, description = "Approving and Rejecting the SBI's")
	public void ApproveAndRejectSbi() {

		dashboardpage = new DashboardPage(driver);

		dashboardpage.clickOnSbiDevices();

		listOfSbiPage = new ListOfSbiPage(driver);
		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(GlobalConstants.AUTOMATION);
		listOfSbiPage.clickOnApproveOrReject();
		listOfSbiPage.clickOnApprove();

		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(GlobalConstants.ALPHANUMERIC);
		listOfSbiPage.clickOnApproveOrReject();
		listOfSbiPage.clickOnApprove();

		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(GlobalConstants.AUTOMATION_DEACTIVATING);
		listOfSbiPage.clickOnApproveOrReject();
		listOfSbiPage.clickOnApprove();

		listOfSbiPage.clickOnThreeDotsOfSbiListAsAdmin(GlobalConstants.SPECIAL_CHARACTERS);
		listOfSbiPage.clickOnApproveOrReject();
		listOfSbiPage.clickOnReject();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(priority = 28, description = "Add and verify device for SBI's")
	public void AddAndVerifyDeviceInSbi() {
		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);
		addDevicePage = new AddDevicePage(driver);

		loginAsDeviceProvider();

		deviceProviderPage = dashboardpage.clickOnDeviceProviderServicesTitle();

		listOfSbiPage.clickOnAddDeviceButton(GlobalConstants.ALPHANUMERIC);
		assertTrue(addDevicePage.isAddDeviceTitleDisplayed(), GlobalConstants.isAddDeviceTitleDisplayed);
		assertTrue(addDevicePage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertEquals(addDevicePage.getSubTitle(), GlobalConstants.LIST_OF_SBI);
		// todo make and mode name should be changed to GlobalConstants.AUTOMATION_2
		// once same device is allowed for multiple devices
		addDeviceFromListOfSbi(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION_TEMP,
				GlobalConstants.AUTOMATION_TEMP);
		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.ALPHANUMERIC);
		listOfDevicesPage.clickOnAddDeviceButtonFromDeviceList();
		addMultipleDevices(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION_DEACTIVATING,
				GlobalConstants.AUTOMATION_DEACTIVATING, 4);
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

		fillDeviceDetails(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION);
		addDevicePage.clickOnClear();
		assertEquals(addDevicePage.getDeviceTypeValue(), GlobalConstants.INITIAL_DEVICE_TYPE);

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
		addDevices(GlobalConstants.FINGER, GlobalConstants.SLAP, GlobalConstants.SPECIAL_CHARACTERS,
				GlobalConstants.SPECIAL_CHARACTERS);
		addDevices(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS, GlobalConstants.ALPHANUMERIC,
				GlobalConstants.AUTOMATION);
		addDevices(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS, GlobalConstants.AUTOMATION,
				GlobalConstants.ALPHANUMERIC);
		addDevices(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION);

		fillDeviceDetails(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION);
		addDevicePage.clickOnSubmit();
		assertEquals(addDevicePage.getDuplicateDeviceErrorMessage(), GlobalConstants.DUPLICATE_DEVICE_ERROR_MESSAGE);

		addDevicePage.clickOnBackToDevices();
		addDevicePage.clickOnProceed();

		listOfDevicesPage.clickOnHome();
		assertTrue(dashboardpage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

	}

	@Test(priority = 29, description = "Approve and reject the devices as admin")
	public void approveAndRejectDevices() {
		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);

//		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(),
//				GlobalConstants.isTermsAndConditionsPopUppDisplayed);
//		dashboardpage.clickOnCheckbox();
//
//		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
//		dashboardpage.clickOnProceedButton();

		dashboardpage.clickOnSbiDevices();
		listOfSbiPage.clickOnDeviceTab();

		listOfDevicesPage.clickOnDeviceThreeDotsAsAdmin(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION, GlobalConstants.AUTOMATION);
		listOfDevicesPage.clickOnApproveOrReject();
		listOfDevicesPage.clickOnApprove();

		listOfDevicesPage.clickOnDeviceThreeDotsAsAdmin(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE);
		listOfDevicesPage.clickOnApproveOrReject();
		listOfDevicesPage.clickOnApprove();

		listOfDevicesPage.clickOnDeviceThreeDotsAsAdmin(GlobalConstants.FINGER, GlobalConstants.SLAP,
				GlobalConstants.SPECIAL_CHARACTERS, GlobalConstants.SPECIAL_CHARACTERS);
		listOfDevicesPage.clickOnApproveOrReject();
		listOfDevicesPage.clickOnReject();

		listOfDevicesPage.selectMaxRecordsPerPage();
		listOfDevicesPage.clickOnFilterButton();
		listOfDevicesPage.applyMakeFilter(GlobalConstants.AUTOMATION_DEACTIVATING);

		listOfDevicesPage.clickOnDeviceThreeDotsAsAdmin(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_1, GlobalConstants.AUTOMATION_DEACTIVATING_1);
		listOfDevicesPage.clickOnApproveOrReject();
		listOfDevicesPage.clickOnApprove();

		listOfDevicesPage.clickOnDeviceThreeDotsAsAdmin(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_2, GlobalConstants.AUTOMATION_DEACTIVATING_2);
		listOfDevicesPage.clickOnApproveOrReject();
		listOfDevicesPage.clickOnApprove();

		listOfDevicesPage.clickOnDeviceThreeDotsAsAdmin(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_3, GlobalConstants.AUTOMATION_DEACTIVATING_3);
		listOfDevicesPage.clickOnApproveOrReject();
		listOfDevicesPage.clickOnReject();

		assertTrue(listOfDevicesPage.isDeviceStatusDisplayed(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_3, GlobalConstants.AUTOMATION_DEACTIVATING_3,
				GlobalConstants.REJECTED), GlobalConstants.isStatusDisplayed);
	}

	@Test(priority = 30, description = "Verify and Deactivate the Device from List Of Devices Page")
	public void verifyAndDeactivateDeviceFromListOfDevicesPage() {

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
		assertTrue(listOfDevicesPage.isDeviceStatusDisplayed(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE,
				GlobalConstants.DEACTIVATED), GlobalConstants.isDeviceStatusDisplayed);

		listOfDevicesPage.clickOnDeviceThreeDots(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE);
		assertTrue(listOfDevicesPage.isDeactivateDeviceDisabled(), GlobalConstants.isDeactivateDeviceDisabled);
		assertTrue(listOfDevicesPage.isViewDeviceEnabled(), GlobalConstants.isViewDevicesEnabled);

	}

	@Test(priority = 31, description = "Verify List Of Devices and View Device Details Page")
	public void verifyListOfDevicesPage() {

		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);
		viewDeviceDetailsPage = new ViewDeviceDetailsPage(driver);

		loginAsDeviceProvider();

		deviceProviderPage = dashboardpage.clickOnDeviceProviderServicesTitle();
		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.AUTOMATION);

		assertEquals(listOfDevicesPage.getListOFDevicesTitle(), GlobalConstants.LIST_OF_DEVICES_TITLE);
		assertTrue(listOfDevicesPage.isSubTitleDisplayed(GlobalConstants.AUTOMATION),
				GlobalConstants.isSbiVersionDisplayed);
		assertTrue(listOfDevicesPage.isApprovedStatusDisplayed(), GlobalConstants.isStatusDisplayed);
		assertEquals(listOfDevicesPage.getBreadcrumbText(), GlobalConstants.LIST_OF_SBI_PAGE_BREADCUMB);
		verifyListOfDevicesHeaders();
		listOfDevicesPage.clickOnDevice(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION);
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
				GlobalConstants.AUTOMATION);
		viewDeviceDetailsPage.clickOnListOfDevices();
		assertTrue(listOfDevicesPage.isListOfDevicesHeadingDisplayed(),
				GlobalConstants.isListOfDevicesHeadingDisplayed);

		listOfDevicesPage.clickOnDeviceThreeDots(GlobalConstants.FINGER, GlobalConstants.SLAP,
				GlobalConstants.SPECIAL_CHARACTERS, GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(listOfDevicesPage.isDeactivateDeviceDisabled(), GlobalConstants.isDeactivateDeviceDisabled);
		assertTrue(listOfDevicesPage.isViewDeviceEnabled(), GlobalConstants.isViewDevicesEnabled);
		listOfDevicesPage.clickOnViewDevice();
		viewDeviceDetailsPage.clickOnListOfSbi();
		assertEquals(listOfSbiPage.getListOfSbiMessage(), GlobalConstants.LIST_OF_SBI_PAGE_MESSAGE);

		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.AUTOMATION);
		verifyDeviceDetailsWithViewDeviceFromMenuDots(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION, GlobalConstants.AUTOMATION, GlobalConstants.APPROVED);
		verifyDeviceDetailsWithViewDeviceFromMenuDots(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS,
				GlobalConstants.AUTOMATION, GlobalConstants.ALPHANUMERIC, GlobalConstants.PENDING_FOR_APPROVAL);
		verifyDeviceDetailsWithViewDeviceFromMenuDots(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_LOWERCASE, GlobalConstants.AUTOMATION_LOWERCASE,
				GlobalConstants.DEACTIVATED);
		verifyDeviceDetailsWithViewDeviceFromMenuDots(GlobalConstants.FINGER, GlobalConstants.SLAP,
				GlobalConstants.SPECIAL_CHARACTERS, GlobalConstants.SPECIAL_CHARACTERS, GlobalConstants.REJECTED);

		listOfDevicesPage.clickOnDevice(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION);
		viewDeviceDetailsPage.clickOnHome();
		assertTrue(dashboardpage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

	}

	@Test(priority = 32, description = "Verify Device filtering in list of devices page")
	public void verifyDeviceFilteringInListOfDevicesPage() {
		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);

		loginAsDeviceProvider();

		deviceProviderPage = dashboardpage.clickOnDeviceProviderServicesTitle();
		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.AUTOMATION);
		listOfDevicesPage.clickOnFilterButton();
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

		listOfDevicesPage.selectDeviceTypeFilter(GlobalConstants.FACE);
		assertEquals(listOfDevicesPage.getListOFDevicesTitle(), GlobalConstants.LIST_OF_DEVICES_TITLE_COUNT_27);
		listOfDevicesPage.selectStatusFilter(GlobalConstants.APPROVED);
		assertEquals(listOfDevicesPage.getListOFDevicesTitle(), GlobalConstants.LIST_OF_DEVICES_TITLE_COUNT_1);
		listOfDevicesPage.clickOnResetFilter();

		listOfDevicesPage.clickOnFilterButton();
		deviceFilterWithStatus(GlobalConstants.APPROVED, true, false, false, false);
		assertEquals(listOfDevicesPage.getListOFDevicesTitle(), GlobalConstants.LIST_OF_DEVICES_TITLE_COUNT_1);
		deviceFilterWithStatus(GlobalConstants.PENDING_FOR_APPROVAL, false, true, false, false);
		assertEquals(listOfDevicesPage.getListOFDevicesTitle(),
				GlobalConstants.LIST_OF_DEVICES_TITLE_PENDING_FOR_APPROVAL);
		deviceFilterWithStatus(GlobalConstants.DEACTIVATED, false, false, true, false);
		assertEquals(listOfDevicesPage.getListOFDevicesTitle(), GlobalConstants.LIST_OF_DEVICES_TITLE_COUNT_1);
		deviceFilterWithStatus(GlobalConstants.REJECTED, false, false, false, true);
		assertEquals(listOfDevicesPage.getListOFDevicesTitle(), GlobalConstants.LIST_OF_DEVICES_TITLE_COUNT_1);
		listOfDevicesPage.clickOnResetFilter();

	}

	@Test(priority = 33, description = "Verify List Of SBI Page and Deactivate Sbi")
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

		assertEquals(listOfSbiPage.isAddDeviceButtonEnabled(GlobalConstants.SPECIAL_CHARACTERS), false);
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
		assertEquals(listOfDevicesPage.isDeviceListAddDeviceButtonEnabled(), false);
		listOfDevicesPage.clickOnListOfSbiButton();

		listOfSbiPage.clickOnThreeDotsOfSbiList(GlobalConstants.AUTOMATION_DEACTIVATING);
		listOfSbiPage.clickOnDeactivateSbi();
		listOfSbiPage.clickOnDeactivateSubmit();
		assertTrue(listOfSbiPage.isDeactivatedStatusDisplayed(GlobalConstants.AUTOMATION_DEACTIVATING),
				GlobalConstants.isStatusDisplayed);

		verifyDeactivateSbiIsEnabled(GlobalConstants.ALPHANUMERIC, false);
		verifyDeactivateSbiIsEnabled(GlobalConstants.SPECIAL_CHARACTERS, false);
		verifyDeactivateSbiIsEnabled(GlobalConstants.AUTOMATION, true);

		assertTrue(listOfSbiPage.isRejectedStatusDisplayed(GlobalConstants.SPECIAL_CHARACTERS),
				GlobalConstants.isStatusDisplayed);
		assertTrue(listOfSbiPage.isViewDeviceButtonEnabled(GlobalConstants.SPECIAL_CHARACTERS),
				GlobalConstants.isViewDevicesEnabled);
		verifySbiDetails(GlobalConstants.SPECIAL_CHARACTERS);
		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(listOfDevicesPage.isRejectedStatusDisplayed(), GlobalConstants.isStatusDisplayed);
		assertEquals(listOfSbiPage.getListOfSbiMessage(), GlobalConstants.LIST_OF_SBI_PAGE_MESSAGE);
		assertEquals(listOfDevicesPage.isAddDeviceButtonEnabled(), false);
		listOfDevicesPage.clickOnListOfSbiButton();

	}

	@Test(priority = 34, description = "Verify SBI Devices with Pending for approval status")
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
		assertEquals(listOfDevicesPage.isAddDeviceButtonEnabled(), false);
		addDevicePage.clickOnListOfSbiButton();

	}

	@Test(priority = 35, description = "Creating SBI Devices which are already exists")
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
		addSbi(GlobalConstants.SPECIAL_CHARACTERS, GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(deviceProviderPage.isSbiExistsErrorMessageDisplayed(),
				GlobalConstants.isSbiExistsErrorMessageDisplayed);
	}

	@Test(priority = 36, description = "Verifying the SBI and Devices after deactivating")
	public void verifySbiAndDeviceAfterDeactivate() {

		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		listOfDevicesPage = new ListOfDevicesPage(driver);
		loginAsDeviceProvider();

		deviceProviderPage = dashboardpage.clickOnDeviceProviderServicesTitle();

		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.ALPHANUMERIC);

		listOfDevicesPage.isDeviceStatusDisplayed(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
				GlobalConstants.AUTOMATION_DEACTIVATING_4, GlobalConstants.AUTOMATION_DEACTIVATING_4,
				GlobalConstants.REJECTED);
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
		assertEquals(listOfDevicesPage.isAddDeviceButtonEnabled(), false);
	}

	private void loginAsDeviceProvider() {
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName("pmpui-device");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();

		assertTrue(dashboardpage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
	}

	private void fillSbiDetails(String sbiVersion, String sbiBinaryHash) {
		deviceProviderPage.clickOnAddSbiFromSbiListButton();
		deviceProviderPage.clickOnAddSbiPartnerIdBox();
		deviceProviderPage.clickOnPartnerOption();
		assertEquals(deviceProviderPage.getPartnerType(), GlobalConstants.DEVICE_PROVIDER);
		deviceProviderPage.enterSbiVersion(sbiVersion);
		deviceProviderPage.enterSbiBinaryHash(sbiVersion);
		assertTrue(deviceProviderPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
	}

	private void fillSbiDetailsOnly(String sbiVersion, String sbiBinaryHash) {
		deviceProviderPage.clickOnAddSbiPartnerIdBox();
		deviceProviderPage.clickOnPartnerOption();
		deviceProviderPage.enterSbiVersion(sbiVersion);
		deviceProviderPage.enterSbiBinaryHash(sbiVersion);
	}

	private void verifyCreatedSbi(String sbiVersion) {
		assertTrue(deviceProviderPage.isListOfSbiTitleDisplayed(), GlobalConstants.isListOfSbiTitleDisplayed);
		assertTrue(listOfSbiPage.isCreatedSbiDisplayed(sbiVersion), GlobalConstants.isCreatedSbiDisplayed);
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
		if (sbiVersion == GlobalConstants.ALPHANUMERIC) {
			assertTrue(listOfSbiPage.isPreviousMonthSbiCreationDateDisplayed(),
					GlobalConstants.isSbiDetailsAreDisplayed);
		} else {
			assertTrue(listOfSbiPage.isSbiCreationDateDisplayed(), GlobalConstants.isSbiDetailsAreDisplayed);
		}
		assertTrue(listOfSbiPage.isSbiExpirationDateDisplayed(), GlobalConstants.isSbiDetailsAreDisplayed);
		listOfSbiPage.clickOnSbiListArrow(sbiVersion);
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

	private void fillDeviceDetails(String deviceType, String deviceSubType, String make, String model) {
		addDevicePage.selectAddDeviceType(deviceType);
		addDevicePage.selectDeviceSubType(deviceSubType);
		assertEquals(addDevicePage.isSubmitEnabled(), false);
		addDevicePage.enterMakeName(make);
		addDevicePage.enterModelName(model);
		assertTrue(addDevicePage.isSubmitEnabled(), GlobalConstants.isSubmitButtonEnabled);
	}

	private void addDevices(String deviceType, String deviceSubType, String make, String model) {
		fillDeviceDetails(deviceType, deviceSubType, make, model);
		addDevicePage.clickOnSubmit();
		assertTrue(addDevicePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		addDevicePage.closeSuccessMessage();
		assertEquals(addDevicePage.isDeviceTypeEnabled(), false);
		addDevicePage.clickOnBackToDevices();
		listOfDevicesPage.clickOnAddDeviceButtonFromDeviceList();
	}

	private void addDeviceFromListOfSbi(String deviceType, String deviceSubType, String make, String model) {
		fillDeviceDetails(deviceType, deviceSubType, make, model);
		addDevicePage.clickOnSubmit();
		assertTrue(addDevicePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		addDevicePage.closeSuccessMessage();
		assertEquals(addDevicePage.isDeviceTypeEnabled(), false);
		addDevicePage.clickOnBackToListOfSbiButton();
	}

	private void fillDeviceDetailsWithPosition(String deviceType, String deviceSubType, String make, String model,
			int position) {
		addDevicePage.selectAddDeviceTypeWithPosition(deviceType, position);
		addDevicePage.selectDeviceSubTypeWithPosition(deviceSubType, position);
		assertEquals(addDevicePage.isSubmitEnabled(), false);
		addDevicePage.enterMakeNameWithPosition(make, position);
		addDevicePage.enterModelNameWithPosition(model, position);
		assertTrue(addDevicePage.isSubmitEnabled(), GlobalConstants.isSubmitButtonEnabled);
	}

	private void addMultipleDevices(String deviceType, String deviceSubType, String make, String model,
			int deviceCount) {
		for (int position = 1; position <= deviceCount; position++) {
			fillDeviceDetailsWithPosition(deviceType, deviceSubType, make + position, model + position, position);
			addDevicePage.clickOnSubmit();
			assertTrue(addDevicePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
			addDevicePage.closeSuccessMessage();
			assertEquals(addDevicePage.isDeviceTypeEnabled(), false);
			assertTrue(addDevicePage.isAddDeviceEnabled(), GlobalConstants.isAddDeviceButtonEnabled);
			addDevicePage.clickOnAddDevice();
		}
	}

	private void verifyInitialDevicePage() {
		assertEquals(addDevicePage.isDeviceSubTypeEnabled(), false);
		assertEquals(addDevicePage.isSubmitEnabled(), false);
		assertEquals(addDevicePage.isAddDeviceEnabled(), false);
		assertEquals(addDevicePage.isDeleteButtonEnabled(), false);
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
				GlobalConstants.SPECIAL_CHARACTERS, GlobalConstants.SPECIAL_CHARACTERS), rejected);
	}

	private void verifyDeviceDetailsWithViewDeviceFromMenuDots(String deviceType, String deviceSubType, String make,
			String model, String status) {
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

	private void verifyDeactivateSbiIsEnabled(String sbiVersion, boolean status) {
		listOfSbiPage.clickOnThreeDotsOfSbiList(sbiVersion);
		assertEquals(listOfSbiPage.isDeactivateOptionEnabled(), status);
	}

	private void addSbi(String sbiVersion, String sbiBinaryHash) {
		fillSbiDetails(sbiVersion, sbiBinaryHash);
		deviceProviderPage.enterExpiryDate();
		deviceProviderPage.clickOnSubmit();
	}
}
