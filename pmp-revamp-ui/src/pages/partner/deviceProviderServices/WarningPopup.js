import React, { useEffect } from "react";
import { useTranslation } from "react-i18next";
import { isLangRTL } from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService.js";
import FocusTrap from "focus-trap-react";

function WarningPopup({ closePopUp, clickOnConfirm }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const closingPopUp = () => {
        closePopUp()
    };

    useEffect(() => {
        document.body.style.overflow = 'hidden';
        return () => {
            document.body.style.overflow = 'auto';
        };
    }, []);

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-[50%] z-50 font-inter cursor-default">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className={`bg-white md:w-[390px] w-[55%] mx-auto rounded-lg shadow-lg h-fit`}>
                    <div className={`p-7 flex-col text-center justify-center items-center`}>
                        <p className="text-sm break-words pb-[6%] text-left">
                            {t('addDevices.limitExceedWarning')}
                        </p>
                        <div className="flex flex-row items-center justify-center space-x-3 pt-[4%]">
                            <button onClick={() => closingPopUp()} type="button" className="w-40 h-12 border-[#1447B2] border rounded-md text-tory-blue text-sm font-semibold">{t('requestPolicy.cancel')}</button>
                            <button onClick={() => clickOnConfirm()} type="button" className={`w-40 h-12 border-[#1447B2] border rounded-md bg-tory-blue text-white text-sm font-semibold ${isLoginLanguageRTL && '!mr-3'}`}>{t('deactivateOidcClient.confirm')}</button>
                        </div>
                    </div>
                </div>
            </FocusTrap>
        </div>
    )

}

export default WarningPopup;