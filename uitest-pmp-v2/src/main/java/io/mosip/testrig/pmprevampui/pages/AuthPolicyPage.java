package io.mosip.testrig.pmpuiv2.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmpuiv2.utility.TestRunner;

public class AuthPolicyPage extends BasePage {

	@FindBy(id = "create_auth_policy_btn")
	private WebElement createAuthPolicyButton;
	
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
	private WebElement saveAsDraftButton;

	@FindBy(id = "confirmation_go_back_btn")
	private WebElement goBackButton;
	
	@FindBy(id = "filter_btn")
	private WebElement filterButton;
	
	@FindBy(id = "policy_name_filter")
	private WebElement policyNameFilter;
	
	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilterButton;
	
	@FindBy(id = "policy_name_filter_option1")
	private WebElement policyNameFilterOption1;
	
	@FindBy(id = "policies_list_view1")
	private WebElement policiesListViewElipsisButton;
	
	@FindBy(id = "policy_edit_btn")
	private WebElement policyEditButton;
	
	@FindBy(id = "policy_publish_btn")
	private WebElement policyPublishButton;
	
	@FindBy(id = "publish_policy_button")
	private WebElement publishPolicyButton;
	
	@FindBy(id = "success_msg_close_icon")
	private WebElement successMsgCloseButton;
	
	@FindBy(id = "publish_policy_close_button")
	private WebElement publishPolicyCloseButton;
	
	@FindBy(id = "policy_group_filter")
	private WebElement policyGroupFilterBox;
	
	@FindBy(id = "policy_deactivate_btn")
	private WebElement deactivateButton;
	
	@FindBy(id = "deactivate_policy_group__confirm_btn")
	private WebElement deactivateConfirmButton;
	
	@FindBy(xpath = "//h1[text()='Create Authentication Policy']")
	private WebElement titleOfCreatePolicYPage;
	
	@FindBy(xpath = "//p[contains(text(), 'All fields marked with')]")
	private WebElement authPolicyFormSubTitle;
	
	@FindBy(xpath = "//span[text()='Select policy group']")
	private WebElement policyGroupPlaceholder;
	
	@FindBy(xpath = "//span[text()='automationui policy group']")
	private WebElement policyGroupName;
	
	@FindBy(xpath = "//p[text()='desc automationui policy group']")
	private WebElement policyGroupDescription;
	
	@FindBy(xpath = "//p[text()='No Data Available.']")
	private WebElement noDataAvailable;

	@FindBy(xpath = "//input[@placeholder='Enter name for Authentication Policy']")
	private WebElement authPolicyPlaceHolder;
	
	@FindBy(xpath = "//textarea[@placeholder='Enter description about Authentication Policy']")
	private WebElement authPolicyDescriptionInput;
	
	@FindBy(xpath = "//p[text()='The given policy name already exists. Please try with a different policy name']")
	private WebElement policyNameExistErrorMessage;
	
	@FindBy(id = "error_close_btn")
	private WebElement errorCloseButton;
	
	@FindBy(xpath = "//h6[text()='Upload Policy Data']")
	private WebElement uploadPolicyDataLabel;
	
	@FindBy(xpath = "//p[text()='Only .json file format is allowed for upload']")
	private WebElement uploadPolicyDataHelpText;
	
	@FindBy(xpath = "//textarea[@placeholder='Upload the json file successfully to display its content here']")
	private WebElement fileUploadPlaceHolder;
	
	@FindBy(id = "policy_data_box")
	private WebElement policyDataBox;
	
	@FindBy(xpath = "//textarea[contains(text(), '\"authTokenType\": \"policy\"')]")
	private WebElement uploadedPolicyData;
	
	@FindBy(xpath = "//p[text()='Policy data has been uploaded successfully']")
	private WebElement policyDataUploadedSuccessMessage;
	
	@FindBy(xpath = "//textarea[contains(@value, 'vcgdytdsgvdshaccggv')]")
	private WebElement editedPolicyData;
	
