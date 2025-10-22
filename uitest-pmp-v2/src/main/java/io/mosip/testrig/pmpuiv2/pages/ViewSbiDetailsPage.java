package io.mosip.testrig.pmpuiv2.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;

public class ViewSbiDetailsPage extends BasePage {

	@FindBy(xpath = "//h1[text()='View SBI Details']")
	private WebElement viewSbiDetailsTitle;

	@FindBy(id = "sub_title_btn")
	private WebElement listOfSbiButton;

	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;

	@FindBy(xpath = "//p[@class='text-lg text-dark-blue mb-2']")
	private WebElement sbiIdWithValue;

	@FindBy(id = "view_admin_sbi_partner_id_context")
	private WebElement partnerIdText;

	@FindBy(id = "view_admin_sbi_partner_type_context")
	private WebElement partnerTypeText;

	@FindBy(id = "view_admin_sbi_organisation_context")
	private WebElement organisationText;

	@FindBy(id = "view_admin_sbi_linked_devices_context")
	private WebElement linkedDeviceText;

	@FindBy(id = "view_admin_sbi_version_context")
	private WebElement sbiVersionText;

	@FindBy(id = "view_admin_sbi_details_back_btn")
	private WebElement sbiDetailsBackButton;

	@FindBy(xpath = "//div[text()='Pending For Approval']")
	private WebElement pendingForApproval;

	@FindBy(xpath = "//div[text()='Approved']")
	private WebElement approved;

	@FindBy(xpath = "//div[text()='Rejected']")
	private WebElement rejected;

	@FindBy(xpath = "//div[text()='Deactivated']")
	private WebElement deactivated;

	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;

	public ViewSbiDetailsPage(WebDriver driver) {
		super(driver);
	}

	public boolean isViewSbiDetailsTitleDisplayed() {
		return isElementDisplayed(viewSbiDetailsTitle);

	}

	public String getBreadcrumbText() {
		return getTextFromLocator(homeButton) + getTextFromLocator(listOfSbiButton);
	}

	public boolean isViewSbiDetailsSubTitleDisplayed() {
		return isElementDisplayed(sbiIdWithValue);

	}

	public boolean isCreatedDateDisplayed() {
		WebElement createdDate = driver
				.findElement(By.xpath("//div[text()='Created On " + PmpTestUtil.todayDateWithoutZeroPadder + "']"));
		return isElementDisplayed(createdDate);
	}

	public String getPartnerIdText() {
		return partnerIdText.getText();
	}

	public String getPartnerTypeText() {
		return partnerTypeText.getText();
	}

	public String getOrganisationText() {
		return organisationText.getText();
	}

	public String getLinkedDeviceText() {
		return linkedDeviceText.getText();
	}

	public String getSbiVersionText() {
		return sbiVersionText.getText();
	}

	public boolean isSbiCreationDateSameAsBrowserDateFormat() {

		WebElement createdDateCell = driver.findElement(By.id("view_admin_sbi_created_date_context"));
		String browserTime = createdDateCell.getText().trim();

		DateTimeFormatter dateFormatter = PmpTestUtil.nonZeroPadderDateFormatter;
		try {
			LocalDate.parse(browserTime, dateFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public boolean isSbiExpirationDateSameAsBrowserDateFormat() {

		WebElement expiryDateCell = driver.findElement(By.id("view_admin_sbi_expiry_date_context"));
		String browserTime = expiryDateCell.getText().trim();

		DateTimeFormatter dateFormatter = PmpTestUtil.nonZeroPadderDateFormatter;
		try {
			LocalDate.parse(browserTime, dateFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}

	}

	public void clickOnSbiDetailsBackButton() {
		clickOnElement(sbiDetailsBackButton);
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

	public void clickOnLinkedDevices() {
		clickOnElement(linkedDeviceText);
	}

	public void clickOnTitleBackIcon() {
		clickOnElement(titleBackIcon);
	}

}
