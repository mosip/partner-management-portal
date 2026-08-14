package io.mosip.testrig.pmpuiv2.pages;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class MispPolicyPage extends BasePage {

	@FindBy(id = "create_auth_policy_btn")
	private WebElement createMispPolicyButton;

	@FindBy(id = "policy_group_selector_dropdown_button")
	private WebElement policyGroupDropdown;

	@FindBy(id = "policy_name_box")
	private WebElement policyNameBox;

	@FindBy(id = "policy_description_box")
	private WebElement policyDescriptionBox;

	@FindBy(id = "fileInput")
	private WebElement uploadFile;

	@FindBy(id = "create_policy_form_clear_btn")
	private WebElement createPolicyClearButton;

	@FindBy(id = "create_policy_form_cancel_btn")
	private WebElement createPolicyCancelButton;

	@FindBy(id = "create_policy_form_submit_btn")
	private WebElement createPolicySubmitButton;

	@FindBy(id = "policy_group_selector_label_text")
	private WebElement policyGroupLabel;

	@FindBy(id = "policy_name_box_label")
	private WebElement policyNameLabel;

	@FindBy(id = "create_policy_policy_description_label")
	private WebElement policyDescriptionLabel;

	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;

	@FindBy(id = "sub_title_btn")
	private WebElement listOfMispPoliciesButton;

	@FindBy(id = "create_policy_mandatory_field_msg")
	private WebElement fieldsMarkedWithMandatoryLabel;

	@FindBy(id = "policy_group_selector_dropdown_button_text")
	private WebElement selectPolicyGroupPlaceholder;

	@FindBy(id = "policy_data_box")
	private WebElement policyDataBox;

	@FindBy(id = "upload_policy_data_file_header")
	private WebElement policyDataUploadHeader;

	@FindBy(id = "upload_policy_data_file_description")
	private WebElement policyDataUploadDescription;

	@FindBy(id = "policy_group_selector_search_input")
	private WebElement policyGroupDropdownSearchInput;

	@FindBy(id = "create_policy_error_msg")
	private WebElement provideValidJsonDataErrorMessage;

	@FindBy(id = "error_close_btn")
	private WebElement errorCloseButton;

	@FindBy(id = "create_policy_confirmation_header")
	private WebElement titleOfPolicyCreatedSuccessMessage;

	@FindBy(id = "create_policy_confirmation_description")
	private WebElement subTitleOfPolicyCreatedSuccessMessage;

	@FindBy(id = "confirmation_custom_btn")
	private WebElement successPublishButton;

	@FindBy(id = "confirmation_go_back_btn")
	private WebElement goBackButton;

	@FindBy(id = "policiesList.policyName_header")
	private WebElement policiesListPolicyNameHeader;

	@FindBy(id = "policiesList.creationDate_header")
	private WebElement policiesListCreationDateHeader;

	@FindBy(id = "publish_policy_popup_title")
	private WebElement publishPolicyPopupTitle;

	@FindBy(id = "publish_policy_popup_policy_name")
	private WebElement publishPolicyPopupPolicyName;

	@FindBy(id = "publish_policy_cancel")
	private WebElement publishPolicyCancelButton;

	@FindBy(id = "publish_policy_button")
	private WebElement publishPolicyButton;

	@FindBy(id = "publish_policy_popup_success_msg_with_param")
	private WebElement publishPolicyPopupSuccessMessage;

	@FindBy(id = "publish_policy_close_button")
	private WebElement publishPolicyCloseButton;

	@FindBy(id = "policy_edit_btn")
	private WebElement policyEditButton;

	@FindBy(id = "policy_replicate_btn")
	private WebElement cloneButton;

	@FindBy(id = "policy_deactivate_btn")
	private WebElement policyDeactivateButton;

	@FindBy(id = "page_title")
	private WebElement pageTitle;

	@FindBy(id = "policy_publish_btn")
	private WebElement policyPublishButton;

	@FindBy(id = "clone_policy_popup_title")
	private WebElement clonePolicyTitle;

	@FindBy(id = "clone_policy_popup_success_msg")
	private WebElement clonePolicyPopupSuccessMessage;

	@FindBy(id = "clone_policy_close_button")
	private WebElement clonePolicyCloseButton;

	@FindBy(id = "clone_policy_button")
	private WebElement clonePolicyButton;

	@FindBy(id = "policy_group_selector_dropdown_button")
	private WebElement clonePolicyGroupDropdown;

	@FindBy(id = "policy_group_selector_search_input")
	private WebElement clonePolicyGroupDropdownSearchInput;

	@FindBy(id = "deactivate_policy_popup_header")
	private WebElement deactivatePolicyPopupHeader;

	@FindBy(id = "deactivate_policy_group__confirm_btn")
	private WebElement deactivateConfirmButton;

	@FindBy(id = "policy_details_view_btn")
	private WebElement viewButton;

	@FindBy(id = "edit_policy_form_submit_btn")
	private WebElement editPolicyFormSubmitButton;

	public MispPolicyPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnCreateMispPolicyButton() {
		clickOnElement(createMispPolicyButton);
	}

	public void clickOnCreatePolicySubmitButton() {
		clickOnElement(createPolicySubmitButton);
	}

	public void clickOnEditPolicyFormSubmitButton() {
		clickOnElement(editPolicyFormSubmitButton);
	}

	public void clickOnErrorCloseButton() {
		clickOnElement(errorCloseButton);
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

	public void enterPolicyName(String val) {
		enter(policyNameBox, val);
	}

	public void enterpolicyDescription(String val) {
		enter(policyDescriptionBox, val);
	}

	public void uploadPolicyData() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "MispPolicy.json"));
	}

	public void uploadInvalidPolicyData() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "invalidData.json"));
	}

	public boolean isPolicyGroupLabelDisplayed() {
		return isElementDisplayed(policyGroupLabel);
	}

	public boolean isPolicyNameLabelDisplayed() {
		return isElementDisplayed(policyNameLabel);
	}

	public boolean isPolicyDescriptionLabelDisplayed() {
		return isElementDisplayed(policyDescriptionLabel);
	}

	public boolean isHomeButtonDisplayed() {
		return isElementDisplayed(homeButton);
	}

	public boolean isSubmitButtonDisabled() {
		return isElementDisabled(createPolicySubmitButton);
	}

	public boolean isListOfMispPoliciesButtonDisplayed() {
		return isElementDisplayed(listOfMispPoliciesButton);
	}

	public boolean isFieldsMarkedWithMandatoryLabelDisplayed() {
		return isElementDisplayed(fieldsMarkedWithMandatoryLabel);
	}

	public boolean isSelectPolicyGroupPlaceholderDisplayed() {
		return isElementDisplayed(selectPolicyGroupPlaceholder);
	}

	public String getPolicyNamePlaceholder() {
		return getTextFromAttribute(policyNameBox, GlobalConstants.PLACEHOLDER);
	}

	public String getPolicyDescriptionPlaceholder() {
		return getTextFromAttribute(policyDescriptionBox, GlobalConstants.PLACEHOLDER);
	}

	public String getPolicyDataPlaceholder() {
		return getTextFromAttribute(policyDataBox, GlobalConstants.PLACEHOLDER);
	}

	public boolean isPolicyDataUploadHeaderDisplayed() {
		return isElementDisplayed(policyDataUploadHeader);
	}

	public boolean isPolicyDataUploadDescriptionDisplayed() {
		return isElementDisplayed(policyDataUploadDescription);
	}

	public boolean isProvideValidJsonDataErrorMessageDisplayed() {
		return isElementDisplayed(provideValidJsonDataErrorMessage);
	}

	public void clickOnCreatePolicyClearButton() {
		clickOnElement(createPolicyClearButton);
	}

	public boolean isTitleOfSuccessMessageDisplayed() {
		return isElementDisplayed(titleOfPolicyCreatedSuccessMessage);
	}

	public boolean isSubTitleOfSuccessMessageDisplayed() {
		return isElementDisplayed(subTitleOfPolicyCreatedSuccessMessage);
	}

	public boolean isSuccessGoBackButtonAvailable() {
		return isElementDisplayed(goBackButton);
	}

	public boolean isSuccessPublishButtonAvailable() {
		return isElementDisplayed(successPublishButton);
	}

	public void clickOnGoBackButton() {
		clickOnElement(goBackButton);
	}

	public void clickOnSuccessPublishButton() {
		clickOnElement(successPublishButton);
	}

	public void clickOnCreatePolicyCancelButton() {
		clickOnElement(createPolicyCancelButton);
	}

	public boolean isPoliciesListPolicyNameHeaderDisplayed() {
		return isElementDisplayed(policiesListPolicyNameHeader);
	}

	public boolean isPoliciesListCreationDateHeaderDisplayed() {
		return isElementDisplayed(policiesListCreationDateHeader);
	}

	public boolean isPublishPolicyPopupTitleDisplayed() {
		return isElementDisplayed(publishPolicyPopupTitle);
	}

	public boolean isPublishPolicyPopupPolicyNameDisplayed() {
		return isElementDisplayed(publishPolicyPopupPolicyName);
	}

	public void clickOnPublishPolicyCancelButton() {
		clickOnElement(publishPolicyCancelButton);
	}

	public void clickOnPublishPolicyButton() {
		clickOnElement(publishPolicyButton);
	}

	public boolean isPublishPolicyPopupSuccessMessageDisplayed() {
		return isElementDisplayed(publishPolicyPopupSuccessMessage);
	}

	public void clickOnPublishPolicyCloseButton() {
		clickOnElement(publishPolicyCloseButton);
	}

	public void clickOnActionOfMispPolicy(String policyName) {
		By threeDotsButton = By.xpath(
				"//td[normalize-space()='" + policyName + "']/parent::tr//button[contains(@id,'policies_list_view')]");
		click(threeDotsButton);
	}

	public boolean isPolicyEditButtonDisplayed() {
		return isElementDisplayed(policyEditButton);
	}

	public void clickOnPolicyEditButton() {
		clickOnElement(policyEditButton);
	}

	public boolean isPolicyCloneButtonDisplayed() {
		return isElementDisplayed(cloneButton);
	}

	public void clickOnPolicyCloneButton() {
		clickOnElement(cloneButton);
	}

	public boolean isPolicyDeactivateButtonDisplayed() {
		return isElementDisplayed(policyDeactivateButton);
	}

	public void clickOnPolicyDeactivateButton() {
		clickOnElement(policyDeactivateButton);
	}

	public String getPageTitle() {
		return getTextFromLocator(pageTitle);
	}

	public boolean isPolicyPublishButtonDisplayed() {
		return isElementDisplayed(policyPublishButton);
	}

	public void clickOnPolicyPublishButton() {
		clickOnElement(policyPublishButton);
	}

	public boolean isClonePolicyTitleDisplayed() {
		return isElementDisplayed(clonePolicyTitle);
	}

	public void selectValidPolicyGroupForClone(String value) {
		clickOnElement(clonePolicyGroupDropdown);
		clickOnElement(clonePolicyGroupDropdownSearchInput);
		enter(clonePolicyGroupDropdownSearchInput, value);
		By policyGroupOption = By.xpath("//span[normalize-space()='" + value + "']");
		click(policyGroupOption);
	}

	public boolean isClonePolicyPopupSuccessMessageDisplayed() {
		return isElementDisplayed(clonePolicyPopupSuccessMessage);
	}

	public void clickOnClonePolicyCloseButton() {
		clickOnElement(clonePolicyCloseButton);
	}

	public void clickOnClonePolicyButton() {
		clickOnElement(clonePolicyButton);
	}

	public boolean isDeactivatePolicyPopupHeaderDisplayed() {
		return isElementDisplayed(deactivatePolicyPopupHeader);
	}

	public void clickOnDeactivateConfirmButton() {
		clickOnElement(deactivateConfirmButton);
	}

	public boolean isViewButtonDisplayed() {
		return isElementDisplayed(viewButton);
	}

	public void clickOnViewButton() {
		clickOnElement(viewButton);
	}

	public void clickOnListOfMispPoliciesButton() {
		clickOnElement(listOfMispPoliciesButton);
	}

}
