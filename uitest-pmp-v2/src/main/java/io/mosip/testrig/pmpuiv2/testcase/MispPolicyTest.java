package io.mosip.testrig.pmpuiv2.testcase;

import io.mosip.testrig.pmpuiv2.pages.*;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.utility.BaseClass;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test(dependsOnGroups = { "MispPartnerTest" }, groups = { "MispPolicyTest" })
public class MispPolicyTest extends BaseClass {
	private DashboardPage dashboardPage;
	private PoliciesPage policiesPage;
	private LoginPage loginPage;
	private PartnerPolicyMappingPage partnerPolicyMappingPage;
	private MispPolicyPage mispPolicyPage;
	private PolicyGroupPage policygroupPage;
	private BasePage basePage;

	@Test(priority = 01, description = "This is a test case to create new misp policy")
	public void createMispPolicy() throws InterruptedException {
		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		mispPolicyPage = new MispPolicyPage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		basePage = new BasePage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		dashboardPage.clickOnPolicyButton();

		createPolicyGroup(GlobalConstants.MISP_POLICYGROUP, GlobalConstants.MISP_POLICYGROUP);

		policiesPage.clickOnMispPolicyTab();
		mispPolicyPage.clickOnCreateMispPolicyButton();

		verifyMispPolicyCreationLabels();

		verifyMispPolicyCreationPlaceHoders();

		createMispPolicy(GlobalConstants.DEFAULT_POLICYGROUP, GlobalConstants.MISP_POLICY_01,
				GlobalConstants.MISP_POLICY_01_DESCRIPTION);

		assertTrue(mispPolicyPage.isTitleOfSuccessMessageDisplayed(), GlobalConstants.isTitleOfSuccessMessageDisplayed);
		assertTrue(mispPolicyPage.isSubTitleOfSuccessMessageDisplayed(),
				GlobalConstants.isSubTitleOfSuccessMessageDisplayed);
		assertTrue(mispPolicyPage.isSuccessGoBackButtonAvailable(), GlobalConstants.isSuccessGoBackButtonAvailable);
		assertTrue(mispPolicyPage.isSuccessPublishButtonAvailable(), GlobalConstants.isSuccessPublishButtonAvailable);
		mispPolicyPage.clickOnSuccessPublishButton();
		mispPolicyPage.clickOnPublishPolicyButton();
		mispPolicyPage.clickOnPublishPolicyCloseButton();

		mispPolicyPage.clickOnCreateMispPolicyButton();
		mispPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		mispPolicyPage.enterPolicyName(GlobalConstants.MISP_POLICY_02);
		mispPolicyPage.enterpolicyDescription(GlobalConstants.MISP_POLICY_02_DESCRIPTION);

		assertTrue(mispPolicyPage.isSubmitButtonDisabled(), GlobalConstants.isSubmitButtonDisabled);
		mispPolicyPage.uploadInvalidPolicyData();
		assertTrue(mispPolicyPage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		mispPolicyPage.clickOnErrorCloseButton();
		mispPolicyPage.clickOnCreatePolicyClearButton();

		createMispPolicy(GlobalConstants.DEFAULT_POLICYGROUP, GlobalConstants.MISP_POLICY_DEACTIVATE,
				GlobalConstants.MISP_POLICY_DEACTIVATE_DESCRIPTION);
		mispPolicyPage.clickOnSuccessPublishButton();
		assertTrue(mispPolicyPage.isPublishPolicyPopupTitleDisplayed(),
				GlobalConstants.isPublishPolicyPopupTitleDisplayed);
		assertTrue(mispPolicyPage.isPublishPolicyPopupPolicyNameDisplayed(),
				GlobalConstants.isPublishPolicyPopupPolicyNameDisplayed);
		mispPolicyPage.clickOnPublishPolicyButton();
		mispPolicyPage.clickOnPublishPolicyCloseButton();

		mispPolicyPage.clickOnCreateMispPolicyButton();
		createMispPolicy(GlobalConstants.DEFAULT_POLICYGROUP, GlobalConstants.MISP_POLICY_EDIT,
				GlobalConstants.MISP_POLICY_EDIT_DESCRIPTION);
		mispPolicyPage.clickOnGoBackButton();

		clickOnEditMispPolicy(GlobalConstants.MISP_POLICY_EDIT);
		assertEquals(mispPolicyPage.getPageTitle(), GlobalConstants.EDIT_MISP_POLICY_PAGE);
		mispPolicyPage.enterpolicyDescription(GlobalConstants.MISP_POLICY2_EDIT_DESCRIPTION);
		mispPolicyPage.clickOnEditPolicyFormSubmitButton();
		mispPolicyPage.clickOnGoBackButton();

		clickOnPublishMispPolicy(GlobalConstants.MISP_POLICY_EDIT);
		mispPolicyPage.clickOnPublishPolicyButton();
		mispPolicyPage.clickOnPublishPolicyCloseButton();

		clickOnCloneMispPolicy(GlobalConstants.MISP_POLICY_EDIT);
		assertTrue(mispPolicyPage.isClonePolicyTitleDisplayed(), GlobalConstants.isClonePolicyTitleDisplayed);
		mispPolicyPage.selectValidPolicyGroupForClone(GlobalConstants.MISP_POLICYGROUP);
		mispPolicyPage.clickOnClonePolicyButton();
		assertTrue(mispPolicyPage.isClonePolicyPopupSuccessMessageDisplayed(),
				GlobalConstants.isClonePolicyPopupSuccessMessageDisplayed);
		mispPolicyPage.clickOnClonePolicyCloseButton();

		clickOnDeactivateMispPolicy(GlobalConstants.MISP_POLICY_DEACTIVATE);
		assertTrue(mispPolicyPage.isDeactivatePolicyPopupHeaderDisplayed(),
				GlobalConstants.isDeactivatePolicyPopupHeaderDisplayed);
		mispPolicyPage.clickOnDeactivateConfirmButton();

		clickOnPartnerPolicyLinkingViewButton(GlobalConstants.MISP_POLICY_DEACTIVATE);
		assertEquals(mispPolicyPage.getPageTitle(), GlobalConstants.VIEW_MISP_POLICY_PAGE);
		mispPolicyPage.clickOnListOfMispPoliciesButton();

		mispPolicyPage.clickOnCreateMispPolicyButton();
		createMispPolicy(GlobalConstants.DEFAULT_POLICYGROUP, GlobalConstants.MISP_POLICY_03,
				GlobalConstants.MISP_POLICY_03_DESCRIPTION);
		mispPolicyPage.clickOnSuccessPublishButton();
		mispPolicyPage.clickOnPublishPolicyButton();
		mispPolicyPage.clickOnPublishPolicyCloseButton();

	}

	@Test(priority = 02, description = "This is a test case to link misp policy", dependsOnMethods = "createMispPolicy")
	public void partnerPolicyLinking() throws InterruptedException {
		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		mispPolicyPage = new MispPolicyPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);
		basePage = new BasePage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnRequestPolicyButton();

		assertTrue(partnerPolicyMappingPage.isRequestPolicyHomeButtonDisplayed(),
				GlobalConstants.isRequestPolicyHomeButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isListOfPartnerPolicyLinkagesDisplayed(),
				GlobalConstants.isListOfPartnerPolicyLinkagesDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyBreadcrumbDisplayed(),
				GlobalConstants.isRequestPolicyBreadcrumbDisplayed);

		assertTrue(partnerPolicyMappingPage.isRequestPolicyMandatoryMsgDisplayed(),
				GlobalConstants.isRequestPolicyMandatoryMsgDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyPartnerIdInfoDisplayed(),
				GlobalConstants.isRequestPolicyPartnerIdInfoDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyPartnerTypeDropdownDisplayed(),
				GlobalConstants.isRequestPolicyPartnerTypeDropdownDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyPartnerIdDropdownDisplayed(),
				GlobalConstants.isRequestPolicyPartnerIdDropdownDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyPolicyGroupDisplayed(),
				GlobalConstants.isRequestPolicyPolicyGroupDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesPolicyNameDropdownDisplayed(),
				GlobalConstants.isRequestPoliciesPolicyNameDropdownDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyCommentBoxDisplayed(),
				GlobalConstants.isRequestPolicyCommentBoxDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesFormClearButtonDisplayed(),
				GlobalConstants.isRequestPoliciesFormClearButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesFormCancelButtonDisplayed(),
				GlobalConstants.isRequestPoliciesFormCancelButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesFormSubmitButtonDisplayed(),
				GlobalConstants.isRequestPoliciesFormSubmitButtonDisplayed);

		partnerPolicyMappingPage.selectPartnerType(GlobalConstants.MISP_PARTNER);
		partnerPolicyMappingPage.searchPartnerIdInDropdown(GlobalConstants.DEACTIVATE_MISPPARTNER);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyPartnerIdNoDataAvailableDisplayed(),
				GlobalConstants.isRequestPolicyPartnerIdNoDataAvailableDisplayed);

