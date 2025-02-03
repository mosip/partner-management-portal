package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

	@FindBy(xpath = "(//*[contains(text(), 'Enter a valid URI')])[1]")
	private WebElement enterValidUriForLogoUriText;

	@FindBy(xpath = "(//*[contains(text(), 'Enter a valid URI')])[2]")
	private WebElement enterValidUriForRedirectUriText;

	@FindBy(xpath = "//*[contains(text(), 'Authorization Code')]")
	private WebElement authorizationCode;
	
	@FindBy(xpath = "//*[contains(text(), 'User Id does not exists')]")
	private WebElement userIdDoesNotExistsPopup;
	
	@FindBy(xpath = "//*[contains(text(), 'No Data Available.')]")
	private WebElement NoDataAvailableText;
	
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
	private WebElement oidcSelectStatusFilterOption1;

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
	private WebElement subTitleHomeButton;
	
	@FindBy(id = "create_oidc_btn")
	private WebElement oidcClientListPageCreateOidcClientBtn;

	@FindBy(xpath = "//*[contains(text(), 'Your changes will be lost, are you sure you want to proceed?')]")
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
	
	public boolean isApiKeyTabDisplayed() {
		return isElementDisplayed(ApiKeyTab);
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
	
	public boolean isUserIdDoesNotExistsPopupDisplayed() {
		return isElementDisplayed(userIdDoesNotExistsPopup);
	}
	
	public boolean isNoDataAvailableTextDisplayed() {
		return isElementDisplayed(NoDataAvailableText);
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
	
	public boolean isSubTitleHomeButtonDisplayed() {
		return isElementDisplayed(subTitleHomeButton);
	}
	public void clickOnOidcClientListPageCreateOidcClientBtn() {
		clickOnElement(oidcClientListPageCreateOidcClientBtn);
	}
	
	public void navigateBackDefaultButton() {
		driver.navigate().back();
	}
	
	public void navigateRefreshDefaultButton() {
		driver.navigate().refresh();
	}
	
	public boolean isBrowserConfirmationPopupDisplayed() {
		return isElementDisplayed(browserConfirmationPopup);
	}
	
	public boolean isBrowserConfirmationPopupProceedBtnDisplayed() {
		return isElementDisplayed(browserConfirmationPopupProceedBtn);
	}
	
	public void clickOnBrowserConfirmationPopupProceedBtn() {
		clickOnElement(browserConfirmationPopupProceedBtn);
	}
	
	public boolean isBrowserConfirmationPopupCancelBtnDisplayed() {
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
}
