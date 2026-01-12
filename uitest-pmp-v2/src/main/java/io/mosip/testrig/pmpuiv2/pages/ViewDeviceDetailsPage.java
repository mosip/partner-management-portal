package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;

public class ViewDeviceDetailsPage extends BasePage {

	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;

	@FindBy(id = "sub_title_btn")
	private WebElement listOfSbiButton;

	@FindBy(id = "sub_title_two_btn")
	private WebElement listOfDevicesButton;

	@FindBy(id = "device_details_partner_id_context")
	private WebElement partnerIdContext;

	@FindBy(id = "device_details_partner_type_context")
	private WebElement partnerTypeContext;

	@FindBy(id = "device_details_device_type_context")
	private WebElement deviceTypeContext;

	@FindBy(id = "device_details_device_sub_type_context")
	private WebElement deviceSubTypeContext;

	@FindBy(id = "device_details_make_context")
	private WebElement makeContext;

	@FindBy(id = "device_details_model_context")
	private WebElement modelContext;

	@FindBy(id = "device_details_partner_id_label")
	private WebElement partnerIdLabel;

	@FindBy(id = "device_details_partner_type_label")
	private WebElement partnerTypeLabel;

	@FindBy(id = "device_details_device_type_label")
	private WebElement deviceTypeLabel;

	@FindBy(id = "device_details_device_sub_type_label")
	private WebElement deviceSubTypeLabel;

	@FindBy(id = "device_details_make_label")
	private WebElement makeLabel;

	@FindBy(id = "device_details_model_label")
	private WebElement modelLabel;

	@FindBy(id = "device_details__sbi_version_label")
	private WebElement sbiVersionLabel;

	@FindBy(id = "device_details_sbi_version_context")
	private WebElement sbiVersionContext;

	@FindBy(id = "view_device_details_back_btn")
	private WebElement backButton;

	@FindBy(id = "page_title")
	private WebElement viewDeviceDetailsTitle;

	@FindBy(xpath = "//div[contains(@class, 'bg-[#FEF1C6] text-[#6D1C00]') and text()='Pending For Approval']")
	private WebElement pendingForApproval;

	@FindBy(xpath = "//div[contains(@class, 'bg-[#D1FADF] text-[#155E3E]') and text()='Approved']")
	private WebElement approved;

	@FindBy(xpath = "//div[contains(@class, 'bg-[#FAD6D1] text-[#5E1515]') and text()='Rejected']")
	private WebElement rejected;

	@FindBy(xpath = "//div[contains(@class, 'bg-[#EAECF0] text-[#525252]') and text()='Deactivated']")
	private WebElement deactivated;

	@FindBy(id = "sub_title_btn")
	private WebElement listOfDevicesInAdmin;

	@FindBy(id = "view_admin_device_details_partner_id_context")
	private WebElement partnerIdContextInAdmin;

	@FindBy(id = "view_admin_device_details_partner_type_context")
	private WebElement partnerTypeContextInAdmin;

	@FindBy(id = "view_admin_device_details_device_type_context")
	private WebElement deviceTypeContextInAdmin;

	@FindBy(id = "view_admin_device_details_device_sub_type_context")
	private WebElement deviceSubTypeContextInAdmin;

	@FindBy(id = "view_admin_device_details_make_context")
	private WebElement makeContextInAdmin;

	@FindBy(id = "view_admin_device_details_model_context")
	private WebElement modelContextInAdmin;

	@FindBy(id = "view_admin_device_details_org_name_context")
	private WebElement orgNameContextInAdmin;

	@FindBy(id = "view_admin_device_details_sbi_id_context")
	private WebElement sbiIdContextInAdmin;

	@FindBy(id = "view_admin_device_details_back_btn")
	private WebElement backButtonInAdminDeviceDetails;

	@FindBy(id = "view_admin_device_details_sub_title_id")
	private WebElement deviceIdContextInAdmin;

	public ViewDeviceDetailsPage(WebDriver driver) {
		super(driver);
	}

	public boolean isDeviceDetailsPageTitleDisplayed() {
		return isElementDisplayed(viewDeviceDetailsTitle);
	}

