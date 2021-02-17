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
    roles: ['GLOBAL_ADMIN']
  },
  {
    displayName: 'menuItems.item2.title',
    icon: 'assets/images/support.svg',
    route: '',
    children: [
      {
        displayName: 'menuItems.item2.subItem1',
        icon: null,
        route: '/pmp/resources/policygroup/view',
        auditEventId: 'ADM-004',
        roles: ['POLICYMANAGER', 'GLOBAL_ADMIN']
      },
      {
        displayName: 'menuItems.item2.subItem2',
        icon: null,
        route: '/pmp/resources/authpolicy/view',
        auditEventId: 'ADM-005',
        roles: ['POLICYMANAGER', 'GLOBAL_ADMIN']
      },
      {
        displayName: 'menuItems.item2.subItem3',
        icon: null,
        route: '/pmp/resources/datasharepolicy/view',
        auditEventId: 'ADM-005',
        roles: ['POLICYMANAGER', 'GLOBAL_ADMIN']
      },
    ],
    auditEventId: 'ADM-003',
    roles: ['POLICYMANAGER', 'GLOBAL_ADMIN']
  },
  {
    displayName: 'Partner',
    icon: 'assets/images/support.svg',
    route: '/pmp/resources/partner/view',
    children: null,
    auditEventId: 'ADM-006',
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
    apiName: 'policies/policyGroup',
    specFileName: 'policy-group',
    name: 'Policy Group',
    nameKey: 'titleName',
    idKey: 'id',
    headerName: 'Policy Group'
  },
  authpolicy: {
    apiName: 'policies/policy',
    specFileName: 'auth-policy',
    name: 'Auth Policy',
    nameKey: 'titleName',
    idKey: 'id',
    headerName: 'Auth Policy'
  },
  datasharepolicy: {
    apiName: 'policies/policy',
    specFileName: 'data-share-policy',
    name: 'Data Share Policy',
    nameKey: 'titleName',
    idKey: 'id',
    headerName: 'Data Share Policy'
  },
  devicedetails: {
    apiName: 'partners/devicedetail',
    specFileName: 'device-detail',
    name: 'Device Detail',
    nameKey: 'titleName',
    idKey: 'id',
    headerName: 'Device Detail'
  },
  ftmdetails: {
    apiName: 'partners/ftpchipdetail',
    specFileName: 'ftm-detail',
    name: 'FTM Detail',
    nameKey: 'titleName',
    idKey: 'ftpChipDetailId',
    headerName: 'FTM Detail'
  },
  sbidetails: {
    apiName: 'partners/securebiometricinterface',
    specFileName: 'sbi-detail',
    name: 'Device Detail',
    nameKey: 'titleName',
    idKey: 'id',
    headerName: 'Secure Biometric Interface'
  },
  policymapping: {
    apiName: 'partners/partners/apikeyRequest',
    specFileName: 'policy-mapping',
    name: 'Partner Policy Mapping',
    nameKey: 'titleName',
    idKey: 'id',
    headerName: 'Partner Policy Mapping'
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
  policymapping: { idKey: 'id', auditEventId: 'ADM-069' },
};

export const keyboardMapping = {
  eng: 'en',
  fra: 'fr',
  ara: 'ar'
};
