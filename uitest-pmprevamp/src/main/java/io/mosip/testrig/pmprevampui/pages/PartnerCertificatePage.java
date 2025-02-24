package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmprevampui.utility.TestRunner;

public class PartnerCertificatePage extends BasePage {

	@FindBy(id = "title_back_icon")
	private WebElement titleBackButton;

	@FindBy(xpath = "//*[text()='Upload']")
	private WebElement uploadButton;

	@FindBy(xpath = "//*[text()='Upload Partner Certificate']")
	private WebElement uploadPartnerCertificatePopUp;

	@FindBy(xpath = "//*[text()='Submit']")
	private WebElement submitButton;

	@FindBy(xpath = "//*[text()='Partner certificate for Authentication Partner is uploaded successfully.']")
	private WebElement successMessage;
	
	@FindBy(xpath = "//*[text()='Partner certificate for Device Provider is uploaded successfully.']")
	private WebElement deviceProviderSuccessMessage;
	
	@FindBy(xpath = "//*[contains(text(), 'successfully')]")
	private WebElement successMessageForFtmCert;
	
	@FindBy(id = "fileInput")
	private WebElement uploadFile;

	@FindBy(xpath = "//*[text()='Close']")
	private WebElement closeButton;
	
	@FindBy(id = "success_msg_close_icon")
	private WebElement successMsgCloseButton;
	
	@FindBy(xpath = "//*[text()='Re-Upload Partner Certificate']")
	private WebElement ReUploadPartnerCertificateText;
	
	@FindBy(xpath = "//*[text()='Please tap to select the certificate']")
	private WebElement PleaseTabToSelectText;
	
	@FindBy(xpath = "//*[text()='Only .cer or .pem certificate formats are allowed for upload']")
	private WebElement CertFormatesText;
	
	@FindBy(xpath = "//*[contains(text(), \"Last certificate was uploaded on\")]")
	private WebElement lastUploadTimeAndDate;

	@FindBy(xpath = "//*[text()='Please select all fields and upload the certificate")
	private WebElement ReUploadPartnerCertificateSubText;

	@FindBy(xpath = "//*[text()='Originally uploaded CA signed certificate downloaded successfully.']")
	private WebElement originalSignedCertDownloadedPopup;

	@FindBy(xpath = "//*[text()='MOSIP signed certificate downloaded successfully.']")
	private WebElement mosipSignedCertPopup;

	@FindBy(xpath = "//*[text()='Partner Domain Type']")
	private WebElement partnerDomainType;

	@FindBy(id = "upload_file_FILL0_wght200_GRAD0_opsz24")
	private WebElement partnerCertOvelay;

	@FindBy(xpath = "//*[contains(text(), \"The certificate uploaded is not in the correct format.\")]")
	private WebElement InvalidFormatErrorPopup;

	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;

	@FindBy(id = "download_btn1")
	private WebElement downloadButton;

	@FindBy(id = "original_certificate_download_btn1")
	private WebElement originalCertificateDownloadButton;

	@FindBy(id = "mosip_signed_certificate_download_btn1")
	private WebElement mosipSignedCertificateDownloadButton;

	@FindBy(id = "partner_certificate_re_upload_btn1")
	private WebElement partnerCertificateReuploadButton;

	@FindBy(id = "certificate_upload_close_btn")
	private WebElement certificateUploadCloseButton;

	@FindBy(id = "remove_certificate_btn")
	private WebElement removeCertificateButton;

	@FindBy(id = "certificate_upload_cancel_btn")
	private WebElement certificateUploadCancelButton;
	
	@FindBy(id = "partnerDomain_selector_dropdown")
	private WebElement partnerDomainSelectorDropdown;
	
	@FindBy(id = "partnerDomain_selector_dropdown_option1")
	private WebElement partnerDomainSelectorDropdownOptionAuth;
	
	@FindBy(id = "partnerDomain_selector_dropdown_option2")
	private WebElement partnerDomainSelectorDropdownOptionFtm;
	@FindBy(id = "partnerDomain_selector_dropdown_option3")
	private WebElement deviceInPartnerDomainSelectorDropdown3;
	
	@FindBy(id = "upload_trust_certificate_submit_btn")
	private WebElement SubmitButtonForAdmin;
	
	@FindBy(id = "confirmation_go_back_btn")
	private WebElement GoBackButton;
	
