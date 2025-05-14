package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmprevampui.utility.TestRunner;

public class FtmPage extends BasePage {

	@FindBy(xpath = "//*[contains(text(), 'successfully')]")
	private WebElement successMessageForFtmCert;
	
	@FindBy(id = "partnerDomain_selector_dropdown_option1")
	private WebElement partnerDomainSelectorDropdownOptionFtm;
	
	@FindBy(id = "dashboard_ftm_chip_provider_card")
	private WebElement dashboardFtmChipProviderCard;
	
	@FindBy(id = "side_nav_ftmchip_provider_service_icon")
	private WebElement sideNavFtmchipProviderServiceIcon;
	
	@FindBy(id = "add_ftm")
	private WebElement addFtmButton;
	
	@FindBy(xpath = "//*[contains(text(), 'Partner ID')]")
	private WebElement partnerIdCoulumeHeader;
	
	@FindBy(xpath = "//*[contains(text(), 'Make')]")
	private WebElement makeCoulumeHeader;
	
	@FindBy(xpath = "//*[contains(text(), 'Model')]")
	private WebElement modelCoulumeHeader;
	
	@FindBy(xpath = "//*[contains(text(), 'Creation Date')]")
	private WebElement createdDateCoulumeHeader;
	
	@FindBy(xpath = "//*[contains(text(), 'Cert Time of Upload')]")
	private WebElement certTimeofUploadCoulumeHeader;
	
	@FindBy(xpath = "//*[contains(text(), 'Cert Expiry Date & Time')]")
	private WebElement certExpiryCoulumeHeader;
	
	@FindBy(xpath = "//*[contains(text(), 'Cert Expiry Status')]")
	private WebElement certExpiryStatusCoulumeHeader;
	
	@FindBy(xpath = "//*[contains(text(), 'Status')]")
	private WebElement statusCoulumeHeader;
	
	@FindBy(xpath = "//*[contains(text(), 'Action')]")
	private WebElement actionCoulumeHeader;
	
	@FindBy(xpath = "//*[contains(text(), 'FTM ID')]")
	private WebElement ftmIdCoulumeHeader;
	
	@FindBy(id = "add_ftm_partner_id")
	private WebElement addFtmPartnerId;
	
	@FindBy(id = "add_ftm_partner_id_option1")
	private WebElement addFtmPartnerIdOption1;

	@FindBy(id = "add_ftm_make")
	private WebElement addFtmMake;
	
	@FindBy(id = "add_ftm_model")
	private WebElement addFtmModel;
	
	@FindBy(id = "add_ftm_submit_btn")
	private WebElement addFtmSubmitButton;
	
	@FindBy(id = "confirmation_custom_btn")
	private WebElement confirmationCustomButton;
	
	@FindBy(id = "certificate_upload_submit_btn")
	private WebElement certificateUploadSubmitButton;
		
	@FindBy(id = "list_of_ftm")
	private WebElement listOfFtm;
	
	@FindBy(id = "ftm_list_action1")
	private WebElement ftmListAction1;
		
	@FindBy(id = "ftm_list_view")
	private WebElement ftmListView;
	
	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;
	
	@FindBy(id = "sub_title_home_btn")
	private WebElement subTitleHomeButton;
	
	@FindBy(id = "sub_title_btn")
	private WebElement subTitleFtmButton;
	
	@FindBy(id = "ftm_chip_details_label_partner_id")
	private WebElement ftmChipDetailsLabelPartnerId;
	
	@FindBy(id = "ftm_chip_details_context_partner_id")
	private WebElement ftmChipDetailsContextPartnerId;
	
	@FindBy(id = "ftm_chip_details_label_make")
	private WebElement ftmChipDetailsLabelMake;
	
	@FindBy(id = "ftm_chip_details_context_make")
	private WebElement ftmChipDetailsContextMake;
	
	@FindBy(id = "ftm_chip_details_label_partner_type")
	private WebElement ftmChipDetailsLabelPartnerType;
	
