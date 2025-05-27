package io.mosip.testrig.pmpuiv2.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
	
}
