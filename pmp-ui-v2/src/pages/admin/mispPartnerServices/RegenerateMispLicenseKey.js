import { useTranslation } from "react-i18next";
import { useBlocker, useNavigate } from "react-router-dom";
import { createRequest, getPartnerManagerUrl, getPartnerTypeDescription, handleServiceErrors, isLangRTL, moveToMispPartnerServices, validateInputRegex } from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService";
import { useEffect, useState } from "react";
import { HttpService } from "../../../services/HttpService";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import CalendarInput from "../../common/CalendarInput";
import CopyIdPopUp from "../../common/CopyIdPopup";
import Confirmation from "../../common/Confirmation";
import BlockerPrompt from "../../common/BlockerPrompt";
import Title from "../../common/Title";
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';

function RegenerateMispLicenseKey() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [regenerateLicenseKeySuccess, setRegenerateLicenseKeySuccess] = useState(false);
    const [confirmationData, setConfirmationData] = useState({});
    const [licenseKeyName, setLicenseKeyName] = useState("");
    const [expiryDate, setExpiryDate] = useState("");
    const [invalidLicenseKeyNameError, setInvalidLicenseKeyNameError] = useState("");
    const [isExpiryCalenderOpen, setIsExpiryCalenderOpen] = useState(false);
    const [showPopup, setShowPopup] = useState(false);
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);
    const [mispLicenseKey, setMispLicenseKey] = useState('');
    const [mispLicenseKeyDetails, setMispLicenseKeyDetails] = useState({});
    const [unexpectedError, setUnexpectedError] = useState(false);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const blocker = useBlocker(
        ({ currentLocation, nextLocation }) => {
            if (isSubmitClicked || regenerateLicenseKeySuccess) {
                setIsSubmitClicked(false);
                return false;
            }

            return (
                (licenseKeyName !== "") &&
                currentLocation.pathname !== nextLocation.pathname
            );
        }
    );

    useEffect(() => {
        const shouldWarnBeforeUnload = () => {
            return licenseKeyName !== "";
        };

        const handleBeforeUnload = (event) => {
            if (shouldWarnBeforeUnload() && !isSubmitClicked) {
                event.preventDefault();
                event.returnValue = '';
            }
        };

        window.addEventListener('beforeunload', handleBeforeUnload);

        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload);
        };
    }, [licenseKeyName, isSubmitClicked]);

    useEffect(() => {
        const data = sessionStorage.getItem('selectedMispLicenseKey');
        if (!data) {
            setUnexpectedError(true);
            return;
        }
        const mispKeyData = JSON.parse(data);
        setMispLicenseKeyDetails(mispKeyData);
    }, []);

    const onChangeLicenseKeyName = (value) => {
        setLicenseKeyName(value);
        validateInputRegex(value, setInvalidLicenseKeyNameError, t);
    };

    const onHandleChangeExpiryDate = (dateStr) => {
        console.log(`onHandleChangeExpiryDate ${dateStr}`);
        setExpiryDate(dateStr);
    };

    const clearForm = () => {
        setErrorCode("");
        setErrorMsg("");
        setLicenseKeyName("");
        setExpiryDate("");
        setInvalidLicenseKeyNameError("");
    };

    const clickOnCancel = () => {
        moveToMispPartnerServices(navigate);
    };

    const isFormValid = () => {
        return licenseKeyName.trim() && !invalidLicenseKeyNameError;
    };

    const clickOnSubmit = async () => {
        setShowPopup(false);
        setIsSubmitClicked(true);
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);

        let request = createRequest({
            partnerId: mispLicenseKeyDetails.partnerId,
            policyId: mispLicenseKeyDetails.policyId,
            licenseKeyName: licenseKeyName.trim(),
            expiryDate: expiryDate === "" ? new Date().toISOString().split("T")[0] : new Date(expiryDate).toISOString().split("T")[0]
        }, "mosip.pms.misp.generate.license.post", true);

        try {
            const response = await HttpService.post(getPartnerManagerUrl(`/misp-licenses`, process.env.NODE_ENV), request, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const requireData = {
                        title: "regenerateMispLicenseKey.regenerateMispLicenseKey",
                        backUrl: "/partnermanagement/admin/misp-partner-services/misp-license-list",
                        header: "regenerateMispLicenseKey.regenerateLicenseKeySuccessHeader",
                        subNavigation: "mispLicenseList.mispPartnerServices",
                    }
                    setConfirmationData(requireData);
                    setMispLicenseKey(responseData.response.licenseKey);
                    setShowPopup(true);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('regenerateMispLicenseKey.errorInRegenerateMispLicenseKey'));
            }
            setDataLoaded(true);
        } catch (err) {
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            console.log("Error fetching data: ", err);
        }
        setIsSubmitClicked(false);
    };

    const closePopUp = (state) => {
        setShowPopup(state);
        setRegenerateLicenseKeySuccess(true);
    };

    const style = {
        backArrowIcon: "!mt-[9%]",
    };

    const calenderStyleSet = {
        datePicker: "h-10",
        outerDiv: "w-[48%]"
    };

    const copyIdPopupStyle = {
        outerDiv: "!bg-opacity-[50%]"
    };

    return (
        <div className={`mt-2 w-full ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll relative font-inter`}>
            {!dataLoaded && (
                <LoadingIcon />
            )}
            {dataLoaded && (
                <>
                    {!unexpectedError && errorMsg && (
                        <ErrorMessage id='regenerate_misp_license_key_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5 w-full">
                        <div className="w-fit">
                            <Title title='regenerateMispLicenseKey.regenerateMispLicenseKey' subTitle='mispLicenseList.mispPartnerServices' backLink={'/partnermanagement/admin/misp-partner-services/misp-license-list'} style={style} />
                        </div>
                        {unexpectedError && (
                            <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center items-center">
                                        <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                        <p id='regenerate_misp_license_key_unexpected_error_header' className="text-base font-semibold text-[#6F6E6E] pt-4">{t('commons.unexpectedError')}</p>
                                        <button onClick={() => moveToMispPartnerServices(navigate)} type="button" id='regenerate_misp_license_key_go_back_btn'
                                            className={`w-32 h-10 flex items-center justify-center font-semibold rounded-md text-sm mx-8 py-3 bg-tory-blue text-white`}>
                                            {t('commons.goBack')}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        )}
                        {!unexpectedError && (
                            <>
                                {!regenerateLicenseKeySuccess ? (
                                    <div className="w-[100%] bg-snow-white mt-[1%] rounded-lg shadow-md">
                                        <div className="p-7">
                                            <p id='regenerate_license_key_mandantory_msg' className="text-base mb-2 text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                            <form>
                                                <div className="flex flex-col w-full">
                                                    <div className="flex flex-row justify-between space-x-4 my-2 max-[450px]:flex-col">
                                                        <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                            <label id='regenerate_license_key_partner_id_label' className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerId')}<span className="text-crimson-red">*</span></label>
                                                            <button id='regenerate_license_key_partner_id' disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                                <span className={`w-full break-words text-dark-blue text-wrap text-start`}>{mispLicenseKeyDetails.partnerId}</span>
                                                            </button>
                                                        </div>
                                                        <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                            <label id='regenerate_license_key_partner_type_label' className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red">*</span></label>
                                                            <button id='regenerate_license_key_partner_type' disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                                <span className={`w-full break-words text-dark-blue text-wrap text-start`}>{getPartnerTypeDescription('MISP_PARTNER', t)}</span>
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <div className="flex flex-row justify-between space-x-4 my-2 max-[450px]:flex-col">
                                                        <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                            <label id='regenerate_license_key_policy_group_label' className={`flex items-center text-dark-blue text-sm font-semibold mb-2 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.policyGroup')}</label>
                                                            <button id='regenerate_license_key_policy_group' disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                                <span className={`w-full break-words text-dark-blue text-wrap text-start`}>{mispLicenseKeyDetails.policyGroupName || t('generateLicenseKey.noPolicyGroupSelected')}</span>
                                                            </button>
                                                        </div>
                                                        <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                            <label id='regenerate_license_key_policy_name_label' className={`flex items-center text-dark-blue text-sm font-semibold mb-2 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.policyName')}</label>
                                                            <button id='regenerate_license_key_policy_name' disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                                <span className={`w-full break-words text-dark-blue text-wrap text-start`}>{mispLicenseKeyDetails.policyName || t('regenerateMispLicenseKey.noPolicySelected')}</span>
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <div className="flex flex-row justify-between space-x-4 my-2 max-[450px]:flex-col">
                                                        <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                            <label id='regenerate_license_key_name_label' className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('generateLicenseKey.mispLicenseKeyName')}<span className="text-crimson-red mx-1">*</span></label>
                                                            <input value={licenseKeyName} onChange={(e) => onChangeLicenseKeyName(e.target.value)} maxLength={128}
                                                                className="h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                                                placeholder={t('generateLicenseKey.enterLicenseKeyName')}
                                                                data-placeholder-id="regenerate_license_key_name_placeholder"
                                                                id="regenerate_license_key_name" />
                                                            {invalidLicenseKeyNameError && <span id='regenerate_license_key_invalid_license_key_name' className="text-sm text-crimson-red font-semibold">{invalidLicenseKeyNameError}</span>}
                                                        </div>
                                                        <CalendarInput
                                                            label={t('generateLicenseKey.expirationDuration')}
                                                            showCalendar={isExpiryCalenderOpen}
                                                            setShowCalender={setIsExpiryCalenderOpen}
                                                            onChange={onHandleChangeExpiryDate}
                                                            selectedDateStr={expiryDate}
                                                            containsAsterisk
                                                            addInfoIcon
                                                            infoKey='generateLicenseKey.dateFormatInfoKey'
                                                            id='regenerate_license_key_expiry_date_calender'
                                                            styleSet={calenderStyleSet}
                                                            isUsedAsFilter={false}
                                                        />
                                                    </div>
                                                    <div className="flex items-center justify-center my-5">
                                                        <div className="p-2 bg-[#FFF7E5] border-2 border-[#EDDCAF] rounded-md w-full">
                                                            <p id='regenerate_misp_license_key_important_note' className="text-sm font-medium text-[#8B6105]">{t('generateLicenseKey.importantNote')}</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div className="border bg-medium-gray" />
                                        <div className="flex flex-row max-[450px]:flex-col px-[3%] py-5 justify-between max-[450px]:space-y-2">
                                            <button id="regenerate_license_key_clear_form" onClick={() => clearForm()} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.clearForm')}</button>
                                            <div className={`flex flex-row max-[450px]:flex-col space-x-3 max-[450px]:space-x-0 max-[450px]:space-y-2 w-full md:w-auto justify-end`}>
                                                <button id="regenerate_license_key_cancel_btn" onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                                                <button id="regenerate_license_key_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t('requestPolicy.submit')}</button>
                                                {(showPopup && !errorMsg) && (
                                                    <CopyIdPopUp closePopUp={closePopUp} subtitle={mispLicenseKeyDetails.partnerId} title={licenseKeyName} id={mispLicenseKey}
                                                        header='mispLicenseList.mispLicenseKey' alertMsg='generateLicenseKey.licenseKeyAlertMsg' styleSet={copyIdPopupStyle} />
                                                )}
                                            </div>
                                        </div>
                                    </div>
                                ) :
                                    <Confirmation id='regenerate_license_key_confirmation' confirmationData={confirmationData} />
                                }
                            </>
                        )}
                    </div>

                </>
            )}
            <BlockerPrompt blocker={blocker} />
        </div>
    )

}
export default RegenerateMispLicenseKey;