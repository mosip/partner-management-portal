import React from "react";
import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../services/UserProfileService";
import { isLangRTL, handleMouseClickForDropdown, getGrantTypes } from "../../utils/AppUtils";
import backArrow from "../../svg/back_arrow.svg";
import { formatDate, moveToOidcClientsList, getStatusCode } from "../../utils/AppUtils";
import adminImage from "../../svg/admin.png";
import partnerImage from "../../svg/partner.png";
import CopyIdPopUp from "./CopyIdPopUp";

function ViewApiKeyDetails() {
    const { t } = useTranslation();
    const [copied, setCopied] = useState(false);
    const [showPopup, setShowPopup] = useState(false);
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();

    const moveToHome = () => {
        navigate("/partnermanagement");
    };

    function bgOfStatus(status) {
        if (status === "ACTIVE") {
            return ("bg-[#D1FADF] text-[#155E3E]")
        }
        else if (status === "INACTIVE") {
            return ("bg-[#EAECF0] text-[#525252]")
        }
    };

    const showCopyPopUp = () => {
        setShowPopup(true);
    };

    return (
        <>
            <div className={`flex-col w-full p-4 bg-anti-flash-white h-full font-inter break-all max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-[7%]" : "ml-[7%]"} overflow-x-scroll`}>
                <div className="flex justify-between mb-3">
                    <div className="flex items-center gap-x-2">
                        <img
                            src={backArrow}
                            alt=""
                            onClick={() => moveToOidcClientsList(navigate)}
                            className={`cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`}
                        />
                        <div className="flex-col">
                            <h1 className="font-bold text-lg text-dark-blue">
                                {t("viewApiKeyDetails.viewApiKeyDetails")}
                            </h1>
                            <div className="flex space-x-1">
                                <p onClick={() => moveToHome()}
                                    className="font-semibold text-tory-blue text-xs cursor-pointer">
                                    {t("commons.home")} /
                                </p>
                                <p onClick={() => moveToOidcClientsList(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                    {t("viewOidcClientDetails.authenticationServiceSection")}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="bg-snow-white h-fit mt-1 rounded-t-xl shadow-lg font-inter">
                    <div className="flex justify-between px-7 pt-3 border-b max-[450px]:flex-col">
                        <div className="flex-col">
                            <p className="font-bold text-sm text-dark-blue mb-2">
                                {t('viewApiKeyDetails.apiKeyNameGoeshere')}
                            </p>
                            <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                <div className={`${bgOfStatus("ACTIVE")} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                    {getStatusCode("ACTIVE", t)}
                                </div>
                                <div className={`font-medium ${isLoginLanguageRTL ? "mr-1" : "ml-3"} text-sm text-dark-blue`}>
                                    {t("viewOidcClientDetails.createdOn") + ' ' +
                                        formatDate("2024-05-30T14:52:30.580593", "date")}
                                </div>
                                <div className="mx-1 text-gray-300">|</div>
                                <div className="font-medium text-sm text-dark-blue">
                                    {formatDate("2024-05-30T14:52:30.580593", "time")}
                                </div>
                            </div>
                        </div>
                        <div className={`${"ACTIVE" === "ACTIVE" ? 'bg-[#F0F5FF] border-[#BED3FF] cursor-pointer hover:shadow-md' : 'bg-gray-200 border-gray-400'} flex gap-1 items-center border h-[4%] px-[1%] py-[0.5%] rounded-md text-right`}>
                            <svg onClick={() => showCopyPopUp()}
                                xmlns="http://www.w3.org/2000/svg" width="22.634" height="15.433" viewBox="0 0 22.634 15.433">
                                <path id="visibility_FILL0_wght400_GRAD0_opsz48"
                                    d="M51.32-787.911a4.21,4.21,0,0,0,3.1-1.276,4.225,4.225,0,0,0,1.273-3.1,4.21,4.21,0,0,0-1.276-3.1,4.225,4.225,0,0,0-3.1-1.273,4.21,4.21,0,0,0-3.1,1.276,4.225,4.225,0,0,0-1.273,3.1,4.21,4.21,0,0,0,1.276,3.1A4.225,4.225,0,0,0,51.32-787.911Zm-.009-1.492a2.764,2.764,0,0,1-2.039-.842,2.794,2.794,0,0,1-.836-2.045,2.764,2.764,0,0,1,.842-2.039,2.794,2.794,0,0,1,2.045-.836,2.764,2.764,0,0,1,2.039.842,2.794,2.794,0,0,1,.836,2.045,2.764,2.764,0,0,1-.842,2.039A2.794,2.794,0,0,1,51.311-789.4Zm.006,4.836a11.528,11.528,0,0,1-6.79-2.135A13,13,0,0,1,40-792.284a13.006,13.006,0,0,1,4.527-5.582A11.529,11.529,0,0,1,51.317-800a11.529,11.529,0,0,1,6.79,2.135,13.006,13.006,0,0,1,4.527,5.582,13,13,0,0,1-4.527,5.581A11.528,11.528,0,0,1,51.317-784.568ZM51.317-792.284Zm0,6.173A10.351,10.351,0,0,0,57.04-787.8a10.932,10.932,0,0,0,3.974-4.488,10.943,10.943,0,0,0-3.97-4.488,10.33,10.33,0,0,0-5.723-1.685,10.351,10.351,0,0,0-5.727,1.685,11.116,11.116,0,0,0-4,4.488,11.127,11.127,0,0,0,4,4.488A10.33,10.33,0,0,0,51.313-786.111Z"
                                    transform="translate(-40 800)" fill="#1447B2" />
                            </svg>
                            <p className="text-sm font-bold text-[#1447B2]">{t('viewApiKeyDetails.viewApiKeyId')}</p>
                            {showPopup && (
                                <CopyIdPopUp closePopUp={setShowPopup} partnerId={"P28394092"} policyName={"Policy Name Goes here"} id={"239492374"} idType={"API Key ID"} />
                            )}
                        </div>
                    </div>
                    <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                        <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                            <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.partnerId")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm">
                                    P238478392
                                </p>
                            </div>
                            <div className="mb-3 max-[600px]:w-[100%] w-[50%]">
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.partnerType")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm">
                                    {t("partnerTypes.authPartner")}
                                </p>
                            </div>
                        </div>
                        <hr className={`h-px w-full bg-gray-200 border-0`} />
                        <div className={`flex flex-wrap pt-3`}>
                            <div className={`w-[50%] max-[600px]:w-[100%]`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.policyGroup")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm">
                                    Policy Group Goes Here
                                </p>
                            </div>
                            <div className={`w-[50%] max-[600px]:w-[100%]`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.policyName")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm">
                                    Policy Name Goes Here
                                </p>
                            </div>
                            <div className={`w-[50%] max-[600px]:w-[100%] my-3`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.policyGroupDescription")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm">
                                    Policy group details, descriptive goes here.
                                </p>
                            </div>
                            <div className={`w-[50%] max-[600px]:w-[100%] my-3`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.policyNameDescription")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm">
                                    Policy Name details, descriptive goes here.
                                </p>
                            </div>
                        </div>
                        <div className="my-4">
                            <p className="font-[600] text-suva-gray text-xs">
                                {t("viewApiKeyDetails.label")}
                            </p>
                            <p className="font-[600] text-vulcan text-sm">
                                Label Goes Here
                            </p>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className="mt-3">
                            <p className="font-medium text-vulcan text-base mb-3">
                                {t("viewPolicyDetails.comments")}
                            </p>
                            <div>
                                <div className="flex font-medium w-full ">
                                    <span className={`w-8 h-8 rounded-full flex justify-center items-center ${isLoginLanguageRTL ? "ml-3" : "mr-3"} text-sm text-white lg:w-10 lg:h-10`}>
                                        <div className={`relative flex-1 after:content-['']  after:w-0.5 after:h-[4rem] after:bg-gray-200 after:inline-block after:absolute ${isLoginLanguageRTL ? "after:right-[1.2rem]" : "after:left-[1.2rem]"} after:mt-7`}></div>
                                        <img src={adminImage} alt="Example" className="w-8 h-8" />
                                    </span>
                                    <div className="flex bg-floral-white w-full flex-col p-4 relative rounded-md">
                                        <div className={`w-0 h-0 border-t-[0.5rem] border-t-transparent border-b-[0.5rem] border-b-transparent absolute top-4 ${isLoginLanguageRTL ? "-right-[0.38rem] border-l-[7px] border-l-[#FFF9F0]" : "-left-[0.38rem] border-r-[7px] border-r-[#FFF9F0]"}`}></div>
                                        <h4 className="text-sm  text-[#031640]">
                                            {t("viewPolicyDetails.adminComments")}/{t("viewPolicyDetails.adminName")}
                                        </h4>
                                        <span className="text-sm mt-3 break-all text-[#666666]">
                                            Admin Comments Goes here
                                        </span>
                                        <div className="flex items-center justify-start mt-4">
                                            <div className={`${bgOfStatus("ACTIVE")}flex w-fit py-1.5 px-3 text-xs rounded-md`}>
                                                {getStatusCode("ACTIVE", t)}
                                            </div>
                                            <div>
                                                <div className="flex">
                                                    <div className={`font-medium ${isLoginLanguageRTL ? "mr-3" : "ml-3"} text-sm text-dark-blue`}>
                                                        {formatDate("2024-05-30T14:52:30.580593", "date")}
                                                    </div>
                                                    <div className="mx-3 text-gray-300">|</div>
                                                    <div className="font-medium text-sm text-dark-blue">
                                                        {formatDate("2024-05-30T14:52:30.580593", "time")}
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="mt-4">
                                    <div className="flex font-medium w-full">
                                        <span className={`w-8 h-8 rounded-full flex justify-center items-center ${isLoginLanguageRTL ? "ml-3" : "mr-3"} text-sm lg:w-10 lg:h-10`}>
                                            <img src={partnerImage} alt="Example" className="w-8 h-8" />
                                        </span>
                                        <div className="flex bg-alice-green w-full flex-col p-4 relative rounded-md">
                                            <div className={`w-0 h-0 border-t-[0.5rem] border-t-transparent border-b-[0.5rem] border-b-transparent absolute top-4 ${isLoginLanguageRTL ? "-right-[0.38rem] border-l-[#F2F5FC] border-l-[7px]" : "-left-[0.38rem] border-r-[#F2F5FC] border-r-[7px]"}`}></div>
                                            <h4 className="text-sm text-[#031640]">
                                                {t("viewPolicyDetails.partnerComments")}
                                            </h4>
                                            <span className="text-sm mt-3 break-all text-[#666666]">
                                                Partner's Comments Goes Here
                                            </span>
                                            <hr className="h-px w-full bg-gray-200 border-0 my-4" />
                                            <div className="flex items-center justify-start">
                                                <div className="font-medium text-xs text-dark-blue">
                                                    {t("viewPolicyDetails.createdOn") + ' ' +
                                                        formatDate("2024-05-30T14:52:30.580593", "date")}
                                                </div>
                                                <div className="mx-3 text-gray-300">|</div>
                                                <div className="font-medium text-xs text-dark-blue">
                                                    {formatDate("2024-05-30T14:52:30.580593", "time")}
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
                        <button onClick={() => moveToOidcClientsList(navigate)}
                            className="h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center"
                        >
                            {t("viewPolicyDetails.back")}
                        </button>
                    </div>
                </div>
            </div>
        </>
    )
}

export default ViewApiKeyDetails;