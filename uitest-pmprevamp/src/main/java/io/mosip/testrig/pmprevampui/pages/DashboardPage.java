package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

	@FindBy(id = "header_user_profile_title")
	private WebElement profileDropdown;
	
	@FindBy(id = "dashboard_authentication_clients_list_card_header")
	private WebElement authenticationHeader;
	
	@FindBy(id = "header_user_profile_logout_btn")
	private WebElement logoutButton;

	@FindBy(xpath = "//*[text()='Select Policy Group']")
	private WebElement selectPolicyGroupPopUp;

	@FindBy(xpath = "//div[@class='relative w-full']/button")

	private WebElement selectPolicyGroupDropdown;

	@FindBy(xpath = "//*[text()='No Data Available.']")
	private WebElement noDataAvailableText;

	@FindBy(id = "select_policy_group_dropdown_search_input")
	private WebElement SearchBox;

	@FindBy(id = "select_policy_group_submit")
	private WebElement submitButton;

	@FindBy(xpath = "//*[@class='min-h-2']")
	private WebElement value;

	@FindBy(xpath = "//*[text()='Terms and Conditions']")
	private WebElement termsAndConditionsPopUp;

	@FindBy(id = "default-checkbox")
	private WebElement checkbox;

	@FindBy(id = "consent_proceed_btn")
	private WebElement proceedButton;

	@FindBy(id = "dashboard_partner_certificate_list_card")
	private WebElement partnerCertificateTitle;

	@FindBy(id = "dashboard_policies_card")
	private WebElement policiesTitle;

	@FindBy(id = "dashboard_authentication_clients_list_card")
	private WebElement AuthenticationServices;
	
	@FindBy(id = "dashboard_device_provider_service_card")
	private WebElement deviceProviderServices;
	
	@FindBy(id = "welcome_msg")
	private WebElement welcomeMessage;
	
	@FindBy(id = "side_nav_device_provider_service_icon")
	private WebElement sideNavDeviceProvider;
	
	@FindBy(id = "header_hamburger_open_sidenav")
	private WebElement hamburgerOpen;
	
	@FindBy(id = "header_hamburger_close_sidenav")
	private WebElement hamburgerClose;
	
	@FindBy(id = "select_policy_group_view_text")
	private WebElement selectPolicyGroupViewMoreAndLess;

	@FindBy(id = "select_policy_group_logout")
	private WebElement selectPolicyGroupLogout;

	@FindBy(id = "select_policy_group_submit")
	private WebElement selectPolicyGroupSubmit;

	@FindBy(id = "select_policy_group_dropdown_option1")
	private WebElement selectPolicyGrouDropdownOption1;
	
	@FindBy(xpath = "//*[text()='Root of Trust Certificate']")
	private WebElement RootOFTrustCertText;
	
	@FindBy(id = "rootCertificateList.uploadRootCaCertificate")
	//@FindBy(id = "root_certificate_upload_btn")
	//@FindBy(id = "upload_root_trust_certificate_btn")
	@FindBy(id = "upload_certificate_btn")
	private WebElement rootCertificateUploadButton;

	@FindBy(xpath = "//*[text()='Policies']")
	private WebElement policyButton;
	
	@FindBy(xpath = "//*[text()='Partner - Policy Linking']")
	private WebElement PartnerPolicyMappingTab;
	
	public DashboardPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnProfileDropdown() {
		clickOnElement(profileDropdown);
	}

	public LoginPage clickOnLogoutButton() {
		clickOnElement(logoutButton);
		return new LoginPage(driver);
	}

	public boolean isLogoutButtonDisplayed() {
		return isElementDisplayed(logoutButton);
	}

	public boolean isSelectPolicyGroupPopUpDisplayed() {
		
		return isElementDisplayed(selectPolicyGroupPopUp);
	}

	public boolean isSubmitButtonSelectPolicyGroupPopUpDisplayed() {
		return isElementDisplayed(submitButton);
	}

	public void selectSelectPolicyGroupDropdown(String value) {
		clickOnElement(selectPolicyGroupDropdown);
		if(!isElementDisplayed(SearchBox)){
		clickOnElement(selectPolicyGroupDropdown);
		}
		enter(SearchBox, value);
		String val = "'" + value + "'";
//		click(By.xpath("//*[contains(text(),"+val+")]"));
		clickOnElement(selectPolicyGrouDropdownOption1);
	}

	public void selectSelectPolicyGroupDropdownForInvalid(String value) {
		clickOnElement(selectPolicyGroupDropdown);
		enter(SearchBox, value);
		String val = "'" + value + "'";
//		clickOnElement(value);
	}

	public void clickOnSubmitButton() {
		clickOnElement(submitButton);
	}

	public boolean isTermsAndConditionsPopUppDisplayed() {
		return isElementDisplayed(termsAndConditionsPopUp);
	}

	public void clickOnCheckbox() {
		clickOnElement(checkbox);
	}

	public boolean isProceedButtonDisplayed() {
		return isElementDisplayed(proceedButton);
	}

	public void clickOnProceedButton() {
		clickOnElement(proceedButton);
	}

	public boolean isPartnerCertificateTitleDisplayed() {
		return isElementDisplayed(partnerCertificateTitle);
	}

	public boolean isPoliciesTitleDisplayed() {
		return isElementDisplayed(policiesTitle);
	}

	public boolean isAuthenticationServicesTitleDisplayed() {
		return isElementDisplayed(AuthenticationServices);
	}
	
	public boolean isDeviceProviderServicesTitleDisplayed() {
		return isElementDisplayed(deviceProviderServices);
	}
	
	public boolean isWelcomeMessageDisplayed() {
		return isElementDisplayed(welcomeMessage);
	}

	public PoliciesPage clickOnPoliciesTitle() {
		clickOnElement(policiesTitle);
		return new PoliciesPage(driver);
	}

	public PartnerCertificatePage clickOnPartnerCertificateTitle() {
		clickOnElement(partnerCertificateTitle);
		return new PartnerCertificatePage(driver);
	}

	public OidcClientPage clickOnAuthenticationServicesTitle() {
		clickOnElement(AuthenticationServices);
		return new OidcClientPage(driver);
	}
	
	public DeviceProviderPage clickOnDeviceProviderServicesTitle() {
		clickOnElement(deviceProviderServices);
		return new DeviceProviderPage(driver);
	}

	public boolean isSelectPolicyGroupViewMoreAndLess() {
		return isElementDisplayed(selectPolicyGroupViewMoreAndLess);
	}

	public boolean isNoDataAvailableTextDisplayed() {
		return isElementDisplayed(noDataAvailableText);
	}

	public boolean isSelectPolicyGroupSubmitEnabled() {
		return isElementEnabled(selectPolicyGroupSubmit);
	}

	public void clickOnSelectPolicyGroupSubmit() {
		clickOnElement(selectPolicyGroupSubmit);
	}

	public void clickOnSelectPolicyGroupLogout() {
		clickOnElement(selectPolicyGroupLogout);
	}
	
	public void clickOnAuthenticationHeader() {
		clickOnElement(authenticationHeader);
	}
	
	public void clickOnRootOFTrustCertText() {
		clickOnElement(RootOFTrustCertText);
	}
	
	public void clickOnRootCertificateUploadButton() {
		clickOnElement(rootCertificateUploadButton);
	}
	
	public void clickOnPolicyButton() {
		clickOnElement(policyButton);
	}
	
	public void clickOnPartnerPolicyMappingTab() {
		clickOnElement(PartnerPolicyMappingTab);
	}
	
	public void clickOnHamburgerOpen() {
		clickOnElement(hamburgerOpen);
	}
	
	public void clickOnHamburgerClose() {
		clickOnElement(hamburgerClose);
	}
	
	public DeviceProviderPage clickOnSideNavDeviceProvider() {
		clickOnElement(sideNavDeviceProvider);
		return new DeviceProviderPage(driver);
	}
	
	public String getSideNavDeviceProviderTitle() {
		return getTextFromLocator(sideNavDeviceProvider);
	}
	
}
