import React from 'react';
import { useNavigate } from "react-router-dom";
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../../services/UserProfileService';
import { isLangRTL, onPressEnterKey } from '../../../utils/AppUtils';

function PoliciesTab() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const activeTab = localStorage.getItem('activeTab') ? localStorage.getItem('activeTab') : 'policyGroup';
    const navigate = useNavigate();

    const changeToPolicyGroup = () => {
        localStorage.setItem('activeTab', 'policyGroup');
        navigate('/partnermanagement/policy-manager/policy-group-list')
    };

    const changeToAuthPolicy = () => {
        localStorage.setItem('activeTab', 'Auth');
        navigate('/partnermanagement/policy-manager/auth-policies-list')
    };

    const changeToDataSharePolicy = () => {
        localStorage.setItem('activeTab', 'DataShare');
        navigate('/partnermanagement/policy-manager/data-share-policies-list')
    };

    return (
        <div className='flex text-xs bg-[#FCFCFC] font-bold space-x-16 items-start rounded-lg px-[1.5%] pt-[2%] overflow-x-auto'>
            <div className={`flex-col justify-center`}>
                <button id='policies_policy_group_tab' onClick={changeToPolicyGroup} className={`${activeTab === "policyGroup" ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] cursor-pointer text-sm`}>
                    <h6> {t('policyGroupList.policyGroup')} </h6>
                </button>
                
                <div className={`h-1 w-full ${activeTab === "policyGroup" ? "bg-tory-blue" : "bg-transparent"}  rounded-t-md`}></div>
            </div>
            <div className={`flex-col justify-center`}>
                <button id='policies_auth_policy_tab' onClick={changeToAuthPolicy} className={`${activeTab === "Auth" ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] cursor-pointer text-sm ${isLoginLanguageRTL && 'mr-10'}`}>
                    <h6> {t('policyGroupList.authPolicy')} </h6>
                </button>

                <div className={`h-1 w-full ${isLoginLanguageRTL && 'mr-6'} ${activeTab === "Auth" ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
            </div>
            <div className={`flex-col justify-center`}>
                <button id='policies_data_share_policy_tab' onClick={changeToDataSharePolicy} className={`${activeTab === "DataShare" ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] cursor-pointer text-sm`}>
                    <h6> {t('policyGroupList.dataSharePolicy')}</h6>
                </button>

                <div className={`h-1 w-full ${activeTab === "DataShare" ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
            </div>
        </div>
    )
}
export default PoliciesTab;