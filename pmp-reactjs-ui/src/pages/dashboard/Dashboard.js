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
import pending_requests_icon from '../../svg/pending_requests_icon.svg';
import partner_admin_icon from '../../svg/partner_admin_icon.svg';
import admin_policies_icon from '../../svg/admin_policies_icon.svg';
import partner_policy_mapping_icon from '../../svg/partner_policy_mapping_icon.svg';
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
  const [isPartnerAdmin, setIsPartnerAdmin] = useState(false);
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
        if (getUserProfile().roles.includes('PARTNER_ADMIN' || 'POLICY_MANAGER')) {
          setIsPartnerAdmin(true);
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
    navigate('/partnermanagement/certificates/partnerCertificate')
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

  const rootTrustCertificateList = () =>{
    navigate('/partnermanagement/admin/certificates/rootTrustCertificateList')
  }

  const partnersList = () =>{
    navigate('/partnermanagement/admin/partnersList')
  }

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
            <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
          )}
          <div className="mb-6 mt-5 ml-[2%] text-lg font-semibold tracking-tight text-gray-700">
            <p >
              {t('dashboard.welcomeMsg', { firstName: getUserProfile().firstName, lastName: getUserProfile().lastName })}!
            </p>
          </div>
          <div className="flex mt-2 ml-[3%] flex-wrap break-words">
            {!isPartnerAdmin &&
              < div id='dashboard_partner_certificated_list_card' onClick={() => partnerCertificatesList()} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, partnerCertificatesList())}>
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
            }
            {!isPartnerAdmin && showPolicies && (
              <div id='dashboard_policies_card' onClick={() => policies()} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, policies())}>
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
            {!isPartnerAdmin && showAuthenticationServices && (
              <div id='dashboard_authentication_clients_list_card' onClick={() => moveToOidcClientsList(navigate)} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, moveToOidcClientsList(navigate))}>
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
            {!isPartnerAdmin && showDeviceProviderServices && (
              <div id='dashboard_device_provider_service_card' onClick={deviceProviderServices} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, deviceProviderServices)}>
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
            {!isPartnerAdmin && showFtmServices && (
              <div id='dashboard_ftm_chip_provider_card' onClick={ftmChipProviderServices} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, ftmChipProviderServices)}>
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
            {isPartnerAdmin && (
              <>
                <div className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
                  <div className="flex justify-center mb-5">
                    <img src={pending_requests_icon} alt="" className="w-8 h-8"></img>
                  </div>
                  <div>
                    <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                      {t('dashboard.pendingRequests')}
                    </h5>
                    <p className="mb-3 text-xs font-normal text-gray-400">
                      {t('dashboard.pendingRequestsDesc')}
                    </p>
                  </div>
                </div>

                <div onClick={rootTrustCertificateList} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, rootTrustCertificateList())}>
                  <div className="flex justify-center mb-5">
                    <img src={partnerCertificateIcon} alt="" className="w-8 h-8"></img>
                  </div>
                  <div>
                    <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                      {t('dashboard.rootOfTrustCertificate')}
                    </h5>
                    <p className="mb-3 text-xs font-normal text-gray-400">
                      {t('dashboard.rootOfTrustCertificateDesc')}
                    </p>
                  </div>
                </div>

                <div onClick={partnersList} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
                  <div className="flex justify-center mb-5">
                    <img src={partner_admin_icon} alt="" className="w-8 h-8"></img>
                  </div>
                  <div>
                    <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                      {t('dashboard.partner')}
                    </h5>
                    <p className="mb-3 text-xs font-normal text-gray-400">
                      {t('dashboard.partnerDesc')}
                    </p>
                  </div>
                </div>

                <div className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
                  <div className="flex justify-center mb-5">
                    <img src={admin_policies_icon} alt="" className="w-8 h-8"></img>
                  </div>
                  <div>
                    <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                      {t('dashboard.policies')}
                    </h5>
                    <p className="mb-3 text-xs font-normal text-gray-400">
                      {t('dashboard.policiesadminDesc')}
                    </p>
                  </div>
                </div>

                <div className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
                  <div className="flex justify-center mb-5">
                    <img src={partner_policy_mapping_icon} alt="" className="w-8 h-8"></img>
                  </div>
                  <div>
                    <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                      {t('dashboard.partnerPolicyMapping')}
                    </h5>
                    <p className="mb-3 text-xs font-normal text-gray-400">
                      {t('dashboard.partnerPolicyMappingDesc')}
                    </p>
                  </div>
                </div>

                <div className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
                  <div className="flex justify-center mb-5">
                    <img src={deviceProviderServices_icon} alt="" className="w-8 h-8"></img>
                  </div>
                  <div>
                    <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                      {t('dashboard.sbiDevice')}
                    </h5>
                    <p className="mb-3 text-xs font-normal text-gray-400">
                      {t('dashboard.sbiDeviceDesc')}
                    </p>
                  </div>
                </div>

                <div className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
                  <div className="flex justify-center mb-5">
                    <img src={ftmServicesIcon} alt="" className="w-8 h-8"></img>
                  </div>
                  <div>
                    <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                      {t('dashboard.ftmChip')}
                    </h5>
                    <p className="mb-3 text-xs font-normal text-gray-400">
                      {t('dashboard.ftmChipDesc')}
                    </p>
                  </div>
                </div>

                <div className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
                  <div className="flex justify-center mb-5">
                    <img src={authServiceIcon} alt="" className="w-8 h-8"></img>
                  </div>
                  <div>
                    <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600 ">
                      {t('dashboard.authenticationServices')}
                    </h5>
                    <p className="mb-3 text-xs font-normal text-gray-400">
                      {t('dashboard.adminAuthenticationServicesDesc')}
                    </p>
                  </div>
                </div>
              </>
            )}
          </div>
          {showPopup && (
            <SelectPolicyPopup />
          )}
          {showConsentPopup && (
            <ConsentPopup />
          )}
        </>)
      }
    </div >
  )
}

export default Dashboard;