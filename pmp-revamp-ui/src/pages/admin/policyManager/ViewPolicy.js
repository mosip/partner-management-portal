import React, { useState, useEffect, } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import Title from '../../common/Title';
import { bgOfStatus, downloadFile, formatDate, getPolicyDetails, getStatusCode, isLangRTL, onPressEnterKey, getErrorMessage } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import fileUploadBlue from '../../../svg/file_upload_blue_icon.svg';
import previewIcon from "../../../svg/preview_icon.svg";
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg'
import { HttpService } from '../../../services/HttpService';
import LoadingIcon from '../../common/LoadingIcon';
import ViewPolicyPopup from './ViewPolicyPopup';

function ViewPolicy() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [viewDetails, setViewDetails] = useState(true);
    const [viewPolicyPageHeaders, setViewPolicyPageHeaders] = useState(true);
    const [previewJsonPopup, setPreviewJsonPopup] = useState(false);

    useEffect(() => {
        const data = localStorage.getItem('selectedPolicyAttributes');

        if (!data) {
            setUnexpectedError(true);
            return;
        }
        const viewData = JSON.parse(data);
        setViewPolicyPageHeaders(viewData);

        const fetchData = async () => {
            setDataLoaded(false);
            const policyData = await getPolicyDetails(HttpService, viewData.policyId, setErrorCode, setErrorMsg);
            if (policyData !== null) {
                setViewDetails(policyData);
            } else {
                setUnexpectedError(true);
                setErrorMsg(t('clonePolicyPopup.errorInPolicyDetails'));
            }
            setDataLoaded(true);
        };
        fetchData();
    }, []);

    useEffect(() => {
        const handleKeyDown = (event) => {
            if (event.key === 'Escape' || event.key === 'Esc') {
                closePopUp();
            }
        };
        document.addEventListener('keydown', handleKeyDown);
        return () => {
            document.removeEventListener('keydown', handleKeyDown);
        };
    }, []);

    const moveBackToList = () => {
        navigate(viewPolicyPageHeaders.backLink);
    };

    const showUploadedJsonData = () => {
        setPreviewJsonPopup(true);
    };

    const downloadPolicyData = (policyJsonData) => {
        downloadFile(policyJsonData, 'policy-data-json.json', 'application/json');
    };

    const closePopUp = () => {
        setPreviewJsonPopup(false);
    };

    const getPolicyStatus = (policy) => {
        if (policy.is_Active === true && policy.schema !== null) {
            return 'activated';
        } else if (policy.is_Active === false && policy.schema !== null) {
            return 'deactivated';
        } else if (policy.schema === null && policy.is_Active === false) {
            return 'draft';
        }
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
            {!dataLoaded && (
                <LoadingIcon />
            )}
            {dataLoaded && (
                    <div className="flex-col mt-5">
                        <div className="flex justify-between mb-5">
                            <Title title={viewPolicyPageHeaders.header} subTitle={viewPolicyPageHeaders.subTitle} backLink={viewPolicyPageHeaders.backLink} />
                        </div>
                        {unexpectedError && (
                            <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center items-center">
                                        <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                        <p className="text-base font-semibold text-[#6F6E6E] pt-4">{t('commons.unexpectedError')}</p>
                                        <p className="text-sm font-semibold text-[#6F6E6E] pt-1 pb-4">{getErrorMessage(errorCode, t, errorMsg)}</p>
                                        <button onClick={() => navigate(viewPolicyPageHeaders.backLink)}
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
                                            {t('policiesList.policyId')}: <span className="font-semibold">{viewDetails.policyId}</span>
                                        </p>
                                        <div className="flex items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                                            <div className={`${bgOfStatus(getPolicyStatus(viewDetails), t)} flex w-fit py-1 px-5 text-sm rounded-md my-2 font-semibold`}>
                                                {getStatusCode(getPolicyStatus(viewDetails), t)}
                                            </div>
                                            <div className={`font-semibold ${isLoginLanguageRTL ? "mr-[1.4rem]" : "ml-[0.75rem]"} text-sm text-dark-blue`}>
                                                {t("viewDeviceDetails.createdOn") + ' ' +
                                                    formatDate(viewDetails.cr_dtimes, "date")
                                                }
                                            </div>
                                            <div className="mx-2 text-gray-300">|</div>
                                            <div className="font-semibold text-sm text-dark-blue">
                                                {formatDate(viewDetails.cr_dtimes, "time")}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className={`${isLoginLanguageRTL ? "pr-4 ml-8" : "pl-4 mr-8"} pt-3 mb-2`}>
                                    <div className="flex flex-wrap py-2 max-[450px]:flex-col justify-evenly">
                                        <div className="w-[48%] max-[600px]:w-[100%] mb-3">
                                            <p className="font-[600] text-suva-gray text-sm">
                                                {t("policiesList.policyName")}
                                            </p>
                                            <p className="font-[600] text-vulcan text-md break-all">
                                                {viewDetails.policyName}
                                            </p>
                                        </div>
                                        <div className="mb-3 max-[600px]:w-[100%] w-[48%]">
                                            <p className="font-[600] text-suva-gray text-sm">
                                                {t("viewAuthPoliciesList.policyGroup")}
                                            </p>
                                            <p className="font-[600] text-vulcan text-md break-all">
                                                {viewDetails.policyGroupName}
                                            </p>
                                        </div>
                                    </div>
                                    <div className="flex flex-wrap py-2 max-[450px]:flex-col justify-evenly">
                                        <div className="w-[48%] max-[600px]:w-[100%] mb-3">
                                            <p className="font-[600] text-suva-gray text-sm">
                                                {t("viewAuthPoliciesList.policyDescription")}
                                            </p>
                                            <p className="font-[600] text-vulcan text-md break-all">
                                                {viewDetails.policyDesc}
                                            </p>
                                        </div>
                                        <div className="mb-3 max-[600px]:w-[100%] w-[48%]">
                                            <p className="font-[600] text-suva-gray text-sm">
                                                {t("viewAuthPoliciesList.policyGroupDescription")}
                                            </p>
                                            <p className="font-[600] text-vulcan text-md break-all">
                                                {viewDetails.policyGroupDesc}
                                            </p>
                                        </div>
                                    </div>
                                    <div className={`${isLoginLanguageRTL ? "pr-4" : "pl-4"} pt-3 mb-[3.5rem] w-fit`}>
                                        <p className="font-[600] mb-1 text-suva-gray text-sm">
                                            {t("viewAuthPoliciesList.policyData")}
                                        </p>
                                        <div className='flex flex-wrap justify-between px-3 items-center h-[5.5rem] border-2 border-[#fedff] rounded-md bg-[#f4f6fb] gap-x-8'>
                                            <div className='flex items-center'>
                                                <img src={fileUploadBlue} className="h-7" alt="" />
                                                <p className='font-semibold text-sm mx-2'>{t('viewAuthPoliciesList.policyData')}</p>
                                            </div>
                                            <div role='button' onClick={showUploadedJsonData} className='flex justify-between px-2 py-1.5 w-[6rem] bg-white border-2 border-blue-800 rounded-md hover:cursor-pointer' tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, showUploadedJsonData)}>
                                                <p className='text-xs font-semibold text-blue-800'>{t('viewAuthPoliciesList.preview')}</p>
                                                <img src={previewIcon} alt="" />
                                            </div>
                                        </div>
                                        {previewJsonPopup &&
                                            <ViewPolicyPopup
                                                title={t('viewAuthPoliciesList.policyData')}
                                                downloadJsonFile={downloadPolicyData}
                                                closePopUp={closePopUp}
                                                jsonData={viewDetails.policies}
                                            />
                                        }
                                    </div>
                                </div>
                                <hr className={`h-px w-full bg-gray-200 border-0`} />
                                <div className={`flex justify-end py-7 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                                    <button id="auth_Policy_view_back_btn" onClick={() => moveBackToList()} className={`h-10 w-[8rem] text-sm text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center`}>
                                        {t("commons.goBack")}
                                    </button>
                                </div>
                            </div>
                        )}
                    </div>
            )}
        </div>
    )
}
export default ViewPolicy;