	@FindBy(id = "confirmation_home_btn")
	private WebElement confirmationHomeButton;
	
	@FindBy(id = "dashboard_ftm_chip_provider_card")
	private WebElement dashboardFtmChipProviderCard;
	
	@FindBy(id = "error_close_btn")
	private WebElement errorCloseButton;
	
	@FindBy(id = "partnerDomain_selector_dropdown_option3")
	private WebElement deviceInPartnerDomainSelectorDropdown;
	
	@FindBy(xpath = "//*[text()='Root CA/Intermediate CA Certificates not found.']")
	private WebElement noRootCert;
	
	@FindBy(xpath = "//*[text()='Self Signed Certificate not allowed as partner.']")
	private WebElement errorCodeForSelfSignedCer;
	
	@FindBy(id = "sub_title_btn")
	private WebElement subTitelButton;
	
	
	@FindBy(xpath = "//*[contains(text(), \"Upload Trust Certificate\")]")
	private WebElement uploadTrustCertificateText;
	
	@FindBy(xpath = "//*[text()='Please select the partner domain and upload Root CA / Intermediate CA Certificate.']")
	private WebElement partnerPageSubTitleText;
	
	@FindBy(xpath = "//*[text()='Please tap to select the Root CA / Intermediate CA Certificate']")
	private WebElement uploadBoxHeader;
	
	@FindBy(id = "upload_trust_certificate_cancel_btn")
	private WebElement adminCertUploadCancelButton;
	
	@FindBy(xpath = "//*[text()='Trust Certificate for FTM is uploaded successfully!']")
	private WebElement  ftmCertUploadSuccessMessage;
	
	@FindBy(id = "certificate_list_view1")
	private WebElement certificatelistview1;
	
	@FindBy(id = "root_certificate_details_view_btn")
	private WebElement rootCertificateDetailsViewButton;
	
	
	@FindBy(id = "file_upload_blue")
	private WebElement fileIcon;
	
	@FindBy(id = "certificate_download_btn")
	private WebElement certificateDownloadButton;
	
	@FindBy(id = "trust_certificate_partner_type_label")
	private WebElement trustCertificatePartnerTypeLabel;
	
	@FindBy(id = "trust_certificate_partner_type_context")
	private WebElement trustCertificatePartnerTypeContext;
	
	@FindBy(id = "trust_certificate_label_upload_date_time")
	private WebElement trustCertificateLabelUploadDateTime;
	
	@FindBy(id = "trust_certificate_context_upload_date_time")
	private WebElement trustCertificateContextUploadDateTime;
	
	@FindBy(id = "trust_certificate_label_expiry_date_time")
	private WebElement trustCertificateLabelExpiryDateTime;
	
	@FindBy(id = "trust_certificate_context_expiry_date_time")
	private WebElement trustCertificateContextExpiryDateTime;
	
	@FindBy(id = "view_trust_certificate_back_btn")
	private WebElement viewTrustCertificateBackButton;
	
	@FindBy(id = "success_msg_close_icon")
	private WebElement successMeassageCloseIcon;
	
	@FindBy(id = "certificate_list_view_btn")
	private WebElement certificateListViewButton;
	
	
	public PartnerCertificatePage(WebDriver driver) {
		super(driver);
	}
	public boolean isDeviceProviderSuccessMessage() {
		return isElementDisplayed(deviceProviderSuccessMessage);
	}
	
	public boolean isPartnerCertificatePageDisplayed() {
		return isElementDisplayed(titleBackButton);
	}

	public void clickOnUploadButton() {
		clickOnElement(uploadButton);
	}
	
	public boolean isUploadPartnerCertificatePopUpDisplayed() {
		return isElementDisplayed(uploadPartnerCertificatePopUp);
	}

	public void uploadCertificateRootCa() {
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_revamp_cert\\RootCA.cer");
	}
	public void uploadCertificateSubCa() {
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_revamp_cert\\IntermediateCA.cer");
	}
	public void uploadCertificate() {
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_revamp_cert\\Client.cer");
	}
	
	public void uploadCertificateForAnotherOrg() {
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_revamp_cert\\FTM_ca.cer");
	}


	public void clickOnSubmitButton() {
		clickOnElement(submitButton);
	}

	public boolean isSuccessMessageDisplayed() {
		return isElementDisplayed(successMessage);
	}

