import axios from 'axios';
import { getLoginRedirectUrl } from './LoginRedirectService.js';
import { jwtDecode } from 'jwt-decode';
import { setUserProfile, getUserProfile } from './UserProfileService.js';

export const HttpService = axios.create({
  withCredentials: true,
  baseURL: process.env.NODE_ENV !== 'production'? '' : window._env_.REACT_APP_PARTNER_MANAGER_API_BASE_URL
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
          "langCode": userData.locale ? userData.locale: 'eng',
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
      if (error.response && (error.response.status === 401
        || error.response.status === 403)) { 
        // Code inside this block will refresh the auth token  
        console.log(error);
        let redirectUrl = process.env.NODE_ENV !== 'production'? '' : window._env_.REACT_APP_PARTNER_MANAGER_API_BASE_URL; 
        redirectUrl = redirectUrl + getLoginRedirectUrl(window.location.href);
        console.log(redirectUrl);
        window.location.href = redirectUrl;
      }
      return Promise.reject(error);
    });
}


