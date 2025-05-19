package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.fw.util.PmpTestUtil;
import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
import io.mosip.testrig.pmprevampui.pages.ApiKeyPage;
import io.mosip.testrig.pmprevampui.pages.AuthPolicyPage;
import io.mosip.testrig.pmprevampui.pages.BasePage;
import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.OidcClientPage;
import io.mosip.testrig.pmprevampui.pages.PoliciesPage;
import io.mosip.testrig.pmprevampui.pages.PolicyGroupPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class AuthenticationServices extends BaseClass {
	
	@Test(priority = 1, description = "Oidc Client Deactivate")
	public void oidcClientDeactivate() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServices();
		
		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		oidcClientPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_OIDCPOLICY2);
		oidcClientPage.clickOnApplyFilterButton();
		
		oidcClientPage.clickOnActionButton();
		assertTrue(oidcClientPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		assertTrue(oidcClientPage.isDeactivateButtonEnabled(),
				GlobalConstants.isDeactivateButtonEnabled);
		
		oidcClientPage.clickOnDeactivateButton();
		assertTrue(oidcClientPage.isDeactivateOidcClientPopupDisplayed(),
				GlobalConstants.isDeactivateOidcClientPopupDisplayed);
		assertTrue(oidcClientPage.isDeactivateOidcClientTitleDisplayed(),
				GlobalConstants.isDeactivateOidcClientTitleDisplayed);
		assertTrue(oidcClientPage.isDeactivateOidcClientSubtitleDisplayed(),
				GlobalConstants.isDeactivateOidcClientSubtitleDisplayed);
		assertTrue(oidcClientPage.isDeactivateSubmitButtonAvailable(),
				GlobalConstants.isDeactivateSubmitButtonAvailable);
		assertTrue(oidcClientPage.isDeactivateCancelButtonAvailable(),
				GlobalConstants.isDeactivateCancelButtonAvailable);
		
		oidcClientPage.clickOnDeactivateCancelButton();
		assertTrue(oidcClientPage.isSubTitleOfTabularViewDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewDisplayed);
		
		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_OIDCPOLICY2);
		oidcClientPage.clickOnApplyFilterButton();
		
		oidcClientPage.clickOnActionButton();
		oidcClientPage.clickOnDeactivateButton();
		oidcClientPage.clickOnDeactivateSubmitButtonButton();
		
		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		oidcClientPage.selectDeactivateStatusInFilter();
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_OIDCPOLICY2);
		oidcClientPage.clickOnApplyFilterButton();
		oidcClientPage.clickOidcShowCopyPopupButton();
		assertFalse(oidcClientPage.iscopyIdButtonDisplayed(),
				GlobalConstants.iscopyIdButtonDisplayed);
		
		oidcClientPage.clickOnDeactivatedOidcRow();
		assertFalse(oidcClientPage.isOidcClientDetailsPageDisplayed(),
				GlobalConstants.isOidcClientDetailsPageDisplayed);
		assertTrue(oidcClientPage.isStatusDeavtivatedDisplayed(),
				GlobalConstants.isStatusDeavtivatedDisplayed);
		
		oidcClientPage.clickOnActionButton();		
		oidcClientPage.clickOnViewButton();
		assertTrue(oidcClientPage.isOidcClientDetailsPageDisplayed(),
				GlobalConstants.isOidcClientDetailsPageDisplayed);
		assertTrue(oidcClientPage.isOidcDetailsPageStatusDeactivatedDisplayed(),
				GlobalConstants.isOidcDetailsPageStatusDeactivatedDisplayed);
		assertTrue(oidcClientPage.isDeactivatedOidcClientIdElementDisplayed(),
				GlobalConstants.isDeactivatedOidcClientIdElementDisplayed);
		
		dashboardPage.clickOnProfileDropdown();
		LoginPage loginpage = dashboardPage.clickOnLogoutButton();
		
		loginPage.enterUserName("pmpui-auth");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();
		
		dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.listPageCreateOidcClientButton();
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(data);
		oidcClientPage.enterNameOidcTextBox(GlobalConstants.AUTOMATION_LOWERCASE);
		String publicKeytemp = PmpTestUtil.generateJWKPublicKey();
		oidcClientPage.enterPublicKeyTextBox(publicKeytemp);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnSubmitButton();
		assertTrue(oidcClientPage.isOidcSubmittedSuccessfullyDisplayed(),
				GlobalConstants.isOidcSubmittedSuccessfullyDisplayed);
		oidcClientPage.clickConfirmationGoBackButton();
		
	}

	@Test(priority = 1, description = "Oidc Client Tabular View")
	public void oidcClientTabularView() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		AuthPolicyPage authPolicyPage = new AuthPolicyPage(driver);

		assertTrue(dashboardPage.isAuthenticationServicesDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServices();
		assertTrue(oidcClientPage.isOidcClientTabDisplayed(), GlobalConstants.isOidcClientTabDisplayed);

		assertTrue(oidcClientPage.isSubTitleOfTabularViewDisplayed(), GlobalConstants.isSubTitleOfTabularViewDisplayed);
		assertTrue(oidcClientPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertTrue(oidcClientPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);

		assertTrue(oidcClientPage.isPartnerIdHeaderDisplayed(), GlobalConstants.isPartnerIdHeaderDisplayed);
		assertTrue(oidcClientPage.isOrganisationHeaderDisplayed(), GlobalConstants.isOrganisationHeaderDisplayed);
		assertTrue(oidcClientPage.isPolicyGroupHeaderDisplayed(), GlobalConstants.isPolicyGroupHeaderDisplayed);
		assertTrue(oidcClientPage.isPolicyNameHeaderDisplayed(), GlobalConstants.isPolicyNameHeaderDisplayed);
		assertTrue(oidcClientPage.isOidcClientNameHeaderwDisplayed(), GlobalConstants.isOidcClientNameHeaderwDisplayed);
		assertTrue(oidcClientPage.isCreationDateHeaderDisplayed(), GlobalConstants.isCreationDateHeaderDisplayed);
		assertTrue(oidcClientPage.isStatusHeaderDisplayed(), GlobalConstants.isStatusHeaderDisplayed);
		assertTrue(oidcClientPage.isOidcClientIdHeaderDisplayed(), GlobalConstants.isOidcClientIdHeaderDisplayed);
		assertTrue(oidcClientPage.isActionHeaderDisplayed(), GlobalConstants.isActionHeaderDisplayed);

		assertTrue(oidcClientPage.isPartnerIdDescIconDisplayed(), GlobalConstants.isPartnerIdDescIconDisplayed);
		assertTrue(oidcClientPage.isPartnerIdAscIconDisplayed(), GlobalConstants.isPartnerIdAscIconDisplayed);
//		assertTrue(oidcClientPage.isOidcClientNameDescIconDisplayed(), GlobalConstants.isOidcClientNameDescIconDisplayed);
//		assertTrue(oidcClientPage.isOidcClientNameAscIconDisplayed(), GlobalConstants.isActionHeaderDisplayed);
		assertTrue(oidcClientPage.isPolicyGroupNameDescIconDisplayed(),
				GlobalConstants.isPolicyGroupNameDescIconDisplayed);
		assertTrue(oidcClientPage.isPolicyGroupNameAscIconDisplayed(),
				GlobalConstants.isPolicyGroupNameAscIconDisplayed);
		assertTrue(oidcClientPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescIconDisplayed);
		assertTrue(oidcClientPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameAscIconDisplayed);
		assertTrue(oidcClientPage.isCreatedDateTimeDescISconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescISconDisplayed);
		assertTrue(oidcClientPage.isCreatedDateTimeAscIconDisplayed(),
				GlobalConstants.isCreatedDateTimeAscIconDisplayed);
		assertTrue(oidcClientPage.isStatusDescIconDisplayed(), GlobalConstants.isStatusDescIconDisplayed);
		assertTrue(oidcClientPage.isStatusAscIconDisplayed(), GlobalConstants.isStatusAscIconDisplayed);
		assertTrue(oidcClientPage.isOrgNameDescIconDisplayed(), GlobalConstants.isOrgNameDescIconDisplayed);
		assertTrue(oidcClientPage.isOrgNameAscIconDisplayed(), GlobalConstants.isOrgNameAscIconDisplayed);

		oidcClientPage.clickOnPartnerIdDescIcon();
		oidcClientPage.clickOnPartnerIdAscIcon();
		oidcClientPage.clickOnOrgNameDescIcon();
		oidcClientPage.clickOnOrgNameAscIcon();
		oidcClientPage.clickOnPolicyGroupNameDescIcon();
		oidcClientPage.clickOnPolicyGroupNameAscIcon();
		oidcClientPage.clickOnPolicyNameDescIcon();
		oidcClientPage.clickOnPolicyNameAscIcon();
		oidcClientPage.clickOnCreatedDateTimeDescIcon();
		oidcClientPage.clickOnCreatedDateTimeAscIcon();
		oidcClientPage.clickOnStatusDescIcon();
		oidcClientPage.clickOnStatusAscIcon();

		oidcClientPage.clickOnFilterButton();
		assertTrue(oidcClientPage.isfilterResetButtonEnabled(), GlobalConstants.isfilterResetButtonEnabled);
		assertFalse(oidcClientPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);

		assertTrue(oidcClientPage.isPartnerIdFilterHeaderDisplayed(), GlobalConstants.isPartnerIdFilterHeaderDisplayed);
		assertTrue(oidcClientPage.isOrganisationFilterHeaderDisplayed(),
				GlobalConstants.isOrganisationFilterHeaderDisplayed);
		assertTrue(oidcClientPage.isPolicyGroupFilterHeaderDisplayed(),
				GlobalConstants.isPolicyGroupFilterHeaderDisplayed);
		assertTrue(oidcClientPage.isPolicyNameFilterHeaderDisplayed(),
				GlobalConstants.isPolicyNameFilterHeaderDisplayed);
		assertTrue(oidcClientPage.isOidcClientNameFilterHeaderDisplayed(),
				GlobalConstants.isOidcClientNameFilterHeaderDisplayed);
		assertTrue(oidcClientPage.isStatusFilterHeaderDisplayed(), GlobalConstants.isStatusFilterHeaderDisplayed);

		assertTrue(oidcClientPage.isPartnerIdPlaceHolderDisplayed(), GlobalConstants.isPartnerIdPlaceHolderDisplayed);
		assertTrue(oidcClientPage.isOrganisationPlaceHolderDisplayed(),
				GlobalConstants.isOrganisationPlaceHolderDisplayed);
		assertTrue(oidcClientPage.isPolicyGroupPlaceHolderDisplayed(),
				GlobalConstants.isPolicyGroupPlaceHolderDisplayed);
		assertTrue(oidcClientPage.isPolicyNamePlaceHolderDisplayed(), GlobalConstants.isPolicyNamePlaceHolderDisplayed);
		assertTrue(oidcClientPage.isOidcClientNamePlaceHolderDisplayed(),
				GlobalConstants.isOidcClientNamePlaceHolderDisplayed);
		assertTrue(oidcClientPage.isStatusPlaceHolderDisplayed(), GlobalConstants.isStatusPlaceHolderDisplayed);

		assertFalse(oidcClientPage.isApplyFilterButtonEnabled(), GlobalConstants.isApplyFilterButtonEnabled);
		oidcClientPage.enterInvalidOidcClientNameInFilter(GlobalConstants.Random_DATA);
		assertTrue(oidcClientPage.isApplyFilterButtonEnabled(), GlobalConstants.isApplyFilterButtonEnabled);
		oidcClientPage.clickOnApplyFilterButton();
		assertTrue(oidcClientPage.isNoResultFoundDisplayed(), GlobalConstants.isNoResultFoundDisplayed);

		oidcClientPage.clickOnStatusFilter();
		assertTrue(oidcClientPage.isActivatedStatusInFilterDisplayed(),
				GlobalConstants.isActivatedStatusInFilterDisplayed);
		assertTrue(oidcClientPage.isActivatedStatusInFilterDisplayed(),
				GlobalConstants.isActivatedStatusInFilterDisplayed);
		oidcClientPage.clickOnStatusFilter();
		oidcClientPage.clickOnFilterResetButton();
		assertFalse(oidcClientPage.isfilterResetButtonEnabled(), GlobalConstants.isfilterResetButtonEnabled);

		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		oidcClientPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		oidcClientPage.clickOnFilterCloseButton();
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.AUTOMATION_LOWERCASE);
		oidcClientPage.clickOnApplyFilterButton();

		oidcClientPage.clickOnPartnerIdDescIcon();
		oidcClientPage.clickOnPartnerIdAscIcon();
		oidcClientPage.clickOnOrgNameDescIcon();
		oidcClientPage.clickOnOrgNameAscIcon();
		oidcClientPage.clickOnPolicyGroupNameDescIcon();
		oidcClientPage.clickOnPolicyGroupNameAscIcon();
		oidcClientPage.clickOnPolicyNameDescIcon();
		oidcClientPage.clickOnPolicyNameAscIcon();
		oidcClientPage.clickOnCreatedDateTimeDescIcon();
		oidcClientPage.clickOnCreatedDateTimeAscIcon();
		oidcClientPage.clickOnStatusDescIcon();
		oidcClientPage.clickOnStatusAscIcon();

		oidcClientPage.clickOnActivatedOidcClient();
		assertTrue(oidcClientPage.isOidcClientDetailsPageDisplayed(), GlobalConstants.isOidcClientDetailsPageDisplayed);
		oidcClientPage.clickOidcClientDetailsBackButton();

		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		oidcClientPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.AUTOMATION_LOWERCASE);
		oidcClientPage.clickOnApplyFilterButton();

		oidcClientPage.clickOnActionButton();
		assertTrue(oidcClientPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		assertTrue(oidcClientPage.isDeactivateButtonEnabled(),
				GlobalConstants.isDeactivateButtonEnabled);

		oidcClientPage.clickOnActionButton();
		assertTrue(oidcClientPage.isOidcClientIdEyeIconDisplayed(), GlobalConstants.isOidcClientIdEyeIconDisplayed);
		oidcClientPage.clickOnOidcClientIdEyeIcon();
		assertTrue(oidcClientPage.isPolicyNameAsTitleDisplayed(), GlobalConstants.isPolicyNameAsTitleDisplayed);
		assertTrue(oidcClientPage.isPartnerIdAsSubTitleDisplayed(), GlobalConstants.isPartnerIdAsSubTitleDisplayed);
		assertTrue(oidcClientPage.isOidcClientIdLabelInEyeIconPopupDisplayed(),
				GlobalConstants.isOidcClientIdLabelInEyeIconPopupDisplayed);
		assertTrue(oidcClientPage.iscopyIdButtonDisplayed(), GlobalConstants.iscopyIdButtonDisplayed);
		assertTrue(oidcClientPage.isCopyIdCloseButtonDisplayed(), GlobalConstants.isCopyIdCloseButtonDisplayed);

		oidcClientPage.clickOnCopyIdButton();
		assertTrue(oidcClientPage.isCopyIdCloseButtonDisplayed(), GlobalConstants.isCopyIdCloseButtonDisplayed);
		oidcClientPage.clickOnCopyIdCloseButton();
		
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		oidcClientPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_OIDCPOLICY2);
		oidcClientPage.clickOnApplyFilterButton();
		
		oidcClientPage.clickOnActionButton();
		assertTrue(oidcClientPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		oidcClientPage.clickOnDeactivateButton();
		assertFalse(oidcClientPage.isDeactivateOidcClientPopupDisplayed(),
				GlobalConstants.isDeactivateOidcClientPopupDisplayed);
		
		oidcClientPage.clickOidcShowCopyPopupButton();
		assertFalse(oidcClientPage.iscopyIdButtonDisplayed(),
				GlobalConstants.iscopyIdButtonDisplayed);
		
		oidcClientPage.clickOnDeactivatedOidcRow();
		assertFalse(oidcClientPage.isOidcClientDetailsPageDisplayed(),
				GlobalConstants.isOidcClientDetailsPageDisplayed);
		assertTrue(oidcClientPage.isStatusDeavtivatedDisplayed(),
				GlobalConstants.isStatusDeavtivatedDisplayed);

		oidcClientPage.clickOnFilterResetButton();
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
		
		oidcClientPage.clickOnTitleBackButton();
		assertTrue(dashboardPage.isAuthenticationServicesDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);

	}

	@Test(priority = 3, description = "Oidc Client View Details")
	public void oidcClientViewDetails() {

		DashboardPage dashboardpage = new DashboardPage(driver);

		OidcClientPage oidcClientPage = dashboardpage.clickOnAuthenticationServices();
		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		oidcClientPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.AUTOMATION_LOWERCASE);
		oidcClientPage.clickOnApplyFilterButton();

		oidcClientPage.clickOnActionButton();
		assertTrue(oidcClientPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		oidcClientPage.clickOnViewButton();

		assertTrue(oidcClientPage.isOidcClientDetailsPageDisplayed(), GlobalConstants.isOidcClientDetailsPageDisplayed);
		assertTrue(oidcClientPage.isViewOidcClientDetailsPageTitleDisplayed(),
				GlobalConstants.isViewOidcClientDetailsPageTitleDisplayed);
		assertTrue(oidcClientPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertTrue(oidcClientPage.isActivatedOidcClientIdElementDisplayed(),
				GlobalConstants.isActivatedOidcClientIdElementDisplayed);
		
		assertTrue(oidcClientPage.isOidcClientNameLabelDisplayed(), GlobalConstants.isOidcClientNameLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientIdLabelDisplayed(), GlobalConstants.isOidcClientIdLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPartnerIdLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsPartnerIdLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPartnerIdContextDisplayed(),
				GlobalConstants.isOidcClientDetailsPartnerIdContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPartnerTypeLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsPartnerTypeLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPartnerTypeContextDisplayed(),
				GlobalConstants.isOidcClientDetailsPartnerTypeContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsOrgNameLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsOrgNameLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsOrgNameContextDisplayed(),
				GlobalConstants.isOidcClientDetailsOrgNameContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyGroupLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyGroupLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyGroupNameContextDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyGroupNameContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyGoupDescriptionLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyGoupDescriptionLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyGroupDescriptionContextDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyGroupDescriptionContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyNameLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyNameLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyNameContextDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyNameContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyNameDescriptionLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyNameDescriptionLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyDescriptionContextDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyDescriptionContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPublicKeyLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsPublicKeyLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPublicKeyContextDisplayed(),
				GlobalConstants.isOidcClientDetailsPublicKeyContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsLogoUriLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsLogoUriLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsLogoUriContextDisplayed(),
				GlobalConstants.isOidcClientDetailsLogoUriContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsRedirectUrisLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsRedirectUrisLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsRedirectUrisContextDisplayed(),
				GlobalConstants.isOidcClientDetailsRedirectUrisContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsGrantTypesLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsGrantTypesLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsGrantTypesContextDisplayed(),
				GlobalConstants.isOidcClientDetailsGrantTypesContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsActivatedStatusDisplayed(),
				GlobalConstants.isOidcClientDetailsActivatedStatusDisplayed);
		assertTrue(oidcClientPage.isCreatedOnLabelDisplayed(), GlobalConstants.isCreatedOnLabelDisplayed);
		assertTrue(oidcClientPage.isCreatedDateDisplayed(), GlobalConstants.isCreatedDateDisplayed);
		
		assertTrue(oidcClientPage.isOidcClientDetailsCopyIdDisplayed(),
				GlobalConstants.isOidcClientDetailsCopyIdDisplayed);
		oidcClientPage.clickOnOidcClientDetailsCopyId();
		assertTrue(oidcClientPage.isCopiedTextDisplayed(),
				GlobalConstants.isCopiedTextDisplayed);
		oidcClientPage.clickOnOidcClientDetailsCopyId();
		assertTrue(oidcClientPage.isCopiedTextDisplayed(),
				GlobalConstants.isCopiedTextDisplayed);
		oidcClientPage.clickOnOidcClientDetailsCopyId();
		assertTrue(oidcClientPage.isCopiedTextDisplayed(),
				GlobalConstants.isCopiedTextDisplayed);
		
		assertTrue(oidcClientPage.isOidcClientDetailsBackButtonDisplayed(),
				GlobalConstants.isOidcClientDetailsBackButtonDisplayed);
		oidcClientPage.clickOidcClientDetailsBackButton();
		
		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		oidcClientPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_OIDCPOLICY2);
		oidcClientPage.clickOnApplyFilterButton();	
		oidcClientPage.clickOnActionButton();
		
		oidcClientPage.clickOnViewButton();
		assertTrue(oidcClientPage.isOidcClientDetailsPageDisplayed(),
				GlobalConstants.isOidcClientDetailsPageDisplayed);
		assertTrue(oidcClientPage.isOidcDetailsPageStatusDeactivatedDisplayed(),
				GlobalConstants.isOidcDetailsPageStatusDeactivatedDisplayed);
		assertTrue(oidcClientPage.isDeactivatedOidcClientIdElementDisplayed(),
				GlobalConstants.isDeactivatedOidcClientIdElementDisplayed);
		
		assertTrue(oidcClientPage.isOidcClientDetailsCopyIdDisplayed(),
				GlobalConstants.isOidcClientDetailsCopyIdDisplayed);
		oidcClientPage.clickOnOidcClientDetailsCopyId();
		assertFalse(oidcClientPage.isCopiedTextDisplayed(),
				GlobalConstants.isCopiedTextDisplayed);
		oidcClientPage.clickOnTitleBackButton();
		assertTrue(oidcClientPage.isOidcClientTabDisplayed(), GlobalConstants.isOidcClientTabDisplayed);
		
	}
	
	@Test(priority = 4, description = "ApiKey Deactivate")
	public void apiKeyDeactivate() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		ApiKeyPage apiKeyPage = new ApiKeyPage(driver);
		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServices();
		BasePage basePage = new BasePage(driver);
		
		assertTrue(oidcClientPage.isOidcClientTabDisplayed(), GlobalConstants.isOidcClientTabDisplayed);
		assertTrue(oidcClientPage.isApiKeyTabDisplayed(), GlobalConstants.isApiKeyTabDisplayed);
		oidcClientPage.clickOnApiKeyTab();
		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		apiKeyPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		apiKeyPage.enterValidApiKeyNameInFilter(GlobalConstants.DEACTIVATE_APIKEY);
		apiKeyPage.clickOnApplyFilterButton();
		
		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		assertTrue(apiKeyPage.isDeactivateButtonEnabled(),
				GlobalConstants.isDeactivateButtonEnabled);
		
		apiKeyPage.clickOnApiKeyDeactivateButton();
		assertTrue(apiKeyPage.isApiKeyInAdminDeactivatePopupDisplayed(),
				GlobalConstants.isApiKeyDeactivatePopupDisplayed);
		assertTrue(apiKeyPage.isApiKeyInAdminDeactivateTitleDisplayed(),
				GlobalConstants.isApiKeyDeactivateTitleDisplayed);
		assertTrue(apiKeyPage.isApiKeyInAdminDeactivateInfoTextDisplayed(),
				GlobalConstants.isApiKeyDeactivationInfoTextDisplayed);
		assertTrue(apiKeyPage.isDeactivateSubmitButtonAvailable(),
				GlobalConstants.isDeactivateSubmitButtonAvailable);
		assertTrue(apiKeyPage.isDeactivateCancelButtonAvailable(),
				GlobalConstants.isDeactivateCancelButtonAvailable);
		
		apiKeyPage.clickOnDeactivateCancelButton();
		apiKeyPage.clickOnFilterResetButton();
		assertTrue(apiKeyPage.isSubTitleOfTabularViewDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewDisplayed);
		
		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		apiKeyPage.enterValidApiKeyNameInFilter(GlobalConstants.DEACTIVATE_APIKEY);
		apiKeyPage.clickOnApplyFilterButton();
		
		apiKeyPage.clickOnActionButton();
		apiKeyPage.clickOnApiKeyDeactivateButton();
		apiKeyPage.clickOnDeactivateSubmitButton();
		
		basePage.navigateBack();
		basePage.navigateForword();
	
		dashboardPage.clickOnProfileDropdown();
		LoginPage loginpage = dashboardPage.clickOnLogoutButton();
		
		loginPage.enterUserName("pmpui-auth");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();
		
		dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnApiKeyTab();
		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox(GlobalConstants.DEACTIVATE_APIKEY);
		apiKeyPage.clickOnSubmitButton();
		assertTrue(apiKeyPage.isDuplicateApiKeyNameErrorMessageDisplayed(),
				GlobalConstants.isDuplicateApiKeyNameErrorMessageDisplayed);
		apiKeyPage.clickOnDuplicateApiKeyNameErrorMessageCloseButton();
		
	}
	
	@Test(priority = 5, description = "API Key Tabular View")
	public void apiKeyTabularView() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		AuthPolicyPage authPolicyPage = new AuthPolicyPage(driver);
		ApiKeyPage apiKeyPage = new ApiKeyPage(driver);

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
		assertTrue(apiKeyPage.isApiKeyNameAscIconDisplayed(), GlobalConstants.isActionHeaderDisplayed);
		assertTrue(apiKeyPage.isPolicyGroupNameDescIconDisplayed(),
				GlobalConstants.isPolicyGroupNameDescIconDisplayed);
		assertTrue(apiKeyPage.isPolicyGroupNameAscIconDisplayed(),
				GlobalConstants.isPolicyGroupNameAscIconDisplayed);
		assertTrue(apiKeyPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescIconDisplayed);
		assertTrue(apiKeyPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameAscIconDisplayed);
		assertTrue(apiKeyPage.isCreatedDateTimeDescISconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescISconDisplayed);
		assertTrue(apiKeyPage.isCreatedDateTimeAscIconDisplayed(),
				GlobalConstants.isCreatedDateTimeAscIconDisplayed);
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
		assertTrue(apiKeyPage.isPolicyGroupFilterHeaderDisplayed(),
				GlobalConstants.isPolicyGroupFilterHeaderDisplayed);
		assertTrue(apiKeyPage.isPolicyNameFilterHeaderDisplayed(),
				GlobalConstants.isPolicyNameFilterHeaderDisplayed);
		assertTrue(apiKeyPage.isApiKeyNameFilterHeaderDisplayed(),
				GlobalConstants.isOidcClientNameFilterHeaderDisplayed);
		assertTrue(apiKeyPage.isStatusFilterHeaderDisplayed(), GlobalConstants.isStatusFilterHeaderDisplayed);

		assertTrue(apiKeyPage.isPartnerIdPlaceHolderDisplayed(), GlobalConstants.isPartnerIdPlaceHolderDisplayed);
		assertTrue(apiKeyPage.isOrganisationPlaceHolderDisplayed(),
				GlobalConstants.isOrganisationPlaceHolderDisplayed);
		assertTrue(apiKeyPage.isPolicyGroupPlaceHolderDisplayed(),
				GlobalConstants.isPolicyGroupPlaceHolderDisplayed);
		assertTrue(apiKeyPage.isPolicyNamePlaceHolderDisplayed(), GlobalConstants.isPolicyNamePlaceHolderDisplayed);
		assertTrue(apiKeyPage.isApiKeyNamePlaceHolderDisplayed(),
				GlobalConstants.isApiKeyNamePlaceHolderDisplayed);
		assertTrue(apiKeyPage.isStatusPlaceHolderDisplayed(), GlobalConstants.isStatusPlaceHolderDisplayed);

		assertFalse(apiKeyPage.isApplyFilterButtonEnabled(), GlobalConstants.isApplyFilterButtonEnabled);
		apiKeyPage.enterInvalidDataInAdminApiKeyNameFilter(GlobalConstants.Random_DATA);
		assertTrue(apiKeyPage.isApplyFilterButtonEnabled(), GlobalConstants.isApplyFilterButtonEnabled);
		apiKeyPage.clickOnApplyFilterButton();
		assertTrue(apiKeyPage.isNoResultFoundDisplayed(), GlobalConstants.isNoResultFoundDisplayed);

		apiKeyPage.clickOnFilterResetButton();
		apiKeyPage.clickOnFilterButton();
		apiKeyPage.clickOnStatusFilter();
		assertTrue(apiKeyPage.isActivatedStatusInFilterDisplayed(),
				GlobalConstants.isActivatedStatusInFilterDisplayed);
		assertTrue(apiKeyPage.isDeactivatedStatusInFilterDisplayed(),
				GlobalConstants.isDeactivatedStatusInFilterDisplayed);
		apiKeyPage.clickOnStatusFilter();
		apiKeyPage.clickOnFilterResetButton();
		assertFalse(apiKeyPage.isfilterResetButtonEnabled(), GlobalConstants.isfilterResetButtonEnabled);

		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		apiKeyPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		apiKeyPage.clickOnFilterCloseButton();
		apiKeyPage.enterValidApiKeyNameInFilter(GlobalConstants.ACTIVATE_ADMINAPIKEY);
		apiKeyPage.clickOnApplyFilterButton();

		apiKeyPage.clickOnActivatedAdminApiKey();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isOidcClientDetailsPageDisplayed);
		apiKeyPage.clickOnViewApiKeyBackButton();

		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		apiKeyPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		apiKeyPage.enterValidApiKeyNameInFilter(GlobalConstants.ACTIVATE_ADMINAPIKEY);
		apiKeyPage.clickOnApplyFilterButton();

		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		assertTrue(apiKeyPage.isDeactivateButtonEnabled(),
				GlobalConstants.isDeactivateButtonEnabled);
		
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		apiKeyPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		apiKeyPage.enterValidApiKeyNameInFilter(GlobalConstants.DEACTIVATE_APIKEY);
		apiKeyPage.clickOnApplyFilterButton();
		
		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		apiKeyPage.clickOnApiKeyDeactivateButton();
		assertFalse(apiKeyPage.isApiKeyDeactivatePopupDisplayed(),
				GlobalConstants.isApiKeyDeactivatePopupDisplayed);
		
		apiKeyPage.clickOnDeactivatedApiKeyRow();
		assertFalse(apiKeyPage.isApiKeyDetailsPageDisplayed(),
				GlobalConstants.isApiKeyDetailsPageDisplayed);
		assertTrue(apiKeyPage.isStatusDeavtivatedDisplayed(),
				GlobalConstants.isStatusDeavtivatedDisplayed);
		
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
	
	@Test(priority = 6, description = "Api Key View Details")
	public void apiKeyViewDetails() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		ApiKeyPage apiKeyPage = new ApiKeyPage(driver);

		OidcClientPage oidcClientPage = dashboardpage.clickOnAuthenticationServices();
		oidcClientPage.clickOnApiKeyTab();
		oidcClientPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		apiKeyPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		apiKeyPage.enterValidApiKeyNameInFilter(GlobalConstants.ACTIVATE_ADMINAPIKEY);
		apiKeyPage.clickOnApplyFilterButton();
		
		apiKeyPage.clickOnActivatedAdminApiKey();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(),
				GlobalConstants.isApiKeyDetailsPageDisplayed);
		apiKeyPage.clickOnBreadCombButton();
		
		oidcClientPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		apiKeyPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		apiKeyPage.enterValidApiKeyNameInFilter(GlobalConstants.ACTIVATE_ADMINAPIKEY);
		apiKeyPage.clickOnApplyFilterButton();
		apiKeyPage.clickOnActionButton();
		assertTrue(apiKeyPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		apiKeyPage.clickOnViewButton();

		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isApiKeyDetailsPageDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPageTitleDisplayed(),
				GlobalConstants.isApiKeyDetailsPageTitleDisplayed);
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
		assertTrue(apiKeyPage.isApiKeyStatusActivatedDisplayed(),
				GlobalConstants.isApiKeyStatusActivatedDisplayed);
		assertTrue(apiKeyPage.isCreatedOnLabelDisplayed(), GlobalConstants.isCreatedOnLabelDisplayed);
		assertTrue(apiKeyPage.isCreatedDateDisplayed(), GlobalConstants.isCreatedDateDisplayed);
		
		assertTrue(apiKeyPage.isViewApiKeyBackButtonDisplayed(),
				GlobalConstants.isViewApiKeyBackButtonDisplayed);
		apiKeyPage.clickOnViewApiKeyBackButton();
		
		apiKeyPage.clickOnFilterButton();
		apiKeyPage.enterPartnerIdInFilter(GlobalConstants.AUTHPARTNER);
		apiKeyPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		apiKeyPage.enterValidApiKeyNameInFilter(GlobalConstants.DEACTIVATE_APIKEY);
		apiKeyPage.clickOnApplyFilterButton();
		
		apiKeyPage.clickOnDeactivatedApiKeyRow();
		assertFalse(apiKeyPage.isApiKeyDetailsPageDisplayed(),
				GlobalConstants.isApiKeyDetailsPageDisplayed);
		
		apiKeyPage.clickOnActionButton();
		apiKeyPage.clickOnViewButton();
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(),
				GlobalConstants.isApiKeyDetailsPageDisplayed);
		assertTrue(apiKeyPage.isApiKeyStatusDeactivatedDisplayed(),
				GlobalConstants.isApiKeyStatusDeactivatedDisplayed);		
		oidcClientPage.clickOnTitleBackButton();
		assertTrue(oidcClientPage.isOidcClientTabDisplayed(), GlobalConstants.isOidcClientTabDisplayed);
		
	}
	
}
