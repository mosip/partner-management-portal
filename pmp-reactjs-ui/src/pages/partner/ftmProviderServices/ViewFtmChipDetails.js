import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { getUserProfile } from "../../../services/UserProfileService";
import { bgOfStatus, formatDate, getStatusCode, isLangRTL, getPartnerDomainType, getPartnerManagerUrl } from "../../../utils/AppUtils";
import Title from "../../common/Title";
import fileUploadBlue from '../../../svg/file_upload_blue_icon.svg';
import fileUploadDisabled from '../../../svg/file_upload_disabled_icon.svg';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';
import fileUpload from '../../../svg/file_upload_icon.svg';
import file from '../../../svg/file_icon.svg';
import UploadCertificate from "./../certificates/UploadCertificate";
import ErrorMessage from "../../common/ErrorMessage";
import SuccessMessage from "../../common/SuccessMessage";
import { HttpService } from "../../../services/HttpService";

function ViewFtmChipDetails() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [ftmDetails, setFtmDetails] = useState(true);
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [uploadCertificateRequest, setUploadCertificateRequest] = useState({});
    const [showPopup, setShowPopup] = useState(false);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [uploadCertificateData, setUploadCertificateData] = useState({});

    useEffect(() => {
        const selectedFtm = localStorage.getItem('selectedFtmData');
        if (!selectedFtm) {
            setUnexpectedError(true);
            return;
        }
        let ftmData = JSON.parse(selectedFtm);
        setFtmDetails(ftmData);
    }, []);

    const clickOnUpload = () => {
        document.body.style.overflow = "hidden";
        const requiredDataForCertUpload = {
            partnerType: "FTM_Provider",
            uploadHeader: 'addFtm.uploadFtmCertHeader',
            reUploadHeader: 'addFtm.reUploadFtmCertHeader',
            successMessage: 'addFtm.uploadFtmCertSuccessMsg',
            isUploadFtmCertificate: true,
            isCertificateAvailable: ftmDetails.isCertificateAvailable,
            certificateUploadDateTime: ftmDetails.certificateUploadDateTime,
        };
        setUploadCertificateData(requiredDataForCertUpload);
        const request = {
            ftpProviderId: ftmDetails.partnerId,
            ftpChipDeatilId: ftmDetails.ftmId,
            isItForRegistrationDevice: true,
            organizationName: getUserProfile().orgName,
            partnerDomain: getPartnerDomainType("FTM_Provider"),
        };
        setUploadCertificateRequest(request);
        setShowPopup(true);
    };

    const closePopup = (state, btnName) => {
        document.body.style.overflow = "auto";
        if (state && btnName === 'cancel') {
            setShowPopup(false);
        } else if (state && btnName === 'close') {
            navigate('/partnermanagement/ftmChipProviderServices/ftmList');
        }
    };

    const getOriginalCertificate = async (ftmDetails) => {
        const response = await getCertificate(ftmDetails.ftmId);
        if (response !== null) {
            if (response.isCaSignedCertificateExpired) {
                setErrorMsg(t('partnerCertificatesList.certificateExpired'));
            } else {
                setSuccessMsg(t('viewFtmChipDetails.originalCertSuccessMsg'));
                downloadCertificate(response.caSignedCertificateData, 'ca_signed_ftm_certificate.cer')
            }
        }
    }

    const downloadCertificate = (certificateData, fileName) => {
        const blob = new Blob([certificateData], { type: 'application/x-x509-ca-cert' });
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = fileName;

        document.body.appendChild(link);
        link.click();

        // Cleanup
        window.URL.revokeObjectURL(url);
        document.body.removeChild(link);
    }

    const getCertificate = async (ftmId) => {
        setErrorCode("");
        setErrorMsg("");
        try {
            const response = await HttpService.get(getPartnerManagerUrl('/ftpchipdetail/' + ftmId + '/original-ftm-certificate', process.env.NODE_ENV));
            if (response !== null) {
                const responseData = response.data;
                if (responseData.errors && responseData.errors.length > 0) {
                    const errorCode = responseData.errors[0].errorCode;
                    const errorMessage = responseData.errors[0].message;
                    setErrorCode(errorCode);
                    setErrorMsg(errorMessage);
                    console.error('Error:', errorMessage);
                    return null;
                } else {
                    const resData = responseData.response;
                    console.log('Response data:', resData);
                    return resData;
                }
            } else {
                setErrorMsg(t('partnerCertificatesList.errorWhileDownloadingCertificate'));
                return null;
            }
        } catch (err) {
            console.error('Error fetching certificate:', err);
            setErrorMsg(err);
        }
    }

    const moveToFtmList = () => {
        navigate('/partnermanagement/ftmChipProviderServices/ftmList');
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const cancelSuccessMsg = () => {
        setSuccessMsg("");
    };

    return (
        <>
            {errorMsg && (
                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
            )}
            {successMsg && (
                <SuccessMessage successMsg={successMsg} clickOnCancel={cancelSuccessMsg} />
            )}
            <div className={`flex-col w-full p-5 bg-anti-flash-white h-full font-inter break-all break-normal max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} overflow-x-scroll`}>
                <div className="flex justify-between mb-3">
                    <Title title={ftmDetails.title} subTitle='viewFtmChipDetails.listOfFtmChipDetails' backLink='/partnermanagement/ftmChipProviderServices/ftmList' />
                </div>

                {unexpectedError && (
                    <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                        <div className="flex items-center justify-center p-24">
                            <div className="flex flex-col justify-center items-center">
                                <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                <p className="text-sm font-semibold text-[#6F6E6E] py-4">{t('devicesList.unexpectedError')}</p>
                                <button onClick={moveToFtmList} type="button"
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
                                <p className="font-semibold text-lg text-dark-blue mb-2">
                                    {ftmDetails.make} - {ftmDetails.model}
                                </p>
                                <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                    <div className={`${bgOfStatus(ftmDetails.status, t)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                        {getStatusCode(ftmDetails.status, t)}
                                    </div>
                                    <div className={`font-semibold ${isLoginLanguageRTL ? "mr-1" : "ml-3"} text-sm text-dark-blue`}>
                                        {t("viewDeviceDetails.createdOn") + ' ' +
                                            formatDate(ftmDetails.createdDateTime, "date", false)}
                                    </div>
                                    <div className="mx-1 text-gray-300">|</div>
                                    <div className="font-semibold text-sm text-dark-blue">
                                        {formatDate(ftmDetails.createdDateTime, "time", false
                                        )}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                            <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                                <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                                    <p className="font-[600] text-suva-gray text-xs">
                                        {t("viewOidcClientDetails.partnerId")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-sm">
                                        {ftmDetails.partnerId}
                                    </p>
                                </div>
                                <div className="mb-3 max-[600px]:w-[100%] w-[50%]">
                                    <p className="font-[600] text-suva-gray text-xs">
                                        {t("viewOidcClientDetails.partnerType")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-sm">
                                        {t("viewFtmChipDetails.ftmChipProvider")}
                                    </p>
                                </div>
                                <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                                    <p className="font-[600] text-suva-gray text-xs">
                                        {t("ftmList.make")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-sm">
                                        {ftmDetails.make}
                                    </p>
                                </div>
                                <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                                    <p className="font-[600] text-suva-gray text-xs">
                                        {t("ftmList.model")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-sm">
                                        {ftmDetails.model}
                                    </p>
                                </div>
                            </div>
                            <hr className={`h-px w-full bg-gray-200 border-0 mb-[3%]`} />
                            <div className="rounded-lg shadow-lg border mb-[2%]">
                                <div className={`flex-col`}>
                                    <div className={`flex py-[1rem] px-5 ${ftmDetails.status === "deactivated" ? 'bg-gray-100' : 'bg-[#F9FBFF]'} justify-between items-center max-520:flex-col`}>
                                        <div className="flex space-x-4 items-center ">
                                            { ftmDetails.status === "deactivated" ? 
                                                <img src={fileUploadDisabled} className="h-8" alt="" />
                                            :
                                                <img src={ftmDetails.isViewFtmChipDetails ? fileUploadBlue : ftmDetails.isCertificateAvailable ? fileUpload : file} className="h-8" alt="" />
                                            }
                                            <div className="flex-col p-3 items-center">
                                                <h6 className={`text-sm ${ftmDetails.isCertificateAvailable ? 'font-bold text-black' : 'font-semibold text-charcoal-gray'}`}>
                                                    {ftmDetails.isViewFtmChipDetails ? t('viewFtmChipDetails.ftmChipCertificate') : ftmDetails.isCertificateAvailable ? t('viewFtmChipDetails.ftmChipCertificate') : t('manageFtmChipCertificate.uploadFtmCertificate')}
                                                </h6>
                                                {ftmDetails.isManageFtmCertificate && (
                                                    <p className="text-xs text-light-gray">{ftmDetails.isCertificateAvailable ? null : t('manageFtmChipCertificate.certificateFormatMsg')}</p>
                                                )}
                                            </div>
                                        </div>

                                        <div className=" flex space-x-2">
                                            {ftmDetails.isViewFtmChipDetails && (
                                                <button id='download_btn' disabled={ftmDetails.status !== 'approved' && ftmDetails.status !== 'pending_approval'} onClick={() => getOriginalCertificate(ftmDetails)}
                                                    className={`flex items-center text-center w-fit h-10 ${isLoginLanguageRTL ? "ml-5" : "mr-5"} ${(ftmDetails.status !== 'approved' && ftmDetails.status !== 'pending_approval') ? 'text-[#6f7070] border-gray-300 bg-white' : 'text-tory-blue bg-white border-blue-800'} text-xs px-[1.5rem] py-[1%] border font-semibold rounded-lg text-center`}>
                                                    {t('commons.download')}
                                                </button>
                                            )}
                                            {ftmDetails.isManageFtmCertificate && (
                                                <div className="flex space-x-2 max-640:flex-col max-640:space-y-2 max-640:space-x-0">
                                                    <button id='download_btn' disabled={!ftmDetails.isCertificateAvailable} onClick={() => getOriginalCertificate(ftmDetails)}
                                                        className={`flex items-center text-center w-fit h-10 ${isLoginLanguageRTL ? "ml-5" : "mr-5"} ${!ftmDetails.isCertificateAvailable ? 'text-[#6f7070] border-gray-300 bg-white' : 'text-tory-blue bg-white border-blue-800'} text-xs px-[1.5rem] py-[1%] border font-semibold rounded-lg text-center`}>
                                                        {t('commons.download')}
                                                    </button>
                                                    <button id="certificate_reupload_btn" onClick={clickOnUpload} className={`h-10 w-28 text-xs p-3 py-2 ${ftmDetails.isCertificateAvailable ? 'text-tory-blue bg-white border-blue-800' : 'bg-tory-blue text-snow-white'} border font-semibold rounded-md text-center`}>
                                                        {ftmDetails.isCertificateAvailable ? t('partnerCertificatesList.reUpload') : t('partnerCertificatesList.upload')}
                                                    </button>
                                                    {showPopup && (
                                                        <UploadCertificate header={t('addFtm.uploadFtmCertificate')} closePopup={closePopup} popupData={uploadCertificateData} request={uploadCertificateRequest} />
                                                    )}
                                                </div>
                                            )}
                                        </div>
                                    </div>
                                    <hr className="border bg-medium-gray h-px" />
                                    <div className="flex items-center p-5 bg-white rounded-lg">
                                        <div className="flex-col space-y-1">
                                            <p className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.partnerType')}</p>
                                            <p className="font-bold text-sm text-charcoal-gray">{t('viewFtmChipDetails.ftmChipProvider')}</p>
                                        </div>
                                        <div className={`flex-col ${isLoginLanguageRTL ? "mr-[5%]" : "ml-[5%]"} space-y-1`}>
                                            <p className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.expiryDate')}</p>
                                            <p className="font-semibold text-sm text-charcoal-gray">{formatDate(ftmDetails.certificateExpiryDateTime, 'dateTime', false)}</p>
                                        </div>
                                        <div className={`flex-col ${isLoginLanguageRTL ? "mr-[10%]" : "ml-[10%]"} space-y-1`}>
                                            <p className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.timeOfUpload')}</p>
                                            <p className="font-semibold text-sm text-charcoal-gray">{formatDate(ftmDetails.certificateUploadDateTime, 'dateTime', false)}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className={`flex justify-end py-5 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                            <button id="ftm_view_back_btn" onClick={moveToFtmList} className={`h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center`}>
                                {t("commons.back")}
                            </button>
                        </div>
                    </div>
                )}
            </div>
        </>
    )
}

export default ViewFtmChipDetails;