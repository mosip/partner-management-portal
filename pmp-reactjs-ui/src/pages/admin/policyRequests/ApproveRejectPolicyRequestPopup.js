import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { getPartnerManagerUrl, handleServiceErrors, isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from '../../common/LoadingIcon';
import { HttpService } from '../../../services/HttpService';

function ApproveRejectPolicyRequestPopup({ headerMsg, descriptionMsg, popupData, request, closePopUp }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const closingPopUp = () => {
        document.body.style.overflow = "auto"
        closePopUp()
    };

    const clickOnConfirm = async () => {
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        document.body.style.overflow = "auto";
        try {
            let response;
            console.log(request)
            if (popupData.id) {
                response = await HttpService.put(getPartnerManagerUrl(`/partners/policy/${popupData.id}`, process.env.NODE_ENV), request, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
                );
            }
            const responseData = response.data;
            if (responseData && responseData.response) {
                window.location.reload();
            } else {
                setDataLoaded(true);
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        } catch (error) {
            setDataLoaded(true);
            setErrorMsg(error)
        }
    };

    const styles = {
        loadingDiv: "!py-[35%]"
    };

    const customStyle = {
        outerDiv: "!flex !justify-end",
        innerDiv: "!flex !justify-between !items-center !rounded-xl !w-[55%] !min-h-12 !p-3 !m-1 !-mb-6"
    }

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-[50%] z-50 font-inter cursor-default">
            <div className={`bg-white min-w-[22rem] mx-auto rounded-lg shadow-lg min-h-[20rem]`}>
                {!dataLoaded && (
                    <LoadingIcon styleSet={styles} />
                )}
                {dataLoaded && (
                    <>
                        <div className="relative">
                            {errorMsg && (
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={customStyle} />
                            )}
                            <div className='px-[1.5rem] my-4'>
                                <div className={`flex-col space-y-3 text-center justify-center p-[1rem] w-[16.5rem] place-self-center`}>
                                    <p className="text-[1rem] leading-snug font-bold text-black break-normal">
                                        {!isLoginLanguageRTL ? t(headerMsg + ' ?') : t(headerMsg)}
                                    </p>
                                    <p className="text-xs text-[#666666] break-normal px-[1.2rem]">
                                        {descriptionMsg}
                                    </p>
                                </div>
                                <div>
                                    <p className='text-sm leading-snug'>{t('acceptRejectRequestPopup.comments')}</p>
                                    <textarea
                                        className="h-10 w-full px-2 py-3 my-1 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                        placeholder={t('acceptRejectRequestPopup.commentPlaceholder')}
                                    />
                                </div>
                            </div>
                            <hr className="h-px bg-gray-100 border-[0.02rem]" />
                            <div className="flex flex-row items-center justify-center space-x-3 py-[6%]">
                                <button id="accept_reject_request_close_btn" onClick={() => closingPopUp()} type="button" className="w-36 h-10 border-[#1447B2] border rounded-md text-tory-blue text-sm font-semibold">{t('requestPolicy.cancel')}</button>
                                <button id="accept_reject_request_submit_btn" onClick={() => clickOnConfirm()} type="button" className={`w-36 h-10 border-[#1447B2] border rounded-md bg-tory-blue text-white text-sm font-semibold ${isLoginLanguageRTL && '!mr-3'}`}>{t('deactivateOidcClient.confirm')}</button>
                            </div>
                        </div>
                    </>
                )}
            </div>
        </div>
    )
}

export default ApproveRejectPolicyRequestPopup;