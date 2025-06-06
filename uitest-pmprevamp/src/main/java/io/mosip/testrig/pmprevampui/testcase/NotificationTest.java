package io.mosip.testrig.pmprevampui.testcase;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.mosip.testrig.pmprevampui.pages.DashboardPage;
import io.mosip.testrig.pmprevampui.pages.NotificationPage;
import io.mosip.testrig.pmprevampui.utility.BaseClass;
import io.mosip.testrig.pmprevampui.utility.GlobalConstants;

public class NotificationTest extends BaseClass {

	@Test(priority = 1, description = "Partner Admin Notification Panel")
	public void notificationPanelOfPartnerAdmin() {

		DashboardPage dashboardPage = new DashboardPage(driver);

		assertTrue(dashboardPage.isNotificationIconDisplayed(), GlobalConstants.isNotificationIconDisplayed);
		NotificationPage notificationPage = dashboardPage.clickOnNotificationIcon();
		assertTrue(notificationPage.isNotificationPanelDisplayed(), GlobalConstants.isNotificationPanelDisplayed);
		assertTrue(notificationPage.isNotificationPanelTitleDisplayed(),
				GlobalConstants.isNotificationPanelTitleDisplayed);
		assertTrue(notificationPage.isNotificationPanelSubtitleDisplayed(),
				GlobalConstants.isNotificationPanelSubtitleDisplayed);
		assertTrue(notificationPage.isDateTimeInNotificationPanelDisplayed(),
				GlobalConstants.isDateTimeInNotificationPanelDisplayed);
		assertTrue(notificationPage.isDescriptionOfNotificationsDisplayed(),
				GlobalConstants.isDescriptionOfNotificationsDisplayed);
		notificationPage.clickOnDismissButton();
		assertFalse(notificationPage.isDescriptionOfNotificationsDisplayed(),
				GlobalConstants.isDescriptionOfNotificationsDisplayed);

		notificationPage.clickOnViewAllNotificationsButton();
		assertTrue(notificationPage.isRootCACertificateTabDisplayed(), GlobalConstants.isRootCACertificateTabDisplayed);

	}

