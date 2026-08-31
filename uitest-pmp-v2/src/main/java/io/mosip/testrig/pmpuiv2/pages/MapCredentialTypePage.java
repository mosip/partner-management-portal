package io.mosip.testrig.pmpuiv2.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Step 3 of the Credential Partner policy request flow - Map Credential Type.
 *
 * The auto populated fields (Partner ID, Partner Type, Policy Group, Policy
 * Name, Biometric Modality, Biometric Provider Configuration) are rendered as
 * disabled buttons that carry no id of their own, so they are located through
 * the label that sits immediately above them.
 */
public class MapCredentialTypePage extends BasePage {

	private static final String READ_ONLY_FIELD_XPATH = "//label[starts-with(normalize-space(.), '%s')]/following-sibling::button";

	public static final String PARTNER_ID_FIELD = "Partner ID";
	public static final String PARTNER_TYPE_FIELD = "Partner Type";
	public static final String POLICY_GROUP_FIELD = "Policy Group";
	public static final String POLICY_NAME_FIELD = "Policy Name";
	public static final String BIOMETRIC_MODALITY_FIELD = "Biometric Modality";
	public static final String BIOMETRIC_PROVIDER_CONFIG_FIELD = "Biometric Provider Configuration";

	@FindBy(id = "map_credential_type_mandatory_mapping_msg")
	private WebElement mandatoryMappingBanner;

	@FindBy(id = "map_credential_type_1_dropdown_btn")
	private WebElement credentialTypeDropdown;

	@FindBy(id = "map_credential_type_clear_btn")
	private WebElement clearFormButton;

	@FindBy(id = "map_credential_type_cancel_btn")
	private WebElement cancelButton;

	@FindBy(id = "map_credential_type_submit_btn")
	private WebElement submitButton;

	@FindBy(id = "map_credential_type_error_msg")
	private WebElement errorMessage;

	@FindBy(id = "map_credential_type_confirmation_header")
	private WebElement acknowledgementHeader;

	@FindBy(id = "map_credential_type_confirmation_description")
	private WebElement acknowledgementDescription;

	@FindBy(id = "confirmation_success_icon")
	private WebElement acknowledgementSuccessIcon;

	@FindBy(id = "confirmation_go_back_btn")
	private WebElement goBackButton;

	@FindBy(id = "confirmation_home_btn")
	private WebElement homeButton;

	public MapCredentialTypePage(WebDriver driver) {
		super(driver);
	}

	private By readOnlyField(String label) {
		return By.xpath(String.format(READ_ONLY_FIELD_XPATH, label));
	}

	public boolean isMapCredentialTypePageTitleDisplayed(String expectedTitle) {
		return isTextPresent(By.id("page_title"), expectedTitle);
	}

	public boolean isMandatoryMappingBannerDisplayed() {
		return isElementDisplayed(mandatoryMappingBanner);
	}

	public String getMandatoryMappingBannerText() {
		return getTextFromLocator(mandatoryMappingBanner);
	}

	public boolean isReadOnlyFieldDisplayed(String label) {
		return isDisplayed(readOnlyField(label));
	}

	public String getReadOnlyFieldValue(String label) {
		return getTextFromLocator(readOnlyField(label));
	}

	/** The auto populated fields are disabled buttons, so they can never take input. */
	public boolean isReadOnlyFieldNotEditable(String label) {
		WebElement field = waitAndFindElement(readOnlyField(label));
		return !field.isEnabled();
	}

	public boolean isCredentialTypeDropdownDisplayed() {
		return isElementDisplayed(credentialTypeDropdown);
	}

	public void clickOnCredentialTypeDropdown() {
		clickOnElement(credentialTypeDropdown);
	}

	public String getSelectedCredentialType() {
		return getTextFromLocator(credentialTypeDropdown);
	}

	/** Reads every option rendered under the credential type dropdown. */
	public List<String> getCredentialTypeOptions() {
		clickOnCredentialTypeDropdown();
		List<String> options = new ArrayList<>();
		for (WebElement option : driver.findElements(By.xpath("//*[starts-with(@id,'map_credential_type_1_option')]"))) {
			options.add(option.getText().trim());
		}
		return options;
	}

	public void selectCredentialType(String value) {
		clickOnCredentialTypeDropdown();
		click(By.xpath("//*[starts-with(@id,'map_credential_type_1_option') and normalize-space()='" + value + "']"));
	}

	public boolean isClearFormButtonDisplayed() {
		return isElementDisplayed(clearFormButton);
	}

	public boolean isClearFormButtonEnabled() {
		return isElementEnabled(clearFormButton);
	}

	public void clickOnClearFormButton() {
		clickOnElement(clearFormButton);
	}

	public boolean isCancelButtonDisplayed() {
		return isElementDisplayed(cancelButton);
	}

	public boolean isCancelButtonEnabled() {
		return isElementEnabled(cancelButton);
	}

	public void clickOnCancelButton() {
		clickOnElement(cancelButton);
	}

	public boolean isSubmitButtonDisplayed() {
		return isElementDisplayed(submitButton);
	}

	/** Submit stays disabled until a credential type is picked. */
	public boolean isSubmitButtonEnabled() {
		return isElementEnabled(submitButton);
	}

	public boolean isSubmitButtonDisabled() {
		return isElementDisabled(submitButton);
	}

	public void clickOnSubmitButton() {
		clickOnElement(submitButton);
	}

	public boolean isErrorMessageDisplayed() {
		return isElementDisplayed(errorMessage);
	}

	public String getErrorMessage() {
		return getTextFromLocator(errorMessage);
	}

	public boolean isAcknowledgementScreenDisplayed() {
		return isElementDisplayed(acknowledgementSuccessIcon) && isElementDisplayed(acknowledgementHeader);
	}

	public String getAcknowledgementHeader() {
		return getTextFromLocator(acknowledgementHeader);
	}

	public String getAcknowledgementDescription() {
		return getTextFromLocator(acknowledgementDescription);
	}

	public boolean isGoBackButtonDisplayed() {
		return isElementDisplayed(goBackButton);
	}

	public boolean isGoBackButtonEnabled() {
		return isElementEnabled(goBackButton);
	}

	public void clickOnGoBackButton() {
		clickOnElement(goBackButton);
	}

	public boolean isHomeButtonDisplayed() {
		return isElementDisplayed(homeButton);
	}

	public boolean isHomeButtonEnabled() {
		return isElementEnabled(homeButton);
	}

	public void clickOnHomeButton() {
		clickOnElement(homeButton);
	}
}
