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
    }, [t, errorCode, errorMessage]);

    return (
        <>
            <div className="h-full mr-8">
                <p className=" text-sm font-bold text-white break-words font-inter pb-1">
                    {errorMsg}
                </p>
            </div>
            <div className="mr-3 ml-5 absolute right-2">
                <img src={cancelIcon} alt="" onClick={clickOnCancel}></img>
            </div>
        </>
    );
}

export default ErrorMessage;