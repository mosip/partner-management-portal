import axios from 'axios';
import { getLoginRedirectUrl } from './LoginRedirectService.js';
import { jwtDecode } from 'jwt-decode';
import { setUserProfile, getUserProfile } from './UserProfileService.js';
import { getAppConfig } from './ConfigService.js';

export const HttpService = axios.create({
  withCredentials: true,
  baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_PARTNER_MANAGER_API_BASE_URL,
})

export const setupResponseInterceptor = (navigate) => {

  HttpService.interceptors.response.use((response) => { // block to handle success case
    const originalRequestUrl = response.config.url;
    //console.log("interceptor: " + originalRequestUrl);
    if (originalRequestUrl.split('/').includes('validateToken')) { // Added this condition to avoid infinite loop 
      if (!getUserProfile()) {
        const resp = response.data.response;
        const userData = jwtDecode(resp.token);
        const profile = {
          "userName": userData.preferred_username,
          "firstName": userData.given_name,
          "lastName": userData.family_name,
          "email": userData.email,
          "address": userData.addressTest,
          "phoneNumber": userData.phoneNumber,
          "orgName": userData.organizationName,
          "partnerType": userData.partnerType,
          "langCode": userData.locale ? userData.locale : 'eng',
          "roles": resp.role
        };
        localStorage.setItem("isAdmin", resp.role.includes("PARTNER_ADMIN"))
        localStorage.setItem("isPolicyManager", resp.role.includes("POLICYMANAGER"))
        setUserProfile(profile);
        console.log(profile);
      }
    }
    //in case user has a new started session on any page other than dashboard 
    //and he is not a registered user, then we want to forecfully redirect him to dashboard 
    //where he will be forced to select a policy group
    if (originalRequestUrl.split('/').includes('verify')) {
      const emailResp = response.data.response;
      const reqUrl = window.location.href.split('/');
      if (emailResp && !emailResp.emailExists && !reqUrl.includes("dashboard")) {
        window.location.href = '/';
      }
    }
    return response;
  },
    (error) => { // block to handle error case
      if (error.code === 'ECONNABORTED') {
        // Handle timeout error
        navigate('/partnermanagement/runtimeError', { state: { messageType: 'timeout'} });
      } else if (error.response) {
        if (error.response.status === 401) {
          console.log(error);
          let redirectUrl = process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_PARTNER_MANAGER_API_BASE_URL;
          redirectUrl = redirectUrl + getLoginRedirectUrl(window.location.href);
          console.log(redirectUrl);
          window.location.href = redirectUrl;
        } else if (error.response.status === 403) {
          navigate('/partnermanagement/runtimeError', { state: { messageType: 'noAccess', errorCode: error.response.status, errorText: error.response.statusText } });
        } else {
          navigate('/partnermanagement/runtimeError', { state: { messageType: 'somethingWentWrong', errorCode: error.response.status, errorText: error.response.statusText } });
        }
      } else {
        navigate('/partnermanagement/runtimeError');
      }
      return Promise.reject(error);
    });
}

export const changeHttpServiceTimeout = async () => {
  try {
    const configData = await getAppConfig();
    const AXIOS_TIMEOUT = 'axiosTimeout';
    // Convert minutes to milliseconds
    const axiosTimeout = Number(configData[AXIOS_TIMEOUT]) * 60 * 1000;
    HttpService.defaults.timeout = axiosTimeout;
  } catch (error) {
    console.error("An error occurred while setting axios timeout :", error);
    // Set default timeout in case of error or invalid config
    HttpService.defaults.timeout = 3 * 60 * 1000;
  }
};

changeHttpServiceTimeout();
