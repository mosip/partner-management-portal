import React, { useState } from 'react'
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, moveToHome } from '../../utils/AppUtils';
import successIcon from '../../svg/success_message_icon.svg';

function Confirmation({ confirmationData, onClickFunction }) {

    const navigate = useNavigate();
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [style, setStyle] = useState(confirmationData.styleSet);

    return (
        <div className="flex items-center justify-center w-[100%] h-[480px] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
            <div className="flex flex-col justify-center items-center w-[50%]">
                <img id="confirmation_success_icon" src={successIcon} alt="" className={`${isLoginLanguageRTL ? (style && style.imgIconRtl ? style.imgIconRtl : "") : (style && style.imgIconLtr ? style.imgIconLtr : "")} h-40`} />
                <div className={`text-center space-y-2`}>
                    <h1 className="font-bold text-black text-lg max-[450px]:text-sm">
                        {t(confirmationData.header)}
                    </h1>
                    <p className="text-[#666666] text-sm font-semibold max-[450px]:text-xs">
                    {confirmationData.descriptionParams ? t(confirmationData.description, confirmationData.descriptionParams) : t(confirmationData.description)}
                        {confirmationData.description1 && (
                            <span className="text-[#666666] text-sm font-semibold max-[450px]:text-xs">{t(confirmationData.description1)}</span>
                        )}
                    </p>
                    {!confirmationData.customBtnName &&
                        <div className={`flex gap-x-3 p-2 max-[450px]:flex-col max-[450px]:gap-x-0 justify-center`}>
                            <button id='confirmation_go_back_btn' onClick={() => navigate(confirmationData.backUrl)} type="button" className="text-white font-semibold bg-tory-blue rounded-md text-sm px-12 py-4 max-[450px]:text-xs max-[450px]:mx-6 max-[450px]:mb-2">
                                {t('commons.goBack')}
                            </button>
                            <button id='confirmation_home_btn' onClick={() => moveToHome(navigate)} type="button" className="text-[#1447b2] font-semibold bg-white border border-[#1447b2] rounded-md text-sm px-12 py-4 max-[450px]:text-xs max-[450px]:mx-6">
                                {t('commons.home')}
                            </button>
                        </div>
                    }
                    {confirmationData.customBtnName &&
                        <div>
                            <button id='confirmation_custom_btn' onClick={onClickFunction} type="button" className="text-white font-semibold bg-tory-blue rounded-md text-sm px-3 py-4 max-[450px]:text-xs max-[450px]:mx-6 max-[450px]:mb-2">
                                {t(confirmationData.customBtnName)}
                            </button>
                        </div>
                    }
                </div>
            </div>
        </div>
    )
}

export default Confirmation;