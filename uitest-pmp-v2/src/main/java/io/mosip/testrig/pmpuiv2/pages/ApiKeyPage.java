package io.mosip.testrig.pmpuiv2.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;

public class ApiKeyPage extends BasePage {

	@FindBy(id = "generate_api_key_name")
	private WebElement enterNameOfApiKeyTextBox;

	@FindBy(id = "generate_submit_btn")
	private WebElement submitButton;

	@FindBy(id = "generate_partner_id_dropdown_btn")
	private WebElement partnerIdDropdown;

	@FindBy(id = "generate_policy_name_dropdown_btn")
	private WebElement policyNameDropdown;

	@FindBy(id = "generate_policy_name_search_input")
	private WebElement generatePolicyNameSearchInputBox;

	@FindBy(id = "generate_policy_name_option1")
	private WebElement generatePolicyNameOption1;

	@FindBy(id = "generate_api_key")
	private WebElement generateApiKey;

	@FindBy(xpath = "(//*[@id='columnheaderName'])[1]")
	private WebElement partnerIDHeaderText;

	@FindBy(xpath = "(//*[@id='columnheaderName'])[2]")
	private WebElement PolicyGroupHeaderText;

	@FindBy(xpath = "(//*[@id='columnheaderName'])[3]")
	private WebElement PolicyNameHeaderText;

	@FindBy(xpath = "(//*[@id='columnheaderName'])[4]")
	private WebElement ApiKeyHeaderText;

	@FindBy(xpath = "(//*[@id='columnheaderName'])[5]")
	private WebElement CreatedDateHeaderText;

	@FindBy(xpath = "(//*[@id='columnheaderName'])[6]")
	private WebElement StatusHeaderText;

	@FindBy(xpath = "(//*[@id='columnheaderName'])[7]")
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

	@FindBy(id = "orgName_asc_icon")
	private WebElement orgName_asc_icon;

	@FindBy(id = "orgName_desc_icon")
	private WebElement orgName_desc_icon;

	@FindBy(id = "apiKeyLabel_desc_icon")
	private WebElement apiKeyName_desc_icon;

	@FindBy(id = "apiKeyLabel_asc_icon")
	private WebElement apiKeyName_asc_icon;

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

	@FindBy(id = "api_key_partner_id_filter_dropdown_btn")
	private WebElement apiKeyPartnerIdFilter;

	@FindBy(id = "api_key_partner_id_filter_option1")
	private WebElement apiKeyPartnerIdFilterOption1;

	@FindBy(id = "api_key_policy_group_filter_dropdown_btn")
	private WebElement apiKeyPolicyGroupFilter;

	@FindBy(id = "api_key_policy_group_filter_option1")
	private WebElement apiKeyPolicyGroupFilterOption1;

	@FindBy(id = "api_key_policy_name_filter_dropdown_btn")
	private WebElement apiKeyPolicyNameFilter;

	@FindBy(id = "api_key_policy_name_filter_option1")
	private WebElement apiKeyPolicyNameFilterOption1;

	@FindBy(id = "api_key_name_filter_dropdown_btn")
	private WebElement apiKeyNameFilter;

	@FindBy(id = "api_key_name_filter")
	private WebElement apiKeyNameFilterInAdmin;

	@FindBy(id = "api_key_name_filter_option1")
	private WebElement apiKeyNameFilterOption1;

	@FindBy(id = "api_key_status_filter_dropdown_btn")
	private WebElement apiKeyStatusFilter;

	@FindBy(id = "api_key_status_filter_option2")
	private WebElement apiKeyStatusFilterOption2;

	@FindBy(id = "title_back_icon")
	private WebElement backicon;

	@FindBy(id = "sub_title_btn")
	private WebElement subTitleButton;

	@FindBy(id = "api_key_details_partner_id_label")
	private WebElement apiKeyDetailsPartnerIdLabel;

	@FindBy(id = "api_key_details_partner_id_context")
	private WebElement apiKeyDetailsPartnerIdContext;

	@FindBy(id = "api_key_details_policy_group_label")
	private WebElement apiKeyDetailsPolicyGroupLabel;

	@FindBy(id = "api_key_details_policy_group_name_context")
	private WebElement apiKeyDetailsPolicyGroupNameContext;

	@FindBy(id = "api_key_details_policy_name_label")
	private WebElement apiKeyDetailsPolicyNameLabel;

	@FindBy(id = "api_key_details_policy_name_context")
	private WebElement apiKeyDetailsPolicyNameContext;

	@FindBy(id = "api_key_details_policy_group_description_label")
	private WebElement apiKeyDetailsPolicyGoupDescriptionLabel;

	@FindBy(id = "api_key_details_policy_group_description_context")
	private WebElement apiKeyDetailsPolicyGroupDescriptionContext;

	@FindBy(id = "api_key_details_policy_name_description_label")
	private WebElement apiKeyDetailsPolicyNameDescriptionLabel;

	@FindBy(id = "api_key_details_policy_description_context")
	private WebElement apiKeyDetailsPolicyDescriptionContext;

	@FindBy(id = "api_key_details_api_key_name_label")
	private WebElement apiKeyDetailsApiKeyNameLabel;

	@FindBy(id = "api_key_details_api_key_label_context")
	private WebElement apiKeyDetailsApiKeyLabelContext;

	@FindBy(id = "view_api_key_back_btn")
	private WebElement viewApiKeyBackButton;

	@FindBy(xpath = "//p[text()='No Data Available.']")
	private WebElement noDataAvailableText;

	@FindBy(id = "generate_api_key_btn")
	private WebElement apiKeyListPageGenerateApiKeyBtn;

	@FindBy(xpath = "//span[text()='Select Partner ID']")
	private WebElement partnerIdHelpText;

