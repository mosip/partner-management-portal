package io.mosip.testrig.pmpuiv2.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NotificationPage extends BasePage {

	@FindBy(xpath = "//h1[text()='Notifications']")
	private WebElement notifications;

	@FindBy(xpath = "//p[text()='Latest']")
	private WebElement notificationPanelSubtitle;

	@FindBy(xpath = "(//p[contains(@class, 'text-xs') and contains(@class, 'text-gray-500') and contains(@class, 'text-right')])[1]")
	private WebElement dateTimeOfCertificates;

	@FindBy(xpath = "(//p[contains(@class, 'font-semibold') and contains(@class, 'text-left')])[1]")
	private WebElement descriptionOfNotifications;

	@FindBy(xpath = "(//button[text()='Dismiss'])[1]")
	private WebElement dismissButton;

	@FindBy(xpath = "//p[text()='View all notifications']")
	private WebElement viewAllNotificationsButton;

	@FindBy(xpath = "//h6[text()='Root CA Certificate']")
	private WebElement rootCACertificateTab;

	@FindBy(xpath = "//h6[text()='Intermediate CA Certificate']")
	private WebElement intermediateCACertificateTab;

	@FindBy(xpath = "//h6[text()='Weekly Summary - Partner']")
	private WebElement weeklySummaryTab;

	@FindBy(id = "sub_title_home_btn")
	private WebElement breadcumb;

	@FindBy(xpath = "//h6[contains(text(), 'List of Notifications')]")
	private WebElement subtitle;

	@FindBy(id = "filter_btn")
	private WebElement filterButton;

	@FindBy(id = "cert_id_filter")
	private WebElement certIdFilterTextbox;

	@FindBy(id = "cert_issued_by_domain_filter")
	private WebElement certIssuedByFilterTextbox;

	@FindBy(id = "cert_issued_to_filter")
	private WebElement certIssuedToFilterTextbox;

	@FindBy(id = "view_notifications_expiry_date_calender")
	private WebElement certExpiryDateFilterTextbox;

	@FindBy(id = "cert_partner_domain_filter")
	private WebElement certPartnerDomainFilterDropdown;

	@FindBy(id = "filter_reset_btn")
	private WebElement resetFilterBtn;

	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilterBtn;

	@FindBy(id = "cert_partner_domain_filter_option1")
	private WebElement partnerDomainAuth;

	@FindBy(id = "cert_partner_domain_filter_option2")
	private WebElement partnerDomainDevice;

	@FindBy(id = "cert_partner_domain_filter_option3")
	private WebElement partnerDomainFTM;

	@FindBy(xpath = "//p[text()='Certificate ID']")
	private WebElement certificateIdLabel;

	@FindBy(xpath = "//p[text()='Issued To']")
	private WebElement issuedToLabel;

	@FindBy(xpath = "//p[text()='Issued By']")
	private WebElement issuedByLabel;

	@FindBy(xpath = "//p[text()='Partner Domain']")
	private WebElement partnerDomainLabel;

	@FindBy(xpath = "//p[text()='Certificate Expiry Date']")
	private WebElement certificateExpiryDateLabel;

	@FindBy(xpath = "//input[@placeholder='Search Certificate ID']")
	private WebElement certificateIdPlaceHolder;

	@FindBy(xpath = "//input[@placeholder='Search Issued By']")
	private WebElement issuedByPlaceHolder;

	@FindBy(xpath = "//input[@placeholder='Search Issued To']")
	private WebElement issuedToPlaceHolder;

	@FindBy(xpath = "//input[@placeholder='Search Certificate Expiry Date']")
	private WebElement certificateExpiryDatePlaceHolder;

	@FindBy(xpath = "//span[text()='Select Partner Domain']")
	private WebElement partnerDomainPlaceHolder;

	@FindBy(xpath = "//div[contains(@class, 'react-datepicker')]")
	private WebElement calendarContainer;

	@FindBy(xpath = "//p[text()='No Notifications Found']")
	private WebElement noNotificationsFound;

	@FindBy(xpath = "//button[.='x']")
	private WebElement textBoxCloseButton;

	@FindBy(xpath = "(//p[text()='Root CA Certificate Expiration'])[1]")
	private WebElement titleOfExpiryRootCACertificate;

	@FindBy(xpath = "(//p[text()='Intermediate CA Certificate Expiration'])[1]")
	private WebElement titleOfExpiryIntCACertificate;

	@FindBy(xpath = "//p[contains(@class, 'text-xs') and contains(@class, 'text-gray-500') and contains(@class, 'text-right')]")
	private WebElement expiryCACertDateAndTime;

	@FindBy(xpath = "//img[@id='featuredIcon']")
	private WebElement featuredIcon;

	@FindBy(id = "pagination_card")
	private WebElement pagination;

	@FindBy(xpath = "//a[@aria-label='Previous page']")
	private WebElement previousPage;

	@FindBy(xpath = "//a[@aria-label='Next page']")
	private WebElement nextPage;

	@FindBy(id = "expand_more_FILL0_wght400_GRAD0_opsz48")
	private WebElement expandIcon;

	@FindBy(id = "pagination_select_record_per_page")
	private WebElement recordPerPage;

	@FindBy(xpath = "//h6[text()='Items per page']")
	private WebElement prefixOfPage;

	@FindBy(xpath = "(//button[normalize-space()='Dismiss'])[1]")
	private WebElement firstDismissButton;

	@FindBy(id = "created_from_date_calender")
	private WebElement creationDateFromTextbox;

	@FindBy(id = "created_to_date_calender")
	private WebElement creationDateToTextbox;

	@FindBy(xpath = "//p[text()='Notification Creation Date From']")
	private WebElement creationFromDateFilterLabel;

	@FindBy(xpath = "//p[text()='Notification Creation Date To']")
	private WebElement creationToDateFilterLabel;

	@FindBy(xpath = "//input[@placeholder='Search Creation Date From']")
	private WebElement creationFromDatePlaceHolder;

	@FindBy(xpath = "//input[@placeholder='Search Creation Date To']")
	private WebElement creationToDatePlaceHolder;

	public NotificationPage(WebDriver driver) {
		super(driver);
	}

	public boolean isNotificationPanelDisplayed() {
		return isElementDisplayed(notifications);
	}

	public boolean isNotificationPanelTitleDisplayed() {
		return isElementDisplayed(notifications);
	}

	public boolean isNotificationPanelSubtitleDisplayed() {
		return isElementDisplayed(notificationPanelSubtitle);
	}

	public boolean isDateTimeInNotificationPanelDisplayed() {
		return isElementDisplayed(dateTimeOfCertificates);
	}

	public boolean isDescriptionOfNotificationsDisplayed() {
		return isElementDisplayed(descriptionOfNotifications);
	}

	public void clickOnDismissButton() {
		clickOnElement(dismissButton);
	}

	public void clickOnViewAllNotificationsButton() {
		clickOnElement(viewAllNotificationsButton);
	}

	public boolean isRootCACertificateTabDisplayed() {
		return isElementDisplayed(rootCACertificateTab);
	}

	public boolean isViewAllNotificationsTitleDisplayed() {
		return isElementDisplayed(notifications);
	}

	public boolean isIntermediateCACertificateTabDisplayed() {
		return isElementDisplayed(intermediateCACertificateTab);
	}

	public boolean isWeeklySummaryTabDisplayed() {
		return isElementDisplayed(weeklySummaryTab);
	}

	public boolean isBreadcumbDisplayed() {
		return isElementDisplayed(breadcumb);
	}

	public boolean isViewAllNotificationsSubtitleDisplayed() {
		return isElementDisplayed(subtitle);
	}

	public boolean isFilterButtonEnabled() {
		return isElementEnabled(filterButton);
	}

	public void clickOnFilterButton() {
		clickOnElement(filterButton);
	}

	public boolean isCertIdFilterTextBoxDisplayed() {
		return isElementDisplayed(certIdFilterTextbox);
	}

	public boolean isCertIssuedByFilterTextBoxDisplayed() {
		return isElementDisplayed(certIssuedByFilterTextbox);
	}

	public boolean isCertIssuedToFilterTextBoxDisplayed() {
		return isElementDisplayed(certIssuedToFilterTextbox);
	}

	public boolean isCertExpiryDateFilterTextBoxDisplayed() {
		return isElementDisplayed(certExpiryDateFilterTextbox);
	}

	public boolean isCertPartnerDomainFilterDropdownDisplayed() {
		return isElementDisplayed(certPartnerDomainFilterDropdown);
	}

	public void clickOnPartnerDomainFilterDropdown() {
		clickOnElement(certPartnerDomainFilterDropdown);
	}

	public boolean isPartnerDomainAuthInDropdownDisplayed() {
		return isElementDisplayed(partnerDomainAuth);
	}

	public boolean isPartnerDomainDeviceInDropdownDisplayed() {
		return isElementDisplayed(partnerDomainDevice);
	}

	public boolean isPartnerDomainFTMInDropdownDisplayed() {
		return isElementDisplayed(partnerDomainFTM);
	}

	public boolean isResetFilterButtonEnabled() {
		return isElementEnabled(resetFilterBtn);
	}

	public boolean isApplyFilterButtonEnabled() {
		return isElementEnabled(applyFilterBtn);
	}

	public boolean isCertificateIdLabelDisplayed() {
		return isElementDisplayed(certificateIdLabel);
	}

	public boolean isIssuedToLabelDisplayed() {
		return isElementDisplayed(issuedToLabel);
	}

	public boolean isIssuedByLabelDisplayed() {
		return isElementDisplayed(issuedByLabel);
	}

	public boolean isPartnerDomainLabelDisplayed() {
		return isElementDisplayed(partnerDomainLabel);
	}

	public boolean isCertificateExpiryDateLabelDisplayed() {
		return isElementDisplayed(certificateExpiryDateLabel);
	}

	public boolean isCertificateIdPlaceHolderDisplayed() {
		return isElementDisplayed(certificateIdPlaceHolder);
	}

	public boolean isIssuedByPlaceHolderDisplayed() {
		return isElementDisplayed(issuedByPlaceHolder);
	}

	public boolean isIssuedToPlaceHolderDisplayed() {
		return isElementDisplayed(issuedToPlaceHolder);
	}

	public boolean isCertificateExpiryDatePlaceHolderDisplayed() {
		return isElementDisplayed(certificateExpiryDatePlaceHolder);
	}

	public boolean isPartnerDomainPlaceHolderDisplayed() {
		return isElementDisplayed(partnerDomainPlaceHolder);
	}

	public void clickOnCertExpiryDateFilterTextbox() {
		clickOnElement(certExpiryDateFilterTextbox);
	}

	public boolean isDatePickerFormatDisplayed() {
		return isElementDisplayed(calendarContainer);
	}

	public void enterInvalidExpiryDateInFilter(String value) {
		enter(certExpiryDateFilterTextbox,value);
	}

	public void clickOnApplyFilterButton() {
		clickOnElement(applyFilterBtn);
	}

	public boolean isNoNotificationsFoundDisplayed() {
		return isElementDisplayed(noNotificationsFound);
	}

	public void selectPartnerDomainAsAuthInFilter() {
		clickOnElement(certPartnerDomainFilterDropdown);
		clickOnElement(partnerDomainAuth);
	}

	public void clickOnResetFilterButton() {
		clickOnElement(resetFilterBtn);
	}

	public void enterCertIssuedToInFilter(String value) {
		enter(certIssuedToFilterTextbox,value);
	}

	public void enterCertIssuedByInFilter(String value) {
		enter(certIssuedByFilterTextbox,value);
	}

	public void clickOnTextBoxCloseButton() {
		clickOnElement(textBoxCloseButton);
	}

	public boolean isTitleOfExpiryRootCACertificateDisplayed() {
		return isElementDisplayed(titleOfExpiryRootCACertificate);
	}

	public boolean isTitleOfExpiryIntCACertificateDisplayed() {
		return isElementDisplayed(titleOfExpiryIntCACertificate);
	}

	public boolean isExpiryCACertDateAndTimeDisplayed() {
		return isElementDisplayed(expiryCACertDateAndTime);
	}

	public boolean isFeaturedIconDisplayed() {
		return isElementDisplayed(featuredIcon);
	}

	public boolean isPaginationDisplayed() {
		return isElementDisplayed(pagination);
	}

	public boolean isPreviusPageButtonDisplayed() {
		return isElementDisplayed(previousPage);
	}

	public boolean isNextPageButtonDisplayed() {
		return isElementDisplayed(nextPage);
	}

	public boolean isPrefixOfPageDisplayed() {
		return isElementDisplayed(prefixOfPage);
	}

	public boolean isRecordPerPageDisplayed() {
		return isElementDisplayed(recordPerPage);
	}

	public boolean isexpandIconDisplayed() {
		return isElementDisplayed(expandIcon);
	}

	public void clickOnCACertDismissButton() {
		clickOnElement(firstDismissButton);
	}

	public void clickOnIntermediateCACertificateTab() {
		clickOnElement(intermediateCACertificateTab);
	}

	public void clickOnWeeklySummaryTab() {
		clickOnElement(weeklySummaryTab);
	}

	public boolean isCreationDateFromTextboxDisplayed() {
		return isElementDisplayed(creationDateFromTextbox);
	}

	public boolean isCreationDateToTextboxDisplayed() {
		return isElementDisplayed(creationDateToTextbox);
	}

	public void enterCreationFromDateInFilter(String value) {
		enter(creationDateFromTextbox,value);
	}

	public void enterCreationToDateInFilter(String value) {
		enter(creationDateToTextbox,value);
	}

	public boolean isCreationFromDateFilterLabelDisplayed() {
		return isElementDisplayed(creationFromDateFilterLabel);
	}

	public boolean isCreationToDateFilterLabelDisplayed() {
		return isElementDisplayed(creationToDateFilterLabel);
	}

	public boolean isCreationFromDatePlaceHolderDisplayed() {
		return isElementDisplayed(creationFromDatePlaceHolder);
	}

	public boolean isCreationToDatePlaceHolderDisplayed() {
		return isElementDisplayed(creationToDatePlaceHolder);
	}
}