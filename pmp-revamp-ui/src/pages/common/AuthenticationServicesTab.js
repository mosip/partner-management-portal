import React from 'react';
import { useNavigate } from "react-router-dom";
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, onPressEnterKey } from '../../utils/AppUtils';

function AuthenticationServicesTab({ activeOidcClient, oidcClientPath, activeApiKey, apiKeyPath }) {

    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();

    const changeToOidcClients = () => {
        navigate(oidcClientPath)
    };

    const changeToApiKey = () => {
        navigate(apiKeyPath)
    };

    return (
        <div className='flex text-xs bg-[#FCFCFC] font-bold space-x-16 items-start rounded-lg px-[1.5%] pt-[2%]'>
            <div className={`flex-col justify-center`}>
                <button id='authentication_oidc_tab' onClick={changeToOidcClients} className={`${activeOidcClient ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] ${isLoginLanguageRTL ? "mr-[10%]" : "ml-[10%]"} cursor-pointer text-sm`}>
                    <h6> {t('authenticationServices.oidcClient')} </h6>
                </button>
                
                <div className={`h-1 w-24 ${activeOidcClient ? "bg-tory-blue" : "bg-transparent"}  rounded-t-md`}></div>
            </div>
            <div className={`flex-col justify-center`}>
                <button id='authentication_apikey_tab' onClick={changeToApiKey} className={`${activeApiKey ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] ${isLoginLanguageRTL ? "mr-[20%]" : "ml-[20%]"} cursor-pointer text-sm`}>
                    <h6> {t('authenticationServices.apiKey')} </h6>
                </button>

                <div className={`h-1 w-24 ${activeApiKey ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
            </div>
        </div>
    )
}

export default AuthenticationServicesTab;