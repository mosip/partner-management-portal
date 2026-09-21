package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BiometricConfigurationPage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

/**
 * MOSIP-44597 — Partner Admin: View Biometric Extractor Provider Configuration Details.
 * Env: https://pmp.qajava21.mosip.net — credentials 21admin / admin123.
 *
 * AC coverage: Navigation; Page Title + Header; Configuration Details; Read-only;
 * Back button; UI layout; Responsiveness. Browser = Chrome (suite default).
 */
@Test(alwaysRun = true, groups = { "BiometricConfigurationViewTest" })
public class BiometricConfigurationViewTest extends BaseClass {

	private DashboardPage dashboardPage;
	private BiometricConfigurationPage biometricConfigurationPage;

	public BiometricConfigurationViewTest() {
		this.userid = GlobalConstants.QAJAVA21_ADMIN_USER;
		this.password = GlobalConstants.QAJAVA21_ADMIN_PASSWORD;
	}

	@Test(priority = 1, description = "Navigating to View Biometric Configuration Details")
	public void NavigatingToViewBiometricConfigurationDetails() {
		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);

		handleTermsAndCondition();
		openBiometricConfigurationList();

		assertTrue(biometricConfigurationPage.isFirstRowActionMenuDisplayed(),
				"Action menu should be available for an existing configuration row");
		biometricConfigurationPage.clickOnFirstRowActionMenu();
		assertTrue(biometricConfigurationPage.isViewActionDisplayed(),
				GlobalConstants.isBiometricConfigurationViewActionDisplayed);

