package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page object for Partner Admin Biometric Extractor Provider Configuration —
 * create (MOSIP-44595), listing (MOSIP-44596), and view details (MOSIP-44597).
 */
public class BiometricConfigurationPage extends BasePage {

	private static final By DASHBOARD_CARD = By.id("dashboard_biometric_provider_configuration_card");
	private static final By CREATE_BTN_TOP = By.id("bio_extractor_config_create_btn_top");
	private static final By CREATE_BTN_CENTER = By.id("bio_extractor_config_create_btn_center");
	private static final By CONFIG_NAME_INPUT = By.id("config_name_input");
	private static final By PROVIDER_NAME_INPUT = By.id("provider_name_input");
	private static final By PROVIDER_VERSION_INPUT = By.id("provider_version_input");
	private static final By MODALITY_DROPDOWN = By.id("modality_dropdown_btn");
	private static final By CREDENTIAL_DATA_FORMAT_DROPDOWN = By.id("credential_data_format_dropdown_btn");
	private static final By ATTRIBUTE_NAME_DROPDOWN = By.id("attribute_name_dropdown_btn");
	private static final By CLEAR_FORM_BTN = By.id("create_bio_extractor_config_clear_form_btn");
	private static final By CANCEL_BTN = By.id("create_bio_extractor_config_cancel_btn");
	private static final By SUBMIT_BTN = By.id("create_bio_extractor_config_submit_btn");
	private static final By SUCCESS_HEADER = By.id("create_bio_extractor_config_confirmation_header");
	private static final By GO_BACK_BTN = By.id("confirmation_go_back_btn");
	private static final By HOME_BTN = By.id("confirmation_home_btn");
	private static final By PAGE_TITLE = By.id("page_title");

	private static final By FILTER_BTN = By.id("filter_btn");
	private static final By FILTER_RESET_BTN = By.id("filter_reset_btn");
	private static final By CONFIG_NAME_FILTER = By.id("bio_extractor_config_name_filter");
	private static final By PROVIDER_NAME_FILTER = By.id("bio_extractor_provider_name_filter");
	private static final By PROVIDER_VERSION_FILTER = By.id("bio_extractor_provider_version_filter");
	private static final By MODALITY_FILTER_DROPDOWN = By.id("bio_modality_filter_dropdown_btn");
	private static final By APPLY_FILTER_BTN = By.id("apply_bio_extractor_config_filter_btn");
	private static final By VIEW_BTN = By.id("bio_extractor_config_list_view_btn");
	private static final By DELETE_BTN = By.id("bio_extractor_config_list_delete_btn");
	private static final By DEACTIVATE_POPUP_HEADER = By.id("deactivate_popup_header");
	private static final By DEACTIVATE_CONFIRM_BTN = By.id("deactivate_submit_btn");
	private static final By DEACTIVATE_CANCEL_BTN = By.id("deactivate_cancel_btn");
	private static final By VIEW_CONFIG_NAME = By.id("view_bio_extractor_config_name_sub_title");
	private static final By VIEW_PROVIDER_NAME = By.id("view_bio_extractor_provider_name");
	private static final By VIEW_PROVIDER_VERSION = By.id("view_bio_extractor_provider_version");
	private static final By VIEW_MODALITY = By.id("view_bio_extractor_modality");
	private static final By VIEW_CREDENTIAL_DATA_FORMAT = By.id("view_bio_extractor_credential_data_format");
	private static final By VIEW_ATTRIBUTE_NAME = By.id("view_bio_extractor_attribute_name");
	private static final By VIEW_CREATED_ON = By.id("view_bio_extractor_config_created_on");
	private static final By VIEW_CREATED_TIME = By.id("view_bio_extractor_config_created_time");
	private static final By VIEW_BACK_BTN = By.id("view_bio_extractor_back_btn");
	private static final By VIEW_PAGE_TITLE = By.id("page_title");
	private static final By VIEW_STATUS = By.id("sub_title_status");
	private static final By VIEW_COMMENT = By.id("view_bio_extractor_comment");
	private static final By TITLE_BACK_ICON = By.id("title_back_icon");

