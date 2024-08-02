import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import {
    isLangRTL, handleServiceErrors, getPartnerManagerUrl, formatDate, getStatusCode,
    handleMouseClickForDropdown, toggleSortDescOrder, toggleSortAscOrder, createRequest, bgOfStatus,
    onPressEnterKey
} from '../../utils/AppUtils';
import { HttpService } from '../../services/HttpService';
import ErrorMessage from '../common/ErrorMessage';
import LoadingIcon from "../common/LoadingIcon";
import rectangleGrid from '../../svg/rectangle_grid.svg';
import DeactivatePopup from '../common/DeactivatePopup.js';
import FilterButtons from '../common/FilterButtons.js';
import SortingIcon from '../common/SortingIcon.js';
import Pagination from '../common/Pagination.js';
import Title from '../common/Title.js';
import ViewDevicesFilter from './viewDevicesFilter.js';

function ViewDevices() {
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
    const [showDeactivatePopup, setShowDeactivatePopup] = useState(false);
    const defaultFilterQuery = {
        deviceType: "",
        deviceSubType: ""
    };
    const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setViewDeviceId(-1));
    }, [submenuRef]);

    useEffect(() => {
        const fetchData = async () => {

            setDataLoaded(false);
            const response = [
                {
                    "id": "46146",
                    "deviceProviderId": "mayuradevice",
                    "deviceType": "Finger",
                    "deviceSubType": "Slap",
                    "make": "MOSIP48",
                    "model": "XYZS",
                    "partnerOrganizationName": "ABCDEF",
                    "isActive": true,
                    "status": "approved",
                    "crDtimes": "2022-07-01T00:00:00.000+00:00"
                },
                {
                    "id": "64895",
                    "deviceProviderId": "mayuradevice",
                    "deviceType": "Finger",
                    "deviceSubType": "Slap",
                    "make": "MOSIP123",
                    "model": "XYZE",
                    "partnerOrganizationName": "ABCDEF",
                    "isActive": true,
                    "status": "approved",
                    "crDtimes": "2023-01-01T00:00:00.000+00:00"
                },
                {
                    "id": "mdevice_detail_default_finger",
                    "deviceProviderId": "MOSIP.PROXY.SBI",
                    "deviceType": "Finger",
                    "deviceSubType": "Slap",
                    "make": "MOSIP",
                    "model": "SLAP01",
                    "partnerOrganizationName": "MOSIP",
                    "isActive": true,
                    "status": "rejected",
                    "crDtimes": "2025-01-01T00:00:00.000+00:00"
                },
                {
                    "id": "70913",
                    "deviceProviderId": "Tech-123",
                    "deviceType": "Iris",
                    "deviceSubType": "Single",
                    "make": "abcde",
                    "model": "FRO90000",
                    "partnerOrganizationName": "Techno",
                    "isActive": false,
                    "status": "pending_approval",
                    "crDtimes": "2020-01-01T00:00:00.000+00:00"
                },
                {
                    "id": "70913",
                    "deviceProviderId": "Tech-123",
                    "deviceType": "Iris",
                    "deviceSubType": "Single",
                    "make": "abcde",
                    "model": "FRO90000",
                    "partnerOrganizationName": "Techno",
                    "isActive": false,
                    "status": "inactive",
                    "crDtimes": "2020-01-01T00:00:00.000+00:00"
                },
            ]

            const sortedData = response.sort((a, b) => new Date(b.crDtimes) - new Date(a.crDtimes));
            setDevicesList(sortedData);
            setFilteredDevicesList(sortedData);
            setDataLoaded(true);

        };
        fetchData();
    }, []);

    const tableHeaders = [
        { id: "deviceType", headerNameKey: 'viewDevices.deviceType' },
        { id: "deviceSubType", headerNameKey: "viewDevices.deviceSubType" },
        { id: "make", headerNameKey: "viewDevices.make" },
        { id: "model", headerNameKey: "viewDevices.model" },
        { id: "crDtimes", headerNameKey: "viewDevices.createdDate" },
        { id: "status", headerNameKey: "viewDevices.status" },
        { id: "action", headerNameKey: 'viewDevices.action' }
    ];

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const addDevice = () => {
        console.log('addDevice');
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
        const isDateCol = (header === "crDtimes") ? true : false;
        toggleSortAscOrder(header, isDateCol, filteredDevicesList, setFilteredDevicesList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
    }

    const sortDescOrder = (header) => {
        const isDateCol = (header === "crDtimes") ? true : false;
        toggleSortDescOrder(header, isDateCol, filteredDevicesList, setFilteredDevicesList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
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
                            <Title title='deviceProviderServices.listOfSbisAndDevices' backLink='/partnermanagement/deviceProviderServices/sbiList' styleSet={styleForTitle}></Title>
                            {devicesList.length > 0 ?
                                <button onClick={() => addDevice()} type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                                    {t('viewDevices.addDevice')}
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
                                            <h6 className="px-2 mx-2">{t('viewDevices.deviceType')}</h6>
                                            <h6 className="px-2 mx-2">{t('viewDevices.deviceSubType')}</h6>
                                            <h6 className="px-2 mx-2">{t('viewDevices.make')}</h6>
                                            <h6 className="px-2 mx-2">{t('viewDevices.model')}</h6>
                                            <h6 className="px-2 mx-2">{t('viewDevices.createdDate')}</h6>
                                            <h6 className="px-2 mx-2">{t('viewDevices.status')}</h6>
                                            <h6 className="px-2 mx-2 text-center">{t('viewDevices.action')}</h6>
                                        </div>
                                    </div>
                                }

                                <hr className="h-px mx-3 bg-gray-200 border-0" />

                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center">
                                        <img src={rectangleGrid} alt="" />
                                        <button onClick={() => addDevice()} type="button"
                                            className={`text-white font-semibold mt-8 bg-tory-blue rounded-md text-sm mx-8 py-3`}>
                                            {t('viewDevices.addDevice')}
                                        </button>
                                    </div>
                                </div>
                            </div>
                            :
                            <>
                                <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg">
                                    <FilterButtons listTitle='viewDevices.listOfDevices' dataList={filteredDevicesList} filter={filter} onResetFilter={onResetFilter} setFilter={setFilter}></FilterButtons>
                                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                    {filter &&
                                        <ViewDevicesFilter
                                            filteredDevicesList={filteredDevicesList}
                                            onFilterChange={onFilterChange}>
                                        </ViewDevicesFilter>
                                    }
                                    <div className="mx-[2%] overflow-x-scroll">
                                        <table className="table-fixed">
                                            <thead>
                                                <tr>
                                                    {tableHeaders.map((header, index) => {
                                                        return (
                                                            <th key={index} className={`py-4 px-2 text-xs text-[#6F6E6E] lg:w-[14%]  ${header.id === "status" && '!w-[10%]'}`}>
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
                                                            <tr key={index} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words ${device.status.toLowerCase() === "inactive" ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                                                <td onClick={() => console.log('show device details', device)} className="px-2 mx-2">{device.deviceType}</td>
                                                                <td onClick={() => console.log('show device details', device)} className="px-2 mx-2">{device.deviceSubType}</td>
                                                                <td onClick={() => console.log('show device details', device)} className="px-2 mx-2">{device.make}</td>
                                                                <td onClick={() => console.log('show device details', device)} className="px-2 mx-2">{device.model}</td>
                                                                <td onClick={() => console.log('show device details', device)} className="px-2 mx-2">{formatDate(device.crDtimes, 'date')}</td>
                                                                <td onClick={() => console.log('show device details', device)} className="px-2 mx-2">
                                                                    <div className={`${bgOfStatus(device.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-semibold rounded-md`}>
                                                                        {getStatusCode(device.status, t)}
                                                                    </div>
                                                                </td>
                                                                <td className="px-2 mx-2">
                                                                    <div className="flex items-center justify-center relative" ref={el => submenuRef.current[index] = el}>
                                                                        <p onClick={() => setViewDeviceId(index === viewDeviceId ? null : index)} className="font-semibold mb-0.5 cursor-pointer text-[#1447B2]"
                                                                            tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setViewDeviceId(index === viewDeviceId ? null : index))}>
                                                                            ...</p>
                                                                        {viewDeviceId === index && (
                                                                            <div className={`absolute w-[7%] ${currentArray.length - 1 === index ? '-bottom-2' : currentArray.length - 2 === index ? '-bottom-2' : 'top-7'}  z-20 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-10 text-left"}`}>
                                                                                <p onClick={() => console.log('view', device)} className={`py-2 px-4 cursor-pointer text-[#3E3E3E] hover:bg-gray-100 ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => console.log(""))}>
                                                                                    {t('viewDevices.view')}
                                                                                </p>
                                                                                <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                {device.status !== "inactive" &&
                                                                                    (
                                                                                        <p onClick={() => console.log("deactivate", device)} className={`py-2 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} text-crimson-red cursor-pointer hover:bg-gray-100`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => console.log(""))}>
                                                                                            {t('viewDevices.deActivate')}
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

export default ViewDevices;