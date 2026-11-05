package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.kernel.util.KeycloakUserManager;
import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerAdminPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "OnlineVerificationPartnerDeactivateTest" })
public class OnlineVerificationPartnerDeactivateTest extends BaseClass {

	private static String activePartnerId;
	private static String deactivatePartnerId;
	private static String organisationName;
	private static String phoneNumber;
	private static String deactivateEmail;
	private static String deactivatePolicyGroup;

	@Test(priority = 1, description = "Create active Online Verification Partners used by the deactivate cases")
	public void createOnlineVerificationPartnersForDeactivate() {
		String suffix = BaseClass.data;
		activePartnerId = "ovpac" + suffix;
		deactivatePartnerId = "ovpde" + suffix;
		organisationName = GlobalConstants.ORGANISATION_NAME;
		phoneNumber = GlobalConstants.ABIS_CONTACT_NUMBER;
		deactivateEmail = "ovpde" + suffix + "@test.com";

		uploadAuthTrustCertificates();
		MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
		createPartner(mispPartnerPage, activePartnerId, "ovpac" + suffix + "@test.com");
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
		uploadPartnerCertificate(mispPartnerPage);

		mispPartnerPage = navigateToCreatePartnerPage();
		createPartner(mispPartnerPage, deactivatePartnerId, deactivateEmail);
		deactivatePolicyGroup = mispPartnerPage.getSelectedPolicyGroupText();
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
		uploadPartnerCertificate(mispPartnerPage);
	}

	@Test(priority = 2, description = "TC_1855_01: Deactivate is available for an ACTIVE Online Verification Partner", dependsOnMethods = "createOnlineVerificationPartnersForDeactivate")
	public void deactivateOptionDisplayedForActivePartner() {
		PartnerAdminPage partnerAdminPage = openActionMenu(activePartnerId);

		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.PARTNER_STATUS_ACTIVE,
				GlobalConstants.OVP_DEACTIVATE_OPTION_FOR_ACTIVE);
		assertEquals(partnerAdminPage.getFirstRowPartnerType(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
				GlobalConstants.OVP_DEACTIVATE_OPTION_FOR_ACTIVE);
		assertTrue(partnerAdminPage.isViewButtonsDisplayed(), GlobalConstants.OVP_DEACTIVATE_OPTION_FOR_ACTIVE);
		assertTrue(partnerAdminPage.isViewOptionEnabled(), GlobalConstants.OVP_DEACTIVATE_OPTION_FOR_ACTIVE);
		assertTrue(partnerAdminPage.isDeactivateButtonsDisplayed(), GlobalConstants.OVP_DEACTIVATE_OPTION_FOR_ACTIVE);
		assertTrue(partnerAdminPage.isDeactivateOptionEnabled(), GlobalConstants.OVP_DEACTIVATE_OPTION_FOR_ACTIVE);
	}

	@Test(priority = 3, description = "TC_1855_03 to TC_1855_06: confirmation popup shows Confirm, Cancel, Partner ID, and Organisation", dependsOnMethods = "createOnlineVerificationPartnersForDeactivate")
	public void confirmationPopupShowsPartnerAndOrganisation() {
		PartnerAdminPage partnerAdminPage = openActionMenu(activePartnerId);
		partnerAdminPage.clickOnDeactivateOptionInActionMenu();

		assertTrue(partnerAdminPage.isDeactivatePopupHeaderDisplayed(),
				GlobalConstants.OVP_DEACTIVATE_POPUP_DISPLAYED);
		assertTrue(partnerAdminPage.isDeactivatePopupDescriptionDisplayed(),
				GlobalConstants.OVP_DEACTIVATE_POPUP_DISPLAYED);
		assertTrue(partnerAdminPage.isDeactivatePopupConfirmButtonDisplayed(),
				GlobalConstants.OVP_DEACTIVATE_POPUP_BUTTONS);
		assertTrue(partnerAdminPage.isDeactivatePopupCancelButtonDisplayed(),
				GlobalConstants.OVP_DEACTIVATE_POPUP_BUTTONS);
		assertTrue(partnerAdminPage.isDeactivatePopupConfirmButtonEnabled(),
				GlobalConstants.OVP_DEACTIVATE_POPUP_BUTTONS);
		assertTrue(partnerAdminPage.isDeactivatePopupCancelButtonEnabled(),
				GlobalConstants.OVP_DEACTIVATE_POPUP_BUTTONS);

		String expectedTitle = String.format(GlobalConstants.DEACTIVATE_PARTNER_POPUP_TITLE, activePartnerId,
				organisationName);
		assertEquals(partnerAdminPage.getDeactivatePopupTitle(), expectedTitle,
				GlobalConstants.OVP_DEACTIVATE_POPUP_PARTNER_ID);
		assertTrue(partnerAdminPage.getDeactivatePopupTitle().contains(activePartnerId),
				GlobalConstants.OVP_DEACTIVATE_POPUP_PARTNER_ID);
		assertTrue(partnerAdminPage.getDeactivatePopupTitle().contains(organisationName),
				GlobalConstants.OVP_DEACTIVATE_POPUP_ORGANISATION);
		assertEquals(partnerAdminPage.getDeactivatePopupSubTitle(),
				GlobalConstants.DEACTIVATE_PARTNER_POPUP_DESCRIPTION, GlobalConstants.OVP_DEACTIVATE_POPUP_DISPLAYED);

		partnerAdminPage.clickOnDeactivatePopupCancelButton();
	}