	@FindBy(xpath = "//p[text()='Please provide valid JSON data']")
	private WebElement provideValidJsonErroMessage;
	
	@FindBy(xpath = "//p[text()='Policy data should not exceed more than 5120 characters.']")
	private WebElement policyDataExceedChractersMessage;
	
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
	
	@FindBy(xpath = "//p[text()='Policy ID']")
	private WebElement policyIdLabel;
	
	@FindBy(xpath = "//p[text()='Policy Name']")
	private WebElement policyNameLabel;
	
	@FindBy(xpath = "//p[text()='Policy Description']")
	private WebElement policyDescriptionLabel;
	
	@FindBy(xpath = "//p[text()='Policy Group']")
	private WebElement policyGroupLabel;
	
	@FindBy(xpath = "//p[text()='Status']")
	private WebElement statusLabel;
	
	@FindBy(xpath = "//input[@placeholder='Search Policy ID']")
	private WebElement searchPolicyId;
	
	@FindBy(xpath = "//input[@placeholder='Search Policy Name']")
	private WebElement searchPolicyName;
	
	@FindBy(xpath = "//input[@placeholder='Search Policy Description']")
	private WebElement searchPolicyDescription;
	
	@FindBy(xpath = "//input[@placeholder='Search Policy Group']")
	private WebElement searchPolicyGroup;
	
	@FindBy(xpath = "//span[text()='Select Status']")
	private WebElement selectStatus;
	
	@FindBy(id = "status_filter")
	private WebElement statusFilter;
	
	@FindBy(id = "status_filter_option1")
	private WebElement activatedStatus;
	
	@FindBy(id = "status_filter_option2")
	private WebElement deactivatedStatus;
	
	@FindBy(id = "status_filter_option3")
	private WebElement draftStatus;
	
	@FindBy(xpath = "(//div[contains(text(), 'Activated')])[1]")
	private WebElement statusActivated;
	
	@FindBy(xpath = "(//div[contains(text(), 'Deactivated')])[2]")
	private WebElement statusDeactivated;
	
	@FindBy(id = "policies_list_item1")
	private WebElement authPolicy1;
	
	@FindBy(xpath = "//h1[text()='View Authentication Policy']")
	private WebElement policyViewPageTitle;
	
	@FindBy(id = "auth_Policy_view_back_btn")
	private WebElement viewBackButton;
	
	@FindBy(id = "policy_details_view_btn")
	private WebElement viewButton;
	
	@FindBy(id = "policy_replicate_btn")
	private WebElement cloneButton;
	
	@FindBy(xpath = "//p[text()='No Results Found']")
	private WebElement noResultFound;
	
	@FindBy(xpath = "//button[text()='x']")
	private WebElement closeButton;
	
	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;
	
	@FindBy(xpath = "//h6[text()='Items per page']")
	private WebElement prefixOfPage;
	
	@FindBy(id = "pagination_card")
	private WebElement pagination;
	
	@FindBy(id = "pagination_select_record_per_page")
	private WebElement recordPerPage;
	
	@FindBy(id = "pagination_select_record_per_page")
	private WebElement expandForPage;
	
	@FindBy(id = "expand_more_FILL0_wght400_GRAD0_opsz48")
	private WebElement expandIcon;
	
	@FindBy(xpath = "//a[@aria-label='Previous page']")
	private WebElement previousPage;
	
	@FindBy(xpath = "//a[@aria-label='Next page']")
	private WebElement nextPage;
	
	@FindBy(xpath = "//a[@aria-label='Page 1 is your current page']")
	private WebElement page1;
	
	@FindBy(xpath = "//a[@aria-label='Page 2 is your current page']")
	private WebElement page2;
	
	@FindBy(id = "pagination_each_num_option2")
	private WebElement itemNumber;
	
	@FindBy(xpath = "//p[text()='8']")
	private WebElement itemPerPage8;
	
	@FindBy(xpath = "//p[text()='16']")
	private WebElement itemPerPage16;
	
	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;
	
	@FindBy(id = "sub_title_btn")
	private WebElement subTitleButton;
	
