import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import { isLangRTL, onPressEnterKey } from '../../../utils/AppUtils';
import Title from '../../common/Title.js';
import EmptyList from '../../common/EmptyList.js';

function ManualAdjudicationServices() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'oidcClientsList.partnerId' },
        { id: "policyGroupName", headerNameKey: "oidcClientsList.policyGroup" },
        { id: "policyName", headerNameKey: "oidcClientsList.policyName" },
        { id: "apiKeyLabel", headerNameKey: "apiKeysList.apiKeyName" },
        { id: "createdDateTime", headerNameKey: "oidcClientsList.creationDate" },
        { id: "status", headerNameKey: "oidcClientsList.status" },
        { id: "action", headerNameKey: 'oidcClientsList.action' }
    ];

    const generateApiKey = () => {
        // TODO: Navigate to generate API key page
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            <div className="flex-col mt-5">
                <div className="flex justify-between mb-5">
                    <Title title='dashboard.manualAdjudication' backLink='/partnermanagement' />
                </div>
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                    <EmptyList
                        tableHeaders={tableHeaders}
                        showCustomButton={true}
                        customButtonName='apiKeysList.generateApiKey'
                        buttonId='generate_api_key'
                        onClickButton={generateApiKey}
                    />
                </div>
            </div>
        </div>
    );
}

export default ManualAdjudicationServices;