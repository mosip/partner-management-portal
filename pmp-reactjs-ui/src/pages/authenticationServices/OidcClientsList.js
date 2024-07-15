import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import {
    isLangRTL, handleServiceErrors, getPartnerManagerUrl, formatDate, getStatusCode,
    handleMouseClickForDropdown, toggleSortDescOrder, toggleSortAscOrder, moveToHome, createRequest, bgOfStatus
} from '../../utils/AppUtils';
import { HttpService } from '../../services/HttpService';
import ErrorMessage from '../common/ErrorMessage';
import LoadingIcon from "../common/LoadingIcon";
import backArrow from '../../svg/back_arrow.svg';
import rectangleGrid from '../../svg/rectangle_grid.svg';
import CopyIdPopUp from '../common/CopyIdPopup.js';
import OidcClientsFilter from './OidcClientsFilter';
import AuthenticationServicesTab from './AuthenticationServicesTab.js';
import DeactivatePopup from '../common/DeactivatePopup.js';
import FilterButtons from '../common/FilterButtons.js';
import SortingIcon from '../common/SortingIcon.js';
import Pagination from '../common/Pagination.js';

function OidcClientsList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(false);
    const [activeOidcClient, setActiveOicdClient] = useState(true);
    const [activeApiKey, setActiveApiKey] = useState(false);
    const [filter, setFilter] = useState(false);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
    const [order, setOrder] = useState("ASC");
    const [activeSortAsc, setActiveSortAsc] = useState("crDtimes");
    const [activeSortDesc, setActiveSortDesc] = useState("");
    const [isDescending, setIsDescending] = useState(false);
    const [showPopup, setShowPopup] = useState(false);

    const [firstIndex, setFirstIndex] = useState(0);
    const [oidcClientsList, setOidcClientsList] = useState([]);
    const [filteredOidcClientsList, setFilteredOidcClientsList] = useState([]);
    const [currentClient, setCurrentClient] = useState(null);
    const [viewClientId, setViewClientId] = useState(-1);
    const [showDeactivatePopup, setShowDeactivatePopup] = useState(false);
    const [deactivateRequest, setDeactivateRequest] = useState({});
    const defaultFilterQuery = {
        partnerId: "",
        policyGroupName: ""
    };
    const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
    const submenuRef = useRef(null);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setViewClientId(null));
    }, [submenuRef]);

    // const tableValues = [
    //     { "partnerId": "P28394091", "policyGroup": "Policy Group 01", "policyName": "Full KYC", "oidcClientName": "Client 13", "createdDate": "11/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394092", "policyGroup": "Policy Group 02", "policyName": "KYC", "oidcClientName": "Client 22", "createdDate": "21/10/2025", "status": "Deactivated", "oidcClientId": "0" },
    //     { "partnerId": "P28394093", "policyGroup": "Policy Group 03", "policyName": "KYC1", "oidcClientName": "Client 11", "createdDate": "06/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    //     { "partnerId": "P28394094", "policyGroup": "Policy Group 04", "policyName": "Full KYC", "oidcClientName": "Client 03", "createdDate": "30/09/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394095", "policyGroup": "Policy Group 05", "policyName": "KYC1", "oidcClientName": "Client 05", "createdDate": "12/10/2025", "status": "Rejected", "oidcClientId": "0" },
    //     { "partnerId": "P28394096", "policyGroup": "Policy Group 06", "policyName": "KYC", "oidcClientName": "Client 16", "createdDate": "07/10/2025", "status": "Deactivated", "oidcClientId": "0" },
    //     { "partnerId": "P28394097", "policyGroup": "Policy Group 07", "policyName": "Full KYC", "oidcClientName": "Client 07", "createdDate": "01/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    //     { "partnerId": "P28394098", "policyGroup": "Policy Group 08", "policyName": "KYC", "oidcClientName": "Client 04", "createdDate": "17/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394099", "policyGroup": "Policy Group 09", "policyName": "KYC1", "oidcClientName": "Client 12", "createdDate": "13/10/2025", "status": "Deactivated", "oidcClientId": "0" },
    //     { "partnerId": "P28394100", "policyGroup": "Policy Group 10", "policyName": "KYC", "oidcClientName": "Client 09", "createdDate": "02/10/2025", "status": "Rejected", "oidcClientId": "0" },
    //     { "partnerId": "P28394101", "policyGroup": "Policy Group 11", "policyName": "Full KYC", "oidcClientName": "Client 02", "createdDate": "08/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394102", "policyGroup": "Policy Group 12", "policyName": "KYC", "oidcClientName": "Client 06", "createdDate": "18/10/2025", "status": "Deactivated", "oidcClientId": "0" },
    //     { "partnerId": "P28394103", "policyGroup": "Policy Group 13", "policyName": "KYC", "oidcClientName": "Client 01", "createdDate": "14/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    //     { "partnerId": "P28394104", "policyGroup": "Policy Group 14", "policyName": "Full KYC", "oidcClientName": "Client 10", "createdDate": "03/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394105", "policyGroup": "Policy Group 15", "policyName": "KYC1", "oidcClientName": "Client 08", "createdDate": "09/10/2025", "status": "Rejected", "oidcClientId": "0" },
    //     { "partnerId": "P28394106", "policyGroup": "Policy Group 16", "policyName": "Full KYC", "oidcClientName": "Client 20", "createdDate": "19/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394107", "policyGroup": "Policy Group 17", "policyName": "KYC", "oidcClientName": "Client 17", "createdDate": "04/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394108", "policyGroup": "Policy Group 18", "policyName": "Full KYC", "oidcClientName": "Client 14", "createdDate": "15/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    //     { "partnerId": "P28394109", "policyGroup": "Policy Group 19", "policyName": "KYC", "oidcClientName": "Client 19", "createdDate": "10/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394110", "policyGroup": "Policy Group 20", "policyName": "Full KYC", "oidcClientName": "Client 18", "createdDate": "20/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394111", "policyGroup": "Policy Group 21", "policyName": "KYC1", "oidcClientName": "Client 21", "createdDate": "05/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    //     { "partnerId": "P28394112", "policyGroup": "Policy Group 22", "policyName": "Full KYC", "oidcClientName": "Client 15", "createdDate": "16/10/2025", "status": "Rejected", "oidcClientId": "0" }

    // ];

    useEffect(() => {
        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(getPartnerManagerUrl('/getAllOidcClients', process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        const sortedData = resData.sort((a, b) => new Date(b.crDtimes) - new Date(a.crDtimes));
                        setOidcClientsList(sortedData);
                        setFilteredOidcClientsList(sortedData)
                        // console.log('Response data:', oidcClientsList.length);
                    } else {
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('oidcClientsList.errorInOidcClientsList'));
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
        { id: "oidcClientName", headerNameKey: "oidcClientsList.oidcClientName" },
        { id: "crDtimes", headerNameKey: "oidcClientsList.createdDate" },
        { id: "status", headerNameKey: "oidcClientsList.status" },
        { id: "oidcClientId", headerNameKey: "oidcClientsList.oidcClientId" },
        { id: "action", headerNameKey: 'oidcClientsList.action' }
    ];

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const createOidcClient = () => {
        navigate('/partnermanagement/authenticationServices/createOidcClient')
    }

    const showViewOidcClientDetails = (selectedClientdata) => {
        if (selectedClientdata.status === "ACTIVE") {
            localStorage.setItem('selectedClientData', JSON.stringify(selectedClientdata));
            navigate('/partnermanagement/authenticationServices/viewOidcClienDetails')
        }
    };

    const onClickView = (selectedClientdata) => {
        localStorage.setItem('selectedClientData', JSON.stringify(selectedClientdata));
        navigate('/partnermanagement/authenticationServices/viewOidcClienDetails')
    };

    const showEditOidcClient = (selectedClientdata) => {
        if (selectedClientdata.status === "ACTIVE") {
            localStorage.setItem('selectedClientData', JSON.stringify(selectedClientdata));
            navigate('/partnermanagement/authenticationServices/editOidcClient')
        }
    };

    const showDeactivateOidcClient = (selectedClientdata) => {
        if (selectedClientdata.status === "ACTIVE") {
            const request = createRequest({
                logoUri: selectedClientdata.logoUri,
                redirectUris: selectedClientdata.redirectUris,
                status: "INACTIVE",
                grantTypes: selectedClientdata.grantTypes,
                clientName: selectedClientdata.oidcClientName,
                clientAuthMethods: selectedClientdata.clientAuthMethods,
                clientNameLangMap: {
                    "eng": selectedClientdata.oidcClientName
                }
            });
            setDeactivateRequest(request);
            setShowDeactivatePopup(true);
            document.body.style.overflow="hidden";
        }
    };

    const showCopyPopUp = (client) => {
        if (client.status.toLowerCase() === "active") {
            setCurrentClient(client);
            setShowPopup(true);
            document.body.style.overflow="hidden"
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
        let filteredRows = oidcClientsList;
        Object.keys(filterQuery).forEach(key => {
            //console.log(`${key} : ${filterQuery[key]}`);
            if (filterQuery[key] !== '') {
                filteredRows = filteredRows.filter(item => item[key] === filterQuery[key]);
            }
        });
        setFilteredOidcClientsList(filteredRows);
        setFirstIndex(0);
    }, [filterQuery, oidcClientsList]);

    const onResetFilter = () => {
        window.location.reload();
    }

    const sortAscOrder = (header) => {
        const isDateCol = (header === "crDtimes") ? true : false;
        toggleSortAscOrder(header, isDateCol, filteredOidcClientsList, setFilteredOidcClientsList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
    }

    const sortDescOrder = (header) => {
        const isDateCol = (header === "crDtimes") ? true : false;
        toggleSortDescOrder(header, isDateCol, filteredOidcClientsList, setFilteredOidcClientsList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
    }

    //This part related to Pagination Logic
    let tableRows = filteredOidcClientsList.slice(firstIndex, firstIndex + (selectedRecordsPerPage));

    const styles = {
        outerDiv: "!bg-opacity-[16%]"
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
                            <div className={`flex items-start gap-x-2`}>
                                <img src={backArrow} alt="" onClick={() => moveToHome(navigate)} className={`mt-[7%] cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} />
                                <div className="flex-col mt-[3%]">
                                    <h1 className="font-semibold text-lg text-dark-blue">{t('authenticationServices.authenticationServices')}</h1>
                                    <p onClick={() => moveToHome(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                        {t('commons.home')}
                                    </p>
                                </div>
                            </div>
                            {oidcClientsList.length > 0 ?
                                <button onClick={() => createOidcClient()} type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                                    {t('createOidcClient.createOidcClient')}
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

                        {oidcClientsList.length === 0
                            ?
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                {
                                    activeOidcClient && (
                                        <div className="flex justify-between py-2 pt-4 text-sm font-semibold text-[#6F6E6E]">
                                            <div className={`flex sm:gap-x-3 md:gap-x-8 lg:gap-x-16 xl:gap-x-24`}>
                                                <h6 className={`${isLoginLanguageRTL ? "mr-5" : "ml-5"}`}>{t('authenticationServices.partnerId')}</h6>
                                                <h6>{t('authenticationServices.policyGroup')}</h6>
                                                <h6>{t('authenticationServices.policyName')}</h6>
                                                <h6>{t('authenticationServices.oidcClientName')}</h6>
                                                <h6>{t('authenticationServices.createdDate')}</h6>
                                                <h6>{t('authenticationServices.status')}</h6>
                                                <h6>{t('authenticationServices.oidcClientId')}</h6>
                                                <h6 className={isLoginLanguageRTL ? "ml-5" : "mr-5"}>{t('authenticationServices.action')}</h6>
                                            </div>
                                        </div>)
                                }

                                <hr className="h-px mx-3 bg-gray-200 border-0" />

                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center">
                                        <img src={rectangleGrid} alt="" />
                                        {activeOidcClient &&
                                            (<button onClick={() => createOidcClient()} type="button"
                                                className={`text-white font-semibold mt-8 bg-tory-blue rounded-md text-sm mx-8 py-3`}>
                                                {t('authenticationServices.createOidcClientBtn')}
                                            </button>)
                                        }
                                    </div>
                                </div>
                            </div>
                            :
                            <>
                                <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg">
                                    <FilterButtons listTitle='oidcClientsList.listOfOidcClients' dataList={filteredOidcClientsList} filter={filter} onResetFilter={onResetFilter} setFilter={setFilter}></FilterButtons>
                                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                    {filter &&
                                        <OidcClientsFilter
                                            filteredOidcClientsList={filteredOidcClientsList}
                                            onFilterChange={onFilterChange}>
                                        </OidcClientsFilter>
                                    }
                                    <div className="mx-[2%] overflow-x-scroll">
                                        <table className="table-fixed">
                                            <thead>
                                                <tr>
                                                    {tableHeaders.map((header, index) => {
                                                        return (
                                                            <th key={index} className={`py-4 text-xs text-[#6F6E6E] lg:w-[14%] ${header.id === "policyName" && 'pl-4'} ${header.id === "crDtimes" && 'pl-9'} ${header.id === "status" && 'pl-12'} ${header.id === "oidcClientId" && 'pr-2'}`}>
                                                                <div className="flex gap-x-1 items-center font-semibold">
                                                                    {t(header.headerNameKey)}
                                                                    {(header.id !== "action") && (header.id !== "oidcClientId") && (
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
                                                            <tr key={index} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold ${client.status.toLowerCase() === "inactive" ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                                                <td onClick={() => showViewOidcClientDetails(client)} className="px-2">{client.partnerId}</td>
                                                                <td onClick={() => showViewOidcClientDetails(client)} className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"} break-all break-normal break-word`}>{client.policyGroupName}</td>
                                                                <td onClick={() => showViewOidcClientDetails(client)} className="px-4 break-all break-normal break-words">{client.policyName}</td>
                                                                <td onClick={() => showViewOidcClientDetails(client)} className="px-1 break-all break-normal break-words">{client.oidcClientName}</td>
                                                                <td onClick={() => showViewOidcClientDetails(client)} className={`${isLoginLanguageRTL ? "pr-9" : "pl-9"}`}>{formatDate(client.crDtimes, 'date')}</td>
                                                                <td onClick={() => showViewOidcClientDetails(client)} className="px-12">
                                                                    <div className={`${bgOfStatus(client.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-semibold rounded-md`}>
                                                                        {getStatusCode(client.status, t)}
                                                                    </div>
                                                                </td>
                                                                <td className={`${isLoginLanguageRTL ? "pr-4" : "pl-4"}`}>
                                                                    <svg onClick={() => showCopyPopUp(client)}
                                                                        xmlns="http://www.w3.org/2000/svg" width="22.634" height="15.433" viewBox="0 0 22.634 15.433">
                                                                        <path id="visibility_FILL0_wght400_GRAD0_opsz48"
                                                                            d="M51.32-787.911a4.21,4.21,0,0,0,3.1-1.276,4.225,4.225,0,0,0,1.273-3.1,4.21,4.21,0,0,0-1.276-3.1,4.225,4.225,0,0,0-3.1-1.273,4.21,4.21,0,0,0-3.1,1.276,4.225,4.225,0,0,0-1.273,3.1,4.21,4.21,0,0,0,1.276,3.1A4.225,4.225,0,0,0,51.32-787.911Zm-.009-1.492a2.764,2.764,0,0,1-2.039-.842,2.794,2.794,0,0,1-.836-2.045,2.764,2.764,0,0,1,.842-2.039,2.794,2.794,0,0,1,2.045-.836,2.764,2.764,0,0,1,2.039.842,2.794,2.794,0,0,1,.836,2.045,2.764,2.764,0,0,1-.842,2.039A2.794,2.794,0,0,1,51.311-789.4Zm.006,4.836a11.528,11.528,0,0,1-6.79-2.135A13,13,0,0,1,40-792.284a13.006,13.006,0,0,1,4.527-5.582A11.529,11.529,0,0,1,51.317-800a11.529,11.529,0,0,1,6.79,2.135,13.006,13.006,0,0,1,4.527,5.582,13,13,0,0,1-4.527,5.581A11.528,11.528,0,0,1,51.317-784.568ZM51.317-792.284Zm0,6.173A10.351,10.351,0,0,0,57.04-787.8a10.932,10.932,0,0,0,3.974-4.488,10.943,10.943,0,0,0-3.97-4.488,10.33,10.33,0,0,0-5.723-1.685,10.351,10.351,0,0,0-5.727,1.685,11.116,11.116,0,0,0-4,4.488,11.127,11.127,0,0,0,4,4.488A10.33,10.33,0,0,0,51.313-786.111Z"
                                                                            transform="translate(-40 800)" fill={`${client.status === 'ACTIVE' ? "#1447B2" : "#D1D1D1"}`} />
                                                                    </svg>
                                                                    {showPopup && (
                                                                        <CopyIdPopUp closePopUp={setShowPopup} partnerId={currentClient.partnerId} policyName={currentClient.policyName} id={currentClient.oidcClientId} header='oidcClientsList.oidcClientId' styleSet={styles} />
                                                                    )}
                                                                </td>

                                                                <td className="text-center">
                                                                    <div>
                                                                        <p onClick={() => setViewClientId(index)} className={`${isLoginLanguageRTL ? "ml-9" : "mr-9"} font-semibold mb-0.5 cursor-pointer text-[#1447B2]`}>...</p>
                                                                        {viewClientId === index && (
                                                                            <div ref={submenuRef} className={`absolute w-[7%] bg-white text-xs font-semibold rounded-lg shadow-md border ${isLoginLanguageRTL ? "mr-16 left-32 max-[800px]:left-20 max-[400px]:left-8 text-right" : "right-20 text-left"}`}>
                                                                                <p onClick={() => onClickView(client)} className={`${isLoginLanguageRTL ?"pr-3" :"pl-3"} py-2 cursor-pointer text-[#3E3E3E] hover:bg-gray-100`}>
                                                                                    {t('oidcClientsList.view')}
                                                                                </p>
                                                                                <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                <p onClick={() => showEditOidcClient(client)} className={`${isLoginLanguageRTL ?"pr-3" :"pl-3"} py-2 ${client.status === "ACTIVE" ? 'text-[#3E3E3E] cursor-pointer hover:bg-gray-100' : 'text-[#BEBEBE]'}`}>
                                                                                    {t('oidcClientsList.edit')}
                                                                                </p>
                                                                                <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                <p onClick={() => showDeactivateOidcClient(client)} className={`${isLoginLanguageRTL ?"pr-3" :"pl-3"} break-all break-normal py-2 ${client.status === "ACTIVE" ? 'text-crimson-red cursor-pointer hover:bg-gray-100' : 'text-[#D8ADAD]'}`}>
                                                                                    {t('oidcClientsList.deActivate')}
                                                                                </p>
                                                                                {showDeactivatePopup && (
                                                                                    <DeactivatePopup closePopUp={setShowDeactivatePopup} clientData={client} request={deactivateRequest} headerMsg='deactivateOidcClient.oidcClientName' descriptionMsg='deactivateOidcClient.description' clientName={client.oidcClientName}></DeactivatePopup>
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
                                <Pagination dataList={filteredOidcClientsList} selectedRecordsPerPage={selectedRecordsPerPage} setSelectedRecordsPerPage={setSelectedRecordsPerPage} setFirstIndex={setFirstIndex}></Pagination>
                            </>
                        }
                    </div>
                </>
            )}
        </div>
    )
}

export default OidcClientsList; 