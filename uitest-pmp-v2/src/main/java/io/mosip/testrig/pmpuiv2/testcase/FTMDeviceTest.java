package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.FtmPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.RegisterPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class FTMDeviceTest extends BaseClass {

	private BasePage basePage;
	private DashboardPage dashboardpage;
	private PartnerCertificatePage partnerCertificatePage;
	private RegisterPage registerPage;
	private FtmPage ftmPage;
	private LoginPage loginpage;

	@Test(priority = 19)
	public void registerNewUserForFtmNoCert() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		registerPage = new RegisterPage(driver);
		ftmPage = new FtmPage(driver);

		dashboardpage.clickOnProfileDropdown();
		loginpage = dashboardpage.clickOnLogoutButton();
		loginpage.clickRegisterButton();
		registerPage.enterFirstName("pmpui-ftmnocert");
		registerPage.enterLastName("pmpui-ftmnocert");
		registerPage.enterOrganizationName("AABBCC");
		registerPage.selectPartnerTypeDropdown(1);
		registerPage.enterAddress("0" + data);
		registerPage.enterEmail("0" + data + "ftmnocert" + "@gmail.com");
		registerPage.enterPhone("9876544211");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername("pmpui-ftmnocert");
		registerPage.enterPassword("mosip123");
		registerPage.enterPasswordConfirm("mosip123");
		dashboardpage = registerPage.clickSubmitButton();

		dashboardpage.clickOnCheckbox();
		dashboardpage.clickOnProceedButton();

		assertTrue(partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed(),
				GlobalConstants.isProceedButtonDisplayed);

		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmButtonWioutRecord();

		ftmPage.clickOnAddFtmPartnerIdForNoCert();
		assertTrue(ftmPage.isNoDataAvailableMessageDisplayed(), GlobalConstants.isNoDataTextDisplaed);

		ftmPage.EnterInAddFtmMakeBox(data);
		assertTrue(ftmPage.isAutoPopulatedMessageDisplayed(), GlobalConstants.isAutoPopulatedTextDisplaed);

		ftmPage.EnterInAddFtmModelBox(data);
		ftmPage.clickOnAddFtmSubmitButton();

		ftmPage.clickOnAddFtmPartnerIdInfo();
		assertTrue(ftmPage.isInfoMessageDisplayed(), GlobalConstants.isInfoMessageDisplayed);

		ftmPage.EnterInAddFtmMakeBox(" ");
		ftmPage.EnterInAddFtmModelBox(" ");
		ftmPage.clickOnAddFtmSubmitButton();
		assertTrue(ftmPage.isInfoMessageDisplayed(), GlobalConstants.isInfoMessageDisplayed);

		ftmPage.EnterInAddFtmMakeBox(data);
		ftmPage.EnterInAddFtmModelBox(data);
		basePage.navigateBack();

		ftmPage.clickOnBlockMesssageProceed();
		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		ftmPage.clickOnAddFtmButtonWioutRecord();
		ftmPage.EnterInAddFtmMakeBox(data);
		ftmPage.EnterInAddFtmModelBox(data);

		ftmPage.clickOnSideNavHomeIcon();
		ftmPage.clickOnBlockMesssageProceed();

		assertTrue(partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed(),
				GlobalConstants.isProceedButtonDisplayed);
	}

	@Test(priority = 20)
	public void registerNewUserForFtm() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		registerPage = new RegisterPage(driver);

