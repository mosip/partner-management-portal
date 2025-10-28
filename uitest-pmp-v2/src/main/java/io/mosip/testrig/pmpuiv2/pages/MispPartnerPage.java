package io.mosip.testrig.pmpuiv2.pages;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;

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

	@FindBy(id = "sub_title_btn")
	private WebElement listOfDataSharePolicesTitle;

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

	@FindBy(xpath = "//tr[starts-with(@id, 'policies_list_item')]/td[6]/div")
	private WebElement draftButton;

	@FindBy(id = "dashboard_policies_card_header")
	private WebElement policiesButtonInAdmin;

	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButtonInCreatePolicyGroup;

	@FindBy(id = "undefined_title")
	private WebElement listOfPolicyGroupsTitle;

	@FindBy(id = "undefined_title")
	private WebElement listOfPoliciesTitleInMispPolicy;

	@FindBy(id = "side_nav_policy_icon")
	private WebElement policyOverlayMenu;

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
	private WebElement cancelBtnInMispPartnerPage;

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
	private WebElement createMispPolicyHeader;

	@FindBy(id = "upload_policy_data_file_btn")
	private WebElement uploadButtonInMispPolicy;

	@FindBy(id = "create_policy_form_submit_btn")
	private WebElement saveAsDraftButtonInMispPolicies;

	@FindBy(id = "policy_name_box")
	private WebElement policyNameInMispPolicy;

	@FindBy(id = "policy_description_box")
	private WebElement policyDescriptionInMispPolicy;

	@FindBy(id = "policy_group_selector_option_name_1")
	private WebElement policyGroupDropDownInMispPolicies;

	@FindBy(id = "fileInput")
	private WebElement uploadFile;

	@FindBy(id = "create_policy_confirmation_description")
	private WebElement messageDisplayInCreateMispPolicy;

	@FindBy(id = "confirmation_go_back_btn")
	private WebElement backButtonInMispPolicySuccessPage;

	@FindBy(id = "policies_list_view1")
	private WebElement actionTabInMispPartner;

	@FindBy(id = "policy_details_view_btn")
	private WebElement viewButtonInMispPolicy;

	@FindBy(id = "page_title")
	private WebElement viewMispPolicyInViewPage;

	@FindBy(id = "auth_Policy_view_back_btn")
	private WebElement backButtonInViewMispPolicy;

	@FindBy(id = "policy_deactivate_btn")
	private WebElement deactivatePopUp;

	@FindBy(id = "policy_edit_btn")
	private WebElement editButtonInMispPolicy;

	@FindBy(id = "policy_publish_btn")
	private WebElement publishBtnInActionTab;

	@FindBy(id = "publish_policy_popup_policy_name")
	private WebElement popUpInPublishBtn;

	@FindBy(id = "publish_policy_button")
	private WebElement publishBtn;

	@FindBy(id = "publish_policy_cancel")
	private WebElement cancelBtnInPublishTab;

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
		return isElementDisplayed(cancelBtnInMispPartnerPage);
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

	public boolean isListOfAuthenticationPolicesTitleDisplayed() {
		return isElementDisplayed(listOfAuthenticationPolicesTitle);
	}

	public boolean isListOfDataSharePolicesTitleDisplayed() {
		return isElementDisplayed(listOfDataSharePolicesTitle);
	}

	public boolean isCreateMispPolicyHeaderDisplayed() {
		return isElementDisplayed(createMispPolicyHeader);
	}

	public boolean isListOfPoliciesTitleInMispPolicyDisplayed() {
		return isElementDisplayed(listOfPoliciesTitleInMispPolicy);
	}

	public boolean isPolicyNameInMispPolicyDisplayed() {
		return isElementDisplayed(policyNameInMispPolicy);
	}

	public boolean isPolicyDescriptionInMispPolicyDisplayed() {
		return isElementDisplayed(policyDescriptionInMispPolicy);
	}

	public boolean isSubmitBtnInMispPoliciesDisabledDisplayed() {
		return isElementDisplayed(saveAsDraftButtonInMispPolicies);
	}

	public boolean isUploadBtnInMispPolicyDisplayed() {
		return isElementDisplayed(uploadButtonInMispPolicy);
	}

	public boolean isSuccessMessageInMispPolicyDisplayed() {
		return isElementDisplayed(messageDisplayInCreateMispPolicy);
	}

	public boolean isViewButtonInMispPolicyDisplayed() {
		return isElementDisplayed(viewButtonInMispPolicy);
	}

	public boolean isViewMispPolicyDisplayed() {
		return isElementDisplayed(viewMispPolicyInViewPage);
	}

	public boolean isDeactivatePopUpDisplayed() {
		return isElementDisplayed(deactivatePopUp);
	}

	public boolean isEditBtnInActionTabDisplayed() {
		return isElementDisplayed(editButtonInMispPolicy);
	}

	public boolean isDraftBtnInEditPageDisplayed() {
		return isElementDisplayed(editButtonInMispPolicy);
	}

	public boolean isPublishBtnInActionTabDisplayed() {
		return isElementDisplayed(publishBtnInActionTab);
	}

	public boolean isPopUpInPublishBtnDisplayed() {
		return isElementDisplayed(popUpInPublishBtn);
	}

	public boolean isPublishBtnDisplayed() {
		return isElementDisplayed(publishBtn);
	}

	public boolean isCancelBtnInPublishTabDisplayed() {
		return isElementDisplayed(cancelBtnInPublishTab);
	}

	public void clickOnPoliciesButton() {
		clickOnElement(policiesButtonInAdmin);
	}

	public void clickOnMispPolicyButton() {
		clickOnElement(viewButtonInMispPolicy);
	}

	public void clickOnBackButtonInMispPolicySuccessPage() {
		clickOnElement(backButtonInMispPolicySuccessPage);
	}

	public void clickOnListOfPolicyButton() {
		clickOnElement(listOfPartnerButton);
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

	public void clickOnListOfAuthPoliciesTitle() {
		clickOnElement(listOfAuthenticationPolicesTitle);
	}

	public void clickOnListOfDataSharePoliciesTitle() {
		clickOnElement(listOfDataSharePolicesTitle);
	}

	public void clickOnMispPolicyTitle() {
		clickOnElement(mispPolicyTabInCreatePolicyGroup);
	}

	public void clickOnCreateMispPolicyTitle() {
		clickOnElement(createMispPolicyHeader);
	}

	public void clickOnUploadBtnInMispPolicy() {
		clickOnElement(uploadButtonInMispPolicy);
	}

	public void clickOnPolicyGroupDropDownField() {
		clickOnElement(policyGroupDropdown);
	}

	public void clickOnPolicyGroupDropDownInMispPolicies() {
		clickOnElement(policyGroupDropDownInMispPolicies);
	}

	public void clickOnSaveAsDraftButtonInMispPolicy() {
		clickOnElement(saveAsDraftButtonInMispPolicies);
	}

	public void clickOnActionButtonMispPolicy() {
		clickOnElement(actionTabInMispPartner);
	}

	public void clickOnBackButtonInViewMispPolicyMispPolicy() {
		clickOnElement(backButtonInViewMispPolicy);
	}

	public void clickOnCancelBtnInPublishTab() {
		clickOnElement(cancelBtnInPublishTab);
	}

	public void uploadPolicyData() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "mispPolicy.json"));
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

	public void clickOnActionInPublishBtn() {
		clickOnElement(publishBtnInActionTab);
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

	public void enterPolicyName(String address) {
		enter(policyNameInMispPolicy, address);
	}

	public void enterPolicyDescription(String address) {
		enter(policyDescriptionInMispPolicy, address);
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
