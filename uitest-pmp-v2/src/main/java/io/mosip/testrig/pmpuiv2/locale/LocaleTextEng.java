package io.mosip.testrig.pmpuiv2.locale;

// All localized text for uitest-pmp-v2, English (default/base language) - one file per language,
// sectioned by page, rather than a file-per-page-per-language. Field names are shared verbatim
// across LocaleTextEng/Fra/Ara; each page class's init() picks one of these three classes based
// on the active login language and copies in just the fields that page owns.
public class LocaleTextEng {

	// ==== MISP Services ====

	// Source: pmp-ui-v2/public/i18n/eng.json, generateLicenseKey.dateFormatInfoKey
	public static final String EXPIRY_DATE_CALENDER_INFO_TEXT = "Date Format: mm/dd/yyyy. Ensure that you provide a valid future date, as the license key will be automatically deactivated once this date is reached.";

	// MISP License List - tabular view, filters, deactivate/regenerate flows
	// Source: pmp-ui-v2/public/i18n/eng.json, mispLicenseList.*
	public static final String MISP_LIST_HEADER_PARTNER_ID = "Partner ID";
	public static final String MISP_LIST_HEADER_ORG_NAME = "Organisation";
	public static final String MISP_LIST_HEADER_POLICY_GROUP = "Policy Group";
	public static final String MISP_LIST_HEADER_POLICY_NAME = "Policy Name";
	public static final String MISP_LIST_HEADER_LICENSE_KEY_NAME = "MISP License Key Name";
	public static final String MISP_LIST_HEADER_CREATION_DATE = "Creation Date";
	public static final String MISP_LIST_HEADER_EXPIRATION_DATE = "Expiration Date";
	public static final String MISP_LIST_HEADER_STATUS = "Status";
	public static final String MISP_LIST_HEADER_LICENSE_KEY = "MISP License Key";
	public static final String MISP_LIST_HEADER_ACTION = "Action";

	// Source: pmp-ui-v2/public/i18n/eng.json, mispLicenseList.listOfMispLicenseKeys (rendered as "<text> (<count>)" by FilterButtons)
	public static final String LIST_OF_MISP_LICENSE_KEYS_SUBTITLE_TEXT = "List of MISP License Keys";

	// Source: pmp-ui-v2/public/i18n/eng.json, mispLicenseList.searchMispLicenseKeyName
	public static final String MISP_LICENSE_KEY_NAME_SEARCH_PLACEHOLDER = "Search MISP License Key Name";

	// Source: pmp-ui-v2/public/i18n/eng.json, partnerList.noResultsFound
	public static final String NO_RESULTS_FOUND_TEXT = "No Results Found";

	// Regenerate MISP License Key
	// Source: pmp-ui-v2/src/pages/admin/mispPartnerServices/RegenerateMispLicenseKey.js, regenerateMispLicenseKey.*
	public static final String REGENERATE_MISP_LICENSE_KEY_PAGE_TITLE = "Regenerate MISP License Key";

	// Rendered by common/Title.js as "<commons.home>" + "/ <mispLicenseList.mispPartnerServices>"
	public static final String REGENERATE_BREADCRUMB_TEXT = "Home/ MISP Services";

	// Rendered as requestPolicy.mandatoryFieldsMsg1 + " * " + requestPolicy.mandatoryFieldsMsg2
	public static final String REGENERATE_MANDATORY_FIELDS_SUBTITLE_TEXT = "All fields marked with * are mandatory.";

	// Source: pmp-ui-v2/public/i18n/eng.json, regenerateMispLicenseKey.noPolicySelected
	public static final String NO_POLICY_NAME_SELECTED = "No Policy Name selected";

	// Source: pmp-ui-v2/public/i18n/eng.json, generateLicenseKey.enterLicenseKeyName
	public static final String REGENERATE_LICENSE_KEY_NAME_HELP_TEXT = "Enter a name for MISP License key";

	// Source: pmp-ui-v2/public/i18n/eng.json, mispLicenseList.mispLicenseKey
	public static final String MISP_LICENSE_KEY_POPUP_HEADER_TEXT = "MISP License Key";

	// Source: pmp-ui-v2/public/i18n/eng.json, regenerateMispLicenseKey.regenerateLicenseKeySuccessHeader
	public static final String REGENERATE_LICENSE_KEY_CONFIRMATION_HEADER_TEXT = "MISP License Key is regenerated successfully!";

	// MISP Services page title, reused across regenerate/deactivate navigation checks
	// Source: pmp-ui-v2/public/i18n/eng.json, mispLicenseList.mispPartnerServices
	public static final String MISP_SERVICES_PAGE_TITLE = "MISP Services";

	// ==== API Key - Edit Expiry (MOSIP-38157) ====
	// Strings verified directly against the live QA build (pmp.qajava21.mosip.net), not the source repo.

	public static final String API_KEY_EDIT_EXPIRY_PAGE_TITLE = "Edit API key expiry";
	public static final String API_KEY_PARTNER_ID_LABEL = "Partner ID";
	public static final String API_KEY_PARTNER_TYPE_LABEL = "Partner Type";
	public static final String API_KEY_ORGANISATION_LABEL = "Organisation";
	public static final String API_KEY_POLICY_GROUP_LABEL = "Policy Group";
	public static final String API_KEY_POLICY_NAME_LABEL = "Policy Name";
	public static final String API_KEY_POLICY_GROUP_DESCRIPTION_LABEL = "Policy Group Description";
	public static final String API_KEY_POLICY_NAME_DESCRIPTION_LABEL = "Policy Name Description";
	public static final String API_KEY_UPDATE_EXPIRY_TITLE = "Update Expiry Date";
	public static final String API_KEY_EXPIRY_DATE_LABEL = "Expiry Date*";
	public static final String API_KEY_UNDO_CHANGES_BTN_TEXT = "Undo Changes";
	public static final String API_KEY_CANCEL_BTN_TEXT = "Cancel";
	public static final String API_KEY_SUBMIT_BTN_TEXT = "Submit";
	public static final String API_KEY_CONFIRMATION_GO_BACK_BTN_TEXT = "Go Back";
	public static final String API_KEY_CONFIRMATION_HOME_BTN_TEXT = "Home";
	// {0} is replaced with the API key name, e.g. "Expiry date for API key IJWFIJWF has been updated successfully."
	public static final String API_KEY_CONFIRMATION_MESSAGE_TEMPLATE = "Expiry date for API key {0} has been updated successfully.";
	public static final String API_KEY_ACTION_VIEW_OPTION_TEXT = "View";
	public static final String API_KEY_ACTION_EDIT_EXPIRY_OPTION_TEXT = "Edit Expiry Date";
	public static final String API_KEY_ACTION_DEACTIVATE_OPTION_TEXT = "Deactivate";

	// ==== Add further pages' localized text below as they gain multilanguage support ====

}
