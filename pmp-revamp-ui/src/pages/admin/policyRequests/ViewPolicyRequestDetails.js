import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { bgOfStatus, formatDate, getStatusCode, isLangRTL, onPressEnterKey } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import { useNavigate } from 'react-router-dom';
import Title from '../../common/Title';
import adminImage from "../../../svg/admin.png";
import partnerImage from "../../../svg/partner.png";
import dotImg from "../../../svg/dot.svg";

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
            <div className={`w-full p-5 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm overflow-x-scroll mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} mt-3`}>
                <div className="flex justify-between mb-3">
                    <Title title='viewPolicyRequest.viewPolicyRequest' subTitle='viewPolicyRequest.listOfPolicyRequests' backLink='/partnermanagement/admin/policy-requests-list' />
                </div>
                <div className="bg-snow-white h-fit mt-1 rounded-md shadow-lg font-inter">
                    <div className="flex justify-between px-7 pt-3 border-b max-[450px]:flex-col">
                        <div className="flex-col py-3">
                            <p className="text-lg text-dark-blue mb-2">
                                {t('partnerList.partnerId')}: <span className="font-semibold">{policyRequestDetails.partnerId}</span>
                            </p>
                            <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                <div className={`${bgOfStatus(policyRequestDetails.status)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                    {getStatusCode(policyRequestDetails.status, t)}
                                </div>
                                <div className={`font-semibold ${isLoginLanguageRTL ? "mr-[1.4rem]" : "ml-[0.75rem]"} text-sm text-dark-blue`}>
                                    {t("viewOidcClientDetails.createdOn") + ' ' +
                                        formatDate(policyRequestDetails.createdDateTime, "date")}
                                </div>
                                <div className="mx-2 text-gray-300">|</div>
                                <div className="font-semibold text-sm text-dark-blue">
                                    {formatDate(policyRequestDetails.createdDateTime, "time")}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                        <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                            <div className={`mb-3 max-[600px]:w-[100%] w-[50%] ${isLoginLanguageRTL ? "pl-[1%]" : "pr-[1%]"}`}>
                                <p className="font-[600] text-suva-gray text-sm">
                                    {t("viewPolicyRequest.partnerType")}
                                </p>
                                <p className="font-[600] text-vulcan text-md">
                                    {policyRequestDetails.partnerType}
                                </p>
                            </div>
                            <div className="w-[50%] max-[600px]:w-[100%] mb-3 px-2">
                                <p className="font-[600] text-suva-gray text-sm">
                                    {t("viewPolicyRequest.organisation")}
                                </p>
                                <p className="font-[600] text-vulcan text-md">
                                    {policyRequestDetails.orgName}
                                </p>
                            </div>
                        </div>
                        <div className={`flex flex-wrap pt-3`}>
                            <div className={`w-[50%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "pl-[1%]" : "pr-[1%]"}`}>

                                <p className="font-[600] text-suva-gray text-sm">
                                    {t("viewPolicyRequest.policyId")}
                                </p>
                                <p className="font-[600] text-vulcan text-md">
                                    {policyRequestDetails.policyId}
                                </p>
                            </div>
                            <div className={`w-[50%] max-[600px]:w-[100%] mb-3 px-2`}>
                                <p className="font-[600] text-suva-gray text-sm">
                                    {t("viewPolicyRequest.policyName")}
                                </p>
                                <p className="font-[600] text-vulcan text-md">
                                    {policyRequestDetails.policyName}
                                </p>
                            </div>
                        </div>
                        <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                            <div className={`mb-3 w-[50%] max-[600px]:w-[100%]`}>
                                <p className="font-[600] text-suva-gray text-sm">
                                    {t("viewPolicyRequest.policyGroup")}
                                </p>
                                <p className="font-[600] text-vulcan text-md">
                                    {policyRequestDetails.policyGroupName}
                                </p>
                            </div>
                            <div className={`w-[50%] max-[600px]:w-[100%] mb-3 px-2`}>
                                <p className="font-[600] text-suva-gray text-sm">
                                    {t("viewPolicyRequest.partnerStatus")}
                                </p>
                                <p className={`flex w-fit py-1 px-3 text-sm rounded-md my-1 font-semibold ${bgOfStatus(policyRequestDetails.partnerStatus)} text-md`}>
                                    <img src={dotImg} alt="" /> 
                                    <span className={`${isLoginLanguageRTL ? 'pr-2' : 'pl-2'}`}>{getStatusCode(policyRequestDetails.partnerStatus, t)}</span>
                                </p>
                            </div>
                        </div>
                        <hr className="h-px mt-3 w-full bg-gray-200 border-0" />
                        <div className="py-3">
                            <p className="font-semibold text-vulcan text-base mb-3">
                                {t("viewPolicyDetails.comments")}
                            </p>
                            <div>
                                <div className="flex font-semibold">
                                    <span className={`w-8 h-8 rounded-full flex justify-center items-center ${isLoginLanguageRTL ? "ml-3" : "mr-3"} text-sm text-white lg:w-10 lg:h-10`}>
                                        <div className={`relative flex-1 after:content-['']  after:w-0.5 after:h-[4rem] after:bg-gray-200 after:inline-block after:absolute ${isLoginLanguageRTL ? "after:right-[1.2rem]" : "after:left-[1.2rem]"} after:mt-7`}></div>
                                        <img src={adminImage} alt="Example" className="w-8 h-8" />
                                    </span>
                                    <div className="flex bg-floral-white w-full flex-col p-4 relative rounded-md overflow-hidden">
                                        <div className={`w-0 h-0 border-t-[0.5rem] border-t-transparent border-b-[0.5rem] border-b-transparent absolute top-4 ${isLoginLanguageRTL ? "-right-[0.38rem] border-l-[7px] border-l-[#FFF9F0]" : "-left-[0.38rem] border-r-[7px] border-r-[#FFF9F0]"}`}></div>
                                        <h4 className="text-sm text-[#031640]">
                                            {t("viewPolicyDetails.adminComments")}
                                        </h4>
                                        <div className="flex items-center justify-start mt-4">
                                            <div className={`${bgOfStatus(policyRequestDetails.status)} flex w-fit py-1.5 px-3 text-xs rounded-md`}>
                                                {getStatusCode(policyRequestDetails.status, t)}
                                            </div>
                                            <div>
                                                {policyRequestDetails.updatedDateTime && (
                                                    <div className="flex">
                                                        <div className={`font-semibold ${isLoginLanguageRTL ? "mr-3" : "ml-3"} text-sm text-dark-blue`}>
                                                            {formatDate(policyRequestDetails.updatedDateTime, "date")}
                                                        </div>
                                                        <div className="mx-3 text-gray-300">|</div>
                                                        <div className="font-semibold text-sm text-dark-blue">
                                                            {formatDate(policyRequestDetails.updatedDateTime, "time")}
                                                        </div>
                                                    </div>
                                                )}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="mt-4">
                                    <div className="flex font-semibold">
                                        <span className={`w-8 h-8 rounded-full flex justify-center items-center ${isLoginLanguageRTL ? "ml-3" : "mr-3"} text-sm lg:w-10 lg:h-10`}>
                                            <img src={partnerImage} alt="Example" className="w-8 h-8" />
                                        </span>
                                        <div className="flex bg-alice-green w-full flex-col p-4 relative rounded-md overflow-hidden">
                                            <div className={`w-0 h-0 border-t-[0.5rem] border-t-transparent border-b-[0.5rem] border-b-transparent absolute top-4 ${isLoginLanguageRTL ? "-right-[0.38rem] border-l-[#F2F5FC] border-l-[7px]" : "-left-[0.38rem] border-r-[#F2F5FC] border-r-[7px]"}`}></div>
                                            <h4 className="text-sm text-[#031640]">
                                                {t("viewPolicyDetails.partnerComment")}
                                            </h4>
                                            <span className="text-sm mt-3 break-words">
                                                {policyRequestDetails.partnerComment}
                                            </span>
                                            <hr className="h-px w-full bg-gray-200 border-0 my-4" />
                                            <div className="flex items-center justify-start">
                                                <div className="font-semibold text-xs text-dark-blue">
                                                    {t("viewPolicyDetails.createdOn") + ' ' +
                                                        formatDate(policyRequestDetails.createdDateTime, "date")}
                                                </div>
                                                <div className="mx-3 text-gray-300">|</div>
                                                <div className="font-semibold text-xs text-dark-blue">
                                                    {formatDate(policyRequestDetails.createdDateTime, "time")}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr className="h-px w-full bg-gray-200 border-0" />
                    <div className={`flex justify-end py-8 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                        <button id="view_api_key_back_btn" onClick={moveToPolicyRequestsList}
                            className="h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center" onKeyDown={(e) => onPressEnterKey(e, moveToPolicyRequestsList)}>
                            {t("commons.back")}
                        </button>
                    </div>
                </div>
            </div>
        </>
    )
}

export default ViewPolicyRequestDetails;