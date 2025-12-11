package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.RegisterPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class DevicePartnerCreation extends BaseClass {

	private DashboardPage dashboardpage;
	private LoginPage loginpage;
	private PartnerCertificatePage partnerCertificatePage;
	private RegisterPage registerPage;

	@Test(priority = 1, description = "This is a test case register new device user")
	public void registerDevicePartnerUser() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		registerPage = new RegisterPage(driver);

//		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(),
//				GlobalConstants.isTermsAndConditionsPopUppDisplayed);
//		dashboardpage.clickOnCheckbox();
//
//		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
//		dashboardpage.clickOnProceedButton();

		dashboardpage.clickOnProfileDropdown();
		loginpage = dashboardpage.clickOnLogoutButton();
		loginpage.clickRegisterButton();
		registerPage.enterFirstName("pmpui-device");
		registerPage.enterLastName("pmpui-device");
		registerPage.enterOrganizationName("AABBCC");
		registerPage.selectDeviceProviderInPartnerTypeDropdown();
		registerPage.enterAddress("0" + data + "device");
		registerPage.enterEmail("0" + data + "device" + "@gmail.com");
		registerPage.enterPhone("9876543010");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername("pmpui-device");
		registerPage.enterPassword("mosip123");
		registerPage.enterPasswordConfirm("mosip123");
		dashboardpage = registerPage.clickSubmitButton();

		assertTrue(dashboardpage.isTermsAndConditionsPopupDisplayed(),
				GlobalConstants.isTermsAndConditionsPopUpDisplayed);
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

		assertTrue(partnerCertificatePage.isPartnerDomainTypeLabelDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		assertTrue(partnerCertificatePage.isPartnerCertOvelayDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);

		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnRemoveCertificateButton();

		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isDeviceProviderSuccessMessage(),
				GlobalConstants.isDeviceProviderSucessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();

		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();
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
		partnerCertificatePage.clickOnSuccessMsgCloseButton();
	}

}
