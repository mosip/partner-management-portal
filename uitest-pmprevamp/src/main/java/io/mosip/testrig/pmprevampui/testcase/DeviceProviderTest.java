package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.fw.util.PmpTestUtil;
import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.DeviceProviderPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.PartnerCertificatePage;
import io.mosip.testrig.pmprevampui.pages.RegisterPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class DeviceProviderTest extends BaseClass {
	
	private DeviceProviderPage deviceProviderPage;

	@Test(priority = 1,description = "This is a test case register new device user")
	public void registerNewUser() throws InterruptedException {
		DashboardPage dashboardpage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		RegisterPage registerPage = new RegisterPage(driver);
		
		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(), GlobalConstants.isTermsAndConditionsPopUppDisplayed);
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
		registerPage.enterAddress("0"+ data + "device");

		assertTrue(registerPage.isEmailTextBoxDisplayed(), GlobalConstants.isEmailTextBoxDisplayed);
		registerPage.enterEmail("0"+ data + "device" + "@gmail.com");

		assertTrue(registerPage.isPhoneNumberTextboxDisplayed(), GlobalConstants.isPhoneNumberTextboxDisplayed);
		registerPage.enterPhone("9876543010");

		assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(), GlobalConstants.isNotificationLanguageDropdownDisplayed);
		registerPage.selectNotificationLanguageDropdown();

		assertTrue(registerPage.isUsernameTextBoxDisplayed(), GlobalConstants.isUsernameTextBoxDisplayed);
		registerPage.enterUsername("pmpui-device");

		assertTrue(registerPage.isPasswordTextBoxDisplayed(), GlobalConstants.isPasswordTextBoxDisplayed);
		registerPage.enterPassword("mosip123");

		assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(), GlobalConstants.isPasswordConfirmTextBoxDisplayed);
		registerPage.enterPasswordConfirm("mosip123");

		assertTrue(registerPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage = registerPage.clickSubmitButton();

		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(), GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardpage.clickOnCheckbox();

		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
		dashboardpage.clickOnProceedButton();
		
		Thread.sleep(3000);
		assertTrue(dashboardpage.isPartnerCertificateTitleDisplayed(), GlobalConstants.isPartnerCertificateTitleDisplayed);
		
		dashboardpage.clickOnPartnerCertificateTitle();

 		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(), GlobalConstants.isPartnerCertificatePageDisplayed);
 		partnerCertificatePage.clickOnUploadButton();

		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(), GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();

		assertTrue(partnerCertificatePage.isDeviceProviderSuccessMessage(), GlobalConstants.isDeviceProviderSucessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		dashboardpage = partnerCertificatePage.clickOnHomeButton();

		dashboardpage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();
		
		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(), GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
//		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateSubTextDisplayed(), GlobalConstants.isReUploadPartnerCertificateSubTextDisplayed);
		
		assertTrue(partnerCertificatePage.isPartnerDomainTypeDisplayed(), GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		assertTrue(partnerCertificatePage.isPartnerCertOvelayDisplayed(), GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isDeviceProviderSuccessMessage(), GlobalConstants.isDeviceProviderSucessMessageDisplayed);
		partnerCertificatePage.clickOnRemoveCertificateButton();
		
		partnerCertificatePage.uploadCertificateInvalidCert();
		assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(), GlobalConstants.isInvalidCertFormatePopupDisplayed);
		
		partnerCertificatePage.clickOnCertificateUploadCancelButton();
		
		partnerCertificatePage.clickOnDownloadButton();
		partnerCertificatePage.clickOnOriginalCertificateDownloadButton();
		assertTrue(partnerCertificatePage.isOriginalSignedCertDownloadedPopupDisplayed(), GlobalConstants.isOriginalCertificateDownloadPopupDisplayed);
		
		partnerCertificatePage.clickOnMosipSignedCertificateDownloadButton();
		assertTrue(partnerCertificatePage.isMosipSignedCertPopupDisplayed(), GlobalConstants.isMosipCertificateDownloadPopupDisplayed);

//		assertTrue(partnerCertificatePage.isSucessMessageDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.ClickOnsuccessMsgCloseButton();
	}
	
	@Test(priority = 2, description = "Creating SBI Device")
	public void CreateSbiDevice() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName("pmpui-device");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();

		assertTrue(dashboardpage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

		dashboardpage.clickOnHamburgerOpen();
		assertEquals(dashboardpage.getSideNavDeviceProviderTitle(), GlobalConstants.DEVICE_PROVIDER_TITLE);
		deviceProviderPage = dashboardpage.clickOnSideNavDeviceProvider();

		dashboardpage.clickOnHamburgerClose();
		deviceProviderPage.clickOnHome();

		dashboardpage.clickOnSideNavDeviceProvider();
		deviceProviderPage.clickOnHome();

		dashboardpage.clickOnDeviceProviderServicesTitle();
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
		assertTrue(deviceProviderPage.isPendingForApprovalDisplayed(GlobalConstants.AUTOMATION),
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
		// assertEquals(deviceProviderPage.cancelAlert(),
		// GlobalConstants.RELOAD_MESSAGE);

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
		assertTrue(deviceProviderPage.isCreatedSbiDisplayed(sbiVersion), GlobalConstants.isCreatedSbiDisplayed);
	}
	
}
