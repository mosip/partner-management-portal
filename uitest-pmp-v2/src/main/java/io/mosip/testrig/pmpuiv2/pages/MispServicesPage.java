package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.openqa.selenium.By;
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

public class MispServicesPage extends BasePage {

	@FindBy(id = "generate_misp_license_key_btn")
	private WebElement generateMispLicenceKeyButton;

	@FindBy(id = "page_title")
	private WebElement generateMispLicenceKeyTitle;

	@FindBy(id = "sub_title_btn")
	private WebElement mispServicesBreadcomb;

	@FindBy(id = "title_back_icon")
	private WebElement mispServicesTitleBackIcon;

	@FindBy(id = "filter_btn")
	private WebElement filterButton;

	@FindBy(id = "apply_filter__btn")
	private WebElement mispLicenseFilterApplyButton;

	@FindBy(id = "filter_reset_btn")
	private WebElement mispLicenseFilterResetButton;

	@FindBy(id = "misp_partner_id_filter")
	private WebElement mispFilterPartnerIdInput;

	@FindBy(id = "misp_org_name_filter")
	private WebElement mispFilterOrgNameInput;

	@FindBy(id = "misp_policy_group_filter")
	private WebElement mispFilterPolicyGroupInput;

	@FindBy(id = "misp_policy_name_filter")
	private WebElement mispFilterPolicyNameInput;

	@FindBy(id = "misp_license_key_name_filter")
	private WebElement mispFilterLicenseKeyNameInput;

	@FindBy(id = "misp_status_filter_dropdown_btn")
	private WebElement mispFilterStatusDropdownBtn;

	@FindBy(id = "misp_status_filter_option1")
	private WebElement mispFilterStatusActiveOption;

	@FindBy(id = "no_results_found")
	private WebElement noResultsFoundMessage;

	@FindBy(id = "misp_partner_id_filter_input_error")
	private WebElement mispFilterPartnerIdInputError;

	private static final int RIGHT_EDGE_ALIGNMENT_TOLERANCE_PX = 5;

	@FindBy(id = "generate_license_key_mandantory_msg")
	private WebElement allFieldsAreMandatorySubtitle;

	@FindBy(id = "sub_title_home_btn")
	private WebElement generateMispLicenceKeyHomeButton;

	@FindBy(id = "generate_license_key_partner_id_label")
	private WebElement partnerIdLabel;

	@FindBy(id = "generate_license_key_partner_type_label")
	private WebElement partnerTypeLabel;

	@FindBy(id = "generate_license_key_policy_group_label")
	private WebElement policyGroupLabel;

	@FindBy(id = "generate_license_key_policy_name_label")
	private WebElement policyNameLabel;

	@FindBy(id = "generate_license_key_name_label")
	private WebElement mispLicenceKeyLabel;

	@FindBy(id = "generate_license_key_expiry_date_calender_label")
	private WebElement calenderLabel;

	@FindBy(id = "generate_license_key_clear_form")
	private WebElement clearFormButton;

	@FindBy(id = "generate_license_key_cancel_btn")
	private WebElement cancelButton;

	@FindBy(id = "generate_license_key_submit_btn")
	private WebElement submitButton;

	@FindBy(id = "generate_license_key_partner_id_info")
	private WebElement partnerIdInfoButton;

	@FindBy(id = "generate_license_key_partner_id_info_info_description")
	private WebElement partnerIdInfoDescription;

	@FindBy(id = "generate_license_key_partner_id_dropdown_btn")
	private WebElement partnerIdDropdownButton;

	@FindBy(id = "generate_license_key_partner_id_option1")
	private WebElement partnerIdOption1;

	@FindBy(id = "generate_license_key_policy_group_info")
	private WebElement policyGroupInfoButton;

	@FindBy(id = "generate_license_key_policy_group_info_info_description")
	private WebElement policyGroupInfoDescription;

	@FindBy(id = "generate_license_key_policy_name_info")
	private WebElement policyNameInfoButton;

	@FindBy(id = "generate_license_key_policy_name_info_info_description")
	private WebElement policyNameInfoDescription;

	@FindBy(id = "generate_misp_license_key_guidence")
	private WebElement mispLicenseKeyGuidence;

	@FindBy(id = "generate_misp_license_key_important_note")
	private WebElement mispLicenseKeyImportantNote;

	@FindBy(css = "#generate_license_key_policy_name_dropdown_btn span")
	private WebElement policyNamePlaceholder;

	@FindBy(css = "#generate_license_key_policy_group span")
	private WebElement policyGroupPlaceholder;

	@FindBy(css = "#generate_license_key_partner_id_dropdown_btn span")
	private WebElement partnerIdPlaceholder;

	@FindBy(css = "#generate_license_key_partner_type span")
	private WebElement partnerTypePlaceholder;

	@FindBy(css = "input[data-placeholder-id='generate_license_key_name_placeholder']")
	private WebElement mispLicenseKeyNamePlaceholder;

	@FindBy(id = "generate_license_key_partner_id_search_input")
	private WebElement partnerIdDropdownSearchInput;

	@FindBy(id = "generate_license_key_partner_id_no_data_available")
	private WebElement partnerIdNoDataAvailableMessage;

	@FindBy(id = "generate_license_key_partner_type")
	private WebElement partnerType;

	@FindBy(id = "generate_license_key_policy_group")
	private WebElement policyGroup;

	@FindBy(id = "generate_license_key_policy_name_dropdown_btn")
	private WebElement policyNameDropdown;

	@FindBy(id = "generate_license_key_policy_name_option1")
	private WebElement mispPolicyName;

	@FindBy(id = "generate_license_key_policy_name_description_1")
	private WebElement mispPolicyNameDescription;

	@FindBy(id = "generate_license_key_name")
	private WebElement licenseKeyNameTextbox;

	@FindBy(id = "generate_license_key_policy_name_search_input")
	private WebElement policyNameSearchInput;

	@FindBy(css = "button.react-datepicker__navigation--next")
	private WebElement nextMonth;

	@FindBy(css = "button.react-datepicker__navigation--previous")
	private WebElement previousMonth;

	@FindBy(css = "div.react-datepicker__day.react-datepicker__day--024:not(.react-datepicker__day--outside-month)")
	private WebElement date24InCalender;

	@FindBy(css = "div.react-datepicker__day.react-datepicker__day--004:not(.react-datepicker__day--outside-month)")
	private WebElement date4InCalender;

	@FindBy(css = "div.react-datepicker__day--today")
	private WebElement todayInCalender;

	@FindBy(id = "generate_license_key_expiry_date_calender")
	private WebElement expiryDate;

	@FindBy(id = "copy_id_popup_tile")
	private WebElement copyIdPopupTitle;

	@FindBy(id = "copy_id_popup_sub_title")
	private WebElement copyIdPopupSubtitle;

	@FindBy(id = "copy_id_popup_alert_msg")
	private WebElement copyIdPopupAlertMessage;

	@FindBy(id = "copy_id_close_btn")
	private WebElement mispLicenseKeyPopupCloseButton;

	@FindBy(id = "copy_id_popup_header")
	private WebElement mispLicenseKeyPopup;

	@FindBy(id = "copy_id_popup_id")
	private WebElement mispLicenseKeyId;

