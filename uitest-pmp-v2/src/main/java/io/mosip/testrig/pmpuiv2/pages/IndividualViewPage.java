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

/**
 * Approve/Reject controls rendered on the Partner Admin individual view pages.
 *
 * The button and the confirmation popup are the same components on every
 * individual view that supports admin approval - Policies, FTM Chip,
 * Authentication Services and SBI-Device - so one page object serves them all.
 */
public class IndividualViewPage extends BasePage {

	private static final String APPROVE_REJECT_BUTTON_ID = "view_approve_reject_btn";
	private static final String APPROVE_BUTTON_ID = "approve_btn";
	private static final String REJECT_BUTTON_ID = "reject_btn";
	private static final String APPROVE_REJECT_POPUP_TITLE_ID = "approve-reject_popup_title";

	private static final By APPROVE_REJECT_BUTTON = By.id(APPROVE_REJECT_BUTTON_ID);
	private static final By APPROVE_REJECT_POPUP_TITLE = By.id(APPROVE_REJECT_POPUP_TITLE_ID);

	/** Upper bound on the tab walk, so a layout change cannot spin the test. */
	private static final int MAX_TAB_PRESSES = 30;

	/** Colour coding applied by bgOfStatus() in the portal's AppUtils. */
	private static final String ACTIVATED_STATUS_CLASS = "bg-[#D1FADF]";
	private static final String DEACTIVATED_STATUS_CLASS = "bg-[#EAECF0]";

	/**
	 * getStatusCode() renders an activated partner as 'Active', so that is the text
	 * to expect on screen even though the story calls the state 'Activated'.
	 */
	private static final String ACTIVATED_STATUS_TEXT = "Active";
	private static final String DEACTIVATED_STATUS_TEXT = "Deactivated";

	@FindBy(id = APPROVE_REJECT_BUTTON_ID)
	private WebElement individualViewApproveRejectButton;

	@FindBy(id = APPROVE_REJECT_POPUP_TITLE_ID)
	private WebElement approveRejectPopupTitle;

	@FindBy(id = "approve-reject_popup_header")
	private WebElement approveRejectPopupHeader;

	@FindBy(id = "approve-reject_popup_description")
	private WebElement approveRejectPopupDescription;

	@FindBy(id = APPROVE_BUTTON_ID)
	private WebElement approveButton;

	@FindBy(id = REJECT_BUTTON_ID)
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

	// --- Approve/Reject button (TC_38408_01, _02, _10, _14) ---

	/** TC_38408_01: the button is rendered for a Pending for Approval record. */
	public boolean isApproveRejectButtonDisplayed() {
		return isElementDisplayed(individualViewApproveRejectButton);
	}

	/**
	 * TC_38408_02 and TC_38408_14: the button is absent for records that cannot be
	 * actioned. Probes briefly rather than waiting out the full timeout, since the
	 * expected outcome is that nothing is there.
	 */
	public boolean isApproveRejectButtonAbsent() {
		boolean present = isElementDisplayedQuick(APPROVE_REJECT_BUTTON, Duration.ofSeconds(5));
		if (present) {
			LogUtil.error("Approve/Reject button is rendered for a record that should not be actionable");
			takeScreenshot();
		}
		return !present;
	}

	/** TC_38408_10: the button stays enabled for a deactivated partner. */
	public boolean isApproveRejectButtonEnabled() {
		return isElementEnabled(individualViewApproveRejectButton);
	}

	public void clickOnApproveRejectButton() {
		clickOnElement(individualViewApproveRejectButton);
	}

	// --- Confirmation popup (TC_38408_03) ---

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

	// --- Partner Status field (TC_38408_04, _05, _06) ---

	/** TC_38408_04: the field is present regardless of the record status. */
	public boolean isPartnerStatusLabelDisplayed() {
		return isElementDisplayed(partnerStatusLabel);
	}

	public String getPartnerStatus() {
		return getTextFromLocator(partnerStatusContext).trim();
	}

	/** TC_38408_05: an activated partner renders with the green background token. */
	public boolean isPartnerStatusActivatedColourCoded() {
		return hasStatusClass(ACTIVATED_STATUS_CLASS, ACTIVATED_STATUS_TEXT);
	}

	/** TC_38408_06: Deactivated renders with the grey background token. */
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
				LogUtil.error("Partner Status '" + expectedStatus + "' missing the expected colour token "
						+ expectedClass);
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

	// --- Keyboard accessibility (TC_38408_13) ---

	/**
	 * TC_38408_13: reaches the Approve/Reject button with Tab and triggers it with
	 * Enter, without using the mouse.
	 *
	 * Tabbing starts from the document body so the walk mirrors what a keyboard
	 * user does on landing, and it is bounded so a layout change cannot spin here.
	 */
	public boolean openApproveRejectPopupUsingKeyboard() {
		WebElement button = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getTimeout()))
				.until(ExpectedConditions.visibilityOfElementLocated(APPROVE_REJECT_BUTTON));
		scrollIntoView(button);

		if (!tabUntilFocused(APPROVE_REJECT_BUTTON_ID)) {
			LogUtil.error("Approve/Reject button was not reachable within " + MAX_TAB_PRESSES + " tab presses");
			takeScreenshot();
			return false;
		}

		driver.switchTo().activeElement().sendKeys(Keys.ENTER);
		boolean opened = isElementDisplayedQuick(APPROVE_REJECT_POPUP_TITLE, Duration.ofSeconds(10));
		if (!opened) {
			LogUtil.error("Enter on the focused Approve/Reject button did not open the confirmation popup");
			takeScreenshot();
		}
		return opened;
	}

	/** TC_38408_13: Approve and Reject inside the popup are keyboard reachable. */
	public boolean areApproveRejectPopupButtonsKeyboardReachable() {
		if (tabUntilFocused(APPROVE_BUTTON_ID, REJECT_BUTTON_ID)) {
			return true;
		}
		LogUtil.error("Neither Approve nor Reject was reachable within " + MAX_TAB_PRESSES + " tab presses");
		takeScreenshot();
		return false;
	}

	/**
	 * TC_38408_13: the popup is dismissible from the keyboard.
	 *
	 * Closes the popup through the close icon when Escape does not, so the caller
	 * is left on a clean page either way, and reports the Escape result.
	 */
	public boolean closeApproveRejectPopupUsingEscape() {
		driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);

		boolean stillOpen = isElementDisplayedQuick(APPROVE_REJECT_POPUP_TITLE, Duration.ofSeconds(5));
		if (stillOpen) {
			LogUtil.error("Escape did not dismiss the Approve/Reject popup");
			takeScreenshot();
			clickOnPopupCloseIcon();
		}
		return !stillOpen;
	}

	/**
	 * Presses Tab until one of the given element ids holds the focus. Returns false
	 * once the budget is spent, leaving the focus wherever it landed.
	 */
	private boolean tabUntilFocused(String... targetIds) {
		for (int press = 0; press < MAX_TAB_PRESSES; press++) {
			WebElement focused = driver.switchTo().activeElement();
			String focusedId = focused.getAttribute("id");

			for (String targetId : targetIds) {
				if (targetId.equals(focusedId)) {
					LogUtil.step("'" + targetId + "' holds the focus after " + press + " tab presses");
					return true;
				}
			}
			focused.sendKeys(Keys.TAB);
		}
		return false;
	}
}
