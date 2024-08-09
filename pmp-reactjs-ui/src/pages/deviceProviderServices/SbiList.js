import { useState, useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService.js';
import Title from '../common/Title.js';
import { HttpService } from '../../services/HttpService';
import {
    isLangRTL, onPressEnterKey, bgOfStatus, getStatusCode, getPartnerTypeDescription, handleServiceErrors, formatDate, getPartnerManagerUrl,
    handleMouseClickForDropdown,
} from '../../utils/AppUtils.js';
import LoadingIcon from "../common/LoadingIcon.js";
import ErrorMessage from '../common/ErrorMessage.js';
import rectangleGrid from '../../svg/rectangle_grid.svg';
import upArrow from '../../svg/up_arrow.svg';
import verifiedIcon from '../../svg/verified_icon.svg';
import expiredIcon from '../../svg/expiry_icon.svg';
import deactivatedIcon from '../../svg/deactivated_shield_icon.svg';

function SbiList() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(false);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [open, setOpen] = useState(-1);
    const [deactivateBtnId, setDeactivateBtnId] = useState(-1);
    const [sbiList, setSbiList] = useState([]);
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setDeactivateBtnId(-1));
    }, [submenuRef]);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    useEffect(() => {
        localStorage.removeItem('selectedSbiData');
        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(getPartnerManagerUrl('/partners/getAllSBIDetails', process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        const sortedData = resData.sort((a, b) => new Date(b.crDtimes) - new Date(a.crDtimes));
                        setSbiList(sortedData);
                    } else {
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('sbiList.errorInSbiList'));
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                setErrorMsg(err);
            }
        };
        fetchData();
    }, []);

    const addSbi = () => {
        navigate('/partnermanagement/deviceProviderServices/addSbi');
    };

    const addDevices = (sbi) => {
        if (sbi.status !== "deactivated") {
            localStorage.setItem('selectedSbiData', JSON.stringify(sbi));
            navigate('/partnermanagement/deviceProviderServices/addDevices');
        }
    };

    const devicesList = (sbi) => {
        localStorage.setItem('selectedSbiData', JSON.stringify(sbi));
        navigate('/partnermanagement/deviceProviderServices/devicesList');
    };

    const onClickAction = (sbi, index) => {
        if (sbi.status !== "deactivated") {
            setDeactivateBtnId(deactivateBtnId === index ? null : index);
        }
    };

    const styleForTitle = {
        backArrowIcon: "!mt-[5%]"
    }

    const getIcon = (status, expired) => {
        if (status === "deactivated") {
            return deactivatedIcon;
        }
        else if (expired) {
            return expiredIcon;
        }
        else  {
            return verifiedIcon;
        }
    }

    const getbgOfStatus = (status, expired) => {
        if (status === "deactivated") {
            return 'bg-[#e8e6e6e3] border';
        }
        else if (status === "deactivated" ||expired) {
            return 'bg-[#fef1f1]';
        }
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
                            {sbiList.length > 0 ?
                                <button type="button" onClick={addSbi}
                                    className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                                    {t('sbiList.addSbi')}
                                </button>
                                : null
                            }
                        </div>
                        {sbiList.length === 0 ?
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
                            sbiList.map((sbi, index) => {
                                return (
                                    <div key={index} className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center max-[510px]:overflow-x-scroll`}>
                                        <div className={`p-4 ${getbgOfStatus(sbi.status, sbi.expired)}`}>
                                            <div className="flex flex-row max-[670px]:flex-col justify-between items-center max-[670px]:items-start">
                                                <div className="flex flex-row justify-between items-center max-[670px]:mb-2">
                                                    <img src={getIcon(sbi.status, sbi.expired)} alt="" className={`${isLoginLanguageRTL ? "ml-4" : "mr-4"}`} />
                                                    <div className="flex flex-col">
                                                        <p className={`text-base font-bold ${sbi.status === "deactivated" ? 'text-[#8E8E8E]' : 'text-dark-blue'}`}>{sbi.sbiVersion}</p>
                                                        <div className="flex flex-row items-center gap-1">
                                                            <div className={`${(sbi.status === 'deactivated' || sbi.expired) ? 'bg-[#A5A5A5] text-white' : bgOfStatus(sbi.status)} flex w-fit max-[900px]:w-min py-1.5 px-2 ${isLoginLanguageRTL ? "ml-1" : "mr-1"} text-xs font-semibold rounded-md`}>
                                                                {getStatusCode(sbi.status, t)}
                                                            </div>
                                                            <div className='flex'>
                                                                <p className="text-xs font-semibold text-[#505E7C] max-[830px]:w-min max-[670px]:w-fit">
                                                                    <span onClick={() => devicesList(sbi)} className={`text-xs font-semibold ${sbi.status === "deactivated" ? 'text-[#4F5E7C]' : 'text-tory-blue cursor-pointer'} `}>
                                                                        {sbi.countOfApprovedDevices} {t('sbiList.devices')}
                                                                    </span> {t('sbiList.approved')} <span className='mx-1'>{'|'}</span>
                                                                </p>
                                                                <p className="text-xs font-semibold text-[#505E7C] max-[830px]:w-min max-[670px]:w-fit">
                                                                    <span onClick={() => devicesList(sbi)} className={`text-xs font-semibold ${sbi.status === "deactivated" ? 'text-[#4F5E7C]' : 'text-[#ba5f04] cursor-pointer'} `}>
                                                                        {sbi.countOfPendingDevices} {t('sbiList.devices')}
                                                                    </span> {t('sbiList.pendingForApprovalContx')}
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="flex flex-row justify-between items-center space-x-6 relative">
                                                    <div className="flex flex-row justify-between items-center space-x-3">
                                                        <button onClick={() => addDevices(sbi)} className={`${sbi.status === "approved" && !sbi.expired ? 'bg-tory-blue border-[#1447B2]' : 'border-[#A5A5A5] bg-[#A5A5A5] cursor-auto'} ${sbi.status !== "approved" && "disabled"} h-10 w-28 text-white text-xs font-semibold rounded-md ${isLoginLanguageRTL && "ml-3"}`}>{t('sbiList.addDevices')}</button>
                                                        <button onClick={() => devicesList(sbi)} className="h-10 w-28 text-xs px-3 py-1 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center">{t('sbiList.viewDevices')}</button>
                                                        <button ref={el => submenuRef.current[index] = el} onClick={() => onClickAction(sbi, index)} className={`h-10 w-8 text-lg pb-3 ${sbi.status === "deactivated" ? 'border-[#A5A5A5] text-dim-gray cursor-auto' : 'text-tory-blue border-[#1447B2]'} bg-white border font-bold rounded-md text-center`}>...</button>
                                                        {deactivateBtnId === index && (
                                                            <div className={`w-[17rem] min-w-fit absolute top-full mt-2 ${isLoginLanguageRTL ? "left-[3.25rem]" : "right-[3.25rem]"} rounded-md bg-white shadow-lg ring-gray-50 border duration-700`}>
                                                                <div className="flex items-center justify-between cursor-pointer">
                                                                    <button className="block px-4 py-2 text-sm font-medium text-crimson-red">{t('sbiList.deactivate')}</button>
                                                                </div>
                                                            </div>
                                                        )}
                                                    </div>
                                                    <img src={upArrow} alt="" className={`cursor-pointer ${open === index ? "rotate-180" : "rotate-0"}`} onClick={() => setOpen(index === open ? null : index)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setOpen(index === open ? null : index))} />
                                                </div>
                                            </div>
                                        </div>
                                        {open === index && (
                                            <div>
                                                <hr className="border bg-medium-gray" />
                                                <div className="p-4 bg-[#FCFCFC]">
                                                    <div className="flex flex-col">
                                                        <div className="flex flex-row justify-between items-center max-[530px]:flex-col max-[530px]:items-start max-[530px]:pt-2">
                                                            <div className="flex flex-col w-1/3 max-[530px]:w-full">
                                                                <p className="text-xs text-suva-gray">{t('sbiList.partnerId')}</p>
                                                                <p className="font-normal text-sm text-vulcan">{sbi.partnerId}</p>
                                                            </div>
                                                            <div className={`flex flex-col w-1/3 max-[530px]:w-full`}>
                                                                <p className="text-xs text-suva-gray">{t('sbiList.partnerType')}</p>
                                                                <p className="font-normal text-sm text-vulcan">{getPartnerTypeDescription(sbi.partnerType, t)}</p>
                                                            </div>
                                                            <div className={`flex flex-col w-1/3 max-[530px]:w-full`}>
                                                                <p className="text-xs text-suva-gray">{t('sbiList.submittedOn')}</p>
                                                                <p className="font-normal text-sm text-vulcan">{formatDate(sbi.crDtimes, 'date')}</p>
                                                            </div>
                                                        </div>
                                                        <div className="flex flex-row justify-between items-center max-[530px]:flex-col max-[530px]:items-start mt-6">
                                                            <div className={`flex flex-col w-1/3 max-[530px]:w-full`}>
                                                                <p className="text-xs text-suva-gray">{t('sbiList.createdDate')}</p>
                                                                <p className="font-normal text-sm text-vulcan">{sbi.sbiSoftwareCreatedDtimes}</p>
                                                            </div>
                                                            <div className={`flex flex-col w-1/3 max-[530px]:w-full`}>
                                                                <p className={`text-xs ${(sbi.status !== "deactivated" && sbi.expired) ? 'text-red-700 font-semibold' : 'text-suva-gray'} `}>{t('sbiList.expiryDate')}</p>
                                                                <p className={`font-normal text-sm ${(sbi.status !== "deactivated" && sbi.expired) ? 'text-red-700 font-semibold' : 'text-vulcan'} `}>{sbi.sbiSoftwareExpiryDtimes}</p>
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
