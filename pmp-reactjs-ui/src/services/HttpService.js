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
        setUserProfile(profile);
        console.log(profile);
      }
      //Example: navigate('/partnermanagement/policies');
    }
    //in case user has a new started session on any page other than dashboard 
    //and he is not a registered user, then we want to forecfully redirect him to dashboard 
    //where he will be forced to select a policy group
    if (originalRequestUrl.split('/').includes('verify')) {
      const emailResp = response.data.response;
      const reqUrl = window.location.href.split('/');
      if (!emailResp.emailExists && !reqUrl.includes("dashboard")) {
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
  const configData = await getAppConfig();
  let axiosTimeout;
  const AXIOS_TIMEOUT = 'axiosTimeout';
  if (configData && configData[AXIOS_TIMEOUT] !== undefined) {
    // Convert minutes to milliseconds
    axiosTimeout = Number(configData[AXIOS_TIMEOUT]) * 60 * 1000;
  } else {
    axiosTimeout = 3 * 60 * 1000;
    console.error("axios timeout config properties not found, setting to default values");
  }
  HttpService.defaults.timeout = axiosTimeout;
};

changeHttpServiceTimeout();
