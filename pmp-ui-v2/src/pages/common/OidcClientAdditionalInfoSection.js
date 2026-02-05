import { useTranslation } from "react-i18next";
import PropTypes from "prop-types";
import { onPressEnterKey, isLangRTL, getOidcPlaceholderForLanguage, getOidcPlaceholderIdForLanguage, getAvailableOidcLanguages } from "../../utils/AppUtils";
import DropdownComponent from "./fields/DropdownComponent";
import Information from "./fields/Information";
import TextInputComponentWithDeleteButton from "./fields/TextInputComponentWithDeleteButton";
import expandToggleIcon from '../../svg/expand_toggle_icon.svg';

function OidcClientAdditionalInfoSection({
  isAdditionalInfoExpanded,
  setIsAdditionalInfoExpanded,
  isLoginLanguageRTL,
  additionalConfigRequired,
  forgotPasswordBanner,
  setForgotPasswordBanner,
  signUpBanner,
  setSignUpBanner,
  consentExpiry,
  handleConsentExpiryChange,
  consentExpiryError,
  userInfoResponseType,
  handleUserInfoResponseTypeChange,
  userInfoResponseTypeDropdownData,
  purposeType,
  handlePurposeTypeChange,
  purposeTypeDropdownData,
  purposeTitleEntries,
  addPurposeTitleEntry,
  updatePurposeTitleEntry,
  deletePurposeTitleEntry,
  purposeTitleErrors,
  purposeTitleDefaultError,
  purposeSubtitleEntries,
  addPurposeSubtitleEntry,
  updatePurposeSubtitleEntry,
  deletePurposeSubtitleEntry,
  purposeSubtitleErrors,
  purposeSubtitleDefaultError,
  languageDropdownData,
  styles
}) {
  const { t } = useTranslation();

  return (
    <div className="bg-snow-white px-7 py-4 mt-[1.5%] mb-4 rounded-lg shadow-md">
      <div
        className="flex items-center justify-between cursor-pointer"
        onClick={() => setIsAdditionalInfoExpanded(!isAdditionalInfoExpanded)}
        role="button"
        tabIndex="0"
        onKeyDown={(e) => onPressEnterKey(e, () => setIsAdditionalInfoExpanded(!isAdditionalInfoExpanded))}
      >
        <h3 className="text-lg font-semibold text-dark-blue">{t('createOidcClient.additionalInformation')}</h3>
        <img src={expandToggleIcon} alt="Toggle" className={`w-7 h-7 transform transition-transform ${isAdditionalInfoExpanded ? 'rotate-180' : ''}`} />
      </div>

      {isAdditionalInfoExpanded && (
        <div className="flex flex-col space-y-4 mt-3">
          <div className="border-b border-gray-200 mb-3"></div>
          
          {/* Toggles - Side by Side */}
          <div className="flex justify-between space-x-4">
            {/* Forgot Password Banner Toggle */}
            <div className="flex flex-col w-[48%]">
              <div className={`flex items-center ${isLoginLanguageRTL ? 'flex-row-reverse justify-end' : 'justify-start'} gap-3`}>
                <div className="flex items-center gap-1">
                  <label htmlFor="forgot_password_banner_toggle" className={`text-dark-blue text-sm font-semibold ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                    {t('createOidcClient.forgotPasswordBanner')}
                  </label>
                  <Information infoKey={t('createOidcClient.forgotPasswordBannerTooltip')} id='forgot_password_banner_info' />
                </div>
                <button
                  type="button"
                  role="switch"
                  aria-checked={forgotPasswordBanner}
                  aria-label={t('createOidcClient.forgotPasswordBanner')}
                  className={`relative inline-flex items-center cursor-pointer flex-shrink-0 ${isLoginLanguageRTL ? "" : "ml-7"} focus:outline focus:outline-2 focus:outline-[#1447B2] focus:outline-offset-2 rounded`}
                  onClick={() => additionalConfigRequired && setForgotPasswordBanner(!forgotPasswordBanner)}
                  disabled={!additionalConfigRequired}
                  tabIndex={additionalConfigRequired ? 0 : -1}
                >
                  <input
                    type="checkbox"
                    checked={forgotPasswordBanner}
                    onChange={(e) => additionalConfigRequired && setForgotPasswordBanner(e.target.checked)}
                    className="sr-only peer focus:outline-none"
                    id="forgot_password_banner_toggle"
                    tabIndex={-1}
                  />
                  <div className={`relative w-9 h-5 rounded-full transition-colors duration-200 ease-in-out ${forgotPasswordBanner ? 'bg-[#1447B2]' : 'bg-neutral-100'}`}>
                    <div className={`absolute top-0.5 left-0.5 w-4 h-4 bg-white rounded-full shadow-sm transform transition-transform duration-200 ease-in-out ${forgotPasswordBanner ? 'translate-x-4' : ''}`}></div>
                  </div>
                </button>
              </div>
            </div>

            {/* SignUp Banner Toggle */}
            <div className="flex flex-col w-[48%]">
              <div className={`flex items-center ${isLoginLanguageRTL ? 'flex-row-reverse justify-end' : 'justify-start'} gap-3`}>
                <div className="flex items-center gap-1">
                  <label htmlFor="signup_banner_toggle" className={`text-dark-blue text-sm font-semibold ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                    {t('createOidcClient.signUpBanner')}
                  </label>
                  <Information infoKey={t('createOidcClient.signUpBannerTooltip')} id='signup_banner_info' />
                </div>
                <button
                  type="button"
                  role="switch"
                  aria-checked={signUpBanner}
                  aria-label={t('createOidcClient.signUpBanner')}
                  className={`relative inline-flex items-center cursor-pointer flex-shrink-0 ${isLoginLanguageRTL ? "" : "ml-7"} focus:outline focus:outline-2 focus:outline-[#1447B2] focus:outline-offset-2 rounded`}
                  onClick={() => additionalConfigRequired && setSignUpBanner(!signUpBanner)}
                  disabled={!additionalConfigRequired}
                  tabIndex={additionalConfigRequired ? 0 : -1}
                >
                  <input
                    type="checkbox"
                    checked={signUpBanner}
                    onChange={(e) => additionalConfigRequired && setSignUpBanner(e.target.checked)}
                    className="sr-only peer focus:outline-none"
                    id="signup_banner_toggle"
                    tabIndex={-1}
                  />
                  <div className={`relative w-9 h-5 rounded-full transition-colors duration-200 ease-in-out ${signUpBanner ? 'bg-[#1447B2]' : 'bg-neutral-100'}`}>
                    <div className={`absolute top-0.5 left-0.5 w-4 h-4 bg-white rounded-full shadow-sm transform transition-transform duration-200 ease-in-out ${signUpBanner ? 'translate-x-4' : ''}`}></div>
                  </div>
                </button>
              </div>
            </div>
          </div>

          {/* Consent Expiry Duration and User Info Response Type - Side by Side */}
          <div className="flex flex-row justify-between space-x-4">
            {/* Consent Expiry Duration */}
            <div className="flex flex-col w-[48%]">
              <label id="consent_expiry_label" htmlFor="consent_expiry_input" className={`flex items-center gap-1 text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                {t('createOidcClient.consentExpiryDuration')}
                <Information infoKey={t('createOidcClient.consentExpiryDurationTooltip')} id='consent_expiry_info' />
              </label>
              <input
                id="consent_expiry_input"
                value={consentExpiry}
                onChange={(e) => handleConsentExpiryChange(e.target.value)}
                className={`h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue ${additionalConfigRequired ? "bg-white" : "bg-platinum-gray"}  leading-tight focus:outline-none focus:shadow-outline w-full`}
                placeholder={t('createOidcClient.consentExpiryPlaceholder')}
                data-placeholder-id="consent_expiry_input_placeholder"
                readOnly={!additionalConfigRequired}
              />
              {consentExpiryError && <span id="consent_expiry_error" className="text-sm text-crimson-red font-semibold mt-1">{consentExpiryError}</span>}
            </div>

            {/* User Info Response Type */}
            <div className="flex flex-col w-[48%]">
              <DropdownComponent
                fieldName='userInfoResponseType'
                dropdownDataList={userInfoResponseTypeDropdownData}
                onDropDownChangeEvent={handleUserInfoResponseTypeChange}
                fieldNameKey='createOidcClient.userInfoResponseType'
                placeHolderKey='createOidcClient.selectUserInfoResponseType'
                selectedDropdownValue={userInfoResponseType}
                styleSet={styles}
                addInfoIcon={true}
                infoKey={t('createOidcClient.userInfoResponseTypeTooltip')}
                isPlaceHolderPresent={true}
                id='user_info_response_type'
                disabled={!additionalConfigRequired} />
            </div>
          </div>

          {/* Purpose Type - Full Width */}
          <div className="flex flex-col w-[48%] mb-2">
            <DropdownComponent
              fieldName='purposeType'
              dropdownDataList={purposeTypeDropdownData}
              onDropDownChangeEvent={handlePurposeTypeChange}
              fieldNameKey='createOidcClient.purposeType'
              placeHolderKey='createOidcClient.selectPurposeType'
              selectedDropdownValue={purposeType}
              styleSet={styles}
              addInfoIcon={true}
              infoKey={t('createOidcClient.purposeTypeTooltip')}
              isPlaceHolderPresent={true}
              id='purpose_type'
              disabled={!additionalConfigRequired} />
          </div>

          {/* Purpose Title - shown only when Purpose Type is selected */}
          {purposeType && (
            <div className="flex flex-col">
              <label id="purpose_title_label" className={`flex items-center text-dark-blue text-sm font-semibold mb-2 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                {t('createOidcClient.purposeTitle')}
                <Information infoKey={t('createOidcClient.purposeTitleTooltip')} id='purpose_title_info' />
              </label>
              {purposeTitleEntries.length === 0 ? (
                <div className="bg-white border border-neutral-200 rounded-md p-8 flex flex-col items-center justify-center min-h-[120px]">
                  <button
                    type="button"
                    id="add_purpose_title_language_btn"
                    className={`${additionalConfigRequired ? 'bg-[#1447b2] text-white hover:bg-[#0f3a8a] cursor-pointer' : 'bg-[#A5A5A5] text-white'} font-semibold text-sm px-6 py-2 rounded-md transition-colors`}
                    tabIndex={additionalConfigRequired ? 0 : -1}
                    onKeyDown={additionalConfigRequired ? (e) => onPressEnterKey(e, addPurposeTitleEntry) : undefined}
                    onClick={additionalConfigRequired ? addPurposeTitleEntry : undefined}
                    disabled={!additionalConfigRequired}
                  >
                    {t('createOidcClient.addTitle')}
                  </button>
                  <p className="text-gray-400 text-sm mt-2 text-center">
                    {t('createOidcClient.addTitleHelperText')}
                  </p>
                </div>
              ) : (
                <div className="bg-white border border-neutral-300 shadow-sm rounded-md p-4">
                  {purposeTitleEntries.map((entry, index) => {
                    const availableLangs = getAvailableOidcLanguages(entry.id, purposeTitleEntries, languageDropdownData);
                    return (
                      <div key={entry.id} className="flex mb-2">
                        <div className="w-1/3">
                          <DropdownComponent
                            fieldName={`purposeTitleLang_${entry.id}`}
                            dropdownDataList={availableLangs}
                            onDropDownChangeEvent={(field, value) => updatePurposeTitleEntry(entry.id, 'language', value)}
                            fieldNameKey=""
                            placeHolderKey="createOidcClient.selectLanguage"
                            selectedDropdownValue={entry.language}
                            styleSet={styles}
                            id={`purpose_title_lang_${entry.id}`}
                            disabled={!additionalConfigRequired} />
                        </div>
                        <div className={`w-full mt-1 ${isLoginLanguageRTL ? 'mr-5' : 'ml-5'}`}>
                          <TextInputComponentWithDeleteButton
                            value={entry.text}
                            onChange={(e) => updatePurposeTitleEntry(entry.id, 'text', e.target.value)}
                            onDelete={() => deletePurposeTitleEntry(entry.id)}
                            placeholder={getOidcPlaceholderForLanguage(entry.language, 'PurposeTitle', t)}
                            placeholderId={getOidcPlaceholderIdForLanguage(entry.language, 'PurposeTitle', t)}
                            id={`purpose_title_text_${entry.id}`}
                            showDelete={purposeTitleEntries.length > 0}
                            errorMessage={purposeTitleErrors[entry.id]}
                            isRTL={isLangRTL(entry.language)}
                            languageCode={entry.language}
                            disabled={!additionalConfigRequired}
                          />
                        </div>
                      </div>
                    );
                  })}
                  {purposeTitleEntries.length < languageDropdownData.length && (
                    <div
                      role="button"
                      id="add_purpose_title_language_link"
                      className={`font-bold text-xs w-fit ${additionalConfigRequired ? 'text-[#1447b2] cursor-pointer' : 'text-gray-400'}`}
                      tabIndex={additionalConfigRequired ? 0 : -1}
                      onKeyDown={additionalConfigRequired ? (e) => onPressEnterKey(e, addPurposeTitleEntry) : undefined}
                      onClick={additionalConfigRequired ? addPurposeTitleEntry : undefined}
                    >
                      <span className="text-lg text-center">+</span>
                      <span>{t('createOidcClient.addNew')}</span>
                    </div>
                  )}
                </div>
              )}
              {purposeTitleDefaultError && (
                <span className="text-sm text-crimson-red font-semibold mt-2">{purposeTitleDefaultError}</span>
              )}
            </div>
          )}

          {/* Purpose Subtitle - shown only when Purpose Type is selected */}
          {purposeType && (
            <div className="flex flex-col">
              <label id="purpose_subtitle_label" className={`flex items-center text-dark-blue text-sm font-semibold mb-2 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                {t('createOidcClient.purposeSubtitle')}
                <Information infoKey={t('createOidcClient.purposeSubtitleTooltip')} id='purpose_subtitle_info' />
              </label>
              {purposeSubtitleEntries.length === 0 ? (
                <div className="bg-white border border-neutral-200 rounded-md p-8 flex flex-col items-center justify-center min-h-[120px]">
                  <button
                    type="button"
                    id="add_purpose_subtitle_language_btn"
                    className={`${additionalConfigRequired ? 'bg-[#1447b2] text-white hover:bg-[#0f3a8a] cursor-pointer' : 'bg-[#A5A5A5] text-white'} font-semibold text-sm px-6 py-2 rounded-md transition-colors`}
                    tabIndex={additionalConfigRequired ? 0 : -1}
                    onKeyDown={additionalConfigRequired ? (e) => onPressEnterKey(e, addPurposeSubtitleEntry) : undefined}
                    onClick={additionalConfigRequired ? addPurposeSubtitleEntry : undefined}
                    disabled={!additionalConfigRequired}
                  >
                    {t('createOidcClient.addSubtitle')}
                  </button>
                  <p className="text-gray-400 text-sm mt-2 text-center">
                    {t('createOidcClient.addSubtitleHelperText')}
                  </p>
                </div>
              ) : (
                <div className="bg-white border border-neutral-300 shadow-sm rounded-md p-4">
                  {purposeSubtitleEntries.map((entry, index) => {
                    const availableLangs = getAvailableOidcLanguages(entry.id, purposeSubtitleEntries, languageDropdownData);
                    return (
                      <div key={entry.id} className="flex mb-2">
                        <div className="w-1/3">
                          <DropdownComponent
                            fieldName={`purposeSubtitleLang_${entry.id}`}
                            dropdownDataList={availableLangs}
                            onDropDownChangeEvent={(field, value) => updatePurposeSubtitleEntry(entry.id, 'language', value)}
                            fieldNameKey=""
                            placeHolderKey="createOidcClient.selectLanguage"
                            selectedDropdownValue={entry.language}
                            styleSet={styles}
                            id={`purpose_subtitle_lang_${entry.id}`}
                            disabled={!additionalConfigRequired} />
                        </div>
                        <div className={`w-full mt-1 ${isLoginLanguageRTL ? 'mr-5' : 'ml-5'}`}>
                          <TextInputComponentWithDeleteButton
                            value={entry.text}
                            onChange={(e) => updatePurposeSubtitleEntry(entry.id, 'text', e.target.value)}
                            onDelete={() => deletePurposeSubtitleEntry(entry.id)}
                            placeholder={getOidcPlaceholderForLanguage(entry.language, 'PurposeSubtitle', t)}
                            placeholderId={getOidcPlaceholderIdForLanguage(entry.language, 'PurposeSubtitle')}
                            id={`purpose_subtitle_text_${entry.id}`}
                            showDelete={purposeSubtitleEntries.length > 0}
                            errorMessage={purposeSubtitleErrors[entry.id]}
                            isRTL={isLangRTL(entry.language)}
                            languageCode={entry.language}
                            disabled={!additionalConfigRequired}
                          />
                        </div>
                      </div>
                    );
                  })}
                  {purposeSubtitleEntries.length < languageDropdownData.length && (
                    <div
                      role="button"
                      id="add_purpose_subtitle_language_link"
                      className={`font-bold text-xs w-fit ${additionalConfigRequired ? 'text-[#1447b2] cursor-pointer' : 'text-gray-400'}`}
                      tabIndex={additionalConfigRequired ? 0 : -1}
                      onKeyDown={additionalConfigRequired ? (e) => onPressEnterKey(e, addPurposeSubtitleEntry) : undefined}
                      onClick={additionalConfigRequired ? addPurposeSubtitleEntry : undefined}
                    >
                      <span className="text-lg text-center">+</span>
                      <span>{t('createOidcClient.addNew')}</span>
                    </div>
                  )}
                </div>
              )}
              {purposeSubtitleDefaultError && (
                <span className="text-sm text-crimson-red font-semibold mt-2">{purposeSubtitleDefaultError}</span>
              )}
            </div>
          )}
        </div>
      )}
    </div>
  );
}

OidcClientAdditionalInfoSection.propTypes = {
  isAdditionalInfoExpanded: PropTypes.bool.isRequired,
  setIsAdditionalInfoExpanded: PropTypes.func.isRequired,
  isLoginLanguageRTL: PropTypes.bool.isRequired,
  additionalConfigRequired: PropTypes.bool.isRequired,
  forgotPasswordBanner: PropTypes.bool.isRequired,
  setForgotPasswordBanner: PropTypes.func.isRequired,
  signUpBanner: PropTypes.bool.isRequired,
  setSignUpBanner: PropTypes.func.isRequired,
  consentExpiry: PropTypes.string.isRequired,
  handleConsentExpiryChange: PropTypes.func.isRequired,
  consentExpiryError: PropTypes.string.isRequired,
  userInfoResponseType: PropTypes.string.isRequired,
  handleUserInfoResponseTypeChange: PropTypes.func.isRequired,
  userInfoResponseTypeDropdownData: PropTypes.arrayOf(
    PropTypes.shape({
      fieldCode: PropTypes.string,
      fieldValue: PropTypes.string
    })
  ).isRequired,
  purposeType: PropTypes.string.isRequired,
  handlePurposeTypeChange: PropTypes.func.isRequired,
  purposeTypeDropdownData: PropTypes.arrayOf(
    PropTypes.shape({
      fieldCode: PropTypes.string,
      fieldValue: PropTypes.string
    })
  ).isRequired,
  purposeTitleEntries: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.string.isRequired,
      language: PropTypes.string.isRequired,
      text: PropTypes.string.isRequired
    })
  ).isRequired,
  addPurposeTitleEntry: PropTypes.func.isRequired,
  updatePurposeTitleEntry: PropTypes.func.isRequired,
  deletePurposeTitleEntry: PropTypes.func.isRequired,
  purposeTitleErrors: PropTypes.object.isRequired,
  purposeTitleDefaultError: PropTypes.string.isRequired,
  purposeSubtitleEntries: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.string.isRequired,
      language: PropTypes.string.isRequired,
      text: PropTypes.string.isRequired
    })
  ).isRequired,
  addPurposeSubtitleEntry: PropTypes.func.isRequired,
  updatePurposeSubtitleEntry: PropTypes.func.isRequired,
  deletePurposeSubtitleEntry: PropTypes.func.isRequired,
  purposeSubtitleErrors: PropTypes.object.isRequired,
  purposeSubtitleDefaultError: PropTypes.string.isRequired,
  languageDropdownData: PropTypes.arrayOf(
    PropTypes.shape({
      fieldCode: PropTypes.string,
      fieldValue: PropTypes.string
    })
  ).isRequired,
  styles: PropTypes.shape({
    outerDiv: PropTypes.string,
    dropdownLabel: PropTypes.string,
    dropdownButton: PropTypes.string,
    selectionBox: PropTypes.string
  }).isRequired
};

export default OidcClientAdditionalInfoSection;

