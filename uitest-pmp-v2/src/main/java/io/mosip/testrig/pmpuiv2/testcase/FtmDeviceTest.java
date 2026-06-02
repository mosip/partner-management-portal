package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.FtmPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "FtmPartnerCreation" }, groups = { "FtmDeviceTest" })
public class FtmDeviceTest extends BaseClass {

	private BasePage basePage;
	private DashboardPage dashboardpage;
	private PartnerCertificatePage partnerCertificatePage;
	private FtmPage ftmPage;
	private LoginPage loginpage;

	@Test(priority = 1, description = "Add ftm chip with valid details")
	public void addFtm() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		ftmPage = new FtmPage(driver);

		loginAsFtmPartner();

		assertTrue(partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed(),
				GlobalConstants.isDashboardFtmChipProviderCardDisplayed);
		partnerCertificatePage.clickOnFtmChipProviderCard();

		assertTrue(ftmPage.isFtmIdDisplayedAsFirstColumn(), GlobalConstants.isFtmIdDisplayedAsFirstColumn);
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
		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOFTM);
		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOFTM);
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
		assertTrue(ftmPage.isFtmChipDetailsLabelMakeDisplayed(), GlobalConstants.isFtmChipMakeLabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextMakeDisplayed(), GlobalConstants.isFtmChipMakeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelPartnerTypeDisplayed(), GlobalConstants.isPartnerTypeLabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextFtmChipProviderDisplayed(),
				GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelModelDisplayed(), GlobalConstants.isFtmModelLabelDisplayed);
		assertTrue(ftmPage.isFtmChipdetailsContextModelDisplayed(), GlobalConstants.isFtmModelValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsCertificatelabelDisplayed(),
				GlobalConstants.isFtmChipDetailsCertificatelabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeContextDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelUploadDateTimeDisplayed(),
				GlobalConstants.isTimeOfUploadLabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextUploadDateTimeDisplayed(),
				GlobalConstants.isTimeOfUploadValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelExpiryDateTimeDisplayed(), GlobalConstants.isExpiryDateLabelDisplayed);
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

		assertTrue(ftmPage.isFtmIdDisplayedInThirdColumnOnPartnerAdminPage(),
				GlobalConstants.isFtmIdDisplayedInThirdColumnOnPartnerAdminPage);

		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isListOfFtmChipDisplayed(), GlobalConstants.isListOfFtmChipTextDisplayed);

		assertTrue(ftmPage.isTitleBackIconDisplayed(), GlobalConstants.isBackArrow);
		assertTrue(ftmPage.isListofFtmChipDetailsDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isFilterButtonDisplayed(), GlobalConstants.isFilterButtonDisplayedOrEnabled);

		assertTrue(ftmPage.isPartneridFilterDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		assertTrue(ftmPage.isOrgNameFilterDisplayed(), GlobalConstants.isOrganizationNameDisplayed);
//		assertTrue(ftmPage.isFtmIdFilterDisplayed(), GlobalConstants.isFtmTextBoxDisplayed);
		assertTrue(ftmPage.isMakeFilterDisplayed(), GlobalConstants.isMakeTextBoxDisplayed);
		assertTrue(ftmPage.isModelFilterDisplayed(), GlobalConstants.isModelTextBoxDisplayed);
		assertTrue(ftmPage.isStatusFilterDisplayed(), GlobalConstants.isStatusDisplayed);

		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFilterButtonDisplayed);
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
		assertTrue(ftmPage.isFtmChipDetailsLabelMakeDisplayed(), GlobalConstants.isFtmChipMakeLabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextMakeDisplayed(), GlobalConstants.isFtmChipMakeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelPartnerTypeDisplayed(), GlobalConstants.isPartnerTypeLabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextPartnerTypeDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
//		assertTrue(ftmPage.isFtmChipDetailsLabelModelDisplayed(), GlobalConstants.isFtmModelLableDisplayed);
//		assertTrue(ftmPage.isFtmChipdetailsContextModelDisplayed(), GlobalConstants.isFtmModelValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsCertificatelabelDisplayed(),
				GlobalConstants.isFtmChipDetailsCertificatelabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeLabelDisplayed(), GlobalConstants.isPartnerTypeLabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeContextDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelUploadDateTimeDisplayed(),
				GlobalConstants.isTimeOfUploadLabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextUploadDateTimeDisplayed(),
				GlobalConstants.isTimeOfUploadValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelExpiryDateTimeDisplayed(), GlobalConstants.isExpiryDateLabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextExpiryDateTimeDisplayed(),
				GlobalConstants.isExpiryDateValueDisplayed);
		assertTrue(ftmPage.isDownloadButtonViewPageDisplayed(), GlobalConstants.isDownlaodButtonDisplayed);
		assertTrue(ftmPage.isFtmViewBackButtonDisplayed(), GlobalConstants.isBackButton);

		loginAsFtmPartner();

		dashboardpage.clickOnDashboardFtmChipproviderCardHeader();
		assertTrue(ftmPage.isApprovedTextDisplayed(), GlobalConstants.isApproveTextDisplayed);
		ftmPage.clickOnAddFtmChipButton();

		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOFTM1);
		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOFTM1);
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
		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOFTM1);
		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOFTM1);
		ftmPage.clickOnAddFtmSubmitButton();
		assertTrue(ftmPage.isDuplicateFtmChipErrorMessageDisplayed(),
				GlobalConstants.isDuplicateFtmErrorMessageDisplayed);

		assertTrue(ftmPage.isErrorCloseButtonDisplayed(), GlobalConstants.isErrorCrossButtonDisplayed);

		ftmPage.clickOnAddFtmClearForm();

	}

	@Test(priority = 2, description = "Try to reject added ftm chip", dependsOnMethods = "addFtm")
	public void addFtmAndreject() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		ftmPage = new FtmPage(driver);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmChipButton();

		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOFTMREJECT);
		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOFTMREJECT);
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
		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFilterButtonDisplayed);
		assertTrue(ftmPage.isListOfFtmChipDisplayed(), GlobalConstants.isListOfFtmChipTextDisplayed);

		assertTrue(ftmPage.isTitleBackIconDisplayed(), GlobalConstants.isBackArrow);
		assertTrue(ftmPage.isListofFtmChipDetailsDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isFilterButtonDisplayed(), GlobalConstants.isFilterButtonDisplayedOrEnabled);
		ftmPage.enterOrgNameFilterBox(GlobalConstants.ORGANISATION_NAME);
		ftmPage.clickOnApplyFilterButton();
		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);
		ftmPage.ClickOnFilterResetButton();
		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFilterButtonDisplayed);
		ftmPage.enterMakeFilterBox(GlobalConstants.AUTOFTMREJECT);
		ftmPage.clickOnApplyFilterButton();
		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);
		ftmPage.ClickOnFilterResetButton();
