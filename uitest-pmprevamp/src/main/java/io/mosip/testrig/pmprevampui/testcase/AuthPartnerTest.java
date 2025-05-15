package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.fw.util.PmpTestUtil;
import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
import io.mosip.testrig.pmprevampui.kernel.util.KeycloakUserManager;
import io.mosip.testrig.pmprevampui.pages.ApiKeyPage;
import io.mosip.testrig.pmprevampui.pages.AuthPolicyPage;
import io.mosip.testrig.pmprevampui.pages.BasePage;
import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.OidcClientPage;
import io.mosip.testrig.pmprevampui.pages.PartnerCertificatePage;
import io.mosip.testrig.pmprevampui.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmprevampui.pages.PoliciesPage;
import io.mosip.testrig.pmprevampui.pages.ProfilePage;
import io.mosip.testrig.pmprevampui.pages.RegisterPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class AuthPartnerTest extends BaseClass {

	@Test(priority = 1, description = "Uploading Trust Certificate")
	public void uploadTrustCertificate() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);

		assertTrue(dashboardPage.isTermsAndConditionsPopUppDisplayed(),
				GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardPage.clickOnCheckbox();
		assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
		dashboardPage.clickOnProceedButton();
		dashboardPage.clickOnCertificateTrustStore();
//		dashboardpage.clickOnRootOFTrustCertText();
		dashboardPage.clickOnRootCertificateUploadButton();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnpartnerpartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.ClickonSubmitButtonForAdmin();
		partnerCertificatePage.ClickOnGoBackButton();
		dashboardPage.clickOnRootCertificateUploadButton();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnpartnerpartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.ClickonSubmitButtonForAdmin();
		partnerCertificatePage.ClickOnGoBackButton();
		dashboardPage.clickOnProfileDropdown();
		assertTrue(dashboardPage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);
		dashboardPage.clickOnLogoutButton();
	}

	@Test(priority = 2, description = "This is a test case register new user")
	public void RegisterNewUser() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		LoginPage loginPage = new LoginPage(driver);

		logoutFromPartner(dashboardPage);

		RegisterPage registerPage = loginPage.clickRegisterButton();
		assertTrue(loginPage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		loginPage.clickRegisterButton();

		registerPage.enterFirstName("pmpui-auth");
		assertTrue(registerPage.isLastNameTextBoxDisplayed(), GlobalConstants.isLastNameTextBoxDisplayed);
		registerPage.enterLastName("pmpui-auth");

		assertTrue(registerPage.isOrganizationNameDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
		registerPage.enterOrganizationName("AABBCC");

		assertTrue(registerPage.isPartnerTypeDropdownDisplayed(), GlobalConstants.isPartnerTypeDropdownDisplayed);
		registerPage.selectPartnerTypeDropdown(2);

		assertTrue(registerPage.isAddressTextBoxDisplayed(), GlobalConstants.isAddressTextBoxDisplayed);
		registerPage.enterAddress("0" + data);

		assertTrue(registerPage.isEmailTextBoxDisplayed(), GlobalConstants.isEmailTextBoxDisplayed);
		registerPage.enterEmail("0" + data + "@gmail.com");

		assertTrue(registerPage.isPhoneNumberTextboxDisplayed(), GlobalConstants.isPhoneNumberTextboxDisplayed);
		registerPage.enterPhone("9876543210");

		assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(),
				GlobalConstants.isNotificationLanguageDropdownDisplayed);
		registerPage.selectNotificationLanguageDropdown();

		assertTrue(registerPage.isUsernameTextBoxDisplayed(), GlobalConstants.isUsernameTextBoxDisplayed);
		registerPage.enterUsername("pmpui-auth");

		assertTrue(registerPage.isPasswordTextBoxDisplayed(), GlobalConstants.isPasswordTextBoxDisplayed);
		registerPage.enterPassword("mosip123");

		assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(), GlobalConstants.isPasswordConfirmTextBoxDisplayed);
		registerPage.enterPasswordConfirm("mosip123");

		assertTrue(registerPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		dashboardPage = registerPage.clickSubmitButton();

		assertTrue(dashboardPage.isSelectPolicyGroupPopUpDisplayed(),
				GlobalConstants.isSelectPolicyGroupPopUpDisplayed);
		dashboardPage.selectPolicyGroupDropdownForInvalid(data + 123);
		assertTrue(dashboardPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataAvailableTextDisplayed);
		dashboardPage.clickOnSubmitButton();
		assertFalse(dashboardPage.isTermsAndConditionsPopUppDisplayed(),
				GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardPage.clickOnSelectPolicyGroupLogout();

		assertTrue(loginPage.isPageNotFoundMessageDisplayed(), GlobalConstants.isKeycloakPageDisplayed);
		BasePage.navigateBack();
		dashboardPage.clickOnProfileDropdown();
		dashboardPage.clickOnLogoutButton();

		loginPage.enterUserName("pmpui-auth");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();
		dashboardPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		assertTrue(dashboardPage.isSubmitButtonSelectPolicyGroupPopUpDisplayed(),
				GlobalConstants.isSubmitButtonDisplayed);
		dashboardPage.clickOnSubmitButton();

		assertTrue(dashboardPage.isTermsAndConditionsPopUppDisplayed(),
				GlobalConstants.isTermsAndConditionsPopUppDisplayed);
//		dashboardpage.clickOnProceedButton();
//		assertFalse(dashboardpage.isPartnerCertificateTitleDisplayed(),GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnCheckbox();
		assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
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
		assertTrue(partnerCertificatePage.isCertFormatesTextDisplayed(),
				GlobalConstants.isCertFormatesTextDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
		dashboardPage = partnerCertificatePage.clickOnHomeButton();

		dashboardPage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();

		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
	    assertTrue(partnerCertificatePage.isReUploadPartnerCertificateSubTextDisplayed(),GlobalConstants.isReUploadPartnerCertificateSubTextDisplayed);
	    
	    assertTrue(partnerCertificatePage.isPartnerTypeLabelDisplayed(),
				GlobalConstants.isPartnerTypeLabelDisplayed);
	    assertTrue(partnerCertificatePage.isPartnerTypeValueDisplayed(),
				GlobalConstants.isPartnerTypeValueDisplayed);
	    assertTrue(partnerCertificatePage.isPartnerTypeValueDisabled(),
				GlobalConstants.isPartnerTypeValueDisabled);
	    
		assertTrue(partnerCertificatePage.isPartnerDomainTypeLabelDisplayed(),
				GlobalConstants.isPartnerDomainTypeDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainTypeValueDisplayed(),
				GlobalConstants.isPartnerDomainTypeValueDisplayed);
		assertTrue(partnerCertificatePage.isPartnerDomainTypeValueDisabled(),
				GlobalConstants.isPartnerDomainTypeValueDisabled);
		
		assertTrue(partnerCertificatePage.isUploadCertificateIconDisplayed(),
				GlobalConstants.isUploadCertificateIconDisplayed);
		assertTrue(partnerCertificatePage.isCertFormatesTextDisplayed(),
				GlobalConstants.isCertFormatesTextDisplayed);
		
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
		partnerCertificatePage.ClickOnsuccessMsgCloseButton();
		partnerCertificatePage.clickOnTitleBackButton();
	}

	@Test(priority = 3, description = "Policy creation and filter")
	public void verifyingPolicyCreationAndFilter() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		AuthPolicyPage authPolicyPage = new AuthPolicyPage(driver);
		BasePage basePage = new BasePage(driver);
		LoginPage loginPage = new LoginPage(driver);

		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();

		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		authPolicyPage.enterPolicyName(data);
		authPolicyPage.enterpolicyDescription(data);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();
		basePage.scrollToStartPage();

		createAuthPolicy(authPolicyPage, basePage, GlobalConstants.AUTHPOLICY01,
				GlobalConstants.AUTHPOLICY01_DESCRIPTION);

		basePage.scrollToStartPage();

		createAuthPolicy(authPolicyPage, basePage, GlobalConstants.AUTHPOLICY02,
				GlobalConstants.AUTHPOLICY02_DESCRIPTION);

		basePage.scrollToStartPage();

		createAuthPolicy(authPolicyPage, basePage, GlobalConstants.PENDING_POLICY,
				GlobalConstants.PENDING_POLICY_DESCRIPTION);

		basePage.scrollToStartPage();

		createAuthPolicy(authPolicyPage, basePage, GlobalConstants.DEACTIVATE_POLICY,
				GlobalConstants.DEACTIVATE_POLICY_DESCRIPTION);

		basePage.scrollToStartPage();
		authPolicyPage.clickOnFilterButton();

		filterAndPublishAuthPolicy(authPolicyPage, data);

		filterAndPublishAuthPolicy(authPolicyPage, GlobalConstants.AUTHPOLICY01);

		filterAndPublishAuthPolicy(authPolicyPage, GlobalConstants.AUTHPOLICY02);

		filterAndPublishAuthPolicy(authPolicyPage, GlobalConstants.PENDING_POLICY);

		filterAndDeactivateAuthPolicy(authPolicyPage, GlobalConstants.DEACTIVATE_POLICY);

		logoutFromPartner(dashboardPage);

		loginPage.enterUserName("pmpui-auth");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		loginPage.clickOnSomethingWentWrongHomeBtn();
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		dashboardPage.clickOnPoliciesTitle();

		assertTrue(policiesPage.isPoliciesEmptyTableDisplayed(), GlobalConstants.isPolicyEmptyTableIsDisplayed);
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
//		assertTrue(policiesPage.isSubTitleButtonDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(policiesPage.isTitleBackIconDisplayed(), GlobalConstants.isBackArrow);
//		assertTrue(policiesPage.isViewPolicyDetailsTextDisplayed(), GlobalConstants.isViewPolicyTitle);
		policiesPage.clickOnTitleBackIcon();
		assertTrue(policiesPage.isTitleOfPolicyPageDisplayed(), GlobalConstants.isTitleOfPolicyPageDisplayed);
		assertTrue(policiesPage.isRequestPolicyButtonDisplayed(), GlobalConstants.isRequestPolicyButtonDisplayed);

		requestPolicy(policiesPage, GlobalConstants.PENDING_POLICY);

		requestPolicy(policiesPage, GlobalConstants.AUTHPOLICY02);

		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterInvalidPolicyNameDropdown(GlobalConstants.DEACTIVATE_POLICY);
		assertTrue(policiesPage.isNoDataAvailableDisplayed(), GlobalConstants.isNoDataAvailableDisplayed);
		policiesPage.clickOnRequestPoliciesFormClearButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterInvalidPolicyNameDropdown(GlobalConstants.AUTHPOLICY01);
		assertTrue(policiesPage.isPolicyNameDisplayed(), GlobalConstants.isPolicyNameDisplayed);
		assertTrue(policiesPage.isPolicyDescriptionDisplayed(), GlobalConstants.isPolicyDescriptionDisplayed);
		policiesPage.enterComments(data);
		policiesPage.enterComments(GlobalConstants.SPACE);
//		assertFalse(policiesPage.isSubmitButtonEnabled(),GlobalConstants.isSubmitButtonEnable);
		policiesPage.enterComments(GlobalConstants.AUTHPOLICY01_DESCRIPTION);
		policiesPage.clickOnRequestPoliciesFormClearButton();

	}

	@Test(priority = 4, description = "Partner-Policy maaping & creation OIDC client")
	public void createOidecClient() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		PartnerPolicyMappingPage PartnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		dashboardPage.clickOnPartnerPolicyMappingTab();
		PartnerPolicyMappingPage.clickOnFilterButton();
		PartnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		PartnerPolicyMappingPage.enterPendingPolicyNameInFilter(data);
		PartnerPolicyMappingPage.clickOnApplyFilterButton();
		PartnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		PartnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertTrue(PartnerPolicyMappingPage.isConfirmationPopupDisplayed(),
				GlobalConstants.isConfirmationPopupDisplayed);
		assertTrue(PartnerPolicyMappingPage.isConfirmationPopupDetailedMessageDisplayed(),
				GlobalConstants.isConfirmationPopupDetailedMessageDisplayed);
		assertTrue(PartnerPolicyMappingPage.isApproveRejectButtonDisplayed(),
				GlobalConstants.isApproveRejectButtonDisplayed);
		assertTrue(PartnerPolicyMappingPage.isApproveSubmitButtonDisplayed(),
				GlobalConstants.isApproveSubmitButtonDisplayed);
		PartnerPolicyMappingPage.clickOnApproveSubmitButton();

		PartnerPolicyMappingPage.clickOnFilterButton();
		PartnerPolicyMappingPage.enterpolicyGroupFilter(GlobalConstants.DEFAULTPOLICYGROUP);
		PartnerPolicyMappingPage.enterPendingPolicyNameInFilter(GlobalConstants.AUTHPOLICY02);
		PartnerPolicyMappingPage.clickOnApplyFilterButton();
		PartnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		PartnerPolicyMappingPage.clickOnApproveOrRejectButton();
		PartnerPolicyMappingPage.clickOnRejectButton();

		dashboardPage.clickOnProfileDropdown();
		assertTrue(dashboardPage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);
		LoginPage loginPage = dashboardPage.clickOnLogoutButton();

		assertTrue(loginPage.isPageNotFoundMessageDisplayed(), GlobalConstants.isKeycloakPageDisplayed);
		BasePage.navigateBack();
		dashboardPage.clickOnProfileDropdown();
		dashboardPage.clickOnLogoutButton();

		loginPage.enterUserName("pmpui-auth");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();
		
		PartnerPolicyMappingPage.clickOnTitleBackIcon();

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
//		assertTrue(oidcClientPage.isOidcClientNameContextDisplayed(),GlobalConstants.isOidcClientNameContextDisplayed);  [Validating oidc client name]
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

		createOidcClient(oidcClientPage, GlobalConstants.ALPHANUMERIC);

		createOidcClient(oidcClientPage, GlobalConstants.AUTOMATION_UPPERCASE);

		createOidcClient(oidcClientPage, GlobalConstants.DEACTIVATE_POLICY2);

		createOidcClient(oidcClientPage, GlobalConstants.DEACTIVATE_POLICY);

		oidcClientPage.listPageCreateOidcClientButton();
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

	@Test(priority = 5, description = "APIkey creation")
	public void CreateApiKey() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		ApiKeyPage apiKeyPage = new ApiKeyPage(driver);

		loginAsAuthPartner(dashboardPage);

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();

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
//  	assertFalse(apiKeyPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnable);
		apiKeyPage.enterNameOfApiKeyTextBox(GlobalConstants.SPECIAL_CHARACTERS);
		apiKeyPage.clickOnSubmitButton();
		assertTrue(apiKeyPage.isDuplicateApiKeyNameErrorMessageDisplayed(),
				GlobalConstants.isDuplicateApiKeyNameErrorMessageDisplayed);

		apiKeyPage.clickOnDuplicateApiKeyNameErrorMessageCloseButton();
		apiKeyPage.enterNameOfApiKeyTextBox(GlobalConstants.SPECIAL_NUMERIC);
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

		createApiKey(apiKeyPage, GlobalConstants.AUTHPOLICY01);

		createApiKey(apiKeyPage, GlobalConstants.AUTHPOLICY02);
		apiKeyPage.clickOnApiListItem1();
		apiKeyPage.clickOnViewApiKeyBackButton();

		createApiKey(apiKeyPage, GlobalConstants.AUTHPOLICY03);

		createApiKey(apiKeyPage, GlobalConstants.AUTHPOLICY04);
		apiKeyPage.clickOnApiListItem1();
		apiKeyPage.clickOnViewApiKeyBackButton();

		createApiKey(apiKeyPage, GlobalConstants.AUTHPOLICY05);

		createApiKey(apiKeyPage, GlobalConstants.AUTHPOLICY06);

		assertTrue(apiKeyPage.isItemsPerPageDisplayed(), GlobalConstants.isItemsPerPageDisplayed);
		assertTrue(apiKeyPage.isItemsPerPageDropdownAvailable(), GlobalConstants.isItemsPerPageDropdownAvailable);
		apiKeyPage.clickOnItemsPerPageDropdown();
		apiKeyPage.selectNumberOfRecordPerPage();

		assertTrue(apiKeyPage.isBreadcombDisplayed(), GlobalConstants.isBreadcombDisplayed);
		apiKeyPage.clickOnBreadcomb();
	}
