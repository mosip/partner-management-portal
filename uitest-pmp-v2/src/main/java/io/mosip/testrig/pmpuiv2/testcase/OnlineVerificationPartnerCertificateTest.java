package io.mosip.testrig.pmpuiv2.testcase;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;
import io.mosip.testrig.pmpuiv2.pages.LoginPage;

import io.mosip.testrig.pmpuiv2.pages.DashboardPage;
import io.mosip.testrig.pmpuiv2.pages.MispPartnerPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerAdminPage;
import io.mosip.testrig.pmpuiv2.pages.PartnerCertificatePage;
import io.mosip.testrig.pmpuiv2.utility.BaseClass;
import io.mosip.testrig.pmpuiv2.utility.GlobalConstants;
import io.mosip.testrig.pmpuiv2.utility.LogUtil;

@Test(dependsOnGroups = { "PartnerAdminCreation" }, groups = { "OnlineVerificationPartnerCertificateTest" })
public class OnlineVerificationPartnerCertificateTest extends BaseClass {

	private static boolean authTrustChainUploaded;

	@Test(priority = 1, description = "TC_1854_01 Verify Partner Admin navigates to PMP Home Page")
	public void partnerAdminHomePage() {
		DashboardPage dashboardPage = new DashboardPage(driver);

		LogUtil.step("Partner Admin home page after login");
		Assert.assertTrue(dashboardPage.isWelcomeMessageDisplayed(), GlobalConstants.isWelcomeMessageDisplayed);
		Assert.assertTrue(dashboardPage.isPartnersDisplayed(), GlobalConstants.isPartnersDisplayed);
		Assert.assertTrue(dashboardPage.isCertificateTrustStoreDisplayed(),
				GlobalConstants.isCertificateTrustStoreDisplayed);
	}

