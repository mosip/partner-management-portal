import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { isLangRTL } from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService";

function PartnerNotificationsTab({ partnerType, activeTab }) {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const changeToPartnerCert = () => {
        navigate('/partnermanagement/notifications/view-partner-certificate-expiry');
    };

    const changeToFtmCert = () => {
        navigate('/partnermanagement/notifications/view-ftm-chip-certificate-expiry')
    };

    const changeToApiKey = () => {
        navigate('/partnermanagement/notifications/view-api-key-expiry')
    };

    const changeToSbi = () => {
        navigate('/partnermanagement/notifications/view-sbi-expiry')
    };

    return (
        <div className='flex text-xs bg-[#FCFCFC] font-bold space-x-16 items-start rounded-lg px-8'>
            <div id='partner_cert_notifications_tab' className={`flex-col justify-center text-center`}>
                <button onClick={changeToPartnerCert} className={`${activeTab === "partner" ? "text-[#1447b2]" : "text-[#031640]"} py-4 cursor-pointer text-base`}>
                    <h6> {t('partnerNotificationsTab.partnerCertificate')} </h6>
                </button>

                <div className={`h-1 w-full ${activeTab === "partner" ? "bg-tory-blue" : "bg-transparent"}  rounded-t-md`}></div>
            </div>
            {partnerType === "FTM_PROVIDER" && (
                <div id='ftm_cert_notifications_tab' className={`flex-col justify-center text-center`}>
                    <button onClick={changeToFtmCert} className={`${activeTab === "ftm" ? "text-[#1447b2]" : "text-[#031640]"} py-4 cursor-pointer text-base ${isLoginLanguageRTL && 'mr-10'}`}>
                        <h6> {t('partnerNotificationsTab.ftmCertificate')}</h6>
                    </button>
                    <div className={`h-1 w-full ${isLoginLanguageRTL && 'mr-6'} ${activeTab === "ftm" ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
                </div>
            )}
            {partnerType === "AUTH_PARTNER" && (
            <div id='api_key_notifications_tab' className={`flex-col justify-center text-center`}>
                <button onClick={changeToApiKey} className={`${activeTab === "apikey" ? "text-[#1447b2]" : "text-[#031640]"} py-4 cursor-pointer text-base ${isLoginLanguageRTL && 'mr-10'}`}>
                    <h6> {t('partnerNotificationsTab.apikey')}</h6>
                </button>
                <div className={`h-1 w-full ${isLoginLanguageRTL && 'mr-6'} ${activeTab === "apikey" ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
            </div>
            )}
            {partnerType === "DEVICE_PROVIDER" && (
            <div id='sbi_notifications_tab' className={`flex-col justify-center text-center`}>
                <button onClick={changeToSbi} className={`${activeTab === "sbi" ? "text-[#1447b2]" : "text-[#031640]"} py-4 cursor-pointer text-base ${isLoginLanguageRTL && 'mr-10'}`}>
                    <h6> {t('partnerNotificationsTab.sbi')}</h6>
                </button>
                <div className={`h-1 w-full ${isLoginLanguageRTL && 'mr-6'} ${activeTab === "sbi" ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
            </div>
            )}
        </div>
    );
}

export default PartnerNotificationsTab;