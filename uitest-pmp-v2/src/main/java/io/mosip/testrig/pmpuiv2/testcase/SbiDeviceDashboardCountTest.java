package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "SbiDeviceProviderTest" }, groups = { "SbiDeviceDashboardCountTest" })
public class SbiDeviceDashboardCountTest extends BaseClass {

	private DashboardPage dashboardPage;

	@Test(priority = 1, description = "Verify SBI - Device card in dashboard")
	public void sbiDeviceCardShowsPendingCountAndHoverDescription() {
		dashboardPage = new DashboardPage(driver);

		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
		assertTrue(dashboardPage.isSbiDeviceCountDisplayed(), GlobalConstants.isSbiDeviceCountDisplayed);

		String countText = dashboardPage.getSbiDeviceCountText();
		String[] counts = countText.split("\\|");
		assertEquals(counts.length, 2, GlobalConstants.isSbiDeviceCountFormatValid);
		String sbiCount = counts[0].trim();
		String deviceCount = counts[1].trim();
		assertTrue(sbiCount.matches("\\d+") && deviceCount.matches("\\d+"), GlobalConstants.isSbiDeviceCountFormatValid);

		dashboardPage.hoverOverSbiDeviceCount();
		assertTrue(dashboardPage.isSbiDeviceCountDescriptionDisplayed(),
				GlobalConstants.isSbiDeviceCountDescriptionDisplayedOnHover);
		assertEquals(dashboardPage.getSbiDeviceCountDescriptionText(),
				sbiCount + GlobalConstants.SBI_DEVICE_COUNT_DESCRIPTION_MIDDLE + deviceCount
						+ GlobalConstants.SBI_DEVICE_COUNT_DESCRIPTION_SUFFIX,
				GlobalConstants.isSbiDeviceCountDescriptionTextCorrect);
	}

}
