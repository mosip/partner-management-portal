import { createHashRouter, RouterProvider, redirect } from 'react-router-dom';
import GuardedRoute from './auth/GuardedRoute.js';
import MainLayout from './nav/MainLayout.js';
import PartnerCertificatesList from './pages/certificates/PartnerCertificatesList.js';
import Policies from './pages/policies/Policies.js';
import Dashboard from './pages/dashboard/Dashboard.js';
import ViewPolicyDetails from './pages/policies/ViewPolicyDetails.js';
import RequestPolicy from './pages/policies/RequestPolicy';
import StatusOfRequestPolicy from './pages/policies/StatusOfRequestPolicy.js';

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
        },
        {
          path: 'viewPolicyDetails',
          element: <GuardedRoute><MainLayout><ViewPolicyDetails/></MainLayout></GuardedRoute>,
        },
        {
          path: 'requestPolicy',
          element: <GuardedRoute><MainLayout><RequestPolicy/></MainLayout></GuardedRoute>,
        },
        {
          path: 'statusOfRequestPolicy',
          element: <GuardedRoute><MainLayout><StatusOfRequestPolicy/></MainLayout></GuardedRoute>,
        }
      ],
    },
  ])

  return (<RouterProvider router={router} />);
}

export default AppRoutes;