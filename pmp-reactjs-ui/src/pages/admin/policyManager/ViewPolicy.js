import React, { useState, useEffect, } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import Title from '../../common/Title';
import { bgOfStatus, formatDate, getStatusCode, isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import fileUploadBlue from '../../../svg/file_upload_blue_icon.svg';
import previewIcon from "../../../svg/preview_icon.svg";

function ViewPolicy({setShowAuthPolicyDetails, header, subTitle, viewData}) {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const moveBackToList = () => {
        setShowAuthPolicyDetails(false);
    };
    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter`}>
            <div className="flex justify-between mb-3">
                <Title title={header} subTitle={subTitle} backLink={setShowAuthPolicyDetails(false)} />
            </div>
            <div className="bg-snow-white h-fit mt-1 rounded-t-xl shadow-lg font-inter">
                <div className="flex justify-between px-7 pt-3 border-b max-[450px]:flex-col">
                    <div className="flex-col">
                        <p className="font-bold text-md text-dark-blue mb-2">
                            {viewData.policyName}
                        </p>
                        <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                        <div className={`${bgOfStatus(viewData.isActive ? 'ACTIVE' : 'INACTIVE', t)} flex w-fit py-1 px-5 text-xs rounded-md my-2 font-semibold`}>
                            {getStatusCode(viewData.isActive ? 'ACTIVE' : 'INACTIVE', t)}
                            </div>
                            <div className={`font-semibold ${isLoginLanguageRTL ? "mr-1" : "ml-3"} text-sm text-dark-blue`}>
                                {t("viewDeviceDetails.createdOn") + ' ' +
                                    formatDate(viewData.policyGroup_cr_dtimes, "date", false)
                                }
                            </div>
                            <div className="mx-1 text-gray-300">|</div>
                            <div className="font-semibold text-sm text-dark-blue">
                                {formatDate(viewData.policyGroup_cr_dtimes, "time", false)}
                            </div>
                        </div>
                    </div>
                </div>
                <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                    <div className="flex flex-wrap py-2 max-[450px]:flex-col">
                        <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                            <p className="font-[600] text-suva-gray text-xs">
                                {t("viewAuthPoliciesList.policyId")}
                            </p>
                            <p className="font-[600] text-vulcan text-sm">
                                {viewData.policyId}
                            </p>
                        </div>
                        <div className="mb-3 max-[600px]:w-[100%] w-[50%]">
                            <p className="font-[600] text-suva-gray text-xs">
                                {t("viewAuthPoliciesList.policyGroup")}
                            </p>
                            <p className="font-[600] text-vulcan text-sm">
                                {viewData.policyGroupName}
                            </p>
                        </div>
                    </div>
                    <div className="flex flex-wrap py-2 max-[450px]:flex-col">
                        <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                            <p className="font-[600] text-suva-gray text-xs">
                                {t("viewAuthPoliciesList.policyNameDescription")}
                            </p>
                            <p className="font-[600] text-vulcan text-sm">
                                {viewData.policyDesc}
                            </p>
                        </div>
                        <div className="mb-3 max-[600px]:w-[100%] w-[50%]">
                            <p className="font-[600] text-suva-gray text-xs">
                                {t("viewAuthPoliciesList.policyGroupDescription")}
                            </p>
                            <p className="font-[600] text-vulcan text-sm">
                                {viewData.policyGroupDesc}
                            </p>
                        </div>
                    </div>
                    <div className="mb-[3.5rem] max-[600px]:w-[100%] w-[30%]">
                        <p className="font-[600] mb-3 text-suva-gray text-xs">
                            {t("viewAuthPoliciesList.policyData")}
                        </p>
                        <div className='flex justify-between px-3 items-center h-[5.5rem] border-2 border-[#fedff] rounded-md bg-[#f4f6fb] '>
                            <div className='flex items-center'>
                                <img src={fileUploadBlue} className="h-7" alt="" />
                                <p className='font-semibold text-sm mx-2'>{t('viewAuthPoliciesList.jsonFilePlace')}</p>
                            </div>
                            <div className='flex justify-between px-2 py-1.5 w-[6rem] bg-white border-2 border-blue-800 rounded-md hover:cursor-pointer'>
                                <p className='text-xs font-semibold text-blue-800'>{t('viewAuthPoliciesList.preview')}</p>
                                <img src={previewIcon} alt=""/>
                            </div>
                        </div>
                    </div>
                </div>
                <hr className={`h-px w-full bg-gray-200 border-0`} />
                <div className={`flex justify-end py-7 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                    <button id="auth_Policy_view_back_btn" onClick={() => moveBackToList()} className={`h-10 w-[8rem] text-sm text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center`}>
                        {t("commons.goBack")}
                    </button>
                </div>
            </div>
        </div>
    )
}

export default ViewPolicy;