		partnerPolicyMappingPage.clickOnRequestPolicyPartnerIdDropdown();
		partnerPolicyMappingPage.searchPartnerIdInDropdown(GlobalConstants.DEVICE_PARTNER_ID);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyPartnerIdNoDataAvailableDisplayed(),
				GlobalConstants.isRequestPolicyPartnerIdNoDataAvailableDisplayed);

		partnerPolicyMappingPage.clickOnRequestPoliciesFormClearButton();
		partnerPolicyMappingPage.selectPartnerType(GlobalConstants.MISP_PARTNER);
		partnerPolicyMappingPage.selectPartnerIdDropdown(GlobalConstants.MISP_PARTNER_USER);
		partnerPolicyMappingPage.clickOnRequestPoliciesPolicyNameDropdown();
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesPolicyNameSearchInputDisplayed(),
				GlobalConstants.isRequestPoliciesPolicyNameSearchInputDisplayed);
		partnerPolicyMappingPage.clickOnRequestPoliciesPolicyNameDropdown();

		partnerPolicyMappingPage.searchPolicyNameInDropdown(GlobalConstants.MISP_POLICY_01);
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesPolicyNameOptionDisplayed(),
				GlobalConstants.isRequestPoliciesPolicyNameOptionDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesPolicyNameDescriptionDisplayed(),
				GlobalConstants.isRequestPoliciesPolicyNameDescriptionDisplayed);

		assertTrue(partnerPolicyMappingPage.isRequestPoliciesFormSubmitButtonDisabled(),
				GlobalConstants.isRequestPoliciesFormSubmitButtonDisabled);

		partnerPolicyMappingPage.clickOnRequestPoliciesFormCancelButton();
		assertTrue(partnerPolicyMappingPage.isBlockerPromptDescriptionDisplayed(),
				GlobalConstants.isBlockerPromptDescriptionDisplayed);
		partnerPolicyMappingPage.clickOnBlockMessageProceedButton();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingTitleDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingSubTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingSubTitleDisplayed);

		requestPolicyForMispPartner(GlobalConstants.MISP_PARTNER, GlobalConstants.MISP_PARTNER_USER,
				GlobalConstants.MISP_POLICY_01, GlobalConstants.MISP_POLICY_01_DESCRIPTION);

		assertTrue(partnerPolicyMappingPage.isConfirmationSuccessIconDisplayed(),
				GlobalConstants.isConfirmationSuccessIconDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyConfirmationHeaderDisplayed(),
				GlobalConstants.isRequestPolicyConfirmationHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isRequestPolicyConfirmationDescriptionDisplayed(),
				GlobalConstants.isRequestPolicyConfirmationDescriptionDisplayed);
		assertTrue(partnerPolicyMappingPage.isConfirmationCustomButtonDisplayed(),
				GlobalConstants.isConfirmationCustomButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isConfirmationGoBackButtonDisplayed(),
				GlobalConstants.isConfirmationGoBackButtonDisplayed);
		partnerPolicyMappingPage.clickOnConfirmationCustomButton();

		assertTrue(partnerPolicyMappingPage.isApprovePopupTitleDisplayed(),
				GlobalConstants.isApprovePopupTitleDisplayed);
		assertTrue(partnerPolicyMappingPage.isConfirmPopupDetailedMessageDisplayed(),
				GlobalConstants.isConfirmationPopupDetailedMessageDisplayed);
		assertTrue(partnerPolicyMappingPage.isApprovePopupCancelButtonDisplayed(),
				GlobalConstants.isApprovePopupCancelButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isApprovePopupSubmitButtonDisplayed(),
				GlobalConstants.isApprovePopupSubmitButtonDisplayed);
		partnerPolicyMappingPage.clickOnApprovePopupSubmitButton();

		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingTitleDisplayed);
		verifyStatusOfPartnerPolicyMapping(GlobalConstants.MISP_POLICY_01, GlobalConstants.APPROVED);

		requestPolicyForMispPartner(GlobalConstants.MISP_PARTNER, GlobalConstants.MISP_PARTNER_USER,
				GlobalConstants.MISP_POLICY_01, GlobalConstants.MISP_POLICY_01_DESCRIPTION);
		basePage.scrollToStartPage();
		assertTrue(partnerPolicyMappingPage.isRequestPolicyErrorMsgDisplayed(),
				GlobalConstants.isRequestPolicyErrorMsgDisplayed);
		partnerPolicyMappingPage.clickOnRequestPoliciesFormClearButton();
		partnerPolicyMappingPage.clickOnRequestPoliciesFormCancelButton();

		requestPolicyForMispPartner(GlobalConstants.MISP_PARTNER, GlobalConstants.MISP_PARTNER_USER,
				GlobalConstants.MISP_POLICY_03, GlobalConstants.MISP_POLICY_03_DESCRIPTION);
		partnerPolicyMappingPage.clickOnConfirmationGoBackButton();

		verifyStatusOfPartnerPolicyMapping(GlobalConstants.MISP_POLICY_03, GlobalConstants.PENDING_FOR_APPROVAL);

		clickOnActionButton(GlobalConstants.MISP_POLICY_03);
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();

		assertTrue(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyPopupSubtitleDisplayed(),
				GlobalConstants.isPolicyPopupSubtitleDisplayed);
		assertTrue(partnerPolicyMappingPage.isApproveRejectPopupTitleDisplayed(),
				GlobalConstants.isApproveRejectPopupTitleDisplayed);
		assertTrue(partnerPolicyMappingPage.isApproveRejectPopupSubTitleDisplayed(),
				GlobalConstants.isApproveRejectPopupSubTitleDisplayed);
		assertTrue(partnerPolicyMappingPage.isConfirmationPopupDetailedMessageDisplayed(),
				GlobalConstants.isConfirmationPopupDetailedMessageDisplayed);
		assertTrue(partnerPolicyMappingPage.isApproveRejectButtonDisplayed(),
				GlobalConstants.isApproveRejectButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isApproveSubmitButtonDisplayed(),
				GlobalConstants.isApproveSubmitButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameInPopupDisplayed(),
				GlobalConstants.isPolicyNameInPopupDisplayed);
		partnerPolicyMappingPage.clickOnRejectButton();
		partnerPolicyMappingPage.clickOnFilterResetButton();

		verifyStatusOfPartnerPolicyMapping(GlobalConstants.MISP_POLICY_03, GlobalConstants.REJECTED);
		requestPolicyForMispPartner(GlobalConstants.MISP_PARTNER, GlobalConstants.MISP_PARTNER_USER,
				GlobalConstants.MISP_POLICY_03, GlobalConstants.MISP_POLICY_03_DESCRIPTION);
		partnerPolicyMappingPage.clickOnConfirmationCustomButton();
		partnerPolicyMappingPage.clickOnApprovePopupSubmitButton();

		clickOnActionButton(GlobalConstants.MISP_POLICY_03);
		partnerPolicyMappingPage.clickOnViewButton();
		assertEquals(mispPolicyPage.getPageTitle(), GlobalConstants.VIEW_PARTNER_POLICY_PAGE);
		partnerPolicyMappingPage.clickOnTitleBackIcon();

	}

	private void createMispPolicy(String policyGroup, String policyName, String policyDescription) {
		mispPolicyPage.selectPolicyGroupDropdown(policyGroup);
		mispPolicyPage.enterPolicyName(policyName);
		mispPolicyPage.enterpolicyDescription(policyDescription);
		mispPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		mispPolicyPage.clickOnCreatePolicySubmitButton();
	}

	private void verifyMispPolicyCreationLabels() {
		assertTrue(mispPolicyPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertTrue(mispPolicyPage.isListOfMispPoliciesButtonDisplayed(),
				GlobalConstants.isListOfMispPoliciesButtonDisplayed);
		assertTrue(mispPolicyPage.isFieldsMarkedWithMandatoryLabelDisplayed(),
				GlobalConstants.isFieldsMarkedWithMandatoryLabelDisplayed);
		assertTrue(mispPolicyPage.isPolicyGroupLabelDisplayed(), GlobalConstants.isPolicyGroupLabelDisplayed);
		assertTrue(mispPolicyPage.isPolicyNameLabelDisplayed(), GlobalConstants.isPolicyNameLabelDisplayed);
		assertTrue(mispPolicyPage.isPolicyDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyDescriptionLabelDisplayed);

	}

	private void verifyMispPolicyCreationPlaceHoders() {
		assertTrue(mispPolicyPage.isSelectPolicyGroupPlaceholderDisplayed(),
				GlobalConstants.isPolicyGroupPlaceHolderDisplayed);
		assertEquals(mispPolicyPage.getPolicyNamePlaceholder(), "Enter name for MISP Policy",
				"Verify policy name placeholder");
		assertEquals(mispPolicyPage.getPolicyDescriptionPlaceholder(), "Enter description about MISP Policy",
				"Verify policy description placeholder");
		assertEquals(mispPolicyPage.getPolicyDataPlaceholder(),
				"Upload the json file successfully to display its content here", "Verify policy data placeholder");
		assertTrue(mispPolicyPage.isPolicyDataUploadHeaderDisplayed(),
				GlobalConstants.isPolicyDataUploadHeaderDisplayed);
		assertTrue(mispPolicyPage.isPolicyDataUploadDescriptionDisplayed(),
				GlobalConstants.isPolicyDataUploadDescriptionDisplayed);

	}

	private void loginAsPartnerAdmin() {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.PARTNER_ADMIN);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

	}

	private void createPolicyGroup(String policyGroupNameValue, String policyGroupDescValue) {
		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.enterPolicyGroupName(policyGroupNameValue);
		policygroupPage.enterPolicyGroupNameDescription(policyGroupDescValue);
		policygroupPage.clickOnSubmitButton();
		policygroupPage.clickOnSuccessGoBackButton();

	}

	private void clickOnEditMispPolicy(String policyName) {
		mispPolicyPage.clickOnActionOfMispPolicy(policyName);
		mispPolicyPage.clickOnPolicyEditButton();
	}

	private void clickOnPublishMispPolicy(String policyName) {
		mispPolicyPage.clickOnActionOfMispPolicy(policyName);
		mispPolicyPage.clickOnPolicyPublishButton();
	}

	private void clickOnCloneMispPolicy(String policyName) {
		mispPolicyPage.clickOnActionOfMispPolicy(policyName);
		mispPolicyPage.clickOnPolicyCloneButton();
	}

	private void clickOnDeactivateMispPolicy(String policyName) {
		mispPolicyPage.clickOnActionOfMispPolicy(policyName);
		mispPolicyPage.clickOnPolicyDeactivateButton();
	}

	private void clickOnPartnerPolicyLinkingViewButton(String policyName) {
		mispPolicyPage.clickOnActionOfMispPolicy(policyName);
		mispPolicyPage.clickOnViewButton();
	}

	private void requestPolicyForMispPartner(String partnerType, String partnerId, String policyName, String comment) {
		partnerPolicyMappingPage.clickOnRequestPolicyButton();
		partnerPolicyMappingPage.selectPartnerType(partnerType);
		partnerPolicyMappingPage.selectPartnerIdDropdown(partnerId);
		partnerPolicyMappingPage.selectPolicyName(policyName);
		partnerPolicyMappingPage.enterRequestPolicyComment(comment);
		assertTrue(partnerPolicyMappingPage.isRequestPoliciesFormSubmitButtonEnabled(),
				GlobalConstants.isRequestPoliciesFormSubmitButtonEnabled);
		partnerPolicyMappingPage.clickOnRequestPoliciesFormSubmitButton();
	}

	private void verifyStatusOfPartnerPolicyMapping(String policyName, String status) {
		assertTrue(partnerPolicyMappingPage.isStatusDisplayedForPolicyName(policyName, status),
				GlobalConstants.isRequestPoliciesFormSubmitButtonEnabled);
	}

	private void clickOnActionButton(String policyName) {
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(policyName);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();

	}

}
