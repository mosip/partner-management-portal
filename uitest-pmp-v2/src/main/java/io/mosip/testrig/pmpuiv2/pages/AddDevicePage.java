package io.mosip.testrig.pmpuiv2.pages;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
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

import io.mosip.testrig.pmpuiv2.driver.DriverManager;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

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

	@FindBy(id = "success_msg_close_icon")
	private WebElement successMessageCloseButton;

	@FindBy(id = "add_device_back_view_devices_btn")
	private WebElement backToViewDevicesButton;

	@FindBy(id = "block_messsage_proceed")
	private WebElement blockProceedButton;

	@FindBy(id = "footer_documentation_link")
	private WebElement footerDocumentationButton;

	@FindBy(id = "footer_contact_us_link")
	private WebElement footerContactButton;

	@FindBy(id = "header_user_profile_title")
	private WebElement headerUserProfile;

	@FindBy(id = "add_device_device_type_dropdown_btn")
	private WebElement addDeviceTypeSelectDropdown;

	@FindBy(id = "add_device_device_sub_type_dropdown_btn")
	private WebElement addDeviceSubTypeSelectDropdown;

	@FindBy(id = "add_device_device_type_option1")
	private WebElement addDeviceTypeOption;

	@FindBy(id = "add_device_device_sub_type_option1")
	private WebElement addDeviceSubTypeOption;

	@FindBy(id = "add_device_make_input")
	private WebElement addDeviceMakeTextbox;

	@FindBy(id = "add_device_model_input")
	private WebElement addDeviceModelTextbox;

	@FindBy(id = "add_device_back_sbi_list_btn")
	private WebElement addDeviceBackToSbiList;

	@FindBy(id = "page_title")
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

	@FindBy(xpath = "//p[contains(text(), 'Maximum of 25 devices')]")
	private WebElement maximumDeviceAlert;

	@FindBy(xpath = "//button[text()='Cancel']")
	private WebElement cancelButton;

	@FindBy(xpath = "//button[text()='Confirm']")
	private WebElement confirmButton;

	@FindBy(xpath = "//input[@value='Automation25']")
	private WebElement twentyFifthDevice;

	@FindBy(xpath = "//p[contains(text(), 'Device Details already exists')]")
	private WebElement duplicateDeviceErrorMessage;

	public AddDevicePage(WebDriver driver) {
		super(driver);
	}

	private By homeButtonBy = By.id("sub_title_home_btn");

	public boolean isHomeButtonDisplayed() {
		return isDisplayed(homeButtonBy);
	}

	public String getSubTitle() {
		return getTextFromLocator(subTitleButton);
	}

	public void clickOnListOfSbiButton() {
		clickOnElement(subTitleButton);
	}

	public void clickOnBackToListOfSbiButton() {
		clickOnElement(addDeviceBackToSbiList);
	}

	private By pageTitle = By.id("page_title");

	public boolean isAddDeviceTitleDisplayed() {
		return isTextPresent(pageTitle, "Add Devices");
	}

	public boolean isSbiVersionDisplayed(String sbiVersion) {
		WebElement createdSbi = driver.findElement(By.xpath("//h1[text()='" + sbiVersion + " | ']"));
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

	public void selectAddDeviceType(String value) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			// 🔑 re-locate element fresh every time
			WebElement deviceTypeDropdown = wait
					.until(ExpectedConditions.elementToBeClickable(By.id("add_device_device_type_dropdown_btn")));

			dropdown(deviceTypeDropdown, value);

		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	public void selectDeviceSubType(String value) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			// 🔑 re-locate Sub Type dropdown fresh
			WebElement subTypeDropdown = wait
					.until(ExpectedConditions.elementToBeClickable(By.id("add_device_device_sub_type_dropdown_btn")));

			dropdown(subTypeDropdown, value);

		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	public void enterMakeName(String makeName) {
		enter(addDeviceMakeTextbox, makeName);
	}

	public void enterModelName(String modelName) {
		enter(addDeviceModelTextbox, modelName);
	}

	public void selectAddDeviceTypeWithPosition(String value, int position) throws InterruptedException {
		position = position - 1;
		Thread.sleep(1000);
		WebElement addDeviceTypeSelectDropdown = driver
				.findElements(By.xpath("//button[@id='add_device_device_type_dropdown_btn']")).get(position);
		try {
			dropdownWithPosition(addDeviceTypeSelectDropdown, value, position + 1);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}

	public void selectDeviceSubTypeWithPosition(String value, int position) {
		WebElement addDeviceSubTypeSelectDropdown = driver
				.findElement(By.xpath("(//button[@id='add_device_device_sub_type_dropdown_btn'])[" + position + "]"));
		try {
			dropdownWithPosition(addDeviceSubTypeSelectDropdown, value, position);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}

	public void enterMakeNameWithPosition(String makeName, int position) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		By makeInputLocator = By.xpath("(//form)[" + position + "]//input[@placeholder='Enter Make']");
		WebElement makeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(makeInputLocator));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", makeInput);
		enter(makeInput, makeName);
	}

	public void enterModelNameWithPosition(String modelName, int position) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		By modelInputLocator = By.xpath("(//form)[" + position + "]//input[@placeholder='Enter Model']");
		WebElement modelInput = wait.until(ExpectedConditions.visibilityOfElementLocated(modelInputLocator));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", modelInput);
		enter(modelInput, modelName);
	}

	public boolean isSubmitEnabled() {
		return isElementEnabled(submitButton);
	}

	public boolean isSubmitDisabled() {
		return isElementDisabled(submitButton);
	}

	public boolean isDeviceSubTypeEnabled() {
		return isElementEnabled(addDeviceSubTypeSelectDropdown);
	}

	public boolean isDeviceSubTypeDisabled() {
		return isElementDisabled(addDeviceSubTypeSelectDropdown);
	}

	public boolean isAddDeviceEnabled() {
		return isElementEnabled(addDeviceButton);
	}

	public boolean isAddDeviceDisabled() {
		return isElementDisabled(addDeviceButton);
	}

	public boolean isDeleteButtonEnabled() {
		return isElementEnabled(deleteButton);
	}

	public boolean isDeleteButtonDisabled() {
		return isElementDisabled(deleteButton);
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

	public void clickOnProceed() {
		clickOnElement(blockProceedButton);
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

	public boolean isDeviceTypeDisabled() {
		return isElementDisabled(addDeviceTypeSelectDropdown);
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
		WebElement deviceTypeElement = driver.findElement(By.xpath("//span[text()='" + deviceType + "']"));
		return isElementEnabled(deviceTypeElement);
	}

	public String getMakePlaceholder() {
		return getTextFromAttribute(addDeviceMakeTextbox, GlobalConstants.PLACEHOLDER);
	}

	public String getModelPlaceholder() {
		return getTextFromAttribute(addDeviceModelTextbox, GlobalConstants.PLACEHOLDER);
	}

	public String getMaximumDeviceAlert() {
		return getTextFromLocator(maximumDeviceAlert);
	}

	public void clickOnCancel() {
		clickOnElement(cancelButton);
	}

	public void clickOnConfirm() {
		clickOnElement(confirmButton);
	}

	public boolean isTwentyFifthDeviceIsDisplayed() {
		return isElementDisplayed(twentyFifthDevice);
	}

	public String getDuplicateDeviceErrorMessage() {
		return getTextFromLocator(duplicateDeviceErrorMessage);
	}

	public void selectAddDeviceTypeForReject() {
		clickOnElement(addDeviceTypeSelectDropdown);
		clickOnElement(addDeviceTypeOption);
	}

}
