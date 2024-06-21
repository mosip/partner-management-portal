import React from "react";
import { useState, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../services/UserProfileService";
import { HttpService } from "../../services/HttpService";
import backArrow from "../../svg/back_arrow.svg";
import { moveToOidcClientsList, createRequest, isLangRTL, getPartnerManagerUrl, handleServiceErrors, getGrantTypes } from "../../utils/AppUtils";
import LoadingIcon from "../common/LoadingIcon";
import ErrorMessage from "../common/ErrorMessage";
import info from '../../svg/info_icon.svg';
import DropdownComponent from "../common/fields/DropdownComponent";

function EditOidcClient() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [showPublicKeyToolTip, setShowPublicKeyToolTip] = useState(false);
    const [invalidLogoUrl, setInvalidLogoUrl] = useState("");
    const [invalidRedirectUrl, setInvalidRedirectUrl] = useState("");
    const [showPartnerIdTooltip, setShowPartnerIdTooltip] = useState(false);
    const [showPolicyNameToolTip, setShowPolicyNameToolTip] = useState(false);
    const [nameValidationError, setNameValidationError] = useState("");
    const [grantTypesDropdownData, setGrantTypesDropdownData] = useState([]);
    const [oidcClientDetails, setOidcClientDetails] = useState({
        partnerId: '',
        policyGroupName: '',
        policyName: '',
        oidcClientName: '',
        publicKey: '',
        logoUri: '',
        redirectUris: [],
        grantTypes: [],
    });
    const [selectedClientDetails, setSelectedClientDetails] = useState({
        partnerId: '',
        policyGroupName: '',
        policyName: '',
        oidcClientName: '',
        publicKey: '',
        logoUri: '',
        redirectUris: [],
        grantTypes: [],
    });

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
        console.log(dataArr);
        return dataArr;
    }, [t]);

    useEffect(() => {
        const clientData = localStorage.getItem('selectedClientData');
        const config = localStorage.getItem('appConfig');
        if (config) {
            const configData = JSON.parse(config);
            const configGrantTypes = configData.grantTypes.split(',').map(item => item.trim());
            setGrantTypesDropdownData(createGrantTypesDropdownData(configGrantTypes));
        }
        if (clientData) {
            try {
                const selectedClient = JSON.parse(clientData);
                setOidcClientDetails(selectedClient);
                setSelectedClientDetails(selectedClient);
            } catch (error) {
                navigate('/partnermanagement/authenticationServices/oidcClientsList');
                console.error('Error in viewOidcClientDetails page :', error);
            }
        } else {
            navigate('/partnermanagement/authenticationServices/oidcClientsList');
        }
    }, [navigate, createGrantTypesDropdownData]);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const moveToHome = () => {
        navigate("/partnermanagement");
    };

    const onChangeOidcClientName = (value) => {
        const regexPattern = /^(?!\s+$)[a-zA-Z0-9-_ ,.&()]*$/;
        if (value.length > 256) {
          setNameValidationError(t('createOidcClient.nameTooLong'))
        } else if (!regexPattern.test(value)) {
          setNameValidationError(t('requestPolicy.specialCharNotAllowed'))
        } else {
          setNameValidationError("");
        }
        setOidcClientDetails(prevDetails => ({
            ...prevDetails,
            oidcClientName: value
        }));
    }

    const handleLogoUrlChange = (value) => {
        setOidcClientDetails(prevDetails => ({
            ...prevDetails,
            logoUri: value
        }));
        const urlPattern = /^(http|https):\/\/[^ "]+$/;
        if (value.trim() === "") {
            setInvalidLogoUrl("");
        } else if (value.length > 2048) {
            setInvalidLogoUrl(t('createOidcClient.urlTooLong'));
        } else if (!urlPattern.test(value)) {
            setInvalidLogoUrl(t('createOidcClient.invalidUrl'));
        } else {
            setInvalidLogoUrl("");
        }
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
        const urlPattern = /^(http|https):\/\/[^ "]+$/;
        const newRedirectUrls = [...oidcClientDetails.redirectUris];
        newRedirectUrls[index] = value;
        if (value.trim() === "") {
            setInvalidRedirectUrl("");
        } else if (value.length > 2048) {
            setInvalidRedirectUrl(t('createOidcClient.urlTooLong'));
        } else if (!urlPattern.test(value)) {
            setInvalidRedirectUrl(t('createOidcClient.invalidUrl'));
        } else if (newRedirectUrls.some((url, i) => url === value && i !== index)) {
            setInvalidRedirectUrl(t('createOidcClient.duplicateUrl'));
        } else {
            setInvalidRedirectUrl("");
        }
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

    const checkIfRedirectUrisIsUpdated = () => {
        const filteredOidcUris = oidcClientDetails.redirectUris.filter(uri => uri !== '');
        const filteredSelectedUris = selectedClientDetails.redirectUris.filter(uri => uri !== '');
        // Check if the lengths of the filtered arrays are different
        if (filteredOidcUris.length !== filteredSelectedUris.length) {
            return true;
        }
        for (let i = 0; i < filteredOidcUris.length; i++) {
            if (filteredOidcUris[i] !== filteredSelectedUris[i]) {
                return true;
            }
        }
        return false;
    }

    const isFormValid = () => {
        return (checkIfRedirectUrisIsUpdated() || (oidcClientDetails.grantTypes[0] !== selectedClientDetails.grantTypes[0]) || 
            (oidcClientDetails.logoUri !== selectedClientDetails.logoUri) || (oidcClientDetails.oidcClientName !== selectedClientDetails.oidcClientName)) 
            && !invalidLogoUrl && !invalidRedirectUrl && !nameValidationError;
    }

    const clearForm = () => {
        setNameValidationError("");
        setInvalidLogoUrl("");
        setInvalidRedirectUrl("");
        setErrorCode("");
        setErrorMsg("");
        setOidcClientDetails(selectedClientDetails);
    }

    const clickOnSubmit = async () => {
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        const request = createRequest({
            logoUri: oidcClientDetails.logoUri,
            redirectUris: oidcClientDetails.redirectUris,
            status: oidcClientDetails.status,
            grantTypes: oidcClientDetails.grantTypes,
            clientName: oidcClientDetails.oidcClientName,
            clientAuthMethods: oidcClientDetails.clientAuthMethods,
            clientNameLangMap: {
                "eng" : oidcClientDetails.oidcClientName
            }
        });
        console.log(request);
        try {
            const response = await HttpService.put(getPartnerManagerUrl(`/oauth/client/${oidcClientDetails.oidcClientId}`, process.env.NODE_ENV), request, {
              headers: {
                'Content-Type': 'application/json'
              }
            });
            const responseData = response.data;
            if (responseData && responseData.response) {
                setDataLoaded(true);
                navigate('/partnermanagement/authenticationServices/editOidcClientConfirmation');
            } else {
                setDataLoaded(true);
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        } catch (err) {
            setDataLoaded(true);
            setErrorMsg(err);
        }
    }

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !mb-1",
        dropdownButton: "!w-full !h-10 !rounded-md !text-base !text-left",
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
                        <div className={`flex justify-end max-w-7xl mb-5 absolute ${isLoginLanguageRTL? "left-0" : "right-2"}`}>
                            <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3">
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                            </div>
                        </div>
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between">
                            <div className="flex items-start gap-x-3">
                                <img src={backArrow} alt="" onClick={() => moveToOidcClientsList(navigate)} className={`mt-[5%] cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} />
                                <div className="flex-col">
                                <h1 className="font-semibold text-lg text-dark-blue">{t('editOidcClient.editOidcClient')}</h1>
                                <div className="flex space-x-1">
                                    <p onClick={() => moveToHome()} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                    {t('commons.home')} /
                                    </p>
                                    <p onClick={() => moveToOidcClientsList(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                    {t('authenticationServices.authenticationServices')}
                                    </p>
                                </div>
                                </div>
                            </div>
                            {/* <div className="flex items-center space-x-2 px-4 py-2 bg-snow-white border-2 border-[#1447B2] rounded-md text-sm text-[#1447B2] font-semibold opacity-md shadow-[#1447b2] cursor-pointer">
                                <img src={help_icon} className="h-4"/>
                                <p>{t('createOidcClient.help')}</p>
                            </div> */}
                        </div>
                        <div className="w-[100%] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                            <div className="px-[2.5%] py-[2%]">
                                <p className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                <form>
                                    <div className="flex flex-col">
                                        <div className="flex flex-row justify-between space-x-4 my-[1%]">
                                            <div className="flex flex-col w-[48%]">
                                                <label className={`flex text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>{t('requestPolicy.partnerId')}
                                                    <span className="text-crimson-red">*</span>
                                                    <img src={info} alt="" className= {`ml-1 cursor-pointer`} 
                                                        onMouseEnter={() => setShowPartnerIdTooltip(true)}
                                                        onMouseLeave={() => setShowPartnerIdTooltip(false)}>
                                                    </img>
                                                </label>
                                                {showPartnerIdTooltip && (
                                                    <div className={`z-20 p-4 -mt-[3.5%] w-[20%] max-h-[32%] overflow-y-auto absolute ${isLoginLanguageRTL?"mr-[9.5%]":"ml-[115px]"} shadow-lg bg-white border border-gray-300 rounded`}>
                                                        <p className="text-black text-sm">{t('createOidcClient.partnerIdTooltip')}</p>
                                                    </div>
                                                )}
                                                <button disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-nowrap no-scrollbar" type="button">
                                                    <span>{oidcClientDetails.partnerId}</span>
                                                    <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                    </svg>
                                                </button>
                                            </div>
                                            <div className="flex flex-col w-[48%]">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red">*</span></label>
                                                <button disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-nowrap no-scrollbar" type="button">
                                                    <span>{t("partnerTypes.authPartner")}</span>
                                                    <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                    </svg>
                                                </button>
                                            </div>
                                        </div>
                                        <div className="flex flex-row justify-between space-x-4 my-2">
                                            <div className="flex flex-col w-[48%]">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>{t('requestPolicy.policyGroup')}<span className="text-crimson-red">*</span></label>
                                                <button disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-nowrap no-scrollbar" type="button">
                                                    <span>{oidcClientDetails.policyGroupName}</span>
                                                    <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                    </svg>
                                                </button>
                                            </div>
                                            <div className="flex flex-col w-[48%]">
                                                <label className={`flex text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>{t('requestPolicy.policyName')}
                                                    <span className="text-crimson-red">*</span>
                                                    <img src={info} alt="" className= {`ml-1 cursor-pointer`} 
                                                        onMouseEnter={() => setShowPolicyNameToolTip(true)}
                                                        onMouseLeave={() => setShowPolicyNameToolTip(false)}>
                                                    </img>
                                                </label>
                                                {showPolicyNameToolTip && (
                                                    <div className={`z-20 p-4 -mt-[3.5%] w-[20%] max-h-[32%] overflow-y-auto absolute ${isLoginLanguageRTL?"mr-[9.5%]":"ml-[125px]"} shadow-lg bg-white border border-gray-300 rounded`}>
                                                        <p className="text-black text-sm">{t('createOidcClient.policyNameToolTip')}</p>
                                                    </div>
                                                )}
                                                <button disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-nowrap no-scrollbar" type="button">
                                                    <span>{oidcClientDetails.policyName}</span>
                                                    <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                    </svg>
                                                </button>
                                            </div>
                                        </div>
                                        <div className="flex my-2">
                                            <div className="flex flex-col w-[562px]">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>{t('createOidcClient.name')}<span className="text-crimson-red">*</span></label>
                                                <input value={oidcClientDetails.oidcClientName} onChange={(e) => onChangeOidcClientName(e.target.value)}
                                                    className="h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                                />
                                                {nameValidationError && <span className="text-sm text-crimson-red font-medium">{nameValidationError}</span>}
                                            </div>
                                        </div>
                                        <div className="flex my-[1%]">
                                            <div className="flex flex-col w-full">
                                                <label className={`flex space-x-1 items-center text-dark-blue text-sm font-semibold mb-1  ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>
                                                {t('createOidcClient.publicKey')}<span className="text-crimson-red">*</span>
                                                <img src={info} alt="" className={`${isLoginLanguageRTL ? "mr-2" :"ml-2"} cursor-pointer`} 
                                                    onMouseEnter={() => setShowPublicKeyToolTip(true)}
                                                    onMouseLeave={() => setShowPublicKeyToolTip(false)} />
                                                </label>
                                                {showPublicKeyToolTip &&
                                                (
                                                    <div className={`z-20 -mt-2 w-[15%] max-h-[32%] overflow-y-auto absolute ${isLoginLanguageRTL ? "mr-[10%]" :"ml-[115px]"} shadow-lg bg-white border border-gray-300 p-3 rounded`}>
                                                    <p className="text-black text-sm">{t('createOidcClient.publicKeyToolTip')}</p>
                                                    </div>
                                                )}
                                                <textarea value={oidcClientDetails.publicKey} readOnly
                                                    className="px-2 py-4 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-pre-wrap no-scrollbar">
                                                </textarea>
                                            </div>
                                        </div>
                                        <div className="flex my-[1%]">
                                            <div className="flex flex-col w-full">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1  ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>{t('createOidcClient.logoUrl')}<span className="text-crimson-red">*</span></label>
                                                <input value={oidcClientDetails.logoUri} onChange={(e) => handleLogoUrlChange(e.target.value)}
                                                className="h-10 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"/>
                                                {invalidLogoUrl && <span className="text-sm text-crimson-red font-medium">{invalidLogoUrl}</span>}
                                            </div>
                                        </div>
                                        <div className="flex flex-row justify-between space-x-4 my-[1%]">
                                            <div className="flex flex-col w-[48%]">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>
                                                    {t('createOidcClient.redirectUrl')}<span className="text-crimson-red">*</span>
                                                </label>
                                                {(oidcClientDetails.redirectUris).map((url, index) => (
                                                    <div key={index} className="flex w-full justify-between items-center h-10 px-2 py-2 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar focus:shadow-outline mb-2">
                                                        <input
                                                        value={url}
                                                        onChange={(e) => onChangeRedirectUrl(index, e.target.value)}
                                                        placeholder={t('createOidcClient.redirectUrlPlaceHolder')}
                                                        className="w-[85%] focus:outline-none"
                                                        />
                                                        <div className="flex flex-row items-center" onClick={() => onDeleteRedirectUrl(index)}>
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
                                                {invalidRedirectUrl && <span className="text-sm text-crimson-red font-medium">{invalidRedirectUrl}</span>}
                                                {oidcClientDetails.redirectUris.length < 5 && (
                                                    <p className="text-[#1447b2] font-bold text-xs" onClick={addNewRedirectUrl}>
                                                        <span className="text-lg text-center cursor-pointer">+</span>
                                                        <span className="cursor-pointer">{t('createOidcClient.addNew')}</span>
                                                    </p>
                                                )}
                                            </div>
                                            <div className="flex flex-col w-[48%]">
                                                <DropdownComponent
                                                    fieldName='grantTypes'
                                                    dropdownDataList={grantTypesDropdownData}
                                                    onDropDownChangeEvent={handleGrantTypesChange}
                                                    fieldNameKey='createOidcClient.grantTypes*'
                                                    selectedDropdownValue={oidcClientDetails.grantTypes[0]}
                                                    styleSet={styles}>
                                                </DropdownComponent>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="border bg-medium-gray" />
                            <div className="flex flex-row px-[3%] py-[2%] justify-between">
                                <button onClick={() => clearForm()} className="mr-2 w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold">{t('requestPolicy.clearForm')}</button>
                                <div className="flex flex-row space-x-3 w-full md:w-auto justify-end">
                                <button onClick={() => moveToOidcClientsList(navigate)} className={`${isLoginLanguageRTL ?"ml-2" :"mr-2"} w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                                <button disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ?"ml-2" :"mr-2"} w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t('requestPolicy.submit')}</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </>
            )}
        </div>
    )
}

export default EditOidcClient;