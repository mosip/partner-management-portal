import React, { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from '../../common/LoadingIcon';
import EmptyList from '../../common/EmptyList';
import Title from '../../common/Title.js';
import approveRejectIcon from "../../../svg/approve_reject_icon.svg";
import viewIcon from "../../../svg/view_icon.svg";
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import FilterButtons from '../../common/FilterButtons.js';
import SortingIcon from '../../common/SortingIcon.js';
import Pagination from '../../common/Pagination.js';
import { bgOfStatus, formatDate, getStatusCode, handleMouseClickForDropdown, isLangRTL, onClickApplyFilter, onPressEnterKey, onResetFilter, setPageNumberAndPageSize } from '../../../utils/AppUtils.js';
import DeviceProviderServicesTab from './DeviceProviderServicesTab.js';
import AdminSbiListFilter from './AdminSbiListFilter.js';

function AdminSbiList () {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [sbiList, setSbiList] = useState([]);
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
    const [filterAttributes, setFilterAttributes] = useState({
        partnerId: null,
        orgName: null,
        version: null,
        status: null,
    });
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
    }, [submenuRef]);

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'sbiList.partnerId' },
        { id: "orgName", headerNameKey: 'sbiList.orgName' },
        { id: "version", headerNameKey: "sbiList.version" },
        { id: "sbiCreatedDateTime", headerNameKey: "sbiList.createdDate" },
        { id: "sbiExpiryDateTime", headerNameKey: "sbiList.expiryDate" },
        { id: "status", headerNameKey: "sbiList.status" },
        { id: "linkedDevices", headerNameKey: "sbiList.linkedDevices" },
        { id: "action", headerNameKey: "sbiList.action" }
    ];

    useEffect(() => {
        const tableValues = [	
            {"sbiId":"10000","partnerId":"A10001","orgName":"ABC","status":"approved","sbiCreatedDateTime":"2024-09-17T10:25:46.000+00:00","sbiExpiryDateTime":"2025-09-17T10:25:46.000+00:00","createdDateTime":"2024-09-17T10:25:37.009826","version": "test1", "linkedDevices": 10},
            {"sbiId":"20000","partnerId":"A10002","orgName":"BCD","status":"rejected","sbiCreatedDateTime":"2024-09-18T10:25:46.000+00:00","sbiExpiryDateTime":"2025-09-18T10:25:46.000+00:00","createdDateTime":"2024-09-18T10:25:37.009826","version": "test2", "linkedDevices": 5},
            {"sbiId":"30000","partnerId":"A10003","orgName":"CDE","status":"pending_approval","sbiCreatedDateTime":"2024-09-19T10:25:46.000+00:00","sbiExpiryDateTime":"2025-09-19T10:25:46.000+00:00","createdDateTime":"2024-09-19T10:25:37.009826","version": "test3", "linkedDevices": 1},
            {"sbiId":"40000","partnerId":"A10004","orgName":"DEF","status":"approved","sbiCreatedDateTime":"2024-09-19T10:25:46.000+00:00","sbiExpiryDateTime":"2025-09-19T10:25:46.000+00:00","createdDateTime":"2024-09-17T10:25:37.009826","version": "test4", "linkedDevices": 50},
            {"sbiId":"50000","partnerId":"A10005","orgName":"EFG","status":"deactivated","sbiCreatedDateTime":"2024-09-20T10:25:46.000+00:00","sbiExpiryDateTime":"2025-09-20T10:25:46.000+00:00","createdDateTime":"2024-09-20T10:25:37.009826","version": "test5", "linkedDevices": 14},
            {"sbiId":"60000","partnerId":"A10006","orgName":"FGH","status":"approved","sbiCreatedDateTime":"2023-10-21T10:25:46.000+00:00","sbiExpiryDateTime":"2024-10-21T10:25:46.000+00:00","createdDateTime":"2024-09-21T10:25:37.009826","version": "test6", "linkedDevices": 0},
            {"sbiId":"70000","partnerId":"A10007","orgName":"GHI","status":"rejected","sbiCreatedDateTime":"2024-09-17T10:25:46.000+00:00","sbiExpiryDateTime":"2025-09-17T10:25:46.000+00:00","createdDateTime":"2024-09-17T10:25:37.009826","version": "test7", "linkedDevices": 5},
            {"sbiId":"80000","partnerId":"A10008","orgName":"ABC","status":"pending_approval","sbiCreatedDateTime":"2023-09-22T10:25:46.000+00:00","sbiExpiryDateTime":"2024-09-22T10:25:46.000+00:00","createdDateTime":"2024-09-22T10:25:37.009826","version": "test8", "linkedDevices": 7},
            {"sbiId":"90000","partnerId":"A10009","orgName":"ABCDEF","status":"approved","sbiCreatedDateTime":"2024-09-23T10:25:46.000+00:00","sbiExpiryDateTime":"2025-09-23T10:25:46.000+00:00","createdDateTime":"2024-09-23T10:25:37.009826","version": "test9", "linkedDevices": 22},
            {"sbiId":"10001","partnerId":"A10010","orgName":"ABC","status":"deactivated","sbiCreatedDateTime":"2024-09-17T10:25:46.000+00:00","sbiExpiryDateTime":"2025-09-17T10:25:46.000+00:00","createdDateTime":"2024-09-17T10:25:37.009826","version": "test10", "linkedDevices": 17},
            {"sbiId":"10002","partnerId":"A10011","orgName":"XXX","status":"rejected","sbiCreatedDateTime":"2024-09-17T10:25:46.000+00:00","sbiExpiryDateTime":"2025-09-17T10:25:46.000+00:00","createdDateTime":"2024-09-17T10:25:37.009826","version": "test11", "linkedDevices": 16}
        ];
        setSbiList(tableValues);

    }, []);

    const onApplyFilter = (updatedfilters) => {
        onClickApplyFilter(updatedfilters, setApplyFilter, setResetPageNo, setFetchData, setFilterAttributes);
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

    const viewSbiDetails = (selectedSbi) => {
        localStorage.setItem('selectedSbiAttributes',JSON.stringify(selectedSbi));
        navigate("/partnermanagement/admin/device-provider-services/view-sbi-details");
    };

    const approveRejectSbi = (selectedSbi) => {
    };

    const deactivateSbi = (selectedSbi) => {
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const styles = {
        loadingDiv: "!py-[20%]",
    }

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
            { !dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            { dataLoaded && (
                <>
                    { errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between mb-5 max-470:flex-col">
                            <Title title='deviceProviderServices.sbiDeviceDetails' backLink='/partnermanagement' ></Title>
                        </div>
                        <DeviceProviderServicesTab
                            activeSbi={true}
                            sbiListPath='/partnermanagement/admin/device-provider-services/sbi-list'
                            activeDevice={false}
                            devicesListPath='/partnermanagement/admin/device-provider-services/devices-list' 
                        />
                        { !applyFilter && sbiList.length === 0 ? (
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <EmptyList tableHeaders={tableHeaders} />
                            </div>
                        ) : (
                            <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                                <FilterButtons
                                    listTitle='sbiList.listOfSbi'
                                    dataListLength={totalRecords}
                                    filter={expandFilter}
                                    onResetFilter={onResetFilter}
                                    setFilter={setExpandFilter}
                                />
                                <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                { expandFilter && (
                                    <AdminSbiListFilter onApplyFilter={onApplyFilter} />
                                )}
                                { !tableDataLoaded && <LoadingIcon styleSet={styles}></LoadingIcon>}
                                { tableDataLoaded && applyFilter && sbiList.length === 0 ?
                                    <EmptyList tableHeaders={tableHeaders} />
                                    : (
                                        <>
                                            <div className="mx-[2%] overflow-x-scroll">
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
                                                        {sbiList.map((sbi, index) => {
                                                            return (
                                                                <tr id={"sbi_list_item" + (index + 1)} key={index}
                                                                    className={`border-t border-[#E5EBFA] ${sbi.status !== 'deactivated' ? 'cursor-pointer text-[#191919]' : 'cursor-default text-[#969696]'} text-[0.8rem] text-[#191919] font-semibold break-words`}>
                                                                    <td onClick={() => sbi.status !== 'deactivated' && viewSbiDetails(sbi)} className="px-2">{sbi.partnerId}</td>
                                                                    <td onClick={() => sbi.status !== 'deactivated' && viewSbiDetails(sbi)} className="px-2">{sbi.orgName}</td>
                                                                    <td onClick={() => sbi.status !== 'deactivated' && viewSbiDetails(sbi)} className="px-2">{sbi.version}</td>
                                                                    <td onClick={() => sbi.status !== 'deactivated' && viewSbiDetails(sbi)} className="px-2">{formatDate(sbi.sbiCreatedDateTime, "date", true)}</td>
                                                                    <td onClick={() => sbi.status !== 'deactivated' && viewSbiDetails(sbi)} className="px-2">{formatDate(sbi.sbiExpiryDateTime, "date", true)}</td>
                                                                    <td onClick={() => sbi.status !== 'deactivated' && viewSbiDetails(sbi)}>
                                                                        <div className={`${bgOfStatus(sbi.status)} flex min-w-fit w-14 justify-center py-1.5 px-2 mx-2 my-3 text-xs font-semibold rounded-md`}>
                                                                            {getStatusCode(sbi.status, t)}
                                                                        </div>
                                                                    </td>
                                                                    <td className="px-2">{sbi.linkedDevices}</td>
                                                                    <td className="text-center">
                                                                        <div ref={(el) => (submenuRef.current[index] = el)}>
                                                                            <p id={"sbi_list_action" + (index + 1)} onClick={() => setActionId(index === actionId ? null : index)} className={`font-semibold mb-0.5 text-[#191919] cursor-pointer text-center`}
                                                                                tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setActionId(index === actionId ? null : index))}>
                                                                                ...
                                                                            </p>
                                                                            {actionId === index && (
                                                                                <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                                                                    <div onClick={() => approveRejectSbi(sbi)} className={`flex justify-between hover:bg-gray-100 ${sbi.status === 'pending_approval' ? 'cursor-pointer' : 'cursor-default'} `} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => approveRejectSbi(sbi))}>
                                                                                        <p id="ftm_list_approve_reject_option" className={`py-1.5 px-4 ${sbi.status === 'pending_approval' ? 'text-[#3E3E3E] cursor-pointer' : 'text-[#A5A5A5] cursor-default'} ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("approveRejectPopup.approveReject")}</p>
                                                                                        <img src={approveRejectIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`}></img>
                                                                                    </div>
                                                                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                    <div className="flex justify-between hover:bg-gray-100" onClick={() => viewSbiDetails(sbi)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => viewSbiDetails(sbi))}>
                                                                                        <p id="sbi_list_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                                                        <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`}></img>
                                                                                    </div>
                                                                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                    <div className={`flex justify-between hover:bg-gray-100 ${sbi.status === 'approved' ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => deactivateSbi(sbi)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => deactivateSbi(sbi))}>
                                                                                        <p id="sbi_list_deactivate_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${sbi.status === 'approved' ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t("partnerList.deActivate")}</p>
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
                                    )
                                }
                            </div>
                        )}
                    </div>
                </>
            )}
        </div>
    );

}
export default AdminSbiList;