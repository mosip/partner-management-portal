import FocusTrap from "focus-trap-react";
import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import LoadingIcon from "./LoadingIcon";
import ErrorMessage from "./ErrorMessage";
import close_icon from '../../svg/close_icon.svg';
import { createRequest, getPartnerManagerUrl, handleEscapeKey, handleServiceErrors } from "../../utils/AppUtils";
import { HttpService } from "../../services/HttpService";

function RejectPopup({ popupData, closePopUp, rejectResponse, title }) {
    const { t } = useTranslation();
    const [errorCode, setErrorCode] = useState('');
    const [errorMsg, setErrorMsg] = useState('');
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
    }, []);

    const cancelErrorMsg = () => setErrorMsg("");

    const onClickReject = async () => {
        setErrorCode('');
        setErrorMsg('');
        setDataLoaded(false);
        try {
            const request = createRequest({
                partnerId: popupData.partnerId,
                sbiId: popupData.sbiId,
                status: 'rejected'
            }, "mosip.pms.approval.mapping.device.to.sbi.post", true);

            const url = getPartnerManagerUrl(`/devicedetail/${popupData.deviceId}/approval`, process.env.NODE_ENV);

            const response = await HttpService.post(url, request, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const responseData = response.data;
            if (responseData && responseData.response) {
                rejectResponse(responseData.response);
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
    };

    return(
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-35 z-50 font-inter cursor-default mx-1 break-normal">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className="bg-white md:w-[24rem] w-[55%] mx-auto rounded-lg shadow-sm h-fit">
                    {!dataLoaded ? (
                        <LoadingIcon styleSet={{ loadingDiv: '!py-[35%]' }} />
                    ) : (
                        <>
                            <div className="relative">
                                {errorMsg && (
                                    <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={customStyle} />
                                )}
                                <>
                                    <div className="flex justify-between items-center px-[1rem] p-4 w-full border-b-2 border-gray-200">
                                        <p className="text-sm font-bold">{title}</p>
                                        <button id="reject_popup_close_icon" onClick={() => closePopUp()} className="h-6 hover:cursor-pointer">
                                            <img src={close_icon} alt="close" />
                                        </button>
                                    </div>
                                    <div className="px-[1.5rem] py-3 text-center break-words border-b-2 border-gray-200">
                                        <p className="text-base font-semibold text-black">{t('deviceApproveRejectPopup.rejectHeader')}</p>
                                        <p className="my-4 text-sm font-medium text-[#8B6105] py-2 px-4 bg-[#FFF7E5] border-2 border-[#EDDCAF] rounded-md">
                                            {t('deviceApproveRejectPopup.rejectDescription')}
                                        </p>
                                    </div>
                                    <div className="flex justify-end p-5">
                                        <button onClick={onClickReject} className="w-36 h-10 border-[#1447B2] border rounded-md bg-tory-blue text-white">
                                            Reject
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

export default RejectPopup;