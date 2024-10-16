package io.mosip.testrig.pmprevampui.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PoliciesPage extends BasePage{	
	
	@FindBy(id = "title_back_icon")
	private WebElement policiesTitle;
	
	@FindBy(xpath = "//div[@class='flex flex-col items-center']")
	private WebElement policiesEmptyTable;
	
	@FindBy(id = "policies_request_policy_btn")
	private WebElement requestPolicyButton;
	
	@FindBy(id = "policies_request_btn")
	private WebElement policies_request_btn;
	
	
	@FindBy(id = "request_policy_partner_id")
	private WebElement partnerIdDropdown;
	
	@FindBy(id = "request_policies_policy_name")
	private WebElement policyNameDropdown;
	
	@FindBy(id = "request_policies_policy_name_search_input")
	private WebElement searchBoxForPolicyName;
	
	@FindBy(id = "request_policy_comment_box")
	private WebElement commentsTextBox;
	
	@FindBy(id = "request_policies_form_submit_btn")
	private WebElement submitButton;
	
	@FindBy(xpath = "//*[text()='Policy Submitted Successfully!']")
	private WebElement policySubmittedSuccessfully;
	
	@FindBy(xpath = "//*[text()='No Data Available.']")
	private WebElement noDataAvailableText;
	
	@FindBy(id = "request_policies_policy_name_option1")
	private WebElement requestPolicyNameOption;
	
	@FindBy(id = "sub_title_home_btn")
	private WebElement HomeButton;
	
	@FindBy(id = "sub_title_btn")
	private WebElement PolicyButton;
	
//
//	
//	
//	
	
	
	
	@FindBy(xpath = "//*[text()='List of Policy Requests (1)']")
	private WebElement ListOfPolicyRequested;
	
	@FindBy(xpath = "//*[text()='Pending For Approval']")
	private WebElement PendingForApproval;
	
	@FindBy(id = "policy_list_view1")
	private WebElement ElipcisButton;
	
	@FindBy(id = "policy_list_view_card")
	private WebElement CardViewButton;
	
	@FindBy(id = "view_policy_back_btn")
	private WebElement BackButton;
	
	@FindBy(id = "policy_partner_id_filter")
	private WebElement policyPartnerIdFilter;
	
	@FindBy(id = "policy_partner_type_filter")
	private WebElement policyPartnerTypeFilter;
	
	@FindBy(id = "policy_partner_type_filter_option1")
	private WebElement policyPartnerTypeFilterOption1;
	
	@FindBy(id = "policy_status_filter_option1")
	private WebElement policy_status_filter_option1;
	
	
	
	@FindBy(id = "policy_group_filter")
	private WebElement policyGroupFilter;
	
	@FindBy(id = "policy_name_filter")
	private WebElement policyNameFilter;
	
	@FindBy(id = "policy_status_filter")
	private WebElement policyStatusFilter;
	
	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;
	
	@FindBy(id = "policy_partner_id_filter_option1")
	private WebElement policyPartnerIdFilterOption1;
	
	@FindBy(id = "filter_btn")
	private WebElement filterButton;
	
	@FindBy(xpath = "//*[@aria-label='Next page']")
	private WebElement nextPage;
	
	@FindBy(xpath = "//*[@aria-label='Previous page']")
	private WebElement previousPage;

	@FindBy(xpath = "//*[text()='Partner ID']")
	private WebElement PartnerIdText;
	
	
	
	@FindBy(id = "policy_list_item1")
	private WebElement policyListItem1;
	
	@FindBy(id = "sub_title_home_btn")
	private WebElement subTitleHomeButton;
	
	@FindBy(id = "sub_title_btn")
	private WebElement subTitleButton;
	
	@FindBy(id = "title_back_icon")
	private WebElement titleBackIcon;
	
	@FindBy(xpath = "//*[text()='View Policy Details']")
	private WebElement ViewPolicyDetailsText;
	
	@FindBy(id = "partnerId_desc_icon")
	private WebElement partnerId_desc_icon;
	
	@FindBy(id = "partnerId_asc_icon")
	private WebElement partnerId_asc_icon;
	
	@FindBy(id = "partnerType_desc_icon")
	private WebElement partnerType_desc_icon;
	
	@FindBy(id = "partnerType_asc_icon")
	private WebElement partnerType_asc_icon;
	
	@FindBy(id = "policyGroupName_desc_icon")
	private WebElement policyGroupName_desc_icon;
	
	@FindBy(id = "policyGroupName_asc_icon")
	private WebElement policyGroupName_asc_icon;
	
	@FindBy(id = "policyName_desc_icon")
	private WebElement policyName_desc_icon;
	
	@FindBy(id = "policyName_asc_icon")
	private WebElement policyName_asc_icon;
	
	@FindBy(id = "createdDateTime_desc_icon")
	private WebElement createdDateTime_desc_icon;
	
	@FindBy(id = "createdDateTime_asc_icon")
	private WebElement createdDateTime_asc_icon;
	
	@FindBy(id = "status_desc_icon")
	private WebElement status_desc_icon;
	
	@FindBy(id = "status_asc_icon")
	private WebElement status_asc_icon;
	
	

	
//	
//	
//	
//	
//	
//	
//	
//	
//	
	
	
	
	public PoliciesPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPoliciesPageDisplayed() {
		return isElementDisplayed(policiesTitle);
	}
	
	public  void clickOnRequestPolicyButton() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clickOnElement(requestPolicyButton);
	}
	
	public  void clickOnRequestPolicyButtonWithFilter() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clickOnElement(policies_request_btn);
	}
	
	public  void selectPartnerIdDropdown(String value) {
		clickOnElement(partnerIdDropdown);
		clickOnElement(driver.findElement(By.xpath("(//*[contains(text(),'"+value+"')])[2]")));
	}
	
	public  void selectPolicyNameDropdown(String value) {
		clickOnElement(policyNameDropdown);
//		enter(SearchBox,value);
		clickOnElement(requestPolicyNameOption)
;		
//		
//		String val="'"+value +"'";
//		clickOnElement(driver.findElement(By.xpath("(//*[contains(text(),'"+value+"')])[2]")));
	}
	
	public  void selectInvalidPolicyNameDropdown(String value) {
		clickOnElement(policyNameDropdown);
		enter(searchBoxForPolicyName,value);
	}
	
	public void enterComments(String comments) {
		enter(commentsTextBox, comments);
	}
	
	public boolean isPartnerIdDropdownDisplayed() {
		return isElementDisplayed(partnerIdDropdown);
	}
	
	public boolean isPolicyNameDropdownDisplayed() {
		return isElementDisplayed(policyNameDropdown);
	}
	
	public boolean isSubmitButtonDisplayed() {
		return isElementDisplayed(submitButton);
	}
	
	public  void clickSubmitButton() {
		clickOnElement(submitButton);
	}
	
	public boolean isPolicySubmittedSuccessfullyDisplayed() {
		return isElementDisplayed(policySubmittedSuccessfully);
	}
	
	public boolean isNoDataAvailableTextDisplayed() {
		return isElementDisplayed(noDataAvailableText);
	}
	
	
	public  void searchInPolicyName(String value) {
		enter(searchBoxForPolicyName,value);
	}
	
	public boolean isPoliciesEmptyTableDisplayed() {
		return isElementDisplayed(policiesEmptyTable);
	}
	
	public boolean isPoliciesEmptyTableEnabled() {
		return isElementEnabled(requestPolicyButton);
	}
	
	public  void clickOnHomeButton() {
		clickOnElement(HomeButton);
	}
	
	public  void clickOnPartnerIdDropdown() {
		clickOnElement(partnerIdDropdown);
	}
