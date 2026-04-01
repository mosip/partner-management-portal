import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { getPartnerManagerUrl, handleServiceErrors, createRequest, handleEscapeKey, getPartnerTypeDescription, isLangRTL } from '../../utils/AppUtils';
import { HttpService } from '../../services/HttpService';
import LoadingIcon from './LoadingIcon';
import ErrorMessage from './ErrorMessage';
import close_icon from '../../svg/close_icon.svg';
import FocusTrap from 'focus-trap-react';
import PropTypes from 'prop-types';
import { getUserProfile } from '../../services/UserProfileService';

function ApproveRejectPopup({
    popupData,
    closePopUp,
    approveRejectResponse,
    title,
    subtitle,
    header,
    description,
    renderPolicyDetails,
}) {
    const { t } = useTranslation();
    const [errorCode, setErrorCode] = useState('');
    const [errorMsg, setErrorMsg] = useState('');
    const [dataLoaded, setDataLoaded] = useState(true);
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

    const hasPolicyDetails = typeof renderPolicyDetails === 'function';

    const [policyDetailsLoading, setPolicyDetailsLoading] = useState(() => Boolean(hasPolicyDetails));

    useEffect(() => {
        const previousOverflow = document.body.style.overflow;
        document.body.style.overflow = "hidden";
        return () => {
            document.body.style.overflow = previousOverflow;
        };
    }, []);

    useEffect(() => {
        const removeListener = handleEscapeKey(() => closePopUp());
        return removeListener;
    }, [closePopUp]);

    const cancelErrorMsg = () => setErrorMsg("");
    const closingPopUp = () => {
        closePopUp();
    };

    const handleStatusChange = async (status) => {
        setErrorCode('');
        setErrorMsg('');
        setDataLoaded(false);

        try {
            let response;
            if (popupData.isPartnerPolicyRequest) {
                const request = createRequest({
                    status: status
                });
                response = await HttpService.put(getPartnerManagerUrl(`/partners/policy/${popupData.id}`, process.env.NODE_ENV), request, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
                );
            }
            if (popupData.isFtmRequest) {
                const request = createRequest({
                    ftpChipDetailId: popupData.ftmId,
                    approvalStatus: status === "approved" ? true : false
                });
                response = await HttpService.patch(getPartnerManagerUrl(`/ftpchipdetail`, process.env.NODE_ENV), request, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
                );
            }
            if (popupData.isSbiRequest) {
                const request = createRequest({
                    id: popupData.sbiId,
                    approvalStatus: status === "approved" ? 'Activate' : 'De-activate'
                });
                response = await HttpService.patch(getPartnerManagerUrl(`/securebiometricinterface`, process.env.NODE_ENV), request, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
                );
            }
            if (popupData.isDeviceRequest) {
                const request = createRequest({
                    partnerId: popupData.partnerId,
                    sbiId: popupData.sbiId,
                    status: status
                }, "mosip.pms.approval.mapping.device.to.sbi.post", true);

                const url = getPartnerManagerUrl(`/devicedetail/${popupData.deviceId}/approval`, process.env.NODE_ENV);

                response = await HttpService.post(url, request, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
            }
            const responseData = response.data;
            if (responseData && responseData.response) {
                approveRejectResponse(responseData.response, status);
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

    const customStyle = {
        outerDiv: "!flex !justify-end p-1",
        innerDiv: "!flex !justify-between !items-center !rounded-xl !min-h-12 !p-3 !m-1 !-mb-2 w-full"
    }

    const modalWidth = hasPolicyDetails ? "md:w-[42rem] w-[92%]" : "md:w-[24rem] w-[55%]";

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-35 z-50 font-inter cursor-default mx-1 break-normal">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className={`bg-white mx-auto rounded-lg shadow-sm ${modalWidth} max-h-[90vh] flex flex-col`}>
                    {!dataLoaded ? (
                        <LoadingIcon styleSet={{ loadingDiv: '!py-[35%]' }} />
                    ) : (
                        <>
                            <div className="relative flex flex-col max-h-[90vh]">
                                {errorMsg && (
                                    <ErrorMessage id='approve_reject_popup_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={customStyle} />
                                )}
                                <>
                                    <div className="relative px-[1rem] pt-4 pb-2 w-full">
                                        <div
                                            className={`flex-col space-y-1 break-words ${isLoginLanguageRTL ? 'pl-12 text-right' : 'pr-12 text-left'}`}
                                        >
                                            <p id='approve-reject_popup_title' className="text-sm font-bold">{title}</p>
                                            {subtitle && (
                                                <p id='approve-reject_popup_sub_title' className="text-[#A5A5A5] text-xs">{subtitle}</p>
                                            )}
                                        </div>
                                        <button
                                            id="approve_reject_popup_close_icon"
                                            aria-label={t('commons.close')}
                                            onClick={closingPopUp}
                                            className={`h-8 w-8 rounded-full bg-[#F2F4F8] flex items-center justify-center hover:cursor-pointer absolute top-3 ${isLoginLanguageRTL ? 'left-4' : 'right-4'}`}
                                        >
                                            <img src={close_icon} alt="" className="h-4 w-4" />
                                        </button>
                                    </div>
                                    <hr className="h-px bg-gray-100 border-[0.02rem]" />
                                    <div
                                        className={`flex-1 overflow-y-auto ${
                                            hasPolicyDetails
                                                ? '[-ms-overflow-style:none] [scrollbar-width:none] [&::-webkit-scrollbar]:hidden'
                                                : ''
                                        }`}
                                    >
                                        <div className="px-[1.5rem] py-3 text-center break-words">
                                            <p id='approve-reject_popup_header' className="text-base font-semibold text-black">{header}</p>
                                            <p id='approve-reject_popup_description' className="text-sm text-[#666666] py-3">{description}</p>
                                        </div>

                                        {hasPolicyDetails && (
                                            <div className="px-[1.5rem] pb-3">
                                                {renderPolicyDetails({
                                                    t,
                                                    isLoginLanguageRTL,
                                                    popupData,
                                                    onLoadingChange: setPolicyDetailsLoading,
                                                    getPartnerTypeDescription,
                                                })}
                                            </div>
                                        )}
                                    </div>
                                    <hr className="h-px bg-gray-100 border-[0.02rem]" />
                                    <div className="flex items-center justify-between gap-3 px-6 py-4">
                                        <button
                                            id="reject_btn"
                                            onClick={() => handleStatusChange('rejected')}
                                            type="button"
                                            disabled={policyDetailsLoading}
                                            className={`w-32 h-9 border-[#1447B2] border rounded-md text-tory-blue ${policyDetailsLoading ? 'opacity-50 cursor-not-allowed' : ''}`}
                                        >
                                            {t('approveRejectPopup.reject')}
                                        </button>
                                        <button
                                            id="approve_btn"
                                            onClick={() => handleStatusChange('approved')}
                                            type="button"
                                            disabled={policyDetailsLoading}
                                            className={`w-32 h-9 border-[#1447B2] border rounded-md bg-tory-blue text-white ${policyDetailsLoading ? 'opacity-50 cursor-not-allowed' : ''}`}
                                        >
                                            {t('approveRejectPopup.approve')}
                                        </button>
                                    </div>
                                </>
                            </div>
                        </>
                    )}
                </div>
            </FocusTrap>
        </div>
    );
}

ApproveRejectPopup.propTypes = {
    popupData: PropTypes.object.isRequired,
    closePopUp: PropTypes.func.isRequired,
    approveRejectResponse: PropTypes.func.isRequired,
    title: PropTypes.string.isRequired,
    subtitle: PropTypes.string,
    header: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    renderPolicyDetails: PropTypes.func,
};

export default ApproveRejectPopup;
