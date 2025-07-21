import React, { useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { handleMouseClickForDropdown, isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import infoIcon from '../../../svg/info_icon.svg';
import PropTypes from 'prop-types';

function Information({ infoKey, infoKey1, id }) {

    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [showTooltip, setShowTooltip] = useState(false);
    const tooltipRef = useRef(null);

    useEffect(() => {
        handleMouseClickForDropdown(tooltipRef, () => setShowTooltip(false));
    }, [tooltipRef])
    return (
        <div>
            <div ref={tooltipRef} className="absolute mx-2 flex items-center">
                <div
                    id={id}
                    onClick={() => setShowTooltip(!showTooltip)}
                    className="cursor-pointer h-[13px] w-[13px] ml-1 -mt-1.5"
                    role="button"
                    tabIndex="0"
                    onKeyDown={(e) => e.key === 'Enter' && setShowTooltip(!showTooltip)}
                >
                    <img src={infoIcon} alt="info" />
                </div>
                {showTooltip && (
                    <div className={`absolute z-20 p-4 w-[20vw] max-w-[300px] max-h-[20vh] overflow-y-auto max-[800px]:h-32 max-[800px]:w-32 shadow-lg bg-white border border-gray-300 rounded ${isLoginLanguageRTL ? 'right-6' : 'left-6'} mt-2`}>
                        <p className="text-black text-sm">{t(infoKey)}</p>
                        {infoKey1 && (
                            <p className="text-black text-sm">{t(infoKey1)}</p>
                        )}
                    </div>
                )}
            </div>
        </div>
    )
}

Information.propTypes = {
    infoKey: PropTypes.string.isRequired,
    infoKey1: PropTypes.string,
    id: PropTypes.string.isRequired,
};

export default Information;