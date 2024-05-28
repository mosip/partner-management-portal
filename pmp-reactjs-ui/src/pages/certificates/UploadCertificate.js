import { useState, useEffect } from 'react';
import { HttpService } from "../../services/HttpService";
import { formatDate, getPartnerTypeDescription, getPartnerManagerUrl } from '../../utils/AppUtils';
import { useTranslation } from 'react-i18next';
import ErrorMessage from "../common/ErrorMessage";
import LoadingIcon from "../common/LoadingIcon";
import SuccessMessage from '../common/SuccessMessage';

import fileUploadImg from '../../svg/file_upload_certificate.svg';
import fileDescription from '../../svg/file_description.svg';

function UploadCertificate({ closePopup, partnerData }) {
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const [selectedDomainType, setSelectedDomainType] = useState("");
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
    const { t } = useTranslation();

    const openDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };
    const clickOnCancel = () => {
        closePopup(true, "cancel");
    };
    const clickOnSubmit = async () => {
        if (uploadSuccess) {
            closePopup(true, "close");
        } else {
            setDataLoaded(false);
            let request = {
                request: {
                    partnerId: partnerData.partnerId,
                    certificateData: certificateData,
                    partnerDomain: selectedDomainType,
                },
            }
            try {
                const response = await HttpService.post(getPartnerManagerUrl('/partners/certificate/upload', process.env.NODE_ENV), request)
                if (response !== null) {
                    const resData = response.data.response;
                    if (response.data.errors && response.data.errors.length > 0) {
                        const errorCode = response.data.errors[0].errorCode;
                        const errorMessage = response.data.errors[0].message;
                        setUploadFailure(true);
                        setErrorCode(errorCode)
                        setErrorMsg(errorMessage);
                    } else if (resData === null) {
                        setUploadFailure(true);
                        setErrorMsg(t('uploadCertificate.unableToUploadCertificate'));
                    } else {
                        setUploadSuccess(true);
                        setSuccessMsg(t('uploadCertificate.successMsg', { partnerType: getPartnerType(partnerData) }));
                    }
                } else {
                    setUploadFailure(true);
                    setErrorMsg(t('uploadCertificate.errorWhileUploadingCertificate'));
                }
                setDataLoaded(true);
            } catch (err) {
                setUploadFailure(true);
                setErrorMsg(err);
                console.log("Unable to upload partner certificate: ", err);
            }
        }
    };

    const getPartnerType = (partner) => {
        if (partner.partnerType) {
            const partnerTypeDesc = getPartnerTypeDescription(partner.partnerType, t);
            return partnerTypeDesc;
        }
    }

    const selectDomainType = (option) => {
        setSelectedDomainType(option);
        openDropdown();
    };
    const setDefaultDomainType = () => {
        if (partnerData.partnerType === "Device_Provider") {
            setSelectedDomainType("DEVICE");
        } else if (partnerData.partnerType === "FTM_Provider") {
            setSelectedDomainType("FTM");
        } else {
            setSelectedDomainType("AUTH");
        }
    };
    const cancelUpload = () => {
        setFileName("");
        setUploading(false);
    };
    const removeUpload = () => {
        setFileName("");
        setUploading(false);
    };
    const cancelErrorMsg = () => {
        setErrorMsg("");
    };
    const cancelSuccessMsg = () => {
        setSuccessMsg("");
    };
    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const fileName = file.name;
            const fileExtension = fileName.split('.').pop().toLowerCase();
            if (fileExtension === 'cer' || fileExtension === 'pem') {
                const reader = new FileReader();
                reader.onload = (e) => {
                    const fileData = e.target.result;
                    setUploading(true);
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
        if (partnerData.isCertificateAvailable && partnerData.certificateUploadDate) {
            const dateString = partnerData.certificateUploadDate.toString();
            const formatted = formatDate(dateString, 'dateTime');
            setFormattedDate(formatted);
        }
    }, [partnerData.isCertificateAvailable, partnerData.certificateUploadDate]);

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-[30%] z-50">
            <div className={`bg-white md:w-[35%] w-[55%] mx-auto ${partnerData.isCertificateAvailable ? 'h-[89%]' : 'h-[84%]'} rounded-lg shadow-lg mt-5`}>
                {!dataLoaded && (
                    <LoadingIcon></LoadingIcon>
                )}
                {dataLoaded && (
                    <>
                        <div className="px-[4.5%] py-[3%]">
                            <h3 className="text-base font-bold text-[#333333]">{partnerData.isCertificateAvailable ? t('uploadCertificate.reUploadPartnerCertificate') : t('uploadCertificate.uploadPartnerCertificate')}</h3>
                            <p className="text-sm text-[#717171]">{t('uploadCertificate.selectFieldsMsg')}</p>
                        </div>
                        <div className="border-gray-200 border-opacity-75 border-t"></div>
                        <div className="px-[6%] py-[3%]">
                            <form>
                                <div className="mb-3">
                                    <label className="block text-dark-blue text-base font-semibold mb-1">{t('uploadCertificate.partnerTypeLabel')}</label>
                                    <input type="text" className="w-full h-12 px-3 py-2 border border-[#C1C1C1] rounded-md text-md text-gunmetal-gray bg-[#EBEBEB] leading-tight focus:outline-none focus:shadow-outline"
                                        value={getPartnerType(partnerData)} disabled />
                                </div>
                                <div className="mb-3">
                                    <label className="block text-dark-blue text-base font-semibold mb-1">{t('uploadCertificate.partnerDomainTypeLabel')}<span className="text-red-500">*</span></label>
                                    <div className="relative z-10">
                                        <button onClick={openDropdown} className="flex items-center justify-between w-full h-12 px-3 py-2 border border-gray-400 rounded-md text-md text-start text-gunmetal-gray leading-tight focus:outline-none focus:shadow-none" type="button">
                                            <span>{selectedDomainType || setDefaultDomainType()}</span>
                                            <svg className={`w-3 h-2 ml-3 transform ${isDropdownOpen ? 'rotate-180' : 'rotate-0'} text-gray-500 text-sm`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                            </svg>
                                        </button>
                                        {isDropdownOpen && (
                                            <div className="absolute z-50 top-12 left-0 w-full">
                                                <div className="z-10 border border-gray-400 bg-white rounded-lg shadow-lg w-full dark:bg-gray-700 cursor-pointer">
                                                    <button className={`block w-full px-4 py-2 text-left text-base text-dark-blue
                                                        ${selectedDomainType === "DEVICE" ? 'bg-gray-100' : 'hover:bg-gray-100'}`}
                                                        onClick={() => selectDomainType("DEVICE")}>DEVICE
                                                    </button>
                                                    <div className="border-gray-200 border-t mx-2"></div>
                                                    <button className={`block w-full px-4 py-2 text-left text-base text-dark-blue
                                                        ${selectedDomainType === "FTM" ? 'bg-gray-100' : 'hover:bg-gray-100'}`}
                                                        onClick={() => selectDomainType("FTM")}>FTM
                                                    </button>
                                                    <div className="border-t border-gray-200 mx-2"></div>
                                                    <button className={`block w-full px-4 py-2 text-left text-base text-dark-blue
                                                        ${selectedDomainType === "AUTH" ? 'bg-gray-100' : 'hover:bg-gray-100'}`}
                                                        onClick={() => selectDomainType("AUTH")}>AUTH
                                                    </button>
                                                </div>
                                            </div>
                                        )}
                                    </div>
                                </div>
                            </form>
                            <div className="flex items-center justify-center mt-[4%] w-full h-36 p-4 border-2 border-[#9CB2E0] rounded-xl bg-[#F8FBFF] bg-opacity-100 text-center cursor-pointer relative">
                                {uploading && (
                                    <div className={`flex flex-col items-center justify-center mb-2 cursor-pointer ${!isDropdownOpen ? 'z-10' : 'z-0'}`}>
                                        <svg aria-hidden="true" className="w-9 h-9 text-gray-200 animate-spin fill-blue-800" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                            <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor" />
                                            <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill" />
                                        </svg>
                                        <h5 className="text-charcoal-gray text-base font-medium">
                                            {t('uploadCertificate.selectingFile')}
                                        </h5>
                                        <p className="text-sm font-semibold text-tory-blue" onClick={cancelUpload}>
                                            {t('uploadCertificate.cancel')}
                                        </p>
                                    </div>
                                )}
                                {!uploading && fileName === '' && (
                                    <div className={`flex flex-col items-center justify-center mb-2 cursor-pointer ${!isDropdownOpen ? 'z-10' : 'z-0'}`}>
                                        <label htmlFor="fileInput" className="flex flex-col items-center justify-center cursor-pointer">
                                            <img src={fileUploadImg} alt="" className="mb-2 w-10 h-10"/>
                                            <h5 className="text-charcoal-gray text-base font-medium">
                                                {t('uploadCertificate.selectCertificate')}
                                            </h5>
                                            <p className="text-xs text-light-gray">
                                                {t('uploadCertificate.certificateFormat')}
                                            </p>
                                        </label>
                                        <input id="fileInput" type="file" className="hidden" accept=".cer,.pem" onChange={handleFileChange} />
                                    </div>
                                )}
                                {!uploading && fileName && (
                                    <div className={`flex flex-col items-center justify-center mb-2 cursor-pointer ${!isDropdownOpen ? 'z-10' : 'z-0'}`}>
                                        <label htmlFor="fileInput" className="flex flex-col items-center justify-center cursor-pointer">
                                            <img src={fileDescription} alt="" className="w-10 h-10 mb-2" />
                                        </label>
                                        <h5 className="text-charcoal-gray text-base font-medium">
                                            {fileName}
                                        </h5>
                                        <p className="text-sm font-semibold text-tory-blue" onClick={removeUpload}>
                                            {t('uploadCertificate.remove')}
                                        </p>
                                    </div>
                                )}
                            </div>
                            {partnerData.isCertificateAvailable && (
                                <p className="text-sm text-gray-800 text-center mt-1">{t('uploadCertificate.lastcertificateUploadDate', { date: formattedDate })}</p>
                            )}
                        </div>
                        <div className="border-gray-200 border-opacity-50 border-t"></div>
                        <div className="p-[4%] flex justify-end relative">
                            <button className="mr-2 w-40 h-12 border-[#1447B2] border rounded-md bg-white text-tory-blue text-base font-semibold relative z-10" onClick={clickOnCancel}>{t('uploadCertificate.cancel')}</button>
                            {(!uploading && fileName) ? (
                                <button className="w-40 h-12 border-[#1447B2] border bg-tory-blue rounded-md text-white text-base font-semibold relative z-10" onClick={clickOnSubmit}>{uploadSuccess ? t('uploadCertificate.close') : t('uploadCertificate.submit')}</button>
                            ) : (
                                <button disabled className="w-40 h-12 border-[#A5A5A5] border bg-[#A5A5A5] rounded-md text-white text-base font-semibold">{t('uploadCertificate.submit')}</button>
                            )}
                            {uploadSuccess && successMsg && (
                                <div className="fixed inset-0 flex mt-[122px] justify-center">
                                    <div className="bg-fruit-salad md:w-[400px] w-[60%] h-[50px] flex items-center justify-between p-4">
                                        <SuccessMessage successMsg={successMsg} clickOnCancel={cancelSuccessMsg}></SuccessMessage>
                                    </div>
                                </div>
                            )}
                            {uploadFailure && errorMsg && (
                                <div className="fixed inset-0 flex mt-[122px] justify-center">
                                    <div className="bg-moderate-red md:w-[400px] w-[60%] h-[50px] flex items-center justify-between p-4">
                                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                                    </div>
                                </div>
                            )}
                        </div>
                    </>
                )}
            </div>
        </div>

    );
}

export default UploadCertificate;