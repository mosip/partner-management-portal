package io.mosip.testrig.pmprevampui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ListOfDevicesPage extends BasePage {

	@FindBy(id = "add_devices")
	private WebElement addDeviceButton;

	@FindBy(id = "device_list_add_device_btn")
	private WebElement addDeviceButtonFromDeviceList;

	@FindBy(id = "list_of_devices")
	private WebElement listOfDevicesTitle;

	public ListOfDevicesPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnAddDeviceButton() {
		clickOnElement(addDeviceButton);
	}

	public void clickOnAddDeviceButtonFromDeviceList() {
		clickOnElement(addDeviceButtonFromDeviceList);
	}

	public boolean isAddDeviceButtonEnabled() {
		return isElementEnabled(addDeviceButton);
	}

	public String getListOFDevicesTitle() {
		return getTextFromLocator(listOfDevicesTitle);
	}

	public boolean isAddedDeviceDisplayed(String deviceType, String deviceSubType, String make, String model) {
		try {
			WebElement addedDevice = driver.findElement(By.xpath("//td[text()='" + deviceType + "']/..//td[text()='"
					+ deviceSubType + "']/..//td[text()='" + make + "']/..//td[text()='" + model + "']"));
			return isElementDisplayed(addedDevice);
		} catch (NoSuchElementException | TimeoutException e) {
			return false;
		}
	}

	public void clickOnDeviceThreeDots(String deviceType, String deviceSubType, String make, String model) {
		WebElement addedDeviceThreeDots = driver.findElement(
				By.xpath("//td[text()='" + deviceType + "']/..//td[text()='" + deviceSubType + "']/..//td[text()='"
						+ make + "']/..//td[text()='" + model + "']/..//p[contains(@id, 'device_list_action')]"));
		clickOnElement(addedDeviceThreeDots);
	}

}
