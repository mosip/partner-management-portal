import React, { useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { handleMouseClickForDropdown, isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import infoIcon from '../../../svg/info_icon.svg';

function Information({ infoKey }) {

    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [showTooltip, setShowTooltip] = useState(false);
    const tooltipRef = useRef(null);

    useEffect(() => {
        handleMouseClickForDropdown(tooltipRef, () => setShowTooltip(false));
    },[tooltipRef])
    return (
        <div>
            <div className="absolute flex items-center">
                <img src={infoIcon} onClick={() => setShowTooltip(!showTooltip)} alt="" className={`cursor-pointer h-[13px] w-[13px] ml-1 -mt-1.5`} />
                {showTooltip && (
                    <div ref={tooltipRef} className={`absolute z-20 p-4 w-[20vw] max-w-[300px] max-h-[20vh] overflow-y-auto max-[800px]:h-32 max-[800px]:w-32 shadow-lg bg-white border border-gray-300 rounded ${isLoginLanguageRTL ? 'right-6' : 'left-6'} mt-2`}>
                        <p className="text-black text-sm">{t(infoKey)}</p>
                    </div>
                )}
            </div>
        </div>
    )
}

export default Information;