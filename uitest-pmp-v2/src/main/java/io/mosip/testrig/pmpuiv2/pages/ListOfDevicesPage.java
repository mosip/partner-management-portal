package io.mosip.testrig.pmpuiv2.pages;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;

public class ListOfDevicesPage extends BasePage {

	@FindBy(id = "add_devices")
	private WebElement addDeviceButton;

	@FindBy(id = "device_list_add_device_btn")
	private WebElement addDeviceButtonFromDeviceList;

	@FindBy(id = "list_of_device_details_title")
	private WebElement listOfDevicesTitle;

	@FindBy(id = "list_of_devices_title")
	private WebElement isListOfDevicesTitleInPartner;

	@FindBy(xpath = "//div[@id='list_of_devices']//p")
	private WebElement listOfDevicesText;

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

	@FindBy(id = "device_list_filter_status_dropdown_btn")
	private WebElement statusFilter;

	@FindBy(id = "device_list_filter_device_type_dropdown_btn")
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

	@FindBy(id = "deviceId_asc_icon")
	private WebElement deviceIdAscIcon;

	@FindBy(id = "deviceId_desc_icon")
	private WebElement deviceIdDescIcon;

	@FindBy(id = "deviceTypeCode_asc_icon")
	private WebElement deviceTypeCodeAscIcon;

	@FindBy(id = "deviceTypeCode_desc_icon")
	private WebElement deviceTypeCodeDescIcon;

	@FindBy(id = "deviceSubTypeCode_asc_icon")
	private WebElement deviceSubTypeCodeAscIcon;

	@FindBy(id = "deviceSubTypeCode_desc_icon")
	private WebElement deviceSubTypeCodeDescIcon;

	@FindBy(id = "make_asc_icon")
	private WebElement makeAscIcon;

	@FindBy(id = "make_desc_icon")
	private WebElement makeDescIcon;

	@FindBy(id = "model_asc_icon")
	private WebElement modelAscIcon;

	@FindBy(id = "model_desc_icon")
	private WebElement modelDescIcon;

	@FindBy(id = "createdDateTime_asc_icon")
	private WebElement createdDateTimeAscIcon;

	@FindBy(id = "createdDateTime_desc_icon")
	private WebElement createdDateTimeDescIcon;

	@FindBy(id = "status_asc_icon")
	private WebElement statusAscIcon;

	@FindBy(id = "status_desc_icon")
	private WebElement statusDescIcon;

	@FindBy(id = "device_list_view_option")
	private WebElement deviceListViewOption;

	@FindBy(id = "device_list_deactivate_option")
	private WebElement deviceListDeactivateOption;

	@FindBy(xpath = "//p[@id='device_list_approve_reject_option' and contains(@class, 'text-[#A5A5A5]')]")
	private WebElement approveRejectWithGreyedOut;

	@FindBy(xpath = "//div[contains(@class, 'flex-col') and .//p[text()='AutomationDeactivating1 | AutomationDeactivating1']]")
	private WebElement approvePopupTitle;

	@FindBy(xpath = "//p[text()='Do you want to Approve or Reject the Device?']")
	private WebElement approvePopupSubTitle;

	@FindBy(xpath = "//p[text()='Please review the Device details carefully before taking appropriate action.']")
	private WebElement approvePopupDescription;

	@FindBy(id = "approve_reject_popup_close_icon")
	private WebElement approvePopupCloseIcon;

	@FindBy(id = "device_list_filter_make_dropdown_btn")
	private WebElement deviceMakeFilter;

	@FindBy(id = "device_list_filter_model_dropdown_btn")
	private WebElement deviceModelFilter;

	@FindBy(id = "device_list_filter_make_search_input")
	private WebElement deviceMakeFilterSearchBar;

	@FindBy(id = "device_list_filter_model_search_input")
	private WebElement deviceModelFilterSearchBar;

	@FindBy(xpath = "//p[text()='No Data Available.']")
	private WebElement noDataAvailableMessage;

	@FindBy(xpath = "//span[text()='Select Device ID']")
	private WebElement deviceIdPlaceHolder;

	@FindBy(xpath = "//span[text()='Select Device Type']")
	private WebElement deviceTypePlaceHolder;

	@FindBy(xpath = "//span[text()='Select Device Sub Type']")
	private WebElement deviceSubTypePlaceHolder;

	@FindBy(xpath = "//span[text()='Select Make Name']")
	private WebElement makePlaceHolder;

	@FindBy(xpath = "//span[text()='Select Model Name']")
	private WebElement modelPlaceHolder;

