package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;

/**
 * Partner Admin view of a single partner policy request
 * (ViewPolicyRequestDetails.js, reached from Partner Policy Linking).
 *
 * The page reads the selected request out of sessionStorage rather than the
 * URL, so a missing or unreadable entry is how a "non existing" or "invalid"
 * request is reached; the page is expected to bounce back to the listing.
 *
 * For Credential and Online Verification Partners the page also renders the
 * biometric extractor mapping table and the credential type. Both are fetched
 * after the page renders, so readers wait for them to settle first.
 */
public class ViewPolicyRequestPage extends BasePage {

	private static final String VIEW_POLICY_REQUEST_PATH = "/partnermanagement/admin/view-policy-request";
	private static final String POLICY_REQUESTS_LIST_PATH = "/partnermanagement/admin/policy-requests-list";
	private static final String PORTAL_ROOT_SEGMENT = "/partnermanagement";
	private static final String SELECTED_REQUEST_SESSION_KEY = "selectedPartnerPolicyRequest";
	/** The credential type block renders t('commons.loading', 'Loading...'); no bundle defines the key. */
	private static final String CREDENTIAL_TYPE_LOADING_TEXT = "Loading...";
	private static final Duration ABSENCE_CHECK_TIMEOUT = Duration.ofSeconds(3);

	/**
	 * Filled in from the locale bundle of the login language by
	 * {@link #init(String)}, which TestRunner calls once per language.
	 */
	public static String VIEW_POLICY_REQUEST_TITLE;
	public static String ONLINE_VERIFICATION_PARTNER_TYPE;
	public static String BIO_EXTRACTOR_PROVIDER_MAPPING_SECTION;
	public static String CREDENTIAL_TYPE_SECTION;
	public static String NO_BIO_EXTRACTORS_MAPPED;
	public static String NO_ACCESS_TITLE;

	public static void init(String loginLanguage) {
		copyLocaleFields(ViewPolicyRequestPage.class, loginLanguage);
	}

	@FindBy(id = "view_partner_policy_sub_title_id")
	private WebElement partnerIdHeader;

	@FindBy(id = "view_partner_policy_request_status")
	private WebElement status;

	@FindBy(id = "view_partner_policy_request_created_on")
	private WebElement createdOnDate;

	@FindBy(id = "view_partner_policy_request_created_date_time")
	private WebElement createdOnTime;

	@FindBy(id = "view_partner_policy_request_partner_type_context")
	private WebElement partnerType;

	@FindBy(id = "view_partner_policy_request_organisation_context")
	private WebElement organisation;

	@FindBy(id = "view_partner_policy_request_policy_id_context")
	private WebElement policyId;

	@FindBy(id = "view_partner_policy_request_policy_name_context")
	private WebElement policyName;

	@FindBy(id = "view_partner_policy_request_policy_group_context")
	private WebElement policyGroup;

	@FindBy(id = "view_partner_policy_request_partner_status_context")
	private WebElement partnerStatus;

	@FindBy(id = "view_partner_policy_request_comments_label")
	private WebElement commentsLabel;

	@FindBy(id = "view_partner_policy_request_admin_comments_label")
	private WebElement adminCommentLabel;

	@FindBy(id = "partner_policy_request_status")
	private WebElement adminCommentStatus;

	@FindBy(id = "partner_policy_request_partner_comment_label")
	private WebElement partnerCommentLabel;

	@FindBy(id = "partner_policy_request_partner_comment")
	private WebElement partnerComment;

	@FindBy(id = "partner_policy_request_created_on")
	private WebElement partnerCommentCreatedOn;

	@FindBy(id = "view_partner_policy_request_back_btn")
	private WebElement backButton;

	public ViewPolicyRequestPage(WebDriver driver) {
		super(driver);
	}

	public boolean isViewPolicyRequestPageDisplayed() {
		return isTextPresent(By.id("page_title"), VIEW_POLICY_REQUEST_TITLE);
	}

	/** Quick absence check - the page must not render at all for these callers. */
	public boolean isViewPolicyRequestPageAbsent() {
		return !isElementDisplayedQuick(By.id("view_partner_policy_request_back_btn"), ABSENCE_CHECK_TIMEOUT);
	}

	public String getPartnerIdHeaderText() {
		return getTextFromLocator(partnerIdHeader);
	}

	public String getStatusText() {
		return getTextFromLocator(status).trim();
	}

	/** The status badge colour comes from bgOfStatus(), so the class carries the colour coding. */
	public String getStatusColourClass() {
		return getTextFromAttribute(status, "class");
	}

	public boolean isCreatedOnDisplayed() {
		return isElementDisplayed(createdOnDate) && isElementDisplayed(createdOnTime)
				&& !getTextFromLocator(createdOnTime).trim().isEmpty();
	}

	public String getPartnerType() {
		return getTextFromLocator(partnerType).trim();
	}

	public String getOrganisation() {
		return getTextFromLocator(organisation).trim();
	}

	public String getPolicyId() {
		return getTextFromLocator(policyId).trim();
	}

	public String getPolicyName() {
		return getTextFromLocator(policyName).trim();
	}

	public String getPolicyGroup() {
		return getTextFromLocator(policyGroup).trim();
	}

	public String getPartnerStatus() {
		return getTextFromLocator(partnerStatus).trim();
	}

	// ------------------------------------------------------------------
	// Biometric extractor mapping and credential type
	// ------------------------------------------------------------------

	private By biometricMappingRows() {
		return By.xpath("//p[normalize-space()='" + BIO_EXTRACTOR_PROVIDER_MAPPING_SECTION
				+ "']/following-sibling::div//tbody/tr");
	}

	private By credentialTypeValue() {
		return By.xpath("//p[normalize-space()='" + CREDENTIAL_TYPE_SECTION + "']/following-sibling::p[1]");
	}

