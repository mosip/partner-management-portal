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

                    <div className="p-4 text-sm text-[#414141]">
                        <div className="bg-gray-50 p-4 mb-2 rounded-lg border-l-2 border-amber-400">
                            <div className="text-sm text-gray-700 leading-relaxed">
                                <Trans
                                    i18nKey="missingAttributesPopup.description"
                                    components={{ br: <br /> }}
                                />
                            </div>
                        </div>
                        <Trans
                            i18nKey="missingAttributesPopup.instructionsTitle"
                            components={{ strong: <strong /> }}
                        />
                        <ul className="list-disc list-inside mt-2">
                            {t('missingAttributesPopup.instructionsList', { returnObjects: true }).map((item, index) => (
                                <li key={index}>{item}</li>
                            ))}
                        </ul>
                    </div>

                    <div className="p-4 flex justify-between items-center border-t border-gray-200">
                        <p className="text-[#333333] text-sm font-semibold">
                            <button
                                className="w-24 flex min-w-fit h-9 py-2 px-3 border rounded-md bg-[#1447B2] text-white text-sm font-semibold"
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