	@FindBy(xpath = "//span[text()='Select Status']")
	private WebElement statusPlaceHolder;

	@FindBy(xpath = "//p[starts-with(normalize-space(), 'Do you want to deactivate Device')]")
	private WebElement deactivateDeviceText;

	@FindBy(xpath = "//p[normalize-space()='On clicking Confirm, your device details will be deactivated.']")
	private WebElement deactivateDeviceSubtitle;

	@FindBy(id = "sbi_version_filter")
	private WebElement sbiVersionFilter;

	@FindBy(xpath = "//p[text()='No Results Found']")
	private WebElement noResultsFound;

	@FindBy(xpath = "//button[text()='x']")
	private WebElement cancelButtonInFilter;

	@FindBy(id = "device_list_view_option")
	private WebElement viewDeviceInAdmin;

	@FindBy(id = "status_filter_dropdown_btn")
	private WebElement deviceStatusFilterInAdmin;

	@FindBy(id = "partner_id_filter")
	private WebElement partnerIdFilterInAdmin;

	@FindBy(id = "deactivate_popup_header")
	private WebElement deactivateDevicePopupInAdmin;

	@FindBy(id = "deactivate_popup_description")
	private WebElement deactivateDevicePopupDescInAdmin;

	@FindBy(xpath = "//p[@id='device_list_deactivate_option' and contains(@class, 'text-[#A5A5A5]')]")
	private WebElement deactivateDeviceWithGreyedOutInAdmin;

	@FindBy(xpath = "//p[@id='device_list_deactivate_option' and contains(@class, 'text-[#3E3E3E]')]")
	private WebElement deactivateDeviceEnabledButtonInAdmin;

	@FindBy(id = "list_of_device_details_title")
	private WebElement listOfDeviceInAdmin;

	@FindBy(xpath = "//input[@placeholder='Search Make']")
	private WebElement makeFilterPlaceHolderInAdmin;

	@FindBy(xpath = "//input[@placeholder='Search Partner ID']")
	private WebElement partnerIdFilterPlaceHolderInAdmin;

	@FindBy(xpath = "//input[@placeholder='Search Organisation']")
	private WebElement organisationFilterPlaceHolderInAdmin;

	@FindBy(xpath = "//input[@placeholder='Search SBI ID']")
	private WebElement sbiIdFilterPlaceHolderInAdmin;

	@FindBy(xpath = "//input[@placeholder='Search SBI Version']")
	private WebElement sbiVersionFilterPlaceHolderInAdmin;

	@FindBy(xpath = "//input[@placeholder='Search Device ID']")
	private WebElement deviceIdFilterPlaceHolderInAdmin;

	@FindBy(xpath = "//input[@placeholder='Search Model']")
	private WebElement modelFilterPlaceHolderInAdmin;

	@FindBy(id = "model_filter")
	private WebElement adminModelFilter;

	@FindBy(id = "no_results_found")
	private WebElement noResultsFoundInAdmin;

	@FindBy(xpath = "//tr[@id='device_list_item1']/td[1]")
	private WebElement partnerIdInFirstColumn;

	@FindBy(xpath = "//tr[@id='device_list_device_item1']/td[1]")
	private WebElement partnerIdInFirstColumnInPartner;

	@FindBy(id = "device_type_filter_dropdown_btn")
	private WebElement deviceTypeFilterInAdmin;

	@FindBy(id = "partnerId_asc_icon")
	private WebElement partnerIdAscIconInAdmin;

	@FindBy(id = "partnerId_desc_icon")
	private WebElement partnerIdDescIconInAdmin;

	@FindBy(id = "orgName_asc_icon")
	private WebElement orgNameAscIconInAdmin;

	@FindBy(id = "orgName_desc_icon")
	private WebElement orgNameDescIconInAdmin;

	@FindBy(id = "sbiId_asc_icon")
	private WebElement sbiIdAscIconInAdmin;

	@FindBy(id = "sbiId_desc_icon")
	private WebElement sbiIdDescIconInAdmin;

	@FindBy(id = "sbiVersion_asc_icon")
	private WebElement sbiVersionAscIconInAdmin;

	@FindBy(id = "sbiVersion_desc_icon")
	private WebElement sbiVersionDescIconInAdmin;

	@FindBy(id = "deviceId_asc_icon")
	private WebElement deviceIdAscIconInAdmin;

	@FindBy(id = "deviceId_desc_icon")
	private WebElement deviceIdDescIconInAdmin;

	@FindBy(id = "deviceType_asc_icon")
	private WebElement deviceTypeAscIconInAdmin;

