import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import { bgOfStatus, checkExpiryDate, downloadFile, formatDate, getPartnerManagerUrl, getStatusCode, handleServiceErrors, isLangRTL } from '../../../utils/AppUtils';
import ErrorMessage from '../../common/ErrorMessage';
import SuccessMessage from '../../common/SuccessMessage';
import Title from '../../common/Title';
import fileUploadBlue from '../../../svg/file_upload_blue_icon.svg';
import fileUploadDisabled from '../../../svg/file_upload_disabled_icon.svg';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';
import fileUpload from '../../../svg/file_upload_icon.svg';
import file from '../../../svg/file_icon.svg';
import { HttpService } from '../../../services/HttpService';

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
            try {
                const response = await HttpService.get(getPartnerManagerUrl('/ftpchipdetail/' + selectedFtmDetails.ftmId + '/original-ftm-certificate', process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        setCertificateDetails(resData);
                    }
                    else {
                        handleServiceErrors(setErrorCode, setErrorMsg, responseData);
                    }
                } else {
                    setErrorMsg(t('viewAdminFtmDetails.errorWhileGettingFtmDetails'));
                }
            } catch (err) {
                console.error('Error fetching certificate Details:', err);
                setErrorMsg(err);
            }
        }
        fetchCertificateDetails();
    }, []);

    const getOriginalCertificate = async () => {
        if (certificateDetails.isCaSignedCertificateExpired) {
            setErrorMsg(t('partnerCertificatesList.certificateExpired'));
        } else {
            setSuccessMsg(t('viewFtmChipDetails.originalCertSuccessMsg'));
            downloadFile(certificateDetails.caSignedCertificateData, 'ca_signed_ftm_certificate.cer', 'application/x-x509-ca-cert')
        }
    }

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
        <>
            {errorMsg && (
                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
            )}
            {successMsg && (
                <SuccessMessage successMsg={successMsg} clickOnCancel={cancelSuccessMsg} />
            )}
            <div className={`flex-col w-full p-5 bg-anti-flash-white h-full font-inter break-all break-normal max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} overflow-x-scroll`}>
                <div className="flex justify-between mb-3">
                    <Title title={'viewFtmChipDetails.listOfFtmChipDetails'} subTitle='viewFtmChipDetails.listOfFtmChipDetails' backLink='/partnermanagement/admin/ftm-chip-provider-services/ftm-list' />
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
                                <p className="font-semibold text-lg text-dark-blue mb-2">
                                    {ftmDetails.make} - {ftmDetails.model}
                                </p>
                                <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                    <div className={`${bgOfStatus(ftmDetails.status, t)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                        {getStatusCode(ftmDetails.status, t)}
                                    </div>
                                    <div className={`font-semibold ${isLoginLanguageRTL ? "mr-1" : "ml-3"} text-sm text-dark-blue`}>
                                        {t("viewDeviceDetails.createdOn") + ' ' +
                                            formatDate(ftmDetails.createdDateTime, "date", true)}
                                    </div>
                                    <div className="mx-1 text-gray-300">|</div>
                                    <div className="font-semibold text-sm text-dark-blue">
                                        {formatDate(ftmDetails.createdDateTime, "time", true)}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                            <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                                <div className={`w-[49%] max-[600px]:w-[100%] mb-5 ${isLoginLanguageRTL ? "ml[1%]" : "mr-[1%]"}`}>
                                    <p id="ftm_chip_details_label_partner_id" className="font-[600] text-suva-gray text-xs">
                                        {t("viewOidcClientDetails.partnerId")}
                                    </p>
                                    <p id="ftm_chip_details_context_partner_id" className="font-[600] text-vulcan text-sm">
                                        {ftmDetails.partnerId}
                                    </p>
                                </div>
                                <div className={`mb-5 max-[600px]:w-[100%] w-[50%]`}>
                                    <p id="ftm_chip_details_label_partner_type" className="font-[600] text-suva-gray text-xs">
                                        {t("viewOidcClientDetails.partnerType")}
                                    </p>
                                    <p id="ftm_chip_details_context_ftm_chip_provider" className="font-[600] text-vulcan text-sm">
                                        {t("viewFtmChipDetails.ftmChipProvider")}
                                    </p>
                                </div>
                                <div className={`w-[50%] max-[600px]:w-[100%] mb-5 ${isLoginLanguageRTL ? "ml[1%]" : "mr-[1%]"}`}>
                                    <p id="ftm_chip_details_label_make" className="font-[600] text-suva-gray text-xs">
                                        {t("viewAdminOidcClientDetails.organisation")}
                                    </p>
                                    <p id="ftm_chip_details_context_make" className="font-[600] text-vulcan text-sm">
                                        {ftmDetails.orgName}
                                    </p>
                                </div>
                            </div>
                            <hr className={`h-px w-full bg-gray-200 border-0 mb-[3%]`} />
                            <div className="rounded-lg shadow-lg border mb-[2%]">
                                <div className={`flex-col`}>
                                    <div className={`flex py-[1rem] px-5 ${(ftmDetails.status === 'rejected' || ftmDetails.status === 'deactivated') ? 'bg-gray-100' : (ftmDetails.status === 'pending_approval' || ftmDetails.status === "pending_cert_upload") ? 'bg-[#f7f9fe]' : 'bg-[#f0fff6]'} justify-between items-center max-520:flex-col`}>
                                        <div className="flex space-x-4 items-center ">
                                            {(ftmDetails.status === 'rejected' || ftmDetails.status === 'deactivated') ?
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
                                            <div className="flex space-x-2 max-640:flex-col max-640:space-y-2 max-640:space-x-0">
                                                <button id='download_btn' disabled={ftmDetails.status !== 'approved' && ftmDetails.status !== 'pending_approval'} onClick={() => getOriginalCertificate()}
                                                    className={`flex items-center text-center w-fit h-10 ${isLoginLanguageRTL ? "ml-5" : "mr-5"} ${(ftmDetails.status !== 'approved' && ftmDetails.status !== 'pending_approval') ? 'text-[#6f7070] border-gray-300 bg-white' : 'text-tory-blue bg-white border-blue-800'} text-xs px-[1.5rem] py-[1%] border font-semibold rounded-lg text-center`}>
                                                    {t('commons.download')}
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <hr className="border bg-medium-gray h-px" />
                                    <div className="flex items-center p-5 bg-white rounded-lg">
                                        <div className="flex-col space-y-1">
                                            <p id="ftm_chip_details_partner_type_label" className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.partnerType')}</p>
                                            <p id="ftm_chip_details_partner_type_context" className="font-bold text-sm text-charcoal-gray">{t('viewFtmChipDetails.ftmChipProvider')}</p>
                                        </div>
                                        <div className={`flex-col ${isLoginLanguageRTL ? "mr-[5%]" : "ml-[5%]"} space-y-1`}>
                                            <p id="ftm_chip_details_label_expiry_date_time" className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.expiryDate')}</p>
                                            <p id="ftm_chip_details_context_expiry_date_time" className={`font-semibold text-sm ${checkExpiryDate(certificateDetails.caSignedCertExpiryDateTime) ? 'text-red-700' : 'text-charcoal-gray'} `}>
                                                {formatDate(certificateDetails.caSignedCertExpiryDateTime, 'dateTime', false)}
                                            </p>
                                        </div>
                                        <div className={`flex-col ${isLoginLanguageRTL ? "mr-[10%]" : "ml-[10%]"} space-y-1`}>
                                            <p id="ftm_chip_details_label_upload_date_time" className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.timeOfUpload')}</p>
                                            <p id="ftm_chip_details_context_upload_date_time" className="font-semibold text-sm text-charcoal-gray">
                                                {formatDate(certificateDetails.caSignedCertUploadDateTime, 'dateTime', false)}
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
    )
}

export default ViewAdminFtmChipDetails;