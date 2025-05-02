package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmprevampui.fw.util.PmpTestUtil;

public class OidcClientPage extends BasePage {

	@FindBy(id = "create_oid_client")
	private WebElement createOidcClient;

	@FindBy(id = "partnerId")
	private WebElement partnerIDHeaderText;

	@FindBy(id = "policyGroupName")
	private WebElement PolicyGroupHeaderText;

	@FindBy(id = "policyName")
	private WebElement PolicyNameHeaderText;

	@FindBy(id = "clientNameEng")
	private WebElement OIDCClientNameHeaderText;

	@FindBy(id = "createdDateTime")
	private WebElement CreatedDateHeaderText;

	@FindBy(id = "status")
	private WebElement StatusHeaderText;

	@FindBy(id = "oidcClientId")
	private WebElement OIDCClientIDHeaderText;

	@FindBy(id = "action")
	private WebElement ActionHeaderText;

	@FindBy(id = "authentication_apikey_tab")
	private WebElement ApiKeyTab;

	@FindBy(id = "generate_api_key")
	private WebElement enerateAPIKey;

	@FindBy(id = "generate_partner_id")
	private WebElement partnerIdDropdown;

	@FindBy(id = "generate_policy_name")
	private WebElement policyNameDropdown;

	@FindBy(id = "create_oidc_client_name")
	private WebElement EnterNameOidcTextBox;

	@FindBy(id = "create_oidc_public_key")
	private WebElement EnterPublickeyTextBox;

	@FindBy(id = "oidc_edit_enter_redirect_url2")
	private WebElement EnterPublickeyTextBoxSecond;

	@FindBy(id = "create_oidc_logo_url")
	private WebElement enterLogoUriTextBox;

	@FindBy(id = "create_oidc_redirect_url1")
	private WebElement enterRedirectUriTextBox;

	@FindBy(id = "create_oidc_submit_btn")
	private WebElement submitButton;

	@FindBy(id = "oidc_edit_submit_btn")
	private WebElement oidcEditSubmitButton;

	@FindBy(xpath = "(//span[contains(text(), 'Enter a valid URI')])[1]")
	private WebElement enterValidUriForLogoUriText;

	@FindBy(xpath = "(//span[contains(text(), 'Enter a valid URI')])[2]")
	private WebElement enterValidUriForRedirectUriText;

	@FindBy(xpath = "//span[contains(text(), 'Authorization Code')]")
	private WebElement authorizationCode;
	
	@FindBy(xpath = "//*[contains(text(), 'User Id does not exists')]")
	private WebElement userIdDoesNotExistsPopup;
	
	@FindBy(xpath = "//p[contains(text(), 'No Data Available.')]")
	private WebElement noDataAvailableText;
	
	@FindBy(id = "create_oidc_partner_id")
	private WebElement SelectPartneridForOidc;
	
	@FindBy(id = "create_oidc_partner_id_option1")
	private WebElement createOidcPartnerIdOption1;
	
	@FindBy(id = "create_oidc_policy_name")
	private WebElement SelectPolicyNameForOidc;

	@FindBy(id = "create_oidc_policy_name_search_input")
	private WebElement createOidcPolicyNameSearchInput;

	@FindBy(id = "create_oidc_policy_name_option1")
	private WebElement createOidcPolicyNameOption1;

	@FindBy(xpath = "//h1[contains(text(), 'Details Submitted Successfully!')]")
	private WebElement detailsSubmittedSuccessfully;

	@FindBy(xpath = "//div[contains(text(), 'Activated')]")
	private WebElement activatedText;

	@FindBy(id = "confirmation_go_back_btn")
	private WebElement confirmationGoBackButton;

	@FindBy(id = "oidc_show_copy_popup_btn1")
	private WebElement oidcShowCopyPopupButton;

	@FindBy(id = "oidc_details1")
	private WebElement oidcDetailsElipsisButton;

	@FindBy(id = "oidc_details_view_btn")
	private WebElement oidcDetailsViewButton;

	@FindBy(id = "oidc_client_details_back_btn")
	private WebElement oidcClientDetailsBackButton;

	@FindBy(id = "oidc_edit_btn")
	private WebElement oidcEditButton;

	@FindBy(id = "oidc_deactive_btn")
	private WebElement oidcDeactiveButton;

	@FindBy(id = "copy_id_btn")
	private WebElement copyIdButton;

	@FindBy(id = "copy_id_close_btn")
	private WebElement copyIdCloseButton;

	@FindBy(id = "oidc_edit_add_new_redirect_url")
	private WebElement oidcEditAddNewRedirectUrl;

	@FindBy(xpath = "//h1[contains(text(), 'Modified details submitted successfully!')]")
	private WebElement ModifiedSuccessfullTextMessage;

	@FindBy(id = "filter_btn")
	private WebElement filterButton;

	@FindBy(id = "partnerId_desc_icon")
	private WebElement partnerId_desc_icon;

	@FindBy(id = "partnerId_asc_icon")
	private WebElement partnerId_asc_icon;

	@FindBy(id = "clientNameEng_desc_icon")
	private WebElement oidcClientName_desc_icon;

	@FindBy(id = "clientNameEng_asc_icon")
	private WebElement oidcClientName_asc_icon;

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

	@FindBy(id = "oidc_select_partner_id_filter")
	private WebElement oidcSelectPartnerIdFilter;

	@FindBy(id = "oidc_select_partner_id_filter_option1")
	private WebElement oidcSelectPartnerIdFilterOption1;