//		assertTrue(dashboardpage.isTermsAndConditionsPopUppDisplayed(), GlobalConstants.isTermsAndConditionsPopUppDisplayed);
//		dashboardpage.clickOnCheckbox();
//
//		assertTrue(dashboardpage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
//		dashboardpage.clickOnProceedButton();

		dashboardpage.clickOnRootOFTrustCertText();
		assertTrue(partnerCertificatePage.isUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionFtm();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		partnerCertificatePage.clickOnIntermediateCACertTab();
		assertTrue(partnerCertificatePage.isIntermediateUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionFtm();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();

		dashboardpage.clickOnProfileDropdown();
		loginpage = dashboardpage.clickOnLogoutButton();
		loginpage.clickRegisterButton();
		registerPage.enterFirstName(GlobalConstants.FTM_PARTNER_ID);
		registerPage.enterLastName(GlobalConstants.FTM_PARTNER_ID);
		registerPage.enterOrganizationName(GlobalConstants.Organisation_Name);
		registerPage.selectPartnerTypeDropdown(1);
		registerPage.enterAddress("0" + data);
		registerPage.enterEmail("0" + data + "ftm" + "@gmail.com");
		registerPage.enterPhone("9876544210");
		registerPage.selectNotificationLanguageDropdown();
		registerPage.enterUsername(GlobalConstants.FTM_PARTNER_ID);
		registerPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		registerPage.enterPasswordConfirm(GlobalConstants.PARTNER_PASSWORD);
		dashboardpage = registerPage.clickSubmitButton();

		assertTrue(dashboardpage.isTermsAndConditionsPopupDisplayed(),
				GlobalConstants.isTermsAndConditionsPopUppDisplayed);
		dashboardpage.clickOnCheckbox();
		dashboardpage.clickOnProceedButton();

		dashboardpage.clickOnDashboardPartnerCertificateListHeader();

		assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.clickOnUploadButton();

		assertTrue(partnerCertificatePage.isUploadPartnerCertificatePopUpDisplayed(),
				GlobalConstants.isUploadPartnerCertificatePopUpDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();

		assertTrue(partnerCertificatePage.isSuccessMessageForFtmCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOnSuccessMsgCloseButton();
		partnerCertificatePage.certifiCateUploadCancelButton();
		dashboardpage = partnerCertificatePage.clickOnHomeButton();

		dashboardpage.clickOnPartnerCertificateTitle();
		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();

		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateTextDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
//		assertTrue(partnerCertificatePage.isReUploadPartnerCertificateSubTextDisplayed(), GlobalConstants.isReUploadPartnerCertificateSubTextDisplayed);

		assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(), GlobalConstants.isPleaseTabTextDisplayed);
		assertTrue(partnerCertificatePage.isCertFormatesTextDisplayed(), GlobalConstants.isCertFormateDisplayed);
		assertTrue(partnerCertificatePage.isLastUploadTimeAndDateTextDisplayed(),
				GlobalConstants.isLastUploadTimeAndDateDisplayed);

		assertTrue(partnerCertificatePage.isPartnerDomainTypeLabelDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		assertTrue(partnerCertificatePage.isPartnerCertOvelayDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);

		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnRemoveCertificateButton();

		partnerCertificatePage.uploadCertificateInvalidCert();
		assertTrue(partnerCertificatePage.isInvalidFormatErrorPopupDisplayed(),
				GlobalConstants.isInvalidCertFormatePopupDisplayed);

		partnerCertificatePage.clickOnCertificateUploadCancelButton();

		partnerCertificatePage.clickOnPartnerCertificateReuploadButton();
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
	}

	@Test(priority = 21)
	public void AddFtm() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		ftmPage = new FtmPage(driver);

		loginAsFtmPartner();

		assertTrue(partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed(),
				GlobalConstants.isDashboardFtmChipProviderCardDisplayed);
		partnerCertificatePage.clickOnFtmChipProviderCard();

		assertTrue(ftmPage.isPartnerIdCoulumeHeaderDisplayed(), GlobalConstants.isPartnerIdHeaderDisplayed);
		assertTrue(ftmPage.isMakeCoulumeHeaderDisplayed(), GlobalConstants.isMakeCoulumeHeaderDisplayed);
		assertTrue(ftmPage.isModelCoulumeHeaderDisplayed(), GlobalConstants.isModelCoulumeHeaderDisplayed);
		assertTrue(ftmPage.isCreatedDateCoulumeHeaderDisplayed(), GlobalConstants.isCreatedDateHeaderDisplayed);
		assertTrue(ftmPage.iscertTimeofUploadCoulumeHeaderDisplayed(),
				GlobalConstants.isCreatedDateCoulumeHeaderDisplayed);
		assertTrue(ftmPage.isCertExpiryCoulumeHeaderDisplayed(), GlobalConstants.isCertExpiryHeaderDisplayed);
		assertTrue(ftmPage.isCertExpiryStatusCoulumeHeaderDisplayed(),
				GlobalConstants.isCertExpiryStatusHeaderDisplayed);
		assertTrue(ftmPage.isStatusCoulumeHeaderDisplayed(), GlobalConstants.isStatusHeaderDisplayed);
		assertTrue(ftmPage.isActionCoulumeHeaderDisplayed(), GlobalConstants.isActionHeaderDisplayed);
		ftmPage.clickOnAddFtmButtonWioutRecord();

		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data);
		ftmPage.EnterInAddFtmModelBox(data);
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		assertTrue(ftmPage.isListOfFtmTextDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		assertTrue(ftmPage.islFtmListAction1Displayed(), GlobalConstants.isSuccessMessageDisplayed);
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListViewButton();

		assertTrue(ftmPage.isSubTitleHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isSubTitleFtmButtonDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelPartnerIdDisplayed(), GlobalConstants.isPartnerIdLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextPartnerIdDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelMakeDisplayed(), GlobalConstants.isFtmChipMakeLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextMakeDisplayed(), GlobalConstants.isFtmChipMakeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelPartnerTypeDisplayed(), GlobalConstants.isPartnerTypeLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextFtmChipProviderDisplayed(),
				GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelModelDisplayed(), GlobalConstants.isFtmModelLableDisplayed);
		assertTrue(ftmPage.isFtmChipdetailsContextModelDisplayed(), GlobalConstants.isFtmModelValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsCertificatelabelDisplayed(),
				GlobalConstants.isFtmModelCertifiateLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeContextDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelUploadDateTimeDisplayed(),
				GlobalConstants.isTimeOfUploadLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextUploadDateTimeDisplayed(),
				GlobalConstants.isTimeOfUploadValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelExpiryDateTimeDisplayed(), GlobalConstants.isExpiryDateLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextExpiryDateTimeDisplayed(),
				GlobalConstants.isExpiryDateValueDisplayed);
		assertTrue(ftmPage.isDownloadButtonViewPageDisplayed(), GlobalConstants.isDownlaodButtonDisplayed);
		assertTrue(ftmPage.isFtmViewBackButtonDisplayed(), GlobalConstants.isBackButton);

		ftmPage.clickOnFtmViewBackButton();
		ftmPage.clickOnFilterButton();

		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterPendingForApproval();

		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();
		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isListOfFtmChipDisplayed(), GlobalConstants.isListOfFtmChipTextDisplayed);

		assertTrue(ftmPage.isTitleBackIconDisplayed(), GlobalConstants.isBackArrow);
		assertTrue(ftmPage.isListofFtmChipDetailsDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isFilterButtonDisplayed(), GlobalConstants.isFiletrButtonDisplayedOrEnabled);

		assertTrue(ftmPage.isPartneridFilterDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		assertTrue(ftmPage.isOrgNameFilterDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
//		assertTrue(ftmPage.isFtmIdFilterDisplayed(), GlobalConstants.isFtmTextBoxDisplayed);
		assertTrue(ftmPage.isMakeFilterDisplayed(), GlobalConstants.isMakeTextBoxDisplayed);
		assertTrue(ftmPage.isModelFilterDisplayed(), GlobalConstants.isModelTextBoxDisplayed);
		assertTrue(ftmPage.isStatusFilterDisplayed(), GlobalConstants.isStatusDisplayed);

		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFiletrButtonDisplayed);
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.SelectValueFromStatusFilter();

		ftmPage.clickOnApplyFilterButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();

		ftmPage.clickOnApproveButton();
		ftmPage.clickOnApprovedButton();

		assertTrue(ftmPage.isSubTitleHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isSubTitleFtmButtonDisplayed(), GlobalConstants.isSubTitleDisplayed);
//		assertTrue(ftmPage.isFtmChipDetailsLabelPartnerIdDisplayed(), GlobalConstants.isPartnerIdLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextPartnerIdDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelMakeDisplayed(), GlobalConstants.isFtmChipMakeLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextMakeDisplayed(), GlobalConstants.isFtmChipMakeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelPartnerTypeDisplayed(), GlobalConstants.isPartnerTypeLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextPartnerTypeDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
//		assertTrue(ftmPage.isFtmChipDetailsLabelModelDisplayed(), GlobalConstants.isFtmModelLableDisplayed);
//		assertTrue(ftmPage.isFtmChipdetailsContextModelDisplayed(), GlobalConstants.isFtmModelValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsCertificatelabelDisplayed(),
				GlobalConstants.isFtmModelCertifiateLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeContextDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelUploadDateTimeDisplayed(),
				GlobalConstants.isTimeOfUploadLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextUploadDateTimeDisplayed(),
				GlobalConstants.isTimeOfUploadValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelExpiryDateTimeDisplayed(), GlobalConstants.isExpiryDateLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextExpiryDateTimeDisplayed(),
				GlobalConstants.isExpiryDateValueDisplayed);
		assertTrue(ftmPage.isDownloadButtonViewPageDisplayed(), GlobalConstants.isDownlaodButtonDisplayed);
		assertTrue(ftmPage.isFtmViewBackButtonDisplayed(), GlobalConstants.isBackButton);

		loginAsFtmPartner();

		dashboardpage.clickOnDashboardFtmChipproviderCardHeader();
		assertTrue(ftmPage.isApprovedTextDisplayed(), GlobalConstants.isApproveTextDisplayed);
		ftmPage.clickOnAddFtmChipButton();

		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data + "1");
		ftmPage.EnterInAddFtmModelBox(data + "1");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageForFtmChipCertDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		assertTrue(ftmPage.isApprovedTextDisplayed(), GlobalConstants.isApproveTextDisplayed);
		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		ftmPage.clickOnStatusAscIcon();
		assertTrue(partnerCertificatePage.VerifyTheStatusWithAsendingOrder(), GlobalConstants.isApproveTextDisplayed);

		ftmPage.clickOnStatusDescIcon();
		assertTrue(partnerCertificatePage.VerifyTheStatusWithDesendingOrder(),
				GlobalConstants.isPendingForApprovalTextDisplayed);

		ftmPage.clickOnAddFtmChipButton();
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data + "1");
		ftmPage.EnterInAddFtmModelBox(data + "1");
		ftmPage.clickOnAddFtmSubmitButton();
		assertTrue(ftmPage.isDublicateFtmChipErrorMessageDisplayed(),
				GlobalConstants.isDublicateFTMErrorMessageDisplayed);

		assertTrue(ftmPage.isErrorCloseButtonDisplayed(), GlobalConstants.isErrorCrossButtonDisplayed);

		ftmPage.clickOnAddFtmClearForm();

	}

	@Test(priority = 22)
	public void AddFtmAndreject() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		ftmPage = new FtmPage(driver);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmChipButton();

		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data + "reject");
		ftmPage.EnterInAddFtmModelBox(data + "reject");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		assertTrue(ftmPage.isListOfFtmTextDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		assertTrue(ftmPage.islFtmListAction1Displayed(), GlobalConstants.isSuccessMessageDisplayed);
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListViewButton();

		assertTrue(ftmPage.isSubTitleHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isSubTitleFtmButtonDisplayed(), GlobalConstants.isSubTitleDisplayed);
		ftmPage.clickOnFtmViewBackButton();
		ftmPage.clickOnFilterButton();

		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterPendingForApproval();

		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();
		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFiletrButtonDisplayed);
		assertTrue(ftmPage.isListOfFtmChipDisplayed(), GlobalConstants.isListOfFtmChipTextDisplayed);

		assertTrue(ftmPage.isTitleBackIconDisplayed(), GlobalConstants.isBackArrow);
		assertTrue(ftmPage.isListofFtmChipDetailsDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isFilterButtonDisplayed(), GlobalConstants.isFiletrButtonDisplayedOrEnabled);
		ftmPage.enterOrgNameFilterBox("AABBCC");
		ftmPage.clickOnApplyFilterButton();
		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);
		ftmPage.ClickOnFilterResetButton();
		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFiletrButtonDisplayed);
		ftmPage.enterMakeFilterBox(data + "reject");
		ftmPage.clickOnApplyFilterButton();
		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);
		ftmPage.ClickOnFilterResetButton();
