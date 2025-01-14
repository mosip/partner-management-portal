import React, { useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { bgOfStatus, copyClientId, formatDate, getErrorMessage, getGrantTypes, getPartnerManagerUrl, getStatusCode, handleMouseClickForDropdown, handleServiceErrors, isLangRTL, onPressEnterKey } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import Title from '../../common/Title';
import { useNavigate } from 'react-router-dom';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from '../../common/LoadingIcon';
import content_copy_icon from "../../../svg/content_copy_icon.svg";
import disabled_copy_icon from "../../../svg/disabled_copy_icon.svg";
import { HttpService } from '../../../services/HttpService';

function ViewAdminOidcClientDetails() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(false);
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [copied, setCopied] = useState(false);
    const [selectedClientData, setSelectedClientData] = useState({});
    const [oidcClientDetails, setOidcClientDetails] = useState({
        redirectUris: [],
        grantTypes: [],
    });
    const copyToolTipRef = useRef(null);

    useEffect(() => {
        const data = localStorage.getItem('selectedOidcClientAttributes');
        handleMouseClickForDropdown(copyToolTipRef, () => setCopied(false));
        if (!data) {
            setUnexpectedError(true);
            return;
        }
        const clientData = JSON.parse(data);
        setSelectedClientData(clientData);

        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(getPartnerManagerUrl(`/oauth/client/${clientData.clientId}`, process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        setOidcClientDetails(resData);
                        console.log(oidcClientDetails);
                    } else {
                        setUnexpectedError(true);
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('viewAdminOidcClientDetails.errorWhileGettingOidcClientDetails'))
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                setErrorMsg(err);
            }
        };
        fetchData();
    }, [copyToolTipRef]);

    const moveToOidcClientsList = () => {
        navigate('/partnermanagement/admin/authentication-services/oidc-clients-list');
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-24 ml-5" : "ml-24 mr-5"} font-inter relative`}>
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
                            <Title title='viewOidcClientDetails.viewOidcClientDetails' subTitle='oidcClientsList.listOfOidcClients' backLink='/partnermanagement/admin/authentication-services/oidc-clients-list' />
                        </div>
                        {unexpectedError && (
                            <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center items-center">
                                        <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                        <p className="text-base font-semibold text-[#6F6E6E] pt-4">{t('commons.unexpectedError')}</p>
                                        <p className="text-sm font-semibold text-[#6F6E6E] pt-1 pb-4">{getErrorMessage(errorCode, t, errorMsg)}</p>
                                        <button onClick={moveToOidcClientsList} type="button"
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
                                        <p className="text-lg text-dark-blue mb-2">{t('authenticationServices.oidcClientName')}: <span className="font-semibold">{selectedClientData.clientNameEng}</span></p>
                                        <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                            <div className={`${bgOfStatus(oidcClientDetails.status)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                                {getStatusCode(selectedClientData.status, t)}
                                            </div>
                                            <div className={`font-semibold ${isLoginLanguageRTL ? "mr-[1.4rem]" : "ml-[0.75rem]"} text-sm text-dark-blue`}>
                                                {t("viewOidcClientDetails.createdOn") + ' ' +
                                                    formatDate(selectedClientData.createdDateTime, "date", true)}
                                            </div>
                                            <div className="mx-1 text-gray-300">|</div>
                                            <div className="font-semibold text-sm text-dark-blue">
                                                {formatDate(selectedClientData.createdDateTime, "time", true)}
                                            </div>
                                        </div>
                                    </div>

                                    <button id="oidc_client_details_copy_id" className={`${oidcClientDetails.status === "ACTIVE" ? 'bg-[#F0F5FF] border-[#BED3FF] cursor-pointer hover:shadow-md' : 'bg-gray-200 border-gray-400'}  border h-[4%] w-[15%] max-[450px]:w-[40%] max-[800px]:w-[25%] ${isLoginLanguageRTL ? "pr-[3%] pl-[1.5%]" : "pl-[3%] pr-[1%]"} py-[0.5%] rounded-md text-right`}
                                         onClick={() => copyClientId(selectedClientData, selectedClientData.clientId, setCopied)} tabIndex={oidcClientDetails.status === "ACTIVE" && "0"}>
                                        <p className="text-sm font-semibold text-[#333333]">{t('viewOidcClientDetails.oidcClientId')}</p>
                                        <div className="flex space-x-1 items-center">
                                            <p className={`text-md font-bold ${selectedClientData.status === "ACTIVE" ? 'text-[#1447B2]' : 'text-gray-400'} truncate`}>
                                                {selectedClientData.clientId}
                                            </p>
                                            {selectedClientData.status === "ACTIVE" ? (
                                                <img id="oidc_client_details_copy_id_icon" src={content_copy_icon} alt=""/>
                                            ) : (
                                                <img src={disabled_copy_icon} alt="" />
                                            )}
                                            {copied &&
                                                (
                                                    <div ref={copyToolTipRef} className={`z-20 px-4 py-1 mt-[3.5rem] max-h-[32%] font-semibold overflow-y-auto absolute ${isLoginLanguageRTL ? "left-10" : "right-10"} shadow-lg bg-white border border-gray-300 rounded-md`}>
                                                        <p className="text-[#36393E] text-md font-inter">{t('viewOidcClientDetails.copied!')}</p>
                                                    </div>
                                                )
                                            }
                                        </div>
                                    </button>
                                </div>

                                <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                                    <div className="flex flex-wrap py-1 max-[450px]:flex-col justify-between">
                                        <div className={`w-[37.5rem] max-[600px]:w-full mb-3`}>
                                            <p id='oidc_client_details_partner_id_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewOidcClientDetails.partnerId")}
                                            </p>
                                            <p id='oidc_client_details_partner_id_context' className="font-[600] text-vulcan text-md">
                                                {selectedClientData.partnerId}
                                            </p>
                                        </div>
                                        <div className="mb-5 max-[600px]:w-full w-[37.5rem]">
                                            <p id='oidc_client_details_partner_type_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewOidcClientDetails.partnerType")}
                                            </p>
                                            <p id='oidc_client_details_auth_partner_context' className="font-[600] text-vulcan text-md">
                                                {t("partnerTypes.authPartner")}
                                            </p>
                                        </div>
                                        <div className="mb-3 max-[600px]:w-full w-[41rem]">
                                            <p id='oidc_client_details_partner_type_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewAdminOidcClientDetails.organisation")}
                                            </p>
                                            <p id='oidc_client_details_auth_partner_context' className="font-[600] text-vulcan text-md">
                                                {selectedClientData.orgName}
                                            </p>
                                        </div>
                                    </div>
                                    <hr className={`h-px w-full bg-gray-200 border-0`} />
                                    <div className={`flex flex-wrap pt-3 justify-between`}>
                                        <div className={`w-[37.5rem] max-[600px]:w-full`}>
                                            <p id='oidc_client_details_policy_group_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewOidcClientDetails.policyGroup")}
                                            </p>
                                            <p id='oidc_client_details_policy_group_name_context' className="font-[600] text-vulcan text-md">
                                                {selectedClientData.policyGroupName}
                                            </p>
                                        </div>
                                        <div className={`w-[37.5rem] max-[600px]:w-full`}>
                                            <p id='oidc_client_details_policy_name_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewOidcClientDetails.policyName")}
                                            </p>
                                            <p id='oidc_client_details_policy_name_context' className="font-[600] text-vulcan text-md">
                                                {selectedClientData.policyName ? selectedClientData.policyName : ' - '}
                                            </p>
                                        </div>
                                        <div className={`w-[37.5rem] max-[600px]:w-full my-3`}>
                                            <p id='oidc_client_details_policy_group_description_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewOidcClientDetails.policyGroupDescription")}
                                            </p>
                                            <p id='oidc_client_details_policy_group_description_context' className="font-[600] text-vulcan text-md">
                                                {selectedClientData.policyGroupDescription}
                                            </p>
                                        </div>
                                        <div className={`w-[37.5rem] max-[600px]:w-full my-3`}>
                                            <p id='oidc_client_details_policy_name_description_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewOidcClientDetails.policyNameDescription")}
                                            </p>
                                            <p id='oidc_client_details_policy_description_context' className="font-[600] text-vulcan text-md">
                                                {selectedClientData.policyDescription ? selectedClientData.policyDescription : ' - '}
                                            </p>
                                        </div>
                                    </div>
                                    <hr className="h-px w-full bg-gray-200 border-0" />
                                    <div className="space-y-6">
                                        <div className="my-3 space-y-2 break-all">
                                            <p id='oidc_client_details_public_key_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewOidcClientDetails.publicKey")}
                                            </p>
                                            <p id='oidc_client_details_public_key_context' className="font-[600] text-vulcan text-md text-wrap w-[90%]">
                                                {oidcClientDetails.publicKey}
                                            </p>
                                        </div>
                                        <div className="my-4 space-y-1 w-[41rem]">
                                            <p id='oidc_client_details_logo_uri_label' className="font-[600] text-suva-gray text-sm">
                                                {t("viewOidcClientDetails.logoUri")}
                                            </p>
                                            <p id='oidc_client_details_logo_uri_context' className="font-[600] text-vulcan text-md">
                                                {oidcClientDetails.logoUri}
                                            </p>
                                        </div>
                                        <div className="flex flex-wrap my-3 max-[800px]:flex-col max-[1020px]:flex-col justify-between">
                                            <div className={`flex-col space-y-1 w-[37.5rem]`}>
                                                <p id='oidc_client_details_redirect_uris' className="font-[600] text-suva-gray text-sm">
                                                    {t("viewOidcClientDetails.redirectUri")}
                                                </p>
                                                <div id='oidc_client_redirect_uris' className="flex-col">
                                                    {(oidcClientDetails.redirectUris).map((uri, index) => {
                                                        return (
                                                            <ul>
                                                                <li key={index} className={`space-y-3 mt-2 ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                                    <p className="text-md max-[450px]:text-sm max-[450px]:font-semibold font-[600] text-[#36393E] py-1">
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
                                            <div className="flex-col space-y-1 w-[37.5rem]">
                                                <p id='oidc_client_details_grant_types' className="font-[600] text-suva-gray text-sm max-[800px]:mt-4 max-[1020px]:mt-4">
                                                    {t("viewOidcClientDetails.grantTypes")}
                                                </p>
                                                <div id='oidc_client_grant_types' className="flex-col">
                                                    {(oidcClientDetails.grantTypes).map((type, index) => {
                                                        return (
                                                            <ul>
                                                                <li key={index} className={`space-y-4 text-sm ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                                    <p className="text-md max-[450px]:text-sm max-[450px]:font-semibold font-[600] text-[#36393E] py-1">
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
                                    <button id="oidc_client_details_back_btn" onClick={moveToOidcClientsList}
                                        className="h-10 w-[120px] text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center">
                                        {t("viewOidcClientDetails.back")}
                                    </button>
                                </div>
                            </div>
                        )}
                    </div>
                </>
            )
            }
        </div>
    )
}

export default ViewAdminOidcClientDetails;