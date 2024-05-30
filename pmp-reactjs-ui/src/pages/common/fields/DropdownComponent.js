
import { useState, useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { handleMouseClickForDropdown } from '../../../utils/AppUtils';

function DropdownComponent({ fieldName, dropdownDataList, onDropDownChangeEvent, fieldNameKey, 
    dropDownPlaceHolder, defaultDropdownValue, outerDivStyle, fieldNameStyle, fieldBtnStyle, dropdownBoxStyle }) {

    const { t } = useTranslation();

    const [selectedDropdownEntry, setSelectedDropdownEntry] = useState("");
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const dropdownRef = useRef(null);

    useEffect(() => {
        const clickOutSideDropdown = handleMouseClickForDropdown(dropdownRef, () => setIsDropdownOpen(false));
        return clickOutSideDropdown;
    }, [dropdownRef]);

    useEffect(() => {
        if(defaultDropdownValue) {
            setSelectedDropdownEntry(defaultDropdownValue);
        }
    }, [defaultDropdownValue])

    const changeDropdownSelection = (selectedid) => {
        setSelectedDropdownEntry(selectedid);
        setIsDropdownOpen(false);
        onDropDownChangeEvent(fieldName, selectedid);
    };
    const openDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };

    return (
        <div key={fieldName} className={`${outerDivStyle}`}>
            <label className={`block text-dark-blue font-semibold ${fieldNameStyle}`}>
                {t(fieldNameKey)}:
            </label>
            <div className="relative w-full" ref={dropdownRef}>
                <button onClick={openDropdown} className={`${fieldBtnStyle} flex items-center justify-between py-2 border border-gray-400 bg-white leading-tight focus:outline-none focus:shadow-none`} type="button">
                    <span>{
                        selectedDropdownEntry ?
                        dropdownDataList.map(dropdownItem => { return (selectedDropdownEntry === dropdownItem.fieldValue ? dropdownItem.fieldCode : '') })
                        : t(dropDownPlaceHolder)}
                    </span>
                    <svg className={`w-3 h-2 ml-3 transform ${isDropdownOpen ? 'rotate-180' : 'rotate-0'} text-gray-500 text-sm`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                    </svg>
                </button>
                {isDropdownOpen && (
                    <div className={`${dropdownBoxStyle} absolute z-50 left-0 w-full`}>
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