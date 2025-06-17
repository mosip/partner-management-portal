package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import io.mosip.testrig.pmpuiv2.pages.ApiKeyPage;
import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.DatasharePolicyPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.OidcClientPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.pages.PolicyGroupPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class PartnerManagerPoliciesTest extends BaseClass {
	private BasePage basePage;

	@Test(priority = 1, description = "Create Policy Group")
	public void createPolicyGroup() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);

		dashboardPage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);

		policygroupPage.clickOnCreatePolicyGroupButton();
		assertTrue(policygroupPage.isCreatePolicyGroupTitleDisplayed(),
				GlobalConstants.isCreatePolicyGroupTitleDisplayed);
		assertTrue(policygroupPage.isSubTitleDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(policygroupPage.isSubTitleHomeDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(policygroupPage.isPolicyGroupNameTextboxDisplayed(),
				GlobalConstants.isPolicyGroupNameTextboxDisplayed);
		assertTrue(policygroupPage.isPolicyGroupNameDescriptionTextboxDisplayed(),
				GlobalConstants.isPolicyGroupNameDescriptionTextboxDisplayed);
		assertTrue(policygroupPage.isSubmitButtonAvailable(), GlobalConstants.isSubmitButtonAvailable);
		assertFalse(policygroupPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
		policygroupPage.clickOnClearFormButton();
		policygroupPage.clickOnCancelButton();

		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.enterPolicyGroupName(GlobalConstants.ALPHANUMERIC);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.ALPHANUMERIC);
		policygroupPage.clickOnSubmitButton();
		assertTrue(policygroupPage.isPolicyGroupSuccessMessageDisplayed(),
				GlobalConstants.isPolicyGroupSuccessMessageDisplayed);
		assertTrue(policygroupPage.isTitleOfSuccessMessageDisplayed(),
				GlobalConstants.isTitleOfSuccessMessageDisplayed);
		assertTrue(policygroupPage.isSuccessHomeButtonAvailable(), GlobalConstants.isSuccessHomeButtonAvailable);
		assertTrue(policygroupPage.isSuccessGoBackButtonAvailable(), GlobalConstants.isSuccessGoBackButtonAvailable);
		policygroupPage.clickOnSuccessHomeButton();

		dashboardPage.clickOnPolicyButton();
		createPolicyGroup(policygroupPage, GlobalConstants.NUMERIC, GlobalConstants.NUMERIC);

		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.enterPolicyGroupName(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(policygroupPage.isSpecialCharactersAreNotAllowedErrorMessageDisplayed(),
				GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(policygroupPage.isSpecialCharactersAreNotAllowedErrorMessageDisplayed(),
				GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
		policygroupPage.clickOnClearFormButton();
		policygroupPage.clickOnTitleBackIconButton();

		createPolicyGroup(policygroupPage, GlobalConstants.CHARACTERS_1, GlobalConstants.CHARACTERS_1);
		createPolicyGroup(policygroupPage, GlobalConstants.POLICYGROUP07, GlobalConstants.POLICYGROUP07);

		createPolicyGroup(policygroupPage, GlobalConstants.DEACTIVATE_POLICYGROUP,
				GlobalConstants.DEACTIVATE_POLICYGROUP);

		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.enterPolicyGroupName(GlobalConstants.SPACE);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.SPACE);
		assertTrue(policygroupPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
		policygroupPage.clickOnSubmitButton();
		policygroupPage.clickOnSomethingWentWrongHomeBtn();

		dashboardPage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);

		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.enterPolicyGroupName(GlobalConstants.CHARACTERS_1);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.CHARACTERS_1);
		policygroupPage.clickOnSubmitButton();
		assertTrue(policygroupPage.isSameNamePolicyGroupAlreadyExistMessageDisplayed(),
				GlobalConstants.isSameNamePolicyGroupAlreadyExistMessageDisplayed);
		policygroupPage.clickOnErrorCloseButton();
		policygroupPage.enterPolicyGroupName(GlobalConstants.AUTOMATION_LOWERCASE);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.AUTOMATION_LOWERUPPERCASE);
		policygroupPage.clickOnSubmitButton();
		policygroupPage.clickOnSuccessGoBackButton();

		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.navigateBackDefaultButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);
		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.enterPolicyGroupName(GlobalConstants.AUTOMATION_LOWERUPPERCASE);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.AUTOMATION_LOWERUPPERCASE);
		policygroupPage.navigateBackDefaultButton();
		assertTrue(policygroupPage.isBrowserBackConfirmationPopupDisplayed(),
				GlobalConstants.isBrowserBackConfirmationPopupDisplayed);
		assertTrue(policygroupPage.isBrowserBackProceedButtonAvailable(),
				GlobalConstants.isBrowserBackProceedButtonAvailable);
		assertTrue(policygroupPage.isBrowserBackCancelButtonAvailable(),
				GlobalConstants.isBrowserBackCancelButtonAvailable);
		policygroupPage.clickOnBrowserBackCancelButton();
		policygroupPage.navigateBackDefaultButton();
		policygroupPage.clickOnBrowserBackProceedButton();
	}

	@Test(priority = 2, description = "Policygroup Tabular View")
	public void policyGroupTabularView() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);

		dashboardPage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);
		assertTrue(policygroupPage.isAuthPolicyTabDisplayed(), GlobalConstants.isAuthPolicyTabDisplayed);
		assertTrue(policygroupPage.isDatasharePolicyTabDisplayed(), GlobalConstants.isDatasharePolicyTabDisplayed);
		assertTrue(policygroupPage.isTitleOfPageDisplayed(), GlobalConstants.isTitleOfPageDisplayed);
		assertTrue(policygroupPage.isBackiconDisplayed(), GlobalConstants.isBackiconDisplayed);
		assertTrue(policygroupPage.isSubTitleHomeDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(policygroupPage.isSubTitleOfPageDisplayed(), GlobalConstants.isSubTitleOfPageDisplayed);
		assertTrue(policygroupPage.isPolicyGroupHeaderTextDisplayed(),
				GlobalConstants.isPolicyGroupHeaderTextDisplayed);
		assertTrue(policygroupPage.isPolicyGroupNameHeaderDisplayed(),
				GlobalConstants.isPolicyGroupNameHeaderDisplayed);
		assertTrue(policygroupPage.isPolicyGroupDescriptionHeaderDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionHeaderDisplayed);
		assertTrue(policygroupPage.isCreatedDateHeaderTextDisplayed(),
				GlobalConstants.isCreatedDateHeaderTextDisplayed);
		assertTrue(policygroupPage.isStatusHeaderTextDisplayed(), GlobalConstants.isStatusHeaderTextDisplayed);
		assertTrue(policygroupPage.isActionHeaderTextDisplayed(), GlobalConstants.isActionHeaderTextDisplayed);

		assertTrue(policygroupPage.isPolicyGroupIdDescIconDisplayed(),
				GlobalConstants.isPolicyGroupIdDescIconDisplayed);
		assertTrue(policygroupPage.isPolicyGroupIdAscIconDisplayed(), GlobalConstants.isPolicyGroupIdAscIconDisplayed);
		assertTrue(policygroupPage.isPolicyGroupNameDescIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(policygroupPage.isPolicyGroupNameAscIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(policygroupPage.isPolicyGroupDescriptionDescIconDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionDescIconDisplayed);
		assertTrue(policygroupPage.isPolicyGroupDescriptionAscIconDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionAscIconDisplayed);
		assertTrue(policygroupPage.isCreatedDateTimeDescISconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(policygroupPage.isCreatedDateTimeAscIconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(policygroupPage.isStatusDescISconDisplayed(), GlobalConstants.isStatusDescISconDisplayed);
		assertTrue(policygroupPage.isStatusAscIconDisplayed(), GlobalConstants.isStatusAscIconDisplayed);

		assertTrue(policygroupPage.isFiletrButtonDisplayedOrEnabled(),
				GlobalConstants.isFiletrButtonDisplayedOrEnabled);
		assertTrue(policygroupPage.isPaginationDisplayed(), GlobalConstants.isPaginationDisplayed);

		policygroupPage.clickOnPolicyGroupList1();
		assertTrue(policygroupPage.isViewPolicyGroupPageTitleDisplayed(),
				GlobalConstants.isViewPolicyGroupPageTitleDisplayed);
		policygroupPage.clickOnPolicyGroupViewBackButton();

		policygroupPage.clickOnFilterButton();
		policygroupPage.clickOnPolicyGroupNameFilter(GlobalConstants.DEACTIVATE_POLICYGROUP);
		policygroupPage.clickOnApplyFilterButton();
		policygroupPage.clickOnPolicyGroupActionButton();
		assertTrue(policygroupPage.isPolicyGroupViewButtonDisplayed(),
				GlobalConstants.isPolicyGroupViewButtonDisplayed);
		assertTrue(policygroupPage.isDeactivateButtonDisplayed(),
				GlobalConstants.isPolicyGroupDeactivateButtonDisplayed);
		policygroupPage.clickOnDeactivateButton();
		policygroupPage.clickOnDeactivateConfirmButton();

		policygroupPage.clickOnFilterButton();
		policygroupPage.clickOnPolicyGroupNameFilter(GlobalConstants.DEACTIVATE_POLICYGROUP);
		policygroupPage.clickOnApplyFilterButton();
		policygroupPage.clickOnPolicyGroupList1();
		assertFalse(policygroupPage.isViewPolicyGroupPageTitleDisplayed(),
				GlobalConstants.isViewPolicyGroupPageTitleDisplayed);

		policygroupPage.clickOnFilterButton();
		policygroupPage.clickOnPolicyGroupNameFilter(GlobalConstants.INVALID_DATA);
		policygroupPage.clickOnApplyFilterButton();
		assertTrue(policygroupPage.isNoResultsFoundMessageDisplayed(),
				GlobalConstants.isNoResultsFoundMessageDisplayed);
		policygroupPage.navigateBackDefaultButton();

	}

	@Test(priority = 3, description = "Policygroup Details View")
	public void policyGroupDetailsView() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);
		BasePage basePage = new BasePage(driver);

		dashboardPage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);
		policygroupPage.clickOnFilterButton();
		policygroupPage.clickOnPolicyGroupNameFilter(GlobalConstants.ALPHANUMERIC);
		policygroupPage.clickOnApplyFilterButton();
		policygroupPage.clickOnPolicyGroupList1();
		assertTrue(policygroupPage.isViewPolicyGroupPageTitleDisplayed(),
				GlobalConstants.isViewPolicyGroupPageTitleDisplayed);
		assertTrue(policygroupPage.isSubTitleDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(policygroupPage.isSubTitleHomeDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(policygroupPage.isStatusOfPolicyGroupDisplayed(), GlobalConstants.isStatusOfPolicyGroupDisplayed);
		assertTrue(policygroupPage.isPolicyGroupNameLabelDisplayed(), GlobalConstants.isPolicyGroupNameLabelDisplayed);
		assertTrue(policygroupPage.isPolicyGroupNameContextDisplayed(),
				GlobalConstants.isPolicyGroupNameContextDisplayed);
		assertTrue(policygroupPage.isPolicyGroupDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionLabelDisplayed);
		assertTrue(policygroupPage.isPolicyGroupDescriptionContextDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionContextDisplayed);
		policygroupPage.clickOnTitleBackIconButton();

		policygroupPage.clickOnPolicyGroupList1();
		assertTrue(policygroupPage.isHamburgerOptionDisplayed(), GlobalConstants.isHamburgerOptionDisplayed);
		basePage.scrollToEndPage();
		assertTrue(policygroupPage.isMosipRightsTextDisplayed(), GlobalConstants.isMosipRightsTextDisplayed);
		assertTrue(policygroupPage.isFooterDocumentationLinkDisplayed(),
				GlobalConstants.isFooterDocumentationLinkDisplayed);
		assertTrue(policygroupPage.isFooterContactUsLinkDisplayed(), GlobalConstants.isFooterContactUsLinkDisplayed);
		policygroupPage.navigateBackDefaultButton();

	}

	@Test(priority = 4, description = "Create Datashare Policy")
	public void createDatasharePolicy() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		BasePage basePage = new BasePage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharePolicyPage = new DatasharePolicyPage(driver);

		dashboardpage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);
		assertTrue(policygroupPage.isDatasharePolicyTabDisplayed(), GlobalConstants.isDatasharePolicyTabDisplayed);
		policygroupPage.clickOnDatasharePolicyTab();

		assertTrue(datasharePolicyPage.isDataSharePolicyCreateButtonAvailable(),
				GlobalConstants.isDatasharePolicyCreateButtonAvailable);
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		assertTrue(datasharePolicyPage.isCreateDatashareTitleDisplayed(),
				GlobalConstants.isCreateDatashareTitleDisplayed);
		assertTrue(datasharePolicyPage.isSubTitleDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(datasharePolicyPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertTrue(datasharePolicyPage.isDatashareFormSubTitleDisplayed(),
				GlobalConstants.isDatashareFormSubTitleDisplayed);
		assertTrue(datasharePolicyPage.isPolicyGroupPlaceHolderDisplayed(),
				GlobalConstants.isPolicyGroupPlaceHolderDisplayed);
		datasharePolicyPage.clickOnPolicyGroupDropdown();
		assertTrue(datasharePolicyPage.isPolicyGroupDropdownSearchInputDisplayed(),
				GlobalConstants.isPolicyGroupDropdownSearchInputDisplayed);
		datasharePolicyPage.searchPolicyGroup(GlobalConstants.DEFAULTPOLICYGROUP);
		assertTrue(datasharePolicyPage.isPolicyGroupNameDisplayed(), GlobalConstants.isPolicyGroupNameDisplayed);
		assertTrue(datasharePolicyPage.isPolicyGroupDescriptionDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionDisplayed);
		datasharePolicyPage.clickOnPolicyGroupDropdown();

		assertTrue(datasharePolicyPage.isPolicyNameTextLabelDisplayed(),
				GlobalConstants.isPolicyNameTextLabelDisplayed);
		assertTrue(datasharePolicyPage.isPolicyNamePlaceHolderDisplayed(),
				GlobalConstants.isPolicyNamePlaceHolderDisplayed);
		assertTrue(datasharePolicyPage.isPolicyDescriptionTextLabelDisplayed(),
				GlobalConstants.isPolicyDescriptionTextLabelDisplayed);
		assertTrue(datasharePolicyPage.isPolicyDescriptionPlaceHolderDisplayed(),
				GlobalConstants.isPolicyDescriptionPlaceHolderDisplayed);
		assertTrue(datasharePolicyPage.isUploadPolicyDataLabelDisplayed(),
				GlobalConstants.isUploadPolicyDataLabelDisplayed);
		assertTrue(datasharePolicyPage.isUploadPolicyDataHelpTextDisplayed(),
				GlobalConstants.isUploadPolicyDataHelpTextDisplayed);
		assertTrue(datasharePolicyPage.isFileUploadPlaceHolderDisplayed(),
				GlobalConstants.isFileUploadPlaceHolderDisplayed);
		assertFalse(datasharePolicyPage.isPolicyDataBoxEnabled(), GlobalConstants.isPolicyDataBoxEnabled);

		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.ALPHANUMERIC);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.ALPHANUMERIC);
		datasharePolicyPage.uploadPolicyData();
		assertTrue(datasharePolicyPage.isPolicyDataUploadedSuccessMessageDisplayed(),
				GlobalConstants.isPolicyDataUploadedSuccessMessageDisplayed);
		assertTrue(datasharePolicyPage.isPolicyDataBoxEnabled(), GlobalConstants.isPolicyDataBoxEnabled);
		assertTrue(datasharePolicyPage.isPolicyDataContentDisplayed(), GlobalConstants.isPolicyDataContentDisplayed);
		basePage.scrollToEndPage();
		assertTrue(datasharePolicyPage.isSaveAsDraftButtonEnabled(), GlobalConstants.isSaveAsDraftButtonEnabled);
		datasharePolicyPage.clickOnSaveAsDraftButton();
		assertTrue(datasharePolicyPage.isTitleOfSuccessMessageDisplayed(),
				GlobalConstants.isTitleOfSuccessMessageDisplayed);
		assertTrue(datasharePolicyPage.isSubTitleOfSuccessMessageDisplayed(),
				GlobalConstants.isSubTitleOfSuccessMessageDisplayed);
		assertTrue(datasharePolicyPage.isSuccessGoBackButtonAvailable(),
				GlobalConstants.isSuccessGoBackButtonAvailable);
		assertTrue(datasharePolicyPage.isSuccessHomeButtonAvailable(), GlobalConstants.isSuccessHomeButtonAvailable);
		datasharePolicyPage.clickOnSuccessHomeButton();

	}

	@Test(priority = 5, description = "Create Multiple Datashare Policy")
	public void createMultipleDatasharePolicy() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		BasePage basePage = new BasePage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharePolicyPage = new DatasharePolicyPage(driver);

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.ALPHANUMERIC);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		assertTrue(datasharePolicyPage.isPublishButtonDisplayed(), GlobalConstants.isPublishButtonDisplayed);
		assertTrue(datasharePolicyPage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		assertTrue(datasharePolicyPage.isCloneButtonDisplayed(), GlobalConstants.isCloneButtonDisplayed);
		assertTrue(datasharePolicyPage.isDeactivateButtonDisplayed(), GlobalConstants.isDeactivateButtonDisplayed);

		datasharePolicyPage.clickOnPublishButton();
		assertTrue(datasharePolicyPage.isPublishConfirmationPopupDisplayed(),
				GlobalConstants.isPublishConfirmationPopupDisplayed);
		assertTrue(datasharePolicyPage.isPublishPolicyInfoMessageisplayed(),
				GlobalConstants.isPublishPolicyInfoMessageisplayed);
		assertTrue(datasharePolicyPage.isPublishPolicyCancelButtonDisplayed(),
				GlobalConstants.isPublishPolicyCancelButtonDisplayed);
		assertTrue(datasharePolicyPage.isPublishPolicyButtonDisplayed(),
				GlobalConstants.isPublishPolicyButtonDisplayed);
		datasharePolicyPage.clickOnPublishPolicyButton();
		datasharePolicyPage.clickOnPublishPolicyCloseButton();

		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.DATAPOLICYPUBLISH);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.DATAPOLICYPUBLISH_DESCRIPTION);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		datasharePolicyPage.clickOnGoBackButton();

		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.DEACTIVATE_DATA1);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.DEACTIVATE_DATA1_DESCRIPTION);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		datasharePolicyPage.clickOnGoBackButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnPublishButton();
		datasharePolicyPage.clickOnPublishPolicyButton();
		datasharePolicyPage.clickOnPublishPolicyCloseButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnDeactivateButton();
		datasharePolicyPage.clickOnDeactivateConfirmButton();

	}

	@Test(priority = 6, description = "Create Duplicate Datashare Policy")
	public void createDuplicateDatasharePolicy() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharePolicyPage = new DatasharePolicyPage(driver);

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.ALPHANUMERIC);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.ALPHANUMERIC);
		datasharePolicyPage.uploadPolicyData();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		assertTrue(datasharePolicyPage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);
		datasharePolicyPage.clickOnErrorCloseButton();
		datasharePolicyPage.enterPolicyName(GlobalConstants.DEACTIVATE_DATA1);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.DEACTIVATE_DATA1_DESCRIPTION);
		datasharePolicyPage.clickOnSaveAsDraftButton();
		assertTrue(datasharePolicyPage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);
		datasharePolicyPage.clickOnErrorCloseButton();
		datasharePolicyPage.enterPolicyName(GlobalConstants.SPECIAL_CHARACTERS);

		assertTrue(datasharePolicyPage.isSpecialCharactersAreNotAllowedErrorMessageDisplayed(),
				GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.SPECIAL_CHARACTERS);
		datasharePolicyPage.uploadPolicyData();
		assertFalse(datasharePolicyPage.isSaveAsDraftButtonEnabled(), GlobalConstants.isSaveAsDraftButtonEnabled);
		assertTrue(datasharePolicyPage.isClearFormDisplayed(), GlobalConstants.isClearFormDisplayed);
		datasharePolicyPage.clickOnClearForm();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.SPACE);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.SPACE);
		datasharePolicyPage.uploadPolicyData();
		assertTrue(datasharePolicyPage.isCancelFormDisplayed(), GlobalConstants.isCancelFormDisplayed);
		datasharePolicyPage.clickOnCancelForm();
		assertTrue(datasharePolicyPage.isDataLostWarningMessageDisplayed(),
				GlobalConstants.isDataLostWarningMessageDisplayed);
		datasharePolicyPage.clickOnlostWarningCancelButton();
		datasharePolicyPage.clickOnCancelForm();
		datasharePolicyPage.clickOnlostWarningCancelButton();
		datasharePolicyPage.clickOnCancelForm();
		datasharePolicyPage.clickOnlostWarningProceedButton();
		assertTrue(policygroupPage.isDatasharePolicyTabDisplayed(), GlobalConstants.isDatasharePolicyTabDisplayed);

	}

	@Test(priority = 7, description = "Upload Invalid Policy Data")
	public void uploadInvalidPolicyData() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		BasePage basepage = new BasePage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharePolicyPage = new DatasharePolicyPage(driver);

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.AUTOMATION_LOWERUPPERCASE);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.AUTOMATION_LOWERUPPERCASE);
		datasharePolicyPage.uploadExceedPolicyData();
		basepage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		basepage.scrollToStartPage();
		assertTrue(datasharePolicyPage.isPolicyDataExceedChractersMessageDisplayed(),
				GlobalConstants.isPolicyDataExceedChractersMessageDisplayed);
		datasharePolicyPage.clickOnErrorCloseButton();
		datasharePolicyPage.uploadInvalidPolicyData();
		assertTrue(datasharePolicyPage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		datasharePolicyPage.clickOnErrorCloseButton();
		datasharePolicyPage.uploadPolicyData();
		datasharePolicyPage.clickOnSaveAsDraftButton();

	}

	@Test(priority = 8, description = "Datashare Policy Tabular View")
	public void datasharePolicyTabularView() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		BasePage basePage = new BasePage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharePolicyPage = new DatasharePolicyPage(driver);

		dashboardPage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);
		assertTrue(policiesPage.isPoliciesAuthPolicyTabDisplayed(), GlobalConstants.isPoliciesAuthPolicyTabDisplayed);
		assertTrue(policygroupPage.isDatasharePolicyTabDisplayed(), GlobalConstants.isDatasharePolicyTabDisplayed);
		policygroupPage.clickOnDatasharePolicyTab();
		assertTrue(datasharePolicyPage.isSubTitleOfTabularViewDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewDisplayed);

		assertTrue(datasharePolicyPage.isPolicyIdHeaderTextDisplayed(), GlobalConstants.isPolicyIdHeaderTextDisplayed);
		assertTrue(datasharePolicyPage.isPolicyNameHeaderTextDisplayed(),
				GlobalConstants.isPolicyNameHeaderTextDisplayed);
		assertTrue(datasharePolicyPage.isPolicyDescriptionHeaderTextDisplayed(),
				GlobalConstants.isPolicyDescriptionHeaderTextDisplayed);
		assertTrue(datasharePolicyPage.isPolicyGroupHeaderTextDisplayed(),
				GlobalConstants.isPolicyGroupHeaderTextDisplayed);
		assertTrue(datasharePolicyPage.isCreatedDateHeaderTextDisplayed(),
				GlobalConstants.isCreatedDateHeaderTextDisplayed);
		assertTrue(datasharePolicyPage.isStatusHeaderTextDisplayed(), GlobalConstants.isStatusHeaderTextDisplayed);
		assertTrue(datasharePolicyPage.isActionHeaderTextDisplayed(), GlobalConstants.isActionHeaderTextDisplayed);
		assertTrue(datasharePolicyPage.isPolicyIdAscIconDisplayed(), GlobalConstants.isPolicyIdAscIconDisplayed);
		assertTrue(datasharePolicyPage.isPolicyIdDescIconDisplayed(), GlobalConstants.isPolicyIdDescIconDisplayed);
		assertTrue(datasharePolicyPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameAscIconDisplayed);
		assertTrue(datasharePolicyPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescIconDisplayed);
		assertTrue(datasharePolicyPage.isPolicyDescriptionAscIconDisplayed(),
				GlobalConstants.isPolicyDescriptionAscIconDisplayed);
		assertTrue(datasharePolicyPage.isPolicyGroupAscIconDisplayed(), GlobalConstants.isPolicyGroupAscIconDisplayed);
		assertTrue(datasharePolicyPage.isPolicyGroupDescIconDisplayed(),
				GlobalConstants.isPolicyGroupDescIconDisplayed);
		assertTrue(datasharePolicyPage.isPolicyDescriptionDescIconDisplayed(),
				GlobalConstants.isPolicyDescriptionDescIconDisplayed);
		assertTrue(datasharePolicyPage.isCreatedDateTimeDescISconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(datasharePolicyPage.isCreatedDateTimeAscIconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(datasharePolicyPage.isStatusDescIconDisplayed(), GlobalConstants.isStatusDescISconDisplayed);
		assertTrue(datasharePolicyPage.isStatusAscIconDisplayed(), GlobalConstants.isStatusAscIconDisplayed);

		datasharePolicyPage.clickOnFilterButton();
		assertTrue(datasharePolicyPage.isFilterResetButtonEnabled(), GlobalConstants.isFilterResetButtonEnabled);
		assertFalse(datasharePolicyPage.isFiletrButtonDisplayedOrEnabled(),
				GlobalConstants.isFiletrButtonDisplayedOrEnabled);
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.ALPHANUMERIC);
		datasharePolicyPage.clickOnApplyFilterButton();
		assertFalse(datasharePolicyPage.isPolicyStatusDeactivateDisplayed(),
				GlobalConstants.isPolicyStatusDeactivateDisplayed);
		assertTrue(datasharePolicyPage.isPolicyStatusActivateDisplayed(),
				GlobalConstants.isPolicyStatusActivateDisplayed);
		datasharePolicyPage.clickOnDatasharePolicyList1();
		assertTrue(datasharePolicyPage.isViewDatasharePolicyPageTitleDisplayed(),
				GlobalConstants.isViewDatasharePolicyPageTitleDisplayed);
		datasharePolicyPage.clickOnViewBackButton();

		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.DEACTIVATE_DATA2);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.DEACTIVATE_DATA2_DESCRIPTION);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		datasharePolicyPage.clickOnGoBackButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnPublishButton();
		datasharePolicyPage.clickOnPublishPolicyButton();
		datasharePolicyPage.clickOnPublishPolicyCloseButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnDeactivateButton();
		datasharePolicyPage.clickOnDeactivateConfirmButton();

		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.selectDeactivateStatusFilter();
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DEACTIVATE_DATA2);
		datasharePolicyPage.clickOnApplyFilterButton();
		assertTrue(datasharePolicyPage.isPolicyStatusDeactivateDisplayed(),
				GlobalConstants.isPolicyStatusDeactivateDisplayed);
		datasharePolicyPage.clickOnDeactivatedPolicy();
		assertFalse(datasharePolicyPage.isViewDatasharePolicyPageTitleDisplayed(),
				GlobalConstants.isViewDatasharePolicyPageTitleDisplayed);
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnPublishButton();
		assertFalse(datasharePolicyPage.isPublishConfirmationPopupDisplayed(),
				GlobalConstants.isPublishConfirmationPopupDisplayed);

		datasharePolicyPage.clickOnFilterResetButton();
		datasharePolicyPage.clickOnFilterButton();
		assertFalse(datasharePolicyPage.isApplyFilterButtonEnabled(), GlobalConstants.isApplyFilterButtonEnabled);
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.INVALID_DATA);
		datasharePolicyPage.clickOnApplyFilterButton();
		assertTrue(datasharePolicyPage.isNoResultsFoundMessageDisplayed(),
				GlobalConstants.isNoResultsFoundMessageDisplayed);
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnPolicyIdAscIcon();
		datasharePolicyPage.clickOnPolicyIdDescIcon();
		datasharePolicyPage.clickOnPolicyNameAscIcon();
		datasharePolicyPage.clickOnPolicyNameDescIcon();
		datasharePolicyPage.clickOnPolicyDescriptionAscIcon();
		datasharePolicyPage.clickOnPolicyDescriptionDescIcon();
		datasharePolicyPage.clickOnPolicyGroupNameAscIcon();
		datasharePolicyPage.clickOnPolicyGroupNameDescIcon();
		datasharePolicyPage.clickOnCreationDateAscIcon();
		datasharePolicyPage.clickOnCreationDateDescIcon();
		datasharePolicyPage.clickOnStatusAscIcon();
		datasharePolicyPage.clickOnStatusDescIcon();

		basePage.scrollToEndPage();
		assertTrue(datasharePolicyPage.isPaginationDisplayed(), GlobalConstants.isPaginationDisplayed);
		assertTrue(datasharePolicyPage.isMosipRightsTextDisplayed(), GlobalConstants.isMosipRightsTextDisplayed);
		assertTrue(datasharePolicyPage.isFooterDocumentationLinkDisplayed(),
				GlobalConstants.isFooterDocumentationLinkDisplayed);
		assertTrue(datasharePolicyPage.isFooterContactUsLinkDisplayed(),
				GlobalConstants.isFooterContactUsLinkDisplayed);
		policygroupPage.navigateBackDefaultButton();

	}

	@Test(priority = 9, description = "View Datashare Policy Details")
	public void viewDatasharePolicyDetails() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		BasePage basePage = new BasePage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharePolicyPage = new DatasharePolicyPage(driver);

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.selectActivateStatusFilter();
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnDatasharePolicyList1();
		assertTrue(datasharePolicyPage.isViewDatasharePolicyPageTitleDisplayed(),
				GlobalConstants.isViewDatasharePolicyPageTitleDisplayed);
		datasharePolicyPage.clickOnViewBackButton();

		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.ALPHANUMERIC);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnViewButton();

		assertTrue(datasharePolicyPage.isViewDatasharePolicyPageTitleDisplayed(),
				GlobalConstants.isViewDatasharePolicyPageTitleDisplayed);
		assertTrue(datasharePolicyPage.isSubTitleDisplayed(), GlobalConstants.isSubtitleButtonDisplayed);
		assertTrue(datasharePolicyPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertTrue(datasharePolicyPage.isPolicyIdLabelDisplayed(), GlobalConstants.isPolicyIdLabelDisplayed);
//		assertTrue(datasharepolicypage.isPolicyIdContextDisplayed(), GlobalConstants.isPolicyIdContextDisplayed);
		assertTrue(datasharePolicyPage.isPolicyNameLabelDisplayed(), GlobalConstants.isPolicyNameLabelDisplayed);
		assertTrue(datasharePolicyPage.isPolicyNameContextDisplayed(), GlobalConstants.isPolicyNameContextDisplayed);
		assertTrue(datasharePolicyPage.isPolicyGroupLabelDisplayed(), GlobalConstants.isPolicyGroupLabelDisplayed);
		assertTrue(datasharePolicyPage.isPolicyGroupContextDisplayed(), GlobalConstants.isPolicyGroupContextDisplayed);
		assertTrue(datasharePolicyPage.isPolicyDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyDescriptionLabelDisplayed);
		assertTrue(datasharePolicyPage.isPolicyDescriptionContextDisplayed(),
				GlobalConstants.isPolicyDescriptionContextDisplayed);
		assertTrue(datasharePolicyPage.isPolicyGroupDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionLabelDisplayed);
		assertTrue(datasharePolicyPage.isPolicyGroupDescriptionContextDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionContextDisplayed);
		assertTrue(datasharePolicyPage.isPolicyDataLabelDisplayed(), GlobalConstants.isPolicyDataLabelDisplayed);
		assertTrue(datasharePolicyPage.isPolicyDataTitleDisplayed(), GlobalConstants.isPolicyDataTitleDisplayed);
		assertTrue(datasharePolicyPage.isPolicyDataPreviewDisplayed(), GlobalConstants.isPolicyDataPreviewDisplayed);

		datasharePolicyPage.clickOnPolicyDataPreviewButton();
		assertTrue(datasharePolicyPage.ispolicyDataPopupDisplayed(), GlobalConstants.ispolicyDataPopupDisplayed);
		assertTrue(datasharePolicyPage.isDownloadButtonDisplayed(), GlobalConstants.isDownloadButtonDisplayed);
		assertTrue(datasharePolicyPage.isCloseButtonDisplayed(), GlobalConstants.isCloseButtonDisplayed);
		datasharePolicyPage.clickOnDownloadButton();
		datasharePolicyPage.clickOnCloseButton();
		policygroupPage.navigateBackDefaultButton();

		basePage.scrollToEndPage();
		assertTrue(datasharePolicyPage.isMosipRightsTextDisplayed(), GlobalConstants.isMosipRightsTextDisplayed);
		assertTrue(datasharePolicyPage.isFooterDocumentationLinkDisplayed(),
				GlobalConstants.isFooterDocumentationLinkDisplayed);
		assertTrue(datasharePolicyPage.isFooterContactUsLinkDisplayed(),
				GlobalConstants.isFooterContactUsLinkDisplayed);
	}

	@Test(priority = 10, description = "Publish Datashare Policy")
	public void publishDatasharePolicy() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharePolicyPage = new DatasharePolicyPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		LoginPage loginPage = new LoginPage(driver);

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICYPUBLISH);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnPublishButton();
		datasharePolicyPage.clickOnPublishPolicyButton();
		datasharePolicyPage.clickOnPublishPolicyCloseButton();

		logoutFromPartner(dashboardPage);

		loginPage.enterUserName("pmpui-auth");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectInvalidPolicyNameDropdown(GlobalConstants.INVALID_DATA);
		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		policiesPage.clickOnHomeButton();
		datasharePolicyPage.clickOnlostWarningProceedButton();

		logoutFromPartner(dashboardPage);

		loginPage.enterUserName("pmpui-v2");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.DATAPOLICY01_DESCRIPTION);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		datasharePolicyPage.clickOnGoBackButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnPublishButton();
		datasharePolicyPage.clickOnPublishPolicyButton();
		datasharePolicyPage.clickOnPublishPolicyCloseButton();
		policiesPage.clickOnHomeButton();

	}

	@Test(priority = 11, description = "Edit Datashare Policy")
	public void editDatasharePolicy() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		BasePage basepage = new BasePage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		DatasharePolicyPage datasharePolicyPage = new DatasharePolicyPage(driver);

		dashboardpage.clickOnPolicyButton();
		policiesPage.clickOnDataSharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();

		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.EDITDATAPOLICY);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.EDITDATAPOLICY_DESCRIPTION);
		datasharePolicyPage.uploadPolicyData();
		basepage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		datasharePolicyPage.clickOnGoBackButton();

		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.EDITDATAPOLICY);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		assertTrue(datasharePolicyPage.isEditButtonEnable(), GlobalConstants.isEditButtonEnable);
		datasharePolicyPage.clickOnEditButton();

		assertTrue(datasharePolicyPage.isEditPolicyPageTitleDisplayed(),
				GlobalConstants.isEditPolicyPageTitleDisplayed);
		assertTrue(datasharePolicyPage.isHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(datasharePolicyPage.isSubTitleDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(datasharePolicyPage.isPolicyFormSubTitleDisplayed(), GlobalConstants.isPolicyFormSubTitleDisplayed);
		assertTrue(datasharePolicyPage.isPolicyGroupDropdownEnabled(), GlobalConstants.isPolicyGroupDropdownEnabled);
		assertTrue(datasharePolicyPage.isEditPolicyGroupDropdownValueDisplayed(),
				GlobalConstants.isEditPolicyGroupDropdownValueDisplayed);
		assertTrue(datasharePolicyPage.isPolicyNameBoxDisplayed(), GlobalConstants.isPolicyNameBoxDisplayed);
		assertTrue(datasharePolicyPage.isEditPolicyNameValueDisplayed(),
				GlobalConstants.isEditPolicyNameValueDisplayed);
		assertTrue(datasharePolicyPage.isPolicyDescriptionBoxDisplayed(),
				GlobalConstants.isPolicyDescriptionBoxDisplayed);
		assertTrue(datasharePolicyPage.isEditPolicyDescriptionValueDisplayed(),
				GlobalConstants.isEditPolicyDescriptionValueDisplayed);
		assertTrue(datasharePolicyPage.isReUploadPolicyDataLabelDisplayed(),
				GlobalConstants.isReUploadPolicyDataLabelDisplayed);
		assertTrue(datasharePolicyPage.isReuploadButtonDisplayed(), GlobalConstants.isReuploadButtonDisplayed);
		assertTrue(datasharePolicyPage.isEditPolicyDataContextDisplayed(),
				GlobalConstants.isEditPolicyDataContextDisplayed);
		assertTrue(datasharePolicyPage.isEditPolicyClearButtonDisplayed(),
				GlobalConstants.isEditPolicyClearButtonDisplayed);
		assertTrue(datasharePolicyPage.isEditPolicySubmitButtonDisplayed(),
				GlobalConstants.isEditPolicySubmitButtonDisplayed);
		assertTrue(datasharePolicyPage.isEditPolicyCancelButtonDisplayed(),
				GlobalConstants.isEditPolicyCancelButtonDisplayed);

		datasharePolicyPage.enterPolicyName(GlobalConstants.SPACE);
		assertFalse(datasharePolicyPage.isSaveAsDraftButtonEnabled(), GlobalConstants.isSaveAsDraftButtonEnabled);

		datasharePolicyPage.enterPolicyName(GlobalConstants.Single_CHARACTERS);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.Single_CHARACTERS);
		datasharePolicyPage.clickOnEditPolicyFormSubmitButton();
		assertTrue(datasharePolicyPage.isEditPolicySuccessTitleDisplayed(),
				GlobalConstants.isEditPolicySuccessTitleDisplayed);
		assertTrue(datasharePolicyPage.isEditPolicySuccessSubTitleDisplayed(),
				GlobalConstants.isEditPolicySuccessSubTitleDisplayed);
		assertTrue(datasharePolicyPage.isEditSuccessGoBackButtonEnabled(),
				GlobalConstants.isEditSuccessGoBackButtonEnabled);
		assertTrue(datasharePolicyPage.isEditSuccessHomeButtonEnabled(),
				GlobalConstants.isEditSuccessHomeButtonEnabled);
		datasharePolicyPage.clickOnGoBackButton();

		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.Single_CHARACTERS);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnEditButton();
		datasharePolicyPage.enterPolicyName(GlobalConstants.AUTOMATION);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.AUTOMATION);
		datasharePolicyPage.clickOnEditPolicyFormSubmitButton();
		datasharePolicyPage.clickOnGoBackButton();

		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTOMATION);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnEditButton();
		datasharePolicyPage.enterPolicyName(GlobalConstants.NUMERIC2);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.NUMERIC2);
		datasharePolicyPage.uploadBlankData();
		assertTrue(datasharePolicyPage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		datasharePolicyPage.clickOnErrorCloseButton();
		datasharePolicyPage.uploadInvalidPolicyData();
		assertTrue(datasharePolicyPage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		datasharePolicyPage.clickOnErrorCloseButton();
		datasharePolicyPage.uploadExceedPolicyData();
		basepage.scrollToEndPage();
		datasharePolicyPage.clickOnEditPolicyFormSubmitButton();
		basepage.scrollToStartPage();
		assertTrue(datasharePolicyPage.isInvalidInfoInPoliyDataMessageDisplayed(),
				GlobalConstants.isPolicyDataExceedChractersMessageDisplayed);
		datasharePolicyPage.clickOnErrorCloseButton();
		datasharePolicyPage.uploadPolicyData();
		datasharePolicyPage.clearTextBoxPolicyData();
		basepage.scrollToEndPage();
		assertTrue(datasharePolicyPage.isEditPolicySubmitButtonEnabled(), GlobalConstants.isSaveAsDraftButtonEnabled);
		datasharePolicyPage.clickOnEditPolicyFormSubmitButton();
		datasharePolicyPage.clickOnGoBackButton();

		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.NUMERIC2);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnEditButton();
		datasharePolicyPage.clickOnEditPolicyFormCancelButton();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.NUMERIC2);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnEditButton();
		datasharePolicyPage.enterPolicyName(GlobalConstants.EDITDATAPOLICY);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.EDITDATAPOLICY);
		datasharePolicyPage.clickOnEditPolicyFormCancelButton();
		assertTrue(datasharePolicyPage.isChangesLostConfirmationMessageDisplayed(),
				GlobalConstants.isChangesLostConfirmationMessageDisplayed);
		datasharePolicyPage.clickOnlostWarningCancelButton();
		datasharePolicyPage.clickOnUndoChangesButton();

		datasharePolicyPage.enterPolicyName(GlobalConstants.EDITDATAPOLICY);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.EDITDATAPOLICY);
		datasharePolicyPage.uploadAlphabetData();
		assertTrue(datasharePolicyPage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		datasharePolicyPage.clickOnErrorCloseButton();
		datasharePolicyPage.uploadSpecialChData();
		assertTrue(datasharePolicyPage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		datasharePolicyPage.clickOnErrorCloseButton();
		datasharePolicyPage.uploadPolicyData();
		basepage.scrollToEndPage();
		datasharePolicyPage.clickOnEditPolicyFormSubmitButton();
		datasharePolicyPage.clickOnGoBackButton();
		basepage.navigateBack();

	}

	@Test(priority = 12, description = "Clone Datashare Policy")
	public void cloneDatasharePolicy() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		BasePage basePage = new BasePage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharePolicyPage = new DatasharePolicyPage(driver);

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.DATAPOLICY02);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.DATAPOLICY02);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		datasharePolicyPage.clickOnGoBackButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnCloneButton();
		assertFalse(datasharePolicyPage.isClonePolicyPopupTitleDisplayed(),
				GlobalConstants.isClonePolicyPopupTitleDisplayed);
		datasharePolicyPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DEACTIVATE_DATA1);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		assertTrue(datasharePolicyPage.isCloneButtonEnabled(), GlobalConstants.isCloneButtonEnabled);
		datasharePolicyPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		assertTrue(datasharePolicyPage.isCloneButtonEnabled(), GlobalConstants.isCloneButtonEnabled);
		datasharePolicyPage.clickOnCloneButton();
		assertTrue(datasharePolicyPage.isClonePolicyPopupTitleDisplayed(),
				GlobalConstants.isClonePolicyPopupTitleDisplayed);
		assertTrue(datasharePolicyPage.isClonePolicyInfoMessageDisplayed(),
				GlobalConstants.isClonePolicyInfoMessageDisplayed);
		assertTrue(datasharePolicyPage.isClonePolicyGroupDropdownDisplayed(),
				GlobalConstants.isClonePolicyGroupDropdownDisplayed);

		datasharePolicyPage.clickOnClonePolicyGroupDropdown();
		assertTrue(datasharePolicyPage.isClonePolicyGroupSearchInputDisplayed(),
				GlobalConstants.isClonePolicyGroupSearchInputDisplayed);
		assertTrue(datasharePolicyPage.isClonePolicyCancelButtonAvailable(),
				GlobalConstants.isClonePolicyCancelButtonAvailable);
		assertTrue(datasharePolicyPage.isClonePolicyButtonAvailable(), GlobalConstants.isClonePolicyButtonAvailable);
		assertFalse(datasharePolicyPage.isClonePolicyButtonEnabled(), GlobalConstants.isClonePolicyButtonEnabled);

		datasharePolicyPage.searchPolicyGroupForClone(GlobalConstants.CHARACTERS_1);
		assertTrue(datasharePolicyPage.isClonePolicyGroupNameDisplayed(),
				GlobalConstants.isClonePolicyGroupNameDisplayed);
		assertTrue(datasharePolicyPage.isClonePolicyGroupDescriptionDisplayed(),
				GlobalConstants.isClonePolicyGroupDescriptionDisplayed);

		datasharePolicyPage.searchPolicyGroupForClone(GlobalConstants.DEACTIVATE_DATA1);
		assertTrue(datasharePolicyPage.isNoDataAvailableDisplayed(), GlobalConstants.isNoDataAvailableDisplayed);
		datasharePolicyPage.clickOnClonePolicyGroupDropdown();
		datasharePolicyPage.selectPolicyGroupForClone(GlobalConstants.CHARACTERS_1);
		assertTrue(datasharePolicyPage.isClonePolicyButtonEnabled(), GlobalConstants.isClonePolicyButtonEnabled);
		datasharePolicyPage.clickOnClonePolicyButton();
		assertTrue(datasharePolicyPage.isClonedSuccessMessageDisplayed(),
				GlobalConstants.isClonedSuccessPopupDisplayed);
		assertFalse(datasharePolicyPage.isClonePolicyCancelButtonEnabled(),
				GlobalConstants.isClonePolicyCancelButtonEnabled);
		assertTrue(datasharePolicyPage.isClonePolicyCloseButtonEnabled(),
				GlobalConstants.isClonePolicyCloseButtonEnabled);
		datasharePolicyPage.clickOnClonePolicyCloseButton();
		assertTrue(datasharePolicyPage.isSubTitleOfTabularViewDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewDisplayed);

		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		assertTrue(datasharePolicyPage.isUpdatedPolicyGroupDisplayed(), GlobalConstants.isUpdatedPolicyGroupDisplayed);
		assertTrue(datasharePolicyPage.isClonedPolicyStatusDraftDisplayed(),
				GlobalConstants.isClonedPolicyStatusDraftDisplayed);
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnViewButton();
		assertTrue(datasharePolicyPage.isViewPolicyDetailsStatusDraftDisplayed(),
				GlobalConstants.isViewPolicyDetailsStatusDraftDisplayed);
		datasharePolicyPage.clickOnViewBackButton();

		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnCloneButton();
		datasharePolicyPage.clickOnClonePolicyCancelButton();
		assertTrue(datasharePolicyPage.isSubTitleOfTabularViewDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewDisplayed);

		datasharePolicyPage.clickOnFilterResetButton();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnCloneButton();
		datasharePolicyPage.selectPolicyGroupForClone(GlobalConstants.CHARACTERS_1);
		datasharePolicyPage.clickOnClonePolicyButton();
		assertTrue(datasharePolicyPage.isAlreadyExistErrorMessageDisplayed(),
				GlobalConstants.isAlreadyExistErrorMessageDisplayed);
		datasharePolicyPage.clickOnCloseIcon();
		assertTrue(datasharePolicyPage.isSubTitleOfTabularViewDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewDisplayed);

		datasharePolicyPage.clickOnFilterResetButton();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnCloneButton();
		datasharePolicyPage.selectPolicyGroupForClone(GlobalConstants.CHARACTERS_1);
		datasharePolicyPage.clickOnClonePolicyButton();
		assertTrue(datasharePolicyPage.isAlreadyExistErrorMessageDisplayed(),
				GlobalConstants.isAlreadyExistErrorMessageDisplayed);
		datasharePolicyPage.clickOnCloseIcon();
		assertTrue(datasharePolicyPage.isSubTitleOfTabularViewDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewDisplayed);

		datasharePolicyPage.clickOnFilterResetButton();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnCloneButton();
		datasharePolicyPage.selectPolicyGroupForClone(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.clickOnClonePolicyButton();
		assertTrue(datasharePolicyPage.isAlreadyExistErrorMessageDisplayed(),
				GlobalConstants.isAlreadyExistErrorMessageDisplayed);
		datasharePolicyPage.clickOnCloseIcon();
		assertTrue(datasharePolicyPage.isSubTitleOfTabularViewDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewDisplayed);

		datasharePolicyPage.clickOnFilterResetButton();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnCloneButton();
		datasharePolicyPage.selectPolicyGroupForClone(GlobalConstants.POLICYGROUP07);
		datasharePolicyPage.clickOnClonePolicyButton();
		datasharePolicyPage.clickOnClonePolicyCloseButton();

	}

	@Test(priority = 13, description = "Deactivate Datashare Policy")
	public void deactivateDatasharePolicy() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharePolicyPage = new DatasharePolicyPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		ApiKeyPage apikeyPage = new ApiKeyPage(driver);
		PartnerPolicyMappingPage partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.EDITDATAPOLICY1);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.EDITDATAPOLICY1);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		datasharePolicyPage.clickOnGoBackButton();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.EDITDATAPOLICY1);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnPublishButton();
		datasharePolicyPage.clickOnPublishPolicyButton();
		datasharePolicyPage.clickOnPublishPolicyCloseButton();
		datasharePolicyPage.clickOnFilterResetButton();

		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY02);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		assertTrue(datasharePolicyPage.isDeactivateButtonEnabled(), GlobalConstants.isDeactivateButtonEnabled);
		datasharePolicyPage.clickOnPublishButton();
		datasharePolicyPage.clickOnPublishPolicyButton();
		datasharePolicyPage.clickOnPublishPolicyCloseButton();
		datasharePolicyPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DEACTIVATE_DATA2);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		assertTrue(datasharePolicyPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		assertTrue(datasharePolicyPage.isCloneButtonEnabled(), GlobalConstants.isCloneButtonEnabled);
		datasharePolicyPage.clickOnDeactivateButton();
		assertFalse(datasharePolicyPage.isDeactivatePolicyPopupDisplayed(),
				GlobalConstants.isDeactivatePolicyPopupDisplayed);
		datasharePolicyPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		assertTrue(datasharePolicyPage.isDeactivateButtonEnabled(), GlobalConstants.isDeactivateButtonEnabled);
		datasharePolicyPage.clickOnDeactivateButton();
		assertTrue(datasharePolicyPage.isDeactivatePolicyPopupDisplayed(),
				GlobalConstants.isDeactivatePolicyPopupDisplayed);
		assertTrue(datasharePolicyPage.isDeactivatePolicyPopupTitleDisplayed(),
				GlobalConstants.isDeactivatePolicyPopupTitleDisplayed);
		assertTrue(datasharePolicyPage.isDeactivatePolicyInfoMessageDisplayed(),
				GlobalConstants.isDeactivatePolicyInfoMessageDisplayed);
		assertTrue(datasharePolicyPage.isDeactivateConfirmButtonAvailable(),
				GlobalConstants.isDeactivateConfirmButtonAvailable);
		assertTrue(datasharePolicyPage.isDeactivateCancelButtonAvailable(),
				GlobalConstants.isDeactivateCancelButtonAvailable);
		datasharePolicyPage.clickOnDeactivateCancelButton();
		assertTrue(datasharePolicyPage.isSubTitleOfTabularViewDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewDisplayed);

		logoutFromPartner(dashboardPage);

		loginPage.enterUserName("pmpui-auth");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.listPageCreateOidcClientButton();
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.enterDeactivePolicyNameInDropdown(GlobalConstants.DEACTIVATE_DATA2);
		assertTrue(oidcClientPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		oidcClientPage.clickOnCreateOidcClearForm();
		policiesPage.clickOnHomeButton();

		dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnApiKeyTab();
		apikeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apikeyPage.selectPartnerIdDropdown();
		apikeyPage.enterDeactivePolicyNameInDropdown(GlobalConstants.DEACTIVATE_DATA2);
		assertTrue(apikeyPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		apikeyPage.clickOnClearButton();
		policiesPage.clickOnHomeButton();

		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectInvalidPolicyNameDropdown(GlobalConstants.DEACTIVATE_DATA2);
		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		policiesPage.clickOnRequestPoliciesFormClearButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(GlobalConstants.DATAPOLICY02);
		policiesPage.enterComments(GlobalConstants.DATAPOLICY02);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(GlobalConstants.EDITDATAPOLICY1);
		policiesPage.enterComments(GlobalConstants.EDITDATAPOLICY1);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(GlobalConstants.ALPHANUMERIC);
		policiesPage.enterComments(GlobalConstants.ALPHANUMERIC);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();
		
		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(GlobalConstants.DATAPOLICY01);
		policiesPage.enterComments(GlobalConstants.DATAPOLICY01);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

		logoutFromPartner(dashboardPage);

		loginPage.enterUserName("pmpui-v2");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.ALPHANUMERIC);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		partnerPolicyMappingPage.clickOnFilterResetButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		partnerPolicyMappingPage.clickOnRejectButton();
		partnerPolicyMappingPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.ALPHANUMERIC);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnDeactivateButton();
		datasharePolicyPage.clickOnDeactivateConfirmButton();
		assertTrue(datasharePolicyPage.isPartnerPolicyLinkPendingErrorDisplayed(),
				GlobalConstants.isPartnerPolicyLinkPendingErrorDisplayed);
		datasharePolicyPage.clickOnAlertErrorOkButton();
		partnerPolicyMappingPage.clickOnFilterResetButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.EDITDATAPOLICY1);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnDeactivateButton();
		datasharePolicyPage.clickOnDeactivateConfirmButton();
		assertTrue(datasharePolicyPage.isPartnerPolicyLinkPendingErrorDisplayed(),
				GlobalConstants.isPartnerPolicyLinkPendingErrorDisplayed);
		datasharePolicyPage.clickOnAlertErrorOkButton();
		partnerPolicyMappingPage.clickOnFilterResetButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnDeactivateButton();
		datasharePolicyPage.clickOnDeactivateConfirmButton();
		basePage.navigateBack();
		basePage.navigateForword();

		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		assertTrue(datasharePolicyPage.isPolicyStatusDeactivateDisplayed(),
				GlobalConstants.isPolicyStatusDeactivateDisplayed);

		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.DATAPOLICY01_DESCRIPTION);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		assertTrue(datasharePolicyPage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);

	}

	@Test(priority = 14, description = "Create Auth Policy")
	public void createAuthPolicy() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		BasePage basePage = new BasePage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		AuthPolicyPage authPolicyPage = new AuthPolicyPage(driver);

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

		authPolicyPage.selectPolicyGroup(GlobalConstants.DEFAULTPOLICYGROUP);
		assertTrue(authPolicyPage.isPolicyGroupNameDisplayed(), GlobalConstants.isPolicyGroupNameDisplayed);
		assertTrue(authPolicyPage.isPolicyGroupDescriptionDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionDisplayed);
		authPolicyPage.clickOnPolicyGroupDropdown();

		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
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
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
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
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
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
		authPolicyPage.selectPolicyGroup(GlobalConstants.DEACTIVATE_POLICYGROUP);
		assertTrue(authPolicyPage.isNoDataAvailableDisplayed(), GlobalConstants.isNoDataAvailableDisplayed);
		authPolicyPage.clickOnPolicyGroupDropdown();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
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
		authPolicyPage.editPolicyData(GlobalConstants.Random_DATA);
		assertTrue(authPolicyPage.isPolicyDataEdited(), GlobalConstants.isPolicyDataEdited);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
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

	@Test(priority = 15, description = "Upload Invalid auth Policy Data")
	public void uploadInvalidAuthPolicyData() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		BasePage basePage = new BasePage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		AuthPolicyPage authpolicyPage = new AuthPolicyPage(driver);

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authpolicyPage.clickOnCreateAuthPolicyButton();
		authpolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		authpolicyPage.enterPolicyName(GlobalConstants.DEACTIVATE_AUTH2);
		authpolicyPage.enterpolicyDescription(GlobalConstants.DEACTIVATE_AUTH2_DESCRIPTION);
		authpolicyPage.uploadBlankData();
		assertTrue(authpolicyPage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		authpolicyPage.clickOnErrorCloseButton();
		authpolicyPage.uploadInvalidPolicyData();
		assertTrue(authpolicyPage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		authpolicyPage.clickOnErrorCloseButton();
		authpolicyPage.uploadExceedData();
		basePage.scrollToEndPage();
		authpolicyPage.clickOnSaveAsDraftButton();
		basePage.scrollToStartPage();
		assertTrue(authpolicyPage.isPolicyDataExceedChractersMessageDisplayed(),
				GlobalConstants.isPolicyDataExceedChractersMessageDisplayed);
		authpolicyPage.clickOnErrorCloseButton();
		authpolicyPage.uploadPolicyData();
		authpolicyPage.clearTextBoxPolicyData();
		basePage.scrollToEndPage();
		assertTrue(authpolicyPage.isSaveAsDraftButtonEnabled(), GlobalConstants.isSaveAsDraftButtonEnabled);
		authpolicyPage.uploadPolicyData();
		authpolicyPage.clickOnSaveAsDraftButton();
		assertTrue(authpolicyPage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);
		authpolicyPage.clickOnErrorCloseButton();

		authpolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		authpolicyPage.enterPolicyName(GlobalConstants.ALPHANUMERIC2);
		authpolicyPage.enterpolicyDescription(GlobalConstants.ALPHANUMERIC2);
		authpolicyPage.clickOnPolicyClearButton();

		authpolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		authpolicyPage.enterPolicyName(GlobalConstants.ALPHANUMERIC2);
		authpolicyPage.enterpolicyDescription(GlobalConstants.ALPHANUMERIC2);
		authpolicyPage.uploadPolicyData();
		authpolicyPage.clickOnPolicyCancelButton();
		authpolicyPage.clickOnProceedButton();
		assertTrue(authpolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		authpolicyPage.clickOnCreateAuthPolicyButton();
		authpolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		authpolicyPage.enterPolicyName(GlobalConstants.SPACE);
		authpolicyPage.enterpolicyDescription(GlobalConstants.SPACE);
		authpolicyPage.uploadPolicyData();
		assertFalse(authpolicyPage.isSaveAsDraftButtonEnabled(), GlobalConstants.isSaveAsDraftButtonEnabled);
		authpolicyPage.clickOnHomeButton();
	}

	@Test(priority = 16, description = "Auth Policy Tabular View")
	public void authPolicyTabularView() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		BasePage basePage = new BasePage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		AuthPolicyPage authPolicyPage = new AuthPolicyPage(driver);

		dashboardPage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesAuthPolicyTabDisplayed(), GlobalConstants.isPoliciesAuthPolicyTabDisplayed);
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
		assertFalse(authPolicyPage.isFiletrButtonDisplayedOrEnabled(),
				GlobalConstants.isFiletrButtonDisplayedOrEnabled);
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

		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULTPOLICYGROUP);
		authPolicyPage.selectActivateStatusFilter();
		authPolicyPage.clickOnApplyFilterButton();
		assertTrue(authPolicyPage.isPolicyStatusActivateDisplayed(), GlobalConstants.isPolicyStatusActivateDisplayed);
		authPolicyPage.clickOnActivatedAuthPolicy();
		assertTrue(authPolicyPage.isPolicyViewPageTitleDisplayed(), GlobalConstants.isPolicyViewPageTitleDisplayed);
		authPolicyPage.clickOnViewBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULTPOLICYGROUP);
		authPolicyPage.selectActivateStatusFilter();
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		assertTrue(authPolicyPage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		assertTrue(authPolicyPage.isCloneButtonDisplayed(), GlobalConstants.isCloneButtonDisplayed);
		assertTrue(authPolicyPage.isDeactivateButtonDisplayed(), GlobalConstants.isDeactivateButtonDisplayed);

		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULTPOLICYGROUP);
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

	@Test(priority = 17, description = "Auth Policy Details View")
	public void authPolicyDetailsView() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		AuthPolicyPage authPolicyPage = new AuthPolicyPage(driver);

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULTPOLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY03);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		assertTrue(authPolicyPage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		authPolicyPage.clickOnViewButton();
		assertTrue(authPolicyPage.isPolicyViewPageTitleDisplayed(), GlobalConstants.isPolicyViewPageTitleDisplayed);
		assertTrue(authPolicyPage.isSubTitleDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(authPolicyPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertTrue(authPolicyPage.isPolicyIdLabelDisplayed(), GlobalConstants.isPolicyIdLabelDisplayed);
//		assertTrue(authpolicypage.isPolicyIdContextDisplayed(), GlobalConstants.isPolicyIdContextDisplayed);
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
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULTPOLICYGROUP);
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

	@Test(priority = 18, description = "Edit Auth Policy")
	public void editAuthPolicy() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		BasePage basePage = new BasePage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		AuthPolicyPage authPolicyPage = new AuthPolicyPage(driver);

		dashboardpage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();

		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.EDITAUTHPOLICY);
		authPolicyPage.enterpolicyDescription(GlobalConstants.EDITAUTHPOLICY_DESCRIPTION);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnFilterButton();
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

		authPolicyPage.enterPolicyName(GlobalConstants.Single_CHARACTERS);
		authPolicyPage.enterpolicyDescription(GlobalConstants.Single_CHARACTERS);
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
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.Single_CHARACTERS);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnEditButton();
		authPolicyPage.enterPolicyName(GlobalConstants.AUTOMATION);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTOMATION);
		basePage.scrollToEndPage();
		authPolicyPage.clickOnEditPolicyFormSubmitButton();
		authPolicyPage.clickOnGoBackButton();