	@FindBy(id = "copy_id_btn")
	private WebElement copyIdButton;

	@FindBy(css = "#copy_id_btn[class*='bg-[#1447B2]']")
	private WebElement copiedToast;

	@FindBy(id = "confirmation_success_icon")
	private WebElement confirmationSuccessIcon;

	@FindBy(id = "generate_license_key_confirmation_header")
	private WebElement licenseKeyConfirmationHeader;

	@FindBy(id = "generate_license_key_confirmation_description")
	private WebElement licenseKeyConfirmationDescription;

	@FindBy(id = "confirmation_home_btn")
	private WebElement confirmationHomeBtn;

	@FindBy(id = "confirmation_go_back_btn")
	private WebElement confirmationGoBackBtn;

	@FindBy(id = "generate_misp_license_key_error_msg")
	private WebElement generateLicenseKeyErrorMessage;

	@FindBy(id = "run_time_error_title")
	private WebElement networkErrorTitle;

	@FindBy(id = "something_went_wrong_home_btn")
	private WebElement networkErrorRetryButton;

	@FindBy(css = "div.react-datepicker__month-container")
	private WebElement calendarPopup;

	@FindBy(css = "div.react-datepicker__current-month")
	private WebElement calendarMonthHeader;

	@FindBy(id = "generate_license_key_expiry_date_calender_info_info_description")
	private WebElement expiryDateCalenderInfoDescription;

	@FindBy(id = "generate_license_key_expiry_date_calender_info")
	private WebElement expiryDateCalenderInfoIcon;

	@FindBy(id = "generate_license_key_invalid_license_key_name")
	private WebElement invalidCharacterErrorMessage;

	@FindBy(id = "mispLicenseList.partnerId_header")
	private WebElement partnerIdHeader;

	@FindBy(id = "mispLicenseList.orgName_header")
	private WebElement orgNameHeader;

	@FindBy(id = "mispLicenseList.policyGroup_header")
	private WebElement policyGroupHeader;

	@FindBy(id = "mispLicenseList.policyName_header")
	private WebElement policyNameHeader;

	@FindBy(id = "mispLicenseList.mispLicenseKeyName_header")
	private WebElement mispLicenseKeyNameHeader;

	@FindBy(id = "mispLicenseList.creationDate_header")
	private WebElement creationDateHeader;

	@FindBy(id = "mispLicenseList.expirationDate_header")
	private WebElement expirationDateHeader;

	@FindBy(id = "mispLicenseList.status_header")
	private WebElement statusHeader;

	@FindBy(id = "mispLicenseList.mispLicenseKey_header")
	private WebElement mispLicenseKeyHeader;

	@FindBy(id = "mispLicenseList.action_header")
	private WebElement actionHeader;

	@FindBy(id = "misp_license_list_action_view1")
	private WebElement mispLicenseListActionButton;

	@FindBy(id = "misp_license_list_view_btn")
	private WebElement mispLicenseListViewButton;

	@FindBy(id = "misp_license_list_regenerate_btn")
	private WebElement mispLicenseListRegenerateButton;

	@FindBy(id = "misp_license_list_deactivate_btn")
	private WebElement mispLicenseListDeactivateButton;

	@FindBy(id = "regenerate_license_key_mandantory_msg")
	private WebElement regenerateMandatoryFieldsSubtitle;

	@FindBy(id = "regenerate_license_key_partner_id_label")
	private WebElement regeneratePartnerIdLabel;

	@FindBy(id = "regenerate_license_key_partner_id")
	private WebElement regeneratePartnerId;

	@FindBy(id = "regenerate_license_key_partner_type_label")
	private WebElement regeneratePartnerTypeLabel;

	@FindBy(id = "regenerate_license_key_partner_type")
	private WebElement regeneratePartnerType;

	@FindBy(id = "regenerate_license_key_policy_group_label")
	private WebElement regeneratePolicyGroupLabel;

	@FindBy(id = "regenerate_license_key_policy_group")
	private WebElement regeneratePolicyGroup;

	@FindBy(id = "regenerate_license_key_policy_name_label")
	private WebElement regeneratePolicyNameLabel;

	@FindBy(id = "regenerate_license_key_policy_name")
	private WebElement regeneratePolicyName;

	@FindBy(id = "regenerate_license_key_name_label")
	private WebElement regenerateLicenseKeyNameLabel;

	@FindBy(id = "regenerate_license_key_name")
	private WebElement regenerateLicenseKeyNameTextbox;

	@FindBy(id = "regenerate_license_key_expiry_date_calender")
	private WebElement regenerateExpiryDate;

	@FindBy(id = "regenerate_license_key_clear_form")
	private WebElement regenerateClearFormButton;

	@FindBy(id = "regenerate_license_key_cancel_btn")
	private WebElement regenerateCancelButton;

	@FindBy(id = "regenerate_license_key_submit_btn")
	private WebElement regenerateSubmitButton;

	@FindBy(id = "regenerate_misp_license_key_important_note")
	private WebElement regenerateImportantNote;

	@FindBy(id = "regenerate_license_key_invalid_license_key_name")
	private WebElement regenerateInvalidCharacterErrorMessage;

	@FindBy(id = "regenerate_misp_license_key_error_msg")
	private WebElement regenerateErrorMessage;

	@FindBy(id = "regenerate_license_key_confirmation_header")
	private WebElement regenerateConfirmationHeader;

	@FindBy(id = "regenerate_license_key_confirmation_description")
	private WebElement regenerateConfirmationDescription;

	@FindBy(id = "regenerate_license_key_expiry_date_calender_info")
	private WebElement regenerateExpiryDateCalenderInfoIcon;

	@FindBy(id = "regenerate_license_key_expiry_date_calender_info_info_description")
	private WebElement regenerateExpiryDateCalenderInfoDescription;

	@FindBy(id = "misp_license_key_details_expiry_date_context")
	private WebElement viewMispLicenseKeyExpiryDateContext;

	@FindBy(id = "view_misp_license_key_details_status")
	private WebElement viewMispLicenseKeyDetailsStatus;

	@FindBy(id = "view_misp_license_key_back_btn")
	private WebElement viewMispLicenseKeyBackButton;

	@FindBy(id = "blocker_prompt_description")
	private WebElement cancelConfirmationPopup;

	@FindBy(id = "block_messsage_proceed")
	private WebElement cancelConfirmationPopupProceedButton;

	@FindBy(id = "undefined_title")
	private WebElement mispLicenseListSubTitleWithCount;

	@FindBy(id = "deactivate_popup_header")
	private WebElement deactivatePopupHeader;

	@FindBy(id = "deactivate_popup_description")
	private WebElement deactivatePopupDescription;

	@FindBy(id = "deactivate_cancel_btn")
	private WebElement deactivateCancelButton;

	@FindBy(id = "deactivate_submit_btn")
	private WebElement deactivateSubmitButton;

	private static final Duration POPUP_CHECK_TIMEOUT = Duration.ofSeconds(3);

	public MispServicesPage(WebDriver driver) {
		super(driver);
	}

	public boolean isGenerateMispLicenceKeyButtonDisplayed() {
		return isElementDisplayed(generateMispLicenceKeyButton);
	}

