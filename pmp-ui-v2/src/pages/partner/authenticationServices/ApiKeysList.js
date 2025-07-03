import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import {
    isLangRTL, handleServiceErrors, getPartnerManagerUrl, formatDate, getStatusCode,
    handleMouseClickForDropdown, toggleSortDescOrder, toggleSortAscOrder, createRequest, bgOfStatus,
    onPressEnterKey, setSubmenuRef
} from '../../../utils/AppUtils';
import { HttpService } from '../../../services/HttpService';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from "../../common/LoadingIcon";
import ApiKeysFilter from '../authenticationServices/ApiKeysFilter.js';
import AuthenticationServicesTab from '../../common/AuthenticationServicesTab.js';
import DeactivatePopup from '../../common/DeactivatePopup';
import FilterButtons from '../../common/FilterButtons.js';
import SortingIcon from '../../common/SortingIcon.js';
import Pagination from '../../common/Pagination.js';
import viewIcon from "../../../svg/view_icon.svg";
import disableDeactivateIcon from "../../../svg/disable_deactivate_icon.svg";
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import Title from '../../common/Title.js';
import EmptyList from '../../common/EmptyList.js';

function ApiKeysList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(false);
    const [filter, setFilter] = useState(false);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
    const [order, setOrder] = useState("DESC");
    const [activeSortAsc, setActiveSortAsc] = useState("");
    const [activeSortDesc, setActiveSortDesc] = useState("createdDateTime");
    const [isDescending, setIsDescending] = useState(false);
    const [apiKeysList, setApiKeysList] = useState([]);
    const [selectedApiKey, setSelectedApiKey] = useState({});
    const [filteredApiKeysList, setFilteredApiKeysList] = useState([]);
    const [firstIndex, setFirstIndex] = useState(0);
    const [viewApiKeyId, setViewApiKeyId] = useState(-1);
    const [showActiveIndexDeactivatePopup, setShowActiveIndexDeactivatePopup] = useState(null);
    const [deactivateRequest, setDeactivateRequest] = useState({});
    const defaultFilterQuery = {
        partnerId: "",
        policyGroupName: ""
    };
    const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setViewApiKeyId(-1));
    }, [submenuRef]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(getPartnerManagerUrl('/partner-api-keys/v2', process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response.data;
                        const sortedData = resData.sort((a, b) => new Date(b.createdDateTime) - new Date(a.createdDateTime));
                        setApiKeysList(sortedData);
                        setFilteredApiKeysList(sortedData);
                    } else {
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('apiKeysList.errorInApiKeysList'));
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                if (err.response?.status && err.response.status !== 401) {
                    setErrorMsg(err.toString());
                }
            }
        };
        fetchData();
    }, []);

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'oidcClientsList.partnerId' },
        { id: "policyGroupName", headerNameKey: "oidcClientsList.policyGroup" },
        { id: "policyName", headerNameKey: "oidcClientsList.policyName" },
        { id: "apiKeyLabel", headerNameKey: "apiKeysList.apiKeyName" },
        { id: "createdDateTime", headerNameKey: "oidcClientsList.creationDate" },
        { id: "apiKeyExpiryDateTime", headerNameKey: "apiKeysList.expirationDate" },
        { id: "status", headerNameKey: "oidcClientsList.status" },
        { id: "action", headerNameKey: 'oidcClientsList.action' }
    ];

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const generateApiKey = () => {
        navigate('/partnermanagement/authentication-services/generate-api-key')
    };

    const showViewApiKeyDetails = (selectedApiKeyData) => {
        if (selectedApiKeyData.status === "activated") {
            localStorage.setItem('selectedApiKeyData', JSON.stringify(selectedApiKeyData));
            navigate('/partnermanagement/authentication-services/view-api-key-details')
        }
    };

    const onClickView = (selectedApiKeyData) => {
        localStorage.setItem('selectedApiKeyData', JSON.stringify(selectedApiKeyData));
        navigate('/partnermanagement/authentication-services/view-api-key-details')
    };

    const onClickDeactivate = (selectedApiKeyData, index) => {
        if (selectedApiKeyData.status === "activated") {
            const request = createRequest({
                label: selectedApiKeyData.apiKeyLabel,
                status: "De-Active"
            });
            setViewApiKeyId(-1);
            setSelectedApiKey(selectedApiKeyData);
            setDeactivateRequest(request);
            setShowActiveIndexDeactivatePopup(index);
        }
    };

    const closeDeactivatePopup = () => {
        setSelectedApiKey({});
        setShowActiveIndexDeactivatePopup(null);
    };

    //This part is related to Filter
    const onFilterChange = (fieldName, selectedFilter) => {
        setFilterQuery(oldFilterQuery => ({
            ...oldFilterQuery,
            [fieldName]: selectedFilter
        }));
    }
    useEffect(() => {
        let filteredRows = apiKeysList;
        const allowedKeys = tableHeaders.map(header => header.id);
        Object.keys(filterQuery).forEach(key => {
            if (allowedKeys.includes(key) && filterQuery[key] !== '') {
                filteredRows = filteredRows.filter(item => item[key] === filterQuery[key]);
            }
        });
        setFilteredApiKeysList(filteredRows);
        setFirstIndex(0);
    }, [filterQuery, apiKeysList]);

    const onResetFilter = () => {
        window.location.reload();
    }

    const sortAscOrder = (header) => {
        const isDateCol = (header === "createdDateTime" || header === "apiKeyExpiryDateTime") ? true : false;
        toggleSortAscOrder(header, isDateCol, filteredApiKeysList, setFilteredApiKeysList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
    }

    const sortDescOrder = (header) => {
        const isDateCol = (header === "createdDateTime" || header === "apiKeyExpiryDateTime") ? true : false;
        toggleSortDescOrder(header, isDateCol, filteredApiKeysList, setFilteredApiKeysList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
    }

    const onClickConfirmDeactivate = (deactivationResponse, selectedApiKey) => {
        if (deactivationResponse !== "") {
            setShowActiveIndexDeactivatePopup(null);
            setSelectedApiKey({});
            // Update the specific row in the state with the new status
            setApiKeysList((prevList) =>
                prevList.map(apiKey =>
                    (apiKey.apiKeyLabel === selectedApiKey.apiKeyLabel && apiKey.policyId === selectedApiKey.policyId && apiKey.partnerId === selectedApiKey.partnerId) ? { ...apiKey, status: "deactivated" } : apiKey
                )
            );
            setFilteredApiKeysList((prevList) =>
                prevList.map(apiKey =>
                    (apiKey.apiKeyLabel === selectedApiKey.apiKeyLabel && apiKey.policyId === selectedApiKey.policyId && apiKey.partnerId === selectedApiKey.partnerId) ? { ...apiKey, status: "deactivated" } : apiKey
                )
            );
        }
    };

    //This part related to Pagination Logic
    let tableRows = filteredApiKeysList.slice(firstIndex, firstIndex + (selectedRecordsPerPage));



    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <div className="flex justify-between mb-5">
                            <Title title='authenticationServices.authenticationServices' backLink='/partnermanagement' />
                            {apiKeysList.length > 0 ?
                                <button id='generate_api_key_btn' type="button" onClick={generateApiKey} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, generateApiKey)}
                                    className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                                    {t('apiKeysList.generateApiKey')}
                                </button>
                                : null
                            }
                        </div>
                        <AuthenticationServicesTab
                            activeOidcClient={false}
                            oidcClientPath='/partnermanagement/authentication-services/oidc-clients-list'
                            activeApiKey={true}
                            apiKeyPath='/partnermanagement/authentication-services/api-keys-list'
                        />

                        {apiKeysList.length === 0
                            ?
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <EmptyList
                                    tableHeaders={tableHeaders}
                                    showCustomButton={true}
                                    customButtonName='apiKeysList.generateApiKey'
                                    buttonId='generate_api_key'
                                    onClickButton={generateApiKey}
                                />
                            </div>
                            :
                            <>
                                <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg">
                                    <FilterButtons titleId='list_of_api_key_requests' listTitle='apiKeysList.listOfApiKeyRequests' dataListLength={filteredApiKeysList.length} filter={filter} onResetFilter={onResetFilter} setFilter={setFilter}></FilterButtons>
                                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                    {filter &&
                                        <ApiKeysFilter
                                            filteredApiKeysList={filteredApiKeysList}
                                            onFilterChange={onFilterChange}>
                                        </ApiKeysFilter>
                                    }
                                    <div className="mx-[1.4rem] overflow-x-scroll">
                                        <table className="table-fixed">
                                            <thead>
                                                <tr>
                                                    {tableHeaders.map((header, index) => {
                                                        return (
                                                            <th key={index} className={`py-4 px-2 text-sm font-semibold text-[#6F6E6E] w-[15%] whitespace-nowrap`}>
                                                                <div id={`${header.headerNameKey}_header`} className={`flex gap-x-1 items-center font-semibold ${header.id === "action" && 'justify-center'}`}>
                                                                    {t(header.headerNameKey)}
                                                                    {(header.id !== "action") && (header.id !== "apiKeyReqID") && (
                                                                        <SortingIcon
                                                                            id={`${header.headerNameKey}_sorting_icon`}
                                                                            headerId={header.id}
                                                                            sortDescOrder={sortDescOrder}
                                                                            sortAscOrder={sortAscOrder}
                                                                            order={order}
                                                                            activeSortDesc={activeSortDesc}
                                                                            activeSortAsc={activeSortAsc}
                                                                        />
                                                                    )}
                                                                </div>
                                                            </th>
                                                        )
                                                    })}
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {
                                                    tableRows.map((apiKey, index, currentArray) => {
                                                        return (
                                                            <tr id={'api_list_item' + (index + 1)} key={index} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words ${apiKey.status === "deactivated" ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                                                <td onClick={() => showViewApiKeyDetails(apiKey)} className="px-2 mx-2">{apiKey.partnerId}</td>
                                                                <td onClick={() => showViewApiKeyDetails(apiKey)} className="px-2 mx-2">{apiKey.policyGroupName}</td>
                                                                <td onClick={() => showViewApiKeyDetails(apiKey)} className="px-2 mx-2">{apiKey.policyName}</td>
                                                                <td onClick={() => showViewApiKeyDetails(apiKey)} className="px-2 mx-2">{apiKey.apiKeyLabel}</td>
                                                                <td onClick={() => showViewApiKeyDetails(apiKey)} className="px-2 mx-2">{formatDate(apiKey.createdDateTime, 'date')}</td>
                                                                <td onClick={() => showViewApiKeyDetails(apiKey)} className="px-2 mx-2">{apiKey.apiKeyExpiryDateTime ? formatDate(apiKey.apiKeyExpiryDateTime, 'date') : t('apiKeysList.noExpiry')}</td>
                                                                <td onClick={() => showViewApiKeyDetails(apiKey)} className="px-2 mx-2">
                                                                    <div className={`${bgOfStatus(apiKey.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-semibold rounded-md`}>
                                                                        {getStatusCode(apiKey.status, t)}
                                                                    </div>
                                                                </td>

                                                                <td className="text-center cursor-default">
                                                                    <div ref={setSubmenuRef(submenuRef, index)}>
                                                                        <button id={'api_list_action' + (index + 1)} onClick={() => setViewApiKeyId(index === viewApiKeyId ? null : index)} className={`font-semibold mb-0.5 cursor-pointer text-[#1447B2]`}>
                                                                            <p> ... </p>
                                                                        </button>
                                                                        {viewApiKeyId === index && (
                                                                            <div className={`absolute w-[7rem] z-50 bg-white text-xs text-start font-semibold rounded-lg shadow-md border ${isLoginLanguageRTL ? "left-[3.5rem] text-right" : "right-[3.5rem] text-left"}`}>
                                                                                <div role='button' tabIndex="0" id='api_key_view' onClick={() => onClickView(apiKey)} className={`flex justify-between py-2 w-full px-2 ${isLoginLanguageRTL ? "text-right" : "text-left"} cursor-pointer text-[#3E3E3E] hover:bg-gray-100`} onKeyDown={(e) => onPressEnterKey(e, () => onClickView(apiKey))}>
                                                                                    <p> {t('oidcClientsList.view')} </p>
                                                                                    <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                </div>
                                                                                <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                <div role='button' tabIndex="0" id='api_key_deactivate' onClick={() => onClickDeactivate(apiKey, index)} className={`flex justify-between py-2 w-full px-2 ${isLoginLanguageRTL ? "text-right" : "text-left"} ${apiKey.status === "activated" ? 'text-[#3E3E3E] cursor-pointer' : 'text-[#A5A5A5] cursor-auto'} hover:bg-gray-100`} onKeyDown={(e) => onPressEnterKey(e, () => onClickDeactivate(apiKey, index))}>
                                                                                    <p> {t('oidcClientsList.deActivate')} </p>
                                                                                    <img src={apiKey.status === "activated" ? deactivateIcon : disableDeactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                </div>
                                                                            </div>
                                                                        )}
                                                                        {showActiveIndexDeactivatePopup === index && (
                                                                            <DeactivatePopup
                                                                                closePopUp={closeDeactivatePopup}
                                                                                onClickConfirm={(deactivationResponse) => onClickConfirmDeactivate(deactivationResponse, selectedApiKey)}
                                                                                popupData={selectedApiKey} request={deactivateRequest}
                                                                                headerMsg='deactivateApiKey.apiKeyName'
                                                                                descriptionMsg='deactivateApiKey.description'
                                                                                headerKeyName={selectedApiKey.apiKeyLabel}
                                                                            />
                                                                        )}
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        )
                                                    })
                                                }
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <Pagination dataListLength={filteredApiKeysList.length} selectedRecordsPerPage={selectedRecordsPerPage} setSelectedRecordsPerPage={setSelectedRecordsPerPage} setFirstIndex={setFirstIndex}></Pagination>
                            </>
                        }
                    </div>
                </>
            )}
        </div>
    )
}

export default ApiKeysList;