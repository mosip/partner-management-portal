package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerPolicyMappingTest" }, groups = { "PartnerPolicyLinkingDashboardCountTest" })
public class PartnerPolicyLinkingDashboardCountTest extends BaseClass {

	private DashboardPage dashboardPage;

	@Test(priority = 1, description = "Verify Partner - Policy Mapping card in dashboard")
	public void partnerPolicyLinkingCardShowsPendingCountAndHoverDescription() {
		dashboardPage = new DashboardPage(driver);

		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
		assertTrue(dashboardPage.isPartnerPolicyLinkingCountDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingCountDisplayed);

		String pendingCount = dashboardPage.getPartnerPolicyLinkingCountText();
		assertTrue(pendingCount.matches("\\d+"), GlobalConstants.isPartnerPolicyLinkingCountNumeric);

		dashboardPage.hoverOverPartnerPolicyLinkingCount();
		assertTrue(dashboardPage.isPartnerPolicyLinkingCountDescriptionDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingCountDescriptionDisplayedOnHover);
		assertEquals(dashboardPage.getPartnerPolicyLinkingCountDescriptionText(),
				pendingCount + GlobalConstants.PARTNER_POLICY_LINKING_COUNT_DESCRIPTION_SUFFIX,
				GlobalConstants.isPartnerPolicyLinkingCountDescriptionTextCorrect);
	}

	@Test(priority = 2, description = "Partner Admin Dashboard: Pending Requests count and description")
	public void pendingRequestsCardNotDisplayedInPartnerAdminDashboard() {
		dashboardPage = new DashboardPage(driver);

		assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
		assertFalse(dashboardPage.isTextPresentOnPage(GlobalConstants.PENDING_REQUESTS_CARD_TEXT),
				GlobalConstants.isPendingRequestsCardAbsentForPartnerAdmin);
	}

}