	public void clickOnGenerateMispLicenceKeyButton() {
		clickOnElement(generateMispLicenceKeyButton);
	}

	public boolean isGenerateMispLicenceKeyPageDisplayed() {
		return isElementDisplayed(generateMispLicenceKeyTitle);
	}

	public void clickOnMispServicesTitleBackIcon() {
		clickOnElement(mispServicesTitleBackIcon);
	}

	public boolean isGenerateMispLicenceKeyButtonPositionedTopRight() {
		return isElementWithinViewport(generateMispLicenceKeyButton)
				&& isElementInRightHalfOfViewport(generateMispLicenceKeyButton);
	}

	public boolean isFilterButtonDisplayed() {
		return isElementDisplayed(filterButton);
	}

	public boolean isFilterButtonPositionedTopRight() {
		return isElementWithinViewport(filterButton) && isElementInRightHalfOfViewport(filterButton);
	}

	public boolean isFilterButtonEnabled() {
		return isElementEnabled(filterButton);
	}

	public void clickOnFilterButton() {
		clickOnElement(filterButton);
	}

	public boolean isMispLicenseFilterPanelDisplayed() {
		return isElementDisplayed(mispLicenseFilterApplyButton);
	}

	public void clickOnMispLicenseFilterResetButton() {
		clickOnElement(mispLicenseFilterResetButton);
	}

	public boolean isMispLicenseFilterResetButtonDisplayed() {
		return isElementDisplayedQuick(By.id("filter_reset_btn"), POPUP_CHECK_TIMEOUT);
	}

	public boolean areMispFilterTextFieldsGenuineInputs() {
		return "input".equalsIgnoreCase(mispFilterPartnerIdInput.getTagName())
				&& "input".equalsIgnoreCase(mispFilterOrgNameInput.getTagName())
				&& "input".equalsIgnoreCase(mispFilterPolicyGroupInput.getTagName())
				&& "input".equalsIgnoreCase(mispFilterPolicyNameInput.getTagName())
				&& "input".equalsIgnoreCase(mispFilterLicenseKeyNameInput.getTagName());
	}

	public boolean isMispFilterStatusFieldADropdown() {
		return isElementDisplayed(mispFilterStatusDropdownBtn) && "button".equalsIgnoreCase(mispFilterStatusDropdownBtn.getTagName());
	}

	public String getMispFilterLicenseKeyNameSearchPlaceholder() {
		return getTextFromAttribute(mispFilterLicenseKeyNameInput, "placeholder");
	}

	public void enterMispFilterPolicyGroup(String policyGroupValue) {
		enter(mispFilterPolicyGroupInput, policyGroupValue);
	}

	public void selectMispFilterStatusActive() {
		clickOnElement(mispFilterStatusDropdownBtn);
		clickOnElement(mispFilterStatusActiveOption);
	}

	public void clickOnApplyFilterButton() {
		clickOnElement(mispLicenseFilterApplyButton);
	}

	public String getLicenseRowPolicyGroup(int rowIndex) {
		return getLicenseListRowCellText(rowIndex, 3);
	}

	public void enterMispFilterPartnerId(String value) {
		enter(mispFilterPartnerIdInput, value);
	}

	public boolean isNoResultsFoundMessageDisplayed() {
		return isElementDisplayed(noResultsFoundMessage);
	}

	public String getNoResultsFoundMessageText() {
		return getTextFromLocator(noResultsFoundMessage);
	}

	public boolean isMispFilterPartnerIdInputErrorDisplayed() {
		return isElementDisplayed(mispFilterPartnerIdInputError);
	}

	public boolean isApplyFilterButtonEnabled() {
		return isElementEnabled(mispLicenseFilterApplyButton);
	}

	public boolean areGenerateAndFilterButtonsRightEdgeAligned() {
		return areElementsRightEdgeAligned(generateMispLicenceKeyButton, filterButton, RIGHT_EDGE_ALIGNMENT_TOLERANCE_PX);
	}

	public boolean isGenerateButtonAboveFilterButton() {
		return isElementAboveOther(generateMispLicenceKeyButton, filterButton);
	}

	public boolean isAllFieldsAreMandatorySubtitleDisplayed() {
		return isElementDisplayed(allFieldsAreMandatorySubtitle);
	}

	public boolean isGenerateMispLicenceKeyHomeButtonDisplayed() {
		return isElementDisplayed(generateMispLicenceKeyHomeButton);
	}

	public boolean isMispServicesBreadcombDisplayed() {
		return isElementDisplayed(mispServicesBreadcomb);
	}

	public String getBreadcrumbText() {
		return getTextFromLocator(generateMispLicenceKeyHomeButton) + getTextFromLocator(mispServicesBreadcomb);
	}

	public void clickOnMispServicesBreadcomb() {
		clickOnElement(mispServicesBreadcomb);
	}

	public boolean isPartnerIdLabelDisplayed() {
		return isElementDisplayed(partnerIdLabel);
	}

	public boolean isPartnerTypeLabelDisplayed() {
		return isElementDisplayed(partnerTypeLabel);
	}

	public boolean isPolicyGroupLabelDisplayed() {
		return isElementDisplayed(policyGroupLabel);
	}

	public boolean isPolicyNameLabelDisplayed() {
		return isElementDisplayed(policyNameLabel);
	}

	public boolean isMispLicenceKeyLabelDisplayed() {
		return isElementDisplayed(mispLicenceKeyLabel);
	}

	public boolean isCalenderLabelDisplayed() {
		return isElementDisplayed(calenderLabel);
	}

	public boolean isClearFormButtonDisplayed() {
		return isElementDisplayed(clearFormButton);
	}

	public boolean isCancelButtonDisplayed() {
		return isElementDisplayed(cancelButton);
	}

	public boolean isSubmitButtonDisplayed() {
		return isElementDisplayed(submitButton);
	}

	public boolean isPartnerIdInfoDescriptionDisplayed() {
		return isElementDisplayed(partnerIdInfoDescription);
	}

	public void clickOnPartnerIdInfoButton() {
		clickOnElement(partnerIdInfoButton);
	}

	public void clickOnPartnerIdDropdownButton() {
		clickOnElement(partnerIdDropdownButton);
	}

	public boolean isPartnerIdDisplayedInDropdown() {
		return isElementDisplayed(partnerIdOption1);
	}

	public void clickOnPartnerIdOption1() {
		clickOnElement(partnerIdOption1);
	}

	public boolean isPolicyGroupInfoDescriptionDisplayed() {
		return isElementDisplayed(policyGroupInfoDescription);
	}

	public void clickOnPolicyGroupInfoButton() {
		clickOnElement(policyGroupInfoButton);
	}

	public boolean isPolicyNameInfoDescriptionDisplayed() {
		return isElementDisplayed(policyNameInfoDescription);
	}

	public void clickOnPolicyNameInfoButton() {
		clickOnElement(policyNameInfoButton);
	}

	public boolean isMispLicenseKeyGuidenceNoteDisplayed() {
		return isElementDisplayed(mispLicenseKeyGuidence);
	}

	public boolean isMispLicenseKeyGuidenceNoteNotEditable() {
		return isElementNotEditable(mispLicenseKeyGuidence);
	}

