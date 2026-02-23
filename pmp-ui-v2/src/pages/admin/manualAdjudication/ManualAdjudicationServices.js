import React, { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import {
    isLangRTL, handleMouseClickForDropdown, resetPageNumber, onClickApplyFilter, setPageNumberAndPageSize,
    getPartnerManagerUrl, handleServiceErrors, onResetFilter, formatDate, bgOfStatus, getStatusCode, setSubmenuRef, createRequest
} from '../../../utils/AppUtils';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from '../../common/LoadingIcon';
import EmptyList from '../../common/EmptyList';
import Title from '../../common/Title.js';
import { HttpService } from '../../../services/HttpService.js';
import FilterButtons from '../../common/FilterButtons.js';
import AdminApiKeysListFilter from '../authenticationServices/AdminApiKeysListFilter.js';
import SortingIcon from '../../common/SortingIcon.js';
import Pagination from '../../common/Pagination.js';

const MANUAL_ADJUDICATION_RETURN_PATH = '/partnermanagement/admin/manual-adjudication-services/api-keys-list';

function ManualAdjudicationServices() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [apiKeysList, setApiKeysList] = useState([]);
    const [expandFilter, setExpandFilter] = useState(false);
    const [order, setOrder] = useState("DESC");
    const [activeAscIcon, setActiveAscIcon] = useState("");
    const [activeDescIcon, setActiveDescIcon] = useState("createdDateTime");
    const [actionId, setActionId] = useState(-1);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(sessionStorage.getItem('itemsPerPage') ? Number(sessionStorage.getItem('itemsPerPage')) : 8);
    const [sortFieldName, setSortFieldName] = useState("createdDateTime");
    const [sortType, setSortType] = useState("desc");
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState(sessionStorage.getItem('itemsPerPage') ? Number(sessionStorage.getItem('itemsPerPage')) : 8);
    const [fetchData, setFetchData] = useState(false);
    const [tableDataLoaded, setTableDataLoaded] = useState(true);
    const [totalRecords, setTotalRecords] = useState(0);
    const [showActiveIndexDeactivatePopup, setShowActiveIndexDeactivatePopup] = useState(null);
    const [selectedApiKey, setSelectedApiKey] = useState({});
    const [deactivateRequest, setDeactivateRequest] = useState({});
    const [resetPageNo, setResetPageNo] = useState(false);
    const [applyFilter, setApplyFilter] = useState(false);
    const [isApplyFilterClicked, setIsApplyFilterClicked] = useState(false);
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
        { id: "partnerId", headerNameKey: 'manualAdjudicationServices.partnerId' },
        { id: "orgName", headerNameKey: "manualAdjudicationServices.orgName" },
        { id: "policyGroupName", headerNameKey: "manualAdjudicationServices.policyGroup" },
        { id: "policyName", headerNameKey: "manualAdjudicationServices.policyName" },
        { id: "apiKeyLabel", headerNameKey: "manualAdjudicationServices.apiKeyName" },
        { id: "createdDateTime", headerNameKey: "manualAdjudicationServices.creationDate" },
        { id: "apiKeyExpiryDateTime", headerNameKey: "apiKeysList.expirationDate" },
        { id: "status", headerNameKey: "manualAdjudicationServices.status" },
        { id: "action", headerNameKey: 'manualAdjudicationServices.action' }
    ];

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
    }, [submenuRef]);

    const fetchApiKeysListData = async () => {
        const queryParams = new URLSearchParams();
        queryParams.append('sortFieldName', sortFieldName);
        queryParams.append('sortType', sortType);
        queryParams.append('pageSize', pageSize);

        const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, resetPageNo);
        queryParams.append('pageNo', effectivePageNo);
        setResetPageNo(false);

        queryParams.append('partnerType', 'Manual_Adjudication');
        if (filterAttributes.partnerId) queryParams.append('partnerId', filterAttributes.partnerId);
        if (filterAttributes.orgName) queryParams.append('orgName', filterAttributes.orgName);
        if (filterAttributes.policyGroupName) queryParams.append('policyGroupName', filterAttributes.policyGroupName);
        if (filterAttributes.policyName) queryParams.append('policyName', filterAttributes.policyName);
        if (filterAttributes.apiKeyLabel) queryParams.append('apiKeyLabel', filterAttributes.apiKeyLabel);
        if (filterAttributes.status) queryParams.append('status', filterAttributes.status);

        const url = `${getPartnerManagerUrl('/partner-api-keys/v2', process.env.NODE_ENV)}?${queryParams.toString()}`;
        try {
            fetchData ? setTableDataLoaded(false) : setDataLoaded(false);
            const response = await HttpService.get(url);
            if (response) {
                const responseData = response.data;
                if (responseData?.response) {
                    const resData = responseData.response.data;
                    setTotalRecords(responseData.response.totalResults);
                    setApiKeysList(resData || []);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('manualAdjudicationServices.errorInManualAdjudicationList'));
            }
            fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
            setFetchData(false);
        } catch (err) {
            console.error('Error fetching Manual Adjudication API keys:', err);
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            setFetchData(false);
            fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
        }
    };

    useEffect(() => {
        fetchApiKeysListData();
    }, [sortFieldName, sortType, pageNo, pageSize]);

    useEffect(() => {
        if (isApplyFilterClicked && pageNo === 0) {
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
                status: "De-active"
            }, "mosip.pms.update.api.key.patch", true);
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

    const editExpiryDate = (selectedApiKeyData) => {
        if (selectedApiKeyData.status !== 'deactivated') {
            setActionId(-1);
            sessionStorage.setItem('selectedApiKeyAttributes', JSON.stringify(selectedApiKeyData));
            sessionStorage.setItem('apiKeyListReturnPath', MANUAL_ADJUDICATION_RETURN_PATH);
            navigate('/partnermanagement/admin/authentication-services/edit-api-key');
        }
    };

    const onClickConfirmDeactivate = (deactivationResponse, selectedApiKeyData) => {
        if (deactivationResponse !== "") {
            setSelectedApiKey({});
            setShowActiveIndexDeactivatePopup(null);
            setApiKeysList((prevList) =>
                prevList.map(apiKey =>
                    (apiKey.apiKeyLabel === selectedApiKeyData.apiKeyLabel && apiKey.policyId === selectedApiKeyData.policyId && apiKey.partnerId === selectedApiKeyData.partnerId) ? { ...apiKey, status: "deactivated" } : apiKey
                )
            );
        }
    };

    const viewApiKeyRequestDetails = (selectedApiKeyData) => {
        sessionStorage.setItem('selectedApiKeyAttributes', JSON.stringify(selectedApiKeyData));
        sessionStorage.setItem('apiKeyListReturnPath', MANUAL_ADJUDICATION_RETURN_PATH);
        navigate('/partnermanagement/admin/authentication-services/view-api-key-details');
    };

    const generateApiKey = () => {
        navigate('/partnermanagement/admin/manual-adjudication-services/generate-api-key');
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const styles = {
        loadingDiv: "!py-[20%]",
        outerDiv: "!bg-opacity-[16%]"
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage id='manual_adjudication_api_key_list_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <div className="flex justify-between mb-5 max-470:flex-col">
                            <Title title='dashboard.manualAdjudication' backLink='/partnermanagement' />
                            <button
                                id="generate_api_key"
                                type="button"
                                onClick={generateApiKey}
                                className="h-10 text-sm font-semibold px-5 rounded-md bg-tory-blue text-white"
                            >
                                {t('manualAdjudicationServices.generateApiKey')}
                            </button>
                        </div>
                        {!applyFilter && apiKeysList.length === 0 && totalRecords === 0 ? (
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <EmptyList
                                    tableHeaders={tableHeaders}
                                    showCustomButton={true}
                                    customButtonName='manualAdjudicationServices.generateApiKey'
                                    buttonId='generate_api_key_empty'
                                    onClickButton={generateApiKey}
                                />
                            </div>
                        ) : (
                            <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                                <FilterButtons
                                    listTitle='manualAdjudicationServices.listOfManualAdjudicationApiKeys'
                                    dataListLength={totalRecords}
                                    filter={expandFilter}
                                    onResetFilter={onResetFilter}
                                    setFilter={setExpandFilter}
                                />
                                <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                {expandFilter && (
                                    <AdminApiKeysListFilter onApplyFilter={onApplyFilter} />
                                )}
                                {!tableDataLoaded ? (
                                    <LoadingIcon styleSet={styles} />
                                ) : (
                                    <>
                                        {applyFilter && apiKeysList.length === 0 ?
                                            <EmptyList tableHeaders={tableHeaders} />
                                            : (
                                                <>
                                                    <div className="mx-[1.5rem] overflow-x-scroll">
                                                        <table className="table-fixed">
                                                            <thead>
                                                                <tr>
                                                                    {tableHeaders.map((header, index) => (
                                                                        <th key={index} className="py-4 text-sm font-semibold text-[#6F6E6E] w-[13%]">
                                                                            <div id={`${header.headerNameKey}_header`} className={`mx-2 flex gap-x-0 items-center ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                                                {t(header.headerNameKey)}
                                                                                {(header.id !== "action") && (
                                                                                    <SortingIcon
                                                                                        id={`${header.headerNameKey}_sorting_icon`}
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
                                                                    ))}
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                {apiKeysList.map((apiKey, index) => (
                                                                    <tr id={"manual_adj_api_key_list_item" + (index + 1)} key={index}
                                                                        className={`border-t border-[#E5EBFA] text-[0.8rem] font-semibold break-words cursor-default ${apiKey.status === 'deactivated' ? "text-[#969696]" : "text-[#191919]"}`}>
                                                                        <td className="px-2">{apiKey.partnerId}</td>
                                                                        <td className="px-2">{apiKey.orgName ? apiKey.orgName : '-'}</td>
                                                                        <td className="px-2">{apiKey.policyGroupName ? apiKey.policyGroupName : '-'}</td>
                                                                        <td className="px-2">{apiKey.policyName ? apiKey.policyName : '-'}</td>
                                                                        <td className="px-2">{apiKey.apiKeyLabel}</td>
                                                                        <td className="px-2">{formatDate(apiKey.createdDateTime, "date")}</td>
                                                                        <td className="px-2">{apiKey.apiKeyExpiryDateTime ? formatDate(apiKey.apiKeyExpiryDateTime, 'date') : t('apiKeysList.noExpiry')}</td>
                                                                        <td>
                                                                            <div className={`${bgOfStatus(apiKey.status)} flex min-w-fit w-14 justify-center py-1.5 px-2 mx-2 my-3 text-xs font-semibold rounded-md`}>
                                                                                {getStatusCode(apiKey.status, t)}
                                                                            </div>
                                                                        </td>
                                                                        <td className="text-center cursor-default">
                                                                            <div ref={setSubmenuRef(submenuRef, index)}>
                                                                                <button id={"manual_adj_api_key_list_action_view" + (index + 1)} onClick={() => setActionId(index === actionId ? null : index)} className="font-semibold mb-0.5 text-[#191919] cursor-pointer text-center">
                                                                                    ...
                                                                                </button>
                                                                                {actionId === index && (
                                                                                    <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                                                                        <div className={`flex justify-between py-1.5 px-4 cursor-default text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>
                                                                                            <p id="manual_adj_api_key_list_view_btn">{t("partnerList.view")}</p>
                                                                                        </div>
                                                                                        <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                        <div className={`flex justify-between py-1.5 px-4 cursor-default ${isLoginLanguageRTL ? "pl-10" : "pr-10"} text-[#3E3E3E]`}>
                                                                                            <p id="manual_adj_api_key_list_deactivate_btn">{t("partnerList.deActivate")}</p>
                                                                                        </div>
                                                                                    </div>
                                                                                )}
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                ))}
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </>
                                            )}
                                    </>
                                )}
                                <Pagination
                                    dataListLength={totalRecords}
                                    selectedRecordsPerPage={selectedRecordsPerPage}
                                    setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                                    isServerSideFilter={true}
                                    getPaginationValues={getPaginationValues}
                                    isApplyFilterClicked={isApplyFilterClicked}
                                    setIsApplyFilterClicked={setIsApplyFilterClicked}
                                />
                            </div>
                        )}
                    </div>
                </>
            )}
        </div>
    );
}

export default ManualAdjudicationServices;
