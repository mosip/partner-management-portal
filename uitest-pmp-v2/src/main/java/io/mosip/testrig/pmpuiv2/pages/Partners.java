package io.mosip.testrig.pmpuiv2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;

public class Partners extends BasePage {

	@FindBy(xpath = "//h1[text()='Partners']")
	private WebElement partnersPageTitle;

	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;

	@FindBy(xpath = "//p(contains(text(), 'List of Partners'")
	private WebElement tabularViewSubtitle;

	@FindBy(xpath = "//div[text()='Partner ID']")
	private WebElement partnerIdHeader;

	@FindBy(xpath = "//div[text()='Partner Type']")
	private WebElement partnerTypeHeader;

	@FindBy(xpath = "//div[text()='Organisation']")
	private WebElement organisationHeader;

	@FindBy(xpath = "//div[text()='Policy Group']")
	private WebElement policyGroupHeader;

	@FindBy(xpath = "//div[text()='Email Address']")
	private WebElement emailAddressHeader;

	@FindBy(xpath = "//h5[normalize-space()='Cert. Upload Status']")
	private WebElement certUploadStatusHeader;

	@FindBy(xpath = "//div[text()='Status']")
	private WebElement statusHeader;

	@FindBy(xpath = "//div[text()='Action']")
	private WebElement actionHeader;

	@FindBy(id = "partnerId_desc_icon")
	private WebElement partnerId_desc_icon;

	@FindBy(id = "partnerId_asc_icon")
	private WebElement partnerId_asc_icon;

	@FindBy(id = "partnerType_desc_icon")
	private WebElement partnerType_desc_icon;

	@FindBy(id = "partnerType_asc_icon")
	private WebElement partnerType_asc_icon;

	@FindBy(id = "orgName_asc_icon")
	private WebElement orgName_asc_icon;

	@FindBy(id = "orgName_desc_icon")
	private WebElement orgName_desc_icon;

	@FindBy(id = "policyGroupName_desc_icon")
	private WebElement policyGroupName_desc_icon;

	@FindBy(id = "policyGroupName_asc_icon")
	private WebElement policyGroupName_asc_icon;

	@FindBy(id = "certificateUploadStatus_desc_icon")
	private WebElement certificateUploadStatus_desc_icon;

	@FindBy(id = "certificateUploadStatus_asc_icon")
	private WebElement certificateUploadStatus_asc_icon;

	@FindBy(id = "status_asc_icon")
	private WebElement statusAscIcon;

	@FindBy(id = "status_desc_icon")
	private WebElement statusDescIcon;

	@FindBy(id = "filter_btn")
	private WebElement filterbtn;

	@FindBy(id = "partner_id_filter")
	private WebElement partnerIdFilter;

	@FindBy(id = "policy_group_filter")
	private WebElement policyGroupFilter;

	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilterBtn;

	@FindBy(id = "partner_list_item1")
	private WebElement partnerList1;

	@FindBy(id = "status_filter")
	private WebElement statusFilter;

	@FindBy(id = "status_filter_option1")
	private WebElement activatedStatusInFilter;

	@FindBy(id = "status_filter_option2")
	private WebElement deActivatedStatusInFilter;

	@FindBy(id = "partner_list_view1")
	private WebElement actionButton;

	@FindBy(id = "partner_details_view_btn")
	private WebElement viewButton;

	@FindBy(id = "partner_deactive_btn")
	private WebElement deactivateButton;

	@FindBy(xpath = "//h1[text()='View Partner Details']")
	private WebElement viewPartnerDetailsPage;

	@FindBy(id = "ftm_view_back_btn")
	private WebElement detailsPageGoBackButton;

	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;

	@FindBy(xpath = "//div[text()='Deactivated']")
	private WebElement deactivatedStatus;

	@FindBy(xpath = "//div[text()='Activated']")
	private WebElement ActivatedStatus;

	@FindBy(id = "partner_type_filter")
	private WebElement partnerTypeFilter;

	@FindBy(id = "partner_organisation_filter")
	private WebElement partnerOrganisationFilter;

	@FindBy(id = "cert_upload_status_filter")
	private WebElement certUploadStatusFilter;

	@FindBy(id = "email_address_filter")
	private WebElement emailAddressFilter;

	@FindBy(xpath = "//p[contains(text(), 'Do you want to deactivate partner')]")
	private WebElement partnerDeactivatePopup;

	@FindBy(xpath = "//p[text()='No Results Found']")
	private WebElement noResultsFound;

	@FindBy(xpath = "//h6[text()='Items per page']")
	private WebElement prefixOfPage;

	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;

	@FindBy(xpath = "//input[@placeholder='Search Partner ID']")
	private WebElement partnerIdPlaceHolder;

