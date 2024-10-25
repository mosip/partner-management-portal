import { useState } from 'react';
import { useTranslation } from 'react-i18next';

function TextInputComponent({ fieldName, fieldNameKey, placeHolderKey, onTextChange, styleSet, id }) {
    const { t } = useTranslation();
    const [inputValue, setInputValue] = useState('');

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

            <input
                id={id}
                type="text"
                value={inputValue}
                onChange={handleInputChange}
                placeholder={t(placeHolderKey)}
                className={`w-full px-2 py-2 h-[2.3rem] border border-[#707070] rounded-[4px] text-[15px] 
                    ${styleSet && styleSet.inputField ? styleSet.inputField : ''} focus:outline-none `}
            />
        </div>
    );
}

export default TextInputComponent;