	@FindBy(id = "deviceType_desc_icon")
	private WebElement deviceTypeDescIconInAdmin;

	@FindBy(id = "deviceSubType_asc_icon")
	private WebElement deviceSubTypeAscIconInAdmin;

	@FindBy(id = "deviceSubType_desc_icon")
	private WebElement deviceSubTypeDescIconInAdmin;

	@FindBy(id = "make_asc_icon")
	private WebElement makeAscIconInAdmin;

	@FindBy(id = "make_desc_icon")
	private WebElement makeDescIconInAdmin;

	@FindBy(id = "model_asc_icon")
	private WebElement modelAscIconInAdmin;

	@FindBy(id = "model_desc_icon")
	private WebElement modelDescIconInAdmin;

	@FindBy(id = "createdDateTime_asc_icon")
	private WebElement createdDateTimeAscIconInAdmin;

	@FindBy(id = "createdDateTime_desc_icon")
	private WebElement createdDateTimeDescIconInAdmin;

	@FindBy(id = "status_asc_icon")
	private WebElement statusAscIconInAdmin;

	@FindBy(id = "status_desc_icon")
	private WebElement statusDescIconInAdmin;

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

	public boolean isListOfDevicesTitleDisplayed() {
		return isElementDisplayed(listOfDevicesTitle);
	}

	public String getListOfDevicesTitle() {
		return getTextFromLocator(listOfDevicesText);
	}

	public boolean isDeviceDisplayed(String deviceType, String deviceSubType, String make, String model) {
		try {
			By addedDevice = By.xpath("//td[text()='" + deviceType + "']/..//td[text()='" + deviceSubType
					+ "']/..//td[text()='" + make + "']/..//td[text()='" + model + "']");
			return isDisplayed(addedDevice);
		} catch (NoSuchElementException | TimeoutException e) {
			return false;
		}
	}

	public void clickOnDeviceThreeDots(String deviceType, String deviceSubType, String make, String model) {
		By addedDeviceThreeDots = By
				.xpath("//td[text()='" + deviceType + "']/..//td[text()='" + deviceSubType + "']/..//td[text()='" + make
						+ "']/..//td[text()='" + model + "']/..//button[contains(@id, 'device_list_action')]");
		click(addedDeviceThreeDots);
	}

	public void clickOnDevice(String deviceType, String deviceSubType, String make, String model, String status) {
		By addedDevice = By.xpath("//td[text()='" + deviceType + "']/..//td[text()='" + deviceSubType
				+ "']/..//td[text()='" + make + "']/..//td[text()='" + model + "']");
		click(addedDevice);
	}

