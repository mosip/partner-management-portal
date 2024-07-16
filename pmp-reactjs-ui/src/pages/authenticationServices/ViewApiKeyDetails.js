import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../services/UserProfileService";
import { isLangRTL, moveToApiKeysList, getStatusCode, formatDate, bgOfStatus} from "../../utils/AppUtils";
import Title from "../common/Title";

function ViewApiKeyDetails() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();
    const [apiKeyDetails, setApiKeyDetails] = useState({});

    useEffect(() => {
        const clientData = localStorage.getItem('selectedApiKeyClientdata');
        if (clientData) {
            try {
                const selectedApiKeyClientdata = JSON.parse(clientData);
                setApiKeyDetails(selectedApiKeyClientdata);
            } catch (error) {
                navigate('/partnermanagement/authenticationServices/apiKeysList');
                console.error('Error in viewApiKeyDetails page :', error);
            }
        } else {
            navigate('/partnermanagement/authenticationServices/apiKeysList');
        }
    }, [navigate]);

    const styleForTitle = {
        backArrowIcon: "!mt-[4%]"
    }

    return (
        <>
            <div className={`flex-col w-full p-5 bg-anti-flash-white h-full font-inter break-all break-normal max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-[7%]" : "ml-[7%]"} overflow-x-scroll`}>
                <div className="flex justify-between mb-3">
                    <Title title='viewApiKeyDetails.viewApiKeyDetails' subTitle='authenticationServices.authenticationServices' backLink='/partnermanagement/authenticationServices/apiKeysList' styleSet={styleForTitle}></Title>
                </div>
                <div className="bg-snow-white h-fit mt-1 rounded-t-xl shadow-lg font-inter">
                    <div className="flex justify-between px-7 pt-3 border-b max-[450px]:flex-col">
                        <div className="flex-col">
                            <p className="font-bold text-sm text-dark-blue mb-2">
                                {apiKeyDetails.apiKeyLabel}
                            </p>
                            <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                <div className={`${bgOfStatus(apiKeyDetails.status)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                    {getStatusCode(apiKeyDetails.status, t)}
                                </div>
                                <div className={`font-semibold ${isLoginLanguageRTL ? "mr-1" : "ml-3"} text-sm text-dark-blue`}>
                                    {t("viewOidcClientDetails.createdOn") + ' ' +
                                        formatDate(apiKeyDetails.crDtimes, "date")}
                                </div>
                                <div className="mx-1 text-gray-300">|</div>
                                <div className="font-semibold text-sm text-dark-blue">
                                    {formatDate(apiKeyDetails.crDtimes, "time")}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                        <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                            <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.partnerId")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm">
                                    {apiKeyDetails.partnerId}
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
                            <div className={`w-[49%] max-[600px]:w-[100%] ${isLoginLanguageRTL ? "ml[1%]" : "mr-[1%]"}`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.policyGroup")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm break-normal">
                                    {apiKeyDetails.policyGroupName}
                                </p>
                            </div>
                            <div className={`w-[50%] max-[600px]:w-[100%]`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.policyName")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm break-normal">
                                    {apiKeyDetails.policyName}
                                </p>
                            </div>
                            <div className={`w-[49%] max-[600px]:w-[100%] my-3 ${isLoginLanguageRTL ? "ml[1%]" : "mr-[1%]"}`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.policyGroupDescription")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm break-normal">
                                    {apiKeyDetails.policyGroupDescription}
                                </p>
                            </div>
                            <div className={`w-[49%] max-[600px]:w-[100%] my-3 ${isLoginLanguageRTL ? "ml[1%]" : "mr-[1%]"}`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.policyNameDescription")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm break-normal">
                                    {apiKeyDetails.policyNameDescription}
                                </p>
                            </div>
                        </div>
                        <div className="my-4">
                            <p className="font-[600] text-suva-gray text-xs">
                                {t("viewApiKeyDetails.apiKeyName")}
                            </p>
                            <p className="font-[600] text-vulcan text-sm">
                                {apiKeyDetails.apiKeyLabel}
                            </p>
                        </div>
                    </div>
                    <hr className="h-px w-full bg-gray-200 border-0" />
                    <div className={`flex justify-end py-5 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                        <button onClick={() => moveToApiKeysList(navigate)}
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