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
	private WebElement partnerId_desc_icon_trigger;

	@FindBy(id = "partnerId_asc_icon")
	private WebElement partnerId_asc_icon_trigger;

	@FindBy(id = "policyGroupName_desc_icon")
	private WebElement policyGroupName_desc_icon_trigger;

	@FindBy(id = "policyGroupName_asc_icon")
	private WebElement policyGroupName_asc_icon_trigger;

	@FindBy(id = "orgName_asc_icon")
	private WebElement orgName_asc_icon_trigger;

	@FindBy(id = "orgName_desc_icon")
	private WebElement orgName_desc_icon_trigger;

	@FindBy(id = "certificateUploadStatus_desc_icon")
	private WebElement certificateUploadStatus_desc_icon_trigger;

	@FindBy(id = "certificateUploadStatus_asc_icon")
	private WebElement certificateUploadStatus_asc_icon_trigger;

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

	@FindBy(xpath = "//*[@id='status_filter_dropdown_btn']/span")
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
	private WebElement partnerType_asc_icons;

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

	public partnersAdmin(WebDriver driver) {
		super(driver);
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
		return isElementDisplayed(partnerId_desc_icon_trigger);
	}

	public boolean isPartnersIdAscIconDisplayed() {
		return isElementDisplayed(partnerId_asc_icon_trigger);
	}

	public boolean isPolicyGroupNamesDescIconDisplayed() {
		return isElementDisplayed(policyGroupName_desc_icon_trigger);
	}

	public boolean isPolicyGroupNamesAscIconDisplayed() {
		return isElementDisplayed(policyGroupName_asc_icon_trigger);
	}

	public boolean isOrganizationAscIconDisplayed() {
		return isElementDisplayed(orgName_asc_icon_trigger);
	}

	public boolean isOrganizationDescIconDisplayed() {
		return isElementDisplayed(orgName_desc_icon_trigger);
	}

	public boolean isCertificatesUploadStatusDescIconDisplayed() {
		return isElementDisplayed(certificateUploadStatus_desc_icon_trigger);
	}

	public boolean isCertificateUploadsStatusAscIconDisplayed() {
		return isElementDisplayed(certificateUploadStatus_desc_icon_trigger);
	}

	public boolean isFilterButtonsDisplayed() {
		return isElementDisplayed(filterbtnTrigger);
	}

	public void clickOnrowInPartnerDetailsScreen() {
		rowInPartnerDetailsScreen.click();
	}

	public boolean isUserNavigatedToPartnerDetailsPage() {
		return navigateToPartnerDetailsPage.isDisplayed();
	}

	public void clickOngobackButtonInPartnerDetailsPage() {
		gobackButtonInPartnerDetailsPage.click();
	}

	public void clickOnFilterButton() {
		filterbtnTrigger.click();
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
		partnerTypeDropdown.click();
	}

	public void clickOnAuthenticationPartner() {
		authenticationPartner.click();
	}

	public void enterOrganisationName(String organisationName) {
		partnersOrganisationFilter.clear();
		partnersOrganisationFilter.sendKeys("mosip");
	}

	public void enterPartnerIds(String partnerId) {
		enter(partnersIdFilter, partnerId);
	}

	public void clickOnApplyFiltersBtn() {
		clickOnElement(applyFiltersBtn);
	}

	public boolean isAuthenticationPartnerCellDisplayed() {
		return isElementDisplayed(authenticationPartnerCell);
	}

	public boolean isPartnerIdSearchBar() {
		return partnersIdPlaceHolder.getTagName().equals("input")
				&& partnersIdPlaceHolder.getAttribute("type").equals("text");
	}

	public boolean isOrganisationSearchBar() {
		return organisationsPlaceHolder.getTagName().equals("input")
				&& organisationsPlaceHolder.getAttribute("type").equals("text");
	}

	public boolean isPolicyGroupsSearchBar() {
		return policyGroupsPlaceHolder.getTagName().equals("input")
				&& policyGroupsPlaceHolder.getAttribute("type").equals("text");
	}

	public boolean isEmailAddressSearchBar() {
		return emailsAddressPlaceHolder.getTagName().equals("input")
				&& emailsAddressPlaceHolder.getAttribute("type").equals("text");
	}

	public void clickActivatedButton() {
		activatedButton.click();
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
		statusFilters.click();
	}

	public void clickOnDeActivatedStatusInFilters() {
		deActivatedStatusInFilters.click();
	}

	public boolean isDeactivatedPartnerRowDisplayed() {
		return deactivatedPartnerRow.isDisplayed();
	}

	public boolean isViewButtonsEnabled() {
		return isElementEnabled(viewButtons);
	}

	public boolean isDeactivateButtonDisabled() {
		return !deactivateButtons.isEnabled();
	}

	public void clickOnPartnerTypeAscIcons() {
		clickOnElement(partnerType_asc_icons);
	}

	public boolean isRowDisplayed() {
		return rowInPartnerDetailsScreen.isDisplayed();
	}

	public void enterInvalidPartnerId(String invalidId) {
		enter(partnersIdFilter, invalidId);
	}

	public void enterInvalidOrganisationName(String invalidOrg) {
		enter(partnersOrganisationFilter, invalidOrg);

	}

	public void enterInvalidPolicyGroup(String invalidPolicy) {
		enter(policyGroupsFilter, invalidPolicy);

	}

	public void enterInvalidEmail(String invalidEmail) {
		enter(emailsAddressFilter, invalidEmail);

	}

	public boolean isNoResultsFoundsDisplayed() {
		return noResultsFoundMessages.isDisplayed();
	}

	public boolean isSubTitleOfTabularViewsDisplayed() {
		return isElementDisplayed(tabularViewsSubtitle);
	}

	public boolean isPrefixOfPagesDisplayed() {
		return isElementDisplayed(prefixOfPages);
	}

	public void clickOnBreadcrumb() {
		breadcrumbs.click();
	}

	public boolean isPartnersButtonDisplayed() {
		return isElementDisplayed(partnersTab);
	}

	public void clickOnPartnersButton() {
		partnersTab.click();
	}

	public boolean isBackButtonAccessible() {
		return isElementDisplayed(listOfPartnerBackButton);
	}

	public void clickOnListOfPartnerBackButton() {
		clickOnElement(listOfPartnerBackButton);
	}

	public boolean isBreadcrumbsDisplayed() {
		return breadcrumbs.isDisplayed();
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
		viewButtons.click();
	}

}