	/** Waits for both mapping fetches to return: no spinner in the table, no loading text on the credential type. */
	public void waitForMappingDetailsLoaded() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getTimeout())).until(d -> {
				if (!d.findElements(By.xpath("//tbody//*[@id='loading_text']")).isEmpty()) {
					return false;
				}
				List<WebElement> value = d.findElements(credentialTypeValue());
				return !value.isEmpty() && !CREDENTIAL_TYPE_LOADING_TEXT.equals(value.get(0).getText().trim());
			});
		} catch (TimeoutException e) {
			logger.warn("Mapping details did not finish loading on the View Policy Request page");
			takeScreenshot();
		}
	}

	public boolean isBiometricMappingSectionDisplayed() {
		return isDisplayed(By.xpath("//p[normalize-space()='" + BIO_EXTRACTOR_PROVIDER_MAPPING_SECTION + "']"));
	}

	public boolean isCredentialTypeSectionDisplayed() {
		return isDisplayed(By.xpath("//p[normalize-space()='" + CREDENTIAL_TYPE_SECTION + "']"));
	}

	/**
	 * Mapped rows only, one list of cell texts per row. The empty-state and error
	 * messages are a single cell spanning the table, so they are left out.
	 */
	public List<List<String>> getBiometricMappingRows() {
		waitForMappingDetailsLoaded();
		List<List<String>> rows = new ArrayList<>();
		for (WebElement row : driver.findElements(biometricMappingRows())) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			if (cells.size() < 2) {
				continue;
			}
			List<String> texts = new ArrayList<>();
			for (WebElement cell : cells) {
				texts.add(cell.getText().trim());
			}
			rows.add(texts);
		}
		return rows;
	}

	public boolean isNoBiometricMappingMessageDisplayed() {
		waitForMappingDetailsLoaded();
		return isDisplayed(By.xpath("//tbody//td[normalize-space()='" + NO_BIO_EXTRACTORS_MAPPED + "']"));
	}

	/** Returns the rendered credential type, or "-" when nothing is mapped. */
	public String getCredentialType() {
		waitForMappingDetailsLoaded();
		return getTextFromLocator(credentialTypeValue()).trim();
	}

	// ------------------------------------------------------------------
	// Comments
	// ------------------------------------------------------------------

	public boolean isCommentsSectionDisplayed() {
		return isElementDisplayed(commentsLabel);
	}

	public boolean isAdminCommentCardDisplayed() {
		return isElementDisplayed(adminCommentLabel) && isElementDisplayed(adminCommentStatus);
	}

	public String getAdminCommentStatus() {
		return getTextFromLocator(adminCommentStatus).trim();
	}

	public boolean isPartnerCommentCardDisplayed() {
		return isElementDisplayed(partnerCommentLabel) && isElementDisplayed(partnerCommentCreatedOn);
	}

	public String getPartnerComment() {
		return getTextFromLocator(partnerComment).trim();
	}

	/** The admin decision card is rendered above the partner's request comment - latest first. */
	public boolean isAdminCommentAbovePartnerComment() {
		return isElementAboveOther(adminCommentLabel, partnerCommentLabel);
	}

	// ------------------------------------------------------------------
	// Controls
	// ------------------------------------------------------------------

	/** Approve / Reject is only offered while the request is still pending. */
	public boolean isApproveRejectButtonPresent() {
		return !driver.findElements(By.id("view_approve_reject_btn")).isEmpty();
	}

	/** Counts anything a user could type into or pick from inside the details card. */
	public int getEditableFieldCount() {
		return driver.findElements(By.xpath("//button[@id='view_partner_policy_request_back_btn']"
				+ "/ancestor::div[contains(@class,'bg-snow-white')][1]"
				+ "//*[self::input or self::textarea or self::select or @contenteditable='true']")).size();
	}

	public boolean isBackButtonDisplayed() {
		return isElementDisplayed(backButton);
	}

	public void clickOnBackButton() {
		clickOnElement(backButton);
	}

	// ------------------------------------------------------------------
	// Direct navigation
	// ------------------------------------------------------------------

	/** Full URL of a portal route, derived from wherever the portal is currently open. */
	public String getPortalUrl(String path) {
		String current = driver.getCurrentUrl();
		int root = current.indexOf(PORTAL_ROOT_SEGMENT);
		String base = root >= 0 ? current.substring(0, root) : current;
		while (base.endsWith("/")) {
			base = base.substring(0, base.length() - 1);
		}
		return base + path;
	}

	public String getViewPolicyRequestUrl() {
		return getPortalUrl(VIEW_POLICY_REQUEST_PATH);
	}

	public String getPolicyRequestsListUrl() {
		return getPortalUrl(POLICY_REQUESTS_LIST_PATH);
	}

	public void openUrl(String url) {
		driver.get(url);
	}

	/**
	 * Opens the page with the given raw sessionStorage value for the selected
	 * request - null removes the entry, anything else is written as is, so a
	 * malformed value can be planted.
	 */
	public void openWithSelectedRequest(String rawSessionValue) {
		String url = getViewPolicyRequestUrl();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if (rawSessionValue == null) {
			js.executeScript("window.sessionStorage.removeItem(arguments[0]);", SELECTED_REQUEST_SESSION_KEY);
		} else {
			js.executeScript("window.sessionStorage.setItem(arguments[0], arguments[1]);",
					SELECTED_REQUEST_SESSION_KEY, rawSessionValue);
		}
		driver.get(url);
	}

	/** The route guard sends a user without PARTNER_ADMIN to the runtime error page. */
	public boolean isNoAccessErrorDisplayed() {
		return isTextPresent(By.id("run_time_error_title"), NO_ACCESS_TITLE);
	}
}
