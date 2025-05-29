package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmprevampui.fw.util.PmpTestUtil;
import io.mosip.testrig.pmprevampui.utility.TestRunner;

public class DatasharePolicyPage extends BasePage {
	
	@FindBy(id = "create_auth_policy_btn")
	private WebElement createDatasharePolicyButton;
	
	@FindBy(id = "sub_title_home_btn")
	private WebElement subTitleHomeButton;

	@FindBy(id = "sub_title_btn")
	private WebElement subTitleButton;
	
	@FindBy(xpath = "//h1[text()='Create Datashare Policy']")
	private WebElement datasharePolicyCreationPageTitle;
	
	@FindBy(xpath = "//p[contains(text(), 'All fields marked with')]")
	private WebElement datashareFormSubTitle;
	
	@FindBy(id = "policy_group_dropdown")
	private WebElement policyGroupDropdown;
	
	@FindBy(id = "policy_group_dropdown_search_input")
	private WebElement policyGroupDropdownSearchInput;
	
	@FindBy(id = "policy_group_dropdown_option1")
	private WebElement policyGroupDropdownOption1;
	
	@FindBy(id = "policy_name_box")
	private WebElement policyNameBox;
	
	@FindBy(id = "policy_description_box")
	private WebElement policyDescriptionBox;
	
	@FindBy(id = "fileInput")
	private WebElement uploadFile;
	
	@FindBy(id = "create_policy_form_submit_btn")
	private WebElement policySaveAsDraftButton;
	
	@FindBy(xpath = "//span[text()='Select policy group']")
	private WebElement policyGroupPlaceHolder;
	
	@FindBy(xpath = "//span[text()='automationui policy group']")
	private WebElement policyGroupName;
	
	@FindBy(xpath = "//p[text()='Policy Name']")
	private WebElement policyNameTextLabel;
	
	@FindBy(xpath = "//input[@placeholder='Enter name for Datashare Policy']")
	private WebElement policyNamePlaceHolder;
	
	@FindBy(xpath = "//label[text()='Policy Description']")
	private WebElement policyDescriptionTextLabel;
	
	@FindBy(xpath = "//textarea[@placeholder='Enter description about Datashare Policy']")
	private WebElement datasharePolicyDescription;
	
	@FindBy(xpath = "//h6[text()='Upload Policy Data']")
	private WebElement uploadPolicyDataLabel;
	
	@FindBy(xpath = "//p[text()='Only .json file format is allowed for upload']")
	private WebElement uploadPolicyDataHelpText;
	
	@FindBy(xpath = "//textarea[@placeholder='Upload the json file successfully to display its content here']")
	private WebElement fileUploadPlaceHolder;
	
	@FindBy(id = "confirmation_go_back_btn")
	private WebElement goBackButton;
	
	@FindBy(xpath = "//p[text()='desc automationui policy group']")
	private WebElement policyGroupDescription;
	
	@FindBy(xpath = "//p[text()='Policy data has been uploaded successfully']")
	private WebElement policyDataUploadedSuccessMessage;
	
	@FindBy(xpath = "//h1[text()='Policy saved as Draft']")
	private WebElement titleOfPolicyCreatedSuccessMessage;
	
	@FindBy(xpath = "//p[contains(text(), 'This Datashare Policy is currently in draft mode')]")
	private WebElement subTitleOfPolicyCreatedSuccessMessage;
	
	@FindBy(id = "confirmation_home_btn")
	private WebElement successHomeButton;
	
	@FindBy(xpath = "//p[text()='Policy data should not exceed more than 5120 characters.']")
	private WebElement policyDataExceedChractersMessage;
	
	@FindBy(xpath = "//p[text()='Invalid input parameter - info in policy data']")
	private WebElement invalidInfoInPoliyDataMessage;
	
	@FindBy(id = "error_close_btn")
	private WebElement errorCloseButton;
	
	@FindBy(xpath = "//p[text()='The given policy name already exists. Please try with a different policy name']")
	private WebElement policyNameExistErrorMessage;
	
	@FindBy(xpath = "//p[text()='Please provide valid JSON data']")
	private WebElement provideValidJsonDataErrorMessage;
	
	@FindBy(id = "create_policy_form_clear_btn")
	private WebElement clearForm;
	
	@FindBy(id = "create_policy_form_cancel_btn")
	private WebElement cancelForm;
	
	@FindBy(xpath = "//p[text()='Your changes will be lost, are you sure you want to proceed?']")
	private WebElement dataLostWarningMessage;
	
	@FindBy(id = "block_messsage_proceed")
	private WebElement lostWarningProceedButton;
	
	@FindBy(id = "block_message_cancel")
	private WebElement lostWarningCancelButton;
	
	@FindBy(xpath = "//p[contains(text(), 'List of Datashare Policies')]")
	private WebElement subTitleOfTabularView;
	
	@FindBy(xpath = "//div[text()='Policy ID']")
	private WebElement policyIdHeader;
	
	@FindBy(xpath = "//div[text()='Policy Name']")
	private WebElement policyNameHeader;
	
	@FindBy(xpath = "//div[text()='Policy Description']")
	private WebElement policyDescriptionHeader;
	
	@FindBy(xpath = "//div[text()='Policy Group']")
	private WebElement policyGroupHeader;
	
	@FindBy(xpath = "//div[text()='Creation Date']")
	private WebElement creationDateHeader;
	
	@FindBy(xpath = "//div[text()='Status']")
	private WebElement statusHeader;
	
	@FindBy(xpath = "//div[text()='Action']")
	private WebElement actionHeader;
	
	@FindBy(id = "policyId_asc_icon")
	private WebElement policyIdAscIcon;
	
	@FindBy(id = "policyId_desc_icon")
	private WebElement policyIdDescIcon;
	
