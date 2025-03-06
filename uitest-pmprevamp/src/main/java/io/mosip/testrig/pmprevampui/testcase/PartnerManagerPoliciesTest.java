package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.pages.AuthPolicyPage;
import io.mosip.testrig.pmprevampui.pages.BasePage;
import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.DatasharePolicyPage;
import io.mosip.testrig.pmprevampui.pages.PoliciesPage;
import io.mosip.testrig.pmprevampui.pages.PolicyGroupPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class PartnerManagerPoliciesTest extends BaseClass {

	@Test(priority = 1, description = "Create Policy Group")
	public void createPolicyGroup() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		PolicyGroupPage policygrouppage = new PolicyGroupPage(driver);

		dashboardpage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);

		policygrouppage.clickOnCreatePolicyGroupButton();
		assertTrue(policygrouppage.isCreatePolicyGroupTitleDisplayed(),
				GlobalConstants.isCreatePolicyGroupTitleDisplayed);
		assertTrue(policygrouppage.isSubTitleDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(policygrouppage.isSubTitleHomeDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(policygrouppage.isPolicyGroupNameTextboxDisplayed(),
				GlobalConstants.isPolicyGroupNameTextboxDisplayed);
		assertTrue(policygrouppage.isPolicyGroupNameDescriptionTextboxDisplayed(),
				GlobalConstants.isPolicyGroupNameDescriptionTextboxDisplayed);
		assertTrue(policygrouppage.isSubmitButtonAvailable(), GlobalConstants.isSubmitButtonAvailable);
		assertFalse(policygrouppage.isSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
		policygrouppage.clickOnClearFormButton();
		policygrouppage.clickOnCancelButton();

		policygrouppage.clickOnCreatePolicyGroupButton();
		policygrouppage.enterPolicyGroupName("Ab12Cd2E4f");
		policygrouppage.enterPolicyGroupNameDescription("Ab12Cd2E4f");
		policygrouppage.clickOnSubmitButton();
		assertTrue(policygrouppage.isPolicyGroupSuccessMessageDisplayed(),
				GlobalConstants.isPolicyGroupSuccessMessageDisplayed);
		assertTrue(policygrouppage.isTitleOfSuccessMessageDisplayed(),
				GlobalConstants.isTitleOfSuccessMessageDisplayed);
		assertTrue(policygrouppage.isSuccessHomeButtonAvailable(), GlobalConstants.isSuccessHomeButtonAvailable);
		assertTrue(policygrouppage.isSuccessGoBackButtonAvailable(), GlobalConstants.isSuccessGoBackButtonAvailable);
		policygrouppage.clickOnSuccessHomeButton();

		dashboardpage.clickOnPolicyButton();
		policygrouppage.clickOnCreatePolicyGroupButton();
		policygrouppage.enterPolicyGroupName("136789");
		policygrouppage.enterPolicyGroupNameDescription("136789");
		policygrouppage.clickOnSubmitButton();
		policygrouppage.clickOnSuccessGoBackButton();

		policygrouppage.clickOnCreatePolicyGroupButton();
		policygrouppage.enterPolicyGroupName("&*#^%@");
		policygrouppage.enterPolicyGroupNameDescription("&*#^%@");
		policygrouppage.clickOnSubmitButton();
		policygrouppage.clickOnSuccessGoBackButton();

		policygrouppage.clickOnCreatePolicyGroupButton();
		policygrouppage.enterPolicyGroupName("A");
		policygrouppage.enterPolicyGroupNameDescription("A");
		policygrouppage.clickOnSubmitButton();
		policygrouppage.clickOnSuccessGoBackButton();

		policygrouppage.clickOnCreatePolicyGroupButton();
		policygrouppage.enterPolicyGroupName("deActIvate");
		policygrouppage.enterPolicyGroupNameDescription("deActIvatepolicy");
		policygrouppage.clickOnSubmitButton();
		policygrouppage.clickOnSuccessGoBackButton();

		policygrouppage.clickOnCreatePolicyGroupButton();
		policygrouppage.enterPolicyGroupName("  ");
		policygrouppage.enterPolicyGroupNameDescription("  ");
		assertTrue(policygrouppage.isSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
		policygrouppage.clickOnSubmitButton();
		policygrouppage.isMissingParameterErrorMessageDisplayed();	
		policygrouppage.clickOnErrorCloseButton();
		
		policygrouppage.enterPolicyGroupName("A");
		policygrouppage.enterPolicyGroupNameDescription("A");
		policygrouppage.clickOnSubmitButton();
		assertTrue(policygrouppage.isSameNamePolicyGroupAlreadyExistMessageDisplayed(),
				GlobalConstants.isSameNamePolicyGroupAlreadyExistMessageDisplayed);
		policygrouppage.clickOnErrorCloseButton();
		policygrouppage.enterPolicyGroupName("abcdf");
		policygrouppage.enterPolicyGroupNameDescription("AbCdEf");
		policygrouppage.clickOnSubmitButton();
		policygrouppage.clickOnSuccessGoBackButton();

		policygrouppage.clickOnCreatePolicyGroupButton();
		policygrouppage.navigateBackDefaultButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);
		policygrouppage.clickOnCreatePolicyGroupButton();
		policygrouppage.enterPolicyGroupName("Advcgd");
		policygrouppage.enterPolicyGroupNameDescription("Ahcgdh");
		policygrouppage.navigateBackDefaultButton();
		assertTrue(policygrouppage.isBrowserBackConfirmationPopupDisplayed(),
				GlobalConstants.isBrowserBackConfirmationPopupDisplayed);
		assertTrue(policygrouppage.isBrowserBackProceedButtonAvailable(),
				GlobalConstants.isBrowserBackProceedButtonAvailable);
		assertTrue(policygrouppage.isBrowserBackCancelButtonAvailable(),
				GlobalConstants.isBrowserBackCancelButtonAvailable);
		policygrouppage.clickOnBrowserBackCancelButton();
		policygrouppage.navigateBackDefaultButton();
		policygrouppage.clickOnBrowserBackProceedButton();

	}

	@Test(priority = 2, description = "Policygroup Tabular View")
	public void policyGroupTabularView() {
		DashboardPage dashboardpage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		PolicyGroupPage policygrouppage = new PolicyGroupPage(driver);

		dashboardpage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);
		assertTrue(policygrouppage.isAuthPolicyTabDisplayed(), GlobalConstants.isAuthPolicyTabDisplayed);
		assertTrue(policygrouppage.isDatasharePolicyTabDisplayed(), GlobalConstants.isDatasharePolicyTabDisplayed);
		assertTrue(policygrouppage.isTitleOfPageDisplayed(), GlobalConstants.isTitleOfPageDisplayed);
		assertTrue(policygrouppage.isBackiconDisplayed(), GlobalConstants.isBackiconDisplayed);
		assertTrue(policygrouppage.isSubTitleHomeDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(policygrouppage.isSubTitleOfPageDisplayed(), GlobalConstants.isSubTitleOfPageDisplayed);
		assertTrue(policygrouppage.isPolicyGroupHeaderTextDisplayed(),
				GlobalConstants.isPolicyGroupHeaderTextDisplayed);
		assertTrue(policygrouppage.isPolicyGroupNameHeaderDisplayed(),
				GlobalConstants.isPolicyGroupNameHeaderDisplayed);
		assertTrue(policygrouppage.isPolicyGroupDescriptionHeaderDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionHeaderDisplayed);
		assertTrue(policygrouppage.isCreatedDateHeaderTextDisplayed(),
				GlobalConstants.isCreatedDateHeaderTextDisplayed);
		assertTrue(policygrouppage.isStatusHeaderTextDisplayed(), GlobalConstants.isStatusHeaderTextDisplayed);
		assertTrue(policygrouppage.isActionHeaderTextDisplayed(), GlobalConstants.isActionHeaderTextDisplayed);

		assertTrue(policygrouppage.isPolicyGroupIdDescIconDisplayed(),
				GlobalConstants.isPolicyGroupIdDescIconDisplayed);
		assertTrue(policygrouppage.isPolicyGroupIdAscIconDisplayed(), GlobalConstants.isPolicyGroupIdAscIconDisplayed);
		assertTrue(policygrouppage.isPolicyGroupNameDescIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(policygrouppage.isPolicyGroupNameAscIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(policygrouppage.isPolicyGroupDescriptionDescIconDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionDescIconDisplayed);
		assertTrue(policygrouppage.isPolicyGroupDescriptionAscIconDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionAscIconDisplayed);
		assertTrue(policygrouppage.isCreatedDateTimeDescISconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(policygrouppage.isCreatedDateTimeAscIconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(policygrouppage.isStatusDescISconDisplayed(), GlobalConstants.isStatusDescISconDisplayed);
		assertTrue(policygrouppage.isStatusAscIconDisplayed(), GlobalConstants.isStatusAscIconDisplayed);

		assertTrue(policygrouppage.isFiletrButtonDisplayedOrEnabled(),
				GlobalConstants.isFiletrButtonDisplayedOrEnabled);
		assertTrue(policygrouppage.isPaginationDisplayed(), GlobalConstants.isPaginationDisplayed);

		policygrouppage.clickOnPolicyGroupList1();
		assertTrue(policygrouppage.isViewPolicyGroupPageTitleDisplayed(),
				GlobalConstants.isViewPolicyGroupPageTitleDisplayed);
		policygrouppage.clickOnPolicyGroupViewBackButton();

		policygrouppage.clickOnFilterButton();
		policygrouppage.clickOnPolicyGroupNameFilter("deActIvate");
		policygrouppage.clickOnApplyFilterButton();
		policygrouppage.clickOnPolicyGroupActionButton();
		assertTrue(policygrouppage.isPolicyGroupViewButtonDisplayed(),
				GlobalConstants.isPolicyGroupViewButtonDisplayed);
		assertTrue(policygrouppage.isPolicyGroupDeactivateButtonDisplayed(),
				GlobalConstants.isPolicyGroupDeactivateButtonDisplayed);
		policygrouppage.clickOnPolicyGroupDeactivateButton();
		policygrouppage.clickOnDeactivateConfirmButton();

		policygrouppage.clickOnFilterButton();
		policygrouppage.clickOnPolicyGroupNameFilter("deActIvate");
		policygrouppage.clickOnApplyFilterButton();
		policygrouppage.clickOnPolicyGroupList1();
		assertFalse(policygrouppage.isViewPolicyGroupPageTitleDisplayed(),
				GlobalConstants.isViewPolicyGroupPageTitleDisplayed);

		policygrouppage.clickOnFilterButton();
		policygrouppage.clickOnPolicyGroupNameFilter("Avutrf");
		policygrouppage.clickOnApplyFilterButton();
		assertTrue(policygrouppage.isNoResultsFoundMessageDisplayed(),
				GlobalConstants.isNoResultsFoundMessageDisplayed);
		policygrouppage.navigateBackDefaultButton();

	}

	@Test(priority = 3, description = "Policygroup Details View")
	public void policyGroupDetailsView() {
		DashboardPage dashboardpage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		PolicyGroupPage policygrouppage = new PolicyGroupPage(driver);
		BasePage basepage = new BasePage(driver);

		dashboardpage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);
		policygrouppage.clickOnFilterButton();
		policygrouppage.clickOnPolicyGroupNameFilter("Ab12Cd2E4f");
		policygrouppage.clickOnApplyFilterButton();
		policygrouppage.clickOnPolicyGroupList1();
		assertTrue(policygrouppage.isViewPolicyGroupPageTitleDisplayed(),
				GlobalConstants.isViewPolicyGroupPageTitleDisplayed);
		assertTrue(policygrouppage.isSubTitleDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(policygrouppage.isSubTitleHomeDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(policygrouppage.isStatusOfPolicyGroupDisplayed(), GlobalConstants.isStatusOfPolicyGroupDisplayed);
		assertTrue(policygrouppage.isPolicyGroupNameLabelDisplayed(), GlobalConstants.isPolicyGroupNameLabelDisplayed);
		assertTrue(policygrouppage.isPolicyGroupNameContextDisplayed(),
				GlobalConstants.isPolicyGroupNameContextDisplayed);
		assertTrue(policygrouppage.isPolicyGroupDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionLabelDisplayed);
		assertTrue(policygrouppage.isPolicyGroupDescriptionContextDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionContextDisplayed);
		policygrouppage.clickOnTitleBackIconButton();

		policygrouppage.clickOnPolicyGroupList1();
		assertTrue(policygrouppage.isHamburgerOptionDisplayed(), GlobalConstants.isHamburgerOptionDisplayed);
		basepage.scrollToEndPage();
		assertTrue(policygrouppage.isMosipRightsTextDisplayed(), GlobalConstants.isMosipRightsTextDisplayed);
		assertTrue(policygrouppage.isFooterDocumentationLinkDisplayed(),
				GlobalConstants.isFooterDocumentationLinkDisplayed);
		assertTrue(policygrouppage.isFooterContactUsLinkDisplayed(), GlobalConstants.isFooterContactUsLinkDisplayed);
		policygrouppage.navigateBackDefaultButton();
	}

	@Test(priority = 4, description = "Create Datashare Policy")
	public void createDatasharePolicy() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		BasePage basepage = new BasePage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		PolicyGroupPage policygrouppage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharepolicypage = new DatasharePolicyPage(driver);

		dashboardpage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);
		assertTrue(policygrouppage.isDatasharePolicyTabDisplayed(), GlobalConstants.isDatasharePolicyTabDisplayed);
		policygrouppage.clickOnDatasharePolicyTab();

		assertTrue(datasharepolicypage.isDataSharePolicyCreateButtonAvailable(),
				GlobalConstants.isDatasharePolicyCreateButtonAvailable);
		datasharepolicypage.clickOnDatasharePolicyCreateButton();
		assertTrue(datasharepolicypage.isCreateDatashareTitleDisplayed(),
				GlobalConstants.isCreateDatashareTitleDisplayed);
		assertTrue(datasharepolicypage.isSubTitleDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(datasharepolicypage.isSubTitleHomeDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(datasharepolicypage.isDatashareFormSubTitleDisplayed(),
				GlobalConstants.isDatashareFormSubTitleDisplayed);
		assertTrue(datasharepolicypage.isPolicyGroupPlaceHolderDisplayed(),
				GlobalConstants.isPolicyGroupPlaceHolderDisplayed);
		datasharepolicypage.clickOnPolicyGroupDropdown();
		assertTrue(datasharepolicypage.isPolicyGroupDropdownSearchInputDisplayed(),
				GlobalConstants.isPolicyGroupDropdownSearchInputDisplayed);
		datasharepolicypage.searchPolicyGroup("automationui policy group");
		assertTrue(datasharepolicypage.isPolicyGroupNameDisplayed(), GlobalConstants.isPolicyGroupNameDisplayed);
		assertTrue(datasharepolicypage.isPolicyGroupDescriptionDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionDisplayed);
		datasharepolicypage.clickOnPolicyGroupDropdown();

		assertTrue(datasharepolicypage.isPolicyNameTextLabelDisplayed(),
				GlobalConstants.isPolicyNameTextLabelDisplayed);
		assertTrue(datasharepolicypage.isPolicyNamePlaceHolderDisplayed(),
				GlobalConstants.isPolicyNamePlaceHolderDisplayed);
		assertTrue(datasharepolicypage.isPolicyDescriptionTextLabelDisplayed(),
				GlobalConstants.isPolicyDescriptionTextLabelDisplayed);
		assertTrue(datasharepolicypage.isPolicyDescriptionPlaceHolderDisplayed(),
				GlobalConstants.isPolicyDescriptionPlaceHolderDisplayed);
		assertTrue(datasharepolicypage.isUploadPolicyDataLabelDisplayed(),
				GlobalConstants.isUploadPolicyDataLabelDisplayed);
		assertTrue(datasharepolicypage.isUploadPolicyDataHelpTextDisplayed(),
				GlobalConstants.isUploadPolicyDataHelpTextDisplayed);
		assertTrue(datasharepolicypage.isFileUploadPlaceHolderDisplayed(),
				GlobalConstants.isFileUploadPlaceHolderDisplayed);
		assertFalse(datasharepolicypage.isPolicyDataBoxEnabled(), GlobalConstants.isPolicyDataBoxEnabled);

		datasharepolicypage.selectPolicyGroup("automationui policy group");
		datasharepolicypage.enterPolicyName(data);
		datasharepolicypage.enterpolicyDescription(data);
		datasharepolicypage.uploadPolicyData();
		assertTrue(datasharepolicypage.isPolicyDataUploadedSuccessMessageDisplayed(),
				GlobalConstants.isPolicyDataUploadedSuccessMessageDisplayed);
		assertTrue(datasharepolicypage.isPolicyDataBoxEnabled(), GlobalConstants.isPolicyDataBoxEnabled);
		assertTrue(datasharepolicypage.isPolicyDataContentDisplayed(), GlobalConstants.isPolicyDataContentDisplayed);
		basepage.scrollToEndPage();
		assertTrue(datasharepolicypage.isSaveAsDraftButtonEnabled(), GlobalConstants.isSaveAsDraftButtonEnabled);
		datasharepolicypage.clickOnSaveAsDraftButton();
		assertTrue(datasharepolicypage.isTitleOfSuccessMessageDisplayed(),
				GlobalConstants.isTitleOfSuccessMessageDisplayed);
		assertTrue(datasharepolicypage.isSubTitleOfSuccessMessageDisplayed(),
				GlobalConstants.isSubTitleOfSuccessMessageDisplayed);
		assertTrue(datasharepolicypage.isSuccessGoBackButtonAvailable(),
				GlobalConstants.isSuccessGoBackButtonAvailable);
		assertTrue(datasharepolicypage.isSuccessHomeButtonAvailable(), GlobalConstants.isSuccessHomeButtonAvailable);
		datasharepolicypage.clickOnSuccessHomeButton();

	}

	@Test(priority = 5, description = "Create Multiple Datashare Policy")
	public void createMultipleDatasharePolicy() {
		DashboardPage dashboardpage = new DashboardPage(driver);
		BasePage basepage = new BasePage(driver);
		PolicyGroupPage policygrouppage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharepolicypage = new DatasharePolicyPage(driver);

		dashboardpage.clickOnPolicyButton();
		policygrouppage.clickOnDatasharePolicyTab();
		datasharepolicypage.clickOnDatasharePolicyCreateButton();
		datasharepolicypage.selectPolicyGroup("automationui policy group");
		datasharepolicypage.enterPolicyName("ab11CD22ef");
		datasharepolicypage.enterpolicyDescription("ab11CD22ef");
		datasharepolicypage.uploadPolicyData();
		basepage.scrollToEndPage();
		datasharepolicypage.clickOnSaveAsDraftButton();
		datasharepolicypage.clickOnGoBackButton();
		datasharepolicypage.clickOnDatashareActionButton();
		assertTrue(datasharepolicypage.isDatasharePublishButtonDisplayed(),
				GlobalConstants.isDatasharePublishButtonDisplayed);
		assertTrue(datasharepolicypage.isDatashareViewButtonDisplayed(),
				GlobalConstants.isDatashareViewButtonDisplayed);
		assertTrue(datasharepolicypage.isDatashareCloneButtonDisplayed(),
				GlobalConstants.isDatashareCloneButtonDisplayed);
		assertTrue(datasharepolicypage.isDatashareDeactivateButtonDisplayed(),
				GlobalConstants.isDatashareDeactivateButtonDisplayed);

		datasharepolicypage.clickOnDatasharePublishButton();
		assertTrue(datasharepolicypage.isPublishConfirmationPopupDisplayed(),
				GlobalConstants.isPublishConfirmationPopupDisplayed);
		assertTrue(datasharepolicypage.isPublishPolicyInfoMessageisplayed(),
				GlobalConstants.isPublishPolicyInfoMessageisplayed);
		assertTrue(datasharepolicypage.isPublishPolicyCancelButtonDisplayed(),
				GlobalConstants.isPublishPolicyCancelButtonDisplayed);
		assertTrue(datasharepolicypage.isPublishPolicyButtonDisplayed(),
				GlobalConstants.isPublishPolicyButtonDisplayed);
		datasharepolicypage.clickOnPublishPolicyButton();
		datasharepolicypage.clickOnPublishPolicyCloseButton();

		datasharepolicypage.clickOnDatasharePolicyCreateButton();
		datasharepolicypage.selectPolicyGroup("automationui policy group");
		datasharepolicypage.enterPolicyName("deactivate");
		datasharepolicypage.enterpolicyDescription("deactivate");
		datasharepolicypage.uploadPolicyData();
		basepage.scrollToEndPage();
		datasharepolicypage.clickOnSaveAsDraftButton();
		datasharepolicypage.clickOnGoBackButton();
		datasharepolicypage.clickOnDatashareActionButton();
		datasharepolicypage.clickOnDatasharePublishButton();
		datasharepolicypage.clickOnPublishPolicyButton();
		datasharepolicypage.clickOnPublishPolicyCloseButton();
		datasharepolicypage.clickOnDatashareActionButton();
		datasharepolicypage.clickOnDatashareDeactivateButton();
		datasharepolicypage.clickOnDeactivatePolicyConfirmButton();

	}

	@Test(priority = 6, description = "Create Duplicate Datashare Policy")
	public void createDuplicateDatasharePolicy() {
		DashboardPage dashboardpage = new DashboardPage(driver);
		PolicyGroupPage policygrouppage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharepolicypage = new DatasharePolicyPage(driver);

		dashboardpage.clickOnPolicyButton();
		policygrouppage.clickOnDatasharePolicyTab();
		datasharepolicypage.clickOnDatasharePolicyCreateButton();
		datasharepolicypage.selectPolicyGroup("automationui policy group");
		datasharepolicypage.enterPolicyName("ab11CD22ef");
		datasharepolicypage.enterpolicyDescription("ab11CD22ef");
		datasharepolicypage.uploadPolicyData();
		datasharepolicypage.clickOnSaveAsDraftButton();
		assertTrue(datasharepolicypage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);
		datasharepolicypage.clickOnErrorCloseButton();
		datasharepolicypage.enterPolicyName("deactivate");
		datasharepolicypage.enterpolicyDescription("deactivate");
		datasharepolicypage.clickOnSaveAsDraftButton();
		assertTrue(datasharepolicypage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);
		datasharepolicypage.clickOnErrorCloseButton();
		datasharepolicypage.enterPolicyName("#$@^#&");
		datasharepolicypage.enterpolicyDescription("#$@^#&");
		datasharepolicypage.uploadPolicyData();
		datasharepolicypage.clickOnSaveAsDraftButton();
		datasharepolicypage.clickOnGoBackButton();

		datasharepolicypage.clickOnDatasharePolicyCreateButton();
		datasharepolicypage.selectPolicyGroup("automationui policy group");
		datasharepolicypage.enterPolicyName("#$@^#&");
		datasharepolicypage.enterpolicyDescription("     ");
		datasharepolicypage.uploadPolicyData();
		assertFalse(datasharepolicypage.isSaveAsDraftButtonEnabled(), GlobalConstants.isSaveAsDraftButtonEnabled);
		assertTrue(datasharepolicypage.isClearFormDisplayed(), GlobalConstants.isClearFormDisplayed);
		datasharepolicypage.clickOnClearForm();
		datasharepolicypage.selectPolicyGroup("automationui policy group");
		datasharepolicypage.enterPolicyName("#$@^#&");
		datasharepolicypage.enterpolicyDescription("     ");
		datasharepolicypage.uploadPolicyData();
		assertTrue(datasharepolicypage.isCancelFormDisplayed(), GlobalConstants.isCancelFormDisplayed);
		datasharepolicypage.clickOnCancelForm();
		assertTrue(datasharepolicypage.isDataLostWarningMessageDisplayed(),
				GlobalConstants.isDataLostWarningMessageDisplayed);
		datasharepolicypage.clickOnlostWarningCancelButton();
		datasharepolicypage.clickOnCancelForm();
		datasharepolicypage.clickOnlostWarningCancelButton();
		datasharepolicypage.clickOnCancelForm();
		datasharepolicypage.clickOnlostWarningProceedButton();
		assertTrue(policygrouppage.isDatasharePolicyTabDisplayed(), GlobalConstants.isDatasharePolicyTabDisplayed);

	}

	@Test(priority = 7, description = "Upload Invalid Policy Data")
	public void uploadInvalidPolicyData() {
		DashboardPage dashboardpage = new DashboardPage(driver);
		BasePage basepage = new BasePage(driver);
		PolicyGroupPage policygrouppage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharepolicypage = new DatasharePolicyPage(driver);

		dashboardpage.clickOnPolicyButton();
		policygrouppage.clickOnDatasharePolicyTab();
		datasharepolicypage.clickOnDatasharePolicyCreateButton();
		datasharepolicypage.selectPolicyGroup("automationui policy group");
		datasharepolicypage.enterPolicyName("abCDef");
		datasharepolicypage.enterpolicyDescription("abCDef");
		datasharepolicypage.uploadExceedPolicyData();
		basepage.scrollToEndPage();
		datasharepolicypage.clickOnSaveAsDraftButton();
		basepage.scrollToStartPage();
		assertTrue(datasharepolicypage.isPolicyDataExceedChractersMessageDisplayed(),
				GlobalConstants.isPolicyDataExceedChractersMessageDisplayed);
		datasharepolicypage.clickOnErrorCloseButton();
		datasharepolicypage.uploadInvalidPolicyData();
		assertTrue(datasharepolicypage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		datasharepolicypage.clickOnErrorCloseButton();
		datasharepolicypage.uploadPolicyData();
		datasharepolicypage.clickOnSaveAsDraftButton();

	}

	@Test(priority = 8, description = "Datashare Policy Tabular View")
	public void datasharePolicyTabularView() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		BasePage basepage = new BasePage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		PolicyGroupPage policygrouppage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharepolicypage = new DatasharePolicyPage(driver);

		dashboardpage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);
		assertTrue(policiesPage.isPoliciesAuthPolicyTabDisplayed(), GlobalConstants.isPoliciesAuthPolicyTabDisplayed);
		assertTrue(policygrouppage.isDatasharePolicyTabDisplayed(), GlobalConstants.isDatasharePolicyTabDisplayed);
		policygrouppage.clickOnDatasharePolicyTab();
		assertTrue(datasharepolicypage.isSubTitleOfTabularViewDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewDisplayed);

		assertTrue(datasharepolicypage.isPolicyIdHeaderTextDisplayed(), GlobalConstants.isPolicyIdHeaderTextDisplayed);
		assertTrue(datasharepolicypage.isPolicyNameHeaderTextDisplayed(),
				GlobalConstants.isPolicyNameHeaderTextDisplayed);
		assertTrue(datasharepolicypage.isPolicyDescriptionHeaderTextDisplayed(),
				GlobalConstants.isPolicyDescriptionHeaderTextDisplayed);
		assertTrue(datasharepolicypage.isPolicyGroupHeaderTextDisplayed(),
				GlobalConstants.isPolicyGroupHeaderTextDisplayed);
		assertTrue(datasharepolicypage.isCreatedDateHeaderTextDisplayed(),
				GlobalConstants.isCreatedDateHeaderTextDisplayed);
		assertTrue(datasharepolicypage.isStatusHeaderTextDisplayed(), GlobalConstants.isStatusHeaderTextDisplayed);
		assertTrue(datasharepolicypage.isActionHeaderTextDisplayed(), GlobalConstants.isActionHeaderTextDisplayed);
		assertTrue(datasharepolicypage.isPolicyIdAscIconDisplayed(), GlobalConstants.isPolicyIdAscIconDisplayed);
		assertTrue(datasharepolicypage.isPolicyIdDescIconDisplayed(), GlobalConstants.isPolicyIdDescIconDisplayed);
		assertTrue(datasharepolicypage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameAscIconDisplayed);
		assertTrue(datasharepolicypage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescIconDisplayed);
		assertTrue(datasharepolicypage.isPolicyDescriptionAscIconDisplayed(),
				GlobalConstants.isPolicyDescriptionAscIconDisplayed);
		assertTrue(datasharepolicypage.isPolicyGroupAscIconDisplayed(), GlobalConstants.isPolicyGroupAscIconDisplayed);
		assertTrue(datasharepolicypage.isPolicyGroupDescIconDisplayed(),
				GlobalConstants.isPolicyGroupDescIconDisplayed);
		assertTrue(datasharepolicypage.isPolicyDescriptionDescIconDisplayed(),
				GlobalConstants.isPolicyDescriptionDescIconDisplayed);
		assertTrue(datasharepolicypage.isCreatedDateTimeDescISconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(datasharepolicypage.isCreatedDateTimeAscIconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(datasharepolicypage.isStatusDescISconDisplayed(), GlobalConstants.isStatusDescISconDisplayed);
		assertTrue(datasharepolicypage.isStatusAscIconDisplayed(), GlobalConstants.isStatusAscIconDisplayed);

		datasharepolicypage.clickOnFilterButton();
		assertTrue(datasharepolicypage.isFilterResetButtonEnabled(), GlobalConstants.isFilterResetButtonEnabled);
		assertFalse(datasharepolicypage.isFiletrButtonDisplayedOrEnabled(),
				GlobalConstants.isFiletrButtonDisplayedOrEnabled);
		datasharepolicypage.clickOnPolicyGroupFilter("automationui policy group");
		datasharepolicypage.clickOnPolicyNameFilter("ab11CD22ef");
		datasharepolicypage.clickOnApplyFilterButton();
		assertFalse(datasharepolicypage.isPolicyStatusDeactivateDisplayed(),
				GlobalConstants.isPolicyStatusDeactivateDisplayed);
		assertTrue(datasharepolicypage.isPolicyStatusActivateDisplayed(),
				GlobalConstants.isPolicyStatusActivateDisplayed);
		datasharepolicypage.clickOnDatasharePolicyList1();
		assertTrue(datasharepolicypage.isViewDatasharePolicyPageTitleDisplayed(),
				GlobalConstants.isViewDatasharePolicyPageTitleDisplayed);
		datasharepolicypage.clickOnDatashareViewBackButton();
		
		datasharepolicypage.clickOnFilterButton();
		datasharepolicypage.clickOnPolicyGroupFilter("automationui policy group");
		datasharepolicypage.selectDeactivateStatusFilter();
		datasharepolicypage.clickOnPolicyNameFilter("deactivate");	
		datasharepolicypage.clickOnApplyFilterButton();
		assertTrue(datasharepolicypage.isPolicyStatusDeactivateDisplayed(),
				GlobalConstants.isPolicyStatusDeactivateDisplayed);
		datasharepolicypage.clickOnDeactivatedPolicy();
		assertFalse(datasharepolicypage.isViewDatasharePolicyPageTitleDisplayed(),
				GlobalConstants.isViewDatasharePolicyPageTitleDisplayed);
		
		datasharepolicypage.clickOnFilterResetButton();
		dashboardpage.clickOnPolicyButton();
		policygrouppage.clickOnDatasharePolicyTab();
		datasharepolicypage.clickOnFilterButton();
		assertFalse(datasharepolicypage.isApplyFilterButtonEnabled(), GlobalConstants.isApplyFilterButtonEnabled);
		datasharepolicypage.clickOnPolicyGroupFilter("sdcgsvsfvds");
		datasharepolicypage.clickOnApplyFilterButton();
		assertTrue(datasharepolicypage.isNoResultsFoundMessageDisplayed(),
				GlobalConstants.isNoResultsFoundMessageDisplayed);
		datasharepolicypage.clickOnPolicyGroupFilter("automationui policy group");
		datasharepolicypage.clickOnApplyFilterButton();
		datasharepolicypage.clickOnPolicyIdAscIcon();
		datasharepolicypage.clickOnPolicyIdDescIcon();
		datasharepolicypage.clickOnPolicyNameAscIcon();
		datasharepolicypage.clickOnPolicyNameDescIcon();
		datasharepolicypage.clickOnPolicyDescriptionAscIcon();
		datasharepolicypage.clickOnPolicyDescriptionDescIcon();
		datasharepolicypage.clickOnPolicyGroupNameAscIcon();
		datasharepolicypage.clickOnPolicyGroupNameDescIcon();
		datasharepolicypage.clickOnCreationDateAscIcon();
		datasharepolicypage.clickOnCreationDateDescIcon();
		datasharepolicypage.clickOnStatusAscIcon();
		datasharepolicypage.clickOnStatusDescIcon();
		
		basepage.scrollToEndPage();
		assertTrue(datasharepolicypage.isPaginationDisplayed(), GlobalConstants.isPaginationDisplayed);
		assertTrue(datasharepolicypage.isMosipRightsTextDisplayed(), GlobalConstants.isMosipRightsTextDisplayed);
		assertTrue(datasharepolicypage.isFooterDocumentationLinkDisplayed(),
				GlobalConstants.isFooterDocumentationLinkDisplayed);
		assertTrue(datasharepolicypage.isFooterContactUsLinkDisplayed(),
				GlobalConstants.isFooterContactUsLinkDisplayed);
		policygrouppage.navigateBackDefaultButton();

	}

	@Test(priority = 9, description = "View Datashare Policy Details")
	public void viewDatasharePolicyDetails() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		BasePage basepage = new BasePage(driver);
		PolicyGroupPage policygrouppage = new PolicyGroupPage(driver);
		DatasharePolicyPage datasharepolicypage = new DatasharePolicyPage(driver);

		dashboardpage.clickOnPolicyButton();
		policygrouppage.clickOnDatasharePolicyTab();
		datasharepolicypage.clickOnFilterButton();
		datasharepolicypage.selectActivateStatusFilter();
		datasharepolicypage.clickOnApplyFilterButton();
		datasharepolicypage.clickOnDatasharePolicyList1();
		assertTrue(datasharepolicypage.isViewDatasharePolicyPageTitleDisplayed(),
				GlobalConstants.isViewDatasharePolicyPageTitleDisplayed);
		datasharepolicypage.clickOnDatashareViewBackButton();

		datasharepolicypage.clickOnFilterButton();
		datasharepolicypage.clickOnPolicyNameFilter("ab11CD22ef");
		datasharepolicypage.clickOnApplyFilterButton();
		datasharepolicypage.clickOnDatashareActionButton();
		datasharepolicypage.clickOnDatashareViewButton();

		assertTrue(datasharepolicypage.isViewDatasharePolicyPageTitleDisplayed(),
				GlobalConstants.isViewDatasharePolicyPageTitleDisplayed);
		assertTrue(datasharepolicypage.isSubtitleButtonDisplayed(), GlobalConstants.isSubtitleButtonDisplayed);
		assertTrue(datasharepolicypage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertTrue(datasharepolicypage.isPolicyIdLabelDisplayed(), GlobalConstants.isPolicyIdLabelDisplayed);
//		assertTrue(datasharepolicypage.isPolicyIdContextDisplayed(), GlobalConstants.isPolicyIdContextDisplayed);
		assertTrue(datasharepolicypage.isPolicyNameLabelDisplayed(), GlobalConstants.isPolicyNameLabelDisplayed);
		assertTrue(datasharepolicypage.isPolicyNameContextDisplayed(), GlobalConstants.isPolicyNameContextDisplayed);
		assertTrue(datasharepolicypage.isPolicyGroupLabelDisplayed(), GlobalConstants.isPolicyGroupLabelDisplayed);
		assertTrue(datasharepolicypage.isPolicyGroupContextDisplayed(), GlobalConstants.isPolicyGroupContextDisplayed);
		assertTrue(datasharepolicypage.isPolicyDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyDescriptionLabelDisplayed);
		assertTrue(datasharepolicypage.isPolicyDescriptionContextDisplayed(),
				GlobalConstants.isPolicyDescriptionContextDisplayed);
		assertTrue(datasharepolicypage.isPolicyGroupDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionLabelDisplayed);
		assertTrue(datasharepolicypage.isPolicyGroupDescriptionContextDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionContextDisplayed);
		assertTrue(datasharepolicypage.isPolicyDataLabelDisplayed(), GlobalConstants.isPolicyDataLabelDisplayed);
		assertTrue(datasharepolicypage.isPolicyDataTitleDisplayed(), GlobalConstants.isPolicyDataTitleDisplayed);
		assertTrue(datasharepolicypage.isPolicyDataPreviewDisplayed(), GlobalConstants.isPolicyDataPreviewDisplayed);

		datasharepolicypage.clickOnPolicyDataPreviewButton();
		assertTrue(datasharepolicypage.ispolicyDataPopupDisplayed(), GlobalConstants.ispolicyDataPopupDisplayed);
		assertTrue(datasharepolicypage.isDownloadButtonDisplayed(), GlobalConstants.isDownloadButtonDisplayed);
		assertTrue(datasharepolicypage.isCloseButtonDisplayed(), GlobalConstants.isCloseButtonDisplayed);
		datasharepolicypage.clickOnDownloadButton();
		datasharepolicypage.clickOnCloseButton();
		policygrouppage.navigateBackDefaultButton();
		
		basepage.scrollToEndPage();
		assertTrue(datasharepolicypage.isMosipRightsTextDisplayed(), GlobalConstants.isMosipRightsTextDisplayed);
		assertTrue(datasharepolicypage.isFooterDocumentationLinkDisplayed(),
				GlobalConstants.isFooterDocumentationLinkDisplayed);
		assertTrue(datasharepolicypage.isFooterContactUsLinkDisplayed(),
				GlobalConstants.isFooterContactUsLinkDisplayed);
	}

	@Test(priority = 10, description = "Create Auth Policy")
	public void createAuthPolicy() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		BasePage basepage = new BasePage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		AuthPolicyPage authpolicypage = new AuthPolicyPage(driver);

		dashboardpage.clickOnPolicyButton();
		policiesPage.clickOnpoliciesAuthPolicyTab();
		authpolicypage.clickOnCreateAuthPolicyButton();

		assertTrue(authpolicypage.isAuthPolicyFormSubTitleDisplayed(),
				GlobalConstants.isAuthPolicyFormSubTitleDisplayed);
		assertTrue(authpolicypage.isPolicyGroupDropdownDisplayed(), GlobalConstants.isPolicyGroupDropdownDisplayed);
		authpolicypage.clickOnPolicyGroupDropdown();
		assertTrue(authpolicypage.isPolicyGroupDropdownSearchInputDisplayed(),
				GlobalConstants.isPolicyGroupDropdownSearchInputDisplayed);
		assertTrue(authpolicypage.isPolicyGroupPlaceHolderDisplayed(),
				GlobalConstants.isPolicyGroupPlaceHolderDisplayed);
		authpolicypage.clickOnPolicyGroupDropdown();
		assertTrue(authpolicypage.isAuthPolicyPlaceHolderDisplayed(), GlobalConstants.isAuthPolicyPlaceHolderDisplayed);
		assertTrue(authpolicypage.isPolicyDescriptionPlaceHolderDisplayed(),
				GlobalConstants.isPolicyDescriptionPlaceHolderDisplayed);
		assertTrue(authpolicypage.isUploadPolicyDataLabelDisplayed(), GlobalConstants.isUploadPolicyDataLabelDisplayed);
		assertTrue(authpolicypage.isUploadPolicyDataHelpTextDisplayed(),
				GlobalConstants.isUploadPolicyDataHelpTextDisplayed);
		assertTrue(authpolicypage.isFileUploadPlaceHolderDisplayed(), GlobalConstants.isFileUploadPlaceHolderDisplayed);
		assertFalse(authpolicypage.isPolicyDataBoxEnabled(), GlobalConstants.isPolicyDataBoxEnabled);

		authpolicypage.selectPolicyGroup("automationui policy group");
		assertTrue(authpolicypage.isPolicyGroupNameDisplayed(), GlobalConstants.isPolicyGroupNameDisplayed);
		assertTrue(authpolicypage.isPolicyGroupDescriptionDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionDisplayed);
		authpolicypage.clickOnPolicyGroupDropdown();
		
		authpolicypage.selectpolicyGroupDropdown("automationui policy group");
		authpolicypage.enterPolicyName("authpolicy01");
		authpolicypage.enterpolicyDescription("auth policy 01");
		authpolicypage.uploadPolicyData();
		authpolicypage.clickOnCreatePolicyFormSubmitButton();
		authpolicypage.clickOnGoBackButton();
		authpolicypage.clickOnFilterButton();
		authpolicypage.enterPolicyNameInFilter("authpolicy01");
		authpolicypage.clickOnApplyFilterButton();
		authpolicypage.clickOnPoliciesListViewElipsisButton();
		authpolicypage.clickOnPolicyPublishButton();
		authpolicypage.clickOnPublishPolicyButton();
		authpolicypage.clickOnSuccessMsgCloseButton();
		authpolicypage.clickOnPublishPolicyCloseButton();
		
		authpolicypage.clickOnCreateAuthPolicyButton();
		authpolicypage.selectpolicyGroupDropdown("automationui policy group");
		authpolicypage.enterPolicyName("deactivate auth");
		authpolicypage.enterpolicyDescription("deactivate auth policy");
		authpolicypage.uploadPolicyData();
		authpolicypage.clickOnCreatePolicyFormSubmitButton();
		authpolicypage.clickOnGoBackButton();
		authpolicypage.clickOnFilterButton();
		authpolicypage.enterPolicyNameInFilter("deactivate auth");
		authpolicypage.clickOnApplyFilterButton();
		authpolicypage.clickOnPoliciesListViewElipsisButton();
		authpolicypage.clickOnPolicyPublishButton();
		authpolicypage.clickOnPublishPolicyButton();
		authpolicypage.clickOnSuccessMsgCloseButton();
		authpolicypage.clickOnPublishPolicyCloseButton();
		authpolicypage.clickOnPoliciesListViewElipsisButton();
		authpolicypage.clickOnDeactivateButton();
		authpolicypage.clickOnDeactivateConfirmButton();
		
		authpolicypage.clickOnCreateAuthPolicyButton();
		authpolicypage.selectPolicyGroup("deActIvate");
		assertTrue(authpolicypage.isNoDataAvailableDisplayed(), GlobalConstants.isNoDataAvailableDisplayed);
		authpolicypage.clickOnPolicyGroupDropdown();
		authpolicypage.selectpolicyGroupDropdown("automationui policy group");
		authpolicypage.enterPolicyName("#@%#*&*#");
		authpolicypage.enterpolicyDescription("#@%#*&*#");
		authpolicypage.uploadPolicyData();
		assertTrue(authpolicypage.isPolicyDataUploadedSuccessMessageDisplayed(),
				GlobalConstants.isPolicyDataUploadedSuccessMessageDisplayed);
		assertTrue(authpolicypage.isPolicyDataBoxEnabled(), GlobalConstants.isPolicyDataBoxEnabled);
		assertTrue(authpolicypage.isPolicyDataContentDisplayed(), GlobalConstants.isPolicyDataContentDisplayed);
		authpolicypage.editPolicyData("vcgdytdsgvdshaccggv");
		assertTrue(authpolicypage.isPolicyDataEdited(), GlobalConstants.isPolicyDataEdited);
		authpolicypage.uploadPolicyData();
		basepage.scrollToEndPage();
		authpolicypage.clickOnCreatePolicyFormSubmitButton();
		authpolicypage.clickOnGoBackButton();

		authpolicypage.clickOnCreateAuthPolicyButton();
		authpolicypage.selectpolicyGroupDropdown("automationui policy group");
		authpolicypage.enterPolicyName("authpolicy01");
		authpolicypage.enterpolicyDescription("auth policy 01");
		authpolicypage.uploadPolicyData();
		authpolicypage.clickOnCreatePolicyFormSubmitButton();
		assertTrue(authpolicypage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);
		authpolicypage.clickOnErrorCloseButton();
		authpolicypage.enterpolicyDescription("auth546353");
		authpolicypage.clickOnCreatePolicyFormSubmitButton();
		assertTrue(authpolicypage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);
		authpolicypage.clickOnErrorCloseButton();
	}

	@Test(priority = 11, description = "Upload Invalid auth Policy Data")
	public void uploadInvalidAuthPolicyData() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		BasePage basepage = new BasePage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		AuthPolicyPage authpolicypage = new AuthPolicyPage(driver);
		
		dashboardpage.clickOnPolicyButton();
		policiesPage.clickOnpoliciesAuthPolicyTab();
		authpolicypage.clickOnCreateAuthPolicyButton();
		authpolicypage.selectpolicyGroupDropdown("automationui policy group");
		authpolicypage.enterPolicyName("deactivate auth");
		authpolicypage.enterpolicyDescription("deactivate auth policy");
		authpolicypage.uploadBlankData();
		assertTrue(authpolicypage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		authpolicypage.clickOnErrorCloseButton();
		authpolicypage.uploadInvalidPolicyData();
		assertTrue(authpolicypage.isProvideValidJsonDataErrorMessageDisplayed(),
				GlobalConstants.isProvideValidJsonDataErrorMessageDisplayed);
		authpolicypage.clickOnErrorCloseButton();
		authpolicypage.uploadExceedData();
		basepage.scrollToEndPage();
		authpolicypage.clickOnCreatePolicyFormSubmitButton();
		basepage.scrollToStartPage();
		assertTrue(authpolicypage.isPolicyDataExceedChractersMessageDisplayed(),
				GlobalConstants.isPolicyDataExceedChractersMessageDisplayed);
		authpolicypage.clickOnErrorCloseButton();
		authpolicypage.uploadPolicyData();
		authpolicypage.clickOnCreatePolicyFormSubmitButton();
		assertTrue(authpolicypage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);
		authpolicypage.clickOnErrorCloseButton();

	}

	@Test(priority = 12, description = "Auth Policy Tabular View")
	public void authPolicyTabularView() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		BasePage basepage = new BasePage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		AuthPolicyPage authpolicypage = new AuthPolicyPage(driver);

		dashboardpage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesAuthPolicyTabDisplayed(), GlobalConstants.isPoliciesAuthPolicyTabDisplayed);
		policiesPage.clickOnpoliciesAuthPolicyTab();

		assertTrue(authpolicypage.isPolicyIdHeaderTextDisplayed(), GlobalConstants.isPolicyIdHeaderTextDisplayed);
		assertTrue(authpolicypage.isPolicyNameHeaderTextDisplayed(), GlobalConstants.isPolicyNameHeaderTextDisplayed);
		assertTrue(authpolicypage.isPolicyDescriptionHeaderTextDisplayed(),
				GlobalConstants.isPolicyDescriptionHeaderTextDisplayed);
		assertTrue(authpolicypage.isPolicyGroupHeaderTextDisplayed(), GlobalConstants.isPolicyGroupHeaderTextDisplayed);
		assertTrue(authpolicypage.isCreatedDateHeaderTextDisplayed(), GlobalConstants.isCreatedDateHeaderTextDisplayed);
		assertTrue(authpolicypage.isStatusHeaderTextDisplayed(), GlobalConstants.isStatusHeaderTextDisplayed);

		assertFalse(authpolicypage.isFilterResetButtonAvailableOrEnabled(),
				GlobalConstants.isFilterResetButtonAvailableOrEnabled);
		authpolicypage.clickOnFilterButton();
		assertFalse(authpolicypage.isFiletrButtonDisplayedOrEnabled(),
				GlobalConstants.isFiletrButtonDisplayedOrEnabled);
		assertTrue(authpolicypage.isPolicyIdFilterLabelDisplayed(), GlobalConstants.isPolicyIdFilterLabelDisplayed);
		assertTrue(authpolicypage.isPolicyNameFilterLabelDisplayed(), GlobalConstants.isPolicyNameFilterLabelDisplayed);
		assertTrue(authpolicypage.isPolicyDescriptionFilterLabelDisplayed(),
				GlobalConstants.isPolicyDescriptionFilterLabelDisplayed);
		assertTrue(authpolicypage.isPolicyGroupFilterLabelDisplayed(),
				GlobalConstants.isPolicyGroupFilterLabelDisplayed);
		assertTrue(authpolicypage.isStatusFilterLabelDisplayed(), GlobalConstants.isStatusFilterLabelDisplayed);

		assertTrue(authpolicypage.isPolicyIdFilterPlaceHolderDisplayed(),
				GlobalConstants.isPolicyIdFilterPlaceHolderDisplayed);
		assertTrue(authpolicypage.isPolicyNameFilterPlaceHolderDisplayed(),
				GlobalConstants.isPolicyNameFilterPlaceHolderDisplayed);
		assertTrue(authpolicypage.isPolicyGroupDescriptionFilterPlaceHolderDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionFilterPlaceHolderDisplayed);
		assertTrue(authpolicypage.isPolicyGroupFilterPlaceHolderDisplayed(),
				GlobalConstants.isPolicyGroupFilterPlaceHolderDisplayed);
		assertTrue(authpolicypage.isStatusFilterPlaceHolderDisplayed(),
				GlobalConstants.isStatusFilterPlaceHolderDisplayed);

		authpolicypage.enterpolicyGroupFilterBox("automationui policy group");
		authpolicypage.selectActivateStatusFilter();
		authpolicypage.clickOnApplyFilterButton();
		assertTrue(authpolicypage.isPolicyStatusActivateDisplayed(), GlobalConstants.isPolicyStatusActivateDisplayed);
		authpolicypage.clickOnActivatedAuthPolicy();
		assertTrue(authpolicypage.isPolicyViewPageTitleDisplayed(), GlobalConstants.isPolicyViewPageTitleDisplayed);
		authpolicypage.clickOnViewBackButton();

		authpolicypage.clickOnFilterButton();
		authpolicypage.enterpolicyGroupFilterBox("automationui policy group");
		authpolicypage.selectActivateStatusFilter();
		authpolicypage.clickOnApplyFilterButton();
		authpolicypage.clickOnPoliciesListViewElipsisButton();
		assertTrue(authpolicypage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		assertTrue(authpolicypage.isCloneButtonDisplayed(), GlobalConstants.isCloneButtonDisplayed);
		assertTrue(authpolicypage.isDeactivateButtonDisplayed(), GlobalConstants.isDeactivateButtonDisplayed);		
		
		authpolicypage.enterpolicyGroupFilterBox("automationui policy group");
		authpolicypage.selectDeactivateStatusFilter();
		authpolicypage.enterPolicyNameInFilter("deactivate auth");
		authpolicypage.clickOnApplyFilterButton();
		assertTrue(authpolicypage.isPolicyStatusDeactivateDisplayed(),
				GlobalConstants.isPolicyStatusDeactivateDisplayed);
		assertFalse(authpolicypage.isPolicyViewPageTitleDisplayed(), GlobalConstants.isPolicyViewPageTitleDisplayed);
		authpolicypage.clickOnPoliciesListViewElipsisButton();
		assertTrue(authpolicypage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		assertTrue(authpolicypage.isCloneButtonDisplayed(), GlobalConstants.isCloneButtonDisplayed);
		authpolicypage.clickOnPolicyGroupCloseButton();
		authpolicypage.clickOnPolicyNameCloseButton();
		authpolicypage.clickOnSelectStatusButton();	
		assertFalse(authpolicypage.isApplyFilterButtonEnabled(), GlobalConstants.isApplyFilterButtonEnabled);
		
		authpolicypage.enterpolicyGroupFilterBox("automat6384376r36");
		authpolicypage.clickOnApplyFilterButton();
		assertTrue(authpolicypage.isNoResultsFoundMessageDisplayed(), GlobalConstants.isNoResultsFoundMessageDisplayed);
		authpolicypage.clickOnPolicyGroupCloseButton();
		
		authpolicypage.selectActivateStatusFilter();
		authpolicypage.clickOnApplyFilterButton();
		basepage.scrollToEndPage();
		assertTrue(authpolicypage.isPaginationDisplayed(), GlobalConstants.isPaginationDisplayed);
		assertTrue(authpolicypage.isPreviusPageButtonDisplayed(), GlobalConstants.isPreviusPageButtonDisplayed);
		assertTrue(authpolicypage.isNextPageButtonDisplayed(), GlobalConstants.isNextPageButtonDisplayed);
		assertTrue(authpolicypage.isPage1Displayed(), GlobalConstants.isPage1Displayed);
		authpolicypage.clickOnNextPageButton();
		assertTrue(authpolicypage.isPage2Displayed(), GlobalConstants.isPage2Displayed);
		authpolicypage.clickOnPreviusPageButton();
		assertTrue(authpolicypage.isPage1Displayed(), GlobalConstants.isPage1Displayed);

		assertTrue(authpolicypage.isPrefixOfPageDisplayed(), GlobalConstants.isPrefixOfPageDisplayed);
		assertTrue(authpolicypage.isRecordPerPageDisplayed(), GlobalConstants.isRecordPerPageDisplayed);
		assertTrue(authpolicypage.isItemPerPage8Displayed(), GlobalConstants.isItemPerPage8Displayed);
		assertTrue(authpolicypage.isexpandIconDisplayed(), GlobalConstants.isexpandIconDisplayed);
		authpolicypage.selectItemPerPageNumber();
		assertTrue(authpolicypage.isItemPerPage16Displayed(), GlobalConstants.isItemPerPage16Displayed);
	}

	@Test(priority = 13, description = "Auth Policy Details View")
	public void authPolicyDetailsView() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		AuthPolicyPage authpolicypage = new AuthPolicyPage(driver);

		dashboardpage.clickOnPolicyButton();
		policiesPage.clickOnpoliciesAuthPolicyTab();
		authpolicypage.clickOnFilterButton();
		authpolicypage.enterpolicyGroupFilterBox("automationui policy group");
		authpolicypage.enterPolicyNameInFilter("authpolicy01");
		authpolicypage.clickOnApplyFilterButton();
		authpolicypage.clickOnPoliciesListViewElipsisButton();
		assertTrue(authpolicypage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		authpolicypage.clickOnViewButton();
		assertTrue(authpolicypage.isPolicyViewPageTitleDisplayed(), GlobalConstants.isPolicyViewPageTitleDisplayed);
		assertTrue(authpolicypage.isSubTitleDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(authpolicypage.isSubTitleHomeDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(authpolicypage.isPolicyIdLabelDisplayed(), GlobalConstants.isPolicyIdLabelDisplayed);
//		assertTrue(authpolicypage.isPolicyIdContextDisplayed(), GlobalConstants.isPolicyIdContextDisplayed);
		assertTrue(authpolicypage.isPolicyStatusActivatedDisplayed(), GlobalConstants.isPolicyStatusActivatedDisplayed);
		assertTrue(authpolicypage.isPolicyNameLabelDisplayed(), GlobalConstants.isPolicyNameLabelDisplayed);
		assertTrue(authpolicypage.isPolicyNameContextDisplayed(), GlobalConstants.isPolicyNameContextDisplayed);
		assertTrue(authpolicypage.isPolicyGroupLabelDisplayed(), GlobalConstants.isPolicyGroupLabelDisplayed);
		assertTrue(authpolicypage.isPolicyGroupContextDisplayed(), GlobalConstants.isPolicyGroupContextDisplayed);
		assertTrue(authpolicypage.isPolicyDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyDescriptionLabelDisplayed);
		assertTrue(authpolicypage.isPolicyDescriptionContextDisplayed(),
				GlobalConstants.isPolicyDescriptionContextDisplayed);
		assertTrue(authpolicypage.isPolicyGroupDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionLabelDisplayed);
		assertTrue(authpolicypage.isPolicyGroupDescriptionContextDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionContextDisplayed);
		assertTrue(authpolicypage.isPolicyDataLabelDisplayed(), GlobalConstants.isPolicyDataLabelDisplayed);
		assertTrue(authpolicypage.isPolicyDataTitleDisplayed(), GlobalConstants.isPolicyDataTitleDisplayed);
		assertTrue(authpolicypage.isPolicyDataPreviewDisplayed(), GlobalConstants.isPolicyDataPreviewDisplayed);

		authpolicypage.clickOnPolicyDataPreviewButton();
		assertTrue(authpolicypage.ispolicyDataPopupDisplayed(), GlobalConstants.ispolicyDataPopupDisplayed);
		assertTrue(authpolicypage.isDownloadButtonDisplayed(), GlobalConstants.isDownloadButtonDisplayed);
		assertTrue(authpolicypage.isPolicyDataJsonDisplayed(), GlobalConstants.isPolicyDataJsonDisplayed);
		assertTrue(authpolicypage.isCloseButtonDisplayed(), GlobalConstants.isCloseButtonDisplayed);
		authpolicypage.clickOnDownloadButton();
		authpolicypage.clickOnPreviewCloseButton();
		authpolicypage.clickOnSubTitleButton();

		authpolicypage.clickOnFilterButton();
		authpolicypage.enterpolicyGroupFilterBox("automationui policy group");
		authpolicypage.enterPolicyNameInFilter("deactivate auth");
		authpolicypage.clickOnApplyFilterButton();
		authpolicypage.clickOnPoliciesListViewElipsisButton();
		assertTrue(authpolicypage.isViewButtonDisplayed(), GlobalConstants.isViewButtonDisplayed);
		authpolicypage.clickOnViewButton();
		assertTrue(authpolicypage.isPolicyViewPageTitleDisplayed(), GlobalConstants.isPolicyViewPageTitleDisplayed);
		assertTrue(authpolicypage.isPolicyStatusDeactivatedDisplayed(),
				GlobalConstants.isPolicyStatusDeactivatedDisplayed);
		authpolicypage.clickOnSubTitleHomeButton();
	}

}
