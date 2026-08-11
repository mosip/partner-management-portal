import { useEffect, useState } from "react";
import { getUserProfile } from "../../../services/UserProfileService";
import { bgOfStatus, formatDate, getPartnerManagerUrl, getStatusCode, isLangRTL } from "../../../utils/AppUtils";
import ErrorMessage from "../../common/ErrorMessage";
import LoadingIcon from "../../common/LoadingIcon";
import Title from "../../common/Title";
import { useTranslation } from "react-i18next";
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';
import { useNavigate } from "react-router-dom";
import { HttpService } from "../../../services/HttpService";

function ViewMispLicenseKey() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [mispLicenseKeyDetails, setMispLicenseKeyDetails] = useState({});
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(false);

    const moveToMispLicenseList = () => {
        navigate('/partnermanagement/admin/misp-partner-services/misp-license-list');
    };

    useEffect(() => {
        const data = sessionStorage.getItem('selectedMispLicenseKey');
        if(!data){
            setUnexpectedError(true);
            return ;
        }
        const mispKeyData = JSON.parse(data);

        const url = getPartnerManagerUrl(`/misp-licenses/${mispKeyData.mispLicenseId}`, process.env.NODE_ENV);

        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(url);
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        setMispLicenseKeyDetails(resData);
                    } else {
                        setMispLicenseKeyDetails(mispKeyData);
                    }
                } else {
                    setErrorMsg(t('mispLicenseList.errorWhileGettingMispDetails'))
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                if (err.response?.status && err.response.status !== 401) {
                    setErrorMsg(err.toString());
                }
            }
        };
        fetchData();
    }, []);

    const getMaskedLicenseKey = (licenseKey) => {
        if (!licenseKey) {
            return "-";
        }
        const visiblePart = licenseKey.slice(-10);
        return visiblePart
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const styles = {
        loadingDiv: "!py-[20%]",
    }

    return (
        <div className={`w-full p-4 bg-anti-flash-white h-full font-inter break-words max-450:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} overflow-x-scroll`}>
            {!dataLoaded && (
                <LoadingIcon styleSet={styles} />
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage id='view_admin_oidc_client_details_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className={`flex-col mt-5 bg-anti-flash-white h-full font-inter break-words max-450:text-sm mb-[2%]`}>
                        <div className="flex justify-between mb-3">
                            <Title title='mispLicenseList.viewMispLicenseKey' subTitle='mispLicenseList.mispPartnerServices' backLink={'/partnermanagement/admin/misp-partner-services/misp-license-list'} />
                        </div>
                    </div>
                    {unexpectedError && (
                        <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                            <div className="flex items-center justify-center p-24">
                                <div className="flex flex-col justify-center items-center">
                                    <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                    <p id='view_misp_license_key_details_unexpected_error' className="text-base font-semibold text-[#6F6E6E] py-4">{t('commons.unexpectedError')}</p>
                                    <button onClick={moveToMispLicenseList} type="button" id='view_misp_license_key_details_go_back_btn'
                                        className={`w-32 h-10 flex items-center justify-center font-semibold rounded-md text-sm mx-8 py-3 bg-tory-blue text-white`}>
                                        {t('commons.goBack')}
                                    </button>
                                </div>
                            </div>
                        </div>
                    )}
                    {!unexpectedError && (
                        <div className="bg-snow-white h-fit mt-1 rounded-t-xl shadow-lg font-inter">
                            <div className="flex flex-wrap justify-between px-7 pt-3 border-b max-450:flex-col">
                                <div className="flex flex-col flex-wrap max-w-full">
                                    <p id='view_misp_license_key_details_sub_title_id' className="text-lg text-dark-blue mb-2 break-words whitespace-normal max-w-full">
                                        {t('mispLicenseList.mispLicenseKeyName')}: <span className="font-semibold">{mispLicenseKeyDetails.licenseKeyName ? mispLicenseKeyDetails.licenseKeyName : "-"}</span>
                                    </p>
                                    <div className="flex flex-wrap items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                        <div id='view_misp_license_key_details_status' className={`${bgOfStatus(mispLicenseKeyDetails.status)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                            {getStatusCode(mispLicenseKeyDetails.status, t)}
                                        </div>
                                        <div id='view_misp_license_key_details_created_on' className={`font-semibold ${isLoginLanguageRTL ? "mr-[1.4rem]" : "ml-[0.75rem]"} text-sm text-dark-blue`}>
                                            {t("viewOidcClientDetails.createdOn") + ' ' +
                                                formatDate(mispLicenseKeyDetails.createdDateTime, "date")}
                                        </div>
                                        <div className="mx-1 text-gray-300">|</div>
                                        <div id='view_misp_license_key_details_created_date_time' className="font-semibold text-sm text-dark-blue">
                                            {formatDate(mispLicenseKeyDetails.createdDateTime, "time")}
                                        </div>
                                    </div>
                                </div>
                                <button id="view_misp_license_key_details_copy_id" className={`${mispLicenseKeyDetails.status === "ACTIVE" ? 'bg-[#F0F5FF] border-[#BED3FF] cursor-pointer hover:shadow-md' : 'bg-gray-200 border-gray-400 cursor-default'}  border h-[4%] w-[14%] max-450:w-[40%] max-850:w-[25%] ${isLoginLanguageRTL ? "pl-[1.5%] text-left" : "pr-[1%] text-right"} py-[0.5%] rounded-md mb-1`}>
                                    <p id='view_misp_license_key_details_id_label' className="text-sm font-semibold text-[#333333]">{t('mispLicenseList.mispLicenseKey')}</p>
                                    <p id="view_misp_license_key_details_id" className={`text-base font-bold ${mispLicenseKeyDetails.status === "ACTIVE" ? "text-tory-blue"  : "text-gray-400" }`}>
                                        ...{getMaskedLicenseKey(mispLicenseKeyDetails.maskedLicenseKey)}
                                    </p>
                                </button>
                            </div>
                            <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                                <div className="flex flex-wrap py-1 max-450:flex-col">
                                    <div className={`w-[49%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                        <p id='misp_license_key_details_partner_id_label' className="font-[600] text-suva-gray text-sm">
                                            {t("viewOidcClientDetails.partnerId")}
                                        </p>
                                        <p id='misp_license_key_details_partner_id_context' className="font-[600] text-vulcan text-base">
                                            {mispLicenseKeyDetails.partnerId}
                                        </p>
                                    </div>
                                    <div className="mb-3 max-[600px]:w-[100%] w-[49%]">
                                        <p id='misp_license_key_details_partner_type_label' className="font-[600] text-suva-gray text-sm">
                                            {t("viewOidcClientDetails.partnerType")}
                                        </p>
                                        <p id='misp_license_key_details_misp_partner_context' className="font-[600] text-vulcan text-base">
                                            {t("partnerTypes.mispPartner")}
                                        </p>
                                    </div>
                                    <div className="my-3 max-[600px]:w-[100%] w-[49%]">
                                        <p id='misp_license_key_details_organisation_label' className="font-[600] text-suva-gray text-sm">
                                            {t("viewAdminOidcClientDetails.organisation")}
                                        </p>
                                        <p id='misp_license_key_details_organisation_context' className="font-[600] text-vulcan text-base">
                                            {mispLicenseKeyDetails.orgName ? mispLicenseKeyDetails.orgName : '-'}
                                        </p>
                                    </div>
                                </div>
                                <hr className={`h-px w-full bg-gray-200 border-0`} />
                                <div className={`flex flex-wrap pt-3`}>
                                    <div className={`w-[49%] max-[600px]:w-[100%] ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                        <p id='misp_license_key_details_policy_group_label' className="font-[600] text-suva-gray text-sm">
                                            {t("viewOidcClientDetails.policyGroup")}
                                        </p>
                                        <p id='misp_license_key_details_policy_group_name_context' className="font-[600] text-vulcan text-base">
                                            {mispLicenseKeyDetails.policyGroupName ? mispLicenseKeyDetails.policyGroupName : '-'}
                                        </p>
                                    </div>
                                    <div className={`w-[49%] max-[600px]:w-[100%]`}>
                                        <p id='misp_license_key_details_policy_name_label' className="font-[600] text-suva-gray text-sm">
                                            {t("viewOidcClientDetails.policyName")}
                                        </p>
                                        <p id='misp_license_key_details_policy_name_context' className="font-[600] text-vulcan text-base">
                                            {mispLicenseKeyDetails.policyName ? mispLicenseKeyDetails.policyName : '-'}
                                        </p>
                                    </div>
                                    <div className={`w-[49%] max-[600px]:w-[100%] my-4 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                        <p id='misp_license_key_details_policy_group_description_label' className="font-[600] text-suva-gray text-sm">
                                            {t("viewOidcClientDetails.policyGroupDescription")}
                                        </p>
                                        <p id='misp_license_key_details_policy_group_description_context' className="font-[600] text-vulcan text-base">
                                            {mispLicenseKeyDetails.policyGroupDescription ? mispLicenseKeyDetails.policyGroupDescription : '-'}
                                        </p>
                                    </div>
                                    <div className={`w-[49%] max-[600px]:w-[100%] my-4 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                        <p id='misp_license_key_details_policy_name_description_label' className="font-[600] text-suva-gray text-sm">
                                            {t("viewOidcClientDetails.policyNameDescription")}
                                        </p>
                                        <p id='misp_license_key_details_policy_description_context' className="font-[600] text-vulcan text-base">
                                            {mispLicenseKeyDetails.policyDescription ? mispLicenseKeyDetails.policyDescription : '-'}
                                        </p>
                                    </div>
                                    <div className={`w-[49%] max-[600px]:w-[100%] my-4 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                        <p id='misp_license_key_details_expiry_date_label' className="font-[600] text-suva-gray text-sm">
                                            {t("apiKeysList.expirationDate")}
                                        </p>
                                        <p id='misp_license_key_details_expiry_date_context' className="font-[600] text-vulcan text-base">
                                            {formatDate(mispLicenseKeyDetails.expiryDateTime, 'date')}
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <hr className="h-px w-full bg-gray-200 border-0" />
                            <div className={`flex justify-end py-8 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                                <button id="view_misp_license_key_back_btn" onClick={moveToMispLicenseList}
                                    className="h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center">
                                    {t("viewPolicyDetails.back")}
                                </button>
                            </div>
                        </div>
                    )}
                </>
            )}
        </div>
    )
}
export default ViewMispLicenseKey;