	@FindBy(xpath = "//input[@placeholder='Search Organisation']")
	private WebElement organisationPlaceHolder;

	@FindBy(xpath = "//input[@placeholder='Search Policy Group']")
	private WebElement policyGroupPlaceHolder;

	@FindBy(xpath = "//input[@placeholder='Search Full Email Address']")
	private WebElement emailAddressPlaceHolder;

	@FindBy(xpath = "//span[text()='Select Partner Type']")
	private WebElement selectPartnerTypePlaceHolder;

	@FindBy(xpath = "//span[text()='Select Cert. Upload Status']")
	private WebElement certUploadStatusPlaceHolder;

	@FindBy(xpath = "//span[text()='Select Status']")
	private WebElement statusPlaceHolder;

	@FindBy(xpath = "//h6[text()='Partner Certificate']")
	private WebElement titleOfPartnerCertificateInPartnerDetails;

	@FindBy(xpath = "//p[text()='Partner Type']")
	private WebElement partnerTypeLabelInCertificate;	

	@FindBy(xpath = "//h6[text()='Auth_Partner']")
	private WebElement partnerTypeContextInPartnerDetails;

	@FindBy(xpath = "//p[text()='Expiry date & time']")
	private WebElement expiryDateTimeLabelInCertificate;

	@FindBy(xpath = "//p[text()='Time of Upload']")
	private WebElement uploadTimeLabelInCertificate;

	@FindBy(id = "download_partner_cer_btn")
	private WebElement downloadPartnerCertBtn;

	@FindBy(id = "original_certificate_download_partner_cer_btn")
	private WebElement originalCertificateDownloadBtn;

	@FindBy(id = "mosip_signed_certificate_download_partner_cer_btn")
	private WebElement mosipSignedCertificateDownloadBtn;

	@FindBy(xpath = "//p[text()='Original Certificate downloaded successfully.']")
	private WebElement originalCertificateSuccessMsg;

	@FindBy(xpath = "//p[text()='MOSIP signed certificate downloaded successfully.']")
	private WebElement mosipSignedCertificateSuccessMsg;

	@FindBy(xpath = "//div[text()='Created On']")
	private WebElement createdOnLabel;

	@FindBy(xpath = "//img[@alt='Mosip Icon']")
	private WebElement mosipIcon;

	@FindBy(id = "hamburger_close_icon")
	private WebElement hamburgerIcon;

	@FindBy(xpath = "//p[contains(normalize-space(.), 'MOSIP - All rights reserved')]")
	private WebElement footerMosipText;

	@FindBy(id = "footer_documentation_link")
	private WebElement footerDocumentation;

	@FindBy(id = "footer_contact_us_link")
	private WebElement footerContactUs;

	public Partners(WebDriver driver) {
		super(driver);
	}

	public boolean isPartnersPageTitleDisplayed() {
		return isElementDisplayed(partnersPageTitle);
	}

	public boolean isHomeButtonDisplayed() {
		return isElementDisplayed(homeButton);
	}

	public boolean isSubTitleOfTabularViewDisplayed() {
		return isElementDisplayed(tabularViewSubtitle);
	}

	public boolean isPartnerIdHeaderDisplayed() {
	    return isElementDisplayed(partnerIdHeader);
	}

	public boolean isPartnerTypeHeaderDisplayed() {
	    return isElementDisplayed(partnerTypeHeader);
	}

	public boolean isOrganisationNameHeaderDisplayed() {
	    return isElementDisplayed(organisationHeader);
	}

	public boolean isPolicyGroupHeaderDisplayed() {
	    return isElementDisplayed(policyGroupHeader);
	}

	public boolean isStatusHeaderDisplayed() {
	    return isElementDisplayed(statusHeader);
	}

	public boolean isActionHeaderDisplayed() {
	    return isElementDisplayed(actionHeader);
	}

	public boolean isCertUploadStatusHeaderDisplayed() {
	    return isElementDisplayed(certUploadStatusHeader);
	}

	public boolean isEmailAddressHeaderDisplayed() {
	    return isElementDisplayed(emailAddressHeader);
	}

	public boolean isPartnerIdDescIconDisplayed() {
		return isElementDisplayed(partnerId_desc_icon);
	}

	public boolean isPartnerIdAscIconDisplayed() {
		return isElementDisplayed(partnerId_asc_icon);
	}

	public boolean isPolicyGroupNameDescIconDisplayed() {
		return isElementDisplayed(policyGroupName_desc_icon);
	}

	public boolean isPolicyGroupNameAscIconDisplayed() {
		return isElementDisplayed(policyGroupName_asc_icon);
	}

