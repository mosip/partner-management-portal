import { createHashRouter, RouterProvider, redirect } from 'react-router-dom';
import GuardedRoute from './auth/GuardedRoute.js';
import MainLayout from './nav/MainLayout.js';
import PartnerCertificatesList from './pages/partner/certificates/PartnerCertificatesList.js';
import PoliciesList from './pages/partner/policies/PoliciesList.js';
import Dashboard from './pages/dashboard/Dashboard.js';
import ViewPolicyDetails from './pages/partner/policies/ViewPolicyDetails.js';
import RequestPolicy from './pages/partner/policies/RequestPolicy';
import OidcClientsList from './pages/partner/authenticationServices/OidcClientsList.js';
import CreateOidcClient from './pages/partner/authenticationServices/CreateOidcClient.js';
import ViewOidcClientDetails from './pages/partner/authenticationServices/ViewOidcClientDetails.js';
import EditOidcClient from './pages/partner/authenticationServices/EditOidcClient.js';
import ApiKeysList from './pages/partner/authenticationServices/ApiKeysList.js';
import GenerateApiKey from './pages/partner/authenticationServices/GenerateApiKey.js';
import ViewApiKeyDetails from './pages/partner/authenticationServices/ViewApiKeyDetails.js';
import RuntimeError from './pages/common/RuntimeError.js';
import UserProfile from './nav/UserProfile.js';
import AddSbi from './pages/partner/deviceProviderServices/AddSbi.js';
import SbiList from './pages/partner/deviceProviderServices/SbiList.js';
import AddDevices from './pages/partner/deviceProviderServices/AddDevices.js';
import ViewDeviceDetails from './pages/partner/deviceProviderServices/ViewDeviceDetails.js';
import DevicesList from './pages/partner/deviceProviderServices/DevicesList.js';
import FtmList from './pages/partner/ftmProviderServices/FtmList.js';
import AddFtm from './pages/partner/ftmProviderServices/AddFtm.js';
import ViewFtmChipDetails from './pages/partner/ftmProviderServices/ViewFtmChipDetails.js';
import RootTrustCertificateList from './pages/admin/certificates/RootTrustCertificateList.js';
import UploadRootOfCertificate from './pages/admin/certificates/UploadRootOfCertificate.js';


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
          path: 'certificates/partnerCertificate',
          element: <GuardedRoute><MainLayout><PartnerCertificatesList/></MainLayout></GuardedRoute>,
        },
        {
          path: 'policies/policiesList',
          element: <GuardedRoute><MainLayout><PoliciesList/></MainLayout></GuardedRoute>,
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
          path: 'authenticationServices/oidcClientsList',
          element: <GuardedRoute><MainLayout><OidcClientsList/></MainLayout></GuardedRoute>,
        },
        {
          path: 'authenticationServices/createOidcClient',
          element: <GuardedRoute><MainLayout><CreateOidcClient/></MainLayout></GuardedRoute>,
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
          path: 'authenticationServices/apiKeysList',
          element: <GuardedRoute><MainLayout><ApiKeysList/></MainLayout></GuardedRoute>,
        },
        {
          path: 'authenticationServices/generateApiKey',
          element: <GuardedRoute><MainLayout><GenerateApiKey/></MainLayout></GuardedRoute>,
        },
        {
          path: 'authenticationServices/viewApiKeyDetails',
          element: <GuardedRoute><MainLayout><ViewApiKeyDetails/></MainLayout></GuardedRoute>,
        },
        {
          path: 'deviceProviderServices/sbiList',
          element: <GuardedRoute><MainLayout><SbiList/></MainLayout></GuardedRoute>,
        },
        {
          path: 'deviceProviderServices/addSbi',
          element: <GuardedRoute><MainLayout><AddSbi/></MainLayout></GuardedRoute>,
        },
        {
          path: 'deviceProviderServices/addDevices',
          element: <GuardedRoute><MainLayout><AddDevices/></MainLayout></GuardedRoute>
        },
        {
          path: 'deviceProviderServices/devicesList',
          element: <GuardedRoute><MainLayout><DevicesList/></MainLayout></GuardedRoute>
        },
        {
          path: 'deviceProviderServices/viewDeviceDetails',
          element: <GuardedRoute><MainLayout><ViewDeviceDetails/></MainLayout></GuardedRoute>
        },
        {
          path: 'ftmChipProviderServices/ftmList',
          element: <GuardedRoute><MainLayout><FtmList/></MainLayout></GuardedRoute>
        },
        {
          path: 'ftmChipProviderServices/addFtm',
          element: <GuardedRoute><MainLayout><AddFtm/></MainLayout></GuardedRoute>
        },
        {
          path: 'ftmChipProviderServices/viewFtmChipDetails',
          element: <GuardedRoute><MainLayout><ViewFtmChipDetails/></MainLayout></GuardedRoute>
        },
        {
          path: 'ftmChipProviderServices/manageFtmChipCertificate',
          element: <GuardedRoute><MainLayout><ViewFtmChipDetails/></MainLayout></GuardedRoute>
        },
        {
          path: 'admin/certificates/rootTrustCertificateList',
          element: <GuardedRoute><MainLayout><RootTrustCertificateList/></MainLayout></GuardedRoute>
        },
        {
          path: 'admin/certificates/uploadRootOfCertificate',
          element: <GuardedRoute><MainLayout><UploadRootOfCertificate/></MainLayout></GuardedRoute>
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