//		ftmPage.clickOnApplyFilterButton();
//		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFiletrButtonDisplayed);

		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFilterButtonDisplayed);
		ftmPage.enterModelFilterBox(GlobalConstants.AUTOFTMREJECT);

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();

		assertTrue(ftmPage.isApproveRejectPopupDisplayed(), GlobalConstants.isApproveRejectPopupDisplayed);
		assertTrue(ftmPage.isApproveRejectPopupHeaderDisplayed(), GlobalConstants.isApproveRejectPopupHeaderDisplayed);
		assertTrue(ftmPage.isApproveRejectPopupSubHeaderDisplayed(),
				GlobalConstants.isApproveRejectPopupSubHeaderDisplayed);

		ftmPage.clickOnRejectButton();

		loginAsFtmPartner();

		dashboardpage.clickOnDashboardFtmChipproviderCardHeader();
		assertTrue(ftmPage.isRejectedTextDisplayed(), GlobalConstants.isRejectedTextDisplayed);

		ftmPage.clickOnAddFtmChipButton();

		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOFTMREJECT);
		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOFTMREJECT);
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
	}

	@Test(priority = 3, description = "Try to deactivate added ftm chip", dependsOnMethods = "addFtmAndreject")
	public void addFtmAndDeactive() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		ftmPage = new FtmPage(driver);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmChipButton();
		ftmPage.clickOnAddFtmPartnerId();

		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOFTMDEACTIVATE);
		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOFTMDEACTIVATE);
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
		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFilterButtonDisplayed);
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
		basePage.navigateForward();
		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);

		loginAsFtmPartner();

		dashboardpage.clickOnDashboardFtmChipproviderCardHeader();
		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);

		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();
		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);

		ftmPage.clickOnAddFtmChipButton();

		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOFTMDEACTIVATE);
		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOFTMDEACTIVATE);
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

	@Test(priority = 4, description = "Without ftm chip certificate try to add ftm chip", dependsOnMethods = "addFtmAndDeactive")
	public void addFtmWithoutUploadingFtmChipCert() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		ftmPage = new FtmPage(driver);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmChipButton();
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOFTMWITHOUTCERT);
		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOFTMWITHOUTCERT);
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();

		ftmPage.clickOnSubTitleFtmButton();
		assertTrue(ftmPage.isPendingForCertificateUploadTextDisplayed(),
				GlobalConstants.isPendingForUploadCertTextDisplayed);

		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();