	@FindBy(id = "ftm_chip_details_context_ftm_chip_provider")
	private WebElement ftmChipDetailsContextFtmChipProvider;
	
	@FindBy(id = "ftm_chip_details_label_model")
	private WebElement ftmChipDetailsLabelModel;
	
	@FindBy(id = "ftm_chip_details_context_model")
	private WebElement ftmChipdetailsContextModel;
	
	@FindBy(id = "ftm_chip_details__certificate_label")
	private WebElement ftmChipDetailsCertificatelabel;
	
	@FindBy(id = "ftm_chip_details_certificate_context")
	private WebElement ftmChipDetailsCertificateContext;
	
	@FindBy(id = "ftm_chip_details_partner_type_label")
	private WebElement ftmChipDetailsPartnerTypeLabel;
	
	@FindBy(id = "ftm_chip_details_partner_type_context")
	private WebElement ftmChipDetailsPartnerTypeContext;
	
	@FindBy(id = "ftm_chip_details_label_upload_date_time")
	private WebElement ftmChipDetailsLabelUploadDateTime;
	
	@FindBy(id = "ftm_chip_details_context_upload_date_time")
	private WebElement ftmChipDetailsContextUploadDateTime;
	
	@FindBy(id = "ftm_chip_details_label_expiry_date_time")
	private WebElement ftmChipDetailsLabelExpiryDateTime;
	
	@FindBy(id = "ftm_chip_details_context_expiry_date_time")
	private WebElement ftmChipDetailsContextExpiryDateTime;
	
	@FindBy(id = "download_btn")
	private WebElement downloadButtonViewPage;
	
	@FindBy(id = "ftm_view_back_btn")
	private WebElement ftmViewBackButton;
	
	@FindBy(id = "filter_btn")
	private WebElement filterButton;
	
	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;
	
	@FindBy(id = "ftm_partner_id_filter")
	private WebElement ftmPartnerIdFilter;

	@FindBy(id = "ftm_partner_id_filter_option1")
	private WebElement ftmPartnerIdFilterOption1;
	
	@FindBy(id = "ftm_make_name_filter")
	private WebElement ftmMakeNameFilter;
	
	@FindBy(id = "ftm_make_name_filter_option1")
	private WebElement ftmMakeNameFilterOption1;
	
	@FindBy(id = "ftm_model_name_filter")
	private WebElement ftmModelNameFilter;
	
	@FindBy(id = "ftm_model_name_filter_option1")
	private WebElement ftmModelNameFilterOption1;
	
	@FindBy(id = "ftm_certificate_expiry_filter")
	private WebElement ftmCertificateExpiryFilter;
	
	@FindBy(id = "ftm_certificate_expiry_filter_option1")
	private WebElement ftmCertificateExpiryFilterOption1;
	
	@FindBy(id = "ftm_status_filter")
	private WebElement ftmStatusFilter;
	
	@FindBy(id = "ftm_status_filter_option1")
	private WebElement ftmStatusFilterOption1;
	
	@FindBy(id = "ftm_status_filter_option2")
	private WebElement ftmStatusFilterOption2;

	@FindBy(id = "	ftm_status_filter_option3")
	private WebElement ftmStatusFilterOption3;
	
	@FindBy(xpath = "//*[contains(text(), 'Pending For Approval')]")
	private WebElement pendingForApproval;
	
	@FindBy(id = "partner_id_filter")
	private WebElement partnerIdFilterBox;
	
	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilterButton;
	
	@FindBy(id = "ftm_list_action_menu1")
	private WebElement ftmListActionMenuEllipsisButton;
	
	@FindBy(id = "ftm_list_approve_reject_option")
	private WebElement ftmListApproveRejectOption;
	
	@FindBy(xpath = "//*[@class='text-sm font-bold']")
	private WebElement makAndModeTitelForPopup;
	
