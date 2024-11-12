import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { createRequest, getPartnerManagerUrl, handleServiceErrors, isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from '../../common/LoadingIcon';
import { HttpService } from '../../../services/HttpService';
import close_icon from '../../../svg/close_icon.svg';

function ApproveRejectPolicyRequestPopup({ popupData, closePopUp }) {
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

    const changeTheRequestStatus = async (selectedStatus) => {
        let request;
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        request = createRequest({
            status : selectedStatus
        });
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
        innerDiv: "!flex !justify-between !items-center !rounded-xl !min-h-12 !p-3 !m-1 !-mb-2"
    }

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-[50%] z-50 font-inter cursor-default mx-1">
            <div className={`bg-white w-[24rem] mx-auto rounded-lg shadow-lg max-h-[26rem]`}>
                {!dataLoaded && (
                    <LoadingIcon styleSet={styles} />
                )}
                {dataLoaded && (
                    <>
                        <div className="relative">
                            {errorMsg && (
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={customStyle} />
                            )}
                            <div className='flex justify-between px-[1.5rem] my-4'>
                                <div className='flex-col justify-around space-y-2'>
                                    <p className='text-[1rem] font-bold'>{popupData.policyName}</p>
                                    <p className='text-[#A5A5A5] text-xs'>{'# ' + popupData.partnerId}</p>
                                </div>
                                <img src={close_icon} alt="" className='h-6 hover:cursor-pointer mx-1' onClick={() => closingPopUp()}/>
                            </div>
                            <hr className="h-px bg-gray-100 border-[0.02rem]" />
                            <div className='space-y-4 px-[1.5rem] my-4'>
                                <div className={`flex-col space-y-3 text-center justify-center w-[21.5rem] h-[5.5rem] place-self-center`}>
                                    <p className="text-[1rem] leading-snug font-semibold text-black break-normal">
                                        {!isLoginLanguageRTL ? t('approveRejectRequestPopup.header') + ' ?' : t('approveRejectRequestPopup.header')}
                                    </p>
                                    <p className="text-sm text-[#666666] break-normal">
                                        {t('approveRejectRequestPopup.description')}
                                    </p>
                                </div>
                                <div>
                                    <p className='text-sm leading-snug'>{t('approveRejectRequestPopup.comments')}</p>
                                    <textarea
                                        className="h-10 w-full px-2 py-3 my-1 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                        placeholder={t('approveRejectRequestPopup.commentPlaceholder')}
                                    />
                                </div>
                            </div>
                            <hr className="h-px bg-gray-100 border-[0.02rem]" />
                            <div className="flex flex-row items-center justify-between space-x-3 p-[6%]">
                                <button id="approve_reject_request_close_btn" onClick={() => changeTheRequestStatus('rejected')} type="button" className="w-36 h-10 border-[#1447B2] border rounded-md text-tory-blue text-sm font-normal">{t('approveRejectRequestPopup.reject')}</button>
                                <button id="approve_reject_request_submit_btn" onClick={() => changeTheRequestStatus('approved')} type="button" className={`w-36 h-10 border-[#1447B2] border rounded-md bg-tory-blue text-white text-sm font-normal ${isLoginLanguageRTL && '!mr-3'}`}>{t('approveRejectRequestPopup.approve')}</button>
                            </div>
                        </div>
                    </>
                )}
            </div>
        </div>
    )
}

export default ApproveRejectPolicyRequestPopup;