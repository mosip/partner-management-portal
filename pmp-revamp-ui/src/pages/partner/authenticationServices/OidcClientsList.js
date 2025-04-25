import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import {
    isLangRTL, handleServiceErrors, getPartnerManagerUrl, formatDate, getStatusCode,
    handleMouseClickForDropdown, toggleSortDescOrder, toggleSortAscOrder, createRequest, bgOfStatus,
    onPressEnterKey,
    populateClientNames,
    getClientNameLangMap,
    setSubmenuRef,
    isOidcClientAvailable
} from '../../../utils/AppUtils';
import { HttpService } from '../../../services/HttpService';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from "../../common/LoadingIcon";
import CopyIdPopUp from '../../common/CopyIdPopup.js';
import OidcClientsFilter from './OidcClientsFilter';
import AuthenticationServicesTab from '../../common/AuthenticationServicesTab.js';
import DeactivatePopup from '../../common/DeactivatePopup.js';
import FilterButtons from '../../common/FilterButtons.js';
import SortingIcon from '../../common/SortingIcon.js';
import Pagination from '../../common/Pagination.js';
import Title from '../../common/Title.js';
import EmptyList from '../../common/EmptyList.js'; 
import viewIcon from "../../../svg/view_icon.svg";
import disableDeactivateIcon from "../../../svg/disable_deactivate_icon.svg";
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import editIcon from "../../../svg/edit_policy_icon.svg";
import disableEditPolicyIcon from "../../../svg/disable_edit_policy_icon.svg";


