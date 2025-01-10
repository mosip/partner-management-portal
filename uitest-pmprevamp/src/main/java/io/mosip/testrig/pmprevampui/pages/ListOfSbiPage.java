package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmprevampui.fw.util.PmpTestUtil;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class ListOfSbiPage extends BasePage {

	@FindBy(id = "ftm_list_approve_reject_option")
	private WebElement approveRejectButton;

	@FindBy(id = "sbi_list_deactivate")
	private WebElement deactivateSbiButton;

	@FindBy(id = "deactivate_submit_btn")
	private WebElement deactivateSubmitButton;

	@FindBy(id = "sbi_list_arrow1")
	private WebElement listOfSbiArrowButton;

	@FindBy(xpath = "//p[contains(text(),'Note: 1. User')]")
	private WebElement listOfSbiMessage;

	@FindBy(xpath = "//p[text()='approved']")
	private WebElement deviceApprovedCount;

	@FindBy(xpath = "//button[text()='Approve']")
	private WebElement approveButton;

	@FindBy(xpath = "//button[text()='Reject']")
	private WebElement rejectButton;

	@FindBy(xpath = "//p[text()='pending for approval under this SBI']")
	private WebElement devicePendingForApprovalCount;

	@FindBy(xpath = "//p[text()='Do you want to deactivate SBI Version ']")
	private WebElement deactivateSbiPopupTitle;

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

	public ListOfSbiPage(WebDriver driver) {
		super(driver);
	}

	public String getListOfSbiMessage() {
		return getTextFromLocator(listOfSbiMessage);
	}

	public boolean isDeviceApprovedCountDisplayed() {
		return isElementDisplayed(deviceApprovedCount);
	}

	public boolean isDevicePendingForApprovalCountDisplayed() {
		return isElementDisplayed(devicePendingForApprovalCount);
	}

	public boolean isAddDeviceButtonEnabled(String sbiVersion) {
		WebElement addDeviceButton = driver.findElement(
				By.xpath("//p[text()='" + sbiVersion + "']/../../..//button[contains(@id, 'sbi_list_add_Devices')]"));
		return isElementEnabled(addDeviceButton);
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
		return isElementEnabled(deactivateSbiButton);
	}

	public void clickOnDeactivateSbi() {
		clickOnElement(deactivateSbiButton);
	}

	public boolean isDeactivateSbiPopupTitleDisplayed() {
		return isElementDisplayed(deactivateSbiPopupTitle);
	}

	public void clickOnDeactivateSubmit() {
		clickOnElement(deactivateSbiButton);
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
				.findElement(By.xpath("//p[text()='" + sbiVersion + "']/..//*[text()='Pending For Approval']"));
		return isElementDisplayed(status);
	}

	public String getSbiListArrowDirection() {
		return getTextFromAttribute(listOfSbiArrowButton, GlobalConstants.CLASS);
	}

	public void clickOnSbiListArrow(String sbiVersion) {
		WebElement sbiArrowButton = driver
				.findElement(By.xpath("//p[text()='" + sbiVersion + "']/../../..//img[@id='sbi_list_arrow1']"));
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
		WebElement status = driver
				.findElement(By.xpath(generateXpathWithDateAndTitle("Submitted On", PmpTestUtil.todayDate)));
		return isElementDisplayed(status);
	}

	public boolean isSbiCreationDateDisplayed() {
		WebElement status = driver
				.findElement(By.xpath(generateXpathWithDateAndTitle("SBI Creation Date", PmpTestUtil.todayDate)));
		return isElementDisplayed(status);
	}

	public boolean isSbiExpirationDateDisplayed() {
		WebElement status = driver.findElement(
				By.xpath(generateXpathWithDateAndTitle("SBI Expiration Date", PmpTestUtil.nextMonth24thDate)));
		return isElementDisplayed(status);
	}

	private String generateXpathWithDateAndTitle(String title, String date) {
		String xpath = "//p[text()='" + title + "']/..//p[text()='" + date + "']";
		return xpath;
	}

}