	@FindBy(xpath = "//*[@class='text-base font-semibold text-black']")
	private WebElement doYouWantToApproveAndRejecPopupHeader;
	
	@FindBy(xpath = "//*[@class='text-sm text-[#666666] py-3']")
	private WebElement pleaseReviewTheFtmChipPopupSubHeader;
	
	@FindBy(id = "approve_btn")
	private WebElement approveButton;
	
	@FindBy(id = "reject_btn")
	private WebElement rejectButton;
	
	@FindBy(xpath = "//*[contains(text(), 'Approve')]")
	private WebElement Approved;
	
	@FindBy(xpath = "//*[contains(text(), 'Rejected')]")
	private WebElement rejectedText;
	
	@FindBy(id = "add_ftm_chip_btn")
	private WebElement addFtmChipButton;
	
	@FindBy(id = "ftm_list_item1")
	private WebElement ftmListItem1;
	
	@FindBy(id = "ftm_list_item2")
	private WebElement ftmListItem2;
	
	@FindBy(id = "status_asc_icon")
	private WebElement statusAscIcon;
	
	@FindBy(id = "status_desc_icon")
	private WebElement statusDescIcon;
	
	@FindBy(xpath = "//*[contains(text(), 'FTM Chip details already exists')]")
	private WebElement dublicateFtmChipErrorMessage;
	
	@FindBy(id = "add_ftm_clear_form")
	private WebElement addFtmClearForm;
	
	@FindBy(id = "error_close_btn")
	private WebElement errorCloseButton;
	
	@FindBy(xpath = "//*[contains(text(), 'No Data Available.')]")
	private WebElement noDataAvailableMessage;
	
	@FindBy(xpath = "//*[contains(text(), 'Auto-populated')]")
	private WebElement autoPopulatedMessage;
	
	@FindBy(id = "add_ftm_partner_id_info")
	private WebElement addFtmPartnerIdInfo;
	
	@FindBy(xpath = "//*[@class='text-black text-sm']")
	private WebElement infoMessage;
	
	@FindBy(id = "block_messsage_proceed")
	private WebElement blockMesssageProceed;
	
	@FindBy(id = "block_message_cancel")
	private WebElement blockMessageCancel;
	
	@FindBy(id = "side_nav_home_icon")
	private WebElement sideNavHomeIcon;
	
	@FindBy(xpath = "//*[contains(text(), 'Pending For Certificate Upload')]")
	private WebElement pendingForCertificateUploadText;
	
	@FindBy(id = "ftm_list_manage_certificate")
	private WebElement ftmListManageCertificate;
	
	@FindBy(id = "fileInput")
	private WebElement uploadFile;
	
	@FindBy(id = "certificate_reupload_btn")
	private WebElement certificateReuploadButton;
	
	@FindBy(xpath = "//*[@class='font-semibold text-lg text-dark-blue']")
	private WebElement manageFtmChipCertText;
	
	@FindBy(id = "ftm_list_deactivate_option")
	private WebElement ftmListDeactivateOption;
	
	@FindBy(id = "deactivate_cancel_btn")
	private WebElement deactivateCancelButton;
	
	@FindBy(id = "deactivate_submit_btn")
	private WebElement deactivateSubmitButton;
	
	@FindBy(xpath = "//*[contains(text(), 'Deactivated')]")
	private WebElement DeactivatedText;
	
	@FindBy(id = "certificate_upload_close_btn")
	private WebElement certificateUploadCloseButton;
	
	@FindBy(id = "list_of_ftm_chip")
	private WebElement listOfFtmChip;
	
	@FindBy(xpath = "//*[contains(text(), 'List of FTM Chip details')]")
	private WebElement listofFtmChipDetails;
	
	@FindBy(id = "partner_id_filter")
	private WebElement partneridFilter;
	
	@FindBy(id = "org_name_filter")
	private WebElement orgNameFilter;
	
