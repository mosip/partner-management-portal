import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import {
    createRequest, getPolicyManagerUrl,
    handleServiceErrors, resetPageNumber, setPageNumberAndPageSize, isLangRTL
} from '../../utils/AppUtils';
import ErrorMessage from './ErrorMessage';
import LoadingIcon from "./LoadingIcon";
import Pagination from './Pagination.js';
import { HttpService } from '../../services/HttpService.js';
import searchIcon from '../../svg/policy_group_selector_search_icon.svg';
import { getUserProfile } from '../../services/UserProfileService';

function PolicyGroupSelector({ onPolicyGroupSelect, selectedPolicyGroup, required = false }) {
    const { t } = useTranslation();
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(false);
    const [policyGroupList, setPolicyGroupList] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [debouncedSearchTerm, setDebouncedSearchTerm] = useState("");
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(5);
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState(5);
    const [fetchData, setFetchData] = useState(false);
    const [tableDataLoaded, setTableDataLoaded] = useState(true);
    const [totalRecords, setTotalRecords] = useState(0);
    const [resetPageNo, setResetPageNo] = useState(false);
    const [isSearchFilterClicked, setIsSearchFilterClicked] = useState(false);
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

    // Handle policy group selection
    const handlePolicyGroupSelect = (policyGroup) => {
        if (onPolicyGroupSelect) {
            onPolicyGroupSelect(policyGroup);
        }
    };

    // Handle search functionality
    const handleSearch = (event) => {
        setSearchTerm(event.target.value);
    };

    // Debounce search term
    useEffect(() => {
        const timer = setTimeout(() => {
            setDebouncedSearchTerm(searchTerm);
            setPageNo(0);
            setFetchData(true);
            setIsSearchFilterClicked(true);
        }, 500);

        return () => clearTimeout(timer);
    }, [searchTerm]);

    const fetchPolicyGroupListData = async () => {
        //reset page number to 0 if filter applied or page number is out of bounds
        const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, resetPageNo);
        setResetPageNo(false);

        // Create search filter
        let filterRequest = [];
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
            pagination: {
                pageStart: effectivePageNo,
                pageFetch: pageSize
            }
        });

        try {
            fetchData ? setTableDataLoaded(false) : setDataLoaded(false);
            const response = await HttpService({
                url: getPolicyManagerUrl('/policies/group/search', process.env.NODE_ENV),
                method: 'post',
                baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL,
                data: request
            });
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response.data;
                    setTotalRecords(responseData.response.totalRecord);
                    if (resData !== null) setPolicyGroupList(resData);
                    else setPolicyGroupList([]);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('policyGroupList.errorInPolicyGroupList'));
            }
            fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
            setFetchData(false);
        } catch (err) {
            console.error('Error fetching data:', err);
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            setFetchData(false);
            fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
        }
    };

    useEffect(() => {
        fetchPolicyGroupListData();
    }, [pageNo, pageSize, debouncedSearchTerm, t]);

    useEffect(() => {
        if (isSearchFilterClicked && pageNo === 0) {
            setIsSearchFilterClicked(false);
        }
    }, [isSearchFilterClicked]);

    const getPaginationValues = (recordsPerPage, pageIndex) => {
        setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setFetchData);
    }

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const styles = {
        loadingDiv: "py-20"
    }

    return (
        <div className="w-full mx-auto">
            {/* Label */}
            <label className="flex items-center text-dark-blue text-sm mb-2 ml-1">
                <p className="font-semibold">
                    {t('selectPolicyPopup.policyGroup')}
                    {required && <span className="text-crimson-red mx-1">*</span>}
                </p>
            </label>

            <div className="bg-white border border-gray-200 rounded-lg shadow-sm overflow-hidden">
                {!dataLoaded && (
                    <LoadingIcon styleSet={styles} />
                )}
                {dataLoaded && (
                    <>
                        {errorMsg && (
                            <ErrorMessage
                                id='policy_group_selector_error_msg'
                                errorCode={errorCode}
                                errorMessage={errorMsg}
                                clickOnCancel={cancelErrorMsg}
                            />
                        )}

                        {/* Header */}
                        <div className="flex flex-col sm:flex-row sm:justify-between sm:items-center gap-3 p-3 sm:p-2 border-b border-gray-200">
                            <h2 id="policy_group_selector_title" className="text-sm sm:text-sm font-semibold text-gray-800">
                                {t('policyGroupList.listOfPolicyGroups')} ({totalRecords})
                            </h2>

                            {/* Search Bar */}
                            <div className="relative w-full sm:w-auto">
                                <div className={`absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none`}>
                                    <img src={searchIcon} alt="" className="h-4 w-4 text-gray-400" />
                                </div>
                                <input
                                    id="policy_group_selector_search_input"
                                    type="text"
                                    placeholder={t('commons.search')}
                                    value={searchTerm}
                                    onChange={handleSearch}
                                    className={`block w-full sm:w-64 pl-10 pr-3 py-2 border border-gray-300 rounded-md leading-5 bg-white placeholder-gray-500 focus:outline-none focus:placeholder-gray-400 focus:ring-1 focus:ring-blue-500 focus:border-blue-500 text-sm`}
                                />
                            </div>
                        </div>

                        {/* Policy Group List */}
                        {!tableDataLoaded ? (
                            <LoadingIcon styleSet={styles} />
                        ) : (
                            <div className='overflow-y-auto max-h-[12.5rem]'>
                                {policyGroupList.length === 0 ? (
                                    <div id="policy_group_selector_empty_state" className="p-6 sm:p-8 text-center text-gray-500">
                                        <p className="text-sm sm:text-base">{t('policyGroupList.noPolicyGroupsFound')}</p>
                                    </div>
                                ) : (
                                    policyGroupList.map((policyGroup, index) => (
                                        <div key={policyGroup.id} >
                                            <div

                                                id={`policy_group_selector_item_${index + 1}`}
                                                className="p-2 hover:bg-gray-50 cursor-pointer transition-colors duration-150"
                                                onClick={() => handlePolicyGroupSelect(policyGroup)}
                                            >
                                                <div className="flex items-start">
                                                    {/* Radio Button */}
                                                    <div className={`flex items-center justify-center h-12 ${isLoginLanguageRTL ? 'pl-3' : 'pr-3'}`}>
                                                        <input
                                                            id={`policy_group_selector_radio_${index + 1}`}
                                                            type="radio"
                                                            name="policyGroup"
                                                            value={policyGroup.id}
                                                            checked={selectedPolicyGroup?.id === policyGroup.id}
                                                            onChange={() => handlePolicyGroupSelect(policyGroup)}
                                                            className="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300"
                                                        />
                                                    </div>

                                                    {/* Policy Group Info */}
                                                    <div className="flex-1 min-w-0">
                                                        <h3 id={`policy_group_selector_name_${index + 1}`} className="text-sm sm:text-sm font-semibold text-gray-900 leading-tight">
                                                            {policyGroup.name}
                                                        </h3>
                                                        <p id={`policy_group_selector_description_${index + 1}`} className="mt-1 text-xs sm:text-sm text-gray-500 leading-relaxed">
                                                            {policyGroup.desc || t('policyGroupList.noDescription')}
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                            <hr className="mx-3 sm:mx-4" />
                                        </div>
                                    ))
                                )}
                            </div>
                        )}

                        {/* Pagination */}
                        <Pagination
                            dataListLength={totalRecords}
                            selectedRecordsPerPage={selectedRecordsPerPage}
                            setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                            isServerSideFilter={true}
                            getPaginationValues={getPaginationValues}
                            isApplyFilterClicked={isSearchFilterClicked}
                            setIsApplyFilterClicked={setIsSearchFilterClicked}
                        />
                    </>
                )}
            </div>
        </div>
    )

}
export default PolicyGroupSelector;