	@FindBy(xpath = "//p[text()='Policy ID']")
	private WebElement policyId;
	
	@FindBy(xpath = "//span[text()='ab11CD22ef']")
	private WebElement policyIdContext;
	
	@FindBy(xpath = "//p[text()='Policy Name']")
	private WebElement policyName;
	
	@FindBy(xpath = "//p[text()='authpolicy03']")
	private WebElement policyNameContext;
	
	@FindBy(xpath = "//p[text()='Policy Group']")
	private WebElement policyGroup;
	
	@FindBy(xpath = "//p[text()='automationui policy group']")
	private WebElement policyGroupContext;
	
	@FindBy(xpath = "//p[text()='Policy Description']")
	private WebElement policyDescription;
	
	@FindBy(xpath = "//p[text()='authpolicy 03']")
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
	private WebElement previewCloseButton;
	
	@FindBy(xpath = "//textarea[contains(text(), '\"authTokenType\": \"policy\"')]")
	private WebElement policyData;
	
	@FindBy(xpath = "//div[text()='Deactivated']")
	private WebElement deactivateStatus;
	
	@FindBy(xpath = "//div[text()='Activated']")
	private WebElement activateStatus;
	
	@FindBy(xpath = "//button[text()='Select Status']")
	private WebElement selectStatusButton;
	
	@FindBy(xpath = "//h3[text()='Publish Policy']")
	private WebElement publishPopup;
	
	@FindBy(xpath = "//p[contains(text(), 'By clicking ‘Publish,’ the policy - ')]")
	private WebElement publishPopupInfoText;
	
	@FindBy(id = "publish_policy_cancel")
	private WebElement publishCancelButton;
	
	@FindBy(xpath = "//p[contains(text(), 'List of Authentication Policies')]")
	private WebElement listOfPolicies;
	
	@FindBy(id = "clone_policy_group_dropdown_search_input")
	private WebElement clonePolicyGroupsearchInput;
	
	@FindBy(xpath = "//h1[text()='Policy saved as Draft']")
	private WebElement policySavedAsDraft;
	
	@FindBy(id = "create_policy_form_cancel_btn")
	private WebElement policyCancelButton;
	
	@FindBy(id = "create_policy_form_clear_btn")
	private WebElement policyClearButton;
	
	@FindBy(xpath = "//p[contains(text(), 'This Authentication Policy is currently in draft mode')]")
	private WebElement policyDraftInfoMessage;
	
	@FindBy(id = "block_messsage_proceed")
	private WebElement proceedButton;
	
	@FindBy(xpath = "//h3[text()='Clone Policy']")
	private WebElement clonePolicyTitle;
	
	@FindBy(xpath = "//p[contains(text(), 'Select the policy group to nest the cloned policy')]")
	private WebElement clonePolicyInfoMessage;
	
	@FindBy(id = "clone_policy_group_dropdown")
	private WebElement clonePolicyGroupDropdown;
	
	@FindBy(id = "clone_policy_group_dropdown_search_input")
	private WebElement clonePolicyGroupDropdownSearchInput;
	
	@FindBy(xpath = "//span[text()='A']")
	private WebElement clonePolicyGroupName;
	
	@FindBy(xpath = "//p[text()='A']")
	private WebElement clonePolicyGroupDescription;
	
	@FindBy(id = "clone_policy_group_dropdown_option1")
	private WebElement clonePolicyGroupDropdownOption1;
	
	@FindBy(id = "clone_policy_cancel")
	private WebElement clonePolicyCancelButton;
	
	@FindBy(id = "clone_policy_button")
	private WebElement clonePolicyButton;
	
	@FindBy(xpath = "//p[contains(text(),'has been cloned and associated to')]")
	private WebElement cloneSuccessMessage;
	
	@FindBy(id = "clone_policy_close_button")
	private WebElement clonePolicyCloseButton;
	
	@FindBy(xpath = "//td[text()='A']")
	private WebElement updatedPolicyGroup;
	
	@FindBy(xpath = "//div[text()='Draft']")
	private WebElement clonedPolicyStatusDraft;
	
