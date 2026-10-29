package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BiometricConfigurationPage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(alwaysRun = true, groups = { "BiometricConfigurationDeleteTest" })
public class BiometricConfigurationDeleteTest extends BaseClass {

	private DashboardPage dashboardPage;
	private BiometricConfigurationPage biometricConfigurationPage;

	private static String configNameToDelete;

	public BiometricConfigurationDeleteTest() {
		this.userid = GlobalConstants.QAJAVA21_ADMIN_USER;
		this.password = GlobalConstants.QAJAVA21_ADMIN_PASSWORD;
	}

	@Test(priority = 1, description = "Creating Biometric Configuration for delete")
	public void CreatingBiometricConfigurationForDelete() {
		configNameToDelete = "biodelete" + data;

		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);

		handleTermsAndCondition();
		openBiometricConfigurationList();

		biometricConfigurationPage.clickOnCreateBiometricConfigurationButton();
		assertTrue(biometricConfigurationPage.isCreateBiometricConfigurationFormDisplayed(),
				GlobalConstants.isCreateBiometricConfigurationFormDisplayed);

		biometricConfigurationPage.enterConfigurationName(configNameToDelete);
		biometricConfigurationPage.enterProviderName(GlobalConstants.BIOMETRIC_CONFIGURATION_PROVIDER_NAME);
		biometricConfigurationPage.enterProviderVersion(GlobalConstants.BIOMETRIC_CONFIGURATION_PROVIDER_VERSION);
		biometricConfigurationPage.selectBiometricModality(GlobalConstants.BIOMETRIC_CONFIGURATION_MODALITY_FACE);
		biometricConfigurationPage.selectCredentialDataFormatRawData();
		biometricConfigurationPage.selectFirstAttributeNameIfNeeded();

		assertTrue(biometricConfigurationPage.isSubmitButtonEnabled(),
				"Submit should be enabled after all mandatory fields are filled");
		biometricConfigurationPage.clickOnSubmitButton();
		assertTrue(biometricConfigurationPage.isBiometricConfigurationCreatedSuccessfully(),
				GlobalConstants.isBiometricConfigurationCreatedSuccessfully);
		biometricConfigurationPage.clickOnSuccessGoBackButton();

		assertTrue(biometricConfigurationPage.isBiometricConfigurationListPageDisplayed(),
				GlobalConstants.isBiometricConfigurationListPageDisplayed);
		assertTrue(biometricConfigurationPage.isConfigurationNamePresentInList(configNameToDelete),
				"Created biometric configuration should appear in the list before delete");
	}

	@Test(priority = 2, description = "Verifying Delete action menu and Cancel keeps configuration",
			dependsOnMethods = "CreatingBiometricConfigurationForDelete")
	public void VerifyingDeleteActionAndCancel() throws InterruptedException {
		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);

		handleTermsAndCondition();
		openBiometricConfigurationList();
		filterConfigurationByName(configNameToDelete);

		assertTrue(biometricConfigurationPage.isFirstRowActionMenuDisplayed(),
				"Action menu should be available for the configuration row");
		biometricConfigurationPage.clickOnFirstRowActionMenu();
		assertTrue(biometricConfigurationPage.isDeleteActionDisplayed(),
				GlobalConstants.isBiometricConfigurationDeleteActionDisplayed);

		biometricConfigurationPage.clickOnDeleteAction();
		assertTrue(biometricConfigurationPage.isDeleteConfirmationPopupDisplayed(),
				GlobalConstants.isBiometricConfigurationDeletePopupDisplayed);

		biometricConfigurationPage.clickOnDeleteCancelButton();
		Thread.sleep(1000);

		assertTrue(biometricConfigurationPage.isBiometricConfigurationListPageDisplayed(),
				GlobalConstants.isBiometricConfigurationListPageDisplayed);
		assertTrue(biometricConfigurationPage.isConfigurationNamePresentInList(configNameToDelete),
				GlobalConstants.isBiometricConfigurationDeleteCancelKeepsConfig);
	}

	@Test(priority = 3, description = "Confirming Delete removes configuration from listing",
			dependsOnMethods = "VerifyingDeleteActionAndCancel")
	public void ConfirmingDeleteBiometricConfiguration() throws InterruptedException {
		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);

		handleTermsAndCondition();
		openBiometricConfigurationList();
		filterConfigurationByName(configNameToDelete);

		assertTrue(biometricConfigurationPage.isConfigurationNamePresentInList(configNameToDelete),
				"Configuration must be present before confirm delete");
		assertTrue(biometricConfigurationPage.isFirstRowActionMenuDisplayed(),
				"Action menu should be available for the configuration row");
		biometricConfigurationPage.clickOnFirstRowActionMenu();
		assertTrue(biometricConfigurationPage.isDeleteActionDisplayed(),
				GlobalConstants.isBiometricConfigurationDeleteActionDisplayed);

		biometricConfigurationPage.clickOnDeleteAction();
		assertTrue(biometricConfigurationPage.isDeleteConfirmationPopupDisplayed(),
				GlobalConstants.isBiometricConfigurationDeletePopupDisplayed);

		biometricConfigurationPage.clickOnDeleteConfirmButton();
		Thread.sleep(2000);

		assertTrue(biometricConfigurationPage.isBiometricConfigurationListPageDisplayed(),
				GlobalConstants.isBiometricConfigurationListPageDisplayed);
		assertTrue(biometricConfigurationPage.isConfigurationNameAbsentFromList(configNameToDelete),
				GlobalConstants.isBiometricConfigurationDeletedFromList);
	}

	private void openBiometricConfigurationList() {
		assertTrue(biometricConfigurationPage.isBiometricConfigurationDashboardCardDisplayed(),
				GlobalConstants.isBiometricConfigurationDashboardCardDisplayed);
		biometricConfigurationPage.clickOnBiometricConfigurationDashboardCard();
		assertTrue(biometricConfigurationPage.isBiometricConfigurationListPageDisplayed(),
				GlobalConstants.isBiometricConfigurationListPageDisplayed);
	}

	private void filterConfigurationByName(String configName) throws InterruptedException {
		assertTrue(biometricConfigurationPage.isFilterButtonDisplayed(),
				GlobalConstants.isBiometricConfigurationFilterButtonDisplayed);
		biometricConfigurationPage.clickOnFilterButton();
		assertTrue(biometricConfigurationPage.isFilterPanelDisplayed(),
				GlobalConstants.isBiometricConfigurationFilterPanelDisplayed);
		biometricConfigurationPage.enterConfigurationNameInFilter(configName);
		biometricConfigurationPage.clickOnApplyFilterButton();
		Thread.sleep(1500);
		assertTrue(biometricConfigurationPage.isConfigurationNamePresentInList(configName),
				"Filter by configuration name should return the target config");
	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayed()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}
}
