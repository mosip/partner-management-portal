import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import Information from './Information';
import PropTypes from 'prop-types';

function TextInputComponent({ fieldName, fieldNameKey, placeHolderKey, textBoxValue, onTextChange, styleSet, id, maxLength, addInfoIcon, infoKey, disableField, inputError }) {
    const { t } = useTranslation();
    const [inputValue, setInputValue] = useState("");
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

    const handleInputChange = (event) => {
        const newValue = event.target.value;
        setInputValue(newValue);
        onTextChange(fieldName, newValue.trim());
    };

    const onTextClear = () => {
        setInputValue("");
        onTextChange(fieldName, "");
    };

    useEffect(() => {
        if (textBoxValue !== undefined && textBoxValue !== null) {
            setInputValue(textBoxValue);
        }
    }, [textBoxValue]);

    const containsAsterisk = fieldNameKey.includes('*');
    fieldNameKey = containsAsterisk ? fieldNameKey.replace('*', '') : fieldNameKey;

    return (
        <div className={`mb-2 ${styleSet?.outerDiv || ''}`}>
            <label className={`flex items-center text-dark-blue text-sm mb-1 ${styleSet?.inputLabel || ''}`}>
                <p className={`font-semibold`}>{t(fieldNameKey)}{containsAsterisk && <span className={`text-crimson-red mx-1`}>*</span>}</p>
                {addInfoIcon && (
                    <Information infoKey={infoKey} id={id + '_info'}/>
                )}
            </label>
            <div className={`flex border border-[#707070] rounded-[4px] text-[15px] items-center`}>
                <input
                    id={id}
                    type="text"
                    value={inputValue}
                    onChange={handleInputChange}
                    placeholder={t(placeHolderKey)}
                    {...(maxLength && { maxLength })}
                    readOnly={disableField}
                    className={`${disableField ? 'bg-platinum-gray': 'bg-white'} rounded-[4px] h-9 w-full p-2 focus:outline-none items-center ${styleSet?.inputField || ''}`}
                />
                {inputValue && !disableField && (
                    <button
                    onClick={onTextClear}
                    className={`flex items-center bg-white font-bold rounded-md px-2 min-h-9 ${isLoginLanguageRTL ? '-mr-6' : '-ml-6'} focus:ring-2 focus:ring-blue-500 items-center hover:cursor-pointer`}
                    tabIndex="0"
                    type="button"
                >
                    x
                </button>
                )}
            </div>
            {inputError && <span className="text-sm text-crimson-red font-semibold">{inputError}</span>}
        </div>
    );
}

TextInputComponent.propTypes = {
    fieldName: PropTypes.string.isRequired,
    fieldNameKey: PropTypes.string.isRequired,
    placeHolderKey: PropTypes.string.isRequired,
    textBoxValue: PropTypes.string.isRequired,
    onTextChange: PropTypes.func.isRequired,
    styleSet: PropTypes.object.isRequired,
    id: PropTypes.string.isRequired,
    maxLength: PropTypes.number.isRequired,
    addInfoIcon: PropTypes.bool.isRequired,
    infoKey: PropTypes.string.isRequired,
    disableField: PropTypes.bool.isRequired,
    inputError: PropTypes.string.isRequired,
};

export default TextInputComponent;