//		assertTrue(ftmPage.isDownloadButtonViewPageDisabled(), GlobalConstants.isElementIsDisabled);

		assertTrue(ftmPage.isFtmChipDetailsCertificatelabelDisplayed(),
				GlobalConstants.isFtmChipDetailsCertificatelabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsCertificateContextDisplayed(),
				GlobalConstants.isFtmChipDetailsCertificateContextDisplayed);
		assertTrue(ftmPage.isManageFtmChipCertTextDisplayed(), GlobalConstants.isManageFtmChipCertDisplaed);

		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeContextDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelUploadDateTimeDisplayed(),
				GlobalConstants.isTimeOfUploadLabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextUploadDateTimeDisplayed(),
				GlobalConstants.isTimeOfUploadValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelExpiryDateTimeDisplayed(), GlobalConstants.isExpiryDateLabelDisplayed);
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

	@Test(priority = 5, description = "ftm chip tabular view", dependsOnMethods = "addFtmWithoutUploadingFtmChipCert")
	public void ftmChipTabularView() throws InterruptedException {

		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		ftmPage = new FtmPage(driver);
		basePage = new BasePage(driver);

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		loginpage = dashboardpage.clickOnLogoutButton();
		assertTrue(loginpage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);

		loginpage.enterUserName(GlobalConstants.FTM_PARTNER_ID);
		loginpage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginpage.clickOnLoginButton();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();

		ftmPage.clickOnAddFtmChipButton();

		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOFTM + "make1");
		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOFTM + "model1");
		ftmPage.clickOnAddFtmSubmitButton();

		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmPartnerIdFilterDropdownButton();
		ftmPage.clickOnFtmPartnerIdFilterOption1();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption3();

		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		loginpage = dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName(GlobalConstants.PARTNER_ADMIN);
		loginpage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginpage.clickOnLoginButton();

		dashboardpage.clickOnFTMChipTab();

		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isListOfFtmChipDisplayed(), GlobalConstants.isListOfFtmChipTextDisplayed);

		assertTrue(ftmPage.isPartneridFilterDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);

		assertTrue(ftmPage.isStatusFilterDisplayed(), GlobalConstants.isStatusDisplayed);
		ftmPage.SelectValueFromStatusFilter();

		ftmPage.clickOnApplyFilterButton();
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		ftmPage.clickOnApproveButton();

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		loginpage = dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName(GlobalConstants.FTM_PARTNER_ID);
		loginpage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginpage.clickOnLoginButton();

		dashboardpage.clickOnDashboardFtmChipproviderCardHeader();
		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption1();
		assertTrue(ftmPage.isApprovedTextDisplayed(), GlobalConstants.isApproveTextDisplayed);

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		loginpage = dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName(GlobalConstants.PARTNER_ADMIN);
		loginpage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginpage.clickOnLoginButton();

		dashboardpage.clickOnFTMChipTab();

		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isListOfFtmChipDisplayed(), GlobalConstants.isListOfFtmChipTextDisplayed);

		assertTrue(ftmPage.isPartneridFilterDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox("MAK");
		ftmPage.enterModelFilterBox("mo");

		ftmPage.clickOnApplyFilterButton();

		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);

		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isFilterResetButtonDisplayed);
		ftmPage.ClickOnFilterResetButton();

		assertTrue(ftmPage.isListOfFtmChipDisplayed(), GlobalConstants.isListOfFtmChipTextDisplayed);
		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isPartneridFilterDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox("Ftm");
		ftmPage.enterModelFilterBox("MyFtm");
		ftmPage.clickOnApplyFilterButton();

		assertTrue(ftmPage.isNoResultsFoundDisplayed(), GlobalConstants.isNoResultsFoundDisplayed);

		ftmPage.ClickOnFilterResetButton();

		ftmPage.clickOnFilterButton();

		assertTrue(ftmPage.isPartneridFilterDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);

		ftmPage.clickOnApplyFilterButton();
		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		dashboardpage.clickOnLogoutButton();
		assertTrue(loginpage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);

		loginpage.enterUserName(GlobalConstants.FTM_PARTNER_ID);
		loginpage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginpage.clickOnLoginButton();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();

		ftmPage.clickOnAddFtmChipButton();

		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOFTM + "make2");
		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOFTM + "model2");
		ftmPage.clickOnAddFtmSubmitButton();

		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		dashboardpage.clickOnProfileDropdown();
		dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName(GlobalConstants.PARTNER_ADMIN);
		loginpage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginpage.clickOnLoginButton();

		dashboardpage.clickOnFTMChipTab();

		ftmPage.clickOnFilterButton();
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(GlobalConstants.AUTOFTM + "make2");
		ftmPage.enterModelFilterBox(GlobalConstants.AUTOFTM + "model2");
		ftmPage.SelectValueFromStatusFilter();

		ftmPage.clickOnApplyFilterButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		assertTrue(ftmPage.isFtmListApproveRejectOptionIsEnabled(),
				GlobalConstants.isFtmListApproveRejectOptionIsEnabled);
		assertTrue(ftmPage.isFtmListViewIsEnabled(), GlobalConstants.isFtmListViewIsEnabled);
		assertTrue(ftmPage.isFtmListDeactivateOptionIsDisabled(), GlobalConstants.isElementIsDisabled);

		ftmPage.clickOnFtmListApproveRejectOption();
		assertTrue(ftmPage.isApproveRejectPopupDisplayed(), GlobalConstants.isApproveRejectPopupDisplayed);
		assertTrue(ftmPage.isApproveRejectPopupHeaderDisplayed(), GlobalConstants.isApproveRejectPopupHeaderDisplayed);
		assertTrue(ftmPage.isApproveRejectPopupSubHeaderDisplayed(),
				GlobalConstants.isApproveRejectPopupSubHeaderDisplayed);
		ftmPage.clickOnRejectButton();
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		assertTrue(ftmPage.isFtmListApproveRejectOptionIsDisabled(),
				GlobalConstants.isFtmListApproveRejectOptionIsDisabled);
		assertTrue(ftmPage.isFtmListViewIsEnabled(), GlobalConstants.isFtmListViewIsEnabled);
		assertTrue(ftmPage.isFtmListDeactivateOptionIsDisabled(), GlobalConstants.isFtmListDeactivateOptionIsDisabled);

		ftmPage.ClickOnFilterResetButton();
		ftmPage.clickOnFilterButton();
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(GlobalConstants.AUTOFTM + "make1");
		ftmPage.enterModelFilterBox(GlobalConstants.AUTOFTM + "model1");

		ftmPage.clickOnApplyFilterButton();

		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListDeactivateOption();
		assertTrue(ftmPage.isDeactivateSubmitButtonDisplayed(), GlobalConstants.isElementIsDisabled);
		ftmPage.clickOnDeactivateSubmitButton();
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		assertTrue(ftmPage.isFtmListApproveRejectOptionIsDisabled(), GlobalConstants.isElementIsDisabled);
		assertTrue(ftmPage.isFtmListViewIsEnabled(), GlobalConstants.isFtmListViewIsEnabled);
		assertTrue(ftmPage.isFtmListDeactivateOptionIsDisabled(), GlobalConstants.isElementIsDisabled);

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		dashboardpage.clickOnLogoutButton();
		assertTrue(loginpage.isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);

		loginpage.enterUserName(GlobalConstants.FTM_PARTNER_ID);
		loginpage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginpage.clickOnLoginButton();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();

		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmPartnerIdFilterDropdownButton();
		ftmPage.clickOnFtmPartnerIdFilterOption1();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption2();
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();
		assertFalse(ftmPage.isCertificateReuploadButtonDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);

		ftmPage.clickOnTitleBackIcon();
		partnerCertificatePage.clickOnFtmChipProviderCard();

		ftmPage.clickOnAddFtmChipButton();

		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(GlobalConstants.AUTOFTM + "Make3");
		ftmPage.EnterInAddFtmModelBox(GlobalConstants.AUTOFTM + "Model3");
		ftmPage.clickOnAddFtmSubmitButton();

		dashboardpage.clickOnProfileDropdown();
		dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName(GlobalConstants.PARTNER_ADMIN);
		loginpage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginpage.clickOnLoginButton();

		dashboardpage.clickOnFTMChipTab();

		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);
		assertTrue(ftmPage.isPendingForCertificateUploadTextDisplayed(),
				GlobalConstants.isPendingForUploadCertTextDisplayed);
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		assertFalse(ftmPage.isApproveRejectPopupHeaderDisplayed(), GlobalConstants.isApproveRejectPopupHeaderDisplayed);

		dashboardpage.clickOnProfileDropdown();
		dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName(GlobalConstants.FTM_PARTNER_ID);
		loginpage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginpage.clickOnLoginButton();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmPartnerIdFilterDropdownButton();
		ftmPage.clickOnFtmPartnerIdFilterOption1();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption4();

		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();

		assertTrue(ftmPage.isCertificateReuploadButtonDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		ftmPage.clickOnCertificateUploadButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmPartnerIdFilterDropdownButton();
		ftmPage.clickOnFtmPartnerIdFilterOption1();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption3();
		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);
		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		dashboardpage.clickOnProfileDropdown();
		assertTrue(dashboardpage.isLogoutButtonDisplayed(), GlobalConstants.isLogoutButtonDisplayed);

		loginpage = dashboardpage.clickOnLogoutButton();

		loginpage.enterUserName(GlobalConstants.PARTNER_ADMIN);
		loginpage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginpage.clickOnLoginButton();
		dashboardpage.clickOnFTMChipTab();

		ftmPage.clickOnFilterButton();

		assertTrue(ftmPage.isPartneridFilterDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(GlobalConstants.AUTOFTM + "Make3");
		ftmPage.enterModelFilterBox(GlobalConstants.AUTOFTM + "Model3");
		ftmPage.SelectValueFromStatusFilter();

		ftmPage.clickOnApplyFilterButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		ftmPage.clickOnApproveButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListDeactivateOption();
		assertTrue(ftmPage.isDeactivateSubmitButtonDisplayed(), GlobalConstants.isElementIsDisabled);
		ftmPage.clickOnDeactivateSubmitButton();

		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		assertTrue(ftmPage.isFtmListViewIsEnabled(), GlobalConstants.isFtmListViewIsEnabled);
		assertTrue(ftmPage.isFtmListDeactivateOptionIsDisabled(), GlobalConstants.isElementIsDisabled);

		basePage.navigateBack();

		dashboardpage.clickOnFTMChipTab();
		ftmPage.clickOnFilterButton();
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(GlobalConstants.AUTOFTM + "Make3");
		ftmPage.enterModelFilterBox(GlobalConstants.AUTOFTM + "Model3");
		ftmPage.clickOnApplyFilterButton();
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		assertTrue(ftmPage.isFtmListViewIsEnabled(), GlobalConstants.isFtmListViewIsEnabled);
		ftmPage.clickOnViewButton();
		assertTrue(ftmPage.isSubTitleHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isSubTitleFtmButtonDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelPartnerIdDisplayed(), GlobalConstants.isPartnerIdLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextPartnerIdDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelMakeDisplayed(), GlobalConstants.isFtmChipDetailsLabelMakeDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextMakeDisplayed(), GlobalConstants.isFtmChipMakeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelPartnerTypeDisplayed(),
				GlobalConstants.isFtmChipDetailsLabelPartnerTypeDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextPartnerTypeDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelModelDisplayed(), GlobalConstants.isFtmChipDetailsLabelModelDisplayed);
		assertTrue(ftmPage.isFtmChipdetailsContextModelDisplayed(), GlobalConstants.isFtmModelValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsCertificatelabelDisplayed(),
				GlobalConstants.isFtmChipDetailsCertificatelabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeLabelDisplayed(),
				GlobalConstants.isFtmChipDetailsPartnerTypeLabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeContextDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(ftmPage.isFtmViewBackButtonDisplayed(), GlobalConstants.isBackButton);

		assertTrue(ftmPage.isTitleBackIconDisplayed(), GlobalConstants.isBackArrow);
		ftmPage.clickOnTitleBackIcon();
		assertTrue(ftmPage.isListOfFtmChipDisplayed(), GlobalConstants.isListOfFtmChipDisplayed);

		ftmPage.clickOnFilterButton();
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.clickOnApplyFilterButton();
		ftmPage.clickOnModelDescIcon();
		ftmPage.clickOnModelAscIcon();

	}

	@Test(priority = 6, description = "approve reject ftm chip", dependsOnMethods = "ftmChipTabularView")
	public void ApproveRejectFTMchip() throws InterruptedException {

		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		ftmPage = new FtmPage(driver);
		basePage = new BasePage(driver);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();

		ftmPage.clickOnAddFtmChipButton();

		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data + "approve");
		ftmPage.EnterInAddFtmModelBox(data + "approve");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmPartnerIdFilterDropdownButton();
		ftmPage.clickOnFtmPartnerIdFilterOption1();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption3();

		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();

		ftmPage.clickOnFilterButton();
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "approve");
		ftmPage.enterModelFilterBox(data + "approve");
		ftmPage.clickOnApplyFilterButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		assertTrue(ftmPage.isApproveRejectPopupDisplayed(), GlobalConstants.isApproveRejectPopupDisplayed);
		assertTrue(ftmPage.isApproveRejectCloseButtonDisplayed(), GlobalConstants.isApproveRejectCloseButtonDisplayed);
		assertTrue(ftmPage.isApproveRejectPopupHeaderDisplayed(), GlobalConstants.isApproveRejectPopupHeaderDisplayed);
		assertTrue(ftmPage.isApproveRejectPopupSubHeaderDisplayed(),
				GlobalConstants.isApproveRejectPopupSubHeaderDisplayed);
		assertTrue(ftmPage.isRejectButtonDisplayed(), GlobalConstants.isRejectButtonDisplayed);
		assertTrue(ftmPage.isApproveButtonDisplayed(), GlobalConstants.isApproveButtonDisplayed);

		ftmPage.clickOnApproveButton();
		assertTrue(ftmPage.isApprovedTextDisplayed(), GlobalConstants.isApproveTextDisplayed);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();

		ftmPage.clickOnAddFtmChipButton();

		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data + "reject01");
		ftmPage.EnterInAddFtmModelBox(data + "reject01");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmPartnerIdFilterDropdownButton();
		ftmPage.clickOnFtmPartnerIdFilterOption1();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption3();
		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);
		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();

		ftmPage.clickOnFilterButton();

		assertTrue(ftmPage.isPartneridFilterDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "reject01");
		ftmPage.enterModelFilterBox(data + "reject01");

		ftmPage.clickOnApplyFilterButton();
		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();

		assertTrue(ftmPage.isApproveRejectCloseButtonDisplayed(), GlobalConstants.isApproveRejectCloseButtonDisplayed);
		ftmPage.clickOnApproveRejectCloseButton();

		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		assertTrue(ftmPage.isRejectButtonDisplayed(), GlobalConstants.isRejectButtonDisplayed);
		ftmPage.clickOnRejectButton();
		assertTrue(ftmPage.isRejectedTextDisplayed(), GlobalConstants.isRejectedTextDisplayed);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();

		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmPartnerIdFilterDropdownButton();
		ftmPage.clickOnFtmPartnerIdFilterOption1();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption5();
		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);
		assertTrue(ftmPage.isRejectedTextDisplayed(), GlobalConstants.isRejectedTextDisplayed);
	}

	@Test(priority = 7, description = "ftm chip certificate section", dependsOnMethods = "ApproveRejectFTMchip")
	public void FtmChipCertificateSection() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		ftmPage = new FtmPage(driver);
		basePage = new BasePage(driver);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmChipButton();
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data + "approveOrdeactivateStatus");
		ftmPage.EnterInAddFtmModelBox(data + "approveOrdeactivateStatus");
		ftmPage.clickOnAddFtmSubmitButton();

		ftmPage.clickOnTitleBackIcon();

		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);
		assertTrue(ftmPage.isPendingForCertificateUploadTextDisplayed(),
				GlobalConstants.isPendingForUploadCertTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();

		ftmPage.clickOnFilterButton();

		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "approveOrdeactivateStatus");
		ftmPage.enterModelFilterBox(data + "approveOrdeactivateStatus");

		ftmPage.clickOnApplyFilterButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnViewButton();

		assertTrue(ftmPage.isFtmChipDetailsCertificatelabelDisplayed(),
				GlobalConstants.isFtmChipDetailsCertificatelabelDisplayed);

		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeLabelDisplayed(),
				GlobalConstants.isFtmChipDetailsPartnerTypeLabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeContextDisplayed(),
				GlobalConstants.isFtmModelCertifiateLableDisplayed);

		assertTrue(ftmPage.isFtmChipDetailsLabelUploadDateTimeDisplayed(),
				GlobalConstants.isFtmChipDetailsLabelUploadDateTimeDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextUploadDateTimeDisplayed(),
				GlobalConstants.isTimeOfUploadValueDisplayed);

		assertTrue(ftmPage.isFtmChipDetailsLabelExpiryDateTimeDisplayed(),
				GlobalConstants.isFtmChipDetailsLabelExpiryDateTimeDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextExpiryDateTimeDisplayed(),
				GlobalConstants.isExpiryDateValueDisplayed);

		assertTrue(ftmPage.isDownloadButtonViewPageDisabled(), GlobalConstants.isElementIsDisabled);

		ftmPage.clickOnTitleBackIcon();

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();

		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmPartnerIdFilterDropdownButton();
		ftmPage.clickOnFtmPartnerIdFilterOption1();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption4();

		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();
		ftmPage.clickOnCertificateUploadButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();

		ftmPage.clickOnFilterButton();

		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "approveOrdeactivateStatus");
		ftmPage.enterModelFilterBox(data + "approveOrdeactivateStatus");

		ftmPage.clickOnApplyFilterButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnViewButton();
		assertTrue(ftmPage.isDownloadButtonViewPageDisplayed(), GlobalConstants.isDownlaodButtonDisplayed);
		assertTrue(ftmPage.isDownloadButtonViewPageEnabled(), GlobalConstants.isDownloadButtonViewPageEnabled);

		ftmPage.clickOnFtmViewBackButton();
		ftmPage.clickOnFilterButton();
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "approveOrdeactivateStatus");
		ftmPage.enterModelFilterBox(data + "approveOrdeactivateStatus");

		ftmPage.clickOnApplyFilterButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		ftmPage.clickOnApproveButton();
		assertTrue(ftmPage.isApprovedTextDisplayed(), GlobalConstants.isApproveTextDisplayed);

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnViewButton();
		assertTrue(ftmPage.isDownloadButtonViewPageDisplayed(), GlobalConstants.isDownlaodButtonDisplayed);
		assertTrue(ftmPage.isDownloadButtonViewPageEnabled(), GlobalConstants.isDownloadButtonViewPageEnabled);

		ftmPage.clickOnFtmViewBackButton();
		ftmPage.clickOnFilterButton();
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "approveOrdeactivateStatus");
		ftmPage.enterModelFilterBox(data + "approveOrdeactivateStatus");

		ftmPage.clickOnApplyFilterButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListDeactivateOption();
		assertTrue(ftmPage.isDeactivateSubmitButtonDisplayed(), GlobalConstants.isElementIsDisabled);
		ftmPage.clickOnDeactivateSubmitButton();
		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnViewButton();
		assertTrue(ftmPage.isDownloadButtonViewPageDisabled(), GlobalConstants.isElementIsDisabled);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmChipButton();
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data + "rejectStatus");
		ftmPage.EnterInAddFtmModelBox(data + "rejectStatus");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();

		ftmPage.clickOnFilterButton();
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "rejectStatus");
		ftmPage.enterModelFilterBox(data + "rejectStatus");
		ftmPage.clickOnApplyFilterButton();
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		ftmPage.clickOnRejectButton();
		assertTrue(ftmPage.isRejectedTextDisplayed(), GlobalConstants.isRejectedTextDisplayed);

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnViewButton();
		assertTrue(ftmPage.isDownloadButtonViewPageDisplayed(), GlobalConstants.isDownlaodButtonDisplayed);
		assertTrue(ftmPage.isDownloadButtonViewPageDisabled(), GlobalConstants.isElementIsDisabled);
	}

	@Test(priority = 8, description = "Deactivate ftm chip details", dependsOnMethods = "FtmChipCertificateSection")
	public void DeactivateFtmChipDetails() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		ftmPage = new FtmPage(driver);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmChipButton();
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data + "deactivate01");
		ftmPage.EnterInAddFtmModelBox(data + "deactivate01");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnTitleBackIcon();

		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);
		assertTrue(ftmPage.isPendingForCertificateUploadTextDisplayed(),
				GlobalConstants.isPendingForUploadCertTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();

		ftmPage.clickOnFilterButton();

		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "deactivate01");
		ftmPage.enterModelFilterBox(data + "deactivate01");
		ftmPage.clickOnApplyFilterButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		assertTrue(ftmPage.isFtmListDeactivateOptionIsDisabled(), GlobalConstants.isElementIsDisabled);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();

		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmPartnerIdFilterDropdownButton();
		ftmPage.clickOnFtmPartnerIdFilterOption1();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption4();

		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();
		ftmPage.clickOnCertificateUploadButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();

		ftmPage.clickOnFilterButton();

		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "deactivate01");
		ftmPage.enterModelFilterBox(data + "deactivate01");

		ftmPage.clickOnApplyFilterButton();
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		assertTrue(ftmPage.isFtmListDeactivateOptionIsDisabled(), GlobalConstants.isElementIsDisabled);

		assertTrue(ftmPage.isFtmListApproveRejectOptionIsEnabled(),
				GlobalConstants.isFtmListApproveRejectOptionIsEnabled);
		ftmPage.clickOnFtmListApproveRejectOption();
		ftmPage.clickOnApproveButton();
		assertTrue(ftmPage.isApprovedTextDisplayed(), GlobalConstants.isApproveTextDisplayed);

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		assertTrue(ftmPage.isFtmListDeactivateOptionIsEnabled(), GlobalConstants.isFtmListDeactivateOptionIsEnabled);

		ftmPage.clickOnFtmListDeactivateOption();
		assertTrue(ftmPage.isDoYouWantToDeactivateFtmChipPopupTitleDisplayed(),
				GlobalConstants.isDeactivateFtmPopupTitleDisplayed);
		assertTrue(ftmPage.isonClickingConfirmYourFtmChipDetailsWillBeDeactivatedSubTitleDisplayed(),
				GlobalConstants.isDeactivateFtmPopupSubTitleDisplayed);
		assertTrue(ftmPage.isDeactivateCancelButtonDisplayed(), GlobalConstants.isElementIsDisabled);
		assertTrue(ftmPage.isDeactivateSubmitButtonDisplayed(), GlobalConstants.isElementIsDisabled);

		ftmPage.clickOnDeactivateCancelButton();
		assertTrue(ftmPage.isListofFtmChipDetailsDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListDeactivateOption();
		ftmPage.clickOnDeactivateSubmitButton();
		assertTrue(ftmPage.isDeactivatedTextDisplayed(), GlobalConstants.isDeactivatedTextDisplayed);
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		assertTrue(ftmPage.isFtmListViewIsEnabled(), GlobalConstants.isFtmListViewIsEnabled);
		assertTrue(ftmPage.isFtmListDeactivateOptionIsDisabled(), GlobalConstants.isFtmListDeactivateOptionIsDisabled);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmChipButton();
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data + "deactivate02");
		ftmPage.EnterInAddFtmModelBox(data + "deactivate02");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnConfirmationCustomButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();

		ftmPage.clickOnFilterButton();
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "deactivate02");
		ftmPage.enterModelFilterBox(data + "deactivate02");
		ftmPage.clickOnApplyFilterButton();
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		ftmPage.clickOnRejectButton();
		assertTrue(ftmPage.isRejectedTextDisplayed(), GlobalConstants.isRejectedTextDisplayed);

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		assertTrue(ftmPage.isListofFtmChipDetailsDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isFtmListDeactivateOptionIsDisabled(), GlobalConstants.isElementIsDisabled);

	}

	@Test(priority = 9, description = "View ftm chip details", dependsOnMethods = "DeactivateFtmChipDetails")
	public void viewFtmChipDetails() throws InterruptedException {

		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		ftmPage = new FtmPage(driver);
		basePage = new BasePage(driver);

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmChipButton();
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data + "DownloadCert");
		ftmPage.EnterInAddFtmModelBox(data + "DownloadCert");
		ftmPage.clickOnAddFtmSubmitButton();
		ftmPage.clickOnTitleBackIcon();
		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);
		assertTrue(ftmPage.isPendingForCertificateUploadTextDisplayed(),
				GlobalConstants.isPendingForUploadCertTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();

		ftmPage.clickOnFtmListItem1();
		assertTrue(ftmPage.isViewFtmChipDetailsTitleDisplayed(), GlobalConstants.isViewFtmChipDetailsTitleDisplayed);
		ftmPage.clickOnFtmViewBackButton();

		ftmPage.clickOnFilterButton();
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "DownloadCert");
		ftmPage.enterModelFilterBox(data + "DownloadCert");
		ftmPage.clickOnApplyFilterButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnViewButton();
		assertTrue(ftmPage.isViewFtmChipDetailsTitleDisplayed(), GlobalConstants.isViewFtmChipDetailsTitleDisplayed);
		assertTrue(ftmPage.isSubTitleHomeButtonDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isSubTitleFtmButtonDisplayed(), GlobalConstants.isSubTitleDisplayed);

		assertTrue(ftmPage.isDownloadButtonViewPageDisplayed(), GlobalConstants.isDownlaodButtonDisplayed);
		assertTrue(ftmPage.isDownloadButtonViewPageDisabled(), GlobalConstants.isElementIsDisabled);
		ftmPage.clickOnFtmViewBackButton();

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();

		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmPartnerIdFilterDropdownButton();
		ftmPage.clickOnFtmPartnerIdFilterOption1();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption4();

		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();
		ftmPage.clickOnCertificateUploadButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();
		ftmPage.clickOnFilterButton();

		assertTrue(ftmPage.isPartneridFilterDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "DownloadCert");
		ftmPage.enterModelFilterBox(data + "DownloadCert");

		ftmPage.clickOnApplyFilterButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnViewButton();
		assertTrue(ftmPage.isDownloadButtonViewPageDisplayed(), GlobalConstants.isDownlaodButtonDisplayed);
		assertTrue(ftmPage.isDownloadButtonViewPageEnabled(), GlobalConstants.isDownloadButtonViewPageEnabled);
		ftmPage.clickOnFtmViewBackButton();

		ftmPage.clickOnSubTitleHomeButton();

		assertTrue(ftmPage.isFtmDetailsSideNavIconDisplayed(), GlobalConstants.isFtmDetailsSideNavIconDisplayed);
		ftmPage.clickOnFtmDetailsSideNavIcon();
		assertTrue(ftmPage.isListOfFtmChipDisplayed(), GlobalConstants.isListOfFtmChipTextDisplayed);

		ftmPage.clickOnFilterButton();

		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "DownloadCert");
		ftmPage.enterModelFilterBox(data + "DownloadCert");

		ftmPage.clickOnApplyFilterButton();
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnViewButton();

		assertTrue(ftmPage.isPendingForApprovalStatusDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);
		assertTrue(ftmPage.isCreatedDateDisplayed(), GlobalConstants.isCreatedDateDisplayed);
		assertTrue(ftmPage.isCreatedOnLabelDisplayed(), GlobalConstants.isCreatedOnLabelDisplayed);

		assertTrue(ftmPage.isFtmChipDetailsCertificatelabelDisplayed(),
				GlobalConstants.isFtmModelCertifiateLableDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeLabelDisplayed(),
				GlobalConstants.isFtmChipDetailsPartnerTypeLabelDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsPartnerTypeContextDisplayed(), GlobalConstants.isPartnerTypeValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelUploadDateTimeDisplayed(),
				GlobalConstants.isFtmChipDetailsLabelUploadDateTimeDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextUploadDateTimeDisplayed(),
				GlobalConstants.isTimeOfUploadValueDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsLabelExpiryDateTimeDisplayed(),
				GlobalConstants.isFtmChipDetailsLabelExpiryDateTimeDisplayed);
		assertTrue(ftmPage.isFtmChipDetailsContextExpiryDateTimeDisplayed(),
				GlobalConstants.isExpiryDateValueDisplayed);
		assertTrue(ftmPage.isDownloadButtonViewPageDisplayed(), GlobalConstants.isDownlaodButtonDisplayed);
		assertTrue(ftmPage.isFtmViewBackButtonDisplayed(), GlobalConstants.isBackButton);

		ftmPage.clickOnTitleBackIcon();
		ftmPage.clickOnFilterButton();

		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "DownloadCert");
		ftmPage.enterModelFilterBox(data + "DownloadCert");

		ftmPage.clickOnApplyFilterButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnViewButton();
		assertTrue(ftmPage.isDownloadButtonViewPageDisplayed(), GlobalConstants.isDownlaodButtonDisplayed);
		assertTrue(ftmPage.isDownloadButtonViewPageEnabled(), GlobalConstants.isDownloadButtonViewPageEnabled);
		ftmPage.clickOnDownloadButton();
		assertTrue(ftmPage.isDownloadSuccessMessageDisplayed(), GlobalConstants.isDownloadSuccessMessageDisplayed);
		assertTrue(ftmPage.isDownloadButtonViewPageEnabled(), GlobalConstants.isDownloadButtonViewPageEnabled);
		ftmPage.clickOnDownloadButton();
		assertTrue(ftmPage.isDownloadSuccessMessageDisplayed(), GlobalConstants.isDownloadSuccessMessageDisplayed);
		assertTrue(ftmPage.isDownloadButtonViewPageEnabled(), GlobalConstants.isDownloadButtonViewPageEnabled);
		ftmPage.clickOnDownloadButton();
		assertTrue(ftmPage.isDownloadSuccessMessageDisplayed(), GlobalConstants.isDownloadSuccessMessageDisplayed);

		ftmPage.clickOnFtmViewBackButton();
		assertTrue(ftmPage.isListofFtmChipDetailsDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnViewButton();

		basePage.navigateBack();
		assertTrue(ftmPage.isListofFtmChipDetailsDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnViewButton();

		ftmPage.clickOnTitleBackIcon();
		assertTrue(ftmPage.isListofFtmChipDetailsDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
	}

	@Test(priority = 10, description = "Reload ftm chip certificate", dependsOnMethods = "viewFtmChipDetails")
	public void reUploadFtmChipCertificate() throws InterruptedException {
		dashboardpage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		ftmPage = new FtmPage(driver);

		loginAsFtmPartner();
		
		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnAddFtmChipButton();
		ftmPage.clickOnAddFtmPartnerId();
		ftmPage.EnterInAddFtmMakeBox(data + "reuploadCert");
		ftmPage.EnterInAddFtmModelBox(data + "reuploadCert");
		ftmPage.clickOnAddFtmSubmitButton();

		ftmPage.clickOnSubTitleFtmButton();
		assertTrue(ftmPage.isFtmList1Displayed(), GlobalConstants.isFtmList1Displayed);
		assertTrue(ftmPage.isPendingForCertificateUploadTextDisplayed(),
				GlobalConstants.isPendingForUploadCertTextDisplayed);

		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();
		ftmPage.clickOnCertificateUploadButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();
		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();
		ftmPage.clickOnFilterButton();

		assertTrue(ftmPage.isPartneridFilterDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "reuploadCert");
		ftmPage.enterModelFilterBox(data + "reuploadCert");
		ftmPage.clickOnApplyFilterButton();

		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		ftmPage.clickOnApproveButton();

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmPartnerIdFilterDropdownButton();
		ftmPage.clickOnFtmPartnerIdFilterOption1();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption1();
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();

		assertTrue(ftmPage.isCertificateReuploadButtonDisplayed(),
				GlobalConstants.iReUploadPartnerCertificateTextDisplayed);
		ftmPage.clickOnCertificateUploadButton();

		assertTrue(ftmPage.isReUploadPopUpPageTitleDisplayed(), GlobalConstants.isReUploadPopUpPageTitleDisplayed);
		assertTrue(ftmPage.isReUploadPopUpPagePartnerTypeNameDisplayed(),
				GlobalConstants.isReUploadPopUpPagePartnerTypeNameDisplayed);
		assertTrue(ftmPage.isReUploadPopUpPagePartnerDomainDisplayed(),
				GlobalConstants.isReUploadPopUpPagePartnerDomainDisplayed);
		assertTrue(ftmPage.isReUploadPopUpPageUploadIconDisplayed(),
				GlobalConstants.isReUploadPopUpPageUploadIconDisplayed);
		assertTrue(ftmPage.isReUploadPopUpPageCloseButtonDisplayed(),
				GlobalConstants.isReUploadPopUpPageCloseButtonDisplayed);
		assertTrue(ftmPage.isReUploadPopUpPageSubmitButtonDisplayed(),
				GlobalConstants.isReUploadPopUpPageSubmitButtonDisplayed);
		assertTrue(ftmPage.isReUploadPopUpPagePartnerTypeNameDisabled(),
				GlobalConstants.isReUploadPopUpPagePartnerTypeNameDisabled);
		assertTrue(ftmPage.isReUploadPopUpPagePartnerDomainDisabled(),
				GlobalConstants.isReUploadPopUpPagePartnerDomainDisabled);
		assertTrue(ftmPage.isReUploadPopUpPageSubmitButtonDisabled(),
				GlobalConstants.isReUploadPopUpPageSubmitButtonDisabled);

		ftmPage.uploadCertificateConfrmationForFtm();
		assertTrue(ftmPage.isReUploadPopUpPageFetchingCertMsgDisplayed(),
				GlobalConstants.isReUploadPopUpPageFetchingCertMsgDisplayed);
		assertTrue(ftmPage.isReUploadCertNameDisplayed(), GlobalConstants.isReUploadCertNameDisplayed);
		assertTrue(ftmPage.isReUploadCertRemoveBtnDisplayed(), GlobalConstants.isReUploadCertRemoveBtnDisplayed);

		assertTrue(ftmPage.isReUploadPopUpPageSubmitButtonEnabled(),
				GlobalConstants.isReUploadPopUpPageSubmitButtonEnabled);
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();
		ftmPage.clickOnFilterButton();

		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "reuploadCert");
		ftmPage.enterModelFilterBox(data + "reuploadCert");
		ftmPage.clickOnApplyFilterButton();
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		ftmPage.clickOnApproveButton();

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmPartnerIdFilterDropdownButton();
		ftmPage.clickOnFtmPartnerIdFilterOption1();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption1();
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();
		ftmPage.clickOnCertificateUploadButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		loginAsPartnerAdmin();

		dashboardpage.clickOnFTMChipTab();
		ftmPage.clickOnFilterButton();

		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "reuploadCert");
		ftmPage.enterModelFilterBox(data + "reuploadCert");
		ftmPage.clickOnApplyFilterButton();
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		ftmPage.clickOnApproveButton();

		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmPartnerIdFilterDropdownButton();
		ftmPage.clickOnFtmPartnerIdFilterOption1();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption1();
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();
		ftmPage.clickOnCertificateUploadButton();
		ftmPage.uploadCertificateConfrmationForFtm();
		ftmPage.clickOnCertificateUploadSubmitButton();
		assertTrue(partnerCertificatePage.isSuccessMessageForFtmChipCertDisplayed(),
				GlobalConstants.isSuccessMessageDisplayed);
		partnerCertificatePage.clickOncertificateUploadCloseButton();

		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);

		loginAsPartnerAdmin();
		
		dashboardpage.clickOnFTMChipTab();
		ftmPage.clickOnFilterButton();

		ftmPage.enterPartnerIdFilterBox(GlobalConstants.FTM_PARTNER_ID);
		ftmPage.enterMakeFilterBox(data + "reuploadCert");
		ftmPage.enterModelFilterBox(data + "reuploadCert");
		ftmPage.clickOnApplyFilterButton();
		ftmPage.clickOnFtmListActionMenuEllipsisButton();
		ftmPage.clickOnFtmListApproveRejectOption();
		ftmPage.clickOnApproveButton();
		
		loginAsFtmPartner();

		partnerCertificatePage.isDashboardFtmChipProviderCardDisplayed();
		partnerCertificatePage.clickOnFtmChipProviderCard();
		ftmPage.clickOnFilterButton();
		ftmPage.clickOnFtmPartnerIdFilterDropdownButton();
		ftmPage.clickOnFtmPartnerIdFilterOption1();
		ftmPage.clickOnFtmStatusFilter();
		ftmPage.clickOnFtmStatusFilterOption1();
		ftmPage.clickOnFtmListAction1Button();
		ftmPage.clickOnFtmListManageCertificate();
		assertTrue(ftmPage.isFtmChipDetailsContextUploadDateTimeUpdated(),
				GlobalConstants.isFtmChipDetailsContextUploadDateTimeUpdated);

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
