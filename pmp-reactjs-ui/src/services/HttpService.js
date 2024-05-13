import axios from 'axios';
import { loginRedirect } from './LoginRedirectService.js';
import { jwtDecode } from 'jwt-decode';
import { setUserProfile, getUserProfile } from './UserProfileService.js';

const HttpService = axios.create({
  withCredentials: true
});

HttpService.interceptors.response.use((response) => { // block to handle success case
  const originalRequest = response.config;
  if (originalRequest.url.split('/').includes('validateToken')) { // Added this condition to avoid infinite loop 
    if (!getUserProfile()) {
      const resp = response.data.response;
      const userData = jwtDecode(resp.token);
      //console.log(userData);
      setUserProfile({
        "userName": userData.preferred_username,
        "firstName": userData.given_name,
        "lastName": userData.family_name,
        "email": userData.email,
        "orgName": userData.organizationName,
        "partnerType": userData.partnerType
      });
    }
  }
  return response;
}, function (error) { // block to handle error case
  const originalRequest = error.config;
  if (error.response.status === 401 && originalRequest.url.split('/').includes('validateToken')) { // Added this condition to avoid infinite loop 
    // Redirect to any unauthorised route to avoid infinite loop...
    loginRedirect(window.location.href);
  }
});

export default HttpService;