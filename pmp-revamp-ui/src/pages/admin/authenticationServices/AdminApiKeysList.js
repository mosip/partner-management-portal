import React, { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../../services/UserProfileService';
import {
    isLangRTL, handleMouseClickForDropdown, resetPageNumber, onClickApplyFilter, setPageNumberAndPageSize,
    getPartnerManagerUrl, handleServiceErrors, onResetFilter, formatDate, bgOfStatus, getStatusCode, onPressEnterKey, createRequest,
    escapeKeyHandler, setSubmenuRef
} from '../../../utils/AppUtils';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from '../../common/LoadingIcon';
import EmptyList from '../../common/EmptyList';
import Title from '../../common/Title.js';
import { HttpService } from '../../../services/HttpService.js';
import AuthenticationServicesTab from '../../common/AuthenticationServicesTab.js';
import FilterButtons from '../../common/FilterButtons.js';
import AdminApiKeysListFilter from './AdminApiKeysListFilter.js';
import SortingIcon from '../../common/SortingIcon.js';
import Pagination from '../../common/Pagination.js';
import viewIcon from "../../../svg/view_icon.svg";
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import disableDeactivateIcon from "../../../svg/disable_deactivate_icon.svg";
import { useNavigate } from 'react-router-dom';
import DeactivatePopup from '../../common/DeactivatePopup.js';

function AdminApiKeysList() {
    const { t } = useTranslation();
    const navigate = useNavigate('');
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [apiKeysList, setApiKeysList] = useState([]);
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
    const [showActiveIndexDeactivatePopup, setShowActiveIndexDeactivatePopup] = useState(null);
    const [selectedApiKey, setSelectedApiKey] = useState({});
    const [deactivateRequest, setDeactivateRequest] = useState({});
    const [filterAttributes, setFilterAttributes] = useState({
        partnerId: null,
        orgName: null,
        policyGroupName: null,
        policyName: null,
        apiKeyLabel: null,
        status: null,
    });
    const submenuRef = useRef([]);

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'oidcClientsList.partnerId' },
        { id: "orgName", headerNameKey: 'oidcClientsList.orgName' },
        { id: "policyGroupName", headerNameKey: "oidcClientsList.policyGroup" },
        { id: "policyName", headerNameKey: "oidcClientsList.policyName" },
        { id: "apiKeyLabel", headerNameKey: "apiKeysList.apiKeyName" },
        { id: "createdDateTime", headerNameKey: "oidcClientsList.creationDate" },
        { id: "status", headerNameKey: "oidcClientsList.status" },
        { id: "action", headerNameKey: 'oidcClientsList.action' }
    ];

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
    }, [submenuRef]);

    const fetchApiKeysListData = async () => {
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
        if (filterAttributes.apiKeyLabel) queryParams.append('apiKeyLabel', filterAttributes.apiKeyLabel);
        if (filterAttributes.status) queryParams.append('status', filterAttributes.status);

        const url = `${getPartnerManagerUrl('/partner-api-keys', process.env.NODE_ENV)}?${queryParams.toString()}`;
        try {
            fetchData ? setTableDataLoaded(false) : setDataLoaded(false);
            const response = await HttpService.get(url);
            if (response) {
                const responseData = response.data;
                if (responseData?.response) {
                    const resData = responseData.response.data;
                    setTotalRecords(responseData.response.totalResults);
                    setApiKeysList(resData);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('apiKeysList.errorInApiKeysList'));
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
        fetchApiKeysListData();
    }, [sortFieldName, sortType, pageNo, pageSize]);

    useEffect(() => {

        if (isApplyFilterClicked) {
            fetchApiKeysListData();
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


    const deactivateApiKey = (selectedApiKeyData, index) => {
        if (selectedApiKeyData.status === "activated") {
            const request = createRequest({
                label: selectedApiKeyData.apiKeyLabel,
                status: "De-Active"
            });
            setActionId(-1);
            setSelectedApiKey(selectedApiKeyData);
            setDeactivateRequest(request);
            setShowActiveIndexDeactivatePopup(index);
            document.body.style.overflow = "hidden";
        }
    };

    const closeDeactivatePopup = () => {
        setSelectedApiKey({});
        setShowActiveIndexDeactivatePopup(null);
        document.body.style.overflow = "auto";
    };

    const onClickConfirmDeactivate = (deactivationResponse, selectedApiKey) => {
        if (deactivationResponse !== "") {
            setSelectedApiKey({});
            setShowActiveIndexDeactivatePopup(null);
            setApiKeysList((prevList) =>
                prevList.map(apiKey =>
                    (apiKey.apiKeyLabel === selectedApiKey.apiKeyLabel && apiKey.policyId === selectedApiKey.policyId && apiKey.partnerId === selectedApiKey.partnerId) ? { ...apiKey, status: "deactivated" } : apiKey
                )
            );
        }
    };

    const viewApiKeyRequestDetails = (selectedApiKey) => {
        localStorage.setItem('selectedApiKeyAttributes', JSON.stringify(selectedApiKey));
        navigate('/partnermanagement/admin/authentication-services/view-api-key-details');
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    useEffect(() => {
        escapeKeyHandler(closeDeactivatePopup)
    }, [showActiveIndexDeactivatePopup]);

    const styles = {
        loadingDiv: "!py-[20%]",
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
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <div className="flex justify-between mb-5 max-470:flex-col">
                            <Title title='authenticationServices.authenticationServices' backLink='/partnermanagement' />
                        </div>
                        <AuthenticationServicesTab
                            activeOidcClient={false}
                            oidcClientPath='/partnermanagement/admin/authentication-services/oidc-clients-list'
                            activeApiKey={true}
                            apiKeyPath='/partnermanagement/admin/authentication-services/api-keys-list'
                        />
                        {!applyFilter && apiKeysList.length === 0 ? (
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <EmptyList tableHeaders={tableHeaders} />
                            </div>
                        ) : (
                            <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                                <FilterButtons
                                    listTitle='apiKeysList.listOfApiKeyRequests'
                                    dataListLength={totalRecords}
                                    filter={expandFilter}
                                    onResetFilter={onResetFilter}
                                    setFilter={setExpandFilter}
                                />
                                <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                {expandFilter && (
                                    <AdminApiKeysListFilter onApplyFilter={onApplyFilter} />
                                )}
                                {!tableDataLoaded && <LoadingIcon styleSet={styles}></LoadingIcon>}
                                {tableDataLoaded && applyFilter && apiKeysList.length === 0 ?
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
                                                                            {(header.id !== "action") && (
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
                                                        {apiKeysList.map((apiKey, index) => {
                                                            return (
                                                                <tr id={"api_key_list_item" + (index + 1)} key={index}
                                                                    className={`border-t border-[#E5EBFA] ${apiKey.status !== 'deactivated' ? 'cursor-pointer' : 'cursor-default'} text-[0.8rem] text-[#191919] font-semibold break-words ${apiKey.status === 'deactivated' ? "text-[#969696]" : "text-[#191919]"}`}>
                                                                    <td onClick={() => apiKey.status !== 'deactivated' && viewApiKeyRequestDetails(apiKey)} className="px-2">{apiKey.partnerId}</td>
                                                                    <td onClick={() => apiKey.status !== 'deactivated' && viewApiKeyRequestDetails(apiKey)} className="px-2">{apiKey.orgName ? apiKey.orgName : '-'}</td>
                                                                    <td onClick={() => apiKey.status !== 'deactivated' && viewApiKeyRequestDetails(apiKey)} className="px-2">{apiKey.policyGroupName ? apiKey.policyGroupName : '-'}</td>
                                                                    <td onClick={() => apiKey.status !== 'deactivated' && viewApiKeyRequestDetails(apiKey)} className="px-2">{apiKey.policyName ? apiKey.policyName : '-'}</td>
                                                                    <td onClick={() => apiKey.status !== 'deactivated' && viewApiKeyRequestDetails(apiKey)} className="px-2">{apiKey.apiKeyLabel}</td>
                                                                    <td onClick={() => apiKey.status !== 'deactivated' && viewApiKeyRequestDetails(apiKey)} className="px-2">{formatDate(apiKey.createdDateTime, "date")}</td>
                                                                    <td onClick={() => apiKey.status !== 'deactivated' && viewApiKeyRequestDetails(apiKey)}>
                                                                        <div className={`${bgOfStatus(apiKey.status)} flex min-w-fit w-14 justify-center py-1.5 px-2 mx-2 my-3 text-xs font-semibold rounded-md`}>
                                                                            {getStatusCode(apiKey.status, t)}
                                                                        </div>
                                                                    </td>
                                                                    <td className="text-center cursor-default">
                                                                        <div ref={setSubmenuRef(submenuRef, index)}>
                                                                            <button id={"api_key_list_action_view" + (index + 1)} onClick={() => setActionId(index === actionId ? null : index)} className={`font-semibold mb-0.5 text-[#191919] cursor-pointer text-center`}>
                                                                                ...
                                                                            </button>
                                                                            {actionId === index && (
                                                                                <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                                                                    <div role='button' className="flex justify-between hover:bg-gray-100" onClick={() => viewApiKeyRequestDetails(apiKey)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => viewApiKeyRequestDetails(apiKey))}>
                                                                                        <p id="api_key_list_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                                                        <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                    <div role='button' className={`flex justify-between hover:bg-gray-100 ${apiKey.status === 'activated' ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => deactivateApiKey(apiKey, index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => deactivateApiKey(apiKey, index))}>
                                                                                        <p id="api_key_list_deactivate_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${apiKey.status === 'activated' ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t("partnerList.deActivate")}</p>
                                                                                        <img src={apiKey.status === 'activated' ? deactivateIcon : disableDeactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                </div>
                                                                            )}
                                                                            {showActiveIndexDeactivatePopup === index && (
                                                                                <DeactivatePopup
                                                                                    closePopUp={closeDeactivatePopup}
                                                                                    onClickConfirm={(deactivationResponse) => onClickConfirmDeactivate(deactivationResponse, selectedApiKey)}
                                                                                    popupData={selectedApiKey}
                                                                                    request={deactivateRequest}
                                                                                    headerMsg="adminDeactivateApiKey.title"
                                                                                    descriptionMsg="adminDeactivateApiKey.description"
                                                                                    headerKeyName={selectedApiKey.apiKeyLabel}
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
export default AdminApiKeysList;