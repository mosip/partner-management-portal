import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, moveToHome, getStatusCode, bgOfStatus, onPressEnterKey } from '../../utils/AppUtils';
import backArrow from '../../svg/back_arrow.svg';
import backArrowReversed from '../../svg/back_arrow_reversed.svg';
import PropTypes from 'prop-types';

function Title({ title, subTitle, subTitle2, backLink, backLink2, status, version }) {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

    const goBack = () => {
        if (backLink2) {
            navigate(backLink2);
        } else {
            navigate(backLink);
        }
    };

    return (
        <div className="flex-col items-start">
            <div className={`flex gap-x-2 ${isLoginLanguageRTL ? 'pr-[0.5rem]' : 'pl-[0.5rem]'}`}>
                <button type="button" tabIndex={0} id='title_back_icon' onClick={goBack} onKeyDown={(e) => onPressEnterKey(e, goBack)} className={`mt-1 cursor-pointer rounded`} >
                    <img src={isLoginLanguageRTL ? backArrowReversed : backArrow} alt="" />
                </button>
                <h1 id='page_title' className="font-semibold text-lg text-dark-blue break-words min-w-0">{t(title)}</h1>
            </div>

            <div className={`flex-col min-w-0 ${!isLoginLanguageRTL ? 'ml-7' : 'mr-7'}`}>
                {(status && version) && (
                    <div className="flex flex-wrap items-center gap-2 mb-1 min-w-0">
                        <h1 id='sub_title_version' className={`font-semibold text-lg text-[#707070] break-all min-w-0`}>{version + ' | '}</h1>
                        <div id='sub_title_status' className={`${(status === 'deactivated') ? 'bg-[#A5A5A5] text-white' : bgOfStatus(status)} flex h-fit py-1.5 px-2 text-xs font-semibold rounded-md flex-shrink-0`}>
                            {getStatusCode(status, t)}
                        </div>
                    </div>
                )}
                <div className="flex space-x-1">
                    <button type="button" tabIndex={0} id='sub_title_home_btn' onClick={() => moveToHome(navigate)} onKeyDown={(e) => onPressEnterKey(e, () => moveToHome(navigate))} className="font-semibold text-tory-blue text-xs cursor-pointer rounded px-2">
                        <p> {t('commons.home')} </p>
                    </button>
                    {subTitle && (
                        <button type="button" tabIndex={0} id='sub_title_btn' onClick={() => navigate(backLink)} onKeyDown={(e) => onPressEnterKey(e, () => navigate(backLink))} className="font-semibold text-tory-blue text-xs cursor-pointer rounded px-2">
                            <p> / {t(subTitle)}  </p>
                        </button>
                    )}
                    {subTitle2 && (
                        <button type="button" tabIndex={0} id='sub_title_two_btn' onClick={() => navigate(backLink2)} onKeyDown={(e) => onPressEnterKey(e, () => navigate(backLink2))} className="font-semibold text-tory-blue text-xs cursor-pointer rounded px-2">
                            <p> / {t(subTitle2)}</p>
                        </button>
                    )}
                </div>
            </div>
        </div>
    )
}

Title.propTypes = {
    title: PropTypes.string,
    subTitle: PropTypes.string,
    subTitle2: PropTypes.string,
    backLink: PropTypes.string,
    backLink2: PropTypes.string,
    status: PropTypes.string,
    version: PropTypes.string,
};

export default Title;