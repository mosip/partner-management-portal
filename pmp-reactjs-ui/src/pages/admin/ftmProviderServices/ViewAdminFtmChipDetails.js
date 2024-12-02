import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import { bgOfStatus, downloadFile, formatDate, getPartnerDomainType, getPartnerManagerUrl, getStatusCode, handleServiceErrors, isLangRTL } from '../../../utils/AppUtils';
import ErrorMessage from '../../common/ErrorMessage';
import SuccessMessage from '../../common/SuccessMessage';
import Title from '../../common/Title';
import fileUploadBlue from '../../../svg/file_upload_blue_icon.svg';
import fileUploadDisabled from '../../../svg/file_upload_disabled_icon.svg';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';
import fileUpload from '../../../svg/file_upload_icon.svg';
import file from '../../../svg/file_icon.svg';
import adminImage from "../../../svg/admin.png";
import partnerImage from "../../../svg/partner.png";
import UploadCertificate from '../../partner/certificates/UploadCertificate';
import { HttpService } from '../../../services/HttpService';

function ViewAdminFtmChipDetails() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [ftmDetails, setFtmDetails] = useState({});
    const [dataLoaded, setDataLoaded] = useState(false);
    const [selectedFtmDetails, setSelectedFtmDetails] = useState({});
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");

    useEffect(() => {
        const data = localStorage.getItem('selectedFtmAttributes');
        if (!data) {
            setUnexpectedError(true);
            return
        }
        const ftmData = JSON.parse(data);
        setSelectedFtmDetails(ftmData);
    }, []);

    const getOriginalCertificate = async (ftmDetails) => {
        const response = await fetchCertificate(ftmDetails.ftmId);
        if (response !== null) {
            if (response.isCaSignedCertificateExpired) {
                setErrorMsg(t('partnerCertificatesList.certificateExpired'));
            } else {
                setSuccessMsg(t('viewFtmChipDetails.originalCertSuccessMsg'));
                downloadFile(response.caSignedCertificateData, 'ca_signed_ftm_certificate.cer', 'application/x-x509-ca-cert')
            }
        }
    }

    const fetchCertificate = async (ftmId) => {
        setErrorCode("");
        setErrorMsg("");
        try {
            const response = await HttpService.get(getPartnerManagerUrl('/ftpchipdetail/' + '96376' + '/original-ftm-certificate', process.env.NODE_ENV));
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
    };
    // useEffect(() => {
    //     const fetchData = async () => {
    //         try {
    //             setDataLoaded(false);
    //             const response = await HttpService.get(getPartnerManagerUrl(`/partners/${selectedFtmDetails.partnerId}/v2`, process.env.NODE_ENV));
    //             if (response) {
    //                 const responseData = response.data;
    //                 if (responseData && responseData.response) {
    //                     const resData = responseData.response;
    //                     setFtmDetails(resData);
    //                 } else {
    //                     setUnexpectedError(true);
    //                     handleServiceErrors(responseData, setErrorCode, setErrorMsg);
    //                 }
    //             } else {
    //                 setErrorMsg(t('viewPartnerDetails.errorInPartnerList'));
    //             }
    //             setDataLoaded(true);
    //         } catch (err) {
    //             console.error('Error fetching data:', err);
    //             setErrorMsg(err);
    //         }
    //     };
    //     fetchData();
    // }, []);

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
                                    <div className={`${bgOfStatus(selectedFtmDetails.status, t)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                        {getStatusCode(selectedFtmDetails.status, t)}
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
                                    <div className={`flex py-[1rem] px-5 ${(selectedFtmDetails.status === 'rejected' || selectedFtmDetails.status === 'deactivated') ? 'bg-gray-100' : (selectedFtmDetails.status === 'pending_approval' || selectedFtmDetails.status === "pending_cert_upload") ? 'bg-[#f7f9fe]' : 'bg-[#f0fff6]'} justify-between items-center max-520:flex-col`}>
                                        <div className="flex space-x-4 items-center ">
                                            {(selectedFtmDetails.status === 'rejected' || selectedFtmDetails.status === 'deactivated') ?
                                                <img id='file_upload_disabled' src={fileUploadDisabled} className="h-8" alt="" />
                                                :
                                                <img id='file_upload_blue' src={selectedFtmDetails.status === 'pending_approval' ? fileUploadBlue : !ftmDetails.isCertificateAvailable ? file : fileUpload} className="h-8" alt="" />
                                            }
                                            <div className="flex-col p-3 items-center">
                                                <h6 id="ftm_chip_details__certificate_label" className={`text-sm ${true ? 'font-bold text-black' : 'font-semibold text-charcoal-gray'}`}>
                                                    {t('viewFtmChipDetails.ftmChipCertificate')}
                                                </h6>
                                            </div>
                                        </div>

                                        <div className=" flex space-x-2">
                                            <div className="flex space-x-2 max-640:flex-col max-640:space-y-2 max-640:space-x-0">
                                                <button id='download_btn' disabled={selectedFtmDetails.status !== 'approved' && selectedFtmDetails.status !== 'pending_approval'} onClick={() => getOriginalCertificate(ftmDetails)}
                                                    className={`flex items-center text-center w-fit h-10 ${isLoginLanguageRTL ? "ml-5" : "mr-5"} ${(selectedFtmDetails.status !== 'approved' && selectedFtmDetails.status !== 'pending_approval') ? 'text-[#6f7070] border-gray-300 bg-white' : 'text-tory-blue bg-white border-blue-800'} text-xs px-[1.5rem] py-[1%] border font-semibold rounded-lg text-center`}>
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
                                            <p id="ftm_chip_details_context_expiry_date_time" className="font-semibold text-sm text-charcoal-gray">
                                                {formatDate(ftmDetails.certificateExpiryDateTime, 'dateTime', false)}
                                            </p>
                                        </div>
                                        <div className={`flex-col ${isLoginLanguageRTL ? "mr-[10%]" : "ml-[10%]"} space-y-1`}>
                                            <p id="ftm_chip_details_label_upload_date_time" className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.timeOfUpload')}</p>
                                            <p id="ftm_chip_details_context_upload_date_time" className="font-semibold text-sm text-charcoal-gray">
                                                {formatDate(ftmDetails.certificateUploadDateTime, 'dateTime', false)}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr className="h-px mt-3 w-full bg-gray-200 border-0" />
                            <div className="py-3">
                                <p className="font-semibold text-vulcan text-base mb-3">
                                    {t("viewPolicyDetails.comments")}
                                </p>
                                <div>
                                    <div className="flex font-semibold w-full ">
                                        <span className={`w-8 h-8 rounded-full flex justify-center items-center ${isLoginLanguageRTL ? "ml-3" : "mr-3"} text-sm text-white lg:w-10 lg:h-10`}>
                                            <div className={`relative flex-1 after:content-['']  after:w-0.5 after:h-[4rem] after:bg-gray-200 after:inline-block after:absolute ${isLoginLanguageRTL ? "after:right-[1.2rem]" : "after:left-[1.2rem]"} after:mt-7`}></div>
                                            <img src={adminImage} alt="Example" className="w-8 h-8" />
                                        </span>
                                        <div className="flex bg-floral-white w-full flex-col p-4 relative rounded-md">
                                            <div className={`w-0 h-0 border-t-[0.5rem] border-t-transparent border-b-[0.5rem] border-b-transparent absolute top-4 ${isLoginLanguageRTL ? "-right-[0.38rem] border-l-[7px] border-l-[#FFF9F0]" : "-left-[0.38rem] border-r-[7px] border-r-[#FFF9F0]"}`}></div>
                                            <h4 className="text-sm  text-[#031640]">
                                                {t("viewPolicyDetails.adminComments")}
                                            </h4>
                                            <div className="flex items-center justify-start mt-4">
                                                <div className={`${bgOfStatus(selectedFtmDetails.status)}flex w-fit py-1.5 px-3 text-xs rounded-md`}>
                                                    {getStatusCode(selectedFtmDetails.status, t)}
                                                </div>
                                                <div>
                                                    {ftmDetails.updatedDateTime && (
                                                        <div className="flex">
                                                            <div className={`font-semibold ${isLoginLanguageRTL ? "mr-3" : "ml-3"} text-sm text-dark-blue`}>
                                                                {formatDate(ftmDetails.updatedDateTime, "date", false)}
                                                            </div>
                                                            <div className="mx-3 text-gray-300">|</div>
                                                            <div className="font-semibold text-sm text-dark-blue">
                                                                {formatDate(ftmDetails.updatedDateTime, "time", false)}
                                                            </div>
                                                        </div>
                                                    )}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="mt-4">
                                        <div className="flex font-semibold w-full">
                                            <span className={`w-8 h-8 rounded-full flex justify-center items-center ${isLoginLanguageRTL ? "ml-3" : "mr-3"} text-sm lg:w-10 lg:h-10`}>
                                                <img src={partnerImage} alt="Example" className="w-8 h-8" />
                                            </span>
                                            <div className="flex bg-alice-green w-full flex-col p-4 relative rounded-md">
                                                <div className={`w-0 h-0 border-t-[0.5rem] border-t-transparent border-b-[0.5rem] border-b-transparent absolute top-4 ${isLoginLanguageRTL ? "-right-[0.38rem] border-l-[#F2F5FC] border-l-[7px]" : "-left-[0.38rem] border-r-[#F2F5FC] border-r-[7px]"}`}></div>
                                                <h4 className="text-sm text-[#031640]">
                                                    {t("viewPolicyDetails.partnerComments")}
                                                </h4>
                                                <span className="text-sm mt-3 break-all break-normal break-words">
                                                    {ftmDetails.requestDetail}
                                                </span>
                                                <hr className="h-px w-full bg-gray-200 border-0 my-4" />
                                                <div className="flex items-center justify-start">
                                                    <div className="font-semibold text-xs text-dark-blue">
                                                        {t("viewPolicyDetails.createdOn") + ' ' +
                                                            formatDate(ftmDetails.createdDateTime, "date", false)}
                                                    </div>
                                                    <div className="mx-3 text-gray-300">|</div>
                                                    <div className="font-semibold text-xs text-dark-blue">
                                                        {formatDate(ftmDetails.createdDateTime, "time", false)}
                                                    </div>
                                                </div>
                                            </div>
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