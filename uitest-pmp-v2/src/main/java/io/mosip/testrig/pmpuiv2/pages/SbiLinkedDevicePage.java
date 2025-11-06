package io.mosip.testrig.pmpuiv2.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;

public class SbiLinkedDevicePage extends BasePage {

	@FindBy(id = "page_title")
	private WebElement sbi_DeviceListTitle;

	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;

	@FindBy(id = "list_of_device_details_sub_title")
	private WebElement sbiIdAndSbiVersion;

	@FindBy(id = "filter_btn")
	private WebElement filterButton;
	
	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;
	
	@FindBy(id = "sbiList.partnerId_header")
	private WebElement partnerIdHeader;
	
	@FindBy(id = "sbiList.orgName_header")
	private WebElement organisationHeader;
	
	@FindBy(id = "devicesList.deviceId_header")
	private WebElement deviceIdHeader;
	
	@FindBy(id = "devicesList.deviceType_header")
	private WebElement deviceTypeHeader;
	
	@FindBy(id = "devicesList.deviceSubType_header")
	private WebElement deviceSubtypeHeader;
	
	@FindBy(id = "devicesList.make_header")
	private WebElement makeHeader;
	
	@FindBy(id = "devicesList.model_header")
	private WebElement modelHeader;
	
	@FindBy(id = "devicesList.creationDate_header")
	private WebElement creationDateHeader;
	
	@FindBy(id = "devicesList.status_header")
	private WebElement statusHeader;
	
	@FindBy(id = "devicesList.action_header")
	private WebElement actiondHeader;
	
	@FindBy(id = "partner_id_filter")
	private WebElement partnerIdFilter;
	
	@FindBy(id = "org_name_filter")
	private WebElement organisationFilter;
	
	@FindBy(id = "device_id_filter")
	private WebElement deviceIdFilter;
	
	@FindBy(id = "device_type_filter_dropdown_btn")
	private WebElement deviceTypeFilterDropdown;
	
	@FindBy(id = "device_sub_type_filter_dropdown_btn")
	private WebElement deviceSubTypeFilterDropdown;
	
	@FindBy(id = "make_filter")
	private WebElement makeFilter;
	
	@FindBy(id = "model_filter")
	private WebElement modelFilter;
	
	@FindBy(id = "status_filter_dropdown_btn")
	private WebElement statusFilterDropdown;
	
	@FindBy(id = "partner_id_filter_label")
	private WebElement partnerIdFilterLabel;
	
	@FindBy(id = "org_name_filter_label")
	private WebElement organisationFilterLabel;
	
	@FindBy(id = "device_id_filter_label")
	private WebElement deviceIdFilterLabel;
	
	@FindBy(id = "device_type_filter_label")
	private WebElement deviceTypeFilterLabel;
	
	@FindBy(id = "device_sub_type_filter_label")
	private WebElement deviceSubTypeFilterLabel;
	
	@FindBy(id = "make_filter_label")
	private WebElement makeFilterLabel;
	
	@FindBy(id = "model_filter_label")
	private WebElement modelFilterLabel;
	
	@FindBy(id = "status_filter_label")
	private WebElement statusFilterLabel;
	
	@FindBy(xpath = "//input[@placeholder='Search Partner ID']")
	private WebElement partnerIdPlaceHolder;
	
	@FindBy(xpath = "//input[@placeholder='Search Organisation']")
	private WebElement organisationPlaceHolder;
	
	@FindBy(xpath = "//input[@placeholder='Search Device ID']")
	private WebElement deviceIdPlaceHolder;
	
	@FindBy(xpath = "//input[@placeholder='Search Make']")
	private WebElement makePlaceHolder;
	
	@FindBy(xpath = "//input[@placeholder='Search Model']")
	private WebElement modelPlaceHolder;
	
	@FindBy(xpath = "//span[text()='Select Status']")
	private WebElement statusPlaceHolder;
	
	@FindBy(xpath = "//span[text()='Select Device Sub Type']")
	private WebElement deviceSubTypePlaceHolder;
	
	@FindBy(xpath = "//span[text()='Select Device Type']")
	private WebElement deviceTypePlaceHolder;
	
	@FindBy(id = "device_list_view_option")
	private WebElement deviceListViewOption;
	
	@FindBy(id = "device_list_deactivate_option")
	private WebElement deviceListDeactivateOption;
	
