import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import {
    isLangRTL, handleServiceErrors, getPartnerManagerUrl, formatDate, getStatusCode,
    handleMouseClickForDropdown, toggleSortDescOrder, toggleSortAscOrder, createRequest, bgOfStatus,
    onPressEnterKey
} from '../../utils/AppUtils';
import { HttpService } from '../../services/HttpService';
import ErrorMessage from '../common/ErrorMessage';
import LoadingIcon from "../common/LoadingIcon";
import rectangleGrid from '../../svg/rectangle_grid.svg';
import ApiKeysFilter from '../authenticationServices/ApiKeysFilter.js';
import AuthenticationServicesTab from './AuthenticationServicesTab';
import DeactivatePopup from '../common/DeactivatePopup';
import FilterButtons from '../common/FilterButtons.js';
import SortingIcon from '../common/SortingIcon.js';
import Pagination from '../common/Pagination.js';
import Title from '../common/Title.js';

function ApiKeysList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(false);
    const [activeOidcClient, setActiveOicdClient] = useState(false);
    const [activeApiKey, setActiveApiKey] = useState(true);
    const [filter, setFilter] = useState(false);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
    const [order, setOrder] = useState("ASC");
    const [activeSortAsc, setActiveSortAsc] = useState("crDtimes");
    const [activeSortDesc, setActiveSortDesc] = useState("");
    const [isDescending, setIsDescending] = useState(false);
    const [apiKeysList, setApiKeysList] = useState([]);
    const [filteredApiKeysList, setFilteredApiKeysList] = useState([]);
    const [firstIndex, setFirstIndex] = useState(0);
    const [viewApiKeyId, setViewApiKeyId] = useState(-1);
    const [showDeactivatePopup, setShowDeactivatePopup] = useState(false);
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
                const response = await HttpService.get(getPartnerManagerUrl('/partners/getAllApiKeysForAuthPartners', process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        const sortedData = resData.sort((a, b) => new Date(b.crDtimes) - new Date(a.crDtimes));
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
                setErrorMsg(err);
            }
        };
        fetchData();
    }, []);

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'oidcClientsList.partnerId' },
        { id: "policyGroupName", headerNameKey: "oidcClientsList.policyGroup" },
        { id: "policyName", headerNameKey: "oidcClientsList.policyName" },
        { id: "apiKeyLabel", headerNameKey: "apiKeysList.apiKeyLabel" },
        { id: "crDtimes", headerNameKey: "oidcClientsList.createdDate" },
        { id: "status", headerNameKey: "oidcClientsList.status" },
        { id: "action", headerNameKey: 'oidcClientsList.action' }
    ];

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const generateApiKey = () => {
        navigate('/partnermanagement/authenticationServices/generateApiKey')
    };

    const showViewApiKeyDetails = (selectedApiKeyData) => {
        if (selectedApiKeyData.status === "ACTIVE") {
            localStorage.setItem('selectedApiKeyData', JSON.stringify(selectedApiKeyData));
            navigate('/partnermanagement/authenticationServices/viewApiKeyDetails')
        }
    };

    const onClickView = (selectedApiKeyData) => {
        localStorage.setItem('selectedApiKeyData', JSON.stringify(selectedApiKeyData));
        navigate('/partnermanagement/authenticationServices/viewApiKeyDetails')
    };

    const onClickDeactivate = (selectedApiKeyData) => {
        if (selectedApiKeyData.status === "ACTIVE") {
            const request = createRequest({
                label: selectedApiKeyData.apiKeyLabel,
                status: "De-Active"
            });
            setDeactivateRequest(request);
            setShowDeactivatePopup(true);
            document.body.style.overflow = "hidden";
        }
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
        Object.keys(filterQuery).forEach(key => {
            if (filterQuery[key] !== '') {
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
        const isDateCol = (header === "crDtimes") ? true : false;
        toggleSortAscOrder(header, isDateCol, filteredApiKeysList, setFilteredApiKeysList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
    }

    const sortDescOrder = (header) => {
        const isDateCol = (header === "crDtimes") ? true : false;
        toggleSortDescOrder(header, isDateCol, filteredApiKeysList, setFilteredApiKeysList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
    }

    const closeDeactivatePopup = () => {
        setViewApiKeyId(-1);
        setShowDeactivatePopup(false);
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
                        <div className={`flex justify-end max-w-7xl mb-5 mt-2 absolute ${isLoginLanguageRTL ? "left-0" : "right-2"}`}>
                            <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 z-10">
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                            </div>
                        </div>
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between mb-5">
                            <Title title='authenticationServices.authenticationServices' backLink='/partnermanagement' ></Title>
                            {apiKeysList.length > 0 ?
                                <button type="button" onClick={generateApiKey} tabIndex="0" onKeyPress={(e)=>onPressEnterKey(e,generateApiKey)}
                                    className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                                    {t('apiKeysList.generateApiKey')}
                                </button>
                                : null
                            }
                        </div>
                        <AuthenticationServicesTab
                            activeOidcClient={activeOidcClient}
                            setActiveOicdClient={setActiveOicdClient}
                            activeApiKey={activeApiKey}
                            setActiveApiKey={setActiveApiKey}
                        />

                        {apiKeysList.length === 0
                            ?
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                {
                                    activeApiKey && (
                                        <div className="flex justify-between py-2 pt-4 text-sm font-semibold text-[#6F6E6E]">
                                            <div className={`flex sm:gap-x-3 md:gap-x-12 lg:gap-x-20 xl:gap-x-28`}>
                                                <h6 className="px-2 mx-2">{t('authenticationServices.partnerId')}</h6>
                                                <h6 className="px-2 mx-2">{t('authenticationServices.policyGroup')}</h6>
                                                <h6 className="px-2 mx-2">{t('authenticationServices.policyName')}</h6>
                                                <h6 className="px-2 mx-2">{t('apiKeysList.apiKeyLabel')}</h6>
                                                <h6 className="px-2 mx-2">{t('authenticationServices.createdDate')}</h6>
                                                <h6 className="px-2 mx-2">{t('authenticationServices.status')}</h6>
                                                <h6 className="px-2 mx-2">{t('authenticationServices.action')}</h6>
                                            </div>
                                        </div>)
                                }

                                <hr className="h-px mx-3 bg-gray-200 border-0" />

                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center">
                                        <img src={rectangleGrid} alt="" />
                                        {activeApiKey &&
                                            (<button onClick={generateApiKey} type="button" tabIndex="0" onKeyPress={(e)=>onPressEnterKey(e,generateApiKey)}
                                                className={`text-white font-semibold mt-8 bg-tory-blue rounded-md text-sm mx-8 py-3`}>
                                                {t('apiKeysList.generateApiKey')}
                                            </button>)
                                        }
                                    </div>
                                </div>
                            </div>
                            :
                            <>
                                <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg">
                                    <FilterButtons listTitle='apiKeysList.listOfApiKeyRequests' dataList={filteredApiKeysList} filter={filter} onResetFilter={onResetFilter} setFilter={setFilter}></FilterButtons>
                                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                    {filter &&
                                        <ApiKeysFilter
                                            filteredApiKeysList={filteredApiKeysList}
                                            onFilterChange={onFilterChange}>
                                        </ApiKeysFilter>
                                    }
                                    <div className="mx-[2%] overflow-x-scroll">
                                        <table className="table-fixed">
                                            <thead>
                                                <tr>
                                                    {tableHeaders.map((header, index) => {
                                                        return (
                                                            <th key={index} className={`py-4 ${isLoginLanguageRTL ? `${header.id === "status" ?'pr-2':'pr-1'}` :`pl-1.5`} text-xs text-[#6F6E6E] lg:w-[14%]`}>
                                                                <div className={`flex gap-x-1 items-center font-semibold ${header.id === "action" && 'justify-center'}`}>
                                                                    {t(header.headerNameKey)}
                                                                    {(header.id !== "action") && (header.id !== "apiKeyReqID") && (
                                                                        <SortingIcon headerId={header.id} sortDescOrder={sortDescOrder} sortAscOrder={sortAscOrder} order={order} activeSortDesc={activeSortDesc} activeSortAsc={activeSortAsc}></SortingIcon>
                                                                    )}
                                                                </div>
                                                            </th>
                                                        )
                                                    })}
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {
                                                    tableRows.map((apiKey, index) => {
                                                        return (
                                                            <tr key={index} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words ${apiKey.status === "INACTIVE" ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                                                <td onClick={() => showViewApiKeyDetails(apiKey)} className="px-2 mx-2">{apiKey.partnerId}</td>
                                                                <td onClick={() => showViewApiKeyDetails(apiKey)} className="px-2 mx-2">{apiKey.policyGroupName}</td>
                                                                <td onClick={() => showViewApiKeyDetails(apiKey)} className="px-2 mx-2">{apiKey.policyName}</td>
                                                                <td onClick={() => showViewApiKeyDetails(apiKey)} className="px-2 mx-2">{apiKey.apiKeyLabel}</td>
                                                                <td onClick={() => showViewApiKeyDetails(apiKey)} className="px-2 mx-2">{formatDate(apiKey.crDtimes, 'date')}</td>
                                                                <td onClick={() => showViewApiKeyDetails(apiKey)} className="px-2 mx-2">
                                                                    <div className={`${bgOfStatus(apiKey.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-semibold rounded-md`}>
                                                                        {getStatusCode(apiKey.status, t)}
                                                                    </div>
                                                                </td>

                                                                <td className="px-2 mx-2">
                                                                    <div className="flex items-center justify-center relative" ref={el => submenuRef.current[index] = el}>
                                                                        <p onClick={() => setViewApiKeyId(index === viewApiKeyId ? null : index)} className={`font-semibold mb-0.5 cursor-pointer text-[#1447B2]`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e,()=>setViewApiKeyId(index === viewApiKeyId ? null : index))}>
                                                                            ...</p>
                                                                        {viewApiKeyId === index && (
                                                                            <div className={`absolute w-[7%] top-7 z-50 bg-white text-xs text-start font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-10 text-left"}`}>
                                                                                <p onClick={() => onClickView(apiKey)} className={`${isLoginLanguageRTL ? "pl-10" : "pr-10"} py-2 px-4 cursor-pointer text-[#3E3E3E] hover:bg-gray-100`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e,()=>onClickView(apiKey))}>
                                                                                    {t('oidcClientsList.view')}
                                                                                </p>
                                                                                <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                <p onClick={() => onClickDeactivate(apiKey)} className={`${isLoginLanguageRTL ? "pl-10" : "pr-10"} py-2 px-4 ${apiKey.status === "ACTIVE" ? 'text-crimson-red cursor-pointer' : 'text-[#A5A5A5] cursor-auto'} hover:bg-gray-100`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e,()=>onClickDeactivate(apiKey))}>
                                                                                    {t('oidcClientsList.deActivate')}
                                                                                </p>
                                                                                {showDeactivatePopup && (
                                                                                    <DeactivatePopup closePopUp={closeDeactivatePopup} popupData={apiKey} request={deactivateRequest} headerMsg='deactivateApiKey.apiKeyName' descriptionMsg='deactivateApiKey.description' headerKeyName={apiKey.apiKeyLabel}></DeactivatePopup>
                                                                                )}
                                                                            </div>
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
                                <Pagination dataList={filteredApiKeysList} selectedRecordsPerPage={selectedRecordsPerPage} setSelectedRecordsPerPage={setSelectedRecordsPerPage} setFirstIndex={setFirstIndex}></Pagination>
                            </>
                        }
                    </div>
                </>
            )}
        </div>
    )
}

export default ApiKeysList;