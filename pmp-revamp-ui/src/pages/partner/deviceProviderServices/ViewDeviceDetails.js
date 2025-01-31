import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { bgOfStatus, formatDate, getStatusCode, isLangRTL, moveToSbisList } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import { useNavigate } from 'react-router-dom';
import Title from '../../common/Title';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';

function ViewDeviceDetails() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();
    const [deviceDetails, setDeviceDetails] = useState([]);
    const [selectedSbidata, setSelectedSbidata] = useState(true);
    const [unexpectedError, setUnexpectedError] = useState(false);

    const moveToDevicesList = () => {
        navigate('/partnermanagement/device-provider-services/devices-list');
    };

    useEffect(() => {
        const selectedSbi = localStorage.getItem('selectedSbiData');
        if (!selectedSbi) {
            setUnexpectedError(true);
            return;
        }
        let sbiData = JSON.parse(selectedSbi);
        setSelectedSbidata(sbiData);

        const deviceData = localStorage.getItem('selectedDeviceData');
        if (!deviceData) {
            setUnexpectedError(true);
            return;
        }
        const selectedDevice = JSON.parse(deviceData);
        setDeviceDetails(selectedDevice);
    }, []);



    return (
        <>
            <div className={`w-full p-5 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} overflow-x-scroll font-inter`}>

                <>
                    <div className="flex justify-between mb-3">
                        <Title title='viewDeviceDetails.viewDeviceDetails' subTitle='sbiList.listOfSbi' subTitle2='devicesList.listOfDevices' backLink='/partnermanagement/device-provider-services/sbi-list' backLink2='/partnermanagement/device-provider-services/devices-list' />
                    </div>
                    {unexpectedError && (
                        <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                            <div className="flex items-center justify-center p-24">
                                <div className="flex flex-col justify-center items-center">
                                    <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                    <p className="text-sm font-semibold text-[#6F6E6E] py-4">{t('devicesList.unexpectedError')}</p>
                                    <button onClick={() => moveToSbisList(navigate)} type="button"
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
                                        {t('devicesList.deviceId')}: <span className="font-semibold">{deviceDetails.deviceId}</span>
                                    </p>
                                    <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                        <div className={`${bgOfStatus(deviceDetails.status, t)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                            {getStatusCode(deviceDetails.status, t)}
                                        </div>
                                        <div className={`font-semibold ${isLoginLanguageRTL ? "mr-1" : "ml-3"} text-sm text-dark-blue`}>
                                            {t("viewDeviceDetails.createdOn") + ' ' +
                                                formatDate(deviceDetails.createdDateTime, "date")}
                                        </div>
                                        <div className="mx-1 text-gray-300">|</div>
                                        <div className="font-semibold text-sm text-dark-blue">
                                            {formatDate(deviceDetails.createdDateTime, "time")}
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                                <div className="flex flex-wrap py-1 justify-between">
                                    <div className="w-[49%] max-[600px]:w-[100%] mb-3">
                                        <p id="device_details_partner_id_label" className="font-[600] text-suva-gray text-xs">
                                            {t("viewOidcClientDetails.partnerId")}
                                        </p>
                                        <p id="device_details_partner_id_context" className="font-[600] text-vulcan text-sm">
                                            {selectedSbidata.partnerId}
                                        </p>
                                    </div>
                                    <div className="mb-3 max-[600px]:w-[100%] w-[49%]">
                                        <p id="device_details_partner_type_label" className="font-[600] text-suva-gray text-xs">
                                            {t("viewOidcClientDetails.partnerType")}
                                        </p>
                                        <p id="device_details_partner_type_context" className="font-[600] text-vulcan text-sm">
                                            {t("partnerTypes.deviceProvider")}
                                        </p>
                                    </div>
                                    <div className="w-[49%] max-[600px]:w-[100%] mb-3">
                                        <p id="device_details_device_type_label" className="font-[600] text-suva-gray text-xs">
                                            {t("addDevices.deviceType")}
                                        </p>
                                        <p id="device_details_device_type_context" className="font-[600] text-vulcan text-sm">
                                            {deviceDetails.deviceTypeCode}
                                        </p>
                                    </div>
                                    <div className="mb-3 max-[600px]:w-[100%] w-[49%]">
                                        <p id="device_details_device_sub_type_label" className="font-[600] text-suva-gray text-xs">
                                            {t("addDevices.deviceSubType")}
                                        </p>
                                        <p id="device_details_device_sub_type_context" className="font-[600] text-vulcan text-sm">
                                            {deviceDetails.deviceSubTypeCode}
                                        </p>
                                    </div>
                                </div>
                                <hr className={`h-px w-full bg-gray-200 border-0`} />
                                <div className={`flex flex-wrap pt-2 justify-between`}>
                                    <div className={`w-[49%] pt-2 max-[600px]:w-[100%] ${isLoginLanguageRTL ? "ml[1%]" : "mr-[1%]"}`}>
                                        <p id="device_details_make_label" className="font-[600] text-suva-gray text-xs">
                                            {t("addDevices.make")}
                                        </p>
                                        <p id="device_details_make_context" className="font-[600] text-vulcan text-sm">
                                            {deviceDetails.make}
                                        </p>
                                    </div>
                                    <div className={`w-[49%] pt-2 max-[600px]:w-[100%]`}>
                                        <p id="device_details_model_label" className="font-[600] text-suva-gray text-xs">
                                            {t("addDevices.model")}
                                        </p>
                                        <p id="device_details_model_context" className="font-[600] text-vulcan text-sm">
                                            {deviceDetails.model}
                                        </p>
                                    </div>
                                    <div className={`w-[49%] max-[600px]:w-[100%] my-3 ${isLoginLanguageRTL ? "ml[1%]" : "mr-[1%]"}`}>
                                        <p id="device_details__sbi_version_label" className="font-[600] text-suva-gray text-xs">
                                            {t("addSbis.sbiVersion")}
                                        </p>
                                        <p id="device_details_sbi_version_context" className="font-[600] text-vulcan text-sm">
                                            {selectedSbidata.sbiVersion}
                                        </p>
                                    </div>
                                </div>
                                <hr className="h-px w-full bg-gray-200 border-0" />
                                <div className={`flex justify-end py-5 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                                    <button id='view_device_details_back_btn' onClick={() => moveToDevicesList()} className="h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center">
                                        {t("commons.back")}
                                    </button>
                                </div>
                            </div>
                        </div>
                    )}
                </>
            </div>
        </>
    )
}

export default ViewDeviceDetails;