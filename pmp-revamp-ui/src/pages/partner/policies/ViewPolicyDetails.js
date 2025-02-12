import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { isLangRTL, formatDate, moveToPolicies, getStatusCode, getPartnerTypeDescription, bgOfStatus } from "../../../utils/AppUtils";
import adminImage from "../../../svg/admin.png";
import partnerImage from "../../../svg/partner.png";
import Title from "../../common/Title";

function ViewPolicyDetails() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();
    const [policyDetails, setPolicyDetails] = useState([]);

    useEffect(() => {
        const partnerData = localStorage.getItem('selectedPolicyAttributes');
        if (partnerData) {
            try {
                const selectedPartner = JSON.parse(partnerData);
                setPolicyDetails(selectedPartner);
            } catch (error) {
                navigate('/partnermanagement/policies/policies-list');
                console.error('Error in viewPolicyDetails page :', error);
            }
        } else {
            navigate('/partnermanagement/policies/policies-list');
        }
    }, [navigate]);

    const style = {
        backArrowIcon: "!mt-[6%]"
    }

    return (
        <>
            <div className={`w-full p-5 bg-anti-flash-white h-full break-words font-inter mb-[2%] ${isLoginLanguageRTL ? "mr-20 ml-1" : "ml-20 mr-1"} overflow-x-scroll`}>
                <div className="flex justify-between mb-5">
                    <Title title='viewPolicyDetails.viewPolicyDetails' subTitle='viewPolicyDetails.policySection' backLink='/partnermanagement/policies/policies-list' styleSet={style} />
                </div>
                <div className="bg-snow-white h-fit mt-1 rounded-t-xl shadow-lg ml-3">
                    <div className={`flex-col ${isLoginLanguageRTL ? "pr-8" : "pl-8"} pt-6 pb-5`}>
                        <p className="text-lg text-dark-blue mb-3">{t('policies.policyId')}: <span className="font-semibold">{policyDetails.policyId}</span></p>
                        <div className="flex items-center justify-start">
                            <div
                                className={`${bgOfStatus(
                                    policyDetails.status
                                )}flex w-fit py-1.5 px-3 text-sm rounded-md font-semibold`}
                            >
                                {getStatusCode(policyDetails.status, t)}
                            </div>
                            <div className={`font-semibold ${isLoginLanguageRTL ? "mr-3" : "ml-3"} text-xs text-dark-blue`}>
                                {t("viewPolicyDetails.createdOn") + ' ' +
                                    formatDate(policyDetails.createdDateTime, "date")}
                            </div>
                            <div className="mx-3 text-gray-300">|</div>
                            <div className="font-semibold text-xs text-dark-blue">
                                {formatDate(policyDetails.createdDateTime, "time")}
                            </div>
                        </div>
                    </div>
                    <hr className="h-px w-full bg-gray-200 border-0" />
                    <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-6 mb-4`}>
                        <div className="flex flex-wrap">
                            <div className={`w-[49%] mb-4 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                <p id='policy_details_partner_id_label' className="font-semibold text-suva-gray text-xs">
                                    {t("viewPolicyDetails.partnerId")}
                                </p>
                                <p id='policy_details_partner_id_context' className="font-semibold text-vulcan text-sm">
                                    {policyDetails.partnerId}
                                </p>
                            </div>
                            <div className="mb-5 w-[50%]">
                                <p id='policy_details_partner_type_label' className="font-semibold text-suva-gray text-xs">
                                    {t("viewPolicyDetails.partnerType")}
                                </p>
                                <p id='policy_details_partner_type_context' className="font-semibold text-vulcan text-sm">
                                    {getPartnerTypeDescription(policyDetails.partnerType, t)}
                                </p>
                            </div>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className="flex flex-wrap pt-4">
                            <div className={`w-[49%] ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                <p id='policy_details_policy_group_name_label' className="font-semibold text-suva-gray text-xs">
                                    {t("viewPolicyDetails.policyGroupName")}
                                </p>
                                <p id='policy_details_policy_group_name_context' className="font-semibold text-vulcan text-sm">
                                    {policyDetails.policyGroupName}
                                </p>
                            </div>
                            <div className="w-[50%]">
                                <p id='policy_details_policy_name_label' className="font-semibold text-suva-gray text-xs">
                                    {t("viewPolicyDetails.policyName")}
                                </p>
                                <p id='policy_details_policy_name_context' className="font-semibold text-vulcan text-sm">
                                    {policyDetails.policyName}
                                </p>
                            </div>
                            <div className={`w-[49%] my-5 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                <p id='policy_details_policy_group_description_label' className="font-semibold text-suva-gray text-xs">
                                    {t("viewPolicyDetails.policyGroupDescription")}
                                </p>
                                <p id='policy_details_policy_group_description_context' className="font-semibold text-vulcan text-sm">
                                    {policyDetails.policyGroupDescription}
                                </p>
                            </div>
                            <div className="w-[50%]  my-5">
                                <p id='policy_details_policy_name_description_label' className="font-semibold text-suva-gray text-xs">
                                    {t("viewPolicyDetails.policyNameDescription")}
                                </p>
                                <p id='policy_details_policy_name_description_context' className="font-semibold text-vulcan text-sm">
                                    {policyDetails.policyDescription}
                                </p>
                            </div>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className="mt-3">
                            <p id='policy_details_comments' className="font-semibold text-vulcan text-base mb-3">
                                {t("viewPolicyDetails.comments")}
                            </p>
                            <div>
                                <div className="flex font-semibold">
                                    <span className={`w-8 h-8 rounded-full flex justify-center items-center ${isLoginLanguageRTL ? "ml-3" : "mr-3"} text-sm text-white lg:w-10 lg:h-10`}>
                                        <div className={`relative flex-1 after:content-['']  after:w-0.5 after:h-[4rem] after:bg-gray-200 after:inline-block after:absolute ${isLoginLanguageRTL ? "after:right-[1.2rem]" : "after:left-[1.2rem]"} after:mt-7`}></div>
                                        <img src={adminImage} alt="Example" className="w-8 h-8" id='admin_image' />
                                    </span>
                                    <div className="flex bg-floral-white w-full flex-col p-4 relative rounded-md overflow-hidden">
                                        <div className={`w-0 h-0 border-t-[0.5rem] border-t-transparent border-b-[0.5rem] border-b-transparent absolute top-4 ${isLoginLanguageRTL ? "-right-[0.38rem] border-l-[7px] border-l-[#FFF9F0]" : "-left-[0.38rem] border-r-[7px] border-r-[#FFF9F0]"}`}></div>
                                        <h4 className="text-sm  text-[#031640]">
                                            {t("viewPolicyDetails.adminComments")}
                                        </h4>
                                        <div className="flex items-center justify-start mt-4 w-[79rem]">
                                            <div className={`${bgOfStatus(policyDetails.status)}flex w-fit py-1.5 px-3 text-xs rounded-md`}>
                                                {getStatusCode(policyDetails.status, t)}
                                            </div>
                                            <div>
                                                {policyDetails.updatedDateTime && (
                                                    <div className="flex">
                                                        <div className={`font-semibold ${isLoginLanguageRTL ? "mr-3" : "ml-3"} text-sm text-dark-blue`}>
                                                            {formatDate(policyDetails.updatedDateTime, "date")}
                                                        </div>
                                                        <div className="mx-3 text-gray-300">|</div>
                                                        <div className="font-semibold text-sm text-dark-blue">
                                                            {formatDate(policyDetails.updatedDateTime, "time")}
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
                                                {policyDetails.partnerComment}
                                            </span>
                                            <hr className="h-px w-full bg-gray-200 border-0 my-4" />
                                            <div className="flex items-center justify-start">
                                                <div className="font-semibold text-xs text-dark-blue">
                                                    {t("viewPolicyDetails.createdOn") + ' ' +
                                                        formatDate(policyDetails.createdDateTime, "date")}
                                                </div>
                                                <div className="mx-3 text-gray-300">|</div>
                                                <div className="font-semibold text-xs text-dark-blue">
                                                    {formatDate(policyDetails.createdDateTime, "time")}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr className="h-px w-full bg-gray-200 border-0" />
                    <div className={`flex justify-end py-5 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                        <button id="view_policy_back_btn"
                            onClick={() => moveToPolicies(navigate)}
                            className="h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center"
                        >
                            {t("viewPolicyDetails.back")}
                        </button>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ViewPolicyDetails;