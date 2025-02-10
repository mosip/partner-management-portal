import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import { HttpService } from '../../../services/HttpService.js';
import ErrorMessage from '../../common/ErrorMessage';
import Title from '../../common/Title';
import LoadingIcon from '../../common/LoadingIcon';
import FilterButtons from '../../common/FilterButtons';
import SortingIcon from '../../common/SortingIcon';
import Pagination from '../../common/Pagination';
import viewIcon from "../../../svg/view_icon.svg";
import disableDeactivateIcon from "../../../svg/disable_deactivate_icon.svg";
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import approveRejectIcon from "../../../svg/approve_reject_icon.svg";
import disabledApproveRejectIcon from "../../../svg/approve_reject_disabled_icon.svg";
import EmptyList from '../../common/EmptyList';
import AdminFtmListFilter from './AdminFtmListFilter.js';
import { handleMouseClickForDropdown, isLangRTL, onClickApplyFilter, setPageNumberAndPageSize, onResetFilter, bgOfStatus, getStatusCode, onPressEnterKey, formatDate, resetPageNumber, getPartnerManagerUrl, handleServiceErrors, createRequest, getApproveRejectStatus, updateActiveState, escapeKeyHandler, setSubmenuRef } from '../../../utils/AppUtils';
import ApproveRejectPopup from '../../common/ApproveRejectPopup.js';
import DeactivatePopup from '../../common/DeactivatePopup.js';

