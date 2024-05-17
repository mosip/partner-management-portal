import profileIcon from '../profile_icon.png';
import { getUserProfile } from '../services/UserProfileService.js';
import { useState, useRef, useEffect } from 'react';
import { handleMouseClickForDropdown } from '../utils/AppUtils.js';
import { useTranslation } from 'react-i18next';

function HeaderNav({ open, setOpen }) {
    const { t } = useTranslation();
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
        // const cachedAppConfig = await getAppConfig();
        // if (cachedAppConfig)
        // console.log(cachedAppConfig['sbiPorts']);
        localStorage.clear();
        if (process.env.NODE_ENV !== 'production') {
            window.location.href = `/api/logout/user?redirecturi=` + btoa(window.location.href);
        } else {
            window.location.href = `/logout/user?redirecturi=` + btoa(window.location.href);
        }

    }
    return (
        <nav className="flex justify-between w-full h-16 font-inter shadow-[rgba(0,0,0,0.13)_5px_3px_8px_0px] relative">
            <div className="p-6 cursor-pointer" onClick={() => setOpen(!open)}>
                <svg xmlns="http://www.w3.org/2000/svg" height="14" viewBox="0 0 22 14">
                    <path id="menu_FILL0_wght300_GRAD0_opsz24" d="M140-691.384v-1.863h22v1.863Zm0-6.068v-1.863h22v1.863Zm0-6.068v-1.863h22v1.863Z" transform="translate(-140.001 705.384)" fill="#071121" />
                </svg>
            </div>
            <div className="px-5 xl:px-12">
                <div className=" flex-1 justify-evenly mt-6 cursor-pointer">
                    &nbsp;
                </div>
            </div>
            <div className="flex items-center relative justify-between space-x-14">
                <div className="flex items-center">
                    <div className="p-2 bg-blue-50">
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            width="16.106" height="20" viewBox="0 0 16.106 21">
                            <path id="moving_ministry_24dp_FILL0_wght300_GRAD0_opsz24"
                                d="M60-799v-21H76.106v21H69.692v-4.894H66.414V-799Zm1.853-1.853h2.708v-4.894h6.984v4.894h2.708v-17.294h-12.4Zm2.708-7.6H66.7v-2.138H64.561Zm0-4.846H66.7v-2.138H64.561Zm4.846,4.846h2.138v-2.138H69.407Zm0-4.846h2.138v-2.138H69.407Zm-4.846,12.448v-4.894h6.984v0H64.561Z"
                                transform="translate(-60 820)" fill="#1447b2" />
                        </svg>
                    </div>

                    <h2 className="text-xs font-bold text-gray-600 ml-1">{getUserProfile().orgName}</h2>
                </div>
                <div className="flex items-center" ref={dropdownRef}>
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
                        <div className=" absolute top-14 right-10 z-10 w-40 h-33 origin-top-right rounded-md bg-white py-1 shadow-md ring-1 ring-gray-50 focus:outline-none">
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