	public String getBreadcrumbTextOfViewDetails() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOf(homeButton),
				ExpectedConditions.visibilityOf(listOfSbiButton),
				ExpectedConditions.visibilityOf(listOfDevicesButton)));

		return homeButton.getText().trim() + listOfSbiButton.getText().trim() + listOfDevicesButton.getText().trim();
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

	public boolean isCreatedDateDisplayed() {
		WebElement createdDate = driver
				.findElement(By.xpath("//div[text()='Created On " + PmpTestUtil.todayDateWithoutZeroPadder + "']"));
		return isElementDisplayed(createdDate);
	}

	public String getPartnerIdContext() {
		return getTextFromLocator(partnerIdContext);
	}

	public String getPartnerTypeContext() {
		return getTextFromLocator(partnerTypeContext);
	}

	public String getDeviceTypeContext() {
		return getTextFromLocator(deviceTypeContext);
	}

	public String getDeviceSubTypeContext() {
		return getTextFromLocator(deviceSubTypeContext);
	}

	public String getMakeContext() {
		return getTextFromLocator(makeContext);
	}

	public String getModelContext() {
		return getTextFromLocator(modelContext);
	}

	public String getPartnerIdLabel() {
		return getTextFromLocator(partnerIdLabel);
	}

	public String getPartnerTypeLabel() {
		return getTextFromLocator(partnerTypeLabel);
	}

	public String getDeviceTypeLabel() {
		return getTextFromLocator(deviceTypeLabel);
	}

	public String getDeviceSubTypeLabel() {
		return getTextFromLocator(deviceSubTypeLabel);
	}

	public String getMakeLabel() {
		return getTextFromLocator(makeLabel);
	}

	public String getModelLabel() {
		return getTextFromLocator(modelLabel);
	}

	public String getSbiVersionContext() {
		return getTextFromLocator(sbiVersionContext);
	}

	public String getSbiVersionLabel() {
		return getTextFromLocator(sbiVersionLabel);
	}

	public void clickOnBack() {
		clickOnElement(backButton);
	}

	public void clickOnHome() {
		clickOnElement(homeButton);
	}

	public void clickOnListOfSbi() {
		clickOnElement(listOfSbiButton);
	}

	public void clickOnListOfDevices() {
		clickOnElement(listOfDevicesButton);
	}

	public boolean isBackButtonDisplayed() {
		return isElementDisplayed(backButton);
	}

	public String getPartnerIdContextInAdmin() {
		return getTextFromLocator(partnerIdContextInAdmin);
	}

	public String getPartnerTypeContextInAdmin() {
		return getTextFromLocator(partnerTypeContextInAdmin);
	}

	public String getDeviceTypeContextInAdmin() {
		return getTextFromLocator(deviceTypeContextInAdmin);
	}

	public String getDeviceSubTypeContextInAdmin() {
		return getTextFromLocator(deviceSubTypeContextInAdmin);
	}

	public String getMakeContextInAdmin() {
		return getTextFromLocator(makeContextInAdmin);
	}

	public String getModelContextInAdmin() {
		return getTextFromLocator(modelContextInAdmin);
	}

	public String getOrgNameContextInAdmin() {
		return getTextFromLocator(orgNameContextInAdmin);
	}

	public boolean isSbiIdContextInAdminDisplayed() {
		return isElementDisplayed(sbiIdContextInAdmin);
	}

	public boolean isBackButtonInAdminDeviceDetailsDisplayed() {
		return isElementDisplayed(backButtonInAdminDeviceDetails);
	}

	public boolean isDeviceIdContextInAdminDisplayed() {
		return isElementDisplayed(deviceIdContextInAdmin);
	}

	public boolean isCreationDateInAdminSameAsBrowserDateFormat() {
		By createdOnLocator = By.id("view_admin_device_details_created_on");
		WebElement dateCell = waitForElementToBeVisible(createdOnLocator);
		String browserTime = dateCell.getText().trim();
		String dateText = browserTime.replace("Created On", "").trim();
		DateTimeFormatter dateFormatter = PmpTestUtil.nonZeroPadderDateFormatter;

		try {
			LocalDate.parse(dateText, dateFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public void clickOnBackButtonInAdminDeviceDetails() {
		clickOnElement(backButtonInAdminDeviceDetails);
	}

	public void clickOnListOfDevicesBreadCumbInAdmin() {
		clickOnElement(listOfDevicesInAdmin);
	}

	public String getBreadcrumbTextOfDeviceDetailsInAdmin() {
		return getTextFromLocator(homeButton) + getTextFromLocator(listOfDevicesInAdmin);
	}
}
