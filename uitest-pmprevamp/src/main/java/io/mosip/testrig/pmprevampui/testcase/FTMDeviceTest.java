package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
import io.mosip.testrig.pmprevampui.pages.ApiKeyPage;
import io.mosip.testrig.pmprevampui.pages.BasePage;
import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.FtmPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.OidcClientPage;
import io.mosip.testrig.pmprevampui.pages.PartnerCertificatePage;
import io.mosip.testrig.pmprevampui.pages.PoliciesPage;
import io.mosip.testrig.pmprevampui.pages.RegisterPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class FTMDeviceTest extends BaseClass {
	
	
//	@Test(priority = 1)
//	public void registerNewUserForFtmNoCert() throws InterruptedException {
//		DashboardPage dashboardpage = new DashboardPage(driver);
//		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
//		RegisterPage registerPage = new RegisterPage(driver);
//		FtmPage ftmPage = new FtmPage(driver);
//		
//		dashboardpage.clickOnProfileDropdown();
//		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);
//
//		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
//		assertTrue(loginpage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
//		
//		loginpage.clickRegisterButton();
//		
//		registerPage.enterFirstName("pmpui-ftmnocert");
//		assertTrue(registerPage.isLastNameTextBoxDisplayed(), GlobalConstants.isLastNameTextBoxDisplayed);
//		registerPage.enterLastName("pmpui-ftmnocert");
//
//		assertTrue(registerPage.isOrganizationNameDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
//		registerPage.enterOrganizationName("AABBCC");
//
//		assertTrue(registerPage.isPartnerTypeDropdownDisplayed(), GlobalConstants.isPartnerTypeDropdownDisplayed);
//		registerPage.selectPartnerTypeDropdown(1);
//
//		assertTrue(registerPage.isAddressTextBoxDisplayed(), GlobalConstants.isAddressTextBoxDisplayed);
//		registerPage.enterAddress("0"+ data);
//
//		assertTrue(registerPage.isEmailTextBoxDisplayed(), GlobalConstants.isEmailTextBoxDisplayed);
//		registerPage.enterEmail("0"+ data +"ftmnocert"+"@gmail.com");
//
//		assertTrue(registerPage.isPhoneNumberTextboxDisplayed(), GlobalConstants.isPhoneNumberTextboxDisplayed);
//		registerPage.enterPhone("9876544211");
//
//		assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(), GlobalConstants.isNotificationLanguageDropdownDisplayed);
//		registerPage.selectNotificationLanguageDropdown();
//
//		assertTrue(registerPage.isUsernameTextBoxDisplayed(), GlobalConstants.isUsernameTextBoxDisplayed);
//		registerPage.enterUsername("pmpui-ftmnocert");
//
//		assertTrue(registerPage.isPasswordTextBoxDisplayed(), GlobalConstants.isPasswordTextBoxDisplayed);
//		registerPage.enterPassword("mosip123");
//
//		assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(), GlobalConstants.isPasswordConfirmTextBoxDisplayed);
//		registerPage.enterPasswordConfirm("mosip123");
//
//		assertTrue(registerPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
//		dashboardpage = registerPage.clickSubmitButton();
//		
//		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(), GlobalConstants.isTermsAndConditionsPopUppDisplayed);
//		dashboardpage.clickOnCheckbox();
//
//		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
//		dashboardpage.clickOnProceedButton();
//		
//		dashboardpage.clickOnDashboardPartnerCertificateListHeader();
//		
//		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(), GlobalConstants.isPartnerCertificatePageDisplayed);
// 		partnerCertificatePage.clickOnUploadButton();
//
//		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(), GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
//		partnerCertificatePage.uploadCertificate();
//		partnerCertificatePage.clickOnSubmitButton();
//		assertTrue(partnerCertificatePage.isNoRootCertDisplayed(), GlobalConstants.isPartnerCertificatePageDisplayed);
//		partnerCertificatePage.clickOnErrorCloseButton();
//		partnerCertificatePage.clickOnRemoveCertificateButton();
//		
//		
//		partnerCertificatePage.uploadCertificateForAnotherOrg();
//		partnerCertificatePage.clickOnSubmitButton();
//		assertTrue(partnerCertificatePage.isErrorCodeForSelfSignedCerDisplayed(), GlobalConstants.isPartnerCertificatePageDisplayed);
//		partnerCertificatePage.clickOnErrorCloseButton();
//		partnerCertificatePage.clickOnCertificateUploadCancelButton();
//		partnerCertificatePage.clickOnHomeButton();
//	
//		assertTrue(partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed(), GlobalConstants.isProceedButtonDisplayed);
//		
//		partnerCertificatePage.clickOnFtmChipProviderCard();
//		ftmPage.clickOnAddFtmButton();
//		
//		ftmPage.clickOnAddFtmPartnerIdForNoCert();
//		assertTrue(ftmPage.isNoDataAvailableMessageDisplayed(), GlobalConstants.isNoDataTextDisplaed);
//		
//		
//		ftmPage.EnterInAddFtmMakeBox(data);
//		assertTrue(ftmPage.isAutoPopulatedMessageDisplayed(), GlobalConstants.isAutoPopulatedTextDisplaed);
//		
//		ftmPage.EnterInAddFtmModelBox(data);
//		ftmPage.clickOnAddFtmSubmitButton();
//		
//		ftmPage.clickOnAddFtmPartnerIdInfo();
//		assertTrue(ftmPage.isInfoMessageDisplayed(), GlobalConstants.isInfoMessageDisplayed);
//		
//		ftmPage.EnterInAddFtmMakeBox(" ");
//		ftmPage.EnterInAddFtmModelBox(" ");
//		ftmPage.clickOnAddFtmSubmitButton();
//		assertTrue(ftmPage.isInfoMessageDisplayed(), GlobalConstants.isInfoMessageDisplayed);
//		
//		ftmPage.EnterInAddFtmMakeBox(data);
//		ftmPage.EnterInAddFtmModelBox(data);
//		BasePage.NavigateBack();
//		
//		ftmPage.clickOnBlockMesssageProceed();
//		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(), GlobalConstants.isPartnerCertificatePageDisplayed);
//		
//		ftmPage.clickOnAddFtmButton();
//		ftmPage.EnterInAddFtmMakeBox(data);
//		ftmPage.EnterInAddFtmModelBox(data);
//		
//		ftmPage.clickOnSideNavHomeIcon();
//		ftmPage.clickOnBlockMesssageProceed();
//		
//		assertTrue(partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed(), GlobalConstants.isProceedButtonDisplayed);	
//	}

	@Test(priority = 2)
	public void registerNewUserForFtm() throws InterruptedException {
		DashboardPage dashboardpage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		RegisterPage registerPage = new RegisterPage(driver);
		FtmPage ftmPage = new FtmPage(driver);
		
		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(), GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardpage.clickOnCheckbox();

		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
		dashboardpage.clickOnProceedButton();
		
		dashboardpage.clickOnRootOFTrustCertText();
		dashboardpage.clickOnRootCertificateUploadButton();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionFtm();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.ClickonSubmitButtonForAdmin();
		partnerCertificatePage.ClickOnGoBackButton();
		dashboardpage.clickOnRootCertificateUploadButton();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionFtm();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.ClickonSubmitButtonForAdmin();
		partnerCertificatePage.ClickOnGoBackButton();
		
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		assertTrue(loginpage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		
		loginpage.clickRegisterButton();
		
		registerPage.enterFirstName("pmpui-ftm");
		assertTrue(registerPage.isLastNameTextBoxDisplayed(), GlobalConstants.isLastNameTextBoxDisplayed);
		registerPage.enterLastName("pmpui-ftm");

		assertTrue(registerPage.isOrganizationNameDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
		registerPage.enterOrganizationName("AABBCC");

		assertTrue(registerPage.isPartnerTypeDropdownDisplayed(), GlobalConstants.isPartnerTypeDropdownDisplayed);
		registerPage.selectPartnerTypeDropdown(1);

		assertTrue(registerPage.isAddressTextBoxDisplayed(), GlobalConstants.isAddressTextBoxDisplayed);
		registerPage.enterAddress("0"+ data);

		assertTrue(registerPage.isEmailTextBoxDisplayed(), GlobalConstants.isEmailTextBoxDisplayed);
		registerPage.enterEmail("0"+ data +"ftm"+"@gmail.com");

		assertTrue(registerPage.isPhoneNumberTextboxDisplayed(), GlobalConstants.isPhoneNumberTextboxDisplayed);
		registerPage.enterPhone("9876544210");

		assertTrue(registerPage.isNotificationLanguageDropdownDisplayed(), GlobalConstants.isNotificationLanguageDropdownDisplayed);
		registerPage.selectNotificationLanguageDropdown();

		assertTrue(registerPage.isUsernameTextBoxDisplayed(), GlobalConstants.isUsernameTextBoxDisplayed);
		registerPage.enterUsername("pmpui-ftm");

		assertTrue(registerPage.isPasswordTextBoxDisplayed(), GlobalConstants.isPasswordTextBoxDisplayed);
		registerPage.enterPassword("mosip123");

		assertTrue(registerPage.isPasswordConfirmTextBoxDisplayed(), GlobalConstants.isPasswordConfirmTextBoxDisplayed);
		registerPage.enterPasswordConfirm("mosip123");

		assertTrue(registerPage.isSubmitButtonDisplayed(), GlobalConstants.isSubmitButtonDisplayed);
		dashboardpage = registerPage.clickSubmitButton();
		
		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(), GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardpage.clickOnCheckbox();

		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
		dashboardpage.clickOnProceedButton();
		
		dashboardpage.clickOnDashboardPartnerCertificateListHeader();
		
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(), GlobalConstants.isPartnerCertificatePageDisplayed);
 		partnerCertificatePage.clickOnUploadButton();

		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(), GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();

		assertTrue(partnerCertificatePage.isSuccessMessageForFtmCertDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.ClickOnsuccessMsgCloseButton();
		partnerCertificatePage.certifiCateUploadCancelButton();
		dashboardpage = partnerCertificatePage.clickOnHomeButton();

		dashboardpage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();
		
		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(), GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
//		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateSubTextDisplayed(), GlobalConstants.isReUploadPartnerCertificateSubTextDisplayed);
		
		assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(), GlobalConstants.isPleaseTabTextDisplayed);
		assertTrue(partnerCertificatePage.isCertFormatesTextDisplayed(), GlobalConstants.isCertFormateDisplayed);
		assertTrue(partnerCertificatePage.isLastUploadTimeAndDateTextDisplayed(), GlobalConstants.isLastUploadTimeAndDateDisplayed);
		
		assertTrue(partnerCertificatePage.isPartnerDomainTypeDisplayed(), GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		assertTrue(partnerCertificatePage.isPartnerCertOvelayDisplayed(), GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmCertDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.clickOnRemoveCertificateButton();
		
		partnerCertificatePage.uploadCertificateInvalidCert();
		assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(), GlobalConstants.isInvalidCertFormatePopupDisplayed);
		
		partnerCertificatePage.clickOnCertificateUploadCancelButton();
	}
	
	
	@Test(priority = 3)
	public void AddFtm() throws InterruptedException {
		DashboardPage dashboardpage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		RegisterPage registerPage = new RegisterPage(driver);
		FtmPage ftmPage = new FtmPage(driver);
		
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		
		loginpage.enterUserName("pmpui-ftm");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();
		
		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		
		assertTrue(ftmPage.isPartnerIdCoulumeHeaderDisplayed(), GlobalConstants.isFtmTablHeadersDisplaed);
		assertTrue(ftmPage.isMakeCoulumeHeaderDisplayed(), GlobalConstants.isFtmTablHeadersDisplaed);
		assertTrue(ftmPage.isModelCoulumeHeaderDisplayed(), GlobalConstants.isFtmTablHeadersDisplaed);
		assertTrue(ftmPage.isCreatedDateCoulumeHeaderDisplayed(), GlobalConstants.isFtmTablHeadersDisplaed);
		assertTrue(ftmPage.iscertTimeofUploadCoulumeHeaderDisplayed(), GlobalConstants.isFtmTablHeadersDisplaed);
		assertTrue(ftmPage.isCertExpiryCoulumeHeaderDisplayed(), GlobalConstants.isFtmTablHeadersDisplaed);
		assertTrue(ftmPage.isCertExpiryStatusCoulumeHeaderDisplayed(), GlobalConstants.isFtmTablHeadersDisplaed);
		assertTrue(ftmPage.isStatusCoulumeHeaderDisplayed(), GlobalConstants.isFtmTablHeadersDisplaed);
		assertTrue(ftmPage.isActionCoulumeHeaderDisplayed(), GlobalConstants.isFtmTablHeadersDisplaed);
		
		
		ftmPage.clickOnAddFtmButton();
		
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data);
		ftmPage.EnterInAddFtmModelBox(data);
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmCertDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
		
		assertTrue(ftmPage.isListOfFtmTextDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		assertTrue(ftmPage.islFtmListAction1Displayed(), GlobalConstants.isSucessMessageDisplayed);
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListViewButton();
		
		assertTrue(ftmPage.isSubTitleHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isSubTitleFtmButtonDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelPartnerIdDisplayed(), GlobalConstants.isPartnerIdLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextPartnerIdDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelMakeDisplayed(), GlobalConstants.isFtmChipMakeLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextMakeDisplayed(), GlobalConstants.isFtmChipMakeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelPartnerTypeDisplayed(), GlobalConstants.isPartnerTypeLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextFtmChipProviderDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelModelDisplayed(), GlobalConstants.isFtmModelLableDisplayed);
		assertTrue(ftmPage.isFtmChipdetailsContextModelDisplayed(), GlobalConstants.isFtmModelValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsCertificatelabelDisplayed(), GlobalConstants.isFtmModelCertifiateLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeContextDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelUploadDateTimeDisplayed(), GlobalConstants.isTimeOfUploadLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextUploadDateTimeDisplayed(), GlobalConstants.isTimeOfUploadValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelExpiryDateTimeDisplayed(), GlobalConstants.isExpiryDateLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextExpiryDateTimeDisplayed(), GlobalConstants.isExpiryDateValueDisplayed);
		assertTrue(ftmPage.isDownloadButtonViewPageDisplayed(), GlobalConstants.isDownlaodButtonDisplayed);
		assertTrue(ftmPage.isFtmViewBackButtonDisplayed(), GlobalConstants.isBackButton);
		
		ftmPage.clickOnFtmViewBackButton();
		ftmPage.clickOnFilterButton();
		
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption1();
		
		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);
		
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		dashboardpage.clickOnLogoutButton();
		
		loginpage.enterUserName(userid);
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();
		dashboardpage.clickOnFTMChipTab();
		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFiletrButtonDisplayed);
		ftmPage.enterPartnerIdFilterBox("pmpui-ftm");
		
		ftmPage.clickOnApplyFilterButton();
		
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		
		ftmPage.clickOnApproveButton();
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		dashboardpage.clickOnLogoutButton();
		
		loginpage.enterUserName("pmpui-ftm");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();
		
		dashboardpage.clickOnDashboardFtmChipproviderCardHeader();
		assertTrue(ftmPage.isApprovedTextDisplayed(), GlobalConstants.isApproveTextDisplayed);
		ftmPage.clickOnAddFtmChipButton();
		
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data+"1");
		ftmPage.EnterInAddFtmModelBox(data+"1");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmCertDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
		
		assertTrue(ftmPage.isApprovedTextDisplayed(), GlobalConstants.isApproveTextDisplayed);
		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);
		
		ftmPage.clickOnStatusAscIcon();
		assertTrue(partnerCertificatePage.VerifyTheStatusWithAsendingOrder(), GlobalConstants.isApproveTextDisplayed);
		
		ftmPage.clickOnStatusDescIcon();
		assertTrue(partnerCertificatePage.VerifyTheStatusWithDesendingOrder(), GlobalConstants.isPendingForApprovalTextDisplayed);

		ftmPage.clickOnAddFtmChipButton();
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data+"1");
		ftmPage.EnterInAddFtmModelBox(data+"1");
		ftmPage.clickOnAddFtmSubmitButton();
		assertTrue(ftmPage.isDublicateFtmChipErrorMessageDisplayed(), GlobalConstants.isDublicateFTMErrorMessageDisplayed);
		
		assertTrue(ftmPage.isErrorCloseButtonDisplayed(), GlobalConstants.isErrorCrossButtonDisplayed);
		
		ftmPage.clickOnAddFtmClearForm();
