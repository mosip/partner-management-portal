import { useEffect, useState } from "react";
import { getUserProfile } from "../../../services/UserProfileService.js";
import {
    formatDate, getNoticationTitle, getNotificationDescription, getPartnerManagerUrl, handleServiceErrors,
    isLangRTL, resetPageNumber, setPageNumberAndPageSize, onResetFilter, onClickApplyFilter,
    createRequest,
    fetchNotificationsList
} from "../../../utils/AppUtils.js";
import LoadingIcon from "../../common/LoadingIcon.js";
import ErrorMessage from "../../common/ErrorMessage.js";
import Title from "../../common/Title.js";
import AdminNotificationsTab from "./AdminNotificationsTab.js";
import { Trans, useTranslation } from "react-i18next";
import featuredIcon from "../../../svg/featured_icon.svg";
import noNotificationIcon from "../../../svg/frame.svg";
import Pagination from "../../common/Pagination.js";
import { HttpService } from "../../../services/HttpService.js";
import FilterButtons from "../../common/FilterButtons"
import CertificateNotificationsFilter from "./CertificateNotificationsFilter.js";
import PartnerNotificationsTab from "../../partner/notifications/PartnerNotificationsTab.js";
import PartnerCertificateNotificationsFilter from "../../partner/notifications/PartnerCertificateNotificationsFilter.js";
import { useDispatch } from "react-redux";
import WeeklyNotificationsFilter from "./WeeklyNotificationsFilter.js";

