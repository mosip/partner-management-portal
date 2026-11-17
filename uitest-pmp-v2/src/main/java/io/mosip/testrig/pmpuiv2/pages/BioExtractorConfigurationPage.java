package io.mosip.testrig.pmpuiv2.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BioExtractorConfigurationPage extends BasePage {

	@FindBy(id = "bio_extractor_config_create_btn_top")
	private WebElement createButton;

	@FindBy(id = "config_name_input")
	private WebElement configurationNameInput;

	@FindBy(id = "provider_name_input")
	private WebElement providerNameInput;

	@FindBy(id = "provider_version_input")
	private WebElement providerVersionInput;

	@FindBy(id = "modality_dropdown_btn")
	private WebElement modalityDropdown;

	@FindBy(id = "modality_option_face")
	private WebElement faceModalityOption;

	@FindBy(id = "credential_data_format_dropdown_btn")
	private WebElement credentialDataFormatDropdown;

	@FindBy(id = "credential_data_format_option_rawData")
	private WebElement rawDataFormatOption;

	@FindBy(id = "attribute_name_dropdown_btn")
	private WebElement attributeNameDropdown;

	@FindBy(css = "#attribute_name_dropdown_options button")
	private WebElement attributeNameOption;

	@FindBy(id = "create_bio_extractor_config_submit_btn")
	private WebElement submitButton;

	@FindBy(id = "create_bio_extractor_config_confirmation_header")
	private WebElement confirmationHeader;

	@FindBy(id = "confirmation_home_btn")
	private WebElement confirmationHomeButton;

	public BioExtractorConfigurationPage(WebDriver driver) {
		super(driver);
	}

	public void clickCreate() {
		clickOnElement(createButton);
	}

	public void enterConfigurationName(String configurationName) {
		enter(configurationNameInput, configurationName);
	}

	public void enterProviderName(String providerName) {
		enter(providerNameInput, providerName);
	}

	public void enterProviderVersion(String providerVersion) {
		enter(providerVersionInput, providerVersion);
	}

	public void selectFaceModality() {
		clickOnElement(modalityDropdown);
		clickOnElement(faceModalityOption);
	}

	public void selectRawDataFormat() {
		clickOnElement(credentialDataFormatDropdown);
		clickOnElement(rawDataFormatOption);
	}

	public void selectAttributeName() {
		clickOnElement(attributeNameDropdown);
		clickOnElement(attributeNameOption);
	}

	public void clickSubmit() {
		clickOnElement(submitButton);
	}

	public boolean isConfirmationDisplayed() {
		return isElementDisplayed(confirmationHeader);
	}

	public void clickConfirmationHome() {
		clickOnElement(confirmationHomeButton);
	}
}
