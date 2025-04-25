import React, { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import {
    isLangRTL, handleMouseClickForDropdown, resetPageNumber, onClickApplyFilter, setPageNumberAndPageSize,
    getPartnerManagerUrl, handleServiceErrors, onResetFilter, formatDate, bgOfStatus, getStatusCode, onPressEnterKey,
    getOidcClientDetails,
    createRequest,
    populateClientNames,
    getClientNameLangMap,
    escapeKeyHandler,
    setSubmenuRef,
    isOidcClientAvailable,
} from '../../../utils/AppUtils';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from '../../common/LoadingIcon';
import EmptyList from '../../common/EmptyList';
import Title from '../../common/Title.js';
import AuthenticationServicesTab from '../../common/AuthenticationServicesTab.js';
import AdminOidcClientsFilter from './AdminOidcClientsFilter.js';
import viewIcon from "../../../svg/view_icon.svg";
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import disableDeactivateIcon from "../../../svg/disable_deactivate_icon.svg";
import FilterButtons from '../../common/FilterButtons.js';
import SortingIcon from '../../common/SortingIcon.js';
import Pagination from '../../common/Pagination.js';
import { HttpService } from '../../../services/HttpService.js';
import CopyIdPopUp from '../../common/CopyIdPopup.js';
import DeactivatePopup from '../../common/DeactivatePopup.js';

function AdminOidcClientsList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [oidcClientsList, setOidcClientsList] = useState([]);
    const [expandFilter, setExpandFilter] = useState(false);
    const [order, setOrder] = useState("DESC");
    const [activeAscIcon, setActiveAscIcon] = useState("");
    const [activeDescIcon, setActiveDescIcon] = useState("createdDateTime");
    const [actionId, setActionId] = useState(-1);
    const [firstIndex, setFirstIndex] = useState(0);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
    const [sortFieldName, setSortFieldName] = useState("createdDateTime");
    const [sortType, setSortType] = useState("desc");
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
    const [fetchData, setFetchData] = useState(false);
    const [tableDataLoaded, setTableDataLoaded] = useState(true);
    const [totalRecords, setTotalRecords] = useState(0);
    const [resetPageNo, setResetPageNo] = useState(false);
    const [applyFilter, setApplyFilter] = useState(false);
    const [isApplyFilterClicked, setIsApplyFilterClicked] = useState(false);
    const [selectedOidcClient, setSelectedOidcClient] = useState({});
    const [showActiveIndexClientIdPopup, setShowActiveIndexClientIdPopup] = useState(null);
    const [currentClient, setCurrentClient] = useState(null);
    const [showActiveIndexDeactivatePopup, setShowActiveIndexDeactivatePopup] = useState(null);
    const [deactivateRequest, setDeactivateRequest] = useState({});
    const [filterAttributes, setFilterAttributes] = useState({
        partnerId: null,
        orgName: null,
        policyGroupName: null,
        policyName: null,
        clientNameEng: null,
        status: null,
    });
    const submenuRef = useRef([]);
    const [showCompatibilityMsg, setShowCompatibilityMsg] = useState(false);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
    }, [submenuRef]);

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'oidcClientsList.partnerId' },
        { id: "orgName", headerNameKey: 'oidcClientsList.orgName' },
        { id: "policyGroupName", headerNameKey: "oidcClientsList.policyGroup" },
        { id: "policyName", headerNameKey: "oidcClientsList.policyName" },
        { id: "clientNameEng", headerNameKey: "oidcClientsList.oidcClientName" },
        { id: "createdDateTime", headerNameKey: "oidcClientsList.creationDate" },
        { id: "status", headerNameKey: "oidcClientsList.status" },
        { id: "clientId", headerNameKey: "oidcClientsList.oidcClientId" },
        { id: "action", headerNameKey: 'oidcClientsList.action' }
    ];

    const fetchOidcClientsListData = async () => {
        const queryParams = new URLSearchParams();
        queryParams.append('sortFieldName', sortFieldName);
        queryParams.append('sortType', sortType);
        queryParams.append('pageSize', pageSize);

        //reset page number to 0 if filter applied or page number is out of bounds
        const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, resetPageNo);
        queryParams.append('pageNo', effectivePageNo);
        setResetPageNo(false);

        if (filterAttributes.partnerId) queryParams.append('partnerId', filterAttributes.partnerId);
        if (filterAttributes.orgName) queryParams.append('orgName', filterAttributes.orgName);
        if (filterAttributes.policyGroupName) queryParams.append('policyGroupName', filterAttributes.policyGroupName);
        if (filterAttributes.policyName) queryParams.append('policyName', filterAttributes.policyName);
        if (filterAttributes.clientNameEng) queryParams.append('clientName', filterAttributes.clientNameEng);
        if (filterAttributes.status) queryParams.append('status', filterAttributes.status);

        const url = `${getPartnerManagerUrl('/oauth/client', process.env.NODE_ENV)}?${queryParams.toString()}`;
        try {
            fetchData ? setTableDataLoaded(false) : setDataLoaded(false);
            const response = await HttpService.get(url);
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response.data;
                    const populatedData = populateClientNames(resData);
                    setTotalRecords(responseData.response.totalResults);
                    setOidcClientsList(populatedData);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('oidcClientsList.errorInOidcClientsList'));
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
    }

    useEffect(() => {
        const checkCompatibleAndFetch = async () => {
            const isApiExist = await isOidcClientAvailable();
            if (isApiExist) {
                fetchOidcClientsListData();
            } else {
                setShowCompatibilityMsg(true);
            }
        };
        
        checkCompatibleAndFetch();
    }, [sortFieldName, sortType, pageNo, pageSize]);

    useEffect(() => {

        if (isApplyFilterClicked) {
            fetchOidcClientsListData();
            setIsApplyFilterClicked(false);
        }
    }, [isApplyFilterClicked]);

    const onApplyFilter = (updatedfilters) => {
        onClickApplyFilter(updatedfilters, setApplyFilter, setResetPageNo, setFetchData, setFilterAttributes, setIsApplyFilterClicked);
    };

    const getPaginationValues = (recordsPerPage, pageIndex) => {
        setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setFetchData);
    };

    const sortAscOrder = (header) => {
        if (order !== 'ASC' || activeAscIcon !== header) {
            setFetchData(true);
            setSortFieldName(header);
            setSortType('ASC');
            setOrder("ASC");
            setActiveDescIcon("");
            setActiveAscIcon(header);
        }
    };

    const sortDescOrder = (header) => {
        if (order !== 'DESC' || activeDescIcon !== header) {
            setFetchData(true);
            setSortFieldName(header);
            setSortType('DESC');
            setOrder("DESC");
            setActiveDescIcon(header);
            setActiveAscIcon("");
        }
    };

    const openClientIdPopUp = (client, index) => {
        if (client.status.toLowerCase() === "active") {
            setCurrentClient(client);
            setShowActiveIndexClientIdPopup(index);
        }
    };

    const viewOidcClientDetails = (selectedClient) => {
        localStorage.setItem('selectedOidcClientAttributes', JSON.stringify(selectedClient));
        navigate('/partnermanagement/admin/authentication-services/view-oidc-client-details');
    };

    const deactivateOidcClient = async (client, index) => {
        if (client.status === "ACTIVE") {
            const oidcClientDetails = await getOidcClientDetails(HttpService, client.clientId, setErrorCode, setErrorMsg);
            if (oidcClientDetails !== null) {
                const request = createRequest({
                    logoUri: oidcClientDetails.logoUri,
                    redirectUris: oidcClientDetails.redirectUris,
                    status: "INACTIVE",
                    grantTypes: oidcClientDetails.grantTypes,
                    clientName: client.clientNameEng,
                    clientAuthMethods: oidcClientDetails.clientAuthMethods,
                    clientNameLangMap: getClientNameLangMap(client.clientNameEng, client.clientNameJson)
                });
                setActionId(-1);
                setSelectedOidcClient(client);
                setDeactivateRequest(request);
                setShowActiveIndexDeactivatePopup(index);
            } else {
                setErrorMsg(t('deactivateOidc.errorInOidcDetails'));
            }
        }
    };

    const onClickConfirmDeactivate = (deactivationResponse, selectedClient) => {
        if (deactivationResponse && deactivationResponse.status === "INACTIVE") {
            setSelectedOidcClient({});
            setShowActiveIndexDeactivatePopup(null);
            setOidcClientsList((prevList) =>
                prevList.map(client =>
                    client.clientId === selectedClient.clientId ? { ...client, status: "INACTIVE" } : client
                )
            );
        }
    };

    const closeDeactivatePopup = () => {
        setSelectedOidcClient({});
        setShowActiveIndexDeactivatePopup(null);
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const styles = {
        loadingDiv: "!py-[20%]",
        outerDiv: "!bg-opacity-35"
    }

    useEffect(() => {
        if (showActiveIndexDeactivatePopup) {
            escapeKeyHandler(closeDeactivatePopup);
        } else if (showActiveIndexClientIdPopup) {
            escapeKeyHandler(() => setShowActiveIndexClientIdPopup(null));
        }
    }, [showActiveIndexDeactivatePopup, showActiveIndexClientIdPopup]);

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
                        <div className="flex justify-between mb-5 max-470:flex-col">
                            <Title title='authenticationServices.authenticationServices' backLink='/partnermanagement' />
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
                            oidcClientPath='/partnermanagement/admin/authentication-services/oidc-clients-list'
                            activeApiKey={false}
                            apiKeyPath='/partnermanagement/admin/authentication-services/api-keys-list'
                        />
                        {!applyFilter && oidcClientsList.length === 0 ? (
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <EmptyList tableHeaders={tableHeaders} />
                            </div>
                        ) : (
                            <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                                <FilterButtons
                                    listTitle='oidcClientsList.listOfOidcClients'
                                    dataListLength={totalRecords}
                                    filter={expandFilter}
                                    onResetFilter={onResetFilter}
                                    setFilter={setExpandFilter}
                                />
                                <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                {expandFilter && (
                                    <AdminOidcClientsFilter onApplyFilter={onApplyFilter} />
                                )}
                                {!tableDataLoaded && <LoadingIcon styleSet={styles}></LoadingIcon>}
                                {tableDataLoaded && applyFilter && oidcClientsList.length === 0 ?
                                    <EmptyList tableHeaders={tableHeaders} />
                                    : (
                                        <>
                                            <div className="mx-[1.5rem] overflow-x-scroll">
                                                <table className="table-fixed">
                                                    <thead>
                                                        <tr>
                                                            {tableHeaders.map((header, index) => {
                                                                return (
                                                                    <th key={index} className="py-4 text-sm font-semibold text-[#6F6E6E] w-[15%]">
                                                                        <div className={`mx-2 flex gap-x-0 items-center ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                                            {t(header.headerNameKey)}
                                                                            {(header.id !== "action") && (header.id !== "clientId") && (header.id !== "clientNameEng") && (
                                                                                <SortingIcon
                                                                                    headerId={header.id}
                                                                                    sortDescOrder={sortDescOrder}
                                                                                    sortAscOrder={sortAscOrder}
                                                                                    order={order}
                                                                                    activeSortDesc={activeDescIcon}
                                                                                    activeSortAsc={activeAscIcon}
                                                                                />
                                                                            )}
                                                                        </div>
                                                                    </th>
                                                                );
                                                            })}
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        {oidcClientsList.map((client, index) => {
                                                            return (
                                                                <tr id={"oidc_client_list_item" + (index + 1)} key={index}
                                                                    className={`border-t border-[#E5EBFA] ${client.status !== 'INACTIVE' ? 'cursor-pointer' : 'cursor-default'} text-[0.8rem] text-[#191919] font-semibold break-words ${client.status === 'INACTIVE' ? "text-[#969696]" : "text-[#191919]"}`}>
                                                                    <td onClick={() => client.status !== 'INACTIVE' && viewOidcClientDetails(client)} className="px-2">{client.partnerId}</td>
                                                                    <td onClick={() => client.status !== 'INACTIVE' && viewOidcClientDetails(client)} className="px-2">{client.orgName ? client.orgName : '-'}</td>
                                                                    <td onClick={() => client.status !== 'INACTIVE' && viewOidcClientDetails(client)} className="px-2">{client.policyGroupName ? client.policyGroupName : '-'}</td>
                                                                    <td onClick={() => client.status !== 'INACTIVE' && viewOidcClientDetails(client)} className="px-2">{client.policyName ? client.policyName : '-'}</td>
                                                                    <td onClick={() => client.status !== 'INACTIVE' && viewOidcClientDetails(client)} className="px-2">{client.clientNameEng}</td>
                                                                    <td onClick={() => client.status !== 'INACTIVE' && viewOidcClientDetails(client)} className="px-2">{formatDate(client.createdDateTime, "date")}</td>
                                                                    <td onClick={() => client.status !== 'INACTIVE' && viewOidcClientDetails(client)}>
                                                                        <div className={`${bgOfStatus(client.status)} flex min-w-fit w-14 justify-center py-1.5 px-2 mx-2 my-3 text-xs font-semibold rounded-md`}>
                                                                            {getStatusCode(client.status, t)}
                                                                        </div>
                                                                    </td>
                                                                    <td className="px-2 mx-2 cursor-default">
                                                                        <div className="flex items-center justify-center">
                                                                            <svg className={`${client.status !== 'INACTIVE' ? 'cursor-pointer' : 'cursor-default'}`} id={'oidc_show_copy_popup_btn' + (index + 1)} onClick={() => openClientIdPopUp(client, index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => openClientIdPopUp(client, index))}
                                                                                xmlns="http://www.w3.org/2000/svg" width="22.634" height="15.433" viewBox="0 0 22.634 15.433">
                                                                                <path id="visibility_FILL0_wght400_GRAD0_opsz48"
                                                                                    d="M51.32-787.911a4.21,4.21,0,0,0,3.1-1.276,4.225,4.225,0,0,0,1.273-3.1,4.21,4.21,0,0,0-1.276-3.1,4.225,4.225,0,0,0-3.1-1.273,4.21,4.21,0,0,0-3.1,1.276,4.225,4.225,0,0,0-1.273,3.1,4.21,4.21,0,0,0,1.276,3.1A4.225,4.225,0,0,0,51.32-787.911Zm-.009-1.492a2.764,2.764,0,0,1-2.039-.842,2.794,2.794,0,0,1-.836-2.045,2.764,2.764,0,0,1,.842-2.039,2.794,2.794,0,0,1,2.045-.836,2.764,2.764,0,0,1,2.039.842,2.794,2.794,0,0,1,.836,2.045,2.764,2.764,0,0,1-.842,2.039A2.794,2.794,0,0,1,51.311-789.4Zm.006,4.836a11.528,11.528,0,0,1-6.79-2.135A13,13,0,0,1,40-792.284a13.006,13.006,0,0,1,4.527-5.582A11.529,11.529,0,0,1,51.317-800a11.529,11.529,0,0,1,6.79,2.135,13.006,13.006,0,0,1,4.527,5.582,13,13,0,0,1-4.527,5.581A11.528,11.528,0,0,1,51.317-784.568ZM51.317-792.284Zm0,6.173A10.351,10.351,0,0,0,57.04-787.8a10.932,10.932,0,0,0,3.974-4.488,10.943,10.943,0,0,0-3.97-4.488,10.33,10.33,0,0,0-5.723-1.685,10.351,10.351,0,0,0-5.727,1.685,11.116,11.116,0,0,0-4,4.488,11.127,11.127,0,0,0,4,4.488A10.33,10.33,0,0,0,51.313-786.111Z"
                                                                                    transform="translate(-40 800)" fill={`${client.status === 'ACTIVE' ? "#1447B2" : "#D1D1D1"}`} />
                                                                            </svg>
                                                                            {showActiveIndexClientIdPopup === index && (
                                                                                <CopyIdPopUp closePopUp={() => setShowActiveIndexClientIdPopup(null)} partnerId={currentClient.partnerId} policyName={currentClient.policyName} id={currentClient.clientId} header='oidcClientsList.oidcClientId' styleSet={styles} />
                                                                            )}
                                                                        </div>
                                                                    </td>
                                                                    <td className="text-center cursor-default">
                                                                        <div ref={setSubmenuRef(submenuRef, index)}>
                                                                            <button id={"oidc_client_list_action_view" + (index + 1)} onClick={() => setActionId(index === actionId ? null : index)} className={`font-semibold mb-0.5 text-[#191919] cursor-pointer text-center`}>
                                                                                ...
                                                                            </button>
                                                                            {actionId === index && (
                                                                                <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                                                                    <div role='button' className="flex justify-between hover:bg-gray-100" onClick={() => viewOidcClientDetails(client)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => viewOidcClientDetails(client))}>
                                                                                        <p id="oidc_clients_list_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                                                        <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                    <div role='button' className={`flex justify-between hover:bg-gray-100 ${client.status === 'ACTIVE' ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => deactivateOidcClient(client, index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => deactivateOidcClient(client, index))}>
                                                                                        <p id="oidc_clients_list_deactivate_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${client.status === 'ACTIVE' ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t("partnerList.deActivate")}</p>
                                                                                        <img src={client.status === 'ACTIVE' ? deactivateIcon : disableDeactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                </div>
                                                                            )}
                                                                            {showActiveIndexDeactivatePopup === index && (
                                                                                <DeactivatePopup
                                                                                    closePopUp={closeDeactivatePopup}
                                                                                    onClickConfirm={(deactivationResponse) => onClickConfirmDeactivate(deactivationResponse, selectedOidcClient)}
                                                                                    popupData={selectedOidcClient}
                                                                                    request={deactivateRequest}
                                                                                    headerMsg='deactivateOidc.header'
                                                                                    descriptionMsg='deactivateOidc.description'
                                                                                    headerKeyName={selectedOidcClient.clientNameEng}
                                                                                />
                                                                            )}
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            );
                                                        })}
                                                    </tbody>
                                                </table>
                                            </div>
                                            <Pagination
                                                dataListLength={totalRecords}
                                                selectedRecordsPerPage={selectedRecordsPerPage}
                                                setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                                                setFirstIndex={setFirstIndex}
                                                isServerSideFilter={true}
                                                getPaginationValues={getPaginationValues}
                                            />
                                        </>
                                    )}
                            </div>
                        )}
                    </div>
                </>
            )}
        </div>
    );
}
export default AdminOidcClientsList;