package io.mosip.testrig.pmpuiv2.pages;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MispPartnerPage extends BasePage{
	
	private static final Logger logger = Logger.getLogger(MispPartnerPage.class);
	
	@FindBy(id="create_partner_btn")
	private WebElement createPartnerButton;
	
	@FindBy(id="page_title")
	private WebElement createPrtnerPageTitle;
	
	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;
	
	@FindBy(id = "sub_title_btn")
	private WebElement listOfPartnerButton;
	
	@FindBy(id = "create_partner_mandatory_msg")
	private WebElement createPartnerMandatoryMsg;
	
	@FindBy(id = "create_partner_partner_type_dropdown_btn")
	private WebElement partnerTypeDropdown;
	
	@FindBy(id = "policy_group_selector_search_input")
	private WebElement policyGroupDropdownSearchOption;
	
	@FindBy(id = "create_partner_address")
	private WebElement partnerAddressTextbox;
	
	@FindBy(id = "create_partner_organization_name")
	private WebElement partnerOrganisationNameTextBox;
	
	@FindBy(id = "create_partner_contact_number")
	private WebElement partnerContactNumberTextBox;
	
	@FindBy(id = "create_partner_email_id")
	private WebElement partnerEmailIdTextBox;
	
	@FindBy(id = "create_partner_partner_id")
	private WebElement userNameTextBox;
	
	@FindBy(id = "create_partner_lang_code_dropdown_btn")
	private WebElement notificationDropdown;
	
	@FindBy(id = "create_partner_organization_name_info_info_description")
	private WebElement organizationNameInfo;
	
	@FindBy(id = "policy_group_selector_dropdown_button")
	private WebElement policyGroupDropdown;
	
	@FindBy(id = "create_partner_form_submit_btn")
	private WebElement createPartnerSubmitButton;
	
	@FindBy(id = "create_partner_form_cancel_btn")
	private WebElement createPartnerCancelButton;
	
	@FindBy(id = "create_partner_form_clear_btn")
	private WebElement createPartnerClearButton;
	
	@FindBy(id = "create_partner_confirmation_header")
	private WebElement createPartnerSuccessMsg;
	
	@FindBy(id = "confirmation_custom_btn")
	private WebElement uploadPartnerCertificateButton;
	
	@FindBy(id = "confirmation_custom_btn_2")
	private WebElement createPartnerSuccessMsgHomeButton;
	
	@FindBy(id = "create_partner_address_input_error")
	private WebElement partnerAddressSpecialChNotAllowError;
	
	@FindBy(id = "create_partner_organization_name_input_error")
	private WebElement partnerOrgNameSpecialChNotAllowError;
	
	@FindBy(id = "create_partner_contact_number_input_error")
	private WebElement partnerContactSpecialChNotAllowError;
	
	@FindBy(id = "create_partner_email_id_input_error")
	private WebElement partnerEmailIdSpecialChNotAllowError;
	
	@FindBy(id = "create_partner_partner_id_input_error")
	private WebElement partnerUserNameSpecialChNotAllowError;
	
	@FindBy(xpath = "//input[@placeholder='Enter Partner Organization Name']")
	private WebElement partnerOrganizationPlaceholder;
	
	@FindBy(xpath = "//span[text()='Select Policy Group']")
	private WebElement noPolicyGroup;

	public MispPartnerPage(WebDriver driver) {
		super(driver);		
	}
	
	public void clickOnCreatePartnerButton() {
		clickOnElement(createPartnerButton);
	}
	
	public boolean isCreatePrtnerPageTitleDisplayed() {
		return isElementDisplayed(createPrtnerPageTitle);
	}
	
	public String getBreadcrumbTextOfCreatePartnerPage() {
		return getTextFromLocator(homeButton) 
				+ getTextFromLocator(listOfPartnerButton);
	}
	
	public boolean isCreatePartnerMandatoryFieldInfoDisplayed() {
		return isElementDisplayed(createPartnerMandatoryMsg);
	}
	
	public boolean isDefaultMispPartnerDisplayed(String partnerName) {
		WebElement mispPartner= driver.findElement(
		    By.xpath("//button[@id='create_partner_partner_type_dropdown_btn']//span[text()='" + partnerName + "']"));
		return isElementDisplayed(mispPartner);
	}
	
	public void clickOnPartnerTypeDropdown() {
		clickOnElement(partnerTypeDropdown);
	}
	
	public boolean isMispPartnerOnlyDisplayedInDropdown(String partnerName) {
		clickOnElement(partnerTypeDropdown);
		WebElement mispPartner= driver.findElement(
		    By.xpath("//button[@id='create_partner_partner_type_option1']//span[text()='" + partnerName + "']"));
		return isElementDisplayed(mispPartner);
	}
	
	public boolean isOrganizationNameInfoDisplayed() {
		return isElementDisplayed(organizationNameInfo);
	}
	
	public void selectPolicyGroup(String defaultPolicyGroup) {	
		clickOnElement(policyGroupDropdown);
		WebElement policyGroup= driver.findElement(
			    By.xpath("//button[@id='policy_group_selector_search_input']//span[text()='" + defaultPolicyGroup + "']"));
		clickOnElement(policyGroup);
	}
	
	public void selectPartnerType(String value) {
		try {
			dropdown(partnerTypeDropdown, value);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}
	
	public void selectNotificationLanguage(String value) {
		try {
			dropdown(notificationDropdown, value);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}
	
	public void enterPartnerAddress(String address) {
		enter(partnerAddressTextbox, address);
	}
	
	public void enterPartnerOrganisation(String organisation) {
		enter(partnerOrganisationNameTextBox, organisation);
	}
	
	public void enterPartnerContactNumber(String number) {
		enter(partnerContactNumberTextBox, number);
	}
	
	public void enterEmailId(String emailid) {
		enter(partnerEmailIdTextBox, emailid);
	}
	
	public void enterUserName(String username) {
		enter(userNameTextBox, username);
	}
	
	public boolean isCreatePartnerSubmitButtonEnabled() {
		return isElementEnabled(createPartnerSubmitButton);
	}
	
	public boolean isCreatePartnerSuccessMsgDisplayed() {
		return isElementDisplayed(createPartnerSuccessMsg);
	}
	
	public boolean isUploadPartnerCertificateButtonDisplayed() {
		return isElementDisplayed(uploadPartnerCertificateButton);
	}
	
	public void clickOnUploadPartnerCertificateButton() {
		clickOnElement(uploadPartnerCertificateButton);
	}
	
	public boolean isPartnerAddressSpecialChNotAllowErrorDisplayed() {
		return isElementDisplayed(partnerAddressSpecialChNotAllowError);
	}
	
	public boolean isPartnerOrgNameSpecialChNotAllowErrorDisplayed() {
		return isElementDisplayed(partnerOrgNameSpecialChNotAllowError);
	}
	
	public boolean isPartnerContactSpecialChNotAllowErrorDisplayed() {
		return isElementDisplayed(partnerContactSpecialChNotAllowError);
	}
	
	public boolean isPartnerEmailIdSpecialChNotAllowErrorDisplayed() {
		return isElementDisplayed(partnerEmailIdSpecialChNotAllowError);
	}
	
	public boolean isPartnerUserNameSpecialChNotAllowErrorDisplayed() {
		return isElementDisplayed(partnerUserNameSpecialChNotAllowError);
	}
	
	public boolean isCreatePartnerSubmitButtonDisabled() {
		return isElementDisabled(createPartnerSubmitButton);
	}
	
	public boolean isCreatePartnerSuccessMsgHomeButtonDisplayed() {
		return isElementDisplayed(createPartnerSuccessMsgHomeButton);
	}
	
	public boolean isPartnerOrganizationPlaceholderDisplayed() {
		return isElementDisplayed(partnerOrganizationPlaceholder);
	}
	
	public boolean isNoPolicyGroupDisplayed() {
		return isElementDisplayed(noPolicyGroup);
	}
	
	public void clickOnCreatePartnerClearButton() {
		clickOnElement(createPartnerClearButton);
	}

}
