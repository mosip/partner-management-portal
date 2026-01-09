package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	private By approveButton = By.id("approve_btn");

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

	@FindBy(id = "sbi_list_deactivate_btn")
	private WebElement deactivateSbiButtonInAdmin;

	@FindBy(xpath = "//p[@id='sbi_list_deactivate_btn' and contains(@class, 'text-[#A5A5A5]')]")
	private WebElement deactivateSbiButtonWithGreyedOutInAdmin;

	@FindBy(id = "deactivate_popup_header")
	private WebElement deactivatePopupInAdmin;

	@FindBy(xpath = "//p[@id='deactivate_popup_header' and contains(text(), 'Do you want to deactivate SBI Version')]")
	private WebElement deactivateSbiPopupTitleInAdmin;

	@FindBy(xpath = "//p[@id='deactivate_popup_description' and contains(text(), 'On clicking Confirm')]")
	private WebElement deactivatePopupDescriptionInAdmin;

	@FindBy(id = "deactivate_popup_description_for_sbi")
	private WebElement deactivatePopupSbiConfInAdmin;

	@FindBy(id = "undefined_title")
	private WebElement listOfSbiText;

	@FindBy(xpath = "//tr[@id='sbi_list_item1']/td[1]")
	private WebElement partnerIdInFirstColumn;

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

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			WebElement addDeviceButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"//p[text()='" + sbiVersion + "']/../../..//button[contains(@id, 'sbi_list_add_Devices')]")));
			return isElementEnabled(addDeviceButton);
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isRejectedStatusDisplayed(String sbiVersion) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			WebElement rejected = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='" + sbiVersion
							+ "']/..//div[contains(@class, 'bg-[#FAD6D1] text-[#5E1515]') and text()='Rejected']")));
			return isElementDisplayed(rejected);
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean getDeviceDetails(String sbiVersion) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			WebElement deviceDetails = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//p[text()='" + sbiVersion + "']/..//div[@class='flex items-center w-fit px-2 mx-1']")));
			return isElementDisplayed(deviceDetails);
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isViewDeviceButtonEnabled(String sbiVersion) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			WebElement viewDeviceButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"//p[text()='" + sbiVersion + "']/../../..//button[contains(@id, 'sbi_list_view_Devices')]")));
			return isElementEnabled(viewDeviceButton);
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void clickOnViewDeviceButton(String sbiVersion) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		By viewDeviceButton = By.xpath("//p[normalize-space(text())='" + sbiVersion + "']"
				+ "/ancestor::div[contains(@class,'p-4')]" + "//button[starts-with(@id,'sbi_list_view_Devices')]");

		wait.until(d -> {
			try {
				WebElement btn = d.findElement(viewDeviceButton);
				btn.click();
				return true;
			} catch (NoSuchElementException | StaleElementReferenceException e) {
				return false;
			}
		});
	}

	public void clickOnThreeDotsOfSbiListAsAdmin(String sbiVersion) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		By threeDots = By
				.xpath("//tr[.//td[normalize-space()='" + sbiVersion + "']]//button[contains(@id,'sbi_list_action')]");

		WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(threeDots));

		try {
			button.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
		}
	}

	public void clickOnThreeDotsOfSbiList(String sbiVersion) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement threeDotSbiOptionsButton = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[text()='" + sbiVersion + "']/../../..//button[contains(@id, 'sbi_list_hamburger')]")));

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

	public void clickOnAddDeviceButtonForSbi(String sbiVersion) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'Loading')]")));
		By addDeviceBtn = By.xpath("//p[normalize-space(text())='" + sbiVersion + "']"
				+ "/ancestor::div[contains(@class,'p-4')]" + "//button[starts-with(@id,'sbi_list_add_Devices')]");
		wait.until(d -> {
			try {
				WebElement btn = d.findElement(addDeviceBtn);
				btn.click();
				return true;
			} catch (NoSuchElementException | StaleElementReferenceException e) {
				return false;
			}
		});
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

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			WebElement status = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//p[text()='" + sbiVersion + "']/..//div[text()='Deactivated']")));
			return isElementDisplayed(status);
		} catch (TimeoutException e) {
			return false;
		}
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

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		By rowLocator = By.xpath("//tr[.//td[contains(normalize-space(.),'" + sbiVersion + "')]]");

		WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(rowLocator));
		By statusLocator = By
				.xpath(".//*[contains(normalize-space(.),'Pending') and contains(normalize-space(.),'Approval')]");

		WebElement status = row.findElement(statusLocator);

		return status.isDisplayed();
	}

	public boolean isRejectedStatusDisplayedInAdminPage(String sbiVersion) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		By rowLocator = By.xpath("//tr[.//td[contains(normalize-space(.),'" + sbiVersion + "')]]");

		WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(rowLocator));
		By statusLocator = By.xpath(".//*[contains(normalize-space(.),'Reject')]");
		WebElement status = row.findElement(statusLocator);
		return status.isDisplayed();
	}

	public String getSbiListArrowDirection() {
		return getTextFromAttribute(listOfSbiArrowButton, GlobalConstants.CLASS);
	}

	public void clickOnSbiListArrow(String sbiVersion) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		WebElement sbiArrowButton = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[text()='" + sbiVersion + "']/../../..//button[contains(@id, 'sbi_list_arrow')]")));

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

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			WebElement dateCell = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@id='sbi_list_item1']/td[5]")));

			String browserTime = dateCell.getText().trim();

			DateTimeFormatter dateFormatter = PmpTestUtil.nonZeroPadderDateFormatter;
			LocalDate.parse(browserTime, dateFormatter);

			return true;
		} catch (TimeoutException | DateTimeParseException e) {
			return false;
		}
	}

	public boolean isSbiExpirationDateSameAsBrowserDateFormat() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		try {
			WebElement expiryDateCell = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@id='sbi_list_item1']/td[6]")));

			String browserTime = expiryDateCell.getText().trim();

			DateTimeFormatter dateFormatter = PmpTestUtil.nonZeroPadderDateFormatter;
			LocalDate.parse(browserTime, dateFormatter);

			return true;
		} catch (TimeoutException | DateTimeParseException e) {
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

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until((WebDriver d) -> {
			try {
				WebElement row = d.findElement(By.xpath("//tr[.//td[normalize-space()='Rejected']]"));
				((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView({block:'center'});", row);
				row.findElement(By.xpath(".//td[1]")).click();

				return true;
			} catch (StaleElementReferenceException | NoSuchElementException e) {
				return false;
			}
		});
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

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		try {
			WebElement statusElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Pending For Approval']")));
			return isElementDisplayed(statusElement);
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isLinkedDevicesListDisplayed() {
		return isElementDisplayed(subTitleOfLinkedDevices);
	}

	public void clickOnBackIconOfLinkedDevices() {
		clickOnElement(backIconOfLinkedDevices);
	}

	public boolean isLinkedDevicePresentForStatus(String statusText, String count) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			WebElement linkedDeviceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//tr[.//div[text()='" + statusText + "']]" + "//button//p[text()='" + count + "']")));
			return isElementDisplayed(linkedDeviceElement);
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void clickOnLinkedDevicesInSbiList(String statusText, String count) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		WebElement linkedDeviceElement = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//tr[.//div[text()='" + statusText + "']]" + "//button//p[text()='" + count + "']")));

		clickOnElement(linkedDeviceElement);
	}

	public void clickOnDeactivateSbiButtonAsAdmin() {
		clickOnElement(deactivateSbiButtonInAdmin);
	}

	public boolean isDeactivateOptionEnabledInAdmin() {
		if (isElementDisplayed(deactivateSbiButtonWithGreyedOutInAdmin)) {
			return false;
		}
		return isElementDisplayed(deactivateSbiButtonInAdmin);
	}

	public boolean isDeactivatePopupInAdminDisplayed() {
		return isElementDisplayed(deactivatePopupInAdmin);
	}

	public boolean isDeactivateSbiPopupTitleInAdminDisplayed() {
		return isElementDisplayed(deactivateSbiPopupTitleInAdmin);
	}

	public boolean isDeactivatePopupDescriptionInAdminDisplayed() {
		return isElementDisplayed(deactivatePopupDescriptionInAdmin);
	}

	public boolean isDeactivatePopupSbiConfMsgInAdminDisplayed() {
		return isElementDisplayed(deactivatePopupSbiConfInAdmin);
	}

	public boolean isDeactivateCancelDisplayed() {
		return isElementDisplayed(deactivateCancel);
	}

	public boolean isDeactivateSubmitButtonDisplayed() {
		return isElementDisplayed(deactivateSubmitButton);
	}

	public void selectSbiStatusFilterInAdmin(String status) {
		clickOnElement(statusFilter);
		WebElement statusOption = driver
				.findElement(By.xpath("//button[contains(@id, 'status_filter_option') and text()='" + status + "']"));
		clickOnElement(statusOption);
	}

	public String getListOfSbisTitle() {
		return getTextFromLocator(listOfSbiText);
	}

	public boolean isDeactivatedStatusDisplayedInAdminPage(String sbiVersion) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			WebElement status = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//td[text()='" + sbiVersion + "']/..//div[text()='Deactivated']")));
			return isElementDisplayed(status);
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isPartnerIdInFirstColumnDisplayed() {
		return isElementDisplayed(partnerIdInFirstColumn);
	}

}
