import React, { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next';
import { bgOfStatus, formatDate, getStatusCode, isLangRTL, onPressEnterKey } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import { useNavigate } from 'react-router-dom';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';
import Title from '../../common/Title';

function ViewAdminSbiDetails() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [sbiDetails, setSbiDetails] = useState({});

    useEffect(() => {
        const selectedSbiAttributes = localStorage.getItem('selectedSbiAttributes');
        if (!selectedSbiAttributes) {
            setUnexpectedError(true);
            return;
        }
        const selectedSbi = JSON.parse(selectedSbiAttributes);
        setSbiDetails(selectedSbi);
    }, []);

    const moveToSbiList = () => {
        navigate('/partnermanagement/admin/device-provider-services/sbi-list');
    };

    const showLinkedDevices = () => {
        if (sbiDetails.countOfAssociatedDevices > 0) {
            navigate(`/partnermanagement/admin/device-provider-services/linked-devices-list?sbiId=${sbiDetails.sbiId}&sbiVersion=${sbiDetails.sbiVersion}`);
        }
    };

    return (
        <div className={`w-full p-4 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} overflow-x-scroll`}>
            <div className={`flex-col mt-4 bg-anti-flash-white h-full font-inter break-words max-[450px]:text-sm mb-[2%]`}>
                <div className="flex justify-between mb-3">
                    <Title title={'viewSbiDetails.viewSbiDetails'} subTitle='sbiList.listOfSbis' backLink='/partnermanagement/admin/device-provider-services/sbi-list' />
                </div>

                {unexpectedError && (
                    <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                        <div className="flex items-center justify-center p-24">
                            <div className="flex flex-col justify-center items-center">
                                <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                <p className="text-sm font-semibold text-[#6F6E6E] py-4">{t('devicesList.unexpectedError')}</p>
                                <button onClick={moveToSbiList} type="button"
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
                                    {t('sbiList.sbiId')}: <span className="font-semibold">{sbiDetails.sbiId}</span>
                                </p>
                                <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                    <div className={`${bgOfStatus(sbiDetails.status)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                        {getStatusCode(sbiDetails.status, t)}
                                    </div>
                                    <div className={`font-semibold ${isLoginLanguageRTL ? "mr-[1.4rem]" : "ml-[0.75rem]"} text-sm text-dark-blue`}>
                                        {t("viewOidcClientDetails.createdOn") + ' ' +
                                            formatDate(sbiDetails.createdDateTime, "date")}
                                    </div>
                                    <div className="mx-2 text-gray-300">|</div>
                                    <div className="font-semibold text-sm text-dark-blue">
                                        {formatDate(sbiDetails.createdDateTime, "time")}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-2`}>
                            <div className="flex flex-wrap py-1 max-[450px]:flex-col">
                                <div className={`mb-5 max-[600px]:w-[100%] w-[48%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("viewPolicyRequest.partnerId")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md">
                                        {sbiDetails.partnerId}
                                    </p>
                                </div>
                                <div className={`mb-5 max-[600px]:w-[100%] w-[48%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("viewPolicyRequest.partnerType")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md">
                                        {t("partnerTypes.deviceProvider")}
                                    </p>
                                </div>
                                <div className={`mb-5 max-[600px]:w-[100%] w-[48%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("viewSbiDetails.organisation")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md">
                                        {sbiDetails.orgName}
                                    </p>
                                </div>
                                <div className={`mb-5 max-[600px]:w-[100%] w-[48%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("sbiList.linkedDevices")}
                                    </p>
                                    <button className={`font-[600] text-vulcan text-md ${sbiDetails.countOfAssociatedDevices > 0 && 'text-tory-blue cursor-pointer'}`} onClick={() => showLinkedDevices()}>
                                        {sbiDetails.countOfAssociatedDevices}
                                    </button>
                                </div>
                                <div className={`mb-5 max-[600px]:w-[100%] w-[48%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("viewSbiDetails.sbiCreatedDate")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md">
                                        {formatDate(sbiDetails.sbiCreatedDateTime, "date")}
                                    </p>
                                </div>
                                <div className={`mb-5 max-[600px]:w-[100%] w-[48%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                    <p className={`font-[600] text-suva-gray text-sm`}>
                                        {t("viewSbiDetails.sbiExpiryDate")}
                                    </p>
                                    <p className={`${(sbiDetails.sbiExpiryStatus === 'expired') ? 'text-crimson-red' : 'text-vulcan'} font-[600] text-md`}>
                                        {formatDate(sbiDetails.sbiExpiryDateTime, "date")}
                                    </p>
                                </div>
                                <div className={`mb-5 max-[600px]:w-[100%] w-[48%] ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("sbiList.sbiVersion")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md">
                                        {sbiDetails.sbiVersion}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className={`flex justify-end py-8 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                            <button id="view_admin_sbi_details_back_btn" onClick={moveToSbiList}
                                className="h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center" onKeyDown={(e) => onPressEnterKey(e, moveToSbiList)}>
                                {t("commons.back")}
                            </button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    )
}

export default ViewAdminSbiDetails;