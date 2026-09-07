package io.mosip.testrig.pmpuiv2.locale;

// All localized text for uitest-pmp-v2, French - one file per language, sectioned by page.
// Same field names as LocaleTextEng/Ara; see LocaleTextEng for each field's UI source reference.
public class LocaleTextFra {

	// ==== MISP Services ====

	public static final String EXPIRY_DATE_CALENDER_INFO_TEXT = "Format de date : jj/mm/aaaa. Assurez-vous de fournir une date future valide, car la clé de licence sera automatiquement désactivée une fois cette date atteinte.";

	public static final String MISP_LIST_HEADER_PARTNER_ID = "ID partenaire";
	public static final String MISP_LIST_HEADER_ORG_NAME = "Organisation";
	public static final String MISP_LIST_HEADER_POLICY_GROUP = "Groupe de politiques";
	public static final String MISP_LIST_HEADER_POLICY_NAME = "Nom de la politique";
	public static final String MISP_LIST_HEADER_LICENSE_KEY_NAME = "Nom de la clé de licence MISP";
	public static final String MISP_LIST_HEADER_CREATION_DATE = "Date de création";
	public static final String MISP_LIST_HEADER_EXPIRATION_DATE = "Date d'expiration";
	public static final String MISP_LIST_HEADER_STATUS = "Statut";
	public static final String MISP_LIST_HEADER_LICENSE_KEY = "Clé de licence MISP";
	public static final String MISP_LIST_HEADER_ACTION = "Action";

	public static final String LIST_OF_MISP_LICENSE_KEYS_SUBTITLE_TEXT = "Liste des clés de licence MISP";

	public static final String MISP_LICENSE_KEY_NAME_SEARCH_PLACEHOLDER = "Rechercher le nom de la clé de licence MISP";

	public static final String NO_RESULTS_FOUND_TEXT = "Aucun résultat trouvé";

	public static final String REGENERATE_MISP_LICENSE_KEY_PAGE_TITLE = "Régénérer la clé de licence MISP";

	public static final String REGENERATE_BREADCRUMB_TEXT = "Maison/ Services MISP";

	public static final String REGENERATE_MANDATORY_FIELDS_SUBTITLE_TEXT = "Tous les champs marqués d'un * sont obligatoires.";

	public static final String NO_POLICY_NAME_SELECTED = "Aucun nom de politique sélectionné";

	public static final String REGENERATE_LICENSE_KEY_NAME_HELP_TEXT = "Entrez un nom pour la clé de licence MISP";

	public static final String MISP_LICENSE_KEY_POPUP_HEADER_TEXT = "Clé de licence MISP";

	public static final String REGENERATE_LICENSE_KEY_CONFIRMATION_HEADER_TEXT = "La clé de licence MISP a été régénérée avec succès !";

	public static final String MISP_SERVICES_PAGE_TITLE = "Services MISP";

	// ==== API Key - Edit Expiry (MOSIP-38157) ====
	// Not yet verified against the live French UI (only Eng/Ara were checked for MOSIP-38157) - the
	// API_KEY_* fields are deliberately omitted here rather than filled with guessed text; ApiKeyPage's
	// French run just won't populate them until someone verifies and adds the real strings.

	// ==== Add further pages' localized text below as they gain multilanguage support ====

}
