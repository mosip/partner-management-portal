package io.mosip.testrig.pmpuiv2.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class ListOfSbiPage extends BasePage {

	@FindBy(id = "ftm_list_approve_reject_option")
	private WebElement approveRejectButton;

	@FindBy(id = "sbi_list_deactivate")
	private WebElement deactivateSbiButton;

	@FindBy(id = "deactivate_submit_btn")
	private WebElement deactivateSubmitButton;

	@FindBy(id = "sbi_list_arrow1")
	private WebElement listOfSbiArrowButton;

	@FindBy(id = "devices_tab")
	private WebElement deviceTab;

	@FindBy(id = "deactivate_cancel_btn")
	private WebElement deactivateCancel;

	@FindBy(xpath = "//p[contains(text(),'Note: 1. User')]")
	private WebElement listOfSbiMessage;

	@FindBy(xpath = "//p[contains(normalize-space(), 'device') and contains(normalize-space(), 'approved')]/span")
	private WebElement approvedDeviceCount;

	@FindBy(xpath = "//button[text()='Approve']")
	private WebElement approveButton;

	@FindBy(xpath = "//button[text()='Reject']")
	private WebElement rejectButton;

	@FindBy(xpath = "//p[text()='pending for approval under this SBI']")
	private WebElement devicePendingForApprovalCount;

	@FindBy(xpath = "//p[contains(text(), 'Do you want to deactivate SBI Version')]")
	private WebElement deactivateSbiPopupTitle;

	@FindBy(xpath = "//p[contains(text(), 'On clicking Confirm')]")
	private WebElement deactivateSbiPopupMessage;

	@FindBy(xpath = "//p[contains(text(), 'approved devices associated')]")
	private WebElement deactivateSbiPopupDeviceDetails;

	@FindBy(xpath = "//p[text()='Partner ID']")
	private WebElement partnerIdText;

	@FindBy(xpath = "//p[text()='pmpui-device']")
	private WebElement partnerIdValueText;

	@FindBy(xpath = "//p[text()='Partner Type']")
	private WebElement partnerTypeText;

	@FindBy(xpath = "//p[text()='Device Provider']")
	private WebElement partnerTypeValueText;

	@FindBy(xpath = "//p[text()='Submitted On']")
	private WebElement submittedOnText;

	@FindBy(xpath = "//p[text()='SBI Creation Date']")
	private WebElement sbiCreationDateText;

	@FindBy(xpath = "//p[text()='SBI Expiration Date']")
	private WebElement sbiExpirationDateText;

	@FindBy(xpath = "//button[@id='deactivate_submit_btn' and contains(@class, 'bg-tory-blue text-white')]")
	private WebElement highlightedDeactivateSbiConfirm;

	@FindBy(xpath = "//p[text()='Automation123' and contains(@class, 'text-[#8E8E8E]')]/../../../../..//div[contains(@class, 'bg-[#EAECF0]')]")
	private WebElement deactivatedSbiGreyedOut;

	@FindBy(xpath = "//div[@id='sbi_list_deactivate' and contains(@class, 'cursor-auto')]")
	private WebElement deactivateSbiButtonWithGreyedOut;

	@FindBy(xpath = "//p[text()='Do you want to Approve or Reject the SBI?']")
	private WebElement approveOrRejectSbiPopup;

	@FindBy(xpath = "//p[text()='Automation']")
	private WebElement approveOrRejectSbiPopupTitle;

	@FindBy(xpath = "//p[text()='Please review the SBI details carefully before taking appropriate action.']")
	private WebElement approveOrRejectSbiPopupInfo;

	@FindBy(xpath = "//p[contains(text(), 'List of SBIs')]")
	private WebElement sbiAdminTitle;

	@FindBy(id = "sub_title_home_btn")
	private WebElement sbiAdminBreadcumb;

	@FindBy(id = "filter_btn")
	private WebElement filterButton;

	@FindBy(id = "partner_id_filter")
	private WebElement partnerIdFilter;

	@FindBy(id = "org_name_filter")
	private WebElement organisationFilter;

	@FindBy(id = "sbi_id_filter")
	private WebElement sbiIdFilter;

	@FindBy(id = "sbi_version_filter")
	private WebElement sbiVersionFilter;

	@FindBy(id = "sbi_expiry_status_filter_dropdown_btn")
	private WebElement sbiExpiryStatusFilter;

	@FindBy(id = "status_filter_dropdown_btn")
	private WebElement statusFilter;

	@FindBy(id = "status_filter_option3")
	private WebElement pendingForApprovalStatus;

	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilterButton;

	@FindBy(xpath = "//p[text()='No Results Found']")
	private WebElement noResultsFound;

	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;

	@FindBy(xpath = "//div[text()='Partner ID']")
	private WebElement partnerIdHeader;

	@FindBy(xpath = "//div[text()='Organisation']")
	private WebElement organisationHeader;

	@FindBy(xpath = "//div[text()='SBI ID']")
	private WebElement sbiIdHeader;

	@FindBy(xpath = "//div[text()='SBI Version']")
	private WebElement sbiVersionHeader;

	@FindBy(xpath = "//div[text()='SBI Creation Date']")
	private WebElement sbiCreationDateHeader;

	@FindBy(xpath = "//div[text()='SBI Expiration Date']")
	private WebElement sbiExpirationDateHeader;

	@FindBy(xpath = "//div[text()='SBI Expiry Status']")
	private WebElement sbiExpiryStatusHeader;

	@FindBy(xpath = "//div[text()='Creation Date']")
	private WebElement creationDateHeader;

	@FindBy(xpath = "//div[text()='Status']")
	private WebElement statusHeader;

	@FindBy(xpath = "//div[text()='Linked Devices']")
	private WebElement linkedDevicesHeader;

	@FindBy(xpath = "//div[text()='Action']")
	private WebElement actionHeader;

	@FindBy(id = "sbi_list_item1")
	private WebElement sbiListItem1;

	@FindBy(xpath = "//h1[text()='View SBI Details']")
	private WebElement sbiDetailsPage;

	@FindBy(id = "view_admin_sbi_details_back_btn")
	private WebElement sbiDetailsBackButton;

	@FindBy(id = "sbi_list_view_btn")
	private WebElement sbiViewButton;

	@FindBy(xpath = "//button[.//img[contains(@src, 'active_linked_devices_icon')]]")
	private WebElement linkedDevices;

	@FindBy(xpath = "//p[@id='ftm_list_approve_reject_option' and contains(@class, 'text-[#A5A5A5]')]")
	private WebElement approveRejectOptionGreyedOut;

	@FindBy(id = "partnerId_asc_icon")
	private WebElement partnerIdAscIcon;

	@FindBy(id = "partnerId_desc_icon")
	private WebElement partnerIdDescIcon;

	@FindBy(id = "orgName_asc_icon")
	private WebElement orgNameAscIcon;

	@FindBy(id = "orgName_desc_icon")
	private WebElement orgNameDescIcon;

	@FindBy(id = "sbiId_asc_icon")
	private WebElement sbiIdAscIcon;

	@FindBy(id = "sbiId_desc_icon")
	private WebElement sbiIdDescIcon;

	@FindBy(id = "sbiCreatedDateTime_asc_icon")
	private WebElement sbiCreatedDateTimeAscIcon;

	@FindBy(id = "sbiCreatedDateTime_desc_icon")
	private WebElement sbiCreatedDateTimeDescIcon;

	@FindBy(id = "sbiExpiryDateTime_asc_icon")
	private WebElement sbiExpiryDateTimeAscIcon;

	@FindBy(id = "sbiExpiryDateTime_desc_icon")
	private WebElement sbiExpiryDateTimeDescIcon;

	@FindBy(id = "sbiExpiryStatus_asc_icon")
	private WebElement sbiExpiryStatusAscIcon;

	@FindBy(id = "sbiExpiryStatus_desc_icon")
	private WebElement sbiExpiryStatusDescIcon;

	@FindBy(id = "createdDateTime_asc_icon")
	private WebElement createdDateTimeAscIcon;

	@FindBy(id = "createdDateTime_desc_icon")
	private WebElement createdDateTimeDescIcon;

	@FindBy(id = "status_asc_icon")
	private WebElement statusAscIcon;

	@FindBy(id = "status_desc_icon")
	private WebElement statusDescIcon;

	@FindBy(id = "countOfAssociatedDevices_asc_icon")
	private WebElement countOfAssociatedDevicesAscIcon;

	@FindBy(id = "countOfAssociatedDevices_desc_icon")
	private WebElement countOfAssociatedDevicesDescIcon;

	@FindBy(xpath = "//p[contains(., 'All rights reserved')]")
	private WebElement mosipFooterText;

	@FindBy(xpath = "//p[contains(text(), 'List of Linked Devices')]")
	private WebElement subTitleOfLinkedDevices;

	@FindBy(id = "subtitle_back_icon")
	private WebElement backIconOfLinkedDevices;

	public ListOfSbiPage(WebDriver driver) {
		super(driver);
	}

	public String getListOfSbiMessage() {
		return getTextFromLocator(listOfSbiMessage);
	}

	public boolean isDeviceApprovedCountDisplayed() {
		return isElementDisplayed(approvedDeviceCount);
	}

	public boolean isDevicePendingForApprovalCountDisplayed() {
		return isElementDisplayed(devicePendingForApprovalCount);
	}

	public boolean isAddDeviceButtonEnabled(String sbiVersion) {
		WebElement addDeviceButton = driver.findElement(
				By.xpath("//p[text()='" + sbiVersion + "']/../../..//button[contains(@id, 'sbi_list_add_Devices')]"));
		return isElementEnabled(addDeviceButton);
	}

	public boolean isRejectedStatusDisplayed(String sbiVersion) {
		WebElement rejected = driver.findElement(By.xpath("//p[text()='" + sbiVersion
				+ "']/..//div[contains(@class, 'bg-[#FAD6D1] text-[#5E1515]') and text()='Rejected']"));
		return isElementDisplayed(rejected);
	}

	public boolean getDeviceDetails(String sbiVersion) {
		WebElement deviceDetails = driver.findElement(
				By.xpath("//p[text()='" + sbiVersion + "']/..//div[@class='flex items-center w-fit px-2 mx-1']"));
		return isElementDisplayed(deviceDetails);
	}

	public boolean isViewDeviceButtonEnabled(String sbiVersion) {
		WebElement addDeviceButton = driver.findElement(
				By.xpath("//p[text()='" + sbiVersion + "']/../../..//button[contains(@id, 'sbi_list_view_Devices')]"));
		return isElementEnabled(addDeviceButton);
	}

	public void clickOnViewDeviceButton(String sbiVersion) {
		WebElement addDeviceButton = driver.findElement(
				By.xpath("//p[text()='" + sbiVersion + "']/../../..//button[contains(@id, 'sbi_list_view_Devices')]"));
		clickOnElement(addDeviceButton);
	}

	public void clickOnThreeDotsOfSbiListAsAdmin(String sbiVersion) {
		WebElement threeDotSbiOptionsButton = driver
				.findElement(By.xpath("//*[text()='" + sbiVersion + "']/..//button[contains(@id, 'sbi_list_action')]"));
		clickOnElement(threeDotSbiOptionsButton);
	}

	public void clickOnThreeDotsOfSbiList(String sbiVersion) {
		WebElement threeDotSbiOptionsButton = driver.findElement(
				By.xpath("//p[text()='" + sbiVersion + "']/../../..//button[contains(@id, 'sbi_list_hamburger')]"));
		clickOnElement(threeDotSbiOptionsButton);
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

	public void clickOnAddDeviceButton(String sbiVersion) {
		WebElement addDeviceButton = driver.findElement(
				By.xpath("//p[text()='" + sbiVersion + "']/../../..//button[contains(@id, 'sbi_list_add_Devices')]"));
		clickOnElement(addDeviceButton);
	}

	public boolean isDeactivateOptionEnabled() {
		if (isElementDisplayed(deactivateSbiButtonWithGreyedOut)) {
			return false;
		}
		return isElementDisplayed(deactivateSbiButton);
	}

	public void clickOnDeactivateSbi() {
		clickOnElement(deactivateSbiButton);
	}

	public void clickOnDeactivateSbiCancel() {
		clickOnElement(deactivateCancel);
	}

	public boolean isDeactivateSbiPopupTitleDisplayed() {
		return isElementDisplayed(deactivateSbiPopupTitle);
	}

	public void clickOnDeactivateSubmit() {
		clickOnElement(deactivateSubmitButton);
	}

	public void clickOnDeviceTab() {
		clickOnElement(deviceTab);
	}

	public boolean isDeactivatedStatusDisplayed(String sbiVersion) {
		WebElement status = driver
				.findElement(By.xpath("//p[text()='" + sbiVersion + "']/..//div[text()='Deactivated']"));
		return isElementDisplayed(status);
	}

	public boolean isCreatedSbiDisplayed(String sbiVersion) {
		WebElement createdSbi = driver.findElement(By.xpath("//*[text()='" + sbiVersion + "']"));
		return isElementDisplayed(createdSbi);
	}

	public boolean isPendingForApprovalDisplayed(String sbiVersion) {
		WebElement status = driver
				.findElement(By.xpath("//p[text()='" + sbiVersion + "']/..//div[text()='Pending For Approval']"));
		return isElementDisplayed(status);
	}

	public boolean isPendingForApprovalDisplayedInAdminPage(String sbiVersion) {
		WebElement status = driver
				.findElement(By.xpath("//td[text()='" + sbiVersion + "']/..//div[text()='Pending For Approval']"));
		return isElementDisplayed(status);
	}

	public boolean isRejectedStatusDisplayedInAdminPage(String sbiVersion) {
		WebElement status = driver
				.findElement(By.xpath("//td[text()='" + sbiVersion + "']/..//div[text()='Rejected']"));
		return isElementDisplayed(status);
	}

	public String getSbiListArrowDirection() {
		return getTextFromAttribute(listOfSbiArrowButton, GlobalConstants.CLASS);
	}

	public void clickOnSbiListArrow(String sbiVersion) {
		WebElement sbiArrowButton = driver.findElement(
				By.xpath("//p[text()='" + sbiVersion + "']/../../..//button[contains(@id, 'sbi_list_arrow')]"));
		clickOnElement(sbiArrowButton);
	}

	public boolean isPartnerIdTextDisplayed() {
		return isElementDisplayed(partnerIdText);
	}

	public boolean isPartnerIdValueDisplayed() {
		return isElementDisplayed(partnerIdValueText);
	}

	public boolean isPartnerTypeTextDisplayed() {
		return isElementDisplayed(partnerTypeText);
	}

	public boolean isPartnerTypeValueDisplayed() {
		return isElementDisplayed(partnerTypeValueText);
	}

	public boolean isSubmittedOnTextDisplayed() {
		return isElementDisplayed(submittedOnText);
	}

	public boolean isSbiCreationDateTextDisplayed() {
		return isElementDisplayed(sbiCreationDateText);
	}

	public boolean isSbiExpirationDateTextDisplayed() {
		return isElementDisplayed(sbiExpirationDateText);
	}

	public boolean isSubmittedOnDateDisplayed() {
		WebElement status = driver.findElement(
				By.xpath(generateXpathWithDateAndTitle("Submitted On", PmpTestUtil.todayDateWithoutZeroPadder)));
		return isElementDisplayed(status);
	}

	public boolean isSbiCreationDateDisplayed() {
		WebElement status = driver.findElement(
				By.xpath(generateXpathWithDateAndTitle("SBI Creation Date", PmpTestUtil.todayDateWithoutZeroPadder)));
		return isElementDisplayed(status);
	}

	public boolean isPreviousMonthSbiCreationDateDisplayed() {
		WebElement status = driver.findElement(By.xpath(
				generateXpathWithDateAndTitle("SBI Creation Date", PmpTestUtil.previousMonth4thDateWithoutZeroPadder)));
		return isElementDisplayed(status);
	}

	public boolean isSbiExpirationDateDisplayed() {
		WebElement status = driver.findElement(By.xpath(
				generateXpathWithDateAndTitle("SBI Expiration Date", PmpTestUtil.nextMonth24thDateWithoutZeroPadder)));
		return isElementDisplayed(status);
	}

	private String generateXpathWithDateAndTitle(String title, String date) {
		String xpath = "//p[text()='" + title + "']/..//p[text()='" + date + "']";
		return xpath;
	}

	public String getDeactivateSbiPopupTitle() {
		return getTextFromLocator(deactivateSbiPopupTitle);
	}

	public String getDeactivateSbiPopupMessage() {
		return getTextFromLocator(deactivateSbiPopupMessage);
	}

	public String getDeactivateSbiPopupDeviceDetails() {
		return getTextFromLocator(deactivateSbiPopupDeviceDetails);
	}

	public boolean isHighlightedConfirmDeactivateSbiDisplayed() {
		return isElementDisplayed(highlightedDeactivateSbiConfirm);
	}

	public boolean isDeactivatedSbiGreyedOut() {
		return isElementDisplayed(deactivatedSbiGreyedOut);
	}

	public boolean isApproveRejectButtonEnabled() {
		return isElementEnabled(approveRejectButton);
	}

	public boolean isApproveOrRejectSbiPopupDisplayed() {
		return isElementDisplayed(approveOrRejectSbiPopup);
	}

	public boolean isApproveOrRejectSbiPopupSubtitleDisplayed() {
		return isElementDisplayed(approveOrRejectSbiPopup);
	}

	public boolean isApproveOrRejectSbiPopupTitleDisplayed() {
		return isElementDisplayed(approveOrRejectSbiPopupTitle);
	}

	public boolean isApproveOrRejectSbiPopupDescrDisplayed() {
		return isElementDisplayed(approveOrRejectSbiPopupInfo);
	}

	public boolean isSbiAdminListPageTitleDisplayed() {
		return isElementDisplayed(sbiAdminTitle);
	}

	public boolean isSbiAdminListPageBreadcumbDisplayed() {
		return isElementDisplayed(sbiAdminBreadcumb);
	}

	public boolean isFilterButtonDisplayed() {
		return isElementDisplayed(filterButton);
	}

	public void clickOnFilterButton() {
		clickOnElement(filterButton);
	}

	public boolean isPartnerIdFilterDisplayed() {
		return isElementDisplayed(partnerIdFilter);
	}

	public boolean isOrganisationFilterDisplayed() {
		return isElementDisplayed(organisationFilter);
	}

	public boolean isSbiIdFilterDisplayed() {
		return isElementDisplayed(sbiIdFilter);
	}

	public boolean isSbiVersionFilterDisplayed() {
		return isElementDisplayed(sbiVersionFilter);
	}

	public boolean isSbiExpiryStatusFilterDisplayed() {
		return isElementDisplayed(sbiExpiryStatusFilter);
	}

	public boolean isStatusFilterDisplayed() {
		return isElementDisplayed(statusFilter);
	}

	public boolean isFilterButtonDisabled() {
		return isElementDisabled(filterButton);
	}

	public boolean isApplyFilterButtonDisabled() {
		return isElementDisabled(applyFilterButton);
	}

	public void enterPartnerIdInFilter(String value) {
		enter(partnerIdFilter, value);
	}

	public void enterSbiVersionInFilter(String value) {
		enter(sbiVersionFilter, value);
	}

	public void selectPendingForApprovalStatusInFilter() {
		clickOnElement(statusFilter);
		clickOnElement(pendingForApprovalStatus);
	}

	public void clickOnApplyFilterButton() {
		clickOnElement(applyFilterButton);
	}

	public boolean isNoResultsFoundDisplayed() {
		return isElementDisplayed(noResultsFound);
	}

	public void clickOnFilterResetButton() {
		clickOnElement(filterResetButton);
	}

	public boolean isPartnerIdHeaderDisplayed() {
		return isElementDisplayed(partnerIdHeader);
	}

	public boolean isOrganisationHeaderDisplayed() {
		return isElementDisplayed(organisationHeader);
	}

	public boolean isSbiIdHeaderDisplayed() {
		return isElementDisplayed(sbiIdHeader);
	}

	public boolean isSbiVersionHeaderDisplayed() {
		return isElementDisplayed(sbiVersionHeader);
	}

	public boolean isSbiCreationDateHeaderDisplayed() {
		return isElementDisplayed(sbiCreationDateHeader);
	}

	public boolean isSbiExpirationDateHeaderDisplayed() {
		return isElementDisplayed(sbiExpirationDateHeader);
	}

	public boolean isSbiExpiryStatusHeaderDisplayed() {
		return isElementDisplayed(sbiExpiryStatusHeader);
	}

	public boolean isCreationDateHeaderDisplayed() {
		return isElementDisplayed(creationDateHeader);
	}

	public boolean isStatusHeaderDisplayed() {
		return isElementDisplayed(statusHeader);
	}

	public boolean isLinkedDevicesHeaderDisplayed() {
		return isElementDisplayed(linkedDevicesHeader);
	}

	public boolean isActionHeaderDisplayed() {
		return isElementDisplayed(actionHeader);
	}

	public boolean isSbiCreationDateSameAsBrowserDateFormat() {

		WebElement dateCell = driver.findElement(By.xpath("//tr[@id='sbi_list_item1']/td[5]"));
		String browserTime = dateCell.getText().trim();

		DateTimeFormatter dateFormatter = PmpTestUtil.nonZeroPadderDateFormatter;
		try {
			LocalDate.parse(browserTime, dateFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}

	}

	public boolean isSbiExpirationDateSameAsBrowserDateFormat() {

		WebElement expiryDateCell = driver.findElement(By.xpath("//tr[@id='sbi_list_item1']/td[6]"));
		String browserTime = expiryDateCell.getText().trim();

		DateTimeFormatter dateFormatter = PmpTestUtil.nonZeroPadderDateFormatter;
		try {
			LocalDate.parse(browserTime, dateFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}

	}

	public void clickOnPendingForApprovalSbiItem() {
		clickOnElement(sbiListItem1);
	}

	public boolean isSbiDetailsPageDisplayed() {
		return isElementDisplayed(sbiDetailsPage);
	}

	public void clickOnSbiDetailsBackButton() {
		clickOnElement(sbiDetailsBackButton);
	}

	public void clickOnRejectedSbiItem() {
		clickOnElement(sbiListItem1);
	}

	public void clickOnDeactivatedSbiItem() {
		clickOnElement(sbiListItem1);
	}

	public boolean isViewOptionEnabled() {
		return isElementEnabled(sbiViewButton);
	}

	public boolean isLinkedDevicesDisplayed() {
		return isElementDisplayed(linkedDevices);
	}

	public boolean isApproveRejectOptionEnabled() {
		if (isElementDisplayed(approveRejectOptionGreyedOut)) {
			return false;
		}
		return isElementDisplayed(approveRejectButton);
	}

	public boolean isPartnerIdAscIconDisplayed() {
		return isElementDisplayed(partnerIdAscIcon);
	}

	public boolean isPartnerIdDescIconDisplayed() {
		return isElementDisplayed(partnerIdDescIcon);
	}

	public boolean isOrgNameAscIconDisplayed() {
		return isElementDisplayed(orgNameAscIcon);
	}

	public boolean isOrgNameDescIconDisplayed() {
		return isElementDisplayed(orgNameDescIcon);
	}

	public boolean isSbiIdAscIconDisplayed() {
		return isElementDisplayed(sbiIdAscIcon);
	}

	public boolean isSbiIdDescIconDisplayed() {
		return isElementDisplayed(sbiIdDescIcon);
	}

	public boolean isSbiCreatedDateTimeAscIconDisplayed() {
		return isElementDisplayed(sbiCreatedDateTimeAscIcon);
	}

	public boolean isSbiCreatedDateTimeDescIconDisplayed() {
		return isElementDisplayed(sbiCreatedDateTimeDescIcon);
	}

	public boolean isSbiExpiryDateTimeAscIconDisplayed() {
		return isElementDisplayed(sbiExpiryDateTimeAscIcon);
	}

	public boolean isSbiExpiryDateTimeDescIconDisplayed() {
		return isElementDisplayed(sbiExpiryDateTimeDescIcon);
	}

	public boolean isSbiExpiryStatusAscIconDisplayed() {
		return isElementDisplayed(sbiExpiryStatusAscIcon);
	}

	public boolean isSbiExpiryStatusDescIconDisplayed() {
		return isElementDisplayed(sbiExpiryStatusDescIcon);
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

	public boolean isCountOfAssociatedDevicesAscIconDisplayed() {
		return isElementDisplayed(countOfAssociatedDevicesAscIcon);
	}

	public boolean isCountOfAssociatedDevicesDescIconDisplayed() {
		return isElementDisplayed(countOfAssociatedDevicesDescIcon);
	}

	public boolean isMosipFooterTextDisplayed() {
		return isElementDisplayed(mosipFooterText);
	}

	public void clickOnSbiViewButton() {
		clickOnElement(sbiViewButton);
	}

	public boolean isPendingForApprovalDisplayed() {
		WebElement statusElement = driver.findElement(By.xpath("//div[text()='Pending For Approval']"));
		return isElementDisplayed(statusElement);
	}

	public boolean isLinkedDevicesListDisplayed() {
		return isElementDisplayed(subTitleOfLinkedDevices);
	}

	public void clickOnBackIconOfLinkedDevices() {
		clickOnElement(backIconOfLinkedDevices);
	}

	public boolean isLinkedDevicePresentForStatus(String statusText, String count) {
		WebElement linkedDeviceElement = driver
				.findElement(By.xpath("//tr[.//div[text()='" + statusText + "']]//button//p[text()='" + count + "']"));
		return isElementDisplayed(linkedDeviceElement);
	}

	public void clickOnLinkedDevicesInSbiList(String statusText, String count) {
		WebElement linkedDeviceElement = driver
				.findElement(By.xpath("//tr[.//div[text()='" + statusText + "']]//button//p[text()='" + count + "']"));
		clickOnElement(linkedDeviceElement);
	}

}
