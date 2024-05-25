import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import backArrow from '../../svg/back_arrow.svg';

function ViewPolicyDetails() {
    const { t } = useTranslation();
    const navigate = useNavigate();

    const moveToHome = () => {
        navigate('/partnermanagement')
    };

    const moveToPolicies = () => {
        navigate('/partnermanagement/policies')
    };

    return (
        <div className="flex-col w-full p-5 bg-anti-flash-white h-full font-inter">
            <div className="flex justify-between mb-5">
                <div className="flex space-x-4">
                    <img src={backArrow} alt="" onClick={() => moveToPolicies()} className="mt-1 cursor-pointer" />
                    <div className="flex-col mt-4">
                        <h1 className="font-bold text-lg text-md text-blue-900">{t('viewPolicyDetails.viewPolicyDetails')}</h1>
                        <div className="flex space-x-1"> 
                            <p onClick={() => moveToHome()} className="font-semibold text-blue-500 text-xs cursor-pointer">
                                {t('commons.home')} /
                            </p>
                            <p onClick={() => moveToPolicies()} className="font-semibold text-blue-500 text-xs cursor-pointer">
                                {t('viewPolicyDetails.policySection')}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ViewPolicyDetails;