	@FindBy(xpath = "//span[contains(text(), 'Select policy for which API Key is required')]")
	private WebElement policyNameHelpText;

	@FindBy(id = "generate_clear_form")
	private WebElement clearButton;

	@FindBy(id = "generate_cancel_btn")
	private WebElement cancelButton;

	@FindBy(id = "generate_api_key_error_msg")
	private WebElement duplicateApiKeyNameErrorMessage;

	@FindBy(id = "error_close_btn")
	private WebElement duplicateApiKeyNameErrorMessageCloseButton;

	@FindBy(id = "confirmation_home_btn")
	private WebElement confirmationHomeButton;

	@FindBy(xpath = "//p[contains(text(), 'Do you want to Deactivate API Key')]")
	private WebElement apiKeyDeactivateTitle;

	@FindBy(xpath = "//p[contains(text(), 'On clicking Confirm, you will not be able to use the API Key for authentication anymore.')]")
	private WebElement apiKeyDeactivationInfoText;

	@FindBy(id = "deactivate_cancel_btn")
	private WebElement deactivateCancelButton;

	@FindBy(id = "deactivate_submit_btn")
	private WebElement deactivateConfirmButton;

	@FindBy(id = "api_list_item1")
	private WebElement deactivatedApiKey;

	@FindBy(id = "api_list_item1")
	private WebElement activatedApiKey;

	@FindBy(id = "sub_title_home_btn")
	private WebElement breadcomb;

	@FindBy(xpath = "//div[text()='Deactivated']")
	private WebElement apiKeyStatus;

	@FindBy(xpath = "//p[text()='No Data Available.']")
	private WebElement noDataInApiKeyFilterDropdown;

	@FindBy(xpath = "//h6[text()='Items per page']")
	private WebElement itemsPerPagePrefix;

	@FindBy(id = "pagination_select_record_per_page")
	private WebElement itemsPerPageDropdown;

	@FindBy(id = "pagination_each_num_option2")
	private WebElement recordNumber;

	@FindBy(id = "copy_id_btn")
	private WebElement copyIdPopup;

	@FindBy(id = "api_key_status_filter_option1")
	private WebElement apiKeyStatusFilterOption1;

	@FindBy(id = "api_key_name_filter_search_input")
	private WebElement apiKeySearchTextBox;

	@FindBy(xpath = "//button[text()='Select Status']")
	private WebElement clearStatus;

	@FindBy(xpath = "//p[contains(text(), 'List of API Keys')]")
	private WebElement subTitleOfTabularView;

	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;

	@FindBy(xpath = "//div[text()='Partner ID']")
	private WebElement partnerIdColumnHeader;

	@FindBy(xpath = "//div[text()='Organisation']")
	private WebElement organisationColumnHeader;

	@FindBy(xpath = "//div[text()='Policy Group']")
	private WebElement policyGroupColumnHeader;

	@FindBy(xpath = "//div[text()='Policy Name']")
	private WebElement policyNameColumnHeader;

	@FindBy(xpath = "//div[text()='API Key Name']")
	private WebElement apiKeyNameColumnHeader;

	@FindBy(xpath = "//div[text()='Creation Date']")
	private WebElement creationDateColumnHeader;

	@FindBy(xpath = "//div[text()='Status']")
	private WebElement statusColumnHeader;

	@FindBy(xpath = "//div[text()='OIDC Client ID']")
	private WebElement oidcClientIdColumnHeader;

	@FindBy(xpath = "//div[text()='Action']")
	private WebElement actionColumnHeader;

	@FindBy(xpath = "//p[text()='Partner ID']")
	private WebElement partnerIdFilterHeader;

	@FindBy(xpath = "//p[text()='Organisation']")
	private WebElement organisationFilterHeader;

	@FindBy(xpath = "//p[text()='Policy Group']")
	private WebElement policyGroupFilterHeader;

	@FindBy(xpath = "//p[text()='Policy Name']")
	private WebElement policyNameFilterHeader;

	@FindBy(xpath = "//p[text()='API Key Name']")
	private WebElement apiKeyNameFilterHeader;

	@FindBy(xpath = "//p[text()='Status']")
	private WebElement statusFilterHeader;

	@FindBy(xpath = "//input[@placeholder='Search Partner ID']")
	private WebElement partnerIdPlaceHolder;

	@FindBy(xpath = "//input[@placeholder='Search Organisation']")
	private WebElement organisationPlaceHolder;

	@FindBy(xpath = "//input[@placeholder='Search Policy Group']")
	private WebElement policyGroupPlaceHolder;

	@FindBy(xpath = "//input[@placeholder='Search Policy Name']")
	private WebElement policyNamePlaceHolder;

	@FindBy(xpath = "//input[@placeholder='Search API Key Name']")
	private WebElement apiKeyNamePlaceHolder;

	@FindBy(xpath = "//span[text()='Select Status']")
	private WebElement statusPlaceHolder;

	@FindBy(id = "apply_filter_btn")
	private WebElement applyFilterButton;

	@FindBy(xpath = "//p[text()='No Results Found']")
	private WebElement noResultFound;

	@FindBy(id = "status_filter_option1")
	private WebElement activatedStatusInFilter;

	@FindBy(id = "status_filter_option2")
	private WebElement deactivatedStatusInFilter;

	@FindBy(id = "status_filter_dropdown_btn")
	private WebElement statusFilter;

	@FindBy(id = "policy_group_filter")
	private WebElement policyGroupFilter;

	@FindBy(xpath = "//button[text()='x']")
	private WebElement filterCloseButton;

	@FindBy(id = "api_key_list_action_view1")
	private WebElement actionButton;

	@FindBy(id = "api_key_list_view_btn")
	private WebElement viewButton;

	@FindBy(id = "api_key_list_deactivate_btn")
	private WebElement deactivateButton;

