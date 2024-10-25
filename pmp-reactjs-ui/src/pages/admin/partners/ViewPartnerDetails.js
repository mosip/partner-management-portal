import React, { useState, useEffect, useRef } from 'react';
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { getUserProfile } from '../../../services/UserProfileService';
import { bgOfStatus, downloadCertificate, formatDate, getPartnerManagerUrl, getStatusCode, getCertificate, handleMouseClickForDropdown, handleServiceErrors, isLangRTL } from '../../../utils/AppUtils';
import ErrorMessage from '../../common/ErrorMessage';
import SuccessMessage from '../../common/SuccessMessage';
import Title from '../../common/Title';

import fileUploadDisabled from '../../../svg/file_upload_disabled_icon.svg';
import fileUploadBlue from '../../../svg/file_upload_blue_icon.svg';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';
import fileUpload from '../../../svg/file_upload_icon.svg';
import DownloadCertificateButton from '../../common/DownloadCertificateButton';
import { HttpService } from '../../../services/HttpService';
import LoadingIcon from '../../common/LoadingIcon';

function ViewPartnerDetails() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(false);
    const [downloadBtnId, setDownloadBtnId] = useState(false);
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [partnerDetails, setPartnerDetails] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const dropdownRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(dropdownRef, () => setDownloadBtnId(false));
    }, [dropdownRef]);

    useEffect(() => {
        const selectedPartnerId = localStorage.getItem('selectedPartnerId');
        if (!selectedPartnerId) {
            setUnexpectedError(true);
            return;
        }

        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(getPartnerManagerUrl('/partners/v3/' + selectedPartnerId, process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        setPartnerDetails(resData);
                    } else {
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('viewPartnerDetails.errorInPartnerList'));
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                setErrorMsg(err);
            }
        };
        fetchData();
    }, []);

    const moveToPartnersList = () => {
        navigate('/partnermanagement/admin/partnersList');
    };

    const getOriginalCertificate = async (partner) => {
        const response = await fetchCertificate(partner.partnerId);
        if (response !== null) {
            if (response.isCaSignedCertificateExpired) {
                setErrorMsg(t('partnerCertificatesList.certificateExpired'));
            } else {
                setSuccessMsg(t('partnerCertificatesList.originalCertificateSuccessMsg'));
                downloadCertificate(response.caSignedCertificateData, 'ca_signed_partner_certificate.cer')
            }
        }
    }

    const getMosipSignedCertificate = async (partner) => {
        const response = await fetchCertificate(partner.partnerId);
        if (response !== null) {
            if (response.isMosipSignedCertificateExpired) {
                setErrorMsg(t('partnerCertificatesList.certificateExpired'));
            } else {
                setSuccessMsg(t('partnerCertificatesList.mosipSignedCertificateSuccessMsg'));
                downloadCertificate(response.mosipSignedCertificateData, 'mosip_signed_certificate.cer')
            }
        }
    }

    const fetchCertificate = async (partnerId) => {
        setErrorCode("");
        setErrorMsg("");
        setSuccessMsg("");
        try {
            const responseData = await getCertificate(HttpService, partnerId, setErrorCode, setErrorMsg);
            if (responseData) {
                const resData = responseData.response;
                return resData;
            }
            else {
                setErrorMsg(t('partnerCertificatesList.errorWhileDownloadingCertificate'));
            }
        } catch (err) {
            console.error('Error fetching certificate:', err);
            setErrorMsg(err);
        }
    }

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const cancelSuccessMsg = () => {
        setSuccessMsg("");
    };

    const dropdownStyle = {
        outerDiv: `w-[18%] min-w-fit absolute py-2 px-1  ${isLoginLanguageRTL ? "origin-bottom-right left-[11.5rem] ml-2" : "origin-bottom-left right-[6rem]"} rounded-md bg-white shadow-lg ring-gray-50 border duration-700`
    }

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter`}>
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
                    <div className={`flex-col bg-anti-flash-white h-full font-inter break-all break-normal max-[450px]:text-sm mb-[2%]`}>
                        <div className="flex justify-between mb-3">
                            <Title title={'viewPartnerDetails.viewPartnerDetails'} subTitle='viewPartnerDetails.listOfPartners' backLink='/partnermanagement/admin/partnersList' />
                        </div>

                        {unexpectedError && (
                            <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center items-center">
                                        <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                        <p className="text-sm font-semibold text-[#6F6E6E] py-4">{t('devicesList.unexpectedError')}</p>
                                        <button onClick={moveToPartnersList} type="button"
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
                                            {partnerDetails.partnerId}
                                        </p>
                                        <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                            <div className={`${partnerDetails.isActive ? 'bg-[#D1FADF] text-[#155E3E]': 'bg-[#EAECF0] text-[#525252]'} flex w-fit py-1 px-5 text-xs rounded-md my-2 font-semibold`}>
                                                {partnerDetails.isActive ? t('statusCodes.activated'): t('statusCodes.deactivated')}
                                            </div>
                                            <div className={`font-semibold ${isLoginLanguageRTL ? "mr-1" : "ml-3"} text-sm text-dark-blue`}>
                                                {t("viewPartnerDetails.createdOn") + ' ' +
                                                    formatDate(partnerDetails.createdDateTime, "date", false)}
                                            </div>
                                            <div className="mx-1 text-gray-300">|</div>
                                            <div className="font-semibold text-sm text-dark-blue">
                                                {formatDate(partnerDetails.createdDateTime, "time", false)}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                                    <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                                        <div className="mb-3 max-[600px]:w-[100%] w-[50%]">
                                            <p className="font-[600] text-suva-gray text-xs">
                                                {t("viewOidcClientDetails.partnerType")}
                                            </p>
                                            <p className="font-[600] text-vulcan text-sm">
                                                {t(partnerDetails.partnerType)}
                                            </p>
                                        </div>
                                        <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                                            <p className="font-[600] text-suva-gray text-xs">
                                                {t("viewPartnerDetails.organizationName")}
                                            </p>
                                            <p className="font-[600] text-vulcan text-sm">
                                                {partnerDetails.organizationName}
                                            </p>
                                        </div>
                                        <div className="mb-3 max-[600px]:w-[100%] w-[50%]">
                                            <p className="font-[600] text-suva-gray text-xs">
                                                {t("userProfile.phoneNumber")}
                                            </p>
                                            <p className="font-[600] text-vulcan text-sm">
                                                {t(partnerDetails.contactNumber ? partnerDetails.contactNumber : '-')}
                                            </p>
                                        </div>
                                        <div className="mb-3 max-[600px]:w-[100%] w-[50%]">
                                            <p className="font-[600] text-suva-gray text-xs">
                                                {t("userProfile.emailAddress")}
                                            </p>
                                            <p className="font-[600] text-vulcan text-sm">
                                                {t(partnerDetails.emailId)}
                                            </p>
                                        </div>
                                        <div className="mb-3 w-[100%]">
                                            <p className="font-[600] text-suva-gray text-xs">
                                                {t("viewOidcClientDetails.policyGroup")}
                                            </p>
                                            <p className="font-[600] text-vulcan text-sm">
                                                {t(partnerDetails.policyGroupName ? partnerDetails.policyGroupName : '-')}
                                            </p>
                                        </div>
                                    </div>
                                    <hr className={`h-px w-full bg-gray-200 border-0 mb-[3%]`} />
                                    <div className="rounded-lg shadow-lg border mb-[2%]">
                                        <div className={`flex-col`}>
                                            <div className={`flex py-[1rem] px-5 ${partnerDetails.isActive === false ? 'bg-gray-100' : 'bg-[#f3fdf3]'} justify-between items-center max-520:flex-col`}>
                                                <div className="flex space-x-4 items-center">
                                                    { partnerDetails.isActive === false
                                                        ? <img src={fileUploadDisabled} className="h-8" alt="" />
                                                        : <img src={fileUpload} className="h-8" alt="" />
                                                    }
                                                    <div className='flex-col p-3 items-center'>
                                                        <h6 className={`text-sm ${partnerDetails.isCertificateAvailable ? 'font-bold text-black' : 'font-semibold text-charcoal-gray'}`}>
                                                            {"Partner Certificate"}
                                                        </h6>
                                                    </div>
                                                </div>
                                                <DownloadCertificateButton
                                                    downloadDropdownRef={dropdownRef}
                                                    setShowDropDown={() => setDownloadBtnId(!downloadBtnId)}
                                                    showDropDown={downloadBtnId}
                                                    onClickFirstOption={getOriginalCertificate}
                                                    onClickSecondOption={getMosipSignedCertificate}
                                                    requiredData={partnerDetails}
                                                    styleSet={dropdownStyle}
                                                    disableBtn={partnerDetails.certificateUploadStatus === 'not_uploaded' || !partnerDetails.isActive}
                                                    id={'download_partner_cer_btn'}
                                                />
                                            </div>
                                            <hr className="border bg-medium-gray h-px" />
                                            <div className="flex items-center p-5 bg-white rounded-lg">
                                                <div className="flex-col space-y-1">
                                                    <p className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.partnerType')}</p>
                                                    <p className="font-bold text-sm text-charcoal-gray">{partnerDetails.partnerType}</p>
                                                </div>
                                                <div className={`flex-col ${isLoginLanguageRTL ? "mr-[5%]" : "ml-[5%]"} space-y-1`}>
                                                    <p className="font-semibold text-xs text-dim-gray">{t('viewPartnerDetails.expiryDate')}</p>
                                                    <p className="font-semibold text-sm text-charcoal-gray">{formatDate(partnerDetails.certificateExpiryDateTime, "dateTime", false)}</p>
                                                </div>
                                                <div className={`flex-col ${isLoginLanguageRTL ? "mr-[10%]" : "ml-[10%]"} space-y-1`}>
                                                    <p className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.timeOfUpload')}</p>
                                                    <p className="font-semibold text-sm text-charcoal-gray">{formatDate(partnerDetails.certificateUploadDateTime, "dateTime", false)}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <hr className="h-px w-full bg-gray-200 border-0" />
                                <div className={`flex justify-end py-5 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                                    <button id="ftm_view_back_btn" onClick={moveToPartnersList} className={`h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center`}>
                                        {t("commons.goBack")}
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

export default ViewPartnerDetails;