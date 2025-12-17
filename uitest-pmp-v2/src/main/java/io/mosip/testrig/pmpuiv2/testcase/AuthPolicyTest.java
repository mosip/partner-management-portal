package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.ApiKeyPage;
import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.OidcClientPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.pages.PolicyGroupPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PolicyGroupTest" }, groups = { "AuthPolicyTest" })
public class AuthPolicyTest extends BaseClass {

	private BasePage basePage;
	private DashboardPage dashboardPage;
	private PoliciesPage policiesPage;
	private PolicyGroupPage policygroupPage;
	private LoginPage loginPage;
	private ApiKeyPage apiKeyPage;
	private AuthPolicyPage authPolicyPage;
	private PartnerPolicyMappingPage partnerPolicyMappingPage;
	private OidcClientPage oidcClientPage;

	@Test(priority = 1, description = "Create Auth Policy")
	public void createAuthPolicy() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();

		assertTrue(authPolicyPage.isPolicyFormSubTitleDisplayed(), GlobalConstants.isPolicyFormSubTitleDisplayed);
		assertTrue(authPolicyPage.isPolicyGroupDropdownDisplayed(), GlobalConstants.isPolicyGroupDropdownDisplayed);
		authPolicyPage.clickOnPolicyGroupDropdown();
		assertTrue(authPolicyPage.isPolicyGroupDropdownSearchInputDisplayed(),
				GlobalConstants.isPolicyGroupDropdownSearchInputDisplayed);
		assertTrue(authPolicyPage.isPolicyGroupPlaceHolderDisplayed(),
				GlobalConstants.isPolicyGroupPlaceHolderDisplayed);
		authPolicyPage.clickOnPolicyGroupDropdown();
		assertTrue(authPolicyPage.isAuthPolicyPlaceHolderDisplayed(), GlobalConstants.isAuthPolicyPlaceHolderDisplayed);
		assertTrue(authPolicyPage.isPolicyDescriptionPlaceHolderDisplayed(),
				GlobalConstants.isPolicyDescriptionPlaceHolderDisplayed);
		assertTrue(authPolicyPage.isUploadPolicyDataLabelDisplayed(), GlobalConstants.isUploadPolicyDataLabelDisplayed);
		assertTrue(authPolicyPage.isUploadPolicyDataHelpTextDisplayed(),
				GlobalConstants.isUploadPolicyDataHelpTextDisplayed);
		assertTrue(authPolicyPage.isFileUploadPlaceHolderDisplayed(), GlobalConstants.isFileUploadPlaceHolderDisplayed);
		assertFalse(authPolicyPage.isPolicyDataBoxEnabled(), GlobalConstants.isPolicyDataBoxEnabled);