	@FindBy(id = "policyName_asc_icon")
	private WebElement policyNameAscIcon;
	
	@FindBy(id = "policyName_desc_icon")
	private WebElement policyNameDescIcon;
	
	@FindBy(id = "policyDescription_asc_icon")
	private WebElement policyDescriptionAscIcon;
	
	@FindBy(id = "policyDescription_desc_icon")
	private WebElement policyDescriptionDescIcon;
	
	@FindBy(id = "policyGroupName_asc_icon")
	private WebElement policyGroupNameAscIcon;
	
	@FindBy(id = "policyGroupName_desc_icon")
	private WebElement policyGroupNameDescIcon;
	
	@FindBy(id = "createdDateTime_asc_icon")
	private WebElement creationDateAscIcon;
	
	@FindBy(id = "createdDateTime_desc_icon")
	private WebElement creationDateDescIcon;
	
	@FindBy(id = "status_asc_icon")
	private WebElement statusAscIcon;
	
	@FindBy(id = "status_desc_icon")
	private WebElement statusDescIcon;
	
	@FindBy(xpath = "//p[normalize-space()='2024 © MOSIP - All rights reserved.']")
	private WebElement mosipRightsText;
	
	@FindBy(id = "footer_documentation_link")
	private WebElement footerDocumentationLink;
	
	@FindBy(id = "footer_contact_us_link")
	private WebElement footerContactUsLink;
	
	@FindBy(id = "pagination_card")
	private WebElement pagination;
	
	@FindBy(id = "filter_btn")
	private WebElement filterButton;
	
	@FindBy(id = "policies_list_item1")
	private WebElement datasharePolicy1;
	
	@FindBy(id = "policies_list_item2")
	private WebElement datasharePolicy2;
	
	@FindBy(id = "policies_list_item4")
	private WebElement datasharePolicy4;
	
	@FindBy(xpath = "//h1[text()='View Datashare Policy']")
	private WebElement viewPolicyPageTitle;
	
	@FindBy(id = "auth_Policy_view_back_btn")
	private WebElement viewBackButton;
	
	@FindBy(id = "policies_list_view1")
	private WebElement actionButton;
	
	@FindBy(id = "policy_publish_btn")
	private WebElement publishButton;
	
	@FindBy(id = "policy_details_view_btn")
	private WebElement viewButton;
	
	@FindBy(id = "policy_replicate_btn")
	private WebElement cloneButton;
	
	@FindBy(id = "policy_deactivate_btn")
	private WebElement deactivateButton;
	
	@FindBy(id = "auth_Policy_view_back_btn")
	private WebElement datashareViewBackButton;
	
	@FindBy(id = "policies_list_view1")
	private WebElement datashareActionButton;
	
	@FindBy(id = "policy_publish_btn")
	private WebElement datasharePublishButton;
	
	@FindBy(id = "policy_details_view_btn")
	private WebElement datashareViewButton;
	
	@FindBy(id = "policy_replicate_btn")
	private WebElement datashareCloneButton;
	
	@FindBy(id = "policy_deactivate_btn")
	private WebElement datashareDeactivateButton;
	
	@FindBy(xpath = "//h3[text()='Publish Policy']")
	private WebElement publishConfirmationPopup;
	
	@FindBy(xpath = "//p[contains(text(), 'By clicking ‘Publish,’ the policy - ')]")
	private WebElement publishPolicyInfoMessage;
	
	@FindBy(id = "publish_policy_cancel")
	private WebElement publishPolicyCancelButton;
	
	@FindBy(id = "publish_policy_button")
	private WebElement publishPolicyButton;
	
	@FindBy(xpath = "//p[contains(text(), 'Do you want to deactivate Policy')]")
	private WebElement deactivatePolicyPopup;
	
	@FindBy(xpath = "//p[text()='Upon clicking ‘Confirm’, the selected Datashare Policy will be deactivated.']")
	private WebElement deactivatePolicyInfoMessage;
	
	@FindBy(id = "deactivate_policy_group_cancel_btn")
	private WebElement deactivateCancelButton;
	
	@FindBy(id = "deactivate_policy_group__confirm_btn")
	private WebElement deactivateConfirmButton;
	
	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;
	
	@FindBy(id = "policy_id_filter")
	private WebElement policyIdFilter;
	
	@FindBy(id = "policy_name_filter")
	private WebElement policyNameFilter;
	
	@FindBy(id = "policy_description_filter")
	private WebElement policyDescriptionFilter;
	
	@FindBy(id = "policy_group_filter")
	private WebElement policyGroupFilter;
	
	@FindBy(id = "status_filter")
	private WebElement policyStatusFilter;
	
	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilterButton;
	
	@FindBy(id = "status_filter_option1")
	private WebElement activateStatusButton;
	
	@FindBy(id = "status_filter_option2")
	private WebElement deactivateStatusButton;
	
	@FindBy(id = "status_filter_option3")
	private WebElement draftStatusButton;
	
	@FindBy(xpath = "//div[text()='Deactivated']")
	private WebElement deactivateStatus;
	
	@FindBy(xpath = "//div[text()='Activated']")
	private WebElement activateStatus;
	
	@FindBy(xpath = "//p[text()='No Results Found']")
	private WebElement noResultFound;

	@FindBy(id = "sub_title_btn")
	private WebElement subtitleButton;
	
	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;
	
	@FindBy(xpath = "//p[text()='Policy ID']")
	private WebElement policyIdLabel;
	
	@FindBy(xpath = "//span[text()='ab11CD22ef']")
	private WebElement policyIdContext;
	
	@FindBy(xpath = "//p[text()='Policy Name']")
	private WebElement policyNameLabel;
	
