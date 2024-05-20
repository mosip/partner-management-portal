import HttpService from "../services/HttpService.js";
import { useEffect, useState } from 'react';
import { getUrl } from "../utils/AppUtils";

const GuardedRoute = ({ children }) => {

  const [isUserAuthenticated, setIsUserAuthenticated] = useState(false);

  useEffect(() => {
    async function fetchData() {
      try {
        const apiResp = await HttpService
          .get(getUrl(`/authorize/admin/validateToken`, process.env.NODE_ENV));
        if (apiResp && apiResp.status === 200 && apiResp.data.response) {
          console.log(`isAuthenticated: yes`);
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