import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, moveToHome } from '../../utils/AppUtils';
import backArrow from '../../svg/back_arrow.svg';

function Title({ title, subTitle, backLink, styleSet}) {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    return (
        <div className="flex items-start gap-x-2">
            <img src={backArrow} alt="" onClick={() => navigate(backLink)} className={`mt-[1%] ${styleSet && styleSet.backArrowIcon ? styleSet.backArrowIcon : ''} cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} />
            <div className="flex-col">
                <h1 className="font-semibold text-lg text-dark-blue">{t(title)}</h1>
                <div className="flex space-x-1">
                    <p onClick={() => moveToHome(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                        {t('commons.home')}
                    </p>
                    {subTitle && (
                        <p onClick={() => navigate(backLink)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                            / {t(subTitle)}
                        </p>
                    )}
                </div>
            </div>
        </div>
    )
}

export default Title;