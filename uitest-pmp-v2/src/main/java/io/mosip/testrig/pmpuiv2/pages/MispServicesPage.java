package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpuiv2.utility.LogUtil;

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

	@FindBy(xpath = "//button[@id='generate_license_key_policy_name_dropdown_btn']//span")
	private WebElement policyNamePlaceholder;

	@FindBy(xpath = "//button[@id='generate_license_key_policy_group']//span")
	private WebElement policyGroupPlaceholder;

	@FindBy(xpath = "//button[@id='generate_license_key_partner_id_dropdown_btn']//span")
	private WebElement partnerIdPlaceholder;

	@FindBy(xpath = "//button[@id='generate_license_key_partner_type']//span")
	private WebElement partnerTypePlaceholder;

	@FindBy(xpath = "//input[@data-placeholder-id='generate_license_key_name_placeholder']")
	private WebElement mispLicenseKeyNamePlaceholder;

	@FindBy(id = "generate_license_key_partner_id_search_input")
	private WebElement partnerIdDropdownSearchInput;

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

	@FindBy(xpath = "//*[text()='Next Month']")
	private WebElement nextMonth;

	@FindBy(xpath = "//*[text()='Previous Month']")
	private WebElement previousMonth;

	@FindBy(xpath = "//*[contains(@class, 'react-datepicker__day react-datepicker__day--024') and not(contains(@class, 'react-datepicker__day--outside-month'))]")
	private WebElement date24InCalender;

	@FindBy(xpath = "//*[contains(@class, 'react-datepicker__day react-datepicker__day--004') and not(contains(@class, 'react-datepicker__day--outside-month'))]")
	private WebElement date4InCalender;

	@FindBy(id = "generate_license_key_expiry_date_calender")
	private WebElement expiryDate;

	@FindBy(id = "copy_id_popup_tile")
	private WebElement copyIdPopupTitle;

	@FindBy(id = "copy_id_popup_sub_title")
	private WebElement copyIdPopupSubtitle;

	@FindBy(id = "copy_id_close_btn")
	private WebElement mispLicenseKeyPopupCloseButton;

	@FindBy(id = "copy_id_popup_header")
	private WebElement mispLicenseKeyPopup;

	@FindBy(id = "copy_id_popup_id")
	private WebElement mispLicenseKeyId;

	@FindBy(id = "copy_id_btn")
	private WebElement copyIdButton;

	@FindBy(id = "Copied!")
	private WebElement copiedToast;

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

	@FindBy(xpath = "//div[contains(@class,'react-datepicker')]")
	private WebElement calendarPopup;

	@FindBy(xpath = "//span[contains(text(),'Select MISP policy for which MISP License Key is required')]")
	private WebElement policyNameHelpText;

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

	// Whitespace is collapsed so the assertion survives the note re-wrapping at a different width.
	public String getMispLicenseKeyImportantNoteText() {
		return getTextFromLocator(mispLicenseKeyImportantNote).replaceAll("\\s+", " ").trim();
	}

	// isElementNotEditable only rules out input/textarea tags, which a <p> note passes by
	// construction. This actually clicks the note and types at it, then reports whether the
	// text survived unchanged - the behaviour the test case describes.
	public boolean isMispLicenseKeyImportantNoteUnchangedAfterTyping(String textToType) {
		String before = getMispLicenseKeyImportantNoteText();
		try {
			clickOnElement(mispLicenseKeyImportantNote);
			new Actions(driver).sendKeys(textToType).perform();
		} catch (Exception notInteractable) {
			LogUtil.step("Note rejected the interaction outright: " + notInteractable.getClass().getSimpleName());
		}
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
		click(By.xpath(
				"//*[contains(@id,'generate_license_key_policy_name_option')][normalize-space()='" + policyName + "']"));
	}

	public void enterLicenseKeyName(String licenseKeyName) {
		enter(licenseKeyNameTextbox, licenseKeyName);
	}

	public void enterExpiryDate() {
		clickOnElement(expiryDate);
		clickOnElement(nextMonth);
		clickOnElement(nextMonth);
		clickOnElement(date24InCalender);
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
		return isElementDisplayed(policyNameHelpText);
	}

	public boolean isExpiryDateCalenderInfoDescriptionDisplayed() {
		return isElementDisplayed(expiryDateCalenderInfoDescription);
	}

	public void clickOnExpiryDateCalenderInfoIcon() {
		clickOnElement(expiryDateCalenderInfoIcon);
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

	public void closeCopyIdPopup() {
		clickOnElement(mispLicenseKeyPopupCloseButton);
		clickOnElement(confirmationGoBackBtn);
	}
}
