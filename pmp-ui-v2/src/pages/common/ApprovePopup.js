import { useTranslation } from "react-i18next";
import { getPartnerManagerUrl, handleEscapeKey, handleServiceErrors, isLangRTL } from "../../utils/AppUtils";
import { getUserProfile } from "../../services/UserProfileService";
import { useEffect, useState } from "react";
import FocusTrap from "focus-trap-react";
import LoadingIcon from "./LoadingIcon";
import ErrorMessage from "./ErrorMessage";
import { HttpService } from "../../services/HttpService";

function ApprovePopup({ closePopUp, title, description, request, popupData, onClickConfirm }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);

    useEffect(() => {
        document.body.style.overflow = "hidden";

        return () => {
            document.body.style.overflow = "auto";
        };
    }, []);

    useEffect(() => {
        const removeListener = handleEscapeKey(() => closePopUp());
        return removeListener;
    }, [closePopUp]);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const closingPopUp = () => {
        closePopUp()
    };

    const clickOnConfirm = async () => {
        setErrorCode('');
        setErrorMsg('');
        setDataLoaded(false);
        try {
            const response = await HttpService.put(getPartnerManagerUrl(`/partners/policy/${popupData.id}`, process.env.NODE_ENV), request, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                const responseData = response.data;
                if (responseData && responseData.response) {
                    onClickConfirm();
                } else {
                    setDataLoaded(true);
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
        } catch (error) {
            if (error.response.status !== 401) {
                setDataLoaded(true);
                setErrorMsg(error.toString());
            }
        }
    };

    const styles = {
        loadingDiv: "!py-[35%]"
    };

    const customStyle = {
        outerDiv: "!flex !justify-end",
        innerDiv: "!flex !justify-between !items-center !rounded-xl !w-full !min-h-12 !p-3 !m-1 !-mb-6"
    }

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-35 z-50 font-inter cursor-default">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className={`bg-white md:w-[390px] w-[55%] mx-auto rounded-lg shadow-sm h-fit`}>
                    {!dataLoaded && (
                        <LoadingIcon styleSet={styles} />
                    )}
                    {dataLoaded && (
                        <div className="relative">
                            {errorMsg && (
                                <ErrorMessage id='approve_popup_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={customStyle} />
                            )}
                            <div className={`p-[2rem] flex-col text-center justify-center items-center`}>
                                <p id='approve_popup_title' className="text-base leading-snug font-semibold text-black break-words px-[1.5rem]">
                                    {t(title)}
                                </p>
                                <p id='approve_popup_description' className="text-sm font-semibold text-[#666666] break-normal py-[5%]">
                                    {t(description)}
                                </p>
                                <div className="flex flex-row items-center justify-center space-x-3 pt-[4%]">
                                    <button id="approve_popup_cancel_btn" onClick={() => closingPopUp()} type="button" className="w-40 h-12 border-[#1447B2] border rounded-md text-tory-blue text-sm font-semibold">{t('requestPolicy.cancel')}</button>
                                    <button id="approve_popup_submit_btn" onClick={() => clickOnConfirm()} type="button" className={`w-40 h-12 border-[#1447B2] border rounded-md bg-tory-blue text-white text-sm font-semibold ${isLoginLanguageRTL && '!mr-3'}`}>{t('deactivateOidcClient.confirm')}</button>
                                </div>
                            </div>
                        </div>
                    )}
                </div>
            </FocusTrap>
        </div>
    )
}

export default ApprovePopup;