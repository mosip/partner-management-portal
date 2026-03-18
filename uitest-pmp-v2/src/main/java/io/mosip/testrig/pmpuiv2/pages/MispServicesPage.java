package io.mosip.testrig.pmpuiv2.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MispServicesPage extends BasePage{

	@FindBy(id = "generate_misp_license_key_btn")
	private WebElement generateMispLicenceKeyButton;
	
	@FindBy(id = "page_title")
	private WebElement generateMispLicenceKeyTitle;
	
	@FindBy(id = "sub_title_btn")
	private WebElement mispServicesBreadcomb;
	
	public MispServicesPage(WebDriver driver) {
		super(driver);
	}
	

	public boolean isGenerateMispLicenceKeyButtonDisplayed() {
		return isElementDisplayed(generateMispLicenceKeyButton);
	}
	
	public void clickOnGenerateMispLicenceKeyButton() {
		clickOnElement(generateMispLicenceKeyButton);
	}
	
	public boolean isGenerateMispLicenceKeyPageDisplayed() {
		return isElementDisplayed(generateMispLicenceKeyTitle);
	}
	
	public void clickOnMispServicesBreadcomb() {
		clickOnElement(mispServicesBreadcomb);
	}


}
