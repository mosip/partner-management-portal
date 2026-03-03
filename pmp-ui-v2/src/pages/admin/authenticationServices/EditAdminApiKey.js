import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useBlocker, useLocation, useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import {
    isLangRTL,
    formatDate,
    bgOfStatus,
    getStatusCode,
    getPartnerManagerUrl,
    handleServiceErrors,
    createRequest
} from '../../../utils/AppUtils';
import { HttpService } from '../../../services/HttpService';
import Title from '../../common/Title';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from '../../common/LoadingIcon';
import CalendarInput from '../../common/CalendarInput';
import Confirmation from '../../common/Confirmation';
import BlockerPrompt from '../../common/BlockerPrompt';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';

function EditAdminApiKey() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const location = useLocation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [dataLoaded, setDataLoaded] = useState(false);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [apiKeyDetails, setApiKeyDetails] = useState({});
    const [selectedDateStr, setSelectedDateStr] = useState("");
    const [showCalendar, setShowCalendar] = useState(false);
    const [dateError, setDateError] = useState("");
    const [confirmationData, setConfirmationData] = useState({});
    const [editExpirySuccess, setEditExpirySuccess] = useState(false);
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);
    const [originalExpiryDate, setOriginalExpiryDate] = useState("");

    const blocker = useBlocker(
        ({ currentLocation, nextLocation }) => {
            if (isSubmitClicked || editExpirySuccess) {
                return false;
            }
            return (
                selectedDateStr !== originalExpiryDate &&
                currentLocation.pathname !== nextLocation.pathname
            );
        }
    );

    useEffect(() => {
        const apiKeyData = location.state?.selectedApiKeyAttributes;
        if (!apiKeyData) {
            setUnexpectedError(true);
            setDataLoaded(true);
            return;
        }
        try {
            setApiKeyDetails(apiKeyData);

            // Initialize expiry date
            if (apiKeyData?.apiKeyExpiryDateTime) {
                setSelectedDateStr(apiKeyData.apiKeyExpiryDateTime);
                setOriginalExpiryDate(apiKeyData.apiKeyExpiryDateTime);
            } else {
                // Initialize with tomorrow's start-of-day as default to satisfy "must be future" rule
                const tomorrow = new Date();
                tomorrow.setDate(tomorrow.getDate() + 1);
                tomorrow.setHours(0, 0, 0, 0);
                const tomorrowISO = tomorrow.toISOString();
                setSelectedDateStr(tomorrowISO);
                setOriginalExpiryDate(tomorrowISO);
            }
            setDataLoaded(true);
        } catch (error) {
            console.error('Error with selectedApiKeyAttributes from location state:', error);
            setUnexpectedError(true);
            setDataLoaded(true);
            return;
        }
    }, []);

    useEffect(() => {
        if (editExpirySuccess) {
            setIsSubmitClicked(false);
        }
    }, [editExpirySuccess]);

    useEffect(() => {
        const shouldWarnBeforeUnload = () => selectedDateStr !== originalExpiryDate && !editExpirySuccess;

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
    }, [selectedDateStr, originalExpiryDate, isSubmitClicked, editExpirySuccess]);

    const handleDateChange = (dateStr) => {
        setSelectedDateStr(dateStr);
        setDateError("");

        // Validate that the date is in the future (not today or past)
        if (dateStr) {
            const today = new Date();
            today.setHours(0, 0, 0, 0);
            const selectedDate = new Date(dateStr);
            selectedDate.setHours(0, 0, 0, 0);

            if (selectedDate <= today) {
                setDateError(t('apiKeysList.expiryDateMustBeFuture'));
            } else {
                setDateError("");
            }
        }
    };

    const clickOnSubmit = async () => {
        setIsSubmitClicked(true);

        if (!selectedDateStr) {
            setDateError(t('apiKeysList.expiryDateRequired'));
            setIsSubmitClicked(false);
            return;
        }

        // Validate required fields
        if (!apiKeyDetails?.partnerId || !apiKeyDetails?.policyId || !apiKeyDetails?.apiKeyLabel) {
            setErrorMsg(t('commons.invalidPopupData'));
            setIsSubmitClicked(false);
            return;
        }

        // Validate that the date is in the future (not today or past)
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        const selectedDate = new Date(selectedDateStr);
        selectedDate.setHours(0, 0, 0, 0);

        if (selectedDate <= today) {
            setDateError(t('apiKeysList.expiryDateMustBeFuture'));
            setIsSubmitClicked(false);
            return;
        }

        setErrorCode("");
        setErrorMsg("");
        setDateError("");
        setDataLoaded(false);

        try {
            const request = createRequest({
                expiryDateTime: selectedDateStr
            }, "mosip.pms.update.api.key.patch", true);

            const response = await HttpService.patch(
                getPartnerManagerUrl(`/partners/${apiKeyDetails.partnerId}/policies/${apiKeyDetails.policyId}/api-keys/${apiKeyDetails.apiKeyLabel}`, process.env.NODE_ENV),
                request,
                {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            );

            const responseData = response.data;
            if (responseData && responseData.response) {
                const requiredData = {
                    backUrl: '/partnermanagement/admin/authentication-services/api-keys-list',
                    header: t('apiKeysList.expiryDateUpdatedForApiKey', { apiKeyName: apiKeyDetails.apiKeyLabel }),
                };
                setConfirmationData(requiredData);
                setEditExpirySuccess(true);
            } else {
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        } catch (err) {
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            console.error('Error updating expiry date:', err);
        }
        setDataLoaded(true);
        setIsSubmitClicked(false);
    };

    const undoChanges = () => {
        setErrorCode("");
        setErrorMsg("");
        setDateError("");
        setSelectedDateStr(originalExpiryDate);
    };

    const clickOnCancel = () => {
        navigate('/partnermanagement/admin/authentication-services/api-keys-list');
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const moveToApiKeysList = () => {
        navigate('/partnermanagement/admin/authentication-services/api-keys-list');
    };

    const isFormValid = () => {
        return selectedDateStr !== "" && !dateError && selectedDateStr !== originalExpiryDate;
    };

    const calenderStyleSet = {
        datePicker: dateError ? "!border-red-500" : "",
        outerDiv: "w-full"
    };

    // Set minimum date to tomorrow to prevent selecting today or past dates
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    tomorrow.setHours(0, 0, 0, 0);
    const minSelectableDate = tomorrow;

    const styles = {
        loadingDiv: "!py-[20%]",
    }

    return (
        <div className={`w-full p-4 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} overflow-x-scroll relative`}>
            {!dataLoaded && (
                <LoadingIcon styleSet={styles} />
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage id='edit_admin_api_key_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className={`flex-col mt-5 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm mb-[2%]`}>
                        <div className="flex justify-between mb-3">
                            <Title
                                title='apiKeysList.editApiKeyExpiry'
                                subTitle='apiKeysList.listOfApiKeyRequests'
                                backLink='/partnermanagement/admin/authentication-services/api-keys-list'
                            />
                        </div>

                        {unexpectedError && (
                            <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center items-center">
                                        <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                        <p id='edit_admin_api_key_unexpected_error' className="text-base font-semibold text-[#6F6E6E] py-4">{t('commons.unexpectedError')}</p>
                                        <button onClick={moveToApiKeysList} type="button" id='edit_admin_api_key_go_back_btn'
                                            className={`w-32 h-10 flex items-center justify-center font-semibold rounded-md text-sm mx-8 py-3 bg-tory-blue text-white`}>
                                            {t('commons.goBack')}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        )}

                        {!unexpectedError && !editExpirySuccess && (
                            <>
                                {/* Card 1 - Read-Only Information */}
                                <div className="bg-snow-white h-fit mt-1 rounded-t-xl shadow-lg font-inter">
                                    <div className="flex justify-between px-7 pt-3 border-b max-[450px]:flex-col">
                                        <div className="flex-col">
                                            <p id='edit_admin_api_key_sub_title_id' className="text-lg text-dark-blue mb-2">
                                                {t('apiKeysList.apiKeyName')}: <span className="font-semibold">{apiKeyDetails.apiKeyLabel}</span>
                                            </p>
                                            <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                                <div id='edit_admin_api_key_status' className={`${bgOfStatus(apiKeyDetails.status)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                                    {getStatusCode(apiKeyDetails.status, t)}
                                                </div>
                                                <div id='edit_admin_api_key_created_on' className={`font-semibold ${isLoginLanguageRTL ? "mr-[1.4rem]" : "ml-[0.75rem]"} text-sm text-dark-blue`}>
                                                    {t("viewOidcClientDetails.createdOn") + ' ' +
                                                        formatDate(apiKeyDetails.createdDateTime, "date")}
                                                </div>
                                                <div className="mx-1 text-gray-300">|</div>
                                                <div id='edit_admin_api_key_created_date_time' className="font-semibold text-sm text-dark-blue">
                                                    {formatDate(apiKeyDetails.createdDateTime, "time")}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                                        <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                                            <div className={`w-[49%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                                <p id='edit_admin_api_key_partner_id_label' className="font-[600] text-suva-gray text-sm">
                                                    {t("viewOidcClientDetails.partnerId")}
                                                </p>
                                                <p id='edit_admin_api_key_partner_id_context' className="font-[600] text-vulcan text-base">
                                                    {apiKeyDetails.partnerId}
                                                </p>
                                            </div>
                                            <div className="mb-3 max-[600px]:w-[100%] w-[49%]">
                                                <p id='edit_admin_api_key_partner_type_label' className="font-[600] text-suva-gray text-sm">
                                                    {t("viewOidcClientDetails.partnerType")}
                                                </p>
                                                <p id='edit_admin_api_key_partner_type_context' className="font-[600] text-vulcan text-base">
                                                    {t("partnerTypes.authPartner")}
                                                </p>
                                            </div>
                                            <div className="my-3 max-[600px]:w-[100%] w-[49%]">
                                                <p id='edit_admin_api_key_organisation_label' className="font-[600] text-suva-gray text-sm">
                                                    {t("viewAdminOidcClientDetails.organisation")}
                                                </p>
                                                <p id='edit_admin_api_key_organisation_context' className="font-[600] text-vulcan text-base">
                                                    {apiKeyDetails.orgName}
                                                </p>
                                            </div>
                                        </div>
                                        <hr className={`h-px w-full bg-gray-200 border-0`} />
                                        <div className={`flex flex-wrap pt-3`}>
                                            <div className={`w-[49%] max-[600px]:w-[100%] ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                                <p id='edit_admin_api_key_policy_group_label' className="font-[600] text-suva-gray text-sm">
                                                    {t("viewOidcClientDetails.policyGroup")}
                                                </p>
                                                <p id='edit_admin_api_key_policy_group_name_context' className="font-[600] text-vulcan text-base">
                                                    {apiKeyDetails.policyGroupName}
                                                </p>
                                            </div>
                                            <div className={`w-[49%] max-[600px]:w-[100%]`}>
                                                <p id='edit_admin_api_key_policy_name_label' className="font-[600] text-suva-gray text-sm">
                                                    {t("viewOidcClientDetails.policyName")}
                                                </p>
                                                <p id='edit_admin_api_key_policy_name_context' className="font-[600] text-vulcan text-base">
                                                    {apiKeyDetails.policyName}
                                                </p>
                                            </div>
                                            <div className={`w-[49%] max-[600px]:w-[100%] my-4 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                                <p id='edit_admin_api_key_policy_group_description_label' className="font-[600] text-suva-gray text-sm">
                                                    {t("viewOidcClientDetails.policyGroupDescription")}
                                                </p>
                                                <p id='edit_admin_api_key_policy_group_description_context' className="font-[600] text-vulcan text-base">
                                                    {apiKeyDetails.policyGroupDescription}
                                                </p>
                                            </div>
                                            <div className={`w-[49%] max-[600px]:w-[100%] my-4 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                                <p id='edit_admin_api_key_policy_description_label' className="font-[600] text-suva-gray text-sm">
                                                    {t("viewOidcClientDetails.policyNameDescription")}
                                                </p>
                                                <p id='edit_admin_api_key_policy_description_context' className="font-[600] text-vulcan text-base">
                                                    {apiKeyDetails.policyDescription}
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                {/* Card 2 - Editable Section */}
                                <div className="bg-snow-white h-fit mt-3 rounded-t-xl shadow-lg font-inter">
                                    <div className="px-7 pt-5 pb-3">
                                        <h2 id='edit_admin_api_key_update_expiry_title' className="text-lg font-semibold text-dark-blue mb-4">
                                            {t('apiKeysList.updateExpiryDate')}
                                        </h2>
                                        <div className="flex flex-col w-1/3 min-w-80">
                                            <CalendarInput
                                                label={t('apiKeysList.expiryDate')}
                                                showCalendar={showCalendar}
                                                setShowCalender={setShowCalendar}
                                                onChange={handleDateChange}
                                                selectedDateStr={selectedDateStr}
                                                containsAsterisk
                                                id="edit_admin_api_key_expiry_date_picker"
                                                styleSet={calenderStyleSet}
                                                isUsedAsFilter={false}
                                                placeholderText={t('apiKeysList.selectExpiryDatePlaceholder')}
                                                placeholderId="edit_admin_api_key_expiry_date_placeholder"
                                                disabled={false}
                                                minDate={minSelectableDate}
                                            />
                                            {dateError && (
                                                <p id='edit_admin_api_key_date_error' className="text-red-500 text-xs mt-1 ml-1">{dateError}</p>
                                            )}
                                        </div>
                                    </div>
                                    <hr className="h-px w-full bg-gray-200 border-0" />
                                    <div className={`flex flex-row px-7 py-5 justify-between`}>
                                        <button
                                            id="edit_admin_api_key_undo_changes_btn"
                                            onClick={undoChanges}
                                            className={`w-40 min-w-fit px-3 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}
                                        >
                                            {t('commons.undoChanges')}
                                        </button>
                                        <div className={`flex flex-row space-x-3 w-full md:w-auto justify-end`}>
                                            <button
                                                id="edit_admin_api_key_cancel_btn"
                                                onClick={clickOnCancel}
                                                className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}
                                            >
                                                {t('requestPolicy.cancel')}
                                            </button>
                                            <button
                                                id="edit_admin_api_key_submit_btn"
                                                disabled={!isFormValid()}
                                                onClick={clickOnSubmit}
                                                className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}
                                            >
                                                {t('requestPolicy.submit')}
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </>
                        )}
                        {editExpirySuccess && (
                            <Confirmation id='edit_admin_api_key_confirmation' confirmationData={confirmationData} />
                        )}
                    </div>
                </>
            )}
            <BlockerPrompt blocker={blocker} />
        </div>
    );
}

export default EditAdminApiKey;

