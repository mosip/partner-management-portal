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
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class DeviceProviderTest extends BaseClass {

	private DeviceProviderPage deviceProviderPage;
	private DashboardPage dashboardpage;
	private ListOfSbiPage listOfSbiPage;
	private AddDevicePage addDevicePage;
	private ListOfDevicesPage listOfDevicesPage;

	@Test(priority = 1, description = "This is a test case register new device user")
	public void registerNewUser() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		RegisterPage registerPage = new RegisterPage(driver);

		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(),
				GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardpage.clickOnCheckbox();

		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
		dashboardpage.clickOnProceedButton();

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

	@Test(priority = 2, description = "Creating SBI Device")
	public void createSbiDevice() {

		dashboardpage = new DashboardPage(driver);
		listOfSbiPage = new ListOfSbiPage(driver);
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName("pmpui-device");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();

		assertTrue(dashboardpage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
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

		deviceProviderPage.enterCurrentDateOnCreatedDate();
		deviceProviderPage.enterPastDateOnExpiryDate();
		deviceProviderPage.clickOnSubmit();
		assertTrue(deviceProviderPage.isFutureDateErrorMessageDisplayed(),
				GlobalConstants.isFutureDateErrorMessageDisplayed);

		deviceProviderPage.enterPastDateOnCreatedDate();
		deviceProviderPage.enterPastDateOnExpiryDate();
		deviceProviderPage.clickOnSubmit();
		assertTrue(deviceProviderPage.isSameDateErrorMessageDisplayed(),
				GlobalConstants.isPastDateErrorMessageDisplayed);

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

		fillSbiDetails(GlobalConstants.AUTOMATION, GlobalConstants.AUTOMATION);
		deviceProviderPage.enterExpiryDate();
		deviceProviderPage.clickOnSubmit();
		assertTrue(deviceProviderPage.isSbiExistsErrorMessageDisplayed(),
				GlobalConstants.isSbiExistsErrorMessageDisplayed);

		deviceProviderPage.enterSbiBinaryHash(GlobalConstants.ALPHANUMERIC);
		deviceProviderPage.clickOnSubmit();
		assertTrue(deviceProviderPage.isSbiExistsErrorMessageDisplayed(),
				GlobalConstants.isSbiExistsErrorMessageDisplayed);

		deviceProviderPage.reload();
		assertEquals(deviceProviderPage.getAlertText(), GlobalConstants.RELOAD_MESSAGE);
		addDevicePage.cancelAlert();

		deviceProviderPage.back();
		// assertTrue(deviceProviderPage.isNavigationAlertMessageDisplayed(),
		// GlobalConstants.isNavigationAlertMessageDisplayed);
		// deviceProviderPage.clickOnNavigationAlertCancel();

		fillSbiDetails(GlobalConstants.ALPHANUMERIC, GlobalConstants.ALPHANUMERIC);
		deviceProviderPage.enterPastDateOnCreatedDate();
		deviceProviderPage.enterExpiryDate();
		deviceProviderPage.clickOnSubmit();
		verifyCreatedSbi(GlobalConstants.ALPHANUMERIC);

		fillSbiDetails(GlobalConstants.SPECIAL_CHARACTERS, GlobalConstants.SPECIAL_CHARACTERS);
		deviceProviderPage.enterExpiryDate();
		deviceProviderPage.clickOnSubmit();
		verifyCreatedSbi(GlobalConstants.SPECIAL_CHARACTERS);

		fillSbiDetails(GlobalConstants.NUMERIC, GlobalConstants.NUMERIC);
		deviceProviderPage.enterExpiryDate();
		deviceProviderPage.clickOnSubmit();
		verifyCreatedSbi(GlobalConstants.NUMERIC);
	}

	@Test(priority = 3, description = "Approving and Rejecting the SBI's")
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

	@Test(priority = 4, description = "Add and verify device for SBI's")
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
		addDevices(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION);
		addDevicePage.clickOnBackToListOfSbiButton();

		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.NUMERIC);
		assertTrue(addDevicePage.isPendingForApprovalStatusDisplayed(), GlobalConstants.isStatusDisplayed);
		assertEquals(listOfDevicesPage.isAddDeviceButtonEnabled(), false);
		addDevicePage.clickOnListOfSbiButton();

		listOfSbiPage.clickOnViewDeviceButton(GlobalConstants.AUTOMATION);
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

		addDevices(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION);
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
		addDevices(GlobalConstants.FINGER, GlobalConstants.SINGLE, GlobalConstants.AUTOMATION_UPPERCASE,
				GlobalConstants.AUTOMATION_UPPERCASE);
		addDevices(GlobalConstants.IRIS, GlobalConstants.SINGLE, GlobalConstants.AUTOMATION_LOWERCASE,
				GlobalConstants.AUTOMATION_LOWERCASE);
		addDevices(GlobalConstants.FINGER, GlobalConstants.SLAP, GlobalConstants.SPECIAL_CHARACTERS,
				GlobalConstants.SPECIAL_CHARACTERS);
		addDevices(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS, GlobalConstants.ALPHANUMERIC,
				GlobalConstants.AUTOMATION);
		addDevices(GlobalConstants.FINGER, GlobalConstants.TOUCHLESS, GlobalConstants.AUTOMATION,
				GlobalConstants.ALPHANUMERIC);

		addMultipleDevices(GlobalConstants.IRIS, GlobalConstants.SINGLE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION, 1);
		verifyAddDeviceLabelsAndPageDetails();
		assertTrue(addDevicePage.isMandatoryMessageDisplayed(), GlobalConstants.isMandatoryMessageDisplayed);
		assertTrue(addDevicePage.isDeleteButtonEnabled(), GlobalConstants.isDeleteButtonEnabled);

		fillDeviceDetailsWithPosition(GlobalConstants.IRIS, GlobalConstants.SINGLE, GlobalConstants.AUTOMATION_2,
				GlobalConstants.AUTOMATION_2, 2);
		addDevicePage.clickOnDeleteButton();
		addDevicePage.clickOnBackToDevices();
		assertEquals(listOfDevicesPage.isAddedDeviceDisplayed(GlobalConstants.IRIS, GlobalConstants.SINGLE,
				GlobalConstants.AUTOMATION_2, GlobalConstants.AUTOMATION_2), false);

		listOfDevicesPage.clickOnAddDeviceButtonFromDeviceList();
		addMultipleDevices(GlobalConstants.FACE, GlobalConstants.FULL_FACE, GlobalConstants.AUTOMATION,
				GlobalConstants.AUTOMATION, 25);
		assertEquals(addDevicePage.getMaximumDeviceAlert(), GlobalConstants.MAXIMUM_DEVICE_ALERT_MESSAGE);
		addDevicePage.clickOnCancel();
		verifyAddDeviceLabelsAndPageDetails();
		assertTrue(addDevicePage.isTwentyFifthDeviceIsDisplayed(), GlobalConstants.isCreatedTwentyFifthDeviceDisplayed);
		addDevicePage.clickOnAddDevice();
//		addDevicePage.clickOnConfirm();
		verifyAddDeviceLabelsAndPageDetails();
		assertTrue(addDevicePage.isMandatoryMessageDisplayed(), GlobalConstants.isMandatoryMessageDisplayed);
		verifyInitialDevicePage();
		addDevicePage.clickOnBackToDevices();
		assertTrue(
				listOfDevicesPage.isAddedDeviceDisplayed(GlobalConstants.FACE, GlobalConstants.FULL_FACE,
						GlobalConstants.AUTOMATION_25, GlobalConstants.AUTOMATION_25),
				GlobalConstants.isCreatedTwentyFifthDeviceDisplayed);

		assertEquals(listOfDevicesPage.getListOFDevicesTitle(), GlobalConstants.LIST_OF_DEVICES_TITLE);

		listOfDevicesPage.clickOnDeviceThreeDots(GlobalConstants.FINGER, GlobalConstants.SLAP,
				GlobalConstants.CHARACTERS_1, GlobalConstants.CHARACTERS_1);

	}

	@Test(priority = 5, description = "Approve and reject the devices as admin")
	public void approveAndRejectDevices() {
		dashboardpage = new DashboardPage(driver);

	}

	@Test(priority = 5, description = "Verify List Of SBI Devices Page and Deactivate Sbi")
	public void verifySbiDevicesPage() {

		dashboardpage = new DashboardPage(driver);
		loginAsDeviceProvider();

		deviceProviderPage = dashboardpage.clickOnDeviceProviderServicesTitle();
		listOfSbiPage = new ListOfSbiPage(driver);

		assertEquals(listOfSbiPage.getListOfSbiMessage(), GlobalConstants.LIST_OF_SBI_PAGE_MESSAGE);
		assertTrue(listOfSbiPage.isDeviceApprovedCountDisplayed(), GlobalConstants.isDeviceApprovedCountDisplayed);
		assertTrue(listOfSbiPage.isDevicePendingForApprovalCountDisplayed(),
				GlobalConstants.isDevicePendingForApprovalCountDisplayed);
		assertEquals(listOfSbiPage.isAddDeviceButtonEnabled(GlobalConstants.NUMERIC), false);
		assertEquals(listOfSbiPage.isAddDeviceButtonEnabled(GlobalConstants.AUTOMATION), true);
		assertEquals(listOfSbiPage.getSbiListArrowDirection(), GlobalConstants.SBI_DETAIL_ARROW_VALUE);

		verifySbiDetails(GlobalConstants.NUMERIC);

		assertEquals(listOfSbiPage.isPartnerIdTextDisplayed(), false);

		listOfSbiPage.clickOnThreeDotsOfSbiList(GlobalConstants.NUMERIC);
		assertEquals(listOfSbiPage.isDeactivateOptionEnabled(), false);

		listOfSbiPage.clickOnThreeDotsOfSbiList(GlobalConstants.ALPHANUMERIC);
		assertTrue(listOfSbiPage.isDeactivateOptionEnabled(), GlobalConstants.isDeactivateSbiEnabled);
		listOfSbiPage.clickOnDeactivateSbi();
		assertTrue(listOfSbiPage.isDeactivateSbiPopupTitleDisplayed(), GlobalConstants.isDeactivateSbiEnabled);
		listOfSbiPage.clickOnDeactivateSubmit();
		assertTrue(listOfSbiPage.isDeactivatedStatusDisplayed(GlobalConstants.ALPHANUMERIC),
				GlobalConstants.isStatusDisplayed);
		assertEquals(listOfSbiPage.isAddDeviceButtonEnabled(GlobalConstants.ALPHANUMERIC), false);
		verifySbiDetails(GlobalConstants.ALPHANUMERIC);

		assertEquals(listOfSbiPage.isViewDeviceButtonEnabled(GlobalConstants.ALPHANUMERIC), true);

		System.out.print("Stop");

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
		assertTrue(listOfSbiPage.isSbiCreationDateDisplayed(), GlobalConstants.isSbiDetailsAreDisplayed);
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

}
