package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.fw.util.PmpTestUtil;
import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
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

		oidcClientPage.clickOnOidcStatusFilter();
		assertTrue(oidcClientPage.isActivatedStatusInFilterDisplayed(),
				GlobalConstants.isActivatedStatusInFilterDisplayed);
		assertTrue(oidcClientPage.isActivatedStatusInFilterDisplayed(),
				GlobalConstants.isActivatedStatusInFilterDisplayed);
		oidcClientPage.clickOnOidcStatusFilter();
		oidcClientPage.clickOnActivatedStatusInFilter();
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
		oidcClientPage.enterPartnerIdInFilter("pmpui-auth");
		oidcClientPage.enterPolicyGroupInFilter("automationui policy group");
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.AUTOMATION_LOWERCASE);
		oidcClientPage.clickOnApplyFilterButton();

		oidcClientPage.clickOnOidcClientActionButton();
		assertTrue(oidcClientPage.isOidcClientViewButtonEnabled(), GlobalConstants.isOidcClientViewButtonEnabled);
		assertTrue(oidcClientPage.isOidcClientDeactivateButtonEnabled(),
				GlobalConstants.isOidcClientDeactivateButtonEnabled);

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
		
		oidcClientPage.enterPartnerIdInFilter("pmpui-auth");
		oidcClientPage.enterPolicyGroupInFilter("automationui policy group");
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_POLICY2);
		oidcClientPage.clickOnApplyFilterButton();
		
		oidcClientPage.clickOnOidcClientActionButton();
		assertTrue(oidcClientPage.isOidcClientViewButtonEnabled(), GlobalConstants.isOidcClientViewButtonEnabled);
		assertFalse(oidcClientPage.isOidcClientDeactivateButtonEnabled(),
				GlobalConstants.isOidcClientDeactivateButtonEnabled);
		
		oidcClientPage.clickOidcShowCopyPopupButton();
		assertFalse(oidcClientPage.iscopyIdButtonDisplayed(),
				GlobalConstants.iscopyIdButtonDisplayed);
		
		oidcClientPage.clickOnDeactivatedOidcRow();
		assertFalse(oidcClientPage.isOidcClientDetailsPageDisplayed(),
				GlobalConstants.isOidcClientDetailsPageDisplayed);
		assertTrue(oidcClientPage.isStatusDeavtivatedDisplayed(),
				GlobalConstants.isStatusDeavtivatedDisplayed);

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
	
	@Test(priority = 2, description = "Oidc Client Deactivate")
	public void oidcClientDeactivate() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServices();
		
		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter("pmpui-auth");
		oidcClientPage.enterPolicyGroupInFilter("automationui policy group");
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_POLICY);
		oidcClientPage.clickOnApplyFilterButton();
		
		oidcClientPage.clickOnOidcClientActionButton();
		assertTrue(oidcClientPage.isOidcClientViewButtonEnabled(), GlobalConstants.isOidcClientViewButtonEnabled);
		assertTrue(oidcClientPage.isOidcClientDeactivateButtonEnabled(),
				GlobalConstants.isOidcClientDeactivateButtonEnabled);
		
		oidcClientPage.clickOnOidcClientDeactivateButton();
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
		oidcClientPage.enterPartnerIdInFilter("pmpui-auth");
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_POLICY);
		oidcClientPage.clickOnApplyFilterButton();
		
		oidcClientPage.clickOnOidcClientActionButton();
		oidcClientPage.clickOnOidcClientDeactivateButton();
		oidcClientPage.clickOnDeactivateSubmitButtonButton();
		
		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter("pmpui-auth");
		oidcClientPage.selectDeactivatedStatusInFilter();
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_POLICY);
		oidcClientPage.clickOnApplyFilterButton();
		oidcClientPage.clickOidcShowCopyPopupButton();
		assertFalse(oidcClientPage.iscopyIdButtonDisplayed(),
				GlobalConstants.iscopyIdButtonDisplayed);
		
		oidcClientPage.clickOnDeactivatedOidcRow();
		assertFalse(oidcClientPage.isOidcClientDetailsPageDisplayed(),
				GlobalConstants.isOidcClientDetailsPageDisplayed);
		assertTrue(oidcClientPage.isStatusDeavtivatedDisplayed(),
				GlobalConstants.isStatusDeavtivatedDisplayed);
		
		oidcClientPage.clickOnOidcClientActionButton();
		assertTrue(oidcClientPage.isOidcClientViewButtonEnabled(),
				GlobalConstants.isOidcClientViewButtonEnabled);
		assertFalse(oidcClientPage.isOidcClientDeactivateButtonEnabled(),
				GlobalConstants.isOidcClientDeactivateButtonEnabled);
		
		oidcClientPage.clickOnOidcDetailsViewButton();
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
		oidcClientPage.enterNameOidcTextBox(GlobalConstants.DEACTIVATE_POLICY);
		String publicKeytemp = PmpTestUtil.generateJWKPublicKey();
		oidcClientPage.enterPublicKeyTextBox(publicKeytemp);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnSubmitButton();
		assertTrue(oidcClientPage.isOidcSubmittedSuccessfullyDisplayed(),
				GlobalConstants.isOidcSubmittedSuccessfullyDisplayed);
		oidcClientPage.clickConfirmationGoBackButton();
		
	}

	@Test(priority = 3, description = "Oidc Client View Details")
	public void oidcClientViewDetails() {

		DashboardPage dashboardpage = new DashboardPage(driver);

		OidcClientPage oidcClientPage = dashboardpage.clickOnAuthenticationServices();
		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter("pmpui-auth");
		oidcClientPage.enterPolicyGroupInFilter("automationui policy group");
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.AUTOMATION_LOWERCASE);
		oidcClientPage.clickOnApplyFilterButton();

		oidcClientPage.clickOnOidcClientActionButton();
		assertTrue(oidcClientPage.isOidcClientViewButtonEnabled(), GlobalConstants.isOidcClientViewButtonEnabled);
		oidcClientPage.clickOnOidcClientViewButton();

		assertTrue(oidcClientPage.isOidcClientDetailsPageDisplayed(), GlobalConstants.isOidcClientDetailsPageDisplayed);
		assertTrue(oidcClientPage.isViewOidcClientDetailsPageTitleDisplayed(),
				GlobalConstants.isViewOidcClientDetailsPageTitleDisplayed);
		assertTrue(oidcClientPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsCopyIdDisplayed(),
				GlobalConstants.isOidcClientDetailsCopyIdDisplayed);
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
		
		
		assertTrue(oidcClientPage.isOidcClientDetailsBackButtonDisplayed(),
				GlobalConstants.isOidcClientDetailsBackButtonDisplayed);
		oidcClientPage.clickOidcClientDetailsBackButton();
		
		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter("pmpui-auth");
		oidcClientPage.enterPolicyGroupInFilter("automationui policy group");
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_POLICY);
		oidcClientPage.clickOnApplyFilterButton();
		
		oidcClientPage.clickOnOidcClientActionButton();
		assertTrue(oidcClientPage.isOidcClientViewButtonEnabled(),
				GlobalConstants.isOidcClientViewButtonEnabled);
		assertFalse(oidcClientPage.isOidcClientDeactivateButtonEnabled(),
				GlobalConstants.isOidcClientDeactivateButtonEnabled);
		
		oidcClientPage.clickOnOidcDetailsViewButton();
		assertTrue(oidcClientPage.isOidcClientDetailsPageDisplayed(),
				GlobalConstants.isOidcClientDetailsPageDisplayed);
		assertTrue(oidcClientPage.isOidcDetailsPageStatusDeactivatedDisplayed(),
				GlobalConstants.isOidcDetailsPageStatusDeactivatedDisplayed);
		assertTrue(oidcClientPage.isDeactivatedOidcClientIdElementDisplayed(),
				GlobalConstants.isDeactivatedOidcClientIdElementDisplayed);
		

	}
}
