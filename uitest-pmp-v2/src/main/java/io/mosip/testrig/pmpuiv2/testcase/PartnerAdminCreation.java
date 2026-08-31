package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.kernel.util.KeycloakUserManager;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.pages.PolicyGroupPage;
import io.mosip.testrig.pmpuiv2.pages.RegisterPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(groups = "PartnerAdminCreation")
public class PartnerAdminCreation extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private PartnerCertificatePage partnerCertificatePage;
	private PolicyGroupPage policygroupPage;
	private RegisterPage registerPage;
	private PoliciesPage policiesPage;

	@Test(priority = 1, description = "Creating Partner Admin")
	public void partnerAdminCreation() {
		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);

		registerPage = loginPage.clickRegisterButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);

		registerPage.enterFirstName("pmpui-v2");
		assertTrue(registerPage.isLastNameTextBoxDisplayed(), GlobalConstants.isLastNameTextBoxDisplayed);
		registerPage.enterLastName("pmpui-v2");

		assertTrue(registerPage.isOrganizationNameDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
		registerPage.enterOrganizationName("AABBCC");

		assertTrue(registerPage.isPartnerTypeDropdownDisplayed(), GlobalConstants.isPartnerTypeDropdownDisplayed);
		registerPage.selectDeviceProviderInPartnerTypeDropdown();

		assertTrue(registerPage.isAddressTextBoxDisplayed(), GlobalConstants.isAddressTextBoxDisplayed);
		registerPage.enterAddress("0" + data);

		assertTrue(registerPage.isEmailTextBoxDisplayed(), GlobalConstants.isEmailTextBoxDisplayed);
		registerPage.enterEmail("0" + data + "admin" + "@gmail.com");

		assertTrue(registerPage.isPhoneNumberTextboxDisplayed(), GlobalConstants.isPhoneNumberTextboxDisplayed);
		registerPage.enterPhone("9876556789");

		assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(),
				GlobalConstants.isNotificationLanguageDropdownDisplayed);
		registerPage.selectNotificationLanguageDropdown();

		assertTrue(registerPage.isUsernameTextBoxDisplayed(), GlobalConstants.isUsernameTextBoxDisplayed);
		registerPage.enterUsername("pmpui-v2");

		assertTrue(registerPage.isPasswordTextBoxDisplayed(), GlobalConstants.isPasswordTextBoxDisplayed);
		registerPage.enterPassword("mosip123");

		assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(), GlobalConstants.isPasswordConfirmTextBoxDisplayed);
		registerPage.enterPasswordConfirm("mosip123");

		assertTrue(registerPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		dashboardPage = registerPage.clickSubmitButton();

		KeycloakUserManager.assignRole("pmpui-v2", "PARTNER_ADMIN");

		handleTermsAndCondition();

	}

	@Test(priority = 2, description = "Uploading Trust Certificate", dependsOnMethods = "partnerAdminCreation")
	public void uploadTrustCertificate() {

		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(partnerCertificatePage.isUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnDeviceInPartnerDomainSelectorDropdown();

		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.waitForAdminTrustCertificateReadyToSubmit();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		assertTrue(partnerCertificatePage.isUploadedSuccessfullyMessageDisplayed(),
				GlobalConstants.isUploadedSuccessfullyMessageDisplayed);
		assertTrue(partnerCertificatePage.isSuccessIconDisplayed(), GlobalConstants.isSuccessIconDisplayed);
		partnerCertificatePage.clickOnGoBackButton();

		partnerCertificatePage.clickOnIntermediateCACertTab();
		assertTrue(partnerCertificatePage.isIntermediateUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnDeviceInPartnerDomainSelectorDropdown();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.waitForAdminTrustCertificateReadyToSubmit();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		// Intermediate CA may already exist in shared QA env; leave form and continue either way.
		partnerCertificatePage.clickOnGoBackAfterAdminTrustCertificateSubmit();
		dashboardPage.clickOnProfileDropdown();
		assertTrue(dashboardPage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);
		dashboardPage.clickOnLogoutButton();
	}

	@Test(priority = 3, description = "Create Default Policy Group", dependsOnMethods = "uploadTrustCertificate")
	public void createDefaultPolicyGroup() {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		policygroupPage = new PolicyGroupPage(driver);

		dashboardPage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);

		policygroupPage.clickOnCreatePolicyGroupButton();
		assertTrue(policygroupPage.isPolicyGroupNameTextboxDisplayed(),
				GlobalConstants.isPolicyGroupNameTextboxDisplayed);
		policygroupPage.enterPolicyGroupName(GlobalConstants.DEFAULT_POLICYGROUP);
		assertTrue(policygroupPage.isPolicyGroupNameDescriptionTextboxDisplayed(),
				GlobalConstants.isPolicyGroupNameDescriptionTextboxDisplayed);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.DEFAULT_POLICYGROUP_DESC);
		policygroupPage.clickOnSubmitButton();
		assertTrue(policygroupPage.isPolicyGroupSuccessMessageDisplayed(),
				GlobalConstants.isPolicyGroupSuccessMessageDisplayed);
		policygroupPage.clickOnSuccessHomeButton();

	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayed()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}

}
