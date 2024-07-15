import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import {
    isLangRTL, handleServiceErrors, getPartnerManagerUrl, formatDate, getStatusCode,
    handleMouseClickForDropdown, toggleSortDescOrder, toggleSortAscOrder, createRequest, bgOfStatus
} from '../../utils/AppUtils';
import { HttpService } from '../../services/HttpService';
import ErrorMessage from '../common/ErrorMessage';
import LoadingIcon from "../common/LoadingIcon";
import rectangleGrid from '../../svg/rectangle_grid.svg';
import ApiClientsFilter from './ApiClientsFilter';
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
    const submenuRef = useRef(null);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setViewApiKeyId(null));
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
                        setFilteredApiKeysList(sortedData)
                        // console.log('Response data:', apiKeysList.length);
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

    const showViewApiKeyClientDetails = (selectedApiKeyClientdata) => {
        if (selectedApiKeyClientdata.status === "ACTIVE") {
            localStorage.setItem('selectedApiKeyClientdata', JSON.stringify(selectedApiKeyClientdata));
            navigate('/partnermanagement/authenticationServices/viewApiKeyDetails')
        }
    };

    const onClickView = (selectedApiKeyClientdata) => {
        localStorage.setItem('selectedApiKeyClientdata', JSON.stringify(selectedApiKeyClientdata));
        navigate('/partnermanagement/authenticationServices/viewApiKeyDetails')
    };

    const onClickDeactivate = (selectedApiKeyClientdata) => {
        if (selectedApiKeyClientdata.status === "ACTIVE") {
            const request = createRequest({
                label: selectedApiKeyClientdata.apiKeyLabel,
                status: "De-Active"
            });
            setDeactivateRequest(request);
            setShowDeactivatePopup(true);
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
            //console.log(`${key} : ${filterQuery[key]}`);
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

    //This part related to Pagination Logic
    let tableRows = filteredApiKeysList.slice(firstIndex, firstIndex + (selectedRecordsPerPage));

    const styleForTitle = {
        backArrowIcon: "!mt-[5%]"
    }

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
                            <Title title='authenticationServices.authenticationServices' backLink='/partnermanagement' styleSet={styleForTitle}></Title>
                            {apiKeysList.length > 0 ?
                                <button type="button" onClick={() => generateApiKey()}
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
                                                <h6 className={`${isLoginLanguageRTL ? "mr-5" : "ml-5"}`}>{t('authenticationServices.partnerId')}</h6>
                                                <h6>{t('authenticationServices.policyGroup')}</h6>
                                                <h6>{t('authenticationServices.policyName')}</h6>
                                                <h6>{t('apiKeysList.apiKeyLabel')}</h6>
                                                <h6>{t('authenticationServices.createdDate')}</h6>
                                                <h6>{t('authenticationServices.status')}</h6>
                                                <h6 className={`${isLoginLanguageRTL ? "mr-5" : "ml-5"}`}>{t('authenticationServices.action')}</h6>
                                            </div>
                                        </div>)
                                }

                                <hr className="h-px mx-3 bg-gray-200 border-0" />

                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center">
                                        <img src={rectangleGrid} alt="" />
                                        {activeApiKey &&
                                            (<button onClick={() => generateApiKey()} type="button"
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
                                        <ApiClientsFilter
                                            filteredApiKeysList={filteredApiKeysList}
                                            onFilterChange={onFilterChange}>
                                        </ApiClientsFilter>
                                    }
                                    <div className="mx-[2%] overflow-x-scroll">
                                        <table className="table-fixed">
                                            <thead>
                                                <tr>
                                                    {tableHeaders.map((header, index) => {
                                                        return (
                                                            <th key={index} className={`py-4 text-xs text-[#6F6E6E] lg:w-[14%] ${header.id === "policyName" && 'pl-4'} ${header.id === "crDtimes" && 'pl-9'} ${header.id === "status" && (isLoginLanguageRTL ? "pr-12" : "pl-12")} ${header.id === "action" && (isLoginLanguageRTL ? "pr-12" : "pl-12")} `}>
                                                                <div className="flex gap-x-1 items-center font-semibold">
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
                                                    tableRows.map((client, index) => {
                                                        return (
                                                            <tr key={index} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold ${client.status === "INACTIVE" ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                                                <td onClick={() => showViewApiKeyClientDetails(client)} className="px-2">{client.partnerId}</td>
                                                                <td onClick={() => showViewApiKeyClientDetails(client)} className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`}>{client.policyGroupName}</td>
                                                                <td onClick={() => showViewApiKeyClientDetails(client)} className={`px-4`}>{client.policyName}</td>
                                                                <td onClick={() => showViewApiKeyClientDetails(client)} className="px-2">{client.apiKeyLabel}</td>
                                                                <td onClick={() => showViewApiKeyClientDetails(client)} className={`${isLoginLanguageRTL ? "pr-9" : "pl-9"}`}>{formatDate(client.crDtimes, 'date')}</td>
                                                                <td onClick={() => showViewApiKeyClientDetails(client)} className="px-12">
                                                                    <div className={`${bgOfStatus(client.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-semibold rounded-md`}>
                                                                        {getStatusCode(client.status, t)}
                                                                    </div>
                                                                </td>

                                                                <td className="text-center">
                                                                    <div>
                                                                        <p onClick={() => setViewApiKeyId(index)} className={`${isLoginLanguageRTL ? "ml-9" : "mr-9"} font-semibold mb-0.5 cursor-pointer text-[#1447B2]`}>...</p>
                                                                        {viewApiKeyId === index && (
                                                                            <div ref={submenuRef} className={`absolute w-[7%] bg-white text-xs font-semibold rounded-lg shadow-md border ${isLoginLanguageRTL ? "mr-16 left-32 max-[800px]:left-20 max-[400px]:left-8 text-right" : "right-32 max-[800px]:right-20 max-[400px]:right-8 text-left"}`}>
                                                                                <p onClick={() => onClickView(client)} className={`${isLoginLanguageRTL ? "pr-3":"pl-3"} py-2 cursor-pointer text-[#3E3E3E] hover:bg-gray-100`}>
                                                                                    {t('oidcClientsList.view')}
                                                                                </p>
                                                                                <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                <p onClick={() => onClickDeactivate(client)} className={`${isLoginLanguageRTL ? "pr-3":"pl-3"} py-2 ${client.status === "ACTIVE" ? 'text-crimson-red cursor-pointer hover:bg-gray-100' : 'text-[#D8ADAD]'}`}>
                                                                                    {t('oidcClientsList.deActivate')}
                                                                                </p>
                                                                                {showDeactivatePopup && (
                                                                                    <DeactivatePopup closePopUp={setShowDeactivatePopup} clientData={client} request={deactivateRequest} headerMsg='deactivateApiKey.apiKeyName' descriptionMsg='deactivateApiKey.description' clientName={client.apiKeyLabel}></DeactivatePopup>
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