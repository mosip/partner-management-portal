package io.mosip.testrig.pmpuiv2.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerAdminPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "OnlineVerificationPartnerViewTest" })
public class OnlineVerificationPartnerViewTest extends BaseClass {

	private static final String ORIGINAL_CERTIFICATE_FILE = "ca_signed_partner_certificate.cer";
	private static final String MOSIP_SIGNED_CERTIFICATE_FILE = "mosip_signed_certificate.cer";

	private static String partnerWithoutCertificateId;
	private static String partnerWithCertificateId;
	private static String lengthyPartnerId;
	private static String organisationName;
	private static String phoneNumber;
	private static String emailAddress;
	private static String policyGroupName;
	private static String lengthyOrganisationName;
	private static String lengthyPhoneNumber;
	private static String lengthyEmailAddress;
	private static String lengthyPolicyGroupName;

	@Test(priority = 1, description = "Create Online Verification Partners used by the view-details cases")
	public void createOnlineVerificationPartnersForView() {
		String suffix = BaseClass.data;
		partnerWithoutCertificateId = "ovpnc" + suffix;
		partnerWithCertificateId = "ovpca" + suffix;
		lengthyPartnerId = "ovpln" + suffix;
		organisationName = GlobalConstants.ORGANISATION_NAME;
		phoneNumber = GlobalConstants.ABIS_CONTACT_NUMBER;
		emailAddress = "ovpnc" + suffix + "@test.com";
		lengthyEmailAddress = "ovpln" + suffix + "@" + "a".repeat(30) + ".example.com";

		uploadAuthTrustCertificates();
		MispPartnerPage mispPartnerPage = navigateToCreatePartnerPage();
		lengthyPhoneNumber = phoneOfMaxLength(mispPartnerPage);
		createPartner(mispPartnerPage, partnerWithoutCertificateId, organisationName, phoneNumber, emailAddress);
		policyGroupName = mispPartnerPage.getSelectedPolicyGroupText();
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
		mispPartnerPage.clickOnSuccessMsgHomeButton();

		mispPartnerPage = navigateToCreatePartnerPage();
		createPartner(mispPartnerPage, partnerWithCertificateId, organisationName, phoneNumber,
				"ovpca" + suffix + "@test.com");
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
		uploadPartnerCertificate(mispPartnerPage);

		mispPartnerPage = navigateToCreatePartnerPage();
		lengthyOrganisationName = "A".repeat(GlobalConstants.ORG_NAME_MAX_LENGTH);
		createPartner(mispPartnerPage, lengthyPartnerId, lengthyOrganisationName, lengthyPhoneNumber,
				lengthyEmailAddress);
		lengthyPolicyGroupName = mispPartnerPage.getSelectedPolicyGroupText();
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
		assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
		mispPartnerPage.clickOnSuccessMsgHomeButton();
	}