	@FindBy(id = "ftm_id_filter")
	private WebElement ftmIdFilter;
	
	@FindBy(id = "make_filter")
	private WebElement makeFilter;
	
	@FindBy(id = "model_filter")
	private WebElement modelFilter;
	
	@FindBy(id = "status_filter")
	private WebElement statusFilter;
	
	@FindBy(id = "status_filter_option3")
	private WebElement statusFilterOption3;
	
	@FindBy(xpath = "//*[contains(text(), 'No Results Found')]")
	private WebElement noResultsFound;
	
	
	public FtmPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnAddFtmButtonWioutRecord() {
		 if(isElementDisplayed(addFtmPartnerId)) {
			 clickOnElement(addFtmPartnerId);
		 }
		 if(isElementDisplayed(addFtmButton)) {
		 clickOnElement(addFtmButton);
		 }
		
	}
	
	public void clickOnAddFtmPartnerId() {
		 clickOnElement(addFtmPartnerId);
		 clickOnElement(addFtmPartnerIdOption1); 
	}
	
	public void clickOnAddFtmPartnerIdForNoCert() {
		 clickOnElement(addFtmPartnerId);
	}
	
	public boolean isNoDataAvailableMessageDisplayed() {
		return isElementDisplayed(noDataAvailableMessage);
	}
	
	public void EnterInAddFtmMakeBox(String val) {
		 enter(addFtmMake,val);
	}
	
	public void EnterInAddFtmModelBox(String val) {
		 enter(addFtmModel,val);
	}
	
	public void clickOnAddFtmSubmitButton() {
		 clickOnElement(addFtmSubmitButton);
	}
	
	public void clickOnConfirmationCustomButton() {
		 clickOnElement(confirmationCustomButton);
	}
	
	public void clickOnCertificateUploadSubmitButton() {
		 clickOnElement(certificateUploadSubmitButton);
	}
	
	public boolean isListOfFtmTextDisplayed() {
		return isElementDisplayed(listOfFtm);
	}
	
	public boolean islFtmListAction1Displayed() {
		return isElementDisplayed(ftmListAction1);
	}
	
	public void clickOnFtmListAction1Button() {
		 clickOnElement(ftmListAction1);
	}
	
	public void clickOnFtmListViewButton() {
		 clickOnElement(ftmListView);
	}
	
	public boolean isSubTitleHomeButtonDisplayed() {
		return isElementDisplayed(subTitleHomeButton);
	}
	
	public boolean isSubTitleFtmButtonDisplayed() {
		return isElementDisplayed(subTitleFtmButton);
	}
	
	public boolean isFtmChipDetailsLabelPartnerIdDisplayed() {
		return isElementDisplayed(ftmChipDetailsLabelPartnerId);
	}
	
	public boolean isFtmChipDetailsContextPartnerIdDisplayed() {
		return isElementDisplayed(ftmChipDetailsContextPartnerId);
	}
	
	public boolean isFtmChipDetailsLabelMakeDisplayed() {
		return isElementDisplayed(ftmChipDetailsLabelMake);
	}
	
	public boolean isFtmChipDetailsContextMakeDisplayed() {
		return isElementDisplayed(ftmChipDetailsContextMake);
	}
	
	public boolean isFtmChipDetailsLabelPartnerTypeDisplayed() {
		return isElementDisplayed(ftmChipDetailsLabelPartnerType);
	}
	
	public boolean isFtmChipDetailsContextFtmChipProviderDisplayed() {
		return isElementDisplayed(ftmChipDetailsContextFtmChipProvider);
	}
	
	public boolean isFtmChipDetailsLabelModelDisplayed() {
		return isElementDisplayed(ftmChipDetailsLabelModel);
	}
	
	public boolean isFtmChipdetailsContextModelDisplayed() {
		return isElementDisplayed(ftmChipdetailsContextModel);
	}
	
