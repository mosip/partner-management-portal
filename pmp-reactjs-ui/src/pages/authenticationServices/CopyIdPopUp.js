import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL } from '../../utils/AppUtils';
import close_icon from '../../svg/close_icon.svg';

function CopyIdPopUp({ closePopUp, policyName, partnerId, oidcClientId }) {
    const [copied, setCopied] = useState(false);
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const copyId = () => {
        navigator.clipboard.writeText(oidcClientId).then(() => {
            setCopied(true);
            setTimeout(() => setCopied(false), 3000);
        }).catch(err => {
            console.error('Failed to copy text: ', err);
        });
    };
    const dismiss = () => {
        policyName = '';
        partnerId = '';
        closePopUp(false);
    }

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-[16%] z-50 font-inter cursor-default">
            <div className={`bg-white w-[20%] mx-auto rounded-lg shadow-lg h-[35%]`}>
                <header className={`flex justify-between p-[5%]`}>
                    <div className={`flex-col`}>
                        <h1 className={`font-bold text-md text-[#333333]`}>{policyName}</h1>
                        <p className={`text-xs font-bold text-[#717171]`}># {partnerId}</p>
                    </div>
                    <img src={close_icon} className={`h-7 cursor-pointer`} onClick={() => dismiss()} />
                </header>
                <hr className={`py-[4%]`} />
                <div className={` flex-col text-center items-center`}>
                    <h1 className={`text-[#6A6A6A] font-bold opacity-8 mb-[0.5%]`}>{t('oidcClientsList.oidcClientId')}</h1>
                    <p className={`font-bold text-xs break-words px-2`}>{oidcClientId}</p>
                    <button type="button" onClick={() => copyId()} className={`flex items-center justify-center gap-x-2 mt-[4%] border-2 py-[3%] w-[40%] rounded-2xl ${copied ? "text-[#FFFFFF] bg-[#1447B2] border-0" : "text-[#1447B2] border-[#1447B2]"} ${isLoginLanguageRTL ? "mr-20" : "ml-20"} cursor-pointer`}>
                        <svg xmlns="http://www.w3.org/2000/svg"
                            width="13.808" height="16.481" viewBox="0 0 13.808 16.481">
                            <path id="content_copy_FILL0_wght300_GRAD0_opsz24"
                                d="M154.728-846.637a1.555,1.555,0,0,1-1.143-.468,1.555,1.555,0,0,1-.468-1.143V-858.39a1.554,1.554,0,0,1,.468-1.143,1.554,1.554,0,0,1,1.143-.468H162.2a1.554,1.554,0,0,1,1.143.468,1.555,1.555,0,0,1,.468,1.143v10.142a1.555,1.555,0,0,1-.468,1.143,1.555,1.555,0,0,1-1.143.468Zm0-1.336H162.2a.261.261,0,0,0,.188-.086.261.261,0,0,0,.086-.188V-858.39a.261.261,0,0,0-.086-.188.261.261,0,0,0-.188-.086h-7.469a.261.261,0,0,0-.188.086.261.261,0,0,0-.086.188v10.142a.261.261,0,0,0,.086.188A.261.261,0,0,0,154.728-847.974Zm-3.118,4.454a1.555,1.555,0,0,1-1.143-.468A1.555,1.555,0,0,1,150-845.13v-11.478h1.336v11.478a.261.261,0,0,0,.086.188.261.261,0,0,0,.188.086h8.806v1.336Zm2.844-4.454v0Z"
                                transform="translate(-150 860)" fill={`${copied ? "#FFFFFF" : "#1447b2"}`} />
                        </svg>

                        {!copied ? "Copy" : "Copied!"}
                    </button>
                </div>
            </div>
        </div>
    )
}

export default CopyIdPopUp;