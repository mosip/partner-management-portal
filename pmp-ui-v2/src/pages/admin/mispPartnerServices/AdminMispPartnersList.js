import React, { useState, useRef, useEffect } from 'react';
import { Trans, useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService.js';
import {
    isLangRTL, handleMouseClickForDropdown, resetPageNumber, onClickApplyFilter, setPageNumberAndPageSize,
    getPartnerManagerUrl, handleServiceErrors, onResetFilter, formatDate, bgOfStatus, getStatusCode, onPressEnterKey,
    setSubmenuRef
} from '../../../utils/AppUtils.js';
import ErrorMessage from '../../common/ErrorMessage.js';
import LoadingIcon from '../../common/LoadingIcon.js';
import EmptyList from '../../common/EmptyList.js';
import Title from '../../common/Title.js';
import viewIcon from "../../../svg/view_icon.svg";
import FilterButtons from '../../common/FilterButtons.js';
import SortingIcon from '../../common/SortingIcon.js';
import Pagination from '../../common/Pagination.js';

function AdminMispPartnersList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [mispPartnerServicesList, setMispPartnerServicesList] = useState([]);
    const [expandFilter, setExpandFilter] = useState(false);
    const [order, setOrder] = useState("DESC");
    const [activeAscIcon, setActiveAscIcon] = useState("");
    const [activeDescIcon, setActiveDescIcon] = useState("licenseKeyCreationDate");
    const [actionId, setActionId] = useState(-1);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
    const [sortFieldName, setSortFieldName] = useState("licenseKeyCreationDate");
    const [sortType, setSortType] = useState("desc");
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
    const [fetchData, setFetchData] = useState(false);
    const [tableDataLoaded, setTableDataLoaded] = useState(true);
    const [totalRecords, setTotalRecords] = useState(0);
    const [resetPageNo, setResetPageNo] = useState(false);
    const [isFilterApplied, setIsFilterApplied] = useState(false);
    const [isApplyFilterClicked, setIsApplyFilterClicked] = useState(false);
    const [selectedMispPartner, setSelectedMispPartner] = useState({});
    const [showActiveIndexLicenseKeyPopup, setShowActiveIndexLicenseKeyPopup] = useState(null);
    const [currentPartner, setCurrentPartner] = useState(null);
    const [filterAttributes, setFilterAttributes] = useState({
        partnerId: null,
        policyGroupName: null,
        policyName: null,
        licenseKeyName: null,
        status: null,
    });
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
    }, [submenuRef]);

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'mispPartnerServicesList.partnerId' },
        { id: "policyGroupName", headerNameKey: "mispPartnerServicesList.policyGroup" },
        { id: "policyName", headerNameKey: "mispPartnerServicesList.policyName" },
        { id: "mispLicenseKey", headerNameKey: "mispPartnerServicesList.mispLicenseKey" },
        { id: "licenseKeyCreationDate", headerNameKey: "mispPartnerServicesList.licenseKeyCreationDate" },
        { id: "status", headerNameKey: "mispPartnerServicesList.status" },
        { id: "action", headerNameKey: 'mispPartnerServicesList.action' }
    ];

    const fetchMispPartnerServicesListData = async () => {
        const queryParams = new URLSearchParams();
        queryParams.append('sortFieldName', sortFieldName);
        queryParams.append('sortType', sortType);
        queryParams.append('pageSize', pageSize);

        //reset page number to 0 if filter applied or page number is out of bounds
        const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, resetPageNo);
        queryParams.append('pageNo', effectivePageNo);
        setResetPageNo(false);

        if (filterAttributes.partnerId) queryParams.append('partnerId', filterAttributes.partnerId);
        if (filterAttributes.policyGroupName) queryParams.append('policyGroupName', filterAttributes.policyGroupName);
        if (filterAttributes.policyName) queryParams.append('policyName', filterAttributes.policyName);
        if (filterAttributes.licenseKeyName) queryParams.append('licenseKeyName', filterAttributes.licenseKeyName);
        if (filterAttributes.status) queryParams.append('status', filterAttributes.status);

        try {
            fetchData ? setTableDataLoaded(false) : setDataLoaded(false);
            // TODO: Add HttpService handing
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
        fetchMispPartnerServicesListData();
    }, [sortFieldName, sortType, pageNo, pageSize]);

    useEffect(() => {
        if (isApplyFilterClicked && pageNo === 0) {
            fetchMispPartnerServicesListData();
            setIsApplyFilterClicked(false);
        }
    }, [isApplyFilterClicked]);

    const onApplyFilter = (updatedfilters) => {
        onClickApplyFilter(updatedfilters, setIsFilterApplied, setResetPageNo, setFetchData, setFilterAttributes, setIsApplyFilterClicked);
    };

    const getPaginationValues = (recordsPerPage, pageIndex) => {
        setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setFetchData);
    };

    const sortAscOrder = (header) => {
        if (order !== 'ASC' || activeAscIcon !== header) {
            setFetchData(true);
            setSortFieldName(header);
            setSortType('ASC');
            setOrder("ASC");
            setActiveDescIcon("");
            setActiveAscIcon(header);
        }
    };

    const sortDescOrder = (header) => {
        if (order !== 'DESC' || activeDescIcon !== header) {
            setFetchData(true);
            setSortFieldName(header);
            setSortType('DESC');
            setOrder("DESC");
            setActiveDescIcon(header);
            setActiveAscIcon("");
        }
    };

    const openLicenseKeyPopUp = (partner, index) => {
        if (partner.isActive) {
            setCurrentPartner(partner);
            setShowActiveIndexLicenseKeyPopup(index);
        }
    };

    const viewMispPartnerDetails = (selectedPartner) => {

    };

    const createMispPartner = () => {
        navigate('/partnermanagement/admin/misp-partner-services/create-misp-partner');
    };

    const generateMispLicenseKey = () => {
        // TODO: Implement MISP license key generation
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
                        <ErrorMessage id='admin_misp_partner_services_list_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <div className="flex justify-between mb-3">
                            <Title title='mispPartnerServicesList.mispPartnerServices' backLink='/partnermanagement' />
                            {mispPartnerServicesList.length > 0 ?
                                <button id='create_misp_partner_btn' onClick={() => createMispPartner()} type="button" className="h-10 text-sm font-semibold text-white px-7 rounded-md bg-tory-blue">
                                    {t('mispPartnerServicesList.createMispPartner')}
                                </button>
                                : null
                            }
                        </div>
                        <div className="flex-col justify-center ml-3 h-full">
                            {!isFilterApplied && mispPartnerServicesList.length === 0 ? (
                                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                    <EmptyList
                                        tableHeaders={tableHeaders}
                                        showCustomButton={true}
                                        customButtonName='mispPartnerServicesList.generateMispLicenseKey'
                                        buttonId='generate_misp_license_key_empty_btn'
                                        onClickButton={generateMispLicenseKey}
                                        disableBtn={false} />
                                </div>
                            ) : (
                                <>
                                    <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                                <FilterButtons
                                    listTitle='mispPartnerServicesList.listOfMispPartnerKeys'
                                    dataListLength={totalRecords}
                                    filter={expandFilter}
                                    onResetFilter={onResetFilter}
                                    setFilter={setExpandFilter}
                                />
                                <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                {expandFilter && (
                                    // TODO: Add filter component here
                                    <></>
                                )}
                                {!tableDataLoaded ? (
                                    <LoadingIcon styleSet={styles} />
                                ) : (
                                    <>
                                        {isFilterApplied && mispPartnerServicesList.length === 0 ?
                                            <EmptyList tableHeaders={tableHeaders} showCustomButton={false} />
                                            : (
                                                <>
                                                    <div className="mx-[1.5rem] overflow-x-scroll">
                                                        <table className="table-fixed">
                                                            <thead>
                                                                <tr>
                                                                    {tableHeaders.map((header, index) => {
                                                                        return (
                                                                            <th key={index} className="py-4 text-sm font-semibold text-[#6F6E6E] w-[15%]">
                                                                                <div id={`${header.headerNameKey}_header`} className={`mx-2 flex gap-x-0 items-center ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                                                    {t(header.headerNameKey)}
                                                                                    {(header.id !== "action") && (header.id !== "mispLicenseKey") && (
                                                                                        <SortingIcon
                                                                                            id={`${header.headerNameKey}_sorting_icon`}
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
                                                                {mispPartnerServicesList.map((partner, index) => {
                                                                    return (
                                                                        <tr id={"misp_partner_list_item" + (index + 1)} key={index}
                                                                            className={`border-t border-[#E5EBFA] ${partner.isActive ? 'cursor-pointer' : 'cursor-default'} text-[0.8rem] text-[#191919] font-semibold break-words ${!partner.isActive ? "text-[#969696]" : "text-[#191919]"}`}>
                                                                            <td onClick={() => partner.isActive && viewMispPartnerDetails(partner)} className="px-2">{partner.partnerId}</td>
                                                                            <td onClick={() => partner.isActive && viewMispPartnerDetails(partner)} className="px-2">{partner.policyGroupName ? partner.policyGroupName : '-'}</td>
                                                                            <td onClick={() => partner.isActive && viewMispPartnerDetails(partner)} className="px-2">{partner.policyName ? partner.policyName : '-'}</td>
                                                                            <td onClick={() => partner.isActive && viewMispPartnerDetails(partner)} className="px-2">{partner.mispLicenseKey ? partner.mispLicenseKey : '-'}</td>
                                                                            <td onClick={() => partner.isActive && viewMispPartnerDetails(partner)} className="px-2">{formatDate(partner.licenseKeyCreationDate, "date")}</td>
                                                                            <td onClick={() => partner.isActive && viewMispPartnerDetails(partner)}>
                                                                                <div className={`${bgOfStatus(partner.status)} flex min-w-fit w-14 justify-center py-1.5 px-2 mx-2 my-3 text-xs font-semibold rounded-md`}>
                                                                                    {getStatusCode(partner.status, t)}
                                                                                </div>
                                                                            </td>
                                                                            <td className="text-center cursor-default">
                                                                                <div ref={setSubmenuRef(submenuRef, index)}>
                                                                                    <button id={"misp_partner_list_action_view" + (index + 1)} onClick={() => setActionId(index === actionId ? null : index)} className={`font-semibold mb-0.5 text-[#191919] cursor-pointer text-center`}>
                                                                                        ...
                                                                                    </button>
                                                                                    {actionId === index && (
                                                                                        <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                                                                            <div role='button' className="flex justify-between hover:bg-gray-100" onClick={() => viewMispPartnerDetails(partner)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => viewMispPartnerDetails(partner))}>
                                                                                                <p id="misp_partner_list_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                                                                <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                            </div>

                                                                                        </div>
                                                                                    )}
                                                                                    
                                                                                </div>
                                                                            </td>
                                                                        </tr>
                                                                    );
                                                                })}
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </>
                                            )}
                                    </>
                                )}
                                <Pagination
                                    dataListLength={totalRecords}
                                    selectedRecordsPerPage={selectedRecordsPerPage}
                                    setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                                    isServerSideFilter={true}
                                    getPaginationValues={getPaginationValues}
                                    isApplyFilterClicked={isApplyFilterClicked}
                                    setIsApplyFilterClicked={setIsApplyFilterClicked}
                                />
                                    </div>
                                </>
                            )}
                        </div>
                    </div>
                </>
            )}
        </div>
    );
}

export default AdminMispPartnersList;
