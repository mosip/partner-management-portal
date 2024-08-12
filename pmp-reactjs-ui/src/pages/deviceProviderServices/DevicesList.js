import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import {
    isLangRTL, handleServiceErrors, getPartnerManagerUrl, formatDate, getStatusCode,
    handleMouseClickForDropdown, toggleSortDescOrder, toggleSortAscOrder, createRequest, bgOfStatus,
    onPressEnterKey,
    moveToSbisList
} from '../../utils/AppUtils.js';
import { HttpService } from '../../services/HttpService';
import ErrorMessage from '../common/ErrorMessage';
import LoadingIcon from "../common/LoadingIcon";
import rectangleGrid from '../../svg/rectangle_grid.svg';
import DeactivatePopup from '../common/DeactivatePopup.js';
import FilterButtons from '../common/FilterButtons.js';
import SortingIcon from '../common/SortingIcon.js';
import Pagination from '../common/Pagination.js';
import Title from '../common/Title.js';
import DevicesListFilter from './DevicesListFilter.js';

function DevicesList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(false);
    const [filter, setFilter] = useState(false);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
    const [order, setOrder] = useState("ASC");
    const [activeSortAsc, setActiveSortAsc] = useState("crDtimes");
    const [activeSortDesc, setActiveSortDesc] = useState("");
    const [isDescending, setIsDescending] = useState(false);

    const [firstIndex, setFirstIndex] = useState(0);
    const [devicesList, setDevicesList] = useState([]);
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

    useEffect(() => {
        const selectedSbi = localStorage.getItem('selectedSbiData');
        if (!selectedSbi) {
            moveToSbisList(navigate);
            return;
        }
        let sbiData = JSON.parse(selectedSbi);
        setSelectedSbidata(sbiData);
        console.log(sbiData)
        if(!sbiData.canAddDevices){
            setCanAddDevices(false);
        }
        const fetchData = async () => {
            try {
                setDataLoaded(false);

                let sbiId = sbiData.sbiId;
                const response = await HttpService.get(getPartnerManagerUrl(`/partners/getAllDevicesForSBI/${sbiId}`, process.env.NODE_ENV), {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        const sortedData = resData.sort((a, b) => new Date(b.crDtimes) - new Date(a.crDtimes));
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
        { id: "crDtimes", headerNameKey: "devicesList.createdDate" },
        { id: "approvalStatus", headerNameKey: "devicesList.status" },
        { id: "action", headerNameKey: 'devicesList.action' }
    ];

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const addDevices = () => {
        navigate('/partnermanagement/deviceProviderServices/addDevices');
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
        const isDateCol = (header === "crDtimes");
        toggleSortAscOrder(header, isDateCol, filteredDevicesList, setFilteredDevicesList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
    }

    const sortDescOrder = (header) => {
        const isDateCol = (header === "crDtimes");
        toggleSortDescOrder(header, isDateCol, filteredDevicesList, setFilteredDevicesList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
    }

    const showDeviceDetails = (selectedDeviceData) => {
        localStorage.setItem('selectedDeviceData', JSON.stringify(selectedDeviceData));
        navigate('/partnermanagement/deviceProviderServices/viewDeviceDetails')
    }

    //This part related to Pagination Logic
    let tableRows = filteredDevicesList.slice(firstIndex, firstIndex + (selectedRecordsPerPage));

    const styleForTitle = {
        backArrowIcon: "!mt-[5%]"
    }
    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <div className={`flex justify-end max-w-7xl mb-5 mt-2 absolute ${isLoginLanguageRTL ? "left-0" : "right-2"}`}>
                            <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 z-10">
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                            </div>
                        </div>
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between mb-5">
                            <Title title={`${t('deviceProviderServices.listOfDevices')} ${selectedSbidata.sbiVersion}`} backLink='/partnermanagement/deviceProviderServices/sbiList' styleSet={styleForTitle}></Title>
                            {devicesList.length > 0 ?
                                <button onClick={() => addDevices()} type="button" disabled={!canAddDevices}
                                className={`h-10 text-sm font-semibold px-7  rounded-md ${canAddDevices ? "bg-tory-blue text-white" : "bg-gray-400 opacity-55"}`}>
                                    {t('devicesList.addDevices')}
                                </button>
                                : null
                            }
                        </div>

                        {devicesList.length === 0
                            ?
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                {
                                    <div className="flex justify-between py-2 pt-4 text-sm font-semibold text-[#6F6E6E]">
                                        <div className={`flex w-full justify-between`}>
                                            <h6 className="px-2 mx-2">{t('devicesList.deviceType')}</h6>
                                            <h6 className="px-2 mx-2">{t('devicesList.deviceSubType')}</h6>
                                            <h6 className="px-2 mx-2">{t('devicesList.make')}</h6>
                                            <h6 className="px-2 mx-2">{t('devicesList.model')}</h6>
                                            <h6 className="px-2 mx-2">{t('devicesList.createdDate')}</h6>
                                            <h6 className="px-2 mx-2">{t('devicesList.status')}</h6>
                                            <h6 className="px-2 mx-2 text-center">{t('devicesList.action')}</h6>
                                        </div>
                                    </div>
                                }

                                <hr className="h-px mx-3 bg-gray-200 border-0" />

                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center">
                                        <img src={rectangleGrid} alt="" />
                                        <button onClick={() => addDevices()} type="button" disabled={!canAddDevices}
                                            className={`font-semibold mt-8 rounded-md text-sm mx-8 py-3 ${canAddDevices ? "bg-tory-blue text-white" : "bg-gray-400 opacity-55"}`}>
                                            {t('devicesList.addDevices')}
                                        </button>
                                    </div>
                                </div>
                            </div>
                            :
                            <>
                                <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg">
                                    <FilterButtons listTitle='devicesList.listOfDevices' dataList={filteredDevicesList} filter={filter} onResetFilter={onResetFilter} setFilter={setFilter}></FilterButtons>
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
                                                            <th key={index} className={`py-4 px-2 text-xs text-[#6F6E6E] lg:w-[14%]  ${header.id === "approvalStatus" && '!w-[10%]'}`}>
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
                                                            <tr key={index} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words ${device.isActive === false ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                                                <td onClick={() => showDeviceDetails(device)} className="px-2 mx-2">{device.deviceTypeCode}</td>
                                                                <td onClick={() => showDeviceDetails(device)} className="px-2 mx-2">{device.deviceSubTypeCode}</td>
                                                                <td onClick={() => showDeviceDetails(device)} className="px-2 mx-2">{device.make}</td>
                                                                <td onClick={() => showDeviceDetails(device)} className="px-2 mx-2">{device.model}</td>
                                                                <td onClick={() => showDeviceDetails(device)} className="px-2 mx-2">{formatDate(device.crDtimes, 'date')}</td>
                                                                <td onClick={() => showDeviceDetails(device)} className="px-2 mx-2">
                                                                    <div className={`${bgOfStatus(device.approvalStatus)} flex w-fit py-1.5 px-2 my-3 text-xs font-semibold rounded-md`}>
                                                                        {getStatusCode(device.approvalStatus, t)}
                                                                    </div>
                                                                </td>
                                                                <td className="px-2 mx-2">
                                                                    <div className="flex items-center justify-center relative" ref={el => submenuRef.current[index] = el}>
                                                                        <p onClick={() => setViewDeviceId(index === viewDeviceId ? null : index)} className="font-semibold mb-0.5 cursor-pointer text-[#1447B2]"
                                                                            tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setViewDeviceId(index === viewDeviceId ? null : index))}>
                                                                            ...</p>
                                                                        {viewDeviceId === index && (
                                                                            <div className={`absolute w-[7%] ${currentArray.length - 1 === index ? '-bottom-2' : currentArray.length - 2 === index ? '-bottom-2' : 'top-7'}  z-20 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-10 text-left"}`}>
                                                                                <p onClick={() => showDeviceDetails(device)} className={`py-2 px-4 cursor-pointer text-[#3E3E3E] hover:bg-gray-100 ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showDeviceDetails(device))}>
                                                                                    {t('devicesList.view')}
                                                                                </p>
                                                                                <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                {device.isActive !== false &&
                                                                                    (
                                                                                        <p onClick={() => console.log("deactivate", device)} className={`py-2 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} text-crimson-red cursor-pointer hover:bg-gray-100`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => console.log(""))}>
                                                                                            {t('devicesList.deActivate')}
                                                                                        </p>
                                                                                    )
                                                                                }

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
                                <Pagination dataList={filteredDevicesList} selectedRecordsPerPage={selectedRecordsPerPage} setSelectedRecordsPerPage={setSelectedRecordsPerPage} setFirstIndex={setFirstIndex}></Pagination>
                            </>
                        }
                    </div>
                </>
            )}
        </div>
    )
}

export default DevicesList;