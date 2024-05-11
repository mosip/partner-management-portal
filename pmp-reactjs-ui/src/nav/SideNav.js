import { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { SideNavMenuItem } from './SideNavMenuItem';
import mosip_icon from '../../src/mosip_icon.svg';
import side_menu_title from '../../src/side_menu_title.svg';

function SideNav() {
    const location = useLocation();
    const navigate = useNavigate();
    const [open, setOpen] = useState(false);
    const [activeIcon, setActiveIcon] = useState("");
    let selectedPath = location.pathname;

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
        //navigate('/partnermanagement/policies');
        setActiveIcon("policies");
    };
    const showAuthenticationServices = () => {
        //navigate('/partnermanagement/authenticationServices');
        setActiveIcon("authenticationServices");
    };
    return (
        <div className="flex font-inter">
            <div className={`${open ? "w-60" : "w-20 "} flex-col h-screen relative duration-400`}>
                <div className={`flex pl-5 gap-x-4 h-16 items-center justify-evenly ${open ? 'shadow-md' : 'shadow-sm'}`}>
                    <div className="flex gap-x-1 items-center">
                        <img className="flex" src={mosip_icon} />
                        <div className={`${!open && 'scale-0'} items-center duration-300`}>
                            <img src={side_menu_title} />
                        </div>
                    </div>
                    <div className="mt-2 cursor-pointer" onClick={() => setOpen(!open)}>
                        <svg xmlns="http://www.w3.org/2000/svg" height="14" viewBox="0 0 22 14">
                            <path id="menu_FILL0_wght300_GRAD0_opsz24" d="M140-691.384v-1.863h22v1.863Zm0-6.068v-1.863h22v1.863Zm0-6.068v-1.863h22v1.863Z" transform="translate(-140.001 705.384)" fill="#071121" />
                        </svg>
                    </div>
                </div>
                <ul>
                    <li className="duration-700 cursor-pointer" onClick={() => showHome()}>
                        <SideNavMenuItem title='Home' id='home' isExpanded={open} activeIcon={activeIcon} />
                    </li>
                    <li className="duration-700 cursor-pointer" onClick={() => showPartnerTypeRequest()}>
                        <SideNavMenuItem title='Partner Type Selection' id='partnerTypeRequest' isExpanded={open} activeIcon={activeIcon} />
                    </li>
                    <li className="duration-700 cursor-pointer" onClick={() => showOrganisationUsers()}>
                        <SideNavMenuItem title='Organisation Users' id='organisationUsers' isExpanded={open} activeIcon={activeIcon} />
                    </li>
                    <li className="duration-700 cursor-pointer" onClick={() => showPartnerCertificatesList()}>
                        <SideNavMenuItem title='Partner Certificate' id='partnerCertificate' isExpanded={open} activeIcon={activeIcon} />
                    </li>
                    <li className="duration-700 cursor-pointer" onClick={() => showPolicies()}>
                        <SideNavMenuItem title='Policies' id='policies' isExpanded={open} activeIcon={activeIcon} />
                    </li>
                    <li className="duration-700 cursor-pointer" onClick={() => showAuthenticationServices()}>
                        <SideNavMenuItem title='Authentication Services' id='authenticationServices' isExpanded={open} activeIcon={activeIcon} />
                    </li>
                </ul>
            </div>
        </div>
    );
}

export default SideNav;