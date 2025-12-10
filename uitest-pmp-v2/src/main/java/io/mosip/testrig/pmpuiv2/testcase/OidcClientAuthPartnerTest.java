package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;
import io.mosip.testrig.pmpuiv2.kernel.util.KeycloakUserManager;
import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.OidcClientPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class OidcClientAuthPartnerTest extends BaseClass {
	private BasePage basePage;
	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private AuthPolicyPage authPolicyPage;
	private OidcClientPage oidcClientPage;

	@Test(priority = 1, description = "Creation OIDC client")
	public void createOidcClient() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		oidcClientPage = new OidcClientPage(driver);

		loginAsAuthPartner();

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		assertTrue(oidcClientPage.isOidcClientTabDisplayed(), GlobalConstants.isOidcClientTabDisplayed);

		assertTrue(oidcClientPage.isCreateOidcClientDisplayed(), GlobalConstants.isCreateOIDCClientDisplayed);
		assertTrue(oidcClientPage.isPartnerIDHeaderTextDisplayed(), GlobalConstants.isPartnerIDHeaderTextDisplayed);
		assertTrue(oidcClientPage.isPolicyGroupHeaderTextDisplayed(), GlobalConstants.isPolicyGroupHeaderTextDisplayed);
		assertTrue(oidcClientPage.isPolicyNameHeaderTextDisplayed(), GlobalConstants.isPolicyNameHeaderTextDisplayed);
		assertTrue(oidcClientPage.isOIDCClientNameHeaderTextDisplayed(),
				GlobalConstants.isOIDCClientNameHeaderTextDisplayed);
		assertTrue(oidcClientPage.isCreatedDateHeaderTextDisplayed(), GlobalConstants.isCreatedDateHeaderTextDisplayed);
		assertTrue(oidcClientPage.isStatusHeaderTextDisplayed(), GlobalConstants.isStatusHeaderTextDisplayed);
		assertTrue(oidcClientPage.isOIDCClientIDHeaderTextDisplayed(),
				GlobalConstants.isOIDCClientIDHeaderTextDisplayed);
		assertTrue(oidcClientPage.isActionHeaderTextDisplayed(), GlobalConstants.isActionHeaderTextDisplayed);
		oidcClientPage.clickOnCreateOidcClientButton();

		assertTrue(oidcClientPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		oidcClientPage.selectPartnerIdDropdown();

		assertTrue(oidcClientPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		oidcClientPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		oidcClientPage.enterNameOidcTextBox(GlobalConstants.DEFAULT_POLICY);
		String publicKeytemp = PmpTestUtil.generateJWKPublicKey();
		oidcClientPage.enterPublicKeyTextBox(publicKeytemp);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnSubmitButton();
		assertTrue(oidcClientPage.isOidcSubmittedSuccessfullyDisplayed(),
				GlobalConstants.isOidcSubmittedSuccessfullyDisplayed);
		oidcClientPage.clickConfirmationGoBackButton();

		oidcClientPage.clickOidcShowCopyPopupButton();
		oidcClientPage.clickOnCopyIdButton();

		oidcClientPage.clickOnCopyIdCloseButton();

		assertTrue(oidcClientPage.isPartnerIdDescIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(oidcClientPage.isPartnerIdAscIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(oidcClientPage.isOidcClientNameDescIconDisplayed(), GlobalConstants.isOidcClientNameDescIcon);
		assertTrue(oidcClientPage.isOidcClientNameAscIconDisplayed(), GlobalConstants.isOidcClientNameAscIcon);
		assertTrue(oidcClientPage.isPolicyGroupNameDescIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(oidcClientPage.isPolicyGroupNameAscIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(oidcClientPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(oidcClientPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(oidcClientPage.isCreatedDateTimeDescISconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(oidcClientPage.isCreatedDateTimeAscIconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(oidcClientPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);
		assertTrue(oidcClientPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonInAuthenticationDisplayed);

		assertTrue(oidcClientPage.isOidcDetailsElipsisButtonDisplayed(),
				GlobalConstants.isOidcDetailsElipsisButtonDisplayed);
		oidcClientPage.clickOidcDetailsElipsisButton();
		assertTrue(oidcClientPage.isOidcDetailsViewButtonDisplayed(), GlobalConstants.isOidcDetailsViewButtonDisplayed);
		assertTrue(oidcClientPage.isOidcEditButtonDisplayed(), GlobalConstants.isOidcEditButtonDisplayed);
		assertTrue(oidcClientPage.isOidcDeactivateButtonDisplayed(), GlobalConstants.isOidcDeactivateButtonDisplayed);
		oidcClientPage.clickOnOidcDetailsViewButton();
		assertTrue(oidcClientPage.isOidcClientDetailsPartnerIdLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsPartnerIdLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPartnerIdContextDisplayed(),
				GlobalConstants.isOidcClientDetailsPartnerIdContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyGroupLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyGroupLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyGroupNameContextDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyGroupNameContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyNameLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyNameLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyNameContextDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyNameContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyGoupDescriptionLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyGoupDescriptionLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyGroupDescriptionContextDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyGroupDescriptionContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyNameDescriptionLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyNameDescriptionLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPolicyDescriptionContextDisplayed(),
				GlobalConstants.isOidcClientDetailsPolicyDescriptionContextDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPartnerTypeLabelDisplayed(),
				GlobalConstants.isOidcClientDetailsPartnerTypeLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsPartnerTypeContextDisplayed(),
				GlobalConstants.isOidcClientDetailsPartnerTypeContextDisplayed);
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
		assertTrue(oidcClientPage.isOidcClientDetailsCopyIdDisplayed(),
				GlobalConstants.isOidcClientDetailsCopyIdDisplayed);
		assertTrue(oidcClientPage.isOidcClientNameLabelDisplayed(), GlobalConstants.isOidcClientNameLabelDisplayed);
		assertTrue(oidcClientPage.isOidcClientDetailsBackButtonDisplayed(),
				GlobalConstants.isOidcClientDetailsBackButtonDisplayed);
		oidcClientPage.clickOidcClientDetailsBackButton();

		oidcClientPage.clickOnFilterButton();
		oidcClientPage.clickOnOidcPartnerIdFilter();
		oidcClientPage.clickOnOidcSelectPolicyGroupFilter();
		oidcClientPage.clickOnOidcSelectPolicyNameFilter();
		oidcClientPage.clickOnOidcSelectClientNameFilter();
		oidcClientPage.selectActivatedStatusInFilter();

		assertTrue(oidcClientPage.isfilterResetButtonDisplayed(), GlobalConstants.isfilterResetButtonDisplayed);
		oidcClientPage.clickOnFilterResetButton();
		assertTrue(oidcClientPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);

		createOidcClient(GlobalConstants.ALPHANUMERIC);

		createOidcClient(GlobalConstants.AUTOMATION_UPPERCASE);

		createOidcClient(GlobalConstants.DEACTIVATE_OIDCPOLICY2);

		createOidcClient(GlobalConstants.DEACTIVATE_AUTHPOLICY);

		createOidcClient(GlobalConstants.AUTOMATION_LOWERCASE);

		oidcClientPage.clickOnListCreateOidcClientButton();
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		oidcClientPage.enterNameOidcTextBox(GlobalConstants.SPACE);
		oidcClientPage.enterPublicKeyTextBox(GlobalConstants.SPACE);
		oidcClientPage.enterLogoUrTextBox(GlobalConstants.SPACE);
		oidcClientPage.enterRedirectUriTextBox(GlobalConstants.SPACE);

		assertTrue(oidcClientPage.isEnterValidUriForLogoUriTextDisplayed(),
				GlobalConstants.isEnterValidLogoUriTextDisplayed);
		assertTrue(oidcClientPage.isEnterValidUriForRedirectUriTextDisplayed(),
				GlobalConstants.isEnterRedirectUriTextDisplayed);
		oidcClientPage.clickOnCreateOidcClearForm();

		basePage.refreshThePage();
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		oidcClientPage.enterNameOidcTextBox(GlobalConstants.OIDCNAME);
		oidcClientPage.navigateBackDefaultButton();
		assertTrue(oidcClientPage.isBrowserBackConfirmationPopupDisplayed(),
				GlobalConstants.isBrowserBackConfirmationPopupDisplayed);
		assertTrue(oidcClientPage.isBrowserBackProceedButtonAvailable(),
				GlobalConstants.isBrowserBackProceedButtonAvailable);
		assertTrue(oidcClientPage.isBrowserBackCancelButtonAvailable(),
				GlobalConstants.isBrowserBackCancelButtonAvailable);
		oidcClientPage.clickOnBrowserConfirmationPopupCancelBtn();
		oidcClientPage.navigateBackDefaultButton();
		oidcClientPage.clickOnBrowserConfirmationPopupProceedBtn();

	}

	@Test(priority = 2, description = "Deleting second redirect uri")
	public void deletingSecondRedirectUri() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnListCreateOidcClientButton();
		oidcClientPage.clickOnRedirectUriAddNew();
		assertTrue(oidcClientPage.isRedirectUri2TextBoxDisplayed(), GlobalConstants.isRedirectUri2TextBoxDisplayed);
		oidcClientPage.clickOnRedirectUri2Delete();
		assertFalse(oidcClientPage.isRedirectUri2TextBoxDisplayed(), GlobalConstants.isRedirectUri2TextBoxDisplayed);
	}

	@Test(priority = 3, description = "Adding second redirect uri")
	public void addingSecondRedirectUri() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnListCreateOidcClientButton();
		oidcClientPage.clickOnRedirectUriAddNew();
		assertTrue(oidcClientPage.isRedirectUri2TextBoxDisplayed(), GlobalConstants.isRedirectUri2TextBoxDisplayed);
	}

	@Test(priority = 4, description = "clear form oidc client")
	public void ClearFormOidcClient() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnListCreateOidcClientButton();
		oidcClientPage.enterNameOidcTextBox(GlobalConstants.DEFAULT_POLICY);
		oidcClientPage.enterPublicKeyTextBox(KeycloakUserManager.publicKeytemp);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnClearFormButton();
		assertTrue(oidcClientPage.isLogoUriempty(), GlobalConstants.isLogoUriempty);
	}

	@Test(priority = 5, description = "Using invalid data to create oidc")
	public void usingInvalidDataToCreateOIDC() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnListCreateOidcClientButton();
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		oidcClientPage.enterNameOidcTextBox(GlobalConstants.AUTOMATION_LOWERCASE);
		oidcClientPage.enterPublicKeyTextBox(GlobalConstants.INVALID_PUBLIC_KEY);
		assertTrue(oidcClientPage.isPublicKeyFormatErrorDisplayed(), GlobalConstants.isPublicKeyFormatErrorDisplayed);
		oidcClientPage.enterLogoUrTextBox(GlobalConstants.INVALID_DATA);
		assertTrue(oidcClientPage.isInvalidLogoUriErrorDisplayed(), GlobalConstants.isInvalidLogoUriErrorDisplayed);
		oidcClientPage.enterRedirectUriTextBox(GlobalConstants.INVALID_DATA);
		assertTrue(oidcClientPage.isInvalidRedirectUriErrorDisplayed(),
				GlobalConstants.isInvalidRedirectUriErrorDisplayed);
	}

	@Test(priority = 6, description = "edit OIDC client")
	public void editOIDCClient() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOidcDetailsElipsisButton();
		oidcClientPage.clickOnOidcEditButton();
		oidcClientPage.clickOnoidcEditAddNewRedirectUrl();
		oidcClientPage.enterSecondRedirectUriTextBox(ConfigManager.getRedirectUri() + "c");
		oidcClientPage.clickOnOidcEditSubmitButton();
		assertTrue(oidcClientPage.isModifiedSuccessfullTextMessageDisplayed(),
				GlobalConstants.isOidcModifiedSuccessfullyDisplayed);
		oidcClientPage.clickConfirmationGoBackButton();
		assertTrue(oidcClientPage.isPartnerIdDescIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(oidcClientPage.isPartnerIdAscIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(oidcClientPage.isOidcClientNameDescIconDisplayed(), GlobalConstants.isOidcClientNameDescIcon);
		assertTrue(oidcClientPage.isOidcClientNameAscIconDisplayed(), GlobalConstants.isOidcClientNameAscIcon);
		assertTrue(oidcClientPage.isPolicyGroupNameDescIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(oidcClientPage.isPolicyGroupNameAscIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(oidcClientPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(oidcClientPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(oidcClientPage.isCreatedDateTimeDescISconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(oidcClientPage.isCreatedDateTimeAscIconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(oidcClientPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);
		assertTrue(oidcClientPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);

	}

	@Test(priority = 7, description = "Deactivate OIDC client")
	public void deactivateOIDCClient() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOidcDetailsElipsisButton();
		oidcClientPage.clickOnOidcDeactivateButton();
		assertTrue(oidcClientPage.isdeactivateOidcPopupDisplayed(), GlobalConstants.isdeactivateOidcPopupDisplayed);
		assertTrue(oidcClientPage.isDeactivateOidcInfoMessageDisplayed(),
				GlobalConstants.isDeactivateOidcInfoMessageDisplayed);
		assertTrue(oidcClientPage.isDeactivateCancelButtonAvailable(),
				GlobalConstants.isDeactivateCancelButtonAvailable);
		assertTrue(oidcClientPage.isDeactivateSubmitButtonAvailable(),
				GlobalConstants.isDeactivateSubmitButtonAvailable);
		oidcClientPage.clickOnDeactivateCancelButton();
		oidcClientPage.clickOidcDetailsElipsisButton();
		oidcClientPage.clickOnOidcDeactivateButton();
		oidcClientPage.clickOnDeactivateSubmitButtonButton();
		assertTrue(oidcClientPage.isStatusDeavtivatedDisplayed(), GlobalConstants.isStatusDeavtivatedDisplayed);
		assertTrue(oidcClientPage.isDeactivatedEyeIconDisplayed(), GlobalConstants.isDeactivatedEyeIconDisplayed);
		oidcClientPage.clickOnDeactivatedEyeIcon();
		assertFalse(oidcClientPage.iscopyIdButtonDisplayed(), GlobalConstants.iscopyIdButtonDisplayed);
		oidcClientPage.clickOnDeactivatedOidcRow();
		assertFalse(oidcClientPage.isOidcClientDetailsPageDisplayed(),
				GlobalConstants.isOidcClientDetailsPageDisplayed);
		oidcClientPage.clickOnDeactivatedOidcActionButton();
		assertTrue(oidcClientPage.isOidcDetailsViewButtonDisplayed(), GlobalConstants.isOidcDetailsViewButtonDisplayed);
		oidcClientPage.clickOnOidcDetailsViewButton();
		assertTrue(oidcClientPage.isOidcDetailsPageStatusDeactivatedDisplayed(),
				GlobalConstants.isOidcDetailsPageStatusDeactivatedDisplayed);

	}

	@Test(priority = 8, description = "Oidc Client Deactivate")
	public void oidcClientDeactivate() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		oidcClientPage = dashboardPage.clickOnAuthenticationServices();

		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		oidcClientPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_OIDCPOLICY2);
		oidcClientPage.clickOnApplyFilterButton();

		oidcClientPage.clickOnActionButton();
		assertTrue(oidcClientPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		assertTrue(oidcClientPage.isDeactivateButtonEnabled(), GlobalConstants.isDeactivateButtonEnabled);

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
		assertTrue(oidcClientPage.isSubTitleOfTabularViewDisplayed(), GlobalConstants.isSubTitleOfTabularViewDisplayed);

		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_OIDCPOLICY2);
		oidcClientPage.clickOnApplyFilterButton();

		oidcClientPage.clickOnActionButton();
		oidcClientPage.clickOnDeactivateButton();
		oidcClientPage.clickOnDeactivateSubmitButtonButton();

		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		oidcClientPage.selectDeactivateStatusInFilter();
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_OIDCPOLICY2);
		oidcClientPage.clickOnApplyFilterButton();

		oidcClientPage.clickOnDeactivatedOidcRow();
		assertFalse(oidcClientPage.isOidcClientDetailsPageDisplayed(),
				GlobalConstants.isOidcClientDetailsPageDisplayed);
		assertTrue(oidcClientPage.isStatusDeavtivatedDisplayed(), GlobalConstants.isStatusDeavtivatedDisplayed);

		oidcClientPage.clickOnActionButton();
		oidcClientPage.clickOnViewButton();
		assertTrue(oidcClientPage.isOidcClientDetailsPageDisplayed(), GlobalConstants.isOidcClientDetailsPageDisplayed);
		assertTrue(oidcClientPage.isOidcDetailsPageStatusDeactivatedDisplayed(),
				GlobalConstants.isOidcDetailsPageStatusDeactivatedDisplayed);
		assertTrue(oidcClientPage.isDeactivatedOidcClientIdElementDisplayed(),
				GlobalConstants.isDeactivatedOidcClientIdElementDisplayed);

		loginAsAuthPartner();

		dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnListCreateOidcClientButton();
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
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

	@Test(priority = 9, description = "Oidc Client Tabular View")
	public void oidcClientTabularView() {

		dashboardPage = new DashboardPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		basePage = new BasePage(driver);

		assertTrue(dashboardPage.isAuthenticationServicesDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		oidcClientPage = dashboardPage.clickOnAuthenticationServices();
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
		assertTrue(oidcClientPage.isOidcClientNameDescIconDisplayed(), GlobalConstants.isOidcClientNameDescIconDisplayed);
		assertTrue(oidcClientPage.isOidcClientNameAscIconDisplayed(), GlobalConstants.isOidcClientNameAscIconDisplayed);
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

		basePage.scrollToEndPage();
		basePage.scrollToStartPage();
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
		assertTrue(oidcClientPage.isDeactivatedStatusInFilterDisplayed(),
				GlobalConstants.isDeactivatedStatusInFilterDisplayed);
		oidcClientPage.clickOnStatusFilter();
		oidcClientPage.clickOnFilterResetButton();
		assertFalse(oidcClientPage.isfilterResetButtonEnabled(), GlobalConstants.isfilterResetButtonEnabled);

		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		oidcClientPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULT_POLICYGROUP);
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
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		oidcClientPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.AUTOMATION_LOWERCASE);
		oidcClientPage.clickOnApplyFilterButton();

		oidcClientPage.clickOnActionButton();
		assertTrue(oidcClientPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		assertTrue(oidcClientPage.isDeactivateButtonEnabled(), GlobalConstants.isDeactivateButtonEnabled);

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

		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		oidcClientPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_OIDCPOLICY2);
		oidcClientPage.clickOnApplyFilterButton();

		oidcClientPage.clickOnActionButton();
		assertTrue(oidcClientPage.isViewButtonEnabled(), GlobalConstants.isViewButtonEnabled);
		oidcClientPage.clickOnDeactivateButton();
		assertFalse(oidcClientPage.isDeactivateOidcClientPopupDisplayed(),
				GlobalConstants.isDeactivateOidcClientPopupDisplayed);

		oidcClientPage.clickOnDeactivatedOidcRow();
		assertFalse(oidcClientPage.isOidcClientDetailsPageDisplayed(),
				GlobalConstants.isOidcClientDetailsPageDisplayed);
		assertTrue(oidcClientPage.isStatusDeavtivatedDisplayed(), GlobalConstants.isStatusDeavtivatedDisplayed);

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

	@Test(priority = 10, description = "Oidc Client View Details")
	public void oidcClientViewDetails() {

		dashboardPage = new DashboardPage(driver);

		oidcClientPage = dashboardPage.clickOnAuthenticationServices();
		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		oidcClientPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULT_POLICYGROUP);
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
		assertTrue(oidcClientPage.isCopiedTextDisplayed(), GlobalConstants.isCopiedTextDisplayed);
		oidcClientPage.clickOnOidcClientDetailsCopyId();
		assertTrue(oidcClientPage.isCopiedTextDisplayed(), GlobalConstants.isCopiedTextDisplayed);
		oidcClientPage.clickOnOidcClientDetailsCopyId();
		assertTrue(oidcClientPage.isCopiedTextDisplayed(), GlobalConstants.isCopiedTextDisplayed);

		assertTrue(oidcClientPage.isOidcClientDetailsBackButtonDisplayed(),
				GlobalConstants.isOidcClientDetailsBackButtonDisplayed);
		oidcClientPage.clickOidcClientDetailsBackButton();

		oidcClientPage.clickOnFilterButton();
		oidcClientPage.enterPartnerIdInFilter(GlobalConstants.AUTH_PARTNER_ID);
		oidcClientPage.enterPolicyGroupInFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		oidcClientPage.enterValidOidcClientNameInFilter(GlobalConstants.DEACTIVATE_OIDCPOLICY2);
		oidcClientPage.clickOnApplyFilterButton();
		oidcClientPage.clickOnActionButton();

		oidcClientPage.clickOnViewButton();
		assertTrue(oidcClientPage.isOidcClientDetailsPageDisplayed(), GlobalConstants.isOidcClientDetailsPageDisplayed);
		assertTrue(oidcClientPage.isOidcDetailsPageStatusDeactivatedDisplayed(),
				GlobalConstants.isOidcDetailsPageStatusDeactivatedDisplayed);
		assertTrue(oidcClientPage.isDeactivatedOidcClientIdElementDisplayed(),
				GlobalConstants.isDeactivatedOidcClientIdElementDisplayed);

		assertTrue(oidcClientPage.isOidcClientDetailsCopyIdDisplayed(),
				GlobalConstants.isOidcClientDetailsCopyIdDisplayed);
		oidcClientPage.clickOnOidcClientDetailsCopyId();
		assertFalse(oidcClientPage.isCopiedTextDisplayed(), GlobalConstants.isCopiedTextDisplayed);
		oidcClientPage.clickOnTitleBackButton();
		assertTrue(oidcClientPage.isOidcClientTabDisplayed(), GlobalConstants.isOidcClientTabDisplayed);

	}

	private void loginAsAuthPartner() {
		dashboardPage = new DashboardPage(driver);
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.AUTH_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();
	}

	private void createOidcClient(String oidcTextBoxValue) {

		oidcClientPage = new OidcClientPage(driver);
		oidcClientPage.clickOnListCreateOidcClientButton();
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(GlobalConstants.DEFAULT_POLICY);
		oidcClientPage.enterNameOidcTextBox(oidcTextBoxValue);
		String publicKeytemp = PmpTestUtil.generateJWKPublicKey();
		oidcClientPage.enterPublicKeyTextBox(publicKeytemp);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnSubmitButton();
		oidcClientPage.clickConfirmationGoBackButton();
	}

}