//	
//	
//	
	
	
	public boolean isListOfPolicyRequestedDisplayed() {
		return isElementDisplayed(ListOfPolicyRequested);
	}
	
	public boolean isPendingForApprovalTextDisplayed() {
		return isElementDisplayed(PendingForApproval);
	}
	
	public  void clickOnElipcisButton() {
		clickOnElement(ElipcisButton);
	}
	
	public boolean isCardViewButtonDisplayed() {
		return isElementDisplayed(CardViewButton);
	}
	
	public  void clickOnCardViewButton() {
		clickOnElement(CardViewButton);
	}
	
	public  void clickOnBackButton() {
		clickOnElement(BackButton);
	}
	
	public  void clickOnPolicyPartnerIdFilter(String value) {
			clickOnElement(policyPartnerIdFilter);
			clickOnElement(driver.findElement(By.xpath("(//*[contains(text(),'"+value+"')])[2]")));
	}
	
	public  void clickOnPolicyPartnerTypeFilter() {
		clickOnElement(policyPartnerTypeFilter);
		clickOnElement(policyPartnerTypeFilterOption1);
	}	
	
	public  void clickOnPolicyGroupFilter(String value) {
		clickOnElement(policyGroupFilter);
		clickOnElement(driver.findElement(By.xpath("(//*[contains(text(),'"+value+"')])[3]")));
	}	
	
	public  void clickOnPolicyNameFilter(String value) {
		clickOnElement(policyNameFilter);
		clickOnElement(driver.findElement(By.xpath("(//*[contains(text(),'"+value+"')])[3]")));
	}	
	
	public  void clickOnPolicyStatusFilter() {
		clickOnElement(policyStatusFilter);
		clickOnElement(policy_status_filter_option1);
	}
	
	public  void clickOnFilterResetButton() {
		clickOnElement(filterResetButton);
	}
	
	public  void clickOnFilterButton() {
		clickOnElement(filterButton);
	}
	
	
	public boolean isNextPageDisplayed() {
		return isElementDisplayed(nextPage);
	}
	
	public boolean isPreviousPageDisplayed() {
		return isElementDisplayed(previousPage);
	}
	
	public boolean isPartnerIdTextDisplayed() {
		return isElementDisplayed(PartnerIdText);
	}
	
	public boolean isPartnerIdDescIconDisplayed() {
		return isElementDisplayed(partnerId_desc_icon);
	}
	
	public boolean isPartnerIdAscIconDisplayed() {
		return isElementDisplayed(partnerId_asc_icon);
	}
	
	public boolean isPartnerTypeDescIconDisplayed() {
		return isElementDisplayed(partnerType_desc_icon);
	}
	
	public boolean isPartnerTypeAscIconDisplayed() {
		return isElementDisplayed(partnerType_asc_icon);
	}
	
	public boolean isPolicyGroupNameDescIconDisplayed() {
		return isElementDisplayed(policyGroupName_desc_icon);
	}
	
	public boolean isPolicyGroupNameAscIconDisplayed() {
		return isElementDisplayed(policyGroupName_asc_icon);
	}
	
	public boolean isPolicyNameDescIconDisplayed() {
		return isElementDisplayed(policyName_desc_icon);
	}
	
	public boolean isPolicyNameAscIconDisplayed() {
		return isElementDisplayed(policyName_asc_icon);
	}
	
	public boolean isCreatedDateTimeDescISconDisplayed() {
		return isElementDisplayed(createdDateTime_desc_icon);
	}
	
	public boolean isCreatedDateTimeAscIconDisplayed() {
		return isElementDisplayed(createdDateTime_asc_icon);
	}
	
	public boolean isStatusDescIconDisplayed() {
		return isElementDisplayed(status_desc_icon);
	}
	
	public boolean isStatusAscIconDisplayed() {
		return isElementDisplayed(status_asc_icon);
	}
	
	public boolean isFilterButtonButtonEnabled() {
		return isElementEnabled(filterButton);
	}
	
	public boolean isSubTitleHomeButtonDisplayed() {
		return isElementDisplayed(subTitleHomeButton);
	}
	
	public boolean isSubTitleButtonDisplayed() {
		return isElementDisplayed(subTitleButton);
	}
	
	public boolean isTitleBackIconDisplayed() {
		return isElementDisplayed(titleBackIcon);
	}
	
	public boolean isViewPolicyDetailsTextDisplayed() {
		return isElementDisplayed(ViewPolicyDetailsText);
	}
	
	public  void clickOnPolicyListItem1() {
		clickOnElement(policyListItem1);
	}
	
	
	
}
