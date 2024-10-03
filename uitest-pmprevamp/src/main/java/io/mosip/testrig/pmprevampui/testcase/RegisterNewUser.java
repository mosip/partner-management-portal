package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.PartnerCertificatePage;
import io.mosip.testrig.pmprevampui.pages.PoliciesPage;
import io.mosip.testrig.pmprevampui.pages.RegisterPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class RegisterNewUser extends BaseClass {

	@Test
	public void registerNewUser() {

		DashboardPage dashboardpage = new DashboardPage(driver);

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		assertTrue(loginpage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);

		RegisterPage registerPage = loginpage.clickRegisterButton();
		assertTrue(registerPage.isRegisterPageTitleDisplayed(), GlobalConstants.isRegisterPageTitleDisplayed);
		assertTrue(registerPage.isFirstNameTextBoxDisplayed(), GlobalConstants.isFirstNameTextBoxDisplayed);

		registerPage.enterFirstName("ad" + data);

		assertTrue(registerPage.isLastNameTextBoxDisplayed(), GlobalConstants.isLastNameTextBoxDisplayed);
		registerPage.enterLastName("ad" + data);

		assertTrue(registerPage.isOrganizationNameDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
		registerPage.enterOrganizationName("xyz");

		assertTrue(registerPage.isPartnerTypeDropdownDisplayed(), GlobalConstants.isPartnerTypeDropdownDisplayed);
		registerPage.selectPartnerTypeDropdown();

		assertTrue(registerPage.isAddressTextBoxDisplayed(), GlobalConstants.isAddressTextBoxDisplayed);
		registerPage.enterAddress("ad" + data);

		assertTrue(registerPage.isEmailTextBoxDisplayed(), GlobalConstants.isEmailTextBoxDisplayed);
		registerPage.enterEmail("ad" + data + "@gmail.com");

		assertTrue(registerPage.isPhoneNumberTextboxDisplayed(), GlobalConstants.isPhoneNumberTextboxDisplayed);
		registerPage.enterPhone("9876543210");

		assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(),
				GlobalConstants.isNotificationLanguageDropdownDisplayed);
		registerPage.selectNotificationLanguageDropdown();

		assertTrue(registerPage.isUsernameTextBoxDisplayed(), GlobalConstants.isUsernameTextBoxDisplayed);
		registerPage.enterUsername("ad" + data);

		assertTrue(registerPage.isPasswordTextBoxDisplayed(), GlobalConstants.isPasswordTextBoxDisplayed);
		registerPage.enterPassword("mosip123");

		assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(), GlobalConstants.isPasswordConfirmTextBoxDisplayed);
		registerPage.enterPasswordConfirm("mosip123");

		assertTrue(registerPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage = registerPage.clickSubmitButton();

		assertTrue(dashboardpage.isSelectPolicyGroupPopUpDisplayed(),
				GlobalConstants.isSelectPolicyGroupPopUpDisplayed);
		dashboardpage.selectSelectPolicyGroupDropdown();

		assertTrue(dashboardpage.isSubmitButtonSelectPolicyGroupPopUpDisplayed(),
				GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage.clickOnSubmitButton();

		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(),
				GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardpage.clickOnCheckbox();

		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
		dashboardpage.clickOnProceedButton();

		assertTrue(dashboardpage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		PartnerCertificatePage partnerCertificatePage = dashboardpage.clickOnPartnerCertificateTitle();

		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.clickOnUploadButton();

		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();

		assertTrue(partnerCertificatePage.isSucessMessageDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		dashboardpage = partnerCertificatePage.clickOnHomeButton();

		assertTrue(dashboardpage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		PoliciesPage policiesPage = dashboardpage.clickOnPoliciesTitle();

		assertTrue(policiesPage.isPoliciesPageDisplayed(), GlobalConstants.isPoliciesPageDisplayed);
		policiesPage.clickOnRequestPolicyButton();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.selectPartnerIdDropdown("ad" + data);

		assertTrue(policiesPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		policiesPage.selectPolicyNameDropdown("ad" + data);
		policiesPage.enterComments(data);

		assertTrue(policiesPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		policiesPage.clickSubmitButton();

		assertTrue(policiesPage.isPolicySubmittedSuccessfullyDisplayed(), GlobalConstants.isSubmitButtonDisplayed);

	}
}
