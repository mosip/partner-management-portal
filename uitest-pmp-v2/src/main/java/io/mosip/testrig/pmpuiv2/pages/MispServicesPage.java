package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MispServicesPage extends BasePage {

	@FindBy(id = "generate_misp_license_key_btn")
	private WebElement generateMispLicenceKeyButton;

	@FindBy(id = "page_title")
	private WebElement generateMispLicenceKeyTitle;

	@FindBy(id = "sub_title_btn")
	private WebElement mispServicesBreadcomb;

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

	public boolean isAllFieldsAreMandatorySubtitleDisplayed() {
		return isElementDisplayed(allFieldsAreMandatorySubtitle);
	}

	public boolean isGenerateMispLicenceKeyHomeButtonDisplayed() {
		return isElementDisplayed(generateMispLicenceKeyHomeButton);
	}

	public boolean isMispServicesBreadcombDisplayed() {
		return isElementDisplayed(mispServicesBreadcomb);
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

	public String getLicenseListRowCellText(int rowIndex, int cellIndex) {
		return getTextFromLocator(
				By.xpath("//tr[@id='misp_license_list_item" + rowIndex + "']/td[" + cellIndex + "]"));
	}

	public String getLatestLicenseRowPartnerId() {
		return getLicenseListRowCellText(1, 1);
	}

	public String getLatestLicenseRowStatus() {
		return getLicenseListRowCellText(1, 8);
	}

	public void clickOnViewLicenseKeyButton(int rowIndex) {
		click(By.id("misp_license_show_copy_popup_btn" + rowIndex));
	}

	public String getMispLicenseKeyIdText() {
		return getTextFromLocator(mispLicenseKeyId);
	}
}
