import { useState, useEffect, useRef } from "react";
import { useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { isLangRTL } from "../../../utils/AppUtils";
import { getPartnerManagerUrl, getPolicyManagerUrl, handleServiceErrors, moveToPolicies, getPartnerTypeDescription, createDropdownData, createRequest } from '../../../utils/AppUtils';
import { HttpService } from '../../../services/HttpService';
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import DropdownComponent from "../../common/fields/DropdownComponent";
import DropdownWithSearchComponent from "../../common/fields/DropdownWithSearchComponent";
import BlockerPrompt from "../../common/BlockerPrompt";
import Title from "../../common/Title";
import Confirmation from "../../common/Confirmation";
import TextInputComponent from "../../common/fields/TextInputComponent";

function CreatePolicy() {
    const navigate = useNavigate();
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [policyName, setPolicyName] = useState("");
    const [policyGroup, setPolicyGroup] = useState("");
    const [policyDescription, setPolicyDescription] = useState("");
    const [policyData, setPolicyData] = useState("");
    const [policyGroupDropdownData, setPolicyGroupDropdownData] = useState([]);
    const textareaRef = useRef(null);
    const [createPolicySuccess, setCreatePolicySuccess] = useState(false);
    const [confirmationData, setConfirmationData] = useState({});
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);
    let isCancelledClicked = false;

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };


    const onChangePolicyGroup = async (fieldName, selectedValue) => {
        setPolicyGroup(selectedValue);
    };

    const clearForm = () => {
        setPolicyName("");
        setPolicyGroup("");
        setPolicyDescription("");
        setPolicyData("");
    };

    const clickOnCancel = () => {
        isCancelledClicked = true;
        navigate('/partnermanagement/admin/policy-manager/auth-policies-list')
    }

    const clickOnSubmit = async () => {
    }

    const isFormValid = () => {
        return policyGroup && policyName && policyDescription.trim();
    };

    const handlePolicyDescriptionChange = (e) => {
        const { value } = e.target;
        setPolicyDescription(value);
    };

    const handlePolicyDataChange = (e) => {
        const { value } = e.target;
        setPolicyData(value);
    };

    const adjustTextareaHeight = (ref) => {
        if (ref && ref.current) {
            ref.current.style.height = 'auto';
            ref.current.style.height = `${ref.current.scrollHeight}px`;
        }
    };

    useEffect(() => {
        adjustTextareaHeight(textareaRef);
    }, [policyDescription, policyData]);

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !mb-1",
        dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
        selectionBox: "!top-10"
    }

    const onTextChange = (fieldName, fieldValue) => {
        setPolicyName(fieldValue);
    };

    const styleSet = {
        inputField: "min-w-64 w-full min-h-10",
        outerDiv: "mx-4"
    };

    const handleFileChange = (event) => {
        console.log('File change event triggered');
        const file = event.target.files[0];
        if (file && file.type === "application/json") {
            const reader = new FileReader();
            reader.onload = () => {
                try {
                    const data = JSON.parse(reader.result);
                    const dataString = JSON.stringify(data);
                    setPolicyData(dataString);
                    console.log('aaa')
                } catch (error) {
                    setErrorMsg(t('createPolicy.jsonParseError'));
                }
            };
            reader.readAsText(file);
        } else {
            setErrorMsg(t('createPolicy.uploadFileError'));
        }
    };

    function isPolicyDataEmpty() {
        return policyData.trim() === '';
    }

    const removeFileData = () => {
        setPolicyData("");
    }

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll relative font-inter`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                    )}
                    <div className="flex-col mt-7">
                        <Title title='policyGroupList.policies' subTitle='policyGroupList.policies' backLink='/partnermanagement/admin/policy-manager/auth-policies-list'></Title>
                        {!createPolicySuccess ?
                            <div className="w-[100%] bg-snow-white mt-[1%] rounded-lg shadow-md">
                                <div className="p-7">
                                    <p className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                    <form>
                                        <div className="flex flex-col w-full">
                                            <div className="flex flex-row justify-between space-x-4 my-[1%] max-[450px]:flex-col">
                                                <div className="flex flex-col w-2/4">
                                                    <DropdownComponent
                                                        fieldName='policyGroup'
                                                        dropdownDataList={policyGroupDropdownData}
                                                        onDropDownChangeEvent={onChangePolicyGroup}
                                                        fieldNameKey='createPolicy.policyGroup*'
                                                        placeHolderKey='createPolicy.selectPolicyGroup'
                                                        selectedDropdownValue={policyGroup}
                                                        styleSet={styles}
                                                        id='policy_group_dropdown'>
                                                    </DropdownComponent>
                                                </div>
                                                <div className="flex flex-col w-2/4">
                                                    <TextInputComponent
                                                        fieldName="policyName"
                                                        textBoxValue={policyName}
                                                        onTextChange={onTextChange}
                                                        fieldNameKey="createPolicy.policyName*"
                                                        placeHolderKey="createPolicy.writePolicyName"
                                                        styleSet={styleSet}
                                                        id="policy_name_box"
                                                    />
                                                </div>
                                            </div>
                                            <div className="flex my-[1%]">
                                                <div className="flex flex-col w-full">
                                                    <label className={`block text-dark-blue text-sm font-semibold mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                        {t('createPolicy.policyDescription')}<span className="text-crimson-red">*</span>
                                                    </label>
                                                    <textarea
                                                        id="policy_description_box"
                                                        ref={textareaRef}
                                                        value={policyDescription}
                                                        onChange={(e) => handlePolicyDescriptionChange(e)}
                                                        className="w-full min-h-11 h-11 p-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-pre-wrap no-scrollbar"
                                                        placeholder={t('createPolicy.writePolicyDescription')}
                                                    />
                                                </div>
                                            </div>
                                            <div className="flex my-[1%]">
                                                <div className="w-full">
                                                    <label className={`block text-dark-blue text-sm font-semibold mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                        {t('createPolicy.policyData')}<span className="text-crimson-red">*</span>
                                                    </label>
                                                    <textarea
                                                        id="policy_data_box"
                                                        ref={textareaRef}
                                                        value={policyData}
                                                        onChange={(e) => handlePolicyDataChange(e)}
                                                        className="w-full min-h-11 p-3 max-h-80 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-pre-wrap"
                                                        placeholder={t('createPolicy.policyDataDesc')}
                                                    />
                                                </div>
                                                <div className="pt-4 flex text-gray-500 text-xs font-semibold items-start">
                                                    <div className="px-3 pt-2">
                                                        <label htmlFor="fileInput" className="bg-tory-blue flex items-center justify-center h-11 w-28 text-snow-white text-xs font-semibold rounded-md cursor-pointer">
                                                            <svg width="13" height="13" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                                                                <path d="M7 12V3.85L4.4 6.45L3 5L8 0L13 5L11.6 6.45L9 3.85V12H7ZM2 16C1.45 16 0.979167 15.8042 0.5875 15.4125C0.195833 15.0208 0 14.55 0 14V11H2V14H14V11H16V14C16 14.55 15.8042 15.0208 15.4125 15.4125C15.0208 15.8042 14.55 16 14 16H2Z" fill="white" />
                                                            </svg>
                                                            <span className="px-2">{t('createPolicy.upload')}</span>
                                                        </label>
                                                        <input
                                                            type="file"
                                                            id="fileInput"
                                                            accept=".json"
                                                            style={{ display: 'none' }}
                                                            onChange={handleFileChange}
                                                        />
                                                    </div>

                                                    <button
                                                        onClick={removeFileData}
                                                        disabled={isPolicyDataEmpty()}
                                                        className={`mx-2 pt-2 focus:outline-none flex flex-col items-center ${isPolicyDataEmpty() ? 'text-gray-500' : 'cursor-pointer text-tory-blue'
                                                            }`}
                                                    >
                                                        <svg
                                                            width="20"
                                                            height="21"
                                                            viewBox="0 0 31 34"
                                                            fill={isPolicyDataEmpty() ? '#A5A5A5' : '#1447B2'}
                                                            xmlns="http://www.w3.org/2000/svg"
                                                            className={`${isPolicyDataEmpty() ? '' : 'cursor-pointer'}`}
                                                        >
                                                            <path
                                                                d="M5.65391 34C4.64976 34 3.79193 33.6444 3.08043 32.9333C2.36926 32.2218 2.01368 31.3639 2.01368 30.3598V4.80162H0V1.7811H9.06156V0H21.1437V1.7811H30.2052V4.80162H28.1915V30.3598C28.1915 31.377 27.8391 32.238 27.1344 32.9428C26.4296 33.6476 25.5685 34 24.5513 34H5.65391ZM25.171 4.80162H5.0342V30.3598C5.0342 30.5407 5.09226 30.6892 5.20839 30.8053C5.32451 30.9214 5.47302 30.9795 5.65391 30.9795H24.5513C24.7064 30.9795 24.8483 30.9149 24.9772 30.7857C25.1064 30.6568 25.171 30.5148 25.171 30.3598V4.80162ZM9.87509 26.9521H12.8951V8.82899H9.87509V26.9521ZM17.3101 26.9521H20.3301V8.82899H17.3101V26.9521Z"
                                                                
                                                            />
                                                        </svg>
                                                        <p className="pt-1">{t('createPolicy.remove')}</p>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div className="border bg-medium-gray" />
                                <div className="flex flex-row px-[3%] py-5 justify-between">
                                    <button id="create_policy_form_clear_btn" onClick={() => clearForm()} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.clearForm')}</button>
                                    <div className={`flex flex-row space-x-3 w-full md:w-auto justify-end`}>
                                        <button id="create_policy_form_cancel_btn" onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                                        <button id="create_policy_form_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t('requestPolicy.submit')}</button>
                                    </div>
                                </div>
                            </div>
                            : <Confirmation confirmationData={confirmationData} />
                        }
                    </div>
                </>
            )}
        </div>
    )
}

export default CreatePolicy;