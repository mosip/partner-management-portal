import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { getUserProfile } from '../../services/UserProfileService.js';
import { useTranslation } from "react-i18next";
import { isLangRTL, onPressEnterKey, getPartnerManagerUrl, createRequest, handleServiceErrors, moveToOidcClientsList } from '../../utils/AppUtils.js';
import { HttpService } from '../../services/HttpService.js';
import ErrorMessage from '../common/ErrorMessage.js';
import LoadingIcon from "../common/LoadingIcon.js";
import LoadingCount from '../common/LoadingCount.js';
import SelectPolicyPopup from './SelectPolicyPopup.js';

import partnerCertificateIcon from '../../svg/partner_certificate_icon.svg';
import policiesIcon from '../../svg/policies_icon.svg';
import authServiceIcon from '../../svg/auth_services_icon.svg';
import deviceProviderServicesIcon from '../../svg/deviceProviderServices_icon.svg';
import ftmServicesIcon from "../../svg/ftm_services_icon.svg";
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
  const [isPolicyManager, setIsPolicyManager] = useState(false);
  const [partnerPolicyMappingRequestCount, setPartnerPolicyMappingRequestCount] = useState();
  const [sbiPendingApprovalRequestCount, setSbiPendingApprovalRequestCount] = useState();
  const [devicePendingApprovalRequestCount, setDevicePendingApprovalRequestCount] = useState();
  const [ftmPendingApprovalRequestCount, setFtmPendingApprovalRequestCount] = useState();
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
        const responseData = response.data;
        if (responseData && responseData.response) {
          const resData = responseData.response;
          let consentGiven = resData.consentGiven;
          isUserConsentGiven = consentGiven;
        } else {
          handleServiceErrors(responseData, setErrorCode, setErrorMsg);
        }
      } else {
        setErrorMsg(t('consentPopup.consentFetchError'));
      }
      setDataLoaded(true);
    } catch (err) {
      if (err.response?.status && err.response.status !== 401) {
        setErrorMsg(err.toString());
      }
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
        if (getUserProfile().roles.includes('PARTNER_ADMIN')) {
          setIsPartnerAdmin(true);
        }
        if (getUserProfile().roles.includes('POLICYMANAGER')) {
          setIsPolicyManager(true);
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
                callUserConsentPopup();
              } else {
                handleServiceErrors(registerUserResponseData, setErrorCode, setErrorMsg);
              }
            }
          }
          callUserConsentPopup();
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
        if (err.response?.status && err.response.status !== 401) {
          setErrorMsg(err.toString());
          setDataLoaded(true);
        }
        
      }
    };
    fetchData();

  }, []);

  const callUserConsentPopup = async () => {
    if (!isSelectPolicyPopupVisible) {
      await fetchUserConsent();
      if (!isUserConsentGiven) {
        setShowConsentPopup(true);
      }
    }
  }

  useEffect(() => {
    const fetchPartnerPolicyMappingRequestCount = async () => {
      const queryParams = new URLSearchParams();
      queryParams.append('status', 'InProgress');
      queryParams.append('pageSize', 1);
      queryParams.append('pageNo', 0);

      const url = `${getPartnerManagerUrl('/partner-policy-requests', process.env.NODE_ENV)}?${queryParams.toString()}`;
      try {
        const response = await HttpService.get(url);
        if (response) {
          const responseData = response.data;
          if (responseData && responseData.response) {
            setPartnerPolicyMappingRequestCount(responseData.response.totalResults);
          } else {
            handleServiceErrors(responseData, setErrorCode, setErrorMsg);
          }
        } else {
          setErrorMsg(t('dashboard.requestCountFetchError'));
        }
      } catch (err) {
        if (err.response?.status && err.response.status !== 401) {
          setErrorMsg(t('dashboard.requestCountFetchError'));
        }
        console.error("Error fetching data:", err);
      }
    };

    const fetchPendingApprovalSbiCount = async () => {
      const queryParams = new URLSearchParams();
      queryParams.append('status', 'pending_approval')
      queryParams.append('pageSize', '1');
      queryParams.append('pageNo', '0');

      const url = `${getPartnerManagerUrl('/securebiometricinterface', process.env.NODE_ENV)}?${queryParams.toString()}`;
      try {
        const response = await HttpService.get(url);
        if (response) {
          const responseData = response.data;
          if (responseData && responseData.response) {
            setSbiPendingApprovalRequestCount(responseData.response.totalResults);
          } else {
            handleServiceErrors(responseData, setErrorCode, setErrorMsg);
          }
        } else {
          setErrorMsg(t('dashboard.requestCountFetchError'));
        }
      } catch (err) {
        if (err.response?.status && err.response.status !== 401) {
          setErrorMsg(t('dashboard.requestCountFetchError'));
        }
        console.error("Error fetching data:", err);
      }
    };

    const fetchPendingApprovalDevicesCount = async () => {
      const queryParams = new URLSearchParams();
      queryParams.append('status', 'pending_approval')
      queryParams.append('pageSize', '1');

      const url = `${getPartnerManagerUrl('/devicedetail', process.env.NODE_ENV)}?${queryParams.toString()}`;
      try {
        const response = await HttpService.get(url);
        if (response) {
          const responseData = response.data;
          if (responseData && responseData.response) {
            setDevicePendingApprovalRequestCount(responseData.response.totalResults);
          } else {
            handleServiceErrors(responseData, setErrorCode, setErrorMsg);
          }
        } else {
          setErrorMsg(t('dashboard.requestCountFetchError'));
        }
      } catch (err) {
        if (err.response?.status && err.response.status !== 401) {
          setErrorMsg(t('dashboard.requestCountFetchError'));
        }
        console.error("Error fetching data:", err);
      }
    };

    const fetchPendingApprovalFtmCount = async () => {
      const queryParams = new URLSearchParams();
      queryParams.append('status', 'pending_approval')
      queryParams.append('pageSize', '1');

      const url = `${getPartnerManagerUrl('/ftpchipdetail/v2', process.env.NODE_ENV)}?${queryParams.toString()}`;
      try {
        const response = await HttpService.get(url);
        if (response) {
          const responseData = response.data;
          if (responseData && responseData.response) {
            setFtmPendingApprovalRequestCount(responseData.response.totalResults);
          } else {
            handleServiceErrors(responseData, setErrorCode, setErrorMsg);
          }
        } else {
          setErrorMsg(t('dashboard.requestCountFetchError'));
        }
      } catch (err) {
        if (err.response?.status && err.response.status !== 401) {
          setErrorMsg(t('dashboard.requestCountFetchError'));
        }
        console.error("Error fetching data:", err);
      }
    };

    if (isPartnerAdmin) {
      setTimeout(() => {
        fetchPartnerPolicyMappingRequestCount();
        fetchPendingApprovalSbiCount();
        fetchPendingApprovalDevicesCount();
        fetchPendingApprovalFtmCount();
      }, 3000);
    }

  }, [isPartnerAdmin]);

  const partnerCertificatesList = () => {
    navigate('/partnermanagement/certificates/partner-certificate')
  };

  const policies = () => {
    navigate('/partnermanagement/policies/policies-list')
  };

  const deviceProviderServices = () => {
    navigate('/partnermanagement/device-provider-services/sbi-list')
  };

  const ftmChipProviderServices = () => {
    navigate('/partnermanagement/ftm-chip-provider-services/ftm-list')
  };

  const rootTrustCertificateList = () => {
    navigate('/partnermanagement/admin/certificates/root-ca-certificate-list')
  }

  const partnersList = () => {
    navigate('/partnermanagement/admin/partners-list')
  }

  const policiesInAdmin = () => {
    navigate('/partnermanagement/policy-manager/policy-group-list')
  }

  const partnerPolicyMappingRequestList = () => {
    navigate('/partnermanagement/admin/policy-requests-list')
  }

  const adminAuthenticationServices = () => {
    navigate('/partnermanagement/admin/authentication-services/oidc-clients-list');
  }

  const adminftmChipProviderServices = () => {
    navigate('/partnermanagement/admin/ftm-chip-provider-services/ftm-list');
  }

  const adminDeviceProviderServices = () => {
    navigate('/partnermanagement/admin/device-provider-services/sbi-list');
  }

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  const CountWithHover = ({ count, descriptionKey, descriptionParams }) => (
    <div className="absolute flex items-center -top-3 -right-3 min-w-fit w-10 h-8 bg-[#FEF1C6] rounded-md text-[#6D1C00] text-sm shadow-md">
      <div className="relative group flex items-center justify-center w-full" tabIndex="0">
        <span className="font-medium p-2 rounded">
          {count ?? <LoadingCount />}
        </span>

        {count !== null && count !== undefined && (
          <div className="absolute hidden group-focus:block group-hover:block bg-[#FEF1C6] text-xs font-semibold p-2 w-40 mt-1 z-10 top-9 right-0 rounded-md shadow-md">
            {t(descriptionKey, descriptionParams)}
          </div>
        )}
      </div>
    </div>
  );

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
          <div id='welcome_msg' className="flex mb-6 mt-5 ml-[2%] text-lg font-semibold tracking-tight text-gray-700 justify-between flex-wrap">
            <p>
              {t('dashboard.welcomeMsg', { firstName: getUserProfile().firstName, lastName: getUserProfile().lastName })}!
            </p>
          </div>
          <div className="flex mt-2 ml-[1.8rem] flex-wrap break-words">
            {!isPartnerAdmin && !isPolicyManager &&
              < div role='button' id='dashboard_partner_certificate_list_card' onClick={() => partnerCertificatesList()} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => partnerCertificatesList())}>
                <div className="flex justify-center mb-5">
                  <img id='dashboard_partner_certificated_list_icon' src={partnerCertificateIcon} alt="" className="w-8 h-8" />
                </div>
                <div>
                  <h5 id='dashboard_partner_certificate_list_header' className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                    {t('dashboard.partnerCertificate')}
                  </h5>
                  <p id='dashboard_partner_certificate_list_description' className="mb-3 text-xs font-normal text-gray-400">
                    {t('dashboard.partnerCertificateDesc')}
                  </p>
                </div>
              </div>
            }
            {!isPartnerAdmin && !isPolicyManager && showPolicies && (
              <div role='button' id='dashboard_policies_card' onClick={() => policies()} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => policies())}>
                <div className="flex justify-center mb-5">
                  <img id='dashboard_policies_card_icon' src={policiesIcon} alt="" className="w-8 h-8" />
                </div>
                <div>
                  <h5 id='dashboard_policies_card_header' className="mb-2 text-sm font-semibold tracking-tight text-gray-600 ">
                    {t('dashboard.policies')}
                  </h5>
                  <p id='dashboard_policies_card_description' className="mb-3 text-xs font-normal text-gray-400">
                    {t('dashboard.policiesDesc')}
                  </p>
                </div>
              </div>
            )}
            {!isPartnerAdmin && !isPolicyManager && showAuthenticationServices && (
              <div role='button' id='dashboard_authentication_clients_list_card' onClick={() => moveToOidcClientsList(navigate)} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => moveToOidcClientsList(navigate))}>
                <div className="flex justify-center mb-5">
                  <img id='dashboard_authentication_clients_list_icon' src={authServiceIcon} alt="" className="w-8 h-8" />
                </div>
                <div>
                  <h5 id='dashboard_authentication_clients_list_card_header' className="mb-2 text-sm font-semibold tracking-tight text-gray-600 ">
                    {t('dashboard.authenticationServices')}
                  </h5>
                  <p id='dashboard_authentication_clients_list_card_description' className="mb-3 text-xs font-normal text-gray-400">
                    {t('dashboard.authenticationServicesDesc')}
                  </p>
                </div>
              </div>
            )}
            {!isPartnerAdmin && !isPolicyManager && showDeviceProviderServices && (
              <div role='button' id='dashboard_device_provider_service_card' onClick={deviceProviderServices} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, deviceProviderServices)}>
                <div className="flex justify-center mb-5">
                  <img id='dashboard_device_provider_service_icon' src={deviceProviderServicesIcon} alt="" className="w-8 h-8" />
                </div>
                <div>
                  <h5 id='dashboard_device_provider_service_card_header' className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                    {t('dashboard.deviceProviderServices')}
                  </h5>
                  <p id='dashboard_device_provider_service_card_description' className="mb-3 text-xs font-normal text-gray-400">
                    {t('dashboard.deviceProviderServicesDesc')}
                  </p>
                </div>
              </div>
            )}
            {!isPartnerAdmin && !isPolicyManager && showFtmServices && (
              <div role='button' id='dashboard_ftm_chip_provider_card' onClick={ftmChipProviderServices} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, ftmChipProviderServices)}>
                <div className="flex justify-center mb-5">
                  <img id='dashboard_ftm_chip_provider_icon' src={ftmServicesIcon} alt="" className="w-8 h-8" />
                </div>
                <div>
                  <h5 id='dashboard_ftm_chip_provider_card_header' className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                    {t('dashboard.ftmChipProviderServices')}
                  </h5>
                  <p id='dashboard_ftm_chip_provider_card_description' className="mb-3 text-xs font-normal text-gray-400">
                    {t('dashboard.ftmChipProviderServicesDesc')}
                  </p>
                </div>
              </div>
            )}
            {isPartnerAdmin && (
              <>
                <div role='button' onClick={rootTrustCertificateList} className="w-[23.5%] min-h-[50%] p-6 mr-4 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, rootTrustCertificateList)}>
                  <div className="flex justify-center mb-5">
                    <img id='admin_partner_certificate_list_icon' src={partnerCertificateIcon} alt="" className="w-8 h-8" />
                  </div>
                  <div>
                    <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                      {t('dashboard.certificateTrustStore')}
                    </h5>
                    <p className="mb-3 text-xs font-normal text-gray-400">
                      {t('dashboard.certificateTrustStoreDesc')}
                    </p>
                  </div>
                </div>

                <div role='button' onClick={partnersList} className="w-[23.5%] min-h-[50%] p-6 mr-4 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, partnersList)}>
                  <div className="flex justify-center mb-5">
                    <img id='partner_admin_icon' src={partner_admin_icon} alt="" className="w-8 h-8" />
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
                </>
              )}
              {(isPolicyManager || isPartnerAdmin) && (
                <div role='button' onClick={policiesInAdmin} className="w-[23.5%] min-h-[50%] p-6 mr-4 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, policiesInAdmin)}>
                  <div className="flex justify-center mb-5">
                    <img id='admin_policies_icon' src={admin_policies_icon} alt="" className="w-8 h-8" />
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
              )}
              {isPartnerAdmin && (
                <>
                  <div role='button' onClick={partnerPolicyMappingRequestList} className="relative w-[23.5%] min-h-[50%] p-6 mr-4 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, partnerPolicyMappingRequestList)}>
                    <div className="flex justify-center mb-5">
                      <img id='partner_policy_mapping_icon' src={partner_policy_mapping_icon} alt="" className="w-8 h-8" />
                    </div>
                    <div>
                      <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                        {t('dashboard.partnerPolicyMapping')}
                      </h5>
                      <p className="mb-3 text-xs font-normal text-gray-400">
                        {t('dashboard.partnerPolicyMappingDesc')}
                      </p>
                    </div>
                    <CountWithHover
                      count={partnerPolicyMappingRequestCount}
                      descriptionKey="dashboard.partnerPolicyMappingRequestCountDesc"
                      descriptionParams={{ partnerPolicyMappingRequestCount }}
                    />
                  </div>

                  <div role='button' onClick={adminDeviceProviderServices} className="relative w-[23.5%] min-h-[50%] p-6 mr-4 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, adminDeviceProviderServices)}>
                    <div className="flex justify-center mb-5">
                      <img id='deviceProviderServicesIcon' src={deviceProviderServicesIcon} alt="" className="w-8 h-8" />
                    </div>
                    <div>
                      <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                        {t('dashboard.sbiDevice')}
                      </h5>
                      <p className="mb-3 text-xs font-normal text-gray-400">
                        {t('dashboard.sbiDeviceDesc')}
                      </p>
                    </div>
                    <CountWithHover
                      count={sbiPendingApprovalRequestCount && devicePendingApprovalRequestCount ? `${sbiPendingApprovalRequestCount} | ${devicePendingApprovalRequestCount}` : null}
                      descriptionKey="dashboard.sbiAndDevicePendingApprovalRequestCountDesc"
                      descriptionParams={{
                        sbiPendingApprovalRequestCount,
                        devicePendingApprovalRequestCount
                      }}
                    />
                  </div>

                  <div role='button' onClick={adminftmChipProviderServices} className="relative w-[23.5%] min-h-[50%] p-6 mr-4 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, adminftmChipProviderServices)}>
                    <div className="flex justify-center mb-5">
                      <img id='ftmServicesIcon' src={ftmServicesIcon} alt="" className="w-8 h-8" />
                    </div>
                    <div>
                      <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                        {t('dashboard.ftmChip')}
                      </h5>
                      <p className="mb-3 text-xs font-normal text-gray-400">
                        {t('dashboard.ftmChipDesc')}
                      </p>
                    </div>
                    <CountWithHover
                      count={ftmPendingApprovalRequestCount}
                      descriptionKey="dashboard.ftmPendingApprovalRequestCountDesc"
                      descriptionParams={{ ftmPendingApprovalRequestCount }}
                    />
                  </div>

                  <div role='button' onClick={adminAuthenticationServices} className="w-[23.5%] min-h-[50%] p-6 mr-4 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, adminAuthenticationServices)}>
                    <div className="flex justify-center mb-5">
                      <img id='admin_auth_service_icon' src={authServiceIcon} alt="" className="w-8 h-8" />
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