import React, { useState, useEffect, } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import Title from '../../common/Title';
import { bgOfStatus, formatDate, getStatusCode, isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';

function ViewPolicyGroupDetails() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [policyGroupDetails, setPolicyGroupDetails] = useState('');

    const moveToPolicyGroupList = () => {
        navigate('/partnermanagement/admin/policy-manager/policy-group-list');
    };

    useEffect(() => {
        const selectedPolicyGroup = localStorage.getItem('selectedPolicyGroupAttributes');
        if (!selectedPolicyGroup) {
            setUnexpectedError(true);
            return;
        }
        let policyGroupData = JSON.parse(selectedPolicyGroup);
        setPolicyGroupDetails(policyGroupData);
    }, []);

    return (
        <>
            <div className={`w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter mt-5 overflow-x-scroll`}>
                <div className="flex justify-between mb-3">
                    <Title title='viewPolicyGroupDetails.viewPolicyGroup' subTitle='viewPolicyGroupDetails.listOfPolicyGroups' backLink='/partnermanagement/admin/policy-manager/policy-group-list' />
                </div>
                {unexpectedError && (
                    <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                        <div className="flex items-center justify-center p-24">
                            <div className="flex flex-col justify-center items-center">
                                <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                <p className="text-sm font-semibold text-[#6F6E6E] py-4">{t('devicesList.unexpectedError')}</p>
                                <button onClick={moveToPolicyGroupList} type="button"
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
                                <p className="text-lg text-dark-blue mb-2 break-all">
                                    {t('policyGroupList.policyGroupId')}: <span className="font-semibold">{policyGroupDetails.id}</span>
                                </p>
                                <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                    <div className={`${bgOfStatus(policyGroupDetails.isActive ? 'ACTIVE' : 'INACTIVE', t)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                        {getStatusCode(policyGroupDetails.isActive ? 'active' : 'inactive', t)}
                                    </div>
                                    <div className={`font-semibold ${isLoginLanguageRTL ? "mr-[1.4rem]" : "ml-[0.75rem]"} text-sm text-dark-blue`}>
                                        {t("viewDeviceDetails.createdOn") + ' ' +
                                            formatDate(policyGroupDetails.crDtimes, "date")
                                        }
                                    </div>
                                    <div className="mx-2 text-gray-300">|</div>
                                    <div className="font-semibold text-sm text-dark-blue">
                                        {formatDate(policyGroupDetails.crDtimes, "time")}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-3 mb-3`}>
                            <div className="flex flex-wrap py-3 max-[450px]:flex-col">
                                <div className="w-[50%] max-[600px]:w-[100%] mb-3">
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("policyGroupList.policyGroupName")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md break-all">
                                        {policyGroupDetails.name}
                                    </p>
                                </div>
                            </div>
                            <div className="flex flex-wrap py-3 max-[450px]:flex-col">
                                <div className="w-full mb-5">
                                    <p className="font-[600] text-suva-gray text-sm">
                                        {t("viewPolicyGroupDetails.policyGroupDesc")}
                                    </p>
                                    <p className="font-[600] text-vulcan text-md break-all">
                                        {policyGroupDetails.desc}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <hr className={`h-[0.1rem] w-[95%] bg-gray-200 border-0 ${isLoginLanguageRTL ? "mr-6" : "ml-6"}`} />
                        <div className={`flex justify-end py-8 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                            <button id="policy_group_view_back_btn" onClick={moveToPolicyGroupList} className={`h-11 w-[9rem] text-sm text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center`}>
                                {t("commons.goBack")}
                            </button>
                        </div>
                    </div>
                )}
            </div>
        </>
    )
}

export default ViewPolicyGroupDetails;