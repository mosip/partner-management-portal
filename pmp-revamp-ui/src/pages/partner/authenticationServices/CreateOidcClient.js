import { useState, useEffect, useCallback } from "react";
import { useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import DropdownComponent from '../../common/fields/DropdownComponent';
import { getUserProfile } from '../../../services/UserProfileService';
import {
  getPartnerManagerUrl, handleServiceErrors, getPartnerTypeDescription, createRequest,
  moveToOidcClientsList, getGrantTypes, getApprovedAuthPartners,
  isLangRTL, createDropdownData, validateUrl, getPartnerPolicyRequests,
  onPressEnterKey, trimAndReplace
} from '../../../utils/AppUtils';
import { HttpService } from '../../../services/HttpService';
import DropdownWithSearchComponent from "../../common/fields/DropdownWithSearchComponent";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import { importJWK } from 'jose';
import BlockerPrompt from "../../common/BlockerPrompt";
import Information from "../../common/fields/Information";
import Title from "../../common/Title";
import Confirmation from "../../common/Confirmation";
import JSON5 from 'json5';

function CreateOidcClient() {
  const [oidcClientName, setOidcClientName] = useState("");
  const [publicKey, setPublicKey] = useState("");
  const [publicKeyInJson, setPublicKeyInJson] = useState(null);
  const [logoUrl, setLogoUrl] = useState("");
  const [dataLoaded, setDataLoaded] = useState(true);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const { t } = useTranslation();
  const [partnerIdDropdownData, setPartnerIdDropdownData] = useState([]);
  const [policiesDropdownData, setPoliciesDropdownData] = useState([]);
  const [partnerId, setPartnerId] = useState("");
  const [policyId, setPolicyId] = useState("");
  const [policyName, setPolicyName] = useState("");
  const [partnerType, setPartnerType] = useState("");
  const [policyGroupName, setPolicyGroupName] = useState("");
  const [partnerData, setPartnerData] = useState([]);
  const [activePoliciesData, setActivePoliciesData] = useState([]);
  const [policyRequestsData, setPolicyRequestsData] = useState([]);
  const [redirectUrls, setRedirectUrls] = useState(['']);
  const [grantTypes, setGrantTypes] = useState("authorization_code");
  const [grantTypesList, setGrantTypesList] = useState(['']);
  const [grantTypesDropdownData, setGrantTypesDropdownData] = useState([]);
  const [clientAuthMethods, setClientAuthMethods] = useState(['']);
  const [jsonError, setJsonError] = useState("");
  const [invalidLogoUrl, setInvalidLogoUrl] = useState("");
  const [invalidRedirectUrl, setInvalidRedirectUrl] = useState("");
  const [createOidcClientSuccess, setCreateOidcClientSuccess] = useState(false);
  const [confirmationData, setConfirmationData] = useState({});
  const [isSubmitClicked, setIsSubmitClicked] = useState(false);

  const blocker = useBlocker(
    ({ currentLocation, nextLocation }) => {
      if (isSubmitClicked || createOidcClientSuccess) {
        setIsSubmitClicked(false);
        return false;
      }

      return (
        (partnerId !== "" ||
          oidcClientName !== "" ||
          publicKey !== "" ||
          logoUrl !== "" ||
          policyName !== "" ||
          redirectUrls.some(url => url !== "")
        ) &&
        currentLocation.pathname !== nextLocation.pathname
      );
    }
  );

  useEffect(() => {
    const shouldWarnBeforeUnload = () => {
      return partnerId !== "" ||
        oidcClientName !== "" ||
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
  }, [partnerId, oidcClientName, publicKey, logoUrl, policyId, policyName, redirectUrls, isSubmitClicked]);

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
    const config = localStorage.getItem('appConfig');
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
  }, []);

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

  const onChangeOidcClientName = (value) => {
    setOidcClientName(value);
  }

  const handleGrantTypesChange = (fieldName, selectedValue) => {
    setGrantTypes(selectedValue);
    const grantTypeValue = [''];
    grantTypeValue[0] = selectedValue
    setGrantTypesList(grantTypeValue);
  }


  const navigate = useNavigate();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

  // Below code related to adding & deleting of Redirect URLs
  const onChangeRedirectUrl = (index, value) => {
    const newRedirectUrls = [...redirectUrls];
    newRedirectUrls[index] = value;
    setInvalidRedirectUrl(validateUrl(index, value, 2048, newRedirectUrls, t));
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
    moveToOidcClientsList(navigate)
  }

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
      // validate the JSON
      if (Object.keys(parsedValue).length === 0) {
        throw new Error(); // Triggers the catch block
      }
      setPublicKeyInJson(parsedValue);
      setJsonError("");
    } catch {
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

  const clickOnSubmit = async () => {
    setIsSubmitClicked(true);
    setErrorCode("");
    setErrorMsg("");
    setDataLoaded(false);
    let request = createRequest({
      name: trimAndReplace(oidcClientName),
      policyId: policyId,
      publicKey: publicKeyInJson,
      authPartnerId: partnerId,
      logoUri: logoUrl,
      redirectUris: getRedirectUris(),
      grantTypes: grantTypesList,
      clientAuthMethods: clientAuthMethods,
      clientNameLangMap: {
        "eng": trimAndReplace(oidcClientName)
      }
    });
    console.log(request);
    try {
      const response = await HttpService.post(getPartnerManagerUrl(`/oauth/client`, process.env.NODE_ENV), request, {
        headers: {
          'Content-Type': 'application/json'
        }
      });
      if (response) {
        const responseData = response.data;
        if (responseData && responseData.response) {
          const resData = responseData.response;
          const requiredData = {
            title: "createOidcClient.createOidcClient",
            backUrl: "/partnermanagement/authentication-services/oidc-clients-list",
            header: "createOidcClient.requestSuccessHeader",
            description: "createOidcClient.requestSuccessMsg",
            subNavigation: "authenticationServices.authenticationServices",
          }
          setConfirmationData(requiredData);
          setCreateOidcClientSuccess(true);
          console.log(`Response data: ${resData.length}`);
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
      console.log("Error fetching data: ", err);
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
    setOidcClientName("");
    setPublicKey("");
    setLogoUrl("");
    setRedirectUrls(['']);
    setJsonError("");
    setInvalidLogoUrl("");
    setInvalidRedirectUrl("");
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
    return partnerId && policyName && oidcClientName.trim() && publicKey.trim() && logoUrl && redirectUrlsNotEmpty() && grantTypes
      && !jsonError && !invalidLogoUrl && !invalidRedirectUrl;
  };

  const styles = {
    outerDiv: "!ml-0 !mb-0",
    dropdownLabel: "!text-sm !mb-1",
    dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
    selectionBox: "!top-10"
  }

  const styleForTitle = {
    backArrowIcon: "!mt-[5%]"
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
          <div className="flex-col mt-5">
            <div className="flex justify-between">
              <Title title='createOidcClient.createOidcClient' subTitle='authenticationServices.authenticationServices' backLink='/partnermanagement/authentication-services/oidc-clients-list' />
            </div>
            {!createOidcClientSuccess ?
              <div className="w-[100%] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                <div className="px-[2.5%] py-[2%]">
                  <p className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red mx-1">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                  <form>
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
                          <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red mx-1">*</span></label>
                          <button disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
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
                          <label className={`block text-dark-blue text-sm font-semibold mb-1 mx-1`}>{t('requestPolicy.policyGroup')}<span className="text-crimson-red mx-1">*</span></label>
                          <button disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-sm text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
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
                      <div className="flex my-2">
                        <div className="flex flex-col w-[562px]">
                          <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('authenticationServices.oidcClientName')}<span className="text-crimson-red mx-1">*</span></label>
                          <input value={oidcClientName} onChange={(e) => onChangeOidcClientName(e.target.value)} maxLength={256}
                            className="h-10 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                            placeholder={t('createOidcClient.enterNameForOidcClient')} id="create_oidc_client_name" />
                        </div>
                      </div>
                      <div className="flex my-[1%]">
                        <div className="flex flex-col w-full">
                          <label className={`flex items-center text-dark-blue text-sm mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                            <p className={`font-semibold`}>{t('createOidcClient.publicKey')}<span className={`text-crimson-red mx-1`}>*</span></p>
                            <Information infoKey={t('createOidcClient.publicKeyToolTip')} id='public_key_info' />
                          </label>
                          <textarea id="create_oidc_public_key" value={publicKey} onChange={(e) => handlePublicKeyChange(e.target.value)}
                            className="px-2 py-4 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                            placeholder={t('createOidcClient.publicKeyPlaceHolder')}>
                          </textarea>
                          {jsonError && <span className="text-sm text-crimson-red font-semibold">{jsonError}</span>}
                        </div>
                      </div>
                      <div className="flex my-[1%]">
                        <div className="flex flex-col w-full">
                          <label className={`block text-dark-blue text-sm font-semibold mb-1  ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('createOidcClient.logoUrl')}<span className="text-crimson-red mx-1">*</span></label>
                          <input id="create_oidc_logo_url" value={logoUrl} onChange={(e) => handleLogoUrlChange(e.target.value)}
                            className="h-10 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                            placeholder={t('createOidcClient.logoUrlPlaceHolder')} />
                          {invalidLogoUrl && <span className="text-sm text-crimson-red font-semibold">{invalidLogoUrl}</span>}
                        </div>
                      </div>

                      <div className="flex flex-row justify-between space-x-4 my-[1%]">
                        <div className="flex flex-col w-[48%]">
                          <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                            {t('createOidcClient.redirectUrl')}<span className="text-crimson-red mx-1">*</span>
                          </label>
                          {redirectUrls.map((url, index) => (
                            <div key={index} className="flex w-full justify-between items-center h-10 px-2 py-2 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar focus:shadow-outline mb-2">
                              <input
                                value={url}
                                onChange={(e) => onChangeRedirectUrl(index, e.target.value)}
                                placeholder={t('createOidcClient.redirectUrlPlaceHolder')}
                                className="w-[85%] focus:outline-none"
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
                          {invalidRedirectUrl && <span className="text-sm text-crimson-red font-semibold">{invalidRedirectUrl}</span>}
                          {redirectUrls.length < 5 && (
                            <div
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
                  </form>
                </div>
                <div className="border bg-medium-gray" />
                <div className="flex flex-row px-[3%] py-[2%] justify-between">
                  <button id="create_oidc_clear_form" onClick={() => clearForm()} className="mr-2 w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold">{t('requestPolicy.clearForm')}</button>
                  <div className="flex flex-row space-x-3 w-full md:w-auto justify-end">
                    <button id="create_oidc_cancel_btn" onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                    <button id="create_oidc_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t('requestPolicy.submit')}</button>
                  </div>
                </div>
              </div>
              :
              <Confirmation confirmationData={confirmationData} />
            }
          </div>
        </>
      )}
      <BlockerPrompt blocker={blocker} />
    </div>
  )
}

export default CreateOidcClient;