package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;

import org.openqa.selenium.By;
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
		return isElementDisplayedQuick(modalityOption(row, modality), ABSENCE_CHECK_TIMEOUT);
	}

	public void openModalityDropdown(int row) {
		if (row == 1) {
			clickOnElement(firstModalityDropdown);
			return;
		}
		click(By.id("map_bio_extractor_provider_modality_" + row + "_dropdown_btn"));
	}

	public void selectModality(int row, String modality) {
		openModalityDropdown(row);
		click(modalityOption(row, modality));
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

	public void openPolicyRequestAction(String policyName) {
		click(By.xpath("//td[normalize-space()='" + policyName
				+ "']/parent::tr//button[contains(@id,'partner_list_view')]"));
	}

	public void openPolicyRequestActionForPartner(String partnerId) {
		click(By.xpath("//td[normalize-space()='" + partnerId
				+ "']/parent::tr//button[contains(@id,'partner_list_view')]"));
	}

	public boolean isMapBiometricExtractorActionDisplayed() {
		return isElementDisplayedQuick(mapBiometricExtractorAction, ABSENCE_CHECK_TIMEOUT);
	}

	public String waitForMapBiometricExtractorActionClass() {
		return new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			String className = mapBiometricExtractorAction.getAttribute("class");
			return className != null && className.contains("pointer-events-none") ? className : null;
		});
	}

	public void openMapBiometricExtractorAction() {
		clickOnElement(mapBiometricExtractorAction);
	}

	public String getPolicyRequestRowText(String policyName) {
		return getTextFromLocator(By.xpath("//td[normalize-space()='" + policyName + "']/parent::tr")).trim();
	}

	private By modalityOption(int row, String modality) {
		return By.xpath("//button[starts-with(@id,'map_bio_extractor_provider_modality_" + row
				+ "_option') and normalize-space()='" + modality + "']");
	}
}
