import { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import UploadCertificate from "./UploadCertificate";
import { HttpService } from "../services/HttpService";
import ErrorMessage from "./common/ErrorMessage";
import LoadingIcon from "./common/LoadingIcon";
import Footer from "./common/Footer";
import { formatDate, getPartnerTypeDescription, handleMouseClickForDropdown, getPartnerManagerUrl } from "../utils/AppUtils";
import { useTranslation } from "react-i18next";
import rectangleBox from '../svg/rectangle_box.svg';
import fileUpload from '../svg/file_upload_icon.svg';
import file from '../svg/file_icon.svg';
import downloadIcon from '../svg/download_icon.svg';
import backArrow from '../svg/back_arrow.svg';

function PartnerCertificatesList() {
    const { t } = useTranslation();
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

    const closePopup = (reload) => {
        setShowPopup(false);
        if (reload) {
            window.location.reload();
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
        <div className="flex-col w-full p-5 bg-anti-flash-white h-full font-inter">
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <div className="flex justify-end max-w-7xl">
                            <div className="flex justify-between items-center max-w-96 min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 mr-10">
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                            </div>
                        </div>
                    )}
                    <div className="flex-col ml-1">
                        <div className="flex space-x-4">
                            <img src={backArrow} alt="" onClick={() => moveToHome()} className="mt-1 cursor-pointer" />
                            <div className="flex-col mt-4">
                                <h1 className="font-bold text-md text-blue-900">{t('partnerCertificatesList.partnerCertificate')}</h1>
                                <p onClick={() => moveToHome()} className="font-semibold text-blue-500 text-xs cursor-pointer">
                                    {t('partnerCertificatesList.home')}</p>
                            </div>
                        </div>
                        <ul className="min-w-3.5 bg-white mt-3 rounded-lg shadow-md p-5 mr-8 pb-20">
                            {certificatesData.length === 0 ?
                                <div className="p-14 flex flex-col justify-center items-center w-full">
                                    <img src={rectangleBox} alt="" />
                                    <p className="mt-5 ml-4 font-inter text-xs font-normal tracking-tight text-[#666666]">{t('partnerCertificatesList.noPartnerTypesAreMapped')}</p>
                                </div> :
                                certificatesData.map((partner, index) => {
                                    return (
                                        <li key={index} className="rounded-lg shadow-lg border mb-6">
                                            <div className={`flex p-5 items-center ${partner.isCertificateAvailable ? "bg-green-50" : "bg-slate-100"} justify-between`}>
                                                <div className="flex items-center">
                                                    {partner.isCertificateAvailable
                                                        ? <img src={fileUpload} className="h-11" alt="" />
                                                        : <img src={file} className="h-11" alt="" />
                                                    }

                                                    <div className="flex-col p-3 items-center">
                                                        <h6 className={`text-sm text-gray-600 ${partner.isCertificateAvailable ? 'font-bold' : 'font-medium'}`}>
                                                            {partner.isCertificateAvailable ? partner.certificateName : t('partnerCertificatesList.uploadPartnerCertificate')}
                                                        </h6>
                                                        <p className="font-medium text-xs text-gray-400">{partner.isCertificateAvailable ? null : t('partnerCertificatesList.certificateFormatMsg')}</p>
                                                    </div>
                                                </div>
                                                {partner.isCertificateAvailable
                                                    ? <div className=" flex space-x-4">
                                                        <div className="flex-col" ref={dropdownRef}>
                                                            <button onClick={() => setActiveBtn(!activeBtn)} className={`flex items-center ${activeBtn ? 'bg-blue-800 text-white' : 'text-blue-700'} text-xs px-2 py-2 mr-1 text-blue-700 border border-blue-700 font-semibold rounded-lg text-center`}>
                                                                {t('partnerCertificatesList.download')}
                                                                <svg
                                                                    xmlns="http://www.w3.org/2000/svg" className={`${activeBtn ? 'rotate-180 duration-700 text-white' : null} ml-2`}
                                                                    width="10" height="8" viewBox="0 0 10 8">
                                                                    <path id="Polygon_8"
                                                                        data-name="Polygon 8"
                                                                        d="M3.982,1.628a1.2,1.2,0,0,1,2.035,0L8.853,6.164A1.2,1.2,0,0,1,7.835,8H2.165A1.2,1.2,0,0,1,1.147,6.164Z"
                                                                        transform="translate(10 8) rotate(180)" fill={`${activeBtn ? '#ffff' : '#1447b2'}`} />
                                                                </svg>

                                                            </button>

                                                            {activeBtn && (
                                                                <div className="absolute py-2 px-1 mr-2 right-48 origin-bottom-left rounded-md bg-white shadow-lg ring-gray-50 border duration-700">
                                                                    <div onClick={() => getOriginalCertificate()} className="flex items-center border-b-2 justify-between cursor-pointer">
                                                                        <button className="block px-4 py-2 text-xs font-semibold text-gray-900">{t('partnerCertificatesList.originalCertificate')}</button>
                                                                        <img src={downloadIcon} alt="" />

                                                                    </div>
                                                                    <div onClick={() => getMosipSignedCertificate(partner)} className="flex items-center cursor-pointer">
                                                                        <button className="block px-4 py-2 text-xs font-semibold text-gray-900">{t('partnerCertificatesList.mosipSignedCertificate')}</button>
                                                                        <img src={downloadIcon} alt="" />

                                                                    </div>
                                                                </div>)}
                                                        </div>
                                                        <button onClick={() => clickOnUpload(partner)} className="text-xs p-3 py-2 text-blue-700 border border-blue-700 font-semibold rounded-md text-center">
                                                            {t('partnerCertificatesList.reUpload')}
                                                        </button>
                                                    </div>
                                                    : <button onClick={() => clickOnUpload(partner)} className="bg-tory-blue h-9 w-28 text-white text-sm font-medium rounded-md">
                                                        {t('partnerCertificatesList.upload')}
                                                    </button>}
                                                {showPopup && (
                                                    <UploadCertificate closePopup={closePopup} partnerData={selectedPartnerData} />
                                                )}
                                            </div>
                                            <hr className="border" />
                                            <div className="flex items-center p-5 bg-white">
                                                <div className="flex-col">
                                                    <p className="font-medium text-xs text-gray-400">{t('partnerCertificatesList.partnerType')}</p>
                                                    <p className="font-semibold text-sm text-red-950">{getPartnerType(partner.partnerType)}</p>
                                                </div>
                                                <div className="flex-col ml-12">
                                                    <p className="font-medium text-xs text-gray-400">{t('partnerCertificatesList.expiryDate')}</p>
                                                    <p className="font-semibold text-sm text-red-950">{formatDate(partner.certificateExpiryDate, 'date')}</p>
                                                </div>
                                                <div className="flex-col ml-36">
                                                    <p className="font-medium text-xs text-gray-400">{t('partnerCertificatesList.timeOfUpload')}</p>
                                                    <p className="font-semibold text-sm text-red-950">{formatDate(partner.certificateUploadDate, 'dateTime')}</p>
                                                </div>
                                            </div>
                                        </li>
                                    )
                                }
                                )
                            }
                        </ul>
                    </div>
                    <Footer></Footer>
                </>
            )}
            
        </div>
    );
}

export default PartnerCertificatesList;