package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.LoginPage;
import io.mosip.testrig.pmprevampui.pages.PartnerCertificatePage;
import io.mosip.testrig.pmprevampui.pages.PoliciesPage;
import io.mosip.testrig.pmprevampui.pages.RegisterPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class NewUserPolicy extends BaseClass {

	@Test
	public void searchWithInvalidPolicyName() {

		DashboardPage dashboardpage = new DashboardPage(driver);

		assertTrue(dashboardpage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		PoliciesPage policiesPage=dashboardpage.clickOnPoliciesTitle();

		assertTrue(policiesPage.isPoliciesPageDisplayed(), GlobalConstants.isPoliciesPageDisplayed);
		assertTrue(policiesPage.isPoliciesEmptyTableDisplayed(), GlobalConstants.isPolicyEmptyTableIsDisplayed);
		assertTrue(policiesPage.isPoliciesEmptyTableEnabled(), GlobalConstants.isRequestPolicyEnabled);
		policiesPage.clickOnRequestPolicyButton();
		
		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
		policiesPage.selectPartnerIdDropdown("automationuiiii");

		assertTrue(policiesPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
		policiesPage.selectPolicyNameDropdown(data);
		policiesPage.searchInPolicyName(data +"123");
		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);

	}
	
//	@Test
//	public void searchWithInvalidPolicyName() {
//
//		DashboardPage dashboardpage = new DashboardPage(driver);
//
//		assertTrue(dashboardpage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
//		PoliciesPage policiesPage=dashboardpage.clickOnPoliciesTitle();
//
//		assertTrue(policiesPage.isPoliciesPageDisplayed(), GlobalConstants.isPoliciesPageDisplayed);
//		policiesPage.clickOnRequestPolicyButton();
//
//		assertTrue(policiesPage.isPartnerIdDropdownDisplayed(), GlobalConstants.isPartnerIdDropdownDisplayed);
//		policiesPage.selectPartnerIdDropdown("automationuiiii");
//
//		assertTrue(policiesPage.isPolicyNameDropdownDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
//		policiesPage.selectPolicyNameDropdown(data);
//		policiesPage.searchInPolicyName(data +"123");
//		assertTrue(policiesPage.isNoDataAvailableTextDisplayed(), GlobalConstants.isPolicyNameDropdownDisplayed);
//
//	}
}
