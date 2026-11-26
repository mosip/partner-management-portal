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
		createAndPublishDataSharePolicy(GlobalConstants.ABIS_POLICY_02, GlobalConstants.ABIS_POLICY_02_DESCRIPTION);
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
		uploadAuthTrustCertificate(true);
		partnerCertificatePage.clickOnIntermediateCACertTab();
		uploadAuthTrustCertificate(false);
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

		requestPolicy(GlobalConstants.ABIS_POLICY_02, GlobalConstants.ABIS_POLICY_02_DESCRIPTION);
		partnerPolicyMappingPage.clickOnConfirmationGoBackButton();
		verifyStatus(GlobalConstants.ABIS_POLICY_02, GlobalConstants.PENDING_FOR_APPROVAL);

		approveOrRejectFromList(GlobalConstants.ABIS_POLICY_02, GlobalConstants.PENDING_FOR_APPROVAL, false);
		verifyStatus(GlobalConstants.ABIS_POLICY_02, GlobalConstants.REJECTED);

		requestPolicy(GlobalConstants.ABIS_POLICY_02, GlobalConstants.ABIS_POLICY_02_DESCRIPTION);
		partnerPolicyMappingPage.clickOnConfirmationGoBackButton();
		approveOrRejectFromList(GlobalConstants.ABIS_POLICY_02, GlobalConstants.PENDING_FOR_APPROVAL, true);
		verifyStatus(GlobalConstants.ABIS_POLICY_02, GlobalConstants.APPROVED);

		// Two rows match this policy: the rejected request and the approved one.
		// Open the Approved row, which is the re-requested linking.
		filterPolicy(GlobalConstants.ABIS_POLICY_02);
		partnerPolicyMappingPage.clickOnActionForPolicyStatus(GlobalConstants.ABIS_POLICY_02, GlobalConstants.APPROVED);
		partnerPolicyMappingPage.clickOnViewButton();
		assertEquals(partnerPolicyMappingPage.getPageTitle(), GlobalConstants.VIEW_PARTNER_POLICY_PAGE,
				GlobalConstants.isViewPartnerPolicyLinkingPageTitleDisplayed);
		partnerPolicyMappingPage.clickOnTitleBackIcon();
	}

	private void uploadAuthTrustCertificate(boolean rootCertificate) {
		if (rootCertificate) {
			partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		} else {
			partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		}
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		if (rootCertificate) {
			partnerCertificatePage.uploadCertificateRootCa();
		} else {
			partnerCertificatePage.uploadCertificateSubCa();
		}
		assertTrue(partnerCertificatePage.submitTrustCertificateAcceptingExisting(),
				"AUTH trust certificate was neither accepted nor reported as already present");
	}

	private void approveOrRejectFromList(String policyName, String currentStatus, boolean approve) {
		filterPolicy(policyName);
		partnerPolicyMappingPage.clickOnActionForPolicyStatus(policyName, currentStatus);
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertTrue(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		if (approve) {
			partnerPolicyMappingPage.clickOnApproveSubmitButton();
		} else {
			partnerPolicyMappingPage.clickOnRejectButton();
		}
		partnerPolicyMappingPage.waitForApproveRejectPopupToClose();
	}

	private void filterPolicy(String policyName) {
		if (partnerPolicyMappingPage.isFilterButtonDisabled()) {
			partnerPolicyMappingPage.clickOnFilterResetButton();
		}
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(policyName);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
	}

	private void createAndPublishDataSharePolicy(String policyName, String description) {
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyName(policyName);
		datasharePolicyPage.enterpolicyDescription(description);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		assertTrue(datasharePolicyPage.isTitleOfSuccessMessageDisplayed(), "Data Share policy draft was not saved");
		datasharePolicyPage.clickOnGoBackButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(policyName);
		authPolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnPublishButton();
		datasharePolicyPage.clickOnPublishPolicyButton();
		assertTrue(datasharePolicyPage.isPublishPolicyPopupSuccessMessageDisplayed(),
				"Data Share policy was not published");
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
		filterPolicy(policyName);
		assertTrue(partnerPolicyMappingPage.isStatusDisplayedForPolicyName(policyName, status),
				GlobalConstants.isStatusDisplayedForPolicyName);
		partnerPolicyMappingPage.clickOnFilterResetButton();
	}
}
