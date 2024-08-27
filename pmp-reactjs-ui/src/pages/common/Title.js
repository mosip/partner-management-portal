import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, moveToHome, onPressEnterKey, getStatusCode, bgOfStatus } from '../../utils/AppUtils';
import backArrow from '../../svg/back_arrow.svg';

function Title({ title, subTitle, subTitle2, backLink, backLink2, status, version }) {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const goBack = () => {
        if (backLink2) {
            navigate(backLink2);
        } else {
            navigate(backLink);
        }
    };

    return (
        <div className="flex-col items-start">
            <div className='flex gap-x-2'>
                <img src={backArrow} alt="" onClick={goBack} className={`mt-1 cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, goBack)} />
                <h1 className="font-semibold text-lg text-dark-blue">{t(title)}</h1>
            </div>

            <div className={`flex-col w-full ${status && version ? (!isLoginLanguageRTL ? 'ml-[3%]' : 'mr-[3%]') :(!isLoginLanguageRTL ? 'ml-7' : 'mr-7')}`}>
                {(status && version) && (
                    <div className="flex mb-1 gap-x-1">
                        <h1 className={`font-semibold text-lg text-[#707070]`}>{version} |</h1>
                        <div className={`${(status === 'deactivated') ? 'bg-[#A5A5A5] text-white' : bgOfStatus(status)} flex h-fit py-1.5 px-2 ${isLoginLanguageRTL ? "ml-1" : "mr-1 ml-2"} text-xs font-semibold rounded-md`}>
                            {getStatusCode(status, t)}
                        </div>
                    </div>
                )}
                <div className="flex space-x-1">
                    <p onClick={() => moveToHome(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => moveToHome(navigate))}>
                        {t('commons.home')}
                    </p>
                    {subTitle && (
                        <p onClick={() => navigate(backLink)} className="font-semibold text-tory-blue text-xs cursor-pointer" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => navigate(backLink))}>
                            / {t(subTitle)}
                        </p>
                    )}
                    {subTitle2 && (
                        <p onClick={() => navigate(backLink2)} className="font-semibold text-tory-blue text-xs cursor-pointer" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => navigate(backLink))}>
                            / {t(subTitle2)}
                        </p>
                    )}
                </div>
            </div>
        </div>
    )
}

export default Title;