	@Test(priority = 4, description = "TC_1855_12, TC_1855_13: Cancel closes the popup and leaves the partner Active", dependsOnMethods = "createOnlineVerificationPartnersForDeactivate")
	public void cancelClosesPopupAndKeepsActiveStatus() {
		PartnerAdminPage partnerAdminPage = openActionMenu(activePartnerId);
		String statusBeforeCancel = partnerAdminPage.getFirstRowPartnerStatus();

		partnerAdminPage.clickOnDeactivateOptionInActionMenu();
		assertTrue(partnerAdminPage.isDeactivatePopupHeaderDisplayed(),
				GlobalConstants.OVP_DEACTIVATE_POPUP_DISPLAYED);
		partnerAdminPage.clickOnDeactivatePopupCancelButton();

		assertFalse(partnerAdminPage.isDeactivatePopupHeaderDisplayedQuick(),
				GlobalConstants.OVP_DEACTIVATE_CANCEL_CLOSES_POPUP);
		assertEquals(partnerAdminPage.getPageTitleText(), GlobalConstants.PARTNERS_PAGE_TITLE,
				GlobalConstants.OVP_DEACTIVATE_CANCEL_CLOSES_POPUP);
		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.PARTNER_STATUS_ACTIVE,
				GlobalConstants.OVP_DEACTIVATE_CANCEL_KEEPS_ACTIVE);
		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), statusBeforeCancel,
				GlobalConstants.OVP_DEACTIVATE_CANCEL_KEEPS_ACTIVE);
		assertEquals(partnerAdminPage.getFirstRowPartnerId(), activePartnerId,
				GlobalConstants.OVP_DEACTIVATE_CANCEL_KEEPS_ACTIVE);
	}

	@Test(priority = 5, description = "TC_1855_17: Active Online Verification Partners remain eligible for deactivation", dependsOnMethods = "createOnlineVerificationPartnersForDeactivate")
	public void activeFilterListsPartnersEligibleForDeactivation() {
		PartnerAdminPage partnerAdminPage = openPartnerList();
		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.clickOnPartnerTypeDropdown();
		partnerAdminPage.selectPartnerTypeFilterOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		partnerAdminPage.clickOnStatusFilter();
		partnerAdminPage.selectStatusFilterOption(GlobalConstants.PARTNER_STATUS_ACTIVE);
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isPartnerListLoadedByPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
				GlobalConstants.OVP_ACTIVE_FILTER_ELIGIBLE);
		assertTrue(partnerAdminPage.areAllVisiblePartnerTypes(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
				GlobalConstants.OVP_ACTIVE_FILTER_ELIGIBLE);
		assertTrue(partnerAdminPage.areAllVisiblePartnerStatuses(GlobalConstants.PARTNER_STATUS_ACTIVE),
				GlobalConstants.OVP_ACTIVE_FILTER_ELIGIBLE);
		assertTrue(partnerAdminPage.getPartnerIdColumnValues().contains(activePartnerId),
				GlobalConstants.OVP_ACTIVE_FILTER_ELIGIBLE);

		partnerAdminPage.clickOnActionsButton();
		assertTrue(partnerAdminPage.isDeactivateOptionEnabled(), GlobalConstants.OVP_ACTIVE_FILTER_ELIGIBLE);
	}

	@Test(priority = 6, description = "TC_1855_02, TC_1855_07, TC_1855_08, TC_1855_09, TC_1855_24: confirm deactivation, stay on the list, and keep sort order", dependsOnMethods = "createOnlineVerificationPartnersForDeactivate")
	public void confirmDeactivationKeepsListOrderAndStatus() {
		PartnerAdminPage partnerAdminPage = openPartnerList();
		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.clickOnPartnerTypeDropdown();
		partnerAdminPage.selectPartnerTypeFilterOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		partnerAdminPage.clickOnApplyFiltersBtn();
		assertTrue(partnerAdminPage.isPartnerListLoaded(deactivatePartnerId),
				GlobalConstants.OVP_SORT_ORDER_UNCHANGED);

		List<String> partnerIdsBefore = partnerAdminPage.getPartnerIdColumnValues();
		assertEquals(partnerAdminPage.getFirstRowPartnerId(), deactivatePartnerId,
				GlobalConstants.OVP_SORT_ORDER_UNCHANGED);
		assertEquals(partnerAdminPage.getFirstRowPartnerType(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
				GlobalConstants.OVP_DEACTIVATE_SUCCESS);
		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.PARTNER_STATUS_ACTIVE,
				GlobalConstants.OVP_DEACTIVATE_SUCCESS);

		partnerAdminPage.clickOnActionsButton();
		assertTrue(partnerAdminPage.isDeactivateOptionEnabled(), GlobalConstants.OVP_DEACTIVATE_SUCCESS);
		partnerAdminPage.clickOnDeactivateOptionInActionMenu();
		assertTrue(partnerAdminPage.isDeactivatePopupHeaderDisplayed(),
				GlobalConstants.OVP_DEACTIVATE_POPUP_DISPLAYED);
		partnerAdminPage.clickOnDeactivatePopupConfirmButton();

		assertEquals(partnerAdminPage.getPageTitleText(), GlobalConstants.PARTNERS_PAGE_TITLE,
				GlobalConstants.OVP_REMAINS_ON_PARTNER_LIST);
		assertTrue(partnerAdminPage.isSubTitleListDisplayed(), GlobalConstants.OVP_REMAINS_ON_PARTNER_LIST);
		assertTrue(partnerAdminPage.isFirstRowStatus(GlobalConstants.DEACTIVATED),
				GlobalConstants.OVP_STATUS_WITHOUT_REFRESH);
		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.DEACTIVATED,
				GlobalConstants.OVP_DEACTIVATE_SUCCESS);
		assertEquals(partnerAdminPage.getFirstRowPartnerId(), deactivatePartnerId,
				GlobalConstants.OVP_SORT_ORDER_UNCHANGED);
		assertEquals(partnerAdminPage.getPartnerIdColumnValues(), partnerIdsBefore,
				GlobalConstants.OVP_SORT_ORDER_UNCHANGED);
		assertEquals(partnerAdminPage.getFirstRowPartnerType(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
				GlobalConstants.OVP_DEACTIVATE_SUCCESS);
	}

	@Test(priority = 7, description = "TC_1855_10, TC_1855_11: deactivated partner stays available in search and the partner-type filter", dependsOnMethods = "confirmDeactivationKeepsListOrderAndStatus")
	public void deactivatedPartnerIsFoundBySearchAndFilter() {
		PartnerAdminPage partnerAdminPage = openPartnerList();
		filterByPartnerId(partnerAdminPage, deactivatePartnerId);
		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.DEACTIVATED,
				GlobalConstants.OVP_DEACTIVATED_IN_SEARCH);
		assertEquals(partnerAdminPage.getFirstRowPartnerType(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
				GlobalConstants.OVP_DEACTIVATED_IN_SEARCH);

		partnerAdminPage.clickOnFilterResetButton();
		new WebDriverWait(driver, Duration.ofSeconds(20))
				.until(ExpectedConditions.elementToBeClickable(By.id("filter_btn")));
		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.clickOnPartnerTypeDropdown();
		partnerAdminPage.selectPartnerTypeFilterOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isPartnerListLoadedByPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
				GlobalConstants.OVP_FILTER_AFTER_DEACTIVATION);
		assertTrue(partnerAdminPage.areAllVisiblePartnerTypes(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
				GlobalConstants.OVP_FILTER_AFTER_DEACTIVATION);
		assertTrue(partnerAdminPage.getPartnerIdColumnValues().contains(deactivatePartnerId),
				GlobalConstants.OVP_FILTER_AFTER_DEACTIVATION);
		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.DEACTIVATED,
				GlobalConstants.OVP_FILTER_AFTER_DEACTIVATION);
	}

	@Test(priority = 8, description = "TC_1855_14, TC_1855_15: View stays available and only the status changes", dependsOnMethods = "confirmDeactivationKeepsListOrderAndStatus")
	public void viewKeepsDetailsAndShowsDeactivatedStatus() {
		PartnerAdminPage partnerAdminPage = openPartnerList();
		filterByPartnerId(partnerAdminPage, deactivatePartnerId);
		partnerAdminPage.clickOnActionsButton();
		assertTrue(partnerAdminPage.isViewOptionEnabled(), GlobalConstants.OVP_VIEW_AFTER_DEACTIVATION);
		partnerAdminPage.clickOnViewButtonInListOfPartnerDetailsScreen();

		assertTrue(partnerAdminPage.isViewPartnersDetailsPageDisplayed(),
				GlobalConstants.OVP_VIEW_AFTER_DEACTIVATION);
		assertEquals(partnerAdminPage.getPageTitleText(), GlobalConstants.VIEW_PARTNER_DETAILS_TITLE,
				GlobalConstants.OVP_VIEW_AFTER_DEACTIVATION);
		assertTrue(partnerAdminPage.getViewPartnerIdText().contains(deactivatePartnerId),
				GlobalConstants.OVP_ONLY_STATUS_CHANGES);
		assertEquals(partnerAdminPage.getPartnerStatusInViewPartnerDetails(), GlobalConstants.DEACTIVATED,
				GlobalConstants.OVP_ONLY_STATUS_CHANGES);
		assertEquals(partnerAdminPage.getViewPartnerDetailsPartnerType(),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER, GlobalConstants.OVP_ONLY_STATUS_CHANGES);
		assertEquals(partnerAdminPage.getViewOrganisationName(), organisationName,
				GlobalConstants.OVP_ONLY_STATUS_CHANGES);
		assertEquals(partnerAdminPage.getViewPhoneNumber(), phoneNumber, GlobalConstants.OVP_ONLY_STATUS_CHANGES);
		assertEquals(partnerAdminPage.getViewEmail(), deactivateEmail, GlobalConstants.OVP_ONLY_STATUS_CHANGES);
		assertEquals(partnerAdminPage.getViewPolicyGroup(), deactivatePolicyGroup,
				GlobalConstants.OVP_ONLY_STATUS_CHANGES);
	}

	@Test(priority = 9, description = "TC_1855_21: Deactivate is disabled when the partner is already deactivated", dependsOnMethods = "confirmDeactivationKeepsListOrderAndStatus")
	public void deactivateOptionDisabledAfterDeactivation() {
		PartnerAdminPage partnerAdminPage = openActionMenu(deactivatePartnerId);

		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.DEACTIVATED,
				GlobalConstants.OVP_DEACTIVATE_DISABLED);
		assertTrue(partnerAdminPage.isViewOptionEnabled(), GlobalConstants.OVP_VIEW_AFTER_DEACTIVATION);
		assertTrue(partnerAdminPage.isDeactivateButtonsDisplayed(), GlobalConstants.OVP_DEACTIVATE_DISABLED);
		assertTrue(partnerAdminPage.isDeactivateOptionDisabled(), GlobalConstants.OVP_DEACTIVATE_DISABLED);
		assertFalse(partnerAdminPage.isDeactivateOptionEnabled(), GlobalConstants.OVP_DEACTIVATE_DISABLED);

		partnerAdminPage.clickOnDeactivateOptionInActionMenu();
		assertFalse(partnerAdminPage.isDeactivatePopupHeaderDisplayedQuick(),
				GlobalConstants.OVP_DEACTIVATE_DISABLED);
	}

	@Test(priority = 10, description = "TC_1855_20: a user without PARTNER_ADMIN cannot deactivate a partner", dependsOnMethods = "createOnlineVerificationPartnersForDeactivate")
	public void partnerWithoutAdminRoleHasNoDeactivateAccess() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		KeycloakUserManager.createUsers(GlobalConstants.AUTH_PARTNER_ID, GlobalConstants.PARTNER_PASSWORD,
				"roles.auth", null);
		dashboardPage.clickOnProfileDropdown();
		LoginPage loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.login(GlobalConstants.AUTH_PARTNER_ID, GlobalConstants.PARTNER_PASSWORD);
		if (dashboardPage.isTermsAndConditionsPopupDisplayedQuick()) {
			dashboardPage.clickOnCheckbox();
			dashboardPage.clickOnProceedButton();
		}

		assertTrue(dashboardPage.isAuthenticationServicesDisplayed(), GlobalConstants.OVP_NON_ADMIN_NO_DEACTIVATE);
		assertFalse(dashboardPage.isPartnerAdminCardDisplayed(), GlobalConstants.OVP_NON_ADMIN_NO_DEACTIVATE);
		assertFalse(dashboardPage.isPartnerAdminSideNavDisplayed(), GlobalConstants.OVP_NON_ADMIN_NO_DEACTIVATE);
	}

	private PartnerAdminPage openActionMenu(String partnerId) {
		PartnerAdminPage partnerAdminPage = openPartnerList();
		filterByPartnerId(partnerAdminPage, partnerId);
		partnerAdminPage.clickOnActionsButton();
		return partnerAdminPage;
	}

	private PartnerAdminPage openPartnerList() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		PartnerAdminPage partnerAdminPage = new PartnerAdminPage(driver);
		String page = new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> {
			if (!webDriver.findElements(By.id("create_partner_btn")).isEmpty()) {
				return "list";
			}
			if (!webDriver.findElements(By.id("dashboard_partner_card_header")).isEmpty()) {
				return "dashboard";
			}
			return null;
		});
		if ("dashboard".equals(page)) {
			dashboardPage.clickOnPartners();
		}
		assertTrue(partnerAdminPage.isPartnerListLoaded(), GlobalConstants.isPartnerListLoaded);
		return partnerAdminPage;
	}

	private void filterByPartnerId(PartnerAdminPage partnerAdminPage, String partnerId) {
		if (driver.findElements(By.id("partner_id_filter")).isEmpty()) {
			partnerAdminPage.clickOnFilterButton();
		}
		partnerAdminPage.enterPartnerIdInFilter(partnerId);
		partnerAdminPage.clickOnApplyFiltersBtn();
		assertTrue(partnerAdminPage.isPartnerListLoaded(partnerId), GlobalConstants.isPartnerListLoaded);
	}

	private MispPartnerPage navigateToCreatePartnerPage() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
		if (!driver.findElements(By.id("create_partner_btn")).isEmpty()) {
			mispPartnerPage.clickOnCreatePartnerButton();
			return mispPartnerPage;
		}
		dashboardPage.clickOnPartners();
		mispPartnerPage.clickOnCreatePartnerButton();
		return mispPartnerPage;
	}

	private void createPartner(MispPartnerPage mispPartnerPage, String partnerId, String email) {
		mispPartnerPage.clickOnPartnerTypeDropdown();
		mispPartnerPage.clickOnPartnerTypeOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		mispPartnerPage.selectFirstActivePolicyGroup();
		mispPartnerPage.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
		mispPartnerPage.enterPartnerOrganisation(organisationName);
		mispPartnerPage.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
		mispPartnerPage.enterPartnerContactNumber(phoneNumber);
		mispPartnerPage.enterEmailId(email);
		mispPartnerPage.enterUserName(partnerId);
	}

	private void uploadAuthTrustCertificates() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		dashboardPage.clickOnCertificateTrustStore();
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateRootCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnGoBackButton();
		partnerCertificatePage.clickOnIntermediateCACertTab();
		partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadCertificateSubCa();
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		partnerCertificatePage.clickOnHomeButton();
	}

	private void uploadPartnerCertificate(MispPartnerPage mispPartnerPage) {
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		mispPartnerPage.clickOnUploadPartnerCertificateButton();
		assertTrue(partnerCertificatePage.isMispPartnerCertificatePopupDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		partnerCertificatePage.uploadCertificate();
		partnerCertificatePage.clickOnSubmitButton();
		assertTrue(partnerCertificatePage.isCertificateUploadSuccessMessageDisplayed(),
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		assertTrue(new PartnerAdminPage(driver).isPartnerListLoaded(), GlobalConstants.isPartnerListLoaded);
	}
}
