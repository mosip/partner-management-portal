import React from 'react';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL } from '../../utils/AppUtils';


function AuthenticationTabs({activeOidcClient, setActiveOicdClient, activeApiKey , setActiveApiKey}) {
    
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    return (
        <div className='flex text-xs bg-[#FCFCFC] font-bold space-x-16 items-start rounded-lg px-[1.5%] pt-[2%]'>
            <div className={`flex-col justify-center`}>
                <h6 onClick={() => { setActiveOicdClient(true); setActiveApiKey(false) }}
                    className={`${activeOidcClient ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] ${isLoginLanguageRTL ? "mr-[10%]" : "ml-[10%]"} cursor-pointer text-sm`}>
                    {t('authenticationServices.oidcClient')}
                </h6>
                <div className={`h-1 w-24 ${activeOidcClient ? "bg-tory-blue" : "bg-transparent"}  rounded-t-md`}></div>
            </div>
            <div className={`flex-col justify-center`}>
                <h6 onClick={() => { setActiveOicdClient(false); setActiveApiKey(true) }}
                    className={`${activeApiKey ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] ${isLoginLanguageRTL ? "mr-[20%]" : "ml-[20%]"} cursor-pointer text-sm`}>
                    {t('authenticationServices.apiKey')}
                </h6>
                <div className={`h-1 w-24 ${activeApiKey ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
            </div>
        </div>
    )
}

export default AuthenticationTabs;