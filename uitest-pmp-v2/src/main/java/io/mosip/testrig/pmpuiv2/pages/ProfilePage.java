package io.mosip.testrig.pmpuiv2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProfilePage extends BasePage {

	@FindBy(id = "header_user_profile_info_btn")
	private WebElement userProfileButton;

	@FindBy(id = "header_user_profile_logout_btn")
	private WebElement logoutButton;

	@FindBy(xpath = "//h1[text()='My Profile'] | //h1[@id='page_title']")
	private WebElement titleOfCardView;

	@FindBy(xpath = "//P[text()='Profile Information']")
	private WebElement titleOfInformation;

	@FindBy(xpath = "//P[text()='First Name']")
	private WebElement firstNameLabel;

	@FindBy(xpath = "//P[text()='pmpui-nocert']")
	private WebElement firstNameContext;

	@FindBy(xpath = "//P[text()='Last Name']")
	private WebElement lastNameLabel;

	@FindBy(xpath = "//P[text()='  ']")
	private WebElement lastNameContext;

	@FindBy(xpath = "//P[text()='Organisation Name']")
	private WebElement organisationNameLabel;

	@FindBy(xpath = "//P[text()='AABBCC']")
	private WebElement organisationNameContext;

	@FindBy(xpath = "//P[text()='Address']")
	private WebElement addressLabel;

	@FindBy(xpath = "//P[text()='pmpui-auth']")
	private WebElement addressContext;

	@FindBy(xpath = "//P[text()='Partner Type']")
	private WebElement partnerTypeLabel;

	@FindBy(xpath = "//P[text()='Authentication Partner']")
	private WebElement partnerTypeContext;

	@FindBy(xpath = "//P[text()='Phone Number']")
	private WebElement phoneNumberLabel;

	@FindBy(xpath = "//P[text()='8098768903']")
	private WebElement phoneNumberContext;

	@FindBy(xpath = "//P[text()='Email Address']")
	private WebElement emailAddressLabel;

	@FindBy(xpath = "//P[text()='Authentication Partner']")
	private WebElement emailContext;

	@FindBy(xpath = "//P[text()='User Name']")
	private WebElement userNameLabel;

	@FindBy(xpath = "//P[text()='pmpui-nocert']")
	private WebElement userNameContext;

	@FindBy(xpath = "//p[normalize-space()='First Name' or normalize-space()='Prénom' or normalize-space()='الاسم الأول']/following-sibling::p[1]")
	private WebElement firstNameValue;

	@FindBy(xpath = "//p[normalize-space()='Last Name' or normalize-space()='Nom de famille' or normalize-space()='اسم العائلة']/following-sibling::p[1]")
	private WebElement lastNameValue;

	@FindBy(xpath = "//p[normalize-space()='Partner Type' or normalize-space()='Type de partenaire' or normalize-space()='نوع الشريك']/following-sibling::p[1]")
	private WebElement partnerTypeValue;

	@FindBy(xpath = "//p[normalize-space()='Organisation Name' or normalize-space()='Organization Name' or normalize-space()=\"Nom de l'organisme\" or normalize-space()='اسم المنظمة']/following-sibling::p[1]")
	private WebElement organisationNameValue;

	@FindBy(xpath = "//p[normalize-space()='User Name' or normalize-space()=\"Nom d'utilisateur\" or normalize-space()='اسم المستخدم']/following-sibling::p[1]")
	private WebElement userNameValue;

	@FindBy(xpath = "//p[normalize-space()='Address' or normalize-space()='Adresse' or normalize-space()='عنوان']/following-sibling::p[1]")
	private WebElement addressValue;

	@FindBy(xpath = "//p[normalize-space()='Phone Number' or normalize-space()='Numéro de téléphone' or normalize-space()='رقم التليفون']/following-sibling::p[1]")
	private WebElement phoneNumberValue;

	@FindBy(xpath = "//p[normalize-space()='Email Address' or normalize-space()='Adresse e-mail' or normalize-space()='عنوان البريد الإلكتروني']/following-sibling::p[1]")
	private WebElement emailAddressValue;

	@FindBy(id = "page_title")
	private WebElement pageTitle;

	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;

	public ProfilePage(WebDriver driver) {
		super(driver);
	}

	public void clickOnUserProfileButton() {
		clickOnElement(userProfileButton);
	}

	public boolean isTitleOfCardViewDisplayed() {
		return isElementDisplayed(pageTitle) || isElementDisplayed(titleOfCardView);
	}

	public String getPageTitleText() {
		return getTextFromLocator(pageTitle).trim();
	}

	public boolean isProfileLabelDisplayed(String labelText) {
		return getElementCount(By.xpath("//p[normalize-space()=\"" + labelText + "\"]")) > 0;
	}

	public boolean isTitleOfInformationDisplayed() {
		return isElementDisplayed(titleOfInformation);
	}

	public boolean isFirstNameLabelDisplayed() {
		return isElementDisplayed(firstNameLabel);
	}

	public boolean isFirstNameContextDisplayed() {
		return isElementDisplayed(firstNameContext);
	}

	public boolean isLastNameLabelDisplayed() {
		return isElementDisplayed(lastNameLabel);
	}

	public boolean isLastNameContextDisplayed() {
		return isElementDisplayed(lastNameContext);
	}

	public boolean isOrganisationNameLabelDisplayed() {
		return isElementDisplayed(organisationNameLabel);
	}

	public boolean isOrganisationNameContextDisplayed() {
		return isElementDisplayed(organisationNameContext);
	}

	public boolean isAddressLabelDisplayed() {
		return isElementDisplayed(addressLabel);
	}

	public boolean isAddressContextDisplayed() {
		return isElementDisplayed(addressContext);
	}

	public boolean isPartnerTypeLabelDisplayed() {
		return isElementDisplayed(partnerTypeLabel);
	}

	public boolean isPartnerTypeContextDisplayed() {
		return isElementDisplayed(partnerTypeContext);
	}

	public boolean isPhoneNumberLabelDisplayed() {
		return isElementDisplayed(phoneNumberLabel);
	}

	public boolean isPhoneNumberContextDisplayed() {
		return isElementDisplayed(phoneNumberContext);
	}

	public boolean isEmailAddressLabelDisplayed() {
		return isElementDisplayed(emailAddressLabel);
	}

	public boolean isEmailContextDisplayed() {
		return isElementDisplayed(emailContext);
	}

	public boolean isUserNameLabelDisplayed() {
		return isElementDisplayed(userNameLabel);
	}

	public boolean isUserNameContextDisplayed() {
		return isElementDisplayed(userNameContext);
	}

	public void clickOnPhoneNumber() {
		clickOnElement(phoneNumberContext);
	}

	public boolean isPhoneNumberClickable() {
		return isElementDisplayed(phoneNumberContext);
	}

	public void clickOnTitleBackIcon() {
		clickOnElement(titleBackIcon);
	}

	public String getFirstNameValue() {
		return getTextFromLocator(firstNameValue).trim();
	}

	public String getLastNameValue() {
		return getTextFromLocator(lastNameValue).trim();
	}

	public String getPartnerTypeValue() {
		return getTextFromLocator(partnerTypeValue).trim();
	}

	public String getOrganisationNameValue() {
		return getTextFromLocator(organisationNameValue).trim();
	}

	public String getUserNameValue() {
		return getTextFromLocator(userNameValue).trim();
	}

	public String getAddressValue() {
		return getTextFromLocator(addressValue).trim();
	}

	public String getPhoneNumberValue() {
		return getTextFromLocator(phoneNumberValue).trim();
	}

	public String getEmailAddressValue() {
		return getTextFromLocator(emailAddressValue).trim();
	}

	public void clickOnLogoutButton() {
		clickOnElement(logoutButton);
	}

}
