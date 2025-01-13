import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { onPressEnterKey } from '../../../utils/AppUtils';

function CertificateTab({ activeRootCA, rootCertificatesPath, activeIntermediateCA, intermediateCertificatesPath }) {

    const { t } = useTranslation();
    const navigate = useNavigate();

    const changeToRootCA = () => {
        navigate(rootCertificatesPath)
    };

    const changeToIntermediateCA = () => {
        navigate(intermediateCertificatesPath)
    };

    return (
        <div className='flex text-xs bg-[#FCFCFC] font-bold space-x-16 items-start rounded-lg px-[1.5%] pt-[2%] mt-3'>
            <div id='root_of_trust_certificates_tab' className={`flex-col justify-center text-center`}>
                <button onClick={changeToRootCA} className={`${activeRootCA ? "text-[#1447b2]" : "text-[#031640]"} mb-[0.6rem] cursor-pointer text-sm`}>
                    <h6> {t('certificatesList.rootCA')} </h6>
                </button>

                <div className={`h-1 w-24 ${activeRootCA ? "bg-tory-blue" : "bg-transparent"}  rounded-t-md`}></div>
            </div>
            <div id='intermediate_root_of_trust_certificates_tab' className={`flex-col justify-center text-center`}>
                <button onClick={changeToIntermediateCA} className={`${activeIntermediateCA ? "text-[#1447b2]" : "text-[#031640]"} mb-[0.6rem] cursor-pointer text-sm`}>
                    <h6> {t('certificatesList.intermediateCA')}</h6>
                </button>
                <div className={`h-1 w-32 ${activeIntermediateCA ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
            </div>
        </div>
    )
}

export default CertificateTab;