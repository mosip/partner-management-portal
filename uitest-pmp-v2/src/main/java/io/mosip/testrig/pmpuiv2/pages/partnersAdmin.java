package io.mosip.testrig.pmpuiv2.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;

public class partnersAdmin extends BasePage {

	@FindBy(id = "undefined_title")
	private WebElement subTitleList;

	@FindBy(id = "page_title")
	private WebElement titlePartner;

	@FindBy(xpath = "//h5[text()='Partners']")
	private WebElement partnersTab;

	@FindBy(xpath = "//div[text()='Partner ID']")
	private WebElement partnerIdHeaderTag;

	@FindBy(xpath = "//div[text()='Partner Type']")
	private WebElement partnerTypeHeaderTag;

	@FindBy(xpath = "//div[text()='Organisation Name']")
	private WebElement organisationHeaderTag;

	@FindBy(xpath = "//div[text()='Policy Group']")
	private WebElement policyGroupHeaderTag;

	@FindBy(xpath = "//div[text()='Email Address']")
	private WebElement emailAddressHeaderTag;

	@FindBy(xpath = "//div[text()='Status']")
	private WebElement statusHeaderTag;

	@FindBy(id = "partnerList.action_header")
	private WebElement actionHeaderTag;

	@FindBy(id = "partnerId_desc_icon")
	private WebElement partnerId_desc_icon;

	@FindBy(id = "partnerId_asc_icon")
	private WebElement partnerId_asc_icon;

	@FindBy(id = "policyGroupName_desc_icon")
	private WebElement policyGroupName_desc_icon;

	@FindBy(id = "policyGroupName_asc_icon")
	private WebElement policyGroupName_asc_icon;

	@FindBy(id = "orgName_asc_icon")
	private WebElement orgName_asc_icon;

	@FindBy(id = "orgName_desc_icon")
	private WebElement orgName_desc_icon;

	@FindBy(id = "certificateUploadStatus_desc_icon")
	private WebElement certificateUploadStatus_desc_icon;

	@FindBy(id = "certificateUploadStatus_asc_icon")
	private WebElement certificateUploadStatus_asc_icon;

	@FindBy(id = "filter_btn")
	private WebElement filterbtnTrigger;

	@FindBy(xpath = "//td[normalize-space()='pmpui-auth']")
	private WebElement rowInPartnerDetailsScreen;

	@FindBy(xpath = "//h1[text()='View Partner Details']")
	private WebElement navigateToPartnerDetailsPage;

	@FindBy(id = "ftm_view_back_btn")
	private WebElement gobackButtonInPartnerDetailsPage;

	@FindBy(id = "partner_id_filter")
	private WebElement partnersIdFilter;

	@FindBy(id = "partner_type_filter_label")
	private WebElement partnersTypeFilter;

	@FindBy(xpath = "//*[@id='partner_type_filter_dropdown_btn']/span")
	private WebElement partnerTypeDropdown;

	@FindBy(id = "partner_organisation_filter")
	private WebElement partnersOrganisationFilter;

	@FindBy(id = "email_address_filter")
	private WebElement emailsAddressFilter;

	@FindBy(xpath = "//span[normalize-space()='Select Cert. Upload Status']")
	private WebElement certUploadsStatusFilter;

	@FindBy(xpath = "//button[@id='status_filter_dropdown_btn']/span")
	private WebElement statusFilters;

	@FindBy(id = "policy_group_filter")
	private WebElement policyGroupsFilter;

	@FindBy(id = "filter_reset_btn")
	private WebElement filtersResetButton;

	@FindBy(id = "partner_type_filter_option2")
	private WebElement authenticationPartner;

	@FindBy(id = "apply_filter__btn")
	private WebElement applyFiltersBtn;

	@FindBy(xpath = "//tr[@id='partner_list_item1']/td[2]")
	private WebElement authenticationPartnerCell;

	@FindBy(xpath = "//input[@placeholder='Search Partner ID']")
	private WebElement partnersIdPlaceHolder;

	@FindBy(xpath = "//input[@placeholder='Search Organisation']")
	private WebElement organisationsPlaceHolder;

	@FindBy(xpath = "//input[@placeholder='Search Policy Group']")
	private WebElement policyGroupsPlaceHolder;