function ViewAllNotifications({ notificationType }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(4);
    const [firstIndex, setFirstIndex] = useState(0);
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState(4);
    const [fetchData, setFetchData] = useState(false);
    const [totalRecords, setTotalRecords] = useState(0);
    const [resetPageNo, setResetPageNo] = useState(false);
    const [notificationsList, setNotificationsList] = useState([]);
    const [notificationDataLoaded, setNotificationDataLoaded] = useState(true);
    const [filter, setFilter] = useState(false);
    const [isFilterApplied, setIsFilterApplied] = useState(false);
    const [isApplyFilterClicked, setIsApplyFilterClicked] = useState(false);
    const [filterAttributes, setFilterAttributes] = useState({
        certificateId: null,
        partnerDomain: null,
        issuedTo: null,
        issuedBy: null,
        expiryDate: null,
        createdFromDate: null,
        createdToDate: null
    });
    const dispatch = useDispatch();
    const [showExpiringItems, setShowExpiringItems] = useState(false);
    const [activeTab, setActiveTab] = useState('');
    const [weeklyNotificationList, setWeeklyNotificationList] = useState([]);
    const [notificationCreatedDateTime, setNotificationCreatedDateTime] = useState("");

    const fetchNotifications = async (noDateLoaded) => {
        const queryParams = new URLSearchParams();
        queryParams.append('pageSize', pageSize);
        const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, resetPageNo);
        queryParams.append('pageNo', effectivePageNo);
        setResetPageNo(false);
        queryParams.append('notificationStatus', 'active');
        queryParams.append('notificationType', notificationType);

        if (filterAttributes.certificateId) queryParams.append('certificateId', filterAttributes.certificateId);
        if (filterAttributes.partnerDomain) queryParams.append('partnerDomain', filterAttributes.partnerDomain);
        if (filterAttributes.issuedTo) queryParams.append('issuedTo', filterAttributes.issuedTo);
        if (filterAttributes.issuedBy) queryParams.append('issuedBy', filterAttributes.issuedBy);
        if (filterAttributes.expiryDate) queryParams.append('expiryDate', filterAttributes.expiryDate);
        if (filterAttributes.createdFromDate) queryParams.append('createdFromDate', filterAttributes.createdFromDate);
        if (filterAttributes.createdToDate) queryParams.append('createdToDate', filterAttributes.createdToDate);

        const url = `${getPartnerManagerUrl('/notifications', process.env.NODE_ENV)}?${queryParams.toString()}`;
        try {
            if (!noDateLoaded) {
                fetchData ? setNotificationDataLoaded(false) : setDataLoaded(false);
            }
            const response = await HttpService.get(url);
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response.data;
                    setTotalRecords(responseData.response.totalResults);
                    setNotificationsList(resData);
                } else {
                    handleServiceErrors
                        (responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('notificationPopup.errorInNotifcations'));
            }
            if (!noDateLoaded) {
                fetchData ? setNotificationDataLoaded(true) : setDataLoaded(true);
                setFetchData(false);
            }
        } catch (err) {
            console.error('Error fetching data:', err);
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            if (!noDateLoaded) {
                fetchData ? setNotificationDataLoaded(true) : setDataLoaded(true);
                setFetchData(false);
            }
        }
    }

    useEffect(() => {
        fetchNotifications();
    }, [pageNo, pageSize]);

    useEffect(() => {
        if (isApplyFilterClicked) {
            fetchNotifications();
            setIsApplyFilterClicked(false);
        }
    }, [isApplyFilterClicked]);

    const getPaginationValues = (recordsPerPage, pageIndex) => {
        setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setFetchData);
    };

    const dismissNotification = async (id) => {
        const request = createRequest({
            notificationStatus: "DISMISSED",
        }, "mosip.pms.dismiss.notification.patch", true);
    
        try {
            const response = await HttpService.patch(getPartnerManagerUrl(`/notifications/${id}`, process.env.NODE_ENV), request, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    setNotificationsList((prevNotifications) =>
                        prevNotifications.filter((notif) => notif.notificationId !== id)
                    );
                    await fetchNotifications(true);
                    const notifications = await fetchNotificationsList(dispatch);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('notificationPopup.errorInDismiss'));
            }
        } catch (err) {
            console.error('Error fetching data:', err);
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
        }
    }

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const styles = {
        loadingDiv: "!py-[20%]",
        outerDiv: "!bg-opacity-35"
    }

    const onApplyFilter = (filters) => {
        onClickApplyFilter(filters, setIsFilterApplied, setResetPageNo, setFetchData, setFilterAttributes, setIsApplyFilterClicked);
    };

    const viewExpiringItems = (notification) => {
        setShowExpiringItems(true);
        setActiveTab('partner');
        setNotificationCreatedDateTime(notification.createdDateTime);
        const certificateList = Array.isArray(notification.notificationDetails.certificateDetails) ? notification.notificationDetails.certificateDetails : [];
        const partnerCertList = certificateList.filter(cert => cert.certificateType === "partner");
        setWeeklyNotificationList(partnerCertList);
    };

    const backToWeeklySummary = () => {
        setShowExpiringItems(false);
        setSelectedRecordsPerPage(4);
    };

    const changeToPartnerCert = () => {
        setActiveTab('partner')
    }

    const getWeeklyNoticationTitle = (notification, type) => {
        if (type === 'partner') {
            return t('viewAllNotifications.partnerCertExpiryTitle', {partnerId: notification.partnerId});
        }
    }

    const getWeeklyNoticationDescription = (notification, type) => {
        if (type === 'partner') {
            return (
                <Trans 
                    i18nKey="viewAllNotifications.partnerCertExpiryDescription"
                    values={{
                        certificateId: notification.certificateId,
                        issuedTo: notification.issuedTo,
                        issuedBy: notification.issuedBy,
                        partnerDomain: notification.partnerDomain,
                        expiryDateTime: formatDate(notification.expiryDateTime, 'dateInWords')
                    }}
                    components={{ span: <span className="font-semibold" /> }}
                />
            );
        }
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <div className="flex justify-between mb-5 max-470:flex-col">
                            <Title title='notificationPopup.notification' backLink='/partnermanagement' />
                        </div>
                    </div>
                    {(notificationType === "root" || notificationType === "intermediate" || notificationType === "weekly") ? (
                        <AdminNotificationsTab
                            activeRootCA={notificationType === 'root' ? true : false}
                            rootCaPath={'/partnermanagement/admin/notifications/view-root-certificate-expiry'}
                            activeIntermediateCA={notificationType === 'intermediate' ? true : false}
                            intermediateCaPath={'/partnermanagement/admin/notifications/view-intermediate-certificate-expiry'}
                            activePartner={notificationType === 'weekly' ? true : false}
                            partnerCertPath={'/partnermanagement/admin/notifications/view-partner-created-items-expiry'}
                        />
                    ) : (
                        <PartnerNotificationsTab
                            activeTab={notificationType}
                        />
                    )}
                    
                    {!isFilterApplied && notificationsList.length === 0 ? (
                        <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">

                            <div className="flex flex-col items-center py-20 px-2 border-b border-gray-200">
                                <img src={noNotificationIcon} alt='' id='noNotificationIcon' />
                                <p className="text-sm text-gray-500">{t('notificationPopup.noNotification')}</p>
                                <p className="text-sm text-gray-500">{t('notificationPopup.noNotificationDescr')}</p>
                            </div>
                        </div>
                    ) : (
                        <>
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <FilterButtons
                                    listTitle="viewAllNotifications.listOfNotifications"
                                    showTitleWithoutCount={showExpiringItems ? true : false}
                                    dataListLength={totalRecords}
                                    filter={filter}
                                    onResetFilter={onResetFilter}
                                    setFilter={setFilter}
                                    addBackArrow={showExpiringItems ? true : false}
                                    goBack={showExpiringItems && backToWeeklySummary}
                                    removeFiler={showExpiringItems ? true : false}
                                />
                                <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                {filter && (
                                    <>
                                        {(notificationType === "root" || notificationType === "intermediate") && (
                                            <CertificateNotificationsFilter onApplyFilter={onApplyFilter} />
                                        )}
                                        {(notificationType === "weekly") && (
                                            <WeeklyNotificationsFilter onApplyFilter={onApplyFilter} />
                                        )}
                                        {(notificationType === "partner") && (
                                            <PartnerCertificateNotificationsFilter onApplyFilter={onApplyFilter} />
                                        )}
                                        
                                    </>
                                )}
                                {!notificationDataLoaded ? (
                                    <LoadingIcon styleSet={styles} />
                                ) : (
                                    <>
                                        {isFilterApplied && notificationsList.length === 0 ? (
                                            <div className="flex flex-col items-center py-20 px-2 border-b border-gray-200">
                                                <img src={noNotificationIcon} alt='' id='noNotificationIcon' />
                                                <p className="text-sm text-gray-500">{t('notificationPopup.filterNoNotificationTitle')}</p>
                                            </div>
                                        ) : (
                                            <>
                                                {!showExpiringItems ? (
                                                    <div className="p-6">
                                                        {notificationsList.map((notification) => (
                                                            <div key={notification.notificationId} className="flex items-start w-full bg-white p-4 rounded-lg shadow mb-3 border-b border-[#D0D5DD]">
                                                                <img src={featuredIcon} alt='' id='featuredIcon' className={`${isLoginLanguageRTL ? 'ml-3' : 'mr-3'} mt-2`} />
                                                                <div className="mt-0.5 w-full">
                                                                    <div className="flex justify-between flex-wrap">
                                                                        <p className="font-semibold text-base text-[#101828]">{getNoticationTitle(notification, t)}</p>
                                                                        <p className={`text-xs text-gray-500 ${isLoginLanguageRTL ? 'text-left' : 'text-right'}`}>{formatDate(notification.createdDateTime, 'dateTime')}</p>
                                                                    </div>
                                                                    <div className="text-[#475467] text-sm break-all">{getNotificationDescription(notification, isLoginLanguageRTL, t)}</div>
                                                                    <hr className="h-0.5 my-4 bg-[#BCC5E5] border" />
                                                                    <div className={`flex space-x-4 ${isLoginLanguageRTL && 'space-x-reverse'}`}>
                                                                        <button onClick={() => dismissNotification(notification.notificationId)} className="text-tory-blue font-semibold text-sm px-4 py-[6px] rounded-md bg-[#F7F9FF]">{t('notificationPopup.dismiss')}</button>
                                                                        {(notificationType === "weekly") && (
                                                                            <button onClick={() => viewExpiringItems(notification)} className="text-tory-blue font-semibold text-sm px-4 py-[6px] rounded-md bg-[#F7F9FF]">{t('viewAllNotifications.viewExpiringItems')}</button>
                                                                        )}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        ))}
                                                    </div>
                                                ) : (
                                                    <div>
                                                        <div className='flex text-xs bg-[#FCFCFC] font-bold space-x-16 items-start px-8 border-b-2'>
                                                            <div id='partner_cert_tab' className={`flex-col justify-center text-center`}>
                                                                <button onClick={changeToPartnerCert} className={`${activeTab === "partner" ? "text-[#1447b2]" : "text-[#031640]"} py-3 cursor-pointer text-base`}>
                                                                    <h6> {t('partnerNotificationsTab.partnerCertificate')} </h6>
                                                                </button>

                                                                <div className={`h-1 w-full ${activeTab === "partner" ? "bg-tory-blue" : "bg-transparent"}  rounded-t-md`}></div>
                                                            </div>
                                                        </div>
                                                        {/* <hr className="h-0.5 bg-gray-200 border-0" /> */}
                                                        <div className="p-4">
                                                            {weeklyNotificationList.map((notification, index) => (
                                                                <div key={index} className="flex items-start w-full bg-white p-4 rounded-lg shadow mb-3 border-b border-[#D0D5DD]">
                                                                    <img src={featuredIcon} alt='' id='featuredIcon' className={`${isLoginLanguageRTL ? 'ml-3' : 'mr-3'} mt-2`} />
                                                                    <div className="mt-0.5 w-full">
                                                                        <div className="flex justify-between flex-wrap">
                                                                            <p className="font-semibold text-base text-[#101828]">{getWeeklyNoticationTitle(notification, activeTab)}</p>
                                                                            <p className={`text-xs text-gray-500 ${isLoginLanguageRTL ? 'text-left' : 'text-right'}`}>{formatDate(notificationCreatedDateTime, 'dateTime')}</p>
                                                                        </div>
                                                                        <div className="text-[#475467] text-sm break-all">{getWeeklyNoticationDescription(notification, activeTab)}</div>
                                                                    </div>
                                                                </div>
                                                            ))}
                                                        </div>
                                                    </div>
                                                )}
                                                <hr className="h-0.5 bg-gray-200 border-0" />
                                            </>
                                        )}
                                    </>
                                )}
                                {!showExpiringItems && (
                                    <Pagination
                                        dataListLength={totalRecords}
                                        selectedRecordsPerPage={selectedRecordsPerPage}
                                        setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                                        setFirstIndex={setFirstIndex}
                                        isServerSideFilter={true}
                                        getPaginationValues={getPaginationValues}
                                        isViewNotificationPage={true}
                                    />
                                )}
                            </div>
                        </>
                    )}
                </>
            )}
        </div>
    );
}
export default ViewAllNotifications;