import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL } from '../../utils/AppUtils';
import { AiFillLeftCircle, AiFillRightCircle } from "react-icons/ai"; // icons form react-icons
import { IconContext } from "react-icons"; // for customizing icons
import backArrow from '../../svg/back_arrow.svg';
import rectangleGrid from '../../svg/rectangle_grid.svg';
import ReactPaginate from 'react-paginate';
import CopyIdPopUp from './CopyIdPopUp';
import OidcClientsFilter from './OidcClientsFilter';
import PoliciesFilter from '../policies/PoliciesFilter';

function OidcClientsList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [activeOidcClient, setActiveOicdClient] = useState(true);
    const [activeApiKey, setActiveApiKey] = useState(false);
    const [filter, setFilter] = useState(false);
    const [isData, setIsData] = useState(true);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(5);
    const [order, setOrder] = useState("ASC");
    const [activeSortAsc, setActiveSortAsc] = useState("");
    const [activeSortDesc, setActiveSortDesc] = useState("");
    const [isDescending, setIsDescending] = useState(true);
    const [showPopup, setShowPopup] = useState(false);
    const [firstIndex, setFirstIndex] = useState(0);
    const itemsPerPageOptions = [5, 10, 15, 20];
    const [isItemsPerPageOpen, setIsItemsPerPageOpen] = useState(false);
    const [viewClientId, setViewClientId] = useState(-1);
    const defaultFilterQuery = {
        partnerId: "",
        policyGroupName: ""
    };
    const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });

    const tableValues = [
        { "partnerId": "P28394091", "policyGroup": "Policy Group 01", "policyName": "Full KYC", "oidcClientName": "Client 13", "createdDate": "11/10/2025", "status": "Approved", "oidcClientId": "1" },
        { "partnerId": "P28394092", "policyGroup": "Policy Group 02", "policyName": "KYC", "oidcClientName": "Client 22", "createdDate": "21/10/2025", "status": "Deactivated", "oidcClientId": "0" },
        { "partnerId": "P28394093", "policyGroup": "Policy Group 03", "policyName": "KYC1", "oidcClientName": "Client 11", "createdDate": "06/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
        { "partnerId": "P28394094", "policyGroup": "Policy Group 04", "policyName": "Full KYC", "oidcClientName": "Client 03", "createdDate": "30/09/2025", "status": "Approved", "oidcClientId": "1" },
        { "partnerId": "P28394095", "policyGroup": "Policy Group 05", "policyName": "KYC1", "oidcClientName": "Client 05", "createdDate": "12/10/2025", "status": "Rejected", "oidcClientId": "0" },
        { "partnerId": "P28394096", "policyGroup": "Policy Group 06", "policyName": "KYC", "oidcClientName": "Client 16", "createdDate": "07/10/2025", "status": "Deactivated", "oidcClientId": "0" },
        { "partnerId": "P28394097", "policyGroup": "Policy Group 07", "policyName": "Full KYC", "oidcClientName": "Client 07", "createdDate": "01/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
        { "partnerId": "P28394098", "policyGroup": "Policy Group 08", "policyName": "KYC", "oidcClientName": "Client 04", "createdDate": "17/10/2025", "status": "Approved", "oidcClientId": "1" },
        { "partnerId": "P28394099", "policyGroup": "Policy Group 09", "policyName": "KYC1", "oidcClientName": "Client 12", "createdDate": "13/10/2025", "status": "Deactivated", "oidcClientId": "0" },
        { "partnerId": "P28394100", "policyGroup": "Policy Group 10", "policyName": "KYC", "oidcClientName": "Client 09", "createdDate": "02/10/2025", "status": "Rejected", "oidcClientId": "0" },
        { "partnerId": "P28394101", "policyGroup": "Policy Group 11", "policyName": "Full KYC", "oidcClientName": "Client 02", "createdDate": "08/10/2025", "status": "Approved", "oidcClientId": "1" },
        { "partnerId": "P28394102", "policyGroup": "Policy Group 12", "policyName": "KYC", "oidcClientName": "Client 06", "createdDate": "18/10/2025", "status": "Deactivated", "oidcClientId": "0" },
        { "partnerId": "P28394103", "policyGroup": "Policy Group 13", "policyName": "KYC", "oidcClientName": "Client 01", "createdDate": "14/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
        { "partnerId": "P28394104", "policyGroup": "Policy Group 14", "policyName": "Full KYC", "oidcClientName": "Client 10", "createdDate": "03/10/2025", "status": "Approved", "oidcClientId": "1" },
        { "partnerId": "P28394105", "policyGroup": "Policy Group 15", "policyName": "KYC1", "oidcClientName": "Client 08", "createdDate": "09/10/2025", "status": "Rejected", "oidcClientId": "0" },
        { "partnerId": "P28394106", "policyGroup": "Policy Group 16", "policyName": "Full KYC", "oidcClientName": "Client 20", "createdDate": "19/10/2025", "status": "Approved", "oidcClientId": "1" },
        { "partnerId": "P28394107", "policyGroup": "Policy Group 17", "policyName": "KYC", "oidcClientName": "Client 17", "createdDate": "04/10/2025", "status": "Approved", "oidcClientId": "1" },
        { "partnerId": "P28394108", "policyGroup": "Policy Group 18", "policyName": "Full KYC", "oidcClientName": "Client 14", "createdDate": "15/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
        { "partnerId": "P28394109", "policyGroup": "Policy Group 19", "policyName": "KYC", "oidcClientName": "Client 19", "createdDate": "10/10/2025", "status": "Approved", "oidcClientId": "1" },
        { "partnerId": "P28394110", "policyGroup": "Policy Group 20", "policyName": "Full KYC", "oidcClientName": "Client 18", "createdDate": "20/10/2025", "status": "Approved", "oidcClientId": "1" },
        { "partnerId": "P28394111", "policyGroup": "Policy Group 21", "policyName": "KYC1", "oidcClientName": "Client 21", "createdDate": "05/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
        { "partnerId": "P28394112", "policyGroup": "Policy Group 22", "policyName": "Full KYC", "oidcClientName": "Client 15", "createdDate": "16/10/2025", "status": "Rejected", "oidcClientId": "0" }

    ];

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'oidcClientsList.partnerId' },
        { id: "policyGroup", headerNameKey: "oidcClientsList.policyGroup" },
        { id: "policyName", headerNameKey: "oidcClientsList.policyName" },
        { id: "oidcClientName", headerNameKey: "oidcClientsList.oidcClientName" },
        { id: "createdDate", headerNameKey: "oidcClientsList.createdDate" },
        { id: "status", headerNameKey: "oidcClientsList.status" },
        { id: "oidcClientId", headerNameKey: "oidcClientsList.oidcClientId" },
        { id: "action", headerNameKey: 'oidcClientsList.action' }
    ];
    
    const [oidcClientsList, setOidcClientsList] = useState(tableValues);
    const [filteredOidcClientsList, setFilteredOidcClientsList] = useState(oidcClientsList);

    const moveToHome = () => {
        navigate('/partnermanagement')
    };

    const createOidcClient = () => {
        navigate('/partnermanagement/createOidcClient')
    }

    function bgOfStatus(status) {
        if (status === "Approved") {
            return ("bg-[#D1FADF] text-[#155E3E]")
        }
        else if (status === "Rejected") {
            return ("bg-[#FAD6D1] text-[#5E1515]")
        }
        else if (status === "Pending for Approval") {
            return ("bg-[#FEF1C6] text-[#6D1C00]")
        }
        else if (status === "Deactivated") {
            return ("bg-[#EAECF0] text-[#525252]")
        }
    };

    const showCopyPopUp = (status) => {
        if (status.toLowerCase() === "approved") {
            setShowPopup(true);
        }
    };

    //This part is related to Filter
    const onFilterChange = (fieldName, selectedFilter) => {
        setFilterQuery(oldFilterQuery => ({
            ...oldFilterQuery,
            [fieldName]: selectedFilter
        }));
    }
    useEffect(() => {
        let filteredRows = oidcClientsList;
        Object.keys(filterQuery).forEach(key => {
            //console.log(`${key} : ${filterQuery[key]}`);
            if (filterQuery[key] !== '') {
                filteredRows = filteredRows.filter(item => item[key] === filterQuery[key]);
            }
        });
        setFilteredOidcClientsList(filteredRows);
        setFirstIndex(0);
    }, [filterQuery]);

    //This part is related to Sorting
    const toggleSortDescOrder = (sortItem) => {
        if (order === 'ASC') {
            if (sortItem === "createdDate") {
                const sortedOidcClients = [...tableValues].sort((a, b) => {
                    const dateA = new Date(a.createdDate);
                    const dateB = new Date(b.createdDate);
                    return isDescending ? dateA - dateB : dateB - dateA;
                });
                setFilteredOidcClientsList(sortedOidcClients);
                setOrder("DESC")
                setIsDescending(!isDescending);
                setActiveSortDesc(sortItem);
                setActiveSortAsc(sortItem);
            }
            else {
                const sortedOidcClients = [...tableValues].sort((a, b) =>
                    a[sortItem].toLowerCase() > b[sortItem].toLowerCase() ? 1 : -1
                );
                setFilteredOidcClientsList(sortedOidcClients);
                setOrder("DESC")
                setActiveSortDesc(sortItem);
                setActiveSortAsc(sortItem);
            }
        }
    }
    const toggleSortAscOrder = (sortItem) => {
        if (order === 'DESC') {
            if (sortItem === "createDate") {
                const sortedOidcClients = [...tableValues].sort((a, b) => {
                    const dateA = new Date(a.createdDate);
                    const dateB = new Date(b.createdDate);
                    return isDescending ? dateA - dateB : dateB - dateA;
                });

                setFilteredOidcClientsList(sortedOidcClients);
                setOrder("ASC")
                setIsDescending(!isDescending);
                setActiveSortDesc(sortItem);
                setActiveSortAsc(sortItem);
            }
            else {
                const sortedOidcClients = [...tableValues].sort((a, b) =>
                    a[sortItem].toLowerCase() < b[sortItem].toLowerCase() ? 1 : -1
                );
                setFilteredOidcClientsList(sortedOidcClients);
                setOrder("ASC")
                setActiveSortDesc(sortItem);
                setActiveSortAsc(sortItem);
            }
        }
    };

    //This part related to Pagination Logic
    let tableRows = filteredOidcClientsList.slice(firstIndex, firstIndex + (selectedRecordsPerPage));

    const handlePageChange = (event) => {
        const newIndex = (event.selected * selectedRecordsPerPage) % filteredOidcClientsList.length;
        setFirstIndex(newIndex);
    };
    const changeItemsPerPage = (num) => {
        setIsItemsPerPageOpen(false);
        setSelectedRecordsPerPage(num);
        setFirstIndex(0);
    };

    return (
        <div className={`mt-5 w-[100%] ${isLoginLanguageRTL ? "mr-32 ml-5" : "ml-32 mr-5"} overflow-x-scroll font-inter`}>
            <div className="flex-col">
                <div className="flex justify-between mb-5">
                    <div className={`flex gap-x-3`}>
                        <img src={backArrow} alt="" onClick={() => moveToHome()} className={`cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} />
                        <div className="flex-col mt-[3%]">
                            <h1 className="font-semibold text-xl text-dark-blue">{t('authenticationServices.authenticationServices')}</h1>
                            <p onClick={() => moveToHome()} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                {t('commons.home')}
                            </p>
                        </div>
                    </div>
                </div>
                <div className='flex text-xs bg-[#FCFCFC] font-bold space-x-16 items-start rounded-lg px-[1.5%] pt-[2%]'>
                    <div className={`flex-col justify-center`}>
                        <h6 onClick={() => { setActiveOicdClient(true); setActiveApiKey(false) }}
                            className={`${activeOidcClient ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] ${isLoginLanguageRTL ? "mr-[10%]" : "ml-[10%]"} cursor-pointer text-sm`}>
                            {t('authenticationServices.oidcClient')}
                        </h6>
                        <div className={`h-1 w-24 ${activeOidcClient ? "bg-tory-blue" : "bg-transparent"}  rounded-t-md`}></div>
                    </div>
                    <div className={`flex-col justify-center`}>
                        <h6 className={`${activeApiKey ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] ${isLoginLanguageRTL ? "mr-[20%]" : "ml-[20%]"} cursor-pointer text-sm`}>
                            {t('authenticationServices.apiKey')}
                        </h6>
                        <div className={`h-1 w-24 ${activeApiKey ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
                    </div>
                </div>

                {!isData
                    ?
                    <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                        {
                            activeOidcClient && (<div className="flex justify-between py-2 pt-4 text-sm font-medium text-[#6F6E6E]">
                                <div className={`flex gap-x-28 ${isLoginLanguageRTL ? "mr-6" : "ml-6"}`}>
                                    <h6>{t('authenticationServices.partnerId')}</h6>
                                    <h6>{t('authenticationServices.policyName')}</h6>
                                </div>
                                <h6 className={`${isLoginLanguageRTL ? "mr-24" : "ml-24"}`}>{t('authenticationServices.oidcClientName')}</h6>
                                <div className="flex space-x-16">
                                    <h6>{t('authenticationServices.createdDate')}</h6>
                                    <h6>{t('authenticationServices.status')}</h6>
                                </div>
                                <div className={`flex space-x-12 ${isLoginLanguageRTL ? "ml-10" : "mr-10"}`}>
                                    <h6>{t('authenticationServices.oidcClientId')}</h6>
                                    <h6>{t('authenticationServices.action')}</h6>
                                </div>
                            </div>)
                        }

                        <hr className="h-px mx-3 bg-gray-200 border-0" />

                        <div className="flex items-center justify-center p-24">
                            <div className="flex-col justify-center">
                                <img src={rectangleGrid} alt="" />
                                {activeOidcClient &&
                                    (<button onClick={() => createOidcClient()} type="button"
                                        className={`text-white font-semibold mt-8 mr-5 bg-tory-blue rounded-md text-base px-4 py-3`}>
                                        {t('authenticationServices.createOidcClientBtn')}
                                    </button>)
                                }
                            </div>
                        </div>
                    </div>
                    :
                    <>
                        <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg">
                            <div className="flex w-full p-2">
                                <div className="flex w-full pl-[2%] pt-[1%] items-center justify-start font-semibold text-dark-blue text-lg" >
                                    {t('oidcClientsList.listOfOidcClientRequests')}
                                </div>
                                <div className="w-full flex justify-end relative ">
                                    <button type="button" onClick={() => createOidcClient()}
                                        className="flex justify-center items-center text-sm py-2 px-2 font-semibold text-center text-white bg-tory-blue rounded-md">
                                        {t('oidcClientsList.createOidcClient')}
                                    </button>
                                    <button onClick={() => setFilter(!filter)} type="button" className={`flex justify-center items-center w-[23%] text-sm py-2  text-tory-blue border border-[#1447B2] font-semibold rounded-md text-center
                                        ${filter ? 'bg-tory-blue text-white' : 'text-tory-blue bg-white'} ${isLoginLanguageRTL ? "mr-3" : "ml-3"}`}>
                                        {t('oidcClientsList.filterBtn')}
                                        <svg
                                            xmlns="http://www.w3.org/2000/svg" className={`${filter ? 'rotate-180 text-white duration-700' : null} ${isLoginLanguageRTL ? "mr-2" : "ml-2"}`}
                                            width="10" height="8" viewBox="0 0 10 8">
                                            <path id="Polygon_8"
                                                data-name="Polygon 8"
                                                d="M3.982,1.628a1.2,1.2,0,0,1,2.035,0L8.853,6.164A1.2,1.2,0,0,1,7.835,8H2.165A1.2,1.2,0,0,1,1.147,6.164Z"
                                                transform="translate(10 8) rotate(180)" fill={`${filter ? '#ffff' : '#1447b2'}`} />
                                        </svg>
                                    </button>
                                </div>
                            </div>
                            <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                            {filter &&
                                <OidcClientsFilter
                                    filteredOidcClientsList={filteredOidcClientsList}
                                    onFilterChange={onFilterChange}>
                                </OidcClientsFilter>
                            }
                            <div className="mx-[2%]">
                                <table className="table-fixed">
                                    <thead>
                                        <tr>
                                            {tableHeaders.map((header, index) => {
                                                return (
                                                    <th key={index} className="py-4 text-sm font-medium text-[#6F6E6E] lg:w-[14%]">
                                                        <div className="flex gap-x-1 items-center">
                                                            {t(header.headerNameKey)}
                                                            {header.id !== "action" && (
                                                                <div>
                                                                    <svg className="cursor-pointer mb-0.5" onClick={() => toggleSortAscOrder(header.id)} alt="Ascending"
                                                                        xmlns="http://www.w3.org/2000/svg"
                                                                        width="8" height="8" viewBox="0 0 7 6">
                                                                        <path id="Polygon_3" data-name="Polygon 3" d="M2.636,1.481a1,1,0,0,1,1.728,0L6.123,4.5A1,1,0,0,1,5.259,6H1.741A1,1,0,0,1,.877,4.5Z"
                                                                            fill={`${(activeSortDesc === header.id && order === "ASC") ? "#1447b2" : "#969696"}`} />
                                                                    </svg>
                                                                    <svg className="cursor-pointer" onClick={() => toggleSortDescOrder(header.id)} alt="Descending"
                                                                        xmlns="http://www.w3.org/2000/svg"
                                                                        width="8" height="8" viewBox="0 0 7 6">
                                                                        <path id="Polygon_4" data-name="Polygon 4" d="M2.636,1.481a1,1,0,0,1,1.728,0L6.123,4.5A1,1,0,0,1,5.259,6H1.741A1,1,0,0,1,.877,4.5Z"
                                                                            transform="translate(7 6) rotate(180)" fill={`${(activeSortAsc === header.id && order === "DESC") ? "#1447b2" : "#969696"}`} />
                                                                    </svg>
                                                                </div>
                                                            )}
                                                        </div>
                                                    </th>
                                                )
                                            })}
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {
                                            tableRows.map((client, index) => {
                                                return (
                                                    <tr key={index} className={`border-t-2 cursor-pointer text-sm text-[#191919] font-medium ${client.status.toLowerCase() === "deactivated" ? "text-[#969696]" : "text-[#191919]"}`}>
                                                        <td className="px-2">{client.partnerId}</td>
                                                        <td className="px-2">{client.policyGroup}</td>
                                                        <td className="px-2">{client.policyName}</td>
                                                        <td className="px-2">{client.oidcClientName}</td>
                                                        <td className="px-2">{client.createdDate}</td>
                                                        <td className="">
                                                            <div className={`${bgOfStatus(client.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-medium rounded-md`}>
                                                                {client.status}
                                                            </div>
                                                        </td>
                                                        <td className="pl-[2%]">
                                                            <svg onClick={() => showCopyPopUp(client.status)}
                                                                xmlns="http://www.w3.org/2000/svg" width="22.634" height="15.433" viewBox="0 0 22.634 15.433">
                                                                <path id="visibility_FILL0_wght400_GRAD0_opsz48"
                                                                    d="M51.32-787.911a4.21,4.21,0,0,0,3.1-1.276,4.225,4.225,0,0,0,1.273-3.1,4.21,4.21,0,0,0-1.276-3.1,4.225,4.225,0,0,0-3.1-1.273,4.21,4.21,0,0,0-3.1,1.276,4.225,4.225,0,0,0-1.273,3.1,4.21,4.21,0,0,0,1.276,3.1A4.225,4.225,0,0,0,51.32-787.911Zm-.009-1.492a2.764,2.764,0,0,1-2.039-.842,2.794,2.794,0,0,1-.836-2.045,2.764,2.764,0,0,1,.842-2.039,2.794,2.794,0,0,1,2.045-.836,2.764,2.764,0,0,1,2.039.842,2.794,2.794,0,0,1,.836,2.045,2.764,2.764,0,0,1-.842,2.039A2.794,2.794,0,0,1,51.311-789.4Zm.006,4.836a11.528,11.528,0,0,1-6.79-2.135A13,13,0,0,1,40-792.284a13.006,13.006,0,0,1,4.527-5.582A11.529,11.529,0,0,1,51.317-800a11.529,11.529,0,0,1,6.79,2.135,13.006,13.006,0,0,1,4.527,5.582,13,13,0,0,1-4.527,5.581A11.528,11.528,0,0,1,51.317-784.568ZM51.317-792.284Zm0,6.173A10.351,10.351,0,0,0,57.04-787.8a10.932,10.932,0,0,0,3.974-4.488,10.943,10.943,0,0,0-3.97-4.488,10.33,10.33,0,0,0-5.723-1.685,10.351,10.351,0,0,0-5.727,1.685,11.116,11.116,0,0,0-4,4.488,11.127,11.127,0,0,0,4,4.488A10.33,10.33,0,0,0,51.313-786.111Z"
                                                                    transform="translate(-40 800)" fill={`${client.status === 'Approved' ? "#1447B2" : "#D1D1D1"}`} />
                                                            </svg>
                                                        </td>
                                                        {showPopup && (
                                                            <CopyIdPopUp closePopUp={setShowPopup} partnerId={client.partnerId} policyName={client.policyName} />
                                                        )}
                                                        <td className="text-center">
                                                            <div>
                                                                <p onClick={() => setViewClientId(index)} className={`${isLoginLanguageRTL ? "ml-9" : "mr-9"} font-semibold mb-0.5 cursor-pointer`}>...</p>
                                                                {viewClientId === index && (
                                                                    <div className={`absolute ${isLoginLanguageRTL ? "mr-16" : null} bg-white text-xs font-medium rounded-lg shadow-md border ${isLoginLanguageRTL ? "left-20" : "right-20"}`}>
                                                                        <p className="px-4 py-2 cursor-pointer">
                                                                            {t('oidcClientsList.view')}
                                                                        </p>
                                                                        <p className="px-5 py-2 cursor-pointer">
                                                                            {t('oidcClientsList.edit')}
                                                                        </p>
                                                                        <p className="px-5 py-2 cursor-pointer text-red-700">
                                                                            {t('oidcClientsList.deActivate')}
                                                                        </p>
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
                        <div className="flex justify-between bg-[#FCFCFC] items-center h-9  mt-0.5 p-8 rounded-b-md shadow-md">
                            <div></div>
                            <ReactPaginate
                                containerClassName={"pagination"}
                                pageClassName={"page-item"}
                                activeClassName={"active"}
                                onPageChange={(event) => handlePageChange(event)}
                                pageCount={Math.ceil(filteredOidcClientsList.length / selectedRecordsPerPage)}
                                breakLabel="..."
                                previousLabel={
                                    <IconContext.Provider value={{ color: "#B8C1CC", size: "25px" }}>
                                        {isLoginLanguageRTL ? <AiFillRightCircle /> : <AiFillLeftCircle />}
                                    </IconContext.Provider>
                                }
                                nextLabel={
                                    <IconContext.Provider value={{ color: "#B8C1CC", size: "25px" }}>
                                        {isLoginLanguageRTL ? <AiFillLeftCircle /> : <AiFillRightCircle />}
                                    </IconContext.Provider>
                                }
                            />
                            <div className="flex items-center gap-x-3">
                                <h6 className="text-gray-500 text-xs">{t('policies.itemsPerPage')}</h6>
                                <div>
                                    <div className="cursor-pointer flex justify-between w-10 h-6 items-center text-xs border px-1 rounded-md border-[#1447b2] bg-white text-tory-blue font-medium"
                                        onClick={() => setIsItemsPerPageOpen(!isItemsPerPageOpen)}>
                                        <p>
                                            {selectedRecordsPerPage}
                                        </p>
                                        <svg className={`${isItemsPerPageOpen ? "rotate-180" : null}`}
                                            xmlns="http://www.w3.org/2000/svg"
                                            width="10.359" height="5.697" viewBox="0 0 11.359 6.697">
                                            <path id="expand_more_FILL0_wght400_GRAD0_opsz48"
                                                d="M17.68,23.3,12,17.618,13.018,16.6l4.662,4.686,4.662-4.662,1.018,1.018Z"
                                                transform="translate(-12 -16.6)" fill="#1447b2" />
                                        </svg>
                                    </div>
                                    {isItemsPerPageOpen && (
                                        <div className="absolute bg-white text-xs text-tory-blue font-medium rounded-b-lg shadow-md">
                                            {itemsPerPageOptions.map((num, i) => {
                                                return (
                                                    <p key={i} onClick={() => changeItemsPerPage(num)}
                                                        className="px-3 py-2 cursor-pointer hover:bg-gray-200">
                                                        {num}
                                                    </p>
                                                )
                                            })
                                            }
                                        </div>
                                    )}
                                </div>
                            </div>
                        </div>
                    </>
                }
            </div>
        </div>
    )
}

export default OidcClientsList; 