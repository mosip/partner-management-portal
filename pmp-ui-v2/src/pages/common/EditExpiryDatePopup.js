import React from "react";
import { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import LoadingIcon from "../common/LoadingIcon";
import ErrorMessage from "../common/ErrorMessage";
import SuccessMessage from "../common/SuccessMessage";
import CalendarInput from "../common/CalendarInput";
import { getPartnerManagerUrl, handleServiceErrors, handleEscapeKey, createRequest, formatDate } from "../../utils/AppUtils";
import { HttpService } from "../../services/HttpService.js";
import FocusTrap from "focus-trap-react";
import PropTypes from 'prop-types';

function EditExpiryDatePopup({ onClickConfirm, closePopUp, popupData }) {
    const { t } = useTranslation();
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [updateSuccess, setUpdateSuccess] = useState(false);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [selectedDateStr, setSelectedDateStr] = useState("");
    const [showCalendar, setShowCalendar] = useState(false);
    const [dateError, setDateError] = useState("");
    const [responseData, setResponseData] = useState(null);

    useEffect(() => {
        document.body.style.overflow = "hidden";
        if (popupData?.apiKeyExpiryDateTime) {
            setSelectedDateStr(popupData.apiKeyExpiryDateTime);
        } else {
            // Initialize with today's date as default
            const today = new Date();
            today.setHours(23, 59, 59, 999);
            setSelectedDateStr(today.toISOString());
        }

        return () => {
            document.body.style.overflow = "auto";
        };
    }, [popupData]);

    useEffect(() => {
        const removeListener = handleEscapeKey(() => closePopUp());
        return removeListener;
    }, [closePopUp]);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const cancelSuccessMsg = () => {
        setSuccessMsg("");
    };

    const closingPopUp = () => {
        closePopUp();
    };

    const clickOnClose = () => {
        if (responseData && responseData.response) {
            onClickConfirm(responseData.response, popupData);
        } else {
            closePopUp();
        }
    };

    const handleDateChange = (dateStr) => {
        setSelectedDateStr(dateStr);
        setDateError("");
        
        // Validate that the date is not in the past
        if (dateStr) {
            const today = new Date();
            today.setHours(0, 0, 0, 0);
            const selectedDate = new Date(dateStr);
            selectedDate.setHours(0, 0, 0, 0);
            
            if (selectedDate < today) {
                setDateError(t('apiKeysList.expiryDateMustBeFuture'));
            } else {
                setDateError("");
            }
        }
    };

    const clickOnConfirm = async () => {
        if (!selectedDateStr) {
            setDateError(t('apiKeysList.expiryDateRequired') || 'Expiry date is required');
            return;
        }

        // Validate that the date is not in the past (compare dates only, not time)
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        const selectedDate = new Date(selectedDateStr);
        selectedDate.setHours(0, 0, 0, 0);
        
        if (selectedDate < today) {
            setDateError(t('apiKeysList.expiryDateMustBeFuture') || 'Expiry date must be a future date.');
            return;
        }

        setErrorCode("");
        setErrorMsg("");
        setSuccessMsg("");
        setUpdateSuccess(false);
        setDateError("");
        setDataLoaded(false);
        setResponseData(null);
        
        try {
            // Use the ISO string directly from CalendarInput (same as AddSbi.js)
            const isoString = selectedDateStr === "" ? new Date().toISOString() : selectedDateStr;
            
            const request = createRequest({
                apiKeyName: popupData.apiKeyLabel,
                apiKeyExpiryDateTime: isoString
            }, "mosip.pms.update.api.key.expiry.patch", true);

            const response = await HttpService.patch(
                getPartnerManagerUrl(`/partners/${popupData.partnerId}/policy/${popupData.policyId}/apiKey/expiry-date`, process.env.NODE_ENV),
                request,
                {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            );

            const responseData = response.data;
            if (responseData && responseData.response) {
                setDataLoaded(true);
                setUpdateSuccess(true);
                setResponseData(responseData);
                // Format the selected date for display
                const formattedDate = formatDate(isoString, 'date');
                const successMessage = t('apiKeysList.expiryDateUpdatedSuccessfully', { date: formattedDate }) || `Expiry date updated. API Key will expire on ${formattedDate}.`;
                setSuccessMsg(successMessage);
            } else {
                setDataLoaded(true);
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        } catch (err) {
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            setDataLoaded(true);
        }
    };

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !my-2 mb-0",
        dropdownButton: "!w-full min-h-10 !rounded-md !text-sm !text-dark-blue",
        selectionBox: "",
        loadingDiv: "!py-[50%]"
    };

    const customStyle = {
        outerDiv: "!flex !justify-center !inset-0",
        innerDiv: "!flex !justify-between !items-center !w-full !min-h-12 !p-3 !-mb-2",
        cancelIcon: "!top-4 !mt-[3.25rem]"
    };

    const calenderStyleSet = {
        datePicker: dateError ? "!border-red-500" : "",
        outerDiv: "w-full"
    };

    return (
        <div className="fixed inset-0 w-full flex items-center justify-center bg-black bg-opacity-35 z-50 font-inter cursor-default">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className={`bg-white md:w-[25rem] w-[60%] h-fit rounded-xl shadow-sm`}>
                    {!dataLoaded && (
                        <LoadingIcon styleSet={styles}></LoadingIcon>
                    )}
                    {dataLoaded && (
                        <div className="relative">
                            <div className="px-2 py-3">
                                <h3 id='edit_expiry_date_popup_title' className={`text-base font-bold text-[#333333]`}>{t('apiKeysList.editApiKeyExpiry')}</h3>
                            </div>
                            <div className="border-gray-200 border-opacity-75 border-t"></div>
                            {errorMsg && (
                                <ErrorMessage id='edit_expiry_date_popup_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={customStyle}/>
                            )}
                            {successMsg && (
                                <SuccessMessage id='edit_expiry_date_popup_success_msg' successMsg={successMsg} clickOnCancel={cancelSuccessMsg} customStyle={customStyle}/>
                            )}
                            <div className="py-3 px-6">
                                <p id='edit_expiry_date_popup_description' className="text-sm font-normal text-[#414141] break-words">
                                    {t('apiKeysList.editExpiryDateDescription', { apiKeyName: popupData.apiKeyLabel }) || `Change the expiry date for API Key ${popupData.apiKeyLabel}. The key will be deactivated automatically on the selected date.`}
                                </p>
                            </div>
                            <div className="w-full flex flex-col px-6 pb-2">
                                <CalendarInput
                                    label={t('apiKeysList.expiryDate') || 'Expiry Date'}
                                    showCalendar={showCalendar}
                                    setShowCalender={setShowCalendar}
                                    onChange={handleDateChange}
                                    selectedDateStr={selectedDateStr}
                                    containsAsterisk
                                    id="expiry_date_picker"
                                    styleSet={calenderStyleSet}
                                    isUsedAsFilter={false}
                                    placeholderText={t('apiKeysList.selectExpiryDatePlaceholder') || 'Select expiry date (YYYY-MM-DD)'}
                                    disabled={updateSuccess}
                                />
                                {dateError && (
                                    <p className="text-red-500 text-xs mt-1 ml-1">{dateError}</p>
                                )}
                            </div>
                            <div className="border-[#E5EBFA] border-t mx-2"></div>
                            <div className="px-6 py-2 flex justify-between relative">
                                <button 
                                    disabled={updateSuccess}
                                    className={`w-36 h-10 m-1 ${updateSuccess ? 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed' : 'border-[#1447B2] text-tory-blue bg-white'} border rounded-lg text-sm font-semibold relative z-60`}
                                    onClick={closingPopUp}
                                    id="edit_expiry_date_cancel_btn">
                                    {t('commons.cancel')}
                                </button>
                                {!updateSuccess ? 
                                    <button 
                                        className="w-36 h-10 m-1 border rounded-lg text-white text-sm font-semibold relative z-60 bg-tory-blue border-[#1447B2] cursor-pointer"
                                        onClick={clickOnConfirm}
                                        id="edit_expiry_date_submit_btn">
                                        {t('apiKeysList.save') || 'Save'}
                                    </button> : 
                                    <button 
                                        className="w-36 h-10 m-1 border rounded-lg text-white text-sm font-semibold relative z-60 bg-tory-blue border-[#1447B2] cursor-pointer"
                                        onClick={clickOnClose}
                                        id="edit_expiry_date_close_btn">
                                        {t('commons.close')}
                                    </button>
                                }
                            </div>
                        </div>
                    )}
                </div>
            </FocusTrap>
        </div>
    );
}

EditExpiryDatePopup.propTypes = {
    onClickConfirm: PropTypes.func.isRequired,
    closePopUp: PropTypes.func.isRequired,
    popupData: PropTypes.object.isRequired,
};

export default EditExpiryDatePopup;

