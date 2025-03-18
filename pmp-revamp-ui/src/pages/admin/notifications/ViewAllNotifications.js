import { useEffect, useState } from "react";
import { getUserProfile } from "../../../services/UserProfileService.js";
import {
    dismissNotificationById, formatDate, getNoticationTitle, getNotificationDescription, getPartnerManagerUrl, handleServiceErrors,
    isLangRTL, resetPageNumber, setPageNumberAndPageSize, onResetFilter, onClickApplyFilter
} from "../../../utils/AppUtils.js";
import LoadingIcon from "../../common/LoadingIcon.js";
import ErrorMessage from "../../common/ErrorMessage.js";
import Title from "../../common/Title.js";
import NotificationsTab from "./NotificationsTab.js";
import { useTranslation } from "react-i18next";
import featuredIcon from "../../../svg/featured_icon.svg";
import noNotificationIcon from "../../../svg/frame.svg";
import Pagination from "../../common/Pagination.js";
import { HttpService } from "../../../services/HttpService.js";
import FilterButtons from "../../common/FilterButtons"
import ViewAllNotificationsFilter from "./ViewAllNotificationsFilter.js";

function ViewNotifications({ notificationType }) {
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
    });


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
        dismissNotificationById(HttpService, id, setNotificationsList, true, fetchNotifications, setErrorCode, setErrorMsg, t);
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
                                    dataListLength={totalRecords}
                                    filter={filter}
                                    onResetFilter={onResetFilter}
                                    setFilter={setFilter}
                                />
                                <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                {filter && (
                                    <ViewAllNotificationsFilter
                                        onApplyFilter={onApplyFilter}
                                        setErrorCode={setErrorCode}
                                        setErrorMsg={setErrorMsg}
                                    />
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
                                                <div className="p-6">
                                                    {notificationsList.map((notification) => (
                                                        <div key={notification.notificationId} className="flex items-start w-full bg-white p-4 rounded-lg shadow mb-3 border-b border-[#D0D5DD]">
                                                            <img src={featuredIcon} alt='' id='featuredIcon' className={`${isLoginLanguageRTL ? 'ml-3' : 'mr-3'} mt-2`} />
                                                            <div className="mt-0.5 w-full">
                                                                <div className="flex justify-between">
                                                                    <p className="font-semibold text-base text-[#101828]">{getNoticationTitle(notification, t)}</p>
                                                                    <p className={`text-xs text-[#CBCDD0] ${isLoginLanguageRTL ? 'text-left' : 'text-right'}`}>{formatDate(notification.createdDateTime, 'dateTime')}</p>
                                                                </div>
                                                                <p className="text-[#475467] text-sm">{getNotificationDescription(notification, t)}</p>
                                                                <hr className="h-0.5 my-4 bg-[#BCC5E5] border" />
                                                                <button onClick={() => dismissNotification(notification.notificationId)} className="text-[#475467] font-semibold text-sm">{t('notificationPopup.dismiss')}</button>
                                                            </div>
                                                        </div>
                                                    ))}
                                                </div>
                                                <hr className="h-0.5 bg-gray-200 border-0" />
                                                <Pagination
                                                    dataListLength={totalRecords}
                                                    selectedRecordsPerPage={selectedRecordsPerPage}
                                                    setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                                                    setFirstIndex={setFirstIndex}
                                                    isServerSideFilter={true}
                                                    getPaginationValues={getPaginationValues}
                                                    isViewNotificationPage={true}
                                                />
                                            </>
                                        )}
                                    </>
                                )}
                            </div>
                        </>
                    )}
                </>
            )}
        </div>
    );
}
export default ViewNotifications;