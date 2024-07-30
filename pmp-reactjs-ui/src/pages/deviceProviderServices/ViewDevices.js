import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService.js';
import Title from '../common/Title.js';
import { isLangRTL } from '../../utils/AppUtils.js';
import LoadingIcon from "../common/LoadingIcon.js";
import ErrorMessage from '../common/ErrorMessage.js';
function ViewDevices() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const styleForTitle = {
        backArrowIcon: "!mt-[5%]"
    }

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <div className={`flex justify-end max-w-7xl mb-5 mt-2 absolute ${isLoginLanguageRTL ? "left-0" : "right-2"}`}>
                            <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 z-10">
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                            </div>
                        </div>
                    )}
                    <div className="flex-col mt-7">
                    <div className="flex justify-between mb-5">
                            <Title title='deviceProviderServices.listOfSbisAndDevices' backLink='/partnermanagement/deviceProviderServices/sbiList' styleSet={styleForTitle}></Title>
                        </div>
                    </div>
                </>
            )}
        </div>
    )
}

export default ViewDevices;