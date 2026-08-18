package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;

public class IndividualViewPage extends BasePage {

	private static final int MAX_TAB_PRESSES = 30;

	private static final String ACTIVATED_STATUS_CLASS = "bg-[#D1FADF]";
	private static final String DEACTIVATED_STATUS_CLASS = "bg-[#EAECF0]";
	private static final String ACTIVATED_STATUS_TEXT = "Active";
	private static final String DEACTIVATED_STATUS_TEXT = "Deactivated";

	@FindBy(id = "view_approve_reject_btn")
	private WebElement individualViewApproveRejectButton;

	@FindBy(id = "approve-reject_popup_title")
	private WebElement approveRejectPopupTitle;

	@FindBy(id = "approve-reject_popup_header")
	private WebElement approveRejectPopupHeader;

	@FindBy(id = "approve-reject_popup_description")
	private WebElement approveRejectPopupDescription;

	@FindBy(id = "approve_btn")
	private WebElement approveButton;

	@FindBy(id = "reject_btn")
	private WebElement rejectButton;

	@FindBy(id = "approve_reject_popup_close_icon")
	private WebElement approveRejectPopupCloseIcon;

	@FindBy(id = "view_partner_policy_request_partner_status_label")
	private WebElement partnerStatusLabel;

	@FindBy(id = "view_partner_policy_request_partner_status_context")
	private WebElement partnerStatusContext;

	public IndividualViewPage(WebDriver driver) {
		super(driver);
	}

	public boolean isApproveRejectButtonDisplayed() {
		return isElementDisplayed(individualViewApproveRejectButton);
	}

	public boolean isApproveRejectButtonAbsent() {
		boolean present = isElementDisplayedQuick(By.id("view_approve_reject_btn"), Duration.ofSeconds(5));
		if (present) {
			LogUtil.error("Approve/Reject button is rendered for a record that should not be actionable");
			takeScreenshot();
		}
		return !present;
	}

	public boolean isApproveRejectButtonEnabled() {
		return isElementEnabled(individualViewApproveRejectButton);
	}

	public void clickOnApproveRejectButton() {
		clickOnElement(individualViewApproveRejectButton);
	}

	public boolean isApproveRejectPopupDisplayed() {
		return isElementDisplayed(approveRejectPopupTitle);
	}

	public boolean isApproveRejectPopupHeaderDisplayed() {
		return isElementDisplayed(approveRejectPopupHeader);
	}

	public boolean isApproveRejectPopupDescriptionDisplayed() {
		return isElementDisplayed(approveRejectPopupDescription);
	}

	public boolean isApproveButtonDisplayed() {
		return isElementDisplayed(approveButton);
	}

	public boolean isRejectButtonDisplayed() {
		return isElementDisplayed(rejectButton);
	}

	public void clickOnApproveButton() {
		clickOnElement(approveButton);
	}

	public void clickOnRejectButton() {
		clickOnElement(rejectButton);
	}

	public void clickOnPopupCloseIcon() {
		clickOnElement(approveRejectPopupCloseIcon);
	}

	public boolean isPartnerStatusLabelDisplayed() {
		return isElementDisplayed(partnerStatusLabel);
	}

	public String getPartnerStatus() {
		return getTextFromLocator(partnerStatusContext).trim();
	}

	public boolean isPartnerStatusActivatedColourCoded() {
		return hasStatusClass(ACTIVATED_STATUS_CLASS, ACTIVATED_STATUS_TEXT);
	}

	public boolean isPartnerStatusDeactivatedColourCoded() {
		return hasStatusClass(DEACTIVATED_STATUS_CLASS, DEACTIVATED_STATUS_TEXT);
	}

	private boolean hasStatusClass(String expectedClass, String expectedStatus) {
		try {
			waitForElementVisible(partnerStatusContext);
			String actualClass = partnerStatusContext.getAttribute("class");
			String actualStatus = partnerStatusContext.getText().trim();
			LogUtil.step("Partner Status '" + actualStatus + "' rendered with class: " + actualClass);

			if (!actualStatus.equalsIgnoreCase(expectedStatus)) {
				LogUtil.error("Expected Partner Status '" + expectedStatus + "' but found '" + actualStatus + "'");
				takeScreenshot();
				return false;
			}
			if (actualClass == null || !actualClass.contains(expectedClass)) {
				LogUtil.error(
						"Partner Status '" + expectedStatus + "' missing the expected colour token " + expectedClass);
				takeScreenshot();
				return false;
			}
			return true;

		} catch (Exception e) {
			LogUtil.error("Failed to read the Partner Status colour coding: " + e.getClass().getSimpleName());
			takeScreenshot();
			return false;
		}
	}

	public boolean openApproveRejectPopupUsingKeyboard() {
		new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getTimeout()))
				.until(ExpectedConditions.visibilityOf(individualViewApproveRejectButton));
		scrollIntoView(individualViewApproveRejectButton);

		if (!tabUntilFocused(individualViewApproveRejectButton)) {
			LogUtil.error("Approve/Reject button was not reachable within " + MAX_TAB_PRESSES + " tab presses");
			takeScreenshot();
			return false;
		}

		driver.switchTo().activeElement().sendKeys(Keys.ENTER);
		boolean opened = isElementDisplayedQuick(By.id("approve-reject_popup_title"), Duration.ofSeconds(10));
		if (!opened) {
			LogUtil.error("Enter on the focused Approve/Reject button did not open the confirmation popup");
			takeScreenshot();
		}
		return opened;
	}

	public boolean areApproveRejectPopupButtonsKeyboardReachable() {
		if (tabUntilFocused(approveButton, rejectButton)) {
			return true;
		}
		LogUtil.error("Neither Approve nor Reject was reachable within " + MAX_TAB_PRESSES + " tab presses");
		takeScreenshot();
		return false;
	}

	public boolean closeApproveRejectPopupUsingEscape() {
		driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);

		boolean stillOpen = isElementDisplayedQuick(By.id("approve-reject_popup_title"), Duration.ofSeconds(5));
		if (stillOpen) {
			LogUtil.error("Escape did not dismiss the Approve/Reject popup");
			takeScreenshot();
			clickOnPopupCloseIcon();
		}
		return !stillOpen;
	}

	private boolean tabUntilFocused(WebElement... targets) {
		String[] targetIds = new String[targets.length];
		for (int i = 0; i < targets.length; i++) {
			targetIds[i] = targets[i].getAttribute("id");
		}

		for (int press = 0; press < MAX_TAB_PRESSES; press++) {
			WebElement focused = driver.switchTo().activeElement();
			String focusedId = focused.getAttribute("id");

			for (String targetId : targetIds) {
				if (targetId != null && targetId.equals(focusedId)) {
					LogUtil.step("'" + targetId + "' holds the focus after " + press + " tab presses");
					return true;
				}
			}
			focused.sendKeys(Keys.TAB);
		}
		return false;
	}
}
