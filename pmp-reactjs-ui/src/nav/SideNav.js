import { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { SideNavMenuItem } from './SideNavMenuItem';
import { useTranslation } from 'react-i18next';

function SideNav({ open, policyRequiredPartnerTypes, partnerType }) {
    const location = useLocation();
    const navigate = useNavigate();
    const [activeIcon, setActiveIcon] = useState("");
    let selectedPath = location.pathname;
    const { t } = useTranslation();
    const [enablePoliciesMenu, setEnablePoliciesMenu] = useState(false);
    const [enableAuthenticationServicesMenu, setEnableAuthenticationServicesMenu] = useState(false);

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

    useEffect(() => {
        console.log("called");
        if (policyRequiredPartnerTypes.indexOf(partnerType) > -1) {
            setEnablePoliciesMenu(true);
        }
        if (partnerType === "AUTH_PARTNER") {
            setEnableAuthenticationServicesMenu(true);
        }
    }, [policyRequiredPartnerTypes, partnerType]);

    function showHome() {
        navigate('/partnermanagement/dashboard');
        setActiveIcon("home");
    };
    const showPartnerCertificatesList = () => {
        navigate('/partnermanagement/partnerCertificate');
        setActiveIcon("partnerCertificate");
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
        <div className="flex font-inter bg-white h-screen z-40">
            <div className={`h-full ${open ? "absolute inset-y-14 w-64" : "absolute inset-y-14 w-[4.5rem]"} 
            flex-col duration-500`}>
                <ul className="pt-3 h-full space-y-5 bg-[#FCFCFC] shadow-[rgba(0,0,0,0.13)_5px_2px_8px_-2px]">
                    <li className="duration-700 cursor-pointer" onClick={() => showHome()} onKeyPress={(e)=>{e.key==='Enter' && showHome()}}>
                        <SideNavMenuItem title={t('commons.home')} id='home' isExpanded={open} activeIcon={activeIcon}/>
                    </li>
                    <li className="duration-700 cursor-pointer" onClick={() => showPartnerCertificatesList()} onKeyPress={(e)=>{e.key==='Enter' && showPartnerCertificatesList()}}>
                        <SideNavMenuItem title={t('dashboard.partnerCertificate')} id='partnerCertificate' isExpanded={open} activeIcon={activeIcon} />
                    </li>
                    {enablePoliciesMenu &&
                        <li className="duration-700 cursor-pointer" onClick={() => showPolicies()} onKeyPress={(e)=>{e.key==='Enter' && showPolicies()}}>
                            <SideNavMenuItem title={t('dashboard.policies')} id='policies' isExpanded={open} activeIcon={activeIcon} />
                        </li>
                    }
                    {enableAuthenticationServicesMenu &&
                        <li className="duration-700 cursor-pointer" onClick={() => showAuthenticationServices()} onKeyPress={(e)=>{e.key==='Enter' && showAuthenticationServices()}}>
                            <SideNavMenuItem title={t('dashboard.authenticationServices')} id='authenticationServices' isExpanded={open} activeIcon={activeIcon} />
                        </li>
                    }
                </ul>
            </div>
        </div>
    );
}

export default SideNav;