package io.mosip.testrig.pmpv2ui.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpv2ui.utility.GlobalConstants;

public class ListOfDevicesPage extends BasePage {

	@FindBy(id = "add_devices")
	private WebElement addDeviceButton;

	@FindBy(id = "device_list_add_device_btn")
	private WebElement addDeviceButtonFromDeviceList;

	@FindBy(id = "list_of_devices")
	private WebElement listOfDevicesTitle;

	@FindBy(id = "sub_title_btn")
	private WebElement listOfSbiButton;

	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;

	@FindBy(id = "device_list_approve_reject_option")
	private WebElement approveRejectButton;

	@FindBy(id = "approve_btn")
	private WebElement approveButton;

	@FindBy(id = "reject_btn")
	private WebElement rejectButton;

	@FindBy(id = "device_list_deactivate_device")
	private WebElement deactivateDevice;

	@FindBy(id = "device_list_view_details")
	private WebElement viewDevice;

	@FindBy(id = "devicesList.deviceId_header")
	private WebElement deviceIdHeader;

	@FindBy(id = "devicesList.deviceType_header")
	private WebElement deviceTypeHeader;

	@FindBy(id = "devicesList.deviceSubType_header")
	private WebElement deviceSubTypeHeader;

	@FindBy(id = "devicesList.make_header")
	private WebElement makeHeader;

	@FindBy(id = "devicesList.model_header")
	private WebElement modelHeader;

	@FindBy(id = "devicesList.creationDate_header")
	private WebElement creationDateHeader;

	@FindBy(id = "devicesList.status_header")
	private WebElement statusHeader;

	@FindBy(id = "devicesList.action_header")
	private WebElement actionHeader;

	@FindBy(id = "deviceId")
	private WebElement deviceIdHeaderBeforeAddingDevices;

	@FindBy(id = "deviceTypeCode")
	private WebElement deviceTypeHeaderBeforeAddingDevices;

	@FindBy(id = "deviceSubTypeCode")
	private WebElement deviceSubTypeHeaderBeforeAddingDevices;

	@FindBy(id = "make")
	private WebElement makeHeaderBeforeAddingDevices;

	@FindBy(id = "model")
	private WebElement modelHeaderBeforeAddingDevices;

	@FindBy(id = "createdDateTime")
	private WebElement creationDateHeaderBeforeAddingDevices;

	@FindBy(id = "status")
	private WebElement statusHeaderBeforeAddingDevices;

	@FindBy(id = "action")
	private WebElement actionHeaderBeforeAddingDevices;

	@FindBy(id = "filter_btn")
	private WebElement filterButton;

	@FindBy(id = "device_list_filter_status")
	private WebElement statusFilter;

	@FindBy(id = "device_list_filter_device_type")
	private WebElement deviceTypeFilter;

	@FindBy(id = "deactivate_submit_btn")
	private WebElement deactivateSubmit;

	@FindBy(id = "deactivate_cancel_btn")
	private WebElement deactivateCancel;

	@FindBy(id = "filter_reset_btn")
	private WebElement resetFilter;

	@FindBy(id = "pagination_select_record_per_page")
	private WebElement maxRecordsPerPage;

	@FindBy(id = "make_filter")
	private WebElement adminMakeFilter;

	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilter;

	@FindBy(xpath = "//div[contains(@class, 'bg-[#FEF1C6] text-[#6D1C00]') and text()='Pending For Approval']")
	private WebElement pendingForApproval;

	@FindBy(xpath = "//div[contains(@class, 'bg-[#D1FADF] text-[#155E3E]') and text()='Approved']")
	private WebElement approved;

	@FindBy(xpath = "//div[contains(@class, 'bg-[#FAD6D1] text-[#5E1515]') and text()='Rejected']")
	private WebElement rejected;

	@FindBy(xpath = "//div[contains(@class, 'bg-[#EAECF0] text-[#525252]') and text()='Deactivated']")
	private WebElement deactivated;

	@FindBy(xpath = "//div[@id='device_list_deactivate_device' and contains(@class, 'text-[#A5A5A5] cursor-auto hover:bg-gray-100')]")
	private WebElement deactivateDeviceWithGreyedOut;

	@FindBy(xpath = "//div[@id='device_list_deactivate_device' and contains(@class, 'text-[#3E3E3E] cursor-pointer hover:bg-gray-100')]")
	private WebElement deactivateDeviceWithoutGreyedOut;

	@FindBy(xpath = "//h1[text()='List of Devices']")
	private WebElement listOfDevicesHeading;

