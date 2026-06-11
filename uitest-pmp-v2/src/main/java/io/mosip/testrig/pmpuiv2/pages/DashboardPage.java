package io.mosip.testrig.pmpuiv2.pages;

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

	@FindBy(xpath = "//h3[text()='Select Policy Group']")
	private WebElement selectPolicyGroupPopUp;

	@FindBy(xpath = "//span[text()='Select Policy Group']")
	private WebElement selectPolicyGroupDropdownForInvalid;

	@FindBy(id = "policy_group_selector_dropdown_button")
	private WebElement selectPolicyGroupDropdown;

	@FindBy(id = "select_policy_group_dropdown_no_data_available")
	private WebElement noDataAvailableText;

	@FindBy(id = "policy_group_selector_search_input")
	private WebElement SearchBox;

	@FindBy(id = "select_policy_group_submit_btn")
	private WebElement submitButton;

	@FindBy(xpath = "//*[@class='min-h-2']")
	private WebElement value;

	@FindBy(xpath = "//*[text()='Terms and Conditions']")
	private WebElement termsAndConditionsPopup;

	@FindBy(id = "default-checkbox")
	private WebElement checkbox;

	@FindBy(id = "consent_proceed_btn")
	private WebElement proceedButton;

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

	@FindBy(id = "hamburger_close_icon")
	private WebElement hamburgerOpen;

	@FindBy(id = " hamburger_open_icon")
	private WebElement hamburgerClose;

	@FindBy(id = "select_policy_group_view_text")
	private WebElement selectPolicyGroupViewMoreAndLess;

	@FindBy(id = "select_policy_group_logout")
	private WebElement selectPolicyGroupLogout;

	@FindBy(id = "select_policy_group_submit")
	private WebElement selectPolicyGroupSubmit;

	@FindBy(id = "policy_group_selector_option_1")
	private WebElement selectPolicyGrouDropdownOption1;

	@FindBy(xpath = "//h5[text()='Certificate Trust Store']")
	private WebElement RootOFTrustCertText;

	@FindBy(xpath = "//h5[text()='Policies']")
	private WebElement policyButton;

	@FindBy(xpath = "//h5[text()='Partner - Policy Linking']")
	private WebElement PartnerPolicyMappingTab;

	@FindBy(xpath = "//h5[text()='SBI - Device']")
	private WebElement sbiDeviceHeader;

	@FindBy(id = "dashboard_partner_certificate_list_header")
	private WebElement dashboardPartnerCertificateListHeader;

	@FindBy(xpath = "//h5[text()='FTM Chip']")
	private WebElement FTMChipTab;

	@FindBy(id = "admin_partner_certificate_list_icon")
	private WebElement certificateTrustStore;

	@FindBy(id = "dashboard_ftm_chip_provider_card_header")
	private WebElement dashboardFtmChipproviderCardHeader;

	@FindBy(id = "dashboard_authentication_clients_list_card_description")
	private WebElement authenticationServiceInfoText;

	@FindBy(id = "dashboard_authentication_clients_list_icon")
	private WebElement authenticationServiceIcon;

	@FindBy(id = "side_nav_home_icon")
	private WebElement homeOptionOfHamburger;

	@FindBy(id = "side_nav_partner_certificate_icon")
	private WebElement partnerCertificateOfHamburger;

	@FindBy(id = "side_nav_partner_icon")
	private WebElement partnerOfHamburger;

	@FindBy(id = "side_nav_policies_icon")
	private WebElement policiesOfHamburger;

	@FindBy(id = "side_nav_authentication_service_icon")
	private WebElement authenticationServiceOfHamburger;

	@FindBy(id = "orgIcon")
	private WebElement organizationIconWithName;

	@FindBy(id = "footer_contact_us_link")
	private WebElement contactusLink;

	@FindBy(xpath = "//h5[text()='Authentication Services']")
	private WebElement authenticationServices;

	@FindBy(id = "dashboard_partner_card_header")
	private WebElement partners;

	@FindBy(xpath = "//button[contains(@class, 'bg-blue-50') and contains(@class, 'cursor-pointer')]")
	private WebElement notificationIcon;

	@FindBy(xpath = "//p[normalize-space()='2024 © MOSIP - All rights reserved.']")
	private WebElement mosipRightsText;

	@FindBy(id = "footer_documentation_link")
	private WebElement footerDocumentationLink;

	@FindBy(id = "policy_group_selector_option_button_1")
	private WebElement policyGroupOption;
	
	@FindBy(id = "admin_misp_partner_services_card_header")
	private WebElement mispServices;

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

	public void selectPolicyGroupDropdown(String policyGroupValue) {
		By selectPolicyGroupDropdown = By.id("policy_group_selector_dropdown_button");
		click(selectPolicyGroupDropdown);
		enter(SearchBox, policyGroupValue);
		By policyGroupOption = By.xpath("//span[normalize-space()='" + policyGroupValue + "']");
		click(policyGroupOption);
	}

	public void closePolicyGroupDropdown() {
		clickOnElement(selectPolicyGroupDropdown);
	}

	public void clickOnSubmitButton() {
		clickOnElement(submitButton);
	}

	public boolean isSubmitButtonEnabled() {
		return isElementEnabled(submitButton);
	}

	public boolean isTermsAndConditionsPopupDisplayed() {
		return isElementDisplayed(termsAndConditionsPopup);
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
		return isElementDisplayed(dashboardPartnerCertificateListHeader);
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
		By dashboardPartnerCertificateListTitel = By.id("dashboard_partner_certificate_list_header");
		click(dashboardPartnerCertificateListTitel);
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

	public void clickOnPolicyButton() {
		clickOnElement(policyButton);
	}

	public void clickOnPartnerPolicyMappingTab() {
		clickOnElement(PartnerPolicyMappingTab);
	}

	public void clickOnDashboardPartnerCertificateListHeader() {
		clickOnElement(dashboardPartnerCertificateListHeader);
	}

	public void clickOnFTMChipTab() {
		clickOnElement(FTMChipTab);
	}

	public void clickOnDashboardFtmChipproviderCardHeader() {
		clickOnElement(dashboardFtmChipproviderCardHeader);
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

	public void clickOnSbiDevices() {
		clickOnElement(sbiDeviceHeader);
	}

	public void clickOnCertificateTrustStore() {
		clickOnElement(certificateTrustStore);
	}

	public boolean isAuthenticationServiceInfoTextDisplayed() {
		return isElementEnabled(authenticationServiceInfoText);
	}

	public boolean isAuthenticationServiceIconDisplayed() {
		return isElementEnabled(authenticationServiceIcon);
	}

	public boolean isHumburgerOptionsExpandable() {
		return isElementEnabled(homeOptionOfHamburger);
	}

	public void clickOnHomeOptionOfHamburger() {
		clickOnElement(homeOptionOfHamburger);
	}

	public void clickOnPartnerCertificateOfHamburger() {
		clickOnElement(partnerCertificateOfHamburger);
	}

	public PartnerAdminPage clickOnPartnerOfHamburger() {
		clickOnElement(partnerOfHamburger);
		return new PartnerAdminPage(driver);
	}

	public void clickOnPoliciesOfHamburger() {
		clickOnElement(policiesOfHamburger);
	}

	public void clickOnAuthenticationServiceOfHamburger() {
		clickOnElement(authenticationServiceOfHamburger);
	}

	public boolean isHumburgerOptionsCollapse() {
		return isElementEnabled(hamburgerOpen);
	}

	public boolean isOrganizationIconWithNameDisplayed() {
		return isElementEnabled(organizationIconWithName);
	}

	public boolean isContactusLinkDisplayed() {
		return isElementEnabled(contactusLink);
	}

	public void clickOnContactusLink() {
		clickOnElement(contactusLink);
	}

	public boolean isSideNavigationHomeIconDisplayed() {
		return isElementEnabled(homeOptionOfHamburger);
	}

	public boolean isAuthenticationServicesDisplayed() {
		return isElementDisplayed(authenticationServices);
	}

	public OidcClientPage clickOnAuthenticationServices() {
		clickOnElement(authenticationServices);
		return new OidcClientPage(driver);
	}

	public boolean isCertificateTrustStoreDisplayed() {
		return isElementDisplayed(certificateTrustStore);
	}

	public boolean isPartnersDisplayed() {
		return isElementDisplayed(partners);
	}

	public void clickOnPartners() {
		clickOnElement(partners);
	}

	public boolean isNotificationIconDisplayed() {
		return isElementDisplayed(notificationIcon);
	}

	public NotificationPage clickOnNotificationIcon() {
		clickOnElement(notificationIcon);
		return new NotificationPage(driver);
	}

	public boolean isMosipRightsTextDisplayed() {
		return isElementDisplayed(mosipRightsText);
	}

	public boolean isFooterDocumentationLinkDisplayed() {
		return isElementDisplayed(footerDocumentationLink);
	}
	
	public MispServicesPage clickOnMispServices() {
		clickOnElement(mispServices);
		return new MispServicesPage(driver);
	}

}