	private static final By CONFIG_NAME_HEADER = By.id("configName_header");
	private static final By PROVIDER_NAME_HEADER = By.id("bioextractorProviderName_header");
	private static final By PROVIDER_VERSION_HEADER = By.id("bioextractorProviderVersion_header");
	private static final By MODALITY_HEADER = By.id("bioModality_header");
	private static final By CREATION_DATE_HEADER = By.id("createdDateTime_header");
	private static final By ACTION_HEADER = By.id("action_header");
	private static final By FIRST_LIST_ROW = By.id("bio_extractor_config_list_item_1");
	private static final By FIRST_ACTION_BTN = By.id("bio_extractor_config_list_action_btn_1");

	public BiometricConfigurationPage(WebDriver driver) {
		super(driver);
	}

	public boolean isBiometricConfigurationDashboardCardDisplayed() {
		return isElementDisplayedQuick(DASHBOARD_CARD, Duration.ofSeconds(10))
				|| isDisplayed(By.id("dashboard_biometric_provider_configuration_header"));
	}

	public void clickOnBiometricConfigurationDashboardCard() {
		WebElement card = driver.findElement(DASHBOARD_CARD);
		((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", card);
		isElementDisplayedQuick(CREATE_BTN_TOP, Duration.ofSeconds(20));
		isElementDisplayedQuick(FILTER_BTN, Duration.ofSeconds(5));
	}

	public boolean isBiometricConfigurationListPageDisplayed() {
		String url = driver.getCurrentUrl() == null ? "" : driver.getCurrentUrl();
		if (url.contains("biometric-provider-configuration-list")) {
			return isElementDisplayedQuick(CREATE_BTN_TOP, Duration.ofSeconds(10))
					|| isElementDisplayedQuick(CREATE_BTN_CENTER, Duration.ofSeconds(5))
					|| isElementDisplayedQuick(FILTER_BTN, Duration.ofSeconds(5))
					|| isElementDisplayedQuick(FIRST_LIST_ROW, Duration.ofSeconds(5));
		}
		return isElementDisplayedQuick(CREATE_BTN_TOP, Duration.ofSeconds(15))
				|| isElementDisplayedQuick(CREATE_BTN_CENTER, Duration.ofSeconds(10))
				|| isElementDisplayedQuick(FILTER_BTN, Duration.ofSeconds(5));
	}

	public void clickOnCreateBiometricConfigurationButton() {
		WebElement createBtn;
		if (isElementDisplayedQuick(CREATE_BTN_TOP, Duration.ofSeconds(8))) {
			createBtn = driver.findElement(CREATE_BTN_TOP);
		} else if (isElementDisplayedQuick(CREATE_BTN_CENTER, Duration.ofSeconds(8))) {
			createBtn = driver.findElement(CREATE_BTN_CENTER);
		} else {
			createBtn = driver.findElement(By.xpath(
					"//button[contains(normalize-space(),'Create Configuration') or contains(normalize-space(),'Create')]"));
		}
		((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", createBtn);
		isElementDisplayedQuick(CONFIG_NAME_INPUT, Duration.ofSeconds(20));
	}

	public boolean isCreateButtonTopDisplayed() {
		return isElementDisplayedQuick(CREATE_BTN_TOP, Duration.ofSeconds(5));
	}

	public boolean isCreateButtonCenterDisplayed() {
		return isElementDisplayedQuick(CREATE_BTN_CENTER, Duration.ofSeconds(5));
	}

	public boolean isCreateBiometricConfigurationFormDisplayed() {
		return isElementDisplayedQuick(CONFIG_NAME_INPUT, Duration.ofSeconds(10))
				&& isDisplayed(PROVIDER_NAME_INPUT) && isDisplayed(PROVIDER_VERSION_INPUT)
				&& isDisplayed(MODALITY_DROPDOWN) && isDisplayed(SUBMIT_BTN);
	}

	public void enterConfigurationName(String name) {
		reactType(CONFIG_NAME_INPUT, name);
	}

	public void enterProviderName(String name) {
		reactType(PROVIDER_NAME_INPUT, name);
	}

	public void enterProviderVersion(String version) {
		reactType(PROVIDER_VERSION_INPUT, version);
	}

	public void selectBiometricModality(String modality) {
		click(MODALITY_DROPDOWN);
		String value = modality == null ? "face" : modality.trim().toLowerCase();
		click(By.id("modality_option_" + value));
	}

	public void selectCredentialDataFormatRawData() {
		click(CREDENTIAL_DATA_FORMAT_DROPDOWN);
		click(By.id("credential_data_format_option_rawData"));
	}

	public void selectCredentialDataFormatTemplateData() {
		click(CREDENTIAL_DATA_FORMAT_DROPDOWN);
		click(By.id("credential_data_format_option_templateData"));
	}

	/**
	 * Selects the first available attribute option after modality + format are set.
	 * For rawData the UI usually auto-fills; this ensures selection if still empty.
	 */
	public void selectFirstAttributeNameIfNeeded() {
		if (!isElementDisplayedQuick(ATTRIBUTE_NAME_DROPDOWN, Duration.ofSeconds(3))) {
			return;
		}
		WebElement dropdown = driver.findElement(ATTRIBUTE_NAME_DROPDOWN);
		String current = dropdown.getText() == null ? "" : dropdown.getText().trim();
		if (!current.isEmpty() && !current.toLowerCase().contains("select")) {
			return;
		}
		click(ATTRIBUTE_NAME_DROPDOWN);
		By firstOption = By.xpath(
				"//div[@id='attribute_name_dropdown_options']//button[starts-with(@id,'attribute_name_option_')][1]");
		if (isElementDisplayedQuick(firstOption, Duration.ofSeconds(5))) {
			click(firstOption);
		}
	}

	public boolean isSubmitButtonEnabled() {
		WebElement submit = driver.findElement(SUBMIT_BTN);
		return submit.isEnabled();
	}

	public void clickOnClearFormButton() {
		click(CLEAR_FORM_BTN);
	}

	public void clickOnCancelButton() {
		click(CANCEL_BTN);
	}

	public void clickOnSubmitButton() {
		click(SUBMIT_BTN);
	}

	public boolean isBiometricConfigurationCreatedSuccessfully() {
		return isElementDisplayedQuick(SUCCESS_HEADER, Duration.ofSeconds(15))
				|| isDisplayed(By.xpath(
						"//*[contains(normalize-space(),'Biometric Extractor Provider Configuration is added successfully')]"));
	}

	public void clickOnSuccessGoBackButton() {
		click(GO_BACK_BTN);
	}

	public void clickOnSuccessHomeButton() {
		click(HOME_BTN);
	}

	public boolean isConfigurationNamePresentInList(String configName) {
		return isElementDisplayedQuick(
				By.xpath("//*[contains(normalize-space(),'" + configName + "')]"), Duration.ofSeconds(10));
	}

	public boolean isListingTableDisplayed() {
		return isElementDisplayedQuick(FIRST_LIST_ROW, Duration.ofSeconds(10))
				|| isElementDisplayedQuick(CONFIG_NAME_HEADER, Duration.ofSeconds(5));
	}

	public boolean areListingTableColumnsDisplayed() {
		return isElementDisplayedQuick(CONFIG_NAME_HEADER, Duration.ofSeconds(5))
				&& isElementDisplayedQuick(PROVIDER_NAME_HEADER, Duration.ofSeconds(3))
				&& isElementDisplayedQuick(PROVIDER_VERSION_HEADER, Duration.ofSeconds(3))
				&& isElementDisplayedQuick(MODALITY_HEADER, Duration.ofSeconds(3))
				&& isElementDisplayedQuick(CREATION_DATE_HEADER, Duration.ofSeconds(3));
	}

	public boolean isFilterButtonDisplayed() {
		return isElementDisplayedQuick(FILTER_BTN, Duration.ofSeconds(5));
	}

	public void clickOnFilterButton() {
		jsClick(FILTER_BTN);
	}

	public boolean isFilterPanelDisplayed() {
		return isElementDisplayedQuick(CONFIG_NAME_FILTER, Duration.ofSeconds(5))
				&& isDisplayed(PROVIDER_NAME_FILTER) && isDisplayed(PROVIDER_VERSION_FILTER)
				&& isDisplayed(MODALITY_FILTER_DROPDOWN) && isDisplayed(APPLY_FILTER_BTN);
	}

	public void enterConfigurationNameInFilter(String name) {
		reactType(CONFIG_NAME_FILTER, name);
	}

	public void enterProviderNameInFilter(String name) {
		reactType(PROVIDER_NAME_FILTER, name);
	}

	public void enterProviderVersionInFilter(String version) {
		reactType(PROVIDER_VERSION_FILTER, version);
	}

	public void selectModalityInFilter(String modality) {
		jsClick(MODALITY_FILTER_DROPDOWN);
		String value = modality == null ? "face" : modality.trim().toLowerCase();
		By optionByValue = By.xpath(
				"//button[contains(@id,'bio_modality_filter_option') and contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"
						+ value + "')]");
		if (isElementDisplayedQuick(optionByValue, Duration.ofSeconds(5))) {
			jsClick(optionByValue);
			return;
		}
		By firstRealOption = By.id("bio_modality_filter_option1");
		if (isElementDisplayedQuick(firstRealOption, Duration.ofSeconds(3))) {
			jsClick(firstRealOption);
		}
	}

	public void clickOnApplyFilterButton() {
		WebElement apply = driver.findElement(APPLY_FILTER_BTN);
		long end = System.currentTimeMillis() + 10000L;
		while (System.currentTimeMillis() < end && !apply.isEnabled()) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException ignored) {
			}
			apply = driver.findElement(APPLY_FILTER_BTN);
		}
		click(APPLY_FILTER_BTN);
	}

	public boolean isFilterResetButtonDisplayed() {
		return isElementDisplayedQuick(FILTER_RESET_BTN, Duration.ofSeconds(5));
	}

	public void clickOnFilterResetButton() {
		jsClick(FILTER_RESET_BTN);
	}

	public boolean isEmptyFilterResultDisplayed() {
		boolean noRows = !isElementDisplayedQuick(FIRST_LIST_ROW, Duration.ofSeconds(3));
		boolean emptyMsg = isDisplayed(By.xpath(
				"//*[contains(normalize-space(),'No Data Available') or contains(normalize-space(),'No Items Found') or contains(normalize-space(),'There are no') or contains(normalize-space(),'no matching')]"));
		return noRows || emptyMsg;
	}

	public String getFirstRowConfigurationName() {
		if (!isElementDisplayedQuick(FIRST_LIST_ROW, Duration.ofSeconds(10))) {
			return "";
		}
		try {
			WebElement row = driver.findElement(FIRST_LIST_ROW);
			WebElement firstCell = row.findElement(By.xpath("./td[1]"));
			String text = firstCell.getText();
			return text == null ? "" : text.trim();
		} catch (Exception e) {
			String rowText = driver.findElement(FIRST_LIST_ROW).getText();
			if (rowText == null || rowText.trim().isEmpty()) {
				return "";
			}
			return rowText.trim().split("\\s+")[0];
		}
	}

	public void clickOnSortAsc(String headerId) {
		By asc = By.id(headerId + "_asc_icon");
		if (isElementDisplayedQuick(asc, Duration.ofSeconds(3))) {
			try {
				jsClick(asc);
			} catch (Exception e) {
				((org.openqa.selenium.JavascriptExecutor) driver)
						.executeScript("document.getElementById(arguments[0])?.dispatchEvent(new MouseEvent('click',{bubbles:true}));",
								headerId + "_asc_icon");
			}
		}
	}

	public void clickOnSortDesc(String headerId) {
		By desc = By.id(headerId + "_desc_icon");
		if (isElementDisplayedQuick(desc, Duration.ofSeconds(3))) {
			try {
				jsClick(desc);
			} catch (Exception e) {
				((org.openqa.selenium.JavascriptExecutor) driver)
						.executeScript("document.getElementById(arguments[0])?.dispatchEvent(new MouseEvent('click',{bubbles:true}));",
								headerId + "_desc_icon");
			}
		}
	}

	private void jsClick(By locator) {
		WebElement element = driver.findElement(locator);
		((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", element);
	}

	public boolean isFirstRowActionMenuDisplayed() {
		return isElementDisplayedQuick(FIRST_ACTION_BTN, Duration.ofSeconds(8));
	}

	public void clickOnFirstRowActionMenu() {
		jsClick(FIRST_ACTION_BTN);
	}

	public String getCurrentUrl() {
		String url = driver.getCurrentUrl();
		return url == null ? "" : url;
	}

	public String getViewStatusText() {
		if (isElementDisplayedQuick(VIEW_STATUS, Duration.ofSeconds(3))) {
			String text = driver.findElement(VIEW_STATUS).getText();
			return text == null ? "" : text.trim();
		}
		return "";
	}

	public String getViewCommentText() {
		if (isElementDisplayedQuick(VIEW_COMMENT, Duration.ofSeconds(3))) {
			String text = driver.findElement(VIEW_COMMENT).getText();
			return text == null ? "" : text.trim();
		}
		return "";
	}

	public boolean isViewProviderNameDisplayed() {
		return isElementDisplayedQuick(VIEW_PROVIDER_NAME, Duration.ofSeconds(5));
	}

	public boolean isViewProviderVersionDisplayed() {
		return isElementDisplayedQuick(VIEW_PROVIDER_VERSION, Duration.ofSeconds(3));
	}

	public boolean isViewModalityDisplayed() {
		return isElementDisplayedQuick(VIEW_MODALITY, Duration.ofSeconds(3));
	}

	public boolean isViewCredentialDataFormatDisplayed() {
		return isElementDisplayedQuick(VIEW_CREDENTIAL_DATA_FORMAT, Duration.ofSeconds(3));
	}

	public boolean isViewAttributeNameDisplayed() {
		return isElementDisplayedQuick(VIEW_ATTRIBUTE_NAME, Duration.ofSeconds(3));
	}

	public void resetViewport() {
		driver.manage().window().maximize();
	}

	public boolean isViewActionDisplayed() {
		return isElementDisplayedQuick(VIEW_BTN, Duration.ofSeconds(5));
	}

	public boolean isDeleteActionDisplayed() {
		return isElementDisplayedQuick(DELETE_BTN, Duration.ofSeconds(5));
	}

	public void clickOnViewAction() {
		jsClick(VIEW_BTN);
	}

	public void clickOnDeleteAction() {
		jsClick(DELETE_BTN);
	}

	public boolean isViewConfigurationDetailsPageDisplayed() {
		return isElementDisplayedQuick(VIEW_CONFIG_NAME, Duration.ofSeconds(10))
				|| isElementDisplayedQuick(VIEW_PROVIDER_NAME, Duration.ofSeconds(5))
				|| isDisplayed(VIEW_BACK_BTN)
				|| isViewPageTitleDisplayed();
	}

	public boolean isViewPageTitleDisplayed() {
		if (!isElementDisplayedQuick(VIEW_PAGE_TITLE, Duration.ofSeconds(8))) {
			return isDisplayed(By.xpath(
					"//h1[contains(normalize-space(),'View Biometric Extractor')]"));
		}
		String title = driver.findElement(VIEW_PAGE_TITLE).getText();
		return title != null && title.toLowerCase().contains("view biometric extractor");
	}

	public boolean isViewConfigurationNameDisplayed() {
		return isElementDisplayedQuick(VIEW_CONFIG_NAME, Duration.ofSeconds(8));
	}

	public String getViewConfigurationName() {
		if (!isViewConfigurationNameDisplayed()) {
			return "";
		}
		String text = driver.findElement(VIEW_CONFIG_NAME).getText();
		return text == null ? "" : text.trim();
	}

	public boolean isViewCreatedOnDisplayed() {
		return isElementDisplayedQuick(VIEW_CREATED_ON, Duration.ofSeconds(8));
	}

	public boolean isViewCreatedTimeDisplayed() {
		return isElementDisplayedQuick(VIEW_CREATED_TIME, Duration.ofSeconds(5));
	}

	public boolean isViewStatusDisplayed() {
		return isElementDisplayedQuick(VIEW_STATUS, Duration.ofSeconds(3))
				|| isElementDisplayedQuick(
						By.xpath("//*[normalize-space()='Active' or normalize-space()='Deactivated' or normalize-space()='Inactive']"),
						Duration.ofSeconds(2));
	}

	public boolean areViewConfigurationDetailsDisplayed() {
		return isElementDisplayedQuick(VIEW_PROVIDER_NAME, Duration.ofSeconds(5))
				&& isDisplayed(VIEW_PROVIDER_VERSION) && isDisplayed(VIEW_MODALITY);
	}

	public boolean areAllViewConfigurationAttributesDisplayed() {
		return isViewConfigurationNameDisplayed()
				&& isElementDisplayedQuick(VIEW_PROVIDER_NAME, Duration.ofSeconds(5))
				&& isElementDisplayedQuick(VIEW_PROVIDER_VERSION, Duration.ofSeconds(3))
				&& isElementDisplayedQuick(VIEW_MODALITY, Duration.ofSeconds(3))
				&& isElementDisplayedQuick(VIEW_CREDENTIAL_DATA_FORMAT, Duration.ofSeconds(3))
				&& isElementDisplayedQuick(VIEW_ATTRIBUTE_NAME, Duration.ofSeconds(3));
	}

	public boolean isViewCommentDisplayed() {
		return isElementDisplayedQuick(VIEW_COMMENT, Duration.ofSeconds(3))
				|| isElementDisplayedQuick(
						By.xpath("//*[contains(normalize-space(),'Comment')]/following::*[1]"),
						Duration.ofSeconds(2));
	}

	/**
	 * Values on the view page are rendered as read-only &lt;p&gt; labels — there must be
	 * no editable input/textarea/select for the configuration attributes.
	 */
	public boolean isViewPageReadOnly() {
		if (!isViewConfigurationDetailsPageDisplayed()) {
			return false;
		}
		int editableFields = driver.findElements(By.cssSelector(
				"input:not([type='hidden']):not([disabled]), textarea:not([disabled]), select:not([disabled])"))
				.size();
		boolean valuesAsText = isElementDisplayedQuick(VIEW_PROVIDER_NAME, Duration.ofSeconds(3))
				&& "p".equalsIgnoreCase(driver.findElement(VIEW_PROVIDER_NAME).getTagName());
		return editableFields == 0 && valuesAsText;
	}

	public boolean isViewBackButtonDisplayed() {
		return isElementDisplayedQuick(VIEW_BACK_BTN, Duration.ofSeconds(8));
	}

	public boolean isViewBackButtonAlignedEnd() {
		if (!isViewBackButtonDisplayed()) {
			return false;
		}
		WebElement backBtn = driver.findElement(VIEW_BACK_BTN);
		WebElement parent = backBtn.findElement(By.xpath("./.."));
		String parentClass = parent.getAttribute("class");
		return parentClass != null && parentClass.contains("justify-end");
	}

	public void clickOnViewBackButton() {
		if (isElementDisplayedQuick(VIEW_BACK_BTN, Duration.ofSeconds(5))) {
			jsClick(VIEW_BACK_BTN);
		} else if (isElementDisplayedQuick(By.id("view_bio_extractor_config_go_back_btn"), Duration.ofSeconds(3))) {
			jsClick(By.id("view_bio_extractor_config_go_back_btn"));
		} else if (isElementDisplayedQuick(TITLE_BACK_ICON, Duration.ofSeconds(3))) {
			jsClick(TITLE_BACK_ICON);
		} else {
			jsClick(By.id("subtitle_back_icon"));
		}
	}

	public boolean isViewPageLayoutStructured() {
		return isViewPageTitleDisplayed()
				&& isViewConfigurationNameDisplayed()
				&& isViewCreatedOnDisplayed()
				&& areAllViewConfigurationAttributesDisplayed()
				&& isViewBackButtonDisplayed();
	}

	public boolean isViewPageUsableAtViewport(int width, int height) {
		driver.manage().window().setSize(new org.openqa.selenium.Dimension(width, height));
		try {
			Thread.sleep(500);
		} catch (InterruptedException ignored) {
			Thread.currentThread().interrupt();
		}
		return isViewPageTitleDisplayed()
				&& isViewConfigurationNameDisplayed()
				&& isViewBackButtonDisplayed()
				&& areViewConfigurationDetailsDisplayed();
	}

	public boolean isDeleteConfirmationPopupDisplayed() {
		return isElementDisplayedQuick(DEACTIVATE_POPUP_HEADER, Duration.ofSeconds(10));
	}

	public void clickOnDeleteConfirmButton() {
		jsClick(DEACTIVATE_CONFIRM_BTN);
	}

	public void clickOnDeleteCancelButton() {
		jsClick(DEACTIVATE_CANCEL_BTN);
	}

	public boolean isConfigurationNameAbsentFromList(String configName) {
		return !isElementDisplayedQuick(
				By.xpath("//tr[contains(@id,'bio_extractor_config_list_item_')]//*[contains(normalize-space(),'"
						+ configName + "')]"),
				Duration.ofSeconds(5));
	}

	private void reactType(By locator, String value) {
		WebElement input = driver.findElement(locator);
		input.click();
		((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
				"const input = arguments[0];"
						+ "const value = arguments[1];"
						+ "const setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
						+ "setter.call(input, value);"
						+ "input.dispatchEvent(new Event('input', { bubbles: true }));"
						+ "input.dispatchEvent(new Event('change', { bubbles: true }));",
				input, value);
	}
}
