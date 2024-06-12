import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import DropdownComponent from '../common/fields/DropdownComponent';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL } from '../../utils/AppUtils';
import backArrow from '../../svg/back_arrow.svg';
import info from '../../svg/info_icon.svg';
import { getPartnerManagerUrl, handleServiceErrors, getPartnerTypeDescription, createRequest, 
  handleMouseClickForDropdown, moveToOidcClientsList } from '../../utils/AppUtils';
import { HttpService } from '../../services/HttpService';
import DropdownWithSearchComponent from "../common/fields/DropdownWithSearchComponent";
import LoadingIcon from "../common/LoadingIcon";
import ErrorMessage from "../common/ErrorMessage";

function CreateOidcClient() {
  const [partnerComments, setPartnerComments] = useState("");
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
  const [grantTypes, setGrantTypes] = useState(['']);
  const [validationError, setValidationError] = useState("");
  const [jsonError, setJsonError] = useState("");
  const [invalidLogoUrl, setInvalidLogoUrl] = useState("");
  const [invalidRedirectUrl, setInvalidRedirectUrl] = useState("");
  const tooltipRef = useRef(null);

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  useEffect(() => {
    const clickOutSideDropdown = handleMouseClickForDropdown(tooltipRef, () => setShowPublicKeyToolTip(false));
    return clickOutSideDropdown;
}, [tooltipRef]);

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
          setErrorMsg(t('policies.errorInPoliciesList'));
        }
      } catch (err) {
        console.error('Error fetching data:', err);
      } finally {
        setDataLoaded(true);
      }
    };

    fetchData();
  }, [t]);


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


  const navigate = useNavigate();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

  const moveToHome = () => {
    navigate('/partnermanagement')
  };

  // Below code related to adding & deleting of Redirect URLs
  const onChangeRedirectUrl = (index, value) => {
    const urlPattern = /^(http|https):\/\/([^ "]+)?$/;
    const newRedirectUrls = [...redirectUrls];
    newRedirectUrls[index] = value;
    setRedirectUrls(newRedirectUrls);
    if (value.trim() === "" || urlPattern.test(value)) {
      setInvalidRedirectUrl("");
    } else {
      setInvalidRedirectUrl(t('createOidcClient.invalidUrl'));
    }
  };
  const addNewRedirectUrl = () => {
    setRedirectUrls([...redirectUrls, '']);
  };
  const onDeleteRedirectUrl = (index) => {
    const newRedirectUrls = redirectUrls.filter((_, i) => i !== index);
    setRedirectUrls(newRedirectUrls);
  };

  // Below code related to addind & deleting of Grant Types
  const onChangeGrantType = (index, value) => {
    const newGrantTypes = [...grantTypes];
    newGrantTypes[index] = value;
    setGrantTypes(newGrantTypes);
  }
  const addNewGrantTypes = () => {
    setGrantTypes([...grantTypes, '']);
  }
  const onDeleteGrantTypes = (index) => {
    const newGrantTypes = grantTypes.filter((_, i) => i !== index);
    setGrantTypes(newGrantTypes);
  }

  const handlePublicKeyChange = (value) => {
    setPublicKey(value);
    if (value.trim() === "") {
      setJsonError("");
      setPublicKeyInJson(null);
      return;
    }
    try {
      const parsedValue = JSON.parse(value);
      setPublicKeyInJson(parsedValue);
      setJsonError("");
    } catch (err) {
      setJsonError(t('createOidcClient.invalidJwkFormat'));
    }
  }

  const handleLogoUrlChange = (value) => {
    setLogoUrl(value);
    const urlPattern = /^(http|https):\/\/([^ "]+)?$/;
    if (value.trim() === "" || urlPattern.test(value)) {
      setInvalidLogoUrl("");
    } else {
      setInvalidLogoUrl(t('createOidcClient.invalidUrl'));
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
      grantTypes: grantTypes,
      clientAuthMethods: ["private_key_jwt"],
      clientNameLangMap: {
        "eng": oidcClientName,
        "fra": oidcClientName,
        "ara": oidcClientName
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
    setPartnerId("");
    setPartnerType("");
    setPolicyGroupName("");
    setPoliciesDropdownData([]);
    setPolicyName("");
    setOidcClientName("");
    setPublicKey("");
    setLogoUrl("");
    setRedirectUrls(['']);
    setGrantTypes(['']);
    setPartnerComments("");
    setJsonError("");
    setInvalidLogoUrl("");
    setInvalidRedirectUrl("");
    setValidationError("");
  };

  const validateComments = (comments) => {
    let error = "";
    const maxLength = 500;
    const regexPattern = /^[a-zA-Z0-9-_ ,.]*$/;
    if (comments.length > maxLength) {
        error = t('requestPolicy.commentTooLong');
    } else if (!regexPattern.test(comments)) {
        error = t('requestPolicy.specialCharNotAllowed');
    }
    setValidationError(error);
    return error === "";
  };

  const handleCommentChange = (e) => {
      const { value } = e.target;
      if (validateComments(value)) {
          setValidationError("");
          setPartnerComments(value);
      }
  };

  const isFormValid = () => {
    return partnerId && policyName && oidcClientName && publicKey && logoUrl && redirectUrls && grantTypes 
      && partnerComments && !jsonError && !invalidLogoUrl && !invalidRedirectUrl;
  };

  const styles = {
    outerDiv: "!ml-0 !mb-0",
    dropdownLabel: "!text-base !mb-1",
    dropdownButton: "!w-full !h-12 !rounded-md !text-lg !text-left",
    selectionBox: "!top-12"
  }

  return (
    <div className={`mt-5 w-[100%] ${isLoginLanguageRTL ? "mr-32 ml-5" : "ml-32 mr-5"} overflow-x-scroll font-inter`}>
      {!dataLoaded && (
          <LoadingIcon></LoadingIcon>
      )}
      {dataLoaded && (
        <>
          {errorMsg && (
              <div className={`flex justify-end max-w-7xl absolute ${isLoginLanguageRTL? "left-0" : "right-0"}`}>
                  <div className="flex justify-between items-center max-w-[400px] min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3">
                      <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                  </div>
              </div>
          )}
          <div className="flex-col">
            <div className="flex justify-between">
              <div className="flex items-start gap-x-3">
                <img src={backArrow} alt="" onClick={() => moveToOidcClientsList(navigate)} className={`mt-[5%] cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} />
                <div className="flex-col">
                  <h1 className="font-semibold text-xl text-dark-blue">{t('createOidcClient.createOidcClient')}</h1>
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
              <div className="p-[2.5%]">
                <p className="text-lg text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
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
                        <label className="block text-dark-blue text-base font-semibold mb-1">{t('requestPolicy.partnerType')}<span className="text-crimson-red">*</span></label>
                        <button disabled className="flex items-center justify-between w-full h-12 px-2 py-2 border border-[#C1C1C1] rounded-md text-lg text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                          overflow-x-auto whitespace-nowrap no-scrollbar" type="button">
                        <span>{partnerType || t('requestPolicy.partnerType')}</span>
                          <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                          </svg>
                        </button>
                      </div>
                    </div>
                    <div className="flex flex-row justify-between space-x-4 my-[1%]">
                      <div className="flex flex-col w-[48%]">
                        <label className="block text-dark-blue text-base font-semibold mb-1">{t('requestPolicy.policyGroup')}<span className="text-crimson-red">*</span></label>
                        <button disabled className="flex items-center justify-between w-full h-12 px-2 py-2 border border-[#C1C1C1] rounded-md text-lg text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
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
                    <div className="flex my-[1%]">
                      <div className="flex flex-col w-[562px]">
                        <label className="block text-dark-blue text-base font-semibold mb-1">{t('createOidcClient.name')}<span className="text-crimson-red">*</span></label>
                        <input value={oidcClientName} onChange={(e) => setOidcClientName(e.target.value)}
                          className="h-12 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                          placeholder={t('createOidcClient.enterNameForOidcClient')} />
                      </div>
                    </div>
                    <div className="flex my-[1%]">
                      <div className="flex flex-col w-full">
                        <label className="flex space-x-1 items-center text-dark-blue text-base font-semibold mb-1">
                          {t('createOidcClient.publicKey')}<span className="text-crimson-red">*</span>
                          <img src={info} alt="" className={`${isLoginLanguageRTL ? "mr-2" :"ml-2"} cursor-pointer`} onClick={() => setShowPublicKeyToolTip(!showPublicKeyToolTip)} />
                        </label>
                        {showPublicKeyToolTip &&
                          (
                            <div ref={tooltipRef} className={`z-20 w-[24%] max-h-[32%] overflow-y-auto absolute ${isLoginLanguageRTL ? "mr-[10%]" :"ml-[8%]"} shadow-lg bg-white border border-gray-300 p-3 rounded`}>
                              <p className="text-black text-sm">{t('createOidcClient.publicKeyToolTip')}</p>
                            </div>
                          )}
                        <textarea value={publicKey} onChange={(e) => handlePublicKeyChange(e.target.value)}
                          className="h-14 px-2 py-4 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                          placeholder={t('createOidcClient.publicKeyPlaceHolder')}>
                        </textarea>
                        {jsonError && <span className="text-sm text-crimson-red font-medium">{jsonError}</span>}
                      </div>
                    </div>
                    <div className="flex my-[1%]">
                      <div className="flex flex-col w-full">
                        <label className="block text-dark-blue text-base font-semibold mb-1">{t('createOidcClient.logoUrl')}<span className="text-crimson-red">*</span></label>
                        <input value={logoUrl} onChange={(e) => handleLogoUrlChange(e.target.value)}
                          className="h-12 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                          placeholder={t('createOidcClient.logoUrlPlaceHolder')} />
                        {invalidLogoUrl && <span className="text-sm text-crimson-red font-medium">{invalidLogoUrl}</span>}
                      </div>
                    </div>

                    <div className="flex flex-row justify-between space-x-4 my-[1%]">
                      <div className="flex flex-col w-[48%]">
                        <label className="block text-dark-blue text-base font-semibold mb-1">
                          {t('createOidcClient.redirectUrl')}<span className="text-crimson-red">*</span>
                        </label>
                        {redirectUrls.map((url, index) => (
                          <div key={index} className="flex w-full justify-between items-center h-11 px-2 py-2 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar focus:shadow-outline mb-2">
                            <input
                              value={url}
                              onChange={(e) => onChangeRedirectUrl(index, e.target.value)}
                              placeholder={t('createOidcClient.redirectUrlPlaceHolder')}
                              className="w-[85%] focus:outline-none"
                            />
                            {index > 0 && (
                              <p onClick={() => onDeleteRedirectUrl(index)} className="text-sm text-[#1447b2] font-semibold cursor-pointer">
                                {t('createOidcClient.delete')}
                              </p>
                            )}
                          </div>
                        ))}
                        {invalidRedirectUrl && <span className="text-sm text-crimson-red font-medium">{invalidRedirectUrl}</span>}
                        <p type="button" className="text-[#1447b2] font-bold text-xs cursor-pointer" onClick={addNewRedirectUrl}>
                          <span className="text-lg text-center">+</span>{t('createOidcClient.addNew')}
                        </p>
                      </div>

                      <div className="flex flex-col w-[48%]">
                        <label className="block text-dark-blue text-base font-semibold mb-1">{t('createOidcClient.grantTypes')}<span className="text-crimson-red">*</span></label>
                        {grantTypes.map((grantType, index) => (
                          <div key={index} className="flex w-full justify-between items-center h-11 px-2 py-2 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar focus:shadow-outline mb-2">
                            <input
                              value={grantType}
                              onChange={(e) => onChangeGrantType(index, e.target.value)}
                              placeholder={t('createOidcClient.enterGrantTypes')}
                              className="w-[85%] focus:outline-none"
                            />
                            {index > 0 && (
                              <p onClick={() => onDeleteGrantTypes(index)} className="text-sm text-[#1447b2] font-semibold cursor-pointer">
                                {t('createOidcClient.delete')}
                              </p>
                            )}
                          </div>
                        ))}
                        <p type="button" onClick={() => addNewGrantTypes()} className="text-[#1447b2] font-bold text-xs cursor-pointer">
                          <span className="text-lg text-center">+</span>{t('createOidcClient.addNew')}
                        </p>
                      </div>
                    </div>

                    <div className="flex my-[1%]">
                      <div className="flex flex-col w-full">
                        <label className="block text-dark-blue text-base font-semibold mb-1">{t('requestPolicy.comments')}<span className="text-crimson-red">*</span></label>
                        <textarea value={partnerComments} onChange={(e) => handleCommentChange(e)}
                          className="w-full h-12 px-2 py-2 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                          placeholder={t('createOidcClient.commentBoxDesc')}>
                        </textarea>
                        {validationError && <span className="text-sm text-crimson-red font-medium">{validationError}</span>}
                      </div>
                    </div>
                  </div>
                </form>
              </div>
              <div className="border bg-medium-gray" />
              <div className="border bg-medium-gray" />
              <div className="flex flex-row px-[3%] py-[2%] justify-between">
                <button onClick={() => clearForm()} className="mr-2 w-40 h-12 border-[#1447B2] border rounded-md bg-white text-tory-blue text-base font-semibold">{t('requestPolicy.clearForm')}</button>
                <div className="flex flex-row space-x-3 w-full md:w-auto justify-end">
                  <button onClick={() => moveToOidcClientsList(navigate)} className={`${isLoginLanguageRTL ?"ml-2" :"mr-2"} w-40 h-12 border-[#1447B2] border rounded-md bg-white text-tory-blue text-base font-semibold`}>{t('requestPolicy.cancel')}</button>
                  <button disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ?"ml-2" :"mr-2"} w-40 h-12 border-[#1447B2] border rounded-md text-base font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t('requestPolicy.submit')}</button>
                </div>
              </div>
            </div>
          </div>
        </>  
      )}
    </div>
  )
}

export default CreateOidcClient;