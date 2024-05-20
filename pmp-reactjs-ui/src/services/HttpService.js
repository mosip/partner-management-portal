import axios from 'axios';
import { loginRedirect } from './LoginRedirectService.js';
import { jwtDecode } from 'jwt-decode';
import { setUserProfile, getUserProfile } from './UserProfileService.js';

let HttpService;

if (process.env.NODE_ENV !== 'production') {
  HttpService = axios.create({
    withCredentials: true
  });
} else {
  HttpService = axios.create({
    withCredentials: true,
    baseURL: window._env_.REACT_APP_API_BASE_URL
  })
}

HttpService.interceptors.response.use((response) => { // block to handle success case
  const originalRequest = response.config;
  console.log("interceptor: " + originalRequest.url);
  if (originalRequest.url.split('/').includes('validateToken')) { // Added this condition to avoid infinite loop 
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
}, function (error) { // block to handle error case
  const originalRequest = error.config;
  if (error.response && error.response.status === 401
    && originalRequest.url.split('/').includes('validateToken')
    && !originalRequest._retry) { // Code inside this block will refresh the auth token
    console.log("response interceptor");
    console.log(error);
    console.log("originalRequest._retry" + originalRequest._retry);
    originalRequest._retry = true;
    return loginRedirect(window.location.href);
  } 
  else {
    return Promise.reject(error);
  }
});

export default HttpService;
