import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { bgOfStatus, formatDate, getStatusCode, isLangRTL, onPressEnterKey } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import { useNavigate } from 'react-router-dom';
import Title from '../../common/Title';

function ViewPolicyRequestDetails() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();
    const [policyRequestDetails, setPolicyRequestDetails] = useState({});

    useEffect(() => {
        const policyRequestData = localStorage.getItem('selectedPartnerPolicyRequest');
        if (policyRequestData) {
            try {
                const selectedPolicyRequestData = JSON.parse(policyRequestData);
                setPolicyRequestDetails(selectedPolicyRequestData);
            } catch (error) {
                navigate('/partnermanagement/admin/policy-requests-list');
                console.error('Error in viewPolicyRequestDetails page :', error);
            }
        } else {
            navigate('/partnermanagement/admin/policy-requests-list');
        }
    }, [navigate]);

    const moveToPolicyRequestsList = () => {
        navigate('/partnermanagement/admin/policy-requests-list');
    };

    return (
        <>
            <div className={`flex-col w-full p-5 bg-anti-flash-white h-full font-inter break-all break-normal max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} mt-5`}>
                <div className="flex justify-between mb-3">
                    <Title title='viewPolicyRequest.viewPolicyRequest' subTitle='viewPolicyRequest.listOfPolicyRequests' backLink='/partnermanagement/admin/policy-requests-list' />
                </div>
                <div className="bg-snow-white h-fit mt-1 rounded-md shadow-lg font-inter">
                    <div className="flex justify-between px-7 pt-3 border-b max-[450px]:flex-col">
                        <div className="flex-col py-3">
                            <p className="font-bold text-sm text-dark-blue mb-2">
                                {policyRequestDetails.policyName}
                            </p>
                            <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                <div className={`${bgOfStatus(policyRequestDetails.status)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                    {getStatusCode(policyRequestDetails.status, t)}
                                </div>
                                <div className={`font-semibold ${isLoginLanguageRTL ? "mr-1" : "ml-3"} text-sm text-dark-blue`}>
                                    {t("viewOidcClientDetails.createdOn") + ' ' +
                                        formatDate(policyRequestDetails.createdDateTime, "date", false)}
                                </div>
                                <div className="mx-1 text-gray-300">|</div>
                                <div className="font-semibold text-sm text-dark-blue">
                                    {formatDate(policyRequestDetails.createdDateTime, "time", false)}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                        <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                            <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewPolicyRequest.partnerId")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm">
                                    {policyRequestDetails.partnerId}
                                </p>
                            </div>
                            <div className="mb-3 max-[600px]:w-[100%] w-[50%]">
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewPolicyRequest.partnerType")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm">
                                    {policyRequestDetails.partnerType}
                                </p>
                            </div>
                        </div>
                        <div className={`flex flex-wrap pt-3`}>
                            <div className={`w-[49%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "ml[1%]" : "mr-[1%]"}`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewPolicyRequest.policyId")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm break-normal">
                                    {policyRequestDetails.policyId}
                                </p>
                            </div>
                            <div className={`w-[50%] max-[600px]:w-[100%]`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewPolicyRequest.policyDesc")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm break-normal">
                                    {policyRequestDetails.policyDesc ? policyRequestDetails.policyDesc : '-'}
                                </p>
                            </div>
                            <div className={`w-[49%] max-[600px]:w-[100%] my-3 ${isLoginLanguageRTL ? "ml[1%]" : "mr-[1%]"}`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewPolicyRequest.policyGroup")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm break-normal">
                                    {policyRequestDetails.policyGroupName}
                                </p>
                            </div>
                            <div className={`w-[49%] max-[600px]:w-[100%] my-3 ${isLoginLanguageRTL ? "ml[1%]" : "mr-[1%]"}`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewPolicyRequest.policyGroupDesc")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm break-normal">
                                    {policyRequestDetails.policyGroupDesc ? policyRequestDetails.policyGroupDesc : '-'}
                                </p>
                            </div>
                        </div>
                    </div>
                    <hr className="h-px w-full bg-gray-200 border-0" />
                    <div className={`flex justify-end py-8 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                        <button id="view_api_key_back_btn" onClick={moveToPolicyRequestsList}
                            className="h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center" onKeyPress={(e) => onPressEnterKey(e, moveToPolicyRequestsList)}>
                            {t("commons.back")}
                        </button>
                    </div>
                </div>
            </div>
        </>
    )
}

export default ViewPolicyRequestDetails;