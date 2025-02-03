import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, onPressEnterKey } from '../../utils/AppUtils';
import close_icon from '../../svg/close_icon.svg';
import FocusTrap from 'focus-trap-react';

function CopyIdPopUp({ closePopUp, policyName, partnerId, id, header, alertMsg, styleSet }) {
    const [copied, setCopied] = useState(false);
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const copyId = () => {
        navigator.clipboard.writeText(id).then(() => {
            setCopied(true);
            setTimeout(() => setCopied(false), 3000);
        }).catch(err => {
            console.error('Failed to copy text: ', err);
        });
    };
    const dismiss = () => {
        closePopUp(false);
    }

    useEffect(() => {
        document.body.style.overflow = 'hidden';
        return () => {
            document.body.style.overflow = 'auto';
        };
    }, []);

    return (
        <div className={`fixed inset-0 flex items-center justify-center bg-black ${(styleSet && styleSet.outerDiv) ? styleSet.outerDiv : ''} z-50 font-inter cursor-default !ml-0`}>
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className={`bg-white md:w-[378px] w-[40%] mx-auto rounded-lg shadow-lg h-fit`}>
                    <header className={`flex justify-between p-[5%]`}>
                        <div className={`flex-col w-[19rem]`}>
                            <h1 className={`font-bold text-base text-[#333333] break-words`}>{policyName}</h1>
                            <p className={`text-xs font-bold text-[#717171] ${isLoginLanguageRTL ? "text-right" : "text-left"}`}># {partnerId}</p>
                        </div>
                        <button id='copy_id_close_btn' onClick={dismiss} className={`flex items-start min-w-fit cursor-pointer ${isLoginLanguageRTL ? "mr-2 " : "ml-2"}`}>
                            <img
                                src={close_icon}
                                alt="Close"
                                className='h-7 w-7 block'
                            />
                        </button>
                    </header>
                    <hr className={`py-[3.5%]`} />
                    <div className={`px-5 pt-1 pb-6 flex-col text-center justify-center items-center`}>
                        {alertMsg && (
                            <div className="w-full border border-[#EDDCAF] bg-[#FFF7E5] rounded-md p-2 text-left mb-2">
                                <p className="test-[13px] font-medium text-[#8B6105]">{t(alertMsg)}</p>
                            </div>
                        )}
                        <h1 className={`text-[#6A6A6A] text-sm opacity-8 mb-[0.5%]`}>{t(header)}</h1>
                        <p className={`font-bold text-sm text-black break-words px-6`}>{id}</p>
                        <button id='copy_id_btn' type="button" onClick={() => copyId()} className={`flex items-center justify-center gap-x-2 my-[4%] border-2 py-[3%] w-[40%] rounded-2xl ${copied ? "text-[#FFFFFF] bg-[#1447B2] border-0" : "text-[#1447B2] border-[#1447B2]"} ${isLoginLanguageRTL ? "mr-[31%]" : "ml-[31%]"} cursor-pointer`}>
                            <svg xmlns="http://www.w3.org/2000/svg"
                                width="13" height="15" viewBox="0 0 13.808 16.481">
                                <path id="content_copy_FILL0_wght300_GRAD0_opsz24"
                                    d="M154.728-846.637a1.555,1.555,0,0,1-1.143-.468,1.555,1.555,0,0,1-.468-1.143V-858.39a1.554,1.554,0,0,1,.468-1.143,1.554,1.554,0,0,1,1.143-.468H162.2a1.554,1.554,0,0,1,1.143.468,1.555,1.555,0,0,1,.468,1.143v10.142a1.555,1.555,0,0,1-.468,1.143,1.555,1.555,0,0,1-1.143.468Zm0-1.336H162.2a.261.261,0,0,0,.188-.086.261.261,0,0,0,.086-.188V-858.39a.261.261,0,0,0-.086-.188.261.261,0,0,0-.188-.086h-7.469a.261.261,0,0,0-.188.086.261.261,0,0,0-.086.188v10.142a.261.261,0,0,0,.086.188A.261.261,0,0,0,154.728-847.974Zm-3.118,4.454a1.555,1.555,0,0,1-1.143-.468A1.555,1.555,0,0,1,150-845.13v-11.478h1.336v11.478a.261.261,0,0,0,.086.188.261.261,0,0,0,.188.086h8.806v1.336Zm2.844-4.454v0Z"
                                    transform="translate(-150 860)" fill={`${copied ? "#FFFFFF" : "#1447b2"}`} />
                            </svg>

                            {!copied ? t("oidcClientsList.copy") : t("oidcClientsList.copied")}
                        </button>
                    </div>
                </div>
            </FocusTrap>
        </div>
    )
}

export default CopyIdPopUp;