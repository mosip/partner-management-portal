import { useState, useEffect, useRef } from "react";
import { useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { isLangRTL } from "../../../utils/AppUtils";
import { getPolicyManagerUrl, handleServiceErrors, createDropdownData, createRequest } from '../../../utils/AppUtils';
import { HttpService } from '../../../services/HttpService';
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import DropdownWithSearchComponent from "../../common/fields/DropdownWithSearchComponent";
import BlockerPrompt from "../../common/BlockerPrompt";
import Title from "../../common/Title";
import Confirmation from "../../common/Confirmation";
import TextInputComponent from "../../common/fields/TextInputComponent";
import uploadPolicyDataFileIcon from '../../../svg/upload_policy_data.svg';

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
    const [createPolicySuccess, setCreatePolicySuccess] = useState(false);
    const [confirmationData, setConfirmationData] = useState({});
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);
    const [policyType, setPolicyType] = useState(null);
    const policyDescriptionRef = useRef(null);
    const policyDataRef = useRef(null);
    let isCancelledClicked = false;

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const blocker = useBlocker(({ currentLocation, nextLocation }) => {
        if (isSubmitClicked || isCancelledClicked || createPolicySuccess) {
            setIsSubmitClicked(false);
            isCancelledClicked = false;
            setCreatePolicySuccess(false);
            return false;
        }

        return (
            (policyGroup || policyName || policyDescription || policyData) &&
            currentLocation.pathname !== nextLocation.pathname
        );
    });

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

    useEffect(() => {
        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const storedPolicyType = localStorage.getItem('policyType');
                if (!storedPolicyType) {
                    console.err('policy Type not found');
                    navigate('/partnermanagement/admin/policy-manager/policy-group-list')
                }
                setPolicyType(storedPolicyType);
                const response = await HttpService.get(getPolicyManagerUrl('/policies/policy-groups', process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        setPolicyGroupDropdownData(createDropdownData('name', '', false, resData, t));
                    } else {
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('commons.errorInResponse'));
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                setErrorMsg(err);
            }
        };
        fetchData();
    }, []);

    const clickOnSubmit = async () => {
        setIsSubmitClicked(true);
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);

        // Convert policyData from string to JSON
        let parsedPolicyData;
        try {
            parsedPolicyData = JSON.parse(policyData);
        } catch (error) {
            setErrorMsg(t('createPolicy.jsonParseError'));
            setIsSubmitClicked(false);
            setDataLoaded(true);
            return;
        }
        let request = createRequest({
            name: policyName,
            policyGroupName: policyGroup,
            policyType: policyType,
            desc: policyDescription,
            policies: parsedPolicyData,
            version: "1.1"
        });
        try {
            const response = await HttpService.post(getPolicyManagerUrl(`/policies`, process.env.NODE_ENV), request);
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const requiredData = {
                        backUrl: "/partnermanagement/admin/policy-manager/auth-policies-list",
                        header: "createPolicy.CreatePolicySuccessHeader",
                    }
                    setConfirmationData(requiredData);
                    setCreatePolicySuccess(true);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('requestPolicy.errorInMapPolicy'));
            }
        } catch (err) {
            setErrorMsg(err);
            console.log("Error fetching data: ", err);
        }
        setDataLoaded(true);
        setIsSubmitClicked(false);
    }

    const isFormValid = () => {
        return policyGroup && policyName && policyDescription.trim() && policyData.trim();
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
        adjustTextareaHeight(policyDescriptionRef);
    }, [policyDescription]);

    useEffect(() => {
        adjustTextareaHeight(policyDataRef);
    }, [policyData]);

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
        inputField: "min-h-10",
    };

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file?.type === "application/json") {
            const reader = new FileReader();
            reader.onload = () => {
                try {
                    const data = JSON.parse(reader.result);
                    setPolicyData(JSON.stringify(data));
                } catch (error) {
                    setErrorMsg(t('createPolicy.jsonParseError'));
                }
            };
            reader.readAsText(file);
        } else {
            setErrorMsg(t('createPolicy.uploadFileError'));
        }
        event.target.value = '';
    };    

    return (
        <div className={`mt-2 w-full ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll relative font-inter`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                    )}
                    <div className="flex-col mt-7 w-full">
                        <div className="w-fit">
                            <Title title='policyGroupList.policies' subTitle='policyGroupList.policies' backLink='/partnermanagement/admin/policy-manager/auth-policies-list'></Title>
                        </div>
                        {!createPolicySuccess ?
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
                                                        onDropDownChangeEvent={onChangePolicyGroup}
                                                        fieldNameKey='createPolicy.policyGroup*'
                                                        placeHolderKey='createPolicy.selectPolicyGroup'
                                                        searchKey='commons.search'
                                                        selectedDropdownValue={policyGroup}
                                                        styleSet={styles}
                                                        id='policy_group_dropdown'>
                                                    </DropdownWithSearchComponent>
                                                </div>
                                                <div className={`flex flex-col w-2/4 ${isLoginLanguageRTL ? "mr-4" : "ml-4"}`}>
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
                                            <div className="flex my-2">
                                                <div className="flex flex-col w-full">
                                                    <label className={`block text-dark-blue text-sm font-semibold mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                        {t('createPolicy.policyDescription')}<span className="text-crimson-red">*</span>
                                                    </label>
                                                    <textarea
                                                        id="policy_description_box"
                                                        ref={policyDescriptionRef}
                                                        value={policyDescription}
                                                        onChange={(e) => handlePolicyDescriptionChange(e)}
                                                        className="w-full min-h-11 h-11 p-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-pre-wrap no-scrollbar"
                                                        placeholder={t('createPolicy.writePolicyDescription')}
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
                                                        />
                                                    </div>
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
            <BlockerPrompt blocker={blocker} />
        </div>
    )
}

export default CreatePolicy;