import React, { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import {
    isLangRTL, formatDate, handleMouseClickForDropdown, onPressEnterKey, getPolicyManagerUrl,
    handleServiceErrors, resetPageNumber, onClickApplyFilter, setPageNumberAndPageSize, onResetFilter
} from '../../../utils/AppUtils';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from "../../common/LoadingIcon";
import Title from '../../common/Title.js';
import PoliciesTab from './PoliciesTab.js';
import FilterButtons from '../../common/FilterButtons.js';
import SortingIcon from '../../common/SortingIcon.js';
import viewIcon from "../../../svg/view_icon.svg";
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import Pagination from '../../common/Pagination.js';
import { HttpService } from '../../../services/HttpService.js';
import PoliciesListFilter from './PoliciesListFilter.js';
import EmptyList from '../../common/EmptyList.js';

function PoliciesList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [authPoliciesList, setAuthPoliciesList] = useState([]);
    const [dataSharePoliciesList, setDataSharePoliciesList] = useState([]);
    const [expandFilter, setExpandFilter] = useState(false);
    const [order, setOrder] = useState("ASC");
    const [activeAscIcon, setActiveAscIcon] = useState("createdDateTime");
    const [activeDescIcon, setActiveDescIcon] = useState("");
    const [actionId, setActionId] = useState(-1);
    const [firstIndex, setFirstIndex] = useState(0);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
    const [sortFieldName, setSortFieldName] = useState("createdDateTime");
    const [sortType, setSortType] = useState("desc");
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState(8);
    const [fetchData, setFetchData] = useState(false);
    const [tableDataLoaded, setTableDataLoaded] = useState(true);
    const [totalRecords, setTotalRecords] = useState(0);
    const [resetPageNo, setResetPageNo] = useState(false);
    const [applyFilter, setApplyFilter] = useState(false);
    const [filterAttributes, setFilterAttributes] = useState({
        policyId: null,
        policyName: null,
        policyDescription: null,
        policyGroupName: null,
        status: null,
    });
    const [selectedActiveTab, setSelectedActiveTab] = useState('');
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
    }, [submenuRef]);

    const tableHeaders = [
        { id: "policyId", headerNameKey: "policiesList.policyId" },
        { id: "policyName", headerNameKey: "policiesList.policyName" },
        { id: "policyDescription", headerNameKey: "policiesList.policyDescription" },
        { id: "policyGroupName", headerNameKey: "policiesList.policyGroup" },
        { id: "createdDateTime", headerNameKey: "policiesList.createdDate" },
        { id: "status", headerNameKey: "policiesList.status" },
        { id: "action", headerNameKey: "policiesList.action" },
    ];

    useEffect(() => {
        const fetch = async () => {
            if (fetchData) {
                const queryParams = new URLSearchParams();
                queryParams.append('sortFieldName', sortFieldName);
                queryParams.append('sortType', sortType);
                queryParams.append('pageSize', pageSize);

                //reset page number to 0 if filter applied or page number is out of bounds
                const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, resetPageNo);
                queryParams.append('pageNo', effectivePageNo);
                setResetPageNo(false);

                queryParams.append('policyType', selectedActiveTab);
                if (filterAttributes.policyId) queryParams.append('policyId', filterAttributes.policyId);
                if (filterAttributes.policyName) queryParams.append('policyName', filterAttributes.policyName);
                if (filterAttributes.policyDescription) queryParams.append('policyDescription', filterAttributes.policyDescription);
                if (filterAttributes.policyGroupName) queryParams.append('policyGroupName', filterAttributes.policyGroupName);

                // Check filterAttributes.status
                if (filterAttributes.status !== null) {
                    if (filterAttributes.status === 'active') queryParams.append('isActive', true);
                    else if (filterAttributes.status === 'deactivated') queryParams.append('isActive', false);
                }

                const url = `${getPolicyManagerUrl('/policies/search/v2', process.env.NODE_ENV)}?${queryParams.toString()}`;
                try {
                    setTableDataLoaded(false);
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
                                if (selectedActiveTab === "auth") {
                                    setAuthPoliciesList(resData);
                                } else if (selectedActiveTab === "dataShare") {
                                    setDataSharePoliciesList(resData);
                                }
                            }
                            else {
                                if (selectedActiveTab === "auth") {
                                    setAuthPoliciesList([]);
                                } else if (selectedActiveTab === "dataShare") {
                                    setDataSharePoliciesList([]);
                                }
                            }
                        } else {
                            handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                        }
                    } else {
                        if (selectedActiveTab === "auth") {
                            setErrorMsg(t('policiesList.errorInAuthPolicies'));
                        } else if (selectedActiveTab === "dataShare") {
                            setErrorMsg(t('policiesList.errorInDataSharePolicies'));
                        }
                    }
                    setTableDataLoaded(true);
                    setFetchData(false);
                } catch (err) {
                    setFetchData(false);
                    setTableDataLoaded(true);
                    console.error('Error fetching data:', err);
                    setErrorMsg(err);
                }
            }
        }
        fetch();
    }, [fetchData]);

    const newActiveTab = localStorage.getItem('activeTab');
    if (selectedActiveTab !== newActiveTab) {
        setSelectedActiveTab(newActiveTab);
        setPageNo(0);
        setPageSize(8);
        setSortFieldName("createdDateTime");
        setSortType("desc");
        setActiveAscIcon("createdDateTime");
        setActiveDescIcon("");
        setSelectedRecordsPerPage(8);
        setFilterAttributes({
            policyId: null,
            policyName: null,
            policyDescription: null,
            policyGroupName: null,
            status: null,
        });
        setExpandFilter(false);
        setApplyFilter(false);
        setFetchData(true);
    }

    const onApplyFilter = (updatedfilters) => {
        onClickApplyFilter(updatedfilters, setApplyFilter, setResetPageNo, setFetchData, setFilterAttributes);
    };

    const getPaginationValues = (recordsPerPage, pageIndex) => {
        setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setFetchData);
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const createAuthPolicy = () => {
        if (selectedActiveTab === "auth") {
            localStorage.setItem('policyType', 'Auth');
            navigate('/partnermanagement/admin/policy-manager/create-auth-policy');
        }
    };

    const createDataSharePolicy = () => {
        if (selectedActiveTab === "dataShare") {
            localStorage.setItem('policyType', 'DataShare');
            navigate('/partnermanagement/admin/policy-manager/create-data-share-policy');
        }
    };

    const viewPolicy = (selectedPolicy) => {
        let requiredData = {};
        if (selectedActiveTab === "auth") {
            requiredData = {
                policyId: selectedPolicy.policyId,
                header: 'viewAuthPoliciesList.viewAuthPolicy',
                subTitle: 'viewAuthPoliciesList.listOfAuthenticationPolicies',
                backLink: '/partnermanagement/admin/policy-manager/auth-policies-list'
            }
            localStorage.setItem('selectedPolicyData', JSON.stringify(requiredData));
            navigate('/partnermanagement/admin/policy-manager/view-auth-policy')
        }
        if (selectedActiveTab === "dataShare") {
            requiredData = {
                policyId: selectedPolicy.policyId,
                header: 'viewDataSharePoliciesList.viewDataSharePolicy',
                subTitle: 'viewDataSharePoliciesList.listOfDataSharePolicies',
                backLink: '/partnermanagement/admin/policy-manager/data-share-policies-list'
            }
            localStorage.setItem('selectedPolicyData', JSON.stringify(requiredData));
            navigate('/partnermanagement/admin/policy-manager/view-data-share-policy');
        }
    };

    const showDeactivatePolicy = (policy) => {

    };

    const sortAscOrder = (header) => {
        if (order !== 'ASC' || activeAscIcon !== header) {
            setFetchData(true);
            setSortFieldName((header === 'status') ? 'isActive' : header);
            setSortType((header === 'status') ? 'asc' : 'desc');
            setOrder("ASC");
            setActiveDescIcon("");
            setActiveAscIcon(header);
        }
    };
    const sortDescOrder = (header) => {
        if (order !== 'DESC' || activeDescIcon !== header) {
            setFetchData(true);
            setSortFieldName((header === 'status') ? 'isActive' : header);
            setSortType((header === 'status') ? 'desc' : 'asc');
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
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between mb-5">
                            <Title title='policyGroupList.policies' backLink='/partnermanagement' ></Title>
                            {selectedActiveTab === "auth" && (
                                <>
                                    {applyFilter || authPoliciesList.length > 0 ?
                                        <button onClick={createAuthPolicy} id='create_auth_policy_btn' type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                                            {t('policiesList.createAuthPolicy')}
                                        </button>
                                        : null
                                    }
                                </>
                            )}
                            {selectedActiveTab === "dataShare" && (
                                <>
                                    {applyFilter || dataSharePoliciesList.length > 0 ?
                                        <button onClick={createDataSharePolicy} id='create_data_share_policy_btn' type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                                            {t('policiesList.createDataSharePolicy')}
                                        </button>
                                        : null
                                    }
                                </>
                            )}
                        </div>
                        <PoliciesTab></PoliciesTab>
                        {!applyFilter && ((selectedActiveTab === "auth" && authPoliciesList.length === 0) || (selectedActiveTab === "dataShare" && dataSharePoliciesList.length === 0)) ?
                            (
                                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                    {selectedActiveTab === "Auth" && (
                                        <EmptyList
                                            tableHeaders={tableHeaders}
                                            showCustomButton={true}
                                            customButtonName='policiesList.createAuthPolicy'
                                            onClickButton={createAuthPolicy}
                                        />
                                    )}
                                    {selectedActiveTab === "DataShare" && (
                                        <EmptyList
                                            tableHeaders={tableHeaders}
                                            showCustomButton={true}
                                            customButtonName='policiesList.createDataSharePolicy'
                                            onClickButton={createDataSharePolicy}
                                        />
                                    )}
                                </div>
                            ) : (
                                <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                                    <FilterButtons
                                        listTitle={selectedActiveTab === "auth" ? 'policiesList.listOfAuthPolicies' : 'policiesList.listOfDataSharePolicies'}
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
                                    {tableDataLoaded && applyFilter && ((selectedActiveTab === "auth" && authPoliciesList.length === 0) || (selectedActiveTab === "dataShare" && dataSharePoliciesList.length === 0)) ?
                                        <EmptyList
                                            tableHeaders={tableHeaders}
                                        />
                                        : (
                                            <>
                                                <div className="mx-[2%] overflow-x-scroll">
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
                                                            {(selectedActiveTab === "auth" ? authPoliciesList : dataSharePoliciesList).map((policy, index) => {
                                                                return (
                                                                    <tr id={"policies_list_item" + (index + 1)} key={index}
                                                                        className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words ${policy.isActive === false ? "text-[#969696]" : "text-[#191919]"}`}>
                                                                        <td onClick={() => policy.isActive && viewPolicy(policy)} className="px-2 break-all">{policy.policyId}</td>
                                                                        <td onClick={() => policy.isActive && viewPolicy(policy)} className="px-2 break-all">{policy.policyName}</td>
                                                                        <td onClick={() => policy.isActive && viewPolicy(policy)} className="px-2 break-all">{policy.policyDescription}</td>
                                                                        <td onClick={() => policy.isActive && viewPolicy(policy)} className="px-2 break-all">{policy.policyGroupName}</td>
                                                                        <td onClick={() => policy.isActive && viewPolicy(policy)} className="px-2 break-all">{formatDate(policy.createdDateTime, "date", false)}</td>
                                                                        <td onClick={() => policy.isActive && viewPolicy(policy)}>
                                                                            <div className={`${policy.isActive ? 'bg-[#D1FADF] text-[#155E3E]' : 'bg-[#EAECF0] text-[#525252]'} flex w-fit py-1.5 px-2 mx-2 my-3 text-xs font-semibold rounded-md`}>
                                                                                {policy.isActive ? t('statusCodes.activated') : t('statusCodes.deactivated')}
                                                                            </div>
                                                                        </td>
                                                                        <td className="text-center break-all">
                                                                            <div ref={(el) => (submenuRef.current[index] = el)}>
                                                                                <p id={"policies_list_view" + (index + 1)} onClick={() => setActionId(index === actionId ? null : index)} className={`font-semibold mb-0.5 cursor-pointer text-center`}
                                                                                    tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setActionId(index === actionId ? null : index))}>
                                                                                    ...
                                                                                </p>
                                                                                {actionId === index && (
                                                                                    <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                                                                        <div className="flex justify-between hover:bg-gray-100" onClick={() => viewPolicy(policy)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => viewPolicy(policy))}>
                                                                                            <p id="policy_details_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                                                            <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`}></img>
                                                                                        </div>
                                                                                        <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                        <div className={`flex justify-between hover:bg-gray-100 ${policy.isActive === true ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => showDeactivatePolicy(policy)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showDeactivatePolicy(policy))}>
                                                                                            <p id="policy_deactivate_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${policy.isActive === true ? "text-crimson-red" : "text-[#A5A5A5]"}`}>{t("partnerList.deActivate")}</p>
                                                                                            <img src={deactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`}></img>
                                                                                        </div>
                                                                                    </div>
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