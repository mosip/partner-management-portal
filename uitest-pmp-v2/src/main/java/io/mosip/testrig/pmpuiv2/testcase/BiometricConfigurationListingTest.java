package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BiometricConfigurationPage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(groups = { "BiometricConfigurationListingTest" })
public class BiometricConfigurationListingTest extends BaseClass {

	private DashboardPage dashboardPage;
	private BiometricConfigurationPage biometricConfigurationPage;

	private static String existingConfigName;

	public BiometricConfigurationListingTest() {
		this.userid = GlobalConstants.QAJAVA21_ADMIN_USER;
		this.password = GlobalConstants.QAJAVA21_ADMIN_PASSWORD;
	}

	@Test(priority = 1, description = "Verifying Biometric Configuration Listing Table")
	public void VerifyingBiometricConfigurationListingTable() {
		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);

		handleTermsAndCondition();
		openBiometricConfigurationList();

		assertTrue(biometricConfigurationPage.isListingTableDisplayed(),
				GlobalConstants.isBiometricConfigurationListingTableDisplayed);
		assertTrue(biometricConfigurationPage.isConfigNameColumnDisplayed(),
				GlobalConstants.isBiometricConfigurationListingColumnsDisplayed);
		assertTrue(biometricConfigurationPage.isProviderNameColumnDisplayed(),
				GlobalConstants.isBiometricConfigurationListingColumnsDisplayed);
		assertTrue(biometricConfigurationPage.isProviderVersionColumnDisplayed(),
				GlobalConstants.isBiometricConfigurationListingColumnsDisplayed);
		assertTrue(biometricConfigurationPage.isModalityColumnDisplayed(),
				GlobalConstants.isBiometricConfigurationListingColumnsDisplayed);
		assertTrue(biometricConfigurationPage.isCreationDateColumnDisplayed(),
				GlobalConstants.isBiometricConfigurationListingColumnsDisplayed);
		assertTrue(biometricConfigurationPage.isCreateButtonTopDisplayed(),
				"Create Configuration button should be at top-right when configurations exist");
		assertTrue(biometricConfigurationPage.isFilterButtonDisplayed(),
				GlobalConstants.isBiometricConfigurationFilterButtonDisplayed);

		existingConfigName = biometricConfigurationPage.getFirstRowConfigurationName();
		assertTrue(existingConfigName != null && !existingConfigName.isEmpty(),
				"At least one configuration row should be present for listing verification");

		biometricConfigurationPage.clickOnSortAsc("configName");
		biometricConfigurationPage.clickOnSortDesc("createdDateTime");
		assertTrue(biometricConfigurationPage.isListingTableDisplayed(),
				"Listing table should remain available after sort interaction");
	}

	@Test(priority = 2, description = "Filtering Biometric Configuration List",
			dependsOnMethods = "VerifyingBiometricConfigurationListingTable")
	public void FilteringBiometricConfigurationList() {
		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);

		handleTermsAndCondition();
		openBiometricConfigurationList();

		assertTrue(biometricConfigurationPage.isFilterButtonDisplayed(),
				GlobalConstants.isBiometricConfigurationFilterButtonDisplayed);
		biometricConfigurationPage.clickOnFilterButton();
		assertTrue(biometricConfigurationPage.isFilterConfigNameDisplayed(),
				GlobalConstants.isBiometricConfigurationFilterPanelDisplayed);
		assertTrue(biometricConfigurationPage.isFilterProviderNameDisplayed(),
				GlobalConstants.isBiometricConfigurationFilterPanelDisplayed);
		assertTrue(biometricConfigurationPage.isFilterProviderVersionDisplayed(),
				GlobalConstants.isBiometricConfigurationFilterPanelDisplayed);
		assertTrue(biometricConfigurationPage.isFilterModalityDisplayed(),
				GlobalConstants.isBiometricConfigurationFilterPanelDisplayed);
		assertTrue(biometricConfigurationPage.isApplyFilterButtonDisplayed(),
				GlobalConstants.isBiometricConfigurationFilterPanelDisplayed);

		biometricConfigurationPage.enterConfigurationNameInFilter(existingConfigName);
		biometricConfigurationPage.clickOnApplyFilterButton();
		assertTrue(biometricConfigurationPage.isConfigurationNamePresentInList(existingConfigName),
				"Filter by configuration name should return matching config");

		assertTrue(biometricConfigurationPage.isFilterResetButtonDisplayed(),
				"Filter reset should be available after apply");
		biometricConfigurationPage.clickOnFilterResetButton();
		assertTrue(biometricConfigurationPage.isListingTableDisplayed(),
				"Listing table should be restored after filter reset");
	}

	@Test(priority = 3, description = "Viewing Biometric Configuration Details",
			dependsOnMethods = "VerifyingBiometricConfigurationListingTable")
	public void ViewingBiometricConfigurationDetails() {
		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);

		handleTermsAndCondition();
		openBiometricConfigurationList();

		biometricConfigurationPage.clickOnFilterButton();
		biometricConfigurationPage.enterConfigurationNameInFilter(existingConfigName);
		biometricConfigurationPage.clickOnApplyFilterButton();
		assertTrue(biometricConfigurationPage.isConfigurationNamePresentInList(existingConfigName),
				"Configuration must be present before view");

		biometricConfigurationPage.clickOnFirstRowActionMenu();
		assertTrue(biometricConfigurationPage.isViewActionDisplayed(),
				GlobalConstants.isBiometricConfigurationViewActionDisplayed);
		assertTrue(biometricConfigurationPage.isDeleteActionDisplayed(),
				GlobalConstants.isBiometricConfigurationDeleteActionDisplayed);
		biometricConfigurationPage.clickOnViewAction();

		assertTrue(biometricConfigurationPage.isViewConfigurationDetailsPageDisplayed(),
				GlobalConstants.isBiometricConfigurationViewPageDisplayed);
		assertTrue(biometricConfigurationPage.isViewProviderNameDisplayed(),
				"View page should show provider name");
		assertTrue(biometricConfigurationPage.isViewProviderVersionDisplayed(),
				"View page should show provider version");
		assertTrue(biometricConfigurationPage.isViewModalityDisplayed(),
				"View page should show modality");
		biometricConfigurationPage.clickOnViewBackButton();
		assertTrue(biometricConfigurationPage.isBiometricConfigurationListPageDisplayed(),
				GlobalConstants.isBiometricConfigurationListPageDisplayed);
	}

	private void openBiometricConfigurationList() {
		assertTrue(biometricConfigurationPage.isBiometricConfigurationDashboardCardDisplayed(),
				GlobalConstants.isBiometricConfigurationDashboardCardDisplayed);
		biometricConfigurationPage.clickOnBiometricConfigurationDashboardCard();
		assertTrue(biometricConfigurationPage.isBiometricConfigurationListPageDisplayed(),
				GlobalConstants.isBiometricConfigurationListPageDisplayed);
	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayedQuick()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}
}