	@Test(priority = 2, description = "TC_1854_02 Verify upload of valid partner certificate activates the partner")
	public void uploadValidCertificateActivatesPartner() {
		String partnerId = "ovp02" + BaseClass.data;
		ensureAuthTrustChain();
		createOnlineVerificationPartner(partnerId, "ovp02" + BaseClass.data + "@test.com");

		MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		Assert.assertTrue(mispPartnerPage.isUploadPartnerCertificateButtonDisplayed(),
				GlobalConstants.isUploadPartnerCertificateButtonDisplayed);
		Assert.assertEquals(mispPartnerPage.getUploadPartnerCertificateButtonText(),
				GlobalConstants.UPLOAD_ONLINE_VERIFICATION_PARTNER_CERTIFICATE,
				GlobalConstants.isUploadPartnerCertificateButtonDisplayed);

		LogUtil.step("Upload the CA-signed partner certificate");
		mispPartnerPage.clickOnUploadPartnerCertificateButton();
		assertUploadPopup(partnerCertificatePage, partnerId, GlobalConstants.UPLOAD_PARTNER_CERTIFICATE_TITLE, false);
		partnerCertificatePage.uploadGeneratedCertificate("OvpClient02.cer");
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertEquals(partnerCertificatePage.getCertificateUploadSuccessText(),
				GlobalConstants.OVP_CERTIFICATE_UPLOAD_SUCCESS,
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();

		assertPartnerRowStatus(partnerId, GlobalConstants.CERT_UPLOAD_STATUS_UPLOADED,
				GlobalConstants.PARTNER_STATUS_ACTIVE);
		assertMosipSignedCertificateDownloaded(partnerId);
	}

	@Test(priority = 3, description = "TC_1854_03 Verify re-upload of a valid certificate regenerates the MOSIP-signed certificate")
	public void reuploadValidCertificate() {
		String partnerId = "ovp03" + BaseClass.data;
		ensureAuthTrustChain();
		createOnlineVerificationPartner(partnerId, "ovp03" + BaseClass.data + "@test.com");
		uploadPartnerCertificateFromSuccessScreen("OvpClient03.cer");

		LogUtil.step("Re-upload a replacement CA-signed certificate");
		filterPartner(partnerId);
		MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		mispPartnerPage.clickActionButtonByPartnerId(partnerId);
		mispPartnerPage.clickOnUploadOrReuploadCertificateButton();
		assertUploadPopup(partnerCertificatePage, partnerId, GlobalConstants.REUPLOAD_PARTNER_CERTIFICATE_TITLE, true);
		Assert.assertEquals(partnerCertificatePage.getReuploadWarningText(),
				GlobalConstants.REUPLOAD_CERTIFICATE_WARNING, GlobalConstants.isReUploadPartnerCertificateDisplayed);
		partnerCertificatePage.uploadGeneratedCertificate("OvpClientReplacement.cer");
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertEquals(partnerCertificatePage.getCertificateUploadSuccessText(),
				GlobalConstants.OVP_CERTIFICATE_UPLOAD_SUCCESS,
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();

		assertPartnerRowStatus(partnerId, GlobalConstants.CERT_UPLOAD_STATUS_UPLOADED,
				GlobalConstants.PARTNER_STATUS_ACTIVE);
		assertMosipSignedCertificateDownloaded(partnerId);
	}

	@Test(priority = 4, description = "TC_1854_04 Verify Upload Root of Trust Certificate screen options")
	public void uploadRootOfTrustScreen() {
		PartnerCertificatePage partnerCertificatePage = openRootTrustUpload();

		LogUtil.step("Upload Root of Trust Certificate screen");
		Assert.assertEquals(partnerCertificatePage.getUploadRootOfTrustHeadingText(),
				GlobalConstants.UPLOAD_ROOT_OF_TRUST_HEADING, GlobalConstants.isPartnerAdminCertUploadTitleDisplayed);
		Assert.assertTrue(partnerCertificatePage.isSelectPartnerDomainPlaceHolderDisplayed(),
				GlobalConstants.isSelectPartnerDomainPlaceHolderDisplayed);
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		List<String> domains = List.of(partnerCertificatePage.getPartnerDomainOptionText(1),
				partnerCertificatePage.getPartnerDomainOptionText(2),
				partnerCertificatePage.getPartnerDomainOptionText(3));
		Assert.assertTrue(domains.contains(GlobalConstants.PARTNER_DOMAIN_AUTH),
				GlobalConstants.isPartnerDomainDropdownAuthDisplayed);
		Assert.assertTrue(domains.contains(GlobalConstants.PARTNER_DOMAIN_DEVICE),
				GlobalConstants.isPartnerDomainDropdownAuthDisplayed);
		Assert.assertTrue(domains.contains(GlobalConstants.PARTNER_DOMAIN_FTM),
				GlobalConstants.isPartnerDomainDropdownAuthDisplayed);
		Assert.assertEquals(partnerCertificatePage.getUploadTrustSectionDescriptionText(),
				GlobalConstants.TRUST_UPLOAD_SECTION_DESCRIPTION, GlobalConstants.isCertFormatesTextDisplayed);
		Assert.assertEquals(partnerCertificatePage.getTrustCertificateFormatText(),
				GlobalConstants.CERTIFICATE_FORMAT_MESSAGE, GlobalConstants.isCertFormatesTextDisplayed);
		Assert.assertTrue(partnerCertificatePage.isAdminCertUploadCancelButtonDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);
		Assert.assertTrue(partnerCertificatePage.isPartnerCertificatePageDisplayed(),
				GlobalConstants.isPartnerCertificatePageDisplayed);

		LogUtil.step("Back returns to the Root CA certificate list");
		partnerCertificatePage.clickOnTitleBackButton();
		Assert.assertTrue(partnerCertificatePage.isSubtitleOfRootCADisplayed(),
				GlobalConstants.isSubtitleOfRootCADisplayed);
	}

	@Test(priority = 5, description = "TC_1854_05 Verify upload fails when an invalid partner certificate is uploaded")
	public void uploadInvalidPartnerCertificate() {
		String partnerId = "ovp05" + BaseClass.data;
		createOnlineVerificationPartner(partnerId, "ovp05" + BaseClass.data + "@test.com");

		MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		mispPartnerPage.clickOnUploadPartnerCertificateButton();
		assertUploadPopup(partnerCertificatePage, partnerId, GlobalConstants.UPLOAD_PARTNER_CERTIFICATE_TITLE, false);

		LogUtil.step("Select a file that is not a .cer or .pem certificate");
		partnerCertificatePage.uploadResourceCertificate("invalid.txt");
		Assert.assertEquals(partnerCertificatePage.getUploadCertificateErrorText(),
				GlobalConstants.INVALID_CERTIFICATE_FORMAT_ERROR, GlobalConstants.isInvalidCertFormatePopupDisplayed);
		partnerCertificatePage.clickOnCertificateUploadCancelButton();
		mispPartnerPage.clickOnListOfPartnerButton();
		assertPartnerRowStatus(partnerId, GlobalConstants.CERT_UPLOAD_STATUS_NOT_UPLOADED,
				GlobalConstants.PARTNER_STATUS_INACTIVE);
	}

	@Test(priority = 6, description = "TC_1854_06 Verify re-upload failure does not impact the existing active certificate")
	public void reuploadFailureKeepsActiveCertificate() {
		String partnerId = "ovp06" + BaseClass.data;
		ensureAuthTrustChain();
		createOnlineVerificationPartner(partnerId, "ovp06" + BaseClass.data + "@test.com");
		uploadPartnerCertificateFromSuccessScreen("OvpClient06.cer");

		LogUtil.step("Re-upload an invalid certificate");
		filterPartner(partnerId);
		MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		mispPartnerPage.clickActionButtonByPartnerId(partnerId);
		mispPartnerPage.clickOnUploadOrReuploadCertificateButton();
		assertUploadPopup(partnerCertificatePage, partnerId, GlobalConstants.REUPLOAD_PARTNER_CERTIFICATE_TITLE, true);
		partnerCertificatePage.uploadResourceCertificate("invalid.txt");
		Assert.assertEquals(partnerCertificatePage.getUploadCertificateErrorText(),
				GlobalConstants.INVALID_CERTIFICATE_FORMAT_ERROR, GlobalConstants.isInvalidCertFormatePopupDisplayed);
		partnerCertificatePage.clickOnCertificateUploadCancelButton();

		assertPartnerRowStatus(partnerId, GlobalConstants.CERT_UPLOAD_STATUS_UPLOADED,
				GlobalConstants.PARTNER_STATUS_ACTIVE);
		assertMosipSignedCertificateDownloaded(partnerId);
	}

	@Test(priority = 7, description = "TC_1854_07 Verify Partner Admin can open Root of Trust certificates from the dashboard")
	public void openRootOfTrustFromDashboard() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);

		LogUtil.step("Open Certificate Trust Store from the home page");
		Assert.assertTrue(dashboardPage.isCertificateTrustStoreDisplayed(),
				GlobalConstants.isCertificateTrustStoreDisplayed);
		dashboardPage.clickOnCertificateTrustStore();
		Assert.assertTrue(partnerCertificatePage.isCertificateTrustStoreTitleDisplayed(),
				GlobalConstants.isCertificateTrustStoreTitleDisplayed);
		Assert.assertTrue(partnerCertificatePage.isRootCACertTabDisplayed(), GlobalConstants.isRootCACertTabDisplayed);
		Assert.assertTrue(partnerCertificatePage.isIntermediateCACertTabDisplayed(),
				GlobalConstants.isIntermediateCACertTabDisplayed);
		Assert.assertTrue(partnerCertificatePage.isUploadTrustCertificateButtonDisplayed(),
				GlobalConstants.isUploadTrustCertificateButtonDisplayed);

		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		Assert.assertEquals(partnerCertificatePage.getUploadRootOfTrustHeadingText(),
				GlobalConstants.UPLOAD_ROOT_OF_TRUST_HEADING, GlobalConstants.isPartnerAdminCertUploadTitleDisplayed);
		Assert.assertEquals(partnerCertificatePage.getUploadTrustSectionDescriptionText(),
				GlobalConstants.TRUST_UPLOAD_SECTION_DESCRIPTION, GlobalConstants.isCertFormatesTextDisplayed);
	}

