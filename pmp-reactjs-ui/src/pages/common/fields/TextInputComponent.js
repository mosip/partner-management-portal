import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';

function TextInputComponent({ fieldName, fieldNameKey, placeHolderKey, textBoxValue, onTextChange, styleSet, id }) {
    const { t } = useTranslation();
    const [inputValue, setInputValue] = useState("");
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const handleInputChange = (event) => {
        const newValue = event.target.value;
        setInputValue(newValue);
        onTextChange(fieldName, newValue);
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
            </label>
            <div className={`flex border border-[#707070] rounded-[4px] text-[15px] items-center`}>
                <input
                    id={id}
                    type="text"
                    value={inputValue}
                    onChange={handleInputChange}
                    placeholder={t(placeHolderKey)}
                    className={`rounded-[4px] h-auto w-full min-w-fit p-2 focus:outline-none items-center ${styleSet?.inputField || ''}`}
                />
                {inputValue && (
                    <p onClick={onTextClear} className={`bg-white font-bold rounded-md px-2 ${isLoginLanguageRTL ? '-mr-6' : '-ml-6'} focus:outline-none items-center hover:cursor-pointer`}>
                        x
                    </p>
                )}
            </div>
        </div>
    );
}

export default TextInputComponent;
