import React, { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from '../../common/LoadingIcon';
import EmptyList from '../../common/EmptyList';
import Title from '../../common/Title.js';
import approveRejectIcon from "../../../svg/approve_reject_icon.svg";
import disabledApproveRejectIcon from "../../../svg/approve_reject_disabled_icon.svg";
import viewIcon from "../../../svg/view_icon.svg";
import activeLinkedDevices from "../../../svg/active_linked_devices_icon.svg";
import deactiveLinkedDevices from "../../../svg/deactive_linked_devices_icon.svg";
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import disableDeactivateIcon from "../../../svg/disable_deactivate_icon.svg";
import FilterButtons from '../../common/FilterButtons.js';
import SortingIcon from '../../common/SortingIcon.js';
import Pagination from '../../common/Pagination.js';
import { bgOfStatus, formatDate, getPartnerManagerUrl, getApproveRejectStatus, getStatusCode, handleMouseClickForDropdown, handleServiceErrors, isLangRTL, onClickApplyFilter, onPressEnterKey, onResetFilter, resetPageNumber, setPageNumberAndPageSize, updateActiveState, createRequest, escapeKeyHandler, setSubmenuRef } from '../../../utils/AppUtils.js';
import DeviceProviderServicesTab from './DeviceProviderServicesTab.js';
import AdminSbiListFilter from './AdminSbiListFilter.js';
import { HttpService } from '../../../services/HttpService.js';
import ApproveRejectPopup from '../../common/ApproveRejectPopup.js';
import DeactivatePopup from '../../common/DeactivatePopup.js';

function AdminSbiList() {
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
    const [showActiveIndexSbiApproveRejectPopUp, setShowActiveIndexSbiApproveRejectPopUp] = useState(null);
    const [totalRecords, setTotalRecords] = useState(0);
    const [resetPageNo, setResetPageNo] = useState(false);
    const [applyFilter, setApplyFilter] = useState(false);
    const [isApplyFilterClicked, setIsApplyFilterClicked] = useState(false);
    const [showActiveIndexDeactivatePopup, setShowActiveIndexDeactivatePopup] = useState(null);
    const [deactivateRequest, setDeactivateRequest] = useState({});
    const [selectedSbi, setSelectedSbi] = useState({});
    const [filterAttributes, setFilterAttributes] = useState({
        partnerId: null,
        orgName: null,
        sbiId: null,
        sbiVersion: null,
        status: null,
        sbiExpiryStatus: null
    });
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
    }, [submenuRef]);

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'sbiList.partnerId' },
        { id: "orgName", headerNameKey: 'sbiList.orgName' },
        { id: "sbiId", headerNameKey: "sbiList.sbiId" },
        { id: "sbiVersion", headerNameKey: "sbiList.sbiVersion" },
        { id: "sbiCreatedDateTime", headerNameKey: "sbiList.sbiCreatedDate" },
        { id: "sbiExpiryDateTime", headerNameKey: "sbiList.sbiExpiryDate" },
        { id: "sbiExpiryStatus", headerNameKey: "sbiList.sbiExpiryStatus" },
        { id: "createdDateTime", headerNameKey: "sbiList.creationDate" },
        { id: "status", headerNameKey: "sbiList.status" },
        { id: "countOfAssociatedDevices", headerNameKey: "sbiList.linkedDevices" },
        { id: "action", headerNameKey: "sbiList.action" }
    ];

    const fetchSbiListData = async () => {
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
        if (filterAttributes.sbiId) queryParams.append('sbiId', filterAttributes.sbiId);
        if (filterAttributes.sbiVersion) queryParams.append('sbiVersion', filterAttributes.sbiVersion);
        if (filterAttributes.status) queryParams.append('status', filterAttributes.status);
        if (filterAttributes.sbiExpiryStatus) queryParams.append('sbiExpiryStatus', filterAttributes.sbiExpiryStatus);

        const url = `${getPartnerManagerUrl('/securebiometricinterface', process.env.NODE_ENV)}?${queryParams.toString()}`;
        try {
            fetchData ? setTableDataLoaded(false) : setDataLoaded(false);
            const response = await HttpService.get(url);
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response.data;
                    setTotalRecords(responseData.response.totalResults);
                    setSbiList(resData);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('sbiList.errorInSbiList'));
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
        fetchSbiListData();
    }, [sortFieldName, sortType, pageNo, pageSize]);

    useEffect(() => {

        if (isApplyFilterClicked) {
            fetchSbiListData();
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

    const viewSbiDetails = (selectedSbi) => {
        localStorage.setItem('selectedSbiAttributes', JSON.stringify(selectedSbi));
        navigate("/partnermanagement/admin/device-provider-services/view-sbi-details");
    };

    const showLinkedDevices = (selectedSbi) => {
        if (selectedSbi.countOfAssociatedDevices > 0) {
            navigate(`/partnermanagement/admin/device-provider-services/linked-devices-list?sbiId=${selectedSbi.sbiId}&sbiVersion=${selectedSbi.sbiVersion}`);
        }
    };

    const approveRejectSbi = (selectedSbi, index) => {
        if (selectedSbi.status === 'pending_approval') {
            setSelectedSbi(selectedSbi);
            setActionId(-1);
            setShowActiveIndexSbiApproveRejectPopUp(index);
        }
    };

    const onClickApproveReject = (responseData, status, selectedSbi) => {
        if (responseData) {
            setShowActiveIndexSbiApproveRejectPopUp(null);
            setSelectedSbi({});
            setSbiList((prevList) =>
                prevList.map(sbi =>
                    sbi.sbiId === selectedSbi.sbiId ? { ...sbi, status: getApproveRejectStatus(status), isActive: updateActiveState(status) } : sbi
                )
            );
        }
    }

    const closeApproveRejectPopup = () => {
        setShowActiveIndexSbiApproveRejectPopUp(null);
        setSelectedSbi({});
    };

    const deactivateSbi = (selectedSbi, index) => {
        if (selectedSbi.status === "approved") {
            const request = createRequest({
                status: "De-Activate",
            }, "mosip.pms.deactivate.sbi.patch", true);
            setSelectedSbi(selectedSbi);
            setActionId(-1);
            setDeactivateRequest(request);
            setShowActiveIndexDeactivatePopup(index);
        }
    };

    const onClickConfirmDeactivate = (deactivationResponse, selectedSbiData) => {
        if (deactivationResponse && !deactivationResponse.isActive) {
            setSelectedSbi({});
            setShowActiveIndexDeactivatePopup(null);
            // Update the specific row in the state with the new status
            setSbiList((prevList) =>
                prevList.map(sbi =>
                    sbi.sbiId === selectedSbiData.sbiId ? { ...sbi, status: "deactivated", isActive: false } : sbi
                )
            );
        }
    };

    const closeDeactivatePopup = () => {
        setSelectedSbi({});
        setShowActiveIndexDeactivatePopup(null);
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const styles = {
        loadingDiv: "!py-[20%]",
    }

    useEffect(() => {
        if (showActiveIndexSbiApproveRejectPopUp) {
            escapeKeyHandler(closeApproveRejectPopup);
        } else if (showActiveIndexDeactivatePopup) {
            escapeKeyHandler(closeDeactivatePopup);
        }
    }, [showActiveIndexSbiApproveRejectPopUp, showActiveIndexDeactivatePopup]);

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
                            <Title title='dashboard.sbiDevice' backLink='/partnermanagement' />
                        </div>
                        <DeviceProviderServicesTab
                            activeSbi={true}
                            sbiListPath='/partnermanagement/admin/device-provider-services/sbi-list'
                            activeDevice={false}
                            devicesListPath='/partnermanagement/admin/device-provider-services/devices-list'
                        />
                        {!applyFilter && sbiList.length === 0 ? (
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <EmptyList tableHeaders={tableHeaders} />
                            </div>
                        ) : (
                            <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                                <FilterButtons
                                    listTitle='sbiList.listOfSbis'
                                    dataListLength={totalRecords}
                                    filter={expandFilter}
                                    onResetFilter={onResetFilter}
                                    setFilter={setExpandFilter}
                                />
                                <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                {expandFilter && (
                                    <AdminSbiListFilter onApplyFilter={onApplyFilter} />
                                )}
                                {!tableDataLoaded && <LoadingIcon styleSet={styles}></LoadingIcon>}
                                {tableDataLoaded && applyFilter && sbiList.length === 0 ?
                                    <EmptyList tableHeaders={tableHeaders} />
                                    : (
                                        <>
                                            <div className="mx-[1.4rem] overflow-x-scroll">
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
                                                                    <td onClick={() => sbi.status !== 'deactivated' && viewSbiDetails(sbi)} className="px-2">{sbi.sbiId}</td>
                                                                    <td onClick={() => sbi.status !== 'deactivated' && viewSbiDetails(sbi)} className="px-2">{sbi.sbiVersion}</td>
                                                                    <td onClick={() => sbi.status !== 'deactivated' && viewSbiDetails(sbi)} className="px-2">{formatDate(sbi.sbiCreatedDateTime, "date")}</td>
                                                                    <td onClick={() => sbi.status !== 'deactivated' && viewSbiDetails(sbi)} className="px-2">{formatDate(sbi.sbiExpiryDateTime, "date")}</td>
                                                                    <td onClick={() => sbi.status !== 'deactivated' && viewSbiDetails(sbi)} className={`px-2 ${(sbi.status !== 'deactivated' && sbi.sbiExpiryStatus === 'expired') && 'text-crimson-red'}`}>{getStatusCode(sbi.sbiExpiryStatus, t)}</td>
                                                                    <td onClick={() => sbi.status !== 'deactivated' && viewSbiDetails(sbi)} className="px-2">{formatDate(sbi.createdDateTime, "date")}</td>
                                                                    <td onClick={() => sbi.status !== 'deactivated' && viewSbiDetails(sbi)}>
                                                                        <div className={`${bgOfStatus(sbi.status)} flex min-w-fit w-14 justify-center py-1.5 px-2 mx-2 my-3 text-xs font-semibold rounded-md`}>
                                                                            {getStatusCode(sbi.status, t)}
                                                                        </div>
                                                                    </td>
                                                                    <td className={`text-center px-5`}>
                                                                        <button onClick={() => showLinkedDevices(sbi)} className={`flex items-center justify-center ${sbi.countOfAssociatedDevices > 0 ? 'cursor-pointer' : 'cursor-default'}`}>
                                                                            <img src={sbi.countOfAssociatedDevices > 0 ? activeLinkedDevices : deactiveLinkedDevices} alt='' />
                                                                            <p className={`${sbi.countOfAssociatedDevices > 0 ? 'text-tory-blue' : 'text-[#969696]'} px-2`}>{sbi.countOfAssociatedDevices}</p>
                                                                        </button>
                                                                    </td>
                                                                    <td className="text-center cursor-default">
                                                                        <div ref={setSubmenuRef(submenuRef, index)}>
                                                                            <button id={"sbi_list_action" + (index + 1)} onClick={() => setActionId(index === actionId ? null : index)} className={`font-semibold mb-0.5 text-[#191919] cursor-pointer text-center`}>
                                                                                ...
                                                                            </button>

                                                                            {actionId === index && (
                                                                                <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                                                                    <div role='button' onClick={() => approveRejectSbi(sbi, index)} className={`flex justify-between hover:bg-gray-100 ${sbi.status === 'pending_approval' ? 'cursor-pointer' : 'cursor-default'} `} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => approveRejectSbi(sbi, index))}>
                                                                                        <p id="ftm_list_approve_reject_option" className={`py-1.5 px-4 ${sbi.status === 'pending_approval' ? 'text-[#3E3E3E] cursor-pointer' : 'text-[#A5A5A5] cursor-default'} ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("approveRejectPopup.approveReject")}</p>
                                                                                        <img src={sbi.status === 'pending_approval' ? approveRejectIcon : disabledApproveRejectIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                    <div role='button' className="flex justify-between hover:bg-gray-100" onClick={() => viewSbiDetails(sbi)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => viewSbiDetails(sbi))}>
                                                                                        <p id="sbi_list_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                                                        <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                    <div role='button' className={`flex justify-between hover:bg-gray-100 ${sbi.status === 'approved' ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => deactivateSbi(sbi, index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => deactivateSbi(sbi, index))}>
                                                                                        <p id="sbi_list_deactivate_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${sbi.status === 'approved' ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t("partnerList.deActivate")}</p>
                                                                                        <img src={sbi.status === 'approved' ? deactivateIcon : disableDeactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                </div>
                                                                            )}
                                                                            {showActiveIndexSbiApproveRejectPopUp === index && (
                                                                                <ApproveRejectPopup
                                                                                    popupData={{ ...selectedSbi, isSbiRequest: true }}
                                                                                    closePopUp={closeApproveRejectPopup}
                                                                                    approveRejectResponse={(responseData, status) => onClickApproveReject(responseData, status, selectedSbi)}
                                                                                    title={selectedSbi.sbiVersion}
                                                                                    header={t('sbiApproveRejectPopup.header')}
                                                                                    description={t('sbiApproveRejectPopup.description')}
                                                                                />
                                                                            )}
                                                                            {showActiveIndexDeactivatePopup === index && (
                                                                                <DeactivatePopup
                                                                                    closePopUp={() => closeDeactivatePopup()}
                                                                                    onClickConfirm={(deactivationResponse) => onClickConfirmDeactivate(deactivationResponse, selectedSbi)}
                                                                                    popupData={{ ...selectedSbi, isDeactivateSbi: true }}
                                                                                    request={deactivateRequest} headerMsg='deactivateSbi.headerMsg'
                                                                                    descriptionMsg='deactivateSbi.descriptionForAdmin'
                                                                                    headerKeyName={selectedSbi.sbiVersion}
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