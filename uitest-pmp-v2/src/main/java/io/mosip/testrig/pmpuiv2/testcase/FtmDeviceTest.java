package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.FtmPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = {"FtmPartnerCreation"}, groups = {"FtmDeviceTest"})
public class FtmDeviceTest extends BaseClass {

	private BasePage basePage;
	private DashboardPage dashboardpage;
	private PartnerCertificatePage partnerCertificatePage;
	private FtmPage ftmPage;
	private LoginPage loginpage;

	@Test(priority = 1,description = "Add ftm chip with valid details")
	public void addFtm() throws InterruptedException {
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
		assertTrue(ftmPage.isDuplicateFtmChipErrorMessageDisplayed(),
				GlobalConstants.isDuplicateFtmErrorMessageDisplayed);

		assertTrue(ftmPage.isErrorCloseButtonDisplayed(), GlobalConstants.isErrorCrossButtonDisplayed);

		ftmPage.clickOnAddFtmClearForm();

	}

	@Test(priority = 2,description = "Try to reject added ftm chip", dependsOnMethods = "addFtm")
	public void addFtmAndreject() throws InterruptedException {
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
		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFilterButtonDisplayed);
		assertTrue(ftmPage.isListOfFtmChipDisplayed(), GlobalConstants.isListOfFtmChipTextDisplayed);

		assertTrue(ftmPage.isTitleBackIconDisplayed(), GlobalConstants.isBackArrow);
		assertTrue(ftmPage.isListofFtmChipDetailsDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(ftmPage.isFilterButtonDisplayed(), GlobalConstants.isFilterButtonDisplayedOrEnabled);
		ftmPage.enterOrgNameFilterBox("AABBCC");
		ftmPage.clickOnApplyFilterButton();
		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);
		ftmPage.ClickOnFilterResetButton();
		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFilterButtonDisplayed);
		ftmPage.enterMakeFilterBox(data + "reject");
		ftmPage.clickOnApplyFilterButton();
		assertTrue(ftmPage.isPendingForApprovalTextDisplayed(), GlobalConstants.isPendingForApprovalTextDisplayed);
		ftmPage.ClickOnFilterResetButton();
//		ftmPage.clickOnApplyFilterButton();
//		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFiletrButtonDisplayed);

		ftmPage.clickOnFilterButton();
		assertTrue(ftmPage.isFilterResetButtonDisplayed(), GlobalConstants.isResetFilterButtonDisplayed);
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

	@Test(priority = 3,description = "Try to deactivate added ftm chip", dependsOnMethods = "addFtmAndreject")
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

	@Test(priority = 4,description = "Without ftm chip certificate try to add ftm chip", dependsOnMethods = "addFtmAndDeactive")
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
