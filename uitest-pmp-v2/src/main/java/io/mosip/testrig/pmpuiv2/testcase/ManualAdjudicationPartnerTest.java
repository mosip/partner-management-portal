package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.mosip.testrig.pmpuiv2.driver.DriverManager;
import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;
import io.mosip.testrig.pmpuiv2.kernel.util.KeycloakUserManager;
import io.mosip.testrig.pmpuiv2.pages.AuthPolicyPage;
import io.mosip.testrig.pmpuiv2.pages.BasePage;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.ManualAdjudicationPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.pages.PoliciesPage;
import io.mosip.testrig.pmpuiv2.pages.PolicyGroupPage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;

/**
 * Admin-only Manual Adjudication flow.
 * Uses shared partner-admin user pmpui-v2 (same as Partner Admin).
 * Self-contained login in BeforeClass (UserLoginTest is optional suite ordering only).
 */
@Test(groups = { "ManualAdjudicationPartnerTest" })
public class ManualAdjudicationPartnerTest extends BaseClass {

	private static final String MA_ADMIN_USER = "pmpui-v2";
	private static final String MA_ADMIN_PASSWORD = "mosip123";

	private LoginPage loginPage;
	private DashboardPage dashboardPage;
	private PoliciesPage policiesPage;
	private PolicyGroupPage policygroupPage;
	private AuthPolicyPage authPolicyPage;
	private ManualAdjudicationPartnerPage manualAdjudicationPartnerPage;
	private PartnerCertificatePage partnerCertificatePage;

	private static String maPartnerId;
	private static String maPartnerEmail;
	private static String maPolicyGroupName;
	private static String maPolicyName;

	@Override
	protected boolean useDefaultBrowserLifecycle() {
		return false;
	}

	@BeforeClass(alwaysRun = true)
	public void loginOnceAsMaAdmin() {
		KeycloakUserManager.ensureUserExists(MA_ADMIN_USER, MA_ADMIN_PASSWORD, "PARTNER_ADMIN", "PMS_ADMIN",
				"POLICYMANAGER");

		if (System.getProperty("os.name").equalsIgnoreCase("Linux") && ConfigManager.getdocker().equals("yes")) {
			System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
		} else {
			WebDriverManager.chromedriver().setup();
		}
		ChromeOptions options = new ChromeOptions();
		boolean isHeadless = ConfigManager.getheadless().equalsIgnoreCase("yes");
		if (isHeadless) {
			options.addArguments("--headless=new", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage",
					"--window-size=1920,1080");
		}
		WebDriver webDriver = new ChromeDriver(options);
		DriverManager.setDriver(webDriver);
		this.driver = webDriver;
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		if (!isHeadless) {
			driver.manage().window().maximize();
		} else {
			driver.manage().window().setSize(new Dimension(1920, 1080));
		}
		driver.get(envPathPmpUiv2);
		if (isHeadless) {
			((JavascriptExecutor) driver).executeScript("window.resizeTo(1920,1080);");
		}

		String rawData = BasePage.getPreAppend() + BasePage.getDateTime();
		BaseClass.data = rawData.substring(0, BasePage.getSplitdigit());

		loginAsManualAdjudicationPartnerAdmin();
	}

	@BeforeMethod(alwaysRun = true)
	@Override
	public void setUp(Method method) {
		LogUtil.step("Starting Test: " + method.getName());
		this.driver = DriverManager.getDriver();
	}

	@AfterMethod(alwaysRun = true)
	@Override
	public void tearDown(ITestResult result) {
		// keep single session
	}

	@AfterClass(alwaysRun = true)
	public void quitSession() {
		try {
			DriverManager.quitDriver();
		} catch (Exception ignored) {
		}
		this.driver = null;
	}

