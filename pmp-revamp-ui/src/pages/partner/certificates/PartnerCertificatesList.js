import { useEffect, useState, useRef } from "react";
import UploadCertificate from "./UploadCertificate";
import { HttpService } from "../../../services/HttpService";
import { getUserProfile } from "../../../services/UserProfileService";
import ErrorMessage from "../../common/ErrorMessage";
import SuccessMessage from "../../common/SuccessMessage";
import LoadingIcon from "../../common/LoadingIcon";
import { downloadFile, getCertificate, isLangRTL, formatDate, getPartnerTypeDescription, handleMouseClickForDropdown, getPartnerManagerUrl, getPartnerDomainType, handleServiceErrors, isCaSignedPartnerCertificateAvailable, checkCertificateExpired } from "../../../utils/AppUtils";
import { useTranslation } from "react-i18next";

import rectangleBox from '../../../svg/rectangle_box.svg';
import fileUpload from '../../../svg/file_upload_icon.svg';
import file from '../../../svg/file_icon.svg';
import Title from "../../common/Title";
import DownloadCertificateButton from "../../common/DownloadCertificateButton";

function PartnerCertificatesList() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [downloadBtnId, setDownloadBtnId] = useState(-1);
    const [showActiveIndexUploadCertifcatePopup, setShowActiveIndexUploadCertifcatePopup] = useState(null);
    const [selectedPartnerData, setSelectedPartnerData] = useState(null);
    const [certificatesData, setCertificatesData] = useState([]);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(false);
    const [uploadCertificateRequest, setUploadCertificateRequest] = useState({});
    const dropdownRefs = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(dropdownRefs, () => setDownloadBtnId(-1));
    }, [dropdownRefs]);

    const clickOnUpload = (partner, index) => {
        const request = {
            partnerId: partner.partnerId,
            partnerDomain: getPartnerDomainType(partner.partnerType),
        };
        setUploadCertificateRequest(request);
        setShowActiveIndexUploadCertifcatePopup(index);
        setSelectedPartnerData(partner);
    };

    const closePopup = () => {
            setShowActiveIndexUploadCertifcatePopup(null);
            window.location.reload();
    };

    const getPartnerType = (partnerTypeCode) => {
        if (partnerTypeCode) {
            const partnerTypeDesc = getPartnerTypeDescription(partnerTypeCode, t);
            return partnerTypeDesc;
        }
    }

    const getOriginalCertificate = async (partner) => {
        const response = await fetchCertificate(partner.partnerId);
        if (response) {
            if (response.isCaSignedCertificateExpired) {
                setErrorMsg(t('partnerCertificatesList.certificateExpired'));
            } else {
                setSuccessMsg(t('partnerCertificatesList.originalCertificateSuccessMsg'));
                downloadFile(response.caSignedCertificateData, 'ca_signed_partner_certificate.cer', 'application/x-x509-ca-cert')
            }
        }
    }

    const getMosipSignedCertificate = async (partner) => {
        const isApiExist = await isCaSignedPartnerCertificateAvailable();
        let response = null;

        if (isApiExist) {
            response = await fetchCertificate(partner.partnerId);
        } else {
            const isCertExpired = checkCertificateExpired(partner.certificateExpiryDateTime);
            if (isCertExpired) {
                setErrorMsg(t('partnerCertificatesList.certificateExpired'));
                return;
            }
            response = await fetchCertificate(partner.partnerId);
        }
        if (response) {
            if (response.isMosipSignedCertificateExpired) {
                setErrorMsg(t('partnerCertificatesList.certificateExpired'));
            } else {
                setSuccessMsg(t('partnerCertificatesList.mosipSignedCertificateSuccessMsg'));
                downloadFile(isApiExist ? response.mosipSignedCertificateData : response.certificateData, 'mosip_signed_certificate.cer', 'application/x-x509-ca-cert')
            }
        }
    }

    const fetchCertificate = async (partnerId) => {
        setErrorCode("");
        setErrorMsg("");
        setSuccessMsg("");
        try {
            const responseData = await getCertificate(HttpService, partnerId, setErrorCode, setErrorMsg, t);
            if (responseData) {
                const resData = responseData.response;
                return resData;
            } else {
                return null;
            }
        } catch (err) {
            console.error('Error fetching certificate:', err);
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            return null;
        }
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await HttpService.get(getPartnerManagerUrl('/partners/partner-certificates-details', process.env.NODE_ENV));
                if (response != null) {
                    const responseData = response.data;
                    if (responseData.errors && responseData.errors.length > 0) {
                        const errorCode = response.data.errors[0].errorCode;
                        if (errorCode === 'PMS_KKS_001') {
                            setErrorMsg(t('partnerCertificatesList.errorWhileFetchingCertificateList'));
                        } else {
                            handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                        }
                    } else {
                        const resData = responseData.response;
                        setCertificatesData(resData);
                        console.log('Response data:', resData);
                    }
                } else {
                    setErrorMsg(t('partnerCertificatesList.errorInCertificateList'));
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                if (err.response?.status && err.response.status !== 401) {
                    setErrorMsg(err.toString());
                }
            }
        };
        fetchData();
    }, []);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const cancelSuccessMsg = () => {
        setSuccessMsg("");
    };

    const style = {
        backArrowIcon: "!mt-[6%]"
    };

    const dropdownStyle = {
        outerDiv: `w-[18%] min-w-fit absolute py-2 px-1  ${isLoginLanguageRTL ? "origin-bottom-right left-[11.5rem] ml-2" : "origin-bottom-left right-[11.5rem] mr-2"} rounded-md bg-white shadow-lg ring-gray-50 border duration-700`
    }

    return (
        <div className={`mt-2 w-full ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll relative`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    {successMsg && (
                        <SuccessMessage successMsg={successMsg} clickOnCancel={cancelSuccessMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <div className="flex justify-between mb-5">
                            <Title title='partnerCertificatesList.partnerCertificate' backLink='/partnermanagement' styleSet={style} />
                        </div>
                        <ul className="w-[100%] bg-snow-white mt-[1.5%] rounded-lg shadow-md p-[2.5%] pb-[10%]">
                            {certificatesData.length === 0 ?
                                <div className="p-[8%] flex flex-col justify-center items-center w-full">
                                    <img src={rectangleBox} alt="" />
                                    <p className="mt-[1.5%] font-inter text-xs font-normal tracking-tight text-[#666666]">{t('partnerCertificatesList.noPartnerTypesAreMapped')}</p>
                                </div> :
                                certificatesData.map((partner, index) => {
                                    return (
                                        <li key={index} className="rounded-lg shadow-lg border mb-[2%]">
                                            <div className={`flex h-[93px] p-[2.5%] items-center ${partner.isCertificateAvailable ? "bg-[#F9FBFF]" : "bg-[#F4FAF4]"} justify-between`}>
                                                <div className="flex items-center">
                                                    {partner.isCertificateAvailable
                                                        ? <img src={fileUpload} className="h-8" alt="" />
                                                        : <img src={file} className="h-8" alt="" />
                                                    }

                                                    <div className="flex-col p-3 items-center">
                                                        <h6 className={`text-sm ${partner.isCertificateAvailable ? 'font-bold text-black' : 'font-semibold text-charcoal-gray'}`}>
                                                            {partner.isCertificateAvailable ? partner.certificateIssuedTo : t('partnerCertificatesList.uploadPartnerCertificate')}
                                                        </h6>
                                                        <p className="text-xs text-light-gray">{partner.isCertificateAvailable ? null : t('partnerCertificatesList.certificateFormatMsg')}</p>
                                                    </div>
                                                </div>
                                                {partner.isCertificateAvailable
                                                    ? <div className="flex gap-x-6">
                                                        <DownloadCertificateButton
                                                            downloadDropdownRef={el => dropdownRefs.current[index] = el}
                                                            setShowDropDown={() => setDownloadBtnId(downloadBtnId === index ? null : index)}
                                                            showDropDown={downloadBtnId === index}
                                                            onClickFirstOption={getOriginalCertificate}
                                                            onClickSecondOption={getMosipSignedCertificate}
                                                            requiredData={partner}
                                                            styleSet={dropdownStyle}
                                                            disableBtn={!partner.isPartnerActive}
                                                            disabledBtnHoverMsg="partnerCertificatesList.disabledBtnHoverMsg"
                                                            id={'download_btn' + (index + 1)}
                                                        />
                                                        <div className="relative group" tabIndex="0">
                                                            <button disabled={!partner.isPartnerActive} id={"partner_certificate_re_upload_btn" + (index + 1)} onClick={() => clickOnUpload(partner, index)} className={`h-10 w-28 relative text-xs p-3 py-2 ${partner.isPartnerActive ? "text-tory-blue bg-white border border-blue-800" : "bg-white border border-gray-300 text-[#6f7070]"}  font-semibold rounded-md text-center`}>
                                                                {t('partnerCertificatesList.reUpload')}
                                                            </button>
                                                            {!partner.isPartnerActive && (
                                                                <div className={`absolute hidden group-hover:block group-focus:block text-center bg-gray-100 text-xs text-gray-500 font-semibold p-2 w-60 mt-1 z-10 ${isLoginLanguageRTL ? "left-0" : "right-0"} top-11  rounded-md shadow-md`}>
                                                                    {t('partnerCertificatesList.disabledBtnHoverMsg')}
                                                                </div>
                                                            )}
                                                        </div>
                                                    </div>
                                                    : <button id={"partner_certificate_upload_btn" + (index + 1)} onClick={() => clickOnUpload(partner, index)} className="bg-tory-blue h-10 w-28 text-snow-white text-xs font-semibold rounded-md">
                                                        {t('partnerCertificatesList.upload')}
                                                    </button>}
                                                {showActiveIndexUploadCertifcatePopup === index && (
                                                    <UploadCertificate closePopup={closePopup} popupData={{ ...selectedPartnerData, isUploadPartnerCertificate: true, uploadHeader: 'uploadCertificate.uploadPartnerCertificate', reUploadHeader: 'uploadCertificate.reUploadPartnerCertificate' }} request={uploadCertificateRequest} />
                                                )}
                                            </div>
                                            <hr className="border bg-medium-gray" />
                                            <div className="flex items-center p-5 bg-white rounded-lg">
                                                <div className="flex-col">
                                                    <p className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.partnerType')}</p>
                                                    <p className="font-bold text-sm text-charcoal-gray">{getPartnerType(partner.partnerType)}</p>
                                                </div>
                                                <div className={`flex-col ${isLoginLanguageRTL ? "mr-[5%]" : "ml-[5%]"}`}>
                                                    <p className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.expiryDate')}</p>
                                                    <p className="font-semibold text-sm text-charcoal-gray">{formatDate(partner.certificateExpiryDateTime, 'dateTime')}</p>
                                                </div>
                                                <div className={`flex-col ${isLoginLanguageRTL ? "mr-[10%]" : "ml-[10%]"}`}>
                                                    <p className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.timeOfUpload')}</p>
                                                    <p className="font-semibold text-sm text-charcoal-gray">{formatDate(partner.certificateUploadDateTime, 'dateTime')}</p>
                                                </div>
                                            </div>
                                        </li>
                                    )
                                }
                                )
                            }
                        </ul>
                    </div>

                </>
            )}

        </div>
    );
}

export default PartnerCertificatesList;