	@FindBy(xpath = "//input[@placeholder='Search Full Email Address']")
	private WebElement emailsAddressPlaceHolder;

	@FindBy(id = "partner_type_filter")
	private WebElement dropDownBox;

	@FindBy(id = "partner_list_item1")
	private WebElement partnersList1;

	@FindBy(id = "status_filter_option1")
	private WebElement activatedButton;

	@FindBy(id = "page_title")
	private WebElement viewPartnersDetailsPage;

	@FindBy(id = "sub_title_btn")
	private WebElement listOfPartners;

	@FindBy(id = "partner_list_view1")
	private WebElement actionsButton;

	@FindBy(id = "partner_details_view_btn")
	private WebElement viewButtons;

	@FindBy(id = "partner_deactive_btn")
	private WebElement deactivateButtons;

	@FindBy(id = "status_filter_option2")
	private WebElement deActivatedStatusInFilters;

	@FindBy(id = "partner_list_item2")
	private WebElement deactivatedPartnerRow;

	@FindBy(id = "partnerType_asc_icon")
	private WebElement partnerTypeAscIcons;

	@FindBy(xpath = "//p[text()='No Results Found']")
	private WebElement noResultsFoundMessages;

	@FindBy(xpath = "//p[contains(text(), 'List of Partners')]")
	private WebElement tabularViewsSubtitle;

	@FindBy(xpath = "//h6[text()='Items per page']")
	private WebElement prefixOfPages;

	@FindBy(id = "sub_title_home_btn")
	private WebElement breadcrumbs;

	@FindBy(xpath = "//img[@alt='Mosip Icon']")
	private WebElement mosipIcons;

	@FindBy(id = "title_back_icon")
	private WebElement listOfPartnerBackButton;

	@FindBy(id = "hamburger_close_icon")
	private WebElement hamburgersIcon;

	@FindBy(xpath = "//p[contains(normalize-space(.), 'MOSIP - All rights reserved')]")
	private WebElement footerMosipTexts;

	@FindBy(id = "footer_documentation_link")
	private WebElement footerDocumentations;

	@FindBy(id = "footer_contact_us_link")
	private WebElement footersContactUs;

	@FindBy(id = "view_partner_details_partner_id")
	private WebElement partnerIdInViewPartnerDetailsPage;

	@FindBy(id = "view_partner_details_partner_status")
	private WebElement partnerStatusInViewPartnerPage;

	@FindBy(id = "view_partner_details_partner_created_on")
	private WebElement partnerCreatedDateInViewPartnerPage;

	@FindBy(id = "deactivate_popup_header")
	private WebElement deactivatePartnerHeader;

	@FindBy(id = "deactivate_popup_description")
	private WebElement deactivatePartnerDescription;

	@FindBy(id = "deactivate_cancel_btn")
	private WebElement deactivateCancelButton;

	@FindBy(id = "deactivate_submit_btn")
	private WebElement deactivateConfirmButton;

	@FindBy(xpath = "//tr[@id='partner_list_item2']/td[7]/div")
	private WebElement deactivateColorCodeButton;

	@FindBy(id = "view_partner_details_partner_certificate_title")
	private WebElement partnerCertificateInViewPartnerDetailsPage;

	@FindBy(id = "view_expiry_date_label")
	private WebElement expiryDateTimeInViewPartnerPage;

	@FindBy(id = "view_certificate_upload_date_label")
	private WebElement timeOfUploadInViewPartnerPage;

	@FindBy(id = "download_partner_cer_btn")
	private WebElement downloadCertificateButtonInViewPartnerPage;

	@FindBy(id = "view_partner_type_label")
	private WebElement partnerTypeInViewPartnerPage;

	@FindBy(id = "view_partner_type_context")
	private WebElement deviceProviderInViewPartnerPage;

	@FindBy(id = "original_certificate_download_partner_cer_btn")
	private WebElement originalCertificateDropdown;

	@FindBy(id = "mosip_signed_certificate_download_partner_cer_btn")
	private WebElement mosipSignedCertificateDropdown;

	@FindBy(id = "view_partner_details_success_msg")
	private WebElement successMassageInOriginalCertificate;