	private void loginAsManualAdjudicationPartnerAdmin() {
		loginPage = new LoginPage(driver);
		dashboardPage = new DashboardPage(driver);

		// Already logged in — keep session, but always return to Home so dashboard cards
		// (Partners / Policies / Partner-Policy Linking) are visible for the next step.
		if (dashboardPage.isUserProfileVisibleAfterLogin(3)) {
			dashboardPage.acceptTermsAndConditionsIfPresent();
			goToDashboardHome();
			return;
		}

		// Session lost / first login — always land on PMP then Keycloak login form
		if (!loginPage.isLoginPageDisplayed()) {
			driver.get(envPathPmpUiv2);
		}
		assertTrue(loginPage.isLoginPageDisplayed(),
				"Expected Keycloak Sign In page for MA admin login");

		loginPage.login(MA_ADMIN_USER, MA_ADMIN_PASSWORD);
		assertTrue(dashboardPage.isUserProfileVisibleAfterLogin(25),
				"MA admin login failed for " + MA_ADMIN_USER + " / check password mosip123 on qajava21");
		dashboardPage.acceptTermsAndConditionsIfPresent();
		KeycloakUserManager.assignRole(MA_ADMIN_USER, "PARTNER_ADMIN");
		goToDashboardHome();
	}

	private void goToDashboardHome() {
		try {
			if (dashboardPage.isSideNavigationHomeIconDisplayed()) {
				dashboardPage.clickOnHomeOptionOfHamburger();
			}
		} catch (Exception ignored) {
			driver.get(envPathPmpUiv2);
		}
	}

