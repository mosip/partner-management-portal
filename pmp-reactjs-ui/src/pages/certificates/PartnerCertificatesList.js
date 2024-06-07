import { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import UploadCertificate from "./UploadCertificate";
import { HttpService } from "../../services/HttpService";
import { getUserProfile } from "../../services/UserProfileService";
import { isLangRTL } from "../../utils/AppUtils";
import ErrorMessage from "../common/ErrorMessage";
import LoadingIcon from "../common/LoadingIcon";
import { formatDate, getPartnerTypeDescription, handleMouseClickForDropdown, getPartnerManagerUrl } from "../../utils/AppUtils";
import { useTranslation } from "react-i18next";

import rectangleBox from '../../svg/rectangle_box.svg';
import fileUpload from '../../svg/file_upload_icon.svg';
import file from '../../svg/file_icon.svg';
import downloadIcon from '../../svg/download_icon.svg';
import backArrow from '../../svg/back_arrow.svg';

function PartnerCertificatesList() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [activeBtn, setActiveBtn] = useState(false);
    const navigate = useNavigate();
    const [showPopup, setShowPopup] = useState(false);
    const [selectedPartnerData, setSelectedPartnerData] = useState(null);
    const [certificatesData, setcertificatesData] = useState([]);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(false);
    const dropdownRef = useRef(null);

    useEffect(() => {
        const clickOutSideDropdown = handleMouseClickForDropdown(dropdownRef, () => setActiveBtn(false));
        return clickOutSideDropdown;
    }, [dropdownRef]);

    const clickOnUpload = (partner) => {
        setShowPopup(!showPopup);
        setSelectedPartnerData(partner);
    };

    const closePopup = (state, buttonName) => {
        if (state) {
            setShowPopup(false);
            if (buttonName === "close") {
                window.location.reload();
            }
        }
    };

    const getPartnerType = (partnerTypeCode) => {
        if (partnerTypeCode) {
            const partnerTypeDesc = getPartnerTypeDescription(partnerTypeCode, t);
            return partnerTypeDesc;
        }
    }

    const moveToHome = () => {
        navigate('/partnermanagement')
    };

    const getOriginalCertificate = () => {
        setErrorMsg(t('partnerCertificatesList.errorMsgForOriginalCertificate'))
    }

    const getMosipSignedCertificate = async (partner) => {
        try {
            const response = await HttpService.get(getPartnerManagerUrl('/partners/' + partner.partnerId + '/certificate', process.env.NODE_ENV));
            if (response != null) {
                const responseData = response.data;
                if (responseData.errors && responseData.errors.length > 0) {
                    const errorCode = responseData.errors[0].errorCode;
                    const errorMessage = responseData.errors[0].message;
                    setErrorCode(errorCode);
                    setErrorMsg(errorMessage);
                    console.error('Error:', errorMessage);
                } else {
                    const resData = responseData.response;
                    console.log('Response data:', resData);
                    const blob = new Blob([resData.certificateData], { type: 'application/x-x509-ca-cert' });
                    const url = window.URL.createObjectURL(blob);
                    const link = document.createElement('a');
                    link.href = url;
                    link.download = 'mosip_signed_certificate.cer';

                    document.body.appendChild(link);
                    link.click();

                    // Cleanup
                    window.URL.revokeObjectURL(url);
                    document.body.removeChild(link);
                }

            } else {
                setErrorMsg(t('partnerCertificatesList.errorWhileDownloadingCertificate'));
            }

        } catch (err) {
            console.error('Error fetching certificate:', err);
            setErrorMsg(err);
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await HttpService.get(getPartnerManagerUrl('/partners/getAllCertificateDetails', process.env.NODE_ENV));
                if (response != null) {
                    const responseData = response.data;
                    if (responseData.errors && responseData.errors.length > 0) {
                        const errorCode = responseData.errors[0].errorCode;
                        const errorMessage = responseData.errors[0].message;
                        setErrorCode(errorCode);
                        setErrorMsg(errorMessage);
                        console.error('Error:', errorMessage);
                    } else {
                        const resData = responseData.response;
                        setcertificatesData(resData);
                        console.log('Response data:', resData);
                    }
                } else {
                    setErrorMsg(t('partnerCertificatesList.errorInCertificateList'));
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                setErrorMsg(err);
            }
        };
        fetchData();
    }, [t]);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    return (
        <div className={`mt-5 w-full ${isLoginLanguageRTL ? "mr-32 ml-5": "ml-32 mr-5"} overflow-x-scroll relative`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <div className="flex justify-end max-w-7xl absolute right-0">
                            <div className="flex justify-between items-center max-w-96 min-h-14 min-w-72 bg-[#C61818] rounded-xl p-4">
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                            </div>
                        </div>
                    )}
                    <div className="flex-col">
                        <div className="flex justify-between mb-5">
                            <div className="flex items-start space-x-3">
                                <img src={backArrow} alt="" onClick={() => moveToHome()} className={`mt-[9%] cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} />
                                <div className="flex-col">
                                    <h1 className="font-semibold text-xl text-dark-blue">{t('partnerCertificatesList.partnerCertificate')}</h1>
                                    <p onClick={() => moveToHome()} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                        {t('commons.home')}
                                    </p>
                                </div>
                            </div>
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
                                                        ? <img src={fileUpload} className="h-10" alt="" />
                                                        : <img src={file} className="h-10" alt="" />
                                                    }

                                                    <div className="flex-col p-3 items-center">
                                                        <h6 className={`text-base ${partner.isCertificateAvailable ? 'font-bold text-black' : 'font-medium text-charcoal-gray'}`}>
                                                            {partner.isCertificateAvailable ? partner.certificateName : t('partnerCertificatesList.uploadPartnerCertificate')}
                                                        </h6>
                                                        <p className="text-xs text-light-gray">{partner.isCertificateAvailable ? null : t('partnerCertificatesList.certificateFormatMsg')}</p>
                                                    </div>
                                                </div>
                                                {partner.isCertificateAvailable
                                                    ? <div className=" flex space-x-4">
                                                        <div className="flex-col" ref={dropdownRef}>
                                                            <button onClick={() => setActiveBtn(!activeBtn)} className={`h-11 w-[120px] flex items-center ${activeBtn ? 'bg-blue-800 text-white' : 'text-tory-blue bg-white'} text-sm px-[14%] py-[1%] ${isLoginLanguageRTL?"ml-1":"mr-1"} text-tory-blue border border-blue-800 font-semibold rounded-lg text-center`}>
                                                                {t('partnerCertificatesList.download')}
                                                                <svg
                                                                    xmlns="http://www.w3.org/2000/svg" className={`${activeBtn ? 'rotate-180 duration-700 text-white' : null} ${isLoginLanguageRTL?"mr-2":"ml-2"}`}
                                                                    width="10" height="8" viewBox="0 0 10 8">
                                                                    <path id="Polygon_8"
                                                                        data-name="Polygon 8"
                                                                        d="M3.982,1.628a1.2,1.2,0,0,1,2.035,0L8.853,6.164A1.2,1.2,0,0,1,7.835,8H2.165A1.2,1.2,0,0,1,1.147,6.164Z"
                                                                        transform="translate(10 8) rotate(180)" fill={`${activeBtn ? '#ffff' : '#1447b2'}`} />
                                                                </svg>

                                                            </button>

                                                            {activeBtn && (
                                                                <div className={`w-[18%] absolute py-2 px-1  ${isLoginLanguageRTL?"origin-bottom-right left-52 ml-2":"origin-bottom-left right-52 mr-2"} rounded-md bg-white shadow-lg ring-gray-50 border duration-700`}>
                                                                    <div onClick={() => getOriginalCertificate()} className="flex items-center border-b justify-between cursor-pointer">
                                                                        <button className="block px-4 py-2 text-xs font-semibold text-dark-blue">{t('partnerCertificatesList.originalCertificate')}</button>
                                                                        <img src={downloadIcon} alt="" className={`${isLoginLanguageRTL?"ml-2":"mr-2"}`} />

                                                                    </div>
                                                                    <div onClick={() => getMosipSignedCertificate(partner)} className="flex items-center justify-between cursor-pointer">
                                                                        <button className="block px-4 py-2 text-xs font-semibold text-dark-blue">{t('partnerCertificatesList.mosipSignedCertificate')}</button>
                                                                        <img src={downloadIcon} alt="" className={`${isLoginLanguageRTL?"ml-2":"mr-2"}`}/>

                                                                    </div>
                                                                </div>)}
                                                        </div>
                                                        <button onClick={() => clickOnUpload(partner)} className="h-11 w-[120px] text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center">
                                                            {t('partnerCertificatesList.reUpload')}
                                                        </button>
                                                    </div>
                                                    : <button onClick={() => clickOnUpload(partner)} className="bg-tory-blue h-11 w-[120px] text-snow-white text-sm font-semibold rounded-md">
                                                        {t('partnerCertificatesList.upload')}
                                                    </button>}
                                                {showPopup && (
                                                    <UploadCertificate closePopup={closePopup} partnerData={selectedPartnerData} />
                                                )}
                                            </div>
                                            <hr className="border bg-medium-gray" />
                                            <div className="flex items-center p-5 bg-white rounded-lg">
                                                <div className="flex-col">
                                                    <p className="font-medium text-sm text-dim-gray">{t('partnerCertificatesList.partnerType')}</p>
                                                    <p className="font-bold text-base text-charcoal-gray">{getPartnerType(partner.partnerType)}</p>
                                                </div>
                                                <div className="flex-col ml-12">
                                                    <p className="font-medium text-sm text-dim-gray">{t('partnerCertificatesList.expiryDate')}</p>
                                                    <p className="font-semibold text-base text-charcoal-gray">{formatDate(partner.certificateExpiryDate, 'date')}</p>
                                                </div>
                                                <div className="flex-col ml-36">
                                                    <p className="font-medium text-sm text-dim-gray">{t('partnerCertificatesList.timeOfUpload')}</p>
                                                    <p className="font-semibold text-base text-charcoal-gray">{formatDate(partner.certificateUploadDate, 'dateTime')}</p>
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