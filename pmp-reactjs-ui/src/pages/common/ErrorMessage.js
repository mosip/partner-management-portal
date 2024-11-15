import { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import cancelIcon from '../../svg/cancel_icon.svg';
import { isLangRTL } from "../../utils/AppUtils";
import { getUserProfile } from "../../services/UserProfileService";

function ErrorMessage({ errorCode, errorMessage, clickOnCancel, customStyle }) {
    const { t } = useTranslation();
    const [errorMsg, setErrorMsg] = useState();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    useEffect(() => {
        if (errorCode) {
            const serverErrors = t('serverError', { returnObjects: true });
            if (serverErrors[errorCode]) {
                setErrorMsg(serverErrors[errorCode]);
            } else {
                setErrorMsg(errorMessage)
            }
        } else {
            setErrorMsg(errorMessage);
        }
        console.log(errorCode, errorMessage)
    }, [t, errorCode, errorMessage]);

    return (
        <div className={`${customStyle ? customStyle.outerDiv : `flex justify-end max-w-7xl my-5 absolute ${isLoginLanguageRTL ? "left-0.5" : "right-0.5"}`}`}>
            <div className={`bg-[#C61818] ${customStyle ? customStyle.innerDiv : 'flex justify-between items-center rounded-xl max-w-[35rem] min-h-14 min-w-72 p-4'}`}>
                <div className="flex items-center">
                    <div className={`h-full ${isLoginLanguageRTL ? 'ml-8': 'mr-8'}`}>
                        <p className="text-white text-sm max-[450px]:text-xs/4 break-normal font-inter line-clamp-6">
                            {errorMsg}
                        </p>
                    </div>
                    <div className={`${isLoginLanguageRTL ? 'ml-3 mr-5 left-2' : 'mr-3 ml-5 right-2'} absolute ${(customStyle && customStyle.cancelIcon) ? customStyle.cancelIcon : 'top-4  mt-1'}`}>
                        <img id="error_close_btn" src={cancelIcon} alt="" className="cursor-pointer max-[450px]:h-3" onClick={clickOnCancel} />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ErrorMessage;