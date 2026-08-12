package io.mosip.testrig.pmpuiv2.pages;

import java.io.IOException;
import java.time.Duration;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class ManualAdjudicationPartnerPage extends BasePage {

	private static final Logger logger = Logger.getLogger(ManualAdjudicationPartnerPage.class);
	private static final Duration INLINE_VALIDATION_TIMEOUT = Duration.ofSeconds(2);
	private static final By MANUAL_ADJUDICATION_PARTNER_OPTION = By.xpath(
			"//*[contains(@id,'create_partner_partner_type_option') and normalize-space()='Manual Adjudication Partner']");

	@FindBy(id = "create_partner_btn")
	private WebElement createPartnerButton;

	@FindBy(id = "page_title")
	private WebElement createPartnerPageTitle;

	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;

	@FindBy(id = "sub_title_btn")
	private WebElement listOfPartnerButton;

	@FindBy(id = "create_partner_mandatory_msg")
	private WebElement createPartnerMandatoryMsg;

	@FindBy(id = "create_partner_partner_type_dropdown_btn")
	private WebElement partnerTypeDropdown;

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

	@FindBy(id = "create_partner_lang_code_option1")
	private WebElement notificationLanguageOption1;

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

	@FindBy(id = "create_partner_error_msg")
	private WebElement createPartnerErrorMessage;

	@FindBy(id = "error_close_btn")
	private WebElement errorMessageCloseButton;

	@FindBy(id = "create_partner_email_id_cancel_btn")
	private WebElement partnerEmailIdCancelBtn;

	@FindBy(id = "create_partner_organization_name_info")
	private WebElement organizationInfoButton;

	@FindBy(xpath = "(//*[@id='policy_group_selector_search_input'])[1]")
	private WebElement policyGroupDropdownSearchInput;

	@FindBy(id = "partner_upload_certificate_btn")
	private WebElement uploadOrReuploadCertificateButton;

	@FindBy(id = "partner_select_policy_group_btn")
	private WebElement selectPolicyGroupButton;

	@FindBy(id = "select_policy_group_popup_title")
	private WebElement policyGroupPopupTitle;

	@FindBy(id = "select_policy_group_popup_subtitle")
	private WebElement policyGroupPopupSubTitle;

	@FindBy(id = "select_policy_group_popup_description")
	private WebElement policyGroupPopupDescription;

	@FindBy(id = "select_policyPopup_partner_type")
	private WebElement policyGroupPopupPartnerType;

	@FindBy(id = "policy_group_selector_dropdown_button")
	private WebElement policyGroupPopupPolicyGroupDropdown;

	@FindBy(id = "policy_group_selector_search_input")
	private WebElement policyGroupPopupSearchInput;

	@FindBy(id = "policy_group_selector_option_name_1")
	private WebElement policyGroupPopupPolicyGroupName;

	@FindBy(id = "policy_group_selector_option_desc_1")
	private WebElement policyGroupPopupPolicyGroupDescription;

	@FindBy(id = "select_policy_group_cancel")
	private WebElement policyGroupPopupCancelButton;

	@FindBy(id = "select_policy_group_submit_btn")
	private WebElement policyGroupPopupSubmitButton;

	@FindBy(id = "partners_list_success_msg")
	private WebElement policyGroupPopupSuccessMessage;

	public ManualAdjudicationPartnerPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnCreatePartnerButton() {
		clickOnElement(createPartnerButton);
	}

	public boolean isCreatePartnerPageTitleDisplayed() {
		return isElementDisplayed(createPartnerPageTitle);
	}

	public String getBreadcrumbTextOfCreatePartnerPage() {
		return getTextFromLocator(homeButton) + getTextFromLocator(listOfPartnerButton);
	}

	public boolean isCreatePartnerMandatoryFieldInfoDisplayed() {
		return isElementDisplayed(createPartnerMandatoryMsg);
	}

	public boolean isDefaultManualAdjudicationPartnerDisplayed() {
		return getSelectedPartnerTypeText().trim().equals("Manual Adjudication Partner");
	}

	public void clickOnPartnerTypeDropdown() {
		clickOnElement(partnerTypeDropdown);
	}

	public boolean isManualAdjudicationPartnerOptionDisplayed() {
		return isDisplayed(MANUAL_ADJUDICATION_PARTNER_OPTION);
	}

	public void clickOnManualAdjudicationPartnerOption() {
		click(MANUAL_ADJUDICATION_PARTNER_OPTION);
	}

	public String getSelectedPartnerTypeText() {
		return getTextFromLocator(partnerTypeDropdown);
	}

	public void clickOnPartnerOrganizationInfoButton() {
		clickOnElement(organizationInfoButton);
	}

	public boolean isOrganizationNameInfoDisplayed() {
		return isElementDisplayed(organizationNameInfo);
	}

	public void selectPartnerType(String value) {
		try {
			dropdown(partnerTypeDropdown, value);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}

	public void selectPolicyGroupDropdown(String value) {
		ensurePolicyGroupDropdownOpen();
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
		ensurePolicyGroupDropdownOpen();
		clearTextBox(policyGroupDropdownSearchInput);
		enter(policyGroupDropdownSearchInput, value);
	}

	private void ensurePolicyGroupDropdownOpen() {
		if (!isElementDisplayedQuick(By.xpath("(//*[@id='policy_group_selector_search_input'])[1]"),
				INLINE_VALIDATION_TIMEOUT)) {
			clickOnElement(policyGroupDropdown);
		}
	}

	public void selectNotificationLanguage(String value) {
		if (!isElementDisplayedQuick(By.id("create_partner_lang_code_option1"), INLINE_VALIDATION_TIMEOUT)) {
			clickOnElement(notificationDropdown);
		}
		try {
			if (getTextFromLocator(notificationLanguageOption1).trim().equalsIgnoreCase(value)) {
				clickOnElement(notificationLanguageOption1);
				return;
			}
			click(By.xpath("//*[normalize-space()='" + value + "']"));
		} catch (RuntimeException e) {
			logger.info(e.getMessage());
			throw e;
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

	public boolean isCreatePartnerSubmitButtonDisabled() {
		return isElementDisabled(createPartnerSubmitButton);
	}

	public void clickOnCreatePartnerSubmitButton() {
		clickOnElement(createPartnerSubmitButton);
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

	public boolean isCreatePartnerSuccessMsgHomeButtonDisplayed() {
		return isElementDisplayed(createPartnerSuccessMsgHomeButton);
	}

	public void clickOnSuccessMsgHomeButton() {
		clickOnElement(createPartnerSuccessMsgHomeButton);
	}

	public boolean isPartnerAddressSpecialChNotAllowErrorDisplayed() {
		return isElementDisplayedQuick(By.id("create_partner_address_input_error"), INLINE_VALIDATION_TIMEOUT);
	}

	public boolean isPartnerOrgNameSpecialChNotAllowErrorDisplayed() {
		return isElementDisplayedQuick(By.id("create_partner_organization_name_input_error"),
				INLINE_VALIDATION_TIMEOUT);
	}

	public boolean isPartnerContactSpecialChNotAllowErrorDisplayed() {
		return isElementDisplayedQuick(By.id("create_partner_contact_number_input_error"), INLINE_VALIDATION_TIMEOUT);
	}

	public boolean isPartnerEmailIdSpecialChNotAllowErrorDisplayed() {
		return isElementDisplayedQuick(By.id("create_partner_email_id_input_error"), INLINE_VALIDATION_TIMEOUT);
	}

	public boolean isPartnerUserNameSpecialChNotAllowErrorDisplayed() {
		return isElementDisplayedQuick(By.id("create_partner_partner_id_input_error"), INLINE_VALIDATION_TIMEOUT);
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

	public void clickOnCancelConfirmationPopupProceedButton() {
		clickOnElement(cancelConfirmationPopupProceedButton);
	}

	public boolean isPartnerContactNumberNotAllowErrorDisplayed() {
		return isElementDisplayedQuick(By.id("create_partner_contact_number_input_error"), INLINE_VALIDATION_TIMEOUT);
	}

	public void clickOnPartnerEmailIdTextBox() {
		clickOnElement(partnerEmailIdTextBox);
	}

	public boolean isUsernameMustStartWithLetterErrorDisplayed() {
		return isElementDisplayedQuick(By.id("create_partner_partner_id_input_error"), INLINE_VALIDATION_TIMEOUT);
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

	public void clickOnListOfPartnerButton() {
		clickOnElement(listOfPartnerButton);
	}

	public boolean isManualAdjudicationPartnerRowStatusDisplayed(String partnerId, String certStatus, String status) {
		By rowLocator = By.xpath("//td[normalize-space()='" + partnerId + "']/parent::tr" + "[.//td[normalize-space()='"
				+ certStatus + "']" + " and .//td[normalize-space()='" + status + "']]");
		return isDisplayed(rowLocator);
	}

	public void clickActionButtonByPartnerId(String partnerId) {
		By actionBtn = By.xpath("//td[normalize-space()='" + partnerId + "']" + "/parent::tr//td//button");
		click(actionBtn);
	}

	public void clickOnUploadOrReuploadCertificateButton() {
		clickOnElement(uploadOrReuploadCertificateButton);
	}

	public boolean isPolicyGroupPopupTitleDisplayed() {
		return isElementDisplayed(policyGroupPopupTitle);
	}

	public boolean isPolicyGroupPopupSubTitleDisplayed() {
		return isElementDisplayed(policyGroupPopupSubTitle);
	}

	public boolean isPolicyGroupPopupDescriptionDisplayed() {
		return isElementDisplayed(policyGroupPopupDescription);
	}

	public boolean isPolicyGroupPopupPartnerTypeDisplayed() {
		return isElementDisplayed(policyGroupPopupPartnerType);
	}

	public boolean isPolicyGroupPopupPolicyGroupDropdownDisplayed() {
		return isElementDisplayed(policyGroupPopupPolicyGroupDropdown);
	}

	public boolean isPolicyGroupPopupSearchInputDisplayed() {
		return isElementDisplayed(policyGroupPopupSearchInput);
	}

	public boolean isPolicyGroupPopupPolicyGroupNameDisplayed() {
		return isElementDisplayed(policyGroupPopupPolicyGroupName);
	}

	public boolean isPolicyGroupPopupPolicyGroupDescriptionDisplayed() {
		return isElementDisplayed(policyGroupPopupPolicyGroupDescription);
	}

	public boolean isPolicyGroupPopupSubmitButtonDisabled() {
		return isElementDisabled(policyGroupPopupSubmitButton);
	}

	public boolean isPolicyGroupPopupSubmitButtonEnabled() {
		return isElementEnabled(policyGroupPopupSubmitButton);
	}

	public void clickOnPolicyGroupPopupPolicyGroupDropdown() {
		clickOnElement(policyGroupPopupPolicyGroupDropdown);
	}

	public void enterPolicyGroup(String value) {
		enter(policyGroupPopupSearchInput, value);
	}

	public void selectPolicyGroup(String value) {
		clickOnElement(policyGroupDropdown);
		enter(policyGroupPopupSearchInput, value);
		By policyGroupOption = By.xpath("//span[normalize-space()='" + value + "']");
		click(policyGroupOption);
	}

	public void clickOnPolicyGroupPopupCancelButton() {
		clickOnElement(policyGroupPopupCancelButton);
	}

	public void clickOnSelectPolicyGroupButton() {
		clickOnElement(selectPolicyGroupButton);
	}

	public boolean isPolicyGroupPopupSuccessMessageDisplayed() {
		return isElementDisplayed(policyGroupPopupSuccessMessage);
	}

	public void clickOnPolicyGroupPopupSubmitButton() {
		clickOnElement(policyGroupPopupSubmitButton);
	}

	public String getPolicyGroupText(String partnerId) {
		By policyGroup = By.xpath("//tr[td[normalize-space()='" + partnerId + "']]/td[4]");
		return getTextFromLocator(policyGroup);
	}

	public void clickOnViewButton() {
		click(By.id("partner_details_view_btn"));
	}

	public void clickOnDeactivateButton() {
		click(By.id("partner_deactive_btn"));
	}

	public boolean isViewPartnerDetailsPageDisplayed() {
		return isDisplayed(By.id("view_partner_details_partner_id"));
	}

	public boolean isPartnerIdInViewPageDisplayed() {
		return isDisplayed(By.id("view_partner_details_partner_id"));
	}

	public boolean isPartnerStatusInViewPageDisplayed() {
		return isDisplayed(By.id("view_partner_details_partner_status"));
	}

	public boolean isPartnerCreatedOnInViewPageDisplayed() {
		return isDisplayed(By.id("view_partner_details_partner_created_on"));
	}

	public String getPartnerTypeInViewPage() {
		return getTextFromLocator(driver.findElement(By.id("view_partner_type_context")));
	}

	public boolean isOrganisationNameInViewPageDisplayed() {
		return isDisplayed(By.id("view_partner_details_org_name_label"));
	}

	public boolean isDownloadCertificateOptionDisplayed() {
		return isDisplayed(By.id("download_partner_certificate_btn"))
				|| isDisplayed(By.id("view_partner_details_partner_certificate_title"));
	}

	public void clickOnViewPartnerBackButton() {
		click(By.id("title_back_icon"));
	}

	public void clickOnViewPartnerGoBackButton() {
		By goBack = By.id("view_partner_details_back_btn");
		if (isDisplayed(goBack)) {
			click(goBack);
		} else {
			clickOnViewPartnerBackButton();
		}
	}

	public boolean isDeactivatePopupDisplayed() {
		return isDisplayed(By.id("deactivate_popup_header"));
	}

	public void clickOnDeactivateConfirmButton() {
		click(By.id("deactivate_submit_btn"));
	}

	public void clickOnDeactivateCancelButton() {
		click(By.id("deactivate_cancel_btn"));
	}

	public boolean isPartnerStatusDisplayed(String partnerId, String status) {
		By rowLocator = By.xpath("//td[normalize-space()='" + partnerId + "']/parent::tr[.//td[normalize-space()='"
				+ status + "']]");
		return isDisplayed(rowLocator);
	}

	public void clickOnFilterButton() {
		click(By.id("filter_btn"));
	}

	public void enterPartnerIdInFilter(String partnerId) {
		enter(driver.findElement(By.id("partner_id_filter")), partnerId);
	}

	public void clickOnApplyFilterButton() {
		click(By.id("apply_filter__btn"));
	}

	public void clickOnFilterResetButton() {
		click(By.id("filter_reset_btn"));
	}

	// --- OVP-style aliases used by ManualAdjudicationPartnerTest ---

	public boolean isCertificateTrustStorePageDisplayed() {
		return isDisplayed(By.id("root_upload_trust_certificate_btn"))
				|| isDisplayed(By.xpath("//h1[contains(normalize-space(),'Certificate Trust Store')]"))
				|| isDisplayed(By.xpath("//*[contains(text(),'Root CA Certificate')]"));
	}

	public boolean isRootUploadTrustCertificateButtonDisplayed() {
		return isDisplayed(By.id("root_upload_trust_certificate_btn"));
	}

	public void clickonUploadRootTrustCertificate() {
		click(By.id("root_upload_trust_certificate_btn"));
	}

	public boolean isPartnerDomainDropdownDisplayed() {
		return isDisplayed(By.id("partnerDomain_selector_dropdown_dropdown_btn"));
	}

	public void clickOnAuthPartnerDomainTypeDropdown() {
		click(By.id("partnerDomain_selector_dropdown_dropdown_btn"));
		click(By.id("partnerDomain_selector_dropdown_option1"));
	}

	public void uploadAuthTrustRootCa() {
		uploadImage(driver.findElement(By.id("fileInput")),
				PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "RootCA.cer"));
	}

	public void uploadAuthTrustSubCa() {
		uploadImage(driver.findElement(By.id("fileInput")),
				PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "IntermediateCA.cer"));
	}

	public void uploadTrustCertificateAndConfirm() {
		click(By.id("upload_trust_certificate_submit_btn"));
		By goBack = By.id("confirmation_go_back_btn");
		if (isElementDisplayedQuick(goBack, Duration.ofSeconds(8))) {
			click(goBack);
		}
	}

	public boolean isIntermediateUploadTrustCertificateButtonDisplayed() {
		return isDisplayed(By.id("intermediate_upload_trust_certificate_btn"));
	}

	public void clickonUploadIntermediateTrustCertificate() {
		By tab = By.xpath("//h6[text()='Intermediate CA']");
		if (isDisplayed(tab)) {
			click(tab);
		}
		click(By.id("intermediate_upload_trust_certificate_btn"));
	}

	public boolean isPartnerTypeDropdownDisplayed() {
		return isElementDisplayed(partnerTypeDropdown);
	}

	public void selectManualAdjudicationPartnerInPartnerTypeDropdown() {
		clickOnPartnerTypeDropdown();
		clickOnManualAdjudicationPartnerOption();
	}

	public boolean isPolicyGroupSelected(String policyGroupName) {
		return getTextFromLocator(policyGroupDropdown).contains(policyGroupName)
				|| isDisplayed(By.xpath("//*[normalize-space()='" + policyGroupName + "']"));
	}

	public void enterAddress(String address) {
		enterPartnerAddress(address);
	}

	public void enterOrganizationName(String organisation) {
		enterPartnerOrganisation(organisation);
	}

	public void enterPhoneNumber(String number) {
		enterPartnerContactNumber(number);
	}

	public void enterEmailAdrress(String email) {
		enterEmailId(email);
	}

	public void enterUsername(String username) {
		enterUserName(username);
	}

	public void selectNotificationLanguageDropdown() {
		selectNotificationLanguage(GlobalConstants.MANUAL_ADJUDICATION_NOTIFICATION_LANGUAGE);
	}

	public void clickOnSubmitButton() {
		clickOnCreatePartnerSubmitButton();
	}

	public boolean isPartnerCreatedSuccessfully() {
		return isCreatePartnerSuccessMsgDisplayed();
	}

	public void clickOnUploadManualAdjudicationPartnerCertificateButton() {
		clickOnUploadPartnerCertificateButton();
	}

	public void uploadManualAdjudicationPartnerCertificate() {
		// Unique leaf for MA (same O=AABBCC); Client.cer is already used by Auth partner on env.
		By fileInput = By.id("fileInput");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(fileInput));
		uploadImage(input, PmpTestUtil.getResourceFilePath("pmp_uiv2_cert", "deactivateUserClient.cer"));
	}

	public void clickOnUploadCertificateFromPartnersList() {
		clickOnUploadOrReuploadCertificateButton();
		// Wait for upload dialog before caller sends the file
		new WebDriverWait(driver, Duration.ofSeconds(15))
				.until(ExpectedConditions.presenceOfElementLocated(By.id("fileInput")));
	}

	public void clickOnUploadSubmitButton() {
		By submitById = By.id("certificate_upload_submit_btn");
		if (isDisplayed(submitById)) {
			click(submitById);
		} else {
			click(By.xpath("//*[text()='Submit']"));
		}
	}

	public void clickOnUploadCloseButton() {
		By close = By.id("certificate_upload_close_btn");
		if (isDisplayed(close)) {
			click(close);
		}
	}

	public boolean isSubTitleListDisplayed() {
		return isListOfPartnersDisplayed() || isDisplayed(By.id("undefined_title"))
				|| isDisplayed(By.id("page_title"));
	}

	public void clickOnApplyFiltersBtn() {
		clickOnApplyFilterButton();
	}

	public boolean isActivatedPartnersDisplayed() {
		return isDisplayed(By.id("partner_list_item1"))
				|| isDisplayed(By.xpath("//tr[contains(@id,'partner_list_item')]"));
	}

	public void clickOnActionsButton() {
		click(By.id("partner_list_view1"));
	}

	public boolean isViewButtonsDisplayed() {
		return isDisplayed(By.id("partner_details_view_btn"));
	}

	public boolean isUploadCertificateFromListButtonDisplayed() {
		return isDisplayed(By.id("partner_upload_certificate_btn"));
	}

	public boolean isUploadCertificateFromListButtonEnabled() {
		// Action menu items are <p> tags; enabled/disabled is via text color, not HTML disabled.
		return isActionMenuItemEnabled(By.id("partner_upload_certificate_btn"));
	}

	public boolean isSelectPolicyGroupFromListButtonDisplayed() {
		return isDisplayed(By.id("partner_select_policy_group_btn"));
	}

	public boolean isSelectPolicyGroupButtonDisabled() {
		// PartnersList.js: Select Policy Group is enabled ONLY for MISP without policy group.
		// For MA it is a <p> with gray text / cursor-default — not HTML disabled.
		return !isSelectPolicyGroupButtonEnabled();
	}

	public boolean isSelectPolicyGroupButtonEnabled() {
		return isActionMenuItemEnabled(By.id("partner_select_policy_group_btn"));
	}

	private boolean isActionMenuItemDisabled(By locator) {
		return !isActionMenuItemEnabled(locator);
	}

	private boolean isActionMenuItemEnabled(By locator) {
		WebElement el = driver.findElement(locator);
		String cls = el.getAttribute("class");
		if (cls != null && cls.contains("3E3E3E")) {
			return true;
		}
		if (cls != null && (cls.contains("A5A5A5") || cls.contains("a5a5a5"))) {
			return false;
		}
		try {
			WebElement parent = el.findElement(By.xpath("./ancestor::div[@role='button'][1]"));
			String parentCls = parent.getAttribute("class");
			if (parentCls != null && parentCls.contains("cursor-pointer")
					&& !parentCls.contains("cursor-default")) {
				return true;
			}
		} catch (Exception ignored) {
		}
		return false;
	}

	public boolean isDeactivateButtonsDisplayed() {
		return isDisplayed(By.id("partner_deactive_btn"));
	}

	public void clickOnViewPartnerDetailsScreen() {
		clickOnViewButton();
	}

	public boolean isViewPartnersDetailsPageDisplayed() {
		return isViewPartnerDetailsPageDisplayed();
	}

	public boolean isPartnerIdDisplayed() {
		return isPartnerIdInViewPageDisplayed();
	}

	public boolean isPartnerStatusInViewPartnerPageDisplayed() {
		return isPartnerStatusInViewPageDisplayed();
	}

	public boolean isPartnerCreatedDateInViewPartnerPageDisplayed() {
		return isPartnerCreatedOnInViewPageDisplayed();
	}

	public boolean isViewPartnerDetailsPartnerTypeLabelDisplayed() {
		return isDisplayed(By.id("view_partner_details_partner_type_label"))
				|| isDisplayed(By.id("view_partner_details_partner_type_context"))
				|| isDisplayed(By.id("view_partner_type_label"))
				|| isDisplayed(By.id("view_partner_type_context"));
	}

	public boolean isOrganisationNameInViewPartnerPageDisplayed() {
		return isOrganisationNameInViewPageDisplayed();
	}

	public boolean isViewPartnerDetailsEmailLabelDisplayed() {
		return isDisplayed(By.id("view_partner_details_email_label"))
				|| isDisplayed(By.xpath("//*[contains(text(),'Email')]"));
	}

	public boolean isViewPartnerDetailsPolicyGroupLabelDisplayed() {
		return isDisplayed(By.id("view_partner_details_policy_group_label"))
				|| isDisplayed(By.xpath("//*[contains(text(),'Policy Group')]"));
	}

	public boolean isPartnerCertificateInViewPartnerDetailsDisplayed() {
		return isDisplayed(By.id("view_partner_details_partner_certificate_title"))
				|| isDownloadCertificateOptionDisplayed();
	}

	public boolean isDownloadCertificateButtonDisplayed() {
		return isDisplayed(By.id("download_partner_cer_btn"))
				|| isDisplayed(By.id("download_partner_certificate_btn"))
				|| isDownloadCertificateOptionDisplayed();
	}

	public boolean isExpiryDateTimeDisplayed() {
		return isDisplayed(By.id("view_expiry_date_label"))
				|| isDisplayed(By.id("view_expiry_date_context"))
				|| isDisplayed(By.xpath("//*[contains(text(),'Expiry')]"));
	}

	public boolean isTimeOfUploadDisplayed() {
		return isDisplayed(By.id("view_certificate_upload_date_label"))
				|| isDisplayed(By.id("view_certificate_upload_date_context"))
				|| isDisplayed(By.xpath("//*[contains(text(),'Upload')]"));
	}

	public boolean isGobackButtonInViewPatnerPageDisplayed() {
		return isDisplayed(By.id("ftm_view_back_btn"))
				|| isDisplayed(By.id("view_partner_details_back_btn"))
				|| isDisplayed(By.id("title_back_icon"));
	}

	public void clickOngobackButtonInPartnerDetailsPage() {
		By goBack = By.id("ftm_view_back_btn");
		if (isDisplayed(goBack)) {
			click(goBack);
		} else {
			clickOnViewPartnerGoBackButton();
		}
	}

	public boolean isPartnerPolicyLinkingTitleDisplayed() {
		return isDisplayed(By.id("page_title"))
				|| isDisplayed(By.id("request_policy_btn"))
				|| isDisplayed(By.id("partner_policy_linking_sub_title"))
				|| isDisplayed(By.xpath("//*[contains(text(),'Partner - Policy Linking')]"))
				|| isDisplayed(By.xpath("//*[contains(text(),'List of Partner-Policy Linkages')]"))
				|| isDisplayed(By.xpath("//*[contains(text(),'List of Partner Policy Linkages')]"));
	}

	public void clickOnRequestPolicyButton() {
		click(By.id("request_policy_btn"));
	}

	public boolean isRequestPolicyPartnerTypeDropdownDisplayed() {
		return isDisplayed(By.id("request_policy_partner_type_dropdown_btn"));
	}

	public void selectManualAdjudicationPartnerInRequestPolicyDropdown() {
		click(By.id("request_policy_partner_type_dropdown_btn"));
		click(By.xpath("//button[contains(@id,'request_policy_partner_type_option') and normalize-space()='"
				+ GlobalConstants.MANUAL_ADJUDICATION_PARTNER + "']"));
	}

	public void selectPartnerIDPartnerTypeDropdown(String partnerId) {
		click(By.id("request_policy_partner_id_dropdown_btn"));
		WebElement search = driver.findElement(By.id("request_policy_partner_id_search_input"));
		clearTextBox(search);
		enter(search, partnerId);
		By option = By.xpath("//button[contains(@id,'request_policy_partner_id_option') and normalize-space()='"
				+ partnerId + "']");
		if (isElementDisplayedQuick(option, Duration.ofSeconds(5))) {
			click(option);
		} else {
			click(By.xpath("//*[normalize-space()='" + partnerId + "']"));
		}
	}

	public boolean isPolicyNameDropdownDisplayed() {
		return isDisplayed(By.id("request_policies_policy_name_dropdown_btn"));
	}

	public void selectPolicyNamePartnerTypeDropdown(String policyName) {
		click(By.id("request_policies_policy_name_dropdown_btn"));
		WebElement search = driver.findElement(By.id("request_policies_policy_name_search_input"));
		clearTextBox(search);
		enter(search, policyName);
		By option = By.xpath("//button[contains(@id,'request_policies_policy_name_option') and normalize-space()='"
				+ policyName + "']");
		if (isElementDisplayedQuick(option, Duration.ofSeconds(5))) {
			click(option);
		} else {
			click(By.xpath("//*[normalize-space()='" + policyName + "']"));
		}
	}

	public void entercomments(String comment) {
		enter(driver.findElement(By.id("request_policy_comment_box")), comment);
	}

	public void clickOnRequestPolicySubmitButton() {
		click(By.id("request_policies_form_submit_btn"));
	}

	public void approvePolicyRequest() {
		// Admin Request Policy success screen: Approve → Confirm popup → policy requests list
		By confirmationApprove = By.id("confirmation_custom_btn");
		if (isElementDisplayedQuick(confirmationApprove, Duration.ofSeconds(12))) {
			click(confirmationApprove);
			By approvePopupSubmit = By.id("approve_popup_submit_btn");
			if (isElementDisplayedQuick(approvePopupSubmit, Duration.ofSeconds(10))) {
				click(approvePopupSubmit);
			}
			// Wait until navigated back to Partner - Policy Linking list
			isElementDisplayedQuick(By.id("request_policy_btn"), Duration.ofSeconds(15));
			return;
		}

		// Fallback: approve from Partner - Policy Linking list actions
		By action = By.id("partner_list_view1");
		if (isElementDisplayedQuick(action, Duration.ofSeconds(8))) {
			click(action);
		}
		By approveReject = By.id("partner_details_approve_or_reject_btn");
		if (isElementDisplayedQuick(approveReject, Duration.ofSeconds(8))) {
			click(approveReject);
		}
		By approve = By.id("approve_btn");
		if (isElementDisplayedQuick(approve, Duration.ofSeconds(8))) {
			click(approve);
		}
	}

	public void clickOnStatusFilter() {
		click(By.id("status_filter_dropdown_btn"));
	}

	public void clickActivatedButton() {
		By byId = By.id("status_filter_option1");
		By byText = By.xpath("//button[contains(@id,'status_filter_option') and normalize-space()='Active']");
		if (isElementDisplayedQuick(byText, Duration.ofSeconds(3))) {
			click(byText);
		} else {
			click(byId);
		}
	}

	public void clickOnDeActivatedStatusInFilters() {
		By byId = By.id("status_filter_option2");
		By byText = By.xpath("//button[contains(@id,'status_filter_option') and normalize-space()='Deactivated']");
		if (isElementDisplayedQuick(byText, Duration.ofSeconds(3))) {
			click(byText);
		} else {
			click(byId);
		}
	}

	public boolean isDeactivatePartnerHeaderDisplayed() {
		return isDisplayed(By.id("deactivate_popup_header"));
	}

	public boolean isDeactivatePartnerDescriptionDisplayed() {
		return isDisplayed(By.id("deactivate_popup_description"));
	}

	public boolean isDeactivateCancelButtonDisplayed() {
		return isDisplayed(By.id("deactivate_cancel_btn"));
	}

	public boolean isDeactivateConfirmButtonDisplayed() {
		return isDisplayed(By.id("deactivate_submit_btn"));
	}

	public void clickOnConfirmButton() {
		clickOnDeactivateConfirmButton();
	}

	public void waitForDeactivatePopupToClose() {
		By header = By.id("deactivate_popup_header");
		try {
			new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(12))
					.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(header));
		} catch (Exception ignored) {
			// popup already closed or not present
		}
	}

	public boolean isDeactivatedStatusOnPartnerRowDisplayed() {
		return isDisplayed(By.xpath("//td[normalize-space()='Deactivated']"))
				|| isDisplayed(By.xpath("//*[contains(text(),'Deactivated')]"));
	}

	public boolean isDeactivateButtonDisabled() {
		return isActionMenuItemDisabled(By.id("partner_deactive_btn"));
	}
}
