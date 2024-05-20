import { createHashRouter, RouterProvider, redirect } from 'react-router-dom';
import GuardedRoute from './GuardedRoute.js';
import MainLayout from '../pages/MainLayout.js';
import PartnerCertificatesList from '../pages/PartnerCertificatesList.js';
import Policies from '../pages/Policies.js';
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
        { path: '', loader: () => redirect('/partnermanagement/dashboard') },
        {
          path: 'dashboard',
          element: <GuardedRoute><MainLayout><Dashboard /></MainLayout></GuardedRoute>,
        },
        {
          path: 'partnerCertificate',
          element: <GuardedRoute><MainLayout><PartnerCertificatesList /></MainLayout></GuardedRoute>,
        },
        {
          path: 'policies',
          element: <GuardedRoute><MainLayout><Policies /></MainLayout></GuardedRoute>,
        }
      ],
    },
  ])

  return (<RouterProvider router={router} />);
}

export default AppRoutes;