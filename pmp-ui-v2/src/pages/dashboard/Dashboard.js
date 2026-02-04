import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { getUserProfile } from '../../services/UserProfileService.js';
import { useTranslation } from "react-i18next";
import { isLangRTL, onPressEnterKey, getPartnerManagerUrl, createRequest, handleServiceErrors, moveToOidcClientsList, moveToMispPartnerServices, getPartnerType, fetchUserConsent } from '../../utils/AppUtils.js';
import { HttpService } from '../../services/HttpService.js';
import ErrorMessage from '../common/ErrorMessage.js';
import LoadingIcon from "../common/LoadingIcon.js";
import LoadingCount from '../common/LoadingCount.js';
import SelectPolicyPopup from './SelectPolicyPopup.js';
import PropTypes from 'prop-types';

import partnerCertificateIcon from '../../svg/partner_certificate_icon.svg';
import policiesIcon from '../../svg/policies_icon.svg';
import authServiceIcon from '../../svg/auth_services_icon.svg';
import deviceProviderServicesIcon from '../../svg/deviceProviderServices_icon.svg';
import ftmServicesIcon from "../../svg/ftm_services_icon.svg";
import partner_admin_icon from '../../svg/partner_admin_icon.svg';
import admin_policies_icon from '../../svg/admin_policies_icon.svg';
import partner_policy_mapping_icon from '../../svg/partner_policy_mapping_icon.svg';
import adminMispPartnerServicesIcon from '../../svg/admin_misp_partner_services_icon.svg';
import ConsentPopup from './ConsentPopup.js';
import { getAppConfig } from '../../services/ConfigService.js';
import OnboardingPartnerAlertPopup from './OnboardingPartnerAlertPopup.js';

