import { useState, useEffect } from 'react';
import { HttpService } from "../../services/HttpService.js";
import {
    getPartnerTypeDescription, createRequest,
    getPartnerManagerUrl, handleServiceErrors, logout, isLangRTL,
    validateEmailRegex, validateContactNumberRegex, createDropdownData, getLanguageLabel
} from '../../utils/AppUtils.js';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService.js';
import ErrorMessage from "../common/ErrorMessage.js";
import LoadingIcon from '../common/LoadingIcon.js';
import TextInputComponent from '../common/fields/TextInputComponent.js';
import DropdownComponent from '../common/fields/DropdownComponent.js';
import Confirmation from '../common/Confirmation.js';
import FocusTrap from 'focus-trap-react';
import PropTypes from 'prop-types';
import { getAppConfig } from '../../services/ConfigService.js';

function PartnerAdminBasicInfoForm({
    missingAttributes,
    onSubmit,
    onSuccessContinue
}) {
    const { t } = useTranslation();
    const [currentUserProfile, setCurrentUserProfile] = useState(null);
    const isLoginLanguageRTL = isLangRTL(getUserProfile()?.locale);

    // Form state
    const [userName, setUserName] = useState("");
    const [email, setEmail] = useState("");
    const [organizationName, setOrganizationName] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [address, setAddress] = useState("");
    const [partnerType, setPartnerType] = useState("");
    const [langCode, setLangCode] = useState("");

    // UI state
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [showSuccess, setShowSuccess] = useState(false);
    const [confirmationData, setConfirmationData] = useState({});

    // Validation errors
    const [invalidEmailError, setInvalidEmailError] = useState("");
    const [invalidPhoneNumberError, setInvalidPhoneNumberError] = useState("");
    const [invalidOrganizationNameError, setInvalidOrganizationNameError] = useState("");
    const [invalidAddressError, setInvalidAddressError] = useState("");

    // Dropdown data
    const [languageDropdownData, setLanguageDropdownData] = useState([]);

    // Partner Type display value - always "PARTNER_ADMIN"
    const [partnerTypeDisplay, setPartnerTypeDisplay] = useState("");

    // Determine which fields are required (missing attributes)
    const isRequired = (fieldName) => {
        return missingAttributes.includes(fieldName);
    };

    // Determine which fields should be disabled (pre-filled)
    // Note: langCode (notificationLanguage) should always be selectable, never disabled
    // Note: partnerType should always be disabled as it's always "PARTNER_ADMIN"
    const isDisabled = (fieldName) => {
        // partnerType is always "PARTNER_ADMIN" and should always be disabled
        if (fieldName === 'partnerType') {
            return true;
        }
        // langCode should always be selectable
        if (fieldName === 'langCode') {
            return false;
        }

        // Disable when the field isn’t missing and there is a value for it in the profile
        const isFieldNotMissing = !missingAttributes.includes(fieldName);
        const hasFieldValue = !!currentUserProfile?.[fieldName];

        return isFieldNotMissing && hasFieldValue;
    };

    useEffect(() => {
        document.body.style.overflow = "hidden";
        return () => {
            document.body.style.overflow = "auto";
        };
    }, []);

    // Initialize user profile and form state
    useEffect(() => {
        const profile = getUserProfile();
        setCurrentUserProfile(profile);

        // Initialize form values from user profile
        if (profile) {
            setUserName(profile.userName || "");
            setEmail(profile.email || "");
            setOrganizationName(profile.orgName || "");
            setPhoneNumber(profile.phoneNumber || "");
            setAddress(profile.address || "");
        }
        // Partner Type is always "PARTNER_ADMIN" for Partner Admins
        setPartnerType("PARTNER_ADMIN");
    }, []);

    // Initialize dropdown data
    useEffect(() => {
        const fetchDropdownData = async () => {
            try {
                // Set Partner Type display text - always "PARTNER_ADMIN"
                const partnerTypeDescription = getPartnerTypeDescription("PARTNER_ADMIN", t);
                setPartnerTypeDisplay(partnerTypeDescription);

                // Fetch supported languages from app config
                const appConfig = await getAppConfig();
                const supportedLanguages = appConfig && appConfig.supportedNotificationLanguages;

                let languageCodes = [];
                if (Array.isArray(supportedLanguages)) {
                    languageCodes = supportedLanguages;
                } else if (typeof supportedLanguages === 'string') {
                    languageCodes = supportedLanguages.split(',').map(code => code.trim()).filter(code => code);
                }

                const languageData = languageCodes.map(langCode => {
                    return {
                        languageCode: langCode,
                        name: getLanguageLabel(langCode, t) || langCode
                    };
                });

                setLanguageDropdownData(createDropdownData('languageCode', 'name', false, languageData, t));
            } catch (err) {
                console.error('Error fetching dropdown data:', err);
            }
        };

        fetchDropdownData();
    }, [t]);

    // Set langCode from currentUserProfile when dropdown data is available
    useEffect(() => {
        // Early return if dropdown data is not loaded or currentUserProfile is not available
        if (languageDropdownData.length === 0 || !currentUserProfile) {
            return;
        }

        // If currentUserProfile has a langCode, validate it against available dropdown options
        if (currentUserProfile.langCode) {
            const isLangCodeAvailable = languageDropdownData.some(item => item.fieldValue === currentUserProfile.langCode);
            // Use currentUserProfile langCode if available, otherwise use first available language
            setLangCode(isLangCodeAvailable ? currentUserProfile.langCode : (languageDropdownData[0]?.fieldValue || ""));
        } else {
            // If currentUserProfile exists but no langCode, use first available language
            setLangCode(languageDropdownData[0]?.fieldValue || "");
        }
    }, [languageDropdownData, currentUserProfile]);


    const handleInputChange = (fieldName, value) => {
        switch (fieldName) {
            case 'userName':
                setUserName(value);
                break;
            case 'email':
                setEmail(value);
                validateEmailRegex(value, setInvalidEmailError, t);
                break;
            case 'organizationName':
                setOrganizationName(value);
                if (isRequired('orgName') && !value.trim()) {
                    setInvalidOrganizationNameError(t('partnerAdminBasicInfoForm.organizationNameRequired'));
                } else {
                    setInvalidOrganizationNameError("");
                }
                break;
            case 'phoneNumber':
                setPhoneNumber(value);
                validateContactNumberRegex(value, setInvalidPhoneNumberError, t);
                break;
            case 'address':
                setAddress(value);
                if (isRequired('address') && !value.trim()) {
                    setInvalidAddressError(t('partnerAdminBasicInfoForm.addressRequired'));
                } else {
                    setInvalidAddressError("");
                }
                break;
            default:
                break;
        }
    };

    const handleDropdownChange = (fieldName, value) => {
        if (fieldName === 'langCode') {
            setLangCode(value);
        }
    };

    const handleBlur = (fieldName, value) => {
        switch (fieldName) {
            case 'email':
                validateEmailRegex(value, setInvalidEmailError, t);
                break;
            case 'phoneNumber':
                validateContactNumberRegex(value, setInvalidPhoneNumberError, t);
                break;
            case 'organizationName':
                if (isRequired('orgName') && !value.trim()) {
                    setInvalidOrganizationNameError(t('partnerAdminBasicInfoForm.organizationNameRequired'));
                } else {
                    setInvalidOrganizationNameError("");
                }
                break;
            case 'address':
                if (isRequired('address') && !value.trim()) {
                    setInvalidAddressError(t('partnerAdminBasicInfoForm.addressRequired'));
                } else {
                    setInvalidAddressError("");
                }
                break;
            default:
                break;
        }
    };

    const clearForm = () => {
        // Reset fields to their original user profile values or empty if not present
        if (isRequired('userName')) setUserName(currentUserProfile?.userName || "");
        if (isRequired('email')) setEmail(currentUserProfile?.email || "");
        if (isRequired('orgName')) setOrganizationName(currentUserProfile?.orgName || "");
        if (isRequired('phoneNumber')) setPhoneNumber(currentUserProfile?.phoneNumber || "");
        if (isRequired('address')) setAddress(currentUserProfile?.address || "");
        // Partner Type is always "PARTNER_ADMIN" - always reset to this value
        setPartnerType("PARTNER_ADMIN");
        // langCode (notificationLanguage) resets to user profile value or empty
        setLangCode(currentUserProfile?.langCode || "");

        // Clear validation errors
        setInvalidEmailError("");
        setInvalidPhoneNumberError("");
        setInvalidOrganizationNameError("");
        setInvalidAddressError("");
        setErrorMsg("");
    };

    const isFormValid = () => {
        // Check required fields
        const emailValid = !isRequired('email') || (email.trim() && !invalidEmailError);
        const orgNameValid = !isRequired('orgName') || (organizationName.trim() && !invalidOrganizationNameError);
        const phoneValid = !isRequired('phoneNumber') || (phoneNumber.trim() && !invalidPhoneNumberError);
        const addressValid = !isRequired('address') || (address.trim() && !invalidAddressError);
        const userNameValid = !isRequired('userName') || userName.trim();
        const partnerTypeValid = !isRequired('partnerType') || partnerType;
        const langCodeValid = !isRequired('langCode') || langCode;

        return emailValid && orgNameValid && phoneValid && addressValid &&
            userNameValid && partnerTypeValid && langCodeValid;
    };

    const clickOnSubmit = async () => {
        setErrorCode("");
        setErrorMsg("");
        setSuccessMsg("");

        // Validate form
        if (!isFormValid()) {
            // Trigger validation for all required fields
            if (isRequired('email')) validateEmailRegex(email, setInvalidEmailError, t);
            if (isRequired('phoneNumber')) validateContactNumberRegex(phoneNumber, setInvalidPhoneNumberError, t);
            if (isRequired('orgName') && !organizationName.trim()) {
                setInvalidOrganizationNameError(t('createPartner.organizationNameRequired'));
            }
            if (isRequired('address') && !address.trim()) {
                setInvalidAddressError(t('createPartner.addressRequired'));
            }
            return;
        }

        setDataLoaded(false);

        try {
            const registerUserRequest = createRequest({
                partnerId: userName || currentUserProfile?.userName,
                organizationName: organizationName,
                address: address,
                contactNumber: phoneNumber,
                emailId: email || currentUserProfile?.email,
                partnerType: "PARTNER_ADMIN", // Always "PARTNER_ADMIN" for Partner Admins
                langCode: langCode || currentUserProfile?.langCode,
            }, "mosip.pms.create.partner.post", true);

            const registerUserResponse = await HttpService.post(
                getPartnerManagerUrl('/partners/v3', process.env.NODE_ENV),
                registerUserRequest
            );

            const registerUserResponseData = registerUserResponse.data;

            if (registerUserResponseData && registerUserResponseData.response) {
                setDataLoaded(true);
                const requiredData = {
                    title: "partnerAdminBasicInfoForm.title",
                    header: "partnerAdminBasicInfoForm.successDescription",
                    customBtnName1: "partnerAdminBasicInfoForm.continue",
                    styleSet: confirmationStyleSet
                };
                setConfirmationData(requiredData);
                setShowSuccess(true);
                setSuccessMsg(t('partnerAdminBasicInfoForm.submissionSuccess'));
                if (onSubmit) {
                    onSubmit(registerUserResponseData);
                }
            } else {
                setDataLoaded(true);
                handleServiceErrors(registerUserResponseData, setErrorCode, setErrorMsg);
            }
        } catch (err) {
            setDataLoaded(true);
            console.error('Error registering partner admin:', err);
            if (err.response?.data) {
                handleServiceErrors(err.response.data, setErrorCode, setErrorMsg);
            } else {
                setErrorMsg(t('createPartner.errorInCreatingPartner'));
            }
        }
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const handleContinue = () => {
        if (onSuccessContinue) {
            onSuccessContinue();
        }
    };

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !mb-1",
        dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
        selectionBox: "!top-10"
    };

    const loadingStyles = {
        loadingDiv: "!py-[20%]"
    };

    const textInputStyles = {
        outerDiv: "!ml-0 !mb-0",
        inputLabel: "!text-sm !mb-1",
        inputField: "!w-full min-h-10"
    };

    const customStyle = {
        outerDiv: "!flex !justify-end !relative !items-center !w-full",
        innerDiv: "!flex !justify-between !items-center !w-full !min-h-14 !px-3 !py-2"
    };

    const confirmationStyleSet = {
        header: "font-bold text-[#414141] text-base px-10",
        customBtn1: "w-36 h-10 my-4 rounded-md text-white text-sm font-semibold bg-tory-blue border border-[#1447B2]"
    };

    return (
        <div className="fixed inset-0 w-full overflow-y-auto flex items-center justify-center bg-black bg-opacity-40 z-50 font-inter pt-1 pb-1">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className={`bg-white w-full max-w-2xl h-fit rounded-xl shadow-lg mt-6 mb-6 ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                    {!dataLoaded && (
                        <LoadingIcon styleSet={loadingStyles} />
                    )}
                    {dataLoaded && (
                        <>
                            {!showSuccess ? (
                                <>

                                    <div className="px-6 pt-4 pb-2">
                                        <h3 id='partner_admin_basic_info_form_title' className="text-xl font-bold text-[#414141]">
                                            {t('partnerAdminBasicInfoForm.title')}
                                        </h3>
                                        <p id='partner_admin_basic_info_form_description' className='text-sm text-[#414141]'>
                                            {t('partnerAdminBasicInfoForm.description')}
                                        </p>
                                    </div>

                                    {errorMsg && (
                                        <ErrorMessage
                                            id='partner_admin_basic_info_form_error_msg'
                                            errorCode={errorCode}
                                            errorMessage={errorMsg}
                                            clickOnCancel={cancelErrorMsg}
                                            customStyle={customStyle}
                                        />
                                    )}

                                    <div className="py-4 px-6 text-sm text-[#414141]">
                                        <p id='partner_admin_basic_info_form_mandatory_note' className="mb-4 text-sm text-[#414141]">
                                            {(() => {
                                                const text = t('partnerAdminBasicInfoForm.mandatoryFieldsNote');
                                                const parts = text.split('{asterisk}');
                                                return (
                                                    <>
                                                        {parts[0]}
                                                        <span style={{ color: 'red' }}>*</span>
                                                        {parts[1]}
                                                    </>
                                                );
                                            })()}
                                        </p>
                                        <form>
                                            <div className="grid grid-cols-2 gap-4">
                                                {/* Left Column */}
                                                <div className="flex flex-col gap-4">
                                                    {/* Email */}
                                                    <TextInputComponent
                                                        fieldName="email"
                                                        fieldNameKey="partnerAdminBasicInfoForm.email*"
                                                        placeHolderKey="partnerAdminBasicInfoForm.enterEmail"
                                                        textBoxValue={email}
                                                        onTextChange={handleInputChange}
                                                        onBlur={(e) => handleBlur('email', e.target.value)}
                                                        styleSet={textInputStyles}
                                                        id="partner_admin_basic_info_form_email"
                                                        disableField={isDisabled('email')}
                                                        inputError={invalidEmailError}
                                                    />

                                                    {/* Organization Name */}
                                                    <TextInputComponent
                                                        fieldName="organizationName"
                                                        fieldNameKey="partnerAdminBasicInfoForm.organizationName*"
                                                        placeHolderKey="partnerAdminBasicInfoForm.enterOrganizationName"
                                                        textBoxValue={organizationName}
                                                        onTextChange={handleInputChange}
                                                        onBlur={(e) => handleBlur('organizationName', e.target.value)}
                                                        styleSet={textInputStyles}
                                                        id="partner_admin_basic_info_form_organization_name"
                                                        disableField={isDisabled('orgName')}
                                                        inputError={invalidOrganizationNameError}
                                                    />

                                                    {/* Address */}
                                                    <TextInputComponent
                                                        fieldName="address"
                                                        fieldNameKey="partnerAdminBasicInfoForm.address*"
                                                        placeHolderKey="partnerAdminBasicInfoForm.enterAddress"
                                                        textBoxValue={address}
                                                        onTextChange={handleInputChange}
                                                        onBlur={(e) => handleBlur('address', e.target.value)}
                                                        styleSet={textInputStyles}
                                                        id="partner_admin_basic_info_form_address"
                                                        disableField={isDisabled('address')}
                                                        inputError={invalidAddressError}
                                                    />
                                                </div>

                                                {/* Right Column */}
                                                <div className="flex flex-col gap-4">
                                                    {/* Phone Number */}
                                                    <TextInputComponent
                                                        fieldName="phoneNumber"
                                                        fieldNameKey="partnerAdminBasicInfoForm.phone*"
                                                        placeHolderKey="partnerAdminBasicInfoForm.enterPhone"
                                                        textBoxValue={phoneNumber}
                                                        onTextChange={handleInputChange}
                                                        onBlur={(e) => handleBlur('phoneNumber', e.target.value)}
                                                        styleSet={textInputStyles}
                                                        id="partner_admin_basic_info_form_phone_number"
                                                        maxLength={16}
                                                        disableField={isDisabled('phoneNumber')}
                                                        inputError={invalidPhoneNumberError}
                                                    />

                                                    {/* Partner Type */}
                                                    <TextInputComponent
                                                        fieldName="partnerType"
                                                        fieldNameKey="partnerAdminBasicInfoForm.partnerType*"
                                                        placeHolderKey=""
                                                        textBoxValue={partnerTypeDisplay}
                                                        onTextChange={() => { }} // No-op since field is disabled
                                                        styleSet={textInputStyles}
                                                        id="partner_admin_basic_info_form_partner_type"
                                                        disableField={true}
                                                    />

                                                    {/* Notification Language */}
                                                    <DropdownComponent
                                                        fieldName="langCode"
                                                        dropdownDataList={languageDropdownData}
                                                        onDropDownChangeEvent={handleDropdownChange}
                                                        fieldNameKey="partnerAdminBasicInfoForm.notificationLanguage*"
                                                        placeHolderKey="partnerAdminBasicInfoForm.selectLanguage"
                                                        selectedDropdownValue={langCode}
                                                        styleSet={styles}
                                                        id="partner_admin_basic_info_form_lang_code"
                                                        disabled={isDisabled('langCode')}
                                                    />
                                                </div>
                                            </div>
                                        </form>
                                    </div>

                                    <div className="border-[#E5EBFA] border-t"></div>
                                    <div className={`pt-6 py-3 px-6 flex items-center ${isLoginLanguageRTL ? 'justify-start' : 'justify-end'}`}>
                                        <button
                                            id="partner_admin_basic_info_form_clear"
                                            className={`${isLoginLanguageRTL ? "ml-5" : "mr-5"} border-[#1447B2] bg-white text-tory-blue border rounded-md w-36 h-10 text-sm font-semibold`}
                                            onClick={clearForm}
                                        >
                                            {t('createPartner.clearForm')}
                                        </button>
                                        <button
                                            id="partner_admin_basic_info_form_submit"
                                            className={`w-36 h-10 rounded-md text-white text-sm font-semibold relative ${!isFormValid() ? 'bg-gray-400 opacity-50 cursor-not-allowed' : 'bg-tory-blue'
                                                }`}
                                            onClick={clickOnSubmit}
                                            disabled={!isFormValid()}
                                        >
                                            {t('createPartner.submit')}
                                        </button>
                                    </div>
                                    <div className="pt-2 pb-4 px-6 text-right">
                                        <span className="text-sm text-[#414141]">
                                            {t('partnerAdminBasicInfoForm.logoutMessage')}
                                            <button
                                                id="partner_admin_basic_info_form_logout"
                                                className="text-tory-blue font-semibold cursor-pointer text-sm mx-1"
                                                onClick={logout}
                                            >
                                                {t('commons.logout')}
                                            </button>
                                        </span>
                                    </div>
                                </>
                            ) : (
                                <Confirmation
                                    id='partner_admin_basic_info_form_confirmation'
                                    confirmationData={confirmationData}
                                    onClickCustomBtn1={handleContinue}
                                />
                            )}
                        </>
                    )}
                </div>
            </FocusTrap>
        </div>
    );
}

PartnerAdminBasicInfoForm.propTypes = {
    missingAttributes: PropTypes.arrayOf(PropTypes.string).isRequired,
    onSubmit: PropTypes.func,
    onSuccessContinue: PropTypes.func.isRequired,
};

export default PartnerAdminBasicInfoForm;

