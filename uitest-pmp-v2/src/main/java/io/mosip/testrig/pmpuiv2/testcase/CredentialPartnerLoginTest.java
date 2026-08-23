package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.ProfilePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;

/**
 * MOSIP-44515 Credential Partner – Login &amp; Dashboard Display with Service Cards.
 * TC_44515_01: Verify successful login redirects to Dashboard.
 * TC_44515_02: Verify Welcome message is displayed on Dashboard.
 * TC_44515_04: Verify Welcome message displays correct user name.
 * TC_44515_05: Verify the service cards are displayed on Dashboard.
 * TC_44515_06: Verify service cards are visible only for Credential Partner role.
 * TC_44515_07: Verify Partner Certificate card title.
 * Registration steps (1–4) are covered in {@link CredentialPartnerCreation#registerCredentialPartnerUser}.
 */
@Test(dependsOnGroups = { "CredentialPartnerCreation" }, groups = { "CredentialPartnerLoginTest" })
public class CredentialPartnerLoginTest extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private ProfilePage profilePage;

	@Test(priority = 1, description = "MOSIP-44515 TC_44515_01 - Verify successful login redirects to Dashboard")
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

	@Test(priority = 2, description = "MOSIP-44515 TC_44515_02 - Verify Welcome message is displayed on Dashboard")
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

	@Test(priority = 3, description = "MOSIP-44515 TC_44515_04 - Verify Welcome message displays correct user name")
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

	@Test(priority = 4, description = "MOSIP-44515 TC_44515_05 - Verify the service cards are displayed on Dashboard")
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

	@Test(priority = 5, description = "MOSIP-44515 TC_44515_06 - Verify service cards are visible only for Credential Partner role")
	public void verifyServiceCardsVisibleOnlyForCredentialPartnerRole() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		profilePage = new ProfilePage(driver);

		LogUtil.step("Step 1: Login with Credential Partner role");
		loginAsCredentialPartner();

		LogUtil.step("Step 2: Navigate to Dashboard");
		dashboardPage.clickOnProfileDropdown();
		profilePage.clickOnUserProfileButton();
		assertEquals(profilePage.getPartnerTypeValue(), GlobalConstants.CREDENTIAL_PARTNER_TYPE,
				GlobalConstants.isCredentialPartnerRoleMapped);
		profilePage.clickOnTitleBackIcon();

		LogUtil.step("Step 3: Observe available service cards");
		LogUtil.step("Step 4: Validate role-based visibility");
		validateCredentialPartnerDashboardServiceCards();
	}

	@Test(priority = 6, description = "MOSIP-44515 TC_44515_07 - Verify Partner Certificate card title")
	public void verifyPartnerCertificateCardTitle() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);

		LogUtil.step("Step 1: Login with valid Credential Partner credentials");
		loginAsCredentialPartner();

		LogUtil.step("Step 2: Navigate to Dashboard");
		LogUtil.step("Step 3: Locate Partner Certificate card");
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);

		LogUtil.step("Step 4: Observe the card title");
		assertEquals(dashboardPage.getPartnerCertificateCardTitleText(),
				GlobalConstants.PARTNER_CERTIFICATE_CARD_TITLE,
				GlobalConstants.isPartnerCertificateCardTitleCorrect);
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
