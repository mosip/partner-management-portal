import React, { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import {
    isLangRTL, formatDate, handleMouseClickForDropdown, onPressEnterKey, getPolicyManagerUrl, 
    handleServiceErrors, resetPageNumber, applyFilter, setPageNumberAndPageSize, onResetFilter
} from '../../../utils/AppUtils';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from "../../common/LoadingIcon";
import rectangleGrid from '../../../svg/rectangle_grid.svg';
import Title from '../../common/Title.js';
import PoliciesTab from './PoliciesTab.js';
import FilterButtons from '../../common/FilterButtons.js';
import SortingIcon from '../../common/SortingIcon.js';
import viewIcon from "../../../svg/view_icon.svg";
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import Pagination from '../../common/Pagination.js';
import { HttpService } from '../../../services/HttpService.js';
import PoliciesListFilter from './PoliciesListFilter.js';

function PoliciesList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [activePolicyGroup, setActivePolicyGroup] = useState(false);
    const [activeAuthPolicy, setActiveAuthPolicy] = useState(false);
    const [activeDataSharePolicy, setActiveDataSharePolicy] = useState(false);
    const [policiesList, setPoliciesList] = useState([]);
    const [filter, setFilter] = useState(false);
    const [order, setOrder] = useState("ASC");
    const [activeSortAsc, setActiveSortAsc] = useState("createdDateTime");
    const [activeSortDesc, setActiveSortDesc] = useState("");
    const [viewPolicyId, setViewPolicyId] = useState(-1);
    const [firstIndex, setFirstIndex] = useState(0);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
    const [sortFieldName, setSortFieldName] = useState("createdDateTime");
    const [sortType, setSortType] = useState("desc");
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState(8);
    const [triggerServerMethod, setTriggerServerMethod] = useState(false);
    const [tableDataLoaded, setTableDataLoaded] = useState(true);
    const [totalRecords, setTotalRecords] = useState(0);
    const [resetPageNo, setResetPageNo] = useState(false);
    const [isFilterApplied, setIsFilterApplied ] = useState(false);
    const [filters, setFilters] = useState({
        policyId: null,
        policyName: null,
        policyDescription: null,
        policyGroupName: null,
        status: null,
    });
    const [policyTypeData, setPolicyTypeData] = useState({});  
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setViewPolicyId(-1));
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
        // get the policyType from localStorage
        let data = localStorage.getItem('policyTypeData');
        if (!data) {
            setPoliciesList([]);
            return;
        }
        data = JSON.parse(data);
        setPolicyTypeData(data);
        if (data.policyType === 'Auth') {
            setActiveAuthPolicy(true);
        }
        if (data.policyType === 'DataShare') {
            setActiveDataSharePolicy(true);
        }

        const fetchData = async () => {
            const queryParams = new URLSearchParams();
            queryParams.append('sortFieldName', sortFieldName);
            queryParams.append('sortType', sortType);
            queryParams.append('pageSize', pageSize);

            //reset page number to 0 if filter applied or page number is out of bounds
            const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, resetPageNo);    
            queryParams.append('pageNo', effectivePageNo); 
            setResetPageNo(false);

            queryParams.append('policyType', data.policyType);
            if (filters.policyId) queryParams.append('policyId', filters.policyId);
            if (filters.policyName) queryParams.append('policyName', filters.policyName);
            if (filters.policyDescription) queryParams.append('policyDescription', filters.policyDescription);
            if (filters.policyGroupName) queryParams.append('policyGroupName', filters.policyGroupName);

            // Check filters.status
            if (filters.status !== null) {
                if (filters.status === 'active') queryParams.append('isActive', true);
                else if (filters.status === 'deactivated') queryParams.append('isActive', false);
            }

            const url = `${getPolicyManagerUrl('/policies/search/v2', process.env.NODE_ENV)}?${queryParams.toString()}`;
            try {
                triggerServerMethod ? setTableDataLoaded(false) : setDataLoaded(false);
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
                        if (resData !== null) setPoliciesList(resData);
                        else setPoliciesList([]);
                    } else {
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(data.errorMessage);
                }
                triggerServerMethod ? setTableDataLoaded(true) : setDataLoaded(true);
                setTriggerServerMethod(false);
            } catch (err) {
                triggerServerMethod ? setTableDataLoaded(true) : setDataLoaded(true);
                console.error('Error fetching data:', err);
                setErrorMsg(err);
            }
        }
        fetchData();
    }, [sortFieldName, sortType, pageNo, pageSize, filters, activeDataSharePolicy]);

    const onApplyFilter = (filters) => {
        applyFilter(filters, setIsFilterApplied, setResetPageNo, setTriggerServerMethod, setFilters);
    };

    const getPaginationValues = (recordsPerPage, pageIndex) => {
        setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setTriggerServerMethod);
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const createPolicy = () => {
        if (policyTypeData.policyType === "Auth") {
            localStorage.setItem('policyType', 'Auth');
            navigate('/partnermanagement/admin/policy-manager/create-auth-policy');
        }
        if (policyTypeData.policyType === "DataShare") {
            localStorage.setItem('policyType', 'DataShare');
            navigate('/partnermanagement/admin/policy-manager/create-data-share-policy');
        }
    };

    const viewPolicy = (selectedPolicy) => {
        let requiredData = {}; 
        if (policyTypeData.policyType === "Auth") {
            requiredData = {
                policyId: selectedPolicy.policyId,
                header: 'viewAuthPoliciesList.viewAuthPolicy',
                subTitle: 'viewAuthPoliciesList.listOfAuthenticationPolicies',
                backLink: '/partnermanagement/admin/policy-manager/auth-policies-list'
            }
            localStorage.setItem('selectedPolicyData', JSON.stringify(requiredData));
            navigate('/partnermanagement/admin/policy-manager/view-auth-policy')
        }
        if (policyTypeData.policyType === "DataShare") {
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
        if (order !== 'ASC' || activeSortAsc !== header) {
            setTriggerServerMethod(true);
            setSortFieldName((header === 'status') ? 'isActive' : header);
            setSortType((header === 'status') ? 'asc' : 'desc');
            setOrder("ASC");
            setActiveSortDesc("");
            setActiveSortAsc(header);
        }
    };
    const sortDescOrder = (header) => {
        if (order !== 'DESC' || activeSortDesc !== header) {
            setTriggerServerMethod(true);
            setSortFieldName((header === 'status') ? 'isActive' : header);
            setSortType((header === 'status') ? 'desc' : 'asc');
            setOrder("DESC");
            setActiveSortDesc(header);
            setActiveSortAsc("");
        }
    };

    const styles = {
        loadingDiv: "!py-[20%]"
    }

    const renderNoData = () => (
        <>
            <hr className="h-0.5 bg-gray-200 border-0" />
            <div className="flex justify-between mt-5">
                <div className="flex w-full justify-between font-[400] text-[14px] m-auto">
                    <h6 className="px-2 mx-2">{t('policiesList.policyId')}</h6>
                    <h6 className="px-2 mx-2">{t('policiesList.policyName')}</h6>
                    <h6 className="px-2 mx-2">{t('policiesList.policyDescription')}</h6>
                    <h6 className="px-2 mx-2">{t('policiesList.policyGroup')}</h6>
                    <h6 className="px-2 mx-2">{t('policiesList.createdDate')}</h6>
                    <h6 className="px-2 mx-2">{t('policiesList.status')}</h6>
                    <h6 className="px-2 mx-2">{t('policiesList.action')}</h6>
                </div>
            </div>
            
            <hr className="h-px mx-3 my-2 bg-gray-200 border-0" />
            
            <div className="flex items-center justify-center p-24">
                <div className="flex flex-col items-center">
                    {/* Ensure rectangleGrid has a valid import path and alt text for accessibility */}
                    <img src={rectangleGrid} alt="No data available icon" />
                    {isFilterApplied ? 
                        <p className="text-[#A1A1A1] mt-3">{t("partnerList.noData")}</p>
                        : 
                        <button id='create_policy_group_btn' type="button" onClick={createPolicy}
                            className={`text-white font-semibold mt-8 w-[75%] bg-tory-blue rounded-md text-sm mx-8 py-3`}>
                            {policyTypeData.createPolicyButtonName}
                        </button>
                    }
                </div>
            </div>
        </>
    );

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
            { !dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            { dataLoaded && (
                <>
                    { errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}/>
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between mb-5">
                            <Title title='policyGroupList.policies' backLink='/partnermanagement' ></Title>
                            { isFilterApplied || policiesList.length > 0  ?
                                <button onClick={createPolicy} id='create_policy_group_btn' type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                                    {policyTypeData.createPolicyButtonName}
                                </button>
                                : null
                            }
                        </div>
                        <PoliciesTab
                            activePolicyGroup={activePolicyGroup}
                            setActivePolicyGroup={setActivePolicyGroup}
                            activeAuthPolicy={activeAuthPolicy}
                            setActiveAuthPolicy={setActiveAuthPolicy}
                            activeDataSharePolicy={activeDataSharePolicy}
                            setActiveDataSharePolicy={setActiveDataSharePolicy}>
                        </PoliciesTab>
                        { !isFilterApplied && policiesList.length === 0 ? (
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                {renderNoData()}
                            </div>
                            ) : (
                                <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                                    <FilterButtons
                                        listTitle={policyTypeData.subTitle}
                                        dataListLength={totalRecords}
                                        filter={filter}
                                        onResetFilter={onResetFilter}
                                        setFilter={setFilter}
                                    />
                                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                    {filter && (
                                        <PoliciesListFilter onApplyFilter={onApplyFilter}/>
                                    )}
                                    {!tableDataLoaded && <LoadingIcon styleSet={styles}></LoadingIcon>}
                                    {tableDataLoaded && isFilterApplied && policiesList.length === 0 ? renderNoData() : (
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
                                                                                activeSortDesc={activeSortDesc}
                                                                                activeSortAsc={activeSortAsc}
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
                                                                <tr id={"policy_group_list_item" + (index + 1)} key={index}
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
                                                                            <p id={"policy_group_list_view" + (index + 1)} onClick={() => setViewPolicyId(index === viewPolicyId ? null : index)} className={`font-semibold mb-0.5 cursor-pointer text-center`}
                                                                                tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setViewPolicyId(index === viewPolicyId ? null : index))}>
                                                                                ...
                                                                            </p>
                                                                            {viewPolicyId === index && (
                                                                                <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                                                                    <div className="flex justify-between hover:bg-gray-100" onClick={() => viewPolicy(policy)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => viewPolicy(policy))}>
                                                                                        <p id="partner_details_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                                                        <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`}></img>
                                                                                    </div>
                                                                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                    <div className={`flex justify-between hover:bg-gray-100 ${policy.isActive === true ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => showDeactivatePolicy(policy)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showDeactivatePolicy(policy))}>
                                                                                        <p id="partner_deactive_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${policy.isActive === true ? "text-crimson-red" : "text-[#A5A5A5]"}`}>{t("partnerList.deActivate")}</p>
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