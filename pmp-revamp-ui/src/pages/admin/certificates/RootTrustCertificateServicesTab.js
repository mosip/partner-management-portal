import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../../services/UserProfileService';
import { isLangRTL, onPressEnterKey } from '../../../utils/AppUtils';

function RootTrustCertificateServicesTab({ activeRootOfTustCertificates, rootOfTustCertificatesPath, activeIntermediateRootOfTrustCertificates, intermediateRootOfTrustCertificatesPath }) {

    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();

    const changeToRootOfTustCertificates = () => {
        navigate(rootOfTustCertificatesPath)
    };

    const changeToIntermediateRootOfTrustCertificates = () => {
        navigate(intermediateRootOfTrustCertificatesPath)
    };

    return (
        <div className='flex text-xs bg-[#FCFCFC] font-bold space-x-16 items-start rounded-lg px-[1.5%] pt-[2%] mt-3'>
            <div className={`flex-col justify-center`}>
                <h6 id='root_of_trust_certificates_tab' onClick={changeToRootOfTustCertificates}
                    className={`${activeRootOfTustCertificates ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] cursor-pointer text-sm`}
                    tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, changeToRootOfTustCertificates)}>
                    {t('listOfRootTrustCertificates.rootOfTrustCertificates')}
                </h6>
                <div className={`h-1 w-[9rem] ${activeRootOfTustCertificates ? "bg-tory-blue" : "bg-transparent"}  rounded-t-md`}></div>
            </div>
            <div className={`flex-col justify-center`}>
                <h6 id='intermediate_root_of_trust_certificates_tab' onClick={changeToIntermediateRootOfTrustCertificates}
                    className={`${activeIntermediateRootOfTrustCertificates ? "text-[#1447b2]" : "text-[#031640]"} mb-[7%] cursor-pointer text-sm`}
                    tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, changeToIntermediateRootOfTrustCertificates)}>
                    {t('listOfIntermediateRootTrustCertificates.intermediateRootTrustCertificates')}
                </h6>
                <div className={`h-1 w-[15rem] ${activeIntermediateRootOfTrustCertificates ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
            </div>
        </div>
    )
}

export default RootTrustCertificateServicesTab;