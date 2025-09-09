import { useState, useEffect } from "react";
import { useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { isLangRTL, onPressEnterKey, validateInputRegex } from "../../../utils/AppUtils";
import { getPartnerManagerUrl, handleServiceErrors, createDropdownData, createRequest, getPolicyGroupList, getPartnerTypeDescription, getLanguageLabel, getPartnerDomainType } from '../../../utils/AppUtils';
import { getAppConfig } from '../../../services/ConfigService';
import { HttpService } from '../../../services/HttpService';
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import TextInputComponent from "../../common/fields/TextInputComponent";
import DropdownWithSearchComponent from "../../common/fields/DropdownWithSearchComponent";
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
    const [partnerType, setPartnerType] = useState(getPartnerTypeDescription("MISP_PARTNER", t));
    const [partnerTypeValue, setPartnerTypeValue] = useState("MISP_PARTNER");
    const [policyGroup, setPolicyGroup] = useState("");
    const [address, setAddress] = useState("");
    const [organizationName, setOrganizationName] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [notificationLanguage, setNotificationLanguage] = useState("");
    const [policyGroupDropdownData, setPolicyGroupDropdownData] = useState([]);
    const [languageDropdownData, setLanguageDropdownData] = useState([]);
    const [createPartnerSuccess, setCreatePartnerSuccess] = useState(false);
    const [confirmationData, setConfirmationData] = useState({});
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);
    const [invalidAddressError, setInvalidAddressError] = useState("");
    const [invalidOrganizationNameError, setInvalidOrganizationNameError] = useState("");
    const [invalidPhoneNumberError, setInvalidPhoneNumberError] = useState("");
    const [invalidEmailError, setInvalidEmailError] = useState("");
    const [invalidUsernameError, setInvalidUsernameError] = useState("");
    const [policyGroupsLoaded, setPolicyGroupsLoaded] = useState(false);
    const [policyGroupLoading, setPolicyGroupLoading] = useState(false);
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
                email !== "" || username !== "" || policyGroup !== "" || notificationLanguage !== "") &&
            currentLocation.pathname !== nextLocation.pathname
        );
    });

    useEffect(() => {
        const shouldWarnBeforeUnload = () => {
            return address !== "" || organizationName !== "" || phoneNumber !== "" || 
                email !== "" || username !== "" || policyGroup !== "" || notificationLanguage !== "";
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
    }, [address, organizationName, phoneNumber, email, username, policyGroup, notificationLanguage, isSubmitClicked]);

    const onChangePolicyGroup = async (fieldName, selectedValue) => {
        setPolicyGroup(selectedValue);
    };

    const onPolicyGroupDropdownClick = async () => {
        if (!policyGroupsLoaded) {
            setPolicyGroupLoading(true);
            try {
                const policyGroupResponse = await getPolicyGroupList(HttpService, setPolicyGroupDropdownData, setErrorCode, setErrorMsg, t);
                if (policyGroupResponse && policyGroupResponse.data && policyGroupResponse.data.response) {
                    setPolicyGroupDropdownData(createDropdownData('name', 'description', false, policyGroupResponse.data.response, t));
                }
                setPolicyGroupsLoaded(true);
            } catch (err) {
                console.error('Error fetching policy groups:', err);
                if (err.response?.status && err.response.status !== 401) {
                    setErrorMsg(err.toString());
                }
            }
            setPolicyGroupLoading(false);
        }
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
                validateInputRegex(value, setInvalidPhoneNumberError, t);
                break;
            case 'emailId':
                setEmail(value);
                validateInputRegex(value, setInvalidEmailError, t);
                break;
            case 'username':
                setUsername(value);
                validateInputRegex(value, setInvalidUsernameError, t);
                break;
            default:
                break;
        }
    };

    const clearForm = () => {
        setAddress("");
        setOrganizationName("");
        setPhoneNumber("");
        setEmail("");
        setUsername("");
        setPolicyGroup("");
        setNotificationLanguage("");
        setErrorCode("");
        setErrorMsg("");
        setInvalidAddressError("");
        setInvalidOrganizationNameError("");
        setInvalidPhoneNumberError("");
        setInvalidEmailError("");
        setInvalidUsernameError("");
        setPolicyGroupsLoaded(false);
        setPolicyGroupLoading(false);
        setPolicyGroupDropdownData([]);
        setLanguageDropdownData([]);
        setPartnerType(getPartnerTypeDescription("MISP_PARTNER", t));
        setPartnerTypeValue("MISP_PARTNER");
    };

    const clickOnUpload = () => {
        const request = {
            partnerId: username.trim(),
            partnerDomain: getPartnerDomainType("MISP_Partner"),
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
        moveToPartnersList(navigate);
    };

    const moveToPartnersList = () => {
        navigate('/partnermanagement/admin/partners/partners-list');
    };

    useEffect(() => {
        const fetchData = async () => {
            setDataLoaded(false);
            try {
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
            policyGroup: policyGroup,
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
                    const requiredData = {
                        title: "createPartner.createPartner",
                        backUrl: "/partnermanagement/admin/partners/partners-list",
                        header: "createPartner.mispPartnerSuccessHeader",
                        description: "createPartner.mispPartnerSuccessMsg",
                        subNavigation: "createPartner.listOfPartners",
                        customBtnName: "createPartner.uploadMispPartnerCertificate",
                        showHome: true,
                    };
                    setConfirmationData(requiredData);
                    
                    // Set upload certificate data
                    const requiredDataForCertUpload = {
                        partnerType: "MISP_Partner",
                        uploadHeader: 'uploadMispPartnerCertificate.uploadMispPartnerCertificate',
                        successMessage: 'uploadMispPartnerCertificate.successMsg',
                        isUploadPartnerCertificate: true,
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

    const isFormValid = () => {
        return address.trim() && organizationName.trim() && phoneNumber.trim() && 
               email.trim() && username.trim() && notificationLanguage && 
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
                                                    <label id='create_partner_partner_type_label' className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                        {t('createPartner.partnerType')}
                                                    </label>
                                                    <input
                                                        id='create_partner_partner_type'
                                                        type="text"
                                                        value={partnerType}
                                                        disabled={true}
                                                        className="w-full min-h-10 p-3 border border-[#707070] rounded-md text-base text-dark-blue bg-gray-100 leading-tight focus:outline-none focus:shadow-outline"
                                                    />
                                                </div>
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <DropdownWithSearchComponent
                                                        fieldName='policyGroup'
                                                        dropdownDataList={policyGroupDropdownData}
                                                        onDropDownChangeEvent={onChangePolicyGroup}
                                                        fieldNameKey='createPartner.policyGroup'
                                                        placeHolderKey='createPartner.selectPolicyGroup'
                                                        selectedDropdownValue={policyGroup}
                                                        searchKey='commons.search'
                                                        styleSet={styles}
                                                        id='create_partner_policy_group'
                                                        onDropdownClick={onPolicyGroupDropdownClick}
                                                        loading={policyGroupLoading}>
                                                    </DropdownWithSearchComponent>
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
                                                        maxLength={128}
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
                                            <div className="flex flex-row justify-between space-x-4 my-[1%] max-[450px]:flex-col">
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
                                                    />
                                                </div>
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <TextInputComponent
                                                        fieldName='address'
                                                        fieldNameKey='createPartner.address*'
                                                        placeHolderKey='createPartner.enterAddress'
                                                        textBoxValue={address}
                                                        onTextChange={handleTextChange}
                                                        styleSet={textInputStyles}
                                                        id='create_partner_address'
                                                        maxLength={256}
                                                        inputError={invalidAddressError}
                                                    />
                                                </div>
                                            </div>
                                            <div className="flex flex-row justify-between space-x-4 my-[1%] max-[450px]:flex-col">
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <TextInputComponent
                                                        fieldName='emailId'
                                                        fieldNameKey='createPartner.emailId*'
                                                        placeHolderKey='createPartner.enterEmailId'
                                                        textBoxValue={email}
                                                        onTextChange={handleTextChange}
                                                        styleSet={textInputStyles}
                                                        id='create_partner_email_id'
                                                        maxLength={128}
                                                        inputError={invalidEmailError}
                                                    />
                                                </div>
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <TextInputComponent
                                                        fieldName='contactNumber'
                                                        fieldNameKey='createPartner.contactNumber*'
                                                        placeHolderKey='createPartner.enterContactNumber'
                                                        textBoxValue={phoneNumber}
                                                        onTextChange={handleTextChange}
                                                        styleSet={textInputStyles}
                                                        id='create_partner_contact_number'
                                                        maxLength={20}
                                                        inputError={invalidPhoneNumberError}
                                                    />
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
                                <Confirmation id='create_partner_confirmation' confirmationData={confirmationData} onClickFunction={clickOnUpload} />
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
