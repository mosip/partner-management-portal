package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeoutException;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.pages.RegisterPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(groups = { "AuthPartnerCreation" })
public class AuthPartnerCreation extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private PartnerCertificatePage partnerCertificatePage;
	private RegisterPage registerPage;
	private PoliciesPage policiesPage;
	private BasePage basePage;

	@Test(priority = 1, description = "This is a test case register new user")
	public void registerAuthPartnerUser() throws TimeoutException {

		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		loginPage = new LoginPage(driver);
		basePage = new BasePage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(partnerCertificatePage.isUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		partnerCertificatePage.clickOnIntermediateCACertTab();
		assertTrue(partnerCertificatePage.isIntermediateUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		logoutFromPartner();

		registerPage = loginPage.clickRegisterButton();

		registerPage.enterFirstName(GlobalConstants.AUTH_PARTNER_ID);
		registerPage.enterLastName(GlobalConstants.AUTH_PARTNER_ID);
		registerPage.enterOrganizationName(GlobalConstants.ORGANISATION_NAME);
		registerPage.selectPartnerTypeDropdown(2);
		registerPage.enterAddress("0" + data);
		registerPage.enterEmail("0" + data + "@gmail.com");
		registerPage.enterPhone("9876543210");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername(GlobalConstants.AUTH_PARTNER_ID);
		registerPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		registerPage.enterPasswordConfirm(GlobalConstants.PARTNER_PASSWORD);
		dashboardPage = registerPage.clickSubmitButton();

		dashboardPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		dashboardPage.clickOnSubmitButton();

		handleTermsAndCondition();

		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();

		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.clickOnUploadButton();

		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(),
				GlobalConstants.isPleaseTabToSelectTextDisplayed);
		assertTrue(partnerCertificatePage.isCertFormatesTextDisplayed(), GlobalConstants.isCertFormatesTextDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
		dashboardPage = partnerCertificatePage.clickOnHomeButton();

		dashboardPage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();

		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateSubTextDisplayed(),
				GlobalConstants.isReUploadPartnerCertificateSubTextDisplayed);

		assertTrue(partnerCertificatePage.isPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLabelDisplayed);
		assertTrue(partnerCertificatePage.isPartnerTypeValueDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(partnerCertificatePage.isPartnerTypeValueDisabled(), GlobalConstants.isPartnerTypeValueDisabled);

		assertTrue(partnerCertificatePage.isPartnerDomainTypeLabelDisplayed(),
				GlobalConstants.isPartnerDomainTypeDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainTypeValueDisplayed(),
				GlobalConstants.isPartnerDomainTypeValueDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainTypeValueDisabled(),
				GlobalConstants.isPartnerDomainTypeValueDisabled);

		assertTrue(partnerCertificatePage.isUploadCertificateIconDisplayed(),
				GlobalConstants.isUploadCertificateIconDisplayed);
		assertTrue(partnerCertificatePage.isCertFormatesTextDisplayed(), GlobalConstants.isCertFormatesTextDisplayed);

		assertTrue(partnerCertificatePage.isLastCertificateUploadDateDisplayed(),
				GlobalConstants.isLastCertificateUploadDateDisplayed);

		partnerCertificatePage.uploadCertificate();

		assertTrue(partnerCertificatePage.isUploadedCertificateNameDisplayed(),
				GlobalConstants.isUploadedCertificateNameDisplayed);
		assertTrue(partnerCertificatePage.isCertificateRemoveButtonDisplayed(),
				GlobalConstants.isCertificateRemoveButtonDisplayed);
		partnerCertificatePage.clickOnSubmitButton();

		assertTrue(partnerCertificatePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

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

//	    assertTrue(partnerCertificatePage.isSucessMessageDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.clickOnSuccessMsgCloseButton();
		partnerCertificatePage.clickOnTitleBackButton();
	}

	@Test(priority = 2, description = "Register auth user with out uploading certificates")
	public void registerAuthUserWithoutUploadingCertificates() throws InterruptedException {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		basePage = new BasePage(driver);

		logoutFromPartner();

		loginPage.clickRegisterButton();

		registerPage.enterFirstName("pmpui-nocert");
		registerPage.enterLastName("  ");
		registerPage.enterOrganizationName("AABBCC");
		registerPage.selectPartnerTypeDropdown(2);
		registerPage.enterAddress("0" + data);
		registerPage.enterEmail(data + "nocert" + "@gmail.com");
		registerPage.enterPhone("  ");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername("pmpui-nocert");
		registerPage.enterPassword("mosip123");
		registerPage.enterPasswordConfirm("mosip123");
		dashboardPage = registerPage.clickSubmitButton();

		assertTrue(registerPage.isPhoneNumberWarningMessageDisplayed(),
				GlobalConstants.isPhoneNumberWarningMessageDisplayed);
		registerPage.selectPartnerTypeDropdown(2);
		registerPage.enterPhone("8098768903");
		registerPage.enterPassword("mosip123");
		registerPage.enterPasswordConfirm("mosip123");
		dashboardPage = registerPage.clickSubmitButton();

		dashboardPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		dashboardPage.clickOnSubmitButton();

		handleTermsAndCondition();
		
	}

	private void logoutFromPartner() {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayed()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}

}
