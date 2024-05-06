import { createHashRouter, RouterProvider, Navigate, redirect } from 'react-router-dom';
import GuardedRoute from './GuardedRoute.js';

import MainLayout from '../pages/MainLayout.js';
import NewPage from '../pages/NewPage.js';
import Dashboard from '../pages/Dashboard.js';

function AppRoutes() {
  const router = createHashRouter([
    {
      path: '',
      loader: () => redirect('/partnermanagement')
    },
    {
      path: '/partnermanagement',
      children: [
        { path: '', loader: () => redirect('/partnermanagement/dashboard'),},
        {
          path: 'dashboard',
          element: <GuardedRoute><MainLayout><Dashboard/></MainLayout></GuardedRoute>,
        },
        {
          path: 'partnerCertificate',
          element: <GuardedRoute><MainLayout><PartnerCertificate/></MainLayout></GuardedRoute>,
        }
      ],
    },
  ])
  
  return (<RouterProvider router={router} />);
}

export default AppRoutes;