import profileIcon from '../profile_icon.png';
import { useState } from 'react';

function HeaderNav() {
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const openDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };
    const logout = async () => {
        // const cachedAppConfig = await getAppConfig();
        // if (cachedAppConfig)
        // console.log(cachedAppConfig['sbiPorts']);
        localStorage.clear();
        window.location.href = `/api/logout/user?redirecturi=` + btoa(window.location.href);
    }
    return (
        <nav className="flex justify-between w-screen h-16 font-inter">
            <div className="px-5 xl:px-12">
                <div className=" flex-1 justify-evenly mt-6 cursor-pointer">
                    <svg xmlns="http://www.w3.org/2000/svg" width="22" height="14" viewBox="0 0 22 14">
                        <path id="menu_FILL0_wght300_GRAD0_opsz24" d="M140-691.384v-1.863h22v1.863Zm0-6.068v-1.863h22v1.863Zm0-6.068v-1.863h22v1.863Z" transform="translate(-140.001 705.384)" fill="#071121" />
                    </svg>
                </div>
            </div>
            <div className="px-5 xl:px-12 xl:pr-4 py-3 flex items-center relative">
                <div className="flex items-center">
                <button className="relative flex rounded-full text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-transparent"
                    onClick={openDropdown}>
                    <img className="h-11 w-10 rounded-full" src={profileIcon} alt=""/>
                </button>
                <svg className="w-4 h-4 ml-2 text-gray-800 cursor-pointer" viewBox="0 0 24 24" onClick={openDropdown}>
                    <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="M19 9l-7 7-7-7"
                    ></path>
                </svg>
                </div>
                {isDropdownOpen && (
                <div className=" absolute top-14 right-10 z-10 w-40 h-33 origin-top-right rounded-md bg-white py-1 shadow-md ring-1 ring-gray-50 focus:outline-none">
                    <a href="#" className="block px-4 py-2 text-sm text-gray-900">Partner Profile</a>
                    <div className="border-gray-100 border-t mx-2"></div>
                    <a href="#" className="block px-4 py-2 text-sm text-gray-900">Change Password</a>
                    <div className="border-t border-gray-100 mx-2"></div>
                    <a href="#" className="block px-4 py-2 text-sm text-red-700" onClick={logout}>Logout</a>
                </div>
                )}
            </div>
        </nav>
    );
}

export default HeaderNav;