	public boolean isMispLicenseKeyImportantNoteDisplayed() {
		return isElementDisplayed(mispLicenseKeyImportantNote);
	}

	public boolean isMispLicenseKeyImportantNoteNotEditable() {
		return isElementNotEditable(mispLicenseKeyImportantNote);
	}

	public String getMispLicenseKeyImportantNoteText() {
		return getTextFromLocator(mispLicenseKeyImportantNote).replaceAll("\\s+", " ").trim();
	}

	public boolean isMispLicenseKeyImportantNoteUnchangedAfterTyping(String textToType) {
		String before = getMispLicenseKeyImportantNoteText();
		new Actions(driver).sendKeys(mispLicenseKeyImportantNote, textToType).perform();
		return before.equals(getMispLicenseKeyImportantNoteText());
	}

	public boolean isMispLicenseKeyImportantNoteFocusable() {
		return isElementFocusable(mispLicenseKeyImportantNote);
	}

	public boolean isMispLicenseKeyImportantNoteWithinViewport() {
		return isElementWithinViewport(mispLicenseKeyImportantNote);
	}

	public boolean isMispLicenseKeyImportantNoteCovered() {
		return isElementCoveredAtCentre(mispLicenseKeyImportantNote);
	}

	public boolean isImportantNoteOverlappingSubmitButton() {
		return doElementsOverlap(mispLicenseKeyImportantNote, submitButton);
	}

	public boolean isLicenseKeyNameFieldDisplayed() {
		return isElementDisplayed(licenseKeyNameTextbox);
	}

	public boolean isImportantNoteOverlappingLicenseKeyNameField() {
		return doElementsOverlap(mispLicenseKeyImportantNote, licenseKeyNameTextbox);
	}

	public boolean isImportantNoteOverlappingGuidenceNote() {
		return doElementsOverlap(mispLicenseKeyImportantNote, mispLicenseKeyGuidence);
	}

	public boolean isPartnerTypePlaceholderDisplayed() {
		return isElementDisplayed(partnerTypePlaceholder);
	}

	public boolean isPartnerIdPlaceholderDisplayed() {
		return isElementDisplayed(partnerIdPlaceholder);
	}

	public boolean isPolicyNamePlaceholderDisplayed() {
		return isElementDisplayed(policyNamePlaceholder);
	}

	public boolean isPolicyGroupPlaceholderDisplayed() {
		return isElementDisplayed(policyGroupPlaceholder);
	}

	public boolean isMispLicenseKeyNamePlaceholderDisplayed() {
		return isElementDisplayed(mispLicenseKeyNamePlaceholder);
	}

	public boolean isPartnerIdInfoDescriptionNotEditable() {
		return isElementNotEditable(partnerIdInfoDescription);
	}

	public void selectPartnerId(String partnerIdValue) {
		clickOnElement(partnerIdDropdownButton);
		enter(partnerIdDropdownSearchInput, partnerIdValue);
		click(By.xpath(
				"//*[contains(@id,'generate_license_key_partner_id_option')][normalize-space()='" + partnerIdValue + "']"));
	}

	public void enterInvalidPartnerId(String partnerIdValue) {
		clickOnElement(partnerIdDropdownButton);
		enter(partnerIdDropdownSearchInput, partnerIdValue);
	}

	public boolean isPartnerIdNoDataAvailableDisplayed() {
		return isElementDisplayed(partnerIdNoDataAvailableMessage);
	}

	public String getPartnerType() {
		return getTextFromLocator(partnerType);
	}

	public String getPolicyGroup() {
		return getTextFromLocator(policyGroup);
	}

	public void clickOnPolicyNameDropdown() {
		clickOnElement(policyNameDropdown);
	}

	public boolean isMispPolicyNameDisplayed() {
		return isElementDisplayed(mispPolicyName);
	}

	public boolean isMispPolicyNameDescriptionDisplayed() {
		return isElementDisplayed(mispPolicyNameDescription);
	}

	public void selectPolicyName(String policyName) {
		clickOnElement(policyNameDropdown);
		enter(policyNameSearchInput, policyName);
		clickOnElement(mispPolicyName);
	}

	public void enterLicenseKeyName(String licenseKeyName) {
		enter(licenseKeyNameTextbox, licenseKeyName);
	}

	public String getLicenseKeyNameFieldValue() {
		return getTextFromAttribute(licenseKeyNameTextbox, "value");
	}

	public void enterExpiryDate() {
		clickOnElement(expiryDate);
		clickOnElement(nextMonth);
		clickOnElement(nextMonth);
		clickOnElement(date24InCalender);
	}

	public String selectFutureDateAndGetValue() {
		clickOnElement(expiryDate);
		clickOnElement(nextMonth);
		clickOnElement(nextMonth);
		clickOnElement(date24InCalender);
		return getTextFromAttribute(expiryDate, "value");
	}

	public String reopenCalendarAndSelectAlternateDate() {
		clickOnElement(expiryDate);
		clickOnElement(nextMonth);
		clickOnElement(nextMonth);
		clickOnElement(date4InCalender);
		return getTextFromAttribute(expiryDate, "value");
	}

	public void enterPastExpiryDate() {
		clickOnElement(expiryDate);
		clickOnElement(previousMonth);
		clickOnElement(date24InCalender);
	}

	public void enterTodayExpiryDate() {
		clickOnElement(expiryDate);
		clickOnElement(todayInCalender);
	}

	public boolean isCreateLicenseKeySubmitButtonEnabled() {
		return isElementEnabled(submitButton);
	}

	public void clickOnSubmitButton() {
		clickOnElement(submitButton);
	}

	public boolean isGenerateLicenseKeyErrorMessageDisplayed() {
		return isElementDisplayed(generateLicenseKeyErrorMessage);
	}

	public boolean isNetworkErrorPageDisplayed() {
		return isElementDisplayed(networkErrorTitle);
	}

	public void clickOnNetworkErrorRetryButton() {
		clickOnElement(networkErrorRetryButton);
	}

	public String getGenerateLicenseKeyErrorText() {
		return getTextFromLocator(generateLicenseKeyErrorMessage);
	}

	public boolean isCalendarDisplayed() {
		return isElementDisplayed(calendarPopup);
	}

	public void clickOnExpiryDate() {
		clickOnElement(expiryDate);
	}

	public boolean isPolicyNameHelpTextDisplayed() {
		return isElementDisplayed(policyNamePlaceholder);
	}

	public boolean isExpiryDateCalenderInfoDescriptionDisplayed() {
		return isElementDisplayed(expiryDateCalenderInfoDescription);
	}

	public String getExpiryDateCalenderInfoDescriptionText() {
		return getTextFromLocator(expiryDateCalenderInfoDescription);
	}

	public void clickOnExpiryDateCalenderInfoIcon() {
		clickOnElement(expiryDateCalenderInfoIcon);
	}

