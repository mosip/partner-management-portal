import axios from 'axios';
import { loginRedirect } from './LoginRedirectService.js';

const HttpService = axios.create({
  withCredentials: true
});

HttpService.interceptors.response.use((response) => { // block to handle success case
  return response;
}, function (error) { // block to handle error case
  const originalRequest = error.config;
  if (error.response.status === 401 && originalRequest.url.split('/').includes('validateToken')) { // Added this condition to avoid infinite loop 
    // Redirect to any unauthorised route to avoid infinite loop...
    loginRedirect(window.location.href);
  }
});

export default HttpService;