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
  id,
  maxLength,
  showDelete = false,
  errorMessage,
  className = "",
  containerClassName = "",
  isRTL = null
}) {
  const { t } = useTranslation();
  const isLoginLanguageRTL = isRTL !== null ? isRTL : isLangRTL(getUserProfile().locale);

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
          className={`h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline w-full ${showDelete ? 'pr-28' : ''} ${className}`}
          id={id}
        />
        {showDelete && (
          <div
            role='button'
            id={`${id}_delete`}
            className="absolute right-2 top-1/2 -translate-y-1/2 flex flex-row items-center"
            onClick={handleDelete}
            tabIndex="0"
            onKeyDown={(e) => onPressEnterKey(e, handleDelete)}
          >
            <img src={deleteIconBlue} alt="Delete" className="w-[18px] h-5 mx-1 cursor-pointer" />
            <p className="text-sm font-normal text-[#1447b2] cursor-pointer">
              {t('createOidcClient.delete')}
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
  id: PropTypes.string.isRequired,
  maxLength: PropTypes.number,
  showDelete: PropTypes.bool,
  errorMessage: PropTypes.string,
  className: PropTypes.string,
  containerClassName: PropTypes.string,
  isRTL: PropTypes.bool
};

export default TextInputComponentWithDeleteButton;