	@Test(priority = 8, description = "TC_1854_08 Verify upload of Root and Intermediate CA certificates in .cer format")
	public void uploadRootAndIntermediateCertificate() {
		uploadTrustCertificate(false, "OvpTc08RootCA.cer");
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		Assert.assertEquals(partnerCertificatePage.getUploadedSuccessfullyMessageText(),
				GlobalConstants.ROOT_OF_TRUST_UPLOAD_SUCCESS, GlobalConstants.isUploadedSuccessfullyMessageDisplayed);
		partnerCertificatePage.clickOnGoBackButton();

		uploadTrustCertificate(true, "OvpTc08IntermediateCA.cer");
		Assert.assertEquals(partnerCertificatePage.getUploadedSuccessfullyMessageText(),
				GlobalConstants.ROOT_OF_TRUST_UPLOAD_SUCCESS, GlobalConstants.isUploadedSuccessfullyMessageDisplayed);
	}

	@Test(priority = 9, description = "TC_1854_09 Verify upload fails for an unsupported certificate format")
	public void uploadUnsupportedCertificateFormat() {
		PartnerCertificatePage partnerCertificatePage = openRootTrustUpload();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();

		LogUtil.step("Select a .txt file");
		partnerCertificatePage.uploadResourceCertificate("invalid.txt");
		Assert.assertEquals(partnerCertificatePage.getTrustCertificateErrorText(),
				GlobalConstants.INVALID_CERTIFICATE_FORMAT_ERROR, GlobalConstants.isInvalidCertFormatePopupDisplayed);
	}

