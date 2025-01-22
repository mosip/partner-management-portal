import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, moveToHome, getStatusCode, bgOfStatus } from '../../utils/AppUtils';
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
            <div className={`flex gap-x-2 ${isLoginLanguageRTL ? 'pr-[0.1rem]' : 'pl-[0.1rem]'}`}>
                <button id='title_back_icon' onClick={goBack} className={`mt-1 cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} >
                    <img src={backArrow} alt="" />
                </button>
                <h1 className="font-semibold text-lg text-dark-blue">{t(title)}</h1>
            </div>

            <div className={`flex-col w-full ${!isLoginLanguageRTL ? 'ml-7' : 'mr-7'}`}>
                {(status && version) && (
                    <div className="flex flex-wrap max-w-[63rem] mb-1">
                        <h1 className={`font-semibold text-lg text-[#707070] break-words max-w-[63rem]`}>{version + ' | '}</h1>
                        <div className={`${(status === 'deactivated') ? 'bg-[#A5A5A5] text-white' : bgOfStatus(status)} flex h-fit py-1.5 px-2 ${isLoginLanguageRTL ? "ml-1" : "mr-1 ml-2"} text-xs font-semibold rounded-md`}>
                            {getStatusCode(status, t)}
                        </div>
                    </div>
                )}
                <div className="flex space-x-1">
                    <button id='sub_title_home_btn' onClick={() => moveToHome(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                        <p> {t('commons.home')} </p>
                    </button>
                    {subTitle && (
                        <button id='sub_title_btn' onClick={() => navigate(backLink)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                            <p> / {t(subTitle)}  </p>
                        </button>
                    )}
                    {subTitle2 && (
                        <button id='sub_title_two_btn' onClick={() => navigate(backLink2)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                            <p> / {t(subTitle2)}</p>
                        </button>
                    )}
                </div>
            </div>
        </div>
    )
}

export default Title;