/*
	@Test(priority = 6, description = "Search with invalid policy name")
	public void searchWithInvalidPolicyName() {
		DashboardPage dashboardPage = new DashboardPage(driver);

		loginAsAuthPartner(dashboardPage);

		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		PoliciesPage policiesPage = dashboardPage.clickOnPoliciesTitle();

		assertTrue(policiesPage.isPoliciesPageDisplayed(), GlobalConstants.isPoliciesPageDisplayed);
		policiesPage.clickOnRequestPolicyButtonOfTabularPage();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
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

//	@Test(priority = 7, description = "Resubmit already submitted request policy")
	public void reSubmitAlreadySubmittedRequestPolicy() {
		DashboardPage dashboardpage = new DashboardPage(driver);

		loginAsAuthPartner(dashboardpage);

		assertTrue(dashboardpage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		PoliciesPage policiesPage = dashboardpage.clickOnPoliciesTitle();

		assertTrue(policiesPage.isPoliciesPageDisplayed(), GlobalConstants.isPoliciesPageDisplayed);
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

	@Test(priority = 8, description = "Request new policy with out uploading certificates")
	public void RequestNewPolicyWithoutUploadingCertificates() throws InterruptedException {

		DashboardPage dashboardPage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		RegisterPage registerPage = new RegisterPage(driver);
		LoginPage loginPage = new LoginPage(driver);

		logoutFromPartner(dashboardPage);

		loginPage.clickRegisterButton();

		registerPage.enterFirstName("pmpui-nocert");
		assertTrue(registerPage.isLastNameTextBoxDisplayed(), GlobalConstants.isLastNameTextBoxDisplayed);
		registerPage.enterLastName("  ");

		assertTrue(registerPage.isOrganizationNameDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
		registerPage.enterOrganizationName("AABBCC");

		assertTrue(registerPage.isPartnerTypeDropdownDisplayed(), GlobalConstants.isPartnerTypeDropdownDisplayed);
		registerPage.selectPartnerTypeDropdown(2);

		assertTrue(registerPage.isAddressTextBoxDisplayed(), GlobalConstants.isAddressTextBoxDisplayed);
		registerPage.enterAddress("0" + data);

		assertTrue(registerPage.isEmailTextBoxDisplayed(), GlobalConstants.isEmailTextBoxDisplayed);
		registerPage.enterEmail(data + "nocert" + "@gmail.com");

		assertTrue(registerPage.isPhoneNumberTextboxDisplayed(), GlobalConstants.isPhoneNumberTextboxDisplayed);
		registerPage.enterPhone("  ");

		assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(),
				GlobalConstants.isNotificationLanguageDropdownDisplayed);
		registerPage.selectNotificationLanguageDropdown();

		assertTrue(registerPage.isUsernameTextBoxDisplayed(), GlobalConstants.isUsernameTextBoxDisplayed);
		registerPage.enterUsername("pmpui-nocert");

		assertTrue(registerPage.isPasswordTextBoxDisplayed(), GlobalConstants.isPasswordTextBoxDisplayed);
		registerPage.enterPassword("mosip123");

		assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(), GlobalConstants.isPasswordConfirmTextBoxDisplayed);
		registerPage.enterPasswordConfirm("mosip123");

		assertTrue(registerPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		dashboardPage = registerPage.clickSubmitButton();

		assertTrue(registerPage.isPhoneNumberWarningMessageDisplayed(),
				GlobalConstants.isPhoneNumberWarningMessageDisplayed);
		registerPage.selectPartnerTypeDropdown(2);
		registerPage.enterPhone("8098768903");
		registerPage.enterPassword("mosip123");
		registerPage.enterPasswordConfirm("mosip123");

		dashboardPage = registerPage.clickSubmitButton();

		assertTrue(dashboardPage.isSelectPolicyGroupPopUpDisplayed(),
				GlobalConstants.isSelectPolicyGroupPopUpDisplayed);
		dashboardPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);

		assertTrue(dashboardPage.isSubmitButtonSelectPolicyGroupPopUpDisplayed(),
				GlobalConstants.isSubmitButtonDisplayed);
		dashboardPage.clickOnSubmitButton();

		assertTrue(dashboardPage.isTermsAndConditionsPopUppDisplayed(),
				GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardPage.clickOnCheckbox();

		assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
		dashboardPage.clickOnProceedButton();

		Thread.sleep(3000);
		assertTrue(dashboardPage.isPartnerCertificateTitleDisplayed(),
				GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardPage.clickOnPoliciesTitle();

		assertTrue(policiesPage.isPoliciesPageDisplayed(), GlobalConstants.isPoliciesPageDisplayed);
		policiesPage.clickOnRequestPolicyButton();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.clickOnPartnerIdDropdown();
		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataTextDisplaed);

	}

	@Test(priority = 9, description = "Create oidc client with out uploading certficates")
	public void CreateOidcClientWithoutUploadingCertificates() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		LoginPage loginPage = new LoginPage(driver);

		logoutFromPartner(dashboardPage);

		loginPage.enterUserName("pmpui-nocert");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();

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
//		assertTrue(oidcClientPage.isUserIdDoesNotExistsPopupDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
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

	@Test(priority = 10, description = " Create apikey without uploading certificates")
	public void CreateApiKeyWithoutUploadingCertificates() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		ApiKeyPage apiKeyPage = new ApiKeyPage(driver);
		LoginPage loginPage = new LoginPage(driver);

		logoutFromPartner(dashboardPage);

		loginPage.enterUserName("pmpui-nocert");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();

		oidcClientPage.clickOnApiKeyTab();

		assertTrue(apiKeyPage.isGenerateAPIKeyDisplayed(), GlobalConstants.isGenerateAPIKeyDisplayed);
		apiKeyPage.clickOnAPIKeyDisplayed();
		assertTrue(apiKeyPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		apiKeyPage.ClickOnPartnerIdDropdown();
		assertTrue(apiKeyPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isNoDataTextDisplaed);

	}

	@Test(priority = 11, description = "Deleting second redirct uri")
	public void deletingSecondRedirectUri() {
		DashboardPage dashboardPage = new DashboardPage(driver);

		loginAsAuthPartner(dashboardPage);

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.listPageCreateOidcClientButton();
		oidcClientPage.clickOnRedirectUriAddNew();
		assertTrue(oidcClientPage.isRedirectUri2TextBoxDisplayed(), GlobalConstants.isRedirectUri2TextBoxDisplayed);
		oidcClientPage.clickOnRedirectUri2Delete();
		assertFalse(oidcClientPage.isRedirectUri2TextBoxDisplayed(), GlobalConstants.isRedirectUri2TextBoxDisplayed);
	}

	@Test(priority = 12, description = "Adding second redirect uri")
	public void addingSecondRedirectUri() {
		DashboardPage dashboardPage = new DashboardPage(driver);

		loginAsAuthPartner(dashboardPage);

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.listPageCreateOidcClientButton();
		oidcClientPage.clickOnRedirectUriAddNew();
		assertTrue(oidcClientPage.isRedirectUri2TextBoxDisplayed(), GlobalConstants.isRedirectUri2TextBoxDisplayed);
	}

	@Test(priority = 13, description = "clear form oidc client")
	public void ClearFormOidcClient() {
		DashboardPage dashboardPage = new DashboardPage(driver);

		loginAsAuthPartner(dashboardPage);

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.listPageCreateOidcClientButton();
		oidcClientPage.enterNameOidcTextBox(data);
		oidcClientPage.enterPublicKeyTextBox(KeycloakUserManager.publicKeytemp);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnClearFormButton();
		assertTrue(oidcClientPage.isLogoUriempty(), GlobalConstants.isLogoUriempty);
	}

	@Test(priority = 14, description = "Using invalid data to create oidc")
	public void usingInvalidDataToCreateOIDC() {
		DashboardPage dashboardPage = new DashboardPage(driver);

		loginAsAuthPartner(dashboardPage);

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
		oidcClientPage.listPageCreateOidcClientButton();
		oidcClientPage.enterPublicKeyTextBox(GlobalConstants.INVALID_DATA);
		assertTrue(oidcClientPage.isPublicKeyFormatErrorDisplayed(), GlobalConstants.isPublicKeyFormatErrorDisplayed);
		oidcClientPage.enterLogoUrTextBox(GlobalConstants.INVALID_DATA);
		assertTrue(oidcClientPage.isInvalidLogoUriErrorDisplayed(), GlobalConstants.isInvalidLogoUriErrorDisplayed);
		oidcClientPage.enterRedirectUriTextBox(GlobalConstants.INVALID_DATA);
		assertTrue(oidcClientPage.isInvalidRedirectUriErrorDisplayed(),
				GlobalConstants.isInvalidRedirectUriErrorDisplayed);
	}

	@Test(priority = 15, description = "edit OIDC client")
	public void editOIDCClient() {
		DashboardPage dashboardPage = new DashboardPage(driver);

		loginAsAuthPartner(dashboardPage);

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
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

	@Test(priority = 16, description = "Deactivate OIDC client")
	public void deactivateOIDCClient() {
		DashboardPage dashboardPage = new DashboardPage(driver);

		loginAsAuthPartner(dashboardPage);

		assertTrue(dashboardPage.isAuthenticationServicesTitleDisplayed(),
				GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardPage.clickOnAuthenticationServicesTitle();
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

	@Test(priority = 17, description = "User Profile")
	public void userProfile() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		ProfilePage profilePage = new ProfilePage(driver);
		LoginPage loginPage = new LoginPage(driver);

		logoutFromPartner(dashboardPage);

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
		assertTrue(profilePage.isAddressLabelDisplayed(), GlobalConstants.isAddressLabelDisplayed);
//		assertTrue(profilepage.isAddressContextDisplayed(),GlobalConstants.isAddressContextDisplayed);
		assertTrue(profilePage.isPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLabelDisplayed);
		assertTrue(profilePage.isPartnerTypeContextDisplayed(), GlobalConstants.isPartnerTypeContextDisplayed);
		assertTrue(profilePage.isPhoneNumberLabelDisplayed(), GlobalConstants.isPhoneNumberLabelDisplayed);
		assertTrue(profilePage.isPhoneNumberContextDisplayed(), GlobalConstants.isPhoneNumberContextDisplayed);
		assertTrue(profilePage.isEmailAddressLabelDisplayed(), GlobalConstants.isEmailAddressLabelDisplayed);
//		assertTrue(profilepage.isEmailContextDisplayed(),GlobalConstants.isEmailContextDisplayed);
		assertTrue(profilePage.isUserNameLabelDisplayed(), GlobalConstants.isUserNameLabelDisplayed);
		assertTrue(profilePage.isUserNameContextDisplayed(), GlobalConstants.isUserNameContextDisplayed);
		profilePage.clickOnPhoneNumber();
		assertTrue(profilePage.isPhoneNumberClickable(), GlobalConstants.isPhoneNumberClickable);
		profilePage.clickOnTitleBackIcon();

	}

	@Test(priority = 18, description = "User dashboard of authentication partner")
	public void userDashboardOfAuthenticationPartner() {

		DashboardPage dashboardPage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		OidcClientPage oidcClientPage = new OidcClientPage(driver);

		loginAsAuthPartner(dashboardPage);

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
//		dashboardpage.clickOnContactusLink();
		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);

	}
*/
	private void logoutFromPartner(DashboardPage dashboardPage) {
		dashboardPage.clickOnProfileDropdown();
		assertTrue(dashboardPage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);
		LoginPage loginPage = dashboardPage.clickOnLogoutButton();

		assertTrue(loginPage.isPageNotFoundMessageDisplayed(), GlobalConstants.isKeycloakPageDisplayed);
		BasePage.navigateBack();
		dashboardPage.clickOnProfileDropdown();
		dashboardPage.clickOnLogoutButton();
	}

	private void loginAsAuthPartner(DashboardPage dashboardPage) {
		dashboardPage.clickOnProfileDropdown();
		assertTrue(dashboardPage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);
		LoginPage loginPage = dashboardPage.clickOnLogoutButton();

		assertTrue(loginPage.isPageNotFoundMessageDisplayed(), GlobalConstants.isKeycloakPageDisplayed);
		BasePage.navigateBack();
		dashboardPage.clickOnProfileDropdown();
		dashboardPage.clickOnLogoutButton();

		loginPage.enterUserName("pmpui-auth");
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();

		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
	}

	private void createAuthPolicy(AuthPolicyPage authpolicyPage, BasePage basePage, String policyNameValue,
			String policyDescValue) {
		authpolicyPage.clickOnCreateAuthPolicyButton();
		authpolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULTPOLICYGROUP);
		authpolicyPage.enterPolicyName(policyNameValue);
		authpolicyPage.enterpolicyDescription(policyDescValue);
		authpolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
		authpolicyPage.clickOnSaveAsDraftButton();
		authpolicyPage.clickOnGoBackButton();
	}

	private void filterAndPublishAuthPolicy(AuthPolicyPage authPolicyPage, String policyNameFilterValue) {
		authPolicyPage.enterPolicyNameInFilter(policyNameFilterValue);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();

	}

	private void filterAndDeactivateAuthPolicy(AuthPolicyPage authPolicyPage, String policyNameFilterValue) {
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

	private void requestPolicy(PoliciesPage policiesPage, String authPolicyName) {
		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterAuthPolicyNameDropdown(authPolicyName);
		policiesPage.enterComments(data);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

	}

	private void createOidcClient(OidcClientPage oidcClientPage, String oidcTextBoxValue) {
		oidcClientPage.listPageCreateOidcClientButton();
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

	private void createApiKey(ApiKeyPage apiKeyPage, String apiKeyTextBoxValue) {
		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox(apiKeyTextBoxValue);
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();
	}
}
