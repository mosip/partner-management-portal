import { useState, useEffect } from "react";
import { useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import DropdownComponent from '../../common/fields/DropdownComponent';
import { getUserProfile } from '../../../services/UserProfileService';
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import {
    getPartnerManagerUrl, handleServiceErrors, getPartnerTypeDescription, isLangRTL, moveToApiKeysList,
    createRequest, getPartnerPolicyRequests, createDropdownData, trimAndReplace, getApprovedAuthPartners
} from "../../../utils/AppUtils";
import { HttpService } from '../../../services/HttpService';
import DropdownWithSearchComponent from "../../common/fields/DropdownWithSearchComponent";
import BlockerPrompt from "../../common/BlockerPrompt";
import CopyIdPopUp from "../../common/CopyIdPopup";
import Title from "../../common/Title";
import Confirmation from "../../common/Confirmation";

function GenerateApiKey() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [partnerData, setPartnerData] = useState([]);
    const [policyRequestsData, setPolicyRequestsData] = useState([]);
    const [showPopup, setShowPopup] = useState(false);
    const [partnerIdDropdownData, setPartnerIdDropdownData] = useState([]);
    const [policiesDropdownData, setPoliciesDropdownData] = useState([]);
    const [partnerId, setPartnerId] = useState("");
    const [policyName, setPolicyName] = useState("");
    const [partnerType, setPartnerType] = useState("");
    const [policyGroupName, setPolicyGroupName] = useState("");
    const [nameLabel, setNameLabel] = useState('');
    const [apiKeyId, setApiKeyId] = useState('');
    const [validationError, setValidationError] = useState("");
    const [generateApiKeySuccess, setGenerateApiKeySuccess] = useState(false);
    const [confirmationData, setConfirmationData] = useState({});
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);

    const navigate = useNavigate();

    const blocker = useBlocker(
        ({ currentLocation, nextLocation }) => {
            if (isSubmitClicked || generateApiKeySuccess) {
                setIsSubmitClicked(false);
                return false;
            }
            return (
                (partnerId !== "" || nameLabel !== "" || policyName !== "") && currentLocation.pathname !== nextLocation.pathname
            );
        }
    );

    useEffect(() => {
        const shouldWarnBeforeUnload = () => {
            return partnerId !== "" ||
                nameLabel !== "" ||
                policyName !== "";
        };

        const handleBeforeUnload = (event) => {
            if (shouldWarnBeforeUnload() && !isSubmitClicked) {
                event.preventDefault();
                event.returnValue = '';
            }
        };

        window.addEventListener('beforeunload', handleBeforeUnload);

        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload);
        };
    }, [partnerId, nameLabel, policyName, isSubmitClicked]);

    const cancelErrorMsg = () => {
        setErrorMsg("");
        setShowPopup(false);
    };

    const onChangePartnerId = async (fieldName, selectedValue) => {
        setPartnerId(selectedValue);
        setPolicyName("");
        setPolicyGroupName("");
        setPoliciesDropdownData([]);
        setPartnerType("");
        // Find the selected partner data
        const selectedPartner = partnerData.find(item => item.partnerId === selectedValue);
        if (selectedPartner) {
          const activePolicies = policyRequestsData.filter(
            item => item.partnerId === selectedValue && item.status === 'approved'
          );
          setPartnerType(getPartnerTypeDescription("AUTH_PARTNER", t));
          setPolicyGroupName(selectedPartner.policyGroupName);
          setPoliciesDropdownData(createDropdownData('policyName', 'policyDescription', false, activePolicies, t));
        }
      };

    const onChangePolicyName = (fieldName, selectedValue) => {
        setPolicyName(selectedValue);
    };

    const onChangeNameLabel = (value) => {
        setNameLabel(value)
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const resData = await getApprovedAuthPartners(HttpService, setErrorCode, setErrorMsg, t);
                if (resData) {
                    setPartnerData(resData);
                    setPartnerIdDropdownData(createDropdownData('partnerId', '', false, resData, t));
                } else {
                    setErrorMsg(t('commons.errorInResponse'));
                }
            } catch (err) {
                console.error('Error fetching data:', err);
            } finally {
                setDataLoaded(true);
            }
        };
        const fetchPolicyRequestsData = async () => {
            try {
                setDataLoaded(false);
                const resData = await getPartnerPolicyRequests(HttpService, setErrorCode, setErrorMsg, t);
                if (resData) {
                    setPolicyRequestsData(resData);
                } else {
                    setErrorMsg(t('commons.errorInResponse'));
                }
            } catch (err) {
                console.error('Error fetching data:', err);
            } finally {
                setDataLoaded(true);
            }
        };

        fetchPolicyRequestsData();
        fetchData();
    }, []);

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !mb-1",
        dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
        selectionBox: "!top-10"
    }

    const clearForm = () => {
        setPartnerId("");
        setPartnerType("");
        setPolicyGroupName("");
        setPolicyName("");
        setNameLabel("");
        setPoliciesDropdownData([]);
        setValidationError("");
    };

    const isFormValid = () => {
        return partnerId && policyName && nameLabel.trim() && !validationError;
    };

    useEffect(() => {
        if (showPopup && !errorMsg) {
            setIsSubmitClicked(true);
        }
    }, [showPopup, errorMsg]);

    const clickOnCancel = () => {
        moveToApiKeysList(navigate);
    }

    const clickOnSubmit = async () => {
        setShowPopup(false);
        setIsSubmitClicked(true);
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        let request = createRequest({
            policyName: policyName,
            label: trimAndReplace(nameLabel)
        });
        try {
            const response = await HttpService.patch(getPartnerManagerUrl(`/partners/${partnerId}/generate/apikey`, process.env.NODE_ENV), request, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response;
                    console.log(`Response data: ${resData.length}`);
                    const requireData = {
                        title: "generateApiKey.generateApiKey",
                        backUrl: "/partnermanagement/authentication-services/api-keys-list",
                        header: "generateApiKey.generateApiKeySuccessHeader",
                        description: "generateApiKey.apiKeySuccessMsg",
                        subNavigation: "authenticationServices.authenticationServices",
                    }
                    setConfirmationData(requireData);
                    setApiKeyId(responseData.response.apiKey);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('generateApiKey.errorInGenerateApiKey'));
            }
            setShowPopup(true);
            setDataLoaded(true);
        } catch (err) {
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            console.log("Error fetching data: ", err);
        }
        setIsSubmitClicked(false);
    }

    const copyIdPopupStyle = {
        outerDiv: "!bg-opacity-[50%]"
    }

    const handleFormSubmit = (event) => {
        event.preventDefault();
    };

    const closePopUp = (state) => {
        setShowPopup(state);
        setGenerateApiKeySuccess(true);
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter max-[450px]:text-xs relative`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <div className="flex justify-between">
                            <Title title='generateApiKey.generateApiKey' subTitle='authenticationServices.authenticationServices' backLink='/partnermanagement/authentication-services/api-keys-list'  />
                        </div>
                        {!generateApiKeySuccess ?
                            <div className="w-[100%] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                                <div className="px-[2.5%] py-[2%]">
                                    <p className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                    <form onSubmit={handleFormSubmit}>
                                        <div className="flex flex-col">
                                            <div className="flex flex-row justify-between space-x-4 max-[450px]:space-x-0 my-[1%] max-[450px]:flex-col">
                                                <div className="flex-col w-[48%] max-[450px]:w-full">
                                                    <DropdownComponent
                                                        fieldName='partnerId'
                                                        dropdownDataList={partnerIdDropdownData}
                                                        onDropDownChangeEvent={onChangePartnerId}
                                                        fieldNameKey='requestPolicy.partnerId*'
                                                        placeHolderKey='createOidcClient.selectPartnerId'
                                                        selectedDropdownValue={partnerId}
                                                        styleSet={styles}
                                                        addInfoIcon={true}
                                                        infoKey='createOidcClient.partnerIdTooltip'
                                                        id='generate_partner_id'>
                                                    </DropdownComponent>
                                                </div>
                                                <div className="flex-col w-[48%] max-[450px]:w-full">
                                                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red mx-1">*</span></label>
                                                    <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                        <span className={`w-full break-all ${partnerType ? 'text-dark-blue' : 'text-gray-400'} text-wrap text-start`}>{partnerType || t('commons.partnersHelpText')}</span>
                                                        <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                        </svg>
                                                    </button>
                                                </div>
                                            </div>
                                            <div className="flex flex-row justify-between space-x-4 max-[450px]:space-x-0 my-2 max-[450px]:flex-col">
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.policyGroup')}<span className="text-crimson-red mx-1">*</span></label>
                                                    <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                        <span className={`w-full break-all ${partnerType ? 'text-dark-blue' : 'text-gray-400'} text-wrap text-start`}>{policyGroupName || t('commons.partnersHelpText')}</span>
                                                        <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                        </svg>
                                                    </button>
                                                </div>
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <DropdownWithSearchComponent
                                                        fieldName='policyName'
                                                        dropdownDataList={policiesDropdownData}
                                                        onDropDownChangeEvent={onChangePolicyName}
                                                        fieldNameKey='requestPolicy.policyName*'
                                                        placeHolderKey='generateApiKey.selectedPolicyName'
                                                        selectedDropdownValue={policyName}
                                                        searchKey='commons.search'
                                                        styleSet={styles}
                                                        addInfoIcon={true}
                                                        disabled={!partnerId}
                                                        infoKey={t('createOidcClient.policyNameToolTip')} 
                                                        id='generate_policy_name'/>
                                                </div>
                                            </div>
                                            <div className="space-y-6">
                                                <div className="my-4">
                                                    <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('viewApiKeyDetails.apiKeyName')}<span className="text-crimson-red mx-1">*</span></label>
                                                        <input value={nameLabel} onChange={(e) => onChangeNameLabel(e.target.value)} maxLength={36}
                                                            className="h-10 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                                            placeholder={t('generateApiKey.enterNameForApiKey')} id="generate_api_key_name"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div className="border bg-medium-gray" />
                                <div className="flex flex-row max-[450px]:flex-col px-[3%] py-5 justify-between max-[450px]:space-y-2">
                                    <button id="generate_clear_form" onClick={() => clearForm()} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.clearForm')}</button>
                                    <div className={`flex flex-row max-[450px]:flex-col space-x-3 max-[450px]:space-x-0 max-[450px]:space-y-2 w-full md:w-auto justify-end`}>
                                        <button id="generate_cancel_btn" onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                                        <button id="generate_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t('requestPolicy.submit')}</button>
                                        {(showPopup && !errorMsg) && (
                                            <CopyIdPopUp closePopUp={closePopUp} partnerId={partnerId} policyName={policyName} id={apiKeyId}
                                                header='apiKeysList.apiKey' alertMsg='apiKeysList.apiKeyIdAlertMsg' styleSet={copyIdPopupStyle} />
                                        )}
                                    </div>
                                </div>
                            </div>
                            : <Confirmation confirmationData={confirmationData} />
                        }
                    </div>
                </>
            )}
            <BlockerPrompt blocker={blocker} />
        </div>
    )
}

export default GenerateApiKey;