	public boolean isFtmChipDetailsCertificatelabelDisplayed() {
		return isElementDisplayed(ftmChipDetailsCertificatelabel);
	}
	
	public boolean isFtmChipDetailsPartnerTypeLabelDisplayed() {
		return isElementDisplayed(ftmChipDetailsPartnerTypeLabel);
	}
	
	public boolean isFtmChipDetailsPartnerTypeContextDisplayed() {
		return isElementDisplayed(ftmChipDetailsPartnerTypeContext);
	}
	
	public boolean isFtmChipDetailsLabelUploadDateTimeDisplayed() {
		return isElementDisplayed(ftmChipDetailsLabelUploadDateTime);
	}
	
	public boolean isFtmChipDetailsContextUploadDateTimeDisplayed() {
		return isElementDisplayed(ftmChipDetailsContextUploadDateTime);
	}
	
	public boolean isFtmChipDetailsLabelExpiryDateTimeDisplayed() {
		return isElementDisplayed(ftmChipDetailsLabelExpiryDateTime);
	}
	
	public boolean isFtmChipDetailsContextExpiryDateTimeDisplayed() {
		return isElementDisplayed(ftmChipDetailsContextExpiryDateTime);
	}
	
	public boolean isDownloadButtonViewPageDisplayed() {
		return isElementDisplayed(downloadButtonViewPage);
	}
	
	public boolean isFtmViewBackButtonDisplayed() {
		return isElementDisplayed(ftmViewBackButton);
	}
	
	public void clickOnFtmViewBackButton() {
		 clickOnElement(ftmViewBackButton);
	}
	
	public void clickOnFilterButton() {
		 clickOnElement(filterButton);
	}
	
	public void clickOnFtmPartnerIdFilter() {
		 clickOnElement(ftmPartnerIdFilter);
	}
	
	public void clickOnFtmPartnerIdFilterOption1() {
		 clickOnElement(ftmPartnerIdFilterOption1);
	}
	
	public void clickOnFtmMakeNameFilter() {
		 clickOnElement(ftmMakeNameFilter);
	}
	
	public void clickOnFtmMakeNameFilterOption1() {
		 clickOnElement(ftmMakeNameFilterOption1);
	}
	
	public void clickOnFtmModelNameFilter() {
		 clickOnElement(ftmModelNameFilter);
	}
	
	public void clickOnFtmModelNameFilterOption1() {
		 clickOnElement(ftmModelNameFilterOption1);
	}
	
	public void clickOnFtmCertificateExpiryFilter() {
		 clickOnElement(ftmCertificateExpiryFilter);
	}
	
	public void clickOnFtmCertificateExpiryFilterOption1() {
		 clickOnElement(ftmCertificateExpiryFilterOption1);
	}
	
	public void clickOnFtmStatusFilter() {
		 clickOnElement(ftmStatusFilter);
	}
	
	public void clickOnFtmStatusFilterOption1() {
		 clickOnElement(ftmStatusFilterOption1);
	}
	
	public void clickOnFtmStatusFilterOption2() {
		 clickOnElement(ftmStatusFilterOption2);
	}

	public boolean isPendingForApprovalTextDisplayed() {
		return isElementDisplayed(pendingForApproval);
	}
	
	public boolean isFilterResetButtonDisplayed() {
		return isElementDisplayed(filterResetButton);
	}
	
	public void enterPartnerIdFilterBox(String value) {
		 enter(partnerIdFilterBox,value);
	}
	
	public void clickOnApplyFilterButton() {
		 clickOnElement(applyFilterButton);
	}
	
	public void clickOnFtmListActionMenuEllipsisButton() {
		 clickOnElement(ftmListActionMenuEllipsisButton);
	}
	
	public void clickOnFtmListApproveRejectOption() {
		 clickOnElement(ftmListApproveRejectOption);
	}
	
	public void clickOnApproveButton() {
		 clickOnElement(approveButton);
	}	
	
	public boolean isApprovedTextDisplayed() {
		return isElementDisplayed(Approved);
	}
	
