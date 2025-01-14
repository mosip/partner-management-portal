
import { useState, useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { isLangRTL, handleMouseClickForDropdown } from '../../../utils/AppUtils';
import {  } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import Information from './Information';

function DropdownComponent({ fieldName, dropdownDataList, onDropDownChangeEvent, fieldNameKey,
    placeHolderKey, selectedDropdownValue, styleSet, addInfoIcon, infoKey, disabled, isPlaceHolderPresent, changeDropdownBackground, id}) {

    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const [selectedDropdownEntry, setSelectedDropdownEntry] = useState("");
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const dropdownRef = useRef(null);

    const containsAsterisk = fieldNameKey.includes('*');
    fieldNameKey = containsAsterisk ? fieldNameKey.replace('*', '') : fieldNameKey;

    useEffect(() => {
        handleMouseClickForDropdown(dropdownRef, () => setIsDropdownOpen(false));
    }, [dropdownRef]);

    useEffect(() => {
        setSelectedDropdownEntry(selectedDropdownValue || "");
    }, [selectedDropdownValue]);

    useEffect(() => {
        if (dropdownDataList.length === 0 && selectedDropdownEntry !== "") {
            setSelectedDropdownEntry("");
        }
    }, [dropdownDataList, selectedDropdownEntry]);

    const changeDropdownSelection = (selectedid) => {
        setSelectedDropdownEntry(selectedid);
        setIsDropdownOpen(false);
        onDropDownChangeEvent(fieldName, selectedid);
    };
    const openDropdown = () => {
        if (!disabled) {
            setIsDropdownOpen(!isDropdownOpen);
        }
    };

    return (
        <div key={fieldName} className={`ml-4 mb-2 ${(styleSet && styleSet.outerDiv) ? styleSet.outerDiv : ''}`}>
            <label className={`flex items-center text-dark-blue text-sm mb-2 ${(styleSet && styleSet.dropdownLabel) ? styleSet.dropdownLabel : ''} ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                <p className={`font-semibold`}>{t(fieldNameKey)}{containsAsterisk && <span className={`text-crimson-red mx-1`}>*</span>}</p>
                {addInfoIcon && (
                    <Information infoKey={infoKey} id={id + '_info'}/>
                )}
            </label>

            <div className="relative w-full" ref={dropdownRef}>
                <button id={id} onClick={openDropdown} disabled={disabled} className={`flex items-center justify-between w-fit h-auto px-2 py-2 border border-[#707070] ${disabled ? 'bg-platinum-gray' : changeDropdownBackground ? 'bg-[#EBEBEB]' : 'bg-white'}
                 rounded-[4px] text-[15px] ${selectedDropdownEntry ? 'text-[#343434]' : 'text-grayish-blue'} leading-tight
                    focus:shadow-none overflow-x-auto whitespace-normal no-scrollbar ${(styleSet && styleSet.dropdownButton) ? styleSet.dropdownButton : ''}`} type="button">
                    <span className='w-full break-all text-wrap text-start'>{
                        selectedDropdownEntry ?
                            dropdownDataList.map(dropdownItem => { return (selectedDropdownEntry === dropdownItem.fieldValue ? dropdownItem.fieldCode : '') })
                            : t(placeHolderKey)}
                    </span>
                    <svg className={`w-3 h-2 ${isLoginLanguageRTL ? "mr-3" :"ml-3"} transform ${isDropdownOpen ? 'rotate-180' : 'rotate-0'} text-gray-500 text-sm`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                    </svg>
                </button>
                {isDropdownOpen && (
                    <div className={`z-30 top-10 left-0 w-full ${(styleSet && styleSet.selectionBox) ? styleSet.selectionBox : ''}`}>
                        <div className="absolute mt-auto z-10 border border-gray-400 scroll-auto bg-white rounded-t-[2px] shadow-lg w-full cursor-pointer">
                            {dropdownDataList.length === 0 && (
                                <div className="min-h-3 p-4 cursor-auto">
                                    <p className="text-base text-dark-blue font-semibold">{t('commons.emptyMsg')}</p>
                                </div>
                            )}
                            <div className="max-h-40 overflow-y-auto">
                                {dropdownDataList.map((dropdownItem, index) => {
                                    return (
                                        <div key={index} className="min-h-3">
                                            <button id={isPlaceHolderPresent ? (index > 0 ? id + '_option' + (index) : undefined) : id + '_option' + (index + 1)}
                                                className={`block w-full h-auto px-4 py-1 text-sm text-dark-blue overflow-x-auto whitespace-normal no-scrollbar break-words
                                                    ${isPlaceHolderPresent && index === 0 ? 'text-gray-500' : 'text-dark-blue'}
                                                    ${selectedDropdownEntry === dropdownItem.fieldValue ? 'bg-gray-100' : 'hover:bg-gray-100'} ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}
                                                onClick={() => changeDropdownSelection(dropdownItem.fieldValue)}>
                                                {dropdownItem.fieldCode}
                                            </button>
                                            <div className="border-gray-200 border-t mx-2"></div>
                                        </div>
                                    )
                                })}
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

export default DropdownComponent;