	@FindBy(id = "view_partner_details_success_msg")
	private WebElement successMassageInMosipSignedCertificate;

	public partnersAdmin(WebDriver driver) {
		super(driver);
	}

	public boolean isPartnerCertificateDisplayed() {
		return isElementDisplayed(partnerCertificateInViewPartnerDetailsPage);
	}

	public boolean isExpiryDateTimeDisplayed() {
		return isElementDisplayed(expiryDateTimeInViewPartnerPage);
	}

	public boolean isTimeOfUploadDisplayed() {
		return isElementDisplayed(timeOfUploadInViewPartnerPage);
	}

	public boolean isDownloadCertificateButtonDisplayed() {
		return isElementDisplayed(downloadCertificateButtonInViewPartnerPage);
	}

	public boolean isSubTitleListDisplayed() {
		return isElementDisplayed(subTitleList);

	}

	public boolean isTitlePartnerDisplayed() {
		return isElementDisplayed(titlePartner);

	}

	public void clickOnPartnersTab() {
		clickOnElement(partnersTab);

	}

	public boolean isPartnerIdHeaderTagDisplayed() {
		return isElementDisplayed(partnerIdHeaderTag);
	}

	public boolean isPartnerTypeHeaderTagDisplayed() {
		return isElementDisplayed(partnerTypeHeaderTag);
	}

	public boolean isOrganisationNameHeaderTagDisplayed() {
		return isElementDisplayed(organisationHeaderTag);
	}

	public boolean isPolicyGroupHeaderTagDisplayed() {
		return isElementDisplayed(policyGroupHeaderTag);
	}

	public boolean isEmailAddressHeaderTagDisplayed() {
		return isElementDisplayed(emailAddressHeaderTag);
	}

	public boolean isStatusHeaderTagDisplayed() {
		return isElementDisplayed(statusHeaderTag);
	}

	public boolean isActionHeaderTagDisplayed() {
		return isElementDisplayed(actionHeaderTag);
	}

	public boolean isPartnersIdDescIconDisplayed() {
		return isElementDisplayed(partnerId_desc_icon);
	}

	public boolean isPartnersIdAscIconDisplayed() {
		return isElementDisplayed(partnerId_asc_icon);
	}

	public boolean isPolicyGroupNamesDescIconDisplayed() {
		return isElementDisplayed(policyGroupName_desc_icon);
	}

	public boolean isPolicyGroupNamesAscIconDisplayed() {
		return isElementDisplayed(policyGroupName_asc_icon);
	}

	public boolean isOrganizationAscIconDisplayed() {
		return isElementDisplayed(orgName_asc_icon);
	}

	public boolean isOrganizationDescIconDisplayed() {
		return isElementDisplayed(orgName_desc_icon);
	}

	public boolean isCertificatesUploadStatusDescIconDisplayed() {
		return isElementDisplayed(certificateUploadStatus_desc_icon);
	}

	public boolean isCertificateUploadsStatusAscIconDisplayed() {
		return isElementDisplayed(certificateUploadStatus_desc_icon);
	}

	public boolean isFilterButtonsDisplayed() {
		return isElementDisplayed(filterbtnTrigger);
	}

	public void clickOnrowInPartnerDetailsScreen() {
		clickOnElement(rowInPartnerDetailsScreen);
	}

	public boolean isUserNavigatedToPartnerDetailsPage() {
		return isElementDisplayed(navigateToPartnerDetailsPage);
	}

	public void clickOngobackButtonInPartnerDetailsPage() {
		clickOnElement(gobackButtonInPartnerDetailsPage);
	}

	public void clickOnFilterButton() {
		clickOnElement(filterbtnTrigger);
	}

	public boolean isPartnersIdFilterDisplayed() {
		return isElementDisplayed(partnersIdFilter);
	}

	public boolean isPartnersTypeFilterDisplayed() {
		return isElementDisplayed(partnersTypeFilter);
	}

	public boolean isOrganisationFilterDisplayed() {
		return isElementDisplayed(partnersOrganisationFilter);
	}

	public boolean isEmailsAddressFilterDisplayed() {
		return isElementDisplayed(emailsAddressFilter);
	}

	public boolean isCertUploadsStatusFilterDisplayed() {
		return isElementDisplayed(certUploadsStatusFilter);
	}

