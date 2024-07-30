import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService.js';
import Title from '../common/Title.js';
import { isLangRTL, onPressEnterKey, bgOfStatus, getStatusCode, getPartnerTypeDescription } from '../../utils/AppUtils.js';
import LoadingIcon from "../common/LoadingIcon.js";
import ErrorMessage from '../common/ErrorMessage.js';
import rectangleGrid from '../../svg/rectangle_grid.svg';
import downArrow from '../../svg/down_arrow.svg';
import upArrow from '../../svg/up_arrow.svg';
import verifiedIcon from '../../svg/verified_icon.svg';

function SbiList () {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [open, setOpen] = useState(-1);
    const [deactivateBtnId, setDeactivateBtnId] = useState(false);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const listOfSbis = [
        { "sbiVersion": "1.0.0", "status": "approved", "listOfDevices": 24, "partnerId": "P28394091", "partnerType": "Device_Provider", "submittedOn": "2024-07-23", "crDtimes": "2024-04-26 T00:00:00.435Z", "expDtimes": "2024-04-26 T00:00:00.435Z"},
        { "sbiVersion": "2.0.0", "status": "InProgress", "listOfDevices": 4, "partnerId": "P28394092", "partnerType": "Device_Provider", "submittedOn": "2024-07-23", "crDtimes": "2024-04-27 T00:00:00.435Z", "expDtimes": "2024-04-27 T00:00:00.435Z"},
        { "sbiVersion": "3.0.0", "status": "rejected", "listOfDevices": 20, "partnerId": "P28394093", "partnerType": "Device_Provider", "submittedOn": "2024-07-23", "crDtimes": "2024-04-28 T00:00:00.435Z", "expDtimes": "2024-04-28 T00:00:00.435Z"},
        { "sbiVersion": "4.0.0", "status": "deactivated", "listOfDevices": 23, "partnerId": "P28394094", "partnerType": "Device_Provider", "submittedOn": "2024-07-23", "crDtimes": "2024-04-29 T00:00:00.435Z", "expDtimes": "2024-04-29 T00:00:00.435Z"},
        { "sbiVersion": "5.0.0", "status": "approved", "listOfDevices": 32, "partnerId": "P28394095", "partnerType": "Device_Provider", "submittedOn": "2024-07-23", "crDtimes": "2024-04-30 T00:00:00.435Z", "expDtimes": "2024-04-30 T00:00:00.435Z"}
    ];

    const addSbi = () => {
        navigate('/partnermanagement/deviceProviderServices/addSbi');
    };

    const addDevices = (sbi) => {
        if (sbi.status !== "deactivated") {
            navigate('/partnermanagement/deviceProviderServices/addDevices');
        }
    };

    const viewDevices = () => {
        navigate('/partnermanagement/deviceProviderServices/viewDevices');
    };

    const onClickAction = (sbi, index) => {
        if (sbi.status !== "deactivated") {
            setDeactivateBtnId(deactivateBtnId === index ? null : index)
        }
    };

    const styleForTitle = {
        backArrowIcon: "!mt-[5%]"
    }

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <div className={`flex justify-end max-w-7xl mb-5 mt-2 absolute ${isLoginLanguageRTL ? "left-0" : "right-2"}`}>
                            <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 z-10">
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                            </div>
                        </div>
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between mb-5">
                            <Title title='deviceProviderServices.listOfSbisAndDevices' backLink='/partnermanagement' styleSet={styleForTitle}></Title>
                            {listOfSbis.length > 0 ?
                                <button type="button" onClick={() => addSbi()} tabIndex="0" onKeyPress={(e)=>onPressEnterKey(e,addSbi())}
                                    className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                                    {t('sbiList.addSbi')}
                                </button>
                                : null
                            }
                        </div>
                        {listOfSbis.length === 0 ? 
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <div className="flex items-center justify-center p-44">
                                    <div className="flex flex-col items-center">
                                        <img src={rectangleGrid} alt="" />
                                        <button onClick={() => addSbi()} type="button" className="text-white font-semibold mt-8 bg-tory-blue rounded-md text-sm h-11 px-5 py-3">
                                            {t('sbiList.addSbiDevice')}
                                        </button>
                                    </div>
                                </div>
                            </div> :
                            listOfSbis.map((sbi, index) => {
                                return (
                                    <div key={index} className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center max-[510px]:overflow-x-scroll">
                                        <div className="p-4">
                                            <div className="flex flex-row max-[670px]:flex-col justify-between items-center max-[670px]:items-start">
                                                <div className="flex flex-row justify-between items-center max-[670px]:mb-2">
                                                    <img src={verifiedIcon} alt="" className={`${isLoginLanguageRTL ? "ml-4" : "mr-4"}`}></img>
                                                    <div className="flex flex-col">
                                                        <p className={`text-base font-bold ${sbi.status === "deactivated" ? 'text-[#8E8E8E]' : 'text-dark-blue'}`}>{sbi.sbiVersion}</p>
                                                        <div className="flex flex-row items-center justify-between">
                                                            <div className={`${bgOfStatus(sbi.status)} flex w-fit py-1.5 px-2 ${isLoginLanguageRTL ? "ml-1" : "mr-1"} text-xs font-semibold rounded-md`}>
                                                                {getStatusCode(sbi.status, t)}
                                                            </div>
                                                            <p className="text-xs font-semibold text-[#505E7C]"><span className={`text-xs font-semibold ${sbi.status === "deactivated" ? 'text-[#4F5E7C]' : 'text-tory-blue cursor-pointer'} `}>{sbi.listOfDevices} {t('sbiList.devices')}</span> {t('sbiList.devicesCountTxt')}</p>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="flex flex-row justify-between items-center space-x-6 relative">
                                                    <div className="flex flex-row justify-between items-center space-x-3">
                                                        <button onClick={() => addDevices(sbi)} className={`${sbi.status === "deactivated" ? 'border-[#A5A5A5] bg-[#A5A5A5] cursor-auto' : 'bg-tory-blue border-[#1447B2]'} h-10 w-28 text-white text-xs font-semibold rounded-md ${isLoginLanguageRTL && "ml-3"}`}>{t('sbiList.addDevices')}</button>
                                                        <button onClick={() => viewDevices()} className="h-10 w-28 text-xs px-3 py-1 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center">{t('sbiList.viewDevices')}</button>
                                                        <button onClick={() => onClickAction(sbi, index)} className={`h-10 w-8 text-lg pb-3 ${sbi.status === "deactivated" ? 'border-[#A5A5A5] text-dim-gray cursor-auto' : 'text-tory-blue border-[#1447B2]'} bg-white border font-bold rounded-md text-center`}>...</button>
                                                        {deactivateBtnId === index && (
                                                            <div className={`w-[17rem] min-w-fit absolute top-full mt-2 ${isLoginLanguageRTL ? "left-[3.25rem]" : "right-[3.25rem]"} rounded-md bg-white shadow-lg ring-gray-50 border duration-700`}>
                                                                <div className="flex items-center justify-between cursor-pointer">
                                                                    <button className="block px-4 py-2 text-sm font-medium text-crimson-red">{t('sbiList.deactivate')}</button>
                                                                </div>
                                                            </div>
                                                        )}
                                                    </div>
                                                    {open === index ? 
                                                        <img src={upArrow} alt="" className="cursor-pointer" onClick={() => setOpen(index === open ? null : index)}></img>
                                                        :
                                                        <img src={downArrow} alt="" className="cursor-pointer" onClick={() => setOpen(index === open ? null : index)}></img>
                                                    }                              
                                                </div>
                                            </div>
                                        </div>
                                        {open === index && (
                                            <div>
                                                <hr className="border bg-medium-gray" />
                                                <div className="p-6">
                                                    <div className="flex flex-col">
                                                        <div className="flex flex-row justify-between items-center">
                                                            <div className="flex flex-col w-1/3">
                                                                <p className="font-semibold text-sm text-suva-gray">{t('sbiList.partnerId')}</p>
                                                                <p className="font-semibold text-base text-vulcan">{sbi.partnerId}</p>
                                                            </div>
                                                            <div className={`flex flex-col w-1/3`}>
                                                                <p className="font-semibold text-sm text-suva-gray">{t('sbiList.partnerType')}</p>
                                                                <p className="font-semibold text-base text-vulcan">{getPartnerTypeDescription(sbi.partnerType, t)}</p>
                                                            </div>
                                                            <div className={`flex flex-col w-1/3`}>
                                                                <p className="font-semibold text-sm text-suva-gray">{t('sbiList.submittedOn')}</p>
                                                                <p className="font-semibold text-base text-vulcan">{sbi.submittedOn}</p>
                                                            </div>
                                                        </div>
                                                        <div className="flex flex-row justify-between items-center pt-2">
                                                            <div className={`flex flex-col w-1/3`}>
                                                                <p className="font-semibold text-sm text-suva-gray">{t('sbiList.createdDate')}</p>
                                                                <p className="font-semibold text-base text-vulcan">{sbi.crDtimes}</p>
                                                            </div>
                                                            <div className={`flex flex-col w-1/3`}>
                                                                <p className="font-semibold text-sm text-suva-gray">{t('sbiList.expiryDate')}</p>
                                                                <p className="font-semibold text-base text-vulcan">{sbi.expDtimes}</p>
                                                            </div>
                                                            <div className="flex flex-col w-1/3"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        )}
                                    </div>
                                )
                            })
                        }
                    </div>
                </>
            )}
        </div>
    )
}

export default SbiList;