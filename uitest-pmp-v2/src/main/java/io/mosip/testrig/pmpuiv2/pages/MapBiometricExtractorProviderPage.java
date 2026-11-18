package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class MapBiometricExtractorProviderPage extends BasePage {

	@FindBy(id = "page_title")
	private WebElement pageTitle;

	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;

	@FindBy(id = "map_bio_extractor_provider_mandatory_mapping_msg")
	private WebElement mandatoryMappingBanner;

	@FindBy(id = "map_bio_extractor_provider_modality_1_dropdown_btn")
	private WebElement firstModalityDropdown;

	@FindBy(css = "[id^='map_bio_extractor_provider_modality_1_option']")
	private List<WebElement> firstModalityOptions;

	@FindBy(id = "map_bio_extractor_provider_modality_2_dropdown_btn")
	private WebElement secondModalityDropdown;

	@FindBy(css = "[id^='map_bio_extractor_provider_modality_2_option']")
	private List<WebElement> secondModalityOptions;

	@FindBy(id = "map_bio_extractor_provider_config_1_dropdown_btn")
	private WebElement firstProviderDropdown;

	@FindBy(id = "map_bio_extractor_provider_config_1_search_input")
	private WebElement firstProviderSearchInput;

	@FindBy(css = "[id^='map_bio_extractor_provider_config_1_option']")
	private List<WebElement> firstProviderOptions;

	@FindBy(id = "map_bio_extractor_provider_add_more_btn_0")
	private WebElement addMoreButton;

	@FindBy(id = "map_bio_extractor_provider_clear_btn")
	private WebElement clearButton;

	@FindBy(id = "map_bio_extractor_provider_cancel_btn")
	private WebElement cancelButton;

	@FindBy(id = "map_bio_extractor_provider_submit_btn")
	private WebElement saveAndProceedButton;

	@FindBy(id = "map_bio_extractor_save_confirm_title")
	private WebElement saveConfirmTitle;

	@FindBy(id = "map_bio_extractor_save_confirm_submit")
	private WebElement saveConfirmButton;

	@FindBy(xpath = "//label[normalize-space(.)='Partner ID*']/following-sibling::button[1]")
	private WebElement partnerIdValue;

	@FindBy(xpath = "//label[normalize-space(.)='Partner Type*']/following-sibling::button[1]")
	private WebElement partnerTypeValue;

	@FindBy(xpath = "//label[normalize-space(.)='Policy Name*']/following-sibling::button[1]")
	private WebElement policyNameValue;

	@FindBy(xpath = "//label[normalize-space(.)='Biometric Extractor Provider Name*']/following-sibling::button[1]")
	private WebElement providerNameValue;

	@FindBy(xpath = "//label[normalize-space(.)='Biometric Extractor Provider Version*']/following-sibling::button[1]")
	private WebElement providerVersionValue;

	public MapBiometricExtractorProviderPage(WebDriver driver) {
		super(driver);
	}

	public String waitUntilPageTitle(String expectedTitle) {
		return new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			String title = getTextFromLocator(pageTitle);
			return expectedTitle.equals(title == null ? "" : title.trim()) ? title.trim() : null;
		});
	}

	public String getPageTitle() {
		return getTextFromLocator(pageTitle).trim();
	}

	public String getMandatoryMappingBanner() {
		return getTextFromLocator(mandatoryMappingBanner).trim();
	}

	public boolean isMandatoryMappingBannerDisplayed() {
		return isElementDisplayed(mandatoryMappingBanner);
	}

	public String getPartnerId() {
		return getTextFromLocator(partnerIdValue).trim();
	}

	public String getPartnerType() {
		return getTextFromLocator(partnerTypeValue).trim();
	}

	public String getPolicyName() {
		return getTextFromLocator(policyNameValue).trim();
	}

	public boolean isSaveAndProceedDisabled() {
		return isElementDisabled(saveAndProceedButton);
	}

	public void openFirstModalityDropdown() {
		clickOnElement(firstModalityDropdown);
	}

	public boolean isModalityOptionDisplayed(int row, String modality) {
		for (WebElement option : modalityOptions(row)) {
			if (optionShowsModality(option, modality)) {
				return true;
			}
		}
		return false;
	}

	public void openModalityDropdown(int row) {
		clickOnElement(row == 1 ? firstModalityDropdown : secondModalityDropdown);
	}

	public void selectModality(int row, String modality) {
		openModalityDropdown(row);
		for (WebElement option : modalityOptions(row)) {
			if (optionShowsModality(option, modality)) {
				clickOnElement(option);
				return;
			}
		}
		throw new NoSuchElementException("Biometric modality was not offered: " + modality);
	}

	public String getFirstModalityText() {
		return getTextFromLocator(firstModalityDropdown).trim();
	}

	public void selectProviderConfiguration(String configurationName) {
		clickOnElement(firstProviderDropdown);
		enter(firstProviderSearchInput, configurationName);
		for (WebElement option : firstProviderOptions) {
			String optionText = getTextFromLocator(option);
			if (optionText != null && configurationName.equalsIgnoreCase(optionText.trim())) {
				clickOnElement(option);
				return;
			}
		}
		throw new NoSuchElementException("Biometric extractor configuration was not offered: " + configurationName);
	}

	public String waitUntilFirstProviderConfigurationSelected() {
		return new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			String configuration = getTextFromLocator(firstProviderDropdown);
			if (configuration == null) {
				return null;
			}
			configuration = configuration.trim();
			return configuration.isEmpty()
					|| GlobalConstants.SELECT_BIOMETRIC_PROVIDER.equals(configuration) ? null : configuration;
		});
	}

	public String getProviderName() {
		return getTextFromLocator(providerNameValue).trim();
	}

	public String getProviderVersion() {
		return getTextFromLocator(providerVersionValue).trim();
	}

	public void clickAddMore() {
		clickOnElement(addMoreButton);
	}

	public void clickClearForm() {
		clickOnElement(clearButton);
	}

	public void clickCancel() {
		clickOnElement(cancelButton);
	}

	public void clickSaveAndProceed() {
		clickOnElement(saveAndProceedButton);
	}

	public boolean isSaveConfirmationDisplayed() {
		return isElementDisplayed(saveConfirmTitle);
	}

	public void confirmSave() {
		clickOnElement(saveConfirmButton);
	}

	public void clickTitleBack() {
		clickOnElement(titleBackIcon);
	}

	private List<WebElement> modalityOptions(int row) {
		return row == 1 ? firstModalityOptions : secondModalityOptions;
	}

	private boolean optionShowsModality(WebElement option, String modality) {
		return isElementDisplayedQuick(option, ABSENCE_CHECK_TIMEOUT)
				&& modality.equalsIgnoreCase(getTextFromLocator(option).trim());
	}
}