//		assertFalse(partnerCertificatePage.isAddFtmSubmitButtonDisabled(), GlobalConstants.isElementIsDisabled);
		
	}
	
	@Test(priority = 4)
	public void AddFtmAndreject() throws InterruptedException {
		DashboardPage dashboardpage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		RegisterPage registerPage = new RegisterPage(driver);
		FtmPage ftmPage = new FtmPage(driver);
		
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		
		loginpage.enterUserName("pmpui-ftm");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();
		
		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmChipButton();
		
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data+"reject");
		ftmPage.EnterInAddFtmModelBox(data+"reject");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmCertDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
		
		assertTrue(ftmPage.isListOfFtmTextDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		assertTrue(ftmPage.islFtmListAction1Displayed(), GlobalConstants.isSucessMessageDisplayed);
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListViewButton();
		
		assertTrue(ftmPage.isSubTitleHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isSubTitleFtmButtonDisplayed(), GlobalConstants.isSubTitleDisplayed);
		ftmPage.clickOnFtmViewBackButton();
		ftmPage.clickOnFilterButton();
		
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption1();
		
		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);
		
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		dashboardpage.clickOnLogoutButton();
		
		loginpage.enterUserName(userid);
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();
		dashboardpage.clickOnFTMChipTab();
		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFiletrButtonDisplayed);
		ftmPage.enterPartnerIdFilterBox("pmpui-ftm");
		
		ftmPage.clickOnApplyFilterButton();
		
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		
		assertTrue(ftmPage.isMakAndModeTitelForPopupDisplayed(), GlobalConstants.isMakeAndModelTitleDisplayed);
		assertTrue(ftmPage.isDoYouWantToApproveAndRejecPopupHeaderDisplayed(), GlobalConstants.isApproveRejectPopupHeaderDisplayed);
		assertTrue(ftmPage.isPleaseReviewTheFtmChipPopupSubHeaderDisplayed(), GlobalConstants.isApproveRejectPopupSubHeaderDisplayed);
		
		ftmPage.clickOnRejectButton();
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		dashboardpage.clickOnLogoutButton();
		
		loginpage.enterUserName("pmpui-ftm");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();
		
		dashboardpage.clickOnDashboardFtmChipproviderCardHeader();
		assertTrue(ftmPage.isRejectedTextDisplayed(), GlobalConstants.isRejectedTextDisplayed);
		
		ftmPage.clickOnAddFtmChipButton();
		
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data+"reject");
		ftmPage.EnterInAddFtmModelBox(data+"reject");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmCertDisplayed(), GlobalConstants.isSucessMessageDisplayed);
	}

	@Test(priority = 5)
	public void AddFtmAndDeactive() throws InterruptedException {
		DashboardPage dashboardpage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		RegisterPage registerPage = new RegisterPage(driver);
		FtmPage ftmPage = new FtmPage(driver);
		
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
		
		loginpage.enterUserName("pmpui-ftm");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();
		
		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmPartnerId();
		
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data+"deactivate");
		ftmPage.EnterInAddFtmModelBox(data+"deactivate");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmCertDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
		
		assertTrue(ftmPage.isListOfFtmTextDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		assertTrue(ftmPage.islFtmListAction1Displayed(), GlobalConstants.isSucessMessageDisplayed);
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListViewButton();
		
		assertTrue(ftmPage.isSubTitleHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isSubTitleFtmButtonDisplayed(), GlobalConstants.isSubTitleDisplayed);
		ftmPage.clickOnFtmViewBackButton();
		ftmPage.clickOnFilterButton();
		
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption1();
		
		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);
		
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		dashboardpage.clickOnLogoutButton();
		
		loginpage.enterUserName(userid);
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();
		dashboardpage.clickOnFTMChipTab();
		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFiletrButtonDisplayed);
		ftmPage.enterPartnerIdFilterBox("pmpui-ftm");
		
		ftmPage.clickOnApplyFilterButton();
		
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		
		ftmPage.clickOnApproveButton();
		
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListDeactivateOption();
		assertTrue(ftmPage.isDeactivateSubmitButtonDisplayed(), GlobalConstants.isElementIsDisabled);
		ftmPage.clickOnDeactivateSubmitButton();
		
		BasePage.NavigateBack();
		BasePage.NavigateForword();
		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);
		
		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		dashboardpage.clickOnLogoutButton();
		
		loginpage.enterUserName("pmpui-ftm");
		loginpage.enterPassword(password);
		loginpage.ClickOnLoginButton();
		
		dashboardpage.clickOnDashboardFtmChipproviderCardHeader();
		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);
		
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();
		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);
		
		ftmPage.clickOnAddFtmChipButton();
	
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data+"deactivate");
		ftmPage.EnterInAddFtmModelBox(data+"deactivate");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmCertDisplayed(), GlobalConstants.isSucessMessageDisplayed);
		ftmPage.clickOnCertificateUploadCloseButton();
		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption2();
		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);
	}
	
	
	
