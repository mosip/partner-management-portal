package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.DeviceProviderPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

// Runs as pmpui-deactivatedevice; nothing disables Add SBI for an inactive partner, so refusal shows at submit.
@Test(dependsOnGroups = { "DeactivateDevicePartnerCreation" }, groups = { "DeactivatedDeviceProviderTest" })
public class DeactivatedDeviceProviderTest extends BaseClass {

	private DashboardPage dashboardPage;
	private DeviceProviderPage deviceProviderPage;

	public DeactivatedDeviceProviderTest() {
		userid = GlobalConstants.DEACTIVATE_DEVICE_PARTNER_ID;
	}

	@Test(priority = 1, description = "Verify Partner is not able to create New SBI / Devices / FTM after Partner is deactivated")
	public void deactivatedDeviceProviderCannotCreateSbi() {
		dashboardPage = new DashboardPage(driver);

		assertTrue(dashboardPage.isDeviceProviderServicesTitleDisplayed(),
				GlobalConstants.isDeviceProviderServicesTitleDisplayed);
		handleTermsAndCondition();
		deviceProviderPage = dashboardPage.clickOnDeviceProviderServicesTitle();

		deviceProviderPage.clickOnAddSbiButton();
		deviceProviderPage.clickOnAddSbiPartnerIdBox();
		deviceProviderPage.clickOnPartnerOption();
		deviceProviderPage.enterSbiVersion(GlobalConstants.AUTOMATION);
		deviceProviderPage.enterSbiBinaryHash(GlobalConstants.AUTOMATION);
		deviceProviderPage.clickOnSubmit();

		assertTrue(deviceProviderPage.isAddSbiErrorMessageDisplayed(),
				GlobalConstants.isSbiCreationRejectedForDeactivatedPartner);
	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayedQuick()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}

}