	@FindBy(xpath = "//p[text()='This policy already exists within the selected policy group. Please choose a different policy group to proceed further.']")
	private WebElement alreadyExistErrorMessage;
	
	@FindBy(xpath = "//img[contains(@src, 'close_icon') and @alt='closeIcon']")
	private WebElement closeIcon;
	
	@FindBy(xpath = "//div[text()='Draft']")
	private WebElement viewPolicyStatusDraft;
	
	@FindBy(xpath = "//p[contains(text(), 'Do you want to deactivate Policy')]")
	private WebElement deactivatePolicyPopup;
	
	@FindBy(xpath = "//p[text()='Upon clicking ‘Confirm’, the selected Authentication Policy will be deactivated.']")
	private WebElement deactivatePolicyInfoMessage;
	
	@FindBy(id = "deactivate_policy_group_cancel_btn")
	private WebElement deactivateCancelButton;
	
	@FindBy(xpath = "//p[text()='Error:  Active Partner-Policy Linking Detected!']")
	private WebElement partnerPolicyLinkActivated;
	
	@FindBy(id = "alert_error_popup_okay_btn")
	private WebElement alertErrorOkButton;
	
	@FindBy(xpath = "//p[text()='Error: Partner - Policy Request Detected!']")
	private WebElement partnerPolicyLinkPending;
	
	@FindBy(xpath = "//h1[text()='Edit Authentication Policy']")
	private WebElement editPolicyPageTitle;
	
	@FindBy(xpath = "//span[contains(text(), 'automationui policy group')]")
	private WebElement editPolicyGroupDropdownValue;
	
	@FindBy(xpath = "//span[contains(text(), 'automationui policy group')]")
	private WebElement disabledPolicyGroupDropdown;
	
	@FindBy(xpath = "//input[@id='policy_name_box' and @value='authpolicy07']")
	private WebElement editPolicyNameValue;
	
	@FindBy(xpath = "//textarea[@id='policy_description_box' and text()='authpolicy 07']")
	private WebElement editPolicyDescriptionValue;
	
	@FindBy(xpath = "//textarea[contains(text(), '\"authTokenType\": \"policy\"')]")
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
	
	@FindBy(xpath = "//h1[text()='Authentication Policy updated and submitted successfully!']")
	private WebElement editPolicySuccessTitle;
	
	@FindBy(xpath = "//p[contains(text(), 'The Authentication Policy for Policy Group: 0004501 has been successfully updated and submitted. To publish the policy, select the 'Publish' option from the action menu on the 'List of Authentication Policies' screen.')]")
	private WebElement editPolicySuccessSubTitle;
	
	@FindBy(id = "confirmation_go_back_btn")
	private WebElement editSuccessGoBackButton;
	
	@FindBy(id = "confirmation_home_btn")
	private WebElement editSuccessHomeButton;
	
	@FindBy(id = "sub_title_home_btn")
	private WebElement subTitleHomeButton;
	
	public AuthPolicyPage(WebDriver driver) {
		super(driver);
	}
	
	public void clickOnCreateAuthPolicyButton() {
		clickOnElement(createAuthPolicyButton);
	}
	
	public void selectPolicyGroupDropdown(String policyGroupValue) {
		clickOnElement(policyGroupDropdown);
		enter(policyGroupDropdownSearchInput,policyGroupValue);
		clickOnElement(policyGroupDropdownOption1);
	}
	
	public void enterPolicyName(String policyNameValue) {
		enter(policyNameBox,policyNameValue);
	}
	
	public void enterpolicyDescription(String policyDescValue) {
		enter(policyDescriptionBox,policyDescValue);
	}
	
	public void uploadPolicyData() {
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_revamp_cert\\policyData.json");
	}
	
	public void uploadExceedData() {
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_revamp_cert\\exceedData.json");
	}
	
	public void uploadInvalidPolicyData() {
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_revamp_cert\\invalidData.json");
	}
	