	@FindBy(xpath = "//p[text()='Automation123']")
	private WebElement policyNameContext;
	
	@FindBy(xpath = "//p[text()='Policy Group']")
	private WebElement policyGroupLabel;
	
	@FindBy(xpath = "//p[text()='automationui policy group']")
	private WebElement policyGroupContext;
	
	@FindBy(xpath = "//p[text()='Policy Description']")
	private WebElement policyDescriptionLabel;
	
	@FindBy(xpath = "//p[text()='Automation123']")
	private WebElement policyDescriptionContext;
	
	@FindBy(xpath = "//p[text()='Policy Group Description']")
	private WebElement policyGroupDescriptionLabel;
	
	@FindBy(xpath = "//p[text()='desc automationui policy group']")
	private WebElement policyGroupDescriptionContext;
	
	@FindBy(xpath = "//p[text()='Policy Data']")
	private WebElement policyDataLabel;
	
	@FindBy(xpath = "//p[text()='Preview']")
	private WebElement policyDataPreviewButton;
	
	@FindBy(xpath = "//h2[text()='Policy Data']")
	private WebElement policyDataPopup;
	
	@FindBy(xpath = "//button[text()='Download']")
	private WebElement downloadButton;
	
	@FindBy(xpath = "//p[text()='X']")
	private WebElement closeButton;
	
	@FindBy(id = "policy_data_box")
	private WebElement policyDataBox;
	
	@FindBy(xpath = "//textarea[@id='policy_data_box' and contains(text(), '\"shareableAttributes\"')]")
	private WebElement policyDataBoxWithAttributes;
	
	@FindBy(id = "publish_policy_close_button")
	private WebElement publishPolicyCloseButton;
	
	@FindBy(xpath = "//p[text()='No Data Available.']")
	private WebElement noDataAvailable;
	
	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;
	
	@FindBy(xpath = "//h3[text()='Clone Policy']")
	private WebElement clonePolicyTitle;
	
	@FindBy(xpath = "//p[contains(text(), 'Select the policy group to nest the cloned policy')]")
	private WebElement clonePolicyInfoMessage;
	
	@FindBy(id = "clone_policy_group_dropdown")
	private WebElement clonePolicyGroupDropdown;
	
	@FindBy(id = "clone_policy_group_dropdown_search_input")
	private WebElement clonePolicyGroupDropdownSearchInput;
	
	@FindBy(xpath = "//span[normalize-space(text())='A']")
	private WebElement clonePolicyGroupName;
	
	@FindBy(xpath = "//p[text()='A']")
	private WebElement clonePolicyGroupDescription;
	
	@FindBy(id = "clone_policy_group_dropdown_option1")
	private WebElement clonePolicyGroupDropdownOption1;
	
	@FindBy(id = "clone_policy_cancel")
	private WebElement clonePolicyCancelButton;
	
	@FindBy(id = "clone_policy_button")
	private WebElement clonePolicyButton;
	
	@FindBy(xpath = "//p[contains(text(), 'has been cloned and associated to')]")
	private WebElement clonedSuccessMessage;
	
	@FindBy(id = "clone_policy_close_button")
	private WebElement clonePolicyCloseButton;
	
	@FindBy(xpath = "//td[contains(text(), 'A')]")
	private WebElement updatedPolicyGroup;
	
	@FindBy(xpath = "//div[text()='Draft' and contains(@class, 'rounded-md')]")
	private WebElement clonedPolicyStatusDraft;
	
	@FindBy(xpath = "//p[text()='This policy already exists within the selected policy group. Please choose a different policy group to proceed further.']")
	private WebElement alreadyExistErrorMessage;
	
	@FindBy(xpath = "//img[contains(@src, 'close_icon') and @alt='closeIcon']")
	private WebElement closeIcon;
	
	@FindBy(xpath = "//div[text()='Draft']")
	private WebElement viewPolicyStatusDraft;
	
	@FindBy(xpath = "//p[text()='Error:  Active Partner-Policy Linking Detected!']")
	private WebElement partnerPolicyLinkActivated;
	
	@FindBy(id = "alert_error_popup_okay_btn")
	private WebElement alertErrorOkButton;
	
	@FindBy(xpath = "//p[text()='Error: Partner - Policy Request Detected!']")
	private WebElement partnerPolicyLinkPending;
	
	@FindBy(id = "policy_edit_btn")
	private WebElement policyEditButton;
	
	@FindBy(xpath = "//h1[text()='Edit Datashare Policy']")
	private WebElement editPolicyPageTitle;
	
	@FindBy(xpath = "//p[contains(text(), 'All fields marked with')]")
	private WebElement policyFormSubTitle;
	
	@FindBy(xpath = "//span[text()='automationui policy group']")
	private WebElement disabledPolicyGroupDropdown;
	
	@FindBy(xpath = "//span[text()='automationui policy group']")
	private WebElement editPolicyGroupDropdownValue;
	
	@FindBy(xpath = "//input[@id='policy_name_box' and @value='editdatapolicy']")
	private WebElement editPolicyNameValue;
	
	@FindBy(xpath = "//textarea[@id='policy_description_box' and text()='editdatapolicy']")
	private WebElement editPolicyDescriptionValue;
	
	@FindBy(xpath = "//textarea[@id='policy_data_box' and contains(text(), '\"shareableAttributes\"')]")
	private WebElement editPolicyDataContext;
	
	@FindBy(xpath = "//h6[text()='Re-Upload Policy Data']")
	private WebElement reuploadPolicyDataLabel;
	
	@FindBy(id = "fileInput")
	private WebElement reUploadFile;
	
	@FindBy(id = "edit_policy_undo_changes_btn")
	private WebElement undoChangesButton;
	
	@FindBy(id = "edit_policy_form_cancel_btn")
	private WebElement editPolicyFormCancelButton;
	
