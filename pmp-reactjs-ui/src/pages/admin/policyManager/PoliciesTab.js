import React from 'react';
import { useNavigate } from "react-router-dom";
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../../services/UserProfileService';
import { isLangRTL, onPressEnterKey } from '../../../utils/AppUtils';

function PoliciesTab ({activePolicyGroup, setActivePolicyGroup, activeAuthPolicy, setActiveAuthPolicy, activeDataSharePolicy, setActiveDataSharePolicy}) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();

    const changeToPolicyGroup = () => {
        navigate('/partnermanagement/admin/policy-manager/policy-group-list')
        setActivePolicyGroup(true); 
        setActiveAuthPolicy(false);
        setActiveDataSharePolicy(false);
    };

    const changeToAuthPolicy = () => {
        const policyTypeData = {
            policyType: "Auth",
            subTitle: t('policiesList.listOfAuthPolicies'),
            createPolicyButtonName: t('policiesList.createAuthPolicy'),
            errorMessage: t('policiesList.errorInAuthPolicies'),
        }
        localStorage.setItem('policyTypeData', JSON.stringify(policyTypeData));
        navigate('/partnermanagement/admin/policy-manager/auth-policies-list')
        setActivePolicyGroup(false); 
        setActiveAuthPolicy(true);
        setActiveDataSharePolicy(false);
    };

    const changeToDataSharePolicy = () => {
        const policyTypeData = {
            policyType: "DataShare",
            subTitle: t('policiesList.listOfDataSharePolicies'),
            createPolicyButtonName: t('policiesList.createDataSharePolicy'),
            errorMessage: t('policiesList.errorInDataSharePolicies'),
        }
        localStorage.setItem('policyTypeData', JSON.stringify(policyTypeData));
        navigate('/partnermanagement/admin/policy-manager/data-share-policies-list')
        setActivePolicyGroup(false); 
        setActiveAuthPolicy(false);
        setActiveDataSharePolicy(true);
    };

    return (
        <div className='flex text-xs bg-[#FCFCFC] font-bold space-x-16 items-start rounded-lg px-[1.5%] pt-[2%]'>
            <div className={`flex-col justify-center`}>
                <h6 id='policies_policy_group_tab' onClick={changeToPolicyGroup}
                    className={`${activePolicyGroup ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] cursor-pointer text-sm`}
                    tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, changeToPolicyGroup)}>
                    {t('policyGroupList.policyGroup')}
                </h6>
                <div className={`h-1 w-full ${activePolicyGroup ? "bg-tory-blue" : "bg-transparent"}  rounded-t-md`}></div>
            </div>
            <div className={`flex-col justify-center`}>
                <h6 id='policies_auth_policy_tab' onClick={changeToAuthPolicy}
                    className={`${activeAuthPolicy ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] cursor-pointer text-sm ${isLoginLanguageRTL && 'mr-10'}`}
                    tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, changeToAuthPolicy)}>
                    {t('policyGroupList.authPolicy')}
                </h6>
                <div className={`h-1 w-full ${isLoginLanguageRTL && 'mr-6'} ${activeAuthPolicy ? "bg-tory-blue" : "bg-transparent" } rounded-t-md`}></div>
            </div>
            <div className={`flex-col justify-center`}>
                <h6 id='policies_data_share_policy_tab' onClick={changeToDataSharePolicy}
                    className={`${activeDataSharePolicy ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] cursor-pointer text-sm`}
                    tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, changeToDataSharePolicy)}>
                    {t('policyGroupList.dataSharePolicy')}
                </h6>
                <div className={`h-1 w-full ${activeDataSharePolicy ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
            </div>
        </div>
    )
}
export default PoliciesTab;