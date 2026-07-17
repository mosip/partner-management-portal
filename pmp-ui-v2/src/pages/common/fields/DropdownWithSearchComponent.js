import { useState, useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { handleMouseClickForDropdown, isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import Information from './Information';
import LoadingIcon from '../LoadingIcon';
import PropTypes from 'prop-types';

function DropdownWithSearchComponent({ fieldName, dropdownDataList, onDropDownChangeEvent, fieldNameKey,
    placeHolderKey, searchKey, selectedDropdownValue, styleSet, addInfoIcon, infoKey, disabled, selectPolicyPopup, isPlaceHolderPresent, id, onDropdownClick, loading }) {

    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

    const [selectedDropdownEntry, setSelectedDropdownEntry] = useState("");
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const [searchItem, setSearchItem] = useState("");
    const dropdownRef = useRef(null);

    const containsAsterisk = fieldNameKey.includes('*');
    fieldNameKey = containsAsterisk ? fieldNameKey.replaceAll('*', '') : fieldNameKey;

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
        if (!disabled && !loading) {
            setSearchItem("");
            setIsDropdownOpen(!isDropdownOpen);

            // Call onDropdownClick callback if provided and dropdown is opening
            if (onDropdownClick && !isDropdownOpen) {
                onDropdownClick();
            }
        };
    };

const handleKeyDown = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
        }
    };

    return (
        <div key={fieldName} className={`ml-4 mb-2 ${(styleSet && styleSet.outerDiv) ? styleSet.outerDiv : ''}`}>
            <label id={id + '_label'} className={`flex items-center text-dark-blue text-sm mb-2 ${(styleSet && styleSet.dropdownLabel) ? styleSet.dropdownLabel : ''} ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                <p className={`font-semibold`}>{t(fieldNameKey)}{containsAsterisk && <span className={`text-crimson-red mx-1`}>*</span>}</p>
                {addInfoIcon && (
                    <Information infoKey={infoKey} id={id + "_info"} />
                )}
            </label>
            <div
                className={`relative w-full ${isDropdownOpen ? "z-50" : "z-20"}`}
                ref={dropdownRef}
            >
                <button id={id + '_dropdown_btn'} onClick={openDropdown} disabled={disabled} tabIndex={disabled ? -1 : 0} className={`flex items-center justify-between w-full h-auto px-2 py-2 border border-[#707070] bg-white rounded-[4px] text-[15px] ${selectedDropdownEntry ? 'text-[#343434]' : 'text-grayish-blue'} leading-tight
                    outline-none focus:outline-none focus-visible:ring-2 focus-visible:ring-[#1447B2]/35 focus-visible:ring-offset-0 focus-visible:border-[#1447B2]
                    overflow-x-auto whitespace-nowrap no-scrollbar ${(styleSet && styleSet.dropdownButton) ? styleSet.dropdownButton : ''}`} type="button">
                    <span className='w-full break-all text-wrap text-start'>{
                        selectedDropdownEntry ?
                            dropdownDataList.map(dropdownItem => { return (selectedDropdownEntry === dropdownItem.fieldValue ? dropdownItem.fieldCode : '') })
                            : t(placeHolderKey)}
                    </span>
                    <svg className={`w-3 h-2 ${isLoginLanguageRTL ? "mr-3" : "ml-3"} transform ${isDropdownOpen ? 'rotate-180' : 'rotate-0'} text-gray-500 text-sm`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                    </svg>
                </button>
                {isDropdownOpen && (
                    loading ? (
                        <div className={`${!selectPolicyPopup && 'absolute'} mt-auto left-0 w-full ${(styleSet && styleSet.selectionBox) ? styleSet.selectionBox : ''}`}>
                            <div className="absolute z-50 border border-gray-400 bg-white rounded-md shadow-lg w-full">
                                <div className="flex items-center justify-center p-8">
                                    <LoadingIcon />
                                </div>
                            </div>
                        </div>
                    ) : (
                        <div className={`${!selectPolicyPopup && 'absolute'} mt-auto left-0 w-full ${(styleSet && styleSet.selectionBox) ? styleSet.selectionBox : ''}`}>
                            <div className="absolute z-50 border border-gray-400 bg-white rounded-md shadow-lg w-full cursor-pointer">
                                <div className="p-2 border-b border-gray-200 shadow-sm relative">
                                    <span className={`absolute inset-y-0 ${isLoginLanguageRTL ? "right-0 pr-3" : "left-0 pl-3"} flex items-center pointer-events-none`}>
                                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-4 text-black mx-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 18a8 8 0 100-16 8 8 0 000 16zM21 21l-5.2-5.2" />
                                        </svg>
                                    </span>
                                    <input id={id + "_search_input"} type="text" placeholder={t(searchKey)} data-placeholder-id={`${id}_search_placeholder`} value={searchItem} onChange={(e) => setSearchItem(e.target.value)}
                                        className={`w-full h-8 ${isLoginLanguageRTL ? "pr-8 pl-2 text-right" : "pl-8 pr-2 text-left"} py-1 text-sm text-dark-blue border border-gray-400 rounded-md focus:outline-none`} />
                                </div>
                                {filteredPolicyGroupList.length === 0 && (
                                    <div className="min-h-3 p-4 cursor-auto">
                                        <p id={id + '_no_data_available'} className="text-sm text-dark-blue font-semibold">{t('commons.emptyMsg')}</p>
                                    </div>
                                )}
                                <div className="max-h-32 overflow-y-auto">
                                    {filteredPolicyGroupList.map((dropdownItem, index) => {
                                        return (
                                            <div key={index} className="min-h-2">
                                                <button type="button" tabIndex={0} id={isPlaceHolderPresent ? (index > 0 ? id + '_option' + (index) : undefined) : id + '_option' + (index + 1)}
                                                    className={`block ${dropdownItem.fieldDescription ? 'min-h-16' : 'min-h-8'} w-full px-4 py-1 text-sm text-dark-blue overflow-x-auto whitespace-normal no-scrollbar break-words
                                                        ${selectedDropdownEntry === dropdownItem.fieldValue ? 'bg-gray-100' : 'hover:bg-gray-100 focus:bg-gray-100 focus:outline-none'} ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}
                                                    onClick={() => changeDropdownSelection(dropdownItem.fieldValue)}>
                                                    <span className={` ${dropdownItem.fieldDescription ? 'font-semibold' : 'font-normal'} 
                                                        ${isPlaceHolderPresent && index === 0 && searchItem === "" ? 'text-gray-500' : 'text-dark-blue'}
                                                    `}>{dropdownItem.fieldCode}</span>
                                                    {dropdownItem.fieldDescription && (
                                                        <>
                                                            <br />
                                                            <p id={id + '_description_' + (index + 1)} className="text-xs text-[#727272]">{dropdownItem.fieldDescription}</p>
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
                    )
                )}
            </div>
        </div>
    );
}

DropdownWithSearchComponent.propTypes = {
    fieldName: PropTypes.string.isRequired,
    dropdownDataList: PropTypes.array.isRequired,
    onDropDownChangeEvent: PropTypes.func.isRequired,
    fieldNameKey: PropTypes.string.isRequired,
    placeHolderKey: PropTypes.string.isRequired,
    searchKey: PropTypes.string.isRequired,
    selectedDropdownValue: PropTypes.string,
    styleSet: PropTypes.object.isRequired,
    addInfoIcon: PropTypes.bool,
    infoKey: PropTypes.string,
    disabled: PropTypes.bool,
    isPlaceHolderPresent: PropTypes.bool,
    selectPolicyPopup: PropTypes.bool,
    id: PropTypes.string.isRequired,
    onDropdownClick: PropTypes.func,
    loading: PropTypes.bool,
};

export default DropdownWithSearchComponent;