	@FindBy(id = "oidc_select_policy_group_filter")
	private WebElement oidcSelectPolicyGroupFilter;

	@FindBy(id = "oidc_select_policy_group_filter_option1")
	private WebElement oidcSelectPolicyGroupFilterOption1;

	@FindBy(id = "oidc_select_policy_name_filter")
	private WebElement oidcSelectPolicyNameFilter;

	@FindBy(id = "oidc_select_policy_name_filter_option1")
	private WebElement oidcSelectPolicyNameFilterOption1;

	@FindBy(id = "oidc_select_client_name_filter")
	private WebElement oidcSelectClientNameFilter;

	@FindBy(id = "oidc_select_client_name_filter_option1")
	private WebElement oidcSelectClientNameFilterOption1;

	@FindBy(id = "oidc_select_status_filter")
	private WebElement oidcSelectStatusFilter;

	@FindBy(id = "oidc_select_status_filter_option1")
	private WebElement oidcActivatedStatusFilter;

	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;
	
	@FindBy(id = "add_new_redirect_url")
	private WebElement addNewRedirectUrl;
	
	@FindBy(id = "create_oidc_redirect_url2")
	private WebElement createOidcRedirectUrl2;
	
	@FindBy(id = "create_oidc_redirect_url3")
	private WebElement createOidcRedirectUrl3;
	
	@FindBy(id = "create_oidc_redirect_url4")
	private WebElement createOidcRedirectUrl4;
	
	@FindBy(id = "create_oidc_redirect_url5")
	private WebElement createOidcRedirectUrl5;
	
	@FindBy(id = "create_oidc_clear_form")
	private WebElement createOidcClearForm;
	
	@FindBy(id = "create_oidc_cancel_btn")
	private WebElement createOidcCancelButton;
	
	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;
	
	@FindBy(id = "create_oidc_btn")
	private WebElement oidcClientListPageCreateOidcClientBtn;

	@FindBy(xpath = "//p[contains(text(), 'Your changes will be lost, are you sure you want to proceed?')]")
	private WebElement browserConfirmationPopup;
	
	@FindBy(id = "block_messsage_proceed")
	private WebElement browserConfirmationPopupProceedBtn;
	
	@FindBy(id = "block_message_cancel")
	private WebElement browserConfirmationPopupCancelBtn;
	
	@FindBy(id = "title_back_icon")
	private WebElement backicon;

	@FindBy(id = "add_new_redirect_url")
	private WebElement redirectUriAddNew;
	
	@FindBy(id = "delete_redirect_url2")
	private WebElement RedirectUri2Delete;
	
	@FindBy(id = "create_oidc_redirect_url2")
	private WebElement RedirectUri2;
	
	@FindBy(id = "create_oidc_clear_form")
	private WebElement clearForm;
	
	@FindBy(xpath = "//*[@placeholder='Enter Logo URI']")  
	private WebElement enterLogoUriTextBoxEmpty;
	
	@FindBy(xpath = "(//span[@class='text-sm text-crimson-red font-semibold'])[1]")
	private WebElement publicKeyFormatErrorDisplayed;
	
	@FindBy(xpath = "(//span[@class='text-sm text-crimson-red font-semibold' and text()='Enter a valid URI'])[1]")
	private WebElement invalidLogoUriErrorDisplayed;
	
	@FindBy(xpath = "(//span[@class='text-sm text-crimson-red font-semibold' and text()='Enter a valid URI'])[1]")
	private WebElement invalidRedirectUriErrorDisplayed;

	@FindBy(id = "authentication_oidc_tab")
	private WebElement oidcClientTab;
	
	@FindBy(id = "oidc_edit_enter_client_name_input")
	private WebElement editOidcClientName;
	
	@FindBy(id = "oidc_deactive_btn")
	private WebElement oidcDeactivateButton;
	
	@FindBy(id = "deactivate_submit_btn")
	private WebElement deactivateSubmitButton;
	
	@FindBy(id = "deactivate_cancel_btn")
	private WebElement deactivateCancelButton;
	
	@FindBy(xpath = "//P[text()='Do you want to Deactivate OIDC Client']")
	private WebElement deactivateOidcPopup;
	
	@FindBy(xpath = "//P[text()='On clicking Confirm, you will not be able to use the OIDC Client ID for authentication anymore.']")
	private WebElement deactivateOidcInfoMessage;
	
	@FindBy(xpath = "//div[text()='Deactivated']")
	private WebElement deactivatedStatus;
	
	@FindBy(id = "oidc_show_copy_popup_btn1")
	private WebElement deactivatedEyeIcon;
	
	@FindBy(id = "oidc_details1")
	private WebElement deactivatedOidcActionButton;
	
	@FindBy(id = "oidc_client_list_item1")
	private WebElement oidcClientItem1;
	
	@FindBy(xpath = "//h1[text()='View OIDC Client Details']")
	private WebElement oidcClientTitle;
	
	@FindBy(xpath = "//div[text()='Deactivated']")
	private WebElement OidcDetailsPageStatusDeactivated;
	
	@FindBy(id = "oidc_client_details_partner_id_label")
	private WebElement oidcClientDetailsPartnerIdLabel;
	
	@FindBy(id = "oidc_client_details_partner_id_context")
	private WebElement oidcClientDetailsPartnerIdContext;
	
	@FindBy(id = "oidc_client_details_policy_group_label")
	private WebElement oidcClientDetailsPolicyGroupLabel;
	
	@FindBy(id = "oidc_client_details_policy_group_name_context")
	private WebElement oidcClientDetailsPolicyGroupNameContext;
	
