package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.RegisterPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "DevicePartnerCreation" })
public class DevicePartnerCreation extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginpage;
	private PartnerCertificatePage partnerCertificatePage;
	private RegisterPage registerPage;

	@Test(priority = 1, description = "This is a test case register new device user")
	public void registerDevicePartnerUser() throws InterruptedException {
		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		registerPage = new RegisterPage(driver);

//		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(),
//				GlobalConstants.isTermsAndConditionsPopUppDisplayed);
//		dashboardpage.clickOnCheckbox();
//
//		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
//		dashboardpage.clickOnProceedButton();

		dashboardPage.clickOnProfileDropdown();
		loginpage = dashboardPage.clickOnLogoutButton();
		loginpage.clickRegisterButton();
		registerPage.enterFirstName(GlobalConstants.DEVICE_PARTNER_ID);
		registerPage.enterLastName(GlobalConstants.DEVICE_PARTNER_ID);
		registerPage.enterOrganizationName(GlobalConstants.ORGANISATION_NAME);
		registerPage.selectDeviceProviderInPartnerTypeDropdown();
		registerPage.enterAddress("0" + data + "device");
		registerPage.enterEmail("0" + data + "device" + "@gmail.com");
		registerPage.enterPhone("9876543010");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername(GlobalConstants.DEVICE_PARTNER_ID);
		registerPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		registerPage.enterPasswordConfirm(GlobalConstants.PARTNER_PASSWORD);
		dashboardPage = registerPage.clickSubmitButton();

		handleTermsAndCondition();

		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);

		dashboardPage.clickOnPartnerCertificateTitle();

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
		dashboardPage = partnerCertificatePage.clickOnHomeButton();

		dashboardPage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();

		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
//		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateSubTextDisplayed(), GlobalConstants.isReUploadPartnerCertificateSubTextDisplayed);

		assertTrue(partnerCertificatePage.isPartnerDomainTypeLabelDisplayed(),
				GlobalConstants.isPartnerDomainTypeLabelDisplayed);
		assertTrue(partnerCertificatePage.isPartnerCertOverlayDisplayed(),
				GlobalConstants.isPartnerCertOverlayDisplayed);

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

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayed()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}

}
