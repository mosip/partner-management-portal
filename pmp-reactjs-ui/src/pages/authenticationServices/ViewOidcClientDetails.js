import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../services/UserProfileService";
import { isLangRTL } from "../../utils/AppUtils";
import backArrow from "../../svg/back_arrow.svg";
import { formatDate, moveToOidcClientsList } from "../../utils/AppUtils";
import adminImage from "../../svg/admin.png";
import clientImage from "../../svg/partner.png";
import content_copy_icon from "../../svg/content_copy_icon.svg";

function ViewOidcClientDetails() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();
    const [oidcClientDetails, setOidcClientDetails] = useState({
        redirectUris: [],
        grantTypes: [],
    });

    useEffect(() => {
        const clientData = localStorage.getItem('selectedClientData');
        if (clientData) {
            try {
                const selectedClient = JSON.parse(clientData);
                setOidcClientDetails(selectedClient);
            } catch (error) {
                navigate('/partnermanagement/authenticationServices/oidcClientsList');
                console.error('Error in viewOidcClientDetails page :', error);
            }
        } else {
            navigate('/partnermanagement/authenticationServices/oidcClientsList');
        }
    }, [navigate]);

    const moveToHome = () => {
        navigate("/partnermanagement");
    };


    function bgOfStatus(status) {
        if (status === "Approved") {
            return ("bg-[#D1FADF] text-[#155E3E]")
        }
        else if (status === "Rejected") {
            return ("bg-[#FAD6D1] text-[#5E1515]")
        }
        else if (status === "Pending for Approval") {
            return ("bg-[#FEF1C6] text-[#6D1C00]")
        }
        else if (status === "Deactivated") {
            return ("bg-[#EAECF0] text-[#525252]")
        }
    };

    return (
        <>
            <div className={`flex-col w-full p-5 bg-anti-flash-white h-full font-inter mb-[2%] ${isLoginLanguageRTL ? "mr-[8%]" : "ml-[8%]"} overflow-x-scroll`}>
                <div className="flex justify-between mb-5">
                    <div className="flex items-center gap-x-2">
                        <img
                            src={backArrow}
                            alt=""
                            onClick={() => moveToOidcClientsList(navigate)}
                            className={`cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`}
                        />
                        <div className="flex-col">
                            <h1 className="font-bold text-lg text-md text-dark-blue">
                                {t("viewOidcClientDetails.viewOidcClientDetails")}
                            </h1>
                            <div className="flex space-x-1">
                                <p
                                    onClick={() => moveToHome()}
                                    className="font-semibold text-tory-blue text-xs cursor-pointer"
                                >
                                    {t("commons.home")} /
                                </p>
                                <p onClick={() => moveToOidcClientsList(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                    {t("viewOidcClientDetails.authenticationServiceSection")}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="bg-snow-white h-fit mt-1 rounded-t-xl shadow-lg">
                    <div className={`flex-col p-6`}>
                        <div className="flex justify-between">
                            <div className="flex-col">
                                <p className="font-bold text-lg text-dark-blue mb-3">{oidcClientDetails.oidcClientName}</p>
                                <div className="flex items-center justify-start">
                                    <div className={`${bgOfStatus(oidcClientDetails.status)} flex w-fit py-1.5 px-3 text-xs rounded-md my-2 font-semibold`}>
                                        {oidcClientDetails.status}
                                    </div>
                                    <div className={`font-medium ${isLoginLanguageRTL ? "mr-3" : "ml-3"} text-sm text-dark-blue`}>
                                        {t("viewOidcClientDetails.createdOn") + ' ' +
                                            formatDate(oidcClientDetails.crDtimes, "date")}
                                    </div>
                                    <div className="mx-3 text-gray-300">|</div>
                                    <div className="font-medium text-sm text-dark-blue">
                                        {formatDate(oidcClientDetails.crDtimes, "time")}
                                    </div>
                                </div>
                            </div>
                            <button type="button"
                                className={`bg-[#F0F5FF] border-2 h-[4%] border-[#BED3FF] ${isLoginLanguageRTL ? "pr-[3%] pl-[1.5%]" : "pl-[3%] pr-[1.5%]"} py-[0.5%] text-right rounded-md cursor-pointer hover:shadow-md`}>
                                <p className="text-sm font-base">{t('viewOidcClientDetails.oidcClientId')}</p>
                                <div className="flex gap-x-2">
                                    <p className="text-md font-semibold text-[#1447B2]">
                                        {oidcClientDetails.oidcClientId}
                                    </p>
                                    <img src={content_copy_icon} />
                                </div>
                            </button>
                        </div>

                        <hr className="h-px w-full bg-gray-200 border-0 mt-[1%]" />
                        <div className={`${isLoginLanguageRTL ? "pr-2 ml-8" : "pl-2 mr-8"} pt-6 mb-4`}>
                            <div className="flex flex-wrap justify-between">
                                <div className="flex-col">
                                    <div className="mb-5">
                                        <p className="font-semibold text-suva-gray text-base">
                                            {t("viewOidcClientDetails.partnerId")}
                                        </p>
                                        <p className="font-semibold text-vulcan text-lg">
                                            {oidcClientDetails.partnerId}
                                        </p>
                                    </div>
                                    <div className="mb-5">
                                        <p className="font-semibold text-suva-gray text-base">
                                            {t("viewOidcClientDetails.partnerType")}
                                        </p>
                                        <p className="font-semibold text-vulcan text-lg">
                                            {t('Partner Type Goes Here')}
                                        </p>
                                    </div>
                                </div>

                                <div className="mb-5 w-[50%]">
                                    <p className="font-semibold text-suva-gray text-base">
                                        {t("viewOidcClientDetails.partnerIdAlias")}
                                    </p>
                                    <p className="font-semibold text-vulcan text-lg">
                                        {t('Partner ID ALias Goes Here')}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className="flex flex-wrap pt-6">
                            <div className="w-[50%]">
                                <p className="font-semibold text-suva-gray text-base">
                                    {t("viewOidcClientDetails.policyGroup")}
                                </p>
                                <p className="font-semibold text-vulcan text-lg">
                                    {oidcClientDetails.policyGroupName}
                                </p>
                            </div>
                            <div className="w-[50%]">
                                <p className="font-semibold text-suva-gray text-base">
                                    {t("viewOidcClientDetails.policyName")}
                                </p>
                                <p className="font-semibold text-vulcan text-lg">
                                    {oidcClientDetails.policyName}
                                </p>
                            </div>
                            <div className="w-[50%] my-6">
                                <p className="font-semibold text-suva-gray text-base">
                                    {t("viewOidcClientDetails.policyGroupDescription")}
                                </p>
                                <p className="font-semibold text-vulcan text-lg">
                                    {oidcClientDetails.policyGroupDescription}
                                </p>
                            </div>
                            <div className="w-[50%]  my-6">
                                <p className="font-semibold text-suva-gray text-base">
                                    {t("viewOidcClientDetails.policyNameDescription")}
                                </p>
                                <p className="font-semibold text-vulcan text-lg">
                                    {oidcClientDetails.policyNameDescription}
                                </p>
                            </div>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className="flex-col mt-6">
                            <div className="mb-4">
                                <p className="font-semibold text-suva-gray text-base">
                                    {t("viewOidcClientDetails.name")}
                                </p>
                                <p className="font-semibold text-vulcan text-lg">
                                    {oidcClientDetails.oidcClientName}
                                </p>
                            </div>
                            <div className="mb-4">
                                <p className="font-semibold text-suva-gray text-base">
                                    {t("viewOidcClientDetails.publicKey")}
                                </p>
                                <p className="font-semibold text-vulcan text-lg">
                                    {oidcClientDetails.publicKey}
                                </p>
                            </div>
                            <div className="mb-4">
                                <p className="font-semibold text-suva-gray text-base">
                                    {t("viewOidcClientDetails.logoUri")}
                                </p>
                                <p className="font-semibold text-vulcan text-lg">
                                    {oidcClientDetails.logoUri}
                                </p>
                            </div>
                            <div className="flex justify-between w-[60%] mb-4">
                                <div className="flex-col">
                                    <p className="font-semibold text-suva-gray text-base">
                                        {t("viewOidcClientDetails.redirectUri")}
                                    </p>
                                    <div className="flex flex-col">
                                        {oidcClientDetails.redirectUris.map((uri, index) => {
                                            return (
                                                <ul>
                                                    <li className="p-1">{uri}</li>
                                                    <hr className="h-px bg-gray-200 border-2" />
                                                </ul>
                                            )
                                        })}
                                    </div>
                                </div>
                                <div className="flex-col">
                                    <p className="font-semibold text-suva-gray text-base">
                                        {t("viewOidcClientDetails.grantTypes")}
                                    </p>
                                    <div className="flex flex-col">
                                        {oidcClientDetails.grantTypes.map((type, index) => {
                                            return (
                                                <ul>
                                                    <li className="p-1">{type}</li>
                                                    <hr className="h-px bg-gray-200 border-2" />
                                                </ul>
                                            )
                                        })}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className="mt-4">
                            <p className="font-medium text-vulcan text-lg mb-4">
                                {t("viewOidcClientDetails.comments")}
                            </p>
                            <div>
                                <div className="flex font-medium w-full ">
                                    <span className={`w-8 h-8 rounded-full flex justify-center items-center ${isLoginLanguageRTL ? "ml-3" : "mr-3"} text-sm text-white lg:w-10 lg:h-10`}>
                                        <div className={`relative flex-1 after:content-['']  after:w-0.5 after:h-[4rem] after:bg-gray-200 after:inline-block after:absolute ${isLoginLanguageRTL ? "after:right-[1.2rem]" : "after:left-[1.2rem]"} after:mt-7`}></div>
                                        <img src={adminImage} alt="Example" className="" />
                                    </span>
                                    <div className="flex bg-floral-white w-full flex-col px-4 relative rounded-md">
                                        <div className={`w-0 h-0 border-t-[0.5rem] border-t-transparent border-b-[0.5rem] border-b-transparent absolute top-4 ${isLoginLanguageRTL ? "-right-[0.38rem] border-l-[7px] border-l-[#FFF9F0]" : "-left-[0.38rem] border-r-[7px] border-r-[#FFF9F0]"}`}></div>
                                        <h4 className="text-lg  text-[#031640]">
                                            {t("viewOidcClientDetails.adminComment") + " / " + t("viewOidcClientDetails.adminName")}
                                        </h4>
                                        <div className="flex-col items-center justify-start my-2">
                                            <p className="text-[#666666] text-sm ">{t('viewOidcClientDetails.commentsOfAdmin')}</p>
                                            <div className={`${bgOfStatus(oidcClientDetails.status)} flex w-fit py-1.5 px-3 text-xs rounded-md my-2`}>
                                                {oidcClientDetails.status}
                                            </div>
                                            <div>
                                                {oidcClientDetails.updDtimes && (
                                                    <div className="flex">
                                                        <div className={`font-medium ${isLoginLanguageRTL ? "mr-3" : "ml-3"} text-sm text-dark-blue`}>
                                                            {formatDate(oidcClientDetails.updDtimes, "date")}
                                                        </div>
                                                        <div className="mx-3 text-gray-300">|</div>
                                                        <div className="font-medium text-sm text-dark-blue">
                                                            {formatDate(oidcClientDetails.updDtimes, "time")}
                                                        </div>
                                                    </div>
                                                )}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="mt-4">
                                    <div className="flex font-medium w-full">
                                        <span className={`w-8 h-8 rounded-full flex justify-center items-center ${isLoginLanguageRTL ? "ml-3" : "mr-3"} text-sm lg:w-10 lg:h-10`}>
                                            <img src={clientImage} alt="Example" className="" />
                                        </span>
                                        <div className="flex bg-alice-green w-full flex-col px-4 py-3 relative rounded-md">
                                            <div className={`w-0 h-0 border-t-[0.5rem] border-t-transparent border-b-[0.5rem] border-b-transparent absolute top-4 ${isLoginLanguageRTL ? "-right-[0.38rem] border-l-[#F2F5FC] border-l-[7px]" : "-left-[0.38rem] border-r-[#F2F5FC] border-r-[7px]"}`}></div>
                                            <h4 className="text-lg  text-[#031640]">
                                                {t("viewOidcClientDetails.partnersComment")}
                                            </h4>

                                            <span className="text-sm mt-3 break-all">
                                                {oidcClientDetails.partnerComments}
                                            </span>
                                            <hr className="h-px w-full bg-gray-200 border-0 my-1" />
                                            <div className="flex items-center justify-start">
                                                <div className="font-medium text-sm text-dark-blue">
                                                    {t("viewOidcClientDetails.createdOn") + ' ' +
                                                        formatDate(oidcClientDetails.crDtimes, "date")}
                                                </div>
                                                <div className="mx-3 text-gray-300">|</div>
                                                <div className="font-medium text-sm text-dark-blue">
                                                    {formatDate(oidcClientDetails.crDtimes, "time")}
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
                            className="h-11 w-[120px] text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center">
                            {t("viewOidcClientDetails.back")}
                        </button>
                    </div>
                </div>
            </div>
        </>

    )
}

export default ViewOidcClientDetails;