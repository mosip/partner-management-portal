package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;
import io.mosip.testrig.pmpuiv2.utility.WaitUtil;

public class PartnerAdminPage extends BasePage {

	private static final Duration SHORT_ABSENCE_TIMEOUT = Duration.ofSeconds(3);
	private static final Duration LIST_LOAD_TIMEOUT = Duration.ofSeconds(20);
	private static final int POPUP_CENTRING_TOLERANCE_PX = 2;
	private static final int STALE_READ_RETRY = 3;

	@FindBy(id = "undefined_title")
	private WebElement subTitleList;

	@FindBy(id = "page_title")
	private WebElement titlePartner;

	@FindBy(id = "dashboard_partner_card_header")
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
	private WebElement partnerIdDescendingIcon;

	@FindBy(id = "partnerId_asc_icon")
	private WebElement partnerIdAscendingIcon;

	@FindBy(id = "policyGroupName_desc_icon")
	private WebElement policyGroupNameDescendingIcon;

	@FindBy(id = "policyGroupName_asc_icon")
	private WebElement policyGroupNameAscendingIcon;

	@FindBy(id = "orgName_asc_icon")
	private WebElement orgNameAscendingIcon;

	@FindBy(id = "orgName_desc_icon")
	private WebElement orgNameDescendingIcon;

	@FindBy(id = "certificateUploadStatus_desc_icon")
	private WebElement certificateUploadStatusDescIcon;

	@FindBy(id = "certificateUploadStatus_asc_icon")
	private WebElement certificateUploadStatusAscIcon;

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

	@FindBy(xpath = "//button[@id='partner_type_filter_dropdown_btn']/span")
	private WebElement partnerTypeDropdown;

	@FindBy(id = "partner_organisation_filter")
	private WebElement partnersOrganisationFilter;

	@FindBy(id = "email_address_filter")
	private WebElement emailsAddressFilter;

	@FindBy(id = "email_address_filter_info")
	private WebElement emailAddressFilterInfoIcon;

	@FindBy(id = "email_address_filter_info_info_description")
	private WebElement emailAddressFilterInfoDescription;

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
	private WebElement firstRowPartnerTypeCell;

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
	private WebElement listOfPartnerPageRow;

	@FindBy(id = "partner_list_item1")
	private WebElement listOfPartnerRowGreyedOut;

	@FindBy(id = "status_filter_option1")
	private WebElement activatedButton;

	@FindBy(xpath = "//button[starts-with(@id,'partner_type_filter_option')]")
	private List<WebElement> partnerTypeFilterOptions;

	@FindBy(xpath = "//button[starts-with(@id,'status_filter_option')]")
	private List<WebElement> statusFilterOptions;

	@FindBy(id = "page_title")
	private WebElement viewPartnersDetailsPage;

	@FindBy(id = "sub_title_btn")
	private WebElement listOfPartners;

	@FindBy(id = "partner_list_view1")
	private WebElement actionsButton;

	@FindBy(id = "partner_list_view1")
	private WebElement actionButtonInActivatedPartner;

	@FindBy(id = "partner_details_view_btn")
	private WebElement viewButtons;

	@FindBy(id = "partner_details_view_btn")
	private WebElement viewButtonsInListOfPartnersPage;

	@FindBy(id = "view_partner_details_org_name_label")
	private WebElement organisationNameInViewDetailsPage;

	@FindBy(xpath = "//tr[@id=\"partner_list_item4\"]/td[7]/div")
	private WebElement disabledDeactivateButtonInListOfPartner;

	@FindBy(id = "partner_deactive_btn")
	private WebElement deactivateButtons;

	@FindBy(xpath = "//p[@id='partner_deactive_btn']/parent::div")
	private WebElement deactivateOptionWrapper;

	@FindBy(id = "partner_list_item1")
	private WebElement firstPartnerRow;

	@FindBy(id = "status_filter_option2")
	private WebElement deactivatedStatusInFilters;

	@FindBy(id = "partner_list_item2")
	private WebElement deactivatedPartnerRow;

	@FindBy(id = "partnerType_asc_icon")
	private WebElement partnerTypeAscendingIcons;

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

	@FindBy(id = "title_back_icon")
	private WebElement listOfViewPartnerBackButton;

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

	@FindBy(xpath = "//td[contains(text(),'Authentication Partner')]")
	private WebElement authPartnerId;

	@FindBy(id = "view_partner_details_partner_certificate_title")
	private WebElement partnerCertificateInViewPartnerDetailsPage;

	@FindBy(id = "view_expiry_date_label")
	private WebElement expiryDateTimeInViewPartnerPage;

	@FindBy(id = "view_certificate_upload_date_label")
	private WebElement timeOfUploadInViewPartnerPage;