	@Test(priority = 2, description = "TC_1853_01, TC_1853_03 to TC_1853_08: filter Online Verification Partner and open View", dependsOnMethods = "createOnlineVerificationPartnersForView")
	public void filterAndOpenView() {
		PartnerAdminPage partnerAdminPage = openPartnerList();
		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.clickOnPartnerTypeDropdown();
		assertTrue(partnerAdminPage.isPartnerTypeFilterOptionDisplayed(),
				GlobalConstants.isOnlineVerificationPartnerTypeSelectable);
		assertFalse(partnerAdminPage.isPartnerTypeDropdownSearchInputDisplayed(),
				GlobalConstants.isPartnerTypeFilterSearchAbsent);
		partnerAdminPage.selectPartnerTypeFilterOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		assertEquals(partnerAdminPage.getPartnerTypeFilterButtonText(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
				GlobalConstants.isOnlineVerificationPartnerTypeSelectable);
		partnerAdminPage.clickOnApplyFiltersBtn();
		assertTrue(partnerAdminPage.isPartnerListLoadedByPartnerType(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
				GlobalConstants.isOnlineVerificationPartnerFilterApplied);
		assertTrue(partnerAdminPage.areAllVisiblePartnerTypes(GlobalConstants.ONLINE_VERIFICATION_PARTNER),
				GlobalConstants.isOnlyOnlineVerificationPartnersListed);

		partnerAdminPage.clickOnFilterResetButton();
		new WebDriverWait(driver, Duration.ofSeconds(20))
				.until(ExpectedConditions.elementToBeClickable(By.id("filter_btn")));
		partnerAdminPage.clickOnFilterButton();
		partnerAdminPage.clickOnPartnerTypeDropdown();
		partnerAdminPage.selectPartnerTypeFilterOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		partnerAdminPage.enterPartnerIdInFilter(partnerWithCertificateId);
		partnerAdminPage.clickOnApplyFiltersBtn();
		assertTrue(partnerAdminPage.isPartnerListLoaded(partnerWithCertificateId),
				GlobalConstants.isCombinedFilterShowsOnlineVerificationPartner);
		assertEquals(partnerAdminPage.getFirstRowPartnerType(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
				GlobalConstants.isCombinedFilterShowsOnlineVerificationPartner);

		partnerAdminPage.clickOnActionsButton();
		assertTrue(partnerAdminPage.isViewButtonsDisplayed(), GlobalConstants.isViewOptionAvailableForPartner);
		partnerAdminPage.clickOnViewButtonInListOfPartnerDetailsScreen();
		assertTrue(partnerAdminPage.isViewPartnersDetailsPageDisplayed(),
				GlobalConstants.isOnlineVerificationPartnerViewOpened);
		assertEquals(partnerAdminPage.getPageTitleText(), GlobalConstants.VIEW_PARTNER_DETAILS_TITLE,
				GlobalConstants.isOnlineVerificationPartnerViewOpened);
	}

	@Test(priority = 3, description = "TC_1853_09 to TC_1853_11: View is available for Active and Inactive partners", dependsOnMethods = "createOnlineVerificationPartnersForView")
	public void viewOptionForActiveAndInactivePartners() {
		assertViewOptionAvailable(partnerWithCertificateId, GlobalConstants.PARTNER_STATUS_ACTIVE);
		assertViewOptionAvailable(partnerWithoutCertificateId, GlobalConstants.PARTNER_STATUS_INACTIVE);
	}

	@Test(priority = 4, description = "TC_1853_12, TC_1853_13, TC_1853_14, TC_1853_15, TC_1853_33: title and navigation", dependsOnMethods = "createOnlineVerificationPartnersForView")
	public void viewPageTitleAndNavigation() {
		PartnerAdminPage partnerAdminPage = openView(partnerWithoutCertificateId);
		assertEquals(partnerAdminPage.getPageTitleText(), GlobalConstants.VIEW_PARTNER_DETAILS_TITLE,
				GlobalConstants.isViewPartnerDetailsTitleCorrect);
		assertTrue(partnerAdminPage.isGobackButtonInViewPatnerPageDisplayed(),
				GlobalConstants.isGoBackNavigatesToPartnerList);
		assertEquals(partnerAdminPage.getGoBackButtonText(), GlobalConstants.GO_BACK_BUTTON_LABEL,
				GlobalConstants.isGoBackNavigatesToPartnerList);

		partnerAdminPage.clickOnViewPartnerBackButton();
		assertPartnerListDisplayed(partnerAdminPage);

		partnerAdminPage = openView(partnerWithoutCertificateId);
		partnerAdminPage.clickOnBreadcrumb();
		assertTrue(new DashboardPage(driver).isPartnersDisplayed(), GlobalConstants.isViewHomeNavigatesToDashboard);

		partnerAdminPage = openView(partnerWithoutCertificateId);
		partnerAdminPage.clickOnlistOfPartners();
		assertPartnerListDisplayed(partnerAdminPage);

		partnerAdminPage = openView(partnerWithoutCertificateId);
		partnerAdminPage.clickOngobackButtonInPartnerDetailsPage();
		assertPartnerListDisplayed(partnerAdminPage);
	}

	@Test(priority = 5, description = "TC_1853_02, TC_1853_16, TC_1853_17: view page is read only", dependsOnMethods = "createOnlineVerificationPartnersForView")
	public void viewPageIsReadOnly() {
		PartnerAdminPage partnerAdminPage = openView(partnerWithoutCertificateId);
		assertTrue(partnerAdminPage.areViewPartnerValueFieldsReadOnly(), GlobalConstants.isViewFieldsReadOnly);
		assertFalse(partnerAdminPage.isViewPartnerEditControlDisplayed(), GlobalConstants.isViewHasNoEditActions);
	}

	@Test(priority = 6, description = "TC_1853_18 to TC_1853_22, TC_1853_24, TC_1853_28: field values and blank certificate", dependsOnMethods = "createOnlineVerificationPartnersForView")
	public void viewFieldValuesAndBlankCertificate() {
		PartnerAdminPage partnerAdminPage = openView(partnerWithoutCertificateId);
		assertTrue(partnerAdminPage.getViewPartnerIdText().contains(partnerWithoutCertificateId),
				GlobalConstants.isViewPartnerIdMatchesCreation);
		assertEquals(partnerAdminPage.getPartnerStatusInViewPartnerDetails(), GlobalConstants.PARTNER_STATUS_INACTIVE,
				GlobalConstants.isViewStatusDisplayed);
		assertTrue(partnerAdminPage.isPartnerCreatedDateInViewPartnerPageDisplayed(),
				GlobalConstants.isViewCreatedOnDisplayed);
		assertTrue(partnerAdminPage.getViewPartnerCreatedOnText().startsWith(GlobalConstants.CREATED_ON_LABEL + " "),
				GlobalConstants.isViewCreatedOnDisplayed);
		assertTrue(partnerAdminPage.getViewPartnerCreatedOnText().matches(".*\\d.*"),
				GlobalConstants.isViewCreatedOnDisplayed);
		assertTrue(partnerAdminPage.getViewPartnerCreatedTimeText().matches(".*\\d{1,2}:\\d{2}.*"),
				GlobalConstants.isViewCreatedOnDisplayed);

		assertEquals(partnerAdminPage.getViewPartnerDetailsPartnerType(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
				GlobalConstants.isViewFieldsMatchCreation);
		assertEquals(partnerAdminPage.getViewOrganisationName(), organisationName,
				GlobalConstants.isViewFieldsMatchCreation);
		assertEquals(partnerAdminPage.getViewPhoneNumber(), phoneNumber, GlobalConstants.isViewFieldsMatchCreation);
		assertEquals(partnerAdminPage.getViewEmail(), emailAddress, GlobalConstants.isViewFieldsMatchCreation);
		assertEquals(partnerAdminPage.getViewPolicyGroup(), policyGroupName, GlobalConstants.isViewFieldsMatchCreation);
		assertEquals(partnerAdminPage.getViewFirstName(), GlobalConstants.BLANK_FIELD_PLACEHOLDER,
				GlobalConstants.isBlankFieldShownAsDash);
		assertEquals(partnerAdminPage.getViewLastName(), GlobalConstants.BLANK_FIELD_PLACEHOLDER,
				GlobalConstants.isBlankFieldShownAsDash);

		assertTrue(partnerAdminPage.isPartnerCertificateSectionGreyedOut(),
				GlobalConstants.isCertificateGreyedOutBeforeUpload);
		assertTrue(partnerAdminPage.isDownloadCertificateButtonDisplayed(),
				GlobalConstants.isDownloadDisabledWithoutCertificate);
		assertFalse(partnerAdminPage.isDownloadCertificateButtonEnabledInViewPartnerPage(),
				GlobalConstants.isDownloadDisabledWithoutCertificate);
		assertEquals(partnerAdminPage.getViewCertificateExpiry(), GlobalConstants.BLANK_FIELD_PLACEHOLDER,
				GlobalConstants.isBlankFieldShownAsDash);
		assertEquals(partnerAdminPage.getViewCertificateUploadTime(), GlobalConstants.BLANK_FIELD_PLACEHOLDER,
				GlobalConstants.isBlankFieldShownAsDash);
	}

	@Test(priority = 7, description = "TC_1853_23: lengthy organisation, phone, email, and policy group stay visible", dependsOnMethods = "createOnlineVerificationPartnersForView")
	public void lengthyFieldValuesStayVisible() {
		PartnerAdminPage partnerAdminPage = openView(lengthyPartnerId);
		assertEquals(partnerAdminPage.getViewOrganisationName(), lengthyOrganisationName,
				GlobalConstants.isLengthyFieldsWrapped);
		assertEquals(partnerAdminPage.getViewPhoneNumber(), lengthyPhoneNumber, GlobalConstants.isLengthyFieldsWrapped);
		assertEquals(partnerAdminPage.getViewEmail(), lengthyEmailAddress, GlobalConstants.isLengthyFieldsWrapped);
		assertEquals(partnerAdminPage.getViewPolicyGroup(), lengthyPolicyGroupName,
				GlobalConstants.isLengthyFieldsWrapped);
		assertTrue(partnerAdminPage.isViewOrganisationNameFullyVisible(), GlobalConstants.isLengthyFieldsWrapped);
		assertTrue(partnerAdminPage.isViewPhoneNumberFullyVisible(), GlobalConstants.isLengthyFieldsWrapped);
		assertTrue(partnerAdminPage.isViewEmailFullyVisible(), GlobalConstants.isLengthyFieldsWrapped);
		assertTrue(partnerAdminPage.isViewPolicyGroupFullyVisible(), GlobalConstants.isLengthyFieldsWrapped);
	}

	@Test(priority = 8, description = "TC_1853_25, TC_1853_26, TC_1853_29 to TC_1853_32: certificate details and download for an active partner", dependsOnMethods = "createOnlineVerificationPartnersForView")
	public void certificateDownloadForActivePartner() throws Exception {
		PartnerAdminPage partnerAdminPage = openView(partnerWithCertificateId);
		assertEquals(partnerAdminPage.getPartnerStatusInViewPartnerDetails(), GlobalConstants.PARTNER_STATUS_ACTIVE,
				GlobalConstants.isDownloadEnabledForActivePartner);
		assertTrue(partnerAdminPage.isDownloadCertificateButtonEnabledInViewPartnerPage(),
				GlobalConstants.isDownloadEnabledForActivePartner);
		assertFalse(partnerAdminPage.getViewCertificateExpiry().equals(GlobalConstants.BLANK_FIELD_PLACEHOLDER),
				GlobalConstants.isCertificateDetailsAfterUpload);
		assertFalse(partnerAdminPage.getViewCertificateUploadTime().equals(GlobalConstants.BLANK_FIELD_PLACEHOLDER),
				GlobalConstants.isCertificateDetailsAfterUpload);
		assertTrue(partnerAdminPage.getViewCertificateExpiry().matches(".*\\d.*"),
				GlobalConstants.isCertificateDetailsAfterUpload);
		assertTrue(partnerAdminPage.getViewCertificateUploadTime().matches(".*\\d{1,2}:\\d{2}.*"),
				GlobalConstants.isCertificateDetailsAfterUpload);

		Path downloadDir = Files.createTempDirectory("ovp-view-cert");
		allowDownloads(downloadDir);
		partnerAdminPage.clickOnDownloadCertificateButtonInViewPartnerPage();
		assertTrue(partnerAdminPage.isOriginalCertificateDropdownDisplayed(),
				GlobalConstants.isDownloadOptionsDisplayed);
		assertTrue(partnerAdminPage.isMosipSignedCertificateDropdownDisplayed(),
				GlobalConstants.isDownloadOptionsDisplayed);
		assertTrue(partnerAdminPage.isOriginalCertificateOptionEnabled(),
				GlobalConstants.isOriginalCertificateDownloaded);

		partnerAdminPage.clickOnOriginnalCertificateInViewPartnerPage();
		assertEquals(partnerAdminPage.getViewPartnerSuccessMessage(),
				GlobalConstants.ORIGINAL_CERTIFICATE_DOWNLOAD_SUCCESS,
				GlobalConstants.isOriginalCertificateDownloaded);
		assertTrue(waitForDownloadedFile(downloadDir, ORIGINAL_CERTIFICATE_FILE),
				GlobalConstants.isOriginalCertificateDownloaded);
		partnerAdminPage.dismissViewPartnerSuccessMessage();

		partnerAdminPage.clickOnDownloadCertificateButtonInViewPartnerPage();
		partnerAdminPage.clickOnMosipSignedCertificateInViewPartnerPage();
		assertEquals(partnerAdminPage.getViewPartnerSuccessMessage(),
				GlobalConstants.MOSIP_SIGNED_CERTIFICATE_DOWNLOAD_SUCCESS,
				GlobalConstants.isMosipSignedCertificateDownloaded);
		assertTrue(waitForDownloadedFile(downloadDir, MOSIP_SIGNED_CERTIFICATE_FILE),
				GlobalConstants.isMosipSignedCertificateDownloaded);
		partnerAdminPage.dismissViewPartnerSuccessMessage();

		assertTrue(deleteCertificateFiles(downloadDir, ORIGINAL_CERTIFICATE_FILE),
				GlobalConstants.isRepeatCertificateDownload);
		partnerAdminPage.clickOnDownloadCertificateButtonInViewPartnerPage();
		partnerAdminPage.clickOnOriginnalCertificateInViewPartnerPage();
		assertEquals(partnerAdminPage.getViewPartnerSuccessMessage(),
				GlobalConstants.ORIGINAL_CERTIFICATE_DOWNLOAD_SUCCESS, GlobalConstants.isRepeatCertificateDownload);
		assertTrue(waitForDownloadedFile(downloadDir, ORIGINAL_CERTIFICATE_FILE),
				GlobalConstants.isRepeatCertificateDownload);
	}

	@Test(priority = 9, description = "TC_1853_11 and TC_1853_27: View stays available and download is disabled after deactivation", dependsOnMethods = "certificateDownloadForActivePartner")
	public void deactivatedPartnerViewAndCertificate() {
		PartnerAdminPage partnerAdminPage = openPartnerList();
		filterByPartnerId(partnerAdminPage, partnerWithCertificateId);
		partnerAdminPage.clickOnActionsButton();
		assertTrue(partnerAdminPage.isDeactivateOptionEnabled(), GlobalConstants.isDeactivateOptionEnabled);
		partnerAdminPage.clickOnDeactivateOptionInActionMenu();
		partnerAdminPage.clickOnDeactivatePopupConfirmButton();

		filterByPartnerId(partnerAdminPage, partnerWithCertificateId);
		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), GlobalConstants.DEACTIVATED,
				GlobalConstants.isViewStatusDisplayed);
		partnerAdminPage.clickOnActionsButton();
		assertTrue(partnerAdminPage.isViewOptionEnabled(), GlobalConstants.isViewOptionAvailableForPartner);
		partnerAdminPage.clickOnViewButtonInListOfPartnerDetailsScreen();

		assertEquals(partnerAdminPage.getPartnerStatusInViewPartnerDetails(), GlobalConstants.DEACTIVATED,
				GlobalConstants.isViewStatusDisplayed);
		assertTrue(partnerAdminPage.isPartnerCertificateSectionGreyedOut(),
				GlobalConstants.isDownloadDisabledForDeactivatedPartner);
		assertTrue(partnerAdminPage.isDownloadCertificateButtonDisplayed(),
				GlobalConstants.isDownloadDisabledForDeactivatedPartner);
		assertFalse(partnerAdminPage.isDownloadCertificateButtonEnabledInViewPartnerPage(),
				GlobalConstants.isDownloadDisabledForDeactivatedPartner);
	}

	private void assertViewOptionAvailable(String partnerId, String status) {
		PartnerAdminPage partnerAdminPage = openPartnerList();
		filterByPartnerId(partnerAdminPage, partnerId);
		assertEquals(partnerAdminPage.getFirstRowPartnerStatus(), status, GlobalConstants.isViewStatusDisplayed);
		partnerAdminPage.clickOnActionsButton();
		assertTrue(partnerAdminPage.isViewOptionEnabled(), GlobalConstants.isViewOptionAvailableForPartner);
	}

	private PartnerAdminPage openView(String partnerId) {
		PartnerAdminPage partnerAdminPage = openPartnerList();
		filterByPartnerId(partnerAdminPage, partnerId);
		partnerAdminPage.clickOnActionsButton();
		partnerAdminPage.clickOnViewButtonInListOfPartnerDetailsScreen();
		assertTrue(partnerAdminPage.isViewPartnersDetailsPageDisplayed(),
				GlobalConstants.isOnlineVerificationPartnerViewOpened);
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

	private void assertPartnerListDisplayed(PartnerAdminPage partnerAdminPage) {
		assertEquals(partnerAdminPage.getPageTitleText(), GlobalConstants.PARTNERS_PAGE_TITLE,
				GlobalConstants.isViewBackNavigatesToPartnerList);
		assertTrue(partnerAdminPage.isSubTitleListDisplayed(), GlobalConstants.isViewBackNavigatesToPartnerList);
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

	private void createPartner(MispPartnerPage mispPartnerPage, String partnerId, String organisation, String phone,
			String email) {
		mispPartnerPage.clickOnPartnerTypeDropdown();
		mispPartnerPage.clickOnPartnerTypeOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		mispPartnerPage.selectFirstActivePolicyGroup();
		mispPartnerPage.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
		mispPartnerPage.enterPartnerOrganisation(organisation);
		mispPartnerPage.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
		mispPartnerPage.enterPartnerContactNumber(phone);
		mispPartnerPage.enterEmailId(email);
		mispPartnerPage.enterUserName(partnerId);
	}

	private String phoneOfMaxLength(MispPartnerPage mispPartnerPage) {
		int maxLength = mispPartnerPage.getContactNumberMaxLength();
		if (maxLength <= 0) {
			return GlobalConstants.ABIS_CONTACT_NUMBER;
		}
		return "9".repeat(maxLength);
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

	private void allowDownloads(Path downloadDir) {
		Map<String, Object> params = new HashMap<>();
		params.put("behavior", "allow");
		params.put("downloadPath", downloadDir.toAbsolutePath().toString());
		params.put("eventsEnabled", true);
		((ChromiumDriver) driver).executeCdpCommand("Browser.setDownloadBehavior", params);
	}

	private boolean waitForDownloadedFile(Path downloadDir, String fileName) {
		String prefix = fileName.substring(0, fileName.lastIndexOf('.'));
		try {
			new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver -> countCertificateFiles(downloadDir, prefix) > 0
					&& newestCertificate(downloadDir, prefix).length() > 0);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	private boolean deleteCertificateFiles(Path downloadDir, String fileName) {
		String prefix = fileName.substring(0, fileName.lastIndexOf('.'));
		File[] files = downloadDir.toFile().listFiles(
				(dir, name) -> name.startsWith(prefix) && name.endsWith(".cer") && !name.endsWith(".crdownload"));
		if (files == null) {
			return false;
		}
		for (File file : files) {
			if (!file.delete()) {
				return false;
			}
		}
		return countCertificateFiles(downloadDir, prefix) == 0;
	}

	private int countCertificateFiles(Path downloadDir, String fileNameOrPrefix) {
		String prefix = fileNameOrPrefix.endsWith(".cer")
				? fileNameOrPrefix.substring(0, fileNameOrPrefix.lastIndexOf('.'))
				: fileNameOrPrefix;
		File[] files = downloadDir.toFile().listFiles(
				(dir, name) -> name.startsWith(prefix) && name.endsWith(".cer") && !name.endsWith(".crdownload"));
		return files == null ? 0 : files.length;
	}

	private File newestCertificate(Path downloadDir, String prefix) {
		File[] files = downloadDir.toFile().listFiles(
				(dir, name) -> name.startsWith(prefix) && name.endsWith(".cer") && !name.endsWith(".crdownload"));
		File newest = files[0];
		for (File file : files) {
			if (file.lastModified() > newest.lastModified()) {
				newest = file;
			}
		}
		return newest;
	}
}
