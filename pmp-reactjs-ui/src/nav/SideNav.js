import { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { SideNavMenuItem } from './SideNavMenuItem';
import { useTranslation } from 'react-i18next';

function SideNav({ open }) {
    const location = useLocation();
    const navigate = useNavigate();
    const [activeIcon, setActiveIcon] = useState("");
    let selectedPath = location.pathname;
    const { t } = useTranslation();

    useEffect(() => {
        //console.log(selectedPath);
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
        } else {
            setActiveIcon("home");
        }
    }, [selectedPath]);

    function showHome() {
        navigate('/partnermanagement/dashboard');
        setActiveIcon("home");
    };
    const showPartnerCertificatesList = () => {
        navigate('/partnermanagement/partnerCertificate');
        setActiveIcon("partnerCertificate");
    };
    const showPartnerTypeRequest = () => {
        //navigate('/partnermanagement/partnerTypeRequest');
        setActiveIcon("partnerTypeRequest");
    };
    const showOrganisationUsers = () => {
        //navigate('/partnermanagement/organisationUsers');
        setActiveIcon("organisationUsers");
    };
    const showPolicies = () => {
        navigate('/partnermanagement/policies');
        setActiveIcon("policies");
    };
    const showAuthenticationServices = () => {
        navigate('/partnermanagement/authenticationServices/oidcClientsList');
        setActiveIcon("authenticationServices");
    };
    return (
        <div className="flex font-inter bg-white h-screen z-30">
            <div className={`h-full ${open ? "absolute inset-y-16 w-64" : "absolute inset-y-16 w-20"} 
            flex-col duration-500`}>
                <ul className="pt-3 h-full space-y-5 bg-[#FCFCFC] shadow-[rgba(0,0,0,0.13)_5px_2px_8px_-2px]">
                    <li className="duration-700 cursor-pointer" onClick={() => showHome()}>
                        <SideNavMenuItem title={t('commons.home')} id='home' isExpanded={open} activeIcon={activeIcon} />
                    </li>
                    <li className="duration-700 cursor-pointer" onClick={() => showPartnerTypeRequest()}>
                        <SideNavMenuItem title={t('dashboard.partnerTypeRequest')} id='partnerTypeRequest' isExpanded={open} activeIcon={activeIcon} />
                    </li>
                    <li className="duration-700 cursor-pointer" onClick={() => showOrganisationUsers()}>
                        <SideNavMenuItem title={t('dashboard.organisationUsers')} id='organisationUsers' isExpanded={open} activeIcon={activeIcon} />
                    </li>
                    <li className="duration-700 cursor-pointer" onClick={() => showPartnerCertificatesList()}>
                        <SideNavMenuItem title={t('dashboard.partnerCertificate')} id='partnerCertificate' isExpanded={open} activeIcon={activeIcon} />
                    </li>
                    <li className="duration-700 cursor-pointer" onClick={() => showPolicies()}>
                        <SideNavMenuItem title={t('dashboard.policies')} id='policies' isExpanded={open} activeIcon={activeIcon} />
                    </li>
                    <li className="duration-700 cursor-pointer" onClick={() => showAuthenticationServices()}>
                        <SideNavMenuItem title={t('dashboard.authenticationServices')} id='authenticationServices' isExpanded={open} activeIcon={activeIcon} />
                    </li>
                </ul>
            </div>
        </div>
    );
}

export default SideNav;