	@Test(priority = 10, description = "TC_1854_10 Verify upload fails when certificate expiry is less than one year")
	public void uploadCertificateExpiringWithinOneYear() {
		PartnerCertificatePage partnerCertificatePage = openRootTrustUpload();
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadGeneratedCertificate("shortValidityRoot.cer");
		partnerCertificatePage.clickonSubmitButtonForAdmin();
		Assert.assertEquals(partnerCertificatePage.getTrustCertificateErrorText(),
				GlobalConstants.CERTIFICATE_EXPIRY_LESS_THAN_ONE_YEAR_ERROR,
				GlobalConstants.isCertificateExpiredErrorDisplayed);
	}

	@Test(priority = 11, description = "TC_1854_12 to TC_1854_23, TC_1854_34 to TC_1854_36 and TC_1854_55 Upload popup, deferred upload, and keyboard cancel")
	public void uploadPopupDeferredUploadAndKeyboardCancel() {
		String partnerId = "ovp11" + BaseClass.data;
		createOnlineVerificationPartner(partnerId, "ovp11" + BaseClass.data + "@test.com");

		MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		Assert.assertTrue(mispPartnerPage.isUploadPartnerCertificateButtonDisplayed(),
				GlobalConstants.isUploadPartnerCertificateButtonDisplayed);
		Assert.assertEquals(mispPartnerPage.getUploadPartnerCertificateButtonText(),
				GlobalConstants.UPLOAD_ONLINE_VERIFICATION_PARTNER_CERTIFICATE,
				GlobalConstants.isUploadPartnerCertificateButtonDisplayed);

		LogUtil.step("Open the upload popup from the success screen");
		mispPartnerPage.clickOnUploadPartnerCertificateButton();
		assertUploadPopup(partnerCertificatePage, partnerId, GlobalConstants.UPLOAD_PARTNER_CERTIFICATE_TITLE, false);
		Assert.assertTrue(partnerCertificatePage.isUploadCertificateIconDisplayed(),
				GlobalConstants.isUploadPartnerCertificateButtonDisplayed);
		Assert.assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(),
				GlobalConstants.PLEASE_TAP_TO_SELECT_CERTIFICATE);
		Assert.assertEquals(partnerCertificatePage.getPartnerCertificateFormatText(),
				GlobalConstants.CERTIFICATE_FORMAT_MESSAGE, GlobalConstants.isCertFormatesTextDisplayed);
		Assert.assertTrue(partnerCertificatePage.isPartnerCertificateSubmitDisabled(),
				GlobalConstants.isSubmitButtonForAdminDisabled);

