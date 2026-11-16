package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.DatasharePolicyPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.PolicyGroupPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "AbisPartnerPolicyLinkingTest" })
public class AbisPartnerPolicyLinkingTest extends BaseClass {

	private DashboardPage dashboardPage;
	private DatasharePolicyPage datasharePolicyPage;
	private AuthPolicyPage authPolicyPage;
	private PolicyGroupPage policyGroupPage;
	private MispPartnerPage partnerPage;
	private PartnerCertificatePage partnerCertificatePage;
	private PartnerPolicyMappingPage partnerPolicyMappingPage;
	private BasePage basePage;

	@Test(priority = 1, description = "MOSIP-43610: Create and publish Data Share policies for ABIS partner policy linking")
	public void createDataSharePoliciesForAbisPartner() {
		dashboardPage = new DashboardPage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		policyGroupPage = new PolicyGroupPage(driver);
		basePage = new BasePage(driver);

		dashboardPage.clickOnPolicyButton();
		policyGroupPage.clickOnDatasharePolicyTab();
		createAndPublishDataSharePolicy(GlobalConstants.ABIS_POLICY_01, GlobalConstants.ABIS_POLICY_01_DESCRIPTION);
		createAndPublishDataSharePolicy(GlobalConstants.ABIS_POLICY_03, GlobalConstants.ABIS_POLICY_03_DESCRIPTION);
		datasharePolicyPage.clickOnHomeButton();
	}

	@Test(priority = 2, description = "MOSIP-43610: Create an ABIS partner and upload the AUTH partner certificate",
			dependsOnMethods = "createDataSharePoliciesForAbisPartner")
	public void createAbisPartnerForPolicyLinking() {
		dashboardPage = new DashboardPage(driver);
		partnerPage = new MispPartnerPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		basePage = new BasePage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		partnerCertificatePage.clickOnIntermediateCACertTab();
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();
		partnerCertificatePage.clickOnHomeButton();

		dashboardPage.clickOnPartners();
		partnerPage.clickOnCreatePartnerButton();
		partnerPage.clickOnPartnerTypeDropdown();
		partnerPage.clickOnAbisPartnerOption();
		assertEquals(partnerPage.getSelectedPartnerTypeText(), GlobalConstants.ABIS_PARTNER,
				GlobalConstants.isAbisPartnerSelectedSuccessfully);
		partnerPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPage.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
		partnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
		partnerPage.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
		partnerPage.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
		partnerPage.enterEmailId(GlobalConstants.ABIS_EMAIL_ID);
		partnerPage.enterUserName(GlobalConstants.ABIS_PARTNER_USER);
		partnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(partnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isAbisPartnerCreatedSuccessfully);

		partnerPage.clickOnUploadPartnerCertificateButton();
		assertTrue(partnerCertificatePage.isMispPartnerCertificatePopupDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed(),
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		basePage.scrollToStartPage();
	}

	@Test(priority = 3, description = "MOSIP-43610: Request, approve, reject, and view an ABIS partner policy linking",
			dependsOnMethods = "createAbisPartnerForPolicyLinking")
	public void requestAndManageAbisPartnerPolicyLinking() {
		dashboardPage = new DashboardPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);
		basePage = new BasePage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingTitleDisplayed);

		partnerPolicyMappingPage.clickOnRequestPolicyButton();
		assertTrue(partnerPolicyMappingPage.isRequestPolicyMandatoryMsgDisplayed(),
				GlobalConstants.isRequestPolicyMandatoryMsgDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyPartnerTypeDropdownDisplayed(),
				GlobalConstants.isRequestPolicyPartnerTypeDropdownDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyPartnerIdDropdownDisplayed(),
				GlobalConstants.isRequestPolicyPartnerIdDropdownDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyPartnerIdInfoDisplayed(),
				GlobalConstants.isRequestPolicyPartnerIdInfoDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesFormSubmitButtonDisabled(),
				GlobalConstants.isRequestPoliciesFormSubmitButtonDisabled);