	@FindBy(id = "edit_policy_form_submit_btn")
	private WebElement editPolicyFormSubmitButton;
	
	@FindBy(xpath = "//h1[text()='Data-Share Policy updated and submitted successfully!']")
	private WebElement editPolicySuccessTitle;
	
	@FindBy(xpath = "//p[contains(text(), 'The Data-Share Policy for Policy Group')]")
	private WebElement editPolicySuccessSubTitle;
	
	@FindBy(id = "confirmation_go_back_btn")
	private WebElement editSuccessGoBackButton;
	
	@FindBy(id = "confirmation_home_btn")
	private WebElement editSuccessHomeButton;
	
	@FindBy(xpath = "//p[text()='Your changes will be lost, are you sure you want to proceed?']")
	private WebElement confirmationMessage;
	
	@FindBy(id = "block_messsage_proceed")
	private WebElement changesLostProceedButton;
	
	@FindBy(id = "block_message_cancel")
	private WebElement changesLostCancelButton;
	
	@FindBy(xpath = "//span[text()='Special characters are not allowed.']")
	private WebElement specialCharactersAreNotAllowedErrorMessage;
	
	@FindBy(xpath = "//span[text()='Re-Upload']")
	private WebElement reUploadButton;
	
	public DatasharePolicyPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean isDataSharePolicyCreateButtonAvailable() {
		return isElementEnabled(createDatasharePolicyButton);
	}

	public void clickOnDatasharePolicyCreateButton() {
		clickOnElement(createDatasharePolicyButton);
	}
	
	public boolean isSubTitleHomeDisplayed() {
		return isElementDisplayed(subTitleHomeButton);
	}
	
	public boolean isSubTitleDisplayed() {
		return isElementDisplayed(subTitleButton);
	}
	
	public boolean isCreateDatashareTitleDisplayed() {
		return isElementDisplayed(datasharePolicyCreationPageTitle);
	}
	
	public boolean isDatashareFormSubTitleDisplayed() {
		return isElementDisplayed(datashareFormSubTitle);
	}
	
	public void clickOnPolicyGroupDropdown() {
		clickOnElement(policyGroupDropdown);
	}
	
	public boolean isPolicyGroupDropdownSearchInputDisplayed() {
		return isElementDisplayed(policyGroupDropdownSearchInput);
	}
	
	public boolean isPolicyGroupPlaceHolderDisplayed() {
		return isElementDisplayed(policyGroupPlaceHolder);
	}
	
	public void searchPolicyGroup(String value) {
		enter(policyGroupDropdownSearchInput,value);
	}
	
	public boolean isPolicyGroupNameDisplayed() {
		return isElementDisplayed(policyGroupName);
	}
	
	public boolean isPolicyGroupDescriptionDisplayed() {
		return isElementDisplayed(policyGroupDescription);
	}
	
	public void selectPolicyGroup(String value) {
		clickOnElement(policyGroupDropdown);
		enter(policyGroupDropdownSearchInput,value);
		clickOnElement(policyGroupDropdownOption1);
	}
	
	public boolean isPolicyNameTextLabelDisplayed() {
		return isElementDisplayed(policyNameTextLabel);
	}
	
	public boolean isPolicyNamePlaceHolderDisplayed() {
		return isElementDisplayed(policyNamePlaceHolder);
	}
	
	public boolean isPolicyDescriptionTextLabelDisplayed() {
		return isElementDisplayed(policyDescriptionTextLabel);
	}
	
	public boolean isPolicyDescriptionPlaceHolderDisplayed() {
		return isElementDisplayed(datasharePolicyDescription);
	}
	
	public boolean isUploadPolicyDataLabelDisplayed() {
		return isElementDisplayed(uploadPolicyDataLabel);
	}
	
	public boolean isUploadPolicyDataHelpTextDisplayed() {
		return isElementDisplayed(uploadPolicyDataHelpText);
	}
	
	public boolean isFileUploadPlaceHolderDisplayed() {
		return isElementDisplayed(fileUploadPlaceHolder);
	}
	
	public void enterPolicyName(String val) {
		enter(policyNameBox,val);
	}
	
	public void enterpolicyDescription(String val) {
		enter(policyDescriptionBox,val);
	}
	
