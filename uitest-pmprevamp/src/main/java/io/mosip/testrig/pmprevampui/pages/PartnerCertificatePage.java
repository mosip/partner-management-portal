package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.mosip.testrig.pmprevampui.utility.TestRunner;

public class PartnerCertificatePage extends BasePage{
	
	@FindBy(id = "title_back_icon")
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
	
	@FindBy(id = "download_btn1")
	private WebElement downloadButton;
	
	@FindBy(id = "original_certificate_download_btn1")
	private WebElement originalCertificateDownloadButton;
	
	@FindBy(id = "mosip_signed_certificate_download_btn1")
	private WebElement mosipSignedCertificateDownloadButton;
	
	@FindBy(id = "partner_certificate_re_upload_btn1")
	private WebElement partnerCertificateReuploadButton;
	
	
	
	
	
	
	
	
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
	
	public boolean isDownloadButtonDisplayed() {
		return isElementDisplayed(downloadButton);
	}
	
	public  void clickOnPartnerCertificateReuploadButton() {
		clickOnElement(partnerCertificateReuploadButton);
	}
	
	
}