	public boolean areInfoIconsColorAndFontSizeConsistent() {
		String[] colors = { partnerIdInfoButton.getCssValue("color"), policyGroupInfoButton.getCssValue("color"),
				policyNameInfoButton.getCssValue("color"), expiryDateCalenderInfoIcon.getCssValue("color") };
		String[] fontSizes = { partnerIdInfoButton.getCssValue("font-size"),
				policyGroupInfoButton.getCssValue("font-size"), policyNameInfoButton.getCssValue("font-size"),
				expiryDateCalenderInfoIcon.getCssValue("font-size") };

		for (int i = 1; i < colors.length; i++) {
			if (!colors[i].equals(colors[0]) || !fontSizes[i].equals(fontSizes[0])) {
				return false;
			}
		}
		return true;
	}

	public void clickOnCancelButton() {
		clickOnElement(cancelButton);
	}

	public void clickOnClearFormButton() {
		clickOnElement(clearFormButton);
	}

	public boolean isInvalidCharacterErrorMessageDisplayed() {
		return isElementDisplayed(invalidCharacterErrorMessage);
	}

	public boolean isMispLicenseKeyPopupDisplayed() {
		return isElementDisplayedQuick(By.id("copy_id_close_btn"), POPUP_CHECK_TIMEOUT);
	}

	public String getCopyIdPopupTitleText() {
		return getTextFromLocator(copyIdPopupTitle);
	}

	public String getCopyIdPopupSubtitleText() {
		return getTextFromLocator(copyIdPopupSubtitle);
	}

	public String getCopyIdPopupAlertMessageText() {
		return getTextFromLocator(copyIdPopupAlertMessage);
	}

	public void clickOnCopyIdButton() {
		clickOnElement(copyIdButton);
	}

	public boolean isCopiedTextDisplayed() {
		return isElementDisplayed(copiedToast);
	}