function OidcClientsList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(false);
    const [tableDataLoaded, setTableDataLoaded] = useState(true);
    const [filter, setFilter] = useState(false);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
    const [order, setOrder] = useState("DESC");
    const [activeSortAsc, setActiveSortAsc] = useState("");
    const [activeSortDesc, setActiveSortDesc] = useState("createdDateTime");
    const [isDescending, setIsDescending] = useState(false);
    const [showActiveIndexCopyIdPopup, setShowActiveIndexCopyIdPopup] = useState(null);
    const [selectedOidcClient, setSelectedOidcClient] = useState({});
    const [firstIndex, setFirstIndex] = useState(0);
    const [oidcClientsList, setOidcClientsList] = useState([]);
    const [filteredOidcClientsList, setFilteredOidcClientsList] = useState([]);
    const [currentClient, setCurrentClient] = useState(null);
    const [viewClientId, setViewClientId] = useState(-1);
    const [showActiveIndexDeactivatePopup, setShowActiveIndexDeactivatePopup] = useState(null);
    const [deactivateRequest, setDeactivateRequest] = useState({});
    const defaultFilterQuery = {
        partnerId: "",
        policyGroupName: ""
    };
    const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
    const submenuRef = useRef([]);
    const [showCompatibilityMsg, setShowCompatibilityMsg] = useState(false);
    const [disableCreateOidcBtn, setDisableCreateOidcBtn] = useState(false);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setViewClientId(-1));
    }, [submenuRef]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(getPartnerManagerUrl('/oauth/client', process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response.data;
                        const populatedData = populateClientNames(resData);
                        const sortedData = populatedData.sort((a, b) => new Date(b.createdDateTime) - new Date(a.createdDateTime));
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
                if (err.response?.status && err.response.status !== 401) {
                    setErrorMsg(err.toString());
                }
            }
        };
        const checkCompatibleAndFetch = async () => {
            const isApiExist = await isOidcClientAvailable();
            if (isApiExist) {
                fetchData();
            } else {
                setDataLoaded(true);
                setDisableCreateOidcBtn(true);
                setShowCompatibilityMsg(true);
            }
        };
        
        checkCompatibleAndFetch();
    }, []);

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'oidcClientsList.partnerId' },
        { id: "policyGroupName", headerNameKey: "oidcClientsList.policyGroup" },
        { id: "policyName", headerNameKey: "oidcClientsList.policyName" },
        { id: "clientNameEng", headerNameKey: "oidcClientsList.oidcClientName" },
        { id: "createdDateTime", headerNameKey: "oidcClientsList.creationDate" },
        { id: "status", headerNameKey: "oidcClientsList.status" },
        { id: "oidcClientId", headerNameKey: "oidcClientsList.oidcClientId" },
        { id: "action", headerNameKey: 'oidcClientsList.action' }
    ];

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const createOidcClient = () => {
        navigate('/partnermanagement/authentication-services/create-oidc-client')
    }

    const showViewOidcClientDetails = (selectedClientdata) => {
        if (selectedClientdata.status === "ACTIVE") {
            localStorage.setItem('selectedClientData', JSON.stringify(selectedClientdata));
            navigate('/partnermanagement/authentication-services/view-oidc-client-details')
        }
    };

    const onClickView = (selectedClientdata) => {
        localStorage.setItem('selectedClientData', JSON.stringify(selectedClientdata));
        navigate('/partnermanagement/authentication-services/view-oidc-client-details')
    };

    const showEditOidcClient = (selectedClientdata) => {
        if (selectedClientdata.status === "ACTIVE") {
            localStorage.setItem('selectedClientData', JSON.stringify(selectedClientdata));
            navigate('/partnermanagement/authentication-services/edit-oidc-client')
        }
    };

    const showDeactivateOidcClient = async (selectedClientdata, index) => {
        if (selectedClientdata.status === "ACTIVE") {
            setTableDataLoaded(false);
            try {
                const response = await HttpService.get(getPartnerManagerUrl(`/oauth/client/${selectedClientdata.clientId}`, process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const clientData = responseData.response;
                        const request = createRequest({
                            logoUri: clientData.logoUri,
                            redirectUris: clientData.redirectUris,
                            status: "INACTIVE",
                            grantTypes: clientData.grantTypes,
                            clientName: selectedClientdata.clientNameEng,
                            clientAuthMethods: clientData.clientAuthMethods,
                            clientNameLangMap: getClientNameLangMap(selectedClientdata.clientNameEng, selectedClientdata.clientNameJson)
                        });
                        setDeactivateRequest(request);
                        setViewClientId(-1);
                        setSelectedOidcClient(selectedClientdata);
                        setShowActiveIndexDeactivatePopup(index);
                    } else {
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('oidcClientsList.errorInOidcClientsList'))
                }
            } catch (err) {
                console.error('Error fetching data:', err);
                if (err.response?.status && err.response.status !== 401) {
                    setErrorMsg(err.toString());
                }
            }
            setTableDataLoaded(true);
        }
    };

    const closeDeactivatePopup = () => {
        setSelectedOidcClient({});
        setShowActiveIndexDeactivatePopup(null);
    };

    const showCopyPopUp = (client, index) => {
        if (client.status.toLowerCase() === "active") {
            setCurrentClient(client);
            setShowActiveIndexCopyIdPopup(index);
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


    const onClickConfirmDeactivate = (deactivationResponse, selectedClient) => {
        if (deactivationResponse && deactivationResponse.status === "INACTIVE") {
            setShowActiveIndexDeactivatePopup(null);
            setSelectedOidcClient({});
            // Update the specific row in the state with the new status
            setFilteredOidcClientsList((prevList) =>
                prevList.map(client =>
                    client.clientId === selectedClient.clientId ? { ...client, status: "INACTIVE" } : client
                )
            );
        }
    };

    const styles = {
        outerDiv: "!bg-opacity-35"
    }

    const LoadingIconStyle = {
        loadingDiv: "!bg-opacity-[16%] !h-96"
    }


    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
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
                            {oidcClientsList.length > 0 ?
                                <button disabled={disableCreateOidcBtn} id='create_oidc_btn' onClick={() => createOidcClient()} type="button" className={`h-10 text-sm font-semibold text-white px-7 rounded-md ${disableCreateOidcBtn ? 'border-[#A5A5A5] bg-[#A5A5A5]' : 'bg-tory-blue'}`}>
                                    {t('createOidcClient.createOidcClient')}
                                </button>
                                : null
                            }
                        </div>
                        {showCompatibilityMsg && (
                            <div className="bg-[#FCFCFC] w-full my-3 rounded-lg shadow-lg items-center">
                                <div className="flex items-center justify-center p-2">
                                    <div className="p-2 bg-[#FFF7E5] border-2 border-[#EDDCAF] rounded-md w-full">
                                        <p className="text-sm font-medium text-[#8B6105]">{t('oidcClientsList.compatibilityMsg')}</p>
                                    </div>
                                </div>
                            </div>
                        )}
                        <AuthenticationServicesTab
                            activeOidcClient={true}
                            oidcClientPath='/partnermanagement/authentication-services/oidc-clients-list'
                            activeApiKey={false}
                            apiKeyPath='/partnermanagement/authentication-services/api-keys-list'
                        />

                        {oidcClientsList.length === 0
                            ?
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <EmptyList
                                    tableHeaders={tableHeaders}
                                    showCustomButton={true}
                                    customButtonName='createOidcClient.createOidcClient'
                                    buttonId='create_oid_client'
                                    onClickButton={createOidcClient}
                                    disableBtn={disableCreateOidcBtn}
                                />
                            </div>
                            :
                            <>
                                <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg">
                                    <FilterButtons titleId='list_of_oidc_clients' listTitle='oidcClientsList.listOfOidcClients' dataListLength={filteredOidcClientsList.length} filter={filter} onResetFilter={onResetFilter} setFilter={setFilter}></FilterButtons>
                                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                    {filter &&
                                        <OidcClientsFilter
                                            filteredOidcClientsList={filteredOidcClientsList}
                                            onFilterChange={onFilterChange}>
                                        </OidcClientsFilter>
                                    }
                                    {!tableDataLoaded && <LoadingIcon styleSet={LoadingIconStyle}></LoadingIcon>}
                                    {tableDataLoaded &&
                                        <div className="mx-[1.5rem] overflow-x-scroll">
                                            <table className="table-fixed">
                                                <thead>
                                                    <tr>
                                                        {tableHeaders.map((header, index) => {
                                                            return (
                                                                <th key={index} className={`py-4 text-xs text-[#6F6E6E] w-[14%] ${header.id === "status" && 'w-[10%]'} ${(header.id === 'policyName' || header.id === 'policyGroupName') ? (isLoginLanguageRTL ? 'pr-0.5' : 'pl-0.5') : 'px-1.5'}`}>
                                                                    <div id={`${header.headerNameKey}_header`} className={`flex items-center gap-x-1 font-semibold  ${header.id === "oidcClientId" && 'justify-center'} ${header.id === "action" && 'justify-center'}`}>
                                                                        {t(header.headerNameKey)}
                                                                        {(header.id !== "action") && (header.id !== "oidcClientId") && (
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
                                                        tableRows.map((client, index, currentArray) => {
                                                            return (
                                                                <tr id={'oidc_client_list_item' + (index + 1)} key={index} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words ${client.status.toLowerCase() === "inactive" ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                                                    <td onClick={() => showViewOidcClientDetails(client)} className="px-2 mx-2">{client.partnerId}</td>
                                                                    <td onClick={() => showViewOidcClientDetails(client)}>{client.policyGroupName}</td>
                                                                    <td onClick={() => showViewOidcClientDetails(client)} className={`${isLoginLanguageRTL ? 'pr-1' : 'pl-1'}`}>{client.policyName}</td>
                                                                    <td onClick={() => showViewOidcClientDetails(client)} className="px-2 mx-2">{client.clientNameEng}</td>
                                                                    <td onClick={() => showViewOidcClientDetails(client)} className="px-2 mx-2">{formatDate(client.createdDateTime, 'date')}</td>
                                                                    <td onClick={() => showViewOidcClientDetails(client)} className="px-2 mx-2">
                                                                        <div className={`${bgOfStatus(client.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-semibold rounded-md`}>
                                                                            {getStatusCode(client.status, t)}
                                                                        </div>
                                                                    </td>
                                                                    <td className="px-2 mx-2 cursor-default">
                                                                        <div className="flex items-center justify-center">
                                                                            <svg className={`${client.status !== 'INACTIVE' ? 'cursor-pointer' : 'cursor-default'}`} id={'oidc_show_copy_popup_btn' + (index + 1)} onClick={() => showCopyPopUp(client, index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => showCopyPopUp(client, index))}
                                                                                xmlns="http://www.w3.org/2000/svg" width="22.634" height="15.433" viewBox="0 0 22.634 15.433">
                                                                                <path id="visibility_FILL0_wght400_GRAD0_opsz48"
                                                                                    d="M51.32-787.911a4.21,4.21,0,0,0,3.1-1.276,4.225,4.225,0,0,0,1.273-3.1,4.21,4.21,0,0,0-1.276-3.1,4.225,4.225,0,0,0-3.1-1.273,4.21,4.21,0,0,0-3.1,1.276,4.225,4.225,0,0,0-1.273,3.1,4.21,4.21,0,0,0,1.276,3.1A4.225,4.225,0,0,0,51.32-787.911Zm-.009-1.492a2.764,2.764,0,0,1-2.039-.842,2.794,2.794,0,0,1-.836-2.045,2.764,2.764,0,0,1,.842-2.039,2.794,2.794,0,0,1,2.045-.836,2.764,2.764,0,0,1,2.039.842,2.794,2.794,0,0,1,.836,2.045,2.764,2.764,0,0,1-.842,2.039A2.794,2.794,0,0,1,51.311-789.4Zm.006,4.836a11.528,11.528,0,0,1-6.79-2.135A13,13,0,0,1,40-792.284a13.006,13.006,0,0,1,4.527-5.582A11.529,11.529,0,0,1,51.317-800a11.529,11.529,0,0,1,6.79,2.135,13.006,13.006,0,0,1,4.527,5.582,13,13,0,0,1-4.527,5.581A11.528,11.528,0,0,1,51.317-784.568ZM51.317-792.284Zm0,6.173A10.351,10.351,0,0,0,57.04-787.8a10.932,10.932,0,0,0,3.974-4.488,10.943,10.943,0,0,0-3.97-4.488,10.33,10.33,0,0,0-5.723-1.685,10.351,10.351,0,0,0-5.727,1.685,11.116,11.116,0,0,0-4,4.488,11.127,11.127,0,0,0,4,4.488A10.33,10.33,0,0,0,51.313-786.111Z"
                                                                                    transform="translate(-40 800)" fill={`${client.status === 'ACTIVE' ? "#1447B2" : "#D1D1D1"}`} />
                                                                            </svg>
                                                                            {showActiveIndexCopyIdPopup === index && (
                                                                                <CopyIdPopUp closePopUp={() => setShowActiveIndexCopyIdPopup(null)} partnerId={currentClient.partnerId} policyName={currentClient.policyName} id={currentClient.clientId} header='oidcClientsList.oidcClientId' styleSet={styles} />
                                                                            )}
                                                                        </div>
                                                                    </td>
                                                                    <td className="px-2 mx-2 cursor-default">
                                                                        <div className="flex items-center justify-center relative" ref={setSubmenuRef(submenuRef, index)}>
                                                                            <button id={'oidc_details' + (index + 1)} onClick={() => setViewClientId(index === viewClientId ? null : index)} className="font-semibold mb-0.5 cursor-pointer text-[#1447B2]">
                                                                                ...
                                                                            </button>
                                                                            {viewClientId === index && (
                                                                                <div className={`absolute w-[7rem] ${currentArray.length - 1 === index ? '-bottom-2' : currentArray.length - 2 === index ? '-bottom-2' : 'top-5'} z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-[0.7rem] text-right" : "right-[0.7rem] text-left"}`}>
                                                                                    <div role='button' id="oidc_details_view_btn" onClick={() => onClickView(client)} className={`flex justify-between py-2 px-2 cursor-pointer text-[#3E3E3E] hover:bg-gray-100 items-center ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                                                        <p>{t('oidcClientsList.view')}</p>
                                                                                        <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                    <div role='button' id="oidc_edit_btn" onClick={() => showEditOidcClient(client)} className={`flex justify-between py-2 px-2 ${isLoginLanguageRTL ? "text-right" : "text-left"} ${client.status === "ACTIVE" ? 'text-[#3E3E3E] cursor-pointer hover:bg-gray-100' : 'text-[#BEBEBE]'}`}>
                                                                                        <p>{t('oidcClientsList.edit')}</p>
                                                                                        <img src={client.status === "ACTIVE" ? editIcon : disableEditPolicyIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                    <div role='button' id="oidc_deactive_btn" onClick={() => showDeactivateOidcClient(client, index)} className={`flex justify-between py-2 px-2 ${isLoginLanguageRTL ? "text-right" : "text-left"} ${client.status === "ACTIVE" ? 'text-[#3E3E3E] cursor-pointer' : 'text-[#A5A5A5] cursor-auto'} hover:bg-gray-100`} >
                                                                                        <p>{t('oidcClientsList.deActivate')}</p>
                                                                                        <img src={client.status === "ACTIVE" ? deactivateIcon : disableDeactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                </div>
                                                                            )}
                                                                            {showActiveIndexDeactivatePopup === index && (
                                                                                <DeactivatePopup
                                                                                    closePopUp={closeDeactivatePopup}
                                                                                    onClickConfirm={(deactivationResponse) => onClickConfirmDeactivate(deactivationResponse, selectedOidcClient)}
                                                                                    popupData={selectedOidcClient} request={deactivateRequest}
                                                                                    headerMsg='deactivateOidcClient.oidcClientName'
                                                                                    descriptionMsg='deactivateOidcClient.description'
                                                                                    headerKeyName={selectedOidcClient.clientNameEng}
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
                                    }
                                    <Pagination dataListLength={filteredOidcClientsList.length} selectedRecordsPerPage={selectedRecordsPerPage} setSelectedRecordsPerPage={setSelectedRecordsPerPage} setFirstIndex={setFirstIndex}></Pagination>
                                </div>
                            </>
                        }
                    </div>
                </>
            )}
        </div>
    )
}

export default OidcClientsList; 