import { useEffect, useRef, useState } from "react"
import { useTranslation } from "react-i18next";
import { useBlocker, useNavigate } from "react-router-dom";
import { getPolicyDetails, getPolicyGroupList, isLangRTL } from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService";
import Title from "../../common/Title";
import ErrorMessage from "../../common/ErrorMessage";
import LoadingIcon from "../../common/LoadingIcon";
import uploadPolicyDataFileIcon from '../../../svg/upload_policy_data.svg';
import DropdownWithSearchComponent from "../../common/fields/DropdownWithSearchComponent";
import { HttpService } from "../../../services/HttpService";
import TextInputComponent from "../../common/fields/TextInputComponent";
import Confirmation from "../../common/Confirmation";
import BlockerPrompt from "../../common/BlockerPrompt";


function EditPolicy() {
    const navigate = useNavigate();
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [requiredPolicyData, setRequiredPolicyData] = useState({});
    const [policyGroup, setPolicyGroup] = useState("");
    const [policyName, setPolicyName] = useState("");
    const [policyData, setPolicyData] = useState("");
    const [policyDescription, setPolicyDescription] = useState("");
    const [policyGroupDropdownData, setPolicyGroupDropdownData] = useState([]);
    const [editPolicySuccess, setEditPolicySuccess] = useState(false);
    const [policyType, setPolicyType] = useState(null);
    const [backLink, setBackLink] = useState("");
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);
    const [policyNamePlaceHolderKey, setPolicyNamePlaceHolderKey] = useState("");
    const [policyDescriptionPlaceHolderKey, setPolicyDescriptionPlaceHolderKey] = useState("");
    const [confirmationHeader, setConfirmationHeader] = useState("");
    const [confirmationMessage, setConfirmationMessage] = useState("");

    const policyDescriptionRef = useRef(null);
    const policyDataRef = useRef(null);
    let isCancelledClicked = false;

    const blocker = useBlocker(
        ({ currentLocation, nextLocation }) => {
            if (isSubmitClicked || isCancelledClicked || editPolicySuccess) {
                setIsSubmitClicked(false);
                isCancelledClicked(false);
                return false;
            }
            return (
                (policyGroup || policyName || policyDescription || policyData) &&
                currentLocation.pathname !== nextLocation.pathname
            );
        }
    );

    useEffect(() => {
        const fetchData = async () => {
            const selectedPolicyData = localStorage.getItem('selectedPolicyData');
            if (selectedPolicyData) {
                try {
                    const data = JSON.parse(selectedPolicyData);
                    console.log(data);
                    setRequiredPolicyData(data);
                    let policyDetails = await getPolicyDetails(HttpService, data.policyId, setErrorCode, setErrorMsg, t);
                    console.log(policyDetails);
                    setPolicyGroup(policyDetails.policyGroupName)
                    setPolicyName(policyDetails.policyGroupName)
                    setPolicyDescription(policyDetails.policyDesc)
                    setPolicyData(JSON.stringify(policyDetails.policies.allowedKycAttributes, null, 2))
                } catch (err) {
                    console.error('Error in editPolicy page :', err);
                };
            };
            setDataLoaded(false);
            try {
                const storedPolicyType = localStorage.getItem('policyType');
                if (!storedPolicyType) {
                    console.err('policy Type not found');
                    navigate('/partnermanagement/admin/policy-manager/policy-group-list')
                }
                setPolicyType(storedPolicyType);
                if (storedPolicyType === 'Auth') {
                    setPolicyNamePlaceHolderKey('createPolicy.enterAuthPolicyName');
                    setPolicyDescriptionPlaceHolderKey('createPolicy.authPolicyDescription');
                    setConfirmationHeader('createPolicy.authPolicyConfirmationHeader');
                    setConfirmationMessage('createPolicy.authPolicyConfirmationMessage');
                    setBackLink('/partnermanagement/admin/policy-manager/auth-policies-list');
                } else if (storedPolicyType === 'DataShare') {
                    setPolicyNamePlaceHolderKey('createPolicy.enterDataSharePolicyName');
                    setPolicyDescriptionPlaceHolderKey('createPolicy.dataSharePolicyDescription');
                    setConfirmationHeader('createPolicy.dataSharePolicyConfirmationHeader');
                    setConfirmationMessage('createPolicy.dataSharePolicyConfirmationMessage');
                    setBackLink('/partnermanagement/admin/policy-manager/data-share-policies-list');
                }
                await getPolicyGroupList(HttpService, setPolicyGroupDropdownData, setErrorCode, setErrorMsg, t);

            } catch (err) {
                console.error('Error fetching data:', err);
                setErrorMsg(err);
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
            policyGroup || policyName || policyDescription || policyData;

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
    }, [policyGroup, policyName, policyDescription, policyData]);


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
        // setDataLoaded(false);
        setSuccessMsg("");
    };

    const onEditPolicyGroup = async (fieldName, selectedValue) => {
        setPolicyGroup(selectedValue);
    };

    const clearForm = () => {
        setPolicyName("");
        setPolicyGroup("");
        setPolicyDescription("");
        setPolicyData("");
        setErrorCode("");
        setErrorMsg("");
        setSuccessMsg("");
    };

    const clickOnCancel = () => {
        isCancelledClicked = true;
        navigate(backLink)
    }

    const onTextChange = (fieldName, fieldValue) => {
        setPolicyName(fieldValue);
    };

    const styleSet = {
        inputField: "min-h-10",
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const isFormValid = () => {
        return policyGroup && policyName && policyDescription.trim() && policyData;
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

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !mb-1",
        dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
        selectionBox: "!top-10"
    };

    const handleFileChange = (event) => {
        setErrorMsg("");
        setErrorCode("");
        setSuccessMsg("");
        const file = event.target.files[0];
        if (file?.type === "application/json") {
            const reader = new FileReader();
            reader.onload = () => {
                try {
                    const data = JSON.parse(reader.result);
                    setPolicyData(JSON.stringify(data, null, 2));
                } catch (error) {
                    setErrorMsg(t('createPolicy.jsonParseError'));
                }
            };
            reader.readAsText(file);
            setSuccessMsg(t('createPolicy.fileUploadSuccessMsg'));
        } else {
            setErrorMsg(t('createPolicy.uploadFileError'));
        }
        event.target.value = '';
    };

    return (
        <div className={`mt-2 w-full ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll relative font-inter`}>
            {!dataLoaded && (
                <LoadingIcon />
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                    )}
                    <div className="flex-col mt-7 w-full">
                        <div className="w-fit">
                            <Title title={requiredPolicyData.header} subTitle={requiredPolicyData.subTitle} backLink={requiredPolicyData.backLink} />
                        </div>
                        {!editPolicySuccess ?
                            <div className="w-[100%] bg-snow-white mt-[1%] rounded-lg shadow-md">
                                <div className="p-7">
                                    <p className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                    <form>
                                        <div className="flex flex-col w-full">
                                            <div className="flex flex-row justify-between my-4 max-[450px]:flex-col">
                                                <div className="flex flex-col w-2/4">
                                                    <DropdownWithSearchComponent
                                                        fieldName='policyGroup'
                                                        dropdownDataList={policyGroupDropdownData}
                                                        onDropDownChangeEvent={onEditPolicyGroup}
                                                        fieldNameKey='createPolicy.policyGroup*'
                                                        placeHolderKey='createPolicy.selectPolicyGroup'
                                                        searchKey='commons.search'
                                                        selectedDropdownValue={policyGroup}
                                                        styleSet={styles}
                                                        id='edit_policy_group_dropdown'
                                                    />
                                                </div>
                                                <div className={`flex flex-col w-2/4 ${isLoginLanguageRTL ? "mr-4" : "ml-4"}`}>
                                                    <TextInputComponent
                                                        fieldName="policyName"
                                                        textBoxValue={policyName}
                                                        onTextChange={onTextChange}
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
                                                                    {t('createPolicy.uploadPolicyDataFile')}
                                                                </h6>
                                                                <p className="text-xs text-light-gray">{t('createPolicy.uploadPolicyDataFileDesc')}</p>
                                                            </div>
                                                        </div>
                                                        <div>
                                                            <label htmlFor="fileInput" className="bg-tory-blue flex items-center justify-center h-11 w-28 text-snow-white text-xs font-semibold rounded-md cursor-pointer">
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
                                    <button id="edit_policy_form_clear_btn" onClick={() => clearForm()} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.clearForm')}</button>
                                    <div className={`flex flex-row space-x-3 w-full md:w-auto justify-end`}>
                                        <button id="edit_policy_form_cancel_btn" onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                                        <button id="edit_policy_form_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t('requestPolicy.submit')}</button>
                                    </div>
                                </div>
                            </div>
                            : <Confirmation />
                        }
                    </div>
                </>
            )}
            <BlockerPrompt blocker={blocker} />
        </div>
    )
}

export default EditPolicy;