	@Test(priority = 2, description = "view all notifications of root ca")
	public void viewAllNotificationsOfRootCA() {

		DashboardPage dashboardPage = new DashboardPage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(dashboardPage.isNotificationIconDisplayed(), GlobalConstants.isNotificationIconDisplayed);
		NotificationPage notificationPage = dashboardPage.clickOnNotificationIcon();

		notificationPage.clickOnViewAllNotificationsButton();
		assertTrue(notificationPage.isRootCACertificateTabDisplayed(), GlobalConstants.isRootCACertificateTabDisplayed);
		assertTrue(notificationPage.isIntermediateCACertificateTabDisplayed(),
				GlobalConstants.isIntermediateCACertificateTabDisplayed);
		assertTrue(notificationPage.isWeeklySummaryTabDisplayed(), GlobalConstants.isWeeklySummaryTabDisplayed);

		assertTrue(notificationPage.isViewAllNotificationsTitleDisplayed(),
				GlobalConstants.isViewAllNotificationsTitleDisplayed);
		assertTrue(notificationPage.isBreadcumbDisplayed(), GlobalConstants.isBreadcumbDisplayed);

		assertTrue(notificationPage.isViewAllNotificationsSubtitleDisplayed(),
				GlobalConstants.isViewAllNotificationsSubtitleDisplayed);
		assertTrue(notificationPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);

		notificationPage.clickOnFilterButton();
		assertFalse(notificationPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);
		assertTrue(notificationPage.isResetFilterButtonEnabled(), GlobalConstants.isResetFilterButtonEnabled);
		assertFalse(notificationPage.isApplyFilterButtonEnabled(), GlobalConstants.isApplyFilterButtonEnabled);

		assertTrue(notificationPage.isCertIdFilterTextBoxDisplayed(), GlobalConstants.isCertIdFilterTextBoxDisplayed);
		assertTrue(notificationPage.isCertIssuedByFilterTextBoxDisplayed(),
				GlobalConstants.isCertIssuedByFilterTextBoxDisplayed);
		assertTrue(notificationPage.isCertIssuedToFilterTextBoxDisplayed(),
				GlobalConstants.isCertIssuedToFilterTextBoxDisplayed);
		assertTrue(notificationPage.isCertExpiryDateFilterTextBoxDisplayed(),
				GlobalConstants.isCertExpiryDateFilterTextBoxDisplayed);
		assertTrue(notificationPage.isCertPartnerDomainFilterDropdownDisplayed(),
				GlobalConstants.isCertPartnerDomainFilterDropdownDisplayed);
		
		assertTrue(notificationPage.isCertificateIdLabelDisplayed(),
				GlobalConstants.isCertificateIdLabelDisplayed);
		assertTrue(notificationPage.isIssuedToLabelDisplayed(),
				GlobalConstants.isIssuedToLabelDisplayed);
		assertTrue(notificationPage.isIssuedByLabelDisplayed(),
				GlobalConstants.isIssuedByLabelDisplayed);
		assertTrue(notificationPage.isPartnerDomainLabelDisplayed(),
				GlobalConstants.isPartnerDomainLabelDisplayed);
		assertTrue(notificationPage.isCertificateExpiryDateLabelDisplayed(),
				GlobalConstants.isCertificateExpiryDateLabelDisplayed);
		
		assertTrue(notificationPage.isCertificateIdPlaceHolderDisplayed(),
				GlobalConstants.isCertificateIdPlaceHolderDisplayed);
		assertTrue(notificationPage.isIssuedByPlaceHolderDisplayed(),
				GlobalConstants.isIssuedByPlaceHolderDisplayed);
		assertTrue(notificationPage.isIssuedToPlaceHolderDisplayed(),
				GlobalConstants.isIssuedToPlaceHolderDisplayed);
		assertTrue(notificationPage.isCertificateExpiryDatePlaceHolderDisplayed(),
				GlobalConstants.isCertificateExpiryDatePlaceHolderDisplayed);
		assertTrue(notificationPage.isPartnerDomainPlaceHolderDisplayed(),
				GlobalConstants.isPartnerDomainPlaceHolderDisplayed);
		
		notificationPage.clickOnPartnerDomainFilterDropdown();
		assertTrue(notificationPage.isPartnerDomainAuthInDropdownDisplayed(),
				GlobalConstants.isPartnerDomainAuthInDropdownDisplayed);
		assertTrue(notificationPage.isPartnerDomainDeviceInDropdownDisplayed(),
				GlobalConstants.isPartnerDomainDeviceInDropdownDisplayed);
		assertTrue(notificationPage.isPartnerDomainFTMInDropdownDisplayed(),
				GlobalConstants.isPartnerDomainFTMInDropdownDisplayed);
		notificationPage.clickOnPartnerDomainFilterDropdown();
		
		notificationPage.clickOnCertExpiryDateFilterTextbox();
		assertTrue(notificationPage.isDatePickerFormatDisplayed(),
				GlobalConstants.isDatePickerFormatDisplayed);
		notificationPage.enterInvalidExpiryDateInFilter(GlobalConstants.INVALID_EXPIRY_DATE);
		notificationPage.clickOnApplyFilterButton();
		assertTrue(notificationPage.isNoNotificationsFoundDisplayed(),
				GlobalConstants.isNoNotificationsFoundDisplayed);
		notificationPage.clickOnResetFilterButton();
		
		notificationPage.clickOnFilterButton();
		notificationPage.enterCertIssuedToInFilter(GlobalConstants.INVALID_DATA);
		notificationPage.clickOnApplyFilterButton();
		assertTrue(notificationPage.isNoNotificationsFoundDisplayed(),
				GlobalConstants.isNoNotificationsFoundDisplayed);
		notificationPage.clickOnTextBoxCloseButton();
		notificationPage.enterCertIssuedToInFilter("C=IN");
		notificationPage.enterCertIssuedByInFilter("C=IN");
		notificationPage.selectPartnerDomainAsAuthInFilter();
		notificationPage.clickOnApplyFilterButton();
		assertTrue(notificationPage.isTitleOfExpiryRootCACertificateDisplayed(),
				GlobalConstants.isTitleOfExpiryRootCACertificateDisplayed);
		assertTrue(notificationPage.isExpiryCACertDateAndTimeDisplayed(),
				GlobalConstants.isExpiryCACertDateAndTimeDisplayed);
		assertTrue(notificationPage.isFeaturedIconDisplayed(),
				GlobalConstants.isFeaturedIconDisplayed);
		notificationPage.clickOnCACertDismissButton();
			
		assertTrue(notificationPage.isPaginationDisplayed(), GlobalConstants.isPaginationDisplayed);
		assertTrue(notificationPage.isPreviusPageButtonDisplayed(), GlobalConstants.isPreviusPageButtonDisplayed);
		assertTrue(notificationPage.isNextPageButtonDisplayed(), GlobalConstants.isNextPageButtonDisplayed);

		assertTrue(notificationPage.isPrefixOfPageDisplayed(), GlobalConstants.isPrefixOfPageDisplayed);
		assertTrue(notificationPage.isRecordPerPageDisplayed(), GlobalConstants.isRecordPerPageDisplayed);
		assertTrue(notificationPage.isexpandIconDisplayed(), GlobalConstants.isexpandIconDisplayed);

	}
	
