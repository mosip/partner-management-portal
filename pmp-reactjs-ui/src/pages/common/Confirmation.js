import React, { useState, useEffect } from 'react'
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, moveToHome } from '../../utils/AppUtils';
import backArrow from '../../svg/back_arrow.svg';
import successIcon from '../../svg/success_message_icon.svg';
import UploadCertificate from '../certificates/UploadCertificate';

function Confirmation() {

    const navigate = useNavigate();
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [confirmationData, setConfirmationData] = useState({});
    const [selectedPartnerData, setSelectedPartnerData] = useState(null);
    const [showPopup, setShowPopup] = useState(false);
    const [style, setStyle] = useState({});

    useEffect(() => {
        const confirmationData = localStorage.getItem('confirmationData');
        if (confirmationData) {
            try {
                const data = JSON.parse(confirmationData);
                setConfirmationData(data);
                setStyle(data.styleSet);
            } catch (error) {
                console.error('Error in confirmation page :', error);
            }
        } else {
            navigate('/partnermanagement');
        }
    }, [navigate]);


    const clickOnUpload = (partner) => {
        document.body.style.overflow = "hidden";
        setShowPopup(!showPopup);
        setSelectedPartnerData(partner);
    };

    const closePopup = (state) => {
        if (state) {
            setShowPopup(false);
            document.body.style.overflow = "auto";
            window.location.reload();
        }
    };

    return (
        <div className={`mt-5 w-[100%]  ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter`}>
            <div className="flex-col">
                <div className={`flex items-start space-x-3`}>
                    <img src={backArrow} onClick={() => navigate(confirmationData.backUrl)} alt="" className={`cursor-pointer max-[450px]:h-3 ${isLoginLanguageRTL ? "ml-2 rotate-180" : ""} mt-[1%] max-[450px]:mt-[3%]`} />
                    <div className="flex-col">
                        <h1 className="font-semibold text-xl text-dark-blue max-[450px]:text-sm">{t(confirmationData.title)}</h1>
                        <div className="flex space-x-1  max-[350px]:flex-col">
                            <p onClick={() => moveToHome(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                {t('commons.home')} /
                            </p>
                            <p onClick={() => navigate(confirmationData.backUrl)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                {t(confirmationData.subNavigation)}
                            </p>
                        </div>
                    </div>
                </div>
                <div className="flex items-center justify-center w-[100%] h-[480px] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                    <div className="flex-col justify-center items-center">
                        <img src={successIcon} alt="" className={`${isLoginLanguageRTL ? (style && style.imgIconRtl ? style.imgIconRtl : "") : (style && style.imgIconLtr ? style.imgIconLtr : "")} h-40`} />
                        <div className="text-center space-y-1">
                            <h1 className="font-bold text-black text-lg max-[450px]:text-sm">
                                {t(confirmationData.header)}
                            </h1>
                            <p className="text-[#666666] text-sm font-semibold max-[450px]:text-xs">
                                {t(confirmationData.description)}
                            </p>
                            {!confirmationData.ftmPartnerData &&
                                <div className={`flex gap-x-3 mt-12 max-[450px]:flex-col max-[450px]:gap-x-0 justify-center`}>
                                    <button onClick={() => navigate(confirmationData.backUrl)} type="button" className="text-white font-semibold bg-tory-blue rounded-md text-sm px-12 py-4 max-[450px]:text-xs max-[450px]:mx-6 max-[450px]:mb-2">
                                        {t('commons.goBack')}
                                    </button>
                                    <button onClick={() => moveToHome(navigate)} type="button" className="text-[#1447b2] font-semibold bg-white border border-[#1447b2] rounded-md text-sm px-12 py-4 max-[450px]:text-xs max-[450px]:mx-6">
                                        {t('commons.home')}
                                    </button>
                                </div>
                            }

                            {confirmationData.ftmPartnerData &&
                                <div>
                                    <button onClick={() => clickOnUpload(confirmationData.ftmPartnerData)} type="button" className="text-white font-semibold bg-tory-blue rounded-md text-sm px-3 py-4 max-[450px]:text-xs max-[450px]:mx-6 max-[450px]:mb-2 mt-4">
                                        {t(confirmationData.uploadFtm)}
                                    </button>
                                </div>
                            }
                        </div>
                    </div>
                </div>
            </div>
            {showPopup && (
                <UploadCertificate header={t('addFtm.uploadFtmCertificate')} closePopup={closePopup} partnerData={confirmationData.ftmPartnerData} />
            )}
        </div>
    )
}

export default Confirmation;