import { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../services/UserProfileService.js';
import { isLangRTL } from '../utils/AppUtils.js';
import { handleMouseClickForDropdown, logout } from '../utils/AppUtils.js';
import profileIcon from '../profile_icon.png';
import hamburgerIcon from '../svg/hamburger_icon.svg';
import orgIcon from '../svg/org_icon.svg';
import side_menu_title from '../../src/side_menu_title.svg';
import profileDropDown from '.././svg/profileDropDown.svg'

function HeaderNav({ open, setOpen }) {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const dropdownRef = useRef(null);

    useEffect(() => {
        handleMouseClickForDropdown(dropdownRef, () => setIsDropdownOpen(false));
    }, [dropdownRef]);

    const openDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };

    const moveToMyProfile = () => {
        navigate('/partnermanagement/userProfile')
        setIsDropdownOpen(false)
    };

    return (
        <nav className="sticky top-0 z-50 bg-white flex justify-between w-full h-14 font-inter 
            shadow-[rgba(0,0,0,0.13)_5px_2px_8px_0px] ">
            <div className={`flex gap-x-4 h-14 items-center shadow-sm`}>
                {!open && (
                    <div className={`flex items-center ${isLoginLanguageRTL ? "pr-5" : "pl-6"}`}>
                        <img src={process.env.PUBLIC_URL + '/mosip_icon.svg'} alt="Mosip Icon" className="w-8 h-8" />
                        <div className="p-9 cursor-pointer" onClick={() => setOpen(!open)}>
                            <img className="min-w-5 w-5 h-5" src={hamburgerIcon} alt=""></img>
                        </div>
                    </div>
                )}
                {open && (
                    <div className={`flex items-center w-64 gap-x-4 ${isLoginLanguageRTL ? "pr-5" : "pl-6"} h-14 shadow-md`}>
                        <img src={process.env.PUBLIC_URL + '/mosip_icon.svg'} alt="Mosip Icon" className="w-8 h-8" />
                        <div className={`duration-700`}>
                            <img src={side_menu_title} alt="" className="w-32 h-10" />
                        </div>
                        <div className="cursor-pointer" onClick={() => setOpen(!open)}>
                            <img className="min-w-5" src={hamburgerIcon} alt="" />
                        </div>
                    </div>
                )}
            </div>
            <div className="px-5 xl:px-12">
                <div className=" flex-1 justify-evenly mt-6">
                    &nbsp;
                </div>
            </div>
            <div className={`flex items-center relative justify-between gap-x-12 ${isLoginLanguageRTL ?"left-3":"right-3"}`}>
                <div className="flex items-center">
                    <div className="p-2 m-1 bg-blue-50">
                        <img src={orgIcon} alt="" className="w-5 h-5"></img>
                    </div>

                    <h2 className={`text-xs font-bold text-gray-600 ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>{getUserProfile().orgName}</h2>
                </div>
                <div onClick={openDropdown} className={`flex items-center ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`} ref={dropdownRef}>
                    <button className="relative flex rounded-full text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-transparent">
                        <img className="h-9 w-8 rounded-full" src={profileIcon} alt="" />
                    </button>
                    <h2 className={`text-xs font-bold text-gray-600 cursor-pointer ${isLoginLanguageRTL ? "mr-1": "ml-1"}`}>{getUserProfile().userName}</h2>
                    <img src={profileDropDown} alt="" className={`h-2 mt-[1%] cursor-pointer ${isLoginLanguageRTL ? "mr-2 ml-2" : "ml-2 mr-2"} ${isDropdownOpen ? "rotate-180 duration-500" : "duration-500"}`}/>

                    {isDropdownOpen && (
                        <div className={`absolute top-14 ${isLoginLanguageRTL ? "left-1 origin-top-left" : "right-1 origin-top-right"} z-10 w-40 h-33 rounded-md bg-white py-1 shadow-md ring-1 ring-gray-50 focus:outline-none`}>
                            <button onClick={() => moveToMyProfile()} className={`block px-4 py-2 text-xs text-gray-900 ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                {t('header.userProfile')}
                            </button>
                            <div className="border-gray-100 border-t mx-2"></div>
                            <button className={`block px-4 py-2 text-xs text-red-700 ${isLoginLanguageRTL ? "text-right" : "text-left"}`} onClick={logout}>{t('commons.logout')}</button>
                        </div>
                    )}
                </div>
            </div>
        </nav>
    );
}

export default HeaderNav;