	@FindBy(id = "api_key_list_item1")
	private WebElement apiKeyItem1;

	@FindBy(xpath = "//h1[text()='View API Key Details']")
	private WebElement apiKeyTitle;

	@FindBy(xpath = "//div[text()='Deactivated']")
	private WebElement deactivatedStatus;

	@FindBy(xpath = "//div[text()='Active']")
	private WebElement activatedStatus;

	@FindBy(xpath = "//p[text()='API Key Name']")
	private WebElement apiKeyNameLabel;

	@FindBy(id = "api_key_details_partner_type_label")
	private WebElement apiKeyDetailsPartnerTypeLabel;

	@FindBy(id = "api_key_details_auth_partner_context")
	private WebElement apiKeyDetailsPartnerTypeContext;

	@FindBy(xpath = "//div[contains(text(), 'Created On')]")
	private WebElement createdOnLabel;

	@FindBy(xpath = "//span[contains(text(), 'Invalid character. Allowed special characters')]")
	private WebElement specialCharacterErrorMessage;

	@FindBy(id = "partner_id_filter")
	private WebElement partnerIdFilter;

	@FindBy(xpath = "//p[contains(text(), 'On clicking ‘Confirm’, the API Key will be deactivated and can no longer be used for authentication')]")
	private WebElement apiKeyInAdminDeactivateInfoText;

	@FindBy(xpath = "//p[contains(text(), 'Are you sure you want to Deactivate API Key')]")
	private WebElement apiKeyInAdminDeactivateTitle;

	@FindBy(id = "api_key_list_item1")
	private WebElement activatedAdminApiKey;

	@FindBy(id = "apiKeyExpiryDateTime_desc_icon")
	private WebElement apiKeyExpiryDateTime_desc_icon;

	@FindBy(id = "apiKeyExpiryDateTime_asc_icon")
	private WebElement apiKeyExpiryDateTime_asc_icon;

	public ApiKeyPage(WebDriver driver) {
		super(driver);
	}

	private static final String CREATION_DATE_HEADER_ID = "oidcClientsList.creationDate_header";
	private static final String EXPIRATION_DATE_HEADER_ID = "apiKeysList.expirationDate_header";

	private static final By EXPIRATION_DATE_HEADER = By.xpath("//*[@id='" + EXPIRATION_DATE_HEADER_ID + "']");
	private static final By EXPIRATION_DATE_LABEL = By.id("api_key_details_expiration_date_label");
	private static final By EXPIRATION_DATE_CONTEXT = By.id("api_key_details_expiration_date_context");
	private static final By API_KEY_DETAILS_TITLE = By.xpath("//h1[text()='View API Key Details']");

	private static final By ADMIN_EXPIRY_CELL = By.xpath("//tr[starts-with(@id,'api_key_list_item')][1]/td[7]");
	private static final By PARTNER_EXPIRY_CELL = By.xpath("//tr[starts-with(@id,'api_list_item')][1]/td[6]");
	private static final By ADMIN_CREATED_CELL = By.xpath("//tr[starts-with(@id,'api_key_list_item')][1]/td[6]");
	private static final By PARTNER_CREATED_CELL = By.xpath("//tr[starts-with(@id,'api_list_item')][1]/td[5]");
	private static final By ADMIN_EXPIRY_COLUMN = By.xpath("//tr[starts-with(@id,'api_key_list_item')]/td[7]");
	private static final By PARTNER_EXPIRY_COLUMN = By.xpath("//tr[starts-with(@id,'api_list_item')]/td[6]");

	private static final By ADMIN_FIRST_ROW = By.id("api_key_list_item1");
	private static final By PARTNER_FIRST_ROW = By.id("api_list_item1");

	private static final By INDIVIDUAL_VIEW_LABELS = By
			.xpath("//p[starts-with(@id,'api_key_details_') and contains(@id,'_label')]");

	private static final String NO_EXPIRY_TEXT = "No Expiry";

	private static final Logger logger = Logger.getLogger(ApiKeyPage.class);

	public void enterNameOfApiKeyTextBox(String apiKeyTextBoxValue) {
		enter(enterNameOfApiKeyTextBox, apiKeyTextBoxValue);
	}

	public void selectPartnerIdDropdown() {
		clickOnElement(partnerIdDropdown);
		clickOnElement(generatePartnerIdOption1);
	}

	public void ClickOnPartnerIdDropdown() {
		clickOnElement(partnerIdDropdown);
	}

	public boolean isPartnerIdDropdownDisplayed() {
		return isElementDisplayed(partnerIdDropdown);
	}

	public boolean isPolicyNameDropdownDisplayed() {
		return isElementDisplayed(policyNameDropdown);
	}

	public boolean selectPolicyNameDropdown(String value) {
		By policyNameDropdown = By.id("generate_policy_name_dropdown_btn");
		By noDataText = By.xpath("//*[text()='No Data Available']");
		By policyOptions = By.xpath("//div[@role='listbox']//button");
		By policyNameOption = By.xpath("//button[.//span[normalize-space()='" + value + "']]");

		click(policyNameDropdown);
		enter(generatePolicyNameSearchInputBox, value);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {
			// wait until either data loads OR "No Data Available" disappears
			wait.until(ExpectedConditions.or(ExpectedConditions.invisibilityOfElementLocated(noDataText),
					ExpectedConditions.numberOfElementsToBeMoreThan(policyOptions, 0)));

			wait.until(ExpectedConditions.elementToBeClickable(policyNameOption)).click();
			return true;

		} catch (TimeoutException e) {
			logger.warn("Policy name not found or not loaded: " + value);
			return false;
		}
	}

	public void enterDeactivePolicyNameInDropdown(String value) {
		clickOnElement(policyNameDropdown);
		enter(generatePolicyNameSearchInputBox, value);
	}

