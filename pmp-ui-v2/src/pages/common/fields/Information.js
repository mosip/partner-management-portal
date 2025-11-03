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
                    <div className={`absolute z-20 p-4 w-[20vw] max-w-[300px] max-h-[20vh] overflow-y-auto max-800:h-32 max-800:w-32 shadow-lg bg-white border border-gray-300 rounded ${isLoginLanguageRTL ? 'right-6' : 'left-6'} mt-2 
                        max-750:-left-full max-750:right-auto max-750:transform max-750:-translate-x-1/2 
                        max-750:top-3 max-750:mt-0 max-750:w-[80vw] max-750:max-w-none`}>
                        <p id={id + '_info_description'} className="text-black text-sm">{t(infoKey)}</p>
                        {infoKey1 && (
                            <p id={id + '_info_description1'} className="text-black text-sm">{t(infoKey1)}</p>
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