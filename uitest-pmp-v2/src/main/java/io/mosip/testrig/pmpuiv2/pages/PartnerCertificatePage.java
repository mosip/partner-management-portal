package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class PartnerCertificatePage extends BasePage {

	@FindBy(id = "title_back_icon")
	private WebElement titleBackButton;

	@FindBy(id = "page_title")
	private WebElement pageTitle;

	@FindBy(id = "partner_certificate_upload_btn1")
	private WebElement uploadButton;

	@FindBy(id = "upload_certificate_popup_title")
	private WebElement uploadPartnerCertificatePopUp;

	@FindBy(id = "certificate_upload_submit_btn")
	private WebElement certificateUploadSubmitButton;

	@FindBy(xpath = "//button[@disabled and text()='Submit']")
	private WebElement certificateUploadDisabledSubmitButton;

	@FindBy(id = "upload_popup_selecting_file")
	private WebElement selectingFileFetchingMsg;

	@FindBy(id = "upload_popup_cancel_file")
	private WebElement uploadPopupCancelFileButton;

	@FindBy(id = "upload_certificate_success_msg")
	private WebElement successMessage;

	@FindBy(xpath = "//p[text()='Partner certificate for Device Provider is uploaded successfully.']")
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

	@FindBy(id = "upload_popup_selecting_certificate_msg")
	private WebElement uploadPopupSelectCertificateMsg;

	@FindBy(id = "upload_trust_certificate_format_msg")
	private WebElement certificateFormatText;

	@FindBy(id = "upload_popup_certificate_format_msg")
	private WebElement partnerCertificateFormatText;

	@FindBy(id = "last_certificate_upload_date")
	private WebElement lastUploadTimeAndDate;

	@FindBy(id = "upload_certificate_warning_message")
	private WebElement reUploadCertificateWarningMessage;

	@FindBy(xpath = "//p[contains(text(), 'Please select all fields and upload')]")
	private WebElement ReUploadPartnerCertificateSubText;

	@FindBy(xpath = "//p[text()='Originally uploaded CA signed certificate downloaded successfully.']")
	private WebElement originalSignedCertDownloadedPopup;

	@FindBy(xpath = "//p[text()='MOSIP signed certificate downloaded successfully.']")
	private WebElement mosipSignedCertPopup;

	@FindBy(xpath = "//label[text()='Partner Domain Type']")
	private WebElement partnerDomainType;

	@FindBy(id = "upload_file_FILL0_wght200_GRAD0_opsz24")
	private WebElement partnerCertOverlay;

	@FindBy(id = "upload_certificate_error_msg")
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

	@FindBy(id = "partnerDomain_selector_dropdown_dropdown_btn")
	private WebElement partnerDomainSelectorDropdown;

	@FindBy(id = "partnerDomain_selector_dropdown_option1")
	private WebElement partnerDomainSelectorDropdownOptionAuth;

	@FindBy(id = "partnerDomain_selector_dropdown_option2")
	private WebElement partnerDomainSelectorDropdownOptionFtm;

	@FindBy(id = "partnerDomain_selector_dropdown_option3")
	private WebElement partnerDomainSelectorDropdownOptionDevice;

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
	private WebElement ftmCertUploadSuccessMessage;

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

	@FindBy(css = "#upload_certificate_card img")
	private WebElement uploadPopupCertificateIcon;

	@FindBy(xpath = "//h5[text()='Client.cer']")
	private WebElement uploadedCertificateFileName;

	@FindBy(id = "upload_popup_file_name")
	private WebElement uploadedCertificateFileNameLabel;

	@FindBy(id = "fetch_certificate_success_msg")
	private WebElement fetchCertificateSuccessMessage;

	@FindBy(id = "remove_certificate_card")
	private WebElement removeCertificateCard;

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

	@FindBy(id = "cert_partner_domain_filter_dropdown_btn")
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
	private WebElement breadcumbOfRootCA;

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

	@FindBy(xpath = "//h6[text()='Intermediate CA']")
	private WebElement intermediateCACertTab;

	@FindBy(xpath = "//p[contains(text(), 'List of Intermediate CA Certificates')]")
	private WebElement subtitleOfIntermediateCA;

	@FindBy(xpath = "//h1[text()='View Intermediate CA Certificate Details']")
	private WebElement viewIntermediateCADetails;

	@FindBy(xpath = "//td[text()='Valid']")
	private WebElement statusValid;

	@FindBy(xpath = "//td[@class='px-2 break-all' and text()='CN=SUBCA,OU=SUBCA,O=SUBCA,L=aa,ST=aa,C=aa']")
	private WebElement issuedToDetails;

	@FindBy(xpath = "//td[@class='px-2 break-all' and text()='CN=CA,OU=CA,O=CA,L=aa,ST=aa,C=aa']")
	private WebElement issuedByDetails;

	@FindBy(xpath = "//p[contains(text(), 'Certificate Chain of Trust for the given Intermediate CA certificate is downloaded successfully.')]")
	private WebElement intCACertDownloadedMsg;

	@FindBy(xpath = "//p[contains(normalize-space(), 'List of Intermediate CA Certificates')]")
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

	@FindBy(xpath = "//span[text()='Select partner domain']")
	private WebElement selectPartnerDomainPlaceHolder;

	@FindBy(xpath = "//h5[contains(text(), 'Please tap to select the Root CA / Intermediate CA Certificate')]")
	private WebElement uploadCertInstructionText;

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

	@FindBy(id = "root_upload_trust_certificate_btn")
	private WebElement rootUploadTrustCertificateButtonInAdmin;

	@FindBy(id = "intermediate_upload_trust_certificate_btn")
	private WebElement intermediateUploadTrustCertificateButtonInAdmin;

	@FindBy(id = "upload_trust_certificate_confirmation_header")
	private WebElement uploadedSuccessfullyMessage;

	@FindBy(id = "partnerDomain_selector_dropdown_option4")
	private WebElement partnerDomainSelectorDropdownOptionMisp;

	@FindBy(id = "upload_certificate_popup_title")
	private WebElement mispPartnerCertificatePopup;

	@FindBy(id = "upload_certificate_popup_msg")
	private WebElement uploadCertificatePopupSubtitle;

	@FindBy(id = "dashboard_ftm_chip_provider_card")
	private WebElement dashboardFtmChipProviderCardDashboard;

	@FindBy(id = "upload_certificate_success_msg")
	private WebElement certificateUploadSuccessMessage;

	@FindBy(id = "upload_certificate_popup_partner_id_field")
	private WebElement correspondingPartnerId;

	@FindBy(id = "upload_popup_partner_type_context")
	private WebElement partnerTypeContext;

	@FindBy(id = "partner_type_context")
	private WebElement listViewPartnerTypeContext;

	@FindBy(id = "upload_popup_partner_domain_type_context")
	private WebElement partnerDomainTypeContext;

	@FindBy(id = "upload_popup_partner_type_label")
	private WebElement uploadPopupPartnerTypeLabel;

	@FindBy(id = "upload_popup_partner_domain_type_label")
	private WebElement uploadPopupPartnerDomainTypeLabel;

	@FindBy(id = "upload_certificate_card")
	private WebElement uploadCertificateCard;

	public PartnerCertificatePage(WebDriver driver) {
		super(driver);
	}

	public boolean isDeviceProviderSuccessMessage() {
		return isElementDisplayed(deviceProviderSuccessMessage);
	}

	public boolean isPartnerCertificatePageDisplayed() {
		return isElementDisplayed(titleBackButton);
	}

	public boolean isCertificateListViewDisplayed() {
		return isElementDisplayed(pageTitle)
				&& getTextFromLocator(pageTitle).trim().equals(GlobalConstants.PARTNER_CERTIFICATE_LIST_PAGE_TITLE);
	}

	public boolean isUploadButtonPresent() {
		return getElementCount(By.id("partner_certificate_upload_btn1")) > 0;
	}

	public boolean isUploadButtonDisplayed() {
		return isElementDisplayed(uploadButton);
	}

	public boolean isUploadButtonEnabled() {
		return isElementEnabled(uploadButton);
	}

	public void clickOnUploadButton() {
		clickOnElement(uploadButton);
	}

	public boolean isUploadPartnerCertificatePopUpDisplayed() {
		return isElementDisplayed(uploadPartnerCertificatePopUp);
	}

	public boolean isUploadPartnerCertificatePopUpDisplayedQuick() {
		return isElementDisplayedQuick(By.id("upload_certificate_popup_title"), Duration.ofSeconds(5))
				|| isElementDisplayedQuick(By.xpath("//h3[text()='Re-Upload Partner Certificate']"),
						Duration.ofSeconds(2));
	}

	public String getUploadCertificatePopupTitle() {
		return getTextFromLocator(mispPartnerCertificatePopup).trim();
	}

	public String getUploadCertificatePopupSubtitle() {
		return getTextFromLocator(uploadCertificatePopupSubtitle).trim();
	}

	public boolean isUploadCertificatePopupSubtitleDisplayed() {
		return isElementDisplayed(uploadCertificatePopupSubtitle);
	}

	public boolean isUploadPopupSubtitleDisplayedBelowTitle() {
		return uploadCertificatePopupSubtitle.getLocation().getY() > mispPartnerCertificatePopup.getLocation().getY();
	}

	public boolean isUploadPopupPartnerTypeLabelDisplayed() {
		return isElementDisplayed(uploadPopupPartnerTypeLabel);
	}

	public String getUploadPopupPartnerTypeLabelText() {
		return getTextFromLocator(uploadPopupPartnerTypeLabel).trim();
	}

	public boolean isUploadPopupPartnerDomainTypeLabelDisplayed() {
		return isElementDisplayed(uploadPopupPartnerDomainTypeLabel);
	}

	public String getUploadPopupPartnerDomainTypeLabelText() {
		return getTextFromLocator(uploadPopupPartnerDomainTypeLabel).trim();
	}

	public boolean isPartnerTypeContextDisplayed() {
		return isElementDisplayed(partnerTypeContext);
	}

	public boolean isPartnerDomainTypeContextDisplayed() {
		return isElementDisplayed(partnerDomainTypeContext);
	}

	public boolean isPartnerTypeContextDisabled() {
		return isElementDisabled(partnerTypeContext);
	}

	public boolean isPartnerTypeFieldNonEditable() {
		boolean disabledBySelenium = isElementDisabled(partnerTypeContext);
		boolean disabledAttributePresent = partnerTypeContext.getAttribute("disabled") != null;
		boolean valueIsCredentialPartner = GlobalConstants.CREDENTIAL_PARTNER_TYPE_NAME
				.equals(getPartnerType());
		return disabledBySelenium && disabledAttributePresent && valueIsCredentialPartner;
	}

	public boolean isPartnerDomainTypeContextDisabled() {
		return isElementDisabled(partnerDomainTypeContext);
	}

	public boolean isUploadCertificateCardDisplayed() {
		return isElementDisplayed(uploadCertificateCard);
	}

	public boolean isCertificateUploadCancelButtonDisplayed() {
		return isElementDisplayed(certificateUploadCancelButton);
	}

	public boolean isCertificateUploadCancelButtonEnabled() {
		return isElementEnabled(certificateUploadCancelButton);
	}

	public boolean isCertificateUploadCancelButtonFocusable() {
		return isElementFocusable(certificateUploadCancelButton);
	}

	public boolean isCertificateUploadSubmitButtonDisplayed() {
		return isElementDisplayed(certificateUploadSubmitButton);
	}

	public boolean isCertificateUploadSubmitButtonDisabled() {
		return isElementDisabled(certificateUploadDisabledSubmitButton);
	}

	public boolean isCertificateUploadSubmitButtonEnabled() {
		return isElementEnabled(certificateUploadSubmitButton);
	}

	public boolean isCertificateUploadFetchingMsgDisplayed() {
		return isElementDisplayed(selectingFileFetchingMsg);
	}

	public String getCertificateUploadFetchingMessage() {
		return getTextFromLocator(selectingFileFetchingMsg).trim();
	}

	public boolean isCertificateFetchProgressIndicatorDisplayed() {
		return isElementDisplayedQuick(By.cssSelector("svg.animate-spin"), Duration.ofSeconds(4));
	}

	public boolean isUploadPopupCancelFileButtonDisplayed() {
		return isElementDisplayed(uploadPopupCancelFileButton);
	}

	public boolean isUploadPopupCancelFileButtonEnabled() {
		return isElementEnabled(uploadPopupCancelFileButton);
	}

	public void clickOnUploadPopupCancelFileButton() {
		clickOnElement(uploadPopupCancelFileButton);
	}

	public String getCertificateFileInputAcceptAttribute() {
		return uploadFile.getAttribute("accept");
	}

	public boolean isOnlyCerAndPemAcceptedByFileInput() {
		String accept = getCertificateFileInputAcceptAttribute();
		return accept != null && accept.contains(".cer") && accept.contains(".pem")
				&& !accept.toLowerCase().contains(".pdf") && !accept.toLowerCase().contains(".jpg");
	}

	public void uploadCertificateRootCa() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "RootCA.cer"));
	}

	public void uploadCertificateSubCa() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "IntermediateCA.cer"));
	}

	public void uploadCertificate() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "Client.cer"));
	}

	public void uploadCertificateForAnotherOrg() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "FTM_ca.cer"));
	}

	public void uploadPartnerCertificateWithMissingCa() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "MissingCaClient.cer"));
	}

	public void uploadExpiredCertificateForRootCa() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "expiredRoot.cer"));
	}

	public void clickOnSubmitButton() {
		clickOnElement(certificateUploadSubmitButton);
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

	public boolean isDownloadButtonPresent() {
		return getElementCount(By.id("download_btn1")) > 0;
	}

	// Both buttons are disabled, not hidden, for a deactivated partner.
	public boolean isDownloadButtonEnabled() {
		return isElementEnabled(downloadButton);
	}

	public boolean isPartnerCertificateReuploadButtonDisplayed() {
		return isElementDisplayed(partnerCertificateReuploadButton);
	}

	public boolean isPartnerCertificateReuploadButtonPresent() {
		return getElementCount(By.id("partner_certificate_re_upload_btn1")) > 0;
	}

	public boolean isPartnerCertificateReuploadButtonEnabled() {
		return isElementEnabled(partnerCertificateReuploadButton);
	}

	public String getPartnerCertificateReuploadButtonText() {
		return getTextFromLocator(partnerCertificateReuploadButton).trim();
	}

	public String getInvalidFormatErrorMessage() {
		return getTextFromLocator(InvalidFormatErrorPopup).trim();
	}

	public String getCertificateUploadedDateInPartnerPortal() {
		return getTextFromLocator(By.id("certificate_uploaded_date_context")).trim();
	}

	public String getCertificateExpiryDateInPartnerPortal() {
		return getTextFromLocator(By.id("certificate_expiry_date_context")).trim();
	}

	public boolean isPartnerCertificateReuploadButtonGreyedOut() {
		return getTextFromAttribute(partnerCertificateReuploadButton, GlobalConstants.CLASS)
				.contains(GlobalConstants.PARTNER_CERT_BUTTON_DISABLED_TEXT);
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
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "cert.crdownload"));
	}

	public void clickOnRemoveCertificateButton() {
		clickOnElement(removeCertificateButton);
	}

	public boolean isPartnerDomainTypeLabelDisplayed() {
		return isElementDisplayed(partnerDomainType);
	}

	public boolean isPartnerCertOverlayDisplayed() {
		return isElementDisplayed(partnerCertOverlay);
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

	public void clickOnPartnerDomainSelectorDropdownOptionAuth() {
		clickOnElement(partnerDomainSelectorDropdownOptionAuth);
	}

	public void clickOnDeviceInPartnerDomainSelectorDropdown() {
		clickOnElement(partnerDomainSelectorDropdownOptionDevice);
	}

	public void clickonSubmitButtonForAdmin() {
		clickOnElement(SubmitButtonForAdmin);
	}

	public void waitForAdminTrustCertificateReadyToSubmit() {
		waitForElementVisible(removeCertificateButton);
		waitForElementClickable(SubmitButtonForAdmin);
	}

	public void clickOnAdminCertUploadCancelButton() {
		clickOnElement(adminCertUploadCancelButton);
	}

	public boolean isAdminTrustCertificateConfirmationDisplayed() {
		return isElementDisplayedQuick(By.id("confirmation_go_back_btn"), Duration.ofSeconds(5));
	}

	public boolean isAdminTrustCertificateErrorDisplayed() {
		return isElementDisplayedQuick(By.id("upload_trust_certificate_error_msg"), Duration.ofSeconds(3));
	}

	public void clickOnGoBackAfterAdminTrustCertificateSubmit() {
		By confirmationGoBack = By.id("confirmation_go_back_btn");
		By cancelBtn = By.id("upload_trust_certificate_cancel_btn");
		By clearBtn = By.id("upload_trust_certificate_clear");
		By errorMsg = By.id("upload_trust_certificate_error_msg");
		By blockerProceed = By.id("block_messsage_proceed");

		boolean confirmationShown = false;
		long deadline = System.currentTimeMillis() + Duration.ofSeconds(40).toMillis();
		while (System.currentTimeMillis() < deadline) {
			if (isElementDisplayedQuick(confirmationGoBack, Duration.ofMillis(500))) {
				confirmationShown = true;
				break;
			}
			if (isElementDisplayedQuick(errorMsg, Duration.ofMillis(500))
					|| isElementDisplayedQuick(cancelBtn, Duration.ofMillis(500))) {
				break;
			}
		}

		if (confirmationShown) {
			clickOnGoBackButton();
			return;
		}

		io.mosip.testrig.pmpuiv2.utility.LogUtil
				.step("Trust certificate confirmation not shown after submit; clearing and cancelling upload form");
		if (isElementDisplayedQuick(errorMsg, Duration.ofSeconds(2))) {
			try {
				io.mosip.testrig.pmpuiv2.utility.LogUtil
						.step("Trust certificate upload error: " + driver.findElement(errorMsg).getText());
			} catch (Exception ignored) {
				// best-effort diagnostics only
			}
		}
		// Clear form first so Cancel does not open the unsaved-changes blocker.
		if (isElementDisplayedQuick(clearBtn, Duration.ofSeconds(3))) {
			click(clearBtn);
		}
		if (isElementDisplayedQuick(cancelBtn, Duration.ofSeconds(3))) {
			clickOnAdminCertUploadCancelButton();
		}
		if (isElementDisplayedQuick(blockerProceed, Duration.ofSeconds(5))) {
			click(blockerProceed);
		}
	}

	public void clickOnGoBackButton() {
		clickOnElement(GoBackButton);
	}

	public void clickOnSuccessMsgCloseButton() {
		clickOnElement(successMsgCloseButton);
	}

	public void clickOnPartnerDomainSelectorDropdownOptionFtm() {
		clickOnElement(partnerDomainSelectorDropdownOptionFtm);
	}

	public boolean isDashboardFtmChipProviderCardDisplayed() {
		By dashboardFtmChipProviderCard = By.id("dashboard_ftm_chip_provider_card");
		return isDisplayed(dashboardFtmChipProviderCard);
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
		if (isElementDisplayed(certificateUploadCloseButton)) {
			clickOnElement(certificateUploadCloseButton);
		}
	}

	public boolean VerifyTheStatusWithAsendingOrder() {
		WebElement first = driver
				.findElement(By.xpath("//tr[@id='ftm_list_item1']//td[contains(text(), 'Approved')]"));
		return isElementDisplayed(first);
	}

	public boolean VerifyTheStatusWithDesendingOrder() {
		WebElement first = driver.findElement(
				By.xpath("//tr[@id='ftm_list_item1']//td[contains(text(), 'Pending For Approval')]"));
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

	public String getUploadPopupSelectCertificateText() {
		return getTextFromLocator(uploadPopupSelectCertificateMsg).trim();
	}

	public String getUploadPopupCertificateFormatText() {
		return getTextFromLocator(partnerCertificateFormatText).trim();
	}

	public String getCertificateUploadSectionDisplayText() {
		return getUploadPopupSelectCertificateText() + ". " + getUploadPopupCertificateFormatText();
	}

	public boolean isCertFormatesTextDisplayed() {
		return isElementDisplayed(certificateFormatText);
	}

	public boolean isPartnercertFormatesTextDisplayed() {
		return isElementDisplayed(partnerCertificateFormatText);
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

	public boolean isSubmitButtonForAdminDisabled() {
		return isElementDisabled(SubmitButtonForAdmin);
	}

	public boolean isSubmitButtonForAdminEnabled() {
		return isElementEnabled(SubmitButtonForAdmin);
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

	public boolean isUploadPopupCertificateIconDisplayed() {
		return isElementDisplayed(uploadPopupCertificateIcon);
	}

	public boolean isUploadPopupCertificateIconEnabledAndClickable() {
		boolean iconDisplayed = isElementDisplayed(uploadPopupCertificateIcon);
		boolean cardDisplayed = isElementDisplayed(uploadCertificateCard);
		boolean cardClickable = getTextFromAttribute(uploadCertificateCard, GlobalConstants.CLASS)
				.contains("cursor-pointer");
		// fileInput uses class="hidden" — do not wait for visibility
		boolean fileInputEnabled = uploadFile.isEnabled() && uploadFile.getAttribute("disabled") == null;
		return iconDisplayed && cardDisplayed && cardClickable && fileInputEnabled;
	}

	public boolean isCertificateFileInputConfiguredForLocalFileBrowser() {
		String type = uploadFile.getAttribute("type");
		String accept = uploadFile.getAttribute("accept");
		boolean enabled = uploadFile.isEnabled() && uploadFile.getAttribute("disabled") == null;
		return "file".equalsIgnoreCase(type) && accept != null && accept.contains(".cer")
				&& accept.contains(".pem") && enabled;
	}

	public void clickOnUploadCertificateCard() {
		clickOnElement(uploadCertificateCard);
	}

	public boolean isUploadedCertificateFileNameLabelDisplayed() {
		return isElementDisplayed(uploadedCertificateFileNameLabel);
	}

	public boolean isUploadedCertificateFileNameLabelDisplayedQuick() {
		return isElementDisplayedQuick(By.id("upload_popup_file_name"), Duration.ofSeconds(3));
	}

	public boolean isFetchCertificateSuccessMessageDisplayed() {
		return isElementDisplayed(fetchCertificateSuccessMessage);
	}

	public String getFetchCertificateSuccessMessage() {
		return getTextFromLocator(fetchCertificateSuccessMessage).trim();
	}

	public boolean isRemoveCertificateCardDisplayed() {
		return isElementDisplayed(removeCertificateCard);
	}

	public boolean isLastCertificateUploadDateDisplayed() {
		return isElementDisplayed(lastUploadTimeAndDate);
	}

	public boolean isReUploadCertificateWarningMessageDisplayed() {
		return isElementDisplayed(reUploadCertificateWarningMessage);
	}

	public String getReUploadCertificateWarningMessage() {
		return getTextFromLocator(reUploadCertificateWarningMessage);
	}

	public boolean isUploadedCertificateNameDisplayed() {
		return isElementDisplayed(uploadedCertificateFileName);
	}

	public String getUploadedCertificateFileName() {
		return getTextFromLocator(uploadedCertificateFileNameLabel).trim();
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
		return isElementDisplayed(rootUploadTrustCertificateButtonInAdmin);
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
		enter(certIssuedToFilter, value);
	}

	public void enterIssuedByInFilter(String value) {
		enter(certIssuedByFilter, value);
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

	public boolean isBreadcumbOfRootCADisplayed() {
		return isElementDisplayed(breadcumbOfRootCA);
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
		String uiDateTime = trustCertificateContextUploadDateTime.getText().replaceAll("\\s+", " ").trim();

		try {
			LocalDateTime parsed = LocalDateTime.parse(uiDateTime, PmpTestUtil.browserFormatter);
			String reformatted = parsed.format(PmpTestUtil.browserFormatter);

			return uiDateTime.equals(reformatted);
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public boolean isValidToDateTimeFormatValid() {
		String uiDateTime = trustCertificateContextExpiryDateTime.getText().replaceAll("\\s+", " ").trim();

		try {
			LocalDateTime parsed = LocalDateTime.parse(uiDateTime, PmpTestUtil.browserFormatter);
			String reformatted = parsed.format(PmpTestUtil.browserFormatter);

			return uiDateTime.equals(reformatted);
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public void clickOnBreadcumbOfRootCA() {
		clickOnElement(breadcumbOfRootCA);
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

	public boolean isIntermediateUploadTrustCertificateButtonDisplayed() {
		return isElementDisplayed(intermediateUploadTrustCertificateButtonInAdmin);
	}

	public void clickOnIntCACertBreadcumb() {
		clickOnElement(intCACertBreadcumb);
	}

	public void clickOnRootUploadTrustCertificateButtonInAdmin() {
		clickOnElement(rootUploadTrustCertificateButtonInAdmin);
	}

	public void clickOnIntermediateUploadTrustCertificateButtonInAdmin() {
		clickOnElement(intermediateUploadTrustCertificateButtonInAdmin);
	}

	public void clickOnPartnerDomainSelectorDropdownOptionMisp() {
		clickOnElement(partnerDomainSelectorDropdownOptionMisp);
	}

	public boolean isMispPartnerCertificatePopupDisplayed() {
		return isElementDisplayed(mispPartnerCertificatePopup);
	}

	public void uploadDeactivateUserRootCaCert() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "deactivateUserRootCA.cer"));
	}

	public void uploadDeactivateUserIntermediateCaCert() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "deactivateUserIntermediateCA.cer"));
	}

	public void uploadDeactivateUserClientCertificate() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "deactivateUserClient.cer"));
	}

	public void uploadPolicyAdminUserRootCaCert() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "policyadminca.cer"));
	}

	public void uploadPolicyAdminUserRootSubCaCert() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "policyadminsubca.cer"));
	}

	public void uploadPolicyUserRootCaCert() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "policyUserRootCA.cer"));
	}

	public void uploadPolicyUserIntermediateCaCert() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "policyUserIntermediateCA.cer"));
	}

	public void uploadPolicyUserClientCertificate() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "policyUserClient.cer"));
	}

	public void uploadCertificateMispRootCa() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "MispRootCA.cer"));
	}

	public void uploadCertificateMispSubCa() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "MispIntermediateCA.cer"));
	}

	public void uploadCertificateMispClient() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "MipsClient.cer"));
	}

	public boolean isCertificateUploadSuccessMessageDisplayed() {
		return isElementDisplayed(certificateUploadSuccessMessage);
	}

	public void uploadExpiredCertificate() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "expiredRoot.cer"));
	}

	public boolean isCertificateExpiredErrorDisplayed() {
		return isElementDisplayed(InvalidFormatErrorPopup);
	}

	public boolean isReUploadPartnerCertificateDisplayed() {
		return isElementDisplayed(mispPartnerCertificatePopup);
	}

	public boolean isCorrespondingPartnerIdDisplayed() {
		return isElementDisplayed(correspondingPartnerId);
	}

	public String getPartnerType() {
		return getTextFromAttribute(partnerTypeContext, "value");
	}

	public String getPartnerTypeFromListView() {
		return getTextFromLocator(listViewPartnerTypeContext).trim();
	}

	public boolean isPartnerTypeFromListViewDisplayed() {
		return isElementDisplayed(listViewPartnerTypeContext);
	}

	public String getPartnerDomainType() {
		return getTextFromAttribute(partnerDomainTypeContext, "value");
	}

	public boolean isCertificateFormatTextNotEditable() {
		return isElementNotEditable(partnerCertificateFormatText);
	}

}