	public void clickOnAddFtmChipButton() {
		 clickOnElement(addFtmChipButton);
	}	
	
	public void clickOnStatusAscIcon() {
		 clickOnElement(statusAscIcon);
	}
	
	public void clickOnStatusDescIcon() {
		 clickOnElement(statusDescIcon);
	}
	
	public boolean isDublicateFtmChipErrorMessageDisplayed() {
		return isElementDisplayed(dublicateFtmChipErrorMessage);
	}
	
	public void clickOnAddFtmClearForm() {
		 clickOnElement(addFtmClearForm);
	}
	
	public boolean isAddFtmSubmitButtonDisabled() {
		 refreshThePage();
		 WebElement submitButton= driver.findElement(By.id("add_ftm_submit_btn"));
		 return isElementEnabled(addFtmSubmitButton);
	}
		
	public boolean isErrorCloseButtonDisplayed() {
		return isElementDisplayed(errorCloseButton);
	}
	
	public void clickOnAddFtmPartnerIdInfo() {
		 clickOnElement(addFtmPartnerIdInfo);
	}
	
	public boolean isInfoMessageDisplayed() {
		return isElementDisplayed(infoMessage);
	}
	
	public boolean isAutoPopulatedMessageDisplayed() {
		return isElementDisplayed(autoPopulatedMessage);
	}
	
	public void clickOnBlockMesssageProceed() {
		 clickOnElement(blockMesssageProceed);
	}
	
	public void clickOnBlockMessageCancel() {
		 clickOnElement(blockMessageCancel);
	}
	
	public void clickOnSideNavHomeIcon() {
		 clickOnElement(sideNavHomeIcon);
	}
	
	public void clickOnSubTitleFtmButton() {
		 clickOnElement(subTitleFtmButton);
	}
	
	public boolean isPendingForCertificateUploadTextDisplayed() {
		return isElementDisplayed(pendingForCertificateUploadText);
	}
	
	public boolean isDownloadButtonViewPageDisabled() {
		return isElementEnabled(downloadButtonViewPage);
	}
	
	public void clickOnFtmListManageCertificate() {
		 clickOnElement(ftmListManageCertificate);
	}
	
	public void uploadCertificateConfrmationForFtm() {
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_revamp_cert\\Client.cer");
	}
	
	public boolean isCertificateReuploadButtonDisplayed() {
		return isElementDisplayed(certificateReuploadButton);
	}
	
	public boolean isFtmChipDetailsCertificateContextDisplayed() {
		return isElementDisplayed(ftmChipDetailsCertificateContext);
	}
	
	public boolean isManageFtmChipCertTextDisplayed() {
		return isElementDisplayed(manageFtmChipCertText);
	}
	
	public boolean isFtmListApproveRejectOptionIsEnabled() {
		return isElementEnabled(ftmListApproveRejectOption);
	}
	
	public void clickOnRejectButton() {
		 clickOnElement(rejectButton);
	}
	
	public boolean isRejectedTextDisplayed() {
		return isElementDisplayed(rejectedText);
	}
		
	public void clickOnFtmListDeactivateOption() {
		 clickOnElement(ftmListDeactivateOption);
	}
	
	public void clickOnDeactivateCancelButton() {
		 clickOnElement(deactivateCancelButton);
	}
	
	public void clickOnDeactivateSubmitButton() {
		 clickOnElement(deactivateSubmitButton);
	}
	
	public boolean isDeactivatedTextDisplayed() {
		return isElementDisplayed(DeactivatedText);
	}
	
	public boolean isMakAndModeTitelForPopupDisplayed() {
		return isElementDisplayed(makAndModeTitelForPopup);
	}
	
	public boolean isDoYouWantToApproveAndRejecPopupHeaderDisplayed() {
		return isElementDisplayed(doYouWantToApproveAndRejecPopupHeader);
	}
	
