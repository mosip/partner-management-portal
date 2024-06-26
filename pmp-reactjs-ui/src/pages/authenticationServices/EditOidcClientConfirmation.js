import React from 'react'
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, moveToOidcClientsList, moveToHome } from '../../utils/AppUtils';

import backArrow from '../../svg/back_arrow.svg';
import successIcon from '../../svg/success_message_icon.svg';

function GenerateApiKeyConfirmation() {

    const navigate = useNavigate();
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    return (
        <div className={`mt-5 w-[100%]  ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter`}>
            <div className="flex-col">
                <div className={`flex items-start space-x-3`}>
                    <img src={backArrow} onClick={() => moveToOidcClientsList(navigate)} alt="" className={`cursor-pointer max-[450px]:h-3 ${isLoginLanguageRTL ? "ml-2" :""} mt-[1%] max-[450px]:mt-[3%]`} />
                    <div className="flex-col">
                        <h1 className="font-semibold text-xl text-dark-blue max-[450px]:text-sm">{t('editOidcClient.editOidcClient')}</h1>
                        <div className="flex space-x-1  max-[350px]:flex-col">
                            <p onClick={() => moveToHome()} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                {t('commons.home')} /
                            </p>
                            <p onClick={() => moveToOidcClientsList(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                {t('authenticationServices.authenticationServices')}
                            </p>
                        </div>
                    </div>
                </div>
                <div className="flex items-center justify-center w-[100%] h-[480px] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                    <div className="flex-col justify-center items-center">
                        <div className="text-center">
                            <img src={successIcon} alt="" className={`${isLoginLanguageRTL ? "mr-28 max-[450px]:mr-20" : "ml-36 max-[450px]:ml-20"} h-40`} />
                            <h1 className="font-bold text-black text-lg max-[450px]:text-sm">
                                {t('editOidcClient.editSuccessHeader')}
                            </h1>
                            <p className="text-[#666666] text-sm font-semibold max-[450px]:text-xs">
                                {t('editOidcClient.editSuccessMsg')}
                            </p>
                            <div className={`flex gap-x-3 mt-12 max-[450px]:flex-col max-[450px]:gap-x-0 ${isLoginLanguageRTL ? "justify-center" :"justify-center"}`}>
                                <button onClick={() => moveToOidcClientsList(navigate)} type="button" className="text-white font-semibold bg-tory-blue rounded-md text-sm px-12 py-4 max-[450px]:text-xs max-[450px]:mx-6 max-[450px]:mb-2">
                                    {t('commons.goBack')}
                                </button>
                                <button onClick={() => moveToHome()} type="button" className="text-[#1447b2] font-semibold bg-white border border-[#1447b2] rounded-md text-sm px-12 py-4 max-[450px]:text-xs max-[450px]:mx-6">
                                {t('commons.home')}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default GenerateApiKeyConfirmation;