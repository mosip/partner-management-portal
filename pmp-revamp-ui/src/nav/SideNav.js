import { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { SideNavMenuItem } from './SideNavMenuItem';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../services/UserProfileService';
import { isLangRTL } from '../utils/AppUtils';

function SideNav({ open, policyRequiredPartnerTypes, partnerType }) {
    const location = useLocation();
    const navigate = useNavigate();
    const [activeIcon, setActiveIcon] = useState("");
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    let selectedPath = location.pathname;
    const { t } = useTranslation();
    const [enablePoliciesMenu, setEnablePoliciesMenu] = useState(false);
    const [enableAuthenticationServicesMenu, setEnableAuthenticationServicesMenu] = useState(false);
    const [enableDeviceProviderServicesMenu, setEnableDeviceProviderServicesMenu] = useState(false);
    const [enableFtmServicesMenu, setEnableFtmServicesMenu] = useState(false);
    const [enablePartnerAdminMenu, setEnablePartnerAdminMenu] = useState(false);
    const [enablePolicyManagerMenu, setEnablePolicyManagerMenu] = useState(false);

    useEffect(() => {
        if (selectedPath.includes('dashboard')) {
            setActiveIcon("home");
        } else if (selectedPath.includes('policy-manager')) {
            setActiveIcon("admin_policies");
        } else if (selectedPath.includes('partner-certificate')) {
            setActiveIcon("partnerCertificate");
        } else if (selectedPath.includes('partner-type-request')) {
            setActiveIcon("partnerTypeRequest");
        } else if (selectedPath.includes('organisation-users')) {
            setActiveIcon("organisationUsers");
        } else if (selectedPath.includes('policies')) {
            setActiveIcon("policies");
        } else if (selectedPath.includes('authentication-services')) {
            setActiveIcon("authenticationServices");
        } else if (selectedPath.includes('device-provider-services')) {
            setActiveIcon('deviceProviderServices');
        } else if (selectedPath.includes('ftm-chip-provider-services')) {
            setActiveIcon('ftmChipProviderServices');
        } else if (selectedPath.includes('admin/certificates')) {
            setActiveIcon("rootOfTrustCertificate");
        } else if (selectedPath.includes('partners-list') || selectedPath.includes('view-partner-details')) {
            setActiveIcon("partner");
        } else if (selectedPath.includes('policy-requests-list') || selectedPath.includes('view-policy-request')) {
            setActiveIcon("partnerPolicyMapping");
        }
        else {
            setActiveIcon("home");
        }
    }, [selectedPath]);

    useEffect(() => {
        if (policyRequiredPartnerTypes.indexOf(partnerType) > -1) {
            setEnablePoliciesMenu(true);
        }
        if (partnerType === "AUTH_PARTNER") {
            setEnableAuthenticationServicesMenu(true);
        }
        if (partnerType === "DEVICE_PROVIDER") {
            setEnableDeviceProviderServicesMenu(true);
        }
        if (partnerType === "FTM_PROVIDER") {
            setEnableFtmServicesMenu(true);
        }
        if (getUserProfile().roles.includes('PARTNER_ADMIN')) {
            setEnablePartnerAdminMenu(true);
        }
        if (getUserProfile().roles.includes('POLICYMANAGER')) {
            setEnablePolicyManagerMenu(true);
        }
    }, [policyRequiredPartnerTypes, partnerType]);

    function showHome() {
        navigate('/partnermanagement/dashboard');
        setActiveIcon("home");
    };
    const showPartnerCertificatesList = () => {
        navigate('/partnermanagement/certificates/partner-certificate');
        setActiveIcon("partnerCertificate");
    };
    const showPolicies = () => {
        navigate('/partnermanagement/policies/policies-list');
        setActiveIcon("policies");
    };
    const showAuthenticationServices = () => {
        navigate('/partnermanagement/authentication-services/oidc-clients-list');
        setActiveIcon("authenticationServices");
    };
    const showDeviceProviderServices = () => {
        navigate('/partnermanagement/device-provider-services/sbi-list');
    };
    const showFtmServices = () => {
        navigate('/partnermanagement/ftm-chip-provider-services/ftm-list');
    };
    const showRootOfTrustCertificate = () => {
        navigate('/partnermanagement/admin/certificates/root-ca-certificate-list');
    };
    const showPartner = () => {
        navigate('/partnermanagement/admin/partners-list');
    };
    const showAdminPolicies = () => {
        navigate('/partnermanagement/policy-manager/policy-group-list');
    };
    const showPartnerPolicyMapping = () => {
        navigate('/partnermanagement/admin/policy-requests-list');
    };
    const showSbiDeviceDetails = () => {
        navigate('/partnermanagement/admin/device-provider-services/sbi-list');
    };
    const showAdminFtmDetails = () => {
        navigate('/partnermanagement/admin/ftm-chip-provider-services/ftm-list');
    };
    const showAdminAuthenticationServices = () => {
        navigate('/partnermanagement/admin/authentication-services/oidc-clients-list');
    };

    return (
        <div className="flex font-inter bg-white h-screen z-40">
            <div className={`h-full ${open ? "absolute inset-y-14 min-w-[16rem]" : "absolute inset-y-14 w-[4.5rem]"} 
            flex-col duration-500`}>
                <div className={` flex flex-col pt-3 h-full space-y-[0.7rem] bg-[#FCFCFC] shadow-[rgba(0,0,0,0.13)_5px_2px_8px_-2px]`}>
                    <button id='side_nav_home_icon' className="duration-700 cursor-pointer" onClick={() => showHome()}>
                        <SideNavMenuItem title={t('commons.home')} id='home' isExpanded={open} activeIcon={activeIcon} />
                    </button>
                    {!enablePartnerAdminMenu && !enablePolicyManagerMenu &&
                        <button id='side_nav_partner_certificate_icon' className="duration-700 cursor-pointer" onClick={() => showPartnerCertificatesList()}>
                            <SideNavMenuItem title={t('dashboard.partnerCertificate')} id='partnerCertificate' isExpanded={open} activeIcon={activeIcon} />
                        </button>
                    }
                    {!enablePartnerAdminMenu && !enablePolicyManagerMenu && enablePoliciesMenu &&
                        <button id='side_nav_policies_icon' className="duration-700 cursor-pointer" onClick={() => showPolicies()}>
                            <SideNavMenuItem title={t('dashboard.policies')} id='policies' isExpanded={open} activeIcon={activeIcon} />
                        </button>
                    }
                    {!enablePartnerAdminMenu && !enablePolicyManagerMenu && enableAuthenticationServicesMenu &&
                        <button id='side_nav_authentication_service_icon' className="duration-700 cursor-pointer" onClick={() => showAuthenticationServices()}>
                            <SideNavMenuItem title={t('dashboard.authenticationServices')} id='authenticationServices' isExpanded={open} activeIcon={activeIcon} />
                        </button>
                    }
                    {!enablePartnerAdminMenu && !enablePolicyManagerMenu && enableDeviceProviderServicesMenu &&
                        <button id='side_nav_device_provider_service_icon' className="duration-700 cursor-pointer" onClick={() => showDeviceProviderServices()}>
                            <SideNavMenuItem title={t('dashboard.deviceProviderServices')} id='deviceProviderServices' isExpanded={open} activeIcon={activeIcon} />
                        </button>
                    }
                    {!enablePartnerAdminMenu && !enablePolicyManagerMenu && enableFtmServicesMenu &&
                        <button id='side_nav_ftmchip_provider_service_icon' className="duration-700 cursor-pointer" onClick={() => showFtmServices()}>
                            <SideNavMenuItem title={t('dashboard.ftmChipProviderServices')} id='ftmChipProviderServices' isExpanded={open} activeIcon={activeIcon} />
                        </button>
                    }
                    {enablePartnerAdminMenu && (
                        <>
                            <button id='side_nav_rootOfTrustCertificate_service_icon' className="duration-700 cursor-pointer" onClick={() => showRootOfTrustCertificate()}>
                                <SideNavMenuItem title={t('dashboard.certificateTrustStore')} id='rootOfTrustCertificate' isExpanded={open} activeIcon={activeIcon} />
                            </button>

                            <button id='side_nav_partner_icon' className="duration-700 cursor-pointer" onClick={() => showPartner()}>
                                <SideNavMenuItem title={t('dashboard.partner')} id='partner' isExpanded={open} activeIcon={activeIcon} />
                            </button>
                        </>
                    )}
                    {(enablePolicyManagerMenu || enablePartnerAdminMenu) && (
                        <button id='side_nav_policy_icon' className="duration-700 cursor-pointer" onClick={() => showAdminPolicies()}>
                            <SideNavMenuItem title={t('dashboard.policies')} id='admin_policies' isExpanded={open} activeIcon={activeIcon} />
                        </button>
                    )}
                    {enablePartnerAdminMenu && (
                        <>
                            <button id='side_nav_partnerPolicyMapping_icon' className={`duration-700 cursor-pointer ${isLoginLanguageRTL ? 'pl-1' : 'pr-1'}`} onClick={() => showPartnerPolicyMapping()}>
                                <SideNavMenuItem title={t('dashboard.partnerPolicyMapping')} id='partnerPolicyMapping' isExpanded={open} activeIcon={activeIcon} />
                            </button>

                            <button id='side_nav_sbiDeviceDetails_icon' className="duration-700 cursor-pointer" onClick={() => showSbiDeviceDetails()}>
                                <SideNavMenuItem title={t('dashboard.sbiDevice')} id='deviceProviderServices' isExpanded={open} activeIcon={activeIcon} />
                            </button>

                            <button id='side_nav_ftmDetails_icon' className="duration-700 cursor-pointer" onClick={() => showAdminFtmDetails()}>
                                <SideNavMenuItem title={t('dashboard.ftmChip')} id='ftmChipProviderServices' isExpanded={open} activeIcon={activeIcon} />
                            </button>
                            
                            <button id='side_nav_authenticationServices_icon' className="duration-700 cursor-pointer" onClick={() => showAdminAuthenticationServices()}>
                                <SideNavMenuItem title={t('dashboard.authenticationServices')} id='authenticationServices' isExpanded={open} activeIcon={activeIcon} />
                            </button>

                        </>
                    )}
                </div>
            </div>
        </div>
    );
}

export default SideNav;