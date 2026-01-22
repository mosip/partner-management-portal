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
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PolicyCreationForAuthPartner" }, groups = { "ApiKeyAuthPartnerTest" })
public class ApiKeyAuthPartnerTest extends BaseClass {
	private BasePage basePage;
	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private ApiKeyPage apiKeyPage;
	private AuthPolicyPage authPolicyPage;
	private OidcClientPage oidcClientPage;

	@Test(priority = 1, description = "APIkey creation")
	public void createApiKey() {

		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();
		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();

		assertTrue(oidcClientPage.isApiKeyTabDisplayed(), GlobalConstants.isApiKeyTabDisplayed);
		oidcClientPage.clickOnApiKeyTab();
		assertTrue(apiKeyPage.isGenerateApiKeyDisplayed(), GlobalConstants.isGenerateAPIKeyDisplayed);
		apiKeyPage.clickOnCreateApiKey();
		assertTrue(apiKeyPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		apiKeyPage.selectPartnerIdDropdown();

		assertTrue(apiKeyPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		apiKeyPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		apiKeyPage.enterNameOfApiKeyTextBox("0" + GlobalConstants.DEFAULT_POLICY);

		apiKeyPage.clickOnSubmitButton();

		apiKeyPage.clickOnCopyIdButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		assertTrue(apiKeyPage.isConfirmationGoBackButtonDisplayed(), GlobalConstants.isGoBackButtonDisplayed);
		apiKeyPage.clickOnConfirmationGoBackButton();
		assertTrue(apiKeyPage.isApiListItem1Displayed(), GlobalConstants.isApiListDisplayed);
		apiKeyPage.clickOnApiListItem1();

		assertTrue(apiKeyPage.isApiKeyDetailsPartnerIdLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsPartnerIdLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPartnerIdContextDisplayed(),
				GlobalConstants.isApiKeyDetailsPartnerIdContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyGroupLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyGroupLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyGroupNameContextDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyGroupNameContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyNameLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyNameLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyNameContextDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyNameContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyGoupDescriptionLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyGoupDescriptionLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyGroupDescriptionContextDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyGroupDescriptionContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyNameDescriptionLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyNameDescriptionLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyDescriptionContextDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyDescriptionContextDisplayed);
		assertTrue(apiKeyPage.isViewApiKeyBackButtonDisplayed(), GlobalConstants.isViewApiKeyBackButtonDisplayed);
		assertTrue(apiKeyPage.isBackiconDisplayed(), GlobalConstants.isBackiconDisplayed);
		apiKeyPage.clickOnViewApiKeyBackButton();

		assertTrue(apiKeyPage.isApiKeyListPageGenerateApiKeyBtnDisplayed(),
				GlobalConstants.isApiKeyListPageGenerateApiKeyBtnDisplayed);
		apiKeyPage.clickOnCreateApiKey();
		assertTrue(apiKeyPage.isPartnerIdHelpTextDisplayed(), GlobalConstants.isPartnerIdHelpTextDisplayed);
		assertTrue(apiKeyPage.isPolicyNameHelpTextDisplayed(), GlobalConstants.isPolicyNameHelpTextDisplayed);
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		apiKeyPage.enterNameOfApiKeyTextBox("0" + GlobalConstants.DEFAULT_POLICY);
		assertTrue(apiKeyPage.isClearButtonDisplayed(), GlobalConstants.isClearButtonDisplayed);
		apiKeyPage.clickOnClearButton();
		assertTrue(apiKeyPage.isCancelButtonDisplayed(), GlobalConstants.isCancelButtonDisplayed);
		apiKeyPage.clickOnCancelButton();

		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.enterPendingPolicyNameDropdown(GlobalConstants.PENDING_POLICY);
		assertTrue(apiKeyPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		apiKeyPage.clickOnClearButton();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		apiKeyPage.enterNameOfApiKeyTextBox(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(apiKeyPage.isSpecialCharacterErrorMessageDisplayed(),
				GlobalConstants.isSpecialCharacterErrorMessageDisplayed);
		apiKeyPage.enterNameOfApiKeyTextBox(GlobalConstants.AUTOMATION);
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationHomeButton();

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnApiKeyTab();
		apiKeyPage.clickOnCreateApiKey();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		apiKeyPage.enterNameOfApiKeyTextBox(GlobalConstants.AUTOMATION);
		apiKeyPage.clickOnSubmitButton();
		assertTrue(apiKeyPage.isDuplicateApiKeyNameErrorMessageDisplayed(),
				GlobalConstants.isDuplicateApiKeyNameErrorMessageDisplayed);

		apiKeyPage.clickOnDuplicateApiKeyNameErrorMessageCloseButton();
		apiKeyPage.enterNameOfApiKeyTextBox(GlobalConstants.SPECIAL_NUMERIC);
		assertTrue(apiKeyPage.isSpecialCharacterErrorMessageDisplayed(),
				GlobalConstants.isSpecialCharacterErrorMessageDisplayed);
		apiKeyPage.enterNameOfApiKeyTextBox(GlobalConstants.ALPHANUMERIC);
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();

		apiKeyPage.clickOnapiListElipsisButton();
		apiKeyPage.clickOnDeactivateButton();
		assertTrue(apiKeyPage.isApiKeyDeactivatePopupDisplayed(),
				GlobalConstants.isApiKeyDeactivateConfirmationTextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDeactivationInfoTextDisplayed(),
				GlobalConstants.isApiKeyDeactivationInfoTextDisplayed);
		assertTrue(apiKeyPage.isDeactivateCancelButtonAvailable(), GlobalConstants.isDeactivateCancelButtonAvailable);
		assertTrue(apiKeyPage.isDeactivateSubmitButtonAvailable(), GlobalConstants.isDeactivateSubmitButtonAvailable);
		apiKeyPage.clickOnDeactivateCancelButton();
		apiKeyPage.clickOnapiListElipsisButton();
		apiKeyPage.clickOnDeactivateButton();
		apiKeyPage.clickOnDeactivateSubmitButton();
		apiKeyPage.clickOnDeactivatedApiKey();
		assertTrue(apiKeyPage.isDeactivatedApiKeyDisabled(), GlobalConstants.isDeactivatedApiKeyDisabled);
		assertTrue(apiKeyPage.isDeactivatedApiKeyGreyColored(), GlobalConstants.isDeactivatedApiKeyGreyColored);
		apiKeyPage.clickOnapiListElipsisButton();
		apiKeyPage.clickOnDeactivateButton();
		assertFalse(apiKeyPage.isApiKeyDeactivatePopupDisplayed(),
				GlobalConstants.isApiKeyDeactivateConfirmationTextDisplayed);
		apiKeyPage.clickOnApiKeyViewButton();
		assertTrue(apiKeyPage.isApiKeyStatusDeactivatedDisplayed(), GlobalConstants.isApiKeyStatusDeactivated);
		apiKeyPage.clickOnViewApiKeyBackButton();

		assertTrue(apiKeyPage.isPartnerIdDescIconDisplayed(), GlobalConstants.isPartnerIdDescIconDisplayed);
		assertTrue(apiKeyPage.isPartnerIdAscIconDisplayed(), GlobalConstants.isPartnerIdAscIconDisplayed);
		assertTrue(apiKeyPage.isApiKeyNameDescIconDisplayed(), GlobalConstants.isApiKeyNameDescIconDisplayed);
		assertTrue(apiKeyPage.isApiKeyNameAscIconDisplayed(), GlobalConstants.isApiKeyNameAscIconDisplayed);
		assertTrue(apiKeyPage.isPolicyGroupNameDescIconDisplayed(), GlobalConstants.isPolicyGroupNameDescIconDisplayed);
		assertTrue(apiKeyPage.isPolicyGroupNameAscIconDisplayed(), GlobalConstants.isPolicyGroupNameAscIconDisplayed);
		assertTrue(apiKeyPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescIconDisplayed);
		assertTrue(apiKeyPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameAscIconDisplayed);
		assertTrue(apiKeyPage.isCreatedDateTimeDescIconDisplayed(), GlobalConstants.isCreatedDateTimeDescIconDisplayed);
		assertTrue(apiKeyPage.isCreatedDateTimeAscIconDisplayed(), GlobalConstants.isCreatedDateTimeAscIconDisplayed);
		assertTrue(apiKeyPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);

		apiKeyPage.clickOnFilterButton();
		apiKeyPage.clickOnApiKeyPartnerIdFilter();
		apiKeyPage.clickOnApiKeySelectPolicyGroupFilter();
		apiKeyPage.clickOnApiKeySelectPolicyNameFilter();
		apiKeyPage.clickOnApiKeySelectStatusFilter();
		apiKeyPage.clickOnActivatedStatusApiKeyFilter();
		apiKeyPage.clickOnApiKeyNameDescIcon();
		apiKeyPage.clickOnApiKeyNameAscIcon();
		apiKeyPage.enterInvalidDataInApiKeyNameFilter(GlobalConstants.INVALID_DATA);
		assertTrue(apiKeyPage.isNoDataAvailabelDisplayed(), GlobalConstants.isNoDataAvailabelDisplayed);
		apiKeyPage.unSelectApiKeyNameFilter();
		apiKeyPage.clickOnapiListElipsisButton();
		apiKeyPage.clickOnApiKeyViewButton();
		apiKeyPage.clickOnTitleBackButton();

		apiKeyPage.clickOnFilterButton();
		apiKeyPage.clickOnApiKeyPartnerIdFilter();
		apiKeyPage.clickOnApiKeySelectPolicyGroupFilter();
		apiKeyPage.clickOnApiKeySelectPolicyNameFilter();
		apiKeyPage.clickOnApiKeySelectClientNameFilter();
		apiKeyPage.clickOnApiKeySelectStatusFilter();
		apiKeyPage.clickOnFilterResetButton();
		assertTrue(apiKeyPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);

		createApiKey(GlobalConstants.AUTHPOLICY01);

		createApiKey(GlobalConstants.AUTHPOLICY02);
		apiKeyPage.clickOnApiListItem1();
		apiKeyPage.clickOnViewApiKeyBackButton();

		createApiKey(GlobalConstants.AUTHPOLICY03);

		createApiKey(GlobalConstants.AUTHPOLICY04);
		apiKeyPage.clickOnApiListItem1();
		apiKeyPage.clickOnViewApiKeyBackButton();

		createApiKey(GlobalConstants.AUTHPOLICY05);

		createApiKey(GlobalConstants.AUTHPOLICY06);

		createApiKey(GlobalConstants.DEACTIVATE_APIKEY);

		createApiKey(GlobalConstants.ACTIVATE_ADMINAPIKEY);

		assertTrue(apiKeyPage.isItemsPerPageDisplayed(), GlobalConstants.isItemsPerPageDisplayed);
		assertTrue(apiKeyPage.isItemsPerPageDropdownAvailable(), GlobalConstants.isItemsPerPageDropdownAvailable);
		apiKeyPage.clickOnItemsPerPageDropdown();
		apiKeyPage.selectNumberOfRecordPerPage();

		assertTrue(apiKeyPage.isBreadcombDisplayed(), GlobalConstants.isBreadcombDisplayed);
		apiKeyPage.clickOnBreadcomb();
	}

	@Test(priority = 2, description = "ApiKey Deactivate", dependsOnMethods = "createApiKey")
	public void apiKeyDeactivate() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		apiKeyPage = new ApiKeyPage(driver);
		oidcClientPage = dashboardPage.clickOnAuthenticationServices();
		basePage = new BasePage(driver);

		assertTrue(oidcClientPage.isOidcClientTabDisplayed(), GlobalConstants.isOidcClientTabDisplayed);
		assertTrue(oidcClientPage.isApiKeyTabDisplayed(), GlobalConstants.isApiKeyTabDisplayed);
		oidcClientPage.clickOnApiKeyTab();
		apiKeyPage.clickOnFilterButton();

		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		apiKeyPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		apiKeyPage.enterValidApiKeyNameInAdminFilter(GlobalConstants.DEACTIVATE_APIKEY);
		apiKeyPage.clickOnApplyFilterButton();

		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		assertTrue(apiKeyPage.isDeactivateButtonEnabled(), GlobalConstants.isDeactivateButtonEnabled);

		apiKeyPage.clickOnApiKeyDeactivateButton();
		assertTrue(apiKeyPage.isApiKeyInAdminDeactivatePopupDisplayed(),
				GlobalConstants.isApiKeyDeactivatePopupDisplayed);
		assertTrue(apiKeyPage.isApiKeyInAdminDeactivateTitleDisplayed(),
				GlobalConstants.isApiKeyDeactivateTitleDisplayed);
		assertTrue(apiKeyPage.isApiKeyInAdminDeactivateInfoTextDisplayed(),
				GlobalConstants.isApiKeyDeactivationInfoTextDisplayed);
		assertTrue(apiKeyPage.isDeactivateSubmitButtonAvailable(), GlobalConstants.isDeactivateSubmitButtonAvailable);
		assertTrue(apiKeyPage.isDeactivateCancelButtonAvailable(), GlobalConstants.isDeactivateCancelButtonAvailable);

		apiKeyPage.clickOnDeactivateCancelButton();
		apiKeyPage.clickOnFilterResetButton();
		assertTrue(apiKeyPage.isSubTitleOfTabularViewDisplayed(), GlobalConstants.isSubTitleOfTabularViewDisplayed);

		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		apiKeyPage.enterValidApiKeyNameInAdminFilter(GlobalConstants.DEACTIVATE_APIKEY);
		apiKeyPage.clickOnApplyFilterButton();

		apiKeyPage.clickOnActionButton();
		apiKeyPage.clickOnApiKeyDeactivateButton();
		apiKeyPage.clickOnDeactivateSubmitButton();

		basePage.navigateBack();
		basePage.navigateForward();

		loginAsAuthPartner();

		dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnApiKeyTab();
		apiKeyPage.clickOnCreateApiKey();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		apiKeyPage.enterNameOfApiKeyTextBox(GlobalConstants.DEACTIVATE_APIKEY);
		apiKeyPage.clickOnSubmitButton();
		assertTrue(apiKeyPage.isDuplicateApiKeyNameErrorMessageDisplayed(),
				GlobalConstants.isDuplicateApiKeyNameErrorMessageDisplayed);
		apiKeyPage.clickOnDuplicateApiKeyNameErrorMessageCloseButton();

	}

	@Test(priority = 3, description = "API Key Tabular View", dependsOnMethods = "apiKeyDeactivate")
	public void apiKeyTabularView() {
		dashboardPage = new DashboardPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		apiKeyPage = new ApiKeyPage(driver);

		assertTrue(dashboardPage.isAuthenticationServicesDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServices();
		assertTrue(oidcClientPage.isOidcClientTabDisplayed(), GlobalConstants.isOidcClientTabDisplayed);
		assertTrue(oidcClientPage.isApiKeyTabDisplayed(), GlobalConstants.isApiKeyTabDisplayed);
		oidcClientPage.clickOnApiKeyTab();

		assertTrue(apiKeyPage.isSubTitleOfTabularViewDisplayed(), GlobalConstants.isSubTitleOfTabularViewDisplayed);
		assertTrue(apiKeyPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertTrue(apiKeyPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);

		assertTrue(apiKeyPage.isPartnerIdHeaderDisplayed(), GlobalConstants.isPartnerIdHeaderDisplayed);
		assertTrue(apiKeyPage.isOrganisationHeaderDisplayed(), GlobalConstants.isOrganisationHeaderDisplayed);
		assertTrue(apiKeyPage.isPolicyGroupHeaderDisplayed(), GlobalConstants.isPolicyGroupHeaderDisplayed);
		assertTrue(apiKeyPage.isPolicyNameHeaderDisplayed(), GlobalConstants.isPolicyNameHeaderDisplayed);
		assertTrue(apiKeyPage.isApiKeyNameHeaderDisplayed(), GlobalConstants.isApiKeyNameHeaderDisplayed);
		assertTrue(apiKeyPage.isCreationDateHeaderDisplayed(), GlobalConstants.isCreationDateHeaderDisplayed);
		assertTrue(apiKeyPage.isStatusHeaderDisplayed(), GlobalConstants.isStatusHeaderDisplayed);
		assertTrue(apiKeyPage.isActionHeaderDisplayed(), GlobalConstants.isActionHeaderDisplayed);

		assertTrue(apiKeyPage.isPartnerIdDescIconDisplayed(), GlobalConstants.isPartnerIdDescIconDisplayed);
		assertTrue(apiKeyPage.isPartnerIdAscIconDisplayed(), GlobalConstants.isPartnerIdAscIconDisplayed);
		assertTrue(apiKeyPage.isApiKeyNameDescIconDisplayed(), GlobalConstants.isApiKeyNameDescIconDisplayed);
		assertTrue(apiKeyPage.isApiKeyNameAscIconDisplayed(), GlobalConstants.isApiKeyNameAscIconDisplayed);
		assertTrue(apiKeyPage.isPolicyGroupNameDescIconDisplayed(), GlobalConstants.isPolicyGroupNameDescIconDisplayed);
		assertTrue(apiKeyPage.isPolicyGroupNameAscIconDisplayed(), GlobalConstants.isPolicyGroupNameAscIconDisplayed);
		assertTrue(apiKeyPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescIconDisplayed);
		assertTrue(apiKeyPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameAscIconDisplayed);
		assertTrue(apiKeyPage.isCreatedDateTimeDescIconDisplayed(), GlobalConstants.isCreatedDateTimeDescIconDisplayed);
		assertTrue(apiKeyPage.isCreatedDateTimeAscIconDisplayed(), GlobalConstants.isCreatedDateTimeAscIconDisplayed);
		assertTrue(apiKeyPage.isStatusDescIconDisplayed(), GlobalConstants.isStatusDescIconDisplayed);
		assertTrue(apiKeyPage.isStatusAscIconDisplayed(), GlobalConstants.isStatusAscIconDisplayed);
		assertTrue(apiKeyPage.isOrgNameDescIconDisplayed(), GlobalConstants.isOrgNameDescIconDisplayed);
		assertTrue(apiKeyPage.isOrgNameAscIconDisplayed(), GlobalConstants.isOrgNameAscIconDisplayed);

		apiKeyPage.clickOnPartnerIdDescIcon();
		apiKeyPage.clickOnPartnerIdAscIcon();
		apiKeyPage.clickOnOrgNameDescIcon();
		apiKeyPage.clickOnOrgNameAscIcon();
		apiKeyPage.clickOnPolicyGroupNameDescIcon();
		apiKeyPage.clickOnPolicyGroupNameAscIcon();
		apiKeyPage.clickOnPolicyNameDescIcon();
		apiKeyPage.clickOnPolicyNameAscIcon();
		apiKeyPage.clickOnCreatedDateTimeDescIcon();
		apiKeyPage.clickOnCreatedDateTimeAscIcon();
		apiKeyPage.clickOnStatusDescIcon();
		apiKeyPage.clickOnStatusAscIcon();

		apiKeyPage.clickOnFilterButton();
		assertTrue(apiKeyPage.isfilterResetButtonEnabled(), GlobalConstants.isfilterResetButtonEnabled);
		assertFalse(apiKeyPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);

		assertTrue(apiKeyPage.isPartnerIdFilterHeaderDisplayed(), GlobalConstants.isPartnerIdFilterHeaderDisplayed);
		assertTrue(apiKeyPage.isOrganisationFilterHeaderDisplayed(),
				GlobalConstants.isOrganisationFilterHeaderDisplayed);
		assertTrue(apiKeyPage.isPolicyGroupFilterHeaderDisplayed(), GlobalConstants.isPolicyGroupFilterHeaderDisplayed);
		assertTrue(apiKeyPage.isPolicyNameFilterHeaderDisplayed(), GlobalConstants.isPolicyNameFilterHeaderDisplayed);
		assertTrue(apiKeyPage.isApiKeyNameFilterHeaderDisplayed(),
				GlobalConstants.isOidcClientNameFilterHeaderDisplayed);
		assertTrue(apiKeyPage.isStatusFilterHeaderDisplayed(), GlobalConstants.isStatusFilterHeaderDisplayed);

		assertTrue(apiKeyPage.isPartnerIdPlaceHolderDisplayed(), GlobalConstants.isPartnerIdPlaceHolderDisplayed);
		assertTrue(apiKeyPage.isOrganisationPlaceHolderDisplayed(), GlobalConstants.isOrganisationPlaceHolderDisplayed);
		assertTrue(apiKeyPage.isPolicyGroupPlaceHolderDisplayed(), GlobalConstants.isPolicyGroupPlaceHolderDisplayed);
		assertTrue(apiKeyPage.isPolicyNamePlaceHolderDisplayed(), GlobalConstants.isPolicyNamePlaceHolderDisplayed);
		assertTrue(apiKeyPage.isApiKeyNamePlaceHolderDisplayed(), GlobalConstants.isApiKeyNamePlaceHolderDisplayed);
		assertTrue(apiKeyPage.isStatusPlaceHolderDisplayed(), GlobalConstants.isStatusPlaceHolderDisplayed);

		assertFalse(apiKeyPage.isApplyFilterButtonEnabled(), GlobalConstants.isApplyFilterButtonEnabled);
		apiKeyPage.enterInvalidDataInAdminApiKeyNameFilter(GlobalConstants.RANDOM_DATA);
		assertTrue(apiKeyPage.isApplyFilterButtonEnabled(), GlobalConstants.isApplyFilterButtonEnabled);
		apiKeyPage.clickOnApplyFilterButton();
		assertTrue(apiKeyPage.isNoResultFoundDisplayed(), GlobalConstants.isNoResultFoundDisplayed);

		apiKeyPage.clickOnFilterResetButton();
		apiKeyPage.clickOnFilterButton();
		apiKeyPage.clickOnStatusFilter();
		assertTrue(apiKeyPage.isActivatedStatusInFilterDisplayed(), GlobalConstants.isActivatedStatusInFilterDisplayed);
		assertTrue(apiKeyPage.isDeactivatedStatusInFilterDisplayed(),
				GlobalConstants.isDeactivatedStatusInFilterDisplayed);
		apiKeyPage.clickOnStatusFilter();
		apiKeyPage.clickOnFilterResetButton();
		assertFalse(apiKeyPage.isfilterResetButtonEnabled(), GlobalConstants.isfilterResetButtonEnabled);

		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		apiKeyPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		apiKeyPage.clickOnFilterCloseButton();
		apiKeyPage.enterValidApiKeyNameInAdminFilter(GlobalConstants.ACTIVATE_ADMINAPIKEY);
		apiKeyPage.clickOnApplyFilterButton();

		assertTrue(apiKeyPage.isApiKeyCreationDateSameAsBrowserDateFormat(),
				GlobalConstants.isCreationDateSameAsBrowserDateFormat);
		apiKeyPage.clickOnActivatedAdminApiKey();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);
		apiKeyPage.clickOnViewApiKeyBackButton();

		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		apiKeyPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		apiKeyPage.enterValidApiKeyNameInAdminFilter(GlobalConstants.ACTIVATE_ADMINAPIKEY);
		apiKeyPage.clickOnApplyFilterButton();

		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		assertTrue(apiKeyPage.isDeactivateButtonEnabled(), GlobalConstants.isDeactivateButtonEnabled);

		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		apiKeyPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		apiKeyPage.enterValidApiKeyNameInAdminFilter(GlobalConstants.DEACTIVATE_APIKEY);
		apiKeyPage.clickOnApplyFilterButton();

		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		apiKeyPage.clickOnApiKeyDeactivateButton();
		assertFalse(apiKeyPage.isApiKeyDeactivatePopupDisplayed(), GlobalConstants.isApiKeyDeactivatePopupDisplayed);

		apiKeyPage.clickOnDeactivatedApiKeyRow();
		assertFalse(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);
		assertTrue(apiKeyPage.isStatusDeavtivatedDisplayed(), GlobalConstants.isStatusDeavtivatedDisplayed);

		apiKeyPage.clickOnFilterResetButton();
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

		apiKeyPage.clickOnTitleBackButton();
		assertTrue(dashboardPage.isAuthenticationServicesDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);

	}

	@Test(priority = 4, description = "Api Key View Details", dependsOnMethods = "apiKeyTabularView")
	public void apiKeyViewDetails() {

		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);

		oidcClientPage = dashboardPage.clickOnAuthenticationServices();
		oidcClientPage.clickOnApiKeyTab();
		oidcClientPage.clickOnFilterButton();

		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		apiKeyPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		apiKeyPage.enterValidApiKeyNameInAdminFilter(GlobalConstants.ACTIVATE_ADMINAPIKEY);
		apiKeyPage.clickOnApplyFilterButton();

		apiKeyPage.clickOnActivatedAdminApiKey();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);
		apiKeyPage.clickOnBreadCombButton();

		oidcClientPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		apiKeyPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		apiKeyPage.enterValidApiKeyNameInAdminFilter(GlobalConstants.ACTIVATE_ADMINAPIKEY);
		apiKeyPage.clickOnApplyFilterButton();
		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		apiKeyPage.clickOnViewButton();

		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPageTitleDisplayed(), GlobalConstants.isApiKeyDetailsPageTitleDisplayed);
		assertTrue(apiKeyPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertTrue(apiKeyPage.isListOfApiKeysButtonDisplayed(), GlobalConstants.isListOfApiKeysButtonDisplayed);

		assertTrue(apiKeyPage.isApiKeyNameLabelDisplayed(), GlobalConstants.isApiKeyNameLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPartnerIdLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsPartnerIdLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPartnerIdContextDisplayed(),
				GlobalConstants.isApiKeyDetailsPartnerIdContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPartnerTypeLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsPartnerTypeLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPartnerTypeContextDisplayed(),
				GlobalConstants.isApiKeyDetailsPartnerTypeContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsOrgNameLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsOrgNameLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsOrgNameContextDisplayed(),
				GlobalConstants.isApiKeyDetailsOrgNameContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyGroupLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyGroupLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyGroupNameContextDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyGroupNameContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyGoupDescriptionLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyGoupDescriptionLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyGroupDescriptionContextDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyGroupDescriptionContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyNameLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyNameLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyNameContextDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyNameContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyNameDescriptionLabelDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyNameDescriptionLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyDescriptionContextDisplayed(),
				GlobalConstants.isApiKeyDetailsPolicyDescriptionContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyStatusActivatedDisplayed(), GlobalConstants.isApiKeyStatusActivatedDisplayed);
		assertTrue(apiKeyPage.isCreatedOnLabelDisplayed(), GlobalConstants.isCreatedOnLabelDisplayed);
		assertTrue(apiKeyPage.isCreatedDateDisplayed(), GlobalConstants.isCreatedDateDisplayed);

		assertTrue(apiKeyPage.isViewApiKeyBackButtonDisplayed(), GlobalConstants.isViewApiKeyBackButtonDisplayed);
		apiKeyPage.clickOnViewApiKeyBackButton();

		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		apiKeyPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		apiKeyPage.enterValidApiKeyNameInAdminFilter(GlobalConstants.DEACTIVATE_APIKEY);
		apiKeyPage.clickOnApplyFilterButton();

		apiKeyPage.clickOnDeactivatedApiKeyRow();
		assertFalse(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);

		apiKeyPage.clickOnActionButton();
		apiKeyPage.clickOnViewButton();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);
		assertTrue(apiKeyPage.isApiKeyStatusDeactivatedDisplayed(), GlobalConstants.isApiKeyStatusDeactivatedDisplayed);
		oidcClientPage.clickOnTitleBackButton();
		assertTrue(oidcClientPage.isOidcClientTabDisplayed(), GlobalConstants.isOidcClientTabDisplayed);

	}

	private void loginAsAuthPartner() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.AUTH_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();
	}

	private void createApiKey(String apiKeyTextBoxValue) {
		apiKeyPage.clickOnCreateApiKey();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		apiKeyPage.enterNameOfApiKeyTextBox(apiKeyTextBoxValue);
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();
	}

}
