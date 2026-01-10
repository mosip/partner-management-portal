package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeoutException;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;
import io.mosip.testrig.pmpuiv2.pages.ApiKeyPage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.OidcClientPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.pages.ProfilePage;
import io.mosip.testrig.pmpuiv2.pages.RegisterPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(groups = { "AuthPartnerWithoutCertificateTest" })
public class AuthPartnerWithoutCertificateTest extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private ApiKeyPage apiKeyPage;
	private PartnerCertificatePage partnerCertificatePage;
	private PoliciesPage policiesPage;
	private OidcClientPage oidcClientPage;
	private ProfilePage profilePage;

	@Test(priority = 1, description = "Create oidc client with out uploading certficates")
	public void createOidcClientWithoutUploadingCertificates() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		policiesPage = new PoliciesPage(driver);

		logoutFromPartner();

		loginPage.enterUserName("pmpui-nocert");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();
		
		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnRequestPolicyButton();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.clickOnPartnerIdDropdown();
		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataTextDisplaed);
		policiesPage.clickOnHomeButton();
		
		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();

		assertTrue(oidcClientPage.isCreateOidcClientDisplayed(), GlobalConstants.isCreateOIDCClientDisplayed);
		assertTrue(oidcClientPage.isPartnerIDHeaderTextDisplayed(), GlobalConstants.isPartnerIDHeaderTextDisplayed);
		assertTrue(oidcClientPage.isPolicyGroupHeaderTextDisplayed(), GlobalConstants.isPolicyGroupHeaderTextDisplayed);
		assertTrue(oidcClientPage.isPolicyNameHeaderTextDisplayed(), GlobalConstants.isPolicyNameHeaderTextDisplayed);
		assertTrue(oidcClientPage.isOIDCClientNameHeaderTextDisplayed(),
				GlobalConstants.isOIDCClientNameHeaderTextDisplayed);
		assertTrue(oidcClientPage.isCreatedDateHeaderTextDisplayed(), GlobalConstants.isCreatedDateHeaderTextDisplayed);
		assertTrue(oidcClientPage.isStatusHeaderTextDisplayed(), GlobalConstants.isStatusHeaderTextDisplayed);
		assertTrue(oidcClientPage.isOIDCClientIDHeaderTextDisplayed(),
				GlobalConstants.isOIDCClientIDHeaderTextDisplayed);
		assertTrue(oidcClientPage.isActionHeaderTextDisplayed(), GlobalConstants.isActionHeaderTextDisplayed);
		oidcClientPage.clickOnCreateOidcClientButton();

		assertTrue(oidcClientPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		oidcClientPage.clickOnPartnerIdDropdown();
		assertTrue(oidcClientPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);

		String publicKeytemp = PmpTestUtil.generateJWKPublicKey();
		oidcClientPage.enterPublicKeyTextBox(publicKeytemp);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnAddNewRedirectUrlButton();

		oidcClientPage.entercreateOidcRedirectUrl2(ConfigManager.getRedirectUri() + "a");
		oidcClientPage.clickOnAddNewRedirectUrlButton();
		oidcClientPage.entercreateOidcRedirectUrl3(ConfigManager.getRedirectUri() + "b");
		oidcClientPage.clickOnAddNewRedirectUrlButton();
		oidcClientPage.entercreateOidcRedirectUrl4(ConfigManager.getRedirectUri() + "c");
		oidcClientPage.clickOnAddNewRedirectUrlButton();
		oidcClientPage.entercreateOidcRedirectUrl5(ConfigManager.getRedirectUri() + "d");
		oidcClientPage.clickOnCreateOidcClearForm();
		assertFalse(oidcClientPage.isCreateOidcRedirectUrl5Displayed(), GlobalConstants.isNoDataAvailableTextDisplayed);

	}

	@Test(priority = 2, description = " Create apikey without uploading certificates", dependsOnMethods = "createOidcClientWithoutUploadingCertificates")
	public void createApiKeyWithoutUploadingCertificates() {
		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);
		loginPage = new LoginPage(driver);

		logoutFromPartner();

		loginPage.enterUserName("pmpui-nocert");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();

		oidcClientPage.clickOnApiKeyTab();

		assertTrue(apiKeyPage.isGenerateApiKeyDisplayed(), GlobalConstants.isGenerateAPIKeyDisplayed);
		apiKeyPage.clickOnCreateApiKey();
		assertTrue(apiKeyPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		apiKeyPage.ClickOnPartnerIdDropdown();
		assertTrue(apiKeyPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataTextDisplaed);

	}

	@Test(priority = 3, description = "User Profile")
	public void userProfile() {
		dashboardPage = new DashboardPage(driver);
		profilePage = new ProfilePage(driver);
		loginPage = new LoginPage(driver);

		logoutFromPartner();

		loginPage.enterUserName("pmpui-nocert");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		dashboardPage.clickOnProfileDropdown();
		profilePage.clickOnUserProfileButton();
		assertTrue(profilePage.isFirstNameLabelDisplayed(), GlobalConstants.isFirstNameLabelDisplayed);
		assertTrue(profilePage.isFirstNameContextDisplayed(), GlobalConstants.isFirstNameContextDisplayed);
		assertTrue(profilePage.isLastNameLabelDisplayed(), GlobalConstants.isLastNameLabelDisplayed);
		assertTrue(profilePage.isLastNameContextDisplayed(), GlobalConstants.isLastNameContextDisplayed);
		assertTrue(profilePage.isOrganisationNameLabelDisplayed(), GlobalConstants.isOrganisationNameLabelDisplayed);
		assertTrue(profilePage.isOrganisationNameContextDisplayed(),
				GlobalConstants.isOrganisationNameContextDisplayed);
		assertTrue(profilePage.isAddressLabelDisplayed(), GlobalConstants.isAddressLabelDisplayed); //
//		assertTrue(profilePage.isAddressContextDisplayed(), GlobalConstants.isAddressContextDisplayed);
		assertTrue(profilePage.isPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLabelDisplayed);
		assertTrue(profilePage.isPartnerTypeContextDisplayed(), GlobalConstants.isPartnerTypeContextDisplayed);
		assertTrue(profilePage.isPhoneNumberLabelDisplayed(), GlobalConstants.isPhoneNumberLabelDisplayed);
		assertTrue(profilePage.isPhoneNumberContextDisplayed(), GlobalConstants.isPhoneNumberContextDisplayed);
		assertTrue(profilePage.isEmailAddressLabelDisplayed(), GlobalConstants.isEmailAddressLabelDisplayed); //
		assertTrue(profilePage.isEmailContextDisplayed(), GlobalConstants.isEmailContextDisplayed);
		assertTrue(profilePage.isUserNameLabelDisplayed(), GlobalConstants.isUserNameLabelDisplayed);
		assertTrue(profilePage.isUserNameContextDisplayed(), GlobalConstants.isUserNameContextDisplayed);
		profilePage.clickOnPhoneNumber();
		assertTrue(profilePage.isPhoneNumberClickable(), GlobalConstants.isPhoneNumberClickable);
		profilePage.clickOnTitleBackIcon();

	}

	@Test(priority = 4, description = "User dashboard of authentication partner")
	public void userDashboardOfAuthenticationPartner() throws TimeoutException {

		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		policiesPage = new PoliciesPage(driver);
		oidcClientPage = new OidcClientPage(driver);

		loginAsAuthPartner();

		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesTitleDisplayed);
		assertTrue(dashboardPage.isAuthenticationServiceInfoTextDisplayed(),
				GlobalConstants.isAuthenticationServiceInfoTextDisplayed);
		assertTrue(dashboardPage.isAuthenticationServiceIconDisplayed(),
				GlobalConstants.isAuthenticationServiceIconDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnTitleBackButton();
		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnTitleBackIcon();
		dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnTitleBackButton();
		dashboardPage.clickOnHamburgerOpen();
		assertTrue(dashboardPage.isHumburgerOptionsExpandable(), GlobalConstants.isHumburgerOptionsExpandable);
		dashboardPage.clickOnHomeOptionOfHamburger();
		dashboardPage.clickOnPartnerCertificateOfHamburger();
		dashboardPage.clickOnPoliciesOfHamburger();
		dashboardPage.clickOnAuthenticationServiceOfHamburger();
		dashboardPage.clickOnHamburgerClose();
		oidcClientPage.clickOnTitleBackButton();
		assertTrue(dashboardPage.isOrganizationIconWithNameDisplayed(),
				GlobalConstants.isOrganizationIconWithNameDisplayed);
		assertTrue(dashboardPage.isContactusLinkDisplayed(), GlobalConstants.isContactusLinkDisplayed);
		assertTrue(dashboardPage.isMosipRightsTextDisplayed(), GlobalConstants.isMosipRightsTextDisplayed);
		assertTrue(dashboardPage.isFooterDocumentationLinkDisplayed(),
				GlobalConstants.isFooterDocumentationLinkDisplayed);
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
	}

	private void logoutFromPartner() {
		dashboardPage.clickOnProfileDropdown();
		dashboardPage.clickOnLogoutButton();
	}

	private void loginAsAuthPartner() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.AUTH_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();
	}

}
