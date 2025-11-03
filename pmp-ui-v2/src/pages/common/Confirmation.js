import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, moveToHome } from '../../utils/AppUtils';
import successIcon from '../../svg/success_message_icon.svg';
import PropTypes from 'prop-types';

function Confirmation({ id, confirmationData, onClickCustomBtn1, onClickCustomBtn2 }) {

    const navigate = useNavigate();
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const style = confirmationData.styleSet;

    return (
        <div className="flex items-center justify-center w-[100%] h-[480px] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
            <div className="flex flex-col justify-center items-center w-[50%] min-w-fit">
                <img id="confirmation_success_icon" src={successIcon} alt="" className={`${isLoginLanguageRTL ? (style && style.imgIconRtl ? style.imgIconRtl : "") : (style && style.imgIconLtr ? style.imgIconLtr : "")} h-40`} />
                <div className={`text-center space-y-2`}>
                    <h1 id={id + '_header'} className="font-bold text-black text-lg max-450:text-sm">
                        {t(confirmationData.header)}
                    </h1>
                    <p id={id + '_description'} className="text-[#666666] text-sm font-semibold max-450:text-xs">
                    {confirmationData.descriptionParams ? t(confirmationData.description, confirmationData.descriptionParams) : t(confirmationData.description)}
                        {confirmationData.description1 && (
                            <span className="text-[#666666] text-sm font-semibold max-450:text-xs">{t(confirmationData.description1)}</span>
                        )}
                    </p>
                    {!confirmationData.customBtnName1 && (
                        <div className="grid grid-cols-2 gap-4 ustify-center p-2 max-450:flex max-450:flex-col max-450:items-center">
                            <div className="flex justify-end">
                                <button id="confirmation_go_back_btn" onClick={() => navigate(confirmationData.backUrl)} type="button"
                                    className="text-white font-semibold bg-tory-blue rounded-md text-sm px-12 py-4 max-450:text-xs max-450:mx-6 max-450:mb-2">
                                    {t('commons.goBack')}
                                </button>
                            </div>
                            <div className="flex justify-start">
                                <button id="confirmation_home_btn" onClick={() => moveToHome(navigate)} type="button"
                                    className="text-[#1447b2] font-semibold bg-white border border-[#1447b2] rounded-md text-sm px-12 py-4 max-450:text-xs max-450:mx-6">
                                    {t('commons.home')}
                                </button>
                            </div>
                        </div>
                    )}
                    {confirmationData.customBtnName1 &&
                        <div className={confirmationData.customBtnName2 ? `flex justify-center items-center p-2 max-450:flex max-450:flex-col max-450:items-center` : ''}>
                            <div className={confirmationData.customBtnName2 ? `flex justify-end` : 'flex justify-center'}>
                                <button id='confirmation_custom_btn' onClick={onClickCustomBtn1} type="button" className={`text-white font-semibold bg-tory-blue rounded-md mt-1 text-sm ${confirmationData.customBtnName2 ? 'px-12' : 'px-3'} py-4 mx-2`}>
                                    {t(confirmationData.customBtnName1)}
                                </button>
                            </div>
                            {confirmationData.customBtnName2 && (
                                <div className="flex justify-start">
                                    <button id="confirmation_custom_btn_2" onClick={onClickCustomBtn2} type="button" className={`text-[#1447b2] mx-2 mt-1 font-semibold bg-white border border-[#1447b2] rounded-md text-sm px-12 py-4`}>
                                        {t(confirmationData.customBtnName2)}
                                    </button>
                                </div>
                            )}
                        </div>
                    }
                </div>
            </div>
        </div>
    )
}

Confirmation.propTypes = {
    id: PropTypes.string.isRequired,
    confirmationData: PropTypes.object.isRequired,
    onClickCustomBtn1: PropTypes.func,
    onClickCustomBtn2: PropTypes.func,
};

export default Confirmation;