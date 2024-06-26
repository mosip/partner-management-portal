import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, handleServiceErrors, getPartnerManagerUrl, formatDate, getStatusCode, 
    handleMouseClickForDropdown, toggleSortDescOrder, toggleSortAscOrder, moveToHome } from '../../utils/AppUtils';
import { HttpService } from '../../services/HttpService';
import ErrorMessage from '../common/ErrorMessage';
import LoadingIcon from "../common/LoadingIcon";
import { AiFillLeftCircle, AiFillRightCircle } from "react-icons/ai"; // icons form react-icons
import { IconContext } from "react-icons"; // for customizing icons
import backArrow from '../../svg/back_arrow.svg';
import rectangleGrid from '../../svg/rectangle_grid.svg';
import ReactPaginate from 'react-paginate';
import ApiClientsFilter from './ApiClientsFilter';
import ApiKeyIdPopup from './ApiKeyIdPopup';
import AuthenticationServicesTab from './AuthenticationServicesTab';

function ApiKeysList () {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [firstTimeLoad, setFirstTimeLoad] = useState(false);
    const [dataLoaded, setDataLoaded] = useState(false);
    const [activeOidcClient, setActiveOicdClient] = useState(false);
    const [activeApiKey, setActiveApiKey] = useState(true);
    const [filter, setFilter] = useState(false);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
    const [order, setOrder] = useState("ASC");
    const [activeSortAsc, setActiveSortAsc] = useState("");
    const [activeSortDesc, setActiveSortDesc] = useState("");
    const [isDescending, setIsDescending] = useState(true);
    const [apiKeysList, setApiKeysList] = useState([]);
    const [filteredApiKeysList, setFilteredApiKeysList] = useState([]);
    const [showPopup, setShowPopup] = useState(false);
    const [firstIndex, setFirstIndex] = useState(0);
    const itemsPerPageOptions = [8, 16, 24, 32];
    const [isItemsPerPageOpen, setIsItemsPerPageOpen] = useState(false);
    const [currentClient, setCurrentClient] = useState(null);
    const [viewApiKeyId, setViewApiKeyId] = useState(-1);
    const defaultFilterQuery = {
        partnerId: "",
        policyGroupName: ""
    };
    const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
    const submenuRef = useRef(null);
    const itemsCountSelectionRef = useRef(null);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setViewApiKeyId(null));
        handleMouseClickForDropdown(itemsCountSelectionRef, () => setIsItemsPerPageOpen(false));
    }, [submenuRef, itemsCountSelectionRef]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(getPartnerManagerUrl('/partners/getAllApiKeysForAuthPartners', process.env.NODE_ENV));
                setFirstTimeLoad(true);
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        const sortedData = resData.sort((a, b) => new Date(b.crDtimes) - new Date(a.crDtimes));
                        setApiKeysList(sortedData);
                        setFilteredApiKeysList(sortedData)
                        // console.log('Response data:', apiKeysList.length);
                    } else {
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('apiKeysList.errorInApiKeysList'));
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                setErrorMsg(err);
            }
        };
        fetchData();
    }, [firstTimeLoad, t]);

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'oidcClientsList.partnerId' },
        { id: "policyGroupName", headerNameKey: "oidcClientsList.policyGroup" },
        { id: "policyName", headerNameKey: "oidcClientsList.policyName" },
        { id: "apiKeyLabel", headerNameKey: "apiKeysList.apiKeyLabel" },
        { id: "crDtimes", headerNameKey: "oidcClientsList.createdDate" },
        { id: "status", headerNameKey: "oidcClientsList.status" },
        { id: "apiKeyReqID", headerNameKey: "apiKeysList.apiKeyId" },
        { id: "action", headerNameKey: 'oidcClientsList.action' }
    ];

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const generateApiKey = () => {
        navigate('/partnermanagement/authenticationServices/generateApiKey')
    };

    function bgOfStatus(status) {
        if (status === "ACTIVE") {
            return ("bg-[#D1FADF] text-[#155E3E]")
        }
        else if (status === "INACTIVE") {
            return ("bg-[#EAECF0] text-[#525252]")
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
        let filteredRows = apiKeysList;
        Object.keys(filterQuery).forEach(key => {
            //console.log(`${key} : ${filterQuery[key]}`);
            if (filterQuery[key] !== '') {
                filteredRows = filteredRows.filter(item => item[key] === filterQuery[key]);
            }
        });
        setFilteredApiKeysList(filteredRows);
        setFirstIndex(0);
    }, [filterQuery, apiKeysList]);

    const onResetFilter = () => {
        window.location.reload();
    }

    const sortAscOrder = (header) => {
        if (header === "crDtimes") {
            toggleSortAscOrder(header, true, filteredApiKeysList, setFilteredApiKeysList, order, setOrder, isDescending, setIsDescending, setActiveSortAsc, setActiveSortDesc);
        } else {
            toggleSortAscOrder(header, false, filteredApiKeysList, setFilteredApiKeysList, order, setOrder, isDescending, setIsDescending, setActiveSortAsc, setActiveSortDesc);
        }
    }

    const sortDescOrder = (header) => {
        if (header === "crDtimes") {
            toggleSortDescOrder(header, true, filteredApiKeysList, setFilteredApiKeysList, order, setOrder, isDescending, setIsDescending, setActiveSortAsc, setActiveSortDesc);
        } else {
            toggleSortDescOrder(header, false, filteredApiKeysList, setFilteredApiKeysList, order, setOrder, isDescending, setIsDescending, setActiveSortAsc, setActiveSortDesc);
        }
    }

    //This part related to Pagination Logic
    let tableRows = filteredApiKeysList.slice(firstIndex, firstIndex + (selectedRecordsPerPage));

    const handlePageChange = (event) => {
        const newIndex = (event.selected * selectedRecordsPerPage) % filteredApiKeysList.length;
        setFirstIndex(newIndex);
    };
    const changeItemsPerPage = (num) => {
        setIsItemsPerPageOpen(false);
        setSelectedRecordsPerPage(num);
        setFirstIndex(0);
    };
    const showApiKeyIdPopUp = (client) => {
        if (client.status.toLowerCase() === "active") {
            console.log(client);
            setCurrentClient(client);
            setShowPopup(true);
        }
    };

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
                            <div className={`flex items-start gap-x-2`}>
                                <img src={backArrow} alt="" onClick={() => moveToHome(navigate)} className={`mt-[8%] cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} />
                                <div className="flex-col mt-[3%]">
                                    <h1 className="font-semibold text-lg text-dark-blue">{t('authenticationServices.authenticationServices')}</h1>
                                    <p onClick={() => moveToHome(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                        {t('commons.home')}
                                    </p>
                                </div>
                            </div>
                            {apiKeysList.length > 0 ?
                                <button onClick={() => generateApiKey()} type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                                    {t('apiKeysList.generateApiKey')}
                                </button>
                                : null
                            }
                        </div>
                        <AuthenticationServicesTab
                            activeOidcClient={activeOidcClient}
                            setActiveOicdClient={setActiveOicdClient}
                            activeApiKey={activeApiKey}
                            setActiveApiKey={setActiveApiKey}
                        />

                        {apiKeysList.length === 0
                            ?
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                {
                                    activeApiKey && (
                                        <div className="flex justify-between py-2 pt-4 text-sm font-medium text-[#6F6E6E]">
                                            <div className={`flex sm:gap-x-3 md:gap-x-8 lg:gap-x-16 xl:gap-x-24`}>
                                                <h6 className="ml-5">{t('authenticationServices.partnerId')}</h6>
                                                <h6>{t('authenticationServices.policyGroup')}</h6>
                                                <h6>{t('authenticationServices.policyName')}</h6>
                                                <h6>{t('apiKeysList.apiKeyLabel')}</h6>
                                                <h6>{t('authenticationServices.createdDate')}</h6>
                                                <h6>{t('authenticationServices.status')}</h6>
                                                <h6>{t('apiKeysList.apiKeyId')}</h6>
                                                <h6 className="mr-5">{t('authenticationServices.action')}</h6>
                                            </div>
                                        </div>)
                                }

                                <hr className="h-px mx-3 bg-gray-200 border-0" />

                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center">
                                        <img src={rectangleGrid} alt="" />
                                        {activeApiKey &&
                                            (<button onClick={() => generateApiKey()} type="button"
                                                className={`text-white font-semibold mt-8 bg-tory-blue rounded-md text-sm mx-8 py-3`}>
                                                {t('apiKeysList.generateApiKey')}
                                            </button>)
                                        }
                                    </div>
                                </div>
                            </div>
                            :
                            <>
                                <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg">
                                    <div className="flex w-full p-2">
                                        <div className="flex w-full pl-[2%] pt-[1%] items-center justify-start font-semibold text-dark-blue text-sm" >
                                            {t('apiKeysList.listOfApiKeyRequests') + ' (' + filteredApiKeysList.length + ")"}
                                        </div>
                                        <div className="w-full flex justify-end relative ">
                                            {filter && <button onClick={() => onResetFilter()} type="button"
                                                className="flex justify-center items-center w-[23%] text-sm py-2 font-semibold text-cente text-tory-blue">
                                                {t('policies.resetFilter')}
                                            </button>}
                                            <button onClick={() => setFilter(!filter)} type="button" className={`flex justify-center items-center w-[23%] text-sm py-2 mt-2 text-tory-blue border border-[#1447B2] font-semibold rounded-md text-center
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
                                        <ApiClientsFilter
                                            filteredApiKeysList={filteredApiKeysList}
                                            onFilterChange={onFilterChange}>
                                        </ApiClientsFilter>
                                    }
                                    <div className="mx-[2%] overflow-x-scroll">
                                        <table className="table-fixed">
                                            <thead>
                                                <tr>
                                                    {tableHeaders.map((header, index) => {
                                                        return (
                                                            <th key={index} className={`py-4 text-xs font-medium text-[#6F6E6E] lg:w-[14%] ${header.id === "policyName" && 'pl-4'} ${header.id === "crDtimes" && 'pl-9'} ${header.id === "status" && 'pl-12'} ${header.id === "apiKeyReqID" && 'pr-2'}`}>
                                                                <div className="flex gap-x-1 items-center">
                                                                    {t(header.headerNameKey)}
                                                                    {(header.id !== "action") && (header.id !== "apiKeyReqID") && (
                                                                        <div>
                                                                            <svg className="cursor-pointer mb-0.5" onClick={() => sortAscOrder(header.id)} alt="Ascending"
                                                                                xmlns="http://www.w3.org/2000/svg"
                                                                                width="8" height="8" viewBox="0 0 7 6">
                                                                                <path id="Polygon_3" data-name="Polygon 3" d="M2.636,1.481a1,1,0,0,1,1.728,0L6.123,4.5A1,1,0,0,1,5.259,6H1.741A1,1,0,0,1,.877,4.5Z"
                                                                                    fill={`${(activeSortDesc === header.id && order === "ASC") ? "#1447b2" : "#969696"}`} />
                                                                            </svg>
                                                                            <svg className="cursor-pointer" onClick={() => sortDescOrder(header.id)} alt="Descending"
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
                                                            <tr key={index} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-medium ${client.status === "INACTIVE" ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                                                <td className="px-2">{client.partnerId}</td>
                                                                <td className="pr-2">{client.policyGroupName}</td>
                                                                <td className="px-4">{client.policyName}</td>
                                                                <td className="px-2">{client.apiKeyLabel}</td>
                                                                <td className="pl-9">{formatDate(client.crDtimes, 'dateTime')}</td>
                                                                <td className="px-12">
                                                                    <div className={`${bgOfStatus(client.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-medium rounded-md`}>
                                                                        {getStatusCode(client.status, t)}
                                                                    </div>
                                                                </td>
                                                                <td className={`${isLoginLanguageRTL ? "pr-2" : "pl-2"}`}>
                                                                    <svg onClick={() => showApiKeyIdPopUp(client)}
                                                                        xmlns="http://www.w3.org/2000/svg" width="22.634" height="15.433" viewBox="0 0 22.634 15.433">
                                                                        <path id="visibility_FILL0_wght400_GRAD0_opsz48"
                                                                            d="M51.32-787.911a4.21,4.21,0,0,0,3.1-1.276,4.225,4.225,0,0,0,1.273-3.1,4.21,4.21,0,0,0-1.276-3.1,4.225,4.225,0,0,0-3.1-1.273,4.21,4.21,0,0,0-3.1,1.276,4.225,4.225,0,0,0-1.273,3.1,4.21,4.21,0,0,0,1.276,3.1A4.225,4.225,0,0,0,51.32-787.911Zm-.009-1.492a2.764,2.764,0,0,1-2.039-.842,2.794,2.794,0,0,1-.836-2.045,2.764,2.764,0,0,1,.842-2.039,2.794,2.794,0,0,1,2.045-.836,2.764,2.764,0,0,1,2.039.842,2.794,2.794,0,0,1,.836,2.045,2.764,2.764,0,0,1-.842,2.039A2.794,2.794,0,0,1,51.311-789.4Zm.006,4.836a11.528,11.528,0,0,1-6.79-2.135A13,13,0,0,1,40-792.284a13.006,13.006,0,0,1,4.527-5.582A11.529,11.529,0,0,1,51.317-800a11.529,11.529,0,0,1,6.79,2.135,13.006,13.006,0,0,1,4.527,5.582,13,13,0,0,1-4.527,5.581A11.528,11.528,0,0,1,51.317-784.568ZM51.317-792.284Zm0,6.173A10.351,10.351,0,0,0,57.04-787.8a10.932,10.932,0,0,0,3.974-4.488,10.943,10.943,0,0,0-3.97-4.488,10.33,10.33,0,0,0-5.723-1.685,10.351,10.351,0,0,0-5.727,1.685,11.116,11.116,0,0,0-4,4.488,11.127,11.127,0,0,0,4,4.488A10.33,10.33,0,0,0,51.313-786.111Z"
                                                                            transform="translate(-40 800)" fill={`${client.status === "ACTIVE" ? "#1447B2" : "#D1D1D1"}`} />
                                                                    </svg>
                                                                    {showPopup && (
                                                                        <ApiKeyIdPopup closePopUp={setShowPopup} partnerId={currentClient.partnerId} policyName={currentClient.policyName} apiKeyId={currentClient.apiKeyReqID} />
                                                                    )}
                                                                </td>

                                                                <td className="text-center">
                                                                    <div>
                                                                        <p onClick={() => setViewApiKeyId(index)} className={`${isLoginLanguageRTL ? "ml-9" : "mr-9"} font-semibold mb-0.5 cursor-pointer`}>...</p>
                                                                        {viewApiKeyId === index && (
                                                                            <div ref={submenuRef} className={`absolute ${isLoginLanguageRTL ? "mr-16" : null} bg-white text-xs font-medium rounded-lg shadow-md border ${isLoginLanguageRTL ? "left-20" : "right-20"}`}>
                                                                                <p className="px-4 py-2 cursor-pointer text-[#3E3E3E]">
                                                                                    {t('oidcClientsList.view')}
                                                                                </p>
                                                                                <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                <p className={`px-5 py-2 text-[#BEBEBE]`}>
                                                                                    {t('oidcClientsList.edit')}
                                                                                </p>
                                                                                <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                <p className={`px-5 py-2 ${client.status === "ACTIVE" ? 'text-crimson-red cursor-pointer' : 'text-[#D8ADAD]'}`}>
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
                                        pageCount={Math.ceil(filteredApiKeysList.length / selectedRecordsPerPage)}
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
                                            {isItemsPerPageOpen && (
                                                <div ref={itemsCountSelectionRef}  className={`absolute bg-white text-xs text-tory-blue font-medium rounded-lg border-[2px] -mt-[130px] duration-700`}>
                                                    {itemsPerPageOptions.map((num, i) => {
                                                        return (
                                                            <p key={i} onClick={() => changeItemsPerPage(num)}
                                                                className={`px-3 py-2 cursor-pointer ${selectedRecordsPerPage === num ? 'bg-[#F2F5FC]' : 'hover:bg-[#F2F5FC]'}`}>
                                                                {num}
                                                            </p>
                                                        )
                                                    })
                                                    }
                                                </div>
                                            )}
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
                                        </div>
                                    </div>
                                </div>
                            </>
                        }
                    </div>
                </>
            )}
        </div>
    )
}

export default ApiKeysList;