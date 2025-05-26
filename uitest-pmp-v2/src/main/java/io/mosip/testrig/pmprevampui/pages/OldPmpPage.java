package io.mosip.testrig.pmpv2ui.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OldPmpPage extends BasePage {

	@FindBy(xpath = "//*[@class='menu-button mat-menu-trigger']")
	private WebElement UserProfile;

	@FindBy(xpath = "//*[contains(text(), 'Logout')]")
	private WebElement LogoutButton;

	@FindBy(id = "username")
	private WebElement usenameTextBox;

	@FindBy(id = "password")
	private WebElement PasswordTextBox;

	@FindBy(xpath = "//input[@name='login']")
	private WebElement LoginButton;

	@FindBy(id = "policymenugroup")
	private WebElement PolicyMenuGroup;

	@FindBy(xpath = "//a[@href='#/pmp/resources/policygroup/view']")
	private WebElement PolicyMenuGroupTab;

	@FindBy(xpath = "//button[@id='Create Policy Group']")
	private WebElement CreatePolicyGroup;

	@FindBy(id = "name")
	private WebElement nameFormPolicyGroup;

	@FindBy(id = "desc")
	private WebElement descFormPolicyGroup;

	@FindBy(xpath = "//button[@id='createButton']")
	private WebElement CreatButton;

	@FindBy(xpath = "//button[@id='confirmmessagepopup']")
	private WebElement ConfirmMessagePopup;

	@FindBy(xpath = "//*[contains(text(), 'Policy')]")
	private WebElement PolicyTab;

	@FindBy(xpath = "//*[contains(text(), 'Auth Policy')]")
	private WebElement AuthPolicyTab;

	@FindBy(xpath = "//*[contains(text(), 'Create Policy')]")
	private WebElement CreatePolicyTab;

	@FindBy(xpath = "//*[@placeholder='Name']")
	private WebElement NameTextBox;

	@FindBy(xpath = "//*[@placeholder='Description']")
	private WebElement DescriptionTextBox;

	@FindBy(xpath = "//*[@id='policyGroupName']")
	private WebElement policyGroupNameDropDown;

	@FindBy(id = "policies")
	private WebElement policiesTextBox;

	@FindBy(xpath = "//*[contains(text(), 'Filter')]")
	private WebElement filterButton;

	@FindBy(id = "applyTxt")
	private WebElement ApplyTextButton;

	@FindBy(xpath = "(//span[@class='mat-button-wrapper']/mat-icon)[2]")
	private WebElement PolicyGroupElipsis;

	@FindBy(xpath = "//*[contains(text(), 'Activate')]")
	private WebElement ActivateButton;

	@FindBy(xpath = "//*[contains(text(), ' Yes ')]")
	private WebElement YesButton;

	@FindBy(id = "/pmp/resources/policymapping/view")
	private WebElement partnerPolicyMappingTab;

	@FindBy(id = "Filter")
	private WebElement FilterButton;

	@FindBy(id = "partnerName")
	private WebElement partnerName;

	@FindBy(id = "requestDetail")
	private WebElement requestDetail;

	@FindBy(id = "applyTxt")
	private WebElement applyTxt;

	@FindBy(id = "ellipsis-button0")
	private WebElement ellipsisButton0;

	@FindBy(id = "Manage Policy0")
	private WebElement ManagePolicy0;

	@FindBy(id = "confirmpopup")
	private WebElement confirmpopup;

	public OldPmpPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnUserProfile() {
		clickOnElement(UserProfile);
	}

	public void clickOnLogOut() {
		clickOnElement(LogoutButton);
	}

	public void EnterUserName(String value) {
		enter(usenameTextBox, value);
	}

	public void EntePasswordTextBox(String value) {
		enter(PasswordTextBox, value);
	}

	public void clickOnLoginButton() {
		clickOnElement(LoginButton);
	}

	public void clickOnPolicyMenuGroup() {
		clickOnElement(PolicyMenuGroup);
	}

	public void clickOnPolicyMenuGroupTab() {
		clickOnElement(PolicyMenuGroupTab);
	}

	public void clickOnCreatePolicyGroup() {
		clickOnElement(CreatePolicyGroup);
	}

	public void EnterName() {
		enter(nameFormPolicyGroup, "");
	}

	public void EnterDescription() {
		enter(descFormPolicyGroup, "");
	}

	public void clickOnCreateButton() {
		clickOnElement(CreatButton);
	}

	public void clickOnConfirmMessagePopup() {
		clickOnElement(ConfirmMessagePopup);
	}

	public void clickOnPolicyTab() {
		clickOnElement(PolicyTab);
	}

	public void clickOnAuthPolicyTab() {
		clickOnElement(AuthPolicyTab);
	}

	public void clickOnCreatePolicyTab() {
		clickOnElement(CreatePolicyTab);
	}

	public void EnterNameTextBox(String value) {
		enter(NameTextBox, value);
	}

	public void EnterDescriptionTextBox(String value) {
		enter(DescriptionTextBox, value);
	}

	public void policyGroupNameDropDown(String value) {
		try {
			dropdown(policyGroupNameDropDown, value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void EnterPoliciesTextBox(String value) {
		enter(policiesTextBox, value);
	}

	public void clickOnfilterButton() {
		clickOnElement(filterButton);
	}

	public void clickOnApplyTextButton() {
		clickOnElement(ApplyTextButton);
	}

	public void clickOnPolicyGroupElipsis() {
		clickOnElement(PolicyGroupElipsis);
	}

	public void clickOnActivateButton() {
		clickOnElement(ActivateButton);
	}

	public void clickOnYesButton() {
		clickOnElement(YesButton);
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public void clickOnPartnerPolicyMappingTab() {
		clickOnElement(partnerPolicyMappingTab);
	}

	public void EnterPartnerNameTextBox(String value) {
		enter(partnerName, value);
	}

	public void EnterRequestDetail(String value) {
		enter(requestDetail, value);
	}

	public void clickOnEllipsisButton() {
		clickOnElement(ellipsisButton0);
	}

	public void clickOnManagePolicy() {
		clickOnElement(ManagePolicy0);
	}

	public void clickOnConfirmpopup() {
		clickOnElement(confirmpopup);
	}

}
