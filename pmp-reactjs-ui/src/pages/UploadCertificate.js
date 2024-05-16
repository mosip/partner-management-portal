import { useState, useEffect } from 'react';
import HttpService from '../services/HttpService';
import { formatDate, getPartnerTypeDescription } from '../utils/AppUtils';
import { useTranslation } from 'react-i18next';

function UploadCertificate({ closePopup, partnerData }) {
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const [selectedDomainType, setSelectedDomainType] = useState("");
    const [uploading, setUploading] = useState(false);
    const [fileName, setFileName] = useState('');
    const [uploadSuccess, setUploadSuccess] = useState(false);
    const [uploadFailure, setUploadFailure] = useState(false);
    const [errorMsg, setErrorMsg] = useState("");
    const [certificateData, setCertificateData] = useState("");
    const [formattedDate, setFormattedDate] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const { t } = useTranslation();

    const openDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };
    const clickOnCancel = () => {
        closePopup(false);
    };
    const clickOnSubmit = async () => {
        if (uploadSuccess) {
            closePopup(true);
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
                const response = await HttpService.post('/api/partners/certificate/upload', request)
                if (response !== null) {
                    const resData = response.data.response;
                    if (response.data.errors && response.data.errors.length > 0) {
                        const errorMessage = response.data.errors[0].message;
                        setUploadFailure(true);
                        setErrorMsg(errorMessage);
                    } else if (resData === null) {
                        setUploadFailure(true);
                        setErrorMsg(t('uploadCertificate.unableToUploadCertificate'));
                    } else {
                        setUploadSuccess(true);
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
        setUploadFailure(false);
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
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
            <div className={`bg-white md:w-[400px] w-[60%] mx-auto ${partnerData.isCertificateAvailable ? 'h-[520px]' : 'h-[500px]'} rounded-lg shadow-lg mt-5`}>
                {!dataLoaded && (
                    <div className="flex items-center justify-center h-full">

                        <div role="status" className="flex items-center">
                            <svg aria-hidden="true" className="w-8 h-8 text-gray-200 animate-spin fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor" />
                                <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill" />
                            </svg>
                            <p className="ml-3">{t('partnerCertificatesList.loading')}</p>
                        </div>

                    </div>
                )}
                {dataLoaded && (
                    <>
                        <div className="px-4 py-3">
                            <h3 className="text-md font-bold text-gray-900">{partnerData.isCertificateAvailable ? t('uploadCertificate.reUploadPartnerCertificate') : t('uploadCertificate.uploadPartnerCertificate')}</h3>
                            <p className="text-sm text-gray-600">{t('uploadCertificate.selectFieldsMsg')}</p>
                        </div>
                        <div className="border-gray-200 border-opacity-75 border-t"></div>
                        <div className="p-3">
                            <form>
                                <div className="mb-4">
                                    <label className="block text-indigo-950 text-md font-semibold mb-2">{t('uploadCertificate.partnerTypeLabel')}</label>
                                    <input type="text" className="w-full h-15 px-3 py-2 border border-gray-300 rounded-md text-md text-gray-800 bg-gray-200 leading-tight focus:outline-none focus:shadow-outline"
                                        value={getPartnerType(partnerData)} disabled />
                                </div>
                                <div className="mb-4">
                                    <label className="block text-indigo-950 text-md font-semibold mb-2">{t('uploadCertificate.partnerDomainTypeLabel')}<span className="text-red-500">*</span></label>
                                    <div className="relative z-10">
                                        <button onClick={openDropdown} className="flex items-center justify-between w-full h-10 px-2 py-2 border border-gray-400 rounded-md text-md text-start text-gray-800 leading-tight focus:outline-none focus:shadow-none" type="button">
                                            <span>{selectedDomainType || setDefaultDomainType()}</span>
                                            <svg className={`w-3 h-2 ml-3 transform ${isDropdownOpen ? 'rotate-180' : 'rotate-0'} text-gray-500 text-sm`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                            </svg>
                                        </button>
                                        {isDropdownOpen && (
                                            <div className="absolute z-50 top-10 left-0 w-full">
                                                <div className="z-10 border border-gray-400 bg-white rounded-lg shadow-lg w-full dark:bg-gray-700 cursor-pointer">
                                                    <button className={`block w-full px-4 py-2 text-left text-base text-blue-950
                                                        ${selectedDomainType === "DEVICE" ?  'bg-gray-100' : 'hover:bg-gray-100'}`} 
                                                        onClick={() => selectDomainType("DEVICE")}>DEVICE
                                                    </button>
                                                    <div className="border-gray-100 border-t mx-2"></div>
                                                    <button className={`block w-full px-4 py-2 text-left text-base text-blue-950
                                                        ${selectedDomainType === "FTM" ?  'bg-gray-100' : 'hover:bg-gray-100'}`} 
                                                        onClick={() => selectDomainType("FTM")}>FTM
                                                    </button>
                                                    <div className="border-t border-gray-100 mx-2"></div>
                                                    <button className={`block w-full px-4 py-2 text-left text-base text-blue-950
                                                        ${selectedDomainType === "AUTH" ?  'bg-gray-100' : 'hover:bg-gray-100'}`}
                                                        onClick={() => selectDomainType("AUTH")}>AUTH
                                                    </button>
                                                </div>
                                            </div>
                                        )}
                                    </div>
                                </div>
                            </form>
                            <div className="flex items-center justify-center w-full h-36 p-4 border-2 border-blue-300 rounded-lg bg-blue-50 bg-opacity-25 text-center cursor-pointer relative">
                                {uploading && (
                                    <div className={`flex flex-col items-center justify-center mb-2 cursor-pointer ${!isDropdownOpen ? 'z-10' : 'z-0' }`}>
                                        <svg aria-hidden="true" className="w-8 h-8 text-gray-200 animate-spin fill-blue-800" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                            <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor" />
                                            <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill" />
                                        </svg>
                                        <h5 className="text-gray-800 text-sm">
                                            {t('uploadCertificate.selectingFile')}
                                        </h5>
                                        <p className="text-sm font-semibold text-blue-700" onClick={cancelUpload}>
                                            {t('uploadCertificate.cancel')}
                                        </p>
                                    </div>
                                )}
                                {!uploading && fileName === '' && (
                                    <div className={`flex flex-col items-center justify-center mb-2 cursor-pointer ${!isDropdownOpen ? 'z-10' : 'z-0'}`}>
                                        <label htmlFor="fileInput" className="flex flex-col items-center justify-center cursor-pointer">
                                            <svg
                                                xmlns="http://www.w3.org/2000/svg"
                                                width="28.75"
                                                height="39.26"
                                                viewBox="0 0 36.754 47.255"
                                            >
                                                <path
                                                    id="upload_file_FILL0_wght200_GRAD0_opsz24"
                                                    d="M217.064-801.227h2.625V-813.55l5.513,5.513,1.858-1.873-8.684-8.684-8.684,8.684,1.873,1.858,5.5-5.5Zm-12.823,8.482a4.107,4.107,0,0,1-3.027-1.214A4.108,4.108,0,0,1,200-796.986v-38.773a4.108,4.108,0,0,1,1.214-3.027A4.108,4.108,0,0,1,204.241-840h20.7l11.814,11.814v31.2a4.108,4.108,0,0,1-1.214,3.027,4.107,4.107,0,0,1-3.027,1.214Zm19.387-34.129v-10.5H204.241a1.544,1.544,0,0,0-1.111.5,1.544,1.544,0,0,0-.5,1.111v38.773a1.544,1.544,0,0,0,.5,1.111,1.544,1.544,0,0,0,1.111.5h28.272a1.544,1.544,0,0,0,1.111-.5,1.544,1.544,0,0,0,.5-1.111v-29.888Zm-21-10.5v0Z"
                                                    transform="translate(-200 840)"
                                                    fill="#1347b2"
                                                />
                                            </svg>
                                            <h5 className="text-gray-700 text-sm">
                                                {t('uploadCertificate.selectCertificate')}
                                            </h5>
                                            <p className="text-sm text-gray-400">
                                                {t('uploadCertificate.certificateFormat')}
                                            </p>
                                        </label>
                                        <input id="fileInput" type="file" className="hidden" accept=".cer,.pem" onChange={handleFileChange} />
                                    </div>
                                )}
                                {!uploading && fileName && (
                                    <div className={`flex flex-col items-center justify-center mb-2 cursor-pointer ${!isDropdownOpen ? 'z-10' : 'z-0'}`}>
                                        <label htmlFor="fileInput" className="flex flex-col items-center justify-center cursor-pointer">
                                            <svg
                                                xmlns="http://www.w3.org/2000/svg"
                                                width="28.75"
                                                height="39.26"
                                                viewBox="0 0 36.754 47.255"
                                            >
                                                <path
                                                    id="description_FILL0_wght200_GRAD0_opsz24"
                                                    d="M209.188-801.934h18.377v-2.625H209.188Zm0-10.5h18.377v-2.625H209.188Zm-4.948,19.69a4.108,4.108,0,0,1-3.027-1.214A4.108,4.108,0,0,1,200-796.986v-38.773a4.108,4.108,0,0,1,1.214-3.027A4.108,4.108,0,0,1,204.241-840h20.7l11.814,11.814v31.2a4.108,4.108,0,0,1-1.214,3.027,4.107,4.107,0,0,1-3.027,1.214Zm19.387-34.129v-10.5H204.241a1.544,1.544,0,0,0-1.111.5,1.544,1.544,0,0,0-.5,1.111v38.773a1.544,1.544,0,0,0,.5,1.111,1.544,1.544,0,0,0,1.111.5h28.272a1.544,1.544,0,0,0,1.111-.5,1.544,1.544,0,0,0,.5-1.111v-29.888Zm-21-10.5v0Z"
                                                    transform="translate(-200 840)"
                                                    fill="#1447b2"
                                                />
                                            </svg>
                                        </label>
                                        <h5 className="text-gray-800 text-sm">
                                            {fileName}
                                        </h5>
                                        <p className="text-sm font-semibold text-blue-700" onClick={removeUpload}>
                                            {t('uploadCertificate.remove')}
                                        </p>
                                    </div>
                                )}
                            </div>
                            {partnerData.isCertificateAvailable && (
                                <p className="text-sm text-gray-800 text-center mt-1">{t('uploadCertificate.lastcertificateUploadDate', {date: formattedDate})}</p>
                            )}
                        </div>
                        <div className="border-gray-200 border-opacity-50 border-t"></div>
                        <div className="p-4 flex justify-end relative">
                            <button className="mr-2 w-36 h-10 border-blue-700 border rounded-md text-blue-700 text-base font-semibold relative z-10" onClick={clickOnCancel}>{t('uploadCertificate.cancel')}</button>
                            {(!uploading && fileName) ? (
                                <button className="w-36 h-10 border-blue-700 border bg-blue-700 rounded-md text-white text-base font-semibold relative z-10" onClick={clickOnSubmit}>{uploadSuccess ? t('uploadCertificate.close') : t('uploadCertificate.submit')}</button>
                            ) : (
                                <button disabled className="w-36 h-10 border-zinc-400 border bg-zinc-400 rounded-md text-white text-base font-semibold">{t('uploadCertificate.submit')}</button>
                            )}
                            {uploadSuccess && !uploadFailure && (
                                <div className="fixed inset-0 flex mt-[122px] justify-center">
                                    <div className=" bg-fruit-salad md:w-[400px] w-[60%] h-[50px] flex items-center justify-between p-4">
                                        <p className="text-sm font-semibold text-white break-words">
                                            {t('uploadCertificate.successMsg', {partnerType: getPartnerType(partnerData)})}
                                        </p>
                                        <svg
                                            className="cursor-pointer"
                                            xmlns="http://www.w3.org/2000/svg"
                                            width="16.828"
                                            height="16.828"
                                            viewBox="0 0 16.828 16.828"
                                            onClick={() => setUploadFailure(true)}
                                        >
                                            <path
                                                id="close_FILL0_wght400_GRAD0_opsz48"
                                                d="M 23.27308082580566 25.05710983276367 L 22.91953086853027 24.70355033874512 L 17.35000038146973 19.13401985168457 L 11.7804708480835 24.70355033874512 L 11.42691993713379 25.05710983276367 L 11.0733699798584 24.70355033874512 L 9.996450424194336 23.62663078308105 L 9.642889976501465 23.27308082580566 L 9.996450424194336 22.91953086853027 L 15.56597995758057 17.35000038146973 L 9.996450424194336 11.7804708480835 L 9.642889976501465 11.42691993713379 L 9.996450424194336 11.07336044311523 L 11.07338047027588 9.996439933776855 L 11.42693042755127 9.642889976501465 L 11.78048038482666 9.996450424194336 L 17.35000038146973 15.5659704208374 L 22.91953086853027 9.996450424194336 L 23.27308082580566 9.642889976501465 L 23.62663078308105 9.996450424194336 L 24.70355033874512 11.0733699798584 L 25.05710983276367 11.42691993713379 L 24.70355033874512 11.7804708480835 L 19.13401985168457 17.35000038146973 L 24.70355033874512 22.91953086853027 L 25.05710983276367 23.27308082580566 L 24.70355033874512 23.62663078308105 L 23.62663078308105 24.70355033874512 L 23.27308082580566 25.05710983276367 Z"
                                                transform="translate(-8.936 -8.936)"
                                                fill="#fff"
                                            />
                                        </svg>
                                    </div>
                                </div>

                            )}
                            {uploadFailure && errorMsg && (
                                <div className="fixed inset-0 flex mt-[122px] justify-center">
                                    <div className="bg-moderate-red md:w-[400px] w-[60%] h-[50px] flex items-center justify-between p-4">
                                        <p className="text-sm font-semibold text-white break-words">
                                            {errorMsg}
                                        </p>
                                        <svg
                                            className="cursor-pointer"
                                            xmlns="http://www.w3.org/2000/svg"
                                            width="16.828"
                                            height="16.828"
                                            viewBox="0 0 16.828 16.828"
                                            onClick={cancelErrorMsg}
                                        >
                                            <path
                                                id="close_FILL0_wght400_GRAD0_opsz48"
                                                d="M 23.27308082580566 25.05710983276367 L 22.91953086853027 24.70355033874512 L 17.35000038146973 19.13401985168457 L 11.7804708480835 24.70355033874512 L 11.42691993713379 25.05710983276367 L 11.0733699798584 24.70355033874512 L 9.996450424194336 23.62663078308105 L 9.642889976501465 23.27308082580566 L 9.996450424194336 22.91953086853027 L 15.56597995758057 17.35000038146973 L 9.996450424194336 11.7804708480835 L 9.642889976501465 11.42691993713379 L 9.996450424194336 11.07336044311523 L 11.07338047027588 9.996439933776855 L 11.42693042755127 9.642889976501465 L 11.78048038482666 9.996450424194336 L 17.35000038146973 15.5659704208374 L 22.91953086853027 9.996450424194336 L 23.27308082580566 9.642889976501465 L 23.62663078308105 9.996450424194336 L 24.70355033874512 11.0733699798584 L 25.05710983276367 11.42691993713379 L 24.70355033874512 11.7804708480835 L 19.13401985168457 17.35000038146973 L 24.70355033874512 22.91953086853027 L 25.05710983276367 23.27308082580566 L 24.70355033874512 23.62663078308105 L 23.62663078308105 24.70355033874512 L 23.27308082580566 25.05710983276367 Z"
                                                transform="translate(-8.936 -8.936)"
                                                fill="#fff"
                                            />
                                        </svg>
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