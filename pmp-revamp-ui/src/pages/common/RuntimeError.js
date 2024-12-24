import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { logout, moveToHome } from '../../utils/AppUtils.js';
import somethingWentWrongIcon from '../../svg/something_went_wrong_icon.svg';

function RuntimeError() {
    const { t } = useTranslation();
    const { state } = useLocation();
    const { messageType, errorCode } = state || { messageType: 'somethingWentWrong', errorCode: null };
    const { errorText } = state || { errorText: null };
    const navigate = useNavigate();

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
        <div className="w-full h-screen bg-white flex items-center justify-center">
            <div className="flex flex-col items-center justify-center p-4 ">
                <img src={somethingWentWrongIcon} alt="" className="max-w-60 min-w-52 my-2" />
                {(errorCode || errorText) && (
                    <div className="flex items-center justify-center text-base mb-3">
                        {errorCode && <p className="font-semibold mx-1">{errorCode}</p>}
                        {errorText && <p className="font-semibold">{errorText}</p>}
                    </div>
                )}
                <p className="text-xl font-semibold">{message.title}</p>
                <p className="text-base text-vulcan font-semibold">{message.description}</p>
                <div className="p-1 flex flex-wrap justify-center relative items-center my-1">
                    <button
                     id='something_went_wrong_home_btn' className="w-24 min-w-fit h-9 mx-2 my-1 p-2 border-[#1447B2] border rounded-md text-white text-sm font-semibold
                               bg-tory-blue cursor-pointer"
                        onClick={() => moveToHome(navigate)}
                    >
                        {t('commons.home')}
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
