import React, { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../../services/UserProfileService';
import {
    isLangRTL, formatDate, handleMouseClickForDropdown, onPressEnterKey, getPolicyManagerUrl,
    handleServiceErrors, resetPageNumber, onClickApplyFilter, setPageNumberAndPageSize, onResetFilter,
    getStatusCode, bgOfStatus, escapeKeyHandler,
    createRequest, setSubmenuRef
} from '../../../utils/AppUtils';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from "../../common/LoadingIcon";
import Title from '../../common/Title.js';
import PoliciesTab from './PoliciesTab.js';
import FilterButtons from '../../common/FilterButtons.js';
import SortingIcon from '../../common/SortingIcon.js';
import viewIcon from "../../../svg/view_icon.svg";
import replicateIcon from "../../../svg/replicate_icon.svg";
import disableReplicateIcon from "../../../svg/disable_replicate_icon.svg";
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import disableDeactivateIcon from "../../../svg/disable_deactivate_icon.svg";
import Pagination from '../../common/Pagination.js';
import { HttpService } from '../../../services/HttpService.js';
import PoliciesListFilter from './PoliciesListFilter.js';
import EmptyList from '../../common/EmptyList.js';
import ClonePolicyPopup from './ClonePolicyPopup.js';
import editPolicyIcon from "../../../svg/edit_policy_icon.svg";
import disableEditPolicyIcon from "../../../svg/disable_edit_policy_icon.svg";
import publishPolicyIcon from "../../../svg/publish_policy_icon.svg";
import disablePublishPolicyIcon from "../../../svg/disable_publish_policy_icon.svg";
import DeactivatePolicyPopup from './DeactivatePolicyPopup.js';
import PublishPolicyPopup from './PublishPolicyPopup.js';

function PoliciesList({ policyType, createPolicyButtonName, createPolicy, subTitle, fetchDataErrorMessage, viewPolicy, editPolicy }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [policiesList, setPoliciesList] = useState([]);
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
    const [deactivatePolicyHeader, setDeactivatePolicyHeader] = useState();
    const [deactivatePolicyDescription, setDeactivatePolicyDescription] = useState();
    const [deactivateRequest, setDeactivateRequest] = useState({});
    const [selectedPolicy, setSelectedPolicy] = useState({});
    const [filterAttributes, setFilterAttributes] = useState({
        policyId: null,
        policyName: null,
        policyDescription: null,
        policyGroupName: null,
        status: null,
    });
    const submenuRef = useRef([]);
    const [showActiveIndexClonePopup, setActiveIndexShowClonePopup] = useState(null);
    const [showActiveIndexPublishPolicyPopup, setShowActiveIndexPublishPolicyPopup] = useState(null);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
    }, [submenuRef]);

    const tableHeaders = [
        { id: "policyId", headerNameKey: "policiesList.policyId" },
        { id: "policyName", headerNameKey: "policiesList.policyName" },
        { id: "policyDescription", headerNameKey: "policiesList.policyDescription" },
        { id: "policyGroupName", headerNameKey: "policiesList.policyGroup" },
        { id: "createdDateTime", headerNameKey: "policiesList.creationDate" },
        { id: "status", headerNameKey: "policiesList.status" },
        { id: "action", headerNameKey: "policiesList.action" },
    ];


    const fetchPoliciesListData = async () => {
        const queryParams = new URLSearchParams();
        queryParams.append('sortFieldName', sortFieldName);
        queryParams.append('sortType', sortType);
        queryParams.append('pageSize', pageSize);

        //reset page number to 0 if filter applied or page number is out of bounds
        const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, resetPageNo);
        queryParams.append('pageNo', effectivePageNo);
        setResetPageNo(false);

        queryParams.append('policyType', policyType);
        if (filterAttributes.policyId) queryParams.append('policyId', filterAttributes.policyId);
        if (filterAttributes.policyName) queryParams.append('policyName', filterAttributes.policyName);
        if (filterAttributes.policyDescription) queryParams.append('policyDescription', filterAttributes.policyDescription);
        if (filterAttributes.policyGroupName) queryParams.append('policyGroupName', filterAttributes.policyGroupName);
        if (filterAttributes.status) queryParams.append('status', filterAttributes.status);

        const url = `${getPolicyManagerUrl('/policies/v2', process.env.NODE_ENV)}?${queryParams.toString()}`;
        try {
            fetchData ? setTableDataLoaded(false) : setDataLoaded(false);
            const response = await HttpService({
                url: url,
                method: 'get',
                baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL
            });
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response.data;
                    setTotalRecords(responseData.response.totalResults);
                    if (resData !== null) {
                        setPoliciesList(resData)
                    }
                    else {
                        setPoliciesList([]);
                    }
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t(fetchDataErrorMessage));
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
        localStorage.setItem('activeTab', policyType);
        fetchPoliciesListData();
    }, [sortFieldName, sortType, pageNo, pageSize]);

    useEffect(() => {

        if (isApplyFilterClicked) {
            fetchPoliciesListData();
            setIsApplyFilterClicked(false);
        }
    }, [isApplyFilterClicked]);

    const onApplyFilter = (updatedfilters) => {
        onClickApplyFilter(updatedfilters, setApplyFilter, setResetPageNo, setFetchData, setFilterAttributes, setIsApplyFilterClicked);
    };

    const getPaginationValues = (recordsPerPage, pageIndex) => {
        setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setFetchData);
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const showDeactivatePolicy = (policy, index) => {
        if (policy.status === 'activated') {
            setDeactivatePolicyHeader('deactivatePolicyPopup.headerMsg');
            if (policyType === 'Auth') {
                setDeactivatePolicyDescription('deactivatePolicyPopup.authPolicyDescriptionMsg');
            } else if (policyType === 'DataShare') {
                setDeactivatePolicyDescription('deactivatePolicyPopup.dataSharePolicyDescriptionMsg');
            }
            const request = createRequest({
                status: "De-Activate",
            }, "mosip.pms.deactivate.policy.patch", true);
            setActionId(-1);
            setSelectedPolicy(policy);
            setDeactivateRequest(request);
            setShowActiveIndexDeactivatePopup(index);
        }
    };

    const onClickClone = (selectedPolicy, index) => {
        if (selectedPolicy.status !== 'draft') {
            setActiveIndexShowClonePopup(index);
            setActionId(-1);
            setSelectedPolicy(selectedPolicy);
        }
    };

    const onClickPublish = (selectedPolicy, index) => {
        if (selectedPolicy.status === "draft") {
            setShowActiveIndexPublishPolicyPopup(index);
            setActionId(-1);
            setSelectedPolicy(selectedPolicy);
        }
    };

    const onClickEdit = (selectedPolicy) => {
        if (selectedPolicy.status === "draft") {
            editPolicy(selectedPolicy);
        }
    };

    const publishSuccess = (selectedPolicy) => {
        setShowActiveIndexPublishPolicyPopup(null);
        setSelectedPolicy({});
        setPoliciesList((prevList) =>
            prevList.map(policy =>
                policy.policyId === selectedPolicy.policyId ? { ...policy, status: 'activated' } : policy
            )
        );
    }

    const closePublishPolicyPopup = () => {
        setSelectedPolicy({});
        setShowActiveIndexPublishPolicyPopup(null);
    };

    const closeClonePolicyPopup = () => {
        setActiveIndexShowClonePopup(null);
        setSelectedPolicy({});
    };

    useEffect(() => {
        if (showActiveIndexClonePopup) {
            escapeKeyHandler(closeClonePolicyPopup);
        } else if (showActiveIndexDeactivatePopup) {
            escapeKeyHandler(closeDeactivatePopup);
        } else if (showActiveIndexPublishPolicyPopup) {
            escapeKeyHandler(closePublishPolicyPopup);
        }
    }, [showActiveIndexClonePopup, showActiveIndexDeactivatePopup, showActiveIndexPublishPolicyPopup]);

    const onClickConfirmDeactivate = (deactivationResponse, selectedPolicy) => {
        if (deactivationResponse && !deactivationResponse.isActive) {
            setActionId(-1);
            setSelectedPolicy({});
            setShowActiveIndexDeactivatePopup(null);
            // Update the specific row in the state with the new status
            setPoliciesList((prevList) =>
                prevList.map(policy =>
                    policy.policyId === selectedPolicy.policyId ? { ...policy, status: "deactivated" } : policy
                )
            );
        }
    };

    const closeDeactivatePopup = () => {
        setSelectedPolicy({});
        setShowActiveIndexDeactivatePopup(null);
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

    const styles = {
        loadingDiv: "!py-[20%]"
    }

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
            {!dataLoaded && (
                <LoadingIcon />
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <div className="flex justify-between mb-5 max-470:flex-col">
                            <Title title='policyGroupList.policies' backLink='/partnermanagement' />
                            <>
                                {applyFilter || policiesList.length > 0 ?
                                    <button onClick={createPolicy} id='create_auth_policy_btn' type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md max-330:h-fit">
                                        {t(createPolicyButtonName)}
                                    </button>
                                    : null
                                }
                            </>
                        </div>
                        <PoliciesTab />
                        {!applyFilter && policiesList.length === 0 ?
                            (
                                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                    <EmptyList
                                        tableHeaders={tableHeaders}
                                        showCustomButton={true}
                                        customButtonName={createPolicyButtonName}
                                        buttonId='create_policy'
                                        onClickButton={createPolicy}
                                    />
                                </div>
                            ) : (
                                <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                                    <FilterButtons
                                        listTitle={subTitle}
                                        dataListLength={totalRecords}
                                        filter={expandFilter}
                                        onResetFilter={onResetFilter}
                                        setFilter={setExpandFilter}
                                    />
                                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                    {expandFilter && (
                                        <PoliciesListFilter onApplyFilter={onApplyFilter} />
                                    )}
                                    {!tableDataLoaded && <LoadingIcon styleSet={styles}></LoadingIcon>}
                                    {tableDataLoaded && applyFilter && policiesList.length === 0 ?
                                        <EmptyList
                                            tableHeaders={tableHeaders}
                                        />
                                        : (
                                            <>
                                                <div className="mx-[1.4rem] overflow-x-scroll">
                                                    <table className="table-fixed">
                                                        <thead>
                                                            <tr>
                                                                {tableHeaders.map((header, index) => {
                                                                    return (
                                                                        <th key={index} className="py-4 text-sm font-semibold text-[#6F6E6E] w-[20%]">
                                                                            <div className={`mx-2 flex gap-x-0 items-center ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                                                {t(header.headerNameKey)}
                                                                                {header.id !== "action" && (
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
                                                            {policiesList.map((policy, index) => {
                                                                return (
                                                                    <tr id={"policies_list_item" + (index + 1)} key={index}
                                                                        className={`border-t border-[#E5EBFA] ${policy.status !== 'deactivated' ? 'cursor-pointer' : 'cursor-default'} text-[0.8rem] text-[#191919] font-semibold break-words ${policy.status === 'deactivated' ? "text-[#969696]" : "text-[#191919]"}`}>
                                                                        <td onClick={() => policy.status !== 'deactivated' && viewPolicy(policy)} className={`px-2`}>{policy.policyId}</td>
                                                                        <td onClick={() => policy.status !== 'deactivated' && viewPolicy(policy)} className={`px-2`}>{policy.policyName}</td>
                                                                        <td onClick={() => policy.status !== 'deactivated' && viewPolicy(policy)} className={`px-2`}>{policy.policyDescription}</td>
                                                                        <td onClick={() => policy.status !== 'deactivated' && viewPolicy(policy)} className={`px-2`}>{policy.policyGroupName}</td>
                                                                        <td onClick={() => policy.status !== 'deactivated' && viewPolicy(policy)} className="px-2">{formatDate(policy.createdDateTime, "date")}</td>
                                                                        <td onClick={() => policy.status !== 'deactivated' && viewPolicy(policy)}>
                                                                            <div className={`${bgOfStatus(policy.status)} flex min-w-fit w-14 justify-center py-1.5 px-2 mx-2 my-3 text-xs font-semibold rounded-md`}>
                                                                                {getStatusCode(policy.status, t)}
                                                                            </div>
                                                                        </td>
                                                                        <td className="text-center cursor-default">
                                                                            <div ref={setSubmenuRef(submenuRef, index)}>
                                                                                <button id={"policies_list_view" + (index + 1)} onClick={() => setActionId(index === actionId ? null : index)} className={`font-semibold mb-0.5 text-[#191919] cursor-pointer text-center`}>
                                                                                    ...
                                                                                </button>
                                                                                {actionId === index && (
                                                                                    <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                                                                        <div role='button' className={`flex justify-between hover:bg-gray-100 ${policy.status === 'draft' ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => onClickPublish(policy, index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => onClickPublish(policy, index))}>
                                                                                            <p id="policy_publish_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${policy.status === 'draft' ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t("policiesList.publish")}</p>
                                                                                            <img src={policy.status === 'draft' ? publishPolicyIcon : disablePublishPolicyIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                        </div>
                                                                                        <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                        <div role='button' className="flex justify-between hover:bg-gray-100" onClick={() => viewPolicy(policy)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => viewPolicy(policy))}>
                                                                                            <p id="policy_details_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                                                            <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                        </div>
                                                                                        <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                        <div role='button' className={`flex justify-between ${policy.status === 'draft' ? 'hover:bg-gray-100 cursor-pointer' : 'cursor-default'}`} onClick={() => onClickEdit(policy)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => onClickEdit(policy))}>
                                                                                            <p id="policy_publish_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${policy.status === 'draft' ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t("policiesList.edit")}</p>
                                                                                            <img src={policy.status === 'draft' ? editPolicyIcon : disableEditPolicyIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                        </div>
                                                                                        <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                        <div role='button' className={`flex justify-between hover:bg-gray-100 ${policy.status !== 'draft' ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => onClickClone(policy, index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => onClickClone(policy, index))}>
                                                                                            <p id="policy_replicate_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${policy.status !== 'draft' ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t("policiesList.clone")}</p>
                                                                                            <img src={policy.status !== 'draft' ? replicateIcon : disableReplicateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                        </div>
                                                                                        <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                        <div role='button' className={`flex justify-between hover:bg-gray-100 ${policy.status === 'activated' ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => showDeactivatePolicy(policy, index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => showDeactivatePolicy(policy, index))}>
                                                                                            <p id="policy_deactivate_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${policy.status === 'activated' ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t("partnerList.deActivate")}</p>
                                                                                            <img src={policy.status === 'activated' ? deactivateIcon : disableDeactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                        </div>
                                                                                    </div>
                                                                                )}
                                                                                {showActiveIndexPublishPolicyPopup === index && (
                                                                                    <PublishPolicyPopup
                                                                                        policyDetails={selectedPolicy}
                                                                                        closePopUp={closePublishPolicyPopup}
                                                                                        onClickPublish={() => publishSuccess(selectedPolicy)}
                                                                                    />
                                                                                )}
                                                                                {showActiveIndexClonePopup === index && (
                                                                                    <ClonePolicyPopup
                                                                                        policyDetails={selectedPolicy}
                                                                                        closePopUp={closeClonePolicyPopup}
                                                                                    />
                                                                                )}
                                                                                {showActiveIndexDeactivatePopup === index && (
                                                                                    <DeactivatePolicyPopup
                                                                                        header={deactivatePolicyHeader}
                                                                                        description={deactivatePolicyDescription}
                                                                                        popupData={{ ...selectedPolicy, isDeactivatePolicy: true }}
                                                                                        headerKeyName={selectedPolicy.policyName}
                                                                                        closePopUp={closeDeactivatePopup}
                                                                                        onClickConfirm={(deactivationResponse) => onClickConfirmDeactivate(deactivationResponse, selectedPolicy)}
                                                                                        request={deactivateRequest}
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
                            )
                        }
                    </div>
                </>
            )}
        </div>
    )
}
export default PoliciesList;