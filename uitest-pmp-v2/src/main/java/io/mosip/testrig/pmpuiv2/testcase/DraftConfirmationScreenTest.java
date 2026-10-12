package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PolicyGroupTest" }, groups = { "DraftConfirmationScreenTest" })
public class DraftConfirmationScreenTest extends BaseClass {

	private DashboardPage dashboardPage;
	private PoliciesPage policiesPage;
	private AuthPolicyPage authPolicyPage;
	private LoginPage loginPage;
	private BasePage basePage;

	@Test(priority = 1, description = "Verify Save as Draft button is clickable")
	public void saveAsDraftButtonBecomesEnabled() {
		fillNewAuthPolicyForm(uniquePolicyName("clickable"));

		assertTrue(authPolicyPage.waitForSaveAsDraftButtonEnabled(), GlobalConstants.isSaveAsDraftButtonEnabled);
	}

	@Test(priority = 2, description = "Verify draft confirmation message after clicking Save as Draft")
	public void draftConfirmationMessageIsDisplayed() {
		saveNewAuthPolicyAsDraft(uniquePolicyName("message"));

		assertTrue(authPolicyPage.isPolicySavedAsDraftMessageDisplayed(),
				GlobalConstants.isPolicySavedAsDraftMessageDisplayed);
		assertEquals(authPolicyPage.getDraftConfirmationHeaderText(), GlobalConstants.DRAFT_CONFIRMATION_HEADER,
				GlobalConstants.isDraftConfirmationHeaderDisplayed);
		assertEquals(authPolicyPage.getDraftConfirmationDescriptionText(),
				GlobalConstants.AUTH_POLICY_DRAFT_CONFIRMATION_MESSAGE,
				GlobalConstants.isDraftConfirmationMessageCorrect);
	}

	@Test(priority = 3, description = "Verify confirmation screen provides two buttons after saving draft")
	public void draftConfirmationScreenShowsPublishAndGoBackButtons() {
		saveNewAuthPolicyAsDraft(uniquePolicyName("buttons"));

		assertTrue(authPolicyPage.isDraftConfirmationPublishButtonDisplayed(),
				GlobalConstants.isDraftConfirmationPublishButtonDisplayed);
		assertTrue(authPolicyPage.isDraftConfirmationGoBackButtonDisplayed(),
				GlobalConstants.isDraftConfirmationGoBackButtonDisplayed);

		assertEquals(authPolicyPage.getDraftConfirmationPublishButtonText(), GlobalConstants.PUBLISH_BUTTON_LABEL,
				GlobalConstants.isDraftConfirmationPublishButtonDisplayed);
		assertEquals(authPolicyPage.getDraftConfirmationGoBackButtonText(), GlobalConstants.GO_BACK_BUTTON_LABEL,
				GlobalConstants.isDraftConfirmationGoBackButtonDisplayed);

		assertTrue(authPolicyPage.isPublishButtonLeftOfGoBackButton(), GlobalConstants.isPublishButtonOnLeftOfGoBack);
	}

	@Test(priority = 4, description = "Verify Go Back button functionality on draft confirmation screen")
	public void goBackReturnsToListOfPolicies() {
		saveNewAuthPolicyAsDraft(uniquePolicyName("goback"));

		authPolicyPage.clickOnGoBackButton();
		assertTrue(authPolicyPage.isListOfPoliciesPageDisplayed(), GlobalConstants.isRedirectedToPolicyListAfterGoBack);
	}

	@Test(priority = 5, description = "Verify Publish button functionality on draft confirmation screen")
	public void publishFromDraftConfirmationStartsPublishFlow() {
		saveNewAuthPolicyAsDraft(uniquePolicyName("publish"));

		authPolicyPage.clickOnDraftConfirmationPublishButton();
		assertTrue(authPolicyPage.isPublishPopupDisplayed(), GlobalConstants.isPublishInitiatedFromDraftConfirmation);
		assertTrue(authPolicyPage.isPublishPopupInfoTextDisplayed(),
				GlobalConstants.isPublishInitiatedFromDraftConfirmation);
	}

	private String uniquePolicyName(String suffix) {
		return GlobalConstants.DRAFT_CONFIRMATION_POLICY + suffix + data;
	}

	private void saveNewAuthPolicyAsDraft(String policyName) {
		fillNewAuthPolicyForm(policyName);
		assertTrue(authPolicyPage.waitForSaveAsDraftButtonEnabled(), GlobalConstants.isSaveAsDraftButtonEnabled);
		authPolicyPage.clickOnSaveAsDraftButton();
	}

	private void fillNewAuthPolicyForm(String policyName) {
		dashboardPage = new DashboardPage(driver);
		basePage = new BasePage(driver);
		policiesPage = new PoliciesPage(driver);
		authPolicyPage = new AuthPolicyPage(driver);

		loginAsPolicyAdmin();
		dashboardPage.clickOnPolicyButton();
		policiesPage.clickOnAuthPolicyTab();
		authPolicyPage.clickOnCreateAuthPolicyButton();

		authPolicyPage.selectPolicyGroupDropdown(GlobalConstants.DEFAULT_POLICYGROUP);
		authPolicyPage.enterPolicyName(policyName);
		authPolicyPage.enterpolicyDescription(GlobalConstants.DRAFT_CONFIRMATION_POLICY_DESCRIPTION);
		authPolicyPage.uploadPolicyData();
		basePage.scrollToEndPage();
	}

	private void loginAsPolicyAdmin() {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.login(GlobalConstants.POLICIES_ADMIN, GlobalConstants.PARTNER_PASSWORD);
	}

}
