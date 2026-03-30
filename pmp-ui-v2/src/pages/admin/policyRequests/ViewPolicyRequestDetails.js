import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { bgOfStatus, formatDate, getApproveRejectStatus, getPartnerManagerUrl, getPartnerTypeDescription, getStatusCode, handleServiceErrors, isLangRTL, onPressEnterKey } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import { useNavigate } from 'react-router-dom';
import Title from '../../common/Title';
import adminImage from "../../../svg/admin.png";
import partnerImage from "../../../svg/partner.png";
import dotImg from "../../../svg/dot.svg";
import PartnerPolicyApproveRejectPopup from './PartnerPolicyApproveRejectPopup';
import { HttpService } from '../../../services/HttpService';
import LoadingIcon from '../../common/LoadingIcon';

function ViewPolicyRequestDetails() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const navigate = useNavigate();
    const [policyRequestDetails, setPolicyRequestDetails] = useState({});
    const [showApproveRejectPopup, setShowApproveRejectPopup] = useState(false);

    const [bioLoading, setBioLoading] = useState(false);
    const [bioError, setBioError] = useState('');
    const [bioExtractors, setBioExtractors] = useState([]);
    const [credentialTypeLoading, setCredentialTypeLoading] = useState(false);
    const [credentialTypeError, setCredentialTypeError] = useState('');
    const [credentialType, setCredentialType] = useState('');

    useEffect(() => {
        const policyRequestData = sessionStorage.getItem('selectedPartnerPolicyRequest');
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

    const isCredentialPartner = (policyRequestDetails?.partnerType ?? '').toString().toUpperCase() === 'CREDENTIAL_PARTNER';

    const normalizeBioRow = (item) => {
        const modality =
            item?.bioModality ??
            item?.biometricModality ??
            item?.modality ??
            item?.bio_modality ??
            item?.biometric ??
            '-';
        const configuration =
            item?.bioExtractorConfigurationName ??
            item?.bioExtractorConfigName ??
            item?.configName ??
            item?.bio_extractor_configuration_name ??
            item?.bioExtractorConfigurationId ??
            item?.bio_extractor_configuration_id ??
            item?.attributeName ??
            '-';
        const providerVersion =
            item?.bioextractorProviderVersion ??
            item?.providerVersion ??
            item?.version ??
            item?.provider_version ??
            item?.extractor?.version ??
            '-';
        const providerName =
            item?.bioextractorProviderName ??
            item?.providerName ??
            item?.name ??
            item?.provider_name ??
            item?.extractor?.provider ??
            '-';
        return { modality, configuration, providerVersion, providerName };
    };

    useEffect(() => {
        const fetchDetails = async () => {
            if (!isCredentialPartner) return;
            if (!policyRequestDetails?.partnerId || !policyRequestDetails?.policyId) return;

            setBioError('');
            setBioExtractors([]);
            setCredentialTypeError('');
            setCredentialType('');
            setBioLoading(true);
            setCredentialTypeLoading(true);

            try {
                const bioUrl = getPartnerManagerUrl(`/partners/${policyRequestDetails.partnerId}/bioextractors/${policyRequestDetails.policyId}`, process.env.NODE_ENV);
                const credUrl = getPartnerManagerUrl(`/partners/${policyRequestDetails.partnerId}/policies/${policyRequestDetails.policyId}/credential-type`, process.env.NODE_ENV);

                const [bioRes, credRes] = await Promise.all([
                    HttpService.get(bioUrl),
                    HttpService.get(credUrl),
                ]);

                const bioData = bioRes?.data;
                if (bioData?.response) {
                    const payload = bioData.response;
                    const list = Array.isArray(payload)
                        ? payload
                        : (payload.extractors ??
                            payload.data ??
                            payload.bioExtractors ??
                            payload.bioextractors ??
                            payload.extractorList ??
                            []);
                    setBioExtractors(Array.isArray(list) ? list : []);
                } else if (bioData) {
                    handleServiceErrors(bioData, () => { }, (msg) => setBioError(msg), t);
                } else {
                    setBioError(t('commons.somethingWentWrong', 'Something went wrong.'));
                }

                const credData = credRes?.data;
                if (credData?.response !== undefined) {
                    const payload = credData.response;
                    const value =
                        (typeof payload === 'string' ? payload : null) ??
                        payload?.credentialType ??
                        payload?.credential_type ??
                        payload?.type ??
                        payload?.data?.credentialType ??
                        payload?.data?.credential_type ??
                        '';

                    const types =
                        payload?.credentialTypes ??
                        payload?.credential_types ??
                        payload?.data?.credentialTypes ??
                        payload?.data?.credential_types ??
                        null;

                    if (Array.isArray(types)) {
                        const first = types.find((x) => x !== null && x !== undefined && String(x).trim() !== '');
                        setCredentialType(first ? String(first) : '');
                    } else {
                        setCredentialType(value ? String(value) : '');
                    }
                } else if (credData) {
                    handleServiceErrors(credData, () => { }, (msg) => setCredentialTypeError(msg), t);
                } else {
                    setCredentialTypeError(t('commons.somethingWentWrong', 'Something went wrong.'));
                }
            } catch (err) {
                if (err?.response?.status !== 401) {
                    setBioError(err?.message || err?.toString() || t('commons.somethingWentWrong', 'Something went wrong.'));
                    setCredentialTypeError(err?.message || err?.toString() || t('commons.somethingWentWrong', 'Something went wrong.'));
                }
            } finally {
                setBioLoading(false);
                setCredentialTypeLoading(false);
            }
        };

        fetchDetails();
    }, [isCredentialPartner, policyRequestDetails?.partnerId, policyRequestDetails?.policyId, t]);

    const moveToPolicyRequestsList = () => {
        navigate('/partnermanagement/admin/policy-requests-list');
    };

    const onClickApproveReject = (responseData, status) => {
        if (responseData !== "") {
            setShowApproveRejectPopup(false);
            const updatedPolicyRequestDetails = {...policyRequestDetails, status: getApproveRejectStatus(status)};
            setPolicyRequestDetails(updatedPolicyRequestDetails);
            sessionStorage.setItem('selectedPartnerPolicyRequest', JSON.stringify(updatedPolicyRequestDetails));
        }
    }

    return (
        <>
            <div className={`w-full p-5 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm overflow-x-scroll mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} mt-3`}>
                <div className="flex justify-between mb-3">
                    <Title title='viewPolicyRequest.viewPolicyRequest' subTitle='viewPolicyRequest.listOfPolicyRequests' backLink='/partnermanagement/admin/policy-requests-list' />
                </div>
                <div className="bg-snow-white h-fit mt-1 rounded-md shadow-lg font-inter">
                    <div className="flex justify-between items-center px-7 pt-3 border-b max-[450px]:flex-col">
                        <div className="flex-col py-3">
                            <p id='view_partner_policy_sub_title_id' className="text-lg text-dark-blue mb-2">
                                {t('partnerList.partnerId')}: <span className="font-semibold">{policyRequestDetails.partnerId}</span>
                            </p>
                            <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                <div id='view_partner_policy_request_status' className={`${bgOfStatus(policyRequestDetails.status)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                    {getStatusCode(policyRequestDetails.status, t)}
                                </div>
                                <div id='view_partner_policy_request_created_on' className={`font-semibold ${isLoginLanguageRTL ? "mr-[1.4rem]" : "ml-[0.75rem]"} text-sm text-dark-blue`}>
                                    {t("viewOidcClientDetails.createdOn") + ' ' +
                                        formatDate(policyRequestDetails.createdDateTime, "date")}
                                </div>
                                <div className="mx-2 text-gray-300">|</div>
                                <div id='view_partner_policy_request_created_date_time' className="font-semibold text-sm text-dark-blue">
                                    {formatDate(policyRequestDetails.createdDateTime, "time")}
                                </div>
                            </div>
                        </div>
                        {policyRequestDetails.status === 'InProgress' && (
                            <>
                                <div>
                                    <button id="view_approve_reject_btn" onClick={() => setShowApproveRejectPopup(true)}
                                        className="h-fit w-fit text-sm p-4 py-3 text-white bg-tory-blue border border-blue-800 font-medium rounded-md text-center" onKeyDown={(e) => onPressEnterKey(e, () => setShowApproveRejectPopup(true))}>
                                        {t("approveRejectPopup.approveReject")}
                                    </button>
                                </div>
                                {showApproveRejectPopup &&
                                    <PartnerPolicyApproveRejectPopup
                                        popupData={{ ...policyRequestDetails, isPartnerPolicyRequest: true }}
                                        closePopUp={() => setShowApproveRejectPopup(false)}
                                        approveRejectResponse={(responseData, status) => onClickApproveReject(responseData, status)}
                                        title={policyRequestDetails.policyName}
                                        subtitle={`# ${policyRequestDetails.policyId}`}
                                        header={t('partnerPolicyRequestApproveRejectPopup.header')}
                                        description={t('partnerPolicyRequestApproveRejectPopup.description')}
                                    />
                                }
                            </>
                        )}
                    </div>
                    <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                        <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                            <div className={`mb-3 max-[600px]:w-[100%] w-[50%] ${isLoginLanguageRTL ? "pl-[1%]" : "pr-[1%]"}`}>
                                <p id='view_partner_policy_request_partner_type_label' className="font-[600] text-suva-gray text-sm">
                                    {t("viewPolicyRequest.partnerType")}
                                </p>
                                <p id='view_partner_policy_request_partner_type_context' className="font-[600] text-vulcan text-base">
                                    {getPartnerTypeDescription(policyRequestDetails.partnerType, t) ?? policyRequestDetails.partnerType}
                                </p>
                            </div>
                            <div className="w-[50%] max-[600px]:w-[100%] mb-3 px-2">
                                <p id='view_partner_policy_request_organisation_label' className="font-[600] text-suva-gray text-sm">
                                    {t("viewPolicyRequest.organisation")}
                                </p>
                                <p id='view_partner_policy_request_organisation_context' className="font-[600] text-vulcan text-base">
                                    {policyRequestDetails.orgName}
                                </p>
                            </div>
                        </div>
                        <div className={`flex flex-wrap pt-3`}>
                            <div className={`w-[50%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "pl-[1%]" : "pr-[1%]"}`}>

                                <p id='view_partner_policy_request_policy_id_label' className="font-[600] text-suva-gray text-sm">
                                    {t("viewPolicyRequest.policyId")}
                                </p>
                                <p id='view_partner_policy_request_policy_id_context' className="font-[600] text-vulcan text-base">
                                    {policyRequestDetails.policyId}
                                </p>
                            </div>
                            <div className={`w-[50%] max-[600px]:w-[100%] mb-3 px-2`}>
                                <p id='view_partner_policy_request_policy_name_label' className="font-[600] text-suva-gray text-sm">
                                    {t("viewPolicyRequest.policyName")}
                                </p>
                                <p id='view_partner_policy_request_policy_name_context' className="font-[600] text-vulcan text-base">
                                    {policyRequestDetails.policyName}
                                </p>
                            </div>
                        </div>
                        <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                            <div className={`mb-3 w-[50%] max-[600px]:w-[100%]`}>
                                <p id='view_partner_policy_request_policy_group_label' className="font-[600] text-suva-gray text-sm">
                                    {t("viewPolicyRequest.policyGroup")}
                                </p>
                                <p id='view_partner_policy_request_policy_group_context' className="font-[600] text-vulcan text-base">
                                    {policyRequestDetails.policyGroupName}
                                </p>
                            </div>
                            <div className={`w-[50%] max-[600px]:w-[100%] mb-3 px-2`}>
                                <p id='view_partner_policy_request_partner_status_label' className="font-[600] text-suva-gray text-sm">
                                    {t("viewPolicyRequest.partnerStatus")}
                                </p>
                                <p id='view_partner_policy_request_partner_status_context' className={`flex w-fit py-1 px-3 text-sm rounded-md my-1 font-semibold ${bgOfStatus(policyRequestDetails.partnerStatus)} text-base`}>
                                    <img src={dotImg} alt="" /> 
                                    <span className={`${isLoginLanguageRTL ? 'pr-2' : 'pl-2'}`}>{getStatusCode(policyRequestDetails.partnerStatus, t)}</span>
                                </p>
                            </div>
                        </div>
                        {isCredentialPartner && (
                            <>
                                <hr className="h-px mt-3 w-full bg-gray-200 border-0" />
                                <div className="py-4">
                                    <div className="border border-[#E5EBFA] rounded-lg p-4">
                                        <p className={`text-sm font-semibold text-[#191919] ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                                            {t('partnerPolicyRequestApproveRejectPopup.bioExtractorProviderMapping')}
                                        </p>

                                        <div className="mt-1 overflow-x-auto">
                                            <table className="min-w-full table-fixed border border-[#E5EBFA] rounded-md overflow-hidden">
                                                <thead className="bg-[#F7F9FF] text-[#6F6E6E] text-xs">
                                                    <tr>
                                                        <th className={`w-1/4 px-3 py-2 font-semibold whitespace-normal leading-tight align-top ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                                                            <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioModalityLine1')}</span>
                                                            <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioModalityLine2')}</span>
                                                        </th>
                                                        <th className={`w-1/4 px-3 py-2 font-semibold whitespace-normal leading-tight align-top ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                                                            <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorConfigLine1')}</span>
                                                            <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorConfigLine2')}</span>
                                                        </th>
                                                        <th className={`w-1/4 px-3 py-2 font-semibold whitespace-normal leading-tight align-top ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                                                            <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorProviderVersionLine1')}</span>
                                                            <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorProviderVersionLine2')}</span>
                                                        </th>
                                                        <th className={`w-1/4 px-3 py-2 font-semibold whitespace-normal leading-tight align-top ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                                                            <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorProviderNameLine1')}</span>
                                                            <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorProviderNameLine2')}</span>
                                                        </th>
                                                    </tr>
                                                </thead>
                                                <tbody className="text-[#191919] text-sm">
                                                    {(bioLoading || credentialTypeLoading) && (
                                                        <tr>
                                                            <td colSpan={4} className="px-3 py-6">
                                                                <LoadingIcon styleSet={{ loadingDiv: '!py-0' }} />
                                                            </td>
                                                        </tr>
                                                    )}
                                                    {!bioLoading && bioError && (
                                                        <tr>
                                                            <td colSpan={4} className="px-3 py-3 text-crimson-red font-semibold">
                                                                {bioError}
                                                            </td>
                                                        </tr>
                                                    )}
                                                    {!bioLoading && !bioError && bioExtractors.length === 0 && (
                                                        <tr>
                                                            <td colSpan={4} className="px-3 py-3 text-[#6F6E6E] font-semibold">
                                                                {t('partnerPolicyRequestApproveRejectPopup.noBioExtractors')}
                                                            </td>
                                                        </tr>
                                                    )}
                                                    {!bioLoading && !bioError && bioExtractors.map((item, idx) => {
                                                        const row = normalizeBioRow(item);
                                                        const stripe = idx % 2 === 1 ? 'bg-[#FBFCFF]' : 'bg-white';
                                                        return (
                                                            <tr key={idx} className={stripe}>
                                                                <td className={`px-3 py-3 font-semibold whitespace-nowrap ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>{row.modality || '-'}</td>
                                                                <td className={`px-3 py-3 font-semibold ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>{row.configuration || '-'}</td>
                                                                <td className={`px-3 py-3 font-semibold whitespace-nowrap ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>{row.providerVersion || '-'}</td>
                                                                <td className={`px-3 py-3 font-semibold ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>{row.providerName || '-'}</td>
                                                            </tr>
                                                        );
                                                    })}
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>

                                    <hr className="h-px w-full bg-gray-200 border-0 my-6" />

                                    <p className={`text-[0.7rem] text-[#6F6E6E] font-semibold ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                                        {t('partnerPolicyRequestApproveRejectPopup.credentialType')}
                                    </p>
                                    {credentialTypeLoading ? (
                                        <p className={`text-base font-semibold text-[#191919] ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                                            {t('commons.loading', 'Loading...')}
                                        </p>
                                    ) : credentialTypeError ? (
                                        <p className={`text-sm font-semibold text-crimson-red ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                                            {credentialTypeError}
                                        </p>
                                    ) : (
                                        <p className={`text-base font-semibold text-[#191919] break-words ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                                            {credentialType || t('commons.notAvailable', 'Not available')}
                                        </p>
                                    )}
                                </div>
                            </>
                        )}

                        <hr className="h-px mt-3 w-full bg-gray-200 border-0" />
                        <div className="py-3">
                            <p id='view_partner_policy_request_comments_label' className="font-semibold text-vulcan text-base mb-3">
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
                                        <h4 id='view_partner_policy_request_admin_comments_label' className="text-sm text-[#031640]">
                                            {t("viewPolicyDetails.adminComments")}
                                        </h4>
                                        <div className="flex items-center justify-start mt-4">
                                            <div id='partner_policy_request_status' className={`${bgOfStatus(policyRequestDetails.status)} flex w-fit py-1.5 px-3 text-sm rounded-md`}>
                                                {getStatusCode(policyRequestDetails.status, t)}
                                            </div>
                                            <div>
                                                {policyRequestDetails.updatedDateTime && (
                                                    <div className="flex">
                                                        <div id='partner_policy_request_updated_date' className={`font-semibold ${isLoginLanguageRTL ? "mr-3" : "ml-3"} text-sm text-dark-blue`}>
                                                            {formatDate(policyRequestDetails.updatedDateTime, "date")}
                                                        </div>
                                                        <div className="mx-3 text-gray-300">|</div>
                                                        <div id='partner_policy_request_updated_time' className="font-semibold text-sm text-dark-blue">
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
                                            <h4 id='partner_policy_request_partner_comment_label' className="text-sm text-[#031640]">
                                                {t("viewPolicyDetails.partnerComment")}
                                            </h4>
                                            <span id='partner_policy_request_partner_comment' className="text-sm mt-3 break-words">
                                                {policyRequestDetails.partnerComment}
                                            </span>
                                            <hr className="h-px w-full bg-gray-200 border-0 my-4" />
                                            <div className="flex items-center justify-start">
                                                <div id='partner_policy_request_created_on' className="font-semibold text-sm text-dark-blue">
                                                    {t("viewPolicyDetails.createdOn") + ' ' +
                                                        formatDate(policyRequestDetails.createdDateTime, "date")}
                                                </div>
                                                <div className="mx-3 text-gray-300">|</div>
                                                <div id='partner_policy_request_created_date_time' className="font-semibold text-sm text-dark-blue">
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
                        <button id="view_partner_policy_request_back_btn" onClick={moveToPolicyRequestsList}
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