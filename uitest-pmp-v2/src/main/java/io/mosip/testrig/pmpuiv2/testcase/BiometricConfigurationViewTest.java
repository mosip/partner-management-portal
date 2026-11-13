package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.BiometricConfigurationPage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "BiometricConfigurationTest" }, groups = { "BiometricConfigurationViewTest" })
public class BiometricConfigurationViewTest extends BaseClass {

	private DashboardPage dashboardPage;
	private BiometricConfigurationPage biometricConfigurationPage;

	public BiometricConfigurationViewTest() {
		this.userid = GlobalConstants.PARTNER_ADMIN;
		this.password = GlobalConstants.PARTNER_PASSWORD;
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
			assertTrue(!biometricConfigurationPage.getViewStatusText().isEmpty(),
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
			assertTrue(!biometricConfigurationPage.getViewCommentText().isEmpty(),
					"Comment text should be shown when the comment is present on the View page");
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
		if (dashboardPage.isTermsAndConditionsPopupDisplayedQuick()) {
			dashboardPage.clickOnCheckbox();
			assertTrue(dashboardPage.isProceedButtonDisplayed(), GlobalConstants.isProceedButtonDisplayed);
			dashboardPage.clickOnProceedButton();
		}
	}
}