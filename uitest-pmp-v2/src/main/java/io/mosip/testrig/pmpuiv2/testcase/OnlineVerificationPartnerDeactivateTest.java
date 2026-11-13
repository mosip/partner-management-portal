package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

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
		phoneNumber = GlobalConstants.ONLINE_VERIFICATION_PARTNER_CONTACT_NUMBER;
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

	@Test(priority = 2, description = "Deactivate is available for an ACTIVE Online Verification Partner", dependsOnMethods = "createOnlineVerificationPartnersForDeactivate")
	public void deactivateOptionDisplayedForActivePartner() {
		PartnerAdminPage partnerAdminPage = openActionMenu(activePartnerId);

		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.PARTNER_STATUS_ACTIVE,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_OPTION_DISPLAYED);
		assertEquals(partnerAdminPage.getFirstRowPartnerType(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_OPTION_DISPLAYED);
		assertTrue(partnerAdminPage.isViewButtonsDisplayed(), GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_OPTION_DISPLAYED);
		assertTrue(partnerAdminPage.isViewOptionEnabled(), GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_OPTION_DISPLAYED);
		assertTrue(partnerAdminPage.isDeactivateButtonsDisplayed(), GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_OPTION_DISPLAYED);
		assertTrue(partnerAdminPage.isDeactivateOptionEnabled(), GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_OPTION_DISPLAYED);
	}

	@Test(priority = 3, description = "Confirmation popup shows Confirm, Cancel, Partner ID, and Organisation", dependsOnMethods = "createOnlineVerificationPartnersForDeactivate")
	public void confirmationPopupShowsPartnerAndOrganisation() {
		PartnerAdminPage partnerAdminPage = openActionMenu(activePartnerId);
		partnerAdminPage.clickOnDeactivateOptionInActionMenu();

		assertTrue(partnerAdminPage.isDeactivatePopupHeaderDisplayed(),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_POPUP_DISPLAYED);
		assertTrue(partnerAdminPage.isDeactivatePopupDescriptionDisplayed(),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_POPUP_DISPLAYED);
		assertTrue(partnerAdminPage.isDeactivatePopupConfirmButtonDisplayed(),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_POPUP_BUTTONS_DISPLAYED);
		assertTrue(partnerAdminPage.isDeactivatePopupCancelButtonDisplayed(),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_POPUP_BUTTONS_DISPLAYED);
		assertTrue(partnerAdminPage.isDeactivatePopupConfirmButtonEnabled(),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_POPUP_BUTTONS_DISPLAYED);
		assertTrue(partnerAdminPage.isDeactivatePopupCancelButtonEnabled(),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_POPUP_BUTTONS_DISPLAYED);

		String expectedTitle = String.format(GlobalConstants.DEACTIVATE_PARTNER_POPUP_TITLE, activePartnerId,
				organisationName);
		assertEquals(partnerAdminPage.getDeactivatePopupTitle(), expectedTitle,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_POPUP_PARTNER_ID);
		assertTrue(partnerAdminPage.getDeactivatePopupTitle().contains(activePartnerId),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_POPUP_PARTNER_ID);
		assertTrue(partnerAdminPage.getDeactivatePopupTitle().contains(organisationName),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_POPUP_ORGANISATION);
		assertEquals(partnerAdminPage.getDeactivatePopupSubTitle(),
				GlobalConstants.DEACTIVATE_PARTNER_POPUP_DESCRIPTION, GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_POPUP_DISPLAYED);

		partnerAdminPage.clickOnDeactivatePopupCancelButton();
	}

	@Test(priority = 4, description = "Cancel closes the popup and leaves the partner Active", dependsOnMethods = "createOnlineVerificationPartnersForDeactivate")
	public void cancelClosesPopupAndKeepsActiveStatus() {
		PartnerAdminPage partnerAdminPage = openActionMenu(activePartnerId);

		partnerAdminPage.clickOnDeactivateOptionInActionMenu();
		assertTrue(partnerAdminPage.isDeactivatePopupHeaderDisplayed(),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_POPUP_DISPLAYED);
		partnerAdminPage.clickOnDeactivatePopupCancelButton();

		assertFalse(partnerAdminPage.isDeactivatePopupHeaderDisplayedQuick(),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_CANCEL_CLOSES_POPUP);
		assertEquals(partnerAdminPage.getPageTitleText(), GlobalConstants.PARTNERS_PAGE_TITLE,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_CANCEL_CLOSES_POPUP);
		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.PARTNER_STATUS_ACTIVE,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_CANCEL_KEEPS_ACTIVE);
		assertEquals(partnerAdminPage.getFirstRowPartnerId(), activePartnerId,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_CANCEL_KEEPS_ACTIVE);
	}

	@Test(priority = 5, description = "Active Online Verification Partners remain eligible for deactivation", dependsOnMethods = "createOnlineVerificationPartnersForDeactivate")
	public void activeFilterListsPartnersEligibleForDeactivation() {
		PartnerAdminPage partnerAdminPage = openPartnerList();
		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.clickOnPartnerTypeDropdown();
		partnerAdminPage.selectPartnerTypeFilterOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		partnerAdminPage.clickOnStatusFilter();
		partnerAdminPage.selectStatusFilterOption(GlobalConstants.PARTNER_STATUS_ACTIVE);
		partnerAdminPage.clickOnApplyFiltersBtn();

		assertTrue(partnerAdminPage.isPartnerListLoadedByPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_ACTIVE_FILTER_ELIGIBLE);
		assertTrue(partnerAdminPage.areAllVisiblePartnerTypes(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_ACTIVE_FILTER_ELIGIBLE);
		assertTrue(partnerAdminPage.areAllVisiblePartnerStatuses(GlobalConstants.PARTNER_STATUS_ACTIVE),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_ACTIVE_FILTER_ELIGIBLE);
		assertTrue(partnerAdminPage.getPartnerIdColumnValues().contains(activePartnerId),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_ACTIVE_FILTER_ELIGIBLE);

		partnerAdminPage.clickOnActionsButton();
		assertTrue(partnerAdminPage.isDeactivateOptionEnabled(), GlobalConstants.ONLINE_VERIFICATION_PARTNER_ACTIVE_FILTER_ELIGIBLE);
	}

	@Test(priority = 6, description = "Confirm deactivation, stay on the list, and keep sort order", dependsOnMethods = "createOnlineVerificationPartnersForDeactivate")
	public void confirmDeactivationKeepsListOrderAndStatus() {
		PartnerAdminPage partnerAdminPage = openPartnerList();
		filterByPartnerTypeAndId(partnerAdminPage, deactivatePartnerId);
		assertTrue(partnerAdminPage.isPartnerListLoaded(deactivatePartnerId),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_SORT_ORDER_UNCHANGED);

		List<String> partnerIdsBefore = partnerAdminPage.getPartnerIdColumnValues();
		assertEquals(partnerAdminPage.getFirstRowPartnerId(), deactivatePartnerId,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_SORT_ORDER_UNCHANGED);
		assertEquals(partnerAdminPage.getFirstRowPartnerType(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATED);
		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.PARTNER_STATUS_ACTIVE,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATED);

		partnerAdminPage.clickOnActionsButton();
		assertTrue(partnerAdminPage.isDeactivateOptionEnabled(), GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATED);
		partnerAdminPage.clickOnDeactivateOptionInActionMenu();
		assertTrue(partnerAdminPage.isDeactivatePopupHeaderDisplayed(),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_POPUP_DISPLAYED);
		partnerAdminPage.clickOnDeactivatePopupConfirmButton();

		assertEquals(partnerAdminPage.getPageTitleText(), GlobalConstants.PARTNERS_PAGE_TITLE,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_REMAINS_ON_PARTNER_LIST);
		assertTrue(partnerAdminPage.isSubTitleListDisplayed(), GlobalConstants.ONLINE_VERIFICATION_PARTNER_REMAINS_ON_PARTNER_LIST);
		assertTrue(partnerAdminPage.isFirstRowStatus(GlobalConstants.DEACTIVATED),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_STATUS_UPDATES_WITHOUT_REFRESH);
		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.DEACTIVATED,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATED);
		assertEquals(partnerAdminPage.getFirstRowPartnerId(), deactivatePartnerId,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_SORT_ORDER_UNCHANGED);
		assertEquals(partnerAdminPage.getPartnerIdColumnValues(), partnerIdsBefore,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_SORT_ORDER_UNCHANGED);
		assertEquals(partnerAdminPage.getFirstRowPartnerType(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATED);
	}

	@Test(priority = 7, description = "Deactivated partner stays available in search and the partner-type filter", dependsOnMethods = "confirmDeactivationKeepsListOrderAndStatus")
	public void deactivatedPartnerIsFoundBySearchAndFilter() {
		PartnerAdminPage partnerAdminPage = openPartnerList();
		filterByPartnerId(partnerAdminPage, deactivatePartnerId);
		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.DEACTIVATED,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATED_IN_SEARCH);
		assertEquals(partnerAdminPage.getFirstRowPartnerType(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATED_IN_SEARCH);

		partnerAdminPage.clickOnFilterResetButton();
		partnerAdminPage.waitForFilterButtonClickable();
		filterByPartnerTypeAndId(partnerAdminPage, deactivatePartnerId);

		assertTrue(partnerAdminPage.isPartnerListLoadedByPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_FILTER_AFTER_DEACTIVATION);
		assertTrue(partnerAdminPage.areAllVisiblePartnerTypes(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_FILTER_AFTER_DEACTIVATION);
		assertTrue(partnerAdminPage.getPartnerIdColumnValues().contains(deactivatePartnerId),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_FILTER_AFTER_DEACTIVATION);
		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.DEACTIVATED,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_FILTER_AFTER_DEACTIVATION);
	}

	@Test(priority = 8, description = "View stays available and only the status changes", dependsOnMethods = "confirmDeactivationKeepsListOrderAndStatus")
	public void viewKeepsDetailsAndShowsDeactivatedStatus() {
		PartnerAdminPage partnerAdminPage = openPartnerList();
		filterByPartnerId(partnerAdminPage, deactivatePartnerId);
		partnerAdminPage.clickOnActionsButton();
		assertTrue(partnerAdminPage.isViewOptionEnabled(), GlobalConstants.ONLINE_VERIFICATION_PARTNER_VIEW_AFTER_DEACTIVATION);
		partnerAdminPage.clickOnViewButtonInListOfPartnerDetailsScreen();

		assertTrue(partnerAdminPage.isViewPartnersDetailsPageDisplayed(),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_VIEW_AFTER_DEACTIVATION);
		assertEquals(partnerAdminPage.getPageTitleText(), GlobalConstants.VIEW_PARTNER_DETAILS_TITLE,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_VIEW_AFTER_DEACTIVATION);
		assertTrue(partnerAdminPage.getViewPartnerIdText().contains(deactivatePartnerId),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_ONLY_STATUS_CHANGES);
		assertEquals(partnerAdminPage.getPartnerStatusInViewPartnerDetails(), GlobalConstants.DEACTIVATED,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_ONLY_STATUS_CHANGES);
		assertEquals(partnerAdminPage.getViewPartnerDetailsPartnerType(),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER, GlobalConstants.ONLINE_VERIFICATION_PARTNER_ONLY_STATUS_CHANGES);
		assertEquals(partnerAdminPage.getViewOrganisationName(), organisationName,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_ONLY_STATUS_CHANGES);
		assertEquals(partnerAdminPage.getViewPhoneNumber(), phoneNumber, GlobalConstants.ONLINE_VERIFICATION_PARTNER_ONLY_STATUS_CHANGES);
		assertEquals(partnerAdminPage.getViewEmail(), deactivateEmail, GlobalConstants.ONLINE_VERIFICATION_PARTNER_ONLY_STATUS_CHANGES);
		assertEquals(partnerAdminPage.getViewPolicyGroup(), deactivatePolicyGroup,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_ONLY_STATUS_CHANGES);
	}

	@Test(priority = 9, description = "Deactivate is disabled when the partner is already deactivated", dependsOnMethods = "confirmDeactivationKeepsListOrderAndStatus")
	public void deactivateOptionDisabledAfterDeactivation() {
		PartnerAdminPage partnerAdminPage = openActionMenu(deactivatePartnerId);

		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.DEACTIVATED,
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_DISABLED);
		assertTrue(partnerAdminPage.isViewOptionEnabled(), GlobalConstants.ONLINE_VERIFICATION_PARTNER_VIEW_AFTER_DEACTIVATION);
		assertTrue(partnerAdminPage.isDeactivateButtonsDisplayed(), GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_DISABLED);
		assertTrue(partnerAdminPage.isDeactivateOptionDisabled(), GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_DISABLED);

		partnerAdminPage.clickOnDeactivateOptionInActionMenu();
		assertFalse(partnerAdminPage.isDeactivatePopupHeaderDisplayedQuick(),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_DEACTIVATE_DISABLED);
	}

	@Test(priority = 10, description = "A user without PARTNER_ADMIN stays on the main page", dependsOnMethods = "createOnlineVerificationPartnersForDeactivate")
	public void partnerWithoutAdminRoleHasNoDeactivateAccess() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		KeycloakUserManager.createUsers(GlobalConstants.NON_ADMIN_USER_ID, GlobalConstants.PARTNER_PASSWORD,
				"roles.auth", null);
		dashboardPage.clickOnProfileDropdown();
		LoginPage loginPage = dashboardPage.clickOnLogoutButton();
		loginPage.login(GlobalConstants.NON_ADMIN_USER_ID, GlobalConstants.PARTNER_PASSWORD);
		if (dashboardPage.isTermsAndConditionsPopupDisplayedQuick()) {
			dashboardPage.clickOnCheckbox();
			dashboardPage.clickOnProceedButton();
		}

		assertTrue(dashboardPage.isWelcomeMessageDisplayed() && dashboardPage.isAuthenticationServicesDisplayed()
				&& !dashboardPage.isPartnerAdminCardDisplayed() && !dashboardPage.isPartnerAdminSideNavDisplayed(),
				GlobalConstants.ONLINE_VERIFICATION_PARTNER_NON_ADMIN_CANNOT_DEACTIVATE);
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
		if (dashboardPage.waitUntilDashboardInsteadOfPartnerList()) {
			dashboardPage.clickOnPartners();
		}
		assertTrue(partnerAdminPage.isPartnerListLoaded(), GlobalConstants.isPartnerListLoaded);
		return partnerAdminPage;
	}

	private void filterByPartnerTypeAndId(PartnerAdminPage partnerAdminPage, String partnerId) {
		if (!partnerAdminPage.isPartnerIdFilterPresent()) {
			partnerAdminPage.clickOnFilterButton();
		}
		partnerAdminPage.clickOnPartnerTypeDropdown();
		partnerAdminPage.selectPartnerTypeFilterOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		partnerAdminPage.enterPartnerIdInFilter(partnerId);
		partnerAdminPage.clickOnApplyFiltersBtn();
	}

	private void filterByPartnerId(PartnerAdminPage partnerAdminPage, String partnerId) {
		if (!partnerAdminPage.isPartnerIdFilterPresent()) {
			partnerAdminPage.clickOnFilterButton();
		}
		partnerAdminPage.enterPartnerIdInFilter(partnerId);
		partnerAdminPage.clickOnApplyFiltersBtn();
		assertTrue(partnerAdminPage.isPartnerListLoaded(partnerId), GlobalConstants.isPartnerListLoaded);
	}

	private MispPartnerPage navigateToCreatePartnerPage() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
		if (!mispPartnerPage.isCreatePartnerButtonPresent()) {
			dashboardPage.clickOnPartners();
		}
		mispPartnerPage.clickOnCreatePartnerButton();
		return mispPartnerPage;
	}

	private void createPartner(MispPartnerPage mispPartnerPage, String partnerId, String email) {
		mispPartnerPage.clickOnPartnerTypeDropdown();
		mispPartnerPage.clickOnPartnerTypeOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		mispPartnerPage.selectFirstActivePolicyGroup();
		mispPartnerPage.selectNotificationLanguage(GlobalConstants.ONLINE_VERIFICATION_PARTNER_NOTIFICATION_LANGUAGE);
		mispPartnerPage.enterPartnerOrganisation(organisationName);
		mispPartnerPage.enterPartnerAddress(GlobalConstants.ONLINE_VERIFICATION_PARTNER_ADDRESS);
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
