import React from "react";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import LoadingIcon from "../common/LoadingIcon";
import ErrorMessage from "../common/ErrorMessage";
import { getPartnerManagerUrl, handleServiceErrors, isLangRTL } from "../../utils/AppUtils";
import { HttpService } from "../../services/HttpService.js";
import { getUserProfile } from "../../services/UserProfileService.js";
import FocusTrap from "focus-trap-react";

function DeactivatePopup({ closePopUp, popupData, request, headerMsg, descriptionMsg, headerKeyName }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const closingPopUp = () => {
        document.body.style.overflow = "auto"
        closePopUp()
    };

    const formatDeviceCountMessage = (countOfDevices, singularText, pluralText) => {
        if (countOfDevices > 1) {
            return pluralText;
        }
        else {
            return singularText;
        }
    };

    const clickOnConfirm = async () => {
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        document.body.style.overflow = "auto"
        try {
            let response;
            if (popupData.apiKeyLabel) {
                response = await HttpService.patch(getPartnerManagerUrl(`/partners/${popupData.partnerId}/policy/${popupData.policyId}/apiKey/status`, process.env.NODE_ENV), request, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
            } else if (popupData.clientName) {
                response = await HttpService.put(getPartnerManagerUrl(`/oauth/client/${popupData.clientId}`, process.env.NODE_ENV), request, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
            } else if (popupData.isDeactivateDevice) {
                response = await HttpService.post(getPartnerManagerUrl(`/devicedetail/deactivate-device`, process.env.NODE_ENV), request, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
            } else if (popupData.isDeactivateSbi) {
                response = await HttpService.post(getPartnerManagerUrl(`/securebiometricinterface/deactivate-sbi`, process.env.NODE_ENV), request, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
            } else if (popupData.isDeactivateFtm) {
                response = await HttpService.post(getPartnerManagerUrl(`/ftpchipdetail/deactivate-ftm`, process.env.NODE_ENV), request, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
            } else if (popupData.isDeactivatePartner) {
                response = await HttpService.patch(getPartnerManagerUrl(`/partners/${popupData.partnerId}`, process.env.NODE_ENV), request, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
            } 
            const responseData = response.data;
            if (responseData && responseData.response) {
                window.location.reload();
            } else {
                setDataLoaded(true);
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        } catch (err) {
            setDataLoaded(true);
            setErrorMsg(err);
        }
    }

    const styles = {
        loadingDiv: "!py-[35%]"
    }

    const customStyle = {
        outerDiv: "!flex !justify-end",
        innerDiv: "!flex !justify-between !items-center !rounded-xl !w-[55%] !min-h-12 !p-3 !m-1 !-mb-6"
    }

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-[50%] z-50 font-inter cursor-default">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className={`bg-white md:w-[390px] w-[55%] mx-auto rounded-lg shadow-lg h-fit`}>
                    {!dataLoaded && (
                        <LoadingIcon styleSet={styles} />
                    )}
                    {dataLoaded && (
                        <div className="relative">
                            {errorMsg && (
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={customStyle} />
                            )}
                            <div className={`p-[8%] flex-col text-center justify-center items-center`}>
                                {!isLoginLanguageRTL ?
                                    <p className="text-base leading-snug font-semibold text-black break-normal px-[6%]">
                                        {t(headerMsg)} '{(popupData.isDeactivateDevice || popupData.isDeactivateFtm) ? ' - ' + popupData.make + ' - ' + popupData.model : (popupData.isDeactivatePartner) ? '' : ' - ' + headerKeyName}'?
                                    </p>
                                    : <p className="text-base leading-snug font-semibold text-black break-normal px-[6%]">
                                        {t(headerMsg)} '{(popupData.isDeactivateDevice || popupData.isDeactivateFtm) ? ' - ' + popupData.make + ' - ' + popupData.model : (popupData.isDeactivatePartner) ? '' : ' - ' + headerKeyName}'
                                    </p>
                                }
                                <p className="text-sm text-[#666666] break-normal py-[5%]">
                                    {t(descriptionMsg)}
                                </p>
                                {popupData.isDeactivateSbi &&
                                    (<div className="bg-[#FFF7E5] border-2 break-words border-[#EDDCAF] rounded-md w-full p-[2%] mb-2">
                                        <p className="text-sm font-inter text-[#8B6105]">{t(formatDeviceCountMessage(popupData.countOfApprovedDevices, t('deactivateSbi.deactivateApprovedDevicesSingular'), t('deactivateSbi.deactivateApprovedDevicesPlural')), { devicesCount: popupData.countOfApprovedDevices })}
                                            | {t(formatDeviceCountMessage(popupData.countOfPendingDevices, t('deactivateSbi.deactivatePendingDevicesSingular'), t('deactivateSbi.deactivatePendingDevicesPlural')), { devicesCount: popupData.countOfPendingDevices })}
                                        </p>
                                    </div>)
                                }
                                <div className="flex flex-row items-center justify-center space-x-3 pt-[4%]">
                                    <button id="deactivate_cancel_btn" onClick={() => closingPopUp()} type="button" className="w-40 h-12 border-[#1447B2] border rounded-md text-tory-blue text-sm font-semibold">{t('requestPolicy.cancel')}</button>
                                    <button id="deactivate_submit_btn" onClick={() => clickOnConfirm()} type="button" className={`w-40 h-12 border-[#1447B2] border rounded-md bg-tory-blue text-white text-sm font-semibold ${isLoginLanguageRTL && '!mr-3'}`}>{t('deactivateOidcClient.confirm')}</button>
                                </div>
                            </div>
                        </div>
                    )}
                </div>
            </FocusTrap>
        </div>
    )

}

export default DeactivatePopup;