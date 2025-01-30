import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile, setUserProfile } from '../../../services/UserProfileService';
import { bgOfStatus, formatDate, getErrorMessage, getPartnerManagerUrl, getStatusCode, handleFileChange, handleServiceErrors, isLangRTL } from '../../../utils/AppUtils';
import LoadingIcon from '../../common/LoadingIcon';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';
import ErrorMessage from '../../common/ErrorMessage';
import Title from '../../common/Title';
import { HttpService } from '../../../services/HttpService';

function ViewAdminApiKeyDetails() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [apiKeyDetails, setApiKeyDetails] = useState({});

    const moveToApiClientsList = () => {
        navigate('/partnermanagement/admin/authentication-services/api-keys-list');
    };

    useEffect(() => {
        const data = localStorage.getItem('selectedApiKeyAttributes');
        if(!data){
            setUnexpectedError(true);
            return ;
        }
        const apiKeyData = JSON.parse(data);
        setApiKeyDetails(apiKeyData);
    }, []);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    return (
        <div className={`w-full p-4 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} overflow-x-scroll`}>
            {!dataLoaded && (
                <LoadingIcon />
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className={`flex-col mt-5 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm mb-[2%]`}>
                        <div className="flex justify-between mb-3">
                            <Title title='viewApiKeyDetails.viewApiKeyDetails' subTitle='apiKeysList.listOfApiKeyRequests' backLink='/partnermanagement/admin/authentication-services/api-keys-list' />
                        </div>

                        {unexpectedError && (
                            <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center items-center">
                                        <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                        <p className="text-base font-semibold text-[#6F6E6E] pt-4">{t('commons.unexpectedError')}</p>
                                        <p className="text-sm font-semibold text-[#6F6E6E] pt-1 pb-4">{getErrorMessage(errorCode, t, errorMsg)}</p>
                                        <button onClick={moveToApiClientsList} type="button"
                                            className={`w-32 h-10 flex items-center justify-center font-semibold rounded-md text-sm mx-8 py-3 bg-tory-blue text-white`}>
                                            {t('commons.goBack')}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        )}

                        {!unexpectedError && (
                            <div className="bg-snow-white h-fit mt-1 rounded-t-xl shadow-lg font-inter">
                                <div className="flex justify-between px-7 pt-3 border-b max-[450px]:flex-col">
                                    <div className="flex-col">
                                        <p className="text-lg text-dark-blue mb-2">
                                            {t('apiKeysList.apiKeyName')}: <span className="font-semibold">{apiKeyDetails.apiKeyLabel}</span>
                                        </p>
                                        <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                            <div className={`${bgOfStatus(apiKeyDetails.status)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                                {getStatusCode(apiKeyDetails.status, t)}
                                            </div>
                                            <div className={`font-semibold ${isLoginLanguageRTL ? "mr-[1.4rem]" : "ml-[0.75rem]"} text-sm text-dark-blue`}>
                                                {t("viewOidcClientDetails.createdOn") + ' ' +
                                                    formatDate(apiKeyDetails.createdDateTime, "date")}
                                            </div>
                                            <div className="mx-1 text-gray-300">|</div>
                                            <div className="font-semibold text-sm text-dark-blue">
                                                {formatDate(apiKeyDetails.createdDateTime, "time")}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                                    <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                                        <div className={`w-[49%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                            <p id='api_key_details_partner_id_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewOidcClientDetails.partnerId")}
                                            </p>
                                            <p id='api_key_details_partner_id_context' className="font-[600] text-vulcan text-md">
                                                {apiKeyDetails.partnerId}
                                            </p>
                                        </div>
                                        <div className="mb-3 max-[600px]:w-[100%] w-[49%]">
                                            <p id='api_key_details_partner_type_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewOidcClientDetails.partnerType")}
                                            </p>
                                            <p id='api_key_details_auth_partner_context' className="font-[600] text-vulcan text-md">
                                                {t("partnerTypes.authPartner")}
                                            </p>
                                        </div>
                                        <div className="my-3 max-[600px]:w-[100%] w-[49%]">
                                            <p id='api_key_details_partner_type_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewAdminOidcClientDetails.organisation")}
                                            </p>
                                            <p id='api_key_details_auth_partner_context' className="font-[600] text-vulcan text-md">
                                                {apiKeyDetails.orgName}
                                            </p>
                                        </div>
                                    </div>
                                    <hr className={`h-px w-full bg-gray-200 border-0`} />
                                    <div className={`flex flex-wrap pt-3`}>
                                        <div className={`w-[49%] max-[600px]:w-[100%] ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                            <p id='api_key_details_policy_group_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewOidcClientDetails.policyGroup")}
                                            </p>
                                            <p id='api_key_details_policy_group_name_context' className="font-[600] text-vulcan text-md">
                                                {apiKeyDetails.policyGroupName}
                                            </p>
                                        </div>
                                        <div className={`w-[49%] max-[600px]:w-[100%]`}>
                                            <p id='api_key_details_policy_name_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewOidcClientDetails.policyName")}
                                            </p>
                                            <p id='api_key_details_policy_name_context' className="font-[600] text-vulcan text-md">
                                                {apiKeyDetails.policyName}
                                            </p>
                                        </div>
                                        <div className={`w-[49%] max-[600px]:w-[100%] my-4 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                            <p id='api_key_details_policy_group_description_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewOidcClientDetails.policyGroupDescription")}
                                            </p>
                                            <p id='api_key_details_policy_group_description_context' className="font-[600] text-vulcan text-md">
                                                {apiKeyDetails.policyGroupDescription}
                                            </p>
                                        </div>
                                        <div className={`w-[49%] max-[600px]:w-[100%] my-4 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                            <p id='api_key_details_policy_name_description_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewOidcClientDetails.policyNameDescription")}
                                            </p>
                                            <p id='api_key_details_policy_description_context' className="font-[600] text-vulcan text-md">
                                                {apiKeyDetails.policyDescription}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <hr className="h-px w-full bg-gray-200 border-0" />
                                <div className={`flex justify-end py-8 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                                    <button id="view_api_key_back_btn" onClick={moveToApiClientsList}
                                        className="h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center">
                                        {t("viewPolicyDetails.back")}
                                    </button>
                                </div>
                            </div>
                        )}
                    </div>
                </>
            )}
        </div>
    )
}

export default ViewAdminApiKeyDetails;