import { useEffect, useRef, useState } from "react"
import { useTranslation } from "react-i18next";
import { useBlocker, useNavigate } from "react-router-dom";
import { createRequest, getPolicyDetails, getPolicyManagerUrl, handleServiceErrors, isLangRTL, trimAndReplace, handleFileChange } from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService";
import Title from "../../common/Title";
import ErrorMessage from "../../common/ErrorMessage";
import LoadingIcon from "../../common/LoadingIcon";
import uploadPolicyDataFileIcon from '../../../svg/upload_policy_data.svg';
import { HttpService } from "../../../services/HttpService";
import TextInputComponent from "../../common/fields/TextInputComponent";
import Confirmation from "../../common/Confirmation";
import BlockerPrompt from "../../common/BlockerPrompt";
import SuccessMessage from "../../common/SuccessMessage";

function EditPolicy() {
    const navigate = useNavigate();
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [policyType, setPolicyType] = useState(null);
    const [title, setTitle] = useState("");
    const [subTitle, setSubTitle] = useState("");
    const [policyNamePlaceHolderKey, setPolicyNamePlaceHolderKey] = useState("");
    const [policyDescriptionPlaceHolderKey, setPolicyDescriptionPlaceHolderKey] = useState("");
    const [confirmationHeader, setConfirmationHeader] = useState("");
    const [confirmationMessage, setConfirmationMessage] = useState("");
    const [backLink, setBackLink] = useState("");
    const [policyId, setPolicyId] = useState("");
    const [policyDetails, setPolicyDetails] = useState({});
    const [policyName, setPolicyName] = useState("");
    const [policyDescription, setPolicyDescription] = useState("");
    const [policyData, setPolicyData] = useState("");
    const [confirmationData, setConfirmationData] = useState({});
    const [editPolicySuccess, setEditPolicySuccess] = useState(false);
    
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);

    const policyDescriptionRef = useRef(null);
    const policyDataRef = useRef(null);

    const blocker = useBlocker(
        ({ currentLocation, nextLocation }) => {
            if (isSubmitClicked || editPolicySuccess) {
                setIsSubmitClicked(false);
                return false;
            }
            return (
                ((trimAndReplace(policyName) !== policyDetails.policyName) || 
                (trimAndReplace(policyDescription) !== policyDetails.policyDesc) || 
                (policyData !== JSON.stringify(policyDetails.policies, null, 2))) && currentLocation.pathname !== nextLocation.pathname
            );
        }
    );

    useEffect(() => {
        const fetchData = async () => {
            setDataLoaded(false);
            try {
                const storedPolicyType = localStorage.getItem('activeTab');
                if (!storedPolicyType) {
                    console.err('policy Type not found');
                    navigate('/partnermanagement/policy-manager/policy-group-list')
                }
                setPolicyType(storedPolicyType);
                if (storedPolicyType === 'DataShare') {
                    setTitle('editPolicy.editDataSharePolicyTitle');
                    setSubTitle('policiesList.listOfDataSharePolicies');
                    setPolicyNamePlaceHolderKey('createPolicy.enterDataSharePolicyName');
                    setPolicyDescriptionPlaceHolderKey('createPolicy.dataSharePolicyDescription');
                    setConfirmationHeader('editPolicy.dataSharePolicyConfirmationHeader');
                    setConfirmationMessage('editPolicy.dataSharePolicyConfirmationMessage');
                    setBackLink('/partnermanagement/policy-manager/data-share-policies-list');
                } else if (storedPolicyType === 'Auth') {
                    setTitle('editPolicy.editAuthPolicyTitle');
                    setSubTitle('policiesList.listOfAuthPolicies');
                    setPolicyNamePlaceHolderKey('createPolicy.enterAuthPolicyName');
                    setPolicyDescriptionPlaceHolderKey('createPolicy.authPolicyDescription');
                    setConfirmationHeader('editPolicy.authPolicyConfirmationHeader');
                    setConfirmationMessage('editPolicy.authPolicyConfirmationMessage');
                    setBackLink('/partnermanagement/policy-manager/auth-policies-list');
                }
                const selectedPolicyId = localStorage.getItem('policyId');
                if (selectedPolicyId) {
                    setPolicyId(selectedPolicyId)
                    let policyInfo = await getPolicyDetails(HttpService, selectedPolicyId, setErrorCode, setErrorMsg, t);
                    if (policyInfo !== null) {
                        setPolicyDetails(policyInfo);
                        setPolicyName(policyInfo.policyName)
                        setPolicyDescription(policyInfo.policyDesc)
                        setPolicyData(JSON.stringify(policyInfo.policies, null, 2));
                    } else {
                        setErrorMsg(t('clonePolicyPopup.errorInPolicyDetails'))
                    }
                } else {
                    setErrorMsg(t('editPolicy.policyIdNotExist'))
                }
            } catch (err) {
                console.error('Error fetching data:', err);
                if (err.response?.status && err.response.status !== 401) {
                    setErrorMsg(err.toString());
                }
            }
            setDataLoaded(true);
        };
        fetchData();
    }, []);

    const adjustTextareaHeight = (ref) => {
        if (ref && ref.current) {
            ref.current.style.height = 'auto';
            ref.current.style.height = `${ref.current.scrollHeight}px`;
        }
    };

    useEffect(() => {
        const shouldWarnBeforeUnload = () => 
            (trimAndReplace(policyName) !== policyDetails.policyName) || 
            (trimAndReplace(policyDescription) !== policyDetails.policyDesc) || 
            (policyData !== JSON.stringify(policyDetails.policies, null, 2));

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
    }, [policyName, policyDescription, policyData]);


    useEffect(() => {
        adjustTextareaHeight(policyDescriptionRef);
    }, [policyDescription]);


    useEffect(() => {
        adjustTextareaHeight(policyDataRef);
    }, [policyData]);


    const clickOnSubmit = async () => {
        setIsSubmitClicked(true);
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        setSuccessMsg("");

        // Convert policyData from string to JSON
        let parsedPolicyData;
        try {
            parsedPolicyData = JSON.parse(policyData);
            if (Array.isArray(parsedPolicyData) || parsedPolicyData === null) {
                throw new Error("Parsed data is not a valid JSON object");
            }
        } catch (error) {
            setErrorMsg(t('createPolicy.jsonParseError'));
            setIsSubmitClicked(false);
            setDataLoaded(true);
            return;
        }
        let request = createRequest({
            name: trimAndReplace(policyName),
            policyGroupName: policyDetails.policyGroupName,
            desc: trimAndReplace(policyDescription),
            policies: JSON.parse(policyData),
            version: '1.1'
        });
        try {
            const response = await HttpService({
                url: getPolicyManagerUrl(`/policies/${policyId}`, process.env.NODE_ENV),
                method: 'put',
                baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL,
                data: request
            });
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const requiredData = {
                        backUrl: backLink,
                        header: confirmationHeader,
                        description: confirmationMessage,
                        descriptionParams: { policyGroupName: policyDetails.policyGroupName },
                    }
                    setConfirmationData(requiredData);
                    setEditPolicySuccess(true);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('editPolicy.errorInEditPolicy'));
            }
        } catch (err) {
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            console.log("Error fetching data: ", err);
        }
        setDataLoaded(true);
        setIsSubmitClicked(false);
    };

    const undoChanges = () => {
        setErrorCode("");
        setErrorMsg("");
        setSuccessMsg("");
        setPolicyName(policyDetails.policyName);
        setPolicyDescription(policyDetails.policyDesc);
        setPolicyData(JSON.stringify(policyDetails.policies, null, 2));
    };

    const clickOnCancel = () => {
        navigate(backLink);
    }

    const onPolicyNameChange = (fieldName, fieldValue) => {
        setPolicyName(fieldValue);
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const cancelSuccessMsg = () => {
        setSuccessMsg("");
    };

    const isFormValid = () => {
        return (
        (trimAndReplace(policyName) !== policyDetails.policyName) ||
        (trimAndReplace(policyDescription) !== policyDetails.policyDesc) ||
        (policyData !== JSON.stringify(policyDetails.policies, null, 2)))
        && policyName.trim() !== "" && policyDescription !== "" && policyData !== "";
    };

    const handlePolicyDescriptionChange = (e) => {
        const { value } = e.target;
        setPolicyDescription(value);
    };

    const handlePolicyDataChange = (e) => {
        setErrorMsg("");
        setSuccessMsg("");
        const { value } = e.target;
        setPolicyData(value);
    };

    const onFileChangeEvent = (event) => {
        handleFileChange(event, setErrorCode, setErrorMsg, setSuccessMsg, setPolicyData, t);
    }

    const styleSet = {
        inputField: "min-h-10",
    };

    const successCustomStyle = {
        outerDiv: `flex justify-end max-w-7xl my-5 absolute ${isLoginLanguageRTL ? "left-0.5" : "right-0.5"}`,
        innerDiv: "flex justify-between items-center rounded-xl max-w-[35rem] min-h-14 min-w-80 p-4"
    }

    return (
        <div className={`mt-2 w-full ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll relative font-inter`}>
            {!dataLoaded && (
                <LoadingIcon />
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    {successMsg && (
                        <SuccessMessage successMsg={successMsg} clickOnCancel={cancelSuccessMsg} customStyle={successCustomStyle} />
                    )}
                    <div className="flex-col mt-5 w-full">
                        <div className="w-fit">
                            <Title title={title} subTitle={subTitle} backLink={backLink} />
                        </div>
                        {!editPolicySuccess ?
                            <div className="w-[100%] bg-snow-white mt-[1%] rounded-lg shadow-md">
                                <div className="p-7">
                                    <p className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                    <form>
                                        <div className="flex flex-col w-full">
                                            <div className="flex flex-row justify-between my-4 max-[450px]:flex-col">
                                                <div className="flex flex-col w-2/4">
                                                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                        {t('createPolicy.policyGroup')}<span className="text-crimson-red mx-1">*</span>
                                                    </label>
                                                    <button disabled className="flex items-center justify-between w-full min-h-11 px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                        overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                        <span className="w-full break-words text-wrap text-start">{policyDetails.policyGroupName}</span>
                                                        <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                        </svg>
                                                    </button>
                                                </div>
                                                <div className={`flex flex-col w-2/4 ${isLoginLanguageRTL ? "mr-4" : "ml-4"}`}>
                                                    <TextInputComponent
                                                        fieldName="policyName"
                                                        textBoxValue={policyName}
                                                        onTextChange={onPolicyNameChange}
                                                        fieldNameKey="createPolicy.policyName*"
                                                        placeHolderKey={policyNamePlaceHolderKey}
                                                        styleSet={styleSet}
                                                        id="policy_name_box"
                                                        maxLength={128}
                                                    />
                                                </div>
                                            </div>
                                            <div className="flex my-2">
                                                <div className="flex flex-col w-full">
                                                    <label className={`block text-dark-blue text-sm font-semibold mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                        {t('createPolicy.policyDescription')}<span className="text-crimson-red px-1">*</span>
                                                    </label>
                                                    <textarea
                                                        id="policy_description_box"
                                                        ref={policyDescriptionRef}
                                                        value={policyDescription}
                                                        onChange={(e) => handlePolicyDescriptionChange(e)}
                                                        className="w-full min-h-11 h-11 p-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-pre-wrap no-scrollbar"
                                                        placeholder={t(policyDescriptionPlaceHolderKey)}
                                                        maxLength={256}
                                                    />
                                                </div>
                                            </div>
                                            <div className="rounded-lg shadow-md border my-5">
                                                <div className={`flex-col`}>
                                                    <div className={`flex py-[1rem] px-5 bg-[#F9FBFF] justify-between items-center max-520:flex-col`}>
                                                        <div className="flex space-x-4 items-center ">
                                                            <img src={uploadPolicyDataFileIcon} className="h-8" alt="" />
                                                            <div className="flex-col p-1 items-center">
                                                                <h6 className={`text-sm font-semibold text-dark-blue`}>
                                                                    {t('editPolicy.reUploadPolicyData')}<span className="text-crimson-red mx-1">*</span>
                                                                </h6>
                                                                <p className="text-xs text-light-gray">{t('createPolicy.uploadPolicyDataFileDesc')}</p>
                                                            </div>
                                                        </div>
                                                        <div>
                                                            <label htmlFor="fileInput" className="bg-tory-blue flex items-center justify-center h-11 w-28 text-snow-white text-xs font-semibold rounded-md cursor-pointer">
                                                                <span className="px-2">{t('editPolicy.reuploadBtn')}</span>
                                                            </label>
                                                            <input
                                                                type="file"
                                                                id="fileInput"
                                                                accept=".json"
                                                                style={{ display: 'none' }}
                                                                onChange={onFileChangeEvent}
                                                            />
                                                        </div>
                                                    </div>
                                                    <hr className="border bg-medium-gray h-px" />
                                                    <div className="flex items-center p-5 bg-white rounded-lg">
                                                        <textarea
                                                            id="policy_data_box"
                                                            ref={policyDataRef}
                                                            value={policyData}
                                                            onChange={(e) => handlePolicyDataChange(e)}
                                                            className="w-full min-h-11 p-3 max-h-80 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-pre-wrap"
                                                            placeholder={t('createPolicy.policyDataDesc')}
                                                            disabled={!policyData}
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div className="border bg-medium-gray" />
                                <div className="flex flex-row px-[3%] py-5 justify-between">
                                    <button id="edit_policy_undo_changes_btn" onClick={() => undoChanges()} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('commons.undoChanges')}</button>
                                    <div className={`flex flex-row space-x-3 w-full md:w-auto justify-end`}>
                                        <button id="edit_policy_form_cancel_btn" onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                                        <button id="edit_policy_form_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t('requestPolicy.submit')}</button>
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

export default EditPolicy;