	@FindBy(id = "download_partner_cer_btn")
	private WebElement downloadCertificateButtonInViewPartnerPage;

	@FindBy(id = "download_partner_cer_btn")
	private WebElement downloadCertificateButtonDisabledStateInViewPartnerPage;

	// The banner whose background encodes the partner's active state on this screen.
	@FindBy(xpath = "//h6[@id='view_partner_details_partner_certificate_title']"
			+ "/ancestor::div[contains(@class,'justify-between')][1]")
	private WebElement partnerCertificateSectionInViewPartnerPage;

	@FindBy(id = "view_partner_type_label")
	private WebElement partnerTypeInViewPartnerPage;

	@FindBy(id = "view_partner_type_context")
	private WebElement deviceProviderInViewPartnerPage;

	@FindBy(id = "view_partner_details_partner_type_context")
	private WebElement viewPartnerDetailsPartnerType;

	@FindBy(id = "original_certificate_download_partner_cer_btn")
	private WebElement originalCertificateDropdown;

	@FindBy(id = "mosip_signed_certificate_download_partner_cer_btn")
	private WebElement mosipSignedCertificateDropdown;

	@FindBy(id = "view_partner_details_success_msg")
	private WebElement successMassageInOriginalCertificate;

	@FindBy(id = "view_partner_details_success_msg")
	private WebElement successMessageInMosipSignedCertificate;

	@FindBy(id = "partner_list_item6")
	private WebElement nonEditableInListOfPartner;

	@FindBy(id = "partnerList.certUploadStatus_header")
	private WebElement certUploadStatusHeader;

	@FindBy(id = "partner_upload_certificate_btn")
	private WebElement uploadCertificateOption;

	@FindBy(xpath = "//tr[@id='partner_list_item1']/td[1]")
	private WebElement firstRowPartnerIdCell;

	@FindBy(xpath = "//tr[@id='partner_list_item1']/td[3]")
	private WebElement firstRowOrganisationCell;

	@FindBy(xpath = "//tr[@id='partner_list_item1']/td[4]")
	private WebElement firstRowPolicyGroupCell;

	@FindBy(xpath = "//tr[@id='partner_list_item1']/td[5]")
	private WebElement firstRowEmailCell;

	@FindBy(xpath = "//tr[@id='partner_list_item1']/td[7]/div")
	private WebElement firstRowStatusBadge;

	@FindBy(xpath = "//tr[starts-with(@id,'partner_list_item')]")
	private List<WebElement> partnerRows;

	@FindBy(xpath = "//tr[starts-with(@id,'partner_list_item')]/td[1]")
	private List<WebElement> partnerIdColumnCells;

	@FindBy(xpath = "//tr[starts-with(@id,'partner_list_item')]/td[2]")
	private List<WebElement> partnerTypeColumnCells;

	@FindBy(xpath = "//tr[starts-with(@id,'partner_list_item')]/td[5]")
	private List<WebElement> emailColumnCells;

	@FindBy(xpath = "//tr[starts-with(@id,'partner_list_item')]/td[7]/div")
	private List<WebElement> statusColumnBadges;

	@FindBy(css = "svg[id$='_asc_icon'], svg[id$='_desc_icon']")
	private List<WebElement> columnSortIcons;

	@FindBy(xpath = "//span[starts-with(normalize-space(text()),'Invalid character.')]")
	private WebElement invalidCharacterError;

	@FindBy(xpath = "//button[@id='partner_type_filter_dropdown_btn']/parent::div//input")
	private WebElement partnerTypeDropdownSearchInput;

	@FindBy(css = "input, textarea, select")
	private List<WebElement> editableInputs;

	@FindBy(xpath = "//button[normalize-space()='Save' or normalize-space()='Edit' or normalize-space()='Update' or normalize-space()='Delete']")
	private List<WebElement> editActionButtons;

	@FindBy(id = "view_partner_details_org_name_context")
	private WebElement viewOrganisationName;

	@FindBy(id = "view_partner_details_first_name_context")
	private WebElement viewFirstName;

	@FindBy(id = "view_partner_details_last_name_context")
	private WebElement viewLastName;

	@FindBy(id = "view_partner_details_phone_number_context")
	private WebElement viewPhoneNumber;

	@FindBy(id = "view_partner_details_email_context")
	private WebElement viewEmail;

	@FindBy(id = "view_partner_details_policy_group_context")
	private WebElement viewPolicyGroup;

	@FindBy(id = "view_partner_details_partner_created_date_time")
	private WebElement viewPartnerCreatedTime;

	@FindBy(id = "view_expiry_date_context")
	private WebElement viewCertificateExpiry;

	@FindBy(id = "view_certificate_upload_date_context")
	private WebElement viewCertificateUploadTime;

	@FindBy(id = "success_msg_close_icon")
	private WebElement successMessageCloseIcon;

