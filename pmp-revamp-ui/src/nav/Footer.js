import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../services/UserProfileService';
import { isLangRTL } from '../utils/AppUtils';

function Footer() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    return (
        <div className={`bg-anti-flash-white items-center ${isLoginLanguageRTL ? "mr-[8%]" : "ml-[8%]"}`}>
            <hr className="h-px bg-gray-200 border-0 " />
            <div className="flex py-[3%] justify-between text-xs text-gray-400">
                <div>
                    <p>2024 © MOSIP - {t('footer.allRightsReserved')}</p>
                </div>
                <div className="flex justify-between">
                    <a id='footer_documentation_link' href="https://docs.mosip.io/1.2.0/modules/partner-management-services" target="_blank" rel="noopener noreferrer" className={`${isLoginLanguageRTL ? "ml-7" : "mr-7"} cursor-pointer`}>{t('footer.documentation')}</a>
                    <a id='footer_contact_us_link' href="https://community.mosip.io/c/platform/5" target="_blank" rel="noopener noreferrer" className={`${isLoginLanguageRTL ? "ml-7" : "mr-7"} cursor-pointer`}>{t('footer.contactUs')}</a>
                </div>
            </div>
        </div>
    );
}

export default Footer;