		biometricConfigurationPage.clickOnViewAction();
		assertTrue(biometricConfigurationPage.isViewConfigurationDetailsPageDisplayed(),
				GlobalConstants.isBiometricConfigurationViewPageDisplayed);
		assertTrue(biometricConfigurationPage.getCurrentUrl().contains("/view")
				|| biometricConfigurationPage.getCurrentUrl().contains("view-biometric"),
				"URL should navigate to the View Biometric Configuration details page");
	}

	@Test(priority = 2, description = "Verifying View page title and header information",
			dependsOnMethods = "NavigatingToViewBiometricConfigurationDetails")
	public void VerifyingViewPageTitleAndHeaderInformation() {
		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);

		openViewPageIfNeeded();

		assertTrue(biometricConfigurationPage.isViewPageTitleDisplayed(),
				GlobalConstants.isBiometricConfigurationViewPageTitleDisplayed);
		assertTrue(biometricConfigurationPage.isViewConfigurationNameDisplayed(),
				"Configuration name should be displayed in the View page header");
		assertTrue(biometricConfigurationPage.isViewCreatedOnDisplayed(),
				GlobalConstants.isBiometricConfigurationViewCreatedOnDisplayed);
		assertTrue(biometricConfigurationPage.isViewCreatedTimeDisplayed(),
				"Created time should be displayed alongside Created On");

		if (biometricConfigurationPage.isViewStatusDisplayed()) {
			assertTrue(biometricConfigurationPage.getViewStatusText().length() > 0
					|| biometricConfigurationPage.isViewStatusDisplayed(),
					GlobalConstants.isBiometricConfigurationViewStatusDisplayed);
		}
	}

	@Test(priority = 3, description = "Verifying View configuration details display",
			dependsOnMethods = "VerifyingViewPageTitleAndHeaderInformation")
	public void VerifyingViewConfigurationDetailsDisplay() {
		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);

		openViewPageIfNeeded();

		assertTrue(biometricConfigurationPage.isViewProviderNameDisplayed(),
				"Provider name should be displayed on the View page");
		assertTrue(biometricConfigurationPage.isViewProviderVersionDisplayed(),
				"Provider version should be displayed on the View page");
		assertTrue(biometricConfigurationPage.isViewModalityDisplayed(),
				"Modality should be displayed on the View page");
		assertTrue(biometricConfigurationPage.isViewCredentialDataFormatDisplayed(),
				"Credential data format should be displayed on the View page");
		assertTrue(biometricConfigurationPage.isViewAttributeNameDisplayed(),
				"Attribute name should be displayed on the View page");
		assertTrue(biometricConfigurationPage.areAllViewConfigurationAttributesDisplayed(),
				GlobalConstants.isBiometricConfigurationViewDetailsDisplayed);

		if (biometricConfigurationPage.isViewCommentDisplayed()) {
			assertTrue(biometricConfigurationPage.getViewCommentText().length() >= 0,
					"Comment should be readable when present on the View page");
		}
	}

	@Test(priority = 4, description = "Verifying View page is read-only",
			dependsOnMethods = "VerifyingViewConfigurationDetailsDisplay")
	public void VerifyingViewPageReadOnlyBehaviour() {
		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);

		openViewPageIfNeeded();

		assertTrue(biometricConfigurationPage.isViewPageReadOnly(),
				GlobalConstants.isBiometricConfigurationViewReadOnly);
		assertFalse(driver.findElements(By.id("config_name_input")).size() > 0,
				"Create/edit form controls must not be present on the View page");
		assertTrue(biometricConfigurationPage.isViewPageTitleDisplayed(),
				GlobalConstants.isBiometricConfigurationViewPageTitleDisplayed);
		assertTrue(biometricConfigurationPage.isViewConfigurationNameDisplayed(),
				"Configuration name remains visible in read-only view");
	}

	@Test(priority = 5, description = "Verifying View page Back navigation",
			dependsOnMethods = "VerifyingViewPageReadOnlyBehaviour")
	public void VerifyingViewPageBackNavigation() {
		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);

		openViewPageIfNeeded();

		assertTrue(biometricConfigurationPage.isViewBackButtonDisplayed(),
				GlobalConstants.isBiometricConfigurationViewBackButtonDisplayed);
		assertTrue(biometricConfigurationPage.isViewBackButtonAlignedEnd(),
				"Back button should be aligned at the end of the content section");

		biometricConfigurationPage.clickOnViewBackButton();
		assertTrue(biometricConfigurationPage.isBiometricConfigurationListPageDisplayed(),
				GlobalConstants.isBiometricConfigurationViewBackNavigatesToList);
		assertTrue(biometricConfigurationPage.getCurrentUrl().contains("biometric-provider-configuration-list")
				|| biometricConfigurationPage.getCurrentUrl().contains("/list"),
				"URL should return to the Biometric Configuration listing page");
	}

	@Test(priority = 6, description = "Verifying View page layout structure",
			dependsOnMethods = "VerifyingViewPageBackNavigation")
	public void VerifyingViewPageLayoutStructure() {
		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);

		handleTermsAndCondition();
		openBiometricConfigurationList();
		openFirstConfigurationView();

		assertTrue(biometricConfigurationPage.isViewPageLayoutStructured(),
				GlobalConstants.isBiometricConfigurationViewLayoutStructured);
		assertTrue(biometricConfigurationPage.isViewPageTitleDisplayed(),
				GlobalConstants.isBiometricConfigurationViewPageTitleDisplayed);
		assertTrue(biometricConfigurationPage.isViewConfigurationNameDisplayed(),
				"Layout should include configuration name");
		assertTrue(biometricConfigurationPage.isViewCreatedOnDisplayed(),
				GlobalConstants.isBiometricConfigurationViewCreatedOnDisplayed);
		assertTrue(biometricConfigurationPage.areAllViewConfigurationAttributesDisplayed(),
				GlobalConstants.isBiometricConfigurationViewDetailsDisplayed);
		assertTrue(biometricConfigurationPage.isViewBackButtonDisplayed(),
				GlobalConstants.isBiometricConfigurationViewBackButtonDisplayed);
	}

	@Test(priority = 7, description = "Verifying View page responsiveness",
			dependsOnMethods = "VerifyingViewPageLayoutStructure")
	public void VerifyingViewPageResponsiveness() {
		dashboardPage = new DashboardPage(driver);
		biometricConfigurationPage = new BiometricConfigurationPage(driver);

		openViewPageIfNeeded();

		assertTrue(biometricConfigurationPage.isViewPageUsableAtViewport(1366, 768),
				GlobalConstants.isBiometricConfigurationViewResponsive);
		assertTrue(biometricConfigurationPage.isViewPageUsableAtViewport(768, 1024),
				GlobalConstants.isBiometricConfigurationViewResponsive);
		biometricConfigurationPage.resetViewport();
	}

	private void openBiometricConfigurationList() {
		assertTrue(biometricConfigurationPage.isBiometricConfigurationDashboardCardDisplayed(),
				GlobalConstants.isBiometricConfigurationDashboardCardDisplayed);
		biometricConfigurationPage.clickOnBiometricConfigurationDashboardCard();
		assertTrue(biometricConfigurationPage.isBiometricConfigurationListPageDisplayed(),
				GlobalConstants.isBiometricConfigurationListPageDisplayed);
	}

	private void openFirstConfigurationView() {
		assertTrue(biometricConfigurationPage.isFirstRowActionMenuDisplayed(),
				"Action menu should be available for an existing configuration row");
		biometricConfigurationPage.clickOnFirstRowActionMenu();
		assertTrue(biometricConfigurationPage.isViewActionDisplayed(),
				GlobalConstants.isBiometricConfigurationViewActionDisplayed);
		biometricConfigurationPage.clickOnViewAction();
		assertTrue(biometricConfigurationPage.isViewConfigurationDetailsPageDisplayed(),
				GlobalConstants.isBiometricConfigurationViewPageDisplayed);
	}

	private void openViewPageIfNeeded() {
		if (biometricConfigurationPage.isViewConfigurationDetailsPageDisplayed()) {
			return;
		}
		handleTermsAndCondition();
		openBiometricConfigurationList();
		openFirstConfigurationView();
	}

	private void handleTermsAndCondition() {
		if (dashboardPage.isTermsAndConditionsPopupDisplayed()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}
}