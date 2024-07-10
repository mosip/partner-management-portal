import { createHashRouter, RouterProvider, redirect } from 'react-router-dom';
import GuardedRoute from './auth/GuardedRoute.js';
import MainLayout from './nav/MainLayout.js';
import PartnerCertificatesList from './pages/certificates/PartnerCertificatesList.js';
import Policies from './pages/policies/Policies.js';
import Dashboard from './pages/dashboard/Dashboard.js';
import ViewPolicyDetails from './pages/policies/ViewPolicyDetails.js';
import RequestPolicy from './pages/policies/RequestPolicy';
import OidcClientsList from './pages/authenticationServices/OidcClientsList.js';
import CreateOidcClient from './pages/authenticationServices/CreateOidcClient.js';
import ViewOidcClientDetails from './pages/authenticationServices/ViewOidcClientDetails.js';
import EditOidcClient from './pages/authenticationServices/EditOidcClient.js';
import ApiKeysList from './pages/authenticationServices/ApiKeysList.js';
import GenerateApiKey from './pages/authenticationServices/GenerateApiKey.js';
import ViewApiKeyDetails from './pages/authenticationServices/ViewApiKeyDetails.js';
import RuntimeError from './pages/common/RuntimeError.js';
import UserProfile from './nav/UserProfile.js';
import Confirmation from './pages/common/Confirmation.js';


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
          element: <GuardedRoute><MainLayout><Dashboard/></MainLayout></GuardedRoute>,
        },
        {
          path: 'userProfile',
          element: <GuardedRoute><MainLayout><UserProfile/></MainLayout></GuardedRoute>,
        },
        {
          path: 'partnerCertificate',
          element: <GuardedRoute><MainLayout><PartnerCertificatesList/></MainLayout></GuardedRoute>,
        },
        {
          path: 'policies',
          element: <GuardedRoute><MainLayout><Policies/></MainLayout></GuardedRoute>,
        },
        {
          path: 'policies/viewPolicyDetails',
          element: <GuardedRoute><MainLayout><ViewPolicyDetails/></MainLayout></GuardedRoute>,
        },
        {
          path: 'policies/requestPolicy',
          element: <GuardedRoute><MainLayout><RequestPolicy/></MainLayout></GuardedRoute>,
        },
        {
          path: 'policies/requestPolicyConfirmation',
          element: <GuardedRoute><MainLayout><Confirmation/></MainLayout></GuardedRoute>,
        },
        {
          path: 'authenticationServices/oidcClientsList',
          element: <GuardedRoute><MainLayout><OidcClientsList/></MainLayout></GuardedRoute>,
        },
        {
          path: 'authenticationServices/createOidcClient',
          element: <GuardedRoute><MainLayout><CreateOidcClient/></MainLayout></GuardedRoute>,
        },
        {
          path: 'authenticationServices/createOidcClientConfirmation',
          element: <GuardedRoute><MainLayout><Confirmation/></MainLayout></GuardedRoute>,
        },
        {
          path: 'authenticationServices/viewOidcClienDetails',
          element: <GuardedRoute><MainLayout><ViewOidcClientDetails/></MainLayout></GuardedRoute>,
        },
        {
          path: 'authenticationServices/editOidcClient',
          element: <GuardedRoute><MainLayout><EditOidcClient/></MainLayout></GuardedRoute>,
        },
        {
          path: 'authenticationServices/editOidcClientConfirmation',
          element: <GuardedRoute><MainLayout><Confirmation/></MainLayout></GuardedRoute>,
        },
        {
          path: 'authenticationServices/apiKeysList',
          element: <GuardedRoute><MainLayout><ApiKeysList/></MainLayout></GuardedRoute>,
        },
        {
          path: 'authenticationServices/generateApiKey',
          element: <GuardedRoute><MainLayout><GenerateApiKey/></MainLayout></GuardedRoute>,
        },
        {
          path: 'authenticationServices/generateApiKeyConfirmation',
          element: <GuardedRoute><MainLayout><Confirmation/></MainLayout></GuardedRoute>,
        },
        {
          path: 'authenticationServices/viewApiKeyDetails',
          element: <GuardedRoute><MainLayout><ViewApiKeyDetails/></MainLayout></GuardedRoute>,
        },
        {
          path: 'runtimeError',
          element: <RuntimeError/>,
        },
      ],
    },
  ])

  return (<RouterProvider router={router} />);
}

export default AppRoutes;