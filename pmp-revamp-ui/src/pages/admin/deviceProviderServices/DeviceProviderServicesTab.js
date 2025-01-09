import React from 'react';
import { useNavigate } from "react-router-dom";
import { useTranslation } from 'react-i18next';
import { onPressEnterKey } from '../../../utils/AppUtils';

function DeviceProviderServiceTab({ activeSbi, sbiListPath, activeDevice, devicesListPath }) {
    const { t } = useTranslation();
    const navigate = useNavigate();

    const changeToSbi = () => {
        if(!activeSbi) {
            navigate(sbiListPath)
        }
    };

    const changeToDevice = () => {
        if(!activeDevice) {
            navigate(devicesListPath)
        }
    };

    return (
        <div className='flex text-xs bg-[#FCFCFC] font-bold space-x-16 items-start rounded-lg px-[1.5%] pt-[2%]'>
            <div className={`flex-col items-center`}>
                <div role='button' id='sbi_tab' onClick={changeToSbi} className={`${activeSbi ? "text-[#1447b2]" : "text-[#031640]"} mb-[0.6rem] cursor-pointer text-sm text-center`}
                    tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, changeToSbi)}>
                    <h6> {t('deviceProviderServices.sbiDetails')} </h6>
                </div>

                <div className={`h-1 w-20 ${activeSbi ? "bg-tory-blue" : "bg-transparent"}  rounded-t-md`}></div>
            </div>
            <div className={`flex-col justify-center`}>
                <div role='button' id='devices_tab' onClick={changeToDevice} className={`${activeDevice ? "text-[#1447b2]" : "text-[#031640]"} mb-[12%] cursor-pointer text-sm text-center`}
                    tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, changeToDevice)}>
                    <h6> {t('deviceProviderServices.deviceDetails')} </h6>
                </div>

                <div className={`h-1 w-20 ${activeDevice ? "bg-tory-blue" : "bg-transparent"} rounded-t-md`}></div>
            </div>
        </div>
    )
}
export default DeviceProviderServiceTab;