import React from 'react'
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../services/UserProfileService';
import { isLangRTL, moveToHome } from '../utils/AppUtils';
import backArrow from '.././svg/back_arrow.svg';

function UserProfile() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const userData = getUserProfile();
    console.log(userData);

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            <div className="flex-col mt-7">
                <div className="flex justify-between mb-5">
                    <div className={`flex items-start gap-x-2`}>
                        <img src={backArrow} alt="" onClick={() => moveToHome(navigate)} className={`mt-[12%] cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} />
                        <div className="flex-col mt-[3%]">
                            <h1 className="font-semibold text-lg text-dark-blue">{t('userProfile.myProfile')}</h1>
                            <p onClick={() => moveToHome(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                {t('commons.home')}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div className="bg-snow-white h-fit my-0.5 rounded-t-lg shadow-sm font-inter">
                <div className="flex justify-between px-7 py-2 border-b max-[450px]:flex-col">
                    <p className="font-semibold text-md text-[#031640] my-2 text-left">
                        {t('userProfile.profileInformation')}
                    </p>
                </div>
            </div>
            <div className={`bg-white w-[100%] ${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2 rounded-b-lg`}>
                <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                    <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                        <p className="font-[600] text-suva-gray text-xs">
                            {t("userProfile.firstName")}
                        </p>
                        <p className="font-[600] text-vulcan text-sm">
                            {userData.firstName}
                        </p>
                    </div>
                    <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                        <p className="font-[600] text-suva-gray text-xs">
                            {t("userProfile.lastName")}
                        </p>
                        <p className="font-[600] text-vulcan text-sm">
                            {userData.lastName}
                        </p>
                    </div>
                </div>
                <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                    <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                        <p className="font-[600] text-suva-gray text-xs">
                            {t("userProfile.organisationName")}
                        </p>
                        <p className="font-[600] text-vulcan text-sm">
                            {userData.orgName}
                        </p>
                    </div>
                    <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                        <p className="font-[600] text-suva-gray text-xs">
                            {t("userProfile.address")}
                        </p>
                        <p className="font-[600] text-vulcan text-sm">
                            {userData.address}
                        </p>
                    </div>
                </div>
                <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                    <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                        <p className="font-[600] text-suva-gray text-xs">
                            {t("userProfile.partnerType")}
                        </p>
                        <p className="font-[600] text-vulcan text-sm">
                        {t("partnerTypes.authPartner")}
                        </p>
                    </div>
                    <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                        <p className="font-[600] text-suva-gray text-xs">
                            {t("userProfile.phoneNumber")}
                        </p>
                        <p className="font-[600] text-vulcan text-sm">
                            {userData.phoneNumber}
                        </p>
                    </div>
                </div>
                <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                    <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                        <p className="font-[600] text-suva-gray text-xs">
                            {t("userProfile.emailAddress")}
                        </p>
                        <p className="font-[600] text-vulcan text-sm">
                            {userData.email}
                        </p>
                    </div>
                    <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                        <p className="font-[600] text-suva-gray text-xs">
                            {t("userProfile.userName")}
                        </p>
                        <p className="font-[600] text-vulcan text-sm">
                            {userData.userName}
                        </p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default UserProfile;