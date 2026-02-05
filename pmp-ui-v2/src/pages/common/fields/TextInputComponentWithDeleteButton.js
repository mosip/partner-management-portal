import { useTranslation } from 'react-i18next';
import { isLangRTL, onPressEnterKey } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import deleteIconBlue from '../../../svg/delete_icon_blue.svg';
import PropTypes from 'prop-types';

function TextInputComponentWithDeleteButton({
  value,
  onChange,
  onDelete,
  placeholder,
  placeholderId,
  id,
  maxLength,
  showDelete = false,
  errorMessage,
  className = "",
  containerClassName = "",
  isRTL = null,
  languageCode = null,
  disabled = false
}) {
  const { t } = useTranslation();
  const isLoginLanguageRTL = isRTL !== null ? isRTL : isLangRTL(getUserProfile().locale);
  
  // Get delete label in the target language
  const getDeleteLabel = () => {
    if (!languageCode || languageCode === 'default') {
      return t('createOidcClient.delete');
    }
    
    const langCode = languageCode.toLowerCase();
    const deleteKey = `createOidcClient.deleteIn${langCode.charAt(0).toUpperCase() + langCode.slice(1)}`;
    
    // Use the currently loaded resource bundle
    const deleteLabel = t(deleteKey);
    
    // Fallback to default if translation not found
    return deleteLabel === deleteKey ? t('createOidcClient.delete') : deleteLabel;
  };

  const handleDelete = () => {
    if (onDelete) {
      onDelete();
    }
  };

  return (
    <div className={`${containerClassName}`}>
      <div className="relative">
        <input
          value={value}
          onChange={onChange}
          maxLength={maxLength}
          placeholder={placeholder}
          data-placeholder-id={placeholderId ?? `${id}_placeholder`}
          dir={isLoginLanguageRTL ? 'rtl' : 'ltr'}
          disabled={disabled}
          className={`h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue ${disabled ? 'bg-platinum-gray' : 'bg-white'} leading-tight focus:outline-none focus:shadow-outline w-full ${showDelete ? (isLoginLanguageRTL ? 'pl-28' : 'pr-28') : ''} ${className}`}
          id={id}
        />
        {showDelete && (
          <div
            role='button'
            id={`${id}_delete`}
            className={`absolute ${isLoginLanguageRTL ? 'left-2' : 'right-2'} top-1/2 -translate-y-1/2 flex flex-row items-center`}
            onClick={disabled ? undefined : handleDelete}
            tabIndex={disabled ? -1 : 0}
            onKeyDown={disabled ? undefined : (e) => onPressEnterKey(e, handleDelete)}
            style={{ pointerEvents: disabled ? 'none' : 'auto' }}
          >
            <img src={deleteIconBlue} alt="Delete" className={`w-[18px] h-5 mx-1 ${disabled ? 'opacity-50 cursor-not-allowed' : 'cursor-pointer'}`} />
            <p className={`text-sm font-normal ${disabled ? 'text-gray-400 cursor-not-allowed' : 'text-[#1447b2] cursor-pointer'}`}>
              {getDeleteLabel()}
            </p>
          </div>
        )}
      </div>
      {errorMessage && (
        <span className={`block text-sm text-crimson-red font-semibold mt-1 ${isLoginLanguageRTL ? 'mr-5' : 'ml-5'}`}>
          {errorMessage}
        </span>
      )}
    </div>
  );
}

TextInputComponentWithDeleteButton.propTypes = {
  value: PropTypes.string.isRequired,
  onChange: PropTypes.func.isRequired,
  onDelete: PropTypes.func,
  placeholder: PropTypes.string,
  placeholderId: PropTypes.string,
  id: PropTypes.string.isRequired,
  maxLength: PropTypes.number,
  showDelete: PropTypes.bool,
  errorMessage: PropTypes.string,
  className: PropTypes.string,
  containerClassName: PropTypes.string,
  isRTL: PropTypes.bool,
  languageCode: PropTypes.string,
  disabled: PropTypes.bool
};

export default TextInputComponentWithDeleteButton;

