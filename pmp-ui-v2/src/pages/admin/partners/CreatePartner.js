import { useState, useEffect } from "react";
import { useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { isLangRTL, moveToHome, onPressEnterKey, validateInputRegex, validateUsernameRegex, validateEmailRegex, validateContactNumberRegex } from "../../../utils/AppUtils";
import { getPartnerManagerUrl, handleServiceErrors, createDropdownData, createRequest, getPartnerTypeDescription, getLanguageLabel, getPartnerDomainType } from '../../../utils/AppUtils';
import { getAppConfig } from '../../../services/ConfigService';
import { HttpService } from '../../../services/HttpService';
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import TextInputComponent from "../../common/fields/TextInputComponent";
import PolicyGroupSelector from "../../common/PolicyGroupSelector";
import DropdownComponent from "../../common/fields/DropdownComponent";
import BlockerPrompt from "../../common/BlockerPrompt";
import Title from "../../common/Title";
import Confirmation from "../../common/Confirmation";
import UploadCertificate from "../../partner/certificates/UploadCertificate";

function CreatePartner() {
    const navigate = useNavigate();
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [partnerTypeValue, setPartnerTypeValue] = useState("Manual_Adjudication");
    const [address, setAddress] = useState("");
    const [organizationName, setOrganizationName] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [notificationLanguage, setNotificationLanguage] = useState("");
    const [partnerTypeDropdownData, setPartnerTypeDropdownData] = useState([]);
    const [selectedPolicyGroup, setSelectedPolicyGroup] = useState(null);
    const [languageDropdownData, setLanguageDropdownData] = useState([]);
    const [createPartnerSuccess, setCreatePartnerSuccess] = useState(false);
    const [confirmationData, setConfirmationData] = useState({});
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);
    const [invalidAddressError, setInvalidAddressError] = useState("");
    const [invalidOrganizationNameError, setInvalidOrganizationNameError] = useState("");
    const [invalidPhoneNumberError, setInvalidPhoneNumberError] = useState("");
    const [invalidEmailError, setInvalidEmailError] = useState("");
    const [invalidUsernameError, setInvalidUsernameError] = useState("");
    const [partnerIdMaxLength, setPartnerIdMaxLength] = useState(36);
    const [phoneNumberMaxLength, setPhoneNumberMaxLength] = useState(16);

    const [uploadCertificateData, setUploadCertificateData] = useState({});
    const [showPopup, setShowPopup] = useState(false);
    const [uploadCertificateRequest, setUploadCertificateRequest] = useState({});

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const blocker = useBlocker(({ currentLocation, nextLocation }) => {
        if (isSubmitClicked || createPartnerSuccess) {
            setIsSubmitClicked(false);
            setCreatePartnerSuccess(false);
            return false;
        }

        return (
            (address !== "" || organizationName !== "" || phoneNumber !== "" ||
                email !== "" || username !== "" || selectedPolicyGroup !== null || notificationLanguage !== "") &&
            currentLocation.pathname !== nextLocation.pathname
        );
    });

    useEffect(() => {
        const shouldWarnBeforeUnload = () => {
            return address !== "" || organizationName !== "" || phoneNumber !== "" ||
                email !== "" || username !== "" || selectedPolicyGroup !== null || notificationLanguage !== "";
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
    }, [address, organizationName, phoneNumber, email, username, selectedPolicyGroup, notificationLanguage, isSubmitClicked]);

    const onChangePartnerType = async (fieldName, selectedValue) => {
        setPartnerTypeValue(selectedValue);
    };

    const onChangePolicyGroup = (policyGroup) => {
        setSelectedPolicyGroup(policyGroup);
    };

    const onChangeNotificationLanguage = async (fieldName, selectedValue) => {
        if (fieldName === 'langCode') {
            setNotificationLanguage(selectedValue);
        }
    };

    const handleTextChange = (fieldName, value) => {
        switch (fieldName) {
            case 'address':
                setAddress(value);
                validateInputRegex(value, setInvalidAddressError, t);
                break;
            case 'organizationName':
                setOrganizationName(value);
                validateInputRegex(value, setInvalidOrganizationNameError, t);
                break;
            case 'contactNumber':
                setPhoneNumber(value);
                // Clear error message when user starts typing again
                if (invalidPhoneNumberError) {
                    setInvalidPhoneNumberError("");
                }
                break;
            case 'emailId':
                setEmail(value);
                // Clear error message when user starts typing again
                if (invalidEmailError) {
                    setInvalidEmailError("");
                }
                break;
            case 'username':
                setUsername(value);
                validateUsernameRegex(value, setInvalidUsernameError, t);
                break;
            default:
                break;
        }
    };

    const handleEmailBlur = () => {
        validateEmailRegex(email, setInvalidEmailError, t);
    };

    const handleContactNumberBlur = () => {
        validateContactNumberRegex(phoneNumber, setInvalidPhoneNumberError, t);
    };

    const clearForm = () => {
        setAddress("");
        setOrganizationName("");
        setPhoneNumber("");
        setEmail("");
        setUsername("");
        setNotificationLanguage("");
        setErrorCode("");
        setErrorMsg("");
        setInvalidAddressError("");
        setInvalidOrganizationNameError("");
        setInvalidPhoneNumberError("");
        setInvalidEmailError("");
        setInvalidUsernameError("");
        setSelectedPolicyGroup(null);
        setPartnerTypeValue("Manual_Adjudication");
    };

    const clickOnUpload = () => {
        // Use the selected partner type to determine domain
        const partnerTypeForDomain = getPartnerTypeForDomain(partnerTypeValue);
        const request = {
            partnerId: username.trim(),
            partnerDomain: getPartnerDomainType(partnerTypeForDomain),
        };
        setUploadCertificateRequest(request);
        setShowPopup(true);
    };

    const closePopup = (state, btnName) => {
        if (state && btnName === 'cancel') {
            setShowPopup(false);
        } else if (state && btnName === 'close') {
            navigate('/partnermanagement/admin/partners/partners-list');
        }
    };

    const clickOnCancel = () => {
        moveToPartnersList();
    };

    const moveToPartnersList = () => {
        navigate('/partnermanagement/admin/partners/partners-list');
    };

    const getPartnerTypeForDomain = (partnerType) => {
        if (partnerType === "MISP_PARTNER") {
            return "MISP_Partner";
        } else if (partnerType === "ABIS_PARTNER") {
            return "ABIS_Partner";
        } else if (partnerType === "Manual_Adjudication") {
            return "Manual_Adjudication";
        } else if (partnerType === "ONLINE_VERIFICATION_PARTNER") {
            return "ONLINE_VERIFICATION_PARTNER";
        }
    };

    const getUploadCertificateButtonName = (partnerType) => {
        if (partnerType === "MISP_PARTNER") {
            return "createPartner.uploadMispPartnerCertificate";
        } else if (partnerType === "ABIS_PARTNER") {
            return "createPartner.uploadAbisPartnerCertificate";
        } else if (partnerType === "Manual_Adjudication") {
            return "createPartner.uploadManualAdjudicationPartnerCertificate";
        } else if (partnerType === "ONLINE_VERIFICATION_PARTNER") {
            return "createPartner.uploadOnlineVerificationPartnerCertificate";
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            setDataLoaded(false);
            try {
                // Initialize partner type dropdown data
                const partnerTypeData = [
                    {
                        partnerType: "Manual_Adjudication",
                        description: getPartnerTypeDescription("Manual_Adjudication", t)
                    },
                    {
                        partnerType: "MISP_PARTNER",
                        description: getPartnerTypeDescription("MISP_PARTNER", t)
                    },
                    {
                        partnerType: "ABIS_PARTNER",
                        description: getPartnerTypeDescription("ABIS_PARTNER", t)
                    },
                    {
                        partnerType: "ONLINE_VERIFICATION_PARTNER",
                        description: getPartnerTypeDescription("ONLINE_VERIFICATION_PARTNER", t)
                    }
                ];
                setPartnerTypeDropdownData(createDropdownData('partnerType', 'description', false, partnerTypeData, t));

                // Fetch supported languages from app config
                const appConfig = await getAppConfig();
                const supportedLanguages = appConfig && appConfig.supportedNotificationLanguages;

                let languageCodes = [];
                if (Array.isArray(supportedLanguages)) {
                    languageCodes = supportedLanguages;
                } else if (typeof supportedLanguages === 'string') {
                    languageCodes = supportedLanguages.split(',').map(code => code.trim()).filter(code => code);
                }

                const languageData = languageCodes.map(langCode => ({
                    languageCode: langCode,
                    name: getLanguageLabel(langCode, t)
                }));

                setLanguageDropdownData(createDropdownData('languageCode', 'name', false, languageData, t));

                // Set configurable limits from app config
                const configData = await getAppConfig();
                if (configData && configData.partnerIdMaxLength) {
                    setPartnerIdMaxLength(Number.parseInt(configData.partnerIdMaxLength, 10));
                }
                if (configData && configData.phoneNumberMaxLength) {
                    setPhoneNumberMaxLength(Number.parseInt(configData.phoneNumberMaxLength, 10));
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
    }, [t]);

    const clickOnSubmit = async () => {
        setIsSubmitClicked(true);
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);

        let request = createRequest({
            partnerId: username.trim(),
            partnerType: partnerTypeValue,
            policyGroup: selectedPolicyGroup ? selectedPolicyGroup.name : "",
            organizationName: organizationName.trim(),
            address: address.trim(),
            contactNumber: phoneNumber.trim(),
            emailId: email.trim(),
            langCode: notificationLanguage
        }, "mosip.pms.create.partner.post", true);

        try {
            const response = await HttpService.post(getPartnerManagerUrl('/partners/v3', process.env.NODE_ENV), request);
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response;
                    // Determine button text and certificate data based on partner type
                    const isMispPartner = partnerTypeValue === "MISP_PARTNER";
                    const isAbisPartner = partnerTypeValue === "ABIS_PARTNER";
                    const isManualAdjudicationPartner = partnerTypeValue === "Manual_Adjudication";
                    const isOnlineVerificationPartner = partnerTypeValue === "ONLINE_VERIFICATION_PARTNER";

                    const getSuccessHeader = () => {
                        if (isManualAdjudicationPartner) return "createPartner.manualAdjudicationPartnerSuccessHeader";
                        if (isMispPartner) return "createPartner.mispPartnerSuccessHeader";
                        if (isAbisPartner) return "createPartner.abisPartnerSuccessHeader";
                        if (isOnlineVerificationPartner) return "createPartner.onlineVerificationPartnerSuccessHeader";
                        return "createPartner.abisPartnerSuccessHeader";
                    };

                    const requiredData = {
                        title: "createPartner.createPartner",
                        backUrl: "/partnermanagement/admin/partners/partners-list",
                        header: getSuccessHeader(),
                        subNavigation: "createPartner.listOfPartners",
                        customBtnName1: getUploadCertificateButtonName(partnerTypeValue),
                        customBtnName2: "commons.home",
                        customBtn2Id: "confirmation_home_btn"
                    };
                    setConfirmationData(requiredData);

                    // Set upload certificate data based on partner type
                    const partnerTypeForDomain = getPartnerTypeForDomain(partnerTypeValue);
                    const requiredDataForCertUpload = {
                        partnerType: partnerTypeForDomain,
                        uploadHeader: 'uploadCertificate.uploadPartnerCertificate',
                        isUploadPartnerCertificate: true,
                        isMispPartnerCertificate: isMispPartner,
                        isAbisPartnerCertificate: isAbisPartner,
                        isManualAdjudicationPartnerCertificate: isManualAdjudicationPartner,
                        isOnlineVerificationPartnerCertificate: isOnlineVerificationPartner
                    }
                    setUploadCertificateData(requiredDataForCertUpload);

                    setCreatePartnerSuccess(true);
                    console.log(`Response data: ${resData.length}`);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('createPartner.errorInCreatingPartner'));
            }
        } catch (err) {
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            console.log("Error creating partner: ", err);
        }
        setDataLoaded(true);
        setIsSubmitClicked(false);
    };

    const isPolicyGroupRequired = () => {
        return partnerTypeValue === "ABIS_PARTNER" ||
            partnerTypeValue === "Manual_Adjudication" ||
            partnerTypeValue === "ONLINE_VERIFICATION_PARTNER";
    };

    const isFormValid = () => {
        const isPolicyGroupValid = !isPolicyGroupRequired() || selectedPolicyGroup !== null;

        return address.trim() && organizationName.trim() && phoneNumber.trim() &&
            email.trim() && username.trim() && notificationLanguage &&
            isPolicyGroupValid &&
            !invalidAddressError && !invalidOrganizationNameError &&
            !invalidPhoneNumberError && !invalidEmailError && !invalidUsernameError;
    };

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !mb-1",
        dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
        selectionBox: "!top-10"
    };

    const textInputStyles = {
        outerDiv: "!ml-0 !mb-0",
        inputLabel: "!text-sm !mb-1",
        inputField: "!w-full min-h-10"
    };

    return (
        <div className={`mt-2 w-full ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll relative font-inter`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage id='create_partner_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5 w-full">
                        <div className="w-fit">
                            <Title title='createPartner.createPartner' subTitle='createPartner.listOfPartners' backLink='/partnermanagement/admin/partners/partners-list' />
                        </div>
                        {!createPartnerSuccess ?
                            <div className="w-[100%] bg-snow-white mt-[1%] rounded-lg shadow-md">
                                <div className="p-7">
                                    <p id='create_partner_mandatory_msg' className="text-base text-[#3D4468]">{t('createPartner.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('createPartner.mandatoryFieldsMsg2')}</p>
                                    <form>
                                        <div className="flex flex-col w-full">
                                            <div className="flex flex-row justify-between space-x-4 my-[1%] max-[450px]:flex-col">
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <DropdownComponent
                                                        fieldName='partnerType'
                                                        dropdownDataList={partnerTypeDropdownData}
                                                        onDropDownChangeEvent={onChangePartnerType}
                                                        fieldNameKey='createPartner.partnerType*'
                                                        placeHolderKey='createPartner.selectPartnerType'
                                                        selectedDropdownValue={partnerTypeValue}
                                                        searchKey='commons.search'
                                                        styleSet={styles}
                                                        id='create_partner_partner_type'>
                                                    </DropdownComponent>
                                                </div>
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <PolicyGroupSelector
                                                        onPolicyGroupSelect={onChangePolicyGroup}
                                                        selectedPolicyGroup={selectedPolicyGroup}
                                                        placeholderKey='createPartner.selectPolicyGroup'
                                                        isPlaceHolderPresent={true}
                                                        containsAsterisk={isPolicyGroupRequired()}
                                                    />
                                                </div>
                                            </div>
                                            <div className="flex flex-row justify-between space-x-4 my-[1%] max-[450px]:flex-col">
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <TextInputComponent
                                                        fieldName='address'
                                                        fieldNameKey='createPartner.address*'
                                                        placeHolderKey='createPartner.enterAddress'
                                                        textBoxValue={address}
                                                        onTextChange={handleTextChange}
                                                        styleSet={textInputStyles}
                                                        id='create_partner_address'
                                                        maxLength={2000}
                                                        inputError={invalidAddressError}
                                                    />
                                                </div>
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <TextInputComponent
                                                        fieldName='organizationName'
                                                        fieldNameKey='createPartner.organizationName*'
                                                        placeHolderKey='createPartner.enterOrganizationName'
                                                        textBoxValue={organizationName}
                                                        onTextChange={handleTextChange}
                                                        styleSet={textInputStyles}
                                                        id='create_partner_organization_name'
                                                        maxLength={128}
                                                        inputError={invalidOrganizationNameError}
                                                        addInfoIcon={true}
                                                        infoKey='createPartner.organizationNameInfo'
                                                    />
                                                </div>
                                            </div>
                                            <div className="flex flex-row justify-between space-x-4 my-[1%] max-[450px]:flex-col">
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <TextInputComponent
                                                        fieldName='contactNumber'
                                                        fieldNameKey='createPartner.contactNumber*'
                                                        placeHolderKey='createPartner.enterContactNumber'
                                                        textBoxValue={phoneNumber}
                                                        onTextChange={handleTextChange}
                                                        onBlur={handleContactNumberBlur}
                                                        styleSet={textInputStyles}
                                                        id='create_partner_contact_number'
                                                        maxLength={phoneNumberMaxLength}
                                                        inputError={invalidPhoneNumberError}
                                                    />
                                                </div>
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <TextInputComponent
                                                        fieldName='emailId'
                                                        fieldNameKey='createPartner.emailId*'
                                                        placeHolderKey='createPartner.enterEmailId'
                                                        textBoxValue={email}
                                                        onTextChange={handleTextChange}
                                                        onBlur={handleEmailBlur}
                                                        styleSet={textInputStyles}
                                                        id='create_partner_email_id'
                                                        maxLength={254}
                                                        inputError={invalidEmailError}
                                                    />
                                                </div>
                                            </div>
                                            <div className="flex flex-row justify-between space-x-4 my-[1%] max-[450px]:flex-col">
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <TextInputComponent
                                                        fieldName='username'
                                                        fieldNameKey='createPartner.username*'
                                                        placeHolderKey='createPartner.enterUsername'
                                                        textBoxValue={username}
                                                        onTextChange={handleTextChange}
                                                        styleSet={textInputStyles}
                                                        id='create_partner_partner_id'
                                                        maxLength={partnerIdMaxLength}
                                                        inputError={invalidUsernameError}
                                                    />
                                                </div>
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <DropdownComponent
                                                        fieldName='langCode'
                                                        dropdownDataList={languageDropdownData}
                                                        onDropDownChangeEvent={onChangeNotificationLanguage}
                                                        fieldNameKey='createPartner.notificationLanguage*'
                                                        placeHolderKey='createPartner.selectLanguage'
                                                        selectedDropdownValue={notificationLanguage}
                                                        searchKey='commons.search'
                                                        styleSet={styles}
                                                        id='create_partner_lang_code'>
                                                    </DropdownComponent>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div className="border bg-medium-gray" />
                                <div className="flex flex-row px-[3%] py-5 justify-between">
                                    <button id="create_partner_form_clear_btn" onClick={() => clearForm()} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('createPartner.clearForm')}</button>
                                    <div className={`flex flex-row space-x-3 w-full md:w-auto justify-end`}>
                                        <button id="create_partner_form_cancel_btn" onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('createPartner.cancel')}</button>
                                        <button id="create_partner_form_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()}
                                            className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 min-w-fit px-2 md:w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}
                                            tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => clickOnSubmit())}
                                        >
                                            {t('createPartner.submit')}
                                        </button>
                                    </div>
                                </div>
                            </div>
                            : <>
                                <Confirmation id='create_partner_confirmation' confirmationData={confirmationData} onClickCustomBtn1={clickOnUpload} onClickCustomBtn2={() => moveToHome(navigate)} />
                                {
                                    showPopup && (
                                        <UploadCertificate header={t('createPartner.uploadCertificate')} closePopup={closePopup} popupData={uploadCertificateData} request={uploadCertificateRequest} />
                                    )
                                }
                            </>
                        }
                    </div>
                </>
            )}
            <BlockerPrompt blocker={blocker} />
        </div>
    );
}

export default CreatePartner;
