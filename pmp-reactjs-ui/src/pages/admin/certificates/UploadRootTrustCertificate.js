import React, { useState, } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { createDropdownData, createRequest, getPartnerManagerUrl, isLangRTL, onPressEnterKey } from "../../../utils/AppUtils";
import { getUserProfile } from '../../../services/UserProfileService';
import Title from '../../common/Title'; import DropdownComponent from "../../common/fields/DropdownComponent";
import file from '../../../svg/file_icon.svg';
import fileUploadImg from '../../../svg/file_upload_certificate.svg';
import fileDescription from '../../../svg/file_description.svg';
import SuccessMessage from "../../common/SuccessMessage";
import file_uploaded_successful from '../../../svg/file_uploaded_successful_icon.svg';
import ErrorMessage from '../../common/ErrorMessage';
import { HttpService } from '../../../services/HttpService';

function UploadRootTrustCertificate() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [uploading, setUploading] = useState(false);
    const [selectedDomain, setSelectedDomain] = useState('');
    const [certificateData, setCertificateData] = useState('');
    const [removeLastUploadData, setRemoveLastUploadData] = useState(false);
    const [fileName, setFileName] = useState('');
    const [uploadSuccess, setUploadSuccess] = useState(false);
    const [uploadFailure, setUploadFailure] = useState(false);

    const cancelUpload = () => {
        setFileName("");
        setUploading(false);
        setSelectedDomain('');
    };

    const submit = () => {
        setFileName("");
        setUploading(false);
        navigate('/partnermanagement/admin/certificates/rootTrustCertificateList')
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

    const certificateSubmit = async (certificateData, selectedDomain) => {
        setSuccessMsg("");
        setErrorCode("");
        const uploadRequest = createRequest({
            certificateData: certificateData,
            partnerDomain: selectedDomain
        });
        try {
            let response;
            response = await HttpService.post(getPartnerManagerUrl('/partners/certificate/ca/upload', process.env.NODE_ENV), uploadRequest)
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
                    setSuccessMsg({partnerDomain: selectedDomain}, 'uploadRootofTrustCertificate.successMsg');
                }
            } else {
                setUploadFailure(true);
                setErrorMsg(t('uploadCertificate.errorWhileUploadingCertificate'));
            }
        } catch (err) {
            setUploadFailure(true);
            setErrorMsg(err);
            console.log("Unable to upload partner certificate: ", err);
        }
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
                certificateSubmit(certificateData, selectedDomain);
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
        dropdownButton: "!text-[#343434] !w-[23rem]",
        dropdownLabel: "!mb-1 !mt-5"
    }

    const errorcustomStyle = {
        outerDiv: "!flex !justify-center !inset-0",
        innerDiv: "!flex !justify-between !items-center !rounded-none !bg-moderate-red !w-full !min-h-[3rem] !h-fit !px-4 !py-[10px]"
    }

    const successcustomStyle = {
        outerDiv: "!flex !justify-center !inset-0",
        innerDiv: "!flex !justify-between !items-center !rounded-none !w-full !min-h-[3rem] !h-fit !px-4 !py-[10px]"
    }

    const style = {
        backArrowIcon: "!mt-[9%]",
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
            <div className="flex-col mt-7">
                <Title title="uploadRootofTrustCertificate.uploadRootofTrustCertificate" backLink="/partnermanagement" styleSet={style} />
                <div className="flex-col justify-center mt-3 h-full">
                    <div className="w-[100%] bg-snow-white rounded-lg shadow-md p-[1%]">
                        <div className="m-[1%] shadow-md rounded-lg">
                            <div className={`flex items-center shadow-lg rounded-lg justify-between`}>
                                <div className="flex-col items-center w-full">
                                    <div className={`flex items-center ${uploadSuccess && fileName ? "bg-[#F4FAF4]" : "bg-[#edf2fc]"} p-[0.5rem]`}>
                                        {uploadSuccess && fileName
                                            ? <img src={file_uploaded_successful} className="h-8" alt="" />
                                            : <img src={file} className="h-8" alt="" />
                                        }
                                        <div className="flex-col p-3 items-center">
                                            <h6>
                                                {!uploading && fileName ? fileName : t('uploadRootofTrustCertificate.uploadRootofTrustCertificate')}
                                            </h6>
                                            {uploading && <p className="text-xs text-light-gray">{t('partnerCertificatesList.certificateFormatMsg')}</p>}
                                        </div>
                                    </div>
                                    <hr className="border bg-medium-gray " />
                                    <div className="flex items-center p-3 bg-white rounded-lg gap-x-10">
                                        <div className="flex-col">
                                            <p className="font-semibold text-xs text-dim-gray">{t('uploadRootofTrustCertificate.partnerDomain')}</p>
                                            <p className="font-semibold text-xs text-charcoal-gray">{!uploadSuccess && selectedDomain}</p>
                                        </div>
                                        <div className={`flex-col ${isLoginLanguageRTL ? "mr-[5%]" : "ml-[5%]"}`}>
                                            <p className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.expiryDate')}</p>
                                            <p className="font-semibold text-xs text-charcoal-gray">-</p>
                                        </div>
                                        <div className={`flex-col ${isLoginLanguageRTL ? "mr-[10%]" : "ml-[10%]"}`}>
                                            <p className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.timeOfUpload')}</p>
                                            <p className="font-semibold text-xs text-charcoal-gray">-</p>
                                        </div>
                                    </div>
                                    <div className="relative">
                                        {uploadFailure && errorMsg && (
                                            <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={errorcustomStyle} />
                                        )}
                                        {uploadSuccess && successMsg && (
                                            <SuccessMessage successMsg={successMsg} clickOnCancel={cancelSuccessMsg} customStyle={successcustomStyle} />
                                        )}
                                    </div>
                                </div>
                            </div>
                            <div className={`flex-col pt-[0.5rem] justify-center w-[47.5rem] ${isLoginLanguageRTL ? 'pr-[31%]' : 'pl-[31%]'}`}>
                                <DropdownComponent
                                    fieldName='partnerDomain'
                                    fieldNameKey='uploadRootofTrustCertificate.partnerDomain'
                                    onDropDownChangeEvent={onDomainChangeEvent}
                                    dropdownDataList={[
                                        { fieldValue: 'FTM', fieldCode: 'FTM' },
                                        { fieldValue: 'DEVICE', fieldCode: 'DEVICE' },
                                        { fieldValue: 'AUTH', fieldCode: 'AUTH' }
                                    ]}
                                    placeHolderKey={selectedDomain ? selectedDomain : 'uploadRootofTrustCertificate.dropdownPlaceholder'}
                                    isPlaceHolderPresent={true}
                                    styleSet={uploadCertificateDropdownStyle}
                                    id='partnerDomain_selector_dropdown'
                                />
                                <div className={`flex items-center justify-center w-[23rem] mt-[1.5rem] min-h-40 h-fit border-2 border-[#9CB2E0] rounded-xl bg-[#F8FBFF] bg-opacity-100 text-center cursor-pointer ${isLoginLanguageRTL ? 'mr-[0.2rem]' : 'ml-[1rem]'}`}>
                                    {uploading && (
                                        <div className={`flex flex-col items-center justify-center mb-1 cursor-pointer`}>
                                            <svg aria-hidden="true" className="w-8 h-8 text-gray-200 animate-spin fill-blue-800" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                                <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor" />
                                                <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill" />
                                            </svg>
                                            <h5 className="text-charcoal-gray text-sm font-semibold">
                                                {t('uploadCertificate.selectingFile')}
                                            </h5>
                                            <p className="text-sm font-semibold text-tory-blue">
                                                {t('uploadCertificate.cancel')}
                                            </p>
                                        </div>
                                    )}
                                    {!uploading && fileName === '' && (
                                        <div id='upload_certificate_card' className={`flex flex-col items-center justify-center w-full min-h-36 cursor-pointer`}>
                                            <label htmlFor="fileInput" tabIndex={0} onKeyPress={(e) => (e.key === 'Enter' || e.key === ' ') && document.getElementById('fileInput').click()} className="flex flex-col items-center w-full min-h-36 justify-center cursor-pointer">
                                                <img src={fileUploadImg} alt="" className="mb-2 w-10 h-10" />
                                                <h5 className="text-charcoal-gray text-base font-normal">
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
                                        <div id='remove_certificate_card' className={`flex flex-col items-center justify-center mb-1 cursor-pointer`}>
                                            <label htmlFor="fileInput" className="flex flex-col items-center justify-center cursor-pointer">
                                                <img src={fileDescription} alt="" className="w-10 h-10 mb-1" />
                                            </label>
                                            <h5 className="text-charcoal-gray text-sm font-semibold">
                                                {fileName}
                                            </h5>
                                            <p id='remove_certificate_btn' className="text-sm font-semibold text-tory-blue" onClick={removeUpload}>
                                                {t('uploadCertificate.remove')}
                                            </p>
                                        </div>
                                    )}
                                </div>
                            </div>
                            <hr className="border bg-medium-gray mt-[2rem]" />
                            <div className={`flex flex-row max-[450px]:flex-col space-x-3 max-[450px]:space-x-0 max-[450px]:space-y-2 w-full md:w-auto justify-end p-[1.5rem]`}>
                                <button id="upload_admin_certificate_cancel_btn" onClick={cancelUpload} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-/12 md:w-24 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('commons.cancel')}</button>
                                <button disabled={!fileName} id="upload_admin_certificate_btn" onClick={submit} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-8/12 md:w-24 h-10 ${!fileName ? 'bg-[]#cfd1d4] border-[#cfd1d4] text-[#65676b]' : 'border-[#1447B2] bg-tory-blue text-white'}  border rounded-md text-sm font-semibold`}>{t('commons.submit')}</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div >
    )
}

export default UploadRootTrustCertificate;