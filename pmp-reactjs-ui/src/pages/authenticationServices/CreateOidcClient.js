import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import backArrow from '../../svg/back_arrow.svg';
import info from '../../svg/info_icon.svg';
import DropdownComponent from '../common/fields/DropdownComponent';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL } from '../../utils/AppUtils';

function CreateOidcClient() {
  const [partnerComments, setPartnerComments] = useState("");
  const [oidcClientName, setOidcClientName] = useState("");
  const [publicKey, setPublicKey] = useState("");
  const [showPublicKeyToolTip, setShowPublicKeyToolTip] = useState(false);
  const [loginUrl, setLoginUrl] = useState("");
  const [redirectUrls, setRedirectUrls] = useState([]);
  const [logoUrl, setLogoUrl] = useState("");
  const [typeOfGrants, setTypeOfGrants] = useState([]);
  const [grantTypes, setGrantTypes] = useState("");

  const navigate = useNavigate();
  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

  const count = 0;

  const moveToHome = () => {
    navigate('/partnermanagement')
  };

  const moveToAuthenticationServices = () => {
    navigate('/partnermanagement/authenticationServices/oidcClientsList');
  };

  // Below code related to adding & deleting of Redirect URLs

  const addRedirectUrl = () => {
    const updatedRedirectUrls = [...redirectUrls, []];
    setRedirectUrls(updatedRedirectUrls);
    console.log(updatedRedirectUrls);
  };
  const handleRedirectUrlChange = (event, i) => {
    const inputRedirectUrls = [...redirectUrls];
    inputRedirectUrls[i] = event.target.value;
    setRedirectUrls(inputRedirectUrls);
  };
  const deleteLogoUrl = (i) => {
    const filteredRedirectUrls = [...redirectUrls];
    filteredRedirectUrls.splice(i, 1);
    setRedirectUrls(filteredRedirectUrls);
  };

  // Below code related to addind & deleting of Grant Types

  const addTypeOfGrants = () => {
    const updatedtypeOfGrants = [...typeOfGrants, []];
    setTypeOfGrants(updatedtypeOfGrants);
  };
  const handleGrantTypeChange = (value, i) => {
    console.log(value + 'data--');
    const inputGrantTypes = [...typeOfGrants];
    inputGrantTypes[i] = value;
    setTypeOfGrants(inputGrantTypes);
  };
  const deleteGrantType = (i) => {
    const filteredTypeOfGrants = [...typeOfGrants];
    filteredTypeOfGrants.splice(i, 1)
    setTypeOfGrants(filteredTypeOfGrants);
  };


  const clearForm = () => {
    setPartnerComments("");
    setOidcClientName("");
    setPublicKey("");
    setLoginUrl("");
  };

  const styles = {
    outerDiv: "!ml-0 !mb-0",
    dropdownLabel: "!text-base !mb-1",
    dropdownButton: "!w-full !h-12 !rounded-md !text-lg !text-left !text-grayish-blue",
    selectionBox: "!top-12"
  }

  return (
    <div className={`mt-5 w-[100%] ${isLoginLanguageRTL ? "mr-32 ml-5" : "ml-32 mr-5"} overflow-x-scroll font-inter`}>
      <div className="flex-col">
        <div className="flex justify-between">
          <div className="flex items-start gap-x-3">
            <img src={backArrow} alt="" onClick={() => moveToAuthenticationServices()} className={`mt-[5%] cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} />
            <div className="flex-col">
              <h1 className="font-semibold text-xl text-dark-blue">{t('createOidcClient.createOidcClient')}</h1>
              <div className="flex space-x-1">
                <p onClick={() => moveToHome()} className="font-semibold text-tory-blue text-xs cursor-pointer">
                  {t('commons.home')} /
                </p>
                <p onClick={() => moveToAuthenticationServices()} className="font-semibold text-tory-blue text-xs cursor-pointer">
                  {t('authenticationServices.authenticationServices')}
                </p>
              </div>
            </div>
          </div>
          {/* <div className="px-4 py-2 bg-snow-white border-2 border-[#1447B2] rounded-md text-sm text-[#1447B2] font-semibold opacity-md shadow-[#1447b2] cursor-pointer">
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
                      addInfoIcon
                      fieldNameKey='requestPolicy.partnerId*'
                      placeHolderKey='createOidcClient.selectPartnerId'
                      styleSet={styles} />
                  </div>
                  <div className="flex flex-col w-[48%]">
                    <label className="block text-dark-blue text-base font-semibold mb-1">{t('requestPolicy.partnerType')}<span className="text-crimson-red">*</span></label>
                    <button disabled className="flex items-center justify-between w-full h-12 px-2 py-2 border border-[#C1C1C1] rounded-md text-lg text-grayish-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline" type="button">
                      <span>{t('createOidcClient.devicePartner')}</span>
                      <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                      </svg>
                    </button>
                  </div>
                </div>
                <div className="flex flex-row justify-between space-x-4 my-[1%]">
                  <div className="flex flex-col w-[48%]">
                    <label className="block text-dark-blue text-base font-semibold mb-1">{t('requestPolicy.policyGroup')}<span className="text-crimson-red">*</span></label>
                    <button disabled className="flex items-center justify-between w-full h-12 px-2 py-2 border border-[#C1C1C1] rounded-md text-lg text-grayish-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline" type="button">
                      <span>{t('createOidcClient.policyGroupGoesHere')}</span>
                      <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                      </svg>
                    </button>
                  </div>
                  <div className="flex flex-col w-[48%]">
                    <DropdownComponent
                      fieldName='policyName'
                      addInfoIcon
                      infoKey={t('createOidcClient.policyNameToolTip')}
                      fieldNameKey='requestPolicy.policyName*'
                      placeHolderKey='createOidcClient.policyNamePlaceHolder'
                      styleSet={styles} />
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
                      <img src={info} className={`${isLoginLanguageRTL ? "mr-2" :"ml-2"} cursor-pointer`} onClick={() => setShowPublicKeyToolTip(!showPublicKeyToolTip)} />
                    </label>
                    {showPublicKeyToolTip &&
                      (
                        <div className={`z-20 w-[24%] max-h-[32%] overflow-y-auto absolute ${isLoginLanguageRTL ? "mr-[10%]" :"ml-[8%]"} shadow-lg bg-white border border-gray-300 p-3 rounded`}>
                          <p className="text-black text-sm">{t('createOidcClient.publicKeyToolTip')}</p>
                        </div>
                      )}
                    <textarea value={publicKey} onChange={(e) => setPublicKey(e.target.value)}
                      className="h-14 px-2 py-4 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                      placeholder={t('createOidcClient.publicKeyPlaceHolder')}></textarea>
                  </div>
                </div>
                <div className="flex my-[1%]">
                  <div className="flex flex-col w-[562px]">
                    <label className="block text-dark-blue text-base font-semibold mb-1">{t('createOidcClient.loginUrl')}<span className="text-crimson-red">*</span></label>
                    <input value={loginUrl} onChange={(e) => setLoginUrl(e.target.value)}
                      className="h-12 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                      placeholder={t('createOidcClient.loginUrlPlaceHolder')} />
                  </div>
                </div>

                <div className="flex flex-row justify-between space-x-4 my-[1%]">
                  <div className="flex flex-col w-[48%]">
                    <label className="block text-dark-blue text-base font-semibold mb-1">{t('createOidcClient.redirectUrl')}<span className="text-crimson-red">*</span></label>
                    <ul>
                      <div className="flex w-f justify-between h-11 px-2 py-2 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar focus:shadow-outline">
                        <input value={logoUrl} onChange={(e) => setLogoUrl(e.target.value)} placeholder={t('createOidcClient.redirectUrlPlaceHolder')} className="w-[85%] focus:outline-none" />
                        <p onClick={() => setLogoUrl('')} className="text-sm text-[#1447b2] font-semibold cursor-pointer">
                          {t('createOidcClient.delete')}
                        </p>
                      </div>
                      {redirectUrls.map((data, index) => {
                        return (
                          <li key={index}>
                            <div className="flex w-full justify-between h-11 px-2 py-2 mt-1 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar">
                              <input value={data} onChange={(e) => handleRedirectUrlChange(e,index)} placeholder={t("createOidcClient.enterLogoUrl")} className="w-[85%] focus:outline-none" />
                              <p onClick={() => deleteLogoUrl(index)} className="text-sm text-[#1447b2] font-semibold cursor-pointer">
                                {t('createOidcClient.delete')}
                              </p>
                            </div>
                          </li>
                        )
                      })
                      }
                    </ul>
                    <p type="button" onClick={() => addRedirectUrl()} className="text-[#1447b2] font-bold text-xs cursor-pointer"><span className="text-lg text-center">+</span>{t('createOidcClient.addNew')}</p>
                  </div>

                  <div className="flex flex-col w-[48%]">
                    <label className="block text-dark-blue text-base font-semibold mb-1">{t('createOidcClient.grantTypes')}<span className="text-crimson-red">*</span></label>
                    <ul>
                      <div className="flex w-f justify-between h-11 px-2 py-2 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar">
                        <input value={grantTypes} onChange={(e) => setGrantTypes(e.target.value)} placeholder={t('createOidcClient.enterGrantTypes')} className="w-[100%] focus:outline-none" />
                      </div>
                      {typeOfGrants.map((itemData, index) => {
                        return (
                          <li key={index}>
                            <div className="flex w-full justify-between h-11 px-2 py-2 mt-1 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar">
                              <input value={itemData} onChange={(e) => handleGrantTypeChange(e.target.value,index)} placeholder={t('createOidcClient.enterGrantTypes')} className="w-[100%] focus:outline-none" />
                              <p onClick={() => deleteGrantType(index)} className="text-sm text-[#1447b2] font-semibold cursor-pointer">
                                {t('createOidcClient.delete')}
                              </p>
                            </div>
                          </li>
                        )
                      })
                      }
                    </ul>
                    <p type="button" onClick={() => addTypeOfGrants()} className="text-[#1447b2] font-bold text-xs cursor-pointer"><span className="text-lg text-center">+</span>{t('createOidcClient.addNew')}</p>
                  </div>
                </div>


                <div className="flex my-[1%]">
                  <div className="flex flex-col w-full">
                    <label className="block text-dark-blue text-base font-semibold mb-1">{t('requestPolicy.comments')}<span className="text-crimson-red">*</span></label>
                    <textarea value={partnerComments} onChange={(e) => setPartnerComments(e.target.value)}
                      className="w-full h-12 px-2 py-2 border border-[#707070] rounded-md text-md text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                      placeholder={t('createOidcClient.commentBoxDesc')}>
                    </textarea>
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
              <button onClick={() => moveToAuthenticationServices(navigate)} className={`${isLoginLanguageRTL ?"ml-2" :"mr-2"} w-40 h-12 border-[#1447B2] border rounded-md bg-white text-tory-blue text-base font-semibold`}>{t('requestPolicy.cancel')}</button>
              <button className={`${isLoginLanguageRTL ?"ml-2" :"mr-2"} w-40 h-12 border-[#1447B2] border rounded-md text-base font-semibold bg-tory-blue text-white`}>{t('requestPolicy.submit')}</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default CreateOidcClient;