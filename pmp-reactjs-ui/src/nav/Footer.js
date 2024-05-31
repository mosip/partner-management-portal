import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../services/UserProfileService';
import { RTLStyles } from '../utils/AppUtils';

function Footer() {
    const { t } = useTranslation();
    const arabicLang = RTLStyles(getUserProfile().langCode);
    return (
        <div className={`bg-anti-flash-white items-center ${arabicLang ? "mr-[8%]" : "ml-[8%]"}`}>
            <hr className="h-px bg-gray-200 border-0 " />
            <div className="flex py-[3%] justify-between text-sm text-gray-400">
                <div>
                    <p>2024 © MOSIP - {t('footer.allRightsReserved')}</p>
                </div>
                <div className="flex justify-between">
                    <p className={`${arabicLang ? "ml-7" : "mr-7"}`}>{t('footer.documentation')}</p>
                    <p className={`${arabicLang ? "ml-7" : "mr-7"}`}>{t('footer.mosipCommunity')}</p>
                    <p className={`${arabicLang ? "ml-7" : "mr-7"}`}>{t('footer.contactUs')}</p>
                </div>
            </div>
        </div>
    );
}

export default Footer;