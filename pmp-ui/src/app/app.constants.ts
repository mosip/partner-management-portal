import * as config from 'src/assets/config.json';

export const VERSION = '1.0';
export const BASE_URL = config.baseUrl;
export const IDS = 'dummy';
export const URL = {
  centers: `masterdata/registrationcenters/search`,
  partners: `masterdata/registrationcenters/search`,
  devices: `masterdata/devices/search`,
  machines: `masterdata/machines/search`,
  documentCategories: `masterdata/documentcategories`,
  mappedDocUrl: `masterdata/documenttypes/`,
  unMappedDocUrl: `masterdata/documenttypes/`
};
export const navItems = [
  {
    displayName: 'menuItems.item1.title',
    icon: './assets/images/home.svg',
    route: '/pmp/home',
    children: null,
    auditEventId: 'ADM-002',
    roles: []
  },
  {
    displayName: 'menuItems.item2.title',
    icon: 'assets/images/policy.png',
    route: '',
    children: [
      {
        displayName: 'menuItems.item2.subItem1',
        icon: null,
        route: '/pmp/resources/policygroup/view',
        auditEventId: 'ADM-004',
        roles: ['POLICYMANAGER', 'PARTNER_ADMIN']
      },
      {
        displayName: 'menuItems.item2.subItem2',
        icon: null,
        route: '/pmp/resources/authpolicy/view',
        auditEventId: 'ADM-005',
        roles: ['POLICYMANAGER', 'PARTNER_ADMIN']
      },
      {
        displayName: 'menuItems.item2.subItem3',
        icon: null,
        route: '/pmp/resources/datasharepolicy/view',
        auditEventId: 'ADM-005',
        roles: ['POLICYMANAGER', 'PARTNER_ADMIN']
      },
    ],
    auditEventId: 'ADM-003',
    roles: ['POLICYMANAGER', 'PARTNER_ADMIN']
  },
  {
    displayName: 'menuItems.item3.title',
    icon: 'assets/images/Partners.png',
    route: '/pmp/resources/partner/view',
    children: null,
    auditEventId: 'ADM-006',
    roles: ['PARTNER_ADMIN']
  },
  {
    displayName: 'menuItems.item4.title',
    icon: 'assets/images/biometric.png',
    route: '/pmp/resources/devicedetails/view',
    children: null,
    auditEventId: 'ADM-007',
    roles: ['PARTNER_ADMIN', 'DEVICE_PROVIDER']
  },
  {
    displayName: 'menuItems.item5.title',
    icon: 'assets/images/ftm.png',
    route: '/pmp/resources/ftmdetails/view',
    children: null,
    auditEventId: 'ADM-008',
    roles: ['PARTNER_ADMIN', 'FTM_PROVIDER']
  },
  {
    displayName: 'menuItems.item6.title',
    icon: 'assets/images/sbi.png',
    route: '/pmp/resources/sbidetails/view',
    children: null,
    auditEventId: 'ADM-009',
    roles: ['PARTNER_ADMIN', 'DEVICE_PROVIDER']
  },
  {
    displayName: 'menuItems.item7.title',
    icon: 'assets/images/partner-policy mapping.png',
    route: '/pmp/resources/policymapping/view',
    children: null,
    auditEventId: 'ADM-010',
    roles: ['PARTNER_ADMIN', 'AUTH_PARTNER', 'ABIS_PARTNER', 'CREDENTIAL_PARTNER']
  },
  {
    displayName: 'menuItems.item8.title',
    icon: 'assets/images/certificate.png',
    route: '/pmp/resources/uploadcacert/upload',
    children: null,
    auditEventId: 'ADM-011',
    roles: ['PARTNER_ADMIN']
  }
];


export const registrationCreatePartnerId = 'mosip.partnermanagement.partners.create';
export const registrationUpdatePartnerId = 'mosip.partnermanagement.partners.update';

export const MASTERDATA_BASE_URL = `masterdata/`;

export const registrationCenterCreateId = 'string';

export const viewFields = [];

export const masterdataMapping = {
  policygroup: {
    apiName: 'policymanager/policies/group',
    specFileName: 'policy-group',
    name: 'Policy Group',
    nameKey: 'titleName',
    idKey: 'id',
    headerName: 'Policy Group'
  },
  authpolicy: {
    apiName: 'policymanager/policies',
    specFileName: 'auth-policy',
    name: 'Auth Policy',
    nameKey: 'titleName',
    idKey: 'id',
    headerName: 'Auth Policy'
  },
  datasharepolicy: {
    apiName: 'policymanager/policies',
    specFileName: 'data-share-policy',
    name: 'Data Share Policy',
    nameKey: 'titleName',
    idKey: 'id',
    headerName: 'Data Share Policy'
  },
  devicedetails: {
    apiName: 'partnermanager/devicedetail',
    specFileName: 'device-detail',
    name: 'Device Detail',
    nameKey: 'titleName',
    idKey: 'id',
    headerName: 'Device Detail'
  },
  ftmdetails: {
    apiName: 'partnermanager/ftpchipdetail',
    specFileName: 'ftm-detail',
    name: 'FTM Detail',
    nameKey: 'titleName',
    idKey: 'ftpChipDetailId',
    headerName: 'FTM Detail'
  },
  sbidetails: {
    apiName: 'partnermanager/securebiometricinterface',
    specFileName: 'sbi-detail',
    name: 'Secure Biometric Interface',
    nameKey: 'titleName',
    idKey: 'id',
    headerName: 'Secure Biometric Interface'
  },
  policymapping: {
    apiName: 'partnermanager/partners/apikey/request',
    specFileName: 'policy-mapping',
    name: 'Partner Policy Mapping',
    nameKey: 'titleName',
    idKey: 'id',
    headerName: 'Partner Policy Mapping'
  },
  home: {
    apiName: 'partnermanager/partners/apikey/request',
    specFileName: 'home',
    name: 'home',
    nameKey: 'titleName',
    idKey: 'id',
    headerName: 'Policy Mapping'
  }
};

export const ListViewIdKeyMapping = {
  policygroup: { idKey: 'id', auditEventId: 'ADM-069' },
  authpolicy: { idKey: 'id', auditEventId: 'ADM-069' },
  datasharepolicy: { idKey: 'id', auditEventId: 'ADM-069' },
  partner: { idKey: 'id', auditEventId: 'ADM-069' },
  devicedetails: { idKey: 'id', auditEventId: 'ADM-069' },
  ftmdetails: { idKey: 'ftpChipDetailId', auditEventId: 'ADM-069' },
  sbidetails: { idKey: 'id', auditEventId: 'ADM-069' },
  policymapping: { idKey: 'apikeyRequestId', auditEventId: 'ADM-069' },
};

export const keyboardMapping = {
  eng: 'en',
  fra: 'fr',
  ara: 'ar'
};
