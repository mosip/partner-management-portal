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
    const [tooltipPosition, setTooltipPosition] = useState('center');
    const tooltipRef = useRef(null);
    const iconRef = useRef(null);

    useEffect(() => {
        handleMouseClickForDropdown(tooltipRef, () => setShowTooltip(false));
    }, [tooltipRef])

    useEffect(() => {
        if (showTooltip && iconRef.current) {
            const iconRect = iconRef.current.getBoundingClientRect();
            const viewportWidth = window.innerWidth;
            const tooltipWidth = 200;

            const spaceOnLeft = iconRect.left;
            const spaceOnRight = viewportWidth - iconRect.right;

            if (spaceOnLeft > 140 && spaceOnRight > 140) {
                setTooltipPosition('center');
            } else if (spaceOnRight > 260) {
                setTooltipPosition('left');
            } else if (spaceOnLeft > 260) {
                setTooltipPosition('right');
            } else {
                setTooltipPosition('center');
            }
        }
    }, [showTooltip]);



    return (
        <div className="relative inline-flex items-center">
            <div ref={tooltipRef} className="flex items-center">
                <div
                    ref={iconRef}
                    id={id}
                    onClick={(e) => { e.preventDefault(); e.stopPropagation(); setShowTooltip(!showTooltip); }}
                    className="cursor-pointer h-[13px] w-[13px] ml-1 flex-shrink-0"
                    role="button"
                    tabIndex="0"
                    onKeyDown={(e) => { if (e.key === 'Enter') { e.preventDefault(); e.stopPropagation(); setShowTooltip(!showTooltip); } }}
                >
                    <img src={infoIcon} alt="info" />
                </div>
                {showTooltip && (
                    <div
                        className={`
                        absolute z-50 p-4 font-normal
                        w-[min(200px,calc(100vw-4rem))] max-h-[30vh] overflow-y-auto overflow-x-hidden
                        shadow-lg bg-white border border-gray-300 rounded
                        ${tooltipPosition === 'left' ? 'left-0' : tooltipPosition === 'right' ? 'right-0' : 'left-1/2 -translate-x-1/2'} top-full mt-2
                        break-words
                        ${isLoginLanguageRTL ? 'text-right' : 'text-left'}
                        `}
                    >
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