function AdminFtmList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [ftmList, setFtmList] = useState([]);
    const [expandFilter, setExpandFilter] = useState(false);
    const [order, setOrder] = useState("DESC");
    const [activeAscIcon, setActiveAscIcon] = useState("");
    const [activeDescIcon, setActiveDescIcon] = useState("createdDateTime");
    const [actionId, setActionId] = useState(-1);
    const [firstIndex, setFirstIndex] = useState(0);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
    const [sortFieldName, setSortFieldName] = useState("createdDateTime");
    const [sortType, setSortType] = useState("desc");
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
    const [fetchData, setFetchData] = useState(false);
    const [tableDataLoaded, setTableDataLoaded] = useState(true);
    const [totalRecords, setTotalRecords] = useState(0);
    const [resetPageNo, setResetPageNo] = useState(false);
    const [applyFilter, setApplyFilter] = useState(false);
    const [isApplyFilterClicked, setIsApplyFilterClicked] = useState(false);
    const [showActiveIndexFtmApproveRejectPopup, setShowActiveIndexFtmApproveRejectPopup] = useState(null);
    const [showActiveIndexDeactivatePopup, setShowActiveIndexDeactivatePopup] = useState(null);
    const [deactivateRequest, setDeactivateRequest] = useState({});
    const [selectedFtm, setSelectedFtm] = useState({});
    const [filterAttributes, setFilterAttributes] = useState({
        partnerId: null,
        orgName: null,
        ftmId: null,
        make: null,
        model: null,
        status: null,
    });
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
    }, [submenuRef]);

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'ftmList.partnerId' },
        { id: "orgName", headerNameKey: 'ftmList.orgName' },
        { id: "ftmId", headerNameKey: 'ftmList.ftmId' },
        { id: "make", headerNameKey: "ftmList.make" },
        { id: "model", headerNameKey: "ftmList.model" },
        { id: "createdDateTime", headerNameKey: "ftmList.creationDate" },
        { id: "status", headerNameKey: "ftmList.status" },
        { id: "action", headerNameKey: 'ftmList.action' }
    ];

    const fetchFtmListData = async () => {
        const queryParams = new URLSearchParams();
        queryParams.append('sortFieldName', sortFieldName);
        queryParams.append('sortType', sortType);
        queryParams.append('pageSize', pageSize);

        //reset page number to 0 if filter applied or page number is out of bounds
        const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, resetPageNo);
        queryParams.append('pageNo', effectivePageNo);
        setResetPageNo(false);

        if (filterAttributes.partnerId) queryParams.append('partnerId', filterAttributes.partnerId);
        if (filterAttributes.orgName) queryParams.append('orgName', filterAttributes.orgName);
        if (filterAttributes.ftmId) queryParams.append('ftmId', filterAttributes.ftmId);
        if (filterAttributes.make) queryParams.append('make', filterAttributes.make);
        if (filterAttributes.model) queryParams.append('model', filterAttributes.model);
        if (filterAttributes.status) queryParams.append('status', filterAttributes.status);

        const url = `${getPartnerManagerUrl('/ftpchipdetail/v2', process.env.NODE_ENV)}?${queryParams.toString()}`;
        try {
            fetchData ? setTableDataLoaded(false) : setDataLoaded(false);
            const response = await HttpService.get(url);
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response.data;
                    setTotalRecords(responseData.response.totalResults);
                    setFtmList(resData);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('ftmList.errorInFtmList'));
            }
            fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
            setFetchData(false);
        } catch (err) {
            console.error('Error fetching data:', err);
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            setFetchData(false);
            fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
        }
    }

    useEffect(() => {
        fetchFtmListData();
    }, [sortFieldName, sortType, pageNo, pageSize]);

    useEffect(() => {

        if (isApplyFilterClicked) {
            fetchFtmListData();
            setIsApplyFilterClicked(false);
        }
    }, [isApplyFilterClicked]);

    const onApplyFilter = (filters) => {
        onClickApplyFilter(filters, setApplyFilter, setResetPageNo, setFetchData, setFilterAttributes, setIsApplyFilterClicked);
    };

    const getPaginationValues = (recordsPerPage, pageIndex) => {
        setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setFetchData);
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const viewFtmChipDetails = (ftm) => {
        localStorage.setItem('selectedFtmAttributes', JSON.stringify(ftm));
        navigate('/partnermanagement/admin/ftm-chip-provider-services/view-ftm-chip-details');
    };

    const approveRejectFtmDetails = (ftm, index) => {
        if (ftm.status === 'pending_approval') {
            setShowActiveIndexFtmApproveRejectPopup(index);
            setActionId(-1);
            setSelectedFtm(ftm);
        }
    };

    const onClickApproveReject = (responseData, status, selectedFtm) => {
        if (responseData) {
            setSelectedFtm({});
            setShowActiveIndexFtmApproveRejectPopup(null);
            // Update the specific row in the state with the new status
            setFtmList((prevList) =>
                prevList.map(ftm =>
                    ftm.ftmId === selectedFtm.ftmId ? { ...ftm, status: getApproveRejectStatus(status), isActive: updateActiveState(status) } : ftm
                )
            );
        }
    };

    const closeApproveRejectPopup = () => {
        setSelectedFtm({});
        setShowActiveIndexFtmApproveRejectPopup(null);
    };

    const deactivateFtmDetails = (ftm, index) => {
        if (ftm.status === "approved") {
            const request = createRequest({
                status: "De-Activate",
            }, "mosip.pms.deactivate.ftm.patch", true);
            setActionId(-1);
            setDeactivateRequest(request);
            setSelectedFtm(ftm);
            setShowActiveIndexDeactivatePopup(index);
        }
    };

    const onClickConfirmDeactivate = (deactivationResponse, selectedFtm) => {
        if (deactivationResponse && !deactivationResponse.isActive) {
            setSelectedFtm({});
            setShowActiveIndexDeactivatePopup(null);
            // Update the specific row in the state with the new status
            setFtmList((prevList) =>
                prevList.map(ftm =>
                    ftm.ftmId === selectedFtm.ftmId ? { ...ftm, status: "deactivated", isActive: false } : ftm
                )
            );
        }
    };

    const closeDeactivatePopup = () => {
        setSelectedFtm({});
        setShowActiveIndexDeactivatePopup(null);
    };

    const sortAscOrder = (header) => {
        if (order !== 'ASC' || activeAscIcon !== header) {
            setFetchData(true);
            setSortFieldName(header);
            setSortType("asc");
            setOrder("ASC");
            setActiveDescIcon("");
            setActiveAscIcon(header);
        }
    };
    const sortDescOrder = (header) => {
        if (order !== 'DESC' || activeDescIcon !== header) {
            setFetchData(true);
            setSortFieldName(header);
            setSortType('desc');
            setOrder("DESC");
            setActiveDescIcon(header);
            setActiveAscIcon("");
        }
    };

    const styles = {
        loadingDiv: "!py-[20%]"
    };

    useEffect(() => {
        if (showActiveIndexFtmApproveRejectPopup) {
            escapeKeyHandler(closeApproveRejectPopup);
        } else if (showActiveIndexDeactivatePopup) {
            escapeKeyHandler(closeDeactivatePopup);
        }
    }, [showActiveIndexFtmApproveRejectPopup, showActiveIndexDeactivatePopup]);

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
            {!dataLoaded && (
                <LoadingIcon />
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <div className="flex justify-between mb-5 max-470:flex-col">
                            <Title title='dashboard.ftmChip' backLink='/partnermanagement' />
                        </div>
                        {!applyFilter && ftmList.length === 0 ? (
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <EmptyList tableHeaders={tableHeaders} />
                            </div>
                        ) : (
                            <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                                <FilterButtons
                                    titleId='list_of_ftm_chip'
                                    listTitle='ftmList.listOfFtm'
                                    dataListLength={totalRecords}
                                    filter={expandFilter}
                                    onResetFilter={onResetFilter}
                                    setFilter={setExpandFilter}
                                />
                                <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                {expandFilter && (
                                    <AdminFtmListFilter onApplyFilter={onApplyFilter} />
                                )}
                                {!tableDataLoaded && <LoadingIcon styleSet={styles}></LoadingIcon>}
                                {tableDataLoaded && applyFilter && ftmList.length === 0 ?
                                    <EmptyList tableHeaders={tableHeaders} />
                                    : (
                                        <>
                                            <div className="mx-[1.5rem] overflow-x-scroll">
                                                <table className="table-fixed">
                                                    <thead>
                                                        <tr>
                                                            {tableHeaders.map((header, index) => {
                                                                return (
                                                                    <th key={index} className="py-4 text-sm font-semibold text-[#6F6E6E] w-[17%]">
                                                                        <div className={`mx-2 flex gap-x-0 items-center ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                                            {t(header.headerNameKey)}
                                                                            {(header.id !== "action") && (
                                                                                <SortingIcon
                                                                                    headerId={header.id}
                                                                                    sortDescOrder={sortDescOrder}
                                                                                    sortAscOrder={sortAscOrder}
                                                                                    order={order}
                                                                                    activeSortDesc={activeDescIcon}
                                                                                    activeSortAsc={activeAscIcon}
                                                                                />
                                                                            )}
                                                                        </div>
                                                                    </th>
                                                                );
                                                            })}
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        {ftmList.map((ftm, index) => {
                                                            return (
                                                                <tr id={'ftm_list_item' + (index + 1)} key={index} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words ${(ftm.status === "deactivated") ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                                                    <td onClick={() => ftm.status !== 'deactivated' && viewFtmChipDetails(ftm)} className="px-2">{ftm.partnerId}</td>
                                                                    <td onClick={() => ftm.status !== 'deactivated' && viewFtmChipDetails(ftm)} className="px-2">{ftm.orgName}</td>
                                                                    <td onClick={() => ftm.status !== 'deactivated' && viewFtmChipDetails(ftm)} className="px-2">{ftm.ftmId}</td>
                                                                    <td onClick={() => ftm.status !== 'deactivated' && viewFtmChipDetails(ftm)} className="px-2">{ftm.make}</td>
                                                                    <td onClick={() => ftm.status !== 'deactivated' && viewFtmChipDetails(ftm)} className="px-2">{ftm.model}</td>
                                                                    <td onClick={() => ftm.status !== 'deactivated' && viewFtmChipDetails(ftm)} className="px-2">{formatDate(ftm.createdDateTime, 'date')}</td>
                                                                    <td onClick={() => ftm.status !== 'deactivated' && viewFtmChipDetails(ftm)} className="px-2 mx-2">
                                                                        <div className={`${bgOfStatus(ftm.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-semibold rounded-md`}>
                                                                            {getStatusCode(ftm.status, t)}
                                                                        </div>
                                                                    </td>
                                                                    <td className="text-center cursor-default">
                                                                        <div ref={setSubmenuRef(submenuRef, index)}>
                                                                            <button id={"ftm_list_action_menu" + (index + 1)} onClick={() => setActionId(index === actionId ? null : index)} className={`font-semibold mb-0.5 text-[#191919] cursor-pointer text-center`}>
                                                                                ...
                                                                            </button>
                                                                            {actionId === index && (
                                                                                <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                                                                    <div role='button' onClick={() => approveRejectFtmDetails(ftm, index)} className={`flex justify-between hover:bg-gray-100 ${ftm.status === 'pending_approval' ? 'cursor-pointer' : 'cursor-default'} `} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => approveRejectFtmDetails(ftm, index))}>
                                                                                        <p id="ftm_list_approve_reject_option" className={`py-1.5 px-4 ${ftm.status === 'pending_approval' ? 'text-[#3E3E3E] cursor-pointer' : 'text-[#A5A5A5] cursor-default'} ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("approveRejectPopup.approveReject")}</p>
                                                                                        <img src={ftm.status === 'pending_approval' ? approveRejectIcon : disabledApproveRejectIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                    <div role='button' className="flex justify-between hover:bg-gray-100" onClick={() => viewFtmChipDetails(ftm)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => viewFtmChipDetails(ftm))}>
                                                                                        <p id="ftm_list_view_option" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                                                        <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                    <div role='button' onClick={() => deactivateFtmDetails(ftm, index)} className={`flex justify-between hover:bg-gray-100 ${ftm.status === 'approved' ? 'cursor-pointer' : 'cursor-default'}`} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => deactivateFtmDetails(ftm, index))}>
                                                                                        <p id="ftm_list_deactivate_option" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${ftm.status === 'approved' ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t("partnerList.deActivate")}</p>
                                                                                        <img src={ftm.status === 'approved' ? deactivateIcon : disableDeactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                    </div>
                                                                                </div>
                                                                            )}
                                                                            {showActiveIndexFtmApproveRejectPopup === index &&
                                                                                <ApproveRejectPopup
                                                                                    popupData={{ ...selectedFtm, isFtmRequest: true }}
                                                                                    closePopUp={closeApproveRejectPopup}
                                                                                    approveRejectResponse={(responseData, status) => onClickApproveReject(responseData, status, selectedFtm)}
                                                                                    title={`${selectedFtm.make} | ${selectedFtm.model}`}
                                                                                    header={t('ftmRequestApproveRejectPopup.header', { make: selectedFtm.make, model: selectedFtm.model })}
                                                                                    description={t('ftmRequestApproveRejectPopup.description')}
                                                                                />
                                                                            }
                                                                            {showActiveIndexDeactivatePopup === index && (
                                                                                <DeactivatePopup
                                                                                    closePopUp={closeDeactivatePopup}
                                                                                    onClickConfirm={(deactivationResponse) => onClickConfirmDeactivate(deactivationResponse, selectedFtm)}
                                                                                    popupData={{ ...selectedFtm, isDeactivateFtm: true }}
                                                                                    request={deactivateRequest}
                                                                                    headerMsg='deactivateFtmPopup.headerMsg'
                                                                                    descriptionMsg='deactivateFtmPopup.description'
                                                                                />
                                                                            )}
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            )
                                                        })
                                                        }
                                                    </tbody>
                                                </table>
                                            </div>
                                            <Pagination
                                                dataListLength={totalRecords}
                                                selectedRecordsPerPage={selectedRecordsPerPage}
                                                setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                                                setFirstIndex={setFirstIndex}
                                                isServerSideFilter={true}
                                                getPaginationValues={getPaginationValues}
                                            />
                                        </>
                                    )
                                }
                            </div>

                        )}
                    </div>
                </>
            )}
        </div>
    );

}
export default AdminFtmList;