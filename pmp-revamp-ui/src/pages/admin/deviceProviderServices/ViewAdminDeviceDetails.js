import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { bgOfStatus, formatDate, getStatusCode, isLangRTL, onPressEnterKey } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import { useNavigate } from 'react-router-dom';
import Title from '../../common/Title';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';

function ViewAdminDeviceDetails() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [deviceDetails, setDeviceDetails] = useState({});

    useEffect(() => {
        const selectedDeviceAttributes = localStorage.getItem('selectedDeviceAttributes');
        if(!selectedDeviceAttributes) {
            setUnexpectedError(true);
            return;
        }
        const selectedDevice = JSON.parse(selectedDeviceAttributes);
        setDeviceDetails(selectedDevice);
    }, []);

    const  backToDevicesList = () => {
        if(deviceDetails.isViewLinkedDevices) {
            return `/partnermanagement/admin/device-provider-services/linked-devices-list?sbiId=${deviceDetails.sbiId}&sbiVersion=${deviceDetails.sbiVersion}`;
        } else {
            return '/partnermanagement/admin/device-provider-services/devices-list';
        }
    }

    const moveToDevicesList = () => {
        navigate(backToDevicesList());
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter relative`}>
            <div className={`flex-col mt-4 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm mb-[2%]`}>
                <div className="flex justify-between mb-3">
                    <Title title={'viewDeviceDetails.viewDeviceDetails'} subTitle='devicesList.listOfDevices' backLink={backToDevicesList()}/>
                </div>

                {unexpectedError && (
                    <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                        <div className="flex items-center justify-center p-24">
                            <div className="flex flex-col justify-center items-center">
                                <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                <p className="text-sm font-semibold text-[#6F6E6E] py-4">{t('devicesList.unexpectedError')}</p>
                                <button onClick={moveToDevicesList} type="button"
                                    className={`w-32 h-10 flex items-center justify-center font-semibold rounded-md text-sm mx-8 py-3 bg-tory-blue text-white`}>
                                    {t('commons.goBack')}
                                </button>
                            </div>
                        </div>
                    </div>
                )}
                {!unexpectedError && (
                    <div className="bg-snow-white h-fit mt-1 rounded-md shadow-lg font-inter">
                        <div className="flex justify-between px-7 pt-3 border-b max-[450px]:flex-col">
                            <div className="flex-col">
                                <p className="text-lg text-dark-blue mb-2">
                                    {t('devicesList.deviceId')}: <span className="font-semibold">{deviceDetails.deviceId}</span>
                                </p>
                                <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                    <div className={`${bgOfStatus(deviceDetails.status)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                        {getStatusCode(deviceDetails.status, t)}
                                    </div>
                                    <div className={`font-semibold ${isLoginLanguageRTL ? "mr-[1.4rem]" : "ml-[0.75rem]"} text-sm text-dark-blue`}>
                                        {t("viewOidcClientDetails.createdOn") + ' ' +
                                            formatDate(deviceDetails.createdDateTime, "date")}
                                    </div>
                                    <div className="mx-2 text-gray-300">|</div>
                                    <div className="font-semibold text-sm text-dark-blue">
                                        {formatDate(deviceDetails.createdDateTime, "time")}
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
                                        {deviceDetails.partnerId}
                                    </p>
                                </div>
                                <div className={`mb-3 max-[600px]:w-[100%] w-[50%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("viewPolicyRequest.partnerType")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md">
                                        {t("partnerTypes.deviceProvider")}
                                    </p>
                                </div>
                                <div className={`mb-3 max-[600px]:w-[100%] w-[48%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("sbiList.orgName")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md">
                                        {deviceDetails.orgName}
                                    </p>
                                </div>
                                <div className={`mb-5 max-[600px]:w-[100%] w-[48%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("sbiList.sbiId")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md">
                                        {deviceDetails.sbiId}
                                    </p>
                                </div>
                            </div>
                            <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                                <div className={`mb-5 max-[600px]:w-[100%] w-[48%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("devicesList.deviceType")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md">
                                        {deviceDetails.deviceType}
                                    </p>
                                </div>
                                <div className={`mb-5 max-[600px]:w-[100%] w-[50%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("devicesList.deviceSubType")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md">
                                        {deviceDetails.deviceSubType}
                                    </p>
                                </div>
                                <div className={`mb-5 max-[600px]:w-[100%] w-[48%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                <p className="font-[600] text-suva-gray text-sm">
                                        {t("devicesList.make")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md">
                                        {deviceDetails.make}
                                    </p>
                                </div>
                                <div className={`mb-5 max-[600px]:w-[100%] w-[48%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("devicesList.model")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md">
                                        {deviceDetails.model}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className={`flex justify-end py-8 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                            <button id="view_admin_device_details_back_btn" onClick={moveToDevicesList}
                                className="h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center" onKeyDown={(e) => onPressEnterKey(e, moveToDevicesList)}>
                                {t("commons.back")}
                            </button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    )
}

export default ViewAdminDeviceDetails;