	public boolean isDeviceStatusDisplayed(String deviceType, String deviceSubType, String make, String model,
			String status) {
		By deviceStatus = By.xpath("//td[text()='" + deviceType + "']/..//td[text()='" + deviceSubType
				+ "']/..//td[text()='" + make + "']/..//td[text()='" + model + "']/..//div[text()='" + status + "']");
		return isDisplayed(deviceStatus);
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
		By addedDeviceThreeDots = By
				.xpath("//td[text()='" + deviceType + "']/..//td[text()='" + deviceSubType + "']/..//td[text()='" + make
						+ "']/..//td[text()='" + model + "']/..//button[contains(@id, 'device_list_action')]");
		click(addedDeviceThreeDots);
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
			e.printStackTrace();
		}
	}

	public void selectDeviceTypeFilterInAdmin(String deviceType) {
		try {
			dropdown(deviceTypeFilterInAdmin, deviceType);
		} catch (IOException e) {
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

	public boolean isDeviceIdAscIconDisplayed() {
		return isElementDisplayed(deviceIdAscIcon);
	}

	public boolean isDeviceIdDescIconDisplayed() {
		return isElementDisplayed(deviceIdDescIcon);
	}

	public boolean isDeviceTypeCodeAscIconDisplayed() {
		return isElementDisplayed(deviceTypeCodeAscIcon);
	}

	public boolean isDeviceTypeCodeDescIconDisplayed() {
		return isElementDisplayed(deviceTypeCodeDescIcon);
	}

	public boolean isDeviceSubTypeCodeAscIconDisplayed() {
		return isElementDisplayed(deviceSubTypeCodeAscIcon);
	}

	public boolean isDeviceSubTypeCodeDescIconDisplayed() {
		return isElementDisplayed(deviceSubTypeCodeDescIcon);
	}

	public boolean isMakeAscIconDisplayed() {
		return isElementDisplayed(makeAscIcon);
	}

	public boolean isMakeDescIconDisplayed() {
		return isElementDisplayed(makeDescIcon);
	}

	public boolean isModelAscIconDisplayed() {
		return isElementDisplayed(modelAscIcon);
	}

	public boolean isModelDescIconDisplayed() {
		return isElementDisplayed(modelDescIcon);
	}

	public boolean isCreatedDateTimeAscIconDisplayed() {
		return isElementDisplayed(createdDateTimeAscIcon);
	}

	public boolean isCreatedDateTimeDescIconDisplayed() {
		return isElementDisplayed(createdDateTimeDescIcon);
	}

	public boolean isStatusAscIconDisplayed() {
		return isElementDisplayed(statusAscIcon);
	}

	public boolean isStatusDescIconDisplayed() {
		return isElementDisplayed(statusDescIcon);
	}

	public void clickOnDeviceIdAscIcon() {
		clickOnElement(deviceIdAscIcon);
	}

	public void clickOnDeviceIdDescIcon() {
		clickOnElement(deviceIdDescIcon);
	}

	public void clickOnDeviceTypeCodeAscIcon() {
		clickOnElement(deviceTypeCodeAscIcon);
	}

	public void clickOnDeviceTypeCodeDescIcon() {
		clickOnElement(deviceTypeCodeDescIcon);
	}

	public void clickOnDeviceSubTypeCodeAscIcon() {
		clickOnElement(deviceSubTypeCodeAscIcon);
	}

	public void clickOnDeviceSubTypeCodeDescIcon() {
		clickOnElement(deviceSubTypeCodeDescIcon);
	}

	public void clickOnMakeAscIcon() {
		clickOnElement(makeAscIcon);
	}

	public void clickOnMakeDescIcon() {
		clickOnElement(makeDescIcon);
	}

	public void clickOnModelAscIcon() {
		clickOnElement(modelAscIcon);
	}

	public void clickOnModelDescIcon() {
		clickOnElement(modelDescIcon);
	}

	public void clickOnCreatedDateTimeAscIcon() {
		clickOnElement(createdDateTimeAscIcon);
	}

	public void clickOnCreatedDateTimeDescIcon() {
		clickOnElement(createdDateTimeDescIcon);
	}

	public void clickOnStatusAscIcon() {
		clickOnElement(statusAscIcon);
	}

	public void clickOnStatusDescIcon() {
		clickOnElement(statusDescIcon);
	}

	public boolean isApproveRejectOptionDisplayed() {
		return isElementDisplayed(approveRejectButton);
	}

	public boolean isViewOptionDisplayed() {
		return isElementDisplayed(deviceListViewOption);
	}

	public boolean isDeactivateOptionDisplayed() {
		return isElementDisplayed(deviceListDeactivateOption);
	}

	public boolean isApproceRejectDeviceDisabled() {
		if (isElementDisplayed(approveRejectWithGreyedOut)) {
			return true;
		} else {
			return false;
		}
	}

	public void clickOnDeactivateButton() {
		clickOnElement(deviceListDeactivateOption);
	}

	public boolean isApproceRejectpopupDisplayed() {
		return isElementDisplayed(approvePopupTitle);
	}

	public boolean isApprovePopupTitleDisplayed() {
		return isElementDisplayed(approvePopupTitle);
	}

	public boolean isApprovePopupSubTitleDisplayed() {
		return isElementDisplayed(approvePopupSubTitle);
	}

	public boolean isApprovePopupDescriptionDisplayed() {
		return isElementDisplayed(approvePopupDescription);
	}

	public boolean isApprovePopupCloseIconDisplayed() {
		return isElementDisplayed(approvePopupCloseIcon);
	}

	public void clickOnApprovePopupCloseIcon() {
		clickOnElement(approvePopupCloseIcon);
	}

	public boolean isFilterButtonDisabled() {
		return isElementDisabled(filterButton);
	}

	public boolean isResetFilterDisplayed() {
		return isElementDisplayed(resetFilter);
	}

	public void clickOnDeviceMakeFilter() {
		clickOnElement(deviceMakeFilter);
	}

	public void clickOnDeviceModelFilter() {
		clickOnElement(deviceModelFilter);
	}

	public boolean isDeviceMakeFilterSearchBarDisplayed() {
		return isElementDisplayed(deviceMakeFilterSearchBar);
	}

	public boolean isDeviceModelFilterSearchBarDisplayed() {
		return isElementDisplayed(deviceModelFilterSearchBar);
	}

	public void enterInvalidValueInDeviceMakeFilter(String invalidMake) {
		enter(deviceMakeFilterSearchBar, invalidMake);
	}

	public void enterInvalidValueInDeviceModelFilter(String invalidModel) {
		enter(deviceModelFilterSearchBar, invalidModel);
	}

	public boolean isMakeDropdownNoDataAvailableDisplayed() {
		return isElementDisplayed(noDataAvailableMessage);
	}

	public boolean isModelDropdownNoDataAvailableDisplayed() {
		return isElementDisplayed(noDataAvailableMessage);
	}

	public boolean isDeviceIdPlaceHolderDisplayed() {
		return isElementDisplayed(deviceIdPlaceHolder);
	}

	public boolean isDeviceTypePlaceHolderDisplayed() {
		return isElementDisplayed(deviceTypePlaceHolder);
	}

	public boolean isDeviceSubTypePlaceHolderDisplayed() {
		return isElementDisplayed(deviceSubTypePlaceHolder);
	}

	public boolean isMakePlaceHolderDisplayed() {
		return isElementDisplayed(makePlaceHolder);
	}

	public boolean isModelPlaceHolderDisplayed() {
		return isElementDisplayed(modelPlaceHolder);
	}

	public boolean isStatusPlaceHolderDisplayed() {
		return isElementDisplayed(statusPlaceHolder);
	}

	public boolean isDeviceCreationDateSameAsBrowserDateFormat() {

		WebElement dateCell = driver.findElement(By.xpath("//tr[@id='device_list_device_item1']/td[6]"));
		String browserTime = dateCell.getText().trim();

		DateTimeFormatter dateFormatter = PmpTestUtil.nonZeroPadderDateFormatter;
		try {
			LocalDate.parse(browserTime, dateFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public boolean isDeactivateDevicePopupDisplayed() {
		return isElementDisplayed(deactivateDeviceText);
	}

	public boolean isDeactivateDevicePopupTitleDisplayed() {
		return isElementDisplayed(deactivateDeviceText);
	}

	public boolean isDeactivateDeviceSubtitleDisplayed() {
		return isElementDisplayed(deactivateDeviceSubtitle);
	}

	public boolean isDeactivateSubmitButtonDisplayed() {
		return isElementDisplayed(deactivateSubmit);
	}

	public boolean isDeactivateCancelButtonDisplayed() {
		return isElementDisplayed(deactivateCancel);
	}

	public void enterInvalidSbiVersionInFilter(String value) {
		enter(sbiVersionFilter, value);
	}

	public void clickOnApplyFilterButton() {
		clickOnElement(applyFilter);
	}

	public boolean isNoResultsFoundDisplayed() {
		return isElementDisplayed(noResultsFound);
	}

	public void enterSbiVersionInFilter(String value) {
		clickOnElement(sbiVersionFilter);
		clickOnElement(cancelButtonInFilter);
		enter(sbiVersionFilter, value);
	}

	public boolean isCreationDateSameAsBrowserDateFormat() {
		WebElement dateCell = driver.findElement(By.xpath("//tr[@id='device_list_item1']/td[10]"));
		String browserTime = dateCell.getText().trim();
		DateTimeFormatter dateFormatter = PmpTestUtil.nonZeroPadderDateFormatter;
		try {
			LocalDate.parse(browserTime, dateFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public void clickOnDeviceThreeDotsInAdmin(String deviceType, String deviceSubType, String make, String model) {
		By addedDeviceThreeDots = By
				.xpath("//td[text()='" + deviceType + "']/..//td[text()='" + deviceSubType + "']/..//td[text()='" + make
						+ "']/..//td[text()='" + model + "']/..//button[contains(@id, 'device_list_action_menu')]");
		click(addedDeviceThreeDots);
	}

	public void clickOnViewDeviceOfTabularInAdmin() {
		clickOnElement(viewDeviceInAdmin);
	}

	public void enterPartnerIdInFilterInAdmin(String value) {
		enter(partnerIdFilterInAdmin, value);
	}

	public boolean isListOfDevicesTitleDisplayed(String count) {
		By deviceList = By
				.xpath("//p[@id='list_of_device_details_title' and text()='List of Devices (" + count + ")']");
		return isDisplayed(deviceList);
	}

	public boolean isDeactivateDevicePopupInAdminDisplayed() {
		return isElementDisplayed(deactivateDevicePopupInAdmin);
	}

	public boolean isDeactivateDevicePopupTitleInAdminDisplayed(String device) {
		By title = By.xpath(
				"//p[@id='deactivate_popup_header' and contains(normalize-space(text()), \"Do you want to deactivate Device  - '"
						+ device + "'\")]");
		return isDisplayed(title);
	}

	public boolean isDeactivateDevicePopupDescInAdminDisplayed() {
		return isElementDisplayed(deactivateDevicePopupDescInAdmin);
	}

	public boolean isDeactivateDeviceEnabledInAdmin() {
		if (isElementDisplayed(deactivateDeviceEnabledButtonInAdmin)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDeactivateDeviceDisabledInAdmin() {
		if (isElementDisplayed(deactivateDeviceWithGreyedOutInAdmin)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isListOfDeviceInAdminDisplayed() {
		return isElementDisplayed(listOfDeviceInAdmin);
	}

	public boolean isDeviceIdPlaceHolderInAdminDisplayed() {
		return isElementDisplayed(deviceIdFilterPlaceHolderInAdmin);
	}

	public boolean isDeviceTypePlaceHolderInAdminDisplayed() {
		return isElementDisplayed(deviceTypePlaceHolder);
	}

	public boolean isDeviceSubTypePlaceHolderInAdminDisplayed() {
		return isElementDisplayed(deviceSubTypePlaceHolder);
	}

	public boolean isMakePlaceHolderInAdminDisplayed() {
		return isElementDisplayed(makeFilterPlaceHolderInAdmin);
	}

	public boolean isModelPlaceHolderInAdminDisplayed() {
		return isElementDisplayed(modelFilterPlaceHolderInAdmin);
	}

	public boolean isStatusPlaceHolderInAdminDisplayed() {
		return isElementDisplayed(statusPlaceHolder);
	}

	public boolean isPartneIdPlaceHolderInAdminDisplayed() {
		return isElementDisplayed(partnerIdFilterPlaceHolderInAdmin);
	}

	public boolean isOrganisationPlaceHolderInAdminDisplayed() {
		return isElementDisplayed(organisationFilterPlaceHolderInAdmin);
	}

	public boolean isSbiIdPlaceHolderInAdminDisplayed() {
		return isElementDisplayed(sbiIdFilterPlaceHolderInAdmin);
	}

	public boolean isSbiVersionPlaceHolderInAdminDisplayed() {
		return isElementDisplayed(sbiVersionFilterPlaceHolderInAdmin);
	}

	public void enterInvalidValueInDeviceMakeFilterInAdmin(String invalidMake) {
		clickOnElement(adminMakeFilter);
		enter(adminMakeFilter, invalidMake);
	}

	public void enterInvalidValueInDeviceModelFilterInAdmin(String invalidModel) {
		clickOnElement(adminModelFilter);
		enter(adminModelFilter, invalidModel);
	}

	public boolean isNoResultsFoundInAdminDisplayed() {
		return isElementDisplayed(noResultsFoundInAdmin);
	}

	public void selectDeviceStatusFilterInAdmin(String status) {
		clickOnElement(deviceStatusFilterInAdmin);
		By statusOption = By.xpath("//button[contains(@id, 'status_filter_option') and text()='" + status + "']");
		click(statusOption);
	}

	public boolean isHomeBreadcumbDisplayed() {
		return isElementDisplayed(homeButton);
	}

	public boolean isListOfDevicesTitleInPartnerDisplayed() {
		return isElementDisplayed(isListOfDevicesTitleInPartner);
	}

	public boolean isPartnerIdInFirstColumnDisplayed() {
		return isElementDisplayed(partnerIdInFirstColumn);
	}

	public boolean isPartnerIdInFirstColumnInPartnerDisplayed() {
		return isElementDisplayed(partnerIdInFirstColumnInPartner);
	}

	public boolean isPartnerIdAscIconInAdminDisplayed() {
		return isElementDisplayed(partnerIdAscIconInAdmin);
	}

	public boolean isPartnerIdDescIconInAdminDisplayed() {
		return isElementDisplayed(partnerIdDescIconInAdmin);
	}

	public boolean isOrgNameAscIconInAdminDisplayed() {
		return isElementDisplayed(orgNameAscIconInAdmin);
	}

	public boolean isOrgNameDescIconInAdminDisplayed() {
		return isElementDisplayed(orgNameDescIconInAdmin);
	}

	public boolean isSbiIdAscIconInAdminDisplayed() {
		return isElementDisplayed(sbiIdAscIconInAdmin);
	}

	public boolean isSbiIdDescIconInAdminDisplayed() {
		return isElementDisplayed(sbiIdDescIconInAdmin);
	}

	public boolean isSbiVersionAscIconInAdminDisplayed() {
		return isElementDisplayed(sbiVersionAscIconInAdmin);
	}

	public boolean isSbiVersionDescIconInAdminDisplayed() {
		return isElementDisplayed(sbiVersionDescIconInAdmin);
	}

	public boolean isDeviceIdAscIconInAdminDisplayed() {
		return isElementDisplayed(deviceIdAscIconInAdmin);
	}

	public boolean isDeviceIdDescIconInAdminDisplayed() {
		return isElementDisplayed(deviceIdDescIconInAdmin);
	}

	public boolean isDeviceTypeAscIconInAdminDisplayed() {
		return isElementDisplayed(deviceTypeAscIconInAdmin);
	}

	public boolean isDeviceTypeDescIconInAdminDisplayed() {
		return isElementDisplayed(deviceTypeDescIconInAdmin);
	}

	public boolean isDeviceSubTypeAscIconInAdminDisplayed() {
		return isElementDisplayed(deviceSubTypeAscIconInAdmin);
	}

	public boolean isDeviceSubTypeDescIconInAdminDisplayed() {
		return isElementDisplayed(deviceSubTypeDescIconInAdmin);
	}

	public boolean isMakeAscIconInAdminDisplayed() {
		return isElementDisplayed(makeAscIconInAdmin);
	}

	public boolean isMakeDescIconInAdminDisplayed() {
		return isElementDisplayed(makeDescIconInAdmin);
	}

	public boolean isModelAscIconInAdminDisplayed() {
		return isElementDisplayed(modelAscIconInAdmin);
	}

	public boolean isModelDescIconInAdminDisplayed() {
		return isElementDisplayed(modelDescIconInAdmin);
	}

	public boolean isCreatedDateTimeAscIconInAdminDisplayed() {
		return isElementDisplayed(createdDateTimeAscIconInAdmin);
	}

	public boolean isCreatedDateTimeDescIconInAdminDisplayed() {
		return isElementDisplayed(createdDateTimeDescIconInAdmin);
	}

	public boolean isStatusAscIconInAdminDisplayed() {
		return isElementDisplayed(statusAscIconInAdmin);
	}

	public boolean isStatusDescIconInAdminDisplayed() {
		return isElementDisplayed(statusDescIconInAdmin);
	}

	public void clickOnPartnerIdAscIconInAdmin() {
		clickOnElement(partnerIdAscIconInAdmin);
	}

	public void clickOnPartnerIdDescIconInAdmin() {
		clickOnElement(partnerIdDescIconInAdmin);
	}

	public void clickOnOrgNameAscIconInAdmin() {
		clickOnElement(orgNameAscIconInAdmin);
	}

	public void clickOnOrgNameDescIconInAdmin() {
		clickOnElement(orgNameDescIconInAdmin);
	}

	public void clickOnSbiIdAscIconInAdmin() {
		clickOnElement(sbiIdAscIconInAdmin);
	}

	public void clickOnSbiIdDescIconInAdmin() {
		clickOnElement(sbiIdDescIconInAdmin);
	}

	public void clickOnSbiVersionAscIconInAdmin() {
		clickOnElement(sbiVersionAscIconInAdmin);
	}

	public void clickOnSbiVersionDescIconInAdmin() {
		clickOnElement(sbiVersionDescIconInAdmin);
	}

	public void clickOnDeviceIdAscIconInAdmin() {
		clickOnElement(deviceIdAscIconInAdmin);
	}

	public void clickOnDeviceIdDescIconInAdmin() {
		clickOnElement(deviceIdDescIconInAdmin);
	}

	public void clickOnDeviceSubTypeAscIconInAdmin() {
		clickOnElement(deviceSubTypeAscIconInAdmin);
	}

	public void clickOnDeviceSubTypeDescIconInAdmin() {
		clickOnElement(deviceSubTypeDescIconInAdmin);
	}

	public void clickOnMakeAscIconInAdmin() {
		clickOnElement(makeAscIconInAdmin);
	}

	public void clickOnMakeDescIconInAdmin() {
		clickOnElement(makeDescIconInAdmin);
	}

	public void clickOnModelAscIconInAdmin() {
		clickOnElement(modelAscIconInAdmin);
	}

	public void clickOnModelDescIconInAdmin() {
		clickOnElement(modelDescIconInAdmin);
	}

	public void clickOnCreatedDateTimeAscIconInAdmin() {
		clickOnElement(createdDateTimeAscIconInAdmin);
	}

	public void clickOnCreatedDateTimeDescIconInAdmin() {
		clickOnElement(createdDateTimeDescIconInAdmin);
	}

	public void clickOnStatusAscIconInAdmin() {
		clickOnElement(statusAscIconInAdmin);
	}

	public void clickOnStatusDescIconInAdmin() {
		clickOnElement(statusDescIconInAdmin);
	}
	
	public boolean isDeviceIdDisplayedInFifthColumnOnSbiDevicePage() {
	    By deviceIdColumnHeader = By.id("devicesList.deviceId_header");
	    return isDisplayed(deviceIdColumnHeader);
	}

	private static final By REJECT_ONLY_POPUP_TITLE = By.id("reject_popup_title");
	private static final By REJECT_ONLY_POPUP_HEADER = By.id("reject_popup_header");
	private static final By REJECT_ONLY_POPUP_DESCRIPTION = By.id("reject_popup_description");
	private static final By REJECT_ONLY_POPUP_REJECT_BTN = By.id("reject_popup_reject_btn");
	private static final By REJECT_ONLY_POPUP_CLOSE_ICON = By.id("reject_popup_close_icon");
	private static final By APPROVE_BTN = By.id("approve_btn");
	private static final By DEVICE_ROWS = By.xpath("//tr[starts-with(@id,'device_list_item')]");

	private static final String LINKED_SBI_HEADER_ID = "sbiList.sbiId_header";
	private static final String STATUS_HEADER_ID = "devicesList.status_header";

	private static final String EMPTY_CELL_PLACEHOLDER = "-";

	public boolean isRejectOnlyPopupDisplayed() {
		return isDisplayed(REJECT_ONLY_POPUP_TITLE);
	}

	public boolean isRejectOnlyPopupHeaderDisplayed() {
		return isDisplayed(REJECT_ONLY_POPUP_HEADER);
	}

	public boolean isRejectOnlyPopupDescriptionDisplayed() {
		return isDisplayed(REJECT_ONLY_POPUP_DESCRIPTION);
	}

	public boolean isRejectOnlyPopupRejectButtonDisplayed() {
		return isDisplayed(REJECT_ONLY_POPUP_REJECT_BTN);
	}

	public boolean isApproveButtonAbsentInRejectOnlyPopup() {
		boolean present = isElementDisplayedQuick(APPROVE_BTN, Duration.ofSeconds(5));
		if (present) {
			LogUtil.error("An Approve button is offered for a device that is not linked to an SBI");
			takeScreenshot();
		}
		return !present;
	}

	public void clickOnRejectOnlyPopupCloseIcon() {
		click(REJECT_ONLY_POPUP_CLOSE_ICON);
	}

	public int findOrphanPendingDeviceRow() {
		int linkedSbiColumn = getColumnIndex(LINKED_SBI_HEADER_ID);
		int statusColumn = getColumnIndex(STATUS_HEADER_ID);

		if (linkedSbiColumn < 0 || statusColumn < 0) {
			LogUtil.step("Linked SBI or Status column is not on this list, so no orphan device can be identified");
			return -1;
		}

		List<WebElement> rows = driver.findElements(DEVICE_ROWS);
		for (int i = 0; i < rows.size(); i++) {
			try {
				List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
				if (cells.size() <= Math.max(linkedSbiColumn, statusColumn)) {
					continue;
				}

				String linkedSbi = cells.get(linkedSbiColumn).getText().trim();
				String status = cells.get(statusColumn).getText().trim();

				if (isUnlinked(linkedSbi) && status.equalsIgnoreCase(GlobalConstants.PENDING_FOR_APPROVAL)) {
					LogUtil.step("Orphan pending device found at row " + (i + 1));
					return i + 1;
				}
			} catch (StaleElementReferenceException e) {
				LogUtil.step("Row went stale while scanning for an orphan device; skipping row " + (i + 1));
			}
		}
		LogUtil.step("No Pending for Approval device without a linked SBI on this page");
		return -1;
	}

	public void clickOnDeviceListActionMenu(int rowNumber) {
		click(By.id("device_list_action_menu" + rowNumber));
	}

	public boolean isLinkedSbiColumnEmpty(int rowNumber) {
		int linkedSbiColumn = getColumnIndex(LINKED_SBI_HEADER_ID);
		if (linkedSbiColumn < 0) {
			LogUtil.error("Linked SBI column is not present on this list");
			takeScreenshot();
			return false;
		}

		String value = getTextFromLocator(By.xpath(
				"//tr[@id='device_list_item" + rowNumber + "']/td[" + (linkedSbiColumn + 1) + "]")).trim();
		LogUtil.step("Linked SBI value at row " + rowNumber + ": '" + value + "'");
		return isUnlinked(value);
	}

	private boolean isUnlinked(String linkedSbiCellValue) {
		return linkedSbiCellValue.isEmpty() || EMPTY_CELL_PLACEHOLDER.equals(linkedSbiCellValue);
	}
}
