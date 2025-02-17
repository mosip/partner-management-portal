import { useState, useEffect, useCallback } from 'react';
import { HttpService } from "../../../services/HttpService";
import { isLangRTL, formatDate, getPartnerTypeDescription, getPartnerManagerUrl, getPartnerDomainType, createRequest } from '../../../utils/AppUtils';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../../services/UserProfileService';
import ErrorMessage from "../../common/ErrorMessage";
import LoadingIcon from "../../common/LoadingIcon";
import SuccessMessage from '../../common/SuccessMessage';
import fileUploadImg from './../../../svg/file_upload_certificate.svg';
import fileDescription from '../../../svg/file_description.svg';
import FocusTrap from 'focus-trap-react';
import { Certificate } from "pkijs";
import { fromBER } from "asn1js";

function UploadCertificate({ closePopup, popupData, request }) {
    const [partnerDomainType, setPartnerDomainType] = useState("");
    const [partnerType, setPartnerType] = useState("");
    const [uploading, setUploading] = useState(false);
    const [fileName, setFileName] = useState('');
    const [uploadSuccess, setUploadSuccess] = useState(false);
    const [uploadFailure, setUploadFailure] = useState(false);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [certificateData, setCertificateData] = useState("");
    const [formattedDate, setFormattedDate] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [removeLastUploadDate, setRemoveLastUploadData] = useState(false);
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    useEffect(() => {
        document.body.style.overflow = "hidden";

        return () => {
            document.body.style.overflow = "auto";
        };
    }, []);

    const clickOnCancel = () => {
        closePopup(true, 'cancel');
    };
    const clickOnSubmit = async () => {
        setSuccessMsg("");
        setErrorCode("");
        setErrorMsg("");
        if (uploadSuccess) {
            closePopup(true, 'close');
        } else {
            setDataLoaded(false);
            let uploadRequest = {};
            if (popupData.isUploadPartnerCertificate) {
                uploadRequest = createRequest({
                    ...request,
                    certificateData: certificateData,
                });
            }
            if (popupData.isUploadFtmCertificate) {
                uploadRequest = createRequest({
                    ...request,
                    certificateData: certificateData,
                    organizationName: getOrganization(certificateData,)
                });
            }
            try {
                let response;
                if (popupData.isUploadPartnerCertificate) {
                    response = await HttpService.post(getPartnerManagerUrl('/partners/certificate/upload', process.env.NODE_ENV), uploadRequest)
                } else if (popupData.isUploadFtmCertificate) {
                    response = await HttpService.post(getPartnerManagerUrl('/ftpchipdetail/uploadcertificate', process.env.NODE_ENV), uploadRequest)
                }
                if (response) {
                    const resData = response.data.response;
                    if (response.data.errors && response.data.errors.length > 0) {
                        const errorCode = response.data.errors[0].errorCode;
                        const errorMessage = response.data.errors[0].message;
                        setUploadFailure(true);
                        if (errorCode === 'PMS_KKS_001') {
                            setErrorMsg(t('uploadCertificate.errorWhileUploadingCertificate'));
                        } else {
                            setErrorCode(errorCode);
                            setErrorMsg(errorMessage);
                        }
                    } else if (resData === null) {
                        setUploadFailure(true);
                        setErrorMsg(t('uploadCertificate.unableToUploadCertificate'));
                    } else {
                        setUploadSuccess(true);
                        const successText = popupData.successMessage ? t(popupData.successMessage) : t('uploadCertificate.successMsg', { partnerType: getPartnerType(popupData) });
                        setSuccessMsg(successText);
                    }
                } else {
                    setUploadFailure(true);
                    setErrorMsg(t('uploadCertificate.errorWhileUploadingCertificate'));
                }
                setDataLoaded(true);
            } catch (err) {
                if (err.response?.status && err.response.status !== 401) {
                    setUploadFailure(true);
                    setErrorMsg(err.toString());
                }
                console.log("Unable to upload partner certificate: ", err);
            }
        }
    };

    const decodeCertificate = (certificateData) => {
        const certBase64 = certificateData
            .replace("-----BEGIN CERTIFICATE-----", "")
            .replace("-----END CERTIFICATE-----", "")
            .replace(/\s+/g, "");

        // Convert the base64 string to a Uint8Array
        const certBinary = Uint8Array.from(atob(certBase64), (c) => c.charCodeAt(0));

        // Parse the certificate using asn1js and pkijs
        const asn1 = fromBER(certBinary.buffer);
        if (asn1.offset === -1) {
            setErrorMsg(t('uploadCertificate.errorWhileGettingCertOrgName'));
        }
        const cert = new Certificate({ schema: asn1.result });
        return cert;
    };

    const getOrganization = (certificate) => {
        const decodedCert = decodeCertificate(certificate);

        // Iterate over typesAndValues in the subject to find O (Organization)
        const organizationAttr = decodedCert.subject.typesAndValues.find(
            (attr) => attr.type === "2.5.4.10"
        );
        return organizationAttr.value.valueBlock.value;

    };

    const getPartnerType = useCallback((popupData) => {
        if (popupData.partnerType) {
            const partnerTypeDesc = getPartnerTypeDescription(popupData.partnerType, t);
            return partnerTypeDesc;
        }
    }, [t]);

    const cancelUpload = () => {
        setFileName("");
        setUploading(false);
    };
    const removeUpload = () => {
        setFileName("");
        setUploadSuccess(false);
        setUploading(false);
    };
    const cancelErrorMsg = () => {
        setErrorMsg("");
        setErrorCode("");
    };
    const cancelSuccessMsg = () => {
        setSuccessMsg("");
    };
    const handleFileChange = (event) => {
        setErrorMsg("");
        setErrorCode("");
        const file = event.target.files[0];
        if (file) {
            const fileName = file.name;
            const fileExtension = fileName.split('.').pop().toLowerCase();
            if (fileExtension === 'cer' || fileExtension === 'pem') {
                const reader = new FileReader();
                reader.onload = (e) => {
                    const fileData = e.target.result;
                    setUploading(true);
                    setRemoveLastUploadData(true);
                    setFileName(fileName);
                    setCertificateData(fileData);
                    setTimeout(() => {
                        setUploading(false);
                    }, 2000);
                }
                reader.readAsText(file);
            } else {
                setUploadFailure(true);
                setErrorMsg(t('uploadCertificate.fileUploadError'));
            }
        }
    };

    useEffect(() => {
        setPartnerType(getPartnerType(popupData));
        setPartnerDomainType(getPartnerDomainType(popupData.partnerType));
        if (popupData.isCertificateAvailable) {
            const dateString = popupData.certificateUploadDateTime.toString();
            const formatted = formatDate(dateString, 'dateTime');
            setFormattedDate(formatted);
        }
    }, [popupData.isCertificateAvailable, popupData.certificateUploadDateTime, popupData, getPartnerType]);

    const errorcustomStyle = {
        outerDiv: "!flex !justify-center !inset-0",
        innerDiv: "!flex !justify-between !items-center !rounded-none !bg-moderate-red !md:w-[25rem] !w-full !min-h-[3.2rem] !h-fit !px-4 !py-[10px]"
    }

    const successcustomStyle = {
        outerDiv: "!flex !justify-center !inset-0",
        innerDiv: "!flex !justify-between !items-center !rounded-none !md:w-[25rem] !w-full !min-h-[3.2rem] !h-fit !px-4 !py-[10px]"
    }

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-35 z-50 !mx-0 break-words">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className={`bg-white break-normal md:w-[25rem] w-[60%] mx-auto ${popupData.isCertificateAvailable ? 'min-h-[28rem]' : 'min-h-[27rem]'} rounded-lg shadow-lg h-fit`}>
                    {!dataLoaded && (
                        <div className="flex items-center h-[30rem]">
                            <LoadingIcon></LoadingIcon>
                        </div>
                    )}
                    {dataLoaded && (
                        <>
                            <div className="px-[3.5%] py-[2%]">
                                <h3 className="text-base font-bold text-[#333333]">{popupData.isCertificateAvailable ? t(popupData.reUploadHeader) : t(popupData.uploadHeader)}</h3>
                                <p className="text-sm text-[#717171]">{t('uploadCertificate.selectFieldsMsg')}</p>
                            </div>
                            <div className="border-gray-200 border-opacity-75 border-t"></div>
                            <div className="relative">
                                {uploadFailure && errorMsg && (
                                    <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={errorcustomStyle} />
                                )}
                                {uploadSuccess && successMsg && (
                                    <SuccessMessage successMsg={successMsg} clickOnCancel={cancelSuccessMsg} customStyle={successcustomStyle} />
                                )}
                                <div className="px-[4%] py-[2%]">
                                    <form>
                                        <div className="mb-2">
                                            <label className="block text-dark-blue text-base font-semibold mb-1">{t('uploadCertificate.partnerTypeLabel')}</label>
                                            <input type="text" className="w-full h-10 px-3 py-2 border border-[#C1C1C1] rounded-md text-base text-gunmetal-gray bg-[#EBEBEB] leading-tight focus:outline-none focus:shadow-outline"
                                                value={partnerType} disabled />
                                        </div>
                                        <div className="mb-3">
                                            <label className="block text-dark-blue text-base font-semibold mb-1">{t('uploadCertificate.partnerDomainType')}</label>
                                            <input type="text" className="w-full h-10 px-3 py-2 border border-[#C1C1C1] rounded-md text-base text-gunmetal-gray bg-[#EBEBEB] leading-tight focus:outline-none focus:shadow-outline"
                                                value={partnerDomainType} disabled />
                                        </div>
                                    </form>
                                    <div className="flex items-center p-3 justify-center w-full min-h-36 h-fit border-2 border-[#9CB2E0] rounded-xl bg-[#F8FBFF] bg-opacity-100 text-center cursor-pointer relative">
                                        {uploading && (
                                            <div className={`flex flex-col items-center justify-center mb-1 cursor-pointer`}>
                                                <svg aria-hidden="true" className="w-8 h-8 text-gray-200 animate-spin fill-blue-800" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                                    <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor" />
                                                    <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill" />
                                                </svg>
                                                <h5 className="text-charcoal-gray text-sm font-semibold">
                                                    {t('uploadCertificate.selectingFile')}
                                                </h5>
                                                <button className="text-sm font-semibold text-tory-blue" onClick={cancelUpload}>
                                                    <p> {t('uploadCertificate.cancel')} </p>
                                                </button>
                                            </div>
                                        )}
                                        {!uploading && fileName === '' && (
                                            <div id='upload_certificate_card' className={`flex flex-col items-center justify-center w-full min-h-36 cursor-pointer`}>
                                                <button onKeyDown={(e) => (e.key === 'Enter' || e.key === ' ') && document.getElementById('fileInput').click()}>
                                                    <label htmlFor="fileInput" className="flex flex-col items-center w-full min-h-36 justify-center cursor-pointer" >
                                                        <img src={fileUploadImg} alt="" className="mb-2 w-10 h-10" />
                                                        <h5 className="text-charcoal-gray text-base font-normal px-2">
                                                            {t('uploadCertificate.selectCertificate')}
                                                        </h5>
                                                        <p className="text-xs text-light-gray px-2">
                                                            {t('uploadCertificate.certificateFormat')}
                                                        </p>
                                                    </label>
                                                </button>

                                                <input id="fileInput" type="file" className="hidden" accept=".cer,.pem" onChange={handleFileChange} />
                                            </div>
                                        )}
                                        {!uploading && fileName && (
                                            <div id='remove_certificate_card' className={`flex flex-col items-center justify-center mb-1 cursor-pointer`}>
                                                <label
                                                    type="input"
                                                    htmlFor="fileInput"
                                                    className="flex flex-col items-center justify-center cursor-pointer"
                                                >
                                                    <img src={fileDescription} alt="" className="w-10 h-10 mb-1" />
                                                </label>
                                                <h5 className="text-charcoal-gray text-sm font-semibold break-all">
                                                    {fileName}
                                                </h5>
                                                {!uploadSuccess && (
                                                    <button id='remove_certificate_btn' className="text-sm font-semibold text-tory-blue" onClick={removeUpload}>
                                                        <p>{t('uploadCertificate.remove')}</p>
                                                    </button>
                                                )}
                                            </div>
                                        )}
                                    </div>
                                    {popupData.isCertificateAvailable && !removeLastUploadDate && (
                                       <p className="text-sm text-gray-800 text-center mt-1">
                                       {t('uploadCertificate.lastcertificateUploadDate')}{' '}
                                       <span className="whitespace-nowrap">{formattedDate}</span>
                                     </p>
                                    )}
                                </div>
                                <div className="border-gray-200 border-opacity-50 border-t"></div>
                                <div className="px-[4%] flex justify-center mt-2 my-3">
                                    <button id='certificate_upload_cancel_btn' disabled={uploadSuccess} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-36 h-10 ${uploadSuccess ? 'border-[#A5A5A5] bg-[#A5A5A5] text-white' : 'border-[#1447B2] bg-white text-tory-blue'}  border rounded-md text-sm font-semibold relative z-10`} onClick={clickOnCancel}>{t('uploadCertificate.cancel')}</button>
                                    {(!uploading && fileName) ? (
                                        <button id={uploadSuccess ? "certificate_upload_close_btn" : "certificate_upload_submit_btn"} className="w-36 h-10 border-[#1447B2] border bg-tory-blue rounded-md text-white text-sm font-semibold relative z-10" onClick={clickOnSubmit}>{uploadSuccess ? t('uploadCertificate.close') : t('uploadCertificate.submit')}</button>
                                    ) : (
                                        <button disabled className="w-36 h-10 border-[#A5A5A5] border bg-[#A5A5A5] rounded-md text-white text-sm font-semibold">{t('uploadCertificate.submit')}</button>
                                    )}
                                </div>
                            </div>
                        </>
                    )}
                </div>
            </FocusTrap>
        </div>

    );
}

export default UploadCertificate;