	public void uploadBlankData() {
		uploadImage(uploadFile, TestRunner.getResourcePath() + "\\pmp_revamp_cert\\BlankData.json");
	}
	
	public void clickOnSaveAsDraftButton() {
		clickOnElement(saveAsDraftButton);
	}
	
	public void clickOnGoBackButton() {
		clickOnElement(goBackButton);
	}
	
	public void clickOnFilterButton() {
		clickOnElement(filterButton);
	}
	
	public boolean isFiletrButtonDisplayedOrEnabled() {
		return isElementEnabled(filterButton);
	}
	
	public void enterPolicyNameInFilter(String policyNameFilterValue) {
		enter(policyNameFilter,policyNameFilterValue);
	}
	
	public void clickOnPolicyNameFilter() {
		clickOnElement(policyNameFilter);
		clickOnElement(policyNameFilterOption1);
	}
	
	public void clickOnApplyFilterButton() {
		clickOnElement(applyFilterButton);
	}
	
	public void clickOnActionButton() {
		clickOnElement(policiesListViewElipsisButton);
	}
	
	public void clickOnPolicyPublishButton() {
		clickOnElement(policyPublishButton);
	}
	
	public void clickOnPublishPolicyButton() {
		clickOnElement(publishPolicyButton);
	}
	
	public void clickOnSuccessMsgCloseButton() {
		clickOnElement(successMsgCloseButton);
	}
	
	public void clickOnPublishPolicyCloseButton() {
		clickOnElement(publishPolicyCloseButton);
	}
	
	public void enterPolicyGroupFilterBox(String policyGroupFilterValue) {
		enter(policyGroupFilterBox, policyGroupFilterValue);
	}
	
	public boolean isDeactivateButtonEnabled() {
		return isElementDisplayed(deactivateButton);
	}
	
	public void clickOnDeactivateButton() {
		clickOnElement(deactivateButton);
	}
	
	public void clickOnDeactivateConfirmButton() {
		clickOnElement(deactivateConfirmButton);
	}
	
	public boolean isTitleOfCreatePolicYPageDisplayed() {
		return isElementDisplayed(titleOfCreatePolicYPage);
	}
	
	public boolean isAuthPolicyFormSubTitleDisplayed() {
		return isElementDisplayed(authPolicyFormSubTitle);
	}
	
	public boolean isPolicyGroupDropdownDisplayed() {
		return isElementDisplayed(policyGroupDropdown);
	}
	
	public void clickOnPolicyGroupDropdown() {
		clickOnElement(policyGroupDropdown);
	}
	
	public boolean isPolicyGroupDropdownSearchInputDisplayed() {
		return isElementDisplayed(policyGroupDropdownSearchInput);
	}
	
	public boolean isPolicyGroupPlaceHolderDisplayed() {
		return isElementDisplayed(policyGroupPlaceholder);
	}

	public void selectPolicyGroup(String policyGroupValue) {
		clickOnElement(policyGroupDropdown);
		enter(policyGroupDropdownSearchInput,policyGroupValue);
	}
	
	public void enterDeactivatedPolicyGroup(String policyGroupValue) {
		clickOnElement(policyGroupDropdown);
		enter(policyGroupDropdownSearchInput,policyGroupValue);
	}
	
	public boolean isPolicyGroupNameDisplayed() {
		return isElementDisplayed(policyGroupName);
	}
	
	public boolean isPolicyGroupDescriptionDisplayed() {
		return isElementDisplayed(policyGroupDescription);
	}
	
	public boolean isNoDataAvailableDisplayed() {
		return isElementDisplayed(noDataAvailable);
	}
	
	public boolean isAuthPolicyPlaceHolderDisplayed() {
		return isElementDisplayed(authPolicyPlaceHolder);
	}
	
	public boolean isPolicyDescriptionPlaceHolderDisplayed() {
		return isElementDisplayed(authPolicyDescriptionInput);
	}
	
	public boolean isPolicyNameExistErrorMessageDisplayed() {
		return isElementDisplayed(policyNameExistErrorMessage);
	}
	