	public boolean isGenerateApiKeyDisplayed() {
		return isElementDisplayed(generateApiKey);
	}

	public void clickOnCreateApiKey() {
		if (isElementDisplayed(apiKeyListPageGenerateApiKeyBtn)) {
			clickOnElement(apiKeyListPageGenerateApiKeyBtn);
		} else if (isElementDisplayed(generateApiKey)) {
			clickOnElement(generateApiKey);
		} else {
			throw new RuntimeException("Create API Key button is not visible on the page");
		}
	}

	public void clickOnSubmitButton() {
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

	public void clickOnapiListElipsisButton() {
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

	public boolean isApiKeyNameDescIconDisplayed() {
		return isElementDisplayed(apiKeyName_desc_icon);
	}

	public boolean isApiKeyNameAscIconDisplayed() {
		return isElementDisplayed(apiKeyName_asc_icon);
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

	public boolean isCreatedDateTimeDescIconDisplayed() {
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

	public boolean isOrgNameDescIconDisplayed() {
		return isElementDisplayed(orgName_desc_icon);
	}

	public boolean isOrgNameAscIconDisplayed() {
		return isElementDisplayed(orgName_asc_icon);
	}

	public boolean isFilterButtonEnabled() {
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

	public boolean isBackiconDisplayed() {
		return isElementDisplayed(backicon);
	}

	public boolean isListOfApiKeysButtonDisplayed() {
		return isElementDisplayed(subTitleButton);
	}

	public boolean isApiKeyDetailsPartnerIdLabelDisplayed() {
		return isElementDisplayed(apiKeyDetailsPartnerIdLabel);
	}

	public boolean isApiKeyDetailsPartnerIdContextDisplayed() {
		return isElementDisplayed(apiKeyDetailsPartnerIdContext);
	}

	public boolean isApiKeyDetailsPolicyGroupLabelDisplayed() {
		return isElementDisplayed(apiKeyDetailsPolicyGroupLabel);
	}

	public boolean isApiKeyDetailsPolicyGroupNameContextDisplayed() {
		return isElementDisplayed(apiKeyDetailsPolicyGroupNameContext);
	}

	public boolean isApiKeyDetailsPolicyNameLabelDisplayed() {
		return isElementDisplayed(apiKeyDetailsPolicyNameLabel);
	}

	public boolean isApiKeyDetailsPolicyNameContextDisplayed() {
		return isElementDisplayed(apiKeyDetailsPolicyNameContext);
	}

	public boolean isApiKeyDetailsPolicyGoupDescriptionLabelDisplayed() {
		return isElementDisplayed(apiKeyDetailsPolicyGoupDescriptionLabel);
	}

	public boolean isApiKeyDetailsPolicyGroupDescriptionContextDisplayed() {
		return isElementDisplayed(apiKeyDetailsPolicyGroupDescriptionContext);
	}

	public boolean isApiKeyDetailsPolicyNameDescriptionLabelDisplayed() {
		return isElementDisplayed(apiKeyDetailsPolicyNameDescriptionLabel);
	}

	public boolean isApiKeyDetailsPolicyDescriptionContextDisplayed() {
		return isElementDisplayed(apiKeyDetailsPolicyDescriptionContext);
	}

	public boolean isApiKeyDetailsApiKeyNameLabelDisplayed() {
		return isElementDisplayed(apiKeyDetailsApiKeyNameLabel);
	}

	public boolean isApiKeyDetailsApiKeyLabelContextDisplayed() {
		return isElementDisplayed(apiKeyDetailsApiKeyLabelContext);
	}

	public boolean isViewApiKeyBackButtonDisplayed() {
		return isElementDisplayed(viewApiKeyBackButton);
	}

	public void clickOnViewApiKeyBackButton() {
		clickOnElement(viewApiKeyBackButton);
	}

	public boolean isNoDataAvailableTextDisplayed() {
		return isElementDisplayed(noDataAvailableText);
	}

	public boolean isApiKeyListPageGenerateApiKeyBtnDisplayed() {
		return isElementDisplayed(apiKeyListPageGenerateApiKeyBtn);
	}

	public void clickOnApiKeyListPageGenerateApiKeyBtn() {
		clickOnElement(apiKeyListPageGenerateApiKeyBtn);
	}

	public boolean isPartnerIdHelpTextDisplayed() {
		return isElementDisplayed(partnerIdHelpText);
	}

	public boolean isPolicyNameHelpTextDisplayed() {
		return isElementDisplayed(policyNameHelpText);
	}

	public boolean isClearButtonDisplayed() {
		return isElementDisplayed(clearButton);
	}

	public void clickOnClearButton() {
		clickOnElement(clearButton);
	}

	public boolean isCancelButtonDisplayed() {
		return isElementDisplayed(cancelButton);
	}

	public void clickOnCancelButton() {
		clickOnElement(cancelButton);
	}

	public boolean isDuplicateApiKeyNameErrorMessageDisplayed() {
		return isElementDisplayed(duplicateApiKeyNameErrorMessage);
	}

	public void clickOnDuplicateApiKeyNameErrorMessageCloseButton() {
		clickOnElement(duplicateApiKeyNameErrorMessageCloseButton);
	}

	public void clickOnConfirmationHomeButton() {
		clickOnElement(confirmationHomeButton);
	}

	public void clickOnTitleBackButton() {
		clickOnElement(backicon);
	}

	public void clickOnDeactivateButton() {
		clickOnElement(apiKeyDeactivate);
	}

	public boolean isApiKeyDeactivatePopupDisplayed() {
		return isElementDisplayed(apiKeyDeactivateTitle);
	}

	public boolean isApiKeyDeactivateTitleDisplayed() {
		return isElementDisplayed(apiKeyDeactivateTitle);
	}

	public boolean isApiKeyDeactivationInfoTextDisplayed() {
		return isElementDisplayed(apiKeyDeactivationInfoText);
	}

	public boolean isDeactivateCancelButtonAvailable() {
		return isElementDisplayed(deactivateCancelButton);
	}

	public boolean isDeactivateSubmitButtonAvailable() {
		return isElementDisplayed(deactivateConfirmButton);
	}

	public void clickOnDeactivateCancelButton() {
		clickOnElement(deactivateCancelButton);
	}

	public void clickOnDeactivateSubmitButton() {
		clickOnElement(deactivateConfirmButton);
	}

	public void clickOnDeactivatedApiKey() {
		clickOnElement(deactivatedApiKey);
	}

	public void clickOnActivatedApiKey() {
		clickOnElement(activatedApiKey);
	}

	public boolean isDeactivatedApiKeyGreyColored() {
		return isElementDisplayed(deactivatedApiKey);
	}

	public boolean isDeactivatedApiKeyDisabled() {
		return isElementDisplayed(deactivatedApiKey);
	}

	public boolean isBreadcombDisplayed() {
		return isElementDisplayed(breadcomb);
	}

	public void clickOnBreadcomb() {
		clickOnElement(breadcomb);
	}

	public void clickOnApiKeyNameAscIcon() {
		clickOnElement(apiKeyName_asc_icon);
	}

	public void clickOnApiKeyNameDescIcon() {
		clickOnElement(apiKeyName_desc_icon);
	}

	public boolean isApiKeyStatusDeactivatedDisplayed() {
		return isElementDisplayed(deactivatedStatus);
	}

	public boolean isApiKeyStatusActivatedDisplayed() {
		return isElementDisplayed(activatedStatus);
	}

	public void clickOnApiListItem1() {
		clickOnElement(apiListItem1);
	}

	public boolean isSubmitButtonEnabled() {
		return isElementDisplayed(submitButton);
	}

	public void enterInvalidDataInApiKeyNameFilter(String value) {
		clickOnElement(apiKeyNameFilter);
		enter(apiKeySearchTextBox, value);
	}

	public boolean isNoDataAvailabelDisplayed() {
		return isElementDisplayed(noDataInApiKeyFilterDropdown);
	}

	public void unSelectApiKeyNameFilter() {
		clickOnElement(apiKeyNameFilter);
	}

	public boolean isItemsPerPageDisplayed() {
		return isElementDisplayed(itemsPerPagePrefix);
	}

	public boolean isItemsPerPageDropdownAvailable() {
		return isElementDisplayed(itemsPerPageDropdown);
	}

	public void clickOnItemsPerPageDropdown() {
		clickOnElement(itemsPerPageDropdown);
	}

	public void selectNumberOfRecordPerPage() {
		clickOnElement(recordNumber);
	}

	public boolean isCopyIdPopupDisplayed() {
		return isElementDisplayed(copyIdPopup);
	}

	public void clickOnActivatedStatusApiKeyFilter() {
		clickOnElement(apiKeyStatusFilter);
		clickOnElement(apiKeyStatusFilterOption2);
	}

	public void enterPendingPolicyNameDropdown(String value) {
		clickOnElement(policyNameDropdown);
		enter(generatePolicyNameSearchInputBox, value);
	}

	public boolean isSubTitleOfTabularViewDisplayed() {
		return isElementDisplayed(subTitleOfTabularView);
	}

	public boolean isHomeButtonDisplayed() {
		return isElementDisplayed(homeButton);
	}

	public boolean isPartnerIdHeaderDisplayed() {
		return isElementDisplayed(partnerIdColumnHeader);
	}

	public boolean isOrganisationHeaderDisplayed() {
		return isElementDisplayed(organisationColumnHeader);
	}

	public boolean isPolicyGroupHeaderDisplayed() {
		return isElementDisplayed(policyGroupColumnHeader);
	}

	public boolean isPolicyNameHeaderDisplayed() {
		return isElementDisplayed(policyNameColumnHeader);
	}

	public boolean isApiKeyNameHeaderDisplayed() {
		return isElementDisplayed(apiKeyNameColumnHeader);
	}

	public boolean isCreationDateHeaderDisplayed() {
		return isElementDisplayed(creationDateColumnHeader);
	}

	public boolean isStatusHeaderDisplayed() {
		return isElementDisplayed(statusColumnHeader);
	}

	public boolean isOidcClientIdHeaderDisplayed() {
		return isElementDisplayed(oidcClientIdColumnHeader);
	}

	public boolean isActionHeaderDisplayed() {
		return isElementDisplayed(actionColumnHeader);
	}

	public boolean isfilterResetButtonEnabled() {
		return isElementEnabled(filterResetButton);
	}

	public boolean isPartnerIdFilterHeaderDisplayed() {
		return isElementDisplayed(partnerIdFilterHeader);
	}

	public boolean isOrganisationFilterHeaderDisplayed() {
		return isElementDisplayed(organisationFilterHeader);
	}

	public boolean isPolicyGroupFilterHeaderDisplayed() {
		return isElementDisplayed(policyGroupFilterHeader);
	}

	public boolean isPolicyNameFilterHeaderDisplayed() {
		return isElementDisplayed(policyNameFilterHeader);
	}

	public boolean isApiKeyNameFilterHeaderDisplayed() {
		return isElementDisplayed(apiKeyNameFilterHeader);
	}

	public boolean isStatusFilterHeaderDisplayed() {
		return isElementDisplayed(statusFilterHeader);
	}

	public boolean isPartnerIdPlaceHolderDisplayed() {
		return isElementDisplayed(partnerIdPlaceHolder);
	}

	public boolean isOrganisationPlaceHolderDisplayed() {
		return isElementDisplayed(organisationPlaceHolder);
	}

	public boolean isPolicyGroupPlaceHolderDisplayed() {
		return isElementDisplayed(policyGroupPlaceHolder);
	}

	public boolean isPolicyNamePlaceHolderDisplayed() {
		return isElementDisplayed(policyNamePlaceHolder);
	}

	public boolean isApiKeyNamePlaceHolderDisplayed() {
		return isElementDisplayed(apiKeyNamePlaceHolder);
	}

	public boolean isStatusPlaceHolderDisplayed() {
		return isElementDisplayed(statusPlaceHolder);
	}

	public boolean isApplyFilterButtonEnabled() {
		return isElementEnabled(applyFilterButton);
	}

	public void clickOnApplyFilterButton() {
		clickOnElement(applyFilterButton);
	}

	public boolean isNoResultFoundDisplayed() {
		return isElementDisplayed(noResultFound);
	}

	public void clickOnStatusFilter() {
		clickOnElement(statusFilter);
	}

	public boolean isActivatedStatusInFilterDisplayed() {
		return isElementDisplayed(activatedStatusInFilter);
	}

	public boolean isDeactivatedStatusInFilterDisplayed() {
		return isElementDisplayed(deactivatedStatusInFilter);
	}

	public void clickOnActivatedStatusInFilter() {
		clickOnElement(activatedStatusInFilter);
	}

	public void clickOnPartnerIdDescIcon() {
		clickOnElement(partnerId_desc_icon);
	}

	public void clickOnPartnerIdAscIcon() {
		clickOnElement(partnerId_asc_icon);
	}

	public void clickOnPolicyGroupNameDescIcon() {
		clickOnElement(policyGroupName_desc_icon);
	}

	public void clickOnPolicyGroupNameAscIcon() {
		clickOnElement(policyGroupName_asc_icon);
	}

	public void clickOnPolicyNameDescIcon() {
		clickOnElement(policyName_desc_icon);
	}

	public void clickOnPolicyNameAscIcon() {
		clickOnElement(policyName_asc_icon);
	}

	public void clickOnCreatedDateTimeDescIcon() {
		clickOnElement(createdDateTime_desc_icon);
	}

	public void clickOnCreatedDateTimeAscIcon() {
		clickOnElement(createdDateTime_asc_icon);
	}

	public void clickOnStatusDescIcon() {
		clickOnElement(status_desc_icon);
	}

	public void clickOnStatusAscIcon() {
		clickOnElement(status_asc_icon);
	}

	public void clickOnOrgNameDescIcon() {
		clickOnElement(orgName_desc_icon);
	}

	public void clickOnOrgNameAscIcon() {
		clickOnElement(orgName_asc_icon);
	}

	public void enterValidApiKeyNameInFilter(String value) {
		enter(apiKeyNameFilter, value);
	}

	public void enterValidApiKeyNameInAdminFilter(String value) {
		enter(apiKeyNameFilterInAdmin, value);
	}

	public void enterPartnerIdInFilter(String value) {
		enter(partnerIdFilter, value);
	}

	public void enterPolicyGroupInFilter(String value) {
		enter(policyGroupFilter, value);
	}

	public void clickOnFilterCloseButton() {
		clickOnElement(filterCloseButton);
	}

	public void clickOnActionButton() {
		clickOnElement(actionButton);
	}

	public boolean isViewButtonEnabled() {
		return isElementEnabled(viewButton);
	}

	public boolean isDeactivateButtonEnabled() {
		return isElementEnabled(deactivateButton);
	}

	public void clickOnDeactivatedApiKeyRow() {
		clickOnElement(apiKeyItem1);
	}

	public boolean isApiKeyDetailsPageDisplayed() {
		return isElementDisplayed(apiKeyTitle);
	}

	public boolean isStatusDeavtivatedDisplayed() {
		return isElementDisplayed(deactivatedStatus);
	}

	public void selectDeactivateStatusInFilter() {
		clickOnElement(statusFilter);
		clickOnElement(deactivatedStatusInFilter);
	}

	public boolean isApiKeyDetailsPageTitleDisplayed() {
		return isElementDisplayed(apiKeyTitle);
	}

	public boolean isApiKeyNameLabelDisplayed() {
		return isElementDisplayed(apiKeyNameLabel);
	}

	public boolean isApiKeyDetailsPartnerTypeLabelDisplayed() {
		return isElementDisplayed(apiKeyDetailsPartnerTypeLabel);
	}

	public boolean isApiKeyDetailsPartnerTypeContextDisplayed() {
		return isElementDisplayed(apiKeyDetailsPartnerTypeContext);
	}

	public boolean isApiKeyDetailsOrgNameLabelDisplayed() {
		return isElementDisplayed(apiKeyDetailsPartnerTypeContext);
	}

	public boolean isApiKeyDetailsOrgNameContextDisplayed() {
		return isElementDisplayed(apiKeyDetailsPartnerTypeContext);
	}

	public boolean isCreatedOnLabelDisplayed() {
		return isElementDisplayed(createdOnLabel);
	}

	public boolean isCreatedDateDisplayed() {
		WebElement createdDate = driver
				.findElement(By.xpath("//div[text()='Created On " + PmpTestUtil.todayDateWithoutZeroPadder + "']"));
		return isElementDisplayed(createdDate);
	}

	public void clickOnBreadCombButton() {
		clickOnElement(subTitleButton);
	}

	public boolean isSpecialCharacterErrorMessageDisplayed() {
		return isElementDisplayed(specialCharacterErrorMessage);
	}

	public void clickOnApiKeyDeactivateButton() {
		clickOnElement(deactivateButton);
	}

	public boolean isApiKeyInAdminDeactivateInfoTextDisplayed() {
		return isElementDisplayed(apiKeyInAdminDeactivateInfoText);
	}

	public boolean isApiKeyInAdminDeactivatePopupDisplayed() {
		return isElementDisplayed(apiKeyInAdminDeactivateTitle);
	}

	public boolean isApiKeyInAdminDeactivateTitleDisplayed() {
		return isElementDisplayed(apiKeyInAdminDeactivateTitle);
	}

	public void enterInvalidDataInAdminApiKeyNameFilter(String value) {
		enter(apiKeyNameFilterInAdmin, value);
	}

	public void clickOnActivatedAdminApiKey() {
		clickOnElement(activatedAdminApiKey);
	}

	public void clickOnViewButton() {
		clickOnElement(viewButton);
	}

	public boolean isApiKeyCreationDateSameAsBrowserDateFormat() {

		By dateCellLocator = By.xpath("//tr[starts-with(@id,'api_key_list_item')][1]/td[6]");

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getTimeout()));

			WebElement dateCell = wait.until(ExpectedConditions.visibilityOfElementLocated(dateCellLocator));

			String browserTime = dateCell.getText().trim();

			LogUtil.step("API key creation date from UI: " + browserTime);
			LogUtil.step("Thread: " + Thread.currentThread().getName());

			DateTimeFormatter formatter = PmpTestUtil.nonZeroPadderDateFormatter;

			LocalDate.parse(browserTime, formatter);
			return true;

		} catch (TimeoutException e) {
			LogUtil.error("API key table row not visible in time");
			takeScreenshot();
			return false;

		} catch (DateTimeParseException e) {
			LogUtil.error("Date format mismatch: " + e.getMessage());
			takeScreenshot();
			return false;
		}
	}

	public boolean isExpirationDateHeaderDisplayed() {
		return isDisplayed(EXPIRATION_DATE_HEADER);
	}

	public boolean isExpirationDateHeaderAfterCreationDate() {
		int creationIndex = getColumnIndex(CREATION_DATE_HEADER_ID);
		int expirationIndex = getColumnIndex(EXPIRATION_DATE_HEADER_ID);
		LogUtil.step("Creation Date column index: " + creationIndex + ", Expiration Date column index: "
				+ expirationIndex);

		if (creationIndex < 0 || expirationIndex < 0) {
			LogUtil.error("Creation Date or Expiration Date column header is missing from the table");
			takeScreenshot();
			return false;
		}
		if (expirationIndex != creationIndex + 1) {
			LogUtil.error("Expiration Date is not the column immediately after Creation Date");
			takeScreenshot();
			return false;
		}
		return true;
	}

	public boolean isExpiryDateDescIconDisplayed() {
		return isElementDisplayed(apiKeyExpiryDateTime_desc_icon);
	}

	public boolean isExpiryDateAscIconDisplayed() {
		return isElementDisplayed(apiKeyExpiryDateTime_asc_icon);
	}

	public void clickOnExpiryDateDescIcon() {
		clickOnElement(apiKeyExpiryDateTime_desc_icon);
	}

	public void clickOnExpiryDateAscIcon() {
		clickOnElement(apiKeyExpiryDateTime_asc_icon);
	}

	public boolean isExpiryDateColumnSorted(boolean ascending, boolean isAdminView) {
		List<LocalDate> dates = readExpiryColumn(isAdminView);
		if (dates.size() < 2) {
			LogUtil.error("Only " + dates.size() + " dated row(s) on the page, which cannot demonstrate sorting");
			takeScreenshot();
			return false;
		}
		for (int i = 1; i < dates.size(); i++) {
			boolean inOrder = ascending ? !dates.get(i).isBefore(dates.get(i - 1))
					: !dates.get(i).isAfter(dates.get(i - 1));
			if (!inOrder) {
				LogUtil.error("Expiration Date not sorted " + (ascending ? "ascending" : "descending") + " at row "
						+ (i + 1) + ": " + dates.get(i - 1) + " then " + dates.get(i));
				takeScreenshot();
				return false;
			}
		}
		return true;
	}

	private List<LocalDate> readExpiryColumn(boolean isAdminView) {
		By columnLocator = isAdminView ? ADMIN_EXPIRY_COLUMN : PARTNER_EXPIRY_COLUMN;
		List<LocalDate> dates = new ArrayList<>();
		for (WebElement cell : driver.findElements(columnLocator)) {
			String value = cell.getText().trim();
			if (value.isEmpty() || value.equalsIgnoreCase(NO_EXPIRY_TEXT) || value.equals("-")) {
				continue;
			}
			try {
				dates.add(LocalDate.parse(value, PmpTestUtil.nonZeroPadderDateFormatter));
			} catch (DateTimeParseException e) {
				LogUtil.step("Skipping unparsable expiry value: " + value);
			}
		}
		LogUtil.step("Expiration dates read from column: " + dates);
		return dates;
	}

	public boolean isExpirationDateSameAsBrowserDateFormat(boolean isAdminView) {
		By cellLocator = isAdminView ? ADMIN_EXPIRY_CELL : PARTNER_EXPIRY_CELL;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getTimeout()));
			WebElement cell = wait.until(ExpectedConditions.visibilityOfElementLocated(cellLocator));
			String value = cell.getText().trim();
			LogUtil.step("API key expiration date from UI: " + value);

			if (value.equalsIgnoreCase(NO_EXPIRY_TEXT)) {
				LogUtil.error("First row carries no expiry date, so the locale format cannot be verified");
				takeScreenshot();
				return false;
			}
			LocalDate.parse(value, PmpTestUtil.nonZeroPadderDateFormatter);
			return true;

		} catch (TimeoutException e) {
			LogUtil.error("API key expiration cell not visible in time");
			takeScreenshot();
			return false;
		} catch (DateTimeParseException e) {
			LogUtil.error("Expiration date format mismatch: " + e.getMessage());
			takeScreenshot();
			return false;
		}
	}

	public boolean isExpirationDateOffsetFromCreationDate(int expectedDays, boolean isAdminView) {
		By createdLocator = isAdminView ? ADMIN_CREATED_CELL : PARTNER_CREATED_CELL;
		By expiryLocator = isAdminView ? ADMIN_EXPIRY_CELL : PARTNER_EXPIRY_CELL;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getTimeout()));
			String created = wait.until(ExpectedConditions.visibilityOfElementLocated(createdLocator)).getText().trim();
			String expiry = wait.until(ExpectedConditions.visibilityOfElementLocated(expiryLocator)).getText().trim();
			LogUtil.step("Created: " + created + ", Expiry: " + expiry + ", expected offset(days): " + expectedDays);

			if (expiry.equalsIgnoreCase(NO_EXPIRY_TEXT)) {
				LogUtil.error("Expiration date not set for the API key");
				takeScreenshot();
				return false;
			}
			LocalDate createdDate = LocalDate.parse(created, PmpTestUtil.nonZeroPadderDateFormatter);
			LocalDate expiryDate = LocalDate.parse(expiry, PmpTestUtil.nonZeroPadderDateFormatter);
			LocalDate expected = expectedDays > 0 ? createdDate.plusDays(expectedDays) : createdDate.plusYears(100);
			LogUtil.step("Expected expiration date: " + expected);

			if (!expected.equals(expiryDate)) {
				LogUtil.error("Expiration date " + expiryDate + " does not match the expected " + expected);
				takeScreenshot();
				return false;
			}
			return true;

		} catch (TimeoutException e) {
			LogUtil.error("Creation or expiration cell not visible in time");
			takeScreenshot();
			return false;
		} catch (DateTimeParseException e) {
			LogUtil.error("Could not parse the creation or expiration date: " + e.getMessage());
			takeScreenshot();
			return false;
		}
	}

	public boolean isApiKeyDetailsExpirationDateLabelDisplayed() {
		if (isElementDisplayedQuick(EXPIRATION_DATE_LABEL, Duration.ofSeconds(5))) {
			return true;
		}
		return isDisplayed(expirationDateLabelInAdminView());
	}

	public boolean isApiKeyDetailsExpirationDateContextDisplayed() {
		if (isElementDisplayedQuick(EXPIRATION_DATE_CONTEXT, Duration.ofSeconds(5))) {
			return true;
		}
		return isDisplayed(expirationDateContextInAdminView());
	}

	private By expirationDateLabelInAdminView() {
		return By.xpath("//p[normalize-space()='Expiration Date']");
	}

	private By expirationDateContextInAdminView() {
		return By.xpath("//p[normalize-space()='Expiration Date']/following-sibling::p[1]");
	}

	public boolean isIndividualViewFieldOrderCorrect() {
		String[] expectedOrder = { "Partner ID", "Partner Type", "Policy Group", "Policy Name",
				"Policy Group Description", "Policy Name Description", "Expiration Date" };

		List<String> renderedLabels = new ArrayList<>();
		for (WebElement label : driver.findElements(INDIVIDUAL_VIEW_LABELS)) {
			String text = label.getText().trim();
			if (!text.isEmpty()) {
				renderedLabels.add(text);
			}
		}
		LogUtil.step("Individual view labels in render order: " + renderedLabels);

		int cursor = -1;
		for (String expected : expectedOrder) {
			int found = renderedLabels.indexOf(expected);
			if (found < 0) {
				LogUtil.error("Field missing from individual view: " + expected);
				takeScreenshot();
				return false;
			}
			if (found <= cursor) {
				LogUtil.error("Field out of sequence: " + expected + " at index " + found + ", expected after "
						+ cursor);
				takeScreenshot();
				return false;
			}
			cursor = found;
		}
		return true;
	}

	public boolean isDeactivatedRowNotClickable(boolean isAdminView) {
		By rowLocator = isAdminView ? ADMIN_FIRST_ROW : PARTNER_FIRST_ROW;

		WebElement row = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getTimeout()))
				.until(ExpectedConditions.visibilityOfElementLocated(rowLocator));

		scrollIntoView(row);
		row.click();

		boolean detailsOpened = isElementDisplayedQuick(API_KEY_DETAILS_TITLE, Duration.ofSeconds(5));
		if (detailsOpened) {
			LogUtil.error("Deactivated API Key row opened the individual view");
			takeScreenshot();
		}
		return !detailsOpened;
	}

	public boolean isApiKeyListViewDisplayed() {
		return isElementDisplayed(subTitleOfTabularView);
	}

	public boolean isExpirationDateStyledLikeOtherFields() {
		By referenceLabel = By.id("api_key_details_partner_id_label");
		By expirationLabel = isElementDisplayedQuick(EXPIRATION_DATE_LABEL, Duration.ofSeconds(5))
				? EXPIRATION_DATE_LABEL
				: expirationDateLabelInAdminView();

		try {
			WebElement reference = driver.findElement(referenceLabel);
			WebElement expiration = driver.findElement(expirationLabel);

			for (String property : new String[] { "font-family", "font-size", "font-weight", "color" }) {
				String expected = reference.getCssValue(property);
				String actual = expiration.getCssValue(property);
				LogUtil.step("Label " + property + " - Partner ID: " + expected + ", Expiration Date: " + actual);

				if (!expected.equals(actual)) {
					LogUtil.error("Expiration Date label " + property + " is " + actual + " but the other field "
							+ "labels use " + expected);
					takeScreenshot();
					return false;
				}
			}
			return true;

		} catch (NoSuchElementException e) {
			LogUtil.error("Could not find the Expiration Date label or the reference label to compare against");
			takeScreenshot();
			return false;
		}
	}
}