		LogUtil.step("Tab reaches Cancel and Enter closes the popup");
		WebElement cancelButton = driver.findElement(By.id("certificate_upload_cancel_btn"));
		cancelButton.sendKeys(Keys.TAB);
		WebElement afterTab = driver.switchTo().activeElement();
		Assert.assertNotEquals(afterTab.getAttribute("id"), "certificate_upload_cancel_btn",
				GlobalConstants.isPartnerCertificatePageDisplayed);
		afterTab.sendKeys(Keys.chord(Keys.SHIFT, Keys.TAB));
		Assert.assertEquals(driver.switchTo().activeElement().getAttribute("id"), "certificate_upload_cancel_btn",
				GlobalConstants.isPartnerCertificatePageDisplayed);
		driver.switchTo().activeElement().sendKeys(Keys.ENTER);
		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.invisibilityOfElementLocated(By.id("upload_certificate_popup_title")));
		Assert.assertTrue(mispPartnerPage.isUploadPartnerCertificateButtonDisplayed(),
				GlobalConstants.isUploadPartnerCertificateButtonDisplayed);

		LogUtil.step("Deferred upload stays Not Uploaded and Inactive on the partner list");
		mispPartnerPage.clickOnListOfPartnerButton();
		PartnerAdminPage partnerAdminPage = new PartnerAdminPage(driver);
		Assert.assertTrue(partnerAdminPage.isSubTitleOfTabularViewsDisplayed(),
				GlobalConstants.isSubTitleOfTabularViewsDisplayed);
		assertPartnerRowStatus(partnerId, GlobalConstants.CERT_UPLOAD_STATUS_NOT_UPLOADED,
				GlobalConstants.PARTNER_STATUS_INACTIVE);
		mispPartnerPage.clickActionButtonByPartnerId(partnerId);
		mispPartnerPage.clickOnUploadOrReuploadCertificateButton();
		assertUploadPopup(partnerCertificatePage, partnerId, GlobalConstants.UPLOAD_PARTNER_CERTIFICATE_TITLE, true);
		partnerCertificatePage.clickOnCertificateUploadCancelButton();
		Assert.assertTrue(partnerAdminPage.isPartnerListLoaded(partnerId), GlobalConstants.isPartnerListLoaded);
	}

	@Test(priority = 12, description = "TC_1854_24, TC_1854_31 and TC_1854_32 Fetched certificate name, success message, and remove")
	public void fetchedCertificateNameAndRemove() {
		String partnerId = "ovp12" + BaseClass.data;
		createOnlineVerificationPartner(partnerId, "ovp12" + BaseClass.data + "@test.com");
		new MispPartnerPage(driver).clickOnUploadPartnerCertificateButton();

		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		LogUtil.step("Select a .cer file and then remove it before submit");
		partnerCertificatePage.uploadGeneratedCertificate("OvpClient.cer");
		Assert.assertEquals(partnerCertificatePage.getFetchCertificateSuccessText(),
				GlobalConstants.FETCH_CERTIFICATE_SUCCESS, GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		Assert.assertTrue(partnerCertificatePage.isFetchCertificateSuccessGreen(),
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		Assert.assertEquals(partnerCertificatePage.getUploadedPopupFileName(), "OvpClient.cer",
				GlobalConstants.isUploadedCertificateNameDisplayed);
		Assert.assertTrue(partnerCertificatePage.isCertificateRemoveButtonDisplayed(),
				GlobalConstants.isCertificateRemoveButtonDisplayed);
		partnerCertificatePage.clickOnRemoveCertificateButton();
		Assert.assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(),
				GlobalConstants.PLEASE_TAP_TO_SELECT_CERTIFICATE);
		Assert.assertTrue(partnerCertificatePage.isPartnerCertificateSubmitDisabled(),
				GlobalConstants.isSubmitButtonForAdminDisabled);
	}

	@Test(priority = 13, description = "TC_1854_26 to TC_1854_28 Future-dated, self-signed, and version 1 certificates are rejected")
	public void rejectedCertificatesStayInactive() {
		String partnerId = "ovp13" + BaseClass.data;
		ensureAuthTrustChain();
		createOnlineVerificationPartner(partnerId, "ovp13" + BaseClass.data + "@test.com");
		new MispPartnerPage(driver).clickOnUploadPartnerCertificateButton();
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);

		rejectPartnerCertificate(partnerCertificatePage, "OvpFuture.cer", GlobalConstants.FUTURE_DATED_CERTIFICATE_ERROR);
		rejectPartnerCertificate(partnerCertificatePage, "OvpSelfSigned.cer",
				GlobalConstants.SELF_SIGNED_CERTIFICATE_ERROR);
		rejectPartnerCertificate(partnerCertificatePage, "OvpV1.cer", GlobalConstants.VERSION_3_CERTIFICATE_ERROR);

		partnerCertificatePage.clickOnCertificateUploadCancelButton();
		new MispPartnerPage(driver).clickOnListOfPartnerButton();
		assertPartnerRowStatus(partnerId, GlobalConstants.CERT_UPLOAD_STATUS_NOT_UPLOADED,
				GlobalConstants.PARTNER_STATUS_INACTIVE);
	}

	@Test(priority = 14, description = "TC_1854_29 and TC_1854_30 View page shows the uploaded certificate expiry")
	public void certificateExpiryShownAfterUpload() {
		String partnerId = "ovp14" + BaseClass.data;
		ensureAuthTrustChain();
		createOnlineVerificationPartner(partnerId, "ovp14" + BaseClass.data + "@test.com");
		uploadPartnerCertificateFromSuccessScreen("OvpClientView.cer");

		filterPartner(partnerId);
		MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
		PartnerAdminPage partnerAdminPage = new PartnerAdminPage(driver);
		mispPartnerPage.clickActionButtonByPartnerId(partnerId);
		partnerAdminPage.clickOnViewButtonInListOfPartnerDetailsScreen();
		Assert.assertTrue(partnerAdminPage.isViewPartnersDetailsPageDisplayed(),
				GlobalConstants.isViewPartnerDetailsOpened);
		String expiry = partnerAdminPage.getViewCertificateExpiry();
		Assert.assertTrue(expiry.matches("\\d{2}/\\d{2}/\\d{4}, \\d{2}:\\d{2}:\\d{2}"),
				GlobalConstants.isCertificateDatesDisplayed + " displayed [" + expiry + "]");
		String expiryDate = LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Assert.assertTrue(expiry.startsWith(expiryDate),
				GlobalConstants.isCertificateDatesDisplayed + " displayed [" + expiry + "]");
	}

	@Test(priority = 15, description = "TC_1854_33 and TC_1854_37 to TC_1854_48 Re-upload popup and certificate replacement")
	public void reuploadPopupAndReplacement() {
		String partnerId = "ovp15" + BaseClass.data;
		ensureAuthTrustChain();
		createOnlineVerificationPartner(partnerId, "ovp15" + BaseClass.data + "@test.com");
		uploadPartnerCertificateFromSuccessScreen("OvpClientReupload.cer");

		filterPartner(partnerId);
		assertPartnerRowStatus(partnerId, GlobalConstants.CERT_UPLOAD_STATUS_UPLOADED,
				GlobalConstants.PARTNER_STATUS_ACTIVE);
		MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		mispPartnerPage.clickActionButtonByPartnerId(partnerId);
		mispPartnerPage.clickOnUploadOrReuploadCertificateButton();
		assertReuploadPopup(partnerCertificatePage, partnerId);
		partnerCertificatePage.clickOnCertificateUploadCancelButton();
		Assert.assertTrue(new PartnerAdminPage(driver).isPartnerListLoaded(partnerId),
				GlobalConstants.isPartnerListLoaded);

		LogUtil.step("Upload a replacement certificate");
		mispPartnerPage.clickActionButtonByPartnerId(partnerId);
		mispPartnerPage.clickOnUploadOrReuploadCertificateButton();
		partnerCertificatePage.uploadGeneratedCertificate("OvpClientReuploadNext.cer");
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertEquals(partnerCertificatePage.getCertificateUploadSuccessText(),
				GlobalConstants.OVP_CERTIFICATE_UPLOAD_SUCCESS,
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
		assertPartnerRowStatus(partnerId, GlobalConstants.CERT_UPLOAD_STATUS_UPLOADED,
				GlobalConstants.PARTNER_STATUS_ACTIVE);
		assertMosipSignedCertificateDownloaded(partnerId);
	}

	@Test(priority = 16, description = "TC_1854_11 Root certificate with expiry greater than one year uploads")
	public void rootCertificateValidBeyondOneYear() {
		uploadTrustCertificate(false, "longValidityRoot.cer");
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		Assert.assertEquals(partnerCertificatePage.getUploadedSuccessfullyMessageText(),
				GlobalConstants.ROOT_OF_TRUST_UPLOAD_SUCCESS, GlobalConstants.isUploadedSuccessfullyMessageDisplayed);
	}

	@Test(priority = 17, description = "TC_1854_56 Home page without a session opens the login page")
	public void sessionWithoutLoginReturnsToLoginPage() {
		LogUtil.step("Open the portal in a new browser with no session");
		ChromeOptions options = new ChromeOptions();
		if ("yes".equalsIgnoreCase(ConfigManager.getheadless())) {
			options.addArguments("--headless=new", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage",
					"--window-size=1920,1080");
		}
		org.openqa.selenium.WebDriver freshBrowser = new ChromeDriver(options);
		try {
			freshBrowser.get(envPathPmpUiv2);
			Assert.assertTrue(new LoginPage(freshBrowser).isLoginPageDisplayed(), GlobalConstants.isLoginPageDisplayed);
		} finally {
			freshBrowser.quit();
		}
	}

	private void ensureAuthTrustChain() {
		if (authTrustChainUploaded) {
			return;
		}
		uploadTrustCertificate(false, "OvpRootCA.cer");
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		Assert.assertTrue(partnerCertificatePage.isUploadedSuccessfullyMessageDisplayed(),
				GlobalConstants.isUploadedSuccessfullyMessageDisplayed);
		partnerCertificatePage.clickOnGoBackButton();
		uploadTrustCertificate(true, "OvpIntermediateCA.cer");
		Assert.assertTrue(partnerCertificatePage.isUploadedSuccessfullyMessageDisplayed(),
				GlobalConstants.isUploadedSuccessfullyMessageDisplayed);
		partnerCertificatePage.clickOnGoBackButton();
		partnerCertificatePage.clickOnHomeButton();
		authTrustChainUploaded = true;
	}

	private void uploadTrustCertificate(boolean intermediate, String fileName) {
		DashboardPage dashboardPage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		if (!intermediate) {
			dashboardPage.clickOnCertificateTrustStore();
			partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		} else {
			partnerCertificatePage.clickOnIntermediateCACertTab();
			partnerCertificatePage.clickOnIntermediateUploadTrustCertificateButtonInAdmin();
		}
		partnerCertificatePage.clickOnpartnerDomainSelectorDropdown();
		partnerCertificatePage.clickOnPartnerDomainSelectorDropdownOptionAuth();
		partnerCertificatePage.uploadGeneratedCertificate(fileName);
		partnerCertificatePage.clickonSubmitButtonForAdmin();
	}

	private PartnerCertificatePage openRootTrustUpload() {
		DashboardPage dashboardPage = new DashboardPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		dashboardPage.clickOnCertificateTrustStore();
		partnerCertificatePage.clickOnRootUploadTrustCertificateButtonInAdmin();
		return partnerCertificatePage;
	}

	private void createOnlineVerificationPartner(String partnerId, String email) {
		DashboardPage dashboardPage = new DashboardPage(driver);
		MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
		dashboardPage.clickOnPartners();
		mispPartnerPage.clickOnCreatePartnerButton();
		mispPartnerPage.clickOnPartnerTypeDropdown();
		mispPartnerPage.clickOnPartnerTypeOption(GlobalConstants.ONLINE_VERIFICATION_PARTNER);
		mispPartnerPage.selectFirstActivePolicyGroup();
		mispPartnerPage.selectNotificationLanguage(GlobalConstants.ABIS_NOTIFICATION_LANGUAGE);
		mispPartnerPage.enterPartnerOrganisation(GlobalConstants.ORGANISATION_NAME);
		mispPartnerPage.enterPartnerAddress(GlobalConstants.ABIS_ADDRESS);
		mispPartnerPage.enterPartnerContactNumber(GlobalConstants.ABIS_CONTACT_NUMBER);
		mispPartnerPage.enterEmailId(email);
		mispPartnerPage.enterUserName(partnerId);
		mispPartnerPage.clickOnCreatePartnerSubmitButton();
		Assert.assertTrue(mispPartnerPage.isCreatePartnerSuccessMsgDisplayed(),
				GlobalConstants.isCreatePartnerSuccessMsgDisplayed);
	}

	private void uploadPartnerCertificateFromSuccessScreen(String fileName) {
		MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
		PartnerCertificatePage partnerCertificatePage = new PartnerCertificatePage(driver);
		mispPartnerPage.clickOnUploadPartnerCertificateButton();
		partnerCertificatePage.uploadGeneratedCertificate(fileName);
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertEquals(partnerCertificatePage.getCertificateUploadSuccessText(),
				GlobalConstants.OVP_CERTIFICATE_UPLOAD_SUCCESS,
				GlobalConstants.isCertificateUploadSuccessMessageDisplayed);
		partnerCertificatePage.clickOnCloseButton();
	}

	private void assertUploadPopup(PartnerCertificatePage partnerCertificatePage, String partnerId, String title,
			boolean showsPartnerId) {
		Assert.assertEquals(partnerCertificatePage.getUploadCertificatePopupTitle(), title,
				GlobalConstants.isMispPartnerCertificatePopupDisplayed);
		if (showsPartnerId) {
			Assert.assertEquals(partnerCertificatePage.getCorrespondingPartnerIdText(), "# " + partnerId,
					GlobalConstants.isCorrespondingPartnerIdDisplayed);
		} else {
			Assert.assertEquals(partnerCertificatePage.getUploadCertificatePopupMessage(),
					GlobalConstants.SELECT_FIELDS_AND_UPLOAD_CERTIFICATE,
					GlobalConstants.isMispPartnerCertificatePopupDisplayed);
		}
		Assert.assertEquals(partnerCertificatePage.getPartnerType(), GlobalConstants.ONLINE_VERIFICATION_PARTNER,
				GlobalConstants.isOnlineVerificationPartnerSelectedSuccessfully);
		Assert.assertEquals(partnerCertificatePage.getPartnerDomainType(), GlobalConstants.PARTNER_DOMAIN_AUTH,
				GlobalConstants.isPartnerDomainTypeLabelDisplayed);
		Assert.assertTrue(partnerCertificatePage.isUploadPopupPartnerTypeDisabled(),
				GlobalConstants.isPartnerTypeValueDisabled);
		Assert.assertTrue(partnerCertificatePage.isUploadPopupPartnerDomainDisabled(),
				GlobalConstants.isPartnerDomainTypeValueDisabled);
	}

	private PartnerAdminPage filterPartner(String partnerId) {
		PartnerAdminPage partnerAdminPage = new PartnerAdminPage(driver);
		if (driver.findElements(By.id("partner_id_filter")).isEmpty()) {
			partnerAdminPage.clickOnFilterButton();
		}
		partnerAdminPage.enterPartnerIdInFilter(partnerId);
		partnerAdminPage.clickOnApplyFiltersBtn();
		Assert.assertTrue(partnerAdminPage.isPartnerListLoaded(partnerId), GlobalConstants.isPartnerListLoaded);
		return partnerAdminPage;
	}

	private void assertPartnerRowStatus(String partnerId, String certificateStatus, String partnerStatus) {
		if (!new PartnerAdminPage(driver).isPartnerListLoaded()) {
			new DashboardPage(driver).clickOnPartners();
		}
		filterPartner(partnerId);
		Assert.assertTrue(new MispPartnerPage(driver).isMispPartnerRowStatusDisplayed(partnerId, certificateStatus,
				partnerStatus), GlobalConstants.isStatusDisplayed);
	}

	private void assertReuploadPopup(PartnerCertificatePage partnerCertificatePage, String partnerId) {
		assertUploadPopup(partnerCertificatePage, partnerId, GlobalConstants.REUPLOAD_PARTNER_CERTIFICATE_TITLE, true);
		Assert.assertEquals(partnerCertificatePage.getReuploadWarningText(),
				GlobalConstants.REUPLOAD_CERTIFICATE_WARNING, GlobalConstants.isReUploadPartnerCertificateDisplayed);
		Assert.assertTrue(partnerCertificatePage.getLastCertificateUploadDateText()
				.startsWith(GlobalConstants.LAST_CERTIFICATE_UPLOADED_ON),
				GlobalConstants.isLastUploadTimeAndDateTextDisplayed);
		Assert.assertEquals(partnerCertificatePage.getPartnerCertificateFormatText(),
				GlobalConstants.CERTIFICATE_FORMAT_MESSAGE, GlobalConstants.isCertFormatesTextDisplayed);
		Assert.assertTrue(partnerCertificatePage.isUploadCertificateIconDisplayed(),
				GlobalConstants.isUploadPartnerCertificateButtonDisplayed);
		Assert.assertTrue(partnerCertificatePage.isPartnerCertificateSubmitDisabled(),
				GlobalConstants.isSubmitButtonForAdminDisabled);
	}

	private void rejectPartnerCertificate(PartnerCertificatePage partnerCertificatePage, String fileName,
			String expectedError) {
		LogUtil.step("Reject " + fileName);
		partnerCertificatePage.uploadGeneratedCertificate(fileName);
		partnerCertificatePage.clickOnSubmitButton();
		Assert.assertEquals(partnerCertificatePage.getUploadCertificateErrorText(), expectedError,
				GlobalConstants.isInvalidCertFormatePopupDisplayed);
		partnerCertificatePage.clickOnErrorCloseButton();
		partnerCertificatePage.clickOnRemoveCertificateButton();
		Assert.assertTrue(partnerCertificatePage.isPleaseTabToSelectTextDisplayed(),
				GlobalConstants.PLEASE_TAP_TO_SELECT_CERTIFICATE);
	}

	private void assertMosipSignedCertificateDownloaded(String partnerId) {
		PartnerAdminPage partnerAdminPage = new PartnerAdminPage(driver);
		MispPartnerPage mispPartnerPage = new MispPartnerPage(driver);
		mispPartnerPage.clickActionButtonByPartnerId(partnerId);
		partnerAdminPage.clickOnViewButtonInListOfPartnerDetailsScreen();
		Assert.assertTrue(partnerAdminPage.isViewPartnersDetailsPageDisplayed(),
				GlobalConstants.isViewPartnerDetailsOpened);
		partnerAdminPage.clickOnDownloadCertificateButtonInViewPartnerPage();
		Assert.assertTrue(partnerAdminPage.isMosipSignedCertificateDropdownDisplayed(),
				GlobalConstants.isCertificateDownloadOptionsDisplayed);
		partnerAdminPage.clickOnMosipSignedCertificateInViewPartnerPage();
		Assert.assertEquals(partnerAdminPage.getViewPartnerSuccessMessage(),
				GlobalConstants.MOSIP_SIGNED_CERTIFICATE_DOWNLOAD_SUCCESS, GlobalConstants.isCertificateDownloaded);
	}
}
