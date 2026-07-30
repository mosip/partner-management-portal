package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;

@Test(dependsOnGroups = { "CredentialPartnerCreation" }, groups = { "CredentialPartnerLoginTest" })
public class CredentialPartnerLoginTest extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;

	@Test(priority = 1, description = "Credential Partner login redirects to dashboard with welcome message and service cards")
	public void verifySuccessfulLoginRedirectsToDashboard() {

		dashboardPage = new DashboardPage(driver);

		LogUtil.step("Logout admin and login as Credential Partner with registered credentials");
		loginAsCredentialPartner();

		LogUtil.step("Verify dashboard displays welcome message, Partner Certificate card, and Policies card");
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
	}

	private void loginAsCredentialPartner() {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.CREDENTIAL_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();
	}

}
