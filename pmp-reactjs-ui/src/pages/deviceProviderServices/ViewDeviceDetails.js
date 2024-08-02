import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { bgOfStatus, formatDate, getStatusCode, isLangRTL } from '../../utils/AppUtils';
import { getUserProfile } from '../../services/UserProfileService';
import { useNavigate } from 'react-router-dom';
import adminImage from "../../svg/admin.png";
import Title from '../common/Title';

function ViewDeviceDetails() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();
    const [deviceDetails, setDeviceDetails] = useState([]);

    const moveToDevicesList = () => {
        navigate('/partnermanagement/deviceProviderServices/viewDevices');
    };

    useEffect(() => {
        const deviceData = localStorage.getItem('selectedDeviceData');
        if (deviceData) {
            try {
                const selectedDevice = JSON.parse(deviceData);
                setDeviceDetails(selectedDevice);
            } catch (error) {
                navigate('/partnermanagement/deviceProviderServices/viewDevices');
                console.error('Error in view device details', error);
            }
        } else {
            moveToDevicesList();
        }
    }, [navigate]);

    const styleForTitle = {
        backArrowIcon: "!mt-[4%]"
    }

    return (
        <>
            <div className={`flex-col w-full p-5 bg-anti-flash-white h-full font-inter break-all break-normal max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} overflow-x-scroll font-inter`}>
                <div className="flex justify-between mb-3">
                    <Title title='viewDeviceDetails.viewDeviceDetails' subTitle='deviceProviderServices.listOfSbisAndDevices' subTitle2='viewDeviceDetails.sbiVersionGoesHere1' backLink='/partnermanagement/deviceProviderServices/viewDevices' backLink2='' styleSet={styleForTitle}></Title>
                </div>
                <div className="bg-snow-white h-fit mt-1 rounded-t-xl shadow-lg font-inter">
                    <div className="flex justify-between px-7 pt-3 border-b max-[450px]:flex-col">
                        <div className="flex-col">
                            <p className="font-bold text-sm text-dark-blue mb-2">
                                Face-Face 123
                            </p>
                            <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                <div className={`${bgOfStatus('APPROVED', t)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                    {getStatusCode('APPROVED', t)}
                                </div>
                                <div className={`font-semibold ${isLoginLanguageRTL ? "mr-1" : "ml-3"} text-sm text-dark-blue`}>
                                    {t("viewDeviceDetails.createdOn") + ' ' +
                                        formatDate('30/05/2024', "date")}
                                </div>
                                <div className="mx-1 text-gray-300">|</div>
                                <div className="font-semibold text-sm text-dark-blue">
                                    {formatDate('14:52:30', "time")}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                        <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                            <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("addDevices.deviceType")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm">
                                    Face
                                </p>
                            </div>
                            <div className="mb-3 max-[600px]:w-[100%] w-[50%]">
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("addDevices.deviceSubType")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm">
                                    Face 123
                                </p>
                            </div>
                        </div>
                        <hr className={`h-px w-full bg-gray-200 border-0`} />
                        <div className={`flex flex-wrap pt-3`}>
                            <div className={`w-[49%] max-[600px]:w-[100%] ${isLoginLanguageRTL ? "ml[1%]" : "mr-[1%]"}`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("addDevices.make")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm break-normal">
                                    MOSIP 123
                                </p>
                            </div>
                            <div className={`w-[50%] max-[600px]:w-[100%]`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("addDevices.model")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm break-normal">
                                    MOSIP 123
                                </p>
                            </div>
                            <div className={`w-[49%] max-[600px]:w-[100%] my-3 ${isLoginLanguageRTL ? "ml[1%]" : "mr-[1%]"}`}>
                                <p className="font-[600] text-suva-gray text-xs">
                                    {t("viewDevices.createdDate")}
                                </p>
                                <p className="font-[600] text-vulcan text-sm break-normal">
                                    Created On
                                </p>
                            </div>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className="mt-3">
                            <p className="font-semibold text-vulcan text-base mb-3">
                                {t("viewDeviceDetails.comments")}
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
                                            {t("viewDeviceDetails.adminComments")} / {t('viewDeviceDetails.adminName')}
                                        </h4>

                                        <p className='break-normal text-md text-[#666666] font-normal'>
                                            Comment goes here
                                        </p>
                                        <div className="flex items-center justify-start mt-4">
                                            <div
                                                className={`${bgOfStatus('APPROVED')}flex w-fit py-1.5 px-3 text-xs rounded-md`}>
                                                {getStatusCode('APPROVED', t)}
                                            </div>
                                            <div>
                                                {deviceDetails.updDtimes && (
                                                    <div className="flex">
                                                        <div className={`font-semibold ${isLoginLanguageRTL ? "mr-3" : "ml-3"} text-sm text-dark-blue`}>
                                                            {formatDate('30/05/2024', "date")}
                                                        </div>
                                                        <div className="mx-3 text-gray-300">|</div>
                                                        <div className="font-semibold text-sm text-dark-blue">
                                                            {formatDate('14:52:30', "time")}
                                                        </div>
                                                    </div>
                                                )}
                                            </div>
                                        </div>
                                        <hr className="h-px w-full bg-gray-200 border-0" />
                                        <div className={`flex justify-end py-5 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                                            <button onClick={() => moveToDevicesList()} className="h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center">
                                                {t("common.back")}
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default ViewDeviceDetails