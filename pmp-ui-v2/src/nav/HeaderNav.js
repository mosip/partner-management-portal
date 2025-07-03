import { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useLocation, useNavigate } from 'react-router-dom';
import { getUserProfile } from '../services/UserProfileService.js';
import { isLangRTL, onPressEnterKey, handleMouseClickForDropdown, logout, getPartnerManagerUrl, fetchNotificationsList, createRequest } from '../utils/AppUtils.js';
import profileIcon from '../profile_icon.png';
import hamburgerIcon from '../svg/hamburger_icon.svg';
import orgIcon from '../svg/org_icon.svg';
import side_menu_title from '../../src/side_menu_title.svg';
import profileDropDown from '.././svg/profileDropDown.svg';
import NotificationPopup from '../pages/common/NotificationPopup.js';
import { HttpService } from '../services/HttpService.js';
import { useDispatch, useStore } from 'react-redux';
import { updateDismissClicked, updateLastSeenDtimes } from '../notificationsSlice.js';
import PropTypes from 'prop-types';

function HeaderNav({ open, setOpen }) {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const location = useLocation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const dropdownRef = useRef(null);
    const notificationRef = useRef(null);
    const [openNotification, setOpenNotification] = useState(false);
    const [showLatestNotificationIcon, setShowLatestNotificationIcon] = useState(false);
    const [isEmailVerified, setIsEmailVerified] = useState(false);
    const dispatch = useDispatch();
    const store = useStore();
    const [dropdownWidth, setDropdownWidth] = useState(0);

    useEffect(() => {
        const verifyUserEmail = async () => {
            try {
                const { email } = getUserProfile();
                const request = createRequest({ emailId: email });

                const response = await HttpService.put(
                    getPartnerManagerUrl('/partners/email/verify', process.env.NODE_ENV),
                    request
                );
                const emailExists = response?.data?.response?.emailExists || false;
                setIsEmailVerified(emailExists);
            } catch (error) {
                console.error('Error verifying email:', error);
            }
        };

        verifyUserEmail();
    }, []);
      
    useEffect(() => {
        handleMouseClickForDropdown(dropdownRef, () => setIsDropdownOpen(false));
        // Only attach handler to close notification if it's open
        if (openNotification) {
            handleMouseClickForDropdown(notificationRef, () => closeNotificationPanel());
        }
    }, [dropdownRef, notificationRef, openNotification]);

    useEffect(() => {
        if (dropdownRef.current) {
            document.documentElement.style.setProperty('--dropdown-width', `${dropdownRef.current.offsetWidth}px`);
            setDropdownWidth(dropdownRef.current.offsetWidth);
        }
    }, [isDropdownOpen, getUserProfile().userName]);

    useEffect(() => {
        async function fetchNotificationsData() {
            const notificationsSeenTimestamp = await fetchNotificationsSeenTimestamp();
            const notifications = await fetchNotificationsList(dispatch);
            console.log("last seen time : ", notificationsSeenTimestamp);
    
            if (notificationsSeenTimestamp === null && notifications.length === 0) {
                setShowLatestNotificationIcon(false);
            } else if (notificationsSeenTimestamp === null && notifications.length > 0) {
                setShowLatestNotificationIcon(true);
            } else {
                if (notifications.length === 0) {
                    setShowLatestNotificationIcon(false);
                } else {
                    const latestNotificationCrdtimes = notifications[0].createdDateTime;
                    console.log("latest notification created time : ", latestNotificationCrdtimes);
                    const lastSeenDate = new Date(notificationsSeenTimestamp);
                    const latestNotificationDate = new Date(latestNotificationCrdtimes);
    
                    if (latestNotificationDate > lastSeenDate) {
                        setShowLatestNotificationIcon(true);
                    } else {
                        setShowLatestNotificationIcon(false);
                    }
                }
            }
        }
    
        // Fetch refresh time from localStorage before setting interval
        let refreshTime = 300; // Default to 5 min(300seconds)
        const config = localStorage.getItem("appConfig");
    
        try {
            if (config) {
                const configData = JSON.parse(config);
                if (configData?.refreshNotificationsTime) {
                    refreshTime = Number(configData.refreshNotificationsTime);
                }
            }
        } catch (error) {
            console.error("Error fetching refresh notification time:", error);
        }
    
        // Only call fetchNotificationsData if email is verified
        if (isEmailVerified) {
            fetchNotificationsData();
        }
    
        // Set up an interval to call the function every 5 minutes
        const intervalId = setInterval(() => {
            if (isEmailVerified) {
                fetchNotificationsData();
            }
        }, 1000 * refreshTime);
    
        // Cleanup function to clear the interval when component unmounts
        return () => clearInterval(intervalId);
    
    }, [isEmailVerified]);
    

    const fetchNotificationsSeenTimestamp = async () => {
        try {
          const response = await HttpService.get(getPartnerManagerUrl(`/users/${getUserProfile().userName}/notifications-seen-timestamp`, process.env.NODE_ENV));
          if (response) {
            const responseData = response.data;
            if (responseData && responseData.response) {
              const resData = responseData.response;
              dispatch(updateLastSeenDtimes(resData.notificationsSeenDtimes));
              return resData.notificationsSeenDtimes;
            } else {
              return null;
            }
          } else {
            return null;
          }
        } catch (err) {
          console.log("Error: ", err);
          return null;
        }
    };

    const openDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };

    const moveToMyProfile = () => {
        navigate('/partnermanagement/user-profile')
        setIsDropdownOpen(false)
    };

    const openNotificationPopup = () => {
        if (openNotification) {
            closeNotificationPanel();
        } else {
            setOpenNotification(true);
        }
    }

    const closeNotificationPanel = () => {
        const dismissClicked = store.getState().headerNotifications.dismissClicked; 
        if (location.pathname.includes('notifications') && dismissClicked) {
            window.location.reload()
        } else {
            setOpenNotification(false);
            setShowLatestNotificationIcon(false);
        }
        const notificationSeenDtimes = store.getState().headerNotifications.notificationSeenDtimes;
        if (notificationSeenDtimes !== null) {
            dispatch(updateLastSeenDtimes(notificationSeenDtimes));
        }
        if(dismissClicked) {
            dispatch(updateDismissClicked(false));
        }
    }

    return (
        <nav className="sticky top-0 z-50 bg-white flex justify-between w-full h-14 font-inter 
            shadow-[rgba(0,0,0,0.13)_5px_2px_8px_0px] ">
            <div className={`flex gap-x-4 h-14 items-center shadow-sm`}>
                {!open && (
                    <div className={`flex items-center ${isLoginLanguageRTL ? "pr-5" : "pl-6"}`}>
                        <img src={process.env.PUBLIC_URL + '/mosip_icon.svg'} alt="Mosip Icon" className="w-8 h-8" />
                        <div id='header_hamburger_open_sidenav' className="p-9 cursor-pointer">
                            <button id='hamburger_close_icon' className="min-w-5 w-5 h-5" onClick={() => setOpen(!open)}>
                                <img alt="" src={hamburgerIcon} />
                            </button>
                        </div>
                    </div>
                )}
                {open && (
                    <div className={`flex items-center w-64 gap-x-4 ${isLoginLanguageRTL ? "pr-5" : "pl-6"} h-14 shadow-md`}>
                        <img src={process.env.PUBLIC_URL + '/mosip_icon.svg'} alt="Mosip Icon" className="w-8 h-8" />
                        <div className={`duration-700`}>
                            <img src={side_menu_title} alt="" className="w-32 h-10" />
                        </div>
                        <div id='header_hamburger_close_sidenav' className="cursor-pointer" >
                            <button id=' hamburger_open_icon' className="min-w-5" onClick={() => setOpen(!open)}>
                                <img src={hamburgerIcon} alt="" />
                            </button>
                        </div>
                    </div>
                )}
            </div>
            <div className="px-5 xl:px-12">
                <div className=" flex-1 justify-evenly mt-6">
                    &nbsp;
                </div>
            </div>
            <div className={`flex items-center relative justify-between gap-x-4 ${isLoginLanguageRTL ? "left-3" : "right-3"}`}>
                <div className="flex items-center relative" ref={notificationRef}>
                    {!showLatestNotificationIcon ? (
                        <button className='p-1.5 bg-blue-50 cursor-pointer' onClick={openNotificationPopup}>
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M4 19.85V17.75H6.1V10.4C6.1 8.9475 6.5375 7.65687 7.4125 6.52812C8.2875 5.39937 9.425 4.66 10.825 4.31V3.575C10.825 3.1375 10.9781 2.76562 11.2844 2.45937C11.5906 2.15312 11.9625 2 12.4 2C12.8375 2 13.2094 2.15312 13.5156 2.45937C13.8219 2.76562 13.975 3.1375 13.975 3.575V4.31C15.375 4.66 16.5125 5.39937 17.3875 6.52812C18.2625 7.65687 18.7 8.9475 18.7 10.4V17.75H20.8V19.85H4ZM12.4 23C11.8225 23 11.3281 22.7944 10.9169 22.3831C10.5056 21.9719 10.3 21.4775 10.3 20.9H14.5C14.5 21.4775 14.2944 21.9719 13.8831 22.3831C13.4719 22.7944 12.9775 23 12.4 23ZM8.2 17.75H16.6V10.4C16.6 9.245 16.1887 8.25625 15.3663 7.43375C14.5437 6.61125 13.555 6.2 12.4 6.2C11.245 6.2 10.2562 6.61125 9.43375 7.43375C8.61125 8.25625 8.2 9.245 8.2 10.4V17.75Z" fill="#1447B2"/>
                            </svg>
                        </button>
                    ) : (
                        <button className="relative p-1.5 bg-blue-50 cursor-pointer" onClick={openNotificationPopup}>
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M4 19.85V17.75H6.1V10.4C6.1 8.9475 6.5375 7.65687 7.4125 6.52812C8.2875 5.39937 9.425 4.66 10.825 4.31V3.575C10.825 3.1375 10.9781 2.76562 11.2844 2.45937C11.5906 2.15312 11.9625 2 12.4 2C12.8375 2 13.2094 2.15312 13.5156 2.45937C13.8219 2.76562 13.975 3.1375 13.975 3.575V4.31C15.375 4.66 16.5125 5.39937 17.3875 6.52812C18.2625 7.65687 18.7 8.9475 18.7 10.4V17.75H20.8V19.85H4ZM12.4 23C11.8225 23 11.3281 22.7944 10.9169 22.3831C10.5056 21.9719 10.3 21.4775 10.3 20.9H14.5C14.5 21.4775 14.2944 21.9719 13.8831 22.3831C13.4719 22.7944 12.9775 23 12.4 23ZM8.2 17.75H16.6V10.4C16.6 9.245 16.1887 8.25625 15.3663 7.43375C14.5437 6.61125 13.555 6.2 12.4 6.2C11.245 6.2 10.2562 6.61125 9.43375 7.43375C8.61125 8.25625 8.2 9.245 8.2 10.4V17.75Z" fill="#1447B2"/>
                            </svg>
                            <div className="absolute -top-1 -right-1">
                                <svg width="10" height="10" viewBox="0 0 10 10" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <circle cx="5" cy="5" r="5" fill="#ED4537"/>
                                </svg>
                            </div>
                        </button>
                    )}
                    { openNotification && (
                        <div className={`inset-0 bg-black bg-opacity-0 z-40 cursor-default`}>
                            <NotificationPopup
                                closeNotification={closeNotificationPanel}
                            />
                        </div>
                    )}
                </div>
                <div className="flex items-center">
                    <div className="p-2 m-1 bg-blue-50">
                        <img id='orgIcon' src={orgIcon} alt="" className="w-5 h-5" />
                    </div>

                    <h2 className={`text-xs font-bold text-gray-600 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{getUserProfile().orgName}</h2>
                </div>
                <div role='button' onClick={openDropdown} className={`flex items-center ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`} ref={dropdownRef} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, openDropdown)}>
                    <button id='header_user_profile_icon_btn' className="relative flex rounded-full text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-transparent">
                        <img id='orgIcon' className="h-9 w-8 rounded-full" src={profileIcon} alt="" />
                    </button>
                    <h2 id='header_user_profile_title' className={`text-xs font-bold text-gray-600 cursor-pointer ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{getUserProfile().userName}</h2>
                    <img id="profileDropDown" src={profileDropDown} alt="" className={`h-2 mt-[1%] cursor-pointer ${isLoginLanguageRTL ? "mr-2 ml-2" : "ml-2 mr-2"} ${isDropdownOpen ? "rotate-180 duration-500" : "duration-500"}`} />

                    {isDropdownOpen && (
                        <div className={`absolute top-[3.1rem] ${isLoginLanguageRTL ? "origin-top-left" : "origin-top-right"} z-10 rounded-md bg-white py-1 shadow-md ring-1 ring-gray-50 focus:outline-none ${dropdownWidth < 100 ? 'w-min right-0' : 'w-dynamic'}`}>
                            <button id='header_user_profile_info_btn' onClick={moveToMyProfile} className={`block w-full px-4 py-2 text-xs text-gray-900 ${isLoginLanguageRTL ? "text-right" : "text-left"} hover:bg-gray-100`} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, moveToMyProfile)}>
                                {t('header.userProfile')}
                            </button>
                            <div className="border-gray-100 border-t mx-2"></div>
                            <button id='header_user_profile_logout_btn' className={`block w-full px-4 py-2 text-xs text-red-700 ${isLoginLanguageRTL ? "text-right" : "text-left"} hover:bg-gray-100`} onClick={logout} onKeyDown={(e) => onPressEnterKey(e, logout)}>{t('commons.logout')}</button>
                        </div>
                    )}
                </div>
            </div>
        </nav>
    );
}

HeaderNav.propTypes = {
    open: PropTypes.bool.isRequired,
    setOpen: PropTypes.func.isRequired,
};

export default HeaderNav;