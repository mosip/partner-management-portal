import { HttpService } from "../services/HttpService";
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { getPartnerManagerUrl, createRequest } from "../utils/AppUtils";
import { getUserProfile } from '../services/UserProfileService.js';

const GuardedRoute = ({ children }) => {
  const location = useLocation();
  const [isUserAuthenticated, setIsUserAuthenticated] = useState(false);

  useEffect(() => {
    async function fetchData() {
      try {
        const apiResp = await HttpService
          .get(getPartnerManagerUrl(`/authorize/admin/validateToken`, process.env.NODE_ENV));
        if (apiResp && apiResp.status === 200 && apiResp.data.response) {
          console.log(`isAuthenticated: yes`);

          //in case user has a new started session on any page other than dashboard 
          //and he is not a registered user, then we want to forecfully redirect him to dashboard 
          //in the Axios Http Interceptor
          const userProfile = getUserProfile();
          const currentPath = location.pathname;
          const isDashboard = currentPath.split('/').includes('dashboard') ? true : false;
          if (userProfile && !isDashboard) {
            const verifyEmailRequest = createRequest({
              "emailId": userProfile.email
            });
            await HttpService.put(
              getPartnerManagerUrl('/partners/email/verify', process.env.NODE_ENV), verifyEmailRequest);
          }
          setIsUserAuthenticated(true);
        } else {
          console.log(`isAuthenticated: no`);
          setIsUserAuthenticated(false);
        }
      } catch (err) {
        console.log('isAuthenticated error:', err);
        setIsUserAuthenticated(false);
      }
    }
    fetchData();
  }, []);

  return (
    isUserAuthenticated ? children : null
  )
}

export default GuardedRoute;