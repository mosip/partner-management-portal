import React, { useEffect, useState, } from 'react';
import { useTranslation } from 'react-i18next';
import { createRequest, getPartnerManagerUrl, isLangRTL, handleServiceErrors } from "../../../utils/AppUtils";
import { getUserProfile } from '../../../services/UserProfileService';
import Title from '../../common/Title'; import DropdownComponent from "../../common/fields/DropdownComponent";
import fileUploadImg from '../../../svg/file_upload_certificate.svg';
import fileDescription from '../../../svg/file_description.svg';
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from '../../common/ErrorMessage';
import { HttpService } from '../../../services/HttpService';
import Confirmation from "../../common/Confirmation";

function UploadRootTrustCertificate() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [uploading, setUploading] = useState(false);
    const [selectedDomain, setSelectedDomain] = useState("");
    const [certificateData, setCertificateData] = useState("");
    const [fileName, setFileName] = useState('');
    const [uploadSuccess, setUploadSuccess] = useState(false);
    const [uploadFailure, setUploadFailure] = useState(false);
    const [confirmationData, setConfirmationData] = useState({});

    const cancelUpload = () => {
        setFileName("");
        setCertificateData("");
        setSelectedDomain("");;
    };

    const removeUpload = () => {
        setFileName("");
        setCertificateData("");
        setUploading(false);
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
        setErrorCode("");
    };

    const isFormValid = () => {
        return selectedDomain !== "" && certificateData !== "" && !uploading;
    };

    const clickOnSubmit = async () => {
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        let request = createRequest({
            certificateData: certificateData,
            partnerDomain: selectedDomain
        });
        try {
            const response = await HttpService.post(getPartnerManagerUrl(`/partners/certificate/ca/upload`, process.env.NODE_ENV), request, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            if (response) {
                const responseData = response.data;
                console.log(responseData)
                if (responseData && responseData.response) {
                    const resData = responseData.response;
                    const successMessage = t('uploadRootofTrustCertificate.successMsg', { partnerDomain: selectedDomain });
                    const requiredData = {
                        backUrl: "/partnermanagement/admin/certificates/root-trust-certificate-list",
                        header: successMessage,
                    }
                    setConfirmationData(requiredData);
                    setUploadSuccess(true);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('uploadCertificate.errorWhileUploadingCertificate'));
            }
            setDataLoaded(true);
        } catch (err) {
            setErrorMsg(err);
            console.log("Error while uploading certificate: ", err);
        }
    }

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

    const onDomainChangeEvent = (fieldName, value) => {
        setSelectedDomain(value);
    };

    const uploadCertificateDropdownStyle = {
        outerDiv: "!ml-0",
        dropdownButton: "!text-light-grat !w-[23rem] !h-[2.6rem] !text-[1rem]",
        dropdownLabel: "!text-[1.03rem]"
    }

    const style = {
        backArrowIcon: "!mt-[9%]",
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between">
                            <Title title="uploadRootofTrustCertificate.uploadRootofTrustCertificate" subTitle='viewRootOfTrustCertificate.viewRootOfTrustCertificate' backLink="/partnermanagement/admin/certificates/root-trust-certificate-list" />
                        </div>
                        <div className="flex-col justify-center mt-3 h-full">
                            {!uploadSuccess ?
                                <div className="w-[100%] bg-snow-white mt-[1.5%] rounded-lg shadow-md p-1">
                                    <div className={`flex-col text-center text-base my-5`}>
                                        <h1 className='font-semibold text-dark-blue'>{t('uploadRootofTrustCertificate.uploadRootofTrustCertificate')}</h1>
                                        <p className='text-light-gray'>{t('uploadRootofTrustCertificate.uploadRootofTrustCertificateDesc')}</p>
                                    </div>
                                    <div className='flex-col w-full'>
                                        <div className='flex justify-center'>
                                            <div className='outline-[#1447b2] outline-offset-2 outline-2 rounded-md outline-dashed'>
                                                <div className={`flex-col p-6 border-2 bg-[#f9fafb] my-5 mx-4 rounded-xl justify-center items-center`}>
                                                    <DropdownComponent
                                                        fieldName='partnerDomain'
                                                        fieldNameKey='uploadRootofTrustCertificate.partnerDomain*'
                                                        onDropDownChangeEvent={onDomainChangeEvent}
                                                        dropdownDataList={[
                                                            { fieldValue: 'FTM', fieldCode: 'FTM' },
                                                            { fieldValue: 'DEVICE', fieldCode: 'DEVICE' },
                                                            { fieldValue: 'AUTH', fieldCode: 'AUTH' }
                                                        ]}
                                                        placeHolderKey={'uploadRootofTrustCertificate.dropdownPlaceholder'}
                                                        selectedDropdownValue={selectedDomain}
                                                        isPlaceHolderPresent={false}
                                                        styleSet={uploadCertificateDropdownStyle}
                                                        id='partnerDomain_selector_dropdown'
                                                    />
                                                    <div className={`flex items-center justify-center w-[23rem] mt-[1.5rem] min-h-40 h-fit border-2 border-[#9CB2E0] rounded-xl bg-[#F8FBFF] bg-opacity-100 text-center cursor-pointer`}>
                                                        {uploading && (
                                                            <div className={`flex flex-col items-center justify-center mb-1 cursor-pointer`}>
                                                                <svg aria-hidden="true" className="w-8 h-8 text-gray-200 animate-spin fill-blue-800" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                                                    <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor" />
                                                                    <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill" />
                                                                </svg>
                                                                <h5 className="text-charcoal-gray text-sm font-semibold">
                                                                    {t('uploadCertificate.selectingFile')}
                                                                </h5>
                                                                <button
                                                                    id="remove_certificate_btn"
                                                                    className="text-sm font-semibold text-tory-blue"
                                                                    onClick={removeUpload}
                                                                    onKeyPress={(e) => {
                                                                        if (e.key === 'Enter' || e.key === ' ') removeUpload();
                                                                    }}
                                                                >
                                                                    {t('uploadCertificate.cancel')}
                                                                </button>
                                                            </div>
                                                        )}
                                                        {!uploading && fileName === '' && (
                                                            <div id="upload_certificate_card" className="flex flex-col items-center justify-center w-full min-h-36 cursor-pointer">
                                                                <button
                                                                    type="button"
                                                                    onClick={() => document.getElementById('fileInput').click()}
                                                                    className="flex flex-col items-center w-full min-h-36 justify-center"
                                                                >
                                                                    <img src={fileUploadImg} alt="" className="mb-3 w-10 h-10" />
                                                                    <h5 className="text-charcoal-gray text-sm font-medium">
                                                                        {t('uploadCertificate.selectCertificate')}
                                                                    </h5>
                                                                    <p className="text-xs text-light-gray">
                                                                        {t('uploadCertificate.certificateFormat')}
                                                                    </p>
                                                                </button>
                                                                <input
                                                                    id="fileInput"
                                                                    type="file"
                                                                    className="hidden"
                                                                    accept=".cer,.pem"
                                                                    onChange={handleFileChange}
                                                                />
                                                            </div>
                                                        )}
                                                        {!uploading && fileName && (
                                                            <div id="remove_certificate_card" className="flex flex-col items-center justify-center mb-1 cursor-pointer">
                                                                <label
                                                                    htmlFor="fileInput"
                                                                    className="flex flex-col items-center justify-center cursor-pointer"
                                                                    aria-label={t('uploadCertificate.selectCertificate')}
                                                                >
                                                                    <img src={fileDescription} alt="" className="w-10 h-10 mb-3" />
                                                                </label>
                                                                <h5 className="text-charcoal-gray text-sm font-semibold">
                                                                    {fileName}
                                                                </h5>
                                                                <button
                                                                    id="remove_certificate_btn"
                                                                    className="text-sm font-semibold text-tory-blue"
                                                                    onClick={removeUpload}
                                                                    onKeyPress={(e) => {
                                                                        if (e.key === 'Enter' || e.key === ' ') removeUpload();
                                                                    }}
                                                                >
                                                                    {t('uploadCertificate.remove')}
                                                                </button>
                                                            </div>
                                                        )}
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr className="border bg-medium-gray mt-[2rem]" />
                                        <div className={`flex flex-row max-[450px]:flex-col space-x-3 max-[450px]:space-x-0 max-[450px]:space-y-2 max-[650px]:flex-col w-full md:w-auto justify-end p-[1rem]`}>
                                            <button id="upload_admin_certificate_cancel_btn" onClick={cancelUpload} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-[10%] min-w-20 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('commons.cancel')}</button>
                                            <button disabled={!isFormValid()} id="upload_admin_certificate_btn" onClick={clickOnSubmit} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-[10%] min-w-20 h-10 ${!isFormValid() ? 'bg-[#a9abae] border-[#b1b3b6] text-[#f9fafa]' : 'border-[#1447B2] bg-tory-blue text-white'}  border rounded-md text-sm font-semibold`}>{t('commons.submit')}</button>
                                        </div>
                                    </div>
                                </div>
                                : <Confirmation confirmationData={confirmationData} />
                            }
                        </div>
                    </div>
                </>
            )}
        </div >
    )
}

export default UploadRootTrustCertificate;