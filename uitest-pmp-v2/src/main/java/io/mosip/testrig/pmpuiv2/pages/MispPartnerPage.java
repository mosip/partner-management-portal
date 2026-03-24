package io.mosip.testrig.pmpuiv2.pages;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MispPartnerPage extends BasePage {

	private static final Logger logger = Logger.getLogger(MispPartnerPage.class);

	@FindBy(id = "create_partner_btn")
	private WebElement createPartnerButton;

	@FindBy(id = "page_title")
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

	@FindBy(id = "confirmation_home_btn")
	private WebElement createPartnerSuccessMsgHomeButton;

	@FindBy(id = "create_partner_address_input_error")
	private WebElement partnerAddressSpecialChNotAllowError;

	@FindBy(id = "create_partner_organization_name_input_error")
	private WebElement partnerOrgNameSpecialChNotAllowError;

	@FindBy(id = "create_partner_contact_number_input_error")
	private WebElement partnerContactNumberNotAllowError;

	@FindBy(id = "create_partner_email_id_input_error")
	private WebElement partnerEmailIdSpecialChNotAllowError;

	@FindBy(id = "create_partner_partner_id_input_error")
	private WebElement partnerUserNameNotAllowError;

	@FindBy(xpath = "//input[@placeholder='Enter Partner Organization Name']")
	private WebElement partnerOrganizationPlaceholder;

	@FindBy(xpath = "//span[text()='Select Policy Group']")
	private WebElement noPolicyGroup;
	
	@FindBy(id = "undefined_title")
	private WebElement listOfPartners;
	
	@FindBy(id = "blocker_prompt_description")
	private WebElement cancelConfirmationPopup;
	
	@FindBy(id = "block_messsage_proceed")
	private WebElement cancelConfirmationPopupProceedButton;
	
	@FindBy(id = "block_message_cancel")
	private WebElement cancelConfirmationPopupCancelButton;
	
	@FindBy(id = "create_partner_error_msg")
	private WebElement createPartnerErrorMessage;
	
	@FindBy(id = "error_close_btn")
	private WebElement errorMessageCloseButton;
	
	@FindBy(id = "create_partner_email_id_cancel_btn")
	private WebElement partnerEmailIdCancelBtn;

	@FindBy(id = "create_partner_partner_type_option3")
	private WebElement mispPartner;
	
	@FindBy(id = "create_partner_organization_name_info")
	private WebElement oragnizationInfoButton;
	
	@FindBy(id = "policy_group_selector_search_input")
	private WebElement policyGroupDropdownSearchInput;

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
		return getTextFromLocator(homeButton) + getTextFromLocator(listOfPartnerButton);
	}

	public boolean isCreatePartnerMandatoryFieldInfoDisplayed() {
		return isElementDisplayed(createPartnerMandatoryMsg);
	}

	public boolean isDefaultMispPartnerDisplayed() {
		clickOnElement(partnerTypeDropdown);
		return isElementDisplayed(mispPartner);
	}

	public void clickOnPartnerTypeDropdown() {
		clickOnElement(partnerTypeDropdown);
	}

	public boolean isOrganizationNameInfoDisplayed() {
		return isElementDisplayed(organizationNameInfo);
	}

	public void selectPolicyGroupDropdown(String value) {
		clickOnElement(policyGroupDropdown);
		clearTextBox(policyGroupDropdownSearchInput);
		enter(policyGroupDropdownSearchInput, value);
		By policyGroupOption = By.xpath("//span[normalize-space()='" + value + "']");
		try {
			waitScrollAndClick(policyGroupOption);
		} catch (TimeoutException | NoSuchElementException e) {
			logger.warn("Policy group not found: " + value);
			throw new NoSuchElementException("Failed to select policy group: " + value, e);
		}
	}
	
	public void enterInvalidPolicyGroup(String value) {
		clickOnElement(policyGroupDropdown);
		clearTextBox(policyGroupDropdownSearchInput);
		enter(policyGroupDropdownSearchInput, value);
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
		return isElementDisplayed(partnerContactNumberNotAllowError);
	}

	public boolean isPartnerEmailIdSpecialChNotAllowErrorDisplayed() {
		return isElementDisplayed(partnerEmailIdSpecialChNotAllowError);
	}

	public boolean isPartnerUserNameSpecialChNotAllowErrorDisplayed() {
		return isElementDisplayed(partnerUserNameNotAllowError);
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
	
	public void clickOnCreatePartnerCancelButton() {
		clickOnElement(createPartnerCancelButton);
	}
	
	public boolean isListOfPartnersDisplayed() {
		return isElementDisplayed(listOfPartners);
	}
	
	public boolean isCancelConfirmationPopupDisplayed() {
		return isElementDisplayed(cancelConfirmationPopup);
	}
	
	public boolean iscancelConfirmationPopupProceedButtonDisplayed() {
		return isElementDisplayed(cancelConfirmationPopupProceedButton);
	}
	
	public boolean isCancelConfirmationPopupCancelButtonDisplayed() {
		return isElementDisplayed(cancelConfirmationPopupCancelButton);
	}
	
	public void clickOnCancelConfirmationPopupProceedButton() {
		clickOnElement(cancelConfirmationPopupProceedButton);
	}
	
	public void clickOnCreatePartnerSubmitButton() {
		clickOnElement(createPartnerSubmitButton);
	}
	
	public void clickOnSuccessMsgHomeButton() {
		clickOnElement(createPartnerSuccessMsgHomeButton);
	}
	
	public boolean isPartnerContactNumberNotAllowErrorDisplayed() {
		return isElementDisplayed(partnerContactNumberNotAllowError);
	}
	
	public void clickOnPartnerEmailIdTextBox() {
		clickOnElement(partnerEmailIdTextBox);
	}
	
	public boolean isUsernameMustStartWithLetterErrorDisplayed() {
		return isElementDisplayed(partnerUserNameNotAllowError);
	}
	
	public boolean isEmailAddressIsAlreadyRegisteredErrorDisplayed() {
		return isElementDisplayed(createPartnerErrorMessage);
	}
	
	public void clickOnErrorMessageCloseButton() {
		clickOnElement(errorMessageCloseButton);
	}
	
	public void clickOnPartnerEmailIdCancelBtn() {
		clickOnElement(partnerEmailIdCancelBtn);
	}
	
	public boolean isUsernameAlreadyExistErrorDisplayed() {
		return isElementDisplayed(createPartnerErrorMessage);
	}
	
	public void clickOnPartnerOragnizationInfoButton() {
		clickOnElement(oragnizationInfoButton);
	}
	
	public void clickOnUserNameTextBox() {
		clickOnElement(userNameTextBox);
	}

}
