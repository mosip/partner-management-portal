import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { downloadCaTrust, formatDate, getErrorMessage, isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import Title from '../../common/Title';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg'
import ErrorMessage from '../../common/ErrorMessage';
import SuccessMessage from '../../common/SuccessMessage';
import fileUploadDisabled from '../../../svg/file_upload_disabled_icon.svg';
import fileUpload from '../../../svg/file_upload_icon.svg';
import { HttpService } from '../../../services/HttpService';


function ViewTrustDetails() {

    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [viewCertPageHeaders, setViewCertPageHeaders] = useState(true);
    const [viewCertDetails, setViewCertDetails] = useState(true);

    useEffect(() => {
        const data = localStorage.getItem('selectedTrustAttributes')
        if (!data) {
            setUnexpectedError(true);
            return;
        }
        const viewData = JSON.parse(data);
        setViewCertDetails(viewData.trustData);
        setViewCertPageHeaders(viewData);
    }, []);

    const onClickDownload = (certificateId) => {
        downloadCaTrust(HttpService, certificateId, viewCertPageHeaders.trustType, setErrorCode, setErrorMsg, errorMsg, setSuccessMsg, t );
    };

    const moveBackToList = () => {
        navigate(viewCertPageHeaders.backLink);
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const cancelSuccessMsg = () => {
        setSuccessMsg("");
    };

    return (
        <div className={`w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
            {errorMsg && (
                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
            )}
            {successMsg && (
                <SuccessMessage successMsg={successMsg} clickOnCancel={cancelSuccessMsg} />
            )}
            <div className="flex-col mt-5">
                <div className="flex justify-between mb-5">
                    <Title title={viewCertPageHeaders.header} subTitle={viewCertPageHeaders.subTitle} backLink={viewCertPageHeaders.backLink} />
                </div>
                {unexpectedError && (
                    <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                        <div className="flex items-center justify-center p-24">
                            <div className="flex flex-col justify-center items-center">
                                <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                <p className="text-base font-semibold text-[#6F6E6E] pt-4">{t('commons.unexpectedError')}</p>
                                <p className="text-sm font-semibold text-[#6F6E6E] pt-1 pb-4">{getErrorMessage(errorCode, t, errorMsg)}</p>
                                <button onClick={moveBackToList} type="button"
                                    className={`w-32 h-10 flex items-center justify-center font-semibold rounded-md text-sm mx-8 py-3 bg-tory-blue text-white`}>
                                    {t('commons.goBack')}
                                </button>
                            </div>
                        </div>
                    </div>
                )}
                {!unexpectedError && (
                    <div className="bg-snow-white h-fit mt-1 rounded-t-xl shadow-lg font-inter">
                        <div className="flex justify-between px-7 pt-3 border-b max-[450px]:flex-col">
                            <div className="flex-col">
                                <p className="text-lg text-dark-blue mb-2 break-all">
                                    {t('trustList.certificateId')}: <span className="font-semibold">{viewCertDetails.certId}</span>
                                </p>
                                <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                    <div className={`font-semibold text-sm text-dark-blue`}>
                                        {t("viewCertificateDetails.uploadedOn") + ' ' +
                                            formatDate(viewCertDetails.uploadTime, "date")
                                        }
                                    </div>
                                    <div className="mx-2 text-gray-300">|</div>
                                    <div className="font-semibold text-sm text-dark-blue">
                                        {formatDate(viewCertDetails.uploadTime, "time")}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                            <div className="flex flex-wrap py-2 max-[450px]:flex-col">
                                <div className={`w-[48%] max-[600px]:w-[100%] mb-1 ${isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"}`}>
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("trustList.issuedTo")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md break-all">
                                        {viewCertDetails.issuedTo}
                                    </p>
                                </div>
                                <div className="w-[48%] max-[600px]:w-[100%] mb-1">
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("trustList.issuedBy")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md break-all">
                                        {viewCertDetails.issuedBy}
                                    </p>
                                </div>
                            </div>
                            <div className="flex flex-wrap py-2 max-[450px]:flex-col">
                                <div className={`w-[48%] max-[600px]:w-[100%] mb-1`}>
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("viewCertificateDetails.certThumbprint")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md break-all">
                                        {viewCertDetails.certThumbprint}
                                    </p>
                                </div>
                            </div>
                            <hr className={`h-px w-full bg-gray-200 border-0 mb-[2.5%]`} />
                            <div className="rounded-lg shadow-lg border mb-[2%]">
                                <div className={`flex-col`}>
                                    <div className={`flex py-[1rem] px-5 ${(viewCertDetails.status === true) ? 'bg-[#f0fff6]' : 'bg-gray-100'} justify-between items-center max-520:flex-col`}>
                                        <div className="flex space-x-4 items-center ">
                                            {(viewCertDetails.status === true) ?
                                                <img id='file_upload_blue' src={fileUpload} className="h-8" alt="" />
                                                :
                                                <img id='file_upload_disabled' src={fileUploadDisabled} className="h-8" alt="" />
                                            }
                                            <div className="flex-col p-3 items-center">
                                                <h6 className={`text-sm ${(viewCertDetails.status === true) ? 'font-bold text-black' : 'font-semibold text-charcoal-gray'}`}>
                                                    {viewCertPageHeaders.trustType === 'root' ? t('viewCertificateDetails.rootCaCertificate') : t('viewCertificateDetails.intermediateCaCertificate')}
                                                </h6>
                                            </div>
                                        </div>

                                        <div className=" flex space-x-2">
                                            <div className="flex space-x-2 max-640:flex-col max-640:space-y-2 max-640:space-x-0">
                                                <button id='certificate_download_btn' disabled={viewCertDetails.status !== true} onClick={() => onClickDownload(viewCertDetails.certId)}
                                                    className={`flex items-center text-center w-fit h-10 ${isLoginLanguageRTL ? "ml-5" : "mr-5"} ${viewCertDetails.status !== true ? 'text-[#6f7070] border-gray-300 bg-white' : 'text-tory-blue bg-white border-blue-800'} text-xs px-[1.5rem] py-[1%] border font-semibold rounded-lg text-center`}>
                                                    {viewCertPageHeaders.trustType === 'root' ? t('commons.download') : t('viewCertificateDetails.downloadTrustChain')}
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <hr className="border bg-medium-gray h-px" />
                                    <div className="flex items-center p-5 bg-white rounded-lg">
                                        <div className="flex-col space-y-1">
                                            <p id="trust_certificate_partner_type_label" className="font-semibold text-sm text-dim-gray">{t('trustList.partnerDomain')}</p>
                                            <p id="trust_certificate_partner_type_context" className="font-semibold text-md text-charcoal-gray">{viewCertDetails.partnerDomain}</p>
                                        </div>
                                        <div className={`flex-col ${isLoginLanguageRTL ? "mr-[10%]" : "ml-[10%]"} space-y-1`}>
                                            <p id="trust_certificate_label_upload_date_time" className="font-semibold text-sm text-dim-gray">{t('trustList.validFrom')}</p>
                                            <p id="trust_certificate_context_upload_date_time" className="font-semibold text-md text-charcoal-gray">
                                                {formatDate(viewCertDetails.validFromDate, 'dateTime')}
                                            </p>
                                        </div>
                                        <div className={`flex-col ${isLoginLanguageRTL ? "mr-[5%]" : "ml-[5%]"} space-y-1`}>
                                            <p id="trust_certificate_label_expiry_date_time" className={`text-sm font-semibold text-dim-gray`}>{t('viewCertificateDetails.validTo')}</p>
                                            <p id="trust_certificate_context_expiry_date_time" className={`text-md ${!viewCertDetails.status ? 'text-crimson-red font-bold' : 'text-charcoal-gray font-semibold'}`}>
                                                {formatDate(viewCertDetails.validTillDate, 'dateTime')}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className={`flex justify-end py-5 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                            <button id="view_trust_certificate_back_btn" onClick={moveBackToList} className={`h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center`}>
                                {t("commons.back")}
                            </button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    )
}
export default ViewTrustDetails;