import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";

function PartnerNotificationsTab({ activeTab }) {
    const { t } = useTranslation();
    const navigate = useNavigate();

    const changeToPartnerCert = () => {
        navigate('/partnermanagement/notifications/view-partner-certificate-expiry');
    };

    return (
        <div className='flex text-xs bg-[#FCFCFC] font-bold space-x-16 items-start rounded-lg px-8'>
            <div id='partner_cert_notifications_tab' className={`flex-col justify-center text-center`}>
                <button onClick={changeToPartnerCert} className={`${activeTab === "partner" ? "text-[#1447b2]" : "text-[#031640]"} py-4 cursor-pointer text-base`}>
                    <h6> {t('partnerNotificationsTab.partnerCertificate')} </h6>
                </button>

                <div className={`h-1 w-full ${activeTab === "partner" ? "bg-tory-blue" : "bg-transparent"}  rounded-t-md`}></div>
            </div>
        </div>
    );
}

export default PartnerNotificationsTab;