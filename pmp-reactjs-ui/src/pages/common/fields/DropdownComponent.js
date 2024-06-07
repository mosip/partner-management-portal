
import { useState, useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { handleMouseClickForDropdown } from '../../../utils/AppUtils';
import infoIcon from '../../../svg/info_icon.svg';
import { isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';

function DropdownComponent({ fieldName, dropdownDataList, onDropDownChangeEvent, fieldNameKey, 
    placeHolderKey, selectedDropdownValue, styleSet, addInfoIcon, infoKey}) {

    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const [selectedDropdownEntry, setSelectedDropdownEntry] = useState("");
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const [showTooltip, setShowTooltip] = useState(false);
    const dropdownRef = useRef(null);

    const containsAsterisk = fieldNameKey.includes('*');
    fieldNameKey = containsAsterisk ? fieldNameKey.replace('*', '') : fieldNameKey;

    useEffect(() => {
        const clickOutSideDropdown = handleMouseClickForDropdown(dropdownRef, () => setIsDropdownOpen(false));
        return clickOutSideDropdown;
    }, [dropdownRef]);

    useEffect(() => {
        setSelectedDropdownEntry(selectedDropdownValue || "");
    }, [selectedDropdownValue]);

    const changeDropdownSelection = (selectedid) => {
        setSelectedDropdownEntry(selectedid);
        setIsDropdownOpen(false);
        onDropDownChangeEvent(fieldName, selectedid);
    };
    const openDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };

    const handleIconClick = () => {
        setShowTooltip(!showTooltip);
    };

    return (
        <div key={fieldName} className={`ml-4 mb-2 ${(styleSet && styleSet.outerDiv) ? styleSet.outerDiv : ''}`}>
            <label className={`flex text-dark-blue font-semibold text-sm mb-2 ${(styleSet && styleSet.dropdownLabel) ? styleSet.dropdownLabel : ''}`}>
                {t(fieldNameKey)}{containsAsterisk ? <span className="text-crimson-red">*</span> : <span>{isLoginLanguageRTL ?"" :":"}</span>}
                {addInfoIcon && (
                    <img src={infoIcon} alt="" className="ml-2 cursor-pointer" onClick={handleIconClick}></img>
                )}
            </label>
            {showTooltip && (
                <div className={`z-20 p-4 -mt-[4.5%] w-[20%] max-h-[32%] overflow-y-auto absolute ml-28 shadow-lg bg-white border border-gray-300 rounded`}>
                    <p className="text-black text-sm">{t(infoKey)}</p>
                </div>
            )}
            <div className="relative w-full" ref={dropdownRef}>
                <button onClick={openDropdown} className={`flex items-center justify-between w-[282px] h-10 px-2 py-2 border border-[#707070] bg-white rounded-[4px] text-[15px] text-[#343434] leading-tight focus:outline-none 
                    focus:shadow-none overflow-x-auto whitespace-nowrap no-scrollbar ${(styleSet && styleSet.dropdownButton) ? styleSet.dropdownButton : ''}`} type="button">
                    <span>{
                        selectedDropdownEntry ?
                        dropdownDataList.map(dropdownItem => { return (selectedDropdownEntry === dropdownItem.fieldValue ? dropdownItem.fieldCode : '') })
                        : t(placeHolderKey)}
                    </span>
                    <svg className={`w-3 h-2 ml-3 transform ${isDropdownOpen ? 'rotate-180' : 'rotate-0'} text-gray-500 text-sm`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                    </svg>
                </button>
                {isDropdownOpen && (
                    <div className={`absolute z-50 top-10 left-0 w-full ${(styleSet && styleSet.selectionBox) ? styleSet.selectionBox : ''}`}>
                        <div className="z-10 border border-gray-400 scroll-auto bg-white rounded-md shadow-lg w-full dark:bg-gray-700 cursor-pointer">
                            <div className="max-h-40 overflow-y-auto">
                                {dropdownDataList.map((dropdownItem, index) => {
                                    return (
                                        <div key={index} className="min-h-3">
                                            <button
                                                className={`block w-full h-8 px-4 py-1 text-left text-base text-dark-blue
                                                    ${selectedDropdownEntry === dropdownItem.fieldCode ? 'bg-gray-100' : 'hover:bg-gray-100'}`}
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