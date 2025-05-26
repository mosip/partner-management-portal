package io.mosip.testrig.pmpv2ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProfilePage extends BasePage {

	
	@FindBy(id = "header_user_profile_info_btn")
	private WebElement userProfileButton;
	
	@FindBy(id = "header_user_profile_logout_btn")
	private WebElement logoutButton;
	
	@FindBy(xpath = "//h1[text()='My Profile']")
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

	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;
	
	public ProfilePage(WebDriver driver) {
		super(driver);	
	}
	
	public void clickOnUserProfileButton() {
		clickOnElement(userProfileButton);
	}
	
	public boolean isTitleOfCardViewDisplayed() {
		return isElementDisplayed(titleOfCardView);
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

}