//		authPolicyPage.clickOnFilterResetButton();
		authPolicyPage.clickOnFilterButton();
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
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.NUMERIC2);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnEditButton();
		authPolicyPage.clickOnEditPolicyFormCancelButton();
		authPolicyPage.clickOnFilterButton();
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

	@Test(priority = 19, description = "Publish Auth Policy")
	public void publishAuthPolicy() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		AuthPolicyPage authPolicyPage = new AuthPolicyPage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();

		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY08);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY08_DESCRIPTION);
		authPolicyPage.uploadPolicyData();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnFilterButton();
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

		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY08);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		assertTrue(authPolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.ALPHANUMERIC2);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		assertTrue(authPolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY08);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnCloneButton();
		authPolicyPage.selectPolicyGroupDropdownForClone(GlobalConstants.NUMERIC);
		authPolicyPage.clickOnClonePolicyButton();
		authPolicyPage.clickOnClonePolicyCloseButton();
		policygroupPage.navigateBackDefaultButton();

	}

	@Test(priority = 20, description = "Clone Auth Policy")
	public void cloneAuthPolicy() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		AuthPolicyPage authPolicyPage = new AuthPolicyPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
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
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULTPOLICYGROUP);
		authPolicyPage.selectDeactivateStatusFilter();
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.DEACTIVATE_AUTH2);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		assertTrue(authPolicyPage.isCloneButtonEnabled(), GlobalConstants.isCloneButtonEnabled);

		authPolicyPage.clickOnFilterResetButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULTPOLICYGROUP);
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

		authPolicyPage.searchPolicyGroupForClone(GlobalConstants.DEACTIVATE_DATA1);
		assertTrue(authPolicyPage.isNoDataAvailableDisplayed(), GlobalConstants.isNoDataAvailableDisplayed);
		authPolicyPage.clickOnClonePolicyGroupDropdown();

		authPolicyPage.selectPolicyGroupForClone(GlobalConstants.CHARACTERS_1);
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
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULTPOLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnCloneButton();
		authPolicyPage.clickOnClonePolicyCancelButton();

		authPolicyPage.clickOnFilterResetButton();
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULTPOLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnCloneButton();
		authPolicyPage.selectPolicyGroupForClone(GlobalConstants.CHARACTERS_1);
		authPolicyPage.clickOnClonePolicyButton();
		assertTrue(authPolicyPage.isAlreadyExistErrorMessageDisplayed(),
				GlobalConstants.isAlreadyExistErrorMessageDisplayed);
		authPolicyPage.clickOnCloseIcon();
		assertTrue(authPolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULTPOLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnCloneButton();
		authPolicyPage.selectPolicyGroupForClone(GlobalConstants.DEFAULTPOLICYGROUP);
		authPolicyPage.clickOnClonePolicyButton();
		assertTrue(authPolicyPage.isAlreadyExistErrorMessageDisplayed(),
				GlobalConstants.isAlreadyExistErrorMessageDisplayed);
		authPolicyPage.clickOnCloseIcon();
		assertTrue(authPolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULTPOLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnCloneButton();
		authPolicyPage.selectPolicyGroupForClone(GlobalConstants.POLICYGROUP07);
		authPolicyPage.clickOnClonePolicyButton();
		authPolicyPage.clickOnClonePolicyCloseButton();

	}

	@Test(priority = 21, description = "Deactivate Auth Policy")
	public void deactivateAuthPolicy() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		BasePage basePage = new BasePage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		ApiKeyPage apikeyPage = new ApiKeyPage(driver);
		AuthPolicyPage authpolicyPage = new AuthPolicyPage(driver);
		PartnerPolicyMappingPage partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);
		OidcClientPage oidcclientpage = new OidcClientPage(driver);

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authpolicyPage.clickOnFilterButton();
		authpolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULTPOLICYGROUP);
		authpolicyPage.enterPolicyNameInFilter(GlobalConstants.DEACTIVATE_AUTH2);
		authpolicyPage.clickOnApplyFilterButton();
		authpolicyPage.clickOnActionButton();
		assertTrue(authpolicyPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		assertTrue(authpolicyPage.isCloneButtonEnabled(), GlobalConstants.isCloneButtonEnabled);
		authpolicyPage.clickOnDeactivateButton();
		assertFalse(authpolicyPage.isDeactivatePolicyPopupDisplayed(),
				GlobalConstants.isDeactivatePolicyPopupDisplayed);
		authpolicyPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authpolicyPage.clickOnFilterButton();
		authpolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULTPOLICYGROUP);
		authpolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authpolicyPage.clickOnApplyFilterButton();
		authpolicyPage.clickOnActionButton();
		assertTrue(authpolicyPage.isDeactivateButtonEnabled(), GlobalConstants.isDeactivateButtonEnabled);
		authpolicyPage.clickOnDeactivateButton();
		assertTrue(authpolicyPage.isDeactivatePolicyPopupDisplayed(), GlobalConstants.isDeactivatePolicyPopupDisplayed);
		assertTrue(authpolicyPage.isDeactivatePolicyPopupTitleDisplayed(),
				GlobalConstants.isDeactivatePolicyPopupTitleDisplayed);
		assertTrue(authpolicyPage.isDeactivatePolicyInfoMessageDisplayed(),
				GlobalConstants.isDeactivatePolicyInfoMessageDisplayed);
		assertTrue(authpolicyPage.isDeactivateConfirmButtonAvailable(),
				GlobalConstants.isDeactivateConfirmButtonAvailable);
		assertTrue(authpolicyPage.isDeactivateCancelButtonAvailable(),
				GlobalConstants.isDeactivateCancelButtonAvailable);
		authpolicyPage.clickOnDeactivateCancelButton();
		assertTrue(authpolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isListOfPoliciesPageDisplayed);

		logoutFromPartner(dashboardPage);

		loginPage.enterUserName("pmpui-auth");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		dashboardPage.clickOnAuthenticationServicesTitle();
		oidcclientpage.listPageCreateOidcClientButton();
		oidcclientpage.selectPartnerIdDropdown();
		oidcclientpage.enterDeactivePolicyNameInDropdown(GlobalConstants.DEACTIVATE_AUTH2);
		assertTrue(oidcclientpage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		policiesPage.clickOnHomeButton();
		policiesPage.clickOnDataLostProcceedButton();

		dashboardPage.clickOnAuthenticationServicesTitle();
		oidcclientpage.clickOnApiKeyTab();
		apikeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apikeyPage.selectPartnerIdDropdown();
		apikeyPage.enterDeactivePolicyNameInDropdown(GlobalConstants.DEACTIVATE_AUTH2);
		assertTrue(apikeyPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		policiesPage.clickOnHomeButton();
		policiesPage.clickOnDataLostProcceedButton();

		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
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

		logoutFromPartner(dashboardPage);

		loginPage.enterUserName("pmpui-v2");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.ALPHANUMERIC2);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		partnerPolicyMappingPage.clickOnApproveSubmitButton();
		partnerPolicyMappingPage.clickOnFilterResetButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		partnerPolicyMappingPage.clickOnRejectButton();
		partnerPolicyMappingPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnAuthPolicyTab();
		authpolicyPage.clickOnFilterButton();
		authpolicyPage.enterPolicyNameInFilter(GlobalConstants.ALPHANUMERIC2);
		authpolicyPage.clickOnApplyFilterButton();
		authpolicyPage.clickOnActionButton();
		authpolicyPage.clickOnDeactivateButton();
		authpolicyPage.clickOnDeactivateConfirmButton();
		assertTrue(authpolicyPage.isPartnerPolicyLinkActivatedErrorDisplayed(),
				GlobalConstants.isPartnerPolicyLinkActivatedErrorDisplayed);
		authpolicyPage.clickOnAlertErrorOkButton();
		partnerPolicyMappingPage.clickOnFilterResetButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		authpolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY09);
		authpolicyPage.clickOnApplyFilterButton();
		authpolicyPage.clickOnActionButton();
		authpolicyPage.clickOnDeactivateButton();
		authpolicyPage.clickOnDeactivateConfirmButton();
		assertTrue(authpolicyPage.isPartnerPolicyLinkPendingErrorDisplayed(),
				GlobalConstants.isPartnerPolicyLinkPendingErrorDisplayed);
		authpolicyPage.clickOnAlertErrorOkButton();
		partnerPolicyMappingPage.clickOnFilterResetButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		authpolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authpolicyPage.clickOnApplyFilterButton();
		authpolicyPage.clickOnActionButton();
		authpolicyPage.clickOnDeactivateButton();
		authpolicyPage.clickOnDeactivateConfirmButton();
		basePage.navigateBack();
		basePage.navigateForword();

		authpolicyPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		authpolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY06);
		authpolicyPage.clickOnApplyFilterButton();
		assertTrue(authpolicyPage.isPolicyStatusDeactivatedDisplayed(),
				GlobalConstants.isPolicyStatusDeactivatedDisplayed);

		authpolicyPage.clickOnCreateAuthPolicyButton();
		authpolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		authpolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY06);
		authpolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY06_DESCRIPTION);
		authpolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authpolicyPage.clickOnSaveAsDraftButton();
		assertTrue(authpolicyPage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);
	}

	@Test(priority = 22, description = "Deactivate Policy Group")
	public void deactivatePolicyGroup() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharePolicyPage = new DatasharePolicyPage(driver);
		AuthPolicyPage authPolicyPage = new AuthPolicyPage(driver);

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

		policygroupPage.clickOnPolicyGroupNameFilter(GlobalConstants.DEFAULTPOLICYGROUP);
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

		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.enterDeactivatedPolicyGroup(GlobalConstants.DEACTIVATE_POLICYGROUP);
		assertTrue(datasharePolicyPage.isNoDataAvailableDisplayed(), GlobalConstants.isNoDataAvailableDisplayed);
		datasharePolicyPage.clickOnTitleBackIcon();

		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.enterDeactivatedPolicyGroup(GlobalConstants.DEACTIVATE_POLICYGROUP);
		assertTrue(authPolicyPage.isNoDataAvailableDisplayed(), GlobalConstants.isNoDataAvailableDisplayed);
		policygroupPage.navigateBackDefaultButton();

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

	private void createPolicyGroup(PolicyGroupPage policygroupPage, String policyGroupNameValue,
			String policyGroupDescValue) {
		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.enterPolicyGroupName(policyGroupNameValue);
		policygroupPage.enterPolicyGroupNameDescription(policyGroupDescValue);
		policygroupPage.clickOnSubmitButton();
		policygroupPage.clickOnSuccessGoBackButton();

	}

	private void logoutFromPartner(DashboardPage dashboardPage) {
		dashboardPage.clickOnProfileDropdown();
		assertTrue(dashboardPage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);
		LoginPage loginpage = dashboardPage.clickOnLogoutButton();

	}

}
