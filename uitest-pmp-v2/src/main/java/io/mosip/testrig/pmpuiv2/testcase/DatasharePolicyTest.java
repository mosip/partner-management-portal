package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.ApiKeyPage;
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

@Test(dependsOnGroups = { "PolicyGroupTest" }, groups = { "DatasharePolicyTest" })
public class DatasharePolicyTest extends BaseClass {

	private BasePage basePage;
	private DashboardPage dashboardPage;
	private PoliciesPage policiesPage;
	private PolicyGroupPage policygroupPage;
	private DatasharePolicyPage datasharePolicyPage;
	private LoginPage loginPage;
	private ApiKeyPage apiKeyPage;
	private PartnerPolicyMappingPage partnerPolicyMappingPage;

	@Test(priority = 1, description = "Create Datashare Policy")
	public void createDatasharePolicy() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policiesPage = new PoliciesPage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
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
		datasharePolicyPage.searchPolicyGroup(GlobalConstants.DEFAULT_POLICYGROUP);
		assertTrue(datasharePolicyPage.isPolicyGroupNameDisplayed(), GlobalConstants.isPolicyGroupNameDisplayed);
		assertTrue(datasharePolicyPage.isPolicyGroupDescriptionDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionDisplayed);
		datasharePolicyPage.clearSearchedPolicyGroup();
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

		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
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
		assertTrue(datasharePolicyPage.isSuccessPublishButtonAvailable(),
				GlobalConstants.isSuccessPublishButtonAvailable);
		datasharePolicyPage.clickOnGoBackButton();

	}

	@Test(priority = 2, description = "Create Multiple Datashare Policy", dependsOnMethods = "createDatasharePolicy")
	public void createMultipleDatasharePolicy() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);

		loginAsPartnerAdmin();
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
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.DATAPOLICYPUBLISH);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.DATAPOLICYPUBLISH_DESCRIPTION);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		datasharePolicyPage.clickOnGoBackButton();

		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
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

	@Test(priority = 3, description = "Create Duplicate Datashare Policy", dependsOnMethods = "createMultipleDatasharePolicy")
	public void createDuplicateDatasharePolicy() {

		dashboardPage = new DashboardPage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
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
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
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

	@Test(priority = 4, description = "Upload Invalid Policy Data", dependsOnMethods = "createDuplicateDatasharePolicy")
	public void uploadInvalidPolicyData() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.AUTOMATION_LOWERUPPERCASE);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.AUTOMATION_LOWERUPPERCASE);
		datasharePolicyPage.uploadExceedPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		basePage.scrollToStartPage();
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

	@Test(priority = 5, description = "Datashare Policy Tabular View", dependsOnMethods = "uploadInvalidPolicyData")
	public void datasharePolicyTabularView() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
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
		assertTrue(datasharePolicyPage.isCreatedDateTimeDescIconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescIconDisplayed);
		assertTrue(datasharePolicyPage.isCreatedDateTimeAscIconDisplayed(),
				GlobalConstants.isCreatedDateTimeAscIconDisplayed);
		assertTrue(datasharePolicyPage.isStatusDescIconDisplayed(), GlobalConstants.isStatusDescISconDisplayed);
		assertTrue(datasharePolicyPage.isStatusAscIconDisplayed(), GlobalConstants.isStatusAscIconDisplayed);

		datasharePolicyPage.clickOnFilterButton();
		assertTrue(datasharePolicyPage.isFilterResetButtonEnabled(), GlobalConstants.isFilterResetButtonEnabled);
		assertFalse(datasharePolicyPage.isFilterButtonDisplayedOrEnabled(),
				GlobalConstants.isFilterButtonDisplayedOrEnabled);
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
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
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
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
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
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
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
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
		policygroupPage.navigateBackDefaultButton();

	}

	@Test(priority = 6, description = "View Datashare Policy Details", dependsOnMethods = "datasharePolicyTabularView")
	public void viewDatasharePolicyDetails() {

		dashboardPage = new DashboardPage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);

		loginAsPartnerAdmin();
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
		assertTrue(datasharePolicyPage.isPolicyIdContextDisplayed(), GlobalConstants.isPolicyIdContextDisplayed);
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

	}

	@Test(priority = 7, description = "Publish Datashare Policy", dependsOnMethods = "viewDatasharePolicyDetails")
	public void publishDatasharePolicy() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);
		policiesPage = new PoliciesPage(driver);
		loginPage = new LoginPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICYPUBLISH);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnPublishButton();
		datasharePolicyPage.clickOnPublishPolicyButton();
		datasharePolicyPage.clickOnPublishPolicyCloseButton();

		loginAsAuthPartner();

		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnRequestPolicyButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectInvalidPolicyNameDropdown(GlobalConstants.INVALID_DATA);
		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		policiesPage.clickOnHomeButton();
		datasharePolicyPage.clickOnlostWarningProceedButton();

		loginAsPartnerAdmin();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
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

	@Test(priority = 8, description = "Edit Datashare Policy", dependsOnMethods = "publishDatasharePolicy")
	public void editDatasharePolicy() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policiesPage = new PoliciesPage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnDataSharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();

		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.EDITDATAPOLICY);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.EDITDATAPOLICY_DESCRIPTION);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
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

		datasharePolicyPage.enterPolicyName(GlobalConstants.SINGLE_CHARACTERS);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.SINGLE_CHARACTERS);
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
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.SINGLE_CHARACTERS);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnEditButton();
		datasharePolicyPage.enterPolicyName(GlobalConstants.AUTOMATION_3);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.AUTOMATION_3);
		datasharePolicyPage.clickOnEditPolicyFormSubmitButton();
		datasharePolicyPage.clickOnGoBackButton();

		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTOMATION_3);
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
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnEditPolicyFormSubmitButton();
		basePage.scrollToStartPage();
		assertTrue(datasharePolicyPage.isInvalidInfoInPoliyDataMessageDisplayed(),
				GlobalConstants.isInvalidInfoInPolicyDataErrorDisplayed);
		datasharePolicyPage.clickOnErrorCloseButton();
		datasharePolicyPage.clearTextBoxPolicyData();
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		assertTrue(datasharePolicyPage.isEditPolicySubmitButtonEnabled(),
				GlobalConstants.isEditPolicySubmitButtonDisplayed);
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
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnEditPolicyFormSubmitButton();
		datasharePolicyPage.clickOnGoBackButton();
		basePage.navigateBack();

	}

	@Test(priority = 9, description = "Clone Datashare Policy", dependsOnMethods = "editDatasharePolicy")
	public void cloneDatasharePolicy() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
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
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DEACTIVATE_DATA1);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		assertTrue(datasharePolicyPage.isCloneButtonEnabled(), GlobalConstants.isCloneButtonEnabled);
		datasharePolicyPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
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
		assertTrue(datasharePolicyPage.isNoPolicyGroupFoundDisplayed(), GlobalConstants.isNoDataAvailableDisplayed);
		datasharePolicyPage.clearClonePolicyGroupDropdownValue();

		datasharePolicyPage.selectPolicyGroupForClonePolicy(GlobalConstants.CHARACTERS_1);
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
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnCloneButton();
		datasharePolicyPage.clickOnClonePolicyCancelButton();
		assertTrue(datasharePolicyPage.isSubTitleOfTabularViewDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewDisplayed);

		datasharePolicyPage.clickOnFilterResetButton();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnCloneButton();
		datasharePolicyPage.selectValidPolicyGroupForClone(GlobalConstants.CHARACTERS_1);
		datasharePolicyPage.clickOnClonePolicyButton();
		assertTrue(datasharePolicyPage.isAlreadyExistErrorMessageDisplayed(),
				GlobalConstants.isAlreadyExistErrorMessageDisplayed);
		datasharePolicyPage.clickOnCloseIcon();
		assertTrue(datasharePolicyPage.isSubTitleOfTabularViewDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewDisplayed);

		datasharePolicyPage.clickOnFilterResetButton();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnCloneButton();
		datasharePolicyPage.selectValidPolicyGroupForClone(GlobalConstants.CHARACTERS_1);
		datasharePolicyPage.clickOnClonePolicyButton();
		assertTrue(datasharePolicyPage.isAlreadyExistErrorMessageDisplayed(),
				GlobalConstants.isAlreadyExistErrorMessageDisplayed);
		datasharePolicyPage.clickOnCloseIcon();
		assertTrue(datasharePolicyPage.isSubTitleOfTabularViewDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewDisplayed);

		datasharePolicyPage.clickOnFilterResetButton();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnCloneButton();
		datasharePolicyPage.selectValidPolicyGroupForClone(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.clickOnClonePolicyButton();
		assertTrue(datasharePolicyPage.isAlreadyExistErrorMessageDisplayed(),
				GlobalConstants.isAlreadyExistErrorMessageDisplayed);
		datasharePolicyPage.clickOnCloseIcon();
		assertTrue(datasharePolicyPage.isSubTitleOfTabularViewDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewDisplayed);

		datasharePolicyPage.clickOnFilterResetButton();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnCloneButton();
		datasharePolicyPage.selectValidPolicyGroupForClone(GlobalConstants.POLICYGROUP07);
		datasharePolicyPage.clickOnClonePolicyButton();
		datasharePolicyPage.clickOnClonePolicyCloseButton();

	}

	@Test(priority = 10, description = "Deactivate Datashare Policy", dependsOnMethods = "cloneDatasharePolicy")
	public void deactivateDatasharePolicy() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);
		policiesPage = new PoliciesPage(driver);
		loginPage = new LoginPage(driver);
		apiKeyPage = new ApiKeyPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
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
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
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
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
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
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
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

		loginAsAuthPartner();

		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnCreateOidcClientButton();
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.enterDeactivePolicyNameInDropdown(GlobalConstants.DEACTIVATE_DATA2);
		assertTrue(oidcClientPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		oidcClientPage.clickOnCreateOidcClearForm();
		policiesPage.clickOnHomeButton();

		dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnApiKeyTab();
		apiKeyPage.clickOnCreateApiKey();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.enterDeactivePolicyNameInDropdown(GlobalConstants.DEACTIVATE_DATA2);
		assertTrue(apiKeyPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		apiKeyPage.clickOnClearButton();
		policiesPage.clickOnHomeButton();

		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnRequestPolicyButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectInvalidPolicyNameDropdown(GlobalConstants.DEACTIVATE_DATA2);
		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		policiesPage.clickOnRequestPoliciesFormClearButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(GlobalConstants.DATAPOLICY02);
		policiesPage.enterComments(GlobalConstants.DATAPOLICY02);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

		policiesPage.clickOnRequestPolicyButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(GlobalConstants.EDITDATAPOLICY1);
		policiesPage.enterComments(GlobalConstants.EDITDATAPOLICY1);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

		policiesPage.clickOnRequestPolicyButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(GlobalConstants.ALPHANUMERIC);
		policiesPage.enterComments(GlobalConstants.ALPHANUMERIC);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

		policiesPage.clickOnRequestPolicyButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(GlobalConstants.DATAPOLICY01);
		policiesPage.enterComments(GlobalConstants.DATAPOLICY01);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

		loginAsPartnerAdmin();

		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.ALPHANUMERIC);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		partnerPolicyMappingPage.clickOnApproveSubmitButton();
		partnerPolicyMappingPage.clickOnFilterResetButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		partnerPolicyMappingPage.clickOnRejectButton();
		partnerPolicyMappingPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
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
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
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
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnDeactivateButton();
		datasharePolicyPage.clickOnDeactivateConfirmButton();
		basePage.navigateBack();
		basePage.navigateForward();

		datasharePolicyPage.clickOnFilterButton();
		datasharePolicyPage.clickOnPolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.clickOnApplyFilterButton();
		assertTrue(datasharePolicyPage.isPolicyStatusDeactivateDisplayed(),
				GlobalConstants.isPolicyStatusDeactivateDisplayed);

		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.DATAPOLICY01);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.DATAPOLICY01_DESCRIPTION);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		assertTrue(datasharePolicyPage.isPolicyNameExistErrorMessageDisplayed(),
				GlobalConstants.isPolicyNameExistErrorMessageDisplayed);

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
