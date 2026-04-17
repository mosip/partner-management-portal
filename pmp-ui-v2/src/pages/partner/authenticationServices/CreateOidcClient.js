import { useState, useEffect, useCallback } from "react";
import { useNavigate, useBlocker } from "react-router-dom";
import { Trans, useTranslation } from "react-i18next";
import DropdownComponent from '../../common/fields/DropdownComponent';
import { getUserProfile } from '../../../services/UserProfileService';
import {
  getPartnerManagerUrl, handleServiceErrors, getPartnerTypeDescription,
  moveToOidcClientsList, getGrantTypes, getApprovedAuthPartners,
  isLangRTL, createDropdownData, validateUrl, getPartnerPolicyRequests,
  onPressEnterKey, trimAndReplace, validateInputRegex,
  createRequest,
  isOidcClientAdditionalInfoRequired,
  buildClientNameLangMap,
  createOidcClientEntry, findAvailableOidcLanguage, validateOidcEntryText,
  getOidcPlaceholderForLanguage, getOidcPlaceholderIdForLanguage, getAvailableOidcLanguages,
  initializeUserInfoResponseTypeDropdown, initializePurposeTypeDropdown, initializeLanguageDropdown
} from '../../../utils/AppUtils';
import { HttpService } from '../../../services/HttpService';
import DropdownWithSearchComponent from "../../common/fields/DropdownWithSearchComponent";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import BlockerPrompt from "../../common/BlockerPrompt";
import Information from "../../common/fields/Information";
import Title from "../../common/Title";
import Confirmation from "../../common/Confirmation";
import JSON5 from 'json5';
import expandToggleIcon from '../../../svg/expand_toggle_icon.svg';
import TextInputComponentWithDeleteButton from '../../common/fields/TextInputComponentWithDeleteButton';
import OidcClientAdditionalInfoSection from '../../common/OidcClientAdditionalInfoSection';

