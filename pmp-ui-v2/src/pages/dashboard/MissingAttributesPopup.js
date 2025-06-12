import { useEffect } from 'react';
import { useTranslation, Trans } from 'react-i18next';
import { logout, isLangRTL } from '../../utils/AppUtils.js';
import FocusTrap from 'focus-trap-react';
import { getUserProfile } from '../../services/UserProfileService.js';

function MissingAttributesPopup() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

    useEffect(() => {
        document.body.style.overflow = "hidden";
        return () => {
            document.body.style.overflow = "auto";
        };
    }, []);

    return (
        <div className={`fixed inset-0 w-full flex items-center justify-center bg-black bg-opacity-35 z-50 font-inter`}>
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className={`bg-white w-2/5 mx-auto rounded-xl shadow-lg -mt-3 ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                    <div className="p-4 border-b border-gray-300">
                        <h3 className="text-lg font-bold text-[#333333]">
                            {t('missingAttributesPopup.title')}
                        </h3>
                    </div>

                    <div className="p-4 text-base text-[#414141]">
                        <div className="bg-[#FCFCFC] w-full items-center mb-2">
                            <div className="flex items-center justify-center">
                                <div className="p-2 bg-[#FFF7E5] border-2 border-[#EDDCAF] rounded-md w-full">
                                    <p className="text-sm font-medium text-[#8B6105]">
                                        <Trans
                                            i18nKey="missingAttributesPopup.description"
                                            components={{ br: <br /> }}
                                        />
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div className='text-base'>
                            <Trans
                                i18nKey="missingAttributesPopup.instructionsTitle"
                                components={{ strong: <strong /> }}
                            />
                        </div>
                        <div>
                            <ul className={`list-disc mt-2 text-sm space-y-1 ${isLoginLanguageRTL ? 'mr-5' : 'ml-5'}`}>
                                {t('missingAttributesPopup.instructionsList', { returnObjects: true }).map((item, index) => (
                                    <li key={index} className="px-1">
                                        <Trans
                                            i18nKey={`missingAttributesPopup.instructionsList.${index}`}
                                            components={{ strong: <strong /> }}
                                        />
                                    </li>
                                ))}
                            </ul>
                        </div>
                    </div>
                    <div className="p-4 flex justify-end items-center">
                        <p className="text-[#333333] text-sm font-semibold">
                            <button
                                className="flex justify-center w-fit h-10 py-2 px-3 rounded-md bg-[#1447B2] text-white text-sm font-semibold"
                                onClick={logout}
                            >
                                {t('missingAttributesPopup.logout')}
                            </button>
                        </p>
                    </div>
                </div>
            </FocusTrap>
        </div>
    );
}

export default MissingAttributesPopup;