	public boolean isPleaseReviewTheFtmChipPopupSubHeaderDisplayed() {
		return isElementDisplayed(pleaseReviewTheFtmChipPopupSubHeader);
	}
	
	public boolean isDeactivateSubmitButtonDisplayed() {
		return isElementDisplayed(deactivateSubmitButton);
	}
	
	public boolean isPartnerIdCoulumeHeaderDisplayed() {
		return isElementDisplayed(partnerIdCoulumeHeader);
	}
	
	public boolean isMakeCoulumeHeaderDisplayed() {
		return isElementDisplayed(makeCoulumeHeader);
	}
	
	public boolean isModelCoulumeHeaderDisplayed() {
		return isElementDisplayed(modelCoulumeHeader);
	}
	
	public boolean isCreatedDateCoulumeHeaderDisplayed() {
		return isElementDisplayed(createdDateCoulumeHeader);
	}
	
	public boolean iscertTimeofUploadCoulumeHeaderDisplayed() {
		return isElementDisplayed(certTimeofUploadCoulumeHeader);
	}
	
	public boolean isCertExpiryCoulumeHeaderDisplayed() {
		return isElementDisplayed(certExpiryCoulumeHeader);
	}
	public boolean isCertExpiryStatusCoulumeHeaderDisplayed() {
		return isElementDisplayed(certExpiryStatusCoulumeHeader);
	}
	
	public boolean isStatusCoulumeHeaderDisplayed() {
		return isElementDisplayed(statusCoulumeHeader);
	}
	
	public boolean isActionCoulumeHeaderDisplayed() {
		return isElementDisplayed(actionCoulumeHeader);
	}
	
	public void clickOnCertificateUploadCloseButton() {
		 clickOnElement(certificateUploadCloseButton);
	}
	
	public boolean isListOfFtmChipDisplayed() {
		return isElementDisplayed(listOfFtmChip);
	}
	
	public boolean isTitleBackIconDisplayed() {
		return isElementDisplayed(titleBackIcon);
	}
	
	public boolean isListofFtmChipDetailsDisplayed() {
		return isElementDisplayed(listofFtmChipDetails);
	}
	
	public boolean isFilterButtonDisplayed() {
		return isElementDisplayed(filterButton);
	}
	
	public boolean isPartneridFilterDisplayed() {
		return isElementDisplayed(partneridFilter);
	}
	
	public boolean isOrgNameFilterDisplayed() {
		return isElementDisplayed(orgNameFilter);
	}
	
	public boolean isFtmIdFilterDisplayed() {
		return isElementDisplayed(ftmIdFilter);
	}
	
	public boolean isMakeFilterDisplayed() {
		return isElementDisplayed(makeFilter);
	}
	
	public boolean isModelFilterDisplayed() {
		return isElementDisplayed(modelFilter);
	}
	
	public boolean isStatusFilterDisplayed() {
		return isElementDisplayed(statusFilter);
	}
	
	public void SelectValueFromStatusFilter() {
		 clickOnElement(statusFilter);
		 clickOnElement(statusFilterOption3);
	}
	
	public void enterOrgNameFilterBox(String value) {
		 enter(orgNameFilter,value);
	}
	
	public void enterMakeFilterBox(String value) {
		 enter(makeFilter,value);
	}
	
	public void enterModelFilterBox(String value) {
		 enter(modelFilter,value);
	}
	
	public void ClickOnFilterResetButton() {
		 clickOnElement(filterResetButton);
	}
	
	public boolean isNoResultsFoundDisplayed() {
		return isElementDisplayed(noResultsFound);
	}
	
	public boolean isFtmIdCoulumeHeaderDisplayed() {
		return isElementDisplayed(ftmIdCoulumeHeader);
	}
	
	public void clickOnApprovedButton() {
		 clickOnElement(Approved);
	}
	
	public void clickOnStatusFilter() {
		 clickOnElement(statusFilter);
	}
}
