import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import infoIcon from '../../../svg/info_icon.svg';

function Information({ tooltipRef, infoKey }) {

    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [showTooltip, setShowTooltip] = useState(false);

    return (
        <div>
            <div className="relative flex items-center">
                <img src={infoIcon} ref={tooltipRef} onClick={() => setShowTooltip(!showTooltip)} alt="" className={`cursor-pointer h-[13px] w-[13px] ml-1`} />
                {showTooltip && (
                    <div className={`absolute z-20 p-4 w-[20vw] max-w-[300px] max-h-[20vh] overflow-y-auto max-[800px]:h-32 max-[800px]:w-32 shadow-lg bg-white border border-gray-300 rounded ${isLoginLanguageRTL ? 'right-10' : 'left-6'} mt-2`}>
                        <p className="text-black text-sm">{t(infoKey)}</p>
                    </div>
                )}
            </div>
        </div>
    )
}

export default Information;