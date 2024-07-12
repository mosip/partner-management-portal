import { useState, useEffect, useRef } from "react";
import { useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../services/UserProfileService";
import { isLangRTL } from "../../utils/AppUtils";
import { getPartnerManagerUrl, getPolicyManagerUrl, handleServiceErrors, moveToPolicies, getPartnerTypeDescription, moveToHome, createDropdownData, createRequest } from '../../utils/AppUtils';
import { HttpService } from '../../services/HttpService';
import LoadingIcon from "../common/LoadingIcon";
import ErrorMessage from "../common/ErrorMessage";
import backArrow from '../../svg/back_arrow.svg';
import DropdownComponent from "../common/fields/DropdownComponent";
import DropdownWithSearchComponent from "../common/fields/DropdownWithSearchComponent";
import BlockerPrompt from "../common/BlockerPrompt";

function RequestPolicy() {
    const navigate = useNavigate();
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [partnerId, setPartnerId] = useState("");
    const [policyName, setPolicyName] = useState("");
    const [partnerType, setPartnerType] = useState("");
    const [policyGroupName, setPolicyGroupName] = useState("");
    const [partnerComments, setPartnerComments] = useState("");
    const [partnerIdDropdownData, setPartnerIdDropdownData] = useState([]);
    const [policiesDropdownData, setPoliciesDropdownData] = useState([]);
    const [partnerData, setPartnerData] = useState([]);
    const [policyList, setPolicyList] = useState([]);
    const [validationError, setValidationError] = useState("");
    const textareaRef = useRef(null);
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const blocker = useBlocker(
        ({ currentLocation, nextLocation }) => {
            if (isSubmitClicked) {
                setIsSubmitClicked(false);
                return false;
            }

            return (
                (partnerId !== "" || policyName !== "" ||
                    partnerComments !== "") &&
                currentLocation.pathname !== nextLocation.pathname
            );
        }
    );

    useEffect(() => {
        const shouldWarnBeforeUnload = () => {
            return partnerId !== "" ||
                partnerComments !== "" ||
                policyName !== "";
        };

        const handleBeforeUnload = (event) => {
            if (shouldWarnBeforeUnload()) {
                event.preventDefault();
                event.returnValue = '';
            }
        };

        window.addEventListener('beforeunload', handleBeforeUnload);

        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload);
        };
    }, [partnerId, partnerComments, policyName]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(getPartnerManagerUrl('/partners/getAllApprovedPartnerIdsWithPolicyGroups', process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        setPartnerData(resData);
                        setPartnerIdDropdownData(createDropdownData('partnerId', '', false, resData, t));
                        console.log('Response data:', partnerData.length);
                    } else {
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('policies.errorInResponse'));
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                setErrorMsg(err);
            }
        };
        fetchData();
    }, []);

    const onChangePartnerId = async (fieldName, selectedValue) => {
        setPartnerId(selectedValue);
        setPolicyName("");
        // Find the selected partner data
        const selectedPartner = partnerData.find(item => item.partnerId === selectedValue);
        if (selectedPartner) {
            setPartnerType(getPartnerTypeDescription(selectedPartner.partnerType, t));
            setPolicyGroupName(selectedPartner.policyGroupName);
            await getListofPolicies(selectedPartner.policyGroupName);
        }
    };

    const onChangePolicyName = (fieldName, selectedValue) => {
        const selectedPolicy = policyList.find(item => item.name === selectedValue);
        if (selectedPolicy) {
            setPolicyName(selectedValue);
        }
    };

    const getListofPolicies = async (policyGroupName) => {
        try {
            setDataLoaded(false);
            const response = await HttpService({
                url: getPolicyManagerUrl(`/policies/active/group/${policyGroupName}`, process.env.NODE_ENV),
                method: 'get',
                baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL
            });
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response;
                    setPolicyList(resData);
                    setPoliciesDropdownData(createDropdownData('name', 'descr', false, resData, t));
                    console.log(`Response data: ${resData.length}`);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('requestPolicy.errorInFetchingPolicyNames'));
            }
            setDataLoaded(true);
        } catch (err) {
            console.error('Error fetching policies:', err);
            setErrorMsg(err.message);
        }
    };

    const clearForm = () => {
        setPartnerId("");
        setPartnerType("");
        setPolicyGroupName("");
        setPolicyName("");
        setPartnerComments("");
        setPoliciesDropdownData([]);
        setValidationError("");
    };

    const clickOnSubmit = async () => {
        setIsSubmitClicked(true);
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        let request = createRequest({
            policyName: policyName,
            useCaseDescription: partnerComments
        });
        try {
            const response = await HttpService.post(getPartnerManagerUrl(`/partners/${partnerId}/policy/map`, process.env.NODE_ENV), request);
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response;
                    const confirmationData = {
                        title: "requestPolicy.requestPolicy",
                        backUrl: "/partnermanagement/policies",
                        header: "requestPolicy.policySuccessHeader",
                        description: "requestPolicy.policySuccessMsg",
                        subNavigation: "requestPolicy.policies",
                        styleSet: {
                            imgIconLtr: "ml-[33%] max-[450px]:ml-20",
                            imgIconRtl: "mr-[25%] max-[450px]:mr-20"
                        }
                    }
                    localStorage.setItem('confirmationData', JSON.stringify(confirmationData));
                    navigate('/partnermanagement/policies/requestPolicyConfirmation');
                    console.log(`Response data: ${resData.length}`);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('requestPolicy.errorInMapPolicy'));
            }
            setDataLoaded(true);
        } catch (err) {
            setErrorMsg(err);
            console.log("Error fetching data: ", err);
        }
    }

    const isFormValid = () => {
        return partnerId && policyName && partnerComments && !validationError;
    };

    const validateComments = (comments) => {
        let error = "";
        const maxLength = 500;
        const regexPattern = /^(?!\s+$)[a-zA-Z0-9-_ ,.&()]*$/;
        
        if (comments.length > maxLength) {
            error = t('requestPolicy.commentTooLong');
        } else if (!regexPattern.test(comments)) {
            error = t('commons.specialCharNotAllowed');
        }
        setValidationError(error);
        return error === "";
    };

    const handleCommentChange = (e) => {
        const { value } = e.target;

        if (validateComments(value)) {
            setValidationError("");
        }
        setPartnerComments(value);
    };

    const adjustTextareaHeight = () => {
        if (textareaRef.current) {
            textareaRef.current.style.height = 'auto';
            textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
        }
    };

    useEffect(() => {
        adjustTextareaHeight();
    }, [partnerComments]);

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !mb-1",
        dropdownButton: "!w-full !h-10 !rounded-md !text-base !text-left",
        selectionBox: "!top-10"
    }

    const styleForSearch = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !mb-1",
        dropdownButton: "!w-full !h-10 !rounded-md !text-base !text-left",
        selectionBox: "!top-10"
    }

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll relative font-inter`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <div className={`flex justify-end items-center max-w-7xl absolute ${isLoginLanguageRTL ? "left-0" : "right-0"}`}>
                            <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3">
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                            </div>
                        </div>
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex items-start gap-x-2">
                            <img src={backArrow} alt="" onClick={() => moveToPolicies(navigate)} className={`mt-[1%] cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} />
                            <div className="flex-col">
                                <h1 className="font-semibold text-lg text-dark-blue">{t('requestPolicy.requestPolicy')}</h1>
                                <div className="flex space-x-1">
                                    <p onClick={() => moveToHome(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                        {t('commons.home')} /
                                    </p>
                                    <p onClick={() => moveToPolicies(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                        {t('requestPolicy.policies')}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div className="w-[100%] bg-snow-white mt-[1%] rounded-lg shadow-md">
                            <div className="p-7">
                                <p className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                <form>
                                    <div className="flex flex-col">
                                        <div className="flex flex-row justify-between space-x-4 my-[1%]">
                                            <div className="flex flex-col w-[48%]">
                                                <DropdownComponent
                                                    fieldName='partnerId'
                                                    dropdownDataList={partnerIdDropdownData}
                                                    onDropDownChangeEvent={onChangePartnerId}
                                                    fieldNameKey='requestPolicy.partnerId*'
                                                    placeHolderKey='requestPolicy.partnerId'
                                                    selectedDropdownValue={partnerId}
                                                    styleSet={styles}
                                                    addInfoIcon
                                                    infoKey='requestPolicy.info'>
                                                </DropdownComponent>
                                            </div>
                                            <div className="flex flex-col w-[48%]">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red">*</span></label>
                                                <button disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-nowrap no-scrollbar" type="button">
                                                    <span>{partnerType || t('requestPolicy.partnerType')}</span>
                                                    <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                    </svg>
                                                </button>
                                            </div>
                                        </div>
                                        <div className="flex flex-row justify-between space-x-4">
                                            <div className="flex flex-col w-[48%]">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.policyGroup')}<span className="text-crimson-red">*</span></label>
                                                <button disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-sm text-dark-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-nowrap no-scrollbar" type="button">
                                                    <span>{policyGroupName || t('requestPolicy.policyGroup')}</span>
                                                    <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                    </svg>
                                                </button>
                                            </div>
                                            <div className="flex flex-col w-[48%]">
                                                <DropdownWithSearchComponent
                                                    fieldName='policyName'
                                                    dropdownDataList={policiesDropdownData}
                                                    onDropDownChangeEvent={onChangePolicyName}
                                                    fieldNameKey='requestPolicy.policyName*'
                                                    placeHolderKey='requestPolicy.selectPolicyName'
                                                    selectedDropdownValue={policyName}
                                                    searchKey='commons.search'
                                                    styleSet={styleForSearch}>
                                                </DropdownWithSearchComponent>
                                            </div>
                                        </div>
                                        <div className="flex my-[1%]">
                                            <div className="flex flex-col w-full">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                    {t('requestPolicy.comments')}<span className="text-crimson-red">*</span>
                                                </label>
                                                <textarea ref={textareaRef} value={partnerComments} onChange={(e) => handleCommentChange(e)} className="w-full px-2 py-2 border border-[#707070] rounded-md text-base text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-pre-wrap no-scrollbar" placeholder={t('requestPolicy.commentBoxDesc')}>
                                                </textarea>
                                                {validationError && <span className="text-sm text-crimson-red font-semibold">{validationError}</span>}
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="border bg-medium-gray" />
                            <div className="flex flex-row px-[3%] py-5 justify-between">
                                <button onClick={() => clearForm()} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.clearForm')}</button>
                                <div className={`flex flex-row space-x-3 w-full md:w-auto justify-end`}>
                                    <button onClick={() => moveToPolicies(navigate)} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                                    <button disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t('requestPolicy.submit')}</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </>
            )}
            <BlockerPrompt blocker={blocker} />
        </div>

    )
}

export default RequestPolicy;