	public boolean isCopyButtonRevertedWithinFewSeconds() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
		try {
			return wait.until(ExpectedConditions.invisibilityOfElementLocated(
					By.cssSelector("#copy_id_btn[class*='bg-[#1447B2]']")));
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void closeCopyIdPopup() {
		clickOnElement(mispLicenseKeyPopupCloseButton);
		clickOnElement(confirmationGoBackBtn);
	}

	public void clickOnPopupCloseButton() {
		clickOnElement(mispLicenseKeyPopupCloseButton);
	}

	public boolean isLicenseKeyConfirmationHeaderDisplayed() {
		return isElementDisplayed(licenseKeyConfirmationHeader);
	}

	public String getLicenseKeyConfirmationHeaderText() {
		return getTextFromLocator(licenseKeyConfirmationHeader);
	}

	public boolean isLicenseKeyConfirmationHeaderNotEditable() {
		return isElementNotEditable(licenseKeyConfirmationHeader);
	}

	public boolean isConfirmationSuccessIconDisplayed() {
		return isElementDisplayed(confirmationSuccessIcon);
	}

	public void clickOnConfirmationGoBackButton() {
		clickOnElement(confirmationGoBackBtn);
	}

	public void clickOnConfirmationHomeButton() {
		clickOnElement(confirmationHomeBtn);
	}

	public boolean isPartnerIdHeaderDisplayed() {
		return isElementDisplayed(partnerIdHeader);
	}

	public boolean isOrgNameHeaderDisplayed() {
		return isElementDisplayed(orgNameHeader);
	}

	public boolean isPolicyGroupHeaderDisplayed() {
		return isElementDisplayed(policyGroupHeader);
	}

	public boolean isPolicyNameHeaderDisplayed() {
		return isElementDisplayed(policyNameHeader);
	}

	public boolean isMispLicenseKeyNameHeaderDisplayed() {
		return isElementDisplayed(mispLicenseKeyNameHeader);
	}

	public boolean isCreationDateHeaderDisplayed() {
		return isElementDisplayed(creationDateHeader);
	}

	public boolean isExpirationDateHeaderDisplayed() {
		return isElementDisplayed(expirationDateHeader);
	}

	public boolean isStatusHeaderDisplayed() {
		return isElementDisplayed(statusHeader);
	}

	public boolean isMispLicenseKeyHeaderDisplayed() {
		return isElementDisplayed(mispLicenseKeyHeader);
	}

	public boolean isActionHeaderDisplayed() {
		return isElementDisplayed(actionHeader);
	}

	public String getPartnerIdHeaderText() {
		return getTextFromLocator(partnerIdHeader);
	}

	public String getOrgNameHeaderText() {
		return getTextFromLocator(orgNameHeader);
	}

	public String getPolicyGroupHeaderText() {
		return getTextFromLocator(policyGroupHeader);
	}

	public String getPolicyNameHeaderText() {
		return getTextFromLocator(policyNameHeader);
	}

	public String getMispLicenseKeyNameHeaderText() {
		return getTextFromLocator(mispLicenseKeyNameHeader);
	}

	public String getCreationDateHeaderText() {
		return getTextFromLocator(creationDateHeader);
	}

	public String getExpirationDateHeaderText() {
		return getTextFromLocator(expirationDateHeader);
	}

	public String getStatusHeaderText() {
		return getTextFromLocator(statusHeader);
	}

	public String getMispLicenseKeyHeaderText() {
		return getTextFromLocator(mispLicenseKeyHeader);
	}

	public String getActionHeaderText() {
		return getTextFromLocator(actionHeader);
	}

	public int getMispLicenseListRowCount() {
		return getElementCount(By.cssSelector("tr[id*='misp_license_list_item']"));
	}

	public boolean isEmptyMispLicenseListHeadersDisplayed() {
		return isDisplayed(By.id("partnerId")) && isDisplayed(By.id("orgName")) && isDisplayed(By.id("policyGroupName"))
				&& isDisplayed(By.id("policyName")) && isDisplayed(By.id("mispLicenseKeyName"))
				&& isDisplayed(By.id("createdDateTime")) && isDisplayed(By.id("expiryDateTime"))
				&& isDisplayed(By.id("status")) && isDisplayed(By.id("mispLicenseKey")) && isDisplayed(By.id("action"));
	}

	public boolean isMispLicenseListSubTitleDisplayed() {
		return isElementDisplayed(mispLicenseListSubTitleWithCount);
	}

	public String getMispLicenseListSubTitleText() {
		return getTextFromLocator(mispLicenseListSubTitleWithCount);
	}

	public String getLicenseListRowCellText(int rowIndex, int cellIndex) {
		return getTextFromLocator(
				By.cssSelector("#misp_license_list_item" + rowIndex + " > td:nth-child(" + cellIndex + ")"));
	}

	public String getLatestLicenseRowPartnerId() {
		return getLicenseListRowCellText(1, 1);
	}

	public String getLatestLicenseRowStatus() {
		return getLicenseListRowCellText(1, 8);
	}
	
	public boolean waitUntilLatestLicenseRowStatusEquals(String expectedStatus) {
		try {
			return new WebDriverWait(driver, Duration.ofSeconds(30))
					.until(d -> expectedStatus.equals(getLatestLicenseRowStatus()));
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getLicenseRowCreationDate(int rowIndex) {
		return getLicenseListRowCellText(rowIndex, 6);
	}

	public String getLicenseRowExpirationDate(int rowIndex) {
		return getLicenseListRowCellText(rowIndex, 7);
	}

	public String getBrowserTodayLocaleDateString() {
		return executeScriptForString("return new Date().toLocaleDateString();");
	}

	public boolean isDateCellFormatConsistentWithBrowserLocale(String cellText, String browserSampleDateText) {
		return cellText.replaceAll("\\d+", "#").equals(browserSampleDateText.replaceAll("\\d+", "#"));
	}

	public String getLicenseRowStatus(int rowIndex) {
		return getLicenseListRowCellText(rowIndex, 8);
	}

	public String getLicenseRowLicenseKeyName(int rowIndex) {
		return getLicenseListRowCellText(rowIndex, 5);
	}

	public void clickOnViewLicenseKeyButton(int rowIndex) {
		click(By.id("misp_license_show_copy_popup_btn" + rowIndex));
	}

	public String getMispLicenseKeyIdText() {
		return getTextFromLocator(mispLicenseKeyId);
	}

	public boolean isMispLicenseKeyPopupHeaderDisplayed() {
		return isElementDisplayed(mispLicenseKeyPopup);
	}

	public String getMispLicenseKeyPopupHeaderText() {
		return getTextFromLocator(mispLicenseKeyPopup);
	}

	public boolean isMispLicenseKeyIdBold() {
		String fontWeight = getComputedStyle(mispLicenseKeyId, "font-weight");
		return "700".equals(fontWeight) || "bold".equalsIgnoreCase(fontWeight);
	}

	public String getPageTitleText() {
		return getTextFromLocator(generateMispLicenceKeyTitle);
	}

	public void clickOnMispLicenseListActionButton() {
		clickOnElement(mispLicenseListActionButton);
	}

	public boolean isMispLicenseListActionMenuDisplayed() {
		return isElementDisplayed(mispLicenseListViewButton);
	}

	public boolean isMispLicenseListRegenerateButtonDisplayed() {
		return isElementDisplayed(mispLicenseListRegenerateButton);
	}

	public boolean isMispLicenseListRegenerateButtonEnabled() {
		String classAttr = getTextFromAttribute(mispLicenseListRegenerateButton, "class");
		return classAttr != null && classAttr.contains("text-[#3E3E3E]");
	}

	public boolean isMispLicenseListDeactivateButtonDisplayed() {
		return isElementDisplayed(mispLicenseListDeactivateButton);
	}

	public boolean isMispLicenseListDeactivateButtonEnabled() {
		String classAttr = getTextFromAttribute(mispLicenseListDeactivateButton, "class");
		return classAttr != null && classAttr.contains("text-[#3E3E3E]");
	}

	public boolean isMispLicenseListEyeIconPresentForRow(int rowIndex) {
		return isElementDisplayedQuick(By.id("misp_license_show_copy_popup_btn" + rowIndex), POPUP_CHECK_TIMEOUT);
	}

	private static final String LICENSE_ROW_ACTIVE_TEXT_COLOR = "rgb(25,25,25)";
	private static final String LICENSE_ROW_DEACTIVATED_TEXT_COLOR = "rgb(150,150,150)";
	private static final String STATUS_PILL_ACTIVE_BG_COLOR = "rgb(209,250,223)";

	private static String normalizeRgb(String rgb) {
		return rgb == null ? null : rgb.replaceAll("\\s", "");
	}

	public boolean isLicenseRowGreyedOut(int rowIndex) {
		WebElement row = waitAndFindElement(By.id("misp_license_list_item" + rowIndex));
		return LICENSE_ROW_DEACTIVATED_TEXT_COLOR.equals(normalizeRgb(getComputedStyle(row, "color")));
	}

	public boolean isLicenseRowNormalColor(int rowIndex) {
		WebElement row = waitAndFindElement(By.id("misp_license_list_item" + rowIndex));
		return LICENSE_ROW_ACTIVE_TEXT_COLOR.equals(normalizeRgb(getComputedStyle(row, "color")));
	}

	public boolean isLicenseRowStatusPillGreen(int rowIndex) {
		WebElement statusPill = waitAndFindElement(
				By.cssSelector("#misp_license_list_item" + rowIndex + " > td:nth-child(8) > div"));
		return STATUS_PILL_ACTIVE_BG_COLOR.equals(normalizeRgb(getComputedStyle(statusPill, "background-color")));
	}

	public boolean isMispLicenseListActionButtonNormalColor() {
		return LICENSE_ROW_ACTIVE_TEXT_COLOR
				.equals(normalizeRgb(getComputedStyle(mispLicenseListActionButton, "color")));
	}

	public String getSelectedRecordsPerPageText() {
		return getTextFromLocator(By.id("selected_records_count"));
	}

	public void clickOnPaginationRecordsPerPageDropdown() {
		click(By.id("pagination_select_record_per_page"));
	}

	public void selectPaginationRecordsPerPageOption(int optionIndex) {
		click(By.id("pagination_each_num_option" + optionIndex));
	}

	private static final DateTimeFormatter MISP_LIST_CREATION_DATE_FORMAT = DateTimeFormatter.ofPattern("M/d/yyyy",
			Locale.US);

	public boolean isTableSortedDescendingByCreationDate() {
		List<String> actualText = getMispLicenseListColumnValues(6);
		List<LocalDate> actual = new ArrayList<>();
		for (String text : actualText) {
			try {
				actual.add(LocalDate.parse(text, MISP_LIST_CREATION_DATE_FORMAT));
			} catch (DateTimeParseException e) {
				LogUtil.step("Creation date \"" + text + "\" did not match the expected M/d/yyyy format; cannot verify sort order.");
				return false;
			}
		}
		List<LocalDate> expected = new ArrayList<>(actual);
		expected.sort(Comparator.reverseOrder());
		return actual.equals(expected);
	}

	public boolean waitUntilTableSortedDescendingByCreationDate() {
		try {
			return new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(d -> isTableSortedDescendingByCreationDate());
		} catch (TimeoutException e) {
			return false;
		}
	}

	private static final String[] SORTABLE_MISP_LIST_COLUMN_IDS = { "partnerId", "orgName", "policyGroupName",
			"policyName", "mispLicenseKeyName", "createdDateTime", "expiryDateTime", "status" };

	public boolean areAllSortIconsPresentExceptActionAndLicenseKey() {
		for (String columnId : SORTABLE_MISP_LIST_COLUMN_IDS) {
			if (!isDisplayed(By.id(columnId + "_asc_icon")) || !isDisplayed(By.id(columnId + "_desc_icon"))) {
				return false;
			}
		}
		return !isElementDisplayedQuick(By.id("action_asc_icon"), POPUP_CHECK_TIMEOUT)
				&& !isElementDisplayedQuick(By.id("mispLicenseKey_asc_icon"), POPUP_CHECK_TIMEOUT);
	}

	public void clickOnSortAscIcon(String columnId) {
		click(By.id(columnId + "_asc_icon"));
	}

	public void clickOnSortDescIcon(String columnId) {
		click(By.id(columnId + "_desc_icon"));
	}

	private static final String SORT_ICON_ACTIVE_FILL = "#1447b2";

	public boolean isSortAscIconActive(String columnId) {
		return SORT_ICON_ACTIVE_FILL.equalsIgnoreCase(
				getTextFromAttribute(By.cssSelector("#" + columnId + "_asc_icon > :first-child"), "fill"));
	}

	public boolean isSortDescIconActive(String columnId) {
		return SORT_ICON_ACTIVE_FILL.equalsIgnoreCase(
				getTextFromAttribute(By.cssSelector("#" + columnId + "_desc_icon > :first-child"), "fill"));
	}

	private List<String> getMispLicenseListColumnValues(int columnIndex) {
		By locator = By
				.cssSelector("tr[id^='misp_license_list_item'] > td:nth-child(" + columnIndex + ")");
		for (int attempt = 1; attempt <= STALE_RETRY; attempt++) {
			try {
				List<WebElement> cells = new WebDriverWait(driver, Duration.ofSeconds(30))
						.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, 0));
				List<String> values = new ArrayList<>();
				for (WebElement cell : cells) {
					values.add(cell.getText().trim());
				}
				return values;
			} catch (StaleElementReferenceException stale) {
				LogUtil.step("Column read went stale. Retry " + attempt + "/" + STALE_RETRY);
			} catch (TimeoutException noRows) {
				LogUtil.step("No rows rendered for column: " + locator);
				return new ArrayList<>();
			}
		}
		throw new RuntimeException("Column still stale after " + STALE_RETRY + " retries: " + locator);
	}

	public boolean isTableSortedAscendingByColumn(int columnIndex) {
		List<String> actual = getMispLicenseListColumnValues(columnIndex);
		if (actual.isEmpty()) {
			LogUtil.step("No values read for column " + columnIndex + "; cannot verify sort order.");
			return false;
		}
		List<String> expected = new ArrayList<>(actual);
		expected.sort(String.CASE_INSENSITIVE_ORDER);
		return actual.equals(expected);
	}

	public boolean isTableSortedDescendingByColumn(int columnIndex) {
		List<String> actual = getMispLicenseListColumnValues(columnIndex);
		if (actual.isEmpty()) {
			LogUtil.step("No values read for column " + columnIndex + "; cannot verify sort order.");
			return false;
		}
		List<String> expected = new ArrayList<>(actual);
		expected.sort(String.CASE_INSENSITIVE_ORDER.reversed());
		return actual.equals(expected);
	}

	public boolean waitUntilTableSortedAscendingByColumn(int columnIndex) {
		try {
			return new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(d -> isTableSortedAscendingByColumn(columnIndex));
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean waitUntilTableSortedDescendingByColumn(int columnIndex) {
		try {
			return new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(d -> isTableSortedDescendingByColumn(columnIndex));
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean waitUntilMispLicenseListRowCountSatisfies(java.util.function.IntPredicate condition) {
		try {
			return new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(d -> condition.test(getMispLicenseListRowCount()));
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isViewMispLicenseKeyDetailsPageDisplayed() {
		return isElementDisplayed(viewMispLicenseKeyExpiryDateContext);
	}

	public boolean isViewMispLicenseKeyDetailsPageDisplayedQuick() {
		return isElementDisplayedQuick(By.id("misp_license_key_details_expiry_date_context"), POPUP_CHECK_TIMEOUT);
	}

	public String getViewMispLicenseKeyDetailsStatusText() {
		return getTextFromLocator(viewMispLicenseKeyDetailsStatus).trim();
	}

	public void clickOnLicenseRowPartnerIdCell(int rowIndex) {
		click(By.cssSelector("#misp_license_list_item" + rowIndex + " > td:nth-child(1)"));
	}

	public void clickOnMispLicenseListRegenerateButton() {
		clickOnElement(mispLicenseListRegenerateButton);
	}

	public boolean isRegenerateMandatoryFieldsSubtitleDisplayed() {
		return isElementDisplayed(regenerateMandatoryFieldsSubtitle);
	}

	public String getRegenerateMandatoryFieldsSubtitleText() {
		return getTextFromLocator(regenerateMandatoryFieldsSubtitle);
	}

	public boolean isRegeneratePartnerIdLabelDisplayed() {
		return isElementDisplayed(regeneratePartnerIdLabel);
	}

	public boolean isRegeneratePartnerTypeLabelDisplayed() {
		return isElementDisplayed(regeneratePartnerTypeLabel);
	}

	public boolean isRegeneratePolicyGroupLabelDisplayed() {
		return isElementDisplayed(regeneratePolicyGroupLabel);
	}

	public boolean isRegeneratePolicyNameLabelDisplayed() {
		return isElementDisplayed(regeneratePolicyNameLabel);
	}

	public boolean isRegenerateLicenseKeyNameLabelDisplayed() {
		return isElementDisplayed(regenerateLicenseKeyNameLabel);
	}

	public String getRegeneratePartnerId() {
		return getTextFromLocator(regeneratePartnerId);
	}

	public String getRegeneratePartnerType() {
		return getTextFromLocator(regeneratePartnerType);
	}

	public String getRegeneratePolicyGroup() {
		return getTextFromLocator(regeneratePolicyGroup);
	}

	public String getRegeneratePolicyName() {
		return getTextFromLocator(regeneratePolicyName);
	}

	public boolean isRegeneratePartnerIdFieldDisabled() {
		return isElementDisabled(regeneratePartnerId);
	}

	public boolean isRegeneratePartnerTypeFieldDisabled() {
		return isElementDisabled(regeneratePartnerType);
	}

	public boolean isRegeneratePolicyGroupFieldDisabled() {
		return isElementDisabled(regeneratePolicyGroup);
	}

	public boolean isRegeneratePolicyGroupPlaceholderWithinViewport() {
		return isElementWithinViewport(regeneratePolicyGroup);
	}

	public boolean isRegeneratePolicyNamePlaceholderWithinViewport() {
		return isElementWithinViewport(regeneratePolicyName);
	}

	public boolean isRegeneratePolicyNameFieldDisabled() {
		return isElementDisabled(regeneratePolicyName);
	}

	public boolean isRegenerateLicenseKeyNameFieldDisplayed() {
		return isElementDisplayed(regenerateLicenseKeyNameTextbox);
	}

	public boolean isRegenerateLicenseKeyNameFieldEnabled() {
		return isElementEnabled(regenerateLicenseKeyNameTextbox);
	}

	public String getRegenerateLicenseKeyNameFieldValue() {
		return getTextFromAttribute(regenerateLicenseKeyNameTextbox, "value");
	}

	public void enterRegenerateLicenseKeyName(String licenseKeyName) {
		enter(regenerateLicenseKeyNameTextbox, licenseKeyName);
	}

	public String getRegenerateLicenseKeyNameHelpText() {
		return getTextFromAttribute(regenerateLicenseKeyNameTextbox, "placeholder");
	}

	public boolean isRegenerateLicenseKeyNameHelpTextDisabledForEdit() {
		String helpTextBefore = getRegenerateLicenseKeyNameHelpText();
		enterRegenerateLicenseKeyName(GlobalConstants.MISP_LICENSEKEY_REGENERATE_TEMP);
		boolean unchangedWhileTyped = helpTextBefore.equals(getRegenerateLicenseKeyNameHelpText());
		clickOnRegenerateClearFormButton();
		boolean unchangedAfterClear = helpTextBefore.equals(getRegenerateLicenseKeyNameHelpText());
		return unchangedWhileTyped && unchangedAfterClear;
	}

	public boolean isRegenerateExpiryDateFieldDisplayed() {
		return isElementDisplayed(regenerateExpiryDate);
	}

	public boolean isRegenerateClearFormButtonDisplayed() {
		return isElementDisplayed(regenerateClearFormButton);
	}

	public boolean isRegenerateCancelButtonDisplayed() {
		return isElementDisplayed(regenerateCancelButton);
	}

	public boolean isRegenerateSubmitButtonDisplayed() {
		return isElementDisplayed(regenerateSubmitButton);
	}

	public boolean isRegenerateSubmitButtonEnabled() {
		return isElementEnabled(regenerateSubmitButton);
	}

	public void clickOnRegenerateCancelButton() {
		clickOnElement(regenerateCancelButton);
	}

	public boolean isRegenerateImportantNoteDisplayed() {
		return isElementDisplayed(regenerateImportantNote);
	}

	public boolean isRegenerateInvalidCharacterErrorMessageDisplayed() {
		return isElementDisplayed(regenerateInvalidCharacterErrorMessage);
	}

	public boolean isRegenerateErrorMessageDisplayed() {
		return isElementDisplayed(regenerateErrorMessage);
	}

	public String getRegenerateErrorMessageText() {
		return getTextFromLocator(regenerateErrorMessage);
	}

	public boolean isRegenerateConfirmationHeaderDisplayed() {
		return isElementDisplayed(regenerateConfirmationHeader);
	}

	public String getRegenerateConfirmationHeaderText() {
		return getTextFromLocator(regenerateConfirmationHeader);
	}

	public boolean isRegenerateConfirmationHeaderNotEditable() {
		return isElementNotEditable(regenerateConfirmationHeader);
	}

	public void clickOnRegenerateClearFormButton() {
		clickOnElement(regenerateClearFormButton);
	}

	public void clickOnRegenerateSubmitButton() {
		clickOnElement(regenerateSubmitButton);
	}

	public void enterRegenerateExpiryDate() {
		clickOnElement(regenerateExpiryDate);
		clickOnElement(nextMonth);
		clickOnElement(nextMonth);
		clickOnElement(date24InCalender);
	}

	public void clickOnRegenerateExpiryDateField() {
		clickOnElement(regenerateExpiryDate);
	}

	private static final By CURRENT_MONTH_HEADER = By.cssSelector(".react-datepicker__current-month");

	public String getCalendarMonthHeaderText() {
		WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30)).until(webDriver -> {
			List<WebElement> matches = webDriver.findElements(CURRENT_MONTH_HEADER);
			Optional<WebElement> visible = matches.stream().filter(el -> {
				try {
					return el.isDisplayed();
				} catch (StaleElementReferenceException stale) {
					return false;
				}
			}).findFirst();
			return visible.orElse(null);
		});
		return element.getText();
	}

	public void enterFreeTextIntoRegenerateExpiryDateField(String text) {
		waitForElementVisible(regenerateExpiryDate);
		regenerateExpiryDate.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		regenerateExpiryDate.sendKeys(Keys.DELETE);
		regenerateExpiryDate.sendKeys(text);
	}

	public String getRegenerateExpiryDateFieldValue() {
		return getTextFromAttribute(regenerateExpiryDate, "value");
	}

	public String reopenRegenerateCalendarAndSelectAlternateDate() {
		clickOnElement(regenerateExpiryDate);
		clickOnElement(nextMonth);
		clickOnElement(nextMonth);
		clickOnElement(date4InCalender);
		return getTextFromAttribute(regenerateExpiryDate, "value");
	}

	public void clickOnMispLicenseListViewButton() {
		clickOnElement(mispLicenseListViewButton);
	}

	public boolean isViewMispLicenseKeyExpiryDateNotEditable() {
		return isElementNotEditable(viewMispLicenseKeyExpiryDateContext);
	}

	public void clickOnViewMispLicenseKeyBackButton() {
		clickOnElement(viewMispLicenseKeyBackButton);
	}

	public boolean isCancelConfirmationPopupDisplayed() {
		return isElementDisplayed(cancelConfirmationPopup);
	}

	public void clickOnCancelConfirmationPopupProceedButton() {
		clickOnElement(cancelConfirmationPopupProceedButton);
	}

	public String selectFutureDateInOpenCalendarAndGetRegenerateValue() {
		clickOnElement(nextMonth);
		clickOnElement(nextMonth);
		clickOnElement(date24InCalender);
		return getTextFromAttribute(regenerateExpiryDate, "value");
	}

	public void clickOnRegenerateExpiryDateCalenderInfoIcon() {
		clickOnElement(regenerateExpiryDateCalenderInfoIcon);
	}

	public boolean isRegenerateExpiryDateCalenderInfoDescriptionDisplayed() {
		return isElementDisplayed(regenerateExpiryDateCalenderInfoDescription);
	}

	public String getRegenerateExpiryDateCalenderInfoDescriptionText() {
		return getTextFromLocator(regenerateExpiryDateCalenderInfoDescription);
	}

	public boolean isRegenerateExpiryDateCalenderInfoDescriptionNotEditable() {
		return isElementNotEditable(regenerateExpiryDateCalenderInfoDescription);
	}

	public String getRegenerateExpiryDateCalenderInfoIconCursor() {
		return getComputedStyle(regenerateExpiryDateCalenderInfoIcon, "cursor");
	}

	public void enterRegeneratePastExpiryDate() {
		clickOnElement(regenerateExpiryDate);
		clickOnElement(previousMonth);
		clickOnElement(date24InCalender);
	}

	public void enterRegenerateTodayExpiryDate() {
		clickOnElement(regenerateExpiryDate);
		clickOnElement(todayInCalender);
	}

	public void clickOnMispLicenseListDeactivateButton() {
		clickOnElement(mispLicenseListDeactivateButton);
	}

	public boolean isDeactivatePopupHeaderDisplayed() {
		return isElementDisplayed(deactivatePopupHeader);
	}

	public String getDeactivatePopupTitleText() {
		return getTextFromLocator(deactivatePopupHeader).trim();
	}

	public boolean isDeactivatePopupDescriptionDisplayed() {
		return isElementDisplayed(deactivatePopupDescription);
	}

	public String getDeactivatePopupDescriptionText() {
		return getTextFromLocator(deactivatePopupDescription).trim();
	}

	public boolean isDeactivateSubmitButtonDisplayed() {
		return isElementDisplayed(deactivateSubmitButton);
	}

	public boolean isDeactivateSubmitButtonEnabled() {
		return isElementEnabled(deactivateSubmitButton);
	}

	public void clickOnDeactivateSubmitButton() {
		clickOnElement(deactivateSubmitButton);
	}

	public boolean isDeactivateCancelButtonDisplayed() {
		return isElementDisplayed(deactivateCancelButton);
	}

	public boolean isDeactivateCancelButtonEnabled() {
		return isElementEnabled(deactivateCancelButton);
	}

	public void clickOnDeactivateCancelButton() {
		clickOnElement(deactivateCancelButton);
	}

	public boolean isDeactivatePopupHeaderDisplayedQuick() {
		return isElementDisplayedQuick(By.id("deactivate_popup_header"), POPUP_CHECK_TIMEOUT);
	}
}

