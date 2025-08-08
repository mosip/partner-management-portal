package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;
import io.mosip.testrig.pmpuiv2.kernel.util.KeycloakUserManager;
import io.mosip.testrig.pmpuiv2.pages.ApiKeyPage;
import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.OidcClientPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.pages.ProfilePage;
import io.mosip.testrig.pmpuiv2.pages.RegisterPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class AuthPartnerTest extends BaseClass {

	private BasePage basePage;
	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private ApiKeyPage apiKeyPage;
	private PartnerCertificatePage partnerCertificatePage;
	private RegisterPage registerPage;
	private PoliciesPage policiesPage;
	private AuthPolicyPage authPolicyPage;
	private PartnerPolicyMappingPage partnerPolicyMappingPage;
	private OidcClientPage oidcClientPage;
	private ProfilePage profilePage;

	@Test(priority = 3, description = "This is a test case register new user")
	public void RegisterNewUser() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		loginPage = new LoginPage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		partnerCertificatePage.clickOnIntermediateCACertTab();
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		logoutFromPartner();

		registerPage = loginPage.clickRegisterButton();
		loginPage.clickRegisterButton();

		registerPage.enterFirstName("pmpui-auth");
		registerPage.enterLastName("pmpui-auth");
		registerPage.enterOrganizationName("AABBCC");
		registerPage.selectPartnerTypeDropdown(2);
		registerPage.enterAddress("0" + data);
		registerPage.enterEmail("0" + data + "@gmail.com");
		registerPage.enterPhone("9876543210");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername("pmpui-auth");
		registerPage.enterPassword("mosip123");
		registerPage.enterPasswordConfirm("mosip123");
		dashboardPage = registerPage.clickSubmitButton();

		dashboardPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		dashboardPage.clickOnSubmitButton();

		assertTrue(dashboardPage.isTermsAndConditionsPopupDisplayed(),
				GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardPage.clickOnCheckbox();
		dashboardPage.clickOnProceedButton();

		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();

		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.clickOnUploadButton();

		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(),
				GlobalConstants.isPleaseTabToSelectTextDisplayed);
		assertTrue(partnerCertificatePage.isCertFormatesTextDisplayed(), GlobalConstants.isCertFormatesTextDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
		dashboardPage = partnerCertificatePage.clickOnHomeButton();

		dashboardPage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();

		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateSubTextDisplayed(),
				GlobalConstants.isReUploadPartnerCertificateSubTextDisplayed);

		assertTrue(partnerCertificatePage.isPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLabelDisplayed);
		assertTrue(partnerCertificatePage.isPartnerTypeValueDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(partnerCertificatePage.isPartnerTypeValueDisabled(), GlobalConstants.isPartnerTypeValueDisabled);

		assertTrue(partnerCertificatePage.isPartnerDomainTypeLabelDisplayed(),
				GlobalConstants.isPartnerDomainTypeDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainTypeValueDisplayed(),
				GlobalConstants.isPartnerDomainTypeValueDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainTypeValueDisabled(),
				GlobalConstants.isPartnerDomainTypeValueDisabled);

		assertTrue(partnerCertificatePage.isUploadCertificateIconDisplayed(),
				GlobalConstants.isUploadCertificateIconDisplayed);
		assertTrue(partnerCertificatePage.isCertFormatesTextDisplayed(), GlobalConstants.isCertFormatesTextDisplayed);

		assertTrue(partnerCertificatePage.isLastCertificateUploadDateDisplayed(),
				GlobalConstants.isLastCertificateUploadDateDisplayed);

		partnerCertificatePage.uploadCertificate();

		assertTrue(partnerCertificatePage.isUploadedCertificateNameDisplayed(),
				GlobalConstants.isUploadedCertificateNameDisplayed);
		assertTrue(partnerCertificatePage.isCertificateRemoveButtonDisplayed(),
				GlobalConstants.isCertificateRemoveButtonDisplayed);
		partnerCertificatePage.clickOnSubmitButton();

		assertTrue(partnerCertificatePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();
		partnerCertificatePage.uploadCertificateInvalidCert();
		assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(),
				GlobalConstants.isInvalidCertFormatePopupDisplayed);

		partnerCertificatePage.clickOnCertificateUploadCancelButton();

		partnerCertificatePage.clickOnDownloadButton();
		partnerCertificatePage.clickOnOriginalCertificateDownloadButton();
		assertTrue(partnerCertificatePage.isOriginalSignedCertDownloadedPopupDisplayed(),
				GlobalConstants.isOriginalCertificateDownloadPopupDisplayed);

		partnerCertificatePage.clickOnMosipSignedCertificateDownloadButton();
		assertTrue(partnerCertificatePage.isMosipSignedCertPopupDisplayed(),
				GlobalConstants.isMosipCertificateDownloadPopupDisplayed);

//	    assertTrue(partnerCertificatePage.isSucessMessageDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.clickOnSuccessMsgCloseButton();
		partnerCertificatePage.clickOnTitleBackButton();
	}

	@Test(priority = 4, description = "Policy creation and filter")
	public void verifyingPolicyCreationAndFilter() {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		basePage = new BasePage(driver);
		loginPage = new LoginPage(driver);

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();

		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(data);
		authPolicyPage.enterpolicyDescription(data);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();
		basePage.scrollToStartPage();

		createAuthPolicy(GlobalConstants.AUTHPOLICY01, GlobalConstants.AUTHPOLICY01_DESCRIPTION);

		basePage.scrollToStartPage();

		createAuthPolicy(GlobalConstants.AUTHPOLICY02, GlobalConstants.AUTHPOLICY02_DESCRIPTION);

		basePage.scrollToStartPage();

		createAuthPolicy(GlobalConstants.PENDING_POLICY, GlobalConstants.PENDING_POLICY_DESCRIPTION);

		basePage.scrollToStartPage();

		createAuthPolicy(GlobalConstants.DEACTIVATE_AUTHPOLICY, GlobalConstants.DEACTIVATE_POLICY_DESCRIPTION);

		basePage.scrollToStartPage();
		authPolicyPage.clickOnFilterButton();

		filterAndPublishAuthPolicy(data);

		filterAndPublishAuthPolicy(GlobalConstants.AUTHPOLICY01);

		filterAndPublishAuthPolicy(GlobalConstants.AUTHPOLICY02);

		filterAndPublishAuthPolicy(GlobalConstants.PENDING_POLICY);

		filterAndDeactivateAuthPolicy(GlobalConstants.DEACTIVATE_AUTHPOLICY);

		loginAsAuthPartner();

		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		dashboardPage.clickOnPoliciesTitle();

		assertTrue(policiesPage.isPoliciesEmptyTableDisplayed(), GlobalConstants.isPoliciesEmptyTableDisplayed);
		assertTrue(policiesPage.isPoliciesEmptyTableEnabled(), GlobalConstants.isRequestPolicyEnabled);
		policiesPage.clickOnRequestPolicyButton();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.selectPartnerIdDropdown();
		assertTrue(policiesPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		policiesPage.selectPolicyNameDropdown(data);
		policiesPage.enterComments(data);
		assertTrue(policiesPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		policiesPage.clickSubmitButton();
		assertTrue(policiesPage.isPolicySubmittedSuccessfullyDisplayed(), GlobalConstants.isSubmitButtonDisplayed);

		policiesPage.clickOnHomeButton();
		dashboardPage.clickOnPoliciesTitle();
		assertTrue(policiesPage.isListOfPolicyRequestedDisplayed(),
				GlobalConstants.isListOfPolicyRequestedTextDisplayed);
		assertTrue(policiesPage.isNextPageDisplayed(), GlobalConstants.isNextPageDisplayed);
		assertTrue(policiesPage.isPreviousPageDisplayed(), GlobalConstants.isPreviousPageDisplayed);
		assertTrue(policiesPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalDisplayed);

		policiesPage.clickOnElipcisButton();
		policiesPage.clickOnCardViewButton();
		assertTrue(policiesPage.isPolicyDetailsPartnerIdLabelDisplayed(),
				GlobalConstants.isPolicyDetailsPartnerIdLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyGroupNameLabelDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyGroupNameLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyGroupNameContextDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyGroupNameContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPartnerTypeLabelDisplayed(),
				GlobalConstants.isPolicyDetailsPartnerTypeLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPartnerTypeContextDisplayed(),
				GlobalConstants.isPolicyDetailsPartnerTypeContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyNameLabelDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyNameLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyNameContextDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyNameContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyGroupDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyGroupDescriptionLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyGroupDescriptionContextDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyGroupDescriptionContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyNameDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyNameDescriptionLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyNameDescriptionContextDisplayed(),
				GlobalConstants.isPolicyDetailsPolicyNameDescriptionContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsCommentsDisplayed(), GlobalConstants.isPolicyDetailsCommentsDisplayed);
		assertTrue(policiesPage.isPolicyViewPageBackButtonEnabled(), GlobalConstants.isPolicyViewPageBackButtonEnabled);
		policiesPage.clickOnBackButton();

		assertTrue(policiesPage.isPartnerIdDescIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(policiesPage.isPartnerIdAscIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(policiesPage.isPartnerTypeDescIconDisplayed(), GlobalConstants.isPartnerTypeDescIcon);
		assertTrue(policiesPage.isPartnerTypeAscIconDisplayed(), GlobalConstants.isPartnerTypeAscIcon);
		assertTrue(policiesPage.isPolicyGroupNameDescIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(policiesPage.isPolicyGroupNameAscIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(policiesPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(policiesPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(policiesPage.isCreatedDateTimeDescISconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(policiesPage.isCreatedDateTimeAscIconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);

		policiesPage.clickOnFilterButton();
		policiesPage.clickOnPolicyPartnerIdFilter();
		policiesPage.clickOnPolicyPartnerTypeFilter();
		policiesPage.clickOnPolicyGroupFilter();
		policiesPage.clickOnPolicyNameFilter();
		policiesPage.clickOnPolicyStatusFilter();
		policiesPage.clickOnPolicyNameDescendingBtn();
		policiesPage.clickOnPolicyNameAscendingBtn();
		policiesPage.clickOnFilterResetButton();
		policiesPage.isFilterButtonButtonEnabled();

		policiesPage.clickOnPolicyListItem1();
		assertTrue(policiesPage.isSubTitleHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(policiesPage.isTitleBackIconDisplayed(), GlobalConstants.isBackArrow);
		policiesPage.clickOnTitleBackIcon();
		assertTrue(policiesPage.isTitleOfPolicyPageDisplayed(), GlobalConstants.isTitleOfPolicyPageDisplayed);
		assertTrue(policiesPage.isRequestPolicyButtonDisplayed(), GlobalConstants.isRequestPolicyButtonDisplayed);

		requestPolicy(GlobalConstants.PENDING_POLICY);

		requestPolicy(GlobalConstants.AUTHPOLICY02);

		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterInvalidPolicyNameDropdown(GlobalConstants.DEACTIVATE_AUTHPOLICY);
		assertTrue(policiesPage.isNoDataAvailableDisplayed(), GlobalConstants.isNoDataAvailableDisplayed);
		policiesPage.clickOnRequestPoliciesFormClearButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterInvalidPolicyNameDropdown(GlobalConstants.AUTHPOLICY01);
		assertTrue(policiesPage.isPolicyNameDisplayed(), GlobalConstants.isPolicyNameDisplayed);
		assertTrue(policiesPage.isPolicyDescriptionDisplayed(), GlobalConstants.isPolicyDescriptionDisplayed);
		policiesPage.enterComments(data);
		policiesPage.enterComments(GlobalConstants.SPACE);
		policiesPage.enterComments(GlobalConstants.AUTHPOLICY01_DESCRIPTION);
		policiesPage.clickOnRequestPoliciesFormClearButton();

	}

	@Test(priority = 5, description = "Partner-Policy maaping & creation OIDC client")
	public void createOidecClient() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnFilterButton();

		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(data);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertTrue(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		assertTrue(partnerPolicyMappingPage.isConfirmationPopupDetailedMessageDisplayed(),
				GlobalConstants.isConfirmationPopupDetailedMessageDisplayed);
		assertTrue(partnerPolicyMappingPage.isApproveRejectButtonDisplayed(),
				GlobalConstants.isApproveRejectButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isApproveSubmitButtonDisplayed(),
				GlobalConstants.isApproveSubmitButtonDisplayed);
		partnerPolicyMappingPage.clickOnApproveSubmitButton();

		partnerPolicyMappingPage.clickOnFilterButton();

		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY02);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		partnerPolicyMappingPage.clickOnRejectButton();

		loginAsAuthPartner();

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
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
		oidcClientPage.selectPolicyNameDropdown(data);
		oidcClientPage.enterNameOidcTextBox(data);
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
				GlobalConstants.isAutherisationCodeTextDisplayed);
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
		oidcClientPage.selectPolicyNameDropdown(data);
		oidcClientPage.enterNameOidcTextBox(GlobalConstants.SPACE);
		oidcClientPage.enterPublicKeyTextBox(GlobalConstants.SPACE);
		oidcClientPage.enterLogoUrTextBox(GlobalConstants.SPACE);
		oidcClientPage.enterRedirectUriTextBox(GlobalConstants.SPACE);

		assertTrue(oidcClientPage.isEnterValidUriForLogoUriTextDisplayed(),
				GlobalConstants.isEnterValidLogoUriTextDisplayed);
		assertTrue(oidcClientPage.isEnterValidUriForRedirectUriTextDisplayed(),
				GlobalConstants.isEnterRedirectUriTextDisplayed);
		oidcClientPage.clickOnCreateOidcClearForm();

		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(data);
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

	@Test(priority = 6, description = "APIkey creation")
	public void CreateApiKey() {

		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();
		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();

		assertTrue(oidcClientPage.isApiKeyTabDisplayed(), GlobalConstants.isApiKeyTabDisplayed);
		oidcClientPage.clickOnApiKeyTab();
		assertTrue(apiKeyPage.isGenerateAPIKeyDisplayed(), GlobalConstants.isGenerateAPIKeyDisplayed);
		apiKeyPage.clickOnAPIKeyDisplayed();
		assertTrue(apiKeyPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		apiKeyPage.selectPartnerIdDropdown();

		assertTrue(apiKeyPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox("0" + data);

		apiKeyPage.clickOnSubmitButton();
//		assertTrue(oidcClientPage.isAuthorizationCodeTextDisplayed(), GlobalConstants.isAutherisationCodeTextDisplayed);

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
		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		assertTrue(apiKeyPage.isPartnerIdHelpTextDisplayed(), GlobalConstants.isPartnerIdHelpTextDisplayed);
		assertTrue(apiKeyPage.isPolicyNameHelpTextDisplayed(), GlobalConstants.isPolicyNameHelpTextDisplayed);
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox("0" + data);
		assertTrue(apiKeyPage.isClearButtonDisplayed(), GlobalConstants.isClearButtonDisplayed);
		apiKeyPage.clickOnClearButton();
		assertTrue(apiKeyPage.isCancelButtonDisplayed(), GlobalConstants.isCancelButtonDisplayed);
		apiKeyPage.clickOnCancelButton();

		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.enterPendingPolicyNameDropdown(GlobalConstants.PENDING_POLICY);
		assertTrue(apiKeyPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isnoDataAvailableTextDisplayed);
		apiKeyPage.clickOnClearButton();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
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
		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
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

		assertTrue(apiKeyPage.isPartnerIdDescIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(apiKeyPage.isPartnerIdAscIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(apiKeyPage.isApiKeyNameDescIconDisplayed(), GlobalConstants.isApiKeyNameDescIcon);
		assertTrue(apiKeyPage.isApiKeyNameAscIconDisplayed(), GlobalConstants.isApiKeyNameAscIcon);
		assertTrue(apiKeyPage.isPolicyGroupNameDescIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(apiKeyPage.isPolicyGroupNameAscIconDisplayed(), GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(apiKeyPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(apiKeyPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(apiKeyPage.isCreatedDateTimeDescISconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(apiKeyPage.isCreatedDateTimeAscIconDisplayed(), GlobalConstants.isCreatedDateTimeDescAscIcon);
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

	@Test(priority = 7, description = "Search with invalid policy name")

	public void searchWithInvalidPolicyName() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		PoliciesPage policiesPage = dashboardPage.clickOnPoliciesTitle();

		policiesPage.clickOnRequestPolicyButtonOfTabularPage();

		policiesPage.selectPartnerIdDropdown();

		assertTrue(policiesPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		policiesPage.selectInvalidPolicyNameDropdown(data + "123");
		policiesPage.searchInPolicyName(data + "123");

		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		policiesPage.clickOnRequestPoliciesFormClearButton();

		assertEquals(policiesPage.getThePolicyCommentBoxText(), GlobalConstants.isPolicyCommentBoxTextDisplayed);
		assertEquals(policiesPage.getThepolicyNameDropdownBoxText(), GlobalConstants.isPolicyNameBoxTextDisplayed);

		policiesPage.clickOnRequestPoliciesFormCancelButton();
		assertTrue(policiesPage.isListOfPolicyRequestedDisplayed(),
				GlobalConstants.isListOfPolicyRequestedTextDisplayed);
	}

	@Test(priority = 8, description = "Resubmit already submitted request policy")
	public void reSubmitAlreadySubmittedRequestPolicy() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		policiesPage = dashboardPage.clickOnPoliciesTitle();

		policiesPage.clickOnRequestPolicyButtonOfTabularPage();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(data);
		policiesPage.enterComments(data);
		policiesPage.clickSubmitButton();
		assertTrue(policiesPage.isPolicyAlreadyApprovedMessageDisplayed(),
				GlobalConstants.isPolicyAlreadyApprovedMessageDisplayed);
		policiesPage.clickOnErrorCloseButton();
		policiesPage.enterAuthPolicyNameDropdown(GlobalConstants.PENDING_POLICY);
		policiesPage.clickSubmitButton();
		assertTrue(policiesPage.isPolicyPendingForApprovalMessageDisplayed(),
				GlobalConstants.isPolicyPendingForApprovalMessageDisplayed);
		policiesPage.clickOnErrorCloseButton();

		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterAuthPolicyNameDropdown(GlobalConstants.AUTHPOLICY02);
		policiesPage.enterComments(data);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

	}

	@Test(priority = 9, description = "Request new policy with out uploading certificates")
	public void requestNewPolicyWithoutUploadingCertificates() throws InterruptedException {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		basePage = new BasePage(driver);

		logoutFromPartner();

		loginPage.clickRegisterButton();

		registerPage.enterFirstName("pmpui-nocert");
		registerPage.enterLastName("  ");
		registerPage.enterOrganizationName("AABBCC");
		registerPage.selectPartnerTypeDropdown(2);
		registerPage.enterAddress("0" + data);
		registerPage.enterEmail(data + "nocert" + "@gmail.com");
		registerPage.enterPhone("  ");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername("pmpui-nocert");
		registerPage.enterPassword("mosip123");
		registerPage.enterPasswordConfirm("mosip123");
		dashboardPage = registerPage.clickSubmitButton();

		assertTrue(registerPage.isPhoneNumberWarningMessageDisplayed(),
				GlobalConstants.isPhoneNumberWarningMessageDisplayed);
		registerPage.selectPartnerTypeDropdown(2);
		registerPage.enterPhone("8098768903");
		registerPage.enterPassword("mosip123");
		registerPage.enterPasswordConfirm("mosip123");
		dashboardPage = registerPage.clickSubmitButton();

		dashboardPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		dashboardPage.clickOnSubmitButton();
		assertTrue(dashboardPage.isTermsAndConditionsPopupDisplayed(),
				GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardPage.clickOnCheckbox();
		dashboardPage.clickOnProceedButton();

		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnRequestPolicyButton();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.clickOnPartnerIdDropdown();
		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataTextDisplaed);

	}

	@Test(priority = 10, description = "Create oidc client with out uploading certficates")
	public void createOidcClientWithoutUploadingCertificates() {

		dashboardPage = new DashboardPage(driver);
		loginPage = new LoginPage(driver);
		basePage = new BasePage(driver);

		logoutFromPartner();

		loginPage.enterUserName("pmpui-nocert");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();

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
		oidcClientPage.clickOnPartnerIdDropdown();
		assertTrue(oidcClientPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);

		String publicKeytemp = PmpTestUtil.generateJWKPublicKey();
		oidcClientPage.enterPublicKeyTextBox(publicKeytemp);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnAddNewRedirectUrlButton();

		oidcClientPage.entercreateOidcRedirectUrl2(ConfigManager.getRedirectUri() + "a");
		oidcClientPage.clickOnAddNewRedirectUrlButton();
		oidcClientPage.entercreateOidcRedirectUrl3(ConfigManager.getRedirectUri() + "b");
		oidcClientPage.clickOnAddNewRedirectUrlButton();
		oidcClientPage.entercreateOidcRedirectUrl4(ConfigManager.getRedirectUri() + "c");
		oidcClientPage.clickOnAddNewRedirectUrlButton();
		oidcClientPage.entercreateOidcRedirectUrl5(ConfigManager.getRedirectUri() + "d");
		oidcClientPage.clickOnCreateOidcClearForm();
		assertFalse(oidcClientPage.isCreateOidcRedirectUrl5Displayed(), GlobalConstants.isNoDataAvailableTextDisplayed);

	}

	@Test(priority = 11, description = " Create apikey without uploading certificates")
	public void createApiKeyWithoutUploadingCertificates() {
		dashboardPage = new DashboardPage(driver);
		apiKeyPage = new ApiKeyPage(driver);
		loginPage = new LoginPage(driver);
		basePage = new BasePage(driver);

		logoutFromPartner();

		loginPage.enterUserName("pmpui-nocert");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();

		oidcClientPage.clickOnApiKeyTab();

		assertTrue(apiKeyPage.isGenerateAPIKeyDisplayed(), GlobalConstants.isGenerateAPIKeyDisplayed);
		apiKeyPage.clickOnAPIKeyDisplayed();
		assertTrue(apiKeyPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		apiKeyPage.ClickOnPartnerIdDropdown();
		assertTrue(apiKeyPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataTextDisplaed);

	}

	@Test(priority = 12, description = "Deleting second redirct uri")
	public void deletingSecondRedirectUri() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnListCreateOidcClientButton();
		oidcClientPage.clickOnRedirectUriAddNew();
		assertTrue(oidcClientPage.isRedirectUri2TextBoxDisplayed(), GlobalConstants.isRedirectUri2TextBoxDisplayed);
		oidcClientPage.clickOnRedirectUri2Delete();
		assertFalse(oidcClientPage.isRedirectUri2TextBoxDisplayed(), GlobalConstants.isRedirectUri2TextBoxDisplayed);
	}

	@Test(priority = 13, description = "Adding second redirect uri")
	public void addingSecondRedirectUri() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnListCreateOidcClientButton();
		oidcClientPage.clickOnRedirectUriAddNew();
		assertTrue(oidcClientPage.isRedirectUri2TextBoxDisplayed(), GlobalConstants.isRedirectUri2TextBoxDisplayed);
	}

	@Test(priority = 14, description = "clear form oidc client")
	public void ClearFormOidcClient() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnListCreateOidcClientButton();
		oidcClientPage.enterNameOidcTextBox(data);
		oidcClientPage.enterPublicKeyTextBox(KeycloakUserManager.publicKeytemp);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnClearFormButton();
		assertTrue(oidcClientPage.isLogoUriempty(), GlobalConstants.isLogoUriempty);
	}

	@Test(priority = 15, description = "Using invalid data to create oidc")
	public void usingInvalidDataToCreateOIDC() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnListCreateOidcClientButton();
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(data);
		oidcClientPage.enterNameOidcTextBox(GlobalConstants.AUTOMATION_LOWERCASE);
		oidcClientPage.enterPublicKeyTextBox(GlobalConstants.INVALID_PUBLIC_KEY);
		assertTrue(oidcClientPage.isPublicKeyFormatErrorDisplayed(), GlobalConstants.isPublicKeyFormatErrorDisplayed);
		oidcClientPage.enterLogoUrTextBox(GlobalConstants.INVALID_DATA);
		assertTrue(oidcClientPage.isInvalidLogoUriErrorDisplayed(), GlobalConstants.isInvalidLogoUriErrorDisplayed);
		oidcClientPage.enterRedirectUriTextBox(GlobalConstants.INVALID_DATA);
		assertTrue(oidcClientPage.isInvalidRedirectUriErrorDisplayed(),
				GlobalConstants.isInvalidRedirectUriErrorDisplayed);
	}

	@Test(priority = 16, description = "edit OIDC client")
	public void editOIDCClient() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOidcDetailsElipsisButton();
		oidcClientPage.clickOnOidcEditButton();
		oidcClientPage.clickOnoidcEditAddNewRedirectUrl();
		oidcClientPage.EnterPublickeySecondTextBox(ConfigManager.getRedirectUri() + "c");
		oidcClientPage.clickOnOidcEditSubmitButton();
		assertTrue(oidcClientPage.isModifiedSuccessfullTextMessageDisplayed(),
				GlobalConstants.isAutherisationCodeTextDisplayed);
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

	@Test(priority = 17, description = "Deactivate OIDC client")
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

	@Test(priority = 18, description = "User Profile")
	public void userProfile() {
		dashboardPage = new DashboardPage(driver);
		profilePage = new ProfilePage(driver);
		loginPage = new LoginPage(driver);
		basePage = new BasePage(driver);

		logoutFromPartner();

		loginPage.enterUserName("pmpui-nocert");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		dashboardPage.clickOnProfileDropdown();
		profilePage.clickOnUserProfileButton();
		assertTrue(profilePage.isFirstNameLabelDisplayed(), GlobalConstants.isFirstNameLabelDisplayed);
		assertTrue(profilePage.isFirstNameContextDisplayed(), GlobalConstants.isFirstNameContextDisplayed);
		assertTrue(profilePage.isLastNameLabelDisplayed(), GlobalConstants.isLastNameLabelDisplayed);
		assertTrue(profilePage.isLastNameContextDisplayed(), GlobalConstants.isLastNameContextDisplayed);
		assertTrue(profilePage.isOrganisationNameLabelDisplayed(), GlobalConstants.isOrganisationNameLabelDisplayed);
		assertTrue(profilePage.isOrganisationNameContextDisplayed(),
				GlobalConstants.isOrganisationNameContextDisplayed);
		assertTrue(profilePage.isAddressLabelDisplayed(), GlobalConstants.isAddressLabelDisplayed); //
//		assertTrue(profilePage.isAddressContextDisplayed(), GlobalConstants.isAddressContextDisplayed);
		assertTrue(profilePage.isPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLabelDisplayed);
		assertTrue(profilePage.isPartnerTypeContextDisplayed(), GlobalConstants.isPartnerTypeContextDisplayed);
		assertTrue(profilePage.isPhoneNumberLabelDisplayed(), GlobalConstants.isPhoneNumberLabelDisplayed);
		assertTrue(profilePage.isPhoneNumberContextDisplayed(), GlobalConstants.isPhoneNumberContextDisplayed);
		assertTrue(profilePage.isEmailAddressLabelDisplayed(), GlobalConstants.isEmailAddressLabelDisplayed); //
		assertTrue(profilePage.isEmailContextDisplayed(), GlobalConstants.isEmailContextDisplayed);
		assertTrue(profilePage.isUserNameLabelDisplayed(), GlobalConstants.isUserNameLabelDisplayed);
		assertTrue(profilePage.isUserNameContextDisplayed(), GlobalConstants.isUserNameContextDisplayed);
		profilePage.clickOnPhoneNumber();
		assertTrue(profilePage.isPhoneNumberClickable(), GlobalConstants.isPhoneNumberClickable);
		profilePage.clickOnTitleBackIcon();

	}

	@Test(priority = 19, description = "User dashboard of authentication partner")
	public void userDashboardOfAuthenticationPartner() {

		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		policiesPage = new PoliciesPage(driver);
		oidcClientPage = new OidcClientPage(driver);
		basePage = new BasePage(driver);

		loginAsAuthPartner();

		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesTitleDisplayed);
		assertTrue(dashboardPage.isAuthenticationServiceInfoTextDisplayed(),
				GlobalConstants.isAuthenticationServiceInfoTextDisplayed);
		assertTrue(dashboardPage.isAuthenticationServiceIconDisplayed(),
				GlobalConstants.isAuthenticationServiceIconDisplayed);
		dashboardPage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnTitleBackButton();
		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnTitleBackIcon();
		dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnTitleBackButton();
		dashboardPage.clickOnHamburgerOpen();
		assertTrue(dashboardPage.isHumburgerOptionsExpandable(), GlobalConstants.isHumburgerOptionsExpandable);
		dashboardPage.clickOnHomeOptionOfHamburger();
		dashboardPage.clickOnPartnerCertificateOfHamburger();
		dashboardPage.clickOnPoliciesOfHamburger();
		dashboardPage.clickOnAuthenticationServiceOfHamburger();
		dashboardPage.clickOnHamburgerClose();
		oidcClientPage.clickOnTitleBackButton();
		assertTrue(dashboardPage.isOrganizationIconWithNameDisplayed(),
				GlobalConstants.isOrganizationIconWithNameDisplayed);
		assertTrue(dashboardPage.isContactusLinkDisplayed(), GlobalConstants.isContactusLinkDisplayed);
		assertTrue(dashboardPage.isMosipRightsTextDisplayed(), GlobalConstants.isMosipRightsTextDisplayed);
		assertTrue(dashboardPage.isFooterDocumentationLinkDisplayed(),
				GlobalConstants.isFooterDocumentationLinkDisplayed);
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

	}

	private void logoutFromPartner() {
		dashboardPage.clickOnProfileDropdown();
		dashboardPage.clickOnLogoutButton();
	}

	private void loginAsAuthPartner() {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.AUTH_PARTNER_ID);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();
	}

	private void createAuthPolicy(String policyNameValue, String policyDescValue) {
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(policyNameValue);
		authPolicyPage.enterpolicyDescription(policyDescValue);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();
	}

	private void filterAndPublishAuthPolicy(String policyNameFilterValue) {
		authPolicyPage.enterPolicyNameInFilter(policyNameFilterValue);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();

	}

	private void filterAndDeactivateAuthPolicy(String policyNameFilterValue) {
		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(policyNameFilterValue);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnDeactivateButton();
		authPolicyPage.clickOnDeactivateConfirmButton();

	}

	private void requestPolicy(String authPolicyName) {
		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterAuthPolicyNameDropdown(authPolicyName);
		policiesPage.enterComments(data);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

	}

	private void createOidcClient(String oidcTextBoxValue) {

		oidcClientPage = new OidcClientPage(driver);
		oidcClientPage.clickOnListCreateOidcClientButton();
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(data);
		oidcClientPage.enterNameOidcTextBox(oidcTextBoxValue);
		String publicKeytemp = PmpTestUtil.generateJWKPublicKey();
		oidcClientPage.enterPublicKeyTextBox(publicKeytemp);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnSubmitButton();
		oidcClientPage.clickConfirmationGoBackButton();
	}

	private void createApiKey(String apiKeyTextBoxValue) {
		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox(apiKeyTextBoxValue);
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();
	}

}
