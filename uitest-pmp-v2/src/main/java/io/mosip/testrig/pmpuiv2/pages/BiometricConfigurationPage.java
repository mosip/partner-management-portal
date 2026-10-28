package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object for Partner Admin Biometric Extractor Provider Configuration —
 * create (MOSIP-44595), listing (MOSIP-44596), and view details (MOSIP-44597).
 * Structure mirrors {@link ManualAdjudicationPartnerPage} (@FindBy locators).
 */
public class BiometricConfigurationPage extends BasePage {

	private static final Logger logger = Logger.getLogger(BiometricConfigurationPage.class);

	@FindBy(id = "dashboard_biometric_provider_configuration_card")
	private WebElement dashboardCard;

	@FindBy(id = "bio_extractor_config_create_btn_top")
	private WebElement createBtnTop;

	@FindBy(id = "bio_extractor_config_create_btn_center")
	private WebElement createBtnCenter;

	@FindBy(id = "config_name_input")
	private WebElement configNameInput;

	@FindBy(id = "provider_name_input")
	private WebElement providerNameInput;

	@FindBy(id = "provider_version_input")
	private WebElement providerVersionInput;

	@FindBy(id = "modality_dropdown_btn")
	private WebElement modalityDropdown;

	@FindBy(id = "credential_data_format_dropdown_btn")
	private WebElement credentialDataFormatDropdown;

	@FindBy(id = "attribute_name_dropdown_btn")
	private WebElement attributeNameDropdown;

	@FindBy(id = "create_bio_extractor_config_clear_form_btn")
	private WebElement clearFormBtn;

	@FindBy(id = "create_bio_extractor_config_cancel_btn")
	private WebElement cancelBtn;

	@FindBy(id = "create_bio_extractor_config_submit_btn")
	private WebElement submitBtn;

	@FindBy(id = "create_bio_extractor_config_confirmation_header")
	private WebElement successHeader;

	@FindBy(id = "confirmation_go_back_btn")
	private WebElement goBackBtn;

	@FindBy(id = "confirmation_home_btn")
	private WebElement homeBtn;

	@FindBy(id = "page_title")
	private WebElement pageTitle;

	@FindBy(id = "filter_btn")
	private WebElement filterBtn;

	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetBtn;

	@FindBy(id = "bio_extractor_config_name_filter")
	private WebElement configNameFilter;

	@FindBy(id = "bio_extractor_provider_name_filter")
	private WebElement providerNameFilter;

	@FindBy(id = "bio_extractor_provider_version_filter")
	private WebElement providerVersionFilter;

	@FindBy(id = "bio_modality_filter_dropdown_btn")
	private WebElement modalityFilterDropdown;

	@FindBy(id = "apply_bio_extractor_config_filter_btn")
	private WebElement applyFilterBtn;

	@FindBy(id = "bio_extractor_config_list_view_btn")
	private WebElement viewBtn;

	@FindBy(id = "bio_extractor_config_list_delete_btn")
	private WebElement deleteBtn;

	@FindBy(id = "deactivate_popup_header")
	private WebElement deactivatePopupHeader;

	@FindBy(id = "deactivate_submit_btn")
	private WebElement deactivateConfirmBtn;

	@FindBy(id = "deactivate_cancel_btn")
	private WebElement deactivateCancelBtn;

	@FindBy(id = "view_bio_extractor_config_name_sub_title")
	private WebElement viewConfigName;

	@FindBy(id = "view_bio_extractor_provider_name")
	private WebElement viewProviderName;

	@FindBy(id = "view_bio_extractor_provider_version")
	private WebElement viewProviderVersion;

	@FindBy(id = "view_bio_extractor_modality")
	private WebElement viewModality;

	@FindBy(id = "view_bio_extractor_credential_data_format")
	private WebElement viewCredentialDataFormat;

	@FindBy(id = "view_bio_extractor_attribute_name")
	private WebElement viewAttributeName;

	@FindBy(id = "view_bio_extractor_config_created_on")
	private WebElement viewCreatedOn;

	@FindBy(id = "view_bio_extractor_config_created_time")
	private WebElement viewCreatedTime;

	@FindBy(id = "view_bio_extractor_back_btn")
	private WebElement viewBackBtn;

	@FindBy(id = "sub_title_status")
	private WebElement viewStatus;

	@FindBy(id = "view_bio_extractor_comment")
	private WebElement viewComment;

	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;

	@FindBy(id = "configName_header")
	private WebElement configNameHeader;

