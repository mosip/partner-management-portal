import React from "react";
import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { isLangRTL, handleMouseClickForDropdown, getGrantTypes, bgOfStatus, onPressEnterKey, copyClientId } from "../../../utils/AppUtils";
import { formatDate, moveToOidcClientsList, getStatusCode } from "../../../utils/AppUtils";
import content_copy_icon from "../../../svg/content_copy_icon.svg";
import disabled_copy_icon from "../../../svg/disabled_copy_icon.svg";
import Title from "../../common/Title";

function ViewOidcClientDetails() {
    const { t } = useTranslation();
    const [copied, setCopied] = useState(false);
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();
    const [oidcClientDetails, setOidcClientDetails] = useState({
        redirectUris: [],
        grantTypes: [],
    });
    const copyToolTipRef = useRef(null)

    useEffect(() => {
        const clientData = localStorage.getItem('selectedClientData');
        handleMouseClickForDropdown(copyToolTipRef, () => setCopied(false));
        if (clientData) {
            try {
                const selectedClient = JSON.parse(clientData);
                setOidcClientDetails(selectedClient);
            } catch (error) {
                navigate('/partnermanagement/authentication-services/oidc-clients-list');
                console.error('Error in viewOidcClientDetails page :', error);
            }
        } else {
            navigate('/partnermanagement/authentication-services/oidc-clients-list');
        }
    }, [navigate, copyToolTipRef]);

    const styleForTitle = {
        backArrowIcon: "!mt-[4%]"
    }

    return (
        <>
            <div className={`flex-col w-full p-4 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} overflow-x-scroll`}>
                <div className="flex justify-between mb-3">
                    <Title title='viewOidcClientDetails.viewOidcClientDetails' subTitle='authenticationServices.authenticationServices' backLink='/partnermanagement/authentication-services/oidc-clients-list' ></Title>
                </div>
                <div className="bg-snow-white h-fit mt-1 rounded-t-xl shadow-lg font-inter">
                    <div className="flex justify-between px-7 pt-3 border-b max-[450px]:flex-col">
                        <div className="flex-col">
                            <p className="font-bold text-sm text-dark-blue mb-2">{oidcClientDetails.clientNameEng}</p>
                            <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                <div className={`${bgOfStatus(oidcClientDetails.status)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                    {getStatusCode(oidcClientDetails.status, t)}
                                </div>
                                <div className={`font-semibold ${isLoginLanguageRTL ? "mr-1" : "ml-3"} text-sm text-dark-blue`}>
                                    {t("viewOidcClientDetails.createdOn") + ' ' +
                                        formatDate(oidcClientDetails.createdDateTime, "date", true)}
                                </div>
                                <div className="mx-1 text-gray-300">|</div>
                                <div className="font-semibold text-sm text-dark-blue">
                                    {formatDate(oidcClientDetails.createdDateTime, "time", true)}
                                </div>
                            </div>
                        </div>

                        <div id="oidc_client_details_copy_id" className={`${oidcClientDetails.status === "ACTIVE" ? 'bg-[#F0F5FF] border-[#BED3FF] cursor-pointer hover:shadow-md' : 'bg-gray-200 border-gray-400'}  border h-[4%] w-[15%] max-[450px]:w-[40%] max-[800px]:w-[25%] ${isLoginLanguageRTL ? "pr-[3%] pl-[1.5%]" : "pl-[3%] pr-[1%]"} py-[0.5%] rounded-md text-right`}
                            tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => copyClientId(oidcClientDetails, oidcClientDetails.clientId, setCopied))}>
                            <p className="text-sm font-semibold text-[#333333]">{t('viewOidcClientDetails.oidcClientId')}</p>
                            <div className="flex space-x-1 items-center">
                                <p className={`text-md font-bold ${oidcClientDetails.status === "ACTIVE" ? 'text-[#1447B2]' : 'text-gray-400'} truncate`}>
                                    {oidcClientDetails.clientId}
                                </p>
                                {oidcClientDetails.status === "ACTIVE" ? (
                                    <img id="oidc_client_details_copy_id_icon" src={content_copy_icon} alt="" onClick={() => copyClientId(oidcClientDetails, oidcClientDetails.clientId, setCopied)} />
                                ) : (
                                    <img src={disabled_copy_icon} alt="" />
                                )}
                                {copied &&
                                    (
                                        <div ref={copyToolTipRef} className={`z-20 px-4 py-1 mt-[3.5%] max-h-[32%] font-semibold overflow-y-auto absolute ${isLoginLanguageRTL ? "mr-[9.5%] left-16" : "ml-[80px] right-16"} shadow-lg bg-white border border-gray-300 rounded-md`}>
                                            <p className="text-[#36393E] text-md font-inter">{t('viewOidcClientDetails.copied!')}</p>
                                        </div>
                                    )
                                }
                            </div>
                        </div>
                    </div>

                    <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                        <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                            <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                                <p id='oidc_client_details_partner_id_label' className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.partnerId")}
                                </p>
                                <p id='oidc_client_details_partner_id_context' className="font-[600] text-vulcan text-sm">
                                    {oidcClientDetails.partnerId}
                                </p>
                            </div>
                            <div className="mb-3 max-[600px]:w-[100%] w-[50%]">
                                <p id='oidc_client_details_partner_type_label' className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.partnerType")}
                                </p>
                                <p id='oidc_client_details_auth_partner_context' className="font-[600] text-vulcan text-sm">
                                    {t("partnerTypes.authPartner")}
                                </p>
                            </div>
                        </div>
                        <hr className={`h-px w-full bg-gray-200 border-0`} />
                        <div className={`flex flex-wrap pt-3`}>
                            <div className={`w-[49%] max-[600px]:w-[100%] ${isLoginLanguageRTL ? "ml[1%]" : "mr-[1%]"}`}>
                                <p id='oidc_client_details_policy_group_label' className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.policyGroup")}
                                </p>
                                <p id='oidc_client_details_policy_group_name_context' className="font-[600] text-vulcan text-sm">
                                    {oidcClientDetails.policyGroupName}
                                </p>
                            </div>
                            <div className={`w-[50%] max-[600px]:w-[100%]`}>
                                <p id='oidc_client_details_policy_name_label' className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.policyName")}
                                </p>
                                <p id='oidc_client_details_policy_name_context' className="font-[600] text-vulcan text-sm">
                                    {oidcClientDetails.policyName}
                                </p>
                            </div>
                            <div className={`w-[49%] max-[600px]:w-[100%] my-3 ${isLoginLanguageRTL ? "ml[1%]" : "mr-[1%]"}`}>
                                <p id='oidc_client_details_policy_group_description_label' className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.policyGroupDescription")}
                                </p>
                                <p id='oidc_client_details_policy_group_description_context' className="font-[600] text-vulcan text-sm">
                                    {oidcClientDetails.policyGroupDescription}
                                </p>
                            </div>
                            <div className={`w-[50%] max-[600px]:w-[100%] my-3`}>
                                <p id='oidc_client_details_policy_name_description_label' className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.policyNameDescription")}
                                </p>
                                <p id='oidc_client_details_policy_description_context' className="font-[600] text-vulcan text-sm">
                                    {oidcClientDetails.policyDescription}
                                </p>
                            </div>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className="space-y-6">
                            <div className="my-4">
                                <p id='oidc_client_details_oidc_client_name_label' className="font-[600] text-suva-gray text-xs">
                                    {t("authenticationServices.oidcClientName")}
                                </p>
                                <p id='oidc_client_details_client_name_context' className="font-[600] text-vulcan text-sm">
                                    {oidcClientDetails.clientNameEng}
                                </p>
                            </div>
                            <div className="my-3 space-y-2 break-all">
                                <p id='oidc_client_details_public_key_label' className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.publicKey")}
                                </p>
                                <p id='oidc_client_details_public_key_context' className="font-[600] text-vulcan text-sm text-wrap w-[90%]">
                                    {oidcClientDetails.publicKey}
                                </p>
                            </div>
                            <div className="my-4 space-y-1">
                                <p id='oidc_client_details_logo_uri_label' className="font-[600] text-suva-gray text-xs">
                                    {t("viewOidcClientDetails.logoUri")}
                                </p>
                                <p id='oidc_client_details_logo_uri_context' className="font-[600] text-vulcan text-sm">
                                    {oidcClientDetails.logoUri}
                                </p>
                            </div>
                            <div className="flex flex-wrap my-3 max-[800px]:flex-col max-[1020px]:flex-col">
                                <div className="flex-col space-y-1 w-[50%]">
                                    <p id='oidc_client_details_redirect_uris' className="font-[600] text-suva-gray text-xs">
                                        {t("viewOidcClientDetails.redirectUri")}
                                    </p>
                                    <div id='oidc_client_redirect_uris' className="flex-col">
                                        {(oidcClientDetails.redirectUris).map((uri, index) => {
                                            return (
                                                <ul>
                                                    <li key={index} className={`space-y-3 mt-2 ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                        <p className="text-sm max-[450px]:text-xs max-[450px]:font-semibold font-[600] text-[#36393E] py-1">
                                                            {uri}
                                                        </p>
                                                        {(oidcClientDetails.redirectUris).length > 1 &&
                                                            (<hr className="h-px w-[72%] max-[800px]:w-[140%] border-[1px] bg-[#707070]" />)
                                                        }
                                                    </li>
                                                </ul>
                                            )
                                        })}
                                    </div>
                                </div>
                                <div className="flex-col space-y-1 w-[50%]">
                                    <p id='oidc_client_details_grant_types' className="font-[600] text-suva-gray text-xs max-[800px]:mt-4 max-[1020px]:mt-4">
                                        {t("viewOidcClientDetails.grantTypes")}
                                    </p>
                                    <div id='oidc_client_grant_types' className="flex-col">
                                        {(oidcClientDetails.grantTypes).map((type, index) => {
                                            return (
                                                <ul>
                                                    <li key={index} className={`space-y-4 text-sm ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                        <p className="max-[450px]:text-xs max-[450px]:font-semibold font-[600] text-[#36393E] text-sm py-1">
                                                            {getGrantTypes(type, t)}
                                                        </p>
                                                        {(oidcClientDetails.grantTypes).length > 1 &&
                                                            (<hr className="h-px w-[72%] bg-[#707070] border-[1px]" />)
                                                        }
                                                    </li>
                                                </ul>
                                            )
                                        })}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <hr className="h-px w-full bg-gray-200 border-0 mt-6" />
                    <div className={`flex justify-end py-5 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                        <button id="oidc_client_details_back_btn" onClick={() => moveToOidcClientsList(navigate)}
                            className="h-10 w-[120px] text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center">
                            {t("viewOidcClientDetails.back")}
                        </button>
                    </div>
                </div>
            </div>
        </>

    )
}

export default ViewOidcClientDetails;