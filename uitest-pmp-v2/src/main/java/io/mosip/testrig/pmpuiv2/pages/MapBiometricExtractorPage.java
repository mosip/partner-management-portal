package io.mosip.testrig.pmpuiv2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Step 2 of the Credential Partner policy request flow - Map Biometric
 * Extractor Provider. Saving this step navigates straight on to Step 3 (Map
 * Credential Type), so this page object only needs to drive the mapping rows
 * and the save confirmation dialog.
 */
public class MapBiometricExtractorPage extends BasePage {

	@FindBy(id = "map_bio_extractor_provider_modality_1_dropdown_btn")
	private WebElement firstModalityDropdown;

	@FindBy(id = "map_bio_extractor_provider_config_1_dropdown_btn")
	private WebElement firstConfigDropdown;

	@FindBy(id = "map_bio_extractor_provider_cancel_btn")
	private WebElement cancelButton;

	@FindBy(id = "map_bio_extractor_provider_submit_btn")
	private WebElement submitButton;

	@FindBy(id = "map_bio_extractor_save_confirm_title")
	private WebElement saveConfirmTitle;

	@FindBy(id = "map_bio_extractor_save_confirm_cancel")
	private WebElement saveConfirmCancelButton;

	@FindBy(id = "map_bio_extractor_save_confirm_submit")
	private WebElement saveConfirmSubmitButton;

	@FindBy(id = "map_bio_extractor_provider_error_msg")
	private WebElement errorMessage;

	public MapBiometricExtractorPage(WebDriver driver) {
		super(driver);
	}

	public boolean isMapBiometricExtractorPageDisplayed() {
		return isElementDisplayed(firstModalityDropdown);
	}

	public void clickOnModalityDropdown() {
		clickOnElement(firstModalityDropdown);
	}

	/** Picks the first real modality option (index 1 skips the placeholder). */
	public void selectFirstModality() {
		clickOnModalityDropdown();
		click(By.id("map_bio_extractor_provider_modality_1_option1"));
	}

	public void clickOnConfigDropdown() {
		clickOnElement(firstConfigDropdown);
	}

	public void selectFirstConfiguration() {
		clickOnConfigDropdown();
		click(By.id("map_bio_extractor_provider_config_1_option1"));
	}

	public boolean isSubmitButtonEnabled() {
		return isElementEnabled(submitButton);
	}

	public void clickOnSubmitButton() {
		clickOnElement(submitButton);
	}

	public boolean isSaveConfirmDialogDisplayed() {
		return isElementDisplayed(saveConfirmTitle);
	}

	public void clickOnSaveConfirmSubmitButton() {
		clickOnElement(saveConfirmSubmitButton);
	}

	public void clickOnCancelButton() {
		clickOnElement(cancelButton);
	}

	public boolean isErrorMessageDisplayed() {
		return isElementDisplayed(errorMessage);
	}

	/** Maps the first available modality plus configuration and saves the step. */
	public void mapFirstAvailableExtractorAndSave() {
		selectFirstModality();
		selectFirstConfiguration();
		clickOnSubmitButton();
		if (isSaveConfirmDialogDisplayed()) {
			clickOnSaveConfirmSubmitButton();
		}
	}
}
