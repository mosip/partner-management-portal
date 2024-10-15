import React from "react";
import { useState, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { HttpService } from "../../../services/HttpService";
import { moveToOidcClientsList, createRequest, isLangRTL, getPartnerManagerUrl, handleServiceErrors, getGrantTypes, validateUrl, onPressEnterKey, trimAndReplace } from "../../../utils/AppUtils";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import DropdownComponent from "../../common/fields/DropdownComponent";
import Information from "../../common/fields/Information";
import Title from "../../common/Title";
import Confirmation from "../../common/Confirmation";

function EditOidcClient() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [invalidLogoUrl, setInvalidLogoUrl] = useState("");
    const [invalidRedirectUrl, setInvalidRedirectUrl] = useState("");
    const [grantTypesDropdownData, setGrantTypesDropdownData] = useState([]);
    const [editOidcClientSuccess, setEditOidcClientSuccess] = useState(false);
    const [confirmationData, setConfirmationData] = useState({});

    const [oidcClientDetails, setOidcClientDetails] = useState({
        partnerId: '',
        policyGroupName: '',
        policyName: '',
        clientName: '',
        publicKey: '',
        logoUri: '',
        redirectUris: [],
        grantTypes: [],
    });
    const [selectedClientDetails, setSelectedClientDetails] = useState({
        partnerId: '',
        policyGroupName: '',
        policyName: '',
        clientName: '',
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

    const onChangeOidcClientName = (value) => {
        setOidcClientDetails(prevDetails => ({
            ...prevDetails,
            clientName: value
        }));
    }

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

    const checkIfRedirectUrisIsUpdated = () => {
        const oidcUris = oidcClientDetails.redirectUris.filter(uri => uri !== '');
        const selectedUris = selectedClientDetails.redirectUris.filter(uri => uri !== '');
        // Check if the lengths of the filtered arrays are different
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
    const isRedirectUriNotEmpty = () => {
        const filteredOidcUris = oidcClientDetails.redirectUris.filter(uri => uri !== '');
        if (filteredOidcUris.length === 0) {
            return false;
        }
        return true;
    }

    const isFormValid = () => {
        return (checkIfRedirectUrisIsUpdated() ||
            (oidcClientDetails.grantTypes[0] !== selectedClientDetails.grantTypes[0]) ||
            (oidcClientDetails.logoUri !== selectedClientDetails.logoUri) ||
            (oidcClientDetails.clientName.trim() !== selectedClientDetails.clientName))
            && oidcClientDetails.clientName.trim() !== "" && oidcClientDetails.logoUri !== "" && isRedirectUriNotEmpty()
            && !invalidLogoUrl && !invalidRedirectUrl;
    }

    const clearForm = () => {
        setInvalidLogoUrl("");
        setInvalidRedirectUrl("");
        setErrorCode("");
        setErrorMsg("");
        setOidcClientDetails(selectedClientDetails);
    }

    const getRedirectUris = () => {
        const uriList = oidcClientDetails.redirectUris.filter(uri => uri !== '');
        return uriList;
    }

    const clickOnSubmit = async () => {
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        const request = createRequest({
            logoUri: oidcClientDetails.logoUri,
            redirectUris: getRedirectUris(),
            status: oidcClientDetails.status,
            grantTypes: oidcClientDetails.grantTypes,
            clientName: trimAndReplace(oidcClientDetails.clientName),
            clientAuthMethods: oidcClientDetails.clientAuthMethods,
            clientNameLangMap: {
                "eng": trimAndReplace(oidcClientDetails.clientName)
            }
        });
        console.log(request);
        try {
            const response = await HttpService.put(getPartnerManagerUrl(`/oauth/client/${oidcClientDetails.clientId}`, process.env.NODE_ENV), request, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const responseData = response.data;
            if (responseData && responseData.response) {
                setDataLoaded(true);
                const requireData = {
                    title: "editOidcClient.editOidcClient",
                    backUrl: "/partnermanagement/authenticationServices/oidcClientsList",
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
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between">
                            <Title title='editOidcClient.editOidcClient' subTitle='authenticationServices.authenticationServices' backLink='/partnermanagement/authenticationServices/oidcClientsList' ></Title>
                        </div>
                        {!editOidcClientSuccess ?
                            <div className="w-[100%] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                                <div className="px-[2.5%] py-[2%]">
                                    <p className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                    <form>
                                        <div className="flex flex-col">
                                            <div className="flex flex-row justify-between space-x-4 my-[1%]">
                                                <div className="flex flex-col w-[48%]">
                                                    <label className={`flex text-dark-blue items-center text-sm mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                        <p className={`font-semibold`}>{t('requestPolicy.partnerId')}<span className={`text-crimson-red mx-1`}>*</span></p>
                                                        <Information infoKey={t('createOidcClient.partnerIdTooltip')} id='partner_id_info'/>
                                                    </label>
                                                    <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                 overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                        <span className="w-full break-all break-normal break-words text-wrap text-start">{oidcClientDetails.partnerId}</span>
                                                        <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                        </svg>
                                                    </button>
                                                </div>
                                                <div className="flex flex-col w-[48%]">
                                                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                        {t('requestPolicy.partnerType')}<span className="text-crimson-red mx-1">*</span>
                                                    </label>
                                                    <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                 overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                        <span className="w-full break-all break-normal break-words text-wrap text-start">{t("partnerTypes.authPartner")}</span>
                                                        <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                        </svg>
                                                    </button>
                                                </div>
                                            </div>
                                            <div className="flex flex-row justify-between space-x-4 my-2">
                                                <div className="flex flex-col w-[48%]">
                                                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.policyGroup')}<span className="text-crimson-red mx-1">*</span></label>
                                                    <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                 overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                        <span className="w-full break-all break-normal break-words text-wrap text-start">{oidcClientDetails.policyGroupName}</span>
                                                        <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                        </svg>
                                                    </button>
                                                </div>
                                                <div className="flex flex-col w-[48%]">
                                                    <label className={`flex text-dark-blue items-center text-sm mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                        <p className={`font-semibold`}>{t('requestPolicy.policyName')}<span className={`text-crimson-red mx-1`}>*</span></p>
                                                        <Information infoKey={t('createOidcClient.policyNameToolTip')} id='policy_name_info'/>
                                                    </label>
                                                    <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                 overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                        <span className="w-full break-all break-normal break-words text-wrap text-start">{oidcClientDetails.policyName}</span>
                                                        <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                        </svg>
                                                    </button>
                                                </div>
                                            </div>
                                            <div className="flex my-2">
                                                <div className="flex flex-col w-[562px]">
                                                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('authenticationServices.oidcClientName')}<span className="text-crimson-red mx-1">*</span></label>
                                                    <input id="oidc_edit_enter_client_name_input" value={oidcClientDetails.clientName} onChange={(e) => onChangeOidcClientName(e.target.value)} maxLength={256} placeholder={t('createOidcClient.enterNameForOidcClient')}
                                                        className="h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-normal no-scrollbar"
                                                    />
                                                </div>
                                            </div>
                                            <div className="flex my-[1%]">
                                                <div className="flex flex-col w-full">
                                                    <label className={`flex space-x-1 items-center text-dark-blue text-sm mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                        <p className={`font-semibold`}>{t('createOidcClient.publicKey')}<span className={`text-crimson-red ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>*</span></p>
                                                        <Information infoKey={t('createOidcClient.publicKeyToolTip')} id='public_key_info'/>
                                                    </label>
                                                    <textarea value={oidcClientDetails.publicKey} readOnly
                                                        className="px-2 py-4 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-pre-wrap no-scrollbar">
                                                    </textarea>
                                                </div>
                                            </div>
                                            <div className="flex my-[1%]">
                                                <div className="flex flex-col w-full">
                                                    <label className={`block text-dark-blue text-sm font-semibold mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('createOidcClient.logoUrl')}<span className="text-crimson-red mx-1">*</span></label>
                                                    <input id="oidc_edit_enter_logo_url_input" value={oidcClientDetails.logoUri} onChange={(e) => handleLogoUrlChange(e.target.value)} placeholder={t('createOidcClient.logoUrlPlaceHolder')}
                                                        className="h-10 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-normal no-scrollbar" />
                                                    {invalidLogoUrl && <span className="text-sm text-crimson-red font-semibold">{invalidLogoUrl}</span>}
                                                </div>
                                            </div>
                                            <div className="flex flex-row justify-between space-x-4 my-[1%]">
                                                <div className="flex flex-col w-[48%]">
                                                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                                                        {t('createOidcClient.redirectUrl')}<span className="text-crimson-red mx-1">*</span>
                                                    </label>
                                                    {(oidcClientDetails.redirectUris).map((url, index) => (
                                                        <div key={index} className="flex w-full justify-between items-center h-10 px-2 py-2 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-normal no-scrollbar focus:shadow-outline mb-2">
                                                            <input
                                                                id={"oidc_edit_enter_redirect_url" + (index +1)}
                                                                value={url}
                                                                onChange={(e) => onChangeRedirectUrl(index, e.target.value)}
                                                                placeholder={t('createOidcClient.redirectUrlPlaceHolder')}
                                                                className="w-[85%] focus:outline-none"
                                                            />
                                                            <div id={'oidc_edit_delete_redirect_url' + (index + 1)} className="flex flex-row items-center" onClick={() => onDeleteRedirectUrl(index)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => onDeleteRedirectUrl(index))}>
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
                                                    {invalidRedirectUrl && <span className="text-sm text-crimson-red font-semibold">{invalidRedirectUrl}</span>}
                                                    {oidcClientDetails.redirectUris.length < 5 && (
                                                        <p id="oidc_edit_add_new_redirect_url" className="text-[#1447b2] font-bold text-xs w-fit" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, addNewRedirectUrl)}>
                                                            <span onClick={addNewRedirectUrl} className="text-lg text-center cursor-pointer">+</span>
                                                            <span onClick={addNewRedirectUrl} className="cursor-pointer">{t('createOidcClient.addNew')}</span>
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
                                                        styleSet={styles}
                                                        id='oidc_edit_grant_type'>
                                                    </DropdownComponent>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div className="border bg-medium-gray" />
                                <div className="flex flex-row px-[3%] py-[2%] justify-between">
                                    <button id="oidc_edit_clear_form_btn" onClick={() => clearForm()} className="mr-2 w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold">{t('requestPolicy.clearForm')}</button>
                                    <div className="flex flex-row space-x-3 w-full md:w-auto justify-end">
                                        <button id="oidc_edit_cancel_btn" onClick={() => moveToOidcClientsList(navigate)} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                                        <button id="oidc_edit_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t('requestPolicy.submit')}</button>
                                    </div>
                                </div>
                            </div>
                            : <Confirmation confirmationData={confirmationData} />
                        }
                    </div>
                </>
            )
            }
        </div >
    )
}

export default EditOidcClient;