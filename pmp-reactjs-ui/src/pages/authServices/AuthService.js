import React from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL } from '../../utils/AppUtils';
import backArrow from '../../svg/back_arrow.svg';
import rectangleGrid from '../../svg/rectangle_grid.svg';

function AuthService() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const moveToHome = () => {
        navigate('/partnermanagement')
    };

    return (
        <div className={`mt-5 w-[100%] ${isLoginLanguageRTL ? "mr-32 ml-5" : "ml-32 mr-5"} overflow-x-scroll font-inter`}>
            <div className="flex-col">
                <div className="flex justify-between mb-5">
                    <div className={`flex space-x-3`}>
                        <img src={backArrow} alt="" onClick={() => moveToHome()} className={`cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} />
                        <div className="flex-col mt-[3%]">
                            <h1 className="font-semibold text-xl text-dark-blue">{t('authenticationServices.authenticationServices')}</h1>
                            <p onClick={() => moveToHome()} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                {t('commons.home')}
                            </p>
                        </div>
                    </div>
                </div>
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                    <div className="flex justify-between py-2 pt-4 text-xs font-medium text-[#6F6E6E]">
                        <div className="flex gap-x-28 ml-6">
                            <h6>{t('authenticationServices.partnerId')}</h6>
                            <h6>{t('authenticationServices.policyName')}</h6>
                        </div>
                        <h6 className={`${isLoginLanguageRTL?"mr-24":"ml-24"}`}>{t('authenticationServices.oidcClientName')}</h6>
                        <div className="flex space-x-16">
                            <h6>{t('authenticationServices.createdDate')}</h6>
                            <h6>{t('authenticationServices.status')}</h6>
                        </div>
                        <div className="flex space-x-12 mr-10">
                            <h6>{t('authenticationServices.oidcClientId')}</h6>
                            <h6>{t('authenticationServices.action')}</h6>
                        </div>
                    </div>

                    <hr className="h-px mx-3 bg-gray-200 border-0" />

                    <div className="flex items-center justify-center p-28">
                        <div className="flex-col justify-center">
                            <img src={rectangleGrid} alt="" />
                            <button type="button" className={`text-white font-semibold mt-8 ${isLoginLanguageRTL?"mr-":"ml-12"} bg-tory-blue rounded-md text-base px-4 py-3`}>
                                {t('authenticationServices.createOidcClientBtn')}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AuthService