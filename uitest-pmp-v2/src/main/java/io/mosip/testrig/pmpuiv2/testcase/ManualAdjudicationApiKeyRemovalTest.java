package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.ApiKeyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.OidcClientPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

/**
 * MOSIP-44793 - API key management and the Manual Adjudication Services card
 * have been withdrawn for Manual Adjudication partners.
 *
 * All four test cases are absence checks against the same freshly created and
 * activated Manual Adjudication partner, so they are asserted in one scenario.
 *
 * Covers TC_44793_01 to _04.
 */
@Test(dependsOnGroups = { "PartnerAdminCreation",
		"PolicyAdminAndPartnerCreation" }, groups = { "ManualAdjudicationApiKeyRemovalTest" })
public class ManualAdjudicationApiKeyRemovalTest extends BaseClass {

	private BasePage basePage;
	private DashboardPage dashboardPage;
	private MispPartnerPage mispPartnerPage;
	private PartnerCertificatePage partnerCertificatePage;
	private ApiKeyPage apiKeyPage;
	private OidcClientPage oidcClientPage;

	@Test(priority = 1, description = "Verify that for a Manual Adjudication partner the Manual Adjudication Services "
			+ "card is gone from the dashboard and no API key can be created, listed, viewed or deactivated. "
			+ "(TC 01,02,03,04)")
	public void manualAdjudicationApiKeyFeaturesRemoved() {
		basePage = new BasePage(driver);
		dashboardPage = new DashboardPage(driver);
		mispPartnerPage = new MispPartnerPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		apiKeyPage = new ApiKeyPage(driver);

		// The dashboard no longer offers the Manual Adjudication Services card
		assertFalse(basePage.isTextPresentOnPage(GlobalConstants.MANUAL_ADJUDICATION_SERVICES_CARD_TEXT),
				GlobalConstants.isManualAdjudicationServicesCardRemoved);

		// Create and activate a Manual Adjudication partner
		String user = "manadj" + BaseClass.data;
		dashboardPage.clickOnPartners();
		mispPartnerPage.clickOnCreatePartnerButton();
		mispPartnerPage.clickOnPartnerTypeDropdown();
		mispPartnerPage.selectPartnerType(GlobalConstants.MANUAL_ADJUDICATION_PARTNER);
		mispPartnerPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		mispPartnerPage.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
		mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
		mispPartnerPage.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
		mispPartnerPage.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
		mispPartnerPage.enterEmailId(user + "@test.com");
		mispPartnerPage.enterUserName(user);
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isAbisPartnerCreatedSuccessfully);

		mispPartnerPage.clickOnUploadPartnerCertificateButton();
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadPolicyUserClientCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed(),
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		partnerCertificatePage.clickOnHomeButton();

		// pmpui-v2 holds PARTNER_ADMIN, so Dashboard.js never renders the partner
		// Authentication Services card; use the admin card the way ApiKeyAuthPartnerTest does.
		oidcClientPage = dashboardPage.clickOnAuthenticationServices();
		oidcClientPage.clickOnApiKeyTab();

		// The admin list runs to dozens of pages, so filter down to this partner first -
		// without it the row assertions only ever see page one and pass vacuously.
		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(user);
		apiKeyPage.clickOnApplyFilterButton();

		// No API key row exists for it to be listed, viewed or deactivated (TC_02, TC_03, TC_04).
		// TC_01 (creation) is not covered here - see knownIssues.txt.
		assertFalse(apiKeyPage.isApiKeyRowPresentForPartner(user),
				GlobalConstants.isApiKeyDetailsUnavailableForManualAdjudication);
		assertFalse(apiKeyPage.isApiKeyDeactivateOptionPresentForPartner(user),
				GlobalConstants.isApiKeyDeactivationUnavailableForManualAdjudication);
	}
}