	@Test(priority = 3, description = "view all notifications of int ca")
	public void viewAllNotificationsOfIntCA() {

		DashboardPage dashboardPage = new DashboardPage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(dashboardPage.isNotificationIconDisplayed(), GlobalConstants.isNotificationIconDisplayed);
		NotificationPage notificationPage = dashboardPage.clickOnNotificationIcon();
		
		notificationPage.clickOnViewAllNotificationsButton();
		assertTrue(notificationPage.isRootCACertificateTabDisplayed(), GlobalConstants.isRootCACertificateTabDisplayed);
		assertTrue(notificationPage.isIntermediateCACertificateTabDisplayed(),
				GlobalConstants.isIntermediateCACertificateTabDisplayed);
		assertTrue(notificationPage.isWeeklySummaryTabDisplayed(), GlobalConstants.isWeeklySummaryTabDisplayed);

		notificationPage.clickOnIntermediateCACertificateTab();
		assertTrue(notificationPage.isViewAllNotificationsTitleDisplayed(),
				GlobalConstants.isViewAllNotificationsTitleDisplayed);
		assertTrue(notificationPage.isBreadcumbDisplayed(), GlobalConstants.isBreadcumbDisplayed);
		assertTrue(notificationPage.isViewAllNotificationsSubtitleDisplayed(),
				GlobalConstants.isViewAllNotificationsSubtitleDisplayed);
		
		assertTrue(notificationPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);
		notificationPage.clickOnFilterButton();
		assertFalse(notificationPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);
		assertTrue(notificationPage.isResetFilterButtonEnabled(), GlobalConstants.isResetFilterButtonEnabled);
		assertFalse(notificationPage.isApplyFilterButtonEnabled(), GlobalConstants.isApplyFilterButtonEnabled);

		assertTrue(notificationPage.isCertIdFilterTextBoxDisplayed(), GlobalConstants.isCertIdFilterTextBoxDisplayed);
		assertTrue(notificationPage.isCertIssuedByFilterTextBoxDisplayed(),
				GlobalConstants.isCertIssuedByFilterTextBoxDisplayed);
		assertTrue(notificationPage.isCertIssuedToFilterTextBoxDisplayed(),
				GlobalConstants.isCertIssuedToFilterTextBoxDisplayed);
		assertTrue(notificationPage.isCertExpiryDateFilterTextBoxDisplayed(),
				GlobalConstants.isCertExpiryDateFilterTextBoxDisplayed);
		assertTrue(notificationPage.isCertPartnerDomainFilterDropdownDisplayed(),
				GlobalConstants.isCertPartnerDomainFilterDropdownDisplayed);
		
		assertTrue(notificationPage.isCertificateIdLabelDisplayed(),
				GlobalConstants.isCertificateIdLabelDisplayed);
		assertTrue(notificationPage.isIssuedToLabelDisplayed(),
				GlobalConstants.isIssuedToLabelDisplayed);
		assertTrue(notificationPage.isIssuedByLabelDisplayed(),
				GlobalConstants.isIssuedByLabelDisplayed);
		assertTrue(notificationPage.isPartnerDomainLabelDisplayed(),
				GlobalConstants.isPartnerDomainLabelDisplayed);
		assertTrue(notificationPage.isCertificateExpiryDateLabelDisplayed(),
				GlobalConstants.isCertificateExpiryDateLabelDisplayed);
		
		assertTrue(notificationPage.isCertificateIdPlaceHolderDisplayed(),
				GlobalConstants.isCertificateIdPlaceHolderDisplayed);
		assertTrue(notificationPage.isIssuedByPlaceHolderDisplayed(),
				GlobalConstants.isIssuedByPlaceHolderDisplayed);
		assertTrue(notificationPage.isIssuedToPlaceHolderDisplayed(),
				GlobalConstants.isIssuedToPlaceHolderDisplayed);
		assertTrue(notificationPage.isCertificateExpiryDatePlaceHolderDisplayed(),
				GlobalConstants.isCertificateExpiryDatePlaceHolderDisplayed);
		assertTrue(notificationPage.isPartnerDomainPlaceHolderDisplayed(),
				GlobalConstants.isPartnerDomainPlaceHolderDisplayed);
		
		notificationPage.clickOnCertExpiryDateFilterTextbox();
		assertTrue(notificationPage.isDatePickerFormatDisplayed(),
				GlobalConstants.isDatePickerFormatDisplayed);
		notificationPage.enterInvalidExpiryDateInFilter(GlobalConstants.INVALID_EXPIRY_DATE);
		notificationPage.clickOnApplyFilterButton();
		assertTrue(notificationPage.isNoNotificationsFoundDisplayed(),
				GlobalConstants.isNoNotificationsFoundDisplayed);
		notificationPage.clickOnResetFilterButton();
		
		notificationPage.clickOnFilterButton();
		notificationPage.enterCertIssuedToInFilter(GlobalConstants.INVALID_DATA);
		notificationPage.clickOnApplyFilterButton();
		assertTrue(notificationPage.isNoNotificationsFoundDisplayed(),
				GlobalConstants.isNoNotificationsFoundDisplayed);
		notificationPage.clickOnTextBoxCloseButton();
		notificationPage.enterCertIssuedToInFilter("C=IN");
		notificationPage.enterCertIssuedByInFilter("C=IN");
		notificationPage.selectPartnerDomainAsAuthInFilter();
		notificationPage.clickOnApplyFilterButton();
		assertTrue(notificationPage.isTitleOfExpiryIntCACertificateDisplayed(),
				GlobalConstants.isTitleOfExpiryIntCACertificateDisplayed);
		assertTrue(notificationPage.isExpiryCACertDateAndTimeDisplayed(),
				GlobalConstants.isExpiryCACertDateAndTimeDisplayed);
		assertTrue(notificationPage.isFeaturedIconDisplayed(),
				GlobalConstants.isFeaturedIconDisplayed);
		notificationPage.clickOnCACertDismissButton();
	}
	
