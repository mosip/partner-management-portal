import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate, useBlocker } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import {
    isLangRTL, handleServiceErrors, getPartnerManagerUrl, formatDate, getStatusCode,
    handleMouseClickForDropdown, toggleSortDescOrder, toggleSortAscOrder, bgOfStatus,
    onPressEnterKey,
    moveToSbisList, populateDeactivatedStatus,
    createRequest, isFilterChanged
} from '../../../utils/AppUtils.js';
import { HttpService } from '../../../services/HttpService';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from "../../common/LoadingIcon";
import FilterButtons from '../../common/FilterButtons.js';
import SortingIcon from '../../common/SortingIcon.js';
import Pagination from '../../common/Pagination.js';
import Title from '../../common/Title.js';
import DevicesListFilter from './DevicesListFilter.js';
import DeactivatePopup from '../../common/DeactivatePopup.js';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';
import EmptyList from '../../common/EmptyList.js';
import BlockerPrompt from "../../common/BlockerPrompt";

function DevicesList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [dataLoaded, setDataLoaded] = useState(false);
    const [filter, setFilter] = useState(false);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')): 8);
    const [order, setOrder] = useState("ASC");
    const [activeSortAsc, setActiveSortAsc] = useState("createdDateTime");
    const [activeSortDesc, setActiveSortDesc] = useState("");
    const [isDescending, setIsDescending] = useState(false);
    const [showDeactivatePopup, setShowDeactivatePopup] = useState(false);
    const [firstIndex, setFirstIndex] = useState(0);
    const [devicesList, setDevicesList] = useState([]);
    const [deactivateRequest, setDeactivateRequest] = useState({});
    const [filteredDevicesList, setFilteredDevicesList] = useState([]);
    const [viewDeviceId, setViewDeviceId] = useState(-1);
    const [canAddDevices, setCanAddDevices] = useState(true);
    const [selectedSbidata, setSelectedSbidata] = useState(true);

    const defaultFilterQuery = {
        deviceTypeCode: "",
        deviceSubTypeCode: ""
    };
    const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setViewDeviceId(-1));
    }, [submenuRef]);

    const blocker = useBlocker(
        ({ currentLocation, nextLocation }) => {
          if (!isFilterChanged(filterQuery)) {
            return false;
          }
          return (
            (isFilterChanged(filterQuery)) &&
            currentLocation.pathname !== nextLocation.pathname
          );
        }
      );
    
      useEffect(() => {
        const shouldWarnBeforeUnload = () => {
          return isFilterChanged(filterQuery);
        };
    
        const handleBeforeUnload = (event) => {
          if (shouldWarnBeforeUnload()) {
            event.preventDefault();
            event.returnValue = '';
          }
        };
        window.addEventListener('beforeunload', handleBeforeUnload);
        return () => {
          window.removeEventListener('beforeunload', handleBeforeUnload);
        };
      }, [filterQuery]);

    useEffect(() => {
        const selectedSbi = localStorage.getItem('selectedSbiData');
        if (!selectedSbi) {
            setDataLoaded(true);
            setUnexpectedError(true);
            return;
        }
        let sbiData = JSON.parse(selectedSbi);
        setSelectedSbidata(sbiData);
        console.log(sbiData)
        if (!sbiData.canAddDevices) {
            setCanAddDevices(false);
        }
        const fetchData = async () => {
            try {
                setDataLoaded(false);

                let sbiId = sbiData.sbiId;
                const response = await HttpService.get(getPartnerManagerUrl(`/securebiometricinterface/sbi-devices/${sbiId}`, process.env.NODE_ENV), {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        const populatedData = populateDeactivatedStatus(resData, "status", "isActive");
                        const sortedData = populatedData.sort((a, b) => new Date(b.createdDateTime) - new Date(a.createdDateTime));
                        setDevicesList(sortedData);
                        setFilteredDevicesList(sortedData);
                    } else {
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('devicesList.errorInViewDevices'));
                }

                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                setErrorMsg(err);
            }
        };
        fetchData();
    }, []);

    const tableHeaders = [
        { id: "deviceTypeCode", headerNameKey: 'devicesList.deviceType' },
        { id: "deviceSubTypeCode", headerNameKey: "devicesList.deviceSubType" },
        { id: "make", headerNameKey: "devicesList.make" },
        { id: "model", headerNameKey: "devicesList.model" },
        { id: "createdDateTime", headerNameKey: "devicesList.createdDate" },
        { id: "status", headerNameKey: "devicesList.status" },
        { id: "action", headerNameKey: 'devicesList.action' }
    ];

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const addDevices = () => {
        const previousPath = {
            name: 'devicesList.listOfDevices',
            path: '/partnermanagement/device-provider-services/devices-list',
            backToSbiList: false
        };
        localStorage.setItem('previousPath', JSON.stringify(previousPath));
        navigate('/partnermanagement/device-provider-services/add-devices');
    }

    //This part is related to Filter
    const onFilterChange = (fieldName, selectedFilter) => {
        setFilterQuery(oldFilterQuery => ({
            ...oldFilterQuery,
            [fieldName]: selectedFilter
        }));
    }
    useEffect(() => {
        let filteredRows = devicesList;
        Object.keys(filterQuery).forEach(key => {
            if (filterQuery[key] !== '') {
                filteredRows = filteredRows.filter(item => item[key] === filterQuery[key]);
            }
        });
        setFilteredDevicesList(filteredRows);
        setFirstIndex(0);
    }, [filterQuery, devicesList]);

    const onResetFilter = () => {
        window.location.reload();
    }

    const sortAscOrder = (header) => {
        const isDateCol = (header === "createdDateTime");
        toggleSortAscOrder(header, isDateCol, filteredDevicesList, setFilteredDevicesList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
    }

    const sortDescOrder = (header) => {
        const isDateCol = (header === "createdDateTime");
        toggleSortDescOrder(header, isDateCol, filteredDevicesList, setFilteredDevicesList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
    }

    const showDeviceDetails = (selectedDeviceData) => {
        if (selectedDeviceData.status !== "deactivated") {
            localStorage.setItem('selectedDeviceData', JSON.stringify(selectedDeviceData));
            navigate('/partnermanagement/device-provider-services/view-device-details')
        }
    }

    const viewDeviceDetails = (selectedDeviceData) => {
        localStorage.setItem('selectedDeviceData', JSON.stringify(selectedDeviceData));
        navigate('/partnermanagement/device-provider-services/view-device-details')
    }

    //This part related to Pagination Logic
    let tableRows = filteredDevicesList.slice(firstIndex, firstIndex + (selectedRecordsPerPage));

    const showDeactivateDevice = (selectedDevice) => {
        if (selectedDevice.status === "approved") {
            const request = createRequest({
                deviceId: selectedDevice.id,
            }, "mosip.pms.deactivate.device.post", true);
            setDeactivateRequest(request);
            setShowDeactivatePopup(true);
            document.body.style.overflow = "hidden";
        }
    };

    const closeDeactivatePopup = () => {
        setViewDeviceId(-1);
        setShowDeactivatePopup(false);
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}/>
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between mb-5">
                            <Title
                                title='devicesList.listOfDevices'
                                subTitle='sbiList.listOfSbi'
                                backLink='/partnermanagement/device-provider-services/sbi-list'
                                status={!unexpectedError ? selectedSbidata.status : ''}
                                version={!unexpectedError ? selectedSbidata.sbiVersion : ''}
                            />
                            {devicesList.length > 0 ?
                                <button id='device_list_add_device_btn' onClick={() => addDevices()} type="button" disabled={!canAddDevices}
                                    className={`h-10 text-sm font-semibold px-7  rounded-md ${canAddDevices ? "bg-tory-blue text-white" : "bg-gray-400 opacity-55"}`}>
                                    {t('devicesList.addDevices')}
                                </button>
                                : null
                            }
                        </div>
                        {unexpectedError && (
                            <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center items-center">
                                        <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                        <p className="text-sm font-semibold text-[#6F6E6E] py-4">{t('devicesList.unexpectedError')}</p>
                                        <button onClick={() => moveToSbisList(navigate)} type="button"
                                            className={`w-32 h-10 flex items-center justify-center font-semibold rounded-md text-sm mx-8 py-3 bg-tory-blue text-white`}>
                                            {t('commons.goBack')}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        )}
                        {!unexpectedError && (
                            <>
                                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                    <div className="flex items-center justify-center p-2">
                                        <div className="p-2 bg-[#FFF7E5] border-2 border-[#EDDCAF] rounded-md w-full">
                                            <p className="text-sm font-medium text-[#8B6105]">{t('sbiList.guidence')}</p>
                                        </div>
                                    </div>
                                </div>
                                {devicesList.length === 0
                                    ?
                                    <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                        <EmptyList 
                                            tableHeaders={tableHeaders} 
                                            showCustomButton={true}
                                            customButtonName='devicesList.addDevices'
                                            onClickButton={addDevices}
                                        />
                                    </div>
                                    :
                                    <>
                                        <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg">
                                            <FilterButtons listTitle='devicesList.listOfDevices' dataListLength={filteredDevicesList.length} filter={filter} onResetFilter={onResetFilter} setFilter={setFilter}></FilterButtons>
                                            <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                            {filter &&
                                                <DevicesListFilter
                                                    filteredDevicesList={filteredDevicesList}
                                                    onFilterChange={onFilterChange}>
                                                </DevicesListFilter>
                                            }
                                            <div className="mx-[2%] overflow-x-scroll">
                                                <table className="table-fixed">
                                                    <thead>
                                                        <tr>
                                                            {tableHeaders.map((header, index) => {
                                                                return (
                                                                    <th key={index} className={`py-4 px-2 text-xs text-[#6F6E6E] w-[17%]`}>
                                                                        <div className={`flex items-center gap-x-1 font-semibold ${header.id === "action" && 'justify-center'}`}>
                                                                            {t(header.headerNameKey)}
                                                                            {(header.id !== "action") && (
                                                                                <SortingIcon headerId={header.id} sortDescOrder={sortDescOrder} sortAscOrder={sortAscOrder} order={order} activeSortDesc={activeSortDesc} activeSortAsc={activeSortAsc}></SortingIcon>
                                                                            )}
                                                                        </div>
                                                                    </th>
                                                                )
                                                            })}
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        {
                                                            tableRows.map((device, index, currentArray) => {
                                                                return (
                                                                    <tr id={'device_list_device_item' +  (index + 1)} key={index} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words ${(device.status === "deactivated") ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                                                        <td onClick={() => showDeviceDetails(device)} className="px-2 mx-2">{device.deviceTypeCode}</td>
                                                                        <td onClick={() => showDeviceDetails(device)} className="px-2 mx-2">{device.deviceSubTypeCode}</td>
                                                                        <td onClick={() => showDeviceDetails(device)} className="px-2 mx-2">{device.make}</td>
                                                                        <td onClick={() => showDeviceDetails(device)} className="px-2 mx-2">{device.model}</td>
                                                                        <td onClick={() => showDeviceDetails(device)} className="px-2 mx-2">{formatDate(device.createdDateTime, 'date', true)}</td>
                                                                        <td onClick={() => showDeviceDetails(device)} className="px-2 mx-2">
                                                                            <div className={`${bgOfStatus(device.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-semibold rounded-md`}>
                                                                                {getStatusCode(device.status, t)}
                                                                            </div>
                                                                        </td>
                                                                        <td className="px-2 mx-2">
                                                                            <div className="flex items-center justify-center relative" ref={el => submenuRef.current[index] = el}>
                                                                                <p id={'device_list_action' + (index + 1)} onClick={() => setViewDeviceId(index === viewDeviceId ? null : index)} className="font-semibold mb-0.5 cursor-pointer text-[#1447B2]"
                                                                                    tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setViewDeviceId(index === viewDeviceId ? null : index))}>
                                                                                    ...</p>
                                                                                {viewDeviceId === index && (
                                                                                    <div className={`absolute w-[7%] ${currentArray.length - 1 === index ? '-bottom-2' : currentArray.length - 2 === index ? '-bottom-2' : 'top-5'} z-50 bg-white text-xs text-start font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-6 text-right" : "right-6 text-left"}`}>
                                                                                        <p id='device_list_view_details' onClick={() => viewDeviceDetails(device)} className={`py-2 px-4 cursor-pointer text-[#3E3E3E] hover:bg-gray-100 ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => viewDeviceDetails(device))}>
                                                                                            {t('devicesList.view')}
                                                                                        </p>
                                                                                        <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                        <p id='device_list_deactivate_device' onClick={() => showDeactivateDevice(device)} className={`py-2 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${device.status === "approved" ? 'text-[#3E3E3E] cursor-pointer' : 'text-[#A5A5A5] cursor-auto'} hover:bg-gray-100`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showDeactivateDevice(device))}>
                                                                                            {t('devicesList.deActivate')}
                                                                                        </p>
                                                                                        {showDeactivatePopup && (
                                                                                            <DeactivatePopup closePopUp={closeDeactivatePopup} popupData={{ ...device, isDeactivateDevice: true }} request={deactivateRequest} headerMsg='deactivateDevicePopup.headerMsg' descriptionMsg='deactivateDevicePopup.description' />
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
                                            </div>
                                        </div>
                                        <Pagination dataListLength={filteredDevicesList.length} selectedRecordsPerPage={selectedRecordsPerPage} setSelectedRecordsPerPage={setSelectedRecordsPerPage} setFirstIndex={setFirstIndex}></Pagination>
                                    </>
                                }
                            </>
                        )}
                    </div>
                </>
            )}
            <BlockerPrompt blocker={blocker} />
        </div>
    )
}

export default DevicesList;