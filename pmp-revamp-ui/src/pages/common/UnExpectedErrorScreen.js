import React from 'react';
import somethingWentWrongIcon from '../../svg/something_went_wrong_icon.svg';
import { getErrorMessage } from '../../utils/AppUtils';
import { useTranslation } from 'react-i18next';

function UnExpectedErrorScreen({ errCode, errMsg, backLink }) {
    const { t } = useTranslation();

    return (
        <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
            <div className="flex items-center justify-center p-24">
                <div className="flex flex-col justify-center items-center">
                    <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                    <p className="text-base font-semibold text-[#6F6E6E] py-4">{t('commons.unexpectedError')}</p>
                    {errCode && errMsg && (
                        <p className="text-sm font-semibold text-[#6F6E6E] pt-1 pb-4">{getErrorMessage(errCode, t, errMsg)}</p>
                    )}
                    <button onClick={backLink} type="button"
                        className={`w-32 h-10 flex items-center justify-center font-semibold rounded-md text-sm mx-8 py-3 bg-tory-blue text-white`}>
                        {t('commons.goBack')}
                    </button>
                </div>
            </div>
        </div>
    )
}

export default UnExpectedErrorScreen;