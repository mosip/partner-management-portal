import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import DropdownComponent from '../common/fields/DropdownComponent';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL } from '../../utils/AppUtils';
import backArrow from '../../svg/back_arrow.svg';
import LoadingIcon from "../common/LoadingIcon";
import ErrorMessage from "../common/ErrorMessage";
import { moveToAuthenticationServices } from "../../utils/AppUtils";
import DropdownWithSearchComponent from "../common/fields/DropdownWithSearchComponent";

function GenerateApiKey() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [partnerIdDropdownData, setPartnerIdDropdownData] = useState([]);
    const [policiesDropdownData, setPoliciesDropdownData] = useState([]);
    const [partnerId, setPartnerId] = useState("");
    const [policyId, setPolicyId] = useState("");
    const [policyName, setPolicyName] = useState("");
    const [partnerType, setPartnerType] = useState("");
    const [policyGroupName, setPolicyGroupName] = useState("");
    const [partnerData, setPartnerData] = useState([]);
    const [label, setLabel] = useState('');
    const [comments, setComments] = useState("");
    const [validationError, setValidationError] = useState("");
    const textareaRef = useRef(null);

    const navigate = useNavigate();

    const moveToHome = () => {
        navigate('/partnermanagement')
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const onChangePartnerId = async (fieldName, selectedValue) => {
        setPartnerId(selectedValue);
        setPolicyName("");
    };

    const onChangePolicyName = (fieldName, selectedValue) => {
        setPolicyName(selectedValue);
        setPolicyId("");
    };

    const onChangeLabel = (value) => {
        setLabel(value)
    }

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !mb-1",
        dropdownButton: "!w-full !h-10 !rounded-md !text-base !text-left",
        selectionBox: "!top-10"
    }

    const validateComments = (comments) => {
        let error = "";
        const maxLength = 500;
        const regexPattern = /^(?!\s+$)[a-zA-Z0-9-_ ,.]*$/;

        if (comments.length > maxLength) {
            error = t('requestPolicy.commentTooLong');
        } else if (!regexPattern.test(comments)) {
            error = t('requestPolicy.specialCharNotAllowed');
        }
        setValidationError(error);
        return error === "";
    };

    const handleCommentChange = (e) => {
        const { value } = e.target;

        if (validateComments(value)) {
            setValidationError("");
        }
        setComments(value);
    };

    const clearForm = () => {
        setPartnerId("");
        setPartnerType("");
        setPolicyGroupName("");
        setPolicyName("");
        setComments("");
        setPoliciesDropdownData([]);
        setValidationError("");
    };

    const isFormValid = () => {
        return partnerId && policyName && comments && !validationError;
    };

    const clickOnSubmit = async () => {
        setErrorCode("");
        setErrorMsg("");
    }

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <div className={`flex justify-end max-w-7xl mb-5 absolute ${isLoginLanguageRTL ? "left-0" : "right-0"}`}>
                            <div className="flex justify-between items-center max-w-[400px] min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3">
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                            </div>
                        </div>
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between">
                            <div className="flex items-start gap-x-3">
                                <img src={backArrow} alt="" onClick={() => moveToAuthenticationServices(navigate)} className={`mt-[5%] cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} />
                                <div className="flex-col">
                                    <h1 className="font-semibold text-lg text-dark-blue">{t('generateApiKey.generateApiKey')}</h1>
                                    <div className="flex space-x-1">
                                        <p onClick={() => moveToHome()} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                            {t('commons.home')} /
                                        </p>
                                        <p onClick={() => moveToAuthenticationServices(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                            {t('authenticationServices.authenticationServices')}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="w-[100%] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                            <div className="px-[2.5%] py-[2%]">
                                <p className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                <form>
                                    <div className="flex flex-col">
                                        <div className="flex flex-row justify-between space-x-4 my-[1%] max-[700px]:flex-col">
                                            <div className="flex-col w-[48%]">
                                                <DropdownComponent
                                                    fieldName='partnerId'
                                                    fieldNameKey='requestPolicy.partnerId*'
                                                    placeHolderKey='createOidcClient.selectPartnerId'
                                                    selectedDropdownValue={partnerId}
                                                    styleSet={styles}
                                                    addInfoIcon={true}
                                                    infoKey='createOidcClient.partnerIdTooltip'>
                                                </DropdownComponent>
                                            </div>
                                            <div className="flex-col w-[48%] max-[700px]:ml-0">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red">*</span></label>
                                                <button disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-nowrap no-scrollbar" type="button">
                                                    <span>{partnerType || t("partnerTypes.authPartner")}</span>
                                                    <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                    </svg>
                                                </button>
                                            </div>
                                        </div>
                                        <div className="flex flex-row justify-between space-x-4 my-2 max-[500px]:flex-col">
                                            <div className="flex flex-col w-[48%]">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.policyGroup')}<span className="text-crimson-red">*</span></label>
                                                <button disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-sm text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
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
                                                    placeHolderKey='generateApiKey.selectedPolicyName'
                                                    selectedDropdownValue={policyName}
                                                    searchKey='commons.search'
                                                    styleSet={styles}
                                                    addInfoIcon={true}
                                                    disabled={!partnerId}
                                                    infoKey={t('createOidcClient.policyNameToolTip')} />
                                            </div>
                                        </div>
                                        <div className="space-y-6">
                                            <div className="my-4">
                                                <div className="flex flex-col w-[562px]">
                                                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('generateApiKey.label')}<span className="text-crimson-red">*</span></label>
                                                    <input value={label} onChange={(e) => onChangeLabel(e.target.value)}
                                                        className="h-10 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                                        placeholder={t('generateApiKey.labelGoesHere')} />
                                                </div>
                                            </div>
                                        </div>
                                        <div className="flex my-[1%]">
                                            <div className="flex flex-col w-full">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                    {t('requestPolicy.comments')}<span className="text-crimson-red">*</span>
                                                </label>
                                                <textarea ref={textareaRef} value={comments} onChange={(e) => handleCommentChange(e)} className="w-full px-2 py-2 border border-[#707070] rounded-md text-base text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-pre-wrap no-scrollbar" placeholder={t('requestPolicy.commentBoxDesc')}>
                                                </textarea>
                                                {validationError && <span className="text-sm text-crimson-red font-medium">{validationError}</span>}
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="border bg-medium-gray" />
                            <div className="flex flex-row px-[3%] py-5 justify-between max-[400px]:flex-col">
                                <button onClick={() => clearForm()} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.clearForm')}</button>
                                <div className={`flex flex-row space-x-3 w-full md:w-auto justify-end`}>
                                    <button onClick={() => moveToAuthenticationServices(navigate)} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                                    <button disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t('requestPolicy.submit')}</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </>
            )}
        </div>
    )
}

export default GenerateApiKey;