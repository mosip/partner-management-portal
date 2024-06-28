import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { logout, moveToHome } from '../../utils/AppUtils.js';
import somethingWentWrongIcon from '../../svg/something_went_wrong_icon.svg';
import { getUserProfile } from '../../services/UserProfileService.js';
import { isLangRTL } from '../../utils/AppUtils.js';

function RuntimeError() {
    const { state } = useLocation();
    const { messageType, errorCode, errorText } = state || { messageType: 'somethingWentWrong', errorCode: null, errorText: null };
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
            description: t('commons.somethingWentWrongDesc')
        }
    };

    const message = messages[messageType] || messages.somethingWentWrong;

    return (
        <div className={`w-full mb-[2%] ${isLoginLanguageRTL ? "mr-20 ml-2" : "ml-20 mr-2"} overflow-x-scroll relative flex items-center`}>
            <div className="flex flex-col items-center justify-center bg-white w-2/6 min-h-fit min-w-fit mx-auto rounded-xl shadow-lg p-4">
                <img src={somethingWentWrongIcon} alt="" className="max-w-32 min-w-24" />
                {errorCode && errorText && (
                    <div className="flex items-center justify-center">
                        <p className="text-xl font-semibold">{errorText}</p>
                        <p className="text-xl font-semibold mx-1">{errorCode}</p>
                    </div>
                )}
                <p className="text-base font-semibold">{message.title}</p>
                <p className="text-sm text-vulcan font-semibold mt-1">{message.description}</p>
                <div className="p-1 flex flex-wrap justify-center relative items-center mt-1">
                    <button
                        className="w-24 min-w-fit h-9 mx-2 my-1 p-2 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold"
                        onClick={() => moveToHome(navigate)}
                    >
                        {t('commons.goBack')}
                    </button>
                    <button
                        className="w-24 min-w-fit h-9 mx-2 my-1 p-2 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold"
                        onClick={logout}
                    >
                        {t('commons.logout')}
                    </button>
                </div>
            </div>
        </div>
    );
}

export default RuntimeError;