	@FindBy(xpath = "//button[text()='32']")
	private WebElement recordCount32;

	public ListOfDevicesPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnAddDeviceButton() {
		clickOnElement(addDeviceButton);
	}

	public void clickOnAddDeviceButtonFromDeviceList() {
		clickOnElement(addDeviceButtonFromDeviceList);
	}

	public void clickOnListOfSbiButton() {
		clickOnElement(listOfSbiButton);
	}

	public boolean isAddDeviceButtonEnabled() {
		return isElementEnabled(addDeviceButton);
	}
	
	public boolean isAddDeviceButtonDisabled() {
		return isElementDisabled(addDeviceButton);
	}

	public boolean isDeviceListAddDeviceButtonDisabled() {
		return isElementDisabled(addDeviceButtonFromDeviceList);
	}

	public String getListOFDevicesTitle() {
		return getTextFromLocator(listOfDevicesTitle);
	}

	public boolean isDeviceDisplayed(String deviceType, String deviceSubType, String make, String model) {
		try {
			WebElement addedDevice = driver.findElement(By.xpath("//td[text()='" + deviceType + "']/..//td[text()='"
					+ deviceSubType + "']/..//td[text()='" + make + "']/..//td[text()='" + model + "']"));
			return isElementDisplayed(addedDevice);
		} catch (NoSuchElementException | TimeoutException e) {
			return false;
		}
	}

	public void clickOnDeviceThreeDots(String deviceType, String deviceSubType, String make, String model) {
		WebElement addedDeviceThreeDots = driver.findElement(
				By.xpath("//td[text()='" + deviceType + "']/..//td[text()='" + deviceSubType + "']/..//td[text()='"
						+ make + "']/..//td[text()='" + model + "']/..//button[contains(@id, 'device_list_action')]"));
		clickOnElement(addedDeviceThreeDots);
	}

	public void clickOnDevice(String deviceType, String deviceSubType, String make, String model) {
		WebElement addedDevice = driver.findElement(By.xpath("//td[text()='" + deviceType + "']/..//td[text()='"
				+ deviceSubType + "']/..//td[text()='" + make + "']/..//td[text()='" + model + "']"));
		clickOnElement(addedDevice);
	}

	public boolean isDeviceStatusDisplayed(String deviceType, String deviceSubType, String make, String model,
			String status) {
		WebElement deviceStatus = driver.findElement(
				By.xpath("//td[text()='" + deviceType + "']/..//td[text()='" + deviceSubType + "']/..//td[text()='"
						+ make + "']/..//td[text()='" + model + "']/..//div[text()='" + status + "']"));
		return isElementDisplayed(deviceStatus);
	}

	public String getDeviceStatusClassValue(String deviceType, String deviceSubType, String make, String model,
			String status) {
		WebElement deviceStatus = driver.findElement(
				By.xpath("//td[text()='" + deviceType + "']/..//td[text()='" + deviceSubType + "']/..//td[text()='"
						+ make + "']/..//td[text()='" + model + "']/..//div[text()='" + status + "']"));
		return getTextFromAttribute(deviceStatus, GlobalConstants.CLASS);
	}

	public boolean isListOfDevicesHeadingDisplayed() {
		return isElementDisplayed(listOfDevicesHeading);
	}

	public void clickOnDeviceThreeDotsAsAdmin(String deviceType, String deviceSubType, String make, String model) {
		WebElement addedDeviceThreeDots = driver.findElement(
				By.xpath("//td[text()='" + deviceType + "']/..//td[text()='" + deviceSubType + "']/..//td[text()='"
						+ make + "']/..//td[text()='" + model + "']/..//button[contains(@id, 'device_list_action')]"));
		clickOnElement(addedDeviceThreeDots);
	}

	public boolean isSubTitleDisplayed(String sbiVersion) {
		WebElement subTitle = driver.findElement(By.xpath("//h1[text()='" + sbiVersion + " | ']"));
		return isElementDisplayed(subTitle);
	}

	public boolean isFilterHeaderDisplayed(String header) {
		WebElement headerElement = driver.findElement(By.xpath("//p[text()='" + header + "']"));
		return isElementDisplayed(headerElement);
	}

	public void clickOnApproveOrReject() {
		clickOnElement(approveRejectButton);
	}

	public void clickOnApprove() {
		clickOnElement(approveButton);
	}

	public void clickOnReject() {
		clickOnElement(rejectButton);
	}

