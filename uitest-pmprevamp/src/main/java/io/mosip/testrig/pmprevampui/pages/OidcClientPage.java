package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OidcClientPage extends BasePage {

	@FindBy(id = "create_policy_group_btn")
	private WebElement createOidcClient;

	@FindBy(xpath = "//*[contains(text(), 'Partner ID')]")
	private WebElement partnerIDHeaderText;

	@FindBy(xpath = "//*[contains(text(), 'Policy Group')]")
	private WebElement PolicyGroupHeaderText;

	@FindBy(xpath = "//*[contains(text(), 'Policy Name')]")
	private WebElement PolicyNameHeaderText;

	@FindBy(xpath = "//*[contains(text(), 'OIDC Client Name')]")
	private WebElement OIDCClientNameHeaderText;

	@FindBy(xpath = "//*[contains(text(), 'Created Date')]")
	private WebElement CreatedDateHeaderText;

	@FindBy(xpath = "//*[contains(text(), 'Status')]")
	private WebElement StatusHeaderText;

	@FindBy(xpath = "//*[contains(text(), 'OIDC Client ID')]")
	private WebElement OIDCClientIDHeaderText;

	@FindBy(xpath = "//*[contains(text(), 'Action')]")
	private WebElement ActionHeaderText;

	@FindBy(id = "authentication_apikey_tab")
	private WebElement ApiKeyTab;

	@FindBy(id = "generate_api_key_btn")
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
	
	@FindBy(xpath = "(//*[contains(text(), 'Enter a valid URI')])[1]")
	private WebElement enterValidUriForLogoUriText;

	@FindBy(xpath = "(//*[contains(text(), 'Enter a valid URI')])[2]")
	private WebElement enterValidUriForRedirectUriText;

	@FindBy(xpath = "//*[contains(text(), 'Authorization Code')]")
	private WebElement authorizationCode;

	@FindBy(id = "create_oidc_partner_id")
	private WebElement SelectPartneridForOidc;

	@FindBy(id = "create_oidc_policy_name")
	private WebElement SelectPolicyNameForOidc;

	@FindBy(id = "create_oidc_policy_name_search_input")
	private WebElement createOidcPolicyNameSearchInput;

	@FindBy(id = "create_oidc_policy_name_option1")
	private WebElement createOidcPolicyNameOption1;

	@FindBy(xpath = "//*[contains(text(), 'Details Submitted Successfully!')]")
	private WebElement detailsSubmittedSuccessfully;

	@FindBy(xpath = "//*[contains(text(), 'Activated')]")
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

	@FindBy(xpath = "//*[contains(text(), 'Modified details submitted successfully!')]")
	private WebElement ModifiedSuccessfullTextMessage;

	@FindBy(id = "filter_btn")
	private WebElement filterButton;

	@FindBy(id = "partnerId_desc_icon")
	private WebElement partnerId_desc_icon;

	@FindBy(id = "partnerId_asc_icon")
	private WebElement partnerId_asc_icon;

	@FindBy(id = "oidcClientName_desc_icon")
	private WebElement oidcClientName_desc_icon;

	@FindBy(id = "oidcClientName_asc_icon")
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
	private WebElement oidcSelectStatusFilterOption1;
	
	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;
	
	

	public OidcClientPage(WebDriver driver) {
		super(driver);
	}

	public boolean isCreateOidcClientDisplayed() {
		return isElementDisplayed(createOidcClient);
	}

	public void clickOnCreateOidcClientButton() {
		clickOnElement(createOidcClient);
	}

	public void selectPartnerIdDropdown(String value) {
		clickOnElement(SelectPartneridForOidc);
		clickOnElement(driver.findElement(By.xpath("(//*[contains(text(),'" + value + "')])[2]")));
	}

	public boolean isPartnerIdDropdownDisplayed() {
		return isElementDisplayed(SelectPartneridForOidc);
	}

	public boolean isPolicyNameDropdownDisplayed() {
		return isElementDisplayed(SelectPolicyNameForOidc);
	}

	public void selectPolicyNameDropdown(String value) {
		clickOnElement(SelectPolicyNameForOidc);
		enter(createOidcPolicyNameSearchInput, value);
		clickOnElement(createOidcPolicyNameOption1);
	}

	public void enterNameOidcTextBox(String value) {
		enter(EnterNameOidcTextBox, value);
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

	public void clickOnApiKeyTab() {
		clickOnElement(ApiKeyTab);
	}

	public void enterCreateOidcPolicyNameSearchInput(String value) {
		enter(createOidcPolicyNameSearchInput, value);
	}

	public boolean isDetailsSubmittedSuccessFullyDisplayed() {
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

	public void clickCopyIdButton() {
		clickOnElement(copyIdButton);
	}

	public void clickCopyIdCloseButton() {
		clickOnElement(copyIdCloseButton);
	}

	public void clickOidcEditButton() {
		clickOnElement(oidcEditButton);
	}

	public void clickoidcEditAddNewRedirectUrl() {
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

	public boolean isFilterButtonButtonEnabled() {
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
	
	public void clickOnOidcSelectStatusFilter() {
		clickOnElement(oidcSelectStatusFilter);
		clickOnElement(oidcSelectStatusFilterOption1);
	}
	
	public boolean isfilterResetButtonDisplayed() {
		return isElementDisplayed(filterResetButton);
	}
	
	public void clickOnFilterResetButton() {
		clickOnElement(filterResetButton);
	}
	
	public void clickOnOidcEditSubmitButton() {
		clickOnElement(oidcEditSubmitButton);
	}
	
	
	
}
