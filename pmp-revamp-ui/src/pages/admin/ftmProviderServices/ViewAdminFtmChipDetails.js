import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import { bgOfStatus, downloadFile, formatDate, getPartnerManagerUrl, getStatusCode, handleServiceErrors, isLangRTL, isCaSignedPartnerCertificateAvailable } from '../../../utils/AppUtils';
import ErrorMessage from '../../common/ErrorMessage';
import SuccessMessage from '../../common/SuccessMessage';
import Title from '../../common/Title';
import fileUploadBlue from '../../../svg/file_upload_blue_icon.svg';
import fileUploadDisabled from '../../../svg/file_upload_disabled_icon.svg';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';
import fileUpload from '../../../svg/file_upload_icon.svg';
import file from '../../../svg/file_icon.svg';
import { HttpService } from '../../../services/HttpService';
import LoadingIcon from '../../common/LoadingIcon';

function ViewAdminFtmChipDetails() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [ftmDetails, setFtmDetails] = useState({});
    const [certificateDetails, setCertificateDetails] = useState({});
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [downloadCertApiNotExist, setDownloadCertApiNotExist] = useState(false);

    useEffect(() => {
        const selectedFtmData = localStorage.getItem('selectedFtmAttributes');
        if (!selectedFtmData) {
            setUnexpectedError(true);
            return;
        }
        const selectedFtmDetails = JSON.parse(selectedFtmData);
        setFtmDetails(selectedFtmDetails);

        const fetchCertificateDetails = async () => {
            setErrorCode("");
            setErrorMsg("");
            setDataLoaded(false);
            try {
                const response = await HttpService.get(getPartnerManagerUrl(`/ftpchipdetail/${selectedFtmDetails.ftmId}/certificate-data`, process.env.NODE_ENV));
        
                if (!response || !response.data) {
                    setErrorMsg(t('viewAdminFtmDetails.errorWhileGettingFtmDetails'));
                    setDataLoaded(true);
                    return;
                }
        
                if (response.data.response) {
                    setCertificateDetails(response.data.response);
                    setDataLoaded(true);
                    return;
                }
        
                if (response.data.errors && response.data.errors.length > 0) {
                    const errorCode = response.data.errors[0].errorCode;
                    if (errorCode === 'PMS_KKS_001') {
                        setErrorMsg(t('certificatesList.errorWhileDownloadingCertificate'));
                    } else {
                        handleServiceErrors(response.data, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('viewAdminFtmDetails.errorWhileGettingFtmDetails'));
                }
            } catch (err) {
                console.error('Error fetching certificate details:', err);
                if (err.response?.status && err.response.status !== 401) {
                    setErrorMsg(err.message || t('viewAdminFtmDetails.errorWhileGettingFtmDetails'));
                }
            }
            setDataLoaded(true);
        }
    
        const checkCompatibleAndFetch = async () => {
            const isApiExist = await isCaSignedPartnerCertificateAvailable();
            if (isApiExist) {
                if (selectedFtmDetails.status === "approved" || selectedFtmDetails.status === "pending_approval") {
                    fetchCertificateDetails();
                }
            } else {
                setDownloadCertApiNotExist(true);
            }
        };
          
        checkCompatibleAndFetch();
    }, []);

    const getOriginalCertificate = async () => {
        if (Object.keys(certificateDetails).length === 0) {
            setErrorMsg(t('viewAdminFtmDetails.errorWhileGettingFtmDetails'));
            return;
        }
    
        if (certificateDetails.isCaSignedCertificateExpired) {
            setErrorMsg(t('partnerCertificatesList.certificateExpired'));
            return;
        }
    
        setSuccessMsg(t('viewFtmChipDetails.originalCertSuccessMsg'));
        downloadFile(certificateDetails.caSignedCertificateData,'ca_signed_ftm_certificate.cer','application/x-x509-ca-cert' );
    };
    

    const moveToAdminFtmList = () => {
        navigate('/partnermanagement/admin/ftm-chip-provider-services/ftm-list');
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const cancelSuccessMsg = () => {
        setSuccessMsg("");
    };

    return (
        <div className={`w-full p-4 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} overflow-x-scroll`}>
            {!dataLoaded && (
                <LoadingIcon />
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    {successMsg && (
                        <SuccessMessage successMsg={successMsg} clickOnCancel={cancelSuccessMsg} />
                    )}
                    <div className={`flex-col mt-5 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm mb-[2%]`}>
                        <div className="flex justify-between mb-3">
                            <Title title={'viewFtmChipDetails.viewFtmChipDetails'} subTitle='viewFtmChipDetails.listOfFtmChipDetails' backLink='/partnermanagement/admin/ftm-chip-provider-services/ftm-list' />
                        </div>

                        {unexpectedError && (
                            <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center items-center">
                                        <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                        <p className="text-sm font-semibold text-[#6F6E6E] py-4">{t('devicesList.unexpectedError')}</p>
                                        <button onClick={moveToAdminFtmList} type="button"
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
                                        <p className="text-lg text-dark-blue mb-2">
                                            {t('ftmList.ftmId')}: <span className="font-semibold">{ftmDetails.ftmId}</span>
                                        </p>
                                        <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                            <div className={`${bgOfStatus(ftmDetails.status, t)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                                {getStatusCode(ftmDetails.status, t)}
                                            </div>
                                            <div className={`font-semibold ${isLoginLanguageRTL ? "mr-[1.4rem]" : "ml-[0.75rem]"} text-sm text-dark-blue`}>
                                                {t("viewDeviceDetails.createdOn") + ' ' +
                                                    formatDate(ftmDetails.createdDateTime, "date")}
                                            </div>
                                            <div className="mx-1 text-gray-300">|</div>
                                            <div className="font-semibold text-sm text-dark-blue">
                                                {formatDate(ftmDetails.createdDateTime, "time")}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                                    <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                                        <div className={`w-[48%] max-[600px]:w-[100%] mb-3 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                            <p className="font-[600] text-suva-gray text-sm">
                                                {t("sbiList.partnerId")}
                                            </p>
                                            <p className="font-[600] text-vulcan text-md">
                                                {ftmDetails.partnerId}
                                            </p>
                                        </div>
                                        <div className={`mb-3 max-[600px]:w-[100%] w-[50%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                            <p className="font-[600] text-suva-gray text-sm">
                                                {t("viewPolicyRequest.partnerType")}
                                            </p>
                                            <p className="font-[600] text-vulcan text-md">
                                                {t("viewFtmChipDetails.ftmChipProvider")}
                                            </p>
                                        </div>
                                        <div className={`mb-3 max-[600px]:w-[100%] w-[48%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                            <p className="font-[600] text-suva-gray text-sm">
                                                {t("sbiList.orgName")}
                                            </p>
                                            <p className="font-[600] text-vulcan text-md">
                                                {ftmDetails.orgName}
                                            </p>
                                        </div>
                                    </div>
                                    <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                                        <div className={`mb-5 max-[600px]:w-[100%] w-[48%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                            <p className="font-[600] text-suva-gray text-sm">
                                                {t("ftmList.make")}
                                            </p>
                                            <p className="font-[600] text-vulcan text-md">
                                                {ftmDetails.make}
                                            </p>
                                        </div>
                                        <div className={`mb-5 max-[600px]:w-[100%] w-[50%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                            <p className="font-[600] text-suva-gray text-sm">
                                                {t("ftmList.model")}
                                            </p>
                                            <p className="font-[600] text-vulcan text-md">
                                                {ftmDetails.model}
                                            </p>
                                        </div>
                                    </div>
                                    <hr className={`h-px w-full bg-gray-200 border-0 mb-[3%]`} />
                                    <div className="rounded-lg shadow-lg border mb-[2%]">
                                        <div className={`flex-col`}>
                                            <div className={`flex py-[1rem] px-5 ${((downloadCertApiNotExist && ftmDetails.status !== 'pending_cert_upload') || ftmDetails.status === 'rejected' || ftmDetails.status === 'deactivated') ? 'bg-gray-100' : (ftmDetails.status === 'pending_approval' || ftmDetails.status === "pending_cert_upload") ? 'bg-[#f7f9fe]' : 'bg-[#f0fff6]'} justify-between items-center max-520:flex-col`}>
                                                <div className="flex space-x-4 items-center ">
                                                    {((downloadCertApiNotExist && ftmDetails.status !== 'pending_cert_upload') || ftmDetails.status === 'rejected' || ftmDetails.status === 'deactivated') ?
                                                        <img id='file_upload_disabled' src={fileUploadDisabled} className="h-8" alt="" />
                                                        :
                                                        <img id='file_upload_blue' src={ftmDetails.status === 'pending_approval' ? fileUploadBlue : !ftmDetails.isCertificateAvailable ? file : fileUpload} className="h-8" alt="" />
                                                    }
                                                    <div className="flex-col p-3 items-center">
                                                        <h6 id="ftm_chip_details__certificate_label" className={`text-sm ${true ? 'font-bold text-black' : 'font-semibold text-charcoal-gray'}`}>
                                                            {t('viewFtmChipDetails.ftmChipCertificate')}
                                                        </h6>
                                                    </div>
                                                </div>

                                                <div className=" flex space-x-2">
                                                    <div className="relative group space-x-2 max-640:space-y-2 max-640:space-x-0">
                                                        <button id='download_btn' disabled={downloadCertApiNotExist || (ftmDetails.status !== 'approved' && ftmDetails.status !== 'pending_approval')} onClick={() => getOriginalCertificate()}
                                                            className={`flex items-center text-center w-fit h-10 ${isLoginLanguageRTL ? "ml-5" : "mr-5"} ${(downloadCertApiNotExist || (ftmDetails.status !== 'approved' && ftmDetails.status !== 'pending_approval')) ? 'text-[#6f7070] border-gray-300 bg-white' : 'text-tory-blue bg-white border-blue-800'} text-xs px-[1.5rem] py-[1%] border font-semibold rounded-lg text-center`}>
                                                            {t('commons.download')}
                                                        </button>
                                                        {downloadCertApiNotExist && (ftmDetails.status === 'approved' || ftmDetails.status === 'pending_approval') && (
                                                            <div className={`absolute hidden group-hover:block group-focus:block text-center bg-gray-100 text-xs text-gray-500 font-semibold p-2 w-60 mt-1 z-10 ${isLoginLanguageRTL ? "left-0" : "right-0"} top-11  rounded-md shadow-md`}>
                                                                {t('viewAdminFtmDetails.compatibilityMsg')}
                                                            </div>
                                                        )}
                                                    </div>
                                                </div>
                                            </div>
                                            <hr className="border bg-medium-gray h-px" />
                                            <div className="flex items-center p-5 bg-white rounded-lg">
                                                <div className="flex-col space-y-1">
                                                    <p id="ftm_chip_details_partner_type_label" className="font-semibold text-sm text-dim-gray">{t('partnerCertificatesList.partnerType')}</p>
                                                    <p id="ftm_chip_details_partner_type_context" className="font-bold text-md text-charcoal-gray">{t('viewFtmChipDetails.ftmChipProvider')}</p>
                                                </div>
                                                <div className={`flex-col ${isLoginLanguageRTL ? "mr-[10%]" : "ml-[10%]"} space-y-1`}>
                                                    <p id="ftm_chip_details_label_upload_date_time" className="font-semibold text-sm text-dim-gray">{t('partnerCertificatesList.timeOfUpload')}</p>
                                                    <p id="ftm_chip_details_context_upload_date_time" className="font-semibold text-md text-charcoal-gray">
                                                        {downloadCertApiNotExist && (ftmDetails.status === 'approved' || ftmDetails.status === 'pending_approval') ? t('statusCodes.notAvailable') : formatDate(certificateDetails.mosipSignedCertUploadDateTime, 'dateTime')}
                                                    </p>
                                                </div>
                                                <div className={`flex-col ${isLoginLanguageRTL ? "mr-[5%]" : "ml-[5%]"} space-y-1`}>
                                                    <p id="ftm_chip_details_label_expiry_date_time" className={`text-sm font-semibold text-dim-gray font-semibold'}`}>{t('partnerCertificatesList.expiryDate')}</p>
                                                    <p id="ftm_chip_details_context_expiry_date_time" className={`text-md ${certificateDetails.isCaSignedCertificateExpired ? 'text-crimson-red font-bold' : 'text-charcoal-gray font-semibold'}`}>
                                                        {downloadCertApiNotExist && (ftmDetails.status === 'approved' || ftmDetails.status === 'pending_approval') ? t('statusCodes.notAvailable') : formatDate(certificateDetails.caSignedCertExpiryDateTime, 'dateTime')}
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <hr className="h-px w-full bg-gray-200 border-0" />
                                <div className={`flex justify-end py-5 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                                    <button id="ftm_view_back_btn" onClick={moveToAdminFtmList} className={`h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center`}>
                                        {t("commons.back")}
                                    </button>
                                </div>
                            </div>
                        )}
                    </div>
                </>
            )}
        </div>
    )
}

export default ViewAdminFtmChipDetails;