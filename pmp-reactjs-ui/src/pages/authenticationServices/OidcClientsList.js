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
import CopyIdPopUp from '../common/CopyIdPopup.js';
import OidcClientsFilter from './OidcClientsFilter';
import AuthenticationServicesTab from './AuthenticationServicesTab.js';
import DeactivatePopup from '../common/DeactivatePopup.js';
import FilterButtons from '../common/FilterButtons.js';
import SortingIcon from '../common/SortingIcon.js';
import Pagination from '../common/Pagination.js';
import Title from '../common/Title.js';

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
    const [activeSortAsc, setActiveSortAsc] = useState("createdDateTime");
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
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setViewClientId(-1));
    }, [submenuRef]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(getPartnerManagerUrl('/oauth/clients', process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        const sortedData = resData.sort((a, b) => new Date(b.createdDateTime) - new Date(a.createdDateTime));
                        setOidcClientsList(sortedData);
                        setFilteredOidcClientsList(sortedData);
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
        { id: "createdDateTime", headerNameKey: "oidcClientsList.createdDate" },
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
                clientName: selectedClientdata.clientName,
                clientAuthMethods: selectedClientdata.clientAuthMethods,
                clientNameLangMap: {
                    "eng": selectedClientdata.clientName
                }
            });
            setDeactivateRequest(request);
            setShowDeactivatePopup(true);
            document.body.style.overflow = "hidden";
        }
    };

    const showCopyPopUp = (client) => {
        if (client.status.toLowerCase() === "active") {
            setCurrentClient(client);
            setShowPopup(true);
            document.body.style.overflow = "hidden"
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
        const isDateCol = (header === "createdDateTime") ? true : false;
        toggleSortAscOrder(header, isDateCol, filteredOidcClientsList, setFilteredOidcClientsList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
    }

    const sortDescOrder = (header) => {
        const isDateCol = (header === "createdDateTime") ? true : false;
        toggleSortDescOrder(header, isDateCol, filteredOidcClientsList, setFilteredOidcClientsList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
    }

    //This part related to Pagination Logic
    let tableRows = filteredOidcClientsList.slice(firstIndex, firstIndex + (selectedRecordsPerPage));

    const closeDeactivatePopup = () => {
        setViewClientId(-1);
        setShowDeactivatePopup(false);
    };

    const styles = {
        outerDiv: "!bg-opacity-[16%]"
    }


    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
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
                                                <h6 className="px-2 mx-2">{t('authenticationServices.partnerId')}</h6>
                                                <h6 className="px-2 mx-2">{t('authenticationServices.policyGroup')}</h6>
                                                <h6 className="px-2 mx-2">{t('authenticationServices.policyName')}</h6>
                                                <h6 className="px-2 mx-2">{t('authenticationServices.oidcClientName')}</h6>
                                                <h6 className="px-2 mx-2">{t('authenticationServices.createdDate')}</h6>
                                                <h6 className="px-2 mx-2">{t('authenticationServices.status')}</h6>
                                                <h6 className="px-2 mx-2 text-center">{t('authenticationServices.oidcClientId')}</h6>
                                                <h6 className="px-2 mx-2 text-center">{t('authenticationServices.action')}</h6>
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
                                                            <th key={index} className={`py-4 px-2 text-xs text-[#6F6E6E] w-[14%] ${header.id === "status" && 'w-[10%]'}`}>
                                                                <div className={`flex items-center gap-x-1 font-semibold  ${header.id === "oidcClientId" && 'justify-center'} ${header.id === "action" && 'justify-center'}`}>
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
                                                    tableRows.map((client, index, currentArray) => {
                                                        return (
                                                            <tr key={index} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words ${client.status.toLowerCase() === "inactive" ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                                                <td onClick={() => showViewOidcClientDetails(client)} className="px-2 mx-2">{client.partnerId}</td>
                                                                <td onClick={() => showViewOidcClientDetails(client)} className="px-2 mx-2">{client.policyGroupName}</td>
                                                                <td onClick={() => showViewOidcClientDetails(client)} className="px-2 mx-2">{client.policyName}</td>
                                                                <td onClick={() => showViewOidcClientDetails(client)} className="px-2 mx-2">{client.clientName}</td>
                                                                <td onClick={() => showViewOidcClientDetails(client)} className="px-2 mx-2">{formatDate(client.createdDateTime, 'date')}</td>
                                                                <td onClick={() => showViewOidcClientDetails(client)} className="px-2 mx-2">
                                                                    <div className={`${bgOfStatus(client.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-semibold rounded-md`}>
                                                                        {getStatusCode(client.status, t)}
                                                                    </div>
                                                                </td>
                                                                <td className="px-2 mx-2">
                                                                    <div className="flex items-center justify-center">
                                                                        <svg onClick={() => showCopyPopUp(client)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showCopyPopUp(client))}
                                                                            xmlns="http://www.w3.org/2000/svg" width="22.634" height="15.433" viewBox="0 0 22.634 15.433">
                                                                            <path id="visibility_FILL0_wght400_GRAD0_opsz48"
                                                                                d="M51.32-787.911a4.21,4.21,0,0,0,3.1-1.276,4.225,4.225,0,0,0,1.273-3.1,4.21,4.21,0,0,0-1.276-3.1,4.225,4.225,0,0,0-3.1-1.273,4.21,4.21,0,0,0-3.1,1.276,4.225,4.225,0,0,0-1.273,3.1,4.21,4.21,0,0,0,1.276,3.1A4.225,4.225,0,0,0,51.32-787.911Zm-.009-1.492a2.764,2.764,0,0,1-2.039-.842,2.794,2.794,0,0,1-.836-2.045,2.764,2.764,0,0,1,.842-2.039,2.794,2.794,0,0,1,2.045-.836,2.764,2.764,0,0,1,2.039.842,2.794,2.794,0,0,1,.836,2.045,2.764,2.764,0,0,1-.842,2.039A2.794,2.794,0,0,1,51.311-789.4Zm.006,4.836a11.528,11.528,0,0,1-6.79-2.135A13,13,0,0,1,40-792.284a13.006,13.006,0,0,1,4.527-5.582A11.529,11.529,0,0,1,51.317-800a11.529,11.529,0,0,1,6.79,2.135,13.006,13.006,0,0,1,4.527,5.582,13,13,0,0,1-4.527,5.581A11.528,11.528,0,0,1,51.317-784.568ZM51.317-792.284Zm0,6.173A10.351,10.351,0,0,0,57.04-787.8a10.932,10.932,0,0,0,3.974-4.488,10.943,10.943,0,0,0-3.97-4.488,10.33,10.33,0,0,0-5.723-1.685,10.351,10.351,0,0,0-5.727,1.685,11.116,11.116,0,0,0-4,4.488,11.127,11.127,0,0,0,4,4.488A10.33,10.33,0,0,0,51.313-786.111Z"
                                                                                transform="translate(-40 800)" fill={`${client.status === 'ACTIVE' ? "#1447B2" : "#D1D1D1"}`} />
                                                                        </svg>
                                                                        {showPopup && (
                                                                            <CopyIdPopUp closePopUp={setShowPopup} partnerId={currentClient.partnerId} policyName={currentClient.policyName} id={currentClient.clientId} header='oidcClientsList.oidcClientId' styleSet={styles} />
                                                                        )}
                                                                    </div>
                                                                </td>
                                                                <td className="px-2 mx-2">
                                                                    <div className="flex items-center justify-center relative" ref={el => submenuRef.current[index] = el}>
                                                                        <p onClick={() => setViewClientId(index === viewClientId ? null : index)} className="font-semibold mb-0.5 cursor-pointer text-[#1447B2]"
                                                                            tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setViewClientId(index === viewClientId ? null : index))}>
                                                                            ...</p>
                                                                        {viewClientId === index && (
                                                                            <div className={`absolute w-[7%] ${currentArray.length - 1 === index ? '-bottom-2' : currentArray.length - 2 === index ? '-bottom-2' : 'top-5'} z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-9 text-right" : "right-9 text-left"}`}>
                                                                                <p onClick={() => onClickView(client)} className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] hover:bg-gray-100 ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => onClickView(client))}>
                                                                                    {t('oidcClientsList.view')}
                                                                                </p>
                                                                                <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                <p onClick={() => showEditOidcClient(client)} className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${client.status === "ACTIVE" ? 'text-[#3E3E3E] cursor-pointer hover:bg-gray-100' : 'text-[#BEBEBE]'}`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showEditOidcClient(client))}>
                                                                                    {t('oidcClientsList.edit')}
                                                                                </p>
                                                                                <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                <p onClick={() => showDeactivateOidcClient(client)} className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${client.status === "ACTIVE" ? 'text-crimson-red cursor-pointer' : 'text-[#A5A5A5] cursor-auto'} hover:bg-gray-100`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showDeactivateOidcClient(client))}>
                                                                                    {t('oidcClientsList.deActivate')}
                                                                                </p>
                                                                                {showDeactivatePopup && (
                                                                                    <DeactivatePopup closePopUp={closeDeactivatePopup} popupData={client} request={deactivateRequest} headerMsg='deactivateOidcClient.oidcClientName' descriptionMsg='deactivateOidcClient.description' headerKeyName={client.oidcClientName}></DeactivatePopup>
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