	public boolean isStatusFiltersDisplayed() {
		return isElementDisplayed(statusFilters);
	}

	public boolean isPolicyGroupsFilterDisplayed() {
		return isElementDisplayed(policyGroupsFilter);
	}

	public void clickOnFilterResetButton() {
		clickOnElement(filtersResetButton);
	}

	public boolean isTabularFieldDisplayed() {
		return isElementDisplayed(subTitleList);
	}

	public void clickOnPartnerTypeDropdown() {
		clickOnElement(partnerTypeDropdown);
	}

	public void clickOnAuthenticationPartner() {
		clickOnElement(authenticationPartner);
	}

	public void enterOrganisationNameFilter(String organisationName) {
		enter(partnersOrganisationFilter, organisationName);
	}

	public void enterPartnerIdsFilter(String partnerId) {
		enter(partnersIdFilter, partnerId);
	}

	public void clickOnApplyFiltersBtn() {
		clickOnElement(applyFiltersBtn);
	}

	public boolean isAuthenticationPartnerCellDisplayed() {
		return isElementDisplayed(authenticationPartnerCell);
	}

	public boolean isPartnerIdSearchBarDisplayed() {
		return isElementDisplayed(partnersIdPlaceHolder);
	}

	public boolean isOrganisationSearchBarDisplayed() {
		return isElementDisplayed(organisationsPlaceHolder);
	}

	public boolean isPolicyGroupSearchBarDisplayed() {
		return isElementDisplayed(policyGroupsPlaceHolder);
	}

	public boolean isEmailAddressSearchBarDisplayed() {
		return isElementDisplayed(emailsAddressPlaceHolder);
	}

	public void clickActivatedButton() {
		clickOnElement(activatedButton);
	}

	public boolean isFiltersButtonDisabled() {
		return isElementDisabled(filterbtnTrigger);
	}

	public boolean isActivatedPartnersDisplayed() {
		return isElementDisplayed(partnersList1);
	}

	public void clickOnActivatedPartner() {
		clickOnElement(partnersList1);
	}

	public boolean isViewPartnersDetailsPageDisplayed() {
		return isElementDisplayed(viewPartnersDetailsPage);
	}

	public void clickOnlistOfPartners() {
		clickOnElement(listOfPartners);
	}

	public void clickOnActionsButton() {
		clickOnElement(actionsButton);
	}

	public boolean isViewButtonsDisplayed() {
		return isElementDisplayed(viewButtons);
	}

	public boolean isDeactivateButtonsDisplayed() {
		return isElementDisplayed(deactivateButtons);
	}

	public void clickOnStatusFilter() {
		clickOnElement(statusFilters);
	}

	public void clickOnDeActivatedStatusInFilters() {
		clickOnElement(deActivatedStatusInFilters);
	}

	public boolean isDeactivatedPartnerRowDisplayed() {
		return isElementDisplayed(deactivatedPartnerRow);
	}

	public boolean isViewButtonsEnabled() {
		return isElementEnabled(viewButtons);
	}

	public boolean isDeactivateButtonDisabled() {
		return isElementDisabled(deactivateButtons);
	}

	public void clickOnPartnerTypeAscIcons() {
		clickOnElement(partnerTypeAscIcons);
	}

	public void enterInvalidPartnerIdFilter(String invalidId) {
		enter(partnersIdFilter, invalidId);
	}

	public void enterInvalidOrganisationNameFilter(String invalidOrg) {
		enter(partnersOrganisationFilter, invalidOrg);

	}

	public void enterInvalidPolicyGroupFilter(String invalidPolicy) {
		enter(policyGroupsFilter, invalidPolicy);

	}

	public void enterInvalidEmailFilter(String invalidEmail) {
		enter(emailsAddressFilter, invalidEmail);

	}

	public boolean isNoResultsFoundsDisplayed() {
		return isElementDisplayed(noResultsFoundMessages);
	}

	public boolean isSubTitleOfTabularViewsDisplayed() {
		return isElementDisplayed(tabularViewsSubtitle);
	}

	public boolean isPrefixOfPagesDisplayed() {
		return isElementDisplayed(prefixOfPages);
	}

	public void clickOnBreadcrumb() {
		clickOnElement(breadcrumbs);
	}

