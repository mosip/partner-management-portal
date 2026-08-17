package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;

@Test(dependsOnGroups = { "PartnerDeactivateOptionTest" }, groups = { "PartnerDeactivatedPortalTest" })
public class PartnerDeactivatedPortalTest extends BaseClass {

	private DashboardPage dashboardPage;
	private PartnerCertificatePage partnerCertificatePage;
	private PoliciesPage policiesPage;

	public PartnerDeactivatedPortalTest() {
		userid = GlobalConstants.PARTNERDETAILS_USER_ID;
	}

	@Test(priority = 1, description = "Verify the Partner certificate section for Deactivated Partner")
	public void certificateSectionIsGreyedOutForDeactivatedPartner() {
		openPartnerCertificateScreen();

		// The section stays visible - only its controls lose their active styling
		assertTrue(partnerCertificatePage.isPartnerCertificateReuploadButtonDisplayed(),
				GlobalConstants.isCertificateReuploadButtonGreyedOut);
		assertTrue(partnerCertificatePage.isPartnerCertificateReuploadButtonGreyedOut(),
				GlobalConstants.isCertificateReuploadButtonGreyedOut);
		assertTrue(partnerCertificatePage.isDownloadButtonDisplayed(),
				GlobalConstants.isCertificateDownloadButtonDisabledForDeactivatedPartner);
		assertFalse(partnerCertificatePage.isDownloadButtonEnabled(),
				GlobalConstants.isCertificateDownloadButtonDisabledForDeactivatedPartner);
	}

	@Test(priority = 2, description = "Verify partner is not able to upload / reupload / download partner certificate after deactivation")
	public void partnerCannotReuploadOrDownloadCertificateAfterDeactivation() {
		openPartnerCertificateScreen();

		assertTrue(partnerCertificatePage.isPartnerCertificateReuploadButtonDisplayed(),
				GlobalConstants.isCertificateReuploadButtonDisabled);
		assertFalse(partnerCertificatePage.isPartnerCertificateReuploadButtonEnabled(),
				GlobalConstants.isCertificateReuploadButtonDisabled);

		assertTrue(partnerCertificatePage.isDownloadButtonDisplayed(),
				GlobalConstants.isCertificateDownloadButtonDisabledForDeactivatedPartner);
		assertFalse(partnerCertificatePage.isDownloadButtonEnabled(),
				GlobalConstants.isCertificateDownloadButtonDisabledForDeactivatedPartner);
	}

	@Test(priority = 4, description = "Verify the status of already created records after partner is deactivated")
	public void existingCertificateRecordSurvivesDeactivation() {
		openPartnerCertificateScreen();

		String uploadedOn = partnerCertificatePage.getCertificateUploadedDateInPartnerPortal();
		String expiresOn = partnerCertificatePage.getCertificateExpiryDateInPartnerPortal();

		assertFalse(uploadedOn.isEmpty(), GlobalConstants.isExistingCertificateRecordIntactAfterDeactivation);
		assertFalse(expiresOn.isEmpty(), GlobalConstants.isExistingCertificateRecordIntactAfterDeactivation);
		LogUtil.step("Certificate record after deactivation - uploaded: " + uploadedOn + ", expires: " + expiresOn);
	}

	@Test(priority = 3, description = "Verify Partner is not able to create New policy request after Partner is deactivated")
	public void partnerCannotCreateNewPolicyRequestAfterDeactivation() {
		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);

		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		handleTermsAndCondition();
		dashboardPage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesPageDisplayed(), GlobalConstants.isPoliciesPageDisplayed);

		policiesPage.clickOnRequestPolicyButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		policiesPage.enterComments(GlobalConstants.DEFAULT_POLICY);
		policiesPage.clickSubmitButton();

		assertFalse(policiesPage.isPolicySubmittedSuccessfullyDisplayedQuick(),
				GlobalConstants.isPolicyRequestRejectedForDeactivatedPartner);
		assertTrue(policiesPage.isRequestPolicyErrorMessageDisplayed(),
				GlobalConstants.isPolicyRequestErrorShownForDeactivatedPartner);
	}

	private void openPartnerCertificateScreen() {
		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		handleTermsAndCondition();

		dashboardPage.clickOnPartnerCertificateTitle();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayedForDeactivatedPartner);
	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayedQuick()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}

}
