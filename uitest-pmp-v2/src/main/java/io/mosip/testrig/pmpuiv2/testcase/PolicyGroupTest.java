package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import io.mosip.testrig.pmpuiv2.pages.*;
import org.testng.annotations.Test;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

public class PolicyGroupTest extends BaseClass {
	private BasePage basePage;
	private DashboardPage dashboardPage;
	private PoliciesPage policiesPage;
	private PolicyGroupPage policygroupPage;
	private LoginPage loginPage;

	@Test(priority = 1, description = "Create Policy Group")
	public void createPolicyGroup() {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		policygroupPage = new PolicyGroupPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnCreatePolicyGroupButton();
		assertTrue(policygroupPage.isCreatePolicyGroupTitleDisplayed(),
				GlobalConstants.isCreatePolicyGroupTitleDisplayed);
		assertTrue(policygroupPage.isSubTitleDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(policygroupPage.isSubTitleHomeDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(policygroupPage.isPolicyGroupNameTextboxDisplayed(),
				GlobalConstants.isPolicyGroupNameTextboxDisplayed);
		assertTrue(policygroupPage.isPolicyGroupNameDescriptionTextboxDisplayed(),
				GlobalConstants.isPolicyGroupNameDescriptionTextboxDisplayed);
		assertTrue(policygroupPage.isSubmitButtonAvailable(), GlobalConstants.isSubmitButtonAvailable);
		assertFalse(policygroupPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
		policygroupPage.clickOnClearFormButton();
		policygroupPage.clickOnCancelButton();

		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.enterPolicyGroupName(GlobalConstants.ALPHANUMERIC);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.ALPHANUMERIC);
		policygroupPage.clickOnSubmitButton();
		assertTrue(policygroupPage.isPolicyGroupSuccessMessageDisplayed(),
				GlobalConstants.isPolicyGroupSuccessMessageDisplayed);
		assertTrue(policygroupPage.isTitleOfSuccessMessageDisplayed(),
				GlobalConstants.isTitleOfSuccessMessageDisplayed);
		assertTrue(policygroupPage.isSuccessHomeButtonAvailable(), GlobalConstants.isSuccessHomeButtonAvailable);
		assertTrue(policygroupPage.isSuccessGoBackButtonAvailable(), GlobalConstants.isSuccessGoBackButtonAvailable);
		policygroupPage.clickOnSuccessHomeButton();

		dashboardPage.clickOnPolicyButton();
		createPolicyGroup(GlobalConstants.NUMERIC, GlobalConstants.NUMERIC);

		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.enterPolicyGroupName(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(policygroupPage.isSpecialCharactersAreNotAllowedErrorMessageDisplayed(),
				GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.SPECIAL_CHARACTERS);
		assertTrue(policygroupPage.isSpecialCharactersAreNotAllowedErrorMessageDisplayed(),
				GlobalConstants.isSpecialCharactersAreNotAllowedErrorMessageDisplayed);
		policygroupPage.clickOnClearFormButton();
		policygroupPage.clickOnTitleBackIconButton();

		createPolicyGroup(GlobalConstants.CHARACTERS_1, GlobalConstants.CHARACTERS_1);
		createPolicyGroup(GlobalConstants.POLICYGROUP07, GlobalConstants.POLICYGROUP07);

		createPolicyGroup(GlobalConstants.DEACTIVATE_POLICYGROUP, GlobalConstants.DEACTIVATE_POLICYGROUP);

		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.enterPolicyGroupName(GlobalConstants.SPACE);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.SPACE);
		assertTrue(policygroupPage.isSubmitButtonEnabled(), GlobalConstants.isSubmitButtonEnabled);
		policygroupPage.clickOnSubmitButton();
		policygroupPage.clickOnSomethingWentWrongHomeBtn();

		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.enterPolicyGroupName(GlobalConstants.CHARACTERS_1);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.CHARACTERS_1);
		policygroupPage.clickOnSubmitButton();
		assertTrue(policygroupPage.isSameNamePolicyGroupAlreadyExistMessageDisplayed(),
				GlobalConstants.isSameNamePolicyGroupAlreadyExistMessageDisplayed);
		policygroupPage.clickOnErrorCloseButton();
		policygroupPage.enterPolicyGroupName(GlobalConstants.AUTOMATION_LOWERCASE);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.AUTOMATION_LOWERUPPERCASE);
		policygroupPage.clickOnSubmitButton();
		policygroupPage.clickOnSuccessGoBackButton();

		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.navigateBackDefaultButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);
		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.enterPolicyGroupName(GlobalConstants.AUTOMATION_LOWERUPPERCASE);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.AUTOMATION_LOWERUPPERCASE);
		policygroupPage.navigateBackDefaultButton();
		assertTrue(policygroupPage.isBrowserBackConfirmationPopupDisplayed(),
				GlobalConstants.isBrowserBackConfirmationPopupDisplayed);
		assertTrue(policygroupPage.isBrowserBackProceedButtonAvailable(),
				GlobalConstants.isBrowserBackProceedButtonAvailable);
		assertTrue(policygroupPage.isBrowserBackCancelButtonAvailable(),
				GlobalConstants.isBrowserBackCancelButtonAvailable);
		policygroupPage.clickOnBrowserBackCancelButton();
		policygroupPage.navigateBackDefaultButton();
		policygroupPage.clickOnBrowserBackProceedButton();
	}

	@Test(priority = 2, description = "Policygroup Tabular View")
	public void policyGroupTabularView() {

		dashboardPage = new DashboardPage(driver);
		policiesPage = new PoliciesPage(driver);
		policygroupPage = new PolicyGroupPage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		assertTrue(policiesPage.isPoliciesPolicyGroupTabDisplayed(), GlobalConstants.isPoliciesPolicyGroupTabDisplayed);
		assertTrue(policygroupPage.isAuthPolicyTabDisplayed(), GlobalConstants.isAuthPolicyTabDisplayed);
		assertTrue(policygroupPage.isDatasharePolicyTabDisplayed(), GlobalConstants.isDatasharePolicyTabDisplayed);
		assertTrue(policygroupPage.isTitleOfPageDisplayed(), GlobalConstants.isTitleOfPageDisplayed);
		assertTrue(policygroupPage.isBackiconDisplayed(), GlobalConstants.isBackiconDisplayed);
		assertTrue(policygroupPage.isSubTitleHomeDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(policygroupPage.isSubTitleOfPageDisplayed(), GlobalConstants.isSubTitleOfPageDisplayed);
		assertTrue(policygroupPage.isPolicyGroupHeaderTextDisplayed(),
				GlobalConstants.isPolicyGroupHeaderTextDisplayed);
		assertTrue(policygroupPage.isPolicyGroupNameHeaderDisplayed(),
				GlobalConstants.isPolicyGroupNameHeaderDisplayed);
		assertTrue(policygroupPage.isPolicyGroupDescriptionHeaderDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionHeaderDisplayed);
		assertTrue(policygroupPage.isCreatedDateHeaderTextDisplayed(),
				GlobalConstants.isCreatedDateHeaderTextDisplayed);
		assertTrue(policygroupPage.isStatusHeaderTextDisplayed(), GlobalConstants.isStatusHeaderTextDisplayed);
		assertTrue(policygroupPage.isActionHeaderTextDisplayed(), GlobalConstants.isActionHeaderTextDisplayed);

		assertTrue(policygroupPage.isPolicyGroupIdDescIconDisplayed(),
				GlobalConstants.isPolicyGroupIdDescIconDisplayed);
		assertTrue(policygroupPage.isPolicyGroupIdAscIconDisplayed(), GlobalConstants.isPolicyGroupIdAscIconDisplayed);
		assertTrue(policygroupPage.isPolicyGroupNameDescIconDisplayed(), GlobalConstants.isPolicyGroupNameDescIconDisplayed);
		assertTrue(policygroupPage.isPolicyGroupNameAscIconDisplayed(), GlobalConstants.isPolicyGroupNameAscIconDisplayed);
		assertTrue(policygroupPage.isPolicyGroupDescriptionDescIconDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionDescIconDisplayed);
		assertTrue(policygroupPage.isPolicyGroupDescriptionAscIconDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionAscIconDisplayed);
		assertTrue(policygroupPage.isCreatedDateTimeDescIconDisplayed(), GlobalConstants.isCreatedDateTimeDescIconDisplayed);
		assertTrue(policygroupPage.isCreatedDateTimeAscIconDisplayed(), GlobalConstants.isCreatedDateTimeAscIconDisplayed);
		assertTrue(policygroupPage.isStatusDescISconDisplayed(), GlobalConstants.isStatusDescISconDisplayed);
		assertTrue(policygroupPage.isStatusAscIconDisplayed(), GlobalConstants.isStatusAscIconDisplayed);

		assertTrue(policygroupPage.isFilterButtonDisplayedOrEnabled(),
				GlobalConstants.isFilterButtonDisplayedOrEnabled);
		assertTrue(policygroupPage.isPaginationDisplayed(), GlobalConstants.isPaginationDisplayed);

		policygroupPage.clickOnPolicyGroupList1();
		assertTrue(policygroupPage.isViewPolicyGroupPageTitleDisplayed(),
				GlobalConstants.isViewPolicyGroupPageTitleDisplayed);
		policygroupPage.clickOnPolicyGroupViewBackButton();

		policygroupPage.clickOnFilterButton();
		policygroupPage.clickOnPolicyGroupNameFilter(GlobalConstants.DEACTIVATE_POLICYGROUP);
		policygroupPage.clickOnApplyFilterButton();
		policygroupPage.clickOnPolicyGroupActionButton();
		assertTrue(policygroupPage.isPolicyGroupViewButtonDisplayed(),
				GlobalConstants.isPolicyGroupViewButtonDisplayed);
		assertTrue(policygroupPage.isDeactivateButtonDisplayed(),
				GlobalConstants.isPolicyGroupDeactivateButtonDisplayed);
		policygroupPage.clickOnDeactivateButton();
		policygroupPage.clickOnDeactivateConfirmButton();

		policygroupPage.clickOnFilterButton();
		policygroupPage.clickOnPolicyGroupNameFilter(GlobalConstants.DEACTIVATE_POLICYGROUP);
		policygroupPage.clickOnApplyFilterButton();
		policygroupPage.clickOnPolicyGroupList1();
		assertFalse(policygroupPage.isViewPolicyGroupPageTitleDisplayed(),
				GlobalConstants.isViewPolicyGroupPageTitleDisplayed);

		policygroupPage.clickOnFilterButton();
		policygroupPage.clickOnPolicyGroupNameFilter(GlobalConstants.INVALID_DATA);
		policygroupPage.clickOnApplyFilterButton();
		assertTrue(policygroupPage.isNoResultsFoundMessageDisplayed(),
				GlobalConstants.isNoResultsFoundMessageDisplayed);
		policygroupPage.navigateBackDefaultButton();

	}

	@Test(priority = 3, description = "Policygroup Details View")
	public void policyGroupDetailsView() {

		dashboardPage = new DashboardPage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		basePage = new BasePage(driver);

		loginAsPartnerAdmin();
		dashboardPage.clickOnPolicyButton();
		policygroupPage.clickOnFilterButton();
		policygroupPage.clickOnPolicyGroupNameFilter(GlobalConstants.ALPHANUMERIC);
		policygroupPage.clickOnApplyFilterButton();
		policygroupPage.clickOnPolicyGroupList1();
		assertTrue(policygroupPage.isViewPolicyGroupPageTitleDisplayed(),
				GlobalConstants.isViewPolicyGroupPageTitleDisplayed);
		assertTrue(policygroupPage.isSubTitleDisplayed(), GlobalConstants.isSubTitleDisplayed);
		assertTrue(policygroupPage.isSubTitleHomeDisplayed(), GlobalConstants.isSubTitleHomeDisplayed);
		assertTrue(policygroupPage.isStatusOfPolicyGroupDisplayed(), GlobalConstants.isStatusOfPolicyGroupDisplayed);
		assertTrue(policygroupPage.isPolicyGroupNameLabelDisplayed(), GlobalConstants.isPolicyGroupNameLabelDisplayed);
		assertTrue(policygroupPage.isPolicyGroupNameContextDisplayed(),
				GlobalConstants.isPolicyGroupNameContextDisplayed);
		assertTrue(policygroupPage.isPolicyGroupDescriptionLabelDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionLabelDisplayed);
		assertTrue(policygroupPage.isPolicyGroupDescriptionContextDisplayed(),
				GlobalConstants.isPolicyGroupDescriptionContextDisplayed);
		policygroupPage.clickOnTitleBackIconButton();

		policygroupPage.clickOnPolicyGroupList1();
		assertTrue(policygroupPage.isHamburgerOptionDisplayed(), GlobalConstants.isHamburgerOptionDisplayed);
		basePage.scrollToEndPage();
		policygroupPage.navigateBackDefaultButton();

	}

	private void createPolicyGroup(String policyGroupNameValue, String policyGroupDescValue) {
		policygroupPage.clickOnCreatePolicyGroupButton();
		policygroupPage.enterPolicyGroupName(policyGroupNameValue);
		policygroupPage.enterPolicyGroupNameDescription(policyGroupDescValue);
		policygroupPage.clickOnSubmitButton();
		policygroupPage.clickOnSuccessGoBackButton();

	}

	private void loginAsPartnerAdmin() {
		dashboardPage.clickOnProfileDropdown();
		loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.enterUserName(GlobalConstants.POLICIES_ADMIN);
		loginPage.enterPassword(GlobalConstants.PARTNER_PASSWORD);
		loginPage.clickOnLoginButton();

	}

}
