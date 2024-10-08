package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OidcClientPage extends BasePage{	
	
	@FindBy(id = "create_oidc_btn")
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
	
	@FindBy(id = "create_oidc_logo_url")
	private WebElement enterLogoUriTextBox;
	
	@FindBy(id = "create_oidc_redirect_url1")
	private WebElement enterRedirectUriTextBox;
	
	@FindBy(id = "create_oidc_submit_btn")
	private WebElement submitButton;
	
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
	
	
	
	
	public OidcClientPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean isCreateOidcClientDisplayed() {
		return isElementDisplayed(createOidcClient);
	}
	
	public  void clickOnCreateOidcClientButton() {
		clickOnElement(createOidcClient);
	}
	
	public  void selectPartnerIdDropdown(String value) {
		clickOnElement(SelectPartneridForOidc);
		clickOnElement(driver.findElement(By.xpath("(//*[contains(text(),'"+value+"')])[2]")));
	}
	
	public boolean isPartnerIdDropdownDisplayed() {
		return isElementDisplayed(SelectPartneridForOidc);
	}
	
	public boolean isPolicyNameDropdownDisplayed() {
		return isElementDisplayed(SelectPolicyNameForOidc);
	}
	
	public  void selectPolicyNameDropdown(String value) {
		dropdownByIndex(SelectPolicyNameForOidc,0);
	}
	
	public  void enterNameOidcTextBox(String value) {
		enter(EnterNameOidcTextBox,value);
	}
	
	public  void enterPublicKeyTextBox(String value) {
		enter(EnterPublickeyTextBox,value);
	}
	
	public  void enterLogoUrTextBox(String value) {
		enter(enterLogoUriTextBox,value);
	}
	
	public  void enterRedirectUriTextBox(String value) {
		enter(enterRedirectUriTextBox,value);
	}
	
	public  void clickOnSubmitButton() {
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
	
	public  void clickOnApiKeyTab() {
		clickOnElement(ApiKeyTab);
	}
	
}
