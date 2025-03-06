import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import xClose from '../../svg/x_close.svg';
import { formatDate, getNoticationTitle, getNotificationDescription, isLangRTL } from "../../utils/AppUtils";
import featuredIcon from "../../svg/featured_icon.svg";
import noNotificationIcon from "../../svg/frame.svg";
import { getUserProfile } from "../../services/UserProfileService";
import { useNavigate } from "react-router-dom";

function NotificationPopup({ closeNotification }) {
    const { t } = useTranslation();
    const navigate = useNavigate('');
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [isSmallScreen, setIsSmallScreen] = useState(window.innerHeight < 620);
    const [notifications, setNotifications] = useState([
        {
            "notificationId": "1",
            "notificationType": "ROOT_CERT_EXPIRY",
            "notificationStatus": "ACTIVE",
            "notificationPartnerId": "",
            "createdDateTime": "2025-03-04T05:33:23.685+00:00",
            "notificationDetails": {
                "certificateDetails": [
                    {
                        "certificateId": "123456789",
                        "issuedBy": "",
                        "issuedTo": "",
                        "partnerId": "",
                        "partnerDomain": "AUTH",
                        "expiryDateTime": "2025-03-04T05:33:23.685+00:00",
                        "expiryPeriod": "",
                        "certificateType": "root"
                    }
                ],
                "sbiDetails": null,
                "apiKeyDetails": null
            }
        },
        {
            "notificationId": "2",
            "notificationType": "INTERMEDIATE_CERT_EXPIRY",
            "notificationStatus": "ACTIVE",
            "notificationPartnerId": "",
            "createdDateTime": "2025-03-04T05:33:23.685+00:00",
            "notificationDetails": {
                "certificateDetails": [
                    {
                        "certificateId": "123456789",
                        "issuedBy": "",
                        "issuedTo": "",
                        "partnerId": "",
                        "partnerDomain": "AUTH",
                        "expiryDateTime": "2025-03-04T05:33:23.685+00:00",
                        "expiryPeriod": "",
                        "certificateType": "intermediate"
                    }
                ],
                "sbiDetails": null,
                "apiKeyDetails": null
            }
        },
        {
            "notificationId": "3",
            "notificationType": "WEEKLY_SUMMARY",
            "notificationStatus": "ACTIVE",
            "notificationPartnerId": "",
            "createdDateTime": "2025-03-04T05:33:23.685+00:00",
            "notificationDetails": {
                "certificateDetails": [
                    {
                        "certificateId": "123456789",
                        "issuedBy": "",
                        "issuedTo": "",
                        "partnerId": "",
                        "partnerDomain": "AUTH",
                        "expiryDateTime": "2025-03-04T05:33:23.685+00:00",
                        "expiryPeriod": "",
                        "certificateType": "partner"
                    },
                    {
                        "certificateId": "123456789",
                        "issuedBy": "",
                        "issuedTo": "",
                        "partnerId": "",
                        "partnerDomain": "FTM",
                        "expiryDateTime": "2025-03-04T05:33:23.685+00:00",
                        "expiryPeriod": "",
                        "certificateType": "ftm"
                    }
                ],
                "sbiDetails": [
                    {
                        "sbiId": "",
                        "partnerId": "",
                        "expiryDateTime": "2025-03-04T05:33:23.685+00:00",
                        "expiryPeriod": ""
                    },
                    {
                        "sbiId": "",
                        "partnerId": "",
                        "expiryDateTime": "2025-03-04T05:33:23.685+00:00",
                        "expiryPeriod": ""
                    }
                ],
                "apiKeyDetails": [
                    {
                        "apiKeyId": "",
                        "partnerId": "",
                        "expiryDateTime": "2025-03-04T05:33:23.685+00:00",
                        "expiryPeriod": ""
                    },
                    {
                        "apiKeyId": "",
                        "partnerId": "",
                        "expiryDateTime": "2025-03-04T05:33:23.685+00:00",
                        "expiryPeriod": ""
                    }
                ]
            }
        }
    ]);

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

    const dismissNotification = (id) => {
        setNotifications(notifications.filter(notification => notification.notificationId !== id));
    };

    const viewAllNotifications = () => {
        closeNotification();
        navigate('/partnermanagement/view-root-certificate-notifications');
    }

    return (
        <div className={`absolute top-[3.75rem] ${isLoginLanguageRTL ? 'max-850:left-4 left-[15rem]' : 'max-850:right-4 right-[15rem]'} bg-white w-[25rem] max-520:w-full rounded-lg shadow-lg border border-gray-200 z-50`}>
            <div className="flex justify-between items-center p-4 border-b border-gray-200">
                <h2 className="text-lg font-bold text-gray-800">{t('notificationPopup.notification')}</h2>
                <img src={xClose} alt='' id='xIcon' onClick={closeNotification} />
            </div>
            {notifications.length > 0 ? (
                <>
                    <p className={`text-sm text-[#6F6E6E] font-medium ${isLoginLanguageRTL ? 'mr-4' : 'ml-4'} my-2`}>latest</p>
                    <div className={`${isSmallScreen ? 'max-h-64' : 'max-h-96'} overflow-y-auto`}>
                        {notifications.map(notification => (
                            <div key={notification.notificationId} className="flex justify-between items-start p-2 border-b border-gray-200">
                                <img src={featuredIcon} alt='' id='featuredIcon' className={`${isLoginLanguageRTL ? 'ml-3' : 'mr-3'} mt-1`} />
                                <div>
                                    <div className="flex justify-between space-x-2">
                                        <p className={`text-sm font-semibold text-gray-900 ${isLoginLanguageRTL ? 'text-right': 'text-left'}`}>{getNoticationTitle(notification, t)}</p>
                                        <p className={`text-xs text-[#CBCDD0] ${isLoginLanguageRTL ? 'text-left': 'text-right'}`}>{formatDate(notification.createdDateTime, 'dateTime')}</p>
                                    </div>
                                    <p className="text-sm text-[#344054] mt-1 whitespace-pre-line">{getNotificationDescription(notification, t)}</p>
                                    <button 
                                        className="text-[#475467] text-sm mt-2"
                                        onClick={() => dismissNotification(notification.notificationId)}
                                    >
                                        {t('notificationPopup.dismiss')}
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>
                    <div role="button" className="p-3 text-center text-tory-blue text-sm font-medium cursor-pointer" onClick={viewAllNotifications}>
                        {t('notificationPopup.viewAllNotification')}
                    </div>
                </>
                ) : (
                    <>
                        <div className="flex flex-col items-center py-16 px-2 border-b border-gray-200">
                            <img src={noNotificationIcon} alt='' id='noNotificationIcon' />
                            <p className="text-sm text-gray-500">{t('notificationPopup.noNotification')}</p>
                            <p className="text-sm text-gray-500">{t('notificationPopup.noNotificationDescr')}</p>
                        </div>
                        <div className="p-3 text-center text-gray-300 text-sm font-medium">
                            {t('notificationPopup.viewAllNotification')}
                        </div>
                    </>
                )
            }
        </div>

    );
}

export default NotificationPopup;