	@FindBy(id = "oidc_client_details_policy_name_label")
	private WebElement oidcClientDetailsPolicyNameLabel;
	
	@FindBy(id = "oidc_client_details_policy_name_context")
	private WebElement oidcClientDetailsPolicyNameContext;
	
	@FindBy(id = "oidc_client_details_policy_group_description_label")
	private WebElement oidcClientDetailsPolicyGoupDescriptionLabel;
	
	@FindBy(id = "oidc_client_details_policy_group_description_context")
	private WebElement oidcClientDetailsPolicyGroupDescriptionContext;
	
	@FindBy(id = "oidc_client_details_policy_name_description_label")
	private WebElement oidcClientDetailsPolicyNameDescriptionLabel;
	
	@FindBy(id = "oidc_client_details_policy_description_context")
	private WebElement oidcClientDetailsPolicyDescriptionContext;
	
	@FindBy(id = "oidc_client_details_partner_type_label")
	private WebElement oidcClientDetailsPartnerTypeLabel;
	
	@FindBy(id = "oidc_client_details_auth_partner_context")
	private WebElement oidcClientDetailsPartnerTypeContext;
	
	@FindBy(id = "oidc_client_details_public_key_label")
	private WebElement oidcClientDetailsPublicKeyLabel;
	
	@FindBy(id = "oidc_client_details_public_key_context")
	private WebElement oidcClientDetailsPublicKeyContext;
	
	@FindBy(id = "oidc_client_details_logo_uri_label")
	private WebElement oidcClientDetailsLogoUriLabel;
	
	@FindBy(id = "oidc_client_details_logo_uri_context")
	private WebElement oidcClientDetailsLogoUriContext;
	
	@FindBy(id = "oidc_client_details_redirect_uris")
	private WebElement oidcClientDetailsRedirectUrisLabel;
	
	@FindBy(id = "oidc_client_redirect_uris")
	private WebElement oidcClientDetailsRedirectUrisContext;
	
	@FindBy(id = "oidc_client_details_grant_types")
	private WebElement oidcClientDetailsGrantTypesLabel;
	
	@FindBy(id = "oidc_client_grant_types")
	private WebElement oidcClientDetailsGrantTypesContext;
	
	@FindBy(xpath = "//div[text()='Activated']")
	private WebElement activatedStatus;
	
	@FindBy(xpath = "//p[text()='OIDC Client Name']")
	private WebElement oidcClientNameLabel;
	
	@FindBy(xpath = "//span[text()='abcdcd']")
	private WebElement oidcClientNameContext;
	
	@FindBy(id = "oidc_client_details_copy_id")
	private WebElement oidcClientDetailsCopyId;
	
	@FindBy(xpath = "//p[contains(text(), 'List of OIDC Clients')]")
	private WebElement subTitleOfTabularView;
	
	@FindBy(xpath = "//div[contains(text(), 'Partner ID')]")
	private WebElement partnerIdColumnHeader;
	
	@FindBy(xpath = "//div[contains(text(), 'Organisation')]")
	private WebElement organisationColumnHeader;
	
	@FindBy(xpath = "//div[contains(text(), 'Policy Group')]")
	private WebElement policyGroupColumnHeader;
	
	@FindBy(xpath = "//div[contains(text(), 'Policy Name')]")
	private WebElement policyNameColumnHeader;
	
	@FindBy(xpath = "//div[contains(text(), 'OIDC Client Name')]")
	private WebElement oidcClientNameColumnHeader;
	
	@FindBy(xpath = "//div[contains(text(), 'Creation Date')]")
	private WebElement creationDateColumnHeader;
	
	@FindBy(xpath = "//div[contains(text(), 'Status')]")
	private WebElement statusColumnHeader;
	
	@FindBy(xpath = "//div[contains(text(), 'OIDC Client ID')]")
	private WebElement oidcClientIdColumnHeader;
	
	@FindBy(xpath = "//div[contains(text(), 'Action')]")
	private WebElement actionColumnHeader;
	
	@FindBy(xpath = "//p[contains(text(), 'Partner ID')]")
	private WebElement partnerIdFilterHeader;
	
	@FindBy(xpath = "//p[contains(text(), 'Organisation')]")
	private WebElement organisationFilterHeader;
	
	@FindBy(xpath = "//p[contains(text(), 'Policy Group')]")
	private WebElement policyGroupFilterHeader;
	
	@FindBy(xpath = "//p[contains(text(), 'Policy Name')]")
	private WebElement policyNameFilterHeader;
	
	@FindBy(xpath = "//p[contains(text(), 'OIDC Client Name')]")
	private WebElement oidcClientNameFilterHeader;
	
	@FindBy(xpath = "//p[contains(text(), 'Status')]")
	private WebElement statusFilterHeader;
	
	@FindBy(xpath = "//input[@placeholder='Search Partner ID']")
	private WebElement partnerIdPlaceHolder;
	
	@FindBy(xpath = "//input[@placeholder='Search Organisation']")
	private WebElement organisationPlaceHolder;
	
	@FindBy(xpath = "//input[@placeholder='Search Policy Group']")
	private WebElement policyGroupPlaceHolder;
	
	@FindBy(xpath = "//input[@placeholder='Search Policy Name']")
	private WebElement policyNamePlaceHolder;
	
	@FindBy(xpath = "//input[@placeholder='Search OIDC Client Name']")
	private WebElement oidcClientNamePlaceHolder;
	
