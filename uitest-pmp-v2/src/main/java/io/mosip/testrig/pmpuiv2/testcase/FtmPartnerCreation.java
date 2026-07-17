package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.FtmPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.RegisterPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "FtmPartnerCreation" })
public class FtmPartnerCreation extends BaseClass {

	private BasePage basePage;
	private DashboardPage dashboardPage;
	private PartnerCertificatePage partnerCertificatePage;
	private RegisterPage registerPage;
	private FtmPage ftmPage;
	private LoginPage loginpage;

	@Test(priority = 1, description = "Register ftm partner without uploading certificate")
	public void registerNewUserForFtmNoCert() throws InterruptedException {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		registerPage = new RegisterPage(driver);
		ftmPage = new FtmPage(driver);

		dashboardPage.clickOnProfileDropdown();
		loginpage = dashboardPage.clickOnLogoutButton();
		loginpage.clickRegisterButton();
		registerPage.enterFirstName(GlobalConstants.FTM_NOCERT_USER);
		registerPage.enterLastName(GlobalConstants.FTM_NOCERT_USER);
		registerPage.enterOrganizationName(GlobalConstants.ORGANISATION_NAME);
		registerPage.selectPartnerTypeDropdown(1);
		registerPage.enterAddress("0" + data);
		registerPage.enterEmail("0" + data + "ftmnocert" + "@gmail.com");
		registerPage.enterPhone("9876544211");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername(GlobalConstants.FTM_NOCERT_USER);
		registerPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		registerPage.enterPasswordConfirm(GlobalConstants.PARTNER_PASSWORD);
		dashboardPage = registerPage.clickSubmitButton();

		handleTermsAndCondition();

		assertTrue(partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed(),
				GlobalConstants.isDashboardFtmChipProviderCardDisplayed);

		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmButtonWioutRecord();

		ftmPage.clickOnAddFtmPartnerIdForNoCert();
		assertTrue(ftmPage.isNoDataAvailableMessageDisplayed(), GlobalConstants.isNoDataTextDisplaed);

		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOFTM);
		assertTrue(ftmPage.isAutoPopulatedMessageDisplayed(), GlobalConstants.isAutoPopulatedTextDisplaed);

		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOFTM);
		assertEquals(ftmPage.isAddFtmSubmitButtonEnabled(), false);

		ftmPage.clickOnAddFtmPartnerIdInfo();
		assertTrue(ftmPage.isInfoMessageDisplayed(), GlobalConstants.isInfoMessageDisplayed);

		ftmPage.EnterInAddFtmMakeBox(" ");
		ftmPage.EnterInAddFtmModelBox(" ");
		assertTrue(ftmPage.isInfoMessageDisplayed(), GlobalConstants.isInfoMessageDisplayed);

		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOFTM);
		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOFTM);
		basePage.navigateBack();

		ftmPage.clickOnBlockMesssageProceed();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		ftmPage.clickOnAddFtmButtonWioutRecord();
		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOFTM);
		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOFTM);

		ftmPage.clickOnSideNavHomeIcon();
		ftmPage.clickOnBlockMesssageProceed();

		assertTrue(partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed(),
				GlobalConstants.isDashboardFtmChipProviderCardDisplayed);
	}

	@Test(priority = 2, description = "Register ftm partner with valid certificate", dependsOnMethods = "registerNewUserForFtmNoCert")
	public void registerNewUserForFtm() throws InterruptedException {
		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		registerPage = new RegisterPage(driver);

		dashboardPage.clickOnRootOFTrustCertText();
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionFtm();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		partnerCertificatePage.clickOnIntermediateCACertTab();
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionFtm();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		dashboardPage.clickOnProfileDropdown();
		loginpage = dashboardPage.clickOnLogoutButton();
		loginpage.clickRegisterButton();
		registerPage.enterFirstName(GlobalConstants.FTM_PARTNER_ID);
		registerPage.enterLastName(GlobalConstants.FTM_PARTNER_ID);
		registerPage.enterOrganizationName(GlobalConstants.ORGANISATION_NAME);
		registerPage.selectPartnerTypeDropdown(1);
		registerPage.enterAddress("0" + data);
		registerPage.enterEmail("0" + data + "ftm" + "@gmail.com");
		registerPage.enterPhone("9876544210");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername(GlobalConstants.FTM_PARTNER_ID);
		registerPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		registerPage.enterPasswordConfirm(GlobalConstants.PARTNER_PASSWORD);
		dashboardPage = registerPage.clickSubmitButton();

		assertTrue(dashboardPage.isTermsAndConditionsPopupDisplayed(),
				GlobalConstants.isTermsAndConditionsPopUpDisplayed);
		dashboardPage.clickOnCheckbox();
		dashboardPage.clickOnProceedButton();

		dashboardPage.clickOnDashboardPartnerCertificateListHeader();

		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.clickOnUploadButton();

		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();

		assertTrue(partnerCertificatePage.isSuccessMessageForFtmCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOnSuccessMsgCloseButton();
		partnerCertificatePage.certifiCateUploadCancelButton();
		dashboardPage = partnerCertificatePage.clickOnHomeButton();

		dashboardPage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();

		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
//		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateSubTextDisplayed(), GlobalConstants.isReUploadPartnerCertificateSubTextDisplayed);

		assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(), GlobalConstants.isPleaseTabTextDisplayed);
		assertTrue(partnerCertificatePage.isPartnercertFormatesTextDisplayed(), GlobalConstants.isCertFormateDisplayed);
		assertTrue(partnerCertificatePage.isLastUploadTimeAndDateTextDisplayed(),
				GlobalConstants.isLastUploadTimeAndDateDisplayed);

		assertTrue(partnerCertificatePage.isPartnerDomainTypeLabelDisplayed(),
				GlobalConstants.isPartnerDomainTypeLabelDisplayed);
		assertTrue(partnerCertificatePage.isPartnerCertOverlayDisplayed(),
				GlobalConstants.isPartnerCertOverlayDisplayed);

		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnRemoveCertificateButton();

		partnerCertificatePage.uploadCertificateInvalidCert();
		assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(),
				GlobalConstants.isInvalidCertFormatePopupDisplayed);

		partnerCertificatePage.clickOnCertificateUploadCancelButton();

		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmCertDisplayed(),
				GlobalConstants.isSuccessMessageForFtmCertDisplayed);
	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayed()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}

}
