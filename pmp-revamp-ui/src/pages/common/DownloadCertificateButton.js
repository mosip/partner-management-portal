import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { isLangRTL, isCaSignedPartnerCertificateAvailable } from '../../utils/AppUtils';
import { getUserProfile } from '../../services/UserProfileService';

import dropdown_up_icon from '../../svg/download_dropdown_icon.svg';
import dropdown_down_icon from '../../svg/dropdown_up_btn.svg';
import disabled_download_icon from '../../svg/disabled_download_icon.svg';
import downloadIcon from '../../svg/download_icon.svg';

function DownloadCertificateButton({ setShowDropDown, showDropDown, onClickFirstOption, onClickSecondOption, requiredData, downloadDropdownRef, disableBtn, disabledBtnHoverMsg, styleSet, id }) {

    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [disableOriginalCert, setDisableOriginalCert] = useState(false);

    useEffect(() => {
        const checkCompatible = async () => {
            const isApiExist = await isCaSignedPartnerCertificateAvailable();
            if (!isApiExist) {
                setDisableOriginalCert(true);
            }
        };
        checkCompatible();
    }, []);

    return (
        <div ref={downloadDropdownRef} className={`flex-col`}>
            <div className='relative group' tabIndex="0">
                <button id={id} disabled={disableBtn} onClick={() => setShowDropDown()}
                    className={`flex items-center text-center w-fit h-10 ${disableBtn ? 'text-[#6f7070] border-gray-300 bg-white' : showDropDown ? 'bg-blue-800 text-white border-blue-800' : 'text-tory-blue bg-white border-blue-800'} text-xs px-[1.5rem] py-[1%] border font-semibold rounded-md`}>
                    {t('commons.download')}
                    {!disableBtn &&
                        (showDropDown ?
                            <img id='dropdown_down_icon' src={dropdown_down_icon} className={`rotate-180 duration-500 text-white ${isLoginLanguageRTL ? "mr-2" : "ml-2"}`} alt={''} />
                            : <img id='dropdown_up_icon' src={dropdown_up_icon} className={`duration-500 text-white ${isLoginLanguageRTL ? "mr-2" : "ml-2"}`} alt={''} />
                        )
                    }
                    {disableBtn && disabledBtnHoverMsg && (
                        <div className={`absolute hidden group-hover:block group-focus:block text-center bg-gray-100 text-xs text-gray-500 font-semibold p-2 w-60 mt-1 z-10 top-11 ${isLoginLanguageRTL ? "left-5" : "right-5"} rounded-md shadow-md`}>
                            {t(disabledBtnHoverMsg)}
                        </div>
                    )}
                </button>
            </div>

            {showDropDown && (
                <div className={styleSet && styleSet.outerDiv}>
                    <div className='relative group'>
                        <button disabled={disableOriginalCert} id={'original_certificate_' + id} onClick={() => onClickFirstOption(requiredData)} className={`flex items-center border-b w-full justify-between rounded-md ${disableOriginalCert ? 'bg-gray-500 bg-opacity-30' : 'cursor-pointer hover:bg-gray-100'}`}>
                            <p className={`block px-4 py-3 text-xs font-semibold ${disableOriginalCert ? 'text-gray-700' : 'text-dark-blue'}`}>{t('commons.originalCertificate')}</p>
                            <img src={disableOriginalCert ? disabled_download_icon : downloadIcon} alt="" className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"}`} />
                        </button>
                        {disableOriginalCert && (
                            <div className={`absolute hidden group-hover:block group-focus:block text-center bg-gray-100 text-xs text-gray-500 font-semibold p-2 w-60 mt-1 z-10 top-11 ${isLoginLanguageRTL ? "left-5" : "right-5"} rounded-md shadow-md`}>
                                {t('partnerCertificatesList.compatibilityMsg')}
                            </div>
                        )}
                    </div>
                    <button id={'mosip_signed_certificate_' + id} onClick={() => onClickSecondOption(requiredData)} className={`flex items-center border-b w-full justify-between ${requiredData.disableSecondOption ? 'hover:bg-none' : 'hover:bg-gray-100 cursor-pointer'}`}>
                        <p disabled={requiredData.disableSecondOption} className={`block px-4 py-3 text-xs font-semibold ${requiredData.disableSecondOption ? 'text-[#828385e0]' : 'text-dark-blue'}`}>{t('commons.mosipSignedCertificate')}</p>
                        <img src={requiredData.disableSecondOption ? disabled_download_icon : downloadIcon} alt="" className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"}`} />
                    </button>
                </div>)}
        </div>

    )
}

export default DownloadCertificateButton;