	@FindBy(xpath = "//p[@id='device_list_approve_reject_option' and contains(@class, 'text-[#A5A5A5]')]")
	private WebElement approveRejectWithGreyedOut;
	
	@FindBy(xpath = "//p[@id='device_list_deactivate_option' and contains(@class, 'text-[#3E3E3E]')]")
	private WebElement deactivateDeviceEnabledButtonInAdmin;
	
	@FindBy(id = "page_title")
	private WebElement linkedDeviceDetailsPage;
	
	@FindBy(id = "sub_title_btn")
	private WebElement listOfLinkedDevice;
	
	@FindBy(id= "apply_filter__btn")
	private WebElement applyFilterButton;
	
	@FindBy(id= "no_results_found")
	private WebElement noResultFound;
	
	@FindBy(id="partner_id_filter_cancel_btn")
	private WebElement filterCancelButton;
	
	@FindBy(id="status_filter_option1")
	private WebElement approvedStatus;
	
	@FindBy(id="list_of_device_details_title")
	private WebElement listOfLinkedDevicesText;
	
	@FindBy(id="status_filter_option2")
	private WebElement deactivatedStatus;
	
	@FindBy(id="status_filter_option3")
	private WebElement pendingForApprovalStatus;
	
	@FindBy(id="status_filter_option4")
	private WebElement rejectedStatus;
	

	public SbiLinkedDevicePage(WebDriver driver) {
		super(driver);
	}

	public boolean isLinkedDeviceListTitleDisplayed() {
		return isElementDisplayed(sbi_DeviceListTitle);
	}

	public boolean isHomeBreadcumbDisplayed() {
		return isElementDisplayed(homeButton);
	}

	public boolean isSbiIdAndSbiVersionDisplayed() {
		return isElementDisplayed(sbiIdAndSbiVersion);
	}

	public boolean isFilterButtonDisplayed() {
		return isElementDisplayed(filterButton);
	}

	public void clickOnFilterButton() {
		clickOnElement(filterButton);
	}
	
	public boolean isFilterButtonDisabled() {
		return isElementDisabled(filterButton);
	}
	
	public boolean isPartnerIdHeaderDisplayed() {
		return isElementDisplayed(partnerIdHeader);
	}
	
	public boolean isOrganisationHeaderDisplayed() {
		return isElementDisplayed(organisationHeader);
	}
	
	public boolean isDeviceIdHeaderDisplayed() {
		return isElementDisplayed(deviceIdHeader);
	}
	
	public boolean isDeviceTypeHeaderDisplayed() {
		return isElementDisplayed(deviceTypeHeader);
	}
	