		authPolicyPage.selectPolicyGroup(GlobalConstants.DEFAULT_POLICYGROUP);
		assertTrue(authPolicyPage.isPolicyGroupNameDisplayed(), GlobalConstants.isPolicyGroupNameDisplayed);
		assertTrue(authPolicyPage.isPolicyGroupDescriptionDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionDisplayed);
		authPolicyPage.clickOnPolicyGroupDropdown();

		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY03);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY03_DESCRIPTION);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		assertTrue(authPolicyPage.isPolicySavedAsDraftMessageDisplayed(),
				GlobalConstants.isPolicySavedAsDraftMessageDisplayed);
		assertTrue(authPolicyPage.isPolicyDraftInfoMessageDisplayed(),
				GlobalConstants.isPolicyDraftInfoMessageDisplayed);
		authPolicyPage.clickOnGoBackButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY03);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();

		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY09);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY09_DESCRIPTION);
		authPolicyPage.uploadPolicyData();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY09);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();

		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.DEACTIVATE_AUTH2);
		authPolicyPage.enterpolicyDescription(GlobalConstants.DEACTIVATE_AUTH2_DESCRIPTION);
		authPolicyPage.uploadPolicyData();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.DEACTIVATE_AUTH2);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnDeactivateButton();
		authPolicyPage.clickOnDeactivateConfirmButton();

		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectDeactivatedPolicyGroupInDropdown(GlobalConstants.DEACTIVATE_POLICYGROUP);
		assertTrue(authPolicyPage.isNoPolicyGroupFoundDisplayed(), GlobalConstants.isNoPolicyGroupFoundDisplayed);
		authPolicyPage.clickOnPolicyCancelButton();

		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(authPolicyPage.isSpecialCharactersAreNotAllowedErrorMessageDisplayed(),
				GlobalConstants.isPolicyDataUploadedSuccessMessageDisplayed);
		authPolicyPage.enterpolicyDescription(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(authPolicyPage.isSpecialCharactersAreNotAllowedErrorMessageDisplayed(),
				GlobalConstants.isPolicyDataUploadedSuccessMessageDisplayed);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTOMATION_25);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTOMATION_25);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToStartPage();
		assertTrue(authPolicyPage.isPolicyDataUploadedSuccessMessageDisplayed(),
				GlobalConstants.isPolicyDataUploadedSuccessMessageDisplayed);
		assertTrue(authPolicyPage.isPolicyDataBoxEnabled(), GlobalConstants.isPolicyDataBoxEnabled);
		assertTrue(authPolicyPage.isPolicyDataContentDisplayed(), GlobalConstants.isPolicyDataContentDisplayed);
		authPolicyPage.editPolicyData(GlobalConstants.RANDOM_DATA);
		assertTrue(authPolicyPage.isPolicyDataEdited(), GlobalConstants.isPolicyDataEdited);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY03);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY03_DESCRIPTION);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		basePage.scrollToStartPage();
		assertTrue(authPolicyPage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);
		authPolicyPage.clickOnErrorCloseButton();
		authPolicyPage.enterpolicyDescription(GlobalConstants.ALPHANUMERIC2);
		authPolicyPage.clickOnSaveAsDraftButton();
		assertTrue(authPolicyPage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);
		authPolicyPage.clickOnErrorCloseButton();
		authPolicyPage.enterPolicyName(GlobalConstants.ALPHANUMERIC2);
		authPolicyPage.enterpolicyDescription(GlobalConstants.ALPHANUMERIC2);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();
	}

	@Test(priority = 2, description = "Upload Invalid auth Policy Data", dependsOnMethods = "createAuthPolicy")
	public void uploadInvalidAuthPolicyData() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.DEACTIVATE_AUTH2);
		authPolicyPage.enterpolicyDescription(GlobalConstants.DEACTIVATE_AUTH2_DESCRIPTION);
		authPolicyPage.uploadBlankData();
		assertTrue(authPolicyPage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		authPolicyPage.clickOnErrorCloseButton();
		authPolicyPage.uploadInvalidPolicyData();
		assertTrue(authPolicyPage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		authPolicyPage.clickOnErrorCloseButton();
		authPolicyPage.uploadExceedData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		basePage.scrollToStartPage();
		assertTrue(authPolicyPage.isPolicyDataExceedChractersMessageDisplayed(),
				GlobalConstants.isPolicyDataExceedChractersMessageDisplayed);
		authPolicyPage.clickOnErrorCloseButton();
		authPolicyPage.uploadPolicyData();
		authPolicyPage.clearTextBoxPolicyData();
		basePage.scrollToEndPage();
		assertTrue(authPolicyPage.isSaveAsDraftButtonEnabled(), GlobalConstants.isSaveAsDraftButtonEnabled);
		authPolicyPage.uploadPolicyData();
		authPolicyPage.clickOnSaveAsDraftButton();
		assertTrue(authPolicyPage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);
		authPolicyPage.clickOnErrorCloseButton();

		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.ALPHANUMERIC2);
		authPolicyPage.enterpolicyDescription(GlobalConstants.ALPHANUMERIC2);
		authPolicyPage.clickOnPolicyClearButton();

		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.ALPHANUMERIC2);
		authPolicyPage.enterpolicyDescription(GlobalConstants.ALPHANUMERIC2);
		authPolicyPage.uploadPolicyData();
		authPolicyPage.clickOnPolicyCancelButton();
		authPolicyPage.clickOnProceedButton();
		assertTrue(authPolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.SPACE);
		authPolicyPage.enterpolicyDescription(GlobalConstants.SPACE);
		authPolicyPage.uploadPolicyData();
		assertFalse(authPolicyPage.isSaveAsDraftButtonEnabled(), GlobalConstants.isSaveAsDraftButtonEnabled);
		authPolicyPage.clickOnHomeButton();
	}

	@Test(priority = 3, description = "Auth Policy Tabular View", dependsOnMethods = "uploadInvalidAuthPolicyData")
	public void authPolicyTabularView() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();

		assertTrue(authPolicyPage.isPolicyIdHeaderTextDisplayed(), GlobalConstants.isPolicyIdHeaderTextDisplayed);
		assertTrue(authPolicyPage.isPolicyNameHeaderTextDisplayed(), GlobalConstants.isPolicyNameHeaderTextDisplayed);
		assertTrue(authPolicyPage.isPolicyDescriptionHeaderTextDisplayed(),
				GlobalConstants.isPolicyDescriptionHeaderTextDisplayed);
		assertTrue(authPolicyPage.isPolicyGroupHeaderTextDisplayed(), GlobalConstants.isPolicyGroupHeaderTextDisplayed);
		assertTrue(authPolicyPage.isCreatedDateHeaderTextDisplayed(), GlobalConstants.isCreatedDateHeaderTextDisplayed);
		assertTrue(authPolicyPage.isStatusHeaderTextDisplayed(), GlobalConstants.isStatusHeaderTextDisplayed);

		assertFalse(authPolicyPage.isFilterResetButtonAvailableOrEnabled(),
				GlobalConstants.isFilterResetButtonAvailableOrEnabled);
		authPolicyPage.clickOnFilterButton();
		assertFalse(authPolicyPage.isFilterButtonDisplayedOrEnabled(),
				GlobalConstants.isFilterButtonDisplayedOrEnabled);
		assertTrue(authPolicyPage.isPolicyIdFilterLabelDisplayed(), GlobalConstants.isPolicyIdFilterLabelDisplayed);
		assertTrue(authPolicyPage.isPolicyNameFilterLabelDisplayed(), GlobalConstants.isPolicyNameFilterLabelDisplayed);
		assertTrue(authPolicyPage.isPolicyDescriptionFilterLabelDisplayed(),
				GlobalConstants.isPolicyDescriptionFilterLabelDisplayed);
		assertTrue(authPolicyPage.isPolicyGroupFilterLabelDisplayed(),
				GlobalConstants.isPolicyGroupFilterLabelDisplayed);
		assertTrue(authPolicyPage.isStatusFilterLabelDisplayed(), GlobalConstants.isStatusFilterLabelDisplayed);

		assertTrue(authPolicyPage.isPolicyIdFilterPlaceHolderDisplayed(),
				GlobalConstants.isPolicyIdFilterPlaceHolderDisplayed);
		assertTrue(authPolicyPage.isPolicyNameFilterPlaceHolderDisplayed(),
				GlobalConstants.isPolicyNameFilterPlaceHolderDisplayed);
		assertTrue(authPolicyPage.isPolicyGroupDescriptionFilterPlaceHolderDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionFilterPlaceHolderDisplayed);
		assertTrue(authPolicyPage.isPolicyGroupFilterPlaceHolderDisplayed(),
				GlobalConstants.isPolicyGroupFilterPlaceHolderDisplayed);
		assertTrue(authPolicyPage.isStatusFilterPlaceHolderDisplayed(),
				GlobalConstants.isStatusFilterPlaceHolderDisplayed);

		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.selectActivateStatusFilter();
		authPolicyPage.clickOnApplyFilterButton();
		assertTrue(authPolicyPage.isPolicyStatusActivateDisplayed(), GlobalConstants.isPolicyStatusActivateDisplayed);
		authPolicyPage.clickOnActivatedAuthPolicy();
		assertTrue(authPolicyPage.isPolicyViewPageTitleDisplayed(), GlobalConstants.isPolicyViewPageTitleDisplayed);
		authPolicyPage.clickOnViewBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.selectActivateStatusFilter();
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		assertTrue(authPolicyPage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		assertTrue(authPolicyPage.isCloneButtonDisplayed(), GlobalConstants.isCloneButtonDisplayed);
		assertTrue(authPolicyPage.isDeactivateButtonDisplayed(), GlobalConstants.isDeactivateButtonDisplayed);

		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.selectDeactivateStatusFilter();
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.DEACTIVATE_AUTH2);
		authPolicyPage.clickOnApplyFilterButton();
		assertTrue(authPolicyPage.isPolicyStatusDeactivatedDisplayed(),
				GlobalConstants.isPolicyStatusDeactivateDisplayed);
		authPolicyPage.clickOnDeactivatedAuthPolicy();
		assertFalse(authPolicyPage.isPolicyViewPageTitleDisplayed(), GlobalConstants.isPolicyViewPageTitleDisplayed);
		authPolicyPage.clickOnActionButton();
		assertTrue(authPolicyPage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		assertTrue(authPolicyPage.isCloneButtonDisplayed(), GlobalConstants.isCloneButtonDisplayed);
		authPolicyPage.clickOnPolicyGroupCloseButton();
		authPolicyPage.clickOnPolicyNameCloseButton();
		authPolicyPage.clickOnSelectStatusButton();
		assertFalse(authPolicyPage.isApplyFilterButtonEnabled(), GlobalConstants.isApplyFilterButtonEnabled);

		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.INVALID_DATA);
		authPolicyPage.clickOnApplyFilterButton();
		assertTrue(authPolicyPage.isNoResultsFoundMessageDisplayed(), GlobalConstants.isNoResultsFoundMessageDisplayed);
		authPolicyPage.clickOnPolicyGroupCloseButton();

		authPolicyPage.selectActivateStatusFilter();
		authPolicyPage.clickOnApplyFilterButton();
		basePage.scrollToEndPage();
		assertTrue(authPolicyPage.isPaginationDisplayed(), GlobalConstants.isPaginationDisplayed);
		assertTrue(authPolicyPage.isPreviusPageButtonDisplayed(), GlobalConstants.isPreviusPageButtonDisplayed);
		assertTrue(authPolicyPage.isNextPageButtonDisplayed(), GlobalConstants.isNextPageButtonDisplayed);
		assertTrue(authPolicyPage.isPage1Displayed(), GlobalConstants.isPage1Displayed);
		authPolicyPage.clickOnNextPageButton();
		assertTrue(authPolicyPage.isPage2Displayed(), GlobalConstants.isPage2Displayed);
		authPolicyPage.clickOnPreviusPageButton();
		assertTrue(authPolicyPage.isPage1Displayed(), GlobalConstants.isPage1Displayed);

		assertTrue(authPolicyPage.isPrefixOfPageDisplayed(), GlobalConstants.isPrefixOfPageDisplayed);
		assertTrue(authPolicyPage.isRecordPerPageDisplayed(), GlobalConstants.isRecordPerPageDisplayed);
		assertTrue(authPolicyPage.isItemPerPage8Displayed(), GlobalConstants.isItemPerPage8Displayed);
		assertTrue(authPolicyPage.isexpandIconDisplayed(), GlobalConstants.isexpandIconDisplayed);
		authPolicyPage.selectItemPerPageNumber();
		assertTrue(authPolicyPage.isItemPerPage16Displayed(), GlobalConstants.isItemPerPage16Displayed);
	}

	@Test(priority = 4, description = "Auth Policy Details View", dependsOnMethods = "authPolicyTabularView")
	public void authPolicyDetailsView() {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY03);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		assertTrue(authPolicyPage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		authPolicyPage.clickOnViewButton();
		assertTrue(authPolicyPage.isPolicyViewPageTitleDisplayed(), GlobalConstants.isPolicyViewPageTitleDisplayed);
		assertTrue(authPolicyPage.isSubTitleDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(authPolicyPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertTrue(authPolicyPage.isPolicyIdLabelDisplayed(), GlobalConstants.isPolicyIdLabelDisplayed);
		assertTrue(authPolicyPage.isPolicyIdContextDisplayed(), GlobalConstants.isPolicyIdContextDisplayed);
		assertTrue(authPolicyPage.isPolicyStatusActivatedDisplayed(), GlobalConstants.isPolicyStatusActivatedDisplayed);
		assertTrue(authPolicyPage.isPolicyNameLabelDisplayed(), GlobalConstants.isPolicyNameLabelDisplayed);
		assertTrue(authPolicyPage.isPolicyNameContextDisplayed(), GlobalConstants.isPolicyNameContextDisplayed);
		assertTrue(authPolicyPage.isPolicyGroupLabelDisplayed(), GlobalConstants.isPolicyGroupLabelDisplayed);
		assertTrue(authPolicyPage.isPolicyGroupContextDisplayed(), GlobalConstants.isPolicyGroupContextDisplayed);
		assertTrue(authPolicyPage.isPolicyDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyDescriptionLabelDisplayed);
		assertTrue(authPolicyPage.isPolicyDescriptionContextDisplayed(),
				GlobalConstants.isPolicyDescriptionContextDisplayed);
		assertTrue(authPolicyPage.isPolicyGroupDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionLabelDisplayed);
		assertTrue(authPolicyPage.isPolicyGroupDescriptionContextDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionContextDisplayed);
		assertTrue(authPolicyPage.isPolicyDataLabelDisplayed(), GlobalConstants.isPolicyDataLabelDisplayed);
		assertTrue(authPolicyPage.isPolicyDataTitleDisplayed(), GlobalConstants.isPolicyDataTitleDisplayed);
		assertTrue(authPolicyPage.isPolicyDataPreviewDisplayed(), GlobalConstants.isPolicyDataPreviewDisplayed);

		authPolicyPage.clickOnPolicyDataPreviewButton();
		assertTrue(authPolicyPage.isPolicyDataPopupDisplayed(), GlobalConstants.ispolicyDataPopupDisplayed);
		assertTrue(authPolicyPage.isDownloadButtonDisplayed(), GlobalConstants.isDownloadButtonDisplayed);
		assertTrue(authPolicyPage.isCloseButtonDisplayed(), GlobalConstants.isCloseButtonDisplayed);
		authPolicyPage.clickOnDownloadButton();
		authPolicyPage.clickOnPreviewCloseButton();
		authPolicyPage.clickOnSubTitleButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.DEACTIVATE_AUTH2);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		assertTrue(authPolicyPage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		authPolicyPage.clickOnViewButton();
		assertTrue(authPolicyPage.isPolicyViewPageTitleDisplayed(), GlobalConstants.isPolicyViewPageTitleDisplayed);
		assertTrue(authPolicyPage.isPolicyStatusDeactivatedDisplayed(),
				GlobalConstants.isPolicyStatusDeactivatedDisplayed);
		authPolicyPage.clickOnHomeButton();
	}

	@Test(priority = 5, description = "Edit Auth Policy", dependsOnMethods = "authPolicyDetailsView")
	public void editAuthPolicy() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();

		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.EDITAUTHPOLICY);
		authPolicyPage.enterpolicyDescription(GlobalConstants.EDITAUTHPOLICY_DESCRIPTION);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.EDITAUTHPOLICY);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		assertTrue(authPolicyPage.isEditButtonEnable(), GlobalConstants.isEditButtonEnable);
		authPolicyPage.clickOnEditButton();

		assertTrue(authPolicyPage.isEditPolicyPageTitleDisplayed(), GlobalConstants.isEditPolicyPageTitleDisplayed);
		assertTrue(authPolicyPage.isHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(authPolicyPage.isSubTitleDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(authPolicyPage.isPolicyFormSubTitleDisplayed(), GlobalConstants.isPolicyFormSubTitleDisplayed);
		assertTrue(authPolicyPage.isPolicyGroupDropdownEnabled(), GlobalConstants.isPolicyGroupDropdownEnabled);
		assertTrue(authPolicyPage.isEditPolicyGroupDropdownValueDisplayed(),
				GlobalConstants.isEditPolicyGroupDropdownValueDisplayed);
		assertTrue(authPolicyPage.isPolicyNameBoxDisplayed(), GlobalConstants.isPolicyNameBoxDisplayed);
		assertTrue(authPolicyPage.isEditPolicyNameValueDisplayed(), GlobalConstants.isEditPolicyNameValueDisplayed);
		assertTrue(authPolicyPage.isPolicyDescriptionBoxDisplayed(), GlobalConstants.isPolicyDescriptionBoxDisplayed);
		assertTrue(authPolicyPage.isEditPolicyDescriptionValueDisplayed(),
				GlobalConstants.isEditPolicyDescriptionValueDisplayed);
		assertTrue(authPolicyPage.isReUploadPolicyDataLabelDisplayed(),
				GlobalConstants.isReUploadPolicyDataLabelDisplayed);
		assertTrue(authPolicyPage.isReuploadButtonDisplayed(), GlobalConstants.isReuploadButtonDisplayed);
		assertTrue(authPolicyPage.isEditPolicyDataContextDisplayed(), GlobalConstants.isEditPolicyDataContextDisplayed);
		assertTrue(authPolicyPage.isEditPolicyClearButtonDisplayed(), GlobalConstants.isEditPolicyClearButtonDisplayed);
		assertTrue(authPolicyPage.isEditPolicySubmitButtonDisplayed(),
				GlobalConstants.isEditPolicySubmitButtonDisplayed);
		assertTrue(authPolicyPage.isEditPolicyCancelButtonDisplayed(),
				GlobalConstants.isEditPolicyCancelButtonDisplayed);

		authPolicyPage.enterPolicyName(GlobalConstants.SPACE);
		assertFalse(authPolicyPage.isSaveAsDraftButtonEnabled(), GlobalConstants.isSaveAsDraftButtonEnabled);

		authPolicyPage.enterPolicyName(GlobalConstants.SINGLE_CHARACTERS);
		authPolicyPage.enterpolicyDescription(GlobalConstants.SINGLE_CHARACTERS);
		basePage.scrollToEndPage();
		authPolicyPage.clickOnEditPolicyFormSubmitButton();
		assertTrue(authPolicyPage.isEditPolicySuccessTitleDisplayed(),
				GlobalConstants.isEditPolicySuccessTitleDisplayed);
		assertTrue(authPolicyPage.isEditPolicySuccessSubTitleDisplayed(),
				GlobalConstants.isEditPolicySuccessSubTitleDisplayed);
		assertTrue(authPolicyPage.isEditSuccessGoBackButtonEnabled(), GlobalConstants.isEditSuccessGoBackButtonEnabled);
		assertTrue(authPolicyPage.isEditSuccessHomeButtonEnabled(), GlobalConstants.isEditSuccessHomeButtonEnabled);
		authPolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.SINGLE_CHARACTERS);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnEditButton();
		authPolicyPage.enterPolicyName(GlobalConstants.AUTOMATION);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTOMATION);
		basePage.scrollToEndPage();
		authPolicyPage.clickOnEditPolicyFormSubmitButton();
		authPolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTOMATION);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnEditButton();
		authPolicyPage.enterPolicyName(GlobalConstants.NUMERIC2);
		authPolicyPage.enterpolicyDescription(GlobalConstants.NUMERIC2);
		authPolicyPage.uploadBlankData();
		assertTrue(authPolicyPage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		authPolicyPage.clickOnErrorCloseButton();
		authPolicyPage.uploadInvalidPolicyData();
		assertTrue(authPolicyPage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		authPolicyPage.clickOnErrorCloseButton();
		authPolicyPage.uploadExceedData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnEditPolicyFormSubmitButton();
		basePage.scrollToStartPage();
		assertTrue(authPolicyPage.isInvalidInfoInPolicyDataErrorDisplayed(),
				GlobalConstants.isInvalidInfoInPolicyDataErrorDisplayed);
		authPolicyPage.clickOnErrorCloseButton();
		authPolicyPage.uploadPolicyData();
		authPolicyPage.clearTextBoxPolicyData();
		basePage.scrollToEndPage();
		assertTrue(authPolicyPage.isEditPolicySubmitButtonEnabled(), GlobalConstants.isSaveAsDraftButtonEnabled);
		authPolicyPage.clickOnEditPolicyFormSubmitButton();
		authPolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.NUMERIC2);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnEditButton();
		authPolicyPage.clickOnEditPolicyFormCancelButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.NUMERIC2);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnEditButton();
		authPolicyPage.enterPolicyName(GlobalConstants.EDITAUTHPOLICY);
		authPolicyPage.enterpolicyDescription(GlobalConstants.EDITAUTHPOLICY);
		authPolicyPage.clickOnEditPolicyFormCancelButton();
		assertTrue(authPolicyPage.isChangesLostConfirmationMessageDisplayed(),
				GlobalConstants.isChangesLostConfirmationMessageDisplayed);
		authPolicyPage.clickOnChangesLostCancelButton();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnUndoChangesButton();

		basePage.scrollToStartPage();
		authPolicyPage.enterPolicyName(GlobalConstants.EDITAUTHPOLICY);
		authPolicyPage.enterpolicyDescription(GlobalConstants.EDITAUTHPOLICY);
		authPolicyPage.uploadAlphabetData();
		assertTrue(authPolicyPage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		authPolicyPage.clickOnErrorCloseButton();
		authPolicyPage.uploadSpecialChData();
		assertTrue(authPolicyPage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		authPolicyPage.clickOnErrorCloseButton();
		authPolicyPage.uploadPolicyData();
		authPolicyPage.clickOnEditPolicyFormSubmitButton();
		authPolicyPage.clickOnGoBackButton();
		basePage.navigateBack();

	}

	@Test(priority = 6, description = "Publish Auth Policy", dependsOnMethods = "editAuthPolicy")
	public void publishAuthPolicy() {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		policygroupPage = new PolicyGroupPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();

		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY08);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY08_DESCRIPTION);
		authPolicyPage.uploadPolicyData();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY08);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		assertTrue(authPolicyPage.isPolicyPublishButtonDisplayed(), GlobalConstants.isPolicyPublishButtonDisplayed);
		authPolicyPage.clickOnPolicyPublishButton();
		assertTrue(authPolicyPage.isPublishPopupDisplayed(), GlobalConstants.isPublishPopupDisplayed);
		assertTrue(authPolicyPage.isPublishPopupInfoTextDisplayed(), GlobalConstants.isPublishPopupInfoTextDisplayed);
		assertTrue(authPolicyPage.isPublishPolicyButtonDisplayed(), GlobalConstants.isPublishPolicyButtonDisplayed);
		assertTrue(authPolicyPage.isPublishCancelButtonDisplayed(),
				GlobalConstants.isPublishPolicyCancelButtonDisplayed);
		authPolicyPage.clickOnPublishCancelButton();
		assertTrue(authPolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY08);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		assertTrue(authPolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.ALPHANUMERIC2);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		assertTrue(authPolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY08);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnCloneButton();
		authPolicyPage.selectPolicyGroupDropdownForClone(GlobalConstants.NUMERIC);
		authPolicyPage.clickOnClonePolicyButton();
		authPolicyPage.clickOnClonePolicyCloseButton();
		policygroupPage.navigateBackDefaultButton();

	}

	@Test(priority = 7, description = "Clone Auth Policy", dependsOnMethods = "publishAuthPolicy")
	public void cloneAuthPolicy() {

		dashboardPage = new DashboardPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		policiesPage = new PoliciesPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY06_DESCRIPTION);
		authPolicyPage.uploadPolicyData();
		authPolicyPage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnCloneButton();
		assertFalse(authPolicyPage.isClonePolicyPopupTitleDisplayed(),
				GlobalConstants.isClonePolicyPopupTitleDisplayed);

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.selectDeactivateStatusFilter();
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.DEACTIVATE_AUTH2);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		assertTrue(authPolicyPage.isCloneButtonEnabled(), GlobalConstants.isCloneButtonEnabled);

		authPolicyPage.clickOnFilterResetButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		assertTrue(authPolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		authPolicyPage.clickOnFilterResetButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		assertTrue(authPolicyPage.isCloneButtonEnabled(), GlobalConstants.isCloneButtonEnabled);
		authPolicyPage.clickOnCloneButton();
		assertTrue(authPolicyPage.isClonePolicyPopupTitleDisplayed(), GlobalConstants.isClonePolicyPopupTitleDisplayed);
		assertTrue(authPolicyPage.isClonePolicyInfoMessageDisplayed(),
				GlobalConstants.isClonePolicyInfoMessageDisplayed);
		assertTrue(authPolicyPage.isClonePolicyGroupDropdownDisplayed(),
				GlobalConstants.isClonePolicyGroupDropdownDisplayed);
		authPolicyPage.clickOnClonePolicyGroupDropdown();
		assertTrue(authPolicyPage.isClonePolicyGroupSearchInputDisplayed(),
				GlobalConstants.isClonePolicyGroupSearchInputDisplayed);
		assertTrue(authPolicyPage.isClonePolicyCancelButtonAvailable(),
				GlobalConstants.isClonePolicyCancelButtonAvailable);
		assertTrue(authPolicyPage.isClonePolicyButtonAvailable(), GlobalConstants.isClonePolicyButtonAvailable);
		assertFalse(authPolicyPage.isClonePolicyButtonEnabled(), GlobalConstants.isClonePolicyButtonEnabled);

		authPolicyPage.searchPolicyGroupForClone(GlobalConstants.CHARACTERS_1);
		assertTrue(authPolicyPage.isClonePolicyGroupNameDisplayed(), GlobalConstants.isClonePolicyGroupNameDisplayed);
		assertTrue(authPolicyPage.isClonePolicyGroupDescriptionDisplayed(),
				GlobalConstants.isClonePolicyGroupDescriptionDisplayed);
		authPolicyPage.clearClonePolicyGroupDropdownValue();

		authPolicyPage.searchPolicyGroupForClone(GlobalConstants.DEACTIVATE_DATA1);
		assertTrue(authPolicyPage.isNoPolicyGroupFoundDisplayed(), GlobalConstants.isNoDataAvailableDisplayed);
		authPolicyPage.clearClonePolicyGroupDropdownValue();

		authPolicyPage.selectPolicyGroupForClonePolicy(GlobalConstants.CHARACTERS_1);
		assertTrue(authPolicyPage.isClonePolicyButtonEnabled(), GlobalConstants.isClonePolicyButtonEnabled);
		authPolicyPage.clickOnClonePolicyButton();
		assertTrue(authPolicyPage.isClonedSuccessMessageDisplayed(), GlobalConstants.isClonedSuccessPopupDisplayed);
		assertFalse(authPolicyPage.isClonePolicyCancelButtonEnabled(),
				GlobalConstants.isClonePolicyCancelButtonEnabled);
		assertTrue(authPolicyPage.isClonePolicyCloseButtonEnabled(), GlobalConstants.isClonePolicyCloseButtonEnabled);
		authPolicyPage.clickOnClonePolicyCloseButton();
		assertTrue(authPolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.clickOnApplyFilterButton();
		assertTrue(authPolicyPage.isUpdatedPolicyGroupDisplayed(), GlobalConstants.isUpdatedPolicyGroupDisplayed);
		assertTrue(authPolicyPage.isClonedPolicyStatusDraftDisplayed(),
				GlobalConstants.isClonedPolicyStatusDraftDisplayed);
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnViewButton();
		assertTrue(authPolicyPage.isViewPolicyDetailsStatusDraftDisplayed(),
				GlobalConstants.isViewPolicyDetailsStatusDraftDisplayed);
		authPolicyPage.clickOnViewBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnCloneButton();
		authPolicyPage.clickOnClonePolicyCancelButton();

		authPolicyPage.clickOnFilterResetButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnCloneButton();
		authPolicyPage.selectValidPolicyGroupForClone(GlobalConstants.CHARACTERS_1);
		authPolicyPage.clickOnClonePolicyButton();
		assertTrue(authPolicyPage.isAlreadyExistErrorMessageDisplayed(),
				GlobalConstants.isAlreadyExistErrorMessageDisplayed);
		authPolicyPage.clickOnCloseIcon();
		assertTrue(authPolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnCloneButton();
		authPolicyPage.selectValidPolicyGroupForClone(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.clickOnClonePolicyButton();
		assertTrue(authPolicyPage.isAlreadyExistErrorMessageDisplayed(),
				GlobalConstants.isAlreadyExistErrorMessageDisplayed);
		authPolicyPage.clickOnCloseIcon();
		assertTrue(authPolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnCloneButton();
		authPolicyPage.selectPolicyGroupForClone(GlobalConstants.POLICYGROUP07);
		authPolicyPage.clickOnClonePolicyButton();
		authPolicyPage.clickOnClonePolicyCloseButton();

	}

	@Test(priority = 8, description = "Deactivate Auth Policy", dependsOnMethods = "cloneAuthPolicy")
	public void deactivateAuthPolicy() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		policiesPage = new PoliciesPage(driver);
		loginPage = new LoginPage(driver);
		apiKeyPage = new ApiKeyPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);
		oidcClientPage = new OidcClientPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.DEACTIVATE_AUTH2);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		assertTrue(authPolicyPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		assertTrue(authPolicyPage.isCloneButtonEnabled(), GlobalConstants.isCloneButtonEnabled);
		authPolicyPage.clickOnDeactivateButton();
		assertFalse(authPolicyPage.isDeactivatePolicyPopupDisplayed(),
				GlobalConstants.isDeactivatePolicyPopupDisplayed);
		authPolicyPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		assertTrue(authPolicyPage.isDeactivateButtonEnabled(), GlobalConstants.isDeactivateButtonEnabled);
		authPolicyPage.clickOnDeactivateButton();
		assertTrue(authPolicyPage.isDeactivatePolicyPopupDisplayed(), GlobalConstants.isDeactivatePolicyPopupDisplayed);
		assertTrue(authPolicyPage.isDeactivatePolicyPopupTitleDisplayed(),
				GlobalConstants.isDeactivatePolicyPopupTitleDisplayed);
		assertTrue(authPolicyPage.isDeactivatePolicyInfoMessageDisplayed(),
				GlobalConstants.isDeactivatePolicyInfoMessageDisplayed);
		assertTrue(authPolicyPage.isDeactivateConfirmButtonAvailable(),
				GlobalConstants.isDeactivateConfirmButtonAvailable);
		assertTrue(authPolicyPage.isDeactivateCancelButtonAvailable(),
				GlobalConstants.isDeactivateCancelButtonAvailable);
		authPolicyPage.clickOnDeactivateCancelButton();
		assertTrue(authPolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		loginAsAuthPartner();

		dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnCreateOidcClientButton();
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.enterDeactivePolicyNameInDropdown(GlobalConstants.DEACTIVATE_AUTH2);
		assertTrue(oidcClientPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		policiesPage.clickOnHomeButton();
		policiesPage.clickOnDataLostProcceedButton();

		dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnApiKeyTab();
		apiKeyPage.clickOnAPIKeyDisplayed();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.enterDeactivePolicyNameInDropdown(GlobalConstants.DEACTIVATE_AUTH2);
		assertTrue(apiKeyPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		policiesPage.clickOnHomeButton();
		policiesPage.clickOnDataLostProcceedButton();

		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnRequestPolicyButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectInvalidPolicyNameDropdown(GlobalConstants.DEACTIVATE_AUTH2);
		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		policiesPage.clickOnPolicyNameDropdown();
		policiesPage.selectPolicyNameDropdown(GlobalConstants.ALPHANUMERIC2);
		policiesPage.enterComments(GlobalConstants.ALPHANUMERIC2);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(GlobalConstants.AUTHPOLICY09);
		policiesPage.enterComments(GlobalConstants.AUTHPOLICY09);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(GlobalConstants.AUTHPOLICY06);
		policiesPage.enterComments(GlobalConstants.AUTHPOLICY06);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

		loginAsPartnerAdmin();

		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.ALPHANUMERIC2);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		partnerPolicyMappingPage.clickOnApproveSubmitButton();
		partnerPolicyMappingPage.clickOnFilterResetButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		partnerPolicyMappingPage.clickOnRejectButton();
		partnerPolicyMappingPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.ALPHANUMERIC2);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnDeactivateButton();
		authPolicyPage.clickOnDeactivateConfirmButton();
		assertTrue(authPolicyPage.isPartnerPolicyLinkActivatedErrorDisplayed(),
				GlobalConstants.isPartnerPolicyLinkActivatedErrorDisplayed);
		authPolicyPage.clickOnAlertErrorOkButton();
		partnerPolicyMappingPage.clickOnFilterResetButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY09);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnDeactivateButton();
		authPolicyPage.clickOnDeactivateConfirmButton();
		assertTrue(authPolicyPage.isPartnerPolicyLinkPendingErrorDisplayed(),
				GlobalConstants.isPartnerPolicyLinkPendingErrorDisplayed);
		authPolicyPage.clickOnAlertErrorOkButton();
		partnerPolicyMappingPage.clickOnFilterResetButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnDeactivateButton();
		authPolicyPage.clickOnDeactivateConfirmButton();
		basePage.navigateBack();
		basePage.navigateForward();

		authPolicyPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.clickOnApplyFilterButton();
		assertTrue(authPolicyPage.isPolicyStatusDeactivatedDisplayed(),
				GlobalConstants.isPolicyStatusDeactivatedDisplayed);

		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY06_DESCRIPTION);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		assertTrue(authPolicyPage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);
	}

	@Test(priority = 9, description = "Deactivate Policy Group", dependsOnMethods = "deactivateAuthPolicy")
	public void deactivatePolicyGroup() {
		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnFilterButton();
		policygroupPage.clickOnPolicyGroupNameFilter(GlobalConstants.NUMERIC);
		policygroupPage.clickOnApplyFilterButton();
		policygroupPage.clickOnPolicyGroupActionButton();
		assertTrue(policygroupPage.isPolicyGroupViewButtonDisplayed(),
				GlobalConstants.isPolicyGroupViewButtonDisplayed);
		assertTrue(policygroupPage.isDeactivateButtonDisplayed(),
				GlobalConstants.isPolicyGroupDeactivateButtonDisplayed);
		policygroupPage.clickOnDeactivateButton();
		assertTrue(policygroupPage.isDeactivatePolicyGroupPopupDisplayed(),
				GlobalConstants.isDeactivatePolicyGroupPopupDisplayed);
		assertTrue(policygroupPage.isDeactivatePopupTitleDisplayed(), GlobalConstants.isDeactivatePopupTitleDisplayed);
		assertTrue(policygroupPage.isDeactivatePopupSubtitleDisplayed(),
				GlobalConstants.isDeactivatePopupSubtitleDisplayed);
		assertTrue(policygroupPage.isDeactivateConfirmButtonAvailable(),
				GlobalConstants.isDeactivateConfirmButtonAvailable);
		assertTrue(policygroupPage.isDeactivateCancelButtonAvailable(),
				GlobalConstants.isDeactivateCancelButtonAvailable);
		policygroupPage.clickOnDeactivateCancelBtn();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);

		policygroupPage.clickOnFilterButton();
		policygroupPage.clickOnPolicyGroupNameFilter(GlobalConstants.DEACTIVATE_POLICYGROUP);
		policygroupPage.clickOnApplyFilterButton();
		policygroupPage.clickOnPolicyGroupActionButton();
		assertTrue(policygroupPage.isPolicyGroupViewButtonDisplayed(),
				GlobalConstants.isPolicyGroupViewButtonDisplayed);
		policygroupPage.clickOnDeactivateButton();
		assertFalse(policygroupPage.isDeactivatePolicyGroupPopupDisplayed(),
				GlobalConstants.isDeactivatePolicyGroupPopupDisplayed);

		policygroupPage.clickOnPolicyGroupNameFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		policygroupPage.clickOnApplyFilterButton();
		policygroupPage.clickOnPolicyGroupActionButton();
		policygroupPage.clickOnDeactivateButton();
		policygroupPage.clickOnDeactivateConfirmButton();
		assertTrue(policygroupPage.isDeactivateErrorPopupDisplayed(), GlobalConstants.isDeactivateErrorPopupDisplayed);
		assertTrue(policygroupPage.isDeactivateErrorPopupTitleDisplayed(),
				GlobalConstants.isDeactivateErrorPopupTitleDisplayed);
		assertTrue(policygroupPage.isErrorPopupDescriptionDisplayed(),
				GlobalConstants.isErrorPopupDescriptionDisplayed);
		assertTrue(policygroupPage.isErrorPopupOkayBtnDisplayed(), GlobalConstants.isErrorPopupOkayBtnDisplayed);
		policygroupPage.clickOnErrorPopupOkayBtn();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);

		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.CHARACTERS_1);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY05);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY05_DESCRIPTION);
		authPolicyPage.uploadPolicyData();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY05);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnDeactivateButton();
		authPolicyPage.clickOnDeactivateConfirmButton();

		policiesPage.clickOnPoliciesPolicyGroupTab();
		policygroupPage.clickOnFilterButton();
		policygroupPage.clickOnPolicyGroupNameFilter(GlobalConstants.CHARACTERS_1);
		policygroupPage.clickOnApplyFilterButton();
		policygroupPage.clickOnPolicyGroupActionButton();
		policygroupPage.clickOnDeactivateButton();
		policygroupPage.clickOnDeactivateConfirmButton();

	}

	private void loginAsAuthPartner() {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.POLICIES_USER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();
	}

	private void loginAsPartnerAdmin() {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.POLICIES_ADMIN);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

	}

}
