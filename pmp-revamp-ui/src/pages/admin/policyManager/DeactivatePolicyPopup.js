import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../../services/UserProfileService';
import { getPolicyManagerUrl, handleServiceErrors, isLangRTL } from '../../../utils/AppUtils';
import errorIcon from '../../../svg/error_icon.svg';
import LoadingIcon from '../../common/LoadingIcon';
import ErrorMessage from '../../common/ErrorMessage';
import { HttpService } from '../../../services/HttpService';

function DeactivatePolicyPopup({ header, description, popupData, headerKeyName, closePopUp, onClickConfirm }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [showAlertErrorMessage, setShowAlertErrorMessage] = useState(false);
    const [errorHeaderMsg, setErrorHeaderMsg] = useState('');
    const [errorDescriptionMsg, setErrorDescriptionMsg] = useState('');

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const closingPopUp = () => {
        document.body.style.overflow = "auto"
        closePopUp()
    };

    const setPolicyErrorMessage = (errorCode) => {
        if (errorCode === 'PMS_POL_064') {
            setErrorHeaderMsg(t('pendingPolicyRequestsDetectedMsg.header'))
            setErrorDescriptionMsg(t('pendingPolicyRequestsDetectedMsg.description'))
        } else if (errorCode === 'PMS_POL_063') {
            setErrorHeaderMsg(t('activePolicyRequestsDetectedMsg.header'))
            setErrorDescriptionMsg(t('activePolicyRequestsDetectedMsg.description'))
        }
    };

    const clickOnConfirm = async () => {
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        document.body.style.overflow = "auto"
        try {
            let response;
            if (popupData.isDeactivatePolicyGroup) {
                response = await HttpService({
                    url: getPolicyManagerUrl(`/policies/group/${popupData.id}`, process.env.NODE_ENV),
                    method: 'patch',
                    baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL,
                });
            } else if (popupData.isDeactivatePolicy) {
                response = await HttpService({
                    url: getPolicyManagerUrl(`/policies/${popupData.policyId}`, process.env.NODE_ENV),
                    method: 'patch',
                    baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL,
                });
            }
            const responseData = response.data;
            if (responseData && responseData.response) {
                onClickConfirm(responseData.response);
            } else {
                if (responseData && responseData.errors && responseData.errors.length > 0) {
                    const errorCode = responseData.errors[0].errorCode;
                    const errorMessage = responseData.errors[0].message;
                    if (popupData.isDeactivatePolicyGroup && (errorCode === 'PMS_POL_056' || errorCode === 'PMS_POL_069' || errorCode === 'PMS_POL_070')) {
                        await getAssociatedPolicies(errorCode);
                        setShowAlertErrorMessage(true);
                    } else if (popupData.isDeactivatePolicy && (errorCode === 'PMS_POL_063' || errorCode === 'PMS_POL_064')) {
                        setPolicyErrorMessage(errorCode);
                        setShowAlertErrorMessage(true);
                    } else {
                        setErrorCode(errorCode);
                        setErrorMsg(errorMessage);
                    }
                    console.error('Error:', errorMessage);
                }
            }
        } catch (err) {
            setErrorMsg(err);
        }
        setDataLoaded(true);
    };

    const getAssociatedPolicies = async (errorCode) => {
        try {
            const response = await HttpService({
                url: getPolicyManagerUrl(`/policies/group/${popupData.id}`, process.env.NODE_ENV),
                method: 'get',
                baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL,
            });
            const responseData = response.data;
            if (responseData && responseData.response) {
                const resData = responseData.response.policies || [];
                let activePoliciesCount = 0;
                let draftPoliciesCount = 0;
    
                if (errorCode === 'PMS_POL_069') {
                    // Count active and draft policies
                    activePoliciesCount = resData.filter(policy => policy.is_Active && policy.schema).length;
                    draftPoliciesCount = resData.filter(policy => !policy.is_Active && !policy.schema).length;
                    setErrorHeaderMsg(t('activeAndDraftPoliciesDetectedMsg.header'));
                    setErrorDescriptionMsg(t('activeAndDraftPoliciesDetectedMsg.description', { noOfActivePolicies: activePoliciesCount, noOfDraftPolicies: draftPoliciesCount }));
                } else if (errorCode === 'PMS_POL_070') {
                    // Count draft policies
                    draftPoliciesCount = resData.filter(policy => !policy.is_Active && !policy.schema).length;
                    setErrorHeaderMsg(t('draftPoliciesDetectedMsg.header'));
                    setErrorDescriptionMsg(t('draftPoliciesDetectedMsg.description', { noOfDraftPolicies: draftPoliciesCount }));
                } else if (errorCode === 'PMS_POL_056') {
                    // Count active policies
                    activePoliciesCount = resData.filter(policy => policy.is_Active && policy.schema).length;
                    setErrorHeaderMsg(t('activePoliciesDetectedMsg.header'));
                    setErrorDescriptionMsg((activePoliciesCount > 1) ? t('activePoliciesDetectedMsg.description1', { noOfActivePolicies: activePoliciesCount }) : t('activePoliciesDetectedMsg.description2'));
                }
            } else {
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        } catch (err) {
            setErrorMsg(err);
        }
    };

    const closeErrorPopUp = () => {
        setShowAlertErrorMessage(false);
        closePopUp();
    };

    const customStyle = {
        outerDiv: "!flex !justify-end",
        innerDiv: "!flex !justify-between !items-center !rounded-xl !w-full !min-h-12 !p-3 !m-1 !-mb-6"
    }

    const styles = {
        loadingDiv: "!py-[35%]"
    };

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-[50%] z-50 font-inter cursor-default">
            <div className={`bg-white md:w-[390px]  ${showAlertErrorMessage ? 'w-[22rem] h-[30rem]' : 'w-[55%]'} mx-auto rounded-lg shadow-lg h-fit`}>
                {!dataLoaded && (
                    <LoadingIcon styleSet={styles} />
                )}
                {dataLoaded && (
                    <div className="relative">
                        {showAlertErrorMessage
                            ? (
                                <div className={`flex-col space-y-3 text-center justify-center p-[1rem] items-center place-self-center`}>
                                    <img src={errorIcon} alt="" className={`h-[5.5rem] ${isLoginLanguageRTL ? "mr-[8.5rem]" : "ml-[8.5rem]"}`} />
                                    <p className="text-[1rem] leading-snug font-semibold text-black break-normal">
                                        {errorHeaderMsg}
                                    </p>
                                    <p className="text-sm text-center text-[#666666] break-normal p-2">
                                        {errorDescriptionMsg}
                                    </p>
                                    <button id="alert_error_popup_okay_btn" onClick={closeErrorPopUp} type="button" className={`w-36 h-10 border-[#1447B2] border rounded-md bg-tory-blue text-white text-sm font-semibold my-1`}>
                                        {t('commons.okay')}
                                    </button>
                                </div>
                            )
                            : (<>
                                {errorMsg && (
                                    <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={customStyle} />
                                )}
                                <div className={`p-[8%] flex-col text-center justify-center items-center`}>
                                    {!isLoginLanguageRTL ?
                                        <p className="text-base leading-snug font-semibold text-black break-words px-[1%]">
                                            {`'${t(header)} -  ${headerKeyName} ?'`}
                                        </p>
                                        : <p className="text-base leading-snug font-semibold text-black break-words px-[1%]">
                                            {t(header)} { ' - ' + headerKeyName}
                                        </p>
                                    }
                                    <p className="text-sm text-[#666666] break-words py-[5%]">
                                        {t(description)}
                                    </p>
                                    <div className="flex flex-row items-center justify-center space-x-3 pt-[4%]">
                                        <button id="deactivate_policy_group_cancel_btn" onClick={() => closingPopUp()} type="button" className="w-40 h-12 border-[#1447B2] border rounded-md text-tory-blue text-sm font-semibold">{t('requestPolicy.cancel')}</button>
                                        <button id="deactivate_policy_group__confirm_btn" onClick={() => clickOnConfirm()} type="button" className={`w-40 h-12 border-[#1447B2] border rounded-md bg-tory-blue text-white text-sm font-semibold ${isLoginLanguageRTL && '!mr-3'}`}>{t('deactivateOidcClient.confirm')}</button>
                                    </div>
                                </div>
                            </>

                            )}
                    </div>
                )}
            </div>
        </div>
    )
}

export default DeactivatePolicyPopup;