import React, { useEffect, useState } from 'react';
import { useNavigate, useBlocker } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { createRequest, getPolicyManagerUrl, handleServiceErrors, isLangRTL, onPressEnterKey, trimAndReplace } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import BlockerPrompt from "../../common/BlockerPrompt";
import Title from '../../common/Title';
import LoadingIcon from '../../common/LoadingIcon';
import ErrorMessage from '../../common/ErrorMessage';
import { HttpService } from '../../../services/HttpService';
import Confirmation from '../../common/Confirmation';

function CreatePolicyGroup() {

    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [policyGroupName, setPolicyGroupName] = useState('');
    const [policyGroupDesc, setPolicyGroupDesc] = useState('');
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);
    const [validationError, setValidationError] = useState("");
    const [createPolicySuccess, setCreatePolicySuccess] = useState(false);
    const [confirmationData, setConfirmationData] = useState({});

    const blocker = useBlocker(
        ({ currentLocation, nextLocation }) => {
            if (isSubmitClicked || createPolicySuccess) {
                setIsSubmitClicked(false);
                return false;
            }
            return (
                (policyGroupName !== "" || policyGroupDesc !== "") && currentLocation.pathname !== nextLocation.pathname
            );
        }
    );

    useEffect(() => {
        const shouldWarnBeforeUnload = () => {
            return policyGroupName !== "" ||
                policyGroupDesc !== ""
        };

        const handleBeforeUnload = (event) => {
            if (shouldWarnBeforeUnload() && !isSubmitClicked) {
                event.preventDefault();
                event.returnValue = '';
            }
        };

        window.addEventListener('beforeunload', handleBeforeUnload);

        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload);
        };
    }, [policyGroupName, policyGroupDesc, isSubmitClicked]);

    const clearForm = () => {
        setPolicyGroupName("");
        setPolicyGroupDesc("");
        setValidationError("");
    };

    const isFormValid = () => {
        return policyGroupName && policyGroupDesc && !validationError;
    };

    const onChangePolicyGroupName = (value) => {
        setPolicyGroupName(value);
    };

    const onChangePolicyGroupDesc = (value) => {
        setPolicyGroupDesc(value);
    };

    const clickOnCancel = () => {
        navigate('/partnermanagement/policy-manager/policy-group-list');
    };

    const clickOnSubmit = async () => {
        setDataLoaded(false);
        setIsSubmitClicked(true);
        setErrorCode("");
        setErrorMsg("");
        let request = createRequest({
            name: trimAndReplace(policyGroupName),
            desc: trimAndReplace(policyGroupDesc)
        });
        try {
            const response = await HttpService({
                url: getPolicyManagerUrl('/policies/group/new', process.env.NODE_ENV),
                method: 'post',
                baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL,
                data: request
            });
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response;
                    console.log(resData);
                    const requireData = {
                        backUrl: "/partnermanagement/policy-manager/policy-group-list",
                        header: "createPolicyGroup.creatPolicyGroupSuccessHeader",
                    }
                    setConfirmationData(requireData);
                    setCreatePolicySuccess(true);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('createPolicyGroup.errorInCreatePolicyGroup'));
            }
        } catch (err) {
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            console.log("Error fetching data: ", err);
        }
        setDataLoaded(true);
        setIsSubmitClicked(false);
    }

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };


    const handleFormSubmit = (event) => {
        event.preventDefault();
    };

    const style = {
        backArrowIcon: "!mt-[9%]",
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            {!dataLoaded && (
                <LoadingIcon />
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <div className="flex justify-between">
                            <Title title='createPolicyGroup.createPolicyGroup' subTitle='createPolicyGroup.listOfPolicyGroups' backLink={'/partnermanagement/policy-manager/policy-group-list'} style={style} />
                        </div>
                        {!createPolicySuccess ?
                            <div className="w-[100%] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                                <div className="px-[2.5%] py-[2%]">
                                    <p className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                    <form onSubmit={handleFormSubmit}>
                                        <div className="flex flex-col">
                                            <div className="space-y-6">
                                                <div className="my-4">
                                                    <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('createPolicyGroup.policyGroupName')}<span className="text-crimson-red mx-1">*</span></label>
                                                        <input value={policyGroupName} onChange={(e) => onChangePolicyGroupName(e.target.value)} maxLength={128}
                                                            className="h-12 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                                            placeholder={t('createPolicyGroup.enterNameforPolicyGroup')} id="policy_group_name"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="space-y-6">
                                                <div className="my-4">
                                                    <div className="flex flex-col w-full max-[450px]:w-full">
                                                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('createPolicyGroup.policyGroupDescription')}<span className="text-crimson-red mx-1">*</span></label>
                                                        <textarea value={policyGroupDesc} onChange={(e) => onChangePolicyGroupDesc(e.target.value)} maxLength={256}
                                                            className="h-14 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                                            placeholder={t('createPolicyGroup.enterPolicyGroupDescription')} id="policy_group_description"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div className="border bg-medium-gray" />
                                <div className="flex flex-row max-[450px]:flex-col px-[3%] py-9 justify-between max-[450px]:space-y-2">
                                    <button id="createPolicy_clear_form" onClick={() => clearForm()} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => clearForm())}>
                                        {t('requestPolicy.clearForm')}
                                    </button>
                                    <div className={`flex flex-row max-[450px]:flex-col space-x-3 max-[450px]:space-x-0 max-[450px]:space-y-2 w-full md:w-auto justify-end`}>
                                        <button id="createPolicy_cancel_btn" onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                                        <button id="createPolicy_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>
                                            {t('requestPolicy.submit')}
                                        </button>
                                    </div>
                                </div>
                            </div>
                            : <Confirmation confirmationData={confirmationData} /> 
                        }
                    </div>
                </>
            )}
            <BlockerPrompt blocker={blocker} />
        </div>
    )
}

export default CreatePolicyGroup;