function CreateOidcClient() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

  // Loading and error states
  const [dataLoaded, setDataLoaded] = useState(true);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [createOidcClientSuccess, setCreateOidcClientSuccess] = useState(false);
  const [confirmationData, setConfirmationData] = useState({});
  const [isSubmitClicked, setIsSubmitClicked] = useState(false);

  // Partner and policy states
  const [partnerId, setPartnerId] = useState("");
  const [policyId, setPolicyId] = useState("");
  const [policyName, setPolicyName] = useState("");
  const [partnerType, setPartnerType] = useState("");
  const [policyGroupName, setPolicyGroupName] = useState("");
  const [partnerData, setPartnerData] = useState([]);
  const [activePoliciesData, setActivePoliciesData] = useState([]);
  const [policyRequestsData, setPolicyRequestsData] = useState([]);
  const [partnerIdDropdownData, setPartnerIdDropdownData] = useState([]);
  const [policiesDropdownData, setPoliciesDropdownData] = useState([]);

  // Client configuration states
  const [publicKey, setPublicKey] = useState("");
  const [publicKeyInJson, setPublicKeyInJson] = useState(null);
  const [logoUrl, setLogoUrl] = useState("");
  const [redirectUrls, setRedirectUrls] = useState(['']);
  const [grantTypes, setGrantTypes] = useState("authorization_code");
  const [grantTypesList, setGrantTypesList] = useState(['']);
  const [grantTypesDropdownData, setGrantTypesDropdownData] = useState([]);
  const [clientAuthMethods, setClientAuthMethods] = useState(['']);

  // Validation error states
  const [jsonError, setJsonError] = useState("");
  const [invalidLogoUrl, setInvalidLogoUrl] = useState("");
  const [invalidRedirectUrl, setInvalidRedirectUrl] = useState("");

  // Client name states
  const [clientName, setClientName] = useState("");
  const [clientNameError, setClientNameError] = useState("");

  // Multi-language client name lang map states
  const [clientNameLangMapEntries, setClientNameLangMapEntries] = useState([]);
  const [clientNameLangMapErrors, setClientNameLangMapErrors] = useState({});

  // Additional Information states
  const [isMandatoryInfoExpanded, setIsMandatoryInfoExpanded] = useState(true);
  const [isAdditionalInfoExpanded, setIsAdditionalInfoExpanded] = useState(false);
  const [forgotPasswordBanner, setForgotPasswordBanner] = useState(true);
  const [signUpBanner, setSignUpBanner] = useState(true);
  const [consentExpiry, setConsentExpiry] = useState("10");
  const [consentExpiryError, setConsentExpiryError] = useState("");
  const [userInfoResponseType, setUserInfoResponseType] = useState("");
  const [userInfoResponseTypeDropdownData, setUserInfoResponseTypeDropdownData] = useState([]);
  const [purposeType, setPurposeType] = useState("");
  const [purposeTypeDropdownData, setPurposeTypeDropdownData] = useState([]);
  const [purposeTitleEntries, setPurposeTitleEntries] = useState([]);
  const [purposeSubtitleEntries, setPurposeSubtitleEntries] = useState([]);
  const [languageDropdownData, setLanguageDropdownData] = useState([]);
  const [purposeTitleErrors, setPurposeTitleErrors] = useState({});
  const [purposeSubtitleErrors, setPurposeSubtitleErrors] = useState({});
  const [purposeTitleDefaultError, setPurposeTitleDefaultError] = useState("");
  const [purposeSubtitleDefaultError, setPurposeSubtitleDefaultError] = useState("");
  const [additionalConfigRequired, setAdditionalConfigRequired] = useState(false);
  const [showCompatibilityMsg, setShowCompatibilityMsg] = useState(false);

  const blocker = useBlocker(
    ({ currentLocation, nextLocation }) => {
      if (isSubmitClicked || createOidcClientSuccess) {
        setIsSubmitClicked(false);
        return false;
      }

      const hasFormData = partnerId !== "" ||
        clientName !== "" ||
        publicKey !== "" ||
        logoUrl !== "" ||
        policyName !== "" ||
        redirectUrls.some(url => url !== "");

      return hasFormData && currentLocation.pathname !== nextLocation.pathname;
    }
  );

  useEffect(() => {
    const shouldWarnBeforeUnload = () => {
      return partnerId !== "" ||
        clientName !== "" ||
        publicKey !== "" ||
        logoUrl !== "" ||
        policyName !== "" ||
        redirectUrls.some(url => url !== "");
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
  }, [partnerId, clientName, publicKey, logoUrl, policyId, policyName, redirectUrls, isSubmitClicked]);

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  const createGrantTypesDropdownData = useCallback((dataList) => {
    let dataArr = [];
    dataList.forEach(item => {
      let alreadyAdded = false;
      dataArr.forEach(item1 => {
        if (item1.fieldValue === item) {
          alreadyAdded = true;
        }
      });
      if (!alreadyAdded) {
        dataArr.push({
          fieldCode: getGrantTypes(item, t),
          fieldValue: item
        });
      }
    });
    return dataArr;
  }, [t]);

  const defaultGrantTypesList = useCallback((dataList) => {
    const list = [];
    dataList.forEach(item => {
      if (item === grantTypes) {
        list.push(item);
      }
    })
    setGrantTypesList(list);
  }, [grantTypes]);

  useEffect(() => {
    const checkAdditionalConfigSupport = async () => {
      const isRequired = await isOidcClientAdditionalInfoRequired();
      if (isRequired) {
        setAdditionalConfigRequired(isRequired);
      } else {
        setShowCompatibilityMsg(true);
        setForgotPasswordBanner(false);
        setSignUpBanner(false);
        setConsentExpiry("");
      }
    };
    checkAdditionalConfigSupport();
  }, []);

  // Initialize User Info Response Type dropdown
  useEffect(() => {
    setUserInfoResponseTypeDropdownData(initializeUserInfoResponseTypeDropdown(t));
  }, [t]);

  // Initialize Purpose Type dropdown
  useEffect(() => {
    setPurposeTypeDropdownData(initializePurposeTypeDropdown(t));
  }, [t]);

  // Initialize Language dropdown
  useEffect(() => {
    const fetchLanguages = async () => {
      const data = await initializeLanguageDropdown(t);
      setLanguageDropdownData(data);
    };
    fetchLanguages();
  }, [t]);

  useEffect(() => {
    const config = sessionStorage.getItem('appConfig');
    if (config) {
      try {
        const configData = JSON.parse(config);
        const configGrantTypes = configData.grantTypes.split(',').map(item => item.trim());
        const configClientAuthMethods = configData.clientAuthMethods.split(',').map(item => item.trim());
        setGrantTypesDropdownData(createGrantTypesDropdownData(configGrantTypes));
        defaultGrantTypesList(configGrantTypes);
        setClientAuthMethods(configClientAuthMethods);
      } catch (error) {
        console.log("Error in config: ", error)
      }
    }
  }, [createGrantTypesDropdownData, defaultGrantTypesList])

  useEffect(() => {
    const fetchData = async () => {
      try {
        setDataLoaded(false);
        const resData = await getApprovedAuthPartners(HttpService, setErrorCode, setErrorMsg, t);
        if (resData) {
          setPartnerData(resData);
          setPartnerIdDropdownData(createDropdownData('partnerId', '', false, resData, t));
        } else {
          setErrorMsg(t('commons.errorInResponse'));
        }
      } catch (err) {
        console.error('Error fetching data:', err);
      } finally {
        setDataLoaded(true);
      }
    };
    const fetchPolicyRequestsData = async () => {
      try {
        setDataLoaded(false);
        const resData = await getPartnerPolicyRequests(HttpService, setErrorCode, setErrorMsg, t);
        if (resData) {
          setPolicyRequestsData(resData);
        } else {
          setErrorMsg(t('commons.errorInResponse'));
        }
      } catch (err) {
        console.error('Error fetching data:', err);
      } finally {
        setDataLoaded(true);
      }
    };

    fetchPolicyRequestsData();
    fetchData();
  }, [t]);

  const onChangePartnerId = async (fieldName, selectedValue) => {
    setPartnerId(selectedValue);
    setPolicyName("");
    setPolicyGroupName("");
    setPoliciesDropdownData([]);
    setPartnerType("");
    // Find the selected partner data
    const selectedPartner = partnerData.find(item => item.partnerId === selectedValue);
    if (selectedPartner) {
      const activePolicies = policyRequestsData.filter(
        item => item.partnerId === selectedValue && item.status === 'approved'
      );
      setActivePoliciesData(activePolicies);
      setPartnerType(getPartnerTypeDescription("AUTH_PARTNER", t));
      setPolicyGroupName(selectedPartner.policyGroupName);
      setPoliciesDropdownData(createDropdownData('policyName', 'policyDescription', false, activePolicies, t));
    }
  };

  const onChangePolicyName = (fieldName, selectedValue) => {
    const selectedPolicy = activePoliciesData.find(item => item.policyName === selectedValue);
    if (selectedPolicy) {
      setPolicyName(selectedValue);
      setPolicyId(selectedPolicy.policyId);
    }
  };


  // Client name handler
  const handleClientNameChange = (value) => {
    setClientName(value);
    let inputError = "";
    validateInputRegex(value, (error) => {
      inputError = error;
    }, t);
    
    if (value.trim() === '' && value !== '') {
      setClientNameError(t('createOidcClient.clientNameRequired'));
    } else if (inputError) {
      setClientNameError(inputError);
    } else {
      setClientNameError("");
    }
  };

  // Multi-language client name lang map handlers
  const addClientNameLangMapEntry = () => {
    const usedLanguages = clientNameLangMapEntries.map(e => e.language).filter(lang => lang);
    // Exclude 'default' from available languages for lang map
    const availableLangs = languageDropdownData.filter(lang => 
      lang.fieldValue !== 'default' && !usedLanguages.includes(lang.fieldValue)
    );
    const availableLang = availableLangs[0];
    const newEntry = createOidcClientEntry(availableLang?.fieldValue, 'clientName');
    setClientNameLangMapEntries([...clientNameLangMapEntries, newEntry]);
  };


  const updateClientNameLangMapEntry = (id, field, value) => {
    const updated = clientNameLangMapEntries.map(entry =>
      entry.id === id ? { ...entry, [field]: value } : entry
    );
    setClientNameLangMapEntries(updated);

    if (field === 'text') {
      const entry = updated.find(e => e.id === id);
      validateOidcEntryText(value, entry, 'createOidcClient.clientNameRequired', clientNameLangMapErrors, setClientNameLangMapErrors, t);
    }
  };

  const deleteClientNameLangMapEntry = (id) => {
    const updated = clientNameLangMapEntries.filter(entry => entry.id !== id);
    setClientNameLangMapEntries(updated);
    const errors = { ...clientNameLangMapErrors };
    delete errors[id];
    setClientNameLangMapErrors(errors);
  };


  const handleGrantTypesChange = (fieldName, selectedValue) => {
    setGrantTypes(selectedValue);
    const grantTypeValue = [''];
    grantTypeValue[0] = selectedValue;
    setGrantTypesList(grantTypeValue);
  };

  // Below code related to adding & deleting of Redirect URLs
  const onChangeRedirectUrl = (index, value) => {
    const newRedirectUrls = [...redirectUrls];
    newRedirectUrls[index] = value;
    setInvalidRedirectUrl(validateUrl(index, newRedirectUrls, 2048, newRedirectUrls, t));
    setRedirectUrls(newRedirectUrls);
  };

  const addNewRedirectUrl = () => {
    if (redirectUrls.length < 5) {
      setRedirectUrls([...redirectUrls, '']);
    }
  };

  const onDeleteRedirectUrl = (index) => {
    if (redirectUrls.length > 1) {
      const newRedirectUrls = redirectUrls.filter((_, i) => i !== index);
      setRedirectUrls(newRedirectUrls);
      validateUrls(newRedirectUrls);
    }
  };

  const clickOnCancel = () => {
    moveToOidcClientsList(navigate);
  };

  const validateUrls = (urls) => {
    const filteredUrls = urls.filter(url => url.trim() !== "");
    const hasDuplicate = filteredUrls.some((url, index) => urls.indexOf(url) !== index);

    if (hasDuplicate) {
      setInvalidRedirectUrl(t('createOidcClient.duplicateUrl'));
    } else {
      setInvalidRedirectUrl("");
    }
  };

  const handlePublicKeyChange = async (value) => {
    setPublicKey(value);
    if (value.trim() === "") {
      setJsonError("");
      setPublicKeyInJson(null);
      return;
    }
    try {
      const parsedValue = JSON5.parse(value);
      // Validate the JSON has at least one key
      if (Object.keys(parsedValue).length === 0) {
        throw new Error("Empty JSON object");
      }
      setPublicKeyInJson(parsedValue);
      setJsonError("");
    } catch (error) {
      setJsonError(t("createOidcClient.invalidJwkFormat"));
      setPublicKeyInJson(null);
    }
  };

  const handleLogoUrlChange = (value) => {
    setInvalidLogoUrl(validateUrl(null, value, 2048, [], t));
    setLogoUrl(value);
  };

  const getRedirectUris = () => {
    const uriList = redirectUrls.filter(uri => uri !== '');
    return uriList;
  }

  // Additional Information handlers
  const handleConsentExpiryChange = (value) => {
    const numValue = value.replaceAll(/[^0-9]/g, '');
    setConsentExpiry(numValue);
    if (!numValue || numValue.trim() === '') {
      setConsentExpiryError(t('createOidcClient.consentExpiryRequired'));
    } else if (Number.isNaN(Number.parseInt(numValue, 10)) || Number.parseInt(numValue, 10) < 10) {
      setConsentExpiryError(t('createOidcClient.consentExpiryValidation'));
    } else {
      setConsentExpiryError("");
    }
  };

  const handleUserInfoResponseTypeChange = (fieldName, selectedValue) => {
    setUserInfoResponseType(selectedValue);
  };

  const handlePurposeTypeChange = (fieldName, selectedValue) => {
    setPurposeType(selectedValue);
    if (!selectedValue) {
      setPurposeTitleEntries([]);
      setPurposeSubtitleEntries([]);
      setPurposeTitleErrors({});
      setPurposeSubtitleErrors({});
      setPurposeTitleDefaultError("");
      setPurposeSubtitleDefaultError("");
    }
  };

  // Validate default requirement when entries change
  useEffect(() => {
    if (purposeType) {
      validatePurposeDefaultRequirement(purposeTitleEntries, 'title');
      validatePurposeDefaultRequirement(purposeSubtitleEntries, 'subtitle');
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [purposeTitleEntries, purposeSubtitleEntries, purposeType, t]);

  const addPurposeTitleEntry = () => {
    const usedLanguages = purposeTitleEntries.map(e => e.language).filter(lang => lang);
    // If no entries exist, default should be the first one
    const availableLang = purposeTitleEntries.length === 0
      ? languageDropdownData.find(lang => lang.fieldValue === 'default')
      : findAvailableOidcLanguage(usedLanguages, languageDropdownData);
    const newEntry = createOidcClientEntry(availableLang?.fieldValue, 'purposeTitle');
    const updated = [...purposeTitleEntries, newEntry];
    setPurposeTitleEntries(updated);
    validatePurposeDefaultRequirement(updated, 'title');
  };

  const addPurposeSubtitleEntry = () => {
    const usedLanguages = purposeSubtitleEntries.map(e => e.language).filter(lang => lang);
    // If no entries exist, default should be the first one
    const availableLang = purposeSubtitleEntries.length === 0
      ? languageDropdownData.find(lang => lang.fieldValue === 'default')
      : findAvailableOidcLanguage(usedLanguages, languageDropdownData);
    const newEntry = createOidcClientEntry(availableLang?.fieldValue, 'purposeSubtitle');
    const updated = [...purposeSubtitleEntries, newEntry];
    setPurposeSubtitleEntries(updated);
    validatePurposeDefaultRequirement(updated, 'subtitle');
  };

  const updatePurposeTitleEntry = (id, field, value) => {
    const updated = purposeTitleEntries.map(entry =>
      entry.id === id ? { ...entry, [field]: value } : entry
    );
    setPurposeTitleEntries(updated);

    if (field === 'text') {
      const entry = updated.find(e => e.id === id);
      validateOidcEntryText(value, entry, 'createOidcClient.purposeTitleRequired', purposeTitleErrors, setPurposeTitleErrors, t);
    }
    validatePurposeDefaultRequirement(updated, 'title');
  };

  const updatePurposeSubtitleEntry = (id, field, value) => {
    const updated = purposeSubtitleEntries.map(entry =>
      entry.id === id ? { ...entry, [field]: value } : entry
    );
    setPurposeSubtitleEntries(updated);

    if (field === 'text') {
      const entry = updated.find(e => e.id === id);
      validateOidcEntryText(value, entry, 'createOidcClient.purposeSubtitleRequired', purposeSubtitleErrors, setPurposeSubtitleErrors, t);
    }
    validatePurposeDefaultRequirement(updated, 'subtitle');
  };

  const validatePurposeDefaultRequirement = (entries, type) => {
    const errorMessage = entries.length > 0 && !entries.some(entry => entry.language === 'default')
      ? t('createOidcClient.defaultLanguageRequired')
      : "";

    if (type === 'title') {
      setPurposeTitleDefaultError(errorMessage);
    } else {
      setPurposeSubtitleDefaultError(errorMessage);
    }
  };

  const deletePurposeTitleEntry = (id) => {
    const updated = purposeTitleEntries.filter(entry => entry.id !== id);
    setPurposeTitleEntries(updated);
    const errors = { ...purposeTitleErrors };
    delete errors[id];
    setPurposeTitleErrors(errors);
    // Re-validate default requirement after deletion
    validatePurposeDefaultRequirement(updated, 'title');
  };

  const deletePurposeSubtitleEntry = (id) => {
    const updated = purposeSubtitleEntries.filter(entry => entry.id !== id);
    setPurposeSubtitleEntries(updated);
    const errors = { ...purposeSubtitleErrors };
    delete errors[id];
    setPurposeSubtitleErrors(errors);
    // Re-validate default requirement after deletion
    validatePurposeDefaultRequirement(updated, 'subtitle');
  };

  // Build purpose title/subtitle lang map with @none mapping
  const buildPurposeLangMap = (entries) => {
    const langMap = {};
    entries.forEach(entry => {
      if (entry.language && entry.text && entry.text.trim() !== '') {
        const langKey = entry.language === 'default' ? '@none' : entry.language;
        langMap[langKey] = trimAndReplace(entry.text);
      }
    });
    return Object.keys(langMap).length > 0 ? langMap : undefined;
  };

  const clickOnSubmit = async () => {
    setIsSubmitClicked(true);
    setErrorCode("");
    setErrorMsg("");
    setDataLoaded(false);

    // Build additionalConfig
    const additionalConfig = {};

    if (userInfoResponseType) {
      additionalConfig.userinfo_response_type = userInfoResponseType;
    }

    if (purposeType) {
      additionalConfig.purpose = {
        type: purposeType
      };

      const purposeTitleMap = buildPurposeLangMap(purposeTitleEntries);
      const purposeSubtitleMap = buildPurposeLangMap(purposeSubtitleEntries);

      if (purposeTitleMap) {
        additionalConfig.purpose.title = purposeTitleMap;
      }
      if (purposeSubtitleMap) {
        additionalConfig.purpose.subTitle = purposeSubtitleMap;
      }
    }

    additionalConfig.signup_banner_required = signUpBanner;
    additionalConfig.forgot_pwd_link_required = forgotPasswordBanner;

    if (consentExpiry && consentExpiry.trim() !== '') {
      additionalConfig.consent_expire_in_mins = Number.parseInt(consentExpiry, 10);
    }

    const clientNameLangMap = buildClientNameLangMap(clientNameLangMapEntries, additionalConfigRequired, clientName);

    const requestData = {
      name: trimAndReplace(clientName),
      policyId: policyId,
      publicKey: publicKeyInJson,
      authPartnerId: partnerId,
      logoUri: logoUrl,
      redirectUris: getRedirectUris(),
      grantTypes: grantTypesList,
      clientAuthMethods: clientAuthMethods,
      clientNameLangMap: clientNameLangMap
    };

    // Add additionalConfig only if it has any properties
    if (Object.keys(additionalConfig).length > 0 && additionalConfigRequired) {
      requestData.additionalConfig = additionalConfig;
    }
    const request = additionalConfigRequired ? createRequest(requestData, "mosip.pms.create.oidc.client.post", true) : createRequest(requestData);
    const apiUrl = additionalConfigRequired ? `/oidc-clients` : `/oauth/client`;
    try {
      const response = await HttpService.post(getPartnerManagerUrl(apiUrl, process.env.NODE_ENV), request, {
        headers: {
          'Content-Type': 'application/json'
        }
      });
      if (response) {
        const responseData = response.data;
        if (responseData && responseData.response) {
          const requiredData = {
            title: "createOidcClient.createOidcClient",
            backUrl: "/partnermanagement/authentication-services/oidc-clients-list",
            header: "createOidcClient.requestSuccessHeader",
            description: "createOidcClient.requestSuccessMsg",
            subNavigation: "authenticationServices.authenticationServices",
          }
          setConfirmationData(requiredData);
          setCreateOidcClientSuccess(true);
        } else {
          handleServiceErrors(responseData, setErrorCode, setErrorMsg);
        }
      } else {
        setErrorMsg(t('createOidcClient.errorInCreateOIDC'));
      }
      setDataLoaded(true);
    } catch (err) {
      if (err.response?.status && err.response.status !== 401) {
        setErrorMsg(err);
      }
      console.error("Error creating OIDC client: ", err);
    }
    setIsSubmitClicked(false);
  }


  const clearForm = () => {
    setErrorCode("");
    setErrorMsg("");
    setPartnerId("");
    setPartnerType("");
    setPolicyGroupName("");
    setPoliciesDropdownData([]);
    setPolicyName("");
    setClientName("");
    setClientNameError("");
    setClientNameLangMapEntries([]);
    setClientNameLangMapErrors({});
    setPublicKey("");
    setLogoUrl("");
    setRedirectUrls(['']);
    setJsonError("");
    setInvalidLogoUrl("");
    setInvalidRedirectUrl("");
    // Reset section expanded states
    setIsMandatoryInfoExpanded(true);
    setIsAdditionalInfoExpanded(false);
    setForgotPasswordBanner(additionalConfigRequired);
    setSignUpBanner(additionalConfigRequired);
    setConsentExpiry(additionalConfigRequired ? "10" : "");
    setConsentExpiryError("");
    setUserInfoResponseType("");
    setPurposeType("");
    setPurposeTitleEntries([]);
    setPurposeSubtitleEntries([]);
    setPurposeTitleErrors({});
    setPurposeSubtitleErrors({});
    setPurposeTitleDefaultError("");
    setPurposeSubtitleDefaultError("");
  };

  const redirectUrlsNotEmpty = () => {
    const validUris = redirectUrls.filter(uri => uri !== '');
    if (validUris.length > 0) {
      return true;
    } else {
      return false;
    }
  };

  const isFormValid = () => {
    const hasClientName = clientName && clientName.trim() !== '';
    const hasClientNameLangMapErrors = Object.keys(clientNameLangMapErrors).length > 0;
    const hasPurposeErrors = Object.keys(purposeTitleErrors).length > 0 || Object.keys(purposeSubtitleErrors).length > 0;
    const hasPurposeDefaultErrors = purposeTitleDefaultError !== "" || purposeSubtitleDefaultError !== "";
    
    // Validate that all client name lang map entries have non-blank text if entries exist
    const clientNameLangMapEntriesValid = clientNameLangMapEntries.length === 0 || 
      clientNameLangMapEntries.every(entry => entry.text && entry.text.trim() !== '');
    
    // Validate that all purpose title entries have non-blank text if purpose type is selected and entries exist
    const purposeTitleEntriesValid = !purposeType || purposeTitleEntries.length === 0 || 
      purposeTitleEntries.every(entry => entry.text && entry.text.trim() !== '');
    
    // Validate that all purpose subtitle entries have non-blank text if purpose type is selected and entries exist
    const purposeSubtitleEntriesValid = !purposeType || purposeSubtitleEntries.length === 0 || 
      purposeSubtitleEntries.every(entry => entry.text && entry.text.trim() !== '');
    
    return partnerId && policyName && hasClientName && publicKey.trim() && logoUrl && redirectUrlsNotEmpty() && grantTypes
      && !jsonError && !invalidLogoUrl && !invalidRedirectUrl && !consentExpiryError && !clientNameError && !hasClientNameLangMapErrors && !hasPurposeErrors && !hasPurposeDefaultErrors
      && clientNameLangMapEntriesValid && purposeTitleEntriesValid && purposeSubtitleEntriesValid;
  };

  const styles = {
    outerDiv: "!ml-0 !mb-0",
    dropdownLabel: "!text-sm !mb-1",
    dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
    selectionBox: "!top-10"
  }

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
      {!dataLoaded && (
        <LoadingIcon></LoadingIcon>
      )}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage id='create_oidc_client_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
          )}
          <div className="flex-col mt-5">
            <div className="flex justify-between">
              <Title title='createOidcClient.createOidcClient' subTitle='authenticationServices.authenticationServices' backLink='/partnermanagement/authentication-services/oidc-clients-list' />
            </div>
            {showCompatibilityMsg && (
              <div className="bg-[#FCFCFC] w-full my-3 rounded-lg shadow-lg items-center">
                <div className="flex items-center justify-center p-2">
                    <div className="p-2 bg-[#FFF7E5] border-2 border-[#EDDCAF] rounded-md w-full">
                      <p id='oidc_client_list_compatibility_msg' className="text-sm font-medium text-[#8B6105]">
                        <Trans i18nKey="createOidcClient.compatibilityMsg" components={{ italic: <i /> }} />
                      </p>
                    </div>
                </div>
              </div>
            )}
            {!createOidcClientSuccess ?
              <div className="w-full">
                <div className="">
                  <form>
                    {/* Mandatory Information Section */}
                    <div className="mb-4 px-7 py-4 bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                      <div
                        className="flex items-center justify-between cursor-pointer rounded px-2"
                        onClick={() => setIsMandatoryInfoExpanded(!isMandatoryInfoExpanded)}
                        role="button"
                        tabIndex="0"
                        onKeyDown={(e) => onPressEnterKey(e, () => setIsMandatoryInfoExpanded(!isMandatoryInfoExpanded))}
                      >
                        <h3 className="text-lg font-semibold text-dark-blue">{t('createOidcClient.primaryInformation')}</h3>
                        <img src={expandToggleIcon} alt="Toggle" className={`w-7 h-7 transform transition-transform ${isMandatoryInfoExpanded ? 'rotate-180' : ''}`} />
                      </div>
                      {isMandatoryInfoExpanded && (
                        <>
                          <div className="mt-4 mb-4 border-b border-gray-200"></div>
                          <p id='create_oidc_client_mandatory_field_msg' className="text-base text-[#3D4468] mt-2 mb-4">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red mx-1">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                          <div className="flex flex-col">
                            <div className="flex flex-row justify-between space-x-4 my-[1%]">
                              <div className="flex flex-col w-[48%]">
                                <DropdownComponent
                                  fieldName='partnerId'
                                  dropdownDataList={partnerIdDropdownData}
                                  onDropDownChangeEvent={onChangePartnerId}
                                  fieldNameKey='requestPolicy.partnerId*'
                                  placeHolderKey='createOidcClient.selectPartnerId'
                                  selectedDropdownValue={partnerId}
                                  styleSet={styles}
                                  addInfoIcon={true}
                                  infoKey='createOidcClient.partnerIdTooltip'
                                  id='create_oidc_partner_id'>
                                </DropdownComponent>
                              </div>
                              <div className="flex flex-col w-[48%]">
                                <label id='create_oidc_client_partner_type_label' className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red mx-1">*</span></label>
                                <button id='create_oidc_client_partner_type_context' disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                          overflow-x-auto whitespace-nowrap no-scrollbar" type="button">
                                  <span className={`w-full break-words ${partnerType ? 'text-dark-blue' : 'text-gray-400'} text-wrap text-start`}>{partnerType || t('commons.partnersHelpText')}</span>
                                  <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                  </svg>
                                </button>
                              </div>
                            </div>
                            <div className="flex flex-row justify-between space-x-4 my-2">
                              <div className="flex flex-col w-[48%]">
                                <label id='create_oidc_client_policy_group_label' className={`block text-dark-blue text-sm font-semibold mb-1 mx-1`}>{t('requestPolicy.policyGroup')}<span className="text-crimson-red mx-1">*</span></label>
                                <button id='create_oidc_client_policy_group_context' disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                          overflow-x-auto whitespace-nowrap no-scrollbar" type="button">
                                  <span className={`${partnerType ? 'text-dark-blue' : 'text-gray-400'}`}>{policyGroupName || t('commons.partnersHelpText')}</span>
                                  <svg className={`w-3 h-2 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
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
                                  placeHolderKey='createOidcClient.policyNamePlaceHolder'
                                  selectedDropdownValue={policyName}
                                  searchKey='commons.search'
                                  styleSet={styles}
                                  addInfoIcon={true}
                                  disabled={!partnerId}
                                  infoKey={t('createOidcClient.policyNameToolTip')}
                                  id='create_oidc_policy_name' />
                              </div>
                            </div>
                            {/* OIDC Client Name */}
                            <div className="flex my-[1%]">
                              <div className="flex flex-col w-full">
                                <label id='create_oidc_client_name_label' className={`flex items-center text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                  {t('authenticationServices.oidcClientName')}<span className="text-crimson-red mx-1">*</span>
                                  <Information infoKey={t('createOidcClient.clientNameTooltip')} id='client_name_info' />
                                </label>
                                <input
                                  id="create_oidc_client_name"
                                  value={clientName}
                                  onChange={(e) => handleClientNameChange(e.target.value)}
                                  className="h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline w-full"
                                  placeholder={t('createOidcClient.clientNamePlaceholder')}
                                  data-placeholder-id="create_oidc_client_name_placeholder"
                                  maxLength={256}
                                />
                                {clientNameError && <span id="create_oidc_client_name_error" className="text-sm text-crimson-red font-semibold mt-1">{clientNameError}</span>}
                              </div>
                            </div>
                            {/* OIDC Client Name Multilanguage */}
                            <div className="flex flex-col my-2">
                              <label id='create_oidc_client_name_multilang_label' className={`flex items-center text-dark-blue text-sm font-semibold mb-2 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                {t('createOidcClient.clientNameMultilanguage')}
                                <Information infoKey={t('createOidcClient.clientNameMultilanguageTooltip')} id='client_name_multilang_info' />
                              </label>
                              {clientNameLangMapEntries.length === 0 ? (
                                <div className="bg-white border border-neutral-200 rounded-md p-8 flex flex-col items-center justify-center min-h-[120px]">
                                  <button
                                    type="button"
                                    id="add_client_name_lang_map_entry"
                                    className="bg-[#1447b2] text-white font-semibold text-sm px-6 py-2 rounded-md cursor-pointer hover:bg-[#0f3a8a] transition-colors"
                                    tabIndex="0"
                                    onKeyDown={(e) => onPressEnterKey(e, addClientNameLangMapEntry)}
                                    onClick={addClientNameLangMapEntry}
                                  >
                                    {t('createOidcClient.addClientNameLangMap')}
                                  </button>
                                  <p className="text-gray-400 text-sm mt-2 text-center">
                                    {t('createOidcClient.addClientNameLangMapHelperText')}
                                  </p>
                                </div>
                              ) : (
                                <div className="bg-white border border-neutral-300 shadow-sm rounded-md p-4">
                                  {clientNameLangMapEntries.map((entry, index) => {
                                    const availableLangs = getAvailableOidcLanguages(entry.id, clientNameLangMapEntries, languageDropdownData, true);
                                    return (
                                      <div key={index} className="flex mb-2">
                                        <div className="w-1/3">
                                          <DropdownComponent
                                            fieldName={`clientNameLangMapLang_${index + 1}`}
                                            dropdownDataList={availableLangs}
                                            onDropDownChangeEvent={(field, value) => updateClientNameLangMapEntry(entry.id, 'language', value)}
                                            fieldNameKey=""
                                            placeHolderKey="createOidcClient.selectLanguage"
                                            selectedDropdownValue={entry.language}
                                            styleSet={styles}
                                            id={`client_name_lang_map_lang_${index + 1}`} />
                                        </div>
                                        <div className={`w-full mt-1 ${isLoginLanguageRTL ? 'mr-5' : 'ml-5'}`}>
                                          <TextInputComponentWithDeleteButton
                                            value={entry.text}
                                            onChange={(e) => updateClientNameLangMapEntry(entry.id, 'text', e.target.value)}
                                            onDelete={() => deleteClientNameLangMapEntry(entry.id)}
                                            placeholder={getOidcPlaceholderForLanguage(entry.language, 'NameForOidcClient', t)}
                                            placeholderId={getOidcPlaceholderIdForLanguage(entry.language, 'NameForOidcClient')}
                                            id={`client_name_lang_map_text_${index + 1}`}
                                            maxLength={256}
                                            showDelete={clientNameLangMapEntries.length > 0}
                                            errorMessage={clientNameLangMapErrors[entry.id]}
                                            isRTL={isLangRTL(entry.language)}
                                            languageCode={entry.language}
                                          />
                                        </div>
                                      </div>
                                    );
                                  })}
                                  {clientNameLangMapEntries.length < languageDropdownData.filter(lang => lang.fieldValue !== 'default').length && (
                                    <div
                                      role="button"
                                      id="add_client_name_lang_map_entry"
                                      className="text-[#1447b2] font-bold text-xs w-fit cursor-pointer"
                                      tabIndex="0"
                                      onKeyDown={(e) => onPressEnterKey(e, addClientNameLangMapEntry)}
                                      onClick={addClientNameLangMapEntry}
                                    >
                                      <span className="text-lg text-center">+</span>
                                      <span>{t('createOidcClient.addNew')}</span>
                                    </div>
                                  )}
                                </div>
                              )}
                            </div>
                            <div className="flex my-[1%]">
                              <div className="flex flex-col w-full">
                                <label id='create_oidc_client_public_key_label' className={`flex items-center text-dark-blue text-sm mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                  <p className={`font-semibold`}>{t('createOidcClient.publicKey')}<span className={`text-crimson-red mx-1`}>*</span></p>
                                  <Information infoKey={t('createOidcClient.publicKeyToolTip')} id='public_key_info' />
                                </label>
                                <textarea id="create_oidc_public_key" value={publicKey} onChange={(e) => handlePublicKeyChange(e.target.value)}
                                  className="px-2 py-4 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                  placeholder={t('createOidcClient.publicKeyPlaceHolder')}
                                  data-placeholder-id="create_oidc_public_key_placeholder">
                                </textarea>
                                {jsonError && <span id="create_oidc_invalid_public_key" className="text-sm text-crimson-red font-semibold">{jsonError}</span>}
                              </div>
                            </div>
                            <div className="flex my-[1%]">
                              <div className="flex flex-col w-full">
                                <label id="create_oidc_logo_url_label" className={`block text-dark-blue text-sm font-semibold mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('createOidcClient.logoUrl')}<span className="text-crimson-red mx-1">*</span></label>
                                <input id="create_oidc_logo_url" value={logoUrl} onChange={(e) => handleLogoUrlChange(e.target.value)}
                                  className="h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                  placeholder={t('createOidcClient.logoUrlPlaceHolder')}
                                  data-placeholder-id="create_oidc_logo_url_placeholder" />
                                {invalidLogoUrl && <span id="create_oidc_invalid_logo_url" className="text-sm text-crimson-red font-semibold">{invalidLogoUrl}</span>}
                              </div>
                            </div>

                            <div className="flex flex-row justify-between space-x-4 my-[1%]">
                              <div className="flex flex-col w-[48%]">
                                <label id="create_oidc_redirect_url_label" className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                  {t('createOidcClient.redirectUrl')}<span className="text-crimson-red mx-1">*</span>
                                </label>
                                {redirectUrls.map((url, index) => (
                                  <div key={index} className="flex w-full justify-between items-center h-10 px-2 py-2 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar focus:shadow-outline mb-2">
                                    <input
                                      value={url}
                                      onChange={(e) => onChangeRedirectUrl(index, e.target.value)}
                                      placeholder={t('createOidcClient.redirectUrlPlaceHolder')}
                                      className="w-[85%] focus:outline-none"
                                      data-placeholder-id="create_oidc_redirect_url_placeholder"
                                      id={"create_oidc_redirect_url" + (index + 1)}
                                    />
                                    <div role='button' id={"delete_redirect_url" + (index + 1)} className="flex flex-row items-center" onClick={() => onDeleteRedirectUrl(index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => onDeleteRedirectUrl(index))}>
                                      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="2"
                                        stroke={redirectUrls.length > 1 ? '#1447b2' : '#969696'} className={`w-[18px] h-5 mr-1 ${redirectUrls.length > 1 ? 'cursor-pointer' : ''}`}>
                                        <path strokeLinecap="round" strokeLinejoin="round"
                                          d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
                                      </svg>
                                      <p className={`text-sm font-semibold ${redirectUrls.length > 1 ? 'text-[#1447b2] cursor-pointer' : 'text-[#969696]'}`}>
                                        {t('createOidcClient.delete')}
                                      </p>
                                    </div>
                                  </div>
                                ))}
                                {invalidRedirectUrl && <span id="create_oidc_invalid_redirect_url" className="text-sm text-crimson-red font-semibold">{invalidRedirectUrl}</span>}
                                {redirectUrls.length < 5 && (
                                  <div
                                    role="button"
                                    id="add_new_redirect_url"
                                    className="text-[#1447b2] font-bold text-xs w-fit cursor-pointer"
                                    tabIndex="0"
                                    onKeyDown={(e) => onPressEnterKey(e, addNewRedirectUrl)}
                                    onClick={addNewRedirectUrl}
                                  >
                                    <span className="text-lg text-center">+</span>
                                    <span>{t('createOidcClient.addNew')}</span>
                                  </div>
                                )}
                              </div>
                              <div className="flex flex-col w-[48%]">
                                <DropdownComponent
                                  fieldName='grantTypes'
                                  dropdownDataList={grantTypesDropdownData}
                                  onDropDownChangeEvent={handleGrantTypesChange}
                                  fieldNameKey='createOidcClient.grantTypes*'
                                  selectedDropdownValue={grantTypes}
                                  styleSet={styles}
                                  id='create_oidc_grant_type'>
                                </DropdownComponent>
                              </div>
                            </div>
                          </div>
                        </>
                      )}
                    </div>

                    {/* Additional Information Section */}
                    <OidcClientAdditionalInfoSection
                      isAdditionalInfoExpanded={isAdditionalInfoExpanded}
                      setIsAdditionalInfoExpanded={setIsAdditionalInfoExpanded}
                      isLoginLanguageRTL={isLoginLanguageRTL}
                      additionalConfigRequired={additionalConfigRequired}
                      forgotPasswordBanner={forgotPasswordBanner}
                      setForgotPasswordBanner={setForgotPasswordBanner}
                      signUpBanner={signUpBanner}
                      setSignUpBanner={setSignUpBanner}
                      consentExpiry={consentExpiry}
                      handleConsentExpiryChange={handleConsentExpiryChange}
                      consentExpiryError={consentExpiryError}
                      userInfoResponseType={userInfoResponseType}
                      handleUserInfoResponseTypeChange={handleUserInfoResponseTypeChange}
                      userInfoResponseTypeDropdownData={userInfoResponseTypeDropdownData}
                      purposeType={purposeType}
                      handlePurposeTypeChange={handlePurposeTypeChange}
                      purposeTypeDropdownData={purposeTypeDropdownData}
                      purposeTitleEntries={purposeTitleEntries}
                      addPurposeTitleEntry={addPurposeTitleEntry}
                      updatePurposeTitleEntry={updatePurposeTitleEntry}
                      deletePurposeTitleEntry={deletePurposeTitleEntry}
                      purposeTitleErrors={purposeTitleErrors}
                      purposeTitleDefaultError={purposeTitleDefaultError}
                      purposeSubtitleEntries={purposeSubtitleEntries}
                      addPurposeSubtitleEntry={addPurposeSubtitleEntry}
                      updatePurposeSubtitleEntry={updatePurposeSubtitleEntry}
                      deletePurposeSubtitleEntry={deletePurposeSubtitleEntry}
                      purposeSubtitleErrors={purposeSubtitleErrors}
                      purposeSubtitleDefaultError={purposeSubtitleDefaultError}
                      languageDropdownData={languageDropdownData}
                      styles={styles}
                    />
                  </form>
                </div>
                <div className="pb-3 pt-6 px-4 bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                  <div className="border bg-medium-gray" />
                  <div className="flex flex-row px-[3%] py-6 justify-between">
                    <button type="button" tabIndex={0} id="create_oidc_clear_form" onClick={() => clearForm()} onKeyDown={(e) => onPressEnterKey(e, clearForm)} className="mr-2 w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold">{t('requestPolicy.clearForm')}</button>
                    <div className="flex flex-row space-x-3 w-full md:w-auto justify-end">
                      <button type="button" tabIndex={0} id="create_oidc_cancel_btn" onClick={() => clickOnCancel()} onKeyDown={(e) => onPressEnterKey(e, clickOnCancel)} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                      <button type="button" tabIndex={isFormValid() ? 0 : -1} id="create_oidc_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()} onKeyDown={(e) => { if(isFormValid()) onPressEnterKey(e, clickOnSubmit); }} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white'}`}>{t('requestPolicy.submit')}</button>
                    </div>
                  </div>
                </div>
              </div>
              :
              <Confirmation id='create_oidc_client_confirmation' confirmationData={confirmationData} />
            }
          </div>
        </>
      )}
      <BlockerPrompt blocker={blocker} />
    </div>
  )
}

export default CreateOidcClient;