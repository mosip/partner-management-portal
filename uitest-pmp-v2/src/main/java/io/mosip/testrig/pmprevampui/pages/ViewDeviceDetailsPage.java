package io.mosip.testrig.pmpv2ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpv2ui.fw.util.PmpTestUtil;

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

	@FindBy(xpath = "//h1[text()='View Device Details']")
	private WebElement viewDeviceDetailsTitle;

	@FindBy(xpath = "//div[contains(@class, 'bg-[#FEF1C6] text-[#6D1C00]') and text()='Pending For Approval']")
	private WebElement pendingForApproval;

	@FindBy(xpath = "//div[contains(@class, 'bg-[#D1FADF] text-[#155E3E]') and text()='Approved']")
	private WebElement approved;

	@FindBy(xpath = "//div[contains(@class, 'bg-[#FAD6D1] text-[#5E1515]') and text()='Rejected']")
	private WebElement rejected;

	@FindBy(xpath = "//div[contains(@class, 'bg-[#EAECF0] text-[#525252]') and text()='Deactivated']")
	private WebElement deactivated;

	public ViewDeviceDetailsPage(WebDriver driver) {
		super(driver);
	}

	public boolean isDeviceDetailsPageTitleDisplayed() {
		return isElementDisplayed(viewDeviceDetailsTitle);
	}

	public String getBreadcrumbText() {
		return getTextFromLocator(homeButton) + getTextFromLocator(listOfSbiButton)
				+ getTextFromLocator(listOfDevicesButton);
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
}
