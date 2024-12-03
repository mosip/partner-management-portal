import React, { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import {
    isLangRTL, formatDate, handleMouseClickForDropdown, onPressEnterKey, createRequest, getPolicyManagerUrl,
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
import PolicyGroupListFilter from './PolicyGroupListFilter.js';
import EmptyList from '../../common/EmptyList.js';
import DeactivatePolicyPopup from './DeactivatePolicyPopup.js';

function PolicyGroupList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(false);
    const [policyGroupList, setPolicyGroupList] = useState([]);
    const [expandFilter, setExpandFilter] = useState(false);
    const [order, setOrder] = useState("DESC");
    const [activeAscIcon, setActiveAscIcon] = useState("");
    const [activeDescIcon, setActiveDescIcon] = useState("crDtimes");
    const [actionId, setActionId] = useState(-1);
    const [firstIndex, setFirstIndex] = useState(0);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
    const [sortFieldName, setSortFieldName] = useState("crDtimes");
    const [sortType, setSortType] = useState("desc");
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
    const [fetchData, setFetchData] = useState(false);
    const [tableDataLoaded, setTableDataLoaded] = useState(true);
    const [totalRecords, setTotalRecords] = useState(0);
    const [resetPageNo, setResetPageNo] = useState(false);
    const [applyFilter, setApplyFilter] = useState(false);
    const [filterAttributes, setFilterAttributes] = useState({
        id: null,
        name: null,
        desc: null,
        status: null,
    });
    const [showDeactivatePolicyGroupPopup, setShowDeactivatePolicyGroupPopup] = useState(false);
    const [deactivatePolicyGroupRequest, setDeactivatePolicyGroupRequest] = useState({});
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
    }, [submenuRef]);

    const tableHeaders = [
        { id: "id", headerNameKey: "policyGroupList.policyGroupId" },
        { id: "name", headerNameKey: "policyGroupList.policyGroupName" },
        { id: "desc", headerNameKey: "policyGroupList.policyGroupDescription" },
        { id: "crDtimes", headerNameKey: "policyGroupList.createdDate" },
        { id: "status", headerNameKey: "policyGroupList.status" },
        { id: "action", headerNameKey: "policyGroupList.action" },
    ];

    useEffect(() => {
        localStorage.setItem('activeTab', 'policyGroup');
        const fetch = async () => {
            //reset page number to 0 if filter applied or page number is out of bounds
            const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, resetPageNo);
            setResetPageNo(false);

            // filter
            let filterRequest = getFiltersRequest();

            const request = createRequest({
                filters: filterRequest,
                sort: [{ sortField: sortFieldName, sortType: sortType }],
                pagination: {
                    pageStart: effectivePageNo,
                    pageFetch: pageSize
                }
            });
            try {
                fetchData ? setTableDataLoaded(false) : setDataLoaded(false);
                const response = await HttpService({
                    url: getPolicyManagerUrl('/policies/group/search', process.env.NODE_ENV),
                    method: 'post',
                    baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL,
                    data: request
                });
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response.data;
                        setTotalRecords(responseData.response.totalRecord);
                        if (resData !== null) setPolicyGroupList(resData);
                        else setPolicyGroupList([]);
                    } else {
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('policyGroupList.errorInPolicyGroupList'));
                }
                fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
                setFetchData(false);
            } catch (err) {
                fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
                setFetchData(false);
                console.error('Error fetching data:', err);
                setErrorMsg(err);
            }
        }
        fetch();
    }, [sortFieldName, sortType, pageNo, pageSize, filterAttributes]);

    const getFiltersRequest = () => {
        let filtersArr = [];
        if (filterAttributes.id) {
            let addFilter = {
                value: filterAttributes.id,
                columnName: "id",
                type: "contains"
            }
            filtersArr.push(addFilter);
        }
        if (filterAttributes.name) {
            let addFilter = {
                value: filterAttributes.name,
                columnName: "name",
                type: "contains"
            }
            filtersArr.push(addFilter);
        }
        if (filterAttributes.desc) {
            let addFilter = {
                value: filterAttributes.desc,
                columnName: "desc",
                type: "contains"
            }
            filtersArr.push(addFilter);
        }
        if (filterAttributes.status) {
            let addFilter = {};
            if (filterAttributes.status === "active") {
                addFilter = {
                    value: "true",
                    columnName: "isActive",
                    type: "equals"
                }
            } else if (filterAttributes.status === "deactivated") {
                addFilter = {
                    value: "false",
                    columnName: "isActive",
                    type: "equals"
                }
            }
            filtersArr.push(addFilter);
        }
        return filtersArr
    };

    const onApplyFilter = (filters) => {
        onClickApplyFilter(filters, setApplyFilter, setResetPageNo, setFetchData, setFilterAttributes);
    };

    const getPaginationValues = (recordsPerPage, pageIndex) => {
        setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setFetchData);
    }

    const createPolicyGroup = () => {
        navigate('/partnermanagement/admin/policy-manager/create-policy-group');
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const viewPolicyGroupDetails = (selectedPolicyGroup) => {
        localStorage.setItem('selectedPolicyGroupAttributes', JSON.stringify(selectedPolicyGroup));
        navigate('/partnermanagement/admin/policy-manager/view-policy-group-details');
    };

    const sortAscOrder = (header) => {
        if (order !== 'ASC' || activeAscIcon !== header) {
            setFetchData(true);
            setSortFieldName((header === 'status') ? 'isActive' : header);
            setSortType((header === 'status') ? 'desc' : 'asc');
            setOrder("ASC");
            setActiveDescIcon("");
            setActiveAscIcon(header);
        }
    };
    const sortDescOrder = (header) => {
        if (order !== 'DESC' || activeDescIcon !== header) {
            setFetchData(true);
            setSortFieldName((header === 'status') ? 'isActive' : header);
            setSortType((header === 'status') ? 'asc' : 'desc');
            setOrder("DESC");
            setActiveDescIcon(header);
            setActiveAscIcon("");
        }
    };

    const closePopup = () => {
        setShowDeactivatePolicyGroupPopup(false);
        setActionId(-1);
        document.body.style.overflow = 'auto';
    };

    const showDeactivatePolicyGroup = (policyGroup) => {
        if (policyGroup.isActive) {
            const request = createRequest({
                name: policyGroup.name,
                desc: policyGroup.desc,
                isActive: false
            });
            setDeactivatePolicyGroupRequest(request);
            setShowDeactivatePolicyGroupPopup(true);
            document.body.style.overflow = "hidden";
        }
    };

    const onClickConfirmDeactivate = (deactivationResponse, selectedPolicyGroup) => {
        if (deactivationResponse && !deactivationResponse.isActive) {
            setActionId(-1);
            setShowDeactivatePolicyGroupPopup(false);
            // Update the specific row in the state with the new status
            setPolicyGroupList((prevList) =>
                prevList.map(policyGroup =>
                    policyGroup.id === selectedPolicyGroup.id ? { ...policyGroup, isActive: false } : policyGroup
                )
            );
        }
    };

    const styles = {
        loadingDiv: "!py-[20%]"
    };

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
                        <div className="flex justify-between mb-5 max-470:flex-col">
                            <Title title='policyGroupList.policies' backLink='/partnermanagement' ></Title>
                            {applyFilter || policyGroupList.length > 0 ?
                                <button onClick={createPolicyGroup} id='create_policy_group_btn' type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md max-330:h-fit">
                                    {t('policyGroupList.createPolicyGroup')}
                                </button>
                                : null
                            }
                        </div>
                        <PoliciesTab></PoliciesTab>
                        {!applyFilter && policyGroupList.length === 0 ? (
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <EmptyList
                                    tableHeaders={tableHeaders}
                                    showCustomButton={!applyFilter}
                                    customButtonName='policyGroupList.createPolicyGroup'
                                    buttonId= 'create_policy_group'
                                    onClickButton={createPolicyGroup}
                                />
                            </div>
                        ) : (
                            <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                                <FilterButtons
                                    listTitle="policyGroupList.listOfPolicyGroups"
                                    dataListLength={totalRecords}
                                    filter={expandFilter}
                                    onResetFilter={onResetFilter}
                                    setFilter={setExpandFilter}
                                />
                                <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                {expandFilter && (
                                    <PolicyGroupListFilter onApplyFilter={onApplyFilter} />
                                )}
                                {!tableDataLoaded && <LoadingIcon styleSet={styles}></LoadingIcon>}
                                {tableDataLoaded && applyFilter && policyGroupList.length === 0 ?
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
                                                        {policyGroupList.map((policyGroup, index) => {
                                                            return (
                                                                <tr id={"policy_group_list_item" + (index + 1)} key={index}
                                                                    className={`border-t border-[#E5EBFA] ${policyGroup.isActive ? 'cursor-pointer' : 'cursor-default'} text-[0.8rem] text-[#191919] font-semibold break-words ${policyGroup.isActive === false ? "text-[#969696]" : "text-[#191919]"}`}>
                                                                    <td onClick={() => policyGroup.isActive && viewPolicyGroupDetails(policyGroup)} className={`${isLoginLanguageRTL ? 'pl-[4.1rem] pr-[0.8rem]' : 'pr-[6.8rem] pl-[0.8rem]'} break-all`}>{policyGroup.id}</td>
                                                                    <td onClick={() => policyGroup.isActive && viewPolicyGroupDetails(policyGroup)} className={`${isLoginLanguageRTL ? 'pl-[5rem] pr-[0.8rem]' : 'pr-[5.4rem] pl-[0.8rem]'} break-all`}>{policyGroup.name}</td>
                                                                    <td onClick={() => policyGroup.isActive && viewPolicyGroupDetails(policyGroup)} className={`${isLoginLanguageRTL ? 'pl-[3.8rem] pr-[0.8rem]' : 'pr-[3.2rem] pl-[0.8rem]'} break-all`}>{policyGroup.desc}</td>
                                                                    <td onClick={() => policyGroup.isActive && viewPolicyGroupDetails(policyGroup)} className="px-2 break-all">{formatDate(policyGroup.crDtimes, "date", true)}</td>
                                                                    <td onClick={() => policyGroup.isActive && viewPolicyGroupDetails(policyGroup)}>
                                                                        <div className={`${policyGroup.isActive ? 'bg-[#D1FADF] text-[#155E3E]' : 'bg-[#EAECF0] text-[#525252]'} flex w-fit py-1.5 px-3 mx-2 my-3 text-xs font-semibold rounded-md`}>
                                                                            {policyGroup.isActive ? t('statusCodes.activated') : t('statusCodes.deactivated')}
                                                                        </div>
                                                                    </td>
                                                                    <td className="text-center break-all">
                                                                        <div ref={(el) => (submenuRef.current[index] = el)}>
                                                                            <p id={"policy_group_list_view" + (index + 1)} onClick={() => setActionId(index === actionId ? null : index)} className={`font-semibold mb-0.5 text-[#191919] cursor-pointer text-center`}
                                                                                tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setActionId(index === actionId ? null : index))}>
                                                                                ...
                                                                            </p>
                                                                            {actionId === index && (
                                                                                <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                                                                    <div className="flex justify-between hover:bg-gray-100" onClick={() => viewPolicyGroupDetails(policyGroup)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => viewPolicyGroupDetails(policyGroup))}>
                                                                                        <p id="policy_group_details_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                                                        <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`}></img>
                                                                                    </div>
                                                                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                    <div className={`flex justify-between hover:bg-gray-100 ${policyGroup.isActive === true ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => showDeactivatePolicyGroup(policyGroup)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showDeactivatePolicyGroup(policyGroup))}>
                                                                                        <p id="policy_group_deactivate_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${policyGroup.isActive === true ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t("partnerList.deActivate")}</p>
                                                                                        <img src={deactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`}></img>
                                                                                    </div>
                                                                                    {showDeactivatePolicyGroupPopup && (
                                                                                        <DeactivatePolicyPopup
                                                                                            header={'deactivatePolicyGroup.headerMsg'}
                                                                                            description={'deactivatePolicyGroup.description'}
                                                                                            popupData={{...policyGroup, isDeactivatePolicyGroup: true}}
                                                                                            request={deactivatePolicyGroupRequest}
                                                                                            headerKeyName={policyGroup.name}
                                                                                            closePopUp={closePopup}
                                                                                            onClickConfirm={(deactivationResponse) => onClickConfirmDeactivate(deactivationResponse, policyGroup)}
                                                                                        />
                                                                                    )}
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
export default PolicyGroupList;