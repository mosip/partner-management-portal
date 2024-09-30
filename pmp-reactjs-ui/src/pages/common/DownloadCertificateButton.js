import React from 'react';
import { useTranslation } from 'react-i18next';
import { isLangRTL } from '../../utils/AppUtils';
import { getUserProfile } from '../../services/UserProfileService';

import dropdown_up_icon from '../../svg/download_dropdown_icon.svg';
import dropdown_down_icon from '../../svg/dropdown_up_btn.svg';
import disabled_download_icon from '../../svg/disabled_download_icon.svg';
import downloadIcon from '../../svg/download_icon.svg';

function DownloadCertificateButton({ setShowDropDown, showDropDown, onClickFirstOption, onClickSecondOption, requiredData, downloadDropdownRef, disableBtn, styleSet, id }) {
    
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    return (
        <div ref={downloadDropdownRef} className={`flex-col`}>
            <button  id={id} disabled={disableBtn} onClick={() => setShowDropDown()}
                className={`flex items-center w-28 h-10 ${isLoginLanguageRTL ? "ml-5" : "mr-5"} ${disableBtn ? 'text-[#6f7070] border-gray-300 bg-white' : showDropDown ? 'bg-blue-800 text-white border-blue-800' : 'text-tory-blue bg-white border-blue-800'} text-xs px-[1rem] py-[1%] ${isLoginLanguageRTL ? "ml-1" : "mr-1"} border font-semibold rounded-lg text-center`}>
                {t('commons.download')}
                {!disableBtn &&
                    (showDropDown ?
                        <img src={dropdown_down_icon} className={`rotate-180 duration-500 text-white ${isLoginLanguageRTL ? "mr-2" : "ml-2"}`} alt={''} />
                        : <img src={dropdown_up_icon} className={`duration-500 text-white ${isLoginLanguageRTL ? "mr-2" : "ml-2"}`} alt={''} />
                    )
                }
            </button>

            {showDropDown && (
                <div className={styleSet && styleSet.outerDiv}>
                    <div id={'original_certificate_download_' + id} onClick={() => onClickFirstOption(requiredData)} className="flex items-center border-b justify-between cursor-pointer hover:bg-gray-100">
                        <button className="block px-4 py-2 text-xs font-semibold text-dark-blue">{t('commons.originalCertificate')}</button>
                        <img src={downloadIcon} alt="" className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"}`} />
                    </div>
                    <div id={'mosip_signed_certificate_download_' + id} onClick={() => onClickSecondOption(requiredData)} className={`flex items-center justify-between ${requiredData.disableSecondOption ? 'hover:bg-none':'hover:bg-gray-100 cursor-pointer'}`}>
                        <button disabled={requiredData.disableSecondOption} className={`block px-4 py-2 text-xs font-semibold ${requiredData.disableSecondOption ? 'text-[#828385e0]':'text-dark-blue'}`}>{t('commons.mosipSignedCertificate')}</button>
                        <img src={requiredData.disableSecondOption ? disabled_download_icon : downloadIcon} alt="" className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"}`} />
                    </div>
                </div>)}
        </div>

    )
}

export default DownloadCertificateButton;