function Dashboard() {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
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
  const [expiringFtmCertificateCount, setExpiringFtmCertificateCount] = useState();
  const [rootCertExpiryCount, setRootCertExpiryCount] = useState();
  const [intermediateCertExpiryCount, setIntermediateCertExpiryCount] = useState();
  const [partnerCertExpiryCount, setPartnerCertExpiryCount] = useState();
  const [expiringApiKeyCount, setExpiringApiKeyCount] = useState();
  const [expiringSbiCount, setExpiringSbiCount] = useState();
  const [expiringMispLicenseKeyCount, setExpiringMispLicenseKeyCount] = useState();
  const [showOnboardingPartnerAlertPopup, setShowOnboardingPartnerAlertPopup] = useState(false);
  const [onboardingPartnerAlertType, setOnboardingPartnerAlertType] = useState(null);
  const [isEmailVerified, setIsEmailVerified] = useState(false);
  const [configData, setConfigData] = useState(null);
  let isSelectPolicyPopupVisible = false;
  let isUserConsentGiven = false;

  const [showPopup, setShowPopup] = useState(false);

  const fetchUserConsentData = async () => {
    setDataLoaded(false);
    setErrorCode("");
    setErrorMsg("");
    try {
      const consentGiven = await fetchUserConsent();
      isUserConsentGiven = consentGiven;
      setDataLoaded(true);
    } catch (err) {
      if (err.response?.status && err.response.status !== 401) {
        setErrorMsg(err.toString());
      }
      console.log("Error: ", err);
      setDataLoaded(true);
    }
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        const userProfile = getUserProfile();
        const rolesString = userProfile.roles ?? '';
        // Split roles string into array, trim whitespace, and filter empty entries
        const rolesArray = rolesString.split(',')
          .map(role => role.trim())
          .filter(role => role.length > 0);
        const userRoles = rolesArray;
        if (rolesArray.includes("AUTH_PARTNER")) {
          setShowAuthenticationServices(true);
        }
        if (rolesArray.includes("DEVICE_PROVIDER")) {
          setShowDeviceProviderServices(true);
        }
        if (rolesArray.includes("FTM_PROVIDER")) {
          setShowFtmServices(true);
        }
        if (rolesArray.includes('PARTNER_ADMIN')) {
          setIsPartnerAdmin(true);
        }
        if (rolesArray.includes('POLICYMANAGER')) {
          setIsPolicyManager(true);
        }

        // Don't do self registration if partner role is partner admin or policy manager
        if (rolesArray.includes('PARTNER_ADMIN') || rolesArray.includes('POLICYMANAGER')) {
          // Partner admins and policy managers are treated as verified
          setIsEmailVerified(true);
          // Show consent popup for both PARTNER_ADMIN and POLICYMANAGER
          await callUserConsentPopup();
          setDataLoaded(true);
          return;
        }

        // Extract partnerType from roles if not present in userProfile
        let partnerType;
        try {
          partnerType = await getPartnerType(userProfile);
        } catch (err) {
          // Handle 403 error when user doesn't have valid roles
          if (err.response?.status === 403) {
            setOnboardingPartnerAlertType('invalidPartnerTypeError');
            setShowOnboardingPartnerAlertPopup(true);
            setDataLoaded(true);
            return;
          }
          // Re-throw other errors to be handled by outer catch block
          throw err;
        }

        // Check if no valid partner type is found
        if (!partnerType) {
          setOnboardingPartnerAlertType('invalidPartnerTypeError');
          setShowOnboardingPartnerAlertPopup(true);
          setDataLoaded(true);
          return;
        }

        //1. verify that the logged in user is registered in PMS table or not
        // using the email id
        const verifyEmailRequest = createRequest({
          "emailId": userProfile.email,
          "partnerId": userProfile.userName,
          "partnerType": partnerType
        }, "mosip.pms.partner.exists.post", true);
        const response = await HttpService.put(getPartnerManagerUrl('/partners/exists', process.env.NODE_ENV), verifyEmailRequest);
        if (response && response.data && response.data.response) {
          const resData = response.data.response;
          console.log(`user's email exist in db: ${resData.partnerExists}`);

          //check if duplicate exists then show the account error popup
          if(resData.duplicateExists){
              setOnboardingPartnerAlertType('verificationError');
              setShowOnboardingPartnerAlertPopup(true);
              setDataLoaded(true);
              return;
          }
          setIsEmailVerified(resData.partnerExists);
          if (!resData.partnerExists) {
            console.log('user email does not exist in PMS');
            // 2. If email does not exist, check if any required attributes are missing
            const requiredFields = ['userName', 'orgName', 'address', 'phoneNumber', 'email', 'partnerType'];

            const isAttributeMissing = requiredFields.some(field => {
              const value = userProfile[field];

              // Check if value is null, undefined, or an empty/whitespace-only string
              return value == null || (typeof value === 'string' && value.trim() === '');
            });

            if (isAttributeMissing) {
              setOnboardingPartnerAlertType('missingAttributes');
              setShowOnboardingPartnerAlertPopup(true);
              setDataLoaded(true);
              return;
            }

            //3. if email does not exist then check if Policy Group selection is required for this Partner Type or not
            if (
              (userRoles.some(role => resData.policyRequiredPartnerTypes.includes(role))) && !rolesArray.includes('PARTNER_ADMIN') && !rolesArray.includes('POLICYMANAGER')) {
              console.log(`show policy group selection popup`);
              setShowPolicies(true);
              //4. show policy group selection popup
              //TODO show policy group selection popup
              setShowPopup(true);
              isSelectPolicyPopupVisible = true;
            } else {
              //5. register the new user in PMS
              const registerUserRequest = createRequest({
                partnerId: userProfile.userName,
                organizationName: userProfile.orgName,
                address: userProfile.address,
                contactNumber: userProfile.phoneNumber,
                emailId: userProfile.email,
                partnerType: partnerType,
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
            userRoles.some(role => resData.policyRequiredPartnerTypes.includes(role))) {
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

  useEffect(() => {
    async function fetchConfig() {
      const config = await getAppConfig();
      setConfigData(config);
    }

    fetchConfig();
  }, []);

  const callUserConsentPopup = async () => {
    if (!isSelectPolicyPopupVisible) {
      await fetchUserConsentData();
      if (!isUserConsentGiven) {
        setShowConsentPopup(true);
      }
    }
  }

  useEffect(() => {
    const fetchTrustCertExpiryCount = async (certType) => {
      const pageSize = 100;
      let pageNo = 0;
      let totalResults = 0;
      let filteredCerts = [];
    
      try {
        while (true) {
          const queryParams = new URLSearchParams();
          queryParams.append('expiryPeriod', 30);
          queryParams.append('caCertificateType', certType);
          queryParams.append('pageSize', pageSize);
          queryParams.append('pageNo', pageNo);
    
          const url = `${getPartnerManagerUrl('/trust-chain-certificates', process.env.NODE_ENV)}?${queryParams.toString()}`;
    
          const response = await HttpService.get(url);
          const responseData = response?.data;
    
          if (responseData && responseData.response) {
            const { data, totalResults: total } = responseData.response;
            totalResults = total;

            const validCerts = data.filter(cert => cert.status === true);
            filteredCerts = [...filteredCerts, ...validCerts];
    
            const totalPages = Math.ceil(totalResults / pageSize);
            pageNo++;
    
            if (pageNo >= totalPages) break;
          } else {
            handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            break;
          }
        }
    
        // After loop completes, set final count
        if (certType === "ROOT") {
          setRootCertExpiryCount(filteredCerts.length);
        } else if (certType === "INTERMEDIATE") {
          setIntermediateCertExpiryCount(filteredCerts.length)
        }
    
      } catch (err) {
        if (err.response?.status && err.response.status !== 401) {
          setErrorMsg(t('dashboard.requestCountFetchError'));
        }
        console.error("Error fetching data:", err);
      }
    };    

    const fetchPartnerCertExpiryCount = async () => {
      const queryParams = new URLSearchParams();
      queryParams.append('expiryPeriod', 30);
      const url = `${getPartnerManagerUrl('/partners/partner-certificates-details', process.env.NODE_ENV)}?${queryParams.toString()}`;
      try {
        const response = await HttpService.get(url);
        if (response) {
          const responseData = response.data;
          if (responseData && responseData.response) {
              setPartnerCertExpiryCount(responseData.response.length)
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

     const fetchFtmCertificateExpiryCount = async () => {
      const queryParams = new URLSearchParams();
      queryParams.append('expiryPeriod', 30);
      const url = `${getPartnerManagerUrl('/ftpchipdetail', process.env.NODE_ENV)}?${queryParams.toString()}`;
      try {
        const response = await HttpService.get(url);
        if (response) {
          const responseData = response.data;
          if (responseData && responseData.response) {
            console.log(responseData.response.length);

            setExpiringFtmCertificateCount(responseData.response.length);
          } else {
            handleServiceErrors(responseData, setErrorCode, setErrorMsg);
          }
        } else {
          setErrorMsg(t('dashboard.expiryCountFetchError'));
        }
      } catch (err) {
        if (err.response?.status && err.response.status !== 401) {
          setErrorMsg(t('dashboard.expiryCountFetchError'));
        }
        console.error("Error fetching data:", err);
      }
    };

    const fetchExpiringApiKeyCount = async () => {
      const queryParams = new URLSearchParams();
      queryParams.append('expiryPeriod', 30)
      queryParams.append('status', 'activated');
      queryParams.append('pageSize', '1');
      queryParams.append('pageNo', '0');

      const url = `${getPartnerManagerUrl('/partner-api-keys/v2', process.env.NODE_ENV)}?${queryParams.toString()}`;
      try {
        const response = await HttpService.get(url);
        if (response) {
          const responseData = response.data;
          if (responseData && responseData.response) {
            setExpiringApiKeyCount(responseData.response.totalResults);
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

    const fetchExpiringSbiCount = async () => {
      const queryParams = new URLSearchParams();
      queryParams.append('expiryPeriod', 30)
      queryParams.append('status', 'approved');
      queryParams.append('pageSize', '1');
      queryParams.append('pageNo', '0');

      const url = `${getPartnerManagerUrl('/securebiometricinterface', process.env.NODE_ENV)}?${queryParams.toString()}`;
      try {
        const response = await HttpService.get(url);
        if (response) {
          const responseData = response.data;
          if (responseData && responseData.response) {
            setExpiringSbiCount(responseData.response.totalResults);
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

    const fetchExpiringMispLicenseKeyCount = async () => {
      const queryParams = new URLSearchParams();
      queryParams.append('expiryPeriod', 30)
      queryParams.append('status', 'activated');
      queryParams.append('pageSize', '1');
      queryParams.append('pageNo', '0');

      const url = `${getPartnerManagerUrl('/misp-licenses', process.env.NODE_ENV)}?${queryParams.toString()}`;
      try {
        const response = await HttpService.get(url);
        if (response) {
          const responseData = response.data;
          if (responseData && responseData.response) {
            setExpiringMispLicenseKeyCount(responseData.response.totalResults);
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

    async function init() {
      if (!isPartnerAdmin && !isPolicyManager && isEmailVerified) {
        fetchPartnerCertExpiryCount();
      }

      if (!isPartnerAdmin && !isPolicyManager && isEmailVerified && showFtmServices && configData && configData.isCaSignedPartnerCertificateAvailable === 'true') {
        fetchFtmCertificateExpiryCount();
      }

      if (!isPartnerAdmin && !isPolicyManager && isEmailVerified && showAuthenticationServices) {
        fetchExpiringApiKeyCount();
      }

      if (!isPartnerAdmin && !isPolicyManager && isEmailVerified && showDeviceProviderServices) {
        fetchExpiringSbiCount();
      }

      if (isPartnerAdmin && isEmailVerified) {
        if (configData && configData.isRootIntermediateCertAvailable === 'true') {
          fetchTrustCertExpiryCount('ROOT');
          fetchTrustCertExpiryCount('INTERMEDIATE');
        }
        fetchExpiringMispLicenseKeyCount();

        setTimeout(() => {
          fetchPartnerPolicyMappingRequestCount();
          fetchPendingApprovalSbiCount();
          fetchPendingApprovalDevicesCount();
          fetchPendingApprovalFtmCount();
        }, 3000);
      }
    }

    if (configData !== null) {
      init();
    }
  }, [isPartnerAdmin, isEmailVerified, configData]);

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
    navigate('/partnermanagement/admin/partners/partners-list')
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

  const CountWithHover = ({ countLabel, descriptionKey, descriptionParams, isExpiryHover }) => (
    <div className={`absolute flex items-center ${isLoginLanguageRTL ? "-top-3 -left-3" : "-top-3 -right-3"} min-w-fit w-10 h-8 ${isExpiryHover ? 'bg-[#FAD6D1]' : 'bg-[#FEF1C6]'} rounded-md text-[#6D1C00] text-sm shadow-md`}>
      <div id='hover_count_label' role='button' onClick={(e) => e.stopPropagation()} className="relative group flex items-center justify-center w-full" tabIndex="0" 
        onKeyDown={(e) => { if (e.key === 'Enter' || e.key === ' ') { e.preventDefault(); e.stopPropagation(); } }}>
        <span className="font-medium p-2 rounded">
          {countLabel ?? <LoadingCount />}
        </span>

        {countLabel !== null && countLabel !== undefined && (
          <div id='hover_count_description' className={`absolute hidden group-focus:block group-hover:block ${isExpiryHover ? 'bg-[#FAD6D1]' : 'bg-[#FEF1C6]'} text-xs text-center font-semibold p-2 w-40 mt-1 z-10 ${isLoginLanguageRTL ? "top-9 left-0" : "top-9 right-0"} rounded-md shadow-md`}>
            {t(descriptionKey, descriptionParams)}
          </div>
        )}
      </div>
    </div>
  );

  CountWithHover.propTypes = {
    countLabel: PropTypes.oneOfType([
      PropTypes.string,
      PropTypes.number
    ]),
    descriptionKey: PropTypes.string.isRequired,
    descriptionParams: PropTypes.object.isRequired,
    isExpiryHover: PropTypes.bool,
  };

  const getTrustCertExpiryCountDescription = () => {
    if (rootCertExpiryCount > 0 && intermediateCertExpiryCount > 0) {
      if (rootCertExpiryCount > 1 && intermediateCertExpiryCount > 1) {
        return "dashboard.bothTrustCertExpiryCountPlural";
      } 
      if (rootCertExpiryCount === 1 && intermediateCertExpiryCount === 1) {
        return "dashboard.bothTrustCertExpiryCountSingular";
      } 
      if (rootCertExpiryCount === 1 && intermediateCertExpiryCount > 1) {
        return "dashboard.rootTrustCertExpiryCountSingular";
      } 
      if (rootCertExpiryCount > 1 && intermediateCertExpiryCount === 1) {
        return "dashboard.intermediateTrustCertExpiryCountSingular";
      }
    } 
    if (rootCertExpiryCount > 0) {
      return rootCertExpiryCount > 1 ? "dashboard.rootCertExpiryCountPlural" : "dashboard.rootCertExpiryCountSingular";
    }
    if (intermediateCertExpiryCount > 0) {
      return intermediateCertExpiryCount > 1 ? "dashboard.intermediateCertExpiryCountPlural" : "dashboard.intermediateCertExpiryCountSingular";
    }
  }

  return (
    <div className={`w-full mb-[2%] ${isLoginLanguageRTL ? "mr-28" : "ml-20"} overflow-x-scroll relative`}>
      {!dataLoaded && (
        <LoadingIcon></LoadingIcon>
      )}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage id='dashboard_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
          )}
          <div id='welcome_msg' className="flex mb-6 mt-5 ml-[2%] text-lg font-semibold tracking-tight text-gray-700 justify-between flex-wrap">
            <p>
              {t('dashboard.welcomeMsg', { firstName: getUserProfile().firstName, lastName: getUserProfile().lastName })}!
            </p>
          </div>
          <div className="grid grid-cols-[repeat(auto-fill,minmax(256px,1fr))] gap-4 mt-2 ml-[1.8rem] mr-[1.8rem] break-words">
            {!isPartnerAdmin && !isPolicyManager &&
              <div role='button' id='dashboard_partner_certificate_list_card' onClick={() => partnerCertificatesList()} className="relative min-h-[50%] p-6 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => partnerCertificatesList())}>
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
                {partnerCertExpiryCount > 0 && (
                  <CountWithHover
                    countLabel={partnerCertExpiryCount}
                    descriptionKey={partnerCertExpiryCount > 1 ? "dashboard.partnerCertExpiryCountDesc1" : "dashboard.partnerCertExpiryCountDesc2"}
                    descriptionParams={{ partnerCertExpiryCount }}
                    isExpiryHover={true}
                  />
                )}
              </div>
            }
            {!isPartnerAdmin && !isPolicyManager && showPolicies && (
              <div role='button' id='dashboard_policies_card' onClick={() => policies()} className="min-h-[50%] p-6 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => policies())}>
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
              <div role='button' id='dashboard_authentication_clients_list_card' onClick={() => moveToOidcClientsList(navigate)} className="relative min-h-[50%] p-6 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => moveToOidcClientsList(navigate))}>
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
                {expiringApiKeyCount > 0 && (
                  <CountWithHover
                    countLabel={expiringApiKeyCount}
                    descriptionKey={expiringApiKeyCount > 1 ? "dashboard.apiKeyExpiryCountDesc1" : "dashboard.apiKeyExpiryCountDesc2"}
                    descriptionParams={{ expiringApiKeyCount }}
                    isExpiryHover={true}
                  />
                )}
              </div>
            )}
            {!isPartnerAdmin && !isPolicyManager && showDeviceProviderServices && (
              <div role='button' id='dashboard_device_provider_service_card' onClick={deviceProviderServices} className="relative min-h-[50%] p-6 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, deviceProviderServices)}>
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
                {expiringSbiCount > 0 && (
                  <CountWithHover
                    countLabel={expiringSbiCount}
                    descriptionKey={expiringSbiCount > 1 ? "dashboard.sbiExpiryCountDesc1" : "dashboard.sbiExpiryCountDesc2"}
                    descriptionParams={{ expiringSbiCount }}
                    isExpiryHover={true}
                  />
                )}
              </div>
            )}
            {!isPartnerAdmin && !isPolicyManager && showFtmServices && (
              <div role='button' id='dashboard_ftm_chip_provider_card' onClick={ftmChipProviderServices} className="relative min-h-[50%] p-6 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, ftmChipProviderServices)}>
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
                {configData && configData.isCaSignedPartnerCertificateAvailable && expiringFtmCertificateCount > 0 && (
                  <CountWithHover
                    countLabel={expiringFtmCertificateCount}
                    descriptionKey={expiringFtmCertificateCount > 1 ? "dashboard.ftmChipCertExpiryCountDesc1" : "dashboard.ftmChipCertExpiryCountDesc2"}
                    descriptionParams={{ expiringFtmCertificateCount }}
                    isExpiryHover={true}
                  />
                )}
              </div>
            )}
             {isPartnerAdmin && (
              <>
                <div role='button' id='dashboard_certificate_trust_store_card' onClick={rootTrustCertificateList} className="relative min-h-[50%] p-6 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, rootTrustCertificateList)}>
                  <div className="flex justify-center mb-5">
                    <img id='admin_partner_certificate_list_icon' src={partnerCertificateIcon} alt="" className="w-8 h-8" />
                  </div>
                  <div>
                    <h5 id='dashboard_certificate_trust_store_card_header' className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                      {t('dashboard.certificateTrustStore')}
                    </h5>
                    <p id='dashboard_certificate_trust_store_card_description' className="mb-3 text-xs font-normal text-gray-400">
                      {t('dashboard.certificateTrustStoreDesc')}
                    </p>
                  </div>
                  {configData && configData.isRootIntermediateCertAvailable === 'true' && (rootCertExpiryCount > 0 || intermediateCertExpiryCount > 0) && (
                    <CountWithHover
                      countLabel={
                        rootCertExpiryCount > 0 && intermediateCertExpiryCount > 0
                          ? `${rootCertExpiryCount} | ${intermediateCertExpiryCount}`
                          : rootCertExpiryCount > 0
                          ? `${rootCertExpiryCount}`
                          : intermediateCertExpiryCount > 0
                          ? `${intermediateCertExpiryCount}`
                          : null
                      }
                      descriptionKey={getTrustCertExpiryCountDescription()}
                      descriptionParams={
                        rootCertExpiryCount > 0 && intermediateCertExpiryCount > 0
                          ? { rootCertExpiryCount, intermediateCertExpiryCount }
                          : rootCertExpiryCount > 0
                          ? { rootCertExpiryCount }
                          : intermediateCertExpiryCount > 0
                          ? { intermediateCertExpiryCount }
                          : {}}
                      isExpiryHover={true}
                    />
                  )}
                </div>
                <div role='button' id='dashboard_partner_card' onClick={partnersList} className="min-h-[50%] p-6 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, partnersList)}>
                  <div className="flex justify-center mb-5">
                    <img id='partner_admin_icon' src={partner_admin_icon} alt="" className="w-8 h-8" />
                  </div>
                  <div>
                    <h5 id='dashboard_partner_card_header' className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                      {t('dashboard.partner')}
                    </h5>
                    <p id='dashboard_partner_card_description' className="mb-3 text-xs font-normal text-gray-400">
                      {t('dashboard.partnerDesc')}
                    </p>
                  </div>
                </div>
                </>
              )}
              {(isPolicyManager || isPartnerAdmin) && (
                <div role='button' id='dashboard_policies_card' onClick={policiesInAdmin} className="min-h-[50%] p-6 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, policiesInAdmin)}>
                  <div className="flex justify-center mb-5">
                    <img id='admin_policies_icon' src={admin_policies_icon} alt="" className="w-8 h-8" />
                  </div>
                  <div>
                    <h5 id='dashboard_policies_card_header' className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                      {t('dashboard.policies')}
                    </h5>
                    <p id='dashboard_policies_card_description' className="mb-3 text-xs font-normal text-gray-400">
                      {t('dashboard.policiesadminDesc')}
                    </p>
                  </div>
                </div>
              )}
              {isPartnerAdmin && (
                <>
                  <div role='button' id='dashboard_partner_policy_linking_card' onClick={partnerPolicyMappingRequestList} className="relative min-h-[50%] p-6 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, partnerPolicyMappingRequestList)}>
                    <div className="flex justify-center mb-5">
                      <img id='partner_policy_mapping_icon' src={partner_policy_mapping_icon} alt="" className="w-8 h-8" />
                    </div>
                    <div>
                      <h5 id='dashboard_partner_policy_linking_card_header' className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                        {t('dashboard.partnerPolicyMapping')}
                      </h5>
                      <p id='dashboard_partner_policy_linking_card_description' className="mb-3 text-xs font-normal text-gray-400">
                        {t('dashboard.partnerPolicyMappingDesc')}
                      </p>
                    </div>
                    <CountWithHover
                      countLabel={partnerPolicyMappingRequestCount}
                      descriptionKey="dashboard.partnerPolicyMappingRequestCountDesc"
                      descriptionParams={{ partnerPolicyMappingRequestCount }}
                    />
                  </div>

                  <div role='button' id='dashboard_sbi_device_card' onClick={adminDeviceProviderServices} className="relative min-h-[50%] p-6 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, adminDeviceProviderServices)}>
                    <div className="flex justify-center mb-5">
                      <img id='deviceProviderServicesIcon' src={deviceProviderServicesIcon} alt="" className="w-8 h-8" />
                    </div>
                    <div>
                      <h5 id='dashboard_sbi_device_card_header' className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                        {t('dashboard.sbiDevice')}
                      </h5>
                      <p id='dashboard_sbi_device_card_description' className="mb-3 text-xs font-normal text-gray-400">
                        {t('dashboard.sbiDeviceDesc')}
                      </p>
                    </div>
                    <CountWithHover
                      countLabel={
                        sbiPendingApprovalRequestCount !== null &&
                        sbiPendingApprovalRequestCount !== undefined &&
                        devicePendingApprovalRequestCount !== null &&
                        devicePendingApprovalRequestCount !== undefined
                          ? `${sbiPendingApprovalRequestCount} | ${devicePendingApprovalRequestCount}`
                          : null
                      }
                      descriptionKey="dashboard.sbiAndDevicePendingApprovalRequestCountDesc"
                      descriptionParams={{
                        sbiPendingApprovalRequestCount,
                        devicePendingApprovalRequestCount
                      }}
                    />
                  </div>

                  <div role='button' id='dashboard_ftm_chip_card' onClick={adminftmChipProviderServices} className="relative min-h-[50%] p-6 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, adminftmChipProviderServices)}>
                    <div className="flex justify-center mb-5">
                      <img id='ftmServicesIcon' src={ftmServicesIcon} alt="" className="w-8 h-8" />
                    </div>
                    <div>
                      <h5 id='dashboard_ftm_chip_card_header' className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                        {t('dashboard.ftmChip')}
                      </h5>
                      <p id='dashboard_ftm_chip_card_description' className="mb-3 text-xs font-normal text-gray-400">
                        {t('dashboard.ftmChipDesc')}
                      </p>
                    </div>
                    <CountWithHover
                      countLabel={ftmPendingApprovalRequestCount}
                      descriptionKey="dashboard.ftmPendingApprovalRequestCountDesc"
                      descriptionParams={{ ftmPendingApprovalRequestCount }}
                    />
                  </div>

                  <div role='button' id='dashboard_admin_authentication_services_card' onClick={adminAuthenticationServices} className="min-h-[50%] p-6 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, adminAuthenticationServices)}>
                    <div className="flex justify-center mb-5">
                      <img id='admin_auth_service_icon' src={authServiceIcon} alt="" className="w-8 h-8" />
                    </div>
                    <div>
                      <h5 id='dashboard_admin_authentication_services_card_header' className="mb-2 text-sm font-semibold tracking-tight text-gray-600 ">
                        {t('dashboard.authenticationServices')}
                      </h5>
                      <p id='dashboard_admin_authentication_services_card_description' className="mb-3 text-xs font-normal text-gray-400">
                        {t('dashboard.adminAuthenticationServicesDesc')}
                      </p>
                  </div>
                </div>

                <div role='button' id='misp_partner_services_card' onClick={() => moveToMispPartnerServices(navigate)} className="relative min-h-[50%] p-6 pt-16 bg-white border border-gray-200 shadow cursor-pointer text-center rounded-xl" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => moveToMispPartnerServices(navigate))}>
                  <div className="flex justify-center mb-5">
                    <img id='admin_misp_partner_services_icon' src={adminMispPartnerServicesIcon} alt="Admin MISP Partner Services Icon" className="w-8 h-8" />
                  </div>
                  <div>
                    <h5 id='admin_misp_partner_services_card_header' className="mb-2 text-sm font-semibold tracking-tight text-gray-600" >
                      {t('dashboard.mispPartnerServices')}
                    </h5>
                    <p id='admin_misp_partner_services_card_description' className="mb-3 text-xs font-normal text-gray-400" >
                      {t('dashboard.mispPartnerServicesDesc')}
                    </p>
                  </div>
                  {expiringMispLicenseKeyCount > 0 && (
                    <CountWithHover
                      countLabel={expiringMispLicenseKeyCount}
                      descriptionKey={expiringMispLicenseKeyCount > 1 ? "dashboard.mispLicenseKeyExpiryCountDesc1" : "dashboard.mispLicenseKeyExpiryCountDesc2"}
                      descriptionParams={{ expiringMispLicenseKeyCount }}
                      isExpiryHover={true}
                    />
                  )}
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
          {showOnboardingPartnerAlertPopup && (
            <OnboardingPartnerAlertPopup 
              errorType={onboardingPartnerAlertType}
            />
          )}
        </>)
      }
    </div >
  )
}

export default Dashboard;