	public boolean isPartnerTypeDescIconDisplayed() {
		return isElementDisplayed(partnerType_desc_icon);
	}

	public boolean isPartnerTypeAscIconDisplayed() {
	    return isElementDisplayed(partnerType_asc_icon);
	}

	public boolean isCertificateUploadStatusDescIconDisplayed() {
	    return isElementDisplayed(certificateUploadStatus_desc_icon);
	}

	public boolean isCertificateUploadStatusAscIconDisplayed() {
	    return isElementDisplayed(certificateUploadStatus_asc_icon);
	}

	public boolean isStatusDescISconDisplayed() {
		return isElementDisplayed(statusDescIcon);
	}

	public boolean isStatusAscIconDisplayed() {
		return isElementDisplayed(statusAscIcon);
	}

	public void clickOnFilterbtn() {
		 clickOnElement(filterbtn);
	}

	public void enterPartnerIdInFilter(String value) {
		 enter(partnerIdFilter,value);
	}

	public void enterPolicyGroupInFilter(String value) {
		 enter(policyGroupFilter,value);
	}

	public void clickOnApplyFilterBtn() {
		 clickOnElement(applyFilterBtn);
	}

	public boolean isActivatedPartnerDisplayed() {
		return isElementDisplayed(partnerList1);
	}

	public boolean isDeactivatedPartnerDisplayed() {
		return isElementDisplayed(partnerList1);
	}

	public void clickOnActivatedPartner() {
		 clickOnElement(partnerList1);
	}

	public void clickOnActionButton() {
		 clickOnElement(actionButton);
	}

	public void selectActivatedStatusInFilter() {
		 clickOnElement(statusFilter);
		 clickOnElement(activatedStatusInFilter);
	}

	public void selectDeactivatedStatusInFilter() {
		 clickOnElement(statusFilter);
		 clickOnElement(deActivatedStatusInFilter);
	}

	public boolean isViewButtonDisplayed() {
		return isElementDisplayed(viewButton);
	}

	public boolean isViewButtonEnabled() {
		return isElementEnabled(viewButton);
	}

	public boolean isDeactivateButtonDisplayed() {
		return isElementDisplayed(deactivateButton);
	}

	public boolean isViewPartnerDetailsPageDisplayed() {
		return isElementDisplayed(viewPartnerDetailsPage);
	}

	public void clickOnDetailsPageGoBackButton() {
		 clickOnElement(detailsPageGoBackButton);
	}

	public void clickOnFilterResetButton() {
		 clickOnElement(filterResetButton);
	}

	public boolean isDeactivatedStatusDisplayed() {
		return isElementDisplayed(deactivatedStatus);
	}

	public boolean isActivatedStatusDisplayed() {
		return isElementDisplayed(ActivatedStatus);
	}

	public void clickOnActivatedStatus() {
		 clickOnElement(ActivatedStatus);
	}

	public boolean isPartnerIdFilterDisplayed() {
		return isElementDisplayed(partnerIdFilter);
	}

	public boolean isPolicyGroupFilterDisplayed() {
		return isElementDisplayed(policyGroupFilter);
	}

	public boolean isStatusFilterDisplayed() {
		return isElementDisplayed(statusFilter);
	}

	public boolean isOrganisationFilterDisplayed() {
		return isElementDisplayed(partnerOrganisationFilter);
	}

	public boolean isPartnerTypeFilterDisplayed() {
		return isElementDisplayed(partnerTypeFilter);
	}

	public boolean isCertUploadStatusFilterDisplayed() {
		return isElementDisplayed(certUploadStatusFilter);
	}

	public boolean isEmailAddressFilterDisplayed() {
		return isElementDisplayed(emailAddressFilter);
	}

	public boolean isFilterResetButtonEnabled() {
		return isElementEnabled(filterResetButton);
	}

	public boolean isFilterButtonDisbaled() {
		return isElementDisabled(filterbtn);
	}

	public void clickOnDeactivateButton() {
		 clickOnElement(deactivateButton);
	}

	public boolean isPartnerDeactivatePopupDisplayed() {
		return isElementDisplayed(partnerDeactivatePopup);
	}

	public void clickOnPartnerIdDescIcon() {
		 clickOnElement(partnerId_desc_icon);
	}

	public void clickOnPartnerIdAscIcon() {
		 clickOnElement(partnerId_asc_icon);
	}

	public void clickOnPolicyGroupNameDescIcon() {
		 clickOnElement(policyGroupName_desc_icon);
	}

	public void clickOnPolicyGroupNameAscIcon() {
		 clickOnElement(policyGroupName_asc_icon);
	}

	public void clickOnPartnerTypeDescIcon() {
		 clickOnElement(partnerType_desc_icon);
	}

