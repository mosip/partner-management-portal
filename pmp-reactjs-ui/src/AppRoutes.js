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
import UploadRootTrustCertificate from './pages/admin/certificates/UploadRootTrustCertificate.js';
import PartnersList from './pages/admin/partners/PartnersList.js';
import ViewPartnerDetails from './pages/admin/partners/ViewPartnerDetails.js';
import CreatePolicyGroup from './pages/admin/policyManager/CreatePolicyGroup.js';
import PolicyGroupList from './pages/admin/policyManager/PolicyGroupList.js';
import AuthPoliciesList from './pages/admin/policyManager/AuthPoliciesList.js';
import DataSharePoliciesList from './pages/admin/policyManager/DataSharePoliciesList.js';


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
          path: 'user-profile',
          element: <GuardedRoute><MainLayout><UserProfile /></MainLayout></GuardedRoute>,
        },
        {
          path: 'certificates/partner-certificate',
          element: <GuardedRoute><MainLayout><PartnerCertificatesList /></MainLayout></GuardedRoute>,
        },
        {
          path: 'policies/policies-list',
          element: <GuardedRoute><MainLayout><PoliciesList /></MainLayout></GuardedRoute>,
        },
        {
          path: 'policies/view-policy-details',
          element: <GuardedRoute><MainLayout><ViewPolicyDetails /></MainLayout></GuardedRoute>,
        },
        {
          path: 'policies/request-policy',
          element: <GuardedRoute><MainLayout><RequestPolicy /></MainLayout></GuardedRoute>,
        },
        {
          path: 'authentication-services/oidc-clients-list',
          element: <GuardedRoute><MainLayout><OidcClientsList /></MainLayout></GuardedRoute>,
        },
        {
          path: 'authentication-services/create-oidc-client',
          element: <GuardedRoute><MainLayout><CreateOidcClient /></MainLayout></GuardedRoute>,
        },
        {
          path: 'authentication-services/view-oidc-client-details',
          element: <GuardedRoute><MainLayout><ViewOidcClientDetails /></MainLayout></GuardedRoute>,
        },
        {
          path: 'authentication-services/edit-oidc-client',
          element: <GuardedRoute><MainLayout><EditOidcClient /></MainLayout></GuardedRoute>,
        },
        {
          path: 'authentication-services/api-keys-list',
          element: <GuardedRoute><MainLayout><ApiKeysList /></MainLayout></GuardedRoute>,
        },
        {
          path: 'authentication-services/generate-api-key',
          element: <GuardedRoute><MainLayout><GenerateApiKey /></MainLayout></GuardedRoute>,
        },
        {
          path: 'authentication-services/view-api-key-details',
          element: <GuardedRoute><MainLayout><ViewApiKeyDetails /></MainLayout></GuardedRoute>,
        },
        {
          path: 'device-provider-services/sbi-list',
          element: <GuardedRoute><MainLayout><SbiList /></MainLayout></GuardedRoute>,
        },
        {
          path: 'device-provider-services/add-sbi',
          element: <GuardedRoute><MainLayout><AddSbi /></MainLayout></GuardedRoute>,
        },
        {
          path: 'device-provider-services/add-devices',
          element: <GuardedRoute><MainLayout><AddDevices /></MainLayout></GuardedRoute>
        },
        {
          path: 'device-provider-services/devices-list',
          element: <GuardedRoute><MainLayout><DevicesList /></MainLayout></GuardedRoute>
        },
        {
          path: 'device-provider-services/view-device-details',
          element: <GuardedRoute><MainLayout><ViewDeviceDetails /></MainLayout></GuardedRoute>
        },
        {
          path: 'ftm-chip-provider-services/ftm-list',
          element: <GuardedRoute><MainLayout><FtmList /></MainLayout></GuardedRoute>
        },
        {
          path: 'ftm-chip-provider-services/add-ftm',
          element: <GuardedRoute><MainLayout><AddFtm /></MainLayout></GuardedRoute>
        },
        {
          path: 'ftm-chip-provider-services/view-ftm-chip-details',
          element: <GuardedRoute><MainLayout><ViewFtmChipDetails /></MainLayout></GuardedRoute>
        },
        {
          path: 'ftm-chip-provider-services/manage-ftm-chip-certificate',
          element: <GuardedRoute><MainLayout><ViewFtmChipDetails /></MainLayout></GuardedRoute>
        },
        {
          path: 'admin/certificates/root-trust-certificate-list',
          element: <GuardedRoute><MainLayout><RootTrustCertificateList /></MainLayout></GuardedRoute>
        },
        {
          path: 'admin/certificates/upload-root-trust-certificate',
          element: <GuardedRoute><MainLayout><UploadRootTrustCertificate /></MainLayout></GuardedRoute>
        },
        {
          path: 'admin/partners-list',
          element: <GuardedRoute><MainLayout><PartnersList /></MainLayout></GuardedRoute>
        }, 
        {
          path: 'admin/view-partner-details',
          element: <GuardedRoute><MainLayout><ViewPartnerDetails/></MainLayout></GuardedRoute>
        },
        {
          path: 'admin/policy-manager/create-policy-group',
          element: <GuardedRoute><MainLayout><CreatePolicyGroup /></MainLayout></GuardedRoute>
        },
        {
          path: 'admin/policy-manager/policy-group-list',
          element: <GuardedRoute><MainLayout><PolicyGroupList/></MainLayout></GuardedRoute>
        },
        {
          path: 'admin/policy-manager/auth-policies-list',
          element: <GuardedRoute><MainLayout><AuthPoliciesList/></MainLayout></GuardedRoute>
        },
        {
          path: 'admin/policy-manager/data-share-policies-list',
          element: <GuardedRoute><MainLayout><DataSharePoliciesList/></MainLayout></GuardedRoute>
        },
        {
          path: 'runtimeError',
          element: <RuntimeError />,
        },
      ],
    },
  ])

  return (<RouterProvider router={router} />);
}

export default AppRoutes;