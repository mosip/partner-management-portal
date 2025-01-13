import { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import cancelIcon from '../../svg/cancel_icon.svg';
import { isLangRTL, getErrorMessage, onPressEnterKey } from "../../utils/AppUtils";
import { getUserProfile } from "../../services/UserProfileService";

function ErrorMessage({ errorCode, errorMessage, clickOnCancel, customStyle }) {
    const { t } = useTranslation();
    const [errorMsg, setErrorMsg] = useState();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    useEffect(() => {
        setErrorMsg(getErrorMessage(errorCode, t, errorMessage));
        console.log(errorCode, errorMessage)
    }, [t, errorCode, errorMessage]);

    return (
        <div className={`${customStyle ? customStyle.outerDiv : `flex justify-end max-w-7xl my-3 absolute ${isLoginLanguageRTL ? "left-0.5" : "right-0.5"}`}`}>
            <div className={`bg-[#C61818] ${customStyle ? customStyle.innerDiv : 'flex justify-between items-center rounded-xl max-w-[35rem] min-h-14 max-h-[3.8rem] min-w-72 p-4'}`}>
                <div className="flex items-center">
                    <div className={`h-full ${isLoginLanguageRTL ? 'ml-8' : 'mr-8'}`}>
                        <p className="text-white text-sm max-[450px]:text-xs/4 break-normal font-inter">
                            {errorMsg}
                        </p>
                    </div>
                    <div className={`${isLoginLanguageRTL ? 'ml-3 mr-5 left-2' : 'mr-3 ml-5 right-2'} absolute ${(customStyle && customStyle.cancelIcon) ? customStyle.cancelIcon : 'top-4  mt-1'}`}>
                        <button onClick={clickOnCancel} id="error_close_btn" className="cursor-pointer max-[450px]:h-3">
                            <img src={cancelIcon} alt="close" />
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ErrorMessage;