	public void clickOnCloseButton() {
		clickOnElement(closeButton);
	}

	public DashboardPage clickOnHomeButton() {
		clickOnElement(homeButton);
		return new DashboardPage(driver);
	}

	public boolean isDownloadButtonDisplayed() {
		return isElementDisplayed(downloadButton);
	}

	public void clickOnPartnerCertificateReuploadButton() {
		clickOnElement(partnerCertificateReuploadButton);
	}

	public void clickOnDownloadButton() {
		clickOnElement(downloadButton);
	}

	public void clickOnOriginalCertificateDownloadButton() {
		clickOnElement(originalCertificateDownloadButton);
	}

	public void clickOnMosipSignedCertificateDownloadButton() {
		clickOnElement(mosipSignedCertificateDownloadButton);
	}

	public boolean isOriginalSignedCertDownloadedPopupDisplayed() {
		return isElementDisplayed(originalSignedCertDownloadedPopup);
	}

	public boolean isMosipSignedCertPopupDisplayed() {
		return isElementDisplayed(mosipSignedCertPopup);
	}

	public void clickOncertificateUploadCloseButton() {
		clickOnElement(certificateUploadCloseButton);
	}

	public boolean isReUploadPartnerCertificateTextDisplayed() {
		return isElementDisplayed(ReUploadPartnerCertificateText);
	}

	public boolean isReUploadPartnerCertificateSubTextDisplayed() {
		return isElementDisplayed(ReUploadPartnerCertificateSubText);
	}

	public void uploadCertificateInvalidCert() {
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_revamp_cert\\cert.crdownload");
	}

	public void clickOnRemoveCertificateButton() {
		clickOnElement(removeCertificateButton);
	}

	public boolean isPartnerDomainTypeDisplayed() {
		return isElementDisplayed(partnerDomainType);
	}

	public boolean isPartnerCertOvelayDisplayed() {
		return isElementDisplayed(partnerCertOvelay);
	}

	public boolean isInvalidFormatErrorPopupDisplayed() {
		return isElementDisplayed(InvalidFormatErrorPopup);
	}

	public void clickOnCertificateUploadCancelButton() {
		clickOnElement(certificateUploadCancelButton);
	}
	
	public void clickOnpartnerDomainSelectorDropdown() {
		clickOnElement(partnerDomainSelectorDropdown);
	}
	
	public void clickOnpartnerpartnerDomainSelectorDropdownOptionAuth() {
		clickOnElement(partnerDomainSelectorDropdownOptionAuth);
	}
	
	public void ClickOnDeviceInPartnerDomainSelectorDropdown() {
		clickOnElement(deviceInPartnerDomainSelectorDropdown);
	}
	public void ClickonSubmitButtonForAdmin() {
		clickOnElement(SubmitButtonForAdmin);
	}
	
	public void ClickOnGoBackButton() {
		clickOnElement(GoBackButton);
	}
	
	public void ClickOnsuccessMsgCloseButton() {
		clickOnElement(successMsgCloseButton);
	}
	
	public void clickOnPartnerDomainSelectorDropdownOptionFtm() {
		clickOnElement(partnerDomainSelectorDropdownOptionFtm);
	}
	
	public boolean isDashboardFtmChipProviderCardDisplayed() {
		return isElementDisplayed(dashboardFtmChipProviderCard);
	}
	
	public boolean isSuccessMessageForFtmCertDisplayed() {
		return isElementDisplayed(successMessageForFtmCert);
	}
	
	public void clickOnFtmChipProviderCard() {
		 clickOnElement(dashboardFtmChipProviderCard);
	}
	
	public void certifiCateUploadCancelButton() {
		if(isElementDisplayed(certificateUploadCloseButton)) {
		 clickOnElement(certificateUploadCloseButton);
		}
		}
	
	public boolean VerifyTheStatusWithAsendingOrder() {
	 WebElement first= driver.findElement(By.xpath("//*[@id='ftm_list_item1']//*[contains(text(), 'Approved')]"));
	 return isElementDisplayed(first);
	}
	
	public boolean VerifyTheStatusWithDesendingOrder() {
		 WebElement first= driver.findElement(By.xpath("//*[@id='ftm_list_item1']//*[contains(text(), 'Pending For Approval')]"));
		 return isElementDisplayed(first);
		}	
		
