import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { logout, moveToHome } from '../../utils/AppUtils.js';
import somethingWentWrongIcon from '../../svg/something_went_wrong_icon.svg';
import { getUserProfile } from '../../services/UserProfileService.js';
import { isLangRTL } from '../../utils/AppUtils.js';

function RuntimeError() {
    const { state } = useLocation();
    const { messageType, errorCode } = state || { messageType: 'somethingWentWrong', errorCode: null };
    const { errorText } = state || { errorText: null };
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const messages = {
        somethingWentWrong: {
            title: t('commons.somethingWentWrong'),
            description: t('commons.somethingWentWrongDesc')
        },
        noAccess: {
            title: t('commons.noAccess'),
            description: t('commons.noAccessDesc')
        },
        timeout: {
            title: t('commons.timeout'),
            description: t('commons.timeoutDesc')
        }
    };

    const message = messages[messageType] || messages.somethingWentWrong;

    return (
        <div className={`w-full bg-white my-6 ${isLoginLanguageRTL ? "mr-24 ml-5" : "ml-24 mr-5"} overflow-x-scroll relative flex items-center justify-center`}>
            <div className="flex flex-col items-center justify-center p-4 ">
                <img src={somethingWentWrongIcon} alt="" className="max-w-32 min-w-24" />
                {(errorCode || errorText) && (
                    <div className="flex items-center justify-center text-base">
                        {errorCode && <p className="font-semibold mx-1">{errorCode}</p>}
                        {errorText && <p className="font-semibold">{errorText}</p>}
                    </div>
                )}
                <p className="text-xl font-semibold">{message.title}</p>
                <p className="text-base text-vulcan font-semibold">{message.description}</p>
                <div className="p-1 flex flex-wrap justify-center relative items-center my-1">
                    <button
                        className="w-24 min-w-fit h-9 mx-2 my-1 p-2 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold"
                        onClick={logout}
                    >
                        {t('commons.logout')}
                    </button>
                    <button
                        className="w-24 min-w-fit h-9 mx-2 my-1 p-2 border-[#1447B2] border rounded-md text-white text-sm font-semibold
                               bg-tory-blue cursor-pointer"
                        onClick={() => moveToHome(navigate)}
                    >
                        {t('commons.goBack')}
                    </button>
                </div>
            </div>
        </div>
    );
}

export default RuntimeError;
