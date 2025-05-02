package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import io.mosip.testrig.pmprevampui.pages.ApiKeyPage;
import io.mosip.testrig.pmprevampui.pages.AuthPolicyPage;
import io.mosip.testrig.pmprevampui.pages.BasePage;
import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.DatasharePolicyPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.OidcClientPage;
import io.mosip.testrig.pmprevampui.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmprevampui.pages.PoliciesPage;
import io.mosip.testrig.pmprevampui.pages.PolicyGroupPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class PartnerManagerPoliciesTest extends BaseClass {

	@Test(priority = 1, description = "Create Policy Group")
	public void createPolicyGroup() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		PolicyGroupPage policygroupPage = new PolicyGroupPage(driver);
		LoginPage loginPage = new LoginPage(driver);

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
		assertTrue(policygroupPage.isSpecialCharactersAreNotAllowedErrorMessageDisplayed(), GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(policygroupPage.isSpecialCharactersAreNotAllowedErrorMessageDisplayed(), GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
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
		datasharePolicyPage.enterPolicyName(data);
		datasharePolicyPage.enterpolicyDescription(data);
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
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.ALPHANUMERIC);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.ALPHANUMERIC);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		datasharePolicyPage.clickOnGoBackButton();
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
		assertTrue(datasharePolicyPage.isStatusDescISconDisplayed(), GlobalConstants.isStatusDescISconDisplayed);
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
		BasePage basePage = new BasePage(driver);
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

		loginPage.clickOnSomethingWentWrongHomeBtn();
		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnRequestPolicyButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectInvalidPolicyNameDropdown(GlobalConstants.INVALID_DATA);
		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		policiesPage.clickOnHomeButton();
		datasharePolicyPage.clickOnlostWarningProceedButton();

		logoutFromPartner(dashboardPage);

		loginPage.enterUserName("pmpui-revamp");
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

		logoutFromPartner(dashboardPage);

		loginPage.enterUserName("pmpui-auth");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

//		loginPage.clickOnSomethingWentWrongHomeBtn();
		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnRequestPolicyButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(GlobalConstants.DATAPOLICY01);
		policiesPage.enterComments(GlobalConstants.DATAPOLICY01);
		policiesPage.clickSubmitButton();
		policygroupPage.navigateBackDefaultButton();

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

		assertTrue(datasharePolicyPage.isEditPolicyPageTitleDisplayed(), GlobalConstants.isEditPolicyPageTitleDisplayed);
		assertTrue(datasharePolicyPage.isHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(datasharePolicyPage.isSubTitleDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(datasharePolicyPage.isPolicyFormSubTitleDisplayed(),
				GlobalConstants.isPolicyFormSubTitleDisplayed);
		assertTrue(datasharePolicyPage.isPolicyGroupDropdownEnabled(), GlobalConstants.isPolicyGroupDropdownEnabled);
		assertTrue(datasharePolicyPage.isEditPolicyGroupDropdownValueDisplayed(),
				GlobalConstants.isEditPolicyGroupDropdownValueDisplayed);
		assertTrue(datasharePolicyPage.isPolicyNameBoxDisplayed(), GlobalConstants.isPolicyNameBoxDisplayed);
		assertTrue(datasharePolicyPage.isEditPolicyNameValueDisplayed(), GlobalConstants.isEditPolicyNameValueDisplayed);
		assertTrue(datasharePolicyPage.isPolicyDescriptionBoxDisplayed(), GlobalConstants.isPolicyDescriptionBoxDisplayed);
		assertTrue(datasharePolicyPage.isEditPolicyDescriptionValueDisplayed(),
				GlobalConstants.isEditPolicyDescriptionValueDisplayed);
		assertTrue(datasharePolicyPage.isReUploadPolicyDataLabelDisplayed(),
				GlobalConstants.isReUploadPolicyDataLabelDisplayed);
		assertTrue(datasharePolicyPage.isReuploadButtonDisplayed(), GlobalConstants.isReuploadButtonDisplayed);
		assertTrue(datasharePolicyPage.isEditPolicyDataContextDisplayed(), GlobalConstants.isEditPolicyDataContextDisplayed);
		assertTrue(datasharePolicyPage.isEditPolicyClearButtonDisplayed(), GlobalConstants.isEditPolicyClearButtonDisplayed);
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
		assertTrue(datasharePolicyPage.isEditSuccessGoBackButtonEnabled(), GlobalConstants.isEditSuccessGoBackButtonEnabled);
		assertTrue(datasharePolicyPage.isEditSuccessHomeButtonEnabled(), GlobalConstants.isEditSuccessHomeButtonEnabled);
		datasharePolicyPage.clickOnGoBackButton();

		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.Single_CHARACTERS);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnEditButton();
		datasharePolicyPage.enterPolicyName(GlobalConstants.EDITDATAPOLICY1);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.EDITDATAPOLICY1);
		datasharePolicyPage.clickOnEditPolicyFormSubmitButton();
		datasharePolicyPage.clickOnGoBackButton();

		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.EDITDATAPOLICY1);
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
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DEACTIVATE_POLICY);
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

	@Test(priority = 14, description = "Create Auth Policy")
	public void createAuthPolicy() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		BasePage basePage = new BasePage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		AuthPolicyPage authPolicyPage = new AuthPolicyPage(driver);

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();

		assertTrue(authPolicyPage.isPolicyFormSubTitleDisplayed(),
				GlobalConstants.isPolicyFormSubTitleDisplayed);
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
		authPolicyPage.enterpolicyDescription(GlobalConstants.ALPHANUMERIC);
		authPolicyPage.clickOnSaveAsDraftButton();
		assertTrue(authPolicyPage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);
		authPolicyPage.clickOnErrorCloseButton();

	}

//	@Test(priority = 15, description = "Upload Invalid auth Policy Data")
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
		authpolicyPage.enterPolicyName(GlobalConstants.ALPHANUMERIC);
		authpolicyPage.enterpolicyDescription(GlobalConstants.ALPHANUMERIC);
		authpolicyPage.clickOnPolicyClearButton();

		authpolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		authpolicyPage.enterPolicyName(GlobalConstants.ALPHANUMERIC);
		authpolicyPage.enterpolicyDescription(GlobalConstants.ALPHANUMERIC);
		authpolicyPage.uploadPolicyData();
		authpolicyPage.clickOnSaveAsDraftButton();
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
		assertTrue(authPolicyPage.isPolicyStatusDeactivateDisplayed(),
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

		assertTrue(loginpage.isPageNotFoundMessageDisplayed(), GlobalConstants.isKeycloakPageDisplayed);
		BasePage.navigateBack();
		dashboardPage.clickOnProfileDropdown();
		dashboardPage.clickOnLogoutButton();
	}

}
