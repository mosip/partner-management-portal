import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { isLangRTL } from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService";

function AdminNotificationsTab({ activeRootCA, rootCaPath, activeIntermediateCA, intermediateCaPath, activePartner, partnerCertPath }) {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const changeToRootCA = () => {
        navigate(rootCaPath)
    };

    const changeToIntermediateCA = () => {
        navigate(intermediateCaPath)
    };

    const changeToPartnerCert = () => {
        navigate(partnerCertPath)
    };

    return (
        <div className={`flex text-xs bg-[#FCFCFC] font-bold ${isLoginLanguageRTL && 'space-x-reverse'} gap-x-4 sm:gap-x-16 items-start rounded-lg px-8`}>
            <div id='root_trust_notifications_tab' className={`flex-col justify-center text-center w-full sm:w-auto`}>
                <button onClick={changeToRootCA} className={`${activeRootCA ? "text-[#1447b2]" : "text-[#031640]"} py-4 cursor-pointer text-base`}>
                    <h6> {t('notificationsTab.rootCaCertificate')} </h6>
                </button>

                <div className={`h-1 w-full ${activeRootCA ? "bg-tory-blue" : "bg-transparent"}  rounded-t-md`}></div>
            </div>
            <div id='intermediate_trust_notifications_tab' className={`flex-col justify-center text-center w-full sm:w-auto`}>
                <button onClick={changeToIntermediateCA} className={`${activeIntermediateCA ? "text-[#1447b2]" : "text-[#031640]"} py-4 cursor-pointer text-base`}>
                    <h6> {t('notificationsTab.intermediateCaCertificate')}</h6>
                </button>
                <div className={`h-1 w-full ${activeIntermediateCA ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
            </div>
            <div id='partner_notifications_tab' className={`flex-col justify-center text-center w-full sm:w-auto`}>
                <button onClick={changeToPartnerCert} className={`${activePartner ? "text-[#1447b2]" : "text-[#031640]"} py-4 cursor-pointer text-base`}>
                    <h6> {t('notificationsTab.partner')}</h6>
                </button>
                <div className={`h-1 w-full ${activePartner ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
            </div>
        </div>
    );
}
export default AdminNotificationsTab;