	@Test(priority = 4, description = "view all notifications of weekly summary")
	public void viewAllNotificationsOfWeeklyPartner() {

		DashboardPage dashboardPage = new DashboardPage(driver);

		dashboardPage.clickOnCertificateTrustStore();
		assertTrue(dashboardPage.isNotificationIconDisplayed(), GlobalConstants.isNotificationIconDisplayed);
		NotificationPage notificationPage = dashboardPage.clickOnNotificationIcon();
		
		notificationPage.clickOnViewAllNotificationsButton();
		assertTrue(notificationPage.isRootCACertificateTabDisplayed(), GlobalConstants.isRootCACertificateTabDisplayed);
		assertTrue(notificationPage.isIntermediateCACertificateTabDisplayed(),
				GlobalConstants.isIntermediateCACertificateTabDisplayed);
		assertTrue(notificationPage.isWeeklySummaryTabDisplayed(), GlobalConstants.isWeeklySummaryTabDisplayed);

		notificationPage.clickOnWeeklySummaryTab();
		assertTrue(notificationPage.isViewAllNotificationsTitleDisplayed(),
				GlobalConstants.isViewAllNotificationsTitleDisplayed);
		assertTrue(notificationPage.isBreadcumbDisplayed(), GlobalConstants.isBreadcumbDisplayed);
		assertTrue(notificationPage.isViewAllNotificationsSubtitleDisplayed(),
				GlobalConstants.isViewAllNotificationsSubtitleDisplayed);
		
		assertTrue(notificationPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);
		notificationPage.clickOnFilterButton();
		assertFalse(notificationPage.isFilterButtonEnabled(), GlobalConstants.isFilterButtonEnabled);
		assertTrue(notificationPage.isResetFilterButtonEnabled(), GlobalConstants.isResetFilterButtonEnabled);
		assertFalse(notificationPage.isApplyFilterButtonEnabled(), GlobalConstants.isApplyFilterButtonEnabled);

		assertTrue(notificationPage.isCreationDateFromTextboxDisplayed(), GlobalConstants.isCreationDateFromTextboxDisplayed);
		assertTrue(notificationPage.isCreationDateToTextboxDisplayed(), GlobalConstants.isCreationDateToTextboxDisplayed);
		assertTrue(notificationPage.isCreationFromDateFilterLabelDisplayed(), GlobalConstants.isCreationFromDateFilterLabelDisplayed);
		assertTrue(notificationPage.isCreationToDateFilterLabelDisplayed(), GlobalConstants.isCreationToDateFilterLabelDisplayed);
		assertTrue(notificationPage.isCreationFromDatePlaceHolderDisplayed(), GlobalConstants.isCreationFromDatePlaceHolderDisplayed);
		assertTrue(notificationPage.isCreationToDatePlaceHolderDisplayed(), GlobalConstants.isCreationToDatePlaceHolderDisplayed);
		
		notificationPage.enterCreationFromDateInFilter(GlobalConstants.INVALID_EXPIRY_DATE);
		notificationPage.enterCreationToDateInFilter(GlobalConstants.INVALID_EXPIRY_DATE);
		notificationPage.clickOnApplyFilterButton();
		assertTrue(notificationPage.isNoNotificationsFoundDisplayed(),
				GlobalConstants.isNoNotificationsFoundDisplayed);
		notificationPage.clickOnResetFilterButton();
		
	}
	
}