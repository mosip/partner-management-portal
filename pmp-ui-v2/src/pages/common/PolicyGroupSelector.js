import React, { useState, useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import {
    createRequest, getPolicyManagerUrl,
    handleServiceErrors, resetPageNumber, isLangRTL
} from '../../utils/AppUtils';
import LoadingIcon from "./LoadingIcon";
import { HttpService } from '../../services/HttpService.js';
import searchIcon from '../../svg/policy_group_selector_search_icon.svg';
import { getUserProfile } from '../../services/UserProfileService';

function PolicyGroupSelector({ onPolicyGroupSelect, selectedPolicyGroup, containsAsterisk = false, isPlaceHolderPresent, style }) {
    const { t } = useTranslation();
    const [errorMsg, setErrorMsg] = useState("");
    const [policyGroupList, setPolicyGroupList] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [debouncedSearchTerm, setDebouncedSearchTerm] = useState("");
    const [pageNo, setPageNo] = useState(0);
    const [pageSize] = useState(5); // number of items per fetch
    const [fetchData, setFetchData] = useState(false);
    const [tableDataLoaded, setTableDataLoaded] = useState(true);
    const [totalRecords, setTotalRecords] = useState(0);
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const dropdownButtonRef = useRef(null);
    const dropdownPanelRef = useRef(null);
    const dropdownListRef = useRef(null);
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

    // placeholder
    const placeholderOption = {
        id: '__placeholder__',
        name: t('selectPolicyPopup.title'),
        desc: ''
    };
    const displayedPolicyGroupList = isPlaceHolderPresent
        ? [placeholderOption, ...policyGroupList]
        : policyGroupList;

    const handlePolicyGroupSelect = (policyGroup) => {
        if (onPolicyGroupSelect) onPolicyGroupSelect(policyGroup);
        closeDropdown();
    };

    // Debounce search input
    useEffect(() => {
        const timer = setTimeout(() => {
            setDebouncedSearchTerm(searchTerm);
            setPageNo(0);
            setFetchData(true);
        }, 500);
        return () => clearTimeout(timer);
    }, [searchTerm]);

    // Fetch policy groups 
    const fetchPolicyGroupListData = async () => {
        const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, false);

        let filterRequest = [];
        filterRequest.push({
            value: "true",
            columnName: "isActive",
            type: "equals"
        });
        if (debouncedSearchTerm.trim()) {
            filterRequest.push({
                value: debouncedSearchTerm.trim(),
                columnName: "name",
                type: "contains"
            });
        }

        const request = createRequest({
            filters: filterRequest,
            sort: [{ sortField: "crDtimes", sortType: "desc" }],
            pagination: { pageStart: effectivePageNo, pageFetch: pageSize }
        });

        try {
            setTableDataLoaded(false);
            const response = await HttpService({
                url: getPolicyManagerUrl('/policies/group/search', process.env.NODE_ENV),
                method: 'post',
                baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL,
                data: request
            });

            if (response?.data?.response) {
                const resData = response.data.response.data || [];
                setTotalRecords(response.data.response.totalRecord || 0);

                // Append new items if scrolling, replace if new search
                setPolicyGroupList(effectivePageNo > 0 ? [...policyGroupList, ...resData] : resData);
            } else {
                handleServiceErrors(response.data, null, setErrorMsg);
            }

            setTableDataLoaded(true);
            setFetchData(false);
        } catch (err) {
            console.error(err);
            setErrorMsg(err.toString());
            setTableDataLoaded(true);
            setFetchData(false);
        }
    };

    // Fetch data
    useEffect(() => {
        if (isDropdownOpen) fetchPolicyGroupListData();
    }, [pageNo, debouncedSearchTerm, isDropdownOpen]);

    const toggleDropdown = () => setIsDropdownOpen(prev => {
        if (!prev) setPageNo(0); // reset page on open
        return !prev;
    });

    const closeDropdown = () => {
        setIsDropdownOpen(false);
    };

    // Close dropdown when clicking outside
    useEffect(() => {
        const handleClickOutside = (event) => {
            if (!isDropdownOpen) return;
            if (
                dropdownButtonRef.current?.contains(event.target) ||
                dropdownPanelRef.current?.contains(event.target) ||
                dropdownListRef.current?.contains(event.target)
            ) return;
            closeDropdown();
        };
        document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, [isDropdownOpen]);


    // Infinite scroll
    const onDropdownScroll = (event) => {
        const target = event.currentTarget;
        const threshold = 48;
        const nearBottom = target.scrollTop + target.clientHeight >= target.scrollHeight - threshold;
        if (nearBottom && policyGroupList.length < totalRecords && tableDataLoaded && !fetchData) {
            setPageNo(prev => prev + 1);
            setFetchData(true);
        }
    };

    return (
        <div id="policy_group_selector_container" className="w-full mx-auto">
            <label id="policy_group_selector_label" className="flex items-center text-dark-blue text-sm mb-2 ml-1">
                <p id="policy_group_selector_label_text" className="font-semibold">
                    {t('selectPolicyPopup.policyGroup')}
                    {containsAsterisk && <span id="policy_group_selector_asterisk" className="text-crimson-red mx-1">*</span>}
                </p>
            </label>

            <div id="policy_group_selector_dropdown_container" className="relative w-full">
                <button
                    id="policy_group_selector_dropdown_button"
                    type="button"
                    className="flex items-center justify-between w-full h-10 px-2 border border-[#707070] bg-white rounded-[4px] text-[15px] focus:shadow-none"
                    aria-haspopup="listbox"
                    aria-expanded={isDropdownOpen}
                    aria-controls="policy_group_selector_dropdown_listbox"
                    onClick={toggleDropdown}
                    ref={dropdownButtonRef}
                >
                    <span id="policy_group_selector_dropdown_button_text" className={`truncate max-w-full ${isLoginLanguageRTL ? 'text-right' : 'text-left'} ${selectedPolicyGroup?.name ? 'text-[#343434]' : 'text-grayish-blue'}`}>
                        {selectedPolicyGroup?.name || t('selectPolicyPopup.title')}
                    </span>
                    <svg id="policy_group_selector_dropdown_arrow" className={`w-3 h-2 ${isLoginLanguageRTL ? 'mr-3' :'ml-3'} transform ${isDropdownOpen ? 'rotate-180' : 'rotate-0'} text-gray-500 text-sm`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                    </svg>
                </button>

                {isDropdownOpen && (
                    <div id="policy_group_selector_dropdown_panel" ref={dropdownPanelRef} className={`absolute z-10 ${isLoginLanguageRTL ? 'right-0' : 'left-0'} mt-2 w-full bg-white border border-gray-400 rounded-md shadow-lg cursor-pointer`}>
                        <div id="policy_group_selector_search_container" className="p-2 border-b border-gray-200">
                            <div id="policy_group_selector_search_input_container" className="relative">
                                <div id="policy_group_selector_search_icon_container" className={`absolute inset-y-0 ${isLoginLanguageRTL ? 'right-0 pr-3' : 'left-0 pl-3'} flex items-center pointer-events-none`}>
                                    <img id="policy_group_selector_search_icon" src={searchIcon} alt="" className="h-4 w-4 text-gray-400" />
                                </div>
                                <input
                                    id="policy_group_selector_search_input"
                                    type="text"
                                    placeholder={t('commons.search')}
                                    value={searchTerm}
                                    onChange={(e) => { setSearchTerm(e.target.value); }}
                                    className={`block w-full ${isLoginLanguageRTL ? 'pr-10 pl-3 text-right' : 'pl-10 pr-3'} py-2 border border-gray-400 rounded-md leading-5 bg-white placeholder-gray-500 text-sm focus:outline-none focus:border-gray-400 focus:ring-0`}
                                />
                            </div>
                        </div>

                        {!tableDataLoaded && policyGroupList.length === 0 ? (
                            <div className="py-6"><LoadingIcon /></div>
                        ) : (
                            <ul
                                id="policy_group_selector_dropdown_listbox"
                                role="listbox"
                                aria-label={t('policyGroupList.listOfPolicyGroups')}
                                className={`${style?.listboxheight || 'max-h-44'} overflow-auto focus:outline-none`}
                                onScroll={onDropdownScroll}
                                ref={dropdownListRef}
                            >
                                {displayedPolicyGroupList.length === 0 ? (
                                    <li className="px-3 py-3 text-sm text-gray-500">
                                        {t('policyGroupList.noPolicyGroupsFound')}
                                    </li>
                                ) : displayedPolicyGroupList.map((policyGroup, index) => {
                                    const isPlaceholder = policyGroup.id === '__placeholder__';
                                    const isSelected = !isPlaceholder && selectedPolicyGroup?.id === policyGroup.id;
                                    return (
                                        <li
                                            id={isPlaceHolderPresent ? (index > 0 ? `policy_group_selector_option_${index}` : undefined) : `policy_group_selector_option_${index + 1}`}
                                            key={isPlaceholder ? 'placeholder' : policyGroup.id}
                                            role="option"
                                            aria-selected={isSelected}
                                            className="px-0 py-0"
                                        >
                                            <button
                                                id={`policy_group_selector_option_button_${index + 1}`}
                                                type="button"
                                                className={`block w-full ${policyGroup.desc ? 'min-h-16' : 'min-h-8'} px-4 py-2 text-sm text-dark-blue ${isLoginLanguageRTL ? 'text-right' : 'text-left'} ${isSelected ? 'bg-gray-100' : 'bg-white hover:bg-gray-50'}`}
                                                onClick={() => handlePolicyGroupSelect(isPlaceholder ? null : policyGroup)}
                                            >
                                                <span id={`policy_group_selector_option_name_${index + 1}`} className={`${policyGroup.desc ? 'font-semibold' : 'font-normal'} ${isPlaceholder ? 'text-gray-500' : 'text-dark-blue'}`}>
                                                    {policyGroup.name}
                                                </span>
                                                {!isPlaceholder && policyGroup.desc && (
                                                    <>
                                                        <br />
                                                        <span id={`policy_group_selector_option_desc_${index + 1}`} className="text-xs text-[#727272]">{policyGroup.desc || t('policyGroupList.noDescription')}</span>
                                                    </>
                                                )}
                                                {!isPlaceholder && !policyGroup.desc && (
                                                    <></>
                                                )}
                                            </button>
                                            <div id={`policy_group_selector_option_separator_${index + 1}`} className="border-gray-200 border-t mx-2"></div>
                                        </li>
                                    );
                                })}
                                {fetchData && policyGroupList.length > 0 && (
                                    <li id="policy_group_selector_loading_more" className="px-3 py-2"><LoadingIcon inline={true} /></li>
                                )}
                            </ul>
                        )}
                    </div>
                )}
            </div>
        </div>
    );
}

export default PolicyGroupSelector;
