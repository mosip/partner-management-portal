import React, { useState, useEffect} from "react";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { getUserProfile } from "../../services/UserProfileService";
import { bgOfStatus, formatDate, getStatusCode, isLangRTL } from "../../utils/AppUtils";
import Title from "../common/Title";
import fileUpload from '../../svg/file_upload_icon.svg';
import file from '../../svg/file_icon.svg';
import somethingWentWrongIcon from '../../svg/something_went_wrong_icon.svg';
import downloadIcon from '../../svg/download_icon.svg';

function ManageFtmChipCertificate() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [ftmDetails, setFtmDetails] = useState(true);
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [showDownloadDropdown, setShowDownloadDropdown] = useState(false);

    useEffect(() => {
        const selectedFtm = localStorage.getItem('selectedFtmData');
        if (!selectedFtm) {
            setUnexpectedError(true);
            return;
        }
        let ftmData = JSON.parse(selectedFtm);
        setFtmDetails(ftmData);
    }, []);

    const moveToFtmList = () => {
        navigate('/partnermanagement/ftmChipProviderServices/ftmList');
    };

    return (
        <>
            <div className={`flex-col w-full p-5 bg-anti-flash-white h-full font-inter break-all break-normal max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} overflow-x-scroll`}>
                <div className="flex justify-between mb-3">
                    <Title title='manageFtmChipCertificate.manageFtmChipCertificate' subTitle='manageFtmChipCertificate.listOfFtmChipDetails' backLink='/partnermanagement/ftmChipProviderServices/ftmList' ></Title>
                </div>
                {unexpectedError && (
                    <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                        <div className="flex items-center justify-center p-24">
                            <div className="flex flex-col justify-center items-center">
                                <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                <p className="text-sm font-semibold text-[#6F6E6E] py-4">{t('devicesList.unexpectedError')}</p>
                                <button onClick={() => moveToFtmList()} type="button"
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
                                    <div className="flex py-[1rem] px-5 bg-[#F9FBFF] justify-between items-center">
                                        <div className="flex space-x-4 items-center ">
                                            {ftmDetails.isCertificateAvailable
                                                ? <img src={fileUpload} className="h-8" alt="" />
                                                : <img src={file} className="h-8" alt="" />
                                            }

                                            <div className="flex-col p-3 items-center">
                                                <h6 className={`text-sm ${ftmDetails.isCertificateAvailable ? 'font-bold text-black' : 'font-semibold text-charcoal-gray'}`}>
                                                    {ftmDetails.isCertificateAvailable ? t('viewFtmChipDetails.ftmChipCertificate') : t('manageFtmChipCertificate.uploadFtmCertificate')}
                                                </h6>
                                                <p className="text-xs text-light-gray">{ftmDetails.isCertificateAvailable ? null : t('manageFtmChipCertificate.certificateFormatMsg')}</p>
                                            </div>
                                        </div>
                                        <div className=" flex space-x-2">
                                            { ftmDetails.isCertificateAvailable && (
                                                <div className="flex-col">
                                                    <button onClick={() => setShowDownloadDropdown(!showDownloadDropdown)} className={`h-10 w-28 ${isLoginLanguageRTL ? "ml-5" : "mr-5"} flex items-center ${showDownloadDropdown ? 'bg-blue-800 text-white' : 'text-tory-blue bg-white'} text-xs px-[10%] py-[1%] ${isLoginLanguageRTL ? "ml-1" : "mr-1"} text-tory-blue border border-blue-800 font-semibold rounded-lg text-center`}>
                                                        {t('partnerCertificatesList.download')}
                                                        <svg
                                                            xmlns="http://www.w3.org/2000/svg" className={`${showDownloadDropdown ? 'rotate-180 duration-700 text-white' : null} ${isLoginLanguageRTL ? "mr-2" : "ml-2"}`}
                                                            width="10" height="8" viewBox="0 0 10 8">
                                                            <path id="Polygon_8"
                                                                data-name="Polygon 8"
                                                                d="M3.982,1.628a1.2,1.2,0,0,1,2.035,0L8.853,6.164A1.2,1.2,0,0,1,7.835,8H2.165A1.2,1.2,0,0,1,1.147,6.164Z"
                                                                transform="translate(10 8) rotate(180)" fill={`${showDownloadDropdown ? '#ffff' : '#1447b2'}`} />
                                                        </svg>
                                                    </button>
                                                    {showDownloadDropdown && (
                                                        <div className={`w-[18%] min-w-fit absolute py-2 px-1  ${isLoginLanguageRTL ? "origin-bottom-right left-[12.75rem] ml-2" : "origin-bottom-left right-[12.75rem] mr-2"} rounded-md bg-white shadow-lg ring-gray-50 border duration-700`}>
                                                            <div className="flex items-center border-b justify-between cursor-pointer hover:bg-gray-100">
                                                                <button className="block px-4 py-2 text-xs font-semibold text-dark-blue">{t('partnerCertificatesList.originalCertificate')}</button>
                                                                <img src={downloadIcon} alt="" className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"}`} />
                                                            </div>
                                                            <div className="flex items-center justify-between cursor-pointer hover:bg-gray-100">
                                                                <button className="block px-4 py-2 text-xs font-semibold text-dark-blue">{t('partnerCertificatesList.mosipSignedCertificate')}</button>
                                                                <img src={downloadIcon} alt="" className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"}`} />

                                                            </div>
                                                        </div>
                                                    )}
                                                </div>
                                            )}
                                            <button className={`h-10 w-28 text-xs p-3 py-2 ${ftmDetails.isCertificateAvailable ? 'text-tory-blue bg-white border-blue-800' : 'bg-tory-blue text-snow-white'} border font-semibold rounded-md text-center`}>
                                                {ftmDetails.isCertificateAvailable ? t('partnerCertificatesList.reUpload') : t('partnerCertificatesList.upload')}
                                            </button>
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
                                            <p className="font-semibold text-sm text-charcoal-gray">{formatDate(ftmDetails.certificateExpiryDateTime, 'dateTime')}</p>
                                        </div>
                                        <div className={`flex-col ${isLoginLanguageRTL ? "mr-[10%]" : "ml-[10%]"} space-y-1`}>
                                            <p className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.timeOfUpload')}</p>
                                            <p className="font-semibold text-sm text-charcoal-gray">{formatDate(ftmDetails.certificateUploadDateTime, 'dateTime')}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className={`flex justify-end py-5 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                            <button onClick={() => moveToFtmList(navigate)} className={`h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center`}>
                                {t("commons.back")}
                            </button>
                        </div>
                    </div>
                )}
            </div>
        </>
    )
}

export default ManageFtmChipCertificate;