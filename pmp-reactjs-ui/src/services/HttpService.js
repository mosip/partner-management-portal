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
    baseURL: process.env.REACT_APP_API_BASE_URL
  })
}

// Add a request interceptor
HttpService.interceptors.request.use(function (request) {
  // Do something before request is sent
  if (process.env.NODE_ENV === 'production') {
    const originalRequest = request.url;
    console.log(originalRequest);
    request.url = originalRequest.replace("/api", "");
    console.log(request.url);
  }
  return request;
}, function (error) {
  // Do something with request error
  loginRedirect(window.location.href);
});


HttpService.interceptors.response.use((response) => { // block to handle success case
  const originalRequest = response.config;
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
  if (error.response.status === 401 && originalRequest.url.split('/').includes('validateToken')) { // Added this condition to avoid infinite loop 
    // Redirect to any unauthorised route to avoid infinite loop...
    loginRedirect(window.location.href);
  }
});

export default HttpService;