	public void uploadPolicyData() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_revamp_cert", "DatasharePolicy.json"));
	}
	
	public void uploadExceedPolicyData() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_revamp_cert", "exceedData.json"));
	}
	
	public void uploadInvalidPolicyData() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_revamp_cert", "invalidData.json"));
	}
	
	public void uploadBlankData() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_revamp_cert", "BlankData.json"));
	}
	
	public void uploadAlphabetData() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_revamp_cert", "Alphabet.json"));
	}
	
	public void uploadSpecialChData() {
		uploadImage(uploadFile, PmpTestUtil.getResourceFilePath("pmp_revamp_cert", "SpecialCharacter.json"));
	}	
	
	public boolean isSaveAsDraftButtonEnabled() {
		return isElementEnabled(policySaveAsDraftButton);
	}
	
	public void clickOnSaveAsDraftButton() {
		clickOnElement(policySaveAsDraftButton);
	}
	
	public void clickOnGoBackButton() {
		clickOnElement(goBackButton);
	}
	
	public boolean isPolicyDataUploadedSuccessMessageDisplayed() {
		return isElementDisplayed(policyDataUploadedSuccessMessage);
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
	
	public boolean isSuccessHomeButtonAvailable() {
		return isElementDisplayed(successHomeButton);
	}
	
	public void clickOnSuccessHomeButton() {
		clickOnElement(successHomeButton);
	}
	
	public boolean isPolicyDataExceedChractersMessageDisplayed() {
		return isElementDisplayed(policyDataExceedChractersMessage);
	}
	
	public boolean isInvalidInfoInPoliyDataMessageDisplayed() {
		return isElementDisplayed(invalidInfoInPoliyDataMessage);
	}
	
	public void clickOnErrorCloseButton() {
		clickOnElement(errorCloseButton);
	}
	
	public boolean isPolicyNameExistErrorMessageDisplayed() {
		return isElementDisplayed(policyNameExistErrorMessage);
	}
	
	public boolean isProvideValidJsonDataErrorMessageDisplayed() {
		return isElementDisplayed(provideValidJsonDataErrorMessage);
	}
	
	public boolean isClearFormDisplayed() {
		return isElementDisplayed(clearForm);
	}
	
	public void clickOnClearForm() {
		clickOnElement(clearForm);
	}
	
	public void clickOnDatashareViewBackButton() {
		clickOnElement(datashareViewBackButton);
	}
	
	public void clickOnDatashareActionButton() {
		clickOnElement(datashareActionButton);
	}
	
	public void clickOnDatasharePublishButton() {
		clickOnElement(datasharePublishButton);
	}
	
	public boolean isDatasharePublishButtonDisplayed() {
		return isElementDisplayed(datasharePublishButton);
	}
	
	public boolean isDatashareViewButtonDisplayed() {
		return isElementDisplayed(datashareViewButton);
	}
	
	public boolean isDatashareCloneButtonDisplayed() {
		return isElementDisplayed(datashareCloneButton);
	}
	
	public boolean isDatashareDeactivateButtonDisplayed() {
		return isElementDisplayed(datashareDeactivateButton);
	}
	
	public void clickOnDatashareDeactivateButton() {
		clickOnElement(datashareDeactivateButton);
	}

	public void clickOnFilterButton() {
		clickOnElement(filterButton);
	}
	
	public boolean isFilterResetButtonEnabled() {
		return isElementEnabled(filterResetButton);
	}
	
	public void clickOnFilterResetButton() {
		clickOnElement(filterResetButton);
	}
	
	public void clickOnPolicyGroupFilter(String value) {
		enter(policyGroupFilter,value);
	}
	
	public void enterPolicyNameInFilter(String value) {
		enter(policyNameFilter,value);
	}
	
	public void selectDeactivateStatusFilter() {
		clickOnElement(policyStatusFilter);
		clickOnElement(deactivateStatusButton);
	}
	
	public void clickOnApplyFilterButton() {
		clickOnElement(applyFilterButton);
	}
	
	public boolean isPolicyStatusActivateDisplayed() {
		return isElementDisplayed(activateStatus);
	}
	
	public boolean isPolicyStatusDeactivateDisplayed() {
		return isElementDisplayed(deactivateStatus);
	}
	
	public void clickOnDeactivatedPolicy() {
		clickOnElement(datasharePolicy1);
	}
	
	public boolean isApplyFilterButtonEnabled() {
		return isElementEnabled(applyFilterButton);
	}
	
	public boolean isNoResultsFoundMessageDisplayed() {
		return isElementDisplayed(noResultFound);
	}
	
	public void clickOnPolicyIdAscIcon() {
		clickOnElement(policyIdAscIcon);
	}
	
	public void clickOnPolicyIdDescIcon() {
		clickOnElement(policyIdDescIcon);
	}
	
	public void clickOnPolicyNameAscIcon() {
		clickOnElement(policyNameAscIcon);
	}
	
	public void clickOnPolicyNameDescIcon() {
		clickOnElement(policyNameDescIcon);
	}
	
	public void clickOnPolicyDescriptionAscIcon() {
		clickOnElement(policyDescriptionAscIcon);
	}
	
	public void clickOnPolicyDescriptionDescIcon() {
		clickOnElement(policyDescriptionDescIcon);
	}
	
	public void clickOnDatashareViewButton() {
		clickOnElement(datashareViewButton);
	}

	public void selectPolicyGroupDropdown(String value) {
		clickOnElement(policyGroupDropdown);
		enter(policyGroupDropdownSearchInput,value);
		clickOnElement(policyGroupDropdownOption1);
	}
	
	public void enterDeactivatedPolicyGroup(String value) {
		clickOnElement(policyGroupDropdown);
		enter(policyGroupDropdownSearchInput,value);
	}
	
	public boolean isCancelFormDisplayed() {
		return isElementDisplayed(cancelForm);
	}
	
	public void clickOnCancelForm() {
		clickOnElement(cancelForm);
	}
	
	public boolean isDataLostWarningMessageDisplayed() {
		return isElementDisplayed(dataLostWarningMessage);
	}
	
	public void clickOnlostWarningProceedButton() {
		clickOnElement(lostWarningProceedButton);
	}
	
	public void clickOnlostWarningCancelButton() {
		clickOnElement(lostWarningCancelButton);
	}
	
	public boolean isSubTitleOfTabularViewDisplayed() {
		return isElementDisplayed(subTitleOfTabularView);
	}
	
	public boolean isPolicyIdHeaderTextDisplayed() {
		return isElementDisplayed(policyIdHeader);
	}
	
	public boolean isPolicyNameHeaderTextDisplayed() {
		return isElementDisplayed(policyNameHeader);
	}
	
	public boolean isPolicyDescriptionHeaderTextDisplayed() {
		return isElementDisplayed(policyDescriptionHeader);
	}
	
	public boolean isPolicyGroupHeaderTextDisplayed() {
		return isElementDisplayed(policyGroupHeader);
	}
	
	public boolean isCreatedDateHeaderTextDisplayed() {
		return isElementDisplayed(creationDateHeader);
	}
	
	public boolean isStatusHeaderTextDisplayed() {
		return isElementDisplayed(statusHeader);
	}
	
	public boolean isActionHeaderTextDisplayed() {
		return isElementDisplayed(actionHeader);
	}
	
	public boolean isPolicyIdAscIconDisplayed() {
		return isElementDisplayed(policyIdAscIcon);
	}
	
	public boolean isPolicyIdDescIconDisplayed() {
		return isElementDisplayed(policyIdDescIcon);
	}
	
	public boolean isPolicyNameAscIconDisplayed() {
		return isElementDisplayed(policyNameAscIcon);
	}
	
	public boolean isPolicyNameDescIconDisplayed() {
		return isElementDisplayed(policyNameDescIcon);
	}
	
	public boolean isPolicyDescriptionAscIconDisplayed() {
		return isElementDisplayed(policyDescriptionAscIcon);
	}
	
	public boolean isPolicyDescriptionDescIconDisplayed() {
		return isElementDisplayed(policyDescriptionDescIcon);
	}
	
	public boolean isPolicyGroupAscIconDisplayed() {
		return isElementDisplayed(policyGroupNameAscIcon);
	}
	
	public boolean isPolicyGroupDescIconDisplayed() {
		return isElementDisplayed(policyGroupNameDescIcon);
	}
	
	public boolean isCreatedDateTimeDescISconDisplayed() {
		return isElementDisplayed(creationDateDescIcon);
	}
	
	public boolean isCreatedDateTimeAscIconDisplayed() {
		return isElementDisplayed(creationDateAscIcon);
	}
	
	public boolean isStatusDescIconDisplayed() {
		return isElementDisplayed(statusDescIcon);
	}
	
	public boolean isStatusAscIconDisplayed() {
		return isElementDisplayed(statusAscIcon);
	}
	
	public boolean isMosipRightsTextDisplayed() {
		return isElementDisplayed(mosipRightsText);
	}
	
	public boolean isFooterDocumentationLinkDisplayed() {
		return isElementDisplayed(footerDocumentationLink);
	}
	
	public boolean isFooterContactUsLinkDisplayed() {
		return isElementDisplayed(footerContactUsLink);
	}
	
	public boolean isPaginationDisplayed() {
		return isElementDisplayed(pagination);
	}
	
	public boolean isFiletrButtonDisplayedOrEnabled() {
		return isElementEnabled(filterButton);
	}
	
	public void clickOnDatasharePolicyList1() {
		clickOnElement(datasharePolicy1);
	}
	
	public void clickOnDatasharePolicyList2() {
		clickOnElement(datasharePolicy2);
	}
	
	public void clickOnDatasharePolicyList4() {
		clickOnElement(datasharePolicy4);
	}
	
	public boolean isViewDatasharePolicyPageTitleDisplayed() {
		return isElementDisplayed(viewPolicyPageTitle);
	}
	
	public void clickOnViewBackButton() {
		clickOnElement(viewBackButton);
	}
	
	public void clickOnActionButton() {
		clickOnElement(actionButton);
	}
	
	public void clickOnPublishButton() {
		clickOnElement(publishButton);
	}
	
	public boolean isPublishButtonDisplayed() {
		return isElementDisplayed(publishButton);
	}
	
	public boolean isPublishButtonEnabled() {
		return isElementEnabled(publishButton);
	}
	
	public boolean isViewButtonEnabled() {
		return isElementEnabled(viewButton);
	}
	
	public boolean isViewButtonDisplayed() {
		return isElementDisplayed(viewButton);
	}
	
	public boolean isCloneButtonDisplayed() {
		return isElementDisplayed(cloneButton);
	}
	
	public boolean isDeactivateButtonDisplayed() {
		return isElementDisplayed(deactivateButton);
	}
	
	public boolean isPublishConfirmationPopupDisplayed() {
		return isElementDisplayed(publishConfirmationPopup);
	}
	
	public boolean isPublishPolicyInfoMessageisplayed() {
		return isElementDisplayed(publishPolicyInfoMessage);
	}
	
	public boolean isPublishPolicyCancelButtonDisplayed() {
		return isElementDisplayed(publishPolicyCancelButton);
	}
	
	public boolean isPublishPolicyButtonDisplayed() {
		return isElementDisplayed(publishPolicyButton);
	}
	
	public void clickOnPublishPolicyButton() {
		clickOnElement(publishPolicyButton);
	}
	
	public void clickOnDeactivateConfirmButton() {
		clickOnElement(deactivateConfirmButton);
	}
	
	public void clickOnDeactivateButton() {
		clickOnElement(deactivateButton);
	}
	

	
	public void clickOnPolicyGroupNameAscIcon() {
		clickOnElement(policyGroupNameAscIcon);
	}
	
	public void clickOnPolicyGroupNameDescIcon() {
		clickOnElement(policyGroupNameDescIcon);
	}
	
	public void clickOnCreationDateAscIcon() {
		clickOnElement(creationDateAscIcon);
	}
	
	public void clickOnCreationDateDescIcon() {
		clickOnElement(creationDateDescIcon);
	}
	
	public void clickOnStatusDescIcon() {
		clickOnElement(statusDescIcon);
	}
	
	public void clickOnStatusAscIcon() {
		clickOnElement(statusAscIcon);
	}
	
	public void selectActivateStatusFilter() {
		clickOnElement(policyStatusFilter);
		clickOnElement(activateStatusButton);
	}
	
	public void clickOnViewButton() {
		clickOnElement(viewButton);
	}
	
	public boolean isHomeButtonDisplayed() {
		return isElementDisplayed(homeButton);
	}
	
	public boolean isPolicyIdLabelDisplayed() {
		return isElementDisplayed(policyIdLabel);
	}
	
	public boolean isPolicyIdContextDisplayed() {
		return isElementDisplayed(policyIdContext);
	}
	
	public boolean isPolicyNameLabelDisplayed() {
		return isElementDisplayed(policyNameLabel);
	}
	
	public boolean isPolicyNameContextDisplayed() {
		return isElementDisplayed(policyNameContext);
	}
	
	public boolean isPolicyGroupLabelDisplayed() {
		return isElementDisplayed(policyGroupLabel);
	}
	
	public boolean isPolicyGroupContextDisplayed() {
		return isElementDisplayed(policyGroupContext);
	}
	
	public boolean isPolicyDescriptionLabelDisplayed() {
		return isElementDisplayed(policyDescriptionLabel);
	}
	
	public boolean isPolicyDescriptionContextDisplayed() {
		return isElementDisplayed(policyDescriptionContext);
	}
	
	public boolean isPolicyGroupDescriptionLabelDisplayed() {
		return isElementDisplayed(policyGroupDescriptionLabel);
	}
	
	public boolean isPolicyGroupDescriptionContextDisplayed() {
		return isElementDisplayed(policyGroupDescriptionContext);
	}
	
	public boolean isPolicyDataLabelDisplayed() {
		return isElementDisplayed(policyDataLabel);
	}
	
	public boolean isPolicyDataPreviewDisplayed() {
		return isElementDisplayed(policyDataPreviewButton);
	}
	
	public boolean isPolicyDataTitleDisplayed() {
		return isElementDisplayed(policyDataLabel);
	}
	
	public boolean ispolicyDataPopupDisplayed() {
		return isElementDisplayed(policyDataPopup);
	}
	
	public boolean isDownloadButtonDisplayed() {
		return isElementDisplayed(downloadButton);
	}
	
	public boolean isCloseButtonDisplayed() {
		return isElementDisplayed(closeButton);
	}
	
	public void clickOnPolicyDataPreviewButton() {
		clickOnElement(policyDataPreviewButton);
	}
	
	public void clickOnDownloadButton() {
		clickOnElement(downloadButton);
	}
	
	public void clickOnCloseButton() {
		clickOnElement(closeButton);
	}
	
	public boolean isPolicyDataBoxEnabled() {
		return isElementEnabled(policyDataBox);
	}
	
	public boolean isPolicyDataContentDisplayed() {
		return isElementDisplayed(policyDataBoxWithAttributes);
	}
	
	public void clickOnPublishPolicyCloseButton() {
		clickOnElement(publishPolicyCloseButton);
	}
	
	public boolean isNoDataAvailableDisplayed() {
		return isElementDisplayed(noDataAvailable);
	}
	
	public void clickOnTitleBackIcon() {
		clickOnElement(titleBackIcon);
	}
	
	public void clickOnPublishPolicyCancelButton() {
		clickOnElement(publishPolicyCancelButton);
	}
	
	public boolean isCloneButtonEnabled() {
		return isElementEnabled(cloneButton);
	}
	
	public boolean isDeactivateConfirmButtonAvailable() {
		return isElementDisplayed(deactivateConfirmButton);
	}
	
	public boolean isDeactivateCancelButtonAvailable() {
		return isElementDisplayed(deactivateCancelButton);
	}
	
	public void clickOnDeactivateCancelButton() {
		clickOnElement(deactivateCancelButton);
	}
	
	public boolean isDeactivateButtonEnabled() {
		return isElementEnabled(deactivateButton);
	}
	
	public void clickOnSubTitleButton() {
		clickOnElement(subTitleButton);
	}
	
	public void clickOnCloneButton() {
		clickOnElement(cloneButton);
	}
	
	public boolean isClonePolicyPopupTitleDisplayed() {
		return isElementDisplayed(clonePolicyTitle);
	}
	
	public boolean isClonePolicyInfoMessageDisplayed() {
		return isElementDisplayed(clonePolicyInfoMessage);
	}
	
	public boolean isClonePolicyGroupDropdownDisplayed() {
		return isElementDisplayed(clonePolicyGroupDropdown);
	}
	
	public void clickOnClonePolicyGroupDropdown() {
		clickOnElement(clonePolicyGroupDropdown);
	}
	
	public boolean isClonePolicyGroupSearchInputDisplayed() {
		return isElementDisplayed(clonePolicyGroupDropdownSearchInput);
	}
	
	public void searchPolicyGroupForClone(String value) {
		clickOnElement(clonePolicyGroupDropdownSearchInput);
		enter(clonePolicyGroupDropdownSearchInput,value);
	}
	
	public boolean isClonePolicyGroupNameDisplayed() {
		return isElementDisplayed(clonePolicyGroupName);
	}
	
	public boolean isClonePolicyGroupDescriptionDisplayed() {
		return isElementDisplayed(clonePolicyGroupDescription);
	}
	
	public void selectPolicyGroupForClone(String value) {
		clickOnElement(clonePolicyGroupDropdown);
		clickOnElement(clonePolicyGroupDropdownSearchInput);
		enter(clonePolicyGroupDropdownSearchInput,value);
		clickOnElement(clonePolicyGroupDropdownOption1);
	}
	
	public boolean isClonePolicyButtonAvailable() {
		return isElementDisplayed(clonePolicyButton);
	}
	
	public boolean isClonePolicyCancelButtonAvailable() {
		return isElementDisplayed(clonePolicyCancelButton);
	}
	
	public boolean isClonePolicyButtonEnabled() {
		return isElementEnabled(clonePolicyButton);
	}
	
	public void clickOnClonePolicyButton() {
		clickOnElement(clonePolicyButton);
	}
	
	public void clickOnClonePolicyCloseButton() {
		clickOnElement(clonePolicyCloseButton);
	}
	
	public boolean isClonePolicyCancelButtonEnabled() {
		return isElementEnabled(clonePolicyCancelButton);
	}
	
	public boolean isClonePolicyCloseButtonEnabled() {
		return isElementEnabled(clonePolicyCloseButton);
	}
	
	public boolean isClonedSuccessMessageDisplayed() {
		return isElementDisplayed(clonedSuccessMessage);
	}
	
	public boolean isUpdatedPolicyGroupDisplayed() {
		return isElementDisplayed(updatedPolicyGroup);
	}
	
	public boolean isClonedPolicyStatusDraftDisplayed() {
		return isElementDisplayed(clonedPolicyStatusDraft);
	}
	
	public void clickOnClonePolicyCancelButton() {
		clickOnElement(clonePolicyCancelButton);
	}
	
	public boolean isAlreadyExistErrorMessageDisplayed() {
		return isElementDisplayed(alreadyExistErrorMessage);
	}
	
	public void clickOnCloseIcon() {
		clickOnElement(closeIcon);
	}
	
	public boolean isViewPolicyDetailsStatusDraftDisplayed() {
		return isElementDisplayed(viewPolicyStatusDraft);
	}
	
	public boolean isDeactivatePolicyPopupDisplayed() {
		return isElementDisplayed(deactivatePolicyPopup);
	}
	
	public boolean isDeactivatePolicyPopupTitleDisplayed() {
		return isElementDisplayed(deactivatePolicyPopup);
	}
	
	public boolean isDeactivatePolicyInfoMessageDisplayed() {
		return isElementDisplayed(deactivatePolicyInfoMessage);
	}
	
	public boolean isPartnerPolicyLinkActivatedErrorDisplayed() {
		return isElementDisplayed(partnerPolicyLinkActivated);
	}

	public void clickOnAlertErrorOkButton() {
		clickOnElement(alertErrorOkButton);
	}
	
	public boolean isPartnerPolicyLinkPendingErrorDisplayed() {
		return isElementDisplayed(partnerPolicyLinkPending);
	}
	
	public void clickOnHomeButton() {
		clickOnElement(homeButton);
	}

	public boolean isEditButtonEnable() {
		return isElementEnabled(policyEditButton);
	}
	
	public void clickOnEditButton() {
		clickOnElement(policyEditButton);
	}
	
	public boolean isEditPolicyPageTitleDisplayed() {
		return isElementDisplayed(editPolicyPageTitle);
	}
	
	public boolean isPolicyFormSubTitleDisplayed() {
		return isElementDisplayed(policyFormSubTitle);
	}
	
	public boolean isPolicyGroupDropdownEnabled() {
		return isElementEnabled(disabledPolicyGroupDropdown);
	}
	
	public boolean isEditPolicyGroupDropdownValueDisplayed() {
		return isElementDisplayed(editPolicyGroupDropdownValue);
	}
	
	public boolean isPolicyNameBoxDisplayed() {
		return isElementDisplayed(policyNameBox);
	}
	
	public boolean isEditPolicyNameValueDisplayed() {
		return isElementDisplayed(editPolicyNameValue);
	}
	
	public boolean isEditPolicyDescriptionValueDisplayed() {
		return isElementDisplayed(editPolicyDescriptionValue);
	}
	
	public boolean isEditPolicyDataContextDisplayed() {
		return isElementDisplayed(editPolicyDataContext);
	}
	
	public boolean isReUploadPolicyDataLabelDisplayed() {
		return isElementDisplayed(reuploadPolicyDataLabel);
	}
	
	public boolean isReuploadButtonDisplayed() {
		return isElementDisplayed(reUploadButton);
	}
	
	public boolean isPolicyDescriptionBoxDisplayed() {
		return isElementDisplayed(policyDescriptionBox);
	}
	
	public boolean isEditPolicyClearButtonDisplayed() {
		return isElementDisplayed(undoChangesButton);
	}
	
	public boolean isEditPolicyCancelButtonDisplayed() {
		return isElementDisplayed(editPolicyFormCancelButton);
	}
	
	public boolean isEditPolicySubmitButtonDisplayed() {
		return isElementDisplayed(editPolicyFormSubmitButton);
	}
	
	public boolean isEditPolicySubmitButtonEnabled() {
		return isElementEnabled(editPolicyFormSubmitButton);
	}
	
	public boolean isEditPolicySuccessTitleDisplayed() {
		return isElementDisplayed(editPolicySuccessTitle);
	}
	
	public boolean isEditPolicySuccessSubTitleDisplayed() {
		return isElementDisplayed(editPolicySuccessSubTitle);
	}
	
	public boolean isEditSuccessGoBackButtonEnabled() {
		return isElementEnabled(editSuccessGoBackButton);
	}
	
	public boolean isEditSuccessHomeButtonEnabled() {
		return isElementEnabled(editSuccessHomeButton);
    }
	
	public void clearTextBoxPolicyData() {
		clickOnElement(policyDataBox);
		clearTextBox(policyDataBox);
	}
	
	public void clickOnEditPolicyFormCancelButton() {
		clickOnElement(editPolicyFormCancelButton);
	}
	
	public boolean isChangesLostConfirmationMessageDisplayed() {
		return isElementDisplayed(confirmationMessage);
	}
	
	public void clickOnChangesLostProceedButton() {
		clickOnElement(changesLostProceedButton);
	}
	
	public void clickOnChangesLostCancelButton() {
		clickOnElement(changesLostCancelButton);
	}
	
	public void clickOnUndoChangesButton() {
		clickOnElement(undoChangesButton);
	}
	
	public boolean isSpecialCharactersAreNotAllowedErrorMessageDisplayed() {
		return isElementDisplayed(specialCharactersAreNotAllowedErrorMessage);
	}
	
	public void clickOnEditPolicyFormSubmitButton() {
		clickOnElement(editPolicyFormSubmitButton);
	}

}
	
