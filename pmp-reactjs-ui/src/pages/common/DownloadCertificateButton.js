import React from 'react';
import { useTranslation } from 'react-i18next';
import { isLangRTL } from '../../utils/AppUtils';
import { getUserProfile } from '../../services/UserProfileService';

import dropdown_up_icon from '../../svg/download_dropdown_icon.svg';
import dropdown_down_icon from '../../svg/dropdown_up_btn.svg';
import disable_dropdown_icon from '../../svg/disable_dropdown_btn.svg';
import downloadIcon from '../../svg/download_icon.svg';

function DownloadCertificateButton({ setShowDropDown, showDropDown, onClickFirstOption, onClickSecondOption, requiredData, downloadDropdownRef, disableBtn, styleSet }) {
    
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    return (
        <div ref={downloadDropdownRef} className={`flex-col`}>
            <button disabled={disableBtn} onClick={() => setShowDropDown()}
                className={`h-10 ${isLoginLanguageRTL ? "ml-5" : "mr-5"} ${disableBtn ? 'bg-slate-200 text-slate-500 border-gray-200 border' : ''} flex items-center ${showDropDown ? 'bg-blue-800 text-white' : 'text-tory-blue bg-white'} text-xs px-[1rem] py-[1%] ${isLoginLanguageRTL ? "ml-1" : "mr-1"} text-tory-blue border border-blue-800 font-semibold rounded-lg text-center`}>
                {t('commons.download')}
                {!disableBtn ?
                    (showDropDown ?
                        <img src={dropdown_down_icon} className={`rotate-180 duration-500 text-white ${isLoginLanguageRTL ? "mr-2" : "ml-2"}`} alt={''} />
                        : <img src={dropdown_up_icon} className={`duration-500 text-white ${isLoginLanguageRTL ? "mr-2" : "ml-2"}`} alt={''} />
                    ) :
                    <img src={disable_dropdown_icon} className={`${isLoginLanguageRTL ? "mr-2" : "ml-2"}`} alt={''} />
                }
            </button>

            {showDropDown && (
                <div className={styleSet && styleSet.outerDiv}>
                    <div onClick={() => onClickFirstOption(requiredData)} className="flex items-center border-b justify-between cursor-pointer hover:bg-gray-100">
                        <button className="block px-4 py-2 text-xs font-semibold text-dark-blue">{t('commons.originalCertificate')}</button>
                        <img src={downloadIcon} alt="" className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"}`} />
                    </div>
                    <div onClick={() => onClickSecondOption(requiredData)} className="flex items-center justify-between cursor-pointer hover:bg-gray-100">
                        <button className="block px-4 py-2 text-xs font-semibold text-dark-blue">{t('commons.mosipSignedCertificate')}</button>
                        <img src={downloadIcon} alt="" className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"}`} />
                    </div>
                </div>)}
        </div>

    )
}

export default DownloadCertificateButton;