import { useState, useEffect } from 'react';
import { HttpService } from "../../services/HttpService.js";
import {
    getPartnerTypeDescription, createRequest,
    getPolicyManagerUrl, getPartnerManagerUrl, handleServiceErrors, logout
} from '../../utils/AppUtils.js';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService.js';
import ErrorMessage from "../common/ErrorMessage.js";
import LoadingIcon from '../common/LoadingIcon.js';
import DropdownWithSearchComponent from '../common/fields/DropdownWithSearchComponent.js';

function SelectPolicyPopup() {
    const [selectedPolicyGroup, setSelectedPolicyGroup] = useState("");
    const [policyGroupList, setPolicyGroupList] = useState([]);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const { t } = useTranslation();
    const userprofile = getUserProfile();
    const [isExpanded, setIsExpanded] = useState(false);

    const descriptionText = t('selectPolicyPopup.description');
    const maxWords = 20;
    const displayText = isExpanded ? descriptionText : `${descriptionText.split(' ').slice(0, maxWords).join(' ')}...`;
    

    const expandDescription = () => {
        setIsExpanded(!isExpanded);
    };

    const handleDropdownClick = () => {
        setIsExpanded(false);
    };

    useEffect(() => {
        const fetchData = async () => {
            setDataLoaded(false);
            try {
                const response = await HttpService({
                    url: getPolicyManagerUrl('/policies/getAllPolicyGroups', process.env.NODE_ENV),
                    method: 'get',
                    baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL
                });
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        setPolicyGroupList(createPolicyGroupDropdownData(resData));
                        console.log(`Response data: ${resData.length}`);
                    } else {
                      handleServiceErrors(responseData, setErrorCode, setErrorMsg);
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

    const createPolicyGroupDropdownData = (dataList) => {
        let dataArr = [];
        dataList.forEach(item => {
            let alreadyAdded = false;
            dataArr.forEach(item1 => {
                if (item1.fieldValue === item.name) {
                    alreadyAdded = true;
                }
            });
            if (!alreadyAdded) {
                dataArr.push({
                    fieldCode: item.name,
                    fieldValue: item.name,
                    fieldDescription: item.desc
                });
            }
        });
        return dataArr;
    }

    const changePolicyGroupSelection = (fieldName, policyGrpId) => {
        setSelectedPolicyGroup(policyGrpId);
    };
    const cancelErrorMsg = () => {
        setErrorMsg("");
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

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !my-2 mb-0",
        dropdownButton: "!w-full min-w-fit !h-10 !rounded-md !text-sm !text-dark-blue",
        selectionBox: "",
        loadingDiv: "!py-[50%]"
    }

    return (
        <div className="fixed inset-0 w-full flex items-center justify-center bg-black bg-opacity-50 z-50 font-inter">
            <div className={`bg-white w-1/3 h-fit rounded-xl shadow-lg`}>
                {!dataLoaded && (
                    <LoadingIcon styleSet={styles}></LoadingIcon>
                )}
                {dataLoaded && (
                    <>
                        {errorMsg && (
                            <div className="flex justify-end items-center absolute w-1/3">
                                <div className="flex justify-between items-center min-h-14 bg-[#C61818] rounded-xl p-3">
                                    <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                                </div>
                            </div>
                        )}
                        <div className="px-4 py-2">
                            <h3 className="text-base font-bold text-[#333333]">{t('selectPolicyPopup.title')}</h3>
                        </div>
                        <div className="border-gray-200 border-opacity-75 border-t"></div>
                        <div className="py-3 px-4 text-sm text-[#414141]">
                            <p>
                                {displayText}
                            </p>
                            {descriptionText.split(' ').length > maxWords && (
                                <button className="text-tory-blue text-sm font-semibold" onClick={expandDescription}>
                                    {isExpanded ? t('selectPolicyPopup.viewLess'): t('selectPolicyPopup.viewMore')}
                                </button>
                            )}
                            <form>
                                <div className="pt-2 w-full mb-1 flex flex-col">
                                    <div className="flex flex-col">
                                        <label className="block text-dark-blue text-sm font-semibold mb-2">
                                            {t('selectPolicyPopup.partnerTypeLabel')}<span className="text-red-500 pl-1">*</span>
                                        </label>
                                        <button disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-gray-300 rounded-md text-sm text-dark-blue bg-gray-200 leading-tight focus:outline-none focus:shadow-outline" type="button">
                                            <span>{getPartnerTypeDescription(userprofile.partnerType, t)}</span>
                                        </button>
                                    </div>
                                    <div className="flex flex-col" onClick={handleDropdownClick}>
                                        <DropdownWithSearchComponent 
                                            fieldName='policyGroup' 
                                            dropdownDataList={policyGroupList} 
                                            onDropDownChangeEvent={changePolicyGroupSelection}
                                            fieldNameKey='selectPolicyPopup.policyGroup*' 
                                            placeHolderKey='selectPolicyPopup.title' 
                                            searchKey='commons.search'
                                            styleSet={styles}>
                                        </DropdownWithSearchComponent>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div className="border-[#E5EBFA] border-t mx-2"></div>
                        <div className="p-3 flex justify-between relative">
                            <p className="text-[#333333] text-sm font-medium ml-2">{t('selectPolicyPopup.logoutMsg')}
                                <span className="text-tory-blue font-semibold cursor-pointer" onClick={logout}> {t('commons.logout')}</span>
                            </p>
                            <button
                                className={`w-40 h-10 m-1 border-[#1447B2] border rounded-lg text-white text-sm font-semibold relative z-60 
                                ${selectedPolicyGroup ? 'bg-tory-blue cursor-pointer' : 'bg-gray-400 cursor-not-allowed opacity-55'}`}
                                onClick={clickOnSubmit}
                                disabled={!selectedPolicyGroup}
                            >
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