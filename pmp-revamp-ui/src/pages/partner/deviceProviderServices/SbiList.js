import { useState, useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService.js';
import Title from '../../common/Title.js';
import { HttpService } from '../../../services/HttpService';
import {
    isLangRTL, bgOfStatus, getStatusCode, getPartnerTypeDescription, handleServiceErrors, formatDate, getPartnerManagerUrl,
    handleMouseClickForDropdown, populateDeactivatedStatus,
    createRequest, setSubmenuRef
} from '../../../utils/AppUtils.js';
import LoadingIcon from "../../common/LoadingIcon.js";
import ErrorMessage from '../../common/ErrorMessage.js';
import rectangleGrid from '../../../svg/rectangle_grid.svg';
import upArrow from '../../../svg/up_arrow.svg';
import verifiedIcon from '../../../svg/verified_icon.svg';
import expiredIcon from '../../../svg/expiry_icon.svg';
import deactivatedIcon from '../../../svg/deactivated_shield_icon.svg';
import DeactivatePopup from '../../common/DeactivatePopup.js';
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import disableDeactivateIcon from "../../../svg/disable_deactivate_icon.svg";

function SbiList() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(false);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [open, setOpen] = useState(-1);
    const [deactivateBtnId, setDeactivateBtnId] = useState(-1);
    const [showActiveIndexDeactivatePopup, setShowActiveIndexDeactivatePopup] = useState(null);
    const [deactivateRequest, setDeactivateRequest] = useState({});
    const [sbiList, setSbiList] = useState([]);
    const [selectedSbi, setSelectedSbi] = useState({});
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setDeactivateBtnId(-1));
    }, [submenuRef]);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    useEffect(() => {
        // localStorage.removeItem('selectedSbiData');
        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(getPartnerManagerUrl('/securebiometricinterface', process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response.data;
                        const populatedData = populateDeactivatedStatus(resData, "status", "isActive");
                        const sortedData = populatedData.sort((a, b) => new Date(b.createdDateTime) - new Date(a.createdDateTime));
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
                if (err.response?.status && err.response.status !== 401) {
                    setErrorMsg(err.toString());
                }
            }
        };
        fetchData();
    }, []);

    const addSbi = () => {
        navigate('/partnermanagement/device-provider-services/add-sbi');
    };

    const addDevices = (sbi) => {
        console.log(sbi)
        setSelectedSbiData(sbi);
        const previousPath = {
            name: 'sbiList.listOfSbi',
            path: '/partnermanagement/device-provider-services/sbi-list',
            backToSbiList: true
        };
        localStorage.setItem('previousPath', JSON.stringify(previousPath));
        navigate('/partnermanagement/device-provider-services/add-devices');
    };

    const devicesList = (sbi) => {
        setSelectedSbiData(sbi);
        navigate('/partnermanagement/device-provider-services/devices-list');
    }

    const setSelectedSbiData = (sbi) => {
        const sbiData = {
            sbiId: sbi.sbiId,
            sbiVersion: sbi.sbiVersion,
            status: sbi.status,
            canAddDevices: canAddDevices(sbi),
            partnerId: sbi.partnerId
        };

        localStorage.setItem('selectedSbiData', JSON.stringify(sbiData));
    };

    const canAddDevices = (sbi) => {
        if (sbi.status !== "approved" || sbi.sbiExpiryStatus === 'expired') {
            return false;
        }
        return true;
    };

    const onClickAction = (sbi, index) => {
        setDeactivateBtnId(deactivateBtnId === index ? null : index);
    };

    const onClickDeactivate = (sbi, index) => {
        if (sbi.status === "approved") {
            const request = createRequest({
                status: "De-Activate",
            }, "mosip.pms.deactivate.sbi.patch", true);
            setSelectedSbi(sbi);
            setDeactivateBtnId(-1);
            setDeactivateRequest(request);
            setShowActiveIndexDeactivatePopup(index);
        }
    };



    const getIcon = (sbi) => {
        if (sbi.status === "deactivated") {
            return deactivatedIcon;
        }
        else if (sbi.sbiExpiryStatus === 'expired') {
            return expiredIcon;
        }
        else {
            return verifiedIcon;
        }
    }

    const getbgOfStatus = (sbi) => {
        if (sbi.status === "deactivated") {
            return 'bg-[#EAECF0] border rounded-lg';
        }
        else if (sbi.sbiExpiryStatus === 'expired') {
            return 'bg-[#fef1f1]';
        } else {
            return 'bg-[#FCFCFC]';
        }
    }

    const onClickConfirmDeactivate = (deactivationResponse, selectedSbiData) => {
        if (deactivationResponse && !deactivationResponse.isActive) {
            setSelectedSbi({});
            window.location.reload();
        }
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} max-[500px]:overflow-x-scroll font-inter`}>
            {!dataLoaded && (
                <LoadingIcon />
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5 !mb-4">
                        <div className="flex justify-between mb-5">
                            <Title title='sbiList.listOfSbi' backLink='/partnermanagement' />
                            {sbiList.length > 0 ?
                                <button id='sbi_list_add_sbi_btn' type="button" onClick={addSbi}
                                    className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                                    {t('sbiList.addSbi')}
                                </button>
                                : null
                            }
                        </div>
                        <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                            <div className="flex items-center justify-center p-2">
                                <div className="p-2 bg-[#FFF7E5] border-2 border-[#EDDCAF] rounded-md w-full">
                                    <p className="text-sm font-medium text-[#8B6105]">{t('sbiList.guidence')}</p>
                                </div>
                            </div>
                        </div>
                        {sbiList.length === 0 ?
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <div className="flex items-center justify-center p-44">
                                    <div className="flex flex-col items-center">
                                        <img src={rectangleGrid} alt="" />
                                        <button id='add_sbi_btn' onClick={() => addSbi()} type="button" className="text-white font-semibold mt-8 bg-tory-blue rounded-md text-sm h-11 px-5 py-3">
                                            {t('sbiList.addSbi')}
                                        </button>
                                    </div>
                                </div>
                            </div> :
                            sbiList.map((sbi, index) => {
                                return (
                                    <div key={index} className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                                        <div className={`p-4 ${getbgOfStatus(sbi)}`}>
                                            <div className="flex flex-row max-[720px]:flex-col justify-between items-center max-[720px]:items-start">
                                                <div className="flex flex-row justify-between items-center max-[670px]:mb-2">
                                                    <img src={getIcon(sbi)} alt="" className={`${isLoginLanguageRTL ? "ml-4" : "mr-4"}`} />
                                                    <div className="flex flex-col">
                                                        <p className={`text-base break-all font-bold p-1 ${sbi.status === "deactivated" ? 'text-[#8E8E8E]' : 'text-dark-blue'}`}>{sbi.sbiVersion}</p>
                                                        <div className="flex flex-row items-center space-x-1">
                                                            <div className={`${(sbi.status === 'deactivated' || sbi.sbiExpiryStatus === 'expired') ? 'bg-[#A5A5A5] text-white' : bgOfStatus(sbi.status)} flex w-fit py-1.5 px-2 ${isLoginLanguageRTL ? "ml-1" : "mr-1"} text-xs font-semibold rounded-md`}>
                                                                {getStatusCode(sbi.status, t)}
                                                            </div>
                                                            <div className='flex items-center w-fit px-2 mx-1'>
                                                                <p className="text-xs font-semibold text-[#505E7C]">
                                                                    <span className={`text-xs font-semibold ${sbi.status === "deactivated" ? 'text-[#4F5E7C]' : 'text-tory-blue'} `}>
                                                                        {sbi.countOfApprovedDevices} {sbi.countOfApprovedDevices <= 1 ? t('sbiList.device') : t('sbiList.devices')}
                                                                    </span> {t('sbiList.approved')}
                                                                </p>
                                                                <span className='mx-1'>{'|'}</span>
                                                                <p className="text-xs font-semibold text-[#505E7C]">
                                                                    <span className={`text-xs font-semibold ${sbi.status === "deactivated" ? 'text-[#4F5E7C]' : 'text-[#ba5f04]'} `}>
                                                                        {sbi.countOfPendingDevices} {sbi.countOfPendingDevices <= 1 ? t('sbiList.device') : t('sbiList.devices')}
                                                                    </span> {t('sbiList.pendingForApprovalContx')}
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div ref={setSubmenuRef(submenuRef, index)} className="flex flex-row justify-between items-center relative space-x-3">
                                                    <button id={'sbi_list_add_Devices' + (index + 1)} disabled={!canAddDevices(sbi)} onClick={() => addDevices(sbi)} className={`${sbi.status === "approved" && sbi.sbiExpiryStatus === 'valid' ? 'bg-tory-blue border-[#1447B2]' : 'border-[#A5A5A5] bg-[#A5A5A5] cursor-auto'} ${sbi.status !== "approved" && "disabled"} h-10 w-28 text-white text-xs font-semibold rounded-md ${isLoginLanguageRTL && "ml-3"}`}>{t('sbiList.addDevices')}</button>
                                                    <button id={'sbi_list_view_Devices' + (index + 1)} onClick={() => devicesList(sbi)} className="h-10 w-28 text-xs px-3 py-1 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center">{t('sbiList.viewDevices')}</button>
                                                    <button id={'sbi_list_hamburger' + (index + 1)} onClick={() => onClickAction(sbi, index)} className={`h-10 w-8 text-lg pb-3 text-tory-blue border-[#1447B2] bg-white  border font-bold rounded-md text-center`}>...</button>
                                                    <button id={'sbi_list_arrow' + (index + 1)} className={`cursor-pointer px-3 min-w-fit ${open === index ? "rotate-0" : "rotate-180"} duration-300`} onClick={() => setOpen(index === open ? null : index)}>
                                                        <img src={upArrow} alt="" />
                                                    </button>

                                                    {deactivateBtnId === index && (
                                                        <div className={`z-50 w-[15rem] min-w-fit absolute top-full mt-2  ${sbi.status === "approved" ? 'text-[#3E3E3E]' : 'text-[#A5A5A5]'} bg-white ${isLoginLanguageRTL ? "left-[3.25rem]" : "right-[3.25rem]"} rounded-md font-semibold shadow-lg hover:bg-gray-100 ring-gray-50 border duration-200`}>
                                                            <div role='button' id='sbi_list_deactivate' onClick={() => onClickDeactivate(sbi, index)} className={`${isLoginLanguageRTL ? "text-right" : "text-left"} px-4 py-2 flex justify-between text-sm font-medium ${sbi.status !== "approved" ? ' cursor-auto' : 'cursor-pointer'}`}>
                                                                <p>{t('sbiList.deactivate')}</p>
                                                                <img src={sbi.status === "approved" ? deactivateIcon : disableDeactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                            </div>
                                                        </div>
                                                    )}
                                                </div>
                                                {showActiveIndexDeactivatePopup === index && (
                                                    <DeactivatePopup
                                                        closePopUp={() => setShowActiveIndexDeactivatePopup(null)}
                                                        onClickConfirm={(deactivationResponse) => onClickConfirmDeactivate(deactivationResponse, selectedSbi)}
                                                        popupData={{ ...selectedSbi, isDeactivateSbi: true }}
                                                        request={deactivateRequest}
                                                        headerMsg='deactivateSbi.headerMsg'
                                                        descriptionMsg='deactivateSbi.description'
                                                        headerKeyName={selectedSbi.sbiVersion}
                                                    />
                                                )}
                                            </div>
                                        </div>
                                        {open === index && (
                                            <div>
                                                <hr className="border bg-medium-gray" />
                                                <div className="p-4 bg-[#FCFCFC]">
                                                    <div className="flex flex-col space-y-1">
                                                        <div className="flex flex-row justify-evenly items-center font-semibold max-[530px]:flex-col max-[530px]:items-start max-[530px]:space-y-2">
                                                            <div className="flex flex-col w-1/3 max-[530px]:w-full">
                                                                <p className="text-xs text-suva-gray">{t('sbiList.sbiId')}</p>
                                                                <p className="text-sm text-vulcan">{sbi.sbiId}</p>
                                                            </div>
                                                            <div className="flex flex-col w-1/3 max-[530px]:w-full">
                                                                <p className="text-xs text-suva-gray">{t('sbiList.partnerId')}</p>
                                                                <p className="text-sm text-vulcan">{sbi.partnerId}</p>
                                                            </div>
                                                            <div className={`flex flex-col w-1/3 max-[530px]:w-full`}>
                                                                <p className="text-xs text-suva-gray">{t('sbiList.partnerType')}</p>
                                                                <p className="text-sm text-vulcan">{getPartnerTypeDescription("Device_Provider", t)}</p>
                                                            </div>
                                                        </div>
                                                        <div className="flex flex-row justify-evenly font-semibold pt-3 items-center max-[530px]:flex-col max-[530px]:items-start max-[530px]:space-y-2">
                                                            <div className={`flex flex-col w-1/3 max-[530px]:w-full`}>
                                                                <p className="text-xs text-suva-gray">{t('sbiList.submittedOn')}</p>
                                                                <p className="text-sm text-vulcan">{formatDate(sbi.createdDateTime, 'date')}</p>
                                                            </div>
                                                            <div className={`flex flex-col w-1/3 max-[530px]:w-full`}>
                                                                <p className="text-xs text-suva-gray">{t('sbiList.sbiCreatedDate')}</p>
                                                                <p className="text-sm text-vulcan">{formatDate(sbi.sbiCreatedDateTime, 'date')}</p>
                                                            </div>
                                                            <div className={`flex flex-col w-1/3 max-[530px]:w-full`}>
                                                                <p className={'text-xs text-suva-gray'}>{t('sbiList.sbiExpiryDate')}</p>
                                                                <p className={`text-sm ${(sbi.status !== "deactivated" && sbi.sbiExpiryStatus === 'expired') ? 'font-bold text-crimson-red' : 'text-vulcan'} `}>{formatDate(sbi.sbiExpiryDateTime, 'date')}</p>
                                                            </div>
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