	public PartnerAdminPage(WebDriver driver) {
		super(driver);
	}

	public boolean isPartnerCertificateDisplayed() {
		return isElementDisplayed(partnerCertificateInViewPartnerDetailsPage);
	}

	public boolean isExpiryDateTimeDisplayed() {
		return isElementDisplayed(expiryDateTimeInViewPartnerPage);
	}

	public boolean isDisabledDeactivateButtonInListOfPartnerDisplayed() {
		return isElementDisplayed(disabledDeactivateButtonInListOfPartner);
	}

	public void clickOnDeactivateButtonInListOfPartnerPage() {
		clickOnElement(disabledDeactivateButtonInListOfPartner);

	}

	public boolean isTimeOfUploadDisplayed() {
		return isElementDisplayed(timeOfUploadInViewPartnerPage);
	}

	public boolean isDownloadCertificateButtonDisplayed() {
		return isElementDisplayed(downloadCertificateButtonInViewPartnerPage);
	}

	public boolean isDisabledDownloadCertificateButtonDisplayed() {
		return isElementDisplayed(downloadCertificateButtonDisabledStateInViewPartnerPage);
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

	public boolean isListOfPartnerRowGreyedOutDisplayed() {
		return isElementDisplayed(listOfPartnerRowGreyedOut);
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
		return isElementDisplayed(partnerIdDescendingIcon);
	}

	public boolean isPartnersIdAscIconDisplayed() {
		return isElementDisplayed(partnerIdAscendingIcon);
	}

	public void clickOnPartnerIdAscendingIcon() {
		clickOnElement(partnerIdAscendingIcon);
	}

	public void clickOnPartnerIdDescendingIcon() {
		clickOnElement(partnerIdDescendingIcon);
	}

	public boolean isPolicyGroupNamesDescIconDisplayed() {
		return isElementDisplayed(policyGroupNameDescendingIcon);
	}

	public boolean isPolicyGroupNamesAscIconDisplayed() {
		return isElementDisplayed(policyGroupNameAscendingIcon);
	}

	public boolean isOrganizationAscIconDisplayed() {
		return isElementDisplayed(orgNameAscendingIcon);
	}

	public boolean isOrganizationDescIconDisplayed() {
		return isElementDisplayed(orgNameDescendingIcon);
	}

	public boolean isCertificatesUploadStatusDescIconDisplayed() {
		return isElementDisplayed(certificateUploadStatusDescIcon);
	}

	public boolean isCertificateUploadsStatusAscIconDisplayed() {
		return isElementDisplayed(certificateUploadStatusDescIcon);
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

	public void enterPartnerIdInFilter(String partnerId) {
		enter(partnersIdFilter, partnerId);
	}

	public void clickOnApplyFiltersBtn() {
		clickOnElement(applyFiltersBtn);
	}

	public boolean isAuthenticationPartnerCellDisplayed() {
		return isElementDisplayed(firstRowPartnerTypeCell);
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
		return isElementDisplayed(listOfPartnerPageRow);
	}

	public void clickOnActivatedPartner() {
		clickOnElement(listOfPartnerPageRow);
	}

	public boolean isViewPartnersDetailsPageDisplayed() {
		return isElementDisplayed(viewPartnersDetailsPage);
	}

	public boolean isOrganisationNameInViewPartnerPageDisplayed() {
		return isElementDisplayed(organisationNameInViewDetailsPage);
	}

	public void clickOnlistOfPartners() {
		clickOnElement(listOfPartners);
	}

	public void clickOnActionsButton() {
		clickOnElement(actionsButton);
	}

	public void clickOnActionsButtonInActivatedPartner() {
		clickOnElement(actionButtonInActivatedPartner);
	}

	public boolean isUploadCertificateButtonDisplayed() {
		return isElementDisplayed(uploadCertificateOption);
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
		clickOnElement(deactivatedStatusInFilters);
	}

	public boolean isDeactivatedPartnerRowDisplayed() {
		return isElementDisplayed(deactivatedPartnerRow);
	}

	// Callers must wait for the unfiltered list before opening the filter panel, or it reads empty.
	public boolean isPartnerListLoaded() {
		return isElementDisplayedQuick(firstPartnerRow, LIST_LOAD_TIMEOUT);
	}

	// Rows are index-keyed in React, so the same DOM node survives a filter re-render -
	// checking mere presence never actually waits. This waits for row 1's own text to
	// contain the expected partner ID, so a stale pre-filter row can't be mistaken for it.
	public boolean isPartnerListLoaded(String expectedPartnerId) {
		try {
			new WebDriverWait(driver, LIST_LOAD_TIMEOUT)
					.until(ExpectedConditions.textToBePresentInElement(firstRowPartnerIdCell, expectedPartnerId));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isPartnerListLoadedByEmail(String expectedEmail) {
		try {
			new WebDriverWait(driver, LIST_LOAD_TIMEOUT)
					.until(ExpectedConditions.textToBePresentInElement(firstRowEmailCell, expectedEmail));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getFirstRowPartnerType() {
		return getCellTextWithStaleRetry(firstRowPartnerTypeCell);
	}

	public String getEmailOfFirstRowWithPartnerTypeOtherThan(String partnerType) {
		List<String> partnerTypes = getColumnValuesWithStaleRetry(partnerTypeColumnCells);
		List<String> emails = getEmailAddressColumnValues();
		for (int row = 0; row < partnerTypes.size() && row < emails.size(); row++) {
			if (!partnerType.equalsIgnoreCase(partnerTypes.get(row))) {
				return emails.get(row);
			}
		}
		throw new RuntimeException("No partner row found with a type other than: " + partnerType);
	}

	public void clickOnEmailAddressFilterInfoIcon() {
		clickOnElement(emailAddressFilterInfoIcon);
	}

	public void selectPartnerTypeFilterOption(String partnerType) {
		clickOnElement(waitForFilterOption(partnerTypeFilterOptions, partnerType));
	}

	public String getStatusFilterOptionText(int optionNumber) {
		String optionId = "status_filter_option" + optionNumber;
		return new WebDriverWait(driver, LIST_LOAD_TIMEOUT).ignoring(StaleElementReferenceException.class)
				.until(webDriver -> {
					for (WebElement option : statusFilterOptions) {
						if (optionId.equals(option.getAttribute("id")) && option.isDisplayed()) {
							return option.getText().trim();
						}
					}
					return null;
				});
	}

	public List<String> getStatusColumnValues() {
		return getColumnValuesWithStaleRetry(statusColumnBadges);
	}

	// Row 1 is what the Action menu operates on; isDeactivatedPartnerRowDisplayed is pinned to row 2.
	public boolean isFirstPartnerRowDisplayed() {
		return isElementDisplayed(firstPartnerRow);
	}

	public boolean isViewButtonsEnabled() {
		return isElementEnabled(viewButtons);
	}

	public boolean isDeactivateButtonDisabled() {
		return isElementDisabled(deactivateButtons);
	}

	public boolean isDeactivateOptionEnabled() {
		return getTextFromAttribute(deactivateButtons, GlobalConstants.CLASS)
				.contains(GlobalConstants.ACTION_MENU_OPTION_ENABLED);
	}

	public boolean isDeactivateOptionDisabled() {
		return getTextFromAttribute(deactivateButtons, GlobalConstants.CLASS)
				.contains(GlobalConstants.ACTION_MENU_OPTION_DISABLED);
	}

	public boolean isViewOptionEnabled() {
		return getTextFromAttribute(viewButtons, GlobalConstants.CLASS)
				.contains(GlobalConstants.ACTION_MENU_OPTION_ENABLED);
	}

	public void clickOnDeactivateOptionInActionMenu() {
		clickOnElement(deactivateButtons);
	}

	public boolean isDeactivatePopupHeaderDisplayed() {
		return isElementDisplayed(deactivatePartnerHeader);
	}

	public boolean isDeactivatePopupHeaderDisplayedQuick() {
		return isElementDisplayedQuick(deactivatePartnerHeader, SHORT_ABSENCE_TIMEOUT);
	}

	public boolean isDeactivatePopupDescriptionDisplayed() {
		return isElementDisplayed(deactivatePartnerDescription);
	}

	public boolean isDeactivatePopupCancelButtonDisplayed() {
		return isElementDisplayed(deactivateCancelButton);
	}

	public boolean isDeactivatePopupConfirmButtonDisplayed() {
		return isElementDisplayed(deactivateConfirmButton);
	}

	public void clickOnDeactivatePopupCancelButton() {
		clickOnElement(deactivateCancelButton);
	}

	public void clickOnDeactivatePopupConfirmButton() {
		clickOnElement(deactivateConfirmButton);
		WaitUtil.waitForInvisibility(driver, deactivatePartnerHeader);
	}

	public boolean isDeactivateOptionKeyboardOperable() {
		return GlobalConstants.ROLE_BUTTON
				.equals(getTextFromAttribute(deactivateOptionWrapper, GlobalConstants.ROLE))
				&& GlobalConstants.TABINDEX_FOCUSABLE
						.equals(getTextFromAttribute(deactivateOptionWrapper, GlobalConstants.TABINDEX));
	}

	public boolean isDeactivateOptionCursorPointer() {
		return getTextFromAttribute(deactivateOptionWrapper, GlobalConstants.CLASS)
				.contains(GlobalConstants.CURSOR_POINTER);
	}

	public boolean isDeactivateOptionCursorDefault() {
		return getTextFromAttribute(deactivateOptionWrapper, GlobalConstants.CLASS)
				.contains(GlobalConstants.CURSOR_DEFAULT);
	}

	public void pressEnterOnDeactivateOption() {
		focusAndPressEnter(deactivateOptionWrapper);
	}

	public boolean isDeactivatePopupConfirmButtonEnabled() {
		return isElementEnabled(deactivateConfirmButton);
	}

	public boolean isDeactivatePopupConfirmButtonNativeButton() {
		return GlobalConstants.BUTTON_TAG.equalsIgnoreCase(deactivateConfirmButton.getTagName());
	}

	public boolean isDeactivatePopupConfirmButtonFocusable() {
		return isElementFocusable(deactivateConfirmButton);
	}

	public boolean isDeactivatePopupCancelButtonNativeButton() {
		return GlobalConstants.BUTTON_TAG.equalsIgnoreCase(deactivateCancelButton.getTagName());
	}

	public boolean isDeactivatePopupCancelButtonEnabled() {
		return isElementEnabled(deactivateCancelButton);
	}

	public boolean isDeactivatePopupCancelButtonFocusable() {
		return isElementFocusable(deactivateCancelButton);
	}

	public boolean isDeactivatePopupWithinViewport() {
		return isElementWithinViewport(deactivatePartnerHeader);
	}

	public boolean isDeactivatePopupHorizontallyCentred() {
		return isElementHorizontallyCentred(deactivatePartnerHeader, POPUP_CENTRING_TOLERANCE_PX);
	}

	// The popup sets body overflow to hidden while open and back to auto on close.
	public boolean isPageScrollLocked() {
		return GlobalConstants.OVERFLOW_HIDDEN.equals(getBodyComputedStyle(GlobalConstants.OVERFLOW));
	}

	// The overlay spans the viewport, so it sits over the filter button's centre point.
	public boolean isFilterButtonCoveredByPopupOverlay() {
		return isElementCoveredAtCentre(filterbtnTrigger);
	}

	// The popup relies on focus-trap-react rather than aria-modal/inert, so this checks
	// what it actually implements: Tab cannot move focus outside the trapped boundary.
	public void pressTabWithinDeactivatePopup(int times) {
		for (int i = 0; i < times; i++) {
			new Actions(driver).sendKeys(Keys.TAB).perform();
		}
	}

	public boolean isFocusContainedWithinDeactivatePopup() {
		return Boolean.TRUE.equals(((JavascriptExecutor) driver).executeScript(
				"var boundary = arguments[0].closest('.rounded-lg');"
						+ "return boundary ? boundary.contains(document.activeElement) : false;",
				deactivatePartnerHeader));
	}

	public int getPartnerRowCount() {
		return partnerRows.size();
	}

	public String getFirstRowPartnerId() {
		return getCellTextWithStaleRetry(firstRowPartnerIdCell);
	}

	public String getFirstRowEmailAddress() {
		return getCellTextWithStaleRetry(firstRowEmailCell);
	}

	public List<String> getEmailAddressColumnValues() {
		return getColumnValuesWithStaleRetry(emailColumnCells);
	}

	// Waits for the cell rather than reading straight away: after a filter is applied or reset
	// the rows are briefly absent, and consecutive scenarios in one session read across that gap.
	private String getCellTextWithStaleRetry(WebElement cell) {
		for (int attempt = 1; attempt <= STALE_READ_RETRY; attempt++) {
			try {
				return new WebDriverWait(driver, LIST_LOAD_TIMEOUT).until(ExpectedConditions.visibilityOf(cell))
						.getText().trim();
			} catch (StaleElementReferenceException stale) {
				LogUtil.step("Cell read went stale. Retry " + attempt + "/" + STALE_READ_RETRY);
			}
		}
		throw new RuntimeException("Cell still stale after " + STALE_READ_RETRY + " retries: " + cell);
	}

	private List<String> getColumnValuesWithStaleRetry(List<WebElement> cells) {
		for (int attempt = 1; attempt <= STALE_READ_RETRY; attempt++) {
			try {
				new WebDriverWait(driver, LIST_LOAD_TIMEOUT).until(webDriver -> !cells.isEmpty());
				List<String> values = new ArrayList<>();
				for (WebElement cell : cells) {
					values.add(cell.getText().trim());
				}
				return values;
			} catch (StaleElementReferenceException stale) {
				LogUtil.step("Column read went stale. Retry " + attempt + "/" + STALE_READ_RETRY);
			} catch (TimeoutException noRows) {
				LogUtil.step("No rows rendered for column");
				return new ArrayList<>();
			}
		}
		throw new RuntimeException("Column still stale after " + STALE_READ_RETRY + " retries");
	}

	public int getSortIconCountForColumn(String columnId) {
		try {
			new WebDriverWait(driver, LIST_LOAD_TIMEOUT).until(webDriver -> !columnSortIcons.isEmpty());
		} catch (TimeoutException e) {
			return 0;
		}
		int count = 0;
		for (WebElement icon : columnSortIcons) {
			String id = icon.getAttribute("id");
			if ((columnId + "_asc_icon").equals(id) || (columnId + "_desc_icon").equals(id)) {
				count++;
			}
		}
		return count;
	}

	public String getEmailAddressFilterPlaceholder() {
		return getTextFromAttribute(emailsAddressFilter, "placeholder");
	}

	public void enterEmailAddressInFilter(String emailAddress) {
		enterRedacted(emailsAddressFilter, emailAddress);
	}

	public boolean isEmailAddressFilterInfoIconDisplayed() {
		return isElementDisplayed(emailAddressFilterInfoIcon);
	}

	public void hoverOverEmailAddressFilterInfoIcon() {
		hoverOverElement(emailAddressFilterInfoIcon);
	}

	public String getEmailAddressFilterInfoTooltipText() {
		return getTextFromLocator(emailAddressFilterInfoDescription).trim();
	}

	public boolean isInvalidCharacterErrorDisplayed() {
		return isElementDisplayedQuick(invalidCharacterError, SHORT_ABSENCE_TIMEOUT);
	}

	public String getInvalidCharacterErrorText() {
		return getTextFromLocator(invalidCharacterError).trim();
	}

	public List<String> getPartnerIdColumnValues() {
		return getColumnValuesWithStaleRetry(partnerIdColumnCells);
	}

	public List<String> getPartnerTypeColumnValues() {
		return getColumnValuesWithStaleRetry(partnerTypeColumnCells);
	}

	public boolean waitForFilteredCountToChangeFrom(int previousCount) {
		try {
			new WebDriverWait(driver, LIST_LOAD_TIMEOUT)
					.until(driver -> getFilteredPartnersCountFromSubtitle() != previousCount);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean waitForFirstRowPartnerIdToChangeFrom(String previousPartnerId) {
		try {
			new WebDriverWait(driver, LIST_LOAD_TIMEOUT)
					.until(driver -> !previousPartnerId.equals(getFirstRowPartnerId()));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public int getFilteredPartnersCountFromSubtitle() {
		String subtitle = getTextFromLocator(tabularViewsSubtitle).trim();
		Matcher matcher = Pattern.compile("\\((\\d+)\\)").matcher(subtitle);
		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		}
		throw new RuntimeException("Filtered partner count not found in subtitle: " + subtitle);
	}

	public boolean isNoResultsFoundQuick() {
		return isElementDisplayedQuick(noResultsFoundMessages, SHORT_ABSENCE_TIMEOUT);
	}

	public String getPartnerStatusInViewPartnerDetails() {
		return getTextFromLocator(partnerStatusInViewPartnerPage).trim();
	}

	public boolean isPartnerCertificateSectionGreyedOut() {
		return getTextFromAttribute(partnerCertificateSectionInViewPartnerPage, GlobalConstants.CLASS)
				.contains(GlobalConstants.CERT_SECTION_DEACTIVATED_BG);
	}

	// For an uploaded certificate, the disabled state here reflects isActive alone.
	public boolean isDownloadCertificateButtonEnabledInViewPartnerPage() {
		return isElementEnabled(downloadCertificateButtonInViewPartnerPage);
	}

	public boolean isDownloadCertificateButtonEnabledInViewPartnerPageQuick() {
		return isElementDisplayedQuick(downloadCertificateButtonInViewPartnerPage, SHORT_ABSENCE_TIMEOUT)
				&& downloadCertificateButtonInViewPartnerPage.isEnabled();
	}

	// Waits on the first badge - the rows exist before the filtered response paints their status text.
	public boolean areAllPartnerRowStatusesDeactivated() {
		new WebDriverWait(driver, LIST_LOAD_TIMEOUT).until(ExpectedConditions.visibilityOf(firstRowStatusBadge));
		StringBuilder observed = new StringBuilder();
		boolean allDeactivated = !statusColumnBadges.isEmpty();
		for (WebElement cell : statusColumnBadges) {
			String status = cell.getText().trim();
			observed.append('[').append(status).append(']');
			if (!GlobalConstants.DEACTIVATED.equals(status)) {
				allDeactivated = false;
			}
		}
		LogUtil.step("Row statuses after Deactivated filter: " + observed);
		return allDeactivated;
	}

	public String getFirstRowPartnerStatus() {
		return getTextFromLocator(firstRowStatusBadge).trim();
	}

	public boolean isFirstRowStatusBadgeDeactivated() {
		return getTextFromAttribute(firstRowStatusBadge, GlobalConstants.CLASS)
				.contains(GlobalConstants.DEACTIVATED_BACKGROUND);
	}

	public boolean isFirstPartnerRowGreyedOut() {
		return getTextFromAttribute(firstPartnerRow, GlobalConstants.CLASS)
				.contains(GlobalConstants.PARTNER_ROW_DEACTIVATED_TEXT);
	}

	public String getDeactivatePopupTitle() {
		return getTextFromLocator(deactivatePartnerHeader).trim();
	}

	public String getDeactivatePopupSubTitle() {
		return getTextFromLocator(deactivatePartnerDescription).trim();
	}

	public boolean isDeactivatePopupTitleFullyInterpolated() {
		return !getDeactivatePopupTitle().contains(GlobalConstants.UNRESOLVED_PLACEHOLDER);
	}

	// An index of -1 for either means that value never reached the rendered title.
	public boolean isPartnerIdOrderedBeforeOrganisation(String partnerId, String organisationName) {
		String title = getDeactivatePopupTitle();
		int partnerIdIndex = title.indexOf(partnerId);
		int organisationIndex = title.lastIndexOf(organisationName);
		return partnerIdIndex > -1 && organisationIndex > -1 && partnerIdIndex < organisationIndex;
	}

	public void clickOnPartnerTypeAscIcons() {
		clickOnElement(partnerTypeAscendingIcons);
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

	public void clickOnViewPartnerBackButton() {
		clickOnElement(listOfViewPartnerBackButton);
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

	public void clickOnViewButtonInListOfPartnerDetailsScreen() {
		clickOnElement(viewButtonsInListOfPartnersPage);
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

	public void clickOnCancelButton() {
		clickOnElement(deactivateCancelButton);
	}

	public boolean isDeactivateColorCodeButtonDisplayed() {
		return isElementDisplayed(deactivateColorCodeButton);
	}

	public boolean isPartnerCertificateInViewPartnerDetailsDisplayed() {
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
		return isElementDisplayed(successMessageInMosipSignedCertificate);
	}

	public boolean isGobackButtonInViewPatnerPageDisplayed() {
		return isElementDisplayed(gobackButtonInPartnerDetailsPage);
	}

	public void clickOnAuthId() {
		clickOnElement(authPartnerId);
	}

	public boolean isNoneditableInListOfPartnerDisplayed() {
		return isElementDisplayed(nonEditableInListOfPartner);
	}

	public boolean isCertificateUploadStatusHeaderTagDisplayed() {
		return isElementDisplayed(certUploadStatusHeader);
	}

	public void clickOnCertificateUploadascIcon() {
		clickOnElement(certificateUploadStatusAscIcon);
	}

	public boolean isPartnerListLoadedByPartnerType(String partnerType) {
		try {
			new WebDriverWait(driver, LIST_LOAD_TIMEOUT)
					.until(ExpectedConditions.textToBePresentInElement(firstRowPartnerTypeCell, partnerType));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isFirstRowStatus(String status) {
		try {
			new WebDriverWait(driver, LIST_LOAD_TIMEOUT)
					.until(ExpectedConditions.textToBePresentInElement(firstRowStatusBadge, status));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean areAllVisiblePartnerTypes(String partnerType) {
		List<String> partnerTypes = getPartnerTypeColumnValues();
		if (partnerTypes.isEmpty()) {
			return false;
		}
		for (String visibleType : partnerTypes) {
			if (!partnerType.equals(visibleType)) {
				return false;
			}
		}
		return true;
	}

	public boolean areAllVisiblePartnerStatuses(String status) {
		List<String> statuses = getStatusColumnValues();
		if (statuses.isEmpty()) {
			return false;
		}
		for (String visibleStatus : statuses) {
			if (!status.equals(visibleStatus)) {
				return false;
			}
		}
		return true;
	}

	public void selectStatusFilterOption(String statusLabel) {
		clickOnElement(waitForFilterOption(statusFilterOptions, statusLabel));
	}

	private WebElement waitForFilterOption(List<WebElement> options, String label) {
		return new WebDriverWait(driver, LIST_LOAD_TIMEOUT).ignoring(StaleElementReferenceException.class)
				.until(webDriver -> {
					for (WebElement option : options) {
						if (label.equals(option.getText().trim())) {
							return option;
						}
					}
					return null;
				});
	}

	public String getPartnerTypeFilterButtonText() {
		return getTextFromLocator(partnerTypeDropdown).trim();
	}

	public boolean isPartnerTypeFilterOptionDisplayed() {
		try {
			new WebDriverWait(driver, LIST_LOAD_TIMEOUT).ignoring(StaleElementReferenceException.class)
					.until(webDriver -> !partnerTypeFilterOptions.isEmpty() && partnerTypeFilterOptions.get(0).isDisplayed());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isPartnerTypeDropdownSearchInputDisplayed() {
		return isElementDisplayedQuick(partnerTypeDropdownSearchInput, SHORT_ABSENCE_TIMEOUT);
	}

	public String getPageTitleText() {
		return getTextFromLocator(titlePartner).trim();
	}

	public String getViewPartnerDetailsPartnerType() {
		return getTextFromLocator(viewPartnerDetailsPartnerType).trim();
	}

	public boolean isViewPartnerEditControlDisplayed() {
		return isAnyElementDisplayed(editableInputs, SHORT_ABSENCE_TIMEOUT)
				|| isAnyElementDisplayed(editActionButtons, SHORT_ABSENCE_TIMEOUT);
	}

	private boolean isAnyElementDisplayed(List<WebElement> elements, Duration timeout) {
		try {
			return new WebDriverWait(driver, timeout).ignoring(StaleElementReferenceException.class)
					.until(webDriver -> {
						for (WebElement element : elements) {
							if (element.isDisplayed()) {
								return true;
							}
						}
						return null;
					});
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean areViewPartnerValueFieldsReadOnly() {
		WebElement[] valueFields = { viewPartnerDetailsPartnerType, viewOrganisationName, viewFirstName, viewLastName,
				viewPhoneNumber, viewEmail, viewPolicyGroup };
		try {
			for (WebElement valueField : valueFields) {
				WebElement field = new WebDriverWait(driver, LIST_LOAD_TIMEOUT)
						.until(ExpectedConditions.visibilityOf(valueField));
				String tag = field.getTagName();
				if ("input".equalsIgnoreCase(tag) || "textarea".equalsIgnoreCase(tag) || "select".equalsIgnoreCase(tag)) {
					return false;
				}
			}
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getViewPartnerIdText() {
		return getTextFromLocator(partnerIdInViewPartnerDetailsPage).trim();
	}

	public String getViewOrganisationName() {
		return getTextFromLocator(viewOrganisationName).trim();
	}

	public String getViewPolicyGroup() {
		return getTextFromLocator(viewPolicyGroup).trim();
	}

	public String getViewEmail() {
		return getTextFromLocator(viewEmail).trim();
	}

	public String getViewFirstName() {
		return getTextFromLocator(viewFirstName).trim();
	}

	public String getViewLastName() {
		return getTextFromLocator(viewLastName).trim();
	}

	public String getViewCertificateExpiry() {
		return getTextFromLocator(viewCertificateExpiry).trim();
	}

	public String getViewCertificateUploadTime() {
		return getTextFromLocator(viewCertificateUploadTime).trim();
	}

	public String getViewPartnerSuccessMessage() {
		return getTextFromLocator(successMassageInOriginalCertificate).trim();
	}

	public String getFirstRowOrganisationName() {
		return getCellTextWithStaleRetry(firstRowOrganisationCell);
	}

	public String getFirstRowPolicyGroup() {
		return getCellTextWithStaleRetry(firstRowPolicyGroupCell);
	}

	public String getViewPhoneNumber() {
		return getTextFromLocator(viewPhoneNumber).trim();
	}

	public String getViewPartnerCreatedOnText() {
		return getTextFromLocator(partnerCreatedDateInViewPartnerPage).trim();
	}

	public String getViewPartnerCreatedTimeText() {
		return getTextFromLocator(viewPartnerCreatedTime).trim();
	}

	public String getGoBackButtonText() {
		return getTextFromLocator(gobackButtonInPartnerDetailsPage).trim();
	}

	public void dismissViewPartnerSuccessMessage() {
		clickOnElement(successMessageCloseIcon);
	}

	public boolean isOriginalCertificateOptionEnabled() {
		return isElementEnabled(originalCertificateDropdown);
	}

	public boolean isViewOrganisationNameFullyVisible() {
		return isFullyVisible(viewOrganisationName);
	}

	public boolean isViewPolicyGroupFullyVisible() {
		return isFullyVisible(viewPolicyGroup);
	}

	public boolean isViewEmailFullyVisible() {
		return isFullyVisible(viewEmail);
	}

	public boolean isViewPhoneNumberFullyVisible() {
		return isFullyVisible(viewPhoneNumber);
	}

	private boolean isFullyVisible(WebElement element) {
		new WebDriverWait(driver, LIST_LOAD_TIMEOUT).until(ExpectedConditions.visibilityOf(element));
		Object result = ((JavascriptExecutor) driver).executeScript(
				"var el = arguments[0];"
						+ "return el.scrollWidth <= el.clientWidth + 1 && el.scrollHeight <= el.clientHeight + 1;",
				element);
		return Boolean.TRUE.equals(result);
	}

}
