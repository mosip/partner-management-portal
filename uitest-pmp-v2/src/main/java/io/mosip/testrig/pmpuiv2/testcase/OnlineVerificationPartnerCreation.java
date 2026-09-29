package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.MapCredentialTypePage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

/**
 * Creates the Online Verification Partner used by the OVP Map Credential Type
 * and View Policy Details suites, and uploads its partner certificate so the
 * partner is Active and can be picked on the admin Request Policy form.
 *
 * OVP partners are not offered on the self registration page - the Partner
 * Admin creates them from Partners > Create Partner, the same way as the
 * Manual Adjudication partner, and the client certificate comes from the AUTH
 * trust chain uploaded by {@code PolicyAdminAndPartnerCreation}.
 */
@Test(dependsOnGroups = { "PolicyAdminAndPartnerCreation" }, groups = { "OnlineVerificationPartnerCreation" })
public class OnlineVerificationPartnerCreation extends BaseClass {

	@Test(priority = 1, description = "Create an Online Verification Partner as Partner Admin and upload its partner certificate")
	public void createOnlineVerificationPartner() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		MispPartnerPage createPartnerPage = new MispPartnerPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);

		dashboardPage.clickOnPartners();
		createPartnerPage.clickOnCreatePartnerButton();
		// selectPartnerType() opens the dropdown itself - clicking it first would toggle it shut
		createPartnerPage.selectPartnerType(MapCredentialTypePage.ONLINE_VERIFICATION_PARTNER_TYPE);
		createPartnerPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		createPartnerPage.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
		createPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
		createPartnerPage.enterPartnerAddress(GlobalConstants.OVP_PARTNER_ADDRESS);
		createPartnerPage.enterPartnerContactNumber(GlobalConstants.OVP_PARTNER_CONTACT_NUMBER);
		createPartnerPage.enterEmailId("ovp" + data + "@test.com");
		createPartnerPage.enterUserName(GlobalConstants.OVP_PARTNER_USER);
		createPartnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(createPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isOvpPartnerCreatedSuccessfully);

		createPartnerPage.clickOnUploadPartnerCertificateButton();
		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadPolicyUserClientCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed(),
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		partnerCertificatePage.clickOnHomeButton();
	}
}
