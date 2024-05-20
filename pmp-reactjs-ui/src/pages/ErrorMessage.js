import { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";

function ErrorMessage ({errorCode, errorMessage, clickOnCancel}) {
    const { t } = useTranslation();
    const [errorMsg, setErrorMsg] = useState();

    useEffect(() => {
        if (errorCode) {
            const serverErrors = t('serverError', { returnObjects: true });
            if(serverErrors[errorCode]) {
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
            <div>
                <p className=" text-sm font-semibold text-white break-words font-inter">
                    {errorMsg}
                </p>
            </div>
            <div className="mr-3 ml-5">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="16.828"
                    height="16.828"
                    viewBox="0 0 16.828 16.828"
                    onClick={clickOnCancel}
                >
                    <path
                        id="close_FILL0_wght400_GRAD0_opsz48"
                        d="M 23.27308082580566 25.05710983276367 L 22.91953086853027 24.70355033874512 L 17.35000038146973 19.13401985168457 L 11.7804708480835 24.70355033874512 L 11.42691993713379 25.05710983276367 L 11.0733699798584 24.70355033874512 L 9.996450424194336 23.62663078308105 L 9.642889976501465 23.27308082580566 L 9.996450424194336 22.91953086853027 L 15.56597995758057 17.35000038146973 L 9.996450424194336 11.7804708480835 L 9.642889976501465 11.42691993713379 L 9.996450424194336 11.07336044311523 L 11.07338047027588 9.996439933776855 L 11.42693042755127 9.642889976501465 L 11.78048038482666 9.996450424194336 L 17.35000038146973 15.5659704208374 L 22.91953086853027 9.996450424194336 L 23.27308082580566 9.642889976501465 L 23.62663078308105 9.996450424194336 L 24.70355033874512 11.0733699798584 L 25.05710983276367 11.42691993713379 L 24.70355033874512 11.7804708480835 L 19.13401985168457 17.35000038146973 L 24.70355033874512 22.91953086853027 L 25.05710983276367 23.27308082580566 L 24.70355033874512 23.62663078308105 L 23.62663078308105 24.70355033874512 L 23.27308082580566 25.05710983276367 Z"
                        transform="translate(-8.936 -8.936)"
                        fill="#fff"
                    />
                </svg>
            </div>
        </>
    );
}

export default ErrorMessage;