//		ftmPage.clickOnApplyFilterButton();
//		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFiletrButtonDisplayed);

		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFiletrButtonDisplayed);
		ftmPage.enterModelFilterBox(data + "reject");

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();

		assertTrue(ftmPage.isMakAndModeTitelForPopupDisplayed(), GlobalConstants.isMakeAndModelTitleDisplayed);
		assertTrue(ftmPage.isDoYouWantToApproveAndRejecPopupHeaderDisplayed(),
				GlobalConstants.isApproveRejectPopupHeaderDisplayed);
		assertTrue(ftmPage.isPleaseReviewTheFtmChipPopupSubHeaderDisplayed(),
				GlobalConstants.isApproveRejectPopupSubHeaderDisplayed);

		ftmPage.clickOnRejectButton();

		loginAsFtmPartner();

		dashboardpage.clickOnDashboardFtmChipproviderCardHeader();
		assertTrue(ftmPage.isRejectedTextDisplayed(), GlobalConstants.isRejectedTextDisplayed);

		ftmPage.clickOnAddFtmChipButton();

		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data + "reject");
		ftmPage.EnterInAddFtmModelBox(data + "reject");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
	}

	@Test(priority = 23)
	public void AddFtmAndDeactive() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		ftmPage = new FtmPage(driver);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmChipButton();
		ftmPage.clickOnAddFtmPartnerId();

		ftmPage.EnterInAddFtmMakeBox(data + "deactivate");
		ftmPage.EnterInAddFtmModelBox(data + "deactivate");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		assertTrue(ftmPage.isListOfFtmTextDisplayed(), GlobalConstants.isSuccessMessageDisplayed);
		assertTrue(ftmPage.islFtmListAction1Displayed(), GlobalConstants.isSuccessMessageDisplayed);
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListViewButton();

		assertTrue(ftmPage.isSubTitleHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isSubTitleFtmButtonDisplayed(), GlobalConstants.isSubTitleDisplayed);
		ftmPage.clickOnFtmViewBackButton();
		ftmPage.clickOnFilterButton();

		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterPendingForApproval();

		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();
		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFiletrButtonDisplayed);
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);

		ftmPage.clickOnApplyFilterButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();

		ftmPage.clickOnApproveButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListDeactivateOption();
		assertTrue(ftmPage.isDeactivateSubmitButtonDisplayed(), GlobalConstants.isElementIsDisabled);
		ftmPage.clickOnDeactivateSubmitButton();

		ftmPage.clickOnStatusFilter();
		ftmPage.clickOnFtmStatusFilterDeactivated();
		ftmPage.clickOnApplyFilterButton();
		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);

		basePage.navigateBack();
		basePage.navigateForword();
		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);

		loginAsFtmPartner();

		dashboardpage.clickOnDashboardFtmChipproviderCardHeader();
		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);

		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();
		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);

		ftmPage.clickOnAddFtmChipButton();

		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data + "deactivate");
		ftmPage.EnterInAddFtmModelBox(data + "deactivate");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageForFtmChipCertDisplayed);
		ftmPage.clickOnCertificateUploadCloseButton();
		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterDeactivated();
		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);
	}

	@Test(priority = 24)
	public void addFtmWithoutUploadingFtmChipCert() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		ftmPage = new FtmPage(driver);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmChipButton();
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data + "withoutcertUpload");
		ftmPage.EnterInAddFtmModelBox(data + "withoutcertUpload");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();

		ftmPage.clickOnSubTitleFtmButton();
		assertTrue(ftmPage.isPendingForCertificateUploadTextDisplayed(),
				GlobalConstants.isPendingForUploadCertTextDisplayed);

		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();
