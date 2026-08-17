package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.FtmPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "DeactivateFtmPartnerCreation" }, groups = { "DeactivatedFtmProviderTest" })
public class DeactivatedFtmProviderTest extends BaseClass {

	private DashboardPage dashboardPage;
	private PartnerCertificatePage partnerCertificatePage;
	private FtmPage ftmPage;

	public DeactivatedFtmProviderTest() {
		userid = GlobalConstants.DEACTIVATE_FTM_PARTNER_ID;
	}

	@Test(priority = 1, description = "Verify Partner is not able to create New FTM after Partner is deactivated")
	public void deactivatedFtmProviderCannotCreateFtm() {
		dashboardPage = new DashboardPage(driver);
		ftmPage = new FtmPage(driver);

		handleTermsAndCondition();
		dashboardPage.clickOnDashboardFtmChipproviderCardHeader();

		ftmPage.clickOnAddFtmButtonWioutRecord();
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOMATION);
		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOMATION);
		ftmPage.clickOnAddFtmSubmitButton();

		assertTrue(ftmPage.isAddFtmErrorMessageDisplayed(),
				GlobalConstants.isFtmCreationRejectedForDeactivatedPartner);
	}

	@Test(priority = 2, description = "Verify Partner is not able to FTM certificate upload / reupload / download after Partner is deactivated")
	public void deactivatedFtmProviderCannotManageFtmCertificate() {
		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		dashboardPage.clickOnDashboardPartnerCertificateListHeader();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		assertTrue(partnerCertificatePage.isPartnerCertificateReuploadButtonDisplayed(),
				GlobalConstants.isCertificateReuploadButtonDisabled);
		assertTrue(partnerCertificatePage.isPartnerCertificateReuploadButtonGreyedOut(),
				GlobalConstants.isCertificateReuploadButtonGreyedOut);
		assertFalse(partnerCertificatePage.isPartnerCertificateReuploadButtonEnabled(),
				GlobalConstants.isCertificateReuploadButtonDisabled);
		assertTrue(partnerCertificatePage.isDownloadButtonDisplayed(),
				GlobalConstants.isCertificateDownloadButtonDisabledForDeactivatedPartner);
		assertFalse(partnerCertificatePage.isDownloadButtonEnabled(),
				GlobalConstants.isCertificateDownloadButtonDisabledForDeactivatedPartner);
	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayedQuick()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}

}
