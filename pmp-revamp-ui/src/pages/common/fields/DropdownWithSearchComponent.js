
import { useState, useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { handleMouseClickForDropdown, isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import Information from './Information';

function DropdownWithSearchComponent({ fieldName, dropdownDataList, onDropDownChangeEvent, fieldNameKey,
    placeHolderKey, searchKey, selectedDropdownValue, styleSet, addInfoIcon, infoKey, disabled, selectPolicyPopup, isPlaceHolderPresent, id }) {

    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const [selectedDropdownEntry, setSelectedDropdownEntry] = useState("");
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const [searchItem, setSearchItem] = useState("");
    const dropdownRef = useRef(null);

    const containsAsterisk = fieldNameKey.includes('*');
    fieldNameKey = containsAsterisk ? fieldNameKey.replace('*', '') : fieldNameKey;

    const filteredPolicyGroupList = dropdownDataList.filter(dropdownItem =>
        dropdownItem.fieldValue.toLowerCase().includes(searchItem.toLowerCase())
    );

    useEffect(() => {
        handleMouseClickForDropdown(dropdownRef, () => setIsDropdownOpen(false));
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
        if (!disabled) {
            setSearchItem("");
            setIsDropdownOpen(!isDropdownOpen);
        };
    };

    const handleKeyDown = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
        }
    };

    return (
        <div key={fieldName} className={`ml-4 mb-2 ${(styleSet && styleSet.outerDiv) ? styleSet.outerDiv : ''}`}>
            <label className={`flex items-center text-dark-blue text-sm mb-2 ${(styleSet && styleSet.dropdownLabel) ? styleSet.dropdownLabel : ''} ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
            <p className={`font-semibold`}>{t(fieldNameKey)}{containsAsterisk && <span className={`text-crimson-red mx-1`}>*</span>}</p>
                {addInfoIcon && (
                    <Information infoKey={infoKey} id={id + "_info"}/>
                )}
            </label>
            <div className="relative w-full" ref={dropdownRef}>
                <button id={id} onClick={openDropdown} disabled={disabled} className={`flex items-center justify-between w-fit h-auto px-2 py-2 border border-[#707070] bg-white rounded-[4px] text-[15px] ${selectedDropdownEntry ? 'text-[#343434]' : 'text-grayish-blue'} leading-tight
                    focus:shadow-none overflow-x-auto whitespace-nowrap no-scrollbar ${(styleSet && styleSet.dropdownButton) ? styleSet.dropdownButton : ''}`} type="button">
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
                    <div className={`${!selectPolicyPopup && 'absolute'}mt-auto left-0 w-full ${(styleSet && styleSet.selectionBox) ? styleSet.selectionBox : ''}`}>
                        <div className="absolute z-10 border border-gray-400 bg-white rounded-md shadow-lg w-full cursor-pointer">
                            <div className="p-2 border-b border-gray-200 shadow-sm relative">
                                <span className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-4 text-black mx-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 18a8 8 0 100-16 8 8 0 000 16zM21 21l-5.2-5.2" />
                                    </svg>
                                </span>
                                <input id={id + "_search_input"} type="text" placeholder={t(searchKey)} value={searchItem} onChange={(e) => setSearchItem(e.target.value)} onKeyDown={handleKeyDown}
                                    className="w-full h-8 pl-8 py-1 text-sm text-gray-300 border border-gray-400 rounded-md focus:outline-none focus:text focus:text-gray-800" />
                            </div>
                            {filteredPolicyGroupList.length === 0 && (
                                <div className="min-h-3 p-4 cursor-auto">
                                    <p className="text-sm text-dark-blue font-semibold">{t('commons.emptyMsg')}</p>
                                </div>
                            )}
                            <div className="max-h-32 overflow-y-auto">
                                {filteredPolicyGroupList.map((dropdownItem, index) => {
                                    return (
                                        <div key={index} className="min-h-2">
                                            <button id={isPlaceHolderPresent ? (index > 0 ? id + '_option' + (index) : undefined) : id + '_option' + (index + 1)}
                                                className={`block ${dropdownItem.fieldDescription ? 'min-h-16' : 'min-h-8'} w-full px-4 py-1 text-sm text-dark-blue overflow-x-auto whitespace-normal no-scrollbar break-words
                                                    ${selectedDropdownEntry === dropdownItem.fieldValue ? 'bg-gray-100' : 'hover:bg-gray-100'} ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}
                                                onClick={() => changeDropdownSelection(dropdownItem.fieldValue)}>
                                                <span className={` ${dropdownItem.fieldDescription ? 'font-semibold' : 'font-normal'} 
                                                    ${isPlaceHolderPresent && index === 0 && searchItem === "" ? 'text-gray-500' : 'text-dark-blue'}
                                                `}>{dropdownItem.fieldCode}</span>
                                                {dropdownItem.fieldDescription && (
                                                    <>
                                                        <br />
                                                        <p className="text-xs text-[#727272]">{dropdownItem.fieldDescription}</p>
                                                    </>
                                                )}
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

export default DropdownWithSearchComponent;