//		assertTrue(ftmPage.isDownloadButtonViewPageDisabled(), GlobalConstants.isElementIsDisabled);

		assertTrue(ftmPage.isFtmChipDetailsCertificatelabelDisplayed(),
				GlobalConstants.isFtmModelCertifiateLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsCertificateContextDisplayed(),
				GlobalConstants.isFtmModelCertifiateLableDisplayed);
		assertTrue(ftmPage.isManageFtmChipCertTextDisplayed(), GlobalConstants.isManageFtmChipCertDisplaed);

		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeContextDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelUploadDateTimeDisplayed(),
				GlobalConstants.isTimeOfUploadLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextUploadDateTimeDisplayed(),
				GlobalConstants.isTimeOfUploadValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelExpiryDateTimeDisplayed(), GlobalConstants.isExpiryDateLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextExpiryDateTimeDisplayed(),
				GlobalConstants.isExpiryDateValueDisplayed);

		assertTrue(ftmPage.isCertificateReuploadButtonDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();

		assertTrue(ftmPage.isFtmListApproveRejectOptionIsEnabled(), GlobalConstants.isElementIsDisabled);

	}

	private void loginAsFtmPartner() {
		dashboardpage.clickOnProfileDropdown();
		loginpage = dashboardpage.clickOnLogoutButton();
		loginpage.enterUserName(GlobalConstants.FTM_PARTNER_ID);
		loginpage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginpage.clickOnLoginButton();
	}

	private void loginAsPartnerAdmin() {
		dashboardpage.clickOnProfileDropdown();
		loginpage = dashboardpage.clickOnLogoutButton();
		loginpage.enterUserName(GlobalConstants.PARTNER_ADMIN);
		loginpage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginpage.clickOnLoginButton();
	}
}
