package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
import io.mosip.testrig.pmprevampui.kernel.util.KeycloakUserManager;
import io.mosip.testrig.pmprevampui.pages.ApiKeyPage;
import io.mosip.testrig.pmprevampui.pages.BasePage;
import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.OidcClientPage;
import io.mosip.testrig.pmprevampui.pages.PartnerCertificatePage;
import io.mosip.testrig.pmprevampui.pages.PartnerPolicyMappingPage;
import io.mosip.testrig.pmprevampui.pages.PoliciesPage;
import io.mosip.testrig.pmprevampui.pages.RegisterPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;
import io.mosip.testrig.pmprevampui.utility.TestRunner;

public class AuthPartnerTest extends BaseClass {

	@Test(priority = 1, description = "Uploading Trust Certificate")
	public void uploadTrustCertificate() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		RegisterPage registerPage = new RegisterPage(driver);

		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(),GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardpage.clickOnCheckbox();
		assertTrue(dashboardpage.isProceedButtonDisplayed(),GlobalConstants.isProceedButtonDisplayed);
		dashboardpage.clickOnProceedButton();
		dashboardpage.clickOnCertificateTrustStore();
//		dashboardpage.clickOnRootOFTrustCertText();
		dashboardpage.clickOnRootCertificateUploadButton();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnpartnerpartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.ClickonSubmitButtonForAdmin();
		partnerCertificatePage.ClickOnGoBackButton();
		dashboardpage.clickOnRootCertificateUploadButton();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnpartnerpartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.ClickonSubmitButtonForAdmin();
		partnerCertificatePage.ClickOnGoBackButton();
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(),GlobalConstants.isLogoutButtonDisplayed);
		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
	}

	@Test(priority = 2, description = "This is a test case register new user")
	public void registerNewUser() throws InterruptedException {

		DashboardPage dashboardpage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(),GlobalConstants.isLogoutButtonDisplayed);
		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		RegisterPage registerPage = loginpage.clickRegisterButton();
		assertTrue(loginpage.isLoginPageDisplayed(),GlobalConstants.isLoginPageDisplayed);
		loginpage.clickRegisterButton();

		registerPage.enterFirstName("pmpui-auth");
		assertTrue(registerPage.isLastNameTextBoxDisplayed(),GlobalConstants.isLastNameTextBoxDisplayed);
		registerPage.enterLastName("pmpui-auth");

		assertTrue(registerPage.isOrganizationNameDisplayed(),GlobalConstants.isOrganizationNameDisplayed);
		registerPage.enterOrganizationName("AABBCC");

		assertTrue(registerPage.isPartnerTypeDropdownDisplayed(),GlobalConstants.isPartnerTypeDropdownDisplayed);
		registerPage.selectPartnerTypeDropdown(2);

		assertTrue(registerPage.isAddressTextBoxDisplayed(),GlobalConstants.isAddressTextBoxDisplayed);
		registerPage.enterAddress("0" + data);

		assertTrue(registerPage.isEmailTextBoxDisplayed(),GlobalConstants.isEmailTextBoxDisplayed);
		registerPage.enterEmail("0" + data + "@gmail.com");

		assertTrue(registerPage.isPhoneNumberTextboxDisplayed(),GlobalConstants.isPhoneNumberTextboxDisplayed);
		registerPage.enterPhone("9876543210");

		assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(),GlobalConstants.isNotificationLanguageDropdownDisplayed);
		registerPage.selectNotificationLanguageDropdown();

		assertTrue(registerPage.isUsernameTextBoxDisplayed(),GlobalConstants.isUsernameTextBoxDisplayed);
		registerPage.enterUsername("pmpui-auth");

		assertTrue(registerPage.isPasswordTextBoxDisplayed(),GlobalConstants.isPasswordTextBoxDisplayed);
		registerPage.enterPassword("mosip123");

		assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(),GlobalConstants.isPasswordConfirmTextBoxDisplayed);
		registerPage.enterPasswordConfirm("mosip123");

		assertTrue(registerPage.isSubmitButtonDisplayed(),GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage = registerPage.clickSubmitButton();

		assertTrue(dashboardpage.isSelectPolicyGroupPopUpDisplayed(),GlobalConstants.isSelectPolicyGroupPopUpDisplayed);
		dashboardpage.selectSelectPolicyGroupDropdownForInvalid(data + 123);
		assertTrue(dashboardpage.isNoDataAvailableTextDisplayed(),GlobalConstants.isNoDataAvailableTextDisplayed);
		dashboardpage.clickOnSubmitButton();
		assertFalse(dashboardpage.isTermsAndConditionsPopUppDisplayed(),GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardpage.clickOnSelectPolicyGroupLogout();

		loginpage.enterUserName("pmpui-auth");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();
		dashboardpage.selectSelectPolicyGroupDropdown("automationui policy group");
		assertTrue(dashboardpage.isSubmitButtonSelectPolicyGroupPopUpDisplayed(),GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage.clickOnSubmitButton();

		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(),GlobalConstants.isTermsAndConditionsPopUppDisplayed);
//		dashboardpage.clickOnProceedButton();
//		assertFalse(dashboardpage.isPartnerCertificateTitleDisplayed(),GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardpage.clickOnCheckbox();
        assertTrue(dashboardpage.isProceedButtonDisplayed(),GlobalConstants.isProceedButtonDisplayed);
		dashboardpage.clickOnProceedButton();

		Thread.sleep(3000);

		assertTrue(dashboardpage.isPartnerCertificateTitleDisplayed(),GlobalConstants.isPartnerCertificateTitleDisplayed);
        dashboardpage.clickOnPartnerCertificateTitle();

		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.clickOnUploadButton();

		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();

		assertTrue(partnerCertificatePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		dashboardpage = partnerCertificatePage.clickOnHomeButton();

		dashboardpage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();

		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(),GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
//	    assertTrue(partnerCertificatePage.isReUploadPartnerCertificateSubTextDisplayed(),GlobalConstants.isReUploadPartnerCertificateSubTextDisplayed);

		assertTrue(partnerCertificatePage.isPartnerDomainTypeDisplayed(),GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		assertTrue(partnerCertificatePage.isPartnerCertOvelayDisplayed(),GlobalConstants.iReUploadPartnerCertificateTextDisplayed);

		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOnRemoveCertificateButton();

		partnerCertificatePage.uploadCertificateInvalidCert();
		assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(),GlobalConstants.isInvalidCertFormatePopupDisplayed);

		partnerCertificatePage.clickOnCertificateUploadCancelButton();

		partnerCertificatePage.clickOnDownloadButton();
		partnerCertificatePage.clickOnOriginalCertificateDownloadButton();
		assertTrue(partnerCertificatePage.isOriginalSignedCertDownloadedPopupDisplayed(),GlobalConstants.isOriginalCertificateDownloadPopupDisplayed);

		partnerCertificatePage.clickOnMosipSignedCertificateDownloadButton();
		assertTrue(partnerCertificatePage.isMosipSignedCertPopupDisplayed(),GlobalConstants.isMosipCertificateDownloadPopupDisplayed);

//	    assertTrue(partnerCertificatePage.isSucessMessageDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.ClickOnsuccessMsgCloseButton();
	}

	@Test(priority = 3, description = "Policy creation and filter")
	public void VerifyingPolicyCreationAndFilter() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);

		dashboardpage.clickOnPolicyButton();
		policiesPage.clickOnpoliciesAuthPolicyTab();

		policiesPage.clickOnCreateAuthPolicyButton();
		policiesPage.selectpolicyGroupDropdown("automationui policy group");
		policiesPage.enterPolicyName(data);
		policiesPage.enterpolicyDescription(data);
		policiesPage.uploadPolicyData();
		policiesPage.clickOnCreatePolicyFormSubmitButton();
		policiesPage.clickOnGoBackButton();
		policiesPage.clickOnFilterButton();
		policiesPage.enterpolicyGroupFilterBox("automationui policy group");
		policiesPage.clickOnApplyFilterButton();
		policiesPage.clickOnPoliciesListViewElipsisButton();
		policiesPage.clickOnPolicyPublishButton();
		policiesPage.clickOnPublishPolicyButton();
		policiesPage.clickOnSuccessMsgCloseButton();
		policiesPage.clickOnPublishPolicyCloseButton();

		policiesPage.clickOnCreateAuthPolicyButton();
		policiesPage.selectpolicyGroupDropdown("automationui policy group");
		policiesPage.enterPolicyName("authpolicy01");
		policiesPage.enterpolicyDescription("auth policy 01");
		policiesPage.uploadPolicyData();
		policiesPage.clickOnCreatePolicyFormSubmitButton();
		policiesPage.clickOnGoBackButton();
		policiesPage.clickOnFilterButton();
		policiesPage.enterPendingPolicyNameInFilter("authpolicy01");
		policiesPage.clickOnApplyFilterButton();
		policiesPage.clickOnPoliciesListViewElipsisButton();
		policiesPage.clickOnPolicyPublishButton();
		policiesPage.clickOnPublishPolicyButton();
		policiesPage.clickOnSuccessMsgCloseButton();
		policiesPage.clickOnPublishPolicyCloseButton();

		policiesPage.clickOnCreateAuthPolicyButton();
		policiesPage.selectpolicyGroupDropdown("automationui policy group");
		policiesPage.enterPolicyName("authpolicy02");
		policiesPage.enterpolicyDescription("auth policy 02");
		policiesPage.uploadPolicyData();
		policiesPage.clickOnCreatePolicyFormSubmitButton();
		policiesPage.clickOnGoBackButton();
		policiesPage.clickOnFilterButton();
		policiesPage.enterPendingPolicyNameInFilter("authpolicy02");
		policiesPage.clickOnApplyFilterButton();
		policiesPage.clickOnPoliciesListViewElipsisButton();
		policiesPage.clickOnPolicyPublishButton();
		policiesPage.clickOnPublishPolicyButton();
		policiesPage.clickOnSuccessMsgCloseButton();
		policiesPage.clickOnPublishPolicyCloseButton();

		policiesPage.clickOnCreateAuthPolicyButton();
		policiesPage.selectpolicyGroupDropdown("automationui policy group");
		policiesPage.enterPolicyName("pending auth");
		policiesPage.enterpolicyDescription("pending approval auth policy");
		policiesPage.uploadPolicyData();
		policiesPage.clickOnCreatePolicyFormSubmitButton();
		policiesPage.clickOnGoBackButton();
		policiesPage.clickOnFilterButton();
		policiesPage.enterPendingPolicyNameInFilter("pending auth");
		policiesPage.clickOnApplyFilterButton();
		policiesPage.clickOnPoliciesListViewElipsisButton();
		policiesPage.clickOnPolicyPublishButton();
		policiesPage.clickOnPublishPolicyButton();
		policiesPage.clickOnSuccessMsgCloseButton();
		policiesPage.clickOnPublishPolicyCloseButton();

		policiesPage.clickOnCreateAuthPolicyButton();
		policiesPage.selectpolicyGroupDropdown("automationui policy group");
		policiesPage.enterPolicyName("deactivate auth");
		policiesPage.enterpolicyDescription("deactivate auth policy");
		policiesPage.uploadPolicyData();
		policiesPage.clickOnCreatePolicyFormSubmitButton();
		policiesPage.clickOnGoBackButton();
		policiesPage.clickOnFilterButton();
		policiesPage.enterPendingPolicyNameInFilter("deactivate auth");
		policiesPage.clickOnApplyFilterButton();
		policiesPage.clickOnPoliciesListViewElipsisButton();
		policiesPage.clickOnPolicyPublishButton();
		policiesPage.clickOnPublishPolicyButton();
		policiesPage.clickOnSuccessMsgCloseButton();
		policiesPage.clickOnPublishPolicyCloseButton();
		policiesPage.clickOnPoliciesListViewElipsisButton();
		policiesPage.clickOnDeactivateButton();
		policiesPage.clickOnDeactivateConfirmButton();

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(),GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName("pmpui-auth");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();

		assertTrue(dashboardpage.isPoliciesTitleDisplayed(),GlobalConstants.isPoliciesTitleDisplayed);
		dashboardpage.clickOnPoliciesTitle();

		assertTrue(policiesPage.isPoliciesEmptyTableDisplayed(),GlobalConstants.isPolicyEmptyTableIsDisplayed);
		assertTrue(policiesPage.isPoliciesEmptyTableEnabled(),GlobalConstants.isRequestPolicyEnabled);
		policiesPage.clickOnRequestPolicyButton();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(),GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.selectPartnerIdDropdown();
        assertTrue(policiesPage.isPolicyNameDropdownDisplayed(),GlobalConstants.isPolicyNameDropdownDisplayed);
		policiesPage.selectPolicyNameDropdown(data);
		policiesPage.enterComments(data);
		assertTrue(policiesPage.isSubmitButtonDisplayed(),GlobalConstants.isSubmitButtonDisplayed);
		policiesPage.clickSubmitButton();
		assertTrue(policiesPage.isPolicySubmittedSuccessfullyDisplayed(),GlobalConstants.isSubmitButtonDisplayed);

		policiesPage.clickOnHomeButton();
		dashboardpage.clickOnPoliciesTitle();
		assertTrue(policiesPage.isListOfPolicyRequestedDisplayed(),GlobalConstants.isListOfPolicyRequestedTextDisplayed);
		assertTrue(policiesPage.isNextPageDisplayed(),GlobalConstants.isNextPageDisplayed);
		assertTrue(policiesPage.isPreviousPageDisplayed(),GlobalConstants.isPreviousPageDisplayed);
		assertTrue(policiesPage.isPendingForApprovalTextDisplayed(),GlobalConstants.isPendingForApprovalDisplayed);

		policiesPage.clickOnElipcisButton();
		policiesPage.clickOnCardViewButton();
		assertTrue(policiesPage.isPolicyDetailsPartnerIdLabelDisplayed(),GlobalConstants.isPolicyDetailsPartnerIdLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyGroupNameLabelDisplayed(),GlobalConstants.isPolicyDetailsPolicyGroupNameLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyGroupNameContextDisplayed(),GlobalConstants.isPolicyDetailsPolicyGroupNameContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPartnerTypeLabelDisplayed(),GlobalConstants.isPolicyDetailsPartnerTypeLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPartnerTypeContextDisplayed(),GlobalConstants.isPolicyDetailsPartnerTypeContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyNameLabelDisplayed(),GlobalConstants.isPolicyDetailsPolicyNameLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyNameContextDisplayed(),GlobalConstants.isPolicyDetailsPolicyNameContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyGroupDescriptionLabelDisplayed(),GlobalConstants.isPolicyDetailsPolicyGroupDescriptionLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyGroupDescriptionContextDisplayed(),GlobalConstants.isPolicyDetailsPolicyGroupDescriptionContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyNameDescriptionLabelDisplayed(),GlobalConstants.isPolicyDetailsPolicyNameDescriptionLabelDisplayed);
		assertTrue(policiesPage.isPolicyDetailsPolicyNameDescriptionContextDisplayed(),GlobalConstants.isPolicyDetailsPolicyNameDescriptionContextDisplayed);
		assertTrue(policiesPage.isPolicyDetailsCommentsDisplayed(),GlobalConstants.isPolicyDetailsCommentsDisplayed);
		assertTrue(policiesPage.isPolicyViewPageBackButtonEnabled(),GlobalConstants.isPolicyViewPageBackButtonEnabled);
		policiesPage.clickOnBackButton();

		assertTrue(policiesPage.isPartnerIdDescIconDisplayed(),GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(policiesPage.isPartnerIdAscIconDisplayed(),GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(policiesPage.isPartnerTypeDescIconDisplayed(),GlobalConstants.isPartnerTypeDescIcon);
		assertTrue(policiesPage.isPartnerTypeAscIconDisplayed(),GlobalConstants.isPartnerTypeAscIcon);
		assertTrue(policiesPage.isPolicyGroupNameDescIconDisplayed(),GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(policiesPage.isPolicyGroupNameAscIconDisplayed(),GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(policiesPage.isPolicyNameDescIconDisplayed(),GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(policiesPage.isPolicyNameAscIconDisplayed(),GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(policiesPage.isCreatedDateTimeDescISconDisplayed(),GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(policiesPage.isCreatedDateTimeAscIconDisplayed(),GlobalConstants.isCreatedDateTimeDescAscIcon);

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
		assertTrue(policiesPage.isSubTitleHomeButtonDisplayed(),GlobalConstants.isSubTitleHomeDisplayed);
//		assertTrue(policiesPage.isSubTitleButtonDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(policiesPage.isTitleBackIconDisplayed(),GlobalConstants.isBackArrow);
//		assertTrue(policiesPage.isViewPolicyDetailsTextDisplayed(), GlobalConstants.isViewPolicyTitle);
		policiesPage.clickOnTitleBackIcon();
        assertTrue(policiesPage.isTitleOfPolicyPageDisplayed(),GlobalConstants.isTitleOfPolicyPageDisplayed);
		assertTrue(policiesPage.isRequestPolicyButtonDisplayed(),GlobalConstants.isRequestPolicyButtonDisplayed);
		
		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterPendingPolicyNameDropdown("pending auth");
		policiesPage.enterComments(data);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();
		
		
		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterAuthPolicyNameDropdown("authpolicy02");
		policiesPage.enterComments(data);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

		policiesPage.clickOnRequestPolicyButtonOfTabularPage();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterInvalidPolicyNameDropdown("deactivate auth");
		assertTrue(policiesPage.isNoDataAvailableDisplayed(),GlobalConstants.isNoDataAvailableDisplayed);
		policiesPage.clickOnRequestPoliciesFormClearButton();
		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterInvalidPolicyNameDropdown("authpolicy01");
		assertTrue(policiesPage.isPolicyNameDisplayed(),GlobalConstants.isPolicyNameDisplayed);
		assertTrue(policiesPage.isPolicyDescriptionDisplayed(),GlobalConstants.isPolicyDescriptionDisplayed);
		policiesPage.enterComments(data);
		policiesPage.enterComments("   ");
//		assertFalse(policiesPage.isSubmitButtonEnabled(),GlobalConstants.isSubmitButtonEnable);
		policiesPage.enterComments("auth policy 01");
		policiesPage.clickOnRequestPoliciesFormClearButton();

	}

	@Test(priority = 4, description = "Partner-Policy maaping & creation OIDC client")
	public void createOidecClient() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		RegisterPage registerPage = new RegisterPage(driver);
		PartnerPolicyMappingPage PartnerPolicyMappingPage = new PartnerPolicyMappingPage(driver);

		dashboardpage.clickOnPartnerPolicyMappingTab();
		PartnerPolicyMappingPage.clickOnFilterButton();
		PartnerPolicyMappingPage.enterpolicyGroupFilter("automationui policy group");
		PartnerPolicyMappingPage.enterPendingPolicyNameInFilter(data);
		PartnerPolicyMappingPage.clickOnApplyFilterButton();
		PartnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		PartnerPolicyMappingPage.clickOnApproveOrRejectButton();
		assertTrue(PartnerPolicyMappingPage.isConfirmationPopupDisplayed(),GlobalConstants.isConfirmationPopupDisplayed);
		assertTrue(PartnerPolicyMappingPage.isConfirmationPopupDetailedMessageDisplayed(),GlobalConstants.isConfirmationPopupDetailedMessageDisplayed);
		assertTrue(PartnerPolicyMappingPage.isApproveRejectButtonDisplayed(),GlobalConstants.isApproveRejectButtonDisplayed);
		assertTrue(PartnerPolicyMappingPage.isApproveSubmitButtonDisplayed(),GlobalConstants.isApproveSubmitButtonDisplayed);
		PartnerPolicyMappingPage.clickOnApproveSubmitButton();

		PartnerPolicyMappingPage.clickOnFilterButton();
		PartnerPolicyMappingPage.enterpolicyGroupFilter("automationui policy group");
		PartnerPolicyMappingPage.enterPendingPolicyNameInFilter("authpolicy02");
		PartnerPolicyMappingPage.clickOnApplyFilterButton();
		PartnerPolicyMappingPage.clickOnPartnerListViewElipsisButton();
		PartnerPolicyMappingPage.clickOnApproveOrRejectButton();
		PartnerPolicyMappingPage.clickOnRejectButton();

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(),GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		assertTrue(loginpage.isLoginPageDisplayed(),GlobalConstants.isLoginPageDisplayed);

		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterUserName("pmpui-auth");
		loginPage.enterPassword(password);
		loginPage.ClickOnLoginButton();

		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(),GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardpage.clickOnAuthenticationServicesTitle();
		assertTrue(oidcClientPage.isOidcClientTabDisplayed(),GlobalConstants.isOidcClientTabDisplayed);

		assertTrue(oidcClientPage.isCreateOidcClientDisplayed(),GlobalConstants.isCreateOIDCClientDisplayed);
		assertTrue(oidcClientPage.isPartnerIDHeaderTextDisplayed(),GlobalConstants.isPartnerIDHeaderTextDisplayed);
		assertTrue(oidcClientPage.isPolicyGroupHeaderTextDisplayed(),GlobalConstants.isPolicyGroupHeaderTextDisplayed);
		assertTrue(oidcClientPage.isPolicyNameHeaderTextDisplayed(),GlobalConstants.isPolicyNameHeaderTextDisplayed);
		assertTrue(oidcClientPage.isOIDCClientNameHeaderTextDisplayed(),GlobalConstants.isOIDCClientNameHeaderTextDisplayed);
		assertTrue(oidcClientPage.isCreatedDateHeaderTextDisplayed(),GlobalConstants.isCreatedDateHeaderTextDisplayed);
		assertTrue(oidcClientPage.isStatusHeaderTextDisplayed(),GlobalConstants.isStatusHeaderTextDisplayed);
		assertTrue(oidcClientPage.isOIDCClientIDHeaderTextDisplayed(),GlobalConstants.isOIDCClientIDHeaderTextDisplayed);
		assertTrue(oidcClientPage.isActionHeaderTextDisplayed(),GlobalConstants.isActionHeaderTextDisplayed);
		oidcClientPage.clickOnCreateOidcClientButton();

		assertTrue(oidcClientPage.isPartnerIdDropdownDisplayed(),GlobalConstants.isPartnerIdDropdownDisplayed);
		oidcClientPage.selectPartnerIdDropdown();

		assertTrue(oidcClientPage.isPolicyNameDropdownDisplayed(),GlobalConstants.isPolicyNameDropdownDisplayed);
		oidcClientPage.selectPolicyNameDropdown(data);

		oidcClientPage.enterNameOidcTextBox(data);

		String publicKey = KeycloakUserManager.readJsonData(TestRunner.getResourcePath() + "/" + "config/" + "/publicKey.json").toString();

		oidcClientPage.enterPublicKeyTextBox(publicKey);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnSubmitButton();
		assertTrue(oidcClientPage.isDetailsSubmittedSuccessFullyDisplayed(),GlobalConstants.isAutherisationCodeTextDisplayed);
		oidcClientPage.clickConfirmationGoBackButton();

		oidcClientPage.clickOidcShowCopyPopupButton();
		oidcClientPage.clickCopyIdButton();

		oidcClientPage.clickCopyIdCloseButton();
		assertTrue(oidcClientPage.isOidcDetailsElipsisButtonDisplayed(),GlobalConstants.isAutherisationCodeTextDisplayed);
		oidcClientPage.clickOidcDetailsElipsisButton();
		assertTrue(oidcClientPage.isOidcDetailsViewButtonDisplayed(),GlobalConstants.isAutherisationCodeTextDisplayed);
		oidcClientPage.clickOidcEditButton();
		oidcClientPage.clickoidcEditAddNewRedirectUrl();
		assertFalse(oidcClientPage.isEditSecondRedirectUriTextBoxDisplayed(),GlobalConstants.isEditSecondRedirectUriTextBoxDisplayed);
		oidcClientPage.clickoidcEditAddNewRedirectUrl();
		oidcClientPage.EnterPublickeySecondTextBox(ConfigManager.getRedirectUri() + "c");
		oidcClientPage.clickOnOidcEditSubmitButton();
		assertTrue(oidcClientPage.isModifiedSuccessfullTextMessageDisplayed(),GlobalConstants.isAutherisationCodeTextDisplayed);
		oidcClientPage.clickConfirmationGoBackButton();

		assertTrue(oidcClientPage.isPartnerIdDescIconDisplayed(),GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(oidcClientPage.isPartnerIdAscIconDisplayed(),GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(oidcClientPage.isOidcClientNameDescIconDisplayed(),GlobalConstants.isOidcClientNameDescIcon);
		assertTrue(oidcClientPage.isOidcClientNameAscIconDisplayed(),GlobalConstants.isOidcClientNameAscIcon);
		assertTrue(oidcClientPage.isPolicyGroupNameDescIconDisplayed(),GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(oidcClientPage.isPolicyGroupNameAscIconDisplayed(),GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(oidcClientPage.isPolicyNameDescIconDisplayed(),GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(oidcClientPage.isPolicyNameAscIconDisplayed(),GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(oidcClientPage.isCreatedDateTimeDescISconDisplayed(),GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(oidcClientPage.isCreatedDateTimeAscIconDisplayed(),GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(oidcClientPage.isFilterButtonButtonEnabled(),GlobalConstants.isFilterButtonButtonEnabled);
		assertTrue(oidcClientPage.isSubTitleHomeButtonDisplayed(),GlobalConstants.isHomeButtonInAuthenticationDisplayed);

		oidcClientPage.clickOnFilterButton();
		oidcClientPage.clickOnOidcPartnerIdFilter();
		oidcClientPage.clickOnOidcSelectPolicyGroupFilter();
		oidcClientPage.clickOnOidcSelectPolicyNameFilter();
		oidcClientPage.clickOnOidcSelectClientNameFilter();
		oidcClientPage.clickOnOidcSelectStatusFilter();

		assertTrue(oidcClientPage.isfilterResetButtonDisplayed(),GlobalConstants.isfilterResetButtonDisplayed);
		oidcClientPage.clickOnFilterResetButton();
		assertTrue(oidcClientPage.isFilterButtonButtonEnabled(),GlobalConstants.isFilterButtonButtonEnabled);

		oidcClientPage.clickOnOidcClientListPageCreateOidcClientBtn();
		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(data);
		oidcClientPage.enterNameOidcTextBox(" ");
		oidcClientPage.enterPublicKeyTextBox(" ");
		oidcClientPage.enterLogoUrTextBox(" ");
		oidcClientPage.enterRedirectUriTextBox(" ");

		assertTrue(oidcClientPage.isEnterValidUriForLogoUriTextDisplayed(),GlobalConstants.isEnterValidLogoUriTextDisplayed);
		assertTrue(oidcClientPage.isEnterValidUriForRedirectUriTextDisplayed(),GlobalConstants.isEnterRedirectUriTextDisplayed);
		oidcClientPage.clickOnCreateOidcClearForm();

		oidcClientPage.selectPartnerIdDropdown();
		oidcClientPage.selectPolicyNameDropdown(data);
		oidcClientPage.enterNameOidcTextBox("trufgt");
		oidcClientPage.navigateBackDefaultButton();
		assertTrue(oidcClientPage.isBrowserConfirmationPopupDisplayed(),GlobalConstants.isBrowserConfirmationPopup);
		assertTrue(oidcClientPage.isBrowserConfirmationPopupProceedBtnDisplayed(),GlobalConstants.isBrowserConfirmationPopupProceedBtn);
		assertTrue(oidcClientPage.isBrowserConfirmationPopupCancelBtnDisplayed(),GlobalConstants.isBrowserConfirmationPopupCancelBtn);
		oidcClientPage.clickOnBrowserConfirmationPopupCancelBtn();
		oidcClientPage.navigateBackDefaultButton();
		oidcClientPage.clickOnBrowserConfirmationPopupProceedBtn();

	}

	@Test(priority = 5)
	public void CreateApiKey() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		ApiKeyPage apiKeyPage = new ApiKeyPage(driver);
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(),GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName("pmpui-auth");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();

		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(),GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardpage.clickOnAuthenticationServicesTitle();

		assertTrue(oidcClientPage.isApiKeyTabDisplayed(),GlobalConstants.isApiKeyTabDisplayed);
		oidcClientPage.clickOnApiKeyTab();
		assertTrue(apiKeyPage.isGenerateAPIKeyDisplayed(),GlobalConstants.isGenerateAPIKeyDisplayed);
		apiKeyPage.clickOnAPIKeyDisplayed();
		assertTrue(apiKeyPage.isPartnerIdDropdownDisplayed(),GlobalConstants.isPartnerIdDropdownDisplayed);
		apiKeyPage.selectPartnerIdDropdown();

		assertTrue(apiKeyPage.isPolicyNameDropdownDisplayed(),GlobalConstants.isPolicyNameDropdownDisplayed);
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox("0" + data);

		apiKeyPage.clickOnSubmitButton();
//		assertTrue(oidcClientPage.isAuthorizationCodeTextDisplayed(), GlobalConstants.isAutherisationCodeTextDisplayed);

		apiKeyPage.clickOnCopyIdButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		assertTrue(apiKeyPage.isConfirmationGoBackButtonDisplayed(),GlobalConstants.isGoBackButtonDisplayed);
		apiKeyPage.clickOnConfirmationGoBackButton();
		assertTrue(apiKeyPage.isApiListItem1Displayed(),GlobalConstants.isApiListDisplayed);
		apiKeyPage.clickOnApiListItem1();

		assertTrue(apiKeyPage.isApiKeyDetailsPartnerIdLabelDisplayed(),GlobalConstants.isApiKeyDetailsPartnerIdLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPartnerIdContextDisplayed(),GlobalConstants.isApiKeyDetailsPartnerIdContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyGroupLabelDisplayed(),GlobalConstants.isApiKeyDetailsPolicyGroupLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyGroupNameContextDisplayed(),GlobalConstants.isApiKeyDetailsPolicyGroupNameContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyNameLabelDisplayed(),GlobalConstants.isApiKeyDetailsPolicyNameLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyNameContextDisplayed(),GlobalConstants.isApiKeyDetailsPolicyNameContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyGoupDescriptionLabelDisplayed(),GlobalConstants.isApiKeyDetailsPolicyGoupDescriptionLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyGroupDescriptionContextDisplayed(),GlobalConstants.isApiKeyDetailsPolicyGroupDescriptionContextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyNameDescriptionLabelDisplayed(),GlobalConstants.isApiKeyDetailsPolicyNameDescriptionLabelDisplayed);
		assertTrue(apiKeyPage.isApiKeyDetailsPolicyDescriptionContextDisplayed(),GlobalConstants.isApiKeyDetailsPolicyDescriptionContextDisplayed);
		assertTrue(apiKeyPage.isViewApiKeyBackButtonDisplayed(),GlobalConstants.isViewApiKeyBackButtonDisplayed);
		assertTrue(apiKeyPage.isBackiconDisplayed(),GlobalConstants.isBackiconDisplayed);
		apiKeyPage.clickOnViewApiKeyBackButton();

		assertTrue(apiKeyPage.isApiKeyListPageGenerateApiKeyBtnDisplayed(),GlobalConstants.isApiKeyListPageGenerateApiKeyBtnDisplayed);
		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		assertTrue(apiKeyPage.isPartnerIdHelpTextDisplayed(),GlobalConstants.isPartnerIdHelpTextDisplayed);
		assertTrue(apiKeyPage.isPolicyNameHelpTextDisplayed(),GlobalConstants.isPolicyNameHelpTextDisplayed);
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox("0" + data);
		assertTrue(apiKeyPage.isClearButtonDisplayed(),GlobalConstants.isClearButtonDisplayed);
		apiKeyPage.clickOnClearButton();
		assertTrue(apiKeyPage.isCancelButtonDisplayed(),GlobalConstants.isCancelButtonDisplayed);
		apiKeyPage.clickOnCancelButton();

		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.enterPendingPolicyNameDropdown("pending auth");
		assertTrue(apiKeyPage.isnoDataAvailableTextDisplayed(),GlobalConstants.isnoDataAvailableTextDisplayed);
		apiKeyPage.clickOnClearButton();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox("#$%#&*");
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationHomeButton();

		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(),GlobalConstants.isAuthenticationServicesDisplayed);
		dashboardpage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnApiKeyTab();
		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
//  	assertFalse(apiKeyPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnable);
		apiKeyPage.enterNameOfApiKeyTextBox("#$%#&*");
		apiKeyPage.clickOnSubmitButton();
		assertTrue(apiKeyPage.isDuplicateApiKeyNameErrorMessageDisplayed(),GlobalConstants.isDuplicateApiKeyNameErrorMessageDisplayed);
		apiKeyPage.clickOnDuplicateApiKeyNameErrorMessageCancelButton();
		apiKeyPage.enterNameOfApiKeyTextBox("127484urhfufufjfhg$#^#*#&#G%#*#f7f43");
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();

		apiKeyPage.clickOnapiListElipsisButton();
		apiKeyPage.clickOnApiKeyDeactivateButton();
		assertTrue(apiKeyPage.isApiKeyDeactivateConfirmationTextDisplayed(),GlobalConstants.isApiKeyDeactivateConfirmationTextDisplayed);
		assertTrue(apiKeyPage.isApiKeyDeactivationInfoTextDisplayed(),GlobalConstants.isApiKeyDeactivationInfoTextDisplayed);
		assertTrue(apiKeyPage.isDeactivateCancelButtonDisplayed(),GlobalConstants.isDeactivateCancelButtonDisplayed);
		assertTrue(apiKeyPage.isDeactivateConfirmButtonDisplayed(),GlobalConstants.isDeactivateConfirmButtonDisplayed);
		apiKeyPage.clickOnDeactivateCancelButton();
		apiKeyPage.clickOnapiListElipsisButton();
		apiKeyPage.clickOnApiKeyDeactivateButton();
		apiKeyPage.clickOnDeactivateConfirmButton();
		apiKeyPage.clickOnDeactivatedApiKey();
		assertTrue(apiKeyPage.isDeactivatedApiKeyDisabled(),GlobalConstants.isDeactivatedApiKeyDisabled);
		assertTrue(apiKeyPage.isDeactivatedApiKeyGreyColored(),GlobalConstants.isDeactivatedApiKeyGreyColored);
		apiKeyPage.clickOnapiListElipsisButton();
		apiKeyPage.clickOnApiKeyDeactivateButton();
		assertFalse(apiKeyPage.isApiKeyDeactivateConfirmationTextDisplayed(),GlobalConstants.isApiKeyDeactivateConfirmationTextDisplayed);
		apiKeyPage.clickOnApiKeyViewButton();
		assertTrue(apiKeyPage.isApiKeyStatusDeactivatedDisplayed(),GlobalConstants.isApiKeyStatusDeactivated);
		apiKeyPage.clickOnViewApiKeyBackButton();

		assertTrue(apiKeyPage.isPartnerIdDescIconDisplayed(),GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(apiKeyPage.isPartnerIdAscIconDisplayed(),GlobalConstants.isPartnerIdDescAscIcon);
		assertTrue(apiKeyPage.isApiKeyNameDescIconDisplayed(),GlobalConstants.isApiKeyNameDescIcon);
		assertTrue(apiKeyPage.isApiKeyNameAscIconDisplayed(),GlobalConstants.isApiKeyNameAscIcon);
		assertTrue(apiKeyPage.isPolicyGroupNameDescIconDisplayed(),GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(apiKeyPage.isPolicyGroupNameAscIconDisplayed(),GlobalConstants.isPolicyGroupNameDescAscIcon);
		assertTrue(apiKeyPage.isPolicyNameDescIconDisplayed(),GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(apiKeyPage.isPolicyNameAscIconDisplayed(),GlobalConstants.isPolicyNameDescAscIcon);
		assertTrue(apiKeyPage.isCreatedDateTimeDescISconDisplayed(),GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(apiKeyPage.isCreatedDateTimeAscIconDisplayed(),GlobalConstants.isCreatedDateTimeDescAscIcon);
		assertTrue(apiKeyPage.isFilterButtonButtonEnabled(),GlobalConstants.isFilterButtonButtonEnabled);

		apiKeyPage.clickOnFilterButton();
		apiKeyPage.clickOnApiKeyPartnerIdFilter();
		apiKeyPage.clickOnApiKeySelectPolicyGroupFilter();
		apiKeyPage.clickOnApiKeySelectPolicyNameFilter();
		apiKeyPage.clickOnApiKeySelectStatusFilter();
		apiKeyPage.clickOnActivatedStatusApiKeyFilter();
		apiKeyPage.clickOnApiKeyNameDescIcon();
		apiKeyPage.clickOnApiKeyNameAscIcon();
		apiKeyPage.enterInvalidDataInApiKeyNameFilter("ab12er");
		assertTrue(apiKeyPage.isNoDataAvailabelDisplayed(),GlobalConstants.isNoDataAvailabelDisplayed);
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
		assertTrue(apiKeyPage.isFilterButtonButtonEnabled(),GlobalConstants.isFilterButtonButtonEnabled);

		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox("authpolicy01");
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();

		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox("authpolicy02");
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();
		apiKeyPage.clickOnApiListItem1();
		apiKeyPage.clickOnViewApiKeyBackButton();

		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox("authpolicy03");
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();

		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox("authpolicy04");
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();
		apiKeyPage.clickOnApiListItem1();
		apiKeyPage.clickOnViewApiKeyBackButton();

		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox("authpolicy05");
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();

		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox("authpolicy06");
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();

		apiKeyPage.clickOnApiKeyListPageGenerateApiKeyBtn();
		apiKeyPage.selectPartnerIdDropdown();
		apiKeyPage.selectPolicyNameDropdown(data);
		apiKeyPage.enterNameOfApiKeyTextBox("authpolicy07");
		apiKeyPage.clickOnSubmitButton();
		apiKeyPage.clickOnCopyIdCloseButton();
		apiKeyPage.clickOnConfirmationGoBackButton();

		assertTrue(apiKeyPage.isItemsPerPageDisplayed(),GlobalConstants.isItemsPerPageDisplayed);
		assertTrue(apiKeyPage.isItemsPerPageDropdownAvailable(),GlobalConstants.isItemsPerPageDropdownAvailable);
		apiKeyPage.clickOnItemsPerPageDropdown();
		apiKeyPage.selectNumberOfRecordPerPage();

		assertTrue(apiKeyPage.isBreadcombDisplayed(),GlobalConstants.isBreadcombDisplayed);
		apiKeyPage.clickOnBreadcomb();
	}

    @Test(priority = 6)
	public void searchWithInvalidPolicyName() {
		DashboardPage dashboardpage = new DashboardPage(driver);

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(),GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName("pmpui-auth");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();

		assertTrue(dashboardpage.isPoliciesTitleDisplayed(),GlobalConstants.isPoliciesTitleDisplayed);
		PoliciesPage policiesPage = dashboardpage.clickOnPoliciesTitle();

		assertTrue(policiesPage.isPoliciesPageDisplayed(),GlobalConstants.isPoliciesPageDisplayed);
		policiesPage.clickOnRequestPolicyButtonOfTabularPage();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(),GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.selectPartnerIdDropdown();

		assertTrue(policiesPage.isPolicyNameDropdownDisplayed(),GlobalConstants.isPolicyNameDropdownDisplayed);
		policiesPage.selectInvalidPolicyNameDropdown(data + "123");
		policiesPage.searchInPolicyName(data + "123");

		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(),GlobalConstants.isPolicyNameDropdownDisplayed);
		policiesPage.clickOnRequestPoliciesFormClearButton();

		assertEquals(policiesPage.getThePolicyCommentBoxText(),GlobalConstants.isPolicyCommentBoxTextDisplayed);
		assertEquals(policiesPage.getThepolicyNameDropdownBoxText(),GlobalConstants.isPolicyNameBoxTextDisplayed);

		policiesPage.clickOnRequestPoliciesFormCancelButton();
		assertTrue(policiesPage.isListOfPolicyRequestedDisplayed(),GlobalConstants.isListOfPolicyRequestedTextDisplayed);
	}

	@Test(priority = 7)
	public void reSubmitAlreadySubmittedRequestPolicy() {
		DashboardPage dashboardpage = new DashboardPage(driver);

		dashboardpage.clickOnProfileDropdown();
		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		loginpage.enterUserName("pmpui-auth");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();

		assertTrue(dashboardpage.isPoliciesTitleDisplayed(),GlobalConstants.isPoliciesTitleDisplayed);
		PoliciesPage policiesPage = dashboardpage.clickOnPoliciesTitle();

		assertTrue(policiesPage.isPoliciesPageDisplayed(),GlobalConstants.isPoliciesPageDisplayed);
		policiesPage.clickOnRequestPolicyButtonOfTabularPage();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(),GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.selectPartnerIdDropdown();
		policiesPage.selectPolicyNameDropdown(data);
		policiesPage.enterComments(data);
		policiesPage.clickSubmitButton();
		assertTrue(policiesPage.isPolicyAlreadyApprovedMessageDisplayed(),GlobalConstants.isPolicyAlreadyApprovedMessageDisplayed);
		policiesPage.clickOnErrorCloseButton();
		policiesPage.enterPendingPolicyNameDropdown("pending auth");
		policiesPage.clickSubmitButton();
		assertTrue(policiesPage.isPolicyPendingForApprovalMessageDisplayed(),GlobalConstants.isPolicyPendingForApprovalMessageDisplayed);
		policiesPage.clickOnErrorCloseButton();

		policiesPage.selectPartnerIdDropdown();
		policiesPage.enterAuthPolicyNameDropdown("authpolicy02");
//		dashboardpage.clickOnHamburgerOpen();
//		assertTrue(dashboardpage.isSideNavigationHomeIconDisplayed(),GlobalConstants.isSideNavigationHomeIconDisplayed);
//		dashboardpage.clickOnHamburgerClose();
		policiesPage.enterComments(data);
		policiesPage.clickSubmitButton();
		policiesPage.clickOnGoBackButton();

	}

	@Test(priority = 8)
	public void RequestNewPolicyWithouUploadingCertificates() throws InterruptedException {

		DashboardPage dashboardpage = new DashboardPage(driver);
		PoliciesPage policiesPage = new PoliciesPage(driver);
		RegisterPage registerPage = new RegisterPage(driver);

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(),GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		assertTrue(loginpage.isLoginPageDisplayed(),GlobalConstants.isLoginPageDisplayed);

		loginpage.clickRegisterButton();

		registerPage.enterFirstName("pmpui-nocert");
		assertTrue(registerPage.isLastNameTextBoxDisplayed(),GlobalConstants.isLastNameTextBoxDisplayed);
		registerPage.enterLastName("pmpui-nocert");

		assertTrue(registerPage.isOrganizationNameDisplayed(),GlobalConstants.isOrganizationNameDisplayed);
		registerPage.enterOrganizationName("AABBCC");

		assertTrue(registerPage.isPartnerTypeDropdownDisplayed(),GlobalConstants.isPartnerTypeDropdownDisplayed);
		registerPage.selectPartnerTypeDropdown(2);

		assertTrue(registerPage.isAddressTextBoxDisplayed(),GlobalConstants.isAddressTextBoxDisplayed);
		registerPage.enterAddress("0" + data);

		assertTrue(registerPage.isEmailTextBoxDisplayed(),GlobalConstants.isEmailTextBoxDisplayed);
		registerPage.enterEmail(data + "nocert" + "@gmail.com");

		assertTrue(registerPage.isPhoneNumberTextboxDisplayed(),GlobalConstants.isPhoneNumberTextboxDisplayed);
		registerPage.enterPhone("9876543310");

		assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(),GlobalConstants.isNotificationLanguageDropdownDisplayed);
		registerPage.selectNotificationLanguageDropdown();

		assertTrue(registerPage.isUsernameTextBoxDisplayed(),GlobalConstants.isUsernameTextBoxDisplayed);
		registerPage.enterUsername("pmpui-nocert");

		assertTrue(registerPage.isPasswordTextBoxDisplayed(),GlobalConstants.isPasswordTextBoxDisplayed);
		registerPage.enterPassword("mosip123");

		assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(),GlobalConstants.isPasswordConfirmTextBoxDisplayed);
		registerPage.enterPasswordConfirm("mosip123");

		assertTrue(registerPage.isSubmitButtonDisplayed(),GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage = registerPage.clickSubmitButton();

		assertTrue(dashboardpage.isSelectPolicyGroupPopUpDisplayed(),GlobalConstants.isSelectPolicyGroupPopUpDisplayed);
		dashboardpage.selectSelectPolicyGroupDropdown("automationui policy group");

		assertTrue(dashboardpage.isSubmitButtonSelectPolicyGroupPopUpDisplayed(),GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage.clickOnSubmitButton();

		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(),GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardpage.clickOnCheckbox();

		assertTrue(dashboardpage.isProceedButtonDisplayed(),GlobalConstants.isProceedButtonDisplayed);
		dashboardpage.clickOnProceedButton();

		Thread.sleep(3000);
		assertTrue(dashboardpage.isPartnerCertificateTitleDisplayed(),GlobalConstants.isPartnerCertificateTitleDisplayed);
		dashboardpage.clickOnPoliciesTitle();

		assertTrue(policiesPage.isPoliciesPageDisplayed(),GlobalConstants.isPoliciesPageDisplayed);
		policiesPage.clickOnRequestPolicyButton();

		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(),GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.clickOnPartnerIdDropdown();
		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(),GlobalConstants.isNoDataTextDisplaed);

	}

	@Test(priority = 9)
	public void CreateOidecClientWithouUploadingCertificates() {

		DashboardPage dashboardpage = new DashboardPage(driver);
		ApiKeyPage apiKeyPage = new ApiKeyPage(driver);
		BasePage basePage = new BasePage(driver);
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(),GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName("pmpui-nocert");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();

		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(),GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardpage.clickOnAuthenticationServicesTitle();

		assertTrue(oidcClientPage.isCreateOidcClientDisplayed(),GlobalConstants.isCreateOIDCClientDisplayed);
		assertTrue(oidcClientPage.isPartnerIDHeaderTextDisplayed(),GlobalConstants.isPartnerIDHeaderTextDisplayed);
		assertTrue(oidcClientPage.isPolicyGroupHeaderTextDisplayed(),GlobalConstants.isPolicyGroupHeaderTextDisplayed);
		assertTrue(oidcClientPage.isPolicyNameHeaderTextDisplayed(),GlobalConstants.isPolicyNameHeaderTextDisplayed);
		assertTrue(oidcClientPage.isOIDCClientNameHeaderTextDisplayed(),GlobalConstants.isOIDCClientNameHeaderTextDisplayed);
		assertTrue(oidcClientPage.isCreatedDateHeaderTextDisplayed(),GlobalConstants.isCreatedDateHeaderTextDisplayed);
		assertTrue(oidcClientPage.isStatusHeaderTextDisplayed(),GlobalConstants.isStatusHeaderTextDisplayed);
		assertTrue(oidcClientPage.isOIDCClientIDHeaderTextDisplayed(),GlobalConstants.isOIDCClientIDHeaderTextDisplayed);
		assertTrue(oidcClientPage.isActionHeaderTextDisplayed(),GlobalConstants.isActionHeaderTextDisplayed);
		oidcClientPage.clickOnCreateOidcClientButton();

		assertTrue(oidcClientPage.isPartnerIdDropdownDisplayed(),GlobalConstants.isPartnerIdDropdownDisplayed);
//		assertTrue(oidcClientPage.isUserIdDoesNotExistsPopupDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		oidcClientPage.clickOnPartnerIdDropdown();
		assertTrue(oidcClientPage.isNoDataAvailableTextDisplayed(),GlobalConstants.isNoDataAvailableTextDisplayed);

		String publicKey = KeycloakUserManager.readJsonData(TestRunner.getResourcePath() + "/" + "config/" + "/publicKey.json").toString();
		oidcClientPage.enterPublicKeyTextBox(publicKey);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnAddNewRedirectUrlButton();
		assertFalse(oidcClientPage.isSecondRedirectUriTextBoxDisplayed(),GlobalConstants.isSecondRedirectUriTextBoxDisplayed);
		oidcClientPage.clickOnAddNewRedirectUrlButton();
		oidcClientPage.entercreateOidcRedirectUrl2(ConfigManager.getRedirectUri() + "a");
		oidcClientPage.clickOnAddNewRedirectUrlButton();
//		assertFalse(oidcClientPage.isThirdRedirectUriTextBoxDisplayed(),GlobalConstants.isThirdRedirectUriTextBoxDisplayed);
//		oidcClientPage.clickOnAddNewRedirectUrlButton();
		oidcClientPage.entercreateOidcRedirectUrl3(ConfigManager.getRedirectUri() + "b");
		oidcClientPage.clickOnAddNewRedirectUrlButton();
//		assertFalse(oidcClientPage.isFourthRedirectUriTextBoxDisplayed(),GlobalConstants.isFourthRedirectUriTextBoxDisplayed);
//		oidcClientPage.clickOnAddNewRedirectUrlButton();
		oidcClientPage.entercreateOidcRedirectUrl4(ConfigManager.getRedirectUri() + "c");
		oidcClientPage.clickOnAddNewRedirectUrlButton();
//		assertFalse(oidcClientPage.isFifthRedirectUriTextBoxDisplayed(),GlobalConstants.isFifthRedirectUriTextBoxDisplayed);
//		oidcClientPage.clickOnAddNewRedirectUrlButton();
		oidcClientPage.entercreateOidcRedirectUrl5(ConfigManager.getRedirectUri() + "d");

		oidcClientPage.clickOnCreateOidcClearForm();

		assertFalse(oidcClientPage.isCreateOidcRedirectUrl5Displayed(),GlobalConstants.isNoDataAvailableTextDisplayed);

	}

	@Test(priority = 10)
	public void CreateApiKeyWithouUploadingCertificates() {
		DashboardPage dashboardpage = new DashboardPage(driver);
		ApiKeyPage apiKeyPage = new ApiKeyPage(driver);
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(),GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName("pmpui-nocert");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();

		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(),GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardpage.clickOnAuthenticationServicesTitle();

		oidcClientPage.clickOnApiKeyTab();

		assertTrue(apiKeyPage.isGenerateAPIKeyDisplayed(),GlobalConstants.isGenerateAPIKeyDisplayed);
		apiKeyPage.clickOnAPIKeyDisplayed();
		assertTrue(apiKeyPage.isPartnerIdDropdownDisplayed(),GlobalConstants.isPartnerIdDropdownDisplayed);
		apiKeyPage.ClickOnPartnerIdDropdown();
		assertTrue(apiKeyPage.isnoDataAvailableTextDisplayed(),GlobalConstants.isNoDataTextDisplaed);

	}

	@Test(priority = 11)
	public void deletingSecondRedirectUri() {
		DashboardPage dashboardpage = new DashboardPage(driver);
		dashboardpage.clickOnProfileDropdown();
		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		loginpage.enterUserName("pmpui-auth");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();
		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(),GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardpage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnOidcClientListPageCreateOidcClientBtn();
		oidcClientPage.clickOnRedirectUriAddNew();
		assertFalse(oidcClientPage.isRedirectUri2TextBoxDisplayed(),GlobalConstants.isRedirectUri2TextBoxDisplayed);
		oidcClientPage.clickOnRedirectUriAddNew();
		assertTrue(oidcClientPage.isRedirectUri2TextBoxDisplayed(),GlobalConstants.isRedirectUri2TextBoxDisplayed);
		oidcClientPage.clickOnRedirectUri2Delete();
		assertFalse(oidcClientPage.isRedirectUri2TextBoxDisplayed(),GlobalConstants.isRedirectUri2TextBoxDisplayed);

	}

	@Test(priority = 12)
	public void addingSecondRedirectUri() {
		DashboardPage dashboardpage = new DashboardPage(driver);
		dashboardpage.clickOnProfileDropdown();
		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		loginpage.enterUserName("pmpui-auth");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();
		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(),GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardpage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnOidcClientListPageCreateOidcClientBtn();
		oidcClientPage.clickOnRedirectUriAddNew();
		assertFalse(oidcClientPage.isRedirectUri2TextBoxDisplayed(),GlobalConstants.isRedirectUri2TextBoxDisplayed);
		oidcClientPage.clickOnRedirectUriAddNew();
		assertTrue(oidcClientPage.isRedirectUri2TextBoxDisplayed(),GlobalConstants.isRedirectUri2TextBoxDisplayed);
	}
	
	@Test(priority = 13)
	public void ClearFormOidecClient() {
		DashboardPage dashboardpage = new DashboardPage(driver);
		dashboardpage.clickOnProfileDropdown();
		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		loginpage.enterUserName("pmpui-auth");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();
		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(),GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardpage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnOidcClientListPageCreateOidcClientBtn();
		oidcClientPage.enterNameOidcTextBox(data);
		oidcClientPage.enterPublicKeyTextBox(KeycloakUserManager.publicKey);
		oidcClientPage.enterLogoUrTextBox(ConfigManager.getLogouri());
		oidcClientPage.enterRedirectUriTextBox(ConfigManager.getRedirectUri());
		oidcClientPage.clickOnClearFormButton();
		assertTrue(oidcClientPage.isLogoUriempty(),GlobalConstants.isLogoUriempty);
	}
	
	@Test (priority = 14)
	public void usingInvalidDataToCreateOIDC() {
		DashboardPage dashboardpage = new DashboardPage(driver);
		dashboardpage.clickOnProfileDropdown();
		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		loginpage.enterUserName("pmpui-auth");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();
		assertTrue(dashboardpage.isAuthenticationServicesTitleDisplayed(),GlobalConstants.isAuthenticationServicesDisplayed);
		OidcClientPage oidcClientPage = dashboardpage.clickOnAuthenticationServicesTitle();
		oidcClientPage.clickOnOidcClientListPageCreateOidcClientBtn();
		oidcClientPage.enterPublicKeyTextBox("!!!");
		assertTrue(oidcClientPage.isPublicKeyFormatErrorDisplayed(), GlobalConstants.isPublicKeyFormatErrorDisplayed);
		oidcClientPage.enterLogoUrTextBox("!!!");
		assertTrue(oidcClientPage.isInvalidLogoUriErrorDisplayed(), GlobalConstants.isInvalidLogoUriErrorDisplayed);
		oidcClientPage.enterRedirectUriTextBox("!!!");
		assertTrue(oidcClientPage.isInvalidRedirectUriErrorDisplayed(), GlobalConstants.isInvalidRedirectUriErrorDisplayed);
	}
}
