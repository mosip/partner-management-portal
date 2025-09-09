import { useState } from "react";
import { getUserProfile } from "../../../services/UserProfileService";
import { isLangRTL } from "../../../utils/AppUtils";
import ErrorMessage from "../../common/ErrorMessage";
import LoadingIcon from "../../common/LoadingIcon";
import Title from "../../common/Title";

function GenerateMispLicenseKey() {
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const style = {
        backArrowIcon: "!mt-[9%]",
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            {!dataLoaded && (
                <LoadingIcon />
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage id='generate_misp_license_key_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <div className="flex justify-between">
                            <Title title='mispLicenseList.generateMispLicenseKey' subTitle='mispLicenseList.mispPartnerServices' backLink={'/partnermanagement/admin/misp-partner-services/misp-license-list'} style={style} />
                        </div>
                    </div>
                </>
            )}
        </div>
    )
}
export default GenerateMispLicenseKey;