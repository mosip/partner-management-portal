import { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import cancelIcon from '../../svg/cancel_icon.svg';

function ErrorMessage({ errorCode, errorMessage, clickOnCancel }) {
    const { t } = useTranslation();
    const [errorMsg, setErrorMsg] = useState();

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
        <>
            <div className="h-full mr-8">
                <p className="text-white text-sm/4 max-[450px]:text-xs/4 break-words font-inter line-clamp-6">
                    {errorMsg}
                </p>
            </div>
            <div className="mr-3 ml-5 absolute top-4 right-2">
                <img src={cancelIcon} alt="" className="cursor-pointer max-[450px]:h-3" onClick={clickOnCancel}/>
            </div>
        </>
    );
}

export default ErrorMessage;