	public boolean isPartnersButtonDisplayed() {
		return isElementDisplayed(partnersTab);
	}

	public void clickOnPartnersButton() {
		clickOnElement(partnersTab);
	}

	public boolean isBackButtonAccessible() {
		return isElementDisplayed(listOfPartnerBackButton);
	}

	public void clickOnListOfPartnerBackButton() {
		clickOnElement(listOfPartnerBackButton);
	}

	public boolean isBreadcrumbsDisplayed() {
		return isElementDisplayed(breadcrumbs);
	}

	public boolean isMosipIconsDisplayed() {
		return isElementDisplayed(mosipIcons);
	}

	public boolean isHamburgersIconDisplayed() {
		return isElementDisplayed(hamburgersIcon);
	}

	public boolean isFooterMosipTextsDisplayed() {
		return isElementDisplayed(footerMosipTexts);
	}

	public boolean isFooterDocumentationsDisplayed() {
		return isElementDisplayed(footerDocumentations);
	}

	public boolean isFootersContactUsDisplayed() {
		return isElementDisplayed(footersContactUs);
	}

	public void clickOnViewPartnerDetailsScreen() {
		clickOnElement(viewButtons);
	}

	public boolean isPartnerIdDisplayed() {
		return isElementDisplayed(partnerIdInViewPartnerDetailsPage);
	}

	public boolean isPartnerStatusInViewPartnerPageDisplayed() {
		return isElementDisplayed(partnerStatusInViewPartnerPage);
	}

	public boolean isPartnerCreatedDateInViewPartnerPageDisplayed() {
		return isElementDisplayed(partnerCreatedDateInViewPartnerPage);
	}

	public void clickOnDeactivateButton() {
		clickOnElement(deactivateButtons);
	}

	public boolean isDeactivatePartnerHeaderDisplayed() {
		return isElementDisplayed(deactivatePartnerHeader);
	}

	public boolean isDeactivatePartnerDescriptionDisplayed() {
		return isElementDisplayed(deactivatePartnerDescription);
	}

	public boolean isDeactivateCancelButtonDisplayed() {
		return isElementDisplayed(deactivateCancelButton);
	}

	public boolean isDeactivateConfirmButtonDisplayed() {
		return isElementDisplayed(deactivateConfirmButton);
	}

	public void clickOnConfirmButton() {
		clickOnElement(deactivateConfirmButton);
	}

	public boolean isDeactivateColorCodeButtonDisplayed() {
		return isElementDisplayed(deactivateColorCodeButton);
	}

	public boolean isPartnerCertificateInViewPartnerDetailsPageDisplayed() {
		return isElementDisplayed(partnerCertificateInViewPartnerDetailsPage);
	}

	public boolean isPartnerTypeInViewPartnerPageDisplayed() {
		return isElementDisplayed(partnerTypeInViewPartnerPage);
	}

	public boolean isDeviceProviderInViewPartnerPageDisplayed() {
		return isElementDisplayed(deviceProviderInViewPartnerPage);
	}

	public void clickOnDownloadCertificateButtonInViewPartnerPage() {
		clickOnElement(downloadCertificateButtonInViewPartnerPage);
	}

	public boolean isOriginalCertificateDropdownDisplayed() {
		return isElementDisplayed(originalCertificateDropdown);
	}

	public boolean isMosipSignedCertificateDropdownDisplayed() {
		return isElementDisplayed(mosipSignedCertificateDropdown);
	}

	public void clickOnOriginnalCertificateInViewPartnerPage() {
		clickOnElement(originalCertificateDropdown);
	}

	public void clickOnMosipSignedCertificateInViewPartnerPage() {
		clickOnElement(mosipSignedCertificateDropdown);
	}

	public boolean isSuccessMassageInOriginalCertificateDisplayed() {
		return isElementDisplayed(successMassageInOriginalCertificate);
	}

	public boolean isSuccessMassageInMosipSignedCertificateDisplayed() {
		return isElementDisplayed(successMassageInMosipSignedCertificate);
	}

	public boolean isGobackButtonInViewPatnerPageDisplayed() {
		return isElementDisplayed(gobackButtonInPartnerDetailsPage);
	}

}
