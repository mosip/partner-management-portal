import React from 'react';
import { useTranslation } from 'react-i18next';
import Title from '../../common/Title';
import { getUserProfile } from '../../../services/UserProfileService';
import { isLangRTL } from '../../../utils/AppUtils';

const ManualAdjudicationServices = () => {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

    return (
        <div className={`w-full p-4 bg-anti-flash-white h-full font-inter break-words max-450:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} overflow-x-scroll`}>
            <div className={`flex-col mt-5 bg-anti-flash-white h-full font-inter break-words max-450:text-sm mb-[2%]`}>
                <div className="flex justify-between mb-3">
                    <Title title='dashboard.manualAdjudication' subTitle='' backLink={'/partnermanagement/dashboard'} />
                </div>
            </div>

            <div className="bg-white p-6 rounded-lg shadow-md border border-gray-200 w-full flex flex-col items-center justify-center min-h-[400px]">
                <h2 className="text-lg font-semibold mb-2">{t('dashboard.manualAdjudication')}</h2>
                <p className="text-sm text-gray-600 mb-6 text-center">
                    {t('dashboard.manualAdjudicationDesc')}
                </p>
                <button
                    className='bg-tory-blue text-white px-6 py-2 rounded-md hover:bg-blue-800 transition-colors font-semibold'
                >
                    {t('authenticationServices.generateApiKeyBtn')}
                </button>
            </div>
        </div>
    );
};

export default ManualAdjudicationServices;