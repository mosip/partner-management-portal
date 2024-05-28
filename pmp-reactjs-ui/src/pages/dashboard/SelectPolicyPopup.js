import { useState, useEffect } from 'react';
import { HttpService } from "../../services/HttpService.js";
import {
    getPartnerTypeDescription, createRequest,
    getPolicyManagerUrl, getPartnerManagerUrl, handleServiceErrors
} from '../../utils/AppUtils.js';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService.js';
import ErrorMessage from "../common/ErrorMessage.js";
import LoadingIcon from '../common/LoadingIcon.js';

function SelectPolicyPopup() {
    const [selectedPolicyGroup, setSelectedPolicyGroup] = useState("");
    const [policyGroupList, setPolicyGroupList] = useState([]);
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const { t } = useTranslation();
    const userprofile = getUserProfile();
    const [searchItem, setSearchItem] = useState("");
    const [isExpanded, setIsExpanded] = useState(false);

    const descriptionText = t('selectPolicyPopup.description');
    const maxWords = 20;
    const displayText = isExpanded ? descriptionText : `${descriptionText.split(' ').slice(0, maxWords).join(' ')}...`;
    const filteredPolicyGroupList = policyGroupList.filter(policyGroupItem =>
        policyGroupItem.fieldValue.toLowerCase().includes(searchItem.toLowerCase())
    );

    const expandDescription = () => {
        setIsExpanded(!isExpanded);
    };

    useEffect(() => {
        const fetchData = async () => {
            setDataLoaded(false);
            try {
                const request = createRequest({
                    "filters": [{ "columnName": "name", "type": "unique", "text": "" }],
                    "languageCode": "eng",
                    "optionalFilters": [{ "columnName": "isActive", "type": "equals", "value": "true" }]
                });
                const response = await HttpService({
                    url: getPolicyManagerUrl('/policies/group/filtervalues', process.env.NODE_ENV),
                    method: 'post',
                    data: request,
                    baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL
                });
                if (response && response.data && response.data.response) {
                    const resData = response.data.response;
                    if (resData.filters && resData.filters.length > 0) {
                        console.log(`found records: ${resData.filters.length}`);
                        setPolicyGroupList(resData.filters);
                    }
                } else {
                    setErrorMsg(t('selectPolicyPopup.policyGroupError'));
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                setErrorMsg(err);
                setDataLoaded(true);
            }
        };
        fetchData();
    }, [t]);

    const changePolicyGroupSelection = (policyGrpId) => {
        setSelectedPolicyGroup(policyGrpId);
        setIsDropdownOpen(false);
    };
    const cancelErrorMsg = () => {
        setErrorMsg("");
    };
    const openDropdown = () => {
        setSearchItem("")
        setIsDropdownOpen(!isDropdownOpen);
    };
    const clickOnSubmit = async () => {
        setDataLoaded(false);
        const userProfile = getUserProfile();
        const registerUserRequest = createRequest({
            partnerId: userProfile.userName,
            organizationName: userProfile.orgName,
            address: userProfile.address,
            contactNumber: userProfile.phoneNumber,
            emailId: userProfile.email,
            partnerType: userProfile.partnerType,
            policyGroup: selectedPolicyGroup,
            langCode: userProfile.langCode,
        });
        const registerUserResponse = await HttpService.post(getPartnerManagerUrl('/partners', process.env.NODE_ENV), registerUserRequest);
        const registerUserResponseData = registerUserResponse.data;
        if (registerUserResponseData && registerUserResponseData.response) {
            setDataLoaded(true);
            window.location.reload();
        } else {
            setDataLoaded(true);
            handleServiceErrors(registerUserResponseData, setErrorCode, setErrorMsg);
        }
    }
    return (
        <div className="fixed inset-0 w-full flex items-center justify-center bg-black bg-opacity-50 z-50 font-inter">
            <div className={`bg-white w-1/3 mx-auto rounded-xl shadow-lg mt-5`}>
                {!dataLoaded && (
                    <LoadingIcon></LoadingIcon>
                )}
                {dataLoaded && (
                    <>
                        {errorMsg && (
                            <div className="flex justify-end">
                                <div className="flex justify-between items-center min-h-14 bg-[#C61818] rounded-xl p-3 mr-10">
                                    <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                                </div>
                            </div>
                        )}
                        <div className="px-[6%] py-[3%]">
                            <h3 className="text-xl font-bold text-[#333333]">{t('selectPolicyPopup.title')}</h3>
                        </div>
                        <div className="border-gray-200 border-opacity-75 border-t"></div>
                        <div className="px-[6%] py-[3%] text-base text-[#414141]">
                            <p>
                                {displayText}
                            </p>
                            {descriptionText.split(' ').length > maxWords && (
                                <button className="text-tory-blue text-base font-semibold" onClick={expandDescription}>
                                    {isExpanded ? 'View Less' : 'View More'}
                                </button>
                            )}
                            <form>
                                <div className="pt-3  w-full mb-4 flex flex-col">
                                    <div className="flex flex-col">
                                        <label className="block text-dark-blue text-base font-semibold mb-2">
                                            {t('selectPolicyPopup.partnerTypeLabel')}:<span className="text-red-500 pl-1">*</span>
                                        </label>
                                        <button disabled className="flex items-center justify-between w-full h-11 px-2 py-2 border border-gray-300 rounded-md text-lg text-dark-blue bg-gray-200 leading-tight focus:outline-none focus:shadow-outline" type="button">
                                            <span>{getPartnerTypeDescription(userprofile.partnerType, t)}</span>
                                        </button>
                                    </div>
                                    <div className="flex flex-row">
                                        <label className="block text-dark-blue text-lg font-medium my-2">
                                            {t('selectPolicyPopup.policyGroup')}:<span className="text-red-500 pl-1">*</span>
                                        </label>
                                    </div>
                                    <div className="flex flex-row">
                                        <div className="relative z-10 w-full">
                                            <button onClick={openDropdown} className="flex items-center justify-between w-full h-11 px-2 py-2 border border-gray-400 rounded-md text-lg text-start text-dark-blue leading-tight focus:outline-none focus:shadow-none" type="button">
                                                <span>{
                                                    selectedPolicyGroup
                                                        ? policyGroupList.map(policyGroupItem => {
                                                            return selectedPolicyGroup === policyGroupItem.fieldCode ? policyGroupItem.fieldValue : '';
                                                        }).filter(Boolean)
                                                        : t('selectPolicyPopup.title')
                                                }
                                                </span>
                                                <svg className={`w-3 h-2 ml-3 transform ${isDropdownOpen ? 'rotate-180' : 'rotate-0'} text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                </svg>
                                            </button>
                                            {isDropdownOpen && (
                                                <div className="absolute z-50 top-10 left-0 w-full">
                                                    <div className="z-10 border border-gray-400 bg-white rounded-md shadow-lg w-full dark:bg-gray-700 cursor-pointer">
                                                        <div className="p-2 border-b border-gray-200 shadow-sm relative">
                                                            <span className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                                                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-4 text-black mx-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 18a8 8 0 100-16 8 8 0 000 16zM21 21l-5.2-5.2" />
                                                                </svg>
                                                            </span>
                                                            <input type="text" placeholder={t('selectPolicyPopup.search')} value={searchItem} onChange={(e) => setSearchItem(e.target.value)}
                                                                className="w-full h-8 pl-8 py-1 text-base text-gray-300 border border-gray-400 rounded-md focus:outline-none focus:text focus:text-gray-800" />
                                                        </div>
                                                        <div className="max-h-32 overflow-y-auto">
                                                            {filteredPolicyGroupList.map((policyGroupItem, index) => {
                                                                return (
                                                                    <div key={index}>
                                                                        <button className={`block w-full px-4 py-2 text-left text-base text-blue-950
                                                                            ${selectedPolicyGroup === policyGroupItem.id ? 'bg-gray-100' : 'hover:bg-gray-100'}`}
                                                                            onClick={() => changePolicyGroupSelection(policyGroupItem.fieldCode)}>
                                                                            {policyGroupItem.fieldValue}
                                                                        </button>
                                                                        <div className="border-gray-100 border-t mx-2"></div>
                                                                    </div>
                                                                );
                                                            })}
                                                        </div>
                                                    </div>
                                                </div>
                                            )}
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div className="border-gray-200 border-opacity-50 border-t"></div>
                        <div className="p-5 flex justify-end relative">
                            <button className="w-40 h-12 border-[#1447B2] border bg-tory-blue rounded-lg text-white text-base font-semibold relative z-60" onClick={clickOnSubmit}>
                                {t('selectPolicyPopup.submit')}
                            </button>
                        </div>
                    </>
                )}
            </div>
        </div>

    );
}

export default SelectPolicyPopup;