//	@Test(priority = 6)
//	public void addFtmWithoutUploadingFtmChipCert() throws InterruptedException {
//		DashboardPage dashboardpage = new DashboardPage(driver);
//		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
//		RegisterPage registerPage = new RegisterPage(driver);
//		FtmPage ftmPage = new FtmPage(driver);
//		
//		dashboardpage.clickOnProfileDropdown();
//		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);
//
//		LoginPage loginpage = dashboardpage.clickOnLogoutButton();
//		
//		loginpage.enterUserName("pmpui-ftm");
//		loginpage.enterPassword(password);
//		loginpage.ClickOnLoginButton();
//		
//		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
//		partnerCertificatePage.clickOnFtmChipProviderCard();
//		ftmPage.clickOnAddFtmChipButton();
//		ftmPage.clickOnAddFtmPartnerId();
//		ftmPage.EnterInAddFtmMakeBox(data+"withoutcertUpload");
//		ftmPage.EnterInAddFtmModelBox(data+"withoutcertUpload");
//		ftmPage.clickOnAddFtmSubmitButton();
//		ftmPage.clickOnConfirmationCustomButton();
//		
//		ftmPage.clickOnSubTitleFtmButton();
//		assertTrue(ftmPage.isPendingForCertificateUploadTextDisplayed(), GlobalConstants.isPendingForUploadCertTextDisplayed);
//		
//		ftmPage.clickOnFtmListAction1Button();
//		ftmPage.clickOnFtmListManageCertificate();
//		assertTrue(ftmPage.isDownloadButtonViewPageDisabled(), GlobalConstants.isElementIsDisabled);
//		
//		assertTrue(ftmPage.isFtmChipDetailsCertificatelabelDisplayed(), GlobalConstants.isFtmModelCertifiateLableDisplayed);
//		assertTrue(ftmPage.isFtmChipDetailsCertificateContextDisplayed(), GlobalConstants.isFtmModelCertifiateLableDisplayed);
//		assertTrue(ftmPage.isManageFtmChipCertTextDisplayed(), GlobalConstants.isManageFtmChipCertDisplaed);
//		
//		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeContextDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
//		assertTrue(ftmPage.isFtmChipDetailsLabelUploadDateTimeDisplayed(), GlobalConstants.isTimeOfUploadLableDisplayed);
//		assertTrue(ftmPage.isFtmChipDetailsContextUploadDateTimeDisplayed(), GlobalConstants.isTimeOfUploadValueDisplayed);
//		assertTrue(ftmPage.isFtmChipDetailsLabelExpiryDateTimeDisplayed(), GlobalConstants.isExpiryDateLableDisplayed);
//		assertTrue(ftmPage.isFtmChipDetailsContextExpiryDateTimeDisplayed(), GlobalConstants.isExpiryDateValueDisplayed);
//		
//		assertTrue(ftmPage.isCertificateReuploadButtonDisplayed(), GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
//		
//		dashboardpage.clickOnProfileDropdown();
//		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);
//	    dashboardpage.clickOnLogoutButton();
//		
//		loginpage.enterUserName("pmpui-revamp");
//		loginpage.enterPassword(password);
//		loginpage.ClickOnLoginButton();
//		dashboardpage.clickOnFTMChipTab();
//		
//		ftmPage.clickOnFtmListActionMenuEllipsisButton();
//		ftmPage.clickOnFtmListApproveRejectOption();
//		
//		assertTrue(ftmPage.isFtmListApproveRejectOptionIsEnabled(), GlobalConstants.isElementIsDisabled);
//		
//	}	
	
}
