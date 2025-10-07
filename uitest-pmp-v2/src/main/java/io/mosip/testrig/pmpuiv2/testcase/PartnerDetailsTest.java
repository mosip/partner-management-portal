package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;

import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;

import io.mosip.testrig.pmpuiv2.pages.partnersAdmin;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class PartnerDetailsTest extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private BasePage basePage;
	private partnersAdmin partnerAdmin;

	@Test(priority = 2, description = "view individual partner details in tabular form")
	public void partnerIndividualDetailsVerification() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		loginPage = new LoginPage(driver);
		partnerAdmin = new partnersAdmin(driver);

		assertTrue(partnerAdmin.isPartnersButtonDisplayed(), GlobalConstants.isPartnersButtonDisplayed);
		partnerAdmin.clickOnPartnersTab();
		assertTrue(partnerAdmin.isSubTitleOfTabularViewsDisplayed(), GlobalConstants.isSubTitleOfTabularViewsDisplayed);
		assertTrue(partnerAdmin.isBreadcrumbsDisplayed(), GlobalConstants.isBreadcrumbsDisplayed);
		assertTrue(partnerAdmin.isTitlePartnerDisplayed(), GlobalConstants.isTitlePartnerDisplayed);
		assertTrue(partnerAdmin.isOrganisationNameHeaderTagDisplayed(),
				GlobalConstants.isOrganisationNameHeaderTagDisplayed);
		assertTrue(partnerAdmin.isPolicyGroupHeaderTagDisplayed(), GlobalConstants.isPolicyGroupHeaderTagDisplayed);
		assertTrue(partnerAdmin.isEmailAddressHeaderTagDisplayed(), GlobalConstants.isEmailAddressHeaderTagDisplayed);
		assertTrue(partnerAdmin.isBackButtonAccessible(), GlobalConstants.isBackButtonAccessible);
		assertTrue(partnerAdmin.isMosipIconsDisplayed(), GlobalConstants.isMosipIconsDisplayed);
		assertTrue(partnerAdmin.isHamburgersIconDisplayed(), GlobalConstants.isHamburgersIconDisplayed);
		assertTrue(partnerAdmin.isFooterMosipTextsDisplayed(), GlobalConstants.isFooterMosipTextsDisplayed);
		assertTrue(partnerAdmin.isFooterDocumentationsDisplayed(), GlobalConstants.isFooterDocumentationsDisplayed);
		assertTrue(partnerAdmin.isFootersContactUsDisplayed(), GlobalConstants.isFootersContactUsDisplayed);
		partnerAdmin.clickOnActionsButton();
		assertTrue(partnerAdmin.isViewButtonsDisplayed(), GlobalConstants.isViewButtonsDisplayed);
		partnerAdmin.clickOnViewPartnerDetailsScreen();
		assertTrue(partnerAdmin.isViewPartnersDetailsPageDisplayed(),
				GlobalConstants.isViewPartnersDetailsPageDisplayed);
		assertTrue(partnerAdmin.isBreadcrumbsDisplayed(), GlobalConstants.isBreadcrumbsDisplayed);
		assertTrue(partnerAdmin.isPartnerIdDisplayed(), GlobalConstants.isPartnerIdDisplayed);
		assertTrue(partnerAdmin.isPartnerStatusInViewPartnerPageDisplayed(),
				GlobalConstants.isPartnerStatusInViewPartnerPageDisplayed);
		assertTrue(partnerAdmin.isPartnerCreatedDateInViewPartnerPageDisplayed(),
				GlobalConstants.isPartnerCreatedDateInViewPartnerPageDisplayed);
		assertTrue(partnerAdmin.isPartnerCertificateDisplayed(), GlobalConstants.isPartnerCertificateDisplayed);
		assertTrue(partnerAdmin.isPartnerTypeInViewPartnerPageDisplayed(),
				GlobalConstants.isPartnerTypeInViewPartnerPageDisplayed);
		assertTrue(partnerAdmin.isDeviceProviderInViewPartnerPageDisplayed(),
				GlobalConstants.isDeviceProviderInViewPartnerPageDisplayed);
		assertTrue(partnerAdmin.isExpiryDateTimeDisplayed(), GlobalConstants.isExpiryDateTimeDisplayed);
		assertTrue(partnerAdmin.isTimeOfUploadDisplayed(), GlobalConstants.isTimeOfUploadDisplayed);
		assertTrue(partnerAdmin.isDownloadCertificateButtonDisplayed(),
				GlobalConstants.isDownloadCertificateButtonDisplayed);
		partnerAdmin.clickOnDownloadCertificateButtonInViewPartnerPage();
		assertTrue(partnerAdmin.isOriginalCertificateDropdownDisplayed(),
				GlobalConstants.isOriginalCertificateDropdownDisplayed);
		assertTrue(partnerAdmin.isMosipSignedCertificateDropdownDisplayed(),
				GlobalConstants.isMosipSignedCertificateDropdownDisplayed);
		partnerAdmin.clickOnOriginnalCertificateInViewPartnerPage();
		assertTrue(partnerAdmin.isSuccessMassageInOriginalCertificateDisplayed(),
				GlobalConstants.isSuccessMassageInOriginalCertificateDisplayed);
		partnerAdmin.clickOnMosipSignedCertificateInViewPartnerPage();
		assertTrue(partnerAdmin.isSuccessMassageInMosipSignedCertificateDisplayed(),
				GlobalConstants.isSuccessMassageInMosipSignedCertificateDisplayed);
		assertTrue(partnerAdmin.isGobackButtonInViewPatnerPageDisplayed(),
				GlobalConstants.isGobackButtonInViewPatnerPageDisplayed);
		partnerAdmin.clickOngobackButtonInPartnerDetailsPage();
		assertTrue(partnerAdmin.isSubTitleListDisplayed(), GlobalConstants.isSubTitleListDisplayed);
		partnerAdmin.clickOnAuthId();
		partnerAdmin.clickOnViewPartnerBackButton();
		assertTrue(partnerAdmin.isNoneditableInListOfPartnerDisplayed(),
				GlobalConstants.isNoneditableInListOfPartnerDisplayed);
		partnerAdmin.clickOnActionsButton();
		partnerAdmin.clickOnDeactivateButton();
		assertTrue(partnerAdmin.isDeactivatePartnerHeaderDisplayed(),
				GlobalConstants.isDeactivatePartnerHeaderDisplayed);
		assertTrue(partnerAdmin.isDeactivatePartnerDescriptionDisplayed(),
				GlobalConstants.isDeactivatePartnerDescriptionDisplayed);
		assertTrue(partnerAdmin.isDeactivateCancelButtonDisplayed(), GlobalConstants.isDeactivateCancelButtonDisplayed);
		assertTrue(partnerAdmin.isDeactivateConfirmButtonDisplayed(),
				GlobalConstants.isDeactivateConfirmButtonDisplayed);
		partnerAdmin.clickOnConfirmButton();
		assertTrue(partnerAdmin.isDeactivateColorCodeButtonDisplayed(),
				GlobalConstants.isDeactivateColorCodeButtonDisplayed);
		assertTrue(partnerAdmin.isCertificateUploadStatusHeaderTagDisplayed(),
				GlobalConstants.isCertificateUploadStatusHeaderTagDisplayed);

		partnerAdmin.clickOnActionsButton();
		partnerAdmin.clickOnViewButtonInListOfPartnerDetailsScreen();
		assertTrue(partnerAdmin.isViewPartnersDetailsPageDisplayed(),
				GlobalConstants.isViewPartnersDetailsPageDisplayed);

		assertTrue(partnerAdmin.isDisabledDownloadCertificateButtonDisplayed(),
				GlobalConstants.isDisabledDownloadCertificateButtonDisplayed);

	}

}
