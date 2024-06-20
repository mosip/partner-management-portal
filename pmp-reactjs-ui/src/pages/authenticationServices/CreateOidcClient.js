import { useState, useEffect } from "react";
import { useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import DropdownComponent from '../common/fields/DropdownComponent';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL } from '../../utils/AppUtils';
import backArrow from '../../svg/back_arrow.svg';
import info from '../../svg/info_icon.svg';
import { getPartnerManagerUrl, handleServiceErrors, getPartnerTypeDescription, createRequest, 
  moveToOidcClientsList, getGrantTypes } from '../../utils/AppUtils';
import { HttpService } from '../../services/HttpService';
import DropdownWithSearchComponent from "../common/fields/DropdownWithSearchComponent";
import LoadingIcon from "../common/LoadingIcon";
import ErrorMessage from "../common/ErrorMessage";
import { importJWK } from 'jose';

function CreateOidcClient() {
  const [oidcClientName, setOidcClientName] = useState("");
  const [publicKey, setPublicKey] = useState("");
  const [publicKeyInJson, setPublicKeyInJson] = useState(null);
  const [showPublicKeyToolTip, setShowPublicKeyToolTip] = useState(false);
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
  const [redirectUrls, setRedirectUrls] = useState(['']);
  const [grantTypes, setGrantTypes] = useState("authorization_code");
  const [grantTypesList, setGrantTypesList] = useState(['']);
  const [grantTypesDropdownData, setGrantTypesDropdownData] = useState([]);
  const [clientAuthMethods, setClientAuthMethods] = useState(['']);
  const [jsonError, setJsonError] = useState("");
  const [invalidLogoUrl, setInvalidLogoUrl] = useState("");
  const [invalidRedirectUrl, setInvalidRedirectUrl] = useState("");
  const [nameValidationError, setNameValidationError] = useState("");

  let blocker = useBlocker(
    ({ currentLocation, nextLocation }) =>
      ( partnerId !== "" ||
        oidcClientName !== "" ||
        publicKey !== "" ||
        showPublicKeyToolTip ||
        logoUrl !== "" ||
        policyId !== "" ||
        policyName !== "" ||
        partnerType !== "" ||
        policyGroupName !== ""
       ) &&
        currentLocation.pathname !== nextLocation.pathname
);

useEffect(() => {
    const handleBeforeUnload = (event) => {
        event.preventDefault();
    };
    window.addEventListener('beforeunload', handleBeforeUnload);

    return () => {
        window.removeEventListener('beforeunload', handleBeforeUnload);
    };
}, []);


  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

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
  }, [])

  useEffect(() => {
    const fetchData = async () => {
      try {
        setDataLoaded(false);
        const response = await HttpService.get(getPartnerManagerUrl('/partners/getAllApprovedAuthPartnerPolicies', process.env.NODE_ENV));

        if (response && response.data) {
          const responseData = response.data;

          if (responseData.response) {
            const resData = responseData.response;
            setPartnerData(resData);
            setPartnerIdDropdownData(createPartnerIdDropdownData('partnerId', resData));
          } else {
            handleServiceErrors(responseData, setErrorCode, setErrorMsg);
          }
        } else {
          setErrorMsg(t('createOidcClient.errorInResponse'));
        }
      } catch (err) {
        console.error('Error fetching data:', err);
      } finally {
        setDataLoaded(true);
      }
    };

    fetchData();
  }, [t]);

  const defaultGrantTypesList = (dataList) => {
    const list = [];
    dataList.forEach(item => {
      if(item === grantTypes) {
        list.push(item);
      }
    })
    setGrantTypesList(list);
  }


  const createPartnerIdDropdownData = (fieldName, dataList) => {
    let dataArr = [];
    dataList.forEach(item => {
      let alreadyAdded = false;
      dataArr.forEach(item1 => {
        if (item1.fieldValue === item[fieldName]) {
          alreadyAdded = true;
        }
      });
      if (!alreadyAdded) {
        dataArr.push({
          fieldCode: item[fieldName],
          fieldValue: item[fieldName]
        });
      }
    });
    return dataArr;
  }

  const createGrantTypesDropdownData =(dataList) => {
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
  }

  const createPoliciesDropdownData = (fieldName, dataList) => {
    let dataArr = [];
    dataList.forEach(item => {
      let alreadyAdded = false;
      dataArr.forEach(item1 => {
        if (item1.fieldValue === item[fieldName]) {
          alreadyAdded = true;
        }
      });
      if (!alreadyAdded) {
        dataArr.push({
          fieldCode: item[fieldName],
          fieldValue: item[fieldName],
          fieldDescription: item.policyDescription
        });
      }
    });
    return dataArr;
  };

  const onChangePartnerId = async (fieldName, selectedValue) => {
    setPartnerId(selectedValue);
    setPolicyName("");
    // Find the selected partner data
    const selectedPartner = partnerData.find(item => item.partnerId === selectedValue);
    if (selectedPartner) {
      setPartnerType(getPartnerTypeDescription(selectedPartner.partnerType, t));
      setPolicyGroupName(selectedPartner.policyGroupName);
      setPoliciesDropdownData(createPoliciesDropdownData('policyName', selectedPartner.activePolicies));
    }
  };

  const onChangePolicyName = (fieldName, selectedValue) => {
    const selectedPartner = partnerData.find(item => item.partnerId === partnerId);
    if (selectedPartner) {
      const selectedPolicy = selectedPartner.activePolicies.find(item => item.policyName === selectedValue);
      if (selectedPolicy) {
        setPolicyName(selectedValue);
        setPolicyId(selectedPolicy.policyId);
      }
    }
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

  const moveToHome = () => {
    navigate('/partnermanagement')
  };

  // Below code related to adding & deleting of Redirect URLs
  const onChangeRedirectUrl = (index, value) => {
    const urlPattern = /^(http|https):\/\/[^ "]+$/;
    const newRedirectUrls = [...redirectUrls];
    newRedirectUrls[index] = value;
    if (value.trim() === "") {
      setInvalidRedirectUrl("");
    } else if (value.length > 2000) {
      setInvalidRedirectUrl(t('createOidcClient.urlTooLong'));
    } else if (!urlPattern.test(value)) {
      setInvalidRedirectUrl(t('createOidcClient.invalidUrl'));
    } else if (newRedirectUrls.some((url, i) => url === value && i !== index)) {
      setInvalidRedirectUrl(t('createOidcClient.duplicateUrl'));
    } else {
      setInvalidRedirectUrl("");
    }
    setRedirectUrls(newRedirectUrls);
  };

  const addNewRedirectUrl = () => {
    setRedirectUrls([...redirectUrls, '']);
  };

  const onDeleteRedirectUrl = (index) => {
    if (redirectUrls.length > 1) {
      const newRedirectUrls = redirectUrls.filter((_, i) => i !== index);
      setRedirectUrls(newRedirectUrls);
      validateUrls(newRedirectUrls);
    }
  };

  const validateUrls = (urls) => {
    const hasDuplicate = urls.some((url, index) => urls.indexOf(url) !== index);
  
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
      const parsedValue = JSON.parse(value);
      // validate the JWK
      await importJWK(parsedValue);
      setPublicKeyInJson(parsedValue);
      setJsonError("");
    } catch (err) {
      setJsonError(t('createOidcClient.invalidJwkFormat'));
    }
  }

  const handleLogoUrlChange = (value) => {
    setLogoUrl(value);
    const urlPattern = /^(http|https):\/\/[^ "]+$/;
    if (value.trim() === "") {
      setInvalidLogoUrl("");
    } else if (value.length > 2000) {
        setInvalidLogoUrl(t('createOidcClient.urlTooLong'));
    } else if (!urlPattern.test(value)) {
        setInvalidLogoUrl(t('createOidcClient.invalidUrl'));
    } else {
        setInvalidLogoUrl("");
    }
  };

  const clickOnSubmit = async () => {
    setErrorCode("");
    setErrorMsg("");
    setDataLoaded(false);
    let request = createRequest({
      name: oidcClientName,
      policyId: policyId,
      publicKey: publicKeyInJson,
      authPartnerId: partnerId,
      logoUri: logoUrl,
      redirectUris: redirectUrls,
      grantTypes: grantTypesList,
      clientAuthMethods: clientAuthMethods,
      clientNameLangMap: {
        "eng": oidcClientName
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
              navigate('/partnermanagement/createOidcClientConfirmation');
              console.log(`Response data: ${resData.length}`);
          } else {
              handleServiceErrors(responseData, setErrorCode, setErrorMsg);
          }
      } else {
          setErrorMsg(t('createOidcClient.errorInCreateOIDC'));
      }
      setDataLoaded(true);
    } catch (err) {
        setErrorMsg(err);
        console.log("Error fetching data: ", err);
    }
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
    setNameValidationError("");
  };

  const isFormValid = () => {
    return partnerId && policyName && oidcClientName && publicKey && logoUrl && redirectUrls && grantTypes 
      && !jsonError && !invalidLogoUrl && !invalidRedirectUrl && !nameValidationError;
  };

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
                  <h1 className="font-semibold text-lg text-dark-blue">{t('createOidcClient.createOidcClient')}</h1>
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
                        <DropdownComponent
                          fieldName='partnerId'
                          dropdownDataList={partnerIdDropdownData}
                          onDropDownChangeEvent={onChangePartnerId}
                          fieldNameKey='requestPolicy.partnerId*'
                          placeHolderKey='createOidcClient.selectPartnerId'
                          selectedDropdownValue={partnerId}
                          styleSet={styles}
                          addInfoIcon={true}
                          infoKey='createOidcClient.partnerIdTooltip'>
                        </DropdownComponent>
                      </div>
                      <div className="flex flex-col w-[48%]">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red">*</span></label>
                        <button disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                          overflow-x-auto whitespace-nowrap no-scrollbar" type="button">
                        <span>{partnerType || t('requestPolicy.partnerType')}</span>
                          <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                          </svg>
                        </button>
                      </div>
                    </div>
                    <div className="flex flex-row justify-between space-x-4 my-2">
                      <div className="flex flex-col w-[48%]">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>{t('requestPolicy.policyGroup')}<span className="text-crimson-red">*</span></label>
                        <button disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-sm text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                          overflow-x-auto whitespace-nowrap no-scrollbar" type="button">
                        <span>{policyGroupName || t('requestPolicy.policyGroup')}</span>
                          <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
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
                          infoKey={t('createOidcClient.policyNameToolTip')}/>
                      </div>
                    </div>
                    <div className="flex my-2">
                      <div className="flex flex-col w-[562px]">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>{t('createOidcClient.name')}<span className="text-crimson-red">*</span></label>
                        <input value={oidcClientName} onChange={(e) => onChangeOidcClientName(e.target.value)}
                          className="h-10 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                          placeholder={t('createOidcClient.enterNameForOidcClient')} />
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
                            <div className={`z-20 -mt-2 w-[15%] max-h-[32%] overflow-y-auto absolute ${isLoginLanguageRTL ? "mr-[10%]" :"ml-[120px]"} shadow-lg bg-white border border-gray-300 p-3 rounded`}>
                              <p className="text-black text-sm">{t('createOidcClient.publicKeyToolTip')}</p>
                            </div>
                          )}
                        <textarea value={publicKey} onChange={(e) => handlePublicKeyChange(e.target.value)}
                          className="px-2 py-4 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                          placeholder={t('createOidcClient.publicKeyPlaceHolder')}>
                        </textarea>
                        {jsonError && <span className="text-sm text-crimson-red font-medium">{jsonError}</span>}
                      </div>
                    </div>
                    <div className="flex my-[1%]">
                      <div className="flex flex-col w-full">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1  ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>{t('createOidcClient.logoUrl')}<span className="text-crimson-red">*</span></label>
                        <input value={logoUrl} onChange={(e) => handleLogoUrlChange(e.target.value)}
                          className="h-10 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                          placeholder={t('createOidcClient.logoUrlPlaceHolder')} />
                        {invalidLogoUrl && <span className="text-sm text-crimson-red font-medium">{invalidLogoUrl}</span>}
                      </div>
                    </div>

                    <div className="flex flex-row justify-between space-x-4 my-[1%]">
                      <div className="flex flex-col w-[48%]">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>
                          {t('createOidcClient.redirectUrl')}<span className="text-crimson-red">*</span>
                        </label>
                        {redirectUrls.map((url, index) => (
                          <div key={index} className="flex w-full justify-between items-center h-10 px-2 py-2 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar focus:shadow-outline mb-2">
                            <input
                              value={url}
                              onChange={(e) => onChangeRedirectUrl(index, e.target.value)}
                              placeholder={t('createOidcClient.redirectUrlPlaceHolder')}
                              className="w-[85%] focus:outline-none"
                            />
                            <div className="flex flex-row items-center" onClick={() => onDeleteRedirectUrl(index)}>
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
                        {invalidRedirectUrl && <span className="text-sm text-crimson-red font-medium">{invalidRedirectUrl}</span>}
                        <p className="text-[#1447b2] font-bold text-xs cursor-pointer w-20" onClick={addNewRedirectUrl}>
                          <span className="text-lg text-center">+</span>{t('createOidcClient.addNew')}
                        </p>
                      </div>

                      <div className="flex flex-col w-[48%]">
                        <DropdownComponent
                          fieldName='grantTypes'
                          dropdownDataList={grantTypesDropdownData}
                          onDropDownChangeEvent={handleGrantTypesChange}
                          fieldNameKey='createOidcClient.grantTypes*'
                          selectedDropdownValue={grantTypes}
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
      {blocker.state === "blocked" ? (
        <div className="fixed min-w-36 h-fit inset-0 w-full flex flex- col justify-center z-50 font-inter">
          <div className="bg-white w-fit mx-auto rounded-xl justify-center shadow-lg p-2 pt-4 text-sm">
            <p className="text-center">{t('blockerMessage.description')}</p>
            <div className="pt-2">
              <button className="w-24 h-9 mx-2 my-1 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold" onClick={() => blocker.proceed()}>
              {t('blockerMessage.proceed')}
              </button>
              <button className="w-24 h-9 mx-2 my-1 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold" onClick={() => blocker.reset()}>
              {t('blockerMessage.cancel')}
              </button>
            </div>
          </div>
        </div>
      ) : null}
    </div>
  )
}

export default CreateOidcClient;