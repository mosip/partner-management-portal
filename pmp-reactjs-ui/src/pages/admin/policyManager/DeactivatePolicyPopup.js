import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../../services/UserProfileService';
import { getPolicyManagerUrl, handleServiceErrors, isLangRTL } from '../../../utils/AppUtils';
import errorIcon from '../../../svg/error_icon.svg';
import LoadingIcon from '../../common/LoadingIcon';
import LoadingCount from '../../common/LoadingCount';
import ErrorMessage from '../../common/ErrorMessage';
import { HttpService } from '../../../services/HttpService';

function DeactivatePolicyPopup({ header, description, popupData, request, headerKeyName, closePopUp, errorHeaderMsg, errorDescriptionMsg }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [showAlertErrorMessage, setShowAlertErrorMessage] = useState(false);
    const [countOfAssociatedPolicies, setCountOfAssociatedPolicies] = useState(0);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const closingPopUp = () => {
        document.body.style.overflow = "auto"
        closePopUp()
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
                    method: 'put',
                    baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL,
                    data: request
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
                window.location.reload();
            } else {
                setDataLoaded(true);
                if (responseData && responseData.errors && responseData.errors.length > 0) {
                    const errorCode = responseData.errors[0].errorCode;
                    const errorMessage = responseData.errors[0].message;
                    if (errorCode === 'PMS_POL_056') {
                        setShowAlertErrorMessage(true);
                        await getActiveAssociatedPolicies();
                    } else if (errorCode === 'PMS_POL_063' || errorCode === 'PMS_POL_064') {
                        setShowAlertErrorMessage(true);
                    } else {
                        setErrorCode(errorCode);
                        setErrorMsg(errorMessage);
                    }
                    console.error('Error:', errorMessage);
                }
            }
        } catch (err) {
            setDataLoaded(true);
            setErrorMsg(err);
        }
    };

    const getActiveAssociatedPolicies = async () => {
        try {
            const response = await HttpService({
                url: getPolicyManagerUrl(`/policies/active/group/${popupData.name}`, process.env.NODE_ENV),
                method: 'get',
                baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL,
            });
            const responseData = response.data;
            if (responseData && responseData.response) {
                const resData = responseData.response;
                setCountOfAssociatedPolicies(resData.length);
                console.log(resData.length)
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
        innerDiv: "!flex !justify-between !items-center !rounded-xl !w-[55%] !min-h-12 !p-3 !m-1 !-mb-6"
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
                                    <img src={errorIcon} alt="" className='h-[5.5rem] place-self-center' />
                                    <p className="text-[1rem] leading-snug font-semibold text-black break-normal">
                                        {errorHeaderMsg}
                                    </p>
                                    <p className="text-sm text-center text-[#666666] break-normal p-2">
                                        {countOfAssociatedPolicies
                                            ? t(errorDescriptionMsg, {
                                                noOfAssociatedPolicies: countOfAssociatedPolicies || <LoadingCount />
                                            })
                                            : t(errorDescriptionMsg)
                                        }
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
                                        <p className="text-base leading-snug font-semibold text-black break-normal px-[1%]">
                                            {t(header)} {' - ' + headerKeyName + ' ?'}
                                        </p>
                                        : <p className="text-base leading-snug font-semibold text-black break-normal px-[1%]">
                                            {t(header)} '{ ' - ' + headerKeyName}'
                                        </p>
                                    }
                                    <p className="text-sm text-[#666666] break-normal py-[5%]">
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