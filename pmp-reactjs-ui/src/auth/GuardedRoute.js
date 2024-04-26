import HttpService from "../services/HttpService.js";
import { useEffect, useState } from 'react';

const GuardedRoute = ({ children }) => {
  
  const [isUserAuthenticated, setIsUserAuthenticated] = useState(false);

  useEffect(() => {
    async function fetchData() {
      try {
        const apiResp = await HttpService
          .get(`/api/authorize/admin/validateToken`);
        if (apiResp.status === 200 && apiResp.data.response) {
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