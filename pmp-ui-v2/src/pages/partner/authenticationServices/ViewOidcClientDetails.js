import React, { useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { bgOfStatus, copyClientId, formatDate, formatPublicKey, getErrorMessage, getGrantTypes, getPartnerManagerUrl, getStatusCode, handleMouseClickForDropdown, handleServiceErrors, isLangRTL, getLanguageLabel, onPressEnterKey } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import Title from '../../common/Title';
import { useNavigate } from 'react-router-dom';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from '../../common/LoadingIcon';
import content_copy_icon from "../../../svg/content_copy_icon.svg";
import disabled_copy_icon from "../../../svg/disabled_copy_icon.svg";
import expandToggleIcon from '../../../svg/expand_toggle_icon.svg';
import { HttpService } from '../../../services/HttpService';

function ViewOidcClientDetails() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(false);
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [copied, setCopied] = useState(false);
    const [oidcClientDetails, setOidcClientDetails] = useState({
        redirectUris: [],
        grantTypes: [],
    });
    const [clientNameLangMap, setClientNameLangMap] = useState({});
    const [additionalConfig, setAdditionalConfig] = useState({});
    const [isPrimaryInfoExpanded, setIsPrimaryInfoExpanded] = useState(true);
    const [isAdditionalInfoExpanded, setIsAdditionalInfoExpanded] = useState(false);
    const copyToolTipRef = useRef(null);

    useEffect(() => {
        const cleanup = handleMouseClickForDropdown(copyToolTipRef, () => setCopied(false));
        return cleanup;
    }, [copyToolTipRef, setCopied]);

    useEffect(() => {
        const data = sessionStorage.getItem('selectedClientData');
        if (!data) {
            setDataLoaded(true);
            setUnexpectedError(true);
            return;
        }
        let clientData;
        try {
            clientData = JSON.parse(data);
        } catch (err) {
            console.error('Error parsing sessionStorage data:', err);
            setDataLoaded(true);
            setUnexpectedError(true);
            return;
        }

        const fetchData = async () => {
            try {
                setDataLoaded(false);
                // Use clientId from sessionStorage for the GET request
                const clientId = clientData.clientId;
                const response = await HttpService.get(getPartnerManagerUrl(`/oidc-clients/${clientId}`, process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        setOidcClientDetails(resData);

                        // Initialize client name lang map
                        if (resData.clientNameLangMap && typeof resData.clientNameLangMap === 'object') {
                            setClientNameLangMap(resData.clientNameLangMap);
                        }

                        // Initialize additionalConfig
                        if (resData.additionalConfig) {
                            setAdditionalConfig(resData.additionalConfig);
                        }
                    } else {
                        setUnexpectedError(true);
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('viewOidcClientDetails.errorWhileGettingOidcClientDetails'))
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                if (err.response?.status && err.response.status !== 401) {
                    setErrorMsg(err.toString());
                }
                setDataLoaded(true);
            }
        };
        fetchData();
    }, [t]);

    const moveToOidcClientsList = () => {
        navigate('/partnermanagement/authentication-services/oidc-clients-list');
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    // Get language label for display
    const getLanguageDisplayName = (languageCode) => {
        if (languageCode === '@none' || languageCode === 'default') {
            return t('createOidcClient.default');
        }
        return getLanguageLabel(languageCode, t);
    };

    // Format public key for display
    const getFormattedPublicKey = () => {
        if (!oidcClientDetails.publicKey) return '';
        try {
            if (typeof oidcClientDetails.publicKey === 'string') {
                return formatPublicKey(oidcClientDetails.publicKey);
            } else if (typeof oidcClientDetails.publicKey === 'object') {
                return JSON.stringify(oidcClientDetails.publicKey, null, 2);
            }
            return '';
        } catch (error) {
            return JSON.stringify(oidcClientDetails.publicKey, null, 2);
        }
    };

    const styles = {
        loadingDiv: "!py-[20%]"
    }

    return (
        <div className={`w-full p-4 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} overflow-x-scroll`}>
            {!dataLoaded && (
                <LoadingIcon styleSet={styles} />
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage id='view_oidc_client_details_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className={`flex-col mt-5 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm mb-[2%]`}>
                        <div className="flex justify-between mb-3">
                            <Title title='viewOidcClientDetails.viewOidcClientDetails' subTitle='authenticationServices.authenticationServices' backLink='/partnermanagement/authentication-services/oidc-clients-list' />
                        </div>
                        {unexpectedError && (
                            <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center items-center">
                                        <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                        <p id='view_oidc_clients_unexpected_error' className="text-base font-semibold text-[#6F6E6E] pt-4">{t('commons.unexpectedError')}</p>
                                        <p id='view_oidc_clients_unexpected_error_msg' className="text-sm font-semibold text-[#6F6E6E] pt-1 pb-4">{getErrorMessage(errorCode, t, errorMsg)}</p>
                                        <button onClick={moveToOidcClientsList} type="button" id='view_oidc_clients_go_back_btn'
                                            className={`w-32 h-10 flex items-center justify-center font-semibold rounded-md text-sm mx-8 py-3 bg-tory-blue text-white`}>
                                            {t('commons.goBack')}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        )}
                        {!unexpectedError && (
                            <>
                                {/* Primary Information Section */}
                                <div className="bg-snow-white h-fit mt-1 rounded-xl shadow-lg font-inter mb-4">
                                    <div className="px-7 py-4">
                                        <div
                                            className="flex items-center justify-between cursor-pointer"
                                            onClick={() => setIsPrimaryInfoExpanded(!isPrimaryInfoExpanded)}
                                            role="button"
                                            tabIndex="0"
                                            onKeyDown={(e) => onPressEnterKey(e, () => setIsPrimaryInfoExpanded(!isPrimaryInfoExpanded))}
                                        >
                                            <h3 className="text-lg font-semibold text-dark-blue">{t('createOidcClient.primaryInformation')}</h3>
                                            <img src={expandToggleIcon} alt="Toggle" className={`w-7 h-7 transform transition-transform ${isPrimaryInfoExpanded ? 'rotate-180' : ''}`} />
                                        </div>

                                        {isPrimaryInfoExpanded && (
                                            <>
                                                <div className="mt-4 border-b border-gray-200 pb-3">
                                                    <div className="flex justify-between items-start max-[450px]:flex-col">
                                                        <div className="flex-col flex-1">
                                                            <p id='view_oidc_clients_sub_title_id' className="text-lg text-dark-blue mb-2">{t('authenticationServices.oidcClientName')}: {oidcClientDetails.name}</p>
                                                            <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                                                <div id='view_oidc_clients_status' className={`${bgOfStatus(oidcClientDetails.status)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                                                    {getStatusCode(oidcClientDetails.status, t)}
                                                                </div>
                                                                <div id='view_oidc_clients_created_on' className={`font-semibold ${isLoginLanguageRTL ? "mr-[1.4rem]" : "ml-[0.75rem]"} text-sm text-dark-blue`}>
                                                                    {t("viewOidcClientDetails.createdOn") + ' ' +
                                                                        formatDate(oidcClientDetails.createdDateTime, "date")}
                                                                </div>
                                                                <div className="mx-1 text-gray-300">|</div>
                                                                <div id='view_oidc_clients_created_date_time' className="font-semibold text-sm text-dark-blue">
                                                                    {formatDate(oidcClientDetails.createdDateTime, "time")}
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <button id="oidc_client_details_copy_id" className={`${oidcClientDetails.status === "ACTIVE" ? 'bg-[#F0F5FF] border-[#BED3FF] cursor-pointer hover:shadow-md' : 'bg-gray-200 border-gray-400 cursor-default'} border h-[55px] w-[157px] max-[450px]:w-[40%] max-[800px]:w-[25%] px-3 py-2 rounded-md relative flex flex-col justify-center`}
                                                            onClick={() => copyClientId({ ...oidcClientDetails }, oidcClientDetails.id, setCopied)} tabIndex={oidcClientDetails.status === "ACTIVE" ? "0" : "-1"}>
                                                            <p id='view_oidc_client_id_label' className={`text-sm font-semibold text-[#333333] mb-1 ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>{t('viewOidcClientDetails.oidcClientId')}</p>
                                                            <div className={`flex space-x-1 items-center ${isLoginLanguageRTL ? "justify-start flex-row-reverse" : "justify-end"}`}>
                                                                <p id='view_oidc_client_id' className={`text-base font-bold ${oidcClientDetails.status === "ACTIVE" ? 'text-[#1447B2]' : 'text-gray-400'} truncate`}>
                                                                    {oidcClientDetails.id?.length > 10 ? oidcClientDetails.id.substring(0, 10) + '…' : oidcClientDetails.id}
                                                                </p>
                                                                {oidcClientDetails.status === "ACTIVE" ? (
                                                                    <img id="oidc_client_details_copy_id_icon" src={content_copy_icon} alt="" className="w-4 h-4 flex-shrink-0" />
                                                                ) : (
                                                                    <img src={disabled_copy_icon} alt="" className="w-4 h-4 flex-shrink-0" />
                                                                )}
                                                                {copied &&
                                                                    (
                                                                        <div ref={copyToolTipRef} className={`z-20 px-4 py-1 mt-10 min-h-8 font-semibold absolute ${isLoginLanguageRTL ? "left-8" : "right-8"} shadow-lg bg-white border border-gray-300 rounded-md`}>
                                                                            <p id='view_oidc_client_id_copied' className="text-[#36393E] text-base font-inter">{t('viewOidcClientDetails.copied!')}</p>
                                                                        </div>
                                                                    )
                                                                }
                                                            </div>
                                                        </button>
                                                    </div>
                                                </div>

                                                <div className={`pt-3 mb-2`}>
                                                    <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                                                        <div className={`w-[49%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                                            <p id='oidc_client_details_partner_id_label' className="font-[600] text-suva-gray text-sm">
                                                                {t("viewOidcClientDetails.partnerId")}
                                                            </p>
                                                            <p id='oidc_client_details_partner_id_context' className="font-[600] text-vulcan text-base">
                                                                {oidcClientDetails.relyingPartyId}
                                                            </p>
                                                        </div>
                                                        <div className={`w-[49%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                                            <p id='oidc_client_details_partner_type_label' className="font-[600] text-suva-gray text-sm">
                                                                {t("viewOidcClientDetails.partnerType")}
                                                            </p>
                                                            <p id='oidc_client_details_auth_partner_context' className="font-[600] text-vulcan text-base">
                                                                {t("partnerTypes.authPartner")}
                                                            </p>
                                                        </div>
                                                    </div>
                                                    <hr className={`h-px w-full bg-gray-200 border-0`} />
                                                    <div className={`flex flex-wrap pt-3 max-[600px]:flex-col`}>
                                                        {/* Policy Group Section */}
                                                        <div className={`w-[49%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                                            <div className="mb-3">
                                                                <p id='oidc_client_details_policy_group_label' className="font-[600] text-suva-gray text-sm">
                                                                    {t("viewOidcClientDetails.policyGroup")}
                                                                </p>
                                                                <p id='oidc_client_details_policy_group_name_context' className="font-[600] text-vulcan text-base">
                                                                    {oidcClientDetails.policyGroupName}
                                                                </p>
                                                            </div>
                                                            <div>
                                                                <p id='oidc_client_details_policy_group_description_label' className="font-[600] text-suva-gray text-sm">
                                                                    {t("viewOidcClientDetails.policyGroupDescription")}
                                                                </p>
                                                                <p id='oidc_client_details_policy_group_description_context' className="font-[600] text-vulcan text-base">
                                                                    {oidcClientDetails.policyGroupDescription}
                                                                </p>
                                                            </div>
                                                        </div>
                                                        {/* Policy Name Section */}
                                                        <div className={`w-[50%] max-[600px]:w-[100%] mb-3`}>
                                                            <div className="mb-3">
                                                                <p id='oidc_client_details_policy_name_label' className="font-[600] text-suva-gray text-sm">
                                                                    {t("viewOidcClientDetails.policyName")}
                                                                </p>
                                                                <p id='oidc_client_details_policy_name_context' className="font-[600] text-vulcan text-base">
                                                                    {oidcClientDetails.policyName}
                                                                </p>
                                                            </div>
                                                            <div>
                                                                <p id='oidc_client_details_policy_name_description_label' className="font-[600] text-suva-gray text-sm">
                                                                    {t("viewOidcClientDetails.policyNameDescription")}
                                                                </p>
                                                                <p id='oidc_client_details_policy_description_context' className="font-[600] text-vulcan text-base">
                                                                    {oidcClientDetails.policyDescription}
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <hr className="h-px w-full bg-gray-200 border-0" />
                                                    {/* Client Name Multilanguage - Table Format */}
                                                    {clientNameLangMap && Object.keys(clientNameLangMap).length > 0 && (
                                                        <div className="my-3 space-y-1">
                                                            <p id='oidc_client_details_client_name_multilang_label' className="font-[600] text-suva-gray text-sm mb-2">
                                                                {t("createOidcClient.clientNameMultilanguage")}
                                                            </p>
                                                            <div id='oidc_client_client_name_multilang' className="bg-white border border-[#0000001A] rounded-[20px] overflow-hidden w-[60%]">
                                                                <table className="w-full">
                                                                    <thead>
                                                                        <tr className="bg-[#F8F8F8] border-b border-[#0000001A]">
                                                                            <th className="py-3 px-4 text-left text-sm font-normal text-black">
                                                                                {t("viewOidcClientDetails.language")}
                                                                            </th>
                                                                            <th className="py-3 px-4 text-left text-sm font-normal text-[#36393e]">
                                                                                {t("authenticationServices.oidcClientName")}
                                                                            </th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        {Object.entries(clientNameLangMap).map(([language, text], index) => (
                                                                            <tr key={index + language} className={`border-b border-[#0000001A] ${index === Object.keys(clientNameLangMap).length - 1 ? '' : 'border-b-[0.5px]'}`}>
                                                                                <td className="py-3 px-4 text-sm font-normal text-black">
                                                                                    {getLanguageDisplayName(language)}
                                                                                </td>
                                                                                <td className="py-3 px-4 text-sm font-normal text-[#36393e]">
                                                                                    {text}
                                                                                </td>
                                                                            </tr>
                                                                        ))}
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    )}
                                                    <div className="space-y-6">
                                                        <div className="my-3 space-y-2">
                                                            <p id='oidc_client_details_public_key_label' className="font-[600] text-suva-gray text-sm">
                                                                {t("viewOidcClientDetails.publicKey")}
                                                            </p>
                                                            <pre id="oidc_client_details_public_key_context" className="font-[600] text-vulcan text-sm w-full bg-snow-white focus:outline-none focus:ring-0 h-fit overflow-x-auto">
                                                                {getFormattedPublicKey()}
                                                            </pre>
                                                        </div>
                                                        <div className="my-4 space-y-1">
                                                            <p id='oidc_client_details_logo_uri_label' className="font-[600] text-suva-gray text-sm">
                                                                {t("viewOidcClientDetails.logoUri")}
                                                            </p>
                                                            <p id='oidc_client_details_logo_uri_context' className="font-[600] text-vulcan text-base">
                                                                {oidcClientDetails.logoUri}
                                                            </p>
                                                        </div>
                                                        <div className="flex flex-wrap my-3 max-[800px]:flex-col max-[1020px]:flex-col">
                                                            <div className={`flex-col space-y-1 w-[50%] ${isLoginLanguageRTL ? "pl-[1%]" : "pr-[1%]"}`}>
                                                                <p id='oidc_client_details_redirect_uris' className="font-[600] text-suva-gray text-sm">
                                                                    {t("viewOidcClientDetails.redirectUri")}
                                                                </p>
                                                                <div id='oidc_client_redirect_uris' className="flex-col">
                                                                    <ul>
                                                                        {oidcClientDetails.redirectUris && oidcClientDetails.redirectUris.length > 0 ? (
                                                                            oidcClientDetails.redirectUris.map((uri, index) => (
                                                                                <li key={index + uri} className={`space-y-3 mt-2 ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                                                    <p className="text-base  font-[600] text-[#36393E] py-1">
                                                                                        {uri}
                                                                                    </p>
                                                                                    {oidcClientDetails.redirectUris.length > 1 && (
                                                                                        <hr className="h-px w-[72%] max-[800px]:w-[140%] border-[1px] bg-[#707070]" />
                                                                                    )}
                                                                                </li>
                                                                            ))
                                                                        ) : (
                                                                            <li className={`${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                                                <p className="text-base font-[600] text-[#36393E] py-1">-</p>
                                                                            </li>
                                                                        )}
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                            <div className="flex-col space-y-1 w-[50%]">
                                                                <p id='oidc_client_details_grant_types' className="font-[600] text-suva-gray text-sm max-[800px]:mt-4 max-[1020px]:mt-4">
                                                                    {t("viewOidcClientDetails.grantTypes")}
                                                                </p>
                                                                <div id='oidc_client_grant_types' className="flex-col">
                                                                    <ul>
                                                                        {oidcClientDetails.grantTypes && oidcClientDetails.grantTypes.length > 0 ? (
                                                                            oidcClientDetails.grantTypes.map((type, index) => (
                                                                                <li key={index + type} className={`space-y-4 ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                                                    <p className="text-base font-[600] text-[#36393E] py-1">
                                                                                        {getGrantTypes(type, t)}
                                                                                    </p>
                                                                                    {oidcClientDetails.grantTypes.length > 1 && (
                                                                                        <hr className="h-px w-[72%] bg-[#707070] border-[1px]" />
                                                                                    )}
                                                                                </li>
                                                                            ))
                                                                        ) : (
                                                                            <li className={`${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                                                <p className="text-base font-[600] text-[#36393E] py-1">-</p>
                                                                            </li>
                                                                        )}
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>
                                            </>
                                        )}
                                    </div>
                                </div>

                                {/* Additional Information Section */}
                                {additionalConfig && Object.keys(additionalConfig).length > 0 && (
                                    <div className="bg-snow-white h-fit mt-4 rounded-xl shadow-lg font-inter mb-4">
                                        <div className="px-7 py-4">
                                            <div
                                                className="flex items-center justify-between cursor-pointer"
                                                onClick={() => setIsAdditionalInfoExpanded(!isAdditionalInfoExpanded)}
                                                role="button"
                                                tabIndex="0"
                                                onKeyDown={(e) => onPressEnterKey(e, () => setIsAdditionalInfoExpanded(!isAdditionalInfoExpanded))}
                                            >
                                                <h3 className="text-lg font-semibold text-dark-blue">{t('createOidcClient.additionalInformation')}</h3>
                                                <img src={expandToggleIcon} alt="Toggle" className={`w-7 h-7 transform transition-transform ${isAdditionalInfoExpanded ? 'rotate-180' : ''}`} />
                                            </div>

                                            {isAdditionalInfoExpanded && (
                                                <div className={`pt-3 mb-2`}>
                                                    {/* Toggles */}
                                                    <div className="flex flex-wrap max-[600px]:flex-col gap-4 mb-4">
                                                        {additionalConfig.forgot_pwd_link_required !== undefined && (
                                                            <div className={`flex items-center gap-2 ${isLoginLanguageRTL ? "flex-row-reverse" : ""}`}>
                                                                <div className={`relative inline-flex items-center flex-shrink-0`}>
                                                                    <div className={`relative w-9 h-5 rounded-full transition-colors duration-200 ease-in-out ${additionalConfig.forgot_pwd_link_required ? 'bg-[#1447B2]' : 'bg-neutral-100'}`}>
                                                                        <div className={`absolute top-0.5 left-0.5 w-4 h-4 bg-white rounded-full shadow-sm transform transition-transform duration-200 ease-in-out ${additionalConfig.forgot_pwd_link_required ? 'translate-x-4' : ''}`}></div>
                                                                    </div>
                                                                </div>
                                                                <p className="font-[600] text-vulcan text-sm">
                                                                    {t('createOidcClient.forgotPasswordBanner')}
                                                                </p>
                                                            </div>
                                                        )}
                                                        {additionalConfig.signup_banner_required !== undefined && (
                                                            <div className={`flex items-center gap-2 ${isLoginLanguageRTL ? "flex-row-reverse" : ""}`}>
                                                                <div className={`relative inline-flex items-center flex-shrink-0`}>
                                                                    <div className={`relative w-9 h-5 rounded-full transition-colors duration-200 ease-in-out ${additionalConfig.signup_banner_required ? 'bg-[#1447B2]' : 'bg-neutral-100'}`}>
                                                                        <div className={`absolute top-0.5 left-0.5 w-4 h-4 bg-white rounded-full shadow-sm transform transition-transform duration-200 ease-in-out ${additionalConfig.signup_banner_required ? 'translate-x-4' : ''}`}></div>
                                                                    </div>
                                                                </div>
                                                                <p className="font-[600] text-vulcan text-sm">
                                                                    {t('createOidcClient.signUpBanner')}
                                                                </p>
                                                            </div>
                                                        )}
                                                    </div>

                                                    {/* Consent Expiry and User Info Response Type */}
                                                    <div className="flex flex-wrap max-[600px]:flex-col pt-3">
                                                        {additionalConfig.consent_expire_in_mins !== undefined && (
                                                            <div className={`w-[49%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                                                <p className="font-[600] text-suva-gray text-sm">
                                                                    {t('createOidcClient.consentExpiryDuration')}
                                                                </p>
                                                                <p className="font-[600] text-vulcan text-base">
                                                                    {additionalConfig.consent_expire_in_mins} {t('createOidcClient.mins')}
                                                                </p>
                                                            </div>
                                                        )}
                                                        {additionalConfig.userinfo_response_type && (
                                                            <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                                                                <p className="font-[600] text-suva-gray text-sm">
                                                                    {t('createOidcClient.userInfoResponseType')}
                                                                </p>
                                                                <p className="font-[600] text-vulcan text-base">
                                                                    {additionalConfig.userinfo_response_type === 'JWS' ? t('createOidcClient.jws') : t('createOidcClient.jwe')}
                                                                </p>
                                                            </div>
                                                        )}
                                                    </div>

                                                    {/* Purpose Type */}
                                                    {additionalConfig.purpose && additionalConfig.purpose.type && (
                                                        <div className="mb-3 pt-3">
                                                            <p className="font-[600] text-suva-gray text-sm">
                                                                {t('createOidcClient.purposeType')}
                                                            </p>
                                                            <p className="font-[600] text-vulcan text-base">
                                                                {additionalConfig.purpose.type === 'login' ? t('createOidcClient.login') :
                                                                    additionalConfig.purpose.type === 'link' ? t('createOidcClient.link') :
                                                                        additionalConfig.purpose.type === 'verify' ? t('createOidcClient.verify') :
                                                                            additionalConfig.purpose.type}
                                                            </p>
                                                        </div>
                                                    )}

                                                    {/* Purpose Title - Table Format */}
                                                    {additionalConfig.purpose && additionalConfig.purpose.title && Object.keys(additionalConfig.purpose.title).length > 0 && (
                                                        <div className="my-3 space-y-1">
                                                            <p className="font-[600] text-suva-gray text-sm mb-2">
                                                                {t('createOidcClient.purposeTitle')}
                                                            </p>
                                                            <div className="bg-white border border-[#0000001A] rounded-[20px] overflow-hidden w-[60%]">
                                                                <table className="w-full">
                                                                    <thead>
                                                                        <tr className="bg-[#F8F8F8] border-b border-[#0000001A]">
                                                                            <th className="py-3 px-4 text-left text-sm font-normal text-black">
                                                                                {t("viewOidcClientDetails.language")}
                                                                            </th>
                                                                            <th className="py-3 px-4 text-left text-sm font-normal text-[#031640]">
                                                                                {t('createOidcClient.purposeTitle')}
                                                                            </th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        {Object.entries(additionalConfig.purpose.title).map(([langKey, text], index) => (
                                                                            <tr key={index + langKey} className={`border-b border-[#0000001A] ${index === Object.keys(additionalConfig.purpose.title).length - 1 ? '' : 'border-b-[0.5px]'}`}>
                                                                                <td className="py-3 px-4 text-sm font-normal text-black">
                                                                                    {getLanguageDisplayName(langKey)}
                                                                                </td>
                                                                                <td className="py-3 px-4 text-sm font-normal text-[#031640]">
                                                                                    {text}
                                                                                </td>
                                                                            </tr>
                                                                        ))}
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    )}

                                                    {/* Purpose Subtitle - Table Format */}
                                                    {additionalConfig.purpose && additionalConfig.purpose.subTitle && Object.keys(additionalConfig.purpose.subTitle).length > 0 && (
                                                        <div className="my-3 space-y-1">
                                                            <p className="font-[600] text-suva-gray text-sm mb-2">
                                                                {t('createOidcClient.purposeSubtitle')}
                                                            </p>
                                                            <div className="bg-white border border-[#0000001A] rounded-[20px] overflow-hidden w-[60%]">
                                                                <table className="w-full">
                                                                    <thead>
                                                                        <tr className="bg-[#F8F8F8] border-b border-[#0000001A]">
                                                                            <th className="py-3 px-4 text-left text-sm font-normal text-black">
                                                                                {t("viewOidcClientDetails.language")}
                                                                            </th>
                                                                            <th className="py-3 px-4 text-left text-sm font-normal text-[#031640]">
                                                                                {t('createOidcClient.purposeSubtitle')}
                                                                            </th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        {Object.entries(additionalConfig.purpose.subTitle).map(([langKey, text], index) => (
                                                                            <tr key={index + langKey} className={`border-b border-[#0000001A] ${index === Object.keys(additionalConfig.purpose.subTitle).length - 1 ? '' : 'border-b-[0.5px]'}`}>
                                                                                <td className="py-3 px-4 text-sm font-normal text-black">
                                                                                    {getLanguageDisplayName(langKey)}
                                                                                </td>
                                                                                <td className="py-3 px-4 text-sm font-normal text-[#031640]">
                                                                                    {text}
                                                                                </td>
                                                                            </tr>
                                                                        ))}
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    )}
                                                </div>
                                            )}
                                        </div>
                                    </div>
                                )}

                                <div className={`flex justify-end py-5`}>
                                    <button id="oidc_client_details_back_btn" onClick={moveToOidcClientsList}
                                        className="h-10 w-36 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center">
                                        {t("viewOidcClientDetails.back")}
                                    </button>
                                </div>
                            </>
                        )}
                    </div>
                </>
            )
            }
        </div>
    )
}

export default ViewOidcClientDetails;
