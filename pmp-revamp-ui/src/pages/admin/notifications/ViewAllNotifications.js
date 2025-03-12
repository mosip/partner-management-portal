import { useEffect, useState } from "react";
import { getUserProfile } from "../../../services/UserProfileService.js";
import { formatDate, getNoticationTitle, getNotificationDescription, getPartnerManagerUrl, handleServiceErrors, isLangRTL, resetPageNumber, setPageNumberAndPageSize } from "../../../utils/AppUtils.js";
import LoadingIcon from "../../common/LoadingIcon.js";
import ErrorMessage from "../../common/ErrorMessage.js";
import Title from "../../common/Title.js";
import NotificationsTab from "./NotificationsTab.js";
import { useTranslation } from "react-i18next";
import searchIcon from "../../../svg/search_icon.svg";
import featuredIcon from "../../../svg/featured_icon.svg";
import noNotificationIcon from "../../../svg/frame.svg";
import Pagination from "../../common/Pagination.js";
import { HttpService } from "../../../services/HttpService.js";

function ViewNotifications({ notificationType }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [searchQuery, setSearchQuery] = useState("");
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(4);
    const [firstIndex, setFirstIndex] = useState(0);
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState(4);
    const [fetchData, setFetchData] = useState(false);
    const [totalRecords, setTotalRecords] = useState(0);
    const [resetPageNo, setResetPageNo] = useState(false);
    const [notificationsList, setNotificationsList] = useState([]);
    const [notificationDataLoaded, setNotificationDataLoaded] = useState(true);

    const fetchNotifications = async () => {
        const queryParams = new URLSearchParams();
        queryParams.append('pageSize', pageSize);
        const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, resetPageNo);
        queryParams.append('pageNo', effectivePageNo);
        setResetPageNo(false);
        queryParams.append('notificationStatus', 'active');
        queryParams.append('notificationType', notificationType);

        const url = `${getPartnerManagerUrl('/notifications', process.env.NODE_ENV)}?${queryParams.toString()}`;
        try {
            fetchData ? setNotificationDataLoaded(false) : setDataLoaded(false);
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
            fetchData ? setNotificationDataLoaded(true) : setDataLoaded(true);
            setFetchData(false);
        } catch (err) {
            console.error('Error fetching data:', err);
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            fetchData ? setNotificationDataLoaded(true) : setDataLoaded(true);
            setFetchData(false);
        }
    }

    useEffect(() => {
        fetchNotifications();
    }, [pageNo, pageSize]);

    const getPaginationValues = (recordsPerPage, pageIndex) => {
        setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setFetchData);
    };


    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const styles = {
        loadingDiv: "!py-[20%]",
        outerDiv: "!bg-opacity-35"
    }

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
                    <NotificationsTab
                        activeRootCA={notificationType === 'root' ? true : false}
                        rootCaPath={'/partnermanagement/admin/view-root-certificate-notifications'}
                        activeIntermediateCA={notificationType === 'intermediate' ? true : false}
                        intermediateCaPath={'/partnermanagement/admin/view-intermediate-certificate-notifications'}
                        activePartner={notificationType === 'weekly' ? true : false}
                        partnerCertPath={'/partnermanagement/admin/view-partner-notifications'}
                    />
                    <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                        <div className="flex max-640:flex-col items-center justify-between w-full px-2 py-3">
                            <div id='list_of_notifications_title' className={`${isLoginLanguageRTL ? 'pr-[1.3rem] max-640:pr-0' : 'pl-[1.3rem] max-640:pl-0'} font-semibold text-dark-blue text-base`}>
                                <p>{t('viewAllNotifications.listOfNotifications') + ' (' + totalRecords + ")"}</p>
                            </div>
                            <div>
                                <div className="flex h-10 w-96 max-640:w-full px-2 py-3 border border-[#C6C6C6] rounded-md text-sm text-[#B9B9B9] bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto no-scrollbar">
                                    <img src={searchIcon} id="search_icon" alt="" className="w-5 h-5 shrink-0" />
                                    <input 
                                        placeholder={t('viewAllNotifications.searchDescription')} 
                                        className="px-2 py-1 w-full min-w-0 flex-grow outline-none overflow-x-auto whitespace-nowrap no-scrollbar focus:ring-0 focus:border-transparent"
                                        value={searchQuery}
                                        onChange={(e) => setSearchQuery(e.target.value)}
                                    />
                                </div>
                            </div>
                        </div>
                        <hr className="h-0.5 bg-gray-200 border-0" />
                        {!notificationDataLoaded ? (
                            <LoadingIcon styleSet={styles} />
                        ) : (
                            <>
                            {notificationsList.length === 0 ? (
                                <div className="flex flex-col items-center py-20 px-2 border-b border-gray-200">
                                    <img src={noNotificationIcon} alt='' id='noNotificationIcon' />
                                    <p className="text-sm text-gray-500">{t('notificationPopup.noNotification')}</p>
                                    <p className="text-sm text-gray-500">{t('notificationPopup.noNotificationDescr')}</p>
                                </div>
                                ) : (
                                <>
                                    <div className="p-6">
                                        {notificationsList.map((notification) => (
                                            <div key={notification.notificationId} className="flex items-start w-full bg-white p-4 rounded-lg shadow mb-3 border-b border-[#D0D5DD]">
                                                <img src={featuredIcon} alt='' id='featuredIcon' className={`${isLoginLanguageRTL ? 'ml-3' : 'mr-3'} mt-2`} />
                                                <div className="mt-0.5 w-full">
                                                    <div className="flex justify-between">
                                                        <p className="font-semibold text-base text-[#101828]">{getNoticationTitle(notification, t)}</p>
                                                        <p className={`text-xs text-[#CBCDD0] ${isLoginLanguageRTL ? 'text-left': 'text-right'}`}>{formatDate(notification.createdDateTime, 'dateTime')}</p>
                                                    </div>
                                                    <p className="text-[#475467] text-sm">{getNotificationDescription(notification, t)}</p>
                                                    <hr className="h-0.5 my-4 bg-[#BCC5E5] border" />
                                                    <button className="text-[#475467]">{t('notificationPopup.dismiss')}</button>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                    <hr className="h-0.5 bg-gray-200 border-0" />
                                </>
                            )}
                        </>
                    )}
                    <Pagination
                        dataListLength={totalRecords}
                        selectedRecordsPerPage={selectedRecordsPerPage}
                        setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                        setFirstIndex={setFirstIndex}
                        isServerSideFilter={true}
                        getPaginationValues={getPaginationValues}
                        isViewNotificationPage={true}
                    />
                    </div>
                </>
            )}
        </div>
    );
}
export default ViewNotifications;