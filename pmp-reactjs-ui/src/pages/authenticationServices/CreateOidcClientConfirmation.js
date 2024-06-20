import React from 'react'
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, moveToAuthenticationServices } from '../../utils/AppUtils';

import backArrow from '../../svg/back_arrow.svg';
import successIcon from '../../svg/success_message_icon.svg';

function CreateOidcClientConfirmation() {

    const navigate = useNavigate();
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const moveToHome = () => {
        navigate('/partnermanagement')
    };

    return (
        <div className="ml-32 mr-5 mt-5 w-[100%] h-[100%] font-inter">
            <div className="flex-col">
                <div className="flex items-start space-x-3">
                    <img src={backArrow} onClick={() => moveToAuthenticationServices(navigate)} alt="" className="mt-[1%] cursor-pointer" />
                    <div className="flex-col">
                        <h1 className="font-semibold text-xl text-dark-blue">{t('createOidcClient.createOidcClient')}</h1>
                        <div className="flex space-x-1">
                            <p onClick={() => moveToHome()} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                {t('commons.home')} /
                            </p>
                            <p onClick={() => moveToAuthenticationServices(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                {t('authenticationServices.authenticationServices')}
                            </p>
                        </div>
                    </div>
                </div>
                <div className="flex items-center justify-center w-[100%] h-[480px] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                    <div className="flex-col justify-center">
                        <img src={successIcon} alt="" className={`${isLoginLanguageRTL ? "mr-56" : "ml-[125px]"} h-40`} />
                        <div className="text-center">
                            <h1 className="font-bold text-black text-lg">
                                {t('createOidcClient.requestSuccessHeader')}
                            </h1>
                            <p className="text-[#666666] text-sm font-semibold">
                                {t('createOidcClient.requestSuccessMsg')}
                            </p>
                            <div className="flex space-x-3 mt-12 justify-center">
                                <button onClick={() => moveToAuthenticationServices(navigate)} type="button" className="text-white font-semibold border bg-tory-blue rounded-md text-sm px-12 py-4">
                                    {t('commons.goBack')}
                                </button>
                                <button onClick={() => moveToHome()} type="button" className="text-[#1447b2] font-semibold bg-white border border-[#1447b2] rounded-md text-sm px-12 py-4">
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

export default CreateOidcClientConfirmation;