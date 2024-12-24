package io.mosip.testrig.pmprevampui.pages;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class AddDevicePage extends BasePage {
	
	private static final Logger logger = Logger.getLogger(AddDevicePage.class);
	
	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;
	
	@FindBy(id = "sub_title_btn")
	private WebElement subTitleButton;
	
	@FindBy(id = "add_device_submit_btn")
	private WebElement submitButton;
	
	@FindBy(id = "add_device_clear_btn")
	private WebElement clearButton;
	
	@FindBy(id = "add_device_delete_btn")
	private WebElement deleteButton;
	
	@FindBy(id = "add_device_btn")
	private WebElement addDeviceButton;
	
	@FindBy(id = "success_msg_close")
	private WebElement successMessageCloseButton;
	
	@FindBy(id = "add_device_back_view_devices_btn")
	private WebElement backToViewDevicesButton;
	
	@FindBy(id = "footer_documentation_link")
	private WebElement footerDocumentationButton;
	
	@FindBy(id = "footer_contact_us_link")
	private WebElement footerContactButton;
	
	@FindBy(id = "header_user_profile_title")
	private WebElement headerUserProfile;
	
	@FindBy(id = "add_device_device_type")
	private WebElement addDeviceTypeSelectDropdown;
	
	@FindBy(id = "add_device_device_sub_type")
	private WebElement addDeviceSubTypeSelectDropdown;
	
	@FindBy(id = "add_device_device_type_option1")
	private WebElement addDeviceTypeOption;
	
	@FindBy(id = "add_device_device_sub_type_option1")
	private WebElement addDeviceSubTypeOption;
	
	@FindBy(id = "add_device_make_input")
	private WebElement addDeviceMakeTextbox;
	
	@FindBy(id = "add_device_model_input")
	private WebElement addDeviceModelTextbox;
	
	@FindBy(xpath = "//*[text()='Add Devices']")
	private WebElement addDevicesTitle;
	
	@FindBy(xpath = "//span[text()='*']/../..//p[text()='Device Type']")
	private WebElement deviceTypeLabel;

	@FindBy(xpath = "//span[text()='*']/../..//p[text()='Device Sub Type']")
	private WebElement deviceSubTypeLabel;
	
	@FindBy(xpath = "//span[text()='*']/../..//label[text()='Make']")
	private WebElement makeLabel;
	
	@FindBy(xpath = "//span[text()='*']/../..//label[text()='Model']")
	private WebElement modelLabel;
	
	@FindBy(xpath = "//div[text()='Approved' and contains(@class, '#D1FADF')]")
	private WebElement approvedStatus;
	
	@FindBy(xpath = "//div[text()='Pending For Approval' and contains(@class, '#FEF1C6')]")
	private WebElement pendingForApprovalStatus;
	
	@FindBy(xpath = "//p[contains(text(), 'On clicking of')]")
	private WebElement instructionMessage;
	
	@FindBy(xpath = "//span[text()='*']/../..//p[text()='All fields marked with' and text()='are mandatory.']")
	private WebElement mandatoryMessage;
	
	@FindBy(xpath = "//p[text()='2024 © MOSIP - ' and text()='All rights reserved.']")
	private WebElement copyrightsMessage;
	
	@FindBy(xpath = "//h2[text()='AABBCC']")
	private WebElement organizationName;
	
	@FindBy(xpath = "//p[text()='Device has been successfully submitted and is pending with admin for approval.']")
	private WebElement successMessage;
	
	
	public AddDevicePage(WebDriver driver) {
		super(driver);
	}
	
	public boolean isHomeButtonDisplayed() {
		return isElementDisplayed(homeButton);
	}
	
	public String getSubTitle() {
		return getTextFromLocator(subTitleButton);
	}
	
	public void clickOnListOfSbiButton() {
		clickOnElement(subTitleButton);
	}

	public boolean isAddDeviceTitleDisplayed() {
		return isElementDisplayed(addDevicesTitle);
	}
	
	public boolean isSbiVersionDisplayed(String sbiVersion) {
		WebElement createdSbi = driver.findElement(By.xpath("//h1[text()='"+sbiVersion+" | ']"));
		return isElementDisplayed(createdSbi);
	}
	
	public boolean isApprovedStatusDisplayed() {
		return isElementDisplayed(approvedStatus);
	}
	
	public boolean isPendingForApprovalStatusDisplayed() {
		return isElementDisplayed(pendingForApprovalStatus);
	}
	
	public boolean isMandatoryMessageDisplayed() {
		return isElementDisplayed(mandatoryMessage);
	}
	
	public String getAddDeviceMessage() {
		return getTextFromLocator(instructionMessage);
	}
	
	public boolean isDeviceTypeDisplayed() {
		return isElementDisplayed(deviceTypeLabel);
	}
	
	public boolean isDeviceSubTypeDisplayed() {
		return isElementDisplayed(deviceSubTypeLabel);
	}
	
	public boolean isMakeLabelDisplayed() {
		return isElementDisplayed(makeLabel);
	}
	
	public boolean isModelLabelDisplayed() {
		return isElementDisplayed(modelLabel);
	}
	
	public boolean isCopyrightsMessageDisplayed() {
		return isElementDisplayed(copyrightsMessage);
	}
	
	public boolean isFooterDocumentLinkDisplayed() {
		return isElementDisplayed(footerDocumentationButton);
	}
	
	public boolean isFooterContactLinkDisplayed() {
		return isElementDisplayed(footerContactButton);
	}
	
	public boolean isOrganizationNameDisplayed() {
		return isElementDisplayed(organizationName);
	}
	
	public boolean isHeaderUserProfileDisplayed() {
		return isElementDisplayed(headerUserProfile);
	}
	
	public void selectAddDeviceType(String value){
		try {
			dropdown(addDeviceTypeSelectDropdown, value);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}
	
	public void selectDeviceSubType(String value) {
		try {
			dropdown(addDeviceSubTypeSelectDropdown, value);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}
	
	public void enterMakeName(String makeName) {
		enter(addDeviceMakeTextbox, makeName);
	}
	
	public void enterModelName(String modelName) {
		enter(addDeviceModelTextbox, modelName);
	}
	
	public boolean isSubmitEnabled() {
		return isElementEnabled(submitButton);
	}
	
	public boolean isDeviceSubTypeEnabled() {
		return isElementEnabled(addDeviceSubTypeSelectDropdown);
	}
	
	public boolean isAddDeviceEnabled() {
		return isElementEnabled(addDeviceButton);
	}
	
	public boolean isDeleteButtonEnabled() {
		return isElementEnabled(deleteButton);
	}
	
	public void clickOnDeleteButton() {
		clickOnElement(deleteButton);
	}
	
	public void clickOnAddDevice() {
		clickOnElement(addDeviceButton);
	}
	
	public void clickOnSubmit() {
		clickOnElement(submitButton);
	}
	
	public void clickOnClear() {
		clickOnElement(clearButton);
	}
	
	public void clickOnBackToDevices() {
		clickOnElement(backToViewDevicesButton);
	}

	public boolean isSuccessMessageDisplayed() {
		return isElementDisplayed(successMessage);
	}
	
	public void closeSuccessMessage() {
		clickOnElement(successMessageCloseButton);
	}
	
	public String getDeviceTypeValue() {
		return getTextFromLocator(addDeviceTypeSelectDropdown);
	}
	
	public String getDeviceSubTypeValue() {
		return getTextFromLocator(addDeviceSubTypeSelectDropdown);
	}
	
	public void clickOnDeviceType() {
		clickOnElement(addDeviceTypeSelectDropdown);
	}
	
	public boolean isDeviceTypeOptionDisplayed() {
		return isElementDisplayed(addDeviceTypeOption);
	}
	
	public void clickOnDeviceSubType() {
		clickOnElement(addDeviceSubTypeSelectDropdown);
	}
	
	public boolean isDeviceSubTypeOptionDisplayed() {
		return isElementDisplayed(addDeviceSubTypeOption);
	}
	
	public boolean isAddedDeviceTypeEnabled(String deviceType) {
		WebElement deviceTypeElement = driver.findElement(By.xpath("//span[text()='"+deviceType+"']"));
		return isElementEnabled(deviceTypeElement);
	}
	
	public String getMakePlaceholder() {
		return getTextFromAttribute(addDeviceMakeTextbox, GlobalConstants.PLACEHOLDER);
	}
	
	public String getModelPlaceholder() {
		return getTextFromAttribute(addDeviceModelTextbox, GlobalConstants.PLACEHOLDER);
	}
	
	
}
