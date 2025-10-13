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
import io.mosip.testrig.pmpuiv2.pages.DatasharePolicyPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.OidcClientPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.pages.PolicyGroupPage;
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
	private PolicyGroupPage policygroupPage;
	private DatasharePolicyPage datasharePolicyPage;

	@Test(priority = 3, description = "This is a test case register new user")
	public void RegisterNewUser() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		loginPage = new LoginPage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(partnerCertificatePage.isUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		partnerCertificatePage.clickOnIntermediateCACertTab();
		assertTrue(partnerCertificatePage.isIntermediateUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
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

		basePage.refreshThePage();
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
/*
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

	@Test(priority = 20, description = "Create Auth DataShare Policy")
	public void creatAuthPolicyDataSharePolicy() {

		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		datasharePolicyPage = new DatasharePolicyPage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY_PARTLINK);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY_PARTLINK);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY_PARTLINK);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		authPolicyPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(GlobalConstants.AUTHPOLICY_PARTLINK2);
		authPolicyPage.enterpolicyDescription(GlobalConstants.AUTHPOLICY_PARTLINK2);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.AUTHPOLICY_PARTLINK2);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		authPolicyPage.clickOnHomeButton();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnDatasharePolicyTab();
		datasharePolicyPage.clickOnDatasharePolicyCreateButton();
		datasharePolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		datasharePolicyPage.enterPolicyName(GlobalConstants.DATAPOLICY_PARTLINK);
		datasharePolicyPage.enterpolicyDescription(GlobalConstants.DATAPOLICY_PARTLINK);
		datasharePolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		datasharePolicyPage.clickOnSaveAsDraftButton();
		datasharePolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyGroupInFilterBox(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyNameInFilter(GlobalConstants.DATAPOLICY_PARTLINK);
		authPolicyPage.clickOnApplyFilterButton();
		datasharePolicyPage.clickOnActionButton();
		datasharePolicyPage.clickOnPublishButton();
		datasharePolicyPage.clickOnPublishPolicyButton();
		datasharePolicyPage.clickOnPublishPolicyCloseButton();
	}

	@Test(priority = 21, description = "Request Auth DataShare Policy")
	public void RequestAuthDataSharePolicy() {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		loginPage = new LoginPage(driver);

		loginAsAuthPartner();

		dashboardPage.clickOnPoliciesTitle();

		requestPolicy(GlobalConstants.AUTHPOLICY_PARTLINK);

		requestPolicy(GlobalConstants.AUTHPOLICY_PARTLINK2);

		requestPolicy(GlobalConstants.DATAPOLICY_PARTLINK);

	}

	@Test(priority = 22, description = "Tabular View Of Partner Policy")
	public void tabularViewOfPartnerPolicy() {

		dashboardPage = new DashboardPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingTitleDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingSubTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingSubTitleDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerIdHeaderDisplayed(), GlobalConstants.isPartnerIdHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeHeaderDisplayed(),
				GlobalConstants.isPartnerTypeHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isOrganisationNameHeaderDisplayed(),
				GlobalConstants.isOrganisationNameHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyIdHeaderDisplayed(), GlobalConstants.isPolicyIdHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupHeaderDisplayed(),
				GlobalConstants.isPolicyGroupHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameHeaderDisplayed(), GlobalConstants.isPolicyNameHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isCreationDateHeaderDisplayed(),
				GlobalConstants.isCreationDateHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusHeaderDisplayed(), GlobalConstants.isStatusHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isActionHeaderDisplayed(), GlobalConstants.isActionHeaderDisplayed);
		assertTrue(partnerPolicyMappingPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);

		assertFalse(partnerPolicyMappingPage.isFilterResetButtonDisplayed(),
				GlobalConstants.isFilterResetButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isFilterButtonDisplayed(), GlobalConstants.isFilterButtonDisplayed);
		partnerPolicyMappingPage.clickOnFilterButton();
		assertTrue(partnerPolicyMappingPage.isFilterButtonDisabled(), GlobalConstants.isFilterButtonDisabled);
		assertTrue(partnerPolicyMappingPage.isFilterResetButtonEnabled(), GlobalConstants.isFilterResetButtonEnabled);
		assertTrue(partnerPolicyMappingPage.isApplyFilterButtonDisabled(), GlobalConstants.isApplyFilterButtonDisabled);
		partnerPolicyMappingPage.clickOnFilterButton();
		assertFalse(partnerPolicyMappingPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);

		assertTrue(partnerPolicyMappingPage.isPartnerIdFilterDisplayed(), GlobalConstants.isPartnerIdFilterDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeFilterDisplayed(),
				GlobalConstants.isPartnerTypeFilterDisplayed);
		assertTrue(partnerPolicyMappingPage.isOrganisationFilterDisplayed(),
				GlobalConstants.isOrganisationFilterDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyIdFilterDisplayed(), GlobalConstants.isPolicyIdFilterDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameFilterDisplayed(), GlobalConstants.isPolicyNameFilterDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupFilterDisplayed(),
				GlobalConstants.isPolicyGroupFilterDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusDropdownDisplayed(), GlobalConstants.isStatusDropdownDisplayed);

		assertTrue(partnerPolicyMappingPage.isPolicyIdFilterLabelDisplayed(),
				GlobalConstants.isPolicyIdFilterLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameFilterLabelDisplayed(),
				GlobalConstants.isPolicyNameFilterLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupFilterLabelDisplayed(),
				GlobalConstants.isPolicyGroupFilterLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusFilterLabelDisplayed(),
				GlobalConstants.isStatusFilterLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isOrganisationLabelDisplayed(),
				GlobalConstants.isOrganisationLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerIdLabelDisplayed(), GlobalConstants.isPartnerIdLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLabelDisplayed);

		assertTrue(partnerPolicyMappingPage.isPolicyIdFilterPlaceHolderDisplayed(),
				GlobalConstants.isPolicyIdFilterPlaceHolderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameFilterPlaceHolderDisplayed(),
				GlobalConstants.isPolicyNameFilterPlaceHolderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupFilterPlaceHolderDisplayed(),
				GlobalConstants.isPolicyGroupFilterPlaceHolderDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusFilterPlaceHolderDisplayed(),
				GlobalConstants.isStatusFilterPlaceHolderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerIdFilterPlaceHolderDisplayed(),
				GlobalConstants.isPartnerIdFilterPlaceHolderDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeFilterPlaceHolderDisplayed(),
				GlobalConstants.isPartnerTypeFilterPlaceHolderDisplayed);
		assertTrue(partnerPolicyMappingPage.isOrganisationFilterPlaceHolderDisplayed(),
				GlobalConstants.isOrganisationFilterPlaceHolderDisplayed);

		partnerPolicyMappingPage.clickOnStatusFilterDropdown();
		assertTrue(partnerPolicyMappingPage.isApprovedStatusDisplayed(), GlobalConstants.isApprovedStatusDisplayed);
		assertTrue(partnerPolicyMappingPage.isPendingForApprovalStatusDisplayed(),
				GlobalConstants.isPendingForApprovalStatusDisplayed);
		assertTrue(partnerPolicyMappingPage.isRejectedStatusDisplayed(), GlobalConstants.isRejectedStatusDisplayed);

		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.Random_DATA);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		assertTrue(partnerPolicyMappingPage.isNoResultsFoundMessageDisplayed(),
				GlobalConstants.isNoResultsFoundMessageDisplayed);
		assertTrue(partnerPolicyMappingPage.isCancelButtonOfTextBoxDisplayed(),
				GlobalConstants.isCancelButtonOfTextBoxDisplayed);
		partnerPolicyMappingPage.clickOnCancelButtonOfTextBox();
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter("authPollink");
		partnerPolicyMappingPage.clickOnFilterResetButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		assertTrue(partnerPolicyMappingPage.isPartnerIdDescIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(partnerPolicyMappingPage.isPartnerIdAscIconDisplayed(), GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeDescIconDisplayed(),
				GlobalConstants.isPartnerTypeDescIconDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeAscIconDisplayed(),
				GlobalConstants.isPartnerTypeAscIconDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupNameDescIconDisplayed(),
				GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupNameAscIconDisplayed(),
				GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(partnerPolicyMappingPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(partnerPolicyMappingPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(partnerPolicyMappingPage.isCreatedDateTimeDescISconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(partnerPolicyMappingPage.isCreatedDateTimeAscIconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescAscIcon);

		partnerPolicyMappingPage.clickOnPartnerIdDescIcon();
		partnerPolicyMappingPage.clickOnPartnerIdAscIcon();
		partnerPolicyMappingPage.clickOnOrgNameDescIcon();
		partnerPolicyMappingPage.clickOnOrgNameAscIcon();
		partnerPolicyMappingPage.clickOnPolicyGroupNameDescIcon();
		partnerPolicyMappingPage.clickOnPolicyGroupNameAscIcon();
		partnerPolicyMappingPage.clickOnPolicyNameDescIcon();
		partnerPolicyMappingPage.clickOnPolicyNameAscIcon();
		partnerPolicyMappingPage.clickOnCreatedDateTimeDescIcon();
		partnerPolicyMappingPage.clickOnCreatedDateTimeAscIcon();
		partnerPolicyMappingPage.clickOnStatusDescIcon();
		partnerPolicyMappingPage.clickOnStatusAscIcon();

		partnerPolicyMappingPage.clickOnStatusFilterDropdown();
		partnerPolicyMappingPage.clickOnPendingForApprovalStatus();
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		assertTrue(partnerPolicyMappingPage.isApproveRejectButtonEnabled(),
				GlobalConstants.isApproveRejectButtonEnabled);
		assertTrue(partnerPolicyMappingPage.isViewButtoEnabled(), GlobalConstants.isViewButtoEnabled);
		partnerPolicyMappingPage.clickOnPendingForApprovalPolicy();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyDetailsPageDisplayed(),
				GlobalConstants.isPartnerPolicyDetailsPageDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusPendingForApprovalDisplayed(),
				GlobalConstants.isStatusPendingForApprovalDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerStatusActivatedDisplayed(),
				GlobalConstants.isPartnerStatusActivatedDisplayed);
		partnerPolicyMappingPage.clickOnViewBackButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.clickOnStatusFilterDropdown();
		partnerPolicyMappingPage.clickOnApprovedStatus();
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertFalse(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		assertTrue(partnerPolicyMappingPage.isViewButtoEnabled(), GlobalConstants.isViewButtoEnabled);
		partnerPolicyMappingPage.clickOnApprovedPolicy();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyDetailsPageDisplayed(),
				GlobalConstants.isPartnerPolicyDetailsPageDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusApprovedDisplayed(), GlobalConstants.isStatusApprovedDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerStatusActivatedDisplayed(),
				GlobalConstants.isPartnerStatusActivatedDisplayed);
		partnerPolicyMappingPage.clickOnViewBackButton();

		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.clickOnStatusFilterDropdown();
		partnerPolicyMappingPage.clickOnRejectedStatus();
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertFalse(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		assertTrue(partnerPolicyMappingPage.isViewButtoEnabled(), GlobalConstants.isViewButtoEnabled);
		partnerPolicyMappingPage.clickOnRejectedPolicy();
		assertTrue(partnerPolicyMappingPage.isPartnerPolicyDetailsPageDisplayed(),
				GlobalConstants.isPartnerPolicyDetailsPageDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusRejectedDisplayed(), GlobalConstants.isStatusRejectedDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerStatusActivatedDisplayed(),
				GlobalConstants.isPartnerStatusActivatedDisplayed);
		partnerPolicyMappingPage.clickOnViewBackButton();

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
		partnerPolicyMappingPage.clickOnTitleBackIcon();

	}

	@Test(priority = 23, description = "Approve Reject Requested Policies")
	public void approveRejectRequestedPolicies() {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY_PARTLINK);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		assertTrue(partnerPolicyMappingPage.isApproveRejectButtonEnabled(),
				GlobalConstants.isApproveRejectButtonEnabled);
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();

		assertTrue(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameInPopupDisplayed(),
				GlobalConstants.isPolicyNameInPopupDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyPopupSubtitleDisplayed(),
				GlobalConstants.isPolicyPopupSubtitleDisplayed);
		assertTrue(partnerPolicyMappingPage.isConfirmationPopupDetailedMessageDisplayed(),
				GlobalConstants.isConfirmationPopupDetailedMessageDisplayed);
		assertTrue(partnerPolicyMappingPage.isApproveRejectButtonDisplayed(),
				GlobalConstants.isApproveRejectButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isApproveSubmitButtonDisplayed(),
				GlobalConstants.isApproveSubmitButtonDisplayed);
		partnerPolicyMappingPage.clickOnApproveSubmitButton();

		assertTrue(partnerPolicyMappingPage.isPartnerPolicyLinkingTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingTitleDisplayed);
		partnerPolicyMappingPage.clickOnFilterResetButton();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY_PARTLINK);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		assertTrue(partnerPolicyMappingPage.isStatusApprovedDisplayed(), GlobalConstants.isStatusApprovedDisplayed);
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertFalse(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);

		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY_PARTLINK2);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		partnerPolicyMappingPage.clickOnRejectButton();
		assertTrue(partnerPolicyMappingPage.isStatusRejectedDisplayed(), GlobalConstants.isStatusRejectedDisplayed);
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		partnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertFalse(partnerPolicyMappingPage.isApproveOrRejectConfirmationPopupDisplayed(),
				GlobalConstants.isApproveOrRejectConfirmationPopupDisplayed);

		loginAsAuthPartner();

		dashboardPage.clickOnPoliciesTitle();
		policiesPage.clickOnFilterButton();
		policiesPage.selectActivatedStatusInFilter();
		assertTrue(policiesPage.isStatusApprovedDisplayed(), GlobalConstants.isStatusApprovedDisplayed);
		policiesPage.selectRejectedStatusInFilter();
		assertTrue(policiesPage.isStatusRejectedDisplayed(), GlobalConstants.isStatusRejectedDisplayed);

	}

	@Test(priority = 24, description = "View Requested Policie Details")
	public void viewRequestedPolicieDetails() {

		dashboardPage = new DashboardPage(driver);
		partnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		partnerPolicyMappingPage.clickOnFilterButton();
		partnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULT_POLICYGROUP);
		partnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY_PARTLINK);
		partnerPolicyMappingPage.clickOnApplyFilterButton();
		partnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		assertTrue(partnerPolicyMappingPage.isViewButtoEnabled(), GlobalConstants.isViewButtoEnabled);
		partnerPolicyMappingPage.clickOnviewButton();

		assertTrue(partnerPolicyMappingPage.isPartnerPolicyDetailsPageDisplayed(),
				GlobalConstants.isPartnerPolicyDetailsPageDisplayed);
		assertTrue(partnerPolicyMappingPage.isHomeButtonDisplayed(), GlobalConstants.isHomeButtonDisplayed);
		assertTrue(partnerPolicyMappingPage.isListOfPartnerPolicyLinkagesDisplayed(),
				GlobalConstants.isListOfPartnerPolicyLinkagesDisplayed);

		assertTrue(partnerPolicyMappingPage.isPartnerIdLabelDisplayed(), GlobalConstants.isPartnerIdLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerIdContextDisplayed(), GlobalConstants.isPartnerIdContextDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusApprovedDisplayed(), GlobalConstants.isStatusApprovedDisplayed);
		assertTrue(partnerPolicyMappingPage.isCreatedOnLabelDisplayed(), GlobalConstants.isCreatedOnLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isCreatedDateDisplayed(), GlobalConstants.isCreatedDateDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyIdLabelDisplayed(), GlobalConstants.isPolicyIdLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameLabelDisplayed(), GlobalConstants.isPolicyNameLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyNameContextDisplayed(),
				GlobalConstants.isPolicyNameContextDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupLabelDisplayed(), GlobalConstants.isPolicyGroupLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPolicyGroupContextDisplayed(),
				GlobalConstants.isPolicyGroupContextDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerTypeContextDisplayed(),
				GlobalConstants.isPartnerTypeContextDisplayed);
		assertTrue(partnerPolicyMappingPage.isOrgNameLabelDisplayed(), GlobalConstants.isOrgNameLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isOrgNameContextDisplayed(), GlobalConstants.isOrgNameContextDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerStatusLabelDisplayed(),
				GlobalConstants.isPartnerStatusLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerStatusActivatedDisplayed(),
				GlobalConstants.isPartnerStatusActivatedDisplayed);

		assertTrue(partnerPolicyMappingPage.isCommentsLabelDisplayed(), GlobalConstants.isCommentsLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isAdminCommentsLabelDisplayed(),
				GlobalConstants.isAdminCommentsLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerCommentsLabelDisplayed(),
				GlobalConstants.isPartnerCommentsLabelDisplayed);
		assertTrue(partnerPolicyMappingPage.isStatusApprovedDisplayed(), GlobalConstants.isStatusApprovedDisplayed);
		assertTrue(partnerPolicyMappingPage.isCommentsCreatedDateDisplayed(), GlobalConstants.isCreatedDateDisplayed);
		assertTrue(partnerPolicyMappingPage.isPartnerCommentsContextDisplayed(),
				GlobalConstants.isPartnerCommentsContextDisplayed);
		partnerPolicyMappingPage.clickOnTitleBackIcon();

	}

	@Test(priority = 25, description = "Oidc Client Deactivate")
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

	@Test(priority = 26, description = "Oidc Client Tabular View")
	public void oidcClientTabularView() {

		dashboardPage = new DashboardPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);
		basePage = new BasePage(driver);

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
		assertTrue(oidcClientPage.isActivatedStatusInFilterDisplayed(),
				GlobalConstants.isActivatedStatusInFilterDisplayed);
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

	@Test(priority = 27, description = "Oidc Client View Details")
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

	@Test(priority = 28, description = "ApiKey Deactivate")
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
		basePage.navigateForword();

		loginAsAuthPartner();

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

	@Test(priority = 29, description = "API Key Tabular View")
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
		assertTrue(apiKeyPage.isApiKeyNameAscIconDisplayed(), GlobalConstants.isActionHeaderDisplayed);
		assertTrue(apiKeyPage.isPolicyGroupNameDescIconDisplayed(), GlobalConstants.isPolicyGroupNameDescIconDisplayed);
		assertTrue(apiKeyPage.isPolicyGroupNameAscIconDisplayed(), GlobalConstants.isPolicyGroupNameAscIconDisplayed);
		assertTrue(apiKeyPage.isPolicyNameDescIconDisplayed(), GlobalConstants.isPolicyNameDescIconDisplayed);
		assertTrue(apiKeyPage.isPolicyNameAscIconDisplayed(), GlobalConstants.isPolicyNameAscIconDisplayed);
		assertTrue(apiKeyPage.isCreatedDateTimeDescISconDisplayed(),
				GlobalConstants.isCreatedDateTimeDescISconDisplayed);
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
		apiKeyPage.enterInvalidDataInAdminApiKeyNameFilter(GlobalConstants.Random_DATA);
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
		assertTrue(apiKeyPage.isApiKeyDetailsPageDisplayed(), GlobalConstants.isOidcClientDetailsPageDisplayed);
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

	@Test(priority = 30, description = "Api Key View Details")
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
*/
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
