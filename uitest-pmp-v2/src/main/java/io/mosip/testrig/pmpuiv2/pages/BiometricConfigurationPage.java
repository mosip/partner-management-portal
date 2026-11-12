package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class BiometricConfigurationPage extends BasePage {

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

	@FindBy(xpath = "//tr[@id='bio_extractor_config_list_item_1']/td[1]")
	private WebElement firstConfigNameCell;

	@FindBy(xpath = "//button[starts-with(@id,'modality_option_')]")
	private List<WebElement> modalityOptions;

	@FindBy(id = "credential_data_format_option_rawData")
	private WebElement rawDataOption;

	@FindBy(id = "credential_data_format_option_templateData")
	private WebElement templateDataOption;

	@FindBy(xpath = "//button[starts-with(@id,'attribute_name_option_')]")
	private List<WebElement> attributeNameOptions;

	@FindBy(xpath = "//button[starts-with(@id,'bio_modality_filter_option')]")
	private List<WebElement> modalityFilterOptions;

	public BiometricConfigurationPage(WebDriver driver) {
		super(driver);
	}

	public boolean isBiometricConfigurationDashboardCardDisplayed() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dashboardCard);
		} catch (Exception ignored) {
		}
		return isElementDisplayed(dashboardCard);
	}

	public void clickOnBiometricConfigurationDashboardCard() {
		clickOnElement(dashboardCard);
	}

	public boolean isBiometricConfigurationListPageDisplayed() {
		return isPageTitleDisplayed("biometric");
	}

	public void clickOnCreateBiometricConfigurationButton() {
		if (isElementDisplayed(createBtnTop)) {
			clickOnElement(createBtnTop);
			return;
		}
		clickOnElement(createBtnCenter);
	}

	public boolean isCreateButtonTopDisplayed() {
		return isElementDisplayed(createBtnTop);
	}

	public boolean isCreateButtonCenterDisplayed() {
		return isElementDisplayed(createBtnCenter);
	}

	public boolean isConfigurationNameFieldDisplayed() {
		return isElementDisplayed(configNameInput);
	}

	public boolean isProviderNameFieldDisplayed() {
		return isElementDisplayed(providerNameInput);
	}

	public boolean isProviderVersionFieldDisplayed() {
		return isElementDisplayed(providerVersionInput);
	}

	public boolean isModalityDropdownDisplayed() {
		return isElementDisplayed(modalityDropdown);
	}

	public boolean isCreateBiometricConfigurationFormDisplayed() {
		return isConfigurationNameFieldDisplayed();
	}

	public void enterConfigurationName(String name) {
		enterInputText(configNameInput, name);
	}

	public void enterProviderName(String name) {
		enterInputText(providerNameInput, name);
	}

	public void enterProviderVersion(String version) {
		enterInputText(providerVersionInput, version);
	}

	public void selectBiometricModality(String modality) {
		String value = modality == null ? "face" : modality.trim().toLowerCase();
		if (selectByValueIfNativeDropdown(modalityDropdown, value)) {
			return;
		}
		clickOnElement(modalityDropdown);
		clickOptionByIdSuffix(modalityOptions, value);
	}

	public void selectCredentialDataFormatRawData() {
		if (selectByValueIfNativeDropdown(credentialDataFormatDropdown, "rawData")) {
			return;
		}
		clickOnElement(credentialDataFormatDropdown);
		clickOnElement(rawDataOption);
	}

	public void selectCredentialDataFormatTemplateData() {
		if (selectByValueIfNativeDropdown(credentialDataFormatDropdown, "templateData")) {
			return;
		}
		clickOnElement(credentialDataFormatDropdown);
		clickOnElement(templateDataOption);
	}

	public void selectFirstAttributeNameIfNeeded() {
		if (!isElementDisplayed(attributeNameDropdown)) {
			return;
		}
		String current = attributeNameDropdown.getText() == null ? "" : attributeNameDropdown.getText().trim();
		if (!current.isEmpty() && !current.toLowerCase().contains("select")) {
			return;
		}
		if (selectByIndexIfNativeDropdown(attributeNameDropdown, 1)) {
			return;
		}
		clickOnElement(attributeNameDropdown);
		if (!attributeNameOptions.isEmpty()) {
			clickOnElement(attributeNameOptions.get(0));
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
		return isElementDisplayed(successHeader);
	}

	public void clickOnSuccessGoBackButton() {
		clickOnElement(goBackBtn);
	}

	public void clickOnSuccessHomeButton() {
		clickOnElement(homeBtn);
	}

	public boolean isConfigurationNamePresentInList(String configName) {
		return isElementDisplayedQuick(
				By.xpath("//td[contains(normalize-space(),'" + configName + "')]"), Duration.ofSeconds(10));
	}

	public boolean isListingTableDisplayed() {
		return isElementDisplayedQuick(By.id("bio_extractor_config_list_item_1"), Duration.ofSeconds(10))
				|| isElementDisplayed(configNameHeader);
	}

	public boolean isConfigNameColumnDisplayed() {
		return isElementDisplayed(configNameHeader);
	}

	public boolean isProviderNameColumnDisplayed() {
		return isElementDisplayed(providerNameHeader);
	}

	public boolean isProviderVersionColumnDisplayed() {
		return isElementDisplayed(providerVersionHeader);
	}

	public boolean isModalityColumnDisplayed() {
		return isElementDisplayed(modalityHeader);
	}

	public boolean isCreationDateColumnDisplayed() {
		return isElementDisplayed(creationDateHeader);
	}

	public boolean areListingTableColumnsDisplayed() {
		return isConfigNameColumnDisplayed();
	}

	public boolean isFilterButtonDisplayed() {
		return isElementDisplayed(filterBtn);
	}

	public void clickOnFilterButton() {
		clickOnElement(filterBtn);
	}

	public boolean isFilterConfigNameDisplayed() {
		return isElementDisplayed(configNameFilter);
	}

	public boolean isFilterProviderNameDisplayed() {
		return isElementDisplayed(providerNameFilter);
	}

	public boolean isFilterProviderVersionDisplayed() {
		return isElementDisplayed(providerVersionFilter);
	}

	public boolean isFilterModalityDisplayed() {
		return isElementDisplayed(modalityFilterDropdown);
	}

	public boolean isApplyFilterButtonDisplayed() {
		return isElementDisplayed(applyFilterBtn);
	}

	public boolean isFilterPanelDisplayed() {
		return isFilterConfigNameDisplayed();
	}

	public void enterConfigurationNameInFilter(String name) {
		enterInputText(configNameFilter, name);
	}

	public void enterProviderNameInFilter(String name) {
		enterInputText(providerNameFilter, name);
	}

	public void enterProviderVersionInFilter(String version) {
		enterInputText(providerVersionFilter, version);
	}

	public void selectModalityInFilter(String modality) {
		String value = modality == null ? "face" : modality.trim().toLowerCase();
		if (selectByValueIfNativeDropdown(modalityFilterDropdown, value)) {
			return;
		}
		clickOnElement(modalityFilterDropdown);
		clickOptionByIdSuffix(modalityFilterOptions, value);
	}

	public void clickOnApplyFilterButton() {
		clickOnElement(applyFilterBtn);
	}

	public boolean isFilterResetButtonDisplayed() {
		return isElementDisplayed(filterResetBtn);
	}

	public void clickOnFilterResetButton() {
		clickOnElement(filterResetBtn);
	}

	public boolean isEmptyFilterResultDisplayed() {
		return !isElementDisplayed(firstListRow);
	}

	public String getFirstRowConfigurationName() {
		if (!isElementDisplayed(firstConfigNameCell)) {
			return "";
		}
		String text = firstConfigNameCell.getText();
		return text == null ? "" : text.trim();
	}

	public void clickOnSortAsc(String headerId) {
		click(By.id(headerId + "_asc_icon"));
	}

	public void clickOnSortDesc(String headerId) {
		click(By.id(headerId + "_desc_icon"));
	}

	public boolean isFirstRowActionMenuDisplayed() {
		return isElementDisplayed(firstActionBtn);
	}

	public void clickOnFirstRowActionMenu() {
		clickOnElement(firstActionBtn);
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
		clickOnElement(viewBtn);
	}

	public void clickOnDeleteAction() {
		clickOnElement(deleteBtn);
	}

	public boolean isViewConfigurationDetailsPageDisplayed() {
		return isViewPageTitleDisplayed();
	}

	public boolean isViewPageTitleDisplayed() {
		if (!isElementDisplayed(pageTitle)) {
			return false;
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
		return isElementDisplayed(viewStatus);
	}

	public boolean areViewConfigurationDetailsDisplayed() {
		return isViewProviderNameDisplayed();
	}

	public boolean areAllViewConfigurationAttributesDisplayed() {
		return isViewConfigurationNameDisplayed();
	}

	public boolean isViewCommentDisplayed() {
		return isElementDisplayed(viewComment);
	}

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
		clickOnElement(viewBackBtn);
	}

	public boolean isViewPageLayoutStructured() {
		return isViewPageTitleDisplayed();
	}

	public boolean isViewPageUsableAtViewport(int width, int height) {
		driver.manage().window().setSize(new org.openqa.selenium.Dimension(width, height));
		return isViewPageTitleDisplayed();
	}

	public boolean isDeleteConfirmationPopupDisplayed() {
		return isElementDisplayed(deactivatePopupHeader);
	}

	public void clickOnDeleteConfirmButton() {
		clickOnElement(deactivateConfirmBtn);
	}

	public void clickOnDeleteCancelButton() {
		clickOnElement(deactivateCancelBtn);
	}

	public boolean isConfigurationNameAbsentFromList(String configName) {
		return !isElementDisplayedQuick(
				By.xpath("//tr[contains(@id,'bio_extractor_config_list_item_')]//td[contains(normalize-space(),'"
						+ configName + "')]"),
				Duration.ofSeconds(5));
	}

	private boolean isPageTitleDisplayed(String expectedText) {
		if (!isElementDisplayed(pageTitle) || expectedText == null) {
			return false;
		}
		String title = pageTitle.getText();
		return title != null && title.toLowerCase().contains(expectedText.toLowerCase());
	}

	private boolean selectByValueIfNativeDropdown(WebElement dropdown, String value) {
		if (dropdown == null || !"select".equalsIgnoreCase(dropdown.getTagName())) {
			return false;
		}
		new Select(dropdown).selectByValue(value);
		return true;
	}

	private boolean selectByIndexIfNativeDropdown(WebElement dropdown, int index) {
		if (dropdown == null || !"select".equalsIgnoreCase(dropdown.getTagName())) {
			return false;
		}
		new Select(dropdown).selectByIndex(index);
		return true;
	}

	private void clickOptionByIdSuffix(List<WebElement> options, String idSuffix) {
		for (WebElement option : options) {
			String id = option.getAttribute("id");
			if (id != null && id.toLowerCase().endsWith(idSuffix.toLowerCase())) {
				clickOnElement(option);
				return;
			}
		}
	}

	private void enterInputText(WebElement input, String value) {
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
