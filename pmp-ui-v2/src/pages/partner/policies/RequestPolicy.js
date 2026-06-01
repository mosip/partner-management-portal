import { useState, useEffect, useRef } from "react";
import { useNavigate, useBlocker, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { isLangRTL, getPartnerManagerUrl, getPolicyManagerUrl, handleServiceErrors, getPartnerTypeDescription, createDropdownData, createRequest, validateInputRegex } from '../../../utils/AppUtils';
import { HttpService } from '../../../services/HttpService';
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import DropdownComponent from "../../common/fields/DropdownComponent";
import DropdownWithSearchComponent from "../../common/fields/DropdownWithSearchComponent";
import BlockerPrompt from "../../common/BlockerPrompt";
import Title from "../../common/Title";
import Confirmation from "../../common/Confirmation";
import ApprovePopup from "../../common/ApprovePopup";
import PropTypes from 'prop-types';

function RequestPolicy() {
    const userProfile = getUserProfile();
    const userRoles = (userProfile?.roles ?? '').split(',').map(role => role.trim()).filter(Boolean);
    const isCredentialPartner = userRoles.includes('CREDENTIAL_PARTNER');
    const isOnlineVerificationPartner = (userProfile?.partnerType ?? '').toUpperCase() === 'ONLINE_VERIFICATION_PARTNER';
    const navigate = useNavigate();
    const location = useLocation();
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [partnerId, setPartnerId] = useState("");
    const [policyName, setPolicyName] = useState("");
    const [partnerType, setPartnerType] = useState("");
    const [policyGroupName, setPolicyGroupName] = useState("");
    const [partnerComment, setPartnerComment] = useState("");
    const [partnerIdDropdownData, setPartnerIdDropdownData] = useState([]);
    const [policiesDropdownData, setPoliciesDropdownData] = useState([]);
    const [partnerData, setPartnerData] = useState([]);
    const [policyList, setPolicyList] = useState([]);
    const textareaRef = useRef(null);
    const [requestPolicySuccess, setRequestPolicySuccess] = useState(false);
    const [confirmationData, setConfirmationData] = useState({});
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);
    const [inputError, setInputError] = useState("");
    const [showApprovePopup, setShowApprovePopup] = useState(false);
    const [approveRequest, setApproveRequest] = useState({});
    const [popupData, setPopupData] = useState({});
    const [partnerTypeDropdownData, setPartnerTypeDropdownData] = useState([]);
    const [selectedPolicyId, setSelectedPolicyId] = useState("");

    // Determine admin path at the top of component to avoid temporal dead zone
    const isAdminPath = location.pathname.includes('admin');
    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const blocker = useBlocker(
        ({ currentLocation, nextLocation }) => {
            if (isSubmitClicked || requestPolicySuccess) {
                setIsSubmitClicked(false);
                return false;  
            }
            return (
                (partnerId !== "" || partnerType !== "" ||
                    policyName !== "" || partnerComment !== "") &&
                currentLocation.pathname !== nextLocation.pathname
            );
        }
    );
    useEffect(() => {
        const shouldWarnBeforeUnload = () => {
            return partnerId !== "" ||
                partnerType !== "" ||
                partnerComment !== "" ||
                policyName !== "";
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
    }, [partnerId, partnerType, partnerComment, policyName, isSubmitClicked]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const isAdmin = location.pathname.includes('admin');

                // Initialize partner type dropdown data with MISP Partner and ABIS Partner
                if (isAdmin) {
                const partnerTypeData = [
             {
                partnerType: "MISP_Partner",
                description: getPartnerTypeDescription("MISP_Partner", t)
             },
             {
                 partnerType: "ABIS_Partner",
                 description: getPartnerTypeDescription("ABIS_Partner", t)
             },
             {
                partnerType: "Manual_Adjudication",
                description: getPartnerTypeDescription("Manual_Adjudication", t)
             },
             {
                partnerType: "Online_Verification_Partner",
                description: getPartnerTypeDescription("ONLINE_VERIFICATION_PARTNER", t)
             }
            ];
                    setPartnerTypeDropdownData(createDropdownData('partnerType', 'description', false, partnerTypeData, t));
                } else {
                    const apiUrl = '/partners/v3?status=approved&policyGroupAvailable=true';
                    await fetchPartnerList(apiUrl);
                }
            } catch (err) {
                console.error('Error fetching data:', err);
                if (err.response?.status && err.response.status !== 401) {
                    setErrorMsg(err.toString());
                }
            }
        };
        fetchData();
    }, [t]);

    const onChangePartnerType = async (fieldName, selectedValue) => {
        setPartnerType(selectedValue);
        setPartnerId("");
        setPolicyName("");
        setSelectedPolicyId("");
        setPolicyGroupName("");
        setPoliciesDropdownData([]);
        setPartnerComment("");
        const apiUrl = `/partners/v3?status=approved&partnerType=${selectedValue}`;
        await fetchPartnerList(apiUrl);
    }

    const fetchPartnerList = async (apiUrl) => {
        try {
            setDataLoaded(false);
            const response = await HttpService.get(getPartnerManagerUrl(apiUrl, process.env.NODE_ENV));
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response;
                    setPartnerData(resData);
                    setPartnerIdDropdownData(createDropdownData('partnerId', '', false, resData, t));
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('commons.errorInResponse'));
            }
            setDataLoaded(true);
        } catch (err) {
            console.error('Error fetching data:', err);
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            setDataLoaded(true);
        }
    };


    const onChangePartnerId = async (fieldName, selectedValue) => {
        setPartnerId(selectedValue);
        setPolicyName("");
        // Find the selected partner data
        const selectedPartner = partnerData.find(item => item.partnerId === selectedValue);
        console.log("Selected Partner Data: ", selectedPartner);
        if (selectedPartner) {
            if (!isAdminPath) {
                setPartnerType(getPartnerTypeDescription(selectedPartner.partnerType, t));
            }
            if (selectedPartner.partnerType === 'MISP_Partner' && isAdminPath) {
                setPolicyGroupName(selectedPartner.policyGroupName || t('requestPolicy.policyGroupNotAvailable'));
            } else {
                setPolicyGroupName(selectedPartner.policyGroupName);
            }
            if (selectedPartner.policyGroupName) {
                await getListofPolicies(selectedPartner.policyGroupName);
            }
        }
    };

    const onChangePolicyName = (fieldName, selectedValue) => {
        const selectedPolicy = policyList.find(item => item.name === selectedValue);
        if (selectedPolicy) {
            setPolicyName(selectedValue);
            setSelectedPolicyId(selectedPolicy.policyId || selectedPolicy.id || "");
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
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('requestPolicy.errorInFetchingPolicyNames'));
            }
            setDataLoaded(true);
        } catch (err) {
            console.error('Error fetching policies:', err);
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
        }
    };

    const clearForm = () => {
        setErrorMsg("");
        setErrorCode("");
        setPartnerId("");
        setPartnerType("");
        setPolicyGroupName("");
        setPolicyName("");
        setSelectedPolicyId("");
        setPartnerComment("");
        setPoliciesDropdownData([]);
        setInputError("");
    };

    // Determine backUrl and cancelUrl based on admin path
    const backUrl = isAdminPath ? '/partnermanagement/admin/policy-requests-list' : '/partnermanagement/policies/policies-list';
    const cancelUrl = backUrl;

    const clickOnCancel = () => {
        navigate(cancelUrl);
    }
    const clickOnSubmit = async () => {
        setIsSubmitClicked(true);
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        let request = createRequest({
            policyName: policyName,
            useCaseDescription: partnerComment
        });
        try {
            const response = await HttpService.post(getPartnerManagerUrl(`/partners/${partnerId}/policy/map`, process.env.NODE_ENV), request);
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response;
                    if (isCredentialPartner && !isAdminPath) {
                                            const requestPayload = {
                                                partnerId,
                                                partnerType,
                                                policyGroupName,
                                                policyName,
                                                partnerComment,
                                                policyId: selectedPolicyId
                                            };
                                            navigate('/partnermanagement/policies/map-biometric-extractor-provider', {
                                                state: {
                                                    partnerId,
                                                    partnerType,
                                                    policyGroupName,
                                                    policyName,
                                                    policyId: selectedPolicyId,
                                                    mappingKey: resData.mappingkey,
                                                    requestPayload
                                                }
                                            });
                                            setDataLoaded(true);
                                            return;
                                        }
                                        const selectedPartnerData = partnerData.find(p => p.partnerId === partnerId);
                                        const selectedRawPartnerType = (selectedPartnerData?.partnerType ?? '').toUpperCase();
                                        if (selectedRawPartnerType === 'ONLINE_VERIFICATION_PARTNER') {
                                            const requestPayload = {
                                                partnerId,
                                                partnerType,
                                                policyGroupName,
                                                policyName,
                                                partnerComment,
                                                policyId: selectedPolicyId
                                            };
                                            navigate('/partnermanagement/policies/map-biometric-extractor-provider', {
                                                state: {
                                                    partnerId,
                                                    partnerType,
                                                    policyGroupName,
                                                    policyName,
                                                    policyId: selectedPolicyId,
                                                    mappingKey: resData.mappingkey,
                                                    requestPayload,
                                                    isAdminPath
                                                }
                                            });
                                            setDataLoaded(true);
                                            return;
                                        }
                    setPopupData(isAdminPath ? {id: resData.mappingkey} : {});
                    const requiredData = {
                        title: "requestPolicy.requestPolicy",
                        backUrl: backUrl,
                        header: "requestPolicy.policySuccessHeader",
                        description: isAdminPath ? "requestPolicy.policySuccessMsgForAdmin" : "requestPolicy.policySuccessMsg",
                        subNavigation: isAdminPath ? "viewPolicyRequest.listOfPolicyRequests" : "requestPolicy.policies",
                        customBtnName1: isAdminPath ? "approveRejectPopup.approve" : undefined,
                        customBtnName2: isAdminPath ? "commons.goBack" : undefined,
                        customBtn2Id: isAdminPath ? "confirmation_go_back_btn": undefined
                    }
                    setConfirmationData(requiredData);
                    setRequestPolicySuccess(true);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('requestPolicy.errorInMapPolicy'));
            }
            setDataLoaded(true);
        } catch (err) {
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            console.log("Error fetching data: ", err);
        }
        setIsSubmitClicked(false);
    }

    const isFormValid = () => {
        return partnerId && partnerType && policyName && partnerComment.trim() && !inputError;
    };

    const handleCommentChange = (e) => {
        const { value } = e.target;
        setPartnerComment(value);
        validateInputRegex(value, setInputError, t);
    };

    const adjustTextareaHeight = () => {
        if (textareaRef.current) {
            textareaRef.current.style.height = 'auto';
            textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
        }
    };

    useEffect(() => {
        adjustTextareaHeight();
    }, [partnerComment]);

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !mb-1",
        dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
        selectionBox: "!top-10"
    }

    const styleForSearch = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !mb-1",
        dropdownButton: "!w-full !rounded-md !text-base !text-start",
        selectionBox: "!top-10"
    }

    const PartnerTypeLabel = ({ isAdmin }) => (
        <div className="flex flex-col w-[48%] max-[450px]:w-full">
            <label id='request_policy_partner_type_label' className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red mx-1">*</span></label>
            <button id='request_policy_partner_type' disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
        overflow-x-auto whitespace-normal no-scrollbar" type="button">
                <span className={`w-full break-words ${(partnerType || isAdmin) ? 'text-dark-blue' : 'text-gray-400'} text-wrap text-start`}>{isAdmin ? t('partnerTypes.mispPartner') : partnerType || t('commons.partnersHelpText')}</span>
                <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                </svg>
            </button>
        </div>
    );

    PartnerTypeLabel.propTypes = {
        isAdmin: PropTypes.bool.isRequired
    };

    const clickOnApprove = () => {
        const request = createRequest({
            status: "approved"
        });
        setApproveRequest(request);
        setShowApprovePopup(!showApprovePopup);
    };

    const backToPolicyRequestList = async () => {
        navigate('/partnermanagement/admin/policy-requests-list');
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-auto relative font-inter`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage id='request_policy_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <Title title='requestPolicy.requestPolicy' subTitle={isAdminPath ? "viewPolicyRequest.listOfPolicyRequests" : 'requestPolicy.policies'} backLink={backUrl} />
                        {isCredentialPartner && (
                            <p id='request_policy_mandatory_mapping_msg' className="mt-3 rounded-md border border-[#F7D18D] bg-[#FFF8EA] px-3 py-2 text-sm text-[#684B00]">
                                {t('requestPolicy.mandatoryMappingBanner')}
                            </p>
                        )}
                        {isOnlineVerificationPartner && (
                            <p id='request_policy_ovp_mandatory_mapping_msg' className="mt-3 rounded-md border border-[#F7D18D] bg-[#FFF8EA] px-3 py-2 text-sm text-[#684B00]">
                                {t('requestPolicy.ovpMandatoryMappingBanner')}
                            </p>
                        )}
                        {isAdminPath && partnerType.toUpperCase() === 'ONLINE_VERIFICATION_PARTNER' && (
                            <p id='request_policy_ovp_admin_mandatory_mapping_msg' className="mt-3 rounded-md border border-[#F7D18D] bg-[#FFF8EA] px-3 py-2 text-sm text-[#684B00]">
                                {t('requestPolicy.ovpMandatoryMappingBanner')}
                            </p>
                        )}
                        {!requestPolicySuccess ?
                            <div className="w-[100%] bg-snow-white mt-[1%] rounded-lg shadow-md overflow-visible">
                                <div className="p-7 overflow-visible">
                                    <p id='request_policy_mandantory_msg' className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                    <form>
                                        <div className="flex flex-col w-full">
                                            <div className="flex flex-row justify-between space-x-4 my-[1%] max-[450px]:flex-col">
                                                {isAdminPath ?
                                                    <>
                                                        <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                            <DropdownComponent
                                                                fieldName='partnerType'
                                                                dropdownDataList={partnerTypeDropdownData}
                                                                onDropDownChangeEvent={onChangePartnerType}
                                                                fieldNameKey='requestPolicy.partnerType*'
                                                                placeHolderKey='policies.selectPartnerType'
                                                                selectedDropdownValue={partnerType}
                                                                styleSet={styles}
                                                                id='request_policy_partner_type'>
                                                            </DropdownComponent>
                                                        </div>
                                                        <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                            <DropdownWithSearchComponent
                                                                fieldName='partnerId'
                                                                dropdownDataList={partnerIdDropdownData}
                                                                onDropDownChangeEvent={onChangePartnerId}
                                                                fieldNameKey='requestPolicy.partnerId*'
                                                                placeHolderKey='requestPolicy.partnerIdHelpText'
                                                                selectedDropdownValue={partnerId}
                                                                searchKey='commons.search'
                                                                styleSet={styleForSearch}
                                                                addInfoIcon
                                                                disabled={!partnerType}
                                                                infoKey='requestPolicy.info'
                                                                id='request_policy_partner_id'>
                                                            </DropdownWithSearchComponent>
                                                        </div>
                                                    </>
                                                :
                                                    <>
                                                        <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                            <DropdownComponent
                                                                fieldName='partnerId'
                                                                dropdownDataList={partnerIdDropdownData}
                                                                onDropDownChangeEvent={onChangePartnerId}
                                                                fieldNameKey='requestPolicy.partnerId*'
                                                                placeHolderKey='requestPolicy.partnerIdHelpText'
                                                                selectedDropdownValue={partnerId}
                                                                styleSet={styles}
                                                                addInfoIcon
                                                                infoKey='requestPolicy.info'
                                                                id='request_policy_partner_id'>
                                                            </DropdownComponent>
                                                        </div>
                                                        <PartnerTypeLabel isAdmin={false} />
                                                    </>
                                                }
                                            </div>
                                            <div className="flex flex-row justify-between space-x-4 max-[450px]:flex-col">
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <label id='request_policy_policy_group_label' className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.policyGroup')}
                                                        <span className="text-crimson-red mx-1">*</span>
                                                    </label>
                                                    <button id='request_policy_policy_group' disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                        <span className={`w-full break-words ${partnerType ? 'text-dark-blue' : 'text-gray-400'} text-wrap text-start`}>{policyGroupName || t('commons.partnersHelpText')}</span>
                                                        <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                        </svg>
                                                    </button>
                                                </div>
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <DropdownWithSearchComponent
                                                        fieldName='policyName'
                                                        dropdownDataList={policiesDropdownData}
                                                        onDropDownChangeEvent={onChangePolicyName}
                                                        fieldNameKey='requestPolicy.policyName*'
                                                        placeHolderKey='requestPolicy.selectPolicyName'
                                                        selectedDropdownValue={policyName}
                                                        searchKey='commons.search'
                                                        styleSet={styleForSearch}
                                                        id='request_policies_policy_name'>
                                                    </DropdownWithSearchComponent>
                                                </div>
                                            </div>
                                            <div className="flex my-[1%]">
                                                <div className="flex flex-col w-full">
                                                    <label id='request_policy_comment_label' className={`block text-dark-blue text-sm font-semibold mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                        {t('requestPolicy.comments')}<span className="text-crimson-red mx-1">*</span>
                                                    </label>
                                                    <textarea id="request_policy_comment_box" maxLength={500} ref={textareaRef} value={partnerComment} onChange={(e) => handleCommentChange(e)} className="w-full px-2 py-2 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline
                                                overflow-x-auto whitespace-pre-wrap no-scrollbar" placeholder={t('requestPolicy.commentBoxDesc')} data-placeholder-id="request_policy_comment_box_placeholder">
                                                    </textarea>
                                                    {inputError && <span id='request_policy_invalid_comment' className="text-sm text-crimson-red font-semibold">{inputError}</span>}
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div className="border bg-medium-gray" />
                                <div className="flex flex-row px-[3%] py-5 justify-between">
                                    <button id="request_policies_form_clear_btn" onClick={() => clearForm()} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.clearForm')}</button>
                                    <div className={`flex flex-row space-x-3 w-full md:w-auto justify-end`}>
                                        <button id="request_policies_form_cancel_btn" onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                                        <button id="request_policies_form_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t(isCredentialPartner && !isAdminPath
    ? 'requestPolicy.saveAndProceed'
    : isOnlineVerificationPartner && !isAdminPath
        ? 'requestPolicy.saveAndProceed'
        : isAdminPath && partnerType.toUpperCase() === 'ONLINE_VERIFICATION_PARTNER'
            ? 'requestPolicy.saveAndProceed'
            : 'requestPolicy.submit')}</button>
                                    </div>
                                </div>
                            </div>
                            : 
                            <>
                                <Confirmation id='request_policy_confirmation' confirmationData={confirmationData} onClickCustomBtn1={clickOnApprove} onClickCustomBtn2={backToPolicyRequestList} />
                                {showApprovePopup && 
                                    <ApprovePopup closePopUp={() => setShowApprovePopup(false)} onClickConfirm={backToPolicyRequestList} title={t('requestPolicy.approvePopupTitle')} description={t('requestPolicy.approvePopupDesc')} request={approveRequest} popupData={popupData} />
                                }
                            </>
                        }
                    </div>
                </>
            )}
            <BlockerPrompt blocker={blocker} />
        </div>

    )
}

RequestPolicy.propTypes = {
    isAdminPath: PropTypes.bool
};

export default RequestPolicy;