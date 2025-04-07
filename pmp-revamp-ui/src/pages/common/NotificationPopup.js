import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import xClose from '../../svg/x_close.svg';
import { createRequest, dismissNotificationById, formatDate, getNoticationTitle, getNotificationPanelDescription, getPartnerManagerUrl, handleEscapeKey, handleServiceErrors, isLangRTL, onPressEnterKey } from "../../utils/AppUtils";
import featuredIcon from "../../svg/featured_icon.svg";
import noNotificationIcon from "../../svg/frame.svg";
import { getUserProfile } from "../../services/UserProfileService";
import { useNavigate } from "react-router-dom";
import FocusTrap from "focus-trap-react";
import { HttpService } from "../../services/HttpService";
import LoadingIcon from "./LoadingIcon";
import ErrorMessage from "./ErrorMessage";
import { useDispatch, useSelector } from "react-redux";
import { updateHeaderNotifications } from "../../notificationsSlice";

function NotificationPopup({ closeNotification }) {
    const { t } = useTranslation();
    const navigate = useNavigate('');
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [isSmallScreen, setIsSmallScreen] = useState(window.innerHeight < 620);
    const [dataLoaded, setDataLoaded] = useState(true);
    const dispatch = useDispatch();
    const [notifications, setNotifications] = useState(useSelector((state) => state.headerNotifications.headerNotifications));

    useEffect(() => {
        updateNotificationSeenTimestamp();
    }, []);

    const updateNotificationSeenTimestamp = async () => {
        const request = createRequest({
            notificationsSeenDtimes: new Date().toISOString(),
        }, "mosip.pms.users.notifications.seen.timestamp.put", true);
        try {
            setDataLoaded(false);
            const response = await HttpService.put(getPartnerManagerUrl(`/users/${getUserProfile().userName}/notifications-seen-timestamp`, process.env.NODE_ENV), request, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    setDataLoaded(true);
                    return;
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('notificationPopup.errorWhileUpdatingNotificationSeenTime'));
            }
            setDataLoaded(true);
        } catch (err) {
            console.error('Error fetching data:', err);
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            setDataLoaded(true);
        }
    }

    useEffect(() => {
        const updateScreenSize = () => {
            setIsSmallScreen(window.innerHeight < 620);
        };

        window.addEventListener('resize', updateScreenSize);
        return () => window.removeEventListener('resize', updateScreenSize);
    }, []);

    useEffect(() => {
            document.body.style.overflow = "hidden";
    
            return () => {
                document.body.style.overflow = "auto";
            };
    }, []);

    const dismissNotification = async (id) => {
        dismissNotificationById(HttpService, id, setNotifications, fetchNotificationsList, setErrorCode, setErrorMsg, t); 
    }

    const fetchNotificationsList = async () => {
        const queryParams = new URLSearchParams();
        queryParams.append('pageSize', 4);
        queryParams.append('pageNo', 0);
        queryParams.append('notificationStatus', 'active');
        const url = `${getPartnerManagerUrl('/notifications', process.env.NODE_ENV)}?${queryParams.toString()}`;
        try {
            const response = await HttpService.get(url);
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response.data;
                    setNotifications(resData);
                    dispatch(updateHeaderNotifications(resData));
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('notificationPopup.errorInNotifcations'));
            }
        } catch (err) {
            console.error('Error fetching data:', err);
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
        }
    }

    const viewAllNotifications = () => {
        closeNotification();
        if (getUserProfile().roles.includes('PARTNER_ADMIN')) {
            navigate('/partnermanagement/admin/notifications/view-root-certificate-expiry');
        } else {
            navigate('/partnermanagement/notifications/view-partner-certificate-expiry');
        }
    };

    const styles = {
        loadingDiv: "!py-[40%]"
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const errorcustomStyle = {
        outerDiv: "!flex !justify-center",
        innerDiv: "!flex !justify-between !items-center !rounded-none !bg-moderate-red !md:w-[25rem] !w-full !min-h-[3.2rem] !h-fit !px-4 !py-[10px]",
        cancelIcon: "!top-[4.5rem]"
    }

    useEffect(() => {
        const removeListener = handleEscapeKey(() => closeNotification());
        return removeListener;
    }, []);

    return (
        <div className={`absolute top-[3.75rem] ${isLoginLanguageRTL ? 'max-850:left-4 left-[15rem]' : 'max-850:right-4 right-[15rem]'} bg-white w-[28rem] max-520:w-[286px] rounded-lg shadow-lg border border-gray-200 z-50`}>
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div>
                    {!dataLoaded && (
                        <LoadingIcon styleSet={styles} />
                    )}
                    {dataLoaded && (
                        <div>
                            <div className="flex justify-between items-center px-4 py-3 border-b border-gray-200 cursor-default">
                                <h2 className="text-lg font-bold text-gray-800">{t('notificationPopup.notification')}</h2>
                                <img src={xClose} alt='' id='xIcon' onClick={closeNotification} className="cursor-pointer" tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, closeNotification)}/>
                            </div>
                            {errorMsg && (
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={errorcustomStyle}/>
                            )}
                            {notifications.length > 0 ? (
                                <>
                                    <p className={`text-sm text-[#6F6E6E] font-medium ${isLoginLanguageRTL ? 'mr-4' : 'ml-4'} my-2`}>{t('notificationPopup.latest')}</p>
                                    <div className={`${isSmallScreen ? 'max-h-64' : 'max-h-96'} overflow-y-auto`}>
                                        {notifications.map(notification => (
                                            <div key={notification.notificationId} className="flex justify-between items-start mx-2 px-3 py-2 border-b border-gray-200 mb-2">
                                                <img src={featuredIcon} alt='' id='featuredIcon' className={`${isLoginLanguageRTL ? 'ml-3' : 'mr-3'} mt-1`} />
                                                <div>
                                                    <div className="flex justify-between space-x-2">
                                                        <p className={`text-sm font-semibold text-gray-900 ${isLoginLanguageRTL ? 'text-right': 'text-left'}`}>{getNoticationTitle(notification, t)}</p>
                                                        <p className={`text-xs text-gray-500 w-36 ${isLoginLanguageRTL ? 'text-left': 'text-right'}`}>{formatDate(notification.createdDateTime, 'dateTimeWithoutSeconds')}</p>
                                                    </div>
                                                    <div className="text-sm text-[#344054] mt-1 whitespace-pre-line">{getNotificationPanelDescription(notification, isLoginLanguageRTL, t)}</div>
                                                    <button 
                                                        className="text-[#475467] font-semibold text-sm mt-2"
                                                        onClick={() => dismissNotification(notification.notificationId)}
                                                    >
                                                        {t('notificationPopup.dismiss')}
                                                    </button>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                    <div role="button" className="p-3 text-center text-tory-blue text-sm font-medium cursor-pointer" onClick={viewAllNotifications} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, viewAllNotifications)}>
                                        {t('notificationPopup.viewAllNotification')}
                                    </div>
                                </>
                                ) : (
                                    <div className="cursor-default">
                                        <div className="flex flex-col items-center py-16 px-2 border-b border-gray-200">
                                            <img src={noNotificationIcon} alt='' id='noNotificationIcon' />
                                            <p className="text-sm text-gray-500">{t('notificationPopup.noNotification')}</p>
                                            <p className="text-sm text-gray-500">{t('notificationPopup.noNotificationDescr')}</p>
                                        </div>
                                        <button className="p-3 text-center text-gray-300 text-sm font-medium w-full cursor-default">
                                            {t('notificationPopup.viewAllNotification')}
                                        </button>
                                    </div>
                                )
                            }
                        </div>
                    )}
                </div>
            </FocusTrap>
        </div>
    );
}

export default NotificationPopup;