import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { getUserProfile } from '../services/UserProfileService.js';
import { setupResponseInterceptor } from '../services/HttpService.js';
import { HttpService } from "../services/HttpService";
import { getPartnerManagerUrl, createRequest } from "../utils/AppUtils";

const GuardedRoute = ({ children }) => {
  const location = useLocation();
  const navigate = useNavigate();
  const [isUserAuthenticated, setIsUserAuthenticated] = useState(false);

  useEffect(() => {
    setupResponseInterceptor(navigate);

    //if user is accessing partneradmin path
    const isPartnerAdminPath = location.pathname.includes('admin');
    // If user is on a partnerAdmin path, ensure they have admin privileges
    if (isPartnerAdminPath && localStorage.getItem("isAdmin") === 'false') {
      navigate('/partnermanagement/runtimeError', { state: { messageType: 'noAccess', errorCode: '', errorText: '' } });
    };

    const isPolicyManagerPath = location.pathname.includes('admin/policy-manager');
    // If user is on a policy-manager path, ensure they have admin and policy-manager privileges
    if (isPolicyManagerPath && localStorage.getItem("isPolicyManager") === 'false') {
      navigate('/partnermanagement/runtimeError', { state: { messageType: 'noAccess', errorCode: '', errorText: '' } });
    };

  }, [navigate]);

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