	@FindBy(xpath = "//span[contains(text(), 'Select Status')]")
	private WebElement statusPlaceHolder;
	
	@FindBy(id = "partner_id_filter")
	private WebElement partnerIdFilter;
	
	@FindBy(id = "org_name_filter")
	private WebElement organisationNameFilter;
	
	@FindBy(id = "policy_group_filter")
	private WebElement policyGroupFilter;
	
	@FindBy(id = "policy_name_filter")
	private WebElement policyNameFilter;
	
	@FindBy(id = "oidc_client_name_filter")
	private WebElement oidcClientNameFilter;
	
	@FindBy(id = "status_filter")
	private WebElement statusFilter;
	
	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilterButton;
	
	@FindBy(xpath = "//p[contains(text(), 'No Results Found')]")
	private WebElement noResultFound;
	
	@FindBy(id = "status_filter_option1")
	private WebElement activatedStatusInFilter;
	
	@FindBy(id = "status_filter_option2")
	private WebElement deactivatedStatusInFilter;
	
	@FindBy(xpath = "//button[text()='x']")
	private WebElement filterCloseButton;
	
	@FindBy(id = "visibility_FILL0_wght400_GRAD0_opsz48")
	private WebElement oidcClientIdEyeIcon;
	
	@FindBy(id = "oidc_client_list_action_view1")
	private WebElement actionButton;
	
	@FindBy(xpath = "//h1[contains(text(), '  ')]")
	private WebElement policyNameInEyeIconPopup;
	
	@FindBy(xpath = "//p[contains(text(), 'sudeep-auth')]")
	private WebElement partnerIdInEyeIconPopup;
	
	@FindBy(xpath = "//h1[contains(text(), 'OIDC Client ID')]")
	private WebElement oidcClientIdLabelInEyeIconPopup;
	
	@FindBy(xpath = "//button[contains(text(), 'Copied!')]")
	private WebElement copiedInEyeIconPopup;
	
	@FindBy(id = "orgName_desc_icon")
	private WebElement orgName_desc_icon;
	
	@FindBy(id = "orgName_asc_icon")
	private WebElement orgName_asc_icon;
	
	@FindBy(id = "oidc_clients_list_view_btn")
	private WebElement viewButton;
	
	@FindBy(id = "oidc_clients_list_deactivate_btn")
	private WebElement deactivateButton;
	
	@FindBy(id = "pagination_card")
	private WebElement pgination;
	
	@FindBy(id = "sub_title_btn")
	private WebElement listOfOidcClients;
	
	@FindBy(xpath = "//p[contains(text(), 'OIDC Client ID')]")
	private WebElement oidcClientId;
	
	@FindBy(xpath = "//p[contains(text(),'Are you sure you want to Deactivate OIDC Client')]")
	private WebElement deactivateOidcClientPopup;
	
	@FindBy(xpath = "//p[contains(text(),'Are you sure you want to Deactivate OIDC Client')]")
	private WebElement deactivateOidcClientTitle;
	
	@FindBy(xpath = "//p[contains(text(),'On clicking ‘Confirm’, the OIDC Client ID will be deactivated')]")
	private WebElement confirmDeactivationMessage;
	
	@FindBy(xpath = "//p[@class='text-base font-bold text-gray-400 truncate']")
	private WebElement deactivatedOidcClientIdElement;
	
	@FindBy(id = "oidc_client_details_org_name_label")
	private WebElement oidcClientDetailsOrgNameLabel;
	
	@FindBy(id = "oidc_client_details_org_name_context")
	private WebElement oidcClientDetailsOrgNameContext;
	
	@FindBy(xpath = "//p[@class='text-base font-bold text-[#1447B2] truncate']")
	private WebElement activatedOidcClientIdElement;
	
	@FindBy(xpath = "//div[contains(text(), 'Created On')]")
	private WebElement createdOnLabel;
	
	@FindBy(xpath = "//p[contains(text(), 'Copied!')]")
	private WebElement copied;

	public OidcClientPage(WebDriver driver) {
		super(driver);
	}

	public boolean isCreateOidcClientDisplayed() {
		return isElementDisplayed(createOidcClient);
	}

	public void clickOnCreateOidcClientButton() {
		clickOnElement(createOidcClient);
	}

	public void selectPartnerIdDropdown() {
		clickOnElement(SelectPartneridForOidc);
		clickOnElement(createOidcPartnerIdOption1);
	}

	public boolean isPartnerIdDropdownDisplayed() {
		return isElementDisplayed(SelectPartneridForOidc);
	}

	public boolean isPolicyNameDropdownDisplayed() {
		return isElementDisplayed(SelectPolicyNameForOidc);
	}

	public void selectPolicyNameDropdown(String policyNameValue) {
		clickOnElement(SelectPolicyNameForOidc);
		enter(createOidcPolicyNameSearchInput, policyNameValue);
		clickOnElement(createOidcPolicyNameOption1);
	}
	
	public void enterDeactivePolicyNameInDropdown(String policyNameValue) {
		clickOnElement(SelectPolicyNameForOidc);
		enter(createOidcPolicyNameSearchInput, policyNameValue);
	}

	public void enterNameOidcTextBox(String oidcTextBoxValue) {
		enter(EnterNameOidcTextBox, oidcTextBoxValue);
	}

	public void enterPublicKeyTextBox(String value) {
		enter(EnterPublickeyTextBox, value);
	}

	public void EnterPublickeySecondTextBox(String value) {
		enter(EnterPublickeyTextBoxSecond, value);
	}

