package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BiometricConfigurationPage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

/**
 * Biometric Extractor Provider Configuration (MOSIP-44595) — same structure as
 * PartnerAdminCreation (BaseClass login per method + dependsOnGroups PartnerAdminCreation).
 */
@Test(alwaysRun = true, groups = { "BiometricConfigurationTest" })
public class BiometricConfigurationTest extends BaseClass {

	private DashboardPage dashboardPage;
	private BiometricConfigurationPage biometricConfigurationPage;

	private static String biometricConfigName;

	public BiometricConfigurationTest() {
		this.userid = GlobalConstants.QAJAVA21_ADMIN_USER;
		this.password = GlobalConstants.QAJAVA21_ADMIN_PASSWORD;
	}

	@Test(priority = 1, description = "Creating Biometric Configuration")
	public void CreatingBiometricConfiguration() {
		biometricConfigName = "bioconfig" + data;

		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);

		handleTermsAndCondition();

		assertTrue(biometricConfigurationPage.isBiometricConfigurationDashboardCardDisplayed(),
				GlobalConstants.isBiometricConfigurationDashboardCardDisplayed);
		biometricConfigurationPage.clickOnBiometricConfigurationDashboardCard();

		assertTrue(biometricConfigurationPage.isBiometricConfigurationListPageDisplayed(),
				GlobalConstants.isBiometricConfigurationListPageDisplayed);
		biometricConfigurationPage.clickOnCreateBiometricConfigurationButton();

		assertTrue(biometricConfigurationPage.isCreateBiometricConfigurationFormDisplayed(),
				GlobalConstants.isCreateBiometricConfigurationFormDisplayed);
		assertFalse(biometricConfigurationPage.isSubmitButtonEnabled(),
				"Submit should be disabled before mandatory fields are filled");

		biometricConfigurationPage.enterConfigurationName(biometricConfigName);
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
		assertTrue(biometricConfigurationPage.isConfigurationNamePresentInList(biometricConfigName),
				"Created biometric configuration should appear in the list");
	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayed()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}
}
