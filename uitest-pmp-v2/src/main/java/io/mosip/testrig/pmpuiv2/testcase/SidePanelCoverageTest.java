package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.pages.SideNavPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

/**
 * MOSIP-38412 - the side panel must cover the entire screen height on every
 * Partner Admin screen, before and after the page behind it is scrolled.
 *
 * One scenario walks the admin screens rather than one test per screen, since
 * the assertion is identical on each.
 *
 * Covers TC_38412_01 to _07.
 */
@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "SidePanelCoverageTest" })
public class SidePanelCoverageTest extends BaseClass {

	private DashboardPage dashboardPage;
	private PoliciesPage policiesPage;
	private SideNavPage sideNavPage;

	/** Asserts the panel is pinned to the full viewport height, before and after scrolling. */
	private void assertSidePanelCoversFullHeight(String screen) {
		assertTrue(sideNavPage.isSidePanelDisplayed(), GlobalConstants.isSidePanelDisplayed + " [" + screen + "]");
		assertTrue(sideNavPage.isSidePanelCoveringFullHeight(),
				GlobalConstants.isSidePanelCoveringFullHeight + " [" + screen + "]");
		assertTrue(sideNavPage.isSidePanelCoveringFullHeightAfterScroll(),
				GlobalConstants.isSidePanelCoveringFullHeightAfterScroll + " [" + screen + "]");
	}

	@Test(priority = 1, description = "Verify the side panel covers the entire screen height on the Certificate Trust "
			+ "Store, Partners, Policies, Authentication Policy, Data Share Policy and MISP Policy screens of the "
			+ "Partner Admin portal, including after scrolling. (TC 01,02,03,04,05,06,07)")
	public void sidePanelCoversFullScreenHeightAcrossAdminScreens() {
		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		sideNavPage = new SideNavPage(driver);

		// Certificate Trust Store (TC_01)
		dashboardPage.clickOnCertificateTrustStore();
		assertSidePanelCoversFullHeight("Certificate Trust Store");

		// Partners (TC_02)
		sideNavPage.clickOnSidePanelHomeIcon();
		dashboardPage.clickOnPartners();
		assertSidePanelCoversFullHeight("Partners");

		// Policies (TC_03)
		sideNavPage.clickOnSidePanelHomeIcon();
		dashboardPage.clickOnPolicyButton();
		assertSidePanelCoversFullHeight("Policies");

		// Authentication Policy tab (TC_04, TC_07)
		policiesPage.clickOnAuthPolicyTab();
		assertSidePanelCoversFullHeight("Authentication Policy");

		// Data Share Policy tab (TC_05)
		policiesPage.clickOnDataSharePolicyTab();
		assertSidePanelCoversFullHeight("Data Share Policy");

		// MISP Policy tab (TC_06)
		policiesPage.clickOnMispPolicyTab();
		assertSidePanelCoversFullHeight("MISP Policy");
	}
}
