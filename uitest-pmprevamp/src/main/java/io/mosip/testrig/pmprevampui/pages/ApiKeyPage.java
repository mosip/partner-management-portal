package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ApiKeyPage extends BasePage {

	@FindBy(xpath = "Enter a name for API key")
	private WebElement enterNameOfApiKeyTextBox;

	@FindBy(xpath = "generate_submit_btn")
	private WebElement submitButton;

	@FindBy(id = "generate_partner_id")
	private WebElement partnerIdDropdown;

	@FindBy(id = "generate_policy_name")
	private WebElement policyNameDropdown;

	@FindBy(id = "create_policy_group_btn")
	private WebElement generateAPIKey;

	@FindBy(xpath = "//*[contains(text(), 'Partner ID')]")
	private WebElement partnerIDHeaderText;

	@FindBy(xpath = "//*[contains(text(), 'Policy Group')]")
	private WebElement PolicyGroupHeaderText;

	@FindBy(xpath = "//*[contains(text(), 'Policy Name')]")
	private WebElement PolicyNameHeaderText;

	@FindBy(xpath = "//*[contains(text(), 'API Key Name')]")
	private WebElement ApiKeyHeaderText;

	@FindBy(xpath = "//*[contains(text(), 'Created Date')]")
	private WebElement CreatedDateHeaderText;

	@FindBy(xpath = "//*[contains(text(), 'Status')]")
	private WebElement StatusHeaderText;

	@FindBy(xpath = "//*[contains(text(), 'Action')]")
	private WebElement ActionHeaderText;

	@FindBy(id = "generate_partner_id_option1")
	private WebElement generatePartnerIdOption1;

	@FindBy(id = "copy_id_btn")
	private WebElement copyIdButton;
	
	@FindBy(id = "copy_id_close_btn")
	private WebElement copyIdCloseButton;
	
	@FindBy(id = "confirmation_go_back_btn")
	private WebElement confirmationGoBackButton;
	
	@FindBy(id = "api_list_item1")
	private WebElement apiListItem1;
	
	@FindBy(id = "api_list_action1")
	private WebElement apiListAction;
	
	@FindBy(id = "api_key_view")
	private WebElement apiKeyView;
	
	@FindBy(id = "partnerId_desc_icon")
	private WebElement partnerId_desc_icon;

	@FindBy(id = "partnerId_asc_icon")
	private WebElement partnerId_asc_icon;

	@FindBy(id = "apiKeyLabel_desc_icon")
	private WebElement apiKeyLabel_desc_icon;

	@FindBy(id = "apiKeyLabel_asc_icon")
	private WebElement apiKeyLabel_asc_icon;

	@FindBy(id = "policyGroupName_desc_icon")
	private WebElement policyGroupName_desc_icon;

	@FindBy(id = "policyGroupName_asc_icon")
	private WebElement policyGroupName_asc_icon;

	@FindBy(id = "policyName_desc_icon")
	private WebElement policyName_desc_icon;

	@FindBy(id = "policyName_asc_icon")
	private WebElement policyName_asc_icon;

	@FindBy(id = "createdDateTime_desc_icon")
	private WebElement createdDateTime_desc_icon;

	@FindBy(id = "createdDateTime_asc_icon")
	private WebElement createdDateTime_asc_icon;

	@FindBy(id = "status_desc_icon")
	private WebElement status_desc_icon;

	@FindBy(id = "status_asc_icon")
	private WebElement status_asc_icon;

	@FindBy(id = "api_key_deactivate")
	private WebElement apiKeyDeactivate;
	
	@FindBy(id = "filter_btn")
	private WebElement filterButton;
	
	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;
	
	@FindBy(id = "api_key_partner_id_filter")
	private WebElement apiKeyPartnerIdFilter;
	
	@FindBy(id = "api_key_partner_id_filter_option1")
	private WebElement apiKeyPartnerIdFilterOption1;
	
	@FindBy(id = "api_key_policy_group_filter")
	private WebElement apiKeyPolicyGroupFilter;
	
	@FindBy(id = "api_key_policy_group_filter_option1")
	private WebElement apiKeyPolicyGroupFilterOption1;
	
	@FindBy(id = "api_key_policy_name_filter")
	private WebElement apiKeyPolicyNameFilter;
	
	@FindBy(id = "api_key_policy_name_filter_option1")
	private WebElement apiKeyPolicyNameFilterOption1;
	
	@FindBy(id = "api_key_name_filter")
	private WebElement apiKeyNameFilter;
	
	@FindBy(id = "api_key_name_filter_option1")
	private WebElement apiKeyNameFilterOption1;
	
	@FindBy(id = "api_key_status_filter")
	private WebElement apiKeyStatusFilter;
	
	@FindBy(id = "api_key_status_filter")
	private WebElement apiKeyStatusFilterOption1;
	
	public ApiKeyPage(WebDriver driver) {
		super(driver);
	}

	public void enterNameOfApiKeyTextBox(String value) {
		enter(enterNameOfApiKeyTextBox, value);
	}

	public void selectPartnerIdDropdown(String value) {
		clickOnElement(partnerIdDropdown);
		clickOnElement(generatePartnerIdOption1);
	}

	public boolean isPartnerIdDropdownDisplayed() {
		return isElementDisplayed(partnerIdDropdown);
	}

	public boolean isPolicyNameDropdownDisplayed() {
		return isElementDisplayed(policyNameDropdown);
	}

	public void selectPolicyNameDropdown(String value) {
		dropdownByIndex(policyNameDropdown, 0);
	}

	public boolean isGenerateAPIKeyDisplayed() {
		return isElementDisplayed(generateAPIKey);
	}

	public void ClickOnAPIKeyDisplayed() {
		clickOnElement(generateAPIKey);
	}

	public void ClickOnSubmitButton() {
		clickOnElement(submitButton);
	}

	public boolean isPartnerIDHeaderTextDisplayed() {
		return isElementDisplayed(partnerIDHeaderText);
	}

	public boolean isPolicyGroupHeaderTextDisplayed() {
		return isElementDisplayed(PolicyGroupHeaderText);
	}

	public boolean isPolicyNameHeaderTextDisplayed() {
		return isElementDisplayed(PolicyNameHeaderText);
	}

	public boolean isApiKeyHeaderTextDisplayed() {
		return isElementDisplayed(ApiKeyHeaderText);
	}

	public boolean isCreatedDateHeaderTextDisplayed() {
		return isElementDisplayed(CreatedDateHeaderText);
	}

	public boolean isStatusHeaderTextDisplayed() {
		return isElementDisplayed(StatusHeaderText);
	}

	public boolean isActionHeaderTextDisplayed() {
		return isElementDisplayed(ActionHeaderText);
	}

	public void clickOnCopyIdButton() {
		clickOnElement(copyIdButton);
	}
	
	public void clickOnCopyIdCloseButton() {
		clickOnElement(copyIdCloseButton);
	}
	
	
	public boolean isConfirmationGoBackButtonDisplayed() {
		return isElementDisplayed(confirmationGoBackButton);
	}
	
	public void clickOnConfirmationGoBackButton() {
		clickOnElement(confirmationGoBackButton);
	}
	
	public boolean isApiListItem1Displayed() {
		return isElementDisplayed(apiListItem1);
	}
	
	public void clickOnapiListActionButton() {
		clickOnElement(apiListAction);
	}
	
	public void clickOnApiKeyViewButton() {
		clickOnElement(apiKeyView);
	}
	
	public boolean isPartnerIdDescIconDisplayed() {
		return isElementDisplayed(partnerId_desc_icon);
	}

	public boolean isPartnerIdAscIconDisplayed() {
		return isElementDisplayed(partnerId_asc_icon);
	}

	public boolean isApiKeyLabelDescIconDisplayed() {
		return isElementDisplayed(apiKeyLabel_desc_icon);
	}

	public boolean isApiKeyLabelAscIconDisplayed() {
		return isElementDisplayed(apiKeyLabel_asc_icon);
	}

	public boolean isPolicyGroupNameDescIconDisplayed() {
		return isElementDisplayed(policyGroupName_desc_icon);
	}

	public boolean isPolicyGroupNameAscIconDisplayed() {
		return isElementDisplayed(policyGroupName_asc_icon);
	}

	public boolean isPolicyNameDescIconDisplayed() {
		return isElementDisplayed(policyName_desc_icon);
	}

	public boolean isPolicyNameAscIconDisplayed() {
		return isElementDisplayed(policyName_asc_icon);
	}

	public boolean isCreatedDateTimeDescISconDisplayed() {
		return isElementDisplayed(createdDateTime_desc_icon);
	}

	public boolean isCreatedDateTimeAscIconDisplayed() {
		return isElementDisplayed(createdDateTime_asc_icon);
	}

	public boolean isStatusDescIconDisplayed() {
		return isElementDisplayed(status_desc_icon);
	}

	public boolean isStatusAscIconDisplayed() {
		return isElementDisplayed(status_asc_icon);
	}
	
	public boolean isFilterButtonButtonEnabled() {
		return isElementEnabled(filterButton);
	}

	public void clickOnFilterButton() {
		clickOnElement(filterButton);
	}
	
	public void clickOnFilterResetButton() {
		clickOnElement(filterResetButton);
	}
	
	public void clickOnApiKeyPartnerIdFilter() {
		clickOnElement(apiKeyPartnerIdFilter);
		clickOnElement(apiKeyPartnerIdFilterOption1);
	}
	
	public void clickOnApiKeySelectPolicyGroupFilter() {
		clickOnElement(apiKeyPolicyGroupFilter);
		clickOnElement(apiKeyPolicyGroupFilterOption1);
	}
	
	public void clickOnApiKeySelectPolicyNameFilter() {
		clickOnElement(apiKeyPolicyNameFilter);
		clickOnElement(apiKeyPolicyNameFilterOption1);
	}
	
	public void clickOnApiKeySelectClientNameFilter() {
		clickOnElement(apiKeyNameFilter);
		clickOnElement(apiKeyNameFilterOption1);
	}
	
	public void clickOnApiKeySelectStatusFilter() {
		clickOnElement(apiKeyStatusFilter);
		clickOnElement(apiKeyStatusFilterOption1);
	}
}