	public boolean isDeviceSubtypeHeaderDisplayed() {
		return isElementDisplayed(deviceSubtypeHeader);
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
	
	public boolean isActiondHeaderDisplayed() {
		return isElementDisplayed(actiondHeader);
	}
	
	public boolean isPartnerIdFilterDisplayed() {
		return isElementDisplayed(partnerIdFilter);
	}
	
	public boolean isOrganisationFilterDisplayed() {
		return isElementDisplayed(organisationFilter);
	}
	
	public boolean isDeviceIdFilterDisplayed() {
		return isElementDisplayed(deviceIdFilter);
	}
	
	public boolean isDeviceTypeFilterDropdownDisplayed() {
		return isElementDisplayed(deviceTypeFilterDropdown);
	}
	
	public boolean isDeviceSubTypeFilterDropdownDisplayed() {
		return isElementDisplayed(deviceSubTypeFilterDropdown);
	}
	
	public boolean isMakeFilterDisplayed() {
		return isElementDisplayed(makeFilter);
	}
	
	public boolean isModelFilterDisplayed() {
		return isElementDisplayed(modelFilter);
	}
	
	public boolean isStatusFilterDropdownDisplayed() {
		return isElementDisplayed(statusFilterDropdown);
	}
	
	public boolean isPartnerIdFilterLabelDisplayed() {
		return isElementDisplayed(partnerIdFilterLabel);
	}
	
	public boolean isOrganisationFilterLabelDisplayed() {
		return isElementDisplayed(organisationFilterLabel);
	}
	
	public boolean isDeviceIdFilterLabelDisplayed() {
		return isElementDisplayed(deviceIdFilterLabel);
	}
	
	public boolean isDeviceTypeFilterLabelDisplayed() {
		return isElementDisplayed(deviceTypeFilterLabel);
	}
	
	public boolean isDeviceSubTypeFilterLabelDisplayed() {
		return isElementDisplayed(deviceSubTypeFilterLabel);
	}
	
	public boolean isMakeFilterLabelDisplayed() {
		return isElementDisplayed(makeFilterLabel);
	}
	
	public boolean isModelFilterLabelDisplayed() {
		return isElementDisplayed(modelFilterLabel);
	}
	
	public boolean isStatusFilterLabelDisplayed() {
		return isElementDisplayed(statusFilterLabel);
	}
	
	public boolean isPartnerIdPlaceHolderDisplayed() {
		return isElementDisplayed(partnerIdPlaceHolder);
	}
	
	public boolean isOrganisationPlaceHolderDisplayed() {
		return isElementDisplayed(organisationPlaceHolder);
	}
	
	public boolean isDeviceIdPlaceHolderDisplayed() {
		return isElementDisplayed(deviceIdPlaceHolder);
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
	
	public boolean isDeviceSubTypePlaceHolderDisplayed() {
		return isElementDisplayed(deviceSubTypePlaceHolder);
	}
	
	public boolean isDeviceTypePlaceHolderDisplayed() {
		return isElementDisplayed(deviceTypePlaceHolder);
	}
	
	public void clickOnDeviceThreeDots(String deviceType, String deviceSubType, String make, String model) {
		WebElement addedDeviceThreeDots = driver.findElement(
				By.xpath("//td[text()='" + deviceType + "']/..//td[text()='" + deviceSubType + "']/..//td[text()='"
						+ make + "']/..//td[text()='" + model + "']/..//button[contains(@id, 'device_list_action_menu1')]"));
		clickOnElement(addedDeviceThreeDots);
	}
	
//	public boolean isApproveRejectOptionDisplayed() {
//		return isElementDisplayed(approveRejectButton);
//	}

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
	
	public boolean isDeactivateDeviceEnabledInAdmin() {
		if (isElementDisplayed(deactivateDeviceEnabledButtonInAdmin)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isLinkedDeviceDetailsPageTitleDisplayed() {
		return isElementDisplayed(linkedDeviceDetailsPage);
	}
	
	public void clickOnListOfDevices() {
		clickOnElement(listOfLinkedDevice);
	}
	
	public void clickOnLinkedDevice(String deviceType, String deviceSubType, String make, String model, String status) {
		WebElement addedDevice = driver.findElement(By.xpath("//td[text()='" + deviceType + "']/..//td[text()='"
				+ deviceSubType + "']/..//td[text()='" + make + "']/..//td[text()='" + model + "']"));
		clickOnElement(addedDevice);
	}
	
	public boolean isFilterResetButtonDisplayed() {
		return isElementDisplayed(filterResetButton);
	}
	
	public void enterPartnerIdInFilter(String value) {
		enter(partnerIdFilter, value);
	}
	
	public void clickOnApplyFilterButton() {
		clickOnElement(applyFilterButton);
	}
	
	public boolean isNoResultFoundDisplayed() {
		return isElementDisplayed(noResultFound);
	}
	
	public void clickOnFilterCancelButton() {
		clickOnElement(filterCancelButton);
	}
	
	public void clickOnApprovedStatusInFilter() {
		clickOnElement(statusFilterDropdown);
		clickOnElement(approvedStatus);
	}
	
	public boolean getListOfLinkedDevices() {
		return isElementDisplayed(listOfLinkedDevicesText);
	}

	public boolean isApprovedStatusDisplayed() {
		return isElementDisplayed(approvedStatus);
	}
	
	public boolean isDeactivatedStatusDisplayed() {
		return isElementDisplayed(deactivatedStatus);
	}
	
	public boolean isPendingForApprovalStatusDisplayed() {
		return isElementDisplayed(pendingForApprovalStatus);
	}
	
	public boolean isRejectedStatusDisplayed() {
		return isElementDisplayed(rejectedStatus);
	}
	
	public void clickOnFilterResetButton() {
		clickOnElement(filterResetButton);
	}
	
	public boolean isLinkedDeviceCreationDateSameAsBrowserDateFormat() {

		WebElement dateCell = driver.findElement(By.xpath("//tr[@id='device_list_item1']/td[8]"));
		String browserTime = dateCell.getText().trim();

		DateTimeFormatter dateFormatter = PmpTestUtil.nonZeroPadderDateFormatter;
		try {
			LocalDate.parse(browserTime, dateFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}

	}
}
