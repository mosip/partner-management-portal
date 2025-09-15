import { useEffect, useState } from "react";
import { getUserProfile } from "../../../services/UserProfileService";
import { createDropdownData, createRequest, getPartnerManagerUrl, getPartnerTypeDescription, handleServiceErrors, isLangRTL, moveToMispPartnerServices, validateInputRegex } from "../../../utils/AppUtils";
import ErrorMessage from "../../common/ErrorMessage";
import LoadingIcon from "../../common/LoadingIcon";
import Title from "../../common/Title";
import Confirmation from "../../common/Confirmation";
import { HttpService } from "../../../services/HttpService";
import { useTranslation } from "react-i18next";
import DropdownWithSearchComponent from "../../common/fields/DropdownWithSearchComponent";
import CalendarInput from "../../common/CalendarInput";
import { useBlocker, useNavigate } from "react-router-dom";
import CopyIdPopUp from "../../common/CopyIdPopup";
import BlockerPrompt from "../../common/BlockerPrompt";

function GenerateMispLicenseKey() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [generateLicenseKeySuccess, setGenerateLicenseKeySuccess] = useState(false);
    const [confirmationData, setConfirmationData] = useState({});
    const [partnerId, setPartnerId] = useState("");
    const [policyName, setPolicyName] = useState("");
    const [partnerType, setPartnerType] = useState("");
    const [policyGroupName, setPolicyGroupName] = useState("");
    const [licenseKeyName, setLicenseKeyName] = useState("");
    const [expiryDate, setExpiryDate] = useState("");
    const [partnerIdDropdownData, setPartnerIdDropdownData] = useState([]);
    const [policiesDropdownData, setPoliciesDropdownData] = useState([]);
    const [partnerData, setPartnerData] = useState([]);
    const [policyList, setPolicyList] = useState([]);
    const [invalidLicenseKeyNameError, setInvalidLicenseKeyNameError] = useState("");
    const [isExpiryCalenderOpen, setIsExpiryCalenderOpen] = useState(false);
    const [showPopup, setShowPopup] = useState(false);
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);
    const [mispLicenseKey, setMispLicenseKey] = useState('');
    const [policyId, setPolicyId] = useState(null);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const blocker = useBlocker(
        ({ currentLocation, nextLocation }) => {
            if (isSubmitClicked || generateLicenseKeySuccess) {
                setIsSubmitClicked(false);
                return false;
            }

            return (
                (partnerId !== "" || policyName !== "" ||
                    licenseKeyName !== "") &&
                currentLocation.pathname !== nextLocation.pathname
            );
        }
    );

    useEffect(() => {
        const shouldWarnBeforeUnload = () => {
            return partnerId !== "" ||
                licenseKeyName !== "" ||
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
    }, [partnerId, licenseKeyName, policyName, isSubmitClicked]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(getPartnerManagerUrl('/partners/v3?status=approved&partnerType=MISP_Partner', process.env.NODE_ENV));
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
                    setErrorMsg(t('generateLicenseKey.errorInResponse'));
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                if (err.response?.status && err.response.status !== 401) {
                    setErrorMsg(err.toString());
                }
            }
        };
        fetchData();
    }, [t]);

    const onChangePartnerId = async (fieldName, selectedValue) => {
        setPartnerId(selectedValue);
        setPolicyName("");
        // Find the selected partner data
        const selectedPartner = partnerData.find(item => item.partnerId === selectedValue);
        console.log("Selected Partner Data: ", selectedPartner);
        if (selectedPartner) {
            setPartnerType(getPartnerTypeDescription(selectedPartner.partnerType, t));
            setPolicyGroupName(selectedPartner.policyGroupName);
            if (selectedPartner.policyGroupName) {
                await getListofPolicies(selectedPartner);
            }
        }
    };

    const getListofPolicies = async (selectedPartner) => {
        try {
            setDataLoaded(false);
            const queryParams = new URLSearchParams();
            queryParams.append('partnerId', selectedPartner.partnerId);
            queryParams.append('partnerIdSearchType', 'equals');
            queryParams.append('policyGroupName', selectedPartner.policyGroupName);
            queryParams.append('status', "approved");
            const url = `${getPartnerManagerUrl('/partner-policy-requests', process.env.NODE_ENV)}?${queryParams.toString()}`;
            const response = await HttpService.get(url);
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response.data;
                    setPolicyList(resData);
                    setPoliciesDropdownData(createDropdownData('policyName', 'policyDescription', false, resData, t));
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

    const onChangePolicyName = (fieldName, selectedValue) => {
        const selectedPolicy = policyList.find(item => item.policyName === selectedValue);
        if (selectedPolicy) {
            setPolicyName(selectedValue);
            setPolicyId(selectedPolicy.policyId);
        }
    };

    const onChangeLicenseKeyName = (value) => {
        setLicenseKeyName(value);
        validateInputRegex(value, setInvalidLicenseKeyNameError, t);
    };

    const onHandleChangeExpiryDate = (dateStr) => {
        console.log(`onHandleChangeExpiryDate ${dateStr}`);
        setExpiryDate(dateStr);
    };

    const clearForm = () => {
        setErrorCode("");
        setErrorMsg("");
        setPartnerId("");
        setPartnerType("");
        setPolicyGroupName("");
        setPolicyName("");
        setPolicyId("");
        setLicenseKeyName("");
        setExpiryDate("");
        setInvalidLicenseKeyNameError("");
        setPoliciesDropdownData([]);
    };

    const clickOnCancel = () => {
        moveToMispPartnerServices(navigate);
    };

    const isFormValid = () => {
        return partnerId && licenseKeyName.trim() && !invalidLicenseKeyNameError;
    };

    const clickOnSubmit = async () => {
        setShowPopup(false);
        setIsSubmitClicked(true);
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);

        let request = createRequest({
            partnerId: partnerId,
            policyId: policyId,
            licenseKeyName: licenseKeyName.trim(),
            expiryDate: expiryDate === "" ? new Date().toISOString().split("T")[0] : new Date(expiryDate).toISOString().split("T")[0]
        }, "mosip.pms.misp.generate.license.post", true);

        try {
            const response = await HttpService.post(getPartnerManagerUrl(`/misp-licenses`, process.env.NODE_ENV), request, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const requireData = {
                        title: "mispLicenseList.generateMispLicenseKey",
                        backUrl: "/partnermanagement/admin/misp-partner-services/misp-license-list",
                        header: "generateLicenseKey.generateLicenseKeySuccessHeader",
                        description: "generateLicenseKey.generateLicenseKeySuccessMsg",
                        subNavigation: "mispLicenseList.mispPartnerServices",
                    }
                    setConfirmationData(requireData);
                    setMispLicenseKey(responseData.response.licenseKey);
                    setShowPopup(true);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('generateLicenseKey.errorInGenerateLicenseKey'));
            }
            setDataLoaded(true);
        } catch (err) {
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            console.log("Error fetching data: ", err);
        }
        setIsSubmitClicked(false);
    };

    const closePopUp = (state) => {
        setShowPopup(state);
        setGenerateLicenseKeySuccess(true);
    };

    const style = {
        backArrowIcon: "!mt-[9%]",
    };

    const styleForSearch = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !mb-1",
        dropdownButton: "!w-full !rounded-md !text-base !text-start",
        selectionBox: "!top-10"
    };

    const calenderStyleSet = {
        datePicker: "h-10",
        outerDiv: "w-[48%]"
    };

    const copyIdPopupStyle = {
        outerDiv: "!bg-opacity-[50%]"
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            {!dataLoaded && (
                <LoadingIcon />
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage id='generate_misp_license_key_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <Title title='mispLicenseList.generateMispLicenseKey' subTitle='mispLicenseList.mispPartnerServices' backLink={'/partnermanagement/admin/misp-partner-services/misp-license-list'} style={style} />
                    </div>
                    {!generateLicenseKeySuccess ? (
                        <div className="w-[100%] bg-snow-white mt-[1%] rounded-lg shadow-md">
                            <div className="p-7">
                                <p id='generate_license_key_mandantory_msg' className="text-base mb-2 text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                <form>
                                    <div className="flex flex-col w-full">
                                        <div className="flex flex-row justify-between space-x-4 my-2 max-[450px]:flex-col">
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
                                                    infoKey='requestPolicy.mispPartnerInfo'
                                                    id='generate_license_key_partner_id'>
                                                </DropdownWithSearchComponent>
                                            </div>
                                            <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                <label id='generate_license_key_partner_type_label' className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red">*</span></label>
                                                <button id='generate_license_key_partner_type' disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                            overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                    <span className={`w-full break-words ${partnerType ? 'text-dark-blue' : 'text-gray-400'} text-wrap text-start`}>{partnerType || t('commons.partnersHelpText')}</span>
                                                    <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                    </svg>
                                                </button>
                                            </div>
                                        </div>
                                        <div className="flex flex-row justify-between space-x-4 my-2 max-[450px]:flex-col">
                                            <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                <label id='generate_license_key_policy_group_label' className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.policyGroup')}</label>
                                                <button id='generate_license_key_policy_group' disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
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
                                                    fieldNameKey='requestPolicy.policyName'
                                                    placeHolderKey='requestPolicy.selectPolicyName'
                                                    selectedDropdownValue={policyName}
                                                    searchKey='commons.search'
                                                    styleSet={styleForSearch}
                                                    addInfoIcon={true}
                                                    disabled={!partnerId}
                                                    infoKey={t('createOidcClient.policyNameToolTip')} 
                                                    id='generate_license_key_policy_name'>
                                                </DropdownWithSearchComponent>
                                            </div>
                                        </div>
                                        <div className="flex flex-row justify-between space-x-4 my-2 max-[450px]:flex-col">
                                            <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                <label id='generate_license_key_name_label' className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('generateLicenseKey.mispLicenseKeyName')}<span className="text-crimson-red mx-1">*</span></label>
                                                <input value={licenseKeyName} onChange={(e) => onChangeLicenseKeyName(e.target.value)} maxLength={128}
                                                    className="h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                                    placeholder={t('generateLicenseKey.enterLicenseKeyName')} id="generate_license_key_name"/>
                                                {invalidLicenseKeyNameError && <span id='generate_license_key_invalid_license_key_name' className="text-sm text-crimson-red font-semibold">{invalidLicenseKeyNameError}</span>}
                                            </div>
                                            <CalendarInput
                                                label={t('generateLicenseKey.expirationDuration')}
                                                showCalendar={isExpiryCalenderOpen}
                                                setShowCalender={setIsExpiryCalenderOpen}
                                                onChange={onHandleChangeExpiryDate}
                                                selectedDateStr={expiryDate}
                                                containsAsterisk
                                                id='generate_license_key_expiry_date_calender'
                                                styleSet={calenderStyleSet}
                                                isUsedAsFilter={false}
                                            />
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="border bg-medium-gray" />
                            <div className="flex flex-row max-[450px]:flex-col px-[3%] py-5 justify-between max-[450px]:space-y-2">
                                <button id="generate_license_key_clear_form" onClick={() => clearForm()} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.clearForm')}</button>
                                <div className={`flex flex-row max-[450px]:flex-col space-x-3 max-[450px]:space-x-0 max-[450px]:space-y-2 w-full md:w-auto justify-end`}>
                                    <button id="generate_license_key_cancel_btn" onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                                    <button id="generate_license_key_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t('requestPolicy.submit')}</button>
                                    {(showPopup && !errorMsg) && (
                                        <CopyIdPopUp closePopUp={closePopUp} subtitle={policyName} title={partnerId} id={mispLicenseKey}
                                            header='generateLicenseKey.mispLicenseKeyName' alertMsg='generateLicenseKey.licenseKeyAlertMsg' styleSet={copyIdPopupStyle} />
                                    )}
                                </div>
                            </div>
                        </div>
                    ) : 
                        <Confirmation id='generate_license_key_confirmation' confirmationData={confirmationData} />
                    }

                </>
            )}
            <BlockerPrompt blocker={blocker} />
        </div>
    )
}
export default GenerateMispLicenseKey;