	public boolean isDeactivateDeviceEnabled() {
		if (isElementDisplayed(deactivateDeviceWithoutGreyedOut)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDeactivateDeviceDisabled() {
		if (isElementDisplayed(deactivateDeviceWithGreyedOut)) {
			return true;
		} else {
			return false;
		}
	}

	public void clickOnDeactivateDevice() {
		clickOnElement(deactivateDevice);
	}

	public boolean isViewDeviceEnabled() {
		return isElementEnabled(viewDevice);
	}

	public void clickOnViewDevice() {
		clickOnElement(viewDevice);
	}

	public boolean isDeviceIdHeaderDisplayed() {
		return isElementDisplayed(deviceIdHeader);
	}

	public boolean isDeviceTypeHeaderDisplayed() {
		return isElementDisplayed(deviceTypeHeader);
	}

	public boolean isDeviceSubTypeHeaderDisplayed() {
		return isElementDisplayed(deviceSubTypeHeader);
	}

	public boolean isMakeHeaderDisplayed() {
		return isElementDisplayed(makeHeader);
	}

	public boolean isModelHeaderDisplayed() {
		return isElementDisplayed(modelHeader);
	}

	public boolean isCreationDateHeaderDisplayed() {
		return isElementDisplayed(creationDateHeader);
	}

	public boolean isStatusHeaderDisplayed() {
		return isElementDisplayed(statusHeader);
	}

	public boolean isActionHeaderDisplayed() {
		return isElementDisplayed(actionHeader);
	}

	public boolean isDeviceIdHeaderBeforeAddingDevicesDisplayed() {
		return isElementDisplayed(deviceIdHeaderBeforeAddingDevices);
	}

	public boolean isDeviceTypeHeaderBeforeAddingDevicesDisplayed() {
		return isElementDisplayed(deviceTypeHeaderBeforeAddingDevices);
	}

	public boolean isDeviceSubTypeHeaderBeforeAddingDevicesDisplayed() {
		return isElementDisplayed(deviceSubTypeHeaderBeforeAddingDevices);
	}

	public boolean isMakeHeaderBeforeAddingDevicesDisplayed() {
		return isElementDisplayed(makeHeaderBeforeAddingDevices);
	}

	public boolean isModelHeaderBeforeAddingDevicesDisplayed() {
		return isElementDisplayed(modelHeaderBeforeAddingDevices);
	}

	public boolean isCreationDateHeaderBeforeAddingDevicesDisplayed() {
		return isElementDisplayed(creationDateHeaderBeforeAddingDevices);
	}

	public boolean isStatusHeaderBeforeAddingDevicesDisplayed() {
		return isElementDisplayed(statusHeaderBeforeAddingDevices);
	}

	public boolean isActionHeaderBeforeAddingDevicesDisplayed() {
		return isElementDisplayed(actionHeaderBeforeAddingDevices);
	}

	public void clickOnFilterButton() {
		clickOnElement(filterButton);
	}

	public void selectStatusFilter(String status) {
		clickOnElement(statusFilter);
		WebElement statusOption = driver.findElement(
				By.xpath("//button[contains(@id, 'device_list_filter_status_option') and text()='" + status + "']"));
		clickOnElement(statusOption);
	}

	public void selectDeviceTypeFilter(String deviceType) {
		try {
			dropdown(deviceTypeFilter, deviceType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clickOnDeactivateSubmit() {
		clickOnElement(deactivateSubmit);
	}

	public void clickOnDeactivateCancel() {
		clickOnElement(deactivateCancel);
	}

	public void clickOnResetFilter() {
		clickOnElement(resetFilter);
	}

	public void clickOnHome() {
		clickOnElement(homeButton);
	}

	public boolean isPendingForApprovalStatusDisplayed() {
		return isElementDisplayed(pendingForApproval);
	}

	public boolean isApprovedStatusDisplayed() {
		return isElementDisplayed(approved);
	}

	public boolean isRejectedStatusDisplayed() {
		return isElementDisplayed(rejected);
	}

	public boolean isDeactivatedStatusDisplayed() {
		return isElementDisplayed(deactivated);
	}

	public void selectMaxRecordsPerPage() {
		clickOnElement(maxRecordsPerPage);
		clickOnElement(recordCount32);
	}

	public void applyMakeFilter(String make) {
		enter(adminMakeFilter, make);
		clickOnElement(applyFilter);
	}

	public String getBreadcrumbText() {
		return getTextFromLocator(homeButton) + getTextFromLocator(listOfSbiButton);
	}
}
