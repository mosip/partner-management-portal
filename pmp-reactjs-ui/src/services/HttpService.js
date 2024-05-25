import axios from 'axios';
import { getLoginRedirectUrl } from './LoginRedirectService.js';
import { jwtDecode } from 'jwt-decode';
import { setUserProfile, getUserProfile } from './UserProfileService.js';

export const HttpService = axios.create({
  withCredentials: true,
  baseURL: process.env.NODE_ENV !== 'production'? '' : window._env_.REACT_APP_PARTNER_MANAGER_API_BASE_URL,
  count: 0, //custom
  retries: 2
})

export const setupResponseInterceptor = () => {
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
    }
    return response;
  },
    (error) => { // block to handle error case
      const { count, retries } = error.config // extract count and retries
      const originalRequestUrl = error.config.url;
      if (error.response && error.response.status === 401
        && originalRequestUrl.split('/').includes('validateToken')
        && count < retries) { 
        // Code inside this block will refresh the auth token  
        console.log(error);
        error.config.count += 1 // update count
        let redirectUrl = process.env.NODE_ENV !== 'production'? '' : window._env_.REACT_APP_PARTNER_MANAGER_API_BASE_URL; 
        redirectUrl = redirectUrl + getLoginRedirectUrl(window.location.href);
        console.log(redirectUrl);
        window.location.href = redirectUrl;
      }
      return Promise.reject(error);
    });
}


