package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmprevampui.utility.TestRunner;

public class PartnerCertificatePage extends BasePage{
	
	@FindBy(id = "dashboard_partner_certificated_list_card")
	private WebElement partnerCertificateTitle;
	
	@FindBy(xpath = "//*[text()='Upload']")
	private WebElement uploadButton;
	
	@FindBy(xpath = "//*[text()='Upload Partner Certificate']")
	private WebElement uploadPartnerCertificatePopUp;
	
	@FindBy(xpath = "//*[text()='Submit']")
	private WebElement submitButton;
	
	@FindBy(xpath = "//*[text()='Partner certificate for Authentication Partner is uploaded successfully.']")
	private WebElement sucessMessage;
	
	@FindBy(id = "fileInput")
	private WebElement uploadFile;
	
	@FindBy(xpath = "//*[text()='Close']")
	private WebElement closeButton;
	
	@FindBy(id = "sub_title_home_btn")
	private WebElement homeButton;
	
	public PartnerCertificatePage(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPartnerCertificatePageDisplayed() {
		return isElementDisplayed(partnerCertificateTitle);
	}
	
	public  void clickOnUploadButton() {
		clickOnElement(uploadButton);
	}
	
	public boolean isUploadPartnerCertificatePopUpDisplayed() {
		return isElementDisplayed(uploadPartnerCertificatePopUp);
	}
	
	public  void uploadCertificate() {
		uploadImage(uploadFile,TestRunner.getResourcePath()+"\\pmp_revamp_cert\\Client.cer");
	}
	
	public  void clickOnSubmitButton() {
		clickOnElement(submitButton);
	}
	
	public boolean isSucessMessageDisplayed() {
		return isElementDisplayed(sucessMessage);
	}
	
	public  void clickOnCloseButton() {
		clickOnElement(closeButton);
	}
	
	public  DashboardPage clickOnHomeButton() {
		clickOnElement(homeButton);
		return new DashboardPage(driver);
	}
}
