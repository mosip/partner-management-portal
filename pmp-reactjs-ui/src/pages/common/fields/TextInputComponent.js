import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';

function TextInputComponent({ fieldName, fieldNameKey, placeHolderKey, onTextChange, styleSet, id }) {
    const { t } = useTranslation();
    const [inputValue, setInputValue] = useState('');
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);


    const handleInputChange = (event) => {
        const newValue = event.target.value;
        setInputValue(newValue);
        onTextChange(fieldName, newValue);
    };

    return (
        <div className={`ml-4 mb-2 ${(styleSet && styleSet.outerDiv) ? styleSet.outerDiv : ''}`}>
            <label className={`flex items-center text-dark-blue text-sm mb-2 ${(styleSet && styleSet.inputLabel) ? styleSet.inputLabel : ''}`}>
                <p className={`font-semibold`}>{t(fieldNameKey)}</p>
            </label>
            <div className={`flex border border-[#707070] rounded-[4px] text-[15px] items-center`}>
                <input id={id} type="text" value={inputValue} onChange={handleInputChange} placeholder={t(placeHolderKey)} className={`rounded-md h-[2.1rem] ${isLoginLanguageRTL ? 'pr-3' : 'pl-3'} focus:outline-none items-center ${styleSet && styleSet.inputField ? styleSet.inputField : ''}`} />
                {inputValue &&
                    (<p onClick={() => setInputValue('')} className={`bg-white font-bold rounded-md px-2 ${isLoginLanguageRTL ? '-mr-6' : '-ml-6'} focus:outline-none items-center hover:cursor-pointer`}>
                        x
                    </p>
                    )}
            </div>
        </div>
    );
}

export default TextInputComponent;
