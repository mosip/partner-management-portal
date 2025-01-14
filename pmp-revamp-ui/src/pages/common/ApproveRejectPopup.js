import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { getPartnerManagerUrl, handleServiceErrors, createRequest } from '../../utils/AppUtils';
import { HttpService } from '../../services/HttpService';
import LoadingIcon from './LoadingIcon';
import ErrorMessage from './ErrorMessage';
import close_icon from '../../svg/close_icon.svg';
import FocusTrap from 'focus-trap-react';

function ApproveRejectPopup({ popupData, closePopUp, approveRejectResponse, title, subtitle, header, description }) {
    const { t } = useTranslation();
    const [errorCode, setErrorCode] = useState('');
    const [errorMsg, setErrorMsg] = useState('');
    const [dataLoaded, setDataLoaded] = useState(true);

    const cancelErrorMsg = () => setErrorMsg("");
    const closingPopUp = () => {
        document.body.style.overflow = 'auto';
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
            setDataLoaded(true);
            setErrorMsg(error);
        }
    };

    const customStyle = {
        outerDiv: "!flex !justify-end p-1",
        innerDiv: "!flex !justify-between !items-center !rounded-xl !min-h-12 !p-3 !m-1 !-mb-2 w-full"
    }

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-[50%] z-50 font-inter cursor-default mx-1 break-normal">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className="bg-white md:w-[24rem] w-[55%] mx-auto rounded-lg shadow-lg h-fit">
                    {!dataLoaded ? (
                        <LoadingIcon styleSet={{ loadingDiv: '!py-[35%]' }} />
                    ) : (
                        <>
                            <div className="relative">
                                {errorMsg && (
                                    <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={customStyle} />
                                )}
                                <div>
                                    <div className="flex justify-between px-[1.5rem] my-4 w-full">
                                        <div className="flex-col space-y-2 break-words w-[96%]">
                                            <p className="text-sm font-bold">{title}</p>
                                            {subtitle && (
                                                <p className="text-[#A5A5A5] text-xs">{subtitle}</p>
                                            )}
                                        </div>
                                        <button id="approve_reject_popup_close_icon" onClick={closingPopUp} className="h-6 hover:cursor-pointer mx-1">
                                            <img src={close_icon} alt="close" />
                                        </button>
                                    </div>
                                    <hr className="h-px bg-gray-100 border-[0.02rem]" />
                                    <div className="px-[1.5rem] py-3 text-center">
                                        <p className="text-base font-semibold text-black">{header}</p>
                                        <p className="text-sm text-[#666666] py-3">{description}</p>
                                    </div>
                                    <hr className="h-px bg-gray-100 border-[0.02rem]" />
                                    <div className="flex items-center justify-between space-x-3 p-[6%]">
                                        <button id="reject_btn" onClick={() => handleStatusChange('rejected')} type="button" className="w-36 h-10 border-[#1447B2] border rounded-md text-tory-blue">
                                            {t('approveRejectPopup.reject')}
                                        </button>
                                        <button id="approve_btn" onClick={() => handleStatusChange('approved')} type="button" className="w-36 h-10 border-[#1447B2] border rounded-md bg-tory-blue text-white">
                                            {t('approveRejectPopup.approve')}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </>
                    )}
                </div>
            </FocusTrap>
        </div>
    );
}

export default ApproveRejectPopup;
