import React from 'react'
import { Trans, useTranslation } from 'react-i18next';
import { getUserProfile } from '../services/UserProfileService';
import { isLangRTL, getPartnerTypeDescription } from '../utils/AppUtils';
import Title from '../pages/common/Title';

function UserProfile() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const userData = getUserProfile();

    return (
        <div className={`mt-5 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter`}>
            <Title title='userProfile.myProfile' backLink='/partnermanagement' />
            {(!userData.orgName || !userData.address || !userData.partnerType || !userData.phoneNumber) && (
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                    <div className="flex items-center justify-center p-2">
                        <div className="p-2 bg-[#FFF7E5] border-2 border-[#EDDCAF] rounded-md w-full">
                            <p className="text-sm font-medium text-[#8B6105]">
                                <Trans i18nKey="userProfile.info" components={{ italic: <i /> }} />
                            </p>
                        </div>
                    </div>
                </div>
            )}
            <div className="bg-snow-white h-fit my-0.5 mt-3 rounded-t-lg shadow-sm font-inter">
                <div className="flex justify-between px-7 py-2 border-b max-[450px]:flex-col">
                    <p className="font-semibold text-base text-[#031640] my-2 text-left">
                        {t('userProfile.profileInformation')}
                    </p>
                </div>
            </div>
            <div className={`bg-white w-[100%] ${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2 rounded-b-lg`}>
                <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                    <div className="w-[49%] max-[600px]:w-[100%] mb-3">
                        <p className="font-[600] text-suva-gray text-sm">
                            {t("userProfile.firstName")}
                        </p>
                        <p className="font-[600] text-vulcan text-base break-normal break-words">
                            {userData.firstName}
                        </p>
                    </div>
                    <div className={`w-[49%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "mr-[1%]": "ml-[1%]"}`}>
                        <p className="font-[600] text-suva-gray text-sm">
                            {t("userProfile.lastName")}
                        </p>
                        <p className="font-[600] text-vulcan text-base break-normal break-words">
                            {userData.lastName}
                        </p>
                    </div>
                </div>
                <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                    <div className="w-[49%] max-[600px]:w-[100%] mb-3">
                        <p className={`font-[600] text-sm ${userData.orgName ? 'text-suva-gray' : 'text-crimson-red'}`}>
                            {t("userProfile.organisationName")}
                        </p>
                        <p className="font-[600] text-vulcan text-base break-normal break-words">
                            {userData.orgName ?? t('statusCodes.notAvailable')}
                        </p>
                    </div>
                    <div className={`w-[49%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "mr-[1%]": "ml-[1%]"}`}>
                        <p className={`font-[600] text-sm ${userData.address ? 'text-suva-gray' : 'text-crimson-red'}`}>
                            {t("userProfile.address")}
                        </p>
                        <p className="font-[600] text-vulcan text-base break-normal break-words">
                            {userData.address ?? t('statusCodes.notAvailable')}
                        </p>
                    </div>
                </div>
                <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                    <div className="w-[49%] max-[600px]:w-[100%] mb-3">
                        <p className={`font-[600] text-sm ${userData.partnerType ? 'text-suva-gray' : 'text-crimson-red'}`}>
                            {t("userProfile.partnerType")}
                        </p>
                        <p className="font-[600] text-vulcan text-base break-normal break-words">
                            {userData.partnerType ? getPartnerTypeDescription(userData.partnerType, t): t('statusCodes.notAvailable')}
                        </p>
                    </div>
                    <div className={`w-[49%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "mr-[1%]": "ml-[1%]"}`}>
                        <p className={`font-[600] text-sm ${userData.phoneNumber ? 'text-suva-gray' : 'text-crimson-red'}`}>
                            {t("userProfile.phoneNumber")}
                        </p>
                        <p className="font-[600] text-vulcan text-base break-normal break-words">
                            {userData.phoneNumber ?? t('statusCodes.notAvailable')}
                        </p>
                    </div>
                </div>
                <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                    <div className="w-[49%] max-[600px]:w-[100%] mb-3">
                        <p className="font-[600] text-suva-gray text-sm">
                            {t("userProfile.emailAddress")}
                        </p>
                        <p className="font-[600] text-vulcan text-base break-normal break-words">
                            {userData.email}
                        </p>
                    </div>
                    <div className={`w-[49%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "mr-[1%]": "ml-[1%]"}`}>
                        <p className="font-[600] text-suva-gray text-sm">
                            {t("userProfile.userName")}
                        </p>
                        <p className="font-[600] text-vulcan text-base break-normal break-words">
                            {userData.userName}
                        </p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default UserProfile;