		partnerPolicyMappingPage.selectPartnerType(GlobalConstants.ABIS_PARTNER);
		assertTrue(partnerPolicyMappingPage.getPartnerType().contains(GlobalConstants.ABIS_PARTNER),
				GlobalConstants.isAbisPartnerSelectedSuccessfully);
		partnerPolicyMappingPage.selectPartnerIdDropdown(GlobalConstants.ABIS_PARTNER_USER);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyGroupDisabled(),
				GlobalConstants.isRequestPolicyGroupDisabled);
		assertTrue(partnerPolicyMappingPage.getRequestPolicyPolicyGroupText()
				.contains(GlobalConstants.DEFAULT_POLICYGROUP), GlobalConstants.isRequestPolicyPolicyGroupDisplayed);

		partnerPolicyMappingPage.selectPolicyName(GlobalConstants.ABIS_POLICY_01);
		partnerPolicyMappingPage.enterRequestPolicyComment(GlobalConstants.ABIS_POLICY_01_DESCRIPTION);
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesFormSubmitButtonEnabled(),
				GlobalConstants.isRequestPoliciesFormSubmitButtonEnabled);
		partnerPolicyMappingPage.clickOnRequestPoliciesFormSubmitButton();

		assertTrue(partnerPolicyMappingPage.isConfirmationSuccessIconDisplayed(),
				GlobalConstants.isConfirmationSuccessIconDisplayed);
		partnerPolicyMappingPage.clickOnConfirmationCustomButton();
		assertTrue(partnerPolicyMappingPage.isApprovePopupTitleDisplayed(),
				GlobalConstants.isApprovePopupTitleDisplayed);
		partnerPolicyMappingPage.clickOnApprovePopupSubmitButton();
		verifyStatus(GlobalConstants.ABIS_POLICY_01, GlobalConstants.APPROVED);

		requestPolicy(GlobalConstants.ABIS_POLICY_01, GlobalConstants.ABIS_POLICY_01_DESCRIPTION);
		basePage.scrollToStartPage();
		assertTrue(partnerPolicyMappingPage.isRequestPolicyErrorMsgDisplayed(),
				GlobalConstants.isRequestPolicyErrorMsgDisplayed);
		partnerPolicyMappingPage.clickOnRequestPoliciesFormClearButton();
		partnerPolicyMappingPage.clickOnRequestPoliciesFormCancelButton();

		requestPolicy(GlobalConstants.ABIS_POLICY_03, GlobalConstants.ABIS_POLICY_03_DESCRIPTION);
		partnerPolicyMappingPage.clickOnConfirmationGoBackButton();
		verifyStatus(GlobalConstants.ABIS_POLICY_03, GlobalConstants.PENDING_FOR_APPROVAL);

		clickOnActionButton(GlobalConstants.ABIS_POLICY_03);
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertTrue(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		partnerPolicyMappingPage.clickOnRejectButton();
		verifyStatus(GlobalConstants.ABIS_POLICY_03, GlobalConstants.REJECTED);
		partnerPolicyMappingPage.clickOnFilterResetButton();

		requestPolicy(GlobalConstants.ABIS_POLICY_03, GlobalConstants.ABIS_POLICY_03_DESCRIPTION);
		partnerPolicyMappingPage.clickOnConfirmationCustomButton();
		partnerPolicyMappingPage.clickOnApprovePopupSubmitButton();

		clickOnActionButton(GlobalConstants.ABIS_POLICY_03);
		partnerPolicyMappingPage.clickOnViewButton();
		assertEquals(partnerPolicyMappingPage.getPageTitle(), GlobalConstants.VIEW_PARTNER_POLICY_PAGE,
				GlobalConstants.isViewPartnerPolicyLinkingPageTitleDisplayed);
		partnerPolicyMappingPage.clickOnTitleBackIcon();
	}

	private void createAndPublishDataSharePolicy(String policyName, String description) {
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyName(policyName);
		datasharePolicyPage.enterpolicyDescription(description);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		datasharePolicyPage.clickOnGoBackButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(policyName);
		authPolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnPublishButton();
		datasharePolicyPage.clickOnPublishPolicyButton();
		datasharePolicyPage.clickOnPublishPolicyCloseButton();
	}

	private void requestPolicy(String policyName, String comment) {
		partnerPolicyMappingPage.clickOnRequestPolicyButton();
		partnerPolicyMappingPage.selectPartnerType(GlobalConstants.ABIS_PARTNER);
		partnerPolicyMappingPage.selectPartnerIdDropdown(GlobalConstants.ABIS_PARTNER_USER);
		partnerPolicyMappingPage.selectPolicyName(policyName);
		partnerPolicyMappingPage.enterRequestPolicyComment(comment);
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesFormSubmitButtonEnabled(),
				GlobalConstants.isRequestPoliciesFormSubmitButtonEnabled);
		partnerPolicyMappingPage.clickOnRequestPoliciesFormSubmitButton();
	}

	private void verifyStatus(String policyName, String status) {
		assertTrue(partnerPolicyMappingPage.isStatusDisplayedForPolicyName(policyName, status),
				GlobalConstants.isStatusDisplayedForPolicyName);
	}

	private void clickOnActionButton(String policyName) {
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(policyName);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
	}
}
