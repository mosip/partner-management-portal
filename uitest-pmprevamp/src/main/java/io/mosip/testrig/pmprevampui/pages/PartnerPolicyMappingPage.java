package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PartnerPolicyMappingPage extends BasePage {

	
	@FindBy(id = "filter_btn")
	private WebElement filterButton;

	@FindBy(id = "filter_reset_btn")
	private WebElement filterResetButton;
	
	@FindBy(id = "policy_group_filter")
	private WebElement policyGroupFilter;

	@FindBy(id = "apply_filter__btn")
	private WebElement applyFilterButton;

	@FindBy(id = "partner_list_view1")
	private WebElement partnerListViewElipsisButton;
	
	@FindBy(xpath = "//*[contains(text(), 'Approve / Reject')]")
	private WebElement approveOrRejectButton;
	
	@FindBy(xpath = "//*[contains(text(), 'Do you want to Approve/Reject the Policy')]")
	private WebElement confirmationPopup;
	
	@FindBy(xpath = "//*[contains(text(), 'Please review the policy details carefully before taking appropriate action.')]")
	private WebElement confirmationPopupDetailedMessage;
	
	@FindBy(xpath = "(//*[@id='partner_details_view_btn'])[1]")
	private WebElement approveRejectButton;
	
	@FindBy(xpath = "(//*[contains(text(), 'Approve')])[3]")
	private WebElement approveButton;
	
	public PartnerPolicyMappingPage(WebDriver driver) {
		super(driver);
	}

	public boolean isFilterButtonButtonEnabled() {
		return isElementEnabled(filterButton);
	}

	public void clickOnFilterButton() {
		clickOnElement(filterButton);
	}

	public void clickOnFilterResetButton() {
		clickOnElement(filterResetButton);
	}
	
	public void enterpolicyGroupFilter(String val) {
		enter(policyGroupFilter,val);
	}

	public void clickOnApplyFilterButton() {
		clickOnElement(applyFilterButton);
	}
	
	public void clickOnPartnerListViewElipsisButton() {
		clickOnElement(partnerListViewElipsisButton);
	}
	
	public void clickOnApproveOrRejectButton() {
		clickOnElement(approveOrRejectButton);
	}
	
	public boolean isConfirmationPopupDisplayed() {
		return isElementEnabled(confirmationPopup);
	}
	
	public boolean isConfirmationPopupDetailedMessageDisplayed() {
		return isElementEnabled(confirmationPopupDetailedMessage);
	}
	
	public boolean isApproveRejectButtonDisplayed() {
		return isElementEnabled(approveRejectButton);
	}
	
	public boolean isApproveSubmitButtonDisplayed() {
		return isElementEnabled(approveButton);
	}
	
	public void clickOnApproveRejectButton() {
		clickOnElement(approveRejectButton);
	}
	
	public void clickOnApproveSubmitButton() {
		clickOnElement(approveButton);
	}
	
}
