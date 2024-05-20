import axios from 'axios';
import { loginRedirect } from './LoginRedirectService.js';
import { jwtDecode } from 'jwt-decode';
import { setUserProfile, getUserProfile } from './UserProfileService.js';

export const HttpService = axios.create({
  withCredentials: true,
  baseURL: process.env.NODE_ENV !== 'production'? '' : window._env_.REACT_APP_API_BASE_URL,
  count: 0, //custom
  retries: 1, //custom
})

export const setupResponseInterceptor = () => {
  HttpService.interceptors.response.use((response) => { // block to handle success case
    const originalRequestUrl = response.config.url;
    console.log("interceptor: " + originalRequestUrl);
    if (originalRequestUrl.split('/').includes('validateToken')) { // Added this condition to avoid infinite loop 
      if (!getUserProfile()) {
        const resp = response.data.response;
        const userData = jwtDecode(resp.token);
        //console.log(resp);
        setUserProfile({
          "userName": userData.preferred_username,
          "firstName": userData.given_name,
          "lastName": userData.family_name,
          "email": userData.email,
          "orgName": userData.organizationName,
          "partnerType": userData.partnerType,
          "langCode": userData.locale,
          "roles": resp.role
        });
      }
    }
    return response;
  },
    (error) => { // block to handle error case
      const { count, retries } = error.config // extract count and retries
      console.log(count);        
      console.log(retries);
      const originalRequestUrl = error.config.url;
      if (error.response && error.response.status === 401
        && originalRequestUrl.split('/').includes('validateToken')
        && count < retries) { 
        // Code inside this block will refresh the auth token  
        console.log(error);
        error.config.count += 1 // update count
        loginRedirect(window.location.href);
      }
      else {
        return Promise.reject(error);
      }
    });
}


