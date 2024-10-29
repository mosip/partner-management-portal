import React, { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import {
    isLangRTL, formatDate, handleMouseClickForDropdown, onPressEnterKey, createRequest, getPolicyManagerUrl, handleServiceErrors
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

function PolicyGroupList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(false);
    const [activePolicyGroup, setActivePolicyGroup] = useState(true);
    const [activeAuthPolicy, setActiveAuthPolicy] = useState(false);
    const [activeDataSharePolicy, setActiveDataSharePolicy] = useState(false);
    const [policyGroupList, setPolicyGroupList] = useState([]);
    const [filter, setFilter] = useState(false);
    const [order, setOrder] = useState("ASC");
    const [activeSortAsc, setActiveSortAsc] = useState("crDtimes");
    const [activeSortDesc, setActiveSortDesc] = useState("");
    const [viewPolicyGroupId, setViewPolicyGroupId] = useState(-1);
    const [firstIndex, setFirstIndex] = useState(0);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
    const [sortFieldName, setSortFieldName] = useState("crDtimes");
    const [sortType, setSortType] = useState("desc");
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState(8);
    const [triggerServerMethod, setTriggerServerMethod] = useState(false);
    const [tableDataLoaded, setTableDataLoaded] = useState(true);
    const [totalRecords, setTotalRecords] = useState(0);
    const [resetPageNo, setResetPageNo] = useState(false);
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setViewPolicyGroupId(-1));
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
        const fetchData = async () => {
            //reset page number to 0 if filter applied or page number is out of bounds
            const totalNumberOfPages = Math.ceil(totalRecords / pageSize);
            const effectivePageNo = pageNo > totalNumberOfPages || resetPageNo ? 0 : pageNo;    
            setResetPageNo(false);

            const request = createRequest ({
                filters: [],
                sort: [{sortField : sortFieldName, sortType: sortType}],
                pagination: {
                    pageStart: effectivePageNo,
                    pageFetch: pageSize
                }
            });
            try {
                triggerServerMethod ? setTableDataLoaded(false) : setDataLoaded(false);
                const response = await HttpService.post(getPolicyManagerUrl(`/policies/group/search`, process.env.NODE_ENV), request);
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response.data;
                        setTotalRecords(responseData.response.totalRecord);
                        setPolicyGroupList(resData);
                    } else {
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('policyGroupList.errorInPolicyGroupList'));
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
    }, [sortFieldName, sortType, pageNo, pageSize]);

    const getPaginationValues = (recordsPerPage, pageIndex) => {
        // console.log(recordsPerPage, pageIndex);
        if (pageNo !== pageIndex || pageSize !== recordsPerPage) {
            setPageNo(pageIndex);
            setPageSize(recordsPerPage);
            setTriggerServerMethod(true);
        }
    }

    const createPolicyGroup = () => {
        navigate('/partnermanagement/admin/policy-manager/create-policy-group');
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const onResetFilter = () => {
        window.location.reload();
    };

    const viewPolicyGroupDetails = (policyGroup) => {

    };

    const showDeactivatePolicyGroup = (policyGroup) => {

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
                            {policyGroupList.length > 0 ?
                                <button onClick={createPolicyGroup} id='create_policy_group_btn' type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                                    {t('policyGroupList.createPolicyGroup')}
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
                        { policyGroupList.length === 0 
                            ? (
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                { activePolicyGroup && (
                                    <div className="flex justify-between py-2 pt-4 text-sm font-semibold text-[#6F6E6E]">
                                        <div className={`flex w-full justify-between`}>
                                            <h6 className="px-2 mx-2">{t('policyGroupList.policyGroupId')}</h6>
                                            <h6 className="px-2 mx-2">{t('policyGroupList.policyGroupName')}</h6>
                                            <h6 className="px-2 mx-2">{t('policyGroupList.policyGroupDescription')}</h6>
                                            <h6 className="px-2 mx-2">{t('policyGroupList.createdDate')}</h6>
                                            <h6 className="px-2 mx-2">{t('policyGroupList.status')}</h6>
                                            <h6 className="px-2 mx-2">{t('policyGroupList.action')}</h6>
                                        </div>
                                    </div>
                                )}
                                <hr className="h-px mx-3 bg-gray-200 border-0" />
                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center">
                                        <img src={rectangleGrid} alt="" />
                                        {activePolicyGroup &&
                                            (<button id='create_policy_group_btn' type="button" onClick={createPolicyGroup}
                                                className={`text-white font-semibold mt-8 bg-tory-blue rounded-md text-sm mx-8 py-3`}>
                                                {t('policyGroupList.createPolicyGroup')}
                                            </button>)
                                        }
                                    </div>
                                </div>
                            </div>
                            ) : (
                                <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                                    <FilterButtons
                                        listTitle="policyGroupList.listOfPolicyGroups"
                                        dataListLength={totalRecords}
                                        filter={filter}
                                        onResetFilter={onResetFilter}
                                        setFilter={setFilter}
                                    />
                                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                        <>
                                            <div className="mx-[2%] overflow-x-scroll">
                                                {!tableDataLoaded && 
                                                    <LoadingIcon styleSet={styles}></LoadingIcon>
                                                }
                                                {tableDataLoaded && policyGroupList.length !== 0 && (
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
                                                            {policyGroupList.map((policyGroup, index) => {
                                                                return (
                                                                    <tr id={"policy_group_list_item" + (index + 1)} key={index}
                                                                        className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words ${policyGroup.isActive === false ? "text-[#969696]" : "text-[#191919]"}`}>
                                                                        <td onClick={() => policyGroup.isActive && viewPolicyGroupDetails(policyGroup)} className="px-2 break-all">{policyGroup.id}</td>
                                                                        <td onClick={() => policyGroup.isActive && viewPolicyGroupDetails(policyGroup)} className="px-2 break-all">{policyGroup.name}</td>
                                                                        <td onClick={() => policyGroup.isActive && viewPolicyGroupDetails(policyGroup)} className="px-2 break-all">{policyGroup.desc}</td>
                                                                        <td onClick={() => policyGroup.isActive && viewPolicyGroupDetails(policyGroup)} className="px-2 break-all">{formatDate(policyGroup.crDtimes, "dateTime", false)}</td>
                                                                        <td onClick={() => policyGroup.isActive && viewPolicyGroupDetails(policyGroup)}>
                                                                            <div className={`${policyGroup.isActive ? 'bg-[#D1FADF] text-[#155E3E]' : 'bg-[#EAECF0] text-[#525252]'} flex w-fit py-1.5 px-2 mx-2 my-3 text-xs font-semibold rounded-md`}>
                                                                                {policyGroup.isActive ? t('statusCodes.activated') : t('statusCodes.deactivated')}
                                                                            </div>
                                                                        </td>
                                                                        <td className="text-center break-all">
                                                                            <div ref={(el) => (submenuRef.current[index] = el)}>
                                                                                <p id={"policy_group_list_view" + (index + 1)} onClick={() => setViewPolicyGroupId(index === viewPolicyGroupId ? null : index)} className={`font-semibold mb-0.5 cursor-pointer text-center`}
                                                                                    tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setViewPolicyGroupId(index === viewPolicyGroupId ? null : index))}>
                                                                                    ...
                                                                                </p>
                                                                                {viewPolicyGroupId === index && (
                                                                                    <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                                                                        <div className="flex justify-between hover:bg-gray-100" onClick={() => viewPolicyGroupDetails(policyGroup)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => viewPolicyGroupDetails(policyGroup))}>
                                                                                            <p id="partner_details_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                                                            <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`}></img>
                                                                                        </div>
                                                                                        <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                        <div className={`flex justify-between hover:bg-gray-100 ${policyGroup.isActive === true ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => showDeactivatePolicyGroup(policyGroup)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showDeactivatePolicyGroup(policyGroup))}>
                                                                                            <p id="partner_deactive_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${policyGroup.isActive === true ? "text-crimson-red" : "text-[#A5A5A5]"}`}>{t("partnerList.deActivate")}</p>
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
                                                )}
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