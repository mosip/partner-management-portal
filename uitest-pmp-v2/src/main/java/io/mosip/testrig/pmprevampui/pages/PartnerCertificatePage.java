package io.mosip.testrig.pmpuiv2.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.utility.TestRunner;

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
	
	@FindBy(xpath = "//p[contains(text(), 'Partner certificate for FTM Chip Provider is uploaded successfully.')]")
	private WebElement successMessageForFtmCert;
	
	@FindBy(xpath = "//p[contains(text(), 'FTM Chip Certificate is uploaded successfully')]")
	private WebElement successMessageForFtmChipCert;
	
	@FindBy(id = "fileInput")
	private WebElement uploadFile;

	@FindBy(id = "certificate_upload_close_btn")
	private WebElement closeButton;
	
	@FindBy(id = "success_msg_close_icon")
	private WebElement successMsgCloseButton;
	
	@FindBy(xpath = "//h3[text()='Re-Upload Partner Certificate']")
	private WebElement ReUploadPartnerCertificateText;
	
	@FindBy(xpath = "//h5[text()='Please tap to select the certificate']")
	private WebElement PleaseTabToSelectText;
	
	@FindBy(xpath = "//p[text()='Only .cer or .pem certificate formats are allowed for upload']")
	private WebElement CertFormatesText;
	
	@FindBy(xpath = "//*[contains(text(), \"Last certificate was uploaded on\")]")
	private WebElement lastUploadTimeAndDate;

	@FindBy(xpath = "//p[contains(text(), 'Please select all fields and upload')]")
	private WebElement ReUploadPartnerCertificateSubText;

	@FindBy(xpath = "//*[text()='Originally uploaded CA signed certificate downloaded successfully.']")
	private WebElement originalSignedCertDownloadedPopup;

	@FindBy(xpath = "//*[text()='MOSIP signed certificate downloaded successfully.']")
	private WebElement mosipSignedCertPopup;

	@FindBy(xpath = "//label[text()='Partner Domain Type']")
	private WebElement partnerDomainType;

	@FindBy(id = "upload_file_FILL0_wght200_GRAD0_opsz24")
	private WebElement partnerCertOvelay;

	@FindBy(xpath = "//p[contains(text(), \"The certificate uploaded is not in the correct format.\")]")
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
	
	@FindBy(xpath = "//p[text()='Root CA/Intermediate CA Certificates not found.']")
	private WebElement noRootCert;
	
	@FindBy(xpath = "//p[text()='Self Signed Certificate not allowed as partner.']")
	private WebElement errorCodeForSelfSignedCer;
	
	@FindBy(id = "sub_title_btn")
	private WebElement subTitelButton;
	
	@FindBy(xpath = "//h1[contains(text(), \"Upload Trust Certificate\")]")
	private WebElement uploadTrustCertificateText;
	
	@FindBy(xpath = "//p[text()='Please select the partner domain and upload Root CA / Intermediate CA Certificate.']")
	private WebElement partnerPageSubTitleText;
	
	@FindBy(xpath = "//h5[text()='Please tap to select the Root CA / Intermediate CA Certificate']")
	private WebElement uploadBoxHeader;
	
	@FindBy(id = "upload_trust_certificate_cancel_btn")
	private WebElement adminCertUploadCancelButton;
	
	@FindBy(xpath = "//h1[text()='Trust Certificate for FTM is uploaded successfully!']")
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
	
	@FindBy(xpath = "//input[@value='Authentication Partner']")
	private WebElement partnerTypeValue;
	
	@FindBy(xpath = "//input[@value='AUTH']")
	private WebElement partnerDomainTypeValue;
	
	@FindBy(xpath = "//img[@class='mb-2 w-10 h-10']")
	private WebElement uploadCertificateIcon;
	
	@FindBy(xpath = "//h5[text()='Client.cer']")
	private WebElement uploadedCertificateFileName;
	
	@FindBy(xpath = "//label[text()='Partner Type']")
	private WebElement partnerTypeLabel;
	
	@FindBy(xpath = "//h1[text()='Certificate Trust Store']")
	private WebElement certificateTrustStoreTitle;
	
	@FindBy(xpath = "//h1[text()='Root CA']")
	private WebElement rootCATab;
	
	@FindBy(xpath = "//p[contains(text(), 'List of Root CA Certificates')]")
	private WebElement subtitleOfRootCA;
	
	@FindBy(xpath = "//p[text()='Home']")
	private WebElement breadcumbHome;
	
	@FindBy(xpath = "//div[text()='Certificate ID']")
	private WebElement certificateIdHeader;
	
	@FindBy(xpath = "//div[text()='Partner Domain']")
	private WebElement partnerDomainHeader;
	
	@FindBy(xpath = "//div[text()='Issued To']")
	private WebElement issuedToHeader;
	
	@FindBy(xpath = "//div[text()='Issued By']")
	private WebElement issuedByHeader;
	
	@FindBy(xpath = "//div[text()='Valid From']")
	private WebElement validFromHeader;
	
	@FindBy(xpath = "//div[text()='Valid To']")
	private WebElement validToHeader;
	
	@FindBy(xpath = "//div[text()='Uploaded On']")
	private WebElement uploadedOnHeader;
	
	@FindBy(xpath = "//div[text()='Validity Status']")
	private WebElement validityStatusHeader;
	
	@FindBy(xpath = "//div[text()='Action']")
	private WebElement actionHeader;
	
	@FindBy(id = "rootTrustList.uploadRootCaTrust")
	private WebElement uploadTrustCertificateButton;
	
	@FindBy(xpath = "//h1[text()='Upload Trust Certificate']")
	private WebElement certificateUploadPopup;
	
	@FindBy(id = "certificateId_asc_icon")
	private WebElement certificateIdAscIcon;
	
	@FindBy(id = "certificateId_desc_icon")
	private WebElement certificateIdDescIcon;
	
	@FindBy(id = "partnerDomain_asc_icon")
	private WebElement partnerDomainAscIcon;
	
	@FindBy(id = "partnerDomain_desc_icon")
	private WebElement partnerDomainDescIcon;
	
	@FindBy(id = "issuedTo_asc_icon")
	private WebElement issuedToAscIcon;
	
	@FindBy(id = "issuedTo_desc_icon")
	private WebElement issuedToDescIcon;
	
	@FindBy(id = "issuedBy_asc_icon")
	private WebElement issuedByAscIcon;
	
	@FindBy(id = "issuedBy_desc_icon")
	private WebElement issuedByDescIcon;
	
	@FindBy(id = "validFrom_asc_icon")
	private WebElement validFromAscIcon;
	
	@FindBy(id = "validFrom_desc_icon")
	private WebElement validFromDescIcon;
	
	@FindBy(id = "validTill_asc_icon")
	private WebElement validToAscIcon;
	
	@FindBy(id = "validTill_desc_icon")
	private WebElement validToDescIcon;
	
	@FindBy(id = "uploadedDateTime_asc_icon")
	private WebElement uploadedTimeAscIcon;
	
	@FindBy(id = "uploadedDateTime_desc_icon")
	private WebElement uploadedTimeDescIcon;
	
	@FindBy(id = "certificate_list_item1")
	private WebElement certificateList1;
	
	@FindBy(xpath = "//h1[text()='View Root CA Certificate Details']")
	private WebElement rootCACertificateDetailsPage;
	
	@FindBy(id = "filter_btn")
	private WebElement filterButton;
	
	@FindBy(id = "cert_id_filter")
	private WebElement certIdFilter;
	
	@FindBy(id = "cert_partner_domain_filter")
	private WebElement partnerDomainFilter;
	
	@FindBy(id = "cert_issued_to_filter")
	private WebElement certIssuedToFilter;
	
	@FindBy(id = "cert_issued_by_domain_filter")
	private WebElement certIssuedByFilter;

	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;
	
	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilterButton;
	
	@FindBy(id = "cert_partner_domain_filter_option1")
	private WebElement authPartnerDomain;
	
	@FindBy(xpath = "//p[text()='No Results Found']")
	private WebElement noResultsFound;
	
	@FindBy(xpath = "//p[text()='Root CA Certificate is downloaded successfully']")
	private WebElement rootCACertificateDownloaded;

	@FindBy(xpath = "//p[text()='List of Root CA Certificates']")
	private WebElement bredacumbOfRootCA;
	
	@FindBy(xpath = "//p[text()='Certificate ID']")
	private WebElement certificateIdLabel;
	
	@FindBy(xpath = "//p[text()='Issued To']")
	private WebElement issuedToLabel;
	
	@FindBy(xpath = "//p[text()='Issued By']")
	private WebElement issuedByLabel;
	
	@FindBy(xpath = "//p[text()='CN=CA,OU=CA,O=CA,L=aa,ST=aa,C=aa']")
	private WebElement rootCAIssuedToContext;
	
	@FindBy(xpath = "//p[text()='CN=CA,OU=CA,O=CA,L=aa,ST=aa,C=aa']")
	private WebElement rootCAIssuedByContext;
	
	@FindBy(xpath = "//p[text()='Certificate Thumbprint']")
	private WebElement certificateThumbprintLabel;
	
	@FindBy(xpath = "(//p[@class='font-[600] text-vulcan text-base break-all'])[3]")
	private WebElement certificateThumbprintValue;
	
	@FindBy(xpath = "//h6[text()='Root CA Certificate']")
	private WebElement rootCertificateTitle;
	
	@FindBy(id = "root_of_trust_certificates_tab")
	private WebElement rootCACertTab;
	
	@FindBy(id = "intermediate_root_of_trust_certificates_tab")
	private WebElement intermediateCACertTab;
	
	@FindBy(xpath = "//p[contains(text(), 'List of Intermediate CA Certificates')]")
	private WebElement subtitleOfIntermediateCA;
	
	@FindBy(xpath = "//p[text()='View Intermediate CA Certificate Details']")
	private WebElement viewIntermediateCADetails;
	
	@FindBy(xpath = "//td[text()='Valid']")
	private WebElement statusValid;
	
	@FindBy(xpath = "//td[@class='px-2 break-all' and text()='CN=SUBCA,OU=SUBCA,O=SUBCA,L=aa,ST=aa,C=aa']")
	private WebElement issuedToDetails;
	
	@FindBy(xpath = "//td[@class='px-2 break-all' and text()='CN=CA,OU=CA,O=CA,L=aa,ST=aa,C=aa']")
	private WebElement issuedByDetails;
	
	@FindBy(xpath = "//p[contains(text(), 'Certificate Chain of Trust for the given Intermediate CA certificate is downloaded successfully.')]")
	private WebElement intCACertDownloadedMsg;
	
	@FindBy(xpath = "//p[contains(text(), 'List of Intermediate CA Certificates')]")
	private WebElement intCACertBreadcumb;
	
	@FindBy(xpath = "//p[text()='CN=SUBCA,OU=SUBCA,O=SUBCA,L=aa,ST=aa,C=aa']")
	private WebElement intCAIssuedToContext;
	
	@FindBy(xpath = "//p[text()='CN=CA,OU=CA,O=CA,L=aa,ST=aa,C=aa']")
	private WebElement intCAIssuedByContext;
	
	@FindBy(xpath = "//h6[text()='Intermediate CA Certificate']")
	private WebElement intCACertificateTitle;
	
	@FindBy(xpath = "//h1[text()='Upload Trust Certificate']")
	private WebElement partnerAdminCertUploadTitle;
	
	@FindBy(xpath = "//p[@class='text-light-gray py-1' and text()='Please select the partner domain and upload Root CA / Intermediate CA Certificate.']")
	private WebElement uploadInstructionMessage;
	
	@FindBy(xpath = "//h1[text()='Select partner domain']")
	private WebElement selectPartnerDomainPlaceHolder;
	
	@FindBy(xpath = "//h5[contains(text(), 'Please tap to select the Root CA / Intermediate CA Certificate')]")
	private WebElement uploadCertInstructionText;
	
	@FindBy(xpath = "//h1[contains(text(), 'Trust Certificate for AUTH is uploaded successfully!')]")
	private WebElement uploadedSuccessfullyMessage;
	
	@FindBy(xpath = "//img[@id='confirmation_success_icon']")
	private WebElement successIcon;
	
	@FindBy(xpath = "//h5[normalize-space()='expiredRoot.cer']")
	private WebElement uploadedExpiredRootCACertificateName;
	
	@FindBy(xpath = "//p[text()='Certificate Dates are not valid.']")
	private WebElement certificateDatesNotValidMessage;
	
	@FindBy(xpath = "//p[contains(text(), 'Please upload corresponding Root Certificate to proceed further')]")
	private WebElement uploadRootCertificateFirstErrorMessage;
	
	@FindBy(id = "upload_trust_certificate_clear")
	private WebElement certificateClearButton;

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
<<<<<<< HEAD
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "RootCA.cer"));
=======
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_uiv2_cert\\RootCA.cer");
>>>>>>> 1048d585 ([MOSIP-40990] changes related to renaming from pmp-revamp to pmp-ui-v2)
	}

	public void uploadCertificateSubCa() {
<<<<<<< HEAD
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "IntermediateCA.cer"));
=======
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_uiv2_cert\\IntermediateCA.cer");
>>>>>>> 1048d585 ([MOSIP-40990] changes related to renaming from pmp-revamp to pmp-ui-v2)
	}

	public void uploadCertificate() {
<<<<<<< HEAD
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "Client.cer"));
=======
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_uiv2_cert\\Client.cer");
>>>>>>> 1048d585 ([MOSIP-40990] changes related to renaming from pmp-revamp to pmp-ui-v2)
	}

	public void uploadCertificateForAnotherOrg() {
<<<<<<< HEAD
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "FTM_ca.cer"));
=======
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_uiv2_cert\\FTM_ca.cer");
>>>>>>> 1048d585 ([MOSIP-40990] changes related to renaming from pmp-revamp to pmp-ui-v2)
	}

	public void uploadExpiredCertificateForRootCa() {
<<<<<<< HEAD
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "expiredRoot.cer"));
=======
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_uiv2_cert\\expiredRoot.cer");
>>>>>>> 1048d585 ([MOSIP-40990] changes related to renaming from pmp-revamp to pmp-ui-v2)
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
<<<<<<< HEAD
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "cert.crdownload"));
=======
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_uiv2_cert\\cert.crdownload");
>>>>>>> 1048d585 ([MOSIP-40990] changes related to renaming from pmp-revamp to pmp-ui-v2)
	}

	public void clickOnRemoveCertificateButton() {
		clickOnElement(removeCertificateButton);
	}

	public boolean isPartnerDomainTypeLabelDisplayed() {
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
	
	public void ClickOnSuccessMsgCloseButton() {
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
	
	public boolean isSuccessMessageForFtmChipCertDisplayed() {
		return isElementDisplayed(successMessageForFtmChipCert);
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
	
	public void clickOnRootCADownloadButton() {
		 clickOnElement(certificateListViewButton);
	}
	
	public boolean isSubmitButtonForAdminDisplayed() {
		return isElementDisplayed(SubmitButtonForAdmin);
	}
	
	public void clickOnTitleBackButton() {
		 clickOnElement(titleBackButton);
	}
	
	public boolean isPartnerTypeValueDisplayed() {
		return isElementDisplayed(partnerTypeValue);
	}
	
	public boolean isPartnerTypeValueDisabled() {
		return isElementDisabled(partnerTypeValue);
	}
	
	public boolean isPartnerDomainTypeValueDisplayed() {
		return isElementDisplayed(partnerDomainTypeValue);
	}
	
	public boolean isPartnerDomainTypeValueDisabled() {
		return isElementDisabled(partnerDomainTypeValue);
	}
	
	public boolean isUploadCertificateIconDisplayed() {
		return isElementDisplayed(uploadCertificateIcon);
	}
	
	public boolean isLastCertificateUploadDateDisplayed() {
	    String expectedDate = PmpTestUtil.todayDateWithoutZeroPadder;
	    String xpath = "//p[contains(., 'Last certificate was uploaded on') and contains(., '" + expectedDate + "')]";
	    WebElement uploadDate = driver.findElement(By.xpath(xpath));
	    return isElementDisplayed(uploadDate);
	}
	
	public boolean isUploadedCertificateNameDisplayed() {
		return isElementDisplayed(uploadedCertificateFileName);
	}
	
	public boolean isCertificateRemoveButtonDisplayed() {
		return isElementDisplayed(removeCertificateButton);
	}
	
	public boolean isPartnerTypeLabelDisplayed() {
		return isElementDisplayed(partnerTypeLabel);
	}
	
	public boolean isCertificateTrustStoreTitleDisplayed() {
		return isElementDisplayed(certificateTrustStoreTitle);
	}
	
	public boolean isRootCATabDisplayed() {
		return isElementDisplayed(rootCATab);
	}
	
	public boolean isSubtitleOfRootCADisplayed() {
		return isElementDisplayed(subtitleOfRootCA);
	}
	
	public boolean isBreadcumbHomeDisplayed() {
		return isElementDisplayed(breadcumbHome);
	}
	
	public boolean isCertificateIdHeaderDisplayed() {
		return isElementDisplayed(certificateIdHeader);
	}
	
	public boolean isPartnerDomainHeaderDisplayed() {
		return isElementDisplayed(partnerDomainHeader);
	}
	
	public boolean isIssuedToHeaderDisplayed() {
		return isElementDisplayed(issuedToHeader);
	}
	
	public boolean isIssuedByHeaderDisplayed() {
		return isElementDisplayed(issuedByHeader);
	}
	
	public boolean isValidFromHeaderDisplayed() {
		return isElementDisplayed(validFromHeader);
	}
	
	public boolean isValidToHeaderDisplayed() {
		return isElementDisplayed(validToHeader);
	}
	
	public boolean isUploadedOnHeaderDisplayed() {
		return isElementDisplayed(uploadedOnHeader);
	}
	
	public boolean isValidityStatusHeaderDisplayed() {
		return isElementDisplayed(validityStatusHeader);
	}
	
	public boolean isActionHeaderDisplayed() {
		return isElementDisplayed(actionHeader);
	}
	
	public boolean isUploadTrustCertificateButtonDisplayed() {
		return isElementDisplayed(uploadTrustCertificateButton);
	}
	
	public void clickOnUploadTrustCertificateButton() {
		 clickOnElement(uploadTrustCertificateButton);
	}
	
	public boolean isCertificateUploadPopupDisplayed() {
		return isElementDisplayed(certificateUploadPopup);
	}
	
	public boolean isCertificateIdAscIconDisplayed() {
		return isElementDisplayed(certificateIdAscIcon);
	}
	
	public boolean isCertificateIdDescIconDisplayed() {
		return isElementDisplayed(certificateIdDescIcon);
	}
	
	public boolean isPartnerDomainAscIconDisplayed() {
		return isElementDisplayed(partnerDomainAscIcon);
	}
	
	public boolean isPartnerDomainDescIconDisplayed() {
		return isElementDisplayed(partnerDomainDescIcon);
	}
	
	public boolean isIssuedToAscIconDisplayed() {
		return isElementDisplayed(issuedToAscIcon);
	}
	
	public boolean isIssuedToDescIconDisplayed() {
		return isElementDisplayed(issuedToDescIcon);
	}
	
	public boolean isIssuedByAscIconDisplayed() {
		return isElementDisplayed(issuedByAscIcon);
	}
	
	public boolean isIssuedByDescIconDisplayed() {
		return isElementDisplayed(issuedByDescIcon);
	}
	
	public boolean isValidFromAscIconDisplayed() {
		return isElementDisplayed(validFromAscIcon);
	}
	
	public boolean isValidFromDescIconDisplayed() {
		return isElementDisplayed(validFromDescIcon);
	}
	
	public boolean isValidToAscIconDisplayed() {
		return isElementDisplayed(validToAscIcon);
	}
	
	public boolean isValidToDescIconDisplayed() {
		return isElementDisplayed(validToDescIcon);
	}
	
	public boolean isUploadedTimeAscIconDisplayed() {
		return isElementDisplayed(uploadedTimeAscIcon);
	}
	
	public boolean isUploadedTimeDescIconDisplayed() {
		return isElementDisplayed(uploadedTimeDescIcon);
	}
	
	public void clickOnCertificateIdAscIcon() {
		 clickOnElement(certificateIdAscIcon);
	}
	
	public void clickOnCertificateIdDescIcon() {
		 clickOnElement(certificateIdDescIcon);
	}
	
	public void clickOnPartnerDomainAscIcon() {
		 clickOnElement(partnerDomainAscIcon);
	}
	
	public void clickOnPartnerDomainDescIcon() {
		 clickOnElement(partnerDomainDescIcon);
	}
	
	public void clickOnIssuedToAscIcon() {
		 clickOnElement(issuedToAscIcon);
	}
	
	public void clickOnIssuedToDescIcon() {
		 clickOnElement(issuedToDescIcon);
	}
	
	public void clickOnIssuedByAscIcon() {
		 clickOnElement(issuedByAscIcon);
	}
	
	public void clickOnIssuedByDescIcon() {
		 clickOnElement(issuedByDescIcon);
	}
	
	public void clickOnValidFromAscIcon() {
		 clickOnElement(validFromAscIcon);
	}
	
	public void clickOnValidFromDescIcon() {
		 clickOnElement(validFromDescIcon);
	}
	
	public void clickOnValidToAscIcon() {
		 clickOnElement(validToAscIcon);
	}
	
	public void clickOnValidToDescIcon() {
		 clickOnElement(validToDescIcon);
	}
	
	public void clickOnUploadedTimeAscIcon() {
		 clickOnElement(uploadedTimeAscIcon);
	}
	
	public void clickOnUploadedTimeDescIcon() {
		 clickOnElement(uploadedTimeDescIcon);
	}
	
	public void clickOnCertificateList1() {
		 clickOnElement(certificateList1);
	}
	
	public boolean isRootCACertificateDetailsPageDisplayed() {
		return isElementDisplayed(rootCACertificateDetailsPage);
	}
	
	public boolean isFilterButtonDisplayed() {
		return isElementDisplayed(filterButton);
	}
	
	public void clickOnFilterButton() {
		 clickOnElement(filterButton);
	}
	
	public boolean isCertIdFilterDisplayed() {
		return isElementDisplayed(certIdFilter);
	}
	
	public boolean isPartnerDomainFilterDisplayed() {
		return isElementDisplayed(partnerDomainFilter);
	}
	
	public boolean isCertIssuedToFilterDisplayed() {
		return isElementDisplayed(certIssuedToFilter);
	}
	
	public boolean isCertIssuedByFilterDisplayed() {
		return isElementDisplayed(certIssuedByFilter);
	}
	
	public boolean isApplyFilterButtonDisabled() {
		return isElementDisabled(applyFilterButton);
	}
	
	public boolean isFilterResetButtonEnabled() {
		return isElementEnabled(filterResetButton);
	}
	
	public boolean isFilterButtonDisabled() {
		return isElementDisabled(filterButton);
	}
	
	public void selectPartnerDomainAuthInFilter() {
		 clickOnElement(partnerDomainFilter);
		 clickOnElement(authPartnerDomain);
	}
	
	public void enterIssuedToInFilter(String value) {
		enter(certIssuedToFilter,value);
	}
	
	public void enterIssuedByInFilter(String value) {
		enter(certIssuedByFilter,value);
	}
	
	public boolean isApplyFilterButtonEnabled() {
		return isElementEnabled(applyFilterButton);
	}
	
	public void clickOnApplyFilterButton() {
		 clickOnElement(applyFilterButton);
	}
	
	public void clickOnFilterResetButton() {
		 clickOnElement(filterResetButton);
	}
	
	public boolean isNoResultsFoundDisplayed() {
		return isElementDisplayed(noResultsFound);
	}
	
	public boolean isRootCACertificateDownloadedDisplayed() {
		return isElementDisplayed(rootCACertificateDownloaded);
	}
	
	public boolean isBredacumbOfRootCADisplayed() {
		return isElementDisplayed(bredacumbOfRootCA);
	}
	
	public boolean isCertificateIdLabelDisplayed() {
		return isElementDisplayed(certificateIdLabel);
	}
	
	public boolean isUploadedOnDateDisplayed() {
	    String expectedDate = PmpTestUtil.todayDateWithoutZeroPadder;
	    String xpath = "//div[contains(., 'Uploaded On') and contains(., '" + expectedDate + "')]";
	    WebElement uploadDate = driver.findElement(By.xpath(xpath));
	    return isElementDisplayed(uploadDate);
	}
	
	public boolean isIssuedToLabelDisplayed() {
		return isElementDisplayed(issuedToLabel);
	}
	
	public boolean isIssuedByLabelDisplayed() {
		return isElementDisplayed(issuedByLabel);
	}
	
	public boolean isRootCAIssuedToContextDisplayed() {
		return isElementDisplayed(rootCAIssuedToContext);
	}
	
	public boolean isIssuedByContextDisplayed() {
		return isElementDisplayed(rootCAIssuedByContext);
	}
	
	public boolean isCertificateThumbprintLabelDisplayed() {
		return isElementDisplayed(certificateThumbprintLabel);
	}
	
	public boolean isCertificateThumbprintValueDisplayed() {
		return isElementDisplayed(certificateThumbprintValue);
	}
	
	public boolean isRootCertificateTitleDisplayed() {
		return isElementDisplayed(rootCertificateTitle);
	}
	
	public boolean isValidFromDateTimeFormatValid() {	
	    String browserTime = trustCertificateContextUploadDateTime.getText();
	    DateTimeFormatter dateTimeFormatter = PmpTestUtil.dateTimeFormatter;

	    try {
	        LocalDateTime.parse(browserTime, dateTimeFormatter);
	        return true;
	    } catch (DateTimeParseException e) {
	        return false;
	    }
	}
	
	public boolean isValidToDateTimeFormatValid() {	
	    String browserTime = trustCertificateContextExpiryDateTime.getText();
	    DateTimeFormatter dateTimeFormatter = PmpTestUtil.dateTimeFormatter;

	    try {
	        LocalDateTime.parse(browserTime, dateTimeFormatter);
	        return true;
	    } catch (DateTimeParseException e) {
	        return false;
	    }
	}
	
	public void clickOnbredacumbOfRootCA() {
		 clickOnElement(bredacumbOfRootCA);
	}
	
	public boolean isRootCACertTabDisplayed() {
		return isElementDisplayed(rootCACertTab);
	}
	
	public boolean isIntermediateCACertTabDisplayed() {
		return isElementDisplayed(intermediateCACertTab);
	}
	
	public void clickOnIntermediateCACertTab() {
		 clickOnElement(intermediateCACertTab);
	}
	
	public boolean isSubtitleOfIntermediateCADisplayed() {
		return isElementDisplayed(subtitleOfIntermediateCA);
	}
	
	public boolean isViewIntermediateCADetailsPageDisplayed() {
		return isElementDisplayed(viewIntermediateCADetails);
	}
	
	public boolean isStatusValidDisplayed() {
		return isElementDisplayed(statusValid);
	}
	
	public boolean isIssuedToDetailsDisplayed() {
		return isElementDisplayed(issuedToDetails);
	}
	
	public boolean isIssuedByDetailsDisplayed() {
		return isElementDisplayed(issuedByDetails);
	}
	
	public boolean isIntCACertDownloadedSuccessMsgDisplayed() {
		return isElementDisplayed(intCACertDownloadedMsg);
	}
	
	public boolean isDownloadCertificateChainButtonDisplayed() {
		return isElementDisplayed(certificateListViewButton);
	}
	
	public void clickOnDownloadCertificateChainButton() {
		 clickOnElement(certificateListViewButton);
	}
	
	public boolean isIntCACertBreadcumbDisplayed() {
		return isElementDisplayed(intCACertBreadcumb);
	}
	
	public boolean isIntCAIssuedToContextDisplayed() {
		return isElementDisplayed(intCAIssuedToContext);
	}
	
	public boolean isIntCAIssuedByContextDisplayed() {
		return isElementDisplayed(intCAIssuedByContext);
	}
	
	public boolean isIntCACertificateTitleDisplayed() {
		return isElementDisplayed(intCACertificateTitle);
	}
	
	public boolean isPartnerAdminCertUploadTitleDisplayed() {
		return isElementDisplayed(partnerAdminCertUploadTitle);
	}
	
	public boolean isUploadInstructionMessageDisplayed() {
		return isElementDisplayed(uploadInstructionMessage);
	}
	
	public boolean isSelectPartnerDomainPlaceHolderDisplayed() {
		return isElementDisplayed(selectPartnerDomainPlaceHolder);
	}
	
	public boolean isPartnerDomainDropdownAuthDisplayed() {
		return isElementDisplayed(partnerDomainSelectorDropdownOptionAuth);
	}
	
	public boolean isUploadCertInstructionTextDisplayed() {
		return isElementDisplayed(uploadCertInstructionText);
	}
	
	public boolean isSuccessIconDisplayed() {
		return isElementDisplayed(successIcon);
	}
	
	public boolean isUploadedSuccessfullyMessageDisplayed() {
		return isElementDisplayed(uploadedSuccessfullyMessage);
	}
	
	public boolean isUploadedRootCACertificateNameDisplayed() {
		return isElementDisplayed(uploadedExpiredRootCACertificateName);
	}
	
	public boolean isCertificateDatesNotValidMessageDisplayed() {
		return isElementDisplayed(certificateDatesNotValidMessage);
	}
	
	public boolean isUploadRootCertificateFirstErrorMessageDisplayed() {
		return isElementDisplayed(uploadRootCertificateFirstErrorMessage);
	}
	
	public void clickOnCertificateClearButton() {
		 clickOnElement(certificateClearButton);
	}

}


