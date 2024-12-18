import React, { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from '../../common/LoadingIcon';
import EmptyList from '../../common/EmptyList';
import Title from '../../common/Title.js';
import DeviceProviderServicesTab from './DeviceProviderServicesTab.js';
import { handleMouseClickForDropdown, isLangRTL, onClickApplyFilter, setPageNumberAndPageSize, onResetFilter, bgOfStatus, getStatusCode, onPressEnterKey, formatDate, resetPageNumber, getPartnerManagerUrl, handleServiceErrors, createRequest, getApproveRejectStatus, updateActiveState } from '../../../utils/AppUtils';
import { HttpService } from '../../../services/HttpService.js';
import AdminDeviceDetailsFilter from './AdminDeviceDetailsFilter.js';
import FilterButtons from '../../common/FilterButtons.js';
import SortingIcon from '../../common/SortingIcon.js';
import ApproveRejectPopup from '../../common/ApproveRejectPopup.js';
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import disableDeactivateIcon from "../../../svg/disable_deactivate_icon.svg";
import approveRejectIcon from "../../../svg/approve_reject_icon.svg";
import disabledApproveRejectIcon from "../../../svg/approve_reject_disabled_icon.svg";
import viewIcon from "../../../svg/view_icon.svg";
import DeactivatePopup from '../../common/DeactivatePopup.js';
import Pagination from '../../common/Pagination.js';

function AdminDevicesList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [devicesList, setDevicesList] = useState([]);
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
    const [showDeviceDetailApproveRejectPopup, setShowDeviceDetailApproveRejectPopup] = useState(false);
    const [deactivateRequest, setDeactivateRequest] = useState({});
    const [showDeactivatePopup, setShowDeactivatePopup] = useState(false);
    const [filterAttributes, setFilterAttributes] = useState({
        deviceId: null,
        partnerId: null,
        orgName: null,
        make: null,
        model: null,
        status: null,
        deviceType: null,
        deviceSubType: null,
        sbiId: null,
        sbiVersion: null
    });
    const submenuRef = useRef([]);

    const tableHeaders = [
        { id: "deviceId", headerNameKey: 'devicesList.deviceId' },
        { id: "sbiId", headerNameKey: 'sbiList.sbiId' },
        { id: "sbiVersion", headerNameKey: 'sbiList.sbiVersion' },
        { id: "partnerId", headerNameKey: 'sbiList.partnerId' },
        { id: "orgName", headerNameKey: 'sbiList.orgName' },
        { id: "deviceType", headerNameKey: 'devicesList.deviceType' },
        { id: "deviceSubType", headerNameKey: "devicesList.deviceSubType" },
        { id: "make", headerNameKey: "devicesList.make" },
        { id: "model", headerNameKey: "devicesList.model" },
        { id: "createdDateTime", headerNameKey: "devicesList.createdDate" },
        { id: "status", headerNameKey: "devicesList.status" },
        { id: "action", headerNameKey: 'devicesList.action' }
    ];

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
    }, [submenuRef]);

    const fetchDeviceDetails = async () => {
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
        if (filterAttributes.make) queryParams.append('make', filterAttributes.make);
        if (filterAttributes.model) queryParams.append('model', filterAttributes.model);
        if (filterAttributes.status) queryParams.append('status', filterAttributes.status);
        if (filterAttributes.deviceType) queryParams.append('deviceType', filterAttributes.deviceType);
        if (filterAttributes.deviceSubType) queryParams.append('deviceSubType', filterAttributes.deviceSubType);
        if (filterAttributes.deviceId) queryParams.append('deviceId', filterAttributes.deviceId);
        if (filterAttributes.sbiId) queryParams.append('sbiId', filterAttributes.sbiId);
        if (filterAttributes.sbiVersion) queryParams.append('sbiVersion', filterAttributes.sbiVersion);

        const url = `${getPartnerManagerUrl('/devicedetail/search/v2', process.env.NODE_ENV)}?${queryParams.toString()}`;
        try {
            fetchData ? setTableDataLoaded(false) : setDataLoaded(false);
            const response = await HttpService.get(url);
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response.data;
                    setTotalRecords(responseData.response.totalResults);
                    setDevicesList(resData);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('adminDeviceDetailsList.errorInAdminDeviceDetailsList'));
            }
            fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
            setFetchData(false);
        } catch (err) {
            setFetchData(false);
            fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
            console.error('Error fetching data:', err);
            setErrorMsg(err);
        }
    }

    useEffect(() => {
        fetchDeviceDetails();
    }, [sortFieldName, sortType, pageNo, pageSize]);

    useEffect(() => {

        if (isApplyFilterClicked) {
            fetchDeviceDetails();
            setIsApplyFilterClicked(false);
        }
    }, [isApplyFilterClicked]);

    const onApplyFilter = (filters) => {
        onClickApplyFilter(filters, setApplyFilter, setResetPageNo, setFetchData, setFilterAttributes, setIsApplyFilterClicked);
    };

    const getPaginationValues = (recordsPerPage, pageIndex) => {
        setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setFetchData);
    };

    const approveRejectDeviceDetails = (device) => {
        if (device.status === 'pending_approval') {
            setShowDeviceDetailApproveRejectPopup(true);
            document.body.style.overflow = "hidden";
        }
    };

    const onClickApproveReject = (responseData, status, selectedDevice) => {
        if (responseData) {
            setActionId(-1);
            setShowDeviceDetailApproveRejectPopup(false);
            // Update the specific row in the state with the new status
            setDevicesList((prevList) =>
                prevList.map( deviceItem =>
                    deviceItem.deviceId === selectedDevice.deviceId ? { ...deviceItem, status: getApproveRejectStatus(status), isActive: updateActiveState(status) } : deviceItem
                )
            );
            document.body.style.overflow = "auto";
        }
    };

    const closeApproveRejectPopup = () => {
        setActionId(-1);
        setShowDeviceDetailApproveRejectPopup(false);
    };

    const deactivateDevice = (selectedDevice) => {
        if (selectedDevice.status === "approved") {
            const request = createRequest({
                deviceId: selectedDevice.deviceId,
            }, "mosip.pms.deactivate.device.post", true);
            setDeactivateRequest(request);
            setShowDeactivatePopup(true);
            document.body.style.overflow = "hidden";
        }

    };

    const onClickConfirmDeactivate = (deactivationResponse, selectedDevice) => {
        if (deactivationResponse && !deactivationResponse.isActive) {
            setActionId(-1);
            setShowDeactivatePopup(false);
            // Update the specific row in the state with the new status
            setDevicesList((prevList) =>
                prevList.map(device =>
                    device.deviceId === selectedDevice.deviceId ? { ...device, status: "deactivated" } : device
                )
            );
        }
    };

    const closeDeactivatePopup = () => {
        setActionId(-1);
        setShowDeactivatePopup(false);
    };

    const sortAscOrder = (header) => {
        if (order !== 'ASC' || activeAscIcon !== header) {
            setFetchData(true);
            setSortFieldName(header);
            setSortType("asc");
            setOrder("ASC");
            setActiveDescIcon("");
            setActiveAscIcon(header);
        }
    };
    const sortDescOrder = (header) => {
        if (order !== 'DESC' || activeDescIcon !== header) {
            setFetchData(true);
            setSortFieldName(header);
            setSortType('desc');
            setOrder("DESC");
            setActiveDescIcon(header);
            setActiveAscIcon("");
        }
    };

    const viewDeviceDetails = (selectedDevice) => {
        localStorage.setItem('selectedDeviceAttributes', JSON.stringify(selectedDevice));
        navigate("/partnermanagement/admin/device-provider-services/view-device-details");
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
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
                            <Title title='devicesList.listOfDevices' backLink='/partnermanagement' ></Title>
                        </div>
                        <DeviceProviderServicesTab
                            activeSbi={false}
                            sbiListPath='/partnermanagement/admin/device-provider-services/sbi-list'
                            activeDevice={true}
                            devicesListPath='/partnermanagement/admin/device-provider-services/devices-list'
                        />
                        {!applyFilter && devicesList.length === 0 ? (
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <EmptyList tableHeaders={tableHeaders} />
                            </div>
                        ) : (
                            <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                                <FilterButtons
                                    titleId='list_of_device_details'
                                    listTitle='devicesList.listOfDevices'
                                    dataListLength={totalRecords}
                                    filter={expandFilter}
                                    onResetFilter={onResetFilter}
                                    setFilter={setExpandFilter}
                                />
                                <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                {expandFilter && (
                                    <AdminDeviceDetailsFilter onApplyFilter={onApplyFilter} />
                                )}
                                {!tableDataLoaded && <LoadingIcon styleSet={styles}></LoadingIcon>}
                                {tableDataLoaded && applyFilter && devicesList.length === 0 ?
                                    <EmptyList tableHeaders={tableHeaders} />
                                    : (
                                        <>
                                            <div className="mx-[2%] overflow-x-scroll">
                                                <table className="table-fixed">
                                                    <thead>
                                                        <tr>
                                                            {tableHeaders.map((header, index) => {
                                                                return (
                                                                    <th key={index} className="py-4 text-sm font-semibold text-[#6F6E6E] w-[17%]">
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
                                                        {devicesList.map((device, index) => {
                                                            return (
                                                                <tr id={'device_list_item' + (index + 1)} key={index} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words ${(device.status === "deactivated") ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                                                    <td onClick={() => device.status !== 'deactivated' && viewDeviceDetails(device)} className="px-2">{device.deviceId}</td>
                                                                    <td onClick={() => device.status !== 'deactivated' && viewDeviceDetails(device)} className="px-2">{device.sbiId ?? '-'}</td>
                                                                    <td onClick={() => device.status !== 'deactivated' && viewDeviceDetails(device)} className="px-2">{device.sbiVersion ?? '-'}</td>
                                                                    <td onClick={() => device.status !== 'deactivated' && viewDeviceDetails(device)} className="px-2">{device.partnerId}</td>
                                                                    <td onClick={() => device.status !== 'deactivated' && viewDeviceDetails(device)} className="px-2">{device.orgName}</td>
                                                                    <td onClick={() => device.status !== 'deactivated' && viewDeviceDetails(device)} className="px-2">{device.deviceType}</td>
                                                                    <td onClick={() => device.status !== 'deactivated' && viewDeviceDetails(device)} className="px-2">{device.deviceSubType}</td>
                                                                    <td onClick={() => device.status !== 'deactivated' && viewDeviceDetails(device)} className="px-2">{device.make}</td>
                                                                    <td onClick={() => device.status !== 'deactivated' && viewDeviceDetails(device)} className="px-2">{device.model}</td>
                                                                    <td onClick={() => device.status !== 'deactivated' && viewDeviceDetails(device)} className="px-2">{formatDate(device.createdDateTime, 'date', true)}</td>
                                                                    <td onClick={() => device.status !== 'deactivated' && viewDeviceDetails(device)} className="px-2 mx-2">
                                                                        <div className={`${bgOfStatus(device.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-semibold rounded-md`}>
                                                                            {getStatusCode(device.status, t)}
                                                                        </div>
                                                                    </td>
                                                                    <td className="text-center">
                                                                        <div ref={(el) => (submenuRef.current[index] = el)}>
                                                                            <p id={"device_list_action_menu" + (index + 1)} onClick={() => setActionId(index === actionId ? null : index)} className={`font-semibold mb-0.5 text-[#191919] cursor-pointer text-center`}
                                                                                tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setActionId(index === actionId ? null : index))}>
                                                                                ...
                                                                            </p>
                                                                            {actionId === index && (
                                                                                <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                                                                    <div onClick={() => approveRejectDeviceDetails(device)} className={`flex justify-between hover:bg-gray-100 ${device.status === 'pending_approval' ? 'cursor-pointer' : 'cursor-default'} `} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => approveRejectDeviceDetails(device))}>
                                                                                        <p id="device_list_approve_reject_option" className={`py-1.5 px-4 ${device.status === 'pending_approval' ? 'text-[#3E3E3E] cursor-pointer' : 'text-[#A5A5A5] cursor-default'} ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("approveRejectPopup.approveReject")}</p>
                                                                                        <img src={device.status === 'pending_approval' ? approveRejectIcon : disabledApproveRejectIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                    {showDeviceDetailApproveRejectPopup && (
                                                                                        <ApproveRejectPopup
                                                                                            popupData={{ ...device, isDeviceRequest: true }}
                                                                                            closePopUp={closeApproveRejectPopup}
                                                                                            approveRejectResponse={(responseData, status) => onClickApproveReject(responseData, status, device)}
                                                                                            title={`${device.make} | ${device.model}`}
                                                                                            header={t('deviceApproveRejectPopup.header')}
                                                                                            description={t('deviceApproveRejectPopup.description')}
                                                                                        />
                                                                                    )}
                                                                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                    <div className="flex justify-between hover:bg-gray-100" onClick={() => viewDeviceDetails(device)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => viewDeviceDetails(device))}>
                                                                                        <p id="device_list_view_option" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                                                        <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                    <div onClick={() => deactivateDevice(device)} className={`flex justify-between hover:bg-gray-100 ${device.status === 'approved' ? 'cursor-pointer' : 'cursor-default'}`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => deactivateDevice(device))}>
                                                                                        <p id="device_list_deactivate_option" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${device.status === 'approved' ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t("partnerList.deActivate")}</p>
                                                                                        <img src={device.status === 'approved' ? deactivateIcon : disableDeactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                    {showDeactivatePopup && (
                                                                                        <DeactivatePopup closePopUp={closeDeactivatePopup} onClickConfirm={(deactivationResponse) => onClickConfirmDeactivate(deactivationResponse, device)} popupData={{ ...device, isDeactivateDevice: true }} request={deactivateRequest} headerMsg='deactivateDevicePopup.headerMsg' descriptionMsg='deactivateDevicePopup.descriptionForAdmin' />
                                                                                    )}
                                                                                </div>
                                                                            )}
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            )
                                                        })
                                                        }
                                                    </tbody>
                                                </table>
                                                <Pagination
                                                    dataListLength={totalRecords}
                                                    selectedRecordsPerPage={selectedRecordsPerPage}
                                                    setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                                                    setFirstIndex={setFirstIndex}
                                                    isServerSideFilter={true}
                                                    getPaginationValues={getPaginationValues}
                                                />
                                            </div>
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
export default AdminDevicesList;