import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService.js';
import { isLangRTL, handleServiceErrors, getPartnerManagerUrl, handleMouseClickForDropdown } from '../../utils/AppUtils.js';
import { HttpService } from '../../services/HttpService.js';
import ErrorMessage from '../common/ErrorMessage.js';
import LoadingIcon from "../common/LoadingIcon.js";
import backArrow from '../../svg/back_arrow.svg';
import OidcClientList from './OidcClientList.js';
import ApiKeysList from './ApiKeysList.js';

function AuthenticationServices() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [firstTimeLoad, setFirstTimeLoad] = useState(false);
    const [dataLoaded, setDataLoaded] = useState(false);
    const [activeOidcClient, setActiveOicdClient] = useState(true);
    const [activeApiKey, setActiveApiKey] = useState(false);
    const [firstIndex, setFirstIndex] = useState(0);
    const [oidcClientsList, setOidcClientsList] = useState([]);
    const [listOfOidcClients, setListOfOidcClients] = useState([]);
    const [filteredOidcClientsList, setFilteredOidcClientsList] = useState([]);
    const [isItemsPerPageOpen, setIsItemsPerPageOpen] = useState(false);
    const [viewClientId, setViewClientId] = useState(-1);
    const defaultFilterQuery = {
        partnerId: "",
        policyGroupName: ""
    };
    const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
    const submenuRef = useRef(null);
    const itemsCountSelectionRef = useRef(null);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setViewClientId(null));
        handleMouseClickForDropdown(itemsCountSelectionRef, () => setIsItemsPerPageOpen(false));
    }, [submenuRef, itemsCountSelectionRef]);

    // const tableValues = [
    //     { "partnerId": "P28394091", "policyGroup": "Policy Group 01", "policyName": "Full KYC", "oidcClientName": "Client 13", "createdDate": "11/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394092", "policyGroup": "Policy Group 02", "policyName": "KYC", "oidcClientName": "Client 22", "createdDate": "21/10/2025", "status": "Deactivated", "oidcClientId": "0" },
    //     { "partnerId": "P28394093", "policyGroup": "Policy Group 03", "policyName": "KYC1", "oidcClientName": "Client 11", "createdDate": "06/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    //     { "partnerId": "P28394094", "policyGroup": "Policy Group 04", "policyName": "Full KYC", "oidcClientName": "Client 03", "createdDate": "30/09/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394095", "policyGroup": "Policy Group 05", "policyName": "KYC1", "oidcClientName": "Client 05", "createdDate": "12/10/2025", "status": "Rejected", "oidcClientId": "0" },
    //     { "partnerId": "P28394096", "policyGroup": "Policy Group 06", "policyName": "KYC", "oidcClientName": "Client 16", "createdDate": "07/10/2025", "status": "Deactivated", "oidcClientId": "0" },
    //     { "partnerId": "P28394097", "policyGroup": "Policy Group 07", "policyName": "Full KYC", "oidcClientName": "Client 07", "createdDate": "01/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    //     { "partnerId": "P28394098", "policyGroup": "Policy Group 08", "policyName": "KYC", "oidcClientName": "Client 04", "createdDate": "17/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394099", "policyGroup": "Policy Group 09", "policyName": "KYC1", "oidcClientName": "Client 12", "createdDate": "13/10/2025", "status": "Deactivated", "oidcClientId": "0" },
    //     { "partnerId": "P28394100", "policyGroup": "Policy Group 10", "policyName": "KYC", "oidcClientName": "Client 09", "createdDate": "02/10/2025", "status": "Rejected", "oidcClientId": "0" },
    //     { "partnerId": "P28394101", "policyGroup": "Policy Group 11", "policyName": "Full KYC", "oidcClientName": "Client 02", "createdDate": "08/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394102", "policyGroup": "Policy Group 12", "policyName": "KYC", "oidcClientName": "Client 06", "createdDate": "18/10/2025", "status": "Deactivated", "oidcClientId": "0" },
    //     { "partnerId": "P28394103", "policyGroup": "Policy Group 13", "policyName": "KYC", "oidcClientName": "Client 01", "createdDate": "14/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    //     { "partnerId": "P28394104", "policyGroup": "Policy Group 14", "policyName": "Full KYC", "oidcClientName": "Client 10", "createdDate": "03/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394105", "policyGroup": "Policy Group 15", "policyName": "KYC1", "oidcClientName": "Client 08", "createdDate": "09/10/2025", "status": "Rejected", "oidcClientId": "0" },
    //     { "partnerId": "P28394106", "policyGroup": "Policy Group 16", "policyName": "Full KYC", "oidcClientName": "Client 20", "createdDate": "19/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394107", "policyGroup": "Policy Group 17", "policyName": "KYC", "oidcClientName": "Client 17", "createdDate": "04/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394108", "policyGroup": "Policy Group 18", "policyName": "Full KYC", "oidcClientName": "Client 14", "createdDate": "15/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    //     { "partnerId": "P28394109", "policyGroup": "Policy Group 19", "policyName": "KYC", "oidcClientName": "Client 19", "createdDate": "10/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394110", "policyGroup": "Policy Group 20", "policyName": "Full KYC", "oidcClientName": "Client 18", "createdDate": "20/10/2025", "status": "Approved", "oidcClientId": "1" },
    //     { "partnerId": "P28394111", "policyGroup": "Policy Group 21", "policyName": "KYC1", "oidcClientName": "Client 21", "createdDate": "05/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    //     { "partnerId": "P28394112", "policyGroup": "Policy Group 22", "policyName": "Full KYC", "oidcClientName": "Client 15", "createdDate": "16/10/2025", "status": "Rejected", "oidcClientId": "0" }

    // ];

    useEffect(() => {
        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(getPartnerManagerUrl('/getAllOidcClients', process.env.NODE_ENV));
                setFirstTimeLoad(true);
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        const sortedData = resData.sort((a, b) => new Date(b.crDtimes) - new Date(a.crDtimes));
                        setOidcClientsList(sortedData);
                        setOidcClientsList(sortedData);
                        setFilteredOidcClientsList(oidcClientsList)
                        console.log('Response data:', oidcClientsList.length);
                    } else {
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('oidcClientsList.errorInOidcClientsList'));
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                setErrorMsg(err);
            }
        };
        fetchData();
    }, [firstTimeLoad]);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const moveToHome = () => {
        navigate('/partnermanagement')
    };

    const showOidcClientList = () => {
        navigate('/partnermanagement/authenticationServices/oidcClient');
        setActiveOicdClient(true);
        setActiveApiKey(false)
    };

    const showApiKeysList = () => {
        navigate('/partnermanagement/authenticationServices/apiKey');
        setActiveOicdClient(false);
        setActiveApiKey(true)
    };

    const createOidcClient = () => {
        navigate('/partnermanagement/createOidcClient')
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

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <div className={`flex justify-end max-w-7xl mb-5 absolute ${isLoginLanguageRTL ? "left-0" : "right-0"}`}>
                            <div className="flex justify-between items-center max-w-96 min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 z-10">
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                            </div>
                        </div>
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between mb-5">
                            <div className={`flex gap-x-2`}>
                                <img src={backArrow} alt="" onClick={() => moveToHome()} className={`cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} />
                                <div className="flex-col mt-[3%]">
                                    <h1 className="font-semibold text-lg text-dark-blue">{t('authenticationServices.authenticationServices')}</h1>
                                    <p onClick={() => moveToHome()} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                        {t('commons.home')}
                                    </p>
                                </div>
                            </div>
                            {oidcClientsList.length > 0 ?
                                <button onClick={() => createOidcClient()} type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                                    {t('createOidcClient.createOidcClient')}
                                </button>
                                : null
                            }
                        </div>
                        <div className='flex text-xs bg-[#FCFCFC] font-bold space-x-16 items-start rounded-lg px-[1.5%] pt-[2%]'>
                            <div className={`flex-col justify-center`}>
                                <h6 onClick={() => showOidcClientList()}
                                    className={`${activeOidcClient ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] ${isLoginLanguageRTL ? "mr-[10%]" : "ml-[10%]"} cursor-pointer text-sm`}>
                                    {t('authenticationServices.oidcClient')}
                                </h6>
                                <div className={`h-1 w-24 ${activeOidcClient ? "bg-tory-blue" : "bg-transparent"}  rounded-t-md`}></div>
                            </div>
                            <div className={`flex-col justify-center`}>
                                <h6 onClick={() => showApiKeysList()}
                                    className={`${activeApiKey ? "text-[#1447b2]" : "text-[#031640]"} min-w-32 mb-[12%] ${isLoginLanguageRTL ? "mr-[16%]" : "ml-[16%]"} cursor-pointer text-sm`}>
                                    {t('authenticationServices.apiKey')}
                                </h6>
                                <div className={`h-1 ${activeApiKey ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
                            </div>
                        </div>
                        { activeOidcClient && <OidcClientList activeOidcClient={activeOidcClient} listOfOidcClients={listOfOidcClients}/> }

                        { activeApiKey && <ApiKeysList activeApiKey={activeApiKey}/> }
                        
                    </div>
                </>
            )}
        </div>
    )
}

export default AuthenticationServices; 