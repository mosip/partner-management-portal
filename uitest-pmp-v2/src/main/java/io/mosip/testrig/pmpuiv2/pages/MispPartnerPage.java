package io.mosip.testrig.pmpuiv2.pages;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
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

	@FindBy(id = "sub_title_btn")
	private WebElement listOfPolicyGroupTitle;

	@FindBy(id = "sub_title_btn")
	private WebElement listOfAuthenticationPolicesTitle;

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

	@FindBy(id = "dashboard_policies_card_header")
	private WebElement policiesButtonInAdmin;

	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButtonInCreatePolicyGroup;

	@FindBy(id = "undefined_title")
	private WebElement listOfPolicyGroupsTitle;

	@FindBy(id = "side_nav_policy_icon")
	private WebElement policyOverlayMenu;

	@FindBy(id = "policies_policy_group_tab")
	private WebElement policyGroupsTabInCreatePolicyGroup;

	@FindBy(id = "policies_auth_policy_tab")
	private WebElement authPolicyTabInCreatePolicyGroup;

	@FindBy(id = "policies_data_share_policy_tab")
	private WebElement datasharePolicyTabInCreatePolicyGroup;

	@FindBy(id = "policies_misp_policy_tab")
	private WebElement mispPolicyTabInCreatePolicyGroup;

	@FindBy(id = "create_policy_group_btn")
	private WebElement createPolicyGroupBtn;

	@FindBy(id = "items_per_page")
	private WebElement itemsPerPages;

	@FindBy(id = "filter_btn")
	private WebElement filterButtonInCreatePolicyGroup;

	@FindBy(id = "title_back_icon")
	private WebElement titleBackArrow;

	@FindBy(id = "create_policy_group_mandatory_field_msg")
	private WebElement mandatoryMessage;

	@FindBy(id = "policy_group_name")
	private WebElement policyGroupNameInputField;

	@FindBy(id = "policy_group_description")
	private WebElement policyGroupDescriptionInputField;

	@FindBy(id = "createPolicy_cancel_btn")
	private WebElement cancelBtnInCreatePolicyGroup;

	@FindBy(id = "createPolicy_submit_btn")
	private WebElement submitBtnInCreatePolicyGroup;

	@FindBy(id = "policyGroupList.policyGroupId_header")
	private WebElement policyGroupIdHeader;

	@FindBy(id = "policyGroupList.policyGroupName_header")
	private WebElement policyGroupNameheader;

	@FindBy(id = "policyGroupList.policyGroupDescription_header")
	private WebElement policyGroupsDescriptionHeader;

	@FindBy(id = "policyGroupList.creationDate_header")
	private WebElement creationDateHeader;

	@FindBy(id = "policyGroupList.status_header")
	private WebElement statusHeader;

	@FindBy(id = "policyGroupList.action_header")
	private WebElement actionsHeader;

	@FindBy(id = "create_auth_policy_btn")
	private WebElement createAuthPolicyHeader;

	@FindBy(id = "create_policy_mandatory_field_msg")
	private WebElement authPolicyMandatoryMessage;

	@FindBy(id = "policy_group_selector_dropdown_button")
	private WebElement authPolicygroupDropdownField;

	@FindBy(id = "upload_policy_data_file_btn")
	private WebElement uploadButtonInAuthPolicy;

	@FindBy(id = "create_policy_form_cancel_btn")
	private WebElement cancelButtonInAuthPolicy;

	@FindBy(id = "create_policy_form_submit_btn")
	private WebElement saveAsDraftButton;

	@FindBy(id = "policiesList.policyId_header")
	private WebElement policyIdHeaderInAuthPolicy;

	@FindBy(id = "policiesList.policyName_header")
	private WebElement policyNameHeaderInAuthPolicy;

	@FindBy(id = "policiesList.policyDescription_header")
	private WebElement policyDescriptionInAuthPolicy;

	@FindBy(id = "policiesList.creationDate_header")
	private WebElement creationDateInAuthPolicy;

	@FindBy(id = "policiesList.status_header")
	private WebElement statusHeaderInAuthPolicy;

	@FindBy(id = "policiesList.action_header")
	private WebElement actionHeaderInAuthPolicy;

	public MispPartnerPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnCreatePartnerButton() {
		clickOnElement(createPartnerButton);
	}

	public boolean isCreatePrtnerPageTitleDisplayed() {
		return isElementDisplayed(createPrtnerPageTitle);
	}

	public boolean isPoliciesButtonDisplayed() {
		return isElementDisplayed(policiesButtonInAdmin);
	}

	public boolean isListOfPolicyGroupsTitleDisplayed() {
		return isElementDisplayed(listOfPolicyGroupsTitle);
	}

	public boolean isPolicyOverlayMenuDisplayed() {
		return isElementDisplayed(policyOverlayMenu);
	}

	public boolean isHomeButtonInCreatePolicyGroupDisplayed() {
		return isElementDisplayed(homeButtonInCreatePolicyGroup);
	}

	public boolean isPolicyGroupsTabInCreatePolicyGroupDisplayed() {
		return isElementDisplayed(policyGroupsTabInCreatePolicyGroup);
	}

	public boolean isAuthPolicyTabInCreatePolicyGroupDisplayed() {
		return isElementDisplayed(authPolicyTabInCreatePolicyGroup);
	}

	public boolean isDataSharePolicyTabInCreatePolicyGroupDisplayed() {
		return isElementDisplayed(datasharePolicyTabInCreatePolicyGroup);
	}

	public boolean isMispPolicyabInCreatePolicyGroupDisplayed() {
		return isElementDisplayed(mispPolicyTabInCreatePolicyGroup);
	}

	public boolean isCreatePolicyGroupBtnDisplayed() {
		return isElementDisplayed(createPolicyGroupBtn);
	}

	public boolean isItemsPerPagesDisplayed() {
		return isElementDisplayed(itemsPerPages);
	}

	public boolean isFilterButtonInCreatePolicyGroupDisplayed() {
		return isElementDisplayed(filterButtonInCreatePolicyGroup);
	}

	public boolean isTitleBackArrowDisplayed() {
		return isElementDisplayed(titleBackArrow);
	}

	public boolean isListOfPoliyGroupTitleDisplayed() {
		return isElementDisplayed(listOfPolicyGroupTitle);
	}

	public boolean isMandatoryMessagesDisplayed() {
		return isElementDisplayed(mandatoryMessage);
	}

	public boolean isPolicyGroupNameInputFieldDisplayed() {
		return isElementDisplayed(policyGroupNameInputField);
	}

	public boolean isPolicyGroupDescriptionInputFieldDisplayed() {
		return isElementDisplayed(policyGroupDescriptionInputField);
	}

	public boolean isCancelBtnInCreatePolicyGroupDisplayed() {
		return isElementDisplayed(cancelBtnInCreatePolicyGroup);
	}

	public boolean isSubmitBtnInCreatePolicyGroupDisplayed() {
		return isElementDisplayed(submitBtnInCreatePolicyGroup);
	}

	public boolean isPolicyGroupIdHeaderDisplayed() {
		return isElementDisplayed(policyGroupIdHeader);
	}

	public boolean isPolicyGroupNameheaderDisplayed() {
		return isElementDisplayed(policyGroupNameheader);
	}

	public boolean isPolicyGroupsDescriptionDisplayed() {
		return isElementDisplayed(policyGroupsDescriptionHeader);
	}

	public boolean isCreationHeaderDisplayed() {
		return isElementDisplayed(creationDateHeader);
	}

	public boolean isStatusHeadersDisplayed() {
		return isElementDisplayed(statusHeader);
	}

	public boolean isActionsHeaderDisplayed() {
		return isElementDisplayed(actionsHeader);
	}

	public boolean isCreateAuthPartnerHeaderDisplayed() {
		return isElementDisplayed(createAuthPolicyHeader);
	}

	public boolean isListOfAuthenticationPolicesTitleDisplayed() {
		return isElementDisplayed(listOfAuthenticationPolicesTitle);
	}

	public boolean isAuthPolicyMessageDisplayed() {
		return isElementDisplayed(authPolicyMandatoryMessage);
	}

	public boolean isAuthPolicyGroupDropDownDisplayed() {
		return isElementDisplayed(authPolicygroupDropdownField);
	}

	public boolean isUploadButtonInAuthPolicyDisplayed() {
		return isElementDisplayed(uploadButtonInAuthPolicy);
	}

	public boolean isCancelButtonInAuthPolicyDisplayed() {
		return isElementDisplayed(cancelButtonInAuthPolicy);
	}

	public boolean isSaveAsDraftButtonInAuthPolicyDisplayed() {
		return isElementDisplayed(saveAsDraftButton);
	}

	public boolean isPolicyIdHeaderInAuthPolicyDisplayed() {
		return isElementDisplayed(policyIdHeaderInAuthPolicy);
	}

	public boolean isPolicyNameHeaderInAuthPolicyDisplayed() {
		return isElementDisplayed(policyNameHeaderInAuthPolicy);
	}

	public boolean isPolicyDescriptionInAuthPolicyDisplayed() {
		return isElementDisplayed(policyDescriptionInAuthPolicy);
	}

	public boolean isCreationDateHeaderInAuthPolicyDisplayed() {
		return isElementDisplayed(creationDateInAuthPolicy);
	}

	public boolean isStatusHeaderInAuthPolicyDisplayed() {
		return isElementDisplayed(statusHeaderInAuthPolicy);
	}

	public boolean isActionHeaderInAuthPolicyDisplayed() {
		return isElementDisplayed(actionHeaderInAuthPolicy);
	}

	public void clickOnPoliciesButton() {
		clickOnElement(policiesButtonInAdmin);
	}

	public void clickOnHomeButtonInCreatePolicyGroup() {
		clickOnElement(homeButtonInCreatePolicyGroup);
	}

	public void clickOnPolicyOverlayMenu() {
		clickOnElement(policyOverlayMenu);
	}

	public void clickOnCreatePolicyBtn() {
		clickOnElement(createPolicyGroupBtn);
	}

	public void clickOnListOfPolicyGroupTitle() {
		clickOnElement(listOfPolicyGroupTitle);
	}

	public void clickOnAuthPolicyTitle() {
		clickOnElement(authPolicyTabInCreatePolicyGroup);
	}

	public void clickOnCreateAuthPartnerHeader() {
		clickOnElement(createAuthPolicyHeader);
	}

	public void clickOnListOfAuthPoliciesTitle() {
		clickOnElement(listOfAuthenticationPolicesTitle);
	}

	public String getBreadcrumbTextOfCreatePartnerPage() {
		return getTextFromLocator(homeButton) + getTextFromLocator(listOfPartnerButton);
	}

	public boolean isCreatePartnerMandatoryFieldInfoDisplayed() {
		return isElementDisplayed(createPartnerMandatoryMsg);
	}

	public boolean isDefaultMispPartnerDisplayed(String partnerName) {
		WebElement mispPartner = driver.findElement(By
				.xpath("//button[@id='create_partner_partner_type_dropdown_btn']//span[text()='" + partnerName + "']"));
		return isElementDisplayed(mispPartner);
	}

	public void clickOnPartnerTypeDropdown() {
		clickOnElement(partnerTypeDropdown);
	}

	public boolean isMispPartnerOnlyDisplayedInDropdown(String partnerName) {
		clickOnElement(partnerTypeDropdown);
		WebElement mispPartner = driver.findElement(
				By.xpath("//button[@id='create_partner_partner_type_option1']//span[text()='" + partnerName + "']"));
		return isElementDisplayed(mispPartner);
	}

	public boolean isOrganizationNameInfoDisplayed() {
		return isElementDisplayed(organizationNameInfo);
	}

	public void selectPolicyGroup(String defaultPolicyGroup) {
		clickOnElement(policyGroupDropdown);
		WebElement policyGroup = driver.findElement(By.xpath(
				"//button[@id='policy_group_selector_search_input']//span[text()='" + defaultPolicyGroup + "']"));
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
