import { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';

import { getUserProfile } from '../services/UserProfileService.js';
import { RTLStyles } from '../utils/AppUtils.js';
import { handleMouseClickForDropdown, getPartnerManagerUrl } from '../utils/AppUtils.js';
import profileIcon from '../profile_icon.png';
import hamburgerIcon from '../svg/hamburger_icon.svg';
import orgIcon from '../svg/org_icon.svg';
import mosip_icon from '../../src/mosip_icon.svg';
import side_menu_title from '../../src/side_menu_title.svg';

function HeaderNav({ open, setOpen }) {
    const { t } = useTranslation();
    const arabicLang = RTLStyles(getUserProfile().langCode);
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const dropdownRef = useRef(null);

    useEffect(() => {
        const clickOutSideDropdown = handleMouseClickForDropdown(dropdownRef, () => setIsDropdownOpen(false));
        return clickOutSideDropdown;
    }, [dropdownRef]);

    const openDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };
    const logout = async () => {
        localStorage.clear();
        let redirectUrl = process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_PARTNER_MANAGER_API_BASE_URL;
        redirectUrl = redirectUrl + getPartnerManagerUrl(`/logout/user?redirecturi=` + btoa(window.location.href), process.env.NODE_ENV);
        console.log(redirectUrl);
        window.location.href = redirectUrl;
    }

    return (
        <nav className="sticky top-0 z-40 bg-white flex justify-between w-full h-16 font-inter 
            shadow-[rgba(0,0,0,0.13)_5px_2px_8px_0px] ">
            <div className={`flex gap-x-4 h-16 items-center shadow-sm`}>
                {!open && (
                    <div className={`flex items-center ${arabicLang ? "pr-5" : "pl-6"}`}>
                        <img src={mosip_icon} alt="" />
                        <div className="p-9 cursor-pointer" onClick={() => setOpen(!open)}>
                            <img src={hamburgerIcon} alt=""></img>
                        </div>
                    </div>
                )}
                {open && (
                    <div className={`flex items-center w-64 gap-x-4 ${arabicLang ? "pr-5" : "pl-6"} h-16 shadow-md`}>
                        <img src={mosip_icon} alt="" />
                        <div className={`duration-700`}>
                            <img src={side_menu_title} alt="" />
                        </div>
                        <div className="cursor-pointer" onClick={() => setOpen(!open)}>
                            <img src={hamburgerIcon} alt=""></img>
                        </div>
                    </div>
                )}
            </div>
            <div className="px-5 xl:px-12">
                <div className=" flex-1 justify-evenly mt-6">
                    &nbsp;
                </div>
            </div>
            <div className="flex items-center relative justify-between gap-x-12">
                <div className="flex items-center">
                    <div className="p-2 bg-blue-50">
                        <img src={orgIcon} alt=""></img>
                    </div>

                    <h2 className="text-xs font-bold text-gray-600 ml-1">{getUserProfile().orgName}</h2>
                </div>
                <div className="flex items-center mr-2" ref={dropdownRef}>
                    <button className="relative flex rounded-full text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-transparent"
                        onClick={openDropdown}>
                        <img className="h-11 w-10 rounded-full" src={profileIcon} alt="" />
                    </button>
                    <svg className="w-4 h-4 ml-2 text-gray-800 cursor-pointer" viewBox="0 0 24 24" onClick={openDropdown}>
                        <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            strokeWidth="2"
                            d="M19 9l-7 7-7-7"
                        ></path>
                    </svg>
                    {isDropdownOpen && (
                        <div className=" absolute top-14 right-7 z-10 w-40 h-33 origin-top-right rounded-md bg-white py-1 shadow-md ring-1 ring-gray-50 focus:outline-none">
                            <button className="block px-4 py-2 text-sm text-gray-900 text-left">{t('header.partnerProfile')}</button>
                            <div className="border-gray-100 border-t mx-2"></div>
                            <button className="block px-4 py-2 text-sm text-gray-900 text-left">{t('header.changePassword')}</button>
                            <div className="border-t border-gray-100 mx-2"></div>
                            <button className="block px-4 py-2 text-sm text-red-700 text-left" onClick={logout}>{t('header.logout')}</button>
                        </div>
                    )}
                </div>
            </div>
        </nav>
    );
}

export default HeaderNav;