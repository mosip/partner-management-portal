import { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { SideNavMenuItem } from './SideNavMenuItem';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../services/UserProfileService';

function SideNav({ open, policyRequiredPartnerTypes, partnerType }) {
    const location = useLocation();
    const navigate = useNavigate();
    const [activeIcon, setActiveIcon] = useState("");
    let selectedPath = location.pathname;
    const { t } = useTranslation();
    const [enablePoliciesMenu, setEnablePoliciesMenu] = useState(false);
    const [enableAuthenticationServicesMenu, setEnableAuthenticationServicesMenu] = useState(false);
    const [enableDeviceProviderServicesMenu, setEnableDeviceProviderServicesMenu] = useState(false);
    const [enableFtmServicesMenu, setEnableFtmServicesMenu] = useState(false);
    const [enablePartnerAdminMenu, setEnablePartnerAdminMenu] = useState(false);

    useEffect(() => {
        console.log(selectedPath);
        if (selectedPath.includes('dashboard')) {
            setActiveIcon("home");
        } else if (selectedPath.includes('partnerCertificate')) {
            setActiveIcon("partnerCertificate");
        } else if (selectedPath.includes('partnerTypeRequest')) {
            setActiveIcon("partnerTypeRequest");
        } else if (selectedPath.includes('organisationUsers')) {
            setActiveIcon("organisationUsers");
        } else if (selectedPath.includes('policies')) {
            setActiveIcon("policies");
        } else if (selectedPath.includes('authenticationServices')) {
            setActiveIcon("authenticationServices");
        } else if (selectedPath.includes('deviceProviderServices')) {
            setActiveIcon('deviceProviderServices');
        } else if (selectedPath.includes('ftmChipProviderServices')) {
            setActiveIcon('ftmChipProviderServices');
        } else if (selectedPath.includes('rootTrustCertificateList')) {
            setActiveIcon("rootOfTrustCertificate");
        } else if (selectedPath.includes('partnersList')) {
            setActiveIcon("partner");
        }
        else {
            setActiveIcon("home");
        }
    }, [selectedPath]);

    useEffect(() => {
        console.log("called");
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
        if (getUserProfile().roles.includes('PARTNER_ADMIN' || 'POLICY_MANAGER')) {
            setEnablePartnerAdminMenu(true);
        }
    }, [policyRequiredPartnerTypes, partnerType]);

    function showHome() {
        navigate('/partnermanagement/dashboard');
        setActiveIcon("home");
    };
    const showPartnerCertificatesList = () => {
        navigate('/partnermanagement/certificates/partnerCertificate');
        setActiveIcon("partnerCertificate");
    };
    const showPolicies = () => {
        navigate('/partnermanagement/policies/policiesList');
        setActiveIcon("policies");
    };
    const showAuthenticationServices = () => {
        navigate('/partnermanagement/authenticationServices/oidcClientsList');
        setActiveIcon("authenticationServices");
    };
    const showDeviceProviderServices = () => {
        navigate('/partnermanagement/deviceProviderServices/sbiList');
    };
    const showFtmServices = () => {
        navigate('/partnermanagement/ftmChipProviderServices/ftmList');
    };
    const showPendingRequests = () => {
        setActiveIcon("pendingRequests");
    };
    const showRootOfTrustCertificate = () => {
        navigate('/partnermanagement/admin/certificates/rootTrustCertificateList');
    };
    const showPartner = () => {
        navigate('/partnermanagement/admin/partnersList');
    };
    const showAdminPolicies = () => {
        setActiveIcon("admin_policies");
    };
    const showPartnerPolicyMapping = () => {
        setActiveIcon("partnerPolicyMapping");
    };
    const showSbiDeviceDetails = () => {
        setActiveIcon("sbiDeviceDetails");
    };
    const showAdminFtmDetails = () => {
        setActiveIcon("ftmDetails");
    };
    const showAdminAuthenticationServices = () => {
        setActiveIcon("authenticationServices");
    };

    return (
        <div className="flex font-inter bg-white h-screen z-40">
            <div className={`h-full ${open ? "absolute inset-y-14 w-64" : "absolute inset-y-14 w-[4.5rem]"} 
            flex-col duration-500`}>
                <ul className="pt-3 h-full space-y-5 bg-[#FCFCFC] shadow-[rgba(0,0,0,0.13)_5px_2px_8px_-2px]">
                    <li id='side_nav_home_icon' className="duration-700 cursor-pointer" onClick={() => showHome()} onKeyPress={(e) => { e.key === 'Enter' && showHome() }}>
                        <SideNavMenuItem title={t('commons.home')} id='home' isExpanded={open} activeIcon={activeIcon} />
                    </li>
                    {!enablePartnerAdminMenu &&
                        <li id='side_nav_partner_certificate_icon' className="duration-700 cursor-pointer" onClick={() => showPartnerCertificatesList()} onKeyPress={(e) => { e.key === 'Enter' && showPartnerCertificatesList() }}>
                            <SideNavMenuItem title={t('dashboard.partnerCertificate')} id='partnerCertificate' isExpanded={open} activeIcon={activeIcon} />
                        </li>
                    }
                    {!enablePartnerAdminMenu && enablePoliciesMenu &&
                        <li id='side_nav_policies_icon' className="duration-700 cursor-pointer" onClick={() => showPolicies()} onKeyPress={(e) => { e.key === 'Enter' && showPolicies() }}>
                            <SideNavMenuItem title={t('dashboard.policies')} id='policies' isExpanded={open} activeIcon={activeIcon} />
                        </li>
                    }
                    {!enablePartnerAdminMenu && enableAuthenticationServicesMenu &&
                        <li id='side_nav_authentication_service_icon' className="duration-700 cursor-pointer" onClick={() => showAuthenticationServices()} onKeyPress={(e) => { e.key === 'Enter' && showAuthenticationServices() }}>
                            <SideNavMenuItem title={t('dashboard.authenticationServices')} id='authenticationServices' isExpanded={open} activeIcon={activeIcon} />
                        </li>
                    }
                    {!enablePartnerAdminMenu && enableDeviceProviderServicesMenu &&
                        <li id='side_nav_device_provider_service_icon' className="duration-700 cursor-pointer" onClick={() => showDeviceProviderServices()} onKeyPress={(e) => { e.key === 'Enter' && showDeviceProviderServices() }}>
                            <SideNavMenuItem title={t('dashboard.deviceProviderServices')} id='deviceProviderServices' isExpanded={open} activeIcon={activeIcon} />
                        </li>
                    }
                    {!enablePartnerAdminMenu && enableFtmServicesMenu &&
                        <li id='side_nav_ftmchip_provider_service_icon' className="duration-700 cursor-pointer" onClick={() => showFtmServices()} onKeyPress={(e) => { e.key === 'Enter' && showFtmServices() }}>
                            <SideNavMenuItem title={t('dashboard.ftmChipProviderServices')} id='ftmChipProviderServices' isExpanded={open} activeIcon={activeIcon} />
                        </li>
                    }
                    {enablePartnerAdminMenu && (
                        <>
                            <li id='side_nav_pendingRequests_icon' className="duration-700 cursor-pointer" onClick={() => showPendingRequests()}>
                                <SideNavMenuItem title={t('dashboard.pendingRequests')} id='pendingRequests' isExpanded={open} activeIcon={activeIcon} />
                            </li>
                            <li id='side_nav_rootOfTrustCertificate_service_icon' className="duration-700 cursor-pointer" onClick={() => showRootOfTrustCertificate()}>
                                <SideNavMenuItem title={t('dashboard.rootOfTrustCertificate')} id='rootOfTrustCertificate' isExpanded={open} activeIcon={activeIcon} />
                            </li>
                            <li id='side_nav_partner_icon' className="duration-700 cursor-pointer" onClick={() => showPartner()}>
                                <SideNavMenuItem title={t('dashboard.partner')} id='partner' isExpanded={open} activeIcon={activeIcon} />
                            </li>
                            <li id='side_nav_policy_icon' className="duration-700 cursor-pointer" onClick={() => showAdminPolicies()}>
                                <SideNavMenuItem title={t('dashboard.policy')} id='admin_policies' isExpanded={open} activeIcon={activeIcon} />
                            </li>
                            <li id='side_nav_partnerPolicyMapping_icon' className="duration-700 cursor-pointer" onClick={() => showPartnerPolicyMapping()}>
                                <SideNavMenuItem title={t('dashboard.partnerPolicyMapping')} id='partnerPolicyMapping' isExpanded={open} activeIcon={activeIcon} />
                            </li>
                            <li id='side_nav_sbiDeviceDetails_icon' className="duration-700 cursor-pointer" onClick={() => showSbiDeviceDetails()}>
                                <SideNavMenuItem title={t('dashboard.sbiDevice')} id='sbiDeviceDetails' isExpanded={open} activeIcon={activeIcon} />
                            </li>
                            <li id='side_nav_ftmDetails_icon' className="duration-700 cursor-pointer" onClick={() => showAdminFtmDetails()}>
                                <SideNavMenuItem title={t('dashboard.ftmChip')} id='ftmDetails' isExpanded={open} activeIcon={activeIcon} />
                            </li>
                            <li id='side_nav_ftmDetails_icon' className="duration-700 cursor-pointer" onClick={() => showAdminAuthenticationServices()}>
                                <SideNavMenuItem title={t('dashboard.authenticationServices')} id='authenticationServices' isExpanded={open} activeIcon={activeIcon} />
                            </li>
                        </>
                    )}
                </ul>
            </div>
        </div>
    );
}

export default SideNav;