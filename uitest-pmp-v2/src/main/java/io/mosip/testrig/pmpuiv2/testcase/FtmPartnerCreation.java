package io.mosip.testrig.pmpuiv2.testcase;

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

public class FtmPartnerCreation extends BaseClass {

	private BasePage basePage;
	private DashboardPage dashboardpage;
	private PartnerCertificatePage partnerCertificatePage;
	private RegisterPage registerPage;
	private FtmPage ftmPage;
	private LoginPage loginpage;

	@Test(priority = 1, description = "Register ftm partner without uploading certificate")
	public void registerNewUserForFtmNoCert() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		registerPage = new RegisterPage(driver);
		ftmPage = new FtmPage(driver);

		dashboardpage.clickOnProfileDropdown();
		loginpage = dashboardpage.clickOnLogoutButton();
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
		dashboardpage = registerPage.clickSubmitButton();

		dashboardpage.clickOnCheckbox();
		dashboardpage.clickOnProceedButton();

		assertTrue(partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed(),
				GlobalConstants.isDashboardFtmChipProviderCardDisplayed);

		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmButtonWioutRecord();

		ftmPage.clickOnAddFtmPartnerIdForNoCert();
		assertTrue(ftmPage.isNoDataAvailableMessageDisplayed(), GlobalConstants.isNoDataTextDisplaed);

		ftmPage.EnterInAddFtmMakeBox(data);
		assertTrue(ftmPage.isAutoPopulatedMessageDisplayed(), GlobalConstants.isAutoPopulatedTextDisplaed);

		ftmPage.EnterInAddFtmModelBox(data);
		ftmPage.clickOnAddFtmSubmitButton();

		ftmPage.clickOnAddFtmPartnerIdInfo();
		assertTrue(ftmPage.isInfoMessageDisplayed(), GlobalConstants.isInfoMessageDisplayed);

		ftmPage.EnterInAddFtmMakeBox(" ");
		ftmPage.EnterInAddFtmModelBox(" ");
		ftmPage.clickOnAddFtmSubmitButton();
		assertTrue(ftmPage.isInfoMessageDisplayed(), GlobalConstants.isInfoMessageDisplayed);

		ftmPage.EnterInAddFtmMakeBox(data);
		ftmPage.EnterInAddFtmModelBox(data);
		basePage.navigateBack();

		ftmPage.clickOnBlockMesssageProceed();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		ftmPage.clickOnAddFtmButtonWioutRecord();
		ftmPage.EnterInAddFtmMakeBox(data);
		ftmPage.EnterInAddFtmModelBox(data);

		ftmPage.clickOnSideNavHomeIcon();
		ftmPage.clickOnBlockMesssageProceed();

		assertTrue(partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed(),
				GlobalConstants.isDashboardFtmChipProviderCardDisplayed);
	}

	@Test(priority = 2, description = "Register ftm partner with valid certificate")
	public void registerNewUserForFtm() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		registerPage = new RegisterPage(driver);

//		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(), GlobalConstants.isTermsAndConditionsPopUppDisplayed);
//		dashboardpage.clickOnCheckbox();
//
//		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
//		dashboardpage.clickOnProceedButton();

		dashboardpage.clickOnRootOFTrustCertText();
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

		dashboardpage.clickOnProfileDropdown();
		loginpage = dashboardpage.clickOnLogoutButton();
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
		dashboardpage = registerPage.clickSubmitButton();

		assertTrue(dashboardpage.isTermsAndConditionsPopupDisplayed(),
				GlobalConstants.isTermsAndConditionsPopUpDisplayed);
		dashboardpage.clickOnCheckbox();
		dashboardpage.clickOnProceedButton();

		dashboardpage.clickOnDashboardPartnerCertificateListHeader();

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
		dashboardpage = partnerCertificatePage.clickOnHomeButton();

		dashboardpage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();

		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
//		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateSubTextDisplayed(), GlobalConstants.isReUploadPartnerCertificateSubTextDisplayed);

		assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(), GlobalConstants.isPleaseTabTextDisplayed);
		assertTrue(partnerCertificatePage.isCertFormatesTextDisplayed(), GlobalConstants.isCertFormateDisplayed);
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
				GlobalConstants.isSuccessMessageDisplayed);
	}

}