	@Test(priority = 1, description = "UploadingManualAdjudicationTrustCertificateStore")
	public void UploadingManualAdjudicationTrustCertificateStore() {
		dashboardPage = new DashboardPage(driver);
		partnerCertificatePage = new PartnerCertificatePage(driver);
		loginAsManualAdjudicationPartnerAdmin();

		assertTrue(dashboardPage.isCertificateTrustStoreDisplayed(),
				GlobalConstants.isCertificateTrustStoreDisplayed);
		dashboardPage.clickOnCertificateTrustStore();

		// deactivateUser* AUTH chain (O=AABBCC for client leaf). Root/Intermediate may
		// already exist on reused envs — treat upload error as already present so later
		// MA steps are not skipped.
		assertTrue(partnerCertificatePage.isUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadDeactivateUserRootCaCert();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		acceptTrustCertUploadOrAlreadyPresent(true);

		partnerCertificatePage.clickOnIntermediateCACertTab();
		assertTrue(partnerCertificatePage.isIntermediateUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadDeactivateUserIntermediateCaCert();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		acceptTrustCertUploadOrAlreadyPresent(false);
		partnerCertificatePage.clickOnHomeButton();
	}

	/**
	 * @param clickGoBackOnSuccess when true, click Go Back after success (root flow)
	 */
	private void acceptTrustCertUploadOrAlreadyPresent(boolean clickGoBackOnSuccess) {
		if (partnerCertificatePage.isUploadedSuccessfullyMessageDisplayedQuick()) {
			if (clickGoBackOnSuccess) {
				partnerCertificatePage.clickOnGoBackButton();
			}
			return;
		}
		// Reused env: Intermediate/Root already in trust store — close error and continue
		assertTrue(partnerCertificatePage.isTrustCertificateUploadErrorDisplayed(),
				"Trust certificate upload neither succeeded nor showed already-present error");
		try {
			partnerCertificatePage.clickOnErrorCloseButton();
		} catch (Exception ignored) {
		}
		try {
			partnerCertificatePage.clickOncertificateUploadCloseButton();
		} catch (Exception ignored) {
		}
		if (clickGoBackOnSuccess) {
			try {
				partnerCertificatePage.clickOnGoBackButton();
			} catch (Exception ignored) {
			}
		}
	}

	@Test(priority = 2, description = "CreatingManualAdjudicationPolicyGroup", dependsOnMethods = "UploadingManualAdjudicationTrustCertificateStore")
	public void CreatingManualAdjudicationPolicyGroup() {
		maPolicyGroupName = "magroup" + data;
		maPolicyName = "mapolicy" + data;

		manualAdjudicationPartnerPage = new ManualAdjudicationPartnerPage(driver);
		policygroupPage = new PolicyGroupPage(driver);
		loginAsManualAdjudicationPartnerAdmin();

		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		dashboardPage.clickOnPoliciesTitle();
		policiesPage = new PoliciesPage(driver);

		assertTrue(policiesPage.isPoliciesPageDisplayed(), GlobalConstants.isPoliciesPageDisplayed);

		policygroupPage.clickOnCreatePolicyGroupButton();
		assertTrue(policygroupPage.isPolicyGroupNameTextboxDisplayed(),
				GlobalConstants.isPolicyGroupNameTextboxDisplayed);
		policygroupPage.enterPolicyGroupName(maPolicyGroupName);
		policygroupPage.enterPolicyGroupNameDescription(GlobalConstants.MANUAL_ADJUDICATION_POLICYGROUP_DESC);
		policygroupPage.clickOnSubmitButton();
		assertTrue(policygroupPage.isPolicyGroupSuccessMessageDisplayed(),
				GlobalConstants.isPolicyGroupSuccessMessageDisplayed);
		policygroupPage.clickOnSuccessHomeButton();
		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
	}

	@Test(priority = 3, description = "CreatingManualAdjudicationAuthPolicy", dependsOnMethods = "CreatingManualAdjudicationPolicyGroup")
	public void CreatingManualAdjudicationAuthPolicy() {
		authPolicyPage = new AuthPolicyPage(driver);
		loginAsManualAdjudicationPartnerAdmin();

		assertTrue(dashboardPage.isPoliciesTitleDisplayed(), GlobalConstants.isPoliciesTitleDisplayed);
		dashboardPage.clickOnPoliciesTitle();
		policiesPage = new PoliciesPage(driver);

		assertTrue(policiesPage.isPoliciesPageDisplayed(), GlobalConstants.isPoliciesPageDisplayed);
		policiesPage.clickOnAuthPolicyTab();

		authPolicyPage.clickOnCreateAuthPolicyButton();
		authPolicyPage.selectPolicyGroupDropdown(maPolicyGroupName);
		authPolicyPage.enterPolicyName(maPolicyName);
		authPolicyPage.enterpolicyDescription(GlobalConstants.MANUAL_ADJUDICATION_POLICY_01_DESCRIPTION);
		authPolicyPage.uploadPolicyData();
		authPolicyPage.clickOnSaveAsDraftButton();
		authPolicyPage.clickOnGoBackButton();

		authPolicyPage.clickOnFilterButton();
		authPolicyPage.enterPolicyNameInFilter(maPolicyName);
		authPolicyPage.clickOnApplyFilterButton();
		authPolicyPage.clickOnActionButton();
		authPolicyPage.clickOnPolicyPublishButton();
		authPolicyPage.clickOnPublishPolicyButton();
		authPolicyPage.clickOnSuccessMsgCloseButton();
		authPolicyPage.clickOnPublishPolicyCloseButton();
		authPolicyPage.clickOnHomeButton();
	}

	@Test(priority = 4, description = "CreatingManualAdjudicationPartner", dependsOnMethods = "CreatingManualAdjudicationAuthPolicy")
	public void CreatingManualAdjudicationPartner() {
		maPartnerId = "ma" + data;
		maPartnerEmail = maPartnerId + "@gmail.com";

		manualAdjudicationPartnerPage = new ManualAdjudicationPartnerPage(driver);
		dashboardPage = new DashboardPage(driver);
		loginAsManualAdjudicationPartnerAdmin();

		assertTrue(dashboardPage.isPartnersTitleDisplayed(), GlobalConstants.isPartnersTitleDisplayed);
		dashboardPage.clickOnPartners();
		manualAdjudicationPartnerPage.clickOnCreatePartnerButton();
		assertTrue(manualAdjudicationPartnerPage.isPartnerTypeDropdownDisplayed(),
				GlobalConstants.isPartnerTypeDropdownDisplayed);
		manualAdjudicationPartnerPage.selectManualAdjudicationPartnerInPartnerTypeDropdown();
		manualAdjudicationPartnerPage.selectPolicyGroupDropdown(maPolicyGroupName);
		assertTrue(manualAdjudicationPartnerPage.isPolicyGroupSelected(maPolicyGroupName),
				"Policy group was not selected during Manual Adjudication partner creation");

		manualAdjudicationPartnerPage.enterAddress("0" + data);
		manualAdjudicationPartnerPage.enterOrganizationName(GlobalConstants.ORGANISATION_NAME);
		manualAdjudicationPartnerPage.enterPhoneNumber("9067853490");
		manualAdjudicationPartnerPage.enterEmailAdrress(maPartnerEmail);
		manualAdjudicationPartnerPage.enterUsername(maPartnerId);
		manualAdjudicationPartnerPage.selectNotificationLanguageDropdown();
		manualAdjudicationPartnerPage.clickOnSubmitButton();
		assertTrue(manualAdjudicationPartnerPage.isPartnerCreatedSuccessfully(),
				"Manual Adjudication partner creation failed");

		partnerCertificatePage = new PartnerCertificatePage(driver);
		manualAdjudicationPartnerPage.clickOnUploadManualAdjudicationPartnerCertificateButton();
		manualAdjudicationPartnerPage.uploadManualAdjudicationPartnerCertificate();
		manualAdjudicationPartnerPage.clickOnUploadSubmitButton();
		assertTrue(isPartnerCertificateUploadSuccessful(),
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		manualAdjudicationPartnerPage.clickOnUploadCloseButton();

		assertTrue(manualAdjudicationPartnerPage.isSubTitleListDisplayed(), GlobalConstants.isSubTitleListDisplayed);
		manualAdjudicationPartnerPage.clickOnFilterButton();
		manualAdjudicationPartnerPage.enterPartnerIdInFilter(maPartnerId);
		manualAdjudicationPartnerPage.clickOnApplyFiltersBtn();
		assertTrue(manualAdjudicationPartnerPage.isActivatedPartnersDisplayed(),
				"Manual Adjudication partner not found in partners list");

		manualAdjudicationPartnerPage.clickOnActionsButton();
		assertTrue(manualAdjudicationPartnerPage.isViewButtonsDisplayed(), GlobalConstants.isViewButtonsDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isUploadCertificateFromListButtonDisplayed(),
				GlobalConstants.isUploadPartnerCertificateButtonDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isUploadCertificateFromListButtonEnabled(),
				"Upload/Re-upload Certificate should be enabled");
		assertTrue(manualAdjudicationPartnerPage.isSelectPolicyGroupFromListButtonDisplayed(),
				"Select Policy Group action should be visible");
		// MA partners never get Select Policy Group enabled (only MISP without policy group).
		assertFalse(manualAdjudicationPartnerPage.isSelectPolicyGroupButtonEnabled(),
				"Select Policy Group should be disabled for Manual Adjudication partner");
		assertTrue(manualAdjudicationPartnerPage.isDeactivateButtonsDisplayed(),
				GlobalConstants.isDeactivateButtonsDisplayed);

		manualAdjudicationPartnerPage.clickOnViewPartnerDetailsScreen();
		assertTrue(manualAdjudicationPartnerPage.isViewPartnersDetailsPageDisplayed(),
				GlobalConstants.isViewPartnersDetailsPageDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isPartnerIdDisplayed(), GlobalConstants.isPartnerIdDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isPartnerStatusInViewPartnerPageDisplayed(),
				GlobalConstants.isPartnerStatusInViewPartnerPageDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isPartnerCreatedDateInViewPartnerPageDisplayed(),
				GlobalConstants.isPartnerCreatedDateInViewPartnerPageDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isViewPartnerDetailsPartnerTypeLabelDisplayed(),
				GlobalConstants.isPartnerTypeInViewPartnerPageDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isOrganisationNameInViewPartnerPageDisplayed(),
				GlobalConstants.isOrganisationNameInViewPartnerPageDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isViewPartnerDetailsEmailLabelDisplayed(),
				GlobalConstants.isEmailAddressHeaderTagDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isViewPartnerDetailsPolicyGroupLabelDisplayed(),
				GlobalConstants.isPolicyGroupHeaderTagDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isPartnerCertificateInViewPartnerDetailsDisplayed(),
				GlobalConstants.isPartnerCertificateInViewPartnerDetailsPageDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isDownloadCertificateButtonDisplayed(),
				GlobalConstants.isDownloadCertificateButtonDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isExpiryDateTimeDisplayed(), GlobalConstants.isExpiryDateTimeDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isTimeOfUploadDisplayed(),
				GlobalConstants.isUploadTimeLabelInCertificateDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isGobackButtonInViewPatnerPageDisplayed(),
				GlobalConstants.isGobackButtonInViewPatnerPageDisplayed);
		manualAdjudicationPartnerPage.clickOngobackButtonInPartnerDetailsPage();

		manualAdjudicationPartnerPage.clickOnActionsButton();
		manualAdjudicationPartnerPage.clickOnUploadCertificateFromPartnersList();
		manualAdjudicationPartnerPage.uploadManualAdjudicationPartnerCertificate();
		manualAdjudicationPartnerPage.clickOnUploadSubmitButton();
		assertTrue(isPartnerCertificateUploadSuccessful(),
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		manualAdjudicationPartnerPage.clickOnUploadCloseButton();
	}

	private boolean isPartnerCertificateUploadSuccessful() {
		if (partnerCertificatePage.isCertificateUploadSuccessMessageDisplayedQuick()) {
			return true;
		}
		if (partnerCertificatePage.isPartnerCertificateUploadErrorDisplayed()) {
			return false;
		}
		return partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed();
	}

	@Test(priority = 5, description = "CreatingManualAdjudicationPartnerPolicyLinking", dependsOnMethods = "CreatingManualAdjudicationPartner")
	public void CreatingManualAdjudicationPartnerPolicyLinking() {
		manualAdjudicationPartnerPage = new ManualAdjudicationPartnerPage(driver);
		loginAsManualAdjudicationPartnerAdmin();

		assertTrue(dashboardPage.isPartnerPolicyLinkingTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingTitleDisplayed);
		dashboardPage.clickOnPartnerPolicyLinkingTitle();

		assertTrue(manualAdjudicationPartnerPage.isPartnerPolicyLinkingTitleDisplayed(),
				GlobalConstants.isPartnerPolicyLinkingPageDisplayed);

		manualAdjudicationPartnerPage.clickOnRequestPolicyButton();
		assertTrue(manualAdjudicationPartnerPage.isRequestPolicyPartnerTypeDropdownDisplayed(),
				GlobalConstants.isPartnerTypeDropdownDisplayed);
		manualAdjudicationPartnerPage.selectManualAdjudicationPartnerInRequestPolicyDropdown();
		manualAdjudicationPartnerPage.selectPartnerIDPartnerTypeDropdown(maPartnerId);
		assertTrue(manualAdjudicationPartnerPage.isPolicyNameDropdownDisplayed(),
				GlobalConstants.isPolicyNameDropdownDisplayed);
		manualAdjudicationPartnerPage.selectPolicyNamePartnerTypeDropdown(maPolicyName);
		manualAdjudicationPartnerPage.entercomments(GlobalConstants.DEFAULT_Comments);
		manualAdjudicationPartnerPage.clickOnRequestPolicySubmitButton();
		// After submit, admin lands on confirmation with Approve + Go Back
		manualAdjudicationPartnerPage.approvePolicyRequest();

		assertTrue(manualAdjudicationPartnerPage.isPartnerPolicyLinkingTitleDisplayed(),
				"Expected Partner - Policy Linking list after approving policy request");
	}

	@Test(priority = 6, description = "DeactivatingManualAdjudicationPartner", dependsOnMethods = "CreatingManualAdjudicationPartnerPolicyLinking")
	public void DeactivatingManualAdjudicationPartner() {
		manualAdjudicationPartnerPage = new ManualAdjudicationPartnerPage(driver);
		loginAsManualAdjudicationPartnerAdmin();

		assertTrue(dashboardPage.isPartnersTitleDisplayed(), GlobalConstants.isPartnersTitleDisplayed);
		dashboardPage.clickOnPartners();
		assertTrue(manualAdjudicationPartnerPage.isSubTitleListDisplayed(), GlobalConstants.isSubTitleListDisplayed);

		manualAdjudicationPartnerPage.clickOnFilterButton();
		manualAdjudicationPartnerPage.enterPartnerIdInFilter(maPartnerId);
		manualAdjudicationPartnerPage.clickOnStatusFilter();
		manualAdjudicationPartnerPage.clickActivatedButton();
		manualAdjudicationPartnerPage.clickOnApplyFiltersBtn();
		assertTrue(manualAdjudicationPartnerPage.isActivatedPartnersDisplayed(),
				"Active Manual Adjudication partner not found before deactivate");

		manualAdjudicationPartnerPage.clickOnActionsButton();
		manualAdjudicationPartnerPage.clickOnDeactivateButton();
		assertTrue(manualAdjudicationPartnerPage.isDeactivatePartnerHeaderDisplayed(),
				GlobalConstants.isDeactivatePartnerHeaderDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isDeactivatePartnerDescriptionDisplayed(),
				GlobalConstants.isDeactivatePartnerDescriptionDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isDeactivateCancelButtonDisplayed(),
				GlobalConstants.isDeactivateCancelButtonDisplayed);
		assertTrue(manualAdjudicationPartnerPage.isDeactivateConfirmButtonDisplayed(),
				GlobalConstants.isDeactivateConfirmButtonDisplayed);
		manualAdjudicationPartnerPage.clickOnConfirmButton();
		manualAdjudicationPartnerPage.waitForDeactivatePopupToClose();
		manualAdjudicationPartnerPage.clickOnFilterResetButton();
		manualAdjudicationPartnerPage.clickOnFilterButton();
		manualAdjudicationPartnerPage.enterPartnerIdInFilter(maPartnerId);
		manualAdjudicationPartnerPage.clickOnStatusFilter();
		manualAdjudicationPartnerPage.clickOnDeActivatedStatusInFilters();
		manualAdjudicationPartnerPage.clickOnApplyFiltersBtn();
		assertTrue(manualAdjudicationPartnerPage.isActivatedPartnersDisplayed(),
				"Deactivated Manual Adjudication partner not found");
		assertTrue(manualAdjudicationPartnerPage.isDeactivatedStatusOnPartnerRowDisplayed(),
				GlobalConstants.isDeactivatedStatusDisplayed);

		manualAdjudicationPartnerPage.clickOnActionsButton();
		assertTrue(manualAdjudicationPartnerPage.isViewButtonsDisplayed(), GlobalConstants.isViewButtonsDisplayed);
		assertFalse(manualAdjudicationPartnerPage.isUploadCertificateFromListButtonEnabled(),
				"Upload/Re-upload Certificate should be disabled for deactivated partner");
		assertTrue(manualAdjudicationPartnerPage.isDeactivateButtonDisabled(),
				GlobalConstants.isDeactivateButtonDisabled);
	}
}
