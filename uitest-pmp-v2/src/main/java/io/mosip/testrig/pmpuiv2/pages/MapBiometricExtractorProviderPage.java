package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MapBiometricExtractorProviderPage extends BasePage {

	@FindBy(id = "page_title")
	private WebElement pageTitle;

	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;

	@FindBy(id = "map_bio_extractor_provider_mandatory_mapping_msg")
	private WebElement mandatoryMappingBanner;

	@FindBy(id = "map_bio_extractor_provider_modality_1_dropdown_btn")
	private WebElement firstModalityDropdown;

	@FindBy(id = "map_bio_extractor_provider_modality_1_option1")
	private WebElement firstModalityOption1;

	@FindBy(id = "map_bio_extractor_provider_modality_1_option2")
	private WebElement firstModalityOption2;

	@FindBy(id = "map_bio_extractor_provider_modality_1_option3")
	private WebElement firstModalityOption3;

	@FindBy(id = "map_bio_extractor_provider_modality_2_dropdown_btn")
	private WebElement secondModalityDropdown;

	@FindBy(id = "map_bio_extractor_provider_modality_2_option1")
	private WebElement secondModalityOption1;

	@FindBy(id = "map_bio_extractor_provider_modality_2_option2")
	private WebElement secondModalityOption2;

	@FindBy(id = "map_bio_extractor_provider_modality_2_option3")
	private WebElement secondModalityOption3;

	@FindBy(id = "map_bio_extractor_provider_config_1_dropdown_btn")
	private WebElement firstProviderDropdown;

	@FindBy(id = "map_bio_extractor_provider_config_1_option1")
	private WebElement firstProviderOption;

	@FindBy(id = "map_bio_extractor_provider_config_1_no_data_available")
	private WebElement firstProviderNoData;

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

	@FindBy(css = "[id^='policy_requests_ovp_map_biometric_extractor_']")
	private WebElement mapBiometricExtractorAction;

	@FindBy(id = "partner_list_item1")
	private WebElement firstPolicyRequestRow;

	@FindBy(id = "partner_list_view1")
	private WebElement firstPolicyRequestAction;

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
	}

	public String getFirstModalityText() {
		return getTextFromLocator(firstModalityDropdown).trim();
	}

	public void openFirstProviderDropdown() {
		clickOnElement(firstProviderDropdown);
	}

	public boolean isFirstProviderOptionDisplayed() {
		return isElementDisplayedQuick(firstProviderOption, ABSENCE_CHECK_TIMEOUT);
	}

	public void selectFirstProvider() {
		clickOnElement(firstProviderOption);
	}

	public boolean isFirstProviderListEmpty() {
		return isElementDisplayedQuick(firstProviderNoData, ABSENCE_CHECK_TIMEOUT);
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
		return isElementDisplayedQuick(saveConfirmTitle, ABSENCE_CHECK_TIMEOUT);
	}

	public void confirmSave() {
		clickOnElement(saveConfirmButton);
	}

	public void clickTitleBack() {
		clickOnElement(titleBackIcon);
	}

	public void openFirstPolicyRequestAction() {
		clickOnElement(firstPolicyRequestAction);
	}

	public boolean isMapBiometricExtractorActionDisplayed() {
		return isElementDisplayedQuick(mapBiometricExtractorAction, ABSENCE_CHECK_TIMEOUT);
	}

	public boolean isMapBiometricExtractorActionDisabled() {
		String className = new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			if (!isElementDisplayedQuick(mapBiometricExtractorAction, ABSENCE_CHECK_TIMEOUT)) {
				return null;
			}
			String value = mapBiometricExtractorAction.getAttribute("class");
			return value != null && value.contains("pointer-events-none") ? value : null;
		});
		return className.contains("pointer-events-none");
	}

	public void openMapBiometricExtractorAction() {
		clickOnElement(mapBiometricExtractorAction);
	}

	public String getFirstPolicyRequestRowText() {
		return getTextFromLocator(firstPolicyRequestRow).trim();
	}

	private WebElement[] modalityOptions(int row) {
		if (row == 1) {
			return new WebElement[] { firstModalityOption1, firstModalityOption2, firstModalityOption3 };
		}
		return new WebElement[] { secondModalityOption1, secondModalityOption2, secondModalityOption3 };
	}

	private boolean optionShowsModality(WebElement option, String modality) {
		return isElementDisplayedQuick(option, ABSENCE_CHECK_TIMEOUT)
				&& modality.equalsIgnoreCase(getTextFromLocator(option).trim());
	}
}