	public void enterLogoUrTextBox(String value) {
		enter(enterLogoUriTextBox, value);
	}

	public void enterRedirectUriTextBox(String value) {
		enter(enterRedirectUriTextBox, value);
	}

	public void clickOnSubmitButton() {
		clickOnElement(submitButton);
	}

	public boolean isEnterValidUriForLogoUriTextDisplayed() {
		return isElementDisplayed(enterValidUriForLogoUriText);
	}

	public boolean isEnterValidUriForRedirectUriTextDisplayed() {
		return isElementDisplayed(enterValidUriForRedirectUriText);
	}

	public boolean isAuthorizationCodeTextDisplayed() {
		return isElementDisplayed(authorizationCode);
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

	public boolean isOIDCClientNameHeaderTextDisplayed() {
		return isElementDisplayed(OIDCClientNameHeaderText);
	}

	public boolean isCreatedDateHeaderTextDisplayed() {
		return isElementDisplayed(CreatedDateHeaderText);
	}

	public boolean isStatusHeaderTextDisplayed() {
		return isElementDisplayed(StatusHeaderText);
	}

	public boolean isOIDCClientIDHeaderTextDisplayed() {
		return isElementDisplayed(OIDCClientIDHeaderText);
	}

	public boolean isActionHeaderTextDisplayed() {
		return isElementDisplayed(ActionHeaderText);
	}
	
	public boolean isApiKeyTabDisplayed() {
		return isElementDisplayed(ApiKeyTab);
	}

	public void clickOnApiKeyTab() {
		clickOnElement(ApiKeyTab);
	}

	public void enterCreateOidcPolicyNameSearchInput(String value) {
		enter(createOidcPolicyNameSearchInput, value);
	}

	public boolean isOidcSubmittedSuccessfullyDisplayed() {
		return isElementDisplayed(detailsSubmittedSuccessfully);
	}

	public boolean isActivatedTextDisplayed() {
		return isElementDisplayed(activatedText);
	}

	public void clickConfirmationGoBackButton() {
		clickOnElement(confirmationGoBackButton);
	}

	public void clickOidcShowCopyPopupButton() {
		clickOnElement(oidcShowCopyPopupButton);
	}

	public boolean isOidcDetailsElipsisButtonDisplayed() {
		return isElementDisplayed(oidcDetailsElipsisButton);
	}

	public void clickOidcDetailsElipsisButton() {
		clickOnElement(oidcDetailsElipsisButton);
	}

	public boolean isOidcDetailsViewButtonDisplayed() {
		return isElementDisplayed(oidcDetailsViewButton);
	}

	public void clickOidcClientDetailsBackButton() {
		clickOnElement(oidcClientDetailsBackButton);
	}

	public boolean isOidcDeactiveButtonDisplayed() {
		return isElementDisplayed(oidcDeactiveButton);
	}

	public void clickOnCopyIdButton() {
		clickOnElement(copyIdButton);
	}

	public void clickOnCopyIdCloseButton() {
		clickOnElement(copyIdCloseButton);
	}

	public void clickOnOidcEditButton() {
		clickOnElement(oidcEditButton);
	}

	public void clickOnoidcEditAddNewRedirectUrl() {
		clickOnElement(oidcEditAddNewRedirectUrl);
	}

	public boolean isModifiedSuccessfullTextMessageDisplayed() {
		return isElementDisplayed(ModifiedSuccessfullTextMessage);
	}

	public boolean isPartnerIdDescIconDisplayed() {
		return isElementDisplayed(partnerId_desc_icon);
	}

	public boolean isPartnerIdAscIconDisplayed() {
		return isElementDisplayed(partnerId_asc_icon);
	}

	public boolean isOidcClientNameDescIconDisplayed() {
		return isElementDisplayed(oidcClientName_desc_icon);
	}

	public boolean isOidcClientNameAscIconDisplayed() {
		return isElementDisplayed(oidcClientName_asc_icon);
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

	public boolean isFilterButtonEnabled() {
		return isElementEnabled(filterButton);
	}

	public void clickOnFilterButton() {
		clickOnElement(filterButton);
	}

	public void clickOnOidcPartnerIdFilter() {
		clickOnElement(oidcSelectPartnerIdFilter);
		clickOnElement(oidcSelectPartnerIdFilterOption1);
	}

	public void clickOnOidcSelectPolicyGroupFilter() {
		clickOnElement(oidcSelectPolicyGroupFilter);
		clickOnElement(oidcSelectPolicyGroupFilterOption1);
	}

	public void clickOnOidcSelectPolicyNameFilter() {
		clickOnElement(oidcSelectPolicyNameFilter);
		clickOnElement(oidcSelectPolicyNameFilterOption1);
	}

	public void clickOnOidcSelectClientNameFilter() {
		clickOnElement(oidcSelectClientNameFilter);
		clickOnElement(oidcSelectClientNameFilterOption1);
	}

	public void selectActivatedStatusInFilter() {
		clickOnElement(oidcSelectStatusFilter);
		clickOnElement(oidcActivatedStatusFilter);
	}

	public boolean isfilterResetButtonDisplayed() {
		return isElementDisplayed(filterResetButton);
	}
	
	public boolean isfilterResetButtonEnabled() {
		return isElementEnabled(filterResetButton);
	}

	public void clickOnFilterResetButton() {
		clickOnElement(filterResetButton);
	}

	public void clickOnOidcEditSubmitButton() {
		clickOnElement(oidcEditSubmitButton);
	}
	
	public boolean isUserIdDoesNotExistsPopupDisplayed() {
		return isElementDisplayed(userIdDoesNotExistsPopup);
	}
	
	public boolean isNoDataAvailableTextDisplayed() {
		return isElementDisplayed(noDataAvailableText);
	}
	
	public void clickOnPartnerIdDropdown() {
		clickOnElement(SelectPartneridForOidc);
	}
	
	public void clickOnAddNewRedirectUrlButton() {
		clickOnElement(addNewRedirectUrl);
	}
	
	public void entercreateOidcRedirectUrl2(String value) {
		enter(createOidcRedirectUrl2,value);
	}
	
	public void entercreateOidcRedirectUrl3(String value) {
		enter(createOidcRedirectUrl3,value);
	}
	
	public void entercreateOidcRedirectUrl4(String value) {
		enter(createOidcRedirectUrl4,value);
	}
	
	public void entercreateOidcRedirectUrl5(String value) {
		enter(createOidcRedirectUrl5,value);
	}
	
	public void clickOnCreateOidcClearForm() {
		clickOnElement(createOidcClearForm);
	}
	
	public boolean isCreateOidcRedirectUrl5Displayed() {
		return isElementDisplayed(createOidcRedirectUrl5);
	}
	
	public boolean isCreateOidcCancelButtonDisplayed() {
		return isElementDisplayed(createOidcCancelButton);
	}
	
	public boolean isHomeButtonDisplayed() {
		return isElementDisplayed(homeButton);
	}
	
	public void listPageCreateOidcClientButton() {
		clickOnElement(oidcClientListPageCreateOidcClientBtn);
	}
	
	public void navigateBackDefaultButton() {
		driver.navigate().back();
	}
	
	public void navigateRefreshDefaultButton() {
		driver.navigate().refresh();
	}
	
	public boolean isBrowserBackConfirmationPopupDisplayed() {
		return isElementDisplayed(browserConfirmationPopup);
	}
	
	public boolean isBrowserBackProceedButtonAvailable() {
		return isElementDisplayed(browserConfirmationPopupProceedBtn);
	}
	
	public void clickOnBrowserConfirmationPopupProceedBtn() {
		clickOnElement(browserConfirmationPopupProceedBtn);
	}
	
	public boolean isBrowserBackCancelButtonAvailable() {
		return isElementDisplayed(browserConfirmationPopupCancelBtn);
	}
	
	public void clickOnBrowserConfirmationPopupCancelBtn() {
		clickOnElement(browserConfirmationPopupCancelBtn);
	}
	
	public void clickOnTitleBackButton() {
		clickOnElement(backicon);
	}
	
	public boolean isEditSecondRedirectUriTextBoxDisplayed() {
		return isElementDisplayed(EnterPublickeyTextBoxSecond);
	}
	
	public boolean isSecondRedirectUriTextBoxDisplayed() {
		return isElementDisplayed(createOidcRedirectUrl2);
	}
		
	public void clickOnRedirectUriAddNew() {
		clickOnElement(redirectUriAddNew);
	}

	public void clickOnRedirectUri2Delete() {
		clickOnElement(RedirectUri2Delete);
	}
	
	public boolean isRedirectUri2TextBoxDisplayed() {
		return isElementDisplayed(RedirectUri2);
	}
	
	public void clickOnClearFormButton() {
		clickOnElement(clearForm);
	}
	
	public boolean isLogoUriempty() {
		return isElementDisplayed(enterLogoUriTextBoxEmpty);
	}
	
	public boolean isOidcClientTabDisplayed() {
		return isElementDisplayed(oidcClientTab);
	}
	
	public boolean isPublicKeyFormatErrorDisplayed() {
		return isElementDisplayed(publicKeyFormatErrorDisplayed);
	}
	
	public boolean isInvalidLogoUriErrorDisplayed() {
		return isElementDisplayed(invalidLogoUriErrorDisplayed);
	}
	
	public boolean isInvalidRedirectUriErrorDisplayed() {
		return isElementDisplayed(invalidRedirectUriErrorDisplayed);
	}
	
	public boolean isThirdRedirectUriTextBoxDisplayed() {
		return isElementDisplayed(createOidcRedirectUrl3);
	}
	
	public boolean isFourthRedirectUriTextBoxDisplayed() {
		return isElementDisplayed(createOidcRedirectUrl4);
	}
	
	public boolean isFifthRedirectUriTextBoxDisplayed() {
		return isElementDisplayed(createOidcRedirectUrl5);
	}
	
	public void clickOnOidcDeactivateButton() {
		clickOnElement(oidcDeactivateButton);
	}
	
	public boolean isdeactivateOidcPopupDisplayed() {
		return isElementDisplayed(deactivateOidcPopup);
	}
	
	public boolean isDeactivateOidcInfoMessageDisplayed() {
		return isElementDisplayed(deactivateOidcInfoMessage);
	}
	
	public boolean isDeactivateCancelButtonAvailable() {
		return isElementDisplayed(deactivateCancelButton);
	}
	
	public boolean isDeactivateSubmitButtonAvailable() {
		return isElementDisplayed(deactivateSubmitButton);
	}
	
	public boolean isStatusDeavtivatedDisplayed() {
		return isElementDisplayed(deactivatedStatus);
	}
	
	public void clickOnDeactivateCancelButton() {
		clickOnElement(deactivateCancelButton);
	}
	
	public void clickOnDeactivateSubmitButtonButton() {
		clickOnElement(deactivateSubmitButton);
	}
	
	public boolean isDeactivatedEyeIconDisplayed() {
		return isElementDisplayed(deactivatedEyeIcon);
	}
	
	public void clickOnDeactivatedEyeIcon() {
		clickOnElement(deactivatedEyeIcon);
	}
	
	public boolean iscopyIdButtonDisplayed() {
		return isElementDisplayed(copyIdButton);
	}
	
	public void clickOnDeactivatedOidcActionButton() {
		clickOnElement(deactivatedOidcActionButton);
	}
	
	public void clickOnDeactivatedOidcRow() {
		clickOnElement(oidcClientItem1);
	}
	
	public boolean isOidcClientDetailsPageDisplayed() {
		return isElementDisplayed(oidcClientTitle);
	}
	
	public void clickOnOidcDetailsViewButton() {
		clickOnElement(oidcDetailsViewButton);
	}
	
	public boolean isOidcDetailsPageStatusDeactivatedDisplayed() {
		return isElementDisplayed(OidcDetailsPageStatusDeactivated);
	}
	
	public boolean isOidcDeactivateButtonDisplayed() {
		return isElementDisplayed(oidcDeactivateButton);
	}
	
	public boolean isOidcEditButtonDisplayed() {
		return isElementDisplayed(oidcEditButton);
	}
	
	public boolean isOidcClientDetailsPartnerIdLabelDisplayed() {
		return isElementDisplayed(oidcClientDetailsPartnerIdLabel);
	}
	
	public boolean isOidcClientDetailsPartnerIdContextDisplayed() {
		return isElementDisplayed(oidcClientDetailsPartnerIdContext);
	}
	
	public boolean isOidcClientDetailsPolicyGroupLabelDisplayed() {
		return isElementDisplayed(oidcClientDetailsPolicyGroupLabel);
	}
	
	public boolean isOidcClientDetailsPolicyGroupNameContextDisplayed() {
		return isElementDisplayed(oidcClientDetailsPolicyGroupNameContext);
	}
	
	public boolean isOidcClientDetailsPolicyNameLabelDisplayed() {
		return isElementDisplayed(oidcClientDetailsPolicyNameLabel);
	}
	
	public boolean isOidcClientDetailsPolicyNameContextDisplayed() {
		return isElementDisplayed(oidcClientDetailsPolicyNameContext);
	}
	
	public boolean isOidcClientDetailsPolicyGoupDescriptionLabelDisplayed() {
		return isElementDisplayed(oidcClientDetailsPolicyGoupDescriptionLabel);
	}
	
	public boolean isOidcClientDetailsPolicyGroupDescriptionContextDisplayed() {
		return isElementDisplayed(oidcClientDetailsPolicyGroupDescriptionContext);
	}
	
	public boolean isOidcClientDetailsPolicyNameDescriptionLabelDisplayed() {
		return isElementDisplayed(oidcClientDetailsPolicyNameDescriptionLabel);
	}
	
	public boolean isOidcClientDetailsPolicyDescriptionContextDisplayed() {
		return isElementDisplayed(oidcClientDetailsPolicyDescriptionContext);
	}
	
	public boolean isOidcClientDetailsPartnerTypeLabelDisplayed() {
		return isElementDisplayed(oidcClientDetailsPartnerTypeLabel);
	}
	
	public boolean isOidcClientDetailsPartnerTypeContextDisplayed() {
		return isElementDisplayed(oidcClientDetailsPartnerTypeContext);
	}
	
	public boolean isOidcClientDetailsPublicKeyLabelDisplayed() {
		return isElementDisplayed(oidcClientDetailsPublicKeyLabel);
	}
	
	public boolean isOidcClientDetailsPublicKeyContextDisplayed() {
		return isElementDisplayed(oidcClientDetailsPublicKeyContext);
	}
	
	public boolean isOidcClientDetailsLogoUriLabelDisplayed() {
		return isElementDisplayed(oidcClientDetailsLogoUriLabel);
	}
	
	public boolean isOidcClientDetailsLogoUriContextDisplayed() {
		return isElementDisplayed(oidcClientDetailsLogoUriContext);
	}
	
	public boolean isOidcClientDetailsRedirectUrisLabelDisplayed() {
		return isElementDisplayed(oidcClientDetailsRedirectUrisLabel);
	}
	
	public boolean isOidcClientDetailsRedirectUrisContextDisplayed() {
		return isElementDisplayed(oidcClientDetailsRedirectUrisContext);
	}
	
	public boolean isOidcClientDetailsGrantTypesLabelDisplayed() {
		return isElementDisplayed(oidcClientDetailsGrantTypesLabel);
	}
	
	public boolean isOidcClientDetailsGrantTypesContextDisplayed() {
		return isElementDisplayed(oidcClientDetailsGrantTypesContext);
	}
	
	public boolean isOidcClientDetailsActivatedStatusDisplayed() {
		return isElementDisplayed(activatedStatus);
	}
	
	public boolean isCreatedDateDisplayed() {
		WebElement createdDate = driver
				.findElement(By.xpath("//div[text()='Created On " + PmpTestUtil.todayDateWithoutZeroPadder + "']"));
		return isElementDisplayed(createdDate);
	}
	
	public boolean isOidcClientDetailsCopyIdDisplayed() {
		return isElementDisplayed(oidcClientDetailsCopyId);
	}
	
	public void clickOnOidcClientDetailsCopyId() {
		clickOnElement(oidcClientDetailsCopyId);
	}
	
	public boolean isOidcClientDetailsBackButtonDisplayed() {
		return isElementDisplayed(oidcClientDetailsBackButton);
	}
	
	public boolean isOidcClientNameLabelDisplayed() {
		return isElementDisplayed(oidcClientNameLabel);
	}
	
	public boolean isOidcClientNameContextDisplayed() {
		return isElementDisplayed(oidcClientNameContext);
	}
	
	public boolean isSubTitleOfTabularViewDisplayed() {
		return isElementDisplayed(subTitleOfTabularView);
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
	
	public boolean isOidcClientNameHeaderwDisplayed() {
		return isElementDisplayed(oidcClientNameColumnHeader);
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
	
	public boolean isOidcClientNameFilterHeaderDisplayed() {
		return isElementDisplayed(oidcClientNameFilterHeader);
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
	
	public boolean isOidcClientNamePlaceHolderDisplayed() {
		return isElementDisplayed(oidcClientNamePlaceHolder);
	}
	
	public boolean isStatusPlaceHolderDisplayed() {
		return isElementDisplayed(statusPlaceHolder);
	}
	
	public void enterInvalidOidcClientNameInFilter(String value) {
		enter(oidcClientNameFilter,value);
	}
	
	public void enterValidOidcClientNameInFilter(String value) {
		enter(oidcClientNameFilter,value);
	}
	
	public void enterPartnerIdInFilter(String value) {
		enter(partnerIdFilter,value);
	}
	
	public void enterPolicyGroupInFilter(String value) {
		enter(policyGroupFilter,value);
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
	
	public void clickOnDeactivatedStatusInFilter() {
		clickOnElement(deactivatedStatusInFilter);
	}
	
	public void clickOnActivatedStatusInFilter() {
		clickOnElement(activatedStatusInFilter);
	}
	
	public void clickOnFilterCloseButton() {
		clickOnElement(filterCloseButton);
	}
	
	public void clickOnActivatedOidcClient() {
		clickOnElement(oidcClientItem1);
	}
	
	public boolean isOidcClientIdEyeIconDisplayed() {
		return isElementDisplayed(oidcClientIdEyeIcon);
	}
	
	public void clickOnOidcClientIdEyeIcon() {
		clickOnElement(oidcClientIdEyeIcon);
	}
	
	public boolean isPolicyNameAsTitleDisplayed() {
		return isElementDisplayed(policyNameInEyeIconPopup);
	}
	
	public boolean isPartnerIdAsSubTitleDisplayed() {
		return isElementDisplayed(partnerIdInEyeIconPopup);
	}
	
	public boolean isOidcClientIdLabelInEyeIconPopupDisplayed() {
		return isElementDisplayed(oidcClientIdLabelInEyeIconPopup);
	}
	
	public boolean isCopyIdCloseButtonDisplayed() {
		return isElementDisplayed(copyIdCloseButton);
	}
	
	public boolean isOrgNameDescIconDisplayed() {
		return isElementDisplayed(orgName_desc_icon);
	}
	
	public boolean isOrgNameAscIconDisplayed() {
		return isElementDisplayed(orgName_asc_icon);
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
	
	public void clickOnActionButton() {
		clickOnElement(actionButton);
	}
	
	public boolean isViewButtonEnabled() {
		return isElementEnabled(viewButton);
	}
	
	public boolean isDeactivateButtonEnabled() {
		return isElementEnabled(deactivateButton);
	}
	
	public boolean isPaginationDisplayed() {
		return isElementDisplayed(pgination);
	}
	
	public void clickOnViewButton() {
		clickOnElement(viewButton);
	}
	
	public boolean isViewOidcClientDetailsPageTitleDisplayed() {
		return isElementDisplayed(oidcClientTitle);
	}
	
	public boolean isListOfOidcClientsDisplayed() {
		return isElementDisplayed(listOfOidcClients);
	}
	
	public boolean isOidcClientIdLabelDisplayed() {
		return isElementDisplayed(oidcClientId);
	}
	
	public void clickOnDeactivateButton() {
		clickOnElement(deactivateButton);
	}

	public boolean isDeactivateOidcClientPopupDisplayed() {
		return isElementDisplayed(deactivateOidcClientPopup);
	}

	public boolean isDeactivateOidcClientTitleDisplayed() {
		return isElementDisplayed(deactivateOidcClientTitle);
	}
	
	public boolean isDeactivateOidcClientSubtitleDisplayed() {
		return isElementDisplayed(confirmDeactivationMessage);
	}
	
	public void selectDeactivatedStatusInFilter() {
		clickOnElement(oidcSelectStatusFilter);
		clickOnElement(deactivatedStatusInFilter);
	}
	
	public boolean isDeactivatedOidcClientIdElementDisplayed() {
		return isElementDisplayed(deactivatedOidcClientIdElement);
	}
	
	public boolean isActivatedOidcClientIdElementDisplayed() {
		return isElementDisplayed(activatedOidcClientIdElement);
	}
	
	public boolean isOidcClientDetailsOrgNameLabelDisplayed() {
		return isElementDisplayed(oidcClientDetailsOrgNameLabel);
	}
	
	public boolean isOidcClientDetailsOrgNameContextDisplayed() {
		return isElementDisplayed(oidcClientDetailsOrgNameContext);
	}
	
	public boolean isCreatedOnLabelDisplayed() {
		return isElementDisplayed(createdOnLabel);
	}
	
	public boolean isCopiedTextDisplayed() {
		return isElementDisplayed(copied);
	}
	
	public void selectDeactivateStatusInFilter() {
		clickOnElement(statusFilter);
		clickOnElement(deactivatedStatusInFilter);
	}

}
