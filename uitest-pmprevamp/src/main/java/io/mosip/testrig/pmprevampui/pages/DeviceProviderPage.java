package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmprevampui.fw.util.PmpTestUtil;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class DeviceProviderPage extends BasePage {

	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;

	@FindBy(id = "add_sbi_btn")
	private WebElement addSbiButton;

	@FindBy(id = "sbi_list_add_sbi_btn")
	private WebElement addSbiFromSbiListButton;

	@FindBy(id = "add_sbi_partner_id")
	private WebElement addSbiPartnerId;

	@FindBy(id = "add_sbi_partner_id_option1")
	private WebElement sbiPartnerOption;

	@FindBy(id = "add_sbi_software_version_input")
	private WebElement sbiVersion;

	@FindBy(id = "binary_hash_input")
	private WebElement sbiBinaryHash;

	@FindBy(id = "sbi_created_date_calender")
	private WebElement createdDate;

	@FindBy(id = "sbi_expiry_date_calender")
	private WebElement expiryDate;

	@FindBy(id = "add_sbi_form_clear_btn")
	private WebElement clearForm;

	@FindBy(id = "add_sbi_cancel_btn")
	private WebElement cancel;

	@FindBy(id = "add_sbi_submit_btn")
	private WebElement submit;

	@FindBy(id = "sub_title_btn")
	private WebElement listOfSbiTitleButton;

	@FindBy(id = "add_sbi_partner_id_info")
	private WebElement partnerIdInfo;

	@FindBy(id = "sbi_expiry_date_calender_info")
	private WebElement expiryDateInfo;

	@FindBy(id = "block_message_cancel")
	private WebElement navigationAlertCancel;

	@FindBy(xpath = "//*[contains(@class, 'w-full break-words')]")
	private WebElement partnerType;

	@FindBy(xpath = "//*[contains(@class, 'flex items-center justify-between w-full')]")
	private WebElement partnerTypeBox;

	@FindBy(xpath = "//*[contains(@class, 'react-datepicker__day react-datepicker__day--024')]")
	private WebElement date24InCalender;

	@FindBy(xpath = "//*[contains(@class, 'react-datepicker__day react-datepicker__day--004')]")
	private WebElement date4InCalender;

	@FindBy(xpath = "//*[text()='Next Month']")
	private WebElement nextMonth;

	@FindBy(xpath = "//*[text()='Previous Month']")
	private WebElement previousMonth;

	@FindBy(xpath = "//*[text()='List of SBI']")
	private WebElement sbiList;

	@FindBy(xpath = "//*[text()='Add SBI details']")
	private WebElement addSbiDetailsTitle;

	@FindBy(xpath = "//*[text()='All fields marked with']")
	private WebElement addSbiDetailsSubTitle;

	@FindBy(xpath = "//*[text()='After the below details are successfully submitted, the SBI request will be sent to admin for approval.']")
	private WebElement addSbiDetailsInfoMessage;

	@FindBy(xpath = "//*[contains(text(), 'Only those partner IDs')]")
	private WebElement partnerIdInfoMessage;

	@FindBy(xpath = "//p[contains(text(), 'SBI Expiry date')]")
	private WebElement expiryDateInfoMessage;

	@FindBy(xpath = "//p[contains(text(), 'Enter date')]")
	private WebElement expiryDateInfoMessageTwo;

	@FindBy(xpath = "//p[text()='Expiry Date should be greater than current date.']")
	private WebElement sameDateErrorMessage;

	@FindBy(xpath = "//p[text()='The created date should be less than or equal to the current date.']")
	private WebElement futureDateErrorMessage;

	@FindBy(xpath = "//p[text()='SBI details exists for given SBI Version. Multiple SBI with same SBI Version cannot be added.']")
	private WebElement duplicateSbiErrorMessage;

	@FindBy(xpath = "//p[text()='Your changes will be lost, are you sure you want to proceed?']")
	private WebElement navigationAlertMessage;

	@FindBy(xpath = "//p[text()='Partner ID']")
	private WebElement partnerIdLabel;

	@FindBy(xpath = "//label[text()='Partner Type']")
	private WebElement partnerTypeLabel;

	@FindBy(xpath = "//label[text()='SBI Version']")
	private WebElement sbiVersionLabel;

	@FindBy(xpath = "//label[text()='SBI Binary Hash']")
	private WebElement sbiBinaryHashLabel;

	@FindBy(xpath = "//p[text()='SBI Created Date']")
	private WebElement sbiCreatedDateLabel;

	@FindBy(xpath = "//p[text()='SBI Expiry Date']")
	private WebElement sbiExpiryDateLabel;

	DeviceProviderPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnAddSbiButton() {
		clickOnElement(addSbiButton);
	}

	public void clickOnAddSbiFromSbiListButton() {
		clickOnElement(addSbiFromSbiListButton);
	}

	public void clickOnAddSbiPartnerIdBox() {
		clickOnElement(addSbiPartnerId);
	}

	public void clickOnPartnerOption() {
		clickOnElement(sbiPartnerOption);
	}

	public String getPartnerId() {
		return getTextFromLocator(addSbiPartnerId);
	}

	public String getPartnerType() {
		return getTextFromLocator(partnerType);
	}

	public String getSbiVersion() {
		return getTextFromAttribute(sbiVersion, GlobalConstants.PLACEHOLDER);
	}

	public String getSbiBinaryHash() {
		return getTextFromAttribute(sbiBinaryHash, GlobalConstants.PLACEHOLDER);
	}

	public void enterSbiVersion(String sbiVesionValue) {
		enter(sbiVersion, sbiVesionValue);
	}

	public void enterSbiBinaryHash(String sbiBinaryHashValue) {
		enter(sbiBinaryHash, sbiBinaryHashValue);
	}

	public void clickOnDateInCalender() {
		clickOnElement(date24InCalender);
	}

	public void enterCurrentDateOnCreatedDate() {
		clickOnElement(createdDate);
		WebElement todayDateInCalender = driver.findElement(By.xpath(
				"//*[contains(@class, 'react-datepicker__day react-datepicker__day--0" + PmpTestUtil.todayDay + "')]"));
		clickOnElement(todayDateInCalender);
	}

	public void enterExpiryDate() {
		clickOnElement(expiryDate);
		clickOnElement(nextMonth);
		clickOnElement(date24InCalender);
	}

	public void clickOnCreatedDate() {
		clickOnElement(createdDate);
	}

	public void enterPastDateOnCreatedDate() {
		clickOnElement(createdDate);
		clickOnElement(previousMonth);
		clickOnElement(date4InCalender);
	}

	public void enterFutureDateOnCreatedDate() {
		clickOnElement(createdDate);
		clickOnElement(nextMonth);
		clickOnElement(date4InCalender);
	}

	public void enterFutureDateOnExpiryDate() {
		clickOnElement(expiryDate);
		clickOnElement(nextMonth);
		clickOnElement(date4InCalender);
	}

	public void enterPastDateOnExpiryDate() {
		clickOnElement(expiryDate);
		clickOnElement(previousMonth);
		clickOnElement(date24InCalender);
	}

	public String getCreatedDateValue() {
		return getTextFromAttribute(createdDate, GlobalConstants.VALUE);
	}

	public String getExpiredDateValue() {
		return getTextFromAttribute(expiryDate, GlobalConstants.VALUE);
	}

	public void clickOnExpiryDate() {
		clickOnElement(expiryDate);
	}

	public void clickOnClearForm() {
		clickOnElement(clearForm);
	}

	public void clickOnCancel() {
		clickOnElement(cancel);
	}

	public void clickOnSubmit() {
		clickOnElement(submit);
	}

	public boolean isSubmitButtonEnabled() {
		return isElementEnabled(submit);
	}

	public boolean isListOfSbiTitleDisplayed() {
		return isElementDisplayed(sbiList);
	}

	public boolean isCreatedSbiDisplayed(String sbiVersion) {
		WebElement createdSbi = driver.findElement(By.xpath("//*[text()='" + sbiVersion + "']"));
		return isElementDisplayed(createdSbi);
	}

	public boolean isPendingForApprovalDisplayed(String sbiVersion) {
		WebElement status = driver
				.findElement(By.xpath("//p[text()='" + sbiVersion + "']/..//*[text()='Pending For Approval']"));
		return isElementDisplayed(status);
	}

	public void clickOnHome() {
		clickOnElement(homeButton);
	}

	public boolean isAddSbiDetailsTitleDisplayed() {
		return isElementDisplayed(addSbiDetailsTitle);
	}

	public boolean isAddSbiDetailsSubTitleDisplayed() {
		return isElementDisplayed(addSbiDetailsSubTitle);
	}

	public boolean isHomeButtonDisplayed() {
		return isElementDisplayed(homeButton);
	}

	public boolean isListOfSbiTitleButtonDisplayed() {
		return isElementDisplayed(listOfSbiTitleButton);
	}

	public boolean isAddSbiDetailsInfoMessageDisplayed() {
		return isElementDisplayed(addSbiDetailsInfoMessage);
	}

	public void clickOnPartnerIdInfo() {
		clickOnElement(partnerIdInfo);
	}

	public String getPartnerIdInfoMessage() {
		return getTextFromLocator(partnerIdInfoMessage);
	}

	public boolean isPartnerTypeEnabled() {
		return isElementEnabled(partnerTypeBox);
	}

	public boolean isSameDateErrorMessageDisplayed() {
		return isElementDisplayed(sameDateErrorMessage);
	}

	public boolean isFutureDateErrorMessageDisplayed() {
		return isElementDisplayed(futureDateErrorMessage);
	}

	public boolean isSbiExistsErrorMessageDisplayed() {
		return isElementDisplayed(duplicateSbiErrorMessage);
	}

	public void clickOnExpiryInfo() {
		clickOnElement(expiryDateInfo);
	}

	public String getExpiryDateInfoMessage() {
		return getTextFromLocator(expiryDateInfoMessage) + " " + getTextFromLocator(expiryDateInfoMessageTwo);
	}

	public boolean isNavigationAlertMessageDisplayed() {
		return isElementDisplayed(navigationAlertMessage);
	}

	public void clickOnNavigationAlertCancel() {
		clickOnElement(navigationAlertCancel);
	}

	public boolean isPartnerIdLabelDisplayed() {
		return isElementDisplayed(partnerIdLabel);
	}

	public boolean isPartnerTypeLabelDisplayed() {
		return isElementDisplayed(partnerTypeLabel);
	}

	public boolean isSbiVersionLabelDisplayed() {
		return isElementDisplayed(sbiVersionLabel);
	}

	public boolean isSbiBinaryHashLabelDisplayed() {
		return isElementDisplayed(sbiBinaryHashLabel);
	}

	public boolean isCreatedDateLabelDisplayed() {
		return isElementDisplayed(sbiCreatedDateLabel);
	}

	public boolean isExpiryDateLabelDisplayed() {
		return isElementDisplayed(sbiExpiryDateLabel);
	}

}