	public void clickOnErrorCloseButton() {
		clickOnElement(errorCloseButton);
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
	
	public boolean isPolicyDataBoxEnabled() {
		return isElementEnabled(policyDataBox);
	}
	
	public boolean isPolicyDataContentDisplayed() {
		return isElementDisplayed(uploadedPolicyData);
	}
	
	public boolean isPolicyDataUploadedSuccessMessageDisplayed() {
		return isElementDisplayed(policyDataUploadedSuccessMessage);
	}
	
	public void editPolicyData(String policyData) {
		enter(policyDataBox,policyData);
	}
	
	public boolean isPolicyDataEdited() {
		return isElementDisplayed(editedPolicyData);
	}
	
	public boolean isProvideValidJsonDataErrorMessageDisplayed() {
		return isElementDisplayed(provideValidJsonErroMessage);
	}
	
	public void clearTextBoxPolicyData() {
		clickOnElement(policyDataBox);
		clearTextBox(policyDataBox);
	}
	
	public boolean isPolicyDataExceedChractersMessageDisplayed() {
		return isElementDisplayed(policyDataExceedChractersMessage);
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
	
	public boolean isPolicyIdFilterLabelDisplayed() {
		return isElementDisplayed(policyIdLabel);
	}
	
	public boolean isPolicyNameFilterLabelDisplayed() {
		return isElementDisplayed(policyNameLabel);
	}
	
	public boolean isPolicyDescriptionFilterLabelDisplayed() {
		return isElementDisplayed(policyDescriptionLabel);
	}
	
	public boolean isPolicyGroupFilterLabelDisplayed() {
		return isElementDisplayed(policyGroupLabel);
	}
	
	public boolean isStatusFilterLabelDisplayed() {
		return isElementDisplayed(statusLabel);
	}
	
	public boolean isPolicyIdFilterPlaceHolderDisplayed() {
		return isElementDisplayed(searchPolicyId);
	}
	
	public boolean isPolicyNameFilterPlaceHolderDisplayed() {
		return isElementDisplayed(searchPolicyName);
	}
	
	public boolean isPolicyGroupDescriptionFilterPlaceHolderDisplayed() {
		return isElementDisplayed(searchPolicyDescription);
	}
	
	public boolean isPolicyGroupFilterPlaceHolderDisplayed() {
		return isElementDisplayed(searchPolicyGroup);
	}
	
	public boolean isStatusFilterPlaceHolderDisplayed() {
		return isElementDisplayed(selectStatus);
	}
	
	public void selectActivateStatusFilter() {
		clickOnElement(statusFilter);
		clickOnElement(activatedStatus);
	}
	
	public void selectDeactivateStatusFilter() {
		clickOnElement(statusFilter);
		clickOnElement(deactivatedStatus);
	}
	
	public boolean isPolicyStatusActivateDisplayed() {
		return isElementDisplayed(statusActivated);
	}
	
	public boolean isPolicyStatusDeactivateDisplayed() {
		return isElementDisplayed(statusDeactivated);
	}
	
	public void clickOnActivatedAuthPolicy() {
		clickOnElement(authPolicy1);
	}
	
	public void clickOnDeactivatedAuthPolicy() {
		clickOnElement(authPolicy1);
	}
	
	public boolean isPolicyViewPageTitleDisplayed() {
		return isElementDisplayed(policyViewPageTitle);
	}
	
	public void clickOnViewBackButton() {
		clickOnElement(viewBackButton);
	}
	
	public boolean isViewButtonDisplayed() {
		return isElementDisplayed(viewButton);
	}
	
	public boolean isCloneButtonDisplayed() {
		return isElementDisplayed(cloneButton);
	}
	
	public boolean isCloneButtonEnabled() {
		return isElementEnabled(cloneButton);
	}
	
	public boolean isPolicyGroupDropdownEnabled() {
		return isElementEnabled(disabledPolicyGroupDropdown);
	}
	
	public boolean isDeactivateButtonDisplayed() {
		return isElementDisplayed(deactivateButton);
	}
	
	public boolean isNoResultsFoundMessageDisplayed() {
		return isElementDisplayed(noResultFound);
	}
	
	public void clickOnPolicyNameCloseButton() {
		clickOnElement(policyNameFilter);
		clickOnElement(closeButton);
	}
	
	public void clickOnPolicyGroupCloseButton() {
		clickOnElement(policyGroupFilterBox);
		clickOnElement(closeButton);
	}
	
	public boolean isApplyFilterButtonEnabled() {
		return isElementEnabled(applyFilterButton);
	}
	
	public boolean isFilterResetButtonAvailableOrEnabled() {
		return isElementEnabled(filterResetButton);
	}
	
	public void clickOnFilterResetButton() {
		clickOnElement(filterResetButton);
	}
	
	public boolean isPrefixOfPageDisplayed() {
		return isElementDisplayed(prefixOfPage);
	}
	
	public boolean isPaginationDisplayed() {
		return isElementDisplayed(pagination);
	}
	
	public boolean isRecordPerPageDisplayed() {
		return isElementDisplayed(recordPerPage);
	}
	
	public boolean isexpandIconDisplayed() {
		return isElementDisplayed(expandIcon);
	}
	
	public boolean isPreviusPageButtonDisplayed() {
		return isElementDisplayed(previousPage);
	}
	
	public boolean isNextPageButtonDisplayed() {
		return isElementDisplayed(nextPage);
	}
	
	public void clickOnNextPageButton() {
		clickOnElement(nextPage);
	}
	
	public void clickOnPreviusPageButton() {
		clickOnElement(previousPage);
	}
	
	public boolean isPage1Displayed() {
		return isElementDisplayed(page1);
	}
	
	public boolean isPage2Displayed() {
		return isElementDisplayed(page2);
	}
	
	public boolean isItemPerPage8Displayed() {
		return isElementDisplayed(itemPerPage8);
	}
	
	public void selectItemPerPageNumber() {
		clickOnElement(expandForPage);
		clickOnElement(itemNumber);
	}
	
	public boolean isItemPerPage16Displayed() {
		return isElementDisplayed(itemPerPage16);
	}
	
	public void clickOnViewButton() {
		clickOnElement(viewButton);
	}
	
	public boolean isHomeButtonDisplayed() {
		return isElementDisplayed(homeButton);
	}
	
	public boolean isSubTitleDisplayed() {
		return isElementDisplayed(subTitleButton);
	}
	
	public boolean isPolicyIdLabelDisplayed() {
		return isElementDisplayed(policyId);
	}
	
	public boolean isPolicyIdContextDisplayed() {
		return isElementDisplayed(policyIdContext);
	}
	
	public boolean isPolicyNameLabelDisplayed() {
		return isElementDisplayed(policyName);
	}
	
	public boolean isPolicyNameContextDisplayed() {
		return isElementDisplayed(policyNameContext);
	}
	
	public boolean isPolicyGroupLabelDisplayed() {
		return isElementDisplayed(policyGroup);
	}
	
	public boolean isPolicyGroupContextDisplayed() {
		return isElementDisplayed(policyGroupContext);
	}
	
	public boolean isPolicyDescriptionLabelDisplayed() {
		return isElementDisplayed(policyDescription);
	}
	
	public boolean isPolicyDescriptionContextDisplayed() {
		return isElementDisplayed(policyDescriptionContext);
	}
	
	public boolean isPolicyGroupDescriptionLabelDisplayed() {
		return isElementDisplayed(policyGroupDescription);
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
	
	public boolean isPolicyDataPopupDisplayed() {
		return isElementDisplayed(policyDataPopup);
	}
	
	public boolean isDownloadButtonDisplayed() {
		return isElementDisplayed(downloadButton);
	}
	
	public boolean isCloseButtonDisplayed() {
		return isElementDisplayed(previewCloseButton);
	}
	
	public void clickOnPolicyDataPreviewButton() {
		clickOnElement(policyDataPreviewButton);
	}
	
	public boolean isPolicyDataJsonDisplayed() {
		return isElementDisplayed(policyData);
	}
	
	public void clickOnDownloadButton() {
		clickOnElement(downloadButton);
	}
	
	public void clickOnPreviewCloseButton() {
		clickOnElement(previewCloseButton);
	}
	
	public boolean isPolicyStatusActivatedDisplayed() {
		return isElementDisplayed(activateStatus);
	}
	
	public boolean isPolicyStatusDeactivatedDisplayed() {
		return isElementDisplayed(deactivateStatus);
	}
	
	public void clickOnSubTitleButton() {
		clickOnElement(subTitleButton);
	}
	
	public void clickOnHomeButton() {
		clickOnElement(homeButton);
	}
	
	public void clickOnSelectStatusButton() {
		clickOnElement(statusFilter);
		clickOnElement(selectStatusButton);
	}
	
	public void clickOnExpandIcon() {
		clickOnElement(expandForPage);
	}

	public boolean isPolicyPublishButtonDisplayed() {
		return isElementDisplayed(policyPublishButton);
	}
	
	public boolean isPublishPopupDisplayed() {
		return isElementDisplayed(publishPopup);
	}
	
	public boolean isPublishPopupInfoTextDisplayed() {
		return isElementDisplayed(publishPopupInfoText);
	}
	
	public boolean isPublishPolicyButtonDisplayed() {
		return isElementDisplayed(publishPolicyButton);
	}
	
	public boolean isPublishCancelButtonDisplayed() {
		return isElementDisplayed(publishCancelButton);
	}
	
	public void clickOnPublishCancelButton() {
		clickOnElement(publishCancelButton);
	}
	
	public boolean isListOfPoliciesPageDisplayed() {
		return isElementDisplayed(listOfPolicies);
	}
	
	public void clickOnCloneButton() {
		clickOnElement(cloneButton);
	}
	
	public void selectPolicyGroupDropdownForClone(String value) {
		clickOnElement(clonePolicyGroupDropdown);
		enter(clonePolicyGroupsearchInput,value);
		clickOnElement(policyGroupDropdownOption1);
	}
	
	public boolean isPolicySavedAsDraftMessageDisplayed() {
		return isElementDisplayed(policySavedAsDraft);
	}
	
	public boolean isSaveAsDraftButtonEnabled() {
		return isElementEnabled(saveAsDraftButton);
	}
	
	public void clickOnPolicyCancelButton() {
		clickOnElement(policyCancelButton);
	}
	
	public void clickOnPolicyClearButton() {
		clickOnElement(policyClearButton);
	}
	
	public boolean isPolicyDraftInfoMessageDisplayed() {
		return isElementDisplayed(policyDraftInfoMessage);
	}
	
	public void clickOnProceedButton() {
		clickOnElement(proceedButton);
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
		enter(clonePolicyGroupDropdownSearchInput,value);
	}
	
	public boolean isClonePolicyGroupNameDisplayed() {
		return isElementDisplayed(clonePolicyGroupName);
	}
	
	public boolean isClonePolicyGroupDescriptionDisplayed() {
		return isElementDisplayed(clonePolicyGroupDescription);
	}
	
	public void selectPolicyGroupForClone(String value) {
		clickOnElement(clonePolicyGroupName);
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
		return isElementDisplayed(cloneSuccessMessage);
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
	
	public boolean isViewButtonEnabled() {
		return isElementEnabled(viewButton);
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
	
	public boolean isDeactivateConfirmButtonAvailable() {
		return isElementDisplayed(deactivateConfirmButton);
	}
	
	public boolean isDeactivateCancelButtonAvailable() {
		return isElementDisplayed(deactivateCancelButton);
	}
	
	public void clickOnDeactivateCancelButton() {
		clickOnElement(deactivateCancelButton);
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
	
	public boolean isPolicyNameBoxDisplayed() {
		return isElementDisplayed(policyNameBox);
	}
	
	public boolean isEditPolicyGroupDropdownValueDisplayed() {
		return isElementDisplayed(editPolicyGroupDropdownValue);
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
		return isElementDisplayed(reUploadFile);
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

}
