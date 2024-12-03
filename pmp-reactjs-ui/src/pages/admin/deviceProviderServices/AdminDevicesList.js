import React, { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from '../../common/LoadingIcon';
import EmptyList from '../../common/EmptyList';
import Title from '../../common/Title.js';
import DeviceProviderServicesTab from './DeviceProviderServicesTab.js';
import { isLangRTL } from '../../../utils/AppUtils.js';

function AdminDevicesList () {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [devicesList, setDevicesList] = useState([]);

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'sbiList.partnerId' },
        { id: "orgName", headerNameKey: 'sbiList.orgName' },
        { id: "deviceTypeCode", headerNameKey: 'devicesList.deviceType' },
        { id: "deviceSubTypeCode", headerNameKey: "devicesList.deviceSubType" },
        { id: "make", headerNameKey: "devicesList.make" },
        { id: "model", headerNameKey: "devicesList.model" },
        { id: "createdDateTime", headerNameKey: "devicesList.createdDate" },
        { id: "status", headerNameKey: "devicesList.status" },
        { id: "action", headerNameKey: 'devicesList.action' }
    ];

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
            { !dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            { dataLoaded && (
                <>
                    { errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between mb-5 max-470:flex-col">
                            <Title title='deviceProviderServices.sbiDeviceDetails' backLink='/partnermanagement' ></Title>
                        </div>
                        <DeviceProviderServicesTab
                            activeSbi={false}
                            sbiListPath='/partnermanagement/admin/device-provider-services/sbi-list'
                            activeDevice={true}
                            devicesListPath='/partnermanagement/admin/device-provider-services/devices-list' 
                        />
                        <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                            <EmptyList tableHeaders={tableHeaders} />
                        </div>
                    </div>
                </>
            )}
        </div>
    );

}
export default AdminDevicesList;