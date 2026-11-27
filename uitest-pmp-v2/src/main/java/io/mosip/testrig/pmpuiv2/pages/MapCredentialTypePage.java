package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class MapCredentialTypePage extends BasePage {

	@FindBy(id = "page_title")
	private WebElement pageTitle;

	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;

	@FindBy(id = "map_credential_type_mandatory_mapping_msg")
	private WebElement mandatoryMappingBanner;

	@FindBy(id = "map_credential_type_1_dropdown_btn")
	private WebElement credentialTypeDropdown;

	@FindBy(css = "[id^='map_credential_type_1_option']")
	private List<WebElement> credentialTypeOptions;

	@FindBy(id = "map_credential_type_clear_btn")
	private WebElement clearButton;

	@FindBy(id = "map_credential_type_cancel_btn")
	private WebElement cancelButton;

	@FindBy(id = "map_credential_type_submit_btn")
	private WebElement submitButton;

	@FindBy(id = "map_credential_type_confirmation_header")
	private WebElement acknowledgementHeader;

	@FindBy(id = "map_credential_type_confirmation_description")
	private WebElement acknowledgementDescription;

	@FindBy(id = "confirmation_go_back_btn")
	private WebElement goBackButton;

	@FindBy(id = "confirmation_home_btn")
	private WebElement homeButton;

	@FindBy(id = "filter_btn")
	private WebElement filterButton;

	@FindBy(id = "partner_id_filter")
	private WebElement partnerIdFilter;

	@FindBy(id = "policy_name_filter")
	private WebElement policyNameFilter;

	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilterButton;

	@FindBy(id = "partner_list_item1")
	private WebElement firstPolicyRequestRow;

	@FindBy(id = "partner_list_view1")
	private WebElement firstPolicyRequestAction;

	@FindBy(css = "[id^='policy_requests_ovp_map_credential_type_']")
	private WebElement mapCredentialTypeAction;

	@FindBy(xpath = "//label[normalize-space(.)='Partner ID*']/following-sibling::button[1]")
	private WebElement partnerIdValue;

	@FindBy(xpath = "//label[normalize-space(.)='Partner Type*']/following-sibling::button[1]")
	private WebElement partnerTypeValue;

	@FindBy(xpath = "//label[normalize-space(.)='Policy Group*']/following-sibling::button[1]")
	private WebElement policyGroupValue;

	@FindBy(xpath = "//label[normalize-space(.)='Policy Name*']/following-sibling::button[1]")
	private WebElement policyNameValue;

	@FindBy(xpath = "//label[normalize-space(.)='Biometric Modality*']/following-sibling::button[1]")
	private WebElement biometricModalityValue;

	@FindBy(xpath = "//label[normalize-space(.)='Biometric Provider Configuration*']/following-sibling::button[1]")
	private WebElement providerConfigurationValue;

	public MapCredentialTypePage(WebDriver driver) {
		super(driver);
	}

	public String waitUntilPageTitle(String expectedTitle) {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(webDriver -> {
			String title = getTextFromLocator(pageTitle);
			return expectedTitle.equals(title == null ? "" : title.trim()) ? title.trim() : null;
		});
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

	public String getPolicyGroup() {
		return getTextFromLocator(policyGroupValue).trim();
	}

	public String getPolicyName() {
		return getTextFromLocator(policyNameValue).trim();
	}

	public String waitUntilBiometricModalityPopulated() {
		return new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			String modality = getTextFromLocator(biometricModalityValue);
			if (modality == null) {
				return null;
			}
			modality = modality.trim();
			return modality.isEmpty() || GlobalConstants.AUTO_POPULATED_BASED_ON_CONFIGURATION.equals(modality) ? null
					: modality;
		});
	}

	public String waitUntilProviderConfigurationPopulated() {
		return new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			String configuration = getTextFromLocator(providerConfigurationValue);
			if (configuration == null) {
				return null;
			}
			configuration = configuration.trim();
			return configuration.isEmpty() || GlobalConstants.AUTO_POPULATED_BASED_ON_CONFIGURATION.equals(configuration)
					? null
					: configuration;
		});
	}

	public boolean areAutoPopulatedFieldsReadOnly() {
		return isElementDisabled(partnerIdValue) && isElementDisabled(partnerTypeValue)
				&& isElementDisabled(policyGroupValue) && isElementDisabled(policyNameValue)
				&& isElementDisabled(biometricModalityValue) && isElementDisabled(providerConfigurationValue);
	}

	public boolean isSubmitDisabled() {
		return isElementDisabled(submitButton);
	}

	public String getCredentialTypeText() {
		return getTextFromLocator(credentialTypeDropdown).trim();
	}

	public List<String> getCredentialTypeOptions() {
		openCredentialTypeOptions();
		List<String> options = new ArrayList<>();
		for (WebElement option : credentialTypeOptions) {
			scrollIntoView(option);
			options.add(getTextFromLocator(option).trim());
		}
		return options;
	}

	public void selectCredentialType(String credentialType) {
		List<String> options = getCredentialTypeOptions();
		for (WebElement option : credentialTypeOptions) {
			if (credentialType.equals(getTextFromLocator(option).trim())) {
				clickOnElement(option);
				return;
			}
		}
		throw new NoSuchElementException(
				"Credential type was not offered: " + credentialType + ". Options found: " + options);
	}

	private void openCredentialTypeOptions() {
		clickOnElement(credentialTypeDropdown);
		new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> credentialTypeOptions.isEmpty() ? null
				: Integer.valueOf(credentialTypeOptions.size()));
	}

	public void clickClearForm() {
		clickOnElement(clearButton);
	}

	public void clickCancel() {
		clickOnElement(cancelButton);
	}

	public void clickSubmit() {
		clickOnElement(submitButton);
	}

	public String getAcknowledgementHeader() {
		return getTextFromLocator(acknowledgementHeader).trim();
	}

	public String getAcknowledgementDescription() {
		return getTextFromLocator(acknowledgementDescription).replaceAll("\\s+", " ").trim();
	}

	public void clickGoBack() {
		clickOnElement(goBackButton);
	}

	public void clickHome() {
		clickOnElement(homeButton);
	}

	public void clickTitleBack() {
		clickOnElement(titleBackIcon);
	}

	public void filterByPartnerId(String partnerId) {
		clickOnElement(filterButton);
		enter(partnerIdFilter, partnerId);
		clickOnElement(applyFilterButton);
	}

	public void filterByPartnerIdAndPolicyName(String partnerId, String policyName) {
		clickOnElement(filterButton);
		enter(partnerIdFilter, partnerId);
		enter(policyNameFilter, policyName);
		clickOnElement(applyFilterButton);
	}

	public String waitUntilFirstPolicyRequestRowContains(String partnerId) {
		return waitUntilFirstPolicyRequestRowContains(partnerId, null);
	}

	public String waitUntilFirstPolicyRequestRowContains(String partnerId, String expectedText) {
		return new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			if (!isElementDisplayedQuick(firstPolicyRequestRow, ABSENCE_CHECK_TIMEOUT)) {
				return null;
			}
			String row = getTextFromLocator(firstPolicyRequestRow);
			if (row == null || !row.contains(partnerId)) {
				return null;
			}
			row = row.trim();
			return expectedText == null || row.contains(expectedText) ? row : null;
		});
	}

	public void openFirstPolicyRequestAction() {
		clickOnElement(firstPolicyRequestAction);
	}

	public String waitUntilMapCredentialTypeActionEnabled() {
		return new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			if (!isElementDisplayedQuick(mapCredentialTypeAction, ABSENCE_CHECK_TIMEOUT)) {
				return null;
			}
			String className = getTextFromAttribute(mapCredentialTypeAction, GlobalConstants.CLASS);
			return className != null && !className.contains("pointer-events-none") ? "enabled" : null;
		});
	}

	public String waitUntilMapCredentialTypeActionDisabled() {
		return new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			if (!isElementDisplayedQuick(mapCredentialTypeAction, ABSENCE_CHECK_TIMEOUT)) {
				return null;
			}
			String className = getTextFromAttribute(mapCredentialTypeAction, GlobalConstants.CLASS);
			return className != null && className.contains("pointer-events-none") ? "disabled" : null;
		});
	}

	public void openMapCredentialTypeAction() {
		clickOnElement(mapCredentialTypeAction);
	}
}
