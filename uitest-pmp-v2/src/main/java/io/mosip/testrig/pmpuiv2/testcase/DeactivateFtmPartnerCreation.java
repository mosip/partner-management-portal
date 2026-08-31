package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerAdminPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.RegisterPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "DeactivateFtmPartnerCreation" })
public class DeactivateFtmPartnerCreation extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private PartnerAdminPage partnerAdminPage;
	private PartnerCertificatePage partnerCertificatePage;
	private RegisterPage registerPage;

	@Test(priority = 1, description = "Register an FTM Chip Provider reserved for deactivation scenarios")
	public void registerDeactivateFtmPartnerUser() {
		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		registerPage = new RegisterPage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(partnerCertificatePage.isUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionFtm();
		partnerCertificatePage.uploadDeactivateFtmRootCaCert();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		assertTrue(partnerCertificatePage.isUploadedSuccessfullyMessageDisplayed(),
				GlobalConstants.isUploadedSuccessfullyMessageDisplayed);
		partnerCertificatePage.clickOnGoBackButton();

		partnerCertificatePage.clickOnIntermediateCACertTab();
		assertTrue(partnerCertificatePage.isIntermediateUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionFtm();
		partnerCertificatePage.uploadDeactivateFtmIntermediateCaCert();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.clickRegisterButton();

		registerPage.enterFirstName(GlobalConstants.DEACTIVATE_FTM_PARTNER_ID);
		registerPage.enterLastName(GlobalConstants.DEACTIVATE_FTM_PARTNER_ID);
		registerPage.enterOrganizationName(GlobalConstants.ORGANISATION_NAME);
		registerPage.selectPartnerTypeDropdown(1);
		registerPage.enterAddress("0" + data + "deactftm");
		registerPage.enterEmail("0" + data + "deactftm" + "@gmail.com");
		registerPage.enterPhone("9876544210");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername(GlobalConstants.DEACTIVATE_FTM_PARTNER_ID);
		registerPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		registerPage.enterPasswordConfirm(GlobalConstants.PARTNER_PASSWORD);
		dashboardPage = registerPage.clickSubmitButton();

		handleTermsAndCondition();

		// Wait for the dashboard to settle here - clicking right after the consent popup raced the page.
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
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
	}

	@Test(priority = 2, description = "Deactivate the reserved FTM Chip Provider", dependsOnMethods = "registerDeactivateFtmPartnerUser")
	public void deactivateFtmPartner() {
		dashboardPage = new DashboardPage(driver);
		partnerAdminPage = new PartnerAdminPage(driver);

		dashboardPage.clickOnPartners();
		assertTrue(partnerAdminPage.isPartnerListLoaded(), GlobalConstants.isPartnerListLoaded);

		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.enterPartnerIdInFilter(GlobalConstants.DEACTIVATE_FTM_PARTNER_ID);
		partnerAdminPage.clickOnStatusFilter();
		partnerAdminPage.clickActivatedButton();
		partnerAdminPage.clickOnApplyFiltersBtn();
		assertTrue(partnerAdminPage.isPartnerListLoaded(GlobalConstants.DEACTIVATE_FTM_PARTNER_ID),
				GlobalConstants.isFilteredPartnerListLoaded);

		partnerAdminPage.clickOnActionsButton();
		partnerAdminPage.clickOnDeactivateOptionInActionMenu();
		assertTrue(partnerAdminPage.isDeactivatePopupHeaderDisplayed(),
				GlobalConstants.isDeactivatePopupHeaderDisplayed);
		partnerAdminPage.clickOnDeactivatePopupConfirmButton();

		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.DEACTIVATED,
				GlobalConstants.isPartnerStatusDeactivatedAfterConfirm);
	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayed()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}

}