	public void clickOnErrorCloseButton() {
		 clickOnElement(errorCloseButton);
	}
	
	public boolean isNoRootCertDisplayed() {
		return isElementDisplayed(noRootCert);
	}
	
	public boolean isErrorCodeForSelfSignedCerDisplayed() {
		return isElementDisplayed(errorCodeForSelfSignedCer);
	}
	
	public boolean isPleaseTabToSelectTextDisplayed() {
		return isElementDisplayed(PleaseTabToSelectText);
	}
	
	public boolean isCertFormatesTextDisplayed() {
		return isElementDisplayed(CertFormatesText);
	}
	
	public boolean isLastUploadTimeAndDateTextDisplayed() {
		return isElementDisplayed(lastUploadTimeAndDate);
	}
	
	public boolean isSubtitleHomeButtonDisplayed() {
		return isElementDisplayed(homeButton);
	}
	
	public boolean isSubtitleButtonButtonDisplayed() {
		return isElementDisplayed(subTitelButton);
	}
	
	
	public boolean isUploadTrustCertificateTextDisplayed() {
		return isElementDisplayed(uploadTrustCertificateText);
	}
	
	public boolean isPartnerPageSubTitleTextDisplayed() {
		return isElementDisplayed(partnerPageSubTitleText);
	}
	
	public boolean isUploadBoxHeaderTextDisplayed() {
		return isElementDisplayed(uploadBoxHeader);
	}
	
	public boolean isAdminCertUploadCancelButtonDisplayed() {
		return isElementDisplayed(adminCertUploadCancelButton);
	}
	
	public boolean isFtmCertUploadSuccessMessageDisplayed() {
		return isElementDisplayed(ftmCertUploadSuccessMessage);
	}
	
	public boolean isGoBackButtonDisplayed() {
		return isElementDisplayed(GoBackButton);
	}
	
	public boolean isConfirmationHomeButtonDisplayed() {
		return isElementDisplayed(confirmationHomeButton);
	}
	
	public void clickOncertificatelistview1() {
		 clickOnElement(certificatelistview1);
	}
	
	public void clickOnrootCertificateDetailsViewButton() {
		 clickOnElement(rootCertificateDetailsViewButton);
	}
	
	
	public boolean isFileIconDisplayed() {
		return isElementDisplayed(fileIcon);
	}
	
	public boolean isCertificateDownloadButtonDisplayed() {
		return isElementDisplayed(certificateDownloadButton);
	}
	
	public boolean isTrustCertificatePartnerTypeLabelDisplayed() {
		return isElementDisplayed(trustCertificatePartnerTypeLabel);
	}
	
	public boolean isTrustCertificatePartnerTypeContextDisplayed() {
		return isElementDisplayed(trustCertificatePartnerTypeContext);
	}
	
	public boolean isTrustCertificateLabelUploadDateTimeDisplayed() {
		return isElementDisplayed(trustCertificateLabelUploadDateTime);
	}
	
	public boolean isTrustCertificateContextUploadDateTimeDisplayed() {
		return isElementDisplayed(trustCertificateContextUploadDateTime);
	}
	
	public boolean isTrustCertificateLabelExpiryDateTimeDisplayed() {
		return isElementDisplayed(trustCertificateLabelExpiryDateTime);
	}
	
	public boolean isTrustCertificateContextExpiryDateTimeDisplayed() {
		return isElementDisplayed(trustCertificateContextExpiryDateTime);
	}
	
	public boolean isViewTrustCertificateBackButtonDisplayed() {
		return isElementDisplayed(viewTrustCertificateBackButton);
	}
	
	public void clickOnViewTrustCertificateBackButton() {
		 clickOnElement(viewTrustCertificateBackButton);
	}
	
	public void clickOnCertificateDownloadButton() {
		 clickOnElement(certificateDownloadButton);
	}
	
	public boolean isSuccessMeassageCloseIconDisplayed() {
		return isElementDisplayed(successMeassageCloseIcon);
	}
	
	public void clickOnCertificateListViewButton() {
		 clickOnElement(certificateListViewButton);
	}
	
	public boolean isSubmitButtonForAdminDisplayed() {
		return isElementDisplayed(SubmitButtonForAdmin);
	}
	
	public void clickOnTitleBackButton() {
		 clickOnElement(titleBackButton);
	}
	
	
}