	public void clickOnPartnerTypeAscIcon() {
		 clickOnElement(partnerType_asc_icon);
	}

	public void clickOnCertificateUploadStatusDescIcon() {
		 clickOnElement(certificateUploadStatus_desc_icon);
	}

	public void clickOnCertificateUploadStatusAscIcon() {
		 clickOnElement(certificateUploadStatus_asc_icon);
	}

	public void clickOnStatusDescIcon() {
		 clickOnElement(statusDescIcon);
	}

	public void clickOnStatusAscIcon() {
		 clickOnElement(statusAscIcon);
	}

	public boolean isNoResultsFoundDisplayed() {
		return isElementDisplayed(noResultsFound);
	}

	public boolean isPrefixOfPageDisplayed() {
		return isElementDisplayed(prefixOfPage);
	}

	public void clickOnTitleBackIcon() {
		 clickOnElement(titleBackIcon);
	}

	public boolean isPartnerIdPlaceHolderDisplayed() {
		return isElementDisplayed(partnerIdPlaceHolder);
	}

	public boolean isOrganisationPlaceHolderDisplayed() {
		return isElementDisplayed(organisationPlaceHolder);
	}

	public boolean isPolicyGroupPlaceHolderDisplayed() {
		return isElementDisplayed(policyGroupPlaceHolder);
	}

	public boolean isEmailAddressPlaceHolderDisplayed() {
		return isElementDisplayed(emailAddressPlaceHolder);
	}

	public boolean isPartnerTypePlaceHolderDisplayed() {
		return isElementDisplayed(selectPartnerTypePlaceHolder);
	}

	public boolean isCertUploadStatusPlaceHolderDisplayed() {
		return isElementDisplayed(certUploadStatusPlaceHolder);
	}

	public boolean isStatusPlaceHolderDisplayed() {
		return isElementDisplayed(statusPlaceHolder);
	}

	public boolean isTitleOfPartnerCertificateInPartnerDetailsDisplayed() {
		return isElementDisplayed(titleOfPartnerCertificateInPartnerDetails);
	}

	public boolean isExpiryDateTimeLabelInCertificateDisplayed() {
		return isElementDisplayed(expiryDateTimeLabelInCertificate);
	}

	public boolean isUploadTimeLabelInCertificateDisplayed() {
		return isElementDisplayed(uploadTimeLabelInCertificate);
	}

	public boolean isDownloadPartnerCertBtnEnabled() {
		return isElementEnabled(downloadPartnerCertBtn);
	}

	public boolean isOriginalCertificateDownloadBtnEnabled() {
		return isElementEnabled(originalCertificateDownloadBtn);
	}

	public boolean isMosipSignedCertificateDownloadBtnEnabled() {
		return isElementEnabled(mosipSignedCertificateDownloadBtn);
	}

	public void clickOnDownloadPartnerCertBtn() {
		 clickOnElement(downloadPartnerCertBtn);
	}

	public void clickOnOriginalCertificateDownloadBtn() {
		 clickOnElement(originalCertificateDownloadBtn);
	}

	public void clickOnMosipSignedCertificateDownloadBtn() {
		 clickOnElement(mosipSignedCertificateDownloadBtn);
	}

	public boolean isOriginalCertificateSuccessMsgDisplayed() {
		return isElementDisplayed(originalCertificateSuccessMsg);
	}

	public boolean isMosipSignedCertificateSuccessMsgDisplayed() {
		return isElementDisplayed(mosipSignedCertificateSuccessMsg);
	}

	public void clickOnViewButton() {
		 clickOnElement(viewButton);
	}

	public boolean isDownloadPartnerCertBtnDisabled() {
		return isElementDisabled(downloadPartnerCertBtn);
	}

	public boolean isCreatedOnLabelDisplayed() {
		return isElementDisplayed(createdOnLabel);
	}

	public boolean isCreatedDateDisplayed() {
		WebElement createdDate = driver
				.findElement(By.xpath("//div[text()='Created On " + PmpTestUtil.todayDateWithoutZeroPadder + "']"));
		return isElementDisplayed(createdDate);
	}

	public boolean isMosipIconDisplayed() {
		return isElementDisplayed(mosipIcon);
	}

	public boolean isHamburgerIconDisplayed() {
		return isElementDisplayed(hamburgerIcon);
	}

	public boolean isFooterMosipTextDisplayed() {
		return isElementDisplayed(footerMosipText);
	}

	public boolean isFooterDocumentationDisplayed() {
		return isElementDisplayed(footerDocumentation);
	}

	public boolean isFooterContactUsDisplayed() {
		return isElementDisplayed(footerContactUs);
	}

}