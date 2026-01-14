import React from "react";
import { useState, useEffect, useCallback } from "react";
import { useNavigate, useBlocker } from "react-router-dom";
import { Trans, useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { HttpService } from "../../../services/HttpService";
import {
    moveToOidcClientsList, isLangRTL, getPartnerManagerUrl, handleServiceErrors, getGrantTypes, validateUrl, onPressEnterKey, trimAndReplace,
    getErrorMessage,
    formatPublicKey,
    validateInputRegex,
    getLanguageDisplayName,
    createDropdownData,
    createRequest,
    isOidcClientAdditionalInfoRequired,
    buildClientNameLangMap,
    createOidcClientEntry, findAvailableOidcLanguage, validateOidcEntryText,
    getOidcPlaceholderForLanguage, getAvailableOidcLanguages,
    initializeUserInfoResponseTypeDropdown, initializePurposeTypeDropdown, initializeLanguageDropdown
} from "../../../utils/AppUtils";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import DropdownComponent from "../../common/fields/DropdownComponent";
import Information from "../../common/fields/Information";
import Title from "../../common/Title";
import Confirmation from "../../common/Confirmation";
import BlockerPrompt from "../../common/BlockerPrompt";
import somethingWentWrongIcon from '../../../svg/something_went_wrong_icon.svg';
import expandToggleIcon from '../../../svg/expand_toggle_icon.svg';
import TextInputComponentWithDeleteButton from '../../common/fields/TextInputComponentWithDeleteButton';
import OidcClientAdditionalInfoSection from '../../common/OidcClientAdditionalInfoSection';

function EditOidcClient() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const navigate = useNavigate();
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [invalidLogoUrl, setInvalidLogoUrl] = useState("");
    const [invalidRedirectUrl, setInvalidRedirectUrl] = useState("");
    const [grantTypesDropdownData, setGrantTypesDropdownData] = useState([]);
    const [editOidcClientSuccess, setEditOidcClientSuccess] = useState(false);
    const [confirmationData, setConfirmationData] = useState({});
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);
    const [unexpectedError, setUnexpectedError] = useState(false);
    const [oidcClientDetails, setOidcClientDetails] = useState({
        id: '',
        relyingPartyId: '',
        policyId: '',
        policyName: '',
        policyGroupName: '',
        name: '',
        publicKey: '',
        logoUri: '',
        redirectUris: [],
        grantTypes: [],
    });
    const [selectedClientDetails, setSelectedClientDetails] = useState({
        id: '',
        relyingPartyId: '',
        policyId: '',
        policyName: '',
        policyGroupName: '',
        name: '',
        publicKey: '',
        logoUri: '',
        redirectUris: [],
        grantTypes: [],
    });
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
            if (isSubmitClicked || editOidcClientSuccess) {
                setIsSubmitClicked(false);
                return false;
            }
            return (
                (checkIfRedirectUrisIsUpdated() ||
                    (oidcClientDetails.grantTypes[0] !== selectedClientDetails.grantTypes[0]) ||
                    (oidcClientDetails.logoUri !== selectedClientDetails.logoUri) ||
                    (trimAndReplace(clientName) !== selectedClientDetails.name) ||
                    checkIfClientNameLangMapIsUpdated() ||
                    checkIfAdditionalConfigIsUpdated()) && currentLocation.pathname !== nextLocation.pathname
            );
        }
    );

    useEffect(() => {
        const shouldWarnBeforeUnload = () =>
            checkIfRedirectUrisIsUpdated() ||
            (oidcClientDetails.grantTypes[0] !== selectedClientDetails.grantTypes[0]) ||
            (oidcClientDetails.logoUri !== selectedClientDetails.logoUri) ||
            (trimAndReplace(clientName) !== selectedClientDetails.name) ||
            checkIfClientNameLangMapIsUpdated() ||
            checkIfAdditionalConfigIsUpdated();

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
    }, [oidcClientDetails.redirectUris, oidcClientDetails.grantTypes, oidcClientDetails.logoUri, clientName, clientNameLangMapEntries, consentExpiry, userInfoResponseType, forgotPasswordBanner, signUpBanner, purposeType, purposeTitleEntries, purposeSubtitleEntries, isSubmitClicked, selectedClientDetails]);

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

    // Helper function to add missing languages to dropdown data
    const addMissingLanguagesToDropdown = useCallback((entries, currentDropdownData) => {
        if (!entries || entries.length === 0) {
            return currentDropdownData;
        }

        const existingLanguageCodes = new Set(
            currentDropdownData.map(item => item.fieldValue).filter(code => code)
        );

        const missingLanguages = [];
        entries.forEach(entry => {
            if (entry.language && 
                entry.language !== 'default' && 
                !existingLanguageCodes.has(entry.language)) {
                missingLanguages.push(entry.language);
                existingLanguageCodes.add(entry.language);
            }
        });

        if (missingLanguages.length === 0) {
            return currentDropdownData;
        }

        const newLanguageEntries = missingLanguages.map(langCode => ({
            languageCode: langCode,
            name: getLanguageDisplayName(langCode, t)
        }));

        const newDropdownItems = newLanguageEntries.map(lang => ({
            fieldCode: lang.name,
            fieldValue: lang.languageCode
        }));

        return [...currentDropdownData, ...newDropdownItems];
    }, [t]);
    
  useEffect(() => {
         const clientData = sessionStorage.getItem('selectedClientData');
         if (!clientData) {
             setUnexpectedError(true);
             return;
         }
        const config = sessionStorage.getItem('appConfig');
        if (config) {
            const configData = JSON.parse(config);
            const configGrantTypes = configData.grantTypes.split(',').map(item => item.trim());
            setGrantTypesDropdownData(createGrantTypesDropdownData(configGrantTypes));
        }
         const fetchData = async () => {
             try {
                 setDataLoaded(false);
                let selectedOidcClientData;
                try {
                    selectedOidcClientData = JSON.parse(clientData);
                } catch (parseErr) {
                    console.error('Error parsing selectedClientData from sessionStorage:', parseErr);
                    sessionStorage.removeItem('selectedClientData');
                    setUnexpectedError(true);
                    setDataLoaded(true);
                    return;
                }
                // Use clientId from sessionStorage for the GET request
                const clientId = selectedOidcClientData.clientId;
                 const response = await HttpService.get(getPartnerManagerUrl(`/oidc-clients/${clientId}`, process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        setSelectedClientDetails(resData);
                        setOidcClientDetails(resData);
                         
                        // Initialize client name
                        setClientName(resData.name || '');
                        
                        // Initialize client name lang map
                        let langMapEntries = [];
                        if (resData.clientNameLangMap && typeof resData.clientNameLangMap === 'object') {
                            langMapEntries = Object.entries(resData.clientNameLangMap).map(([language, text]) => ({
                                id: `oidc_name_${crypto.randomUUID()}`,
                                language: language,
                                text: text || ''
                            }));
                            setClientNameLangMapEntries(langMapEntries);
                        }
                        
                        // Initialize additionalConfig fields
                        let titleEntries = [];
                        let subtitleEntries = [];
                        if (resData.additionalConfig) {
                            const additionalConfig = resData.additionalConfig;
                             
                             if (additionalConfig.consent_expire_in_mins !== undefined) {
                                 setConsentExpiry(String(additionalConfig.consent_expire_in_mins));
                             }
                             
                             if (additionalConfig.userinfo_response_type) {
                                 setUserInfoResponseType(additionalConfig.userinfo_response_type);
                             }
                             
                             if (additionalConfig.forgot_pwd_link_required !== undefined) {
                                 setForgotPasswordBanner(additionalConfig.forgot_pwd_link_required);
                             }
                             
                             if (additionalConfig.signup_banner_required !== undefined) {
                                 setSignUpBanner(additionalConfig.signup_banner_required);
                             }
                             
                             if (additionalConfig.purpose) {
                                 setPurposeType(additionalConfig.purpose.type || '');
                                 
                                 // Initialize purpose title entries
                                if (additionalConfig.purpose.title && typeof additionalConfig.purpose.title === 'object') {
                                    titleEntries = Object.entries(additionalConfig.purpose.title).map(([langKey, text]) => ({
                                        id: `purpose_title_${crypto.randomUUID()}`,
                                        language: langKey === '@none' ? 'default' : langKey,
                                        text: text || ''
                                    }));
                                    setPurposeTitleEntries(titleEntries);
                                }
                                 
                                 // Initialize purpose subtitle entries
                                if (additionalConfig.purpose.subTitle && typeof additionalConfig.purpose.subTitle === 'object') {
                                    subtitleEntries = Object.entries(additionalConfig.purpose.subTitle).map(([langKey, text]) => ({
                                        id: `purpose_subtitle_${crypto.randomUUID()}`,
                                        language: langKey === '@none' ? 'default' : langKey,
                                        text: text || ''
                                    }));
                                    setPurposeSubtitleEntries(subtitleEntries);
                                }
                             }
                         }
                         
                         // Add missing languages from API response to dropdown data
                         setLanguageDropdownData(prevDropdownData => {
                             let updatedData = addMissingLanguagesToDropdown(langMapEntries, prevDropdownData);
                             updatedData = addMissingLanguagesToDropdown(titleEntries, updatedData);
                             updatedData = addMissingLanguagesToDropdown(subtitleEntries, updatedData);
                             return updatedData;
                         });
                     } else {
                         setUnexpectedError(true);
                         handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                     }
                 } else {
                     setUnexpectedError(true);
                     setErrorMsg(t('editOidcClient.errorWhileGettingOidcClientDetails'))
                 }
                 setDataLoaded(true);
             } catch (err) {
                console.error('Error fetching data:', err);
                if (err.response?.status && err.response.status !== 401) {
                    setUnexpectedError(true);
                    setErrorMsg(err.toString());
                }
             }
         };
         fetchData();
     }, [createGrantTypesDropdownData, t, addMissingLanguagesToDropdown]);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };


    // Client name handler
    const handleClientNameChange = (value) => {
        setClientName(value);
        let inputError = "";
        validateInputRegex(value, (error) => {
            inputError = error;
        }, t);
        
        if (value.trim() === '') {
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


    const handleLogoUrlChange = (value) => {
        setInvalidLogoUrl(validateUrl(null, value, 2048, [], t));
        setOidcClientDetails(prevDetails => ({
            ...prevDetails,
            logoUri: value
        }));
    };

    const handleGrantTypesChange = (fieldName, selectedValue) => {
        const grantTypeValue = [''];
        grantTypeValue[0] = selectedValue
        setOidcClientDetails(prevDetails => ({
            ...prevDetails,
            grantTypes: grantTypeValue
        }));
    }

    // Below code related to adding & deleting of Redirect URLs
    const onChangeRedirectUrl = (index, value) => {
        const newRedirectUrls = [...oidcClientDetails.redirectUris];
        newRedirectUrls[index] = value;
        setInvalidRedirectUrl(validateUrl(index, value, 2048, newRedirectUrls, t));
        setOidcClientDetails(prevDetails => ({
            ...prevDetails,
            redirectUris: newRedirectUrls
        }));
    };
    const addNewRedirectUrl = () => {
        if (oidcClientDetails.redirectUris.length < 5) {
            const addRedirectUrl = [...oidcClientDetails.redirectUris, ''];
            setOidcClientDetails(prevDetails => ({
                ...prevDetails,
                redirectUris: addRedirectUrl
            }));
        }
    };
    const onDeleteRedirectUrl = (index) => {
        if (oidcClientDetails.redirectUris.length > 1) {
            const newRedirectUrls = oidcClientDetails.redirectUris.filter((_, i) => i !== index);
            setOidcClientDetails(prevDetails => ({
                ...prevDetails,
                redirectUris: newRedirectUrls
            }));
            validateUrls(newRedirectUrls);
        }
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

    // Additional Information
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
    }, [purposeTitleEntries, purposeSubtitleEntries, purposeType, t]);

    const addPurposeTitleEntry = () => {
        const usedLanguages = purposeTitleEntries.map(e => e.language).filter(lang => lang);
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
        validatePurposeDefaultRequirement(updated, 'title');
    };

    const deletePurposeSubtitleEntry = (id) => {
        const updated = purposeSubtitleEntries.filter(entry => entry.id !== id);
        setPurposeSubtitleEntries(updated);
        const errors = { ...purposeSubtitleErrors };
        delete errors[id];
        setPurposeSubtitleErrors(errors);
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

    const checkIfRedirectUrisIsUpdated = () => {
        const oidcUris = oidcClientDetails.redirectUris.filter(uri => uri !== '');
        const selectedUris = selectedClientDetails.redirectUris.filter(uri => uri !== '');
        if (oidcUris.length !== selectedUris.length) {
            return true;
        }
        for (let i = 0; i < oidcUris.length; i++) {
            if (oidcUris[i] !== selectedUris[i]) {
                return true;
            }
        }
        return false;
    }

    const checkIfClientNameLangMapIsUpdated = () => {
        const currentLangMap = buildClientNameLangMap(clientNameLangMapEntries, additionalConfigRequired, clientName);
        
        // Normalize selected map the same way as current map to ensure consistent comparison
        const selectedClientNameLangMapEntries = selectedClientDetails.clientNameLangMap && typeof selectedClientDetails.clientNameLangMap === 'object'
            ? Object.entries(selectedClientDetails.clientNameLangMap).map(([language, text]) => ({
                language: language,
                text: text || ''
            }))
            : [];
        const selectedLangMap = buildClientNameLangMap(selectedClientNameLangMapEntries, additionalConfigRequired, selectedClientDetails.name || clientName);
        
        const currentKeys = Object.keys(currentLangMap).sort((a, b) => a.localeCompare(b));
        const selectedKeys = Object.keys(selectedLangMap).sort((a, b) => a.localeCompare(b));
        
        if (currentKeys.length !== selectedKeys.length) {
            return true;
        }
        
        for (let key of currentKeys) {
            if (currentLangMap[key] !== selectedLangMap[key]) {
                return true;
            }
        }
        return false;
    }

    const checkIfAdditionalConfigIsUpdated = () => {
        if(additionalConfigRequired) {
            const selectedAdditionalConfig = selectedClientDetails.additionalConfig || {};
            
            // Check consent expiry
            const currentConsentExpiry = consentExpiry ? Number.parseInt(consentExpiry, 10) : undefined;
            const selectedConsentExpiry = selectedAdditionalConfig.consent_expire_in_mins;
            if (currentConsentExpiry !== selectedConsentExpiry) {
                return true;
            }
            
            // Check user info response type
            if (userInfoResponseType !== (selectedAdditionalConfig.userinfo_response_type || '')) {
                return true;
            }
            
            // Check toggles
            if (forgotPasswordBanner !== (selectedAdditionalConfig.forgot_pwd_link_required !== undefined ? selectedAdditionalConfig.forgot_pwd_link_required : true)) {
                return true;
            }
            if (signUpBanner !== (selectedAdditionalConfig.signup_banner_required !== undefined ? selectedAdditionalConfig.signup_banner_required : true)) {
                return true;
            }
            
            // Check purpose type
            const selectedPurposeType = selectedAdditionalConfig.purpose?.type || '';
            if (purposeType !== selectedPurposeType) {
                return true;
            }
            
            // Check purpose title
            if (purposeType) {
                const currentTitleMap = buildPurposeLangMap(purposeTitleEntries);
                const selectedTitleMap = selectedAdditionalConfig.purpose?.title || {};
                const currentTitleKeys = currentTitleMap ? Object.keys(currentTitleMap).sort((a, b) => a.localeCompare(b)) : [];
                const selectedTitleKeys = Object.keys(selectedTitleMap).sort((a, b) => a.localeCompare(b));
                if (currentTitleKeys.length !== selectedTitleKeys.length) {
                    return true;
                }
                for (let key of currentTitleKeys) {
                    if (currentTitleMap[key] !== selectedTitleMap[key]) {
                        return true;
                    }
                }
                
                // Check purpose subtitle
                const currentSubtitleMap = buildPurposeLangMap(purposeSubtitleEntries);
                const selectedSubtitleMap = selectedAdditionalConfig.purpose?.subTitle || {};
                const currentSubtitleKeys = currentSubtitleMap ? Object.keys(currentSubtitleMap).sort((a, b) => a.localeCompare(b)) : [];
                const selectedSubtitleKeys = Object.keys(selectedSubtitleMap).sort((a, b) => a.localeCompare(b));
                if (currentSubtitleKeys.length !== selectedSubtitleKeys.length) {
                    return true;
                }
                for (let key of currentSubtitleKeys) {
                    if (currentSubtitleMap[key] !== selectedSubtitleMap[key]) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    const isRedirectUriNotEmpty = () => {
        const filteredOidcUris = oidcClientDetails.redirectUris.filter(uri => uri !== '');
        if (filteredOidcUris.length === 0) {
            return false;
        }
        return true;
    }

    const isFormValid = () => {
        const hasClientName = clientName && clientName.trim() !== '';
        const hasClientNameLangMapErrors = Object.keys(clientNameLangMapErrors).length > 0;
        const hasPurposeErrors = Object.keys(purposeTitleErrors).length > 0 || Object.keys(purposeSubtitleErrors).length > 0;
        const hasPurposeDefaultErrors = purposeTitleDefaultError !== "" || purposeSubtitleDefaultError !== "";
        
        const clientNameLangMapEntriesValid = clientNameLangMapEntries.length === 0 || 
            clientNameLangMapEntries.every(entry => entry.text && entry.text.trim() !== '');
        
        const purposeTitleEntriesValid = !purposeType || purposeTitleEntries.length === 0 || 
            purposeTitleEntries.every(entry => entry.text && entry.text.trim() !== '');
        
        const purposeSubtitleEntriesValid = !purposeType || purposeSubtitleEntries.length === 0 || 
            purposeSubtitleEntries.every(entry => entry.text && entry.text.trim() !== '');
        
        const hasChanges = checkIfRedirectUrisIsUpdated() ||
            (oidcClientDetails.grantTypes[0] !== selectedClientDetails.grantTypes[0]) ||
            (oidcClientDetails.logoUri !== selectedClientDetails.logoUri) ||
            (trimAndReplace(clientName) !== selectedClientDetails.name) ||
            checkIfClientNameLangMapIsUpdated() ||
            checkIfAdditionalConfigIsUpdated();
        
        return hasChanges && hasClientName && oidcClientDetails.logoUri !== "" && isRedirectUriNotEmpty()
            && !invalidLogoUrl && !invalidRedirectUrl && !clientNameError && !hasClientNameLangMapErrors && !hasPurposeErrors && !hasPurposeDefaultErrors
            && !consentExpiryError && clientNameLangMapEntriesValid && purposeTitleEntriesValid && purposeSubtitleEntriesValid;
    }

    const undoChanges = () => {
        setInvalidLogoUrl("");
        setInvalidRedirectUrl("");
        setErrorCode("");
        setErrorMsg("");
        setOidcClientDetails(selectedClientDetails);
        setClientName(selectedClientDetails.name || '');
        setClientNameError("");
        setClientNameLangMapEntries([]);
        setClientNameLangMapErrors({});
        
        // Reset additional config fields
        const selectedAdditionalConfig = selectedClientDetails.additionalConfig || {};
        setConsentExpiry(selectedAdditionalConfig.consent_expire_in_mins ? String(selectedAdditionalConfig.consent_expire_in_mins) : additionalConfigRequired ? "10" : "");
        setConsentExpiryError("");
        setUserInfoResponseType(selectedAdditionalConfig.userinfo_response_type || "");
        setForgotPasswordBanner(selectedAdditionalConfig.forgot_pwd_link_required !== undefined ? selectedAdditionalConfig.forgot_pwd_link_required : additionalConfigRequired);
        setSignUpBanner(selectedAdditionalConfig.signup_banner_required !== undefined ? selectedAdditionalConfig.signup_banner_required : additionalConfigRequired);
        setPurposeType(selectedAdditionalConfig.purpose?.type || "");
        
        // Reset purpose entries
        if (selectedAdditionalConfig.purpose?.title) {
            const titleEntries = Object.entries(selectedAdditionalConfig.purpose.title).map(([langKey, text]) => ({
                id: `purpose_title_${crypto.randomUUID()}`,
                language: langKey === '@none' ? 'default' : langKey,
                text: text || ''
            }));
            setPurposeTitleEntries(titleEntries);
        } else {
            setPurposeTitleEntries([]);
        }
        
        if (selectedAdditionalConfig.purpose?.subTitle) {
            const subtitleEntries = Object.entries(selectedAdditionalConfig.purpose.subTitle).map(([langKey, text]) => ({
                id: `purpose_subtitle_${crypto.randomUUID()}`,
                language: langKey === '@none' ? 'default' : langKey,
                text: text || ''
            }));
            setPurposeSubtitleEntries(subtitleEntries);
        } else {
            setPurposeSubtitleEntries([]);
        }
        
        setPurposeTitleErrors({});
        setPurposeSubtitleErrors({});
        setPurposeTitleDefaultError("");
        setPurposeSubtitleDefaultError("");
        
        // Reset client name lang map
        if (selectedClientDetails.clientNameLangMap && typeof selectedClientDetails.clientNameLangMap === 'object') {
            const langMapEntries = Object.entries(selectedClientDetails.clientNameLangMap).map(([language, text]) => ({
                id: `oidc_name_${crypto.randomUUID()}`,
                language: language,
                text: text || ''
            }));
            setClientNameLangMapEntries(langMapEntries);
        } else {
            setClientNameLangMapEntries([]);
        }
    }

    const getRedirectUris = () => {
        const uriList = oidcClientDetails.redirectUris.filter(uri => uri !== '');
        return uriList;
    }

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
            logoUri: oidcClientDetails.logoUri,
            redirectUris: getRedirectUris(),
            status: oidcClientDetails.status,
            grantTypes: oidcClientDetails.grantTypes,
            clientName: trimAndReplace(clientName),
            clientAuthMethods: oidcClientDetails.clientAuthMethods,
            clientNameLangMap: clientNameLangMap
        };
        
        // Add additionalConfig only if it has any properties
        if (Object.keys(additionalConfig).length > 0 && additionalConfigRequired) {
            requestData.additionalConfig = additionalConfig;
        }
        
        const request = additionalConfigRequired ? createRequest(requestData, "mosip.pms.update.oidc.client.put", true) : createRequest(requestData);
        const apiUrl = additionalConfigRequired ? `/oidc-clients/${oidcClientDetails.id}` : `/oauth/client/${oidcClientDetails.id}`;
        try {
            const response = await HttpService.put(getPartnerManagerUrl(apiUrl, process.env.NODE_ENV), request, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const responseData = response.data;
            if (responseData && responseData.response) {
                setDataLoaded(true);
                const requireData = {
                    title: "editOidcClient.editOidcClient",
                    backUrl: "/partnermanagement/authentication-services/oidc-clients-list",
                    header: "editOidcClient.editSuccessHeader",
                    description: "editOidcClient.editSuccessMsg",
                    subNavigation: "authenticationServices.authenticationServices",
                }
                setConfirmationData(requireData);
                setEditOidcClientSuccess(true);
            } else {
                setDataLoaded(true);
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        } catch (err) {
            if (err.response?.status && err.response.status !== 401) {
                setDataLoaded(true);
                setErrorMsg(err.toString());
            }
        }
        setIsSubmitClicked(false);
    }

    const clickOnCancel = () => {
        moveToOidcClientsList(navigate);
    }

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !mb-1",
        dropdownButton: "!w-full !h-10 !rounded-md !text-base !text-left",
        selectionBox: "!top-10"
    }


    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter relative`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {!unexpectedError && errorMsg && (
                        <ErrorMessage id='edit_oidc_client_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <div className="flex justify-between">
                            <Title title='editOidcClient.editOidcClient' subTitle='authenticationServices.authenticationServices' backLink='/partnermanagement/authentication-services/oidc-clients-list' />
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
                        {unexpectedError && (
                            <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center items-center">
                                        <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                                        <p id='edit_oidc_unexpected_error_header' className="text-base font-semibold text-[#6F6E6E] pt-4">{t('commons.unexpectedError')}</p>
                                        <p id='edit_oidc_unexpected_error_message' className="text-sm font-semibold text-[#6F6E6E] pt-1 pb-4">{getErrorMessage(errorCode, t, errorMsg)}</p>
                                        <button onClick={() => moveToOidcClientsList(navigate)} type="button" id='edit_oidc_go_back_btn'
                                            className={`w-32 h-10 flex items-center justify-center font-semibold rounded-md text-sm mx-8 py-3 bg-tory-blue text-white`}>
                                            {t('commons.goBack')}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        )}
                        {!unexpectedError && (
                            <>
                                {!editOidcClientSuccess ?
                                    <div className="w-full">
                                        <div className="">
                                            <form>
                                                {/* Mandatory Information Section */}
                                                <div className="mb-4 px-7 py-4 bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                                                    <div
                                                        className="flex items-center justify-between cursor-pointer"
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
                                                            <p id='edit_oidc_mandatory_fields_msg' className="text-base text-[#3D4468] mt-2 mb-4">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red mx-1">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                                            <div className="flex flex-col">
                                                                <div className="flex flex-row justify-between space-x-4 my-[1%]">
                                                                    <div className="flex flex-col w-[48%]">
                                                                        <label id='edit_oidc_partner_id_label' className={`flex text-dark-blue items-center text-sm mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                                            <p className={`font-semibold`}>{t('requestPolicy.partnerId')}<span className={`text-crimson-red mx-1`}>*</span></p>
                                                                            <Information infoKey={t('createOidcClient.partnerIdTooltip')} id='partner_id_info' />
                                                                        </label>
                                                                        <button id='edit_oidc_partner_id' disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                                            <span className="w-full break-all text-wrap text-start">{oidcClientDetails.relyingPartyId}</span>
                                                                            <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                                            </svg>
                                                                        </button>
                                                                    </div>
                                                                    <div className="flex flex-col w-[48%]">
                                                                        <label id='edit_oidc_partner_type_label' className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                                            {t('requestPolicy.partnerType')}<span className="text-crimson-red mx-1">*</span>
                                                                        </label>
                                                                        <button id='edit_oidc_partner_type' disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                                            <span className="w-full break-all text-wrap text-start">{t("partnerTypes.authPartner")}</span>
                                                                            <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                                            </svg>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                                <div className="flex flex-row justify-between space-x-4 my-2">
                                                                    <div className="flex flex-col w-[48%]">
                                                                        <label id='edit_oidc_policy_group_label' className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.policyGroup')}<span className="text-crimson-red mx-1">*</span></label>
                                                                        <button id='edit_oidc_policy_group' disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                                            <span className="w-full break-all text-wrap text-start">{oidcClientDetails.policyGroupName}</span>
                                                                            <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                                            </svg>
                                                                        </button>
                                                                    </div>
                                                                    <div className="flex flex-col w-[48%]">
                                                                        <label id='edit_oidc_policy_name_label' className={`flex text-dark-blue items-center text-sm mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                                            <p className={`font-semibold`}>{t('requestPolicy.policyName')}<span className={`text-crimson-red mx-1`}>*</span></p>
                                                                            <Information infoKey={t('createOidcClient.policyNameToolTip')} id='policy_name_info' />
                                                                        </label>
                                                                        <button id='edit_oidc_policy_name' disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                                            <span className="w-full break-all text-wrap text-start">{oidcClientDetails.policyName}</span>
                                                                            <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                                            </svg>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                                {/* OIDC Client Name */}
                                                                <div className="flex my-[1%]">
                                                                    <div className="flex flex-col w-full">
                                                                        <label id='edit_oidc_client_name_label' className={`flex items-center text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                                            {t('authenticationServices.oidcClientName')}<span className="text-crimson-red mx-1">*</span>
                                                                            <Information infoKey={t('createOidcClient.clientNameTooltip')} id='client_name_info' />
                                                                        </label>
                                                                        <input
                                                                            id="oidc_edit_enter_client_name_input"
                                                                            value={clientName}
                                                                            onChange={(e) => handleClientNameChange(e.target.value)}
                                                                            className="h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline w-full"
                                                                            placeholder={t('createOidcClient.clientNamePlaceholder')}
                                                                            maxLength={256}
                                                                        />
                                                                        {clientNameError && <span id="edit_oidc_client_name_error" className="text-sm text-crimson-red font-semibold mt-1">{clientNameError}</span>}
                                                                    </div>
                                                                </div>
                                                                {/* OIDC Client Name Multilanguage */}
                                                                <div className="flex flex-col my-2">
                                                                    <label id='edit_oidc_client_name_multilang_label' className={`flex items-center text-dark-blue text-sm font-semibold mb-2 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                                        {t('createOidcClient.clientNameMultilanguage')}
                                                                        <Information infoKey={t('createOidcClient.clientNameMultilanguageTooltip')} id='client_name_multilang_info' />
                                                                    </label>
                                                                    {clientNameLangMapEntries.length === 0 ? (
                                                                        <div className="bg-white border border-neutral-200 rounded-md p-8 flex flex-col items-center justify-center min-h-[120px]">
                                                                            <button
                                                                                type="button"
                                                                                id="add_client_name_lang_map_entry_top"
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
                                                                                    <div key={entry.id} className="flex mb-2">
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
                                                                                    id="add_client_name_lang_map_entry_inline"
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
                                                                        <label id='edit_oidc_public_key_label' className={`flex items-center text-dark-blue text-sm mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                                            <p className={`font-semibold`}>{t('createOidcClient.publicKey')}<span className={`text-crimson-red mx-1`}>*</span></p>
                                                                            <Information infoKey={t('createOidcClient.publicKeyToolTip')} id='public_key_info' />
                                                                        </label>
                                                                        <pre id='edit_oidc_public_key' className="px-2 py-4 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline h-20 overflow-auto no-scrollbar">
                                                                            {formatPublicKey(oidcClientDetails.publicKey)}
                                                                        </pre>
                                                                    </div>
                                                                </div>
                                                                <div className="flex my-[1%]">
                                                                    <div className="flex flex-col w-full">
                                                                        <label id='edit_oidc_logo_url_label' className={`block text-dark-blue text-sm font-semibold mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('createOidcClient.logoUrl')}<span className="text-crimson-red mx-1">*</span></label>
                                                                        <input id="oidc_edit_enter_logo_url_input" value={oidcClientDetails.logoUri} onChange={(e) => handleLogoUrlChange(e.target.value)} placeholder={t('createOidcClient.logoUrlPlaceHolder')}
                                                                            className="h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar" />
                                                                        {invalidLogoUrl && <span id='edit_oidc_invalid_logo_url' className="text-sm text-crimson-red font-semibold">{invalidLogoUrl}</span>}
                                                                    </div>
                                                                </div>

                                                                <div className="flex flex-row justify-between space-x-4 my-[1%]">
                                                                    <div className="flex flex-col w-[48%]">
                                                                        <label id='edit_oidc_redirect_url_label' className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                                            {t('createOidcClient.redirectUrl')}<span className="text-crimson-red mx-1">*</span>
                                                                        </label>
                                                                        {(oidcClientDetails.redirectUris).map((url, index) => (
                                                                            <div key={index} className="flex w-full justify-between items-center h-10 px-2 py-2 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar focus:shadow-outline mb-2">
                                                                                <input
                                                                                    id={"oidc_edit_enter_redirect_url" + (index + 1)}
                                                                                    value={url}
                                                                                    onChange={(e) => onChangeRedirectUrl(index, e.target.value)}
                                                                                    placeholder={t('createOidcClient.redirectUrlPlaceHolder')}
                                                                                    className="w-[85%] focus:outline-none"
                                                                                />
                                                                                <div role='button' id={'oidc_edit_delete_redirect_url' + (index + 1)} className="flex flex-row items-center" onClick={() => onDeleteRedirectUrl(index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => onDeleteRedirectUrl(index))}>
                                                                                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="2"
                                                                                        stroke={oidcClientDetails.redirectUris.length > 1 ? '#1447b2' : '#969696'} className={`w-[18px] h-5 mr-1 ${oidcClientDetails.redirectUris.length > 1 ? 'cursor-pointer' : ''}`}>
                                                                                        <path strokeLinecap="round" strokeLinejoin="round"
                                                                                            d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
                                                                                    </svg>
                                                                                    <p className={`text-sm font-semibold ${oidcClientDetails.redirectUris.length > 1 ? 'text-[#1447b2] cursor-pointer' : 'text-[#969696]'}`}>
                                                                                        {t('createOidcClient.delete')}
                                                                                    </p>
                                                                                </div>
                                                                            </div>
                                                                        ))}
                                                                        {invalidRedirectUrl && <span id='edit_oidc_invalid_redirect_url' className="text-sm text-crimson-red font-semibold">{invalidRedirectUrl}</span>}
                                                                        {oidcClientDetails.redirectUris.length < 5 && (
                                                                            <div
                                                                                role="button"
                                                                                id="oidc_edit_add_new_redirect_url"
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
                                                                            selectedDropdownValue={oidcClientDetails.grantTypes[0]}
                                                                            styleSet={styles}
                                                                            id='oidc_edit_grant_type'>
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
                                                <button id="oidc_edit_undo_changes_btn" onClick={() => undoChanges()} className="mr-2 w-40 min-w-fit px-3 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold">{t('commons.undoChanges')}</button>
                                                <div className="flex flex-row space-x-3 w-full md:w-auto justify-end">
                                                    <button id="oidc_edit_cancel_btn" onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                                                    <button id="oidc_edit_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white'}`}>{t('requestPolicy.submit')}</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    : <Confirmation id='edit_oidc_client_confirmation' confirmationData={confirmationData} />
                                }
                            </>
                        )}
                    </div>
                </>
            )}
            <BlockerPrompt blocker={blocker} />
        </div >
    )
}

export default EditOidcClient;