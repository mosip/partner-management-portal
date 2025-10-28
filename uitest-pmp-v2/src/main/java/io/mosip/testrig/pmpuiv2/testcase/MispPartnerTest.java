package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.fw.util.PmpTestUtil;
import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;

import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;

import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class MispPartnerTest extends BaseClass {

	private DashboardPage dashboardPage;
	private LoginPage loginPage;
	private BasePage basePage;
	private MispPartnerPage mispPartnerPage;

	@Test(priority = 1, description = "Create and manage misp policies")
	public void mispPartnerDetailsVerification() {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		loginPage = new LoginPage(driver);
		mispPartnerPage = new MispPartnerPage(driver);

		assertTrue(dashboardPage.isPoliciesButtonDisplayed(), GlobalConstants.isPoliciesButtonDisplayed);
		dashboardPage.clickOnPoliciesButton();
		assertTrue(mispPartnerPage.isListOfPolicyGroupsTitleDisplayed(),
				GlobalConstants.isListOfPolicyGroupsTitleDisplayed);
		mispPartnerPage.clickOnHomeButtonInCreatePolicyGroup();
		assertTrue(mispPartnerPage.isPolicyOverlayMenuDisplayed(), GlobalConstants.isPolicyOverlayMenuDisplayed);
		mispPartnerPage.clickOnPolicyOverlayMenu();
		assertTrue(mispPartnerPage.isListOfPolicyGroupsTitleDisplayed(),
				GlobalConstants.isListOfPolicyGroupsTitleDisplayed);
		assertTrue(mispPartnerPage.isHomeButtonInCreatePolicyGroupDisplayed(),
				GlobalConstants.isHomeButtonInCreatePolicyGroupDisplayed);

		assertTrue(mispPartnerPage.isMispPolicyabInCreatePolicyGroupDisplayed(),
				GlobalConstants.isMispPolicyabInCreatePolicyGroupDisplayed);
		assertTrue(mispPartnerPage.isCreatePolicyGroupBtnDisplayed(), GlobalConstants.isCreatePolicyGroupBtnDisplayed);
		assertTrue(mispPartnerPage.isItemsPerPagesDisplayed(), GlobalConstants.isItemsPerPagesDisplayed);
		assertTrue(mispPartnerPage.isFilterButtonInCreatePolicyGroupDisplayed(),
				GlobalConstants.isFilterButtonInCreatePolicyGroupDisplayed);
		assertTrue(mispPartnerPage.isCreatePrtnerPageTitleDisplayed(),
				GlobalConstants.isCreatePrtnerPageTitleDisplayed);
		assertTrue(mispPartnerPage.isHomeButtonInCreatePolicyGroupDisplayed(),
				GlobalConstants.isHomeButtonInCreatePolicyGroupDisplayed);
		assertTrue(mispPartnerPage.isTitleBackArrowDisplayed(), GlobalConstants.isTitleBackArrowDisplayed);
		mispPartnerPage.clickOnCreatePolicyBtn();
		assertTrue(mispPartnerPage.isListOfPoliyGroupTitleDisplayed(),
				GlobalConstants.isListOfPoliyGroupTitleDisplayed);
		assertTrue(mispPartnerPage.isMandatoryMessagesDisplayed(), GlobalConstants.isMandatoryMessagesDisplayed);
		assertTrue(mispPartnerPage.isPolicyGroupNameInputFieldDisplayed(),
				GlobalConstants.isPolicyGroupNameInputFieldDisplayed);
		assertTrue(mispPartnerPage.isPolicyGroupDescriptionInputFieldDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionInputFieldDisplayed);
		
		assertTrue(mispPartnerPage.policyNameInMispPolicyDisplayed(), GlobalConstants.policyNameInMispPolicyDisplayed);
		mispPartnerPage.clickOnListOfDataSharePoliciesTitle();

		assertTrue(mispPartnerPage.isMispPolicyabInCreatePolicyGroupDisplayed(),
				GlobalConstants.isMispPolicyabInCreatePolicyGroupDisplayed);
		mispPartnerPage.clickOnMispPolicyTitle();
		assertTrue(mispPartnerPage.isCreateMispPolicyHeaderDisplayed(),
				GlobalConstants.isCreateMispPolicyHeaderDisplayed);
		assertTrue(mispPartnerPage.listOfPoliciesTitleInMispPolicyDisplayed(),
				GlobalConstants.listOfPoliciesTitleInMispPolicyDisplayed);
		mispPartnerPage.clickOnCreateMispPolicyTitle();
		assertTrue(mispPartnerPage.policyNameInMispPolicyDisplayed(), GlobalConstants.policyNameInMispPolicyDisplayed);
		assertTrue(mispPartnerPage.PolicyDescriptionInMispPolicyDisplayed(),
				GlobalConstants.PolicyDescriptionInMispPolicyDisplayed);
		assertTrue(mispPartnerPage.submitBtnInMispPoliciesDisabledDisplayed(),
				GlobalConstants.submitBtnInMispPoliciesDisabledDisplayed);
		assertTrue(mispPartnerPage.uploadBtnInMispPolicyDisplayed(), GlobalConstants.uploadBtnInMispPolicyDisplayed);
		mispPartnerPage.clickOnUploadBtnInMispPolicy();

		mispPartnerPage.selectPolicyGroup("mosip policy group20");
		mispPartnerPage.enterPolicyName("Authentication policy");
		mispPartnerPage.enterPolicyDescription("policy data");

	}

}
