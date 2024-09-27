import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { getUserProfile } from '../../services/UserProfileService.js';
import { isLangRTL, onPressEnterKey } from '../../utils/AppUtils.js';
import { useTranslation } from "react-i18next";
import { getPartnerManagerUrl, createRequest, handleServiceErrors, moveToOidcClientsList } from '../../utils/AppUtils.js';
import { HttpService } from '../../services/HttpService.js';
import ErrorMessage from '../common/ErrorMessage.js';
import LoadingIcon from "../common/LoadingIcon.js";
import SelectPolicyPopup from './SelectPolicyPopup.js';

import partnerCertificateIcon from '../../svg/partner_certificate_icon.svg';
import policiesIcon from '../../svg/policies_icon.svg';
import authServiceIcon from '../../svg/auth_services_icon.svg';
import deviceProviderServices_icon from '../../svg/deviceProviderServices_icon.svg';
import ftmServicesIcon from "../../svg/ftm_services_icon.svg";
import ConsentPopup from './ConsentPopup.js';

function Dashboard() {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [showPolicies, setShowPolicies] = useState(false);
  const [showAuthenticationServices, setShowAuthenticationServices] = useState(false);
  const [showDeviceProviderServices, setShowDeviceProviderServices] = useState(false);
  const [showFtmServices, setShowFtmServices] = useState(false);
  const [showConsentPopup, setShowConsentPopup] = useState(false);
  let isSelectPolicyPopupVisible = false;
  let isUserConsentGiven = false;

  const [showPopup, setShowPopup] = useState(false);

  const fetchUserConsent = async () => {
    setDataLoaded(false);
    setErrorCode("");
    setErrorMsg("");
    try {
      const response = await HttpService.get(getPartnerManagerUrl(`/users/user-consent`, process.env.NODE_ENV));
      if (response) {
        console.log(response)
        const responseData = response.data;
        console.log(responseData)
        if (responseData && responseData.response) {
          const resData = responseData.response;
          let consentGiven = resData.consentGiven;
          console.log(consentGiven)
          isUserConsentGiven = consentGiven;
          console.log(isUserConsentGiven)
        } else {
          handleServiceErrors(responseData, setErrorCode, setErrorMsg);
        }
      } else {
        setErrorMsg(t('consentPopup.consentFetchError'));
      }
      setDataLoaded(true);
    } catch (err) {
      setErrorMsg(err.toString());
      console.log("Error: ", err);
    }
  }
  useEffect(() => {
    const fetchData = async () => {
      try {
        const userProfile = getUserProfile();
        if (userProfile.partnerType === "AUTH_PARTNER") {
          setShowAuthenticationServices(true);
        }
        if (getUserProfile().partnerType === "DEVICE_PROVIDER") {
          setShowDeviceProviderServices(true);
        }
        if (getUserProfile().partnerType === "FTM_PROVIDER") {
          setShowFtmServices(true);
        }
        //1. verify that the logged in user's email is registered in PMS table or not
        // using the email id
        const verifyEmailRequest = createRequest({
          "emailId": userProfile.email
        });
        const response = await HttpService.put(getPartnerManagerUrl('/partners/email/verify', process.env.NODE_ENV), verifyEmailRequest);
        if (response && response.data && response.data.response) {
          const resData = response.data.response;
          console.log(`user's email exist in db: ${resData.emailExists}`);
          if (!resData.emailExists) {
            //2. if email does not exist then check if Policy Group selection is required for this Partner Type or not
            if (
              resData.policyRequiredPartnerTypes.indexOf(userProfile.partnerType) > -1) {
              console.log(`show policy group selection popup`);
              setShowPolicies(true);
              //3. show policy group selection popup
              //TODO show policy group selection popup
              setShowPopup(true);
              document.body.style.overflow = "hidden";
              isSelectPolicyPopupVisible = true;
            } else {
              //4. register the new user in PMS
              const registerUserRequest = createRequest({
                partnerId: userProfile.userName,
                organizationName: userProfile.orgName,
                address: userProfile.address,
                contactNumber: userProfile.phoneNumber,
                emailId: userProfile.email,
                partnerType: userProfile.partnerType,
                langCode: userProfile.langCode,
              });
              const registerUserResponse = await HttpService.post(getPartnerManagerUrl('/partners', process.env.NODE_ENV), registerUserRequest);
              const registerUserResponseData = registerUserResponse.data;
              if (registerUserResponseData && registerUserResponseData.response) {
                window.location.reload();
              } else {
                handleServiceErrors(registerUserResponseData, setErrorCode, setErrorMsg);
              }
            }
          }
          if (!isSelectPolicyPopupVisible) {
            await fetchUserConsent();
            if (!isUserConsentGiven) {
              setShowConsentPopup(true);
              document.body.style.overflow = "hidden";
            }
          }
          //if email exists then do nothing
          if (
            resData.policyRequiredPartnerTypes.indexOf(userProfile.partnerType) > -1) {
            setShowPolicies(true);
          }
        } else {
          setErrorMsg(t('dashboard.verifyEmailError'));
        }
        setDataLoaded(true);
      } catch (err) {
        console.error('Error fetching data:', err);
        setErrorMsg(err.toString());
        setDataLoaded(true);
      }
    };
    fetchData();

  }, []);

  const partnerCertificatesList = () => {
    navigate('/partnermanagement/partnerCertificate')
  };

  const policies = () => {
    navigate('/partnermanagement/policies/policiesList')
  };

  const deviceProviderServices = () => {
    navigate('/partnermanagement/deviceProviderServices/sbiList')
  };

  const ftmChipProviderServices = () => {
    navigate('/partnermanagement/ftmChipProviderServices/ftmList')
  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  return (
    <div className={`w-full mb-[2%] ${isLoginLanguageRTL ? "mr-28" : "ml-20"} overflow-x-scroll relative`}>
      {!dataLoaded && (
        <LoadingIcon></LoadingIcon>
      )}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}/>
          )}
          <div className="mb-6 mt-5 ml-[2%] text-lg font-semibold tracking-tight text-gray-700">
            <p >
              {t('dashboard.welcomeMsg', { firstName: getUserProfile().firstName, lastName: getUserProfile().lastName })}!
            </p>
          </div>
          <div className="flex mt-2 ml-[3%] flex-wrap break-words">
            <div onClick={partnerCertificatesList} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, partnerCertificatesList)}>
              <div className="flex justify-center mb-5">
                <img src={partnerCertificateIcon} alt="" className="w-8 h-8"></img>
              </div>
              <div>
                <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                  {t('dashboard.partnerCertificate')}
                </h5>
                <p className="mb-3 text-xs font-normal text-gray-400">
                  {t('dashboard.partnerCertificateDesc')}
                </p>
              </div>
            </div>
            {showPolicies && (
              <div onClick={policies} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, policies)}>
                <div className="flex justify-center mb-5">
                  <img src={policiesIcon} alt="" className="w-8 h-8"></img>
                </div>
                <div>
                  <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600 ">
                    {t('dashboard.policies')}
                  </h5>
                  <p className="mb-3 text-xs font-normal text-gray-400">
                    {t('dashboard.policiesDesc')}
                  </p>
                </div>
              </div>
            )}
            {showAuthenticationServices && (
              <div onClick={() => moveToOidcClientsList(navigate)} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, moveToOidcClientsList(navigate))}>
                <div className="flex justify-center mb-5">
                  <img src={authServiceIcon} alt="" className="w-8 h-8"></img>
                </div>
                <div>
                  <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600 ">
                    {t('dashboard.authenticationServices')}
                  </h5>
                  <p className="mb-3 text-xs font-normal text-gray-400">
                    {t('dashboard.authenticationServicesDesc')}
                  </p>
                </div>
              </div>
            )}
            {showDeviceProviderServices && (
              <div onClick={deviceProviderServices} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, deviceProviderServices)}>
                <div className="flex justify-center mb-5">
                  <img src={deviceProviderServices_icon} alt="" className="w-8 h-8" />
                </div>
                <div>
                  <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                    {t('dashboard.deviceProviderServices')}
                  </h5>
                  <p className="mb-3 text-xs font-normal text-gray-400">
                    {t('dashboard.deviceProviderServicesDesc')}
                  </p>
                </div>
              </div>
            )}
            {showFtmServices && (
              <div onClick={ftmChipProviderServices} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, ftmChipProviderServices)}>
                <div className="flex justify-center mb-5">
                  <img src={ftmServicesIcon} alt="" className="w-8 h-8" />
                </div>
                <div>
                  <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                    {t('dashboard.ftmChipProviderServices')}
                  </h5>
                  <p className="mb-3 text-xs font-normal text-gray-400">
                    {t('dashboard.ftmChipProviderServicesDesc')}
                  </p>
                </div>
              </div>
            )}
          </div>
          {showPopup && (
            <SelectPolicyPopup />
          )}
          {showConsentPopup && (
            <ConsentPopup />
          )}
        </>)}
    </div>
  )
}

export default Dashboard;