	@FindBy(id = "bioextractorProviderName_header")
	private WebElement providerNameHeader;

	@FindBy(id = "bioextractorProviderVersion_header")
	private WebElement providerVersionHeader;

	@FindBy(id = "bioModality_header")
	private WebElement modalityHeader;

	@FindBy(id = "createdDateTime_header")
	private WebElement creationDateHeader;

	@FindBy(id = "action_header")
	private WebElement actionHeader;

	@FindBy(id = "bio_extractor_config_list_item_1")
	private WebElement firstListRow;

	@FindBy(id = "bio_extractor_config_list_action_btn_1")
	private WebElement firstActionBtn;

	public BiometricConfigurationPage(WebDriver driver) {
		super(driver);
	}

	public boolean isBiometricConfigurationDashboardCardDisplayed() {
		return isElementDisplayedQuick(By.id("dashboard_biometric_provider_configuration_card"), Duration.ofSeconds(10))
				|| isDisplayed(By.id("dashboard_biometric_provider_configuration_header"));
	}

	public void clickOnBiometricConfigurationDashboardCard() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", dashboardCard);
		isElementDisplayedQuick(By.id("bio_extractor_config_create_btn_top"), Duration.ofSeconds(20));
		isElementDisplayedQuick(By.id("filter_btn"), Duration.ofSeconds(5));
	}

	public boolean isBiometricConfigurationListPageDisplayed() {
		String url = driver.getCurrentUrl() == null ? "" : driver.getCurrentUrl();
		if (url.contains("biometric-provider-configuration-list")) {
			return isElementDisplayed(createBtnTop) || isElementDisplayed(createBtnCenter)
					|| isElementDisplayed(filterBtn) || isElementDisplayed(firstListRow);
		}
		return isElementDisplayedQuick(By.id("bio_extractor_config_create_btn_top"), Duration.ofSeconds(15))
				|| isElementDisplayedQuick(By.id("bio_extractor_config_create_btn_center"), Duration.ofSeconds(10))
				|| isElementDisplayedQuick(By.id("filter_btn"), Duration.ofSeconds(5));
	}

	public void clickOnCreateBiometricConfigurationButton() {
		WebElement createBtn;
		if (isElementDisplayedQuick(By.id("bio_extractor_config_create_btn_top"), Duration.ofSeconds(8))) {
			createBtn = createBtnTop;
		} else if (isElementDisplayedQuick(By.id("bio_extractor_config_create_btn_center"), Duration.ofSeconds(8))) {
			createBtn = createBtnCenter;
		} else {
			createBtn = driver.findElement(By.xpath(
					"//button[contains(normalize-space(),'Create Configuration') or contains(normalize-space(),'Create')]"));
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", createBtn);
		isElementDisplayedQuick(By.id("config_name_input"), Duration.ofSeconds(20));
	}

	public boolean isCreateButtonTopDisplayed() {
		return isElementDisplayed(createBtnTop);
	}

	public boolean isCreateButtonCenterDisplayed() {
		return isElementDisplayed(createBtnCenter);
	}

	public boolean isCreateBiometricConfigurationFormDisplayed() {
		return isElementDisplayedQuick(By.id("config_name_input"), Duration.ofSeconds(10))
				&& isElementDisplayed(providerNameInput) && isElementDisplayed(providerVersionInput)
				&& isElementDisplayed(modalityDropdown) && isElementDisplayed(submitBtn);
	}

	public void enterConfigurationName(String name) {
		reactType(configNameInput, name);
	}

	public void enterProviderName(String name) {
		reactType(providerNameInput, name);
	}

	public void enterProviderVersion(String version) {
		reactType(providerVersionInput, version);
	}

	public void selectBiometricModality(String modality) {
		clickOnElement(modalityDropdown);
		String value = modality == null ? "face" : modality.trim().toLowerCase();
		click(By.id("modality_option_" + value));
	}

	public void selectCredentialDataFormatRawData() {
		clickOnElement(credentialDataFormatDropdown);
		click(By.id("credential_data_format_option_rawData"));
	}

	public void selectCredentialDataFormatTemplateData() {
		clickOnElement(credentialDataFormatDropdown);
		click(By.id("credential_data_format_option_templateData"));
	}

	/**
	 * Selects the first available attribute option after modality + format are set.
	 * For rawData the UI usually auto-fills; this ensures selection if still empty.
	 */
	public void selectFirstAttributeNameIfNeeded() {
		if (!isElementDisplayedQuick(By.id("attribute_name_dropdown_btn"), Duration.ofSeconds(3))) {
			return;
		}
		String current = attributeNameDropdown.getText() == null ? "" : attributeNameDropdown.getText().trim();
		if (!current.isEmpty() && !current.toLowerCase().contains("select")) {
			return;
		}
		clickOnElement(attributeNameDropdown);
		By firstOption = By.xpath(
				"//div[@id='attribute_name_dropdown_options']//button[starts-with(@id,'attribute_name_option_')][1]");
		if (isElementDisplayedQuick(firstOption, Duration.ofSeconds(5))) {
			click(firstOption);
		}
	}

	public boolean isSubmitButtonEnabled() {
		return isElementEnabled(submitBtn);
	}

	public void clickOnClearFormButton() {
		clickOnElement(clearFormBtn);
	}

	public void clickOnCancelButton() {
		clickOnElement(cancelBtn);
	}

	public void clickOnSubmitButton() {
		clickOnElement(submitBtn);
	}

	public boolean isBiometricConfigurationCreatedSuccessfully() {
		return isElementDisplayedQuick(By.id("create_bio_extractor_config_confirmation_header"), Duration.ofSeconds(15))
				|| isDisplayed(By.xpath(
						"//*[contains(normalize-space(),'Biometric Extractor Provider Configuration is added successfully')]"));
	}

	public void clickOnSuccessGoBackButton() {
		clickOnElement(goBackBtn);
	}

	public void clickOnSuccessHomeButton() {
		clickOnElement(homeBtn);
	}

	public boolean isConfigurationNamePresentInList(String configName) {
		return isElementDisplayedQuick(
				By.xpath("//*[contains(normalize-space(),'" + configName + "')]"), Duration.ofSeconds(10));
	}

	public boolean isListingTableDisplayed() {
		return isElementDisplayedQuick(By.id("bio_extractor_config_list_item_1"), Duration.ofSeconds(10))
				|| isElementDisplayed(configNameHeader);
	}

	public boolean areListingTableColumnsDisplayed() {
		return isElementDisplayed(configNameHeader) && isElementDisplayed(providerNameHeader)
				&& isElementDisplayed(providerVersionHeader) && isElementDisplayed(modalityHeader)
				&& isElementDisplayed(creationDateHeader);
	}

	public boolean isFilterButtonDisplayed() {
		return isElementDisplayed(filterBtn);
	}

	public void clickOnFilterButton() {
		jsClickElement(filterBtn);
	}

	public boolean isFilterPanelDisplayed() {
		return isElementDisplayedQuick(By.id("bio_extractor_config_name_filter"), Duration.ofSeconds(5))
				&& isElementDisplayed(providerNameFilter) && isElementDisplayed(providerVersionFilter)
				&& isElementDisplayed(modalityFilterDropdown) && isElementDisplayed(applyFilterBtn);
	}

	public void enterConfigurationNameInFilter(String name) {
		reactType(configNameFilter, name);
	}

	public void enterProviderNameInFilter(String name) {
		reactType(providerNameFilter, name);
	}

	public void enterProviderVersionInFilter(String version) {
		reactType(providerVersionFilter, version);
	}

	public void selectModalityInFilter(String modality) {
		jsClickElement(modalityFilterDropdown);
		String value = modality == null ? "face" : modality.trim().toLowerCase();
		By optionByValue = By.xpath(
				"//button[contains(@id,'bio_modality_filter_option') and contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"
						+ value + "')]");
		if (isElementDisplayedQuick(optionByValue, Duration.ofSeconds(5))) {
			click(optionByValue);
			return;
		}
		By firstRealOption = By.id("bio_modality_filter_option1");
		if (isElementDisplayedQuick(firstRealOption, Duration.ofSeconds(3))) {
			click(firstRealOption);
		}
	}

	public void clickOnApplyFilterButton() {
		long end = System.currentTimeMillis() + 10000L;
		while (System.currentTimeMillis() < end && !isElementEnabled(applyFilterBtn)) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException ignored) {
				Thread.currentThread().interrupt();
			}
		}
		clickOnElement(applyFilterBtn);
	}

	public boolean isFilterResetButtonDisplayed() {
		return isElementDisplayed(filterResetBtn);
	}

	public void clickOnFilterResetButton() {
		jsClickElement(filterResetBtn);
	}

	public boolean isEmptyFilterResultDisplayed() {
		boolean noRows = !isElementDisplayedQuick(By.id("bio_extractor_config_list_item_1"), Duration.ofSeconds(3));
		boolean emptyMsg = isDisplayed(By.xpath(
				"//*[contains(normalize-space(),'No Data Available') or contains(normalize-space(),'No Items Found') or contains(normalize-space(),'There are no') or contains(normalize-space(),'no matching')]"));
		return noRows || emptyMsg;
	}

	public String getFirstRowConfigurationName() {
		if (!isElementDisplayedQuick(By.id("bio_extractor_config_list_item_1"), Duration.ofSeconds(10))) {
			return "";
		}
		try {
			WebElement firstCell = firstListRow.findElement(By.xpath("./td[1]"));
			String text = firstCell.getText();
			return text == null ? "" : text.trim();
		} catch (Exception e) {
			logger.warn("Falling back to row text for first config name: " + e.getMessage());
			String rowText = firstListRow.getText();
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
				click(asc);
			} catch (Exception e) {
				((JavascriptExecutor) driver).executeScript(
						"document.getElementById(arguments[0])?.dispatchEvent(new MouseEvent('click',{bubbles:true}));",
						headerId + "_asc_icon");
			}
		}
	}

	public void clickOnSortDesc(String headerId) {
		By desc = By.id(headerId + "_desc_icon");
		if (isElementDisplayedQuick(desc, Duration.ofSeconds(3))) {
			try {
				click(desc);
			} catch (Exception e) {
				((JavascriptExecutor) driver).executeScript(
						"document.getElementById(arguments[0])?.dispatchEvent(new MouseEvent('click',{bubbles:true}));",
						headerId + "_desc_icon");
			}
		}
	}

	public boolean isFirstRowActionMenuDisplayed() {
		return isElementDisplayed(firstActionBtn);
	}

	public void clickOnFirstRowActionMenu() {
		jsClickElement(firstActionBtn);
	}

	public String getCurrentUrl() {
		String url = driver.getCurrentUrl();
		return url == null ? "" : url;
	}

	public String getViewStatusText() {
		if (isElementDisplayed(viewStatus)) {
			String text = viewStatus.getText();
			return text == null ? "" : text.trim();
		}
		return "";
	}

	public String getViewCommentText() {
		if (isElementDisplayed(viewComment)) {
			String text = viewComment.getText();
			return text == null ? "" : text.trim();
		}
		return "";
	}

	public boolean isViewProviderNameDisplayed() {
		return isElementDisplayed(viewProviderName);
	}

	public boolean isViewProviderVersionDisplayed() {
		return isElementDisplayed(viewProviderVersion);
	}

	public boolean isViewModalityDisplayed() {
		return isElementDisplayed(viewModality);
	}

	public boolean isViewCredentialDataFormatDisplayed() {
		return isElementDisplayed(viewCredentialDataFormat);
	}

	public boolean isViewAttributeNameDisplayed() {
		return isElementDisplayed(viewAttributeName);
	}

	public void resetViewport() {
		driver.manage().window().maximize();
	}

	public boolean isViewActionDisplayed() {
		return isElementDisplayed(viewBtn);
	}

	public boolean isDeleteActionDisplayed() {
		return isElementDisplayed(deleteBtn);
	}

	public void clickOnViewAction() {
		jsClickElement(viewBtn);
	}

	public void clickOnDeleteAction() {
		jsClickElement(deleteBtn);
	}

	public boolean isViewConfigurationDetailsPageDisplayed() {
		return isElementDisplayedQuick(By.id("view_bio_extractor_config_name_sub_title"), Duration.ofSeconds(10))
				|| isElementDisplayed(viewProviderName) || isElementDisplayed(viewBackBtn)
				|| isViewPageTitleDisplayed();
	}

	public boolean isViewPageTitleDisplayed() {
		if (!isElementDisplayedQuick(By.id("page_title"), Duration.ofSeconds(8))) {
			return isDisplayed(By.xpath("//h1[contains(normalize-space(),'View Biometric Extractor')]"));
		}
		String title = pageTitle.getText();
		return title != null && title.toLowerCase().contains("view biometric extractor");
	}

	public boolean isViewConfigurationNameDisplayed() {
		return isElementDisplayed(viewConfigName);
	}

	public String getViewConfigurationName() {
		if (!isViewConfigurationNameDisplayed()) {
			return "";
		}
		String text = viewConfigName.getText();
		return text == null ? "" : text.trim();
	}

	public boolean isViewCreatedOnDisplayed() {
		return isElementDisplayed(viewCreatedOn);
	}

	public boolean isViewCreatedTimeDisplayed() {
		return isElementDisplayed(viewCreatedTime);
	}

	public boolean isViewStatusDisplayed() {
		return isElementDisplayed(viewStatus) || isElementDisplayedQuick(
				By.xpath("//*[normalize-space()='Active' or normalize-space()='Deactivated' or normalize-space()='Inactive']"),
				Duration.ofSeconds(2));
	}

	public boolean areViewConfigurationDetailsDisplayed() {
		return isElementDisplayed(viewProviderName) && isElementDisplayed(viewProviderVersion)
				&& isElementDisplayed(viewModality);
	}

	public boolean areAllViewConfigurationAttributesDisplayed() {
		return isViewConfigurationNameDisplayed() && isElementDisplayed(viewProviderName)
				&& isElementDisplayed(viewProviderVersion) && isElementDisplayed(viewModality)
				&& isElementDisplayed(viewCredentialDataFormat) && isElementDisplayed(viewAttributeName);
	}

	public boolean isViewCommentDisplayed() {
		return isElementDisplayed(viewComment) || isElementDisplayedQuick(
				By.xpath("//*[contains(normalize-space(),'Comment')]/following::*[1]"), Duration.ofSeconds(2));
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
		boolean valuesAsText = isElementDisplayed(viewProviderName)
				&& "p".equalsIgnoreCase(viewProviderName.getTagName());
		return editableFields == 0 && valuesAsText;
	}

	public boolean isViewBackButtonDisplayed() {
		return isElementDisplayed(viewBackBtn);
	}

	public boolean isViewBackButtonAlignedEnd() {
		if (!isViewBackButtonDisplayed()) {
			return false;
		}
		WebElement parent = viewBackBtn.findElement(By.xpath("./.."));
		String parentClass = parent.getAttribute("class");
		return parentClass != null && parentClass.contains("justify-end");
	}

	public void clickOnViewBackButton() {
		if (isElementDisplayedQuick(By.id("view_bio_extractor_back_btn"), Duration.ofSeconds(5))) {
			jsClickElement(viewBackBtn);
		} else if (isElementDisplayedQuick(By.id("view_bio_extractor_config_go_back_btn"), Duration.ofSeconds(3))) {
			click(By.id("view_bio_extractor_config_go_back_btn"));
		} else if (isElementDisplayedQuick(By.id("title_back_icon"), Duration.ofSeconds(3))) {
			jsClickElement(titleBackIcon);
		} else {
			click(By.id("subtitle_back_icon"));
		}
	}

	public boolean isViewPageLayoutStructured() {
		return isViewPageTitleDisplayed() && isViewConfigurationNameDisplayed() && isViewCreatedOnDisplayed()
				&& areAllViewConfigurationAttributesDisplayed() && isViewBackButtonDisplayed();
	}

	public boolean isViewPageUsableAtViewport(int width, int height) {
		driver.manage().window().setSize(new org.openqa.selenium.Dimension(width, height));
		try {
			Thread.sleep(500);
		} catch (InterruptedException ignored) {
			Thread.currentThread().interrupt();
		}
		return isViewPageTitleDisplayed() && isViewConfigurationNameDisplayed() && isViewBackButtonDisplayed()
				&& areViewConfigurationDetailsDisplayed();
	}

	public boolean isDeleteConfirmationPopupDisplayed() {
		return isElementDisplayedQuick(By.id("deactivate_popup_header"), Duration.ofSeconds(10));
	}

	public void clickOnDeleteConfirmButton() {
		jsClickElement(deactivateConfirmBtn);
	}

	public void clickOnDeleteCancelButton() {
		jsClickElement(deactivateCancelBtn);
	}

	public boolean isConfigurationNameAbsentFromList(String configName) {
		return !isElementDisplayedQuick(
				By.xpath("//tr[contains(@id,'bio_extractor_config_list_item_')]//*[contains(normalize-space(),'"
						+ configName + "')]"),
				Duration.ofSeconds(5));
	}

	private void jsClickElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", element);
	}

	private void reactType(WebElement input, String value) {
		input.click();
		((JavascriptExecutor) driver).executeScript(
				"const input = arguments[0];"
						+ "const value = arguments[1];"
						+ "const setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
						+ "setter.call(input, value);"
						+ "input.dispatchEvent(new Event('input', { bubbles: true }));"
						+ "input.dispatchEvent(new Event('change', { bubbles: true }));",
				input, value);
	}
}
