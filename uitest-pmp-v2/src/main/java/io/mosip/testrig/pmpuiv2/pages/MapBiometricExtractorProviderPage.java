package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MapBiometricExtractorProviderPage extends BasePage {

	@FindBy(id = "page_title")
	private WebElement pageTitle;

	@FindBy(id = "map_bio_extractor_provider_mandatory_mapping_msg")
	private WebElement mandatoryMappingBanner;

	@FindBy(id = "map_bio_extractor_provider_modality_1_dropdown_btn")
	private WebElement modalityDropdownRow1;

	@FindBy(id = "map_bio_extractor_provider_config_1_dropdown_btn")
	private WebElement configDropdownRow1;

	@FindBy(id = "map_bio_extractor_provider_add_more_btn_0")
	private WebElement addMoreButtonRow1;

	@FindBy(id = "map_bio_extractor_provider_clear_btn")
	private WebElement clearFormButton;

	@FindBy(id = "map_bio_extractor_provider_cancel_btn")
	private WebElement cancelButton;

	@FindBy(id = "map_bio_extractor_provider_submit_btn")
	private WebElement saveAndProceedButton;

	@FindBy(id = "map_bio_extractor_save_confirm_title")
	private WebElement saveConfirmTitle;

	@FindBy(id = "map_bio_extractor_save_confirm_cancel")
	private WebElement saveConfirmCancelButton;

	@FindBy(id = "map_bio_extractor_save_confirm_submit")
	private WebElement saveConfirmSubmitButton;

	@FindBy(id = "map_credential_type_mandatory_mapping_msg")
	private WebElement mapCredentialTypeBanner;

	@FindBy(id = "policy_requests_ovp_map_biometric_extractor_1")
	private WebElement mapBiometricActionFromList;

	@FindBy(id = "policy_list_map_biometric_extractor")
	private WebElement mapBiometricActionFromPartnerPoliciesList;

	@FindBy(id = "block_messsage_proceed")
	private WebElement blockerProceedButton;

	public MapBiometricExtractorProviderPage(WebDriver driver) {
		super(driver);
	}

	public boolean isMapBiometricExtractorProviderPageDisplayed() {
		return isElementDisplayedQuick(By.id("map_bio_extractor_provider_mandatory_mapping_msg"), Duration.ofSeconds(15))
				|| (isElementDisplayed(pageTitle) && getPageTitleText().toLowerCase().contains("biometric"));
	}

	public String getPageTitleText() {
		if (!isElementDisplayed(pageTitle)) {
			return "";
		}
		String text = pageTitle.getText();
		return text == null ? "" : text.trim();
	}

	public boolean isMandatoryMappingBannerDisplayed() {
		return isElementDisplayed(mandatoryMappingBanner);
	}

	public String getMandatoryMappingBannerText() {
		if (!isMandatoryMappingBannerDisplayed()) {
			return "";
		}
		String text = mandatoryMappingBanner.getText();
		return text == null ? "" : text.trim();
	}

	public boolean isModalityDropdownDisplayed() {
		return isElementDisplayed(modalityDropdownRow1);
	}

	public boolean isConfigDropdownDisplayed() {
		return isElementDisplayed(configDropdownRow1);
	}

	public boolean isClearFormButtonDisplayed() {
		return isElementDisplayed(clearFormButton);
	}

	public boolean isCancelButtonDisplayed() {
		return isElementDisplayed(cancelButton);
	}

	public boolean isSaveAndProceedButtonDisplayed() {
		return isElementDisplayed(saveAndProceedButton);
	}

	public boolean isSaveAndProceedButtonEnabled() {
		return isElementEnabled(saveAndProceedButton);
	}

	public void clickOnClearFormButton() {
		jsClickElement(clearFormButton);
	}

	public void clickOnCancelButton() {
		jsClickElement(cancelButton);
	}

	public void clickOnSaveAndProceedButton() {
		jsClickElement(saveAndProceedButton);
	}

	public void selectModalityForRow(int rowNumber, String modalityLabel) {
		By dropdown = By.id("map_bio_extractor_provider_modality_" + rowNumber + "_dropdown_btn");
		click(dropdown);
		By option = By.xpath("//button[contains(@id,'map_bio_extractor_provider_modality_" + rowNumber
				+ "_option') and contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"
				+ modalityLabel.toLowerCase() + "')]");
		if (isElementDisplayedQuick(option, Duration.ofSeconds(5))) {
			click(option);
		}
	}

	public void selectFirstConfigForRow(int rowNumber) {
		By dropdown = By.id("map_bio_extractor_provider_config_" + rowNumber + "_dropdown_btn");
		click(dropdown);
		By firstOption = By.id("map_bio_extractor_provider_config_" + rowNumber + "_option1");
		if (!isElementDisplayedQuick(firstOption, Duration.ofSeconds(5))) {
			firstOption = By.xpath(
					"//button[starts-with(@id,'map_bio_extractor_provider_config_" + rowNumber + "_option')][1]");
		}
		if (isElementDisplayedQuick(firstOption, Duration.ofSeconds(5))) {
			click(firstOption);
		}
	}

	public boolean isAddMoreButtonDisplayed() {
		return isElementDisplayed(addMoreButtonRow1)
				|| isElementDisplayedQuick(By.id("map_bio_extractor_provider_add_more_btn_0"), Duration.ofSeconds(3));
	}

	public boolean isAddMoreButtonEnabled() {
		WebElement addMore = addMoreButtonRow1;
		if (!isElementDisplayed(addMore)) {
			List<WebElement> buttons = driver.findElements(By.id("map_bio_extractor_provider_add_more_btn_0"));
			if (buttons.isEmpty()) {
				return false;
			}
			addMore = buttons.get(0);
		}
		String disabled = addMore.getAttribute("disabled");
		return disabled == null || disabled.isEmpty() || "false".equalsIgnoreCase(disabled);
	}

	public void clickOnAddMoreButton() {
		jsClickElement(addMoreButtonRow1);
	}

	public List<String> getModalityOptionsForRow(int rowNumber) {
		By dropdown = By.id("map_bio_extractor_provider_modality_" + rowNumber + "_dropdown_btn");
		click(dropdown);
		List<WebElement> options = driver.findElements(
				By.xpath("//button[contains(@id,'map_bio_extractor_provider_modality_" + rowNumber + "_option')]"));
		List<String> texts = new ArrayList<>();
		for (WebElement option : options) {
			String text = option.getText();
			if (text != null && !text.trim().isEmpty()) {
				texts.add(text.trim());
			}
		}
		click(dropdown);
		return texts;
	}

	public boolean isSaveConfirmPopupDisplayed() {
		return isElementDisplayedQuick(By.id("map_bio_extractor_save_confirm_title"), Duration.ofSeconds(10));
	}

	public void clickOnSaveConfirmCancelButton() {
		jsClickElement(saveConfirmCancelButton);
	}

	public void clickOnSaveConfirmSubmitButton() {
		jsClickElement(saveConfirmSubmitButton);
	}

	public boolean isMapCredentialTypePageDisplayed() {
		return isElementDisplayedQuick(By.id("map_credential_type_mandatory_mapping_msg"), Duration.ofSeconds(15))
				|| isElementDisplayedQuick(By.id("map_credential_type_submit_btn"), Duration.ofSeconds(5));
	}

	public boolean isMapBiometricActionDisplayedInPolicyRequestsList() {
		return isElementDisplayedQuick(By.id("policy_requests_ovp_map_biometric_extractor_1"), Duration.ofSeconds(8))
				|| isElementDisplayedQuick(By.xpath(
						"//*[(contains(@id,'policy_requests_ovp_map_biometric_extractor') or @id='policy_list_map_biometric_extractor')]"),
						Duration.ofSeconds(5));
	}

	public void clickOnMapBiometricActionFromPolicyRequestsList() {
		if (isElementDisplayedQuick(By.id("policy_requests_ovp_map_biometric_extractor_1"), Duration.ofSeconds(3))) {
			jsClickElement(mapBiometricActionFromList);
		} else if (isElementDisplayedQuick(By.id("policy_list_map_biometric_extractor"), Duration.ofSeconds(3))) {
			jsClickElement(mapBiometricActionFromPartnerPoliciesList);
		} else {
			By any = By.xpath("//*[contains(@id,'map_biometric_extractor')]");
			if (isElementDisplayedQuick(any, Duration.ofSeconds(3))) {
				click(any);
			}
		}
	}

	public boolean isReadOnlyPartnerDetailsDisplayed(String partnerId) {
		return isElementDisplayedQuick(
				By.xpath("//button[@disabled]//span[contains(normalize-space(),'" + partnerId + "')]"),
				Duration.ofSeconds(5));
	}

	public boolean isBlockerPromptDisplayed() {
		return isElementDisplayedQuick(By.id("blocker_prompt_description"), Duration.ofSeconds(3));
	}

	public void clickOnBlockerProceedButton() {
		jsClickElement(blockerProceedButton);
	}

	private void jsClickElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", element);
	}
}