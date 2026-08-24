package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.pages.ProfilePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;

@Test(dependsOnGroups = { "CredentialPartnerCreation" }, groups = { "CredentialPartnerLoginTest" })
public class CredentialPartnerLoginTest extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private ProfilePage profilePage;
	private PartnerCertificatePage partnerCertificatePage;
	private PoliciesPage policiesPage;

	@Test(priority = 1, description = "Verify successful login redirects to Dashboard")
	public void verifySuccessfulLoginRedirectsToDashboard() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);

		LogUtil.step("Step 5: Navigate to Login page");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);

		LogUtil.step("Step 6: Enter registered valid username and password");
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);

		LogUtil.step("Step 7: Click on Login");
		loginPage.clickOnLoginButton();

		LogUtil.step("Verify user is authenticated and redirected to Dashboard with service cards");
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
	}

	@Test(priority = 2, description = "Verify Welcome message is displayed on Dashboard")
	public void verifyWelcomeMessageDisplayedOnDashboard() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);

		LogUtil.step("Step 1: Login to the PMS as a valid Credential Partner");
		loginAsCredentialPartner();

		LogUtil.step("Step 2: Navigate to Dashboard");
		LogUtil.step("Step 3: Observe the top section of the page");
		LogUtil.step("Step 4: Identify the Welcome message");

		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
	}

	@Test(priority = 3, description = "Verify Welcome message displays correct user name")
	public void verifyWelcomeMessageDisplaysCorrectUserName() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		profilePage = new ProfilePage(driver);

		LogUtil.step("Step 1: Login to the PMS as a valid Credential Partner");
		loginAsCredentialPartner();

		LogUtil.step("Step 2: Navigate to Dashboard");
		LogUtil.step("Step 3: Identify the Welcome message");
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
		String welcomeMessageText = dashboardPage.getWelcomeMessageText();

		LogUtil.step("Step 4: Compare displayed name with user profile details");
		dashboardPage.clickOnProfileDropdown();
		profilePage.clickOnUserProfileButton();
		String firstName = profilePage.getFirstNameValue();
		String lastName = profilePage.getLastNameValue();
		String expectedWelcomeMessage = "Welcome " + firstName + " " + lastName + "!";

		assertEquals(welcomeMessageText, expectedWelcomeMessage,
				GlobalConstants.isWelcomeMessageDisplaysCorrectUserName);
	}

	@Test(priority = 4, description = "Verify the service cards are displayed on Dashboard")
	public void verifyServiceCardsDisplayedOnDashboard() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);

		LogUtil.step("Step 1: Login to the PMS Portal as a valid Credential Partner");
		loginAsCredentialPartner();

		LogUtil.step("Step 2: Navigate to Dashboard");
		LogUtil.step("Step 3: Identify all visible service cards");
		validateCredentialPartnerDashboardServiceCards();

		LogUtil.step("Step 4: Count the total number of cards");
	}

	@Test(priority = 5, description = "Verify service cards are visible only for Credential Partner role")
	public void verifyServiceCardsVisibleOnlyForCredentialPartnerRole() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		profilePage = new ProfilePage(driver);

		LogUtil.step("Step 1: Login with Credential Partner role");
		loginAsCredentialPartner();

		LogUtil.step("Step 2: Navigate to Dashboard");
		LogUtil.step("Step 3: Observe available service cards");
		LogUtil.step("Step 4: Validate role-based visibility");
		validateCredentialPartnerDashboardServiceCards();

		dashboardPage.clickOnProfileDropdown();
		profilePage.clickOnUserProfileButton();
		assertEquals(profilePage.getPartnerTypeValue(), GlobalConstants.CREDENTIAL_PARTNER_TYPE,
				GlobalConstants.isCredentialPartnerRoleMapped);
	}

	@Test(priority = 6, description = "Verify side panel navigation for dashboard service cards")
	public void verifySidePanelNavigationForDashboardServiceCards() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		policiesPage = new PoliciesPage(driver);

		LogUtil.step("Step 1: Login as Credential Partner");
		loginAsCredentialPartner();

		LogUtil.step("Step 2: Verify side panel items corresponding to dashboard cards");
		assertTrue(dashboardPage.isSideNavigationHomeIconDisplayed(),
				GlobalConstants.isSideNavigationHomeIconDisplayed);
		assertTrue(dashboardPage.isSideNavPartnerCertificateDisplayed(),
				GlobalConstants.isSideNavPartnerCertificateDisplayed);
		assertTrue(dashboardPage.isSideNavPoliciesDisplayed(), GlobalConstants.isSideNavPoliciesDisplayed);
		assertFalse(dashboardPage.isSideNavAuthenticationServiceVisible(),
				"Verify Authentication Services side panel item is not displayed for Credential Partner");
		assertFalse(dashboardPage.isSideNavDeviceProviderVisible(),
				"Verify Device Provider side panel item is not displayed for Credential Partner");
		assertFalse(dashboardPage.isSideNavFtmChipProviderVisible(),
				"Verify FTM Chip Provider side panel item is not displayed for Credential Partner");

		LogUtil.step("Step 3: Click Partner Certificate on side panel and verify navigation");
		dashboardPage.clickOnPartnerCertificateOfHamburger();
		assertTrue(partnerCertificatePage.isPartnerCertificatesListPageDisplayed(),
				GlobalConstants.isSidePanelPartnerCertificateNavigationSuccessful);
		assertTrue(driver.getCurrentUrl().contains("partner-certificate"),
				GlobalConstants.isSidePanelPartnerCertificateNavigationSuccessful);

		LogUtil.step("Step 4: Click Policies on side panel and verify navigation");
		dashboardPage.clickOnPoliciesOfHamburger();
		assertTrue(policiesPage.isPoliciesListPageDisplayed(),
				GlobalConstants.isSidePanelPoliciesNavigationSuccessful);
		assertTrue(driver.getCurrentUrl().contains("policies"),
				GlobalConstants.isSidePanelPoliciesNavigationSuccessful);

		LogUtil.step("Step 5: Click Home on side panel and verify return to Dashboard");
		dashboardPage.clickOnHomeOptionOfHamburger();
		assertTrue(driver.getCurrentUrl().contains("dashboard")
				|| dashboardPage.isWelcomeMessageDisplayed(),
				GlobalConstants.isSidePanelHomeNavigationSuccessful);
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
	}

	@Test(priority = 7, description = "Verify hamburger menu expands and collapses for Credential Partner")
	public void verifyHamburgerMenuExpandsAndCollapses() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);

		LogUtil.step("Step 1: Login as Credential Partner");
		loginAsCredentialPartner();

		LogUtil.step("Step 2: Click hamburger icon and verify side menu expands");
		if (dashboardPage.isHamburgerMenuExpanded()) {
			dashboardPage.clickOnHamburgerClose();
		}
		assertTrue(dashboardPage.isHamburgerMenuCollapsed(), GlobalConstants.isHamburgerMenuCollapsed);
		dashboardPage.clickOnHamburgerOpen();
		assertTrue(dashboardPage.isHamburgerMenuExpanded(), GlobalConstants.isHamburgerMenuExpanded);
		assertTrue(dashboardPage.isHumburgerOptionsExpandable(), GlobalConstants.isHumburgerOptionsExpandable);
		assertTrue(dashboardPage.isSideNavPartnerCertificateDisplayed(),
				GlobalConstants.isSideNavPartnerCertificateDisplayed);
		assertTrue(dashboardPage.isSideNavPoliciesDisplayed(), GlobalConstants.isSideNavPoliciesDisplayed);

		LogUtil.step("Step 3: Click hamburger icon again and verify side menu collapses");
		dashboardPage.clickOnHamburgerClose();
		assertTrue(dashboardPage.isHamburgerMenuCollapsed(), GlobalConstants.isHamburgerMenuCollapsed);
		assertTrue(dashboardPage.isHumburgerOptionsCollapse(), GlobalConstants.isHumburgerOptionsCollapse);
	}

	@Test(priority = 8, description = "Verify organisation name and user name persist across Credential Partner screens")
	public void verifyOrganisationAndUserNamePersistAcrossScreens() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		policiesPage = new PoliciesPage(driver);
		profilePage = new ProfilePage(driver);

		LogUtil.step("Step 1: Login as Credential Partner");
		loginAsCredentialPartner();

		LogUtil.step("Step 2: Capture organisation name and user name from Dashboard header");
		assertOrganisationAndUserNameOnHeader("Dashboard");

		LogUtil.step("Step 3: Navigate to Partner Certificate and verify header values persist");
		dashboardPage.clickOnPartnerCertificateOfHamburger();
		assertTrue(partnerCertificatePage.isPartnerCertificatesListPageDisplayed(),
				GlobalConstants.isSidePanelPartnerCertificateNavigationSuccessful);
		assertOrganisationAndUserNameOnHeader("Partner Certificate");

		LogUtil.step("Step 4: Navigate to Policies and verify header values persist");
		dashboardPage.clickOnPoliciesOfHamburger();
		assertTrue(policiesPage.isPoliciesListPageDisplayed(),
				GlobalConstants.isSidePanelPoliciesNavigationSuccessful);
		assertOrganisationAndUserNameOnHeader("Policies");

		LogUtil.step("Step 5: Navigate to My Profile and verify organisation name and user name");
		dashboardPage.clickOnProfileDropdown();
		profilePage.clickOnUserProfileButton();
		assertEquals(profilePage.getOrganisationNameValue(), GlobalConstants.ORGANISATION_NAME,
				GlobalConstants.isOrganisationNamePersistedAcrossScreens);
		assertEquals(profilePage.getUserNameValue(), GlobalConstants.CREDENTIAL_PARTNER_ID,
				GlobalConstants.isUserNamePersistedAcrossScreens);
		assertOrganisationAndUserNameOnHeader("My Profile");

		LogUtil.step("Step 6: Return to Dashboard and verify header values still persist");
		dashboardPage.clickOnHomeOptionOfHamburger();
		assertTrue(driver.getCurrentUrl().contains("dashboard") || dashboardPage.isWelcomeMessageDisplayed(),
				GlobalConstants.isSidePanelHomeNavigationSuccessful);
		assertOrganisationAndUserNameOnHeader("Dashboard");
	}

	@Test(priority = 9, description = "Verify Credential Partner user profile details match registered data")
	public void verifyUserProfileDetailsMatchRegisteredData() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		profilePage = new ProfilePage(driver);

		LogUtil.step("Step 1: Login as Credential Partner and open User Profile from dashboard");
		loginAsCredentialPartner();
		dashboardPage.clickOnProfileDropdown();
		profilePage.clickOnUserProfileButton();

		LogUtil.step("Step 2: Verify My Profile page is displayed");
		assertTrue(profilePage.isTitleOfCardViewDisplayed(), GlobalConstants.isUserProfilePageDisplayed);
		assertTrue(driver.getCurrentUrl().contains("user-profile"), GlobalConstants.isUserProfilePageDisplayed);

		LogUtil.step("Step 3: Verify profile details match registered Credential Partner data");
		assertEquals(profilePage.getFirstNameValue(), GlobalConstants.CREDENTIAL_PARTNER_ID,
				GlobalConstants.isUserProfileDetailsMatchingRegisteredData);
		assertEquals(profilePage.getLastNameValue(), GlobalConstants.CREDENTIAL_PARTNER_ID,
				GlobalConstants.isUserProfileDetailsMatchingRegisteredData);
		assertEquals(profilePage.getOrganisationNameValue(), GlobalConstants.ORGANISATION_NAME,
				GlobalConstants.isUserProfileDetailsMatchingRegisteredData);
		assertEquals(profilePage.getAddressValue(), CredentialPartnerCreation.registeredAddress,
				GlobalConstants.isUserProfileDetailsMatchingRegisteredData);
		assertEquals(profilePage.getPartnerTypeValue(), GlobalConstants.CREDENTIAL_PARTNER_TYPE,
				GlobalConstants.isUserProfileDetailsMatchingRegisteredData);
		assertEquals(profilePage.getPhoneNumberValue(), GlobalConstants.CREDENTIAL_PARTNER_PHONE,
				GlobalConstants.isUserProfileDetailsMatchingRegisteredData);
		assertEquals(profilePage.getEmailAddressValue(), CredentialPartnerCreation.registeredEmail,
				GlobalConstants.isUserProfileDetailsMatchingRegisteredData);
		assertEquals(profilePage.getUserNameValue(), GlobalConstants.CREDENTIAL_PARTNER_ID,
				GlobalConstants.isUserProfileDetailsMatchingRegisteredData);
	}

	@Test(priority = 10, description = "Verify Credential Partner logout from User Profile is successful")
	public void verifyLogoutFromUserProfileSucceeds() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		profilePage = new ProfilePage(driver);

		LogUtil.step("Step 1: Login as Credential Partner and open User Profile from dashboard");
		loginAsCredentialPartner();
		dashboardPage.clickOnProfileDropdown();
		profilePage.clickOnUserProfileButton();
		assertTrue(profilePage.isTitleOfCardViewDisplayed(), GlobalConstants.isUserProfilePageDisplayed);

		LogUtil.step("Step 2: Click logout from profile menu");
		dashboardPage.clickOnProfileDropdown();
		profilePage.clickOnLogoutButton();

		LogUtil.step("Step 3: Verify user is logged out successfully");
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isCredentialPartnerLoggedOutSuccessfully);
	}

	@Test(priority = 11, description = "Verify Credential Partner French login and French pages, headers and details")
	public void verifyLoginWithFrenchLanguage() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		policiesPage = new PoliciesPage(driver);
		profilePage = new ProfilePage(driver);

		LogUtil.step("Step 1: Navigate to sign-in page and select French language");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		assertTrue(loginPage.isLanguageDropdownDisplayed(), GlobalConstants.isFrenchLanguageSelectedOnSignIn);
		loginPage.selectFrenchLanguage();
		assertTrue(loginPage.isFrenchLanguageSelected(), GlobalConstants.isFrenchLanguageSelectedOnSignIn);

		LogUtil.step("Step 2: Login as Credential Partner");
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Step 3: Verify dashboard pages, headers and details in French");
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(),
				GlobalConstants.isCredentialPartnerFrenchLoginSuccessful);
		verifyLocalizedCredentialPartnerScreens(
				"Bienvenue",
				GlobalConstants.FRENCH_PARTNER_CERTIFICATE_TITLE,
				GlobalConstants.FRENCH_PARTNER_CERTIFICATE_DESC,
				GlobalConstants.FRENCH_POLICIES_TITLE,
				GlobalConstants.FRENCH_POLICIES_DESC,
				GlobalConstants.FRENCH_MY_PROFILE_TITLE,
				GlobalConstants.FRENCH_CREDENTIAL_PARTNER_TYPE,
				GlobalConstants.FRENCH_FIRST_NAME_LABEL,
				GlobalConstants.FRENCH_LAST_NAME_LABEL,
				GlobalConstants.FRENCH_ORG_NAME_LABEL,
				GlobalConstants.FRENCH_PARTNER_TYPE_LABEL,
				GlobalConstants.FRENCH_USER_NAME_LABEL,
				GlobalConstants.isFrenchWelcomeMessageDisplayed,
				GlobalConstants.isFrenchDashboardCardsDisplayed,
				GlobalConstants.isFrenchPagesAndHeadersDisplayed,
				GlobalConstants.isFrenchProfileDetailsDisplayed);
	}

	@Test(priority = 12, description = "Verify Credential Partner Arabic login and Arabic pages, headers and details")
	public void verifyLoginWithArabicLanguage() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		policiesPage = new PoliciesPage(driver);
		profilePage = new ProfilePage(driver);

		LogUtil.step("Step 1: Navigate to sign-in page and select Arabic language");
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		assertTrue(loginPage.isLanguageDropdownDisplayed(), GlobalConstants.isArabicLanguageSelectedOnSignIn);
		loginPage.selectArabicLanguage();
		assertTrue(loginPage.isArabicLanguageSelected(), GlobalConstants.isArabicLanguageSelectedOnSignIn);

		LogUtil.step("Step 2: Login as Credential Partner");
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

		LogUtil.step("Step 3: Verify dashboard pages, headers and details in Arabic");
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(),
				GlobalConstants.isCredentialPartnerArabicLoginSuccessful);
		verifyLocalizedCredentialPartnerScreens(
				GlobalConstants.ARABIC_WELCOME_PREFIX,
				GlobalConstants.ARABIC_PARTNER_CERTIFICATE_TITLE,
				GlobalConstants.ARABIC_PARTNER_CERTIFICATE_DESC,
				GlobalConstants.ARABIC_POLICIES_TITLE,
				GlobalConstants.ARABIC_POLICIES_DESC,
				GlobalConstants.ARABIC_MY_PROFILE_TITLE,
				GlobalConstants.ARABIC_CREDENTIAL_PARTNER_TYPE,
				GlobalConstants.ARABIC_FIRST_NAME_LABEL,
				GlobalConstants.ARABIC_LAST_NAME_LABEL,
				GlobalConstants.ARABIC_ORG_NAME_LABEL,
				GlobalConstants.ARABIC_PARTNER_TYPE_LABEL,
				GlobalConstants.ARABIC_USER_NAME_LABEL,
				GlobalConstants.isArabicWelcomeMessageDisplayed,
				GlobalConstants.isArabicDashboardCardsDisplayed,
				GlobalConstants.isArabicPagesAndHeadersDisplayed,
				GlobalConstants.isArabicProfileDetailsDisplayed);
	}

	private void verifyLocalizedCredentialPartnerScreens(String welcomePrefix, String partnerCertTitle,
			String partnerCertDesc, String policiesTitle, String policiesDesc, String myProfileTitle,
			String partnerTypeLocalized, String firstNameLabel, String lastNameLabel, String orgNameLabel,
			String partnerTypeLabel, String userNameLabel, String welcomeAssertMsg, String dashboardAssertMsg,
			String pagesAssertMsg, String profileAssertMsg) {

		String welcomeMessageText = dashboardPage.getWelcomeMessageText();
		assertTrue(welcomeMessageText.contains(welcomePrefix),
				welcomeAssertMsg + " Actual: " + welcomeMessageText);
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		assertEquals(dashboardPage.getPartnerCertificateHeaderText(), partnerCertTitle, dashboardAssertMsg);
		assertEquals(dashboardPage.getPoliciesHeaderText(), policiesTitle, dashboardAssertMsg);
		assertEquals(dashboardPage.getPartnerCertificateDescriptionText(), partnerCertDesc, dashboardAssertMsg);
		assertEquals(dashboardPage.getPoliciesDescriptionText(), policiesDesc, dashboardAssertMsg);

		LogUtil.step("Navigate to Partner Certificate and verify localized page header");
		dashboardPage.clickOnPartnerCertificateOfHamburger();
		assertTrue(partnerCertificatePage.isPartnerCertificatesListPageDisplayed(),
				GlobalConstants.isSidePanelPartnerCertificateNavigationSuccessful);
		assertEquals(dashboardPage.getPageTitleText(), partnerCertTitle, pagesAssertMsg);

		LogUtil.step("Navigate to Policies and verify localized page header");
		dashboardPage.clickOnPoliciesOfHamburger();
		assertTrue(policiesPage.isPoliciesListPageDisplayed(),
				GlobalConstants.isSidePanelPoliciesNavigationSuccessful);
		assertEquals(dashboardPage.getPageTitleText(), policiesTitle, pagesAssertMsg);

		LogUtil.step("Navigate to My Profile and verify localized labels and details");
		dashboardPage.clickOnProfileDropdown();
		profilePage.clickOnUserProfileButton();
		assertTrue(profilePage.isTitleOfCardViewDisplayed(), GlobalConstants.isUserProfilePageDisplayed);
		assertEquals(profilePage.getPageTitleText(), myProfileTitle, pagesAssertMsg);
		assertTrue(profilePage.isProfileLabelDisplayed(firstNameLabel), profileAssertMsg);
		assertTrue(profilePage.isProfileLabelDisplayed(lastNameLabel), profileAssertMsg);
		assertTrue(profilePage.isProfileLabelDisplayed(orgNameLabel), profileAssertMsg);
		assertTrue(profilePage.isProfileLabelDisplayed(partnerTypeLabel), profileAssertMsg);
		assertTrue(profilePage.isProfileLabelDisplayed(userNameLabel), profileAssertMsg);
		assertEquals(profilePage.getFirstNameValue(), GlobalConstants.CREDENTIAL_PARTNER_ID, profileAssertMsg);
		assertEquals(profilePage.getLastNameValue(), GlobalConstants.CREDENTIAL_PARTNER_ID, profileAssertMsg);
		assertEquals(profilePage.getOrganisationNameValue(), GlobalConstants.ORGANISATION_NAME, profileAssertMsg);
		assertEquals(profilePage.getPartnerTypeValue(), partnerTypeLocalized, profileAssertMsg);
		assertEquals(profilePage.getUserNameValue(), GlobalConstants.CREDENTIAL_PARTNER_ID, profileAssertMsg);
		assertEquals(profilePage.getPhoneNumberValue(), GlobalConstants.CREDENTIAL_PARTNER_PHONE, profileAssertMsg);
		assertEquals(profilePage.getAddressValue(), CredentialPartnerCreation.registeredAddress, profileAssertMsg);
		assertEquals(profilePage.getEmailAddressValue(), CredentialPartnerCreation.registeredEmail, profileAssertMsg);
	}

	private void assertOrganisationAndUserNameOnHeader(String screenName) {
		assertEquals(dashboardPage.getHeaderOrganisationName(), GlobalConstants.ORGANISATION_NAME,
				GlobalConstants.isOrganisationNamePersistedAcrossScreens + " on " + screenName);
		assertEquals(dashboardPage.getHeaderUserName(), GlobalConstants.CREDENTIAL_PARTNER_ID,
				GlobalConstants.isUserNamePersistedAcrossScreens + " on " + screenName);
	}

	private void validateCredentialPartnerDashboardServiceCards() {
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		assertFalse(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				"Verify Authentication Services card is not displayed for Credential Partner");
		assertFalse(dashboardPage.isDeviceProviderServicesTitleDisplayed(),
				"Verify Device Provider Services card is not displayed for Credential Partner");
		assertFalse(dashboardPage.isFtmChipProviderCardDisplayed(),
				"Verify FTM Chip Provider Services card is not displayed for Credential Partner");
		assertEquals(dashboardPage.getVisiblePartnerServiceCardCount(),
				GlobalConstants.CREDENTIAL_PARTNER_SERVICE_CARD_COUNT,
				GlobalConstants.isCredentialPartnerRoleBasedServiceCardsVisible);
	}

	private void loginAsCredentialPartner() {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();
	}

}
